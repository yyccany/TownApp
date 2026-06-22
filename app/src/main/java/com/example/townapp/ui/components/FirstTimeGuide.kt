package com.example.townapp.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

/**
 * 首次进入小镇的轻量引导组件（非强制）
 *
 * 严格遵循自由平等原则：
 * 1. 不强迫阅读，玩家可随时关闭
 * 2. 平等呈现两条路线，不引导选择
 * 3. 仅首次进入时显示一次，之后自动消失
 * 4. 轻量克制，不破坏沉浸体验
 */
@Composable
fun FirstTimeGuide(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentStep by remember { mutableStateOf(0) }
    val totalSteps = 3

    // 自动消失定时器（30 秒后自动关闭）
    LaunchedEffect(Unit) {
        delay(30000)
        onDismiss()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable { /* 拦截点击，不关闭 */ },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            // 进度指示器
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(totalSteps) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (index <= currentStep) Color.White.copy(alpha = 0.8f)
                                else Color.White.copy(alpha = 0.3f)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 引导内容
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF2A2A2A),
                                Color(0xFF1A1A1A)
                            )
                        )
                    )
                    .padding(32.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 标题
                    Text(
                        text = getStepTitle(currentStep),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // 内容
                    Text(
                        text = getStepContent(currentStep),
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // 两条路线平等呈现
                    if (currentStep == 1) {
                        RouteGuide()
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    // 操作按钮
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // 跳过按钮
                        Text(
                            text = "跳过",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            modifier = Modifier
                                .clickable { onDismiss() }
                                .padding(8.dp)
                        )

                        // 上一步 / 下一步 / 完成
                        if (currentStep > 0) {
                            Text(
                                text = "←",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .clickable { currentStep-- }
                                    .padding(8.dp)
                            )
                        }

                        Text(
                            text = if (currentStep < totalSteps - 1) "→" else "开始",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .clickable {
                                    if (currentStep < totalSteps - 1) {
                                        currentStep++
                                    } else {
                                        onDismiss()
                                    }
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * 两条路线平等引导
 */
@Composable
private fun RouteGuide() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "你可以选择",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 11.sp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 路线A：静默观察
            RouteChip(
                icon = "👁",
                label = "纯粹旁观",
                description = "开启静默观光，看日出日落"
            )

            // 路线B：短暂相交
            RouteChip(
                icon = "💬",
                label = "与人相交",
                description = "点击 NPC，听他们的故事"
            )
        }

        Text(
            text = "两条路线完全平等，没有正确玩法",
            color = Color(0xFFFFD700).copy(alpha = 0.6f),
            fontSize = 10.sp
        )
    }
}

@Composable
private fun RouteChip(icon: String, label: String, description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = icon, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = description,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 10.sp
        )
    }
}

private fun getStepTitle(step: Int): String {
    return when (step) {
        0 -> "欢迎来到小镇"
        1 -> "你可以自由选择"
        2 -> "在这里，时间不可逆"
        else -> ""
    }
}

private fun getStepContent(step: Int): String {
    return when (step) {
        0 -> "这是一座自我运转的小镇\n每个人都在走自己的路\n你可以旁观，也可以相交"
        1 -> "你可以纯粹静默地漫步，看镇上的人日复一日\n也可以点击某个人，听他们说几句话\n两条路线完全平等，没有对错"
        2 -> "年份会逐年增长\n你变老，镇上的人也同步变老\n错过的时光，永久无法回溯"
        else -> ""
    }
}