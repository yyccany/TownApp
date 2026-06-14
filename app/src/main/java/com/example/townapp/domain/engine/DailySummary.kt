package com.example.townapp.domain.engine

/**
 * 单日模拟汇总。
 *
 * 记录某一天内的关键指标变化与触发事件，用于快进后的日志展示。
 *
 * @param day 游戏天数
 * @param moneyChange 当日货币净变化
 * @param maxHunger 当日最大饥饿值
 * @param minEnergy 当日最低精力值
 * @param avgHappiness 当日平均幸福感
 * @param events 当日触发的事件文案列表
 */
data class DailySummary(
    val day: Int,
    val moneyChange: Double,
    val maxHunger: Double,
    val minEnergy: Double,
    val avgHappiness: Double,
    val events: List<String>
)
