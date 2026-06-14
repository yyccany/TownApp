package com.example.townapp.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.townapp.domain.engine.SimulationEngine
import com.example.townapp.core.state.SimulationStatus
import com.example.townapp.core.state.TimeMode
import com.example.townapp.data.repository.SimulationRepository
import com.example.townapp.domain.usecase.PlayerActionUseCase
import com.example.townapp.domain.usecase.SimulationControlUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

/**
 * 模拟器ViewModel
 *
 * 职责：
 * 1. 接收UI操作，调用 UseCase 层
 * 2. 向外暴露 Repository StateFlow（只读），UI 通过 collectAsState 订阅
 * 3. 生命周期跟随页面，退出自动销毁
 */
class SimulationViewModel : ViewModel() {

    // ==================== UseCase 实例 ====================

    private val playerActionUseCase = PlayerActionUseCase()
    private val simulationControlUseCase = SimulationControlUseCase()

    // ==================== 状态暴露（直接透传 Repository StateFlow） ====================

    /** 当前游戏时间 */
    val gameTime: StateFlow<com.example.townapp.domain.engine.GameTime> = SimulationRepository.gameTimeFlow

    /** 当前玩家状态快照 */
    val playerState: StateFlow<com.example.townapp.domain.engine.PlayerState> = SimulationRepository.playerStateFlow

    /** 当前空间状态 */
    val spaceState: StateFlow<com.example.townapp.domain.spacemodel.SpaceState> = SimulationRepository.spaceStateFlow

    /** 模拟运行状态（由 isRunningFlow 映射） */
    val simulationStatus: StateFlow<SimulationStatus> = SimulationRepository.isRunningFlow
        .map { isRunning ->
            if (isRunning) SimulationStatus.RUNNING else SimulationStatus.PAUSED
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SimulationStatus.IDLE)

    /** 当前时间模式（UI 本地状态） */
    val timeMode = mutableStateOf(TimeMode.FOREGROUND)

    /** 事件记录 */
    val eventHistory = mutableStateListOf<String>()

    /** 动作执行结果提示 */
    val actionResult = mutableStateOf("")

    /** 是否显示设置界面 */
    val showSetup = mutableStateOf(true)

    /** 是否显示食物选择器 */
    val showFoodSelector = mutableStateOf(false)

    /** 是否显示空间选择器 */
    val showSpaceSelector = mutableStateOf(false)

    /** 是否显示事件日志 */
    val showEventLog = mutableStateOf(false)

    /** 是否显示角色详情 */
    val showCharacterInfo = mutableStateOf(false)

    /** 食物列表 */
    val foodItems = mutableStateOf<List<com.example.townapp.data.FoodItem>>(emptyList())

    /** 可用空间列表 */
    val availableSpaces = mutableStateOf<List<com.example.townapp.data.repository.SpaceConfig>>(emptyList())

    init {
        SimulationEngine.onEventsTriggered = { newEvents ->
            newEvents.forEach { event ->
                eventHistory.add(0, event)
                if (eventHistory.size > 20) eventHistory.removeAt(eventHistory.lastIndex)
            }
        }
        loadFoodItems()
        loadSpaces()
    }

    // ==================== UI操作接口（调用 UseCase） ====================

    fun startSimulation(mode: TimeMode = TimeMode.FOREGROUND) {
        timeMode.value = mode
        viewModelScope.launch(Dispatchers.IO) {
            simulationControlUseCase.start(when (mode) {
                TimeMode.FOREGROUND -> com.example.townapp.domain.engine.TimeMode.FOREGROUND
                TimeMode.BACKGROUND -> com.example.townapp.domain.engine.TimeMode.BACKGROUND
                TimeMode.FAST_FORWARD -> com.example.townapp.domain.engine.TimeMode.FAST_FORWARD
            })
        }
    }

    fun stopSimulation() {
        simulationControlUseCase.stop()
    }

    fun toggleSimulation() {
        if (simulationStatus.value == SimulationStatus.RUNNING) {
            stopSimulation()
        } else {
            startSimulation(timeMode.value)
        }
    }

    fun fastForward(days: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = simulationControlUseCase.fastForward(days)
            result.dailySummaries.forEach { summary ->
                eventHistory.add(0, "第${summary.day}天: ${summary.events.joinToString(", ")}")
            }
        }
    }

    fun resetSimulation() {
        simulationControlUseCase.reset()
        eventHistory.clear()
        showSetup.value = true
    }

    fun startWithLifePath(age: Int, lifePathId: String) {
        simulationControlUseCase.resetWithLifePath(age, lifePathId)
        showSetup.value = false
    }

    fun eat(foodId: Int) {
        val result = playerActionUseCase.eat(foodId)
        actionResult.value = result.message
    }

    fun sleep(hours: Int) {
        val result = playerActionUseCase.sleep(hours)
        actionResult.value = result.message
    }

    fun work(hours: Int) {
        val result = playerActionUseCase.work(hours)
        actionResult.value = result.message
    }

    fun study(hours: Int) {
        val result = playerActionUseCase.study(hours)
        actionResult.value = result.message
    }

    fun walk(hours: Int) {
        val result = playerActionUseCase.walk(hours)
        actionResult.value = result.message
    }

    fun socialize(hours: Int) {
        val result = playerActionUseCase.socialize(hours)
        actionResult.value = result.message
    }

    fun meditate(hours: Int) {
        val result = playerActionUseCase.meditate(hours)
        actionResult.value = result.message
    }

    fun exercise(hours: Int) {
        val result = playerActionUseCase.exercise(hours)
        actionResult.value = result.message
    }

    fun read(hours: Int) {
        val result = playerActionUseCase.read(hours)
        actionResult.value = result.message
    }

    fun listenMusic(hours: Int) {
        val result = playerActionUseCase.listenMusic(hours)
        actionResult.value = result.message
    }

    fun cook(hours: Int) {
        val result = playerActionUseCase.cook(hours)
        actionResult.value = result.message
    }

    fun organize(hours: Int) {
        val result = playerActionUseCase.organize(hours)
        actionResult.value = result.message
    }

    fun watchMovie(hours: Int) {
        val result = playerActionUseCase.watchMovie(hours)
        actionResult.value = result.message
    }

    fun teaBreak(hours: Int) {
        val result = playerActionUseCase.teaBreak(hours)
        actionResult.value = result.message
    }

    fun tendPlant() {
        val result = playerActionUseCase.tendPlant()
        actionResult.value = result.message
    }

    fun journal(minutes: Int) {
        val result = playerActionUseCase.journal(minutes)
        actionResult.value = result.message
    }

    fun idle(hours: Int) {
        val result = playerActionUseCase.idle(hours)
        actionResult.value = result.message
    }

    fun changeSpace(spaceId: String) {
        val result = playerActionUseCase.changeSpace(spaceId)
        actionResult.value = result.message
    }

    fun payRent() {
        val result = playerActionUseCase.payRent()
        actionResult.value = result.message
    }

    fun clearDailyEvents() {
        SimulationRepository.clearDailyEvents()
    }

    fun clearActionResult() {
        actionResult.value = ""
    }

    // ==================== 私有方法 ====================

    private fun loadFoodItems() {
        foodItems.value = com.example.townapp.data.repository.UnifiedFoodRepository.getAllFoods()
    }

    private fun loadSpaces() {
        availableSpaces.value = com.example.townapp.data.repository.SpaceConfigRepository.getAllSpaces()
    }

    override fun onCleared() {
        super.onCleared()
        simulationControlUseCase.stop()
    }
}
