package com.example.townapp.ui.cognition

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.business.*
import com.example.townapp.data.cognition.*

/**
 * 双生存法则模拟器 - 主界面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurvivalSimulatorScreen(
    onBack: () -> Unit = {}
) {
    var selectedClass by remember { mutableStateOf<SocialClass?>(null) }
    var currentScenarioIndex by remember { mutableIntStateOf(0) }
    var hasMadeChoice by remember { mutableStateOf(false) }
    var selectedChoice by remember { mutableStateOf<ScenarioChoice?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var choicesHistory by remember { mutableStateOf<List<ScenarioChoice>>(emptyList()) }
    
    val scenarios = SimulatorScenarios.getAllScenarios()
    val currentScenario = scenarios.getOrNull(currentScenarioIndex)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "🎭 双生存法则模拟器",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "返回",
                        tint = Color(0xFF333333)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )
        
        when {
            // 阶段1：选择阶层
            selectedClass == null -> {
                ClassSelectionScreen(
                    onClassSelected = { selectedClass = it }
                )
            }
            
            // 阶段2：选择场景
            currentScenario != null && !hasMadeChoice -> {
                ScenarioSelectionScreen(
                    scenario = currentScenario,
                    userClass = selectedClass!!,
                    scenarioIndex = currentScenarioIndex + 1,
                    totalScenarios = scenarios.size,
                    onChoiceSelected = { choice ->
                        selectedChoice = choice
                        hasMadeChoice = true
                        showResult = true
                        choicesHistory = choicesHistory + choice
                    }
                )
            }
            
            // 阶段3：查看结果
            showResult && selectedChoice != null && currentScenario != null -> {
                ConsequenceResultScreen(
                    scenario = currentScenario,
                    choice = selectedChoice!!,
                    userClass = selectedClass!!,
                    onContinue = {
                        if (currentScenarioIndex < scenarios.lastIndex) {
                            currentScenarioIndex++
                            hasMadeChoice = false
                            selectedChoice = null
                            showResult = false
                        } else {
                            // 模拟结束，显示总结
                            showResult = false
                        }
                    },
                    onRestart = {
                        selectedClass = null
                        currentScenarioIndex = 0
                        hasMadeChoice = false
                        selectedChoice = null
                        showResult = false
                        choicesHistory = emptyList()
                    }
                )
            }
            
            // 阶段4：模拟结束，显示总结
            else -> {
                SimulationSummaryScreen(
                    choices = choicesHistory,
                    userClass = selectedClass!!,
                    onRestart = {
                        selectedClass = null
                        currentScenarioIndex = 0
                        hasMadeChoice = false
                        selectedChoice = null
                        showResult = false
                        choicesHistory = emptyList()
                    }
                )
            }
        }
    }
}

/**
 * 阶层选择界面
 */
@Composable
private fun ClassSelectionScreen(
    onClassSelected: (SocialClass) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "🎭 选择你的初始阶层",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "同一个选择，在不同的阶层，后果是完全不一样的。",
            fontSize = 14.sp,
            color = Color(0xFF666666),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        SocialClass.entries.forEach { socialClass ->
            ClassCard(
                socialClass = socialClass,
                onClick = { onClassSelected(socialClass) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // 底部说明
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "💡 说明",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD93D)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "本模拟器将带你体验10个常见生活场景，每个场景都有「传统道德选择」和「现实法则选择」两种选项。\n\n系统会根据你选择的阶层，计算出真实的后果。",
                    fontSize = 12.sp,
                    color = Color(0xFFD0D0D0),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

/**
 * 阶层卡片
 */
@Composable
private fun ClassCard(
    socialClass: SocialClass,
    onClick: () -> Unit
) {
    val (emoji, color, description) = when (socialClass) {
        SocialClass.BOTTOM -> Triple("💸", Color(0xFFFF6B6B), "年收入 < 10万\n资源有限，需要精打细算")
        SocialClass.MIDDLE -> Triple("🏠", Color(0xFFFFD93D), "年收入 10-100万\n有一定资源，需要平衡")
        SocialClass.TOP -> Triple("💎", Color(0xFF4ECDC4), "年收入 > 100万\n资源充足，有更多选择")
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, color)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = emoji,
                fontSize = 40.sp
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = socialClass.displayName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = Color(0xFF666666),
                    lineHeight = 20.sp
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

/**
 * 场景选择界面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScenarioSelectionScreen(
    scenario: SimulatorScenario,
    userClass: SocialClass,
    scenarioIndex: Int,
    totalScenarios: Int,
    onChoiceSelected: (ScenarioChoice) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 进度条
        LinearProgressIndicator(
            progress = { scenarioIndex.toFloat() / totalScenarios },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFF4A90D9),
            trackColor = Color(0xFFE0E0E0),
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "场景 $scenarioIndex / $totalScenarios",
            fontSize = 12.sp,
            color = Color(0xFF666666)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 场景标题
        Text(
            text = scenario.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // 场景描述
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "📋 场景描述",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A90D9)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = scenario.description,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    lineHeight = 22.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "🤔 你会怎么选择？",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 选择卡片
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            ChoiceCard(
                choice = scenario.traditionalChoice,
                isTraditional = true,
                onClick = { onChoiceSelected(scenario.traditionalChoice) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ChoiceCard(
                choice = scenario.realisticChoice,
                isTraditional = false,
                onClick = { onChoiceSelected(scenario.realisticChoice) }
            )
        }
    }
}

/**
 * 选择卡片
 */
@Composable
private fun ChoiceCard(
    choice: ScenarioChoice,
    isTraditional: Boolean,
    onClick: () -> Unit
) {
    val (icon, color, title) = if (isTraditional) {
        Triple("📜", Color(0xFFFFD93D), "传统道德选择")
    } else {
        Triple("⚡", Color(0xFF4ECDC4), "现实法则选择")
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, color)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = icon,
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = choice.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        color = Color(0xFF666666)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = choice.description,
                fontSize = 13.sp,
                color = Color(0xFFD0D0D0),
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "查看后果 →",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
        }
    }
}

/**
 * 后果结果界面
 */
@Composable
private fun ConsequenceResultScreen(
    scenario: SimulatorScenario,
    choice: ScenarioChoice,
    userClass: SocialClass,
    onContinue: () -> Unit,
    onRestart: () -> Unit
) {
    val consequence = choice.consequences[userClass]!!
    val result = CognitionConsequenceEngine.calculateConsequenceScore(choice, userClass)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 后果标题
        Text(
            text = "📊 选择后果",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        
        Text(
            text = "对于${userClass.displayName}的你来说",
            fontSize = 14.sp,
            color = Color(0xFF666666)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // 即时结果
            ResultCard(
                title = "⚡ 即时结果",
                content = consequence.immediateResult,
                color = Color(0xFFFFD93D)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 长期结果
            ResultCard(
                title = "📈 长期结果",
                content = consequence.longTermResult,
                color = Color(0xFFFF6B6B)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 影响维度
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "📉 影响维度",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A90D9)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    consequence.impacts.forEach { impact ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = impact.dimension.displayName,
                                fontSize = 14.sp,
                                color = Color(0xFF333333),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${impact.magnitude}/5",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    impact.magnitude <= 2 -> Color(0xFF4ECDC4)
                                    impact.magnitude <= 3 -> Color(0xFFFFD93D)
                                    else -> Color(0xFFFF6B6B)
                                }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 引用语
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF3F4F6)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "💬 关键洞察",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD93D)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"${consequence.quote}\"",
                        fontSize = 13.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = Color(0xFF666666),
                        lineHeight = 20.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 关键洞察
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "🔑 分析",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4ECDC4)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = consequence.keyInsight,
                        fontSize = 13.sp,
                        color = Color(0xFF666666),
                        lineHeight = 20.sp
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 继续按钮
        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4A90D9)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "继续下一个场景",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        TextButton(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "重新开始",
                color = Color(0xFF666666)
            )
        }
    }
}

/**
 * 结果卡片
 */
@Composable
private fun ResultCard(
    title: String,
    content: String,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                fontSize = 14.sp,
                color = Color(0xFF666666),
                lineHeight = 22.sp
            )
        }
    }
}

/**
 * 模拟总结界面
 */
@Composable
private fun SimulationSummaryScreen(
    choices: List<ScenarioChoice>,
    userClass: SocialClass,
    onRestart: () -> Unit
) {
    val insight = CognitionConsequenceEngine.generateInsight(choices, userClass)
    val traditionalCount = choices.count { it.id.contains("traditional") }
    val realisticCount = choices.count { it.id.contains("realistic") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "🎉",
            fontSize = 64.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "模拟完成",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "基于${userClass.displayName}的视角",
            fontSize = 16.sp,
            color = Color(0xFF666666)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // 统计
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    emoji = "📜",
                    label = "传统选择",
                    value = "$traditionalCount 次",
                    color = Color(0xFFFFD93D)
                )
                StatItem(
                    emoji = "⚡",
                    label = "现实选择",
                    value = "$realisticCount 次",
                    color = Color(0xFF4ECDC4)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 洞察
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "🧠 认知洞察",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4ECDC4)
                )
                Spacer(modifier = Modifier.height(12.dp))
                insight.split("\n\n").forEach { paragraph ->
                    Text(
                        text = paragraph,
                        fontSize = 14.sp,
                        color = Color(0xFF666666),
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // 重新开始按钮
        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4A90D9)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Refresh, null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "再次体验",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * 统计项
 */
@Composable
private fun StatItem(
    emoji: String,
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = emoji,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF666666)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}
