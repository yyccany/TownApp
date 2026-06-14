package com.example.townapp.feature.food

import com.example.townapp.data.FoodItem
import com.example.townapp.data.FoodMaterialInfo
import com.example.townapp.data.GlobalSettings
import com.example.townapp.data.foodMaterialLibrary
import kotlin.math.roundToInt

// ============================================
// 🥗 食物营养计算器
// ============================================

object FoodNutritionCalculator {
    /**
     * 计算营养密度 0-1（越高越有营养）
     */
    fun calculateNutrientDensity(food: FoodItem): Double {
        val proteinScore = food.proteinPer100g / 20.0  // 20g为优秀
        val fiberScore = food.fiberPer100g / 10.0      // 10g为优秀
        val fatPenalty = if (food.fatPer100g > 20) (food.fatPer100g - 20) / 20.0 else 0.0
        val carbPenalty = if (food.carbohydratePer100g > 50) (food.carbohydratePer100g - 50) / 50.0 else 0.0

        val density = (proteinScore * 0.4 + fiberScore * 0.3 - fatPenalty * 0.15 - carbPenalty * 0.15)
        return density.coerceIn(0.0, 1.0)
    }

    /**
     * 计算蛋白质成本效率（每元能买多少克蛋白质）
     */
    fun calculateProteinCostEfficiency(food: FoodItem): Double {
        if (food.pricePer100g == 0.0) return 0.0
        return food.proteinPer100g / food.pricePer100g
    }

    /**
     * 计算热量成本（每元能买多少千卡热量）
     */
    fun calculateCalorieCost(food: FoodItem): Double {
        if (food.pricePer100g == 0.0) return 0.0
        return food.caloriesPer100g / food.pricePer100g
    }

    /**
     * 计算氨基酸评分（简化版）
     */
    fun calculateAminoAcidScore(food: FoodItem): Double {
        // 简化估算：动物蛋白得分高，植物蛋白得分低
        return when (food.category) {
            "肉禽水产" -> 0.9
            "蛋奶豆" -> 0.85
            "主食" -> 0.5
            "蔬菜" -> 0.4
            else -> 0.6
        }
    }
}

// ============================================
// 💰 食物成本计算器
// ============================================

object FoodCostCalculator {
    /**
     * 计算单次食用成本
     */
    fun calculateCostPerMeal(food: FoodItem): Double {
        return food.pricePer100g * food.typicalServing / 100.0
    }

    /**
     * 计算每周食物成本
     */
    fun calculateWeeklyCost(food: FoodItem): Double {
        return calculateCostPerMeal(food) * food.mealsPerWeek
    }

    /**
     * 计算每年食物成本
     */
    fun calculateYearlyCost(food: FoodItem): Double {
        return calculateWeeklyCost(food) * 52
    }

    /**
     * 计算蛋白质单价（每克蛋白质多少钱）
     */
    fun calculateProteinPricePerGram(food: FoodItem): Double {
        if (food.proteinPer100g == 0.0) return 0.0
        return food.pricePer100g / food.proteinPer100g
    }

    /**
     * 计算热量单价（每千卡热量多少钱）
     */
    fun calculateCaloriePricePerKcal(food: FoodItem): Double {
        if (food.caloriesPer100g == 0.0) return 0.0
        return food.pricePer100g / food.caloriesPer100g
    }

    /**
     * 计算食物TCO（总拥有成本）
     * 包括购买成本、存储成本、处理成本
     */
    fun calculateFoodTCO(food: FoodItem, settings: GlobalSettings): Double {
        val yearlyCost = calculateYearlyCost(food)
        val storageCost = yearlyCost * 0.05  // 存储成本约5%
        val wasteCost = yearlyCost * 0.15    // 食物浪费约15%
        return yearlyCost + storageCost + wasteCost
    }
}

// ============================================
// ⭐ 食物评级计算器
// ============================================

object FoodRatingCalculator {
    /**
     * 综合营养价值评级（返回等级文本 + 分数0-5）
     */
    fun getNutritionGrade(food: FoodItem): Pair<String, Double> {
        val density = FoodNutritionCalculator.calculateNutrientDensity(food)
        return when {
            density >= 0.8 -> "营养王者" to 5.0
            density >= 0.6 -> "营养丰富" to 4.0
            density >= 0.4 -> "营养一般" to 3.0
            density >= 0.2 -> "营养较低" to 2.0
            else -> "空热量" to 1.0
        }
    }

    /**
     * 性价比评级（返回等级文本 + 分数0-5）
     */
    fun getCostEfficiencyRating(food: FoodItem): Pair<String, Double> {
        val efficiency = FoodNutritionCalculator.calculateProteinCostEfficiency(food)
        return when {
            efficiency >= 3.0 -> "蛋白质神车" to 5.0
            efficiency >= 1.5 -> "性价比高" to 4.0
            efficiency >= 0.8 -> "性价比一般" to 3.0
            efficiency >= 0.3 -> "有点小贵" to 2.0
            else -> "蛋白质刺客" to 1.0
        }
    }

    /**
     * 健康风险等级 0-1（越高越不健康）
     */
    fun getHealthRiskLevel(food: FoodItem): Pair<String, Double> {
        var risk = 0.0

        // 高脂肪
        if (food.fatPer100g > 20) risk += 0.25
        if (food.fatPer100g > 30) risk += 0.15

        // 高碳水
        if (food.carbohydratePer100g > 60) risk += 0.2
        if (food.carbohydratePer100g > 80) risk += 0.1

        // 低纤维
        if (food.fiberPer100g < 1) risk += 0.15

        // 加工度（根据类别估算）
        val processingRisk = when (food.category) {
            "饮料零食" -> 0.3
            "加工食品" -> 0.25
            else -> 0.1
        }
        risk += processingRisk

        val level = when {
            risk < 0.2 -> "健康友好"
            risk < 0.4 -> "适量食用"
            risk < 0.6 -> "偶尔吃吃"
            risk < 0.8 -> "尽量少吃"
            else -> "健康杀手"
        }
        return level to risk.coerceIn(0.0, 1.0)
    }

    /**
     * 综合评分 0-100
     */
    fun getOverallScore(food: FoodItem): Double {
        val nutrition = FoodNutritionCalculator.calculateNutrientDensity(food)
        val costEfficiency = FoodNutritionCalculator.calculateProteinCostEfficiency(food).coerceAtMost(3.0) / 3.0
        val health = 1.0 - getHealthRiskLevel(food).second

        return (nutrition * 0.4 + costEfficiency * 0.3 + health * 0.3) * 100
    }

    /**
     * 获取综合能量等级文本
     */
    fun getFoodPowerLevel(food: FoodItem): String {
        val score = getOverallScore(food)
        return when {
            score >= 80 -> "超级食物"
            score >= 65 -> "优秀"
            score >= 50 -> "良好"
            score >= 35 -> "一般"
            else -> "垃圾食品"
        }
    }
}

// ============================================
// 🧬 食物进化论计算器
// ============================================

object FoodEvolutionCalculator {
    /**
     * 计算进化错配程度 0-1
     * 现代食物与人类进化环境中食物的差异程度
     */
    fun calculateEvolutionMismatch(food: FoodItem): Double {
        var mismatch = 0.0

        // 高糖错配
        if (food.carbohydratePer100g > 50) mismatch += 0.2
        if (food.carbohydratePer100g > 70) mismatch += 0.15

        // 高脂肪错配
        if (food.fatPer100g > 25) mismatch += 0.15
        if (food.fatPer100g > 40) mismatch += 0.1

        // 精加工错配
        if (food.category == "饮料零食") mismatch += 0.25
        if (food.category == "加工食品") mismatch += 0.15

        // 低纤维错配
        if (food.fiberPer100g < 2) mismatch += 0.15

        return mismatch.coerceIn(0.0, 1.0)
    }

    /**
     * 计算"原始营养"价值
     * 模拟人类狩猎采集时代能获得的营养密度
     */
    fun calculateOriginalNutrientDensity(food: FoodItem): Double {
        // 原始饮食特征：高蛋白、中脂、低碳水、高纤维
        val proteinScore = (food.proteinPer100g / 20.0).coerceAtMost(1.0)
        val fiberScore = (food.fiberPer100g / 10.0).coerceAtMost(1.0)
        val carbPenalty = (food.carbohydratePer100g / 50.0).coerceAtMost(1.0) * 0.3
        val fatScore = if (food.fatPer100g in 10.0..25.0) 1.0 else 0.5

        return (proteinScore * 0.4 + fiberScore * 0.25 + fatScore * 0.2 - carbPenalty * 0.15).coerceIn(0.0, 1.0)
    }

    /**
     * 计算营养折旧
     * 现代农业/加工导致的营养流失
     */
    fun calculateNutritionDepreciation(food: FoodItem): Double {
        val breedingLoss = food.breedingDegradation  // 育种导致的营养下降
        val processingLoss = food.processingLoss      // 加工导致的营养损失
        return breedingLoss + processingLoss
    }

    /**
     * 获取进化错配等级描述
     */
    fun getEvolutionMismatchLevel(food: FoodItem): String {
        val mismatch = calculateEvolutionMismatch(food)
        return when {
            mismatch < 0.2 -> "符合进化设计"
            mismatch < 0.4 -> "轻度错配"
            mismatch < 0.6 -> "中度错配"
            mismatch < 0.8 -> "高度错配"
            else -> "极端错配"
        }
    }
}

// ============================================
// 🧮 食物通用计算工具函数
// ============================================

/**
 * 计算每克蛋白质价格
 */
fun calculateProteinCostPerGram(food: FoodItem): Double {
    return FoodCostCalculator.calculateProteinPricePerGram(food)
}

/**
 * 获取营养等级文本
 */
fun getNutritionGradeText(food: FoodItem): String {
    return FoodRatingCalculator.getNutritionGrade(food).first
}

/**
 * 计算每周食物成本
 */
fun calculateWeeklyFoodCost(food: FoodItem): Double {
    return FoodCostCalculator.calculateWeeklyCost(food)
}

/**
 * 计算原始营养密度
 */
fun calculateOriginalNutrientDensity(food: FoodItem): Double {
    return FoodEvolutionCalculator.calculateOriginalNutrientDensity(food)
}

/**
 * 计算营养折旧
 */
fun calculateNutritionDepreciation(food: FoodItem): Double {
    return FoodEvolutionCalculator.calculateNutritionDepreciation(food)
}

/**
 * 获取进化错配等级
 */
fun getEvolutionMismatchLevel(food: FoodItem): String {
    return FoodEvolutionCalculator.getEvolutionMismatchLevel(food)
}

/**
 * 从食材组合计算成本
 */
fun calculateFoodCost(materials: List<String>, weights: List<Double>): Double {
    var total = 0.0
    for (i in materials.indices) {
        val materialName = materials[i]
        val weight = weights.getOrElse(i) { 0.0 }
        val pricePerKg = foodMaterialLibrary[materialName]?.basePrice ?: 0.0
        total += pricePerKg * weight / 1000.0
    }
    return total
}