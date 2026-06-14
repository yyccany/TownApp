package com.example.townapp.domain

import com.example.townapp.data.cognition.CognitionReflection
import com.example.townapp.data.cognition.CognitionReflectionLibrary
import kotlin.random.Random

/**
 * 成语‑抉择联动引擎
 *
 * 核心逻辑：同一个随机事件，不同角色因为原生家境、已解锁认知条目、
 * 消费观念、过往决策历史的不同，会想起不同的俗语，做出不同的选择。
 *
 * 小镇不评判任何一种倾向——保守、均衡、进取，都是合理的人生路径。
 * 引擎只负责呈现「这个角色更可能倾向哪条路」，玩家可以自由反向选择。
 *
 * ## 四大底层变量
 * 1. CharacterBackground  — 原生家庭与长辈观念（童年预置参数）
 * 2. unlockedReflectionIds — 已解锁的认知反思条目（阅历迭代）
 * 3. SpendingPhilosophy    — 个人消费‑生活观念
 * 4. DecisionHistory       — 过往经历（历史事件记忆）
 *
 * ## 输出
 * - DecisionTendency：当前角色倾向（保守 / 均衡 / 进取）
 * - CandidateIdioms：当前事件适配的 2-3 条候选俗语
 */
object DecisionPreferenceEngine {

    // ============================================
    // 基础枚举
    // ============================================

    /** 原生家庭背景 */
    enum class CharacterBackground(val label: String) {
        CONSERVATIVE_FAMILY("保守工薪家庭"),
        MIDDLE_FAMILY("中产开明家庭"),
        WEALTHY_FAMILY("经商富裕家庭")
    }

    /** 个人消费‑生活观念 */
    enum class SpendingPhilosophy(val label: String) {
        THRIFTY("爱惜节俭型"),
        BALANCED("均衡务实型"),
        CASUAL("随性消耗型")
    }

    /** 决策倾向 */
    enum class DecisionTendency(val label: String) {
        CONSERVATIVE("稳妥保守"),
        BALANCED("均衡折中"),
        AGGRESSIVE("激进进取")
    }

    /** 单条决策历史记录 */
    data class DecisionRecord(
        val eventType: String,
        val idiomId: String,
        val choiceId: String,
        val outcome: String,           // "positive" / "negative" / "neutral"
        val age: Int,
        val timestamp: Long = System.currentTimeMillis()
    )

    /** 决策历史上下文 */
    data class DecisionHistory(
        val records: List<DecisionRecord> = emptyList(),
        /** 连续保守决策次数 */
        val consecutiveConservative: Int = 0,
        /** 连续进取决策次数 */
        val consecutiveAggressive: Int = 0,
        /** 保守决策后负面结果次数（用于触发反思） */
        val conservativeBadOutcomes: Int = 0,
        /** 进取决策后正面结果次数（用于强化进取倾向） */
        val aggressiveGoodOutcomes: Int = 0
    )

    /** 决策上下文 —— 聚合四大变量 */
    data class DecisionContext(
        val age: Int,
        val background: CharacterBackground,
        val spending: SpendingPhilosophy,
        val unlockedReflectionIds: Set<String>,
        val history: DecisionHistory
    )

    /** 候选俗语结果 */
    data class CandidateIdioms(
        val tendency: DecisionTendency,
        val entries: List<CognitionReflection>,
        val tendencyReason: String,
        val tendencyDescription: String
    )

    /** 一条事件‑俗语映射 */
    data class EventIdiomMapping(
        val eventType: String,
        val idiomIds: List<String>,
        val tendency: DecisionTendency
    )

    // ============================================
    // 事件‑候选成语映射池
    // ============================================

    /**
     * 同一事件 → 多条备选俗语，每条绑定决策倾向
     *
     * 新增事件时只需在此处添加映射即可。
     */
    private val eventIdiomPool: Map<LifeEventType, List<EventIdiomMapping>> = mapOf(

        // —— 职场竞争 / 被排挤 ——
        LifeEventType.CAREER_COMPETITION to listOf(
            EventIdiomMapping("职场竞争/被排挤", listOf("nail_that_sticks_out"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("职场竞争/被排挤", listOf("stay_in_your_lane"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("职场竞争/被排挤", listOf("rainbow_after_rain"), DecisionTendency.BALANCED),
            EventIdiomMapping("职场竞争/被排挤", listOf("go_with_the_flow"), DecisionTendency.BALANCED),
            EventIdiomMapping("职场竞争/被排挤", listOf("mind_your_own_business"), DecisionTendency.AGGRESSIVE)
        ),

        // —— 失业 / 事业受挫 ——
        LifeEventType.CAREER_FRUSTRATION to listOf(
            EventIdiomMapping("失业/事业受挫", listOf("rainbow_after_rain"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("失业/事业受挫", listOf("contentment_is_happiness"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("失业/事业受挫", listOf("stay_in_your_lane"), DecisionTendency.BALANCED),
            EventIdiomMapping("失业/事业受挫", listOf("go_with_the_flow"), DecisionTendency.AGGRESSIVE)
        ),

        // —— 经济压力 / 财务焦虑 ——
        LifeEventType.FINANCIAL_STRESS to listOf(
            EventIdiomMapping("经济压力", listOf("contentment_is_happiness"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("经济压力", listOf("prepare_for_rain"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("经济压力", listOf("simplify_life"), DecisionTendency.BALANCED),
            EventIdiomMapping("经济压力", listOf("suffering_is_blessing"), DecisionTendency.BALANCED)
        ),

        // —— 家庭争吵 / 亲子矛盾 ——
        LifeEventType.FAMILY_CONFLICT to listOf(
            EventIdiomMapping("家庭争吵", listOf("family_harmony"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("家庭争吵", listOf("for_the_greater_good"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("家庭争吵", listOf("for_the_greater_good"), DecisionTendency.BALANCED),
            EventIdiomMapping("家庭争吵", listOf("children_own_fortune"), DecisionTendency.BALANCED),
            EventIdiomMapping("家庭争吵", listOf("selfless_devotion"), DecisionTendency.AGGRESSIVE)
        ),

        // —— 社交冲突 / 被排挤 / 被算计 ——
        LifeEventType.SOCIAL_CONFLICT to listOf(
            EventIdiomMapping("社交冲突", listOf("near_vermilion"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("社交冲突", listOf("mind_your_own_business"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("社交冲突", listOf("good_people_suffer"), DecisionTendency.BALANCED),
            EventIdiomMapping("社交冲突", listOf("suffering_is_blessing"), DecisionTendency.BALANCED),
            EventIdiomMapping("社交冲突", listOf("selfless_devotion"), DecisionTendency.AGGRESSIVE)
        ),

        // —— 被过度索取 / 身心透支 ——
        LifeEventType.OVEREXPLOITATION to listOf(
            EventIdiomMapping("被过度索取", listOf("selfless_devotion"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("被过度索取", listOf("for_the_greater_good"), DecisionTendency.BALANCED),
            EventIdiomMapping("被过度索取", listOf("good_people_suffer"), DecisionTendency.AGGRESSIVE)
        ),

        // —— 退休 / 年龄焦虑 ——
        LifeEventType.RETIREMENT_ANXIETY to listOf(
            EventIdiomMapping("退休焦虑", listOf("enjoy_retirement"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("退休焦虑", listOf("hold_on_to_the_past"), DecisionTendency.BALANCED),
            EventIdiomMapping("退休焦虑", listOf("joy_in_solitude"), DecisionTendency.AGGRESSIVE)
        ),

        // —— 孤独 / 社交压力 ——
        LifeEventType.LONELINESS to listOf(
            EventIdiomMapping("孤独感", listOf("joy_in_solitude"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("孤独感", listOf("go_with_the_flow"), DecisionTendency.BALANCED),
            EventIdiomMapping("孤独感", listOf("near_vermilion"), DecisionTendency.AGGRESSIVE)
        ),

        // —— 学习 / 进步焦虑 ——
        LifeEventType.EDUCATION_ANXIETY to listOf(
            EventIdiomMapping("学习焦虑", listOf("reading_is_supreme"), DecisionTendency.CONSERVATIVE),
            EventIdiomMapping("学习焦虑", listOf("stay_in_your_lane"), DecisionTendency.BALANCED),
            EventIdiomMapping("学习焦虑", listOf("prepare_for_rain"), DecisionTendency.AGGRESSIVE)
        )
    )

    /** 事件类型枚举 —— 与 LifeEventScene 对应但更细粒度 */
    enum class LifeEventType(val label: String) {
        CAREER_COMPETITION("职场竞争/被排挤"),
        CAREER_FRUSTRATION("失业/事业受挫"),
        FINANCIAL_STRESS("经济压力/财务焦虑"),
        FAMILY_CONFLICT("家庭争吵/亲子矛盾"),
        SOCIAL_CONFLICT("社交冲突/被排挤/被算计"),
        OVEREXPLOITATION("被过度索取/身心透支"),
        RETIREMENT_ANXIETY("退休焦虑/年龄压力"),
        LONELINESS("孤独感/社交压力"),
        EDUCATION_ANXIETY("学习/进步焦虑")
    }

    // ============================================
    // 决策倾向计算
    // ============================================

    /**
     * 根据四大变量计算角色的当前决策倾向
     *
     * 权重分配：
     * - 原生家庭背景：30%
     * - 已解锁认知条目（阅历）：25%
     * - 消费观念：25%
     * - 过往决策历史：20%
     */
    fun calculateTendency(ctx: DecisionContext): DecisionTendency {
        var conservativeScore = 0f
        var aggressiveScore = 0f

        // 1. 原生家庭背景（权重 30%）
        when (ctx.background) {
            CharacterBackground.CONSERVATIVE_FAMILY -> conservativeScore += 30f
            CharacterBackground.MIDDLE_FAMILY -> { /* 均衡，不加不减 */ }
            CharacterBackground.WEALTHY_FAMILY -> aggressiveScore += 30f
        }

        // 2. 已解锁认知条目（权重 25%）
        // 解锁的反思条目越多，越倾向于打破传统，走向进取/均衡
        val reflectionCount = ctx.unlockedReflectionIds.size
        if (reflectionCount >= 10) {
            // 大量反思条目 → 不再盲目遵从传统
            aggressiveScore += 15f
        } else if (reflectionCount >= 5) {
            aggressiveScore += 8f
            conservativeScore -= 5f
        } else if (reflectionCount >= 2) {
            aggressiveScore += 3f
        }

        // 3. 消费观念（权重 25%）
        when (ctx.spending) {
            SpendingPhilosophy.THRIFTY -> conservativeScore += 25f
            SpendingPhilosophy.BALANCED -> { /* 均衡，不加不减 */ }
            SpendingPhilosophy.CASUAL -> aggressiveScore += 25f
        }

        // 4. 过往决策历史（权重 20%）
        // 连续保守+负面结果 → 反思，倾向进取
        if (ctx.history.conservativeBadOutcomes >= 2) {
            aggressiveScore += 12f
            conservativeScore -= 8f
        }
        // 连续进取+正面结果 → 强化进取
        if (ctx.history.aggressiveGoodOutcomes >= 2) {
            aggressiveScore += 8f
        }
        // 连续保守决策 → 惯性
        if (ctx.history.consecutiveConservative >= 3) {
            conservativeScore += 5f
        }
        // 连续进取决策 → 惯性
        if (ctx.history.consecutiveAggressive >= 3) {
            aggressiveScore += 5f
        }

        // 计算差值
        val netScore = aggressiveScore - conservativeScore

        return when {
            netScore > 10f -> DecisionTendency.AGGRESSIVE
            netScore < -10f -> DecisionTendency.CONSERVATIVE
            else -> DecisionTendency.BALANCED
        }
    }

    /**
     * 生成倾向原因说明（用于UI展示）
     */
    fun describeTendency(ctx: DecisionContext): String {
        val tendency = calculateTendency(ctx)
        val parts = mutableListOf<String>()

        when (ctx.background) {
            CharacterBackground.CONSERVATIVE_FAMILY -> parts.add("出身${ctx.background.label}，长辈常教导稳妥为上")
            CharacterBackground.MIDDLE_FAMILY -> parts.add("在${ctx.background.label}长大，习惯权衡利弊")
            CharacterBackground.WEALTHY_FAMILY -> parts.add("来自${ctx.background.label}，不惧打破常规")
        }

        when (ctx.spending) {
            SpendingPhilosophy.THRIFTY -> parts.add("性格${ctx.spending.label}，偏向保守稳妥")
            SpendingPhilosophy.BALANCED -> parts.add("性格${ctx.spending.label}，懂得取舍")
            SpendingPhilosophy.CASUAL -> parts.add("性格${ctx.spending.label}，不太受传统束缚")
        }

        val count = ctx.unlockedReflectionIds.size
        when {
            count >= 10 -> parts.add("已解锁${count}条认知反思，阅历丰富，不会被老话困住")
            count >= 5 -> parts.add("已解锁${count}条认知反思，开始看清俗语的时代局限")
            count >= 2 -> parts.add("正在解锁认知反思，观念开始松动")
        }

        if (ctx.history.conservativeBadOutcomes >= 2) {
            parts.add("之前隐忍退让吃了亏，这次不想再让步")
        }
        if (ctx.history.aggressiveGoodOutcomes >= 2) {
            parts.add("之前勇敢争取获得了回报，更愿意继续进取")
        }

        return when (tendency) {
            DecisionTendency.CONSERVATIVE -> "倾向稳妥保守。" + parts.take(3).joinToString("；") + "。"
            DecisionTendency.BALANCED -> "倾向均衡折中。" + parts.take(3).joinToString("；") + "。"
            DecisionTendency.AGGRESSIVE -> "倾向积极进取。" + parts.take(3).joinToString("；") + "。"
        }
    }

    // ============================================
    // 候选俗语筛选
    // ============================================

    /**
     * 根据事件类型和角色上下文，筛选 2-3 条候选俗语
     *
     * 筛选策略：
     * 1. 优先匹配角色倾向一致的俗语（70% 概率）
     * 2. 加入一条倾向不同的俗语（30% 概率），保留选择空间
     * 3. 过滤掉年龄不够的条目
     * 4. 过滤掉已解锁（已触发过选择）的认知反思条目
     *
     * @return 候选俗语列表，包含 2-3 条
     */
    fun getCandidateIdioms(
        eventType: LifeEventType,
        ctx: DecisionContext
    ): CandidateIdioms {
        val tendency = calculateTendency(ctx)
        val mappings = eventIdiomPool[eventType] ?: return CandidateIdioms(
            tendency = tendency,
            entries = emptyList(),
            tendencyReason = "暂无可选俗语",
            tendencyDescription = describeTendency(ctx)
        )

        // 收集所有符合条件的俗语条目
        val allCandidates = mappings.flatMap { mapping ->
            mapping.idiomIds.mapNotNull { id ->
                CognitionReflectionLibrary.getById(id)
            }.filter { entry ->
                // 年龄门槛
                ctx.age >= entry.minAge
            }
        }.distinctBy { it.id }

        if (allCandidates.isEmpty()) return CandidateIdioms(
            tendency = tendency,
            entries = emptyList(),
            tendencyReason = "年龄未达到任何俗语的解锁门槛",
            tendencyDescription = describeTendency(ctx)
        )

        // 按倾向分组
        val tendencyGroups = allCandidates.groupBy { entry ->
            mappings.firstOrNull { m -> m.idiomIds.contains(entry.id) }?.tendency
                ?: DecisionTendency.BALANCED
        }

        val results = mutableListOf<CognitionReflection>()

        // 1. 优先加入与角色倾向一致的俗语（1-2条）
        val matchingEntries = tendencyGroups[tendency] ?: emptyList()
        if (matchingEntries.isNotEmpty()) {
            val count = if (matchingEntries.size >= 2) 2 else 1
            results.addAll(matchingEntries.shuffled().take(count))
        }

        // 2. 加入一条倾向不同的俗语（保留选择空间）
        val otherTendencies = tendencyGroups.filterKeys { it != tendency }
        val otherEntries = otherTendencies.values.flatten().filter { it !in results }
        if (otherEntries.isNotEmpty()) {
            results.add(otherEntries.random())
        }

        // 3. 如果还不够 2 条，从匹配组中补充
        if (results.size < 2 && matchingEntries.size > results.size) {
            val remaining = matchingEntries.filter { it !in results }
            results.addAll(remaining.shuffled().take(1))
        }

        // 4. 如果还不够，从所有候选中补充
        if (results.size < 2) {
            val remaining = allCandidates.filter { it !in results }
            results.addAll(remaining.shuffled().take(2 - results.size))
        }

        return CandidateIdioms(
            tendency = tendency,
            entries = results.take(3),
            tendencyReason = describeTendency(ctx),
            tendencyDescription = describeTendency(ctx)
        )
    }

    // ============================================
    // 决策历史更新
    // ============================================

    /**
     * 记录一次决策后，更新历史统计
     */
    fun updateHistory(
        history: DecisionHistory,
        choice: DecisionRecord
    ): DecisionHistory {
        val newRecords = (history.records + choice).takeLast(20) // 保留最近20条

        // 统计连续保守次数
        val consecutiveCon = if (choice.idiomId in CONSERVATIVE_IDIOMS) {
            history.consecutiveConservative + 1
        } else 0

        // 统计连续进取次数
        val consecutiveAgg = if (choice.idiomId in AGGRESSIVE_IDIOMS) {
            history.consecutiveAggressive + 1
        } else 0

        // 保守决策后负面结果
        val badOutcomes = if (choice.idiomId in CONSERVATIVE_IDIOMS && choice.outcome == "negative") {
            history.conservativeBadOutcomes + 1
        } else if (choice.idiomId !in CONSERVATIVE_IDIOMS) {
            0 // 重置
        } else history.conservativeBadOutcomes

        // 进取决策后正面结果
        val goodOutcomes = if (choice.idiomId in AGGRESSIVE_IDIOMS && choice.outcome == "positive") {
            history.aggressiveGoodOutcomes + 1
        } else if (choice.idiomId !in AGGRESSIVE_IDIOMS) {
            0
        } else history.aggressiveGoodOutcomes

        return DecisionHistory(
            records = newRecords,
            consecutiveConservative = consecutiveCon,
            consecutiveAggressive = consecutiveAgg,
            conservativeBadOutcomes = badOutcomes,
            aggressiveGoodOutcomes = goodOutcomes
        )
    }

    // 保守倾向俗语 ID 集合
    private val CONSERVATIVE_IDIOMS = setOf(
        "nail_that_sticks_out", "contentment_is_happiness", "family_harmony",
        "near_vermilion", "stay_in_your_lane", "prepare_for_rain",
        "selfless_devotion", "for_the_greater_good", "suffering_is_blessing",
        "rainbow_after_rain", "enjoy_retirement", "joy_in_solitude",
        "hold_on_to_the_past", "mind_your_own_business"
    )

    // 进取倾向俗语 ID 集合
    private val AGGRESSIVE_IDIOMS = setOf(
        "good_people_suffer", "reading_is_supreme", "children_own_fortune",
        "go_with_the_flow", "simplify_life"
    )

    /**
     * 从 LifeEventScene 转换到 LifeEventType
     */
    fun sceneToEventType(scene: com.example.townapp.data.repository.IdiomSceneMapper.LifeEventScene): LifeEventType? {
        return when (scene) {
            com.example.townapp.data.repository.IdiomSceneMapper.LifeEventScene.CAREER_FRUSTRATION -> LifeEventType.CAREER_FRUSTRATION
            com.example.townapp.data.repository.IdiomSceneMapper.LifeEventScene.FINANCIAL_STRESS -> LifeEventType.FINANCIAL_STRESS
            com.example.townapp.data.repository.IdiomSceneMapper.LifeEventScene.SOCIAL_CONFLICT -> LifeEventType.SOCIAL_CONFLICT
            com.example.townapp.data.repository.IdiomSceneMapper.LifeEventScene.HEALTH_ANXIETY -> LifeEventType.OVEREXPLOITATION
            com.example.townapp.data.repository.IdiomSceneMapper.LifeEventScene.GENERAL_ADVICE -> LifeEventType.FAMILY_CONFLICT
            com.example.townapp.data.repository.IdiomSceneMapper.LifeEventScene.MARRIAGE_PRESSURE -> LifeEventType.SOCIAL_CONFLICT
            com.example.townapp.data.repository.IdiomSceneMapper.LifeEventScene.APPEARANCE_ANXIETY -> LifeEventType.LONELINESS
        }
    }
}