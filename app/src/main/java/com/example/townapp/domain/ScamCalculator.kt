package com.example.townapp.domain

import com.example.townapp.data.BarcodeProductItem

// ============================================
// 🎭 智商税计算器
// ============================================

object ScamCalculator {
    /**
     * 计算商品的智商税指数 0-100
     * 基于价格、营养成分、配料表、添加物等多维度综合评估
     */
    fun calculateScamScore(product: BarcodeProductItem): Int {
        var score = 0

        // 营养维度（使用 nutritionFacts 映射表）
        val carbohydrates = product.nutritionFacts["carbohydrates"] ?: 0.0
        val fat = product.nutritionFacts["fat"] ?: 0.0
        val protein = product.nutritionFacts["protein"] ?: 0.0

        // 高糖食品
        if (carbohydrates > 50) score += 15
        else if (carbohydrates > 30) score += 8

        // 高脂肪食品
        if (fat > 30) score += 15
        else if (fat > 20) score += 8

        // 低蛋白
        if (protein < 2) score += 10

        // 配料数量（作为加工度的粗略指标）
        val ingredientCount = product.ingredients.size
        score += (ingredientCount * 3).coerceAtMost(15)

        // 概念营销关键词（简化版，实际需要NLP）
        val marketingWords = listOf(
            "有机", "纯天然", "古法", "手工", "零添加", "无添加",
            "养生", "排毒", "清肠", "酵素", "葡萄籽", "胶原蛋白",
            "抗糖化", "抗氧化", "量子", "纳米", "负离子"
        )
        var marketingScore = 0
        for (word in marketingWords) {
            if (word in product.name || product.categories.any { word in it }) {
                marketingScore += 5
            }
        }
        score += marketingScore.coerceAtMost(20)

        return score.coerceIn(0, 100)
    }

    /**
     * 获取智商税等级文本
     */
    fun getScamLevel(score: Int): String {
        return when {
            score < 20 -> "实在好物"
            score < 40 -> "有点溢价"
            score < 60 -> "品牌税"
            score < 80 -> "智商税"
            else -> "重灾区"
        }
    }

    /**
     * 计算营销溢价比例
     */
    fun calculateMarketingPremium(product: BarcodeProductItem): Double {
        val scamScore = calculateScamScore(product)
        return scamScore / 100.0 * 0.8  // 最高80%的营销溢价
    }
}
