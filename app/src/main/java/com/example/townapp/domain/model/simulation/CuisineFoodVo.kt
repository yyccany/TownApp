package com.example.townapp.domain.model.simulation

import com.example.townapp.feature.food.CuisineFood

/**
 * 菜肴展示模型（domain 层 VO）
 * 封装 CuisineFood 原始数据，供 UI 层统一渲染
 * 不暴露底层原始实体，保证数据加工一致性
 */
data class CuisineFoodVo(
    val id: Int,
    val name: String,
    val category: String,
    val oil: Double,          // 油含量(g/份)
    val salt: Double,         // 盐含量(g/份)
    val sugar: Double,        // 糖含量(g/份)
    val cholesterol: Double,  // 胆固醇(mg/份)
    val nutritionScore: Double, // 营养评分(0-50)
    val healthRisk: String,   // 健康风险等级: LOW/MEDIUM/HIGH/EXTREME
    val recommendedFrequency: String, // 推荐食用频率
    val description: String,  // 食品描述
    val calories: Double,
    val protein: Double,
    val price: Double,
    val cuisine: String,
    val province: String,
    val healthRiskLabel: String, // 风险等级中文标签
    val riskColorHex: String    // 风险颜色（用于 UI 渲染）
) {
    companion object {
        fun fromEntity(food: CuisineFood): CuisineFoodVo {
            return CuisineFoodVo(
                id = food.id,
                name = food.name,
                category = food.category,
                oil = food.oil,
                salt = food.salt,
                sugar = food.sugar,
                cholesterol = food.cholesterol,
                nutritionScore = food.nutritionScore,
                healthRisk = food.healthRisk,
                recommendedFrequency = food.recommendedFrequency,
                description = food.description,
                calories = food.calories,
                protein = food.protein,
                price = food.price,
                cuisine = food.cuisine,
                province = food.province,
                healthRiskLabel = when (food.healthRisk) {
                    "LOW" -> "低风险"
                    "MEDIUM" -> "中风险"
                    "HIGH" -> "高风险"
                    "EXTREME" -> "极高风险"
                    else -> "未知"
                },
                riskColorHex = when (food.healthRisk) {
                    "LOW" -> "#4CAF50"
                    "MEDIUM" -> "#FF9800"
                    "HIGH" -> "#F44336"
                    "EXTREME" -> "#9C27B0"
                    else -> "#9E9E9E"
                }
            )
        }
    }
}
