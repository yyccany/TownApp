package com.example.townapp.npc.model

/**
 * NPC 人生时间线展示模型（档案馆专用）
 *
 * 严格遵循小镇项目铁律：
 * 1. 纯数据类，无任何业务逻辑、无文件 IO
 * 2. 所有数字 ID 已在 Repository 层转换为完整文本
 * 3. Compose 页面看不到任何 ID，只看到最终展示文字
 *
 * 数据流转：
 * NpcBase + NpcStatus → Repository 组装 → NpcTimelineVo → Compose 渲染
 *
 * 认知联动（落实「以人为本」）：
 * - 高认知浏览时自动追加中立注解
 * - 注解拆解比较偏差、幸存者偏差，极淡金色低透明度小字
 */
data class NpcTimelineVo(
    /** NPC 唯一 ID（仅用于交互回调） */
    val npcId: Int,

    /** 姓名（已转换为完整文本） */
    val name: String,

    /** 职业（已转换为完整文本） */
    val jobName: String,

    /** 当前年龄 */
    val currentAge: Int,

    /** 当前健康度 0-100 */
    val health: Int,

    /** 工作状态描述（已转换：在职/退休/病休/离世） */
    val workStateDesc: String,

    /** 专属色调（用于浏览时自动切换光影） */
    val palette: TonePaletteVo?,

    /** 时间线节点列表 */
    val timelineNodes: List<TimelineNode>,

    /**
     * 认知中立注解（高认知时显示）
     * 极淡金色低透明度小字，拆解比较偏差、幸存者偏差
     * 示例："每个人的人生节奏不同，快慢没有优劣"
     */
    val cognitionAnnotation: String = ""
)

/**
 * 时间线节点（年度生命周期 + 回忆碎片 + 关键人生节点）
 */
data class TimelineNode(
    /** 节点类型 */
    val type: NodeType,

    /** 年份（游戏内年份） */
    val year: Int,

    /** NPC 在该年份的年龄 */
    val ageAtYear: Int,

    /** 季节（已转换为完整文本） */
    val seasonName: String,

    /** 节点标题 */
    val title: String,

    /** 节点描述文本 */
    val description: String,

    /** 是否已解锁（与玩家交互后解锁） */
    val unlocked: Boolean,

    /** 关联记忆碎片 ID（可选，0 表示无关联） */
    val memoryMarkId: Int = 0
)

/**
 * 时间线节点类型
 */
enum class NodeType {
    /** 年度生日节点 */
    YEARLY,

    /** 回忆碎片节点 */
    MEMORY,

    /** 职业变更节点 */
    CAREER_CHANGE,

    /** 健康事件节点 */
    HEALTH_EVENT,

    /** 退休节点 */
    RETIREMENT,

    /** 离世节点 */
    DEATH
}