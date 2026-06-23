package com.example.townapp.data

/**
 * 信念系统（v2.5 长久坚守热爱的特质）
 *
 * 核心设计理念：
 * 1. 长久坚守热爱需要三重条件同时支撑：经济缓冲、环境圈层、时代稳定
 * 2. 任意一项断裂，信念便会慢慢消解——整体人群占比极低，千里挑一
 * 3. 出身影响信念起步条件，但无法锁死最终选择——寒门仍有百里挑几的坚守者
 * 4. 失败不等于信念消亡，许多人终身坚守未获世俗成功，但本心未曾改变
 * 5. 安稳度日与坚守理想人格平等，没有优劣之分
 *
 * 信念值（0-100）：
 *   90-100：坚定不移，风雨不改
 *   70-89： 信念坚定，偶有动摇
 *   50-69： 信念尚存，但现实压力持续侵蚀
 *   30-49： 信念动摇，开始怀疑当初的选择
 *   10-29： 信念所剩无几，近乎放弃
 *   0-9：   信念破碎，彻底放弃
 */

// ============================================
// 一、信念起源类型
// ============================================

/**
 * 信念的起源/类型
 */
enum class BeliefOrigin(val label: String, val description: String) {
    /** 科研理想：追求基础科学突破、原创理论 */
    RESEARCH_IDEAL("科研理想", "追求基础科学突破，深耕原创理论——长周期、高不确定性。"),
    /** 艺术创作：文学、音乐、绘画等创作追求 */
    ARTISTIC_PASSION("艺术创作", "文学、音乐、绘画等创作追求——前期零收入，靠作品说话。"),
    /** 社会理想：教育公平、环保、公益等社会事业 */
    SOCIAL_IDEAL("社会理想", "教育公平、环境保护、公益事业——长期投入，回报间接。"),
    /** 工匠精神：手艺传承、工艺钻研 */
    CRAFTSMANSHIP("工匠精神", "手艺传承、工艺钻研——小众市场，缓慢积累。"),
    /** 创业执念：技术创业、改变行业 */
    ENTREPRENEURIAL_FAITH("创业执念", "技术创业、改变行业——高风险，高不确定性。")
}

// ============================================
// 二、信念动摇原因
// ============================================

/**
 * 信念动摇/消解的原因
 */
enum class BeliefErosionCause(val label: String, val description: String) {
    /** 经济压力：长期无收入，生存开支挤压理想空间 */
    ECONOMIC_PRESSURE("经济压力", "房租、三餐、医疗刚需挤压——坚守热爱的物质基础被生存需求蚕食。"),
    /** 圈层评价：同辈压力、世俗标准持续动摇个人选择 */
    SOCIAL_PRESSURE("圈层评价", "同龄人赚钱成家，世俗标准不断施加心理压力。"),
    /** 制度壁垒：学阀圈层、资本垄断限制理想落地路径 */
    INSTITUTIONAL_BARRIER("制度壁垒", "学阀垄断、资本规则——理想的落地路径被外部力量封锁。"),
    /** 日常消磨：细碎磨难日积月累消耗耐心 */
    DAILY_EROSION("日常消磨", "失眠、返工、无数次失败——日复一日的琐碎消耗了最初的热情。"),
    /** 时代变故：行业兴衰、通胀等外部环境改变 */
    ERA_CHANGE("时代变故", "行业没落、通胀抬头——时代浪潮的变动挫败了长期规划。"),
    /** 自我怀疑：无数次失败后开始怀疑自己是否平庸 */
    SELF_DOUBT("自我怀疑", "无数次失败后，开始怀疑自己是否原本就平庸，是否配得上曾经的理想。")
}

// ============================================
// 三、信念状态数据模型
// ============================================

/**
 * 信念系统的运行时状态
 */
data class BeliefState(
    /** 信念值（0-100） */
    val beliefValue: Int = 80,
    /** 信念起源类型 */
    val origin: BeliefOrigin? = null,
    /** 坚守年限（游戏年） */
    val yearsHeld: Int = 0,
    /** 累计失败次数 */
    val totalFailures: Int = 0,
    /** 最近一次动摇原因 */
    val lastErosionCause: BeliefErosionCause? = null,
    /** 是否仍在坚守 */
    val isStillHolding: Boolean = true,
    /** 是否曾取得世俗成功 */
    val hasAchievedSuccess: Boolean = false,
    /** 从坚守到放弃的转折点描述 */
    val turningPoint: String = ""
) {
    /** 信念等级描述 */
    val beliefLevel: String get() = when {
        beliefValue >= 90 -> "坚如磐石"
        beliefValue >= 70 -> "信念坚定"
        beliefValue >= 50 -> "勉力维持"
        beliefValue >= 30 -> "摇摇欲坠"
        beliefValue >= 10 -> "行将放弃"
        else -> "心如死灰"
    }
}

// ============================================
// 四、信念事件
// ============================================

/**
 * 信念系统里程碑事件
 */
data class BeliefMilestone(
    val id: String,
    /** 触发条件：信念值阈值 */
    val beliefThreshold: Int,
    /** 事件类型：上升/下降 */
    val isPositive: Boolean,
    /** 事件标题 */
    val title: String,
    /** 事件描述 */
    val description: String,
    /** 小镇评述 */
    val townCommentary: String
)

object BeliefMilestones {

    val milestones: List<BeliefMilestone> = listOf(

        // ---- 信念坚定阶段 ----
        BeliefMilestone(
            id = "belief_peak_90",
            beliefThreshold = 90,
            isPositive = true,
            title = "信念如磐石",
            description = "你经历了无数次失败、经济压力、旁人质疑——但你的信念反而更加坚定。你不再需要向任何人证明什么——你在做的事情，本身就是意义。",
            townCommentary = "这份坚定不是天生的，是无数次动摇之后依然选择留下的结果。你不是没有怀疑过——你只是每一次都选择了继续。"
        ),
        BeliefMilestone(
            id = "belief_steady_70",
            beliefThreshold = 70,
            isPositive = true,
            title = "初心尚存",
            description = "你经历了现实的打磨，信念有所下降，但内心深处的那团火还在。你会在深夜偶尔想起当初为什么出发——然后第二天继续。",
            townCommentary = "大多数坚守者都停留在这个位置。不高不低，不卑不亢——在理想和现实之间找到了一个勉强可以呼吸的平衡点。"
        ),

        // ---- 信念动摇阶段 ----
        BeliefMilestone(
            id = "belief_decay_50",
            beliefThreshold = 50,
            isPositive = false,
            title = "信念动摇",
            description = "你开始认真地问自己：这一切值得吗？同龄人已经买房买车，你还在为经费发愁。你开始怀疑自己当初的选择是不是太天真了。",
            townCommentary = "动摇不是失败——它是你对自己诚实的结果。你开始把理想的滤镜摘下来，看清了现实的模样。这个阶段，比任何时候都更需要被理解。"
        ),
        BeliefMilestone(
            id = "belief_decay_30",
            beliefThreshold = 30,
            isPositive = false,
            title = "理想渐远",
            description = "你越来越频繁地想起那些放弃的人——他们过得好像挺好的。你开始认真考虑：也许放弃也不是什么坏事。",
            townCommentary = "在这个阶段，你离放弃只有一步之遥。但这一步，有些人走了很多年都没跨过去——因为放不下。"
        ),

        // ---- 信念破碎阶段 ----
        BeliefMilestone(
            id = "belief_broken_10",
            beliefThreshold = 10,
            isPositive = false,
            title = "信念破碎",
            description = "你终于承认了——这条路走不通了。不是你不努力，是现实的条件不允许。你把曾经的计划书收进了抽屉最深处，关上了门。",
            townCommentary = "放弃不等于失败。你竭尽全力了——只是时代、圈层、经济条件没有站在你这一边。你值得被原谅，尤其值得被自己原谅。"
        ),

        // ---- 坚守纪念 ----
        BeliefMilestone(
            id = "years_held_5",
            beliefThreshold = 0,
            isPositive = true,
            title = "坚守五年",
            description = "你在这条路上走了五年了。回头看，你已经不是当初那个热血沸腾的年轻人——你变得更沉稳，也更清醒。你依然在走。",
            townCommentary = "五年——足够一个人完成从热情到坚持的转变。热情会褪去，但习惯和责任会留下来。"
        ),
        BeliefMilestone(
            id = "years_held_10",
            beliefThreshold = 0,
            isPositive = true,
            title = "坚守十年",
            description = "十年了。你身边的人换了一拨又一拨，放弃的人过得都不错。你还在原地——但你看到的风景，已经和他们完全不同了。",
            townCommentary = "十年的坚守，不是执念，是选择。你选择了一条少有人走的路——不是因为这条路更好，是因为这条路是你的。"
        ),
        BeliefMilestone(
            id = "years_held_20",
            beliefThreshold = 0,
            isPositive = true,
            title = "坚守二十年",
            description = "二十年。你的一生已经过半。你从未取得世俗意义上的大成功，但你从未后悔。你做的事情，在你的生命里，已经是一种完成。",
            townCommentary = "二十年——你不需要任何人的认可了。你走过的路，就是意义本身。"
        )
    )
}

// ============================================
// 五、信念消解事件（与细碎挫折联动）
// ============================================

/**
 * 信念消解事件——日常挫折对信念的累积影响
 */
data class BeliefErosionEvent(
    val cause: BeliefErosionCause,
    /** 信念值减少量 */
    val beliefDecay: Int,
    /** 触发概率（受疲劳值、焦虑值加成） */
    val baseProbability: Double,
    /** 描述文案 */
    val description: String
)

object BeliefErosionEvents {

    val events: List<BeliefErosionEvent> = listOf(
        BeliefErosionEvent(
            cause = BeliefErosionCause.ECONOMIC_PRESSURE,
            beliefDecay = 3,
            baseProbability = 0.08,
            description = "交完房租后，你的账户余额又一次见底了。你问自己：我还能这样撑多久？"
        ),
        BeliefErosionEvent(
            cause = BeliefErosionCause.SOCIAL_PRESSURE,
            beliefDecay = 2,
            baseProbability = 0.05,
            description = "朋友圈里同龄人又晒了新车。你关掉手机，看着自己桌上堆积的资料——你选择了沉默。"
        ),
        BeliefErosionEvent(
            cause = BeliefErosionCause.INSTITUTIONAL_BARRIER,
            beliefDecay = 4,
            baseProbability = 0.04,
            description = "你的论文又被退回了。不是因为写得不好——是因为你没有那个圈子里的人帮你递一句话。"
        ),
        BeliefErosionEvent(
            cause = BeliefErosionCause.DAILY_EROSION,
            beliefDecay = 1,
            baseProbability = 0.15,
            description = "今天又是一事无成——实验失败了，数据全废了。你坐在实验室里，第一次觉得这盏灯有点刺眼。"
        ),
        BeliefErosionEvent(
            cause = BeliefErosionCause.ERA_CHANGE,
            beliefDecay = 5,
            baseProbability = 0.02,
            description = "你深耕多年的行业，正在被新技术取代。你第一次感到——你对抗的不是自己不够努力，是整个时代。"
        ),
        BeliefErosionEvent(
            cause = BeliefErosionCause.SELF_DOUBT,
            beliefDecay = 3,
            baseProbability = 0.10,
            description = "深夜，你看着镜子里的自己。你问自己：我是不是本来就很平庸？那些年是不是在自欺欺人？"
        )
    )
}