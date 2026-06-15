package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.townapp.data.LifePath
import com.example.townapp.data.LifePathData
import com.example.townapp.ui.theme.AppColors
import com.example.townapp.ui.theme.AppDimens

@Composable
fun LifePathChoiceDialog(
    onLifePathSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedDetailPathId by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(AppDimens.radiusLarge),
            color = AppColors.Surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f)
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(AppDimens.paddingLarge)
            ) {
                // 标题栏
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "选择人生路径",
                            style = MaterialTheme.typography.titleLarge,
                            color = AppColors.TextPrimaryDark
                        )
                        Text(
                            text = "每个人都在过自己的日子",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppColors.TextSecondaryWarm
                        )
                    }
                    TextButton(onClick = onDismiss) {
                        Text("关闭", style = MaterialTheme.typography.bodyMedium, color = AppColors.TextSecondaryWarm)
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                // 人生路径列表（完整24条）
                Column(
                    verticalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
                ) {
                    LifePathData.paths.forEach { path ->
                        val params = LifePathData.careerParams[path.id]
                        LifePathCard(
                            path = path,
                            params = params,
                            onShowDetail = { selectedDetailPathId = path.id },
                            onSelect = { onLifePathSelected(path.id) }
                        )
                    }
                }
            }
        }
    }

    // 详情弹窗
    selectedDetailPathId?.let { pathId ->
        val path = LifePathData.paths.find { it.id == pathId }
        val params = LifePathData.careerParams[pathId]
        path?.let {
            LifePathDetailDialog(
                path = it,
                params = params,
                onSelect = {
                    onLifePathSelected(pathId)
                    selectedDetailPathId = null
                },
                onDismiss = { selectedDetailPathId = null }
            )
        }
    }
}

@Composable
private fun LifePathCard(
    path: LifePath,
    params: LifePathData.CareerParams?,
    onShowDetail: () -> Unit,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onShowDetail),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation)
    ) {
        Column(modifier = Modifier.padding(AppDimens.paddingMedium)) {
            // 名称 + 副标题
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = path.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = AppColors.TextPrimaryDark
                )
                Text(
                    text = path.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.TextSecondaryWarm,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall - 4.dp))

            // 简短描述
            Text(
                text = path.sceneDescription,
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.TextTertiary,
                lineHeight = 14.sp,
                maxLines = 2
            )

            // 职业参数（如果有）
            if (params != null && params.baseSalary > 0) {
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall - 2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ParamTag("月薪", "${params.baseSalary.toInt()}")
                    ParamTag("年增长", "${(params.salaryGrowth * 100).toInt()}%")
                    ParamTag("加班", "${(params.overtimeRate * 100).toInt()}%")
                    ParamTag("裁员", "${(params.layoffRisk * 100).toInt()}%")
                }
            }

            // 闪光点天赋标签
            val talent = path.talent
            if (talent != null) {
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall - 2.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(AppColors.Primary.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "✦ $talent",
                        fontSize = 11.sp,
                        color = AppColors.Primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // 选择按钮
            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            Button(
                onClick = onSelect,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary,
                    contentColor = Color.White
                )
            ) {
                Text("选择这个职业", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun LifePathDetailDialog(
    path: LifePath,
    params: LifePathData.CareerParams?,
    onSelect: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(AppDimens.radiusLarge),
            color = AppColors.Surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f)
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(AppDimens.paddingLarge)
            ) {
                // 标题栏
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = path.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = AppColors.TextPrimaryDark
                        )
                        Text(
                            text = path.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppColors.TextSecondaryWarm
                        )
                    }
                    TextButton(onClick = onDismiss) {
                        Text("返回", style = MaterialTheme.typography.bodyMedium, color = AppColors.TextSecondaryWarm)
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                // 场景描述
                Text(
                    text = "场景",
                    style = MaterialTheme.typography.titleSmall,
                    color = AppColors.TextPrimaryDark,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall - 4.dp))
                Text(
                    text = path.sceneDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.TextSecondary,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                // 职业参数
                if (params != null && params.baseSalary > 0) {
                    Text(
                        text = "职业参数",
                        style = MaterialTheme.typography.titleSmall,
                        color = AppColors.TextPrimaryDark,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(AppDimens.paddingSmall - 4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ParamTag("月薪", "${params.baseSalary.toInt()}")
                        ParamTag("年增长", "${(params.salaryGrowth * 100).toInt()}%")
                        ParamTag("加班", "${(params.overtimeRate * 100).toInt()}%")
                        ParamTag("裁员", "${(params.layoffRisk * 100).toInt()}%")
                    }
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                }

                // 闪光点天赋
                val talent = path.talent
                if (talent != null) {
                    Text(
                        text = "天赋",
                        style = MaterialTheme.typography.titleSmall,
                        color = AppColors.TextPrimaryDark,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(AppDimens.paddingSmall - 4.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(AppColors.Primary.copy(alpha = 0.1f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "✦ $talent",
                            fontSize = 12.sp,
                            color = AppColors.Primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                }

                // 随身物品
                if (path.items.isNotEmpty()) {
                    Text(
                        text = "随身物品",
                        style = MaterialTheme.typography.titleSmall,
                        color = AppColors.TextPrimaryDark,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(AppDimens.paddingSmall - 2.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)) {
                        path.items.forEach { item ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(AppDimens.radiusSmall),
                                colors = CardDefaults.cardColors(containerColor = AppColors.CardBackgroundLight),
                                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                            ) {
                                Column(modifier = Modifier.padding(AppDimens.paddingMedium)) {
                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = AppColors.TextPrimaryDark,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = item.appearance,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AppColors.TextSecondary,
                                        lineHeight = 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = item.sparkle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AppColors.TextTertiary,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                }

                // 底部操作按钮
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = AppColors.TextSecondary
                        )
                    ) {
                        Text("再看看")
                    }
                    Button(
                        onClick = onSelect,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Primary,
                            contentColor = Color.White
                        )
                    ) {
                        Text("选择这个职业", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun ParamTag(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = AppColors.TextTertiary,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = AppColors.TextPrimaryDark,
            fontWeight = FontWeight.SemiBold
        )
    }
}
