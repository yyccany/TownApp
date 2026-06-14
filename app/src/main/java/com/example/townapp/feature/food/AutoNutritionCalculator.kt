package com.example.townapp.feature.food

import com.example.townapp.data.FoodItem

/**
 * 自动营养计算器
 * - normalizeTo100g: 已有数据的食材按100g标准化
 * - estimateNutrientsByCategory: 未填营养值时按类别自动推算
 */
object AutoNutritionCalculator {

    data class NormalizedNutrition(
        val proteinPer100g: Double,
        val fatPer100g: Double,
        val carbohydratePer100g: Double,
        val fiberPer100g: Double,
        val caloriesPer100g: Double
    )

    /** 已有营养数据的食材，直接标准化到100g */
    fun normalizeTo100g(item: FoodItem): NormalizedNutrition {
        val scale = 100.0 / item.typicalServing.coerceAtLeast(1)
        return NormalizedNutrition(
            proteinPer100g = item.proteinPer100g,
            fatPer100g = item.fatPer100g,
            carbohydratePer100g = item.carbohydratePer100g,
            fiberPer100g = item.fiberPer100g,
            caloriesPer100g = item.caloriesPer100g
        )
    }

    /** 按类别估算每100g营养值（用户留空时自动填充） */
    fun estimateNutrientsByCategory(category: String): NormalizedNutrition = when (category) {
        "蛋奶豆" -> NormalizedNutrition(
            proteinPer100g = 10.0,
            fatPer100g = 8.0,
            carbohydratePer100g = 3.0,
            fiberPer100g = 0.0,
            caloriesPer100g = 120.0
        )
        "肉禽水产" -> NormalizedNutrition(
            proteinPer100g = 18.0,
            fatPer100g = 10.0,
            carbohydratePer100g = 1.0,
            fiberPer100g = 0.0,
            caloriesPer100g = 170.0
        )
        "蔬菜" -> NormalizedNutrition(
            proteinPer100g = 2.0,
            fatPer100g = 0.3,
            carbohydratePer100g = 5.0,
            fiberPer100g = 2.0,
            caloriesPer100g = 30.0
        )
        "水果" -> NormalizedNutrition(
            proteinPer100g = 0.5,
            fatPer100g = 0.2,
            carbohydratePer100g = 12.0,
            fiberPer100g = 1.5,
            caloriesPer100g = 55.0
        )
        "主食" -> NormalizedNutrition(
            proteinPer100g = 7.0,
            fatPer100g = 1.0,
            carbohydratePer100g = 50.0,
            fiberPer100g = 1.5,
            caloriesPer100g = 240.0
        )
        "零食" -> NormalizedNutrition(
            proteinPer100g = 5.0,
            fatPer100g = 20.0,
            carbohydratePer100g = 55.0,
            fiberPer100g = 1.0,
            caloriesPer100g = 400.0
        )
        "调味料" -> NormalizedNutrition(
            proteinPer100g = 2.0,
            fatPer100g = 5.0,
            carbohydratePer100g = 15.0,
            fiberPer100g = 0.5,
            caloriesPer100g = 100.0
        )
        "饮品" -> NormalizedNutrition(
            proteinPer100g = 0.5,
            fatPer100g = 0.1,
            carbohydratePer100g = 10.0,
            fiberPer100g = 0.0,
            caloriesPer100g = 45.0
        )
        else -> NormalizedNutrition(
            proteinPer100g = 3.0,
            fatPer100g = 5.0,
            carbohydratePer100g = 20.0,
            fiberPer100g = 1.0,
            caloriesPer100g = 130.0
        )
    }
}