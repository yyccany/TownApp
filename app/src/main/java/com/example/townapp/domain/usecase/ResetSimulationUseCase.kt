package com.example.townapp.domain.usecase

import com.example.townapp.domain.engine.SimulationEngine

/**
 * 重置模拟世界用例。
 *
 * 提供便捷的单例调用方式，一键恢复初始状态。
 */
class ResetSimulationUseCase {

    /**
     * 执行重置，清空所有状态并恢复初始值。
     */
    operator fun invoke() {
        SimulationEngine.reset()
    }
}
