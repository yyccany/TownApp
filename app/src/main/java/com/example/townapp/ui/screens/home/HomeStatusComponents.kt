package com.example.townapp.ui.screens.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.feature.town_simulation.GentleTextProvider
import com.example.townapp.data.database.entity.UserBodyState
import com.example.townapp.data.database.entity.UserSpaceState
import com.example.townapp.ui.theme.*
import com.example.townapp.ui.animation.*
import com.example.townapp.ui.modifier.*
import com.example.townapp.ui.viewmodel.AutoLifeTier

@Composable
fun StatusBar(
    label: String,
    value: Int,
    suffix: String,
    color: Color
) {
    val animatedProgress by animateFloatAsState(
        targetValue = value.coerceIn(0, 100) / 100f,
        animationSpec = tween(durationMillis = 800),
        label = "progress"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = HomeBrandColors.textMuted,
            modifier = Modifier.width(32.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(color.copy(alpha = 0.15f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .clip(RoundedCornerShape(3.dp))
                    .background(color)
            )
        }

        Spacer(modifier = Modifier.width(AppDimens.paddingSmall))

        Text(
            text = suffix,
            fontSize = 11.sp,
            color = HomeBrandColors.textSecondary,
            modifier = Modifier.width(56.dp)
        )
    }
}

@Composable
fun StatusBarSection(
    body: UserBodyState,
    space: UserSpaceState,
    currentSpeed: Double,
    autoLifeEnabled: Boolean,
    autoLifeTier: AutoLifeTier,
    onSpeedChange: (Double) -> Unit,
    onAutoLifeToggle: () -> Unit,
    onTierChange: (AutoLifeTier) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(HomeBrandColors.surface)
            .padding(horizontal = AppDimens.paddingXLarge, vertical = AppDimens.paddingMedium)
    ) {
        StatusBar(
            label = "饱腹",
            value = body.satiety,
            suffix = GentleTextProvider.satietyLabel(body.satiety),
            color = HomeStatusColors.progress
        )
        Spacer(modifier = Modifier.height(4.dp))
        StatusBar(
            label = "精力",
            value = body.energy,
            suffix = GentleTextProvider.energyLabel(body.energy),
            color = HomeStatusColors.progress
        )
        Spacer(modifier = Modifier.height(4.dp))
        StatusBar(
            label = "情绪",
            value = body.mood,
            suffix = GentleTextProvider.moodLabel(body.mood),
            color = HomeStatusColors.progress
        )

        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "存款",
                fontSize = 12.sp,
                color = HomeBrandColors.textMuted
            )
            Text(
                text = "${String.format("%.0f", space.currentSavings)}元",
                fontSize = 12.sp,
                color = HomeBrandColors.textSecondary
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "自动生活",
                fontSize = 12.sp,
                color = HomeBrandColors.textMuted
            )
            Switch(
                checked = autoLifeEnabled,
                onCheckedChange = { onAutoLifeToggle() },
                modifier = Modifier.scale(0.8f)
            )
        }

        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "生活标准",
                fontSize = 12.sp,
                color = HomeBrandColors.textMuted
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AutoLifeTier.entries.forEach { tier ->
                    SpeedButton(
                        label = tier.label,
                        isSelected = autoLifeTier == tier,
                        onClick = { onTierChange(tier) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "流速",
                fontSize = 12.sp,
                color = HomeBrandColors.textMuted
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                SpeedButton(
                    label = "1x",
                    isSelected = currentSpeed == 1.0,
                    onClick = { onSpeedChange(1.0) }
                )
                SpeedButton(
                    label = "2x",
                    isSelected = currentSpeed == 2.0,
                    onClick = { onSpeedChange(2.0) }
                )
                SpeedButton(
                    label = "4x",
                    isSelected = currentSpeed == 4.0,
                    onClick = { onSpeedChange(4.0) }
                )
            }
        }
    }
}
