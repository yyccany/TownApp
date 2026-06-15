package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.design.TownAestheticDesign
import kotlin.math.*

@Composable
fun CompositionGallery() {
    var selectedComposition by remember { mutableStateOf(TownAestheticDesign.Composition.CompositionType.CENTER) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(TownAestheticDesign.Spacing.md),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.lg)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.lg)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🎨",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.base))
                Text(
                    text = "构图大师",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.primary
                )
            }
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
            
            CompositionPreview(
                compositionType = selectedComposition,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
            
            CompositionSelector(
                selected = selectedComposition,
                onSelect = { selectedComposition = it }
            )
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
            
            CompositionDescription(selectedComposition)
        }
    }
}

@Composable
fun CompositionPreview(
    compositionType: TownAestheticDesign.Composition.CompositionType,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.md)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            when (compositionType) {
                TownAestheticDesign.Composition.CompositionType.CENTER -> 
                    CenterComposition()
                TownAestheticDesign.Composition.CompositionType.RULE_OF_THIRDS -> 
                    RuleOfThirdsComposition()
                TownAestheticDesign.Composition.CompositionType.GOLDEN_SPIRAL -> 
                    GoldenSpiralComposition()
                TownAestheticDesign.Composition.CompositionType.TRIANGLE -> 
                    TriangleComposition()
                TownAestheticDesign.Composition.CompositionType.DIAGONAL -> 
                    DiagonalComposition()
                TownAestheticDesign.Composition.CompositionType.FOREGOUND -> 
                    ForegroundComposition()
                TownAestheticDesign.Composition.CompositionType.SYMMETRIC -> 
                    SymmetricComposition()
            }
        }
    }
}

@Composable
fun CenterComposition() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(TownAestheticDesign.Spacing.md),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.size(150.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.accent.copy(alpha = 0.2f))
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "★",
                    fontSize = 64.sp,
                    color = TownAestheticDesign.ColorPalette.accent
                )
            }
        }
        
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = TownAestheticDesign.ColorPalette.border,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
                strokeWidth = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
            drawLine(
                color = TownAestheticDesign.ColorPalette.border,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        }
    }
}

@Composable
fun RuleOfThirdsComposition() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val t1 = w / 3
            val t2 = 2 * w / 3
            val s1 = h / 3
            val s2 = 2 * h / 3
            
            listOf(t1, t2).forEach { x ->
                drawLine(
                    color = TownAestheticDesign.ColorPalette.accent.copy(alpha = 0.5f),
                    start = Offset(x, 0f),
                    end = Offset(x, h),
                    strokeWidth = 1.dp.toPx()
                )
            }
            
            listOf(s1, s2).forEach { y ->
                drawLine(
                    color = TownAestheticDesign.ColorPalette.accent.copy(alpha = 0.5f),
                    start = Offset(0f, y),
                    end = Offset(w, y),
                    strokeWidth = 1.dp.toPx()
                )
            }
            
            listOf(
                Offset(t1, s1), Offset(t2, s1),
                Offset(t1, s2), Offset(t2, s2)
            ).forEach { point ->
                drawCircle(
                    color = TownAestheticDesign.ColorPalette.success,
                    radius = 8.dp.toPx(),
                    center = point,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(TownAestheticDesign.Spacing.md),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier
                    .offset((-50).dp, (-50).dp)
                    .size(80.dp)
                    .background(
                        TownAestheticDesign.ColorPalette.success.copy(alpha = 0.2f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "⭐",
                    fontSize = 40.sp,
                    color = TownAestheticDesign.ColorPalette.success
                )
            }
        }
    }
}

@Composable
fun GoldenSpiralComposition() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val maxDim = min(w, h)
            
            drawGoldenRectangles(this, w, h, maxDim)
            drawGoldenSpiral(this, w, h, maxDim)
        }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(TownAestheticDesign.Spacing.md),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .offset((30).dp, (-20).dp)
                    .size(60.dp)
                    .background(
                        TownAestheticDesign.ColorPalette.gold.copy(alpha = 0.3f),
                        CircleShape
                    )
                    .border(
                        2.dp,
                        TownAestheticDesign.ColorPalette.gold,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✨",
                    fontSize = 32.sp,
                    color = TownAestheticDesign.ColorPalette.gold
                )
            }
        }
    }
}

fun drawGoldenRectangles(canvas: androidx.compose.ui.graphics.drawscope.DrawScope, w: Float, h: Float, maxDim: Float) {
    var currentSize = maxDim * 0.8f
    var x = w - currentSize
    var y = 0f
    
    repeat(5) { i ->
        val color = when (i % 4) {
            0 -> TownAestheticDesign.ColorPalette.accent.copy(alpha = 0.1f)
            1 -> TownAestheticDesign.ColorPalette.success.copy(alpha = 0.1f)
            2 -> TownAestheticDesign.ColorPalette.warning.copy(alpha = 0.1f)
            else -> TownAestheticDesign.ColorPalette.info.copy(alpha = 0.1f)
        }
        
        canvas.drawRect(
            color = color,
            topLeft = Offset(x, y),
            size = Size(currentSize, currentSize),
            style = Stroke(width = with(canvas) { 1.5.dp.toPx() })
        )
        
        when (i % 4) {
            0 -> {
                x -= currentSize / TownAestheticDesign.Composition.GOLDEN_RATIO
                currentSize /= TownAestheticDesign.Composition.GOLDEN_RATIO
            }
            1 -> {
                y += currentSize - currentSize / TownAestheticDesign.Composition.GOLDEN_RATIO
                currentSize /= TownAestheticDesign.Composition.GOLDEN_RATIO
            }
            2 -> {
                x += currentSize
                currentSize /= TownAestheticDesign.Composition.GOLDEN_RATIO
            }
            3 -> {
                y -= currentSize / TownAestheticDesign.Composition.GOLDEN_RATIO
                currentSize /= TownAestheticDesign.Composition.GOLDEN_RATIO
                x += currentSize
            }
        }
    }
}

fun drawGoldenSpiral(canvas: androidx.compose.ui.graphics.drawscope.DrawScope, w: Float, h: Float, maxDim: Float) {
    val path = Path()
    val centerX = w * 0.38f
    val centerY = h * 0.38f
    
    var scale = 1f
    for (i in 0 until 400) {
        val angle = i * 0.1f
        val r = scale * exp(angle * 0.18f)
        val x = centerX + r * cos(angle - PI / 2).toFloat()
        val y = centerY + r * sin(angle - PI / 2).toFloat()
        
        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
        
        if (x > w || x < 0 || y > h || y < 0) break
    }
    
    canvas.drawPath(
        path = path,
        color = TownAestheticDesign.ColorPalette.gold,
        style = Stroke(width = with(canvas) { 2.dp.toPx() }, cap = StrokeCap.Round)
    )
}

@Composable
fun TriangleComposition() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            
            val path = Path().apply {
                moveTo(w * 0.5f, h * 0.2f)
                lineTo(w * 0.2f, h * 0.8f)
                lineTo(w * 0.8f, h * 0.8f)
                close()
            }
            
            drawPath(
                path = path,
                color = TownAestheticDesign.ColorPalette.accent.copy(alpha = 0.15f)
            )
            
            drawPath(
                path = path,
                color = TownAestheticDesign.ColorPalette.accent,
                style = Stroke(width = 2.dp.toPx())
            )
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(TownAestheticDesign.Spacing.md),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        TownAestheticDesign.ColorPalette.accent.copy(alpha = 0.3f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "👁️", fontSize = 28.sp)
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            TownAestheticDesign.ColorPalette.success.copy(alpha = 0.3f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "👋", fontSize = 28.sp)
                }
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            TownAestheticDesign.ColorPalette.warning.copy(alpha = 0.3f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🦶", fontSize = 28.sp)
                }
            }
        }
    }
}

@Composable
fun DiagonalComposition() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            
            drawLine(
                color = TownAestheticDesign.ColorPalette.accent,
                start = Offset(0f, h * 0.2f),
                end = Offset(w, h * 0.8f),
                strokeWidth = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
            )
            
            drawLine(
                color = TownAestheticDesign.ColorPalette.accent.copy(alpha = 0.5f),
                start = Offset(w * 0.2f, h),
                end = Offset(w * 0.8f, 0f),
                strokeWidth = 1.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 15f), 0f)
            )
        }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(TownAestheticDesign.Spacing.md),
            contentAlignment = Alignment.TopStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            TownAestheticDesign.ColorPalette.success.copy(alpha = 0.25f),
                            RoundedCornerShape(AppDimens.radiusMedium)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "📐", fontSize = 32.sp)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .size(70.dp)
                        .background(
                            TownAestheticDesign.ColorPalette.accent.copy(alpha = 0.25f),
                            RoundedCornerShape(AppDimens.radiusMedium)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "✏️", fontSize = 36.sp)
                }
            }
        }
    }
}

@Composable
fun ForegroundComposition() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.size(200.dp),
                shape = RoundedCornerShape(AppDimens.radiusLarge)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🖼️",
                        fontSize = 48.sp,
                        color = TownAestheticDesign.ColorPalette.textTertiary
                    )
                }
            }
        }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Card(
                modifier = Modifier
                    .size(100.dp)
                    .offset((-20).dp, (-20).dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.accent)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💎",
                        fontSize = 48.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SymmetricComposition() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = TownAestheticDesign.ColorPalette.accent,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
                strokeWidth = 2.dp.toPx()
            )
        }
        
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(TownAestheticDesign.Spacing.md),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.size(70.dp),
                    shape = RoundedCornerShape(AppDimens.radiusLarge),
                    colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.accent.copy(alpha = 0.2f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "◀️", fontSize = 40.sp)
                    }
                }
            }
            
            Box(
                modifier = Modifier.size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.size(60.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.gold.copy(alpha = 0.3f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "🎯", fontSize = 32.sp)
                    }
                }
            }
            
            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.size(70.dp),
                    shape = RoundedCornerShape(AppDimens.radiusLarge),
                    colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.accent.copy(alpha = 0.2f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "▶️", fontSize = 40.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun CompositionSelector(
    selected: TownAestheticDesign.Composition.CompositionType,
    onSelect: (TownAestheticDesign.Composition.CompositionType) -> Unit
) {
    val compositions = TownAestheticDesign.Composition.CompositionType.values()
    
    LazyRow(
        modifier = Modifier.graphicsLayer { clip = true },
        horizontalArrangement = Arrangement.spacedBy(TownAestheticDesign.Spacing.sm),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(compositions, key = { it }) { type ->
            CompositionTab(
                type = type,
                selected = selected == type,
                onClick = { onSelect(type) }
            )
        }
    }
}

@Composable
fun CompositionTab(
    type: TownAestheticDesign.Composition.CompositionType,
    selected: Boolean,
    onClick: () -> Unit
) {
    val (icon, label) = when (type) {
        TownAestheticDesign.Composition.CompositionType.CENTER -> "◎" to "居中"
        TownAestheticDesign.Composition.CompositionType.RULE_OF_THIRDS -> "⊞" to "三分"
        TownAestheticDesign.Composition.CompositionType.GOLDEN_SPIRAL -> "🌀" to "黄金"
        TownAestheticDesign.Composition.CompositionType.TRIANGLE -> "△" to "三角"
        TownAestheticDesign.Composition.CompositionType.DIAGONAL -> "╱" to "对角"
        TownAestheticDesign.Composition.CompositionType.FOREGOUND -> "📷" to "前景"
        TownAestheticDesign.Composition.CompositionType.SYMMETRIC -> "⟷" to "对称"
    }
    
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) 
                TownAestheticDesign.ColorPalette.primary 
            else 
                TownAestheticDesign.ColorPalette.surface
        ),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.base)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = TownAestheticDesign.Spacing.base,
                vertical = TownAestheticDesign.Spacing.sm
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 24.sp, color = if (selected) Color.White else TownAestheticDesign.ColorPalette.textPrimary)
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected) Color.White else TownAestheticDesign.ColorPalette.textPrimary
            )
        }
    }
}

@Composable
fun CompositionDescription(type: TownAestheticDesign.Composition.CompositionType) {
    val (title, description, tips) = when (type) {
        TownAestheticDesign.Composition.CompositionType.CENTER ->
            Triple(
                "居中构图",
                "将主体放在画面中心，强调主体，简洁有力。",
                listOf("适合展示重要信息", "视觉稳定", "常用于LOGO展示")
            )
        TownAestheticDesign.Composition.CompositionType.RULE_OF_THIRDS ->
            Triple(
                "三分法构图",
                "把画面分成3×3网格，把主体放在交叉点上，符合人眼视觉习惯。",
                listOf("四个黄金焦点", "避免正中央刻板", "更自然的视觉引导")
            )
        TownAestheticDesign.Composition.CompositionType.GOLDEN_SPIRAL ->
            Triple(
                "黄金螺旋构图",
                "基于斐波那契数列的黄金比例，螺旋汇聚点是视觉焦点，自然优雅。",
                listOf("最符合自然美学", "视线自然流动", "达芬奇密码同款")
            )
        TownAestheticDesign.Composition.CompositionType.TRIANGLE ->
            Triple(
                "三角形构图",
                "三点构成稳定或动感的三角形，创造平衡或张力。",
                listOf("正三角：稳定", "倒三角：动感", "斜三角：活力")
            )
        TownAestheticDesign.Composition.CompositionType.DIAGONAL ->
            Triple(
                "对角线构图",
                "利用斜线创造动感，引导视线，打破单调。",
                listOf("最强动态感", "引导视线流动", "增加画面深度")
            )
        TownAestheticDesign.Composition.CompositionType.FOREGOUND ->
            Triple(
                "前景构图",
                "在靠近镜头处放置物体，创造层次感，增加画面深度。",
                listOf("创造3D纵深感", "框架式构图", "增加故事性")
            )
        TownAestheticDesign.Composition.CompositionType.SYMMETRIC ->
            Triple(
                "对称构图",
                "左右或上下对称，庄严、正式、和谐。",
                listOf("视觉平衡", "古典美学", "建筑摄影常用")
            )
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.primary.copy(alpha = 0.05f))
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.md)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TownAestheticDesign.ColorPalette.primary
            )
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xs))
            Text(
                text = description,
                fontSize = 14.sp,
                color = TownAestheticDesign.ColorPalette.textSecondary
            )
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.sm))
            tips.forEach { tip ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "• ",
                        color = TownAestheticDesign.ColorPalette.accent,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = tip,
                        fontSize = 12.sp,
                        color = TownAestheticDesign.ColorPalette.textTertiary
                    )
                }
            }
        }
    }
}
