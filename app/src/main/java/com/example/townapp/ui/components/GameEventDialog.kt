package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.townapp.data.gameevent.EventChoice
import com.example.townapp.data.gameevent.EventType
import com.example.townapp.data.gameevent.GameEvent

@Composable
fun GameEventDialog(
    event: GameEvent,
    onChoice: (EventChoice) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFFFF9EC)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(getEventColor(event.type), RoundedCornerShape(10.dp)),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(getEventIcon(event.type), fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = getEventTypeLabel(event.type),
                            fontSize = 12.sp,
                            color = Color(0xFF888888)
                        )
                        Text(
                            text = event.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A3728)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = event.description,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "你的选择",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A3728)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    event.choices.forEach { choice ->
                        EventChoiceButton(
                            choice = choice,
                            onClick = { onChoice(choice); onDismiss() }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF0F0F0),
                        contentColor = Color(0xFF666666)
                    )
                ) {
                    Text("稍后再处理", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun EventChoiceButton(
    choice: EventChoice,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF4A3728)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.Start
        ) {
            Text(
                text = choice.text,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = choice.consequence,
                fontSize = 12.sp,
                color = Color(0xFF888888),
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (choice.moodChange != 0) {
                    Text(
                        text = if (choice.moodChange > 0) "😊 +${choice.moodChange}" else "😔 ${choice.moodChange}",
                        fontSize = 11.sp,
                        color = if (choice.moodChange > 0) Color(0xFF4CAF50) else Color(0xFFE57373)
                    )
                }
                if (choice.energyChange != 0) {
                    Text(
                        text = if (choice.energyChange > 0) "⚡ +${choice.energyChange}" else "😴 ${choice.energyChange}",
                        fontSize = 11.sp,
                        color = if (choice.energyChange > 0) Color(0xFF4CAF50) else Color(0xFFE57373)
                    )
                }
                if (choice.moneyChange != 0) {
                    Text(
                        text = if (choice.moneyChange > 0) "💰 +${choice.moneyChange}" else "💸 ${choice.moneyChange}",
                        fontSize = 11.sp,
                        color = if (choice.moneyChange > 0) Color(0xFF4CAF50) else Color(0xFFE57373)
                    )
                }
            }
        }
    }
}

private fun getEventColor(type: EventType): Color {
    return when (type) {
        EventType.WORKPLACE -> Color(0xFFE3F2FD)
        EventType.ECONOMIC_DISPUTE -> Color(0xFFFFF3E0)
        EventType.SOCIAL_CONFLICT -> Color(0xFFF3E5F5)
        EventType.POLICY_CHANGE -> Color(0xFFE8F5E9)
    }
}

private fun getEventIcon(type: EventType): String {
    return when (type) {
        EventType.WORKPLACE -> "💼"
        EventType.ECONOMIC_DISPUTE -> "💰"
        EventType.SOCIAL_CONFLICT -> "👥"
        EventType.POLICY_CHANGE -> "🏛️"
    }
}

private fun getEventTypeLabel(type: EventType): String {
    return when (type) {
        EventType.WORKPLACE -> "职场事件"
        EventType.ECONOMIC_DISPUTE -> "经济纠纷"
        EventType.SOCIAL_CONFLICT -> "人际冲突"
        EventType.POLICY_CHANGE -> "政策变动"
    }
}