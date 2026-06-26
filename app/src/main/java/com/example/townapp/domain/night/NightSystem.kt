package com.example.townapp.domain.night

import com.example.townapp.data.asset.GameText
import com.example.townapp.data.database.entity.NightStateEntity
import com.example.townapp.domain.engine.PlayerState
import kotlin.random.Random

object NightSystem {

    /**
     * 夜间状态判定结果
     */
    data class NightResult(
        val sleepStatus: String,
        val sleepQuality: Double,
        val sleepDurationMinutes: Int,
        val hasDream: Boolean,
        val dreamType: String,
        val dreamContent: String,
        val dreamEmoji: String,
        val nightMonoText: String,
        val monoEmotion: String,
        val energyRecovered: Double,
        val traumaReduced: Double,
        val anxietyReduced: Double
    )

    /**
     * 演算夜间状态（核心入口）
     */
    fun calculateNightState(player: PlayerState): NightResult {
        val fatigue = player.fatigue
        val trauma = player.trauma
        val anxiety = player.anxiety
        val workMinutes = player.dailyWorkMinutes
        val assets = player.assets
        val laborIncome = player.dailyLaborIncome
        val compoundIncome = player.dailyCompoundIncome

        val (sleepStatus, sleepQuality, sleepDuration) = determineSleepStatus(
            fatigue, trauma, anxiety, workMinutes, assets, laborIncome, compoundIncome
        )

        val (hasDream, dreamType, dreamContent, dreamEmoji) = generateDream(
            sleepStatus, sleepQuality, trauma, anxiety, fatigue
        )

        val (nightMonoText, monoEmotion) = generateMonologue(
            sleepStatus, trauma, anxiety, fatigue, assets, laborIncome
        )

        val (energyRecovered, traumaReduced, anxietyReduced) = calculateRecovery(
            sleepStatus, sleepQuality, sleepDuration
        )

        return NightResult(
            sleepStatus = sleepStatus,
            sleepQuality = sleepQuality,
            sleepDurationMinutes = sleepDuration,
            hasDream = hasDream,
            dreamType = dreamType,
            dreamContent = dreamContent,
            dreamEmoji = dreamEmoji,
            nightMonoText = nightMonoText,
            monoEmotion = monoEmotion,
            energyRecovered = energyRecovered,
            traumaReduced = traumaReduced,
            anxietyReduced = anxietyReduced
        )
    }

    /**
     * 判定睡眠状态
     */
    private fun determineSleepStatus(
        fatigue: Double,
        trauma: Double,
        anxiety: Double,
        workMinutes: Int,
        assets: Double,
        laborIncome: Double,
        compoundIncome: Double
    ): Triple<String, Double, Int> {
        var insomniaProbability = 0.0

        insomniaProbability += (fatigue / 100.0) * 0.3
        insomniaProbability += (trauma / 100.0) * 0.3
        insomniaProbability += (anxiety / 100.0) * 0.2

        if (workMinutes > 600) {
            insomniaProbability += 0.15
        } else if (workMinutes > 480) {
            insomniaProbability += 0.05
        }

        if (assets < 1000 && laborIncome < 100) {
            insomniaProbability += 0.15
        }

        if (assets > 50000 && workMinutes > 540) {
            insomniaProbability += 0.1
        }

        insomniaProbability = insomniaProbability.coerceIn(0.0, 1.0)

        val random = Random.nextDouble()

        return when {
            random < insomniaProbability * 0.6 -> Triple(
                NightStateEntity.SLEEP_STATUS_INSOMNIA,
                10.0 + Random.nextDouble() * 10.0,
                0
            )
            random < insomniaProbability -> Triple(
                NightStateEntity.SLEEP_STATUS_RESTLESS,
                30.0 + Random.nextDouble() * 20.0,
                240 + Random.nextInt(120)
            )
            fatigue < 30 && trauma < 20 && anxiety < 30 -> Triple(
                NightStateEntity.SLEEP_STATUS_DEEP_SLEEP,
                85.0 + Random.nextDouble() * 15.0,
                480 + Random.nextInt(120)
            )
            else -> Triple(
                NightStateEntity.SLEEP_STATUS_ASLEEP,
                50.0 + Random.nextDouble() * 30.0,
                360 + Random.nextInt(180)
            )
        }
    }

    /**
     * 生成梦境
     */
    private fun generateDream(
        sleepStatus: String,
        sleepQuality: Double,
        trauma: Double,
        anxiety: Double,
        fatigue: Double
    ): Quadruple<Boolean, String, String, String> {
        if (sleepStatus == NightStateEntity.SLEEP_STATUS_INSOMNIA) {
            return Quadruple(false, "", "", "")
        }

        val dreamProbability = when {
            sleepQuality > 80 -> 0.8
            sleepQuality > 50 -> 0.5
            sleepQuality > 30 -> 0.3
            else -> 0.1
        }

        if (Random.nextDouble() > dreamProbability) {
            return Quadruple(false, NightStateEntity.DREAM_TYPE_EMPTY, "", "")
        }

        val dreamType = when {
            trauma > 60 || anxiety > 70 -> NightStateEntity.DREAM_TYPE_NIGHTMARE
            trauma > 40 || anxiety > 50 -> NightStateEntity.DREAM_TYPE_NEGATIVE
            sleepQuality > 70 && fatigue < 30 -> NightStateEntity.DREAM_TYPE_PEACEFUL
            sleepQuality > 50 -> NightStateEntity.DREAM_TYPE_NOSTALGIC
            else -> NightStateEntity.DREAM_TYPE_CONFUSING
        }

        val (content, emoji) = getDreamContent(dreamType)

        return Quadruple(true, dreamType, content, emoji)
    }

    /**
     * 获取梦境内容文案
     */
    private fun getDreamContent(dreamType: String): Pair<String, String> {
        val mdDreamType = when (dreamType) {
            NightStateEntity.DREAM_TYPE_PEACEFUL -> "peaceful"
            NightStateEntity.DREAM_TYPE_NOSTALGIC -> "nostalgic"
            NightStateEntity.DREAM_TYPE_CONFUSING -> "confusing"
            NightStateEntity.DREAM_TYPE_NEGATIVE -> "negative"
            NightStateEntity.DREAM_TYPE_NIGHTMARE -> "nightmare"
            else -> return Pair("", "")
        }
        return Pair(
            GameText.randomDream(mdDreamType),
            GameText.dreamEmoji(mdDreamType)
        )
    }

    /**
     * 生成深夜独白（核心治愈文案）
     */
    private fun generateMonologue(
        sleepStatus: String,
        trauma: Double,
        anxiety: Double,
        fatigue: Double,
        assets: Double,
        laborIncome: Double
    ): Pair<String, String> {
        return when (sleepStatus) {
            NightStateEntity.SLEEP_STATUS_INSOMNIA -> generateInsomniaMonologue(trauma, anxiety, fatigue, assets, laborIncome)
            NightStateEntity.SLEEP_STATUS_RESTLESS -> generateRestlessMonologue(trauma, anxiety, fatigue)
            NightStateEntity.SLEEP_STATUS_DEEP_SLEEP -> generateDeepSleepMonologue()
            else -> generateNormalSleepMonologue(fatigue)
        }
    }

    /**
     * 失眠独白（共情式文案，无指责）
     */
    private fun generateInsomniaMonologue(
        trauma: Double,
        anxiety: Double,
        fatigue: Double,
        assets: Double,
        laborIncome: Double
    ): Pair<String, String> {
        val emotion = when {
            trauma > 50 -> NightStateEntity.MONO_EMOTION_SAD
            anxiety > 60 -> NightStateEntity.MONO_EMOTION_ANXIOUS
            fatigue > 70 -> NightStateEntity.MONO_EMOTION_LONELY
            else -> NightStateEntity.MONO_EMOTION_CALM
        }

        val monologue = GameText.randomNightMonologue(
            sleepStatus = "insomnia",
            emotion = emotion,
            fatigue = fatigue.toInt(),
            anxiety = anxiety.toInt(),
            trauma = trauma.toInt()
        )

        return Pair(monologue, emotion)
    }

    /**
     * 浅眠不安独白
     */
    private fun generateRestlessMonologue(trauma: Double, anxiety: Double, fatigue: Double): Pair<String, String> {
        val emotion = when {
            trauma > 40 -> NightStateEntity.MONO_EMOTION_SAD
            anxiety > 50 -> NightStateEntity.MONO_EMOTION_ANXIOUS
            else -> NightStateEntity.MONO_EMOTION_CALM
        }

        val monologue = GameText.randomNightMonologue(
            sleepStatus = "restless",
            emotion = emotion,
            fatigue = fatigue.toInt(),
            anxiety = anxiety.toInt(),
            trauma = trauma.toInt()
        )

        return Pair(monologue, emotion)
    }

    /**
     * 深度睡眠独白（治愈向）
     */
    private fun generateDeepSleepMonologue(): Pair<String, String> {
        val monologue = GameText.randomNightMonologue(
            sleepStatus = "deep",
            emotion = NightStateEntity.MONO_EMOTION_CALM
        )
        return Pair(monologue, NightStateEntity.MONO_EMOTION_CALM)
    }

    /**
     * 正常睡眠独白
     */
    private fun generateNormalSleepMonologue(fatigue: Double): Pair<String, String> {
        val monologue = GameText.randomNightMonologue(
            sleepStatus = "asleep",
            emotion = NightStateEntity.MONO_EMOTION_CALM,
            fatigue = fatigue.toInt()
        )
        return Pair(monologue, NightStateEntity.MONO_EMOTION_CALM)
    }

    /**
     * 计算睡眠修复效果
     */
    private fun calculateRecovery(
        sleepStatus: String,
        sleepQuality: Double,
        sleepDuration: Int
    ): Triple<Double, Double, Double> {
        val sleepHours = sleepDuration / 60.0

        return when (sleepStatus) {
            NightStateEntity.SLEEP_STATUS_DEEP_SLEEP -> Triple(
                sleepHours * 20.0,
                sleepHours * 8.0,
                sleepHours * 10.0
            )
            NightStateEntity.SLEEP_STATUS_ASLEEP -> Triple(
                sleepHours * 12.0,
                sleepHours * 3.0,
                sleepHours * 4.0
            )
            NightStateEntity.SLEEP_STATUS_RESTLESS -> Triple(
                sleepHours * 5.0,
                sleepHours * 1.0,
                sleepHours * 1.5
            )
            else -> Triple(
                sleepHours * 1.0,
                0.0,
                sleepHours * 0.5
            )
        }
    }

    /**
     * 转换为数据库实体
     */
    fun toNightStateEntity(
        result: NightResult,
        player: PlayerState,
        gameDay: Int,
        gameHour: Int,
        npcId: String = "player",
        npcName: String = "居民"
    ): NightStateEntity {
        return NightStateEntity(
            npcId = npcId,
            npcName = npcName,
            gameDay = gameDay,
            gameHour = gameHour,
            sleepStatus = result.sleepStatus,
            sleepDurationMinutes = result.sleepDurationMinutes,
            sleepQuality = result.sleepQuality,
            hasDream = result.hasDream,
            dreamType = result.dreamType,
            dreamContent = result.dreamContent,
            dreamEmoji = result.dreamEmoji,
            nightMonoText = result.nightMonoText,
            monoEmotion = result.monoEmotion,
            fatigueAtBedtime = player.fatigue,
            anxietyAtBedtime = player.anxiety,
            traumaAtBedtime = player.trauma,
            moneyAtBedtime = player.money,
            workMinutesToday = player.dailyWorkMinutes,
            energyRecovered = result.energyRecovered,
            traumaReduced = result.traumaReduced,
            anxietyReduced = result.anxietyReduced
        )
    }

    /**
     * 四元组辅助类
     */
    data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
}
