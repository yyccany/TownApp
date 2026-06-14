package com.example.townapp.data

/**
 * 日常细碎挫折事件体系（v2.4 微观常态化负面随机层）
 *
 * 核心设计理念：
 * 1. 生活的主体是无数微小得失、临时麻烦、细碎煎熬，不仅仅是重大人生节点
 * 2. 细碎苦难高频、随机、单独影响小，但日积月累消耗疲劳值、情绪、耐心
 * 3. 阶层差距能改变大事层面的资源，但无法隔绝日常随机苦难——众生平等
 * 4. 玩家自由选择应对方式：消极内耗 / 调整补救 / 放空休息
 * 5. 这是对原有世界观的补充，不是颠覆——在现有五层架构上新增"日常细碎波动层"
 *
 * 整体六层架构：
 *   1F 日常细碎波动层 ← 本次新增
 *   2F 微观个人层（先天、消费、健康、财务）
 *   3F 人际圈层层（婚恋、邻里、跨阶层认知）
 *   4F 地域环境层（城乡、物价、风俗）
 *   5F 短期时代浪潮层（赛事、就业、文娱）
 *   6F 长期时代变革层（通胀、行业兴衰、资本垄断）
 */

// ============================================
// 一、挫折类型枚举
// ============================================

/**
 * 日常细碎挫折类型
 */
enum class DailyFrustrationCategory(val label: String, val description: String) {
    /** 财物与行程失误：丢钥匙、忘带手机、采购遗漏、物品损坏 */
    PROPERTY_TIME("财物与行程失误", "出门忘带东西、物品损坏、往返取件——打乱当日计划，浪费通勤时间。"),
    /** 人际瞬时冲突：路人呵斥、同事刻薄、邻里口角、网购争执 */
    INTERPERSONAL("人际瞬时冲突", "无深层矛盾，事情很快结束——但短时间内心郁闷烦躁，当天情绪下降。"),
    /** 工作规划失误：工序返工、数据填错、实验步骤出错 */
    WORK_ERROR("工作规划失误", "干活顺序出错、需要加班修正——损耗工时，催生自我否定与平庸焦虑。"),
    /** 身心健康慢性折磨：失眠、食欲不振、腰酸、焦虑、磕碰擦伤 */
    HEALTH("身心健康波动", "失眠、食欲不振、磕碰擦伤——长期堆积会降低休闲投入意愿，改变生活节奏。"),
    /** 环境突发琐事：大雨没带伞、停水停电、沙尘打乱出行 */
    ENVIRONMENTAL("环境突发琐事", "突发天气变化、停水停电——居家生活节奏被打乱，计划被迫调整。")
}

// ============================================
// 二、玩家应对选择
// ============================================

/**
 * 玩家面对细碎挫折的三种应对方式
 */
enum class FrustrationCoping(val label: String, val description: String) {
    /** 消极内耗：自责、懊恼、陷在负面情绪里 */
    RUMINATE("消极内耗", "陷入自责和懊恼，反复回想失误——短期情绪进一步下滑，消耗额外精力。"),
    /** 调整补救：立刻行动，修正失误，优化流程 */
    ACTIVE_FIX("调整补救", "立刻调整规划、补救失误、优化后续流程——短期额外付出精力，长期养成高效习惯。"),
    /** 放空休息：暂缓工作，给自己喘息的空间 */
    REST("放空休息", "暂缓手头事务，给自己一段空白时间——短期牺牲工作效率，长期保护心理韧性。")
}

// ============================================
// 三、挫折事件数据模型
// ============================================

/**
 * 日常细碎挫折事件
 *
 * 五段式结构：体感 → 闪光点 → 个人工时 → 时代成本 → 小镇评述
 */
data class DailyFrustration(
    val id: String,
    val category: DailyFrustrationCategory,
    /** 事件标题 */
    val title: String,
    /** 事件描述（何时触发） */
    val triggerHint: String,

    // ---- 五段式文案 ----
    val bodyFeeling: String,
    val sparkle: String,
    val personalCost: String,
    val eraCost: String,
    val townCommentary: String,

    /** 适用的职业类型列表（空=全职业通用） */
    val applicableCareers: List<String>,
    /** 触发权重（相对概率，1-10，越高越容易触发） */
    val triggerWeight: Int,
    /** 疲劳值波动 */
    val fatigueChange: Int,
    /** 情绪值波动 */
    val moodChange: Int,
    /** 是否有工时损耗（小时） */
    val hoursWasted: Double,

    /** 三种应对方式及其效果 */
    val choices: List<FrustrationChoice>
)

/**
 * 细碎挫折应对选项及效果
 */
data class FrustrationChoice(
    val coping: FrustrationCoping,
    val label: String,
    val responseText: String,
    /** 额外疲劳变化 */
    val extraFatigue: Int,
    /** 额外情绪变化 */
    val extraMood: Int,
    /** 长期性格倾向（积极/消极/中性） */
    val characterTendency: String
)

// ============================================
// 四、预置细碎挫折事件库
// ============================================

object DailyFrustrations {

    // ========================
    // A. 财物与行程失误类
    // ========================

    val FRUSTRATION_FORGOT_KEYS = DailyFrustration(
        id = "forgot_keys",
        category = DailyFrustrationCategory.PROPERTY_TIME,
        title = "出门忘带钥匙",
        triggerHint = "清晨匆忙出门，关上门的一瞬间发现钥匙还在屋里。",
        bodyFeeling = "急匆匆折返回家，一路狂奔。赶到公司已经迟到，面对考勤记录，内心慌乱压抑。一上午心神不安，总觉得自己这一天从开头就毁了。",
        sparkle = "当天仓促的变故之后，后续出门你慢慢养成了提前清点随身物品的习惯。在失误之中，你悄悄调整了生活细节——下次出门前，你会下意识摸一下口袋。",
        personalCost = "往返多消耗45分钟通勤工时，占用个人晨间休息时间，当日疲劳值小幅上升。",
        eraCost = "属于个体随机性日常失误，不改变宏观行业、物价参数，仅改变个人当日状态。长期大量个体频繁的时间损耗，会细微拉高城市通勤整体隐性时间成本。",
        townCommentary = "宏大的时代浪潮距离普通人很远——更多生活的疲惫，藏在这些不起眼的失误之中。谁都难免粗心犯错，不管出身贫富，细碎的麻烦都会随机降临在每个人身上。平庸日常里的磕磕绊绊，本就是生活常态。",
        applicableCareers = emptyList(),
        triggerWeight = 6,
        fatigueChange = 8,
        moodChange = -10,
        hoursWasted = 0.75,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "一路懊恼，反复自责", "你一路都在骂自己粗心。到了公司也静不下心，反复回想早上的失误。这一整天都罩着一层阴云。", 5, -8, "自我苛责倾向"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "吸取教训，把钥匙放在固定位置", "你深呼吸了一下。今天的事已经发生了，但明天不会再犯同样的错。你把备用钥匙放在了门口的固定挂钩上。", -2, 0, "从失误中优化习惯"),
            FrustrationChoice(FrustrationCoping.REST, "迟到就迟到，先买杯咖啡缓一缓", "你没有急着狂奔——已经迟到了，再急也没用。你在楼下买了杯咖啡，慢慢走过去。心情平静了一些。", -3, 3, "接受不完美的从容")
        )
    )

    val FRUSTRATION_LOST_ITEM = DailyFrustration(
        id = "lost_item",
        category = DailyFrustrationCategory.PROPERTY_TIME,
        title = "采购遗漏必需品",
        triggerHint = "从超市回来，提着大包小包，突然发现最重要的那件东西忘了买。",
        bodyFeeling = "你站在家门口，看着购物袋里的东西，唯独少了你专程去买的那个。心里一阵烦躁——又得跑一趟。本来可以用来休息的时间，就这样被浪费了。",
        sparkle = "这次之后，你开始在出门前列一个简单的购物清单。不是什么大改变——但确实再也没有漏买过重要东西。",
        personalCost = "二次往返消耗约30分钟休息时间，影响当日休闲爱好投入意愿。",
        eraCost = "单次事件无宏观影响。日常生活中此类失误的累积，持续压缩个人的休息与自我提升时间。",
        townCommentary = "生活中最令人烦躁的，往往不是大灾大难，而是这些不断重复的小麻烦。它们不会击垮你——但会一层一层地磨掉你的耐心。每个人都有过这种时刻。",
        applicableCareers = emptyList(),
        triggerWeight = 5,
        fatigueChange = 5,
        moodChange = -7,
        hoursWasted = 0.5,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "烦躁不已，一边骂自己一边回去买", "你气冲冲地又跑了一趟。一路上心情糟透了——这件小事毁了你半个下午的心情。", 8, -10, "易被琐事激怒"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "写个清单，以后出门前看一眼", "你拿出手机，把这次漏买的东西记下来。以后出门采购前列清单——花30秒省30分钟。", 0, 2, "系统性优化习惯"),
            FrustrationChoice(FrustrationCoping.REST, "算了，明天再说吧", "你把东西放下，决定今天不去了。缺一样东西不会怎么样——你自己舒服比较重要。", -3, 0, "学会取舍与放下")
        )
    )

    val FRUSTRATION_ITEM_DAMAGED = DailyFrustration(
        id = "item_damaged",
        category = DailyFrustrationCategory.PROPERTY_TIME,
        title = "物品不慎摔坏",
        triggerHint = "手一滑，杯子摔在地上碎了。或者手机从桌上滑落，屏幕裂了一道。",
        bodyFeeling = "你看着地上的碎片，愣了一下。不是什么值钱的大东西——但就是突然觉得很难过。好像这一天所有的累积疲惫，都在这一刻被这个碎掉的杯子点燃了。",
        sparkle = "你在处理这件小事的过程中发现——东西坏了可以再买，但你对自己情绪的觉察变敏锐了。你不是在为杯子难过，你是在为最近所有的疲惫找一个出口。",
        personalCost = "产生小额维修或购置成本，换算为约1小时工时对应的收入损耗。",
        eraCost = "属于个人随机损耗，无宏观影响。",
        townCommentary = "物品损坏是小，但它常常成为压垮情绪的最后一根稻草。你其实不是心疼那个杯子——是它替你说出了你一直没说出口的疲惫。这种时刻，每个人都会有。",
        applicableCareers = emptyList(),
        triggerWeight = 4,
        fatigueChange = 3,
        moodChange = -8,
        hoursWasted = 0.2,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "心疼半天，越想越气", "你收拾碎片的时候越想越难受。觉得自己最近什么都不顺——连个杯子都跟你作对。", 2, -10, "负面情绪累积"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "清理碎片，下单买个新的", "你平静地清扫了碎片，掏出手机下单了一个新的。生活继续——杯子碎了，但你没有。", 0, -2, "务实应对损失"),
            FrustrationChoice(FrustrationCoping.REST, "不管了，先坐下喝口水", "你没有马上收拾。你坐在沙发上，喝了口水，看着地上的碎片放空了一会儿。此刻你需要的不是解决问题——是喘口气。", -3, 5, "在混乱中寻找喘息")
        )
    )

    // ========================
    // B. 人际瞬时冲突类
    // ========================

    val FRUSTRATION_STRANGER_RUDE = DailyFrustration(
        id = "stranger_rude",
        category = DailyFrustrationCategory.INTERPERSONAL,
        title = "路上被陌生人无端呵斥",
        triggerHint = "你正常走在路上，一个陌生人突然急躁地冲你喊了一句——也许是你挡了他的路，也许他心情不好。",
        bodyFeeling = "你愣在原地——明明自己没有任何错。对方已经走远了，你却站在原地，一整天心里闷闷不快。事情隔天便结束了，糟糕情绪却滞留在心头许久。",
        sparkle = "经历过突如其来的冲突，你后续面对陌生人争执时心态更为淡然。你明白了——有些人发火和你无关，他们只是在把自己的烦躁丢给刚好路过的人。",
        personalCost = "无直接工时损耗，心理情绪参数短期降低。",
        eraCost = "纯粹个体随机人际事件，无宏观影响。",
        townCommentary = "我们习惯把苦难等同于灾祸。临时的言语冒犯微不足道，却足够破坏一整天的好心情。人与人之间随机产生的摩擦，构成了日常细碎的困境——不分贫富阶层，无人可以彻底避开。",
        applicableCareers = emptyList(),
        triggerWeight = 7,
        fatigueChange = 2,
        moodChange = -12,
        hoursWasted = 0.0,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "耿耿于怀，反复回想对方的话", "你一整天都在回想那个人的表情和语气。越想越气——凭什么？你甚至在想，下次见到他要怎么怼回去。", 3, -12, "易被他人评价困扰"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "深呼吸，告诉自己「不是我的问题」", "你深吸一口气。那个人发火和你无关——他只是在倾倒自己的情绪。你不是他的垃圾桶。", -1, 5, "情绪边界清晰"),
            FrustrationChoice(FrustrationCoping.REST, "找个安静的地方，听首歌缓一缓", "你戴上耳机，找了一首喜欢的歌。你不是在逃避——你是在保护自己不被别人的情绪绑架。", -2, 8, "主动保护内心安宁")
        )
    )

    val FRUSTRATION_COLLEAGUE_SNARK = DailyFrustration(
        id = "colleague_snark",
        category = DailyFrustrationCategory.INTERPERSONAL,
        title = "同事随口刻薄评价",
        triggerHint = "你正在认真做自己的事，旁边的同事随口说了一句——也许是关于你的工作效率，也许是你不小心出的一个小错。",
        bodyFeeling = "那句话很轻，轻到对方说完就忘了。但你听进去了，一整天都在反复琢磨——他是认真的吗？他是不是一直看不起我？越想越觉得自己是不是真的不够好。",
        sparkle = "冷静下来后你意识到——对方随口的一句话，反映的是他当时的情绪状态，不是对你全面的评价。你不需要用别人的一句话来定义自己。",
        personalCost = "无直接工时损耗，但情绪内耗降低了专注力，间接影响工作效率。",
        eraCost = "个体职场摩擦事件，无宏观影响。",
        townCommentary = "一句随口的话，说的人没在意，听的人记了一整天。职场的疲惫不只有加班——还有这些看不见的情绪劳动。精英科研人员和流水线工人都会遇到——不是针对你，是人类相处里的日常摩擦。",
        applicableCareers = emptyList(),
        triggerWeight = 5,
        fatigueChange = 2,
        moodChange = -10,
        hoursWasted = 0.3,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "越想越在意，反复琢磨对方的意思", "你开始在脑海里回放那个场景——他当时的语气、表情、周围的反应。你越想越觉得这人有问题。", 5, -12, "对他人评价过度敏感"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "如果是自己的问题就改，不是就不管", "你客观地想了一下：他说的是事实吗？如果是，你改。如果不是，这话跟你没关系。", 0, 3, "区分事实与情绪"),
            FrustrationChoice(FrustrationCoping.REST, "不理他，出去透口气", "你起身去了走廊，望着窗外发了一会儿呆。回来的时候心里平静多了。有些话不值得你花情绪去消化。", -2, 5, "选择性忽略外界噪音")
        )
    )

    // ========================
    // C. 工作规划失误类
    // ========================

    val FRUSTRATION_WORK_REDO = DailyFrustration(
        id = "work_redo",
        category = DailyFrustrationCategory.WORK_ERROR,
        title = "工序规划不当，重复返工",
        triggerHint = "忙活了半天，突然发现工序顺序出了错——前面做的大半工作需要全部重来。",
        bodyFeeling = "你看着眼前需要返工的一大片，疲惫之余生出深深的挫败感。你怀疑自己是不是太粗心了、是不是能力不够。为什么别人不犯错就你犯错？",
        sparkle = "经历返工失误后，你开始养成提前梳理步骤的习惯。下一次开工前，你会先花几分钟在脑子里过一遍流程——这几分钟，省掉了一整天的返工。",
        personalCost = "半天劳动工时产出作废，当日劳动收益缩水。",
        eraCost = "单一个体失误影响有限。大量从业者接连出现规划失误，会小幅拖慢工厂或团队单日产能。",
        townCommentary = "重大的失败容易被人记住，日复一日反复返工带来的自我怀疑，往往容易被忽略。生存压力加上日常失误的挫败感，一点点磨平人的精气神——这是普通人无声的煎熬。",
        applicableCareers = emptyList(),
        triggerWeight = 5,
        fatigueChange = 12,
        moodChange = -15,
        hoursWasted = 4.0,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "陷入自我怀疑，觉得自己太没用", "你蹲在旁边，脑子里全是「为什么是我」「为什么每次都出错」。这一天你都在这种自我否定里挣扎。", 8, -15, "自我否定倾向加重"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "梳理流程，重新规划再动手", "你深吸一口气，拿出一张纸，把正确的步骤从头写了一遍。这次你比上一次做得好——因为你知道了坑在哪里。", 5, 5, "从错误中系统学习"),
            FrustrationChoice(FrustrationCoping.REST, "先停下来，喝杯水歇一歇再继续", "你没有马上返工。你把工具放下，喝了杯水，在车间外面站了一会儿。不是拖延——是你知道，带着挫败感干活只会错更多。", 2, 8, "在挫折中保护情绪")
        )
    )

    val FRUSTRATION_DATA_ERROR = DailyFrustration(
        id = "data_error",
        category = DailyFrustrationCategory.WORK_ERROR,
        title = "表格数据填错，需要加班修正",
        triggerHint = "已经快下班了，突然发现今天填的表格里有一个关键数据出了错——整张表的结果都要重新核对。",
        bodyFeeling = "你盯着那个错误的数据，脑子嗡嗡的。本来已经准备关电脑了——现在至少还要加班一个多小时。你恨自己为什么不多看一眼。",
        sparkle = "这次之后，你养成了在提交前做一次快速自查的习惯。只花两分钟，但再也没有出现过这种低级错误。",
        personalCost = "加班修正耗时约1.5小时，占用了晚间休息或休闲时间。",
        eraCost = "个体随机失误，无宏观影响。",
        townCommentary = "一份表格、一个数据——这些看起来不起眼的工作细节，构成了许多人每日焦虑的来源。精英和普通人的区别不在于会不会出错——而在于出错了之后，有没有人帮你兜底。",
        applicableCareers = emptyList(),
        triggerWeight = 5,
        fatigueChange = 8,
        moodChange = -10,
        hoursWasted = 1.5,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "一边改一边骂自己笨", "你忍着烦躁打开表格重做。每改一格都在心里骂自己一句。改完的时候你整个人都瘫了。", 5, -12, "自我攻击式应对"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "静下心重新核对，加一条自检流程", "你重新打开表格，逐格核对——这次你加了一列自动校验。以后不会再出现同样的错了。", 2, 3, "化失误为流程改进"),
            FrustrationChoice(FrustrationCoping.REST, "先回家，明天早点来改", "你关掉了电脑。今天的状态不适合改数据——越改越容易再出错。你决定明天提前一个小时来。", -5, 5, "合理分配精力")
        )
    )

    // ========================
    // D. 身心健康慢性折磨类
    // ========================

    val FRUSTRATION_INSOMNIA = DailyFrustration(
        id = "insomnia",
        category = DailyFrustrationCategory.HEALTH,
        title = "深夜辗转难眠",
        triggerHint = "已经凌晨两点了，你躺在床上，头脑却异常清醒。白天明明很累，但就是睡不着。",
        bodyFeeling = "深夜清醒毫无睡意，你盯着天花板，听着窗外偶尔经过的车声。白天昏沉乏力，工作时注意力涣散。傍晚原本打算去散步、拍照、做点喜欢的事——现在完全提不起兴致。",
        sparkle = "失眠让你意识到身心状态失衡了。你开始尝试调整作息，缩减夜间刷手机的时长，给自己一段不受屏幕打扰的睡前时间。",
        personalCost = "睡眠不足降低白天工作效率，同等工时实际产出下降约20%。",
        eraCost = "个人短期状态波动。长期失眠人群增多，城市病假、短期离岗概率小幅上涨。",
        townCommentary = "失业、行业变革是时代宏大困境——失眠、疲惫、心绪低落是个人独有的细碎苦难。时代的苦难是浪潮，细碎烦恼则是持续流淌的细水，长久浸润着人的一生。每一个辗转难眠的深夜，都是一次无法被宏大叙事安慰的个体煎熬。",
        applicableCareers = emptyList(),
        triggerWeight = 4,
        fatigueChange = 10,
        moodChange = -8,
        hoursWasted = 2.0,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "越睡不着越焦虑，翻来覆去刷手机", "你越睡不着越烦躁，索性拿起手机刷——但是越刷越清醒。等你终于困了的时候，天快亮了。", 8, -10, "恶性循环式应对"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "起床喝杯温水，做几个深呼吸", "你没有强迫自己睡。你起身喝了杯温水，站在窗边做了几个深呼吸。再躺下的时候，你告诉自己——睡不着也没关系，休息也是一种休息。", -3, 5, "接纳式调整"),
            FrustrationChoice(FrustrationCoping.REST, "索性不睡了，起来看会儿书", "你打开台灯，拿起一本一直想看的书。既然睡不着，就用这段安静的时间做点别的事。明天困就困吧——至少今晚你没有在焦虑中度过。", -2, 3, "顺势而为的豁达")
        )
    )

    val FRUSTRATION_LOW_APPETITE = DailyFrustration(
        id = "low_appetite",
        category = DailyFrustrationCategory.HEALTH,
        title = "一整天毫无胃口",
        triggerHint = "午饭时间到了，你看着眼前的食物，没有任何想吃的欲望。最近都是这样——吃饭变成了一件需要说服自己的事。",
        bodyFeeling = "你强迫自己吃了几口，味同嚼蜡。不是因为饭菜不好——是你整个人处于一种低电量模式。吃不进东西，体力恢复变慢，下午干活明显比平时慢。",
        sparkle = "胃口是身体在对你说话。它在告诉你——你最近压力太大了，需要慢下来。你没有忽略这个信号。",
        personalCost = "进食不足导致体力恢复慢，下午工作效率下降约15%。",
        eraCost = "个体状态波动，无宏观影响。",
        townCommentary = "吃不下饭——这件小事说出来，别人会觉得「至于吗」。但这是你的身体在用最诚实的方式告诉你：你最近过得太紧绷了。倾听身体的声音，停下勉强自己的习惯。",
        applicableCareers = emptyList(),
        triggerWeight = 3,
        fatigueChange = 8,
        moodChange = -6,
        hoursWasted = 1.0,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "硬塞几口，心里更难受", "你勉强自己吃了几口——每一口都像完成任务。吃完之后你觉得自己连吃饭都搞不定。", 3, -8, "勉强式应对"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "少吃多餐，先喝点热汤", "你没有强迫自己吃完。你喝了碗热汤——胃舒服了一点。你决定今天分几顿慢慢吃。", -2, 5, "温和调整节奏"),
            FrustrationChoice(FrustrationCoping.REST, "不逼自己，下午饿了再吃", "你把饭收起来了。现在不想吃就不吃——你不是在亏待自己，你是在尊重身体的节奏。", -3, 3, "尊重身体信号")
        )
    )

    val FRUSTRATION_MINOR_INJURY = DailyFrustration(
        id = "minor_injury",
        category = DailyFrustrationCategory.HEALTH,
        title = "磕碰擦伤",
        triggerHint = "走路时不小心撞到了桌角，或者在搬东西时手指被夹了一下。不严重，但隐隐作痛。",
        bodyFeeling = "伤口不严重——但疼是确实疼的。你忍着疼继续做事，动作比平时慢了一点。心里有点委屈：都已经这么努力了，为什么还要受这些皮肉之苦。",
        sparkle = "这点小伤提醒了你——你的身体不是一台永动机。你需要对自己好一点。",
        personalCost = "轻微行动受限，同等工时产出小幅降低。",
        eraCost = "纯个体事件，无宏观影响。户外体力劳动者此类事件频率更高。",
        townCommentary = "不是大伤，不值得看医生，也不值得请假——但疼是真的。这些不被「当成一回事」的小伤痛，恰恰是最消磨人的。你在疼的时候还在坚持干活——这份韧劲，值得被看见。",
        applicableCareers = emptyList(),
        triggerWeight = 3,
        fatigueChange = 5,
        moodChange = -6,
        hoursWasted = 0.5,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "忍着疼继续干，心里委屈", "你没有停下来——不能因为这点小伤就耽误进度。但心里的委屈在慢慢堆积。", 5, -8, "忽略身体信号"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "简单处理一下伤口，继续干活", "你找了张创可贴，简单处理了一下。然后继续干活——这次动作小心了一点。", 2, 2, "务实处理继续前行"),
            FrustrationChoice(FrustrationCoping.REST, "歇一会儿，等不疼了再继续", "你停下手里的活，坐下来歇了一会儿。不是矫情——是你值得被自己照顾。", -3, 5, "自我关怀优先")
        )
    )

    // ========================
    // E. 环境突发琐事类
    // ========================

    val FRUSTRATION_CAUGHT_IN_RAIN = DailyFrustration(
        id = "caught_in_rain",
        category = DailyFrustrationCategory.ENVIRONMENTAL,
        title = "突遇大雨，没带伞",
        triggerHint = "出门的时候还是晴天，走到半路突然下起了大雨。你没有任何雨具。",
        bodyFeeling = "你在路边的屋檐下躲雨，衣服已经湿了一半。等雨停的时间里，你看着手机上的时间一分一秒过去——等不了了，你冲进雨里，狼狈地跑完了剩下半程。",
        sparkle = "此后你养成了出门看天气预报的习惯。不是杞人忧天——是多了一份提前准备的从容。",
        personalCost = "避雨等待或淋湿赶路，消耗约30分钟机动时间。",
        eraCost = "个体随机天气遭遇，无宏观影响。",
        townCommentary = "一场突如其来的雨，让本来井井有条的计划瞬间被打乱。生活中的很多失控感，来自这些不可预判的小事——你做了所有的计划，但老天爷不跟你商量。接受这一点，也是一种成长。",
        applicableCareers = emptyList(),
        triggerWeight = 5,
        fatigueChange = 5,
        moodChange = -5,
        hoursWasted = 0.5,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "懊恼不已，埋怨自己怎么不看天气预报", "你站在屋檐下越想越气——为什么偏偏今天下雨？为什么偏偏你没带伞？你在心里把所有相关的东西都埋怨了一遍。", 3, -8, "怨天尤人倾向"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "就近买把伞，继续赶路", "你环顾四周，找到最近的便利店买了把伞。多花了点钱，但时间没有浪费太多。", 2, 2, "快速解决问题"),
            FrustrationChoice(FrustrationCoping.REST, "找个咖啡店坐下，等雨停了再说", "你没有逼自己冒雨赶路。你在路边找了家咖啡店坐下来——窗外在下雨，你在喝热咖啡。今天下午的计划确实被打乱了——但也许你本来也需要休息一下。", -3, 8, "把意外变成喘息")
        )
    )

    val FRUSTRATION_POWER_OUTAGE = DailyFrustration(
        id = "power_outage",
        category = DailyFrustrationCategory.ENVIRONMENTAL,
        title = "突发停水停电",
        triggerHint = "你正在做自己的事，灯突然灭了——或者水龙头突然不出水了。一切计划被打断。",
        bodyFeeling = "你愣了几秒才反应过来——停电了。电脑里没保存的东西没了，晚饭也做不了，连手机都快没电了。所有计划在这一瞬间被清零。你坐在黑暗里，感到一种深深的无力。",
        sparkle = "这次之后，你养成了及时保存文件的习惯——也准备了一个充电宝在抽屉里。不是要对抗所有意外——但能准备好的，就提前准备。",
        personalCost = "计划中断，损失约1-2小时有效时间。",
        eraCost = "局部市政偶发事件，无宏观影响。",
        townCommentary = "你可以在岗位上卷赢所有人，但你卷不过停电。这是一种超越个人能力范畴的失控感——承认有些事情不在掌控范围内，是成年人很重要的课题。",
        applicableCareers = emptyList(),
        triggerWeight = 3,
        fatigueChange = 3,
        moodChange = -8,
        hoursWasted = 1.5,
        choices = listOf(
            FrustrationChoice(FrustrationCoping.RUMINATE, "烦躁不已，不停看手机等来电", "你每隔五分钟就刷新一下物业群——怎么还不来电？你所有的计划都被打乱了，你什么也做不了，只能干等。", 5, -10, "失控焦虑被放大"),
            FrustrationChoice(FrustrationCoping.ACTIVE_FIX, "拿出备用充电宝，用手机处理能做的事", "你点上了蜡烛，用手机把能处理的事情处理了。剩下的——来电再说。", 1, 2, "灵活应对突发"),
            FrustrationChoice(FrustrationCoping.REST, "难得清静，躺沙发上听听音乐", "你关了所有待办事项的提醒。既然什么都做不了——那就什么都不做。你在黑暗里听了一会儿音乐——很久没有这么安静过了。", -5, 10, "把失控变成清静的礼物")
        )
    )

    // ========================
    // 全部事件列表
    // ========================

    val allFrustrations: List<DailyFrustration> = listOf(
        // 财物与行程失误
        FRUSTRATION_FORGOT_KEYS,
        FRUSTRATION_LOST_ITEM,
        FRUSTRATION_ITEM_DAMAGED,
        // 人际瞬时冲突
        FRUSTRATION_STRANGER_RUDE,
        FRUSTRATION_COLLEAGUE_SNARK,
        // 工作规划失误
        FRUSTRATION_WORK_REDO,
        FRUSTRATION_DATA_ERROR,
        // 身心健康
        FRUSTRATION_INSOMNIA,
        FRUSTRATION_LOW_APPETITE,
        FRUSTRATION_MINOR_INJURY,
        // 环境突发
        FRUSTRATION_CAUGHT_IN_RAIN,
        FRUSTRATION_POWER_OUTAGE
    )

    /** 按类别筛选事件 */
    fun byCategory(category: DailyFrustrationCategory): List<DailyFrustration> {
        return allFrustrations.filter { it.category == category }
    }

    /** 获取所有全职业通用事件 */
    fun universal(): List<DailyFrustration> {
        return allFrustrations.filter { it.applicableCareers.isEmpty() }
    }

    /** 根据ID查找事件 */
    fun findById(id: String): DailyFrustration? {
        return allFrustrations.find { it.id == id }
    }
}