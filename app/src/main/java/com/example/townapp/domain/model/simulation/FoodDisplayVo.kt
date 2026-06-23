package com.example.townapp.domain.model.simulation

import com.example.townapp.data.database.entity.FoodNutritionEntity
import com.example.townapp.data.database.entity.FoodRiskEntity

/**
 * 食材营养风险展示模型（domain 层 VO）
 * 整合 Room 两张表数据：食材基础信息、营养数值、健康风险描述
 * 遵循实事求是：如实呈现客观数据，不做主观评判
 */
data class FoodDisplayVo(
    // 基础识别
    val foodId: Int,
    val foodName: String,
    val category: String,

    // 营养数据（每100g基准）
    val protein: Double,
    val fat: Double,
    val carbohydrate: Double,
    val fiber: Double,
    val calories: Double,
    val sugar: Double,
    val salt: Double,
    val cholesterol: Double,
    val calcium: Double,
    val iron: Double,
    val vitaminC: Double,
    val nutritionScore: Int,
    val typicalServingG: Int,
    val note: String,

    // 风险数据
    val riskLevel: String,
    val riskLevelLabel: String,
    val riskColorHex: String,
    val heavyMetalRisk: Double,
    val pesticideRisk: Double,
    val sugarOilRisk: Double,
    val processingRisk: Double,
    val additiveRisk: Double,
    val totalRiskScore: Int,
    val heavyMetalDesc: String,
    val merchantClaim: String,
    val actualEffect: String,
    val caution: String,

    // 智商税标识
    val isIQTax: Boolean,
    val iqTaxLevel: Int,
    val iqTaxReason: String
) {
    companion object {
        fun fromEntity(
            nutrition: FoodNutritionEntity,
            risk: FoodRiskEntity?
        ): FoodDisplayVo {
            return FoodDisplayVo(
                // 基础识别
                foodId = nutrition.foodId,
                foodName = nutrition.foodName,
                category = nutrition.category,

                // 营养数据
                protein = nutrition.proteinPer100g,
                fat = nutrition.fatPer100g,
                carbohydrate = nutrition.carbohydratePer100g,
                fiber = nutrition.fiberPer100g,
                calories = nutrition.caloriesPer100g,
                sugar = nutrition.sugarPer100g,
                salt = nutrition.saltPer100g,
                cholesterol = nutrition.cholesterolMg,
                calcium = nutrition.calciumMg,
                iron = nutrition.ironMg,
                vitaminC = nutrition.vitaminCMg,
                nutritionScore = nutrition.nutritionScore,
                typicalServingG = nutrition.typicalServingG,
                note = nutrition.note,

                // 风险数据
                riskLevel = risk?.riskLevel ?: "LOW",
                riskLevelLabel = when (risk?.riskLevel) {
                    "LOW" -> "低风险"
                    "MEDIUM" -> "中风险"
                    "HIGH" -> "高风险"
                    "EXTREME" -> "极高风险"
                    else -> "未知"
                },
                riskColorHex = when (risk?.riskLevel) {
                    "LOW" -> "#4CAF50"
                    "MEDIUM" -> "#FF9800"
                    "HIGH" -> "#F44336"
                    "EXTREME" -> "#9C27B0"
                    else -> "#9E9E9E"
                },
                heavyMetalRisk = risk?.heavyMetalRisk ?: 0.0,
                pesticideRisk = risk?.pesticideRisk ?: 0.0,
                sugarOilRisk = risk?.sugarOilRisk ?: 0.0,
                processingRisk = risk?.processingRisk ?: 0.0,
                additiveRisk = risk?.additiveRisk ?: 0.0,
                totalRiskScore = risk?.totalRiskScore ?: 0,
                heavyMetalDesc = risk?.heavyMetalDesc ?: "",
                merchantClaim = risk?.merchantClaim ?: "",
                actualEffect = risk?.actualEffect ?: "",
                caution = risk?.caution ?: "",

                // 智商税
                isIQTax = nutrition.isIQTax,
                iqTaxLevel = nutrition.iqTaxLevel,
                iqTaxReason = nutrition.iqTaxReason
            )
        }
    }
}
