package com.example.townapp.domain.usecase

import com.example.townapp.domain.engine.SimulationEngine
import com.example.townapp.npc.model.CognitionState

/**
 * 重置模拟世界用例。
 *
 * 提供便捷的单例调用方式，一键恢复初始状态。
 *
 * ═══════════════════════════════════════════════════════════════
 * 多周目认知记忆继承（P2 方案 B）
 *
 * 重置策略：
 * - 保留：认知等级（岁月沉淀不会因重开而消失）
 * - 重置：财富、NPC 好感、年份进度
 * - 二周目及以上：直接解锁智慧者视角
 *
 * 传递价值观：
 * 「岁月得失会清零，但观察与自省沉淀的认知永久留存」
 * 「鼓励玩家尝试完全不同的人生选择」
 *
 * 纯叙事拓展：无多周目成就、无奖励、无强制打卡负担
 * ═══════════════════════════════════════════════════════════════
 */
class ResetSimulationUseCase {

    /**
     * 执行多周目重置
     *
     * @param preserveCognition 是否保留认知状态（跨周目传递）
     *                         - true：保留认知（进入下一周目）
     *                         - false：完全重置（用于测试）
     * @param startAge 开局年龄（默认 20 岁）
     */
    operator fun invoke(preserveCognition: Boolean = true, startAge: Int = 20) {
        if (preserveCognition) {
            // 进入下一周目：先更新认知状态，再重置游戏
            CognitionState.enterNextRound()
        }

        // 重置游戏引擎（财富、NPC 好感、年份进度等全部清零）
        SimulationEngine.resetWithAge(startAge)
    }

    /**
     * 保留认知进入下一周目
     *
     * 核心价值：
     * - 财富清零，好感重置
     * - 认知等级保持（跨周目永存）
     * - 二周目直接解锁智慧者通透对话视角
     */
    fun startNextRound(startAge: Int = 20) {
        invoke(preserveCognition = true, startAge = startAge)
    }

    /**
     * 完全重置（用于测试或特殊场景）
     *
     * 注意：此方法会清空所有认知状态
     */
    fun fullReset(startAge: Int = 20) {
        invoke(preserveCognition = false, startAge = startAge)
    }
}
