package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 带风险角标的像素食材图标。
 * 保留原有像素图标设计，右上角增量增加风险角标。
 * 局部刷新，角标单独更新，不全局重绘。
 *
 * @param emoji 食材 emoji 图标
 * @param categoryColor 品类背景色
 * @param riskLevel 风险等级：LOW / MEDIUM / HIGH / EXTREME
 * @param size 图标尺寸
 */
@Composable
fun RiskBadgeFoodIcon(
    emoji: String,
    categoryColor: Color,
    riskLevel: String = "LOW",
    size: Dp = 56.dp,
    modifier: Modifier = Modifier
) {
    val badgeSize = (size.value * 0.35f).dp
    val badgeColor = riskBadgeColor(riskLevel)
    val badgeSymbol = riskBadgeSymbol(riskLevel)

    Box(
        modifier = modifier.size(size + 4.dp), // 多4dp容纳角标突出
        contentAlignment = Alignment.TopEnd
    ) {
        // 主图标：像素食材色块
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(12.dp))
                .background(categoryColor.copy(alpha = 0.3f))
                .align(Alignment.BottomStart),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = (size.value * 0.4f).sp)
        }

        // 风险角标（右上角，局部独立绘制）
        Box(
            modifier = Modifier
                .size(badgeSize)
                .clip(CircleShape)
                .background(badgeColor)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = badgeSymbol,
                fontSize = (badgeSize.value * 0.6f).sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

/** 风险等级 → 角标颜色 */
fun riskBadgeColor(level: String): Color = when (level) {
    "LOW" -> Color(0xFF4CAF50)       // 绿色圆点
    "MEDIUM" -> Color(0xFFFFC107)    // 黄色
    "HIGH" -> Color(0xFFFF9800)      // 橙色
    "EXTREME" -> Color(0xFFF44336)   // 红色
    else -> Color(0xFF9E9E9E)        // 灰色（未知）
}

/** 风险等级 → 角标符号 */
fun riskBadgeSymbol(level: String): String = when (level) {
    "LOW" -> "●"       // 白色小圆点 → 实际用绿色
    "MEDIUM" -> "■"    // 黄色小方块
    "HIGH" -> "▲"      // 橙色三角
    "EXTREME" -> "✕"   // 红色叉号
    else -> "?"
}

/** 风险等级 → 中文描述 */
fun riskBadgeLabel(level: String): String = when (level) {
    "LOW" -> "低风险"
    "MEDIUM" -> "中风险"
    "HIGH" -> "高风险"
    "EXTREME" -> "严重风险"
    else -> "未知"
}