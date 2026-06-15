package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.theme.*
import kotlin.math.roundToInt

// ============================================
// 通用组件库 —— 对标产品规划报告视觉
// 白底卡片 + 1px边框 + 20px圆角 + 微阴影
// ============================================

/**
 * 分段标题
 */
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = BrandColors.TextPrimary,
        modifier = Modifier.padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium)
    )
}

/**
 * 信息行 - 标签 + 值
 */
@Composable
fun InfoRow(
    label: String,
    value: String,
    valueColor: Color = BrandColors.TextPrimary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppDimens.paddingSmall),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = BrandColors.TextSecondary)
        Text(text = value, fontSize = 13.sp, color = valueColor, fontWeight = FontWeight.Medium)
    }
}

/**
 * 统计卡片
 */
@Composable
fun StatCard(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(4.dp)
            .shadow(4.dp, RoundedCornerShape(AppDimens.radiusXLarge))
            .clip(RoundedCornerShape(AppDimens.radiusXLarge))
            .background(Color.White)
            .border(1.dp, BrandColors.CardBorder, RoundedCornerShape(AppDimens.radiusXLarge))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = label, fontSize = 11.sp, color = BrandColors.TextSecondary)
        }
    }
}

/**
 * 空状态提示
 */
@Composable
fun EmptyHint(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 13.sp, color = BrandColors.TextSecondary)
    }
}

/**
 * 营养素进度条
 */
@Composable
fun NutrientProgressBar(
    name: String,
    valuePer100g: Double,
    nrv: Double,
    color: Color,
    unit: String = "g"
) {
    val nrvPercent = if (nrv > 0) (valuePer100g / nrv) * 100 else 0.0
    val progress = minOf(nrvPercent / 100.0, 1.0).toFloat()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name, fontSize = 12.sp, color = BrandColors.TextSecondary, modifier = Modifier.weight(1f))
        Text(text = "$valuePer100g$unit", fontSize = 12.sp, color = BrandColors.TextPrimary, fontWeight = FontWeight.Medium)
    }
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(BrandColors.CardBorder)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(progress).height(6.dp).background(color, RoundedCornerShape(3.dp))
            )
        }
        Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
        Text(text = "${nrvPercent.roundToInt()}% NRV", fontSize = 10.sp, color = BrandColors.TextTertiary)
    }
}

/**
 * 面料特性进度条
 */
@Composable
fun FabricPropertyBar(name: String, value: Double, color: Color) {
    val displayValue = value.coerceIn(0.0, 1.0).toFloat()
    val levelText = when {
        displayValue >= 0.8f -> "优秀"
        displayValue >= 0.6f -> "良好"
        displayValue >= 0.4f -> "中等"
        else -> "一般"
    }
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = name, fontSize = 12.sp, color = BrandColors.TextSecondary, modifier = Modifier.weight(1f))
        Text(text = levelText, fontSize = 12.sp, color = color, fontWeight = FontWeight.Medium)
    }
    Spacer(modifier = Modifier.height(4.dp))
    Box(modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(BrandColors.CardBorder)) {
        Box(modifier = Modifier.fillMaxWidth(displayValue).height(6.dp).background(color, RoundedCornerShape(3.dp)))
    }
}

/**
 * 风险项组件
 */
@Composable
fun RiskItem(name: String, level: String, ratio: Double, color: Color, description: String) {
    val displayRatio = ratio.coerceIn(0.0, 1.0).toFloat()
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = name, fontSize = 13.sp, color = BrandColors.TextPrimary, modifier = Modifier.weight(1f))
        Text(text = level, fontSize = 13.sp, color = color, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(4.dp))
    Box(modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)).background(BrandColors.CardBorder)) {
        Box(modifier = Modifier.fillMaxWidth(displayRatio).height(4.dp).background(color, RoundedCornerShape(2.dp)))
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = description, fontSize = 11.sp, color = BrandColors.TextSecondary)
}

/**
 * 智商税分项组件
 */
@Composable
fun ScamBreakdownItem(name: String, value: Int, color: Color, description: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(2.dp)).background(color))
        Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
        Text(text = name, fontSize = 12.sp, color = BrandColors.TextPrimary, modifier = Modifier.weight(1f))
        Text(text = "${value}分", fontSize = 12.sp, color = color, fontWeight = FontWeight.Medium)
    }
    Spacer(modifier = Modifier.height(2.dp))
    Text(text = description, fontSize = 11.sp, color = BrandColors.TextSecondary, modifier = Modifier.padding(start = AppDimens.paddingLarge))
}

/**
 * 成本进度条
 */
@Composable
fun CostProgressBar(name: String, value: Double, total: Double, color: Color) {
    val progress = if (total > 0) (value / total).toFloat().coerceIn(0f, 1f) else 0f
    val percent = if (total > 0) (value / total * 100).roundToInt() else 0
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = name, fontSize = 12.sp, color = BrandColors.TextSecondary, modifier = Modifier.weight(1f))
        Text(text = "¥${String.format("%.0f", value)}", fontSize = 12.sp, color = BrandColors.TextPrimary, fontWeight = FontWeight.Medium)
    }
    Spacer(modifier = Modifier.height(4.dp))
    Box(modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(BrandColors.CardBorder)) {
        Box(modifier = Modifier.fillMaxWidth(progress).height(6.dp).background(color, RoundedCornerShape(3.dp)))
    }
}

/**
 * 详情分区标题
 */
@Composable
fun DetailSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold,
            color = BrandColors.TextPrimary,
            modifier = Modifier.padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium)
        )
        ReportCard(modifier = Modifier.padding(horizontal = AppDimens.paddingLarge)) { content() }
    }
}

/**
 * 分区卡片 —— 报告风格：白底 + 1px边框 + 20dp圆角 + 微阴影
 */
@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier,
    highlightColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    ReportCard(
        modifier = modifier.padding(horizontal = AppDimens.paddingLarge, vertical = 6.dp),
        highlightColor = highlightColor
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = BrandColors.TextPrimary
        )
        Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
        content()
    }
}

/**
 * 报告风格卡片 —— 无标题，纯容器
 */
@Composable
fun ReportCard(
    modifier: Modifier = Modifier,
    highlightColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(AppDimens.radiusXLarge))
            .clip(RoundedCornerShape(AppDimens.radiusXLarge))
            .background(Color.White)
            .border(1.dp, BrandColors.CardBorder, RoundedCornerShape(AppDimens.radiusXLarge))
    ) {
        // 渐变高光层（如报告中的 highlight-gradient）
        if (highlightColor != Color.Transparent) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.linearGradient(
                            0.0f to Color.Transparent,
                            1.0f to highlightColor
                        )
                    )
            )
        }
        Column(modifier = Modifier.padding(20.dp)) {
            content()
        }
    }
}

/**
 * 设置项
 */
@Composable
fun SettingItem(
    title: String,
    description: String = "",
    onClick: () -> Unit = {},
    trailing: @Composable (() -> Unit)? = null,
    contentDescription: String = title
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button) { onClick() }
            .padding(horizontal = AppDimens.paddingLarge, vertical = 14.dp)
            .semantics {
                this.contentDescription = contentDescription
                role = Role.Button
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, color = BrandColors.TextPrimary, fontWeight = FontWeight.Medium)
            if (description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = description, fontSize = 12.sp, color = BrandColors.TextSecondary)
            }
        }
        if (trailing != null) trailing()
        else Text("→", color = BrandColors.TextTertiary, fontSize = 16.sp)
    }
}

/**
 * 标签组件
 */
@Composable
fun TagChip(text: String, color: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier.clip(RoundedCornerShape(6.dp)).background(color.copy(alpha = 0.1f)).padding(horizontal = 10.dp, vertical = 5.dp)) {
        Text(text = text, fontSize = 11.sp, color = color, fontWeight = FontWeight.Medium)
    }
}

/**
 * 主按钮
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String = text
) {
    Button(
        onClick = onClick,
        modifier = modifier.semantics {
            this.contentDescription = contentDescription
            role = Role.Button
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = BrandColors.Blue,
            contentColor = Color.White,
            disabledContainerColor = BrandColors.Blue.copy(alpha = 0.4f),
            disabledContentColor = Color.White.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 6.dp,
            focusedElevation = 6.dp
        )
    ) {
        Text(text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

/**
 * 次要按钮
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = text
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.semantics {
            this.contentDescription = contentDescription
            role = Role.Button
        },
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = BrandColors.Blue,
            disabledContentColor = BrandColors.TextTertiary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 2.dp,
            focusedElevation = 2.dp
        ),
        border = BorderStroke(1.5.dp, BrandColors.Blue)
    ) {
        Text(text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

/**
 * 维度卡片（用于主页功能入口）
 */
@Composable
fun DimensionCard(
    icon: String,
    name: String,
    desc: String,
    count: Int,
    color: Color,
    isLocked: Boolean = false,
    onClick: () -> Unit,
    contentDescription: String = if (isLocked) "$name，即将开放" else "$name，$desc，共${count}个"
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button) { onClick() }
            .semantics {
                this.contentDescription = contentDescription
                role = Role.Button
            },
        shape = RoundedCornerShape(AppDimens.radiusXLarge),
        colors = CardDefaults.cardColors(
            containerColor = if (isLocked) BrandColors.TagBg else Color.White,
            contentColor = BrandColors.TextPrimary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 6.dp,
            focusedElevation = 6.dp
        ),
        border = BorderStroke(1.dp, BrandColors.CardBorder)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            Text(text = name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = if (isLocked) BrandColors.TextSecondary else BrandColors.TextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = if (isLocked) "即将开放" else desc, fontSize = 12.sp, color = BrandColors.TextSecondary)
            if (!isLocked) {
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                Text(text = "$count 件", fontSize = 13.sp, color = color, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ============================================
// 通用组件库 END
// ============================================
