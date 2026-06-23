package com.example.townapp.domain.night

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
        return when (dreamType) {
            NightStateEntity.DREAM_TYPE_PEACEFUL -> Pair(
                listOf(
                    "梦到小时候在老家的院子里，阳光暖暖的，什么都不用做",
                    "梦见自己躺在草地上看云，风很轻，时间慢慢的",
                    "梦里有一片很安静的海，海水是蓝色的，没有声音",
                    "梦见和很久没见的朋友一起吃饭，大家都笑着，很温暖",
                    "梦到走在一条开满花的路上，脚步很轻，心里很安宁"
                ).random(),
                "🌤️"
            )
            NightStateEntity.DREAM_TYPE_NOSTALGIC -> Pair(
                listOf(
                    "梦见回到了高中教室，窗外的梧桐树叶沙沙响",
                    "梦里是小时候外婆家的厨房，有饭菜的香味",
                    "梦见第一次骑自行车，摔了但一点都不疼",
                    "梦里和爸爸妈妈一起吃年夜饭，电视在放春晚",
                    "梦到小时候养的那只猫，它还在，蹭了蹭我的手"
                ).random(),
                "🍂"
            )
            NightStateEntity.DREAM_TYPE_CONFUSING -> Pair(
                listOf(
                    "梦到在一个很长很长的走廊里，门一扇扇打开又关上",
                    "梦里的路总是走不完，明明很近却到不了",
                    "梦见自己在说话，但声音怎么也发不出来",
                    "梦里的人都很模糊，想看清却总是看不清楚",
                    "梦到时间一直在循环，同样的事情重复发生"
                ).random(),
                "🌫️"
            )
            NightStateEntity.DREAM_TYPE_NEGATIVE -> Pair(
                listOf(
                    "梦里一直在赶一个永远赶不上的deadline",
                    "梦见手里的东西一直在往下掉，怎么抓都抓不住",
                    "梦里很吵，很多声音在说话，却一句都听不懂",
                    "梦见自己站在很高的地方，脚下是空的",
                    "梦里的灯一直在闪，忽明忽暗的，很不安"
                ).random(),
                "🌧️"
            )
            NightStateEntity.DREAM_TYPE_NIGHTMARE -> Pair(
                listOf(
                    "梦到自己被困在一个房间里，门怎么都打不开",
                    "梦见一直在被什么东西追，跑不动，也喊不出来",
                    "梦里有人一直在质问我，说我做得不够好",
                    "梦见自己从很高的地方掉下来，一直往下掉",
                    "梦里的世界一直在崩塌，什么都留不住"
                ).random(),
                "🌑"
            )
            else -> Pair("", "")
        }
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
        val monologue = if (trauma > 50 || anxiety > 60) {
            listOf(
                "今天实在太累了，睡不着也没关系，不用强迫自己好好生活。",
                "脑子里很乱，想的事情太多了。允许自己难过一会儿，没关系的。",
                "这样的夜晚，很多人都经历过。你不是一个人在撑着。",
                "累了就歇一歇，不需要马上好起来。慢慢来，小镇陪着你。",
                "睡不着也不是你的错，是身体在提醒你需要关注它了。"
            ).random()
        } else if (assets < 1000 && laborIncome < 100) {
            listOf(
                "明天还要上班，钱却不够花。这样的日子，真的很难熬。",
                "想好好休息，但生活不允许。没关系，熬过去就好了。",
                "夜深了，明天的事情明天再说。现在，让自己稍微松一口气吧。",
                "口袋里的钱不多，心里的事不少。今晚，先放过自己。",
                "日子很难，但你还在坚持。这样的你，已经很了不起了。"
            ).random()
        } else if (fatigue > 70) {
            listOf(
                "身体在喊累了，大脑却停不下来。闭上眼睛，什么都不想，只是呼吸。",
                "今天工作了很久，身体已经透支了。睡不着也没关系，躺着也是休息。",
                "很累很累，但就是睡不着。允许自己这样，不需要觉得抱歉。",
                "身体和大脑不在同一个频道。没关系，它们都需要时间调整。",
                "今天辛苦了。睡不着也不用强迫，这样躺着就好。"
            ).random()
        } else {
            listOf(
                "夜深了，还没睡。心里有点空，但也很安静。",
                "睡不着的夜晚，听听自己的呼吸声。这样也很好。",
                "不用急着睡着，今晚就当是和自己独处的时间。",
                "夜色很美，哪怕只是看看窗外的月光，也是好的。",
                "今天已经结束了，明天还没开始。现在，只属于你自己。"
            ).random()
        }

        val emotion = when {
            trauma > 50 -> NightStateEntity.MONO_EMOTION_SAD
            anxiety > 60 -> NightStateEntity.MONO_EMOTION_ANXIOUS
            fatigue > 70 -> NightStateEntity.MONO_EMOTION_LONELY
            else -> NightStateEntity.MONO_EMOTION_CALM
        }

        return Pair(monologue, emotion)
    }

    /**
     * 浅眠不安独白
     */
    private fun generateRestlessMonologue(trauma: Double, anxiety: Double, fatigue: Double): Pair<String, String> {
        val monologue = when {
            trauma > 40 -> listOf(
                "睡得不沉，梦里有些乱。醒了也好，喝杯水，继续睡。",
                "半梦半醒之间，有些事情在脑子里转。没关系，天亮了再说。",
                "身体在休息，脑子却还在跑。让它跑吧，累了自然会停。",
                "夜里醒了好几次，睡眠很浅。但至少，你在休息了。",
                "这样的夜晚，有点难熬，但也会过去的。"
            ).random()
            anxiety > 50 -> listOf(
                "心里有点慌，睡不安稳。深呼吸，慢慢会好的。",
                "很多事情悬在心里，放不下。没关系，先放一放。",
                "半睡半醒中，总觉得有什么事没做完。其实，已经够了。",
                "焦虑像一只小猫，在心里挠啊挠。让它挠吧，不会有事的。",
                "夜里总是醒，心里不踏实。明天再说，现在好好躺着。"
            ).random()
            else -> listOf(
                "睡得不深，但也在休息。这样就很好。",
                "夜里醒了一次，看了看时间，还早。继续睡吧。",
                "睡眠很浅，但至少闭上了眼睛。这样就够了。",
                "半梦半醒的状态，有点奇怪，但也很安静。",
                "夜里有点冷，盖好被子。明天又是新的一天。"
            ).random()
        }

        val emotion = when {
            trauma > 40 -> NightStateEntity.MONO_EMOTION_SAD
            anxiety > 50 -> NightStateEntity.MONO_EMOTION_ANXIOUS
            else -> NightStateEntity.MONO_EMOTION_CALM
        }

        return Pair(monologue, emotion)
    }

    /**
     * 深度睡眠独白（治愈向）
     */
    private fun generateDeepSleepMonologue(): Pair<String, String> {
        val monologue = listOf(
            "今晚睡得很沉，好像什么都放下了。真好。",
            "一夜无梦，醒来的时候心里很平静。",
            "睡得很安稳，身体和心灵都得到了休息。",
            "这一夜，没有打扰，只有安静。",
            "好好睡了一觉，感觉整个人都轻盈了。",
            "梦里很温暖，醒来的时候心情很好。",
            "没有什么事情需要急着做。这样的夜晚，很珍贵。",
            "身体在修复，心里在放松。这样就很好。"
        ).random()

        return Pair(monologue, NightStateEntity.MONO_EMOTION_CALM)
    }

    /**
     * 正常睡眠独白
     */
    private fun generateNormalSleepMonologue(fatigue: Double): Pair<String, String> {
        val monologue = if (fatigue > 50) {
            listOf(
                "今天有点累，躺下很快就睡着了。",
                "睡前有点担心明天的事，但睡着了就忘了。",
                "身体有点沉，睡眠还可以。",
                "今天的疲惫，在睡眠里慢慢消散了。",
                "一觉醒来，感觉比睡前轻松了一些。"
            ).random()
        } else {
            listOf(
                "今晚睡得还可以，不算特别沉，但也够了。",
                "没有什么特别的梦，平平淡淡的一夜。",
                "躺下没多久就睡着了，挺好的。",
                "夜里醒了一次，但很快又睡着了。",
                "睡眠质量一般，但身体得到了休息。"
            ).random()
        }

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
