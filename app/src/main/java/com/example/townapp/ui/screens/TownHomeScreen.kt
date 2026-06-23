package com.example.townapp.ui.screens

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.townapp.ui.theme.AppColors
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.feature.town_simulation.GentleTextProvider
import com.example.townapp.data.FoodItem
import com.example.townapp.data.LifePathBinding
import com.example.townapp.data.database.entity.UserBodyState
import com.example.townapp.data.database.entity.UserMentalState
import com.example.townapp.data.database.entity.UserSpaceState
import com.example.townapp.ui.viewmodel.TownViewModel
import com.example.townapp.ui.viewmodel.DailyPlan
import com.example.townapp.ui.viewmodel.AutoLifeTier
import com.example.townapp.ui.viewmodel.ShoppingMode
import com.example.townapp.ui.viewmodel.WeeklyExpense
import com.example.townapp.ui.viewmodel.MedicalPreference
import com.example.townapp.ui.viewmodel.IllnessType
import com.example.townapp.ui.viewmodel.HobbyBudgetTier
import com.example.townapp.ui.viewmodel.ShoppingListItem
import com.example.townapp.ui.viewmodel.WeekEventChoice
import com.example.townapp.ui.viewmodel.WeeklyEventTier
import com.example.townapp.ui.components.CareerChoiceDialog
import com.example.townapp.ui.components.DietPanel
import com.example.townapp.ui.components.LifePathChoiceDialog
import com.example.townapp.ui.components.WorkdayPanel

// ============================================
// 🌟 MVP 主界面 — 半自动状态驱动
// 布局：顶部状态条 → 中间场景描述 → 底部操作栏
// ============================================

@Composable
fun TownHomeScreen(
    viewModel: TownViewModel,
    onNavigate: (String) -> Unit
) {
    val body by viewModel.bodyState.collectAsState()
    val space by viewModel.spaceState.collectAsState()
    val gameHour by viewModel.gameHour.collectAsState()
    val gameDay by viewModel.gameDay.collectAsState()
    val eventLog by viewModel.eventLog.collectAsState()
    val showFoodPicker by viewModel.showFoodPicker.collectAsState()
    val foodItems by viewModel.foodItems.collectAsState()
    val petDialogue by viewModel.petDialogue.collectAsState()
    val foodEatCount by viewModel.foodEatCount.collectAsState()
    val isEditMode by viewModel.isEditMode.collectAsState()
    val todayPlanExecuted by viewModel.todayPlanExecuted.collectAsState()
    val weeklyEvents by viewModel.weeklyEvents.collectAsState()
    val showWeeklyBrief by viewModel.showWeeklyBrief.collectAsState()
    val weeklyStats by viewModel.weeklyStats.collectAsState()
    val currentWeek by viewModel.currentWeek.collectAsState()
    val weeklyPlan by viewModel.weeklyPlan.collectAsState()
    val showWeeklyPlanDialog by viewModel.showWeeklyPlanDialog.collectAsState()
    val autoLifeEnabled by viewModel.autoLifeEnabled.collectAsState()
    val dailyDietPlan by viewModel.dailyDietPlan.collectAsState()
    val eveningPlan by viewModel.eveningPlan.collectAsState()
    val autoLifeSummary by viewModel.autoLifeSummary.collectAsState()
    val shoppingMode by viewModel.shoppingMode.collectAsState()
    val weeklyExpense by viewModel.weeklyExpense.collectAsState()
    val medicalPreference by viewModel.medicalPreference.collectAsState()
    val currentIllness by viewModel.currentIllness.collectAsState()
    val hobbyBudgetTier by viewModel.hobbyBudgetTier.collectAsState()
    val currentHobby by viewModel.currentHobby.collectAsState()
    val hobbyProficiency by viewModel.hobbyProficiency.collectAsState()
    val lastHobbyEvent by viewModel.lastHobbyEvent.collectAsState()
    val vanityTrait by viewModel.vanityTrait.collectAsState()
    val loveState by viewModel.loveState.collectAsState()
    val playerAge by viewModel.playerAge.collectAsState()
    val lifeStage = remember(playerAge) { com.example.townapp.data.LifeStage.fromAge(playerAge) }
    val weeklySocialScore by viewModel.weeklySocialScore.collectAsState()
    val socialBudgetTier by viewModel.socialBudgetTier.collectAsState()
    val weeklyEventTier by viewModel.weeklyEventTier.collectAsState()
    val weeklyEventSummary by viewModel.weeklyEventSummary.collectAsState()
    val pendingWeekEventChoices by viewModel.pendingWeekEventChoices.collectAsState()
    var showComfortTap by remember { mutableStateOf(false) }
    var comfortMessage by remember { mutableStateOf("") }
    var currentBinding by remember { mutableStateOf<LifePathBinding?>(null) }
    var showStatusPanel by remember { mutableStateOf(false) }
    var showWeekEvents by remember { mutableStateOf(false) }
    var showCareerDialog by remember { mutableStateOf(false) }
    var showLifePathDialog by remember { mutableStateOf(gameDay == 0) }
    var showWeekendPanels by remember { mutableStateOf(false) }
    var showMoreMenu by remember { mutableStateOf(false) }
    var currentSpeed by remember { mutableStateOf(1.0) }

    // 加载当前身份绑定配置和流速
    LaunchedEffect(space.addressName) {
        currentBinding = viewModel.getCurrentBinding()
        currentSpeed = viewModel.getSimulationSpeed()
    }

    // 每日自动执行计划（合并重置与执行，防止重组重复触发）
    LaunchedEffect(gameDay) {
        viewModel.onNewDayStart()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandColors.background)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ==========================================
            // 1. 顶部时间栏（精简版）
            // ==========================================
            TopTimeBar(gameDay, gameHour)

            // ==========================================
            // 1.5 自动生活模式提示条
            // ==========================================
            val isWeekend = (gameDay % 7 == 0) || (gameDay % 7 == 6)
            val dayLabel = when (gameDay % 7) {
                1 -> "周一"
                2 -> "周二"
                3 -> "周三"
                4 -> "周四"
                5 -> "周五"
                6 -> "周六"
                else -> "周日"
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppDimens.paddingLarge, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isWeekend) "🌿 $dayLabel · 手动模式" else "⚙️ $dayLabel · 自动生活${if (autoLifeEnabled) "运行中" else "已暂停"}",
                    fontSize = 13.sp,
                    color = BrandColors.textMuted.copy(alpha = 0.7f)
                )

                // 自动生活迷你 Toggle + 流速快捷切换
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 自动生活一键切换
                    Text(
                        text = if (autoLifeEnabled) "⚙️" else "🌿",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { viewModel.toggleAutoLife() }
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                    SpeedButton(
                        label = "1x",
                        isSelected = currentSpeed == 1.0,
                        onClick = {
                            currentSpeed = 1.0
                            viewModel.setSimulationSpeed(1.0)
                        }
                    )
                    SpeedButton(
                        label = "2x",
                        isSelected = currentSpeed == 2.0,
                        onClick = {
                            currentSpeed = 2.0
                            viewModel.setSimulationSpeed(2.0)
                        }
                    )
                    SpeedButton(
                        label = "4x",
                        isSelected = currentSpeed == 4.0,
                        onClick = {
                            currentSpeed = 4.0
                            viewModel.setSimulationSpeed(4.0)
                        }
                    )
                }
            }

            // 人生阶段与婚恋状态（简洁展示）
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppDimens.paddingLarge, vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 人生阶段标签
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(BrandColors.primary.copy(alpha = 0.1f))
                        .padding(horizontal = AppDimens.paddingSmall, vertical = 3.dp)
                ) {
                    Text(
                        text = "${lifeStage.label} · ${playerAge}岁",
                        fontSize = 11.sp,
                        color = BrandColors.primary
                    )
                }
                // 婚恋状态标签
                val loveLabel = when (loveState.status) {
                    com.example.townapp.data.LoveStatus.SINGLE -> if (playerAge >= 45) "终身单身" else "单身"
                    com.example.townapp.data.LoveStatus.IN_LOVE -> "恋爱中"
                    com.example.townapp.data.LoveStatus.MARRIED -> "已婚${if (loveState.hasChildren) " · 有子女" else ""}"
                    com.example.townapp.data.LoveStatus.DIVORCED -> "离异"
                    com.example.townapp.data.LoveStatus.WIDOWED -> "丧偶"
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(BrandColors.surface)
                        .padding(horizontal = AppDimens.paddingSmall, vertical = 3.dp)
                ) {
                    Text(
                        text = loveLabel,
                        fontSize = 11.sp,
                        color = BrandColors.textSecondary
                    )
                }
                // 养老/退休标签（60岁+）
                if (playerAge >= 60) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(BrandColors.warning.copy(alpha = 0.1f))
                            .padding(horizontal = AppDimens.paddingSmall, vertical = 3.dp)
                    ) {
                        Text(
                            text = "养老金生活",
                            fontSize = 11.sp,
                            color = BrandColors.warning
                        )
                    }
                }
            }

            // 本周事件摘要卡片
            if (weeklyEventSummary.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.paddingLarge, vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (weeklyEventTier) {
                            com.example.townapp.ui.viewmodel.WeeklyEventTier.MUNDANE -> BrandColors.surface
                            com.example.townapp.ui.viewmodel.WeeklyEventTier.MEDIUM -> BrandColors.warning.copy(alpha = 0.08f)
                            com.example.townapp.ui.viewmodel.WeeklyEventTier.MAJOR -> BrandColors.health.copy(alpha = 0.08f)
                        }
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(
                                        when (weeklyEventTier) {
                                            com.example.townapp.ui.viewmodel.WeeklyEventTier.MUNDANE -> BrandColors.textMuted.copy(alpha = 0.15f)
                                            com.example.townapp.ui.viewmodel.WeeklyEventTier.MEDIUM -> BrandColors.warning.copy(alpha = 0.15f)
                                            com.example.townapp.ui.viewmodel.WeeklyEventTier.MAJOR -> BrandColors.health.copy(alpha = 0.15f)
                                        }
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = weeklyEventTier.label,
                                    fontSize = 10.sp,
                                    color = when (weeklyEventTier) {
                                        com.example.townapp.ui.viewmodel.WeeklyEventTier.MUNDANE -> BrandColors.textMuted
                                        com.example.townapp.ui.viewmodel.WeeklyEventTier.MEDIUM -> BrandColors.warning
                                        com.example.townapp.ui.viewmodel.WeeklyEventTier.MAJOR -> BrandColors.health
                                    }
                                )
                            }
                            Text(
                                text = weeklyEventTier.description,
                                fontSize = 10.sp,
                                color = BrandColors.textMuted
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = weeklyEventSummary,
                            fontSize = 12.sp,
                            color = BrandColors.textPrimary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // 今日自动开销摘要（仅工作日自动模式显示）
            if (autoLifeSummary.isNotEmpty() && !isWeekend) {
                Text(
                    text = autoLifeSummary,
                    fontSize = 11.sp,
                    color = BrandColors.textMuted.copy(alpha = 0.6f),
                    modifier = Modifier.padding(horizontal = AppDimens.paddingLarge, vertical = 2.dp)
                )
            }

            // ==========================================
            // 悬浮状态按钮 + 本周事件按钮
            // ==========================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // 本周事件悬浮按钮
                FloatingWeekEventsButton(
                    eventCount = weeklyEvents.size,
                    isExpanded = showWeekEvents,
                    onToggle = { showWeekEvents = !showWeekEvents }
                )
                
                // 状态悬浮按钮
                val autoLifeTier by viewModel.autoLifeTier.collectAsState()
                FloatingStatusButton(
                    body = body,
                    space = space,
                    isExpanded = showStatusPanel,
                    currentSpeed = currentSpeed,
                    autoLifeEnabled = autoLifeEnabled,
                    autoLifeTier = autoLifeTier,
                    onToggle = { showStatusPanel = !showStatusPanel },
                    onSpeedChange = { speed ->
                        viewModel.setSimulationSpeed(speed)
                        currentSpeed = speed
                    },
                    onAutoLifeToggle = { viewModel.toggleAutoLife() },
                    onTierChange = { viewModel.setAutoLifeTier(it) }
                )
            }
            
            // 本周事件展开面板
            if (showWeekEvents) {
                WeekEventsPanel(
                    weeklyEvents = weeklyEvents,
                    currentWeek = currentWeek,
                    onClose = { showWeekEvents = false }
                )
            }

            // ==========================================
            // 2. 中间场景描述 + 事件日志
            // ==========================================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = AppDimens.paddingXLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                SceneDescription(
                    hour = gameHour,
                    roomName = space.addressName,
                    light = space.light,
                    areaSqm = space.areaSqm
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                // 空间详情卡片（身份绑定后展示）
                currentBinding?.let { binding ->
                    SpaceDetailCard(space = space, binding = binding)
                    Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                }

                // 宠物对话卡片（用餐后触发，展示一次后自动清空）
                petDialogue?.let { dialogue ->
                    PetDialogueCard(
                        text = dialogue,
                        onDismiss = { viewModel.clearPetDialogue() }
                    )
                    Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                }

                // 事件日志区（空状态时完全隐藏，不占用空间）
                if (eventLog.isNotEmpty()) {
                    EventLogSection(eventLog)
                }

                // 本周消费预估卡片
                if (weeklyExpense.totalCost > 0) {
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                    // 疾病状态提示
                    if (currentIllness != IllnessType.NONE) {
                        val illnessLabel = when (currentIllness) {
                            IllnessType.ACUTE -> "急性病"
                            IllnessType.CHRONIC -> "慢性病"
                            else -> ""
                        }
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = BrandColors.warning.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(AppDimens.radiusSmall)
                        ) {
                            Text(
                                text = "当前状态：$illnessLabel，已计入本周医疗开销",
                                fontSize = 12.sp,
                                color = BrandColors.warning,
                                modifier = Modifier.padding(horizontal = AppDimens.paddingMedium, vertical = AppDimens.paddingSmall)
                            )
                        }
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                    }

                    WeeklyExpenseCard(
                        expense = weeklyExpense,
                        mode = shoppingMode
                    )
                }

                // 爱好随机事件提示（如发生则展示）
                lastHobbyEvent?.let { event ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = BrandColors.cardBackground
                        ),
                        shape = RoundedCornerShape(AppDimens.radiusSmall)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🎸",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(end = AppDimens.paddingSmall)
                            )
                            Text(
                                text = event,
                                fontSize = 12.sp,
                                color = BrandColors.textSecondary,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))

                // ==========================================
                // 周末手动精细操作面板（仅周末显示）
                // ==========================================
                if (isWeekend) {
                    TextButton(
                        onClick = { showWeekendPanels = !showWeekendPanels },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = BrandColors.primary.copy(alpha = 0.8f)
                        )
                    ) {
                        Text(
                            text = if (showWeekendPanels) "🔼 收起周末操作" else "🔽 展开周末精细操作（饮食 / 晚间活动）",
                            fontSize = 13.sp
                        )
                    }

                    if (showWeekendPanels) {
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = BrandColors.cardBackground
                            ),
                            shape = RoundedCornerShape(AppDimens.radiusMedium)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = "周末安排",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BrandColors.textPrimary,
                                    modifier = Modifier.padding(bottom = AppDimens.paddingSmall)
                                )

                                // 消费模式切换（周末可手动调整）
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "消费模式：${shoppingMode.label}",
                                        fontSize = 13.sp,
                                        color = BrandColors.textPrimary
                                    )
                                    Text(
                                        text = shoppingMode.description,
                                        fontSize = 11.sp,
                                        color = BrandColors.textMuted
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    ShoppingMode.entries.forEach { mode ->
                                        SpeedButton(
                                            label = mode.label,
                                            isSelected = shoppingMode == mode,
                                            onClick = { viewModel.setShoppingMode(mode) }
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                                // 医疗偏好切换（周末可手动调整）
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "医疗偏好：${medicalPreference.label}",
                                        fontSize = 13.sp,
                                        color = BrandColors.textPrimary
                                    )
                                    Text(
                                        text = medicalPreference.description,
                                        fontSize = 11.sp,
                                        color = BrandColors.textMuted
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    MedicalPreference.entries.forEach { pref ->
                                        SpeedButton(
                                            label = pref.label,
                                            isSelected = medicalPreference == pref,
                                            onClick = { viewModel.setMedicalPreference(pref) }
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                                // 爱好预算档位切换（周末可手动调整）
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "爱好预算：${hobbyBudgetTier.label}",
                                        fontSize = 13.sp,
                                        color = BrandColors.textPrimary
                                    )
                                    Text(
                                        text = hobbyBudgetTier.description,
                                        fontSize = 11.sp,
                                        color = BrandColors.textMuted
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    HobbyBudgetTier.entries.forEach { tier ->
                                        SpeedButton(
                                            label = tier.label,
                                            isSelected = hobbyBudgetTier == tier,
                                            onClick = { viewModel.setHobbyBudgetTier(tier) }
                                        )
                                    }
                                }
                                // 熟练度与性格标签（仅展示）
                                if (hobbyProficiency > 0) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "当前爱好：$currentHobby  ·  熟练度：$hobbyProficiency%${if (vanityTrait) "  ·  性格：愿为形象投入" else ""}",
                                        fontSize = 11.sp,
                                        color = BrandColors.textMuted
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                // 周末练习爱好按钮
                                TextButton(
                                    onClick = { viewModel.practiceHobby() },
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = BrandColors.primary.copy(alpha = 0.8f)
                                    )
                                ) {
                                    Text("🎸 练习爱好（提升熟练度）", fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                                // 社交预算与相亲门槛（周末可手动调整）
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "社交预算：${socialBudgetTier.label}",
                                        fontSize = 13.sp,
                                        color = BrandColors.textPrimary
                                    )
                                    Text(
                                        text = "本周社交活跃度：$weeklySocialScore",
                                        fontSize = 11.sp,
                                        color = if (weeklySocialScore >= 20) BrandColors.health.copy(alpha = 0.8f) else BrandColors.textMuted
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    HobbyBudgetTier.entries.forEach { tier ->
                                        SpeedButton(
                                            label = tier.label,
                                            isSelected = socialBudgetTier == tier,
                                            onClick = { viewModel.setSocialBudgetTier(tier) }
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (weeklySocialScore <= 0)
                                        "本周尚无社交活动，相亲候选池为空。"
                                    else if (weeklySocialScore < 20)
                                        "社交活跃度偏低，相亲机会较少。"
                                    else
                                        "社交渠道畅通，有机会结识新人。",
                                    fontSize = 11.sp,
                                    color = BrandColors.textMuted
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                // 周末主动参加社交活动按钮
                                TextButton(
                                    onClick = { viewModel.recordSocialActivity("friend_dinner") },
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = BrandColors.primary.copy(alpha = 0.8f)
                                    )
                                ) {
                                    Text("🍻 参加社交活动（提升相亲机会）", fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                                // 饮食计划面板（禁用内部滚动，依赖父级滚动）
                                DietPanel(
                                    currentPlan = dailyDietPlan,
                                    onPlanChange = { viewModel.setDailyDietPlan(it) },
                                    enableInternalScroll = false,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                                // 晚间活动面板（禁用内部滚动，依赖父级滚动）
                                WorkdayPanel(
                                    currentPlan = eveningPlan,
                                    onActivitiesSelected = { viewModel.setEveningActivities(it) },
                                    enableInternalScroll = false,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                                // 底部收起按钮
                                TextButton(
                                    onClick = { showWeekendPanels = false },
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = BrandColors.textMuted
                                    )
                                ) {
                                    Text("🔼 收起", fontSize = 12.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                // 底部辅助入口区（高频操作直接展示，低频收进"更多"）
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // 情绪出口
                    TextButton(
                        onClick = {
                            val messages = GentleTextProvider.comfortTapMessages()
                            comfortMessage = messages.random()
                            showComfortTap = true
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = BrandColors.textMuted.copy(alpha = 0.6f)
                        )
                    ) {
                        Text("🌙 今天有点累", fontSize = 13.sp)
                    }

                    // 职业选择入口：未开局显示人生路径，已开局显示跳槽弹窗
                    TextButton(
                        onClick = {
                            if (gameDay == 0) {
                                showLifePathDialog = true
                            } else {
                                showCareerDialog = true
                            }
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = BrandColors.primary.copy(alpha = 0.8f)
                        )
                    ) {
                        Text(
                            text = if (gameDay == 0) "💼 选择人生路径" else "💼 更换职业",
                            fontSize = 13.sp
                        )
                    }

                    // 认知觉醒入口
                    TextButton(
                        onClick = { onNavigate("cognitive") },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = BrandColors.primary.copy(alpha = 0.8f)
                        )
                    ) {
                        Text("🧠 认知觉醒", fontSize = 13.sp)
                    }

                    // 更多（收拢低频操作）
                    Box {
                        TextButton(
                            onClick = { showMoreMenu = true },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = BrandColors.textMuted.copy(alpha = 0.6f)
                            )
                        ) {
                            Text("⋮ 更多", fontSize = 13.sp)
                        }
                        DropdownMenu(
                            expanded = showMoreMenu,
                            onDismissRequest = { showMoreMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("😌 发呆") },
                                onClick = {
                                    viewModel.idle()
                                    showMoreMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("🏠 住房") },
                                onClick = {
                                    onNavigate("housing")
                                    showMoreMenu = false
                                }
                            )
                        }
                    }
                }

                // 周计划编辑按钮（仅编辑模式显示）
                if (isEditMode) {
                    Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                    TextButton(
                        onClick = { viewModel.showWeeklyPlanEditor() },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = BrandColors.primary
                        )
                    ) {
                        Text(
                            text = "📅 编辑周计划",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // ==========================================
            // 3. 底部操作栏（小镇不锁用户的手，永远可手动操作）
            // ==========================================
            val planHint = if (isEditMode) "📅 编辑模式" else if (todayPlanExecuted) "✓ 今日计划已执行" else ""
            BottomActionBar(
                onEat = { viewModel.showFoodPicker() },
                onRest = { viewModel.rest() },
                onGoOut = { viewModel.goOut() },
                onWork = { viewModel.work() },
                onStudy = { viewModel.study() },
                lockHint = planHint,
                isEatAuto = !isWeekend && autoLifeEnabled
            )


        }

        // ==========================================
        // 食物选择弹窗（底部弹出）
        // ==========================================
        if (showFoodPicker) {
            FoodPickerSheet(
                foodItems = foodItems,
                savings = space.currentSavings,
                foodEatCount = foodEatCount,
                onSelect = { viewModel.eatFood(it) },
                onDismiss = { viewModel.hideFoodPicker() }
            )
        }

        // 情绪轻出口弹层
        if (showComfortTap) {
            ComfortTapOverlay(
                message = comfortMessage,
                onClose = { showComfortTap = false },
                onTap = {
                    val messages = GentleTextProvider.comfortTapMessages()
                    comfortMessage = messages.random()
                }
            )
        }

        // 周简报弹窗
        if (showWeeklyBrief) {
            WeeklyBriefDialog(
                weeklyStats = weeklyStats,
                currentWeek = currentWeek,
                onDismiss = { viewModel.dismissWeeklyBrief() }
            )
        }

        // 周计划编辑弹窗
        if (showWeeklyPlanDialog) {
            WeeklyPlanDialog(
                viewModel = viewModel,
                weeklyPlan = weeklyPlan,
                currentDay = gameDay % 7,
                onDismiss = { viewModel.hideWeeklyPlanEditor() }
            )
        }

        // 职业选择弹窗（游戏中跳槽）
        if (showCareerDialog) {
            CareerChoiceDialog(
                recommendedPath = viewModel.getRecommendedCareerPath(),
                onCareerSelected = { careerId ->
                    viewModel.selectCareer(careerId)
                    showCareerDialog = false
                },
                onDismiss = { showCareerDialog = false }
            )
        }

        // 开局人生路径选择弹窗
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

        // 周事件抉择弹窗（中等/重大事件）
        val weekChoices = pendingWeekEventChoices
        if (weekChoices != null) {
            WeekEventChoiceDialog(
                eventSummary = weeklyEventSummary,
                choices = weekChoices,
                onChoiceSelected = { choice ->
                    viewModel.applyWeekEventChoice(choice)
                }
            )
        }
    }
}

// ============================================
// 顶部时间栏（精简版）
// ============================================
@Composable
private fun TopTimeBar(gameDay: Int, gameHour: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BrandColors.surface)
            .padding(horizontal = AppDimens.paddingXLarge, vertical = AppDimens.paddingSmall),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "第${gameDay}天",
            fontSize = 13.sp,
            color = BrandColors.textSecondary
        )
        Text(
            text = "${GentleTextProvider.timeEmoji(gameHour)} ${String.format("%02d", gameHour)}:00",
            fontSize = 13.sp,
            color = BrandColors.textSecondary
        )
    }
}

// ============================================
// 悬浮状态按钮与折叠面板
// ============================================
@Composable
private fun FloatingStatusButton(
    body: UserBodyState,
    space: UserSpaceState,
    isExpanded: Boolean,
    currentSpeed: Double,
    autoLifeEnabled: Boolean,
    autoLifeTier: AutoLifeTier,
    onToggle: () -> Unit,
    onSpeedChange: (Double) -> Unit,
    onAutoLifeToggle: () -> Unit,
    onTierChange: (AutoLifeTier) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // 悬浮按钮
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(horizontal = AppDimens.paddingXLarge, vertical = AppDimens.paddingSmall),
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(AppDimens.radiusMedium))
                    .background(BrandColors.surface)
                    .padding(horizontal = AppDimens.paddingMedium, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${body.energy}%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = BrandColors.textPrimary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isExpanded) "▼" else "▲",
                    fontSize = 10.sp,
                    color = BrandColors.textMuted
                )
            }
        }

        // 折叠面板
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrandColors.surface)
                    .padding(horizontal = AppDimens.paddingXLarge, vertical = AppDimens.paddingMedium)
            ) {
                // 状态条：饱腹感、精力、情绪
                StatusBar(
                    label = "饱腹",
                    value = body.satiety,
                    suffix = GentleTextProvider.satietyLabel(body.satiety),
                    color = StatusColors.progress
                )
                Spacer(modifier = Modifier.height(4.dp))
                StatusBar(
                    label = "精力",
                    value = body.energy,
                    suffix = GentleTextProvider.energyLabel(body.energy),
                    color = StatusColors.progress
                )
                Spacer(modifier = Modifier.height(4.dp))
                StatusBar(
                    label = "情绪",
                    value = body.mood,
                    suffix = GentleTextProvider.moodLabel(body.mood),
                    color = StatusColors.progress
                )

                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                // 存款行
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "存款",
                        fontSize = 12.sp,
                        color = BrandColors.textMuted
                    )
                    Text(
                        text = "${String.format("%.0f", space.currentSavings)}元",
                        fontSize = 12.sp,
                        color = BrandColors.textSecondary
                    )
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                // 自动生活开关
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "自动生活",
                        fontSize = 12.sp,
                        color = BrandColors.textMuted
                    )
                    Switch(
                        checked = autoLifeEnabled,
                        onCheckedChange = { onAutoLifeToggle() },
                        modifier = Modifier.scale(0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                // 自动生活档位选择（仅展开时显示）
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "生活标准",
                        fontSize = 12.sp,
                        color = BrandColors.textMuted
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        AutoLifeTier.entries.forEach { tier ->
                            SpeedButton(
                                label = tier.label,
                                isSelected = autoLifeTier == tier,
                                onClick = { onTierChange(tier) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                // 时间流速控制
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "流速",
                        fontSize = 12.sp,
                        color = BrandColors.textMuted
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        SpeedButton(
                            label = "1x",
                            isSelected = currentSpeed == 1.0,
                            onClick = { onSpeedChange(1.0) }
                        )
                        SpeedButton(
                            label = "2x",
                            isSelected = currentSpeed == 2.0,
                            onClick = { onSpeedChange(2.0) }
                        )
                        SpeedButton(
                            label = "4x",
                            isSelected = currentSpeed == 4.0,
                            onClick = { onSpeedChange(4.0) }
                        )
                    }
                }
            }
        }
    }
}

// ============================================
// 旧状态条（保留以兼容其他调用）
// ============================================
@Composable
private fun StatusBarSection(
    _body: UserBodyState,
    _space: UserSpaceState,
    _mental: UserMentalState,
    gameHour: Int,
    gameDay: Int
) {
    TopTimeBar(gameDay, gameHour)
}

// ============================================
// 单条状态条
// ============================================
@Composable
private fun StatusBar(
    label: String,
    value: Int,
    suffix: String,
    color: Color
) {
    val animatedProgress by animateFloatAsState(
        targetValue = value.coerceIn(0, 100) / 100f,
        animationSpec = tween(durationMillis = 800),
        label = "progress"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = BrandColors.textMuted,
            modifier = Modifier.width(32.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(color.copy(alpha = 0.15f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .clip(RoundedCornerShape(3.dp))
                    .background(color)
            )
        }

        Spacer(modifier = Modifier.width(AppDimens.paddingSmall))

        Text(
            text = suffix,
            fontSize = 11.sp,
            color = BrandColors.textSecondary,
            modifier = Modifier.width(56.dp)
        )
    }
}

// ============================================
// 时间流速按钮
// ============================================
@Composable
private fun SpeedButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(AppDimens.radiusSmall))
            .background(if (isSelected) BrandColors.primary else BrandColors.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else BrandColors.textSecondary
        )
    }
}

// ============================================
// 本周消费预估卡片（点到面 v2.0）
// ============================================
@Composable
private fun WeeklyExpenseCard(
    expense: WeeklyExpense,
    mode: ShoppingMode
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BrandColors.cardBackground
        ),
        shape = RoundedCornerShape(AppDimens.radiusMedium)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // 标题行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "本周预估开销",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandColors.textPrimary
                )
                Text(
                    text = "总预算 ¥${expense.totalBudget.toInt()}",
                    fontSize = 11.sp,
                    color = BrandColors.textMuted
                )
            }
            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            // 刚需 vs 溢价
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "刚需",
                        fontSize = 12.sp,
                        color = BrandColors.textMuted
                    )
                    Text(
                        text = "¥${expense.baseCost.toInt()}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandColors.textPrimary
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "溢价（${mode.label}）",
                        fontSize = 12.sp,
                        color = BrandColors.textMuted
                    )
                    Text(
                        text = "¥${expense.premiumCost.toInt()} (${expense.premiumRatio}%)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (expense.premiumRatio > 25) BrandColors.health.copy(alpha = 0.8f) else BrandColors.textPrimary
                    )
                }
            }

            // 五大板块分项
            if (expense.categoryBreakdown.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = BrandColors.surface, thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))
                val categoryLabels = mapOf(
                    "food" to "饮食", "clothing" to "穿戴", "medical" to "医药",
                    "commute" to "通勤", "hobby" to "爱好"
                )
                // 补齐到6项，确保两行各3个
                val items = expense.categoryBreakdown.toList().toMutableList()
                while (items.size < 6) {
                    items.add("" to 0.0)
                }
                items.chunked(3).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowItems.forEach { (cat, amount) ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (cat.isNotEmpty()) {
                                    Text(
                                        text = categoryLabels[cat] ?: cat,
                                        fontSize = 11.sp,
                                        color = BrandColors.textMuted
                                    )
                                    Text(
                                        text = "¥${amount.toInt()}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = BrandColors.textPrimary,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 本周采购清单
            if (expense.shoppingList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = BrandColors.surface, thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "本周采购清单",
                    fontSize = 12.sp,
                    color = BrandColors.textMuted,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                val gridItems = expense.shoppingList.chunked(3)
                gridItems.forEachIndexed { index, row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        row.forEach { item ->
                            ShoppingTag(name = item.name, isPremium = item.isPremium)
                        }
                        // 补齐空白
                        if (row.size < 3) {
                            repeat(3 - row.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                    if (index < gridItems.size - 1) {
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            Divider(color = BrandColors.surface, thickness = 1.dp)
            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "本周总计",
                    fontSize = 13.sp,
                    color = BrandColors.textMuted
                )
                Text(
                    text = "¥${expense.totalCost.toInt()}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandColors.primary
                )
            }
        }
    }
}

/** 采购清单小标签 */
@Composable
private fun ShoppingTag(name: String, isPremium: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(
                if (isPremium) BrandColors.warning.copy(alpha = 0.12f)
                else BrandColors.surface
            )
            .padding(horizontal = AppDimens.paddingSmall, vertical = 4.dp)
    ) {
        Text(
            text = name,
            fontSize = 11.sp,
            color = if (isPremium) BrandColors.warning else BrandColors.textSecondary
        )
    }
}

// ============================================
// 场景描述卡片
// ============================================
@Composable
private fun SceneDescription(
    hour: Int,
    roomName: String,
    light: Int,
    areaSqm: Int
) {
    val sceneText = GentleTextProvider.describeScene(hour, roomName, light, areaSqm)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BrandColors.cardBackground
        ),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = GentleTextProvider.timeEmoji(hour),
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            Text(
                text = sceneText,
                fontSize = 14.sp,
                color = BrandColors.textPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        }
    }
}

// ============================================
// 事件日志
// ============================================
@Composable
private fun EventLogSection(eventLog: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = AppDimens.paddingSmall)
    ) {
        eventLog.take(8).forEach { event ->
            Text(
                text = event,
                fontSize = 12.sp,
                color = BrandColors.textSecondary,
                lineHeight = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            )
        }
    }
}

// ============================================
// 空间详情卡片（v1.2 阶段二）
// 展示身份绑定后的居住空间信息
// ============================================
@Composable
private fun SpaceDetailCard(
    space: UserSpaceState,
    binding: LifePathBinding
) {
    val config = binding.spaceConfig
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = BrandColors.cardBackground),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // 标题行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = GentleTextProvider.spaceDetailTitle(space.addressName),
                    fontSize = 14.sp,
                    color = BrandColors.textPrimary
                )
                Text(
                    text = if (expanded) "收起 ▲" else "展开 ▼",
                    fontSize = 11.sp,
                    color = BrandColors.textMuted
                )
            }

            // 一句话描述
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = config.description,
                fontSize = 12.sp,
                color = BrandColors.textSecondary,
                lineHeight = 18.sp
            )

            // 展开后的详情
            if (expanded) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = BrandColors.textMuted.copy(alpha = 0.15f))
                Spacer(modifier = Modifier.height(10.dp))

                // 面积
                SpaceDetailRow(
                    label = "面积",
                    value = "${config.areaSqm}㎡",
                    description = GentleTextProvider.describeArea(config.areaSqm)
                )

                // 月租
                SpaceDetailRow(
                    label = "月租",
                    value = if (config.monthlyRent == 0.0) "无需付租" else "${String.format("%.0f", config.monthlyRent)}元",
                    description = GentleTextProvider.describeRent(config.monthlyRent, config.monthlyIncome)
                )

                // 月收入
                SpaceDetailRow(
                    label = "月收入",
                    value = "${String.format("%.0f", config.monthlyIncome)}元",
                    description = GentleTextProvider.describeIncome(config.monthlyIncome)
                )

                // 通勤
                SpaceDetailRow(
                    label = "通勤",
                    value = if (config.commuteMinutesOneWay == 0) "无通勤" else "${config.commuteMinutesOneWay}分钟",
                    description = GentleTextProvider.describeCommute(config.commuteMinutesOneWay)
                )

                // 日工作时长
                SpaceDetailRow(
                    label = "工作时长",
                    value = "${config.workHoursPerDay}小时/天",
                    description = GentleTextProvider.describeWorkHours(config.workHoursPerDay)
                )

                // 环境综合
                SpaceDetailRow(
                    label = "居住感受",
                    value = "",
                    description = GentleTextProvider.describeEnvironment(config.privacy, config.light, config.noise)
                )

                // 存款
                SpaceDetailRow(
                    label = "存款",
                    value = "${String.format("%.0f", config.currentSavings)}元",
                    description = GentleTextProvider.describeSavingsDetail(config.currentSavings, config.monthlyRent)
                )
            }
        }
    }
}

@Composable
private fun SpaceDetailRow(
    label: String,
    value: String,
    description: String
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row {
            Text(
                text = label,
                fontSize = 11.sp,
                color = BrandColors.textMuted,
                modifier = Modifier.width(60.dp)
            )
            if (value.isNotEmpty()) {
                Text(
                    text = value,
                    fontSize = 11.sp,
                    color = BrandColors.textPrimary
                )
            }
        }
        Text(
            text = description,
            fontSize = 11.sp,
            color = BrandColors.textSecondary.copy(alpha = 0.8f),
            lineHeight = 16.sp,
            modifier = Modifier.padding(start = 60.dp)
        )
    }
}

// ============================================
// 空状态提示（分首日/后续，更贴合当前节奏）
// ============================================
@Composable
private fun EmptyStateHint(gameDay: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = AppDimens.paddingXXLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isFirstDay = gameDay <= 1
        Text(
            text = if (isFirstDay) "🌱" else "🍃",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
        Text(
            text = if (isFirstDay) "小镇刚刚开始运转" else "今天很平静，没什么特别的事",
            fontSize = 13.sp,
            color = BrandColors.textMuted
        )
        Text(
            text = if (isFirstDay) "你可以点下面的按钮，或者什么也不做" else "平静本身也是一种状态",
            fontSize = 12.sp,
            color = BrandColors.textMuted.copy(alpha = 0.7f)
        )
        Text(
            text = if (isFirstDay) "它自己也会好好运转的" else "不用每天都有故事发生",
            fontSize = 12.sp,
            color = BrandColors.textMuted.copy(alpha = 0.5f)
        )
    }
}

// ============================================
// 底部操作栏
// ============================================
@Composable
private fun BottomActionBar(
    onEat: () -> Unit,
    onRest: () -> Unit,
    onGoOut: () -> Unit,
    onWork: () -> Unit,
    onStudy: () -> Unit,
    lockHint: String = "",
    isEatAuto: Boolean = false
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = BrandColors.surface,
        shadowElevation = AppDimens.cardElevation
    ) {
        Column {
            // 计划状态提示
            if (lockHint.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BrandColors.cardBackground)
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = lockHint,
                        fontSize = 11.sp,
                        color = BrandColors.textMuted
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomActionButton(
                    emoji = if (isEatAuto) "🤖" else "🍜",
                    label = if (isEatAuto) "自动" else "吃饭",
                    onClick = onEat,
                    enabled = !isEatAuto
                )
                BottomActionButton(
                    emoji = "🚶",
                    label = "出门",
                    onClick = onGoOut
                )
                BottomActionButton(
                    emoji = "😴",
                    label = "休息",
                    onClick = onRest
                )
                BottomActionButton(
                    emoji = "💼",
                    label = "工作",
                    onClick = onWork
                )
                BottomActionButton(
                    emoji = "📚",
                    label = "学习",
                    onClick = onStudy
                )
            }
        }
    }
}

@Composable
private fun BottomActionButton(
    emoji: String,
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = Modifier
            .clickable(enabled = enabled, onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = emoji,
            fontSize = 24.sp,
            color = if (enabled) Color.Unspecified else BrandColors.textMuted.copy(alpha = 0.4f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (enabled) BrandColors.textSecondary else BrandColors.textMuted.copy(alpha = 0.4f)
        )
    }
}

// ============================================
// 食物选择弹窗
// ============================================
@Composable
private fun FoodPickerSheet(
    foodItems: List<FoodItem>,
    savings: Double,
    foodEatCount: Map<String, Int>,
    onSelect: (FoodItem) -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .clickable(enabled = false) {}, // 阻止点击穿透
            shape = RoundedCornerShape(topStart = AppDimens.radiusLarge, topEnd = AppDimens.radiusLarge),
            colors = CardDefaults.cardColors(containerColor = BrandColors.surface)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // 标题栏
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "想吃什么？",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = BrandColors.textPrimary
                    )
                    Text(
                        text = "余额 ${String.format("%.0f", savings)}元",
                        fontSize = 12.sp,
                        color = BrandColors.textMuted
                    )
                }

                // 食物列表
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = AppDimens.paddingLarge)
                ) {
                    items(foodItems) { food ->
                        val cost = food.pricePer100g * food.typicalServing / 100.0
                        val canAfford = savings >= cost

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = canAfford) { onSelect(food) }
                                .padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = food.name,
                                    fontSize = 14.sp,
                                    color = if (canAfford) BrandColors.textPrimary else BrandColors.textMuted
                                )
                                val eatCount = foodEatCount[food.name] ?: 0
                                Text(
                                    text = if (eatCount > 0) {
                                        "${food.note}  ·  ${String.format("%.1f", food.pricePer100g)}元/100g  ·  已吃${eatCount}次"
                                    } else {
                                        "${food.note}  ·  ${String.format("%.1f", food.pricePer100g)}元/100g"
                                    },
                                    fontSize = 11.sp,
                                    color = BrandColors.textMuted
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = if (canAfford) "${String.format("%.1f", cost)}元" else "不够",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (canAfford) BrandColors.textSecondary else BrandColors.textMuted.copy(alpha = 0.5f)
                                )
                                Text(
                                    text = "${food.typicalServing}g",
                                    fontSize = 10.sp,
                                    color = BrandColors.textMuted
                                )
                            }
                        }
                        HorizontalDivider(color = BrandColors.divider)
                    }
                }
            }
        }
    }
}

// ============================================
// 情绪轻出口弹层 —— 点一下，获得一句温柔的话
// ============================================
@Composable
private fun ComfortTapOverlay(
    message: String,
    onClose: () -> Unit,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(32.dp)
                .clickable(onClick = onTap),
            shape = RoundedCornerShape(AppDimens.radiusXLarge),
            colors = CardDefaults.cardColors(containerColor = AppColors.CardBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🌱", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                Text(
                    text = message,
                    fontSize = 16.sp,
                    color = AppColors.TextPrimaryDark,
                    textAlign = TextAlign.Center,
                    lineHeight = 26.sp
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                Text(
                    text = "点一下，换一句",
                    fontSize = 11.sp,
                    color = BrandColors.textMuted.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                TextButton(onClick = onClose) {
                    Text(
                        text = "好了，我知道了",
                        fontSize = 12.sp,
                        color = BrandColors.textMuted
                    )
                }
            }
        }
    }
}

// ============================================
// 宠物对话卡片
// ============================================
@Composable
private fun PetDialogueCard(
    text: String,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.paddingMedium),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        colors = CardDefaults.cardColors(
            containerColor = BrandColors.cardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDismiss() }
                .padding(16.dp)
        ) {
            Text(
                text = "🐾",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            Text(
                text = text,
                fontSize = 14.sp,
                color = BrandColors.textPrimary,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            Text(
                text = "（轻触关闭）",
                fontSize = 11.sp,
                color = BrandColors.textMuted
            )
        }
    }
}



// ============================================
// 品牌色系统
// ============================================
private object BrandColors {
    val background = AppColors.Background
    val surface = AppColors.Surface
    val cardBackground = AppColors.CardBackground
    val textPrimary = AppColors.TextPrimaryDark
    val textSecondary = AppColors.TextSecondaryWarm
    val textMuted = AppColors.TextMuted
    val divider = AppColors.Divider
    val primary = AppColors.PrimaryWarm  // 温暖棕色
    val health = AppColors.Success       // 健康/正常状态
    val warning = AppColors.WarningOrange // 警告/溢价过高
}

private object StatusColors {
    val progress = AppColors.ProgressWarm  // 统一暖灰米色，不区分红/绿/黄
}

// ============================================
// 本周事件悬浮按钮
// ============================================
@Composable
private fun FloatingWeekEventsButton(
    eventCount: Int,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onToggle)
            .padding(horizontal = AppDimens.paddingXLarge, vertical = AppDimens.paddingSmall),
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(AppDimens.radiusMedium))
                .background(BrandColors.surface)
                .padding(horizontal = AppDimens.paddingMedium, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📋",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "本周${eventCount}件事",
                fontSize = 12.sp,
                color = BrandColors.textPrimary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (isExpanded) "▼" else "▲",
                fontSize = 10.sp,
                color = BrandColors.textMuted
            )
        }
    }
}

// ============================================
// 本周事件展开面板
// ============================================
@Composable
private fun WeekEventsPanel(
    weeklyEvents: List<String>,
    currentWeek: Int,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BrandColors.surface)
            .padding(horizontal = AppDimens.paddingXLarge, vertical = AppDimens.paddingMedium)
    ) {
        // 标题行
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "第${currentWeek}周记事",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = BrandColors.textPrimary
            )
            TextButton(onClick = onClose) {
                Text(
                    text = "收起",
                    fontSize = 12.sp,
                    color = BrandColors.textMuted
                )
            }
        }
        
        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
        
        // 事件列表 - 可滚动容器
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 280.dp)
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                if (weeklyEvents.isNotEmpty()) {
                    weeklyEvents.reversed().forEachIndexed { index, event ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "${weeklyEvents.size - index}.",
                                fontSize = 11.sp,
                                color = BrandColors.textMuted,
                                modifier = Modifier.width(24.dp)
                            )
                            Text(
                                text = event,
                                fontSize = 12.sp,
                                color = BrandColors.textSecondary,
                                lineHeight = 19.sp
                            )
                        }
                    }
                } else {
                    Text(
                        text = "本周还没有记录",
                        fontSize = 12.sp,
                        color = BrandColors.textMuted
                    )
                }
                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
            }
        }
    }
}

// ============================================
// 周简报弹窗
// ============================================
@Composable
private fun WeeklyBriefDialog(
    weeklyStats: com.example.townapp.domain.WeeklyStats,
    currentWeek: Int,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .clickable(enabled = false) {},
            shape = RoundedCornerShape(AppDimens.radiusLarge),
            colors = CardDefaults.cardColors(containerColor = BrandColors.surface)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // 标题
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "📅 第${currentWeek}周简报",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = BrandColors.textPrimary
                    )
                }
                
                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                
                // 周总结
                if (weeklyStats.summary.isNotEmpty()) {
                    Text(
                        text = weeklyStats.summary,
                        fontSize = 14.sp,
                        color = BrandColors.textPrimary,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                }
                
                // 统计数据
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
                ) {
                    StatRow("疲惫累积", "${String.format("%.1f", weeklyStats.totalFatigue)}%")
                    StatRow("平均焦虑", "${String.format("%.1f", weeklyStats.avgAnxiety)}%")
                    StatRow("食物开销", "${String.format("%.0f", weeklyStats.totalFoodCost)}元")
                    StatRow("熬夜天数", "${weeklyStats.stayUpLateDays}天")
                    StatRow("可用事件槽", "${weeklyStats.availableActionSlots}个")
                }
                
                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                
                // 关闭按钮
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandColors.primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(AppDimens.radiusSmall)
                    ) {
                        Text(text = "知道了", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = BrandColors.textMuted
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = BrandColors.textPrimary
        )
    }
}

// ============================================
// 周计划编辑弹窗
// ============================================
@Composable
private fun WeeklyPlanDialog(
    viewModel: TownViewModel,
    weeklyPlan: Map<Int, DailyPlan>,
    currentDay: Int,
    onDismiss: () -> Unit
) {
    val daysOfWeek = listOf(1 to "周一", 2 to "周二", 3 to "周三", 4 to "周四", 5 to "周五", 6 to "周六", 7 to "周日")
    var selectedDay by remember { mutableStateOf(currentDay) }
    
    // 获取当天计划或创建新计划
    val todayPlan = weeklyPlan[selectedDay] ?: DailyPlan(dayOfWeek = selectedDay)
    
    // 表单状态
    var breakfast by remember { mutableStateOf(todayPlan.breakfast) }
    var lunch by remember { mutableStateOf(todayPlan.lunch) }
    var dinner by remember { mutableStateOf(todayPlan.dinner) }
    var workHours by remember { mutableStateOf(todayPlan.workHours.toString()) }
    var studyHours by remember { mutableStateOf(todayPlan.studyHours.toString()) }
    var restHours by remember { mutableStateOf(todayPlan.restHours.toString()) }
    var goOut by remember { mutableStateOf(todayPlan.goOut) }
    var notes by remember { mutableStateOf(todayPlan.notes) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .clickable(enabled = false) {},
            shape = RoundedCornerShape(topStart = AppDimens.radiusXLarge, topEnd = AppDimens.radiusXLarge),
            colors = CardDefaults.cardColors(containerColor = BrandColors.surface)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // 标题栏
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📅 周计划",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = BrandColors.textPrimary
                    )
                    IconButton(onClick = onDismiss) {
                        Text(text = "✕", fontSize = 20.sp, color = BrandColors.textMuted)
                    }
                }
                
                // 星期选择标签
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = AppDimens.paddingLarge),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
                ) {
                    daysOfWeek.forEach { (day, label) ->
                        val isSelected = selectedDay == day
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(AppDimens.radiusSmall))
                                .background(if (isSelected) BrandColors.primary else BrandColors.cardBackground)
                                .clickable {
                                    selectedDay = day
                                    val newPlan = weeklyPlan[day] ?: DailyPlan(dayOfWeek = day)
                                    breakfast = newPlan.breakfast
                                    lunch = newPlan.lunch
                                    dinner = newPlan.dinner
                                    workHours = newPlan.workHours.toString()
                                    studyHours = newPlan.studyHours.toString()
                                    restHours = newPlan.restHours.toString()
                                    goOut = newPlan.goOut
                                    notes = newPlan.notes
                                }
                                .padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingSmall)
                        ) {
                            Text(
                                text = label,
                                fontSize = 13.sp,
                                color = if (isSelected) Color.White else BrandColors.textPrimary
                            )
                        }
                    }
                }
                
                // 内容区域
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(AppDimens.paddingLarge)
                ) {
                    // 快捷操作按钮
                    item {
                        Text(
                            text = "快捷操作",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = BrandColors.textPrimary,
                            modifier = Modifier.padding(bottom = AppDimens.paddingSmall)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            QuickActionButton("🍜", "吃饭", onClick = { viewModel.showFoodPicker() })
                            QuickActionButton("🚶", "出门", onClick = { viewModel.goOut() })
                            QuickActionButton("😴", "休息", onClick = { viewModel.rest() })
                            QuickActionButton("💼", "工作", onClick = { viewModel.work() })
                            QuickActionButton("📚", "学习", onClick = { viewModel.study() })
                        }
                        Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                    }
                    
                    // 三餐设置
                    item {
                        Text(
                            text = "三餐计划",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = BrandColors.textPrimary,
                            modifier = Modifier.padding(bottom = AppDimens.paddingSmall)
                        )
                        PlanInput("早餐", breakfast, onValueChange = { breakfast = it })
                        PlanInput("午餐", lunch, onValueChange = { lunch = it })
                        PlanInput("晚餐", dinner, onValueChange = { dinner = it })
                        Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                    }
                    
                    // 时间安排
                    item {
                        Text(
                            text = "时间安排",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = BrandColors.textPrimary,
                            modifier = Modifier.padding(bottom = AppDimens.paddingSmall)
                        )
                        PlanInput("工作时长(小时)", workHours, onValueChange = { workHours = it })
                        PlanInput("学习时长(小时)", studyHours, onValueChange = { studyHours = it })
                        PlanInput("休息时长(小时)", restHours, onValueChange = { restHours = it })
                        Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                    }
                    
                    // 外出设置
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "外出",
                                fontSize = 14.sp,
                                color = BrandColors.textPrimary
                            )
                            Switch(
                                checked = goOut,
                                onCheckedChange = { goOut = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = BrandColors.primary,
                                    checkedTrackColor = BrandColors.primary.copy(alpha = 0.3f)
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                    }
                    
                    // 备注
                    item {
                        Text(
                            text = "备注",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = BrandColors.textPrimary,
                            modifier = Modifier.padding(bottom = AppDimens.paddingSmall)
                        )
                        TextField(
                            value = notes,
                            onValueChange = { notes = it },
                            placeholder = { Text("写点什么...", color = BrandColors.textMuted) },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(fontSize = 14.sp, color = BrandColors.textPrimary),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = BrandColors.cardBackground,
                                unfocusedContainerColor = BrandColors.cardBackground,
                                focusedIndicatorColor = BrandColors.primary,
                                unfocusedIndicatorColor = BrandColors.divider
                            )
                        )
                        Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                    }
                }
                
                // 底部保存按钮
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            // 保存当前编辑的计划
                            saveCurrentPlan(viewModel, selectedDay, breakfast, lunch, dinner, 
                                workHours.toDoubleOrNull() ?: 0.0, 
                                studyHours.toDoubleOrNull() ?: 0.0, 
                                restHours.toDoubleOrNull() ?: 8.0, 
                                goOut, notes)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandColors.primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(AppDimens.radiusSmall)
                    ) {
                        Text(text = "保存计划", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

// ============================================
// 周事件抉择弹窗（中等/重大事件）
// ============================================
@Composable
private fun WeekEventChoiceDialog(
    eventSummary: String,
    choices: List<WeekEventChoice>,
    onChoiceSelected: (WeekEventChoice) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .clickable(enabled = false) {},
            shape = RoundedCornerShape(AppDimens.radiusLarge),
            colors = CardDefaults.cardColors(containerColor = BrandColors.surface)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "这周发生了一些事",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = BrandColors.textPrimary
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                Text(
                    text = eventSummary,
                    fontSize = 14.sp,
                    color = BrandColors.textPrimary,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                Text(
                    text = "你会怎么选？",
                    fontSize = 13.sp,
                    color = BrandColors.textMuted
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                choices.forEach { choice ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onChoiceSelected(choice) },
                        colors = CardDefaults.cardColors(containerColor = BrandColors.cardBackground),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = choice.label,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = BrandColors.textPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = choice.description,
                                fontSize = 11.sp,
                                color = BrandColors.textSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionButton(emoji: String, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = emoji, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 11.sp, color = BrandColors.textSecondary)
    }
}

@Composable
private fun PlanInput(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = BrandColors.textMuted,
            modifier = Modifier.width(100.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(fontSize = 14.sp, color = BrandColors.textPrimary),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = BrandColors.cardBackground,
                unfocusedContainerColor = BrandColors.cardBackground,
                focusedIndicatorColor = BrandColors.primary,
                unfocusedIndicatorColor = BrandColors.divider
            )
        )
    }
}

private fun saveCurrentPlan(
    viewModel: TownViewModel,
    dayOfWeek: Int,
    breakfast: String,
    lunch: String,
    dinner: String,
    workHours: Double,
    studyHours: Double,
    restHours: Double,
    goOut: Boolean,
    notes: String
) {
    viewModel.saveDailyPlan(dayOfWeek, DailyPlan(
        dayOfWeek = dayOfWeek,
        breakfast = breakfast,
        lunch = lunch,
        dinner = dinner,
        workHours = workHours,
        studyHours = studyHours,
        restHours = restHours,
        goOut = goOut,
        notes = notes
    ))
}