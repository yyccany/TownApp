package com.example.townapp.domain

import androidx.compose.ui.graphics.Color

object ValueDensityCalculator {

    enum class Rating(val level: String, val color: Color, val description: String) {
        SSS("SSS", Color(0xFFFFD700), "价值极高，物超所值"),
        SS("SS", Color(0xFFFFA500), "非常优秀，值得推荐"),
        S("S", Color(0xFF4CAF50), "优秀，性价比高"),
        A("A", Color(0xFF2196F3), "良好，物有所值"),
        B("B", Color(0xFF9C27B0), "一般，可以接受"),
        C("C", Color(0xFFFF9800), "较低，谨慎购买"),
        D("D", Color(0xFFF44336), "较低，不推荐")
    }

    fun calculateFoodValueDensity(
        nutritionScore: Double,
        pricePer100g: Double,
        servingSize: Int,
        mealsPerWeek: Int
    ): Pair<Double, Rating> {
        val functionalValue = nutritionScore * 10 * mealsPerWeek * 52 / 100
        val costPerYear = (pricePer100g * servingSize / 100) * mealsPerWeek * 52
        
        val density = if (costPerYear > 0) functionalValue / costPerYear else 0.0
        return Pair(density, getRating(density))
    }

    fun calculateClothingValueDensity(
        wearCount: Int,
        price: Double,
        expectedLifespanMonths: Double
    ): Pair<Double, Rating> {
        val functionalValue = wearCount.toDouble()
        val monthsUsed = if (expectedLifespanMonths > 0) wearCount / (60 / 12).toDouble() else 0.0
        val costPerMonth = if (expectedLifespanMonths > 0) price / expectedLifespanMonths else 0.0
        
        val density = if (costPerMonth > 0) functionalValue / costPerMonth else 0.0
        return Pair(density, getRating(density))
    }

    fun calculateHealthCost(nutritionScore: Double, pricePer100g: Double, weight: Double): Double {
        val healthFactor = (100 - nutritionScore) * 0.01
        val unitCost = pricePer100g * weight / 100
        return healthFactor * unitCost
    }

    fun getRating(density: Double): Rating {
        return when {
            density >= 5.0 -> Rating.SSS
            density >= 3.0 -> Rating.SS
            density >= 2.0 -> Rating.S
            density >= 1.0 -> Rating.A
            density >= 0.5 -> Rating.B
            density >= 0.2 -> Rating.C
            else -> Rating.D
        }
    }

    fun getHealthCostRatio(nutritionScore: Double): Double {
        return (100 - nutritionScore) * 0.01
    }

    fun isJunkFood(nutritionScore: Double): Boolean {
        return nutritionScore < 50
    }

    /**
     * 通用价值密度计算方法
     * 用于从 FoodBusiness 和 ClothingBusiness 等调用
     */
    fun calculate(functionalValue: Double, totalCost: Double, totalMaintenance: Double): Double {
        val totalCostWithMaintenance = totalCost + totalMaintenance
        return if (totalCostWithMaintenance <= 0.0) {
            0.0
        } else {
            functionalValue / totalCostWithMaintenance
        }
    }
}
