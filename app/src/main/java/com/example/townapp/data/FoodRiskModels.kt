package com.example.townapp.data

enum class RiskLevel(val label: String, val score: Int) {
    LOW("低风险", 1),
    MEDIUM("中风险", 2),
    HIGH("高风险", 3),
    CRITICAL("严重风险", 4)
}

/** 人体生理状态5档 */
enum class BodyLevel(val label: String, val description: String, val scoreRange: IntRange) {
    HEALTHY("健康舒适", "营养均衡、无毒素累积，身体状态最优", 80..100),
    SLIGHT_DISCOMFORT("轻微不适", "少量高糖高油/微量污染物，身体略有负担", 60..79),
    MODERATE_FATIGUE("中度疲惫", "长期高油高糖、农残累积，乏力、精神差", 40..59),
    OBVIOUS_DISCOMFORT("明显不适", "重金属/致病菌摄入，头晕、肠胃不适", 20..39),
    SEVERE_ILLNESS("严重病态", "大量有害物摄入、长期超标食材食用，机能大幅下降", 0..19)
}

/** 标准化营养数据（每100g） */
data class FoodNutrition(
    val foodId: Int,
    val protein: Double = 0.0,      // 蛋白质 g
    val fat: Double = 0.0,           // 脂肪 g
    val carbohydrate: Double = 0.0,  // 碳水化合物 g
    val sugar: Double = 0.0,         // 总糖分 g
    val fiber: Double = 0.0,         // 膳食纤维 g
    val calorie: Double = 0.0,       // 热量 kcal
    val vitaminScore: Double = 0.0,  // 维生素综合评分 0-100
    val mineralScore: Double = 0.0   // 矿物质综合评分 0-100
)

/** 健康风险指标 */
data class FoodRisk(
    val foodId: Int,
    val heavyMetal: Double = 0.0,     // 重金属含量 0-100 (0=无, 100=严重超标)
    val pesticide: Double = 0.0,      // 农残含量 0-100
    val greaseSugar: Double = 0.0,    // 糖油风险 0-100 (高糖/高油/反式脂肪)
    val parasite: Double = 0.0,       // 寄生虫风险 0-100 (野味/生食)
    val bacteria: Double = 0.0,       // 微生物/变质风险 0-100
    val riskLevel: RiskLevel = RiskLevel.LOW  // 综合风险评级
)

/** 角色身体状态追踪 */
data class BodyStateRecord(
    val roleId: Int,
    val bodyLevel: BodyLevel = BodyLevel.HEALTHY,
    val toxinAccumulation: Double = 0.0,    // 毒素累积 0-100
    val nutritionBalance: Double = 80.0,     // 营养均衡度 0-100
    val lastUpdateTime: Long = System.currentTimeMillis()
)

/** 身体状态→情绪修正值（食物→身体→情绪联动） */
data class EmotionModifier(
    val moodDelta: Int,           // 情绪变化值 (-50 to +20)
    val energyDelta: Int,         // 精力变化值 (-50 to +20)
    val description: String       // 描述文字
)

/** 食品风险评估与生理状态计算 */
object FoodRiskEvaluator {

    /** 根据风险指标计算综合风险评级 */
    fun evaluateRisk(risk: FoodRisk): RiskLevel {
        val maxFactor = maxOf(
            risk.heavyMetal, risk.pesticide, risk.greaseSugar,
            risk.parasite, risk.bacteria
        )
        return when {
            maxFactor >= 80 -> RiskLevel.CRITICAL
            maxFactor >= 50 -> RiskLevel.HIGH
            maxFactor >= 25 -> RiskLevel.MEDIUM
            else -> RiskLevel.LOW
        }
    }

    /** 根据营养数据+风险数据计算生理状态 */
    fun calculateBodyLevel(
        nutrition: FoodNutrition,
        risk: FoodRisk,
        currentToxin: Double
    ): BodyLevel {
        val riskScore = risk.riskLevel.score * 15
        val sugarFatPenalty = (risk.greaseSugar * 0.3).toInt()
        val heavyMetalPenalty = (risk.heavyMetal * 0.5).toInt()
        val totalPenalty =
            riskScore + sugarFatPenalty + heavyMetalPenalty + (currentToxin * 0.5).toInt()

        val healthScore = (100 - totalPenalty).coerceIn(0, 100)
        return BodyLevel.entries.firstOrNull { healthScore in it.scoreRange }
            ?: BodyLevel.SLIGHT_DISCOMFORT
    }

    /** 根据食物计算情绪修正值 */
    fun calculateEmotionModifier(
        nutrition: FoodNutrition,
        risk: FoodRisk
    ): EmotionModifier {
        // 短期即时影响：高糖高油让大脑短暂愉悦但身体受损
        val sugarHigh = (risk.greaseSugar * 0.3).toInt().coerceIn(0, 15)
        val healthPenalty = -(risk.riskLevel.score * 8)
        val nutrientBonus =
            ((nutrition.vitaminScore + nutrition.mineralScore) / 20).toInt().coerceIn(0, 10)

        val moodDelta = (sugarHigh + healthPenalty + nutrientBonus).coerceIn(-50, 20)
        val energyDelta =
            ((nutrition.calorie / 50).toInt().coerceIn(0, 15) - risk.riskLevel.score * 5)
                .coerceIn(-50, 20)

        val desc = when {
            risk.riskLevel == RiskLevel.CRITICAL -> "摄入严重有害物质，身体极度不适"
            risk.riskLevel == RiskLevel.HIGH -> "高风险管理食材，身体负担加重"
            moodDelta > 0 && risk.greaseSugar > 30 -> "短暂的味觉愉悦，但身体已在抗议"
            moodDelta > 0 -> "营养均衡，身心舒畅"
            else -> "身体状态拖累了心情"
        }

        return EmotionModifier(moodDelta, energyDelta, desc)
    }

    /** 长期毒素累积计算（按天） */
    fun accumulateToxin(risk: FoodRisk, currentToxin: Double, days: Int = 1): Double {
        val dailyIncrease =
            risk.heavyMetal * 0.1 + risk.pesticide * 0.08 + risk.parasite * 0.15
        return (currentToxin + dailyIncrease * days).coerceIn(0.0, 100.0)
    }

    /** 毒素自然代谢（每天） */
    fun metabolizeToxin(currentToxin: Double): Double {
        return (currentToxin * 0.85).coerceAtLeast(0.0)
    }
}

/** 风险等级角标配置 */
object RiskBadgeConfig {
    /** 风险等级对应的角标字符 */
    fun badgeChar(level: RiskLevel): String = when (level) {
        RiskLevel.LOW -> "⚪"
        RiskLevel.MEDIUM -> "🟨"
        RiskLevel.HIGH -> "🟧"
        RiskLevel.CRITICAL -> "❌"
    }

    /** 风险等级对应的像素颜色ID（复用现有colorId） */
    fun colorId(level: RiskLevel): Int = when (level) {
        RiskLevel.LOW -> 1
        RiskLevel.MEDIUM -> 3
        RiskLevel.HIGH -> 2
        RiskLevel.CRITICAL -> 4
    }
}