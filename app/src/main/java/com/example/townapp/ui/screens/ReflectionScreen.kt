package com.example.townapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.graphicsLayer
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
import com.example.townapp.data.model.IdiomReflection
import com.example.townapp.ui.components.ReflectionCard
import com.example.townapp.ui.viewmodel.TownViewModel
import kotlinx.coroutines.launch

/**
 * 观念思辨三阶段展示页。
 *
 * 展示所有反思卡片（现代规训 + AI 时代），
 * 已解锁的可展开查看详情，未解锁的显示条件和阶段标签。
 * 觉醒值达到阈值时自动弹出 Snackbar 通知。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReflectionScreen(
    viewModel: TownViewModel,
    onBack: () -> Unit
) {
    val allReflections by viewModel.allReflections.collectAsState()
    val unlockedIds by viewModel.unlockedReflectionIds.collectAsState()
    val awakeningLevel by viewModel.awakeningLevel.collectAsState()
    val totalPoints by viewModel.totalAwakeningPoints.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // 监听新解锁事件 → 弹出 Snackbar
    LaunchedEffect(Unit) {
        viewModel.newlyUnlockedReflection.collect { ref ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "💡 解锁新认知：${ref.idiom}",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    // 计算当前认知阶段
    val currentStage = when {
        totalPoints < 1000 -> "传统成语"
        totalPoints < 5000 -> "现代生活规训"
        else -> "AI 时代新认知"
    }
    val nextThreshold = when {
        totalPoints < 1000 -> 1000
        totalPoints < 5000 -> 5000
        else -> null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("观念思辨") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← 返回")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E1B4B),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .graphicsLayer { clip = true },
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 阶段概览头部
            item {
                StageHeader(
                    currentStage = currentStage,
                    awakeningLevel = awakeningLevel,
                    totalPoints = totalPoints,
                    nextThreshold = nextThreshold
                )
            }

            // 阶段分隔 + 卡片
            val modernReflections = allReflections.filter {
                it.unlockCondition.contains("≥1000") ||
                it.unlockCondition.contains("≥1500") ||
                it.unlockCondition.contains("≥2000") ||
                it.unlockCondition.contains("≥2500") ||
                it.unlockCondition.contains("≥3000") ||
                it.unlockCondition.contains("≥3500")
            }
            val aiReflections = allReflections.filter {
                it.unlockCondition.contains("≥5000") ||
                it.unlockCondition.contains("≥6000") ||
                it.unlockCondition.contains("≥7000") ||
                it.unlockCondition.contains("≥8000")
            }

            // 第一阶段：传统成语（引导）
            item {
                StageSectionHeader(
                    title = "📜 第一阶段 · 传统成语",
                    subtitle = "觉醒值 ≥ 0 解锁 · 已收录 100+ 个",
                    isActive = totalPoints < 1000
                )
            }

            // 第二阶段：现代生活规训
            item {
                StageSectionHeader(
                    title = "🌆 第二阶段 · 现代生活规训",
                    subtitle = "觉醒值 ≥ 1000 解锁 · 反思现代社会的隐形规训",
                    isActive = totalPoints in 1000..4999
                )
            }
            items(modernReflections, key = { it.id }) { ref ->
                ReflectionCard(
                    reflection = ref,
                    isUnlocked = unlockedIds.contains(ref.id)
                )
            }

            // 第三阶段：AI 时代新认知
            item {
                Spacer(modifier = Modifier.height(8.dp))
                StageSectionHeader(
                    title = "🤖 第三阶段 · AI 时代新认知",
                    subtitle = "觉醒值 ≥ 5000 解锁 · AI 时代的人类认知锚点",
                    isActive = totalPoints >= 5000
                )
            }
            items(aiReflections, key = { it.id }) { ref ->
                ReflectionCard(
                    reflection = ref,
                    isUnlocked = unlockedIds.contains(ref.id)
                )
            }

            // 底部留白
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
private fun StageHeader(
    currentStage: String,
    awakeningLevel: Int,
    totalPoints: Int,
    nextThreshold: Int?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B4B)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🧠 当前认知阶段",
                fontSize = 14.sp,
                color = Color(0xFFA5B4FC)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = currentStage,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "觉醒值 $totalPoints",
                    fontSize = 13.sp,
                    color = Color(0xFFC7D2FE)
                )
                if (nextThreshold != null) {
                    Text(
                        text = "  ·  下一阶段需 $nextThreshold",
                        fontSize = 13.sp,
                        color = Color(0xFFA5B4FC)
                    )
                }
            }
            if (nextThreshold != null) {
                Spacer(modifier = Modifier.height(12.dp))
                val progress = (totalPoints.toFloat() / nextThreshold).coerceAtMost(1f)
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = Color(0xFF6366F1),
                    trackColor = Color(0xFF312E81),
                )
            }
        }
    }
}

@Composable
private fun StageSectionHeader(
    title: String,
    subtitle: String,
    isActive: Boolean
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (isActive) Color(0xFF1E1B4B) else MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}