package com.example.townapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.townapp.ui.theme.AppDimens
import com.example.townapp.ui.viewmodel.ConsumptionViewModel
import com.example.townapp.ui.viewmodel.ConsumptionViewModel.DimensionScore

/**
 * 消费仪表盘界面
 *
 * 展示衣食住行四大维度的消费倾向评分，
 * 以及不同消费习惯带来的长期效果。
 *
 * 设计原则：
 * - 无对错评判，只用客观数值展示不同取舍的长期结果
 * - 温柔的色调，不制造焦虑
 * - 数据默认折叠，体现无评判的治愈内核
 *
 * 遵循小镇宪章：
 * - 永不输出评判性结论
 * - 永不强迫用户行动
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumptionDashboardScreen(
    viewModel: ConsumptionViewModel = viewModel(),
    onBack: () -> Unit
) {
    val consumptionState by viewModel.consumptionState.collectAsState()
    val choiceEvents by viewModel.choiceEvents.collectAsState()
    val pendingChoice by viewModel.pendingChoice.collectAsState()

    // 是否展开详细数据（默认折叠，体现无评判的治愈内核）
    var showDetails by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("消费观察") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← 返回")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(AppDimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
        ) {
            // 综合评分卡片
            item {
                OverallScoreCard(
                    overallScore = consumptionState.overallScore,
                    level = viewModel.getOverallLevel(),
                    narrative = viewModel.getOrientationNarrative(),
                    onClick = { showDetails = !showDetails }
                )
            }

            // 四大维度评分
            if (showDetails) {
                item {
                    Text(
                        text = "四大维度",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = AppDimens.paddingSmall)
                    )
                }

                items(viewModel.getDimensionScores()) { dimension ->
                    DimensionScoreCard(dimension = dimension)
                }

                // 长期效果
                item {
                    Text(
                        text = "长期影响",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = AppDimens.paddingMedium)
                    )
                }

                item {
                    LongTermEffectCard(effect = viewModel.getLongTermEffect())
                }

                // 统计数据
                item {
                    Text(
                        text = "消费记录",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = AppDimens.paddingMedium)
                    )
                }

                item {
                    ConsumptionStatsCard(
                        peopleOrientedCount = consumptionState.peopleOrientedCount,
                        vanityDrivenCount = consumptionState.vanityDrivenCount,
                        totalAmount = consumptionState.totalConsumptionAmount,
                        savedTimeHours = consumptionState.savedTimeHours
                    )
                }
            }

            // 消费抉择历史
            if (choiceEvents.isNotEmpty()) {
                item {
                    Text(
                        text = "人生十字路口",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = AppDimens.paddingMedium)
                    )
                }

                items(choiceEvents.take(10)) { event ->
                    ChoiceEventCard(event = event)
                }
            }
        }
    }

    // 待处理的消费抉择弹窗
    if (pendingChoice != null) {
        ConsumptionChoiceDialog(
            event = pendingChoice!!,
            onChoice = { choice ->
                viewModel.makeChoice(pendingChoice!!.eventId, choice)
            },
            onDismiss = { /* 不允许关闭，必须做出选择 */ }
        )
    }
}

// ============================================================
// 综合评分卡片
// ============================================================

@Composable
private fun OverallScoreCard(
    overallScore: Int,
    level: String,
    narrative: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.paddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = level,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            // 大数字评分
            Text(
                text = overallScore.toString(),
                fontSize = 64.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            Text(
                text = "人本取向指数",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!narrative.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                Text(
                    text = narrative,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = AppDimens.paddingMedium)
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            Text(
                text = "点击查看详情",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// ============================================================
// 维度评分卡片
// ============================================================

@Composable
private fun DimensionScoreCard(
    dimension: DimensionScore
) {
    val progressColor = when (dimension.level) {
        "high" -> Color(0xFF4CAF50)  // 绿色
        "medium" -> Color(0xFFFFC107)  // 黄色
        else -> Color(0xFFFF9800)  // 橙色
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.paddingMedium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dimension.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${dimension.score}分",
                    style = MaterialTheme.typography.titleSmall,
                    color = progressColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            // 进度条
            LinearProgressIndicator(
                progress = { dimension.score / 100f },
                modifier = Modifier.fillMaxWidth(),
                color = progressColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            Text(
                text = dimension.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ============================================================
// 长期效果卡片
// ============================================================

@Composable
private fun LongTermEffectCard(
    effect: com.example.townapp.domain.consumption.ConsumptionSystem.LongTermEffect
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.paddingMedium)
        ) {
            EffectRow(
                label = "每日健康变化",
                value = if (effect.healthDeltaPerDay >= 0) "+${String.format("%.2f", effect.healthDeltaPerDay)}" else String.format("%.2f", effect.healthDeltaPerDay),
                isPositive = effect.healthDeltaPerDay >= 0
            )

            Divider(modifier = Modifier.padding(vertical = AppDimens.paddingSmall))

            EffectRow(
                label = "每日情绪变化",
                value = if (effect.moodDeltaPerDay >= 0) "+${String.format("%.2f", effect.moodDeltaPerDay)}" else String.format("%.2f", effect.moodDeltaPerDay),
                isPositive = effect.moodDeltaPerDay >= 0
            )

            Divider(modifier = Modifier.padding(vertical = AppDimens.paddingSmall))

            EffectRow(
                label = "精力消耗倍率",
                value = String.format("%.2fx", effect.fatigueMultiplier),
                isPositive = effect.fatigueMultiplier <= 1.0
            )

            Divider(modifier = Modifier.padding(vertical = AppDimens.paddingSmall))

            EffectRow(
                label = "睡眠质量修正",
                value = if (effect.sleepQualityDelta >= 0) "+${String.format("%.2f", effect.sleepQualityDelta)}" else String.format("%.2f", effect.sleepQualityDelta),
                isPositive = effect.sleepQualityDelta >= 0
            )

            Divider(modifier = Modifier.padding(vertical = AppDimens.paddingSmall))

            EffectRow(
                label = "每周额外自由时间",
                value = "${String.format("%.1f", effect.freeTimeBonusHoursPerWeek)}小时",
                isPositive = effect.freeTimeBonusHoursPerWeek >= 0
            )
        }
    }
}

@Composable
private fun EffectRow(
    label: String,
    value: String,
    isPositive: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = if (isPositive) Color(0xFF4CAF50) else Color(0xFFFF9800)
        )
    }
}

// ============================================================
// 消费统计卡片
// ============================================================

@Composable
private fun ConsumptionStatsCard(
    peopleOrientedCount: Int,
    vanityDrivenCount: Int,
    totalAmount: Double,
    savedTimeHours: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.paddingMedium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatItem(
                    label = "人本消费",
                    value = peopleOrientedCount.toString(),
                    color = Color(0xFF4CAF50)
                )
                StatItem(
                    label = "虚荣消费",
                    value = vanityDrivenCount.toString(),
                    color = Color(0xFFFF9800)
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatItem(
                    label = "累计消费",
                    value = "¥${String.format("%.0f", totalAmount)}",
                    color = MaterialTheme.colorScheme.onSurface
                )
                StatItem(
                    label = "节省时间",
                    value = "${String.format("%.1f", savedTimeHours)}h",
                    color = Color(0xFF2196F3)
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ============================================================
// 抉择事件卡片
// ============================================================

@Composable
private fun ChoiceEventCard(
    event: com.example.townapp.data.database.entity.ConsumptionChoiceEventEntity
) {
    val choiceText = when (event.playerChoice) {
        1 -> "选择了人本路线"
        2 -> "选择了虚荣路线"
        else -> "尚未选择"
    }

    val choiceColor = when (event.playerChoice) {
        1 -> Color(0xFF4CAF50)
        2 -> Color(0xFFFF9800)
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.paddingMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = event.eventId,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${event.eventType} · ${event.triggerAge}岁",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = choiceText,
                style = MaterialTheme.typography.bodySmall,
                color = choiceColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ============================================================
// 消费抉择对话框
// ============================================================

@Composable
private fun ConsumptionChoiceDialog(
    event: com.example.townapp.data.database.entity.ConsumptionChoiceEventEntity,
    onChoice: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "人生十字路口")
        },
        text = {
            Column {
                Text(
                    text = "你遇到了一个消费抉择，两种选择会带来不同的长期影响。",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                Text(
                    text = "没有对错，只是不同的人生。",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onChoice(1) }) {
                Text("选择人本路线")
            }
        },
        dismissButton = {
            TextButton(onClick = { onChoice(2) }) {
                Text("选择虚荣路线")
            }
        }
    )
}
