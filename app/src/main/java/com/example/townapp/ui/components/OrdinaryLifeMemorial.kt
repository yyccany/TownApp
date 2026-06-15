package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrdinaryLifeMemorial(
    mealCount: Int,
    instantNoodleCount: Int,
    goodSleepDays: Int,
    lateNightCount: Int,
    selfCareActions: Int,
    placesLived: Int,
    moneyDelta: Double,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val cardModifier = if (onClick != null) {
        modifier.clickable(onClick = onClick)
    } else {
        modifier
    }
    
    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E7)),
        elevation = if (onClick != null) CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation) else CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🏠 平凡生活纪念馆",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B6914)
                )
                if (onClick != null) {
                    Text(
                        text = "→",
                        fontSize = 18.sp,
                        color = Color(0xFFA0826D)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem("🍜 吃饭次数", mealCount.toString())
                StatItem("🥡 泡面次数", instantNoodleCount.toString())
                StatItem("😴 好觉天数", goodSleepDays.toString())
            }
            
            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem("🌙 熬夜次数", lateNightCount.toString())
                StatItem("💆 自我关怀", selfCareActions.toString())
                StatItem("🏠 居住地点", placesLived.toString())
            }
            
            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "💰 余额变化",
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (moneyDelta >= 0) "↑ ¥${String.format("%.2f", moneyDelta)}" else "↓ ¥${String.format("%.2f", -moneyDelta)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (moneyDelta >= 0) Color(0xFF228B22) else Color(0xFFDC143C)
                )
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B6914)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF666666)
        )
    }
}