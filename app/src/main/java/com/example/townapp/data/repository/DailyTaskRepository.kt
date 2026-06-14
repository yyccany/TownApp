package com.example.townapp.data.repository

import com.example.townapp.data.model.DailyTask
import com.example.townapp.data.model.DailyTaskPool
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import java.util.TimeZone

/**
 * 每日任务仓库（纯内存实现）。
 *
 * 任务状态仅在应用运行期间维护，关闭后自动清空。
 * 每天凌晨 00:00 自动刷新任务池。
 */
class DailyTaskRepository {

    private val _dailyTasks = MutableStateFlow<List<DailyTask>>(emptyList())
    val dailyTasks: StateFlow<List<DailyTask>> = _dailyTasks.asStateFlow()

    /** 今日是否所有任务都已完成。 */
    val allCompleted: Boolean
        get() = _dailyTasks.value.isNotEmpty() && _dailyTasks.value.all { it.isCompleted }

    private var lastRefreshDate: Long = 0L

    init {
        loadOrRefresh()
    }

    // ============================================================
    // 公开方法
    // ============================================================

    /**
     * 通知任务系统：用户执行了一个动作。
     * 检查所有未完成任务，若 targetActionType 匹配则进度 +1。
     */
    fun notifyAction(type: String) {
        val now = _dailyTasks.value.toMutableList()
        var changed = false

        now.forEachIndexed { i, task ->
            if (!task.isCompleted && task.targetActionType == type) {
                val newCount = task.currentCount + 1
                now[i] = task.copy(
                    currentCount = newCount,
                    isCompleted = newCount >= task.targetCount
                )
                changed = true
            }
        }

        if (changed) {
            _dailyTasks.value = now
        }
    }

    /** 强制刷新今日任务。 */
    fun refreshTasks() {
        val todayTasks = generateDailyTasks()
        _dailyTasks.value = todayTasks
        lastRefreshDate = getTodayStart()
    }

    /** 手动标记指定任务已完成。 */
    fun forceComplete(taskId: String) {
        val now = _dailyTasks.value.toMutableList()
        val idx = now.indexOfFirst { it.id == taskId }
        if (idx != -1 && !now[idx].isCompleted) {
            now[idx] = now[idx].copy(currentCount = now[idx].targetCount, isCompleted = true)
            _dailyTasks.value = now
        }
    }

    // ============================================================
    // 内部实现
    // ============================================================

    private fun loadOrRefresh() {
        val todayStart = getTodayStart()
        if (lastRefreshDate < todayStart) {
            refreshTasks()
        }
    }

    private fun generateDailyTasks(): List<DailyTask> {
        val pool = DailyTaskPool.allTasks
        val shuffled = pool.shuffled()
        return shuffled.take(DAILY_TASK_COUNT).map { it.copy() }
    }

    companion object {
        private const val DAILY_TASK_COUNT = 3

        /** 获取今天 00:00 UTC 的时间戳。 */
        private fun getTodayStart(): Long {
            val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return cal.timeInMillis
        }
    }
}