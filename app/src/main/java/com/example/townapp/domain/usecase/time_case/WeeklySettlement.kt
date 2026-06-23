package com.example.townapp.domain

import com.example.townapp.data.Season
import com.example.townapp.data.model.WeatherState

/**
 * 周度结算器（v2.14 时间系统重构）
 *
 * 每周六为抉择节点。汇总本周7天的日常累积，刷新可选事件池。
 * 玩家最多手动选择2-3件规划事项，其余行为系统自动按人物观念运行。
 *
 * 核心原则：
 * - 每周六刷新事件池，不是每周一
 * - 汇总疲惫、焦虑、孤独、技能进度、物品损耗
 * - 同步更新本周气温、湿度，调整穿搭面料适配
 */

data class WeeklyStats(
    /** 本周总疲惫累积 */
    val totalFatigue: Double = 0.0,
    /** 本周平均焦虑 */
    val avgAnxiety: Double = 0.0,
    /** 本周平均孤独感 */
    val avgLoneliness: Double = 0.0,
    /** 本周食物总消耗（元） */
    val totalFoodCost: Double = 0.0,
    /** 本周日用品总折旧（元） */
    val totalItemDepreciation: Double = 0.0,
    /** 本周总屏幕使用时间（小时） */
    val totalScreenTime: Double = 0.0,
    /** 本周熬夜天数 */
    val stayUpLateDays: Int = 0,
    /** 本周脚气风险累积 */
    val accumulatedFootFungusRisk: Double = 0.0,
    /** 本周感冒风险累积 */
    val accumulatedColdRisk: Double = 0.0,
    /** 本周平均温度 */
    val avgTemperature: Double = 20.0,
    /** 本周主要天气 */
    val dominantWeather: WeatherState = WeatherState.SUNNY,
    /** 本周可选手动事件数量 */
    val availableActionSlots: Int = 2,
    /** 本周可选事件池 */
    val weeklyEventPool: List<String> = emptyList(),
    /** 周度总结消息 */
    val summary: String = ""
)

object WeeklySettlement {

    /** 每周最多手动事件数 */
    const val MAX_MANUAL_ACTIONS = 3
    /** 每周最少手动事件数 */
    const val MIN_MANUAL_ACTIONS = 2

    /**
     * 汇总7天的每日统计数据，生成周度报告
     */
    fun aggregate(
        dailyStatsList: List<DailyStats>,
        weekNumber: Int,
        month: Int,
        season: Season,
        avgTemperature: Double,
        dominantWeather: WeatherState,
        isYouth: Boolean = true
    ): WeeklyStats {
        if (dailyStatsList.isEmpty()) {
            return WeeklyStats(summary = "本周无事发生。")
        }

        val totalFatigue = dailyStatsList.sumOf { it.fatigueDelta }
        val avgAnxiety = dailyStatsList.map { it.anxietyDelta }.average()
        val totalFoodCost = dailyStatsList.sumOf { it.foodCost }
        val totalScreenTime = dailyStatsList.sumOf { it.screenTime }
        val stayUpLateDays = dailyStatsList.count { it.stayedUpLate }
        val footFungusRisk = dailyStatsList.sumOf { it.footFungusRisk }
        val coldRisk = dailyStatsList.sumOf { it.coldRisk }
        val totalDepreciation = dailyStatsList.sumOf { it.dailyItemDepreciation }

        // 青年阶段多给一个事件槽
        val actionSlots = if (isYouth) MAX_MANUAL_ACTIONS else MIN_MANUAL_ACTIONS

        // 生成可选事件池
        val eventPool = generateWeeklyEventPool(season, dominantWeather, weekNumber)

        val summary = buildString {
            append("第${weekNumber}周 · ${season.label}季\n")
            append("━━━━━━━━━━━━━━━━\n")
            if (totalFatigue > 0.3) {
                append("本周疲惫累积较高，周末注意休息。\n")
            }
            if (footFungusRisk > 0.2) {
                append("脚部潮湿风险累积——检查鞋袜透气性。\n")
            }
            if (coldRisk > 0.15) {
                append("受寒风险偏高——下周注意保暖。\n")
            }
            if (stayUpLateDays >= 3) {
                append("本周熬夜${stayUpLateDays}天——身体在透支。\n")
            }
            append("━━━━━━━━━━━━━━━━\n")
            append("你可以选择${actionSlots}件事来安排这个周末。")
        }

        return WeeklyStats(
            totalFatigue = totalFatigue,
            avgAnxiety = avgAnxiety,
            totalFoodCost = totalFoodCost,
            totalItemDepreciation = totalDepreciation,
            totalScreenTime = totalScreenTime,
            stayUpLateDays = stayUpLateDays,
            accumulatedFootFungusRisk = footFungusRisk.coerceIn(0.0, 1.0),
            accumulatedColdRisk = coldRisk.coerceIn(0.0, 1.0),
            avgTemperature = avgTemperature,
            dominantWeather = dominantWeather,
            availableActionSlots = actionSlots,
            weeklyEventPool = eventPool,
            summary = summary
        )
    }

    /**
     * 根据季节和天气生成本周可选事件池
     */
    private fun generateWeeklyEventPool(
        season: Season,
        weather: WeatherState,
        weekNumber: Int
    ): List<String> {
        val pool = mutableListOf<String>()

        // 通用事件
        pool.addAll(listOf(
            "周末休闲娱乐",
            "采购衣物与日用品",
            "检修保养衣物数码产品"
        ))

        // 季节事件
        when (season) {
            Season.SPRING -> pool.add("春游踏青")
            Season.SUMMER -> pool.add("海边度假")
            Season.AUTUMN -> pool.add("登山赏秋")
            Season.WINTER -> pool.add("温泉疗养")
        }

        // 天气事件
        when (weather) {
            WeatherState.RAINY, WeatherState.STORM -> pool.add("居家阅读/观影")
            WeatherState.SUNNY -> pool.add("户外徒步")
            else -> {}
        }

        // 定期事件
        if (weekNumber % 2 == 0) {
            pool.add("和长辈沟通谈心")
        }
        if (weekNumber % 4 == 0) {
            pool.add("体检购药")
        }
        pool.add("相亲约会")

        return pool
    }
}