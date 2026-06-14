package com.example.townapp.business

import com.example.townapp.data.database.entity.LifePathType
import com.example.townapp.data.database.entity.UserBodyState
import com.example.townapp.data.database.entity.UserMentalState

object FatigueBusiness {

    enum class RecoveryType {
        SLEEP, LEISURE, FOOD, WALK, SOCIAL
    }

    private object FatigueConfig {
        
        val bodySymptoms = listOf(
            SymptomConfig(threshold = 70, message = "持续性腰酸背痛"),
            SymptomConfig(threshold = 80, message = "入睡困难"),
            SymptomConfig(threshold = 85, message = "心悸或头晕"),
            SymptomConfig(threshold = 90, message = "免疫力明显下降")
        )

        val bodyImpactCoefficients = BodyImpactCoefficients(
            energyFactor = -1.0 / 20,
            healthThreshold = 80,
            healthDelta = -1,
            immuneThreshold = 70,
            immuneDelta = -1,
            moodFactor = -1.0 / 25,
            comfortFactor = -1.0 / 30
        )

        val mentalImpactCoefficients = MentalImpactCoefficients(
            anxietyFactor = 1.0 / 20,
            happinessFactor = -1.0 / 25,
            sleepQualityFactor = -1.0 / 20,
            workStressFactor = 1.0 / 20,
            burnoutThreshold = 75,
            burnoutHighDelta = 2,
            burnoutLowDelta = 1,
            controlFactor = -1.0 / 30
        )

        val recoveryActivities = mapOf(
            RecoveryType.SLEEP to RecoveryConfig(
                fatigueRecovery = 30,
                energyRecovery = 25,
                moodBoost = 10,
                costMoney = 0.0,
                description = "你好好睡了一觉，身体像被重启了一样"
            ),
            RecoveryType.LEISURE to RecoveryConfig(
                fatigueRecovery = 15,
                energyRecovery = 10,
                moodBoost = 15,
                costMoney = 0.0,
                description = "你放松了一会儿，心情好了一些"
            ),
            RecoveryType.FOOD to RecoveryConfig(
                fatigueRecovery = 10,
                energyRecovery = 15,
                moodBoost = 20,
                costMoney = 50.0,
                description = "你吃到了喜欢的东西，胃和心情都暖了"
            ),
            RecoveryType.WALK to RecoveryConfig(
                fatigueRecovery = 20,
                energyRecovery = 10,
                moodBoost = 12,
                costMoney = 0.0,
                description = "你出去走了走，风把脑子里的雾吹散了一些"
            ),
            RecoveryType.SOCIAL to RecoveryConfig(
                fatigueRecovery = 15,
                energyRecovery = 10,
                moodBoost = 25,
                costMoney = 30.0,
                description = "和朋友聊了聊，压在心里的东西轻了一半"
            )
        )

        val longTermDamages = listOf(
            DamageConfig(
                overtimeThreshold = 1000.0,
                ageThreshold = null,
                message = "慢性腰肌劳损"
            ),
            DamageConfig(
                overtimeThreshold = 2000.0,
                ageThreshold = null,
                message = "颈椎退行性变"
            ),
            DamageConfig(
                overtimeThreshold = null,
                ageThreshold = 35,
                fatigueThreshold = 80,
                message = "高血压前期"
            ),
            DamageConfig(
                overtimeThreshold = 3000.0,
                ageThreshold = 40,
                message = "心脏负荷过重"
            )
        )
    }

    data class SymptomConfig(
        val threshold: Int,
        val message: String
    )

    data class BodyImpactCoefficients(
        val energyFactor: Double,
        val healthThreshold: Int,
        val healthDelta: Int,
        val immuneThreshold: Int,
        val immuneDelta: Int,
        val moodFactor: Double,
        val comfortFactor: Double
    )

    data class MentalImpactCoefficients(
        val anxietyFactor: Double,
        val happinessFactor: Double,
        val sleepQualityFactor: Double,
        val workStressFactor: Double,
        val burnoutThreshold: Int,
        val burnoutHighDelta: Int,
        val burnoutLowDelta: Int,
        val controlFactor: Double
    )

    data class RecoveryConfig(
        val fatigueRecovery: Int,
        val energyRecovery: Int,
        val moodBoost: Int,
        val costMoney: Double,
        val description: String
    )

    data class DamageConfig(
        val overtimeThreshold: Double? = null,
        val ageThreshold: Int? = null,
        val fatigueThreshold: Int? = null,
        val message: String
    )

    fun getAdjustedWorkHours(baseHours: Double, lifePath: LifePathType): Double {
        return when (lifePath) {
            LifePathType.HUSTLE -> baseHours + 2.0
            LifePathType.BALANCED -> baseHours
            LifePathType.REST -> (baseHours - 2.0).coerceAtLeast(4.0)
        }
    }

    fun getAdjustedMonthlyIncome(income: Double, type: LifePathType): Double {
        return when (type) {
            LifePathType.HUSTLE -> income * 1.2
            LifePathType.BALANCED -> income
            LifePathType.REST -> income * 0.7
        }
    }

    fun calculateBodyImpact(body: UserBodyState): BodyImpactResult {
        val fatigue = body.fatigueLevel
        
        val symptoms = FatigueConfig.bodySymptoms
            .filter { fatigue > it.threshold }
            .map { it.message }

        val config = FatigueConfig.bodyImpactCoefficients
        
        return BodyImpactResult(
            systolicSymptoms = symptoms,
            energyDelta = (fatigue * config.energyFactor).toInt(),
            healthDelta = if (fatigue > config.healthThreshold) config.healthDelta else 0,
            immuneDelta = if (fatigue > config.immuneThreshold) config.immuneDelta else 0,
            moodDelta = (fatigue * config.moodFactor).toInt(),
            comfortDelta = (fatigue * config.comfortFactor).toInt()
        )
    }

    fun calculateMentalImpact(body: UserBodyState, mental: UserMentalState): MentalImpactResult {
        val fatigue = body.fatigueLevel
        val config = FatigueConfig.mentalImpactCoefficients
        
        return MentalImpactResult(
            anxietyDelta = (fatigue * config.anxietyFactor).toInt(),
            happinessDelta = (fatigue * config.happinessFactor).toInt(),
            sleepQualityDelta = (fatigue * config.sleepQualityFactor).toInt(),
            workStressDelta = (fatigue * config.workStressFactor).toInt(),
            burnoutRiskDelta = if (fatigue > config.burnoutThreshold) config.burnoutHighDelta else config.burnoutLowDelta,
            controlDelta = (fatigue * config.controlFactor).toInt()
        )
    }

    fun calculateRecovery(type: RecoveryType, body: UserBodyState): RecoveryActivityResult {
        val config = FatigueConfig.recoveryActivities[type]
            ?: throw IllegalArgumentException("Unknown recovery type: $type")
        
        return RecoveryActivityResult(
            fatigueRecovery = config.fatigueRecovery,
            energyRecovery = config.energyRecovery,
            moodBoost = config.moodBoost,
            costMoney = config.costMoney,
            description = config.description
        )
    }

    fun assessLongTermDamage(body: UserBodyState, age: Int): List<String> {
        return FatigueConfig.longTermDamages.filter { config ->
            val overtimeOk = config.overtimeThreshold?.let { body.totalOvertimeHours > it } ?: true
            val ageOk = config.ageThreshold?.let { age > it } ?: true
            val fatigueOk = config.fatigueThreshold?.let { body.fatigueLevel > it } ?: true
            overtimeOk && ageOk && fatigueOk
        }.map { it.message }
    }

    fun getSeasonalDescription(month: Int): String {
        return when (month) {
            in 3..5 -> "春天来了，天亮得越来越早，人容易犯困也容易有精神"
            in 6..8 -> "夏天昼长夜短，高温让人更容易疲劳，中午尽量眯一会儿"
            in 9..11 -> "秋天气温舒适，是一年中最适合集中精力干活的季节"
            in 12..2 -> "冬天昼短夜长，天黑得早，人自然想多睡会儿"
            else -> ""
        }
    }
}

data class BodyImpactResult(
    val systolicSymptoms: List<String>,
    val energyDelta: Int,
    val healthDelta: Int,
    val immuneDelta: Int,
    val moodDelta: Int,
    val comfortDelta: Int
)

data class MentalImpactResult(
    val anxietyDelta: Int,
    val happinessDelta: Int,
    val sleepQualityDelta: Int,
    val workStressDelta: Int,
    val burnoutRiskDelta: Int,
    val controlDelta: Int
)

data class RecoveryActivityResult(
    val fatigueRecovery: Int,
    val energyRecovery: Int,
    val moodBoost: Int,
    val costMoney: Double,
    val description: String
)