package com.example.townapp.domain.engine

/**
 * 游戏时间快照 —— 纯不可变数据类。
 *
 * 记录当前游戏世界的时间维度：天数、小时、周数、星期几、季节。
 * 所有时间推进通过 [TimeEngine.advanceHour] 生成新实例。
 */
data class GameTime(
    val days: Int = 1,
    val hours: Int = 8,
    val week: Int = 1,
    /** 1=周一, 7=周日 */
    val dayOfWeek: Int = 1,
    val season: String = "spring"
) {
    /**
     * 获取星期几的中文名称。
     *
     * @return "周一" 至 "周日"，非法值默认为 "周一"
     */
    fun getDayOfWeekName(): String = when (dayOfWeek) {
        1 -> "周一"
        2 -> "周二"
        3 -> "周三"
        4 -> "周四"
        5 -> "周五"
        6 -> "周六"
        7 -> "周日"
        else -> "周一"
    }
}
