package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.design.TownAestheticDesign
import kotlin.math.*

@Composable
fun LightShadowGallery() {
    var selectedLight by remember { 
        mutableStateOf(TownAestheticDesign.LightShadow.LightDirection.TOP_LEFT) 
    }
    var shadowIntensity by remember { 
        mutableStateOf(TownAestheticDesign.LightShadow.ShadowIntensity.MEDIUM) 
    }
    var animationEnabled by remember { mutableStateOf(true) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "💡",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
                Text(
                    text = "光影魔法师",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
            
            LightShadowPreview(
                lightDirection = selectedLight,
                shadowIntensity = shadowIntensity,
                animationEnabled = animationEnabled
            )
            
            Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
            
            LightDirectionSelector(
                selected = selectedLight,
                onSelect = { selectedLight = it }
            )
            
            Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
            
            ShadowIntensitySelector(
                selected = shadowIntensity,
                onSelect = { shadowIntensity = it }
            )
            
            Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = animationEnabled,
                    onCheckedChange = { animationEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.secondary,
                        checkedTrackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                    )
                )
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                Text(
                    text = "动态光影",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
            
            LightShadowDescription(selectedLight, shadowIntensity)
        }
    }
}

@Composable
fun LightShadowPreview(
    lightDirection: TownAestheticDesign.LightShadow.LightDirection,
    shadowIntensity: TownAestheticDesign.LightShadow.ShadowIntensity,
    animationEnabled: Boolean
) {
    val transition = rememberInfiniteTransition(label = "lightTransition")
    
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (animationEnabled) 8000 else 0,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    val scale by transition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (animationEnabled) 2000 else 0,
                easing = EaseInOut
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(AppDimens.radiusMedium)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val gradient = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFF8E1),
                        Color(0xFFFFECB3)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, size.height)
                )
                drawRect(brush = gradient)
            }
            
            LightSourceIndicator(lightDirection)
            
            VignetteEffect()
            
            Box(
                modifier = Modifier.scale(scale),
                contentAlignment = Alignment.Center
            ) {
                ShadowLayer(
                    lightDirection = lightDirection,
                    shadowIntensity = shadowIntensity
                )
                
                HighlightLayer(
                    lightDirection = lightDirection
                )
                
                MainObject()
            }
            
            if (animationEnabled) {
                ParticleEffects(rotation)
            }
        }
    }
}

@Composable
fun LightSourceIndicator(lightDirection: TownAestheticDesign.LightShadow.LightDirection) {
    val (xAlign, yAlign) = when (lightDirection) {
        TownAestheticDesign.LightShadow.LightDirection.TOP_LEFT -> 
            Alignment.TopStart to Alignment.TopStart
        TownAestheticDesign.LightShadow.LightDirection.TOP -> 
            Alignment.TopCenter to Alignment.TopCenter
        TownAestheticDesign.LightShadow.LightDirection.TOP_RIGHT -> 
            Alignment.TopEnd to Alignment.TopEnd
        TownAestheticDesign.LightShadow.LightDirection.LEFT -> 
            Alignment.CenterStart to Alignment.CenterStart
        TownAestheticDesign.LightShadow.LightDirection.CENTER -> 
            Alignment.Center to Alignment.Center
        TownAestheticDesign.LightShadow.LightDirection.RIGHT -> 
            Alignment.CenterEnd to Alignment.CenterEnd
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM_LEFT -> 
            Alignment.BottomStart to Alignment.BottomStart
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM -> 
            Alignment.BottomCenter to Alignment.BottomCenter
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM_RIGHT -> 
            Alignment.BottomEnd to Alignment.BottomEnd
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = xAlign
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .size(50.dp)
                .background(
                    color = Color(0xFFFFD700).copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .blur(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "☀️",
                fontSize = 28.sp
            )
        }
    }
}

@Composable
fun VignetteEffect() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val gradient = Brush.radialGradient(
            colors = listOf(
                Color.Transparent,
                Color(0x26000000)
            ),
            center = Offset(size.width / 2, size.height / 2),
            radius = size.width * 0.7f
        )
        drawRect(brush = gradient)
    }
}

@Composable
fun ShadowLayer(
    lightDirection: TownAestheticDesign.LightShadow.LightDirection,
    shadowIntensity: TownAestheticDesign.LightShadow.ShadowIntensity
) {
    val (offsetX, offsetY) = when (lightDirection) {
        TownAestheticDesign.LightShadow.LightDirection.TOP_LEFT -> 25.dp to 25.dp
        TownAestheticDesign.LightShadow.LightDirection.TOP -> 0.dp to 25.dp
        TownAestheticDesign.LightShadow.LightDirection.TOP_RIGHT -> (-25).dp to 25.dp
        TownAestheticDesign.LightShadow.LightDirection.LEFT -> 25.dp to 0.dp
        TownAestheticDesign.LightShadow.LightDirection.CENTER -> 0.dp to 0.dp
        TownAestheticDesign.LightShadow.LightDirection.RIGHT -> (-25).dp to 0.dp
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM_LEFT -> 25.dp to (-25).dp
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM -> 0.dp to (-25).dp
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM_RIGHT -> (-25).dp to (-25).dp
    }
    
    val blurRadius = when (shadowIntensity) {
        TownAestheticDesign.LightShadow.ShadowIntensity.SOFT -> 12.dp
        TownAestheticDesign.LightShadow.ShadowIntensity.MEDIUM -> 20.dp
        TownAestheticDesign.LightShadow.ShadowIntensity.HARD -> 28.dp
        TownAestheticDesign.LightShadow.ShadowIntensity.DRAMATIC -> 40.dp
    }
    
    val alpha = when (shadowIntensity) {
        TownAestheticDesign.LightShadow.ShadowIntensity.SOFT -> 0.2f
        TownAestheticDesign.LightShadow.ShadowIntensity.MEDIUM -> 0.35f
        TownAestheticDesign.LightShadow.ShadowIntensity.HARD -> 0.5f
        TownAestheticDesign.LightShadow.ShadowIntensity.DRAMATIC -> 0.7f
    }
    
    Box(
        modifier = Modifier
            .offset(x = offsetX, y = offsetY)
            .size(160.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(
                color = Color.Black.copy(alpha = alpha)
            )
            .blur(blurRadius)
    )
}

@Composable
fun HighlightLayer(lightDirection: TownAestheticDesign.LightShadow.LightDirection) {
    val (alignX, alignY) = when (lightDirection) {
        TownAestheticDesign.LightShadow.LightDirection.TOP_LEFT -> -1f to -1f
        TownAestheticDesign.LightShadow.LightDirection.TOP -> 0f to -1f
        TownAestheticDesign.LightShadow.LightDirection.TOP_RIGHT -> 1f to -1f
        TownAestheticDesign.LightShadow.LightDirection.LEFT -> -1f to 0f
        TownAestheticDesign.LightShadow.LightDirection.CENTER -> 0f to 0f
        TownAestheticDesign.LightShadow.LightDirection.RIGHT -> 1f to 0f
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM_LEFT -> -1f to 1f
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM -> 0f to 1f
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM_RIGHT -> 1f to 1f
    }
    
    Box(
        modifier = Modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(160.dp)) {
            val gradient = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.5f),
                    Color.Transparent
                ),
                center = Offset(
                    size.width * (0.5f + alignX * 0.25f),
                    size.height * (0.5f + alignY * 0.25f)
                ),
                radius = size.width * 0.4f
            )
            drawRoundRect(
                brush = gradient,
                cornerRadius = CornerRadius(32.dp.toPx())
            )
        }
    }
}

@Composable
fun MainObject() {
    Card(
        modifier = Modifier.size(160.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🏛️",
                    fontSize = 56.sp
                )
                Text(
                    text = "小镇",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ParticleEffects(rotation: Float) {
    val secondaryColor = MaterialTheme.colorScheme.secondary
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val maxRadius = min(size.width, size.height) * 0.4f
        
        repeat(12) { i ->
            val angle = (i * 30f + rotation) * PI / 180f
            val radius = maxRadius * (0.6f + 0.4f * sin((i * 0.5f + rotation * 0.01f).toDouble()).toFloat())
            val x = centerX + radius * cos(angle).toFloat()
            val y = centerY + radius * sin(angle).toFloat()
            
            drawCircle(
                color = when (i % 3) {
                    0 -> secondaryColor.copy(alpha = 0.4f)
                    1 -> Color(0xFF4CAF50).copy(alpha = 0.4f)
                    else -> Color(0xFFFFD700).copy(alpha = 0.4f)
                },
                radius = 8.dp.toPx() + 4.dp.toPx() * sin((i + rotation * 0.02f).toDouble()).toFloat().absoluteValue,
                center = Offset(x, y)
            )
        }
    }
}

@Composable
fun LightDirectionSelector(
    selected: TownAestheticDesign.LightShadow.LightDirection,
    onSelect: (TownAestheticDesign.LightShadow.LightDirection) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "光源方向",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = AppDimens.paddingSmall)
            )
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    LightButton(
                        direction = TownAestheticDesign.LightShadow.LightDirection.TOP_LEFT,
                        selected = selected == TownAestheticDesign.LightShadow.LightDirection.TOP_LEFT,
                        onClick = { onSelect(TownAestheticDesign.LightShadow.LightDirection.TOP_LEFT) },
                        icon = "↖️"
                    )
                    LightButton(
                        direction = TownAestheticDesign.LightShadow.LightDirection.TOP,
                        selected = selected == TownAestheticDesign.LightShadow.LightDirection.TOP,
                        onClick = { onSelect(TownAestheticDesign.LightShadow.LightDirection.TOP) },
                        icon = "⬆️"
                    )
                    LightButton(
                        direction = TownAestheticDesign.LightShadow.LightDirection.TOP_RIGHT,
                        selected = selected == TownAestheticDesign.LightShadow.LightDirection.TOP_RIGHT,
                        onClick = { onSelect(TownAestheticDesign.LightShadow.LightDirection.TOP_RIGHT) },
                        icon = "↗️"
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    LightButton(
                        direction = TownAestheticDesign.LightShadow.LightDirection.LEFT,
                        selected = selected == TownAestheticDesign.LightShadow.LightDirection.LEFT,
                        onClick = { onSelect(TownAestheticDesign.LightShadow.LightDirection.LEFT) },
                        icon = "⬅️"
                    )
                    LightButton(
                        direction = TownAestheticDesign.LightShadow.LightDirection.CENTER,
                        selected = selected == TownAestheticDesign.LightShadow.LightDirection.CENTER,
                        onClick = { onSelect(TownAestheticDesign.LightShadow.LightDirection.CENTER) },
                        icon = "⭐"
                    )
                    LightButton(
                        direction = TownAestheticDesign.LightShadow.LightDirection.RIGHT,
                        selected = selected == TownAestheticDesign.LightShadow.LightDirection.RIGHT,
                        onClick = { onSelect(TownAestheticDesign.LightShadow.LightDirection.RIGHT) },
                        icon = "➡️"
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    LightButton(
                        direction = TownAestheticDesign.LightShadow.LightDirection.BOTTOM_LEFT,
                        selected = selected == TownAestheticDesign.LightShadow.LightDirection.BOTTOM_LEFT,
                        onClick = { onSelect(TownAestheticDesign.LightShadow.LightDirection.BOTTOM_LEFT) },
                        icon = "↙️"
                    )
                    LightButton(
                        direction = TownAestheticDesign.LightShadow.LightDirection.BOTTOM,
                        selected = selected == TownAestheticDesign.LightShadow.LightDirection.BOTTOM,
                        onClick = { onSelect(TownAestheticDesign.LightShadow.LightDirection.BOTTOM) },
                        icon = "⬇️"
                    )
                    LightButton(
                        direction = TownAestheticDesign.LightShadow.LightDirection.BOTTOM_RIGHT,
                        selected = selected == TownAestheticDesign.LightShadow.LightDirection.BOTTOM_RIGHT,
                        onClick = { onSelect(TownAestheticDesign.LightShadow.LightDirection.BOTTOM_RIGHT) },
                        icon = "↘️"
                    )
                }
            }
        }
    }
}

@Composable
fun LightButton(
    direction: TownAestheticDesign.LightShadow.LightDirection,
    selected: Boolean,
    onClick: () -> Unit,
    icon: String
) {
    Card(
        modifier = Modifier
            .size(48.dp),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = if (selected) CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation) else CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) 
                MaterialTheme.colorScheme.secondary 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                fontSize = 20.sp,
                color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ShadowIntensitySelector(
    selected: TownAestheticDesign.LightShadow.ShadowIntensity,
    onSelect: (TownAestheticDesign.LightShadow.ShadowIntensity) -> Unit
) {
    val intensities = TownAestheticDesign.LightShadow.ShadowIntensity.values()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "阴影强度",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = AppDimens.paddingSmall)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                intensities.forEach { intensity ->
                    ShadowIntensityButton(
                        intensity = intensity,
                        selected = selected == intensity,
                        onClick = { onSelect(intensity) }
                    )
                }
            }
        }
    }
}

@Composable
fun ShadowIntensityButton(
    intensity: TownAestheticDesign.LightShadow.ShadowIntensity,
    selected: Boolean,
    onClick: () -> Unit
) {
    val (label, icon) = when (intensity) {
        TownAestheticDesign.LightShadow.ShadowIntensity.SOFT -> "柔和" to "🌥️"
        TownAestheticDesign.LightShadow.ShadowIntensity.MEDIUM -> "中等" to "⛅"
        TownAestheticDesign.LightShadow.ShadowIntensity.HARD -> "硬朗" to "🌤️"
        TownAestheticDesign.LightShadow.ShadowIntensity.DRAMATIC -> "戏剧" to "🌞"
    }
    
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (selected) 
                Color(0xFFFFD700) 
            else 
                MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = if (selected) CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation) else CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
                .clickable(onClick = onClick),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected) Color.Black else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun LightShadowDescription(
    lightDirection: TownAestheticDesign.LightShadow.LightDirection,
    shadowIntensity: TownAestheticDesign.LightShadow.ShadowIntensity
) {
    val lightDescription = when (lightDirection) {
        TownAestheticDesign.LightShadow.LightDirection.TOP_LEFT -> "左上光源"
        TownAestheticDesign.LightShadow.LightDirection.TOP -> "顶光"
        TownAestheticDesign.LightShadow.LightDirection.TOP_RIGHT -> "右上光源"
        TownAestheticDesign.LightShadow.LightDirection.LEFT -> "左侧光"
        TownAestheticDesign.LightShadow.LightDirection.CENTER -> "环形光"
        TownAestheticDesign.LightShadow.LightDirection.RIGHT -> "右侧光"
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM_LEFT -> "左下光源"
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM -> "底光"
        TownAestheticDesign.LightShadow.LightDirection.BOTTOM_RIGHT -> "右下光源"
    }
    
    val shadowDescription = when (shadowIntensity) {
        TownAestheticDesign.LightShadow.ShadowIntensity.SOFT -> "柔和弥散，如阴天自然光"
        TownAestheticDesign.LightShadow.ShadowIntensity.MEDIUM -> "层次分明，如窗户光"
        TownAestheticDesign.LightShadow.ShadowIntensity.HARD -> "边缘锐利，如阳光直射"
        TownAestheticDesign.LightShadow.ShadowIntensity.DRAMATIC -> "对比强烈，如舞台聚光灯"
    }
    
    val tips = listOf(
        "左上/右上光源：人物肖像常用方向，自然有立体感",
        "顶光：正午阳光下，显得神圣或恐怖",
        "侧光：制造强烈对比，适合表现质感",
        "底光：从下往上打，诡异恐怖效果",
        "柔和阴影：适合产品展示，显得高级",
        "硬朗阴影：适合建筑，显得有力量"
    )
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
        shape = RoundedCornerShape(AppDimens.radiusMedium)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✨",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                Text(
                    text = "$lightDescription + $shadowDescription",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            
            Text(
                text = "光影技巧小贴士",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            tips.take(3).forEach { tip ->
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "• ",
                        color = Color(0xFFFFD700),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = tip,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
