package com.example.townapp.data.extension

import com.example.townapp.data.model.*
import com.example.townapp.performance.ThreadPoolManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar

object LifeTimelineReviewEngine {
    private val behaviorLogs = ConcurrentHashMap<String, MutableList<BehaviorLog>>()

    data class BehaviorLog(val timestamp: Long, val activity: String, val category: String, val durationHours: Double, val moneySpent: Double, val attentionLevel: Int, val bodyStateAfter: Int, val emotionAfter: String)

    fun logBehavior(userId: String, log: BehaviorLog) { behaviorLogs.getOrPut(userId) { mutableListOf() }.add(log) }

    suspend fun generateAnnualReview(userId: String, year: Int): LifeTimelineReview = withContext(ThreadPoolManager.calculationDispatcher) {
        val logs = behaviorLogs[userId] ?: emptyList()
        val calendarStart = Calendar.getInstance().apply { set(year, 0, 1, 0, 0, 0) }
        val yearStart = calendarStart.timeInMillis
        val calendarEnd = Calendar.getInstance().apply { set(year + 1, 0, 1, 0, 0, 0) }
        val yearEnd = calendarEnd.timeInMillis
        val yearLogs = logs.filter { it.timestamp in yearStart until yearEnd }
        generateReview(yearLogs, "年度", yearStart, yearEnd)
    }

    private fun generateReview(logs: List<BehaviorLog>, period: String, start: Long, end: Long): LifeTimelineReview {
        if (logs.isEmpty()) return LifeTimelineReview(period, start, end, 0.0, 0.0, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), "该时间段暂无行为记录")
        val totalHours = logs.sumOf { it.durationHours }
        val totalMoney = logs.sumOf { it.moneySpent }
        val timeCategories = logs.groupBy { it.category }.map { (cat, items) -> val h = items.sumOf { it.durationHours }; TimeCategorySummary(cat, h, h/totalHours*100, items.sumOf { it.moneySpent }, "持平") }.sortedByDescending { it.hours }
        val moneyCategories = logs.groupBy { it.category }.map { (cat, items) -> val a = items.sumOf { it.moneySpent }; MoneyCategorySummary(cat, a, a/totalMoney*100, a/12, "持平") }.sortedByDescending { it.amount }
        return LifeTimelineReview(period, start, end, totalHours, totalMoney, timeCategories, moneyCategories, emptyList(), emptyList(), emptyList(), "过去${period}，你投入了${String.format("%.0f",totalHours)}小时生命时长")
    }

    fun clearUserLogs(userId: String) { behaviorLogs.remove(userId) }
}