package com.example.townapp.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.database.entity.UserBodyState
import com.example.townapp.data.database.entity.UserSpaceState
import com.example.townapp.feature.town_simulation.GentleTextProvider
import com.example.townapp.ui.theme.*
import com.example.townapp.ui.animation.*
import com.example.townapp.ui.modifier.*
import com.example.townapp.ui.viewmodel.AutoLifeTier

internal object HomeBrandColors {
    val background get() = TownDesignTokens.Colors.bgBase
    val surface get() = TownDesignTokens.Colors.bgSurface
    val cardBackground get() = TownDesignTokens.Colors.bgSurface
    val textPrimary get() = TownDesignTokens.Colors.textPrimary
    val textSecondary get() = TownDesignTokens.Colors.textSecondary
    val textMuted get() = TownDesignTokens.Colors.textTertiary
    val divider get() = TownDesignTokens.Colors.divider
    val primary get() = TownDesignTokens.Colors.primary
    val health get() = TownDesignTokens.Colors.accent
    val warning get() = TownDesignTokens.Colors.accentSecondary
}

internal object HomeStatusColors {
    val progress get() = TownDesignTokens.Colors.statMoney
}

@Composable
fun TopTimeBar(gameDay: Int, gameHour: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TownDesignTokens.Colors.bgSurface)
            .border(BorderStroke(1.dp, TownDesignTokens.Colors.border))
            .padding(horizontal = AppDimens.paddingXLarge, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "第${gameDay}天",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = TownDesignTokens.Colors.textPrimary
        )
        Text(
            text = "${GentleTextProvider.timeEmoji(gameHour)} ${String.format("%02d", gameHour)}:00",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = TownDesignTokens.Colors.textPrimary
        )
    }
}

@Composable
fun FloatingStatusButton(
    body: UserBodyState,
    space: UserSpaceState,
    isExpanded: Boolean,
    currentSpeed: Double,
    autoLifeEnabled: Boolean,
    autoLifeTier: AutoLifeTier,
    onToggle: () -> Unit,
    onSpeedChange: (Double) -> Unit,
    onAutoLifeToggle: () -> Unit,
    onTierChange: (AutoLifeTier) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(horizontal = AppDimens.paddingLarge, vertical = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .background(TownDesignTokens.Colors.bgSurface)
                    .border(BorderStroke(1.dp, TownDesignTokens.Colors.border), RoundedCornerShape(2.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${body.energy}%",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = TownDesignTokens.Colors.textPrimary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isExpanded) "▼" else "▲",
                    fontSize = 9.sp,
                    color = TownDesignTokens.Colors.textTertiary
                )
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = cardExpandEnter,
            exit = cardCollapseExit
        ) {
            StatusBarSection(
                body = body,
                space = space,
                currentSpeed = currentSpeed,
                autoLifeEnabled = autoLifeEnabled,
                autoLifeTier = autoLifeTier,
                onSpeedChange = onSpeedChange,
                onAutoLifeToggle = onAutoLifeToggle,
                onTierChange = onTierChange
            )
        }
    }
}

@Composable
fun SpeedButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .background(
                if (isSelected) TownDesignTokens.Colors.primary
                else TownDesignTokens.Colors.bgSurface
            )
            .border(
                BorderStroke(1.dp, if (isSelected) TownDesignTokens.Colors.primary else TownDesignTokens.Colors.border),
                RoundedCornerShape(2.dp)
            )
            .clickable(onClick = onClick)
            .pressScale(pressedScale = 0.94f)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            color = if (isSelected) Color.White else TownDesignTokens.Colors.textSecondary
        )
    }
}

@Composable
fun ShoppingTag(name: String, isPremium: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(
                if (isPremium) HomeBrandColors.warning.copy(alpha = 0.12f)
                else HomeBrandColors.surface
            )
            .padding(horizontal = AppDimens.paddingSmall, vertical = 4.dp)
    ) {
        Text(
            text = name,
            fontSize = 11.sp,
            color = if (isPremium) HomeBrandColors.warning else HomeBrandColors.textSecondary
        )
    }
}

@Composable
fun EmptyStateHint(gameDay: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = AppDimens.paddingXXLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isFirstDay = gameDay <= 1
        Text(
            text = if (isFirstDay) "🌱" else "🍃",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
        Text(
            text = if (isFirstDay) "小镇刚刚开始运转" else "今天很平静，没什么特别的事",
            fontSize = 13.sp,
            color = HomeBrandColors.textMuted
        )
        Text(
            text = if (isFirstDay) "你可以点下面的按钮，或者什么也不做" else "平静本身也是一种状态",
            fontSize = 12.sp,
            color = HomeBrandColors.textMuted.copy(alpha = 0.7f)
        )
        Text(
            text = if (isFirstDay) "它自己也会好好运转的" else "不用每天都有故事发生",
            fontSize = 12.sp,
            color = HomeBrandColors.textMuted.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun BottomActionButton(
    emoji: String,
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .background(if (enabled) TownDesignTokens.Colors.bgSurface else TownDesignTokens.Colors.bgSurface.copy(alpha = 0.5f))
            .border(
                BorderStroke(1.dp, if (enabled) TownDesignTokens.Colors.border else TownDesignTokens.Colors.border.copy(alpha = 0.3f)),
                RoundedCornerShape(2.dp)
            )
            .clickable(enabled = enabled, onClick = onClick)
            .pressScale(pressedScale = if (enabled) 0.94f else 1f)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = emoji,
            fontSize = 20.sp,
            color = if (enabled) Color.Unspecified else TownDesignTokens.Colors.textTertiary.copy(alpha = 0.4f)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = if (enabled) TownDesignTokens.Colors.textSecondary else TownDesignTokens.Colors.textTertiary.copy(alpha = 0.4f)
        )
    }
}

@Composable
fun FloatingWeekEventsButton(
    eventCount: Int,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onToggle)
            .padding(horizontal = AppDimens.paddingLarge, vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(2.dp))
                .background(TownDesignTokens.Colors.bgSurface)
                .border(BorderStroke(1.dp, TownDesignTokens.Colors.border), RoundedCornerShape(2.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📋",
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "本周${eventCount}件事",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TownDesignTokens.Colors.textPrimary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (isExpanded) "▼" else "▲",
                fontSize = 9.sp,
                color = TownDesignTokens.Colors.textTertiary
            )
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = HomeBrandColors.textMuted
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = HomeBrandColors.textPrimary
        )
    }
}

@Composable
fun QuickActionButton(emoji: String, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = emoji, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 11.sp, color = HomeBrandColors.textSecondary)
    }
}

@Composable
fun PlanInput(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = HomeBrandColors.textMuted,
            modifier = Modifier.width(100.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(fontSize = 14.sp, color = HomeBrandColors.textPrimary),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = HomeBrandColors.cardBackground,
                unfocusedContainerColor = HomeBrandColors.cardBackground,
                focusedIndicatorColor = HomeBrandColors.primary,
                unfocusedIndicatorColor = HomeBrandColors.divider
            )
        )
    }
}
