package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.townapp.npc.model.MemoryFragmentVo
import com.example.townapp.npc.model.FragmentType
import com.example.townapp.npc.model.CognitionState
import com.example.townapp.ui.modifier.memoryFilter

/**
 * 记忆碎片弹窗（对标《去月球》回忆叙事）
 *
 * 核心设计：**画面80%，文字仅20%，胶片式画面主导**
 *
 * 认知联动（落实「以人为本」）：
 * - cognitionTier = "surface"：低认知版本，画面暗角略重，文字情绪直白
 * - cognitionTier = "observe"：中认知版本，中性色调，看见客观处境
 * - cognitionTier = "wise"：高认知版本，画面柔光提亮，文字客观和解
 *
 * 布局结构：
 * ┌─────────────────────────────┐
 * │                             │
 * │     回忆场景像素大图        │  ← 80% 高度
 * │     （褪色低饱和滤镜）      │
 * │                             │
 * ├─────────────────────────────┤
 * │                             │
 * │     一行极短总结文字        │  ← 20% 高度
 * │     （底部窄条）            │
 * │                             │
 * └─────────────────────────────┘
 *
 * 叙事节奏：
 * ① 画面完整展示 3 秒（无任何文字）
 * ② 缓慢弹出一行短回忆台词
 * ③ 停留 2 秒后自动进入静默空镜
 * ④ 文字消失，只留回忆褪色画面
 *
 * 严格遵循小镇项目铁律：
 * 1. 纯 UI 组件，无任何文件 IO、无任何数据库调用
 * 2. 回忆滤镜已在 SceneVisualModifier 中实现，此处仅调用
 * 3. 文字极少，大量时间只靠光影画面传递情绪
 * 4. 不主动铺满文字，杜绝开篇就展示全部内容
 */
@Composable
fun MemoryFragmentDialog(
    memory: MemoryFragmentVo,
    onDismiss: () -> Unit
) {
    // ========== 认知联动：视觉差异化 ==========
    val (topTint, vignetteAlpha, filterIntensity, glowAlpha) = when (memory.cognitionTier) {
        "wise" -> Tuple4(
            Color(0xFFF4E4BC).copy(alpha = 0.25f),  // 柔光暖色
            0.15f,                                  // 弱暗角
            0.5f,                                   // 中度滤镜
            0.2f                                    // 柔光加成
        )
        "observe" -> Tuple4(
            Color(0xFFD4A574).copy(alpha = 0.2f),   // 中性米黄
            0.25f,                                  // 中等暗角
            0.7f,                                   // 标准滤镜
            0.1f
        )
        else -> Tuple4(
            Color(0xFFB8A082).copy(alpha = 0.18f),  // 偏灰冷调
            0.4f,                                   // 略重暗角
            0.8f,                                   // 偏重滤镜
            0f
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .fillMaxHeight(0.85f)
                    .memoryFilter(intensity = filterIntensity, noiseDensity = 0.15f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.95f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // ========== 上半部分：回忆画面区域（80%）==========
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.8f),
                        contentAlignment = Alignment.Center
                    ) {
                        // 回忆场景渐变背景（认知联动色调）
                        Box(
                            modifier = Modifier.fillMaxSize().background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        topTint,
                                        Color(0xFF2C2416).copy(alpha = 0.8f)
                                    )
                                )
                            )
                        )

                        // 高认知柔光效果
                        if (glowAlpha > 0) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.radialGradient(
                                            colors = listOf(
                                                Color(0xFFFFFAE8).copy(alpha = glowAlpha),
                                                Color.Transparent
                                            )
                                        )
                                    )
                            )
                        }

                        // 回忆场景像素图占位（后续替换为真实精灵图）
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .background(Color.White.copy(alpha = 0.08f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = memory.title.take(1),
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 64.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }

                        // 认知视角标识（极淡、极小、不抢画面）
                        if (memory.cognitionTier != "surface") {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(20.dp)
                            ) {
                                Text(
                                    text = when (memory.cognitionTier) {
                                        "wise" -> "通透"
                                        "observe" -> "旁观"
                                        else -> ""
                                    },
                                    color = Color(0xFFFFD700).copy(alpha = 0.35f),
                                    fontSize = 10.sp
                                )
                            }
                        }

                        // 时间标记（极小字号，不抢画面）
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(bottom = 24.dp)
                        ) {
                            Text(
                                text = "${memory.seasonName} · ${memory.year}年",
                                color = Color.White.copy(alpha = 0.4f),
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic
                            )
                            if (memory.yearsAgo > 0) {
                                Text(
                                    text = "${memory.yearsAgo}年前",
                                    color = Color.White.copy(alpha = 0.3f),
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }

                    // 分割线（极细）
                    HorizontalDivider(
                        color = Color.White.copy(alpha = 0.15f),
                        thickness = 0.5.dp
                    )

                    // ========== 下半部分：文字区域（20%）==========
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.2f)
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // 打字机文本（深层回忆模式：加长空镜时长）
                        // 颜色根据认知等级微调：
                        // - surface：偏冷白（情绪直白）
                        // - observe：中性白（客观陈述）
                        // - wise：暖白（接纳平和）
                        val textColor = when (memory.cognitionTier) {
                            "wise" -> Color(0xFFF5E6D3).copy(alpha = 0.85f)
                            "observe" -> Color.White.copy(alpha = 0.85f)
                            else -> Color(0xFFCCCCCC).copy(alpha = 0.85f)
                        }

                        TypeWriterText(
                            text = memory.content,
                            onComplete = {
                                // 观看完记忆后自动添加观察类认知碎片（自然沉淀，不弹窗）
                                CognitionState.addFragment(
                                    type = FragmentType.OBSERVATION,
                                    source = memory.title,
                                    description = memory.content.take(30)
                                )
                            },
                            textColor = textColor,
                            lineHeight = 26,
                            textSize = 13,
                            textAlign = TextAlign.Center,
                            pauseBeforeTyping = 3000L,
                            pauseAfterTyping = 2000L,
                            pauseSilentAfter = 2000L,
                            typingDelay = 100L,
                            isDeepMemory = true
                        )
                    }

                    // 关联 NPC（如有）
                    if (memory.relatedNpcName.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "——与 ${memory.relatedNpcName}",
                                color = Color.White.copy(alpha = 0.4f),
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }

                    // 底部按钮（极小，几乎看不见）
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("继续", color = Color.White.copy(alpha = 0.4f), fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 临时元组（用于认知联动配置）
 */
private data class Tuple4<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)
