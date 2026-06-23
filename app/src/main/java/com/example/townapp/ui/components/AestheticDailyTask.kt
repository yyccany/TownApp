package com.example.townapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun AestheticDailyTask() {
    val task = remember { AestheticBusiness.getDailyTask() }
    var isCompleted by remember { mutableStateOf(false) }
    var showReward by remember { mutableStateOf(false) }

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
                        text = "今日审美任务",
                        fontSize = TownAestheticDesign.Typography.Size.lg,
                        fontWeight = FontWeight.Bold,
                        color = TownAestheticDesign.ColorPalette.primary
                    )
                }
                Badge(
                    containerColor = TownAestheticDesign.ColorPalette.accent,
                    contentColor = Color.White
                ) {
                    Text(text = getTaskTypeLabel(task.id))
                }
            }
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
            
            TaskCard(task, isCompleted)
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
            
            if (!isCompleted) {
                Button(
                    onClick = {
                        isCompleted = true
                        showReward = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TownAestheticDesign.ColorPalette.success,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.base)
                ) {
                    Text(
                        text = "完成任务",
                        fontSize = TownAestheticDesign.Typography.Size.base,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = TownAestheticDesign.ColorPalette.success.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.sm)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(TownAestheticDesign.Spacing.md),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "\u2705 任务已完成",
                            fontSize = TownAestheticDesign.Typography.Size.base,
                            fontWeight = FontWeight.SemiBold,
                            color = TownAestheticDesign.ColorPalette.success
                        )
                    }
                }
            }
            
            if (showReward) {
                Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
                RewardCard(task.rewardExp, task.rewardAwakening)
            }
        }
    }
}

@Composable
fun TaskCard(task: DailyAestheticTask, isCompleted: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted)
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
                    text = getTaskIcon(task.id),
                    fontSize = TownAestheticDesign.Typography.Size.`3xl`
                )
                Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.md))
                Column {
                    Text(
                        text = task.title,
                        fontSize = TownAestheticDesign.Typography.Size.lg,
                        fontWeight = FontWeight.SemiBold,
                        color = TownAestheticDesign.ColorPalette.textPrimary
                    )
                    Text(
                        text = task.description,
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
                RewardTag("经验", task.rewardExp.toString(), TownAestheticDesign.ColorPalette.accent)
                RewardTag("觉醒值", task.rewardAwakening.toString(), TownAestheticDesign.ColorPalette.gold)
                RewardTag("耗时", getTaskEstimatedMinutes(task.id).toString() + "分钟", TownAestheticDesign.ColorPalette.textTertiary)
            }
        }
    }
}

@Composable
fun RewardTag(label: String, value: String, color: Color) {
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

@Composable
fun RewardCard(experience: Int, awakening: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = TownAestheticDesign.ColorPalette.gold.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.base),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.sm)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TownAestheticDesign.Spacing.md),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "\uD83C\uDF89",
                fontSize = TownAestheticDesign.Typography.Size.`2xl`
            )
            Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.sm))
            Text(
                text = "获得奖励：",
                fontSize = TownAestheticDesign.Typography.Size.base,
                fontWeight = FontWeight.SemiBold,
                color = TownAestheticDesign.ColorPalette.textPrimary
            )
            Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.sm))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "经验 +$experience",
                    fontSize = TownAestheticDesign.Typography.Size.base,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.accent
                )
                Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.sm))
                Text(
                    text = "觉醒值 +$awakening",
                    fontSize = TownAestheticDesign.Typography.Size.base,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.gold
                )
            }
        }
    }
}

private fun getTaskIcon(taskId: String): String = when (taskId) {
    "delete" -> "\uD83D\uDDD1\uFE0F"
    "observe" -> "\uD83D\uDC41\u200D\uD83D\uDDE8\uFE0F"
    "compare" -> "\u2696\uFE0F"
    "learn" -> "\uD83D\uDCDA"
    else -> "\uD83D\uDCCB"
}

private fun getTaskTypeLabel(taskId: String): String = when (taskId) {
    "delete" -> "清理"
    "observe" -> "观察"
    "compare" -> "对比"
    "learn" -> "学习"
    else -> "任务"
}

private fun getTaskEstimatedMinutes(taskId: String): Int = when (taskId) {
    "delete" -> 5
    "observe" -> 10
    "compare" -> 15
    "learn" -> 10
    else -> 10
}