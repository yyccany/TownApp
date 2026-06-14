package com.example.townapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.PlayerEventSystem

/**
 * 玩家主动触发事件面板
 *
 * 玩家可以主动规划短期变故：主动辞职、自费体检、外出短途旅行等。
 * 选定后时序系统立刻结算对应的参数变化。
 */
@Composable
fun PlayerEventPanel(
    onEventTrigger: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("主动规划事件", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("选择你想主动触发的事件，立刻结算效果", fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant)

        PlayerEventSystem.PlayerEventType.entries.forEach { eventType ->
            val typeEvents = PlayerEventSystem.allPlayerEvents.filter { it.type == eventType }
            if (typeEvents.isNotEmpty()) {
                Text(
                    eventType.label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )

                typeEvents.forEach { event ->
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(event.name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                Text(event.description, fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 14.sp, maxLines = 2)
                                if (event.cost > 0) {
                                    Text("花费 ${event.cost}元", fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.outline)
                                }
                            }
                            Button(
                                onClick = { onEventTrigger(event.id) },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("触发", fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}