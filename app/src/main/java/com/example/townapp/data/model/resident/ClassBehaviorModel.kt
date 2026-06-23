package com.example.townapp.data

/**
 * 阶层行为倾向体系（v2.6 破除二元刻板印象）
 *
 * 核心设计原则：
 * 1. 贫富不决定人的本性善恶——只决定初始环境、焦虑基数、处事模式
 * 2. 两种出身都存在善良者与利己者，只是触发自私冷酷行为的诱因不同
 * 3. 后天经历优先于出身参数：教育、人际事件、细碎磨难能改变先天走向
 * 4. 概率分布贴合现实规律，同时保留大量反例打破标签化
 *
 * 底层逻辑：
 *   - 底层匮乏恐惧 → 容错空间极低 → 不敢温和妥协 → 掌权后易强硬管控
 *   - 富裕物质安全 → 无关核心利益时温和 → 触及圈层利益时体面排他
 *   两者都是"环境塑造的应激选择"，不是固化天性
 */

// ============================================
// 一、阶层出身枚举
// ============================================

/**
 * 阶层出身
 */
enum class ClassBackground(val label: String, val description: String) {
    /** 底层/寒门出身：长期面临资源紧缺，生存焦虑高 */
    UNDERPRIVILEGED(
        "底层/寒门",
        "从小到大长期面临资源紧缺：房租压力、薪资不稳定、失业风险、物资不足。资源一直处于争抢模式。"
    ),
    /** 优渥/富裕出身：物质安全感充足，生存焦虑低 */
    AFFLUENT(
        "优渥/富裕",
        "从小衣食无忧，生存资源稳定，不用争抢基础生存物资。日常友善来自物质安全感。"
    )
}

// ============================================
// 二、权力行为模式枚举
// ============================================

/**
 * 底层出身掌权者的行为模式
 */
enum class PowerBehaviorMode(val label: String, val description: String, val probability: Double) {
    /** 强硬管控：因匮乏恐惧而严苛挤压他人利益 */
    HARSH_CONTROL(
        "强硬管控",
        "内心深处极度害怕重新跌落回贫苦环境。用严苛、强势的管控方式挤压他人利益，杜绝别人威胁自身地位。",
        0.65
    ),
    /** 中庸处事：既不严苛也不特别体恤，保持平衡 */
    MODERATE(
        "中庸处事",
        "既没有强烈的控制欲，也没有特别体恤民众的动力。按规则办事，不越界也不进取。",
        0.25
    ),
    /** 体恤民众：共情良知，亲历疾苦后出台惠民政策 */
    BENEVOLENT(
        "体恤民众",
        "深切体会普通人谋生难处，出于共情良知出台惠民政策。数量稀少但真实存在——时代的革新者。",
        0.10
    )
}

/**
 * 富裕出身掌权者的行为模式
 */
enum class EliteBehaviorMode(val label: String, val description: String, val probability: Double) {
    /** 温和维护：无关核心利益时友善，触及利益时体面排他 */
    MILD_MAINTAIN(
        "温和维护",
        "无关自身核心利益时待人谦和。触及家族垄断、圈层利益时制定排他性规则，手段圆滑体面，属于软性利己。",
        0.70
    ),
    /** 革新惠民：跳出圈层推进改革 */
    REFORMATIVE(
        "革新惠民",
        "亲眼见证底层苦难后打破圈层壁垒，推进民生改革。少数人的选择——需要跳出自身阶层视角的勇气。",
        0.15
    ),
/** 骄横傲慢：依仗家境打压他人 */
    ARROGANT(
        "骄横傲慢",
        "从小被圈层竞争熏陶，习惯利用人脉资源打压竞争者，依仗家境傲慢待人，恃优而骄。",
        0.15
    )
}

// ============================================
// 三、运行状态
// ============================================

/**
 * 阶层行为运行状态
 */
data class ClassBehaviorState(
    /** 阶层出身 */
    val background: ClassBackground = ClassBackground.AFFLUENT,
    /** 生存焦虑（0-100），底层出身初始偏高，富裕出身初始偏低 */
    val survivalAnxiety: Int = 0,
    /** 是否处于掌权位置 */
    val isInPower: Boolean = false,
    /** 当前行为模式（底层视角） */
    val powerMode: PowerBehaviorMode? = null,
    /** 当前行为模式（富裕视角） */
    val eliteMode: EliteBehaviorMode? = null,
    /** 后天偏移量（-30到+30），正数=更温和/体恤，负数=更严苛/利己 */
    val postnatalModifier: Int = 0,
    /** 权力稳定年限 */
    val yearsInPower: Int = 0,
    /** 是否已触发过后天转折 */
    val hasPostnatalShift: Boolean = false,
    /** 后天转折描述 */
    val shiftDescription: String = "",
    /** 当前焦虑等级描述 */
    val anxietyDescription: String = ""
) {
    /** 是否有纾困意愿 */
    val hasReliefWillingness: Boolean get() =
        powerMode == PowerBehaviorMode.BENEVOLENT || eliteMode == EliteBehaviorMode.REFORMATIVE

    /** 焦虑等级 */
    val anxietyLevel: String get() = when {
        survivalAnxiety >= 80 -> "极重度"
        survivalAnxiety >= 60 -> "重度"
        survivalAnxiety >= 40 -> "中度"
        survivalAnxiety >= 20 -> "轻度"
        else -> "几乎无忧"
    }
}

// ============================================
// 四、后天转折事件
// ============================================

/**
 * 能够扭转阶层初始参数的后来经历事件
 */
data class PostnatalShiftEvent(
    val id: String,
    /** 朝向：正向（变温和/体恤）还是负向（变严苛/利己） */
    val isPositive: Boolean,
    /** 偏移量 */
    val shiftAmount: Int,
    /** 触发条件描述 */
    val triggerCondition: String,
    /** 事件描述 */
    val description: String,
    /** 小镇评述 */
    val townCommentary: String
)

object PostnatalShiftEvents {

    val events: List<PostnatalShiftEvent> = listOf(

        // ---- 正向转折：变得更为温和/体恤 ----
        PostnatalShiftEvent(
            id = "mentor_guidance",
            isPositive = true,
            shiftAmount = 10,
            triggerCondition = "青年阶段接触到良师引导，受到人文关怀教育。",
            description = "你遇到了一位真正关心你的导师。他教你的不只是知识——是看待世界的方式。你开始理解：权力除了保护自己，还可以保护别人。",
            townCommentary = "一次善意的引导，可能改变一个人一生的方向。环境决定了你的起点，但有人拉你一把的时候——你选择了接受。"
        ),
        PostnatalShiftEvent(
            id = "witness_suffering",
            isPositive = true,
            shiftAmount = 15,
            triggerCondition = "亲眼见证底层群体的真实困境。",
            description = "你亲眼看到了那些和你父母当年一样挣扎的人。你站在了他们的位置，想起了自己的来路。你第一次认真思考：如果我有能力改变什么？",
            townCommentary = "看见苦难，不等于就会行动。但你选择了共情——这是后天环境给你的选择，不是出身决定的。"
        ),
        PostnatalShiftEvent(
            id = "long_term_security",
            isPositive = true,
            shiftAmount = 8,
            triggerCondition = "长期安稳执政，物质安全感充足，焦虑逐步消解。",
            description = "你在权力的位置上坐得够久了，不再担心随时跌回贫困。安全感慢慢回来之后，你发现——自己不再需要靠强硬来保护什么了。",
            townCommentary = "匮乏带来的恐惧不是本性，是处境。处境改变了，人也会改变。焦虑的消解需要时间——你给了自己这个时间。"
        ),
        PostnatalShiftEvent(
            id = "grassroots_research",
            isPositive = true,
            shiftAmount = 12,
            triggerCondition = "深入基层调研，接触普通民众真实生活。",
            description = "你走出办公室，在工厂、菜市场、老旧小区里看到了一手的生活。数据是冷的，人情是热的。你的决策开始多了一层温度。",
            townCommentary = "圈层是认知盲区的根源——走出圈层，就是打破认知的开始。你选择了走出去。"
        ),

        // ---- 负向转折：变得更严苛/利己 ----
        PostnatalShiftEvent(
            id = "power_lost_threat",
            isPositive = false,
            shiftAmount = -10,
            triggerCondition = "权力受到威胁，面临被替换的风险。",
            description = "有人盯上了你的位置。你开始疑神疑鬼——每一个下属看起来都像潜在的竞争者。你收紧了对一切的控制，因为放松意味着失去。",
            townCommentary = "恐惧不是凭空出现的——它来自真实经历过的失去。但你选择用强硬回应恐惧，而不是去面对恐惧本身。"
        ),
        PostnatalShiftEvent(
            id = "betrayal_experience",
            isPositive = false,
            shiftAmount = -12,
            triggerCondition = "遭到圈层内部或合作伙伴背叛。",
            description = "你信任的人在背后捅了你一刀。这次经历让你彻底关闭了对人性的信任——从此之后，规则优先，人情靠后。",
            townCommentary = "背叛是一剂毒药——它让你把一个人的恶，当成了整个世界的常态。但你值得想一想：那把刀，能代表所有人吗？"
        ),
        PostnatalShiftEvent(
            id = "circle_pressure",
            isPositive = false,
            shiftAmount = -8,
            triggerCondition = "圈层内同辈压力，被迫参与排他性规则制定。",
            description = "你周围的人都在这样做——你不做，就会被排挤出局。你选择了同流——不是为了害谁，是不想被自己的圈层抛弃。",
            townCommentary = "妥协很多时候不是主动选择——是没有选择。但在「没有选择」和「不选择」之间，还是有区别的。"
        )
    )
}

// ============================================
// 五、阶层初始参数配置
// ============================================

/**
 * 阶层初始参数配置
 */
data class ClassBehaviorConfig(
    val background: ClassBackground,
    val initialSurvivalAnxiety: Int,
    val anxietyDescription: String,
    val defaultBehaviorDescription: String
)

object ClassBehaviorConfigs {

    private val configs = listOf(
        ClassBehaviorConfig(
            background = ClassBackground.UNDERPRIVILEGED,
            initialSurvivalAnxiety = 70,
            anxietyDescription = "资源一直处于争抢模式，一辈子习惯了「资源不够，必须抢到手」。退让就意味着失去保障。",
            defaultBehaviorDescription = "当终于获得权力位置后，内心深处极度害怕重新跌落回贫苦环境。容错空间极低——一次软弱就可能失去一切。"
        ),
        ClassBehaviorConfig(
            background = ClassBackground.AFFLUENT,
            initialSurvivalAnxiety = 15,
            anxietyDescription = "从小衣食无忧，不用争抢基础生存物资，不必为三餐房租焦虑。日常友善来自物质安全感。",
            defaultBehaviorDescription = "无关自身核心利益时待人和善。但这份温和存在边界——触及家产、圈层特权时，大概率会维护固有利益。"
        )
    )

    /** 根据出身获取配置 */
    fun getConfig(background: ClassBackground): ClassBehaviorConfig {
        return configs.first { it.background == background }
    }

    /** 获取底层出身默认行为模式（按概率分布） */
    fun rollPoorPowerMode(): PowerBehaviorMode {
        val roll = Math.random()
        return when {
            roll < 0.65 -> PowerBehaviorMode.HARSH_CONTROL
            roll < 0.90 -> PowerBehaviorMode.MODERATE
            else -> PowerBehaviorMode.BENEVOLENT
        }
    }

    /** 获取富裕出身默认行为模式（按概率分布） */
    fun rollEliteMode(): EliteBehaviorMode {
        val roll = Math.random()
        return when {
            roll < 0.70 -> EliteBehaviorMode.MILD_MAINTAIN
            roll < 0.85 -> EliteBehaviorMode.REFORMATIVE
            else -> EliteBehaviorMode.ARROGANT
        }
    }

    /** 根据后天偏移量调整行为模式 */
    fun adjustModeByPostnatalShift(
        currentPowerMode: PowerBehaviorMode,
        postnatalModifier: Int
    ): PowerBehaviorMode {
        if (postnatalModifier >= 15) return PowerBehaviorMode.BENEVOLENT
        if (postnatalModifier >= 5) {
            return if (currentPowerMode == PowerBehaviorMode.HARSH_CONTROL) PowerBehaviorMode.MODERATE
            else currentPowerMode
        }
        if (postnatalModifier <= -10) return PowerBehaviorMode.HARSH_CONTROL
        return currentPowerMode
    }

    /** 根据后天偏移量调整富裕模式 */
    fun adjustEliteModeByPostnatalShift(
        currentMode: EliteBehaviorMode,
        postnatalModifier: Int
    ): EliteBehaviorMode {
        if (postnatalModifier >= 15) return EliteBehaviorMode.REFORMATIVE
        if (postnatalModifier <= -10) return EliteBehaviorMode.ARROGANT
        return currentMode
    }
}