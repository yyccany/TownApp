/**
 * ============================================
 * 🗄️ DataRepository - 多源数据统一整合层
 * ============================================
 * 职责：
 * 1. 自动聚合所有 CategoryData_*.kt + CompleteData*.kt 的数据
 * 2. 合并去重，保留最新量化指标
 * 3. 按 ContactType 自动分类（EDIBLE/SKIN_CONTACT/NON_CONTACT）
 * 4. 全局数据校验，超标指标客观提示
 * 5. 为 UI 层提供统一接口，不感知底层数据来源
 * 
 * 核心设计：
 * - allProducts / allMaterials / allCompositions 已在 Models.kt 中统一定义
 * - 本层在此基础上提供：去重、分组、校验、统计、接触类型路由
 * ============================================
 */
package com.example.townapp.data

// ============================================
// 📊 接触类型分组结果
// ============================================
data class ContactTypeGrouping(
    val edibleProducts: List<ProductItem>,      // 食用/口服类
    val skinContactProducts: List<ProductItem>, // 肤接触类
    val nonContactProducts: List<ProductItem>,  // 非接触类
    val edibleMaterials: List<MaterialItem>,
    val skinContactMaterials: List<MaterialItem>,
    val nonContactMaterials: List<MaterialItem>
)

// ============================================
// 🔍 数据校验结果
// ============================================
data class DataValidationResult(
    val totalChecks: Int,
    val passedChecks: Int,
    val warnings: List<DataWarning>,
    val errors: List<DataError>
)

data class DataWarning(
    val itemId: Int,
    val itemName: String,
    val field: String,
    val message: String
)

data class DataError(
    val itemId: Int,
    val itemName: String,
    val field: String,
    val message: String
)

// ============================================
// 📦 品类信息汇总
// ============================================
data class CategorySummary(
    val displayName: String,
    val internalName: String,
    val emoji: String,
    val productCount: Int,
    val contactType: ContactType
)

// ============================================
// 🗄️ DataRepository 单例
// ============================================
object DataRepository {

    // ── 核心数据引用：委托给 Models.kt 的顶层 lazy 全量数据 ──

    private fun rawProducts(): List<ProductItem> = allProducts
    private fun rawMaterials(): List<MaterialItem> = allMaterials
    private fun rawCompositions(): List<CompositionRelation> = allCompositions

    /** 所有成品（已去重） */
    val products: List<ProductItem> by lazy {
        rawProducts().distinctBy { it.id }
    }

    /** 所有原材料（已去重） */
    val materials: List<MaterialItem> by lazy {
        rawMaterials().distinctBy { it.id }
    }

    /** 所有材料配比（已去重） */
    val compositions: List<CompositionRelation> by lazy {
        rawCompositions().distinct()
    }

    // ── 按 ContactType 自动分组（核心路由逻辑） ──

    /** 按接触类型分组 - 产品和材料 */
    val contactTypeGrouping: ContactTypeGrouping by lazy {
        ContactTypeGrouping(
            edibleProducts = products.filter { it.contactType == ContactType.EDIBLE },
            skinContactProducts = products.filter { it.contactType == ContactType.SKIN_CONTACT },
            nonContactProducts = products.filter { it.contactType == ContactType.NON_CONTACT },
            edibleMaterials = materials.filter { it.contactType == ContactType.EDIBLE },
            skinContactMaterials = materials.filter { it.contactType == ContactType.SKIN_CONTACT },
            nonContactMaterials = materials.filter { it.contactType == ContactType.NON_CONTACT }
        )
    }

    // ── 品类 → 产品列表 索引（O(1)查询） ──

    val productsByCategory: Map<String, List<ProductItem>> by lazy {
        products.groupBy { it.category }
    }

    val materialsByCategory: Map<String, List<MaterialItem>> by lazy {
        materials.groupBy { it.category }
    }

    // ── ID 快速查找（O(1)） ──

    private val productById: Map<Int, ProductItem> by lazy {
        products.associateBy { it.id }
    }

    private val materialById: Map<Int, MaterialItem> by lazy {
        materials.associateBy { it.id }
    }

    fun getProductById(id: Int): ProductItem? = productById[id]
    fun getMaterialById(id: Int): MaterialItem? = materialById[id]

    // ── 品类信息汇总（自动衍生，新增品类自动出现） ──

    val allCategorySummaries: List<CategorySummary> by lazy {
        val emojiMap = mapOf(
            "房产" to "🏠", "数码产品" to "📱", "汽车" to "🚗",
            "宠物用品" to "🐾", "五金工具" to "🔧", "医疗健康" to "🏥",
            "体育健身" to "🏃", "玩具桌游" to "🎲", "美术文创" to "🎨",
            "农林农具" to "🌾", "珠宝古董" to "💎", "无形服务" to "🧩",
            "音乐影音" to "🎵", "包装物料" to "📦",
            "家居用品" to "🪑", "办公文具" to "✏️", "露营户外" to "🏕️",
            "婴童用品" to "🍼"
        )

        products.groupBy { it.category }.map { (category, categoryProducts) ->
            val dominantContact = categoryProducts.groupBy { it.contactType }
                .maxByOrNull { it.value.size }?.key
                ?: ContactType.NON_CONTACT

            CategorySummary(
                displayName = category,
                internalName = category,
                emoji = emojiMap[category] ?: "📦",
                productCount = categoryProducts.size,
                contactType = dominantContact
            )
        }.sortedByDescending { it.productCount }
    }

    // ============================================
    // 🔍 数据校验引擎
    // ============================================

    fun validateAll(): DataValidationResult {
        val warnings = mutableListOf<DataWarning>()
        val errors = mutableListOf<DataError>()
        var checks = 0

        // 1. 产品ID重复检查
        checks++
        val duplicateIds = products.groupBy { it.id }.filter { it.value.size > 1 }
        duplicateIds.forEach { (id, items) ->
            errors.add(DataError(id, items[0].name, "ID",
                "产品ID $id 存在 ${items.size} 条重复记录"))
        }

        // 2. 品类名有效性检查
        checks++
        products.forEach { p ->
            if (p.category.isBlank()) {
                errors.add(DataError(p.id, p.name, "category", "品类名为空"))
            }
        }

        // 3. 接触类型安全提示
        checks++
        val highRiskSkinPatterns = listOf("药品", "外用", "贴", "膏")
        contactTypeGrouping.skinContactProducts.forEach { p ->
            if (highRiskSkinPatterns.any { p.name.contains(it) || p.category.contains(it) }) {
                warnings.add(DataWarning(p.id, p.name, "contactType",
                    "[客观提示] SKIN_CONTACT类产品，请确认满足 GBT 22796 接触安全规范"))
            }
        }

        // 4. 空洞品类检测
        checks++
        productsByCategory.forEach { (category, categoryProducts) ->
            if (categoryProducts.size == 1) {
                warnings.add(DataWarning(0, category, "category",
                    "品类 '$category' 仅含1个产品，建议补充数据"))
            }
        }

        // 5. 材料配比完整性
        checks++
        val prodIdsWithComp = compositions.map { it.productId }.toSet()
        products.forEach { p ->
            if (p.id !in prodIdsWithComp) {
                warnings.add(DataWarning(p.id, p.name, "composition",
                    "[客观提示] 暂无材料配比数据，成本核算将使用默认值"))
            }
        }

        return DataValidationResult(
            totalChecks = checks,
            passedChecks = checks - warnings.size - errors.size,
            warnings = warnings,
            errors = errors
        )
    }

    // ============================================
    // 📊 统计数据（自动聚合，无需手动扩容）
    // ============================================

    val contactTypeStats: Map<ContactType, Int> by lazy {
        products.groupBy { it.contactType }.mapValues { it.value.size }
    }

    val totalCategoryCount: Int by lazy { productsByCategory.size }

    val productsWithCompositionCount: Int by lazy {
        compositions.map { it.productId }.distinct().size
    }

    val productsWithMarketPriceCount: Int by lazy {
        products.count { it.marketPrice > 0 }
    }

    val avgCompositionDepth: Double by lazy {
        val prodIds = compositions.map { it.productId }.distinct()
        if (prodIds.isNotEmpty()) compositions.size.toDouble() / prodIds.size else 0.0
    }

    fun getFullDataSnapshot(): Map<String, Any> = mapOf(
        "totalProducts" to products.size,
        "totalMaterials" to materials.size,
        "totalCompositions" to compositions.size,
        "totalCategories" to totalCategoryCount,
        "edibleCount" to contactTypeGrouping.edibleProducts.size,
        "skinContactCount" to contactTypeGrouping.skinContactProducts.size,
        "nonContactCount" to contactTypeGrouping.nonContactProducts.size,
        "categoryBreakdown" to productsByCategory.mapValues { it.value.size },
        "productsWithComposition" to productsWithCompositionCount,
        "productsWithMarketPrice" to productsWithMarketPriceCount,
        "avgCompositionDepth" to avgCompositionDepth,
        "totalEntries" to (products.size + materials.size + compositions.size)
    )

    // ============================================
    // 🧭 接触类型路由
    // ============================================

    fun affectsPhysiological(product: ProductItem): Boolean =
        product.contactType == ContactType.EDIBLE

    fun affectsSkin(product: ProductItem): Boolean =
        product.contactType == ContactType.SKIN_CONTACT

    fun affectsMood(product: ProductItem): Boolean =
        product.contactType == ContactType.NON_CONTACT
}