package com.example.townapp.domain

import com.example.townapp.data.ClothingItem
import com.example.townapp.data.fabricLibrary

// ============================================
// 👕 衣物价格计算器
// ============================================

object ClothingPriceCalculator {
    /**
     * 计算衣物的出厂成本价
     * 基于面料成分、用量、工艺复杂度
     */
    fun calculateCostPrice(
        fabrics: List<String>,
        ratios: List<Double>,
        fabricUsage: Double = 1.5,
        craftComplexity: Double = 1.0
    ): Double {
        var totalFabricCost = 0.0
        for (i in fabrics.indices) {
            val fabric = fabricLibrary[fabrics[i]] ?: continue
            val ratio = ratios.getOrNull(i) ?: (1.0 / fabrics.size)
            totalFabricCost += fabric.baseCost * ratio
        }
        return totalFabricCost * fabricUsage * craftComplexity
    }

    /**
     * 计算价格倍率（零售价/成本价）
     * 倍率越高，品牌溢价越多
     */
    fun calculatePriceMultiplier(retailPrice: Double, costPrice: Double): Double {
        if (costPrice == 0.0) return 0.0
        return retailPrice / costPrice
    }

    /**
     * 估算衣物的合理价格
     */
    fun estimateFairPrice(item: ClothingItem): Double {
        val cost = calculateWeightedBasePrice(item.fabrics, item.fabricRatios)
        val craftsmanship = when (item.category) {
            "外套" -> 3.0
            "西装外套" -> 4.0
            "皮衣" -> 5.0
            "裙子" -> 2.5
            else -> 2.0
        }
        return cost * craftsmanship
    }
}

// ============================================
// ⭐ 衣物评级计算器
// ============================================

object ClothingRatingCalculator {
    /**
     * 综合性价比评级（返回级别文本 + 分数0-5）
     */
    fun getOverallRating(item: ClothingItem): Pair<String, Double> {
        val costPerHour = item.getCostPerHour()
        return when {
            costPerHour < 0.5 -> "SSR" to 5.0
            costPerHour < 1.0 -> "SR" to 4.5
            costPerHour < 2.0 -> "A级" to 4.0
            costPerHour < 4.0 -> "B级" to 3.0
            costPerHour < 8.0 -> "C级" to 2.0
            else -> "D级" to 1.0
        }
    }

    /**
     * 耐用性评级
     */
    fun getDurabilityRating(item: ClothingItem): Pair<String, Double> {
        val realLife = item.getRealLife()
        return when {
            realLife >= 8.0 -> "非常耐用" to 5.0
            realLife >= 5.0 -> "比较耐用" to 4.0
            realLife >= 3.0 -> "中等" to 3.0
            realLife >= 1.5 -> "一般" to 2.0
            else -> "快消品" to 1.0
        }
    }

    /**
     * 材质质量评分
     */
    fun getFabricQualityScore(fabrics: List<String>, ratios: List<Double>): Double {
        var totalScore = 0.0
        var totalRatio = 0.0
        for (i in fabrics.indices) {
            val fabric = fabricLibrary[fabrics[i]] ?: continue
            val ratio = ratios.getOrNull(i) ?: (1.0 / fabrics.size)
            val quality = (fabric.durability + fabric.comfort + fabric.breathability) / 3.0
            totalScore += quality * ratio
            totalRatio += ratio
        }
        return if (totalRatio > 0) totalScore / totalRatio else 0.0
    }
}

// ============================================
// 💰 衣物成本计算器
// ============================================

object ClothingCostCalculator {
    /**
     * 计算每小时穿着成本
     */
    fun calculateCostPerHour(item: ClothingItem): Double {
        return item.getCostPerHour()
    }

    /**
     * 计算每年穿着成本
     */
    fun calculateYearlyCost(item: ClothingItem): Double {
        return item.price / item.getRealLife()
    }

    /**
     * 计算每次穿着成本
     */
    fun calculateCostPerWear(item: ClothingItem): Double {
        val totalWears = item.getRealLife() * item.useDaysPerYear
        return if (totalWears > 0) item.price / totalWears else 0.0
    }

    /**
     * 计算TCO（总拥有成本）
     * 包括购买价、洗护成本、保养成本
     */
    fun calculateTCO(item: ClothingItem, settings: com.example.townapp.data.GlobalSettings): Double {
        val laundryCost = if (item.dryClean) {
            // 干洗成本
            item.getRealLife() * 12 * 30.0  // 每月一次干洗，每次30元
        } else {
            // 水洗成本（洗衣液、水电、折旧）
            item.getRealLife() * 12 * 5.0
        }
        return item.price + laundryCost
    }

    /**
     * 计算隐藏成本
     * 决策成本、搭配成本、收纳成本
     */
    fun calculateHiddenCosts(item: ClothingItem, settings: com.example.townapp.data.GlobalSettings): Map<String, Double> {
        return mapOf(
            "决策成本" to item.price * item.decisionDifficulty * 0.1,
            "搭配成本" to item.price * item.matchingDifficulty * 0.05,
            "收纳成本" to item.price * 0.02
        )
    }
}

// ============================================
// 🧬 衣物进化论计算器
// ============================================

object ClothingEvolutionCalculator {
    /**
     * 计算衣物的"进化错配"程度
     * 现代衣物为了时尚牺牲了多少实用性
     */
    fun calculateEvolutionMismatch(item: ClothingItem): Double {
        val fashionOverFunction = (item.decisionDifficulty + item.matchingDifficulty) / 2.0
        val plannedObsolescence = item.plannedObsolescence
        val materialDegradation = item.materialDegradation
        return (fashionOverFunction * 0.4 + plannedObsolescence * 0.35 + materialDegradation * 0.25)
    }

    /**
     * 计算"原始衣物"价值
     * 如果用最原始的材料和工艺，这件衣服值多少钱
     */
    fun calculateOriginalValue(item: ClothingItem): Double {
        // 去除品牌溢价、营销成本、时尚元素
        val fairPrice = ClothingPriceCalculator.estimateFairPrice(item)
        val degradation = item.getOverallDegradation()
        return fairPrice * (1 + degradation)  // 退化越多，原始价值越高
    }

    /**
     * 获取错配等级文本
     */
    fun getMismatchLevel(mismatch: Double): String {
        return when {
            mismatch < 0.2 -> "高度实用"
            mismatch < 0.4 -> "比较实用"
            mismatch < 0.6 -> "中等"
            mismatch < 0.8 -> "偏装饰性"
            else -> "纯装饰"
        }
    }
}

// ============================================
// 🧮 衣物通用计算工具函数
// ============================================

/**
 * 计算加权基础面料价格
 */
fun calculateWeightedBasePrice(fabrics: List<String>, ratios: List<Double>): Double {
    var total = 0.0
    var totalRatio = 0.0
    for (i in fabrics.indices) {
        val fabric = fabricLibrary[fabrics[i]] ?: continue
        val ratio = ratios.getOrNull(i) ?: (1.0 / fabrics.size)
        total += fabric.baseCost * ratio
        totalRatio += ratio
    }
    return if (totalRatio > 0) total / totalRatio else 0.0
}

/**
 * 计算价格密度（每克价格）
 */
fun calculatePriceDensity(price: Double, fabrics: List<String>, ratios: List<Double>): Double {
    val basePrice = calculateWeightedBasePrice(fabrics, ratios)
    return if (basePrice > 0) price / basePrice else 0.0
}

/**
 * 计算衣物成本（从面料组合）
 */
fun calculateClothingCost(fabrics: List<String>, ratios: List<Double>, fabricUsage: Double = 1.5): Double {
    var total = 0.0
    for (i in fabrics.indices) {
        val fabric = fabricLibrary[fabrics[i]] ?: continue
        val ratio = ratios.getOrNull(i) ?: (1.0 / fabrics.size)
        total += fabric.baseCost * ratio
    }
    return total * fabricUsage
}

/**
 * 计算衣物溢价倍率
 */
fun calculateClothingPremium(userPrice: Double, costPrice: Double): Double {
    return if (costPrice > 0) userPrice / costPrice else 0.0
}

/**
 * 获取品质等级文本
 */
fun getQualityGradeText(priceDensity: Double): String {
    return when {
        priceDensity < 1.5 -> "超值"
        priceDensity < 2.5 -> "合理"
        priceDensity < 4.0 -> "一般"
        priceDensity < 6.0 -> "溢价"
        else -> "智商税"
    }
}

/**
 * 获取评级文本
 */
fun getRatingText(costPerHour: Double): String {
    return when {
        costPerHour < 0.5 -> "SSR"
        costPerHour < 1.0 -> "SR"
        costPerHour < 2.0 -> "A级"
        costPerHour < 4.0 -> "B级"
        costPerHour < 8.0 -> "C级"
        else -> "D级"
    }
}

/**
 * 计算整体退化程度
 */
fun calculateOverallDegradation(item: ClothingItem): Double {
    return item.getOverallDegradation()
}

/**
 * 计算质量折旧
 */
fun calculateQualityDepreciation(item: ClothingItem): Double {
    return item.price * item.getOverallDegradation()
}

/**
 * 计算衣物智商税指数 0-1
 */
fun calculateClothingScamIndex(item: ClothingItem, priceDensity: Double): Double {
    val brandPremium = (priceDensity - 2.0).coerceAtLeast(0.0) / 8.0
    val fashionTax = (item.decisionDifficulty + item.matchingDifficulty) / 4.0
    val planned = item.plannedObsolescence
    return (brandPremium * 0.4 + fashionTax * 0.35 + planned * 0.25).coerceIn(0.0, 1.0)
}

/**
 * 获取智商税等级文本
 */
fun getClothingScamLevelText(scamIndex: Double): String {
    return when {
        scamIndex < 0.2 -> "实在好物"
        scamIndex < 0.4 -> "有点溢价"
        scamIndex < 0.6 -> "品牌税"
        scamIndex < 0.8 -> "智商税"
        else -> "重灾区"
    }
}
