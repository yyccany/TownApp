package com.example.townapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 打字机文本组件（对标《去月球》叙事节奏）
 *
 * 核心设计：**画面氛围先铺垫情绪，文字只做点睛收尾**
 *
 * 三层节奏：
 * 1. 浅层日常闲聊：短句快速弹出，读完自动留白 0.8 秒
 * 2. 中层人生压力：弹出后停留 1.5 秒，自动黑屏静默 0.5 秒
 * 3. 深层回忆感悟：画面完整停留 3 秒空白，再缓慢弹出一句收尾文字
 *
 * 生命周期：
 * ① 初始空镜（纯画面，无文字）→ ② 打字逐字弹出 → ③ 读完停留 → ④ 静默空镜（无文字）→ ⑤ 完成回调
 *
 * 严格遵循小镇项目铁律：
 * 1. 纯 UI 组件，无任何文件 IO、无任何数据库调用
 * 2. 文本分段、停顿节奏在组件内部完成，不污染业务层
 * 3. 文本自动按分句切割，每行不超过 12 个字
 * 4. 字号小、行间距大，弱化文字视觉权重
 */
@Composable
fun TypeWriterText(
    text: String,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    lineHeight: Int = 28,
    textSize: Int = 14,
    textAlign: TextAlign = TextAlign.Center,
    pauseBeforeTyping: Long = 2000L,       // 初始空镜时长（毫秒）
    pauseAfterTyping: Long = 1500L,         // 读完后停留时长
    pauseSilentAfter: Long = 1000L,         // 静默空镜时长
    typingDelay: Long = 80L,                // 打字间隔（毫秒）
    isDeepMemory: Boolean = false           // 是否深层回忆（增加空镜时长）
) {
    val currentText = remember { mutableStateOf("") }
    val isTyping = remember { mutableStateOf(false) }
    val showText = remember { mutableStateOf(true) }

    // 按分句切割，每行不超过 12 个字
    val splitText = remember(text) {
        splitTextByPunctuation(text, maxLength = 12)
    }

    // 深层回忆模式：加长所有停顿时间
    val adjustedPauseBefore = if (isDeepMemory) pauseBeforeTyping * 2 else pauseBeforeTyping
    val adjustedPauseAfter = if (isDeepMemory) pauseAfterTyping * 2 else pauseAfterTyping
    val adjustedPauseSilent = if (isDeepMemory) pauseSilentAfter * 2 else pauseSilentAfter

    // 动画状态
    val textAlpha by animateFloatAsState(
        targetValue = if (showText.value) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "typewriterAlpha"
    )

    LaunchedEffect(text) {
        // ① 初始空镜：纯画面，无文字
        showText.value = false
        delay(adjustedPauseBefore)

        // ② 开始打字
        showText.value = true
        isTyping.value = true
        currentText.value = ""

        // 逐句打字
        for (line in splitText) {
            // 逐字打出当前句
            for (char in line) {
                currentText.value += char
                delay(typingDelay)
            }
            // 句间停顿
            delay(200)
        }

        // ③ 读完停留
        isTyping.value = false
        delay(adjustedPauseAfter)

        // ④ 静默空镜：清空文字，只留画面
        showText.value = false
        delay(adjustedPauseSilent)

        // ⑤ 完成回调
        onComplete()
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (showText.value) {
            Text(
                text = currentText.value,
                color = textColor.copy(alpha = textAlpha),
                lineHeight = lineHeight.sp,
                fontSize = textSize.sp,
                textAlign = textAlign
            )
        }
    }
}

/**
 * 将长文本按标点切割，每行不超过 maxLength 个字
 *
 * 严格落实实事求是原则：
 * - 优先按强标点（。！？；）分割
 * - 其次按逗号分割
 * - 最后按 maxLength 强制截断，绝不超过上限
 * - 贴合普通人简短表达习惯，杜绝大段连续文字
 */
private fun splitTextByPunctuation(text: String, maxLength: Int = 12): List<String> {
    val result = mutableListOf<String>()
    var current = ""

    for (char in text) {
        // 换行符直接分割
        if (char == '\n') {
            if (current.isNotEmpty()) {
                result.add(current.trim())
                current = ""
            }
            continue
        }

        current += char

        // 优先按强标点分割（句号、感叹、问号、分号）
        if (char in listOf('。', '！', '？', '；')) {
            result.add(current.trim())
            current = ""
            continue
        }

        // 其次按逗号分割（如果当前长度超过一半）
        if (char == '，' && current.length > maxLength / 2) {
            result.add(current.trim())
            current = ""
            continue
        }

        // 按长度强制截断（硬性上限，不可超过）
        if (current.length >= maxLength) {
            result.add(current.trim())
            current = ""
        }
    }

    // 剩余文本处理
    if (current.isNotEmpty()) {
        // 如果剩余文本超过一半长度，单独成行
        if (current.length > maxLength / 2) {
            result.add(current.trim())
        } else if (result.isNotEmpty()) {
            // 否则合并到上一行
            val last = result.removeLast()
            result.add((last + current).trim())
        } else {
            result.add(current.trim())
        }
    }

    // 最终校验：确保没有任何一行超过 maxLength
    return result.map { line ->
        if (line.length > maxLength) line.take(maxLength) else line
    }
}
