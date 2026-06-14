package com.example.townapp.data.cognition

/**
 * 认知反思条目（v2.14 时代局限性认知模块）
 *
 * 与 IdiomData（传统成语）不同，CognitionReflection 是三层结构的深度反思：
 * 1. 时代局限性 —— 为什么这句俗语在旧时代是合理的
 * 2. 现代社会打破 —— 为什么在今天不再适用，分有效吃苦/无效吃苦
 * 3. 两种人生选择 —— 给玩家不同路径，不强制单一价值观
 *
 * 核心原则：
 * - 既不全盘否定俗语，承认旧时合理性，也点明当代局限性
 * - 破除「苦难必然值得经历」的固有执念
 * - 玩家可以选择传统路径咬牙扛住，也可以选择现代路径主动避开
 * - 不输出说教式结论，只呈现代价和选择
 */

// ============================================
// 核心数据模型
// ============================================
data class CognitionReflection(
    /** 唯一标识 */
    val id: String,
    /** 俗语原文 */
    val proverb: String,
    /** 触发年龄阈值（>= 此年龄才可触发） */
    val minAge: Int = 35,
    /** 触发场景 */
    val triggerScene: String,
    /** 是否已解锁（运行时状态） */
    val isUnlocked: Boolean = false,

    // 三层解析
    val layerOne: ReflectionLayer,
    val layerTwo: ReflectionLayer,
    val layerThree: ReflectionLayer,

    // 玩家选择
    val choices: List<ReflectionChoice>,

    // 小镇注解
    val townCommentary: String
)

/**
 * 反思层：一层解析
 */
data class ReflectionLayer(
    val title: String,
    val subtitle: String = "",
    /** 核心观点 */
    val coreInsight: String,
    /** 详细展开 */
    val detailText: String,
    /** 游戏内参数影响说明 */
    val gameImpact: String = ""
)

/**
 * 玩家选择
 */
data class ReflectionChoice(
    val id: String,
    val label: String,
    val description: String,
    /** 选择后的结果 */
    val result: ChoiceResult
)

/**
 * 选择结果
 */
data class ChoiceResult(
    val anxietyDelta: Int = 0,
    val identityDelta: Int = 0,
    val socialWillDelta: Int = 0,
    val followUpText: String,
    /** 是否解锁关联认知条目 */
    val unlocksCognitionId: String? = null,
    // v2.14 批量扩展：新增参数维度
    /** 人际好感变化 */
    val socialGoodwillDelta: Int = 0,
    /** 晋升/职业进度变化 */
    val careerProgressDelta: Int = 0,
    /** 孤独值变化 */
    val lonelinessDelta: Int = 0,
    /** 疲惫值变化 */
    val fatigueDelta: Int = 0,
    /** 技能点数变化 */
    val skillPointsDelta: Int = 0,
    /** 家庭亲密值变化 */
    val familyIntimacyDelta: Int = 0,
    /** 是否清除负面状态 */
    val clearNegativeStatus: Boolean = false
)

// ============================================
// 认知反思条目库
// ============================================
object CognitionReflectionLibrary {

    /**
     * 条目1：不经历风雨，怎能见彩虹
     *
     * 触发条件：
     * - 年龄 >= 35 岁（中年阶段）
     * - 场景：失业/负债/事业受挫后向长辈求助
     * - 长辈先说出这句俗语（仅降低焦虑3点，不解决现实问题）
     * - 随后解锁此反思条目，供玩家深度阅读
     */
    val RAINBOW = CognitionReflection(
        id = "rainbow_after_rain",
        proverb = "不经历风雨，怎能见彩虹",
        minAge = 35,
        triggerScene = "失业/负债/事业受挫后向长辈求助",

        // ── 第一层：时代局限性 ──
        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "农耕社会的必然产物",
            coreInsight = "这句老话属于熟人农耕社会的产物——旧时就业选择极少，普通人没有转行、线上副业、跨城市流动的出路，困境可选项匮乏。",
            detailText = "在农耕时代，饥荒、繁重劳作很难规避，人们只能被迫吃苦。于是总结出「苦难必然换取回报」的经验——这不是真理，是当时环境下最优的心理生存方式。用来消解苦难带来的迷茫焦虑。\n\n" +
                "放到小镇参数逻辑里：角色失业、负债受挫后，长辈大概率会说出这句俗语。它只能降低焦虑值、提供情绪安慰，无法直接解决失业负债的现实困境——薪资、存款数值不会自动好转。",
            gameImpact = "长辈说这句俗语时：焦虑值 -3（仅心理安慰），现实参数（薪资、存款、就业）无任何变化。"
        ),

        // ── 第二层：现代社会打破 ──
        layerTwo = ReflectionLayer(
            title = "现代社会打破固定因果",
            subtitle = "苦难 ≠ 必然换来彩虹",
            coreInsight = "风雨不是通往彩虹的必经门槛，它只是一种人生概率事件。可以主动避开不必要的苦难，不必强行经历磨难。",
            detailText = "吃苦分两种情况：\n\n" +
                "【有效吃苦】自学编程搞副业、备考提升学历、阶段性加班打拼——付出对应技能提升，长期能够跳槽涨薪。在小镇里：持续投入副业时长，技能点数稳步上涨，后续收入提升。这时磨难和回报存在正向关联，谚语依旧部分成立。\n\n" +
                "【无效吃苦】在衰退行业内卷熬夜、忍受职场PUA、无休止无意义加班——单纯消耗身体健康，只会拉高疲惫值，催生失眠、慢性病，长期薪资停滞。即便熬过数年辛劳，依旧很难改善处境。风雨结束后没有彩虹。这时老话的因果逻辑失效，苦难只是单纯损耗。\n\n" +
                "因此小镇的核心观点：你可以选择节奏平缓的人生，安稳通勤、适度休闲，平衡生活与工作，不必刻意吃苦承压。",
            gameImpact = "有效吃苦：副业技能点 + 收入增长；无效吃苦：疲惫值 + 慢性病风险，薪资停滞。"
        ),

        // ── 第三层：两种人生选择 ──
        layerThree = ReflectionLayer(
            title = "两种人生选择权",
            subtitle = "拒绝单一价值观",
            coreInsight = "人生没有标准答案——你可以咬牙扛住，也可以主动避开。两种选择都不是错的。",
            detailText = "小镇给你两条路，没有哪条更「正确」：\n\n" +
                "路径一：遵循传统思路——遭遇失业挫折，咬牙扛住压力，不断试错，历经波折后翻身，印证老话。\n\n" +
                "路径二：做出现代选择——察觉行业下行，主动转行、迁居城市，避开失业风波，不必经历失业低谷，直接收获安稳生活。\n\n" +
                "两种选择对应不同的代价和收获，没有高下之分。你不需要向任何人证明你「够不够努力」——你只需要选择适合自己的路。",
            gameImpact = ""
        ),

        // ── 玩家选择 ──
        choices = listOf(
            ReflectionChoice(
                id = "endure",
                label = "咬牙扛住",
                description = "我选择继续扛——不是因为我信这句老话，而是因为我现在没有别的选择。",
                result = ChoiceResult(
                    anxietyDelta = -2,
                    identityDelta = 2,
                    socialWillDelta = 0,
                    followUpText = "你选择了扛住。这不是因为「风雨过后一定有彩虹」——而是因为你评估了自己的处境，决定再试一次。这种选择不是因为盲目乐观，而是因为你在清醒地面对。\n\n小镇不会因为你选了这条路就给你发奖牌——但你的存款、技能、人际关系，会因为你接下来每一步的踏实行动，慢慢发生变化。"
                )
            ),
            ReflectionChoice(
                id = "avoid",
                label = "主动避开",
                description = "我不需要经历这场风雨——我可以换一条路走。",
                result = ChoiceResult(
                    anxietyDelta = -3,
                    identityDelta = 3,
                    socialWillDelta = 1,
                    followUpText = "你选择了避开。这不是懦弱——这是你在看清了「无效吃苦」和「有效吃苦」的区别之后，做出的理性判断。\n\n你不需要用一场毫无意义的风雨来证明自己。安稳地活着，本身就是一种值得尊重的人生选择。小镇不会因为你选了这条路就给你打折——你只是换了一条更适合自己的路。"
                )
            )
        ),

        // ── 小镇注解 ──
        townCommentary = "小镇不评判你选哪条路。老话有老话诞生的土壤，你有你生活的时代。风雨不是你必须经历的，彩虹也不是你应得的回报——它们只是人生中的概率事件。你选择经历，或者选择绕开，都是你自己的事。"
    )

    // ============================================
    // 分组1：青年阶段（22岁解锁 | 职场/人际）
    // ============================================

    /** 条目2：出头椽子先烂 */
    val NAIL_THAT_STICKS_OUT = CognitionReflection(
        id = "nail_that_sticks_out",
        proverb = "出头的椽子先烂",
        minAge = 22,
        triggerScene = "职场表现突出/被同事排挤",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "传统集体环境的生存智慧",
            coreInsight = "传统集体环境讲究「中庸低调」，排斥锋芒，劝人收敛个性、随大流规避是非，是旧时自保生存智慧。",
            detailText = "在农耕熟人社会里，与众不同的言行容易被盯上、被排挤。于是「出头的椽子先烂」成为代代相传的警告——不是因为它正确，而是因为在那个环境下，低调确实能减少麻烦。\n\n但放到今天：职场既有人际博弈，也需要能力展示。一味藏拙会错失晋升机会，过度张扬也易卷入矛盾。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚），实际职场参数不变。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会打破固有逻辑",
            subtitle = "隐藏 vs 展示，需要区分场合",
            coreInsight = "当下职场既有人际博弈，也需要能力展示；一味藏拙会错失晋升机会，过度张扬也易卷入矛盾。",
            detailText = "关键不是「要不要出头」，而是「在什么场合、用什么方式出头」。\n\n在需要协作的团队里，适当收敛锋芒是智慧；在需要展示能力的晋升节点，大胆表达是勇气。\n\n小镇的核心观点：不必盲从「低调保命」，学会区分场合，平衡自我表达与人际相处。",
            gameImpact = "均衡路线：焦虑-2，自我认同+2，各项参数平稳推进。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "收敛 / 平衡 / 坚持",
            coreInsight = "没有标准答案——保守、平衡、进取，各有代价。",
            detailText = "你可以收敛锋芒求安稳，也可以坚持自我搏机会，或者走中间路线——哪条路都不错，看你要什么。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("converge", "收敛锋芒，低调行事",
                "听从长辈劝告，把能力藏起来，少惹是非。",
                ChoiceResult(anxietyDelta = -1, socialGoodwillDelta = 2, careerProgressDelta = -1,
                    followUpText = "你选择了收敛。短期内人际关系确实缓和了——但你也感觉到，有些机会从身边滑过去了。这不是对错问题——是你选择了安稳，暂时放下了进取。")),
            ReflectionChoice("balance", "正常做事，不刻意张扬也不刻意退让",
                "该做的事做好，该说的话说清楚，不过度也不退缩。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 2,
                    followUpText = "你选择了平衡。不刻意张扬，也不刻意退让——这是最需要智慧的路。你发现，当你不再纠结「该不该出头」时，反而能更专注地把事情做好。")),
            ReflectionChoice("insist", "坚持自我，凭能力争取机会",
                "我有能力，为什么要藏？该争取的就要争取。",
                ChoiceResult(anxietyDelta = 1, identityDelta = 3, socialGoodwillDelta = -2, careerProgressDelta = 2,
                    followUpText = "你选择了坚持。不是不在乎别人的眼光——而是你更在乎自己能不能问心无愧。说实话，这条路不轻松，但每一步都踏得踏实。"))
        ),

        townCommentary = "小镇不告诉你该不该出头。你选择收敛，是你在保护自己；你选择坚持，是你在相信自己的价值。两种选择，都是你在为自己的人生负责。"
    )

    /** 条目3：近朱者赤，近墨者黑 */
    val NEAR_VERMILION = CognitionReflection(
        id = "near_vermilion",
        proverb = "近朱者赤，近墨者黑",
        minAge = 22,
        triggerScene = "交友不慎/被朋友拖累",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "固定社交圈的必然推论",
            coreInsight = "古代社交圈固定，圈子基本决定人生走向，因此极度强调择友标准。",
            detailText = "在人口流动极少的农耕社会，你认识的人基本就是你这辈子要打交道的人。择友不慎，影响的是一生。这句老话在那个环境下，是实实在在的生存提醒。\n\n但今天：网络时代社交多元，个人独立三观可以抵御环境影响，不良圈子确实会消耗精力，但你有更多选择权。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "圈子有影响，但选择权在自己",
            coreInsight = "环境有影响，但选择权始终在自己手中。",
            detailText = "圈子确实会影响你——身边都是积极向上的人，你会不自觉地被带动；身边都是消极抱怨的人，你也会被消耗。\n\n但你不是被动地「被染色」——你有判断力，有选择权。你可以保持距离，也可以尝试影响对方。",
            gameImpact = "疏远对方：孤独值+2，焦虑-2，负面状态清零。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "疏远 / 保持距离 / 劝导",
            coreInsight = "环境不是决定性的——你才是。",
            detailText = "你可以果断疏远，也可以保持距离继续相处，或者尝试劝导对方。每条路有不同的代价和收获。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("distance", "疏远对方，更换社交圈子",
                "果断切割，不再让这段关系消耗自己。",
                ChoiceResult(lonelinessDelta = 2, anxietyDelta = -2, clearNegativeStatus = true,
                    followUpText = "你选择了疏远。切割一段关系从来不容易——你会感到孤独，但你也发现，那些让你疲惫的消耗消失了。有时候，保护自己比维持关系更重要。")),
            ReflectionChoice("keep_wall", "保持距离，正常相处不深交",
                "不撕破脸，但也不再投入太多。",
                ChoiceResult(lonelinessDelta = 1, anxietyDelta = -1,
                    followUpText = "你选择了保持距离。不撕破脸，也不深交——这是一种微妙的平衡。你发现，当你不再对这段关系抱期待时，它反而变得轻松了。")),
            ReflectionChoice("persuade", "尝试劝导对方，继续维持友谊",
                "朋友一场，我不想就这样放弃。",
                ChoiceResult(anxietyDelta = 2, identityDelta = 2,
                    followUpText = "你选择了继续。不是因为你傻——是因为你重感情。但你也知道，劝导一个人改变，比改变自己难多了。你愿意尝试，但请给自己设一个底线。"))
        ),

        townCommentary = "小镇不评判你的选择。疏远不是冷漠，是自我保护；继续不是软弱，是重情重义。但请记住：你不是被动地被环境染色——你永远有选择权。"
    )

    // ============================================
    // 分组2：中年阶段（30岁解锁 | 事业/家庭/取舍）
    // ============================================

    /** 条目4：知足常乐 */
    val CONTENTMENT = CognitionReflection(
        id = "contentment_is_happiness",
        proverb = "知足常乐",
        minAge = 30,
        triggerScene = "收入停滞/羡慕他人生活",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "物质匮乏年代的心理安慰",
            coreInsight = "物质匮乏年代，降低欲望是缓解痛苦的主要方式，用来安抚对现状不满的情绪。",
            detailText = "过去物质极度匮乏，大多数人一辈子无法改变阶层。在这种情况下，「知足」是唯一能让自己平静下来的方式——不是因为它正确，而是因为没有别的选择。\n\n但今天：你有上升的通道，有改变的可能。知足可以获得内心安稳，但过度安于现状会停止成长。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "知足与进取不矛盾",
            coreInsight = "接纳当下，同时保留向上的动力，知足与进取并不对立。",
            detailText = "「知足」和「摆烂」是两回事。\n\n知足是：我接受我现在的生活状态，不焦虑、不攀比，但我不拒绝变好的机会。\n摆烂是：反正就这样了，我不努力了。\n\n小镇的核心观点：你可以对当下满意，同时悄悄努力——这两者不矛盾。",
            gameImpact = "均衡路线：焦虑-2，自我认同+2，各项进度正常推进。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "安于现状 / 稳步提升 / 全力拼搏",
            coreInsight = "知足是心态，进取是选择——你可以同时拥有。",
            detailText = "放下攀比不代表放弃努力。你可以选择安于现状享受平静，也可以稳步提升兼顾生活，或者全力拼搏追求事业。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("settle", "放下攀比，安于现状",
                "不再跟别人比了——我现在这样就挺好。",
                ChoiceResult(anxietyDelta = -3, identityDelta = 1, careerProgressDelta = -1, skillPointsDelta = -1,
                    followUpText = "你选择了安于现状。不是放弃——是你在告诉自己：我已经够好了。这种安心是真实的，但你也要知道，停下来的时候，世界还在往前走。")),
            ReflectionChoice("steady", "放平心态，稳步提升",
                "不攀比，但也不停步——按自己的节奏来。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 2,
                    followUpText = "你选择了稳步提升。不焦虑、不攀比，但也不停步——这是最健康的节奏。你发现，当你不再盯着别人时，自己的每一步都走得更踏实。")),
            ReflectionChoice("strive", "不甘现状，全力拼事业",
                "我不想安于现状——我想要更好的生活。",
                ChoiceResult(anxietyDelta = 2, fatigueDelta = 2, careerProgressDelta = 3, skillPointsDelta = 3,
                    followUpText = "你选择了全力拼搏。这不是贪心——是你知道自己想要什么，并且愿意为之付出。累了就歇一歇——进取不需要以健康为代价。"))
        ),

        townCommentary = "小镇不评判你选哪条路。知足是心态，进取是选择——你可以对当下说「够了」，也可以对未来说「我还要」。这两个声音不矛盾，它们都是你。"
    )

    /** 条目5：家和万事兴 */
    val FAMILY_HARMONY = CognitionReflection(
        id = "family_harmony",
        proverb = "家和万事兴",
        minAge = 30,
        triggerScene = "家庭争吵/亲子矛盾",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "传统家庭观的第一原则",
            coreInsight = "传统家庭以「整体和睦」为第一原则，主张隐忍、退让，牺牲个人感受维护家庭完整。",
            detailText = "在传统观念里，家庭的完整性高于个人的感受。于是「家和万事兴」变成了一句话堵住所有不满——你委屈是你不够大度，你计较是你不够顾全大局。\n\n但今天：和睦固然重要，但隐忍积累的矛盾会持续内耗。有效沟通远比一味退让更能维系家庭。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "和睦不是单方面妥协",
            coreInsight = "和睦不是单方面妥协，而是彼此尊重、双向包容。",
            detailText = "真正的「家和」不是一个人忍气吞声换来的表面和平——那是内耗，不是和睦。\n\n好的家庭关系是：每个人的声音都能被听到，每个人的感受都被尊重。这不代表没有矛盾——而是有矛盾时，坐下来好好说。",
            gameImpact = "理性沟通：家庭亲密值+2，自我认同+2（均衡最优）。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "隐忍 / 沟通 / 坚持",
            coreInsight = "和睦需要双向奔赴——单方面的牺牲不是爱。",
            detailText = "你可以选择隐忍维持表面和平，也可以理性沟通化解矛盾，或者坚持立场不愿妥协。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("compromise", "主动退让，隐忍求和",
                "算了，不吵了——我退一步，家里太平就好。",
                ChoiceResult(familyIntimacyDelta = 2, identityDelta = -2,
                    followUpText = "你选择了退让。家里确实安静了——但你也知道，那些没说出口的话，正在心里慢慢堆积。隐忍能换来一时的太平，但换不来真正的理解。")),
            ReflectionChoice("communicate", "理性沟通，化解矛盾",
                "我们都冷静一下，坐下来好好说——不是为了争输赢，是为了让彼此听懂。",
                ChoiceResult(familyIntimacyDelta = 2, identityDelta = 2,
                    followUpText = "你选择了沟通。这不是最容易的路——但这是最值得走的路。当你们坐下来，把话说开的那一刻，你会发现：原来对方不是不想理解你，只是之前没人愿意先开口。")),
            ReflectionChoice("stand_ground", "坚持立场，不愿妥协",
                "这次我不想再退让了——我有我的底线。",
                ChoiceResult(familyIntimacyDelta = -3, identityDelta = 3,
                    followUpText = "你选择了坚持立场。短期来看，矛盾确实加剧了——但你也第一次真正地为自己站了出来。有时候，冲突不是关系的终点，而是重新开始的契机。"))
        ),

        townCommentary = "小镇不评判你选哪条路。退让不是软弱，是你在乎这个家；坚持不是自私，是你在乎你自己。但请记住：最好的和睦，不是一个人扛下所有委屈——是两个人一起扛。"
    )

    /** 条目6：万般皆下品，惟有读书高 */
    val READING_SUPREME = CognitionReflection(
        id = "reading_is_supreme",
        proverb = "万般皆下品，惟有读书高",
        minAge = 30,
        triggerScene = "工作辛苦想放弃学习/觉得读书无用",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "阶层固化的唯一出路",
            coreInsight = "古代阶层固化，读书是普通人唯一的上升通道，因此被奉为最高出路。",
            detailText = "在科举时代，读书确实是普通人改变命运的唯一途径。这句话在那个时代是实实在在的真理——不是因为它绝对正确，而是因为别无选择。\n\n但今天：职业选择百花齐放，技能、经验、资源都能实现价值。但学习依然是提升认知的核心途径。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "读书不是唯一，但终身学习永远有价值",
            coreInsight = "读书不是唯一出路，但终身学习永远有价值。",
            detailText = "在今天的时代，「读书」的含义已经变了——它不一定是捧着书本，而是持续学习、持续成长。\n\n你可以学一门手艺，可以钻研一个领域，可以每天进步一点点。重要的不是「读书」这个形式，而是「学习」这个习惯。",
            gameImpact = "劳逸结合：技能点数+1，焦虑-2（均衡最佳）。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "坚持学习 / 劳逸结合 / 专注工作",
            coreInsight = "学习的形式不重要，重要的是持续成长。",
            detailText = "你可以选择坚持学习提升自己，也可以劳逸结合找到最适合的节奏，或者暂时专注当下工作。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("study_hard", "听从建议，坚持学习提升",
                "没错，学习确实重要——我再挤点时间。",
                ChoiceResult(skillPointsDelta = 2, fatigueDelta = 1, anxietyDelta = -1,
                    followUpText = "你选择了坚持学习。额外的付出会让你感到疲惫——但每次学完新东西，你都能感觉到自己比昨天更扎实了一点。这种积累，时间会给你答案。")),
            ReflectionChoice("balance_study", "劳逸结合，学习与休息兼顾",
                "学习重要，但休息也重要——我按自己的节奏来。",
                ChoiceResult(skillPointsDelta = 1, anxietyDelta = -2,
                    followUpText = "你选择了劳逸结合。不把自己逼得太紧，也不完全放弃成长——这是最可持续的节奏。你发现，当你不再焦虑「学没学够」时，反而能学得更深。")),
            ReflectionChoice("work_only", "放弃深造，专注当下工作",
                "现阶段先把工作做好——学习的事以后再说。",
                ChoiceResult(skillPointsDelta = -1, anxietyDelta = -1,
                    followUpText = "你选择了专注当下。这不是放弃——是你把精力放在了现在最需要的地方。但请记住：当你想再拿起书本的时候，随时都可以。小镇永远不嫌你起步晚。"))
        ),

        townCommentary = "小镇不评判你选哪条路。读书也好，工作也好，学习也好——重要的不是形式，是你有没有在成为更好的自己。而这个「更好」，是你自己定义的，不是别人。"
    )

    // ============================================
    // 分组3：资深阶段（46岁解锁 | 人生态度/阅历）
    // ============================================

    /** 条目7：人善被人欺，马善被人骑 */
    val GOOD_PEOPLE_SUFFER = CognitionReflection(
        id = "good_people_suffer",
        proverb = "人善被人欺，马善被人骑",
        minAge = 46,
        triggerScene = "待人宽厚反被算计",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "熟人社会的残酷经验",
            coreInsight = "熟人社会规则简单，一味善良没有底线，很容易被他人利用，因此劝人「不能太心软」。",
            detailText = "在规则不健全的旧时代，善良确实容易被利用。这句老话是一代代人被欺负后总结出来的血泪经验——它的存在本身就是一种悲哀。\n\n但今天：善良不是问题，没有底线才是。善良需要带锋芒，底线不是刻薄，而是保护自己。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "善良需要带锋芒",
            coreInsight = "心存善意，也要守住边界。",
            detailText = "善良不是软弱——真正的善良是：我有能力伤害你，但我选择不。\n\n没有底线的善良是在纵容别人的伤害。守住边界不代表你变坏了——是你学会了保护自己。\n\n小镇的核心观点：你可以继续善良，但请同时给自己画一条线——线外的人，不必容忍。",
            gameImpact = "保留善意+划定底线：人际好感+1，自我认同+3（均衡最优）。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "变得强硬 / 划清底线 / 继续心软",
            coreInsight = "底线不是刻薄——是你对自己的保护。",
            detailText = "你可以变得强硬不再吃亏，也可以保留善意同时划清底线，或者继续心软选择原谅。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("toughen", "改变性格，变得强硬计较",
                "受够了——以后谁也别想占我便宜。",
                ChoiceResult(socialGoodwillDelta = -2, anxietyDelta = -2, identityDelta = 2,
                    followUpText = "你选择了强硬。短期内你可能失去了一些表面的和气——但你发现，那些曾经欺负你的人开始收敛了。强硬不是你的本性，但它是你给自己的保护。")),
            ReflectionChoice("boundary", "保留善意，同时划定底线",
                "我还是善良的，但我不会再让人随便越界。",
                ChoiceResult(socialGoodwillDelta = 1, identityDelta = 3,
                    followUpText = "你选择了划清底线。这是最需要智慧的选择——你保留了善意，但不再让它变成软肋。你发现，当你学会说「不」的时候，那些真正尊重你的人反而更近了。")),
            ReflectionChoice("forgive", "依旧心软，选择原谅对方",
                "算了——也许他也不是故意的。",
                ChoiceResult(anxietyDelta = 2, identityDelta = -1,
                    followUpText = "你选择了原谅。善良是你的选择，不是你的弱点。但请记住：原谅不代表你要继续承受伤害。你可以原谅，也可以保持距离——这两者不矛盾。"))
        ),

        townCommentary = "小镇不评判你选哪条路。善良是你的底色，但底线是你的铠甲。你不必为了保护自己而变得冷漠——但你可以学会在善良的同时，也保护好自己。"
    )

    /** 条目8：儿孙自有儿孙福 */
    val CHILDREN_OWN_FORTUNE = CognitionReflection(
        id = "children_own_fortune",
        proverb = "儿孙自有儿孙福",
        minAge = 46,
        triggerScene = "过度操心晚辈/身心俱疲",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "传统代际关系中的放手哲学",
            coreInsight = "传统观念中长辈习惯全权安排晚辈人生，这句话用来劝长辈放下执念、减轻负担。",
            detailText = "在传统观念里，长辈对晚辈的人生有近乎无限的责任——从学业到婚姻到工作，事事操心。这句话是少有的劝长辈「放手」的智慧——不是冷漠，是承认人生终究需要自己走。\n\n但今天：过度干涉会造成两代矛盾，适当的距离反而是关系的润滑剂。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "关爱有度，学会放手",
            coreInsight = "可以给予建议与支持，但人生道路终究需要自己走。",
            detailText = "你的操心是爱——但爱有时候也需要距离。\n\n你可以给予建议，但不必替对方做决定；你可以提供支持，但不必扛起对方的责任。\n\n小镇的核心观点：关爱有度，学会放手——不是不在乎，是相信对方有能力走自己的路。",
            gameImpact = "适当提点不过度：疲惫值-2，家庭亲密值+2（均衡最优）。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "彻底放手 / 适当提点 / 继续操心",
            coreInsight = "放手不是不爱——是换一种方式爱。",
            detailText = "你可以彻底放手不再干涉，也可以适当提点不过度插手，或者继续事事过问。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("let_go", "彻底放手，不再干涉晚辈",
                "你说得对——儿孙自有儿孙福，我不管了。",
                ChoiceResult(fatigueDelta = -3, familyIntimacyDelta = -1,
                    followUpText = "你选择了放手。短期内你可能觉得和晚辈疏远了——但你也发现，那些替他们操心的焦虑消失了。放手不是冷漠——是你终于开始把自己的时间还给自己。")),
            ReflectionChoice("moderate", "适当提点，不过度插手",
                "我关心他们，但不再越界——给建议，不给命令。",
                ChoiceResult(fatigueDelta = -2, familyIntimacyDelta = 2,
                    followUpText = "你选择了适当的距离。你发现，当你不再事事过问，晚辈反而更愿意跟你聊天了。距离不是疏远——是给彼此呼吸的空间。")),
            ReflectionChoice("overcare", "继续操心，事事过问",
                "我放不下——他们是我最重要的人，我怎么能不管。",
                ChoiceResult(fatigueDelta = 3,
                    followUpText = "你选择了继续操心。这是你的选择——因为爱，所以放不下。但请记住：你的身体也在替你承载这些焦虑。偶尔，也给自己放个假——不是不关心，是让自己喘口气。"))
        ),

        townCommentary = "小镇不评判你选哪条路。操心是爱，放手也是爱。你选择继续操心，是你放不下；你选择适当放手，是你在学会信任。两种方式，都是爱——只是表达的方式不同。"
    )

    /** 条目9：吃亏是福 */
    val SUFFERING_IS_BLESSING = CognitionReflection(
        id = "suffering_is_blessing",
        proverb = "吃亏是福",
        minAge = 46,
        triggerScene = "利益受损/付出无回报",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "无奈之下的心理疏导",
            coreInsight = "物质匮乏、维权困难的年代，吃亏后只能用精神安慰化解委屈，是无奈之下的心理疏导。",
            detailText = "在旧时代，普通人吃了亏，大多没有能力追回。于是「吃亏是福」变成了一种精神镇痛剂——不是因为它有道理，是因为除了这样安慰自己，没有别的办法。\n\n但今天：主动让利、大度包容是格局；被动无故吃亏、一味忍让，并不是福气，而是损失。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "大度可以，但不必刻意吃亏",
            coreInsight = "主动让利是格局，被动吃亏是损失——两者完全不同。",
            detailText = "「吃亏」分两种：\n\n一种是主动让利——你明明可以多拿，但选择让给别人。这是大度，是格局。\n一种是被人算计——你被人占了便宜，却告诉自己「吃亏是福」。这不是福，是委屈。\n\n小镇的核心观点：大度可以，但不必刻意吃亏。你的权益，值得你站出来。",
            gameImpact = "理性看待该争取就争取：焦虑-1，自我认同+3（均衡最优）。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "自认吃亏 / 理性争取 / 主动讨回",
            coreInsight = "大度是你的选择，不是你的义务。",
            detailText = "你可以选择不计较放下，也可以理性看待该争取就争取，或者主动出击讨回应得利益。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("accept_loss", "自认吃亏，不再计较",
                "算了——就当我吃亏是福吧。",
                ChoiceResult(anxietyDelta = -2, identityDelta = -1,
                    followUpText = "你选择了不计较。短期内你获得了平静——但你的内心也知道，这不是「福」，是你在用「算了」来保护自己不被愤怒消耗。这不是软弱——但下次，你可以选择不亏。")),
            ReflectionChoice("rational", "理性看待，该争取就争取",
                "我不斤斤计较，但也不白吃亏——该我的我要拿回来。",
                ChoiceResult(anxietyDelta = -1, identityDelta = 3,
                    followUpText = "你选择了理性争取。你发现，当你温和但坚定地表达自己的诉求时，大多数情况下，对方是愿意沟通的。吃亏不是福——你为自己的权益站出来，这才是真正的福气。")),
            ReflectionChoice("fight_back", "主动争取，讨要应得利益",
                "吃一次亏就算了，不能次次都吃亏——这次我要讨个说法。",
                ChoiceResult(socialGoodwillDelta = -2, anxietyDelta = 1,
                    followUpText = "你选择了主动争取。短期内你可能会得罪一些人——但你也第一次真正地为自己站了出来。有些关系，经过这次冲突，反而更清楚了：谁值得交，谁不值得。"))
        ),

        townCommentary = "小镇不评判你选哪条路。不计较是你的大度，站出来是你的勇气。但请记住：吃亏不是福——你值得被公平对待，这不是奢求，是基本。"
    )

    // ============================================
    // 分组4：青年阶段（22岁解锁 | 自我边界/生活态度）
    // ============================================

    /** 条目10：独善其身 */
    val MIND_YOUR_OWN_BUSINESS = CognitionReflection(
        id = "mind_your_own_business",
        proverb = "独善其身",
        minAge = 22,
        triggerScene = "被他人拖累/卷入是非",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "乱世中的自保智慧",
            coreInsight = "「穷则独善其身」原出孟子，本意是在无力改变世道时守住自己的底线，不与污浊同流。",
            detailText = "在战乱频仍、朝政腐败的古代，普通人能做的非常有限。与其被卷入是非、同流合污，不如关起门来守住自己的清白。这句老话在乱世里，是普通人最后的尊严。\n\n但今天：你有更多选择——你可以独善，也可以兼济。问题不在于「该不该管别人」，而在于「你有没有先把自己安顿好」。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "先站稳，再伸手",
            coreInsight = "独善其身不是自私——是先把氧气面罩给自己戴上，再去帮别人。",
            detailText = "小镇的核心观点：你可以选择独善其身，也可以选择帮别人——但前提是你自己还没倒下。\n\n一个连自己都照顾不好的人，去帮别人更多时候是添乱。先把自己安顿好，照顾好，再有余力去伸手——这不是冷漠，是清醒。",
            gameImpact = "先安顿自己：焦虑-3，自我认同+2，社交满足不变。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "独善 / 兼顾 / 忘我",
            coreInsight = "先站稳的人，才有力气扶别人。",
            detailText = "你可以选择先把自己管好再帮人，也可以力所能及地兼顾两边，或者全身心投入去帮助他人。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("self_first", "先顾好自己，有余力再帮别人",
                "我自己还没站稳呢——等我安顿好了再说。",
                ChoiceResult(anxietyDelta = -3, identityDelta = 2,
                    followUpText = "你选择了先顾好自己。这不是自私——是你终于学会了先给自己戴上氧气面罩。一个先站稳的人，才有真正有力的手去扶别人。")),
            ReflectionChoice("balance_help", "力所能及地兼顾自己和他人",
                "我自己要顾，能帮的地方也帮一把——不勉强。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 2, socialGoodwillDelta = 1,
                    followUpText = "你选择了兼顾。不把自己掏空去帮人，也不关起门来只管自己。这种分寸感是最难的——但你做到了。")),
            ReflectionChoice("sacrifice_self", "放下自己的事，全力去帮别人",
                "我放不下他们——我自己的事可以往后放。",
                ChoiceResult(anxietyDelta = 2, identityDelta = 1, socialGoodwillDelta = 3, fatigueDelta = 2,
                    followUpText = "你选择了先去帮别人。这份善意是真实的，但你也感觉到了疲惫。请记住：一颗掏空了的井，给不出水来。你值得先被自己善待。"))
        ),

        townCommentary = "小镇不评判你选哪条路。先顾好自己不是自私，是清醒；去帮别人不是傻，是善良。但请记住：你也是那个「别人」——你也值得被善待、被你照顾。"
    )

    /** 条目11：随遇而安 */
    val GO_WITH_THE_FLOW = CognitionReflection(
        id = "go_with_the_flow",
        proverb = "随遇而安",
        minAge = 22,
        triggerScene = "频繁变动/环境不适应",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "低流动性社会的生存适应",
            coreInsight = "古代人生轨迹高度固定，频繁迁徙往往意味着战乱或灾荒，「随遇而安」是在动荡中给自己找到一丝平静的方式。",
            detailText = "在交通不便、信息闭塞的古代，大部分人终其一生生活在一个地方。当被迫离开时，「随遇而安」是用来说服自己接受现实的生存本能——不是因为你想要改变，而是因为你别无选择。\n\n但今天：环境可以主动选择，生活可以主动设计。「随遇」不代表「随波逐流」——你可以接纳当下的处境，同时不放弃对更好的追求。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "接纳当下，不等于放弃选择",
            coreInsight = "「随遇而安」最好的状态是：我知道现在的处境不完美，但我不焦虑——同时，我没有停止往前走。",
            detailText = "小镇的核心观点：随遇而安是一种能力，不是一种妥协。\n\n真正的「安」不是「算了不折腾了」，而是「我接受现在的自己，同时保留变好的可能性」。\n\n你可以对当下满意，同时对未来保持开放——这两者不矛盾。",
            gameImpact = "主动适应：焦虑-2，自我认同+2，社交满足+1。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "接受 / 适应 / 改变",
            coreInsight = "接纳不等于放弃——你可以同时「安于当下」和「追求更好」。",
            detailText = "你可以选择接受现状不再折腾，也可以主动适应新环境，或者不满当下、积极改变。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("accept", "接受现状，不再折腾",
                "到哪都一样——就这样吧，不折腾了。",
                ChoiceResult(anxietyDelta = -3, careerProgressDelta = -1,
                    followUpText = "你选择了接受。内心的平静是真实的——但你也要知道，接受不等于放弃。哪天你想再试试了，小镇随时欢迎你重新出发。")),
            ReflectionChoice("adapt", "主动适应，在新环境里找到自己的节奏",
                "既然到了这里，我就好好看看这里有什么——说不定有惊喜。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 2, socialGoodwillDelta = 1,
                    followUpText = "你选择了主动适应。不抗拒变化，也不被变化吞没——你开始在新环境里找到属于自己的角落。这种能力，比任何技能都珍贵。")),
            ReflectionChoice("change", "不满现状，积极寻求改变",
                "我不想随遇而安——我想去到更好的地方。",
                ChoiceResult(anxietyDelta = 1, careerProgressDelta = 2, fatigueDelta = 1,
                    followUpText = "你选择了改变。不适感是你的动力——你不甘心停在原地。这条路不轻松，但每一步都在走向你想要的生活。"))
        ),

        townCommentary = "小镇不评判你选哪条路。接受是智慧，适应是能力，改变是勇气。你可以在任何地方「安」下来——不是因为别无选择，而是因为你在哪里，哪里就可以是你的家。"
    )

    // ============================================
    // 分组5：中年阶段（30-35岁解锁 | 自我/他人边界）
    // ============================================

    /** 条目12：舍己为人 */
    val SELFLESS_DEVOTION = CognitionReflection(
        id = "selfless_devotion",
        proverb = "舍己为人",
        minAge = 30,
        triggerScene = "被他人过度索取/身心透支",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "集体主义道德的理想标杆",
            coreInsight = "在传统道德叙事里，「舍己为人」被奉为最高的善——一个人愿意牺牲自己去成全别人，这是最值得歌颂的美德。",
            detailText = "这种道德理想的形成有其历史背景：在资源极度匮乏的古代，一个群体的存续往往需要个体的牺牲。于是「舍己为人」不仅是美德，也是生存策略——今天你为集体牺牲，明天集体为你遮风挡雨。\n\n但问题是：当「舍己」成了常态，那个不断付出的人，谁来为他遮风挡雨？",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "善良需要边界",
            coreInsight = "「为人」之前先「为己」——不是自私，是可持续的善良。",
            detailText = "小镇的核心观点：你可以善良，但请先确保你不是在被消耗。\n\n好的关系是双向的——你的付出被看见、被珍惜，而不是被当作理所当然。\n\n如果你的「舍己」换来的是别人的习以为常，那这不是美德，是纵容。真正的善良需要边界——没有边界的善良，最后只会把自己掏空。",
            gameImpact = "划清边界：焦虑-3，自我认同+3，人际好感短期-1（长期更好）。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "继续付出 / 适度助人 / 划清边界",
            coreInsight = "你不必把自己掏空才能证明自己是好人。",
            detailText = "你可以选择继续无私付出，也可以力所能及地帮人但不透支自己，或者果断划清边界不再被消耗。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("keep_giving", "继续无私付出，能帮就帮",
                "别人需要我——我不能不管。",
                ChoiceResult(identityDelta = 2, socialGoodwillDelta = 2, fatigueDelta = 3,
                    followUpText = "你选择了继续付出。这份善良是真实的——但你也在消耗自己。请给自己设一个底线：再善良的井，也有干涸的一天。你不是在「舍己」，你是在透支自己。")),
            ReflectionChoice("moderate_help", "力所能及地帮人，但不透支自己",
                "我帮得动的就帮，帮不动的就说「不」——这不丢人。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 3, socialGoodwillDelta = 1,
                    followUpText = "你选择了适度。学会说「不」的感觉怎么样？一开始可能不习惯——但你会发现，那些真正在乎你的人，不会因为你的一次拒绝就离开。而那些因为你拒绝就翻脸的人，本来也不值得你付出。")),
            ReflectionChoice("set_boundary", "果断划清边界，不再被消耗",
                "够了——我不是谁的提款机，也不是谁的救世主。",
                ChoiceResult(anxietyDelta = -3, identityDelta = 3, socialGoodwillDelta = -1, clearNegativeStatus = true,
                    followUpText = "你选择了划清边界。短期来看，有些关系确实疏远了——但你也第一次感觉到，不再被掏空是什么样的轻松。你不是变冷漠了——你是终于开始把自己当人看了。"))
        ),

        townCommentary = "小镇不评判你选哪条路。付出是你的善良，边界是你的铠甲。你不必把自己掏空才能证明自己是个好人——你存在本身，就已经足够好。"
    )

    /** 条目13：安分守己 */
    val STAY_IN_YOUR_LANE = CognitionReflection(
        id = "stay_in_your_lane",
        proverb = "安分守己",
        minAge = 30,
        triggerScene = "想要改变现状但被劝阻",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "等级社会的维稳工具",
            coreInsight = "「安分守己」在古代是一种社会控制机制——每个人待在自己的位置上，社会就稳定了。",
            detailText = "在阶层固化的古代，底层人任何「不安分」的想法都可能招来麻烦。于是「安分守己」成了一种被内化的自我约束——不是因为你不想改变，而是因为改变的成本太高。\n\n但今天：社会流动的通道是存在的。你可以在不喜欢的时候换工作、换城市、换活法。「安分」不是必须的，「守己」才是——守住自己的底线和判断力。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "「守己」比「安分」更重要",
            coreInsight = "你可以不安分——只要你不丢掉自己。",
            detailText = "小镇的核心观点：不安分不是错。\n\n但如果你的「不安分」是在追逐别人定义的成功、活得越来越不像自己——那就不对了。\n\n「守己」的意思是：不管你在哪里、做什么，你都知道自己是谁、要什么。在这个前提下，你尽管去闯——小镇永远不嫌你折腾。",
            gameImpact = "在守己中进取：自我认同+3，职业进度+1，疲惫小幅增加。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "安于本分 / 稳步尝试 / 大胆闯荡",
            coreInsight = "你不需要向谁证明你有资格「不安分」——你生来就有选择的自由。",
            detailText = "你可以选择安心过好现在的日子，也可以稳扎稳打地尝试新东西，或者放开手脚去闯。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("stay_put", "安于本分，过好现在的日子",
                "现在这样也不错——不是每个人都需要轰轰烈烈。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 1,
                    followUpText = "你选择了安于本分。安稳不是平庸——是在你已经拥有的生活里发现了滋味。不是每个人都需要大风大浪——能在一潭静水里看到自己的倒影，也是一种幸福。")),
            ReflectionChoice("explore", "稳住当下，同时尝试新的可能",
                "我不辞职，但我可以业余时间试试别的东西。",
                ChoiceResult(anxietyDelta = -1, identityDelta = 2, skillPointsDelta = 1,
                    followUpText = "你选择了稳中求进。不赌上全部身家，但也不困在原地——这是成年人最务实的浪漫。你一边守护现有的安稳，一边悄悄为自己开辟新的可能。")),
            ReflectionChoice("break_out", "大胆闯荡，不想再安分了",
                "我受够了——我想试试不一样的人生。",
                ChoiceResult(anxietyDelta = 2, identityDelta = 3, careerProgressDelta = 2, fatigueDelta = 2,
                    followUpText = "你选择了闯荡。未知让人害怕，但比未知更可怕的是——十年后回想起来，后悔自己当初什么都没试。你不必向谁证明你有资格不安分——你只需要对自己负责。"))
        ),

        townCommentary = "小镇不评判你选哪条路。安分是智慧，闯荡是勇气。但请记住：「守己」比「安分」重要一百倍——只要你不丢掉自己，走到哪里都不会迷路。"
    )

    /** 条目14：未雨绸缪 */
    val PREPARE_FOR_RAIN = CognitionReflection(
        id = "prepare_for_rain",
        proverb = "未雨绸缪",
        minAge = 30,
        triggerScene = "经济不确定/未来焦虑",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "农业社会的生存法则",
            coreInsight = "在靠天吃饭的年代，不提前储备粮食就活不过冬天——「未雨绸缪」不是建议，是生存。",
            detailText = "古代农民必须提前一年规划粮食：春天播种、秋天收割、冬天储存。任何一个环节出错，全家都可能挨饿。在这种环境下成长的人，把「为未来焦虑」刻进了骨子里。\n\n但今天：你有社保、有存款、有更多选择。适度的规划是智慧，过度的焦虑是负担。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "规划未来，但不为未来焦虑",
            coreInsight = "提前准备很好——但准备到「现在的生活都没法过了」，就本末倒置了。",
            detailText = "小镇的核心观点：为未来打算是好事，但不要因为「绸缪」而丢掉了「当下」。\n\n那些你为了「以后」而省下的钱、放弃的体验、推迟的快乐——你真的确定那个「以后」会来吗？\n\n适度「绸缪」是给未来的自己留余地，过度「绸缪」是在透支现在的自己。你值得现在就过得好——不只是「将来某一天」。",
            gameImpact = "适度规划：焦虑-2，存款+一笔，疲惫不变。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "拼命存钱 / 适度规划 / 活在当下",
            coreInsight = "你值得现在就过得好——不只是「以后」。",
            detailText = "你可以选择大量储蓄以备不时之需，也可以适度规划兼顾当下，或者放下包袱享受现在。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("save_hard", "拼命储蓄，为最坏的情况做准备",
                "万一以后出事了怎么办——我得多存点。",
                ChoiceResult(anxietyDelta = 1, careerProgressDelta = 1, fatigueDelta = 1,
                    followUpText = "你选择了拼命储蓄。安全感是真实的——但你有没有发现，你为「万一」存的钱，已经影响了「一万」个日常？偶尔，也允许自己花一点点——不是浪费，是善待现在的自己。")),
            ReflectionChoice("moderate_save", "适度规划，该存存该花花",
                "存点钱是必要的，但日子也要过——我得找到平衡。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 2,
                    followUpText = "你选择了适度规划。不为未来焦虑，也不透支现在——你终于学会了和「不确定性」和平共处。该准备的准备了，该享受的也没落下。")),
            ReflectionChoice("live_now", "放下包袱，享受当下",
                "谁知道明天会怎样——我现在就想对自己好一点。",
                ChoiceResult(anxietyDelta = -1, identityDelta = 2, fatigueDelta = -1,
                    followUpText = "你选择了活在当下。这不是不负责任——是你终于允许自己快乐了。当然，给自己留点余地是好的——但偶尔放松一下，天不会塌。"))
        ),

        townCommentary = "小镇不评判你选哪条路。储蓄是给自己安全感，享受是在犒劳现在的自己。但请记住：你值得现在就过得好——不是等到存够了钱、熬到了退休、万事俱备了——就是现在。"
    )

    /** 条目15：顾全大局 */
    val FOR_THE_GREATER_GOOD = CognitionReflection(
        id = "for_the_greater_good",
        proverb = "顾全大局",
        minAge = 30,
        triggerScene = "个人意愿与集体/家庭要求冲突",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "集体至上的道德话语",
            coreInsight = "「顾全大局」在传统语境里，往往不是邀请，而是要求——「大局」高于个人，个人的感受在「大局」面前不值一提。",
            detailText = "在宗族社会里，个人的命运绑在家族的命运上。不「顾全大局」的人，不仅会自己吃亏，还会连累全家。在这种环境下，「大局」成了压制个人意愿的最有力工具。\n\n但「大局」是谁定义的？为什么总是你「顾」，别人不用？",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "「大局」不该总让你一个人扛",
            coreInsight = "「顾全大局」分两种：一种是主动选择为了更重要的东西让步——这是格局。一种是被迫牺牲自己的正当权益——这是剥削。",
            detailText = "小镇的核心观点：你当然可以为了在意的人做出让步——但前提是，这是你的选择，不是你的义务。\n\n如果每次「顾全大局」的代价都是你来承担——那这个「大局」不太对劲。一个真正的大局，应该让每个人都感觉到自己被照顾，而不是总有人在悄悄牺牲。",
            gameImpact = "主动选择让步：焦虑-1，家庭亲密+2。被动牺牲：焦虑+2，自我认同-1。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "主动让步 / 协商平衡 / 优先自己",
            coreInsight = "「大局」不该是某一个人的沉默和牺牲。",
            detailText = "你可以选择主动让步维护和谐，也可以协商找到平衡方案，或者优先自己的需求。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("step_back", "主动让步，维护整体和谐",
                "算了——我不争了，和谐最重要。",
                ChoiceResult(anxietyDelta = -1, familyIntimacyDelta = 2, identityDelta = -1,
                    followUpText = "你选择了让步。你看到了更大的图景，选择了不在小事上计较——这是你的格局。但请记住：让步是选择，不是义务。你不需要每次都让步。")),
            ReflectionChoice("negotiate", "协商平衡，找到双方都能接受的方案",
                "我理解大局——但能不能也考虑一下我的感受？",
                ChoiceResult(anxietyDelta = -2, identityDelta = 2, familyIntimacyDelta = 1,
                    followUpText = "你选择了协商。你发现，当你说出「我希望也能被考虑」的时候，对方并没有你想象的那么抵触。一个好的大局，本来就该容纳每个人的声音——包括你的。")),
            ReflectionChoice("prioritize_self", "这次我优先自己，不再委屈求全",
                "够了——每次都是我来顾大局，谁来顾我？",
                ChoiceResult(anxietyDelta = 1, identityDelta = 3, familyIntimacyDelta = -2,
                    followUpText = "你选择了优先自己。短期内关系有些紧张——但你也第一次不再委屈自己。有些「大局」不配你顾——真正的「大局」，不会让同一个人一次又一次地牺牲。"))
        ),

        townCommentary = "小镇不评判你选哪条路。让步是格局，争取是勇气。但请记住：如果每次「顾全大局」都是你一个人扛——那不叫大局，那叫你的沉默在替别人买单。一个真正好的「大局」，应该是人人都在其中被看见、被尊重。"
    )

    /** 条目16：删繁就简 */
    val SIMPLIFY_LIFE = CognitionReflection(
        id = "simplify_life",
        proverb = "删繁就简",
        minAge = 35,
        triggerScene = "生活杂乱/心力交瘁",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "文人雅士的审美追求",
            coreInsight = "「删繁就简三秋树」本是文艺审美——去掉多余的修饰，留下最本质的东西。后来被延伸到生活态度。",
            detailText = "在古代，这更多是文人墨客的审美追求——书画、园林、衣着，越简越雅。它代表的是一种超脱世俗的高级趣味。\n\n但放到普通人的日常生活：「删繁就简」不是让你去过苦行僧的日子——而是帮你分辨：哪些东西是真正重要的，哪些只是负担。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "不是越少越好，是越「对」越好",
            coreInsight = "「简」不是目的，「对」才是。你不需要一间空荡荡的房间——你需要的是一间没有负担的房间。",
            detailText = "小镇的核心观点：简化生活的目标不是「减少」，是「解放」。\n\n你不必扔掉所有东西去追求极简——你只需要扔掉那些让你看见就烦、每次整理都想叹气的东西。\n\n你不需要过苦行僧的生活——你需要的是：在你的空间和时间里，只留下那些让你觉得「舒服」的人和事。",
            gameImpact = "清理负担：焦虑-2，自我认同+1，疲惫值-1。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "大刀阔斧简化 / 循序渐进 / 维持现状",
            coreInsight = "你需要的不一定是更少——而是更适合你的。",
            detailText = "你可以选择大刀阔斧地简化生活，也可以循序渐进慢慢整理，或者觉得现在这样就挺好。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("radical_simplify", "大刀阔斧，果断清掉不必要的东西",
                "该扔的扔，该断的断——干净利落。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 2, fatigueDelta = -1,
                    followUpText = "你选择了大刀阔斧地简化。你发现，每扔掉一件让你心烦的东西，心里就多出一块空地。这块空地上不再堆满「万一要用」的焦虑——而是可以放进来一些真正让你开心的东西。")),
            ReflectionChoice("gradual_simplify", "慢慢来，一点一点精简",
                "不着急——我先从最乱的角落开始收拾。",
                ChoiceResult(anxietyDelta = -1, identityDelta = 1, fatigueDelta = -1,
                    followUpText = "你选择了循序渐进。不追求一夜之间改头换面——你相信，好的改变是慢慢发生的。每收拾一小块地方，你都觉得心里也清爽了一点点。")),
            ReflectionChoice("keep_as_is", "维持现状，现在这样就挺好",
                "我不想扔东西——每件东西都有它的记忆和理由。",
                ChoiceResult(anxietyDelta = -1, identityDelta = 1,
                    followUpText = "你选择了维持现状。那些旧物确实承载着你的过去——你舍不得它们，这完全合理。如果它们让你感到温暖而不是负担，那就留着。简化的标准不是「少」，是「舒服」。"))
        ),

        townCommentary = "小镇不评判你选哪条路。扔掉是勇气，留着是深情。但请记住：「简」不是目的，「你舒服」才是。你不需要一个空荡荡的人生——你需要的，是一个不让人心累的人生。"
    )

    // ============================================
    // 分组6：资深阶段（46岁解锁 | 阅历沉淀/人生态度）
    // ============================================

    /** 条目17：抱残守缺 */
    val HOLD_ON_TO_THE_PAST = CognitionReflection(
        id = "hold_on_to_the_past",
        proverb = "抱残守缺",
        minAge = 46,
        triggerScene = "拒绝接受新事物/固守旧习惯",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "对守旧者的批判",
            coreInsight = "守旧不可怕，可怕的是不知道自己为什么在守——是因为真的好，还是因为害怕改变？",
            detailText = "古人说「抱残守缺」是在批评那些死守着过时规矩不放的人。但在当时，保守是一种理性策略——在变化缓慢的古代，老方法往往是最可靠的方法。\n\n但今天：世界在加速变化。有些「旧」值得守——比如你的原则、你珍惜的关系。有些「旧」该放——比如那些已经不再服务于你的习惯和观念。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "守什么，放什么——你自己决定",
            coreInsight = "「保守」不是贬义词，「改变」也不是褒义词——关键是你选择它们的原因。",
            detailText = "小镇的核心观点：你可以守护你认为珍贵的东西——那是你的根，你的底气。\n\n但请分清楚：你守护它是因为它真的好、真的让你安心——还是因为它「一直就是这样」？\n\n改变不需要否定过去——你可以在保留了最珍贵的那部分之后，让剩下的部分悄悄更新。",
            gameImpact = "守住核心+开放接纳：自我认同+3，技能点数+1，焦虑-1。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "坚守不变 / 守住核心 / 拥抱变化",
            coreInsight = "你不需要扔掉全部过去才能迎接未来。",
            detailText = "你可以选择坚守旧的习惯和观念，也可以守住核心的同时尝试新事物，或者主动拥抱变化。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("hold_firm", "坚守旧习惯，不轻易改变",
                "老方法用了这么多年，一定有它的道理。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 1, skillPointsDelta = -1,
                    followUpText = "你选择了坚守。你的旧习惯让你感到安稳——这份安稳是真实的。但如果有一天，你发现它在拖累你而不是保护你——那时候再考虑改变也不晚。小镇不急，你也不用急。")),
            ReflectionChoice("keep_core", "守住重要的部分，其余的愿意尝试",
                "我有的东西不能丢——但我不排斥看看新东西。",
                ChoiceResult(anxietyDelta = -1, identityDelta = 3, skillPointsDelta = 1,
                    followUpText = "你选择了守住核心、开放外围。这不是妥协——是智慧。你知道什么不能丢，也知道什么可以更新。这种平衡感，需要阅历才能拥有——而你刚好到了这个年纪。")),
            ReflectionChoice("embrace_change", "主动拥抱变化，放下旧的开始新的",
                "都什么年代了——该换的就得换。",
                ChoiceResult(anxietyDelta = 1, identityDelta = 2, careerProgressDelta = 1, lonelinessDelta = 1,
                    followUpText = "你选择了拥抱变化。你放下了旧的东西，心里有些不舍——但你也感到了久违的轻盈。过去不是被扔掉了——是被你变成了脚下的一块砖，踩着它，去看新的风景。"))
        ),

        townCommentary = "小镇不评判你选哪条路。守着旧物是深情，拥抱新变是勇气。但请记住：你不用扔掉全部过去才能迎接未来——你可以在心里给那些珍贵的东西留一个位置，然后继续往前走。"
    )

    /** 条目18：自得其乐 */
    val JOY_IN_SOLITUDE = CognitionReflection(
        id = "joy_in_solitude",
        proverb = "自得其乐",
        minAge = 46,
        triggerScene = "孤独感/社交压力",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "隐士文化的遗产",
            coreInsight = "「自得其乐」在中国文化里有着深厚的隐士传统——陶渊明采菊东篱下，就是在一个人待着的时候找到了人生的滋味。",
            detailText = "在古代，一个人能「自得其乐」是一件很高级的事——它意味着你不依赖外界的认可，内心足够丰盈。但过去能「自得其乐」的人，往往是衣食无忧的读书人——他们有这个闲情逸致。\n\n今天：「自得其乐」对每个人都是重要的能力。不是因为你应该孤独——而是因为你需要学会，不管身边有没有人，你都不会觉得空。",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "一个人和孤独是两回事",
            coreInsight = "「自得其乐」不是「我没人陪所以我要学会快乐」——是「我能和自己相处得很好，然后我也有能力去和别人建立好的关系」。",
            detailText = "小镇的核心观点：一个人待着不叫孤独——只有当你不喜欢和自己待在一起的时候，独处才变成孤独。\n\n「自得其乐」是一种很深的底气：我不用靠别人来让自己开心——我本身就是一座够大的花园，不需要别人进来种花。\n\n有了这份底气，你去社交的时候反而更轻松——因为你不是在索取，而是在分享。",
            gameImpact = "享受独处：焦虑-3，自我认同+3，孤独值不变（内心丰盈的人不孤独）。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "深度独处 / 平衡生活 / 多去社交",
            coreInsight = "你不孤僻——你只是比别人更懂得怎么和自己相处。",
            detailText = "你可以选择深度享受独处时光，也可以在独处和社交之间找到平衡，或者主动走出去交朋友。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("deep_solitude", "享受独处，一个人也能过得有滋有味",
                "我一个人挺好——看书、散步、发呆，都是享受。",
                ChoiceResult(anxietyDelta = -3, identityDelta = 3, lonelinessDelta = -2,
                    followUpText = "你选择了享受独处。你不是孤僻——你是找到了和自己相处的最佳方式。一个人能把日子过得有滋有味，这是很多人都想要却做不到的能力。你现在就拥有它。")),
            ReflectionChoice("balanced_life", "独处和社交都来一点，不偏废",
                "我享受一个人的时间，但偶尔也想见见人。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 2, socialGoodwillDelta = 2,
                    followUpText = "你选择了平衡。你既拥有独处的丰盈，也享受社交的温度——这两种状态在你这里不矛盾。你有一个饱满的内心世界，同时也不拒绝外面的人进来坐坐。")),
            ReflectionChoice("reach_out", "走出自己的小世界，多交新朋友",
                "一个人待久了也会想找人说话——我去试试看。",
                ChoiceResult(anxietyDelta = 1, socialGoodwillDelta = 3, identityDelta = 1,
                    followUpText = "你选择了走出去。独处的能力不会丢——你已经有了一个丰盈的内心世界。现在你只是在这座花园里开了一扇门，邀请别人进来看看。愿不愿意进来是他们的事——但你已经开了门。"))
        ),

        townCommentary = "小镇不评判你选哪条路。独处是丰盈，社交是温暖。但请记住：你是你自己的花园——你不需要别人进来种花，你的花本来就很美。有人想进来看看，是锦上添花；没人来，你也是一座开满花的园子。"
    )

    /** 条目19：安享晚年 */
    val ENJOY_RETIREMENT = CognitionReflection(
        id = "enjoy_retirement",
        proverb = "安享晚年",
        minAge = 46,
        triggerScene = "退休焦虑/年龄压力",

        layerOne = ReflectionLayer(
            title = "时代局限性",
            subtitle = "传统生命周期观的终点期待",
            coreInsight = "在传统观念里，辛苦了一辈子，老了就该「安享」——这是对你一生辛劳的合理回报。",
            detailText = "在古代，「安享晚年」几乎是每个普通人最朴素的人生目标：年轻时拼命干活，年老时儿孙绕膝、颐养天年。这是对你一生付出的奖赏。\n\n但今天：六十岁精神矍铄的大有人在，四十岁已经累得不想动的也不少。「晚年」是什么时候开始的？谁来定义？",
            gameImpact = "长辈说这句俗语时：焦虑值 -2（仅情绪安抚）。"
        ),
        layerTwo = ReflectionLayer(
            title = "现代社会视角",
            subtitle = "「晚年」的定义权在你手上",
            coreInsight = "「安享」不是「什么都不做」——是「做你想做的，不用再做你不想做的」。",
            detailText = "小镇的核心观点：什么时候算「晚年」——你说了算。\n\n你可以选择慢下来、享受生活——这是你应得的。你也可以选择继续折腾、开启第二人生——这也是你的自由。\n\n「安享」不是躺平——是你终于不用再为别人而活了。你可以去学年轻时没时间学的东西、去年轻时没去过的地方、做年轻时没做过的梦。",
            gameImpact = "按自己的节奏来：焦虑-3，自我认同+3，生活满意度大增。"
        ),
        layerThree = ReflectionLayer(
            title = "三种选择",
            subtitle = "好好休息 / 量力而行 / 继续发光",
            coreInsight = "谁说人生只能按固定的时间表来——你的节奏，你说了算。",
            detailText = "你可以选择好好休息享受生活，也可以量力而行保持活力，或者继续发光发热开创新篇章。",
            gameImpact = ""
        ),

        choices = listOf(
            ReflectionChoice("rest_well", "好好休息，享受该有的清闲",
                "辛苦了大半辈子——现在我只想歇一歇，给自己泡杯茶。",
                ChoiceResult(anxietyDelta = -3, fatigueDelta = -3, identityDelta = 2,
                    followUpText = "你选择了休息。这不是放弃——是你终于给自己按下了暂停键。你值得这段清闲——不是因为你老了，是因为你辛苦了太久太久。好好喝茶，好好晒太阳——这是你挣来的。")),
            ReflectionChoice("stay_active", "量力而行，保持适度的活动",
                "歇够了就动一动——不跟年轻人比，但也不想闲着。",
                ChoiceResult(anxietyDelta = -2, identityDelta = 3, fatigueDelta = -1,
                    followUpText = "你选择了量力而行。不为证明什么，不为追赶谁——就是单纯地觉得，动一动挺好的。这种从容，年轻的时候学不来——是你这些年一步步走出来的底气。")),
            ReflectionChoice("keep_shining", "继续发光发热，开启人生新篇章",
                "我才刚到最好的年纪——现在才是我真正想活的活法。",
                ChoiceResult(anxietyDelta = 1, identityDelta = 3, careerProgressDelta = 2,
                    followUpText = "你选择了继续发光。谁说人生高峰必须在三十岁？你用行动证明了——最好的风景，可能在后半程。你不再是年轻时那个迷茫的人——你现在清楚自己要什么，而且有时间、有底气去追。"))
        ),

        townCommentary = "小镇不评判你选哪条路。休息是你的权利，继续是你的选择。但请记住：「晚年」这个词太老了——不如叫它「你自己的时间」。从这一刻起，你的时间表由你来定，你的节奏由你来踩。你不需要符合任何人的期待——包括年龄的期待。"
    )

    /** 所有条目 */
    val allEntries: List<CognitionReflection> = listOf(
        RAINBOW, NAIL_THAT_STICKS_OUT, NEAR_VERMILION,
        CONTENTMENT, FAMILY_HARMONY, READING_SUPREME,
        GOOD_PEOPLE_SUFFER, CHILDREN_OWN_FORTUNE, SUFFERING_IS_BLESSING,
        MIND_YOUR_OWN_BUSINESS, GO_WITH_THE_FLOW,
        SELFLESS_DEVOTION, STAY_IN_YOUR_LANE, PREPARE_FOR_RAIN,
        FOR_THE_GREATER_GOOD, SIMPLIFY_LIFE,
        HOLD_ON_TO_THE_PAST, JOY_IN_SOLITUDE, ENJOY_RETIREMENT
    )

    /** 按 ID 查找 */
    fun getById(id: String): CognitionReflection? = allEntries.firstOrNull { it.id == id }

    /** 按触发场景查找可用的条目 */
    fun getAvailableForScene(
        scene: String,
        age: Int,
        unlockedIds: Set<String>
    ): List<CognitionReflection> {
        return allEntries.filter { entry ->
            entry.triggerScene.contains(scene) &&
            age >= entry.minAge &&
            entry.id !in unlockedIds
        }
    }
}