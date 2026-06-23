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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.domain.model.CognitionState
import com.example.townapp.domain.model.FragmentType
import kotlinx.coroutines.flow.collectLatest

/**
 * 认知手术刀交互面板
 *
 * 核心功能：
 * 1. 解剖各类认知偏差，看到思维陷阱
 * 2. 结合一生行为给出客观剖析
 * 3. 只展示事实，不输出「应该如何活」的结论
 */
@Composable
fun CognitiveScalpelPanel(
    modifier: Modifier = Modifier,
    personalizedSelfReflection: String? = null
) {
    var selectedBias by remember { mutableStateOf<CognitiveBias?>(null) }
    var isUnlocked by remember { mutableStateOf(false) }
    val currentCognitionLevel by CognitionState.cognitionLevel.collectAsState()

    LaunchedEffect(currentCognitionLevel) {
        isUnlocked = currentCognitionLevel >= 2
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 标题
        Text(
            text = "🔪 认知手术刀",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (!isUnlocked) {
            // 未解锁状态
            UnlockedHint()
        } else if (selectedBias == null) {
            // 偏差列表（按认知等级过滤可解锁的偏差）
            BiasList(
                onSelect = { selectedBias = it },
                currentLevel = currentCognitionLevel
            )
        } else {
            // 偏差详情
            BiasDetail(
                bias = selectedBias!!,
                onBack = { selectedBias = null },
                personalizedSelfReflection = personalizedSelfReflection.takeIf {
                    currentCognitionLevel >= 4 && selectedBias == CognitiveBias.HINDSIGHT_BIAS
                }
            )
        }
    }
}

/**
 * 未解锁时的提示
 */
@Composable
private fun UnlockedHint() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🔒",
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "认知深度 ≥ 2 级解锁",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "继续观察、与人相交\n积累更多认知碎片",
            color = Color.White.copy(alpha = 0.4f),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

/**
 * 认知偏差列表（按认知等级过滤可解锁的偏差）
 */
@Composable
private fun BiasList(onSelect: (CognitiveBias) -> Unit, currentLevel: Int) {
    // 按认知等级过滤可解锁的偏差
    // 0-1：未解锁
    // 2-3：基础2种（比较陷阱、完美主义）
    // 4-5：全部6种
    val unlockedBiases = CognitiveBias.entries.filter { bias ->
        when (currentLevel) {
            in 0..1 -> false
            in 2..3 -> bias == CognitiveBias.COMPARISON_TRAP || bias == CognitiveBias.PERFECTIONISM_TRAP
            else -> true
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(unlockedBiases) { bias ->
            BiasChip(
                bias = bias,
                onClick = { onSelect(bias) }
            )
        }
        // 锁定提示
        if (currentLevel < 4) {
            item {
                Text(
                    text = "还有 ${CognitiveBias.entries.size - unlockedBiases.size} 种认知偏差，认知等级 ≥ 4 解锁",
                    color = Color.White.copy(alpha = 0.3f),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

/**
 * 偏差项
 */
@Composable
private fun BiasChip(bias: CognitiveBias, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = bias.icon,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = bias.name,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = bias.description.take(20) + "...",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 11.sp
            )
        }
        Text(
            text = "→",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 14.sp
        )
    }
}

/**
 * 偏差详情
 */
@Composable
private fun BiasDetail(
    bias: CognitiveBias,
    onBack: () -> Unit,
    personalizedSelfReflection: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2A2A2A),
                        Color(0xFF1A1A1A)
                    )
                )
            )
            .padding(20.dp)
    ) {
        // 返回按钮
        Row(
            modifier = Modifier.clickable { onBack() }
        ) {
            Text(
                text = "← 返回",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 标题
        Text(
            text = "${bias.icon} ${bias.displayName}",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 定义
        ScalpelSectionTitle("定义")
        Text(
            text = bias.definition,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 13.sp,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 表现
        ScalpelSectionTitle("常见表现")
        Text(
            text = bias.manifestation,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 客观陈述（不输出结论）
        ScalpelSectionTitle("客观呈现")
        Text(
            text = bias.objectiveStatement,
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 12.sp,
            lineHeight = 18.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        )

        // 【高认知】个性化自省（结合玩家自身经历）
        if (personalizedSelfReflection != null) {
            Spacer(modifier = Modifier.height(16.dp))
            ScalpelSectionTitle("自省")
            Text(
                text = personalizedSelfReflection,
                color = Color(0xFFFFD700).copy(alpha = 0.6f),
                fontSize = 12.sp,
                lineHeight = 18.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 添加碎片按钮
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.1f))
                .clickable {
                    CognitionState.addFragment(
                        type = FragmentType.REFLECTION,
                        source = bias.displayName,
                        description = bias.objectiveStatement.take(50)
                    )
                    onBack()
                }
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✓ 纳入认知碎片",
                color = Color(0xFFFFD700).copy(alpha = 0.8f),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun ScalpelSectionTitle(title: String) {
    Text(
        text = title,
        color = Color(0xFFFFD700).copy(alpha = 0.7f),
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

/**
 * 认知偏差枚举
 */
enum class CognitiveBias(
    val icon: String,
    val displayName: String,
    val description: String,
    val definition: String,
    val manifestation: String,
    val objectiveStatement: String
) {
    SURVIVOR_BIAS(
        icon = "🎖",
        displayName = "幸存者偏差",
        description = "只看到成功，忽略失败",
        definition = "只研究活下来的人，而忽略那些没有活下来的人，导致对成功原因的错误理解。",
        manifestation = "只看到小镇里「成功」的人，看不到同样努力但未能如愿的人。",
        objectiveStatement = "每一个结局背后，都有无数相似的开始，结局只是概率的一种呈现。"
    ),
    COMPARISON_TRAP(
        icon = "⚖",
        displayName = "比较陷阱",
        description = "与他人比较带来的焦虑",
        definition = "无意识地与他人比较，往往选择不利于自己的参照系，导致自我评判失真。",
        manifestation = "觉得自己的工作不如邻居，人生不如别人精彩，忽略了自己独特的生活轨迹。",
        objectiveStatement = "每个人的起点、处境、机遇都不同，比较本身就缺乏客观基础。"
    ),
    HINDSIGHT_BIAS(
        icon = "🔙",
        displayName = "后见之明",
        description = "事后认为必然",
        definition = "事情发生后，回过头来看，觉得结果是显而易见的、必然的。",
        manifestation = "觉得当初「应该」做某个选择，而忽略当时的信息局限和不确定性。",
        objectiveStatement = "当时的决定是基于当时的信息所作出的最优判断，事后评判有失公平。"
    ),
    PRESENT_BIAS(
        icon = "⏰",
        displayName = "当下偏见",
        description = "过度重视现在，轻视未来",
        definition = "对当下的满足感权重过高，低估未来代价，也低估未来收益。",
        manifestation = "觉得今天先休息，明天再努力；或觉得未来还有机会，不必着急。",
        objectiveStatement = "时间是不可逆的资源，每一个当下都是余生中最年轻的时刻。"
    ),
    CONFIRMATION_BIAS(
        icon = "🔍",
        displayName = "确认偏误",
        description = "只看到想看到的",
        definition = "倾向于寻找支持自己已有观点的信息，忽略反驳的证据。",
        manifestation = "觉得某个人「不行」，就会只注意到他做不好的地方，忽略他的优点。",
        objectiveStatement = "人的复杂性远超单一标签，用固定的视角看人，本身就是一种简化。"
    ),
    PERFECTIONISM_TRAP(
        icon = "🎯",
        displayName = "完美主义陷阱",
        description = "不完美等于失败",
        definition = "认为任何不完美都是失败，导致过度自我批评，忽略已经做到的部分。",
        manifestation = "觉得自己的人生「不够好」，因为没有达到想象中的完美标准。",
        objectiveStatement = "完美从来不是人生的常态，不完美才是真实的样子。"
    ),
    CIRCLE_BIAS(
        icon = "🏘",
        displayName = "圈层局限",
        description = "职业环境带来的认知盲区",
        definition = "长期处于特定职业或生活圈层，信息渠道和生活经验被环境塑造，导致对其他人群的处境缺乏理解。",
        manifestation = "觉得坐办公室的人工作轻松，觉得体力劳动者不用动脑，忽略了不同职业的真实代价和压力。",
        objectiveStatement = "环境塑造了每个人的认知框架，视角差异不是谁的错——是不同的经历带来了不同的盲区。"
    )
}