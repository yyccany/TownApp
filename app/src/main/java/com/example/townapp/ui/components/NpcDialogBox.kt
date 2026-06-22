package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.townapp.npc.model.CognitionState
import com.example.townapp.npc.model.FragmentType
import com.example.townapp.npc.model.NpcDisplayVo
import com.example.townapp.npc.model.TonePaletteVo

/**
 * NPC 对话弹窗（对标《去月球》叙事逻辑）
 *
 * 核心设计：**画面占70%，文字仅30%，强制留白**
 *
 * 平等原则：仅展示名字，职业仅在档案馆查阅完整人生时才显示
 * 以人为本：点击人像区域可直接清空文字，只保留画面静默观察
 *
 * 叙事流程：
 * ① 画面先行：纯人像 + 专属色调滤镜，2 秒无任何文字（空镜共情）
 * ② 打字弹出：短句逐字显示，单句最长 12 字，行间宽留白
 * ③ 读完停留：全部文字输出完成，停留 1.5 秒阅读缓冲
 * ④ 静默空镜：文字自动清空，仅保留画面 1 秒
 * ⑤ 玩家可选：继续交谈 / 直接离开 / 点击人像清空文字保持画面
 *
 * 严格遵循小镇项目铁律：
 * 1. 纯 UI 组件，无任何文件 IO、无任何数据库调用
 * 2. 文本分段、停顿节奏由 TypeWriterText 内部完成
 * 3. 字号小（14sp）、行高宽松（28sp），弱化文字视觉权重
 * 4. 不使用 AlertDialog（太密集），改用自定义宽幅弹窗
 */
@Composable
fun NpcDialogBox(
    npc: NpcDisplayVo,
    palette: TonePaletteVo?,
    onClose: () -> Unit,
    onNextDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 控制文字是否显示（点击人像时可清空文字）
    var isTextVisible by remember { mutableStateOf(true) }
    var textContent by remember { mutableStateOf(npc.currentDialog) }

    // 记录当前对话的认知等级（用于决定是否生成认知碎片）
    val currentCognitionLevel = remember { CognitionState.cognitionLevel.value }
    val cognitionTier = when {
        currentCognitionLevel < 2 -> "surface"
        currentCognitionLevel < 4 -> "observe"
        else -> "wise"
    }

    // 标记本次对话是否已生成过碎片（避免重复）
    val hasGeneratedFragment = remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
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
                modifier = modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.75f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.9f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // ========== 上半部分：画面区域（70%）==========
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.7f)
                            // 点击人像区域清空文字，只保留画面静默观察
                            .clickable {
                                isTextVisible = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        // NPC 专属色调渐变背景（使用完整调色板）
                        val moodColor = palette?.baseTint ?: getNpcMoodColor(npc.jobId)
                        Box(
                            modifier = Modifier.fillMaxSize().background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        moodColor.copy(alpha = 0.3f),
                                        Color.Black.copy(alpha = 0.5f)
                                    )
                                )
                            )
                        )

                        // NPC 像素图占位（后续替换为真实精灵图）
                        Box(
                            modifier = Modifier
                                .size(160.dp)
                                .background(Color.White.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = npc.name.take(1),
                                color = Color.White.copy(alpha = 0.2f),  // 透明度下调至 20%，弱化存在感
                                fontSize = 48.sp
                            )
                        }

                        // NPC 名字（仅显示名字，不显示职业，落实平等原则）
                        Text(
                            text = npc.name,
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }

                    // 分割线
                    HorizontalDivider(
                        color = Color.White.copy(alpha = 0.2f),
                        thickness = 1.dp
                    )

                    // ========== 下半部分：文字区域（30%）==========
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f)
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isTextVisible) {
                            // 打字机文本（小字号、宽行间距）
                            TypeWriterText(
                                text = textContent,
                                onComplete = {
                                    // 打字完成后自动进入静默空镜
                                    isTextVisible = false

                                    // 【认知联动】根据当前认知等级自动生成碎片
                                    // 严格遵循自由平等：仅在 observe/wise 视角下静默生成
                                    // 不弹窗、不通知、不强制查看，自然沉淀
                                    if (!hasGeneratedFragment.value) {
                                        when (cognitionTier) {
                                            "observe" -> {
                                                CognitionState.addFragment(
                                                    type = FragmentType.OBSERVATION,
                                                    source = "与${npc.name}的对话",
                                                    description = "以旁观视角听完了这番话"
                                                )
                                            }
                                            "wise" -> {
                                                CognitionState.addFragment(
                                                    type = FragmentType.ACCEPTANCE,
                                                    source = "与${npc.name}的对话",
                                                    description = "理解每个人都有自己的取舍"
                                                )
                                            }
                                            // surface 视角不主动生成，避免打扰
                                        }
                                        hasGeneratedFragment.value = true
                                    }
                                },
                                textColor = Color.White.copy(alpha = 0.9f),
                                lineHeight = 28,
                                textSize = 14,
                                textAlign = TextAlign.Center,
                                pauseBeforeTyping = 2000L,      // 初始空镜 2 秒
                                pauseAfterTyping = 1500L,         // 读完停留 1.5 秒
                                pauseSilentAfter = 1000L,         // 静默空镜 1 秒
                                typingDelay = 80L
                            )
                        }
                    }

                    // 底部按钮区（极小按钮，不抢画面）
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = onClose) {
                            Text("离开", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                        }
                        // 如果文字被清空，显示「继续说话」；否则显示「继续」
                        TextButton(onClick = {
                            if (!isTextVisible) {
                                // 重新开始打字
                                isTextVisible = true
                                textContent = npc.currentDialog
                            } else {
                                onNextDialog()
                            }
                        }) {
                            Text(
                                text = if (isTextVisible) "继续" else "继续说话",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 根据职业 ID 返回对应氛围色（临时占位，后续接入 NpcMoodPalette）
 *
 * 落实实事求是原则：
 * - 所有色调仅客观还原生存状态
 * - 不设置「明亮=优秀、灰暗=失败」的优劣区分
 * - 暖棕老人只是岁月沉淀，灰黄工人只是谋生常态
 */
private fun getNpcMoodColor(jobId: Int): Color {
    return when (jobId) {
        1 -> Color(0xFF1AFFF2)   // 教师
        2 -> Color(0xFF1ACCED)   // 医生
        3 -> Color(0xFF26B892)   // 工人
        4 -> Color(0xFF33A870)   // 农民
        5 -> Color(0xFF1EFFDD)   // 店主
        else -> Color(0xFF87CEEB)
    }
}
