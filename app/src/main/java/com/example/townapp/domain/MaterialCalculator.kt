package com.example.townapp.domain

import com.example.townapp.data.*

/**
 * 通用材料计算器
 *
 * 支持任意品类（建筑/电子/工业/房产/数码/汽车）的：
 * - 成品材料占比计算
 * - 总成本/到手价计算
 * - 原材料价格更新后自动重算
 *
 * 100% 复用已有的 MaterialItem + CompositionRelation + ProductItem 模型，
 * 与之前的 RecipeCalculator/OutfitCalculator 逻辑一致，只是品类范围更大。
 */
object MaterialCalculator {

    /** 计算结果的简化结构 */
    data class CompositionResult(
        val materialId: Int,
        val materialName: String,
        val category: String,
        val useAmount: Double,
        val unit: String,
        val unitPrice: Double,
        val subtotal: Double,
        val ratioPercent: Double  // 占成品总成本的百分比
    )

    data class ProductCostBreakdown(
        val productId: Int,
        val productName: String,
        val category: String,
        val totalMaterialCost: Double,     // 原材料总成本
        val compositions: List<CompositionResult>,
        val materialCount: Int             // 用了多少种不同材料
    )

    /** 获取某个成品所有原材料组成（按 (productId, materialId) 去重，合并重复用量） */
    fun getCompositionsForProduct(
        productId: Int,
        allCompositions: List<CompositionRelation>,
        allMaterials: List<MaterialItem>
    ): List<CompositionRelation> {
        val matched = allCompositions.filter { it.productId == productId }
        // 按 materialId 去重合并：相同材料多次出现时累加 useAmount
        val merged = linkedMapOf<Int, CompositionRelation>()
        for (rel in matched) {
            val existing = merged[rel.materialId]
            if (existing != null) {
                merged[rel.materialId] = existing.copy(useAmount = existing.useAmount + rel.useAmount)
            } else {
                merged[rel.materialId] = rel
            }
        }
        return merged.values.toList()
    }

    /** 计算成品的完整成本分解 */
    fun calculateProductCost(
        product: ProductItem,
        allCompositions: List<CompositionRelation>,
        allMaterials: List<MaterialItem>
    ): ProductCostBreakdown {
        val relations = getCompositionsForProduct(product.id, allCompositions, allMaterials)
        var totalCost = 0.0
        val results = mutableListOf<CompositionResult>()

        for (rel in relations) {
            val material = allMaterials.firstOrNull { it.id == rel.materialId }
            if (material != null) {
                val subtotal = material.pricePerUnit * rel.useAmount
                totalCost += subtotal
                results.add(
                    CompositionResult(
                        materialId = material.id,
                        materialName = material.name,
                        category = material.category,
                        useAmount = rel.useAmount,
                        unit = rel.unit,
                        unitPrice = material.pricePerUnit,
                        subtotal = subtotal,
                        ratioPercent = 0.0 // 先填0，下面统一算占比
                    )
                )
            }
        }

        // 计算占比
        val finalResults = if (totalCost > 0) {
            results.map { it.copy(ratioPercent = (it.subtotal / totalCost * 100).let { r -> Math.round(r * 10) / 10.0 }) }
        } else results

        return ProductCostBreakdown(
            productId = product.id,
            productName = product.name,
            category = product.category,
            totalMaterialCost = Math.round(totalCost * 100) / 100.0,
            compositions = finalResults,
            materialCount = results.size
        )
    }

    /** 根据ID查所有原材料（合并所有材料品类） */
    fun findMaterialById(id: Int, allMaterials: List<MaterialItem>): MaterialItem? {
        return allMaterials.firstOrNull { it.id == id }
    }

    /** 根据ID查所有成品 */
    fun findProductById(id: Int, allProducts: List<ProductItem>): ProductItem? {
        return allProducts.firstOrNull { it.id == id }
    }

    /** 重新计算价格（当原材料价格变动时调用） */
    fun recalculatePrice(
        productId: Int,
        allCompositions: List<CompositionRelation>,
        allMaterials: List<MaterialItem>,
        allProducts: List<ProductItem>
    ): ProductCostBreakdown? {
        val product = findProductById(productId, allProducts) ?: return null
        return calculateProductCost(product, allCompositions, allMaterials)
    }

    /** 获取某个品类的所有成品列表 */
    fun getProductsByCategory(category: String, allProducts: List<ProductItem>): List<ProductItem> {
        return allProducts.filter { it.category == category }
    }

    /** 获取某个品类的所有材料列表 */
    fun getMaterialsByCategory(category: String, allMaterials: List<MaterialItem>): List<MaterialItem> {
        return allMaterials.filter { it.category == category }
    }

    /**
     * 多维度代价计算（扩展 LifeCost 到所有品类）
     * 金钱成本 = 所有原材料价格总和
     * 生命时长 = 总金钱 / 时薪（由用户设定）
     */
    fun estimateLifeCost(
        productCost: Double,
        hourlyWage: Double = 50.0
    ): Triple<Double, Double, String> {
        val monetaryCost = productCost
        val lifeHours = if (hourlyWage > 0) productCost / hourlyWage else 0.0
        val description = when {
            productCost < 100 -> "小额消费"
            productCost < 1000 -> "日常消费"
            productCost < 10000 -> "大额消费"
            productCost < 100000 -> "重大支出"
            else -> "资产级投资"
        }
        return Triple(monetaryCost, lifeHours, description)
    }
}