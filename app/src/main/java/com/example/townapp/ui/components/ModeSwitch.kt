package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.GameMode

/**
 * 游戏模式切换开关
 *
 * 自动托管 → 系统根据角色参数自动运行
 * 精细手动 → 玩家逐项规划三餐、穿搭、晚间行为
 */
@Composable
fun ModeSwitch(
    currentMode: GameMode,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 自动模式按钮
            FilterChip(
                selected = currentMode == GameMode.AUTO,
                onClick = { if (currentMode != GameMode.AUTO) onToggle() },
                label = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("自动托管", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        Text("系统代劳", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(4.dp))

            // 手动模式按钮
            FilterChip(
                selected = currentMode == GameMode.MANUAL,
                onClick = { if (currentMode != GameMode.MANUAL) onToggle() },
                label = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("精细手动", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        Text("亲手规划", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}