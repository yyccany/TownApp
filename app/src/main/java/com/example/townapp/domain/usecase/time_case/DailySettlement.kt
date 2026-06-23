package com.example.townapp.domain

import com.example.townapp.data.model.WeatherState

/**
 * 单日结算器（v2.14 时间系统重构）
 *
 * 后台静默运算，前端不展示。处理：
 * - 三餐食材消耗、饮用水
 * - 日用品折旧
 * - 鞋袜潮湿后脚气患病概率累积
 * - 当日通勤疲惫值
 * - 夜间电子产品使用时长的当日增量
 * - 熬夜带来的体虚隐患
 * - 生理期天数拆分
 * - 高温闷热出汗、雨天湿度变化
 *
 * 核心原则：
 * - 玩家看不到单日弹窗，避免日常琐事操作冗余
 * - 所有计算静默累积到周度缓存池
 * - 只在异常值（如极度疲惫、严重疾病）时触发提醒
 */

data class DailyStats(
    /** 当日食物消耗（元） */
    val foodCost: Double = 0.0,
    /** 当日饮水消耗（升） */
    val waterConsumed: Double = 2.0,
    /** 当日日用品折旧（元） */
    val dailyItemDepreciation: Double = 0.0,
    /** 当日通勤疲惫累积 */
    val commuteFatigue: Double = 0.0,
    /** 当日屏幕使用时长（小时） */
    val screenTime: Double = 0.0,
    /** 是否熬夜（23点后未休息） */
    val stayedUpLate: Boolean = false,
    /** 当日脚气风险累积 */
    val footFungusRisk: Double = 0.0,
    /** 当日感冒风险累积 */
    val coldRisk: Double = 0.0,
    /** 当日疲惫值变化 */
    val fatigueDelta: Double = 0.0,
    /** 当日焦虑值变化 */
    val anxietyDelta: Int = 0,
    /** 当日是否有异常（需要提醒） */
    val hasAlert: Boolean = false,
    /** 提醒消息 */
    val alertMessage: String = ""
)

object DailySettlement {

    /** 基础每日食物消耗（元） */
    const val BASE_FOOD_COST = 15.0
    /** 基础每日饮水消耗（升） */
    const val BASE_WATER = 2.0
    /** 通勤基础疲惫 */
    const val COMMUTE_BASE_FATIGUE = 0.05
    /** 熬夜体虚疲惫加成 */
    const val STAY_UP_FATIGUE_BONUS = 0.15
    /** 极端疲惫阈值 */
    const val EXTREME_FATIGUE_THRESHOLD = 0.8

    /**
     * 执行单日结算
     *
     * @param weekday 星期几（1-7）
     * @param weather 当日天气
     * @param temperature 当日温度（°C）
     * @param isMenstruating 是否正值生理期
     * @param screenHours 当日屏幕使用小时数
     * @param footFungusBaseRisk 基础脚气风险（来自面料系统）
     */
    fun settle(
        weekday: Int,
        weather: WeatherState,
        temperature: Double,
        isMenstruating: Boolean = false,
        screenHours: Double = 4.0,
        footFungusBaseRisk: Double = 0.0
    ): DailyStats {
        var fatigue = 0.0
        var anxiety = 0
        var footFungus = footFungusBaseRisk
        var coldRisk = 0.0
        val alerts = mutableListOf<String>()

        // 1. 工作日通勤疲惫
        val isWorkday = weekday in 1..5
        if (isWorkday) {
            fatigue += COMMUTE_BASE_FATIGUE
        }

        // 2. 天气影响
        when (weather) {
            WeatherState.SUNNY -> {
                // 高温闷热 → 疲惫
                if (temperature > 28) {
                    fatigue += 0.05
                    footFungus += 0.05
                }
            }
            WeatherState.RAINY, WeatherState.STORM -> {
                // 雨天湿度高 → 脚气风险
                footFungus += 0.08
                coldRisk += 0.03
            }
            else -> {}
        }

        // 3. 生理期加成
        if (isMenstruating) {
            fatigue += 0.05
            anxiety += 1
        }

        // 4. 屏幕使用时间 → 疲惫+焦虑
        if (screenHours > 6) {
            fatigue += 0.03
            anxiety += 1
        }
        if (screenHours > 10) {
            fatigue += 0.05
            anxiety += 2
        }

        // 5. 熬夜检测（通过外部传入）
        val stayedUpLate = false // 由外部调用时设置

        // 6. 异常提醒
        if (fatigue > EXTREME_FATIGUE_THRESHOLD) {
            alerts.add("今天特别累——身体在提醒你该休息了。")
        }
        if (footFungus > 0.3) {
            alerts.add("脚部闷热潮湿——注意透气，脚气风险在累积。")
        }
        if (coldRisk > 0.1) {
            alerts.add("天气转凉，注意保暖——受寒风险在上升。")
        }

        return DailyStats(
            foodCost = BASE_FOOD_COST,
            waterConsumed = BASE_WATER,
            commuteFatigue = if (isWorkday) COMMUTE_BASE_FATIGUE else 0.0,
            screenTime = screenHours,
            stayedUpLate = stayedUpLate,
            footFungusRisk = footFungus.coerceIn(0.0, 1.0),
            coldRisk = coldRisk.coerceIn(0.0, 1.0),
            fatigueDelta = fatigue,
            anxietyDelta = anxiety,
            hasAlert = alerts.isNotEmpty(),
            alertMessage = alerts.joinToString("\n")
        )
    }

    /**
     * 计算熬夜后的体虚影响
     */
    fun stayUpLatePenalty(): DailyStats {
        return DailyStats(
            fatigueDelta = STAY_UP_FATIGUE_BONUS,
            anxietyDelta = 2,
            stayedUpLate = true,
            hasAlert = true,
            alertMessage = "熬夜了——身体需要更多时间恢复。今晚早点睡吧。"
        )
    }
}