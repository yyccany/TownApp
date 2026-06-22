package com.example.townapp.npc.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 小镇全局叙事状态（落实自由平等原则）
 *
 * 核心规则：
 * 1. 玩家完全拥有"纯观光模式"自由，可随时开启/关闭所有漂浮文字
 * 2. 玩家完全拥有"全域隐藏 UI"自由，漂浮文字 + 状态面板 + 菜单全部收起
 * 3. 不强迫阅读、不强迫交互，尊重沉默与独处
 * 4. 所有状态变化不影响底层数据，仅调整 UI 渲染规则
 */
object TownNarrativeState {
    /**
     * 静默观光模式开关
     * 开启后：全局隐藏所有漂浮文字，只剩动态 NPC、四季昼夜、人物专属光影
     */
    private val _silentTourMode = MutableStateFlow(false)
    val silentTourMode = _silentTourMode.asStateFlow()

    /**
     * 全域隐藏 UI 模式开关
     * 开启后：漂浮文字 + 状态面板 + 菜单全部收起，只剩完整小镇画面
     */
    private val _hideAllUI = MutableStateFlow(false)
    val hideAllUI = _hideAllUI.asStateFlow()

    /**
     * 当前同时显示的漂浮文字数量限制（最大值）
     * 遵循实事求是原则：普通人内心独白极少作为环境点缀，不是核心叙事载体
     */
    const val MAX_FLOATING_THOUGHTS = 2

    /**
     * 漂浮文字固定透明度（10%）
     * 极低存在感，不干扰画面氛围，不想交互完全可以无视
     */
    const val FLOATING_TEXT_ALPHA = 0.1f

    /**
     * 漂浮文字极小字号（10sp）
     */
    const val FLOATING_TEXT_SIZE_SP = 10

    /**
     * 漂浮文字极短长度限制（4~8 个字）
     */
    const val MAX_THOUGHT_LENGTH = 8

    /**
     * 切换静默观光模式
     * 落实自由原则：玩家可随时选择完全无文字的纯画面体验
     */
    fun toggleSilentTourMode() {
        _silentTourMode.value = !_silentTourMode.value
    }

    /**
     * 设置静默观光模式
     */
    fun setSilentTourMode(enabled: Boolean) {
        _silentTourMode.value = enabled
    }

    /**
     * 切换全域隐藏 UI 模式
     * 落实自由原则：玩家可一键收起所有悬浮 UI，实现完整无 UI 纯画面浏览
     */
    fun toggleHideAllUI() {
        _hideAllUI.value = !_hideAllUI.value
    }

    /**
     * 设置全域隐藏 UI 模式
     */
    fun setHideAllUI(enabled: Boolean) {
        _hideAllUI.value = enabled
    }
}

/**
 * NPC 漂浮独白数据结构
 *
 * 落实实事求是原则：
 * - 内心碎语极短（4~8 个字），仅作为环境点缀
 * - 不传递完整叙事，只是生活碎片的微弱回响
 * - 贴合普通人含蓄的表达习惯，不会长篇大论倾诉
 */
data class NpcFloatingThought(
    val npcId: Int,
    val npcName: String,
    /** 独白内容，已自动截断为 8 字以内 */
    val content: String,
    /** 屏幕坐标 X（0~1） */
    val x: Float,
    /** 屏幕坐标 Y（0~1） */
    val y: Float,
    /** 剩余显示时长（毫秒） */
    val remainingDuration: Long
)