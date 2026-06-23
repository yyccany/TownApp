package com.example.townapp.domain

import com.example.townapp.data.*
import com.example.townapp.data.model.WeatherState
import kotlin.random.Random

/**
 * 休闲事件管理器
 *
 * 分层触发规则：
 * - 平民娱乐：触发权重80%，日常高频，绝大多数人的绝大多数时间
 * - 中产娱乐：触发权重15%，需储蓄达标，一年可选2-3次
 * - 富豪娱乐：触发权重5%，普通人生仅1-2次体验机会
 *
 * 核心原则：
 * - 散步和游轮，都是一个人选择让自己好过一点的方式
 * - 不评判任何休闲方式，不制造攀比焦虑
 * - 平民娱乐足够调节身心，不需要高端度假才能解压
 */
object LeisureManager {

    // ============================================
    // 分层权重分配
    // ============================================
    private const val COMMON_WEIGHT = 0.80
    private const val MIDDLE_WEIGHT = 0.15
    private const val RICH_WEIGHT = 0.05

    // ============================================
    // 触发结果
    // ============================================
    data class LeisureResult(
        val event: LeisureEvent,
        val actualCost: Double,
        val reason: String,
        val outfitScene: OutfitScene
    )

    // ============================================
    // 主触发入口
    // ============================================

    /**
     * 根据当前角色状态，从休闲事件库中随机选取一个合适的活动
     *
     * @param category 当前时段（工作日晚间/周末/节假日/季节性）
     * @param weather 当前天气
     * @param season 当前季节
     * @param age 角色年龄
     * @param fatigue 当前疲惫值（0-100）
     * @param savings 当前存款
     * @param monthlyIncome 月收入
     * @return 选取结果，null 表示当前无合适活动（如太累了什么都不想做）
     */
    fun selectLeisure(
        category: LeisureCategory,
        weather: WeatherState,
        season: Season,
        age: Int,
        fatigue: Int,
        savings: Double,
        monthlyIncome: Double
    ): LeisureResult? {
        // Step 1: 按权重分层，随机决定从哪个阶层选取
        val tier = rollTier()

        // Step 2: 从对应阶层筛选符合条件的活动
        val candidates = filterCandidates(
            tier = tier,
            category = category,
            weather = weather,
            season = season,
            age = age,
            fatigue = fatigue,
            savings = savings,
            monthlyIncome = monthlyIncome
        )

        // Step 3: 如果当前阶层无合适活动，降级重试
        val finalCandidates = if (candidates.isEmpty()) {
            when (tier) {
                LeisureTier.RICH -> filterCandidates(LeisureTier.MIDDLE, category, weather, season, age, fatigue, savings, monthlyIncome)
                LeisureTier.MIDDLE -> filterCandidates(LeisureTier.COMMON, category, weather, season, age, fatigue, savings, monthlyIncome)
                LeisureTier.COMMON -> emptyList()  // 平民也无可选 → 在家休息
            }
        } else {
            candidates
        }

        if (finalCandidates.isEmpty()) return null

        // Step 4: 从候选中按权重随机选取
        val event = weightedPick(finalCandidates)
        val cost = Random.nextDouble(event.effects.costMin, event.effects.costMax)

        return LeisureResult(
            event = event,
            actualCost = kotlin.math.round(cost * 100) / 100,
            reason = buildReason(event, tier),
            outfitScene = event.outfitScene ?: OutfitScene.COMMUTE
        )
    }

    // ============================================
    // 分层权重掷骰
    // ============================================
    private fun rollTier(): LeisureTier {
        val roll = Random.nextDouble()
        return when {
            roll < COMMON_WEIGHT -> LeisureTier.COMMON
            roll < COMMON_WEIGHT + MIDDLE_WEIGHT -> LeisureTier.MIDDLE
            else -> LeisureTier.RICH
        }
    }

    // ============================================
    // 筛选逻辑
    // ============================================
    private fun filterCandidates(
        tier: LeisureTier,
        category: LeisureCategory,
        weather: WeatherState,
        season: Season,
        age: Int,
        fatigue: Int,
        savings: Double,
        monthlyIncome: Double
    ): List<LeisureEvent> {
        return LeisureLibrary.getByTier(tier)
            .filter { it.category == category }
            .filter { it.season == null || it.season == season }
            .filter { weather !in it.weatherBlocked }
            .filter { age >= it.minAge }
            .filter { fatigue <= it.fatigueThreshold }
            .filter { savings >= it.savingsThreshold }
            .filter { monthlyIncome >= it.monthlyIncomeThreshold }
    }

    private fun weightedPick(candidates: List<LeisureEvent>): LeisureEvent {
        val totalWeight = candidates.sumOf { it.triggerWeight }
        var roll = Random.nextDouble() * totalWeight
        for (event in candidates) {
            roll -= event.triggerWeight
            if (roll <= 0) return event
        }
        return candidates.last()
    }

    private fun buildReason(event: LeisureEvent, tier: LeisureTier): String = when (tier) {
        LeisureTier.COMMON -> "今天是平常的一天，选择了「${event.name}」"
        LeisureTier.MIDDLE -> "攒了一段时间的钱，决定犒劳一下自己——「${event.name}」"
        LeisureTier.RICH -> "难得的体验——「${event.name}」"
    }

    // ============================================
    // 特殊场景触发
    // ============================================

    /**
     * 获取工作日晚间休闲（受通勤疲惫值影响）
     */
    fun getWeekdayEveningLeisure(
        weather: WeatherState,
        season: Season,
        age: Int,
        fatigue: Int,
        savings: Double,
        monthlyIncome: Double
    ): LeisureResult? {
        return selectLeisure(
            category = LeisureCategory.WEEKDAY_EVENING,
            weather = weather,
            season = season,
            age = age,
            fatigue = fatigue,
            savings = savings,
            monthlyIncome = monthlyIncome
        )
    }

    /**
     * 获取周末休闲
     */
    fun getWeekendLeisure(
        weather: WeatherState,
        season: Season,
        age: Int,
        fatigue: Int,
        savings: Double,
        monthlyIncome: Double
    ): LeisureResult? {
        return selectLeisure(
            category = LeisureCategory.WEEKEND,
            weather = weather,
            season = season,
            age = age,
            fatigue = fatigue,
            savings = savings,
            monthlyIncome = monthlyIncome
        )
    }

    /**
     * 获取小长假休闲
     */
    fun getHolidayLeisure(
        weather: WeatherState,
        season: Season,
        age: Int,
        fatigue: Int,
        savings: Double,
        monthlyIncome: Double
    ): LeisureResult? {
        return selectLeisure(
            category = LeisureCategory.SHORT_HOLIDAY,
            weather = weather,
            season = season,
            age = age,
            fatigue = fatigue,
            savings = savings,
            monthlyIncome = monthlyIncome
        )
    }

    /**
     * 获取季节性休闲
     */
    fun getSeasonalLeisure(
        weather: WeatherState,
        season: Season,
        age: Int,
        fatigue: Int,
        savings: Double,
        monthlyIncome: Double
    ): LeisureResult? {
        return selectLeisure(
            category = LeisureCategory.SEASONAL,
            weather = weather,
            season = season,
            age = age,
            fatigue = fatigue,
            savings = savings,
            monthlyIncome = monthlyIncome
        )
    }

    // ============================================
    // 查询统计
    // ============================================

    /** 获取各阶层休闲事件数量 */
    fun getTierDistribution(): Map<LeisureTier, Int> = LeisureTier.entries.associateWith {
        LeisureLibrary.getByTier(it).size
    }

    /** 获取指定时段的所有休闲事件 */
    fun getEventsByCategory(category: LeisureCategory): List<LeisureEvent> =
        LeisureLibrary.getByCategory(category)
}