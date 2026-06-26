package com.example.townapp.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.abs

// 低饱和莫兰迪像素配色，和UI工牌风格统一
private val grassGreen = Color(0xFFA7BE9A)
private val grassGreenDark = Color(0xFF95AD88)
private val roadGray = Color(0xFFC8BFA8)
private val pathLight = Color(0xFFD8CFB8)
private val waterBlue = Color(0xFF9EB7C2)
private val flowerPink = Color(0xFFD8B5C2)
private val flowerYellow = Color(0xFFE6D5B0)
private val treeTrunk = Color(0xFF8B6F52)
private val treeGreen = Color(0xFF7A9A77)
private val treeGreenDark = Color(0xFF6A8A67)
private val roofRed = Color(0xFFC07C6C)
private val roofDarkRed = Color(0xFFB06C5C)
private val wallWarm = Color(0xFFF0E8D8)
private val wallBlue = Color(0xFFC0CAD6)
private val shopPink = Color(0xFFD8BFCB)
private val clinicWhite = Color(0xFFEAEFF0)
private val shadowColor = Color(0x22000000)
private val textColor = Color(0xFF2D2A26)

@Composable
fun Pixel2DTownCanvas(
    sceneState: Scene2DState,
    currentHour: Int,
    onPlayerMoved: (dx: Float, dy: Float) -> Unit,
    onStopWalking: () -> Unit,
    onInteract: () -> Unit,
    modifier: Modifier = Modifier,
    canvasSize: Size = Size(800f, 600f)
) {
    val textMeasurer = rememberTextMeasurer()
    var animationFrame by remember { mutableStateOf(0) }

    LaunchedEffect(sceneState.isWalking) {
        while (sceneState.isWalking) {
            delay(150)
            animationFrame = (animationFrame + 1) % 4
        }
    }

    val halfWidth = canvasSize.width / 2f
    val halfHeight = canvasSize.height / 2f
    val cameraX = (sceneState.playerPosition.x - halfWidth).coerceIn(-200f, 200f)
    val cameraY = (sceneState.playerPosition.y - halfHeight).coerceIn(-150f, 150f)

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onInteract() }
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val moveScale = 0.5f
                            onPlayerMoved(dragAmount.x * moveScale, dragAmount.y * moveScale)
                        },
                        onDragEnd = { onStopWalking() },
                        onDragCancel = { onStopWalking() }
                    )
                }
        ) {
            val width = size.width
            val height = size.height

            drawBackground(width, height, currentHour)

            translate(left = -cameraX, top = -cameraY) {
                drawGround(width, height)

                drawPaths()

                drawDecorations()

                if (currentHour !in 7..18) {
                    drawStreetLamps()
                }

                val sortedBuildings = sceneState.buildings.sortedBy { it.position.y }
                sortedBuildings.forEach { building ->
                    val isNearby = building.buildingId == sceneState.nearbyBuildingId
                    if (isNearby) {
                        drawSelectionGlow(
                            center = building.position,
                            radius = maxOf(building.size.x, building.size.y) * 0.7f
                        )
                    }
                    drawBuilding(
                        building = building,
                        isNearby = isNearby,
                        isNight = currentHour !in 7..18,
                        textMeasurer = textMeasurer
                    )
                }

                val sortedNpcs = sceneState.npcs.sortedBy { it.position.y }
                sortedNpcs.forEach { npc ->
                    val isNearby = npc.npcId == sceneState.nearbyNpcId
                    if (isNearby) {
                        drawSelectionGlow(
                            center = npc.position.copy(y = npc.position.y - 10f),
                            radius = 35f
                        )
                    }
                    drawNpc(
                        npc = npc,
                        isNearby = isNearby,
                        textMeasurer = textMeasurer,
                        animationFrame = animationFrame
                    )
                }

                drawPlayer(
                    state = sceneState,
                    animationFrame = animationFrame
                )

                if (currentHour !in 7..18) {
                    drawNightOverlay(width, height, currentHour)
                }
            }
        }

        val nearbyBuilding = sceneState.buildings.firstOrNull { building ->
            val dx = building.position.x - sceneState.playerPosition.x
            val dy = building.position.y - sceneState.playerPosition.y
            val dist = kotlin.math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
            dist < 130f
        }
        val nearbyNpc = sceneState.npcs.firstOrNull { npc ->
            val dx = npc.position.x - sceneState.playerPosition.x
            val dy = npc.position.y - sceneState.playerPosition.y
            val dist = kotlin.math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
            dist < 100f
        }
        val interactionHint = when {
            nearbyBuilding != null -> "点击进入 ${nearbyBuilding.name}"
            nearbyNpc != null -> "点击和 ${nearbyNpc.name} 说话"
            else -> null
        }
        if (interactionHint != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Surface(
                    color = Color(0xFFFDFBF6).copy(alpha = 0.95f),
                    shape = RoundedCornerShape(2.dp),
                    border = BorderStroke(1.dp, Color(0xFF2D2A26)),
                    shadowElevation = 0.dp
                ) {
                    Text(
                        text = interactionHint,
                        fontSize = 13.sp,
                        color = Color(0xFF2D2A26),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
        ) {
            Surface(
                color = Color(0xFFFDFBF6).copy(alpha = 0.85f),
                shape = RoundedCornerShape(2.dp),
                border = BorderStroke(1.dp, Color(0xFF2D2A26).copy(alpha = 0.6f)),
                shadowElevation = 0.dp
            ) {
                Text(
                    text = "拖动屏幕移动 · 点击互动",
                    fontSize = 11.sp,
                    color = Color(0xFF5A554E),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

private fun DrawScope.drawBackground(width: Float, height: Float, hour: Int) {
    val skyGradient = when {
        // 清晨：低饱和暖米
        hour in 5..6 -> Brush.verticalGradient(
            0f to Color(0xFFE8C8A0),
            0.5f to Color(0xFFF0DCB8),
            1f to Color(0xFFF8F0E0)
        )
        // 上午：低饱和浅蓝
        hour in 7..9 -> Brush.verticalGradient(
            0f to Color(0xFFB0C4D0),
            0.6f to Color(0xFFC5D5DE),
            1f to Color(0xFFE5ECEF)
        )
        // 中午：低饱和天蓝色
        hour in 10..15 -> Brush.verticalGradient(
            0f to Color(0xFF8EAEC4),
            0.5f to Color(0xFFADC2D4),
            1f to Color(0xFFC5D5DE)
        )
        // 黄昏：低饱和暖橘
        hour in 16..17 -> Brush.verticalGradient(
            0f to Color(0xFFE0A880),
            0.4f to Color(0xFFE8BA98),
            1f to Color(0xFFF0D8B8)
        )
        // 傍晚：低饱和蓝紫
        hour in 18..19 -> Brush.verticalGradient(
            0f to Color(0xFF686888),
            0.5f to Color(0xFF7878A0),
            1f to Color(0xFFD8A890)
        )
        // 夜晚：低饱和深蓝灰
        hour in 20..21 -> Brush.verticalGradient(
            0f to Color(0xFF3A4458),
            0.6f to Color(0xFF5A6A80),
            1f to Color(0xFF7A8AA0)
        )
        // 深夜：低饱和深灰蓝
        else -> Brush.verticalGradient(
            0f to Color(0xFF242935),
            0.5f to Color(0xFF343C4E),
            1f to Color(0xFF4A5468)
        )
    }
    drawRect(
        brush = skyGradient,
        size = Size(width, height * 0.4f)
    )

    if (hour in 20..24 || hour in 0..4) {
        for (i in 0..20) {
            val starX = (i * 137 + 50) % width.toInt()
            val starY = (i * 83 + 30) % (height * 0.35f).toInt()
            drawCircle(
                color = Color.White.copy(alpha = 0.7f),
                radius = 1.5f,
                center = Offset(starX.toFloat(), starY.toFloat())
            )
        }
    }
}

private fun DrawScope.drawGround(width: Float, height: Float) {
    drawRect(
        color = grassGreen,
        topLeft = Offset(-100f, height * 0.35f),
        size = Size(width + 200f, height * 0.65f + 100f)
    )
    val tileSize = 32f
    var x = -100f
    while (x < width + 100f) {
        var y = height * 0.35f
        while (y < height + 100f) {
            if ((x.toInt() / 32 + y.toInt() / 32) % 2 == 0) {
                drawRect(
                    color = grassGreenDark,
                    topLeft = Offset(x, y),
                    size = Size(tileSize, tileSize),
                    alpha = 0.3f
                )
            }
            y += tileSize
        }
        x += tileSize
    }
}

private fun DrawScope.drawPaths() {
    drawRect(
        color = roadGray,
        topLeft = Offset(-100f, 330f),
        size = Size(1000f, 60f)
    )
    drawRect(
        color = roadGray,
        topLeft = Offset(370f, 100f),
        size = Size(60f, 500f)
    )
}

private fun DrawScope.drawDecorations() {
    val treePositions = listOf(
        Offset(80f, 150f),
        Offset(720f, 170f),
        Offset(60f, 480f),
        Offset(700f, 500f),
        Offset(300f, 100f),
        Offset(550f, 80f),
        Offset(150f, 520f),
    )
    treePositions.forEach { pos ->
        drawTree(pos.x, pos.y)
    }

    val flowerPositions = listOf(
        Offset(250f, 400f) to flowerPink,
        Offset(320f, 420f) to flowerYellow,
        Offset(500f, 280f) to flowerPink,
        Offset(50f, 300f) to flowerYellow,
        Offset(650f, 450f) to flowerPink,
        Offset(200f, 200f) to flowerYellow,
    )
    flowerPositions.forEach { (pos, color) ->
        drawFlower(pos.x, pos.y, color)
    }
}

private fun DrawScope.drawTree(x: Float, y: Float) {
    drawRect(
        color = shadowColor,
        topLeft = Offset(x - 10f, y + 5f),
        size = Size(20f, 50f)
    )
    drawRect(
        color = treeTrunk,
        topLeft = Offset(x - 8f, y - 40f),
        size = Size(16f, 55f)
    )
    drawCircle(
        color = treeGreen,
        radius = 38f,
        center = Offset(x, y - 55f)
    )
    drawCircle(
        color = treeGreenDark,
        radius = 28f,
        center = Offset(x - 18f, y - 45f)
    )
    drawCircle(
        color = treeGreenDark,
        radius = 28f,
        center = Offset(x + 18f, y - 45f)
    )
    drawCircle(
        color = treeGreen.copy(alpha = 0.9f),
        radius = 22f,
        center = Offset(x, y - 70f)
    )
}

private fun DrawScope.drawFlower(x: Float, y: Float, color: Color) {
    drawCircle(
        color = color,
        radius = 5f,
        center = Offset(x - 5f, y)
    )
    drawCircle(
        color = color,
        radius = 5f,
        center = Offset(x + 5f, y)
    )
    drawCircle(
        color = color,
        radius = 5f,
        center = Offset(x, y - 5f)
    )
    drawCircle(
        color = color,
        radius = 5f,
        center = Offset(x, y + 5f)
    )
    drawCircle(
        color = Color.Yellow,
        radius = 4f,
        center = Offset(x, y)
    )
}

private fun DrawScope.drawSelectionGlow(center: Offset, radius: Float) {
    drawCircle(
        brush = Brush.radialGradient(
            0f to Color(0x55C9A66B),
            0.6f to Color(0x33C9A66B),
            1f to Color(0x00C9A66B)
        ),
        radius = radius * 1.3f,
        center = center,
        alpha = 0.7f
    )
    drawCircle(
        color = Color(0xFFC9A66B),
        radius = 2.5f,
        center = center.copy(y = center.y - radius * 0.8f)
    )
}

private fun DrawScope.drawBuilding(
    building: BuildingScenePosition,
    isNearby: Boolean,
    isNight: Boolean,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
) {
    val x = building.position.x
    val y = building.position.y
    val w = building.size.x
    val h = building.size.y

    drawOval(
        color = shadowColor,
        topLeft = Offset(x - w / 2 + 5f, y + h / 2 - 10f),
        size = Size(w + 10f, 20f)
    )

    val wallColor = when (building.consumptionStyle) {
        ConsumptionStyle.PEOPLE_ORIENTED -> if (isNight) wallWarm.copy(alpha = 0.8f) else wallWarm
        ConsumptionStyle.VANITY_ORIENTED -> if (isNight) shopPink.copy(alpha = 0.8f) else shopPink
        ConsumptionStyle.NEUTRAL -> if (isNight) wallBlue.copy(alpha = 0.8f) else wallBlue
    }
    drawRect(
        color = wallColor,
        topLeft = Offset(x - w / 2, y - h / 2),
        size = Size(w, h * 0.75f)
    )

    drawRect(
        color = wallColor.copy(alpha = 0.7f),
        topLeft = Offset(x - w / 2, y - h / 2),
        size = Size(w, 8f)
    )

    val roofColor = when (building.buildingType) {
        BuildingType.CLINIC -> if (isNight) clinicWhite.copy(alpha = 0.9f) else clinicWhite
        BuildingType.SHOP -> Color(0xFFD4A5D4)
        BuildingType.MARKET -> Color(0xFFA5C4A5)
        else -> roofRed
    }
    drawPath(
        Path().apply {
            moveTo(x - w / 2 - 12f, y - h / 2)
            lineTo(x, y - h / 2 - 40f)
            lineTo(x + w / 2 + 12f, y - h / 2)
            close()
        },
        color = roofColor
    )
    drawPath(
        Path().apply {
            moveTo(x - w / 2 - 12f, y - h / 2)
            lineTo(x, y - h / 2 - 40f)
            lineTo(x, y - h / 2)
            close()
        },
        color = roofDarkRed
    )

    val doorColor = if (isNight) Color(0xFF4A3828) else Color(0xFF6B5344)
    drawRect(
        color = doorColor,
        topLeft = Offset(x - 15f, y),
        size = Size(30f, h * 0.4f)
    )
    if (isNight) {
        drawRect(
            color = Color(0xFFFFE4B5).copy(alpha = 0.4f),
            topLeft = Offset(x - 12f, y + 5f),
            size = Size(24f, h * 0.35f - 5f)
        )
    }
    drawCircle(
        color = Color(0xFFFFD700),
        radius = 3f,
        center = Offset(x + 8f, y + h * 0.2f)
    )

    val windowY = y - h / 2 + 20f
    val windowColor = if (isNight) {
        Brush.verticalGradient(
            0f to Color(0xFFFFE4B5),
            1f to Color(0xFFFFD700).copy(alpha = 0.7f)
        )
    } else {
        Brush.linearGradient(listOf(Color(0xFFADD8E6).copy(alpha = 0.8f), Color(0xFFB0E0E6).copy(alpha = 0.8f)))
    }
    drawRect(
        brush = windowColor,
        topLeft = Offset(x - w / 4 - 10f, windowY),
        size = Size(26f, 26f)
    )
    drawRect(
        color = if (isNight) Color(0xFF8B4513).copy(alpha = 0.6f) else Color(0xFF888888),
        topLeft = Offset(x - w / 4 + 2f, windowY),
        size = Size(2f, 26f)
    )
    drawRect(
        color = if (isNight) Color(0xFF8B4513).copy(alpha = 0.6f) else Color(0xFF888888),
        topLeft = Offset(x - w / 4 - 10f, windowY + 12f),
        size = Size(26f, 2f)
    )

    drawRect(
        brush = windowColor,
        topLeft = Offset(x + w / 4 - 16f, windowY),
        size = Size(26f, 26f)
    )
    drawRect(
        color = if (isNight) Color(0xFF8B4513).copy(alpha = 0.6f) else Color(0xFF888888),
        topLeft = Offset(x + w / 4 - 4f, windowY),
        size = Size(2f, 26f)
    )
    drawRect(
        color = if (isNight) Color(0xFF8B4513).copy(alpha = 0.6f) else Color(0xFF888888),
        topLeft = Offset(x + w / 4 - 16f, windowY + 12f),
        size = Size(26f, 2f)
    )

    if (building.consumptionStyle == ConsumptionStyle.PEOPLE_ORIENTED) {
        drawCircle(
            color = if (isNight) Color(0xFF2D5A2D) else Color(0xFF4CAF50),
            radius = 14f,
            center = Offset(x + w / 2 - 8f, y + h * 0.3f)
        )
        drawRect(
            color = Color(0xFF8B4513),
            topLeft = Offset(x + w / 2 - 10f, y + h * 0.3f + 10f),
            size = Size(4f, 15f)
        )
    }

    if (building.consumptionStyle == ConsumptionStyle.VANITY_ORIENTED) {
        drawCircle(
            color = if (isNight) Color(0xFFFFB300) else Color(0xFFFFD700),
            radius = 8f,
            center = Offset(x - w / 2 + 10f, y - h / 4)
        )
    }

    val nameBgColor = if (isNight) Color(0xCC222233) else Color(0xCCFFFFFF)
    val nameTextColor = if (isNight) Color(0xFFE0E0E0) else textColor
    val nameStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 12.sp,
        color = nameTextColor,
        background = nameBgColor
    )
    val nameLayout = textMeasurer.measure(building.name, style = nameStyle)
    drawText(
        textMeasurer = textMeasurer,
        text = building.name,
        style = nameStyle,
        topLeft = Offset(x - nameLayout.size.width / 2f, y - h / 2 - 55f)
    )
}

private fun DrawScope.drawStreetLamps() {
    val lampPositions = listOf(
        Offset(250f, 360f),
        Offset(500f, 360f),
        Offset(380f, 200f),
        Offset(380f, 480f)
    )
    lampPositions.forEach { pos ->
        drawRect(
            color = Color(0xFF4A4540),
            topLeft = Offset(pos.x - 3f, pos.y - 50f),
            size = Size(6f, 60f)
        )
        drawCircle(
            color = Color(0xFFE8D0A8),
            radius = 8f,
            center = Offset(pos.x, pos.y - 55f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                0f to Color(0x44E8C888),
                0.5f to Color(0x22E8D0A8),
                1f to Color(0x00E8D0A8)
            ),
            radius = 90f,
            center = Offset(pos.x, pos.y - 40f)
        )
    }
}

private fun DrawScope.drawNightOverlay(width: Float, height: Float, hour: Int) {
    val alpha = when {
        hour in 19..20 -> 0.20f
        hour in 21..22 -> 0.35f
        hour in 23..4 -> 0.45f
        hour in 5..6 -> 0.25f
        else -> 0.15f
    }
    drawRect(
        brush = Brush.verticalGradient(
            0f to Color(0x1A1C23).copy(alpha = alpha * 0.6f),
            0.4f to Color(0x272A35).copy(alpha = alpha),
            1f to Color(0x383C4A).copy(alpha = alpha * 0.8f)
        ),
        topLeft = Offset(-200f, 0f),
        size = Size(width + 400f, height + 200f)
    )
}

private fun DrawScope.drawNpc(
    npc: NpcScenePosition,
    isNearby: Boolean,
    textMeasurer: androidx.compose.ui.text.TextMeasurer,
    animationFrame: Int
) {
    val x = npc.position.x
    val y = npc.position.y
    val bobOffset = if (animationFrame % 2 == 0) 0f else -2f

    drawOval(
        color = shadowColor,
        topLeft = Offset(x - 16f, y + 28f),
        size = Size(32f, 8f)
    )

    val clothingColor = getNpcClothingColor(npc.consumptionTendency)

    drawRect(
        color = clothingColor,
        topLeft = Offset(x - 12f, y - 5f + bobOffset),
        size = Size(24f, 28f)
    )

    drawRect(
        color = Color(0xFF4A4A4A),
        topLeft = Offset(x - 9f, y + 23f + bobOffset),
        size = Size(7f, 12f)
    )
    drawRect(
        color = Color(0xFF4A4A4A),
        topLeft = Offset(x + 2f, y + 23f + bobOffset),
        size = Size(7f, 12f)
    )

    drawCircle(
        color = Color(0xFFFFDBAC),
        radius = 13f,
        center = Offset(x, y - 18f + bobOffset)
    )

    drawCircle(
        color = Color.Black,
        radius = 2f,
        center = Offset(x - 4f, y - 20f + bobOffset)
    )
    drawCircle(
        color = Color.Black,
        radius = 2f,
        center = Offset(x + 4f, y - 20f + bobOffset)
    )

    drawCircle(
        color = Color(0xFFFF9999),
        radius = 2f,
        center = Offset(x - 8f, y - 14f + bobOffset),
        alpha = 0.5f
    )
    drawCircle(
        color = Color(0xFFFF9999),
        radius = 2f,
        center = Offset(x + 8f, y - 14f + bobOffset),
        alpha = 0.5f
    )

    if (npc.bubbleText != null) {
        val bubbleStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 11.sp,
            color = textColor
        )
        val textLayout = textMeasurer.measure(npc.bubbleText, style = bubbleStyle)
        val bubbleWidth = textLayout.size.width + 20f
        val bubbleHeight = 28f
        val bubbleX = x - bubbleWidth / 2f
        val bubbleY = y - 70f + bobOffset

        drawRoundRect(
            color = Color.White.copy(alpha = 0.95f),
            topLeft = Offset(bubbleX, bubbleY),
            size = Size(bubbleWidth, bubbleHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
        )
        drawCircle(
            color = Color.White.copy(alpha = 0.95f),
            radius = 6f,
            center = Offset(x - 8f, bubbleY + bubbleHeight)
        )
        drawCircle(
            color = Color.White.copy(alpha = 0.95f),
            radius = 4f,
            center = Offset(x - 16f, bubbleY + bubbleHeight + 5f)
        )

        drawText(
            textMeasurer = textMeasurer,
            text = npc.bubbleText,
            style = bubbleStyle,
            topLeft = Offset(bubbleX + 10f, bubbleY + 6f)
        )
    }

    val nameStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 10.sp,
        color = textColor,
        background = Color(0x88FFFFFF)
    )
    val nameLayout = textMeasurer.measure(npc.name, style = nameStyle)
    drawText(
        textMeasurer = textMeasurer,
        text = npc.name,
        style = nameStyle,
        topLeft = Offset(x - nameLayout.size.width / 2f, y - 40f + bobOffset)
    )
}

private fun DrawScope.drawPlayer(
    state: Scene2DState,
    animationFrame: Int
) {
    val x = state.playerPosition.x
    val y = state.playerPosition.y
    val bobOffset = if (state.isWalking && animationFrame % 2 == 0) 0f else -3f

    drawOval(
        color = shadowColor,
        topLeft = Offset(x - 18f, y + 32f),
        size = Size(36f, 10f)
    )

    drawRect(
        color = state.birthIdentity.playerColor,
        topLeft = Offset(x - 14f, y - 8f + bobOffset),
        size = Size(28f, 32f)
    )

    val legOffset = if (state.isWalking) (if (animationFrame % 2 == 0) 4f else -4f) else 0f
    drawRect(
        color = state.birthIdentity.playerColor.copy(alpha = 0.8f),
        topLeft = Offset(x - 10f, y + 24f + bobOffset + legOffset),
        size = Size(9f, 14f - legOffset)
    )
    drawRect(
        color = state.birthIdentity.playerColor.copy(alpha = 0.8f),
        topLeft = Offset(x + 1f, y + 24f + bobOffset - legOffset),
        size = Size(9f, 14f + legOffset)
    )

    drawCircle(
        color = Color(0xFFFFDBAC),
        radius = 15f,
        center = Offset(x, y - 22f + bobOffset)
    )

    drawCircle(
        color = Color.Black,
        radius = 2f,
        center = Offset(x - 5f, y - 24f + bobOffset)
    )
    drawCircle(
        color = Color.Black,
        radius = 2f,
        center = Offset(x + 5f, y - 24f + bobOffset)
    )

    drawCircle(
        color = Color(0xFFFF9999),
        radius = 2.5f,
        center = Offset(x - 9f, y - 18f + bobOffset),
        alpha = 0.5f
    )
    drawCircle(
        color = Color(0xFFFF9999),
        radius = 2.5f,
        center = Offset(x + 9f, y - 18f + bobOffset),
        alpha = 0.5f
    )

    drawCircle(
        color = Color(0xFFFFD700),
        radius = 3f,
        center = Offset(x + 12f, y - 28f + bobOffset)
    )
}

private fun getNpcClothingColor(consumptionTendency: Int): Color {
    return when {
        consumptionTendency >= 60 -> Color(0xFF8FBC8F)
        consumptionTendency <= 40 -> Color(0xFFDDA0DD)
        else -> Color(0xFFA9A9A9)
    }
}
