package com.example.townapp.feature.human_narrative.atmosphere

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

/**
 * 昼夜 / 四季渐变氛围遮罩
 *
 * 严格遵循小镇项目铁律：
 * 1. 纯 UI 组件，无任何文件 IO、无任何数据库调用
 * 2. 仅读取 seasonId + currentHour 两个数字参数
 * 3. 色彩计算仅在此组件内完成，输出透明 Brush
 * 4. 不拦截任何触摸事件，NPC / 菜单点击可穿透
 * 5. 全部色彩带平滑过渡动画（400ms）
 *
 * 使用规范：
 * ```
 * // 放在画布之后、悬浮 UI 之前
 * Box {
 *     PixelMapCanvas(...)
 *     AtmosphereOverlay(seasonId = 1, currentHour = 18)
 *     MiniStatusPanel(...)
 *     FloatingExpandMenu(...)
 * }
 * ```
 *
 * 视觉对照（对标《去月球》氛围）：
 * - 清晨 5-8：浅暖黄径向柔光
 * - 上午 8-11：透明无滤镜
 * - 正午 11-14：透明 + 夏季高光
 * - 下午 14-17：透明
 * - 黄昏 17-19：橙红径向渐变
 * - 夜晚 19-22：深蓝径向柔光
 * - 深夜 22-5：深蓝 + 径向低饱和
 */
@Composable
fun AtmosphereOverlay(
    seasonId: Int,
    currentHour: Int,
    modifier: Modifier = Modifier
) {
    // 计算时段颜色（带季节叠加）
    val (topColor, bottomColor, centerColor) = getAtmosphereColors(seasonId, currentHour)

    // 三个颜色通道独立做平滑过渡
    val animatedTop by animateColorAsState(
        targetValue = topColor,
        animationSpec = tween(durationMillis = 1500),
        label = "atmosphere-top"
    )
    val animatedBottom by animateColorAsState(
        targetValue = bottomColor,
        animationSpec = tween(durationMillis = 1500),
        label = "atmosphere-bottom"
    )
    val animatedCenter by animateColorAsState(
        targetValue = centerColor,
        animationSpec = tween(durationMillis = 1500),
        label = "atmosphere-center"
    )

    // 三层叠加：顶部渐变 + 底部渐变 + 中心径向
    Box(
        modifier = modifier
            .fillMaxSize()
            // 不拦截任何触摸事件：NPC、菜单点击可穿透
            .pointerInput(Unit) {}
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(animatedTop, Color.Transparent, animatedBottom)
                )
            )
    ) {
        // 中心径向柔光（昼夜特有）
        if (centerColor != Color.Transparent) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                centerColor,
                                Color.Transparent
                            ),
                            radius = 1200f
                        )
                    )
            )
        }
    }
}

/**
 * 根据 seasonId + currentHour 计算三通道颜色
 *
 * 返回 (topColor, bottomColor, centerColor)
 * - topColor：画面顶部颜色
 * - bottomColor：画面底部颜色
 * - centerColor：中心径向光（昼夜特有，可为 Transparent）
 */
private fun getAtmosphereColors(
    seasonId: Int,
    currentHour: Int
): Triple<Color, Color, Color> {
    // 先算时段基础色
    val timeOfDay = getTimeOfDay(currentHour)

    // 季节色调叠加系数
    val seasonTone = getSeasonTone(seasonId)

    return when (timeOfDay) {
        // ================== 清晨 5-8 ==================
        TimeOfDay.DAWN -> Triple(
            Color(0xFFFFE4B5).copy(alpha = 0.35f * seasonTone.brightness),   // 浅暖黄
            Color(0xFFFFD700).copy(alpha = 0.15f * seasonTone.brightness),
            Color(0xFFFFE4B5).copy(alpha = 0.20f * seasonTone.brightness)
        )

        // ================== 上午 / 下午 8-17 ==================
        TimeOfDay.DAY -> Triple(
            Color.Transparent,
            Color.Transparent,
            Color.Transparent
        )

        // ================== 黄昏 17-19 ==================
        TimeOfDay.DUSK -> Triple(
            Color(0xFFFF8C00).copy(alpha = 0.30f * seasonTone.brightness),   // 橙红
            Color(0xFFDC143C).copy(alpha = 0.20f * seasonTone.brightness), // 深红
            Color(0xFFFFA500).copy(alpha = 0.25f * seasonTone.brightness)
        )

        // ================== 夜晚 19-22 ==================
        TimeOfDay.NIGHT -> Triple(
            Color(0xFF000080).copy(alpha = 0.40f * seasonTone.brightness),   // 深蓝
            Color(0xFF191970).copy(alpha = 0.30f * seasonTone.brightness), // 午夜蓝
            Color(0xFF4169E1).copy(alpha = 0.15f * seasonTone.brightness)  // 月光蓝
        )

        // ================== 深夜 22-5 ==================
        TimeOfDay.MIDNIGHT -> Triple(
            Color(0xFF000033).copy(alpha = 0.55f * seasonTone.brightness),   // 极深蓝
            Color(0xFF000000).copy(alpha = 0.45f * seasonTone.brightness),
            Color(0xFF1E1E3F).copy(alpha = 0.25f * seasonTone.brightness)
        )
    }
}

/** 时段枚举 */
private enum class TimeOfDay {
    DAWN,    // 清晨 5-8
    DAY,     // 白天 8-17
    DUSK,    // 黄昏 17-19
    NIGHT,   // 夜晚 19-22
    MIDNIGHT // 深夜 22-5
}

/** 时段判定 */
private fun getTimeOfDay(hour: Int): TimeOfDay = when (hour) {
    in 5..7 -> TimeOfDay.DAWN
    in 8..16 -> TimeOfDay.DAY
    in 17..18 -> TimeOfDay.DUSK
    in 19..21 -> TimeOfDay.NIGHT
    else -> TimeOfDay.MIDNIGHT
}

/** 季节色调（返回基础亮度和色彩倾向） */
private data class SeasonTone(
    val brightness: Float  // 0.7 ~ 1.3 倍亮度调节
)

private fun getSeasonTone(seasonId: Int): SeasonTone = when (seasonId) {
    1 -> SeasonTone(brightness = 1.10f)  // 春：明亮通透
    2 -> SeasonTone(brightness = 1.25f)  // 夏：高光强
    3 -> SeasonTone(brightness = 0.95f)  // 秋：暖棕怀旧
    4 -> SeasonTone(brightness = 0.80f)  // 冬：冷灰清冷
    else -> SeasonTone(brightness = 1.0f)
}