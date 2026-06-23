package com.example.townapp.data

/**
 * 独处逃避 / 回避社交行为体系（v2.9 人格行为分支）
 *
 * 核心设计理念：
 * 1. 回避社交不是缺陷——是压力超标后的自然应激反应，适配原子化社会环境
 * 2. 四种差异化模式：短期休整（健康）→ 阶段性回避（中度）→ 长期深度封闭（极端）→ 主动抗压
 * 3. 不分年龄段，青少年、中年、老年均会出现，压力来源不同但行为机制一致
 * 4. 外部压力是诱因，心理参数决定个人应对倾向，不同人物产生差异化行为
 * 5. 独处不等于负面——短期休整是缓冲，长期独居可以是人生选择
 *
 * 联动闭环：
 *   外部压力 → 焦虑/疲惫/迷茫 → 触发回避行为 → 行为结果反向改变心理/人脉/职业参数
 */

// ============================================
// 一、逃避模式枚举
// ============================================

/**
 * 四种独处/逃避模式
 */
enum class WithdrawalMode(val label: String, val description: String) {
    /** 短期休整式独处：1-3天放空，焦虑回落，正常回归 */
    SHORT_REST(
        "短期休整",
        "焦虑短暂飙升后选择独处放空，远离琐事压力。休整结束后重新处理生活难题——这是健康的情绪缓冲。"
    ),
    /** 阶段性回避：数周减少社交，推迟难题 */
    PHASED_AVOIDANCE(
        "阶段性回避",
        "连续数周回避人际往来，推迟处理家庭矛盾、棘手工作。积压问题增多，孤独值稳步上涨——但还可以回头。"
    ),
    /** 长期深度封闭：数月数年闭门独居 */
    DEEP_WITHDRAWAL(
        "深度封闭",
        "接连遭遇重大变故，长期闭门独居，拒绝社交、工作、家庭沟通。自我认同感持续下滑——这是极少数人会走到的尽头。"
    ),
    /** 主动抗压：拒绝逃避，直面问题 */
    ACTIVE_RESISTANCE(
        "主动抗压",
        "信念韧性充足，遭遇压力后优先直面琐事难题。通过沟通谈判、规划清单解决问题——压力同样存在，但不选择封闭。"
    )
}

// ============================================
// 二、年龄分层压力来源
// ============================================

/**
 * 分年龄段压力来源与逃避触发场景
 */
enum class AgePressureSource(
    val label: String,
    val ageRange: String,
    val pressureSources: String,
    val typicalTrigger: String,
    val withdrawalConsequence: String
) {
    YOUTH(
        "青少年",
        "12-22岁",
        "学业压力、长辈说教、同辈攀比、学业返工、同学矛盾",
        "压力超标后躲进房间玩手机、闭门独处，拒绝和家人沟通",
        "长期逃避拉高孤独值，降低共情能力；短期独处焦虑小幅回落，属于正常缓冲"
    ),
    MIDLIFE(
        "中年",
        "35-55岁",
        "房贷育儿、职场内卷、夫妻矛盾、养老重担、重复枯燥工作",
        "疲惫阈值后下班躲车内独处、居家闭门、回避家庭沟通、减少同事往来",
        "长期逃避加剧家庭隔阂，矛盾累积，迷茫值持续上涨"
    ),
    ELDERLY(
        "老年",
        "60岁以上",
        "亲友离世、身体病痛、子女远离独居、衰老与离别",
        "闭门宅家、拒绝邻里走动、回避社交闲谈、害怕聊起衰老话题",
        "孤独值持续走高，低落情绪滋生；也有老人独处静心，接纳衰老现实"
    )
}

// ============================================
// 三、逃避行为运行状态
// ============================================

/**
 * 逃避行为运行时状态
 */
data class WithdrawalState(
    /** 当前模式 */
    val mode: WithdrawalMode = WithdrawalMode.ACTIVE_RESISTANCE,
    /** 连续逃避天数 */
    val consecutiveWithdrawalDays: Int = 0,
    /** 累计逃避总天数（人生统计） */
    val totalWithdrawalDays: Int = 0,
    /** 是否处于逃避中 */
    val isWithdrawing: Boolean = false,
    /** 触发逃避的压力来源 */
    val triggerSource: String = "",
    /** 触发时的心理状态快照 */
    val triggerSnapshot: String = "",
    /** 社交意愿下降幅度 */
    val socialWillDrop: Int = 0,
    /** 是否已形成闭环（深度封闭后找到独处节奏） */
    val hasFoundSolitudePeace: Boolean = false,
    /** 是否有人际恶性循环（越逃避越怕社交） */
    val hasSocialAnxietyLoop: Boolean = false
) {
    /** 逃避深度等级 */
    val withdrawalDepth: String get() = when (mode) {
        WithdrawalMode.ACTIVE_RESISTANCE -> "无逃避"
        WithdrawalMode.SHORT_REST -> "浅层"
        WithdrawalMode.PHASED_AVOIDANCE -> "中层"
        WithdrawalMode.DEEP_WITHDRAWAL -> "深层"
    }
}

// ============================================
// 四、逃避触发事件
// ============================================

/**
 * 分年龄段的逃避触发事件
 */
data class WithdrawalTriggerEvent(
    val id: String,
    val ageGroup: AgePressureSource,
    /** 触发的逃避模式 */
    val targetMode: WithdrawalMode,
    /** 触发所需的最小压力值（焦虑+疲劳+迷茫的加权） */
    val minPressureThreshold: Int,
    /** 触发概率基数 */
    val baseProbability: Double,
    /** 事件描述 */
    val description: String,
    /** 小镇评述 */
    val townCommentary: String
)

object WithdrawalTriggers {

    val triggers: List<WithdrawalTriggerEvent> = listOf(

        // ==== 青少年 ====
        WithdrawalTriggerEvent(
            id = "youth_short_rest",
            ageGroup = AgePressureSource.YOUTH,
            targetMode = WithdrawalMode.SHORT_REST,
            minPressureThreshold = 50,
            baseProbability = 0.20,
            description = "月考成绩出来了，你又没考好。回到家，你径直走进房间，关上门，戴上耳机。你不想听任何人的话——包括你自己。",
            townCommentary = "关上房门的那一刻，你暂时隔绝了所有让你不舒服的声音。这不是逃避考试——是给自己一个喘息的空间。明天再打开门的时候，你会好一点。"
        ),
        WithdrawalTriggerEvent(
            id = "youth_phased_avoidance",
            ageGroup = AgePressureSource.YOUTH,
            targetMode = WithdrawalMode.PHASED_AVOIDANCE,
            minPressureThreshold = 75,
            baseProbability = 0.08,
            description = "你已经连续好几天没有和家里人说超过三句话了。放学回家就关上门——你不想听他们问成绩、问将来、问那些你也没有答案的问题。",
            townCommentary = "你躲的不是家人——是那些你回答不了的问题。但那些问题不会因为你躲开就消失。它们只是换了一种方式，在深夜的时候来找你。"
        ),

        // ==== 中年（逃避高发区间） ====
        WithdrawalTriggerEvent(
            id = "midlife_short_rest_car",
            ageGroup = AgePressureSource.MIDLIFE,
            targetMode = WithdrawalMode.SHORT_REST,
            minPressureThreshold = 55,
            baseProbability = 0.25,
            description = "下班后，你坐在车里，没有立刻熄火下车。你盯着车库里斑驳的墙壁，什么都不想——这是你一天里唯一属于自己的十五分钟。",
            townCommentary = "车里的那十五分钟，是你在工作和家庭之间唯一的缓冲地带。你不是不想回家——你是需要先把自己从「员工」切换回「自己」。这不是逃避，这是生存。"
        ),
        WithdrawalTriggerEvent(
            id = "midlife_phased_avoidance",
            ageGroup = AgePressureSource.MIDLIFE,
            targetMode = WithdrawalMode.PHASED_AVOIDANCE,
            minPressureThreshold = 70,
            baseProbability = 0.12,
            description = "你已经有好几周没有和同事一起吃饭了。公司的群聊你也不看——你不想接任何新的任务，你只想把手里的事做完，然后回家。你开始躲着所有人。",
            townCommentary = "你累了。不是身体累——是那种对自己说「再撑一下」已经不管用了的累。你躲的不是同事——是那个永远在说「没事」的自己。"
        ),
        WithdrawalTriggerEvent(
            id = "midlife_deep_withdrawal",
            ageGroup = AgePressureSource.MIDLIFE,
            targetMode = WithdrawalMode.DEEP_WITHDRAWAL,
            minPressureThreshold = 85,
            baseProbability = 0.04,
            description = "你不再接电话了。有人敲门你也不开。你把自己关在屋子里，窗帘拉得严严实实。你不知道多少天没出门了——你只知道，出去就意味着要面对那些你面对不了的事。",
            townCommentary = "你把世界关在门外——不是因为你恨这个世界，是因为你再也扛不住它了。没有人能一直扛着——你只是比大多数人更早地放下了。这不是懦弱，是极限。"
        ),

        // ==== 老年 ====
        WithdrawalTriggerEvent(
            id = "elderly_short_rest",
            ageGroup = AgePressureSource.ELDERLY,
            targetMode = WithdrawalMode.SHORT_REST,
            minPressureThreshold = 50,
            baseProbability = 0.15,
            description = "今天身体不太舒服，你没有出门散步。你坐在窗边，看着外面的树和行人——你不需要说话，也不需要被人看见。",
            townCommentary = "人老了，沉默不是孤独——是选择。你不需要每天都和世界保持联系——有时候，和自己待一会儿就够了。"
        ),
        WithdrawalTriggerEvent(
            id = "elderly_phased_avoidance",
            ageGroup = AgePressureSource.ELDERLY,
            targetMode = WithdrawalMode.PHASED_AVOIDANCE,
            minPressureThreshold = 65,
            baseProbability = 0.10,
            description = "你开始避开邻里聚会的场合。你不想听他们聊谁家的子女出息、谁又住院了——这些话题让你觉得，自己离那个世界越来越远了。",
            townCommentary = "你躲的不是邻居——是你害怕成为话题的一部分。当你看到别人在衰老、在生病——你不可避免地看到了自己。"
        ),
        WithdrawalTriggerEvent(
            id = "elderly_deep_withdrawal",
            ageGroup = AgePressureSource.ELDERLY,
            targetMode = WithdrawalMode.DEEP_WITHDRAWAL,
            minPressureThreshold = 80,
            baseProbability = 0.05,
            description = "你已经很久没有出门了。电话响了也不想接。你坐在家里，看着墙上那些泛黄的照片——你认识和在意的人，一个接一个地走了。",
            townCommentary = "你把自己关起来——不是因为害怕死亡，是因为害怕那个不断变小的世界。每少一个人，你的世界就少了一块。当世界小到只剩你一个人的时候——你选择了不再出去。"
        )
    )
}

// ============================================
// 五、逃避后果与心理闭环
// ============================================

/**
 * 逃避行为对心理/社交/职业的后续影响
 */
data class WithdrawalConsequence(
    val mode: WithdrawalMode,
    val consecutiveDays: Int,
    /** 孤独值变化 */
    val lonelinessDelta: Int,
    /** 焦虑值变化 */
    val anxietyDelta: Int,
    /** 自我认同变化 */
    val identityDelta: Int,
    /** 迷茫值变化 */
    val nihilismDelta: Int,
    /** 社交意愿变化 */
    val socialWillDelta: Int,
    /** 人际关系衰减概率 */
    val relationshipDecayProbability: Double,
    /** 职业机会损失描述 */
    val careerImpact: String,
    /** 是否触发恶性循环 */
    val triggersViciousCycle: Boolean
)

object WithdrawalConsequences {

    /** 不同逃避模式每日后果 */
    fun dailyConsequence(mode: WithdrawalMode, days: Int): WithdrawalConsequence {
        return when (mode) {
            WithdrawalMode.SHORT_REST -> {
                // 短期休整：前3天焦虑下降，超过3天开始负面
                if (days <= 3) {
                    WithdrawalConsequence(
                        mode, days,
                        lonelinessDelta = 0, anxietyDelta = -3, identityDelta = 0,
                        nihilismDelta = 0, socialWillDelta = 0,
                        relationshipDecayProbability = 0.0,
                        careerImpact = "无影响", triggersViciousCycle = false
                    )
                } else {
                    WithdrawalConsequence(
                        mode, days,
                        lonelinessDelta = 1, anxietyDelta = 0, identityDelta = -1,
                        nihilismDelta = 1, socialWillDelta = -1,
                        relationshipDecayProbability = 0.0,
                        careerImpact = "轻微迟到风险", triggersViciousCycle = false
                    )
                }
            }
            WithdrawalMode.PHASED_AVOIDANCE -> {
                WithdrawalConsequence(
                    mode, days,
                    lonelinessDelta = if (days > 7) 2 else 1,
                    anxietyDelta = if (days > 14) 2 else 0,
                    identityDelta = -1,
                    nihilismDelta = 1,
                    socialWillDelta = -2,
                    relationshipDecayProbability = if (days > 14) 0.15 else 0.05,
                    careerImpact = if (days > 14) "工作积压增多，职场失误概率上升" else "暂无明显影响",
                    triggersViciousCycle = days > 14
                )
            }
            WithdrawalMode.DEEP_WITHDRAWAL -> {
                WithdrawalConsequence(
                    mode, days,
                    lonelinessDelta = 3,
                    anxietyDelta = 2,
                    identityDelta = -3,
                    nihilismDelta = 3,
                    socialWillDelta = -5,
                    relationshipDecayProbability = if (days > 30) 0.30 else 0.10,
                    careerImpact = if (days > 30) "创业、公职、科研类合作型职业可选机会大幅减少" else "工作进度严重滞后",
                    triggersViciousCycle = true
                )
            }
            WithdrawalMode.ACTIVE_RESISTANCE -> {
                WithdrawalConsequence(
                    mode, days,
                    lonelinessDelta = 0, anxietyDelta = 1, identityDelta = 1,
                    nihilismDelta = -1, socialWillDelta = 0,
                    relationshipDecayProbability = 0.0,
                    careerImpact = "无影响", triggersViciousCycle = false
                )
            }
        }
    }

    /** 恶性循环触发：逃避→更怕社交→更逃避 */
    fun viciousCycleEffect(mode: WithdrawalMode): WithdrawalConsequence {
        return WithdrawalConsequence(
            mode, 0,
            lonelinessDelta = 5,
            anxietyDelta = 5,
            identityDelta = -3,
            nihilismDelta = 3,
            socialWillDelta = -8,
            relationshipDecayProbability = 0.20,
            careerImpact = "人际事件触发紧张焦虑，后续社交选项概率大幅降低",
            triggersViciousCycle = true
        )
    }
}

// ============================================
// 六、玩家主动干预选项
// ============================================

/**
 * 玩家面对逃避倾向时的可选行动
 */
data class WithdrawalPlayerChoice(
    val id: String,
    val label: String,
    val description: String,
    val resultMode: WithdrawalMode,
    /** 是否打断当前逃避状态 */
    val breaksCurrentWithdrawal: Boolean
)

object WithdrawalPlayerChoices {

    val choices: List<WithdrawalPlayerChoice> = listOf(
        WithdrawalPlayerChoice(
            id = "continue_withdraw",
            label = "继续独处一段时间",
            description = "你暂时不想面对任何事。你选择继续独处——给自己多一点时间。",
            resultMode = WithdrawalMode.PHASED_AVOIDANCE,
            breaksCurrentWithdrawal = false
        ),
        WithdrawalPlayerChoice(
            id = "break_avoidance",
            label = "主动联系一个人",
            description = "你拿起手机，翻到一个很久没联系的人。你犹豫了一下——然后按下了拨号键。",
            resultMode = WithdrawalMode.ACTIVE_RESISTANCE,
            breaksCurrentWithdrawal = true
        ),
        WithdrawalPlayerChoice(
            id = "small_step_back",
            label = "先处理一件小事",
            description = "你不打算立刻回到所有人面前。但你决定先处理一件最不让你头疼的事——哪怕只是回一条消息。",
            resultMode = WithdrawalMode.SHORT_REST,
            breaksCurrentWithdrawal = false
        ),
        WithdrawalPlayerChoice(
            id = "accept_solitude",
            label = "接受独处，找到自己的节奏",
            description = "你不再把独处当作逃避。你开始给自己安排一个不需要太多社交的日常生活——你找到了属于自己的安静。",
            resultMode = WithdrawalMode.DEEP_WITHDRAWAL,
            breaksCurrentWithdrawal = false
        )
    )
}