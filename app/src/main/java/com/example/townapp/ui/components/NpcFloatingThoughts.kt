package com.example.townapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.npc.model.NpcFloatingThought
import com.example.townapp.npc.model.TownNarrativeState

/**
 * NPC 漂浮独白组件（落实自由平等+实事求是原则）
 *
 * 严格渲染规则：
 * 1. 透明度固定 10%，极小字号 10sp，远距离自动消失
 * 2. NPC 自主行动时的内心碎语极短（4~8 个字），仅作为环境点缀
 * 3. 开启静默观光模式后，完全隐藏所有漂浮文字
 * 4. 同一帧画面同时漂浮文字 ≤ 2 条（由调用方控制）
 */
@Composable
fun NpcFloatingThoughts(
    thoughts: List<NpcFloatingThought>,
    modifier: Modifier = Modifier
) {
    val silentTourMode by TownNarrativeState.silentTourMode.collectAsState()

    // 静默观光模式：完全隐藏所有漂浮文字
    if (silentTourMode) return

    // 严格限制同时显示数量
    val displayThoughts = thoughts.take(TownNarrativeState.MAX_FLOATING_THOUGHTS)

    Box(modifier = modifier) {
        displayThoughts.forEach { thought ->
            FloatingThoughtItem(thought = thought)
        }
    }
}

/**
 * 单个漂浮独白项
 */
@Composable
private fun FloatingThoughtItem(thought: NpcFloatingThought) {
    Box(
        modifier = Modifier
            .padding(
                start = (thought.x * 100).dp,
                top = (thought.y * 100).dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = thought.content,
            style = TextStyle(
                color = Color.White.copy(alpha = TownNarrativeState.FLOATING_TEXT_ALPHA),
                fontSize = TownNarrativeState.FLOATING_TEXT_SIZE_SP.sp,
                fontStyle = FontStyle.Italic,
                letterSpacing = 1.sp
            )
        )
    }
}