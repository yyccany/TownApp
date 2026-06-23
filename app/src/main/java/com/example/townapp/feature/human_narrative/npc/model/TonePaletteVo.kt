package com.example.townapp.feature.human_narrative.npc.model

import androidx.compose.ui.graphics.Color

/**
 * NPC 专属氛围色调展示模型（ViewModel → Compose 的唯一数据载体）
 *
 * 严格遵循小镇项目铁律：
 * 1. 这是 ViewModel 输出给 NpcMoodOverlay 的唯一数据格式
 * 2. tonePaletteId 已在 Repository/TextAssetLoader 层转换为 Color + Float
 * 3. Compose 组件看不到任何 ID，只看到最终渲染参数
 * 4. 纯数据类，无任何业务逻辑、无任何文件 IO
 *
 * 落实平等原则：
 * - annotationTextId 对应的注解是客观描述，不附带褒贬
 * - 暖棕老人只是岁月沉淀，灰黄工人只是谋生常态，没有优劣之分
 * - 注解仅档案馆查阅时轻量展示，对话弹窗不出现评判文字
 *
 * 数据流转：
 * tonePaletteId (NpcBase) → TextAssetLoader 解析 → TonePaletteVo → NpcMoodOverlay 渲染
 */
data class TonePaletteVo(
    /** 调色板 ID（仅用于调试日志，UI 不展示） */
    val paletteId: Int,

    /** 名称（仅用于调试，UI 不展示） */
    val paletteName: String,

    /** 基础色调（已从 hex 解析为 Color） */
    val baseTint: Color,

    /** 暗角强度 0f ~ 1f */
    val vignetteBase: Float,

    /** 饱和度 0.5f ~ 1.3f */
    val saturation: Float,

    /**
     * 中立注解文本（客观描述，无价值评判）
     * 仅档案馆浏览时轻量展示，对话弹窗不出现评判文字
     * 示例："岁月沉淀的从容"、"谋生常态的质朴"、"理性克制的专业"
     */
    val annotation: String = ""
)
