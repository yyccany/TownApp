package com.example.townapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlin.math.ceil

object PixelPalette {
    val DarkBg = Color(0xFF1A1A2E)
    val DarkBg2 = Color(0xFF16213E)
    val Highlight = Color(0xFFE94560)
    val Gold = Color(0xFFFFD93D)
    val Green = Color(0xFF6BCB77)
    val Blue = Color(0xFF4D96FF)
    val Purple = Color(0xFF9B59B6)
    val Cyan = Color(0xFF00D9FF)
    val LightGray = Color(0xFFEEEEEE)
    val DarkGray = Color(0xFF333333)
    val Border = Color(0xFF0F0F0F)
    val PixelWhite = Color(0xFFFAFAFA)
}

fun Modifier.pixelBorder(
    borderWidth: Dp = 2.dp,
    borderColor: Color = PixelPalette.Border,
    innerHighlight: Boolean = true
): Modifier = this.then(
    Modifier
        .border(borderWidth, borderColor, RectangleShape)
        .then(
            if (innerHighlight) {
                Modifier.padding(borderWidth)
                    .border(1.dp, borderColor.copy(alpha = 0.3f), RectangleShape)
                    .padding(1.dp)
            } else {
                Modifier.padding(borderWidth)
            }
        )
)

fun Modifier.pixelScanlines(
    alpha: Float = 0.08f,
    lineHeight: Dp = 2.dp
): Modifier = this.then(
    Modifier.drawWithCache {
        val linePx = lineHeight.toPx()
        onDrawWithContent {
            drawContent()
            var y = 0f
            while (y < size.height) {
                drawRect(
                    color = Color.Black.copy(alpha = alpha),
                    topLeft = Offset(0f, y),
                    size = androidx.compose.ui.geometry.Size(size.width, linePx / 2)
                )
                y += linePx
            }
        }
    }
)

fun Modifier.pixelInnerShadow(): Modifier = this.then(
    Modifier.graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
        .drawWithCache {
            onDrawWithContent {
                drawContent()
                drawRect(
                    color = Color.Black.copy(alpha = 0.15f),
                    blendMode = BlendMode.SrcOver
                )
            }
        }
)

@Composable
fun PixelImageCard(
    imagePath: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null
) {
    val cardModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    Card(
        modifier = cardModifier,
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = PixelPalette.DarkBg
        ),
        border = BorderStroke(2.dp, PixelPalette.Border)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .border(1.dp, PixelPalette.DarkGray, RectangleShape)
        ) {
            if (imagePath != null && imagePath.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imagePath)
                        .crossfade(true)
                        .build(),
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PixelPalette.DarkBg2),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "无图",
                        color = PixelPalette.LightGray.copy(alpha = 0.5f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun PixelImageGrid(
    imagePaths: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: ((Int) -> Unit)? = null
) {
    val count = imagePaths.size

    when {
        count == 0 -> {
            PixelEmptyPlaceholder(modifier = modifier)
        }
        count == 1 -> {
            PixelLayout1(imagePaths[0], modifier, onImageClick)
        }
        count == 2 -> {
            PixelLayout2(imagePaths, modifier, onImageClick)
        }
        count == 3 -> {
            PixelLayout3(imagePaths, modifier, onImageClick)
        }
        count == 4 -> {
            PixelLayout4(imagePaths, modifier, onImageClick)
        }
        count == 5 -> {
            PixelLayout5(imagePaths, modifier, onImageClick)
        }
        count == 6 -> {
            PixelLayout6(imagePaths, modifier, onImageClick)
        }
        count == 7 -> {
            PixelLayout7(imagePaths, modifier, onImageClick)
        }
        count == 8 -> {
            PixelLayout8(imagePaths, modifier, onImageClick)
        }
        else -> {
            PixelLayoutN(imagePaths, modifier, onImageClick)
        }
    }
}

@Composable
private fun PixelEmptyPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(PixelPalette.DarkBg, RectangleShape)
            .border(2.dp, PixelPalette.Border, RectangleShape)
            .padding(2.dp)
            .border(1.dp, PixelPalette.DarkGray, RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "📷",
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "暂无图片",
                color = PixelPalette.LightGray.copy(alpha = 0.6f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun PixelLayout1(
    path: String,
    modifier: Modifier = Modifier,
    onImageClick: ((Int) -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
    ) {
        PixelImageCard(
            imagePath = path,
            modifier = Modifier.fillMaxSize(),
            onClick = { onImageClick?.invoke(0) }
        )
    }
}

@Composable
private fun PixelLayout2(
    paths: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: ((Int) -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        PixelImageCard(
            imagePath = paths[0],
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f),
            onClick = { onImageClick?.invoke(0) }
        )
        PixelImageCard(
            imagePath = paths[1],
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f),
            onClick = { onImageClick?.invoke(1) }
        )
    }
}

@Composable
private fun PixelLayout3(
    paths: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: ((Int) -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        PixelImageCard(
            imagePath = paths[0],
            modifier = Modifier
                .weight(2f)
                .aspectRatio(1f),
            onClick = { onImageClick?.invoke(0) }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(0.5f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            PixelImageCard(
                imagePath = paths[1],
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onClick = { onImageClick?.invoke(1) }
            )
            PixelImageCard(
                imagePath = paths[2],
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onClick = { onImageClick?.invoke(2) }
            )
        }
    }
}

@Composable
private fun PixelLayout4(
    paths: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: ((Int) -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            PixelImageCard(
                imagePath = paths[0],
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                onClick = { onImageClick?.invoke(0) }
            )
            PixelImageCard(
                imagePath = paths[1],
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                onClick = { onImageClick?.invoke(1) }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            PixelImageCard(
                imagePath = paths[2],
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                onClick = { onImageClick?.invoke(2) }
            )
            PixelImageCard(
                imagePath = paths[3],
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                onClick = { onImageClick?.invoke(3) }
            )
        }
    }
}

@Composable
private fun PixelLayout5(
    paths: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: ((Int) -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            PixelImageCard(
                imagePath = paths[0],
                modifier = Modifier
                    .weight(2f)
                    .aspectRatio(2f),
                onClick = { onImageClick?.invoke(0) }
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                PixelImageCard(
                    imagePath = paths[1],
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    onClick = { onImageClick?.invoke(1) }
                )
                PixelImageCard(
                    imagePath = paths[2],
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    onClick = { onImageClick?.invoke(2) }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            PixelImageCard(
                imagePath = paths[3],
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                onClick = { onImageClick?.invoke(3) }
            )
            PixelImageCard(
                imagePath = paths[4],
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                onClick = { onImageClick?.invoke(4) }
            )
        }
    }
}

@Composable
private fun PixelLayout6(
    paths: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: ((Int) -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        repeat(3) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(2) { col ->
                    val index = row * 2 + col
                    if (index < paths.size) {
                        PixelImageCard(
                            imagePath = paths[index],
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            onClick = { onImageClick?.invoke(index) }
                        )
                    } else {
                        Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun PixelLayout7(
    paths: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: ((Int) -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            ) {
                PixelImageCard(
                    imagePath = paths[0],
                    modifier = Modifier.fillMaxSize(),
                    onClick = { onImageClick?.invoke(0) }
                )
            }
            Box(
                modifier = Modifier
                    .weight(2f)
                    .aspectRatio(2f)
            ) {
                PixelImageCard(
                    imagePath = paths[1],
                    modifier = Modifier.fillMaxSize(),
                    onClick = { onImageClick?.invoke(1) }
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            ) {
                PixelImageCard(
                    imagePath = paths[2],
                    modifier = Modifier.fillMaxSize(),
                    onClick = { onImageClick?.invoke(2) }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            repeat(4) { col ->
                val index = col + 3
                if (index < paths.size) {
                    PixelImageCard(
                        imagePath = paths[index],
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onClick = { onImageClick?.invoke(index) }
                    )
                } else {
                    Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                }
            }
        }
    }
}

@Composable
private fun PixelLayout8(
    paths: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: ((Int) -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        repeat(4) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(2) { col ->
                    val index = row * 2 + col
                    if (index < paths.size) {
                        PixelImageCard(
                            imagePath = paths[index],
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            onClick = { onImageClick?.invoke(index) }
                        )
                    } else {
                        Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun PixelLayoutN(
    paths: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: ((Int) -> Unit)? = null
) {
    val cols = 3
    val rows = kotlin.math.ceil(paths.size.toFloat() / cols).toInt()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        repeat(rows) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(cols) { col ->
                    val index = row * cols + col
                    if (index < paths.size) {
                        PixelImageCard(
                            imagePath = paths[index],
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                            onClick = { onImageClick?.invoke(index) }
                        )
                    } else {
                        Box(modifier = Modifier.weight(1f).aspectRatio(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun PixelImageCardWithLabel(
    imagePath: String?,
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PixelImageCard(
            imagePath = imagePath,
            modifier = Modifier
                .size(64.dp),
            onClick = onClick
        )
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun PixelDecoratedCard(
    imagePath: String?,
    title: String,
    modifier: Modifier = Modifier,
    cornerColor: Color = PixelPalette.Highlight,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = if (onClick != null) modifier.clickable { onClick() } else modifier,
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = PixelPalette.DarkBg
        ),
        border = BorderStroke(2.dp, PixelPalette.Border)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(cornerColor, RectangleShape)
                    .align(Alignment.TopStart)
            )
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(cornerColor, RectangleShape)
                    .align(Alignment.TopEnd)
            )
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(cornerColor, RectangleShape)
                    .align(Alignment.BottomStart)
            )
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(cornerColor, RectangleShape)
                    .align(Alignment.BottomEnd)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (imagePath != null && imagePath.isNotEmpty()) {
                    AsyncImage(
                        model = imagePath,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RectangleShape)
                    )
                }
                Text(
                    text = title,
                    color = PixelPalette.PixelWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}