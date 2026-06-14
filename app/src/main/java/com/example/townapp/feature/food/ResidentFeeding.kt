package com.example.townapp.feature.food

import kotlin.math.roundToInt

/**
 * 居民喂食系统 —— 阶段1核心交互闭环
 * "选菜品 → 喂居民 → 看身体反应"
 */
data class ResidentHealth(
    val id: Int,
    val name: String,
    val age: Int,
    var bloodSugar: Double,     // 血糖 mmol/L (正常空腹 3.9-6.1)
    var bloodPressure: Int,     // 收缩压 mmHg (正常 90-140)
    var mood: Int,              // 情绪 0-100
    var energy: Int,            // 精力 0-100
    var healthScore: Int,       // 综合健康 0-100
    var lastMeal: String = "还没吃东西"
)

/** 喂食一颗菜品后的身体反应 */
data class FeedingResult(
    val residentName: String,
    val dishName: String,
    val bloodSugarChange: Double,
    val moodChange: Int,
    val healthChange: Int,
    val reaction: String,       // 一句话反馈
    val reactionEmoji: String   // 🥰😋😐😟🤢
)

object ResidentFeedingSystem {

    /** 初始居民数据 */
    fun defaultResidents(): List<ResidentHealth> = listOf(
        ResidentHealth(id = 1, name = "小明", age = 28, bloodSugar = 5.2, bloodPressure = 118, mood = 75, energy = 80, healthScore = 85),
        ResidentHealth(id = 2, name = "小红", age = 25, bloodSugar = 4.8, bloodPressure = 110, mood = 70, energy = 72, healthScore = 82),
        ResidentHealth(id = 3, name = "老王", age = 55, bloodSugar = 6.8, bloodPressure = 148, mood = 60, energy = 55, healthScore = 58),
        ResidentHealth(id = 4, name = "小李", age = 22, bloodSugar = 4.5, bloodPressure = 105, mood = 85, energy = 90, healthScore = 92)
    )

    /** 喂食菜品 → 计算身体反应 */
    fun feed(resident: ResidentHealth, dish: CuisineFood): FeedingResult {
        // 血糖变化: 含糖量每10g大约升0.5mmol/L, 油脂也会轻微升糖
        val sugarImpact = dish.sugar / 10.0 * 0.5
        val oilImpact = dish.oil / 50.0 * 0.3
        val carbImpact = dish.cholesterol / 200.0 * 0.4
        var bloodSugarDelta = sugarImpact + oilImpact + carbImpact

        // 高营养分可以缓冲血糖冲击
        if (dish.nutritionScore > 30) bloodSugarDelta *= 0.6

        // 情绪变化
        val moodDelta: Int = when {
            dish.nutritionScore >= 40 -> +8
            dish.nutritionScore >= 30 -> +4
            dish.nutritionScore >= 20 -> -2
            else -> -6
        }

        // 健康影响
        val healthDelta: Int = when (dish.healthRisk) {
            "LOW" -> +3
            "MEDIUM" -> -2
            "HIGH" -> -5
            "EXTREME" -> -10
            else -> 0
        }

        // 应用变化
        resident.bloodSugar = (resident.bloodSugar + bloodSugarDelta).coerceIn(3.0, 12.0)
        resident.mood = (resident.mood + moodDelta).coerceIn(0, 100)
        resident.energy = (resident.energy + (moodDelta / 2)).coerceIn(0, 100)
        resident.healthScore = (resident.healthScore + healthDelta).coerceIn(0, 100)
        resident.lastMeal = dish.name

        // 反应文案
        val (reaction, emoji) = when {
            bloodSugarDelta > 2.0 -> "血糖飙升了！感觉有点头晕……" to "🥴"
            bloodSugarDelta > 1.0 -> "血糖明显升高，建议多运动" to "😟"
            dish.healthRisk == "EXTREME" -> "身体在抗议……下次还是别吃了" to "🤢"
            dish.healthRisk == "HIGH" -> "感觉不太好，油盐有点重" to "😐"
            dish.nutritionScore >= 40 -> "太好吃了！感觉充满能量！" to "🥰"
            dish.nutritionScore >= 30 -> "味道不错，身体也舒服" to "😋"
            else -> "还行，没啥特别的感觉" to "😐"
        }

        return FeedingResult(
            residentName = resident.name,
            dishName = dish.name,
            bloodSugarChange = bloodSugarDelta,
            moodChange = moodDelta,
            healthChange = healthDelta,
            reaction = reaction,
            reactionEmoji = emoji
        )
    }
}