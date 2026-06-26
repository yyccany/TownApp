package com.example.townapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.domain.util.TimeTickEngine
import com.example.townapp.feature.town_simulation.GentleTextProvider
import com.example.townapp.ui.components.LifePathChoiceDialog
import com.example.townapp.ui.screens.home.WeekEventChoiceDialog
import com.example.townapp.ui.screens.home.rememberTownHomeUiState
import com.example.townapp.ui.viewmodel.HomeViewModel

// 一张纸，只有墨色
private val paper = Color(0xFFF5F0E6)
private val ink = Color(0xFF3A352D)
private val inkLight = Color(0xFF7A7468)
private val inkFaint = Color(0xFFB5AEA0)

@Composable
fun TownHomeScreen(
    viewModel: HomeViewModel,
    onNavigate: (String) -> Unit
) {
    val uiState = rememberTownHomeUiState(viewModel, initialShowLifePathDialog = false)

    var showLifePathDialog by remember { mutableStateOf(false) }
    var stageLabel by remember { mutableStateOf("青年") }

    LaunchedEffect(uiState.time.gameDay) {
        viewModel.onNewDayStart()
        stageLabel = when {
            uiState.time.gameDay < 20 -> "青年"
            uiState.time.gameDay < 40 -> "中年"
            else -> "晚年"
        }
    }

    LaunchedEffect(uiState.time.gameDay) {
        if (uiState.time.gameDay == 0 && !showLifePathDialog) {
            showLifePathDialog = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(paper)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // 右上角：模式切换（速通/精细）—— 低调不打扰
        val currentMode by viewModel.tickMode.collectAsState()
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = 24.dp, vertical = 64.dp)
                .clickable { viewModel.toggleTickMode() }
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = when (currentMode) {
                    TimeTickEngine.TickMode.AUTO -> "速通"
                    TimeTickEngine.TickMode.MANUAL -> "精细"
                },
                fontSize = 11.sp,
                color = inkFaint,
                letterSpacing = 1.sp
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 顶部：只有人生阶段 + 日期时间，安安静静
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp, vertical = 64.dp)
            ) {
                Column {
                    Text(
                        text = stageLabel,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = ink,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "第 ${uiState.time.gameDay} 天  ·  ${String.format("%02d", uiState.time.gameHour)}:00",
                        fontSize = 12.sp,
                        color = inkFaint
                    )
                }
            }

            // 中间：只有文字，AI替你活，你只需要看着
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 48.dp),
                horizontalAlignment = Alignment.Start
            ) {
                val sceneText = GentleTextProvider.describeScene(
                    uiState.time.gameHour,
                    uiState.scene.roomName,
                    uiState.scene.light,
                    uiState.scene.areaSqm
                )
                Text(
                    text = sceneText.lines().firstOrNull() ?: sceneText,
                    fontSize = 16.sp,
                    color = inkLight,
                    lineHeight = 30.sp
                )

                // 所有发生过的事，一段一段铺出来
                uiState.eventLog.forEach { text ->
                    Spacer(modifier = Modifier.height(40.dp))
                    val displayText = text.lines().take(3).joinToString("\n")
                    Text(
                        text = displayText,
                        fontSize = 16.sp,
                        color = inkLight,
                        lineHeight = 30.sp
                    )
                }

                if (uiState.eventLog.isEmpty()) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = "日子刚刚开始。",
                        fontSize = 16.sp,
                        color = inkFaint,
                        lineHeight = 30.sp
                    )
                }

                Spacer(modifier = Modifier.height(120.dp))
            }

            // 底部留足够空白，没有任何按钮——AI在替你过日子
            Spacer(modifier = Modifier.height(80.dp))
        }

        // 周事件选择（关键人生节点才需要你选一下，选完继续自动走）
        val weekChoices = uiState.weeklyEvent.pendingChoices
        if (weekChoices != null) {
            WeekEventChoiceDialog(
                eventSummary = uiState.weeklyEvent.summary,
                choices = weekChoices,
                onChoiceSelected = { choice ->
                    viewModel.applyWeekEventChoice(choice)
                }
            )
        }

        // 开局选一次出身，然后AI接手
        if (showLifePathDialog) {
            LifePathChoiceDialog(
                onLifePathSelected = { pathId ->
                    val mappedId = when (pathId) {
                        "migrant_youth" -> "migrant_worker"
                        "housewife" -> "fulltime_mother"
                        "freelancer" -> "freelance_dev"
                        "shop_owner" -> "small_shop"
                        else -> pathId
                    }
                    viewModel.selectCareer(mappedId)
                    showLifePathDialog = false
                },
                onDismiss = { showLifePathDialog = false }
            )
        }
    }
}
