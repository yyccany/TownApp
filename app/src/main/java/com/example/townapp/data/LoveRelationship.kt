package com.example.townapp.data

/**
 * 爱情/婚恋/亲密关系体系（v2.10 人际圈层层-深度私人关系）
 *
 * 核心设计理念：
 * 1. 归入人际圈层层，属于深度私人关系，区别于邻里同事等浅社交
 * 2. 青年阶段（18-30岁）核心人生变量，直接干预择业、定居、事业规划
 * 3. 失恋/分手/婚姻破裂 → 同步牵动焦虑、孤独、信念韧性、自我认同整套参数
 * 4. 四类情景：奔赴伴侣 → 失恋受挫 → 婚姻育儿 → 伴侣分歧
 * 5. 联动闭环：亲密关系 → 心理参数波动 → 调整个人抉择（城市、职业、人生目标）
 *
 * 世界观定位：
 *   婚恋带来的甜蜜、心碎、现实妥协，都是现实社会普遍现象。
 *   贫富阶层仅改变压力来源，不改变情感层面的痛苦与幸福。
 *   爱情不是"额外加分项"——它就是人生本身。
 */

// ============================================
// 一、婚恋状态枚举
// ============================================

/**
 * 当前婚恋关系状态
 */
enum class LoveStatus(val label: String, val description: String) {
    /** 单身：无固定伴侣 */
    SINGLE("单身", "无固定伴侣。自由，但偶尔也会渴望有人分享生活。"),
    /** 恋爱中：有伴侣但未结婚 */
    IN_LOVE("恋爱中", "有了一个人，你的生活多了一份温暖——也多了一份需要考虑的事情。"),
    /** 已婚：组建家庭 */
    MARRIED("已婚", "两个人搭伙过日子。有依靠，也有磨合——甜蜜和摩擦都翻倍了。"),
    /** 离异：婚姻破裂 */
    DIVORCED("离异", "一段关系走到了尽头。不是失败——是你们选择了一条不再同行的路。"),
    /** 丧偶：伴侣离世 */
    WIDOWED("丧偶", "最重的一种失去。那个人不在了，但你们一起走过的路，永远在。")
}

// ============================================
// 二、四类核心情景
// ============================================

/**
 * 婚恋核心情景类型
 */
enum class LoveScenario(val label: String, val description: String) {
    /** 情景1：奔赴伴侣，变更规划，耽搁事业 */
    STAY_FOR_LOVE(
        "奔赴伴侣",
        "为相守放弃大城市机会，选择小城生活。短期幸福上升，长期可能不甘。"
    ),
    /** 情景2：失恋受挫，事业计划停滞 */
    HEARTBREAK(
        "失恋受挫",
        "备考/创业期间遭遇分手，焦虑飙升，自我认同下滑。可能短期消沉、长期退缩或逆势蜕变。"
    ),
    /** 情景3：婚姻组建家庭，育儿开支调整赛道 */
    FAMILY_DUTY(
        "婚姻家庭",
        "结婚生子后，房贷育儿开销推高压力，放弃自由职业转向安稳岗位。"
    ),
    /** 情景4：伴侣观念分歧，人生路线冲突 */
    PATH_CONFLICT(
        "路线冲突",
        "一方想打拼闯荡，一方想安稳小城。妥协、异地、分手——每个选择指向不同人生。"
    )
}

// ============================================
// 三、婚恋运行时状态
// ============================================

/**
 * 婚恋系统运行时状态
 */
data class LoveRelationshipState(
    /** 当前关系状态 */
    val status: LoveStatus = LoveStatus.SINGLE,
    /** 伴侣简要描述 */
    val partnerDescription: String = "",
    /** 恋爱天数（从确定关系起算） */
    val loveDays: Int = 0,
    /** 婚姻天数 */
    val marriageDays: Int = 0,
    /** 是否异地恋 */
    val isLongDistance: Boolean = false,
    /** 异地持续天数 */
    val longDistanceDays: Int = 0,
    /** 婚姻矛盾累积天数 */
    val maritalConflictDays: Int = 0,
    /** 婚恋带来的幸福加成（临时） */
    val happinessBonus: Int = 0,
    /** 关系压力值（经济/观念/家庭冲突） */
    val relationshipStress: Int = 0,
    /** 是否因婚恋影响了职业选择 */
    val hasCareerImpact: Boolean = false,
    /** 是否因婚恋放弃了改革/科研理想 */
    val hasAbandonedIdeal: Boolean = false,
    /** 伴侣是否支持革新理想 */
    val partnerSupportsReform: Boolean = false,
    /** 已触发的婚恋事件ID列表 */
    val triggeredEventIds: List<String> = emptyList(),
    /** 是否经历过失恋（用于后续心理影响） */
    val hasExperiencedHeartbreak: Boolean = false,
    /** 失恋后恢复状态 */
    val heartbreakRecoveryPhase: HeartbreakPhase = HeartbreakPhase.NOT_APPLICABLE,
    /** 是否有子女 */
    val hasChildren: Boolean = false,
    /** 子女数量 */
    val childrenCount: Int = 0
) {
    /** 是否处于关系中 */
    val isInRelationship: Boolean
        get() = status == LoveStatus.IN_LOVE || status == LoveStatus.MARRIED

    /** 关系稳定性 */
    val relationshipStability: Int
        get() {
            if (!isInRelationship) return 0
            val base = 60
            val stressPenalty = relationshipStress / 2
            val conflictPenalty = maritalConflictDays / 5
            val distancePenalty = if (isLongDistance) longDistanceDays / 10 else 0
            return (base - stressPenalty - conflictPenalty - distancePenalty).coerceIn(0, 100)
        }
}

/**
 * 失恋后恢复阶段
 */
enum class HeartbreakPhase(val label: String) {
    NOT_APPLICABLE("未经历"),
    SHOCK("冲击期"),
    GRIEVING("哀伤期"),
    ADJUSTING("调整期"),
    RECOVERED("恢复期"),
    TRANSFORMED("蜕变期")   // 逆势成长
}

// ============================================
// 四、分年龄段婚恋事件
// ============================================

/**
 * 婚恋随机事件
 */
data class LoveEvent(
    val id: String,
    val scenario: LoveScenario,
    /** 目标年龄段 */
    val ageGroup: LoveAgeGroup,
    /** 触发所需的最小年龄 */
    val minAge: Int,
    /** 触发所需的最大年龄 */
    val maxAge: Int,
    /** 触发概率基数 */
    val baseProbability: Double,
    /** 触发条件（关系状态） */
    val requiredStatus: LoveStatus? = null,
    /** 事件描述 */
    val description: String,
    /** 小镇评述 */
    val townCommentary: String,
    /** 后续可选抉择 */
    val choices: List<LovePlayerChoice>
)

/**
 * 婚恋事件年龄段
 */
enum class LoveAgeGroup(val label: String, val ageRange: String, val description: String) {
    YOUTH("青年", "18-30岁", "爱情事件触发概率最高，直接左右择业、定居城市、求学方向"),
    MIDLIFE("中年", "31-55岁", "婚恋矛盾、婚姻危机为主，冲击现有稳定生活"),
    ELDERLY("老年", "60岁以上", "伴侣离世，丧偶事件拉高孤独值，调整晚年生活方式")
}

// ============================================
// 五、玩家抉择选项
// ============================================

/**
 * 面对婚恋事件时的玩家抉择
 */
data class LovePlayerChoice(
    val id: String,
    val label: String,
    val description: String,
    /** 抉择后的关系状态 */
    val resultStatus: LoveStatus,
    /** 幸福变化 */
    val happinessDelta: Int = 0,
    /** 孤独变化 */
    val lonelinessDelta: Int = 0,
    /** 焦虑变化 */
    val anxietyDelta: Int = 0,
    /** 信念韧性变化 */
    val resilienceDelta: Int = 0,
    /** 自我认同变化 */
    val identityDelta: Int = 0,
    /** 关系压力变化 */
    val stressDelta: Int = 0,
    /** 职业影响描述 */
    val careerImpact: String = "",
    /** 是否影响城市定居 */
    val triggersCityChange: Boolean = false,
    /** 城市等级变化（迁往） */
    val cityTierChange: Int = 0,
    /** 是否放弃理想（革新者候选） */
    val causesIdealAbandonment: Boolean = false,
    /** 结果文案 */
    val resultText: String
)

// ============================================
// 六、婚恋后果与心理闭环
// ============================================

/**
 * 婚恋事件对心理/社交/职业的后续影响
 */
data class LoveConsequence(
    val scenario: LoveScenario,
    /** 孤独值变化 */
    val lonelinessDelta: Int,
    /** 焦虑值变化 */
    val anxietyDelta: Int,
    /** 幸福感变化 */
    val happinessDelta: Int,
    /** 信念韧性变化 */
    val resilienceDelta: Int,
    /** 自我认同变化 */
    val identityDelta: Int,
    /** 社交意愿变化 */
    val socialWillDelta: Int,
    /** 是否触发逃避行为 */
    val triggersWithdrawal: Boolean,
    /** 逃避行为概率加成 */
    val withdrawalProbabilityBonus: Double,
    /** 职业路径影响 */
    val careerPathImpact: String,
    /** 后续叙事情景 */
    val followUpScenario: String
)

object LoveConsequences {

    /** 不同情景的初始心理冲击 */
    fun initialImpact(scenario: LoveScenario): LoveConsequence = when (scenario) {
        LoveScenario.STAY_FOR_LOVE -> LoveConsequence(
            scenario, lonelinessDelta = -10, anxietyDelta = -3,
            happinessDelta = 8, resilienceDelta = 0, identityDelta = 2,
            socialWillDelta = 0, triggersWithdrawal = false,
            withdrawalProbabilityBonus = 0.0,
            careerPathImpact = "放弃大城市机会，薪资上限下调，时代红利窗口错过",
            followUpScenario = "部分人多年后心生不甘，迷茫值上涨"
        )
        LoveScenario.HEARTBREAK -> LoveConsequence(
            scenario, lonelinessDelta = 15, anxietyDelta = 12,
            happinessDelta = -12, resilienceDelta = -8, identityDelta = -10,
            socialWillDelta = -20, triggersWithdrawal = true,
            withdrawalProbabilityBonus = 0.30,
            careerPathImpact = "事业计划可能暂停1-3个月（短期）或数年（长期退缩）",
            followUpScenario = "短期消沉/长期退缩/逆势蜕变 三选一"
        )
        LoveScenario.FAMILY_DUTY -> LoveConsequence(
            scenario, lonelinessDelta = -5, anxietyDelta = 5,
            happinessDelta = -2, resilienceDelta = -3, identityDelta = -3,
            socialWillDelta = 0, triggersWithdrawal = false,
            withdrawalProbabilityBonus = 0.05,
            careerPathImpact = "自由职业→安稳岗位，个人时间缩减，革新者支线解锁难度提升",
            followUpScenario = "长期家庭琐事叠加，疲惫值持续上涨"
        )
        LoveScenario.PATH_CONFLICT -> LoveConsequence(
            scenario, lonelinessDelta = 8, anxietyDelta = 8,
            happinessDelta = -6, resilienceDelta = -4, identityDelta = -3,
            socialWillDelta = -5, triggersWithdrawal = false,
            withdrawalProbabilityBonus = 0.10,
            careerPathImpact = "妥协/异地/分手，每条路指向不同的人生轨迹",
            followUpScenario = "异地模式拉高孤独值，长期分居触发婚姻矛盾"
        )
    }

    /** 异地长期副作用 */
    fun longDistanceEffect(days: Int): LoveConsequence = LoveConsequence(
        scenario = LoveScenario.PATH_CONFLICT,
        lonelinessDelta = if (days > 30) 3 else 1,
        anxietyDelta = 2, happinessDelta = -2,
        resilienceDelta = -1, identityDelta = 0,
        socialWillDelta = 0, triggersWithdrawal = false,
        withdrawalProbabilityBonus = 0.05,
        careerPathImpact = "",
        followUpScenario = if (days > 90) "长期分居极易触发后续婚姻矛盾" else ""
    )

    /** 婚姻矛盾累积效应 */
    fun maritalConflictEffect(days: Int): LoveConsequence = LoveConsequence(
        scenario = LoveScenario.FAMILY_DUTY,
        lonelinessDelta = if (days > 14) 5 else 2,
        anxietyDelta = 4, happinessDelta = -4,
        resilienceDelta = -2, identityDelta = -2,
        socialWillDelta = if (days > 30) -10 else -3,
        triggersWithdrawal = days > 21,
        withdrawalProbabilityBonus = if (days > 21) 0.20 else 0.05,
        careerPathImpact = if (days > 30) "婚姻破裂可能触发重新规划事业、迁居城市" else "",
        followUpScenario = ""
    )

    /** 丧偶的心理冲击 */
    val WIDOWHOOD_IMPACT = LoveConsequence(
        scenario = LoveScenario.HEARTBREAK,
        lonelinessDelta = 25, anxietyDelta = 10,
        happinessDelta = -20, resilienceDelta = -15,
        identityDelta = -12, socialWillDelta = -30,
        triggersWithdrawal = true, withdrawalProbabilityBonus = 0.50,
        careerPathImpact = "",
        followUpScenario = "老人可能封闭社交，或调整晚年生活方式，开启独居养老模式"
    )
}

// ============================================
// 七、婚恋主动修复与出路
// ============================================

/**
 * 面对婚恋困境时的恢复路径选项
 */
data class LoveRecoveryOption(
    val id: String,
    val label: String,
    val description: String,
    val resultStatus: LoveStatus,
    val breaksHeartbreak: Boolean
)

object LoveRecoveryOptions {

    val options: List<LoveRecoveryOption> = listOf(
        LoveRecoveryOption(
            id = "rest_and_recover",
            label = "给自己时间恢复",
            description = "你不急着找人，也不急着振作。你给自己放个假——先让自己缓过来。",
            resultStatus = LoveStatus.SINGLE,
            breaksHeartbreak = true
        ),
        LoveRecoveryOption(
            id = "bury_in_work",
            label = "埋头工作，转移注意",
            description = "你把所有的精力和情绪都投入了工作。忙碌是最好的麻醉剂——至少白天不会想。",
            resultStatus = LoveStatus.SINGLE,
            breaksHeartbreak = true
        ),
        LoveRecoveryOption(
            id = "try_again",
            label = "重新开始寻找",
            description = "你觉得自己准备好了。你开始重新认识人——不是为了忘记谁，是为了重新认识自己。",
            resultStatus = LoveStatus.SINGLE,
            breaksHeartbreak = true
        ),
        LoveRecoveryOption(
            id = "accept_single",
            label = "接受独身，享受自由",
            description = "你不再觉得单身是过渡期。你开始享受一个人的生活——不需要向任何人报备，不需要妥协。",
            resultStatus = LoveStatus.SINGLE,
            breaksHeartbreak = true
        )
    )
}

// ============================================
// 八、青年婚恋事件库（分年龄段）
// ============================================

object LoveEvents {

    val events: List<LoveEvent> = listOf(

        // ======== 青年阶段（18-30）========

        // 情景1：奔赴伴侣
        LoveEvent(
            id = "youth_stay_for_love_small_city",
            scenario = LoveScenario.STAY_FOR_LOVE,
            ageGroup = LoveAgeGroup.YOUTH,
            minAge = 20, maxAge = 30,
            baseProbability = 0.06,
            requiredStatus = LoveStatus.IN_LOVE,
            description = "你在大城市找到了一份不错的工作机会——但你的伴侣留在了家乡的小城。你需要在两周内做出决定。去大城市意味着月薪翻倍、接触前沿行业；留下来意味着放弃这些——但每天都能看到她。",
            townCommentary = "这不是一道选择题——是两种不同人生的岔路口。大城市有风口，小城有她。无论你怎么选，你都会失去点什么——但你也一定会得到点什么。只是现在你还不知道那是什么。",
            choices = listOf(
                LovePlayerChoice("stay_small", "留在小城，和她在一起",
                    "你拒绝了那个offer。你把机票退了——不是因为你不想要那个机会，是因为你更想要她。",
                    LoveStatus.IN_LOVE, happinessDelta = 10, lonelinessDelta = -12,
                    anxietyDelta = -3, resilienceDelta = -2, identityDelta = 3,
                    stressDelta = -5, careerImpact = "放弃大城市新兴行业机会，时代红利窗口错过，薪资上限下调",
                    triggersCityChange = true, cityTierChange = -1,
                    resultText = "你留下来了。有人觉得你傻——但你知道自己要什么。以后会不会后悔？那是以后的事。今天你只想看到她笑着推门进来。"),
                LovePlayerChoice("go_big_city_long_distance", "去大城市，异地保持联系",
                    "你决定去——不过你和她达成了一个协议：每个周末视频，每个月见一次面。你不想放弃机会，也不想放弃她。",
                    LoveStatus.IN_LOVE, happinessDelta = 2, lonelinessDelta = 5,
                    anxietyDelta = 4, resilienceDelta = 1, identityDelta = 2,
                    stressDelta = 3, careerImpact = "进入大城市新兴行业，发展空间打开",
                    triggersCityChange = true, cityTierChange = 1,
                    resultText = "你坐上了去大城市的火车。窗外是她越来越小的身影。你告诉自己——这只是暂时的。但'暂时'要多久？你自己也不知道。"),
                LovePlayerChoice("break_for_career", "分手，独自去大城市",
                    "你和她说清楚了。你不想在最好的年纪困在一座小城里——你还有野心，还有想做的事。分手的时候你们都很难受——但你知道这是对的。",
                    LoveStatus.SINGLE, happinessDelta = -5, lonelinessDelta = 10,
                    anxietyDelta = 5, resilienceDelta = 3, identityDelta = 0,
                    stressDelta = -10, careerImpact = "事业发展空间完全打开",
                    triggersCityChange = true, cityTierChange = 1,
                    resultText = "你一个人到了大城市。房间很小，街上很吵，身边没有认识的人——但你觉得自由。你失去了一个人，但你得到了属于自己的一条路。这条路会通向哪里——你还不知道。")
            )
        ),

        // 情景2：失恋受挫
        LoveEvent(
            id = "youth_heartbreak_exam",
            scenario = LoveScenario.HEARTBREAK,
            ageGroup = LoveAgeGroup.YOUTH,
            minAge = 20, maxAge = 28,
            baseProbability = 0.07,
            requiredStatus = LoveStatus.IN_LOVE,
            description = "就在你备考/筹备创业最关键的时候——你们分手了。你坐在书桌前，什么都看不进去。脑子不是空的——是太满了，全是她。你告诉自己不能停——但你的身体不听你的。",
            townCommentary = "在最不该分心的时候分了心。不是你不坚强——是你在同时扛两场仗：一场是事业，一场是你的心。没有人能同时打赢两场仗——所以先歇一歇。",
            choices = listOf(
                LovePlayerChoice("short_grieve", "短期休整，缓过来再继续",
                    "你给自己放了一个月的假。不看书、不想计划、不去想未来。你允许自己难受——一个月后，你重新拿起了书。",
                    LoveStatus.SINGLE, happinessDelta = -10, lonelinessDelta = 12,
                    anxietyDelta = 8, resilienceDelta = -3, identityDelta = -5,
                    stressDelta = -5, careerImpact = "1-3个月搁置计划，短期进度受阻但可恢复",
                    resultText = "那一个月过得很慢——但是你挺过来了。重新坐回桌前的那一刻，你感觉不是回到了原来的状态——是变成了一个比之前更坚韧的人。"),
                LovePlayerChoice("long_withdraw", "陷入悲伤，暂时放下一切",
                    "你决定不勉强自己了。你放下了所有计划——什么时候能捡起来，你不知道。你只想一个人待着。",
                    LoveStatus.SINGLE, happinessDelta = -15, lonelinessDelta = 18,
                    anxietyDelta = 15, resilienceDelta = -10, identityDelta = -12,
                    stressDelta = 10, careerImpact = "推迟数年择业，放弃外出打拼，就近选择安稳工作，理想路线永久延后",
                    resultText = "你把自己关了起来。一天、一周、一个月……时间过得模糊了。你不是不想振作——是你发现，找回自己比想象中更难。"),
                LovePlayerChoice("transform_pain", "挫折之后，全身心投入事业",
                    "你把所有的情绪都压进了工作里。每天晚上你坐在桌前，脑子还在想她——但你不管。你翻开第一页书，然后是第二页。你用行动告诉自己：你可以被伤到——但不会被击倒。",
                    LoveStatus.SINGLE, happinessDelta = -6, lonelinessDelta = 8,
                    anxietyDelta = 5, resilienceDelta = 8, identityDelta = 3,
                    stressDelta = -5, careerImpact = "信念韧性提升，加快事业节奏，逆境中完成蜕变",
                    resultText = "那段时间很苦——但回头看，你发现那一次分手反而推了你一把。你没有选择消沉——你选择了在废墟上建一座更坚固的房子。")
            )
        ),

        // 情景3：婚姻育儿调整赛道（青年阶段）
        LoveEvent(
            id = "youth_marriage_career_shift",
            scenario = LoveScenario.FAMILY_DUTY,
            ageGroup = LoveAgeGroup.YOUTH,
            minAge = 25, maxAge = 35,
            baseProbability = 0.05,
            requiredStatus = LoveStatus.IN_LOVE,
            description = "你们结婚了。孩子出生之后，房租、奶粉、尿布、早教——每一样都在提醒你：自由的日子结束了。你原来做的那份自由度高的创业/科研工作，收入不稳定——你开始认真考虑换一份朝九晚五的安稳工作。",
            townCommentary = "不是你不热爱原来的工作了——是你现在有了更大的责任。当你需要稳定输出来养活两个人的时候，理想就不得不让一让。这不是妥协——是你在用另一种方式保护你爱的东西。",
            choices = listOf(
                LovePlayerChoice("take_stable_job", "换安稳工作，保障家庭收入",
                    "你投了几份简历，面试了一家国企。对方问你为什么换工作——你说：'家里添了新成员。' 对方点了点头，没有再问。",
                    LoveStatus.MARRIED, happinessDelta = 2, lonelinessDelta = -8,
                    anxietyDelta = -5, resilienceDelta = -3, identityDelta = -5,
                    stressDelta = -10, careerImpact = "自由职业→安稳岗位，个人自由时间缩减，革新者/科创者支线解锁难度提升",
                    resultText = "新工作不算有趣——但每个月工资准时到账的那一刻，你觉得安心。你不再是一个人在拼——你的身后多了一个需要你保护的小家。"),
                LovePlayerChoice("keep_current_risk", "坚持现有工作，承受压力",
                    "你没有辞职。你咬着牙继续做你的事——白天忙创业/科研，晚上带孩子。你知道这样很累——但你不想放弃。",
                    LoveStatus.MARRIED, happinessDelta = -3, lonelinessDelta = -3,
                    anxietyDelta = 10, resilienceDelta = 3, identityDelta = 5,
                    stressDelta = 15, careerImpact = "保持原有路径，但经济压力持续，身体长期处于高负荷状态",
                    resultText = "你很累——但你不后悔。虽然赚的不稳定，但你做的是你真正想做的事。只是有时候半夜醒来，你会看一眼旁边睡着的孩子——然后默默地对自己说：再撑一下。")
            )
        ),

        // 情景4：伴侣观念分歧（青年阶段）
        LoveEvent(
            id = "youth_path_conflict",
            scenario = LoveScenario.PATH_CONFLICT,
            ageGroup = LoveAgeGroup.YOUTH,
            minAge = 24, maxAge = 32,
            baseProbability = 0.06,
            requiredStatus = LoveStatus.IN_LOVE,
            description = "你们吵了一架——不是第一次了。你想去大城市闯一闯，她想留在小城过安稳日子。每次说到这个话题，气氛就僵住了。你们都知道，这个问题没法各退一步——必须有一个人妥协。",
            townCommentary = "两个人都没错——错的是同一个问题有两份正确答案。你要的是可能，她要的是确定。这不是沟通的问题——这是两条不同方向的铁轨，没办法同时走。",
            choices = listOf(
                LovePlayerChoice("compromise_stay", "妥协留下，优先关系",
                    "你选择了留下。你说服自己——不是每个人都需要去大城市打拼。留下来她开心，你也慢慢适应了这个节奏。",
                    LoveStatus.IN_LOVE, happinessDelta = 3, lonelinessDelta = -5,
                    anxietyDelta = -3, resilienceDelta = -3, identityDelta = -4,
                    stressDelta = -10, careerImpact = "放弃大城市发展机会",
                    resultText = "你留下来了。日子很安稳——但偶尔深夜你会打开招聘软件，看一眼大城市的岗位，然后默默关掉。你告诉自己：这没什么——每个选择都有代价。"),
                LovePlayerChoice("long_distance", "异地，各走各的路",
                    "你们决定试一试异地。你去大城市上班，她留在小城。你们约定好每天通电话——但你知道，异地从来不是长久之计。",
                    LoveStatus.IN_LOVE, happinessDelta = -2, lonelinessDelta = 10,
                    anxietyDelta = 5, resilienceDelta = 1, identityDelta = 2,
                    stressDelta = 5, careerImpact = "各自发展，但关系压力累积",
                    resultText = "你在出租屋里接起她的电话。她说今天很好、一切如常——但你能听出声音里的落寞。异地就像两个人在不同的船上划桨——虽然方向一致，但你们之间隔着水。"),
                LovePlayerChoice("break_for_path", "分手，各自发展",
                    "你们坐下来谈了很久。最后你说了那句谁都不想说出口的话：'我们可能不适合一起走下去。' 她说：'我知道。' 然后你们安静地坐了一会儿——没有哭，也没有吵。",
                    LoveStatus.SINGLE, happinessDelta = -8, lonelinessDelta = 15,
                    anxietyDelta = 5, resilienceDelta = 2, identityDelta = 1,
                    stressDelta = -15, careerImpact = "各自选择最适合自己的路线",
                    resultText = "分手之后，你开始去大城市上班。你不想骗自己说这很容易——但你确实不再需要在两个方向上纠结了。你选了一条路，她选了另一条。你们还会想起彼此——但不想再回到过去了。")
            )
        ),

        // ======== 中年阶段（31-55）========

        // 情景3：中年婚姻矛盾（中年阶段核心事件）
        LoveEvent(
            id = "midlife_marriage_crisis",
            scenario = LoveScenario.FAMILY_DUTY,
            ageGroup = LoveAgeGroup.MIDLIFE,
            minAge = 35, maxAge = 55,
            baseProbability = 0.08,
            requiredStatus = LoveStatus.MARRIED,
            description = "房贷、孩子的补习费、老人生病的医药费——你们最近又在为钱的事吵架了。你们已经很久没有好好说过话了。每次开口，不是谈钱就是谈孩子——好像你们不是夫妻，是一对合伙人。",
            townCommentary = "婚姻走到这一步——不是因为不爱了，是因为太累了。当生活的重量压过了两个人之间的温度，爱还在——只是你们都来不及去感受它了。",
            choices = listOf(
                LovePlayerChoice("work_on_it", "主动沟通，修复关系",
                    "你那天晚上没有加班。你回到家，坐在她旁边，说了句：'我们聊聊吧——不是聊钱的事。' 那天你们聊到很晚。",
                    LoveStatus.MARRIED, happinessDelta = 5, lonelinessDelta = -8,
                    anxietyDelta = -4, resilienceDelta = 3, identityDelta = 2,
                    stressDelta = -10, careerImpact = "关系改善，心理负担减轻",
                    resultText = "那天晚上你们说了很多——包括很久都没说过的那句：'我知道你也累。' 问题没有全部解决——但你们又站在同一边了。"),
                LovePlayerChoice("avoid_conflict", "回避矛盾，各忙各的",
                    "你没有找她谈。你选择了加班——不是在逃避，是你觉得说了也没用。你们继续各忙各的——家还是那个家，但两个人之间隔了一个客厅。",
                    LoveStatus.MARRIED, happinessDelta = -5, lonelinessDelta = 8,
                    anxietyDelta = 5, resilienceDelta = -2, identityDelta = -3,
                    stressDelta = 8, careerImpact = "矛盾累积，后续婚姻破裂风险上升",
                    resultText = "你们维持着表面的平静——但墙上的裂缝正在一点一点变宽。你们的婚姻像一个没有开灯的屋子：东西都还在，但谁也看不清谁。"),
                LovePlayerChoice("consider_divorce", "考虑离婚，重新规划",
                    "你开始认真考虑离婚。这个念头你压了很久——但每次吵完架它都会冒出来。你算了一笔账：离婚之后你要一个人还房贷、一个人带孩子。但你也算了一笔账：继续这样过下去，你要消耗多少年。",
                    LoveStatus.DIVORCED, happinessDelta = -10, lonelinessDelta = 12,
                    anxietyDelta = 8, resilienceDelta = -5, identityDelta = -5,
                    stressDelta = 5, careerImpact = "婚姻破裂后重新规划事业、迁居城市",
                    resultText = "你们离了。那个一起还了十年的房贷，名字划掉了一个。你看着那张离婚证——感觉松了一口气，又觉得心里空了一个洞。从此以后你要一个人走了——但你不再需要向第二个人解释每一步。")
            )
        ),

        // ======== 老年阶段（60+）========

        // 情景1/4合并：伴侣离世
        LoveEvent(
            id = "elderly_spouse_passing",
            scenario = LoveScenario.HEARTBREAK,
            ageGroup = LoveAgeGroup.ELDERLY,
            minAge = 60, maxAge = 99,
            baseProbability = 0.10,
            requiredStatus = LoveStatus.MARRIED,
            description = "他/她走了。那个和你一起过了大半辈子的人，在一个安静的早晨——再也没有睁开眼睛。你坐在床边，没有哭。不是不伤心——是太伤心了，以至于你不知道该怎么表达。",
            townCommentary = "相守了几十年的人突然不在了——这种安静比任何哭泣都要响。不是每段离去都需要安慰——有时候你只需要一个人知道：你失去了什么。",
            choices = listOf(
                LovePlayerChoice("grieve_quietly", "独自消化，闭门静处",
                    "你不怎么出门了。邻居约你散步你也不去。你坐在家里，翻着老相册——看着那些年轻的照片，你知道那个人不在了——但你觉得他/她好像还在客厅里。",
                    LoveStatus.WIDOWED, happinessDelta = -20, lonelinessDelta = 25,
                    anxietyDelta = 10, resilienceDelta = -15, identityDelta = -12,
                    stressDelta = 5, careerImpact = "",
                    resultText = "你把自己关在屋子里。别人说你该出去走走——你不听。你需要时间。你需要没有人打扰的时间——来和那个人的记忆好好告别。"),
                LovePlayerChoice("find_new_routine", "调整晚年生活，开启独居节奏",
                    "你没有一直关在家里。你开始给自己安排新的事：养一盆花、每天早起散步、去社区公园坐一坐。你不是忘记了那个人——你只是学会了带着那份想念继续活下去。",
                    LoveStatus.WIDOWED, happinessDelta = -10, lonelinessDelta = 15,
                    anxietyDelta = 3, resilienceDelta = 5, identityDelta = 3,
                    stressDelta = -5, careerImpact = "",
                    resultText = "你慢慢找到了一个新的节奏。每天早上起床泡一杯茶，放着老歌——有时候你还是会对着空气说两句话。但你已经不再觉得那种安静是孤独了——它成了你生活的一部分。")
            )
        )
    )

    /** 按年龄段筛选 */
    fun byAgeGroup(ageGroup: LoveAgeGroup): List<LoveEvent> =
        events.filter { it.ageGroup == ageGroup }

    /** 按年龄段和关系状态筛选（排除已触发的事件） */
    fun matching(age: Int, status: LoveStatus, triggeredIds: List<String> = emptyList()): List<LoveEvent> {
        val ageGroup = when {
            age in 18..30 -> LoveAgeGroup.YOUTH
            age in 31..55 -> LoveAgeGroup.MIDLIFE
            age >= 60 -> LoveAgeGroup.ELDERLY
            else -> return emptyList()
        }
        return events.filter {
            it.ageGroup == ageGroup &&
            age in it.minAge..it.maxAge &&
            (it.requiredStatus == null || it.requiredStatus == status) &&
            it.id !in triggeredIds
        }
    }
}