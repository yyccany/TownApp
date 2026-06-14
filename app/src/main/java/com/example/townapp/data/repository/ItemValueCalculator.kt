package com.example.townapp.data.repository

import com.example.townapp.data.ClothingItem
import com.example.townapp.data.FoodItem
import com.example.townapp.data.HousingItem
import com.example.townapp.data.HouseholdItem

/**
 * ✨ 物品综合价值计算器
 * 
 * 这不仅仅是记账工具，而是从「万物平等」视角计算物品的综合价值：
 * - 💰 金钱层面：购入价、每日折旧成本
 * - 💖 精神层面：愉悦感、焦虑缓解、情绪价值
 * - ✨ 闪光点：陪伴价值、不可替代的意义
 * 
 * 核心理念：每个物品都有其独特价值，平凡的物品也能带来不平凡的意义
 */
object ItemValueCalculator {

    /**
     * 计算物品的每日综合价值
     */
    fun calculateDailyValue(item: Any): DailyValueResult {
        return when (item) {
            is FoodItem -> calculateFoodDailyValue(item)
            is ClothingItem -> calculateClothingDailyValue(item)
            is HouseholdItem -> calculateHouseholdDailyValue(item)
            is HousingItem -> calculateHousingDailyValue(item)
            else -> DailyValueResult()
        }
    }

    /**
     * 计算食品的每日综合价值
     */
    private fun calculateFoodDailyValue(food: FoodItem): DailyValueResult {
        // 每日成本 = 单价 * 典型份量 / 预计食用天数
        val dailyCost = (food.pricePer100g * food.typicalServing / 100) / food.shelfLifeDays
        
        // 营养增益计算
        val nutritionGain = NutritionGain(
            protein = food.proteinPer100g * 0.5,
            vitamins = food.nutritionalScore * 0.3,
            energy = food.caloriesPer100g / 50.0
        )
        
        // 精神增益（基于营养评分和健康风险）
        val mentalGain = MentalGain(
            happiness = when {
                food.nutritionalScore >= 80 -> 3.0
                food.nutritionalScore >= 60 -> 2.0
                else -> 1.0
            },
            anxietyReduction = when {
                food.nutritionalScore >= 80 -> 2.5
                food.nutritionalScore >= 60 -> 1.5
                else -> 0.5
            },
            satiety = food.satietyIndex / 20.0
        )
        
        // 闪光点描述
        val sparkle = generateFoodSparkle(food)
        
        return DailyValueResult(
            dailyCost = dailyCost.coerceAtLeast(0.01),
            nutritionGain = nutritionGain,
            mentalGain = mentalGain,
            sparkle = sparkle,
            overallScore = calculateOverallScore(dailyCost, nutritionGain, mentalGain)
        )
    }

    /**
     * 计算服饰的每日综合价值
     */
    private fun calculateClothingDailyValue(clothing: ClothingItem): DailyValueResult {
        // 每日成本 = 价格 / (预计使用月数 * 30)
        val dailyCost = clothing.price / (clothing.expectedLifespanMonths * 30)
        
        // 精神增益（基于材质和风格）
        val mentalGain = MentalGain(
            happiness = when {
                clothing.material.contains("纯棉", true) || 
                clothing.material.contains("羊毛", true) -> 2.5
                clothing.material.contains("丝绸", true) -> 3.0
                else -> 1.5
            },
            confidence = when (clothing.style) {
                "正式", "商务" -> 2.0
                "休闲", "舒适" -> 1.5
                "时尚", "潮流" -> 2.5
                else -> 1.0
            },
            comfort = if (clothing.fabricType.contains("透气", true)) 2.0 else 1.0
        )
        
        // 闪光点描述
        val sparkle = generateClothingSparkle(clothing)
        
        return DailyValueResult(
            dailyCost = dailyCost.coerceAtLeast(0.01),
            mentalGain = mentalGain,
            sparkle = sparkle,
            overallScore = calculateOverallScore(dailyCost, null, mentalGain)
        )
    }

    /**
     * 计算日用品的每日综合价值
     */
    private fun calculateHouseholdDailyValue(item: HouseholdItem): DailyValueResult {
        // 每日成本 = 价格 / (预计使用年数 * 365)
        val dailyCost = item.price / (item.lifespanYears * 365)
        
        // 精神增益（基于用途）
        val mentalGain = MentalGain(
            happiness = when (item.category) {
                "厨房用品", "餐具" -> 1.5
                "清洁用品" -> 1.0
                "装饰摆件" -> 2.0
                "办公用品" -> 1.5
                else -> 1.0
            },
            convenience = when (item.category) {
                "厨房用品", "清洁用品" -> 2.5
                "电子产品" -> 3.0
                else -> 1.5
            }
        )
        
        // 闪光点描述
        val sparkle = generateHouseholdSparkle(item)
        
        return DailyValueResult(
            dailyCost = dailyCost.coerceAtLeast(0.01),
            mentalGain = mentalGain,
            sparkle = sparkle,
            overallScore = calculateOverallScore(dailyCost, null, mentalGain)
        )
    }

    /**
     * 计算房产/大件物品的每日综合价值
     */
    private fun calculateHousingDailyValue(item: HousingItem): DailyValueResult {
        // 每日成本 = (价格 * 折旧率) / 365
        val dailyCost = (item.price * item.depreciationRate) / 365
        
        // 精神增益
        val mentalGain = MentalGain(
            happiness = 3.0,
            security = 3.5,
            comfort = 3.0
        )
        
        // 闪光点描述
        val sparkle = generateHousingSparkle(item)
        
        return DailyValueResult(
            dailyCost = dailyCost.coerceAtLeast(0.01),
            mentalGain = mentalGain,
            sparkle = sparkle,
            overallScore = calculateOverallScore(dailyCost, null, mentalGain)
        )
    }

    /**
     * 生成食品闪光点描述
     */
    private fun generateFoodSparkle(food: FoodItem): String {
        val qualities = mutableListOf<String>()
        
        if (food.nutritionalScore >= 80) {
            qualities.add("营养满分")
        }
        if (food.fatPer100g < 5) {
            qualities.add("低脂肪")
        }
        if (food.proteinPer100g >= 20) {
            qualities.add("高蛋白")
        }
        if (food.note.isNotBlank()) {
            qualities.add(food.note.take(15))
        }
        
        return if (qualities.isNotEmpty()) {
            "它${qualities.joinToString("，")}，在每一餐中守护你的健康"
        } else {
            "平凡的食物，不平凡的陪伴"
        }
    }

    /**
     * 生成服饰闪光点描述
     */
    private fun generateClothingSparkle(clothing: ClothingItem): String {
        val qualities = mutableListOf<String>()
        
        if (clothing.material.isNotBlank()) {
            qualities.add("${clothing.material}材质")
        }
        if (clothing.style.isNotBlank()) {
            qualities.add("${clothing.style}风格")
        }
        if (clothing.expectedLifespanMonths >= 36) {
            qualities.add("耐用持久")
        }
        
        return if (qualities.isNotEmpty()) {
            "它是${qualities.joinToString("、")}，在每个重要时刻陪伴你"
        } else {
            "一件普通的衣服，承载着你的故事"
        }
    }

    /**
     * 生成日用品闪光点描述
     */
    private fun generateHouseholdSparkle(item: HouseholdItem): String {
        val qualities = mutableListOf<String>()
        
        if (item.brand.isNotBlank()) {
            qualities.add("${item.brand}品牌")
        }
        if (item.lifespanYears >= 5) {
            qualities.add("经久耐用")
        }
        if (item.note.isNotBlank()) {
            qualities.add(item.note.take(15))
        }
        
        return if (qualities.isNotEmpty()) {
            "它${qualities.joinToString("，")}，默默为你的生活添彩"
        } else {
            "小小的物件，大大的温暖"
        }
    }

    /**
     * 生成房产闪光点描述
     */
    private fun generateHousingSparkle(item: HousingItem): String {
        return if (item.model.isNotBlank()) {
            "这是${item.model}，为你遮风挡雨，是心灵的港湾"
        } else {
            "一个温暖的家，承载着生活的点点滴滴"
        }
    }

    /**
     * 计算综合评分
     */
    private fun calculateOverallScore(
        dailyCost: Double,
        nutritionGain: NutritionGain?,
        mentalGain: MentalGain?
    ): Double {
        var score = 50.0 // 基础分：万物皆有价值
        
        // 成本效益：成本越低，得分越高
        score += (10.0 / dailyCost).coerceIn(0.0, 20.0)
        
        // 营养增益
        nutritionGain?.let {
            score += (it.protein + it.vitamins + it.energy) / 3
        }
        
        // 精神增益
        mentalGain?.let {
            val totalMental = it.happiness + it.anxietyReduction + it.confidence + 
                           it.comfort + it.security + it.satiety
            score += totalMental / 2
        }
        
        return score.coerceIn(0.0, 100.0)
    }

    /**
     * 每日综合价值结果
     */
    data class DailyValueResult(
        val dailyCost: Double = 0.0,
        val nutritionGain: NutritionGain? = null,
        val mentalGain: MentalGain? = null,
        val sparkle: String = "平凡中蕴含着不平凡的意义",
        val overallScore: Double = 50.0
    )

    /**
     * 营养增益
     */
    data class NutritionGain(
        val protein: Double = 0.0,
        val vitamins: Double = 0.0,
        val energy: Double = 0.0
    )

    /**
     * 精神增益
     */
    data class MentalGain(
        val happiness: Double = 0.0,
        val anxietyReduction: Double = 0.0,
        val confidence: Double = 0.0,
        val comfort: Double = 0.0,
        val security: Double = 0.0,
        val satiety: Double = 0.0,
        val convenience: Double = 0.0
    )
}
