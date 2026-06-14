package com.example.townapp.data

/**
 * 外貌颜值先天参数体系（v2.11 个人微观先天基础参数）
 *
 * 核心设计理念：
 * 1. 归入个人微观先天基础参数，和原生家境、先天身体素质、初始智商、童年环境并列
 * 2. 开局随机生成，富裕家庭高颜值概率略高，出众/亮眼在所有出身中均为低概率
 * 3. 颜值只调整事件触发概率，不存在强制剧情，玩家始终拥有最终选择权
 * 4. 外貌红利集中在青年前期/初次社交/入职起步，中年后逐年衰减
 * 5. 各档位各有得失：亮眼者享受社交红利也承受流言骚扰，普通者缺少便利但更少被标签化
 *
 * 联动闭环：
 *   先天颜值 → 调整社交/婚恋/求职概率 → 心理参数变化 → 反向影响后续行为选择
 *   时代环境放大或稀释颜值权重（短视频时代=放大，通胀失业=稀释）
 */

// ============================================
// 一、颜值五档分级
// ============================================

/**
 * 外貌颜值等级（五档先天初始参数）
 */
enum class AppearanceLevel(
    val label: String,
    val tier: Int,
    val description: String,
    val socialModifier: Double,    // 初次社交印象修正系数
    val loveModifier: Double,      // 恋爱开启概率修正系数
    val careerModifier: Double,    // 服务业/销售/新媒体岗位面试修正系数
    val selfIdentityBase: Int,     // 自我认同初始加成
    val anxietyBase: Int,          // 焦虑初始加成（外貌相关焦虑）
    val harassmentRisk: Double     // 骚扰/流言风险概率
) {
    /** 极差：初次社交易遇冷淡偏见，短期孤独值更容易上涨 */
    VERY_POOR("极差", 1, "外貌条件偏差，初次社交容易遭遇冷淡和偏见",
        socialModifier = -0.15, loveModifier = -0.25, careerModifier = -0.20,
        selfIdentityBase = -10, anxietyBase = 10, harassmentRisk = 0.0),
    /** 普通：心理基线最为平稳，情绪波动来自家境、职场、时代 */
    NORMAL("普通", 2, "平平无奇的外表，不引人注目，也不让人排斥",
        socialModifier = 0.0, loveModifier = 0.0, careerModifier = 0.0,
        selfIdentityBase = 0, anxietyBase = 0, harassmentRisk = 0.0),
    /** 尚可：略高于平均，会有零星好感，但不至于引起过多关注 */
    DECENT("尚可", 3, "长相端正顺眼，偶尔会收获一些额外的好感",
        socialModifier = 0.05, loveModifier = 0.10, careerModifier = 0.08,
        selfIdentityBase = 3, anxietyBase = 0, harassmentRisk = 0.02),
    /** 出众：明显高于常人，更容易收获社交红利和职业机会 */
    OUTSTANDING("出众", 4, "外貌明显优于常人，社交场合自带好感加成，容易获得更多机会",
        socialModifier = 0.10, loveModifier = 0.20, careerModifier = 0.15,
        selfIdentityBase = 5, anxietyBase = -2, harassmentRisk = 0.05),
    /** 亮眼：极少数人，颜值红利最高，但也面临更多负面衍生风险 */
    STRIKING("亮眼", 5, "人群中最吸引目光的存在。社交红利丰厚——但随之而来的目光，不全是善意的",
        socialModifier = 0.15, loveModifier = 0.30, careerModifier = 0.22,
        selfIdentityBase = 8, anxietyBase = 5, harassmentRisk = 0.12)
}

// ============================================
// 二、开局概率分配（按家境加权）
// ============================================

/**
 * 颜值开局概率分配规则
 *
 * 富裕家庭：营养、穿搭、护肤条件更好 → 高颜值概率略高
 * 底层出身：大多集中在普通档位，出众/亮眼仍为低概率
 */
object AppearanceInitializer {

    /**
     * 不同家境下的颜值概率分布
     * 返回 (AppearanceLevel, 累计概率上限) 的列表
     */
    private val distributionByBackground: Map<ClassBackground, List<Pair<AppearanceLevel, Double>>> = mapOf(
        ClassBackground.UNDERPRIVILEGED to listOf(
            AppearanceLevel.VERY_POOR to 0.05,
            AppearanceLevel.NORMAL to 0.75,
            AppearanceLevel.DECENT to 0.95,
            AppearanceLevel.OUTSTANDING to 0.99,
            AppearanceLevel.STRIKING to 1.00
        ),
        ClassBackground.AFFLUENT to listOf(
            AppearanceLevel.VERY_POOR to 0.02,
            AppearanceLevel.NORMAL to 0.57,
            AppearanceLevel.DECENT to 0.87,
            AppearanceLevel.OUTSTANDING to 0.97,
            AppearanceLevel.STRIKING to 1.00
        )
    )

    /**
     * 根据家境随机生成颜值等级
     */
    fun roll(background: ClassBackground): AppearanceLevel {
        val dist = distributionByBackground[background] ?: distributionByBackground[ClassBackground.UNDERPRIVILEGED]!!
        val roll = Math.random()
        for ((level, threshold) in dist) {
            if (roll < threshold) return level
        }
        return AppearanceLevel.NORMAL
    }

    /** 获取各档位概率分布说明 */
    fun describeDistribution(background: ClassBackground): String {
        val dist = distributionByBackground[background] ?: return ""
        return dist.joinToString(" · ") { pair ->
            val idx = dist.indexOf(pair)
            val prev = if (idx == 0) 0.0 else dist[idx - 1].second
            val prob = (pair.second - prev) * 100
            "${pair.first.label} ${prob.toInt()}%"
        }
    }
}

// ============================================
// 三、外貌运行时状态
// ============================================

/**
 * 外貌系统运行时状态
 */
data class AppearanceState(
    /** 先天颜值等级 */
    val level: AppearanceLevel = AppearanceLevel.NORMAL,
    /** 当前年龄 */
    val currentAge: Int = 25,
    /** 是否已因年龄增长降低了颜值红利 */
    val agePenaltyApplied: Boolean = false,
    /** 累计遭遇的骚扰/流言次数 */
    val harassmentCount: Int = 0,
    /** 累计遭遇的外貌偏见次数 */
    val prejudiceCount: Int = 0,
    /** 是否经历了容貌焦虑（高颜值担心红利消退） */
    val hasAppearanceAnxiety: Boolean = false,
    /** 是否因外貌自卑触发过心理事件 */
    val hasAppearanceInferiority: Boolean = false,
    /** 是否选择了"深耕能力，无视外表"路径 */
    val hasChosenSubstanceOverLooks: Boolean = false
) {
    /**
     * 获取当前年龄下的有效社交修正系数
     * 35岁后逐年衰减，50岁后衰减至原值的20%
     */
    fun effectiveSocialModifier(): Double {
        if (currentAge < 35) return level.socialModifier
        val decayYears = currentAge - 35
        val decayFactor = (1.0 - decayYears * 0.05).coerceAtLeast(0.2)
        return level.socialModifier * decayFactor
    }

    /**
     * 获取当前年龄下的有效恋爱修正系数
     */
    fun effectiveLoveModifier(): Double {
        if (currentAge < 35) return level.loveModifier
        val decayYears = currentAge - 35
        val decayFactor = (1.0 - decayYears * 0.06).coerceAtLeast(0.15)
        return level.loveModifier * decayFactor
    }

    /**
     * 获取当前年龄下的有效职业修正系数
     */
    fun effectiveCareerModifier(): Double {
        if (currentAge < 30) return level.careerModifier
        val decayYears = currentAge - 30
        val decayFactor = (1.0 - decayYears * 0.08).coerceAtLeast(0.1)
        return level.careerModifier * decayFactor
    }

    /** 是否面临容貌焦虑 */
    val isAppearanceAnxietyRisk: Boolean
        get() = level.tier >= 4 && currentAge >= 30 && !hasAppearanceAnxiety

    /** 是否属于外貌弱势群体（极差/普通偏低） */
    val isAppearanceDisadvantaged: Boolean
        get() = level.tier <= 2
}

// ============================================
// 四、外貌相关随机心理事件
// ============================================

/**
 * 外貌相关心理事件
 */
data class AppearancePsychEvent(
    val id: String,
    val title: String,
    val description: String,
    /** 触发该事件需要的最低颜值等级（tier >=） */
    val minTier: Int,
    /** 触发该事件需要的最高颜值等级（tier <=，0=不限） */
    val maxTier: Int,
    /** 触发年龄下限 */
    val minAge: Int,
    /** 触发概率 */
    val probability: Double,
    /** 心理影响 */
    val lonelinessDelta: Int = 0,
    val anxietyDelta: Int = 0,
    val identityDelta: Int = 0,
    val resilienceDelta: Int = 0,
    /** 小镇评述 */
    val townCommentary: String
)

object AppearancePsychEvents {

    val events: List<AppearancePsychEvent> = listOf(

        // ==== 高颜值侧（出众/亮眼）====

        AppearancePsychEvent(
            id = "striking_harassment",
            title = "多余的目光",
            description = "今天又有人用那种眼神看着你——不是欣赏，是一种让你不舒服的打量。你习惯性地假装没看到——但你心里知道，这不是第一次了。",
            minTier = 4, maxTier = 5, minAge = 18,
            probability = 0.08,
            anxietyDelta = 5, identityDelta = -3,
            townCommentary = "好看是一把双刃剑——它给你带来了关注，但并不是所有的关注都是你想要的。那些让你不舒服的目光——不是你的错。"
        ),
        AppearancePsychEvent(
            id = "striking_rumor",
            title = "背后的流言",
            description = "你无意中听到了一些关于你的话——不是好话。别人在背后议论你，猜测你是怎么得到那些机会的。不是因为你的能力——是因为你的脸。",
            minTier = 4, maxTier = 5, minAge = 20,
            probability = 0.06,
            anxietyDelta = 4, identityDelta = -5, resilienceDelta = -2,
            townCommentary = "当别人用'靠脸'来概括你的努力时——他们否定的是你整个人。你不需要向他们证明什么——但你需要知道：你的能力是你自己的，不是别人嘴里的一句标签。"
        ),
        AppearancePsychEvent(
            id = "striking_aging_anxiety",
            title = "镜子里的变化",
            description = "你站在镜子前面，发现眼角多了一条细纹——你不知道它什么时候来的。你盯着那条纹看了很久——然后你想：别的东西也会跟着来吗？那些因为你好看而对你好的目光——会不会也慢慢不见了？",
            minTier = 4, maxTier = 5, minAge = 30,
            probability = 0.05,
            anxietyDelta = 6, identityDelta = -4, resilienceDelta = -1,
            townCommentary = "容貌焦虑的本质不是怕变老——是怕自己除了好看之外，什么都没有。当你开始担心这个的时候——也许该问问自己：除了这张脸，你还拥有什么？答案可能比你以为的多。"
        ),
        AppearancePsychEvent(
            id = "striking_underestimated",
            title = "被低估的能力",
            description = "你提出了一个很好的想法。但对方只是笑了笑——那种笑，像是在说'你不需要懂这个'。你明明是用脑子做出来的成果——但别人只看见了你的脸。",
            minTier = 4, maxTier = 5, minAge = 22,
            probability = 0.07,
            anxietyDelta = 3, identityDelta = -4, resilienceDelta = -2,
            townCommentary = "被低估的感觉很闷——因为你明明不只是好看。但你要知道：那些因为你的脸而低估你的人，不是你该去说服的人。你的能力不需要他们的认可才生效。"
        ),

        // ==== 低颜值侧（极差/普通偏低）====

        AppearancePsychEvent(
            id = "poor_social_coldness",
            title = "空调的沉默",
            description = "你走进一个房间——没有人抬头看你。没有人对你笑。你坐在角落里，感觉自己是透明的。你告诉自己不在乎——但你的手有点凉。",
            minTier = 1, maxTier = 2, minAge = 18,
            probability = 0.08,
            lonelinessDelta = 5, identityDelta = -3, anxietyDelta = 3,
            townCommentary = "第一次见面的时候，人们只能看到你的外表——这是他们的局限，不是你的。你能被看见的东西——需要时间。但那些值得被看见的东西，从来不会因为等得久一点就消失。"
        ),
        AppearancePsychEvent(
            id = "poor_appearance_inferiority",
            title = "镜子里的自卑",
            description = "你又在镜子面前站了一会儿。你看着自己的脸——然后想：如果长得不一样，是不是很多事情都会不一样？你关掉灯，不去看镜子了。但那个问题还在你心里。",
            minTier = 1, maxTier = 2, minAge = 18,
            probability = 0.06,
            anxietyDelta = 5, identityDelta = -6, resilienceDelta = -3,
            townCommentary = "你对着镜子看到的不是你——是这个世界对你的评价。但这个世界不是镜子——它看不见你的善良、你的幽默、你半夜帮朋友搬家时流的那身汗。你值得被看见——而你一定会被看见，在那些不只是看脸的事情上。"
        ),
        AppearancePsychEvent(
            id = "poor_love_insecurity",
            title = "不敢开始的恋爱",
            description = "你喜欢一个人——但你不敢说出来。你总觉得——对方不会喜欢长成你这样的人。你把这个念头压在心里，然后假装你只是不够喜欢他/她。",
            minTier = 1, maxTier = 2, minAge = 20,
            probability = 0.06,
            lonelinessDelta = 4, identityDelta = -5, anxietyDelta = 4,
            townCommentary = "你不敢开始不是因为对方不喜欢你——是因为你还没学会喜欢自己。但你必须知道：你值得被爱——不是因为你的脸，是因为你是一个完整的人。"
        ),
        AppearancePsychEvent(
            id = "poor_interview_fear",
            title = "面试前的镜子",
            description = "你明天要去面试了。你站在镜子前，反复检查自己的衣服、发型、表情——你总觉得哪里不对。你开始担心：面试官看到我的第一眼，会不会就已经把我否了？",
            minTier = 1, maxTier = 2, minAge = 22,
            probability = 0.05,
            anxietyDelta = 6, identityDelta = -3, resilienceDelta = -1,
            townCommentary = "面试官第一眼看到的是你的脸——但第二眼看到的是你的简历，第三眼是你的回答。你只需要撑过第一眼——后面的事，脸帮不了你，也拦不住你。"
        ),

        // ==== 通用（所有档位）====

        AppearancePsychEvent(
            id = "appearance_midlife_fade",
            title = "不再被注意的年纪",
            description = "你走在街上，发现回头看你的人越来越少了。你年轻的时候习惯了被注意——现在那种注意消失了。你有点失落——但又觉得松了一口气。",
            minTier = 3, maxTier = 5, minAge = 40,
            probability = 0.04,
            anxietyDelta = 3, identityDelta = 2, resilienceDelta = 2,
            townCommentary = "外貌红利的消失不是惩罚——是解放。当别人不再因为你的长相而关注你的时候——你终于可以确定：剩下的那些关注，都是冲着你这个人来的。"
        ),
        AppearancePsychEvent(
            id = "appearance_substance_choice",
            title = "选择深耕",
            description = "你不再纠结自己长得好不好了。你开始把时间花在那些不需要脸的事情上——看书、学技术、做项目。你发现——当你的能力足够强的时候，没有人再在意你的脸。",
            minTier = 1, maxTier = 3, minAge = 25,
            probability = 0.04,
            anxietyDelta = -5, identityDelta = 8, resilienceDelta = 6,
            townCommentary = "你选择了深耕能力而不是在意外表——这不是认命，是你在用实际行动告诉世界：我的价值不在这张脸上。这是最有力的一种选择。"
        )
    )

    /** 筛选匹配当前外貌和年龄的心理事件 */
    fun matching(level: AppearanceLevel, age: Int, triggeredIds: List<String>): List<AppearancePsychEvent> {
        return events.filter {
            level.tier >= it.minTier &&
            (it.maxTier == 0 || level.tier <= it.maxTier) &&
            age >= it.minAge &&
            it.id !in triggeredIds
        }
    }
}

// ============================================
// 五、外貌对职业的差异化影响
// ============================================

/**
 * 外貌对各类职业的具体影响
 */
object AppearanceCareerImpact {

    /**
     * 判断外貌是否对指定职业类型有面试加成
     */
    fun getInterviewBonus(level: AppearanceLevel, careerType: String): Double {
        return when (careerType) {
            "service", "sales", "admin", "new_media", "retail" ->
                level.careerModifier  // 服务业/销售/行政/新媒体：外貌加成
            "research", "tech", "engineering" ->
                0.0  // 科研/技术/工程：不受外貌影响
            "civil_service", "reformer" ->
                level.careerModifier * 0.3  // 公职/革新者：仅小幅加成
            "labor", "construction", "delivery" ->
                0.0  // 底层体力岗位：不受影响
            else -> level.careerModifier * 0.5
        }
    }

    /** 外貌对薪资的初始影响（仅入职时） */
    fun getSalaryBonus(level: AppearanceLevel, careerType: String): Double {
        return when (careerType) {
            "service", "sales", "new_media" -> level.careerModifier * 0.5
            "admin", "retail" -> level.careerModifier * 0.3
            else -> 0.0
        }
    }
}

// ============================================
// 六、时代环境对外貌权重的调节
// ============================================

/**
 * 时代环境对外貌权重的放大/稀释
 */
object AppearanceEraModifier {

    /**
     * 计算当前时代的外貌权重修正系数
     *
     * @param year 当前年份
     * @param unemploymentRate 失业率（0-1）
     * @param inflationRate 通胀率
     * @return 外貌修正系数（>1=放大，<1=稀释）
     */
    fun getEraMultiplier(
        year: Int,
        unemploymentRate: Double,
        inflationRate: Double
    ): Double {
        var multiplier = 1.0

        // 短视频/新媒体时代（2018后）：放大外貌权重
        if (year >= 2018) {
            multiplier += 0.15
        }

        // 通胀 > 5%：生存压力稀释外貌红利
        if (inflationRate > 0.05) {
            multiplier -= 0.10
        }

        // 大规模失业（>10%）：外貌权重进一步稀释
        if (unemploymentRate > 0.10) {
            multiplier -= 0.15
        }

        return multiplier.coerceIn(0.5, 1.30)
    }
}