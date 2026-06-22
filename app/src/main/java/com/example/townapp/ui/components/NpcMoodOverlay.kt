package com.example.townapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import com.example.townapp.npc.model.TonePaletteVo

/**
 * NPC 专属情绪氛围遮罩层
 *
 * 严格遵循小镇项目铁律：
 * 1. UI 纯展示，仅接收 TonePaletteVo（Color + Float 参数）
 * 2. 无任何 ID / 文本 / 文件 IO / 数据库调用
 * 3. 触摸事件完全穿透，不拦截 NPC 点击
 * 4. 仅 visible=true 时渲染，关闭 NPC 弹窗自动消失
 *
 * 层级顺序：
 * 1. 像素画布
 * 2. 全局季节昼夜遮罩 AtmosphereOverlay
 * 3. NpcMoodOverlay ⬅️ 当前组件（三层叠加）
 *    3.1 饱和度调整（通过色调透明度模拟）
 *    3.2 人物专属色调叠加层（baseTint）
 *    3.3 动态暗角层（径向渐变 vignette）
 * 4. 悬浮 UI（MiniStatusPanel / FloatingMenu）
 * 5. 弹窗
 *
 * 视觉参数说明：
 * - baseTint：人物专属色调（已从 hex 解析为 Color）
 * - vignetteBase：暗角强度（0.0~0.6）
 * - saturation：饱和度（0.5~1.3），通过色调透明度间接实现
 *
 * 动画效果：
 * - 800ms 平滑淡入淡出（alpha 渐变）
 *
 * @param palette 调色板数据（null = 不渲染）
 * @param visible 是否可见（用于动画渐入渐出）
 */
@Composable
fun NpcMoodOverlay(
    palette: TonePaletteVo?,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    if (palette == null) return

    val fadeAnim by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "npcMoodFade"
    )

    if (fadeAnim <= 0f) return

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {}
    ) {
        val saturationFactor = palette.saturation.coerceIn(0.5f, 1.3f)
        val tintAlpha = when {
            saturationFactor < 1.0f -> fadeAnim * saturationFactor
            else -> fadeAnim
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = tintAlpha
                }
                .background(palette.baseTint)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = fadeAnim
                }
                .background(
                    Brush.radialGradient(
                        colorStops = arrayOf(
                            0.65f to Color.Transparent,
                            1.0f to Color.Black.copy(alpha = palette.vignetteBase)
                        )
                    )
                )
        )
    }
}
