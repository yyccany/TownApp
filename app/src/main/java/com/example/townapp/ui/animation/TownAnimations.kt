package com.example.townapp.ui.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.townapp.ui.theme.TownDesignTokens

// ============================================
// ✨ 小镇动画系统 v1.0
// 统一动效规范 · 柔和舒缓 · 治愈调性
// ============================================

// ────────────────────────────────────────────
// 🎬 常用动画规格（统一时长与缓动）
// ────────────────────────────────────────────

object TownAnimationSpec {

    fun <T> tweenFast(): TweenSpec<T> = tween(
        durationMillis = TownDesignTokens.Animation.fast,
        easing = FastOutSlowInEasing
    )

    fun <T> tweenNormal(): TweenSpec<T> = tween(
        durationMillis = TownDesignTokens.Animation.normal,
        easing = FastOutSlowInEasing
    )

    fun <T> tweenSlow(): TweenSpec<T> = tween(
        durationMillis = TownDesignTokens.Animation.slow,
        easing = FastOutSlowInEasing
    )

    fun <T> tweenGentle(): TweenSpec<T> = tween(
        durationMillis = TownDesignTokens.Animation.gentle,
        easing = FastOutSlowInEasing
    )

    fun <T> tweenAmbient(): TweenSpec<T> = tween(
        durationMillis = TownDesignTokens.Animation.ambient,
        easing = FastOutSlowInEasing
    )

    fun <T> springSoft(): SpringSpec<T> = spring(
        dampingRatio = TownDesignTokens.Animation.springDamping,
        stiffness = TownDesignTokens.Animation.springStiffness
    )

    fun <T> springGentle(): SpringSpec<T> = spring(
        dampingRatio = 0.7f,
        stiffness = 150f
    )
}

// ────────────────────────────────────────────
// 🃏 卡片展开/收起动画
// ────────────────────────────────────────────

val cardExpandEnter: EnterTransition =
    fadeIn(animationSpec = TownAnimationSpec.tweenNormal()) +
            expandVertically(
                animationSpec = TownAnimationSpec.springSoft(),
                expandFrom = Alignment.Top
            )

val cardCollapseExit: ExitTransition =
    fadeOut(animationSpec = TownAnimationSpec.tweenFast()) +
            shrinkVertically(
                animationSpec = TownAnimationSpec.tweenFast(),
                shrinkTowards = Alignment.Top
            )

val cardScaleEnter: EnterTransition =
    fadeIn(animationSpec = TownAnimationSpec.tweenNormal()) +
            scaleIn(
                animationSpec = TownAnimationSpec.springSoft(),
                initialScale = 0.92f,
                transformOrigin = TransformOrigin.Center
            )

val cardScaleExit: ExitTransition =
    fadeOut(animationSpec = TownAnimationSpec.tweenFast()) +
            scaleOut(
                animationSpec = TownAnimationSpec.tweenFast(),
                targetScale = 0.92f,
                transformOrigin = TransformOrigin.Center
            )

// ────────────────────────────────────────────
// 🌙 夜间面板滑入滑出动画
// ────────────────────────────────────────────

val nightPanelEnter: EnterTransition =
    fadeIn(animationSpec = TownAnimationSpec.tweenSlow()) +
            slideInVertically(
                animationSpec = TownAnimationSpec.tweenSlow(),
                initialOffsetY = { it / 3 }
            )

val nightPanelExit: ExitTransition =
    fadeOut(animationSpec = TownAnimationSpec.tweenNormal()) +
            slideOutVertically(
                animationSpec = TownAnimationSpec.tweenNormal(),
                targetOffsetY = { it / 4 }
            )

// ────────────────────────────────────────────
// 📜 底部弹窗滑入滑出
// ────────────────────────────────────────────

val bottomSheetEnter: EnterTransition =
    fadeIn(animationSpec = TownAnimationSpec.tweenFast()) +
            slideInVertically(
                animationSpec = TownAnimationSpec.springSoft(),
                initialOffsetY = { it }
            )

val bottomSheetExit: ExitTransition =
    fadeOut(animationSpec = TownAnimationSpec.tweenFast()) +
            slideOutVertically(
                animationSpec = TownAnimationSpec.tweenNormal(),
                targetOffsetY = { it }
            )

// ────────────────────────────────────────────
// 🔄 页面切换动画
// ────────────────────────────────────────────

val pageFadeEnter: EnterTransition =
    fadeIn(animationSpec = TownAnimationSpec.tweenSlow())

val pageFadeExit: ExitTransition =
    fadeOut(animationSpec = TownAnimationSpec.tweenNormal())

val pageSlideRightEnter: EnterTransition =
    fadeIn(animationSpec = TownAnimationSpec.tweenNormal()) +
            slideInHorizontally(
                animationSpec = TownAnimationSpec.tweenNormal(),
                initialOffsetX = { it / 4 }
            )

val pageSlideLeftExit: ExitTransition =
    fadeOut(animationSpec = TownAnimationSpec.tweenNormal()) +
            slideOutHorizontally(
                animationSpec = TownAnimationSpec.tweenNormal(),
                targetOffsetX = { -it / 4 }
            )

// ────────────────────────────────────────────
// ✨ 微交互 Modifier
// ────────────────────────────────────────────

fun Modifier.pressScale(
    pressedScale: Float = 0.96f
): Modifier = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) pressedScale else 1f,
        animationSpec = TownAnimationSpec.springSoft(),
        label = "press-scale"
    )

    this
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    isPressed = true
                    tryAwaitRelease()
                    isPressed = false
                }
            )
        }
        .scale(scale)
}

fun Modifier.hoverLift(
    elevation: Float = 4.dp.value
): Modifier = composed {
    var isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.02f else 1f,
        animationSpec = TownAnimationSpec.springSoft(),
        label = "hover-lift"
    )

    this
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    isHovered = true
                    tryAwaitRelease()
                    isHovered = false
                }
            )
        }
        .scale(scale)
}

// ────────────────────────────────────────────
// 🎐 呼吸动画（柔和明暗变化）
// ────────────────────────────────────────────

@Composable
fun rememberBreathAlpha(
    minAlpha: Float = 0.6f,
    maxAlpha: Float = 1f,
    durationMillis: Int = 3000
): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "breath")
    return infiniteTransition.animateFloat(
        initialValue = minAlpha,
        targetValue = maxAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breath-alpha"
    ).value
}

@Composable
fun rememberGentlePulse(
    minScale: Float = 0.98f,
    maxScale: Float = 1.02f,
    durationMillis: Int = 4000
): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "gentle-pulse")
    return infiniteTransition.animateFloat(
        initialValue = minScale,
        targetValue = maxScale,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gentle-pulse"
    ).value
}

// ────────────────────────────────────────────
// 🌅 昼夜过渡动画颜色
// ────────────────────────────────────────────

@Composable
fun animateDayNightColor(
    isNight: Boolean,
    dayColor: androidx.compose.ui.graphics.Color,
    nightColor: androidx.compose.ui.graphics.Color
): androidx.compose.ui.graphics.Color {
    return androidx.compose.animation.animateColorAsState(
        targetValue = if (isNight) nightColor else dayColor,
        animationSpec = TownAnimationSpec.tweenAmbient(),
        label = "day-night-color"
    ).value
}
