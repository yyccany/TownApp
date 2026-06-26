package com.example.townapp.ui.screens.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun BirthIdentitySelector(
    currentHour: Int,
    onIdentitySelected: (BirthIdentity) -> Unit
) {
    val isNight = currentHour !in 7..18
    val bgColor = if (isNight) Color(0xFF2A2D3A) else Color(0xFFFAF8F5)
    val titleColor = if (isNight) Color(0xFFE8E4DE) else Color(0xFF5D4037)
    val textColor = if (isNight) Color(0xFFD0CCC4) else Color(0xFF5D4037)
    val descColor = if (isNight) Color(0xFF9A9690) else Color(0xFF8D7B6F)

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = bgColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "欢迎来到小镇",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleColor,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "选择你的出身，开启不一样的小镇生活",
                    fontSize = 13.sp,
                    color = descColor,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))

                BirthIdentity.values().forEach { identity ->
                    IdentityCard(
                        identity = identity,
                        isNight = isNight,
                        textColor = textColor,
                        descColor = descColor,
                        onClick = { onIdentitySelected(identity) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun IdentityCard(
    identity: BirthIdentity,
    isNight: Boolean,
    textColor: Color,
    descColor: Color,
    onClick: () -> Unit
) {
    val cardBg = if (isNight) Color(0xFF353845) else Color(0xFFF5F0EB)
    val borderColor = identity.playerColor.copy(alpha = 0.6f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(cardBg)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(identity.playerColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (identity) {
                        BirthIdentity.OFFICE_WORKER -> "💼"
                        BirthIdentity.MEDIA_FREELANCER -> "📱"
                        BirthIdentity.FRESH_GRADUATE -> "🎓"
                    },
                    fontSize = 22.sp
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = identity.displayName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = identity.description,
                    fontSize = 12.sp,
                    color = descColor,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
