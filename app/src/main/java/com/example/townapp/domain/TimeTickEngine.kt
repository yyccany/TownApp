package com.example.townapp.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 全局时间节拍引擎 —— 统一心跳调度
 *
 * 职责：
 * - 内部维护定时循环，调用 TimeEngine 推进时间
 * - 对外暴露只读 timeStateFlow 与 tickFlow
 * - ViewModel/UI 仅订阅，不自行建定时器
 *
 * 设计原则：
 * - 单一心跳源：全局只有此处存在 delay/while 循环
 * - 不可变快照：时间状态通过 copy() 更新
 * - 生命周期由外部 CoroutineScope 托管
 */
object TimeTickEngine {

    private val _timeStateFlow = MutableStateFlow(TimeEngine.createInitialState())
    val timeStateFlow: StateFlow<TimeState> = _timeStateFlow.asStateFlow()

    /** 每次 tick 的结果流，供订阅者执行业务逻辑 */
    private val _tickFlow = MutableSharedFlow<TickResult>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val tickFlow: SharedFlow<TickResult> = _tickFlow.asSharedFlow()

    /** 运行状态 */
    private val _isRunningFlow = MutableStateFlow(false)
    val isRunningFlow: StateFlow<Boolean> = _isRunningFlow.asStateFlow()

    private var tickJob: Job? = null

    /**
     * 启动心跳
     *
     * @param scope 生命周期托管的协程作用域
     * @param intervalMs 每次 tick 间隔（默认 5000ms = 1游戏小时）
     */
    fun start(scope: CoroutineScope, intervalMs: Long = 5000L) {
        if (_isRunningFlow.value) return
        _isRunningFlow.value = true

        tickJob = scope.launch(Dispatchers.Default) {
            while (isActive && _isRunningFlow.value) {
                delay(intervalMs)
                if (!_isRunningFlow.value) break
                try {
                    val current = _timeStateFlow.value
                    val result = TimeEngine.advanceHour(current)
                    _timeStateFlow.value = result.newState
                    _tickFlow.emit(result)
                } catch (e: Exception) {
                    android.util.Log.e("TimeTickEngine", "心跳异常: ${e.message}", e)
                }
            }
        }
    }

    /**
     * 停止心跳
     */
    fun stop() {
        _isRunningFlow.value = false
        tickJob?.cancel()
        tickJob = null
    }

    /**
     * 重置时间状态（开局/重新开始时调用）
     */
    fun reset(state: TimeState = TimeEngine.createInitialState()) {
        _timeStateFlow.value = state
    }

    /**
     * 直接推进指定小时数（用于快进/测试）
     */
    fun fastForward(hours: Int): List<TickResult> {
        val results = mutableListOf<TickResult>()
        repeat(hours) {
            val result = TimeEngine.advanceHour(_timeStateFlow.value)
            _timeStateFlow.value = result.newState
            results.add(result)
        }
        return results
    }
}
