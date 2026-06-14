package com.example.townapp.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import com.example.townapp.data.microevent.*

/**
 * 微事件气泡组件
 * 
 * 显示软乎乎的陪伴语录
 * 从屏幕底部弹出，带有可爱动画
 */
@Composable
fun MicroEventBubble(
    event: MicroEvent,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 弹跳动画
    val infiniteTransition = rememberInfiniteTransition(label = "bounce")
    val bounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // 点击关闭
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onDismiss() },
            color = Color.Black.copy(alpha = 0.3f)
        ) {}
        
        // 事件气泡
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-bounce * 8).dp)
                .clickable { onDismiss() },
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                getCharacterColor(event.character).copy(alpha = 0.95f),
                                getCharacterColor(event.character).copy(alpha = 0.9f)
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 角色图标
                    Text(
                        text = event.character.emoji,
                        fontSize = 48.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    // 角色名称
                    Text(
                        text = event.character.displayName,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    // 内容
                    Text(
                        text = event.content,
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 26.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // 轻触关闭提示
                    Text(
                        text = "轻触任意处关闭",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

/**
 * 导航式提示气泡（穿戴系统用）
 */
@Composable
fun NavigationTipBubble(
    tip: NavigationTip,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 淡入动画
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            // 半透明背景
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onDismiss() },
                color = Color.Black.copy(alpha = 0.3f)
            ) {}
            
            // 提示气泡
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDismiss() },
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF2D4A5A).copy(alpha = 0.95f),
                                    Color(0xFF1A3A4A).copy(alpha = 0.95f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 角色图标
                        Text(
                            text = tip.character.emoji,
                            fontSize = 36.sp,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        
                        Column {
                            // 标题
                            Text(
                                text = tip.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            
                            // 角色名
                            Text(
                                text = tip.character.displayName,
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // 内容
                    Text(
                        text = tip.content,
                        fontSize = 15.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 24.sp
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // 知道了按钮
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getCharacterColor(tip.character)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = tip.actionText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "或轻触任意处关闭",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * 获取角色对应的颜色
 */
@Composable
private fun getCharacterColor(character: TownCharacter): Color {
    return when (character) {
        TownCharacter.TAFFI -> Color(0xFFFF9E9E)    // 粉色
        TownCharacter.GUGAGA -> Color(0xFFFFD93D)   // 黄色
        TownCharacter.DORO -> Color(0xFF4ECDC4)   // 青色
    }
}

/**
 * 心情状态指示器
 */
@Composable
fun MoodIndicator(
    mood: MoodState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mood.emoji,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = mood.description,
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

/**
 * 角色状态气泡（显示在角落的角色状态）
 */
@Composable
fun CharacterStatusBubble(
    character: TownCharacter,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isActive,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        Box(
            modifier = modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(getCharacterColor(character).copy(alpha = 0.9f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = character.emoji,
                fontSize = 28.sp
            )
        }
    }
}

/**
 * 状态指示器（显示在主界面的小圆点）
 */
@Composable
fun StatusIndicator(
    label: String,
    isGood: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    if (isGood) Color(0xFF4ECDC4) else Color(0xFFFF6B6B)
                )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

/**
 * 小镇状态总览卡片
 */
@Composable
fun TownStatusCard(
    userState: UserState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2D2D44).copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "🏠 小镇状态",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // 心情
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = userState.mood.emoji,
                        fontSize = 28.sp
                    )
                    Text(
                        text = "心情",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
                
                // 喝水
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "💧 ${userState.body.waterIntake}杯",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "喝水",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
                
                // 状态
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (userState.body.hasBrushedTeeth) "🦷已刷" else "🦷未刷",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "刷牙",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 环境状态
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatusIndicator(
                    label = "垃圾桶",
                    isGood = !userState.environment.isTrashFull
                )
                StatusIndicator(
                    label = "桌面",
                    isGood = !userState.environment.isDeskDusty
                )
                StatusIndicator(
                    label = "衣物",
                    isGood = !userState.environment.isClothesPiled
                )
            }
        }
    }
}

/**
 * 角色选择器
 */
@Composable
fun CharacterSelector(
    onCharacterSelected: (TownCharacter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TownCharacter.entries.forEach { character ->
            CharacterButton(
                character = character,
                onClick = { onCharacterSelected(character) }
            )
        }
    }
}

/**
 * 角色按钮
 */
@Composable
private fun CharacterButton(
    character: TownCharacter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .size(56.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = getCharacterColor(character)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = character.emoji,
                fontSize = 32.sp
            )
        }
    }
}
