package com.example.townapp.domain

import com.example.townapp.data.*
import com.example.townapp.feature.food.ResidentHealth

/**
 * 食物→身体状态→情绪联动系统
 * 将食物消费与生理状态变化和情绪修正连接起来
 */
object FoodBodyLinkage {

    /**
     * 应用食物对居民的短期即时影响
     * @param resident 当前居民
     * @param foodItemId 被食用的食物ID
     * @param dishName 菜品名称
     * @param servingSize 食用份量(g)
     * @return FeedingResult 包含喂食结果
     */
    fun applyFoodEffects(
        resident: ResidentHealth,
        foodItemId: Int,
        dishName: String,
        servingSize: Int = 100
    ): FeedingResult {
        val nutrition = getNutrition(foodItemId)
        val risk = getFoodRisk(foodItemId)

        // 1. 计算营养影响
        val proteinImpact = (nutrition.protein / 100.0 * servingSize)
        val fatImpact = (nutrition.fat / 100.0 * servingSize)
        val carbImpact = (nutrition.carbohydrate / 100.0 * servingSize)
        val calorieImpact = (nutrition.calorie / 100.0 * servingSize)

        // 2. 计算血糖变化 (基于碳水+糖分)
        val bloodSugarChange = (carbImpact * 0.08 + nutrition.sugar * 0.12).coerceIn(-2.0, 5.0)

        // 3. 计算情绪修正
        val emotionMod = FoodRiskEvaluator.calculateEmotionModifier(nutrition, risk)

        // 4. 计算健康变化
        val nutritionScore = calculateNutritionScore(nutrition)
        val healthChange = ((nutritionScore - 50) / 25 - risk.riskLevel.score).toInt().coerceIn(-15, 10)

        // 5. 更新居民状态
        resident.mood = (resident.mood + emotionMod.moodDelta).coerceIn(0, 100)
        resident.energy = (resident.energy + emotionMod.energyDelta).coerceIn(0, 100)
        resident.bloodSugar = (resident.bloodSugar + bloodSugarChange).coerceIn(2.0, 15.0)
        resident.healthScore = (resident.healthScore + healthChange).coerceIn(0, 100)

        // 6. 生成反应表情和文本
        val (reactionEmoji, reaction) = generateReaction(nutrition, risk, emotionMod)

        // 7. 更新最近用餐
        resident.lastMeal = dishName

        // 8. 计算身体状态
        val bodyLevel = FoodRiskEvaluator.calculateBodyLevel(nutrition, risk, 0.0)
        val toxinAccum = FoodRiskEvaluator.accumulateToxin(risk, 0.0)

        return FeedingResult(
            residentName = resident.name,
            dishName = dishName,
            reactionEmoji = reactionEmoji,
            reaction = reaction,
            bloodSugarChange = bloodSugarChange,
            moodChange = emotionMod.moodDelta,
            healthChange = healthChange,
            bodyLevel = bodyLevel,
            emotionDescription = emotionMod.description,
            nutritionScore = nutritionScore.toInt(),
            riskLevel = risk.riskLevel
        )
    }

    /**
     * 长期毒素累积影响（按天结算）
     */
    fun applyDailyToxinAccumulation(
        resident: ResidentHealth,
        dailyFoodIds: List<Int>,
        currentToxin: Double = 0.0
    ): Double {
        var toxin = currentToxin
        for (foodId in dailyFoodIds) {
            val risk = getFoodRisk(foodId)
            toxin = FoodRiskEvaluator.accumulateToxin(risk, toxin)
        }
        // 每天代谢一部分
        toxin = FoodRiskEvaluator.accumulateToxin(
            com.example.townapp.data.FoodRisk(foodId = -1, riskLevel = RiskLevel.LOW),
            toxin,
            days = 0
        )
        // 自然代谢 15%
        toxin *= 0.85
        return toxin.coerceIn(0.0, 100.0)
    }

    /**
     * 根据身体状态调整心情（长期基础影响）
     */
    fun applyBodyStateToMood(resident: ResidentHealth, bodyLevel: BodyLevel) {
        val moodAdjustment = when (bodyLevel) {
            BodyLevel.HEALTHY -> 5
            BodyLevel.SLIGHT_DISCOMFORT -> 0
            BodyLevel.MODERATE_FATIGUE -> -8
            BodyLevel.OBVIOUS_DISCOMFORT -> -15
            BodyLevel.SEVERE_ILLNESS -> -25
        }
        resident.mood = (resident.mood + moodAdjustment).coerceIn(0, 100)
    }

    /**
     * 根据营养和风险生成反应
     */
    private fun generateReaction(
        nutrition: FoodNutrition,
        risk: FoodRisk,
        emotionMod: EmotionModifier
    ): Pair<String, String> {
        val emoji = when {
            risk.riskLevel == RiskLevel.CRITICAL -> "🤢"
            risk.riskLevel == RiskLevel.HIGH -> "😰"
            nutrition.calorie > 300.0 && risk.greaseSugar > 50.0 -> "😋"
            nutrition.vitaminScore > 60.0 -> "😌"
            emotionMod.moodDelta > 5 -> "😊"
            emotionMod.moodDelta < -10 -> "😞"
            else -> "😐"
        }

        val text = when {
            risk.riskLevel == RiskLevel.CRITICAL -> "摄入严重有害物质，身体强烈不适！"
            risk.riskLevel == RiskLevel.HIGH -> "高风险食材，长期食用有害健康"
            nutrition.calorie > 300.0 && risk.greaseSugar > 50.0 -> "口感很好但高糖高油，身体负担加重"
            nutrition.vitaminScore > 70.0 -> "营养丰富的健康选择，身体感到舒适"
            emotionMod.moodDelta > 0 -> "食用后心情愉悦，精力充沛"
            emotionMod.moodDelta < 0 -> "食用后身体不适，情绪受到影响"
            else -> "正常进食，无明显影响"
        }

        return Pair(emoji, text)
    }
}

/**
 * 扩展 FeedingResult 以包含新的身体状态信息
 */
data class FeedingResult(
    val residentName: String,
    val dishName: String,
    val reactionEmoji: String = "😐",
    val reaction: String = "",
    val bloodSugarChange: Double = 0.0,
    val moodChange: Int = 0,
    val healthChange: Int = 0,
    val bodyLevel: BodyLevel = BodyLevel.HEALTHY,
    val emotionDescription: String = "",
    val nutritionScore: Int = 0,
    val riskLevel: RiskLevel = RiskLevel.LOW
)