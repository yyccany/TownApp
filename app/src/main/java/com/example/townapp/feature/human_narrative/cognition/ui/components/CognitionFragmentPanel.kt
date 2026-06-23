package com.example.townapp.feature.human_narrative.cognition.ui.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.domain.model.CognitionFragment
import com.example.townapp.domain.model.CognitionState
import com.example.townapp.domain.model.FragmentType

/**
 * 认知碎片展示面板
 *
 * 落实「以人为本」核心理念：
 * 1. 游戏全程积累认知碎片，不是强迫学习，是自然沉淀
 * 2. 只展示事实，不输出「应该如何活」的结论
 * 3. 把思考空间留给玩家
 */
@Composable
fun CognitionFragmentPanel(
    modifier: Modifier = Modifier
) {
    val fragments by CognitionState.cognitionFragments.collectAsState()
    val cognitionLevel = CognitionState.getCognitionDescription()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 认知状态标题
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(
                text = "🧠",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "认知状态",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$cognitionLevel",
                color = Color(0xFFFFD700).copy(alpha = 0.8f),
                fontSize = 12.sp
            )
            Text(
                text = " · ${fragments.size}碎片",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 10.sp
            )
        }

        // 碎片列表
        if (fragments.isEmpty()) {
            EmptyFragmentsHint()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.heightIn(max = 200.dp)
            ) {
                items(fragments.takeLast(10).reversed()) { fragment ->
                    CognitionFragmentItem(fragment = fragment)
                }
            }
        }
    }
}

/**
 * 空碎片时的提示
 */
@Composable
private fun EmptyFragmentsHint() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "暂无认知碎片",
            color = Color.White.copy(alpha = 0.4f),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "观察他人、与NPC对话、经历人生起伏\n会自然积累认知碎片",
            color = Color.White.copy(alpha = 0.3f),
            fontSize = 10.sp,
            lineHeight = 16.sp
        )
    }
}

/**
 * 单个认知碎片项
 */
@Composable
private fun CognitionFragmentItem(fragment: CognitionFragment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(getFragmentTypeColor(fragment.type).copy(alpha = 0.15f))
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        // 类型标识
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(getFragmentTypeColor(fragment.type).copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getFragmentTypeIcon(fragment.type),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = fragment.source,
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = fragment.description,
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

/**
 * 获取碎片类型对应的颜色
 */
private fun getFragmentTypeColor(type: FragmentType): Color {
    return when (type) {
        FragmentType.OBSERVATION -> Color(0xFF6BB3FF)  // 蓝色：观察
        FragmentType.REFLECTION -> Color(0xFFFFB86B)   // 橙色：反思
        FragmentType.ACCEPTANCE -> Color(0xFFB8FF6B)   // 绿色：接纳
    }
}

/**
 * 获取碎片类型对应的图标
 */
private fun getFragmentTypeIcon(type: FragmentType): String {
    return when (type) {
        FragmentType.OBSERVATION -> "👁"
        FragmentType.REFLECTION -> "💭"
        FragmentType.ACCEPTANCE -> "🌿"
    }
}