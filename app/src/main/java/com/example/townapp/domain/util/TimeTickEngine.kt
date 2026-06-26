package com.example.townapp.domain.util

import com.example.townapp.core.constants.NarrativePacingConfig
import com.example.townapp.data.AgeMilestone
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 全局时间节拍引擎 —— 统一心跳调度（v3.1 双模式支持）
 *
 * 支持两种模式：
 * - AUTO（自动托管速通）：分年龄段动态变速，30分钟走完一生
 * - MANUAL（手动精细游玩）：匀速慢节奏，1小时=3-4秒，方便细看
 */
object TimeTickEngine {

    enum class TickMode {
        AUTO,   // 自动托管速通模式
        MANUAL  // 手动精细游玩模式
    }

    private val _timeStateFlow = MutableStateFlow(TimeEngine.createInitialState())
    val timeStateFlow: StateFlow<TimeState> = _timeStateFlow.asStateFlow()

    /** 每次 tick 的结果流，供订阅者执行业务逻辑 */
    private val _tickFlow = MutableSharedFlow<TickResult>(
        replay = 0,
        extraBufferCapacity = 10
    )
    val tickFlow: SharedFlow<TickResult> = _tickFlow.asSharedFlow()

    /** 运行状态 */
    private val _isRunningFlow = MutableStateFlow(false)
    val isRunningFlow: StateFlow<Boolean> = _isRunningFlow.asStateFlow()

    /** 当前模式（默认自动速通） */
    private val _currentMode = MutableStateFlow(TickMode.AUTO)
    val currentMode: StateFlow<TickMode> = _currentMode.asStateFlow()

    private var tickJob: Job? = null

    private fun randomInRange(min: Long, max: Long): Long = (min + (Math.random() * (max - min))).toLong()
    private fun randomIntInRange(min: Int, max: Int): Int = (min + (Math.random() * (max - min + 1))).toInt()

    /**
     * 切换模式（自动/手动）
     */
    fun setMode(mode: TickMode) {
        if (_currentMode.value == mode) return
        _currentMode.value = mode
        // 重启心跳以应用新速度
        if (_isRunningFlow.value) {
            val scope = currentScope ?: return
            stop()
            start(scope)
        }
    }

    private var currentScope: CoroutineScope? = null

    /**
     * 启动心跳
     */
    fun start(scope: CoroutineScope) {
        if (_isRunningFlow.value) return
        _isRunningFlow.value = true
        currentScope = scope

        tickJob = scope.launch(Dispatchers.Default) {
            while (isActive && _isRunningFlow.value) {
                try {
                    val currentState = _timeStateFlow.value
                    val pace = when (_currentMode.value) {
                        TickMode.AUTO -> NarrativePacingConfig.getAutoPace(currentState.age)
                        TickMode.MANUAL -> NarrativePacingConfig.getManualPace(currentState.age)
                    }

                    val hoursToAdvance = randomIntInRange(pace.minHoursPerTick, pace.maxHoursPerTick)
                    val intervalMs = randomInRange(pace.minIntervalMs, pace.maxIntervalMs)

                    var latestState = currentState
                    var finalResult: TickResult? = null
                    var anyNewDay = false
                    var anyNewWeek = false
                    var anyNewMonth = false
                    var anyNewYear = false
                    var anySeasonChange = false
                    var anyAgeUp = false
                    var anyStageTransition = false
                    val allMilestones = mutableListOf<AgeMilestone>()

                    repeat(hoursToAdvance) {
                        val result = TimeEngine.advanceHour(latestState)
                        latestState = result.newState
                        finalResult = result
                        if (result.isNewDay) anyNewDay = true
                        if (result.isNewWeek) anyNewWeek = true
                        if (result.isNewMonth) anyNewMonth = true
                        if (result.isNewYear) anyNewYear = true
                        if (result.isSeasonChange) anySeasonChange = true
                        if (result.isAgeUp) anyAgeUp = true
                        if (result.isStageTransition) anyStageTransition = true
                        allMilestones.addAll(result.milestones)
                    }

                    _timeStateFlow.value = latestState
                    finalResult?.let { base ->
                        _tickFlow.emit(base.copy(
                            hoursAdvanced = hoursToAdvance,
                            isNewDay = anyNewDay,
                            isNewWeek = anyNewWeek,
                            isNewMonth = anyNewMonth,
                            isNewYear = anyNewYear,
                            isSeasonChange = anySeasonChange,
                            isAgeUp = anyAgeUp,
                            isStageTransition = anyStageTransition,
                            milestones = allMilestones.distinct()
                        ))
                    }

                    delay(intervalMs)
                } catch (e: Exception) {
                    android.util.Log.e("TimeTickEngine", "心跳异常: ${e.message}", e)
                    delay(1000)
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
     * 获取本次tick推进了多少小时（供外部判断节奏）
     */
    fun getTickAdvanceHours(age: Int): Int {
        val pace = when (_currentMode.value) {
            TickMode.AUTO -> NarrativePacingConfig.getAutoPace(age)
            TickMode.MANUAL -> NarrativePacingConfig.getManualPace(age)
        }
        return randomIntInRange(pace.minHoursPerTick, pace.maxHoursPerTick)
    }

    /**
     * 直接推进指定小时数（用于快进/测试）
     */
    fun fastForward(hours: Int): List<TickResult> {
        val results = mutableListOf<TickResult>()
        var state = _timeStateFlow.value
        repeat(hours) {
            val result = TimeEngine.advanceHour(state)
            state = result.newState
            results.add(result)
        }
        _timeStateFlow.value = state
        return results
    }
}
