package com.example.townapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.randomtask.RandomSmallTask
import com.example.townapp.data.randomtask.RandomSmallTaskLibrary
import kotlinx.coroutines.delay

/**
 * 无目的随机小事界面
 *
 * 点击骰子后，随机展示一件小事。
 * 没有打卡，没有完成按钮，没有奖励。
 * 你做了就做了，没做就没做。
 * 没有人会知道。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomTaskScreen(
    onBack: () -> Unit = {}
) {
    // 当前小事
    var currentTask by remember { mutableStateOf<RandomSmallTask?>(null) }

    // 是否显示骰子
    var showDice by remember { mutableStateOf(true) }

    // 骰子弹跳动画
    val diceBounce by rememberInfiniteTransition(label = "dice").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    // 骰子旋转动画
    val diceRotation by rememberInfiniteTransition(label = "rotation").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // 淡入动画
    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showContent = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F3460)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 顶部导航
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onBack) {
                    Text(
                        text = "← 返回",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }

                Text(
                    text = "🎲 无目的随机小事",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(80.dp))
            }

            Spacer(modifier = Modifier.height(60.dp))

            // 主体内容
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn() + expandVertically()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (showDice) {
                        // 显示骰子
                        DiceDisplay(
                            bounceOffset = diceBounce * 15,
                            rotation = diceRotation,
                            onClick = {
                                currentTask = RandomSmallTaskLibrary.getRandomTask()
                                showDice = false
                            }
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        Text(
                            text = "点击骰子\n获取一件小事",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center,
                            lineHeight = 26.sp
                        )
                    } else if (currentTask != null) {
                        // 显示小事
                        TaskDisplay(
                            task = currentTask!!,
                            onReroll = {
                                currentTask = RandomSmallTaskLibrary.getRandomTask()
                            },
                            onDone = {
                                // 什么都不做，只是返回
                                onBack()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 底部说明
            AnimatedVisibility(visible = showContent) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2D2D44).copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "💡 规则说明",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD93D)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "没有打卡，没有完成按钮，没有奖励。\n你做了就做了，没做就没做。\n没有人会知道，没有人会评判你。",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

/**
 * 骰子显示
 */
@Composable
private fun DiceDisplay(
    bounceOffset: Float,
    rotation: Float,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(y = -bounceOffset.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF667EEA),
                            Color(0xFF764BA2)
                        )
                    )
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🎲",
                fontSize = 56.sp,
                modifier = Modifier.scale(1f + bounceOffset / 100)
            )
        }
    }
}

/**
 * 小事展示
 */
@Composable
private fun TaskDisplay(
    task: RandomSmallTask,
    onReroll: () -> Unit,
    onDone: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Emoji 大图标
        Text(
            text = task.emoji,
            fontSize = 80.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 分类标签
        Surface(
            color = getCategoryColor(task.category).copy(alpha = 0.2f),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = task.category.displayName,
                fontSize = 12.sp,
                color = getCategoryColor(task.category),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 小事内容
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF2D2D44)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = task.content,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp,
                modifier = Modifier.padding(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 时间和难度提示
        Text(
            text = "大约 ${task.duration} 分钟 · ${task.difficulty.displayName}",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.4f)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // 按钮组
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 再来一次
            OutlinedButton(
                onClick = onReroll,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF667EEA),
                            Color(0xFF764BA2)
                        )
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "🎲 再来一次")
            }

            // 知道了（不做也没关系）
            Button(
                onClick = onDone,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4ECDC4)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "✨ 知道了")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 温柔提示
        Text(
            text = "做了就做了，没做就没做。\n没有人会知道，没有人会评判你。",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.4f),
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

/**
 * 获取分类对应的颜色
 */
private fun getCategoryColor(category: com.example.townapp.data.randomtask.TaskCategory): Color {
    return when (category) {
        com.example.townapp.data.randomtask.TaskCategory.CARE -> Color(0xFFFF9E9E)      // 粉色
        com.example.townapp.data.randomtask.TaskCategory.MOVEMENT -> Color(0xFF4ECDC4)   // 青色
        com.example.townapp.data.randomtask.TaskCategory.PLEASURE -> Color(0xFFFFD93D)   // 黄色
        com.example.townapp.data.randomtask.TaskCategory.SOCIAL -> Color(0xFFFF6B6B)    // 红色
        com.example.townapp.data.randomtask.TaskCategory.CREATIVE -> Color(0xFF9B59B6)   // 紫色
        com.example.townapp.data.randomtask.TaskCategory.MINDFUL -> Color(0xFF3498DB)    // 蓝色
    }
}
