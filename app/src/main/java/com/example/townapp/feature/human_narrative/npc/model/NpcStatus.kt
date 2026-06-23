package com.example.townapp.feature.human_narrative.npc.model

/**
 * NPC 动态状态实体
 *
 * 严格遵循小镇项目铁律：
 * 1. Entity 只存运行时变化的数字状态
 * 2. 严禁存任何文本、描述、长字段
 * 3. 状态值采用约定范围：好感/亲密度 [0, 100]
 *
 * 字段说明（按用途分组）：
 * - 基础交互状态：npcId, favor, intimacy, lastInteractionTime
 * - 时间/季节：currentSeasonId
 * - 对话深度：unlockedDialogLevel
 * - 生命周期（由 NpcLifecycleManager 自动维护）：
 *   - age：NPC 年龄（每年自动 +1）
 *   - health：健康度 0-100（随年龄逐年下降）
 *   - workStateId：工作状态（0=失业/1=在职/2=退休/3=病休/4=离世）
 *   - memoryTriggerMark：回忆触发标记（每 5/10/20 年自动生成，触发回忆弹窗）
 */
data class NpcStatus(
    /** 关联 NpcBase.id */
    val npcId: Int,

    /** 好感度 0-100 */
    val favor: Int,

    /** 亲密度 0-100 */
    val intimacy: Int,

    /** 当前季节ID（1=春、2=夏、3=秋、4=冬） */
    val currentSeasonId: Int,

    /** 上次互动时间戳（毫秒） */
    val lastInteractionTime: Long,

    /** 解锁的对话层数（0=初始、1=浅层、2=深层、3=内心） */
    val unlockedDialogLevel: Int = 0,

    // ================== 生命周期字段（由 NpcLifecycleManager 维护） ==================

    /** NPC 年龄（默认 25，每年 +1） */
    val age: Int = 25,

    /** 健康度 0-100（默认 100，随年龄逐年下降，老年加速衰减） */
    val health: Int = 100,

    /** 工作状态（0=失业/1=在职/2=退休/3=病休/4=离世） */
    val workStateId: Int = 1,

    /** 回忆触发标记（0=无标记，非0=需触发对应 markId 的回忆） */
    val memoryTriggerMark: Int = 0,

    /** 累计存活年数（用于触发 5/10/20 年节点回忆） */
    val livedYears: Int = 0
) {
    companion object {
        /** 默认状态：好感 30，亲密度 0，25 岁在职健康 */
        fun default(npcId: Int, seasonId: Int = 1): NpcStatus = NpcStatus(
            npcId = npcId,
            favor = 30,
            intimacy = 0,
            currentSeasonId = seasonId,
            lastInteractionTime = 0L,
            unlockedDialogLevel = 0,
            age = 25,
            health = 100,
            workStateId = 1,
            memoryTriggerMark = 0,
            livedYears = 0
        )
    }
}
