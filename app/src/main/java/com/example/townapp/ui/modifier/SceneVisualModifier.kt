package com.example.townapp.ui.modifier

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.example.townapp.ui.animation.TownAnimationSpec
import com.example.townapp.ui.theme.TownDesignTokens
import kotlin.random.Random

/**
 * 剧情运镜扩展 Modifier（电影级视觉特效）
 *
 * 提供三个全局可复用的视觉特效：
 * 1. slowZoomIn() — 慢推镜（关键剧情时缓慢放大画面聚焦）
 * 2. vignetteEffect() — 暗角滤镜（晚年/压抑场景的四周渐黑）
 * 3. memoryFilter() — 回忆褪色滤镜（低饱和度 + 颗粒噪点 + 米黄调）
 *
 * 严格遵循小镇项目铁律：
 * 1. 纯 UI 渲染层，无任何文件 IO、无任何数据库调用
 * 2. 仅接收布尔 / 浮点 / 颜色参数，不读取任何 ID、文本、仓库数据
 * 3. 零底层改动：NpcRepository / LifecycleManager / Memory 模型完全不动
 * 4. 全局复用：地图、回忆弹窗、纪念馆页面均可任意挂载
 * 5. 动画柔和：所有过渡 1.8 秒慢速，贴合《去月球》舒缓叙事节奏
 *
 * 使用示例：
 * ```
 * // 地图页面
 * Box(modifier = Modifier.slowZoomIn(enabled = selectedNpc != null))
 *
 * // 回忆弹窗
 * Card(modifier = Modifier.memoryFilter())
 *
 * // 晚年页面
 * Box(modifier = Modifier.vignetteEffect(strength = 0.6f))
 * ```
 */

// ==================== 1. 慢推镜 ====================

/**
 * 慢推镜 Modifier
 *
 * 启用时画面缓慢放大（1.0 → 1.08），用于：
 * - 点击 NPC 触发对话
 * - 进入回忆弹窗
 * - 关键剧情节点
 *
 * @param enabled 是否启用推镜效果
 * @param maxScale 最大放大倍数（默认 1.08，配合慢速 1.8s，视觉聚焦不突兀）
 */
@Composable
fun Modifier.slowZoomIn(
    enabled: Boolean,
    maxScale: Float = 1.08f
): Modifier {
    val animatedScale by animateFloatAsState(
        targetValue = if (enabled) maxScale else 1f,
        animationSpec = tween(durationMillis = 1800),
        label = "slow-zoom-in"
    )
    return this.then(
        if (enabled) Modifier.scale(animatedScale) else Modifier
    )
}

// ==================== 2. 暗角滤镜 ====================

/**
 * 暗角滤镜 Modifier
 *
 * 在画面四周添加径向暗角（边缘渐黑），用于：
 * - 玩家进入晚年（自动启用）
 * - 角色生病场景
 * - 压抑剧情时刻
 *
 * @param strength 暗角强度 0f ~ 1f（0=无效果，1=最强暗角）
 * @param color 暗角颜色（默认黑色）
 */
fun Modifier.vignetteEffect(
    strength: Float = 0.5f,
    color: Color = Color.Black
): Modifier = this.drawWithCache {
    val safeStrength = strength.coerceIn(0f, 1f)
    val brush = Brush.radialGradient(
        colors = listOf(
            Color.Transparent,
            color.copy(alpha = 0.3f * safeStrength),
            color.copy(alpha = 0.7f * safeStrength)
        ),
        center = Offset(size.width / 2f, size.height / 2f),
        radius = size.minDimension / 1.1f
    )
    onDrawWithContent {
        drawContent()
        drawRect(brush = brush)
    }
}

// ==================== 3. 回忆褪色滤镜 ====================

/**
 * 回忆褪色滤镜 Modifier（对标《去月球》回忆片段质感）
 *
 * 视觉效果：
 * - 低饱和度（灰白调）
 * - 米黄色调叠加（怀旧感）
 * - 细颗粒噪点（像素噪点模拟老照片）
 * - 整体偏暗（回忆朦胧）
 *
 * @param intensity 滤镜强度 0f ~ 1f（默认 0.7）
 * @param noiseDensity 噪点密度 0f ~ 1f（默认 0.15）
 */
fun Modifier.memoryFilter(
    intensity: Float = 0.7f,
    noiseDensity: Float = 0.15f
): Modifier = this.drawWithCache {
    val safeIntensity = intensity.coerceIn(0f, 1f)
    val safeNoise = noiseDensity.coerceIn(0f, 1f)

    // 米黄色调（怀旧感）
    val sepiaColor = Color(0xFFD4A574).copy(alpha = 0.25f * safeIntensity)

    // 暗角（朦胧感）
    val vignetteBrush = Brush.radialGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black.copy(alpha = 0.2f * safeIntensity),
            Color.Black.copy(alpha = 0.4f * safeIntensity)
        ),
        center = Offset(size.width / 2f, size.height / 2f),
        radius = size.minDimension / 0.95f
    )

    // 噪点采样数（按面积比例）
    val noiseCount = ((size.width * size.height) / 800f * safeNoise).toInt().coerceAtLeast(50)

    onDrawWithContent {
        // 第一步：先绘制原始内容
        drawContent()

        // 第二步：叠加米黄色调（怀旧）
        drawRect(
            color = sepiaColor,
            blendMode = BlendMode.Multiply
        )

        // 第三步：叠加暗角
        drawRect(brush = vignetteBrush)

        // 第四步：叠加像素噪点
        drawMemoryNoise(noiseCount)
    }
}

/**
 * 绘制像素噪点（内部函数）
 *
 * 使用伪随机分布模拟老照片噪点：
 * - 白色亮点：模拟曝光噪点
 * - 黑色暗点：模拟胶片颗粒
 * - 透明度低：避免遮挡主体内容
 */
private fun DrawScope.drawMemoryNoise(count: Int) {
    val random = Random(seed = 42)  // 固定 seed 保证帧间稳定
    repeat(count) {
        val x = random.nextFloat() * size.width
        val y = random.nextFloat() * size.height
        val isWhite = random.nextBoolean()
        drawRect(
            color = if (isWhite) {
                Color.White.copy(alpha = 0.12f)
            } else {
                Color.Black.copy(alpha = 0.10f)
            },
            topLeft = Offset(x, y),
            size = androidx.compose.ui.geometry.Size(1.5f, 1.5f)
        )
    }
}

// ==================== 4. 柔光氛围效果 ====================

/**
 * 顶部暖光效果 Modifier
 *
 * 在页面顶部添加柔和的暖光渐变，营造温暖氛围
 * 白天使用金黄色柔光，夜晚使用柔和的月光蓝
 *
 * @param isNight 是否夜间模式
 * @param intensity 光效强度 0f ~ 1f
 */
fun Modifier.topGlowEffect(
    isNight: Boolean = false,
    intensity: Float = 0.15f
): Modifier = this.drawWithCache {
    val safeIntensity = intensity.coerceIn(0f, 1f)
    val glowColor = if (isNight) {
        Color(0xFF6B8DB5).copy(alpha = 0.12f * safeIntensity)
    } else {
        Color(0xFFFFE4B5).copy(alpha = 0.25f * safeIntensity)
    }
    val brush = Brush.verticalGradient(
        colors = listOf(
            glowColor,
            Color.Transparent
        ),
        startY = 0f,
        endY = size.height * 0.4f
    )
    onDrawWithContent {
        drawContent()
        drawRect(brush = brush)
    }
}

/**
 * 底部柔焦效果 Modifier
 *
 * 在页面底部添加朦胧渐变，营造层次感和深度
 * 用于衬托底部操作栏，柔和过渡
 */
fun Modifier.bottomFadeEffect(
    isNight: Boolean = false,
    intensity: Float = 0.3f
): Modifier = this.drawWithCache {
    val safeIntensity = intensity.coerceIn(0f, 1f)
    val fadeColor = if (isNight) {
        TownDesignTokens.Colors.nightDeep.copy(alpha = 0.6f * safeIntensity)
    } else {
        TownDesignTokens.Colors.bgBase.copy(alpha = 0.8f * safeIntensity)
    }
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            fadeColor
        ),
        startY = size.height * 0.7f,
        endY = size.height
    )
    onDrawWithContent {
        drawContent()
        drawRect(brush = brush)
    }
}

/**
 * 柔和径向光晕效果
 *
 * 在指定位置添加一个柔和的径向光晕
 * 用于营造氛围光源效果（如台灯、月光）
 *
 * @param centerX 光晕中心 X 轴比例 0f ~ 1f
 * @param centerY 光晕中心 Y 轴比例 0f ~ 1f
 * @param color 光晕颜色
 * @param radius 光晕半径比例 0f ~ 1f（相对于最短边）
 */
fun Modifier.radialGlow(
    centerX: Float = 0.5f,
    centerY: Float = 0.3f,
    color: Color = Color(0x20FFE4B5),
    radius: Float = 0.5f
): Modifier = this.drawWithCache {
    val safeRadius = radius.coerceIn(0.1f, 1f)
    val brush = Brush.radialGradient(
        colors = listOf(
            color,
            color.copy(alpha = color.alpha * 0.5f),
            Color.Transparent
        ),
        center = Offset(size.width * centerX, size.height * centerY),
        radius = size.minDimension * safeRadius
    )
    onDrawWithContent {
        drawContent()
        drawRect(brush = brush)
    }
}

// ==================== 5. 昼夜背景效果 ====================

/**
 * 昼夜渐变背景 Modifier
 *
 * 提供柔和的多层渐变背景，替代纯色背景
 * 白天：温暖米色渐变 + 顶部暖光
 * 夜晚：深蓝紫色渐变 + 柔和月光
 *
 * @param isNight 是否夜间模式
 * @param animated 是否启用过渡动画
 */
@Composable
fun Modifier.dayNightBackground(
    isNight: Boolean,
    animated: Boolean = true
): Modifier = composed {
    if (animated) {
        val transitionProgress by animateFloatAsState(
            targetValue = if (isNight) 1f else 0f,
            animationSpec = TownAnimationSpec.tweenAmbient(),
            label = "day-night-bg"
        )
        this.drawWithCache {
            val colors = if (transitionProgress < 0.5f) {
                listOf(
                    lerpColor(TownDesignTokens.Colors.Gradient.dayTop, TownDesignTokens.Colors.Gradient.nightTop, transitionProgress * 2),
                    lerpColor(TownDesignTokens.Colors.Gradient.dayBottom, TownDesignTokens.Colors.Gradient.nightBottom, transitionProgress * 2)
                )
            } else {
                listOf(
                    lerpColor(TownDesignTokens.Colors.Gradient.dayTop, TownDesignTokens.Colors.Gradient.nightTop, transitionProgress * 2),
                    lerpColor(TownDesignTokens.Colors.Gradient.dayBottom, TownDesignTokens.Colors.Gradient.nightBottom, transitionProgress * 2)
                )
            }
            val brush = Brush.verticalGradient(
                colors = listOf(
                    interp(TownDesignTokens.Colors.Gradient.dayTop, TownDesignTokens.Colors.Gradient.nightTop, transitionProgress),
                    interp(TownDesignTokens.Colors.Gradient.dayBottom, TownDesignTokens.Colors.Gradient.nightBottom, transitionProgress)
                )
            )
            onDrawWithContent {
                drawRect(brush = brush)
                drawContent()
            }
        }
    } else {
        this.drawWithCache {
            val brush = Brush.verticalGradient(
                colors = if (isNight) {
                    listOf(
                        TownDesignTokens.Colors.Gradient.nightTop,
                        TownDesignTokens.Colors.Gradient.nightMid,
                        TownDesignTokens.Colors.Gradient.nightBottom
                    )
                } else {
                    listOf(
                        TownDesignTokens.Colors.Gradient.dayTop,
                        TownDesignTokens.Colors.Gradient.dayBottom
                    )
                }
            )
            onDrawWithContent {
                drawRect(brush = brush)
                drawContent()
            }
        }
    }
}

// ==================== 6. 辅助函数 ====================

private fun interp(color1: Color, color2: Color, fraction: Float): Color {
    val f = fraction.coerceIn(0f, 1f)
    return Color(
        red = color1.red + (color2.red - color1.red) * f,
        green = color1.green + (color2.green - color1.green) * f,
        blue = color1.blue + (color2.blue - color1.blue) * f,
        alpha = color1.alpha + (color2.alpha - color1.alpha) * f
    )
}

private fun lerpColor(color1: Color, color2: Color, fraction: Float): Color {
    return interp(color1, color2, fraction)
}

// ==================== 7. 复合 Modifier 工厂 ====================

/**
 * 完整氛围背景 Modifier（推荐用于页面根容器）
 *
 * 包含：渐变背景 + 顶部柔光 + 底部柔焦
 * 昼夜自动切换，带柔和过渡动画
 *
 * @param isNight 是否夜间模式
 */
@Composable
fun Modifier.atmosphereBackground(
    isNight: Boolean
): Modifier = composed {
    this
        .dayNightBackground(isNight = isNight, animated = true)
        .topGlowEffect(isNight = isNight, intensity = if (isNight) 0.1f else 0.15f)
}

/**
 * 剧情淡入淡出 Modifier（备用，尚未启用）
 *
 * 用于场景切换时的整体淡入淡出转场
 * 当前未启用，预留给后续场景切换器使用
 */
@Suppress("unused")
fun Modifier.sceneFadeInOut(
    visible: Boolean
): Modifier = this.then(
    if (!visible) Modifier.clip(RoundedCornerShape(0.dp)) else Modifier
)

