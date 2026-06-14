package com.example.townapp.domain.engine

/**
 * 快进模拟结果。
 *
 * @param dailySummaries 快进期间每日的汇总数据列表
 */
data class SimulationResult(
    val dailySummaries: List<DailySummary>
)
