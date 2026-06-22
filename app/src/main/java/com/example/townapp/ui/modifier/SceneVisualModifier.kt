package com.example.townapp.ui.modifier

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
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

// ==================== 4. 复合 Modifier 工厂 ====================

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

