package com.example.townapp.business

import com.example.townapp.data.database.dao.UserBodyStateDao
import com.example.townapp.data.database.entity.FoodNutritionEntity
import com.example.townapp.data.database.entity.FoodRiskEntity
import com.example.townapp.data.database.entity.UserBodyState
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.math.roundToInt

/**
 * 生理状态核心业务
 *
 * 设计原则：
 * - 人首先是一个生物有机体
 * - 短期即时影响：单次喂食后即时更新生理状态
 * - 长期累积影响：后台定时计算，毒素自然衰减，营养自然消耗
 * - 状态联动：生理状态会影响情绪和精力
 *
 * 核心公式：
 *   饱腹感 += 食物热量系数 - 每小时自然消耗
 *   营养均衡度 += 食物营养评分 - 不均衡饮食的惩罚
 *   健康值 = 营养*0.3 + 免疫*0.3 + (100-毒素)*0.2 + 精力*0.2
 */
object BodyStateBusiness {

    /** 独立后台计算线程池（与 UI 线程隔离） */
    private val computeExecutor = Executors.newFixedThreadPool(4) { r ->
        Thread(r, "BodyState-Compute").apply {
            isDaemon = true
            priority = Thread.NORM_PRIORITY - 1
        }
    }
    private val computeScope = CoroutineScope(computeExecutor.asCoroutineDispatcher() + SupervisorJob())

    /** 长期累积任务是否运行中 */
    @Volatile
    private var isAccumulationRunning = false

    data class HealthRisk(
        val type: HealthRiskType,
        val title: String,
        val description: String
    )

    enum class HealthRiskType {
        HUNGER,
        GASTROENTERITIS,
        TOXIC_OVERLOAD,
        HIGH_BLOOD_SUGAR,
        LOW_IMMUNITY,
        POOR_HEALTH,
        EXHAUSTION
    }

    // ============================================
    // 短期即时影响：单次进食后立即生效
    // ============================================

    data class FeedingImpact(
        val updatedState: UserBodyState,
        val moodDelta: Int,
        val reaction: String,
        val reactionEmoji: String
    )

    /**
     * 应用食物对身体的即时影响
     *
     * 核心链条：食物 → 饱腹感+ → 营养均衡+/- → 血糖↑ → 肠胃负担↑
     *           → 毒素累积 → 免疫↓ → 健康↓ → 情绪↓
     */
    fun applyFeeding(
        body: UserBodyState,
        nutrition: FoodNutritionEntity,
        risk: FoodRiskEntity,
        servingGrams: Int = 300
    ): FeedingImpact {
        val ratio = servingGrams / 100.0

        // ---- 1. 饱腹感提升 ----
        // 热量越高，饱腹感越强，但过饱反而会降低舒适度
        val calorieDensity = nutrition.caloriesPer100g * ratio
        val satietyDelta = when {
            calorieDensity > 800 -> 50   // 非常饱
            calorieDensity > 500 -> 40
            calorieDensity > 300 -> 30
            calorieDensity > 150 -> 20
            else -> 10
        }.coerceAtMost(100 - body.satiety)

        // ---- 2. 营养均衡度变化 ----
        // 高蛋白高维生素=加分，高糖高脂肪=减分
        val proteinBonus = (nutrition.proteinPer100g * ratio * 0.5).toInt().coerceIn(0, 10)
        val vitaminBonus = (nutrition.vitaminCMg * ratio * 0.1).toInt().coerceIn(0, 8)
        val sugarPenalty = ((nutrition.sugarPer100g ?: 0.0) * ratio * 0.3).toInt().coerceIn(0, 15)
        val fatPenalty = (nutrition.fatPer100g * ratio * 0.2).toInt().coerceIn(0, 10)
        val nutritionDelta = proteinBonus + vitaminBonus - sugarPenalty - fatPenalty

        // ---- 3. 肠胃负担变化 ----
        // 太油、太辣、太撑都会增加肠胃负担
        val greaseBurden = (nutrition.fatPer100g * ratio * 0.3).toInt().coerceIn(0, 25)
        val overeatBurden = if (body.satiety + satietyDelta > 90) 15 else 0
        val processingBurden = (risk.processingRisk * ratio * 0.15).toInt().coerceIn(0, 20)
        val gastroDelta = greaseBurden + overeatBurden + processingBurden

        // ---- 4. 血糖变化 ----
        val sugarImpact = (nutrition.sugarPer100g ?: 0.0) * ratio * 0.05
        val carbImpact = nutrition.carbohydratePer100g * ratio * 0.015
        val bloodSugarDelta = sugarImpact + carbImpact

        // ---- 5. 体温变化（小幅波动）----
        val tempDelta = (if (nutrition.caloriesPer100g * ratio > 400) 0.1 else 0.0)

        // ---- 6. 毒素累积 ----
        val heavyMetalDelta = risk.heavyMetalRisk * ratio * 0.05
        val additiveDelta = risk.additiveRisk * ratio * 0.08
        val toxinDelta = heavyMetalDelta + additiveDelta

        // ---- 7. 精力变化（吃太饱反而犯困）----
        val energyFromFood = when {
            nutrition.nutritionScore >= 60 -> 10
            nutrition.nutritionScore >= 40 -> 5
            nutrition.nutritionScore >= 20 -> -3
            else -> -8
        }
        val foodComaPenalty = if (body.satiety + satietyDelta > 90) -10 else 0
        val energyDelta = energyFromFood + foodComaPenalty

        // ---- 8. 免疫变化 ----
        val immuneDelta = when {
            nutrition.proteinPer100g > 15 -> 3
            risk.processingRisk > 50 -> -8
            nutrition.nutritionScore > 50 -> 2
            else -> 0
        }

        // ---- 9. 综合健康评分 ----
        val newSatiety = (body.satiety + satietyDelta).coerceIn(0, 100)
        val newNutrition = (body.nutritionBalance + nutritionDelta).coerceIn(0, 100)
        val newGastro = (body.gastroBurden + gastroDelta).coerceIn(0, 100)
        val newToxin = (body.toxinLevel + toxinDelta).coerceIn(0.0, 100.0)
        val newImmune = (body.immuneLevel + immuneDelta).coerceIn(0, 100)
        val newEnergy = (body.energy + energyDelta).coerceIn(0, 100)
        val newBloodSugar = (body.bloodSugar + bloodSugarDelta).coerceIn(2.5, 15.0)
        val newTemp = (body.bodyTemperature + tempDelta).coerceIn(35.5, 37.8)
        val newComfort = (body.comfortLevel - (newGastro - body.gastroBurden) * 0.5 - if (newBloodSugar > 7.8) 5.0 else 0.0).coerceIn(
            0.0, 100.0).toInt()
        val newSkin = (body.skinStatus - (risk.additiveRisk * 0.1).toInt() + (vitaminBonus * 0.3).toInt()).coerceIn(0, 100)

        // 健康值重新计算
        val newHealth = ((newNutrition * 0.3) +
                (newImmune * 0.3) +
                ((100 - newToxin) * 0.2) +
                (newEnergy * 0.2)).toInt().coerceIn(0, 100)

        val updated = body.copy(
            satiety = newSatiety,
            nutritionBalance = newNutrition,
            gastroBurden = newGastro,
            bloodSugar = newBloodSugar,
            bodyTemperature = newTemp,
            comfortLevel = newComfort,
            skinStatus = newSkin,
            toxinLevel = newToxin,
            heavyMetalAccum = (body.heavyMetalAccum + heavyMetalDelta).coerceIn(0.0, 100.0),
            immuneLevel = newImmune,
            energy = newEnergy,
            healthScore = newHealth,
            totalMeals = body.totalMeals + 1,
            highRiskMeals = body.highRiskMeals + if (risk.riskLevel in listOf("HIGH", "EXTREME")) 1 else 0,
            lastMealTime = System.currentTimeMillis(),
            lastMealName = nutrition.foodName,
            updateTime = System.currentTimeMillis()
        )

        // 情绪修正（基于整体状态）
        val healthWeight = 0.4
        val toxinWeight = -0.3
        val comfortWeight = 0.3
        val moodDelta = ((newHealth - body.healthScore) * healthWeight +
                (body.toxinLevel - newToxin) * toxinWeight +
                (newComfort - body.comfortLevel) * comfortWeight).toInt().coerceIn(-15, 15)

        val finalState = updated.copy(mood = (body.mood + moodDelta).coerceIn(0, 100))

        // 自然语言反馈
        val (reaction, emoji) = generateFeedingReaction(
            riskLevel = risk.riskLevel,
            bloodSugar = newBloodSugar,
            gastroBurden = newGastro,
            healthScore = newHealth,
            moodDelta = moodDelta
        )

        return FeedingImpact(finalState, moodDelta, reaction, emoji)
    }

    private fun generateFeedingReaction(
        riskLevel: String,
        bloodSugar: Double,
        gastroBurden: Int,
        healthScore: Int,
        moodDelta: Int
    ): Pair<String, String> {
        return when {
            riskLevel == "EXTREME" -> "这个食物的风险很高，身体在默默抗议……" to "\uD83E\uDD22"
            bloodSugar > 7.8 -> "血糖飙升了，感觉有点头晕，不想动……" to "\uD83E\uDD74"
            gastroBurden > 80 -> "吃得太撑太油，肠胃有点不舒服……" to "\uD83D\uDE1F"
            healthScore > 85 && moodDelta > 5 -> "这顿饭营养均衡，身体很满意，心情也变好了！" to "\uD83E\uDD70"
            moodDelta > 0 -> "味道不错，吃完感觉还可以" to "\uD83D\uDE0B"
            moodDelta < -5 -> "吃完感觉有点负担，下次注意了" to "\uD83D\uDE1E"
            else -> "正常进食，身体没什么特别反应" to "\uD83D\uDE10"
        }
    }

    // ============================================
    // 长期累积影响：后台定时计算
    // ============================================

    /**
     * 启动长期累积计算（每30分钟运行一次）
     */
    fun startLongTermAccumulation(dao: UserBodyStateDao) {
        if (isAccumulationRunning) return
        isAccumulationRunning = true

        computeScope.launch {
            while (isActive && isAccumulationRunning) {
                try {
                    val body = withContext(Dispatchers.IO) { dao.getSync(1) }
                    if (body != null) {
                        val updated = applyHourlyDecay(body, hours = 1)
                        withContext(Dispatchers.IO) { dao.updateSync(updated) }
                    }
                } catch (_: Exception) {}
                delay(30 * 60 * 1000L) // 30分钟
            }
        }
    }

    fun stopLongTermAccumulation() {
        isAccumulationRunning = false
    }

    /**
     * 应用时间流逝的自然衰减
     * 每小时：饱腹感-5，营养-2，肠胃负担-10，血糖回归
     */
    fun applyHourlyDecay(body: UserBodyState, hours: Int = 1): UserBodyState {
        // 饱腹感衰减
        val satietyDecay = 5 * hours
        val newSatiety = (body.satiety - satietyDecay).coerceIn(0, 100)

        // 营养均衡度缓慢回归中位
        val nutritionDrift = if (body.nutritionBalance > 50) -hours else if (body.nutritionBalance < 50) hours else 0
        val newNutrition = (body.nutritionBalance + nutritionDrift).coerceIn(0, 100)

        // 肠胃负担自然降低
        val newGastro = (body.gastroBurden - 10 * hours).coerceIn(0, 100)

        // 血糖回归正常范围
        val bloodSugarDrift = when {
            body.bloodSugar > 6.0 -> -0.3 * hours
            body.bloodSugar < 4.5 -> 0.2 * hours
            else -> 0.0
        }
        val newBloodSugar = (body.bloodSugar + bloodSugarDrift).coerceIn(3.0, 8.0)

        // 体温回归
        val tempDrift = when {
            body.bodyTemperature > 36.8 -> -0.1 * hours
            body.bodyTemperature < 36.3 -> 0.05 * hours
            else -> 0.0
        }
        val newTemp = (body.bodyTemperature + tempDrift).coerceIn(35.5, 37.5)

        // 毒素自然衰减
        val newToxin = (body.toxinLevel * (1 - body.toxinDecayRate * 0.01 * hours)).coerceIn(0.0, 100.0)
        val newHeavyMetal = (body.heavyMetalAccum * 0.995).coerceIn(0.0, 100.0)

        // 舒适度随时间改善
        val comfortRecover = if (body.comfortLevel < 70) hours * 2 else 0
        val newComfort = (body.comfortLevel + comfortRecover).coerceIn(0, 100)

        // 饥饿状态下的健康惩罚
        val hungryHealthPenalty = if (newSatiety < 20) -hours * 2 else 0
        val hungerEnergyPenalty = if (newSatiety < 20) -hours * 3 else 0

        // 健康和精力自然恢复
        val healthRecover = if (body.toxinLevel < 30 && body.nutritionBalance > 40) hours else 0
        val newHealth = (body.healthScore + healthRecover + hungryHealthPenalty).coerceIn(0, 100)
        val newEnergy = (body.energy + (hours * 2) + hungerEnergyPenalty).coerceIn(0, 100)

        // 免疫缓慢恢复
        val immuneRecover = if (body.toxinLevel < 40) hours else -hours
        val newImmune = (body.immuneLevel + immuneRecover).coerceIn(0, 100)

        // 情绪基于身体状态微调
        val bodyStatusMood = when {
            newSatiety < 20 -> -hours * 3          // 饿了心情不好
            newGastro > 70 -> -hours * 2           // 肠胃不舒服
            newHealth > 80 -> hours                // 身体好心情好
            else -> 0
        }
        val newMood = (body.mood + bodyStatusMood).coerceIn(0, 100)

        return body.copy(
            satiety = newSatiety,
            nutritionBalance = newNutrition,
            gastroBurden = newGastro,
            bloodSugar = newBloodSugar,
            bodyTemperature = newTemp,
            comfortLevel = newComfort,
            toxinLevel = newToxin,
            heavyMetalAccum = newHeavyMetal,
            immuneLevel = newImmune,
            energy = newEnergy,
            healthScore = newHealth,
            mood = newMood,
            updateTime = System.currentTimeMillis()
        )
    }

    /**
     * 应用一天后的完整变化（用于时间跳跃）
     */
    fun applyOneDay(body: UserBodyState): UserBodyState {
        var state = body
        // 模拟一天8小时的变化（睡眠+活动的简化）
        repeat(8) {
            state = applyHourlyDecay(state, 3)
        }
        return state
    }

    // ============================================
    // 状态评估（供UI和其他系统使用）
    // ============================================

    /**
     * 评估当前是否需要触发健康事件
     */
    fun assessHealthRisks(body: UserBodyState): List<HealthRisk> {
        val risks = mutableListOf<HealthRisk>()

        if (body.satiety < 15) risks.add(
            HealthRisk(HealthRiskType.HUNGER, "严重饥饿", "你已经很久没吃东西了，身体开始消耗自身储备")
        )
        if (body.gastroBurden > 85) risks.add(
            HealthRisk(HealthRiskType.GASTROENTERITIS, "急性肠胃炎风险", "肠胃负担过重，可能引发急性肠胃炎")
        )
        if (body.toxinLevel > 80) risks.add(
            HealthRisk(HealthRiskType.TOXIC_OVERLOAD, "毒素过载", "体内毒素过高，免疫系统正在全力工作")
        )
        if (body.bloodSugar > 10.0) {
            risks.add(
                HealthRisk(HealthRiskType.HIGH_BLOOD_SUGAR, "高血糖预警", "血糖持续偏高，身体有负担")
            )
        }
        if (body.immuneLevel < 25) risks.add(
            HealthRisk(HealthRiskType.LOW_IMMUNITY, "免疫力低下", "免疫力很低，容易生病")
        )
        if (body.healthScore < 30) risks.add(
            HealthRisk(HealthRiskType.POOR_HEALTH, "身体状态很差", "综合健康评分很低，需要好好休养")
        )
        if (body.energy < 15) risks.add(
            HealthRisk(HealthRiskType.EXHAUSTION, "精疲力竭", "精力耗尽，身体需要休息")
        )

        return risks
    }
}
