package com.example.townapp.domain

import com.example.townapp.data.*
import com.example.townapp.data.model.WeatherState
import kotlin.random.Random

/**
 * 公共事件调度器
 *
 * 管理庙会、演唱会、世界杯、追星、音乐节、城市节庆等大众群体性公共休闲事件。
 *
 * 分层权重：
 * - 平民档位：82%，主体消费人群
 * - 中产档位：16%，攒钱体验
 * - 富豪VIP档位：2%，极少数人选择
 *
 * 核心原则：
 * - 世界杯期间，不管你在出租屋用手机看，还是在VIP包厢看现场，那一刻你和全世界一起屏住呼吸
 * - 不强制参与，不制造攀比，基础体验人人平等
 */
object PublicEventScheduler {

    // ============================================
    // 分层档位权重
    // ============================================
    private const val COMMON_TIER_WEIGHT = 0.82
    private const val MIDDLE_TIER_WEIGHT = 0.16
    private const val RICH_TIER_WEIGHT = 0.02

    // ============================================
    // 权重调节参数
    // ============================================
    private const val NORMAL_PUBLIC_EVENT_WEIGHT = 0.20   // 平时公共事件权重20%
    private const val MAJOR_EVENT_WEIGHT_BOOST = 0.35     // 大型事件期间权重上调至35%

    // ============================================
    // 触发结果
    // ============================================
    data class PublicEventResult(
        val event: PublicEvent,
        val selectedTier: PublicEventTier,
        val actualCost: Double,
        val reason: String,
        val outfitScene: OutfitScene,
        val isMajorEvent: Boolean  // 是否是大型事件（世界杯、庙会等）
    )

    // ============================================
    // 主调度入口
    // ============================================

    /**
     * 检查当前月份是否有活跃的公共事件，并尝试触发
     *
     * @param month 当前月份（1-12）
     * @param year 当前年份
     * @param weather 当前天气
     * @param age 角色年龄
     * @param fatigue 当前疲惫值
     * @param savings 当前存款
     * @param monthlyIncome 月收入
     * @return 触发结果，null 表示当前无活跃公共事件或玩家选择不参与
     */
    fun checkAndTrigger(
        month: Int,
        year: Int,
        weather: WeatherState,
        age: Int,
        fatigue: Int,
        savings: Double,
        monthlyIncome: Double
    ): PublicEventResult? {
        // Step 1: 获取当前月份活跃的公共事件
        val activeEvents = PublicEventLibrary.getActiveEvents(month, year)
        if (activeEvents.isEmpty()) return null

        // Step 2: 判断是否有大型事件（世界杯、庙会、烟花节）
        val majorEvents = activeEvents.filter {
            it.type in setOf(PublicEventType.WORLD_CUP, PublicEventType.TEMPLE_FAIR, PublicEventType.FIREWORK_SHOW)
        }
        val isMajorPeriod = majorEvents.isNotEmpty()

        // Step 3: 权重调整 — 大型事件期间权重上调
        val triggerChance = if (isMajorPeriod) MAJOR_EVENT_WEIGHT_BOOST else NORMAL_PUBLIC_EVENT_WEIGHT
        if (Random.nextDouble() > triggerChance) return null

        // Step 4: 从活跃事件中按权重选取
        val selectedEvent = pickEvent(activeEvents, isMajorPeriod)

        // Step 5: 过滤：天气、年龄、疲惫
        if (weather in selectedEvent.weatherBlocked) return null
        if (age < selectedEvent.minAge || age > selectedEvent.maxAge) return null
        if (fatigue > 80) return null  // 太累了不参与任何公共活动

        // Step 6: 根据阶层和储蓄选择消费档位
        val tier = selectTier(selectedEvent, savings, monthlyIncome)
        val cost = Random.nextDouble(tier.costMin, tier.costMax)

        return PublicEventResult(
            event = selectedEvent,
            selectedTier = tier,
            actualCost = kotlin.math.round(cost * 100) / 100,
            reason = buildTriggerReason(selectedEvent, tier, isMajorPeriod),
            outfitScene = tier.outfitScene,
            isMajorEvent = isMajorPeriod
        )
    }

    // ============================================
    // 事件选取
    // ============================================
    private fun pickEvent(events: List<PublicEvent>, isMajorPeriod: Boolean): PublicEvent {
        // 大型事件期间，大型事件权重更高
        val weights = events.map { e ->
            if (isMajorPeriod && e.type in setOf(
                    PublicEventType.WORLD_CUP, PublicEventType.TEMPLE_FAIR, PublicEventType.FIREWORK_SHOW
                )) {
                e.activeWeight * 2.0
            } else {
                e.activeWeight
            }
        }
        val totalWeight = weights.sum()
        var roll = Random.nextDouble() * totalWeight
        for ((i, event) in events.withIndex()) {
            roll -= weights[i]
            if (roll <= 0) return event
        }
        return events.last()
    }

    // ============================================
    // 消费档位选择
    // ============================================
    private fun selectTier(
        event: PublicEvent,
        savings: Double,
        monthlyIncome: Double
    ): PublicEventTier {
        val availableTiers = event.tiers.filter { (_, tier) ->
            savings >= tier.savingsThreshold && monthlyIncome >= tier.monthlyIncomeThreshold
        }

        if (availableTiers.isEmpty()) {
            // 如果连平民档位都不满足（储蓄为负等极端情况），返回平民档
            return event.tiers[LeisureTier.COMMON]
                ?: event.tiers.values.first()
        }

        // 按权重分层随机选择
        val roll = Random.nextDouble()
        return when {
            // 富豪档：2%概率，且需要储蓄达标
            roll < RICH_TIER_WEIGHT && availableTiers.containsKey(LeisureTier.RICH) ->
                availableTiers[LeisureTier.RICH]!!
            // 中产档：16%概率
            roll < RICH_TIER_WEIGHT + MIDDLE_TIER_WEIGHT && availableTiers.containsKey(LeisureTier.MIDDLE) ->
                availableTiers[LeisureTier.MIDDLE]!!
            // 平民档：82%概率
            availableTiers.containsKey(LeisureTier.COMMON) ->
                availableTiers[LeisureTier.COMMON]!!
            else -> availableTiers.values.first()
        }
    }

    private fun buildTriggerReason(
        event: PublicEvent,
        tier: PublicEventTier,
        isMajor: Boolean
    ): String = when {
        isMajor -> "${event.name}正在进行中。你选择了${tier.tier.label}档位体验。"
        else -> "城市里正在举办${event.name}。${tier.description}"
    }

    // ============================================
    // 赛季模式：世界杯期间持续触发
    // ============================================

    /**
     * 检查当前是否处于世界杯赛季
     */
    fun isWorldCupSeason(month: Int, year: Int): Boolean {
        return (year - PublicEventLibrary.WORLD_CUP.cycleYearOffset) % 4 == 0
                && month in PublicEventLibrary.WORLD_CUP.months
    }

    /**
     * 世界杯赛季期间，每周触发一次观赛事件
     * （与checkAndTrigger不同，这个是赛季持续期间的定期触发）
     */
    fun getWorldCupWeeklyEvent(
        weather: WeatherState,
        age: Int,
        fatigue: Int,
        savings: Double,
        monthlyIncome: Double,
        teamWon: Boolean? = null  // null=没有主队比赛, true=主队赢了, false=主队输了
    ): PublicEventResult? {
        val event = PublicEventLibrary.WORLD_CUP
        if (weather in event.weatherBlocked) return null
        if (age < event.minAge) return null
        if (fatigue > 85) return null  // 极度疲惫才会放弃看球

        val tier = selectTier(event, savings, monthlyIncome)
        val cost = Random.nextDouble(tier.costMin, tier.costMax)

        val reason = when (teamWon) {
            true -> "主队赢了！你和全场球迷一起欢呼——今晚注定睡不着了。"
            false -> "主队输了……你坐在那里，半天没说话。但没关系，四年后还有机会。"
            null -> "又一场世界杯比赛。你打开直播，准备好了零食。"
        }

        return PublicEventResult(
            event = event,
            selectedTier = tier,
            actualCost = kotlin.math.round(cost * 100) / 100,
            reason = reason,
            outfitScene = tier.outfitScene,
            isMajorEvent = true
        )
    }

    // ============================================
    // 追星路径：理性 vs 狂热
    // ============================================

    enum class FanStyle { RATIONAL, FANATIC }

    /**
     * 触发追星事件，根据玩家追星风格返回不同结果
     */
    fun getFanEvent(
        style: FanStyle,
        savings: Double,
        monthlyIncome: Double
    ): PublicEventResult? {
        val event = PublicEventLibrary.FAN_IDOL

        val tier = when (style) {
            FanStyle.RATIONAL -> {
                // 理性追星：只选平民档
                event.tiers[LeisureTier.COMMON] ?: return null
            }
            FanStyle.FANATIC -> {
                // 狂热追星：倾向中产档，可能透支
                if (savings >= 500.0) {
                    event.tiers[LeisureTier.MIDDLE] ?: event.tiers[LeisureTier.COMMON]!!
                } else {
                    // 储蓄不足仍强行消费 → 透支
                    event.tiers[LeisureTier.COMMON]!!
                }
            }
        }

        val cost = when (style) {
            FanStyle.RATIONAL -> Random.nextDouble(20.0, 100.0)
            FanStyle.FANATIC -> Random.nextDouble(200.0, 800.0)
        }

        return PublicEventResult(
            event = event,
            selectedTier = tier,
            actualCost = kotlin.math.round(cost * 100) / 100,
            reason = when (style) {
                FanStyle.RATIONAL -> "一年看一场，刚刚好。你在同好群里认识了新朋友——这就是追星的意义。"
                FanStyle.FANATIC -> "你又买了一张票。储蓄在减少，但你觉得——不趁年轻追，老了就来不及了。"
            },
            outfitScene = tier.outfitScene,
            isMajorEvent = false
        )
    }

    // ============================================
    // 查询统计
    // ============================================

    /** 获取当前活跃的公共事件列表 */
    fun getActiveEvents(month: Int, year: Int): List<PublicEvent> =
        PublicEventLibrary.getActiveEvents(month, year)

    /** 获取所有公共事件 */
    fun getAllEvents(): List<PublicEvent> = PublicEventLibrary.ALL_EVENTS

    /** 按类型获取 */
    fun getEventByType(type: PublicEventType): PublicEvent? =
        PublicEventLibrary.getByType(type)
}