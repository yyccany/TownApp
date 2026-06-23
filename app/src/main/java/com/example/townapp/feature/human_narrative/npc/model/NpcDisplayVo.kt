package com.example.townapp.feature.human_narrative.npc.model

import com.example.townapp.core.constants.SimulationConstants

/**
 * NPC 展示模型（ViewModel → Compose 的唯一数据载体）
 *
 * 严格遵循小镇项目铁律：
 * 1. 这是 ViewModel 输出给 Compose 的唯一数据格式
 * 2. 所有数字 ID 已在 Repository 层转换为完整文本
 * 3. Compose 页面看不到任何 ID，只看到最终展示文字
 * 4. 纯数据类，无任何业务逻辑、无任何文件 IO
 *
 * 数据流转：
 * NpcBase (Dao) → NpcDisplayVo (Repository 转换) → ViewModel 状态流 → Compose 渲染
 */
data class NpcDisplayVo(
    /** NPC ID（仅用于交互回调，Compose 不展示） */
    val npcId: Int,

    /** 职业 ID（用于获取专属氛围色，不展示） */
    val jobId: Int,

    /** NPC 姓名（已转换为完整文本） */
    val name: String,

    /** 职业名称（已转换为完整文本） */
    val jobName: String,

    /** 当前季节名称（已转换为完整文本） */
    val seasonName: String,

    /** 像素地图X坐标 */
    val x: Int,

    /** 像素地图Y坐标 */
    val y: Int,

    /** 头像资源ID */
    val portraitId: Int,

    /** 当前好感度 */
    val favor: Int,

    /** 当前亲密度 */
    val intimacy: Int,

    /** 当前对话文本（已根据职业+季节+好感匹配完成） */
    val currentDialog: String,

    /** 好感分级标签（低/中/高，用于 UI 展示） */
    val favorLabel: String,

    /** 当前心情等级（1-5，用于漂浮独白触发和氛围色调整） */
    val moodLevel: Int = 3,

    // ================== 时间使用情况（严格遵循人人均等 24 小时） ==================

    /** 当日总可用逻辑分钟（固定 1440） */
    val totalLogicMinutes: Int = SimulationConstants.LOGIC_MINUTES_PER_DAY,

    /** 已消耗的工作时长（分钟） */
    val usedWorkMinutes: Int = 0,

    /** 已消耗的社交时长（分钟） */
    val usedSocialMinutes: Int = 0,

    /** 已消耗的睡眠时长（分钟） */
    val usedSleepMinutes: Int = 0,

    /** 已消耗的深夜独处时长（分钟） */
    val usedNightMinutes: Int = 0,

    /** 剩余当日生命时长（分钟） */
    val remainingMinutes: Int = SimulationConstants.LOGIC_MINUTES_PER_DAY
) {
    /** 已消耗的总时长 */
    val usedMinutes: Int get() = usedWorkMinutes + usedSocialMinutes + usedSleepMinutes + usedNightMinutes

    /** 进度百分比（0-100） */
    val usagePercent: Int get() = (usedMinutes * 100) / totalLogicMinutes

    /** 是否为深夜时段 */
    val isNightTime: Boolean get() = remainingMinutes <= SimulationConstants.NIGHT_MINUTES
}
