package com.example.townapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.townapp.data.model.DailyTask
import com.example.townapp.data.repository.DailyTaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 每日任务 ViewModel。
 *
 * 封装 DailyTaskRepository，供 Composable 层使用。
 * 生命周期跟随 Activity，保证任务状态在屏幕旋转时不丢失。
 */
class DailyTaskViewModel : ViewModel() {

    private val repository = DailyTaskRepository()

    private val _dailyTasks = MutableStateFlow<List<DailyTask>>(emptyList())
    val dailyTasks: StateFlow<List<DailyTask>> = _dailyTasks.asStateFlow()

    private val _allCompleted = MutableStateFlow(false)
    val allCompleted: StateFlow<Boolean> = _allCompleted.asStateFlow()

    private val _showPanel = MutableStateFlow(false)
    val showPanel: StateFlow<Boolean> = _showPanel.asStateFlow()

    init {
        // 观察 Repository 的 Flow
        viewModelScope.launch {
            repository.dailyTasks.collect { tasks ->
                _dailyTasks.value = tasks
                _allCompleted.value = tasks.isNotEmpty() && tasks.all { it.isCompleted }
            }
        }
    }

    /** 通知任务系统用户执行了一个动作。 */
    fun notifyAction(type: String) {
        repository.notifyAction(type)
    }

    /** 切换任务面板显示/隐藏。 */
    fun togglePanel() {
        _showPanel.value = !_showPanel.value
    }

    /** 隐藏面板。 */
    fun hidePanel() {
        _showPanel.value = false
    }

    /** 强制刷新今日任务。 */
    fun refreshTasks() {
        repository.refreshTasks()
    }

    /** 强制完成指定任务（用于测试）。 */
    fun forceComplete(taskId: String) {
        repository.forceComplete(taskId)
    }

    /** 今日任务总数。 */
    val totalCount: Int
        get() = _dailyTasks.value.size

    /** 今日已完成数。 */
    val completedCount: Int
        get() = _dailyTasks.value.count { it.isCompleted }
}