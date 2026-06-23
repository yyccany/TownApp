package com.example.townapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.townapp.feature.town_simulation.AestheticBusiness
import com.example.townapp.feature.town_simulation.AestheticBusiness.DailyAestheticTask
import com.example.townapp.ui.design.TownAestheticDesign

@Composable
fun AestheticInspiration() {
    val inspiration = remember { AestheticBusiness.getDailyTask() }
    var isAcknowledged by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(TownAestheticDesign.Spacing.md),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.md),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.lg)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.lg)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "\uD83D\uDCCB",
                        fontSize = TownAestheticDesign.Typography.Size.`2xl`
                    )
                    Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.base))
                    Text(
                        text = "今日审美灵感",
                        fontSize = TownAestheticDesign.Typography.Size.lg,
                        fontWeight = FontWeight.Bold,
                        color = TownAestheticDesign.ColorPalette.primary
                    )
                }
                Text(
                    text = getInspirationTypeLabel(inspiration.id),
                    fontSize = TownAestheticDesign.Typography.Size.sm,
                    color = TownAestheticDesign.ColorPalette.accent,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
            
            InspirationCard(inspiration, isAcknowledged)
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
            
            if (!isAcknowledged) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "这只是一个建议，不是必须完成的任务。",
                        fontSize = TownAestheticDesign.Typography.Size.sm,
                        color = TownAestheticDesign.ColorPalette.textTertiary
                    )
                }
            }
        }
    }
}

@Composable
fun InspirationCard(inspiration: DailyAestheticTask, isAcknowledged: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isAcknowledged)
                TownAestheticDesign.ColorPalette.success.copy(alpha = 0.05f)
            else
                TownAestheticDesign.ColorPalette.surface
        ),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.md),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.sm)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.md)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getInspirationIcon(inspiration.id),
                    fontSize = TownAestheticDesign.Typography.Size.`3xl`
                )
                Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.md))
                Column {
                    Text(
                        text = inspiration.title,
                        fontSize = TownAestheticDesign.Typography.Size.lg,
                        fontWeight = FontWeight.SemiBold,
                        color = TownAestheticDesign.ColorPalette.textPrimary
                    )
                    Text(
                        text = inspiration.description,
                        fontSize = TownAestheticDesign.Typography.Size.sm,
                        color = TownAestheticDesign.ColorPalette.textSecondary,
                        modifier = Modifier.padding(top = TownAestheticDesign.Spacing.xs)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.base))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoTag("预计耗时", getInspirationEstimatedMinutes(inspiration.id).toString() + "分钟", TownAestheticDesign.ColorPalette.textTertiary)
            }
        }
    }
}

@Composable
fun InfoTag(label: String, value: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = TownAestheticDesign.Typography.Size.xs,
            color = TownAestheticDesign.ColorPalette.textTertiary
        )
        Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.xs))
        Text(
            text = value,
            fontSize = TownAestheticDesign.Typography.Size.sm,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

private fun getInspirationIcon(taskId: String): String = when (taskId) {
    "delete" -> "\uD83D\uDDD1\uFE0F"
    "observe" -> "\uD83D\uDC41\u200D\uD83D\uDDE8\uFE0F"
    "compare" -> "\u2696\uFE0F"
    "learn" -> "\uD83D\uDCDA"
    else -> "\uD83D\uDCCB"
}

private fun getInspirationTypeLabel(taskId: String): String = when (taskId) {
    "delete" -> "清理"
    "observe" -> "观察"
    "compare" -> "对比"
    "learn" -> "学习"
    else -> "灵感"
}

private fun getInspirationEstimatedMinutes(taskId: String): Int = when (taskId) {
    "delete" -> 5
    "observe" -> 10
    "compare" -> 15
    "learn" -> 10
    else -> 10
}