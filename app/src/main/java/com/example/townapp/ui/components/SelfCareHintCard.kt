package com.example.townapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelfCareHintCard(
    healthScore: Int,
    moodScore: Int,
    energy: Int,
    bloodSugar: Double
) {
    val (hint, emoji, color) = getSelfCareHint(healthScore, moodScore, energy, bloodSugar)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = emoji,
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = hint,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

private fun getSelfCareHint(
    healthScore: Int,
    moodScore: Int,
    energy: Int,
    bloodSugar: Double
): Triple<String, String, Color> {
    return when {
        healthScore < 30 -> Triple(
            "身体状态不太好，记得好好休息和吃饭哦~",
            "💤",
            Color(0xFFE53935)
        )
        moodScore < 30 -> Triple(
            "心情有点低落，做些让自己开心的事情吧~",
            "🌈",
            Color(0xFFF59E0B)
        )
        energy < 20 -> Triple(
            "精力不足，需要补充能量和休息~",
            "⚡",
            Color(0xFFEAB308)
        )
        bloodSugar > 7.8 -> Triple(
            "血糖有点偏高，注意饮食哦~",
            "🍎",
            Color(0xFFF97316)
        )
        healthScore < 50 -> Triple(
            "身体需要关爱，记得照顾好自己~",
            "❤️",
            Color(0xFFEC4899)
        )
        else -> Triple(
            "今天也辛苦了，给自己一个温柔的拥抱~",
            "🤗",
            Color(0xFF10B981)
        )
    }
}
