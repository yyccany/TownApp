package com.example.townapp.npc.util

import com.example.townapp.npc.repository.NpcRepository

/**
 * NPC 生命周期管理器（独立时间线核心）
 *
 * 核心价值：
 * 1. NPC 拥有独立人生时间线，不依附玩家交互
 * 2. 玩家错过交互窗口，角色状态永久改变（时间不可逆）
 * 3. 贴合《去月球》《七年后》角色完整一生设计
 * 4. 严格遵循小镇项目铁律：仅修改数字字段，无任何长文本读写
 *
 * 严格分层：
 * - 不依赖 ViewModel / Compose
 * - 不导入任何 Dao
 * - 所有计算逻辑集中此处
 * - 唯一依赖：NpcRepository（通过 NpcDataLayer 注入）
 *
 * 触发时机：
 * - 玩家过完完整一年、年份自增后统一执行
 * - 由全局时间工具类或主 ViewModel 调用 onYearAdvanced()
 */
class NpcLifecycleManager(
    private val repository: NpcRepository
) {
    // ================== 生命周期常量 ==================

    private companion object {
        /** 退休年龄 */
        const val RETIREMENT_AGE = 60

        /** 病休触发年龄 */
        const val SICK_LEAVE_AGE = 70

        /** 离世年龄上限 */
        const val PASS_AWAY_AGE = 90

        /** 青年阶段上限 */
        const val YOUTH_AGE = 30

        /** 壮年阶段上限 */
        const val MIDDLE_AGE = 50

        /** 回忆标记间隔（年） */
        val MEMORY_MARK_INTERVALS = listOf(5, 10, 20, 30)

        /** 工作状态定义 */
        const val WORK_STATE_UNEMPLOYED = 0
        const val WORK_STATE_EMPLOYED = 1
        const val WORK_STATE_RETIRED = 2
        const val WORK_STATE_SICK = 3
        const val WORK_STATE_PASSED_AWAY = 4
    }

    // ================== 年度迭代主入口 ==================

    /**
     * 年度迭代：每年调用一次
     * 遍历所有 NPC，按规则推进年龄、健康、工作状态、回忆标记
     *
     * @return 本年度触发的回忆事件列表（供 ViewModel 派发弹窗）
     */
    fun onYearAdvanced(): List<LifecycleEvent> {
        val allStatuses = repository.getAllStatuses()
        val updatedStatuses = mutableListOf<com.example.townapp.npc.model.NpcStatus>()
        val events = mutableListOf<LifecycleEvent>()

        for (status in allStatuses) {
            // 已离世的 NPC 跳过
            if (status.workStateId == WORK_STATE_PASSED_AWAY) continue

            val newStatus = evolveOneYear(status)
            updatedStatuses.add(newStatus)

            // 检查是否产生回忆标记
            val newLivedYears = newStatus.livedYears
            val oldLivedYears = status.livedYears
            if (MEMORY_MARK_INTERVALS.contains(newLivedYears) && newLivedYears > oldLivedYears) {
                events.add(
                    LifecycleEvent.MemoryTriggered(
                        npcId = newStatus.npcId,
                        markId = generateMemoryMarkId(newStatus.npcId, newLivedYears),
                        yearsLived = newLivedYears
                    )
                )
            }

            // 检查是否触发重大状态变更
            if (newStatus.workStateId != status.workStateId) {
                events.add(
                    LifecycleEvent.WorkStateChanged(
                        npcId = newStatus.npcId,
                        oldState = status.workStateId,
                        newState = newStatus.workStateId,
                        newAge = newStatus.age
                    )
                )
            }
        }

        // 一次性批量写入，避免 N 次单独更新
        if (updatedStatuses.isNotEmpty()) {
            repository.batchUpdateStatus(updatedStatuses)
        }

        return events
    }

    // ================== 单 NPC 一年推进规则 ==================

    /**
     * 单个 NPC 一年状态推进
     * 纯函数：只根据输入计算输出，无副作用
     */
    private fun evolveOneYear(status: com.example.townapp.npc.model.NpcStatus): com.example.townapp.npc.model.NpcStatus {
        val newAge = status.age + 1
        val newLivedYears = status.livedYears + 1

        // 健康度衰减：青年/壮年 -1，中年 -2，老年 -3
        val healthDecay = when {
            newAge <= YOUTH_AGE -> 0  // 青年几乎不掉血
            newAge <= MIDDLE_AGE -> 1
            newAge < RETIREMENT_AGE -> 2
            newAge < SICK_LEAVE_AGE -> 3
            else -> 5  // 老年加速衰减
        }
        val newHealth = (status.health - healthDecay).coerceAtLeast(0)

        // 工作状态自动推进
        val newWorkState = computeWorkState(status.workStateId, newAge, newHealth)

        // 离世后不再推进
        if (newWorkState == WORK_STATE_PASSED_AWAY) {
            return status.copy(
                age = newAge,
                livedYears = newLivedYears,
                health = 0,
                workStateId = WORK_STATE_PASSED_AWAY
            )
        }

        return status.copy(
            age = newAge,
            livedYears = newLivedYears,
            health = newHealth,
            workStateId = newWorkState
        )
    }

    /**
     * 根据年龄 + 健康度计算工作状态
     * 状态机：
     * - 在职 → 退休（年龄 >= 60）
     * - 在职 → 病休（健康 < 30 且未退休）
     * - 病休 → 退休（年龄到 60）
     * - 任何 → 离世（年龄 >= 90 或 健康归零且年龄 >= 80）
     */
    private fun computeWorkState(currentState: Int, age: Int, health: Int): Int {
        // 年龄到上限 → 离世
        if (age >= PASS_AWAY_AGE) return WORK_STATE_PASSED_AWAY

        // 健康归零 + 老年 → 离世
        if (health <= 0 && age >= 80) return WORK_STATE_PASSED_AWAY

        // 已是离世状态保持
        if (currentState == WORK_STATE_PASSED_AWAY) return WORK_STATE_PASSED_AWAY

        // 已是退休或病休状态，仅在年龄到 60 时从病休→退休
        if (currentState == WORK_STATE_SICK && age >= RETIREMENT_AGE) {
            return WORK_STATE_RETIRED
        }

        // 在职 → 退休
        if (currentState == WORK_STATE_EMPLOYED && age >= RETIREMENT_AGE) {
            return WORK_STATE_RETIRED
        }

        // 在职 → 病休（健康过低）
        if (currentState == WORK_STATE_EMPLOYED && health < 30) {
            return WORK_STATE_SICK
        }

        // 默认保持当前状态
        return currentState
    }

    // ================== 回忆标记生成 ==================

    /**
     * 根据 npcId + 累计年数生成唯一回忆 markId
     * 规则：markId = npcId * 100 + livedYears
     * 例如：npcId=1，5年 → 105；10年 → 110
     * 对应 assets/text/npc/memory/memory_group_*.json 中的 memory_105 / memory_110
     */
    fun generateMemoryMarkId(npcId: Int, livedYears: Int): Int {
        return npcId * 100 + livedYears
    }

    /**
     * 清除已触发的回忆标记（弹窗展示完毕后调用）
     */
    fun clearMemoryMark(npcId: Int) {
        val status = repository.getNpcStatus(npcId)
        if (status.memoryTriggerMark != 0) {
            repository.batchUpdateStatus(listOf(status.copy(memoryTriggerMark = 0)))
        }
    }

    // ================== 时间计算工具 ==================

    /**
     * 根据年龄判断人生阶段
     */
    fun getLifeStage(age: Int): LifeStage = when {
        age < YOUTH_AGE -> LifeStage.YOUTH
        age < MIDDLE_AGE -> LifeStage.PRIME
        age < RETIREMENT_AGE -> LifeStage.MIDDLE
        else -> LifeStage.ELDER
    }
}

/**
 * 人生阶段枚举
 */
enum class LifeStage(val displayName: String) {
    YOUTH("青年"),
    PRIME("壮年"),
    MIDDLE("中年"),
    ELDER("晚年")
}

/**
 * 生命周期事件（由 Manager 发出，ViewModel 订阅消费）
 */
sealed class LifecycleEvent {
    /** 回忆触发 */
    data class MemoryTriggered(
        val npcId: Int,
        val markId: Int,
        val yearsLived: Int
    ) : LifecycleEvent()

    /** 工作状态变更 */
    data class WorkStateChanged(
        val npcId: Int,
        val oldState: Int,
        val newState: Int,
        val newAge: Int
    ) : LifecycleEvent()
}
