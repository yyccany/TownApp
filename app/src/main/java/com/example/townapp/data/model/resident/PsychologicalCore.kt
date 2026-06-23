package com.example.townapp.data

/**
 * 心理内核体系（v2.8 内层底层根基）
 *
 * 核心设计理念：
 * 1. 心理系统是小镇全部外部体系的"内层基础层"——所有外部事件最终都会投射到心理层面
 * 2. 适配现代原子化社会特征：独居增多、邻里疏远、缺少倾诉对象、负面情绪独自消化
 * 3. 心理→行为 反向联动：心理状态改变人物行为选择、处事倾向
 * 4. 形成闭环："外部环境 → 心理状态 → 个人抉择 → 反向改变外部环境"
 * 5. 精神幸福与世俗成功同等价值——平凡内心平和的普通人和功成名就者人格平等
 *
 * 七维核心参数（均为0-100动态浮动）：
 *   happiness      幸福感    — 综合愉悦度
 *   loneliness     孤独值    — 原子化社会基线天然偏高
 *   anxiety         焦虑值    — 生存压力、未来不确定性
 *   beliefResilience 信念韧性 — 决定挫折后理想崩塌速度
 *   identityDelta    自我认同  — 不依赖外部成就的自我价值感
 *   nihilism        虚无迷茫  — 中年高频触发，看透局限后的迷茫
 *   empathy          共情能力  — 决定革新者解锁门槛
 */

// ============================================
// 一、心理核状态
// ============================================

data class PsychologicalCore(
    // ---- 七维核心 ----
    val happiness: Int = 65,
    val loneliness: Int = 35,          // 原子化社会基线：30-40（熟人社会约15-25）
    val anxiety: Int = 25,
    val beliefResilience: Int = 60,     // 受日常消磨持续损耗
    val selfIdentity: Int = 55,         // 青年正值建立期，中年分化
    val nihilism: Int = 10,             // 青年低，中年起上升
    val empathy: Int = 50,              // 底层出身基线偏高，精英圈层长期封闭则偏低

    // ---- 综合指标 ----
    /** 心理能量（综合活力） */
    val mentalEnergy: Int = 70,
    /** 社交意愿（受孤独/焦虑反向影响） */
    val socialWill: Int = 55,
    /** 长期规划意愿（迷茫高则降低） */
    val longTermPlanning: Int = 60,

    // ---- 追踪 ----
    val consecutiveLonelyDays: Int = 0,
    val consecutiveAnxiousDays: Int = 0,
    val daysSinceDeepConnection: Int = 5,
    val atomizedSocietyBaseline: Boolean = true   // 原子化社会标签（影响孤独基线）
) {
    /** 心理综合健康度 */
    val overallHealth: Int
        get() {
            val score = (happiness * 0.20) +
                    ((100 - loneliness) * 0.15) +
                    ((100 - anxiety) * 0.20) +
                    (beliefResilience * 0.10) +
                    (selfIdentity * 0.15) +
                    ((100 - nihilism) * 0.10) +
                    (empathy * 0.10)
            return score.toInt().coerceIn(0, 100)
        }

    /** 心理等级 */
    val healthLevel: MentalHealthLevel
        get() = when {
            overallHealth >= 75 -> MentalHealthLevel.FLOURISHING
            overallHealth >= 55 -> MentalHealthLevel.STABLE
            overallHealth >= 35 -> MentalHealthLevel.STRUGGLING
            overallHealth >= 20 -> MentalHealthLevel.WORN_DOWN
            else -> MentalHealthLevel.BREAKDOWN
        }

    /** 是否处于深度孤独（孤独>70且连续>7天） */
    val isDeeplyLonely: Boolean
        get() = loneliness > 70 && consecutiveLonelyDays >= 7

    /** 是否存在意义危机（迷茫>60且自我认同<30） */
    val hasMeaningCrisis: Boolean
        get() = nihilism > 60 && selfIdentity < 30

    /** 是否具备革新者心理门槛 */
    val meetsReformerPsychThreshold: Boolean
        get() = empathy >= 60 && beliefResilience >= 65 && nihilism in 20..60

    /** 是否处于晚年精神和解状态 */
    val isInLateLifePeace: Boolean
        get() = selfIdentity >= 60 && nihilism <= 30 && anxiety <= 35
}

enum class MentalHealthLevel(val label: String, val description: String) {
    FLOURISHING("蓬勃", "心中自有一片安宁——不是没有烦恼，是不被烦恼裹挟。"),
    STABLE("平稳", "日子不算好也不算坏。偶尔低落，但很快能回到轨道上。"),
    STRUGGLING("挣扎", "你在努力维持。每一件小事都在消耗你，但你还在撑着。"),
    WORN_DOWN("耗竭", "你累了。不是身体的累——是心里的那根弦已经绷了太久。"),
    BREAKDOWN("崩塌", "你撑不住了。你需要停下来——停下来不是失败，是你欠自己的一次喘息。")
}

// ============================================
// 二、外部→心理 双向联动规则
// ============================================

/**
 * 外部事件对心理参数的影响规则
 */
data class ExternalPsychImpact(
    val source: String,              // 来源层级
    val happinessDelta: Int = 0,
    val lonelinessDelta: Int = 0,
    val anxietyDelta: Int = 0,
    val resilienceDelta: Int = 0,    // 信念韧性变化
    val identityDelta: Int = 0,      // 自我认同变化
    val nihilismDelta: Int = 0,      // 虚无迷茫变化
    val empathyDelta: Int = 0        // 共情能力变化
)

/**
 * 六层外部体系 → 心理参数的映射规则库
 */
object ExternalPsychMappings {

    // ========== 第1层：日常细碎波折 ==========
    val DAILY_FRUSTRATION_MILD = ExternalPsychImpact(
        source = "日常琐事-轻度",
        happinessDelta = -2,
        anxietyDelta = 2,
        resilienceDelta = -1
    )
    val DAILY_FRUSTRATION_SEVERE = ExternalPsychImpact(
        source = "日常琐事-重度",
        happinessDelta = -5,
        anxietyDelta = 5,
        resilienceDelta = -2
    )
    val DAILY_FRUSTRATION_CONSECUTIVE = ExternalPsychImpact(
        source = "日常琐事-连续多日",
        happinessDelta = -3,
        lonelinessDelta = 2,
        anxietyDelta = 4,
        resilienceDelta = -3
    )

    // ========== 第2层：个人物质 ==========
    val FINANCIAL_GAIN = ExternalPsychImpact(
        source = "收入增加",
        happinessDelta = 3,
        anxietyDelta = -5,
        identityDelta = 2
    )
    val FINANCIAL_LOSS = ExternalPsychImpact(
        source = "支出/负债",
        happinessDelta = -5,
        anxietyDelta = 8,
        identityDelta = -3
    )
    val FINANCIAL_STABLE = ExternalPsychImpact(
        source = "收支稳定",
        happinessDelta = 1,
        anxietyDelta = -1
    )

    // ========== 第3层：人际圈层 ==========
    val MARRIAGE_HARMONY = ExternalPsychImpact(
        source = "婚姻和睦",
        happinessDelta = 4,
        lonelinessDelta = -5
    )
    val MARRIAGE_CONFLICT = ExternalPsychImpact(
        source = "婚姻矛盾",
        happinessDelta = -6,
        lonelinessDelta = 5,
        anxietyDelta = 4
    )
    val FRIENDSHIP_LOSS = ExternalPsychImpact(
        source = "朋友疏远/断联",
        happinessDelta = -4,
        lonelinessDelta = 8
    )
    val CROSS_CLASS_ENCOUNTER_POSITIVE = ExternalPsychImpact(
        source = "跨圈层接触-正面",
        empathyDelta = 3,
        identityDelta = 1,
        nihilismDelta = -2
    )
    val CROSS_CLASS_ENCOUNTER_NEGATIVE = ExternalPsychImpact(
        source = "跨圈层接触-僵持",
        anxietyDelta = 3,
        identityDelta = -2
    )
    val GENERATIONAL_COMFORT = ExternalPsychImpact(
        source = "代际经验安慰",
        nihilismDelta = 3,      // "等长大就好了"→产生落差
        identityDelta = -2
    )
    val NEIGHBOR_DISTANT = ExternalPsychImpact(
        source = "邻里疏远",
        lonelinessDelta = 2     // 原子化社会特征
    )
    val NEIGHBOR_WARMTH = ExternalPsychImpact(
        source = "邻里帮衬",
        happinessDelta = 2,
        lonelinessDelta = -3
    )

    // ========== 第4层：地域环境 ==========
    val CITY_MIGRATION = ExternalPsychImpact(
        source = "城市迁徙",
        lonelinessDelta = 5,    // 离开熟人圈子
        anxietyDelta = 3
    )
    val CITY_PRICE_RISE = ExternalPsychImpact(
        source = "城市物价上涨",
        anxietyDelta = 5
    )

    // ========== 第5层：短期时代浪潮 ==========
    val MASS_UNEMPLOYMENT = ExternalPsychImpact(
        source = "大规模失业",
        happinessDelta = -8,
        anxietyDelta = 12,
        identityDelta = -5,
        nihilismDelta = 5
    )
    val INDUSTRY_DECLINE = ExternalPsychImpact(
        source = "行业衰败",
        anxietyDelta = 8,
        nihilismDelta = 4,
        resilienceDelta = -3
    )

    // ========== 第6层：长期时代变革 ==========
    val INFLATION_PERSISTENT = ExternalPsychImpact(
        source = "持续通胀",
        anxietyDelta = 6,
        identityDelta = -2
    )
    val REFORM_SUCCESS = ExternalPsychImpact(
        source = "革新者惠民政策",
        happinessDelta = 8,
        anxietyDelta = -10,
        identityDelta = 5,
        nihilismDelta = -5
    )

    // ========== 理想落空 ==========
    val IDEAL_FAILURE = ExternalPsychImpact(
        source = "理想落空",
        happinessDelta = -7,
        resilienceDelta = -5,
        identityDelta = -6,
        nihilismDelta = 6
    )
    val IDEAL_ACHIEVED = ExternalPsychImpact(
        source = "理想达成",
        happinessDelta = 10,
        resilienceDelta = 3,
        identityDelta = 8,
        nihilismDelta = -4
    )
}

// ============================================
// 三、心理→行为 反向联动规则
// ============================================

/**
 * 心理状态对行为选择的约束
 */
data class PsychBehaviorConstraint(
    val condition: String,
    /** 被锁定的行为选项 */
    val lockedActions: List<String> = emptyList(),
    /** 被解锁的行为选项 */
    val unlockedActions: List<String> = emptyList(),
    /** 概率加成描述 */
    val probabilityBonus: String = ""
)

object PsychBehaviorConstraints {

    /** 深度孤独时的行为约束 */
    val DEEP_LONELINESS_CONSTRAINT = PsychBehaviorConstraint(
        condition = "孤独>70且连续7天以上",
        lockedActions = listOf("主动社交", "婚姻追求", "创业合伙"),
        unlockedActions = listOf("独自隐居", "独处读书", "线上闲逛"),
        probabilityBonus = "独居躺平类选项概率+30%，社交类选项概率-50%"
    )

    /** 长期焦虑时的权力倾向 */
    val HIGH_ANXIETY_POWER_TENDENCY = PsychBehaviorConstraint(
        condition = "焦虑>65且底层出身",
        lockedActions = emptyList(),
        unlockedActions = listOf("严苛管控", "收紧规则"),
        probabilityBonus = "强硬管控模式概率+40%"
    )

    /** 虚无迷茫时的理想放弃 */
    val NIHILISM_GIVE_UP = PsychBehaviorConstraint(
        condition = "迷茫>55",
        lockedActions = listOf("坚持长线研发", "推进改革", "坚守理想"),
        unlockedActions = listOf("放弃科研", "躺平度日", "接受平凡"),
        probabilityBonus = "放弃理想类选项概率+50%"
    )

    /** 晚年精神状态决定叙事走向 */
    val LATE_LIFE_PEACE_NARRATIVE = PsychBehaviorConstraint(
        condition = "自我认同>60且迷茫<30",
        lockedActions = listOf("不甘一生平凡", "懊悔未竟事业"),
        unlockedActions = listOf("享受三餐烟火", "与自身平庸和解", "安享晚年"),
        probabilityBonus = "平和接纳类晚年叙事概率+60%"
    )

    val LATE_LIFE_UNREST_NARRATIVE = PsychBehaviorConstraint(
        condition = "迷茫>50且自我认同<35",
        lockedActions = listOf("安享晚年"),
        unlockedActions = listOf("整理毕生经历", "留下思想手记", "反思一生得失"),
        probabilityBonus = "反思挣扎类晚年叙事概率+60%"
    )
}

// ============================================
// 四、原子化社会独有随机心理事件
// ============================================

data class AtomizedPsychEvent(
    val id: String,
    val title: String,
    val description: String,
    val impact: ExternalPsychImpact,
    /** 触发场景 */
    val triggerContext: String,
    /** 触发概率（每日） */
    val dailyProbability: Double,
    /** 小镇评述 */
    val townCommentary: String
)

object AtomizedPsychEvents {

    val events: List<AtomizedPsychEvent> = listOf(

        AtomizedPsychEvent(
            id = "weekend_emptiness",
            title = "周末的空虚感",
            description = "周末独自在家刷了一下午手机，放下手机的那一刻，你突然觉得一阵莫名的空虚。不是因为发生了什么——恰恰是因为什么都没发生。",
            impact = ExternalPsychImpact(
                source = "原子化-周末空虚",
                happinessDelta = -2,
                nihilismDelta = 3,
                lonelinessDelta = 2
            ),
            triggerContext = "周末白天（工作日不触发）",
            dailyProbability = 0.15,
            townCommentary = "一个人不一定会孤独。但如果连一个人独处的周末都觉得是虚度——那可能不是周末的问题，是你需要的东西，不止是休息。"
        ),

        AtomizedPsychEvent(
            id = "no_one_to_talk",
            title = "无人诉说的委屈",
            description = "今天发生了一件让你很难受的事。你想找个人说说——翻了翻通讯录，发现不知道该找谁。",
            impact = ExternalPsychImpact(
                source = "原子化-无人倾诉",
                happinessDelta = -3,
                lonelinessDelta = 6,
                anxietyDelta = 3
            ),
            triggerContext = "工作日傍晚/遭遇日常挫折后",
            dailyProbability = 0.08,
            townCommentary = "通讯录里几百个名字，找不到一个可以说真话的人。这不是你的问题——这是这个时代的通病。但你的委屈是真实的，值得被听见。"
        ),

        AtomizedPsychEvent(
            id = "social_comparison",
            title = "刷到别人的生活",
            description = "你刷到了老同学的动态——新房子、新车、精致的生活。你关掉屏幕，看了看自己桌上的外卖盒，心里堵了一下。",
            impact = ExternalPsychImpact(
                source = "原子化-同辈比较",
                happinessDelta = -4,
                identityDelta = -5,
                anxietyDelta = 4
            ),
            triggerContext = "任意时段",
            dailyProbability = 0.10,
            townCommentary = "你看到的是别人精心剪辑的片段，你生活的却是自己完整的纪录。你不需要和任何人的高光时刻比——你的生活不需要滤镜。"
        ),

        AtomizedPsychEvent(
            id = "hobby_neglect",
            title = "提不起兴趣的爱好",
            description = "你以前很喜欢拍照/练字/钓鱼——但现在下班回到家，你只想瘫着。那些曾经让你开心的事，现在做起来好像也没有那么开心了。",
            impact = ExternalPsychImpact(
                source = "原子化-爱好荒废",
                happinessDelta = -2,
                resilienceDelta = -3,
                nihilismDelta = 2
            ),
            triggerContext = "工作日晚上（高疲劳时概率加倍）",
            dailyProbability = 0.06,
            townCommentary = "爱好是你和自己之间最真诚的对话方式。当你说'没精力'的时候——也许不是你失去了对爱好的兴趣，是你失去了对自己的关照。"
        ),

        AtomizedPsychEvent(
            id = "neighbor_superficial",
            title = "碰面只是客套",
            description = "在楼道里遇见了隔壁的邻居。你们都笑了笑，说了句'改天来坐'——但你俩都知道，'改天'永远不会来。",
            impact = ExternalPsychImpact(
                source = "原子化-邻里客套",
                lonelinessDelta = 2
            ),
            triggerContext = "早晚通勤时段",
            dailyProbability = 0.12,
            townCommentary = "以前一栋楼的人像一家人，现在一栋楼的人像陌生人。不是你们不想走近——是你们都太忙，太累，太习惯了这种不远不近的距离。"
        ),

        AtomizedPsychEvent(
            id = "online_social_void",
            title = "线上热闹，线下孤独",
            description = "群聊里热火朝天，朋友圈一片祥和。但放下手机，屋子里只有你自己。线上的热闹反而让现实的安静更刺耳了。",
            impact = ExternalPsychImpact(
                source = "原子化-线上社交空洞",
                happinessDelta = -2,
                lonelinessDelta = 3,
                nihilismDelta = 2
            ),
            triggerContext = "晚上",
            dailyProbability = 0.09,
            townCommentary = "线上的每一个赞都不是拥抱，每一条评论都不是对话——但它们已经成了我们仅剩的社交形式。这不是你的错，是时代把人与人之间的距离，拉成了屏幕的厚度。"
        ),

        AtomizedPsychEvent(
            id = "work_exhaustion_no_energy",
            title = "下班后的空白",
            description = "又加班到很晚。你回到家，瘫在床上。你本来打算今天看两页书、做十分钟拉伸——但现在你连洗澡的力气都没有。",
            impact = ExternalPsychImpact(
                source = "原子化-工作耗尽",
                happinessDelta = -3,
                resilienceDelta = -2,
                anxietyDelta = 3
            ),
            triggerContext = "工作日深夜（高疲劳概率加倍）",
            dailyProbability = 0.07,
            townCommentary = "你不是懒——你是被榨干了。一个连洗澡都算额外任务的生活，不是你的失败，是系统的失败。但今天，先睡吧。明天的事，明天再说。"
        ),

        AtomizedPsychEvent(
            id = "midlife_meaning_crisis",
            title = "半生回望",
            description = "你回顾了一下这些年的生活——上班、下班、吃饭、睡觉、重复。你不是不努力——你就是看不出来这一切的意义是什么。",
            impact = ExternalPsychImpact(
                source = "原子化-中年意义危机",
                happinessDelta = -5,
                nihilismDelta = 8,
                identityDelta = -4
            ),
            triggerContext = "中年阶段（40-55岁），周末/深夜",
            dailyProbability = 0.04,
            townCommentary = "意义不是找到的——它是在你活着的过程中，一点一点地长出来的。你现在看不到——不代表它不存在。它可能在你还不知道的地方，悄悄等着你。"
        )
    )
}

// ============================================
// 五、晚年精神叙事分支
// ============================================

enum class LateLifeNarrative(val id: String, val label: String, val description: String) {
    /** 与平庸和解 */
    PEACE_WITH_ORDINARY(
        "peace",
        "与平庸和解",
        "你老了。你没做过惊天动地的事，但你好好地活过了每一天。你学会了接受自己的平凡——不是因为认命，是因为你发现平凡的日常，本身就是最难熬的考验。你通过了。"
    ),
    /** 不甘平凡，留下手记 */
    UNREST_HANDWRITTEN(
        "unrest",
        "留下思想手记",
        "你老了，但你心里还有一团火。你没能在有生之年改变世界——但你可以把这一生的思考、挣扎、领悟写下来。不是为了证明什么——是为了告诉后来的人：有人走过这条路。"
    ),
    /** 精神超越 */
    SPIRITUAL_BEYOND(
        "beyond",
        "精神超越",
        "你老了。你不再纠结于年轻时候的那些执念——成功、认可、证明自己。你发现真正的自由不是拥有更多，是不再需要向任何人证明任何事。你与自己达成了和解。"
    )
}

object LateLifeNarratives {
    /** 根据心理状态匹配晚年叙事 */
    fun determineNarrative(psych: PsychologicalCore): LateLifeNarrative {
        return when {
            psych.selfIdentity >= 60 && psych.nihilism <= 30 ->
                LateLifeNarrative.PEACE_WITH_ORDINARY
            psych.nihilism >= 50 && psych.selfIdentity < 35 ->
                LateLifeNarrative.UNREST_HANDWRITTEN
            psych.beliefResilience >= 50 && psych.overallHealth >= 55 ->
                LateLifeNarrative.SPIRITUAL_BEYOND
            else ->
                LateLifeNarrative.PEACE_WITH_ORDINARY  // 默认：与平庸和解
        }
    }
}

// ============================================
// 六、心理自愈规则（自然恢复）
// ============================================

/**
 * 心理参数的每日自然恢复规则
 */
object PsychNaturalRecovery {

    /** 每日自然变化（无外部事件时） */
    data class DailyDrift(
        val paramName: String,
        val minDrift: Int,       // 每日最小变化
        val maxDrift: Int,       // 每日最大变化
        val condition: String    // 条件说明
    )

    val dailyDrifts: List<DailyDrift> = listOf(
        // 孤独：原子化社会基线较高，自然恢复缓慢
        DailyDrift("loneliness", -2, 0, "有社交互动加速恢复，独居时每日+1"),
        // 焦虑：有稳定收入时可自然回落
        DailyDrift("anxiety", -1, 1, "财务紧张时+1，收支稳定时-1"),
        // 信念韧性：坚持热爱的日子小幅回升
        DailyDrift("beliefResilience", -1, 1, "有目标时+1，摆烂时-1"),
        // 自我认同：完成阶段性目标时+2，平淡日子无变化
        DailyDrift("selfIdentity", -1, 1, "对比他人优渥生活时-1"),
        // 虚无迷茫：阅读/思考时-1，重复机械劳动时+1
        DailyDrift("nihilism", -1, 1, "无外部刺激时缓慢回归中线"),
        // 共情：长期脱离大众-1，体察民生+1
        DailyDrift("empathy", -1, 0, "封闭圈层自然下降"),
        // 幸福感：综合其余参数结算
        DailyDrift("happiness", -2, 2, "由其余6维平均值偏移决定")
    )
}