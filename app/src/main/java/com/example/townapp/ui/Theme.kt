package com.example.townapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.jvm.JvmName
import androidx.compose.ui.unit.sp

// ============================================
// 🏛️ 小镇设计系统 v2.0
// 统一圆角 / 阴影 / 间距 / 颜色 / 动效规范
// 温暖治愈 · 安静陪伴 · 柔和层次
// ============================================

// ────────────────────────────────────────────
// 🎨 设计 Token — 所有视觉规范的唯一真相源
// ────────────────────────────────────────────

@Immutable
object TownDesignTokens {

    // ============================================
    // 📐 圆角规范（像素风：硬边小圆角，统一2dp为主）
    // ============================================
    object Radius {
        val pixel = 2.dp    // 像素标准：绝大多数卡片/按钮/标签
        val xs = 2.dp       // 小标签、复选框
        val sm = 2.dp       // 小按钮、小卡片
        val md = 2.dp       // 中等按钮、输入框
        val lg = 2.dp       // 普通卡片
        val xl = 4.dp       // 大卡片、主容器
        val xxl = 4.dp      // 超大卡片、底部弹窗
        val full = 9999.dp  // 圆形/胶囊（尽量少用）
    }

    // ============================================
    // 🖼️ 像素边框规范（硬边1px，像素风核心特征）
    // ============================================
    object Border {
        val pixelWidth = 1.dp  // 标准像素边框宽度
        val thickWidth = 2.dp  // 强调边框宽度
    }

    // ============================================
    // 🌫️ 阴影规范（像素风不用阴影，靠边框区分层级，阴影设为0）
    // ============================================
    object Elevation {
        val none = 0.dp
        val sm = 0.dp
        val md = 0.dp
        val lg = 0.dp
        val xl = 0.dp
    }

    // ============================================
    // 📏 间距规范（8px 基准，统一 10 档）
    // ============================================
    object Spacing {
        val xxs = 2.dp
        val xs = 4.dp
        val sm = 8.dp
        val md = 12.dp
        val lg = 16.dp
        val xl = 20.dp
        val xxl = 24.dp
        val xxxl = 32.dp
        val xxxxl = 40.dp
        val xxxxxl = 48.dp
    }

    // ============================================
    // 🎯 颜色系统 — 低饱和像素治愈风（莫兰迪色调）
    // 基调：暖米白基底 + 炭黑线条 + 人本墨绿/虚荣暖金双点缀
    // 对标星露谷物语：柔和、温暖、低饱和，不刺眼
    // ============================================
    object Colors {

        // ── 品牌主色（像素炭黑，不生硬） ──
        val primary = Color(0xFF2D2A26)        // 炭黑主色（像素线条/文字，比纯黑柔和）
        val primaryLight = Color(0xFF5A554E)   // 深暖灰
        val primaryDark = Color(0xFF1A1816)    // 深炭黑
        val accent = Color(0xFF6B8F71)         // 人本路线：莫兰迪墨绿（低饱和）
        val accentSecondary = Color(0xFFC9A66B) // 虚荣路线：莫兰迪暖金（低饱和）

        // ── 功能色（莫兰迪低饱和，治愈不刺眼） ──
        val success = Color(0xFF7A9E81)        // 柔和莫兰迪绿
        val successLight = Color(0xFFBFD1C3)   // 浅灰绿
        val successBg = Color(0xFFF0F4F1)      // 成功背景：极浅绿

        val warning = Color(0xFFC9A66B)        // 莫兰迪暖金（虚荣色）
        val warningLight = Color(0xFFE6D4B0)   // 浅金灰
        val warningBg = Color(0xFFF9F4EB)      // 警告背景：极浅米黄

        val error = Color(0xFFB07C7C)          // 柔和莫兰迪砖红
        val errorLight = Color(0xFFD6B8B8)     // 浅红灰
        val errorBg = Color(0xFFF7F0F0)        // 错误背景：极浅红

        val info = Color(0xFF7D96A3)           // 柔和莫兰迪灰蓝
        val infoLight = Color(0xFFB8C9D2)      // 浅蓝灰
        val infoBg = Color(0xFFF0F4F7)         // 信息背景：极浅蓝

        // ── 文本色（暖调灰度，层次柔和） ──
        val textPrimary = Color(0xFF2D2A26)    // 主文本：炭黑
        val textSecondary = Color(0xFF5A554E)  // 次文本：深暖灰
        val textTertiary = Color(0xFF868078)   // 辅助文本：中暖灰
        val textMuted = Color(0xFFA9A39A)      // 弱化文本：浅暖灰

        // ── 背景色（暖米白基底，像素纸质感） ──
        val bgBase = Color(0xFFF5F2EB)         // 页面基底：暖米黄（像素纸感）
        val bgSurface = Color(0xFFFDFBF6)      // 卡片表面：米白
        val bgSurfaceVariant = Color(0xFFEFEAE0) // 次级表面：浅米灰
        val bgCard = Color(0xFFFFFDF9)         // 卡片背景：近白
        val bgCardAlt = Color(0xFFF7F3EB)      // 交替卡片背景：极浅暖米
        val bgOverlay = Color(0x66000000)      // 遮罩层：半透明黑

        // ── 边框 / 分割线（像素硬边，暖调灰） ──
        val border = Color(0xFF2D2A26)         // 主边框：炭黑（1px像素边）
        val borderLight = Color(0xFFC9C3B9)    // 浅边框：浅暖灰
        val divider = Color(0xFFE0DBD1)        // 分割线：极浅暖灰

        // ── 状态色（双路线莫兰迪色调） ──
        val statEnergy = Color(0xFFC9A66B)     // 精力：暖金
        val statMood = Color(0xFF6B8F71)       // 情绪：墨绿
        val statHealth = Color(0xFF7D96A3)     // 健康：灰蓝
        val statMoney = Color(0xFFC9A66B)      // 财富：暖金

        // ── 环境光影色（像素治愈风分层） ──
        val shadowSoft = Color(0x14000000)     // 柔和投影：极淡黑
        val highlightWarm = Color(0x1AFFFFFF)  // 暖高光：极淡白
        val ambientDay = Color(0x08FFE8B3)     // 日间环境暖光
        val ambientDusk = Color(0x14FFB366)    // 黄昏暖橘光
        val ambientNight = Color(0x14334466)   // 夜间冷蓝光
        val lampGlow = Color(0x22FFCC66)       // 路灯光晕：暖黄

        // ── 昼夜氛围色 ──
        val dayWarm = Color(0xFFFFFDF5)        // 日间暖光
        val nightDeep = Color(0xFF1A1C23)      // 深夜基底：暖深蓝黑
        val nightMid = Color(0xFF272A35)       // 深夜中间：深灰蓝
        val nightLight = Color(0xFF383C4A)     // 深夜亮部：中灰蓝
        val nightText = Color(0xFFEDEBE4)      // 深夜文本：暖米白
        val nightTextSecondary = Color(0xFFA5A39A) // 深夜次文本：浅暖灰
        val nightBorder = Color(0xFF4A4E5C)    // 深夜边框：深灰蓝

        // ── 渐变（柔和多层叠加，像素质感） ──
        object Gradient {
            val dayTop = Color(0xFFF5F2EB)
            val dayBottom = Color(0xFFEDE8DE)
            val duskTop = Color(0xFFF8E9D6)
            val duskBottom = Color(0xFFE8D0B0)
            val nightTop = Color(0xFF1A1C23)
            val nightMid = Color(0xFF272A35)
            val nightBottom = Color(0xFF383C4A)

            val warmGlow = Color(0x14C9A66B)   // 暖金光晕
            val coolGlow = Color(0x107D96A3)   // 冷光晕
            val lampLight = Color(0x33FFCC66)  // 路灯光晕渐变
        }
    }

    // ============================================
    // ⏱️ 动效规范（柔和舒缓，治愈调性）
    // ============================================
    object Animation {
        const val fast = 150         // 微交互
        const val normal = 300       // 标准过渡
        const val slow = 500         // 页面切换
        const val gentle = 800       // 柔和氛围过渡
        const val ambient = 1200     // 环境光变化（昼夜）

        const val springStiffness = 200f
        const val springDamping = 0.85f
    }
}

// ============================================
// 🎯 兼容层：保留旧 API 供现有代码使用
// ============================================

@Deprecated("迁移到 TownDesignTokens", ReplaceWith("TownDesignTokens"))
object BrandColors {
    val Blue get() = TownDesignTokens.Colors.info
    val Green get() = TownDesignTokens.Colors.success
    val Purple get() = TownDesignTokens.Colors.primary
    val Orange get() = TownDesignTokens.Colors.warning
    val Red get() = TownDesignTokens.Colors.error
    val Teal get() = TownDesignTokens.Colors.accent
    val Yellow get() = TownDesignTokens.Colors.warning

    val BlueHighlight get() = TownDesignTokens.Colors.infoBg
    val GreenHighlight get() = TownDesignTokens.Colors.successBg
    val PurpleHighlight get() = TownDesignTokens.Colors.Gradient.warmGlow
    val OrangeHighlight get() = TownDesignTokens.Colors.warningBg
    val RedHighlight get() = TownDesignTokens.Colors.errorBg
    val TealHighlight get() = TownDesignTokens.Colors.infoBg
    val GoldHighlight get() = TownDesignTokens.Colors.Gradient.warmGlow

    val CardBorder get() = TownDesignTokens.Colors.border
    val CardShadow get() = Color(0x0A000000)

    val TextPrimary get() = TownDesignTokens.Colors.textPrimary
    val TextSecondary get() = TownDesignTokens.Colors.textSecondary
    val TextTertiary get() = TownDesignTokens.Colors.textTertiary

    val PageBg get() = TownDesignTokens.Colors.bgBase
    val TagBg get() = TownDesignTokens.Colors.bgSurfaceVariant

    @Deprecated("使用 TownDesignTokens.Colors.textMuted", ReplaceWith("TownDesignTokens.Colors.textMuted"))
    @get:JvmName("getTextMutedDeprecated")
    val textMuted get() = TownDesignTokens.Colors.textMuted
    @Deprecated("使用 TownDesignTokens.Colors.textPrimary", ReplaceWith("TownDesignTokens.Colors.textPrimary"))
    @get:JvmName("getTextPrimaryDeprecated")
    val textPrimary get() = TownDesignTokens.Colors.textPrimary
    @Deprecated("使用 TownDesignTokens.Colors.textSecondary", ReplaceWith("TownDesignTokens.Colors.textSecondary"))
    @get:JvmName("getTextSecondaryDeprecated")
    val textSecondary get() = TownDesignTokens.Colors.textSecondary
    @Deprecated("使用 TownDesignTokens.Colors.bgSurface", ReplaceWith("TownDesignTokens.Colors.bgSurface"))
    @get:JvmName("getSurfaceDeprecated")
    val surface get() = TownDesignTokens.Colors.bgSurface
    @Deprecated("使用 TownDesignTokens.Colors.primary", ReplaceWith("TownDesignTokens.Colors.primary"))
    @get:JvmName("getPrimaryDeprecated")
    val primary get() = TownDesignTokens.Colors.primary
    @Deprecated("使用 TownDesignTokens.Colors.success", ReplaceWith("TownDesignTokens.Colors.success"))
    @get:JvmName("getHealthDeprecated")
    val health get() = TownDesignTokens.Colors.success
    @Deprecated("使用 TownDesignTokens.Colors.warning", ReplaceWith("TownDesignTokens.Colors.warning"))
    @get:JvmName("getWarningDeprecated")
    val warning get() = TownDesignTokens.Colors.warning
    @Deprecated("使用 TownDesignTokens.Colors.bgCard", ReplaceWith("TownDesignTokens.Colors.bgCard"))
    @get:JvmName("getCardBackgroundDeprecated")
    val cardBackground get() = TownDesignTokens.Colors.bgCard
}

@Deprecated("迁移到 TownDesignTokens", ReplaceWith("TownDesignTokens"))
object AppColors {
    val Background get() = TownDesignTokens.Colors.bgBase
    val Surface get() = TownDesignTokens.Colors.bgSurface
    val SurfaceVariant get() = TownDesignTokens.Colors.bgSurfaceVariant
    val CardBackground get() = TownDesignTokens.Colors.bgCard
    val CardBackgroundLight get() = TownDesignTokens.Colors.bgSurfaceVariant

    val TextPrimary get() = TownDesignTokens.Colors.textPrimary
    val TextPrimaryDark get() = TownDesignTokens.Colors.primaryDark
    val TextSecondary get() = TownDesignTokens.Colors.textSecondary
    val TextSecondaryWarm get() = TownDesignTokens.Colors.textTertiary
    val TextMuted get() = TownDesignTokens.Colors.textMuted
    val TextTertiary get() = TownDesignTokens.Colors.textTertiary
    val TextDark get() = TownDesignTokens.Colors.textPrimary
    val TextGray get() = TownDesignTokens.Colors.textSecondary
    val DeepBrown get() = TownDesignTokens.Colors.primaryDark
    val WarmBrown get() = TownDesignTokens.Colors.primary

    val Primary get() = TownDesignTokens.Colors.primary
    val PrimaryBlue get() = TownDesignTokens.Colors.info
    val PrimaryWarm get() = TownDesignTokens.Colors.primary

    val Success get() = TownDesignTokens.Colors.success
    val SuccessLight get() = TownDesignTokens.Colors.successLight
    val SuccessDark get() = TownDesignTokens.Colors.success
    val SuccessMedium get() = TownDesignTokens.Colors.success
    val SuccessBright get() = TownDesignTokens.Colors.success
    val SuccessLightGreen get() = TownDesignTokens.Colors.successLight

    val Warning get() = TownDesignTokens.Colors.warning
    val WarningOrange get() = TownDesignTokens.Colors.warning
    val WarningAmber get() = TownDesignTokens.Colors.warningLight
    val WarningLight get() = TownDesignTokens.Colors.warningBg

    val Error get() = TownDesignTokens.Colors.error
    val ErrorVivid get() = TownDesignTokens.Colors.error
    val ErrorDeep get() = TownDesignTokens.Colors.error
    val ErrorDark get() = TownDesignTokens.Colors.error
    val ErrorDeepOrange get() = TownDesignTokens.Colors.error
    val Danger get() = TownDesignTokens.Colors.error
    val DangerLight get() = TownDesignTokens.Colors.errorBg

    val Info get() = TownDesignTokens.Colors.info
    val InfoDark get() = TownDesignTokens.Colors.info
    val InfoLight get() = TownDesignTokens.Colors.infoBg

    val StatAwakening get() = TownDesignTokens.Colors.accent
    val StatHealth get() = TownDesignTokens.Colors.statHealth
    val StatHappiness get() = TownDesignTokens.Colors.statMood
    val StatDebt get() = TownDesignTokens.Colors.error
    val StatPollution get() = TownDesignTokens.Colors.textTertiary

    val SelectedBackground get() = TownDesignTokens.Colors.infoBg
    val SuccessBackground get() = TownDesignTokens.Colors.successBg
    val WarningBackground get() = TownDesignTokens.Colors.warningBg
    val ErrorBackground get() = TownDesignTokens.Colors.errorBg
    val AccentBackground get() = TownDesignTokens.Colors.Gradient.warmGlow
    val BeigeBackground get() = TownDesignTokens.Colors.bgCard
    val WarmBackground get() = TownDesignTokens.Colors.bgSurfaceVariant
    val LightOrangeBackground get() = TownDesignTokens.Colors.warningBg
    val OffWhiteBackground get() = TownDesignTokens.Colors.bgBase
    val LightPurpleBackground get() = TownDesignTokens.Colors.Gradient.warmGlow
    val LightYellowBackground get() = TownDesignTokens.Colors.warningBg

    val Divider get() = TownDesignTokens.Colors.divider
    val BorderLight get() = TownDesignTokens.Colors.borderLight
    val ProgressWarm get() = TownDesignTokens.Colors.statMoney
    val GradientStart get() = TownDesignTokens.Colors.primary
    val GradientEnd get() = TownDesignTokens.Colors.accent
    val TrendLine get() = TownDesignTokens.Colors.info
    val DeepPurple get() = TownDesignTokens.Colors.primaryDark
    val LightPurple get() = TownDesignTokens.Colors.primaryLight
    val Gold get() = TownDesignTokens.Colors.statMoney
    val TextLightGray get() = TownDesignTokens.Colors.textTertiary
    val TextDarkGray get() = TownDesignTokens.Colors.textPrimary

    val NeutralDark get() = TownDesignTokens.Colors.textPrimary
    val NeutralMedium get() = TownDesignTokens.Colors.textSecondary
    val NeutralLight get() = TownDesignTokens.Colors.border
    val NeutralBg get() = TownDesignTokens.Colors.bgSurfaceVariant
}

@Deprecated("迁移到 TownDesignTokens", ReplaceWith("TownDesignTokens"))
object AppDimens {
    val paddingSmall get() = TownDesignTokens.Spacing.sm
    val paddingMedium get() = TownDesignTokens.Spacing.md
    val paddingLarge get() = TownDesignTokens.Spacing.lg
    val paddingXLarge get() = TownDesignTokens.Spacing.xl
    val paddingXXLarge get() = TownDesignTokens.Spacing.xxl
    val paddingCard get() = TownDesignTokens.Spacing.xl

    val radiusSmall get() = TownDesignTokens.Radius.sm
    val radiusMedium get() = TownDesignTokens.Radius.md
    val radiusLarge get() = TownDesignTokens.Radius.lg
    val radiusXLarge get() = TownDesignTokens.Radius.xl
    val radiusCard get() = TownDesignTokens.Radius.xl

    val cardElevation get() = TownDesignTokens.Elevation.md
}

// ============================================
// 📝 全局字体排版系统
// ============================================
val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 2.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 1.5.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 1.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 9.sp,
        lineHeight = 12.sp
    )
)

// ============================================
// 🌗 主题配置
// ============================================

private val LightColorScheme = lightColorScheme(
    primary = TownDesignTokens.Colors.primary,
    secondary = TownDesignTokens.Colors.accent,
    tertiary = TownDesignTokens.Colors.warning,
    background = TownDesignTokens.Colors.bgBase,
    surface = TownDesignTokens.Colors.bgSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TownDesignTokens.Colors.textPrimary,
    onSurface = TownDesignTokens.Colors.textPrimary,
    surfaceVariant = TownDesignTokens.Colors.bgSurfaceVariant,
    onSurfaceVariant = TownDesignTokens.Colors.textSecondary,
    outline = TownDesignTokens.Colors.border,
)

private val DarkColorScheme = darkColorScheme(
    primary = TownDesignTokens.Colors.primaryLight,
    secondary = TownDesignTokens.Colors.accent,
    tertiary = TownDesignTokens.Colors.warningLight,
    background = TownDesignTokens.Colors.nightDeep,
    surface = TownDesignTokens.Colors.nightMid,
    onPrimary = TownDesignTokens.Colors.nightDeep,
    onSecondary = TownDesignTokens.Colors.nightDeep,
    onBackground = TownDesignTokens.Colors.nightText,
    onSurface = TownDesignTokens.Colors.nightText,
    surfaceVariant = TownDesignTokens.Colors.nightLight,
    onSurfaceVariant = TownDesignTokens.Colors.nightTextSecondary,
    outline = Color(0xFF3D4F6E),
)

@Composable
fun TownTheme(
    awakeningMode: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (awakeningMode || darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

@Composable fun getSuccessColor(): Color = TownDesignTokens.Colors.success
@Composable fun getWarningColor(): Color = TownDesignTokens.Colors.warning
@Composable fun getDangerColor(): Color = TownDesignTokens.Colors.error
@Composable fun getInfoColor(): Color = TownDesignTokens.Colors.info

// ============================================
// 📖 认知字典设计系统 v3.0（悬浮卡片 · 柔和层次 · 温暖活泼）
// ============================================

object DictionaryTokens {
    // 背景
    val pageBg = Color(0xFFF8F5EB)
    val cardBg = Color(0xFFFFFFFF)

    // 品牌主色（低饱和青柠绿）
    val primary = Color(0xFFA3D98B)
    val primaryLight = Color(0xFFD4F0C4)
    val primaryBg = Color(0xFFF0F9EB)

    // 标签区分色
    val tagPink = Color(0xFFF2D8E0)      // 认知误区
    val tagOrange = Color(0xFFF9E2CC)    // 焦虑陷阱
    val tagYellow = Color(0xFFFFF7D9)    // 片面认知
    val tagPeach = Color(0xFFFBE3E6)     // 传统观念
    val tagBlue = Color(0xFFE0EEFB)      // 底层认知

    // 文字四层分级（+ 已读置灰）
    val textTitle = Color(0xFF121212)    // 大标题深黑
    val textHeading = Color(0xFF4A4A4A)  // 模块标题深灰
    val textBody = Color(0xFF707070)     // 正文浅灰
    val textCaption = Color(0xFF8C8C8C)  // 辅助小字中灰（已读统一色）

    // 详情页板块卡片底色（浅色模式用特定语义色，深色模式回退surface由ContentCard处理）
    val cardDefinition = Color(0xFFFFFFFF)   // 基础定义：纯白
    val cardExample = Color(0xFFFFFDF5)      // 现实案例：浅暖米黄
    val cardCore = Color(0xFFF0F9EB)         // 小镇辩证解读：青柠绿浅底
    val cardTip = Color(0xFFF5F8FA)          // 可操作校准技巧：浅青灰

    /**
     * 主题感知文字色（自动适配深浅色模式）
     */
    @Composable
    fun textTitleThemed(): Color = MaterialTheme.colorScheme.onSurface

    @Composable
    fun textHeadingThemed(): Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)

    @Composable
    fun textBodyThemed(): Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)

    @Composable
    fun textCaptionThemed(): Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    @Composable
    fun textTitleUnreadThemed(): Color = MaterialTheme.colorScheme.onSurface

    @Composable
    fun textTitleReadThemed(): Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    @Composable
    fun textBodyUnreadThemed(): Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)

    @Composable
    fun textBodyReadThemed(): Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

    // 圆角（统一规范：卡片16dp大圆角，标签/按钮8dp小圆角）
    val radiusCard = 16.dp
    val radiusButton = 8.dp
    val radiusTag = 8.dp
    val radiusIcon = 12.dp

    // 阴影（统一柔和向下阴影，低透明度）
    val elevationCard = 4.dp
    val shadowColor = Color(0x14000000)         // 统一柔和投影色
    val shadowColorAmbient = Color(0x0A000000)  // 环境光投影

    // 间距（8倍数栅格，页面左右24dp，模块上下16/24dp两种规格）
    val pagePadding = 24.dp
    val sectionSpacing = 16.dp
    val sectionSpacingLarge = 24.dp
    val cardPadding = 16.dp
    val cardPaddingSmall = 12.dp
    val bottomSafePadding = 40.dp               // 底部安全留白，规避手势遮挡

    // ============================================
    // 🖊️ 图标规范（全局统一 2dp 粗细线性图标，禁止混搭填充/粗线条）
    // ============================================
    val iconStrokeDensity = 2f                    // Canvas stroke 基准：2f × density

    // ============================================
    // 📖 已读/未读文字色值（点击查看后自动切换灰色）
    // ============================================
    val textTitleUnread = Color(0xFF121212)       // 未读标题：深黑
    val textTitleRead = Color(0xFF8C8C8C)         // 已读标题：中灰度
    val textBodyUnread = Color(0xFF707070)        // 未读正文：浅灰
    val textBodyRead = Color(0xFF8C8C8C)          // 已读正文：统一灰度

    // ============================================
    // 📝 文字层级规范（四层，全页面统一，强制拉开主次）
    // ============================================
    object Type {
        // 第一层：大标题（词条名、页面标题）—— Medium 深黑，18~22sp
        val titleSize = 18.sp
        val titleSizeLarge = 28.sp              // 详情页词条名
        val titleWeight = FontWeight.Medium
        val titleColor = Color(0xFF121212)
        val titleLineHeight = 26.sp

        // 第二层：板块小标题（小镇辩证解读、可操作校准技巧）—— 加粗深灰
        val headingSize = 17.sp
        val headingWeight = FontWeight.Bold
        val headingColor = Color(0xFF4A4A4A)
        val headingLineHeight = 24.sp

        // 第三层：正文内容 —— 常规中灰
        val bodySize = 14.sp
        val bodyWeight = FontWeight.Normal
        val bodyColor = Color(0xFF707070)
        val bodyLineHeight = 22.sp

        // 第四层：辅助小字（简介、标签说明）—— 13~14sp 常规浅灰
        val captionSize = 13.sp
        val captionWeight = FontWeight.Normal
        val captionColor = Color(0xFF8C8C8C)
        val captionLineHeight = 18.sp

        // 标签文字（12sp，Medium）
        val tagSize = 12.sp
        val tagWeight = FontWeight.Medium
    }

    // ── Tab 栏分类细色条色彩（4组基础色，同类板块复用） ──
    object TabBar {
        val pink = Color(0xFFF0CFD8)           // 人际关系 / 亲密关系：淡粉
        val warmBrown = Color(0xFFE3CFBD)      // 人格品德：浅暖棕
        val limeGreen = Color(0xFFC5E6B0)      // 处事方法 / 小镇内在：青柠绿系
        val earthGold = Color(0xFFDFCFA8)      // 社会观念：浅土金
        val grayBlue = Color(0xFFCFDEEA)       // 认知思维：浅灰蓝
        val warmNeutral = Color(0xFFECDCC8)    // 今日认知：暖中性
        val featureGreen = Color(0xFFD9EDCA)   // 值得先读：特征绿

        val selectedBg = Color(0xFFA3D98B)     // 选中态：低饱和青柠绿（与primary一致）
        val tabBg = Color(0xFFFFFFFF)          // 未选中：纯白底
        val tabShadow = Color(0x0D000000)      // 轻拟态柔和阴影
    }

    // 按Tab label获取左侧细色条颜色
    fun tabBarColor(tabId: String): Color = when (tabId) {
        "daily" -> TabBar.warmNeutral
        "character" -> TabBar.warmBrown
        "method" -> TabBar.limeGreen
        "interpersonal" -> TabBar.pink
        "society" -> TabBar.earthGold
        "relationship" -> TabBar.pink
        "cognition" -> TabBar.grayBlue
        "town_system" -> TabBar.limeGreen
        "subjectivity" -> TabBar.grayBlue
        "featured" -> TabBar.featureGreen
        else -> TabBar.grayBlue
    }

    // 按分类获取标签/装饰色（保留兼容旧接口）
    fun categoryTagColor(category: com.example.townapp.data.idiom.IdiomCategory): Color =
        when (category) {
            com.example.townapp.data.idiom.IdiomCategory.CHARACTER -> tagPeach
            com.example.townapp.data.idiom.IdiomCategory.METHOD -> tagYellow
            com.example.townapp.data.idiom.IdiomCategory.INTERPERSONAL -> tagYellow
            com.example.townapp.data.idiom.IdiomCategory.SOCIETY -> tagPeach
            com.example.townapp.data.idiom.IdiomCategory.RELATIONSHIP -> tagPeach
            com.example.townapp.data.idiom.IdiomCategory.COGNITION -> tagBlue
            com.example.townapp.data.idiom.IdiomCategory.TOWN_SYSTEM -> tagBlue
            com.example.townapp.data.idiom.IdiomCategory.WORKPLACE -> tagBlue
            com.example.townapp.data.idiom.IdiomCategory.CONSUMPTION -> tagOrange
        }

    fun categoryDarkColor(category: com.example.townapp.data.idiom.IdiomCategory): Color =
        when (category) {
            com.example.townapp.data.idiom.IdiomCategory.CHARACTER -> Color(0xFFB07A8A)
            com.example.townapp.data.idiom.IdiomCategory.METHOD -> Color(0xFFB09B6A)
            com.example.townapp.data.idiom.IdiomCategory.INTERPERSONAL -> Color(0xFFB09B6A)
            com.example.townapp.data.idiom.IdiomCategory.SOCIETY -> Color(0xFFB07A8A)
            com.example.townapp.data.idiom.IdiomCategory.RELATIONSHIP -> Color(0xFFB07A8A)
            com.example.townapp.data.idiom.IdiomCategory.COGNITION -> Color(0xFF7A8FA3)
            com.example.townapp.data.idiom.IdiomCategory.TOWN_SYSTEM -> Color(0xFF7AA38F)
            com.example.townapp.data.idiom.IdiomCategory.WORKPLACE -> Color(0xFF7A8FA3)
            com.example.townapp.data.idiom.IdiomCategory.CONSUMPTION -> Color(0xFFB08A6A)
        }

    // 按毒性等级获取标签色
    fun toxicityTagColor(level: com.example.townapp.data.idiom.ToxicityLevel): Color =
        when (level) {
            com.example.townapp.data.idiom.ToxicityLevel.POISONOUS -> tagPink
            com.example.townapp.data.idiom.ToxicityLevel.HARMFUL -> tagOrange
            com.example.townapp.data.idiom.ToxicityLevel.DISTORTED -> tagYellow
            com.example.townapp.data.idiom.ToxicityLevel.HERITAGE -> tagPeach
            com.example.townapp.data.idiom.ToxicityLevel.TOWN_WISDOM -> tagBlue
        }

    fun toxicityDarkColor(level: com.example.townapp.data.idiom.ToxicityLevel): Color =
        when (level) {
            com.example.townapp.data.idiom.ToxicityLevel.POISONOUS -> Color(0xFFB07A8A)
            com.example.townapp.data.idiom.ToxicityLevel.HARMFUL -> Color(0xFFB08A6A)
            com.example.townapp.data.idiom.ToxicityLevel.DISTORTED -> Color(0xFFB09B6A)
            com.example.townapp.data.idiom.ToxicityLevel.HERITAGE -> Color(0xFFB07A8A)
            com.example.townapp.data.idiom.ToxicityLevel.TOWN_WISDOM -> Color(0xFF7A8FA3)
        }
}