package com.example.townapp.domain.usecase

import com.example.townapp.domain.engine.SimulationEngine
import com.example.townapp.core.state.WorldType
import com.example.townapp.domain.spacemodel.WorldModelRepository

/**
 * 切换社会模型用例。
 *
 * 职责：
 * - 从 [WorldModelRepository] 读取对应模型配置
 * - 停止当前模拟并清空内存数据
 * - 应用新空间参数后恢复运行
 */
class SwitchWorldUseCase {

    /**
     * 切换至指定世界模型。
     *
     * @param worldType 目标世界类型
     */
    operator fun invoke(worldType: WorldType) {
        val config = WorldModelRepository.getConfig(worldType)

        // 停止当前模拟
        SimulationEngine.stop()

        // 清空当前内存数据
        SimulationEngine.reset()

        // 应用世界模型配置到空间状态
        SimulationEngine.updateSpaceState(
            rent = config.rent,
            salary = config.salary,
            commuteMinutes = config.commuteMinutes
        )
    }
}
