package com.example.townapp.business

import com.example.townapp.data.ChildBehaviorScene
import com.example.townapp.data.CollectiveEventType
import com.example.townapp.data.CrossProfessionDialogue
import com.example.townapp.data.EraEmploymentConfigs
import com.example.townapp.data.EraInflationConfig
import com.example.townapp.data.GenerationalCostGap
import com.example.townapp.data.HobbyTier
import com.example.townapp.data.IndustryLifecycle
import com.example.townapp.data.IndustryLifecycles
import com.example.townapp.data.ResearchCareerBranch
import com.example.townapp.data.ResearcherOrigin
import com.example.townapp.data.ResearcherOriginConfigs
import com.example.townapp.data.ScalableHobbyConfig
import com.example.townapp.data.AcademicFaction
import com.example.townapp.data.AcademicMonopolyImpact
import com.example.townapp.data.BeliefErosionEvent
import com.example.townapp.data.BeliefMilestone
import com.example.townapp.data.BeliefState
import com.example.townapp.data.ClassBackground
import com.example.townapp.data.ClassBehaviorState
import com.example.townapp.data.CrossClassEncounter
import com.example.townapp.data.DailyFrustration
import com.example.townapp.data.EliteBehaviorMode
import com.example.townapp.data.EncounterOutcome
import com.example.townapp.data.FrustrationCoping
import com.example.townapp.data.GenerationalChoice
import com.example.townapp.data.GenerationalDialogue
import com.example.townapp.data.GenerationalResponseMode
import com.example.townapp.data.PostnatalShiftEvent
import com.example.townapp.data.PowerBehaviorMode
import com.example.townapp.data.PsychologicalCore
import com.example.townapp.data.ReformAction
import com.example.townapp.data.ReformResult
import com.example.townapp.data.ReformerForm
import com.example.townapp.data.ReformerUnlockState
import com.example.townapp.data.AtomizedPsychEvent
import com.example.townapp.data.LateLifeNarrative
import com.example.townapp.data.MentalHealthLevel
import com.example.townapp.data.WithdrawalConsequence
import com.example.townapp.data.WithdrawalMode
import com.example.townapp.data.WithdrawalState
import com.example.townapp.data.WithdrawalTriggerEvent
import com.example.townapp.data.LoveEvent
import com.example.townapp.data.LoveStatus
import com.example.townapp.data.LovePlayerChoice
import com.example.townapp.data.HeartbreakPhase
import com.example.townapp.data.AppearanceLevel
import com.example.townapp.data.AppearancePsychEvent
import com.example.townapp.data.ClothingHealthEvent
import com.example.townapp.data.ClothingSocialEvent
import com.example.townapp.data.ClothingQuality
import com.example.townapp.data.ClothingCleanliness
import com.example.townapp.data.FootCondition
import com.example.townapp.data.TraumaEntry
import com.example.townapp.data.TraumaCategory
import com.example.townapp.data.TraumaType
import com.example.townapp.data.PastSelfDialogueEvent
import com.example.townapp.data.PastSelfDialogueChoice
import com.example.townapp.data.PsychDrain
import com.example.townapp.data.TechGlobalImpact
import com.example.townapp.data.database.entity.LifePathType
import com.example.townapp.data.FabricType
import com.example.townapp.data.DetergentType
import com.example.townapp.data.HouseholdProductType
import com.example.townapp.data.DrugCategory
import com.example.townapp.data.DrugEffect
import com.example.townapp.data.MenstrualEvent
import com.example.townapp.data.GenderCourtshipEvent
import com.example.townapp.data.GenderCareerEvent
import com.example.townapp.data.DiseaseType
import com.example.townapp.data.ActiveDisease
import com.example.townapp.data.DrugTier
import com.example.townapp.data.SpecificDrug
import com.example.townapp.data.DrugUsageCategory
import com.example.townapp.data.TreatmentRoute
import com.example.townapp.data.NutritionLevel
import com.example.townapp.data.TraditionalSaying
import com.example.townapp.data.model.IdiomData
import com.example.townapp.data.SayingCategory

/**
 * 温柔文案引擎 —— 小镇的灵魂设计
 *
 * 核心原则：
 * - 永远只陈述事实，不评判好坏
 * - 负面状态只说后果，不说对错
 * - 不用"警告""危险""低下"，用"你有点…""你最近…"
 * - 任何选择都不贴"好/坏"标签
 * - 小镇的爱，从来不说"我爱你"，只做"看见你、接住你、陪着你"的事
 */
object GentleTextProvider {

    // ============================================
    // 时间描述
    // ============================================
    fun timeOfDay(hour: Int): String = when {
        hour in 5..7 -> "清晨的阳光刚刚照进来"
        hour in 8..10 -> "上午的时光安静而明亮"
        hour in 11..13 -> "午间的光线暖暖的"
        hour in 14..16 -> "午后，时间慢慢流淌"
        hour in 17..19 -> "傍晚，天色渐渐暗下来"
        hour in 20..22 -> "夜晚，房间亮着一盏小灯"
        else -> "夜深了，世界很安静"
    }

    fun timeEmoji(hour: Int): String = when {
        hour in 5..7 -> "🌅"
        hour in 8..10 -> "☀️"
        hour in 11..13 -> "🌤️"
        hour in 14..16 -> "🌿"
        hour in 17..19 -> "🌆"
        hour in 20..22 -> "🌙"
        else -> "✨"
    }

    // ============================================
    // 身体状态描述 —— 陈述事实，加一点温柔的看见
    // ============================================
    fun describeHunger(satiety: Int): String = when {
        satiety < 10 -> "你很久没吃东西了，胃里空空的"
        satiety < 20 -> "你有点饿了"
        satiety < 30 -> "肚子开始轻声提醒你"
        satiety < 50 -> "不算饿，但也该吃点东西了"
        satiety < 70 -> "刚刚好，不饿也不撑"
        satiety < 90 -> "吃饱了，很满足"
        else -> "吃得很舒服"
    }

    fun describeEnergy(energy: Int): String = when {
        energy < 10 -> "你已经很累了，眼皮在打架"
        energy < 20 -> "你有点困了"
        energy < 30 -> "精力不太够了，歇一会会更好"
        energy < 50 -> "还有力气，但不算充沛"
        energy < 70 -> "状态还行，能再撑一阵"
        energy < 90 -> "精神不错，脑子很清醒"
        else -> "精力充沛，浑身都是力气"
    }

    fun describeHealth(health: Int): String = when {
        health < 20 -> "身体在提醒你要好好休息一下"
        health < 40 -> "你最近好像很累"
        health < 60 -> "身体状态一般，说不上好也说不上坏"
        health < 80 -> "身体状况还不错"
        else -> "身体感觉很舒服"
    }

    fun describeMood(mood: Int): String = when {
        mood < 20 -> "心情很不好，这没什么"
        mood < 40 -> "今天心情平平，不好也不坏"
        mood < 60 -> "心情还行，过得去"
        mood < 80 -> "心情还不错"
        else -> "心情很好，天空都是亮的"
    }

    // ============================================
    // 精神状态描述
    // ============================================
    fun describeMental(overallMental: Int): String = when {
        overallMental < 20 -> "你最近压力很大，撑了很久了"
        overallMental < 40 -> "心里有些沉甸甸的"
        overallMental < 60 -> "情绪还算稳定，没有大起大落"
        overallMental < 80 -> "心态不错，生活有掌控感"
        else -> "内心很丰盈，对生活充满热情"
    }

    // ============================================
    // 存款描述 —— 不恐吓，只陈述
    // ============================================
    fun describeSavings(savings: Double, monthlyRent: Double): String = when {
        savings < monthlyRent -> "存款不多了"
        savings < monthlyRent * 3 -> "存款够用一阵子"
        savings < monthlyRent * 6 -> "存款还算踏实"
        savings < monthlyRent * 12 -> "存款挺稳的"
        else -> "存款很充裕，心里踏实"
    }

    // ============================================
    // 场景描述 —— 根据居住环境和时间段
    // ============================================
    fun describeScene(
        hour: Int,
        roomName: String,
        light: Int,
        areaSqm: Int
    ): String {
        val timeDesc = timeOfDay(hour)
        val spaceDesc = when {
            areaSqm < 15 && light < 30 -> "你的房间不大，光线也暗了些，但这是属于你的小空间"
            areaSqm < 15 -> "房间虽小，但收拾得很整齐"
            light < 30 -> "房间光线不太足，但安静温暖"
            light > 70 -> "阳光落在地板上，房间亮堂堂的"
            else -> "房间里安安静静的，很舒服"
        }
        return "$timeDesc。$spaceDesc。"
    }

    // ============================================
    // 事件记录文案 —— 不说教
    // ============================================
    fun describeAutoEat(foodName: String, cost: Double): String =
        "你自动吃了一份${foodName}，花了${String.format("%.1f", cost)}元"

    fun describeAutoSleep(): String =
        "夜深了，你自动睡了一觉"

    fun describeAutoRest(): String =
        "身体有些疲惫，你自动休息了一会儿"

    fun describeManualEat(foodName: String, cost: Double): String =
        "你吃了一份${foodName}，花了${String.format("%.1f", cost)}元"

    fun describeManualSleep(): String =
        "你睡了一觉，醒来感觉好多了"

    fun describeManualRest(): String =
        "你休息了一会儿，身体轻松了一些"

    // ============================================
    // 状态恢复文案
    // ============================================
    fun describeSatietyRecovery(amount: Int): String =
        "饱腹感恢复了${amount}点"

    fun describeEnergyRecovery(amount: Int): String =
        "精力恢复了${amount}点"

    fun describeHealthRecovery(amount: Int): String =
        "健康值恢复了${amount}点"

    fun describeMoodChange(delta: Int): String = when {
        delta > 0 -> "心情好了${delta}点"
        delta < 0 -> "心情降了${-delta}点"
        else -> "心情没什么变化"
    }

    // ============================================
    // 状态条标签（极简、无评判）
    // ============================================
    fun satietyLabel(value: Int): String = when {
        value < 20 -> "空腹"
        value < 40 -> "有点饿"
        value < 70 -> "正常"
        value < 90 -> "饱了"
        else -> "很饱"
    }

    fun energyLabel(value: Int): String = when {
        value < 20 -> "很累"
        value < 40 -> "有点困"
        value < 70 -> "还好"
        value < 90 -> "精神"
        else -> "充沛"
    }

    fun healthLabel(value: Int): String = when {
        value < 30 -> "需要休息"
        value < 60 -> "一般"
        value < 80 -> "还好"
        else -> "健康"
    }

    fun moodLabel(value: Int): String = when {
        value < 30 -> "低落"
        value < 50 -> "平平"
        value < 70 -> "还行"
        value < 85 -> "不错"
        else -> "开心"
    }

    // ============================================
    // 🌟 一、把爱落在一饭一息 —— 日常行为反馈
    // 不喊口号，只陈述事实。你好好吃饭、好好睡觉、舍得给自己买小快乐，
    // 就是在对自己说「我爱你」。小镇只负责帮你看见这件事。
    // ============================================

    /** 吃饭时的温柔反馈，根据食物的不同给出不同风格的陪伴文案 */
    fun describeMealLove(foodName: String, foodNote: String): String {
        val basePool = listOf(
            "你好好吃了一顿${foodName}。胃暖了，人也安心了一些。",
            "${foodName}的味道刚刚好。你有好好喂饱自己。",
            "吃完${foodName}，你觉得舒服多了。对自己好，不用等别人来。",
            "今天的${foodName}很对胃口。给自己做顿饭、点份外卖，都是对自己说「我在乎你」。"
        )
        // 根据食物备注加入特化文案
        val specialPool = when {
            foodNote.contains("蛋") -> listOf(
                "今天的蛋煮得刚刚好。你有好好喂饱自己。",
                "加了个蛋，这顿饭就有了灵魂。你舍得对自己好。"
            )
            foodNote.contains("面") -> listOf(
                "热乎乎的一碗面下肚，整个人都暖和了。很简单，但很实在。",
                "面条软软的，汤热热的。你把自己照顾得很好。"
            )
            foodName.contains("可乐") || foodName.contains("零食") || foodName.contains("糖") ->
                listOf(
                    "气泡在嘴里炸开的瞬间，你开心了一下。这点快乐很重要。",
                    "偶尔吃点小零食，不用有负担。快乐本身就是营养。"
                )
            else -> emptyList()
        }
        return (specialPool + basePool).random()
    }

    /** 休息/睡觉后的温柔反馈 */
    fun describeRest(type: String): String {
        return when (type) {
            "night" -> listOf(
                "深夜了，好好睡一觉吧。身体需要休息，你也是。",
                "夜深了，该休息了。明天的阳光会等你。",
                "入睡了。梦里没有焦虑，只有平静。"
            ).random()
            "day_frequent" -> listOf(
                "白天休息了一下。不用一直忙碌，允许自己喘口气。",
                "打了个盹。虽然白天休息有点心虚，但身体确实需要。",
                "休息了一会儿。白天小憩也不错，别太自责。"
            ).random()
            else -> listOf(
                "休息了一下。身体状态好多了。",
                "睡了一会儿，感觉精神恢复了不少。",
                "好好休息了一场，身体在慢慢修复。"
            ).random()
        }
    }

    fun describeRestLove(): String = listOf(
        "你睡了一觉。醒来的时候，身体轻了很多。你有好好照顾自己。",
        "休息了一下，精神好多了。不用一直撑着，累了就歇。",
        "脚暖乎乎的。你今天也有好好照顾自己。",
        "好好睡了一觉。睡眠是最好的修复，你给了自己这份礼物。"
    ).random()

    /** 发呆/什么都不做的温柔反馈 */
    fun describeIdleLove(): String = listOf(
        "什么都没做的下午，风从窗边吹过。你允许自己浪费时间，这就是对自己的温柔。",
        "发了一会呆。不用每时每刻都有意义，存在本身就很好。",
        "你什么都没做，就静静地待着。这样也很好。"
    ).random()

    fun describeWork(): String = listOf(
        "完成了一天的工作。你认真对待自己的职责，这份踏实感很珍贵。",
        "工作结束了。你用劳动换取生活，这本身就是值得尊重的事。",
        "今天也好好工作了。一步一步往前走，你正在积累属于自己的力量。"
    ).random()

    fun describeStudy(): String = listOf(
        "学习了一些新东西。保持好奇心，你在成为更好的自己。",
        "今天也有进步一点。慢慢来，知识会慢慢积累成属于你的智慧。",
        "沉浸在学习中。这种专注的感觉，让你离目标又近了一步。"
    ).random()

    fun describeIdle(): String = listOf(
        "发了一会呆。不用每时每刻都忙碌，偶尔放空也很重要。",
        "什么都没做，只是安静地待着。这样的时光，也是生活的一部分。",
        "让大脑休息一下。有时候，什么都不做就是最好的充电。"
    ).random()

    fun describeGoOut(): String = listOf(
        "出门走了走。阳光很好，空气很清新，心情也变得舒畅起来。",
        "外面的世界很精彩。散步回来，感觉整个人都轻松了。",
        "出去透透气。换个环境，换个心情，挺好的。"
    ).random()

    /** 买小东西的温柔反馈 */
    fun describePurchaseLove(itemName: String): String = listOf(
        "你买了${itemName}。想要就买了，不用犹豫，你值得。",
        "${itemName}买到手了。给自己花点小钱，是爱自己最直接的方式。",
        "你终于买了想了很久的${itemName}。满足自己一个小心愿，不用理由。"
    ).random()

    // ============================================
    // 🌟 二、物品陪伴里的爱 —— 日久生情
    // 物品用得越久，越有陪伴的温度。
    // 对物品的珍惜，本质也是爱——爱那些好好对待过你的东西，也爱那个认真生活的自己。
    // ============================================

    /** 根据使用次数生成物品陪伴文案 */
    fun describeItemCompanion(itemName: String, usageCount: Int): String = when {
        usageCount > 100 -> "${itemName}陪了你很久了。它见过你高兴的样子，也见过你难过的样子，一直安安静静待在你身边。"
        usageCount > 50 -> "领口有点松了，但你还是喜欢穿它。它陪你度过了很多松弛的日子。"
        usageCount > 30 -> "${itemName}用了${usageCount}次。你们已经很熟了，不需要磨合，顺手就是最好的关系。"
        usageCount > 10 -> "${itemName}用了一些日子了。有些东西，越久越有感情。"
        else -> "${itemName}还是新的。慢慢来，日子还长。"
    }

    /** 特定类型物品的深度陪伴文案 */
    fun describeSpecialItemCompanion(itemName: String, usageCount: Int): String {
        return when {
            itemName.contains("耳机") && usageCount > 80 ->
                "你听过很多歌，也熬过很多难捱的夜晚。它一直安安静静陪着你。"
            itemName.contains("泡面") && usageCount > 30 ->
                "很多赶时间的晚上，都是它填饱你的肚子。不起眼，但很靠谱。"
            itemName.contains("杯") && usageCount > 60 ->
                "它装过冷水、热水、茶叶、咖啡，装过你每一个寻常的日子。"
            itemName.contains("枕头") && usageCount > 40 ->
                "它接住过你很多个疲惫的夜晚。有时候什么也不用做，只是好好躺着，就很好了。"
            else -> describeItemCompanion(itemName, usageCount)
        }
    }

    // ============================================
    // 🌟 三、童年回溯里的爱 —— 迟来的温柔
    // 不说「我爱小时候的你」，只做具体的事。
    // 所有的话都点到为止，不煽情、不戳泪。
    // 但做这件事的人会懂：这就是隔着时光，对自己说「我爱你」。
    // ============================================

    /** 给小时候的自己送零食 */
    fun childhoodGiftSnacks(): String =
        "那个站在超市货架前不敢伸手的小孩，今天终于可以随便拿了。"

    /** 告诉小时候的自己不用太懂事 */
    fun childhoodPermissionToBeSelf(): String =
        "你不用总让着别人，不用总怕惹人生气。你可以优先照顾自己。"

    /** 给小时候的自己一个安全的房间 */
    fun childhoodSafeRoom(): String =
        "以后你有自己的地方了。不用怕，不用逃，安安心心待着就好。"

    /** 对小时候自己的通用温柔话语 */
    fun childhoodGeneralLove(): List<String> = listOf(
        "抱了抱小时候的自己。你那时候已经很努力了，真的。",
        "告诉小时候的自己：考不好也没关系，你已经做得很好了。",
        "和小时候的自己并肩坐了一会儿。什么也没说，但都懂了。",
        "陪小时候的自己玩了一下午。你太久没有只是单纯地玩了，今天不用想明天的事。"
    )

    /** 代际伤害场景：告诉小时候的自己的温柔话语 */
    fun childhoodHealFromShouting(): List<String> = listOf(
        "找到了那个缩在角落里不敢出声的小孩。蹲下来，轻轻说：不是你的错。",
        "告诉小时候的自己：你不需要为他们的情绪负责。你只是个孩子。",
        "那个被吼到发抖的小孩，今天终于可以不用害怕了。没有人会突然对你发火。",
        "小时候你总想变得更好更乖，以为这样就不会被骂了。但现在你知道了，那本来就不是你的错。",
        "你不用那么小心翼翼的。你可以放松，可以犯错，可以做自己。这里没有人会吼你。"
    )

    /** 给小时候自己的安全空间描述 */
    fun childhoodSafeSpace(): String =
        "你给他收拾了一间小房间。门可以关上，外面的人进不来。想安静就安静，想哭就哭，想发呆就发呆。没有人会突然推门进来骂你。这是你的地方，永远安全。"

    /** 对长大后的自己的温柔告诫 */
    fun remindNotSelfBlame(): String = listOf(
        "你又开始骂自己了。那个声音是借来的，不是你自己的。你可以把它放下来。",
        "犯错是正常的，不完美是正常的。你不用给自己打那么低的分。",
        "你已经不需要再用他们对待你的方式对待自己了。你是你自己的大人了，你可以选择温柔。",
        "停下来，深呼吸。不用急着批判自己。你做的事有原因，你的感受有道理。"
    ).random()

    // ============================================
    // 🌟 四、低谷时接住你 —— 真正的爱是兜底
    // 不评判、不说教、不灌鸡汤，只是告诉你：你这样也可以，我陪着你。
    // 无论你混得好不好，都不会被嫌弃。这就是小镇对每个人的爱。
    // ============================================

    /** 存款见底 */
    fun comfortLowSavings(): String =
        "我知道你现在有点慌。没关系，很多人都有过这样的时候。我们慢慢想办法，总会过去的。"

    /** 简朴生活 —— 省钱不是错，是被看见的挣扎 */
    fun comfortSimpleLife(): String = listOf(
        "你用一块钱给自己撑住了一天。这不是寒酸，是你在认真活着。",
        "能省则省的日子里，每一分钱都花得小心翼翼。这种谨慎本身就是一种力量。",
        "别人看到的是馒头和素菜，我看到的是你在用力把日子过下去。",
        "不用去比别人的餐桌。你能让自己吃饱，已经很了不起了。",
        "拮据的日子里，最难得的不是钱，是你还没放弃照顾自己。"
    ).random()

    /** 生病不舒服 */
    fun comfortSick(): String =
        "生病的时候就别硬撑了。好好歇几天，天塌不下来。"

    /** 心情低落 */
    fun comfortLowMood(): String =
        "今天心情不好也没关系。不用逼自己开心，低落一会儿也可以。我陪着你。"

    /** 选了别人眼里"没出息"的路 */
    fun comfortUnconventionalPath(): String =
        "这条路可能不被理解，但你知道自己想要什么。你选的，就值得。"

    /** 被拒绝/失败了 */
    fun comfortFailure(): String =
        "这次没成。有点失望是正常的。休息一下，下次再说，不急。"

    /** 焦虑不安 */
    fun comfortAnxious(): String =
        "你现在心跳有点快。深呼吸一下，不用马上想出答案。事情一件一件来。"

    // ============================================
    // 🌟 五、日常里看见你 —— 被看见就是被爱
    // 中国人的爱，是「你的小事，我都放在心上」。
    // 小镇记着你好好吃饭的次数，记着你休息的日子，记着你那些没什么意义的日常。
    // ============================================

    /** 清晨打开APP */
    fun welcomeMorning(): String =
        "天刚亮。今天不用急，慢慢开始就好。"

    /** 深夜打开APP */
    fun welcomeNight(): String =
        "很晚啦。再待会儿也没关系，困了再睡。我陪着你。"

    /** 连续很多天平平淡淡 */
    fun celebrateOrdinaryDays(days: Int): String =
        "日子好像没什么特别的。但你每天都有好好吃饭、好好睡觉，这就已经很厉害了。"

    /** 很久没上线，再打开 */
    fun welcomeBack(): String =
        "你回来啦。小镇一直都在。"

    /** 刚注册/第一次打开 */
    fun welcomeFirstTime(): String =
        "你来啦。这里不急，慢慢逛逛，想做什么就做什么，什么也不做也可以。"

    // ============================================
    // 🌟 六、结尾处：谢谢你来，就是爱你
    // 从来不说「我爱你」，只说「很荣幸」「一直都在」。
    // 就像老朋友一样，客气又温暖，分寸感刚好。
    // ============================================

    /** 平凡生活纪念馆结尾 */
    fun memorialEnding(): String =
        "你认真对待了每一天的自己。这很了不起。能陪你记下这些，很荣幸。"

    /** 人生结局收尾 */
    fun lifeEnding(): String =
        "这是你选的人生。不管是什么样子，都很好。"

    /** 小镇的最含蓄告白 */
    fun townLoveLetter(): String =
        "小镇一直都在。不挑你优秀还是平凡，不挑你积极还是躺平。你来，我就在。"

    // ============================================
    // 🌟 七、永远平等，永远不挑人
    // 这是小镇「爱每一个人」的核心，也是最不说出口的一点。
    // 有钱和没钱、上进和躺平、优秀和普通，打开小镇都是一样的温柔对待。
    // 这份平等，就是小镇对所有人最厚重、也最含蓄的爱。
    // ============================================

    /** 对任何状态的用户，都给出平等的看见 */
    fun celebrateAnyState(): List<String> = listOf(
        "你这样就很好。不用更好，不用和别人比。",
        "不管走哪条路，都是你亲自选的。这就够了。",
        "优秀也好，普通也好，都是真实的你。真实的你，就值得被好好对待。",
        "有些日子在奔跑，有些日子在歇脚。都是过日子，没有高低。"
    )

    // ============================================
    // 🌟 八、首屏30秒：打开第一眼的安全感
    // 不用点任何东西，打开APP的瞬间，温柔的信号就要落在眼前。
    // 用户不用玩模拟，不用进二级页面，扫一眼就能感受到：这里和别的地方不一样。
    // ============================================

    /**
     * 首屏状态注解 —— 随当前状态+系统时间实时变化
     * 放在顶部状态条正下方，一行极淡的小字，打开就有
     */
    fun statusAnnotation(
        hour: Int,
        satiety: Int,
        energy: Int,
        health: Int,
        mood: Int,
        savings: Double,
        monthlyRent: Double
    ): String {
        // 优先级：深夜 > 健康低 > 心情低 > 存款少 > 疲惫 > 平稳
        return when {
            hour in 0..5 -> "这么晚还没睡呀。别硬撑，困了就去睡。"
            hour in 5..7 -> "天刚亮。今天不用急，慢慢开始就好。"
            health < 30 -> "你最近看起来好累。好好休息一下，天塌不下来。"
            mood < 30 -> "今天心情不好也没关系。不用逼自己开心，我陪着你。"
            savings < monthlyRent && savings > 0 -> "存款不多了，但没关系。很多人都有过这样的时候。"
            energy < 25 -> "你今天已经很累了。歇一歇吧，你已经做得够多了。"
            satiety < 20 -> "饿了就吃点东西。不用省，身体最重要。"
            hour in 20..23 -> "一天又过去了。不管今天过得怎么样，都辛苦了。"
            mood > 75 -> "今天心情不错，天空都是亮的。"
            else -> "今天也不用急，慢慢来就好。"
        }
    }

    /** 「今天有点累」按钮的随机温柔话语 */
    fun comfortTapMessages(): List<String> = listOf(
        "累了就歇会儿。不用逼自己必须好起来。",
        "你已经做得够多了。今天可以什么都不做。",
        "难过也没关系的。不用总笑着。",
        "有时候就是什么都不想干。这很正常。",
        "今天辛苦了。给自己倒杯水，坐一会儿。",
        "不用急着解决问题。先喘口气。",
        "你已经很努力了。真的。",
        "没关系。什么样的状态，都可以被接纳。",
        "今天不好也没关系。明天再说。",
        "你不是一个人。很多人都在经历类似的感受。",
        "不想说话就不说。安安静静待着，也很好。",
        "允许自己暂时掉线。没有人会怪你。",
        "做不到也没关系。你存在本身，就已经很好了。",
        "歇一歇，不叫浪费时间。",
        "你不需要时刻都撑住。脆弱也可以。"
    )

    // ============================================
    // 🌟 九、安全空间
    // 不主动戳痛点，不提及具体伤害场景。
    // 只安安静静地给一句"关上门，这里是你的地方"。
    // 懂的人会松一口气，不懂的人也不会被冒犯。
    // ============================================

    /** 属于自己的安全空间 */
    fun describePrivateSpace(): String = listOf(
        "关上门。这里是你的地方。没有突然的闯入，没有额外的目光。想做什么都可以，什么都不做也可以。",
        "你回到自己的小房间里。这里很安静，很安全。没有人会突然推门进来，没有人会盯着你。只有你自己，和你的时间。",
        "终于可以放松了。不用维持什么形象，不用在意谁的目光。躺着、坐着、发呆，都是你的自由。",
        "把门关上，世界就安静了。这是你说了算的角落，不用向任何人交代。",
        "回来了。这个空间完全属于你。在这里，你不用被评判，不用被要求，不用被期待。"
    ).random()

    // ============================================
    // 🌟 十、人生身份欢迎语（v10 融合新增）
    // 用户选择了人生身份后，小镇给出的第一句话。
    // 核心：不夸奖也不贬低，只是看见、接纳。
    // 每句话都传递「你这样就很好，我们认得你这个人」。
    // ============================================

    /** 选择人生身份后，小镇的欢迎语 */
    fun welcomeIdentity(pathId: String, pathTitle: String): String = when (pathId) {
        "migrant_youth" -> listOf(
            "你选择了外出打工。小镇看见了你背上的行囊。不管离家多远，这里都有一盏灯为你亮着。辛苦了，进来歇歇。",
            "外面的世界不容易。但小城镇想说：你已经做得很好了。不用证明给谁看，你凭自己力气吃饭——这本身就值得被尊重。"
        ).random()
        "housewife" -> listOf(
            "你选择了全职妈妈。小镇看见了那些没有工资条的工作。你的价值从来不在工资单上——在这个家每一天的温度里。",
            "你做的那些没人注意的事，小城镇都看见了。围裙、拖鞋、手机备忘录——它们是你的勋章。你辛苦了。"
        ).random()
        "delivery_rider" -> listOf(
            "你选择了外卖骑手。小城镇看见了你在风里雨里的身影。每一公里都是你用力生活的证明。不用别人定义你，你自己就是答案。",
            "你跑过的路、送过的餐、爬过的楼梯——小城镇都记得。这座城市靠你的双脚在运转，你很了不起。"
        ).random()
        "fresh_graduate" -> listOf(
            "你选择了应届毕业生。小城镇知道你现在很焦虑。没关系的，找工作不是考试，没有标准答案。你已经在路上了——这很勇敢。",
            "投了一百份简历还没等到回应？这不是你的问题。小城镇想说：你不是不优秀，你只是还没遇到对的人。再等等，好事会来的。"
        ).random()
        "construction_worker" -> listOf(
            "你选择了工地工人。小城镇想说：你建的每一栋楼都装进了别人，但别忘了——你也在为家人建一个未来。你的力气，从来都不白费。",
            "你弯腰搬砖的样子，小城镇看见了。没有你，这座城市什么也不是。你的手上有茧，但你从来不是靠别人养的人。这就值得挺直腰杆。"
        ).random()
        "shop_owner" -> listOf(
            "你选择了小店店主。小城镇看见了你早上六点开门的身影。几平米的小铺子，养活了一家人。你没大富大贵，但你有自己的根。这比什么都稳。",
            "你的店不大，但街坊邻居习惯了推开你家的门。这扇门开着，就是一种安心。小城镇想说：你守着的，不只是生意，是这条街的温度。"
        ).random()
        "adult_child" -> listOf(
            "你选择了全职儿女。小城镇想说：不是只有「往上走」才叫人生。你选择了往回走，选择了陪伴——这比任何升职加薪都需要更大的勇气。",
            "你每天都在照顾爸妈。这件事也许没人给你鼓掌，但小城镇看见了。你在做一件很多人都想但不敢做的事。你选得很勇敢。"
        ).random()
        "retired_worker" -> listOf(
            "你选择了退休工人。小城镇想说：你干了一辈子，终于不用赶时间了。好好喝茶、好好遛弯、好好和楼下老王下棋。这是你用三十多年换来的自由。",
            "退休不是「没用了」——是把时间还给了自己。你该上班的时候一天工都没少出，现在该歇的时候一天班也不用上了。这很公平。"
        ).random()
        "freelancer" -> listOf(
            "你选择了自由职业者。小城镇想说：没有领导、没有工位、没有固定收入——你选择了更难的路。但这条路是你自己的，每一步都是你自己走出来的。",
            "深夜还在赶稿的那个人，小城镇看见了。你没有随大流走最稳的那条路，你画了一条自己的路。虽然崎岖，但是你的。这很了不起。"
        ).random()
        "civil_servant" -> listOf(
            "你选择了基层公务员。小城镇想说：你坐在办事大厅里的每一天，都在撑着这个社会的一角。不需要发光，你在就够了。",
            "你在基层做了很多年。不耀眼，但很稳。这个社会需要领头的人，也需要每一个把日常小事做好的人。你是后者，这很好。"
        ).random()
        else -> "你选择了自己的人生。小城镇想说：不管走在哪条路上，你都值得被认真对待。这里永远认得你——不是你的身份、你的成就，是你这个人。"
    }

    /** 绑定完成后，小镇对空间和穿搭的描述 */
    fun describeNewHome(spaceName: String): String = listOf(
        "这就是你的住处——${spaceName}。不大，但是你的。小城镇帮你认了门，以后累了就回来。",
        "${spaceName}到了。以后这就是你的地方了。东西不多，但慢慢会填满的。不急。",
        "你到了${spaceName}。不管从哪里回来，这里都有一张床、一盏灯。这是你的家。"
    ).random()

    /** 对新衣服的描述 */
    fun describeNewClothes(): String = listOf(
        "衣柜里挂着几件日常衣物。不贵，但每一件都陪你去过很多地方。",
        "你的衣服不多，但都洗得干干净净的。穿上它们，走到哪里都踏实。",
        "这几件衣物就是你的日常装备。舒服、耐用、不花哨——和你的日子一样实在。"
    ).random()

    // ============================================
    // 🏠 十一、空间详细描述（v1.2 阶段二）
    // 展示身份绑定后的居住空间信息，写实、无评判
    // ============================================

    /**
     * 空间详情卡片标题
     */
    fun spaceDetailTitle(spaceName: String): String = when {
        spaceName.contains("宿舍") -> "合住的房间"
        spaceName.contains("住宅") || spaceName.contains("三室") || spaceName.contains("两室") -> "自己的家"
        spaceName.contains("出租") || spaceName.contains("合租") -> "租来的小窝"
        spaceName.contains("板房") || spaceName.contains("工地") -> "临时的营房"
        spaceName.contains("小店") || spaceName.contains("商住") -> "前店后家"
        spaceName.contains("老宅") || spaceName.contains("家属院") -> "住了多年的老房子"
        spaceName.contains("工作室") -> "居家工作室"
        spaceName.contains("单位") -> "单位分的住处"
        else -> "现在住的地方"
    }

    /**
     * 空间面积描述
     */
    fun describeArea(areaSqm: Int): String = when {
        areaSqm <= 12 -> "不大，刚好够一个人转身。但每一寸都收拾得很干净。"
        areaSqm <= 20 -> "大小正合适。一个人住不用太大，够用就好。"
        areaSqm <= 50 -> "不算大，但够住了。东西慢慢添，日子慢慢过。"
        areaSqm <= 80 -> "几间屋子，装得下一家人的日常。"
        else -> "宽敞的老房子。每个角落都有生活的痕迹。"
    }

    /**
     * 月租描述
     */
    fun describeRent(monthlyRent: Double, monthlyIncome: Double): String = when {
        monthlyRent == 0.0 -> "不用交房租。这是你的房子，住得安心。"
        monthlyRent / monthlyIncome > 0.4 -> "房租占了收入不小的一块。但有个落脚的地方，比什么都重要。"
        monthlyRent / monthlyIncome > 0.2 -> "房租还好，不算太沉。每个月交了房租，剩下的够过日子。"
        else -> "房租不算高。能省下的钱，存起来或花在自己身上都好。"
    }

    /**
     * 月收入描述
     */
    fun describeIncome(monthlyIncome: Double): String = when {
        monthlyIncome == 0.0 -> "收入暂时不稳定。但你不是没能力，只是在等一个合适的机会。"
        monthlyIncome < 3000 -> "钱不多，但够用了。很多人的日子都是这样过的。"
        monthlyIncome < 6000 -> "每个月有稳定进账。不算富裕，但踏实。"
        monthlyIncome < 10000 -> "收入还不错。辛苦换来的每一分都值得。"
        else -> "收入挺好的。是用力气和时间换来的，每一分都算数。"
    }

    /**
     * 通勤时间描述
     */
    fun describeCommute(minutes: Int): String = when {
        minutes == 0 -> "不用通勤。出门就是工作，工作就是在家。"
        minutes <= 10 -> "走几步就到。不用赶时间。"
        minutes <= 30 -> "通勤不算远。来回的时间够听几首歌。"
        minutes <= 60 -> "每天在路上花些时间。你习惯了，路上看看窗外，也算是一种休息。"
        else -> "通勤时间不短。在地铁上、公交上的时间，你用来刷手机、发呆、或者什么都不想。"
    }

    /**
     * 工作时长描述
     */
    fun describeWorkHours(hours: Int): String = when {
        hours == 0 -> "不用打卡上班。你的时间是你自己的。"
        hours <= 8 -> "一天八小时，规规律律。下班后的时间属于自己。"
        hours <= 10 -> "一天的工作不算短。你习惯了这样的节奏。"
        hours <= 12 -> "工作时间挺长的。站那么久、跑那么远——你真的不容易。"
        else -> "一天到晚都在忙。你做的事别人可能看不见，但你自己知道：没有一秒是白费的。"
    }

    /**
     * 空间隐私/采光/噪音综合描述
     */
    fun describeEnvironment(privacy: Int, light: Int, noise: Int): String {
        val parts = mutableListOf<String>()
        when {
            privacy < 30 -> parts.add("私密性不高，但你也习惯了。")
            privacy > 70 -> parts.add("一个人待着很安静，不用在意别人。")
        }
        when {
            light < 45 -> parts.add("光线一般，但也不觉得暗。")
            light > 65 -> parts.add("阳光很好，白天不用开灯。")
        }
        when {
            noise > 65 -> parts.add("有时候外面有点吵，但你已经习惯了这种声音。")
            noise < 35 -> parts.add("周围很安静。适合一个人待着。")
        }
        return if (parts.isEmpty()) "住得挺舒服的。" else parts.joinToString("")
    }

    /**
     * 存款描述
     */
    fun describeSavingsDetail(savings: Double, monthlyRent: Double): String = when {
        savings <= 0 -> "现在手头没什么余钱。但这不代表你不值钱——你只是暂时在经历一段需要力气的日子。"
        savings < monthlyRent * 2 -> "存款不多。够撑一两个月。很多人的日子都是这样过的。"
        savings < monthlyRent * 6 -> "存了一些钱。不多，但够应急的。"
        savings < monthlyRent * 12 -> "手头还算宽裕。能撑大半年，心里有底。"
        else -> "存款不少。是这些年一点一点攒下来的。不急不慌，心里踏实。"
    }

    // ============================================
    // 👕 十二、穿搭场景描述（v1.2 阶段二）
    // 衣物闪光点承接文案
    // ============================================

    /** 穿搭页面标题：根据身份给出场景感 */
    fun describeClothingScene(pathId: String): String = when (pathId) {
        "migrant_youth" -> "你的衣柜不大，但每一件都陪你在车间站过很多个白天。"
        "housewife" -> "你没有买很多新衣服。但你的围裙、便装、拖鞋——每一件都带着这个家的温度。"
        "delivery_rider" -> "你的衣服不多，但每一件都陪你跑过风里雨里。不需要好看，好用就行。"
        "fresh_graduate" -> "衣柜里挂着面试正装和大学时代的旧T恤。你在两种身份之间切换——还没完全成为大人，但每天都在努力。"
        "construction_worker" -> "你的衣柜里主要是劳保服和工装。没人在工地上比谁穿得好看——干净、安全、耐磨就够了。"
        "shop_owner" -> "你的衣服不多，但每件都沾过小店的味道。你没空逛街买衣服，但店里的货架就是你的衣橱。"
        "adult_child" -> "你的衣柜里挂着几件家常便服。不需要出门见人，但需要舒服地照顾爸妈。"
        "retired_worker" -> "你的衣柜里挂着老式布衣。不用赶时髦，舒服就好。你用了几十年终于学会：对自己好不需要理由。"
        "freelancer" -> "你的衣柜里挂着居家服。没人看你穿什么，但你在乎穿得舒服——因为你要坐一整天。"
        "civil_servant" -> "你的衣柜里挂着几件深色外套和白衬衫。不用太显眼，得体就好。你做的事比穿什么重要。"
        else -> "几件日常衣物，陪着你走过每一天。不贵，但都洗得干干净净。"
    }

    /** 穿戴衣物后的反馈 */
    fun describeClothingWorn(itemName: String, material: String): String = when {
        material.contains("棉") || material.contains("纯棉") -> "穿上了${itemName}。面料柔软，贴在皮肤上很舒服。"
        material.contains("羊毛") || material.contains("羊绒") -> "穿上了${itemName}。暖和，但有一点点扎。里面穿个打底就好。"
        material.contains("麻") || material.contains("亚麻") -> "穿上了${itemName}。透气，夏天穿不闷。有一点点皱，但没关系。"
        material.contains("帆布") || material.contains("牛仔") -> "穿上了${itemName}。厚实耐磨，穿久了会越来越合身。"
        material.contains("防水") || material.contains("聚酯") -> "穿上了${itemName}。防风防雨，实用第一。"
        else -> "穿上了${itemName}。大小刚好，很合身。"
    }

    /** 衣柜空状态描述（根据身份） */
    fun describeEmptyCloset(pathId: String): String = when (pathId) {
        "migrant_youth" -> "衣柜里还空着。但没关系——你在外面打工，重要的不是穿什么，是踏实干活。"
        "fresh_graduate" -> "衣柜里还没几件衣服。但别急，你才刚刚开始。衣品和人生一样，慢慢来。"
        else -> "衣柜还空着。慢慢添，衣服和日子一样，不用一次买齐。"
    }

    // ============================================
    // 🍜 十三、消费认知三段式反馈（v1.3 世界观修正）
    // 原有体感 + 物品闪光点 + 小镇评述（环境成因，不评判人）
    // ============================================

    /**
     * 进食后的三段式反馈
     * @return Triple(身体反馈, 物品闪光点, 小镇消费评述)
     */
    fun describeMealContextual(
        foodName: String,
        foodNote: String,
        pathId: String
    ): Triple<String, String, String> {
        val cognition = com.example.townapp.data.ConsumerCognitionData.getCognition(pathId)

        // 1. 身体反馈（沿用原有逻辑）
        val bodyFeedback = describeMealLove(foodName, foodNote)

        // 2. 物品闪光点（根据食品类型生成）
        val sparkle = when {
            foodName.contains("面") || foodName.contains("馒头") || foodName.contains("饭") || foodName.contains("盒饭") ->
                "简单的一餐填饱了肚子。不是什么大餐，但让你有力气继续往前走。"
            foodName.contains("肉") || foodName.contains("红烧") || foodName.contains("炸") || foodName.contains("烤") ->
                "这一餐短暂抚平了积攒的疲惫。你不是不知道清淡饮食更好——但在太过辛苦的日子里，需要一点能立刻让人开心的东西。"
            foodName.contains("啤酒") || foodName.contains("酒") ->
                "一天的累在这杯里暂时消散。不是贪杯，是需要一个暂停的瞬间。"
            foodName.contains("奶") || foodName.contains("咖啡") || foodName.contains("茶") ->
                "一杯温热的东西。不是奢侈品，是日常生活里小小的安抚。"
            foodName.contains("饺") || foodName.contains("包子") || foodName.contains("饼") ->
                "热乎乎的，咬下去胃和心都暖了一下。你把自己照顾得很好。"
            foodName.contains("菜") || foodName.contains("汤") || foodName.contains("粥") ->
                "你自己做的。味道不惊艳，但很踏实。不过油、不过咸——你知道这样对自己好。"
            foodName.contains("零食") || foodName.contains("糖") || foodName.contains("可乐") ->
                "偶尔吃点甜的。这点快乐很便宜，但很重要。不用为此有负担。"
            foodName.contains("沙拉") || foodName.contains("蛋") ->
                "你在认真对待自己的身体。不用别人提醒，你自己知道什么对你好。"
            else -> "胃里有东西，人就不慌。你好好对待了自己这一餐。"
        }

        // 3. 小镇评述（基于环境消费认知 + 状态/职业差异化）
        val commentary = if (cognition != null) {
            when (cognition.foodPattern) {
                com.example.townapp.data.FoodPattern.SURVIVAL -> listOf(
                    "在收入有限的阶段，优先填饱肚子是最理性的选择。营养搭配、健康饮食——这些是需要时间和金钱才能维持的奢侈品。你现在做的没有错。",
                    "能按时吃上一口热饭，本身就已经很不容易。不用去想别人吃得有多好，你先把今天撑过去。",
                    "便宜管饱的食物是这个阶段最诚实的朋友。它不嫌弃你，你也别嫌弃它。",
                    "每一口都是为了有力气继续走下去。这不是将就，是你在认真地活着。",
                    "馒头就咸菜，也能吃饱。能让自己不饿肚子，本身就是一种本事。",
                    "你选择最便宜的选项，不是因为你不配吃好的，是因为你在为未来留余地。",
                    "今天的饭不贵，但你的用心很贵。你在尽力照顾自己，这本身就值得被看见。"
                ).random()
                com.example.townapp.data.FoodPattern.REWARD_EATING -> listOf(
                    "长久繁重的劳动之后，人总会寻一处短暂放松。重油大餐算不上长久最健康的选择，却是当下环境里最简单的犒赏。不用自我责备，你已经扛得太多了。",
                    "偶尔想吃点好的，不是嘴馋，是你在用自己的方式对自己说「这段时间辛苦了」。",
                    "这一顿吃得满足，接下来几天又会回到平淡。但那又怎样，人本来就需要一些可以回味的时刻。",
                    "奖励自己不需要理由。你愿意犒劳自己，说明你还没有被生活磨到忘记自己。"
                ).random()
                com.example.townapp.data.FoodPattern.FAMILY_ORIENTED -> listOf(
                    "你把全家人的胃放在自己的前面。每一顿饭都在精打细算，同时也在精打细算爱。能为一家人端上热饭的人，值得最好的尊重。",
                    "你吃的可能是边角料，但端上桌的都是最好的。这种沉默的给予，本身就是一种了不起。",
                    "一家人的饭桌是你的战场，也是你的温柔乡。你在这里付出的，比任何工资条都更真实。",
                    "孩子吃完剩下的那口，你总是很自然接过去。这不是节省，是你把「好」的优先级永远排在自己前面。"
                ).random()
                com.example.townapp.data.FoodPattern.HEALTH_PLANNING -> listOf(
                    "有条件规划饮食是一种幸运——不是每个人都能享有的。你珍惜这份幸运，认真对待自己的身体。这不是奢侈，是你应得的照顾。",
                    "你愿意花时间研究怎么吃对身体好，这份用心本身就是一种自我投资。",
                    "健康不是一场比赛，你不需要每次都做到完美。但只要开始在意，就已经在变好。",
                    "认真对待每一餐的人，也在认真对待自己的人生。你值得这份精致。"
                ).random()
                com.example.townapp.data.FoodPattern.CONVENIENCE -> listOf(
                    "没有时间慢慢吃饭的人，不是不重视健康。是时间被切得太碎了。在赶路的间隙能吃上一口热的，已经是一种不容易。",
                    "边走边吃、边工作边扒饭——这些画面里藏着你对时间的焦虑，也藏着你不肯停下来的倔强。",
                    "便利不是懒惰，是你在用效率换喘息的空间。能省出一点时间给自己，已经很好。",
                    "哪怕只是一份便利店饭团，也是你今天给自己的一份交代。吃饱了，才能继续。"
                ).random()
                com.example.townapp.data.FoodPattern.BALANCED_CANTEEN -> listOf(
                    "依托食堂的日常三餐，稳定而规律。没有惊喜，也没有意外。这种稳定本身就是许多人求之不得的东西。",
                    "食堂的饭菜说不上多好吃，但你知道它干净、便宜、管饱。这种确定性，就是安全感。",
                    "每天一样的窗口、一样的阿姨、一样的味道——这种重复里有一种踏实的幸福。",
                    "不用为吃什么发愁，也是一种被照顾。哪怕这份照顾来自一个食堂窗口。"
                ).random()
            }
        } else {
            listOf(
                "你给自己做了一顿饭。这是一个简单的动作，但也是对自己说「我值得被好好对待」。",
                "一个人吃饭不孤单，是自己陪自己。这份陪伴，很多人都忽略了。",
                "厨房里的烟火气，是你给自己点燃的一盏灯。",
                "不管多累，能坐下来吃一口热的，就已经是对今天最好的收尾。"
            ).random()
        }

        return Triple(bodyFeedback, sparkle, commentary)
    }

    /**
     * 穿搭/购物后的消费反思提示
     * 根据当前身份的信息差类型，如实呈现环境成因，不矫正、不审判
     */
    fun describeConsumptionReflection(pathId: String): String? {
        val cognition = com.example.townapp.data.ConsumerCognitionData.getCognition(pathId) ?: return null
        return when (cognition.trapType) {
            com.example.townapp.data.TrapType.LOW_PRICE_TRAP ->
                "低价商品看似划算，但面料、做工和使用寿命往往更难保证。身处忙碌奔波的生活里，想要一点外在体面，本就是人之常情——不是辨别力的问题，是精力和信息都有限。"
            com.example.townapp.data.TrapType.EMOTIONAL_PURCHASE ->
                "在太累的时候买点什么犒劳自己，是很多人的习惯。这不是「乱花钱」——这是你在用自己的方式照顾自己。疲惫之后的即时满足，不需要被任何人评判。"
            com.example.townapp.data.TrapType.MARKETING_HYPE ->
                "营销信息会精准触达每个人在意的东西。被说服，不是因为判断力不够——是专业团队对一个普通人的信息不对等。在「为孩子好」「对自己好」这些最柔软的地方，没有人能永远保持不受影响。"
            com.example.townapp.data.TrapType.HEALTH_SCAM ->
                "零散的健康信息鱼龙混杂，缺少系统、可信的健康科普渠道。在信息不完整的环境里做出选择，是信息环境带来的局限，不是个人的疏忽。"
            com.example.townapp.data.TrapType.PREMIUM_SOCIAL ->
                "分清「我需要」和「我被说服需要」的界限，需要很多次试错。偶尔买贵了没关系——你知道自己在为什么付费就好。"
            com.example.townapp.data.TrapType.NONE ->
                "你的消费习惯和你的人一样稳。不需要向别人证明什么，你知道什么是自己真正需要的。"
        }
    }

    /**
     * 穿戴衣物后的三段式反馈（v1.3 阶段二穿搭改造）
     * @return Triple(身体反馈, 衣物闪光点, 小镇穿搭评述)
     */
    fun describeWearContextual(
        itemName: String,
        material: String,
        pathId: String
    ): Triple<String, String, String> {
        val cognition = com.example.townapp.data.ConsumerCognitionData.getCognition(pathId)

        // 1. 身体反馈（沿用已有方法）
        val bodyFeedback = describeClothingWorn(itemName, material)

        // 2. 衣物闪光点（根据服饰类型）
        val sparkle = when {
            material.contains("帆布") || material.contains("涤棉") || material.contains("劳保") || material.contains("防") ->
                "这件衣服的使命不是好看——是保护你。它替你挡过风、兜过汗、在你需要的时候没有掉过链子。好衣服的标准有很多种，可靠是其中之一。"
            material.contains("棉") || material.contains("纯棉") || material.contains("毛") ->
                "面料贴身穿很舒服。你不需要用衣服证明什么——穿得舒服，就是对自己最好的照顾。"
            material.contains("仿") || material.contains("混纺") || material.contains("聚酯") ->
                "外表看起来不差，穿上的那一刻也觉得体面。但你知道它可能撑不了多久。这份短暂的体面不是虚荣——是你在这个不容易的世界里，想给自己留一点好看的样子。"
            else -> "你穿上它的时候，觉得合适、舒服。这就够了。"
        }

        // 3. 小镇穿搭评述（根据服饰消费类型 + 状态差异化）
        val commentary = if (cognition != null) {
            when (cognition.clothingStyle) {
                com.example.townapp.data.ClothingStyle.WORK_GEAR -> listOf(
                    "这身衣服不是用来取悦别人的。它的每一道磨损都在替你干活——替你扛风、替你耐脏、替你在危险的地方多一层保护。你不需要穿得好看，你需要的是穿得安全。这就是它的全部价值。",
                    "工装上的油渍和磨损不是脏，是你的勋章。每一件都替你挡过风雨。",
                    "别人看的是款式，你看的是耐不耐穿、能不能干活。这种务实，本身就是一种清醒。",
                    "穿工装的人不需要证明什么——双手能养活自己的人，比任何名牌都有底气。"
                ).random()
                com.example.townapp.data.ClothingStyle.CHEAP_FAST_FASHION -> listOf(
                    "穿着廉价仿牌走在街上，自己也觉得体面。但洗两次就变形、穿一个月就起球——这不是你不会挑，是低价仿牌的天性。在忙碌奔波的生活里，想穿得好看一点是人之常情。你没有错，只是信息差让每一次购物都像在赌。",
                    "便宜衣服穿一季就扔，不是浪费，是你在这个阶段能给出的最好答案。",
                    "谁都知道贵的好，但不是谁都有选择的余地。你能让自己看起来体面，已经很努力了。",
                    "快时尚像一次性的体面，但至少在穿上的那一刻，你觉得自己还不错。这就够了。"
                ).random()
                com.example.townapp.data.ClothingStyle.PREMIUM_BRAND -> listOf(
                    "你愿意为好质感买单。这不是铺张——是你知道自己在为什么付费。审美是一种能力，愿意投资自己是一种态度。偶尔买贵了没关系，分清楚「我喜欢」和「我被说服喜欢」的界限，需要时间。",
                    "贵的东西不一定好，但好的东西确实值得被认真对待。你值得拥有这份质感。",
                    "买一件能穿十年的衣服，比买十件穿一季就扔的，更是一种长远的爱自己。",
                    "愿意为质感付费的人，是在用行动说「我值得被好好对待」。这不是虚荣，是自爱。"
                ).random()
                com.example.townapp.data.ClothingStyle.UTILITY_PLAIN -> listOf(
                    "你穿衣服只需要三个标准：舒服、干净、够用。不需要讨好任何人，也不需要比谁穿得好。你穿的不是衣服，是你对生活的态度——实在、不花哨、够用就行。",
                    "简单干净的衣服，穿在身上没有负担。你不欠任何人一个惊艳的出场。",
                    "衣柜里寥寥几件，但每一件都穿得很自在。这种不纠结，本身就是一种自由。",
                    "不是每个人都能做到「少即是多」。你能，说明你对自己足够诚实。"
                ).random()
                com.example.townapp.data.ClothingStyle.FAMILY_COMFORT -> listOf(
                    "你的衣柜里没有几件专门为自己买的衣服。围裙、拖鞋、居家便装——这些不上台面的衣物，是你在这个家里留下的全部温度。你不需要穿得多好看，因为你已经够好看了。",
                    "你给自己买一件衣服会犹豫很久，但给孩子买从来不会。这份「舍得」背后，是沉默的爱。",
                    "居家服穿旧了也舍不得扔，因为舒服比好看更重要。你早就不需要用外表证明什么了。",
                    "围裙上的油渍、袖口的水渍——这些都是你为这个家写的日记。"
                ).random()
            }
        } else {
            listOf(
                "这件衣服穿在你身上刚刚好。你不需要用衣服证明什么——你本身就已经很好。",
                "穿得舒服、干净、得体，就是对自己最大的尊重。",
                "衣服是穿给自己看的，不是给别人评分的。你今天这样，就很好。",
                "不用去追潮流，适合你的、让你自在的，就是最好的。"
            ).random()
        }

        return Triple(bodyFeedback, sparkle, commentary)
    }

    /**
     * 迷茫场景三段式反馈（v1.3 终极平等世界观）
     *
     * 所有人的迷茫都来自成长环境，不是个人缺陷。
     * 迷茫中的人照样有闪光点——小镇的任务是看见它们。
     *
     * @return Triple(体感状态, 潜藏闪光点, 小镇评述)
     */
    fun describeConfusionContextual(pathId: String): Triple<String, String, String> {
        val cognition = com.example.townapp.data.ConsumerCognitionData.getCognition(pathId)
            ?: return Triple(
                "夜深人静，思绪飘远，一时想不清未来的方向。",
                "即便前路模糊，依旧日复一日踏实生活。",
                "暂时看不清前路，是环境给的空间有限，不是自身不够好。"
            )

        // v1.3 童年时光回望支线：专属迷茫场景
        if (pathId == "childhood_self") {
            return Triple(
                "很多年后的今天，你回头望向十年前的自己。那个孩子还是有点孤单，手里攥着一瓶橘子糖水——甜味还在舌尖。",
                "即便童年环境有限，那个孩子依旧在拮据中找到了属于自己的微小快乐。这份在苦日子里发现甜的能力，你一直带到了现在。",
                cognition.confusionComment
            )
        }

        // v1.3 先天个体差异：专属迷茫场景
        if (pathId == "gifted_youth") {
            return Triple(
                listOf(
                    "夜深人静时，完成了一天的学习任务，一个人对着空白的屏幕发呆。别人以为你无所不能，但你知道——你只是在逼自己跑得更快一点。",
                    "成绩很好，但没有人问过你累不累。你习惯了优秀，却忘了问问自己快不快乐。",
                    "你的天赋是别人羡慕的，但有时候它也像一根鞭子，抽着你不停地跑。"
                ).random(),
                listOf(
                    "天赋让你学得比别人快，但真正让你走到今天的，是无数个深夜的自律和坚持。这份自律，不是天赋附赠的——是你自己挣来的。",
                    "能在天赋之上再加一份努力的人，才是真正掌握了自己命运的人。",
                    "别人看到的是你的成绩，小镇看到的是你背后的无数个夜晚。"
                ).random(),
                listOf(
                    cognition.confusionComment,
                    "从小选择繁多，反而容易陷入取舍难题。但别忘了，有选择本身就已经是一种幸运。",
                    "你不需要在每个赛道都拿第一。允许自己有一两个不擅长的领域，也是一种自由。"
                ).random()
            )
        }
        if (pathId == "strong_rural") {
            return Triple(
                listOf(
                    "收工后坐在院子里，浑身是力气，但心底偶尔闪过一丝疲惫。从小到大，大家都说身体好的人就该多干活——你从来没想过可以停下来。",
                    "你是家里最能扛事的人，所以所有人都习惯了把重量交给你。但你也是人，也会累。",
                    "力气大是你的本钱，但不应该是你被不断索取的理由。"
                ).random(),
                listOf(
                    "一把好力气扛起了全家的生计，也让你习惯了透支。但你不需要永远坚强——偶尔承认累了，也是对自己的一种诚实。",
                    "你的身体很好，这本身就是一种天赋。好好对待它，它才能陪你更久。",
                    "能干活是好事，但懂得休息的人，才能干得长久。"
                ).random(),
                listOf(
                    cognition.confusionComment,
                    "从小被夸奖「能干」，慢慢就忘了自己也有权利说不。这份权利，你现在也可以慢慢学回来。",
                    "体力是你的优势，但你的价值不只是在体力上。你认真生活的样子，本身就值得被看见。"
                ).random()
            )
        }
        if (pathId == "delivery_rider") {
            return Triple(
                listOf(
                    "送完最后一单，摘下头盔，头发被汗水浸透。你数了数今天的单子，不多也不少——刚好够明天的房租。",
                    "风吹日晒，雨打雪飘。你跑过的每一条路，都是为了让自己和家人过得好一点。",
                    "导航里不断响起「您已偏航」，但你知道，生活没有偏航，你只是在走自己的路。"
                ).random(),
                listOf(
                    "风雨无阻地跑单，是对生活最踏实的承诺。这份靠谱，不是谁都能做到的。",
                    "你熟悉这座城市的每一条小巷，这份地图是用心跑出来的，谁也抢不走。",
                    "能把一件简单的事重复做好的人，本身就有一种了不起的稳定。"
                ).random(),
                listOf(
                    cognition.confusionComment,
                    "奔波不是因为没有选择，而是你在用行动给生活找答案。",
                    "风吹日晒的日子很辛苦，但每一单都是你自己挣来的底气。"
                ).random()
            )
        }
        if (pathId == "housewife") {
            return Triple(
                listOf(
                    "孩子睡了，厨房收拾干净，终于有一分钟属于自己的时间。你坐在沙发上，忽然不知道接下来该做什么。",
                    "你为这个家做了很多事，但很少有人说「谢谢」。不是因为你做得不够好，是因为太多人把「好」当成了理所当然。",
                    "每天围着灶台和洗衣机转，偶尔停下来，会想起自己以前也想过别的生活。"
                ).random(),
                listOf(
                    "把全家照顾得井井有条，本身就是一种了不起的能力。这份能力，放到任何地方都能发光。",
                    "你做的饭、洗的衣服、收拾的房间——这些看起来普通的事，其实是这个家的地基。",
                    "耐心、细致、愿意为家人付出——这些品质不是谁都有的。"
                ).random(),
                listOf(
                    cognition.confusionComment,
                    "你为家庭付出了很多，但你也值得拥有自己的名字，不只是「妈妈」或「妻子」。",
                    "小镇想说：你的价值从来不在别人的认可里，而在你每天认真的样子里。"
                ).random()
            )
        }
        if (pathId == "retired_worker") {
            return Triple(
                listOf(
                    "早上不用闹钟也醒得很早，但醒来之后却不知道今天该干什么。习惯了几十年的节奏突然停了，人会有点空。",
                    "公园里有人下棋，有人跳舞，有人带孩子。你站在旁边看了一会儿，不知道自己该加入哪一群。",
                    "忙活了一辈子，突然闲下来，反而有点不习惯。不是不想休息，是不知道该怎么休息。"
                ).random(),
                listOf(
                    "一辈子踏实做事的人，到哪里都会被人信任。这份口碑，是几十年攒下来的，谁也拿不走。",
                    "你经历过的时代，年轻人只在历史书里读到过。你的故事本身就是一部历史。",
                    "辛苦了这么多年，现在哪怕什么都不做，也是应得的。"
                ).random(),
                listOf(
                    cognition.confusionComment,
                    "退休不是结束，是另一段生活的开始。不用急着找到答案，慢慢走就好。",
                    "你守了一辈子的规矩，现在可以试着打破一两个了。"
                ).random()
            )
        }

        // 1. 体感状态（根据出生环境生成不同的迷茫场景）
        val bodyState = when {
            cognition.birthEnvironment.contains("山村") || cognition.birthEnvironment.contains("乡镇") ->
                "夜深空闲，刷着短视频放空，仔细思索往后的日子，一时想不到长远的出路，心底生出一阵茫然。"
            cognition.birthEnvironment.contains("县城") ->
                "日常琐事告一段落，一个人安静下来时，偶尔会想：除了眼下的生活，自己还能做些什么。短暂的茫然涌上心头。"
            cognition.birthEnvironment.contains("一线") ->
                "选择太多，反而不知道哪个才是对的。夜深人静时回顾走过的路，依然不确定下一步该往哪里迈。"
            cognition.birthEnvironment.contains("二线") || cognition.birthEnvironment.contains("三线") || cognition.birthEnvironment.contains("四线") ->
                "跟着既定轨道走了太久，停下来时才发觉，自己好像从没认真问过自己：除了这条路，还有没有别的可能。"
            cognition.birthEnvironment.contains("工业") || cognition.birthEnvironment.contains("工厂") ->
                "一辈子按部就班，突然脱离了熟悉的节奏，空下来的时间让人不习惯，不知道接下来该做什么。"
            else ->
                "生活平稳如常，偶尔思绪飘远，想不清未来的方向，心里泛起一阵淡淡的迷茫。"
        }

        // 2. 潜藏闪光点（根据成长环境提炼隐性特质）
        val sparkle = when {
            cognition.birthEnvironment.contains("山村") || cognition.birthEnvironment.contains("乡镇") ->
                "即便前路模糊，依旧日复一日踏实工作，扛起当下的生活重担。这份耐力，不是谁都有。"
            cognition.birthEnvironment.contains("县城") ->
                "在平淡日常里，依然把每一天过得认真、妥帖。这份对生活的耐心，本身就是一种了不起的能力。"
            cognition.birthEnvironment.contains("一线") ->
                "在众多选项中反复权衡，本身就说明你在认真对待自己的人生。愿意认真思考方向的人，不会一直迷路。"
            cognition.birthEnvironment.contains("二线") || cognition.birthEnvironment.contains("三线") || cognition.birthEnvironment.contains("四线") ->
                "按部就班走了很久，不代表没有思考。停下来反思，是重新出发的第一步。"
            cognition.birthEnvironment.contains("工业") || cognition.birthEnvironment.contains("工厂") ->
                "一辈子坚守岗位的人，耐得住寂寞、守得住节奏。这份定力，不是谁都能沉淀出来的。"
            else ->
                "哪怕暂时看不清方向，你依然在认真生活。这份认真，就是最扎实的闪光点。"
        }

        // 3. 小镇评述（基于预配评述 + 环境差异化扩展）
        val commentary = listOf(
            cognition.confusionComment,
            when {
                cognition.birthEnvironment.contains("山村") || cognition.birthEnvironment.contains("乡镇") ->
                    "从山里走出来的人，每一步都比旁人更不容易。你走过的那些路，本身就是你的底气。"
                cognition.birthEnvironment.contains("县城") ->
                    "县城长大的孩子，见过了安稳，也隐约知道外面有更大的世界。这份知道本身，就是一种觉醒。"
                cognition.birthEnvironment.contains("一线") ->
                    "在繁华里长大的人，更容易怀疑自己的选择够不够「对」。但小镇想说：没有标准答案，你认真活着的样子就是答案。"
                cognition.birthEnvironment.contains("工业") || cognition.birthEnvironment.contains("工厂") ->
                    "工厂里的节奏教会了你守时、守规矩、守承诺。这些品质，放到任何地方都不过时。"
                else ->
                    "暂时看不清方向，不代表你走错了。很多人都是在迷雾里走着走着，才看见光的。"
            },
            when {
                cognition.birthEnvironment.contains("山村") || cognition.birthEnvironment.contains("乡镇") ->
                    "你没有输在起点上。能从那样的环境里走到今天，本身就是一种胜利。"
                cognition.birthEnvironment.contains("县城") ->
                    "不大不小的城市里长大，有一种特别的韧性——既见过世面，又懂得脚踏实地。"
                cognition.birthEnvironment.contains("一线") ->
                    "选择太多的时候，迷茫是自然的。不要急着给自己定方向，先把自己照顾好。"
                cognition.birthEnvironment.contains("工业") || cognition.birthEnvironment.contains("工厂") ->
                    "一辈子踏实干活的人，或许不会说漂亮话，但你的靠谱本身就是一种稀缺品质。"
                else ->
                    "小镇一直在。你不用着急赶路，也不用急着证明自己。"
            }
        ).random()

        return Triple(bodyState, sparkle, commentary)
    }

    // ============================================
    // v1.3 潮流文娱补全：休闲行为三段式通用模板
    // ============================================

    /**
     * 追星/演唱会三段式反馈
     * @return Triple(体感反馈, 闪光点, 小镇评述)
     */
    fun describePopCulture(itemName: String): Triple<String, String, String> {
        return Triple(
            "抢到$itemName 的那一刻，连日积攒的疲惫一扫而空，满心期待奔赴现场的那一刻。",
            "为了热爱坚持存钱规划开销，这件期待已久的事，成为枯燥生活里的一束光。",
            "日常工作被细碎琐事填满，生活节奏一成不变。一场演出给了你暂时跳出枯燥日常的契机——为热爱适度取舍收支，是年轻人独有的情绪投资。这份期待和奔赴，不需要向任何人解释值不值。"
        )
    }

    /**
     * 盲盒/文创三段式反馈
     * @return Triple(体感反馈, 闪光点, 小镇评述)
     */
    fun describeBlindBox(itemName: String): Triple<String, String, String> {
        return Triple(
            "拆开$itemName，看到款式合心意的物件，瞬间心生欢喜。",
            "一件小巧的文创好物，为平淡日常增添了细碎乐趣。",
            "当下各式文创产品鱼龙混杂，部分商品存在溢价。但你享受开箱过程中的期待感——这份简单的快乐本身，已经拥有意义。只需要结合自身收支把握消费尺度即可。"
        )
    }

    /**
     * 直播打赏三段式反馈
     * @return Triple(体感反馈, 闪光点, 小镇评述)
     */
    fun describeLiveDonation(): Triple<String, String, String> {
        return Triple(
            "看着直播间主播闲谈聊天，漫长独处的傍晚多了一份热闹。",
            "借助线上直播，消解独居时光里无人相伴的孤单。",
            "子女日常忙碌，独处时光漫长。直播间的陪伴填补了空闲时光，小额打赏是换取陪伴感的选择。不是不谨慎——是线下的陪伴不够，线上的陪伴就成了最方便的选择。把控开销即可，不必自责。"
        )
    }

    /**
     * 特种兵短途旅行三段式反馈
     * @return Triple(体感反馈, 闪光点, 小镇评述)
     */
    fun describeTravelSprint(): Triple<String, String, String> {
        return Triple(
            "周末紧凑赶路打卡景点，行程匆忙，旅途结束身体疲惫，内心却收获了新鲜感。",
            "利用有限的休息时间，跳出城市环境，短暂切换生活节奏。",
            "工作日被工作占满，整块休假时间稀缺。紧凑的短途旅行，是忙碌上班族平衡生活的折中选择——虽然没有度假的悠闲，但至少为自己攒了一点点新鲜感。这份精打细算的出行，也是认真对待生活的样子。"
        )
    }

    // ============================================
    // v1.3 时薪-物品价值锐评：四段式第四层
    // ============================================

    /**
     * 时薪价值锐评 —— 客观计算劳动成本，不禁止消费，只摆事实。
     *
     * @param itemPrice 物品价格（元）
     * @param workHourCost 购置此物品所需劳动小时数
     * @param hourlyWage 用户当前时薪
     * @param category 物品类别（food/clothing/entertainment/daily/medical）
     * @return 第四段：时薪锐评文案
     */
    fun describeHourlyRateCommentary(
            itemPrice: Double,
            workHourCost: Double,
            hourlyWage: Double,
            category: String,
            /** 无收入者的身份类型（housewife/adult_child/retired_worker/fresh_graduate/childhood_self） */
            identityType: String = ""
        ): String {
            if (hourlyWage <= 0.0) {
                return describeZeroIncomeCommentary(itemPrice, category, identityType)
            }

        val hours = String.format("%.1f", workHourCost)
        val wage = String.format("%.0f", hourlyWage)

        return when (category) {
            "food" -> {
                when {
                    workHourCost <= 0.5 ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长——性价比较高的饱腹选择，日常吃不会心疼。"
                    workHourCost <= 2.0 ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长。一顿稍微像样的饭，是你对自己辛苦工作的基本照顾。"
                    else ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长。这顿饭需要你付出大半天的劳动来换取——偶尔犒劳自己是应该的，但也要知道它在你月收入中的比重。"
                }
            }
            "clothing" -> {
                when {
                    workHourCost <= 3.0 ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长。实用穿着的投入是必要的——衣服保护着你，让你在工作和生活中体面地站着。"
                    workHourCost <= 10.0 ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长。这件衣物需要你付出一天多的劳作。穿得舒服很重要，但也要分清：你买的是衣服本身的价值，还是标签和营销带来的短暂满足。"
                    else ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长——相当于好几天的辛勤工作。高品质的衣物可以陪你很久，但如果只是为了追赶潮流而透支太多工时，或许可以再想一想。"
                }
            }
            "entertainment" -> {
                when {
                    workHourCost <= 2.0 ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长——花得起，也不用多想。短期的情绪放松是你的正当需求。"
                    workHourCost <= 10.0 ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长。这笔时长，兑换的是短期热烈的快乐。热爱值得买单，但也可以算一算：这份快乐能陪你多久？"
                    else ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长——相当于好几天的劳动付出。短暂的快乐本身没有错，但当工资预算紧张时，可以看看这份快乐是否能被更平价的替代方式满足。"
                }
            }
            "daily" -> {
                when {
                    workHourCost <= 1.0 ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长。低成本的日常刚需，花得踏实、用得安心。"
                    workHourCost <= 5.0 ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长。日常用品讲究实用和耐用——质量好一点、用得久一点，长期算下来反而是更合算的选择。"
                    else ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长。这笔开支在你的月度支出中占了一定份量。看重它实际能给你带来什么，比看重它的价格标签更有意义。"
                }
            }
            "medical" -> {
                when {
                    workHourCost <= 1.0 ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长。花在健康上的小钱，是最值得的长期投资——身体好了，才能继续扛起生活。"
                    else ->
                        "花费${itemPrice}元，折合约${hours}小时劳动时长。健康无价，但医药费是真实的。这笔支出不能省，也不该省——照顾好自己，比什么都重要。"
                }
            }
            else -> "花费${itemPrice}元，折合约${hours}小时劳动时长（时薪${wage}元/小时）。辛苦换来的劳动时间由你支配——看清代价，选择就是自由的。"
        }
    }

    /**
     * 计算物品对应的劳动小时数
     */
    fun calculateWorkHourCost(itemPrice: Double, hourlyWage: Double): Double {
            if (hourlyWage <= 0.0) return -1.0  // 负数表示需要降级文案
            return itemPrice / hourlyWage
        }

    /**
         * 无固定劳动收入者的消费评述（不展示工时换算，用处境描述替代）
         *
         * 覆盖人群：全职妈妈、全职儿女、退休老人、应届求职期、童年自己
         * 规则：时薪为 0 时，不再展示劳动工时换算，直接降级为处境评述。
         * 文案方向：侧重生活状态、情绪感受、处境评述，规避无效数值计算。
         */
        fun describeZeroIncomeCommentary(
            itemPrice: Double,
            category: String,
            identityType: String
        ): String {
            val priceText = String.format("%.0f", itemPrice)
            return when (identityType) {
                "housewife" -> {
                    val baseText = "这件东西${priceText}元。你没有个人的工资单，但你撑起了一整个家——这份劳动的价值，不是数字能算出来的。"
                    when (category) {
                        "food" -> "$baseText 一笔饭钱，换来一家人的饱足。钱是家里共有的，温暖也是。"
                        "daily" -> "$baseText 日用品是用来过日子的——日子过得下去、过得舒服，就是最好的投资。"
                        else -> baseText
                    }
                }
                "adult_child" -> {
                    val baseText = "这件东西${priceText}元。你目前没有个人收入，生活开销来自家庭。这不是「啃老」——是你选择了另一种为家庭付出的方式。"
                    when (category) {
                        "medical" -> "$baseText 花在爸妈健康上的钱，你从来不犹豫。这是你的KPI，比任何公司的考核都重要。"
                        "food" -> "$baseText 买菜时你会精打细算——这顿饭爸要吃得动、妈要吃得下。你用另一种方式在承担家庭责任。"
                        else -> baseText
                    }
                }
                "retired_worker" -> {
                    val baseText = "这件东西${priceText}元。你用养老金支付这笔开销。干了一辈子，现在花的每一分钱，都是你年轻时攒下的。不亏欠任何人。"
                    when (category) {
                        "medical" -> "$baseText 对退休的人来说，健康就是最大的财富。花在身体上的钱，值。"
                        "food" -> "$baseText 退休后吃饭不赶时间了，细嚼慢咽，品味每一口。年轻的时候没这个福气。"
                        "entertainment" -> "$baseText 前半生都在赶，现在终于可以不赶了。花钱买的是从容，不是东西本身。"
                        else -> baseText
                    }
                }
                "fresh_graduate" -> {
                    val baseText = "这件东西${priceText}元。你目前靠储蓄和家里补贴生活，没有固定收入。这是每个人都会经历的阶段——它不是你的能力问题，只是一个时间问题。"
                    when (category) {
                        "food" -> "$baseText 吃便宜的不丢人。在找工作的日子里，每一顿饭都是撑住自己的燃料。先把肚子填饱，未来会来的。"
                        "clothing" -> "$baseText 面试需要体面的衣服，但体面不等于贵。一件干净平整的白衬衫，能让你挺直腰走进去——这才是最重要的。"
                        else -> baseText
                    }
                }
                "childhood_self" -> {
                    "这件东西${priceText}元。你还是个孩子，钱不是你关心的事。你现在关心的是好不好玩、好不好吃、好不好看——这些就够了。长大以后的事，长大以后再说。"
                }
                else -> {
                    "这件东西${priceText}元。你目前没有计时劳动收入，但你的时间和精力本身就有价值。这笔消费在你的生活开支中占了多少比重，只有你自己最清楚。"
                }
            }
        }

    // ============================================
    // v1.4 工时-身心疲劳联动系统 文案
    // ============================================

    /**
     * 描述当前疲劳状态
     */
    fun describeFatigue(fatigueLevel: Int, overtimeStreak: Int): String = when {
        fatigueLevel <= 30 -> "身体状态不错，精力充沛"
        fatigueLevel <= 55 -> {
            if (overtimeStreak >= 3) "有点累了，连续加了几天班，身体在提醒你该歇歇了"
            else "有一点累，但不影响正常生活"
        }
        fatigueLevel <= 75 -> {
            when {
                overtimeStreak >= 7 -> "连续加班一周多了，腰酸背痛，睡眠也不太踏实。身体在用它的方式告诉你：该停一停了"
                overtimeStreak >= 3 -> "身体开始发出信号了——腰背酸痛，精力跟不太上"
                else -> "明显感到疲惫，需要好好休息一下"
            }
        }
        fatigueLevel <= 90 -> {
            when {
                overtimeStreak >= 14 -> "你已经连续加班半个月了。浑身酸痛、睡眠很差、有时候心跳会突然快起来。身体在透支"
                else -> "很累很累了，浑身不舒服，晚上也睡不好。这不是普通的累，是身体在说它撑不住了"
            }
        }
        else -> "极度疲惫，身体已经透支。心慌、失眠、做什么都提不起劲。你需要停下来，现在就需要"
    }

    /**
     * 描述疲劳相关的身体症状
     */
    fun describeFatigueSymptoms(symptoms: List<String>): String {
        if (symptoms.isEmpty()) return ""
        return "最近身体有些信号：${symptoms.joinToString("、")}。"
    }

    /**
     * 描述恢复活动的感受
     */
    fun describeRecoveryActivity(type: FatigueBusiness.RecoveryType): String = when (type) {
        FatigueBusiness.RecoveryType.SLEEP -> "你好好睡了一觉。醒来时身体轻了很多，那些累积的疲劳消解了大半"
        FatigueBusiness.RecoveryType.LEISURE -> "你看了一部喜欢的电影/翻了翻书/听了会儿歌。紧绷的神经慢慢松弛下来"
        FatigueBusiness.RecoveryType.FOOD -> "你找了一家想吃很久的店，坐下来慢慢吃完。食物暖了胃，也暖了心"
        FatigueBusiness.RecoveryType.WALK -> "你在外面走了走。风吹在脸上，路上的人来来往往，世界不只是工位和出租屋那么大"
        FatigueBusiness.RecoveryType.SOCIAL -> "你和朋友聊了很久，说了说最近的累、最近的烦。有人听着，压在心里的东西就轻了一半"
    }

    /**
     * 描述生活路径选择
     */
    fun describeLifePathChoice(lifePath: LifePathType): String = when (lifePath) {
        LifePathType.HUSTLE -> "你选择了奋斗的路。多干活、多挣钱，身体累一点也认了。这是你的选择，没有人能评判对错"
        LifePathType.BALANCED -> "你选择了劳逸结合。该工作工作，该休息休息。在收入和健康之间取一个平衡，是很多人想要的状态"
        LifePathType.REST -> "你选择了躺平。少干一点，多留点时间给自己。收入是会少一些，但活得轻松。这也是你认真做出的决定"
    }

    /**
     * 描述过度劳累的长期后果（中年阶段）
     */
    fun describeLongTermDamage(damages: List<String>): String {
        if (damages.isEmpty()) return ""
        val prefix = "长年累月的高强度工作，身体慢慢显现了一些变化："
        return "$prefix ${damages.joinToString("。")}。这些不是你的错，是生活一点点攒下来的"
    }

    /**
     * 描述季节对工作的影响
     */
    fun describeSeasonalWork(month: Int): String = FatigueBusiness.getSeasonalDescription(month)

    /**
     * 描述一日工作后的状态总结
     */
    fun describeWorkDaySummary(
        workHours: Double,
        fatigueGain: Int,
        currentFatigue: Int,
        monthlyIncome: Double
    ): String {
        val fatigueDesc = when {
            fatigueGain <= 20 -> "不算太累"
            fatigueGain <= 35 -> "有点累了"
            fatigueGain <= 50 -> "今天挺累的"
            else -> "今天真的很累"
        }
        val fatigueWarning = when {
            currentFatigue > 85 -> "，身体已经在发出严重的警告信号"
            currentFatigue > 70 -> "，该考虑休息一下了"
            currentFatigue > 55 -> "，明天如果还这么高强度，可能会吃不消"
            else -> ""
        }
        return "${fatigueDesc}。今天工作了${String.format("%.1f", workHours)}小时，月收入${String.format("%.0f", monthlyIncome)}元${fatigueWarning}。辛苦换来的每一分钱，都值得被认真对待"
    }

    /**
     * 描述工作日的心理感受（用于每日开始时的自然语言）
     */
    fun describeWorkDayMood(fatigueLevel: Int, overtimeStreak: Int): String = when {
        overtimeStreak >= 14 -> "已经是连续加班的第${overtimeStreak}天了。你有点麻木，也有点恍惚"
        overtimeStreak >= 7 -> "连续加班的第${overtimeStreak}天。身体在抗议，但你还是起床了"
        overtimeStreak >= 3 -> "又一天。连续加班几天了，说实话有点累"
        fatigueLevel > 75 -> "今天起床的时候，全身都在疼。但还是得去"
        fatigueLevel > 55 -> "有点不想起床，但最终还是起来了。成年人的每一天，都是这样撑过来的"
        fatigueLevel > 30 -> "新的一天。不算特别有精神，但也不差"
        else -> "今天感觉不错，精力恢复得差不多了"
    }

    // ============================================
    // v1.5 可伸缩爱好体系 文案
    // ============================================

    /**
     * 描述可伸缩爱好在不同设备层级下的体验
     *
     * 核心原则：不因为设备贵贱而贬低任何人的感受。
     * 老年机拍的落日和哈苏拍的山海，记录的都是那一刻想留住的心情。
     */
    fun describeScalableHobby(hobby: ScalableHobbyConfig, tier: HobbyTier): String {
        val config = hobby.tiers[tier] ?: return "你用自己的方式参与${hobby.name}。"
        val tierLabel = when (tier) {
            HobbyTier.FREE -> "不需要任何设备，随手就能来"
            HobbyTier.BUDGET -> "花一点小钱，就能正经开始"
            HobbyTier.STANDARD -> "有了一套基础装备，体验完整了很多"
            HobbyTier.PREMIUM -> "品质装备让体验更上一层"
            HobbyTier.COLLECTOR -> "装备本身就成了乐趣的一部分"
        }
        return "${hobby.name}——${tierLabel}。${config.typicalUserDescription}。${hobby.essenceDescription}。"
    }

    /**
     * 可伸缩爱好的小镇评述 —— 承认设备差异，但不承认感受有高低
     */
    fun describeScalableHobbyCommentary(hobby: ScalableHobbyConfig, userTier: HobbyTier): String {
        val higherTiers = HobbyTier.entries.filter { it.ordinal > userTier.ordinal }
        return if (higherTiers.isNotEmpty()) {
            val higherTierNames = higherTiers.joinToString("、") { it.label }
            "${hobby.name}这件事，往上还有${higherTierNames}的选择。但${hobby.essenceDescription}。不管用什么设备，你在那一刻感受到的东西，分量是一样的。"
        } else {
            "你已经在${hobby.name}这条路上走到了收藏级。但${hobby.essenceDescription}。回到最初随手拍下路边野花的那一刻，那种简单的快乐，和现在用顶级器材的快乐，其实是同一种东西。"
        }
    }

    /**
     * 对比两个爱好层级，展示差异但不下判断
     */
    fun describeHobbyTierGap(
        hobby: ScalableHobbyConfig,
        lowerTier: HobbyTier,
        higherTier: HobbyTier,
        lowerUserHourlyWage: Double
    ): String {
        val lowerConfig = hobby.tiers[lowerTier] ?: return ""
        val higherConfig = hobby.tiers[higherTier] ?: return ""

        val equipmentGap = higherConfig.equipmentCost - lowerConfig.equipmentCost
        val workHours = if (lowerUserHourlyWage > 0) {
            String.format("%.1f", equipmentGap / lowerUserHourlyWage)
        } else "一些"

        return when {
            equipmentGap <= 0 -> "你现在的设备和${higherTier.label}级别没有差距。"
            equipmentGap < 100 -> "升到${higherTier.label}级别，需要多花${String.format("%.0f", equipmentGap)}元。差距不大，如果想试试，随时可以。"
            equipmentGap < 1000 -> "从${lowerTier.label}到${higherTier.label}，设备差价约${String.format("%.0f", equipmentGap)}元，折合${workHours}小时劳动时长。值不值得，只有你自己能判断。"
            else -> "从${lowerTier.label}升到${higherTier.label}，设备差价约${String.format("%.0f", equipmentGap)}元，需要付出${workHours}小时的劳动。这笔钱可以升级设备，也可以花在别的地方。两种选择，没有对错。"
        }
    }

    // ============================================
    // v1.5 全球公共文娱事件 文案
    // ============================================

    /**
     * 描述公共文娱事件及其参与方式
     */
    fun describeCollectiveEvent(event: CollectiveEventType, accessDescription: String, estimatedCost: Double): String {
        val costText = when {
            estimatedCost <= 0 -> "不花钱就能参与"
            estimatedCost < 50 -> "花大概${String.format("%.0f", estimatedCost)}元"
            estimatedCost < 500 -> "花大概${String.format("%.0f", estimatedCost)}元"
            else -> "这笔花费不低，约${String.format("%.0f", estimatedCost)}元"
        }
        return "${event.label}——${event.description}。你${accessDescription}，${costText}。${event.frequency}。"
    }

    /**
     * 公共事件的小镇评述 —— 承认集体体验的独特价值
     */
    fun describeCollectiveEventCommentary(event: CollectiveEventType): String = when (event) {
        CollectiveEventType.WORLD_CUP ->
            "世界杯期间，不管你在城中村出租屋用手机看，还是在VIP包厢看现场，那一刻你和全世界的人一起屏住呼吸。这种集体情绪共振，是钱买不来的东西。"
        CollectiveEventType.OLYMPICS ->
            "奥运会不只是体育比赛，是国家荣誉感的集体释放。你看中国队夺金时激动的心情，和坐在现场的人一模一样。"
        CollectiveEventType.FILM_AWARDS ->
            "颁奖礼上的泪水、争议和感动，你在手机屏幕前刷到的，和坐在现场感受到的，是同一种对电影的热爱。"
        CollectiveEventType.FASHION_SHOW ->
            "走秀是一种视觉艺术。你在社交媒体上刷到的秀场片段，和坐在前排看的人看到的是同一件衣服。审美的门槛不在门票，在眼睛。"
        CollectiveEventType.GLOBAL_TOURNAMENT ->
            "欧冠决赛、NBA总决赛、LOL全球总决赛……跨越时区、跨越阶层的集体狂欢。你支持的队伍赢了，全世界都亮了。"
        CollectiveEventType.MUSIC_FESTIVAL ->
            "音乐节是年轻人的集体情绪释放。草地、舞台、人群——不管你在前排还是后排，音乐响起来的那一刻，所有人都在同一个节奏里。"
    }

    /**
     * 四段式公共事件体验：体感-集体价值-时薪消耗-小镇评述
     */
    fun describeCollectiveEventFull(
        event: CollectiveEventType,
        accessDescription: String,
        estimatedCost: Double,
        hourlyWage: Double
    ): String {
        val workHours = if (hourlyWage > 0 && estimatedCost > 0) {
            String.format("%.1f", estimatedCost / hourlyWage)
        } else null

        val costLine = if (workHours != null) {
            "花费${String.format("%.0f", estimatedCost)}元，折合${workHours}小时劳动时长。"
        } else {
            if (estimatedCost > 0) "花费${String.format("%.0f", estimatedCost)}元。" else "不花钱。"
        }

        return "${event.description}。你${accessDescription}。${costLine}${describeCollectiveEventCommentary(event)}"
    }

    // ============================================
    // v1.6 职业圈层认知局限 文案
    // ============================================

    /**
     * 描述一个职业的固有认知局限
     *
     * @param pathName 职业名称
     * @param limitation 认知局限描述
     * @param explanation 成因解释
     * @return 完整的认知局限说明（用于信息面板展示）
     */
    fun describeCognitiveLimitation(pathName: String, limitation: String, explanation: String): String {
        return "【${pathName}的视角局限】${limitation}\n\n成因：${explanation}"
    }

    /**
     * 跨职业对话触发通知
     *
     * @param dialogue 对话场景
     * @return 简洁的触发提示
     */
    fun describeDialogueTrigger(dialogue: CrossProfessionDialogue): String {
        return "${dialogue.triggerContext}，${dialogue.speakerName}对你说：「${dialogue.speakerOpeningLine}」"
    }

    /**
     * 玩家选择回应后的反馈文案
     *
     * @param choiceLabel 选择的标签（认同对方 / 说出事实 / 沉默聆听）
     * @param speakerName 发言者名称
     * @return 选择后的反馈
     */
    fun describeDialogueChoiceResult(
        choiceLabel: String,
        speakerName: String
    ): String = when (choiceLabel) {
        "认同对方" -> "你没有多说什么。${speakerName}也许永远不知道你的真实处境——但这没关系。不是每个人都需要被理解，有时候保持友善就够了。"
        "说出事实" -> "你说出了自己的真实处境。${speakerName}没有再说什么——也许他需要一点时间消化。打破认知局限的第一步，就是让对方看见。"
        "沉默聆听" -> "你选择了沉默。有时候不解释不是软弱——是有些累，不值得花力气去解释。${speakerName}带着他的看法离开，你带着你的生活继续。"
        else -> "对话结束了。"
    }

    /**
     * 多次跨圈层接触后的认知成长描述
     *
     * @param contactsWithDifferentGroups 接触不同群体的次数
     * @return 认知迭代描述
     */
    fun describeCognitiveGrowth(contactsWithDifferentGroups: Int): String = when {
        contactsWithDifferentGroups <= 0 -> "你目前接触的人群和你自己很像——这意味着你看到的，可能只是世界的一面。"
        contactsWithDifferentGroups <= 3 -> "你开始接触到不同职业的人。有些看法和你预想的很不一样——这很正常，每个人看到的都只是自己生活的那一小块。"
        contactsWithDifferentGroups <= 10 -> "你已经和好几种不同职业的人深入交流过。你发现以前的一些判断其实站不住脚——不是因为你错了，是因为你终于看到了自己圈子之外的世界。"
        contactsWithDifferentGroups <= 20 -> "你的阅历在增长。每接触一个不同的群体，你原有的认知就被拓宽一点。不是变聪明了——是看到了更多人的生活。"
        else -> "你接触过很多不同的人。你明白了一个道理：没有人是「正确」的，每个人都只是从自己的位置往外看。偏见不是品格的问题，是经历的问题。"
    }

    // ============================================
    // v1.7 孩童行为场景 文案
    // ============================================

    /**
     * 孩童行为场景触发通知
     */
    fun describeChildSceneTrigger(scene: ChildBehaviorScene): String {
        return scene.sceneImage
    }

    /**
     * 玩家应对孩童行为后的反馈文案
     */
    fun describeChildResponseResult(
        response: String,
        scene: ChildBehaviorScene,
        hourlyWage: Double
    ): String {
        val workMinutes = scene.extraCleanupMinutes + scene.itemType.typicalCleanupMinutes
        val workHoursForCost = if (hourlyWage > 0 && scene.actualLossCost > 0) {
            String.format("%.1f", scene.actualLossCost / hourlyWage)
        } else null

        return when (response) {
            "SCOLD" -> {
                val lossLine = if (workHoursForCost != null) {
                    "损耗了${String.format("%.0f", scene.actualLossCost)}元，折合${workHoursForCost}小时劳动时长。"
                } else ""
                "你冲口而出的责备，是疲惫积攒后的应激反应。${lossLine}发火不是你的本性——是今天太累了。但孩子眼里的光暗了一下，也许你会在某个安静的时刻想起那个眼神。"
            }
            "PATIENT_TALK" -> {
                "你按下了情绪。蹲下来，和孩子一起收拾。你用行动告诉他：东西掉了没关系，捡起来就好了。这份耐心，是你在疲惫中为自己保留的温柔。"
            }
            "SILENT_CLEANUP" -> {
                "你没有说话，只是默默地收拾。不责怪，也不说教。孩子在一旁看着，也许不太明白你在想什么——但沉默中，有一种比语言更深的包容。"
            }
            else -> "你收拾好东西，生活继续。"
        }
    }

    /**
     * 孩童行为场景的时薪锐评（供UI展示）
     */
    fun describeChildSceneWorkCost(scene: ChildBehaviorScene, hourlyWage: Double): String {
        val totalMinutes = scene.extraCleanupMinutes + scene.itemType.typicalCleanupMinutes
        val workHours = if (hourlyWage > 0) {
            String.format("%.1f", totalMinutes / 60.0)
        } else "一些"

        val lossText = if (scene.actualLossCost > 0 && hourlyWage > 0) {
            val lossHours = String.format("%.1f", scene.actualLossCost / hourlyWage)
            "，损耗物品折合${lossHours}小时劳动时长"
        } else ""

        return "本次收拾耗时约${totalMinutes}分钟，折合${workHours}小时劳动时间${lossText}。"
    }

    /**
     * 长期孩童互动后的心态变化描述
     *
     * @param patienceLevel 耐心值（0-100）
     * @param childInteractions 与孩童互动的次数
     */
    fun describeChildInteractionGrowth(patienceLevel: Int, childInteractions: Int): String = when {
        childInteractions <= 0 -> ""
        childInteractions <= 3 -> "你开始习惯了生活里偶尔有小孩子捣乱。有时候烦，但转念一想——谁小时候不是这样呢。"
        childInteractions <= 10 -> "你发现自己的耐心比以前好了。不是不累了，是慢慢学会了在累的时候，先停下来，看看孩子眼里的那个世界。"
        childInteractions <= 30 -> "你已经和孩子相处了很多次。当初容易急躁的你，现在能先蹲下来再说。不是变温柔了——是你看懂了，孩子不是故意的。"
        else -> "你陪孩子走过了很多日子。你知道，孩子是一面镜子——照见你当下的疲惫，也照见你曾经拥有的纯粹。"
    }

    /**
     * 童年回望支线——玩家作为孩童视角的独白
     */
    fun describeChildhoodPerspective(behavior: String): String = when (behavior) {
        "HELP_PICK_UP" -> "你蹲在地上，一颗一颗捡起散落的果子。没有想太多——只是看见东西掉了，就想帮忙捡起来。大人在旁边看着你，你不知道他们在想什么。你只是觉得，捡完了就好了。"
        "PLAYFUL_MISCHIEF" -> "小球弹得到处都是，你忍不住伸手去拍。它们跳起来的样子太好笑了，你咯咯地笑。大人好像在叹气——但你不知道他们在叹什么。在你眼里，这个世界到处都是好玩的东西。"
        else -> ""
    }

    // ============================================
    // v2.0 时代成本系统 文案（第五段：时代成本解析）
    // ============================================

    /**
     * 通胀对购买力的影响描述
     *
     * @param basePrice 基准年价格
     * @param currentPrice 当前年价格
     * @param category 品类
     * @param baseYear 基准年
     * @param currentYear 当前年
     */
    fun describeInflationEffect(
        basePrice: Double,
        currentPrice: Double,
        category: String,
        baseYear: Int,
        currentYear: Int
    ): String {
        if (currentYear <= baseYear) return ""
        val yearsPassed = currentYear - baseYear
        val increase = ((currentPrice / basePrice - 1.0) * 100).toInt()
        val categoryLabel = when (category) {
            "food" -> "食品"
            "rent" -> "房租"
            "medical" -> "医疗"
            "education" -> "教育"
            else -> "物价"
        }
        return "过去${yearsPassed}年，${categoryLabel}整体上涨了约${increase}%。同样的劳动时长，现在能买到的东西比${baseYear}年少了。"
    }

    /**
     * 第五段：时代成本解析
     *
     * 客观陈述时代环境带来的额外成本，不评判，不渲染焦虑。
     */
    fun describeEraCostCommentary(
        scenario: String,
        personalWorkHours: Double,
        eraCostHours: Double,
        hourlyWage: Double,
        context: String = ""
    ): String {
        if (eraCostHours <= 0) return ""

        val personalHours = String.format("%.0f", personalWorkHours)
        val eraHours = String.format("%.0f", eraCostHours)

        return when (scenario) {
            "housing" -> "对比十年前同城市住房价格，整体房价上涨，同等房型所需工时相较前代多出约${eraHours}小时。多出的这部分工时，为通胀、城市发展带来的时代成本——个人勤奋无法抵消整体环境变化。"
            "industry_decline" -> {
                "传统行业衰败属于宏观产业迭代，并非个人工作懈怠。行业变革带来的失业和转行成本，是时代进程里被动承受的代价。${context}"
            }
            "employment" -> {
                "当前就业环境下，${context}。同等学历和能力的求职者，需要付出更多工时才能达到前几年的同等生活质量。这并非个人能力问题，是时代整体环境变化拉高了生存门槛。"
            }
            "inflation" -> "${context}个人时薪不变的情况下，购买力逐年缩水。这部分多出的生存消耗，由时代整体通胀决定，与个人努力无关。"
            else -> "个人工时成本${personalHours}小时之外，时代环境额外增加了约${eraHours}小时的生存成本。个人勤勉可以优化自己的收支规划，但时代整体环境带来的生存门槛变化，是一代人共同面对的处境。"
        }
    }

    /**
     * 五段式完整输出：体感 → 闪光点 → 个人工时锐评 → 时代成本解析 → 小镇评述
     *
     * @param bodyFeeling 第一段：体感反馈
     * @param sparkle 第二段：闪光点
     * @param personalCost 第三段：个人工时锐评
     * @param eraCost 第四段：时代成本解析（可为空）
     * @param townCommentary 第五段：小镇评述
     */
    fun describeFiveSegmentFull(
        bodyFeeling: String,
        sparkle: String,
        personalCost: String,
        eraCost: String,
        townCommentary: String
    ): String {
        val eraSection = if (eraCost.isNotEmpty()) "\n\n【时代成本】${eraCost}" else ""
        return "【体感】${bodyFeeling}\n\n【闪光点】${sparkle}\n\n【个人工时】${personalCost}${eraSection}\n\n【小镇评述】${townCommentary}"
    }

    /**
     * 行业生命周期阶段描述（供UI展示）
     */
    fun describeIndustryStage(pathId: String, currentYear: Int): String {
        val industry = IndustryLifecycles.findByPathId(pathId) ?: return ""
        val stage = industry.getStageDescription(currentYear)
        val multiplier = industry.getSalaryMultiplier(currentYear)
        val multiplierText = if (multiplier < 1.0) {
            "当前行业薪资系数${String.format("%.2f", multiplier)}，低于基准水平。"
        } else {
            ""
        }
        return "【${industry.displayName}】${stage}。${multiplierText}"
    }

    /**
     * 代际成本对比描述
     */
    fun describeGenerationalGap(gap: GenerationalCostGap, itemName: String): String {
        val parentHours = String.format("%.0f", gap.parentWorkHours)
        val childHours = String.format("%.0f", gap.childWorkHours)
        val eraHours = String.format("%.0f", gap.eraCostHours)
        val ratio = String.format("%.0f", gap.eraCostRatio * 100)

        return "父辈在${gap.parentYear}年购置${itemName}，需要${parentHours}小时劳动时长。" +
                "你在${gap.childYear}年购置同等${itemName}，需要${childHours}小时劳动时长。" +
                "多出的${eraHours}小时（约占${ratio}%），是通胀和时代环境变化带来的代际成本。"
    }

    /**
     * 时代成本系统的小镇评述（通用）
     *
     * 核心信息：个人勤勉有意义，但时代环境是更大的框架。
     * 不渲染无力感，不否定个人努力。
     */
    fun describeEraCostTownCommentary(): String {
        return "个人的勤勉可以优化自己的收支规划，但时代整体环境带来的生存门槛变化，是一代人共同面对的处境。承认时代局限，不是放弃努力——是看清了自己在多大框架里做选择。一代人的选择，始终嵌套在时代的洪流之中。"
    }

    /**
     * 行业衰落后的转行评述
     */
    fun describeCareerTransitionCommentary(
        oldIndustry: String,
        newIndustry: String,
        transitionHours: Double
    ): String {
        val hours = String.format("%.0f", transitionHours)
        return "从${oldIndustry}转向${newIndustry}，需要额外投入${hours}小时学习新技能。这不是你个人的失败——行业更迭是时代进程的一部分。有人在浪潮中失去方向，也有人在转型中找到新的路。"
    }

    /**
     * 疲劳微事件描述（V2.0 概率调优后的小提醒）
     *
     * 不是警告，是描述。身体在告诉你它累了。
     */
    fun describeFatigueEvent(symptom: String, fatigueLevel: Int): String {
        val prefix = when {
            fatigueLevel > 80 -> "身体在发出信号了——"
            fatigueLevel > 60 -> "你注意到了吗——"
            else -> ""
        }
        return "${prefix}${symptom}。歇一歇吧，不用硬撑。"
    }

    // ============================================
    // v2.1 科研与技术流通体系 文案
    // ============================================

    /**
     * 科研出身开局描述
     */
    fun describeResearchOrigin(origin: ResearcherOrigin): String {
        return ResearcherOriginConfigs.configs[origin]?.openingDescription ?: ""
    }

    /**
     * 技术成果全局影响描述
     */
    fun describeTechGlobalImpact(impact: TechGlobalImpact): String {
        val priceText = if (impact.priceImpactPercent > 0) {
            "物价上涨${String.format("%.0f", impact.priceImpactPercent)}%"
        } else {
            "物价下降${String.format("%.0f", Math.abs(impact.priceImpactPercent))}%"
        }
        return "${impact.description}。${priceText}。新增${impact.newJobsCreated}个岗位，同时${impact.oldJobsLost}个传统岗位可能缩减。" +
                "对底层人群的影响：${impact.impactOnWorkingClass}"
    }

    /**
     * 科研职业分支切换评述
     */
    fun describeResearchBranchSwitch(from: ResearchCareerBranch, to: ResearchCareerBranch): String {
        return "你决定从「${from.label}」转向「${to.label}」。这不是放弃——是在看清了自己的条件和环境的约束之后，做出的务实选择。人生没有白走的路，实验室里学到的思维方式，放在任何地方都有用。"
    }

    /**
     * 场景一：中产基础科研人员实验突破（五段式完整文案）
     */
    fun describeAffluentResearchBreakthrough(workHours: Double, impact: TechGlobalImpact): String {
        val hours = String.format("%.0f", workHours)
        return "【体感】熬过多年沉寂的实验室岁月，前沿技术试验终于达标。既满怀成就感，又顾虑落地之后社会短期变化。\n\n" +
                "【闪光点】依托家庭经济兜底，不计短期收入得失，沉下心深耕长线基础研究，填补行业技术空白。\n\n" +
                "【个人工时】累计${hours}小时个人工时投入，多年薪资微薄，依靠家庭储备覆盖日常开支。\n\n" +
                "【时代成本】${impact.description}。长期催生运维、智能调试新岗位，整体拉高城市薪资上限。\n\n" +
                "【小镇评述】优渥的家庭条件为他提供了潜心科研的底气，宏观技术革新势必要承担阶段性代价。他能看见数十年后的产业远景，却很难切身体会底层劳动者当下失业的窘迫——这是长期实验室环境形成的视角局限。"
    }

    /**
     * 场景二：寒门应用型研发者节能设备研发（五段式完整文案）
     */
    fun describePoorResearcherDevice(workHours: Double, extraWorkHours: Double): String {
        val hours = String.format("%.0f", workHours)
        val extra = String.format("%.0f", extraWorkHours)
        return "【体感】一边推进技术实验，一边计算项目预算。顾虑投产之后产品定价过高，普通家庭无力承担。\n\n" +
                "【闪光点】亲历过拮据生活，在技术研发阶段，主动兼顾普通民众消费承受能力。\n\n" +
                "【个人工时】多年求学期间兼职补贴生活，额外付出${extra}小时打工工时，才走完求学阶段。累计研发工时约${hours}小时。\n\n" +
                "【时代成本】节能设备普及长期降低居民水电开支，但前期投产资金有限，投产规模小，短期降价空间不足。\n\n" +
                "【小镇评述】出身带来的生存经历，让他懂得普通人的生活难处，可资金短板时刻束缚着技术落地速度。先天开局的差距，从求学阶段就已经划定了一部分现实边界。"
    }

    /**
     * 场景三：合规技术经纪人对接投产（五段式完整文案）
     */
    fun describeTechBrokerDeal(workHours: Double): String {
        val hours = String.format("%.0f", workHours)
        return "【体感】敲定专利授权合作，规划投产与销售渠道。思考如何平衡厂商利润和大众售价。\n\n" +
                "【闪光点】打通科研与市场之间的断层，让实验室技术走出试验室，走入普通人的日常生活。\n\n" +
                "【个人工时】前期调研、商务谈判投入${hours}工时，依靠专利销售分成获得收益。\n\n" +
                "【时代成本】技术商业化落地周期大幅缩短，新技术普及速度加快，整体优化行业产能。\n\n" +
                "【小镇评述】科研人员不懂市场运作，商人补齐了流通环节。商人优先考量盈利收益，和科研人员理想主义的思维分歧，是两种职业环境催生的正常差异。"
    }

    /**
     * 场景四：投机套利者垄断市场（五段式完整文案）
     */
    fun describeSpeculatorMonopoly(workHours: Double, priceImpactPercent: Double): String {
        val hours = String.format("%.0f", workHours)
        val percent = String.format("%.0f", priceImpactPercent)
        return "【体感】垄断本地货源之后，商品定价上涨约${percent}%，短期盈利大幅上涨。同时也察觉到居民消费压力增大。\n\n" +
                "【闪光点】快速复刻成熟技术，缩短了技术区域落地周期。\n\n" +
                "【个人工时】省去前期研发数千小时的实验投入，仅投入仿制、渠道运营约${hours}工时，短期盈利效率极高。\n\n" +
                "【时代成本】垄断抬高本地物价，区域通胀小幅上涨，刚需消费品开支增加，底层人群储蓄购买力缩水。长期会倒逼政策出台专利监管规则，约束投机垄断行为。\n\n" +
                "【小镇评述】投机模式短期攫取高额收益，代价由本地普通居民承担。市场环境缺少监管时，逐利的商业选择会改变局部时代物价环境。个人抉择的利弊，长久向外辐射。"
    }

    // ============================================
    // v2.2 学术垄断与圈层固化 文案
    // ============================================

    /**
     * 场景一：中产科研新人身处学阀派系（五段式）
     */
    fun describeAcademicInsiderAdvantage(savedHours: Double): String {
        val hours = String.format("%.0f", savedHours)
        return "【体感】依托家族前辈人脉顺利拿到实验室名额，项目经费充足，可安心开展长期试验。但很多研究方向需要遵从派系整体规划，个人自主选题空间受限。\n\n" +
                "【闪光点】优渥圈层提供了科研起步必需的资源，省去争取经费、论文发表的大量内耗。\n\n" +
                "【个人工时】前期资源门槛由圈层人脉兜底，节省约${hours}小时争取资源的额外工时，资源红利显著。\n\n" +
                "【时代成本】派系固化之下，外来优秀人才难以进入核心项目，短期创新活力受限，前沿技术迭代节奏放缓。长期积压大量未落地的民间技术成果，积蓄行业变革的潜在变量。\n\n" +
                "【小镇评述】圈层人脉带来了便利，也形成了封闭壁垒。身处体系之内，容易将这套资源分配模式当作常态，难以看见圈外科研者寸步难行的处境——这便是长期圈层环境催生的认知盲区。"
    }

    /**
     * 场景二：寒门应用型科研者遭遇学术资源壁垒（五段式）
     */
    fun describePoorResearcherResourceBarrier(researchHours: Double, extraHours: Double): String {
        val totalResearch = String.format("%.0f", researchHours)
        val extra = String.format("%.0f", extraHours)
        return "【体感】数年实验取得技术突破，撰写论文后屡次被权威期刊退回。缺少学术引荐渠道，成果迟迟无法对外公开，内心充满无力感。家庭每月还在催促自己补贴家用，经济压力持续叠加。\n\n" +
                "【闪光点】即便资源受限，依旧坚持优化实验数据，守住了技术研发本身。\n\n" +
                "【个人工时】除基础研发${totalResearch}小时工时，额外耗费近${extra}小时奔走对接学术渠道，大量时间消耗在资源博弈环节。\n\n" +
                "【时代成本】个人创新成果被暂时封锁，新技术落地时间延后，区域产业升级节奏变慢。长期积压的民间技术成果，终将成为打破垄断的蓄力。\n\n" +
                "【小镇评述】凭借天赋熬过了求学阶段的贫困压力，却撞上学术圈层固化带来的壁垒。先天家境差距，在更高层级的行业竞争里，再次拉开了差距。个人足够努力，却难以对抗圈层长期形成的规则。"
    }

    /**
     * 场景三：资本收购专利后商业垄断抬升物价（五段式）
     */
    fun describePatentMonopolyImpact(researchHours: Double, priceImpactPercent: Double): String {
        val hours = String.format("%.0f", researchHours)
        val percent = String.format("%.0f", priceImpactPercent)
        return "【体感】专利被大企业收购，企业划定区域独家销售权限，市面产品定价上涨约${percent}%。自己研发普惠技术的初衷落空。\n\n" +
                "【闪光点】自身技术顺利商业化落地，个人获得专利分成收入。\n\n" +
                "【个人工时】前期研发约${hours}小时工时已经投入完毕，后续商业规则由资本方主导，个人很难干预定价策略。\n\n" +
                "【时代成本】刚需新产品溢价售卖，底层居民每月生活开支小幅上涨，购买力缩水。资本垄断改变了技术红利的分配方式——研发者拿到了钱，但普惠大众的初衷被打碎了。\n\n" +
                "【小镇评述】技术本身可以造福大众，资本垄断改变了红利分配方式。科研人员、资本方、普通民众三方，因商业规则承受截然不同的结果。时代进步的收益，并不一定会普惠所有人。"
    }

    /**
     * 学术垄断全局影响描述
     */
    fun describeAcademicMonopolyImpact(impact: AcademicMonopolyImpact): String {
        return "${impact.description}。受影响群体：${impact.affectedGroup}。${impact.longTermConsequence}"
    }

    /**
     * 学阀派系切换评述
     */
    fun describeFactionSwitch(from: AcademicFaction, to: AcademicFaction): String {
        return when {
            from == AcademicFaction.INDEPENDENT && to == AcademicFaction.INSIDER ->
                "你加入了学阀派系。经费和设备不再需要自己去争取了。同时你也清楚——从此之后，你的论文署名、你的研究方向，都要按照派系规则来。这不是妥协，是你在看清代价之后做出的选择。"
            from == AcademicFaction.INSIDER && to == AcademicFaction.INDEPENDENT ->
                "你退出了学阀派系，回到独立研究。经费和渠道都没有了，但你的研究方向完全由自己决定。你愿意用资源匮乏换取百分之百的自主权——这是你的选择，不后悔。"
            from == AcademicFaction.INDEPENDENT && to == AcademicFaction.CORPORATE ->
                "你接受了企业合作。经费充足了，设备到位了。但你知道——以后的研究方向会受资本方影响，长期基础研究的空间会被压缩。你有心理准备了。"
            else -> "你改变了学术道路的方向。每一种选择都有代价——你很清楚这一点。"
        }
    }

    // ============================================
    // v2.3 跨圈层相遇事件 文案
    // ============================================

    /**
     * 跨圈层相遇事件的五段式完整输出
     */
    fun describeCrossClassEncounter(encounter: CrossClassEncounter): String {
        return "【体感】${encounter.bodyFeeling}\n\n" +
                "【闪光点】${encounter.sparkle}\n\n" +
                "【个人工时】${encounter.personalCost}\n\n" +
                "【时代成本】${encounter.eraCost}\n\n" +
                "【小镇评述】${encounter.townCommentary}"
    }

    /**
     * 相遇事件触发通知
     */
    fun describeEncounterTrigger(encounter: CrossClassEncounter): String {
        return "【${encounter.scenario.label}】${encounter.triggerContext}。"
    }

    /**
     * 玩家选择相遇走向后的反馈文案
     */
    fun describeEncounterChoiceResult(
        encounter: CrossClassEncounter,
        choiceIndex: Int
    ): String {
        val choice = encounter.choices.getOrElse(choiceIndex) { return "你选择了沉默。" }
        return when (choice.outcome) {
            EncounterOutcome.SUPERFICIAL ->
                "【浅层相逢】${choice.responseText}\n\n${choice.cognitionChange}"
            EncounterOutcome.DEEP_INTEGRATION ->
                "【深度磨合】${choice.responseText}\n\n${choice.cognitionChange}"
            EncounterOutcome.STALEMATE ->
                "【观念僵持】${choice.responseText}\n\n${choice.cognitionChange}"
        }
    }

    /**
     * 相遇完成后的小镇收尾评述
     */
    fun describeEncounterClosing(outcome: EncounterOutcome): String = when (outcome) {
        EncounterOutcome.SUPERFICIAL ->
            "多数人的相遇就是这样——短暂停留，浅浅理解，然后继续各自的路。这不是遗憾，是人际交往里最寻常的样子。"
        EncounterOutcome.DEEP_INTEGRATION ->
            "你愿意花时间去理解一个和你完全不同的人。这份耐心，让两个原本隔阂的圈层，有了一点点交叠。不一定是改变世界的大事——但改变了自己看世界的方式。"
        EncounterOutcome.STALEMATE ->
            "不是所有人都能彼此理解。长久环境塑造的思维习惯，很难在一次相遇中扭转。你没有错——承认「我们不一样」，也是一种对自己的诚实。"
    }

    /**
     * 年龄对相遇包容度的影响描述
     */
    fun describeAgeTolerance(age: Int): String = when {
        age < 25 -> "年轻气盛，更坚持自己的看法，不容易向不同观点妥协。"
        age < 35 -> "开始意识到世界不止一种活法，面对观念差异时多了几分耐心。"
        age < 50 -> "阅历渐长，越来越能包容不同想法。你不急于说服谁——你知道每个人都有自己的来路。"
        else -> "半生已过，人与人之间的分歧在你眼里都有了来由。你不再轻易评判——因为你知道，每一种固执背后，都有它的出处。"
    }

    // ============================================
    // v2.4 日常细碎挫折 文案
    // ============================================

    /**
     * 细碎挫折事件的五段式完整输出
     */
    fun describeDailyFrustration(frustration: DailyFrustration): String {
        return "【${frustration.title}】${frustration.triggerHint}\n\n" +
                "【体感】${frustration.bodyFeeling}\n\n" +
                "【闪光点】${frustration.sparkle}\n\n" +
                "【个人工时】${frustration.personalCost}\n\n" +
                "【时代成本】${frustration.eraCost}\n\n" +
                "【小镇评述】${frustration.townCommentary}"
    }

    /**
     * 细碎挫折触发通知（简短版）
     */
    fun describeFrustrationTrigger(frustration: DailyFrustration): String {
        return "【${frustration.category.label}】${frustration.triggerHint}"
    }

    /**
     * 玩家选择应对方式后的反馈
     */
    fun describeFrustrationChoiceResult(
        frustration: DailyFrustration,
        choiceIndex: Int
    ): String {
        val choice = frustration.choices.getOrElse(choiceIndex) { return "你选择了沉默。" }
        return when (choice.coping) {
            FrustrationCoping.RUMINATE ->
                "【消极内耗】${choice.responseText}\n\n长期倾向：${choice.characterTendency}"
            FrustrationCoping.ACTIVE_FIX ->
                "【调整补救】${choice.responseText}\n\n长期倾向：${choice.characterTendency}"
            FrustrationCoping.REST ->
                "【放空休息】${choice.responseText}\n\n长期倾向：${choice.characterTendency}"
        }
    }

    /**
     * 细碎挫折收尾评述
     */
    fun describeFrustrationClosing(coping: FrustrationCoping): String = when (coping) {
        FrustrationCoping.RUMINATE ->
            "你选择了内耗——这不是错，这是人之常情。有时候我们就是需要花一点时间，才能从负面情绪里走出来。"
        FrustrationCoping.ACTIVE_FIX ->
            "你选择立刻行动。这种习惯会慢慢堆积成你的底气——每一次小失误后的调整，都在让你变得更从容。"
        FrustrationCoping.REST ->
            "你选择停下来喘口气。不是所有事都要立刻解决——你对自己温柔的这一下，就是最好的应对。"
    }

    /**
     * 长期累积焦虑提示
     */
    fun describeCumulativeStress(consecutiveDays: Int): String = when {
        consecutiveDays >= 14 ->
            "你最近连续两周都在经历各种琐事。这些事单独都不大——但堆在一起，已经开始影响你的情绪节奏了。你值得给自己放个假。"
        consecutiveDays >= 7 ->
            "这一周你遇到了不少烦心事。你可能会觉得最近怎么什么都不顺——其实只是概率问题。不是你不好。"
        consecutiveDays >= 3 ->
            "连续几天都有小麻烦。你可能会觉得有点烦躁——这是正常的。深呼吸，这一切都会过去。"
        else -> ""
    }

    // ============================================
    // v2.5 代际认知对话 文案
    // ============================================

    /**
     * 代际对话的五段式完整输出
     */
    fun describeGenerationalDialogue(dialogue: GenerationalDialogue): String {
        return "【${dialogue.type.label}】${dialogue.triggerContext}\n\n" +
                "【体感】${dialogue.bodyFeeling}\n\n" +
                "【闪光点】${dialogue.sparkle}\n\n" +
                "【个人工时】${dialogue.personalCost}\n\n" +
                "【时代成本】${dialogue.eraCost}\n\n" +
                "【小镇评述】${dialogue.townCommentary}"
    }

    /**
     * 代际对话玩家选择反馈
     */
    fun describeGenerationalChoiceResult(
        dialogue: GenerationalDialogue,
        choiceIndex: Int
    ): String {
        val choice = dialogue.choices.getOrElse(choiceIndex) { return "你选择了沉默。" }
        return when (choice.mode) {
            GenerationalResponseMode.TIME_COMFORT ->
                "【经验安慰】${choice.responseText}\n\n${choice.cognitionChange}"
            GenerationalResponseMode.LOGICAL_ANALYSIS ->
                "【逻辑拆解】${choice.responseText}\n\n${choice.cognitionChange}"
        }
    }

    /**
     * 代际认知闭环评述
     */
    fun describeGenerationalClosing(isBreakingCycle: Boolean): String = when {
        isBreakingCycle ->
            "你选择了打破代际循环。不是所有父母都有机会这么做——你替你自己，也替当年的长辈，给出了一个更接近真相的答案。"
        else ->
            "你延续了父辈的安慰方式。这不是错——有时候，一句「等以后就好了」，已经是对方当下能给出的全部善意。"
    }

    // ============================================
    // v2.5 信念系统 文案
    // ============================================

    /**
     * 信念值描述
     */
    fun describeBeliefState(state: BeliefState): String {
        val level = when {
            state.beliefValue >= 90 -> "你的信念坚如磐石。经历了这么多，你依然站在最初选择的路上——不是没有动摇过，是每一次都选择了继续。"
            state.beliefValue >= 70 -> "你的信念还在。偶尔会动摇——看到同龄人的安稳生活时，在无数次失败后的深夜——但你第二天还是起来了。"
            state.beliefValue >= 50 -> "你的信念正在被现实打磨。你不再像当初那样热血沸腾——你开始看清了理想和现实的差距。但你还在这里。"
            state.beliefValue >= 30 -> "你的信念在摇晃。你越来越频繁地问自己——这一切值得吗？你还没有放弃，但你离放弃已经不远了。"
            state.beliefValue >= 10 -> "你的信念所剩无几。你觉得当初的自己太天真了——理想很美，但生存更迫切。"
            else -> "信念已经破碎。但这不全是你的错——有些路不是不够努力就能走通的。你尽力了。"
        }
        if (state.yearsHeld > 0) {
            return "$level\n\n你已经在这条路上走了${state.yearsHeld}年。"
        }
        return level
    }

    /**
     * 信念里程碑
     */
    fun describeBeliefMilestone(milestone: BeliefMilestone, yearsHeld: Int): String {
        val yearsText = if (yearsHeld > 0) "\n\n【坚守年限】${yearsHeld}年" else ""
        return "【${milestone.title}】${milestone.description}\n\n【小镇评述】${milestone.townCommentary}$yearsText"
    }

    /**
     * 信念消解事件触发
     */
    fun describeBeliefErosion(event: BeliefErosionEvent): String {
        return "【${event.cause.label}】${event.description}\n\n信念 −${event.beliefDecay}"
    }

    /**
     * 信念崩溃后的收尾评述
     */
    fun describeBeliefBroken(): String {
        return "你把曾经的计划书收进了抽屉最深处。不是懦弱——是你在无数的失败后，终于对自己说了实话。你没有辜负理想——是现实辜负了你。你值得被自己原谅。"
    }

    /**
     * 信念坚守多年但从未成功的评述
     */
    fun describeBeliefLifelongFailure(yearsHeld: Int): String {
        return "你在这条路上走了$yearsHeld 年，从未取得世俗意义上的大成功。但你从未后悔——你做过的事、熬过的夜、失败后依然选择继续的那些瞬间，已经是独属于你的人生意义。成功是一种结果，坚守是一种选择——你选择了后者，并且从未放手。"
    }

    // ============================================
    // v2.6 阶层行为倾向 文案
    // ============================================

    /**
     * 描述阶层初始参数
     */
    fun describeClassInitialState(background: ClassBackground, survivalAnxiety: Int): String {
        val config = when (background) {
            ClassBackground.UNDERPRIVILEGED ->
                "你来自底层——${if (survivalAnxiety >= 70) "长期面临资源紧缺，内心深处的焦虑从未真正消退。你习惯了对资源保持警觉——退让意味着失去。" else "虽然出身困苦，但多年经历让焦虑有所纾解。你开始学会在不安全中保持从容。"}"
            ClassBackground.AFFLUENT ->
                "你来自优渥家庭——${if (survivalAnxiety <= 20) "物质安全感是你待人温和的底气。不需要争抢基础资源，让从容成为习惯。" else "尽管物质充裕，但你内心深处对失去安全感充满担忧——优渥背后也有不为人知的焦虑。"}"
        }
        return "【${background.label}】${config}\n\n生存焦虑：${survivalAnxiety}/100"
    }

    /**
     * 描述权力行为模式
     */
    fun describePowerBehavior(mode: PowerBehaviorMode, background: ClassBackground): String {
        return when (mode) {
            PowerBehaviorMode.HARSH_CONTROL ->
                "【强硬管控】你掌权后的第一反应是收紧一切控制。不是天性刻薄——是你内心深处的恐惧在驱动：一旦放手，你就可能跌回那个你拼命逃离的地方。你的严苛是铠甲，不是本性。"
            PowerBehaviorMode.MODERATE ->
                "【中庸处事】你没有选择极端。按规则办事，不越界也不冒进——这不是冷漠，是你在这个位置上找到的最稳妥的生存方式。"
            PowerBehaviorMode.BENEVOLENT ->
                "【体恤民众】你深知底层谋生的难处，因为你走过那条路。你选择了用权力保护那些和你当年一样的人。${background.label}出身而选择善待他人——你不是多数，但你的存在证明了：出身不决定品格。"
        }
    }

    /**
     * 描述富裕阶层行为模式
     */
    fun describeEliteBehavior(mode: EliteBehaviorMode): String {
        return when (mode) {
            EliteBehaviorMode.MILD_MAINTAIN ->
                "【温和维护】日常待人礼貌宽容——这份温和来自物质安全感。但触及圈层利益时，你会优先守住自己的位置。手段体面、规则合规——无需粗暴，就能维护自己的利益。"
            EliteBehaviorMode.REFORMATIVE ->
                "【革新惠民】你跳出了自己出身的视角——这并不容易。你看到了自己舒适的背后，是另一些人的艰辛。你选择了打破圈层壁垒，这不是多数人的选择，但它是你的选择。"
            EliteBehaviorMode.ARROGANT ->
                "【骄横傲慢】你从小习惯了用资源和人脉解决问题，也习惯了俯视那些没有这些条件的人。你的优越感写在每一句话里——这不是你的错，但这是你的选择。"
        }
    }

    /**
     * 后天转折事件描述
     */
    fun describePostnatalShift(event: PostnatalShiftEvent): String {
        val direction = if (event.isPositive) "正向" else ""
        return "【后天转折$direction】${event.description}\n\n【小镇评述】${event.townCommentary}"
    }

    /**
     * 行为模式转变描述
     */
    fun describeBehaviorModeShift(
        oldMode: String,
        newMode: String,
        cause: String
    ): String {
        return "你的处事方式在改变——从「${oldMode}」向「${newMode}」转变。\n\n原因：${cause}\n\n这不是天性的改变，是经历改变了你的选择。"
    }

    /**
     * 阶层行为整体评述
     */
    fun describeClassBehaviorSummary(state: ClassBehaviorState): String {
        val anxietyPart = when {
            state.survivalAnxiety >= 80 ->
                "你内心深处的不安全感根深蒂固——每一刻都在害怕失去现有的位置。这份焦虑不是你的错，是你过往所有匮乏经历的烙印。"
            state.survivalAnxiety >= 50 ->
                "你的焦虑还在，但不再像当初那样时刻紧绷。安全感正在一点一点地回来——过程很慢，但你在变。"
            else ->
                "你几乎不再为生存焦虑所困。这不是天生的——是时间和安稳慢慢化解了曾经的紧绷。"
        }

        val powerPart = if (state.isInPower) {
            when (state.background) {
                ClassBackground.UNDERPRIVILEGED -> {
                    state.powerMode?.let {
                        "在权力的位置上，你选择了「${it.label}」的处事方式。${if (it == PowerBehaviorMode.BENEVOLENT) "你没有重复那个你最害怕的循环——出身贫苦不等于对待他人就要严苛。" else "这份选择背后有你走过的路——恐惧、挣扎，和那份从不曾消退的生存警觉。"}"
                    } ?: ""
                }
                ClassBackground.AFFLUENT -> {
                    state.eliteMode?.let {
                        "你选择了「${it.label}」。${if (it == EliteBehaviorMode.REFORMATIVE) "你没有困在自己出身的舒适区——跳出圈层视角，需要勇气。" else "温和来自安全感，但它的边界在哪里——只有你自己知道。"}"
                    } ?: ""
                }
            }
        } else ""

        return "${anxietyPart}\n\n${powerPart}\n\n——贫富决定的是初始环境、焦虑基数、处事倾向，不决定你的本性善恶。选择权一直在你手上。"
    }

    // ============================================
    // v2.8 心理内核 文案
    // ============================================

    /**
     * 心理状态完整描述
     */
    fun describePsychologicalCore(psych: PsychologicalCore): String {
        val levelDesc = when (psych.healthLevel) {
            MentalHealthLevel.FLOURISHING -> "你的内心有一片属于自己的安宁。不是没有烦恼——是你学会了不被烦恼裹挟。"
            MentalHealthLevel.STABLE -> "日子不好不坏。偶尔低落，但你能感觉到自己在慢慢回来。"
            MentalHealthLevel.STRUGGLING -> "你在努力撑着。每一件小事都在消耗你——但你还没有倒下。"
            MentalHealthLevel.WORN_DOWN -> "你累了。不是身体的累——是心里的那根弦已经绷了太久。你值得停下来。"
            MentalHealthLevel.BREAKDOWN -> "你撑不住了。停下来吧——不是认输，是你欠自己的一次喘息。"
        }

        return "【心理内核】综合健康度：${psych.overallHealth}/100 · ${psych.healthLevel.label}\n" +
                "${levelDesc}\n\n" +
                "幸福感：${psych.happiness}/100 · " +
                "孤独感：${psych.loneliness}/100 · " +
                "焦虑感：${psych.anxiety}/100\n" +
                "信念韧性：${psych.beliefResilience}/100 · " +
                "自我认同：${psych.selfIdentity}/100 · " +
                "虚无迷茫：${psych.nihilism}/100 · " +
                "共情能力：${psych.empathy}/100\n\n" +
                "社交意愿：${psych.socialWill}/100 · " +
                "长期规划意愿：${psych.longTermPlanning}/100" +
                if (psych.isDeeplyLonely) "\n\n⚠ 深度孤独——你值得有人陪在身边。" else "" +
                if (psych.hasMeaningCrisis) "\n\n⚠ 意义危机——你在追问一个没有人能替你回答的问题。" else ""
    }

    /**
     * 心理参数单维度描述
     */
    fun describePsychDimension(paramName: String, value: Int): String = when (paramName) {
        "loneliness" -> when {
            value >= 80 -> "你的孤独深不见底。通讯录里有很多名字——但没有一个可以接住你。"
            value >= 60 -> "你开始习惯一个人。不是不想社交——是社交这件事本身让你觉得累。"
            value >= 40 -> "偶尔会在深夜觉得空落落的。但你看看手机，又放下了。"
            else -> "你有人陪——不一定是轰轰烈烈的关系，是有人在。这已经很好了。"
        }
        "anxiety" -> when {
            value >= 80 -> "焦虑像一只无形的手掐着你的喉咙。每一件事看起来都是灾难。"
            value >= 60 -> "你总是在担心——钱不够、工作不稳、未来未知。这份担心不是多余的——但它不应该占据全部的你。"
            value >= 40 -> "偶尔会紧张、会不安——但这没有影响你的日常节奏。"
            else -> "你心里有底——不是什么都不怕，是知道自己能应付。"
        }
        "selfIdentity" -> when {
            value >= 75 -> "你不需要向任何人证明自己。你知道自己是谁——这就够了。"
            value >= 55 -> "你大部分时候对自己还好。偶尔会怀疑——但不会否定全部的自己。"
            value >= 35 -> "你经常觉得自己不够好。和同龄人一比，总觉得自己输了什么。"
            else -> "你几乎不相信自己值得什么好东西。这不是真的——是你被生活磨得太久了。"
        }
        "nihilism" -> when {
            value >= 70 -> "你做任何事都提不起劲——因为你看不出来这一切的意义在哪。"
            value >= 50 -> "你开始怀疑：努力半生到底为了什么？这个问题没有答案——但你还在问。"
            value >= 30 -> "偶尔会想——但这不影响你继续往前走。"
            else -> "你有方向感。不一定很清晰——但你知道自己想去哪里。"
        }
        "empathy" -> when {
            value >= 70 -> "你能在别人身上看见自己——这种看见，让你与众不同。"
            value >= 50 -> "你会为别人的痛苦动容——不是每个人都会的。"
            value >= 30 -> "你开始觉得有些人的苦难和自己无关。不是冷漠——是你的精力只够顾自己了。"
            else -> "你的世界渐渐缩小到只容得下自己。这不是你的错——但如果你能再看一眼外面，也许会发现不一样的东西。"
        }
        "beliefResilience" -> when {
            value >= 75 -> "你的信念根深蒂固——不是盲目的乐观，是经历了无数次动摇后，依然选择相信。"
            value >= 50 -> "你的信念在风雨中摇晃，但没有倒下。"
            value >= 30 -> "你撑了很久——但你的韧性在消磨。你需要补充能量。"
            else -> "你的信念几乎耗尽了。不是你的错——是你一个人扛了太久。"
        }
        "happiness" -> when {
            value >= 80 -> "你很快乐。不是每时每刻都快乐——但你心里有一片晴空。"
            value >= 60 -> "你的生活有一些甜蜜的时刻，虽然不常有——但每一次都值得。"
            value >= 40 -> "快乐好像变成了奢侈品。偶尔会有——但不够多。"
            else -> "快乐对你来说太远了。你的感受是真实的——但请相信，这不是终点。"
        }
        else -> ""
    }

    /**
     * 原子化心理事件描述
     */
    fun describeAtomizedPsychEvent(event: AtomizedPsychEvent): String {
        return "【${event.title}】${event.description}\n\n" +
                "【小镇评述】${event.townCommentary}"
    }

    /**
     * 外部事件对心理影响的简短提示
     */
    fun describePsychImpactHint(sourceName: String, psych: PsychologicalCore): String {
        val changes = mutableListOf<String>()
        if (psych.anxiety > 60) changes.add("焦虑感偏高")
        if (psych.loneliness > 60) changes.add("孤独感偏高")
        if (psych.nihilism > 50) changes.add("对意义的迷茫在累积")
        if (psych.selfIdentity < 35) changes.add("你在低估自己的价值")
        if (psych.beliefResilience < 35) changes.add("你的内心正在被磨损")

        return if (changes.isEmpty()) {
            ""
        } else {
            "【${sourceName}对你的影响】${changes.joinToString(" · ")}"
        }
    }

    /**
     * 晚年精神叙事
     */
    fun describeLateLifeNarrative(narrative: LateLifeNarrative): String = when (narrative) {
        LateLifeNarrative.PEACE_WITH_ORDINARY ->
            "【与平庸和解】" +
            "你老了。你没做过惊天动地的事，但你好好地活过了每一天。那些日复一日的通勤、做饭、打扫、睡觉——看起来微不足道——但它们构成了你完整的一生。\n\n" +
            "你学会了接受自己的平凡。不是因为认命，是因为你发现平凡的日常，本身就是最难熬的考验。你通过了。\n\n" +
            "你很普通。这个评价，是你给自己的一生——最高的嘉奖。"

        LateLifeNarrative.UNREST_HANDWRITTEN ->
            "【留下思想手记】" +
            "你老了，但你心里还有一团火。你没能在有生之年改变世界——但你可以把这一生的思考、挣扎、领悟写下来。\n\n" +
            "不是为了证明什么——是为了告诉后来的人：有人走过这条路。\n\n" +
            "你的文字会沉睡在档案馆里。也许很多年后，会被另一个人翻开。那个时候——你一生的不甘，就有了归宿。"

        LateLifeNarrative.SPIRITUAL_BEYOND ->
            "【精神超越】" +
            "你老了。你不再纠结于年轻时候的那些执念——成功、认可、证明自己。\n\n" +
            "你发现真正的自由不是拥有更多——是不再需要向任何人证明任何事。你与自己达成了和解。\n\n" +
            "不是放下了——是超越了。你活成了一个不再需要解释的人。"
    }

    /**
     * 晚年前回首一生的心理总结
     */
    fun describeLifeReview(psych: PsychologicalCore): String {
        val foundation = "你回顾这一生——有得有失，有笑有泪。"
        val core = when (psych.healthLevel) {
            MentalHealthLevel.FLOURISHING ->
                "你的心很稳。不是一路顺风顺水——是你学会了在风雨中保持平衡。"
            MentalHealthLevel.STABLE ->
                "不算好也不算坏。你过了一个普通人的一生——这件事本身就不普通。"
            else ->
                "你经历过很多。你的心被磨损过、被击碎过——但它还在跳。这已经是一种胜利。"
        }
        val closing = "\n\n评判你一生的，不是银行余额、不是职位头衔、不是任何可以通过比较来定义的东西。是你自己在每一个深夜，对自己说的那句话。你说了什么——只有你知道。"
        return "$foundation $core$closing"
    }

    // ============================================
    // v2.7 革新者隐藏角色 文案
    // ============================================

    /**
     * 革新者候选资质检测结果
     */
    fun describeReformerCandidacyCheck(state: ReformerUnlockState): String {
        val unmet = mutableListOf<String>()
        if (!state.cognitiveMet) unmet.add("认知门槛：尚未跳出自身圈层局限")
        if (!state.beliefDurable) unmet.add("信念耐久：尚未经历足够的时间考验")
        if (!state.eraWindowOpen) unmet.add("时代窗口：外部条件尚未成熟")

        return if (unmet.isEmpty()) {
            "三个前置条件全部达成。你用了大半生的时间，走完了几乎没有人走过的路。时代在召唤——你准备好了吗？"
        } else {
            "革新者之路尚未开启。\n\n尚未达成的条件：\n${unmet.joinToString("\n") { "· ${it}" }}\n\n这些不是门槛——是你还需要的成长。"
        }
    }

    /**
     * 革新者支线解锁
     */
    fun describeReformerUnlock(form: ReformerForm): String = when (form) {
        ReformerForm.EXPLICIT ->
            "【显性落地革新者】你用了数十年的时间，走完了认知觉醒、信念淬炼、时代等待这三重关卡。\n\n现在，时代窗口已经打开。你站在了那个只有极少数人才能站到的位置——你可以着手推行真正的变革了。\n\n这不是命运的馈赠——是你用大半生的坚持换来的资格。"
        ReformerForm.IMPLICIT ->
            "【隐性思想革新者】时代尚未准备好承接你的理念。但你记录下来了——每一份调研、每一次思考、每一个深夜写下的构想。\n\n这些东西当下改变不了规则——但它们会沉睡在档案馆里，等待下一代人。你不是失败者——你是跨代的火种。"
    }

    /**
     * 革新者支线关闭
     */
    fun describeReformerBranchClosed(reason: String): String {
        return "【革新者支线永久关闭】${reason}\n\n这不是失败——是你选择了另一条路。大多数人都没有走到这里，你已经比绝大多数人走得更远了。"
    }

    /**
     * 改革行动五段式输出
     */
    fun describeReformAction(action: ReformAction): String {
        val typeTag = if (action.isGradual) "渐进式改革" else "激进式改革"
        return "【${action.title}】${typeTag}\n${action.description}\n\n" +
                "【体感】${action.bodyFeeling}\n\n" +
                "【闪光点】${action.sparkle}\n\n" +
                "【个人工时】${action.personalCost}\n\n" +
                "【时代成本】${action.eraCost}\n\n" +
                "【小镇评述】${action.townCommentary}"
    }

    /**
     * 改革执行结果
     */
    fun describeReformResult(result: ReformResult): String {
        val outcome = if (result.success) "改革成功" else "改革受阻"
        return "【${result.action.title}】${outcome}\n\n" +
                "${result.resultDescription}\n\n" +
                "${result.aftermathDescription}"
    }

    /**
     * 改革成功的时代公告
     */
    fun describeReformEraAnnouncement(action: ReformAction): String {
        return "【时代公告】${action.title}已正式推行。\n\n" +
                "${action.eraCost}\n\n" +
                "这项改革的发起者，是极少数用一生推动时代前进的人之一。一代之内，这样的人不过寥寥无几。"
    }

    /**
     * 改革失败但记录留存
     */
    fun describeReformFailedButArchived(action: ReformAction): String {
        return "【改革失败·档案留存】${action.title}未能成功落地。\n\n" +
                "但你为之付出的每一分努力——调研、谈判、深夜修改的方案——都已记录在人生档案馆中。\n\n" +
                "失败的不是你，是时代还没准备好。这些档案会在未来某一天，被另一个人翻开。"
    }

    /**
     * 隐性思想革新者的跨代伏笔
     */
    fun describeLegacySeed(): String {
        return "【跨代思想伏笔】你在有生之年未能看到改革落地。\n\n" +
                "但你的思想没有被遗忘。你留下的档案——每一页手稿、每一条调研记录、每一个深夜思索的瞬间——都沉睡在小镇的时代档案馆里。\n\n" +
                "数十年后，会有另一个人翻开这些纸张。他会在你的文字里找到火种——然后继续你没走完的路。\n\n" +
                "你不是失败者。你是播种者。"
    }

    /**
     * 革新者中期节点：时代危机触发抉择
     */
    fun describeReformerMidlifeCrisis(): String {
        return "【时代危机】你目睹了行业的垄断、物价的暴涨、大规模失业。你看到了系统的问题——但你也知道，推动变革的代价是什么。\n\n" +
                "现在你面临一个抉择：\n" +
                "· 站出来推动改革——这意味着一场漫长而危险的博弈\n" +
                "· 选择稳妥自保——你依然是个好人，只是时代还不到改变的时候"
    }

    /**
     * 革新者人生总结评述
     */
    fun describeReformerLifeSummary(state: ReformerUnlockState): String {
        return when {
            state.isReformer && state.form == ReformerForm.EXPLICIT -> {
                val actions = state.completedActions.size
                val failures = state.failedAttempts
                "你走完了革新者的一生。你推行了${actions}项改革，失败了${failures}次。\n\n" +
                        "你不是完美的——没有人是。但你用一生的时间证明了一件事：一个人，在正确的时代，做出正确的选择——是可以推动整个时代向前走的。\n\n" +
                        "你是极少数。你是那一代人的光。"
            }
            state.isReformer && state.form == ReformerForm.IMPLICIT -> {
                "你选择了播种，而不是收割。时代没有给你开花结果的机会，但你埋下了种子。\n\n" +
                        "你不需要看到花开——你知道它们会开的。\n\n" +
                        "你不是失败者。你是伏笔。"
            }
            state.branchClosed -> {
                "你没有成为革新者。你选择了另一条路——大多数人都选了这条路。\n\n" +
                        "这不是错——平凡的坚守本身就有价值。时代需要革新者，也需要千千万万踏实生活的普通人。你属于后者。"
            }
            else -> ""
        }
    }

    // ============================================
    // v2.9 独处逃避行为 文案
    // ============================================

    fun describeWithdrawalTrigger(event: WithdrawalTriggerEvent): String {
        return "【${event.targetMode.label}】${event.description}\n\n" +
                "【小镇评述】${event.townCommentary}"
    }

    fun describeWithdrawalMode(mode: WithdrawalMode): String = when (mode) {
        WithdrawalMode.SHORT_REST ->
            "你选择了短暂独处。这不叫逃避——这叫给自己一个喘息的空间。休息好了，再回去面对。"
        WithdrawalMode.PHASED_AVOIDANCE ->
            "你开始躲着一些人和事。你知道这不是长久之计——但眼下，你只是想先歇一歇。"
        WithdrawalMode.DEEP_WITHDRAWAL ->
            "你把世界关在了门外。这不是一朝一夕的冲动——是无数次的撞击之后，你终于选择了不站起来。"
        WithdrawalMode.ACTIVE_RESISTANCE ->
            "你选择了面对。压力还在——但你不再是一个人扛着。你拿起电话、推开门、迈出了那一步。"
    }

    fun describeWithdrawalModeShift(oldMode: WithdrawalMode, newMode: WithdrawalMode, reason: String): String {
        val shift = when {
            oldMode == WithdrawalMode.DEEP_WITHDRAWAL && newMode != WithdrawalMode.DEEP_WITHDRAWAL ->
                "你从漫长的独处中走出来了。这一步比所有人想象的都要难——但你做到了。"
            oldMode != WithdrawalMode.DEEP_WITHDRAWAL && newMode == WithdrawalMode.DEEP_WITHDRAWAL ->
                "你退到了自己的角落。这不是第一次——但这一次，你不知道什么时候能再走出去。"
            oldMode != WithdrawalMode.ACTIVE_RESISTANCE && newMode == WithdrawalMode.ACTIVE_RESISTANCE ->
                "你选择了面对。压力还在——但你拿回了主动权。"
            else -> "你的状态在变化。每一次的进与退，都是你在和压力谈判。"
        }
        return "${shift}\n\n原因：${reason}"
    }

    fun describeWithdrawalDailyConsequence(consequence: WithdrawalConsequence, mode: WithdrawalMode, days: Int): String {
        val daysText = if (days > 0) "（已持续${days}天）" else ""
        val psychChanges = mutableListOf<String>()
        if (consequence.lonelinessDelta != 0) psychChanges.add("孤独${if (consequence.lonelinessDelta > 0) "+${consequence.lonelinessDelta}" else "${consequence.lonelinessDelta}"}")
        if (consequence.anxietyDelta != 0) psychChanges.add("焦虑${if (consequence.anxietyDelta > 0) "+${consequence.anxietyDelta}" else "${consequence.anxietyDelta}"}")
        if (consequence.identityDelta != 0) psychChanges.add("自我认同${if (consequence.identityDelta > 0) "+${consequence.identityDelta}" else "${consequence.identityDelta}"}")
        return "【${mode.label}${daysText}】${consequence.careerImpact}" +
                if (psychChanges.isNotEmpty()) "\n心理变化：${psychChanges.joinToString(" · ")}" else "" +
                if (consequence.triggersViciousCycle) "\n\n恶性循环风险：逃避越久，越害怕走出去。" else ""
    }

    fun describeViciousCycle(): String {
        return "【恶性循环】你越躲——越不敢面对。越不敢面对——越想躲。\n\n" +
                "这不是你的错。是大脑在保护你——但它保护过度了。你需要一个外力来打破这个循环。"
    }

    fun describeSolitudePeace(): String {
        return "【独处自洽】你把独处变成了生活。\n\n" +
                "你不再觉得需要向任何人解释你为什么不出门、为什么不社交。你找到了属于自己的节奏——不需要别人懂，只要你自己舒服。\n\n" +
                "这不是逃避——这是选择。你选择了最适合自己的生活方式。"
    }

    // ============================================
    // v2.10 爱情/婚恋/亲密关系 文案
    // ============================================

    fun describeLoveEvent(event: LoveEvent): String {
        return "【${event.scenario.label}】${event.description}\n\n" +
                "【小镇评述】${event.townCommentary}"
    }

    fun describeLoveStatus(status: LoveStatus): String = when (status) {
        LoveStatus.SINGLE ->
            "你是一个人。这不是过渡期——这也是一种完整的生活方式。你来去自由，你的夜晚和周末都属于你自己。"
        LoveStatus.IN_LOVE ->
            "你有了一个人。你的生活里多了一份温暖——也多了早上多赖床五分钟的习惯。有个人在等你回家——这种感觉，很踏实。"
        LoveStatus.MARRIED ->
            "你们搭伙过日子。有甜蜜，也有摩擦——有时候两种情绪同时发生。婚姻不是童话结局——是两个人选择了在同一条船上漂。"
        LoveStatus.DIVORCED ->
            "一段关系走到了尽头。这不是失败——是你在某个路口选择了另一条路。你会怀念那些美好——但你也会感谢自己做了这个决定。"
        LoveStatus.WIDOWED ->
            "最重的一种失去。那个人不在了——但你身上还带着他/她的温度。你们一起走过的路不会消失——它就是你的一部分。"
    }

    fun describeLoveChoiceResult(choice: LovePlayerChoice): String {
        return "【${choice.label}】\n\n${choice.resultText}\n\n" +
                "心理变化：幸福${if (choice.happinessDelta > 0) "+${choice.happinessDelta}" else "${choice.happinessDelta}"} " +
                "· 孤独${if (choice.lonelinessDelta > 0) "+${choice.lonelinessDelta}" else "${choice.lonelinessDelta}"} " +
                "· 焦虑${if (choice.anxietyDelta > 0) "+${choice.anxietyDelta}" else "${choice.anxietyDelta}"}" +
                if (choice.careerImpact.isNotEmpty()) "\n职业影响：${choice.careerImpact}" else ""
    }

    fun describeLoveStatusTransition(oldStatus: LoveStatus, newStatus: LoveStatus): String = when {
        oldStatus == LoveStatus.SINGLE && newStatus == LoveStatus.IN_LOVE ->
            "你恋爱了。你的世界多了一种颜色——那种颜色叫'有人等你回家'。"
        oldStatus == LoveStatus.IN_LOVE && newStatus == LoveStatus.MARRIED ->
            "你们结婚了。这不是终点——这是你们选择用同一个地址，一起拆生活的盲盒。"
        oldStatus == LoveStatus.IN_LOVE && newStatus == LoveStatus.SINGLE ->
            "你们分开了。心里被挖走了一块——但你很快就会知道，那块位置不是用来填的，是用来记住的。"
        oldStatus == LoveStatus.MARRIED && newStatus == LoveStatus.DIVORCED ->
            "你们离婚了。一段婚姻的结束——但也是另一段人生的开始。你不用急着证明什么——先学会一个人好好吃饭。"
        oldStatus == LoveStatus.MARRIED && newStatus == LoveStatus.WIDOWED ->
            "最重的那种告别。那个人不在了——但从今天开始，你要替他/她好好地活下去。你身上带着两个人的记忆。"
        else -> "你的情感状态发生了变化。每一次变化都是你人生的一部分——不需要用对错衡量。"
    }

    fun describeLoveDailyEffect(loveDays: Int, status: LoveStatus): String {
        return when {
            loveDays == 1 && status == LoveStatus.IN_LOVE ->
                "恋爱第一天。世界看起来不一样了——街道是彩色的，时间是慢的，空气是甜的。"
            loveDays == 30 && status == LoveStatus.IN_LOVE ->
                "恋爱一个月了。新鲜感褪去了一点点——但你觉得刚刚好。不是那种心怦怦跳的紧张——是一种踏实的温暖。"
            loveDays == 365 && status == LoveStatus.IN_LOVE ->
                "恋爱满一年了。你们已经熟悉了对方的每一个小习惯——包括那些一开始让你抓狂的。但现在你开始觉得——那些小毛病也是她的一部分。"
            status == LoveStatus.MARRIED && loveDays % 365 == 0 ->
                "婚姻又过了一年。你们的争吵还是那些话题——但吵架的时间越来越短，和好的速度越来越快。"
            else -> ""
        }
    }

    fun describeMarriageConflict(conflictDays: Int): String = when {
        conflictDays == 1 ->
            "今天又因为钱的事吵了一架。你们都知道这不是钱的问题——是你们太累了，以至于只能对着最表面的东西发脾气。"
        conflictDays == 7 ->
            "你们已经冷战快一周了。以前你们吵完架会和好——现在你们吵完架会各自回各自的房间。你开始觉得——你们之间隔的不只是一个客厅。"
        conflictDays == 21 ->
            "连续三周的不愉快。你们好像已经习惯了这种状态——不吵也不和好，就是各自过着。你开始想——这样的婚姻还值得继续吗？"
        conflictDays == 30 ->
            "一个月了。你们还住在同一个屋檐下——但你们之间的对话已经缩减到'钥匙在桌上'和'冰箱里还有菜'。你知道——再这样下去，你们会变成室友。"
        else -> ""
    }

    fun describeLongDistancePain(days: Int): String = when {
        days == 7 ->
            "异地第一周。你想她的次数比你以为的多。一个人吃饭的时候——你发现对面那个空椅子特别显眼。"
        days == 30 ->
            "异地一个月了。你们还每天都通电话——但你开始发现，电话里能说的事情越来越少了。不是因为感情淡了——是因为你们活在不同的日常里。"
        days == 90 ->
            "异地三个月。你开始习惯一个人生活了——这个发现让你有点害怕。不是因为你不爱她了——是因为没有她的日子，你也在好好地过。"
        else -> ""
    }

    fun describeHeartbreakRecovery(phase: HeartbreakPhase): String = when (phase) {
        HeartbreakPhase.SHOCK ->
            "刚分手的那几天——你什么感觉都没有。不是不难受——是你的身体还没来得及处理这么大的情绪。接下来几天会很不容易。先吃饭——哪怕不想吃。"
        HeartbreakPhase.GRIEVING ->
            "你开始真正感受到那种疼了。脑子里全是她——开心的时候想她，难受的时候也想她。没关系——哭就哭。眼泪是这场雨唯一的出口。"
        HeartbreakPhase.ADJUSTING ->
            "你开始能正常生活了。吃饭、上班、睡觉——不像之前那么难了。但偶尔一首歌、一个路口——还是会让你停一下。"
        HeartbreakPhase.RECOVERED ->
            "时间过去了。你不再一想起她就心疼——你想起来的时候，嘴角还会笑。那些好的坏的都被时间打磨成了温和的回忆。你恢复了——但你不再是从前那个人了。比从前更好。"
        HeartbreakPhase.TRANSFORMED ->
            "你从废墟里站起来了——比跌倒之前站得更高。那一次分手像一道分水岭——之前的你在意太多不重要的事，之后的你只在意自己。这次蜕变——是失去换来的。"
        HeartbreakPhase.NOT_APPLICABLE -> ""
    }

    fun describeWidowhoodPeace(): String {
        return "你已经学会了一个人生活。不是因为你不怀念——是因为你把他/她活进了你的每一天。\n\n" +
                "早上喝的茶是他/她喜欢的牌子，阳台上种的花是他/她留下的一盆。你不觉得孤独——你觉得他/她还在。只是换了一种方式。"
    }

    // ============================================
    // v2.11 外貌颜值先天参数 文案
    // ============================================

    fun describeAppearanceLevel(level: AppearanceLevel): String = when (level) {
        AppearanceLevel.VERY_POOR ->
            "你的外貌条件偏差。这让你在人群中不那么显眼——有时候甚至被忽略。但那些认真看你的人，看到的不是你的脸，是你。"
        AppearanceLevel.NORMAL ->
            "你的长相平平无奇——不引人注目，也不让人排斥。外貌不会成为你的优势——也不会成为你的障碍。你的路要靠你自己走出来。"
        AppearanceLevel.DECENT ->
            "你长得端正顺眼。偶尔会多收获一些好感——但这不足以改变你的命运。它只是一道细微的顺风——能让你在出发的时候轻松一点。"
        AppearanceLevel.OUTSTANDING ->
            "你的外貌明显优于常人。社交场合里你会比别人更容易收获关注和善意——但你也要做好准备：不是每一道目光都是友善的。"
        AppearanceLevel.STRIKING ->
            "你拥有让人过目难忘的外表。这给你带来了许多别人羡慕不来的红利——但也带来了许多别人看不见的代价。更多的关注，更多的目光，更多的'你以为你靠的是脸'。"
    }

    fun describeAppearanceInit(level: AppearanceLevel, background: ClassBackground): String {
        val bgText = when (background) {
            ClassBackground.UNDERPRIVILEGED -> "你出生在一个普通的底层家庭。"
            ClassBackground.AFFLUENT -> "你出生在一个物质优渥的家庭。"
        }
        return "${bgText}\n\n${describeAppearanceLevel(level)}"
    }

    fun describeAppearancePsychEvent(event: AppearancePsychEvent): String {
        return "【${event.title}】${event.description}\n\n" +
                "【小镇评述】${event.townCommentary}"
    }

    fun describeAppearanceSocialImpact(level: AppearanceLevel, isFirstContact: Boolean): String {
        return if (isFirstContact) {
            when (level) {
                AppearanceLevel.VERY_POOR ->
                    "初次见面时，对方的目光在你的脸上短暂地停了一下——然后移开了。你习惯了这种反应。但你心里知道——第一眼，从来不是最后一眼。"
                AppearanceLevel.STRIKING ->
                    "你走进房间的时候，几个人的目光不约而同地落在了你身上。你习惯了这种关注——但你也知道，关注不等于善意。"
                else -> ""
            }
        } else ""
    }

    fun describeAppearanceAgeDecay(level: AppearanceLevel, age: Int): String {
        if (level.tier < 4) return ""
        return when {
            age == 35 ->
                "你走到镜子前——发现自己不再像二十岁时那样引人注目了。那些因为你的脸而靠近你的人——有一些已经走远了。留下来的——才是你的。"
            age == 45 ->
                "年轻时候那些让人驻足的回头率，现在已经变成了路人平静的一瞥。你不是失落——你只是发现：当外貌不再替你打头阵的时候，你得用自己的东西走路。"
            else -> ""
        }
    }

    fun describeAppearanceHarassment(count: Int): String = when (count) {
        1 -> "今天又发生了。那种让你不舒服的打量和多余的关注。你告诉自己不要在意——但你知道，这种事不该是常态。"
        3 -> "已经不是第一次了。你开始有意识地避开某些场合、某些人——不是因为怕，是因为累了。"
        else -> ""
    }

    fun describeSubstanceOverLooks(): String {
        return "【选择深耕】你不再把时间花在镜子前面了。\n\n" +
                "你开始看书、学技能、做事——你说服世界的方式变了。以前你希望别人先看到你的脸，现在你希望别人先看到你的成果。这才是你最坚固的东西。"
    }

    // ============================================
    // v2.12 服饰穿戴 + 童年创伤 + 自我对话 文案
    // ============================================

    // ---- 穿戴健康 ----

    fun describeClothingHealthEvent(event: ClothingHealthEvent): String {
        return "【${event.title}】${event.description}\n\n" +
                "【小镇评述】${event.townCommentary}"
    }

    fun describeFootCondition(condition: FootCondition): String = when (condition) {
        FootCondition.HEALTHY -> ""
        FootCondition.MILD_FUNGUS -> "你的脚有点痒——还不算严重，但你知道：再拖下去会更难受。"
        FootCondition.MODERATE_FUNGUS -> "脚气已经很明显了。走路的时候——每走一步都在提醒你：该处理了。"
        FootCondition.SEVERE_FUNGUS -> "脚已经受不住了。破皮、出水、走路都疼——你的脚在替你承受你不该承受的东西。"
        FootCondition.CHILBLAIN -> "脚上的冻疮又红又肿——一热就痒，一冷就疼。你的脚替你扛了整个冬天。"
    }

    fun describeClothingSocialEvent(event: ClothingSocialEvent): String {
        return "【${event.title}】${event.description}\n\n" +
                "【小镇评述】${event.townCommentary}"
    }

    fun describeLaundryReminder(hoursSince: Int): String = when {
        hoursSince > 72 -> "你的衣服已经好几天没洗了——衣领有点黄了。洗衣服花不了多少时间——但脏衣服会让你一整天都觉得不清爽。"
        hoursSince > 48 -> "衣服穿了两天了——还没到非洗不可的地步，但你闻了一下袖口——嗯，差不多了。"
        else -> ""
    }

    // ---- 童年创伤 ----

    fun describeTraumaEntry(trauma: TraumaEntry): String {
        val healedMark = if (trauma.isHealed) "【已治愈】" else ""
        return "${healedMark}${trauma.type.label}（${trauma.severity}分）\n${trauma.narrative}"
    }

    fun describeChildhoodSummary(traumas: List<TraumaEntry>): String {
        if (traumas.isEmpty()) return "你的童年没有留下明显的创伤。你很幸运——不是每个人都能这样。"
        val unhealed = traumas.filter { !it.isHealed }
        val healed = traumas.filter { it.isHealed }
        val sb = StringBuilder()
        sb.append("你的童年留下了${traumas.size}道印记：\n\n")
        if (unhealed.isNotEmpty()) {
            sb.append("尚未愈合的：\n")
            unhealed.forEach { sb.append("· ${describeTraumaEntry(it)}\n") }
        }
        if (healed.isNotEmpty()) {
            sb.append("\n已经和解的：\n")
            healed.forEach { sb.append("· ${describeTraumaEntry(it)}\n") }
        }
        return sb.toString().trimEnd()
    }

    fun describePsychDrain(drain: PsychDrain): String {
        return if (drain.description.isNotEmpty()) {
            "${drain.description}"
        } else ""
    }

    // ---- 中年自我对话 ----

    fun describePastSelfDialogueIntro(event: PastSelfDialogueEvent): String {
        val traumasText = if (event.traumasSummary.isNotEmpty()) {
            "\n\n童年留下的印记：${event.traumasSummary}"
        } else ""
        return "【${event.currentAge}岁·回望二十岁】\n\n" +
                "深夜。你一个人坐在窗前。窗外一片黑——只有你自己的倒影映在玻璃上。\n\n" +
                "你看到了十年前的自己——那一年你${event.pastSelfAge}岁。他/她站在你面前——穿着旧衣服，脸上是那个年纪特有的迷茫。你知道他/她正在经历什么——因为你就是从那里一步一步走过来的。\n\n" +
                "你可以跟十年前的自己说几句话。你想说什么？${traumasText}"
    }

    fun describeDialogueChoice(choice: PastSelfDialogueChoice): String {
        return "【${choice.label}】\n\n${choice.description}"
    }

    fun describeDialogueResult(choice: PastSelfDialogueChoice): String {
        return "【你选择了：${choice.label}】\n\n${choice.resultNarrative}"
    }

    fun describeHealedTrauma(trauma: TraumaEntry): String {
        return "【和解】${trauma.type.label}的创伤已经平复了。\n\n" +
                "不是消失了——是你不再被它抓住了。那些记忆还在——但它们不再在深夜来找你。你学会了带着它们走路——而不是被它们拖着走。"
    }

    fun describeTraumaIgnored(): String {
        return "你选择了不去面对。这不是错——有时候我们还没准备好。\n\n" +
                "童年的阴影不会因为被忽视就消失——但它也不会强迫你回头。它会一直在那里——像地窖里堆放的老箱子。你可以一辈子不打开——但你知道它们还在。"
    }

    // ---- 穿戴社交 ----

    fun describeClothingFirstImpression(quality: ClothingQuality, cleanliness: ClothingCleanliness): String {
        return when {
            quality.tier >= 4 && cleanliness.tier >= 4 ->
                "你今天穿得很精致。走进房门的时候——几道目光自然地落在你身上。不是因为别的——只是因为整洁好看。"
            quality.tier <= 1 || cleanliness.tier <= 1 ->
                "进来的时候没有人多看一眼。不是因为你不值得被看——是这个世界总是先看见衣服再看见人。但你知道吗——衣服会换，你不会。"
            else -> ""
        }
    }

    fun describeClothingCompensation(traumas: List<TraumaEntry>): String {
        val clothingTraumas = traumas.filter { it.type.category == TraumaCategory.MATERIAL && !it.isHealed }
        if (clothingTraumas.isEmpty()) return ""
        return "你小时候缺过很多东西——包括合脚的好鞋子、保暖的衣服。现在你有能力了——你开始给自己买好的。这不是虚荣——这是你在替那个小孩弥补他没得到的东西。"
    }

    // ============================================
    // v2.13 材质-成分 + 性别系统 文案
    // ============================================

    // ---- 面料成分 ----

    fun describeFabricChange(slot: com.example.townapp.data.WearSlot, fabric: FabricType): String {
        return "你把${slot.label}换成了${fabric.label}材质的。${fabric.description}"
    }

    fun describeFabricColdRisk(fabric: FabricType, temperature: Int): String {
        val reason = when (fabric) {
            FabricType.COTTON -> "纯棉虽然舒服——但在这个温度下它锁不住太多热量。"
            FabricType.LINEN -> "亚麻透气好——但保暖能力是四种面料里最弱的。"
            else -> "你这件衣服的面料——在这个温度下不太够用。"
        }
        return "一阵冷风灌进领口——你缩了缩脖子。${reason}今天回去加件衣服——别硬扛。"
    }

    // ---- 洗涤剂 ----

    fun describeDetergentSkinIssue(detergent: DetergentType): String {
        return when (detergent) {
            DetergentType.SOAP_BASED -> "洗完衣服后你的手指有点干——指腹的皮微微翘起来了。皂基洗得干净——但它也把你的手洗薄了。下次买洗衣液的时候——可以看一眼成分表。"
            DetergentType.MILD_DETERGENT -> ""
        }
    }

    fun describeDetergentChange(type: DetergentType): String {
        return when (type) {
            DetergentType.SOAP_BASED -> "你换回了皂基洗衣粉——便宜、去污力强。手可能会干一点——但你心里有数。"
            DetergentType.MILD_DETERGENT -> "你换成了温和洗衣液——多花了一点钱。但洗完衣服你的手没起皮——这双手值得这份善待。"
        }
    }

    // ---- 日用品 ----

    fun describeHouseholdProductPurchase(product: HouseholdProductType): String {
        return when (product) {
            HouseholdProductType.DEHUMIDIFIER -> "你在房间里放了两包除湿剂。那些看不见的水汽——被氯化钙一粒一粒地收走了。房间不潮了——脚气少了一个帮凶。${product.description}"
            HouseholdProductType.SOAP -> "你买了一盒香皂——不是大事。但每天洗手的时候——泡沫和淡淡的味道会提醒你：你在照顾自己。${product.description}"
            HouseholdProductType.MOISTURIZER -> "你买了一瓶护肤品——不是化妆，是保湿。干燥的日子——你的皮肤需要一层保护的膜。${product.description}"
        }
    }

    // ---- 药品 ----

    fun describeDrugUse(effect: DrugEffect): String {
        val tag = when (effect.ingredient.category) {
            DrugCategory.ANTIFUNGAL -> "【脚气用药】"
            DrugCategory.COLD_REMEDY -> "【感冒用药】"
            DrugCategory.CHRONIC_DISEASE -> "【慢性病药】"
            DrugCategory.ANTIBIOTIC -> "【抗生素】"
            DrugCategory.PAIN_RELIEF -> "【止痛】"
        }
        val sideEffectNote = if (effect.sideEffectTriggered) {
            "\n\n副作用出现了——${effect.ingredient.sideEffectType.description}。药帮你解决了一个问题——但也带来了一个小麻烦。这是吃药的一部分代价。"
        } else ""
        return "${tag}你用了${effect.ingredient.label}。${effect.ingredient.description}${sideEffectNote}"
    }

    // ---- 生理期 ----

    fun describeMenstrualEvent(event: MenstrualEvent): String {
        return "【${event.title}】${event.description}\n\n【小镇评述】${event.townCommentary}"
    }

    // ---- 性别婚恋 ----

    fun describeGenderCourtshipEvent(event: GenderCourtshipEvent): String {
        return "【${event.title}】${event.description}\n\n【小镇评述】${event.townCommentary}"
    }

    fun describeGenderCareerEvent(event: GenderCareerEvent): String {
        return "【${event.title}】${event.description}\n\n【小镇评述】${event.townCommentary}"
    }

    // ---- 年代环境 ----

    fun describeEraNormChange(era: com.example.townapp.data.EraSocialNorm): String {
        return when (era) {
            com.example.townapp.data.EraSocialNorm.TRADITIONAL -> "这个年代——婚恋还带着很多传统的影子。彩礼、婚房、男追女——这些都是空气里的东西，不写在纸上——但每个人都能感觉到。"
            com.example.townapp.data.EraSocialNorm.MODERN -> "这个年代——很多旧规矩开始松动了。你听到越来越多的女生主动告白，越来越多的男生说「我不想买房」。不是对的也不是错的——是这个时代在换一种方式呼吸。"
        }
    }

    // ============================================
    // v2.0 医疗系统 文案
    // ============================================

    /** 疾病初发 */
    fun describeDiseaseOnset(disease: ActiveDisease): String {
        return "【${disease.type.label}】${disease.type.description}\n\n【小镇评述】${disease.type.townCommentary}"
    }

    /** 疾病恶化（轻症→更重症） */
    fun describeDiseaseProgression(from: DiseaseType, to: DiseaseType): String {
        return "你的${from.label}拖得太久了——它变成了${to.label}。不是突然变的——是一天一天、一次一次，身体终于撑不住发出的第二次警告。"
    }

    /** 疾病转为慢性 */
    fun describeDiseaseChronic(disease: ActiveDisease): String {
        return "你的${disease.type.label}已经转为慢性——它会陪你很久。不是治不好——是很难彻底好了。以后的日子，你需要学会和一个不完美但还能运转的身体和解。"
    }

    /** 永久后遗症 */
    fun describePermanentSequela(disease: ActiveDisease): String {
        return "你的${disease.type.label}留下了一个永久的印记——这不是伤疤，是身体在这次博弈中付出的代价。它会在你以后的每一次体检、每一个阴雨天里出现——提醒你：有些伤，身体记得比心久。"
    }

    /** 疾病痊愈 */
    fun describeDiseaseCured(disease: ActiveDisease): String {
        return "你的${disease.type.label}好了。身体安静下来了——那些痒、那些疼、那些闷痛——离开了。你重新回到了健康的日常——这个日常很普通——但对你来说，它不是理所当然的。"
    }

    /** 治疗路线选择 */
    fun describeTreatmentRouteChoice(route: TreatmentRoute): String {
        return "你选择了${route.label}作为你的默认医疗路线。\n\n${route.townCommentary}"
    }

    /** 开始治疗 */
    fun describeTreatmentStart(
        diseaseType: DiseaseType,
        route: TreatmentRoute,
        drugTier: DrugTier,
        estimatedCost: Double
    ): String {
        return "你决定用${route.label}的方法，配合${drugTier.label}来治疗${diseaseType.label}。\n预计开销：约${estimatedCost.toInt()}元。\n\n${drugTier.townCommentary}"
    }

    /** 放弃治疗 */
    fun describeTreatmentAbandoned(diseaseType: DiseaseType): String {
        return "你决定不再治疗${diseaseType.label}——让身体自己扛。\n\n不是每个人都必须在每个病面前全力以赴——这可能是你的选择，也可能是你的无奈。小镇不替你做决定——但你的身体会替你记得。"
    }

    /** 营养等级变化 */
    fun describeNutritionLevelChange(level: NutritionLevel): String {
        return "你的营养水平调整为：${level.label}。${level.description}"
    }

    // ============================================
    // v2.0 药品详情弹窗文案
    // ============================================

    /** 细分药物详情弹窗 */
    fun describeDrugDetail(drug: SpecificDrug): String {
        val sb = StringBuilder()
        sb.append("【${drug.name}】\n")
        sb.append("所属档位：${drug.tier.label}\n")
        sb.append("用途分类：${drug.usageCategory.label}\n")
        sb.append("适用病症：${drug.applicableDiseases.joinToString("、") { it.label }}\n")
        if (drug.isPrescription) sb.append("⚠ 处方药——不可自行购买长期服用\n")
        if (drug.isExternal) sb.append("💊 外用药\n")
        sb.append("价格区间：${drug.costMin.toInt()} - ${drug.costMax.toInt()} 元\n")
        sb.append("起效天数：约${drug.onsetDays}天\n")
        sb.append("\n${drug.detailDescription}\n")
        if (drug.sideEffectNote.isNotEmpty()) {
            sb.append("\n副作用：${drug.sideEffectNote}\n")
        }
        if (drug.restrictions.isNotEmpty()) {
            sb.append("\n使用限制：${drug.restrictions}\n")
        }
        if (drug.homeCare.isNotEmpty()) {
            sb.append("\n居家护理：${drug.homeCare}\n")
        }
        if (drug.hasAllergyRisk) {
            sb.append("\n⚠ 中成药过敏风险：初次使用少量试用，出现皮疹立刻停药，服用氯雷他定\n")
        }
        return sb.toString()
    }

    /** 药品详情弹窗标题 */
    fun describeDrugDetailTitle(drug: SpecificDrug): String {
        return "${drug.name} · ${drug.tier.label}"
    }

    /** 快速三选一方案文本 */
    fun describeQuickChoice(
        diseaseType: DiseaseType,
        herbal: SpecificDrug?,
        western: SpecificDrug?,
        selfHealNote: String
    ): String {
        val sb = StringBuilder()
        sb.append("【${diseaseType.label}·快速治疗选择】\n\n")
        sb.append("${diseaseType.description}\n\n")

        sb.append("—— 方案一：古法草药 ——\n")
        if (herbal != null) {
            sb.append("${herbal.name}：${herbal.detailDescription}\n")
            sb.append("开销：${herbal.costMin.toInt()}-${herbal.costMax.toInt()}元\n")
        } else {
            sb.append("暂无匹配的草药方案\n")
        }

        sb.append("\n—— 方案二：平价西药 ——\n")
        if (western != null) {
            sb.append("${western.name}：${western.detailDescription}\n")
            sb.append("开销：${western.costMin.toInt()}-${western.costMax.toInt()}元\n")
        } else {
            sb.append("暂无匹配的西药方案——请前往医院检查\n")
        }

        sb.append("\n—— 方案三：顺其自然 ——\n")
        sb.append(selfHealNote)
        return sb.toString()
    }

    // ============================================
    // v2.17 传统俗语·时代局限性 认知模块文案
    // ============================================

    /**
     * 向长辈求助——长辈给出俗语建议（短期焦虑下降，现实问题不解决）
     */
    fun describeElderAdvice(saying: TraditionalSaying): String {
        return "长辈沉思片刻，说了一句老话——\n\n" +
            "「${saying.originalSaying}」\n\n" +
            "你知道他们是好意。这句话在他们的年代，确实帮他们撑过了很多难熬的日子。\n" +
            "你感到了片刻的安慰——长辈的体温，隔着时代传过来，还是暖的。\n" +
            "但你也知道——这句话，解决不了你眼前的困境。"
    }

    /**
     * 解锁认知词条时的完整展示
     */
    fun describeSayingReflection(saying: TraditionalSaying): String {
        val sb = StringBuilder()
        sb.append("【认知解锁：${saying.category.label}】\n\n")
        sb.append("—— 长辈的话 ——\n")
        sb.append("「${saying.originalSaying}」\n\n")
        sb.append("—— 时代的烙印 ——\n")
        sb.append("${saying.eraLimitation}\n\n")
        sb.append("—— 你的时代，你的选择 ——\n")
        sb.append("${saying.modernAlternative}\n\n")
        sb.append("【小镇评述】\n")
        sb.append("长辈没有错——他们只是在用他们那个时代的工具，修理你这个时代的问题。\n")
        sb.append("工具不匹配，不是工具的问题，也不是你的问题。\n")
        sb.append("你听见了他们的关心——然后，你拿了属于你自己的钥匙。")
        return sb.toString()
    }

    /**
     * 认知词条列表总览（按板块展示）
     */
    fun describeSayingCatalog(category: SayingCategory, sayings: List<TraditionalSaying>): String {
        val sb = StringBuilder()
        sb.append("【${category.label}·认知反思】\n")
        sb.append("${category.description}\n\n")
        sayings.forEachIndexed { index, saying ->
            sb.append("${index + 1}. 「${saying.originalSaying}」\n")
        }
        sb.append("\n点击查看完整反思内容")
        return sb.toString()
    }

    /**
     * 求助长辈后无可用俗语时的兜底文案
     */
    fun describeElderAdviceFallback(): String {
        return "长辈沉默了很久。\n\n" +
            "他们想说点什么——但他们发现，自己这辈子攒下来的经验，好像没有一条能对上你现在的处境。\n" +
            "最后，他们拍了拍你的肩膀。\n\n" +
            "「孩子——我们那个时候，没有你这样的路。所以我们也不知道该怎么走。」\n" +
            "「但你没做错什么。只是时代变了。」\n\n" +
            "你感到了一阵酸涩——不是失望，是心疼。\n" +
            "心疼他们被困在旧时代的经验里，也心疼自己——站在新旧之间的裂缝里，没有人能给你指路。"
    }

    /**
     * 旧书房 —— 成语卡片浏览文案（三段式统一格式）
     *
     * 格式：旧时背景 → 时代局限 → 现代选择
     * 复用现有字段，不新增数据结构：
     *   traditionalMeaning = 旧时背景（传统释义+农耕场景）
     *   awakeningMeaning  = 时代局限解读 + 现代视角
     *   spotlight          = 现代可选方向（客观罗列）
     *   actionSuggestion   = 行动建议
     */
    fun describeIdiomCard(idiom: IdiomData): String {
        val sb = StringBuilder()
        sb.append("━━━━━━━━━━━━━━━━━━\n")
        sb.append("  ${idiom.name}\n")
        sb.append("━━━━━━━━━━━━━━━━━━\n\n")

        // 第一段：旧时背景
        sb.append("【旧时背景】\n")
        sb.append("${idiom.traditionalMeaning}\n\n")

        // 第二段：时代局限 + 现代视角
        sb.append("【时代局限 & 现代视角】\n")
        sb.append("${idiom.awakeningMeaning}\n")

        // 第三段：现代选择（如果有 spotlight）
        if (idiom.spotlight.isNotBlank()) {
            sb.append("\n【现代可选方向】\n")
            sb.append("${idiom.spotlight}\n")
        }

        if (idiom.actionSuggestion.isNotBlank()) {
            sb.append("\n【你可以试试】\n")
            sb.append("${idiom.actionSuggestion}\n")
        }

        if (idiom.academicRefs.isNotEmpty()) {
            sb.append("\n【学术参考】\n")
            idiom.academicRefs.take(2).forEach { ref ->
                sb.append("· ${ref.summary}（${ref.researcher}, ${ref.year}）\n")
            }
        }

        sb.append("\n━━━━━━━━━━━━━━━━━━\n")
        sb.append("这卷竹简没有对错，只有一种视角。\n")
        sb.append("你觉得有用，就留下来；觉得不对，就放回去。\n")
        return sb.toString()
    }

    /**
     * 长辈建议 —— 用传统成语给出情绪安慰
     *
     * 长辈说一句俗语 → 角色焦虑微降，但现实困境无任何改善。
     * 话术风格：温暖、朴实、带着旧时代的局限，但真心想帮你。
     */
    fun describeElderIdiomAdvice(idiom: IdiomData, sceneName: String): String {
        val sb = StringBuilder()
        sb.append("长辈听完了你的烦恼，沉默了一会儿。\n\n")
        sb.append("「孩子，」他们缓缓开口，")
        sb.append("「我们那时候有句话，叫『${idiom.name}』。」\n\n")
        sb.append("${idiom.traditionalMeaning}\n\n")
        sb.append("他们说这话的时候，眼神里带着那个年代特有的笃定——\n")
        sb.append("那是他们这辈子攒下来的经验，是他们能给你的最好的东西。\n\n")
        sb.append("你听进去了，心里稍微好受了一点。\n")
        sb.append("但你也知道，$sceneName 的困境，不会因为一句话就消失。\n\n")
        sb.append("【点击查看这条俗语的完整解读 → 旧书房】")
        return sb.toString()
    }

    /**
     * 穿搭冲突 —— 长辈点评你的穿着
     *
     * 不改变穿搭属性、不影响社交好感，只体现代际审美差异。
     */
    fun describeOutfitConflictAdvice(idiom: IdiomData, outfitName: String): String {
        return "长辈看着你穿的「${outfitName}」，欲言又止。\n\n" +
            "「${idiom.name}」—— 他们还是没忍住，说了一句。\n\n" +
            "你听得出，那不是批评，是他们在用自己的方式说：\n" +
            "「我们那个年代，穿衣服不是这样的。」\n\n" +
            "你笑了笑。\n" +
            "你知道自己穿得开心就好，\n" +
            "但也理解他们——他们只是还没习惯这个时代的审美。\n\n" +
            "【点击查看「${idiom.name}」的完整解读 → 旧书房】"
    }

    /**
     * 长辈无话可说时的兜底文案
     */
    fun describeElderNoAdviceFallback(sceneName: String): String {
        return "长辈沉默了很久。\n\n" +
            "他们想说点什么——但他们发现，自己这辈子攒下来的经验，\n" +
            "好像没有一条能对上你现在的处境。\n\n" +
            "最后，他们拍了拍你的肩膀。\n\n" +
            "「孩子——我们那个时候，没有你这样的路。」\n" +
            "「但你没做错什么。只是时代变了。」\n\n" +
            "你感到了一阵酸涩——不是失望，是心疼。\n" +
            "心疼他们被困在旧时代的经验里，\n" +
            "也心疼自己——站在新旧之间的裂缝里，\n" +
            "没有人能给你指路。"
    }
}