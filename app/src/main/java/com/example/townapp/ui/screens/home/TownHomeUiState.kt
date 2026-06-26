package com.example.townapp.ui.screens.home

import androidx.compose.ui.graphics.Color
import com.example.townapp.data.*
import com.example.townapp.data.database.entity.UserBodyState
import com.example.townapp.data.database.entity.UserSpaceState
import com.example.townapp.domain.WeeklyStats
import com.example.townapp.domain.consumption.ConsumptionSystem
import com.example.townapp.ui.viewmodel.*

data class HomeTimeInfo(
    val gameDay: Int,
    val gameHour: Int,
    val currentWeek: Int,
    val isNight: Boolean,
    val isWeekend: Boolean,
    val dayOfWeekLabel: String,
    val dayOfWeekIndex: Int,
    val modeStatusText: String,
    val autoLifeIcon: String
)

data class HomePlayerInfo(
    val playerAge: Int,
    val lifeStage: LifeStage,
    val lifeStageLabel: String,
    val loveStatusLabel: String,
    val loveStatus: LoveStatus,
    val hasChildren: Boolean,
    val isRetired: Boolean
)

data class HomeWeeklyEventCard(
    val isVisible: Boolean,
    val summary: String,
    val tier: WeeklyEventTier,
    val tierLabel: String,
    val tierDescription: String,
    val containerColor: Color,
    val tagColor: Color,
    val tagTextColor: Color,
    val pendingChoices: List<WeekEventChoice>?,
    val eventCount: Int
)

data class HomeStatusInfo(
    val satiety: Int,
    val energy: Int,
    val health: Int,
    val mood: Int,
    val savingsText: String,
    val autoLifeEnabled: Boolean,
    val lockHint: String,
    val isEatAuto: Boolean
)

data class HomeSceneInfo(
    val roomName: String,
    val light: Int,
    val areaSqm: Int,
    val timeGreeting: String
)

data class HomeActionBarState(
    val showFoodPicker: Boolean,
    val isEditMode: Boolean,
    val todayPlanExecuted: Boolean,
    val planHint: String
)

data class HomeWeekendPanel(
    val isVisible: Boolean,
    val shoppingMode: ShoppingMode,
    val medicalPreference: MedicalPreference,
    val hobbyBudgetTier: HobbyBudgetTier,
    val socialBudgetTier: HobbyBudgetTier,
    val currentHobby: String,
    val hobbyProficiency: Int,
    val vanityTrait: Boolean,
    val weeklySocialScore: Int,
    val socialStatusText: String,
    val socialScoreColor: Color
)

data class HomeDialogsState(
    val showFoodPicker: Boolean = false,
    val showWeeklyBrief: Boolean = false,
    val showWeeklyPlanDialog: Boolean = false,
    val showCareerDialog: Boolean = false,
    val showLifePathDialog: Boolean = false,
    val pendingWeekEventChoices: List<WeekEventChoice>? = null
)

data class TownHomeUiState(
    val time: HomeTimeInfo,
    val player: HomePlayerInfo,
    val body: UserBodyState,
    val space: UserSpaceState,
    val status: HomeStatusInfo,
    val scene: HomeSceneInfo,
    val weeklyEvent: HomeWeeklyEventCard,
    val weekendPanel: HomeWeekendPanel,
    val dialogs: HomeDialogsState,
    val eventLog: List<String>,
    val autoLifeSummary: String,
    val showAutoLifeSummary: Boolean,
    val weeklyExpense: WeeklyExpense,
    val currentIllness: IllnessType,
    val illnessLabel: String,
    val showIllnessHint: Boolean,
    val lastHobbyEvent: String?,
    val petDialogue: String?,
    val foodItems: List<FoodItem>,
    val foodEatCount: Map<String, Int>,
    val weeklyEvents: List<String>,
    val isEditMode: Boolean,
    val weeklyStats: WeeklyStats,
    val weeklyPlan: Map<Int, DailyPlan>,
    val dailyDietPlan: DietSystem.DailyDietPlan,
    val eveningPlan: WorkdayPlanner.EveningPlan?,
    val autoLifeTier: AutoLifeTier,
    val currentSpeed: Double,
    val showLifePathButton: Boolean,
    val careerButtonText: String,
    val consumptionScore: ConsumptionSystem.OrientationScore
)
