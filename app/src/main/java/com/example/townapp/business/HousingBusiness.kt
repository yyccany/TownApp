package com.example.townapp.business

import com.example.townapp.data.model.DecorationStyle
import com.example.townapp.data.model.HousingStats

object HousingBusiness {
    val decorationStyles = mapOf(
        "毛坯房" to DecorationStyle(0.0, 1.0, 0.0, 0.0),
        "适老化日式" to DecorationStyle(15.0, 0.95, 25.0, 15.0),
        "日式极简" to DecorationStyle(10.8, 0.9, 20.0, 10.0),
        "现代简约" to DecorationStyle(3.9, 0.7, 10.0, 5.0),
        "北欧风" to DecorationStyle(2.6, 0.6, 15.0, 3.0),
        "新中式" to DecorationStyle(1.2, 0.5, 5.0, 2.0),
        "欧式豪华" to DecorationStyle(0.7, 0.4, -5.0, -5.0),
        "网红ins风" to DecorationStyle(0.2, 0.3, 5.0, -10.0),
        "杂物堆" to DecorationStyle(0.0, 0.1, -30.0, -20.0)
    )

    fun calculateDecorationBonus(housingStats: HousingStats): DecorationBonus {
        val style = decorationStyles[housingStats.decorationStyle] ?: decorationStyles["毛坯房"]!!
        val idlePenalty = if (housingStats.idleItemsCount >= 50) {
            DecorationBonus(-30.0, -20.0, -25.0, 30.0)
        } else if (housingStats.idleItemsCount >= 30) {
            DecorationBonus(-10.0, -5.0, -10.0, 15.0)
        } else {
            DecorationBonus(0.0, 0.0, 0.0, 0.0)
        }

        return DecorationBonus(
            happinessBonus = style.happinessBonus + idlePenalty.happinessBonus,
            awakeningBonus = style.awakeningBonus + idlePenalty.awakeningBonus,
            productivityBonus = (style.spaceUtilization - 0.5) * 50 + idlePenalty.productivityBonus,
            redundancyBonus = (1 - style.spaceUtilization) * 50 + idlePenalty.redundancyBonus
        )
    }

    fun calculateSpaceUtilization(housingStats: HousingStats): Double {
        val style = decorationStyles[housingStats.decorationStyle] ?: decorationStyles["毛坯房"]!!
        val idleFactor = if (housingStats.idleItemsCount >= 50) {
            0.1
        } else if (housingStats.idleItemsCount >= 30) {
            0.3
        } else {
            0.0
        }
        return maxOf(0.1, style.spaceUtilization - idleFactor)
    }

    fun calculateDecorationCost(decorationStyle: String): Int {
        return when (decorationStyle) {
            "毛坯房" -> 0
            "适老化日式" -> 600
            "日式极简" -> 500
            "现代简约" -> 700
            "北欧风" -> 800
            "新中式" -> 1000
            "欧式豪华" -> 1800
            "网红ins风" -> 1000
            else -> 0
        }
    }

    fun isDecorationStyleUnlocked(decorationStyle: String, awakeningValue: Int): Boolean {
        return when (decorationStyle) {
            "适老化日式" -> awakeningValue >= 800
            "日式极简" -> awakeningValue >= 500
            "新中式" -> awakeningValue >= 300
            else -> true
        }
    }

    data class DecorationBonus(
        val happinessBonus: Double,
        val awakeningBonus: Double,
        val productivityBonus: Double,
        val redundancyBonus: Double
    )
}