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