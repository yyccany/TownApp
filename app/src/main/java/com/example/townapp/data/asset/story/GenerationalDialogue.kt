package com.example.townapp.data

/**
 * 代际认知对话体系（v2.5 代际认知局限）
 *
 * 核心设计理念：
 * 1. "等长大就好了"是长辈基于自身生存经验总结的时间式安慰，不是敷衍，是认知局限
 * 2. 长辈忙于谋生，缺少系统思考社会底层逻辑的精力与教育资源，只能给出经验式安慰
 * 3. 少数受过高等教育、长期深度思考的人，才会从底层逻辑（通胀、工时、圈层壁垒）分析问题
 * 4. 两种认知并存，完整还原真实社会结构
 * 5. 青年阶段经历社会压力后，一部分人延续父辈模式，一部分人打破循环
 *
 * 代际闭环：
 *   孩童阶段：长辈说"等长大就好了" → 孩童无生存压力认知，默认成年就能解决
 *   青年阶段：步入社会，发现年龄增长≠困境消解 → 一部分迷茫，一部分钻研底层逻辑
 *   中年阶段：面对下一代 → 一部分重复"等长大就好了"，一部分用底层逻辑沟通
 */

// ============================================
// 一、代际对话类型
// ============================================

/**
 * 代际对话场景类型
 */
enum class GenerationalDialogueType(val label: String, val description: String) {
    /** 孩童消费诉求 → 长辈时间式安慰 */
    CHILD_CONSUMPTION("孩童消费诉求", "孩子想要买不起的东西，长辈说'等长大就好了'。"),
    /** 青年迷茫 → 反思"等以后就好了"的局限 */
    YOUTH_REALIZATION("青年社会觉醒", "步入社会后，发现单纯年龄增长无法消解困境。"),
    /** 中年面对下一代 → 两种回应模式 */
    MIDLIFE_CLOSURE("中年认知闭环", "面对下一代时，延续经验安慰还是开启底层逻辑沟通。"),
    /** 深度思考者反思 */
    DEEP_THINKER("深度思考者视角", "受过高等教育、长期思考的人，从底层逻辑拆解生存压力。")
}

// ============================================
// 二、回应模式
// ============================================

/**
 * 代际对话的两种回应模式
 */
enum class GenerationalResponseMode(val label: String, val description: String) {
    /** 经验式时间安慰：依据自身人生经历，默认时间会消解困境 */
    TIME_COMFORT("经验式安慰", "依据自身经历总结：长大、结婚、变老，困境便会消解。"),
    /** 底层逻辑拆解：系统分析通胀、工时、圈层壁垒、行业兴衰等深层变量 */
    LOGICAL_ANALYSIS("底层逻辑拆解", "拆解社会运行规律，给出理性分析而非时间式安慰。")
}

// ============================================
// 三、代际对话事件
// ============================================

/**
 * 代际认知对话事件
 *
 * 五段式结构：体感 → 闪光点 → 个人工时 → 时代成本 → 小镇评述
 */
data class GenerationalDialogue(
    val id: String,
    val type: GenerationalDialogueType,
    /** 触发场景描述 */
    val triggerContext: String,
    /** 说话者身份 */
    val speaker: String,
    /** 听话者身份 */
    val listener: String,

    // ---- 五段式文案 ----
    val bodyFeeling: String,
    val sparkle: String,
    val personalCost: String,
    val eraCost: String,
    val townCommentary: String,

    /** 玩家可选回应（青年/中年阶段） */
    val choices: List<GenerationalChoice>
)

/**
 * 玩家回应选项
 */
data class GenerationalChoice(
    val mode: GenerationalResponseMode,
    val label: String,
    val responseText: String,
    /** 对认知参数的影响 */
    val cognitionChange: String
)

// ============================================
// 四、预置代际对话场景
// ============================================

object GenerationalDialogues {

    val dialogues: List<GenerationalDialogue> = listOf(

        // ===== 场景1：孩童消费诉求 → 长辈时间式安慰 =====
        GenerationalDialogue(
            id = "child_consumption",
            type = GenerationalDialogueType.CHILD_CONSUMPTION,
            triggerContext = "商场里，你盯着橱窗里那个你买不起的东西看了很久。长辈走过来，拍了拍你的肩膀。",
            speaker = "长辈（父辈/祖辈）",
            listener = "孩童（你）",
            bodyFeeling = "你听到长辈说「等以后长大就好了，到时候想买什么就买什么」。你听了之后心里稍微好受了一点——原来不是永远买不起，只是暂时买不起。你把希望寄托在了「长大」这两个字上。",
            sparkle = "长辈说这句话，不是因为敷衍你。他们年轻时确实经历过没钱、被约束的窘迫——随着年龄增长，收入提升、经济自主权提高，那些幼年买不起的东西确实慢慢能买了。他们用自己的人生经验，给了你当时最需要的那一点点安慰。",
            personalCost = "无需工时评估。这是长辈在有限的认知框架里，给出的最真诚的安慰。",
            eraCost = "这句话代代相传，构成了社会大众普遍习得的生存安慰方式。它本身没有错——只是它没有告诉你，长大之后会有新的、更复杂的问题在等着你。",
            townCommentary = "长辈的一句话，背后是他们一生的生存经验。他们不是故意骗你——他们只是把自己走过来的路，当成了一条所有人都能走通的路。他们看不到房租、通胀、职场内卷这些新问题，因为他们的时代没有这些。这句话不是谎言，是认知局限的产物。",
            choices = emptyList()
        ),

        // ===== 场景2：青年觉醒——发现"等长大就好了"不够用 =====
        GenerationalDialogue(
            id = "youth_realization",
            type = GenerationalDialogueType.YOUTH_REALIZATION,
            triggerContext = "你已经成年了，工作了几年。某天发工资时，你看着到账的数字——扣掉房租、生活费、交通费，剩下的钱和当年一样少。小时候以为「长大就好了」——现在你长大了，但问题并没有消失，只是换了一种形式。",
            speaker = "你（青年阶段）",
            listener = "自己（内心独白）",
            bodyFeeling = "你坐在出租屋里，想起小时候长辈说的那句「等长大就好了」。你突然觉得一阵荒诞——你长大了，但买不起的东西还是买不起，只是从小卖部的玩具变成了房子、车子和安全感。你第一次意识到：年龄增长，并不自动消解困境。",
            sparkle = "这个发现让你从「被动等待时间解决一切」的思维里跳了出来。你开始主动思考：为什么长大了还是不够？是什么在吃掉你的收入？你开始接触通胀、工时成本、行业周期这些概念——虽然还没有答案，但你不再只是等待了。",
            personalCost = "认知觉醒本身不需要消耗工时，但后续的主动学习、思维转变，需要持续投入精力。",
            eraCost = "越来越多年轻人意识到「等长大就好了」的局限性，开始主动钻研社会运行规律。这种认知迭代，长期会推动社会从经验主义走向理性分析。",
            townCommentary = "你发现了长辈那句话里的漏洞——不是他们骗你，是他们的世界和你的世界，已经是两个不同的时代了。他们年轻时面对的是「没钱买」，你现在面对的是「赚了钱也不够」。这不是你的错，也不是他们的错——是时代在两个人之间划了一道认知的鸿沟。",
            choices = listOf(
                GenerationalChoice(
                    mode = GenerationalResponseMode.TIME_COMFORT,
                    label = "算了，可能再过几年就好了",
                    responseText = "你把这个问题放在了一边。也许等你到了中年，收入更高了，一切就会好起来——就像长辈说的那样。",
                    cognitionChange = "延续经验主义思维，认知模式未改变。"
                ),
                GenerationalChoice(
                    mode = GenerationalResponseMode.LOGICAL_ANALYSIS,
                    label = "开始学习底层逻辑，弄懂为什么",
                    responseText = "你决定不再只是等待。你开始读书、看文章、和人讨论——你去了解什么叫通胀、什么叫工时成本、什么叫圈层壁垒。你不再问「为什么我这么穷」，你开始问「这个系统是怎么运作的」。",
                    cognitionChange = "认知模式从经验主义升级为底层逻辑分析。认知局限减少20点。"
                )
            )
        ),

        // ===== 场景3：中年认知闭环——面对下一代 =====
        GenerationalDialogue(
            id = "midlife_closure",
            type = GenerationalDialogueType.MIDLIFE_CLOSURE,
            triggerContext = "你已经人到中年。某天，一个晚辈用和你当年一样的眼神，看着一件买不起的东西。你站在长辈的位置上，即将说出你当年听到的那句话。",
            speaker = "你（中年阶段）",
            listener = "晚辈",
            bodyFeeling = "你看着晚辈的眼睛，你清楚地记得自己当年听到「等长大就好了」时的心情——那种把希望寄托给未来的复杂感受。现在轮到你说了。你张了张嘴，发现这句话没有当年听起来那么理所当然。",
            sparkle = "你比当年的长辈多了一个优势——你经历过「等长大」之后发现问题没消失的全过程。你知道这句话的局限，也知道它背后的善意。你可以选择重复它，也可以选择说点不一样的。",
            personalCost = "一次对话，不影响工时。但你说的每一个字，都可能影响一个年轻人未来多年的思维模式。",
            eraCost = "如果一代人全部选择打破经验主义循环，社会整体的认知水平会逐步提升。但现实中，大多数人会延续父辈模式——这是认知代际传承的客观规律。",
            townCommentary = "你站在了当年长辈站过的位置。你终于理解了——他们当年说那句话，不是不知道世界复杂，而是在有限的认知框架里，给了你当时最需要的安慰。你现在的选择，决定了这个循环是继续还是打破。",
            choices = listOf(
                GenerationalChoice(
                    mode = GenerationalResponseMode.TIME_COMFORT,
                    label = "「等以后长大就好了」",
                    responseText = "你说出了你当年听到的那句话。你心里清楚它的局限——但你也知道，有些安慰的意义不在于正确，在于让人在当下好受一点。",
                    cognitionChange = "代际认知循环延续。你选择了善意，但未打破认知鸿沟。"
                ),
                GenerationalChoice(
                    mode = GenerationalResponseMode.LOGICAL_ANALYSIS,
                    label = "「长大之后会有新的问题，但你可以学会拆解它们」",
                    responseText = "你没有说「等长大就好了」。你坐下来，用他能听懂的方式，解释了为什么有些东西贵、为什么赚钱不容易、为什么「长大」不等于「问题消失」——以及，他可以怎么去理解这一切。",
                    cognitionChange = "代际认知循环被打破。下一代获得底层逻辑思维启蒙，认知参数正向调整。"
                )
            )
        ),

        // ===== 场景4：深度思考者视角——为什么大多数人只会说"等以后就好了" =====
        GenerationalDialogue(
            id = "deep_thinker",
            type = GenerationalDialogueType.DEEP_THINKER,
            triggerContext = "夜深人静，你回顾自己这些年从「等长大就好了」到「弄懂底层逻辑」的转变，开始思考一个更深层的问题：为什么大多数人只会说那句安慰的话？",
            speaker = "你（深度思考者）",
            listener = "自己（内心独白）",
            bodyFeeling = "你意识到——不是长辈不想给出更好的答案，是他们没有条件。拆解社会运行规律需要教育资源、深度思考习惯、充足的自由时间。父辈年轻时忙于谋生，繁重体力与工作消耗了大部分精力，没有多余条件钻研底层逻辑。他们不是不想，是做不到。",
            sparkle = "你理解了两种认知的根源差异：经验主义来自生存压力下对时间的朴素依赖，逻辑分析来自教育资源和思考自由。这不是谁更聪明的问题——是两个人的生存条件完全不同。",
            personalCost = "深度思考需要长期投入时间与精力——这是很多人无法负担的奢侈品。",
            eraCost = "少数人的深度思考，长期会推动社会认知迭代。但大多数人的认知模式受限于生存条件——这是社会结构的客观现实，不是个人选择的问题。",
            townCommentary = "你不再责怪当初说「等长大就好了」的长辈。他们给你的，是他们在那个年代能给出的最好的东西。而你现在能做的最好的事——不是嫌弃他们不够好，是下一次有人问你怎么了的时候，你给出一个比「等以后就好了」更接近真相的答案。",
            choices = emptyList()
        )
    )

    /** 根据类型查找对话 */
    fun findByType(type: GenerationalDialogueType): List<GenerationalDialogue> {
        return dialogues.filter { it.type == type }
    }

    /** 根据ID查找 */
    fun findById(id: String): GenerationalDialogue? {
        return dialogues.find { it.id == id }
    }
}