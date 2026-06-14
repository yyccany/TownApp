package com.example.townapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.townapp.ui.theme.AppColors
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.townapp.domain.engine.PlayerState
import com.example.townapp.domain.spacemodel.SpaceState
import com.example.townapp.data.CareerPathSystem
import com.example.townapp.data.FoodItem
import com.example.townapp.data.LifePathData
import com.example.townapp.data.model.TownNPC
import com.example.townapp.data.repository.SpaceConfig

import com.example.townapp.ui.viewmodel.SimulationViewModel
import kotlinx.coroutines.launch

@Composable
fun SimulationScreen(
    onNavigateToFoodList: () -> Unit,
    onNavigateToDataViewer: () -> Unit
) {
    val vm: SimulationViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    // 页面级UI状态（不由引擎决定）
    val showFoodSelector = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val showSpaceSelector = remember { mutableStateOf(false) }
    val showSetup = remember { mutableStateOf(true) }
    val selectedAge = remember { mutableStateOf(20) }
    val selectedLifePath = remember { mutableStateOf(LifePathData.paths.first()) }
    val showEventLog = remember { mutableStateOf(false) }
    val showCharacterInfo = remember { mutableStateOf(false) }

    // 从ViewModel订阅的状态（StateFlow → Compose 自动订阅）
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("小镇模拟", fontSize = 18.sp)
                        Text(
                            "${gameTime.getDayOfWeekName()} · 第${gameTime.week}周 · 第${gameTime.days}天 · ${gameTime.hours}:00",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showCharacterInfo.value = true }) {
                        Icon(Icons.Default.Person, contentDescription = "角色信息")
                    }
                    IconButton(onClick = { showEventLog.value = true }) {
                        BadgedBox(
                            badge = {
                                if (events.isNotEmpty()) {
                                    Badge { Text("${events.size}") }
                                }
                            }
                        ) {
                            Icon(Icons.Default.Notifications, contentDescription = "事件")
                        }
                    }
                    IconButton(onClick = {
                        vm.toggleSimulation()
                    }) {
                        Icon(
                            if (isRunning) Icons.Default.Close else Icons.Default.PlayArrow,
                            contentDescription = if (isRunning) "暂停" else "开始"
                        )
                    }
                    IconButton(onClick = {
                        vm.resetSimulation()
                        showSetup.value = true
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "重新开始")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 角色信息卡片
            CharacterInfoCard(player)

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // 状态面板
            StatePanel(
                title = "身体状态",
                icon = Icons.Default.Favorite,
                items = listOf(
                    "饱腹" to player.hunger,
                    "精力" to player.energy,
                    "健康" to player.health
                )
            )

            StatePanel(
                title = "精神状态",
                icon = Icons.Default.FavoriteBorder,
                items = listOf(
                    "愉悦感" to player.happiness,
                    "焦虑感" to player.anxiety,
                    "孤独感" to player.loneliness,
                    "掌控感" to player.control
                )
            )

            // 今日事件显示（新增：成语、微事件等）
            if (player.dailyEvents.isNotEmpty()) {
                DailyEventsPanel(
                    events = player.dailyEvents,
                    onDismiss = { vm.clearDailyEvents() }
                )
            }

            AssetPanel(player)
            SpacePanel(spaceState)

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // 行为按钮网格
            ActionButtonsGrid(
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
                onIdle = { hours -> vm.idle(hours) }
            )

            // 快进按钮
            FastForwardButtons(
                onFastForward = { days -> vm.fastForward(days) }
            )

            // 弹窗和对话框
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
                EventLogDialog(
                    events = events,
                    onDismiss = { showEventLog.value = false }
                )
            }

            if (showCharacterInfo.value) {
                CharacterDetailDialog(
                    player = player,
                    onDismiss = { showCharacterInfo.value = false }
                )
            }

            // Snackbar显示操作结果
            AnimatedVisibility(
                visible = actionResult.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    backgroundColor = AppColors.Success,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(actionResult, color = Color.White, modifier = Modifier.weight(1f))
                        IconButton(onClick = { vm.clearActionResult() }) {
                            Icon(Icons.Default.Close, contentDescription = "关闭", tint = Color.White)
                        }
                    }
                }
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
    // 分类：前10条是主要人生路径，后5条是特殊路径
    val mainPaths = LifePathData.paths.take(10)
    val specialPaths = LifePathData.paths.drop(10)
    val showSpecial = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        Text("选择你的人生", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("每个人都在过自己的日子。有些被看见了，有些安静发生着。", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 24.dp))
        
        // 人生路径选择
        Text("1. 选择人生路径", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(12.dp))
        
        mainPaths.forEach { path ->
            LifePathCard(
                path = path,
                isSelected = selectedLifePath.id == path.id,
                onSelect = { onLifePathChange(path) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // 特殊路径（可选展开）
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showSpecial.value = !showSpecial.value }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                if (showSpecial.value) "▼ 收起特殊路径" else "▶ 展开更多人生路径",
                fontSize = 14.sp,
                color = AppColors.PrimaryBlue,
                fontWeight = FontWeight.Bold
            )
        }
        
        if (showSpecial.value) {
            specialPaths.forEach { path ->
                LifePathCard(
                    path = path,
                    isSelected = selectedLifePath.id == path.id,
                    onSelect = { onLifePathChange(path) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 年龄选择
        Text("2. 选择年龄", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(20, 25, 30, 35, 40).forEach { age ->
                AgeChip(
                    ageValue = age,
                    isSelected = selectedAge == age,
                    onSelect = { onAgeChange(age) }
                )
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
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.Start)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("开始人生模拟", fontSize = 18.sp)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
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
        backgroundColor = if (isSelected) AppColors.SelectedBackground else Color.White,
        elevation = if (isSelected) 4.dp else 1.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    path.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                if (isSelected) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AppColors.PrimaryBlue)
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                path.subtitle,
                fontSize = 12.sp,
                color = AppColors.PrimaryBlue,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                path.sceneDescription.take(60) + "...",
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 2
            )
        }
    }
}

@Composable
fun AgeChip(ageValue: Int, isSelected: Boolean, onSelect: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { onSelect() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) MaterialTheme.colors.primary else Color.LightGray.copy(alpha = 0.3f)
    ) {
        Text(
            "${ageValue}岁",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected) Color.White else Color.Black,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun CharacterInfoCard(player: PlayerState) {
    val career = CareerPathSystem.allCareers.find { it.id == player.careerId }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = AppColors.SurfaceVariant,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    "${career?.name ?: "未知职业"} · ${player.age}岁",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    "存款: ¥${String.format("%.0f", player.money)} · 技能: ${player.skillLevel.toInt()} · 工龄: ${player.workingYears}年",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun StatePanel(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, items: List<Pair<String, Double>>) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), elevation = 2.dp, shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = title, tint = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column {
                items.forEach { (label, value) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(label, fontSize = 14.sp)
                        LinearProgressIndicator(
                            progress = (value / 100.0).toFloat(),
                            modifier = Modifier.width(120.dp),
                            color = getProgressColor(value),
                            backgroundColor = Color.LightGray.copy(alpha = 0.3f)
                        )
                        Text(String.format("%.0f", value), fontSize = 14.sp, modifier = Modifier.width(30.dp), textAlign = TextAlign.End)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

fun getProgressColor(value: Double): Color {
    return when {
        value >= 70 -> AppColors.Success
        value >= 40 -> AppColors.Warning
        else -> Color(0xFFE53935)
    }
}

@Composable
fun AssetPanel(player: PlayerState) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), elevation = 2.dp, shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccountBox, contentDescription = "资产", tint = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("资产状态", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                AssetItem("余额", "¥${String.format("%.0f", player.money)}", Icons.Default.ShoppingCart)
                AssetItem("技能", "${player.skillLevel.toInt()}", Icons.Default.Star)
                AssetItem("压力", "${player.generationalPressure.toInt()}", Icons.Default.Warning)
            }
            if (player.housingDebt > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("房贷: ¥${String.format("%.0f", player.housingDebt)}", color = Color.Red, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun AssetItem(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = MaterialTheme.colors.primary)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun SpacePanel(spaceState: SpaceState) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp), elevation = 2.dp, shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Home, contentDescription = "空间", tint = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("居住环境", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                SpaceItem("采光", spaceState.light)
                SpaceItem("噪音", 100 - spaceState.noise)
                SpaceItem("清洁", spaceState.cleanliness)
                SpaceItem("通风", spaceState.ventilation)
            }
        }
    }
}

@Composable
fun SpaceItem(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(getSpaceColor(value), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("$value", color = Color.White, fontSize = 12.sp)
        }
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

fun getSpaceColor(value: Int): Color {
    return when {
        value >= 70 -> AppColors.Success
        value >= 40 -> AppColors.Warning
        else -> Color(0xFFE53935)
    }
}

@Composable
fun ActionButtonsGrid(
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
    onIdle: (Int) -> Unit
) {
    var showDurationDialog by remember { mutableStateOf(false) }
    var durationCallback by remember { mutableStateOf<((Int) -> Unit)?>(null) }
    var durationOptions by remember { mutableStateOf(listOf(1, 2, 4, 8)) }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text("日常行为", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))

        // 第一行：基本生存
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ActionButtonSim("🍔", "吃饭", onClick = onEat)
            ActionButtonSim("😴", "睡觉", onClick = { durationCallback = onSleep; durationOptions = listOf(4, 6, 8, 10); showDurationDialog = true })
            ActionButtonSim("💼", "工作", onClick = { durationCallback = onWork; durationOptions = listOf(2, 4, 6, 8); showDurationDialog = true })
            ActionButtonSim("📚", "学习", onClick = { durationCallback = onStudy; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true })
        }
        Spacer(modifier = Modifier.height(8.dp))

        // 第二行：身心健康
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ActionButtonSim("🚶", "散步", onClick = { durationCallback = onWalk; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true })
            ActionButtonSim("🧘", "冥想", onClick = { durationCallback = onMeditate; durationOptions = listOf(10, 20, 30, 60); showDurationDialog = true })
            ActionButtonSim("🏃", "运动", onClick = { durationCallback = onExercise; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true })
            ActionButtonSim("👥", "社交", onClick = { durationCallback = onSocialize; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true })
        }
        Spacer(modifier = Modifier.height(8.dp))

        // 第三行：生活娱乐
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ActionButtonSim("📖", "阅读", onClick = { durationCallback = onRead; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true })
            ActionButtonSim("🎵", "音乐", onClick = { durationCallback = onListenMusic; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true })
            ActionButtonSim("🍳", "烹饪", onClick = { durationCallback = onCook; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true })
            ActionButtonSim("🧹", "整理", onClick = { durationCallback = onOrganize; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true })
        }
        Spacer(modifier = Modifier.height(8.dp))

        // 第四行：休闲与经济
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ActionButtonSim("🎬", "观影", onClick = { durationCallback = onWatchMovie; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true })
            ActionButtonSim("🍵", "茶歇", onClick = { durationCallback = onTeaBreak; durationOptions = listOf(15, 30, 45, 60); showDurationDialog = true })
            ActionButtonSim("🌱", "植物", onClick = onTendPlant)
            ActionButtonSim("📝", "日记", onClick = { durationCallback = onJournal; durationOptions = listOf(10, 20, 30, 60); showDurationDialog = true })
        }
        Spacer(modifier = Modifier.height(8.dp))

        // 第五行：其他
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ActionButtonSim("☕", "发呆", onClick = { durationCallback = onIdle; durationOptions = listOf(1, 2, 3, 4); showDurationDialog = true })
            ActionButtonSim("🏠", "换房", onClick = onChangeSpace)
            ActionButtonSim("💰", "交租", onClick = onPayRent)
        }
    }

    if (showDurationDialog) {
        DurationSelectorDialog(
            options = durationOptions,
            onSelect = { hours -> durationCallback?.invoke(hours) },
            onDismiss = { showDurationDialog = false }
        )
    }
}

@Composable
fun ActionButtonSim(emoji: String, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = AppColors.SurfaceVariant,
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(emoji, fontSize = 24.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 12.sp)
    }
}

@Composable
fun DurationSelectorDialog(options: List<Int>, onSelect: (Int) -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("选择时长") },
        text = {
            Column {
                options.forEach { hours ->
                    Button(
                        onClick = { onSelect(hours); onDismiss() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(if (hours >= 60) "${hours}分钟" else "${hours}小时")
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("取消") }
        }
    )
}

@Composable
fun FastForwardButtons(onFastForward: (Int) -> Unit) {
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
                                .padding(vertical = 4.dp)
                        ) {
                            Text(if (days == 1) "1天" else "${days}天")
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
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = AppColors.PrimaryBlue)
    ) {
        Icon(Icons.Default.DateRange, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("快进时间", color = Color.White)
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
        title = { Text("选择食物") },
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
                            FoodItemCardSim(food = food, onClick = { onSelectFood(food.id) })
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(onClick = onDismiss) { Text("取消") }
        }
    )
}

@Composable
fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colors.primary else Color.LightGray.copy(alpha = 0.3f)
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 12.sp
        )
    }
}

@Composable
fun FoodItemCardSim(food: FoodItem, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(4.dp).clickable { onClick() }, elevation = 2.dp) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(food.name, fontWeight = FontWeight.Bold)
                Text("${food.category} · ¥${String.format("%.2f", food.pricePer100g)}/100g", fontSize = 12.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("营养: ${String.format("%.0f", food.nutritionalScore)}", fontSize = 12.sp)
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
        title = { Text("选择居住空间") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("当前余额: ¥${String.format("%.2f", currentMoney)}", modifier = Modifier.padding(bottom = 8.dp))
                Box(modifier = Modifier.height(300.dp)) {
                    LazyColumn {
                        items(spaces) { space ->
                            SpaceItemCardSim(
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
            Button(onClick = onDismiss) { Text("取消") }
        }
    )
}

@Composable
fun SpaceItemCardSim(
    space: SpaceConfig,
    canAfford: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(enabled = canAfford) { onClick() },
        elevation = 2.dp,
        backgroundColor = if (canAfford) Color.White else Color.LightGray.copy(alpha = 0.5f)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(space.name, fontWeight = FontWeight.Bold)
                Text("${space.area}㎡ · ¥${String.format("%.2f", space.price)}/月", fontSize = 12.sp, color = Color.Gray)
            }
            if (!canAfford) {
                Text("余额不足", fontSize = 12.sp, color = Color.Red)
            }
        }
    }
}

@Composable
fun EventLogDialog(events: List<String>, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("事件记录") },
        text = {
            if (events.isEmpty()) {
                Text("暂无事件", color = Color.Gray)
            } else {
                Column(modifier = Modifier.height(400.dp).verticalScroll(rememberScrollState())) {
                    events.forEachIndexed { index, event ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            backgroundColor = if (index == 0) AppColors.SelectedBackground else Color.White,
                            elevation = 1.dp
                        ) {
                            Text(
                                event,
                                modifier = Modifier.padding(12.dp),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            Button(onClick = onDismiss) { Text("关闭") }
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
                Text("${career?.name ?: "未知职业"}档案")
            }
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(career?.description ?: "", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                
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
            Button(onClick = onDismiss) { Text("关闭") }
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
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

/**
 * 今日事件面板 - 显示成语、微事件等触发内容
 */
@Composable
fun DailyEventsPanel(
    events: List<String>,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = AppColors.WarningBackground,
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("✨", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("今日发生的事", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                TextButton(onClick = onDismiss) {
                    Text("知道了", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            events.forEach { event ->
                val isIdiom = event.contains("成语")
                val isMicroEvent = event.contains("微事件")
                val isCompanion = event.contains("陪伴")
                val isSpotlight = event.contains("闪光点")

                val icon = when {
                    isIdiom -> "📜"
                    isMicroEvent -> "💭"
                    isCompanion -> "💝"
                    isSpotlight -> "✨"
                    else -> "📌"
                }

                val bgColor = when {
                    isIdiom -> AppColors.SuccessBackground
                    isMicroEvent -> AppColors.SelectedBackground
                    isCompanion -> AppColors.AccentBackground
                    isSpotlight -> AppColors.WarningBackground
                    else -> AppColors.SurfaceVariant
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = bgColor
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(icon, fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            event,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
