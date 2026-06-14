package com.example.townapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// ============================================
// 薪俸小镇 品牌设计系统（对标产品规划报告）
// 白底 + 彩色渐变高光 + 20px圆角 + 微阴影
// ============================================

// ── 品牌主色（来自报告） ──
object BrandColors {
    val Blue    = Color(0xFF1D4ED8)  // 加深蓝色，提升对比度
    val Green   = Color(0xFF16A34A)  // 加深绿色，提升对比度
    val Purple  = Color(0xFF9333EA)  // 加深紫色，提升对比度
    val Orange  = Color(0xFFEA580C)  // 加深橙色，提升对比度
    val Red     = Color(0xFFDC2626)  // 加深红色，提升对比度
    val Teal    = Color(0xFF0D9488)  // 加深青色，提升对比度
    val Yellow  = Color(0xFFCA8A04)  // 加深黄色，提升对比度

    // 渐变高光色（报告里的 highlight-gradient）
    val BlueHighlight   = Color(0x0D1D4ED8)
    val GreenHighlight  = Color(0x0D16A34A)
    val PurpleHighlight = Color(0x0D9333EA)
    val OrangeHighlight = Color(0x0DEA580C)
    val RedHighlight    = Color(0x0DDC2626)
    val TealHighlight   = Color(0x0D0D9488)
    val GoldHighlight   = Color(0x0DCA8A04)

    // 卡片边框
    val CardBorder   = Color(0xFFD1D5DB)  // 加深边框颜色，提升对比度
    // 卡片阴影
    val CardShadow   = Color(0x14000000)  // 加深阴影

    // 文字 - 符合WCAG 2.1 AA标准（对比度≥4.5:1）
    val TextPrimary   = Color(0xFF111827)     // gray-900, 对比度 14.3:1
    val TextSecondary = Color(0xFF4B5563)     // gray-600, 对比度 7.1:1（之前是 gray-500，对比度约4.5:1）
    val TextTertiary  = Color(0xFF6B7280)     // gray-500, 对比度 4.5:1（之前是 gray-400，对比度约3:1）

    // 背景
    val PageBg        = Color(0xFFFFFFFF)
    val TagBg         = Color(0xFFF3F4F6)     // gray-100，与文字对比度更好
}

// 浅色主题 —— 干净白底风
private val LightColorScheme = lightColorScheme(
    primary = BrandColors.Blue,
    secondary = BrandColors.Purple,
    tertiary = BrandColors.Teal,
    background = BrandColors.PageBg,
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = BrandColors.TextPrimary,
    onSurface = BrandColors.TextPrimary,
    surfaceVariant = BrandColors.TagBg,
    onSurfaceVariant = BrandColors.TextSecondary,
    outline = BrandColors.CardBorder,
)

// 深色主题
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF60A5FA),
    secondary = Color(0xFFC084FC),
    tertiary = Color(0xFF2DD4BF),
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onPrimary = Color(0xFF0F172A),
    onSecondary = Color(0xFF0F172A),
    onBackground = Color(0xFFF1F5F9),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = Color(0xFF475569),
)

// 语义化颜色
object AppColors {
    // ==================== 基础背景 ====================
    val Background = Color(0xFFF8F7F4)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFF5F5F5)
    val CardBackground = Color(0xFFF5F0EB)
    val CardBackgroundLight = Color(0xFFFAFAFA)

    // ==================== 文本 ====================
    val TextPrimary = Color(0xFF4A3728)
    val TextPrimaryDark = Color(0xFF3D3226)
    val TextSecondary = Color(0xFF666666)
    val TextSecondaryWarm = Color(0xFF867B6B)
    val TextMuted = Color(0xFFB5AA9A)
    val TextTertiary = Color(0xFFA0896D)
    val TextDark = Color(0xFF37474F)
    val TextGray = Color(0xFF78909C)
    val DeepBrown = Color(0xFF6B5B4F)
    val WarmBrown = Color(0xFF5D4E37)

    // ==================== 主题色 ====================
    val Primary = Color(0xFF3498DB)
    val PrimaryBlue = Color(0xFF2196F3)
    val PrimaryWarm = Color(0xFF8B7355)

    // ==================== 功能色 ====================
    val Success = Color(0xFF4CAF50)
    val SuccessLight = Color(0xFF81C784)
    val SuccessDark = Color(0xFF2E7D32)
    val SuccessMedium = Color(0xFF43A047)
    val SuccessBright = Color(0xFF22C55E)
    val SuccessLightGreen = Color(0xFF66BB6A)

    val Warning = Color(0xFFFFC107)
    val WarningOrange = Color(0xFFF97316)
    val WarningAmber = Color(0xFFFFB74D)
    val WarningLight = BrandColors.OrangeHighlight

    val Error = Color(0xFFE53935)
    val ErrorVivid = Color(0xFFe94560)
    val ErrorDeep = Color(0xFFEF4444)
    val ErrorDark = Color(0xFFBF360C)
    val ErrorDeepOrange = Color(0xFFE65100)
    val Danger = BrandColors.Red
    val DangerLight = BrandColors.RedHighlight

    val Info = Color(0xFF3B82F6)
    val InfoDark = Color(0xFF1565C0)
    val InfoLight = BrandColors.BlueHighlight

    // ==================== 状态色 ====================
    val StatAwakening = Color(0xFF9B59B6)
    val StatHealth = Color(0xFF2ECC71)
    val StatHappiness = Color(0xFFF39C12)
    val StatDebt = Color(0xFFE74C3C)
    val StatPollution = Color(0xFF7F8C8D)

    // ==================== 强调背景 ====================
    val SelectedBackground = Color(0xFFE3F2FD)
    val SuccessBackground = Color(0xFFE8F5E9)
    val WarningBackground = Color(0xFFFFF8E1)
    val ErrorBackground = Color(0xFFFFF0F0)
    val AccentBackground = Color(0xFFFCE4EC)
    val BeigeBackground = Color(0xFFF5E6D3)
    val WarmBackground = Color(0xFFE8DFD4)
    val LightOrangeBackground = Color(0xFFFFF3E0)
    val OffWhiteBackground = Color(0xFFFFFAF0)
    val LightPurpleBackground = Color(0xFFF3E5F5)
    val LightYellowBackground = Color(0xFFFFF8E7)

    // ==================== 其他 ====================
    val Divider = Color(0xFFE8E0D8)
    val BorderLight = Color(0xFFE0E0E0)
    val ProgressWarm = Color(0xFFC4B8A8)
    val GradientStart = Color(0xFF667EEA)
    val GradientEnd = Color(0xFF764BA2)
    val TrendLine = Color(0xFF3498DB)
    val DeepPurple = Color(0xFF7B1FA2)
    val LightPurple = Color(0xFFCE93D8)
    val Gold = Color(0xFF8B6914)
    val TextLightGray = Color(0xFF999999)
    val TextDarkGray = Color(0xFF333333)

    // 保留旧版中性色（兼容）
    val NeutralDark = Color(0xFF1F2937)
    val NeutralMedium = Color(0xFF6B7280)
    val NeutralLight = Color(0xFFE5E7EB)
    val NeutralBg = Color(0xFFF9FAFB)
}

// 尺寸常量
object AppDimens {
    val paddingSmall = 8.dp
    val paddingMedium = 12.dp
    val paddingLarge = 16.dp
    val paddingXLarge = 20.dp
    val paddingXXLarge = 24.dp
    val paddingCard = 20.dp       // 卡片内边距 = 报告的 p-8

    val radiusSmall = 8.dp
    val radiusMedium = 12.dp
    val radiusLarge = 16.dp
    val radiusXLarge = 20.dp      // 卡片圆角 = 报告的 20px
    val radiusCard = 20.dp

    val cardElevation = 4.dp      // 微阴影
}

@Composable
fun TownTheme(
    awakeningMode: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (awakeningMode || darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(colorScheme = colorScheme, content = content)
}

// 便捷颜色函数
@Composable fun getSuccessColor(): Color = BrandColors.Green
@Composable fun getWarningColor(): Color = BrandColors.Orange
@Composable fun getDangerColor(): Color = BrandColors.Red
@Composable fun getInfoColor(): Color = BrandColors.Blue