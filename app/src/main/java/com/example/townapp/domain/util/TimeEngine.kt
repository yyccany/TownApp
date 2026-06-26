package com.example.townapp.domain.util

import com.example.townapp.core.constants.SimulationConstants
import com.example.townapp.data.LifeStage
import com.example.townapp.data.LifeStageRules
import com.example.townapp.data.AgeMilestones
import com.example.townapp.data.AgeMilestone
import com.example.townapp.data.Season

/**
 * 三级时序引擎（v2.14 时间系统重构）
 *
 * 日-周-月三级时序，严格分层运算：
 * - 单日：后台静默运算，前端不展示天数
 * - 周（7天=1周）：每周六刷新事件池，玩家最多选2-3件事
 * - 月（4周=1月）：月末全局结算，弹出月度复盘面板
 * - 年（12月=1年）：年龄+1，检查里程碑
 *
 * 核心原则：
 * - 不展示天数，避免日常琐事操作冗余
 * - 周为抉择节点，月为主结算周期
 * - 后台静默处理日常生活消耗，玩家只看到有意义的抉择
 */

// ============================================
// 时间状态（单一数据类，替代分散的 StateFlow）
// ============================================
data class TimeState(
    /** 游戏小时（0-23） */
    val hour: Int = 8,
    /** 游戏日（1-30，每月30天） */
    val day: Int = 1,
    /** 周序号（1-4，每月4周） */
    val weekOfMonth: Int = 1,
    /** 星期几（1-7，1=周一，6=周六，7=周日） */
    val weekDay: Int = 1,
    /** 游戏月份（1-12） */
    val month: Int = 6,
    /** 游戏年份 */
    val year: Int = 2024,
    /** 总游戏日数（从开局累计） */
    val totalDays: Int = 0,
    /** 总游戏周数 */
    val totalWeeks: Int = 0,
    /** 玩家年龄 */
    val age: Int = 19,
    /** 当前人生阶段 */
    val lifeStage: LifeStage = LifeStage.YOUTH,
    /** 当前季节 */
    val season: Season = Season.SUMMER
) {
    /** 是否周末（周六日） */
    val isWeekend: Boolean get() = weekDay in 6..7

    /** 是否周六（周抉择节点） */
    val isSaturday: Boolean get() = weekDay == 6

    /** 是否月末（每月第30天） */
    val isMonthEnd: Boolean get() = day == 30

    /** 是否年初（1月1日） */
    val isYearStart: Boolean get() = month == 1 && day == 1

    /** 是否换季月（3/6/9/12月） */
    val isSeasonTransition: Boolean get() = month in setOf(3, 6, 9, 12)

    /** 当前周在第几天（1-7） */
    val dayOfWeek: Int get() = weekDay

    /** 本月剩余天数 */
    val daysRemainingInMonth: Int get() = 30 - day

    /** 本月剩余周数 */
    val weeksRemainingInMonth: Int get() = 4 - weekOfMonth
}

// ============================================
// 时序推进结果
// ============================================
data class TickResult(
    /** 推进后的时间状态 */
    val newState: TimeState,
    /** 本次tick推进了多少小时（1~8，高年龄段一次tick推进更多小时） */
    val hoursAdvanced: Int = 1,
    /** 是否触发了新的一天 */
    val isNewDay: Boolean = false,
    /** 是否触发了新的一周 */
    val isNewWeek: Boolean = false,
    /** 是否触发了新的一个月 */
    val isNewMonth: Boolean = false,
    /** 是否触发了新的一年 */
    val isNewYear: Boolean = false,
    /** 是否触发了换季 */
    val isSeasonChange: Boolean = false,
    /** 是否触发了年龄增长 */
    val isAgeUp: Boolean = false,
    /** 本日触发的里程碑（如果有） */
    val milestones: List<AgeMilestone> = emptyList(),
    /** 是否触发了人生阶段切换 */
    val isStageTransition: Boolean = false
)

// ============================================
// 时序引擎
// ============================================
object TimeEngine {



    /**
     * 推进一小时（游戏心跳）
     *
     * @param current 当前时间状态
     * @return 推进结果，包含是否触发日/周/月/年切换
     */
    fun advanceHour(current: TimeState): TickResult {
        val newHour = (current.hour + 1) % 24
        val isNewDay = newHour == 0

        if (!isNewDay) {
            return TickResult(
                newState = current.copy(hour = newHour),
                isNewDay = false
            )
        }

        return advanceDayInternal(current.copy(hour = newHour))
    }

    /**
     * 推进一天（用于测试或直接跳转）
     */
    fun advanceDay(current: TimeState): TickResult {
        return advanceDayInternal(current)
    }

    /**
     * 内部：推进一天，处理日→周→月→年切换
     */
    private fun advanceDayInternal(current: TimeState): TickResult {
        val newDay = current.day + 1
        val newTotalDays = current.totalDays + 1
        val newWeekDay = (current.weekDay % 7) + 1

        var isNewWeek = false
        var isNewMonth = false
        var isNewYear = false
        var isSeasonChange = false
        var isAgeUp = false
        val milestones = mutableListOf<AgeMilestone>()
        var isStageTransition = false

        var newWeekOfMonth = current.weekOfMonth
        var newMonth = current.month
        var newYear = current.year
        var newDayAdjusted = newDay
        var newAge = current.age
        var newLifeStage = current.lifeStage

        // 周切换（每7天）
        if (newWeekDay == 1) {
            isNewWeek = true
            newWeekOfMonth = current.weekOfMonth + 1
        }

        // 月切换（每30天）
        if (newDayAdjusted > SimulationConstants.DAYS_PER_MONTH) {
            isNewMonth = true
            newDayAdjusted = 1
            newWeekOfMonth = 1
            newMonth = (current.month % SimulationConstants.MONTHS_PER_YEAR) + 1

            // 换季检测（3/6/9/12月）
            isSeasonChange = newMonth in setOf(3, 6, 9, 12)

            // 年切换（1月）
            if (newMonth == 1) {
                isNewYear = true
                newYear = current.year + 1
            }
        }

        // 年龄增长（每360天 = 12个月 = 1年）
        // 在月切换时检查：如果总天数可以被360整除
        if (isNewMonth && newTotalDays % (SimulationConstants.DAYS_PER_MONTH * SimulationConstants.MONTHS_PER_YEAR) == 0) {
            isAgeUp = true
            newAge = current.age + 1

            // 检查年龄里程碑
            milestones.addAll(AgeMilestones.getMilestonesForAge(newAge))

            // 检查人生阶段切换
            val newStage = LifeStage.fromAge(newAge)
            if (newStage != current.lifeStage) {
                isStageTransition = true
                newLifeStage = newStage
            }
        }

        val newTotalWeeks = newTotalDays / SimulationConstants.DAYS_PER_WEEK

        return TickResult(
            newState = TimeState(
                hour = current.hour,
                day = newDayAdjusted,
                weekOfMonth = newWeekOfMonth,
                weekDay = newWeekDay,
                month = newMonth,
                year = newYear,
                totalDays = newTotalDays,
                totalWeeks = newTotalWeeks,
                age = newAge,
                lifeStage = newLifeStage,
                season = getSeasonFromMonth(newMonth)
            ),
            isNewDay = true,
            isNewWeek = isNewWeek,
            isNewMonth = isNewMonth,
            isNewYear = isNewYear,
            isSeasonChange = isSeasonChange,
            isAgeUp = isAgeUp,
            milestones = milestones,
            isStageTransition = isStageTransition
        )
    }

    /**
     * 根据月份推导季节
     */
    fun getSeasonFromMonth(month: Int): Season = when (month) {
        in 3..5 -> Season.SPRING
        in 6..8 -> Season.SUMMER
        in 9..11 -> Season.AUTUMN
        else -> Season.WINTER
    }

    /**
     * 创建初始时间状态（19岁开局）
     */
    fun createInitialState(
        startYear: Int = 2024,
        startMonth: Int = 6,
        startAge: Int = 19
    ): TimeState {
        return TimeState(
            hour = 8,
            day = 1,
            weekOfMonth = 1,
            weekDay = 1,
            month = startMonth,
            year = startYear,
            totalDays = 0,
            totalWeeks = 0,
            age = startAge,
            lifeStage = LifeStage.fromAge(startAge),
            season = getSeasonFromMonth(startMonth)
        )
    }

    /**
     * 获取当前是本月第几周
     */
    fun getWeekOfMonth(day: Int): Int = ((day - 1) / SimulationConstants.DAYS_PER_WEEK) + 1

    /**
     * 获取工作日/周末描述
     */
    fun getWeekDayLabel(weekDay: Int): String = when (weekDay) {
        1 -> "周一"
        2 -> "周二"
        3 -> "周三"
        4 -> "周四"
        5 -> "周五"
        6 -> "周六"
        7 -> "周日"
        else -> ""
    }

    /**
     * 距离下一个周六还有几天
     */
    fun daysUntilSaturday(weekDay: Int): Int {
        return if (weekDay < 6) 6 - weekDay else (6 - weekDay + 7)
    }
}