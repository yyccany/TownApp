package com.example.townapp.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * VERA 主题模式枚举
 */
enum class VeraThemeMode {
    /** 默认黑白极简（成年用户日常默认开启） */
    DefaultMonochrome,
    /** 马卡龙童趣彩色（手动切换，兼顾儿童使用） */
    MacaronChild
}

/**
 * VERA 语义色板——两套主题共用一套字段名，
 * 布局 / 交互 / 图标图形完全一致，仅色彩分离。
 *
 * 使用方式：
 * - 在 MainActivity 通过 CompositionLocalProvider 注入当前色板
 * - 详情页通过 LocalVeraColors.current 读取各语义字段
 * - 文字层级仍由 DictionaryTokens.Type 控制，不接入此色板
 */
@Immutable
data class VeraThemeColors(
    // ── 通用图标线条（兜底） ──
    val iconLine: Color,

    // ── 场景专用图标色 ──
    val iconCheck: Color,          // 空心对勾（原生合理场景）
    val iconWarning: Color,        // 空心警示三角（现代滥用场景）
    val iconStarEmpty: Color,      // 空心五角星（未收藏）
    val iconStarFilled: Color,     // 实心五角星（已收藏）
    val iconCircleNumber: Color,   // 空心圆圈序号 ①②③
    val iconExpandCollapse: Color, // 全局展开/收起图标
    val iconRandomArrow: Color,    // 随机箭头图标

    // ── 按钮控件色 ──
    val buttonBorder: Color,       // 展开/收起按钮描边
    val buttonText: Color,         // 展开/收起按钮文字

    // ── 警示提示区块 ──
    val spotlightDot: Color,       // 警示空心圆点
    val spotlightDivider: Color,   // 警示顶部分割线

    // ── 随机词条卡片 ──
    val randomCardBg: Color,       // 随机卡片底色
    val randomCardAccent: Color,   // 随机卡片强调色（图标+标签文字）

    // ── 笔记编辑区 ──
    val noteAccent: Color,         // 写笔记/编辑按钮文字色
    val noteBorder: Color,         // 笔记输入框聚焦描边

    // ── 标签（分类 / 毒性等级） ──
    val tagBg: Color,              // 分类标签底色（黑白版透明，彩色版极浅马卡龙）
    val tagText: Color,            // 分类标签文字
) {
    companion object {
        /** 默认黑白极简版 */
        fun monochrome() = VeraThemeColors(
            iconLine           = Color(0xFF000000),
            iconCheck          = Color(0xFF000000),
            iconWarning        = Color(0xFF000000),
            iconStarEmpty      = Color(0xFF000000).copy(alpha = 0.5f),
            iconStarFilled     = Color(0xFF000000),
            iconCircleNumber   = Color(0xFF000000),
            iconExpandCollapse = Color(0xFF000000),
            iconRandomArrow    = Color(0xFF000000),

            buttonBorder       = Color(0xFF000000).copy(alpha = 0.25f),
            buttonText         = Color(0xFF000000),

            spotlightDot       = Color(0xFF000000),
            spotlightDivider   = Color(0xFF000000).copy(alpha = 0.10f),

            randomCardBg       = Color(0xFFF0F9EB),   // 保持原 primaryBg（极浅绿底）
            randomCardAccent   = Color(0xFF6B8F71),   // 保持原 secondary（墨绿）

            noteAccent         = Color(0xFF000000),
            noteBorder         = Color(0xFF000000).copy(alpha = 0.4f),

            tagBg              = Color.Transparent,
            tagText            = Color(0xFF000000).copy(alpha = 0.55f),
        )

        /** 马卡龙童趣彩色版（低饱和柔和，不刺眼） */
        fun macaron() = VeraThemeColors(
            iconLine           = Color(0xFF8AA8C0),   // 灰蓝
            iconCheck          = Color(0xFF7EC8A0),   // 薄荷绿
            iconWarning        = Color(0xFFF0A8A0),   // 橘粉
            iconStarEmpty      = Color(0xFF8AA8C0).copy(alpha = 0.5f),
            iconStarFilled     = Color(0xFFD4B87A),   // 暖金
            iconCircleNumber   = Color(0xFFB8A8D0),   // 淡紫
            iconExpandCollapse = Color(0xFF8AA8C0),
            iconRandomArrow    = Color(0xFFB8A8D0),

            buttonBorder       = Color(0xFF8AA8C0).copy(alpha = 0.4f),
            buttonText         = Color(0xFF8AA8C0),

            spotlightDot       = Color(0xFFF0A8A0),
            spotlightDivider   = Color(0xFFF0A8A0).copy(alpha = 0.2f),

            randomCardBg       = Color(0xFFF5F0F8),   // 薰衣草白
            randomCardAccent   = Color(0xFFB8A8D0),   // 淡紫

            noteAccent         = Color(0xFF8AA8C0),
            noteBorder         = Color(0xFF8AA8C0).copy(alpha = 0.4f),

            tagBg              = Color(0xFFF0F0F0),   // 极浅灰底
            tagText            = Color(0xFF6A6A6A),
        )
    }
}

/**
 * 全局主题色板 CompositionLocal，默认黑白极简。
 * 在 MainActivity 中通过 CompositionLocalProvider 覆盖为当前用户选中的色板。
 */
val LocalVeraColors = staticCompositionLocalOf { VeraThemeColors.monochrome() }
