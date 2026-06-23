package com.example.townapp.data

/**
 * 童年创伤与三重自我叙事体系（v2.12 成长创伤-自我对话-跨时光和解）
 *
 * 核心设计理念：
 * 1. 三重自我：幼年本能自我（隐藏创伤参数）→ 十年前过往自我（独立意识、心结形成）
 *    → 当下成年自我（具备选择和干预能力）
 * 2. 幼年/少年时期自动记录创伤参数，默认隐藏，青年阶段前不可见
 * 3. 中年阶段（28-35岁）解锁与十年前自我隔空对话的专属事件
 * 4. 玩家选择面对→治愈创伤，或忽视→创伤持续消耗心理参数
 * 5. "幼年穿潮湿鞋子的不适感会埋下潜意识心结"——符合心理学滞后性规律
 *
 * 世界观定位：
 *   治愈不是为了抹掉过去——是为了让现在的你不再被过去抓住。
 *   一部分人终生无法觉察创伤，迷茫内耗度过一生——这也是真实的人生常态。
 *   没有正确答案，没有最优解——只呈现"不同的选择，对应不同的状态与代价"。
 */

// ============================================
// 一、童年创伤类型
// ============================================

/**
 * 童年/少年创伤类型
 */
enum class TraumaType(
    val label: String,
    val category: TraumaCategory,
    val description: String
) {
    /** 穿戴匮乏系列 */
    CLOTHING_DEFICIENCY(
        "衣着匮乏", TraumaCategory.MATERIAL,
        "幼年常年穿着潮湿鞋袜、换季单薄衣物——饱受脚气、受凉折磨"
    ),
    SHOE_POVERTY(
        "鞋袜贫困", TraumaCategory.MATERIAL,
        "从小穿不透气的便宜鞋子，脚气反复发作——鞋穿到破洞才换"
    ),
    COLD_EXPOSURE(
        "受寒挨冻", TraumaCategory.MATERIAL,
        "冬天衣物不够保暖，手脚年年生冻疮——教室冷得写不了字"
    ),

    /** 外貌自卑系列 */
    APPEARANCE_BULLYING(
        "穿搭自卑", TraumaCategory.SOCIAL,
        "家境拮据衣着破旧，被同学议论嘲笑——不敢和人站在一起"
    ),
    LOOKS_INSECURITY(
        "外貌自卑", TraumaCategory.SOCIAL,
        "因长相被同龄人取笑或忽视——觉得自己不值得被看见"
    ),

    /** 情感缺失系列 */
    PARENTAL_NEGLECT(
        "陪伴缺失", TraumaCategory.EMOTIONAL,
        "父母忙于生计无暇陪伴——摔倒了自己爬起来，哭了没人哄"
    ),
    FAMILY_CONFLICT_WITNESS(
        "家庭冲突", TraumaCategory.EMOTIONAL,
        "幼年目睹父母频繁争吵——缩在角落里不敢出声"
    ),

    /** 学业社交系列 */
    ACADEMIC_HUMILIATION(
        "学业羞辱", TraumaCategory.SOCIAL,
        "成绩不好被当众批评——觉得自己'笨'的念头从此住下"
    ),
    PEER_COMPARISON(
        "攀比落差", TraumaCategory.SOCIAL,
        "同学有的你都没有——文具、零食、新衣服，你学会了'我不配'"
    )
}

/**
 * 创伤类别
 */
enum class TraumaCategory(val label: String) {
    MATERIAL("物质匮乏"),
    SOCIAL("社交创伤"),
    EMOTIONAL("情感缺失")
}

// ============================================
// 二、童年创伤运行时状态（隐藏参数）
// ============================================

/**
 * 单个创伤条目
 */
data class TraumaEntry(
    val type: TraumaType,
    /** 创伤严重度 0-100 */
    val severity: Int,
    /** 发生年龄 */
    val ageAtOccurrence: Int,
    /** 是否已被治愈（通过中年自我对话） */
    val isHealed: Boolean = false,
    /** 治愈发生的年龄 */
    val healedAtAge: Int = -1,
    /** 创伤描述 */
    val narrative: String = ""
)

/**
 * 童年创伤系统运行时状态
 */
data class ChildhoodTraumaState(
    /** 所有创伤条目 */
    val traumas: List<TraumaEntry> = emptyList(),
    /** 未治愈创伤总严重度 */
    val totalUnhealedSeverity: Int = 0,
    /** 是否已触发中年自我对话 */
    val pastSelfDialogueTriggered: Boolean = false,
    /** 自我对话选择结果 */
    val dialogueChoice: PastSelfDialogueChoice? = null,
    /** 是否选择了忽视/回避 */
    val hasAvoidedDialogue: Boolean = false,
    /** 当前是否处于自我对话进行中 */
    val isDialogueActive: Boolean = false,
    /** 对话年份 */
    val dialogueYear: Int = -1,
    /** 十年前自我的年龄 */
    val pastSelfAge: Int = -1
) {
    /**
     * 未治愈创伤的每日心理消耗
     */
    fun dailyPsychDrain(): PsychDrain {
        val effectiveSeverity = if (totalUnhealedSeverity > 100) 100 else totalUnhealedSeverity
        return when {
            effectiveSeverity == 0 -> PsychDrain(0, 0, 0)
            effectiveSeverity < 20 -> PsychDrain(
                anxietyDelta = 1, identityDelta = -1, description = "你偶尔会觉得莫名烦躁——说不清从哪里来的"
            )
            effectiveSeverity < 40 -> PsychDrain(
                anxietyDelta = 2, identityDelta = -2,
                description = "你经常感到一种说不清来源的焦虑——它像一团雾，没有形状，但一直裹着你"
            )
            effectiveSeverity < 60 -> PsychDrain(
                anxietyDelta = 3, identityDelta = -3, lonelinessDelta = 1,
                description = "你觉得自己不够好——但你说不清'好'的标准是什么。它来自很久以前——比你的记忆还久"
            )
            else -> PsychDrain(
                anxietyDelta = 4, identityDelta = -5, lonelinessDelta = 2,
                description = "童年的阴影从未离开——它变成了你性格里的一部分。你比大多数人更容易不安——但这不怪你"
            )
        }
    }
}

/**
 * 每日心理消耗
 */
data class PsychDrain(
    val anxietyDelta: Int = 0,
    val identityDelta: Int = 0,
    val lonelinessDelta: Int = 0,
    val description: String = ""
)

// ============================================
// 三、中年自我对话事件
// ============================================

/**
 * 自我对话选择（中年阶段，和十年前自己对话）
 */
enum class PastSelfDialogueChoice(
    val label: String,
    val description: String,
    val healsRatio: Double,  // 治愈创伤比例（0-1）
    val anxietyDelta: Int,
    val identityDelta: Int,
    val resilienceDelta: Int,
    val resultNarrative: String
) {
    /**
     * 选择1：温柔接纳——告诉过去的自己：你已经很好了
     */
    GENTLE_ACCEPTANCE(
        "温柔接纳",
        "你对着十年前的自己说：'你已经很好了。那些让你自卑的东西——不是你该背的。你穿着旧衣服，但你有一颗完整的心——这比什么都重要。'",
        healsRatio = 0.8,
        anxietyDelta = -10, identityDelta = 15, resilienceDelta = 8,
        "你说完之后，感觉有什么东西松开了。那个穿着破旧衣服的小孩，第一次得到了承认——不是来自别人，是来自未来的你。你治愈的不是过去——是你心里住了很多年的那个孩子。"
    ),
    /**
     * 选择2：实用建议——告诉过去的自己可以怎么做
     */
    PRACTICAL_GUIDANCE(
        "实用指引",
        "你对十年前的自己说：'别太在意别人的眼光——他们不会因为你的衣服记住你。把时间花在读书上，花在学技术上一那些东西能陪你一辈子，衣服不行。多照顾自己的脚——换双透气的鞋，别忍着。'",
        healsRatio = 0.6,
        anxietyDelta = -6, identityDelta = 10, resilienceDelta = 5,
        "你给了过去的自己一些具体的建议。你知道这些建议已经来不及改变过去了——但你说出来之后，现在的你好像也能用上。你开始对自己好一点了——从脚开始。"
    ),
    /**
     * 选择3：保持距离——暂时不想面对
     */
    MAINTAIN_DISTANCE(
        "保持距离",
        "你看着十年前的自己——你想说点什么，但不知道怎么开口。你选择了沉默。有些伤口你还没准备好去碰——这没关系。先这样。",
        healsRatio = 0.0,
        anxietyDelta = 2, identityDelta = 0, resilienceDelta = -2,
        "你没有和过去的自己说话。你转身走了。不是不想治愈——是还没准备好。也许下一次——也许永远不会。有些人就是不回头看过去的——这也没关系。"
    ),
    /**
     * 选择4：否认回避——过去的已经过去了
     */
    DENIAL(
        "让它过去",
        "你对自己说：'那些都是过去的事了。纠结那些有什么用？过去了就过去了。' 你说得很坚定——但说完之后，心里某个角落还是有点不舒服。",
        healsRatio = 0.0,
        anxietyDelta = 3, identityDelta = -3, resilienceDelta = 0,
        "你选择把过去留在过去。这是一个选择——但它不解决地下室的积水，只是不再往下面走而已。那些创伤还在——只是不再被看见了。"
    )
}

/**
 * 中年自我对话事件数据
 */
data class PastSelfDialogueEvent(
    val pastSelfAge: Int,
    val currentAge: Int,
    val yearsGap: Int,
    val traumasSummary: String,
    val introNarrative: String,
    val choices: List<PastSelfDialogueChoice> = listOf(
        PastSelfDialogueChoice.GENTLE_ACCEPTANCE,
        PastSelfDialogueChoice.PRACTICAL_GUIDANCE,
        PastSelfDialogueChoice.MAINTAIN_DISTANCE,
        PastSelfDialogueChoice.DENIAL
    )
)

// ============================================
// 四、创伤-穿戴联动
// ============================================

/**
 * 童年穿戴匮乏→成年后补偿行为的影响
 */
object ChildhoodClothingCompensation {

    /**
     * 判断童年穿戴匮乏是否触发成年补偿心理
     * 童年经历了穿戴类创伤 → 成年后精致穿搭的自我认同加成更高
     */
    fun compensationIdentityBonus(traumas: List<TraumaEntry>): Int {
        val clothingTraumas = traumas.filter {
            it.type.category == TraumaCategory.MATERIAL &&
            !it.isHealed &&
            it.severity > 30
        }
        return if (clothingTraumas.isNotEmpty()) {
            // 童年缺什么，成年后获得时心理满足感更强
            clothingTraumas.sumOf { it.severity / 20 }
        } else 0
    }

    /**
     * 判断是否存在童年穿戴自卑带来的长期社交退缩
     */
    fun socialWithdrawalBonus(traumas: List<TraumaEntry>): Double {
        val socialTraumas = traumas.filter {
            it.type == TraumaType.APPEARANCE_BULLYING && !it.isHealed
        }
        return socialTraumas.sumOf { it.severity } / 100.0 * 0.1
    }
}

// ============================================
// 五、创伤生成规则
// ============================================

/**
 * 童年创伤自动生成器
 */
object TraumaGenerator {

    /**
     * 根据家境和童年环境自动生成创伤条目
     *
     * @param background 家境
     * @param childhoodCosmetics 童年环境描述（未来可扩展）
     */
    fun generate(background: ClassBackground): List<TraumaEntry> {
        val traumas = mutableListOf<TraumaEntry>()

        when (background) {
            ClassBackground.UNDERPRIVILEGED -> {
                // 底层出身：物质匮乏类创伤概率高
                if (Math.random() < 0.7) traumas.add(TraumaEntry(
                    TraumaType.CLOTHING_DEFICIENCY, severity = (30..70).random(),
                    ageAtOccurrence = (5..12).random(),
                    narrative = "小时候家里没钱买合身的厚衣服——冬天冻得缩成一团，手脚都是冻疮"
                ))
                if (Math.random() < 0.6) traumas.add(TraumaEntry(
                    TraumaType.SHOE_POVERTY, severity = (40..80).random(),
                    ageAtOccurrence = (5..14).random(),
                    narrative = "鞋子穿到破了洞还在穿——脚气是童年的常客，痒得睡不着的时候只能忍着"
                ))
                if (Math.random() < 0.5) traumas.add(TraumaEntry(
                    TraumaType.APPEARANCE_BULLYING, severity = (30..60).random(),
                    ageAtOccurrence = (10..16).random(),
                    narrative = "同学笑你的衣服旧——你学会了缩在最后一排，不让人注意到你"
                ))
                if (Math.random() < 0.5) traumas.add(TraumaEntry(
                    TraumaType.PARENTAL_NEGLECT, severity = (20..60).random(),
                    ageAtOccurrence = (3..12).random(),
                    narrative = "爸妈太忙了——哭了没人哄，摔了自己爬起来。你学会了自己照顾自己"
                ))
                if (Math.random() < 0.4) traumas.add(TraumaEntry(
                    TraumaType.PEER_COMPARISON, severity = (20..50).random(),
                    ageAtOccurrence = (8..15).random(),
                    narrative = "同学有新文具、新书包——你什么都没有。你跟妈妈说'不需要'——但心里还是有点难过"
                ))
            }
            ClassBackground.AFFLUENT -> {
                // 富裕出身：物质创伤少但情感/圈层创伤存在
                if (Math.random() < 0.4) traumas.add(TraumaEntry(
                    TraumaType.PARENTAL_NEGLECT, severity = (20..50).random(),
                    ageAtOccurrence = (3..10).random(),
                    narrative = "家里什么都有——就是没有大人陪你。钱能买到玩具，买不到一个人坐在你旁边"
                ))
                if (Math.random() < 0.35) traumas.add(TraumaEntry(
                    TraumaType.FAMILY_CONFLICT_WITNESS, severity = (25..55).random(),
                    ageAtOccurrence = (6..14).random(),
                    narrative = "父母在客厅吵得很大声——你关上了房间的门，但声音还是穿过来了"
                ))
                if (Math.random() < 0.3) traumas.add(TraumaEntry(
                    TraumaType.PEER_COMPARISON, severity = (20..45).random(),
                    ageAtOccurrence = (8..15).random(),
                    narrative = "你家里条件不错——但同学说你是'靠爸妈的'。你开始怀疑：自己到底有什么是自己的？"
                ))
            }
        }

        // 通用创伤（不分家境）
        if (Math.random() < 0.2) traumas.add(TraumaEntry(
            TraumaType.ACADEMIC_HUMILIATION, severity = (20..40).random(),
            ageAtOccurrence = (8..14).random(),
            narrative = "一次考试没考好，被老师在班上点名——那个'你不行'的声音，从此跟你一起长大"
        ))

        return traumas
    }

    /** 计算未治愈创伤总严重度 */
    fun totalUnhealedSeverity(traumas: List<TraumaEntry>): Int {
        return traumas.filter { !it.isHealed }.sumOf { it.severity }
    }
}