package com.example.townapp.data

/**
 * 性别系统（v2.13 性别-生理-社会环境差异体系）
 *
 * 核心设计理念：
 * 1. 实事求是：正视男女天然生理差异，不回避也不夸大
 * 2. 分层管理：生理先天参数（不可更改）+ 社会环境差异（时代可调整）
 * 3. 刻板印象破除：婚恋双向选择、职场无先天智力门槛、心理无固化标签
 * 4. 自由选择：所有职业、生活方式对两性完全开放，个人抉择优先级高于群体概率
 *
 * 三大板块联动：
 *   生理层面（月度周期/体能基线/生育影响）→ 身体状态微调
 *   婚恋层面（追求概率/彩礼压力/骚扰风险）→ 社交意愿+焦虑
 *   职场层面（准入偏见/生育断层/政策改革）→ 职业发展+储蓄
 *
 * 世界观定位：
 *   女性生理期痛经、产后体虚、职场断层——是现实存在的困境，不回避。
 *   男性彩礼经济压力、情绪压抑——同样是现实困境，不轻视。
 *   两种困境形式不同，但没有优劣之分。
 *   阶层、时代对人的影响权重，整体高于性别差异。
 */

// ============================================
// 一、性别枚举
// ============================================

/**
 * 生理性别
 */
enum class BiologicalSex(val label: String, val description: String) {
    MALE("男",
        "基础体能均值更高，但后天营养、锻炼可大幅改变个体差异。青春期激素波动偏冲动。"
    ),
    FEMALE("女",
        "青春期起经历生理期月度周期，生育承担更多身体代价。体能基线略低但个体差异远超性别差异。"
    )
}

// ============================================
// 二、生理固有参数
// ============================================

/**
 * 性别相关生理基线
 * 仅影响初始参数，后天环境可大幅修正
 */
data class GenderPhysiology(
    val sex: BiologicalSex,
    /** 基础体能基线 0-100（男性略高，但锻炼可追平） */
    val baseStamina: Int,
    /** 体力劳动疲劳衰减速度系数（越低越快恢复） */
    val fatigueDecayRate: Double,
    /** 冲动情绪触发概率修正（男性青春期偏高） */
    val impulsivityModifier: Double,
    /** 共情基线（女性略高，但个体差异大） */
    val empathyBaseline: Int,
    /** 是否有生理期周期（女性专属） */
    val hasMenstrualCycle: Boolean,
    /** 是否会经历生育（女性专属） */
    val canExperienceChildbirth: Boolean
) {
    companion object {
        fun forSex(sex: BiologicalSex): GenderPhysiology = when (sex) {
            BiologicalSex.MALE -> GenderPhysiology(
                sex = sex, baseStamina = 65, fatigueDecayRate = 0.7,
                impulsivityModifier = 0.06, empathyBaseline = 45,
                hasMenstrualCycle = false, canExperienceChildbirth = false
            )
            BiologicalSex.FEMALE -> GenderPhysiology(
                sex = sex, baseStamina = 55, fatigueDecayRate = 0.6,
                impulsivityModifier = 0.02, empathyBaseline = 55,
                hasMenstrualCycle = true, canExperienceChildbirth = true
            )
        }
    }
}

// ============================================
// 三、生理期-月度周期系统
// ============================================

/**
 * 痛经程度
 */
enum class PeriodPainLevel(val label: String, val severity: Int, val fatigueBonus: Int, val anxietyBonus: Int) {
    MILD("轻度", 1, fatigueBonus = 2, anxietyBonus = 1),
    MODERATE("中度", 2, fatigueBonus = 5, anxietyBonus = 3),
    SEVERE("重度", 3, fatigueBonus = 8, anxietyBonus = 6)
}

/**
 * 生理期周期状态
 */
data class MenstrualCycleState(
    /** 当前天数（1-28天周期） */
    val currentDay: Int = 1,
    /** 周期总长度（天） */
    val cycleLength: Int = 28,
    /** 经期持续天数 */
    val bleedingDays: Int = 5,
    /** 痛经程度 */
    val painLevel: PeriodPainLevel = PeriodPainLevel.MILD,
    /** 是否处于经期 */
    val isMenstruating: Boolean = false,
    /** 是否处于排卵期（情绪波动偏高） */
    val isOvulating: Boolean = false,
    /** 是否使用了止痛药 */
    val usedPainkiller: Boolean = false,
    /** 是否饮用了红糖姜茶 */
    val usedBrownSugarGinger: Boolean = false
) {
    /** 是否处于疼痛状态 */
    val isInPain: Boolean get() = isMenstruating && painLevel.severity >= 2

    /** 当日体能上限削减 */
    fun staminaPenalty(): Int = if (isMenstruating) painLevel.fatigueBonus else 0

    /** 当日专注度下降（影响学习/工作效率） */
    fun focusPenalty(): Double = when {
        isMenstruating && painLevel == PeriodPainLevel.SEVERE -> 0.15
        isMenstruating && painLevel == PeriodPainLevel.MODERATE -> 0.08
        else -> 0.0
    }

    /** 当日焦虑加成 */
    fun anxietyBonus(): Int = if (isMenstruating) painLevel.anxietyBonus else 0

    /** 独处逃避行为触发概率提升 */
    fun withdrawalBonus(): Double = when {
        isMenstruating && painLevel == PeriodPainLevel.SEVERE -> 0.08
        isMenstruating && painLevel == PeriodPainLevel.MODERATE -> 0.03
        else -> 0.0
    }
}

/**
 * 生理期月度调度
 */
object MenstrualCycleSystem {

    /**
     * 每日推进周期
     */
    fun tick(state: MenstrualCycleState): MenstrualCycleState {
        val newDay = state.currentDay + 1
        val isNewCycle = newDay > state.cycleLength
        val day = if (isNewCycle) 1 else newDay

        // 痛经程度有概率在青春期后随机调整（重度可能在某个周期出现）
        val newPain = if (isNewCycle && Math.random() < 0.15) {
            val roll = Math.random()
            when {
                roll < 0.5 -> PeriodPainLevel.MILD
                roll < 0.85 -> PeriodPainLevel.MODERATE
                else -> PeriodPainLevel.SEVERE
            }
        } else state.painLevel

        val isMenstruating = day <= state.bleedingDays
        val isOvulating = day in 12..16

        return state.copy(
            currentDay = day,
            painLevel = newPain,
            isMenstruating = isMenstruating,
            isOvulating = isOvulating,
            usedPainkiller = false,
            usedBrownSugarGinger = false
        )
    }

    /**
     * 使用止痛药缓解痛经
     */
    fun usePainkiller(state: MenstrualCycleState): MenstrualCycleState {
        return state.copy(usedPainkiller = true)
    }

    /**
     * 饮用红糖姜茶
     */
    fun useBrownSugarGinger(state: MenstrualCycleState): MenstrualCycleState {
        return state.copy(usedBrownSugarGinger = true)
    }

    /**
     * 计算生理期当月总开销（药品+调理物资）
     */
    fun monthlyMenstrualCost(
        state: MenstrualCycleState,
        hasSufficientSupplies: Boolean
    ): Double {
        if (!state.isMenstruating) return 0.0
        var cost = 0.0
        if (state.usedPainkiller) cost += DrugIngredient.IBUPROFEN_PAIN.priceTier * 5.0
        if (state.usedBrownSugarGinger) cost += 3.0
        // 物资充足则额外调理开销
        if (hasSufficientSupplies && state.painLevel.severity >= 2) cost += 15.0
        return cost
    }
}

// ============================================
// 四、生育-孕产系统
// ============================================

/**
 * 孕产阶段
 */
enum class ChildbirthPhase(val label: String) {
    NONE("未孕"),
    PREGNANT("孕期"),
    POSTPARTUM("产后恢复"),
    RECOVERED("已恢复")
}

/**
 * 孕产状态
 */
data class ChildbirthState(
    val phase: ChildbirthPhase = ChildbirthPhase.NONE,
    /** 孕期剩余天数 */
    val pregnancyDaysRemaining: Int = 0,
    /** 产后恢复剩余天数 */
    val postpartumDaysRemaining: Int = 0,
    /** 产后物资是否充足 */
    val hasPostpartumSupplies: Boolean = false,
    /** 是否留下了慢性体虚病根 */
    val hasChronicWeakness: Boolean = false
) {
    /** 当前体能折损系数 */
    fun staminaReduction(): Double = when (phase) {
        ChildbirthPhase.NONE -> 0.0
        ChildbirthPhase.PREGNANT -> 0.25
        ChildbirthPhase.POSTPARTUM -> 0.35
        ChildbirthPhase.RECOVERED -> if (hasChronicWeakness) 0.05 else 0.0
    }

    /** 工作效率折损 */
    fun workEfficiencyPenalty(): Double = when (phase) {
        ChildbirthPhase.NONE -> 0.0
        ChildbirthPhase.PREGNANT -> 0.20
        ChildbirthPhase.POSTPARTUM -> 0.40
        ChildbirthPhase.RECOVERED -> 0.0
    }
}

/**
 * 孕产系统
 */
object ChildbirthSystem {

    val PREGNANCY_DURATION = 270  // 孕期约9个月（270天）
    val POSTPARTUM_DURATION = 90  // 产后恢复约3个月（90天）

    /**
     * 每日推进孕产状态
     */
    fun tick(state: ChildbirthState): ChildbirthState {
        return when (state.phase) {
            ChildbirthPhase.NONE -> state
            ChildbirthPhase.PREGNANT -> {
                val remaining = state.pregnancyDaysRemaining - 1
                if (remaining <= 0) {
                    state.copy(
                        phase = ChildbirthPhase.POSTPARTUM,
                        pregnancyDaysRemaining = 0,
                        postpartumDaysRemaining = POSTPARTUM_DURATION
                    )
                } else {
                    state.copy(pregnancyDaysRemaining = remaining)
                }
            }
            ChildbirthPhase.POSTPARTUM -> {
                val remaining = state.postpartumDaysRemaining - 1
                if (remaining <= 0) {
                    // 物资匮乏 → 病根
                    val chronic = !state.hasPostpartumSupplies && Math.random() < 0.4
                    state.copy(
                        phase = ChildbirthPhase.RECOVERED,
                        postpartumDaysRemaining = 0,
                        hasChronicWeakness = chronic
                    )
                } else {
                    state.copy(postpartumDaysRemaining = remaining)
                }
            }
            ChildbirthPhase.RECOVERED -> state
        }
    }
}

// ============================================
// 五、婚恋-社会年代参数
// ============================================

/**
 * 年代社会风气（影响婚恋追求概率）
 */
enum class EraSocialNorm(val label: String, val description: String) {
    TRADITIONAL("传统时代",
        "社会风气偏保守——男性主动占主导，女性主动比例偏低。彩礼、婚房压力大。"
    ),
    MODERN("现代原子化社会",
        "婚恋观念开放——主动追求趋于均衡，相亲、线上社交模式增多。个人选择权更大。"
    )
}

/**
 * 婚恋追求概率模型
 */
data class CourtshipProbability(
    val era: EraSocialNorm,
    /** 男性主动发起追求概率 */
    val maleActiveRate: Double,
    /** 女性主动发起追求概率 */
    val femaleActiveRate: Double,
    /** 相亲/社交被动结识概率 */
    val arrangedRate: Double,
    /** 女性遭遇骚扰/过度纠缠的概率 */
    val femaleHarassmentRate: Double,
    /** 男性承受彩礼/婚房经济压力的概率 */
    val maleFinancialPressureRate: Double
) {
    companion object {
        fun forEra(era: EraSocialNorm): CourtshipProbability = when (era) {
            EraSocialNorm.TRADITIONAL -> CourtshipProbability(
                era = era, maleActiveRate = 0.65, femaleActiveRate = 0.22,
                arrangedRate = 0.13, femaleHarassmentRate = 0.08,
                maleFinancialPressureRate = 0.70
            )
            EraSocialNorm.MODERN -> CourtshipProbability(
                era = era, maleActiveRate = 0.52, femaleActiveRate = 0.44,
                arrangedRate = 0.04, femaleHarassmentRate = 0.05,
                maleFinancialPressureRate = 0.45
            )
        }
    }
}

// ============================================
// 六、职场-社会环境差异
// ============================================

/**
 * 职场性别偏见等级（可随政策改革下降）
 */
enum class WorkplaceGenderBias(val label: String, val biasLevel: Double, val description: String) {
    HIGH("高偏见", 0.25,
        "部分高薪体力和高层岗位女性入职概率偏低25%。社会偏见明确存在但非绝对。"
    ),
    MODERATE("中等偏见", 0.12,
        "反歧视政策推行后偏见减弱——但隐性门槛仍存。"
    ),
    LOW("低偏见", 0.05,
        "岗位准入概率基本持平，仅个别传统行业残留微弱差异。"
    ),
    NONE("无偏见", 0.0,
        "完全基于能力考核，性别不再是录用考量因素。"
    )
}

/**
 * 职场生育断层影响
 */
data class CareerChildbirthImpact(
    /** 是否被迫中断职业 */
    val careerInterrupted: Boolean,
    /** 中断月数 */
    val interruptionMonths: Int = 0,
    /** 回归职场后薪资折损比例 */
    val salaryReductionRate: Double = 0.0,
    /** 晋升延迟年数 */
    val promotionDelayYears: Int = 0
)

/**
 * 职业准入性别影响
 */
object CareerGenderSystem {

    /**
     * 检查某岗位是否因性别偏见降低准入概率
     * @param isHighPayPhysical 是否高薪体力岗位
     * @param isSeniorPublic 是否高层公职
     * @param bias 当前社会偏见等级
     * @param candidateSex 候选者性别
     */
    fun accessBiasModifier(
        isHighPayPhysical: Boolean,
        isSeniorPublic: Boolean,
        bias: WorkplaceGenderBias,
        candidateSex: BiologicalSex
    ): Double {
        if (candidateSex != BiologicalSex.FEMALE) return 1.0
        if (bias == WorkplaceGenderBias.NONE) return 1.0
        if (!isHighPayPhysical && !isSeniorPublic) return 1.0

        // 偏见递减公式：高偏见全减 → 中偏见减半 → 低偏见减四分之一
        return when (bias) {
            WorkplaceGenderBias.HIGH -> 1.0 - bias.biasLevel
            WorkplaceGenderBias.MODERATE -> 1.0 - bias.biasLevel
            WorkplaceGenderBias.LOW -> 1.0 - bias.biasLevel
            WorkplaceGenderBias.NONE -> 1.0
        }
    }

    /**
     * 计算生育带来的职业中断
     */
    fun calculateChildbirthImpact(
        hasChild: Boolean,
        sex: BiologicalSex,
        bias: WorkplaceGenderBias
    ): CareerChildbirthImpact {
        if (sex != BiologicalSex.FEMALE || !hasChild) {
            return CareerChildbirthImpact(careerInterrupted = false)
        }
        val months = (6..18).random()
        val salaryReduction = if (bias.biasLevel > 0.1) 0.08 else 0.03
        val promotionDelay = if (bias.biasLevel > 0.15) (1..3).random() else 0

        return CareerChildbirthImpact(
            careerInterrupted = true,
            interruptionMonths = months,
            salaryReductionRate = salaryReduction,
            promotionDelayYears = promotionDelay
        )
    }
}

// ============================================
// 七、性别相关随机事件
// ============================================

/**
 * 生理期事件
 */
data class MenstrualEvent(
    val id: String,
    val title: String,
    val description: String,
    val fatigueDelta: Int = 0,
    val anxietyDelta: Int = 0,
    val withdrawalBonus: Double = 0.0,
    val townCommentary: String
)

object MenstrualEvents {
    val events: List<MenstrualEvent> = listOf(
        MenstrualEvent(
            id = "mild_cramps",
            title = "隐隐的疼",
            description = "小腹有点闷闷的疼——不算剧烈，但像有人在肚子上压了一小块石头。你深呼吸了一下——还能扛。",
            fatigueDelta = 2, anxietyDelta = 1,
            townCommentary = "月经不是病——但每个月那几天，身体的感受是真实的。红糖水、暖宝宝、一个安静的地方——你值得这些。"
        ),
        MenstrualEvent(
            id = "severe_cramps",
            title = "疼得不想动",
            description = "一阵绞痛从小腹蔓延开来——你趴在桌上不想动。今天的工作你勉强完成了——但每一分钟都比平时累。你的身体在告诉你：我今天很辛苦。",
            fatigueDelta = 8, anxietyDelta = 6, withdrawalBonus = 0.05,
            townCommentary = "重度痛经不是'娇气'——是身体在用它能发出的最大声音告诉你：我在疼。吃片止痛药——不是妥协，是你在照顾自己的身体。"
        ),
        MenstrualEvent(
            id = "period_fatigue",
            title = "说不清的疲倦",
            description = "不是疼——就是整个人有点没劲。做什么都觉得比平时多花了一点力气。你知道这不是懒——是身体在忙着它自己的事情。",
            fatigueDelta = 4, anxietyDelta = 2,
            townCommentary = "月经期间的身体在加班——造血、排废、收缩——这些是你眼睛看不见的劳动。别苛责自己今天效率差——你的身体已经在替你做好几份工作了。"
        )
    )
}

/**
 * 婚恋性别事件
 */
data class GenderCourtshipEvent(
    val id: String,
    val title: String,
    val description: String,
    val targetSex: BiologicalSex,
    val anxietyDelta: Int = 0,
    val socialWillDelta: Int = 0,
    val identityDelta: Int = 0,
    val financialPressure: Double = 0.0,
    val townCommentary: String
)

object GenderCourtshipEvents {
    val events: List<GenderCourtshipEvent> = listOf(
        GenderCourtshipEvent(
            id = "harassment_encounter",
            title = "不舒服的目光",
            description = "回家的路上，一个陌生人的目光在你身上停留了很久——不是善意的。你加快脚步走开了——但你发现自己的手在微微发抖。你觉得不对劲——但又说不清哪里不对。",
            targetSex = BiologicalSex.FEMALE,
            anxietyDelta = 5, socialWillDelta = -3,
            townCommentary = "不是你穿得不对——是那个目光不对。警告别人的眼神不是你的责任——但你受伤了，这却是事实。这种事不是你的错——从来不是。"
        ),
        GenderCourtshipEvent(
            id = "dowry_pressure",
            title = "彩礼的账",
            description = "你开始看房了——不是因为你想买房，是因为有人告诉你：没房就很难结婚。你算了一笔账——首付加彩礼加装修——这个数字让你在工位上沉默了很久。你只是在谈恋爱——为什么开始为一座你没住的房子焦虑？",
            targetSex = BiologicalSex.MALE,
            anxietyDelta = 6, socialWillDelta = -2, financialPressure = 200000.0,
            townCommentary = "彩礼本身没有对错——是你被一套和你无关的账单硬拉到婚姻面前。不是爱情贵——是这个社会给男性开了一张你还没收到的账单。"
        ),
        GenderCourtshipEvent(
            id = "male_heartbreak",
            title = "被拒绝了",
            description = "你鼓了很久的勇气，终于说出了那句话。对方沉默了三秒——然后说了「抱歉」。你笑着说没关系——但回到房间后你坐在黑暗里，很久没动。不是愤怒——是一种被掏空了的感觉。",
            targetSex = BiologicalSex.MALE,
            anxietyDelta = 4, identityDelta = -3, socialWillDelta = -3,
            townCommentary = "感情不是交易——但被拒绝的那一刻，很多人都会偷偷怀疑是不是自己不够好。不是——只是两条路没交叉。但你的难过是真的。"
        ),
        GenderCourtshipEvent(
            id = "female_active_pursuit",
            title = "你先开口了",
            description = "你决定不等了——你主动约他喝了杯咖啡。开口的那一刻你的心怦怦跳——但说出来之后反而轻松了。不管他怎么回答——你今天做了一件了不起的事：你为自己想要的东西主动出了手。",
            targetSex = BiologicalSex.FEMALE,
            anxietyDelta = -2, identityDelta = 4, socialWillDelta = 2,
            townCommentary = "谁说只能等？你喜欢一个人——这是你自己的事。主动不是掉价——是你在肯定自己的欲望和勇气。"
        )
    )
}

/**
 * 职场性别事件
 */
data class GenderCareerEvent(
    val id: String,
    val title: String,
    val description: String,
    val targetSex: BiologicalSex,
    val anxietyDelta: Int = 0,
    val identityDelta: Int = 0,
    val beliefDelta: Int = 0,
    val townCommentary: String
)

object GenderCareerEvents {
    val events: List<GenderCareerEvent> = listOf(
        GenderCareerEvent(
            id = "interview_bias",
            title = "面试之后",
            description = "你完成了面试——表现不差。但面试官问了那个问题：「你近两年有生育计划吗？」你回答了——但这句话像一根细小的刺，嵌进了你的自信心里。你的能力——在这个问题面前好像不够重。",
            targetSex = BiologicalSex.FEMALE,
            anxietyDelta = 4, identityDelta = -2,
            townCommentary = "不是你不够好——是有人在用和你的能力完全无关的问题，衡量你的价值。这个问题本身才是问题。"
        ),
        GenderCareerEvent(
            id = "career_break",
            title = "断档的一年",
            description = "你休完产假回来——座位还在，但项目换了，你之前对接的人也已经和别人配合了半年。你没做错任何事——但一切都得「重新回来」。你花了三个月才让同事重新把你当成原来的你——而不是'刚回来的那个妈妈'。",
            targetSex = BiologicalSex.FEMALE,
            anxietyDelta = 5, identityDelta = -3, beliefDelta = -2,
            townCommentary = "育儿的代价——社会让你一个人背了。不是孩子拖累了你——是这个职场的结构没给当你妈妈之后留位置。这不公平——你知道，但没人替你改。"
        ),
        GenderCareerEvent(
            id = "emotional_suppression",
            title = "不能说",
            description = "下班后你在车里坐了很久——不是不想回家，是那些堵在胸口的东西不知道怎么拿出来。你是男的——身边所有人都在告诉你「不要哭、不要抱怨、不要软」。你学会了把所有东西咽下去——但咽下去的东西不会消失——它们在胃里变成了石头。",
            targetSex = BiologicalSex.MALE,
            anxietyDelta = 3, identityDelta = -2,
            townCommentary = "你是一个人——不是一块石头。可以累，可以哭，可以坐在车里发十分钟呆。社会告诉你男的不能软——但社会错了。"
        )
    )
}

// ============================================
// 八、心理差异参数（群体趋势，个体可突破）
// ============================================

/**
 * 性别心理趋势参数
 * 仅描述群体统计趋势，不固化个体
 */
data class GenderPsychTendency(
    val sex: BiologicalSex,
    /** 生存苦难共情基线 */
    val sufferingEmpathy: Int,
    /** 情绪延迟宣泄倾向（越高=越压抑、越延迟） */
    val emotionalDelayTendency: Double,
    /** 深度迷茫触发阈值（长期压抑后） */
    val deepConfusionThreshold: Int
) {
    companion object {
        fun forSex(sex: BiologicalSex): GenderPsychTendency = when (sex) {
            BiologicalSex.MALE -> GenderPsychTendency(
                sex = sex, sufferingEmpathy = 40,
                emotionalDelayTendency = 0.6, deepConfusionThreshold = 70
            )
            BiologicalSex.FEMALE -> GenderPsychTendency(
                sex = sex, sufferingEmpathy = 60,
                emotionalDelayTendency = 0.3, deepConfusionThreshold = 80
            )
        }
    }
}

// ============================================
// 九、性别运行时状态
// ============================================

/**
 * 性别系统运行时综合状态
 */
data class GenderRuntimeState(
    val sex: BiologicalSex,
    val physiology: GenderPhysiology = GenderPhysiology.forSex(BiologicalSex.MALE),
    /** 生理期周期（女性专属，男性为空状态） */
    val menstrualCycle: MenstrualCycleState? = null,
    /** 孕产状态 */
    val childbirth: ChildbirthState = ChildbirthState(),
    /** 心理趋势 */
    val psychTendency: GenderPsychTendency = GenderPsychTendency.forSex(BiologicalSex.MALE),
    /** 当前社会环境年代 */
    val eraNorm: EraSocialNorm = EraSocialNorm.TRADITIONAL,
    /** 职场偏见等级 */
    val workplaceBias: WorkplaceGenderBias = WorkplaceGenderBias.HIGH,
    /** 是否选择了终身独身 */
    val hasChosenCelibacy: Boolean = false,
    /** 是否选择了丁克 */
    val hasChosenDINK: Boolean = false,
    /** 是否选择了晚婚路线 */
    val hasChosenLateMarriage: Boolean = false,
    /** 累计情绪压抑值 */
    val accumulatedSuppression: Int = 0
) {
    companion object {
        fun forSex(sex: BiologicalSex): GenderRuntimeState {
            val cycle = if (sex == BiologicalSex.FEMALE) {
                MenstrualCycleState()
            } else null
            return GenderRuntimeState(
                sex = sex,
                physiology = GenderPhysiology.forSex(sex),
                menstrualCycle = cycle,
                psychTendency = GenderPsychTendency.forSex(sex)
            )
        }
    }
}