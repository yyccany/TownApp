package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.WorkdayPlanner

/**
 * 工作日晚间规划面板
 *
 * 开放空余时段自定义选择：成长类、休闲类、社交类、生活琐事、极端抉择。
 */
@Composable
fun WorkdayPanel(
    currentPlan: WorkdayPlanner.EveningPlan?,
    onActivitiesSelected: (List<WorkdayPlanner.Activity>) -> Unit,
    modifier: Modifier = Modifier,
    enableInternalScroll: Boolean = true
) {
    // 本地选择状态
    var selectedIds by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (enableInternalScroll) Modifier.verticalScroll(rememberScrollState()) else Modifier),
        verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
    ) {
        Text("今晚做什么？", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("选择 1-3 项活动，系统自动计算影响", fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant)

        WorkdayPlanner.ActivityCategory.entries.forEach { category ->
            val categoryActivities = WorkdayPlanner.allActivities.filter { it.category == category }
            if (categoryActivities.isNotEmpty()) {
                Text(
                    category.label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    categoryActivities.forEach { activity ->
                        val isSelected = activity.id in selectedIds
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
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
                                    Text(activity.name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                    Text(
                                        activity.description,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = 14.sp,
                                        maxLines = 2
                                    )
                                    if (activity.cost > 0) {
                                        Text("${activity.cost}元 | ${activity.duration}小时",
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.outline)
                                    }
                                }
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = { checked ->
                                        selectedIds = if (checked) {
                                            (selectedIds + activity.id).take(3).toSet() // 最多选3项
                                        } else {
                                            selectedIds - activity.id
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        HorizontalDivider()

        // 确认按钮
        val selectedActivities = WorkdayPlanner.allActivities.filter { it.id in selectedIds }
        Button(
            onClick = { onActivitiesSelected(selectedActivities) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimens.radiusMedium),
            enabled = selectedActivities.isNotEmpty()
        ) {
            Text("确认今晚计划（${selectedActivities.size}项）")
        }
    }
}