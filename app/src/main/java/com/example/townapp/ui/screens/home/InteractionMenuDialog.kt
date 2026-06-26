package com.example.townapp.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.townapp.ui.theme.TownDesignTokens

@Composable
fun InteractionMenuDialog(
    title: String,
    options: List<InteractionOption>,
    currentHour: Int,
    onOptionSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val isNight = currentHour !in 7..18

    val bgColor = if (isNight) TownDesignTokens.Colors.nightMid else TownDesignTokens.Colors.bgSurface
    val borderColor = if (isNight) TownDesignTokens.Colors.nightBorder else TownDesignTokens.Colors.border
    val titleColor = if (isNight) TownDesignTokens.Colors.nightText else TownDesignTokens.Colors.textPrimary
    val textColor = if (isNight) TownDesignTokens.Colors.nightText else TownDesignTokens.Colors.textPrimary
    val dismissBgColor = if (isNight) TownDesignTokens.Colors.nightLight else TownDesignTokens.Colors.bgSurfaceVariant
    val dismissTextColor = if (isNight) TownDesignTokens.Colors.nightTextSecondary else TownDesignTokens.Colors.textTertiary

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, borderColor),
            colors = CardDefaults.cardColors(
                containerColor = bgColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = titleColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                options.forEach { option ->
                    OptionButton(
                        label = option.label,
                        tendency = option.consumptionTendency,
                        isNight = isNight,
                        onClick = { onOptionSelected(option.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(2.dp))
                        .background(dismissBgColor)
                        .clickable { onDismiss() }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "离开",
                        fontSize = 14.sp,
                        color = dismissTextColor
                    )
                }
            }
        }
    }
}

@Composable
private fun OptionButton(
    label: String,
    tendency: Int?,
    isNight: Boolean,
    onClick: () -> Unit
) {
    val bgColor = when {
        tendency != null && tendency >= 60 -> if (isNight) Color(0xFF1E3326) else TownDesignTokens.Colors.successBg
        tendency != null && tendency <= 40 -> if (isNight) Color(0xFF33291A) else TownDesignTokens.Colors.warningBg
        else -> if (isNight) TownDesignTokens.Colors.nightLight else TownDesignTokens.Colors.bgSurfaceVariant
    }
    val textColor = when {
        tendency != null && tendency >= 60 -> if (isNight) Color(0xFF8FBC8F) else TownDesignTokens.Colors.accent
        tendency != null && tendency <= 40 -> if (isNight) Color(0xFFDDB97E) else TownDesignTokens.Colors.accentSecondary
        else -> if (isNight) TownDesignTokens.Colors.nightText else TownDesignTokens.Colors.textPrimary
    }
    val borderColor = when {
        tendency != null && tendency >= 60 -> if (isNight) Color(0xFF3D5A44) else TownDesignTokens.Colors.accent.copy(alpha = 0.3f)
        tendency != null && tendency <= 40 -> if (isNight) Color(0xFF5A4A2F) else TownDesignTokens.Colors.accentSecondary.copy(alpha = 0.3f)
        else -> if (isNight) TownDesignTokens.Colors.nightBorder else TownDesignTokens.Colors.borderLight
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp))
            .background(bgColor)
            .border(
                BorderStroke(1.dp, borderColor),
                RoundedCornerShape(2.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = textColor,
                textAlign = TextAlign.Center
            )
            if (tendency != null) {
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = when {
                        tendency >= 60 -> "🌿"
                        tendency <= 40 -> "✨"
                        else -> ""
                    },
                    fontSize = 12.sp
                )
            }
        }
    }
}
