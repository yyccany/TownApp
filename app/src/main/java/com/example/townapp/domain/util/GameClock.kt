package com.example.townapp.domain.util

import android.util.Log
import com.example.townapp.core.constants.SimulationConstants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 游戏时钟引擎（渲染层）
 * 
 * 严格遵循实事求是：
 * 1. 底层逻辑时间固定 1440 逻辑分钟/天，从不动摇
 * 2. 倍率只控制「现实多少毫秒走完 1 逻辑分钟」，不改变总量
 * 3. 全链路日志可追溯
 * 
 * 严格遵循自由平等：
 * - 所有 NPC 共用同一套时间规则，无差异化
 * - 玩家自由切换倍率，暂停/快进/慢放
 * 
 * 设计：
 * - 独立计时协程，按 realMsPerLogicMin 间隔推进
 * - 切换倍率仅修改间隔，不重置逻辑时间
 * - 暂停时协程挂起，保留所有状态
 */
object GameClock {

    private const val TAG = "GAME_CLOCK"

    // ================== 状态 ==================

    /** 当前流速倍率（0 = 暂停） */
    private val _speedMultiplier = MutableStateFlow(SimulationConstants.DEFAULT_SPEED)
    val speedMultiplier: StateFlow<Float> = _speedMultiplier.asStateFlow()

    /** 是否暂停 */
    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    /** 当前时间状态（委托给 TimeEngine） */
    private val _timeState = MutableStateFlow(TimeEngine.createInitialState())
    val timeState: StateFlow<TimeState> = _timeState.asStateFlow()

    // ================== 内部状态 ==================

    private var clockJob: Job? = null
    private val clockScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    /** 上次推进逻辑分钟的时间戳（系统毫秒） */
    private var lastTickRealTime = 0L

    /** 当前累计的逻辑分钟（每 1440 归零重新开始一天） */
    private var accumulatedLogicMinutes = 0

    /** 是否刚触发新的一天（用于通知外部） */
    private val _justAdvancedDay = MutableStateFlow(false)
    val justAdvancedDay: StateFlow<Boolean> = _justAdvancedDay.asStateFlow()

    // ================== 核心计算 ==================

    /**
     * 根据当前倍率计算：多少毫秒走完 1 逻辑分钟
     * 
     * 公式：realMsPerLogicMin = BASE_REAL_MS_PER_LOGIC_MIN / speedMultiplier
     * - 1x   → 60000ms 走完 1 逻辑分钟（1分钟 = 1分钟）
     * - 2x   → 30000ms 走完 1 逻辑分钟
     * - 8x   → 7500ms 走完 1 逻辑分钟（快进）
     * - 0.25x → 240000ms 走完 1 逻辑分钟（慢放）
     * - 0    → 暂停（永不推进）
     */
    private val realMsPerLogicMin: Long
        get() {
            val speed = _speedMultiplier.value
            if (speed <= 0f) return Long.MAX_VALUE  // 暂停
            return (SimulationConstants.BASE_REAL_MS_PER_LOGIC_MIN / speed).toLong().coerceAtLeast(1)
        }

    // ================== 计时协程 ==================

    /**
     * 启动时钟（首次调用或 restart 时）
     */
    fun start() {
        if (clockJob?.isActive == true) return
        
        lastTickRealTime = System.currentTimeMillis()
        
        clockJob = clockScope.launch {
            while (isActive) {
                if (!_isPaused.value && _speedMultiplier.value > 0f) {
                    val now = System.currentTimeMillis()
                    val elapsed = now - lastTickRealTime
                    
                    if (elapsed >= realMsPerLogicMin) {
                        val minutesToAdvance = (elapsed / realMsPerLogicMin).toInt()
                        repeat(minutesToAdvance) {
                            tick()
                        }
                        lastTickRealTime = now - (elapsed % realMsPerLogicMin).toInt()
                    }
                }
                delay(50)  // 50ms 检查间隔，平衡精度与性能
            }
        }
        
        Log.d(TAG, "时钟启动：speed=${_speedMultiplier.value}x, 1逻辑分钟=${realMsPerLogicMin}ms")
    }

    /**
     * 停止时钟（永久销毁）
     */
    fun stop() {
        clockJob?.cancel()
        clockJob = null
        Log.d(TAG, "时钟停止")
    }

    /**
     * 推进 1 逻辑分钟（内部使用，由协程调用）
     */
    private fun tick() {
        accumulatedLogicMinutes++
        
        // 每 1440 分钟走完一天
        if (accumulatedLogicMinutes >= SimulationConstants.LOGIC_MINUTES_PER_DAY) {
            accumulatedLogicMinutes = 0
            val result = TimeEngine.advanceDay(_timeState.value)
            _timeState.value = result.newState
            _justAdvancedDay.value = true
            
            Log.d("DATA_TIME_CHECK", 
                "新的一天：${result.newState.year}年${result.newState.month}月${result.newState.day}日 " +
                "总天数=${result.newState.totalDays}，age=${result.newState.age}")
        }
        
        // 清除新的一天标记（供外部消费）
        if (_justAdvancedDay.value) {
            // 外部需要在适当时机设为 false
        }
    }

    /**
     * 确认新的一天已处理（外部调用，清除标记）
     */
    fun acknowledgeNewDay() {
        _justAdvancedDay.value = false
    }

    // ================== 速度控制（自由平等：玩家自由调节） ==================

    /**
     * 设置速度倍率
     * 
     * @param speed 倍率（0=暂停，0.25/0.5/1/2/4/8）
     */
    fun setSpeed(speed: Float) {
        val clampedSpeed = speed.coerceIn(0f, SimulationConstants.SPEED_OPTIONS.maxOrNull() ?: 8f)
        
        if (_speedMultiplier.value == clampedSpeed) return
        
        _speedMultiplier.value = clampedSpeed
        _isPaused.value = (clampedSpeed == 0f)
        
        Log.d("TIME_SPEED_CHANGE", 
            "倍率切换：${_speedMultiplier.value}x → ${clampedSpeed}x，1逻辑分钟=${realMsPerLogicMin}ms")
    }

    /**
     * 暂停
     */
    fun pause() {
        setSpeed(0f)
        Log.d(TAG, "时钟暂停")
    }

    /**
     * 继续（恢复上次倍率）
     */
    fun resume() {
        if (_speedMultiplier.value == 0f) {
            setSpeed(SimulationConstants.DEFAULT_SPEED)
        }
    }

    /**
     * 切换暂停/继续
     */
    fun togglePause() {
        if (_isPaused.value || _speedMultiplier.value == 0f) {
            resume()
        } else {
            pause()
        }
    }

    // ================== 时间查询（供 UI 显示） ==================

    /** 获取当前逻辑小时（0-23） */
    fun getCurrentHour(): Int = _timeState.value.hour

    /** 获取当前逻辑分钟在一天中的位置（0-1439） */
    fun getCurrentMinuteOfDay(): Int = accumulatedLogicMinutes

    /** 判断是否为深夜时段（22:00-06:00） */
    fun isNightTime(): Boolean {
        val hour = getCurrentHour()
        return hour >= SimulationConstants.NIGHT_START_HOUR || hour < SimulationConstants.NIGHT_END_HOUR
    }

    /** 获取剩余逻辑分钟 */
    fun getRemainingMinutesToday(): Int = SimulationConstants.LOGIC_MINUTES_PER_DAY - accumulatedLogicMinutes

    /** 获取当前时段描述 */
    fun getTimeOfDayLabel(): String {
        val hour = getCurrentHour()
        return when {
            hour in 6..8 -> "清晨"
            hour in 9..11 -> "上午"
            hour in 12..13 -> "中午"
            hour in 14..16 -> "下午"
            hour in 17..19 -> "傍晚"
            hour in 20..21 -> "晚上"
            else -> "深夜"
        }
    }

    // ================== 外部同步 ==================

    /**
     * 重置时钟（重新开局时调用）
     */
    fun reset() {
        stop()
        accumulatedLogicMinutes = 0
        _timeState.value = TimeEngine.createInitialState()
        _speedMultiplier.value = SimulationConstants.DEFAULT_SPEED
        _isPaused.value = false
        _justAdvancedDay.value = false
        start()
        Log.d(TAG, "时钟重置")
    }

    /**
     * 推进指定天数（用于测试或跳转）
     */
    fun advanceDays(days: Int) {
        repeat(days) {
            val result = TimeEngine.advanceDay(_timeState.value)
            _timeState.value = result.newState
        }
        Log.d("DATA_TIME_CHECK", "跳转${days}天：现在${_timeState.value.year}年${_timeState.value.month}月${_timeState.value.day}日")
    }

    /**
     * 获取时间状态快照（供保存游戏用）
     */
    fun getSnapshot(): GameClockSnapshot {
        return GameClockSnapshot(
            speedMultiplier = _speedMultiplier.value,
            timeState = _timeState.value,
            accumulatedLogicMinutes = accumulatedLogicMinutes
        )
    }

    /**
     * 恢复快照（加载游戏时调用）
     */
    fun restore(snapshot: GameClockSnapshot) {
        _speedMultiplier.value = snapshot.speedMultiplier
        _timeState.value = snapshot.timeState
        accumulatedLogicMinutes = snapshot.accumulatedLogicMinutes
        lastTickRealTime = System.currentTimeMillis()
        Log.d(TAG, "时钟恢复：speed=${snapshot.speedMultiplier}x, ${snapshot.timeState.year}年${snapshot.timeState.month}月")
    }
}

/**
 * 游戏时钟快照（用于存档）
 */
data class GameClockSnapshot(
    val speedMultiplier: Float,
    val timeState: TimeState,
    val accumulatedLogicMinutes: Int
)
