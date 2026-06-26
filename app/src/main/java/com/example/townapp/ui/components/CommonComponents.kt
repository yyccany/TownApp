package com.example.townapp.ui.components

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
import com.example.townapp.ui.animation.pressScale
import kotlin.math.roundToInt

// ============================================
// 通用组件库 v2.0 — 基于 TownDesignTokens
// 温暖治愈 · 安静陪伴 · 柔和层次
// ============================================

/**
 * 分段标题
 */
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = TownDesignTokens.Colors.textPrimary,
        modifier = Modifier.padding(
            horizontal = TownDesignTokens.Spacing.lg,
            vertical = TownDesignTokens.Spacing.md
        )
    )
}

/**
 * 信息行 - 标签 + 值
 */
@Composable
fun InfoRow(
    label: String,
    value: String,
    valueColor: Color = TownDesignTokens.Colors.textPrimary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = TownDesignTokens.Spacing.sm),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TownDesignTokens.Colors.textSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor,
            fontWeight = FontWeight.Medium
        )
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
            .padding(TownDesignTokens.Spacing.xs)
            .shadow(
                TownDesignTokens.Elevation.md,
                RoundedCornerShape(TownDesignTokens.Radius.xl)
            )
            .clip(RoundedCornerShape(TownDesignTokens.Radius.xl))
            .background(TownDesignTokens.Colors.bgSurface)
            .border(
                1.dp,
                TownDesignTokens.Colors.border,
                RoundedCornerShape(TownDesignTokens.Radius.xl)
            )
            .padding(TownDesignTokens.Spacing.lg),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(TownDesignTokens.Spacing.xs))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = TownDesignTokens.Colors.textSecondary
            )
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
            .padding(vertical = TownDesignTokens.Spacing.xxxl),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = TownDesignTokens.Colors.textSecondary
        )
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
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = TownDesignTokens.Colors.textSecondary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "$valuePer100g$unit",
            style = MaterialTheme.typography.bodySmall,
            color = TownDesignTokens.Colors.textPrimary,
            fontWeight = FontWeight.Medium
        )
    }
    Spacer(modifier = Modifier.height(TownDesignTokens.Spacing.xs))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(TownDesignTokens.Radius.xs))
                .background(TownDesignTokens.Colors.border)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(6.dp)
                    .background(color, RoundedCornerShape(TownDesignTokens.Radius.xs))
            )
        }
        Spacer(modifier = Modifier.width(TownDesignTokens.Spacing.sm))
        Text(
            text = "${nrvPercent.roundToInt()}% NRV",
            style = MaterialTheme.typography.labelSmall,
            color = TownDesignTokens.Colors.textTertiary
        )
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = TownDesignTokens.Colors.textSecondary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = levelText,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
    Spacer(modifier = Modifier.height(TownDesignTokens.Spacing.xs))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(TownDesignTokens.Radius.xs))
            .background(TownDesignTokens.Colors.border)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(displayValue)
                .height(6.dp)
                .background(color, RoundedCornerShape(TownDesignTokens.Radius.xs))
        )
    }
}

/**
 * 风险项组件
 */
@Composable
fun RiskItem(name: String, level: String, ratio: Double, color: Color, description: String) {
    val displayRatio = ratio.coerceIn(0.0, 1.0).toFloat()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = TownDesignTokens.Colors.textPrimary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = level,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(TownDesignTokens.Spacing.xs))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(TownDesignTokens.Colors.border)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(displayRatio)
                .height(4.dp)
                .background(color, RoundedCornerShape(2.dp))
        )
    }
    Spacer(modifier = Modifier.height(TownDesignTokens.Spacing.xs))
    Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        color = TownDesignTokens.Colors.textSecondary
    )
}

/**
 * 智商税分项组件
 */
@Composable
fun ScamBreakdownItem(name: String, value: Int, color: Color, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(RoundedCornerShape(TownDesignTokens.Radius.xs))
                .background(color)
        )
        Spacer(modifier = Modifier.width(TownDesignTokens.Spacing.sm))
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = TownDesignTokens.Colors.textPrimary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${value}分",
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
    Spacer(modifier = Modifier.height(2.dp))
    Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        color = TownDesignTokens.Colors.textSecondary,
        modifier = Modifier.padding(start = TownDesignTokens.Spacing.lg)
    )
}

/**
 * 成本进度条
 */
@Composable
fun CostProgressBar(name: String, value: Double, total: Double, color: Color) {
    val progress = if (total > 0) (value / total).toFloat().coerceIn(0f, 1f) else 0f
    val percent = if (total > 0) (value / total * 100).roundToInt() else 0
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = TownDesignTokens.Colors.textSecondary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "¥${String.format("%.0f", value)}",
            style = MaterialTheme.typography.bodySmall,
            color = TownDesignTokens.Colors.textPrimary,
            fontWeight = FontWeight.Medium
        )
    }
    Spacer(modifier = Modifier.height(TownDesignTokens.Spacing.xs))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(TownDesignTokens.Radius.xs))
            .background(TownDesignTokens.Colors.border)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(6.dp)
                .background(color, RoundedCornerShape(TownDesignTokens.Radius.xs))
        )
    }
}

/**
 * 详情分区标题
 */
@Composable
fun DetailSection(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = TownDesignTokens.Colors.textPrimary,
            modifier = Modifier.padding(
                horizontal = TownDesignTokens.Spacing.lg,
                vertical = TownDesignTokens.Spacing.md
            )
        )
        ReportCard(modifier = Modifier.padding(horizontal = TownDesignTokens.Spacing.lg)) {
            content()
        }
    }
}

/**
 * 分区卡片 — 温暖治愈风格
 */
@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier,
    highlightColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    ReportCard(
        modifier = modifier.padding(
            horizontal = TownDesignTokens.Spacing.lg,
            vertical = TownDesignTokens.Spacing.xs
        ),
        highlightColor = highlightColor
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TownDesignTokens.Colors.textPrimary
        )
        Spacer(modifier = Modifier.height(TownDesignTokens.Spacing.md))
        content()
    }
}

/**
 * 报告风格卡片 — 无标题，纯容器
 */
@Composable
fun ReportCard(
    modifier: Modifier = Modifier,
    highlightColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                TownDesignTokens.Elevation.md,
                RoundedCornerShape(TownDesignTokens.Radius.xl)
            )
            .clip(RoundedCornerShape(TownDesignTokens.Radius.xl))
            .background(TownDesignTokens.Colors.bgSurface)
            .border(
                1.dp,
                TownDesignTokens.Colors.border,
                RoundedCornerShape(TownDesignTokens.Radius.xl)
            )
    ) {
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
        Column(modifier = Modifier.padding(TownDesignTokens.Spacing.xl)) {
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
            .padding(
                horizontal = TownDesignTokens.Spacing.lg,
                vertical = TownDesignTokens.Spacing.md
            )
            .semantics {
                this.contentDescription = contentDescription
                role = Role.Button
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = TownDesignTokens.Colors.textPrimary,
                fontWeight = FontWeight.Medium
            )
            if (description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TownDesignTokens.Colors.textSecondary
                )
            }
        }
        if (trailing != null) trailing()
        else Text(
            "→",
            color = TownDesignTokens.Colors.textTertiary,
            fontSize = 16.sp
        )
    }
}

/**
 * 标签组件
 */
@Composable
fun TagChip(text: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(TownDesignTokens.Radius.sm))
            .background(color.copy(alpha = 0.1f))
            .padding(
                horizontal = TownDesignTokens.Spacing.sm,
                vertical = TownDesignTokens.Spacing.xs
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
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
        modifier = modifier
            .semantics {
                this.contentDescription = contentDescription
                role = Role.Button
            }
            .pressScale(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = TownDesignTokens.Colors.primary,
            contentColor = Color.White,
            disabledContainerColor = TownDesignTokens.Colors.primary.copy(alpha = 0.4f),
            disabledContentColor = Color.White.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(TownDesignTokens.Radius.md),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = TownDesignTokens.Elevation.md,
            pressedElevation = TownDesignTokens.Elevation.lg,
            disabledElevation = TownDesignTokens.Elevation.none,
            hoveredElevation = TownDesignTokens.Elevation.md + 2.dp,
            focusedElevation = TownDesignTokens.Elevation.md + 2.dp
        )
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
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
        modifier = modifier
            .semantics {
                this.contentDescription = contentDescription
                role = Role.Button
            }
            .pressScale(),
        shape = RoundedCornerShape(TownDesignTokens.Radius.md),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = TownDesignTokens.Colors.primary,
            disabledContentColor = TownDesignTokens.Colors.textTertiary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = TownDesignTokens.Elevation.none,
            pressedElevation = TownDesignTokens.Elevation.sm,
            disabledElevation = TownDesignTokens.Elevation.none,
            hoveredElevation = TownDesignTokens.Elevation.sm,
            focusedElevation = TownDesignTokens.Elevation.sm
        ),
        border = BorderStroke(1.5.dp, TownDesignTokens.Colors.primary)
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
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
            .pressScale()
            .semantics {
                this.contentDescription = contentDescription
                role = Role.Button
            },
        shape = RoundedCornerShape(TownDesignTokens.Radius.xl),
        colors = CardDefaults.cardColors(
            containerColor = if (isLocked) TownDesignTokens.Colors.bgSurfaceVariant else TownDesignTokens.Colors.bgSurface,
            contentColor = TownDesignTokens.Colors.textPrimary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = TownDesignTokens.Elevation.md,
            pressedElevation = TownDesignTokens.Elevation.lg,
            hoveredElevation = TownDesignTokens.Elevation.md + 2.dp,
            focusedElevation = TownDesignTokens.Elevation.md + 2.dp
        ),
        border = BorderStroke(1.dp, TownDesignTokens.Colors.border)
    ) {
        Column(
            modifier = Modifier.padding(TownDesignTokens.Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(TownDesignTokens.Spacing.sm))
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = if (isLocked) TownDesignTokens.Colors.textSecondary else TownDesignTokens.Colors.textPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isLocked) "即将开放" else desc,
                style = MaterialTheme.typography.bodySmall,
                color = TownDesignTokens.Colors.textSecondary
            )
            if (!isLocked) {
                Spacer(modifier = Modifier.height(TownDesignTokens.Spacing.sm))
                Text(
                    text = "$count 件",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ============================================
// 通用组件库 END
// ============================================
