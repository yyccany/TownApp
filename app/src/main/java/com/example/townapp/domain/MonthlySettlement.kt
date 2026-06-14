package com.example.townapp.domain

import com.example.townapp.data.LifeStage
import com.example.townapp.data.Season
import kotlin.random.Random

/**
 * 月度结算器（v2.14 时间系统重构）
 *
 * 月末执行整月全局结算，弹出月度复盘面板。处理：
 * ① 财务结算：发放月薪，扣除房租水电、食材基础开销、各类消费支出
 * ② 健康结算：评估当月慢性病、伤病进展，换季切换
 * ③ 重大人生事件池：裁员失业、升职加薪、婚恋关系变动等
 * ④ 年龄里程碑解锁
 * ⑤ 季节全局参数刷新
 *
 * 核心原则：
 * - 月度是主结算周期，界面核心展示单位
 * - 重大事件受概率补偿机制约束，不会连续崩盘
 * - 换季切换自然发生，不是"警告"
 */

// ============================================
// 月度结算结果
// ============================================
data class MonthlyStats(
    // 财务
    /** 本月收入（月薪） */
    val monthlyIncome: Double = 0.0,
    /** 本月房租 */
    val rent: Double = 0.0,
    /** 本月水电费 */
    val utilities: Double = 0.0,
    /** 本月食物总开销 */
    val foodCost: Double = 0.0,
    /** 本月消费支出 */
    val consumption: Double = 0.0,
    /** 本月医疗支出 */
    val medicalCost: Double = 0.0,
    /** 本月衣物修补/购置支出 */
    val clothingCost: Double = 0.0,
    /** 本月总支出 */
    val totalExpense: Double = 0.0,
    /** 本月结余 */
    val netBalance: Double = 0.0,
    /** 当前储蓄 */
    val currentSavings: Double = 0.0,

    // 健康
    /** 本月慢性病进展 */
    val chronicDiseaseProgress: Double = 0.0,
    /** 本月新增伤病 */
    val newInjuries: List<String> = emptyList(),
    /** 本月感冒次数 */
    val coldCount: Int = 0,
    /** 本月脚气发作 */
    val footFungusOutbreak: Boolean = false,

    // 人生事件
    /** 本月触发的重大事件 */
    val majorEvents: List<MonthlyEvent> = emptyList(),

    // 季节
    /** 是否换季 */
    val isSeasonChange: Boolean = false,
    /** 换季后的新季节 */
    val newSeason: Season? = null,

    // 总结
    /** 月度总结文案 */
    val summary: String = "",
    /** 是否需要弹窗展示 */
    val requiresPopup: Boolean = true
)

// ============================================
// 月度重大事件
// ============================================
data class MonthlyEvent(
    val id: String,
    val title: String,
    val description: String,
    val category: EventCategory,
    val impact: EventImpact,
    val townCommentary: String = ""
)

enum class EventCategory(val label: String) {
    POSITIVE("正向利好"),
    NEUTRAL("中性日常"),
    MILD_NEGATIVE("轻微负面"),
    SEVERE_NEGATIVE("重度负面")
}

data class EventImpact(
    val incomeDelta: Double = 0.0,
    val savingsDelta: Double = 0.0,
    val healthDelta: Int = 0,
    val anxietyDelta: Int = 0,
    val lonelinessDelta: Int = 0,
    val socialWillDelta: Int = 0,
    val identityDelta: Int = 0
)

// ============================================
// 月度结算引擎
// ============================================
object MonthlySettlement {

    /** 重度负面事件月度基础概率（7%-10%） */
    private const val SEVERE_NEGATIVE_BASE_PROBABILITY = 0.08

    /**
     * 执行月度全局结算
     *
     * @param monthlyIncome 月薪
     * @param rent 月租
     * @param currentSavings 当前储蓄
     * @param weeklyStatsList 本月4周的周度统计
     * @param month 当前月份
     * @param age 当前年龄
     * @param lifeStage 当前人生阶段
     * @param isSeasonChange 是否换季
     * @param consecutiveBadMonths 连续负面月份数（用于补偿机制）
     * @param consecutiveGoodMonths 连续正面月份数（用于制衡机制）
     * @param classBackground 家庭阶层（0=底层, 1=中层, 2=富裕）
     */
    fun settle(
        monthlyIncome: Double,
        rent: Double,
        currentSavings: Double,
        weeklyStatsList: List<WeeklyStats>,
        month: Int,
        age: Int,
        lifeStage: LifeStage,
        isSeasonChange: Boolean,
        consecutiveBadMonths: Int = 0,
        consecutiveGoodMonths: Int = 0,
        classBackground: Int = 1
    ): MonthlyStats {
        // ── ① 财务结算 ──
        val utilities = rent * 0.15  // 水电约为房租15%
        val totalFoodCost = weeklyStatsList.sumOf { it.totalFoodCost }
        val totalDepreciation = weeklyStatsList.sumOf { it.totalItemDepreciation }
        val clothingCost = weeklyStatsList.sumOf { it.totalItemDepreciation } * 0.3

        val totalExpense = rent + utilities + totalFoodCost + totalDepreciation + clothingCost
        val netBalance = monthlyIncome - totalExpense
        val newSavings = (currentSavings + netBalance).coerceAtLeast(0.0)

        // ── ② 健康结算 ──
        val coldCount = weeklyStatsList.count { it.accumulatedColdRisk > 0.2 }
        val footFungusOutbreak = weeklyStatsList.any { it.accumulatedFootFungusRisk > 0.5 }

        val chronicProgress = when (lifeStage) {
            LifeStage.SENIOR -> 0.03
            LifeStage.MIDDLE_AGE -> 0.015
            LifeStage.YOUTH -> 0.005
            else -> 0.0
        }

        // 换季病：换季月感冒概率提高
        val seasonChangeHealthPenalty = if (isSeasonChange) {
            if (Random.nextDouble() < 0.15) {
                listOf("换季温差波动——你感冒了。")
            } else emptyList()
        } else emptyList()

        // ── ③ 重大人生事件抽取 ──
        val events = drawMonthlyEvents(
            month = month,
            age = age,
            lifeStage = lifeStage,
            consecutiveBadMonths = consecutiveBadMonths,
            consecutiveGoodMonths = consecutiveGoodMonths,
            classBackground = classBackground
        )

        // ── ④ 季节切换 ──
        val newSeason = if (isSeasonChange) {
            TimeEngine.getSeasonFromMonth(month)
        } else null

        // ── ⑤ 生成总结 ──
        val summary = buildSummary(
            month = month,
            age = age,
            lifeStage = lifeStage,
            monthlyIncome = monthlyIncome,
            totalExpense = totalExpense,
            netBalance = netBalance,
            newSavings = newSavings,
            coldCount = coldCount,
            footFungusOutbreak = footFungusOutbreak,
            events = events,
            isSeasonChange = isSeasonChange,
            newSeason = newSeason,
            seasonChangeHealthPenalty = seasonChangeHealthPenalty
        )

        return MonthlyStats(
            monthlyIncome = monthlyIncome,
            rent = rent,
            utilities = utilities,
            foodCost = totalFoodCost,
            consumption = totalDepreciation,
            clothingCost = clothingCost,
            totalExpense = totalExpense,
            netBalance = netBalance,
            currentSavings = newSavings,
            chronicDiseaseProgress = chronicProgress,
            newInjuries = seasonChangeHealthPenalty,
            coldCount = coldCount,
            footFungusOutbreak = footFungusOutbreak,
            majorEvents = events,
            isSeasonChange = isSeasonChange,
            newSeason = newSeason,
            summary = summary
        )
    }

    /**
     * 月度重大事件抽取（加权伪随机 + 概率补偿）
     *
     * 规则：
     * - 重度负面事件基础概率 7-10%
     * - 连续2个月重度负面 → 下月强制压低重度负面概率
     * - 连续3个月正面 → 小幅拉高轻微负面概率
     * - 底层家庭失业概率略高，富裕家庭破产风险更高
     */
    private fun drawMonthlyEvents(
        month: Int,
        age: Int,
        lifeStage: LifeStage,
        consecutiveBadMonths: Int,
        consecutiveGoodMonths: Int,
        classBackground: Int
    ): List<MonthlyEvent> {
        val events = mutableListOf<MonthlyEvent>()

        // 概率补偿：连续厄运 → 强制好运
        val severeNegativeProb = when {
            consecutiveBadMonths >= 2 -> 0.02  // 补偿：大幅降低
            consecutiveGoodMonths >= 3 -> 0.12  // 制衡：小幅提高
            else -> SEVERE_NEGATIVE_BASE_PROBABILITY
        }

        val roll = Random.nextDouble()

        when {
            // 重度负面
            roll < severeNegativeProb -> {
                events.add(drawSevereNegativeEvent(age, lifeStage, classBackground))
            }
            // 轻微负面
            roll < severeNegativeProb + 0.25 -> {
                events.add(drawMildNegativeEvent(age, lifeStage))
            }
            // 正向利好
            roll < severeNegativeProb + 0.55 -> {
                events.add(drawPositiveEvent(age, lifeStage, classBackground))
            }
            // 中性日常
            else -> {
                events.add(drawNeutralEvent(age, lifeStage))
            }
        }

        // 青年阶段可能多一个事件
        if (lifeStage == LifeStage.YOUTH && Random.nextDouble() < 0.3) {
            events.add(drawNeutralEvent(age, lifeStage))
        }

        return events
    }

    private fun drawPositiveEvent(age: Int, stage: LifeStage, classBg: Int): MonthlyEvent {
        return when {
            stage == LifeStage.YOUTH -> listOf(
                MonthlyEvent("promotion", "升职了", "这个月的努力被看到了——你升职了。虽然不是大跨步，但工资涨了一点。", EventCategory.POSITIVE,
                    EventImpact(incomeDelta = 500.0, anxietyDelta = -2, identityDelta = 3),
                    "升职不是「你足够好」的证明——但多出来的工资是实在的。"),
                MonthlyEvent("side_hustle", "副业接单成功", "你上个月试着接的私活——这个月回款了。不多，但这是你靠自己额外赚到的。", EventCategory.POSITIVE,
                    EventImpact(savingsDelta = 800.0, anxietyDelta = -1, identityDelta = 2),
                    "副业不是必须的——但能靠自己多赚一点，是实实在在的底气。"),
                MonthlyEvent("new_connection", "结识了不错的人", "一次偶然的聚会——你认识了一个聊得来的人。不是那种'人脉'——就是单纯聊得来。", EventCategory.POSITIVE,
                    EventImpact(socialWillDelta = 3, lonelinessDelta = -2),
                    "好的关系不是资源——是让你觉得'世界上有人懂我'的那种安心。")
            ).random()
            stage == LifeStage.MIDDLE_AGE -> listOf(
                MonthlyEvent("promotion", "升职了", "中年升职——比年轻时更难，但分量也更重。", EventCategory.POSITIVE,
                    EventImpact(incomeDelta = 1500.0, anxietyDelta = -2, identityDelta = 4),
                    "中年升职不是运气——是你这些年攒下来的东西终于被看到了。"),
                MonthlyEvent("investment_return", "投资回本了", "几年前那笔你几乎忘了的投资——这个月突然有了回报。", EventCategory.POSITIVE,
                    EventImpact(savingsDelta = 3000.0, anxietyDelta = -3),
                    "有些种子种下去——要等很久才发芽。")
            ).random()
            else -> listOf(
                MonthlyEvent("family_gathering", "孩子回来看你了", "这个月孩子回了一趟家——虽然只是周末，但屋子里热闹了。", EventCategory.POSITIVE,
                    EventImpact(lonelinessDelta = -5, socialWillDelta = 3),
                    "人到晚年——最暖的不是钱，是屋子里有人说话。")
            ).random()
        }
    }

    private fun drawNeutralEvent(age: Int, stage: LifeStage): MonthlyEvent {
        return listOf(
            MonthlyEvent("neighbor_chat", "邻里闲谈", "楼下碰到邻居，聊了几句——没什么大事，但觉得这儿有人味。", EventCategory.NEUTRAL,
                EventImpact(socialWillDelta = 1, lonelinessDelta = -1)),
            MonthlyEvent("routine_checkup", "常规体检", "一年一次的体检——结果没什么大问题，但医生说了句'注意休息'。", EventCategory.NEUTRAL,
                EventImpact(healthDelta = 1)),
            MonthlyEvent("casual_shopping", "逛了逛街", "周末去商场逛了一圈——没买什么大件，但试了两件衣服。", EventCategory.NEUTRAL,
                EventImpact(savingsDelta = -50.0))
        ).random()
    }

    private fun drawMildNegativeEvent(age: Int, stage: LifeStage): MonthlyEvent {
        return listOf(
            MonthlyEvent("mild_cold", "感冒了", "换季的天气——你感冒了。不算严重，但头昏昏沉沉的，工作效率差了一截。", EventCategory.MILD_NEGATIVE,
                EventImpact(healthDelta = -5, anxietyDelta = 2),
                "感冒不是你的错——是天气的锅。好好休息，身体会自己好起来。"),
            MonthlyEvent("traffic_jam", "通勤堵车", "这个月的通勤格外堵——有几次差点迟到。每天都比平时多花了半小时在路上。", EventCategory.MILD_NEGATIVE,
                EventImpact(anxietyDelta = 2),
                "堵车不是你的问题——但堵在路上那种烦躁是真实的。"),
            MonthlyEvent("small_loss", "小东西丢了", "你发现钱包里少了几十块钱——不确定是掉了还是忘了。不是什么大事，但有点烦。", EventCategory.MILD_NEGATIVE,
                EventImpact(savingsDelta = -80.0, anxietyDelta = 1))
        ).random()
    }

    private fun drawSevereNegativeEvent(age: Int, stage: LifeStage, classBg: Int): MonthlyEvent {
        val pool = mutableListOf<MonthlyEvent>()

        // 通用重度负面
        pool.add(MonthlyEvent("major_illness", "生病了", "这次不是普通感冒——你病得不轻。需要休息一段时间，医疗开销也不小。", EventCategory.SEVERE_NEGATIVE,
            EventImpact(healthDelta = -15, savingsDelta = -2000.0, anxietyDelta = 5),
            "生病不是你的错——身体有时候就是会出问题。好好治疗，别硬扛。"))

        // 阶层相关
        if (classBg == 0) {
            // 底层：失业概率更高
            pool.add(MonthlyEvent("layoff_low", "被裁员了", "公司裁员——你的名字在名单上。没有预警，没有理由。下个月开始，收入归零。", EventCategory.SEVERE_NEGATIVE,
                EventImpact(incomeDelta = -9999.0, anxietyDelta = 8, identityDelta = -5),
                "裁员不是你的问题——是公司的问题。但账单不会等人。这不是你的失败——是这个系统让一个人背了太多。"))
        }
        if (classBg == 2) {
            // 富裕：破产风险
            pool.add(MonthlyEvent("investment_loss", "投资亏损", "你投的那笔钱——这个月大幅缩水。不是小数目——是你几年的积蓄。", EventCategory.SEVERE_NEGATIVE,
                EventImpact(savingsDelta = -50000.0, anxietyDelta = 6, identityDelta = -3),
                "投资有风险——这句话在你亏钱的时候一点都不安慰人。但你不是第一个，也不会是最后一个。"))
        }

        pool.add(MonthlyEvent("family_crisis", "家里出事了", "你接到电话——家里有人生病住院了。你需要回去一趟，这个月的计划全部打乱。", EventCategory.SEVERE_NEGATIVE,
            EventImpact(savingsDelta = -5000.0, anxietyDelta = 7, lonelinessDelta = 3),
            "家里有事——不是你的错，但你必须扛。这种感觉——很重。"))

        return pool.random()
    }

    /**
     * 生成月度总结文案
     */
    private fun buildSummary(
        month: Int,
        age: Int,
        lifeStage: LifeStage,
        monthlyIncome: Double,
        totalExpense: Double,
        netBalance: Double,
        newSavings: Double,
        coldCount: Int,
        footFungusOutbreak: Boolean,
        events: List<MonthlyEvent>,
        isSeasonChange: Boolean,
        newSeason: Season?,
        seasonChangeHealthPenalty: List<String>
    ): String = buildString {
        append("${month}月 · ${age}岁 · ${lifeStage.label}\n")
        append("━━━━━━━━━━━━━━━━\n")
        append("收入：¥${"%.0f".format(monthlyIncome)} | 支出：¥${"%.0f".format(totalExpense)}\n")
        if (netBalance >= 0) {
            append("结余：+¥${"%.0f".format(netBalance)} | 储蓄：¥${"%.0f".format(newSavings)}\n")
        } else {
            append("亏损：-¥${"%.0f".format(-netBalance)} | 储蓄：¥${"%.0f".format(newSavings)}\n")
        }
        append("━━━━━━━━━━━━━━━━\n")

        if (isSeasonChange && newSeason != null) {
            append("${newSeason.label}季到了。")
            if (seasonChangeHealthPenalty.isNotEmpty()) {
                append("${seasonChangeHealthPenalty.first()}\n")
            } else {
                append("\n")
            }
        }

        if (coldCount > 0) {
            append("本月感冒${coldCount}次——注意保暖。\n")
        }
        if (footFungusOutbreak) {
            append("脚气发作——检查鞋袜透气性，及时用药。\n")
        }

        events.forEach { event ->
            append("── ${event.title} ──\n")
            append("${event.description}\n")
        }
    }
}