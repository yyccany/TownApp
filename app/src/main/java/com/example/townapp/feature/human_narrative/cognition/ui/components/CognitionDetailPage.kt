package com.example.townapp.feature.human_narrative.cognition.ui.components

import com.example.townapp.ui.theme.AppDimens

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
import com.example.townapp.feature.human_narrative.cognition.CognitionReflection
import com.example.townapp.domain.DecisionPreferenceEngine.DecisionRecord

/**
 * 旧书房——认知反思条目详情页（只读模式）
 *
 * 展示单条俗语的三层深度解读，可选展示该角色在此条目上的过往抉择记录。
 * 无交互按钮，纯阅览。
 *
 * @param reflection 认知反思条目
 * @param pastDecisions 该角色在此条目上的过往决策记录（可为空）
 * @param onBack 返回回调
 */
@Composable
fun CognitionDetailPage(
    reflection: CognitionReflection,
    pastDecisions: List<DecisionRecord> = emptyList(),
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppDimens.paddingLarge)
    ) {
        // ── 返回按钮 ──
        TextButton(onClick = onBack) {
            Text("← 返回书房", fontSize = 14.sp)
        }

        // ── 俗语标题 ──
        Text(
            text = reflection.proverb,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        // ── 个人抉择标签（有历史记录时展示） ──
        if (pastDecisions.isNotEmpty()) {
            Surface(
                shape = RoundedCornerShape(AppDimens.radiusMedium),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "我的过往选择",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    pastDecisions.takeLast(3).forEach { record ->
                        Text(
                            text = "· ${record.eventType}时，选择了「${record.choiceId}」",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        HorizontalDivider()

        // ── 第一层：时代局限 ──
        ReflectionSection(
            title = "第一层：${reflection.layerOne.title}",
            subtitle = reflection.layerOne.subtitle,
            content = reflection.layerOne.coreInsight,
            detail = reflection.layerOne.detailText,
            gameImpact = reflection.layerOne.gameImpact
        )

        // ── 第二层：现代视角 ──
        ReflectionSection(
            title = "第二层：${reflection.layerTwo.title}",
            subtitle = reflection.layerTwo.subtitle,
            content = reflection.layerTwo.coreInsight,
            detail = reflection.layerTwo.detailText,
            gameImpact = reflection.layerTwo.gameImpact
        )

        // ── 第三层：观点总结 ──
        ReflectionSection(
            title = "第三层：${reflection.layerThree.title}",
            subtitle = reflection.layerThree.subtitle,
            content = reflection.layerThree.coreInsight,
            detail = reflection.layerThree.detailText,
            gameImpact = reflection.layerThree.gameImpact
        )

        HorizontalDivider()

        // ── 小镇寄语 ──
        Surface(
            shape = RoundedCornerShape(AppDimens.radiusMedium),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "小镇寄语",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                Text(
                    text = reflection.townCommentary,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp
                )
            }
        }

        // 底部留白
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * 单层解读区块
 */
@Composable
private fun ReflectionSection(
    title: String,
    subtitle: String,
    content: String,
    detail: String,
    gameImpact: String
) {
    Surface(
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = content,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 20.sp
            )
            if (detail.isNotBlank()) {
                Text(
                    text = detail,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
            }
            if (gameImpact.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(AppDimens.radiusSmall),
                    color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "游戏内影响：$gameImpact",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}