package com.example.townapp.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.domain.model.CognitionState
import com.example.townapp.domain.model.FragmentType

/**
 * 晚年人生复盘面板
 *
 * 三大认知分层（落实「自由平等」+「实事求是」）：
 * 1. 表层复盘（低认知）：用物质标尺衡量（财富、工作、得失）
 * 2. 旁观复盘（中认知）：统计人际、陪伴、独处时光
 * 3. 通透复盘（高认知）：无好坏标尺，仅客观陈列所有选择
 *
 * 严格遵循：
 * 1. 不输出"成功与否"结论
 * 2. 任何认知层级都客观呈现，不褒贬
 * 3. 玩家可选择查看任何一层，也可选择不看
 */
@Composable
fun LifeReviewPanel(
    totalYears: Int,
    npcCount: Int,
    memoryCount: Int,
    fragmentCount: Int,
    cognitionLevel: Int,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {}
) {
    val currentTier = when {
        cognitionLevel < 2 -> 1
        cognitionLevel < 4 -> 2
        else -> 3
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A1A),
                        Color(0xFF0A0A0A)
                    )
                )
            )
            .padding(24.dp)
    ) {
        // 标题 + 关闭按钮
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text(
                text = "🌅",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "人生复盘",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "✕",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 16.sp,
                modifier = Modifier.clickable { onClose() }
            )
        }

        Text(
            text = "总时长：$totalYears 年 · 遇见 $npcCount 人 · 收集 $memoryCount 段回忆",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 三大复盘层级（仅解锁的层级可点）
        ReviewTierCard(
            tier = 1,
            title = "表层复盘",
            subtitle = "物质与收获",
            content = "工作：${totalYears}年。\n结余：未知。\n居住：未知。",
            unlocked = true,
            selected = currentTier == 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        ReviewTierCard(
            tier = 2,
            title = "旁观复盘",
            subtitle = "人际与时光",
            content = "与人交谈：$npcCount 次。\n共鸣瞬间：$fragmentCount 个。\n独处时光：未知。",
            unlocked = true,
            selected = currentTier == 2
        )
        Spacer(modifier = Modifier.height(8.dp))
        ReviewTierCard(
            tier = 3,
            title = "通透复盘",
            subtitle = "无好坏标尺",
            content = "一切选择都有它的代价，也都有它的慰藉。\n没有人能告诉你哪种活法更对，\n因为根本没有标准答案。",
            unlocked = cognitionLevel >= 4,
            selected = currentTier == 3
        )
    }
}

@Composable
private fun ReviewTierCard(
    tier: Int,
    title: String,
    subtitle: String,
    content: String,
    unlocked: Boolean,
    selected: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) Color(0xFFFFD700).copy(alpha = 0.08f)
                else Color.White.copy(alpha = 0.05f)
            )
            .clickable(enabled = unlocked) { }
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (unlocked) when (tier) {
                        1 -> "📊"
                        2 -> "👥"
                        else -> "🌿"
                    } else "🔒",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    color = if (unlocked) Color.White.copy(alpha = 0.9f) else Color.White.copy(alpha = 0.3f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = subtitle,
                    color = if (unlocked) Color.White.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.2f),
                    fontSize = 11.sp
                )
            }
            if (unlocked) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = content,
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    lineHeight = 20.sp
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "认知等级 ≥ 4 解锁",
                    color = Color.White.copy(alpha = 0.3f),
                    fontSize = 10.sp
                )
            }
        }
    }
}