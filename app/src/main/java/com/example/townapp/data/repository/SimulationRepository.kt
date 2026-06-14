package com.example.townapp.data.repository

import com.example.townapp.data.memorydb.BodyMemoryTable
import com.example.townapp.data.memorydb.SpaceMemoryTable
import com.example.townapp.data.memorydb.SpiritMemoryTable
import com.example.townapp.domain.engine.GameTime
import com.example.townapp.domain.engine.PlayerState
import com.example.townapp.domain.spacemodel.SpaceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 模拟器仓库 —— 唯一数据出入口
 *
 * 职责：
 * - 对接 memorydb 三层内存表
 * - 领域层（Domain）只调用 Repository，不直接操作内存表
 * - 切换模型、重置模拟时，主动清空对应内存表数据
 * - **状态快照统一维护**：所有状态对象均为不可变，变更通过 copy() 生成新实例
 * - **StateFlow 驱动**：对外仅暴露只读 StateFlow，UI/ViewModel 通过订阅驱动刷新
 *
 * 设计原则：
 * - 全程内存运行，关闭 App 自动清空
 * - 禁止出现任何 SP、Room、File、数据库读写代码
 */
object SimulationRepository {

    // ==================== 内部可变流 ====================

    private val _gameTimeFlow = MutableStateFlow(GameTime())
    private val _playerStateFlow = MutableStateFlow(PlayerState())
    private val _spaceStateFlow = MutableStateFlow(SpaceState())
    private val _isRunningFlow = MutableStateFlow(false)
    private val _simulationSpeedFlow = MutableStateFlow(1.0)

    // ==================== 对外只读流 ====================

    val gameTimeFlow: StateFlow<GameTime> = _gameTimeFlow.asStateFlow()
    val playerStateFlow: StateFlow<PlayerState> = _playerStateFlow.asStateFlow()
    val spaceStateFlow: StateFlow<SpaceState> = _spaceStateFlow.asStateFlow()
    val isRunningFlow: StateFlow<Boolean> = _isRunningFlow.asStateFlow()
    val simulationSpeedFlow: StateFlow<Double> = _simulationSpeedFlow.asStateFlow()

    /** 事件历史记录（静默模式，不弹窗） */
    internal val eventHistory = mutableListOf<String>()

    // ==================== 状态写入接口（唯一合法入口） ====================

    /**
     * 提交新的游戏时间快照，替换当前值。
     *
     * @param newGameTime 新的不可变时间快照
     */
    fun commitGameTime(newGameTime: GameTime) {
        _gameTimeFlow.value = newGameTime
    }

    /**
     * 提交新的玩家状态快照，替换当前值。
     *
     * @param newPlayerState 新的不可变玩家状态快照
     */
    fun commitPlayerState(newPlayerState: PlayerState) {
        _playerStateFlow.value = newPlayerState
    }

    /**
     * 提交新的空间状态快照，替换当前值。
     *
     * @param newSpaceState 新的不可变空间状态快照
     */
    fun commitSpaceState(newSpaceState: SpaceState) {
        _spaceStateFlow.value = newSpaceState
    }

    /**
     * 基于当前玩家状态进行函数式变换并提交。
     *
     * @param transform 变换函数，接收当前状态并返回新状态
     */
    fun updatePlayerState(transform: (PlayerState) -> PlayerState) {
        _playerStateFlow.value = transform(_playerStateFlow.value)
    }

    /**
     * 基于当前空间状态进行函数式变换并提交。
     *
     * @param transform 变换函数，接收当前状态并返回新状态
     */
    fun updateSpaceState(transform: (SpaceState) -> SpaceState) {
        _spaceStateFlow.value = transform(_spaceStateFlow.value)
    }

    // ==================== 时间操作 ====================

    /**
     * 推进游戏时间指定小时数，自动处理跨天与周数更新。
     *
     * @param hours 要推进的小时数，需大于等于 0
     */
    fun advanceTime(hours: Int) {
        var newHours = _gameTimeFlow.value.hours + hours
        var newDays = _gameTimeFlow.value.days
        while (newHours >= 24) {
            newHours -= 24
            newDays++
        }
        _gameTimeFlow.value = _gameTimeFlow.value.copy(
            hours = newHours,
            days = newDays,
            week = (newDays / 7) + 1,
            dayOfWeek = ((newDays - 1) % 7) + 1
        )
    }

    /**
     * 设置当前季节。
     *
     * @param season 季节标识，如 "spring"/"summer"/"autumn"/"winter"
     */
    fun setSeason(season: String) {
        _gameTimeFlow.value = _gameTimeFlow.value.copy(season = season)
    }

    // ==================== 状态读写 ====================

    /**
     * 从三层内存表同步数据到 StateFlow。
     * 每次心跳或状态变更后调用，保证 Repository 与内存表数据一致。
     */
    fun syncFromMemoryTables() {
        var ps = _playerStateFlow.value
        var ss = _spaceStateFlow.value
        ps = BodyMemoryTable.syncTo(ps)
        ss = SpaceMemoryTable.syncToSpace(ss)
        ps = SpaceMemoryTable.syncToPlayer(ps)
        ps = SpiritMemoryTable.syncTo(ps)
        _playerStateFlow.value = ps
        _spaceStateFlow.value = ss
    }

    fun syncToMemoryTables() {
        BodyMemoryTable.syncFrom(_playerStateFlow.value)
        SpaceMemoryTable.syncFromSpace(_spaceStateFlow.value)
        SpaceMemoryTable.syncFromPlayer(_playerStateFlow.value)
        SpiritMemoryTable.syncFrom(_playerStateFlow.value)
    }

    // ==================== 模拟控制 ====================

    fun setSpeed(speed: Double) {
        _simulationSpeedFlow.value = speed.coerceIn(0.5, 4.0)
    }

    fun setRunning(running: Boolean) {
        _isRunningFlow.value = running
    }

    // ==================== 重置与清空 ====================

    fun fullReset() {
        BodyMemoryTable.clear()
        SpaceMemoryTable.clear()
        SpiritMemoryTable.clear()

        _gameTimeFlow.value = GameTime()
        _playerStateFlow.value = PlayerState()
        _spaceStateFlow.value = SpaceState()
        _isRunningFlow.value = false
        _simulationSpeedFlow.value = 1.0
    }

    fun resetSpaceOnly() {
        SpaceMemoryTable.clear()
        syncFromMemoryTables()
    }

    // ==================== 事件记录 ====================

    fun addDailyEvent(event: String) {
        SpiritMemoryTable.addDailyEvent(event)
        _playerStateFlow.value = _playerStateFlow.value.copy(
            dailyEvents = SpiritMemoryTable.dailyEvents.toList()
        )
    }

    fun addEventHistory(event: String) {
        eventHistory.add(event)
        if (eventHistory.size > 100) {
            eventHistory.removeAt(0)
        }
    }

    fun addIdiomRecord(idiom: String) {
        SpiritMemoryTable.addIdiom(idiom)
    }

    fun clearDailyEvents() {
        SpiritMemoryTable.dailyEvents.clear()
        _playerStateFlow.value = _playerStateFlow.value.copy(dailyEvents = emptyList())
    }

    // ==================== 每日重置 ====================

    fun dailyReset() {
        _playerStateFlow.value = _playerStateFlow.value.dailyReset()
    }
}
