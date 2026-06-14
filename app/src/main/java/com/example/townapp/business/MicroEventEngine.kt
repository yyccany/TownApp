package com.example.townapp.business

import com.example.townapp.data.microevent.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 日常微事件引擎
 * 
 * 核心规则：
 * 1. 触发频率：每10-30分钟随机触发一次，绝对不频繁
 * 2. 触发时机：你打开APP、点击任何操作、后台静默
 * 3. 没有惩罚：所有事件都不会降低任何状态
 * 4. 只有陪伴：每个事件都只有一句软乎乎的话
 */
class MicroEventEngine {
    
    // ============================================
    // 状态
    // ============================================
    
    private val _currentEvent = MutableStateFlow<MicroEvent?>(null)
    val currentEvent: StateFlow<MicroEvent?> = _currentEvent.asStateFlow()
    
    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()
    
    private val _isEventShowing = MutableStateFlow(false)
    val isEventShowing: StateFlow<Boolean> = _isEventShowing.asStateFlow()
    
    private val _recentEventIds = MutableStateFlow<Set<String>>(emptySet())
    val recentEventIds: StateFlow<Set<String>> = _recentEventIds.asStateFlow()
    
    private var engineScope: CoroutineScope? = null
    private var randomEventJob: Job? = null
    
    // 配置
    private val minIntervalMs = 10 * 60 * 1000L  // 10分钟
    private val maxIntervalMs = 30 * 60 * 1000L  // 30分钟
    private val eventDisplayDurationMs = 5000L    // 事件显示5秒
    private val recentEventResetMs = 5 * 60 * 1000L  // 5分钟后重置最近事件
    
    // ============================================
    // 生命周期
    // ============================================
    
    /**
     * 启动引擎（关闭自动随机事件，改为手动触发）
     */
    fun start(scope: CoroutineScope) {
        engineScope = scope
        // 关闭自动随机事件循环，改为手动触发
        StructuredLogger.i("MicroEventEngine", "微事件引擎启动（手动触发模式）")
    }
    
    /**
     * 手动触发随机事件（由居民模块调用）
     */
    suspend fun triggerManualRandomEvent() {
        if (_isEventShowing.value) {
            return // 如果正在显示事件，不触发新事件
        }
        
        val allEvents = MicroEventQuotes.getAllEvents()
        val recentIds = _recentEventIds.value
        val availableEvents = allEvents.filter { it.id !in recentIds }
        
        if (availableEvents.isEmpty()) {
            _recentEventIds.value = emptySet()
            return triggerManualRandomEvent()
        }
        
        val selectedEvent = selectEventByPriority(availableEvents)
        showEvent(selectedEvent)
    }
    
    /**
     * 停止引擎
     */
    fun stop() {
        randomEventJob?.cancel()
        engineScope = null
        StructuredLogger.i("MicroEventEngine", "微事件引擎停止")
    }
    
    /**
     * 重置引擎
     */
    fun reset() {
        _currentEvent.value = null
        _isEventShowing.value = false
        _recentEventIds.value = emptySet()
        _userState.value = UserState()
    }
    
    // ============================================
    // 随机事件循环
    // ============================================
    
    private fun startRandomEventLoop() {
        randomEventJob?.cancel()
        randomEventJob = engineScope?.launch(Dispatchers.Default) {
            while (isActive) {
                // 等待随机间隔
                val interval = (minIntervalMs..maxIntervalMs).random()
                delay(interval)
                
                // 检查是否应该触发事件
                if (shouldTriggerEvent()) {
                    triggerRandomEvent()
                }
            }
        }
    }
    
    /**
     * 检查是否应该触发事件
     */
    private fun shouldTriggerEvent(): Boolean {
        // 只有当没有事件正在显示时才触发
        return !_isEventShowing.value
    }
    
    /**
     * 触发随机事件
     */
    private suspend fun triggerRandomEvent() {
        val allEvents = MicroEventQuotes.getAllEvents()
        
        // 过滤掉最近触发过的事件
        val recentIds = _recentEventIds.value
        val availableEvents = allEvents.filter { it.id !in recentIds }
        
        if (availableEvents.isEmpty()) {
            // 如果所有事件都触发过了，清空最近记录
            _recentEventIds.value = emptySet()
            return triggerRandomEvent()
        }
        
        // 根据优先级加权随机选择
        val selectedEvent = selectEventByPriority(availableEvents)
        
        // 显示事件
        showEvent(selectedEvent)
    }
    
    /**
     * 根据优先级加权随机选择事件
     */
    private fun selectEventByPriority(events: List<MicroEvent>): MicroEvent {
        // 优先选择低优先级事件（心情不好 > 身体 > 家务 > 环境）
        val groupedByPriority = events.groupBy { it.priority }
        
        // 优先从低优先级开始选择
        for (priority in 0..3) {
            val priorityEvents = groupedByPriority[priority]
            if (!priorityEvents.isNullOrEmpty()) {
                return priorityEvents.random()
            }
        }
        
        return events.random()
    }
    
    // ============================================
    // 事件显示
    // ============================================
    
    /**
     * 显示事件
     */
    private suspend fun showEvent(event: MicroEvent) {
        _currentEvent.value = event
        _isEventShowing.value = true
        _recentEventIds.value = _recentEventIds.value + event.id
        
        StructuredLogger.d("MicroEventEngine", "显示微事件: ${event.id}")
        
        // 延迟后自动隐藏
        delay(eventDisplayDurationMs)
        hideEvent()
        
        // 延迟后重置最近事件记录
        delay(recentEventResetMs)
        _recentEventIds.value = _recentEventIds.value - event.id
    }
    
    /**
     * 隐藏事件
     */
    private suspend fun hideEvent() {
        _isEventShowing.value = false
        delay(1000) // 动画时间
        _currentEvent.value = null
    }
    
    /**
     * 用户关闭事件
     */
    suspend fun dismissEvent() {
        hideEvent()
    }
    
    // ============================================
    // 用户状态更新
    // ============================================
    
    /**
     * 更新用户状态（不触发事件）
     */
    fun updateUserState(update: (UserState) -> UserState) {
        _userState.value = update(_userState.value)
    }
    
    /**
     * 记录身体状态变化
     */
    suspend fun recordBodyAction(action: BodyAction) {
        updateUserState { state ->
            val newBody = when (action) {
                BodyAction.DRINK_WATER -> state.body.copy(
                    waterIntake = state.body.waterIntake + 1
                )
                BodyAction.BRUSH_TEETH -> state.body.copy(
                    hasBrushedTeeth = true
                )
                BodyAction.SHOWER -> state.body.copy(
                    hasShowered = true
                )
                BodyAction.YAWN -> state.body.copy(
                    yawnCount = state.body.yawnCount + 1
                )
                BodyAction.RUB_EYES -> state.body.copy(
                    eyeRubCount = state.body.eyeRubCount + 1
                )
                BodyAction.HAIR_LOSS -> state.body.copy(
                    hairLossCount = state.body.hairLossCount + 1
                )
            }
            state.copy(
                body = newBody,
                lastEventTime = System.currentTimeMillis()
            )
        }
        
        // 根据动作触发相应事件
        triggerBodyEvent(action)
    }
    
    /**
     * 记录家务状态变化
     */
    suspend fun recordHouseworkAction(action: HouseworkAction) {
        updateUserState { state ->
            val newEnv = when (action) {
                HouseworkAction.EMPTY_TRASH -> state.environment.copy(
                    isTrashFull = false
                )
                HouseworkAction.CLEAN_DESK -> state.environment.copy(
                    isDeskDusty = false
                )
                HouseworkAction.WASH_CLOTHES -> state.environment.copy(
                    isClothesPiled = false
                )
                HouseworkAction.TRASH_FULL -> state.environment.copy(
                    isTrashFull = true
                )
                HouseworkAction.DESK_DUSTY -> state.environment.copy(
                    isDeskDusty = true
                )
                HouseworkAction.CLOTHES_PILE -> state.environment.copy(
                    isClothesPiled = true
                )
            }
            state.copy(
                environment = newEnv,
                lastEventTime = System.currentTimeMillis()
            )
        }
        
        // 根据动作触发相应事件
        triggerHouseworkEvent(action)
    }
    
    /**
     * 记录心情状态变化
     */
    suspend fun recordMoodAction(mood: MoodState) {
        updateUserState { state ->
            state.copy(
                mood = mood,
                lastEventTime = System.currentTimeMillis()
            )
        }
        
        // 触发心情事件
        if (mood == MoodState.SAD || mood == MoodState.ANXIOUS) {
            triggerMoodEvent(MoodTrigger.BAD_MOOD)
        }
    }
    
    // ============================================
    // 事件触发
    // ============================================
    
    /**
     * 触发身体相关事件
     */
    private suspend fun triggerBodyEvent(action: BodyAction) {
        val events = MicroEventQuotes.getAllEvents().filter { 
            it.type == MicroEventType.BODY 
        }
        
        val matchingEvent = events.find { 
            when (action) {
                BodyAction.DRINK_WATER -> it.triggerCondition.contains("水")
                BodyAction.BRUSH_TEETH -> it.triggerCondition.contains("刷牙")
                BodyAction.SHOWER -> it.triggerCondition.contains("洗澡")
                BodyAction.YAWN -> it.triggerCondition.contains("哈欠")
                BodyAction.RUB_EYES -> it.triggerCondition.contains("揉眼睛")
                BodyAction.HAIR_LOSS -> it.triggerCondition.contains("头发")
            }
        }
        
        matchingEvent?.let { showEvent(it) }
    }
    
    /**
     * 触发家务相关事件
     */
    private suspend fun triggerHouseworkEvent(action: HouseworkAction) {
        val events = MicroEventQuotes.getAllEvents().filter { 
            it.type == MicroEventType.HOUSEWORK 
        }
        
        val matchingEvent = events.find { 
            when (action) {
                HouseworkAction.EMPTY_TRASH -> it.triggerCondition.contains("倒了垃圾")
                HouseworkAction.CLEAN_DESK -> it.triggerCondition.contains("擦了桌子")
                HouseworkAction.WASH_CLOTHES -> it.triggerCondition.contains("洗了衣服")
                HouseworkAction.TRASH_FULL -> it.triggerCondition.contains("垃圾桶满")
                HouseworkAction.DESK_DUSTY -> it.triggerCondition.contains("桌子有灰")
                HouseworkAction.CLOTHES_PILE -> it.triggerCondition.contains("衣服堆")
            }
        }
        
        matchingEvent?.let { showEvent(it) }
    }
    
    /**
     * 触发心情相关事件
     */
    private suspend fun triggerMoodEvent(trigger: MoodTrigger) {
        val events = MicroEventQuotes.getAllEvents().filter { 
            it.type == MicroEventType.MOOD 
        }
        
        val matchingEvent = when (trigger) {
            MoodTrigger.BAD_MOOD -> events.find { 
                it.triggerCondition.contains("心情不好") 
            }
            MoodTrigger.IDLE -> events.find { 
                it.triggerCondition.contains("发呆") 
            }
            MoodTrigger.BORED -> events.find { 
                it.triggerCondition.contains("无聊") 
            }
            MoodTrigger.GOOD_STATUS -> events.find { 
                it.triggerCondition.contains("状态都是绿色") 
            }
            MoodTrigger.RESET -> events.find { 
                it.triggerCondition.contains("重置") 
            }
        }
        
        matchingEvent?.let { showEvent(it) }
    }
    
    /**
     * 触发环境相关事件
     */
    suspend fun triggerEnvironmentEvent(weather: WeatherState, time: TimeState) {
        val events = MicroEventQuotes.getAllEvents().filter { 
            it.type == MicroEventType.ENVIRONMENT 
        }
        
        val matchingEvent = events.find { event ->
            when {
                weather == WeatherState.SUNNY && event.triggerCondition.contains("晴天") -> true
                weather == WeatherState.CLOUDY && event.triggerCondition.contains("阴天") -> true
                weather == WeatherState.RAINY && event.triggerCondition.contains("下雨") -> true
                weather == WeatherState.WINDY && event.triggerCondition.contains("刮风") -> true
                time == TimeState.NIGHT && event.triggerCondition.contains("天黑了") -> true
                time == TimeState.MIDNIGHT && event.triggerCondition.contains("天黑了") -> true
                else -> false
            }
        }
        
        matchingEvent?.let { showEvent(it) }
    }
    
    // ============================================
    // 手动触发
    // ============================================
    
    /**
     * 手动触发心情不好事件
     */
    suspend fun triggerBadMood() {
        triggerMoodEvent(MoodTrigger.BAD_MOOD)
    }
    
    /**
     * 手动触发闲置事件
     */
    suspend fun triggerIdle() {
        updateUserState { state ->
            state.copy(idleTime = state.idleTime + 30 * 60 * 1000L)
        }
        triggerMoodEvent(MoodTrigger.IDLE)
    }
    
    /**
     * 手动触发重置事件
     */
    suspend fun triggerReset() {
        reset()
        triggerMoodEvent(MoodTrigger.RESET)
    }
}

/**
 * 身体动作
 */
enum class BodyAction {
    DRINK_WATER,
    BRUSH_TEETH,
    SHOWER,
    YAWN,
    RUB_EYES,
    HAIR_LOSS
}

/**
 * 家务动作
 */
enum class HouseworkAction {
    EMPTY_TRASH,
    CLEAN_DESK,
    WASH_CLOTHES,
    TRASH_FULL,
    DESK_DUSTY,
    CLOTHES_PILE
}

/**
 * 心情触发
 */
enum class MoodTrigger {
    BAD_MOOD,
    IDLE,
    BORED,
    GOOD_STATUS,
    RESET
}
