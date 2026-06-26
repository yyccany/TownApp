package com.example.townapp.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.townapp.data.*
import com.example.townapp.ui.theme.TownDesignTokens
import com.example.townapp.ui.viewmodel.AutoLifeTier
import com.example.townapp.ui.viewmodel.HomeViewModel
import com.example.townapp.ui.viewmodel.IllnessType
import com.example.townapp.ui.viewmodel.WeeklyEventTier

@Composable
fun rememberTownHomeUiState(
    viewModel: HomeViewModel,
    initialShowLifePathDialog: Boolean
): TownHomeUiState {
    val body by viewModel.bodyState.collectAsState()
    val space by viewModel.spaceState.collectAsState()
    val gameHour by viewModel.gameHour.collectAsState()
    val gameDay by viewModel.gameDay.collectAsState()
    val eventLog by viewModel.eventLog.collectAsState()
    val foodItems by viewModel.foodItems.collectAsState()
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
    val weeklySocialScore by viewModel.weeklySocialScore.collectAsState()
    val socialBudgetTier by viewModel.socialBudgetTier.collectAsState()
    val weeklyEventTier by viewModel.weeklyEventTier.collectAsState()
    val weeklyEventSummary by viewModel.weeklyEventSummary.collectAsState()
    val pendingWeekEventChoices by viewModel.pendingWeekEventChoices.collectAsState()
    val showFoodPicker by viewModel.showFoodPicker.collectAsState()
    val petDialogue by viewModel.petDialogue.collectAsState()
    val autoLifeTier by viewModel.autoLifeTier.collectAsState()
    val consumptionScore by viewModel.consumptionScore.collectAsState()

    val lifeStage = remember(playerAge) { LifeStage.fromAge(playerAge) }
    val isNight = gameHour >= 22 || gameHour < 6
    val dayOfWeekIndex = if (gameDay == 0) 1 else ((gameDay - 1) % 7) + 1
    val isWeekend = dayOfWeekIndex == 6 || dayOfWeekIndex == 0 || (gameDay > 0 && (gameDay % 7 == 0 || gameDay % 7 == 6))
    val dayLabel = when {
        gameDay == 0 -> "启程日"
        dayOfWeekIndex == 1 -> "周一"
        dayOfWeekIndex == 2 -> "周二"
        dayOfWeekIndex == 3 -> "周三"
        dayOfWeekIndex == 4 -> "周四"
        dayOfWeekIndex == 5 -> "周五"
        dayOfWeekIndex == 6 -> "周六"
        else -> "周日"
    }

    val timeGreeting = when {
        gameHour in 6..8 -> "早安"
        gameHour in 9..11 -> "上午好"
        gameHour in 12..13 -> "午安"
        gameHour in 14..17 -> "下午好"
        gameHour in 18..21 -> "晚上好"
        else -> "夜深了"
    }

    val loveLabel = when (loveState.status) {
        LoveStatus.SINGLE -> if (playerAge >= 45) "终身单身" else "单身"
        LoveStatus.IN_LOVE -> "恋爱中"
        LoveStatus.MARRIED -> "已婚${if (loveState.hasChildren) " · 有子女" else ""}"
        LoveStatus.DIVORCED -> "离异"
        LoveStatus.WIDOWED -> "丧偶"
    }

    val isRetired = playerAge >= 60

    val modeStatusText = if (isWeekend) {
        "🌿 $dayLabel · 手动模式"
    } else {
        "⚙️ $dayLabel · 自动生活${if (autoLifeEnabled) "运行中" else "已暂停"}"
    }

    val autoLifeIcon = if (autoLifeEnabled) "⚙️" else "🌿"

    val (eventContainerColor, eventTagColor, eventTagTextColor) = when (weeklyEventTier) {
        WeeklyEventTier.MUNDANE -> Triple(
            TownDesignTokens.Colors.bgSurface,
            TownDesignTokens.Colors.textTertiary.copy(alpha = 0.15f),
            TownDesignTokens.Colors.textTertiary
        )
        WeeklyEventTier.MEDIUM -> Triple(
            TownDesignTokens.Colors.warning.copy(alpha = 0.08f),
            TownDesignTokens.Colors.warning.copy(alpha = 0.15f),
            TownDesignTokens.Colors.warning
        )
        WeeklyEventTier.MAJOR -> Triple(
            TownDesignTokens.Colors.success.copy(alpha = 0.08f),
            TownDesignTokens.Colors.success.copy(alpha = 0.15f),
            TownDesignTokens.Colors.success
        )
    }

    val illnessLabel = when (currentIllness) {
        IllnessType.ACUTE -> "急性病"
        IllnessType.CHRONIC -> "慢性病"
        else -> ""
    }
    val showIllnessHint = currentIllness != IllnessType.NONE

    val planHint = when {
        isEditMode -> "📅 编辑模式"
        todayPlanExecuted -> "✓ 今日计划已执行"
        else -> ""
    }

    val socialStatusText = when {
        weeklySocialScore <= 0 -> "本周尚无社交活动，相亲候选池为空。"
        weeklySocialScore < 20 -> "社交活跃度偏低，相亲机会较少。"
        else -> "社交渠道畅通，有机会结识新人。"
    }

    val socialScoreColor = if (weeklySocialScore >= 20) {
        TownDesignTokens.Colors.success.copy(alpha = 0.8f)
    } else {
        TownDesignTokens.Colors.textTertiary
    }

    val isEatAuto = !isWeekend && autoLifeEnabled
    val showAutoLifeSummary = autoLifeSummary.isNotEmpty() && !isWeekend

    return TownHomeUiState(
        time = HomeTimeInfo(
            gameDay = gameDay,
            gameHour = gameHour,
            currentWeek = currentWeek,
            isNight = isNight,
            isWeekend = isWeekend,
            dayOfWeekLabel = dayLabel,
            dayOfWeekIndex = dayOfWeekIndex,
            modeStatusText = modeStatusText,
            autoLifeIcon = autoLifeIcon
        ),
        player = HomePlayerInfo(
            playerAge = playerAge,
            lifeStage = lifeStage,
            lifeStageLabel = "${lifeStage.label} · ${playerAge}岁",
            loveStatusLabel = loveLabel,
            loveStatus = loveState.status,
            hasChildren = loveState.hasChildren,
            isRetired = isRetired
        ),
        body = body,
        space = space,
        status = HomeStatusInfo(
            satiety = body.satiety,
            energy = body.energy,
            health = body.healthScore,
            mood = body.mood,
            savingsText = "¥%.0f".format(space.currentSavings),
            autoLifeEnabled = autoLifeEnabled,
            lockHint = planHint,
            isEatAuto = isEatAuto
        ),
        scene = HomeSceneInfo(
            roomName = space.addressName,
            light = space.light,
            areaSqm = space.areaSqm,
            timeGreeting = timeGreeting
        ),
        weeklyEvent = HomeWeeklyEventCard(
            isVisible = weeklyEventSummary.isNotEmpty(),
            summary = weeklyEventSummary,
            tier = weeklyEventTier,
            tierLabel = weeklyEventTier.label,
            tierDescription = weeklyEventTier.description,
            containerColor = eventContainerColor,
            tagColor = eventTagColor,
            tagTextColor = eventTagTextColor,
            pendingChoices = pendingWeekEventChoices,
            eventCount = weeklyEvents.size
        ),
        weekendPanel = HomeWeekendPanel(
            isVisible = isWeekend,
            shoppingMode = shoppingMode,
            medicalPreference = medicalPreference,
            hobbyBudgetTier = hobbyBudgetTier,
            socialBudgetTier = socialBudgetTier,
            currentHobby = currentHobby,
            hobbyProficiency = hobbyProficiency,
            vanityTrait = vanityTrait,
            weeklySocialScore = weeklySocialScore,
            socialStatusText = socialStatusText,
            socialScoreColor = socialScoreColor
        ),
        dialogs = HomeDialogsState(
            showFoodPicker = showFoodPicker,
            showWeeklyBrief = showWeeklyBrief,
            showWeeklyPlanDialog = showWeeklyPlanDialog,
            showCareerDialog = false,
            showLifePathDialog = initialShowLifePathDialog,
            pendingWeekEventChoices = pendingWeekEventChoices
        ),
        eventLog = eventLog,
        autoLifeSummary = autoLifeSummary,
        showAutoLifeSummary = showAutoLifeSummary,
        weeklyExpense = weeklyExpense,
        currentIllness = currentIllness,
        illnessLabel = illnessLabel,
        showIllnessHint = showIllnessHint,
        lastHobbyEvent = lastHobbyEvent,
        petDialogue = petDialogue,
        foodItems = foodItems,
        foodEatCount = foodEatCount,
        weeklyEvents = weeklyEvents,
        isEditMode = isEditMode,
        weeklyStats = weeklyStats,
        weeklyPlan = weeklyPlan,
        dailyDietPlan = dailyDietPlan,
        eveningPlan = eveningPlan,
        autoLifeTier = autoLifeTier,
        currentSpeed = 1.0,
        showLifePathButton = gameDay == 0,
        careerButtonText = if (gameDay == 0) "💼 选择人生路径" else "💼 更换职业",
        consumptionScore = consumptionScore
    )
}
