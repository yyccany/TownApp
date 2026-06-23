package com.example.townapp.feature.human_narrative.npc.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.feature.human_narrative.npc.model.NpcTimelineVo
import com.example.townapp.feature.human_narrative.npc.model.TimelineNode
import com.example.townapp.feature.human_narrative.npc.model.NodeType
import com.example.townapp.feature.human_narrative.npc.repository.NpcRepository
import com.example.townapp.feature.human_narrative.atmosphere.NpcMoodOverlay
import com.example.townapp.ui.components.TypeWriterText

/**
 * NPC 人生时间线详情页
 *
 * 核心设计：7:3 画面文字占比、大量空镜留白
 * 浏览时自动切换 NPC 专属光影、匹配对应认知视角解读
 *
 * 认知联动（落实「以人为本」）：
 * - 高认知自动追加中立注解，拆解比较偏差、幸存者偏差
 * - 注解极淡金色低透明度小字，玩家可选择看或忽略
 * - 可一键隐藏所有解读文字，纯画面静默浏览
 *
 * 布局结构：
 * ┌─────────────────────────────────┐
 * │  NPC 信息头部（名字+年龄+职业）   │
 * │     专属色调背景                │
 * ├─────────────────────────────────┤
 * │                                 │
 * │       时间线节点列表             │
 * │     年度节点 / 回忆节点          │
 * │                                 │
 * ├─────────────────────────────────┤
 * │  认知中立注解（高认知显示）       │
 * └─────────────────────────────────┘
 */
@Composable
fun NpcTimelineScreen(
    timeline: NpcTimelineVo,
    onBack: () -> Unit,
    onViewMemory: (Int) -> Unit
) {
    // 静默纯画面模式（隐藏所有解读文字）
    var silentMode by remember { mutableStateOf(false) }

    // 选中的节点（用于展开详情）
    var selectedNodeId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // NPC 信息头部（带专属色调）
        Box(modifier = Modifier.fillMaxWidth()) {
            // 专属色调背景（贯穿整个页面）
            timeline.palette?.let {
                NpcMoodOverlay(
                    palette = it,
                    visible = true,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(modifier = Modifier.padding(24.dp)) {
                // 返回按钮
                Text(
                    text = "← 返回",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    modifier = Modifier.clickable { onBack() }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // NPC 名称
                Text(
                    text = timeline.name,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 职业 + 年龄
                Text(
                    text = "${timeline.jobName} · ${timeline.currentAge}岁 · ${timeline.workStateDesc}",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )

                // 健康度
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "健康",
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color.White.copy(alpha = 0.15f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width((80 * timeline.health / 100).dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    when {
                                        timeline.health >= 70 -> Color(0xFF4CAF50)
                                        timeline.health >= 40 -> Color(0xFFFFC107)
                                        else -> Color(0xFFF44336)
                                    }.copy(alpha = 0.6f)
                                )
                        )
                    }
                }

                // 色调中立注解（档案馆特有，极淡）
                timeline.palette?.annotation?.takeIf { it.isNotEmpty() }?.let { annotation ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = annotation,
                        color = Color(0xFFFFD700).copy(alpha = 0.3f),
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }

        // 分割线
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.White.copy(alpha = 0.1f))
        )

        // 静默模式切换按钮
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = if (silentMode) "显示文字" else "纯画面浏览",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 10.sp,
                modifier = Modifier.clickable { silentMode = !silentMode }
            )
        }

        // 时间线节点列表
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(timeline.timelineNodes) { node ->
                TimelineNodeCard(
                    node = node,
                    isSelected = selectedNodeId == node.year,
                    isSilentMode = silentMode,
                    onClick = {
                        if (selectedNodeId == node.year) {
                            selectedNodeId = null
                        } else {
                            selectedNodeId = node.year
                            // 如果是回忆节点，触发回忆弹窗
                            if (node.type == NodeType.MEMORY && node.memoryMarkId > 0) {
                                onViewMemory(node.memoryMarkId)
                            }
                        }
                    }
                )
            }
        }

        // 认知中立注解（高认知时显示，极淡金色）
        if (!silentMode && timeline.cognitionAnnotation.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFD700).copy(alpha = 0.05f)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = timeline.cognitionAnnotation,
                        color = Color(0xFFFFD700).copy(alpha = 0.4f),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // 职业圈层认知局限（消费认知数据）
        if (!silentMode && timeline.cognitiveLimitation.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE1BEE7).copy(alpha = 0.05f)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "视角局限",
                            color = Color(0xFFE1BEE7).copy(alpha = 0.6f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = timeline.cognitiveLimitation,
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 11.sp,
                            lineHeight = 20.sp
                        )
                        if (timeline.limitationExplanation.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = timeline.limitationExplanation,
                                color = Color.White.copy(alpha = 0.4f),
                                fontSize = 10.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        // 消费环境评述
        if (!silentMode && timeline.consumptionCommentary.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFB2EBF2).copy(alpha = 0.05f)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "消费环境",
                            color = Color(0xFFB2EBF2).copy(alpha = 0.6f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = timeline.consumptionCommentary,
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 11.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }

        // 成长环境描述
        if (!silentMode && timeline.birthEnvironment.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFC8E6C9).copy(alpha = 0.05f)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "成长环境",
                            color = Color(0xFFC8E6C9).copy(alpha = 0.6f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = timeline.birthEnvironment,
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 10.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

/**
 * 时间线节点卡片组件
 */
@Composable
private fun TimelineNodeCard(
    node: TimelineNode,
    isSelected: Boolean,
    isSilentMode: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.White.copy(alpha = 0.08f)
            else Color.White.copy(alpha = 0.04f)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 节点类型图标
                Text(
                    text = when (node.type) {
                        NodeType.YEARLY -> "📅"
                        NodeType.MEMORY -> "📝"
                        NodeType.CAREER_CHANGE -> "💼"
                        NodeType.HEALTH_EVENT -> "❤️"
                        NodeType.RETIREMENT -> "🏠"
                        NodeType.DEATH -> "⭐"
                    },
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.width(12.dp))

                // 标题
                Text(
                    text = node.title,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.weight(1f))

                // 年龄
                Text(
                    text = "${node.ageAtYear}岁",
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 11.sp
                )
            }

            // 展开详情（打字机效果，非静默模式）
            if (!isSilentMode) {
                AnimatedVisibility(
                    visible = isSelected,
                    enter = fadeIn(tween(400)),
                    exit = fadeOut(tween(300))
                ) {
                    Column(modifier = Modifier.padding(top = 12.dp)) {
                        TypeWriterText(
                            text = node.description,
                            onComplete = {},
                            textColor = Color.White.copy(alpha = 0.7f),
                            lineHeight = 24,
                            textSize = 12,
                            textAlign = TextAlign.Left,
                            pauseBeforeTyping = 0L,
                            pauseAfterTyping = 0L,
                            pauseSilentAfter = 0L,
                            typingDelay = 50L
                        )

                        if (node.type == NodeType.MEMORY) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "点击查看回忆碎片",
                                color = Color(0xFFFFD700).copy(alpha = 0.5f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}