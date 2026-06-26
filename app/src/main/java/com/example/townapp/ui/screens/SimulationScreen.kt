package com.example.townapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.townapp.domain.engine.PlayerState
import com.example.townapp.domain.engine.GameTime
import com.example.townapp.domain.spacemodel.SpaceState
import com.example.townapp.data.CareerPathSystem
import com.example.townapp.data.FoodItem
import com.example.townapp.data.LifePathData
import com.example.townapp.data.repository.SpaceConfig
import com.example.townapp.ui.design.TownAestheticDesign
import com.example.townapp.ui.viewmodel.SimulationViewModel
import kotlinx.coroutines.launch

@Composable
fun SimulationScreen(
    onNavigateToFoodList: () -> Unit,
    onNavigateToDataViewer: () -> Unit
) {
    val vm: SimulationViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    val showFoodSelector = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val showSpaceSelector = remember { mutableStateOf(false) }
    val showSetup = remember { mutableStateOf(true) }
    val selectedAge = remember { mutableStateOf(20) }
    val selectedLifePath = remember { mutableStateOf(LifePathData.paths.first()) }
    val showEventLog = remember { mutableStateOf(false) }
    val showCharacterInfo = remember { mutableStateOf(false) }
    val showStatsPanel = remember { mutableStateOf(false) }

    val player by vm.playerState.collectAsState()
    val gameTime by vm.gameTime.collectAsState()
    val spaceState by vm.spaceState.collectAsState()
    val actionResult = vm.actionResult.value
    val events = vm.eventHistory
    val isRunning = vm.simulationStatus.collectAsState().value == com.example.townapp.core.state.SimulationStatus.RUNNING
    val foodList = vm.foodItems.value
    val spaceList = vm.availableSpaces.value

    if (showSetup.value) {
        SetupScreen(
            selectedAge = selectedAge.value,
            selectedLifePath = selectedLifePath.value,
            onAgeChange = { selectedAge.value = it },
            onLifePathChange = { selectedLifePath.value = it },
            onStart = {
                vm.startWithLifePath(selectedAge.value, selectedLifePath.value.id)
                showSetup.value = false
            }
        )
        return
    }

    val isNight = gameTime.hours in 22..23 || gameTime.hours in 0..5
    
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundGradient(isNight)
        
        Scaffold(
            topBar = {
                TownTopBar(
                    gameTime = gameTime,
                    events = events,
                    isRunning = isRunning,
                    onToggleSimulation = { vm.toggleSimulation() },
                    onReset = { vm.resetSimulation(); showSetup.value = true },
                    onShowCharacterInfo = { showCharacterInfo.value = true },
                    onShowEventLog = { showEventLog.value = true }
                )
            },
            backgroundColor = Color.Transparent
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xxl))
        
        GreetingCard(gameTime, player)
        
        Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xl))
        
        TodayMoodCard(player, isNight)
        
        Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xl))
        
        AnimatedVisibility(
            visible = isNight,
            enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(animationSpec = tween(800)),
            exit = fadeOut(animationSpec = tween(800)) + slideOutVertically(animationSpec = tween(600))
        ) {
            NightMonologueCard(player)
        }
        
        Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xl))
        
        TimeProgressBar(gameTime, player, isNight)
        
        Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xl))
                
                DailyEventsSection(events = player.dailyEvents, onDismiss = { vm.clearDailyEvents() }, isNight)
                
                Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xl))
                
                QuietActionsGrid(
                    onEat = { showFoodSelector.value = true },
                    onSleep = { hours -> vm.sleep(hours) },
                    onWork = { hours -> vm.work(hours) },
                    onStudy = { hours -> vm.study(hours) },
                    onWalk = { hours -> vm.walk(hours) },
                    onSocialize = { hours -> vm.socialize(hours) },
                    onMeditate = { minutes -> vm.meditate(minutes) },
                    onExercise = { hours -> vm.exercise(hours) },
                    onRead = { hours -> vm.read(hours) },
                    onListenMusic = { hours -> vm.listenMusic(hours) },
                    onCook = { hours -> vm.cook(hours) },
                    onOrganize = { hours -> vm.organize(hours) },
                    onWatchMovie = { hours -> vm.watchMovie(hours) },
                    onTeaBreak = { minutes -> vm.teaBreak(minutes) },
                    onTendPlant = { vm.tendPlant() },
                    onJournal = { minutes -> vm.journal(minutes) },
                    onPayRent = { vm.payRent() },
                    onChangeSpace = { showSpaceSelector.value = true },
                    onIdle = { hours -> vm.idle(hours) },
                    onViewMemories = onNavigateToDataViewer,
                    isNight = isNight
                )
                
                Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xl))
                
                StatsToggleButton(isExpanded = showStatsPanel.value, onToggle = { showStatsPanel.value = !showStatsPanel.value })
                
                AnimatedVisibility(
                    visible = showStatsPanel.value,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    StatsPanels(player, spaceState)
                }
                
                Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xxl))
                
                FastForwardCard(onFastForward = { days -> vm.fastForward(days) })
                
                Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xxl))
            }
        }
        
        if (showFoodSelector.value) {
            FoodSelectorDialog(
                foods = foodList,
                onSelectFood = { foodId ->
                    vm.eat(foodId)
                    showFoodSelector.value = false
                },
                onDismiss = { showFoodSelector.value = false },
                searchQuery = searchQuery,
                selectedCategory = selectedCategory
            )
        }

        if (showSpaceSelector.value) {
            SpaceSelectorDialog(
                spaces = spaceList,
                currentMoney = player.money,
                onSelectSpace = { spaceId ->
                    vm.changeSpace(spaceId)
                    showSpaceSelector.value = false
                },
                onDismiss = { showSpaceSelector.value = false }
            )
        }

        if (showEventLog.value) {
            EventLogDialog(events = events, onDismiss = { showEventLog.value = false })
        }

        if (showCharacterInfo.value) {
            CharacterDetailDialog(player = player, onDismiss = { showCharacterInfo.value = false })
        }

        AnimatedVisibility(
            visible = actionResult.isNotEmpty(),
            enter = fadeIn() + slideInVertically { -20 },
            exit = fadeOut() + slideOutVertically { -20 }
        ) {
            ActionResultCard(message = actionResult, onDismiss = { vm.clearActionResult() })
        }
    }
}

@Composable
fun BackgroundGradient(isNight: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    if (isNight) {
                        listOf(
                            Color(0xFF1A1A2E),
                            Color(0xFF16213E),
                            Color(0xFF0F3460)
                        )
                    } else {
                        listOf(
                            Color(0xFFFFFBF0),
                            Color(0xFFFDF6E3),
                            Color(0xFFF5EEE6)
                        )
                    }
                )
            )
    )
}

@Composable
fun TownTopBar(
    gameTime: GameTime,
    events: List<String>,
    isRunning: Boolean,
    onToggleSimulation: () -> Unit,
    onReset: () -> Unit,
    onShowCharacterInfo: () -> Unit,
    onShowEventLog: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF2C3E50).copy(alpha = 0.92f),
        elevation = TownAestheticDesign.Elevation.lg,
        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "万物薪俸小镇",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Text(
                        "${gameTime.getDayOfWeekName()} · 第${gameTime.week}周 · ${gameTime.hours}:00",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        letterSpacing = 1.sp
                    )
                }
                
                Row {
                    IconButton(onClick = onShowCharacterInfo) {
                        Text("👤", fontSize = 20.sp)
                    }
                    IconButton(onClick = onShowEventLog) {
                        BadgedBox(
                            badge = {
                                if (events.isNotEmpty()) {
                                    Badge(backgroundColor = Color(0xFF3498DB)) {
                                        Text("${events.size}", color = Color.White, fontSize = 10.sp)
                                    }
                                }
                            }
                        ) {
                            Text("📭", fontSize = 20.sp)
                        }
                    }
                    IconButton(onClick = onToggleSimulation) {
                        Text(if (isRunning) "⏸️" else "▶️", fontSize = 20.sp)
                    }
                    IconButton(onClick = onReset) {
                        Text("🔄", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingCard(gameTime: GameTime, player: PlayerState) {
    val career = CareerPathSystem.allCareers.find { it.id == player.careerId }
    val greeting = getTimeGreeting(gameTime.hours)
    val isNight = gameTime.hours in 22..23 || gameTime.hours in 0..5
    val isInsomnia = player.energy < 30 && isNight
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(24.dp),
        backgroundColor = if (isNight) Color(0xFF1A1A2E).copy(alpha = 0.8f) else Color.White.copy(alpha = 0.8f)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        Brush.radialGradient(
                            if (isNight) {
                                listOf(
                                    Color(0xFF16213E),
                                    Color(0xFF0F3460),
                                    Color(0xFF1A1A2E)
                                )
                            } else {
                                listOf(
                                    Color(0xFFE8F5E9),
                                    Color(0xFFF3E5F5),
                                    Color(0xFFE3F2FD)
                                )
                            }
                        ),
                        RoundedCornerShape(50)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    when (career?.pathType) {
                        CareerPathSystem.CareerPathType.STABLE -> "🏛️"
                        CareerPathSystem.CareerPathType.CORPORATE -> "🏢"
                        CareerPathSystem.CareerPathType.FREELANCE -> "🎨"
                        CareerPathSystem.CareerPathType.BUSINESS -> "🚀"
                        else -> if (isNight) "🌙" else "💼"
                    },
                    fontSize = 48.sp
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                greeting,
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                color = if (isNight) Color.White else Color(0xFF2C3E50),
                letterSpacing = 2.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "${career?.name ?: "朋友"} · ${player.age}岁",
                fontSize = 14.sp,
                color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF7F8C8D)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            if (isInsomnia) {
                Text(
                    "睡不着也没关系，小镇陪着你。",
                    fontSize = 13.sp,
                    color = Color(0xFFF39C12),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            } else if (isNight) {
                Text(
                    "夜深了，愿你有个好梦。",
                    fontSize = 13.sp,
                    color = Color(0xFFAAB7B8),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            } else {
                Text(
                    "今天的你，已经做得很好了。",
                    fontSize = 13.sp,
                    color = Color(0xFFAAB7B8),
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}

fun getTimeGreeting(hour: Int): String {
    return when (hour) {
        in 5..8 -> "🌅 早安"
        in 9..11 -> "☀️ 上午好"
        in 12..13 -> "🍱 午安"
        in 14..17 -> "🌤️ 下午好"
        in 18..20 -> "🌆 傍晚好"
        in 21..22 -> "🌙 晚上好"
        in 23..23 -> "🌃 夜深了"
        in 0..4 -> "🌌 还没睡呢"
        else -> "🌙 晚上好"
    }
}

@Composable
fun TodayMoodCard(player: PlayerState, isNight: Boolean) {
    val isInsomnia = player.energy < 30 && isNight
    
    val moodEmoji = when {
        isInsomnia -> "😰"
        player.happiness >= 70 -> "😊"
        player.happiness >= 40 -> "😌"
        else -> "😔"
    }
    
    val energyEmoji = when {
        isInsomnia -> "😴"
        player.energy >= 70 -> "⚡"
        player.energy >= 40 -> "🔋"
        else -> "💤"
    }
    
    val moodColor = getMoodColor(player.happiness, player.anxiety, player.trauma)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = moodColor.copy(alpha = if (isNight) 0.2f else 0.15f)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            if (isInsomnia) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🌙", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "失眠中",
                        fontSize = 14.sp,
                        color = Color(0xFFF39C12),
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(moodEmoji, fontSize = 32.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("心情", fontSize = 12.sp, color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF95A5A6))
                    Text("${player.happiness.toInt()}%", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = if (isNight) Color.White else Color(0xFF2C3E50))
                }
                
                Divider(color = if (isNight) Color(0xFF34495E) else Color(0xFFECF0F1), modifier = Modifier.width(1.dp), thickness = 1.dp)
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(energyEmoji, fontSize = 32.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("精力", fontSize = 12.sp, color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF95A5A6))
                    Text("${player.energy.toInt()}%", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = if (isNight) Color.White else Color(0xFF2C3E50))
                }
                
                Divider(color = if (isNight) Color(0xFF34495E) else Color(0xFFECF0F1), modifier = Modifier.width(1.dp), thickness = 1.dp)
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("❤️", fontSize = 32.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("健康", fontSize = 12.sp, color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF95A5A6))
                    Text("${player.health.toInt()}%", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = if (isNight) Color.White else Color(0xFF2C3E50))
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            EmotionBar(player, isNight)
        }
    }
}

@Composable
fun EmotionBar(player: PlayerState, isNight: Boolean) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("内心状态", fontSize = 12.sp, color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF95A5A6))
            Text("今日总时长固定1440分钟，人人均等", fontSize = 10.sp, color = Color(0xFFBDC3C7))
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            MiniEmotionBlock("焦虑", player.anxiety, Color(0xFFE74C3C), isNight)
            MiniEmotionBlock("创伤", player.trauma, Color(0xFF8E44AD), isNight)
            MiniEmotionBlock("孤独", player.loneliness, Color(0xFFF39C12), isNight)
            MiniEmotionBlock("掌控", player.control, Color(0xFF27AE60), isNight)
        }
    }
}

@Composable
fun MiniEmotionBlock(label: String, value: Double, color: Color, isNight: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(8.dp)
                .background(if (isNight) Color(0xFF34495E) else Color(0xFFECF0F1), RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .width((50 * (value / 100)).dp)
                    .height(8.dp)
                    .background(color, RoundedCornerShape(4.dp))
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 11.sp, color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF95A5A6))
        Text("${value.toInt()}%", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = if (isNight) Color.White else Color(0xFF2C3E50))
    }
}

fun getMoodColor(happiness: Double, anxiety: Double, trauma: Double): Color {
    return when {
        trauma > 50 || anxiety > 70 -> Color(0xFF2C3E50)
        anxiety > 50 -> Color(0xFF8E44AD)
        happiness < 40 -> Color(0xFFF39C12)
        happiness >= 70 -> Color(0xFF27AE60)
        else -> Color(0xFF3498DB)
    }
}

@Composable
fun NightMonologueCard(player: PlayerState) {
    val nightResult = remember(player) {
        com.example.townapp.domain.night.NightSystem.calculateNightState(player)
    }
    
    val isInsomnia = nightResult.sleepStatus == com.example.townapp.data.database.entity.NightStateEntity.SLEEP_STATUS_INSOMNIA
    val isDeepSleep = nightResult.sleepStatus == com.example.townapp.data.database.entity.NightStateEntity.SLEEP_STATUS_DEEP_SLEEP
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(24.dp),
        backgroundColor = if (isInsomnia) Color(0xFF1A1A2E).copy(alpha = 0.9f) else Color(0xFF0F3460).copy(alpha = 0.85f)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    when {
                        isInsomnia -> "🌙"
                        isDeepSleep -> "💤"
                        else -> "🌃"
                    },
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    when {
                        isInsomnia -> "深夜独白"
                        isDeepSleep -> "安然入睡"
                        else -> "夜晚思绪"
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    when {
                        isInsomnia -> "😰"
                        isDeepSleep -> "😊"
                        else -> "😌"
                    },
                    fontSize = 32.sp,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        nightResult.nightMonoText,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 22.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
            
            if (nightResult.hasDream) {
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(nightResult.dreamEmoji, fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                    Text(
                        "梦里：${nightResult.dreamContent}",
                        fontSize = 13.sp,
                        color = Color(0xFFAAB7B8),
                        lineHeight = 20.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    when {
                        isInsomnia -> "失眠中"
                        isDeepSleep -> "深度睡眠"
                        else -> nightResult.sleepStatus.replace("_", " ")
                    },
                    fontSize = 12.sp,
                    color = if (isInsomnia) Color(0xFFF39C12) else Color(0xFFAAB7B8)
                )
            }
        }
    }
}

@Composable
fun TimeProgressBar(gameTime: GameTime, player: PlayerState, isNight: Boolean) {
    val totalMinutes = 1440
    val passedMinutes = gameTime.hours * 60
    val progress = passedMinutes.toDouble() / totalMinutes
    
    val workMinutes = player.dailyWorkMinutes
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = if (isNight) Color(0xFF16213E).copy(alpha = 0.6f) else Color.White.copy(alpha = 0.6f)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("今日时间", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = if (isNight) Color.White else Color(0xFF2C3E50))
                Text("${gameTime.hours}:00", fontSize = 14.sp, color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF7F8C8D))
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(if (isNight) Color(0xFF0F3460) else Color(0xFFECF0F1), RoundedCornerShape(8.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width((progress * 360).dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF3498DB),
                                    Color(0xFF2ECC71),
                                    if (isNight) Color(0xFF9B59B6) else Color(0xFFF39C12)
                                )
                            ),
                            RoundedCornerShape(8.dp)
                        )
                )
                
                if (workMinutes > 0) {
                    val workWidth = kotlin.math.min(progress, (workMinutes.toDouble() / totalMinutes)) * 360
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(workWidth.dp)
                            .background(Color(0xFFE74C3C).copy(alpha = 0.5f))
                    )
                }
                
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .offset(x = ((progress * 100) * 0.01 * 360 - 12).dp)
                        .background(if (isNight) Color(0xFFF1C40F) else Color(0xFFE74C3C), RoundedCornerShape(50))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(2.dp)
                            .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(50))
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(Modifier.size(8.dp).background(Color(0xFFE74C3C), RoundedCornerShape(2.dp)))
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("工作 ${workMinutes}min", fontSize = 10.sp, color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF95A5A6))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(Modifier.size(8.dp).background(Color(0xFF3498DB), RoundedCornerShape(2.dp)))
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("休息", fontSize = 10.sp, color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF95A5A6))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(Modifier.size(8.dp).background(Color(0xFF2ECC71), RoundedCornerShape(2.dp)))
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("剩余 ${totalMinutes - passedMinutes}min", fontSize = 10.sp, color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF95A5A6))
                }
            }
        }
    }
}

@Composable
fun DailyEventsSection(events: List<String>, onDismiss: () -> Unit, isNight: Boolean) {
    if (events.isEmpty()) return
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = if (isNight) Color(0xFF16213E).copy(alpha = 0.8f) else Color(0xFFFEF9C3).copy(alpha = 0.6f)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("✨", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (isNight) "今晚发生的事" else "今天，你经历了这些",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isNight) Color.White else Color(0xFF2C3E50)
                    )
                }
                TextButton(onClick = onDismiss) {
                    Text("收下", fontSize = 12.sp, color = Color(0xFF8E44AD))
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            events.forEachIndexed { index, event ->
                EventStoryItem(event, index == events.size - 1, isNight)
            }
        }
    }
}

@Composable
fun EventStoryItem(event: String, isLast: Boolean, isNight: Boolean) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (isNight) Color(0xFF0F3460).copy(alpha = 0.8f) else Color.White.copy(alpha = 0.8f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                when {
                    event.contains("成语") -> "📜"
                    event.contains("微事件") -> "💭"
                    event.contains("陪伴") -> "💝"
                    event.contains("闪光点") -> "🌟"
                    event.contains("发现") -> "🔍"
                    else -> "📌"
                },
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    event,
                    fontSize = 14.sp,
                    color = if (isNight) Color.White else Color(0xFF2C3E50),
                    lineHeight = 20.sp
                )
            }
        }
    }
    
    if (!isLast) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun QuietActionsGrid(
    onEat: () -> Unit,
    onSleep: (Int) -> Unit,
    onWork: (Int) -> Unit,
    onStudy: (Int) -> Unit,
    onWalk: (Int) -> Unit,
    onSocialize: (Int) -> Unit,
    onMeditate: (Int) -> Unit,
    onExercise: (Int) -> Unit,
    onRead: (Int) -> Unit,
    onListenMusic: (Int) -> Unit,
    onCook: (Int) -> Unit,
    onOrganize: (Int) -> Unit,
    onWatchMovie: (Int) -> Unit,
    onTeaBreak: (Int) -> Unit,
    onTendPlant: () -> Unit,
    onJournal: (Int) -> Unit,
    onPayRent: () -> Unit,
    onChangeSpace: () -> Unit,
    onIdle: (Int) -> Unit,
    onViewMemories: () -> Unit,
    isNight: Boolean
) {
    var showDurationDialog by remember { mutableStateOf(false) }
    var durationCallback by remember { mutableStateOf<((Int) -> Unit)?>(null) }
    var durationOptions by remember { mutableStateOf(listOf(1, 2, 4, 8)) }
    var selectedAction by remember { mutableStateOf<String>("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = if (isNight) Color(0xFF16213E).copy(alpha = 0.7f) else Color.White.copy(alpha = 0.7f)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🌿", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "想做些什么呢？",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = if (isNight) Color.White else Color(0xFF2C3E50)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Column(modifier = Modifier.height(400.dp).verticalScroll(rememberScrollState())) {
                chunkedActionItems(
                    listOf(
                        QuietAction("🍔", "好好吃饭", onEat),
                        QuietAction("😴", "睡一会儿", onClick = { selectedAction = "睡一会儿"; durationCallback = onSleep; durationOptions = listOf(4, 6, 8, 10); showDurationDialog = true }),
                        QuietAction("☕", "发会儿呆", onClick = { selectedAction = "发会儿呆"; durationCallback = onIdle; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true }),
                        QuietAction("�", "散散步", onClick = { selectedAction = "散散步"; durationCallback = onWalk; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true }),
                        QuietAction("🧘", "安静冥想", onClick = { selectedAction = "安静冥想"; durationCallback = onMeditate; durationOptions = listOf(10, 20, 30, 60); showDurationDialog = true }),
                        QuietAction("🎵", "听听音乐", onClick = { selectedAction = "听听音乐"; durationCallback = onListenMusic; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true }),
                        QuietAction("📖", "读一本书", onClick = { selectedAction = "读一本书"; durationCallback = onRead; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true }),
                        QuietAction("🍵", "喝杯茶", onClick = { selectedAction = "喝杯茶"; durationCallback = onTeaBreak; durationOptions = listOf(15, 30, 45, 60); showDurationDialog = true }),
                        QuietAction("�", "去工作", onClick = { selectedAction = "去工作"; durationCallback = onWork; durationOptions = listOf(2, 4, 6, 8); showDurationDialog = true }),
                        QuietAction("📚", "学些东西", onClick = { selectedAction = "学些东西"; durationCallback = onStudy; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true }),
                        QuietAction("👥", "和人相处", onClick = { selectedAction = "和人相处"; durationCallback = onSocialize; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true }),
                        QuietAction("🏃", "动一动", onClick = { selectedAction = "动一动"; durationCallback = onExercise; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true }),
                        QuietAction("�", "做顿饭", onClick = { selectedAction = "做顿饭"; durationCallback = onCook; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true }),
                        QuietAction("🧹", "整理一下", onClick = { selectedAction = "整理一下"; durationCallback = onOrganize; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true }),
                        QuietAction("�", "看部电影", onClick = { selectedAction = "看部电影"; durationCallback = onWatchMovie; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true }),
                        QuietAction("📝", "写日记", onClick = { selectedAction = "写日记"; durationCallback = onJournal; durationOptions = listOf(10, 20, 30, 60); showDurationDialog = true }),
                        QuietAction("🌱", "照顾植物", onTendPlant),
                        QuietAction("📜", "翻看回忆", onViewMemories),
                        QuietAction("🏠", "换个住处", onChangeSpace),
                        QuietAction("💰", "交房租", onPayRent)
                    )
                ).forEach { rowItems ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        rowItems.forEach { action ->
                            QuietActionCard(action, isNight)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }

    if (showDurationDialog) {
        DurationSelectorDialog(
            title = selectedAction,
            options = durationOptions,
            onSelect = { hours -> durationCallback?.invoke(hours) },
            onDismiss = { showDurationDialog = false },
            isNight = isNight
        )
    }
}

fun chunkedActionItems(items: List<QuietAction>): List<List<QuietAction>> {
    return items.chunked(3)
}

data class QuietAction(val emoji: String, val label: String, val onClick: () -> Unit)

@Composable
fun QuietActionCard(action: QuietAction, isNight: Boolean) {
    Column(
        modifier = Modifier
            .clickable { action.onClick() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (isNight) Color(0xFF0F3460).copy(alpha = 0.8f) else Color(0xFFF8FAFC),
            elevation = 0.dp,
            modifier = Modifier.size(64.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(action.emoji, fontSize = 28.sp)
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(action.label, fontSize = 12.sp, color = if (isNight) Color(0xFFAAB7B8) else Color(0xFF5D6D7E), textAlign = TextAlign.Center)
    }
}

@Composable
fun StatsToggleButton(isExpanded: Boolean, onToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clickable { onToggle() },
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "查看更多细节",
                fontSize = 14.sp,
                color = Color(0xFF7F8C8D)
            )
            Text(
                if (isExpanded) "▼" else "▶",
                fontSize = 14.sp,
                color = Color(0xFF7F8C8D)
            )
        }
    }
}

@Composable
fun StatsPanels(player: PlayerState, spaceState: SpaceState) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White.copy(alpha = 0.7f)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("身体感受", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2C3E50))
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    MiniStat("饱腹", player.hunger.toInt(), getStatColor(player.hunger))
                    MiniStat("焦虑", player.anxiety.toInt(), getAnxietyColor(player.anxiety))
                    MiniStat("孤独", player.loneliness.toInt(), getAnxietyColor(player.loneliness))
                    MiniStat("掌控", player.control.toInt(), getStatColor(player.control))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White.copy(alpha = 0.7f)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("居住环境", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2C3E50))
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    MiniStat("采光", spaceState.light, getStatColor(spaceState.light.toDouble()))
                    MiniStat("安静", 100 - spaceState.noise, getStatColor((100 - spaceState.noise).toDouble()))
                    MiniStat("清洁", spaceState.cleanliness, getStatColor(spaceState.cleanliness.toDouble()))
                    MiniStat("通风", spaceState.ventilation, getStatColor(spaceState.ventilation.toDouble()))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White.copy(alpha = 0.7f)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("生活状态", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2C3E50))
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    MiniStat("余额", player.money.toInt(), Color(0xFF27AE60))
                    MiniStat("技能", player.skillLevel.toInt(), Color(0xFF3498DB))
                    MiniStat("压力", player.generationalPressure.toInt(), getAnxietyColor(player.generationalPressure.toDouble()))
                }
            }
        }
    }
}

@Composable
fun MiniStat(label: String, value: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(color.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "$value",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 11.sp, color = Color(0xFF95A5A6))
    }
}

fun getStatColor(value: Double): Color {
    return when {
        value >= 70 -> Color(0xFF27AE60)
        value >= 40 -> Color(0xFFF39C12)
        else -> Color(0xFFE74C3C)
    }
}

fun getAnxietyColor(value: Double): Color {
    return when {
        value >= 70 -> Color(0xFFE74C3C)
        value >= 40 -> Color(0xFFF39C12)
        else -> Color(0xFF27AE60)
    }
}

@Composable
fun DurationSelectorDialog(title: String, options: List<Int>, onSelect: (Int) -> Unit, onDismiss: () -> Unit, isNight: Boolean) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, color = if (isNight) Color.White else Color(0xFF2C3E50)) },
        text = {
            Column {
                options.forEach { hours ->
                    Button(
                        onClick = { onSelect(hours); onDismiss() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (isNight) Color(0xFF3498DB) else Color(0xFF3498DB)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (hours >= 60) "${hours}分钟" else "${hours}小时", color = Color.White)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("取消", color = if (isNight) Color.White else Color(0xFF3498DB)) }
        },
        backgroundColor = if (isNight) Color(0xFF16213E) else Color.White
    )
}

@Composable
fun FastForwardCard(onFastForward: (Int) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("快进时间") },
            text = {
                Column {
                    listOf(1, 7, 14, 30).forEach { days ->
                        Button(
                            onClick = { onFastForward(days); showDialog = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3498DB)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(if (days == 1) "1天" else "${days}天", color = Color.White)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("取消") }
            }
        )
    }

    Button(
        onClick = { showDialog = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2C3E50)),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
    ) {
        Text("快进时间", color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun FoodSelectorDialog(
    foods: List<FoodItem>,
    onSelectFood: (Int) -> Unit,
    onDismiss: () -> Unit,
    searchQuery: MutableState<String>,
    selectedCategory: MutableState<String?>
) {
    val categories = foods.map { it.category }.distinct()
    val filteredFoods = foods.filter { food ->
        val matchesSearch = food.name.lowercase().contains(searchQuery.value.lowercase()) ||
                          food.category.lowercase().contains(searchQuery.value.lowercase())
        val matchesCategory = selectedCategory.value == null || food.category == selectedCategory.value
        matchesSearch && matchesCategory
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("想吃点什么？") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    placeholder = { Text("搜索食物...") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    FilterChip("全部", selectedCategory.value == null) { selectedCategory.value = null }
                    categories.forEach { category ->
                        FilterChip(category, selectedCategory.value == category) { selectedCategory.value = category }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(300.dp)) {
                    LazyColumn {
                        items(filteredFoods) { food ->
                            FoodItemCard(food = food, onClick = { onSelectFood(food.id) })
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3498DB))) {
                Text("取消", color = Color.White)
            }
        }
    )
}

@Composable
fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color(0xFF3498DB) else Color(0xFFECF0F1)
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = if (isSelected) Color.White else Color(0xFF5D6D7E),
            fontSize = 12.sp
        )
    }
}

@Composable
fun FoodItemCard(food: FoodItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp).clickable { onClick() },
        elevation = 0.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(0xFFFAFAFA)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(food.name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Text("${food.category} · ¥${String.format("%.2f", food.pricePer100g)}/100g", fontSize = 12.sp, color = Color(0xFF95A5A6))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("营养: ${String.format("%.0f", food.nutritionalScore)}", fontSize = 12.sp, color = Color(0xFF27AE60))
            }
        }
    }
}

@Composable
fun SpaceSelectorDialog(
    spaces: List<SpaceConfig>,
    currentMoney: Double,
    onSelectSpace: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("想住在哪里？") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("当前余额: ¥${String.format("%.2f", currentMoney)}", modifier = Modifier.padding(bottom = 8.dp), color = Color(0xFF2C3E50))
                Box(modifier = Modifier.height(300.dp)) {
                    LazyColumn {
                        items(spaces) { space ->
                            SpaceItemCard(
                                space = space,
                                canAfford = currentMoney >= space.price,
                                onClick = { if (currentMoney >= space.price) onSelectSpace(space.id) }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3498DB))) {
                Text("取消", color = Color.White)
            }
        }
    )
}

@Composable
fun SpaceItemCard(
    space: SpaceConfig,
    canAfford: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(enabled = canAfford) { onClick() },
        elevation = 0.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = if (canAfford) Color(0xFFFAFAFA) else Color(0xFFECF0F1)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(space.name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Text("${space.area}㎡ · ¥${String.format("%.2f", space.price)}/月", fontSize = 12.sp, color = Color(0xFF95A5A6))
            }
            if (!canAfford) {
                Text("余额不足", fontSize = 12.sp, color = Color(0xFFE74C3C))
            }
        }
    }
}

@Composable
fun EventLogDialog(events: List<String>, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("故事记录") },
        text = {
            if (events.isEmpty()) {
                Text("还没有故事发生", color = Color(0xFF95A5A6))
            } else {
                Column(modifier = Modifier.height(400.dp).verticalScroll(rememberScrollState())) {
                    events.forEachIndexed { index, event ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            backgroundColor = if (index == 0) Color(0xFFE3F2FD) else Color(0xFFFAFAFA),
                            elevation = 0.dp,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                event,
                                modifier = Modifier.padding(12.dp),
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3498DB))) {
                Text("关闭", color = Color.White)
            }
        }
    )
}

@Composable
fun CharacterDetailDialog(player: PlayerState, onDismiss: () -> Unit) {
    val career = CareerPathSystem.allCareers.find { it.id == player.careerId }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    when (career?.pathType) {
                        CareerPathSystem.CareerPathType.STABLE -> "🏛️"
                        CareerPathSystem.CareerPathType.CORPORATE -> "🏢"
                        CareerPathSystem.CareerPathType.FREELANCE -> "🎨"
                        CareerPathSystem.CareerPathType.BUSINESS -> "🚀"
                        else -> "💼"
                    },
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("关于你")
            }
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(career?.description ?: "", fontSize = 14.sp, color = Color(0xFF7F8C8D), lineHeight = 20.sp)
                Spacer(modifier = Modifier.height(20.dp))
                
                DetailItem("路线", career?.pathType?.label ?: "未知")
                DetailItem("年龄", "${player.age}岁")
                DetailItem("工龄", "${player.workingYears}年")
                DetailItem("存款", "¥${String.format("%.2f", player.money)}")
                DetailItem("技能等级", "${player.skillLevel.toInt()}")
                DetailItem("健康", "${player.health.toInt()}")
                DetailItem("代际压力", "${player.generationalPressure.toInt()}")
                if (player.hasFamily) {
                    DetailItem("家庭", "有")
                    DetailItem("家庭压力", "${player.familyPressure.toInt()}")
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3498DB))) {
                Text("关闭", color = Color.White)
            }
        }
    )
}

@Composable
fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color(0xFF95A5A6), fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Color(0xFF2C3E50))
    }
}

@Composable
fun ActionResultCard(message: String, onDismiss: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        backgroundColor = Color(0xFFE8F5E9).copy(alpha = 0.8f),
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("✅", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(message, color = Color(0xFF1E8449), modifier = Modifier.weight(1f))
            IconButton(onClick = onDismiss) {
                Text("✕", fontSize = 16.sp, color = Color(0xFF95A5A6))
            }
        }
    }
}

@Composable
fun SetupScreen(
    selectedAge: Int,
    selectedLifePath: com.example.townapp.data.LifePath,
    onAgeChange: (Int) -> Unit,
    onLifePathChange: (com.example.townapp.data.LifePath) -> Unit,
    onStart: () -> Unit
) {
    val mainPaths = LifePathData.paths.take(10)
    val specialPaths = LifePathData.paths.drop(10)
    val showSpecial = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundGradient(isNight = false)
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            Text(
                "万物薪俸小镇",
                fontSize = 36.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFF2C3E50),
                letterSpacing = 4.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "每个人都在过自己的日子。有些被看见了，有些安静发生着。",
                fontSize = 14.sp,
                color = Color(0xFF7F8C8D),
                modifier = Modifier.padding(bottom = 40.dp),
                textAlign = TextAlign.Center
            )
            
            Text("你想以怎样的方式开始？", fontSize = 18.sp, fontWeight = FontWeight.Light, modifier = Modifier.align(Alignment.Start), color = Color(0xFF2C3E50))
            Spacer(modifier = Modifier.height(16.dp))
            
            mainPaths.forEach { path ->
                LifePathCard(path = path, isSelected = selectedLifePath.id == path.id, onSelect = { onLifePathChange(path) })
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showSpecial.value = !showSpecial.value }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (showSpecial.value) "▼ 收起其他可能性" else "▶ 看看其他可能性",
                    fontSize = 13.sp,
                    color = Color(0xFF8E44AD),
                    fontWeight = FontWeight.Medium
                )
            }
            
            if (showSpecial.value) {
                specialPaths.forEach { path ->
                    LifePathCard(path = path, isSelected = selectedLifePath.id == path.id, onSelect = { onLifePathChange(path) })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            Text("今年你多大了？", fontSize = 18.sp, fontWeight = FontWeight.Light, modifier = Modifier.align(Alignment.Start), color = Color(0xFF2C3E50))
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                listOf(20, 25, 30, 35, 40).forEach { age ->
                    AgeChip(ageValue = age, isSelected = selectedAge == age, onSelect = { onAgeChange(age) })
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                when {
                    selectedAge == 20 -> "🎓 刚步入社会，充满理想"
                    selectedAge in 25..30 -> "💼 已有${selectedAge - 20}年工作经验"
                    selectedAge >= 35 -> "🏠 成熟稳重，面临更多责任"
                    else -> ""
                },
                fontSize = 13.sp,
                color = Color(0xFF95A5A6),
                modifier = Modifier.align(Alignment.Start)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = onStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2C3E50)),
                shape = RoundedCornerShape(28.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
            ) {
                Text("开始这段旅程", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Light)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun LifePathCard(
    path: com.example.townapp.data.LifePath,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        backgroundColor = if (isSelected) Color(0xFFE3F2FD) else Color.White.copy(alpha = 0.8f),
        elevation = 0.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    path.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f),
                    color = Color(0xFF2C3E50)
                )
                if (isSelected) {
                    Text("✓", fontSize = 18.sp, color = Color(0xFF3498DB))
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                path.subtitle,
                fontSize = 12.sp,
                color = Color(0xFF8E44AD),
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                path.sceneDescription.take(60) + "...",
                fontSize = 12.sp,
                color = Color(0xFF95A5A6),
                maxLines = 2,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun AgeChip(ageValue: Int, isSelected: Boolean, onSelect: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { onSelect() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color(0xFF2C3E50) else Color(0xFFECF0F1),
        elevation = 0.dp
    ) {
        Text(
            "${ageValue}岁",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            color = if (isSelected) Color.White else Color(0xFF5D6D7E),
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}