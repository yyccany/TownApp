package com.example.townapp.data

// ============================================
// 衣物数据类
// ============================================
data class ClothingItem(
    val id: Int,
    val name: String,
    val category: String,
    val brand: String = "",
    val price: Double = 0.0,
    val contactType: ContactType = ContactType.SKIN_CONTACT,  // 接触分类
    val purchaseDate: Long = System.currentTimeMillis(),
    val material: String = "",
    val fabricType: String = "",
    val fabrics: List<String> = emptyList(),
    val fabricRatios: List<Double> = emptyList(),
    val style: String = "",
    val color: String = "",
    val size: String = "",
    val expectedLifespanMonths: Double = 24.0,
    val actualLifespanMonths: Double? = null,
    val wearCount: Int = 0,
    val maxWearsPerYear: Int = 60,
    val useDaysPerYear: Int = 0,
    val dryClean: Boolean = false,
    val costPerWear: Double = 0.0,
    val costPerMonth: Double = 0.0,
    val valueRetentionRate: Double = 0.5,
    val isIQTax: Boolean = false,
    val iqTaxLevel: Int = 0,
    val iqTaxReason: String = "",
    val evolutionParadoxScore: Double = 0.0,
    val seasonalSuitability: String = "四季",
    val versatilityScore: Double = 0.0,
    val comfortScore: Double = 0.0,
    val durabilityScore: Double = 0.0,
    val qualityScore: Double = 0.0,
    val breedingDegradation: Double = 0.0,
    val processingLoss: Double = 0.0,
    val decisionDifficulty: Double = 0.0,
    val matchingDifficulty: Double = 0.0,
    val plannedObsolescence: Double = 0.0,
    val materialDegradation: Double = 0.0,
    val note: String = "",
    val imagePath: String? = null,
    val imagePaths: List<String> = emptyList(),
    /** 衣物闪光点文案（v1.2 阶段二） */
    val sparkle: String = ""
) {
    /**
     * 计算每小时成本
     */
    fun getCostPerHour(): Double {
        val totalHours = getTotalUsageHours()
        if (totalHours <= 0) return Double.MAX_VALUE
        return price / totalHours
    }

    /**
     * 计算总使用时长（小时）
     */
    private fun getTotalUsageHours(): Double {
        val yearsOwned = (System.currentTimeMillis() - purchaseDate) / (1000L * 60 * 60 * 24 * 365)
        val totalDays = yearsOwned * maxWearsPerYear * if (dryClean) 0.8 else 1.0
        return totalDays * 8 // 假设每次穿8小时
    }

    /**
     * 获取实际使用年数
     */
    fun getRealLife(): Double {
        val yearsOwned = (System.currentTimeMillis() - purchaseDate) / (1000L * 60 * 60 * 24 * 365)
        return if (yearsOwned <= 0) expectedLifespanMonths / 12 else yearsOwned.toDouble()
    }

    /**
     * 获取整体退化程度
     */
    fun getOverallDegradation(): Double {
        return (breedingDegradation + processingLoss + materialDegradation) / 3.0
    }
}

// ============================================
// 食物数据类
// ============================================
data class FoodItem(
    val id: Int,
    val name: String,
    val category: String,
    val pricePer100g: Double = 0.0,
    val contactType: ContactType = ContactType.EDIBLE,  // 接触分类
    val nutritionalScore: Double = 75.0, // 综合营养分数（0-100）
    val servingSize: Int = 100, // 每份克数
    val proteinPer100g: Double = 0.0,
    val fatPer100g: Double = 0.0,
    val carbohydratePer100g: Double = 0.0,
    val fiberPer100g: Double = 0.0,
    val caloriesPer100g: Double = 0.0,
    val typicalServing: Int = 100,
    val shelfLifeDays: Double = 30.0,
    val mealsPerWeek: Int = 3,
    val qualityScore: Double = 0.5,
    val breedingDegradation: Double = 0.2,
    val processingLoss: Double = 0.1,
    val isIQTax: Boolean = false,
    val iqTaxLevel: Int = 0,
    val iqTaxReason: String = "",
    val evolutionMismatchLevel: String = "低",
    val nutrientDensityScore: Double = 0.0,
    val vitaminScore: Double = 0.0,
    val mineralScore: Double = 0.0,
    val glycemicIndex: Double = 0.0,
    val satietyIndex: Double = 0.0,
    val note: String = "",
    val imagePath: String? = null,
    val imagePaths: List<String> = emptyList()
)

// ============================================
// 住房/家居数据类
// ============================================
data class HousingItem(
    val id: Int,
    val name: String,
    val category: String,
    val brand: String = "",
    val model: String = "",
    val price: Double = 0.0,
    val contactType: ContactType = ContactType.NON_CONTACT,  // 接触分类
    val lifespanYears: Double = 5.0,
    val depreciationRate: Double = 0.2,
    val annualCost: Double = 0.0,
    val monthlyCost: Double = 0.0,
    val dailyCost: Double = 0.0,
    val isIQTax: Boolean = false,
    val iqTaxLevel: Int = 0,
    val iqTaxReason: String = "",
    val practicalityScore: Double = 0.0,
    val durabilityScore: Double = 0.0,
    val aestheticScore: Double = 0.0,
    val spaceOccupied: String = "小",
    val energyConsumption: String = "低",
    val maintenanceCost: Double = 0.0,
    val maintenanceFrequency: String = "",
    val warrantyYears: Double = 0.0,
    val resaleValueRate: Double = 0.0,
    val note: String = "",
    val imagePath: String? = null,
    val imagePaths: List<String> = emptyList(),
    // 新增字段
    val monthlyRent: Double = 0.0,
    val propertyFee: Double = 0.0,
    val utilityFee: Double = 0.0,
    val residents: Int = 1,
    val houseSize: Double = 50.0,
    val items: List<HouseholdItem> = emptyList()
)

// 家居物品类
data class HouseholdItem(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double = 0.0,
    val contactType: ContactType = ContactType.NON_CONTACT,  // 接触分类
    val lifespanYears: Double = 5.0,
    val brand: String = "",
    val note: String = "",
    val imagePath: String? = null
)

// ============================================
// 认知误区数据类
// ============================================
data class CognitiveItem(
    val id: Int,
    val title: String,
    val category: String,
    val myth: String = "",
    val truth: String = "",
    val explanation: String = "",
    val examples: List<String> = emptyList(),
    val relatedIQTax: List<String> = emptyList(),
    val awakeningLevel: Int = 1,
    val source: String = "",
    val isDisproven: Boolean = true,
    val psychologicalMechanism: String = "",
    val typicalScenario: String = "",
    val howToAvoid: String = ""
)

// ============================================
// 成语纠错数据类
// ============================================
data class IdiomItem(
    val id: Int,
    val idiom: String = "",
    val name: String = "", // 兼容 CognitiveScreen.kt 的使用
    val pinyin: String = "",
    val traditionalMeaning: String = "",
    val originalMeaning: String = "", // 兼容 CognitiveScreen.kt 的使用
    val modernInterpretation: String = "",
    val logicalAnalysis: String = "",
    val exampleSentence: String = "",
    val example: String = "", // 兼容 CognitiveScreen.kt 的使用
    val awakeningValue: Int = 1,
    val awakeningLevel: Int = 1, // 兼容 CognitiveScreen.kt 的使用
    val category: String = "",
    val description: String = "", // 兼容 CognitiveScreen.kt 的使用
    val cognitiveBias: String = "", // 兼容 CognitiveScreen.kt 的使用
    val relatedCognitiveBiases: List<String> = emptyList(),
    val funFact: String? = null, // 兼容 CognitiveScreen.kt 的使用
    val origin: String = "",
    // 第二层：学术注脚
    val academicFootnote: AcademicFootnote? = null,
    // 第三层：哲学思考
    val philosophicalReflection: PhilosophicalReflection? = null
) {
    // 兼容性 getter 方法
    val displayName: String
        get() = if (name.isNotEmpty()) name else idiom
}

// ============================================
// 学术注脚数据类
// ============================================
data class AcademicFootnote(
    val reference: String = "", // 学术引用
    val author: String = "", // 作者
    val year: String = "", // 年份
    val explanation: String = "" // 学术解释
)

// ============================================
// 哲学思考数据类
// ============================================
data class PhilosophicalReflection(
    val question: String = "", // 哲学问题
    val reflection: String = "" // 思考引导
)

// ============================================
// 商品条形码数据类
// ============================================
data class BarcodeProductItem(
    val id: Int,
    val barcode: String,
    val name: String,
    val brand: String = "",
    val categories: List<String> = emptyList(),
    val nutriScore: String = "",
    val novaGroup: Int = 0,
    val ingredients: List<String> = emptyList(),
    val nutritionFacts: Map<String, Double> = emptyMap(),
    val iqTaxScore: Double = 0.0,
    val imageUrl: String = "",
    val servingSize: String = "",
    val allergens: List<String> = emptyList(),
    val labels: List<String> = emptyList(),
    val note: String = ""
)

// ============================================
// 菜谱系统
// ============================================
data class Recipe(
    val recipeId: Int,
    val dishName: String,
    val cuisine: String,
    val simpleSteps: String,
    val ingredientIds: List<RecipeIngredient>  // 食材+克重列表
)

data class RecipeIngredient(
    val foodId: Int,
    val useGram: Float
)

// ============================================
// 穿搭场景分类
// ============================================
enum class OutfitScene(val displayName: String, val description: String) {
    HOME("居家", "仅室内生效，外出穿戴社交好感下降"),
    COMMUTE("通勤", "工作日上班、逛街默认穿搭"),
    BUSINESS("商务正式", "面试、正式相亲、公职会谈启用"),
    OUTDOOR("户外出行", "体力干活、爬山、外勤使用"),
    DATE("约会休闲", "青年相亲、周末约会优先选取")
}

// ============================================
// 穿搭套装系统
// ============================================
data class OutfitSet(
    val setId: Int,
    val setName: String,
    val styleTag: String,
    val colorMatchDesc: String,
    val clothIdList: List<Int>,
    val scene: OutfitScene = OutfitScene.COMMUTE,  // 默认通勤，兼容旧数据
    val fabricType: FabricType = FabricType.COTTON,  // 面料类型
    val isThick: Boolean = false                     // 是否加厚款
)

// ============================================
// 颜色风格标签
// ============================================
enum class ColorStyleTag {
    CLASSIC,    // 经典撞色（黑白/白蓝）
    SAME_TONE,  // 同色系搭配
    LIGHT,      // 浅色系（清爽）
    DARK,       // 深色系（沉稳）
    RETRO       // 复古色系
}

// ============================================
// 物品接触分类（每条数据必标注）
// ============================================
enum class ContactType {
    EDIBLE,          // 食用类：可入口、口服类物品
    SKIN_CONTACT,    // 肤接触类：直接接触皮肤、黏膜的物品
    NON_CONTACT      // 非接触类：常规摆放、使用，不直接接触人体的物品
}

// ============================================
// 免责声明常量
// ============================================
const val DISCLAIMER_TEXT = "【免责声明】本项目所有初始数据、参数、文字均为虚构创作，基于公开资料改编，仅作学习参考使用；用户可自主修改全部内容，自定义内容由使用者自行承担相关责任。若存在相关权益问题，请联系我方处理。"

// ============================================
// 全局颜色库实体
// ============================================
data class GlobalColor(
    val colorId: Int,
    val colorName: String,
    val colorHex: String,
    val styleTag: ColorStyleTag
)

// ============================================
// 标准人体身形数据（纯参考，不参与图形/业务计算）
// ============================================
data class StandardBodyData(
    val bodyId: Int,
    val gender: String,
    val height: Float,
    val bust: Float,
    val waist: Float,
    val hip: Float,
    val tips: String
)

// ============================================
// 像素绘制形状类型（区分款式，固定尺寸）
// ============================================
enum class PixelShapeType {
    SQUARE,       // 方形（配饰/小件）
    HORIZONTAL,   // 横向（上衣/外套）
    VERTICAL,     // 竖向（裤子/裙子）
    DISH_PLATE,   // 盘子（炒菜用）
    DISH_BOWL     // 碗（汤菜用）
}

// ============================================
// 物品状态（全新/磨损/老旧）
// ============================================
enum class ItemState {
    NEW,
    SLIGHT_WORN,
    OLD
}

// ============================================
// 全局颜色库预设数据
// ============================================
val globalColorList = listOf(
    GlobalColor(1, "纯白色", "#FFFFFF", ColorStyleTag.LIGHT),
    GlobalColor(2, "纯黑色", "#222222", ColorStyleTag.DARK),
    GlobalColor(3, "卡其色", "#D2B48C", ColorStyleTag.RETRO),
    GlobalColor(4, "藏青色", "#1F314F", ColorStyleTag.DARK),
    GlobalColor(5, "浅灰色", "#E5E5E5", ColorStyleTag.LIGHT),
    GlobalColor(6, "浅蓝色", "#87CEEB", ColorStyleTag.LIGHT),
    GlobalColor(7, "深灰色", "#666666", ColorStyleTag.DARK),
    GlobalColor(8, "米白色", "#FFF8DC", ColorStyleTag.LIGHT),
    GlobalColor(9, "牛仔蓝", "#4A6FA5", ColorStyleTag.CLASSIC),
    GlobalColor(10, "酒红色", "#8B2252", ColorStyleTag.RETRO)
)

// ============================================
// 标准人体数据（2套）
// ============================================
val standardBodyList = listOf(
    StandardBodyData(
        bodyId = 1,
        gender = "男生",
        height = 170f,
        bust = 90f,
        waist = 78f,
        hip = 92f,
        tips = "170cm标准身形，推荐经典黑白、浅色系穿搭，视觉比例更协调"
    ),
    StandardBodyData(
        bodyId = 2,
        gender = "女生",
        height = 165f,
        bust = 88f,
        waist = 70f,
        hip = 90f,
        tips = "165cm标准身形，推荐同色系浅搭配，风格清爽显瘦"
    )
)

// 示例菜谱数据
val sampleRecipes: List<Recipe> = listOf(
    Recipe(
        recipeId = 1,
        dishName = "番茄炒蛋",
        cuisine = "家常菜",
        ingredientIds = listOf(
            RecipeIngredient(foodId = 1, useGram = 200f),
            RecipeIngredient(foodId = 2, useGram = 100f)
        ),
        simpleSteps = "1. 番茄切块，鸡蛋打散加盐\n2. 热锅凉油，先炒鸡蛋至定型盛出\n3. 锅中加油炒番茄至出汁\n4. 倒入鸡蛋翻炒均匀，调味即可"
    ),
    Recipe(
        recipeId = 2,
        dishName = "麻婆豆腐",
        cuisine = "川菜",
        ingredientIds = listOf(
            RecipeIngredient(foodId = 3, useGram = 300f),
            RecipeIngredient(foodId = 4, useGram = 50f)
        ),
        simpleSteps = "1. 豆腐切块焯水，牛肉末备用\n2. 锅中热油，下牛肉末炒香\n3. 加入豆瓣酱、豆豉、蒜末炒出红油\n4. 加适量水烧开，放入豆腐煮3分钟\n5. 用水淀粉勾芡，撒花椒粉和葱花即可"
    ),
    Recipe(
        recipeId = 3,
        dishName = "白切鸡",
        cuisine = "粤菜",
        ingredientIds = listOf(
            RecipeIngredient(foodId = 5, useGram = 500f)
        ),
        simpleSteps = "1. 鸡洗净，锅中加水烧开\n2. 放入整鸡，大火煮15分钟\n3. 关火焖10分钟至熟透\n4. 捞出过冰水，使皮爽肉滑\n5. 斩件装盘，配姜葱蘸料食用"
    ),
    Recipe(
        recipeId = 4,
        dishName = "蛋炒饭",
        cuisine = "主食",
        ingredientIds = listOf(
            RecipeIngredient(foodId = 6, useGram = 200f),
            RecipeIngredient(foodId = 2, useGram = 50f)
        ),
        simpleSteps = "1. 鸡蛋打散，米饭最好用隔夜饭\n2. 热锅凉油，先炒鸡蛋至碎末状\n3. 倒入米饭大火翻炒，使米粒散开\n4. 加盐调味，撒葱花出锅"
    ),
    Recipe(
        recipeId = 5,
        dishName = "回锅肉",
        cuisine = "川菜",
        ingredientIds = listOf(
            RecipeIngredient(foodId = 7, useGram = 300f)
        ),
        simpleSteps = "1. 五花肉冷水下锅煮至八成熟，捞出切薄片\n2. 青蒜切段，豆瓣酱剁细\n3. 锅中少油，下肉片煸炒至卷曲出油\n4. 加入豆瓣酱炒出红油\n5. 放入青蒜翻炒均匀即可"
    ),
    Recipe(
        recipeId = 6,
        dishName = "清蒸鲈鱼",
        cuisine = "粤菜",
        ingredientIds = listOf(
            RecipeIngredient(foodId = 8, useGram = 400f)
        ),
        simpleSteps = "1. 鲈鱼处理干净，两面划几刀\n2. 鱼身抹盐和料酒，放姜片葱段\n3. 水开后上锅蒸8-10分钟\n4. 倒掉蒸汁，铺上葱姜丝\n5. 淋热油和蒸鱼豉油即可"
    )
)

// ============================================
// 通用原材料数据模型（食材/纺织料/建材/电子料/工业料 统一用此模型）
// ============================================
data class MaterialItem(
    val id: Int,
    val name: String,
    val category: String,         // 品类：建材/电子料/工业料/纺织料
    val unit: String,             // 单位：g/kg/吨/个/平方米
    val pricePerUnit: Double,     // 每单位价格（元）
    val contactType: ContactType = ContactType.NON_CONTACT,  // 接触分类
    val colorId: Int = 5,         // 全局颜色库ID
    val description: String = ""
)

// ============================================
// 通用成品-原材料拆解关系（菜谱/西装/房子/手机/车 统一用此模型）
// ============================================
data class CompositionRelation(
    val productId: Int,        // 成品ID
    val materialId: Int,       // 原材料ID
    val useAmount: Double,     // 用量
    val unit: String           // 单位
)

// ============================================
// 通用成品模型
// ============================================
// ============================================
// 🔬 物理结构量化指标 — 四维度标准框架
// 不分国界、不看品牌、不贴标签，只看物理结构的客观量化数据
// ============================================
data class PhysicalQuantification(
    // ═══ 维度一：吃进去 — 分子层面的物理差异，作用于生理 ═══
    val molecularFormula: String = "",       // 有效成分分子式（如 C₁₃H₁₈O₂）
    val activeIngredientRatio: Double = 0.0, // 有效成分占比 %
    val metabolicHalfLifeH: Double = 0.0,    // 代谢半周期（小时）
    val giStimulationRate: Double = 0.0,     // 肠胃刺激率 %
    val glycemicIndex: Int = 0,              // 升糖指数
    val emptyCalorieRatio: Double = 0.0,     // 空热量占比 %
    val proteinRatio: Double = 0.0,          // 蛋白质含量占比 %

    // ═══ 维度二：接触皮肤 — 材料层面的物理差异，作用于皮肤 ═══
    val formaldehydeMgKg: Double = 0.0,      // 甲醛含量 mg/kg
    val allergyRate: Double = 0.0,           // 致敏率 %
    val phLevel: Double = 0.0,               // pH值
    val radiationWKg: Double = 0.0,          // 表面辐射 W/kg
    val thermalConductivity: Double = 0.0,   // 导热系数 W/(m·K)
    val materialComposition: String = "",    // 材料组成简述

    // ═══ 维度三：承载空间 — 结构层面的物理差异，作用于环境 ═══
    val bodyStrengthMPa: Double = 0.0,       // 结构强度 MPa
    val energyPer100km: Double = 0.0,        // 百公里能耗（kWh或L）
    val energyUnit: String = "",             // 能耗单位："kWh" / "L"
    val annualFaultRate: Double = 0.0,       // 年故障概率 %
    val safetyCoefficient: Int = 0,          // 安全系数 0-100

    // ═══ 维度四：抚平精神 — 信息层面的物理差异，作用于精神 ═══
    val fragmentationRate: Double = 0.0,     // 系统碎片化率 %
    val responseLatencyMs: Double = 0.0,     // 响应延迟 ms
    val notificationInterference: Double = 0.0, // 通知干扰率 %
    val bpm: Int = 0,                        // 节奏 BPM（音乐/乐器）
    val dynamicRangeDb: Double = 0.0,         // 频率动态范围 dB
    val informationEntropy: Double = 0.0      // 信息熵
) {
    /** 判断是否有任何维度的量化数据 */
    fun hasAnyData(): Boolean {
        return molecularFormula.isNotBlank() || activeIngredientRatio > 0 || metabolicHalfLifeH > 0 ||
            giStimulationRate > 0 || glycemicIndex > 0 || emptyCalorieRatio > 0 || proteinRatio > 0 ||
            formaldehydeMgKg > 0 || allergyRate > 0 || phLevel > 0 ||
            radiationWKg > 0 || thermalConductivity > 0 || materialComposition.isNotBlank() ||
            bodyStrengthMPa > 0 || energyPer100km > 0 || annualFaultRate > 0 || safetyCoefficient > 0 ||
            fragmentationRate > 0 || responseLatencyMs > 0 || notificationInterference > 0 ||
            bpm > 0 || dynamicRangeDb > 0 || informationEntropy > 0
    }

    /** 维度一：吃进去 — 是否有数据 */
    fun hasEdibleData(): Boolean {
        return molecularFormula.isNotBlank() || activeIngredientRatio > 0 || metabolicHalfLifeH > 0 ||
            giStimulationRate > 0 || glycemicIndex > 0 || emptyCalorieRatio > 0 || proteinRatio > 0
    }

    /** 维度二：接触皮肤 — 是否有数据 */
    fun hasSkinData(): Boolean {
        return formaldehydeMgKg > 0 || allergyRate > 0 || phLevel > 0 ||
            radiationWKg > 0 || thermalConductivity > 0 || materialComposition.isNotBlank()
    }

    /** 维度三：承载空间 — 是否有数据 */
    fun hasSpaceData(): Boolean {
        return bodyStrengthMPa > 0 || energyPer100km > 0 || annualFaultRate > 0 || safetyCoefficient > 0
    }

    /** 维度四：抚平精神 — 是否有数据 */
    fun hasSpiritData(): Boolean {
        return fragmentationRate > 0 || responseLatencyMs > 0 || notificationInterference > 0 ||
            bpm > 0 || dynamicRangeDb > 0 || informationEntropy > 0
    }
}

data class ProductItem(
    val id: Int,
    val name: String,
    val category: String,         // 品类：菜谱/穿搭/房产/数码/汽车
    val contactType: ContactType = ContactType.NON_CONTACT,  // 接触分类
    val colorId: Int = 2,         // 全局颜色库ID
    val shapeType: PixelShapeType = PixelShapeType.SQUARE,
    val description: String = "",
    val emoji: String = "",       // 产品图标 emoji
    val marketPrice: Double = 0.0, // 市场零售价（用于计算溢价，默认0=未设置）
    val isCustom: Boolean = false, // 是否为用户自定义添加（true=可编辑/删除）
    val physicalQuant: PhysicalQuantification? = null  // 🔬 物理结构量化指标
)

// ============================================
// 📊 数据资产统计模型
// ============================================
data class DataStats(
    val totalProducts: Int = 0,           // 成品总数
    val totalMaterials: Int = 0,          // 原材料总数
    val totalCompositions: Int = 0,       // 材料配比总数
    val totalFoodItems: Int = 0,          // 食材总数
    val totalRecipes: Int = 0,            // 菜谱总数
    val totalOutfits: Int = 0,            // 穿搭总数
    val totalClothingItems: Int = 0,      // 服饰单品总数
    val totalCategories: Int = 0,         // 品类数量
    val categoryBreakdown: Map<String, Int> = emptyMap(),  // 各品类产品数
    val productsWithComposition: Int = 0, // 有材料配比的产品数
    val productsWithMarketPrice: Int = 0, // 有市场价的产品数（可计算溢价）
    val avgCompositionDepth: Double = 0.0,// 平均材料复杂度
    val totalDataEntries: Int = 0         // 数据总条目
)

// ============================================
// 建材原材料数据（ID: 5000-5999）
// ============================================
val buildingMaterials = listOf(
    MaterialItem(id = 5001, name = "水泥", category = "建材", unit = "吨", pricePerUnit = 400.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "硅酸盐水泥 42.5R | 抗压42.5MPa"),
    MaterialItem(id = 5002, name = "钢筋", category = "建材", unit = "吨", pricePerUnit = 5000.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "HRB400螺纹钢 12mm | 屈服400MPa"),
    MaterialItem(id = 5003, name = "沙子", category = "建材", unit = "吨", pricePerUnit = 150.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "中砂河沙 | 细度模数2.3-3.0 | 含泥<3%"),
    MaterialItem(id = 5004, name = "瓷砖", category = "建材", unit = "平方米", pricePerUnit = 80.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "600×600mm抛光砖 | 吸水率<0.5% | 硬度6"),
    MaterialItem(id = 5005, name = "木材", category = "建材", unit = "立方米", pricePerUnit = 2500.0, contactType = ContactType.NON_CONTACT, colorId = 3, description = "松木 含水率12% | 抗弯70MPa"),
    MaterialItem(id = 5006, name = "玻璃", category = "建材", unit = "平方米", pricePerUnit = 120.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "5mm钢化玻璃 | 抗冲击≥90MPa"),
    MaterialItem(id = 5007, name = "油漆", category = "建材", unit = "桶", pricePerUnit = 200.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "18L乳胶漆 | VOC<50g/L | 遮盖率0.95"),
    MaterialItem(id = 5008, name = "电线", category = "建材", unit = "米", pricePerUnit = 5.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "BV 2.5mm²铜芯 | 耐压450/750V"),
    MaterialItem(id = 5009, name = "水管", category = "建材", unit = "米", pricePerUnit = 15.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "PPR冷水管 DN25 | 耐压1.6MPa"),
    MaterialItem(id = 5010, name = "砖块", category = "建材", unit = "块", pricePerUnit = 1.5, contactType = ContactType.NON_CONTACT, colorId = 3, description = "240×115×53mm烧结砖 | 抗压10MPa")
)

// ============================================
// 电子原材料数据（ID: 6000-6999）
// ============================================
val electronicMaterials = listOf(
    MaterialItem(id = 6001, name = "硅晶圆", category = "电子料", unit = "片", pricePerUnit = 200.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "12英寸单晶硅 | 纯度99.9999% | 芯片基底"),
    MaterialItem(id = 6002, name = "铜箔", category = "电子料", unit = "千克", pricePerUnit = 80.0, contactType = ContactType.NON_CONTACT, colorId = 3, description = "电解铜箔 18μm | 导电率≥100% | PCB用"),
    MaterialItem(id = 6003, name = "锂电池", category = "电子料", unit = "个", pricePerUnit = 50.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "3.7V 5000mAh锂聚合物 | 循环500次"),
    MaterialItem(id = 6004, name = "芯片", category = "电子料", unit = "个", pricePerUnit = 300.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "SoC处理器 5nm工艺 | 百亿晶体管级"),
    MaterialItem(id = 6005, name = "显示屏", category = "电子料", unit = "个", pricePerUnit = 500.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "AMOLED 6.7寸 | 120Hz刷新 | 2400×1080"),
    MaterialItem(id = 6006, name = "电路板", category = "电子料", unit = "片", pricePerUnit = 80.0, contactType = ContactType.NON_CONTACT, colorId = 4, description = "FR4 6层PCB | 铜厚1oz | 绿油阻焊"),
    MaterialItem(id = 6007, name = "塑料外壳", category = "电子料", unit = "个", pricePerUnit = 15.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "ABS+PC合金 注塑 | 壁厚2mm | 阻燃V0"),
    MaterialItem(id = 6008, name = "摄像头模组", category = "电子料", unit = "个", pricePerUnit = 120.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "50MP OIS光学防抖 | 1/1.56寸传感器"),
    MaterialItem(id = 6009, name = "传感器", category = "电子料", unit = "个", pricePerUnit = 30.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "MEMS加速度+陀螺仪 | 6轴姿态检测"),
    MaterialItem(id = 6010, name = "连接线", category = "电子料", unit = "条", pricePerUnit = 8.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "FPC排线 30pin | 间距0.5mm | 长度100mm")
)

// ============================================
// 工业原材料数据（ID: 7000-7999）
// ============================================
val industrialMaterials = listOf(
    MaterialItem(id = 7001, name = "钢材", category = "工业料", unit = "吨", pricePerUnit = 4500.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "Q235低碳钢 | 屈服235MPa | 通用结构"),
    MaterialItem(id = 7002, name = "铝材", category = "工业料", unit = "吨", pricePerUnit = 18000.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "6061铝合金 | 抗拉290MPa | 轻质结构"),
    MaterialItem(id = 7003, name = "橡胶", category = "工业料", unit = "千克", pricePerUnit = 30.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "天然橡胶 NR | 拉伸强度20MPa | 弹性件"),
    MaterialItem(id = 7004, name = "玻璃纤维", category = "工业料", unit = "千克", pricePerUnit = 25.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "E玻璃纤维 无碱 | 拉伸强度3.4GPa"),
    MaterialItem(id = 7005, name = "碳纤维", category = "工业料", unit = "千克", pricePerUnit = 200.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "T700级 12K丝束 | 拉伸4900MPa | 轻量"),
    MaterialItem(id = 7006, name = "化工原料", category = "工业料", unit = "桶", pricePerUnit = 500.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "工业溶剂 200L桶 | 纯度≥99%"),
    MaterialItem(id = 7007, name = "塑料颗粒", category = "工业料", unit = "吨", pricePerUnit = 8000.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "ABS注塑级 | 冲击强度30kJ/m² | 阻燃V0"),
    MaterialItem(id = 7008, name = "铜线", category = "工业料", unit = "吨", pricePerUnit = 60000.0, contactType = ContactType.NON_CONTACT, colorId = 3, description = "无氧铜 纯度99.95% | 导电率102%IACS"),
    MaterialItem(id = 7009, name = "皮革", category = "工业料", unit = "平方米", pricePerUnit = 100.0, contactType = ContactType.NON_CONTACT, colorId = 3, description = "头层牛皮 1.2mm厚 | 天然纹理"),
    MaterialItem(id = 7010, name = "纺织纤维", category = "工业料", unit = "千克", pricePerUnit = 40.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "涤纶短纤 | 断裂强度5cN/dtex | 纱线用")
)

// ============================================
// 房产成品数据（ID: 8000-8999）
// ============================================
val housingProducts = listOf(
    ProductItem(id = 8001, name = "单身公寓", category = "房产", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "30-50㎡ 小户型"),
    ProductItem(id = 8002, name = "两室一厅", category = "房产", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "80-100㎡ 标准住宅"),
    ProductItem(id = 8003, name = "三室两厅", category = "房产", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "120-150㎡ 改善型"),
    ProductItem(id = 8004, name = "联排别墅", category = "房产", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "200-300㎡ 联排"),
    ProductItem(id = 8005, name = "独栋别墅", category = "房产", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "400㎡+ 豪华独栋"),
    ProductItem(id = 8006, name = "商铺", category = "房产", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.HORIZONTAL, description = "临街商铺 50-100㎡"),
    ProductItem(id = 8007, name = "写字楼", category = "房产", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "商业办公 100-500㎡"),
    ProductItem(id = 8008, name = "厂房", category = "房产", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "工业厂房 500㎡+")
)

// ============================================
// 数码成品数据（ID: 9000-9999）
// ============================================
val digitalProducts = listOf(
    ProductItem(id = 9001, name = "智能手机", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "6.7寸 OLED 120Hz | 12GB RAM + 256GB | 5000mAh电池 | 骁龙8代处理器",
        physicalQuant = PhysicalQuantification(
            fragmentationRate = 30.0, responseLatencyMs = 100.0, notificationInterference = 20.0
        )),
    ProductItem(id = 9002, name = "笔记本电脑", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "14寸 2.8K OLED | i7-13700H | 16GB DDR5 | 512GB NVMe SSD | 1.3kg轻薄",
        physicalQuant = PhysicalQuantification(
            radiationWKg = 0.1, thermalConductivity = 200.0, materialComposition = "铝合金"
        )),
    ProductItem(id = 9003, name = "平板电脑", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "11寸 LCD 60Hz | 8GB+128GB | 8000mAh | 办公娱乐入门"),
    ProductItem(id = 9004, name = "蓝牙耳机", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "真无线 ANC主动降噪 | 蓝牙5.3 | IPX5防水 | 续航6h+24h充电仓"),
    ProductItem(id = 9005, name = "智能手表", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "1.5寸 AMOLED | GPS | 血氧/心率监测 | 50米防水 | 续航14天"),
    ProductItem(id = 9006, name = "台式电脑", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "DIY组装 | i5-13400F + RTX 4060 | 32GB DDR5 | 1TB NVMe | 750W金牌"),
    ProductItem(id = 9007, name = "显示器", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "27寸 4K 144Hz IPS | HDR600 | 1ms响应 | FreeSync | ΔE<2色准"),
    ProductItem(id = 9008, name = "机械键盘", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "87键 Cherry青轴 | PBT双色键帽 | 全键无冲 | USB-C | RGB背光"),
    // 2025新增 - 核心DIY配件
    ProductItem(id = 9021, name = "独立显卡(RTX4060)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "RTX 4060 8GB GDDR6 | 3072 CUDA核心 | 115W功耗 | DLSS3 | PCIe4.0"),
    ProductItem(id = 9022, name = "独立显卡(RTX4070)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "RTX 4070 12GB GDDR6X | 5888 CUDA核心 | 200W | DLSS3.5 | 4K游戏入门"),
    ProductItem(id = 9023, name = "CPU处理器(i5-13400F)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "10核16线程(6P+4E) | 睿频4.6GHz | 65W | LGA1700 | 无核显"),
    ProductItem(id = 9024, name = "CPU处理器(i7-13700K)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "16核24线程(8P+8E) | 睿频5.4GHz | 125W | 可超频 | LGA1700"),
    ProductItem(id = 9025, name = "固态硬盘(1TB NVMe)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "1TB NVMe PCIe4.0 | 读取7000MB/s | TLC颗粒 | 600TBW耐久"),
    ProductItem(id = 9026, name = "固态硬盘(2TB NVMe)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "2TB NVMe PCIe4.0 | 读取7400MB/s | TLC颗粒 | 1200TBW耐久"),
    ProductItem(id = 9027, name = "机械硬盘(4TB)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "4TB 7200RPM | 256MB缓存 | CMR垂直记录 | SATA3(6Gb/s)"),
    ProductItem(id = 9028, name = "DDR5内存(32GB套装)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "32GB(16GB×2) DDR5-6000 | CL30低时序 | XMP3.0 | 铝制散热片"),
    ProductItem(id = 9029, name = "主板(Z790)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "Z790 ATX | LGA1700 | DDR5 | PCIe5.0 | WiFi6E | 4×M.2插槽"),
    ProductItem(id = 9030, name = "电源(850W金牌)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "850W 80Plus金牌 | 全模组 | 全日系电容 | 12VHPWR接口 | 10年保"),
    ProductItem(id = 9031, name = "CPU散热(240水冷)", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "240mm一体水冷 | 双120mm ARGB风扇 | 解热280W | 支持LGA1700/AM5")
)

// ============================================
// 汽车成品数据（ID: 10000-10999）
// ============================================
val carProducts = listOf(
    ProductItem(id = 10001, name = "电动轿车-经济型", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "续航600km 家用",
        physicalQuant = PhysicalQuantification(bodyStrengthMPa = 1500.0, energyPer100km = 12.0, energyUnit = "kWh", annualFaultRate = 0.5, safetyCoefficient = 90)),
    ProductItem(id = 10002, name = "电动轿车-高性能", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "续航700km 高性能",
        physicalQuant = PhysicalQuantification(bodyStrengthMPa = 1800.0, energyPer100km = 11.0, energyUnit = "kWh", annualFaultRate = 0.3, safetyCoefficient = 95)),
    ProductItem(id = 10003, name = "燃油轿车-经济型", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "1.5T 经济型",
        physicalQuant = PhysicalQuantification(bodyStrengthMPa = 1200.0, energyPer100km = 8.0, energyUnit = "L", annualFaultRate = 0.8, safetyCoefficient = 80)),
    ProductItem(id = 10004, name = "二手车-老旧", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "10年车龄 代步",
        physicalQuant = PhysicalQuantification(bodyStrengthMPa = 800.0, energyPer100km = 12.0, energyUnit = "L", annualFaultRate = 3.0, safetyCoefficient = 60)),
    ProductItem(id = 10005, name = "SUV", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "2.0T 城市越野"),
    ProductItem(id = 10006, name = "MPV", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "7座 家用商务"),
    ProductItem(id = 10007, name = "电动货车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "载重2吨 物流"),
    ProductItem(id = 10008, name = "电动自行车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.VERTICAL, description = "48V 代步",
        physicalQuant = PhysicalQuantification(energyPer100km = 1.2, energyUnit = "kWh", annualFaultRate = 2.0, safetyCoefficient = 50)),
    ProductItem(id = 10009, name = "摩托车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "250cc 街车")
)

// ============================================
// 🔬 物理量化样本产品 — 四维度全覆盖
// 不分国界、不贴标签，只看物理结构量化数据
// ============================================
val physicalQuantSampleProducts = listOf(
    // ═══ 维度一：吃进去 — 分子层面的物理差异 ═══
    ProductItem(id = 110001, name = "布洛芬", category = "药品", contactType = ContactType.EDIBLE, colorId = 1, shapeType = PixelShapeType.SQUARE,
        description = "非甾体抗炎药 200mg/片 | 解热镇痛",
        emoji = "💊",
        physicalQuant = PhysicalQuantification(
            molecularFormula = "C₁₃H₁₈O₂", activeIngredientRatio = 90.0, metabolicHalfLifeH = 2.0, giStimulationRate = 15.0
        )),
    ProductItem(id = 110002, name = "抗病毒中药饮料", category = "药品", contactType = ContactType.EDIBLE, colorId = 4, shapeType = PixelShapeType.SQUARE,
        description = "植物提取物复合配方 | 250ml罐装",
        emoji = "🧪",
        physicalQuant = PhysicalQuantification(
            molecularFormula = "植物提取物分子", activeIngredientRatio = 8.0, metabolicHalfLifeH = 1.5, giStimulationRate = 2.0, glycemicIndex = 15, emptyCalorieRatio = 5.0
        )),
    ProductItem(id = 110003, name = "薯片", category = "加工食品", contactType = ContactType.EDIBLE, colorId = 3, shapeType = PixelShapeType.SQUARE,
        description = "膨化油炸零食 | 75g袋装",
        emoji = "🍟",
        physicalQuant = PhysicalQuantification(
            glycemicIndex = 80, emptyCalorieRatio = 90.0, proteinRatio = 5.0
        )),
    ProductItem(id = 110004, name = "酱牛肉", category = "加工食品", contactType = ContactType.EDIBLE, colorId = 4, shapeType = PixelShapeType.SQUARE,
        description = "卤制熟食 | 200g真空装",
        emoji = "🥩",
        physicalQuant = PhysicalQuantification(
            glycemicIndex = 0, emptyCalorieRatio = 5.0, proteinRatio = 40.0
        )),

    // ═══ 维度二：接触皮肤 — 材料层面的物理差异 ═══
    ProductItem(id = 110005, name = "纯棉T恤", category = "上衣", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL,
        description = "100%棉 基础款 | 可机洗",
        emoji = "👕",
        physicalQuant = PhysicalQuantification(
            formaldehydeMgKg = 0.0, allergyRate = 0.0, phLevel = 5.5, materialComposition = "纤维素"
        )),
    ProductItem(id = 110006, name = "快时尚化纤T恤", category = "上衣", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL,
        description = "聚酯纤维+氨纶混纺 | 快消品牌",
        emoji = "👚",
        physicalQuant = PhysicalQuantification(
            formaldehydeMgKg = 20.0, allergyRate = 15.0, phLevel = 8.0, materialComposition = "聚酯纤维"
        )),
    ProductItem(id = 110007, name = "轻薄笔记本外壳", category = "数码", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL,
        description = "铝合金CNC一体成型 | 1.3kg",
        emoji = "💻",
        physicalQuant = PhysicalQuantification(
            radiationWKg = 0.1, thermalConductivity = 200.0, materialComposition = "铝合金"
        )),
    ProductItem(id = 110008, name = "游戏笔记本外壳", category = "数码", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL,
        description = "塑料+金属混合壳体 | 2.5kg",
        emoji = "🎮",
        physicalQuant = PhysicalQuantification(
            radiationWKg = 0.5, thermalConductivity = 0.2, materialComposition = "塑料"
        )),

    // ═══ 维度三：承载空间 — 结构层面的物理差异（汽车已在 carProducts 中体现） ═══

    // ═══ 维度四：抚平精神 — 信息层面的物理差异 ═══
    ProductItem(id = 110009, name = "安卓生态系统", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE,
        description = "开放生态 · 多品牌适配 · 高度可定制",
        emoji = "🤖",
        physicalQuant = PhysicalQuantification(
            fragmentationRate = 30.0, responseLatencyMs = 100.0, notificationInterference = 20.0
        )),
    ProductItem(id = 110010, name = "苹果生态系统", category = "数码", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE,
        description = "闭环生态 · 统一硬件 · 无缝协同",
        emoji = "🍎",
        physicalQuant = PhysicalQuantification(
            fragmentationRate = 0.0, responseLatencyMs = 50.0, notificationInterference = 5.0
        ))
)

// ============================================
// 成品-原材料组成关系示例
// ============================================
val sampleCompositions = listOf(
    // --- 房产组成 ---
    CompositionRelation(8001, 5001, 10.0, "吨"),    // 公寓：水泥10吨
    CompositionRelation(8001, 5002, 3.0, "吨"),     // 公寓：钢筋3吨
    CompositionRelation(8001, 5003, 15.0, "吨"),    // 公寓：沙子15吨
    CompositionRelation(8001, 5004, 80.0, "平方米"),// 公寓：瓷砖80㎡
    CompositionRelation(8001, 5005, 5.0, "立方米"), // 公寓：木材5m³
    CompositionRelation(8001, 5006, 10.0, "平方米"),// 公寓：玻璃10㎡
    CompositionRelation(8001, 5007, 3.0, "桶"),     // 公寓：油漆3桶
    CompositionRelation(8001, 5008, 200.0, "米"),   // 公寓：电线200米
    CompositionRelation(8001, 5009, 50.0, "米"),    // 公寓：水管50米
    CompositionRelation(8001, 5010, 5000.0, "块"),  // 公寓：砖块5000块

    // --- 手机组成 ---
    CompositionRelation(9001, 6001, 1.0, "片"),     // 手机：硅晶圆1片
    CompositionRelation(9001, 6004, 2.0, "个"),     // 手机：芯片2个
    CompositionRelation(9001, 6005, 1.0, "个"),     // 手机：显示屏1个
    CompositionRelation(9001, 6006, 1.0, "片"),     // 手机：电路板1片
    CompositionRelation(9001, 6007, 1.0, "个"),     // 手机：外壳1个
    CompositionRelation(9001, 6008, 3.0, "个"),     // 手机：摄像头3个
    CompositionRelation(9001, 6009, 5.0, "个"),     // 手机：传感器5个
    CompositionRelation(9001, 6010, 2.0, "条"),     // 手机：连接线2条
    CompositionRelation(9001, 6003, 1.0, "个"),     // 手机：电池1个

    // --- 汽车组成 ---
    CompositionRelation(10001, 7001, 1.2, "吨"),    // 电车：钢材1.2吨
    CompositionRelation(10001, 7002, 0.3, "吨"),    // 电车：铝材0.3吨
    CompositionRelation(10001, 7003, 50.0, "千克"), // 电车：橡胶50kg
    CompositionRelation(10001, 7005, 20.0, "千克"), // 电车：碳纤维20kg
    CompositionRelation(10001, 7006, 2.0, "桶"),    // 电车：化工原料2桶
    CompositionRelation(10001, 7007, 100.0, "千克"),// 电车：塑料颗粒100kg
    CompositionRelation(10001, 7008, 50.0, "千克"), // 电车：铜线50kg
    CompositionRelation(10001, 6003, 1.0, "个"),    // 电车：动力电池1个
    CompositionRelation(10001, 6004, 50.0, "个"),   // 电车：芯片50个
    CompositionRelation(10001, 7009, 5.0, "平方米") // 电车：皮革5㎡
)

// ============================================
// 虚拟原材料（用于无形服务、音像制品、农产品等）
// ============================================
val virtualMaterials = listOf(
    MaterialItem(id = 20001, name = "人工工时", category = "虚拟", unit = "小时", pricePerUnit = 50.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "标准人工服务工时费"),
    MaterialItem(id = 20002, name = "技术咨询费", category = "虚拟", unit = "次", pricePerUnit = 500.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "专业技术服务费用"),
    MaterialItem(id = 20003, name = "场地租金", category = "虚拟", unit = "小时", pricePerUnit = 100.0, contactType = ContactType.NON_CONTACT, colorId = 3, description = "场地使用费"),
    MaterialItem(id = 20004, name = "创意设计费", category = "虚拟", unit = "次", pricePerUnit = 1000.0, contactType = ContactType.NON_CONTACT, colorId = 4, description = "创意/设计方案费用"),
    MaterialItem(id = 20005, name = "内容载体", category = "虚拟", unit = "份", pricePerUnit = 5.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "数字内容载体成本"),
    MaterialItem(id = 20006, name = "录制成本", category = "虚拟", unit = "小时", pricePerUnit = 200.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "音频/视频录制成本"),
    MaterialItem(id = 20007, name = "物流配送费", category = "虚拟", unit = "次", pricePerUnit = 10.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "快递配送服务费"),
    MaterialItem(id = 20008, name = "包装加工费", category = "虚拟", unit = "次", pricePerUnit = 15.0, contactType = ContactType.NON_CONTACT, colorId = 3, description = "产品包装加工服务费"),
    MaterialItem(id = 20009, name = "种植养殖成本", category = "虚拟", unit = "月", pricePerUnit = 500.0, contactType = ContactType.NON_CONTACT, colorId = 4, description = "农产品种植养殖月度成本"),
    MaterialItem(id = 20010, name = "收藏溢价", category = "虚拟", unit = "项", pricePerUnit = 5000.0, contactType = ContactType.NON_CONTACT, colorId = 4, description = "古董/奢侈品收藏溢价"),
)

// ============================================
// 所有原材料汇总（方便全局查询）
// ============================================
val allMaterials: List<MaterialItem> by lazy {
    buildingMaterials + electronicMaterials + industrialMaterials + virtualMaterials + packagingMaterials
}

// ============================================
// 所有成品-材料组成关系汇总（方便全局查询）
// ============================================
val allCompositions: List<CompositionRelation> by lazy {
    sampleCompositions +
    allNewPetToolMedicalCompositions +
    allNewSportsToyArtCompositions +
    allNewAgriJewelServiceCompositions +
    allNewMusicAvCompositions
}

// ============================================
// 所有成品汇总（方便全局查询）+ 自动补全缺失的物理量化数据
// ============================================
val allProducts: List<ProductItem> by lazy {
    val raw = housingProducts + digitalProducts + carProducts +
        // 物理量化样本产品（四维度全覆盖）
        physicalQuantSampleProducts +
        // 2025-2026 全品类扩展数据（CategoryData文件自动接入）
        petSupplies + toolProducts + medicalProducts +
        sportsProducts + toyProducts + artProducts +
        agricultureProducts + jewelryProducts + serviceProducts +
        musicAvProducts +
        // CompleteData_Part2 扩展品
        extendedHousingProducts + extendedDigitalProducts + extendedCarProducts
    // 自动补全所有缺失 physicalQuant 的产品
    raw.map { product ->
        if (product.physicalQuant != null && product.physicalQuant.hasAnyData()) product
        else product.copy(physicalQuant = autoGeneratePhysicalQuant(product))
    }
}

// ============================================
// 自动生成 PhysicalQuantification — 基于品类和描述推断量化数据
// 不分国界不贴标签，只做客观物理量化
// ============================================
private fun autoGeneratePhysicalQuant(product: ProductItem): PhysicalQuantification {
    val desc = product.description.lowercase()
    val cat = product.category

    // ── 辅助：从描述中提取数字 ──
    fun extractNumber(vararg keywords: String): Double {
        for (kw in keywords) {
            val regex = Regex("""${Regex.escape(kw)}\s*[:：]?\s*([\d.]+)""")
            regex.find(desc)?.groupValues?.get(1)?.toDoubleOrNull()?.let { return it }
        }
        return 0.0
    }

    // ═══ 维度一：吃进去（食品/药品/保健品） ═══
    val isMedicine = cat == "药品" || cat == "药品医疗" || desc.contains("药") || desc.contains("mg") || desc.contains("片")
    val isHealth = cat == "保健品" || desc.contains("维生素") || desc.contains("保健")
    val isFood = cat in listOf("加工食品", "生鲜食材", "零食饮料", "调味佐料", "方便速食", "罐头腌制品",
        "蔬菜水果", "肉禽蛋奶", "水产海鲜", "五谷杂粮", "蛋奶豆", "肉禽水产", "蔬菜", "水果", "主食", "零食") ||
        product.contactType == ContactType.EDIBLE

    val gi = if (isFood || isMedicine) {
        when {
            desc.contains("糖") || desc.contains("甜") || desc.contains("薯片") || desc.contains("膨化") -> 75
            desc.contains("蛋白") || desc.contains("肉") || desc.contains("蛋") || desc.contains("奶") -> 5
            desc.contains("蔬菜") || desc.contains("水果") || desc.contains("纤维") -> 25
            else -> 50
        }
    } else 0

    val emptyCal = when {
        desc.contains("油炸") || desc.contains("膨化") || desc.contains("零食") -> 85.0
        desc.contains("加工") && isFood -> 60.0
        desc.contains("蛋白") && isFood -> 5.0
        isMedicine -> 0.0
        else -> if (isFood) 40.0 else 0.0
    }

    val protein = when {
        desc.contains("蛋白") || desc.contains("肉") || desc.contains("奶") || desc.contains("蛋") || desc.contains("豆") -> {
            val p = extractNumber("蛋白", "蛋白质")
            if (p > 0) p else 30.0
        }
        else -> if (isFood) 10.0 else 0.0
    }

    // ═══ 维度二：接触皮肤（衣物/面料/穿戴品） ═══
    val isSkinContact = product.contactType == ContactType.SKIN_CONTACT ||
        cat in listOf("上衣", "裤子", "外套", "裙子", "鞋履", "帽子", "袜子", "内衣", "服饰", "配件") ||
        desc.contains("面料") || desc.contains("棉") || desc.contains("纤维") || desc.contains("纺织") ||
        desc.contains("穿着") || desc.contains("穿戴")

    val formaldehyde = when {
        desc.contains("纯棉") || desc.contains("100%棉") || desc.contains("天然") || desc.contains("有机") -> 0.0
        desc.contains("化纤") || desc.contains("聚酯") || desc.contains("涤纶") -> 20.0
        desc.contains("混纺") -> 10.0
        desc.contains("牛仔") || desc.contains("denim") -> 12.0
        isSkinContact -> 8.0
        else -> 0.0
    }

    val allergy = when {
        desc.contains("化纤") || desc.contains("聚酯") || desc.contains("涤纶") -> 15.0
        desc.contains("羊毛") || desc.contains("毛") -> 20.0
        desc.contains("纯棉") || desc.contains("100%棉") -> 0.0
        isSkinContact -> 5.0
        else -> 0.0
    }

    val ph = when {
        desc.contains("纯棉") || desc.contains("天然") -> 5.5
        desc.contains("化纤") || desc.contains("聚酯") -> 8.0
        desc.contains("丝绸") || desc.contains("丝") -> 7.0
        isSkinContact -> 6.5
        else -> 0.0
    }

    // ═══ 维度三：承载空间（房产/汽车/大型工具） ═══
    val isVehicle = cat == "汽车" || desc.contains("车") || desc.contains("续航") || desc.contains("油耗")
    val isHouse = cat == "房产" || desc.contains("㎡") || desc.contains("平米")

    val strength = when {
        desc.contains("铝合金") || desc.contains("铝") -> 290.0
        desc.contains("钢") || desc.contains("铁") -> 400.0
        desc.contains("碳纤维") -> 4900.0
        desc.contains("塑料") || desc.contains("abs") -> 50.0
        desc.contains("木材") || desc.contains("木") || desc.contains("松") -> 70.0
        desc.contains("混凝土") || desc.contains("水泥") || isHouse -> 30.0
        isVehicle -> 1200.0
        else -> 0.0
    }

    val energy = if (isVehicle) {
        when {
            desc.contains("电动") || desc.contains("kwh") || desc.contains("电") -> {
                extractNumber("续航", "kwh", "能耗", "百公里")
                    .let { if (it > 0) it else 15.0 }
            }
            desc.contains("燃油") || desc.contains("l") || desc.contains("油耗") -> {
                extractNumber("油耗", "百公里", "l")
                    .let { if (it > 0) it else 10.0 }
            }
            else -> 0.0
        }
    } else 0.0

    val energyUnit = when {
        desc.contains("电动") || desc.contains("kwh") || desc.contains("电") -> "kWh"
        desc.contains("燃油") || desc.contains("l") || desc.contains("油耗") -> "L"
        else -> ""
    }

    val safety = when {
        desc.contains("电动") && isVehicle -> 90
        desc.contains("进口") && isVehicle -> 95
        desc.contains("燃油") && isVehicle -> 80
        desc.contains("二手") && isVehicle -> 60
        desc.contains("老旧") && isVehicle -> 50
        isVehicle -> 75
        else -> 0
    }

    val faultRate = when {
        desc.contains("进口") && isVehicle -> 0.3
        desc.contains("电动") && isVehicle -> 0.5
        desc.contains("燃油") && isVehicle -> 0.8
        desc.contains("二手") && isVehicle -> 3.0
        desc.contains("老旧") && isVehicle -> 5.0
        isVehicle -> 1.5
        else -> 0.0
    }

    // ═══ 维度四：抚平精神（数码/音乐/信息类） ═══
    val isDigital = cat == "数码" || desc.contains("hz") || desc.contains("bpm") || desc.contains("延迟") ||
        desc.contains("刷新") || desc.contains("处理器") || desc.contains("ram") || desc.contains("gb") ||
        desc.contains("ssd") || desc.contains("nvme") || desc.contains("显卡") || desc.contains("cpu")

    val isMusic = cat == "音乐影音" || desc.contains("音乐") || desc.contains("bpm") || desc.contains("db") ||
        desc.contains("乐器") || desc.contains("唱片") || desc.contains("专辑") || desc.contains("音频")

    val fragRate = when {
        desc.contains("安卓") || desc.contains("android") -> 30.0
        desc.contains("苹果") || desc.contains("ios") || desc.contains("闭环") -> 0.0
        desc.contains("开放") && isDigital -> 20.0
        isDigital -> 15.0
        else -> 0.0
    }

    val latency = when {
        desc.contains("1ms") || desc.contains("电竞") || desc.contains("144hz") || desc.contains("240hz") -> 1.0
        desc.contains("游戏") && isDigital -> 10.0
        desc.contains("苹果") || desc.contains("ios") -> 50.0
        desc.contains("安卓") -> 100.0
        isDigital -> 80.0
        else -> 0.0
    }

    val notif = when {
        desc.contains("苹果") || desc.contains("闭环") -> 5.0
        desc.contains("安卓") || desc.contains("开放") -> 20.0
        isDigital -> 15.0
        else -> 0.0
    }

    val bpm = when {
        desc.contains("流行") || desc.contains("pop") || desc.contains("快节奏") -> 128
        desc.contains("古典") || desc.contains("classical") || desc.contains("安静") -> 72
        desc.contains("爵士") || desc.contains("jazz") -> 90
        desc.contains("电子") || desc.contains("edm") -> 140
        desc.contains("摇滚") || desc.contains("rock") -> 120
        desc.contains("民谣") || desc.contains("folk") -> 80
        isMusic -> 100
        else -> 0
    }

    val dynRange = when {
        desc.contains("古典") || desc.contains("交响") -> 96.0
        desc.contains("hifi") || desc.contains("高保真") -> 90.0
        desc.contains("唱片") || desc.contains("黑胶") -> 80.0
        isMusic -> 85.0
        else -> 0.0
    }

    return PhysicalQuantification(
        // 维度一：吃进去
        activeIngredientRatio = if (isMedicine) 90.0 else if (isFood) 50.0 else 0.0,
        metabolicHalfLifeH = if (isMedicine) 2.0 else if (isFood) 4.0 else 0.0,
        giStimulationRate = if (isMedicine) 15.0 else if (isFood && emptyCal > 50) 25.0 else 0.0,
        glycemicIndex = gi,
        emptyCalorieRatio = emptyCal,
        proteinRatio = protein,
        // 维度二：接触皮肤
        formaldehydeMgKg = formaldehyde,
        allergyRate = allergy,
        phLevel = ph,
        radiationWKg = if (desc.contains("笔记本") || desc.contains("电脑") || desc.contains("手机")) 0.3 else 0.0,
        thermalConductivity = when {
            desc.contains("铝合金") || desc.contains("金属") -> 200.0
            desc.contains("塑料") -> 0.2
            desc.contains("玻璃") -> 1.0
            else -> 0.0
        },
        materialComposition = when {
            desc.contains("纯棉") || desc.contains("100%棉") -> "纤维素"
            desc.contains("化纤") || desc.contains("聚酯") || desc.contains("涤纶") -> "聚酯纤维"
            desc.contains("羊毛") || desc.contains("毛") -> "角蛋白"
            desc.contains("丝绸") || desc.contains("丝") -> "丝蛋白"
            desc.contains("皮革") || desc.contains("皮") -> "胶原蛋白"
            desc.contains("铝合金") || desc.contains("铝") -> "铝合金"
            desc.contains("钢") -> "钢"
            desc.contains("塑料") || desc.contains("abs") -> "塑料"
            desc.contains("玻璃") -> "玻璃"
            else -> ""
        },
        // 维度三：承载空间
        bodyStrengthMPa = strength,
        energyPer100km = energy,
        energyUnit = energyUnit,
        annualFaultRate = faultRate,
        safetyCoefficient = safety,
        // 维度四：抚平精神
        fragmentationRate = fragRate,
        responseLatencyMs = latency,
        notificationInterference = notif,
        bpm = bpm,
        dynamicRangeDb = dynRange
    )
}

// ============================================
// 🧸 小家伙们的偷偷告白 — 产品名 → 软乎乎的悄悄话
// 不分国界不贴标签，只是每个小家伙想对你说的
// ============================================
val productWhisperMap: Map<String, String> = mapOf(
    "布洛芬" to "我会帮你挡住所有的疼，你好好休息就好",
    "抗病毒中药饮料" to "我没有那么大本事，但我会陪着你",
    "薯片" to "不开心的话，吃我一口，我给你带点开心",
    "酱牛肉" to "我给你满满的力气，你去闯就好",
    "纯棉T恤" to "我会暖着你，不管外面有多冷",
    "快时尚化纤T恤" to "我有点小瑕疵……但我也会努力暖着你的",
    "轻薄笔记本外壳" to "我会安安静静的，不吵你，不烫你",
    "游戏笔记本外壳" to "我有点热……但我会陪你打完这一局",
    "电动轿车(国产)" to "不管你想去哪，我都载着你，不会累",
    "电动轿车(进口)" to "你想去的地方，我都会稳稳地带你去",
    "燃油轿车(合资)" to "我可能慢一点，但我也会陪着你走",
    "二手车(老旧)" to "我虽然老了点，但我也会努力载着你",
    "安卓生态" to "我偶尔会卡，但我在努力变好，谢谢你等我",
    "苹果生态" to "累了的话，我帮你抚平那些小褶皱",
    "国际流行音乐" to "不开心的时候，我会蹦蹦跳跳逗你笑",
    "古典音乐" to "我安安静静陪着你，你不用说话",
    "黑胶唱片" to "我转得很慢，就像时间突然变温柔了",
    "CD专辑" to "每一首歌，都是偷偷为你选的",
    "板蓝根颗粒" to "我没有布洛芬那么厉害，但我会泡一杯暖暖的给你",
    "维生素C片" to "每天一颗，替你的身体说谢谢你"
)

// ============================================
// 品类分段信息（ID区间查询）
// ============================================
data class CategoryRange(
    val startId: Int,
    val endId: Int,
    val name: String,
    val type: String  // "material" 或 "product"
)

val categoryRanges = listOf(
    CategoryRange(1, 999, "基础食材", "material"),
    CategoryRange(1000, 1999, "服饰单品", "material"),
    CategoryRange(2000, 2999, "日用品", "material"),
    CategoryRange(3000, 3999, "菜谱", "product"),
    CategoryRange(4000, 4999, "穿搭套装", "product"),
    CategoryRange(5000, 5999, "建筑建材", "material"),
    CategoryRange(6000, 6999, "电子材料", "material"),
    CategoryRange(7000, 7999, "工业材料", "material"),
    CategoryRange(8000, 8999, "房产", "product"),
    CategoryRange(9000, 9999, "数码产品", "product"),
    CategoryRange(10000, 10999, "交通工具", "product"),
    CategoryRange(11000, 11999, "宠物用品", "product"),
    CategoryRange(12000, 12999, "五金工具", "product"),
    CategoryRange(13000, 13999, "医疗健康", "product"),
    CategoryRange(14000, 14999, "体育健身", "product"),
    CategoryRange(15000, 15999, "玩具桌游", "product"),
    CategoryRange(16000, 16999, "美术文创", "product"),
    CategoryRange(17000, 17999, "农林农具", "product"),
    CategoryRange(18000, 18999, "珠宝古董", "product"),
    CategoryRange(19000, 19999, "无形服务", "product"),
    CategoryRange(20000, 20999, "虚拟原材料", "material"),
    CategoryRange(30000, 34999, "音乐影音", "product"),
    CategoryRange(35000, 35999, "包装物料", "material")
)

// ============================================
// 获取品类名称的工具函数
// ============================================
fun getCategoryNameById(id: Int): String {
    return categoryRanges.firstOrNull { id in it.startId..it.endId }?.name ?: "未知"
}

fun isMaterialId(id: Int): Boolean {
    return categoryRanges.firstOrNull { id in it.startId..it.endId }?.type == "material"
}

fun isProductId(id: Int): Boolean {
    return categoryRanges.firstOrNull { id in it.startId..it.endId }?.type == "product"
}

// ============================================
// 🧠 清醒提示仓库 — 品类 → cognitiveTip 映射
// 所有提示仅客观陈述数据，无主观评价、无品牌/国籍偏见
// ============================================
data class ProductCognitiveTip(
    val category: String,
    val tips: List<String>,
    val relatedIdioms: List<String> = emptyList()  // 关联的成语ID列表
)

object CognitiveTipRepository {

    /** 品类 → 清醒提示 映射表（按接触类型分组，覆盖全品类） */
    private val categoryTipMap: Map<String, List<String>> = mapOf(
        // ═══ EDIBLE 食用/口服类 ═══
        "生鲜食材" to listOf(
            "自己做饭：食材成本约为外卖的 1/3，用油用盐可以自己控制。",
            "清蒸/白灼烹饪温度不超过 100℃，食材营养保留率较高，煎炸>200℃会破坏大部分热敏维生素。",
            "绿叶蔬菜隔夜后亚硝酸盐含量随时间上升，冷藏可延缓但无法完全阻止。",
            "鸡蛋冷藏保存可延长保鲜期 3-4 周，室温下每过一天新鲜度降一个等级。"
        ),
        "加工食品" to listOf(
            "外卖的便利性是用更高的油盐用量和包装成本换来的，通常含盐量为家庭烹饪的 2-3 倍。",
            "一杯 500ml 奶茶的含糖量约为 13 块方糖（约 65g），超过每日建议添加糖摄入上限的 2.5 倍。",
            "预包装食品的成分表按含量降序排列——前 3 项成分决定了该产品 80% 以上的营养结构。"
        ),
        "饮料零食" to listOf(
            "一杯 500ml 奶茶的含糖量约为 13 块方糖（约 65g）。",
            "薯片类零食的脂肪含量通常≥30%，一包 75g 薯片的热量≈一碗米饭×3。",
            "零度饮料的甜味来源为人工甜味剂，虽无热量但可能影响肠道菌群和食欲调节。"
        ),
        "调料油脂" to listOf(
            "每餐吃到七分饱，胃部不需要承受过大的消化负担。",
            "精炼植物油在高温（>180℃）反复使用时会产生反式脂肪酸和极性化合物。",
            "不同油脂的烟点差异很大：初榨橄榄油≈190℃，精炼花生油≈230℃，高温烹饪应选高烟点油。"
        ),
        "菜谱" to listOf(
            "自己做饭的食材成本约为外卖的 1/3，烹饪时间和油盐控制权都在自己手上。",
            "同一种食材，蒸/煮/炖/炒/炸的热量差异可超过 300%，烹饪方式本身决定了营养保留率。"
        ),
        "药品" to listOf(
            "皮肤护理的基础是清洁、保湿和防晒，在这三项之外的项目因人而异。",
            "OTC药品的有效成分在不同品牌之间通常差异小于 10%，品牌差异主要体现在辅料和包装上。",
            "非处方感冒药大多数缓解的是症状而非病因，身体的自愈修复通常在 5-7 天内完成。"
        ),
        "保健品" to listOf(
            "睡前摄入高脂食物会影响睡眠质量，消化不良是失眠的常见诱因。",
            "保健品的监管标准与药品不同：保健品不能声称治疗或预防疾病，只能声称辅助健康。",
            "维生素补充剂的吸收率通常低于食物来源：天然食物的维生素伴随辅因子，吸收效率更高。"
        ),

        // ═══ SKIN_CONTACT 肤接触类 ═══
        "上衣" to listOf(
            "一件基础款衬衫的穿着次数通常能超过 50 次，款式越简单越不容易过时。",
            "单次穿着成本 = 价格 ÷ 预计穿着次数。一件300元的衬衫穿30次 = 10元/次，比30元的快时尚穿3次更划算。"
        ),
        "外套" to listOf(
            "快时尚品牌的设计周期约为 2 周，衣物的平均穿着次数在下降。",
            "一件好外套的使用寿命可以超过 5 年，按年均穿着 60 天算，单天成本远低于每年更换的快消品。"
        ),
        "裤子" to listOf(
            "快时尚品牌的设计周期约为 2 周，衣物的平均穿着次数在下降。",
            "牛仔裤生产一条需消耗约 3800 升水，其环境成本远超售价所反映的。"
        ),
        "鞋履" to listOf(
            "穿高跟鞋时足底压力分布会发生变化，长期穿着可能影响步态和足弓结构。",
            "运动鞋的中底缓震材料约在 500-800 公里后开始衰减，超过这个里程即使鞋面完好也应更换。"
        ),
        "帽子" to listOf(
            "帽子的防晒效果取决于材质和密度，不代表品牌溢价。",
            "UPF50+ 的帽子可阻挡 98% 的紫外线，效果远优于普通棉质帽（UPF≈5-10）。"
        ),
        "袜子" to listOf(
            "棉含量 70% 以上的袜子透气性和吸湿性较优，但耐磨性略低于混纺。"
        ),
        "穿搭" to listOf(
            "穿搭的组合数量 = 上衣数 × 下装数 × 外套数。增加 1 件基础款比增加 1 件限定款能创造更多搭配。"
        ),
        "体育健身" to listOf(
            "运动损伤中约有 70% 源于不正确的姿势和过度的训练量。",
            "健身器材的购买成本 ÷ 实际使用次数 = 单次成本。大多数家庭健身器材在使用 30 次后闲置。",
            "蛋白质摄入的窗口期并非只有运动后 30 分钟——24 小时内的总摄入量比摄入时机更重要。"
        ),
        "宠物用品" to listOf(
            "宠物食品的蛋白质含量直接影响宠物健康状况，建议关注成分表前 3 项。",
            "宠物玩具的使用寿命因材质和宠物体型差异很大，橡胶类耐咬玩具的寿命通常是毛绒类的 5-10 倍。"
        ),

        // ═══ NON_CONTACT — 空间载体/建筑 ═══
        "房产" to listOf(
            "朝南采光充足的户型在冬季可减少约 30% 的取暖能耗，这是建筑设计本身的被动式节能。",
            "地下室通风和采光条件有限，长期居住需要关注空气质量和湿度。",
            "住宅的居住成本 = 房价 + 装修 + 物业 + 能耗 + 交通时间成本，房价只占其中的 60-70%。",
            "小户型的单位面积单价通常比大户型高 15-30%，但总价低、物业费和取暖费也更低。"
        ),
        "家居用品" to listOf(
            "物品数量减少后，清洁和整理的时间成本也随之下降。",
            "家具的甲醛释放周期：板材家具约 3-15 年，实木家具几乎零释放。价格差异的很大一部分是时间和健康成本。",
            "家电的能效等级每高一级，年均电费可节省 15-25%，一级能效的溢价通常在 3-5 年内通过电费收回。"
        ),

        // ═══ NON_CONTACT — 数码/电子 ═══
        "数码" to listOf(
            "第三方配件的价格通常为原装的 20-30%，性能差异因产品类型而异。",
            "电子产品的摩尔定律：同性能产品每年降价约 15-20%。如果不是急需，等待 6-12 个月通常更划算。",
            "游戏本 vs 商务本：同样价格下，游戏本的性能通常高 40-60%，但续航短 60%、重量高 40%。选择取决于使用场景。",
            "256GB vs 512GB vs 1TB：拍摄4K视频每小时约需30GB，普通办公文档几乎不占空间。按实际需求选择容量。"
        ),
        "数码产品" to listOf(
            "第三方配件的价格通常为原装的 20-30%，性能差异因产品类型而异。",
            "电子产品的摩尔定律：同性能产品每年降价约 15-20%。等待 6 个月通常能节省 10-15%。"
        ),

        // ═══ NON_CONTACT — 交通工具 ═══
        "汽车" to listOf(
            "第三方配件的价格通常为原装的 20-30%，性能差异因产品类型而异。",
            "汽车的持有成本 = 车价 + 购置税 + 保险 + 保养 + 油费/电费 + 停车费 + 折旧。年均总成本约为车价的 15-20%。",
            "电动车 vs 燃油车：每公里电费约 0.1 元，油费约 0.6 元。但电池更换成本（5-8 年后）约为车价的 30-50%。"
        ),

        // ═══ NON_CONTACT — 工具/五金 ═══
        "五金工具" to listOf(
            "通用工具的使用频率远高于专业工具，大多数家庭只需要一套基础工具箱。",
            "冲击钻 vs 电锤：砖墙用冲击钻即可，混凝土墙必须用电锤。选错工具不仅效率低，还可能损坏墙体。"
        ),
        "农林农具" to listOf(
            "土壤有机质每提升 1%，每亩蓄水能力增加约 20 吨。",
            "化肥的边际收益递减：第一袋化肥的增产效果可能是第十袋的 5-10 倍，过量施肥不仅浪费还污染地下水。"
        ),

        // ═══ NON_CONTACT — 医疗健康 ═══
        "医疗健康" to listOf(
            "皮肤护理的基础是清洁、保湿和防晒，在这三项之外的项目因人而异。",
            "血压计、血糖仪等家用医疗器械的关键指标是测量精度（偏差%），而非附加功能的数量。"
        ),

        // ═══ NON_CONTACT — 精神文化/艺术 ═══
        "音乐影音" to listOf(
            "流媒体订阅的年度成本可能超过 12 张实体专辑的价格。",
            "乐器入门：2000元和20000元的入门吉他，对于初学者前 6 个月的学习体验差异远小于价格的 10 倍差距。",
            "HiFi耳机的音质提升呈对数曲线：从50元到500元提升巨大，从5000元到50000元提升微乎其微。"
        ),
        "美术文创" to listOf(
            "画材的成本与品质并非线性关系，中端产品通常满足大多数需求。",
            "彩色铅笔：12色和120色的价格差异约 10 倍，但日常绘画中实际使用的颜色通常不超过 24 种。",
            "丙烯颜料 vs 油画颜料：丙烯干燥快、可水洗、无气味，更适合初学者和室内作画。"
        ),
        "玩具桌游" to listOf(
            "开放式玩具（积木、沙盘）的重复使用率远高于电子玩具，使用周期通常为 3-5 年 vs 6-12 个月。",
            "桌游的重玩价值取决于策略深度和变化性：合作类桌游重玩率约 60%，纯运气类重玩率约 20%。"
        ),
        "珠宝古董" to listOf(
            "奢侈品的品牌溢价通常占售价的 60-80%，功能性差异远小于价格差异。",
            "珠宝回收价通常为零售价的 30-50%，保值性因品类而异：金条>钻石裸石>品牌钻戒。",
            "18K金 vs 铂金：18K金硬度更高、不易变形但可能过敏；铂金更纯净但更软、更容易刮花。"
        ),

        // ═══ NON_CONTACT — 服务/无形 ═══
        "无形服务" to listOf(
            "服务消费的体验价值随时间衰减快于实物消费，但记忆留存更久。",
            "教育服务的选择优先级：师资质量 > 课程体系 > 硬件环境 > 品牌名气。硬件投入与学习效果的相关性通常是最低的。"
        ),

        // ═══ NON_CONTACT — 包装/物流 ═══
        "包装物料" to listOf(
            "包装成本通常占日用品零售价的 5-15%，可重复使用的包装长期更经济。",
            "快递纸箱的回收率约 90%，但塑料包装袋的回收率不足 10%。包装材质直接决定了其最终去向。"
        ),

        // ═══ NON_CONTACT — 户外 ═══
        "露营户外" to listOf(
            "户外装备的租赁成本约为购买价的 10-20%，低频使用者可考虑租赁。",
            "帐篷的水压指标（mm）：2000mm 可防中雨，5000mm 可防暴雨。普通露营 2000-3000mm 足够，高山环境才需要 5000mm+。"
        ),

        // ═══ NON_CONTACT — 婴童 ═══
        "婴童用品" to listOf(
            "婴童用品的使用周期短（通常 3-12 个月），二手转让可回收约 50% 的成本。",
            "婴儿推车：轻便型（5-8kg）和功能型（10-15kg）的选择取决于日常使用场景（公共交通 vs 自驾），而非价格。"
        ),

        // ═══ NON_CONTACT — 办公 ═══
        "办公文具" to listOf(
            "数字备份的存储成本远低于纸质存档的物理空间成本。",
            "办公椅的人体工学设计直接影响长期使用者的脊柱健康——办公椅的价格差异中，腰部支撑结构和可调节性是核心变量。"
        ),

        // ═══ 建材类 ═══
        "建材" to listOf(
            "装修预算中，隐蔽工程（水电、防水）占总预算的 10-15% 但决定了住进去之后 90% 的维修问题。",
            "瓷砖铺贴：大尺寸瓷砖（>600mm）铺贴人工费比小尺寸高约 30%，且需要更平整的墙面基础。"
        ),

        // ═══ 电子料/工业料（原材料） ═══
        "电子料" to listOf(
            "芯片的制造成本中，研发（设计+掩模）占 50% 以上，原材料（硅）占比不到 5%。价格主要体现的是知识密度而非材料成本。"
        ),
        "工业料" to listOf(
            "材料的选择标准：强度、重量、耐腐蚀性、成本，这四项的权重因使用场景而异——没有最好的材料，只有最合适的材料。"
        )
    )

    /** 品类 → 关联成语ID 映射（从 IdiomRepository 的 triggerConditions 反推） */
    private val categoryIdiomMap: Map<String, List<String>> = mapOf(
        "生鲜食材" to listOf("多多益善", "食不厌精"),
        "加工食品" to listOf("多多益善"),
        "上衣" to listOf("敝帚自珍", "衣锦还乡", "多多益善"),
        "外套" to listOf("敝帚自珍", "衣锦还乡"),
        "裤子" to listOf("敝帚自珍"),
        "鞋履" to listOf("敝帚自珍"),
        "珠宝古董" to listOf("珠光宝气", "买椟还珠"),
        "房产" to listOf("安居乐业", "多多益善"),
        "数码" to listOf("多多益善"),
        "汽车" to listOf("多多益善"),
        "药品" to listOf("讳疾忌医"),
        "美术文创" to listOf("画蛇添足"),
        "家居用品" to listOf("多多益善")
    )

    /** 根据品类名获取清醒提示列表 */
    fun getTipsForCategory(category: String): List<String>? {
        categoryTipMap[category]?.let { return it }
        for ((key, tips) in categoryTipMap) {
            if (category.contains(key, ignoreCase = true) || key.contains(category, ignoreCase = true)) {
                return tips
            }
        }
        return null
    }

    /** 根据品类名获取关联成语列表 */
    fun getIdiomsForCategory(category: String): List<String> {
        categoryIdiomMap[category]?.let { return it }
        for ((key, idioms) in categoryIdiomMap) {
            if (category.contains(key, ignoreCase = true) || key.contains(category, ignoreCase = true)) {
                return idioms
            }
        }
        return emptyList()
    }

    /** 通用清醒提示（兜底） */
    fun getGenericTip(category: String): String = when {
        category.contains("食品") || category.contains("食材") || category.contains("饮料") -> "食物品质与价格并非线性关系，营养密度和加工深度是更重要的指标。"
        category.contains("衣") || category.contains("鞋") || category.contains("袜") -> "单次穿着成本 = 价格 ÷ 穿着次数，而非价格的绝对值。"
        category.contains("珠宝") || category.contains("奢侈") || category.contains("古董") -> "奢侈品的品牌溢价通常占售价的 60-80%，功能性差异远小于价格差异。"
        category.contains("数码") || category.contains("电子") -> "电子产品的摩尔定律：同样性能的产品，每年降价约 15-20%。"
        category.contains("汽车") || category.contains("交通") -> "持有成本 = 购置 + 保险 + 保养 + 能源 + 停车 + 折旧，年均约车价的 15-20%。"
        category.contains("建材") || category.contains("房产") || category.contains("建筑") -> "建筑的全生命周期成本中，建设成本约占 25%，运营维护成本约占 75%。"
        category.contains("玩具") || category.contains("游戏") || category.contains("桌游") -> "开放式玩具的使用周期是电子玩具的 3-5 倍，但两者刺激的是不同的认知能力。"
        category.contains("音乐") || category.contains("乐器") || category.contains("影音") -> "听觉体验的提升呈对数曲线：入门和高端之间的体验差距，远小于价格差距。"
        else -> "任何商品的真实成本 = 购买价格 + 维护成本 + 时间成本 + 机会成本。"
    }

    // ═══ 产品品类 → BehaviorKey 映射（消费场景绑定） ═══
    private val categoryBehaviorMap: Map<String, String> = mapOf(
        // 奢侈品/面子消费 → luxury_status_consume
        "珠宝古董" to "luxury_status_consume",
        "上衣" to "luxury_status_consume",
        "外套" to "luxury_status_consume",
        "鞋履" to "luxury_status_consume",
        // 应酬聚餐 → social_party_consume
        "加工食品" to "social_party_consume",
        "饮料零食" to "social_party_consume",
        // 消费品幸福局限 → commodity_happiness
        "数码" to "commodity_happiness",
        "数码产品" to "commodity_happiness",
        "家居用品" to "commodity_happiness",
        "玩具桌游" to "commodity_happiness",
        // 音乐 → 精神消费（暂用通用）
        "音乐影音" to "commodity_happiness"
    )

    /** 根据品类名获取关联的 BehaviorKey */
    fun getBehaviorKeyForCategory(category: String): String? {
        categoryBehaviorMap[category]?.let { return it }
        for ((key, bk) in categoryBehaviorMap) {
            if (category.contains(key, ignoreCase = true) || key.contains(category, ignoreCase = true)) {
                return bk
            }
        }
        return null
    }

    /** 根据 BehaviorKey 从 BehaviorBuildingMapping 获取 cognitiveTip */
    fun getCognitiveTipByBehaviorKey(behaviorKey: String): String? {
        return com.example.townapp.business.BehaviorBuildingMapping.getCognitiveTip(behaviorKey)
    }
}

