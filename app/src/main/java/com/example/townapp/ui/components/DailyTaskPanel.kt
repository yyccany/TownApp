package com.example.townapp.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.model.DailyTask

/**
 * 每日任务面板。
 *
 * 折叠式设计，默认收起，点击标题栏展开。
 * 完全中立：只显示任务进展，不评判行为好坏。
 */
@Composable
fun DailyTaskPanel(
    tasks: List<DailyTask>,
    onTogglePanel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isExpanded = remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // 标题行（点击展开/收起）
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .let { if (isExpanded.value) it else it },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📋 今日任务",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${tasks.count { it.isCompleted }}/${tasks.size}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { isExpanded.value = !isExpanded.value }) {
                    Text(
                        text = if (isExpanded.value) "收起 ▲" else "展开 ▼",
                        fontSize = 12.sp
                    )
                }
            }

            // 任务列表（可折叠动画）
            AnimatedVisibility(
                visible = isExpanded.value,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    tasks.forEach { task ->
                        TaskRow(task = task)
                    }

                    // 全部完成提示
                    if (tasks.isNotEmpty() && tasks.all { it.isCompleted }) {
                        Text(
                            text = "✅ 今日任务全部完成！",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskRow(task: DailyTask) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 完成状态图标
        Text(
            text = if (task.isCompleted) "✅" else "  ",
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.name,
                    fontSize = 13.sp,
                    fontWeight = if (task.isCompleted) FontWeight.Normal else FontWeight.Medium,
                    color = if (task.isCompleted)
                        MaterialTheme.colorScheme.onSurfaceVariant
                    else
                        MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${task.currentCount}/${task.targetCount}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { task.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp),
                color = if (task.isCompleted)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }

    Spacer(modifier = Modifier.width(4.dp))
}