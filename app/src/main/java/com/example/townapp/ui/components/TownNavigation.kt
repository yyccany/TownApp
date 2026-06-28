package com.example.townapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.theme.DictionaryTokens

@Composable
fun TownBottomNavigation(
    currentTab: String,
    onTabChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = DictionaryTokens.pagePadding)
            .padding(bottom = 16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(DictionaryTokens.radiusCard),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val tabs = listOf(
                    Triple("town", "字典", "book"),
                    Triple("settings", "设置", "gear")
                )
                tabs.forEach { (id, label, iconType) ->
                    val selected = currentTab == id
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
                            .clickable { onTabChange(id) }
                            .background(
                                if (selected) DictionaryTokens.primary.copy(alpha = 0.12f)
                                else Color.Transparent
                            )
                            .padding(horizontal = 32.dp, vertical = 6.dp)
                    ) {
                        TabIcon(
                            type = iconType,
                            tint = if (selected) DictionaryTokens.primary else DictionaryTokens.textCaptionThemed(),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                            color = if (selected) DictionaryTokens.primary else DictionaryTokens.textCaptionThemed()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TabIcon(type: String, tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 1.6f * density

        when (type) {
            "book" -> {
                // 书本：一个矩形，中间一条竖线
                drawRect(
                    tint,
                    topLeft = Offset(w * 0.1f, h * 0.15f),
                    size = Size(w * 0.8f, h * 0.7f),
                    style = Stroke(stroke)
                )
                drawLine(
                    tint,
                    Offset(w * 0.5f, h * 0.15f),
                    Offset(w * 0.5f, h * 0.85f),
                    stroke,
                    cap = StrokeCap.Round
                )
                // 书页横线
                drawLine(
                    tint,
                    Offset(w * 0.22f, h * 0.35f),
                    Offset(w * 0.45f, h * 0.35f),
                    stroke * 0.7f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    tint,
                    Offset(w * 0.22f, h * 0.55f),
                    Offset(w * 0.45f, h * 0.55f),
                    stroke * 0.7f,
                    cap = StrokeCap.Round
                )
            }
            "gear" -> {
                // 齿轮：外圆+内部十字
                drawCircle(tint, radius = w * 0.32f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(stroke))
                drawCircle(
                    tint,
                    radius = w * 0.12f,
                    center = Offset(w * 0.5f, h * 0.5f),
                    style = Stroke(stroke)
                )
                // 四个小齿
                val teeth = listOf(
                    Offset(w * 0.5f, h * 0.1f) to Offset(w * 0.5f, h * 0.25f),
                    Offset(w * 0.5f, h * 0.9f) to Offset(w * 0.5f, h * 0.75f),
                    Offset(w * 0.1f, h * 0.5f) to Offset(w * 0.25f, h * 0.5f),
                    Offset(w * 0.9f, h * 0.5f) to Offset(w * 0.75f, h * 0.5f)
                )
                teeth.forEach { (start, end) ->
                    drawLine(tint, start, end, stroke, cap = StrokeCap.Round)
                }
            }
        }
    }
}
