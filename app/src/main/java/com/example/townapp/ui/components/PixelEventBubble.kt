package com.example.townapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.business.EventEngine.LifeEvent

@Composable
fun PixelEventBubble(
    event: LifeEvent?,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = event != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        event?.let {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E7)),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = getEventIcon(event.id),
                            fontSize = 48.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = event.title,
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Monospace,
                            color = Color(0xFF4A3728)
                        )
                        Text(
                            text = event.description,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Monospace,
                            color = Color(0xFF6B5B4F),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = "点击关闭",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            color = Color(0xFF8B7B6F),
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .clickable { onDismiss() }
                        )
                    }
                }
            }
        }
    }
}

private fun getEventIcon(eventId: String): String {
    return when (eventId) {
        "mosquito_bite" -> "🦟"
        "scratch" -> "🤕"
        "toy_part_lost" -> "🧩"
        "doll_shedding" -> "🧸"
        "pet_scratch" -> "🐱"
        "find_object" -> "✨"
        "bead_moisture" -> "📿"
        "food_spoil" -> "🍞"
        "clothes_stain" -> "👕"
        "stationery_lost" -> "✏️"
        else -> "💫"
    }
}