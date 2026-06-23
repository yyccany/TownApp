package com.example.townapp.domain

import com.example.townapp.data.ClothingCategory
import com.example.townapp.data.ClothingItem
import com.example.townapp.data.GlobalSettings

object ClothingBusiness {

    data class ClothingAnalysisResult(
        val costPerWear: Double,
        val costPerMonth: Double,
        val usageRate: Double,
        val valueDensity: Double,
        val valueRating: ValueDensityCalculator.Rating,
        val isIdle: Boolean,
        val idleDays: Int,
        val isRedundant: Boolean,
        val redundancyLevel: Int,
        val isPsychologicallyExpired: Boolean,
        val psychologicalAge: Double,
        val iqTaxLevel: Int,
        val iqTaxReason: String,
        val premiumRatio: Double
    )

    fun analyzeClothing(item: ClothingItem, settings: GlobalSettings): ClothingAnalysisResult {
        val costPerWear = if (item.wearCount > 0) item.price / item.wearCount else item.price
        val costPerMonth = if (item.expectedLifespanMonths > 0) item.price / item.expectedLifespanMonths else item.price
        
        val maxWears = item.maxWearsPerYear * (item.expectedLifespanMonths / 12.0)
        val usageRate = if (maxWears > 0) item.wearCount / maxWears else 0.0

        val (valueDensity, valueRating) = ValueDensityCalculator.calculateClothingValueDensity(
            item.wearCount, item.price, item.expectedLifespanMonths
        )

        val purchaseDate = item.purchaseDate
        val daysSincePurchase = (System.currentTimeMillis() - purchaseDate) / (1000 * 60 * 60 * 24)
        val isIdle = item.wearCount == 0 && daysSincePurchase > 90
        val idleDays = if (isIdle) daysSincePurchase.toInt() - 90 else 0

        val isPsychologicallyExpired = daysSincePurchase > item.expectedLifespanMonths * 30 * 1.5
        val psychologicalAge = daysSincePurchase / (item.expectedLifespanMonths * 30)

        val iqTaxResult = calculateIQTax(item)
        val premiumRatio = calculatePremiumRatio(item)

        return ClothingAnalysisResult(
            costPerWear = costPerWear,
            costPerMonth = costPerMonth,
            usageRate = usageRate,
            valueDensity = valueDensity,
            valueRating = valueRating,
            isIdle = isIdle,
            idleDays = idleDays,
            isRedundant = false,
            redundancyLevel = 0,
            isPsychologicallyExpired = isPsychologicallyExpired,
            psychologicalAge = psychologicalAge,
            iqTaxLevel = iqTaxResult.first,
            iqTaxReason = iqTaxResult.second,
            premiumRatio = premiumRatio
        )
    }

    fun calculateIQTax(item: ClothingItem): Pair<Int, String> {
        var level = 0
        val reasons = mutableListOf<String>()

        val category = ClothingCategory.values().find { it.displayName == item.category }
        val refPrice = category?.defaultPrice ?: 150.0

        if (item.price > refPrice * 3) {
            level += 2
            reasons.add("价格远超同类产品")
        } else if (item.price > refPrice * 1.5) {
            level += 1
            reasons.add("有一定品牌溢价")
        }

        if (item.durabilityScore < 0.5 && item.price > refPrice) {
            level += 1
            reasons.add("耐久性差但价格高")
        }

        if (item.evolutionParadoxScore > 0.7) {
            level += 1
            reasons.add("追逐潮流，贬值快")
        }

        return Pair(level, reasons.joinToString("; "))
    }

    fun calculatePremiumRatio(item: ClothingItem): Double {
        val category = ClothingCategory.values().find { it.displayName == item.category }
        val refPrice = category?.defaultPrice ?: 150.0
        return (item.price - refPrice) / refPrice
    }

    fun checkRedundancy(items: List<ClothingItem>): Map<ClothingCategory, Int> {
        val counts = mutableMapOf<ClothingCategory, Int>()
        
        items.forEach { item ->
            val category = ClothingCategory.values().find { it.displayName == item.category } ?: ClothingCategory.TOP
            counts[category] = counts.getOrDefault(category, 0) + 1
        }
        
        return counts
    }

    fun getUsageStatus(item: ClothingItem): String {
        val daysSincePurchase = (System.currentTimeMillis() - item.purchaseDate) / (1000 * 60 * 60 * 24)
        
        return when {
            item.wearCount == 0 && daysSincePurchase > 90 -> "闲置"
            item.wearCount == 0 && daysSincePurchase <= 90 -> "未使用"
            daysSincePurchase / item.wearCount > 30 -> "很少使用"
            daysSincePurchase / item.wearCount > 7 -> "偶尔使用"
            else -> "常用"
        }
    }
}
