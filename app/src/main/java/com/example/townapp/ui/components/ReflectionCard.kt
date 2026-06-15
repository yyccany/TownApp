package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.model.IdiomReflection

/**
 * 观念思辨卡片 —— 适配三阶段体系的通用展示组件。
 *
 * - 阶段标识：传统成语 / 现代规训 / AI 时代
 * - 点击展开：传统认知 → 觉醒解读
 * - 底部显示解锁阈值
 */
@Composable
fun ReflectionCard(
    reflection: IdiomReflection,
    isUnlocked: Boolean = true,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    // 根据解锁条件判断阶段
    val stageLabel = when {
        reflection.unlockCondition.contains("≥1000") ||
        reflection.unlockCondition.contains("≥1500") ||
        reflection.unlockCondition.contains("≥2000") ||
        reflection.unlockCondition.contains("≥2500") ||
        reflection.unlockCondition.contains("≥3000") ||
        reflection.unlockCondition.contains("≥3500") -> "现代规训"

        else -> "AI 时代"
    }

    val stageColor = when (stageLabel) {
        "现代规训" -> Color(0xFF7C3AED)
        else -> Color(0xFF0891B2)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = isUnlocked) { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) Color(0xFFFFF8E1) else Color(0xFFF3F4F6)
        ),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isUnlocked) 2.dp else 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize()
        ) {
            // 头部：观念名称 + 阶段标签
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .height(24.dp)
                            .background(
                                if (isUnlocked) stageColor else Color(0xFF9CA3AF),
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                    Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
                    Text(
                        text = if (isUnlocked) reflection.idiom else "???" ,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isUnlocked) Color(0xFFE65100) else Color(0xFF9CA3AF)
                    )
                    Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                    // 阶段标签
                    Box(
                        modifier = Modifier
                            .background(
                                if (isUnlocked) stageColor.copy(alpha = 0.15f) else Color(0xFFE5E7EB),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = AppDimens.paddingSmall, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (isUnlocked) stageLabel else "未解锁",
                            fontSize = 10.sp,
                            color = if (isUnlocked) stageColor else Color(0xFF9CA3AF),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                if (isUnlocked) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "收起" else "展开",
                        tint = Color(0xFFE65100),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            if (!isUnlocked) {
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                Text(
                    text = "解锁条件：${reflection.unlockCondition}",
                    fontSize = 12.sp,
                    color = Color(0xFF9CA3AF)
                )
            }

            if (isUnlocked) {
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                // 传统/社会规训
                Text(
                    text = reflection.traditionalView,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )

                // 展开区域
                if (isExpanded) {
                    Spacer(modifier = Modifier.height(AppDimens.paddingLarge))

                    // 觉醒解读
                    Text(
                        text = "🔍 只呈现代价，不做评判",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF7C3AED)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = reflection.awakeningView,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    )

                    // 关联行为
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        reflection.relatedBehaviorTypes.forEach { type ->
                            val label = when (type) {
                                "social" -> "社交"
                                "work" -> "工作"
                                "mental" -> "认知"
                                else -> type
                            }
                            Box(
                                modifier = Modifier
                                    .background(
                                        Color(0xFFE0F2FE),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    color = Color(0xFF0369A1)
                                )
                            }
                        }
                    }

                    // 解锁条件
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "🔓 ${reflection.unlockCondition}",
                            fontSize = 11.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
            }
        }
    }
}