package com.example.townapp.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.theme.*

@Composable
fun BottomActionBar(
    onEat: () -> Unit,
    onRest: () -> Unit,
    onGoOut: () -> Unit,
    onWork: () -> Unit,
    onStudy: () -> Unit,
    lockHint: String = "",
    isEatAuto: Boolean = false
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, TownDesignTokens.Colors.border)),
        color = TownDesignTokens.Colors.bgSurface,
        shadowElevation = 0.dp
    ) {
        Column {
            if (lockHint.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TownDesignTokens.Colors.bgCard)
                        .border(BorderStroke(1.dp, TownDesignTokens.Colors.borderLight))
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = lockHint,
                        fontSize = 11.sp,
                        color = TownDesignTokens.Colors.textTertiary
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppDimens.paddingMedium, vertical = AppDimens.paddingMedium),
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
            ) {
                BottomActionButton(
                    emoji = if (isEatAuto) "🤖" else "🍜",
                    label = if (isEatAuto) "自动" else "吃饭",
                    onClick = onEat,
                    enabled = !isEatAuto
                )
                BottomActionButton(
                    emoji = "🚶",
                    label = "出门",
                    onClick = onGoOut
                )
                BottomActionButton(
                    emoji = "😴",
                    label = "休息",
                    onClick = onRest
                )
                BottomActionButton(
                    emoji = "💼",
                    label = "工作",
                    onClick = onWork
                )
                BottomActionButton(
                    emoji = "📚",
                    label = "学习",
                    onClick = onStudy
                )
            }
        }
    }
}
