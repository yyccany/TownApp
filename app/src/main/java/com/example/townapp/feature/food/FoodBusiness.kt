package com.example.townapp.feature.food

import com.example.townapp.data.FoodItem
import com.example.townapp.data.SimpleFoodNutrition
import com.example.townapp.data.GlobalSettings
import com.example.townapp.domain.ValueDensityCalculator
import com.example.townapp.domain.SalaryCalculator

/**
 * 食物分析结果
 * 包含食物的年度成本、价值密度和冗余信息
 */
data class FoodAnalysisResult(
    val costPerYear: Double,
    val valueDensity: Double,
    val isRedundant: Boolean,
    val nutritionalScore: Double,
    val mealsPerWeek: Int
)

/**
 * 食物业务逻辑
 * 包含价值密度计算、健康成本分析等核心算法
 */
object FoodBusiness {

    // 来源系数
    private val sourceCoefficients = mapOf(
        "标准化养殖" to 1.0,
        "野生" to 0.7,
        "有机" to 1.1,
        "散养" to 0.9
    )

    // 烹饪方式系数
    private val cookingCoefficients = mapOf(
        "清蒸" to 0.9,
        "白煮" to 0.85,
        "快炒" to 0.7,
        "红烧" to 0.55,
        "油炸" to 0.4,
        "烧烤" to 0.35
    )

    // 食物温度系数：影响营养得分和健康成本
    private val temperatureCoefficients = mapOf(
        "常温(<40度)" to 1.0,
        "温热(40-65度)" to 0.9,
        "热食(>65度)" to 0.6 // 热食营养得分打6折，健康成本翻倍
    )

    // 食物新鲜度系数：影响营养得分和健康成本
    private val freshnessCoefficients = mapOf(
        "新鲜" to 1.0,
        "隔夜" to 0.7 // 隔夜菜营养得分打7折，健康成本翻倍
    )

    // 消费场景系数：影响总薪俸成本和健康成本
    private val sceneCoefficients = mapOf(
        "家常便饭" to 1.0,
        "普通聚餐" to 1.2,
        "商务宴请" to 1.5,
        "自助餐" to 2.0, // 自助餐最容易吃撑，健康成本翻倍
        "宴席/酒席" to 2.5 // 宴席最容易暴饮暴食，健康成本翻2.5倍
    )

    // 进食量系数：影响健康成本
    private val overeatingCoefficients = mapOf(
        "七分饱" to 1.0,
        "八分饱" to 1.2,
        "十分饱" to 1.8,
        "吃撑了" to 3.0 // 吃撑了健康成本翻3倍
    )

    /**
     * 计算食物价值密度（带温度、新鲜度、场景和进食量修正）
     */
    fun calculateFoodValueDensity(
        food: SimpleFoodNutrition,
        price: Double,
        weight: Double,
        eatCount: Int,
        source: String = "标准化养殖",
        cookingMethod: String = "清炒",
        temperature: String = "温热(40-65度)",
        freshness: String = "新鲜",
        scene: String = "家常便饭",
        overeatingLevel: String = "七分饱"
    ): Double {
        val sourceCoeff = sourceCoefficients[source] ?: 1.0
        val cookingCoeff = cookingCoefficients[cookingMethod] ?: 0.6
        val tempCoeff = temperatureCoefficients[temperature] ?: 0.9
        val freshCoeff = freshnessCoefficients[freshness] ?: 1.0
        val sceneCoeff = sceneCoefficients[scene] ?: 1.0
        val overeatingCoeff = overeatingCoefficients[overeatingLevel] ?: 1.0

        // 调整后的营养得分
        val adjustedNutritionScore = food.nutritionScore * sourceCoeff * cookingCoeff * tempCoeff * freshCoeff

        // 单位重量薪俸成本
        val unitSalaryCost = SalaryCalculator.calculateSalaryPrice(price) / weight

        // 健康隐性成本：吃撑和特殊场景的健康成本翻倍
        val healthCostMultiplier = sceneCoeff * overeatingCoeff
        val healthCost = (100 - adjustedNutritionScore) * 0.01 * unitSalaryCost * healthCostMultiplier

        // 总薪俸成本
        val totalCost = (unitSalaryCost + healthCost) * weight

        // 累计功能价值
        val totalValue = adjustedNutritionScore * 10.0 * eatCount

        // 维护成本
        val totalMaintenance = 0.05 * eatCount

        return ValueDensityCalculator.calculate(totalValue, totalCost, totalMaintenance)
    }

    /**
     * 计算食物的健康隐性成本
     */
    fun calculateHealthCost(
        nutritionScore: Double,
        price: Double,
        weight: Double,
        temperature: String = "温热(40-65度)",
        freshness: String = "新鲜"
    ): Double {
        val tempCoeff = temperatureCoefficients[temperature] ?: 0.9
        val freshCoeff = freshnessCoefficients[freshness] ?: 1.0

        val adjustedNutritionScore = nutritionScore * tempCoeff * freshCoeff
        val unitSalaryCost = SalaryCalculator.calculateSalaryPrice(price) / weight
        val healthCostMultiplier = if (temperature == "热食(>65度)" || freshness == "隔夜") 2.0 else 1.0

        return (100 - adjustedNutritionScore) * 0.01 * unitSalaryCost * healthCostMultiplier * weight
    }

    /**
     * 判断是否为热食（温度>65度）
     */
    fun isHotFood(temperature: Double): Boolean {
        return temperature > 65.0
    }

    /**
     * 判断是否为隔夜菜
     */
    fun isLeftover(hoursSinceCooked: Double): Boolean {
        return hoursSinceCooked > 24.0
    }

    /**
     * 获取温度系数描述
     */
    fun getTemperatureDescription(temperature: String): String {
        return when (temperature) {
            "常温(<40度)" -> "温度适宜，营养保留完整"
            "温热(40-65度)" -> "温度适中，口感好"
            "热食(>65度)" -> "温度过高，可能烫伤食道，增加癌症风险"
            else -> "未知温度"
        }
    }

    /**
     * 获取新鲜度系数描述
     */
    fun getFreshnessDescription(freshness: String): String {
        return when (freshness) {
            "新鲜" -> "新鲜食材，营养完整"
            "隔夜" -> "隔夜食材，亚硝酸盐含量升高，营养流失"
            else -> "未知新鲜度"
        }
    }

    /**
     * 生成热食警告信息
     */
    fun generateHotFoodWarning(): String {
        return """
            ⚠️ 热食警告：
            根据WHO国际癌症研究机构（IARC）2016年的研究，65℃以上的热食被列为2A类致癌物。
            中国食管癌发病率是全球平均的2倍，超过60%的病例与热食习惯相关。
            建议：刚做好的饭放3分钟再吃，等温度降到65℃以下。
        """.trimIndent()
    }

    /**
     * 生成隔夜菜警告信息
     */
    fun generateLeftoverWarning(): String {
        return """
            ⚠️ 隔夜菜警告：
            绿叶菜存放24小时后，亚硝酸盐含量会升高2-3倍，长期食用会增加胃癌风险。
            夏天室温下放2小时，细菌会繁殖1000倍，加热只能杀死细菌，无法消除毒素。
            建议：少做一点，吃多少做多少。绿叶菜尽量当天吃完。
        """.trimIndent()
    }

    /**
     * 计算等待降温的价值提升
     */
    fun calculateWaitBenefit(originalDensity: Double, cooledDensity: Double): Double {
        return ((cooledDensity - originalDensity) / originalDensity) * 100
    }

    /**
     * 计算新鲜食物对比隔夜食物的价值差异
     */
    fun calculateFreshVsLeftover(
        freshNutrition: Double,
        leftoverNutrition: Double,
        price: Double
    ): Pair<Double, Double> {
        val freshDensity = calculateFoodValueDensity(
            SimpleFoodNutrition("temp", freshNutrition, 0.0, 0.0, 0.0),
            price,
            1.0,
            1,
            freshness = "新鲜"
        )
        val leftoverDensity = calculateFoodValueDensity(
            SimpleFoodNutrition("temp", leftoverNutrition, 0.0, 0.0, 0.0),
            price,
            1.0,
            1,
            freshness = "隔夜"
        )
        return Pair(freshDensity, leftoverDensity)
    }

    /**
     * 分析单个食物的价值和成本
     * 用于 TownViewModel 中计算食物的年度成本、价值密度和冗余信息
     */
    fun analyzeFood(food: FoodItem, settings: GlobalSettings): FoodAnalysisResult {
        val nutritionalScore = food.nutritionalScore
        val pricePer100g = food.pricePer100g
        val servingSize = food.servingSize
        val mealsPerWeek = food.mealsPerWeek

        // 计算年度成本：每100g价格 × 每份克数 / 100 × 每周餐数 × 52周
        val costPerYear = (pricePer100g * servingSize / 100.0) * mealsPerWeek * 52.0

        // 计算价值密度：使用通用价值密度计算方法
        val functionalValue = nutritionalScore * 10.0 * mealsPerWeek * 52.0 / 100.0
        val totalCost = costPerYear
        val totalMaintenance = 0.05 * mealsPerWeek * 52.0
        val valueDensity = ValueDensityCalculator.calculate(functionalValue, totalCost, totalMaintenance)

        // 冗余判断：每周只有1餐以下视为冗余
        val isRedundant = mealsPerWeek <= 1

        return FoodAnalysisResult(
            costPerYear = costPerYear,
            valueDensity = valueDensity,
            isRedundant = isRedundant,
            nutritionalScore = nutritionalScore,
            mealsPerWeek = mealsPerWeek
        )
    }
}