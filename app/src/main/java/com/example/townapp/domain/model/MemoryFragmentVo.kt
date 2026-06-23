package com.example.townapp.domain.model

/**
 * 记忆碎片展示模型（ViewModel → Compose 的唯一数据载体）
 *
 * 严格遵循小镇项目铁律：
 * 1. 这是 ViewModel 输出给 Compose 的唯一数据格式
 * 2. 所有 markId / groupId 已在 Repository 层转换为完整文本
 * 3. Compose 页面看不到任何 ID，只看到最终展示文字
 * 4. 纯数据类，无任何业务逻辑、无任何文件 IO
 *
 * 认知联动（落实「以人为本」）：
 * - 当前内容已根据玩家认知等级自动选择最匹配版本
 * - surfaceContent：表层视角（情绪直白，看见委屈）
 * - observeContent：观察视角（看见当时处境）
 * - wiseContent：通透视角（理解身不由己，自然和解）
 *
 * 数据流转：
 * markId (NpcStatus) → MemoryFragmentVo (Repository 转换) → ViewModel 状态流 → Compose 渲染
 */
data class MemoryFragmentVo(
    /** 记忆唯一 ID（仅用于交互回调，Compose 不展示） */
    val markId: Int,

    /** 记忆标题（已转换为完整文本） */
    val title: String,

    /**
     * 记忆内容（已根据玩家认知等级自动选择最匹配版本）
     * - cognitiveLevel 0-1 → surfaceContent
     * - cognitiveLevel 2-3 → observeContent
     * - cognitiveLevel 4-5 → wiseContent
     */
    val content: String,

    /** 发生年份（如：2025） */
    val year: Int,

    /** 当时玩家年龄 */
    val playerAgeAtTime: Int,

    /** 距离现在的年数（用于"X年前"展示） */
    val yearsAgo: Int,

    /** 季节名称（已转换为完整文本） */
    val seasonName: String,

    /** 关联 NPC 姓名（已转换为完整文本，可选） */
    val relatedNpcName: String = "",

    /** 背景音乐 ID（用于触发专属 BGM，UI 不处理） */
    val musicId: Int = 0,

    /**
     * 当前选中的认知视角标识（surface/observe/wise）
     * 用于视觉差异化渲染（暗角 / 柔光等）
     */
    val cognitionTier: String = "surface",

    /**
     * 表层视角解读（情绪直白版）
     * 玩家在低认知时看到的版本：看见委屈、不甘
     */
    val surfaceContent: String = "",

    /**
     * 观察视角解读（看见客观处境版）
     * 玩家在中认知时看到的版本：理解时代、生计、局限
     */
    val observeContent: String = "",

    /**
     * 通透视角解读（接纳无常版）
     * 玩家在高认知时看到的版本：理解身不由己，自然和解
     */
    val wiseContent: String = ""
)