package com.example.townapp.domain.usecase

import com.example.townapp.domain.engine.SimulationEngine
import com.example.townapp.domain.engine.SimulationResult
import com.example.townapp.domain.engine.TimeMode
import com.example.townapp.data.repository.SimulationRepository

/**
 * 模拟控制用例
 *
 * 职责：封装启停、调速、重置、时间跳转等模拟控制操作。
 * 不直接持有 View/ViewModel 引用，仅依赖 Repository 与 Engine。
 */
class SimulationControlUseCase {

    /**
     * 启动模拟。
     *
     * @param mode 时间运行模式（前台/后台/快进）
     */
    suspend fun start(mode: TimeMode) {
        SimulationEngine.start(mode)
    }

    /**
     * 停止模拟，冻结时间推进与状态衰减。
     */
    fun stop() {
        SimulationEngine.stop()
    }

    /**
     * 完全重置模拟世界，清空所有状态并恢复初始值。
     */
    fun reset() {
        SimulationEngine.reset()
    }

    /**
     * 使用指定人生路径重置模拟世界。
     *
     * @param age 起始年龄
     * @param lifePathId 人生路径标识
     */
    fun resetWithLifePath(age: Int, lifePathId: String) {
        SimulationEngine.resetWithLifePath(age, lifePathId)
    }

    /**
     * 快进指定天数，批量推进时间与状态衰减。
     *
     * @param days 快进天数
     * @return 快进结果，包含期间触发的事件日志
     */
    suspend fun fastForward(days: Int): SimulationResult {
        return SimulationEngine.fastForward(days)
    }

    /**
     * 获取当前模拟速度倍率。
     *
     * @return 速度倍率，1.0 为正常速度
     */
    fun getSimulationSpeed(): Double = SimulationEngine.getSimulationSpeed()

    /**
     * 设置模拟速度倍率。
     *
     * @param speed 速度倍率，需大于 0
     */
    fun setSimulationSpeed(speed: Double) {
        SimulationEngine.setSimulationSpeed(speed)
    }

    /**
     * 获取当前完整状态快照。
     *
     * @return 不可变状态快照
     */
    fun getStateSnapshot() = SimulationEngine.getStateSnapshot()

    /**
     * 更新空间状态（租金、薪资、通勤时间）。
     *
     * @param rent 月租金
     * @param salary 月薪资
     * @param commuteMinutes 单程通勤分钟数
     */
    fun updateSpaceState(rent: Double, salary: Double, commuteMinutes: Int) {
        SimulationEngine.updateSpaceState(rent, salary, commuteMinutes)
    }
}
