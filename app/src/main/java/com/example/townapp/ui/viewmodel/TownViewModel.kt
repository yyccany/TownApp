package com.example.townapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import com.example.townapp.business.*
import com.example.townapp.data.*
import com.example.townapp.data.cognition.NormalConversationQuotes
import com.example.townapp.data.cognition.ConversationQuote
import com.example.townapp.data.cognition.ConversationType
import com.example.townapp.data.database.TownDatabase
import com.example.townapp.data.database.entity.FeedingRecordEntity
import com.example.townapp.data.database.entity.LifeEventLogEntity
import com.example.townapp.data.database.entity.LifePathType
import com.example.townapp.data.database.entity.ResidentEntity
import com.example.townapp.data.database.entity.UserEntity
import com.example.townapp.data.database.entity.UserSpaceState
import com.example.townapp.data.database.entity.toEntity
import com.example.townapp.data.database.entity.toResidentHealth
import com.example.townapp.data.model.*
import com.example.townapp.data.repository.IdiomExploreManager
import com.example.townapp.data.repository.IdiomRepository
import com.example.townapp.data.repository.IdiomSceneMapper
import com.example.townapp.data.cognition.CognitionReflection
import com.example.townapp.data.cognition.CognitionReflectionLibrary
import com.example.townapp.data.cognition.ReflectionChoice
import com.example.townapp.data.repository.GameSaveRepository
import com.example.townapp.data.repository.UnifiedFoodRepository
import com.example.townapp.domain.ClothingBusiness
import com.example.townapp.feature.food.FoodBusiness
import com.example.townapp.domain.SalaryCalculator
import com.example.townapp.domain.engine.SimulationEngine
import com.example.townapp.domain.usecase.SimulationControlUseCase
import com.example.townapp.data.Recipe
import com.example.townapp.data.RecipeIngredient
import com.example.townapp.data.OutfitSet
import com.example.townapp.data.OutfitScene
import com.example.townapp.data.BiologicalSex
import com.example.townapp.data.LeisureEvent
import com.example.townapp.data.LeisureCategory
import com.example.townapp.data.LeisureLibrary
import com.example.townapp.data.LeisureTier
import com.example.townapp.data.Season
import com.example.townapp.data.FabricType
import com.example.townapp.data.FabricRules
import com.example.townapp.data.ClimateState
import com.example.townapp.data.PublicEvent
import com.example.townapp.data.PublicEventLibrary
import com.example.townapp.data.PublicEventType
import com.example.townapp.domain.OutfitSceneManager
import com.example.townapp.domain.LeisureManager
import com.example.townapp.domain.LeisureManager.LeisureResult
import com.example.townapp.domain.PublicEventScheduler
import com.example.townapp.domain.PublicEventScheduler.PublicEventResult
import com.example.townapp.domain.PublicEventScheduler.FanStyle
import com.example.townapp.domain.ClothingMaintenance
import com.example.townapp.domain.TimeEngine
import com.example.townapp.domain.TimeTickEngine
import com.example.townapp.domain.TimeState
import com.example.townapp.domain.TickResult
import com.example.townapp.domain.DailySettlement
import com.example.townapp.domain.DailyStats
import com.example.townapp.domain.WeeklySettlement
import com.example.townapp.domain.WeeklyStats
import com.example.townapp.domain.MonthlySettlement
import com.example.townapp.domain.MonthlyStats
import com.example.townapp.domain.EventCategory
import com.example.townapp.domain.DecisionPreferenceEngine
import com.example.townapp.domain.PetManager
import com.example.townapp.data.LifeStage
import com.example.townapp.data.LifeStageRules
import com.example.townapp.data.ClothingWearState
import com.example.townapp.data.WearSlot
import com.example.townapp.data.WearSlotState
import com.example.townapp.data.ShoeBreathability
import com.example.townapp.data.ClothingQuality
import com.example.townapp.data.ClothingCleanliness
import com.example.townapp.data.ClothingWarmth
import com.example.townapp.data.FootCondition
import com.example.townapp.data.SkinCondition
import com.example.townapp.data.ClothingHealthEvents
import com.example.townapp.data.ClothingSocialEvents
import com.example.townapp.data.WeatherClothingLink
import com.example.townapp.data.LaundrySystem
import com.example.townapp.data.ChildhoodTraumaState
import com.example.townapp.data.TraumaGenerator
import com.example.townapp.data.TraumaEntry
import com.example.townapp.data.TraumaType
import com.example.townapp.data.TraumaCategory
import com.example.townapp.data.PastSelfDialogueEvent
import com.example.townapp.data.PastSelfDialogueChoice
import com.example.townapp.data.NutritionLevel
import com.example.townapp.data.DiseaseType
import com.example.townapp.data.TreatmentRoute
import com.example.townapp.data.DrugTier
import com.example.townapp.data.ChildhoodClothingCompensation
import com.example.townapp.data.PsychDrain
import com.example.townapp.feature.food.CuisineFood
import com.example.townapp.feature.food.CuisineFoodRepository
import com.example.townapp.feature.food.FeedingResult
import com.example.townapp.feature.food.ResidentFeedingSystem
import com.example.townapp.feature.food.ResidentHealth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** 每日计划数据类 */
data class DailyPlan(
    val dayOfWeek: Int,  // 1-7 周一到周日
    val breakfast: String = "",
    val lunch: String = "",
    val dinner: String = "",
    val workHours: Double = 0.0,
    val studyHours: Double = 0.0,
    val restHours: Double = 8.0,
    val goOut: Boolean = false,
    val notes: String = ""
)

/** 自动生活标准档位 */
enum class AutoLifeTier(val label: String, val dailyFoodCost: Double, val dailyLivingCost: Double) {
    SIMPLE("简朴", 18.0, 10.0),
    NORMAL("普通", 40.0, 15.0),
    COMFORTABLE("宽裕", 65.0, 20.0)
}

/** 消费模式（刚需/均衡/溢价） */
enum class ShoppingMode(val label: String, val description: String) {
    SURVIVAL("刚需", "只买生存必需品，拒绝溢价"),
    BALANCED("均衡", "基础生活+少量体面消费"),
    PREMIUM("溢价", "愿意为形象支付额外成本")
}

/** 爱好预算档位 */
enum class HobbyBudgetTier(val label: String, val description: String, val weeklyBaseCost: Double) {
    MINIMAL("最低", "免费活动为主，维持最低限度", 0.0),
    BALANCED("常规", "稳定投入爱好预算，适度娱乐", 80.0),
    PREMIUM("高端", "品质器材、私教课、圈层活动", 300.0)
}

/** 爱好随机事件类型 */
enum class HobbyEventType {
    POSITIVE,   // 正向：演出邀约、技能变现
    NEUTRAL,    // 中性：器材损耗、正常维护
    TEMPTATION  // 诱惑：高端课程广告、圈层消费
}

/** 采购物品项（点到面模式：只记录品类，不设单品固定价） */
data class ShoppingListItem(
    val category: String,     // 板块：food / clothing / medical / commute / hobby
    val name: String,         // 品类名称
    val isPremium: Boolean = false  // 是否为溢价/面子消费
)

/** 每周开销汇总（点到面反向模式） */
data class WeeklyExpense(
    val totalBudget: Double,          // 本周总预算（由档位决定）
    val baseCost: Double,             // 基础刚需（饮食+住房+通勤+基础穿戴+基础医疗）
    val premiumCost: Double,          // 面子溢价（网红餐、名牌、高端爱好、滋补品）
    val totalCost: Double,            // 实际总花费（≈ totalBudget，在区间内）
    val premiumRatio: Int,            // 溢价占比 %
    val categoryBreakdown: Map<String, Double> = emptyMap(),  // 五大板块分项金额
    val shoppingList: List<ShoppingListItem> = emptyList()    // 本周随机采购清单
)

/** 本周事件层级 */
enum class WeeklyEventTier(val label: String, val description: String) {
    MUNDANE("平淡日常", "这周没什么特别，生活如常流淌"),
    MEDIUM("生活琐事", "一件小事打破了常规的平静"),
    MAJOR("人生节点", "一个抉择，可能改变往后数年")
}

/** 周事件选项 */
data class WeekEventChoice(
    val id: String,
    val label: String,           // 选项文案
    val description: String,     // 结果描述
    val savingsDelta: Double = 0.0,      // 存款变化
    val healthDelta: Int = 0,            // 健康变化
    val moodDelta: Int = 0,              // 心情变化
    val socialScoreDelta: Int = 0,       // 社交活跃度变化
    val careerImpact: String = ""        // 职业影响描述
)

/** 医疗偏好（用药观念） */
enum class MedicalPreference(val label: String, val description: String) {
    MODERN("现代派", "优先西药，见效快"),
    TRADITIONAL("传统保守派", "中药优先，愿为滋补药材付溢价"),
    PRAGMATIC("务实中立派", "急症西药，调理中药，只买性价比")
}

/** 疾病类型 */
enum class IllnessType {
    NONE, ACUTE, CHRONIC
}

/** 药品类型 */
enum class MedicineType {
    WESTERN, CHINESE, MIXED
}

class TownViewModel(
    private val gameSaveRepository: GameSaveRepository? = null
) : ViewModel() {

    private val simulationControlUseCase = SimulationControlUseCase()

    // Factory to create TownViewModel
    class Factory(private val gameSaveRepository: GameSaveRepository? = null) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TownViewModel(gameSaveRepository) as T
        }
    }

    /**
     * 一键重置：清空所有内存数据，恢复默认值
     * 用于后台隐藏面板的重置功能
     */
    fun resetAllData() {
        // 重置设置模块
        _settings.value = GlobalSettings()
        _hourlySalary.value = ""

        // 重置天气系统
        _weatherState.value = WeatherState.SUNNY
        _awakeningLevel.value = 1
        _totalAwakeningPoints.value = 0

        // 重置食物模块
        _foodItems.value = emptyList()
        _selectedFoodId.value = null
        _showAddFoodDialog.value = false

        // 重置衣物模块
        _clothingItems.value = emptyList()
        _selectedClothingId.value = null
        _showAddClothingDialog.value = false

        // 重置认知模块
        _selectedBiasId.value = null
        _selectedIdiomId.value = null
        _discoveredIdiomIds.value = emptySet()

        // 重置小镇状态
        _townState.value = null
        _foodStats.value = FoodStats()
        _clothingStats.value = ClothingStats()
        _housingStats.value = HousingStats()
        _mentalStats.value = MentalStats()

        // 重置建筑卡片
        _buildingCards.value = emptyList()
        _selectedBuildingCard.value = null

        // 重置趋势数据
        _trendData.value = emptyList()
        _trendSummary.value = null
        _showTrendPanel.value = false

        // 重置内存行为记录
        inMemoryActions.clear()

        // 重置反思相关
        _unlockedReflectionIds.value = emptySet()
        _activeCognitionReflection.value = null
        _cognitionReflectionResult.value = null
        _decisionHistory.value = DecisionPreferenceEngine.DecisionHistory()
        _candidateIdioms.value = null

        // 重置游戏模式
        _gameMode.value = GameMode.AUTO
        _dailyDietPlan.value = DietSystem.DailyDietPlan()
        _dietHabit.value = DietSystem.LongTermDietHabit()
        _dietEffect.value = null

        // 重置周计划
        _weeklyPlan.value = emptyMap()
        _eveningPlan.value = null
        _gameHour.value = 8
        _gameDay.value = 0
        _isEditMode.value = true
        _todayPlanExecuted.value = false
        _isRunning.value = true
        _eventLog.value = emptyList()
        _weeklyEvents.value = emptyList()
        _currentWeek.value = 1
        _showWeeklyBrief.value = false
        _weeklyStats.value = WeeklyStats()
        _showFoodPicker.value = false
        _showWeeklyPlanDialog.value = false
        _editingDayOfWeek.value = 1
        _foodEatCount.value = emptyMap()

        // 重置纪念馆统计
        _memorialMealCount.value = 0
        _memorialInstantNoodleCount.value = 0
        _memorialGoodSleepDays.value = 0
        _memorialLateNightCount.value = 0
        _memorialSelfCareActions.value = 0
        _memorialPlacesLived.value = 0
        _memorialMoneyDelta.value = 0.0

        // 重置身份系统
        _lifePathType.value = LifePathType.BALANCED
        _currentLifePathId = ""

        // 重置时间状态
        _currentMonth.value = 6
        _timeState.value = TimeEngine.createInitialState()
        _lastTickResult.value = null
        _lastMonthlyStats.value = null
        _currentYear.value = 2024

        // 重置身体状态
        _bodyState.value = com.example.townapp.data.database.entity.UserBodyState(
            userId = 1,
            userName = "我",
            satiety = 70,
            energy = 80,
            healthScore = 85,
            mood = 75
        )

        // 重置空间状态
        _spaceState.value = com.example.townapp.data.database.entity.UserSpaceState(userId = 1)

        // 重置精神状态
        _mentalState.value = com.example.townapp.data.database.entity.UserMentalState(userId = 1)

        // 重置菜谱
        _recipes.value = emptyList()
        _cuisineDishes.value = emptyList()
        _residents.value = emptyList()
        _lastFeedingResult.value = null
        _petDialogue.value = null
        _selectedRecipeId.value = null

        // 重置穿搭
        _outfitSets.value = emptyList()
        _selectedOutfitId.value = null
        _outfitMode.value = OutfitSceneManager.OutfitMode.AUTO
        _manualOutfitUnlocked.value = false
        _outfitEffect.value = null
        _outfitSelectReason.value = ""
        _lastLeisureResult.value = null
        _middleLeisureCount.value = 0
        _richLeisureCount.value = 0
        _lastPublicEventResult.value = null
        _activePublicEvents.value = emptyList()
        _fanStyle.value = FanStyle.RATIONAL
        _isWorldCupSeason.value = false

        // 重置穿搭状态
        _outfitWearLevel.value = 0.0

        // 重置对话语录
        _conversationQuotes.value = emptyList()
        _currentQuote.value = null

        // 重置所有反思
        _allReflections.value = IdiomRepository.fetchAllReflections()

        // 重新初始化默认数据
        loadInitialData()
        updateHourlySalary()
        updateTownState()
        refreshBuildingCards()
        refreshTrendData()
    }

    // ============================================
    // 设置模块
    // ============================================
    private val _settings = MutableStateFlow(GlobalSettings())
    val settings: StateFlow<GlobalSettings> = _settings.asStateFlow()

    private val _hourlySalary = MutableStateFlow("")
    val hourlySalary: StateFlow<String> = _hourlySalary.asStateFlow()

    // ============================================
    // 天气系统
    // ============================================
    private val _weatherState = MutableStateFlow(WeatherState.SUNNY)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _awakeningLevel = MutableStateFlow(1)
    val awakeningLevel: StateFlow<Int> = _awakeningLevel.asStateFlow()

    private val _totalAwakeningPoints = MutableStateFlow(0)
    val totalAwakeningPoints: StateFlow<Int> = _totalAwakeningPoints.asStateFlow()

    // ============================================
    // 食物模块
    // ============================================
    private val _foodItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodItems: StateFlow<List<FoodItem>> = _foodItems.asStateFlow()

    private val _selectedFoodId = MutableStateFlow<Int?>(null)
    val selectedFoodId: StateFlow<Int?> = _selectedFoodId.asStateFlow()

    private val _showAddFoodDialog = MutableStateFlow(false)
    val showAddFoodDialog: StateFlow<Boolean> = _showAddFoodDialog.asStateFlow()

    // ============================================
    // 衣物模块
    // ============================================
    private val _clothingItems = MutableStateFlow<List<ClothingItem>>(emptyList())
    val clothingItems: StateFlow<List<ClothingItem>> = _clothingItems.asStateFlow()

    private val _selectedClothingId = MutableStateFlow<Int?>(null)
    val selectedClothingId: StateFlow<Int?> = _selectedClothingId.asStateFlow()

    private val _showAddClothingDialog = MutableStateFlow(false)
    val showAddClothingDialog: StateFlow<Boolean> = _showAddClothingDialog.asStateFlow()

    // ============================================
    // 认知模块
    // ============================================
    private val _selectedBiasId = MutableStateFlow<Int?>(null)
    val selectedBiasId: StateFlow<Int?> = _selectedBiasId.asStateFlow()

    private val _selectedIdiomId = MutableStateFlow<Int?>(null)
    val selectedIdiomId: StateFlow<Int?> = _selectedIdiomId.asStateFlow()

    /** 已发现的成语卡片ID —— 通过触发或自由探索都会记录 */
    private val _discoveredIdiomIds = MutableStateFlow<Set<Int>>(emptySet())
    val discoveredIdiomIds: StateFlow<Set<Int>> = _discoveredIdiomIds.asStateFlow()

    val cognitiveBiases: List<CognitiveBias> = com.example.townapp.data.cognitiveBiases
    val idiomItems: List<IdiomItem> = com.example.townapp.data.idiomItems

    // ============================================
    // 小镇状态模块
    // ============================================
    private val _townState = MutableStateFlow<TownState?>(null)
    val townState: StateFlow<TownState?> = _townState.asStateFlow()

    private val _foodStats = MutableStateFlow(FoodStats())
    private val _clothingStats = MutableStateFlow(ClothingStats())
    private val _housingStats = MutableStateFlow(HousingStats())
    private val _mentalStats = MutableStateFlow(MentalStats())

    // ============================================
    // ✨ 建筑卡片（含等级/价值密度/批判文案）
    // ============================================
    private val _buildingCards = MutableStateFlow<List<TownBuildingCard>>(emptyList())
    val buildingCards: StateFlow<List<TownBuildingCard>> = _buildingCards.asStateFlow()

    private val _selectedBuildingCard = MutableStateFlow<TownBuildingCard?>(null)
    val selectedBuildingCard: StateFlow<TownBuildingCard?> = _selectedBuildingCard.asStateFlow()

    // ============================================
    // ✨ 价值密度趋势数据
    // ============================================
    private val _trendData = MutableStateFlow<List<ValueDensityCalculator.DailyDensitySnapshot>>(emptyList())
    val trendData: StateFlow<List<ValueDensityCalculator.DailyDensitySnapshot>> = _trendData.asStateFlow()

    private val _trendSummary = MutableStateFlow<ValueDensityCalculator.TrendSummary?>(null)
    val trendSummary: StateFlow<ValueDensityCalculator.TrendSummary?> = _trendSummary.asStateFlow()

    private val _showTrendPanel = MutableStateFlow(false)
    val showTrendPanel: StateFlow<Boolean> = _showTrendPanel.asStateFlow()

    // ============================================
    // 菜谱/穿搭模块
    // ============================================
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    /** 八大菜系160道菜品 —— 菜谱Tab的核心数据源 */
    private val _cuisineDishes = MutableStateFlow<List<CuisineFood>>(emptyList())
    val cuisineDishes: StateFlow<List<CuisineFood>> = _cuisineDishes.asStateFlow()

    /** 阶段1居民 —— 喂食交互的受试者 */
    private val _residents = MutableStateFlow<List<ResidentHealth>>(emptyList())
    val residents: StateFlow<List<ResidentHealth>> = _residents.asStateFlow()

    /** 最近一次喂食的结果 */
    private val _lastFeedingResult = MutableStateFlow<FeedingResult?>(null)
    val lastFeedingResult: StateFlow<FeedingResult?> = _lastFeedingResult.asStateFlow()

    /** 宠物对话文本（用餐后触发，展示完自动清空） */
    private val _petDialogue = MutableStateFlow<String?>(null)
    val petDialogue: StateFlow<String?> = _petDialogue.asStateFlow()

    private val _selectedRecipeId = MutableStateFlow<Int?>(null)
    val selectedRecipeId: StateFlow<Int?> = _selectedRecipeId.asStateFlow()
    
    private val _outfitSets = MutableStateFlow<List<OutfitSet>>(emptyList())
    val outfitSets: StateFlow<List<OutfitSet>> = _outfitSets.asStateFlow()

    private val _selectedOutfitId = MutableStateFlow<Int?>(null)
    val selectedOutfitId: StateFlow<Int?> = _selectedOutfitId.asStateFlow()

    /** 穿搭模式：自动（默认） / 手动（青年解锁） */
    private val _outfitMode = MutableStateFlow(OutfitSceneManager.OutfitMode.AUTO)
    val outfitMode: StateFlow<OutfitSceneManager.OutfitMode> = _outfitMode.asStateFlow()

    /** 手动穿搭是否已解锁（青年阶段开启） */
    private val _manualOutfitUnlocked = MutableStateFlow(false)
    val manualOutfitUnlocked: StateFlow<Boolean> = _manualOutfitUnlocked.asStateFlow()

    /** 当前穿搭效果结果 */
    private val _outfitEffect = MutableStateFlow<OutfitSceneManager.OutfitEffectResult?>(null)
    val outfitEffect: StateFlow<OutfitSceneManager.OutfitEffectResult?> = _outfitEffect.asStateFlow()

    /** 上次自动穿搭选择原因（用于UI展示） */
    private val _outfitSelectReason = MutableStateFlow("")
    val outfitSelectReason: StateFlow<String> = _outfitSelectReason.asStateFlow()

    /** 最近一次休闲活动结果 */
    private val _lastLeisureResult = MutableStateFlow<LeisureResult?>(null)
    val lastLeisureResult: StateFlow<LeisureResult?> = _lastLeisureResult.asStateFlow()

    /** 中产/富豪项目已体验次数（用于控制高频消费） */
    private val _middleLeisureCount = MutableStateFlow(0)
    private val _richLeisureCount = MutableStateFlow(0)

    /** 最近一次公共事件结果 */
    private val _lastPublicEventResult = MutableStateFlow<PublicEventResult?>(null)
    val lastPublicEventResult: StateFlow<PublicEventResult?> = _lastPublicEventResult.asStateFlow()

    /** 当前活跃的公共事件列表 */
    private val _activePublicEvents = MutableStateFlow<List<PublicEvent>>(emptyList())
    val activePublicEvents: StateFlow<List<PublicEvent>> = _activePublicEvents.asStateFlow()

    /** 追星风格（初始默认理性） */
    private val _fanStyle = MutableStateFlow(FanStyle.RATIONAL)
    val fanStyle: StateFlow<FanStyle> = _fanStyle.asStateFlow()

    /** 世界杯赛季标记 */
    private val _isWorldCupSeason = MutableStateFlow(false)
    val isWorldCupSeason: StateFlow<Boolean> = _isWorldCupSeason.asStateFlow()

    /** 一条已记录的行为（供 LifeStyleViewModel 监听解锁）。 */
    data class ActionRecorded(val type: String, val subType: String, val amount: Double)

    // 内存行为记录（当 Room 未初始化时，作为回退）
    private data class InMemoryAction(
        val dateLabel: String,
        val type: String,
        val subType: String,
        val amount: Double,
        val timestamp: Long = System.currentTimeMillis()
    )
    private val inMemoryActions = mutableListOf<InMemoryAction>()

    /** 最新一次记录的行为（供 LifeStyleViewModel 监听解锁条件）。 */
    private val _latestAction = MutableSharedFlow<ActionRecorded>(replay = 0)
    val latestAction: SharedFlow<ActionRecorded> = _latestAction.asSharedFlow()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // ============================================
    // ✨ 观念思辨三阶段 —— 觉醒值解锁追踪
    // ============================================
    private val _allReflections = MutableStateFlow(IdiomRepository.fetchAllReflections())
    val allReflections: StateFlow<List<IdiomReflection>> = _allReflections.asStateFlow()

    // ============================================
    // ✨ 正常交流语录系统（非成语日常对话）
    // ============================================
    private val _conversationQuotes = MutableStateFlow<List<ConversationQuote>>(emptyList())
    val conversationQuotes: StateFlow<List<ConversationQuote>> = _conversationQuotes.asStateFlow()

    private val _currentQuote = MutableStateFlow<ConversationQuote?>(null)
    val currentQuote: StateFlow<ConversationQuote?> = _currentQuote.asStateFlow()

    /** 已解锁的反思卡片 ID 集合 */
    private val _unlockedReflectionIds = MutableStateFlow<Set<String>>(emptySet())
    val unlockedReflectionIds: StateFlow<Set<String>> = _unlockedReflectionIds.asStateFlow()

    /** 最新解锁的反思卡片（供 UI 弹出 Snackbar 通知） */
    private val _newlyUnlockedReflection = MutableSharedFlow<IdiomReflection>(replay = 0)
    val newlyUnlockedReflection: SharedFlow<IdiomReflection> = _newlyUnlockedReflection.asSharedFlow()

    /** v2.14 当前激活的认知反思条目（供 UI 展示三层解析 + 选择） */
    private val _activeCognitionReflection = MutableStateFlow<CognitionReflection?>(null)
    val activeCognitionReflection: StateFlow<CognitionReflection?> = _activeCognitionReflection.asStateFlow()

    /** v2.14 认知反思选择结果 */
    private val _cognitionReflectionResult = MutableStateFlow<String?>(null)
    val cognitionReflectionResult: StateFlow<String?> = _cognitionReflectionResult.asStateFlow()

    // ============================================
    // 成语‑抉择联动引擎
    // ============================================
    /** 决策历史记录 */
    private val _decisionHistory = MutableStateFlow(DecisionPreferenceEngine.DecisionHistory())
    val decisionHistory: StateFlow<DecisionPreferenceEngine.DecisionHistory> = _decisionHistory.asStateFlow()

    /** 当前事件的候选俗语 */
    private val _candidateIdioms = MutableStateFlow<DecisionPreferenceEngine.CandidateIdioms?>(null)
    val candidateIdioms: StateFlow<DecisionPreferenceEngine.CandidateIdioms?> = _candidateIdioms.asStateFlow()

    // ============================================
    // 精细手动模式 — 游戏模式开关
    // ============================================
    /** 游戏模式：自动托管 / 精细手动 */
    private val _gameMode = MutableStateFlow(GameMode.AUTO)
    val gameMode: StateFlow<GameMode> = _gameMode.asStateFlow()

    /** 自动生活模式：工作日自动处理基础生活开销与物资消耗（默认开启） */
    private val _autoLifeEnabled = MutableStateFlow(true)
    val autoLifeEnabled: StateFlow<Boolean> = _autoLifeEnabled.asStateFlow()

    /** 当日饮食计划 */
    private val _dailyDietPlan = MutableStateFlow(DietSystem.DailyDietPlan())
    val dailyDietPlan: StateFlow<DietSystem.DailyDietPlan> = _dailyDietPlan.asStateFlow()

    /** 长期饮食习惯 */
    private val _dietHabit = MutableStateFlow(DietSystem.LongTermDietHabit())
    val dietHabit: StateFlow<DietSystem.LongTermDietHabit> = _dietHabit.asStateFlow()

    /** 今日饮食效果 */
    private val _dietEffect = MutableStateFlow<DietSystem.DietEffect?>(null)
    val dietEffect: StateFlow<DietSystem.DietEffect?> = _dietEffect.asStateFlow()

    // ============================================
    // 精细手动模式 — 工作日晚间规划
    // ============================================
    /** 今日晚间计划 */
    private val _eveningPlan = MutableStateFlow<WorkdayPlanner.EveningPlan?>(null)
    val eveningPlan: StateFlow<WorkdayPlanner.EveningPlan?> = _eveningPlan.asStateFlow()

    // ============================================
    // 精细手动模式 — 职业选择
    // ============================================
    /** 当前职业选择结果 */
    private val _careerChoiceEffect = MutableStateFlow<CareerPathSystem.CareerChoiceEffect?>(null)
    val careerChoiceEffect: StateFlow<CareerPathSystem.CareerChoiceEffect?> = _careerChoiceEffect.asStateFlow()

    // ============================================
    // 精细手动模式 — 玩家主动事件
    // ============================================
    /** 玩家主动触发事件的效果 */
    private val _playerEventEffect = MutableStateFlow<PlayerEventSystem.PlayerEventEffect?>(null)
    val playerEventEffect: StateFlow<PlayerEventSystem.PlayerEventEffect?> = _playerEventEffect.asStateFlow()

    /** 根据觉醒值获取当前可解锁的反思卡片 */
    fun getUnlockedReflections(awakeningValue: Int): List<IdiomReflection> {
        return IdiomRepository.getReflectionsByAwakening(awakeningValue)
    }

    // ============================================
    // 正常交流语录系统方法
    // ============================================
    fun getRandomQuote(): ConversationQuote {
        val quote = NormalConversationQuotes.getRandomQuote()
        _currentQuote.value = quote
        return quote
    }

    fun getQuotesByType(type: ConversationType): List<ConversationQuote> {
        return NormalConversationQuotes.getQuotesByType(type)
    }

    fun getQuotesByCharacter(character: com.example.townapp.data.microevent.TownCharacter): List<ConversationQuote> {
        return NormalConversationQuotes.getQuotesByCharacter(character)
    }

    init {
        loadInitialData()
        updateHourlySalary()
        updateTownState()
        refreshBuildingCards()
        refreshTrendData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _foodItems.value = listOf(
                FoodItem(
                    id = 1,
                    name = "鸡蛋",
                    category = "蛋奶豆",
                    pricePer100g = 1.2,
                    proteinPer100g = 13.3,
                    fatPer100g = 8.8,
                    carbohydratePer100g = 2.8,
                    fiberPer100g = 0.0,
                    caloriesPer100g = 144.0,
                    typicalServing = 50,
                    shelfLifeDays = 30.0,
                    mealsPerWeek = 7,
                    qualityScore = 0.9,
                    breedingDegradation = 0.1,
                    processingLoss = 0.0,
                    nutrientDensityScore = 85.0,
                    note = "优质蛋白来源，性价比极高"
                ),
                FoodItem(
                    id = 2,
                    name = "鸡胸肉",
                    category = "肉禽水产",
                    pricePer100g = 2.5,
                    proteinPer100g = 24.6,
                    fatPer100g = 1.9,
                    carbohydratePer100g = 0.0,
                    fiberPer100g = 0.0,
                    caloriesPer100g = 118.0,
                    typicalServing = 150,
                    shelfLifeDays = 3.0,
                    mealsPerWeek = 4,
                    qualityScore = 0.85,
                    breedingDegradation = 0.3,
                    processingLoss = 0.0,
                    nutrientDensityScore = 82.0,
                    note = "高蛋白低脂肪，健身首选"
                ),
                FoodItem(
                    id = 3,
                    name = "西兰花",
                    category = "蔬菜",
                    pricePer100g = 0.8,
                    proteinPer100g = 2.8,
                    fatPer100g = 0.4,
                    carbohydratePer100g = 7.0,
                    fiberPer100g = 2.6,
                    caloriesPer100g = 34.0,
                    typicalServing = 100,
                    shelfLifeDays = 7.0,
                    mealsPerWeek = 3,
                    nutrientDensityScore = 78.0,
                    note = "高纤维低热量，营养丰富"
                ),
                FoodItem(
                    id = 4,
                    name = "精制米面",
                    category = "主食",
                    pricePer100g = 0.5,
                    proteinPer100g = 7.0,
                    fatPer100g = 0.8,
                    carbohydratePer100g = 78.0,
                    fiberPer100g = 1.3,
                    caloriesPer100g = 345.0,
                    typicalServing = 100,
                    shelfLifeDays = 365.0,
                    mealsPerWeek = 7,
                    processingLoss = 0.4,
                    nutrientDensityScore = 45.0,
                    note = "精制加工，营养流失严重"
                ),
                FoodItem(
                    id = 5,
                    name = "薯片",
                    category = "零食",
                    pricePer100g = 8.0,
                    proteinPer100g = 7.0,
                    fatPer100g = 25.0,
                    carbohydratePer100g = 60.0,
                    fiberPer100g = 2.0,
                    caloriesPer100g = 450.0,
                    typicalServing = 50,
                    shelfLifeDays = 180.0,
                    mealsPerWeek = 2,
                    processingLoss = 0.6,
                    nutrientDensityScore = 25.0,
                    isIQTax = true,
                    iqTaxLevel = 2,
                    iqTaxReason = "高油高盐高糖，营养价值低",
                    note = "典型垃圾食品，健康成本高"
                ),
                FoodItem(
                    id = 6,
                    name = "米饭",
                    category = "主食",
                    pricePer100g = 0.3,
                    proteinPer100g = 2.6,
                    fatPer100g = 0.3,
                    carbohydratePer100g = 25.9,
                    fiberPer100g = 0.3,
                    caloriesPer100g = 116.0,
                    typicalServing = 150,
                    shelfLifeDays = 365.0,
                    mealsPerWeek = 7,
                    nutrientDensityScore = 50.0,
                    note = "日常主食，碳水化合物的主要来源"
                ),
                FoodItem(
                    id = 7,
                    name = "馒头",
                    category = "主食",
                    pricePer100g = 0.5,
                    proteinPer100g = 7.8,
                    fatPer100g = 1.1,
                    carbohydratePer100g = 58.6,
                    fiberPer100g = 1.3,
                    caloriesPer100g = 286.0,
                    typicalServing = 100,
                    shelfLifeDays = 3.0,
                    mealsPerWeek = 4,
                    nutrientDensityScore = 55.0,
                    note = "经典面食，饱腹感强"
                ),
                FoodItem(
                    id = 8,
                    name = "水煮蛋",
                    category = "蛋奶豆",
                    pricePer100g = 1.5,
                    proteinPer100g = 13.3,
                    fatPer100g = 9.5,
                    carbohydratePer100g = 2.8,
                    fiberPer100g = 0.0,
                    caloriesPer100g = 143.0,
                    typicalServing = 50,
                    shelfLifeDays = 7.0,
                    mealsPerWeek = 5,
                    qualityScore = 0.85,
                    nutrientDensityScore = 82.0,
                    note = "优质蛋白，营养丰富"
                ),
                FoodItem(
                    id = 9,
                    name = "牛肉",
                    category = "肉禽水产",
                    pricePer100g = 50.0,
                    proteinPer100g = 18.0,
                    fatPer100g = 20.0,
                    carbohydratePer100g = 0.0,
                    fiberPer100g = 0.0,
                    caloriesPer100g = 250.0,
                    typicalServing = 100,
                    shelfLifeDays = 5.0,
                    mealsPerWeek = 2,
                    qualityScore = 0.8,
                    breedingDegradation = 0.2,
                    processingLoss = 0.05,
                    nutrientDensityScore = 75.0,
                    note = "高蛋白高脂肪，适量食用"
                ),
                FoodItem(
                    id = 10,
                    name = "青菜",
                    category = "蔬菜",
                    pricePer100g = 0.5,
                    proteinPer100g = 1.8,
                    fatPer100g = 0.4,
                    carbohydratePer100g = 3.8,
                    fiberPer100g = 1.6,
                    caloriesPer100g = 24.0,
                    typicalServing = 200,
                    shelfLifeDays = 3.0,
                    mealsPerWeek = 7,
                    nutrientDensityScore = 90.0,
                    note = "低热量高纤维，每日必备"
                ),
                FoodItem(
                    id = 11,
                    name = "番茄",
                    category = "蔬菜",
                    pricePer100g = 1.0,
                    proteinPer100g = 0.9,
                    fatPer100g = 0.2,
                    carbohydratePer100g = 4.0,
                    fiberPer100g = 1.2,
                    caloriesPer100g = 18.0,
                    typicalServing = 150,
                    shelfLifeDays = 5.0,
                    mealsPerWeek = 4,
                    nutrientDensityScore = 85.0,
                    note = "富含番茄红素和维生素C"
                ),
                FoodItem(
                    id = 12,
                    name = "黄瓜",
                    category = "蔬菜",
                    pricePer100g = 0.8,
                    proteinPer100g = 0.8,
                    fatPer100g = 0.2,
                    carbohydratePer100g = 2.9,
                    fiberPer100g = 0.5,
                    caloriesPer100g = 16.0,
                    typicalServing = 150,
                    shelfLifeDays = 5.0,
                    mealsPerWeek = 4,
                    nutrientDensityScore = 80.0,
                    note = "水分充足，清爽解腻"
                ),
                FoodItem(
                    id = 13,
                    name = "苹果",
                    category = "水果",
                    pricePer100g = 1.5,
                    proteinPer100g = 0.2,
                    fatPer100g = 0.2,
                    carbohydratePer100g = 13.5,
                    fiberPer100g = 1.2,
                    caloriesPer100g = 52.0,
                    typicalServing = 200,
                    shelfLifeDays = 14.0,
                    mealsPerWeek = 5,
                    nutrientDensityScore = 80.0,
                    note = "富含果胶和维生素，健康零食"
                ),
                FoodItem(
                    id = 14,
                    name = "香蕉",
                    category = "水果",
                    pricePer100g = 1.2,
                    proteinPer100g = 1.4,
                    fatPer100g = 0.2,
                    carbohydratePer100g = 22.0,
                    fiberPer100g = 1.8,
                    caloriesPer100g = 91.0,
                    typicalServing = 150,
                    shelfLifeDays = 5.0,
                    mealsPerWeek = 3,
                    nutrientDensityScore = 78.0,
                    note = "富含钾元素，快速补充能量"
                ),
                FoodItem(
                    id = 15,
                    name = "牛奶",
                    category = "蛋奶豆",
                    pricePer100g = 1.0,
                    proteinPer100g = 3.2,
                    fatPer100g = 3.6,
                    carbohydratePer100g = 5.0,
                    fiberPer100g = 0.0,
                    caloriesPer100g = 66.0,
                    typicalServing = 250,
                    shelfLifeDays = 7.0,
                    mealsPerWeek = 7,
                    qualityScore = 0.7,
                    breedingDegradation = 0.1,
                    processingLoss = 0.05,
                    nutrientDensityScore = 85.0,
                    note = "优质钙源，日常营养补充"
                ),
                FoodItem(
                    id = 16,
                    name = "土豆",
                    category = "蔬菜",
                    pricePer100g = 0.6,
                    proteinPer100g = 2.0,
                    fatPer100g = 0.1,
                    carbohydratePer100g = 19.0,
                    fiberPer100g = 1.8,
                    caloriesPer100g = 81.0,
                    typicalServing = 200,
                    shelfLifeDays = 30.0,
                    mealsPerWeek = 3,
                    nutrientDensityScore = 70.0,
                    note = "可当主食亦可做菜，饱腹感强"
                ),
                FoodItem(
                    id = 17,
                    name = "豆腐",
                    category = "蛋奶豆",
                    pricePer100g = 1.5,
                    proteinPer100g = 8.1,
                    fatPer100g = 4.8,
                    carbohydratePer100g = 4.2,
                    fiberPer100g = 0.4,
                    caloriesPer100g = 81.0,
                    typicalServing = 150,
                    shelfLifeDays = 2.0,
                    mealsPerWeek = 3,
                    qualityScore = 0.75,
                    breedingDegradation = 0.05,
                    processingLoss = 0.0,
                    nutrientDensityScore = 82.0,
                    note = "优质植物蛋白，价格低廉"
                ),
                FoodItem(
                    id = 18,
                    name = "三文鱼",
                    category = "肉禽水产",
                    pricePer100g = 80.0,
                    proteinPer100g = 20.0,
                    fatPer100g = 13.0,
                    carbohydratePer100g = 0.0,
                    fiberPer100g = 0.0,
                    caloriesPer100g = 208.0,
                    typicalServing = 100,
                    shelfLifeDays = 2.0,
                    mealsPerWeek = 1,
                    qualityScore = 0.85,
                    breedingDegradation = 0.1,
                    processingLoss = 0.0,
                    nutrientDensityScore = 88.0,
                    note = "富含Omega-3脂肪酸，营养价值高"
                ),
                FoodItem(
                    id = 19,
                    name = "橙子",
                    category = "水果",
                    pricePer100g = 2.0,
                    proteinPer100g = 1.2,
                    fatPer100g = 0.2,
                    carbohydratePer100g = 15.5,
                    fiberPer100g = 2.4,
                    caloriesPer100g = 62.0,
                    typicalServing = 200,
                    shelfLifeDays = 10.0,
                    mealsPerWeek = 4,
                    nutrientDensityScore = 82.0,
                    note = "富含维生素C，增强免疫力"
                ),
                FoodItem(
                    id = 20,
                    name = "面包",
                    category = "主食",
                    pricePer100g = 2.0,
                    proteinPer100g = 9.0,
                    fatPer100g = 5.0,
                    carbohydratePer100g = 52.0,
                    fiberPer100g = 2.5,
                    caloriesPer100g = 290.0,
                    typicalServing = 100,
                    shelfLifeDays = 5.0,
                    mealsPerWeek = 3,
                    nutrientDensityScore = 50.0,
                    note = "方便快捷的早餐选择"
                )
            )

            _clothingItems.value = listOf(
                ClothingItem(
                    id = 1,
                    name = "基础白T恤",
                    category = "上衣",
                    brand = "优衣库",
                    price = 79.0,
                    material = "纯棉",
                    expectedLifespanMonths = 18.0,
                    maxWearsPerYear = 50,
                    costPerWear = 1.58,
                    isIQTax = false,
                    iqTaxLevel = 0,
                    evolutionParadoxScore = 0.2,
                    versatilityScore = 0.9,
                    comfortScore = 0.85,
                    durabilityScore = 0.7,
                    note = "基础款，百搭耐穿"
                ),
                ClothingItem(
                    id = 2,
                    name = "限量款潮牌卫衣",
                    category = "上衣",
                    brand = "某潮牌",
                    price = 899.0,
                    material = "棉混纺",
                    expectedLifespanMonths = 12.0,
                    maxWearsPerYear = 30,
                    costPerWear = 29.97,
                    isIQTax = true,
                    iqTaxLevel = 3,
                    iqTaxReason = "品牌溢价过高，同款普通品牌仅需1/5价格",
                    evolutionParadoxScore = 0.8,
                    versatilityScore = 0.4,
                    comfortScore = 0.7,
                    durabilityScore = 0.5,
                    note = "智商税典型案例"
                ),
                ClothingItem(
                    id = 3,
                    name = "经典牛仔裤",
                    category = "裤子",
                    brand = "李维斯",
                    price = 599.0,
                    material = "牛仔布",
                    expectedLifespanMonths = 60.0,
                    maxWearsPerYear = 100,
                    costPerWear = 1.20,
                    isIQTax = false,
                    iqTaxLevel = 1,
                    iqTaxReason = "品牌有一定溢价但耐用性强",
                    evolutionParadoxScore = 0.3,
                    versatilityScore = 0.85,
                    comfortScore = 0.75,
                    durabilityScore = 0.95,
                    note = "越穿越好看，经典耐穿"
                ),
                ClothingItem(
                    id = 4,
                    name = "运动鞋",
                    category = "鞋履",
                    brand = "耐克",
                    price = 899.0,
                    material = "合成材料",
                    expectedLifespanMonths = 24.0,
                    maxWearsPerYear = 80,
                    costPerWear = 11.24,
                    isIQTax = false,
                    iqTaxLevel = 1,
                    evolutionParadoxScore = 0.5,
                    versatilityScore = 0.7,
                    comfortScore = 0.9,
                    durabilityScore = 0.8,
                    note = "功能性强，适合运动"
                ),
                ClothingItem(
                    id = 5,
                    name = "羽绒服",
                    category = "外套",
                    brand = "波司登",
                    price = 1299.0,
                    material = "羽绒",
                    expectedLifespanMonths = 36.0,
                    maxWearsPerYear = 30,
                    costPerWear = 13.32,
                    isIQTax = false,
                    iqTaxLevel = 1,
                    evolutionParadoxScore = 0.4,
                    versatilityScore = 0.6,
                    comfortScore = 0.85,
                    durabilityScore = 0.85,
                    note = "保暖性好，冬季必备"
                ),
                ClothingItem(
                    id = 6,
                    name = "白衬衫",
                    category = "上衣",
                    brand = "优衣库",
                    price = 199.0,
                    material = "纯棉",
                    expectedLifespanMonths = 24.0,
                    maxWearsPerYear = 40,
                    costPerWear = 4.98,
                    isIQTax = false,
                    iqTaxLevel = 0,
                    evolutionParadoxScore = 0.2,
                    versatilityScore = 0.85,
                    comfortScore = 0.8,
                    durabilityScore = 0.7,
                    note = "职场必备，简约百搭"
                ),
                ClothingItem(
                    id = 7,
                    name = "休闲裤",
                    category = "裤子",
                    brand = "优衣库",
                    price = 299.0,
                    material = "棉",
                    expectedLifespanMonths = 36.0,
                    maxWearsPerYear = 60,
                    costPerWear = 4.98,
                    isIQTax = false,
                    iqTaxLevel = 0,
                    evolutionParadoxScore = 0.25,
                    versatilityScore = 0.8,
                    comfortScore = 0.85,
                    durabilityScore = 0.8,
                    note = "日常休闲，舒适耐穿"
                ),
                ClothingItem(
                    id = 8,
                    name = "连衣裙",
                    category = "裙子",
                    brand = "ZARA",
                    price = 399.0,
                    material = "棉混纺",
                    expectedLifespanMonths = 18.0,
                    maxWearsPerYear = 30,
                    costPerWear = 13.30,
                    isIQTax = false,
                    iqTaxLevel = 1,
                    evolutionParadoxScore = 0.5,
                    versatilityScore = 0.6,
                    comfortScore = 0.75,
                    durabilityScore = 0.6,
                    note = "时尚优雅，适合社交场合"
                ),
                ClothingItem(
                    id = 9,
                    name = "西装外套",
                    category = "外套",
                    brand = "海澜之家",
                    price = 799.0,
                    material = "羊毛混纺",
                    expectedLifespanMonths = 48.0,
                    maxWearsPerYear = 20,
                    costPerWear = 19.98,
                    isIQTax = true,
                    iqTaxLevel = 2,
                    iqTaxReason = "正式场合需求，使用频率低但单价高",
                    evolutionParadoxScore = 0.6,
                    versatilityScore = 0.5,
                    comfortScore = 0.6,
                    durabilityScore = 0.85,
                    note = "正式场合着装，使用频率有限"
                ),
                ClothingItem(
                    id = 10,
                    name = "羊毛大衣",
                    category = "外套",
                    brand = "鄂尔多斯",
                    price = 1999.0,
                    material = "羊毛",
                    expectedLifespanMonths = 60.0,
                    maxWearsPerYear = 25,
                    costPerWear = 15.99,
                    isIQTax = true,
                    iqTaxLevel = 2,
                    iqTaxReason = "品牌溢价较高，但材质优良",
                    evolutionParadoxScore = 0.6,
                    versatilityScore = 0.5,
                    comfortScore = 0.85,
                    durabilityScore = 0.9,
                    note = "高端保暖外套，冬季必备"
                )
            )

        // ============================================
        // 菜谱示例数据（10道家常菜，食材配比精确到克）
        // ============================================
        _recipes.value = listOf(
            Recipe(
                recipeId = 1, dishName = "番茄炒蛋", cuisine = "家常菜",
                simpleSteps = "1.鸡蛋打散炒熟盛出 2.番茄切块翻炒出汁 3.混合调味",
                ingredientIds = listOf(
                    RecipeIngredient(foodId = 8, useGram = 100f), // 水煮蛋 100g ≈ 2个
                    RecipeIngredient(foodId = 7, useGram = 200f)  // 番茄 200g ≈ 1个
                )
            ),
            Recipe(
                recipeId = 2, dishName = "青椒肉丝", cuisine = "川菜",
                simpleSteps = "1.肉丝腌制上浆 2.青椒切丝 3.肉丝滑炒后加青椒调味",
                ingredientIds = listOf(
                    RecipeIngredient(foodId = 9, useGram = 100f),  // 牛肉 100g
                    RecipeIngredient(foodId = 6, useGram = 80f)    // 青菜(青椒) 80g
                )
            ),
            Recipe(
                recipeId = 3, dishName = "可乐鸡翅", cuisine = "家常菜",
                simpleSteps = "1.鸡翅划刀焯水 2.加可乐生抽焖煮 3.收汁即可",
                ingredientIds = listOf(
                    RecipeIngredient(foodId = 3, useGram = 200f),  // 鸡胸肉≈鸡翅 200g
                    RecipeIngredient(foodId = 12, useGram = 330f)  // 可乐 330ml
                )
            ),
            Recipe(
                recipeId = 4, dishName = "蒜蓉青菜", cuisine = "粤菜",
                simpleSteps = "1.青菜洗净 2.蒜蓉爆香 3.大火快炒加盐",
                ingredientIds = listOf(
                    RecipeIngredient(foodId = 6, useGram = 200f)   // 青菜 200g
                )
            ),
            Recipe(
                recipeId = 5, dishName = "土豆炖牛腩", cuisine = "家常菜",
                simpleSteps = "1.牛腩切块焯水 2.土豆切块 3.加酱油炖煮40分钟",
                ingredientIds = listOf(
                    RecipeIngredient(foodId = 9, useGram = 150f),  // 牛肉 150g
                    RecipeIngredient(foodId = 14, useGram = 200f)  // 土豆 200g
                )
            ),
            Recipe(
                recipeId = 6, dishName = "凉拌黄瓜", cuisine = "家常菜",
                simpleSteps = "1.黄瓜拍碎切段 2.蒜末生抽醋调汁 3.拌匀即可",
                ingredientIds = listOf(
                    RecipeIngredient(foodId = 8, useGram = 200f)   // 黄瓜 200g
                )
            ),
            Recipe(
                recipeId = 7, dishName = "水煮牛肉", cuisine = "川菜",
                simpleSteps = "1.牛肉切片腌制 2.青菜焯水铺底 3.肉片滑熟淋热油",
                ingredientIds = listOf(
                    RecipeIngredient(foodId = 9, useGram = 200f),  // 牛肉 200g
                    RecipeIngredient(foodId = 6, useGram = 100f)   // 青菜 100g
                )
            ),
            Recipe(
                recipeId = 8, dishName = "米饭", cuisine = "主食",
                simpleSteps = "1.米淘洗 2.加水蒸煮 3.焖5分钟",
                ingredientIds = listOf(
                    RecipeIngredient(foodId = 1, useGram = 150f)   // 米饭(米) 150g
                )
            ),
            Recipe(
                recipeId = 9, dishName = "鸡蛋炒饭", cuisine = "家常菜",
                simpleSteps = "1.鸡蛋炒散 2.加米饭翻炒 3.加葱花调味",
                ingredientIds = listOf(
                    RecipeIngredient(foodId = 1, useGram = 200f),  // 米饭 200g
                    RecipeIngredient(foodId = 8, useGram = 50f)    // 水煮蛋≈鸡蛋 50g
                )
            ),
            Recipe(
                recipeId = 10, dishName = "瘦肉粥", cuisine = "家常菜",
                simpleSteps = "1.米加水煮粥 2.瘦肉切末 3.肉末入粥加调味",
                ingredientIds = listOf(
                    RecipeIngredient(foodId = 1, useGram = 80f),   // 米饭(米) 80g
                    RecipeIngredient(foodId = 13, useGram = 50f)   // 猪肉 50g
                )
            )
        )

        // ============================================
        // 八大菜系160道菜品（川鲁粤苏湘浙闽徽）
        // ============================================
        _cuisineDishes.value = CuisineFoodRepository.getAllFoods()

        // ============================================
        // 正常交流语录系统（非成语日常对话）
        // ============================================
        _conversationQuotes.value = NormalConversationQuotes.getAllQuotes()
        _currentQuote.value = NormalConversationQuotes.getRandomQuote()

        // ============================================
        // 阶段1居民 —— 纯内存版本
        // ============================================
        launch {
            // 使用默认居民数据（纯内存模式）
            val defaults = ResidentFeedingSystem.defaultResidents()
            _residents.value = defaults
        }

        // ============================================
        // 穿搭套装示例数据
        // ============================================
        _outfitSets.value = listOf(
            OutfitSet(setId = 1, setName = "休闲日常", styleTag = "休闲", colorMatchDesc = "白+蓝 清爽同色系", clothIdList = listOf(1, 2, 6), scene = OutfitScene.COMMUTE, fabricType = FabricType.COTTON, isThick = false),
            OutfitSet(setId = 2, setName = "通勤简约", styleTag = "通勤", colorMatchDesc = "白+黑 经典搭配", clothIdList = listOf(4, 5, 6), scene = OutfitScene.COMMUTE, fabricType = FabricType.POLYESTER, isThick = false),
            OutfitSet(setId = 3, setName = "温暖居家", styleTag = "居家", colorMatchDesc = "灰+灰 柔和舒适", clothIdList = listOf(3, 7, 8), scene = OutfitScene.HOME, fabricType = FabricType.COTTON, isThick = true),
            OutfitSet(setId = 4, setName = "商务正装", styleTag = "商务", colorMatchDesc = "深色系 沉稳专业", clothIdList = listOf(7, 5, 6), scene = OutfitScene.BUSINESS, fabricType = FabricType.WOOL, isThick = true),
            OutfitSet(setId = 5, setName = "运动出行", styleTag = "运动", colorMatchDesc = "浅色系 活力清爽", clothIdList = listOf(1, 2, 8), scene = OutfitScene.OUTDOOR, fabricType = FabricType.POLYESTER, isThick = false),
            OutfitSet(setId = 6, setName = "文艺简约", styleTag = "文艺", colorMatchDesc = "卡其+白 温柔质感", clothIdList = listOf(4, 10, 9), scene = OutfitScene.DATE, fabricType = FabricType.LINEN, isThick = false),
            OutfitSet(setId = 7, setName = "城市漫步", styleTag = "休闲", colorMatchDesc = "蓝+白 随性自然", clothIdList = listOf(1, 5, 8), scene = OutfitScene.DATE, fabricType = FabricType.COTTON, isThick = false),
            OutfitSet(setId = 8, setName = "温柔女生", styleTag = "甜美", colorMatchDesc = "粉+白 柔和可爱", clothIdList = listOf(4, 10, 9), scene = OutfitScene.DATE, fabricType = FabricType.COTTON, isThick = false),
            OutfitSet(setId = 9, setName = "简约通勤", styleTag = "通勤", colorMatchDesc = "黑白灰 不出错", clothIdList = listOf(7, 2, 6), scene = OutfitScene.COMMUTE, fabricType = FabricType.POLYESTER, isThick = false),
            OutfitSet(setId = 10, setName = "周末出游", styleTag = "休闲", colorMatchDesc = "牛仔+白 经典组合", clothIdList = listOf(1, 2, 8), scene = OutfitScene.DATE, fabricType = FabricType.LINEN, isThick = false)
        )
        }
    }

    fun updateSettings(newSettings: GlobalSettings) {
        _settings.value = newSettings
        updateHourlySalary()
        updateTownState()
    }

    private fun updateHourlySalary() {
        val result = SalaryCalculator.calculateHourlySalary(_settings.value)
        _hourlySalary.value = result.displayText
    }

    private fun updateTownState() {
        val townState = TownBusiness.calculateTownState(
            _foodStats.value,
            _clothingStats.value,
            _housingStats.value,
            _mentalStats.value
        )
        _townState.value = townState
        _weatherState.value = townState.weather
    }

    // ============================================
    // 小镇状态操作
    // ============================================
    fun updateFoodStats(stats: FoodStats) {
        _foodStats.value = stats
        updateTownState()
    }

    fun updateClothingStats(stats: ClothingStats) {
        _clothingStats.value = stats
        updateTownState()
    }

    fun updateHousingStats(stats: HousingStats) {
        _housingStats.value = stats
        updateTownState()
    }

    fun updateMentalStats(stats: MentalStats) {
        _mentalStats.value = stats
        updateTownState()
    }

    // ============================================
    // 食物操作
    // ============================================
    fun selectFoodItem(id: Int?) {
        _selectedFoodId.value = id
    }

    fun getSelectedFood(): FoodItem? {
        return _foodItems.value.find { it.id == _selectedFoodId.value }
    }

    fun showAddFoodDialog(show: Boolean) {
        _showAddFoodDialog.value = show
    }

    fun addFoodItem(item: FoodItem) {
        _foodItems.value = _foodItems.value + item
        updateTownState()
    }

    fun removeFoodItem(id: Int) {
        _foodItems.value = _foodItems.value.filter { it.id != id }
        if (_selectedFoodId.value == id) {
            _selectedFoodId.value = null
        }
        updateTownState()
    }

    fun updateFoodItem(updatedItem: FoodItem) {
        _foodItems.value = _foodItems.value.map {
            if (it.id == updatedItem.id) updatedItem else it
        }
    }

    // ============================================
    // 阶段1核心闭环: 喂食 → 身体反应
    // ============================================
    fun feedDishToResident(dish: CuisineFood, residentId: Int): FeedingResult {
        val resident = _residents.value.find { it.id == residentId }
            ?: return FeedingResult("?", dish.name, 0.0, 0, 0, "居民不存在", "❓")
        val result = ResidentFeedingSystem.feed(resident, dish)
        _residents.value = _residents.value.toMutableList()  // 触发重组
        _lastFeedingResult.value = result

        // 纯内存模式：只更新内存中的居民状态
        updateTownState()
        return result
    }

    fun clearFeedingResult() {
        _lastFeedingResult.value = null
    }

    // ============================================
    // 衣物操作
    // ============================================
    fun selectClothingItem(id: Int?) {
        _selectedClothingId.value = id
    }

    fun getSelectedClothing(): ClothingItem? {
        return _clothingItems.value.find { it.id == _selectedClothingId.value }
    }

    fun showAddClothingDialog(show: Boolean) {
        _showAddClothingDialog.value = show
    }

    fun addClothingItem(item: ClothingItem) {
        _clothingItems.value = _clothingItems.value + item
        updateTownState()
    }

    fun removeClothingItem(id: Int) {
        _clothingItems.value = _clothingItems.value.filter { it.id != id }
        if (_selectedClothingId.value == id) {
            _selectedClothingId.value = null
        }
        updateTownState()
    }

    fun updateClothingItem(updatedItem: ClothingItem) {
        _clothingItems.value = _clothingItems.value.map {
            if (it.id == updatedItem.id) updatedItem else it
        }
    }

    /**
     * 穿戴衣物并返回三段式反馈（v1.3 阶段二穿搭改造）
     * @return 体感反馈 + 衣物闪光点 + 小镇穿搭评述
     */
    fun markClothingAsWorn(itemId: Int): WearFeedback {
        _clothingItems.value = _clothingItems.value.map { item ->
            if (item.id == itemId) {
                val newWearCount = item.wearCount + 1
                val newCostPerWear = item.price / newWearCount
                item.copy(wearCount = newWearCount, costPerWear = newCostPerWear)
            } else {
                item
            }
        }
        updateTownState()

        // v1.3 阶段二：穿戴行为写入日志（三段式）
        val item = _clothingItems.value.find { it.id == itemId }
        if (item != null) {
            val (bodyFeedback, sparkle, commentary) = GentleTextProvider.describeWearContextual(
                item.name, item.material, _currentLifePathId
            )
            addEventLog(bodyFeedback)
            if (_currentLifePathId.isNotEmpty()) {
                addEventLog("【闪光点】${sparkle}")
                addEventLog("【小城镇看到】${commentary}")
            }
            // 触发宠物反馈
            _petDialogue.value = PetManager.getWearReply(
                itemName = item.name,
                material = item.material,
                category = item.category,
                isIQTax = item.isIQTax,
                wearCount = item.wearCount
            )
            // 纯内存模式：已通过addEventLog记录
            return WearFeedback(bodyFeedback, sparkle, commentary)
        }
        return WearFeedback("", "", "")
    }

    /** 穿戴衣物后的三段式反馈数据 */
    data class WearFeedback(
        val bodyFeedback: String,
        val sparkle: String,
        val commentary: String
    )

    /**
     * 获取当前身份的默认穿搭列表（v1.2 阶段二）
     * 纯内存模式：使用_currentLifePathId
     */
    suspend fun getDefaultOutfitsForCurrentIdentity(): List<com.example.townapp.data.DefaultOutfit> {
        if (_currentLifePathId.isEmpty()) return emptyList()
        return com.example.townapp.data.LifePathBindingData.getBinding(_currentLifePathId)?.defaultOutfits ?: emptyList()
    }

    fun selectRecipe(id: Int?) { _selectedRecipeId.value = id }
    fun getSelectedRecipe(): Recipe? = _recipes.value.firstOrNull { it.recipeId == _selectedRecipeId.value }
    fun selectOutfit(id: Int?) { _selectedOutfitId.value = id }
    fun getSelectedOutfit(): OutfitSet? = _outfitSets.value.firstOrNull { it.setId == _selectedOutfitId.value }

    // ============================================
    // v2.20 穿搭系统改造 —— 场景分类 + 自动/手动双模式
    // ============================================

    /**
     * 自动模式：根据场景+天气+家境，自动选取穿搭
     *
     * 调用时机：进入新的一天、切换场景时调用
     * 幼年阶段强制自动模式，青年解锁后玩家可切换
     */
    fun autoSelectOutfit(scene: OutfitScene) {
        val weather = _weatherState.value
        val income = _spaceState.value.monthlyIncome
        val wealth = OutfitSceneManager.classifyWealth(income)

        val result = OutfitSceneManager.autoSelectOutfit(
            scene = scene,
            weather = weather,
            wealth = wealth,
            allOutfits = _outfitSets.value,
            climate = getCurrentClimate()
        )

        if (result != null) {
            _selectedOutfitId.value = result.outfit.setId
            _outfitSelectReason.value = result.reason

            // 应用穿搭效果
            applyOutfitEffect(result.outfit, scene)
        }
    }

    /**
     * 手动模式：玩家自由选择穿搭，不限制可选范围
     * 但需根据场景计算效果
     */
    fun manualSelectOutfit(outfitId: Int, scene: OutfitScene) {
        if (!_manualOutfitUnlocked.value) return
        _selectedOutfitId.value = outfitId
        _outfitSelectReason.value = ""

        val outfit = _outfitSets.value.firstOrNull { it.setId == outfitId } ?: return
        applyOutfitEffect(outfit, scene)
    }

    /**
     * 应用穿搭效果到各项参数
     *
     * 只修改：颜值、疲惫值、自尊感、疾病风险、约会成功率
     * 不参与：收入、健康数值、婚恋好感等核心数值
     */
    private fun applyOutfitEffect(outfit: OutfitSet, scene: OutfitScene) {
        val isFemale = _genderState.value.sex == BiologicalSex.FEMALE
        val isMenstruating = _genderState.value.menstrualCycle?.isMenstruating ?: false
        val climate = getCurrentClimate()

        val effect = OutfitSceneManager.calculateEffect(
            outfit = outfit,
            scene = scene,
            weather = _weatherState.value,
            climate = climate,
            isFemale = isFemale,
            isMenstruating = isMenstruating
        )

        _outfitEffect.value = effect

        // 颜值浮动
        val currentAppearance = _appearanceState.value
        _appearanceState.value = currentAppearance.copy(
            // 叠加穿搭效果到颜值
            currentAge = _playerAge.value
        )

        // 自尊感变化
        val mental = _mentalState.value
        _mentalState.value = mental.copy(
            selfEsteem = (mental.selfEsteem + effect.selfEsteemMod).coerceIn(0, 100)
        )

        // 疲惫值变化（通过 BodyState 或 MentalState 体现）
        if (effect.fatigueMod > 0) {
            _mentalState.value = _mentalState.value.copy(
                anxiety = (_mentalState.value.anxiety + (effect.fatigueMod * 10).toInt()).coerceIn(0, 100)
            )
        }

        // 记录事件
        if (effect.tier == OutfitSceneManager.OutfitEffectTier.MISMATCHED) {
            addEventLog("穿「${outfit.setName}」去${scene.displayName}场景，有点不太合适。")
        }
    }

    /**
     * 青年阶段解锁手动穿搭
     * 调用时机：角色年龄到达 18-20 岁
     */
    fun enableManualOutfit() {
        _manualOutfitUnlocked.value = true
        _outfitMode.value = OutfitSceneManager.OutfitMode.MANUAL
        addEventLog("你已经可以自己决定穿什么了。\n穿搭面板已解锁，10套穿搭自由选择。")
    }

    /**
     * 切换穿搭模式
     */
    fun toggleOutfitMode() {
        if (!_manualOutfitUnlocked.value) return
        _outfitMode.value = when (_outfitMode.value) {
            OutfitSceneManager.OutfitMode.AUTO -> OutfitSceneManager.OutfitMode.MANUAL
            OutfitSceneManager.OutfitMode.MANUAL -> OutfitSceneManager.OutfitMode.AUTO
        }
    }

    /**
     * 获取场景穿搭分布（用于UI展示）
     */
    fun getOutfitSceneDistribution(): Map<OutfitScene, Int> {
        return OutfitSceneManager.getSceneDistribution(_outfitSets.value)
    }

    /**
     * 获取指定场景的套装列表
     */
    fun getOutfitsForScene(scene: OutfitScene): List<OutfitSet> {
        return OutfitSceneManager.getOutfitsByScene(scene, _outfitSets.value)
    }

    /**
     * 获取穿搭效果对约会成功率的修正（供婚恋系统调用）
     */
    fun getOutfitDatingModifier(): Double {
        return _outfitEffect.value?.datingSuccessMod ?: 0.0
    }

    /**
     * 获取穿搭效果对颜值的影响（供外观系统调用）
     */
    fun getOutfitAppearanceModifier(): Double {
        return _outfitEffect.value?.appearanceMod ?: 0.0
    }

    /**
     * 长时间穿搭错位 → 缓慢提升焦虑值
     * 调用时机：每日结算时检查
     */
    fun checkOutfitAnxietyDecay() {
        val effect = _outfitEffect.value ?: return
        if (effect.tier == OutfitSceneManager.OutfitEffectTier.MISMATCHED) {
            val mental = _mentalState.value
            _mentalState.value = mental.copy(
                anxiety = (mental.anxiety + 1).coerceAtMost(100)
            )
        }
    }

    // ============================================
    // v2.21 休闲娱乐系统 —— 31条分层事件
    // ============================================

    /**
     * 根据月份获取季节
     */
    private fun getSeasonFromMonth(month: Int): Season = when (month) {
        in 3..5 -> Season.SPRING
        in 6..8 -> Season.SUMMER
        in 9..11 -> Season.AUTUMN
        else -> Season.WINTER
    }

    /**
     * 触发休闲活动（主入口）
     *
     * 调用时机：每日结算时，根据当前时段（工作日/周末/节假日/季节）触发
     * 幼年阶段默认自动触发平民娱乐，青年后可手动选择
     *
     * @param category 当前时段
     * @return 休闲结果，null 表示太累了什么都没做
     */
    fun doLeisureActivity(category: LeisureCategory): LeisureResult? {
        val weather = _weatherState.value
        val season = getSeasonFromMonth(_currentMonth.value)
        val age = _playerAge.value
        val fatigue = _bodyState.value.fatigueLevel
        val savings = _spaceState.value.currentSavings
        val income = _spaceState.value.monthlyIncome

        // 中产/富豪项目年度次数限制
        val result = LeisureManager.selectLeisure(
            category = category,
            weather = weather,
            season = season,
            age = age,
            fatigue = fatigue,
            savings = savings,
            monthlyIncome = income
        ) ?: return null

        // 中产/富豪项目额外频次检查
        if (result.event.tier == LeisureTier.MIDDLE) {
            if (_middleLeisureCount.value >= 3) {
                // 今年已体验3次中产项目，降级到平民
                return doLeisureActivity(category)
            }
            _middleLeisureCount.value++
        }
        if (result.event.tier == LeisureTier.RICH) {
            if (_richLeisureCount.value >= 2) {
                // 今年已体验2次富豪项目，降级
                return doLeisureActivity(category)
            }
            _richLeisureCount.value++
        }

        // 应用休闲效果
        applyLeisureEffect(result)

        // 记录社交活跃度（休闲活动 = 社交场景）
        val activityType = when (category) {
            LeisureCategory.WEEKDAY_EVENING -> "friend_dinner"
            LeisureCategory.WEEKEND -> "outing"
            LeisureCategory.SHORT_HOLIDAY -> "outing"
            LeisureCategory.SEASONAL -> "hobby_community"
        }
        recordSocialActivity(activityType)

        _lastLeisureResult.value = result
        return result
    }

    /**
     * 应用休闲效果到各项参数
     */
    private fun applyLeisureEffect(result: LeisureResult) {
        val e = result.event.effects
        val mental = _mentalState.value
        val body = _bodyState.value

        // 心理参数
        _mentalState.value = mental.copy(
            anxiety = (mental.anxiety + e.anxietyDelta).coerceIn(0, 100),
            loneliness = (mental.loneliness + e.lonelinessDelta).coerceIn(0, 100),
            happiness = (mental.happiness + e.happinessDelta).coerceIn(0, 100),
            selfEsteem = (mental.selfEsteem + e.selfEsteemDelta).coerceIn(0, 100),
            socialFulfillment = (mental.socialFulfillment + e.socialFulfillmentDelta).coerceIn(0, 100)
        )

        // 身体参数
        _bodyState.value = body.copy(
            fatigueLevel = (body.fatigueLevel + e.fatigueDelta).coerceIn(0, 100)
        )

        // 觉醒值
        if (e.awakeningPoints > 0) {
            addAwakeningPoints(e.awakeningPoints)
        }

        // 消费扣除
        if (result.actualCost > 0) {
            val newSavings = (_spaceState.value.currentSavings - result.actualCost).coerceAtLeast(0.0)
            _spaceState.value = _spaceState.value.copy(currentSavings = newSavings)
        }

        // 穿搭联动
        result.outfitScene.let { scene ->
            if (_outfitMode.value == OutfitSceneManager.OutfitMode.AUTO) {
                autoSelectOutfit(scene)
            }
        }

        // 认知标签触发
        if (result.event.cognitiveTag == "赛博奶嘴") {
            addEventLog("刷完短视频，你觉得放松了——但好像什么都没留下。\n「赛博奶嘴」——你想起这个词。")
        }
        if (result.event.cognitiveTag == "长辈俗语触发") {
            addEventLog("长辈又说了几句老话。\n和以前一样，他们不是要改变你——只是这是他们唯一会的方式。")
        }

        // 事件记录
        addEventLog("${result.reason}\n花费 ¥${result.actualCost}")
    }

    /**
     * 获取当前季节
     */
    fun getCurrentSeason(): Season = getSeasonFromMonth(_currentMonth.value)

    /**
     * 获取分层休闲事件数量统计
     */
    fun getLeisureTierDistribution(): Map<LeisureTier, Int> = LeisureManager.getTierDistribution()

    /**
     * 获取指定时段的休闲事件列表
     */
    fun getLeisureEventsByCategory(category: LeisureCategory): List<LeisureEvent> =
        LeisureManager.getEventsByCategory(category)

    /**
     * 重置年度休闲次数（每年1月1日调用）
     */
    fun resetYearlyLeisureCount() {
        _middleLeisureCount.value = 0
        _richLeisureCount.value = 0
    }

    // ============================================
    // v2.22 公共事件系统 —— 庙会/演唱会/世界杯/追星/音乐节/城市节庆
    // ============================================

    /**
     * 更新当月的公共事件活跃状态
     *
     * 调用时机：每月1日，切换月份时调用
     */
    fun updatePublicEventSeason() {
        val month = _currentMonth.value
        val year = _currentYear.value
        _activePublicEvents.value = PublicEventScheduler.getActiveEvents(month, year)
        _isWorldCupSeason.value = PublicEventScheduler.isWorldCupSeason(month, year)
    }

    /**
     * 触发公共事件活动
     *
     * 调用时机：每日结算时，与 doLeisureActivity 并行调用
     *
     * @return 触发结果，null 表示当前没有活跃公共事件或玩家没有参与
     */
    fun doPublicEventActivity(): PublicEventResult? {
        val month = _currentMonth.value
        val year = _currentYear.value
        val weather = _weatherState.value
        val age = _playerAge.value
        val fatigue = _bodyState.value.fatigueLevel
        val savings = _spaceState.value.currentSavings
        val income = _spaceState.value.monthlyIncome

        val result = PublicEventScheduler.checkAndTrigger(
            month = month, year = year,
            weather = weather, age = age,
            fatigue = fatigue, savings = savings, monthlyIncome = income
        ) ?: return null

        applyPublicEventEffect(result)
        _lastPublicEventResult.value = result
        return result
    }

    /**
     * 应用公共事件效果到各项参数
     */
    private fun applyPublicEventEffect(result: PublicEventResult) {
        val e = result.selectedTier.effects
        val mental = _mentalState.value
        val body = _bodyState.value

        // 心理参数
        _mentalState.value = mental.copy(
            anxiety = (mental.anxiety + e.anxietyDelta).coerceIn(0, 100),
            loneliness = (mental.loneliness + e.lonelinessDelta).coerceIn(0, 100),
            happiness = (mental.happiness + e.happinessDelta).coerceIn(0, 100),
            selfEsteem = (mental.selfEsteem + e.selfEsteemDelta).coerceIn(0, 100),
            socialFulfillment = (mental.socialFulfillment + e.socialFulfillmentDelta).coerceIn(0, 100)
        )

        // 身体参数
        _bodyState.value = body.copy(
            fatigueLevel = (body.fatigueLevel + e.fatigueDelta).coerceIn(0, 100)
        )

        // 觉醒值
        if (e.awakeningPoints > 0) {
            addAwakeningPoints(e.awakeningPoints)
        }
        if (e.awakeningPoints < 0) {
            // 狂热追星等消耗觉醒值
            addAwakeningPoints(e.awakeningPoints)
        }

        // 消费扣除
        if (result.actualCost > 0) {
            val newSavings = (_spaceState.value.currentSavings - result.actualCost).coerceAtLeast(0.0)
            _spaceState.value = _spaceState.value.copy(currentSavings = newSavings)
        }

        // 穿搭联动
        if (_outfitMode.value == OutfitSceneManager.OutfitMode.AUTO) {
            autoSelectOutfit(result.outfitScene)
        }

        // 认知标签触发
        val cognitiveTags = result.event.cognitiveTags
        if ("传统俗语" in cognitiveTags) {
            addEventLog("长辈逛庙会的时候，又说了几句老话。\n和以前一样——他们不是要改变你，只是这是他们唯一会的方式。")
        }
        if ("消费主义" in cognitiveTags) {
            addEventLog("买完门票，你看着余额——快乐是真的，心疼也是真的。\n「消费主义」——你开始思考这个词。")
        }
        if ("集体记忆" in cognitiveTags) {
            addEventLog("这一刻，你和所有人共享着同一种情绪。\n很多年后你还会想起这一天。")
        }
        if ("追星理性" in cognitiveTags && _fanStyle.value == FanStyle.FANATIC) {
            addEventLog("你又透支了。开心吗？开心。但你知道下个月要省着点花了。")
        }

        // 医疗联动：熬夜观赛/演唱会→短期免疫力下降
        if (result.event.type == PublicEventType.WORLD_CUP || result.event.type == PublicEventType.CONCERT) {
            if (e.fatigueDelta >= 3) {
                _bodyState.value = _bodyState.value.copy(
                    immuneLevel = (_bodyState.value.immuneLevel - 3).coerceAtLeast(30)
                )
            }
        }

        // 事件记录
        addEventLog("${result.reason}\n花费 ¥${result.actualCost}")

        // 主队输赢效果（世界杯）
        if (result.event.type == PublicEventType.WORLD_CUP) {
            val roll = kotlin.random.Random.nextDouble()
            if (roll < 0.4) {
                // 主队输了
                val currentAnxiety = _mentalState.value.anxiety
                _mentalState.value = _mentalState.value.copy(
                    anxiety = (currentAnxiety + 2).coerceAtMost(100)
                )
                addEventLog("主队输了……你沉默了很久。但没关系，四年后还有机会。")
            } else if (roll < 0.7) {
                // 主队赢了
                val currentSelfEsteem = _mentalState.value.selfEsteem
                _mentalState.value = _mentalState.value.copy(
                    selfEsteem = (currentSelfEsteem + 3).coerceAtMost(100)
                )
                addEventLog("主队赢了！！你激动得跳了起来——今晚注定是个不眠之夜。")
            }
        }
    }

    /**
     * 世界杯赛季期间，每周触发一次观赛事件
     *
     * 调用时机：世界杯赛季期间，每周一次
     */
    fun doWorldCupWeeklyEvent(): PublicEventResult? {
        if (!_isWorldCupSeason.value) return null

        val weather = _weatherState.value
        val age = _playerAge.value
        val fatigue = _bodyState.value.fatigueLevel
        val savings = _spaceState.value.currentSavings
        val income = _spaceState.value.monthlyIncome

        val teamWon = kotlin.random.Random.nextDouble().let { roll ->
            when {
                roll < 0.35 -> true   // 35%概率主队赢
                roll < 0.65 -> false  // 30%概率主队输
                else -> null          // 35%概率没有主队比赛
            }
        }

        val result = PublicEventScheduler.getWorldCupWeeklyEvent(
            weather, age, fatigue, savings, income, teamWon
        ) ?: return null

        applyPublicEventEffect(result)
        _lastPublicEventResult.value = result
        return result
    }

    /**
     * 触发追星事件
     *
     * 调用时机：青年阶段，随演唱会/应援活动不定期触发
     */
    fun doFanActivity(): PublicEventResult? {
        val savings = _spaceState.value.currentSavings
        val income = _spaceState.value.monthlyIncome

        val result = PublicEventScheduler.getFanEvent(
            style = _fanStyle.value,
            savings = savings,
            monthlyIncome = income
        ) ?: return null

        applyPublicEventEffect(result)
        _lastPublicEventResult.value = result
        return result
    }

    /**
     * 切换追星风格（理性 ↔ 狂热）
     */
    fun switchFanStyle() {
        _fanStyle.value = when (_fanStyle.value) {
            FanStyle.RATIONAL -> FanStyle.FANATIC
            FanStyle.FANATIC -> FanStyle.RATIONAL
        }
    }

    /**
     * 获取当前所有公共事件类型
     */
    fun getAllPublicEventTypes(): List<PublicEventType> = PublicEventType.entries.toList()

    /**
     * 获取指定类型的公共事件详情
     */
    fun getPublicEventDetail(type: PublicEventType): PublicEvent? =
        PublicEventLibrary.getByType(type)

    // ============================================
    // v2.23 面料-鞋袜-材质系统 —— 四季面料适配+熨烫折旧+换季
    // ============================================

    /** 当前套装磨损度 */
    private val _outfitWearLevel = MutableStateFlow(0.0)
    val outfitWearLevel: StateFlow<Double> = _outfitWearLevel.asStateFlow()

    /**
     * 获取当前气候状态（温度等级+湿度等级）
     */
    fun getCurrentClimate(): ClimateState {
        return FabricRules.deriveClimate(_currentMonth.value, _weatherState.value)
    }

    /**
     * 熨烫操作（居家周度可选）
     *
     * @param outfitId 要熨烫的套装ID
     * @return 熨烫结果文案
     */
    fun doIroning(outfitId: Int): String {
        val outfit = _outfitSets.value.firstOrNull { it.setId == outfitId }
            ?: return "未找到该套装。"

        val result = ClothingMaintenance.ironClothing(outfit.fabricType)

        if (result.success && result.appearanceBoost > 0) {
            // 可熨烫面料 → 颜值小幅提升
            val current = _appearanceState.value
            // 颜值提升通过selfEsteem体现
            val mental = _mentalState.value
            _mentalState.value = mental.copy(
                selfEsteem = (mental.selfEsteem + 1).coerceAtMost(100)
            )
        }

        if (!result.success) {
            // 损坏 → 颜值下降
            addEventLog("${outfit.setName}在熨烫中受损。面料不耐高温，下次别冒险了。")
        }

        addEventLog(result.message)
        return result.message
    }

    /**
     * 月度衣物维护结算
     *
     * 调用时机：每月1日
     */
    fun doMonthlyClothingMaintenance(): String {
        val climate = getCurrentClimate()
        val sb = StringBuilder()

        // 对当前穿着的套装进行折旧结算
        val currentId = _selectedOutfitId.value
        if (currentId != null) {
            val outfit = _outfitSets.value.firstOrNull { it.setId == currentId }
            if (outfit != null) {
                val result = ClothingMaintenance.monthlyDepreciation(
                    fabricType = outfit.fabricType,
                    currentWear = _outfitWearLevel.value,  // 使用现有磨损度或创建新的
                    climate = climate
                )

                _outfitWearLevel.value = result.wearLevel

                // 磨损对颜值的影响
                if (result.appearancePenalty < 0) {
                    val current = _mentalState.value
                    _mentalState.value = current.copy(
                        selfEsteem = (current.selfEsteem - 1).coerceAtLeast(0)
                    )
                }

                if (result.needsReplace) {
                    sb.append(result.message)
                    addEventLog(result.message)
                } else if (result.needsRepair) {
                    sb.append(result.message)
                }
            }
        }

        return sb.toString().ifEmpty { "衣物状态良好，无需维护。" }
    }

    /**
     * 换季检查
     *
     * 调用时机：每月1日
     * @return 换季提示文案，空字符串表示无需换季
     */
    fun checkSeasonalClothingTransition(): String {
        val currentId = _selectedOutfitId.value ?: return ""
        val outfit = _outfitSets.value.firstOrNull { it.setId == currentId } ?: return ""

        val result = ClothingMaintenance.checkSeasonalTransition(
            month = _currentMonth.value,
            currentFabric = outfit.fabricType,
            isThick = outfit.isThick
        )

        if (result.isTransition && result.outfitsNeedSwap) {
            addEventLog(result.message)

            // 换季不及时 → 身体受影响
            if (result.coldRisk > 0) {
                _bodyState.value = _bodyState.value.copy(
                    immuneLevel = (_bodyState.value.immuneLevel - 5).coerceAtLeast(30)
                )
            }
            if (result.heatRisk > 0) {
                _bodyState.value = _bodyState.value.copy(
                    fatigueLevel = (_bodyState.value.fatigueLevel + 10).coerceAtMost(100)
                )
                val mental = _mentalState.value
                _mentalState.value = mental.copy(
                    anxiety = (mental.anxiety + 3).coerceAtMost(100)
                )
            }
        }

        return result.message
    }

    /**
     * 修补衣物
     */
    fun repairOutfit(outfitId: Int): String {
        val outfit = _outfitSets.value.firstOrNull { it.setId == outfitId }
            ?: return "未找到该套装。"

        val cost = when (outfit.fabricType) {
            FabricType.WOOL -> 80.0
            FabricType.COTTON -> 30.0
            FabricType.POLYESTER -> 20.0
            FabricType.LINEN -> 40.0
        }

        val savings = _spaceState.value.currentSavings
        if (savings < cost) return "修补「${outfit.setName}」需要 ¥$cost，当前存款不足。"

        _spaceState.value = _spaceState.value.copy(currentSavings = savings - cost)
        _outfitWearLevel.value = (_outfitWearLevel.value - 0.3).coerceAtLeast(0.0)

        val msg = "「${outfit.setName}」修补完成，花费 ¥$cost。衣物状态恢复了一些。"
        addEventLog(msg)
        return msg
    }

    /**
     * 更换新套装（购买新衣）
     */
    fun replaceOutfit(outfitId: Int): String {
        val outfit = _outfitSets.value.firstOrNull { it.setId == outfitId }
            ?: return "未找到该套装。"

        val cost = when (outfit.fabricType) {
            FabricType.WOOL -> 500.0
            FabricType.COTTON -> 100.0
            FabricType.POLYESTER -> 80.0
            FabricType.LINEN -> 120.0
        }

        val savings = _spaceState.value.currentSavings
        if (savings < cost) return "购买新「${outfit.setName}」需要 ¥$cost，当前存款不足。"

        _spaceState.value = _spaceState.value.copy(currentSavings = savings - cost)
        _outfitWearLevel.value = 0.0

        // 新衣提升自尊感
        val mental = _mentalState.value
        _mentalState.value = mental.copy(
            selfEsteem = (mental.selfEsteem + 2).coerceAtMost(100)
        )

        val msg = "购买了新的「${outfit.setName}」，花费 ¥$cost。穿上新衣服，心情都好了很多。"
        addEventLog(msg)
        return msg
    }

    /**
     * 获取季节面料推荐（用于UI展示）
     */
    fun getSeasonFabricRecommendation(): List<FabricType> {
        val climate = getCurrentClimate()
        return FabricRules.getSeasonRankedFabrics(climate)
    }

    /**
     * 获取面料错配提示（用于UI展示当前穿着的面料是否合适）
     */
    fun getFabricMismatchHint(): String {
        val currentId = _selectedOutfitId.value ?: return ""
        val outfit = _outfitSets.value.firstOrNull { it.setId == currentId } ?: return ""
        val climate = getCurrentClimate()
        val penalty = FabricRules.calculatePenalty(outfit.fabricType, outfit.isThick, climate)
        return penalty.reason
    }

    // ============================================
    // 认知模块操作
    // ============================================
    fun selectBias(id: Int?) {
        _selectedBiasId.value = id
        if (id != null) {
            addAwakeningPoints(5)
        }
    }

    fun selectIdiom(id: Int?) {
        _selectedIdiomId.value = id
        if (id != null) {
            val idiom = idiomItems.find { it.id == id }
            idiom?.let { addAwakeningPoints(it.awakeningValue) }
            // 记录已发现
            _discoveredIdiomIds.value = _discoveredIdiomIds.value + id
        }
    }

    fun addAwakeningPoints(points: Int) {
        _totalAwakeningPoints.value += points
        _awakeningLevel.value = (_totalAwakeningPoints.value / 100) + 1

        val currentMentalStats = _mentalStats.value
        _mentalStats.value = currentMentalStats.copy(
            totalAwakeningPoints = _totalAwakeningPoints.value
        )

        // 检查是否有新解锁的反思卡片
        val newValue = _totalAwakeningPoints.value
        val previouslyUnlocked = _unlockedReflectionIds.value
        val nowUnlocked = getUnlockedReflections(newValue).map { it.id }.toSet()
        val justUnlocked = nowUnlocked - previouslyUnlocked
        if (justUnlocked.isNotEmpty()) {
            _unlockedReflectionIds.value = nowUnlocked
            // 逐个发出新解锁事件（供 UI 弹出 Snackbar）
            justUnlocked.forEach { id ->
                IdiomRepository.getReflectionById(id)?.let { ref ->
                    viewModelScope.launch {
                        _newlyUnlockedReflection.emit(ref)
                    }
                }
            }
        }

        updateTownState()
    }

    // ============================================
    // 统计计算
    // ============================================
    fun getTotalFoodCost(): Double {
        return _foodItems.value.sumOf { FoodBusiness.analyzeFood(it, _settings.value).costPerYear }
    }

    fun getTotalClothingCost(): Double {
        return _clothingItems.value.sumOf { ClothingBusiness.analyzeClothing(it, _settings.value).costPerMonth * 12 }
    }

    fun getRedundantFoodCount(): Int {
        return _foodItems.value.count { FoodBusiness.analyzeFood(it, _settings.value).isRedundant }
    }

    fun getIdleClothingCount(): Int {
        return _clothingItems.value.count { ClothingBusiness.analyzeClothing(it, _settings.value).isIdle }
    }

    // ============================================
    // ✨ 行为记录 → 建筑卡片 → 趋势更新
    // ============================================

    /**
     * 记录用户行为：同时更新内存记录、觉醒值、建筑卡片和趋势数据。
     * 与 FoodScreen/ClothingScreen 等录入面板联动。
     */
    fun recordUserAction(type: String, subType: String, amount: Double) {
        // 1. 内存记录（供趋势图使用）
        val todayLabel = dateFormat.format(Date())
        inMemoryActions.add(InMemoryAction(dateLabel = todayLabel, type = type, subType = subType, amount = amount))

        // 2. 计算价值密度反馈 → 增加觉醒值
        val density = ValueDensityCalculator.calculateBehaviorDensity(type, subType, amount)
        if (density != null) {
            addAwakeningPoints(density.density.toInt().coerceAtLeast(0))
        }

        // 3. 发射行为事件（供 LifeStyleViewModel 监听解锁）
        viewModelScope.launch {
            _latestAction.emit(ActionRecorded(type = type, subType = subType, amount = amount))
        }

        // 4. 刷新建筑卡片列表 + 趋势
        refreshBuildingCards()
        refreshTrendData()
    }

    /** 从 TownRepository 的建筑 + BehaviorBuildingMapping 的价值密度生成 TownBuildingCard 列表。 */
    fun refreshBuildingCards() {
        val state = _townState.value ?: return
        val cards = state.activeBuildings.mapNotNull { building ->
            // 从 BehaviorBuildingMapping 找到对应的价值密度基准值
            val mapping = BehaviorBuildingMapping.findByBuildingId(building.id)
            val density = mapping?.let { m ->
                // 用累计的行为量估算当前密度
                val totalForBuilding = inMemoryActions.filter { it.subType == m.behaviorKey }
                    .sumOf { it.amount }
                m.valueDensityPerUnit * totalForBuilding.coerceAtLeast(0.0)
            } ?: 0.0

            BuildingCardFactory.fromBuildingId(building.id, density)
        }
        _buildingCards.value = cards
    }

    /**
     * 刷新近期的日均价值密度趋势。
     * @param days 近 N 天，默认 7 天
     */
    fun refreshTrendData(days: Int = 7) {
        val now = System.currentTimeMillis()
        val dayMs = 24 * 60 * 60 * 1000L
        val records = mutableListOf<ValueDensityCalculator.Tuple4<String, String, String, Double>>()

        for (i in 0 until days) {
            val dayStart = now - (days - i) * dayMs
            val dayLabel = dateFormat.format(Date(dayStart))
            // 从内存中找
            val dayActions = inMemoryActions.filter { it.dateLabel == dayLabel }
            if (dayActions.isNotEmpty()) {
                dayActions.forEach { action ->
                    records.add(ValueDensityCalculator.Tuple4(dayLabel, action.type, action.subType, action.amount))
                }
            }
        }

        if (records.isEmpty()) {
            _trendData.value = emptyList()
            _trendSummary.value = null
            return
        }

        val snaps = ValueDensityCalculator.aggregateDailyDensity(records)
        _trendData.value = snaps

        // 计算"当前" vs "上一个" 7 天的对比
        val half = snaps.size / 2
        val current = if (half > 0) snaps.takeLast(snaps.size - half) else snaps
        val previous = if (half > 0) snaps.take(half) else emptyList()
        if (current.isNotEmpty()) {
            _trendSummary.value = ValueDensityCalculator.summarizeTrend(current, previous)
        }
    }

    /** 切换趋势面板显示/隐藏 */
    fun toggleTrendPanel() {
        _showTrendPanel.value = !_showTrendPanel.value
        if (_showTrendPanel.value) refreshTrendData()
    }

    /** 选中一个建筑卡片（弹出详情） */
    fun selectBuildingCard(card: TownBuildingCard?) {
        _selectedBuildingCard.value = card
    }

    // ============================================
    // 全品类商品数据集成 (已统一定义于 Models.kt 的 allProducts/allCompositions)
    // 本处仅保留自定义数据的动态管理 + 预计算索引优化
    // ============================================
    
    // ── 一级缓存：启动时一次性预计算，后续零开销 ──

    /** 预计算：品类 → 品类内产品列表 索引（O(1)查询） */
    private val categoryProductIndex: Map<String, List<ProductItem>> by lazy {
        allProducts.groupBy { it.category }
    }

    /** 预计算：材料ID → MaterialItem 索引（O(1)查询） */
    private val materialIndex: Map<Int, MaterialItem> by lazy {
        allMaterials.associateBy { it.id }
    }

    /** 预计算：产品ID → ProductItem 索引（O(1)查询） */
    private val productIndex: Map<Int, ProductItem> by lazy {
        allProducts.associateBy { it.id }
    }

    // ── 动态数据流（含自定义数据，仅自定义数据变化时重算） ──

    /** 用户自定义添加的产品列表 */
    private var _customProducts = MutableStateFlow<List<ProductItem>>(emptyList())
    val customProducts: StateFlow<List<ProductItem>> = _customProducts.asStateFlow()

    /** 用户自定义添加的原材料列表 */
    private var _customMaterials = MutableStateFlow<List<MaterialItem>>(emptyList())
    val customMaterials: StateFlow<List<MaterialItem>> = _customMaterials.asStateFlow()

    /** 用户自定义添加的材料配比列表 */
    private var _customCompositions = MutableStateFlow<List<CompositionRelation>>(emptyList())
    val customCompositions: StateFlow<List<CompositionRelation>> = _customCompositions.asStateFlow()

    /** 用户自定义品类：名称 → 图标 */
    private var _customCategories = MutableStateFlow<Map<String, String>>(emptyMap())
    val customCategories: StateFlow<Map<String, String>> = _customCategories.asStateFlow()

    /** 
     * 全量产品列表（静态 allProducts + 自定义动态），仅自定义变化时重建
     * 通过 collectAsState() 订阅，Compose 自动处理重组
     */
    val allExtendedProducts: StateFlow<List<ProductItem>> = 
        customProducts.map { custom: List<ProductItem> ->
            allProducts + custom
        }.stateIn(viewModelScope, SharingStarted.Eagerly, allProducts)

    /** 全量原材料列表（静态 + 自定义动态） */
    val allExtendedMaterials: StateFlow<List<MaterialItem>> =
        customMaterials.map { custom: List<MaterialItem> ->
            allMaterials + custom
        }.stateIn(viewModelScope, SharingStarted.Eagerly, allMaterials)

    /** 全量材料配比列表（静态 + 自定义动态） */
    val allExtendedCompositions: StateFlow<List<CompositionRelation>> =
        customCompositions.map { custom: List<CompositionRelation> ->
            allCompositions + custom
        }.stateIn(viewModelScope, SharingStarted.Eagerly, allCompositions)

    /** 品类 → 数量 索引（仅品类名称变化时重算） */
    val categoryCountMap: Map<String, Int> by lazy {
        val map = mutableMapOf<String, Int>()
        allProducts.forEach { p ->
            map[p.category] = (map[p.category] ?: 0) + 1
        }
        map
    }

    // ── 增删操作（触发 StateFlow 更新 → 自动级联刷新衍生数据） ──

    /** 添加自定义商品 */
    fun addCustomProduct(product: ProductItem) {
        _customProducts.value = _customProducts.value + product
    }

    /** 删除自定义商品 */
    fun removeCustomProduct(productId: Int) {
        _customProducts.value = _customProducts.value.filter { it.id != productId }
    }

    /** 添加自定义原材料 */
    fun addCustomMaterial(material: MaterialItem) {
        _customMaterials.value = _customMaterials.value + material
    }

    /** 删除自定义原材料 */
    fun removeCustomMaterial(materialId: Int) {
        _customMaterials.value = _customMaterials.value.filter { it.id != materialId }
    }

    /** 添加/更新自定义材料配比 */
    fun addCustomComposition(relation: CompositionRelation) {
        _customCompositions.value = _customCompositions.value.filter { 
            it.productId != relation.productId || it.materialId != relation.materialId 
        } + relation
    }

    /** 删除自定义材料配比 */
    fun removeCustomComposition(productId: Int, materialId: Int) {
        _customCompositions.value = _customCompositions.value.filter {
            it.productId != productId || it.materialId != materialId
        }
    }

    /** 添加自定义品类 */
    fun addCustomCategory(name: String, emoji: String) {
        if (name.isNotBlank()) {
            _customCategories.value = _customCategories.value + (name to emoji)
        }
    }

    /** 删除自定义品类 */
    fun removeCustomCategory(name: String) {
        _customCategories.value = _customCategories.value - name
    }
    
    /** 按品类名获取产品列表（O(1)索引查询） */
    fun getProductsByCategory(category: String): List<ProductItem> {
        return categoryProductIndex[category] ?: emptyList()
    }

    /** 所有品类名称列表（用于UI展示） */
    val categoryNames: List<String> = listOf(
        "家居用品", "办公文具", "露营户外", "婴童用品",
        "宠物用品", "五金工具", "医疗健康", "体育健身", "玩具桌游",
        "美术文创", "农林农具", "珠宝古董", "无形服务", "音乐影音"
    )

    // ============================================
    // 🌟 MVP 核心游戏状态 — 半自动状态驱动
    // ============================================

    /** 当前游戏时间（小时，0-23） */
    private val _gameHour = MutableStateFlow(8)
    val gameHour: StateFlow<Int> = _gameHour.asStateFlow()

    /** 当前游戏天数（0=未开局） */
    private val _gameDay = MutableStateFlow(0)
    val gameDay: StateFlow<Int> = _gameDay.asStateFlow()

    /** 当前周计划：每天的计划内容 */
    private val _weeklyPlan = MutableStateFlow<Map<Int, DailyPlan>>(emptyMap())
    val weeklyPlan: StateFlow<Map<Int, DailyPlan>> = _weeklyPlan.asStateFlow()

    /** 当前是否为编辑模式（周日） */
    private val _isEditMode = MutableStateFlow(true)
    val isEditMode: StateFlow<Boolean> = _isEditMode.asStateFlow()

    /** 更新编辑模式状态 */
    private fun updateEditMode() {
        _isEditMode.value = (_gameDay.value % 7 == 0) || (_gameDay.value == 1)
    }

    /** 今日是否已执行自动计划 */
    private val _todayPlanExecuted = MutableStateFlow(false)
    val todayPlanExecuted: StateFlow<Boolean> = _todayPlanExecuted.asStateFlow()

    /** 今日自动生活开销摘要（仅自动模式有效） */
    private val _autoLifeSummary = MutableStateFlow("")
    val autoLifeSummary: StateFlow<String> = _autoLifeSummary.asStateFlow()

    /** 自动生活档位（简朴/普通/宽裕） */
    private val _autoLifeTier = MutableStateFlow(AutoLifeTier.NORMAL)
    val autoLifeTier: StateFlow<AutoLifeTier> = _autoLifeTier.asStateFlow()

    /** 消费模式（刚需/均衡/溢价） */
    private val _shoppingMode = MutableStateFlow(ShoppingMode.BALANCED)
    val shoppingMode: StateFlow<ShoppingMode> = _shoppingMode.asStateFlow()

    /** 本周开销汇总 */
    private val _weeklyExpense = MutableStateFlow(WeeklyExpense(0.0, 0.0, 0.0, 0.0, 0))
    val weeklyExpense: StateFlow<WeeklyExpense> = _weeklyExpense.asStateFlow()

    /** 医疗偏好 */
    private val _medicalPreference = MutableStateFlow(MedicalPreference.PRAGMATIC)
    val medicalPreference: StateFlow<MedicalPreference> = _medicalPreference.asStateFlow()

    /** 当前疾病状态 */
    private val _currentIllness = MutableStateFlow(IllnessType.NONE)
    val currentIllness: StateFlow<IllnessType> = _currentIllness.asStateFlow()

    /** 本周医疗开销 */
    private val _weeklyMedicalExpense = MutableStateFlow(0.0)
    val weeklyMedicalExpense: StateFlow<Double> = _weeklyMedicalExpense.asStateFlow()

    /** 连续高医疗开销周数（用于健康预警） */
    private var _highMedicalWeeks = 0

    /** 爱好预算档位（最低/常规/高端） */
    private val _hobbyBudgetTier = MutableStateFlow(HobbyBudgetTier.BALANCED)
    val hobbyBudgetTier: StateFlow<HobbyBudgetTier> = _hobbyBudgetTier.asStateFlow()

    /** 当前主爱好（如 music / photography / calligraphy） */
    private val _currentHobby = MutableStateFlow("music")
    val currentHobby: StateFlow<String> = _currentHobby.asStateFlow()

    /** 爱好熟练度（0-100） */
    private val _hobbyProficiency = MutableStateFlow(0)
    val hobbyProficiency: StateFlow<Int> = _hobbyProficiency.asStateFlow()

    /** 最近一次爱好随机事件 */
    private val _lastHobbyEvent = MutableStateFlow<String?>(null)
    val lastHobbyEvent: StateFlow<String?> = _lastHobbyEvent.asStateFlow()

    /** 虚荣性格标签（影响溢价档位偏好） */
    private val _vanityTrait = MutableStateFlow(false)
    val vanityTrait: StateFlow<Boolean> = _vanityTrait.asStateFlow()

    /** 本周社交活跃度评分（0-100，决定相亲候选池是否解锁） */
    private val _weeklySocialScore = MutableStateFlow(0)
    val weeklySocialScore: StateFlow<Int> = _weeklySocialScore.asStateFlow()

    /** 社交预算档位（影响社交场景数量与相亲概率） */
    private val _socialBudgetTier = MutableStateFlow(HobbyBudgetTier.BALANCED)
    val socialBudgetTier: StateFlow<HobbyBudgetTier> = _socialBudgetTier.asStateFlow()

    /** 本周事件层级（平淡/中等/重大） */
    private val _weeklyEventTier = MutableStateFlow(WeeklyEventTier.MUNDANE)
    val weeklyEventTier: StateFlow<WeeklyEventTier> = _weeklyEventTier.asStateFlow()

    /** 本周事件摘要文案 */
    private val _weeklyEventSummary = MutableStateFlow("")
    val weeklyEventSummary: StateFlow<String> = _weeklyEventSummary.asStateFlow()

    /** 当前待处理的中等/重大事件选项 */
    private val _pendingWeekEventChoices = MutableStateFlow<List<WeekEventChoice>?>(null)
    val pendingWeekEventChoices: StateFlow<List<WeekEventChoice>?> = _pendingWeekEventChoices.asStateFlow()

    /** 连续平淡周数计数器 */
    private var _consecutiveMundaneWeeks = 0

    /** 重大事件冷却周数 */
    private var _majorEventCooldown = 0

    // ============================================
    // 玩家手动操作覆盖标志（周末手动 > 系统自动）
    // ============================================
    /** 本周玩家是否手动治疗过疾病 */
    private var _playerManuallyTreatedDiseaseThisWeek = false
    /** 本周玩家是否手动处理过生理期 */
    private var _playerManuallyTreatedPeriodThisWeek = false
    /** 本周玩家是否手动做出过中年抉择 */
    private var _playerManuallyChoseMidlifePathThisWeek = false
    /** 本周玩家是否手动参与过相亲/婚恋操作 */
    private var _playerManuallyDatedThisWeek = false

    /** 重置所有手动操作标志（每周一调用） */
    private fun resetWeeklyManualFlags() {
        _playerManuallyTreatedDiseaseThisWeek = false
        _playerManuallyTreatedPeriodThisWeek = false
        _playerManuallyChoseMidlifePathThisWeek = false
        _playerManuallyDatedThisWeek = false
    }

    /** 模拟是否运行中 */
    private val _isRunning = MutableStateFlow(true)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    /** 事件日志（最新在上） */
    private val _eventLog = MutableStateFlow<List<String>>(emptyList())
    val eventLog: StateFlow<List<String>> = _eventLog.asStateFlow()

    /** 本周事件列表（用于周简报） */
    private val _weeklyEvents = MutableStateFlow<List<String>>(emptyList())
    val weeklyEvents: StateFlow<List<String>> = _weeklyEvents.asStateFlow()

    /** 当前周数 */
    private val _currentWeek = MutableStateFlow(1)
    val currentWeek: StateFlow<Int> = _currentWeek.asStateFlow()

    /** 是否显示周简报 */
    private val _showWeeklyBrief = MutableStateFlow(false)
    val showWeeklyBrief: StateFlow<Boolean> = _showWeeklyBrief.asStateFlow()

    /** 本周统计数据 */
    private val _weeklyStats = MutableStateFlow(WeeklyStats())
    val weeklyStats: StateFlow<WeeklyStats> = _weeklyStats.asStateFlow()

    /** 是否显示食物选择弹窗 */
    private val _showFoodPicker = MutableStateFlow(false)
    val showFoodPicker: StateFlow<Boolean> = _showFoodPicker.asStateFlow()

    /** 是否显示周计划编辑弹窗 */
    private val _showWeeklyPlanDialog = MutableStateFlow(false)
    val showWeeklyPlanDialog: StateFlow<Boolean> = _showWeeklyPlanDialog.asStateFlow()

    /** 当前正在编辑的天数 */
    private val _editingDayOfWeek = MutableStateFlow(1)
    val editingDayOfWeek: StateFlow<Int> = _editingDayOfWeek.asStateFlow()

    /** 显示周计划编辑弹窗 */
    fun showWeeklyPlanEditor() {
        _showWeeklyPlanDialog.value = true
    }

    /** 关闭周计划编辑弹窗 */
    fun hideWeeklyPlanEditor() {
        _showWeeklyPlanDialog.value = false
    }

    /** 设置当前编辑的天数 */
    fun setEditingDayOfWeek(day: Int) {
        _editingDayOfWeek.value = day
    }

    /** 保存单日计划 */
    fun saveDailyPlan(dayOfWeek: Int, plan: DailyPlan) {
        val currentPlan = _weeklyPlan.value.toMutableMap()
        currentPlan[dayOfWeek] = plan
        _weeklyPlan.value = currentPlan
    }

    /** 食物使用频率追踪：foodName → 食用次数 */
    private val _foodEatCount = MutableStateFlow<Map<String, Int>>(emptyMap())
    val foodEatCount: StateFlow<Map<String, Int>> = _foodEatCount.asStateFlow()

    // ============================================
    // 🏠 平凡生活纪念馆统计数据
    // ============================================
    private val _memorialMealCount = MutableStateFlow(0)
    val memorialMealCount: StateFlow<Int> = _memorialMealCount.asStateFlow()

    private val _memorialInstantNoodleCount = MutableStateFlow(0)
    val memorialInstantNoodleCount: StateFlow<Int> = _memorialInstantNoodleCount.asStateFlow()

    private val _memorialGoodSleepDays = MutableStateFlow(0)
    val memorialGoodSleepDays: StateFlow<Int> = _memorialGoodSleepDays.asStateFlow()

    private val _memorialLateNightCount = MutableStateFlow(0)
    val memorialLateNightCount: StateFlow<Int> = _memorialLateNightCount.asStateFlow()

    private val _memorialSelfCareActions = MutableStateFlow(0)
    val memorialSelfCareActions: StateFlow<Int> = _memorialSelfCareActions.asStateFlow()

    private val _memorialPlacesLived = MutableStateFlow(0)
    val memorialPlacesLived: StateFlow<Int> = _memorialPlacesLived.asStateFlow()

    private val _memorialMoneyDelta = MutableStateFlow(0.0)
    val memorialMoneyDelta: StateFlow<Double> = _memorialMoneyDelta.asStateFlow()

    // 工时-疲劳联动（v1.4 第二阶段）
    private val _lifePathType = MutableStateFlow(LifePathType.BALANCED)
    val lifePathType: StateFlow<LifePathType> = _lifePathType.asStateFlow()

    /** 当前游戏月份（1-12） */
    private val _currentMonth = MutableStateFlow(6)
    val currentMonth: StateFlow<Int> = _currentMonth.asStateFlow()

    /** v2.14 统一时间状态（单一数据源，日-周-月-年-年龄-阶段） */
    private val _timeState = MutableStateFlow(TimeEngine.createInitialState())
    val timeState: StateFlow<TimeState> = _timeState.asStateFlow()

    /** 最近一次 tick 结果 */
    private val _lastTickResult = MutableStateFlow<TickResult?>(null)
    val lastTickResult: StateFlow<TickResult?> = _lastTickResult.asStateFlow()

    /** 最近一次月度结算 */
    private val _lastMonthlyStats = MutableStateFlow<MonthlyStats?>(null)
    val lastMonthlyStats: StateFlow<MonthlyStats?> = _lastMonthlyStats.asStateFlow()

    /** 连续负面月份计数（用于概率补偿） */
    private var _consecutiveBadMonths = 0
    /** 连续正面月份计数（用于概率制衡） */
    private var _consecutiveGoodMonths = 0

    /** 本周每日统计缓存（用于周度汇总） */
    private val _weeklyDailyStats = mutableListOf<DailyStats>()
    /** 本月周度统计缓存（用于月度汇总） */
    private val _monthlyWeeklyStats = mutableListOf<WeeklyStats>()

    /** 疲劳事件冷却（避免重复记录） */
    private var _lastFatigueEventDay = 0

    /** v2.0 事件日志日限频（工作日最多5条/天，周末15条/天） */
    private var _lastEventLogDay = 0
    private var _dailyEventCount = 0

    // 孩童行为场景（v1.7 原生本能 × 成年疲惫）
    private val _childBehaviorScene = MutableStateFlow<ChildBehaviorScene?>(null)
    val childBehaviorScene: StateFlow<ChildBehaviorScene?> = _childBehaviorScene.asStateFlow()

    /** 最后触发的跨职业对话场景 */
    private val _lastCrossProfessionDialogue = MutableStateFlow<CrossProfessionDialogue?>(null)
    val lastCrossProfessionDialogue: StateFlow<CrossProfessionDialogue?> = _lastCrossProfessionDialogue.asStateFlow()

    /** 与孩童互动累计次数 */
    private val _childInteractionCount = MutableStateFlow(0)
    val childInteractionCount: StateFlow<Int> = _childInteractionCount.asStateFlow()

    /** 当前耐心值（0-100，越高越有耐心） */
    private val _patienceLevel = MutableStateFlow(50)
    val patienceLevel: StateFlow<Int> = _patienceLevel.asStateFlow()

    // 时代成本系统（v2.0 五层人生模型闭环）
    private val eraInflation = EraInflationConfig()

    /** 当前游戏年份 */
    private val _currentYear = MutableStateFlow(2024)
    val currentYear: StateFlow<Int> = _currentYear.asStateFlow()

    // 用户身体状态
    private val _bodyState = MutableStateFlow(
        com.example.townapp.data.database.entity.UserBodyState(
            userId = 1,
            userName = "我",
            satiety = 70,
            energy = 80,
            healthScore = 85,
            mood = 75
        )
    )
    val bodyState: StateFlow<com.example.townapp.data.database.entity.UserBodyState> = _bodyState.asStateFlow()

    // 空间状态
    private val _spaceState = MutableStateFlow(
        com.example.townapp.data.database.entity.UserSpaceState(userId = 1)
    )
    val spaceState: StateFlow<com.example.townapp.data.database.entity.UserSpaceState> = _spaceState.asStateFlow()

    // 当前人生身份 ID（v1.3 世界观修正，用于消费认知提示）
    private var _currentLifePathId = ""

    // 精神状态
    private val _mentalState = MutableStateFlow(
        com.example.townapp.data.database.entity.UserMentalState(userId = 1)
    )
    val mentalState: StateFlow<com.example.townapp.data.database.entity.UserMentalState> = _mentalState.asStateFlow()

    /** 最便宜食物（用于自动兜底）- 每次调用时重新计算 */
    private fun getCheapestFood(): FoodItem? = _foodItems.value.minByOrNull { it.pricePer100g * it.typicalServing / 100.0 }

    init {
        // 纯内存模式：_currentLifePathId初始为空字符串
        startTimeTick()
    }

    // ============================================
    // 🌟 时间心跳订阅（下沉至 TimeTickEngine）
    // ============================================
    private fun startTimeTick() {
        // 启动全局节拍引擎
        TimeTickEngine.start(viewModelScope, intervalMs = 5000L)
        // 订阅 tick 事件，执行业务逻辑
        viewModelScope.launch {
            TimeTickEngine.tickFlow.collect { result ->
                try {
                    onTick(result)
                } catch (e: Exception) {
                    android.util.Log.e("TownViewModel", "tick 业务异常: ${e.message}", e)
                }
            }
        }
    }

    /** 一次心跳：时间已推进，执行状态衰减与自动兜底 */
    private fun onTick(result: TickResult) {
        // 同步时间状态（向后兼容）
        _timeState.value = result.newState
        _lastTickResult.value = result
        _gameHour.value = result.newState.hour
        _gameDay.value = result.newState.totalDays + 1
        _currentMonth.value = result.newState.month
        _currentYear.value = result.newState.year
        _playerAge.value = result.newState.age

        if (result.isNewDay) {
            // 每日结算
            val dailyStats = DailySettlement.settle(
                weekday = result.newState.weekDay,
                weather = _weatherState.value,
                temperature = getTemperatureForMonth(result.newState.month),
                isMenstruating = _genderState.value.menstrualCycle?.isMenstruating ?: false,
                screenHours = 4.0  // 默认每日4小时屏幕时间
            )
            _weeklyDailyStats.add(dailyStats)

            // 每日应用
            applyDailyStats(dailyStats)

            // 每日相遇冷却与年龄更新
            tickEncounterDaily()
            tickFrustrationDaily()
            tickBeliefDaily()
            tickClassBehaviorDaily()
            tickReformerDaily()
            tickPsychologicalDaily()
            tickWithdrawalDaily()
            tickLoveDaily()
            tickAppearanceDaily()
            tickClothingDaily()
            tickChildhoodTraumaDaily()
            tickGenderDaily()
            tickMaterialDaily()
            tickMedicalDaily()

            // —— 周度结算 ——
            if (result.isNewWeek) {
                tickWeekly(result.newState)
            }

            // —— 月度结算 ——
            if (result.isNewMonth) {
                tickMonthly(result)
            }
        }

        // 2. 状态衰减
        val body = _bodyState.value
        var updatedBody = body

        // 纪念馆：判断昨晚是否熬夜（能量低说明没休息好）
        if (body.energy < 40) {
            _memorialLateNightCount.value += 1
        }

        // 每游戏小时衰减
        val newSatiety = (body.satiety - 2).coerceIn(0, 100)
        val newEnergy = (body.energy - 1).coerceIn(0, 100)
        val newMood = (body.mood - 1).coerceIn(0, 100)
        val newHealth = (body.healthScore - 1).coerceIn(0, 100)

        updatedBody = body.copy(
            satiety = newSatiety,
            energy = newEnergy,
            mood = newMood,
            healthScore = newHealth,
            updateTime = System.currentTimeMillis()
        )

        // 精神状态自然衰减
        val mental = _mentalState.value
        val newHappiness = (mental.happiness - 1).coerceIn(0, 100)
        val newAnxiety = (mental.anxiety + 1).coerceIn(0, 100)
        _mentalState.value = mental.copy(
            happiness = newHappiness,
            anxiety = newAnxiety,
            updateTime = System.currentTimeMillis()
        ).coerce()

        // ============================================
        // 2.5 工时-疲劳联动（v1.4 第二阶段）
        // ============================================
        val spaceState = _spaceState.value
        val baseWorkHours = spaceState.workHoursPerDay.toDouble()
        val adjustedWorkHours = FatigueBusiness.getAdjustedWorkHours(baseWorkHours, _lifePathType.value)
        val workStartHour = 9
        val workEndHour = (workStartHour + adjustedWorkHours.toInt()).coerceAtMost(22)

        // 工作时间段内累积疲劳和工时
        if (result.newState.hour in workStartHour until workEndHour) {
            val hourlyFatigue = 4 // 每小时工作产生4点疲劳
            val newFatigue = (updatedBody.fatigueLevel + hourlyFatigue).coerceIn(0, 100)
            val newDailyWorkHours = updatedBody.dailyWorkHours + 1.0
            updatedBody = updatedBody.copy(
                fatigueLevel = newFatigue,
                dailyWorkHours = newDailyWorkHours,
                weeklyWorkHours = body.weeklyWorkHours + 1.0
            )
        }

        // 非工作时间，疲劳自然缓慢恢复
        if (result.newState.hour !in workStartHour until workEndHour && updatedBody.fatigueLevel > 10) {
            val recoveryRate = when (result.newState.hour) {
                in 0..6 -> 3  // 深夜睡眠恢复更快
                else -> 1     // 其他时间缓慢恢复
            }
            updatedBody = updatedBody.copy(
                fatigueLevel = (updatedBody.fatigueLevel - recoveryRate).coerceIn(0, 100)
            )
        }

        // 每日结算（午夜0点）
        if (result.isNewDay) {
            val todayWorkHours = updatedBody.dailyWorkHours
            val isOvertime = todayWorkHours > baseWorkHours

            // 更新加班连续天数
            val newStreak = if (isOvertime) body.overtimeStreakDays + 1 else 0
            val newTotalOvertime = if (isOvertime) {
                body.totalOvertimeHours + (todayWorkHours - baseWorkHours)
            } else body.totalOvertimeHours

            updatedBody = updatedBody.copy(
                dailyWorkHours = 0.0,          // 重置今日工时
                overtimeStreakDays = newStreak,
                totalOvertimeHours = newTotalOvertime
            )

            // 每周结算（每7天重置周工时）
            if (_gameDay.value % 7 == 0) {
                updatedBody = updatedBody.copy(weeklyWorkHours = 0.0)
            }

            // 记录疲劳事件（每天最多一次）
            if (_lastFatigueEventDay != _gameDay.value) {
                _lastFatigueEventDay = _gameDay.value
                val fatigueMsg = GentleTextProvider.describeWorkDayMood(
                    updatedBody.fatigueLevel, newStreak
                )
                addEventLog(fatigueMsg)

                // 疲劳过高时发出额外提醒
                if (updatedBody.fatigueLevel > 80) {
                    val symptoms = FatigueBusiness.calculateBodyImpact(updatedBody).systolicSymptoms
                    if (symptoms.isNotEmpty()) {
                        addEventLog(GentleTextProvider.describeFatigueSymptoms(symptoms))
                    }
                }
            }
        }

        // 应用疲劳对身体和精神的影响（每4小时）
        if (result.newState.hour % 4 == 0 && updatedBody.fatigueLevel > 30) {
            val bodyImpact = FatigueBusiness.calculateBodyImpact(updatedBody)
            updatedBody = updatedBody.copy(
                energy = (updatedBody.energy + bodyImpact.energyDelta).coerceIn(0, 100),
                healthScore = (updatedBody.healthScore + bodyImpact.healthDelta).coerceIn(0, 100),
                immuneLevel = (updatedBody.immuneLevel + bodyImpact.immuneDelta).coerceIn(0, 100),
                mood = (updatedBody.mood + bodyImpact.moodDelta).coerceIn(0, 100),
                comfortLevel = (updatedBody.comfortLevel + bodyImpact.comfortDelta).coerceIn(0, 100)
            )

            // 更新精神状态
            val mentalImpact = FatigueBusiness.calculateMentalImpact(updatedBody, _mentalState.value)
            _mentalState.value = _mentalState.value.copy(
                anxiety = (_mentalState.value.anxiety + mentalImpact.anxietyDelta).coerceIn(0, 100),
                happiness = (_mentalState.value.happiness + mentalImpact.happinessDelta).coerceIn(0, 100),
                sleepQuality = (_mentalState.value.sleepQuality + mentalImpact.sleepQualityDelta).coerceIn(0, 100),
                workStress = (_mentalState.value.workStress + mentalImpact.workStressDelta).coerceIn(0, 100),
                burnoutRisk = (_mentalState.value.burnoutRisk + mentalImpact.burnoutRiskDelta).coerceIn(0, 100),
                senseOfControl = (_mentalState.value.senseOfControl + mentalImpact.controlDelta).coerceIn(0, 100)
            ).coerce()
        }

        // 3. 自动兜底机制：饿了自动吃、困了自动睡、健康低了自动休息
        // 只在深夜时段（0-6点）自动睡觉
        if (result.newState.hour in 0..6 && newEnergy < 30) {
            // 自动睡觉：恢复精力和心情，也恢复疲劳
            val energyRecovery = 50.coerceAtMost(100 - newEnergy)
            val moodRecovery = 20.coerceAtMost(100 - newMood)
            val healthRecovery = 10.coerceAtMost(100 - newHealth)
            val fatigueRecovery = 25.coerceAtMost(updatedBody.fatigueLevel)
            updatedBody = updatedBody.copy(
                energy = (newEnergy + energyRecovery).coerceIn(0, 100),
                mood = (newMood + moodRecovery).coerceIn(0, 100),
                healthScore = (newHealth + healthRecovery).coerceIn(0, 100),
                fatigueLevel = (updatedBody.fatigueLevel - fatigueRecovery).coerceIn(0, 100)
            )
            if (newEnergy < 20) { // 只在真的很困时记录日志
                addEventLog(GentleTextProvider.describeAutoSleep())
            }
        }

        // 极度饥饿时自动吃最便宜的食物
        if (newSatiety < 15) {
            val food = getCheapestFood()
            if (food != null) {
                val satietyRecovery = 40.coerceAtMost(100 - newSatiety)
                val cost = food.pricePer100g * food.typicalServing / 100.0
                val currentSavings = _spaceState.value.currentSavings
                val newSavings = (currentSavings - cost).coerceAtLeast(0.0)

                updatedBody = updatedBody.copy(
                    satiety = (newSatiety + satietyRecovery).coerceIn(0, 100),
                    mood = (updatedBody.mood).coerceIn(0, 100)
                )
                _spaceState.value = _spaceState.value.copy(currentSavings = newSavings)

                addEventLog(GentleTextProvider.describeAutoEat(food.name, cost))

                // 如果存款花完了，自动换成最便宜的房子
                if (newSavings < _spaceState.value.monthlyRent) {
                    _spaceState.value = _spaceState.value.copy(
                        addressName = "最便宜的住处",
                        monthlyRent = 500.0,
                        light = 30,
                        areaSqm = 10
                    ).coerce()
                    addEventLog("存款不够交房租，你自动搬到了更便宜的住处")
                }
            }
        }

        // 健康极低时自动休息
        if (newHealth < 15) {
            val healthRecovery = 15.coerceAtMost(100 - newHealth)
            val energyRecovery = 10.coerceAtMost(100 - updatedBody.energy)
            updatedBody = updatedBody.copy(
                healthScore = (updatedBody.healthScore + healthRecovery).coerceIn(0, 100),
                energy = (updatedBody.energy + energyRecovery).coerceIn(0, 100)
            )
            if (_eventLog.value.lastOrNull()?.contains("自动休息") != true) {
                addEventLog(GentleTextProvider.describeAutoRest())
            }
        }

        _bodyState.value = updatedBody.coerce()

        // ============================================
        // 4. 随机事件分发（V2.0 概率调优）
        // ============================================
        dispatchRandomEvents(result.newState.hour)

        // ============================================
        // 5. 科研进度推进（v2.1 科研与技术流通体系）
        // ============================================
        tickResearchProgress(1)
    }

    /**
     * 统一随机事件分发器
     *
     * 三种事件各有独立的触发窗口和概率，根据当前场景和时间分发：
     * - 孩童互动：傍晚居家/社区场景高概率，办公/劳作场景低概率
     * - 跨职业对话：邻里聚会、同事共事、节庆闲聊高概率，独处时段关闭
     * - 疲劳警告：加班时段高概率，休息日/深夜关闭
     */
    private fun dispatchRandomEvents(hour: Int) {
        val pathId = _currentLifePathId ?: return
        val space = _spaceState.value
        val body = _bodyState.value

        // ------ A. 孩童互动事件 ------
        // 条件：非童年自我、白天、不在深睡/极低能量时段
        if (!IdentityChildReactions.isChildhoodSelf(pathId)
            && hour in 15..21   // 下午到傍晚活跃时段
            && body.energy > 20  // 精力太低时不会遇到孩童（可能在睡觉/极度疲惫）
        ) {
            val reaction = IdentityChildReactions.getReaction(pathId)
            if (reaction != null) {
                // 根据居住场景调整触发权重
                val scenarioMultiplier = when {
                    space.addressName.contains("宿舍") || space.addressName.contains("工棚") -> 0.3
                    space.addressName.contains("居家") || space.addressName.contains("住宅") || space.addressName.contains("次卧") -> 1.0
                    space.commuteMinutesOneWay > 40 -> 0.4  // 远距离通勤，少接触孩童
                    else -> 0.6
                }
                val baseChance = reaction.triggerWeight / 12.0 * scenarioMultiplier
                if (Math.random() < baseChance) {
                    triggerChildBehaviorEvent()
                }
            }
        }

        // ------ B. 跨职业对话事件 ------
        // 条件：非独处、白天/傍晚、不是深夜
        if (hour in 10..20 && !pathId.isNullOrEmpty()) {
            // 聚会/节庆场景高概率（周末/假日），日常中低概率
            val isWeekend = _gameDay.value % 7 >= 5
            val isHolidayHour = hour in 17..20  // 下班后聚餐时间
            val baseChance = when {
                isWeekend && isHolidayHour -> 0.08   // 周末傍晚：8%
                isWeekend -> 0.04                    // 周末其他时间：4%
                isHolidayHour -> 0.05               // 工作日下班：5%
                else -> 0.02                        // 工作日其他时间：2%
            }
            // 独处场景关闭（通勤中、深夜、独居）
            val isAlone = space.privacy > 80 || space.commuteMinutesOneWay > 0 && hour in 7..9
            if (!isAlone && Math.random() < baseChance) {
                triggerCrossProfessionDialogue()
            }
        }

        // ------ C. 跨圈层相遇事件 ------
        dispatchCrossClassEncounter(hour)

        // ------ D. 日常细碎挫折 ------
        dispatchDailyFrustration(hour)

        // ------ E. 代际认知对话 ------
        dispatchGenerationalDialogue(hour)

        // ------ F1. 原子化心理事件 ------
        dispatchAtomizedPsychEvent(hour)

        // ------ F. 疲劳事件 ------
        // 条件：工作时段、疲劳累积中
        if (hour in 9..18 && body.energy < 50) {
            // 高强度劳作/加班后高概率，休息日低概率
            val isRestDay = hour in 12..13 || body.energy < 30
            val baseChance = when {
                body.fatigueLevel > 70 -> 0.15           // 极度疲劳：15%
                body.fatigueLevel > 50 -> 0.08           // 中度疲劳：8%
                body.fatigueLevel > 30 -> 0.03           // 轻度疲劳：3%
                else -> 0.0
            }
            // 休息时段大幅降低
            val adjustedChance = if (isRestDay) baseChance * 0.3 else baseChance
            if (Math.random() < adjustedChance) {
                val fatigueSymptom = when {
                    body.fatigueLevel > 80 -> listOf("腰酸背痛", "眼睛干涩", "头昏脑涨").random()
                    body.fatigueLevel > 60 -> listOf("肩颈僵硬", "犯困走神", "腰隐隐作痛").random()
                    else -> listOf("打了个哈欠", "活动了一下僵硬的脖子", "揉了揉发酸的眼睛").random()
                }
                addEventLog(GentleTextProvider.describeFatigueEvent(fatigueSymptom, body.fatigueLevel))
            }
        }
    }

    // ============================================
    // 🌟 手动操作
    // ============================================

    /** 吃饭：从食物列表选择 */
    fun showFoodPicker() {
        // 时段判断：仅早/中/晚三个饭点可正常打开
        val currentHour = gameHour.value
        val isBreakfast = currentHour in 6..9
        val isLunch = currentHour in 11..14
        val isDinner = currentHour in 17..20
        
        if (isBreakfast || isLunch || isDinner) {
            _showFoodPicker.value = true
        } else {
            // 非饭点提示
            addEventLog("还未到用餐时间，先做点别的事吧")
        }
    }

    fun hideFoodPicker() {
        _showFoodPicker.value = false
    }

    /** 清空宠物对话（展示完毕后调用，避免重复显示） */
    fun clearPetDialogue() {
        _petDialogue.value = null
    }

    /** 手动吃一份食物 */
    fun eatFood(food: com.example.townapp.data.FoodItem) {
        try {
            val body = _bodyState.value
            val satietyRecovery = 30.coerceAtMost(100 - body.satiety)
            val moodRecovery = 10.coerceAtMost(100 - body.mood)
            val cost = food.pricePer100g * food.typicalServing / 100.0
            val savings = _spaceState.value.currentSavings
            val newSavings = (savings - cost).coerceAtLeast(0.0)

            _bodyState.value = body.copy(
                satiety = (body.satiety + satietyRecovery).coerceIn(0, 100),
                mood = (body.mood + moodRecovery).coerceIn(0, 100),
                healthScore = (body.healthScore + kotlin.math.max(0, food.nutrientDensityScore.toInt() / 10)).coerceIn(0, 100),
                lastMealTime = System.currentTimeMillis(),
                lastMealName = food.name,
                updateTime = System.currentTimeMillis()
            ).coerce()
            _spaceState.value = _spaceState.value.copy(currentSavings = newSavings)

            addEventLog(GentleTextProvider.describeMealLove(food.name, food.note))
            // v1.3 世界观修正：进食后追加消费认知提示
            val currentLifePathId = _currentLifePathId
            if (currentLifePathId.isNotEmpty()) {
                val (_, sparkle, commentary) = GentleTextProvider.describeMealContextual(food.name, food.note, currentLifePathId)
                addEventLog("【闪光点】${sparkle}")
                addEventLog("【小城镇看到】${commentary}")
            }
            _showFoodPicker.value = false

            // 更新食物使用频率
            val currentCounts = _foodEatCount.value.toMutableMap()
            currentCounts[food.name] = (currentCounts[food.name] ?: 0) + 1
            _foodEatCount.value = currentCounts

            // 更新纪念馆统计
            _memorialMealCount.value += 1
            if (food.name.contains("泡面") || food.name.contains("方便面") || food.name.contains("速食面")) {
                _memorialInstantNoodleCount.value += 1
            }
            _memorialMoneyDelta.value -= cost

            // 触发宠物反馈
            val reply = PetManager.getFoodReply(
                foodName = food.name,
                foodNote = food.note,
                category = food.category,
                nutritionalScore = food.nutrientDensityScore
            )
            if (reply != null) {
                _petDialogue.value = reply
            }
        } catch (e: Exception) {
            android.util.Log.e("TownViewModel", "吃饭操作异常: ${e.message}", e)
            addEventLog("出了点小问题，没吃成")
        }
    }

    // ============================================
    // 📅 周计划管理
    // ============================================

    /** 设置周计划 */
    fun setWeeklyPlan(plan: Map<Int, DailyPlan>) {
        _weeklyPlan.value = plan
    }

    /** 获取今日计划 */
    fun getTodayPlan(): DailyPlan? {
        val dayOfWeek = (_gameDay.value % 7).let { if (it == 0) 7 else it }
        return _weeklyPlan.value[dayOfWeek]
    }

    /** 执行今日自动计划
     *  v2.0 自动生活模式：工作日自动托管基础生活开销，周末开放手动精细操作
     */
    fun executeTodayPlan() {
        if (_todayPlanExecuted.value) return

        val todayPlan = getTodayPlan()
        val dayOfWeek = (_gameDay.value % 7).let { if (it == 0) 7 else it }
        val isWorkday = dayOfWeek in 1..5

        // 工作日 + 自动生活开启：自动扣除简化三餐与基础生活费
        if (isWorkday && _autoLifeEnabled.value) {
            val body = _bodyState.value
            val space = _spaceState.value
            val tier = _autoLifeTier.value

            // 简化版三餐开销（按档位或当日饮食计划）
            val dietPlan = _dailyDietPlan.value
            val hasMealPlan = dietPlan.breakfast.isNotEmpty() || dietPlan.lunch.isNotEmpty() || dietPlan.dinner.isNotEmpty()
            val dailyFoodCost = if (hasMealPlan) {
                DietSystem.calculateDailyEffect(dietPlan).totalCost
            } else {
                tier.dailyFoodCost  // 按档位自动估算
            }
            val dailyLivingCost = tier.dailyLivingCost
            val totalDailyCost = dailyFoodCost + dailyLivingCost

            val newSavings = (space.currentSavings - totalDailyCost).coerceAtLeast(0.0)
            _spaceState.value = space.copy(currentSavings = newSavings)

            // 按档位恢复基础状态（简朴恢复少，宽裕恢复多）
            val (satietyRecovery, moodRecovery, healthDelta) = when (tier) {
                AutoLifeTier.SIMPLE -> Triple(50, 8, 0)
                AutoLifeTier.NORMAL -> Triple(80, 15, if (dailyFoodCost >= 50) 1 else 0)
                AutoLifeTier.COMFORTABLE -> Triple(90, 25, 1)
            }
            _bodyState.value = body.copy(
                satiety = (body.satiety + satietyRecovery.coerceAtMost(100 - body.satiety)).coerceIn(0, 100),
                mood = (body.mood + moodRecovery.coerceAtMost(100 - body.mood)).coerceIn(0, 100),
                healthScore = (body.healthScore + healthDelta).coerceIn(0, 100),
                updateTime = System.currentTimeMillis()
            ).coerce()

            if (totalDailyCost > 0) {
                _autoLifeSummary.value = "自动支出：餐费 ¥${dailyFoodCost.toInt()} · 生活费 ¥${dailyLivingCost.toInt()}（${tier.label}）"
                addEventLog("工作日自动开销：餐费 ¥${dailyFoodCost.toInt()} + 基础生活费 ¥${dailyLivingCost.toInt()}（${tier.label}）")
                if (tier == AutoLifeTier.SIMPLE) {
                    addEventLog(GentleTextProvider.comfortSimpleLife())
                }
            } else {
                _autoLifeSummary.value = ""
            }
        }

        // 执行工作/学习/外出计划（工作日与周末都执行）
        if (todayPlan != null) {
            if (todayPlan.workHours > 0) work()
            if (todayPlan.studyHours > 0) study()
            if (todayPlan.goOut) goOut()
        }

        _todayPlanExecuted.value = true
        val dayLabel = if (isWorkday) "工作日" else "周末"
        addEventLog("$dayLabel 计划已执行")
    }

    /** 设置自动生活档位 */
    fun setAutoLifeTier(tier: AutoLifeTier) {
        _autoLifeTier.value = tier
        addEventLog("自动生活档位已切换为：${tier.label}")
    }

    /** 手动设置消费模式 */
    fun setShoppingMode(mode: ShoppingMode) {
        _shoppingMode.value = mode
        addEventLog("消费模式已切换为：${mode.label}")
        calculateWeeklyExpense()
    }

    /** 根据经济状况自动更新消费模式 */
    fun updateShoppingMode() {
        val space = _spaceState.value
        val savings = space.currentSavings
        val rent = space.monthlyRent
        val income = space.monthlyIncome

        val newMode = when {
            savings < rent * 1.5 -> ShoppingMode.SURVIVAL
            savings > rent * 6 && income > 8000 -> ShoppingMode.PREMIUM
            else -> ShoppingMode.BALANCED
        }

        if (_shoppingMode.value != newMode) {
            _shoppingMode.value = newMode
            val msg = when (newMode) {
                ShoppingMode.SURVIVAL -> "存款紧张，已自动切换为刚需消费模式"
                ShoppingMode.PREMIUM -> "经济状况良好，消费模式已提升"
                else -> "消费模式已回归均衡"
            }
            addEventLog(msg)
        }
    }

    /** 设置医疗偏好 */
    fun setMedicalPreference(preference: MedicalPreference) {
        _medicalPreference.value = preference
        addEventLog("医疗偏好已切换为：${preference.label}")
        calculateWeeklyExpense()
    }

    /** 设置疾病状态（用于测试或事件触发） */
    fun setIllness(illness: IllnessType) {
        _currentIllness.value = illness
        if (illness != IllnessType.NONE) {
            val msg = when (illness) {
                IllnessType.ACUTE -> "身体不太舒服，可能需要买点药"
                IllnessType.CHRONIC -> "慢性病需要长期调理，是一笔固定开销"
                else -> ""
            }
            if (msg.isNotEmpty()) addEventLog(msg)
        }
        calculateWeeklyExpense()
    }

    /** 设置爱好预算档位 */
    fun setHobbyBudgetTier(tier: HobbyBudgetTier) {
        _hobbyBudgetTier.value = tier
        addEventLog("爱好预算已切换为：${tier.label}（${tier.description}）")
        calculateWeeklyExpense()
    }

    /** 设置当前主爱好 */
    fun setCurrentHobby(hobby: String) {
        _currentHobby.value = hobby
        addEventLog("当前爱好已设置为：$hobby")
    }

    /** 设置虚荣性格标签 */
    fun setVanityTrait(isVanity: Boolean) {
        _vanityTrait.value = isVanity
        addEventLog(if (isVanity) "性格标签更新：愿意为好感受和形象投入额外成本" else "性格标签更新：务实理性，优先性价比")
    }

    /** 练习爱好，提升熟练度 */
    fun practiceHobby() {
        val current = _hobbyProficiency.value
        if (current < 100) {
            _hobbyProficiency.value = (current + kotlin.random.Random.nextInt(2, 6)).coerceAtMost(100)
        }
    }

    /** 触发爱好衍生随机事件 */
    fun triggerHobbyRandomEvent(): String? {
        val tier = _hobbyBudgetTier.value
        val proficiency = _hobbyProficiency.value
        val isVanity = _vanityTrait.value
        val hobby = _currentHobby.value

        val event = when (kotlin.random.Random.nextInt(100)) {
            in 0..15 -> {
                // 正向事件：15% 概率
                when {
                    proficiency >= 70 -> "你在$hobby 方面小有成就，收到一场小型演出邀约，预计获得额外收入 200 元。"
                    proficiency >= 40 -> "你的 $hobby 作品被朋友称赞，圈层里的认可度在悄悄提升。"
                    else -> "一次专注的 $hobby 练习后，你感到久违的平静和满足。"
                }
            }
            in 16..35 -> {
                // 中性事件：20% 概率
                when {
                    hobby == "music" -> "琴弦老化，系统自动采购新琴弦，本周爱好开销增加 30 元。"
                    hobby == "photography" -> "存储卡满了，整理照片时发现几张被遗忘的好片子。"
                    hobby == "calligraphy" -> "墨汁用完，顺手买了两本新字帖，本周开销增加 25 元。"
                    else -> "爱好器材需要正常维护，产生了一笔小额耗材费用。"
                }
            }
            in 36..50 -> {
                // 诱惑事件：15% 概率（虚荣性格更容易中招）
                val actualChance = if (isVanity && tier == HobbyBudgetTier.PREMIUM) 100 else if (isVanity) 70 else 30
                if (kotlin.random.Random.nextInt(100) < actualChance) {
                    when {
                        hobby == "music" -> "商家推送高端私教课广告，你心动了，本周课程预算增加 150 元。"
                        hobby == "photography" -> "看到新款镜头发布，手痒难耐，溢价购入，本周器材开销增加 500 元。"
                        else -> "朋友邀请参加一场高端圈层活动，门票和社交开销让本周预算上涨。"
                    }
                } else null
            }
            else -> null
        }

        event?.let {
            _lastHobbyEvent.value = it
            addEventLog(it)
        }
        return event
    }

    /** 年龄段专属随机生活事件（中年赡养、老年养老等） */
    fun triggerAgeLifeEvent(): String? {
        val age = _playerAge.value
        val loveState = _loveState.value
        val savings = _spaceState.value.currentSavings
        val event = when {
            // 中年事件（31-59岁）
            age in 31..59 -> when (kotlin.random.Random.nextInt(100)) {
                in 0..8 -> {
                    val cost = if (loveState.status == com.example.townapp.data.LoveStatus.MARRIED) 500.0 else 300.0
                    if (savings > cost) {
                        _spaceState.value = _spaceState.value.copy(currentSavings = savings - cost)
                        "父母身体不适，你请假带他们去医院检查，花费 ¥${cost.toInt()}。赡养长辈的开支，是中年人的常态。"
                    } else {
                        "父母打来电话说身体不太好，你手头紧，只能先买些常用药寄回去。"
                    }
                }
                in 9..15 -> {
                    if (loveState.hasChildren) {
                        val eduCost = 800.0
                        if (savings > eduCost) {
                            _spaceState.value = _spaceState.value.copy(currentSavings = savings - eduCost)
                            "子女新学期开学，学费、书本费、兴趣班杂费一次性支出 ¥${eduCost.toInt()}。"
                        } else {
                            "孩子的补习班费用又涨了，存款紧张，你开始犹豫要不要砍掉一些非必要课程。"
                        }
                    } else null
                }
                in 16..22 -> "工作上又一个加班到深夜的周末，你忽然意识到，自己已经很久没有好好吃一顿饭了。"
                else -> null
            }
            // 老年事件（60岁+）
            age >= 60 -> when (kotlin.random.Random.nextInt(100)) {
                in 0..10 -> {
                    val pension = _spaceState.value.monthlyIncome
                    if (pension < 2000) {
                        "退休金每月 ¥${pension.toInt()}，日常开销勉强够用，但不敢生病。你开始理解什么叫'老无所依'。"
                    } else {
                        "和老友在公园下棋，聊起了年轻时的选择。他说：'你现在这样就很好。'"
                    }
                }
                in 11..18 -> {
                    if (savings < 5000) {
                        "存款见底，慢性病药费又涨了。你开始计算，剩下的钱还能撑几个月。"
                    } else {
                        "去医院复查，医生说你保养得不错。你笑了笑，心想：不过是少吃了几顿好的。"
                    }
                }
                in 19..25 -> "社区组织老年活动，你去参加了一次合唱队。没想到，晚年还能认识新朋友。"
                else -> null
            }
            else -> null
        }
        event?.let { addEventLog(it) }
        return event
    }

    /** 根据疾病、年龄、偏好自动选择药品类型 */
    private fun determineMedicineType(): MedicineType {
        val illness = _currentIllness.value
        val age = _playerAge.value
        val preference = _medicalPreference.value

        if (illness == IllnessType.NONE) return MedicineType.WESTERN

        return when (preference) {
            MedicalPreference.MODERN -> MedicineType.WESTERN
            MedicalPreference.TRADITIONAL -> {
                // 传统派：急症+老年才用西药，其他中药
                if (illness == IllnessType.ACUTE && age >= 60) MedicineType.WESTERN else MedicineType.CHINESE
            }
            MedicalPreference.PRAGMATIC -> {
                // 务实派：急症西药，慢性调理中药
                if (illness == IllnessType.ACUTE) MedicineType.WESTERN else MedicineType.CHINESE
            }
        }
    }

    /** 计算每周医疗开销 */
    private fun calculateMedicalExpense(): Pair<Double, Double> {
        val illness = _currentIllness.value
        val age = _playerAge.value
        val mode = _shoppingMode.value
        val preference = _medicalPreference.value

        if (illness == IllnessType.NONE) return Pair(0.0, 0.0)

        // 基础药品开销（根据疾病类型和药品类型）
        val medicineType = determineMedicineType()
        val baseMedical = when (illness) {
            IllnessType.ACUTE -> when (medicineType) {
                MedicineType.WESTERN -> 35.0   // 西药感冒药等
                MedicineType.CHINESE -> 25.0   // 中成药
                MedicineType.MIXED -> 45.0
            }
            IllnessType.CHRONIC -> when (medicineType) {
                MedicineType.WESTERN -> 60.0   // 长期西药
                MedicineType.CHINESE -> 100.0  // 中药汤剂调理贵
                MedicineType.MIXED -> 120.0
            }
            else -> 0.0
        }

        // 年龄系数（老年医疗开销更高）
        val ageMultiplier = when {
            age < 35 -> 0.9
            age < 60 -> 1.1
            else -> 1.4
        }

        // 偏好系数（传统派滋补溢价高）
        val preferenceMultiplier = when (preference) {
            MedicalPreference.MODERN -> 1.0
            MedicalPreference.TRADITIONAL -> 1.3
            MedicalPreference.PRAGMATIC -> 0.9
        }

        // 消费档位系数（刚需只买基础药，溢价买进口/名贵药材）
        val (modeMultiplier, premiumRate) = when (mode) {
            ShoppingMode.SURVIVAL -> Pair(0.7, 0.0)    // 只买最便宜的
            ShoppingMode.BALANCED -> Pair(1.0, 0.15)   // 常规药品+少量调理
            ShoppingMode.PREMIUM -> Pair(1.4, 0.35)    // 进口西药/燕窝人参
        }

        val medicalBase = baseMedical * ageMultiplier * preferenceMultiplier * modeMultiplier
        val medicalPremium = medicalBase * premiumRate

        return Pair(medicalBase, medicalPremium)
    }

    /** 健康预警检测 */
    private fun checkMedicalAlert() {
        val medicalTotal = _weeklyMedicalExpense.value
        val income = _spaceState.value.monthlyIncome
        // 医疗开销超过月收入 20% 视为高负担
        if (medicalTotal > income * 0.2) {
            _highMedicalWeeks++
            if (_highMedicalWeeks >= 3) {
                addEventLog("医疗开销已连续数周偏高，或许该调整用药偏好或消费档位了。身体要紧，但也要量力而行。")
                _highMedicalWeeks = 0
            }
        } else {
            _highMedicalWeeks = 0
        }
    }

    // ============================================
    // 点到面反向消费计算系统 v2.0
    // 先定总预算 → 拆分板块 → 随机生成清单
    // ============================================

    /** 预算区间配置（根据档位与月收入，设保底红线） */
    private fun calculateBudgetRange(mode: ShoppingMode, monthlyIncome: Double): Pair<Double, Double> {
        val base = when (mode) {
            ShoppingMode.SURVIVAL -> monthlyIncome * 0.25 to monthlyIncome * 0.32
            ShoppingMode.BALANCED -> monthlyIncome * 0.35 to monthlyIncome * 0.48
            ShoppingMode.PREMIUM -> monthlyIncome * 0.50 to monthlyIncome * 0.75
        }
        // 生存红线：即便收入极低，也不能跌破基础生存底线
        val survivalFloor = 800.0 to 1000.0
        val balancedFloor = 1400.0 to 1800.0
        val premiumFloor = 2500.0 to 3500.0
        return when (mode) {
            ShoppingMode.SURVIVAL -> kotlin.math.max(base.first, survivalFloor.first) to kotlin.math.max(base.second, survivalFloor.second)
            ShoppingMode.BALANCED -> kotlin.math.max(base.first, balancedFloor.first) to kotlin.math.max(base.second, balancedFloor.second)
            ShoppingMode.PREMIUM -> kotlin.math.max(base.first, premiumFloor.first) to kotlin.math.max(base.second, premiumFloor.second)
        }
    }

    /** 五大板块预算分配比例 */
    private fun getCategoryAllocations(mode: ShoppingMode): Map<String, Double> = when (mode) {
        ShoppingMode.SURVIVAL -> mapOf(
            "food" to 0.55, "clothing" to 0.08, "medical" to 0.07,
            "commute" to 0.20, "hobby" to 0.10
        )
        ShoppingMode.BALANCED -> mapOf(
            "food" to 0.52, "clothing" to 0.15, "medical" to 0.08,
            "commute" to 0.12, "hobby" to 0.13
        )
        ShoppingMode.PREMIUM -> mapOf(
            "food" to 0.40, "clothing" to 0.20, "medical" to 0.10,
            "commute" to 0.10, "hobby" to 0.20
        )
    }

    /** 年龄段专属预算比例修正（在档位基础上微调） */
    private fun applyAgeAllocationModifier(
        base: Map<String, Double>,
        stage: com.example.townapp.data.LifeStage
    ): Map<String, Double> {
        val adjustments = when (stage) {
            com.example.townapp.data.LifeStage.YOUTH -> mapOf(
                "clothing" to 0.05, "hobby" to 0.05, "medical" to -0.03, "commute" to -0.02, "food" to -0.05
            )
            com.example.townapp.data.LifeStage.MIDDLE_AGE -> mapOf(
                "medical" to 0.08, "food" to 0.03, "clothing" to -0.03, "hobby" to -0.05, "commute" to -0.03
            )
            com.example.townapp.data.LifeStage.SENIOR -> mapOf(
                "medical" to 0.15, "food" to 0.05, "clothing" to -0.10, "hobby" to -0.05, "commute" to -0.05
            )
            else -> emptyMap()
        }
        if (adjustments.isEmpty()) return base
        val result = base.mapValues { (k, v) -> (v + (adjustments[k] ?: 0.0)).coerceAtLeast(0.02) }
        val sum = result.values.sum()
        return result.mapValues { (_, v) -> v / sum }
    }

    /** 各品类物品池（基础 vs 溢价） */
    private fun getItemPool(category: String, isPremium: Boolean): List<String> = when (category) {
        "food" -> if (isPremium) listOf(
            "网红奶茶", "精致外卖", "进口水果", "高端零食", "网红餐厅打卡",
            "精品咖啡", "进口牛排", "日式料理", "甜品下午茶"
        ) else listOf(
            "粗粮主食", "家常蔬菜", "白米饭", "馒头", "鸡蛋", "豆腐",
            "当季青菜", "挂面", "土豆", "西红柿炒蛋"
        )
        "clothing" -> if (isPremium) listOf(
            "名牌运动鞋", "潮流外套", "设计师款T恤", "轻奢配饰", "品牌包包",
            "联名款袜子", "限量款帽子"
        ) else listOf(
            "基础T恤", "平价袜子", "保暖内衣", "工装裤", "帆布鞋",
            "换季打底衫", "棉质睡衣"
        )
        "medical" -> {
            val preference = _medicalPreference.value
            val age = _playerAge.value
            when {
                // 青年默认西药优先（见效要快，不影响工作）
                age <= 30 -> if (isPremium)
                    listOf("进口维生素", "高端体检套餐", "进口止痛药", "快速退烧片") else
                    listOf("平价感冒药", "基础消炎药", "退烧药", "创可贴", "布洛芬")
                // 老年默认中药康养，急症才用西药（由 _currentIllness 控制急症切换）
                age >= 60 -> if (isPremium)
                    listOf("人参滋补", "燕窝礼盒", "老字号阿胶", "慢性病调理汤剂") else
                    listOf("普通草药", "中成药", "养生茶包", "拔罐套装", "中药汤剂")
                // 中年：小病西药，慢性病中药调理
                preference == MedicalPreference.MODERN -> if (isPremium)
                    listOf("进口维生素", "高端体检套餐", "进口止痛药") else
                    listOf("平价感冒药", "基础消炎药", "退烧药", "创可贴")
                preference == MedicalPreference.TRADITIONAL -> if (isPremium)
                    listOf("人参滋补", "燕窝礼盒", "老字号阿胶", "名贵中药材") else
                    listOf("普通草药", "中成药", "养生茶包", "拔罐套装")
                else -> if (isPremium)
                    listOf("复合维生素", "进口保健品") else
                    listOf("按需购药", "基础调理药材", "常备药品")
            }
        }
        "commute" -> if (isPremium) listOf(
            "网约车通勤", "打车出行", "自驾油费", "代驾服务"
        ) else listOf(
            "公交地铁", "共享单车", "步行通勤", "拼车出行"
        )
        "hobby" -> {
            val hobby = _currentHobby.value
            if (isPremium) listOf(
                "高端私教课", "专业器材配件", "圈层交流会门票", "品质升级装备",
                "线下大师班", "${hobby}专业道具"
            ) else listOf(
                "公园散步", "居家练习", "免费线上教程", "基础耗材",
                "图书馆借阅", "社区活动室"
            )
        }
        else -> emptyList()
    }

    /** 依托人物标签，在单板块预算内随机生成采购物品清单 */
    private fun generateCategoryItems(
        category: String,
        budget: Double,
        mode: ShoppingMode,
        isVanity: Boolean
    ): List<ShoppingListItem> {
        val items = mutableListOf<ShoppingListItem>()
        val rng = kotlin.random.Random

        // 溢价概率：虚荣性格更高，刚需模式几乎为零
        val premiumChance = when (mode) {
            ShoppingMode.SURVIVAL -> if (isVanity) 5 else 2
            ShoppingMode.BALANCED -> if (isVanity) 25 else 12
            ShoppingMode.PREMIUM -> if (isVanity) 45 else 28
        }

        // 预算越高，采购项数越多（但不过度膨胀）
        val itemCount = when {
            budget < 100 -> rng.nextInt(1, 3)
            budget < 300 -> rng.nextInt(2, 4)
            budget < 600 -> rng.nextInt(3, 5)
            else -> rng.nextInt(4, 7)
        }

        repeat(itemCount) {
            val isPremiumItem = rng.nextInt(100) < premiumChance
            val pool = getItemPool(category, isPremiumItem)
            if (pool.isNotEmpty()) {
                val name = pool.random(rng)
                items.add(ShoppingListItem(category, name, isPremiumItem))
            }
        }

        // 去重
        return items.distinctBy { it.name }
    }

    /** 生成完整本周采购清单 */
    private fun generateWeeklyShoppingList(
        categoryBudgets: Map<String, Double>
    ): List<ShoppingListItem> {
        val mode = _shoppingMode.value
        val isVanity = _vanityTrait.value

        val allItems = mutableListOf<ShoppingListItem>()
        categoryBudgets.forEach { (category, budget) ->
            if (budget > 0) {
                allItems += generateCategoryItems(category, budget, mode, isVanity)
            }
        }
        return allItems
    }

    /** 计算本周预估开销（点到面反向模式） */
    fun calculateWeeklyExpense() {
        val space = _spaceState.value
        val mode = _shoppingMode.value
        val age = _playerAge.value
        val stage = com.example.townapp.data.LifeStage.fromAge(age)

        // 婚后收入合并（夫妻双收入）
        val isMarried = _loveState.value.status == com.example.townapp.data.LoveStatus.MARRIED
        val baseIncome = space.monthlyIncome
        val income = if (isMarried) baseIncome * 1.6 else baseIncome

        // 第一步：确定本周总预算区间
        val (budgetMin, budgetMax) = calculateBudgetRange(mode, income)
        val totalBudget = kotlin.random.Random.nextDouble(budgetMin, budgetMax)

        // 事件临时调整（预留接口，可扩展面试/相亲/失业等事件）
        val adjustedBudget = totalBudget  // TODO: applyEventBudgetAdjustment

        // 第二步：拆分五大板块预算（叠加年龄段修正）
        val baseAllocations = getCategoryAllocations(mode)
        val allocations = applyAgeAllocationModifier(baseAllocations, stage)
        val categoryBudgets = allocations.mapValues { (_, ratio) -> adjustedBudget * ratio }

        // 第三步：随机生成采购物品清单
        val shoppingList = generateWeeklyShoppingList(categoryBudgets)

        // 计算刚需 vs 溢价金额（按清单中溢价项占比估算）
        var baseCost = 0.0
        var premiumCost = 0.0
        categoryBudgets.forEach { (category, budget) ->
            val catItems = shoppingList.filter { it.category == category }
            if (catItems.isNotEmpty()) {
                val premiumCount = catItems.count { it.isPremium }
                val premiumRatio = premiumCount.toDouble() / catItems.size
                premiumCost += budget * premiumRatio
                baseCost += budget * (1 - premiumRatio)
            } else {
                // 无具体清单时，全部算入刚需保底
                baseCost += budget
            }
        }

        val totalCost = baseCost + premiumCost
        val premiumRatio = if (totalCost > 0) ((premiumCost / totalCost) * 100).toInt() else 0

        // 医疗预警（从医疗板块预算触发）
        val medicalBudget = categoryBudgets["medical"] ?: 0.0
        _weeklyMedicalExpense.value = medicalBudget
        checkMedicalAlert()

        _weeklyExpense.value = WeeklyExpense(
            totalBudget = adjustedBudget,
            baseCost = baseCost,
            premiumCost = premiumCost,
            totalCost = totalCost,
            premiumRatio = premiumRatio,
            categoryBreakdown = categoryBudgets,
            shoppingList = shoppingList
        )
    }

    /** 新的一天开始：重置执行标志并尝试自动执行 */
    fun onNewDayStart() {
        if (_todayPlanExecuted.value) return  // 防止重复触发
        _todayPlanExecuted.value = false
        updateShoppingMode()
        calculateWeeklyExpense()
        // 每日有 20% 概率触发爱好随机事件（系统自动）
        if (kotlin.random.Random.nextInt(100) < 20) {
            triggerHobbyRandomEvent()
        }
        // 每日有 15% 概率触发年龄段专属生活事件（中年赡养/老年养老等）
        if (kotlin.random.Random.nextInt(100) < 15) {
            triggerAgeLifeEvent()
        }
        executeTodayPlan()
    }

    /** 是否允许手动操作（周日编辑模式或今日计划未执行） */
    fun canManualAction(): Boolean {
        return isEditMode.value || !_todayPlanExecuted.value
    }

    /** 添加本周事件（用于周简报） */
    private fun addWeeklyEvent(event: String) {
        val current = _weeklyEvents.value.toMutableList()
        current.add(event)
        _weeklyEvents.value = current
    }

    /** 清空本周事件（新的一周开始） */
    private fun clearWeeklyEvents() {
        _weeklyEvents.value = emptyList()
    }

    /** 更新当前周数 */
    private fun updateCurrentWeek() {
        _currentWeek.value = (_gameDay.value + 6) / 7
    }

    /** 触发周简报 */
    fun triggerWeeklyBrief() {
        _showWeeklyBrief.value = true
    }

    /** 关闭周简报 */
    fun dismissWeeklyBrief() {
        _showWeeklyBrief.value = false
    }

    /** 休息/睡觉 */
    fun rest() {
        try {
            val body = _bodyState.value
            val currentHour = gameHour.value

            // 基础恢复值
            var energyRecovery = 30.coerceAtMost(100 - body.energy)
            var healthRecovery = 10.coerceAtMost(100 - body.healthScore)
            var moodChange = 10.coerceAtMost(100 - body.mood) - body.mood

            // 时段判断
            when {
                // 深夜（23点后）休息：额外降低疲惫值
                currentHour >= 23 || currentHour < 5 -> {
                    energyRecovery += 20  // 额外恢复
                    addEventLog(GentleTextProvider.describeRest("night"))
                }
                // 白天频繁休息（10-17点）：增加轻微空虚值
                currentHour in 10..17 -> {
                    moodChange = -5  // 空虚感
                    addEventLog(GentleTextProvider.describeRest("day_frequent"))
                }
                else -> {
                    addEventLog(GentleTextProvider.describeRest("normal"))
                }
            }

            _bodyState.value = body.copy(
                energy = (body.energy + energyRecovery).coerceIn(0, 100),
                healthScore = (body.healthScore + healthRecovery).coerceIn(0, 100),
                mood = (body.mood + moodChange).coerceIn(0, 100),
                updateTime = System.currentTimeMillis()
            ).coerce()

            // 更新纪念馆统计
            _memorialSelfCareActions.value += 1
            if (currentHour >= 23 || currentHour < 5) {
                _memorialGoodSleepDays.value += 1
            }

            // 偶尔给出安全空间的反馈，和休息文案交替出现
            val restMessage = if (Math.random() < 0.5) {
                GentleTextProvider.describeRestLove()
            } else {
                GentleTextProvider.describePrivateSpace()
            }
            addEventLog(restMessage)
        } catch (e: Exception) {
            android.util.Log.e("TownViewModel", "休息操作异常: ${e.message}", e)
            addEventLog("出了点小问题，没休息成")
        }
    }

    fun goOut() {
        try {
            val body = _bodyState.value
            val energyCost = 15
            val remaining = body.energy - energyCost

            _bodyState.value = body.copy(
                energy = remaining.coerceIn(0, 100),
                mood = (body.mood + 10).coerceIn(0, 100),
                updateTime = System.currentTimeMillis()
            ).coerce()

            if (remaining < 0) {
                addEventLog("精力耗尽，你硬撑着出了门")
            }
            addEventLog(GentleTextProvider.describeGoOut())
        } catch (e: Exception) {
            android.util.Log.e("TownViewModel", "出门操作异常: ${e.message}", e)
            addEventLog("出门时出了点小问题")
        }
    }

    fun work() {
        try {
            val body = _bodyState.value
            val energyCost = 25
            val remaining = body.energy - energyCost

            _bodyState.value = body.copy(
                energy = remaining.coerceIn(0, 100),
                fatigueLevel = (body.fatigueLevel + 10).coerceIn(0, 100),
                updateTime = System.currentTimeMillis()
            ).coerce()

            if (remaining < 0) {
                addEventLog("精疲力竭，你硬撑着完成了工作")
            }
            addEventLog(GentleTextProvider.describeWork())
        } catch (e: Exception) {
            android.util.Log.e("TownViewModel", "工作操作异常: ${e.message}", e)
            addEventLog("工作时出了点小问题")
        }
    }

    fun study() {
        try {
            val body = _bodyState.value
            val energyCost = 15
            val remaining = body.energy - energyCost

            _bodyState.value = body.copy(
                energy = remaining.coerceIn(0, 100),
                updateTime = System.currentTimeMillis()
            ).coerce()

            if (remaining < 0) {
                addEventLog("眼皮打架，你硬撑着看了几行字")
            }
            addEventLog(GentleTextProvider.describeStudy())
        } catch (e: Exception) {
            android.util.Log.e("TownViewModel", "学习操作异常: ${e.message}", e)
            addEventLog("学习时出了点小问题")
        }
    }

    fun idle() {
        try {
            val body = _bodyState.value
            
            _bodyState.value = body.copy(
                mood = (body.mood + 5).coerceIn(0, 100),
                updateTime = System.currentTimeMillis()
            ).coerce()

            addEventLog(GentleTextProvider.describeIdle())
        } catch (e: Exception) {
            android.util.Log.e("TownViewModel", "发呆操作异常: ${e.message}", e)
            addEventLog("发呆时出了点小问题")
        }
    }

    /**
     * 绑定人生身份（v10 融合新增）
     * 纯内存模式：只更新内存中的状态
     *
     * 用户选择了一条人生路径后，调用此方法：
     * 1. 更新 _currentLifePathId
     * 2. 加载对应身份的默认空间配置到 UserSpaceState
     * 3. 记录选择事件到日志
     */
    fun bindIdentity(pathId: String) {
        viewModelScope.launch {
            try {
                val binding = LifePathBindingData.getBinding(pathId) ?: return@launch
                val space = binding.spaceConfig

                // 1. 更新用户身份（纯内存）
                _currentLifePathId = pathId

                // 2. 加载绑定空间配置
                _spaceState.value = UserSpaceState(
                    userId = 1,
                    addressName = space.addressName,
                    areaSqm = space.areaSqm,
                    monthlyRent = space.monthlyRent,
                    monthlyIncome = space.monthlyIncome,
                    light = space.light,
                    noise = space.noise,
                    privacy = space.privacy,
                    furnitureLevel = space.furnitureLevel,
                    commuteMinutesOneWay = space.commuteMinutesOneWay,
                    workHoursPerDay = space.workHoursPerDay,
                    currentSavings = space.currentSavings,
                    updateTime = System.currentTimeMillis()
                )

                // 3. 记录日志
                val welcomeMsg = GentleTextProvider.welcomeIdentity(pathId, binding.pathTitle)
                addEventLog(welcomeMsg)

                // 4. 更新纪念馆：居住地点数
                _memorialPlacesLived.value += 1

                StructuredLogger.i("TownViewModel", "身份绑定成功: ${binding.pathTitle} (pathId=$pathId)")
            } catch (e: Exception) {
                android.util.Log.e("TownViewModel", "身份绑定异常: ${e.message}", e)
                addEventLog("选好了。出了点小问题，但不影响你开始。")
            }
        }
    }

    // ============================================
    // v1.3 时薪-物品价值锐评体系
    // ============================================

    /** 当前时薪（元/小时），无收入时返回 0.0 */
    fun getHourlyWage(): Double {
        val pathId = _currentLifePathId ?: return 0.0
        return IdentityHourlyRateTable.findByPathId(pathId)?.hourlyWage ?: 0.0
    }

    /** 计算某件物品所需劳动小时数。无收入者返回 -1 表需降级文案。 */
    fun getWorkHourCost(itemPrice: Double): Double {
        val pathId = _currentLifePathId ?: return -1.0
        val rate = IdentityHourlyRateTable.findByPathId(pathId) ?: return -1.0
        return rate.workHourCost(itemPrice)
    }

    /** 获取时薪锐评文案（第四段） */
    fun getHourlyRateCommentary(itemPrice: Double, category: String): String {
        val pathId = _currentLifePathId ?: ""
        return GentleTextProvider.describeHourlyRateCommentary(
            itemPrice, getWorkHourCost(itemPrice), getHourlyWage(), category, pathId
        )
    }

    /**
     * 获取物品的完整五段式文案（V2.0 标准接口）
     *
     * @param itemName 物品名
     * @param itemPrice 物品价格
     * @param category 品类
     * @param sparkleDesc 闪光点描述
     * @param bodyFeeling 体感描述
     */
    fun getItemFiveSegments(
        itemName: String,
        itemPrice: Double,
        category: String,
        sparkleDesc: String = "",
        bodyFeeling: String = ""
    ): String {
        val workCost = getWorkHourCost(itemPrice)
        val hourlyWage = getHourlyWage()

        // 1. 体感（可由外部传入，默认通用描述）
        val feeling = bodyFeeling.ifEmpty {
            "你拿起${itemName}，感受了一下它的分量。每一件东西背后，都是你用时间换来的。"
        }

        // 2. 闪光点
        val sparkle = sparkleDesc.ifEmpty {
            "每件东西都有它的价值——不在于价格，在于它在你生活里的位置。"
        }

        // 3. 个人工时锐评
        val pathId = _currentLifePathId ?: ""
        val personalCost = GentleTextProvider.describeHourlyRateCommentary(
            itemPrice, workCost, hourlyWage, category, pathId
        )

        // 4. 时代成本解析（有通胀年份且有时薪才输出）
        val currentYear = _currentYear.value
        val eraCost: String = if (currentYear > 2024 && hourlyWage > 0) {
            val adjustedPrice = eraInflation.adjustPrice(itemPrice, currentYear, category)
            val extraCost = adjustedPrice - itemPrice
            if (extraCost > 0) {
                val extraHours = extraCost / hourlyWage
                GentleTextProvider.describeEraCostCommentary(
                    "inflation", workCost, extraHours, hourlyWage,
                    GentleTextProvider.describeInflationEffect(itemPrice, adjustedPrice, category, 2024, currentYear)
                )
            } else ""
        } else ""

        // 5. 小镇评述
        val commentary = GentleTextProvider.describeEraCostTownCommentary()

        return GentleTextProvider.describeFiveSegmentFull(
            bodyFeeling = feeling,
            sparkle = sparkle,
            personalCost = personalCost,
            eraCost = eraCost,
            townCommentary = commentary
        )
    }

    // ============================================
    // 物品闪光点追踪（v10 融合阶段一）
    // ============================================

    /** 已查看的闪光点物品集合（pathId -> Set<itemName>） */
    private val _viewedSparkleItems = MutableStateFlow<Map<String, Set<String>>>(emptyMap())
    val viewedSparkleItems: StateFlow<Map<String, Set<String>>> = _viewedSparkleItems.asStateFlow()

    /** 记录用户查看了某个物品的闪光点（纯内存） */
    fun recordSparkleView(itemName: String, pathId: String) {
        val current = _viewedSparkleItems.value.toMutableMap()
        val items = current[pathId]?.toMutableSet() ?: mutableSetOf()
        items.add(itemName)
        current[pathId] = items
        _viewedSparkleItems.value = current
    }

    /** 获取用户在指定路径下已查看过的物品名称列表 */
    suspend fun getViewedItems(pathId: String): Set<String> {
        return _viewedSparkleItems.value[pathId] ?: emptySet()
    }

    /** 获取当前用户的身份绑定配置（v1.2 阶段二） */
    suspend fun getCurrentBinding(): com.example.townapp.data.LifePathBinding? {
        if (_currentLifePathId.isEmpty()) return null
        return com.example.townapp.data.LifePathBindingData.getBinding(_currentLifePathId)
    }

    /** 添加事件日志（去重 + 日限频，最多保留20条）
     *  v2.0 限频规则：工作日每天最多5条，周末每天最多15条，重大事件不受限
     */
    private fun addEventLog(msg: String, isMajor: Boolean = false) {
        val current = _eventLog.value.toMutableList()
        // 不连续添加相同的日志
        if (current.firstOrNull() == msg) return

        // 日限频（非重大事件）
        if (!isMajor) {
            val dayOfWeek = (_gameDay.value % 7).let { if (it == 0) 7 else it }
            val isWeekend = dayOfWeek == 6 || dayOfWeek == 7
            val maxDailyEvents = if (isWeekend) 15 else 5

            if (_gameDay.value != _lastEventLogDay) {
                _lastEventLogDay = _gameDay.value
                _dailyEventCount = 0
            }
            if (_dailyEventCount >= maxDailyEvents) return
        }

        current.add(0, msg)
        if (current.size > 20) {
            current.removeAt(current.size - 1)
        }
        _eventLog.value = current
        if (!isMajor) _dailyEventCount++

        // 同时添加到本周事件列表
        addWeeklyEvent(msg)
    }

    /** 品类对应的图标emoji */
    val categoryEmojis: Map<String, String> = mapOf(
        "家居用品" to "🏠",
        "办公文具" to "✏️",
        "露营户外" to "🏕️",
        "婴童用品" to "🍼",
        "宠物用品" to "🐾",
        "五金工具" to "🔧",
        "医疗健康" to "🏥",
        "体育健身" to "🏃",
        "玩具桌游" to "🎲",
        "美术文创" to "🎨",
        "农林农具" to "🌾",
        "珠宝古董" to "💎",
        "无形服务" to "🧩",
        "音乐影音" to "🎵"
    )

    /** 获取品类的物品计数（O(1)直接查表） */
    fun getCategoryCount(category: String): Int = categoryCountMap[category] ?: 0

    /** 根据材料ID快速查找MaterialItem（O(1)） */
    fun getMaterialById(id: Int): MaterialItem? = materialIndex[id]

    /** 所有新增品类的成品列表（兼容旧接口 → 已统合到 allProducts） */
    fun getAllNewProducts(): List<ProductItem> = allProducts.filter { it.id >= 11000 }

    /** 获取所有新品的CompositionRelation（兼容旧接口 → 已统合到 allCompositions） */
    fun getAllNewCompositions(): List<CompositionRelation> = allCompositions

    /** 
     * 📊 全量数据资产统计（O(1)，by lazy 只算一次）
     * 展示数据库覆盖率和数据规模
     */
    val dataStats: DataStats by lazy {
        val allProds = allProducts
        val allMats = allMaterials
        val allComps = allCompositions
        val prodIdsWithComposition = allComps.map { it.productId }.distinct()

        DataStats(
            totalProducts = allProds.size,
            totalMaterials = allMats.size,
            totalCompositions = allComps.size,
            totalFoodItems = UnifiedFoodRepository.getTotalCount(),
            totalRecipes = completeRecipes.size,
            totalOutfits = completeOutfitSets.size,
            totalClothingItems = completeClothingItems.size,
            totalCategories = categoryProductIndex.size,
            categoryBreakdown = categoryCountMap,
            productsWithComposition = prodIdsWithComposition.size,
            productsWithMarketPrice = allProds.count { it.marketPrice > 0 },
            avgCompositionDepth = if (prodIdsWithComposition.isNotEmpty()) 
                allComps.size.toDouble() / prodIdsWithComposition.size 
            else 0.0,
            totalDataEntries = allProds.size + allMats.size + allComps.size + 
                UnifiedFoodRepository.getTotalCount() + completeRecipes.size + completeOutfitSets.size + completeClothingItems.size
        )
    }

    // ============================================
    // v1.4 工时-身心疲劳联动系统 公开方法
    // ============================================

    /**
     * 设置生活路径类型
     *
     * 三种路径：
     * - HUSTLE（奋斗）：多干多挣，身体负担重
     * - BALANCED（劳逸结合）：按部就班，收入健康取平衡
     * - REST（躺平）：少干少挣，活得轻松
     */
    fun setLifePath(type: LifePathType) {
        _lifePathType.value = type
        _mentalState.value = _mentalState.value.copy(lifePathType = type.name)

        // 根据生活路径调整月收入和工时
        val currentIncome = _spaceState.value.monthlyIncome
        val adjustedIncome = FatigueBusiness.getAdjustedMonthlyIncome(currentIncome, type)

        _spaceState.value = _spaceState.value.copy(monthlyIncome = adjustedIncome)

        val msg = GentleTextProvider.describeLifePathChoice(type)
        addEventLog(msg)

        // 纯内存模式：收入已通过_spaceState更新
    }

    /**
     * 执行一项恢复活动
     *
     * @param type 恢复类型（睡眠/休闲/美食/散步/社交）
     */
    fun doRecovery(type: FatigueBusiness.RecoveryType) {
        val body = _bodyState.value
        val result = FatigueBusiness.calculateRecovery(type, body)

        // 应用恢复效果
        _bodyState.value = body.copy(
            fatigueLevel = (body.fatigueLevel - result.fatigueRecovery).coerceIn(0, 100),
            energy = (body.energy + result.energyRecovery).coerceIn(0, 100),
            mood = (body.mood + result.moodBoost).coerceIn(0, 100)
        )

        // 扣除金钱
        if (result.costMoney > 0) {
            _spaceState.value = _spaceState.value.copy(
                currentSavings = (_spaceState.value.currentSavings - result.costMoney).coerceAtLeast(0.0)
            )
        }

        // 更新精神状态
        val currentMental = _mentalState.value
        _mentalState.value = currentMental.copy(
            anxiety = (currentMental.anxiety - 5).coerceIn(0, 100),
            happiness = (currentMental.happiness + result.moodBoost / 2).coerceIn(0, 100),
            socialFulfillment = if (type == FatigueBusiness.RecoveryType.SOCIAL) {
                (currentMental.socialFulfillment + 20).coerceIn(0, 100)
            } else currentMental.socialFulfillment
        )

        addEventLog(result.description)

        // 纯内存模式：恢复活动已通过addEventLog记录
    }

    /**
     * 获取当前季节描述
     */
    fun getSeasonalDescription(): String {
        return GentleTextProvider.describeSeasonalWork(_currentMonth.value)
    }

    /**
     * 评估长期劳损风险（供人生档案馆使用）
     */
    fun assessLongTermDamage(age: Int): List<String> {
        return FatigueBusiness.assessLongTermDamage(_bodyState.value, age)
    }

    // ============================================
    // v1.7 孩童行为场景 集成方法
    // ============================================

    /**
     * 触发孩童行为随机事件
     *
     * 根据当前身份的情绪倾向，在居家场景/傍晚时段随机触发。
     * 孩童行为二选一：善意捡拾 或 调皮捣乱。
     */
    fun triggerChildBehaviorEvent() {
        val pathId = _currentLifePathId
        if (pathId.isNullOrEmpty()) return

        // 临时孩童 NPC 对童年主线自我不可触发（童年视角用回望支线手动触发）
        if (IdentityChildReactions.isChildhoodSelf(pathId)) return

        val reaction = IdentityChildReactions.getReaction(pathId) ?: return

        // 根据 triggerWeight 和随机值决定是否触发
        val triggerChance = reaction.triggerWeight / 15.0  // 最高约67%概率
        if (Math.random() > triggerChance) return

        // 随机选取场景
        val scene = ChildBehaviorScenes.random()
        _childBehaviorScene.value = scene

        // 记录触发日志
        addEventLog(GentleTextProvider.describeChildSceneTrigger(scene))
    }

    /**
     * 处理玩家对孩童行为的回应
     *
     * @param response 玩家选择：SCOLD / PATIENT_TALK / SILENT_CLEANUP
     */
    fun handleChildBehaviorResponse(response: String) {
        val scene = _childBehaviorScene.value ?: return

        // 更新互动计数
        val newCount = _childInteractionCount.value + 1
        _childInteractionCount.value = newCount

        // 更新耐心值
        val currentPatience = _patienceLevel.value
        val patienceDelta = when (response) {
            "SCOLD" -> -5      // 发火会降低耐心（情绪紧绷）
            "PATIENT_TALK" -> +3  // 耐心沟通提升耐心
            "SILENT_CLEANUP" -> +1  // 沉默收拾微幅提升
            else -> 0
        }
        _patienceLevel.value = (currentPatience + patienceDelta).coerceIn(0, 100)

        // 发火会短暂提升焦虑
        if (response == "SCOLD") {
            val currentMental = _mentalState.value
            _mentalState.value = currentMental.copy(
                anxiety = (currentMental.anxiety + 5).coerceIn(0, 100)
            )
        }

        // 扣除损耗金额
        if (scene.actualLossCost > 0) {
            _spaceState.value = _spaceState.value.copy(
                currentSavings = (_spaceState.value.currentSavings - scene.actualLossCost).coerceAtLeast(0.0)
            )
        }

        // 生成反馈文案
        val hourlyWage = getHourlyWage()
        val feedbackMsg = GentleTextProvider.describeChildResponseResult(response, scene, hourlyWage)
        addEventLog(feedbackMsg)

        // 纯内存模式：孩童行为已通过addEventLog记录

        // 心态成长提示（每5次互动触发一次）
        if (newCount % 5 == 0) {
            val growthMsg = GentleTextProvider.describeChildInteractionGrowth(_patienceLevel.value, newCount)
            if (growthMsg.isNotEmpty()) {
                addEventLog(growthMsg)
            }
        }

        // 清除当前场景
        _childBehaviorScene.value = null
    }

    /**
     * 童年回望支线：以孩童视角触发场景
     *
     * @param behavior 孩童行为：HELP_PICK_UP 或 PLAYFUL_MISCHIEF
     */
    fun triggerChildhoodPerspective(behavior: String) {
        if (_currentLifePathId != "childhood_self") return

        val scene = when (behavior) {
            "HELP_PICK_UP" -> ChildBehaviorScenes.findById("litchi_help")
            "PLAYFUL_MISCHIEF" -> ChildBehaviorScenes.findById("pingpong_mischief")
            else -> null
        } ?: return

        _childBehaviorScene.value = scene
        val msg = GentleTextProvider.describeChildhoodPerspective(behavior)
        addEventLog(msg)
    }

    /**
     * 清除当前孩童场景（玩家关闭弹窗）
     */
    fun dismissChildBehaviorScene() {
        _childBehaviorScene.value = null
    }

    /**
     * 触发跨职业对话随机事件（V2.0 概率调优）
     *
     * 从 CrossProfessionDialogues 预置场景中随机选取，触发职业圈层认知碰撞。
     * 只在非独处时段触发。
     */
    fun triggerCrossProfessionDialogue() {
        val pathId = _currentLifePathId ?: return
        // 筛选以当前身份为倾听者的对话场景
        val relevantDialogues = CrossProfessionDialogues.dialogues.filter {
            it.listenerPathId == pathId
        }
        if (relevantDialogues.isEmpty()) return

        val dialogue = relevantDialogues.random()
        _lastCrossProfessionDialogue.value = dialogue
        addEventLog(dialogue.speakerOpeningLine)

        // 纯内存模式：跨职业对话已通过addEventLog记录
    }

    // ============================================
    // v2.14 时间系统：辅助方法
    // ============================================

    /** 根据月份获取参考温度（°C） */
    private fun getTemperatureForMonth(month: Int): Double = when (month) {
        1 -> 2.0; 2 -> 5.0; 3 -> 12.0; 4 -> 18.0; 5 -> 22.0; 6 -> 28.0
        7 -> 32.0; 8 -> 30.0; 9 -> 24.0; 10 -> 18.0; 11 -> 10.0; else -> 3.0
    }

    /** 应用每日统计数据到身体/心理状态 */
    private fun applyDailyStats(stats: DailyStats) {
        val body = _bodyState.value
        _bodyState.value = body.copy(
            fatigueLevel = (body.fatigueLevel + (stats.fatigueDelta * 100).toInt()).coerceIn(0, 100),
            satiety = (body.satiety - 2).coerceIn(0, 100)
        )
        val mental = _mentalState.value
        _mentalState.value = mental.copy(
            anxiety = (mental.anxiety + stats.anxietyDelta).coerceIn(0, 100)
        )
    }

    /** 周度结算 */
    // ============================================
    // 三档周事件系统（平淡70% / 中等20% / 重大10%）
    // ============================================

    /** 生成本周平淡日常摘要 */
    private fun generateMundaneSummary(): String {
        val age = _playerAge.value
        val mode = _shoppingMode.value
        val stage = com.example.townapp.data.LifeStage.fromAge(age)
        val expense = _weeklyExpense.value
        val love = _loveState.value
        val rng = kotlin.random.Random

        val routine = when (mode) {
            ShoppingMode.SURVIVAL -> when (stage) {
                com.example.townapp.data.LifeStage.YOUTH ->
                    "本周照常通勤上班，三餐以简餐为主，几乎没有额外开销。"
                com.example.townapp.data.LifeStage.MIDDLE_AGE ->
                    "这周除了上班就是回家，菜市场的打折蔬菜买得比平时多一些。"
                else ->
                    "日子过得很省，每笔钱都算计着花，能不出门就不出门。"
            }
            ShoppingMode.BALANCED -> when (stage) {
                com.example.townapp.data.LifeStage.YOUTH ->
                    "工作日正常上班，周末简单采购，生活平稳如常。"
                com.example.townapp.data.LifeStage.MIDDLE_AGE ->
                    "这周家务、工作、接送孩子，日子像上了发条的钟，准点运转。"
                else ->
                    "去公园走了两圈，和老邻居聊了聊天气，一天又过去了。"
            }
            ShoppingMode.PREMIUM -> when (stage) {
                com.example.townapp.data.LifeStage.YOUTH ->
                    "周末小聚，添置了几件新东西，日子过得还算滋润。"
                com.example.townapp.data.LifeStage.MIDDLE_AGE ->
                    "这周应酬多了两场，消费比预期高了一些，但还在掌控中。"
                else ->
                    "去了一趟温泉疗养，和老友吃了顿好的，享受了一下生活。"
            }
        }

        val detail = if (expense.shoppingList.isNotEmpty()) {
            val items = expense.shoppingList.take(3).joinToString("、") { it.name }
            "采买了 $items 等。"
        } else ""

        val relation = when (love.status) {
            com.example.townapp.data.LoveStatus.MARRIED ->
                if (rng.nextBoolean()) "和配偶的相处平淡而安稳。" else ""
            com.example.townapp.data.LoveStatus.SINGLE ->
                if (age < 45 && _weeklySocialScore.value <= 0) "这周没有社交，也没有认识新的人。" else ""
            else -> ""
        }

        return listOf(routine, detail, relation).filter { it.isNotEmpty() }.joinToString("")
    }

    /** 生成本周中等随机事件（带选项） */
    private fun generateMediumEvent(): Pair<String, List<WeekEventChoice>>? {
        val age = _playerAge.value
        val stage = com.example.townapp.data.LifeStage.fromAge(age)
        val rng = kotlin.random.Random

        return when (stage) {
            com.example.townapp.data.LifeStage.YOUTH -> when (rng.nextInt(4)) {
                0 -> "本周临时加班增多，薪资小幅上涨，但劳累值上升。" to listOf(
                    WeekEventChoice("accept", "接受加班", "多赚了一笔加班费，但身体透支，下周生病概率上升。", savingsDelta = 300.0, healthDelta = -5, moodDelta = -3),
                    WeekEventChoice("refuse", "尽量推掉", "收入没增加，但至少周末能休息。", moodDelta = 5)
                )
                1 -> "朋友邀约周末短途聚餐。" to listOf(
                    WeekEventChoice("go", "参加聚餐", "花了一笔钱，但心情好了很多，社交圈也活络了。", savingsDelta = -200.0, moodDelta = 10, socialScoreDelta = 15),
                    WeekEventChoice("skip", "婉拒", "省下了钱，但周末在家有点闷。", savingsDelta = 0.0, moodDelta = -2)
                )
                2 -> "乐器琴弦老化，需要更换配件。" to listOf(
                    WeekEventChoice("buy", "购买配件", "一笔小额耗材开销，但能继续练习了。", savingsDelta = -80.0, moodDelta = 2),
                    WeekEventChoice("delay", "暂缓更换", "琴声有点沙哑，但还能凑合。", moodDelta = -1)
                )
                3 -> "这周菜价小幅上涨，伙食开销比预期多了一些。" to listOf(
                    WeekEventChoice("ok", "照常买菜", "多花了几十块，但吃得还不错。", savingsDelta = -50.0),
                    WeekEventChoice("save", "改买便宜菜", "省了一点，但口味单调了些。", moodDelta = -2)
                )
                else -> null
            }
            com.example.townapp.data.LifeStage.MIDDLE_AGE -> when (rng.nextInt(4)) {
                0 -> "年度体检发现血压偏高，医生建议定期复查。" to listOf(
                    WeekEventChoice("treat", "开始调理", "每周医疗预算小幅上调，开始中药调理。", savingsDelta = -150.0, healthDelta = 5),
                    WeekEventChoice("ignore", "暂时不管", "省下了调理费，但健康隐患埋下了。", healthDelta = -3)
                )
                1 -> "老家亲友突然来访，人情消费增加。" to listOf(
                    WeekEventChoice("host", "好好招待", "花了一笔钱，但亲情维系住了。", savingsDelta = -400.0, moodDelta = 5, socialScoreDelta = 10),
                    WeekEventChoice("simple", "简单接待", "省了一些，但心里总觉得过意不去。", savingsDelta = -150.0, moodDelta = -3)
                )
                2 -> "公司传出裁员风声，心里不安。" to listOf(
                    WeekEventChoice("save", "减少开销储蓄", "开始缩减不必要开支，存款增加安全感。", savingsDelta = 200.0, moodDelta = -5),
                    WeekEventChoice("maintain", "维持现状", "照常生活，但焦虑感挥之不去。", moodDelta = -8)
                )
                3 -> "孩子学校要交一笔额外的活动费。" to listOf(
                    WeekEventChoice("pay", "正常缴纳", "教育支出又涨了一点。", savingsDelta = -300.0),
                    WeekEventChoice("skip", "不参加", "省了钱，但孩子有点失落。", moodDelta = -4)
                )
                else -> null
            }
            com.example.townapp.data.LifeStage.SENIOR -> when (rng.nextInt(3)) {
                0 -> "老友上门闲谈，一起回忆往事。" to listOf(
                    WeekEventChoice("chat", "畅聊一下午", "心情愉快，孤独感减轻。", moodDelta = 10, socialScoreDelta = 10),
                    WeekEventChoice("rest", "聊一会儿就休息", "保留了精力，但意犹未尽。", moodDelta = 3)
                )
                1 -> "换季降温，身体有点吃不消。" to listOf(
                    WeekEventChoice("warm", "加强保暖", "买了保暖衣物，身体舒适了一些。", savingsDelta = -100.0, healthDelta = 3),
                    WeekEventChoice("tough", "硬扛过去", "没花钱，但感冒风险上升。", healthDelta = -5)
                )
                2 -> "社区通知有免费健康讲座。" to listOf(
                    WeekEventChoice("attend", "去听听", "学了一些保健知识，认识了新朋友。", healthDelta = 2, socialScoreDelta = 8),
                    WeekEventChoice("skip", "懒得去", "在家休息，但也错过了信息。")
                )
                else -> null
            }
            else -> null
        }
    }

    /** 生成本周重大人生事件（带选项） */
    private fun generateMajorEvent(): Pair<String, List<WeekEventChoice>>? {
        val age = _playerAge.value
        val stage = com.example.townapp.data.LifeStage.fromAge(age)
        val love = _loveState.value
        val rng = kotlin.random.Random

        return when (stage) {
            com.example.townapp.data.LifeStage.YOUTH -> when (rng.nextInt(3)) {
                0 -> "一个升职机会摆在面前，但意味着更多的加班和竞争。" to listOf(
                    WeekEventChoice("promote", "争取升职", "薪资上调，消费档位整体提升，但压力倍增。", savingsDelta = 1000.0, moodDelta = -5, careerImpact = "薪资上调20%"),
                    WeekEventChoice("stay", "维持现状", "没多赚钱，但生活平衡，心情安稳。", moodDelta = 5)
                )
                1 -> if (love.status == com.example.townapp.data.LoveStatus.SINGLE)
                    "家里介绍了一个相亲对象，条件看起来还不错。" to listOf(
                        WeekEventChoice("meet", "去见见", "相亲花费了一些，但也多了一次机会。", savingsDelta = -300.0, socialScoreDelta = 20),
                        WeekEventChoice("refuse", "不见", "省下了时间和钱，但家里有些念叨。", moodDelta = -2)
                    ) else null
                2 -> "公司突然裁员，你就在名单上。" to listOf(
                    WeekEventChoice("cut", "紧急缩减开支", "切换到刚需档位，靠存款维持，开始找工作。", savingsDelta = -500.0, moodDelta = -15, careerImpact = "失业，月薪归零"),
                    WeekEventChoice("buffer", "用存款缓冲", "暂时维持生活水平，但存款在快速减少。", savingsDelta = -1500.0, moodDelta = -8)
                )
                else -> null
            }
            com.example.townapp.data.LifeStage.MIDDLE_AGE -> when (rng.nextInt(3)) {
                0 -> "一位至亲长辈病重住院，需要大笔医疗费。" to listOf(
                    WeekEventChoice("pay", "全力救治", "存款大幅消耗，但无愧于心。", savingsDelta = -8000.0, moodDelta = -10),
                    WeekEventChoice("balance", "量力而行", "在能力和良心之间找了个平衡点。", savingsDelta = -4000.0, moodDelta = -5)
                )
                1 -> if (love.status == com.example.townapp.data.LoveStatus.MARRIED && !love.hasChildren)
                    "配偶提起备孕的事，你们认真讨论了一次。" to listOf(
                        WeekEventChoice("try", "开始备孕", "家庭预算结构将长期改变，但也充满期待。", savingsDelta = -2000.0, moodDelta = 10),
                        WeekEventChoice("wait", "再等等", "保留了现在的节奏，但双方都有点不确定。", moodDelta = -3)
                    ) else null
                2 -> "看中了一套房子，首付刚好够，但会掏空存款。" to listOf(
                    WeekEventChoice("buy", "咬牙买下", "有了房，但存款见底，月供压力随之而来。", savingsDelta = -50000.0, moodDelta = 5),
                    WeekEventChoice("wait", "再观望", "存款还在，但房价可能继续涨。", moodDelta = -3)
                )
                else -> null
            }
            com.example.townapp.data.LifeStage.SENIOR -> when (rng.nextInt(2)) {
                0 -> "一次意外摔倒，髋骨骨折，需要手术和长期康复。" to listOf(
                    WeekEventChoice("surgery", "手术治疗", "一笔大额医疗支出，但有望恢复行动能力。", savingsDelta = -15000.0, healthDelta = -15, moodDelta = -10),
                    WeekEventChoice("conservative", "保守治疗", "花费少很多，但可能长期卧床。", savingsDelta = -3000.0, healthDelta = -25, moodDelta = -15)
                )
                1 -> "养老金政策调整，每月到账金额减少。" to listOf(
                    WeekEventChoice("adapt", "缩减开支适应", "生活更省了，但还能维持。", savingsDelta = 0.0, moodDelta = -5),
                    WeekEventChoice("dip", "动用存款填补", "生活水平没变，但存款在减少。", savingsDelta = -500.0, moodDelta = -3)
                )
                else -> null
            }
            else -> null
        }
    }

    /** 判定本周应触发的事件层级 */
    private fun determineWeeklyEventTier(): WeeklyEventTier {
        // 重大事件冷却递减
        if (_majorEventCooldown > 0) _majorEventCooldown--

        // 连续3周平淡后，强制触发中等事件
        if (_consecutiveMundaneWeeks >= 3) {
            _consecutiveMundaneWeeks = 0
            return WeeklyEventTier.MEDIUM
        }

        val roll = kotlin.random.Random.nextInt(100)
        return when {
            // 重大事件：10% 概率，且冷却完毕
            roll < 10 && _majorEventCooldown <= 0 -> {
                _majorEventCooldown = 8  // 8周冷却
                WeeklyEventTier.MAJOR
            }
            // 中等事件：20% 概率
            roll < 30 -> WeeklyEventTier.MEDIUM
            // 其余：平淡日常
            else -> WeeklyEventTier.MUNDANE
        }
    }

    /** 应用玩家对周事件的选择 */
    fun applyWeekEventChoice(choice: WeekEventChoice) {
        val space = _spaceState.value
        val body = _bodyState.value

        // 存款变动
        if (choice.savingsDelta != 0.0) {
            _spaceState.value = space.copy(
                currentSavings = (space.currentSavings + choice.savingsDelta).coerceAtLeast(0.0)
            )
        }
        // 健康变动
        if (choice.healthDelta != 0) {
            _bodyState.value = body.copy(
                healthScore = (body.healthScore + choice.healthDelta).coerceIn(0, 100)
            )
        }
        // 心情变动
        if (choice.moodDelta != 0) {
            _bodyState.value = _bodyState.value.copy(
                mood = (_bodyState.value.mood + choice.moodDelta).coerceIn(0, 100)
            )
        }
        // 社交活跃度变动
        if (choice.socialScoreDelta != 0) {
            _weeklySocialScore.value = (_weeklySocialScore.value + choice.socialScoreDelta).coerceIn(0, 100)
        }
        // 职业影响
        if (choice.careerImpact.isNotEmpty()) {
            addEventLog("职业变动：${choice.careerImpact}")
        }

        addEventLog(choice.description)
        _pendingWeekEventChoices.value = null
    }

    // ============================================
    // 全局每周主调度函数（阶段1核心：8步执行顺序）
    // ============================================

    /** 应用本周消费扣减（点到面预算制，扣除超出日基础开销的部分） */
    private fun applyWeeklySpending() {
        val expense = _weeklyExpense.value
        val space = _spaceState.value
        val tier = _autoLifeTier.value

        // 计算本周已通过 executeTodayPlan 扣除的日基础开销近似值
        val dailyBaseDeduction = (tier.dailyFoodCost + tier.dailyLivingCost) * 5  // 5个工作日
        val weeklyExtra = expense.totalCost - dailyBaseDeduction

        if (weeklyExtra > 0) {
            _spaceState.value = space.copy(
                currentSavings = (space.currentSavings - weeklyExtra).coerceAtLeast(0.0)
            )
            addEventLog("本周额外开销（穿戴/医疗/爱好等）扣除 ¥${weeklyExtra.toInt()}，存款余额 ¥${_spaceState.value.currentSavings.toInt()}")
        }
    }

    /** 健康生理期模块自动运行（玩家未手动处理时） */
    private fun autoRunPeriodModule() {
        val genderState = _genderState.value
        val cycle = genderState.menstrualCycle ?: return
        if (!cycle.isMenstruating) return

        val mode = _shoppingMode.value
        // 经期自动按档位买药：均衡档及以上自动用止痛药，刚需档用红糖姜茶
        when (mode) {
            ShoppingMode.SURVIVAL -> {
                drinkBrownSugarGinger()
                addEventLog("系统自动：经期不适，泡了一杯红糖姜茶（节俭档默认）。")
            }
            else -> {
                usePeriodPainkiller()
                addEventLog("系统自动：经期不适，自动服用止痛药（${mode.label}档默认）。")
            }
        }
    }

    /** 医疗系统自动运行（玩家未手动治疗时） */
    private fun autoRunMedicalModule() {
        val medical = _medicalState.value
        if (medical.activeDiseases.isEmpty()) return

        val mode = _shoppingMode.value
        val income = _spaceState.value.monthlyIncome

        medical.activeDiseases.forEach { disease ->
            if (disease.activeTreatment != null) return@forEach  // 已有治疗方案，跳过

            // 按档位自动匹配默认治疗方案
            val (drug, tier) = autoMatchDrug(disease.type, income)
            val route = when (mode) {
                ShoppingMode.SURVIVAL -> TreatmentRoute.NATURAL_RECOVERY
                ShoppingMode.BALANCED -> if (disease.isChronic) TreatmentRoute.TRADITIONAL_REMEDY else TreatmentRoute.MODERN_MEDICINE
                ShoppingMode.PREMIUM -> TreatmentRoute.MODERN_MEDICINE
            }
            treatDisease(disease.type, route, tier)
            addEventLog("系统自动：检测到${disease.type.label}，按${mode.label}档自动选择${route.label}方案（${tier.label}）。")
        }
    }

    /** 中年支线自动运行（玩家未手动抉择时） */
    private fun autoRunMidlifeModule() {
        val age = _playerAge.value
        val reformer = _reformerState.value
        if (reformer.branchClosed || reformer.isReformer) return

        // 32-35岁区间，超时未手动选择，系统按消费档位自动分配
        if (age in 32..35 && !reformer.allPrerequisitesMet) {
            val isSurvival = _shoppingMode.value == ShoppingMode.SURVIVAL
            if (isSurvival) {
                // 拮据生活状态下，系统偏向稳妥自保
                chooseSafePath()
                addEventLog("系统自动：中年抉择超时，长期拮据生活让你更倾向于稳妥自保。")
            } else {
                // 有一定经济基础时，有一定概率尝试革新
                if (kotlin.random.Random.nextBoolean()) {
                    chooseReformerPath()
                    addEventLog("系统自动：中年抉择超时，系统判定你有一定基础，默认尝试革新路线。")
                } else {
                    chooseSafePath()
                    addEventLog("系统自动：中年抉择超时，系统判定时机未成熟，选择稳妥自保。")
                }
            }
        }
    }

    /**
     * 全局每周主调度函数（8步执行顺序）
     *
     * 1. 结算本周收支，生成采购清单，更新存款
     * 2. 年龄增长（已在 tickEncounterDaily 处理，此处确保联动）
     * 3. 健康模块自动运行（生理期、疾病）
     * 4. 性别系统更新
     * 5. 人生阶段判定（中年抉择）
     * 6. 随机事件生成（平淡/中等/重大）
     * 7. 社交值更新、婚恋相亲推送判定
     * 8. 本周数据存档
     */
    private fun weeklyMasterUpdate(state: TimeState) {
        // === Step 1: 结算本周收支 ===
        calculateWeeklyExpense()
        applyWeeklySpending()

        // === Step 2: 年龄增长 ===
        // 已由 tickEncounterDaily 每360天处理，此处联动人生阶段转换提示
        val age = _playerAge.value
        if (age == 30 || age == 60) {
            addEventLog("人生阶段转换：你进入了${com.example.townapp.data.LifeStage.fromAge(age).label}，生活重心随之变化。")
        }

        // === Step 3 & 4: 健康/性别模块自动运行（玩家未手动时） ===
        if (!_playerManuallyTreatedPeriodThisWeek) {
            autoRunPeriodModule()
        }
        if (!_playerManuallyTreatedDiseaseThisWeek) {
            autoRunMedicalModule()
        }

        // === Step 5: 中年支线自动判定 ===
        if (!_playerManuallyChoseMidlifePathThisWeek) {
            autoRunMidlifeModule()
        }

        // === Step 6: 三档随机事件（已在 tickWeekly 的事件层级判定中处理） ===
        // 事件生成由 determineWeeklyEventTier / generateMundaneSummary / generateMediumEvent / generateMajorEvent 负责

        // === Step 7: 婚恋相亲判定（玩家未手动时） ===
        if (!_playerManuallyDatedThisWeek) {
            tickLoveDaily()
        }

        // === Step 8: 存档 ===
        gameSaveRepository?.save(createSnapshot())
    }

    /** 创建当前状态快照（供存档使用） */
    private fun createSnapshot(): GameSaveRepository.GameSnapshot {
        return GameSaveRepository.GameSnapshot(
            gameDay = _gameDay.value,
            currentWeek = _currentWeek.value,
            playerAge = _playerAge.value,
            timeState = _timeState.value,
            spaceState = _spaceState.value,
            bodyState = _bodyState.value,
            mentalState = _mentalState.value,
            shoppingMode = _shoppingMode.value.name,
            autoLifeTier = _autoLifeTier.value.name,
            medicalState = _medicalState.value,
            genderState = _genderState.value,
            loveState = _loveState.value,
            reformerState = _reformerState.value,
            eventLog = _eventLog.value.takeLast(50),
            weeklySocialScore = _weeklySocialScore.value
        )
    }

    /** 从快照恢复状态（供读档使用） */
    fun restoreSnapshot(snapshot: GameSaveRepository.GameSnapshot) {
        _gameDay.value = snapshot.gameDay
        _currentWeek.value = snapshot.currentWeek
        _playerAge.value = snapshot.playerAge
        _timeState.value = snapshot.timeState
        _spaceState.value = snapshot.spaceState
        _bodyState.value = snapshot.bodyState
        _mentalState.value = snapshot.mentalState
        _shoppingMode.value = ShoppingMode.valueOf(snapshot.shoppingMode)
        _autoLifeTier.value = AutoLifeTier.valueOf(snapshot.autoLifeTier)
        _medicalState.value = snapshot.medicalState
        _genderState.value = snapshot.genderState
        _loveState.value = snapshot.loveState
        _reformerState.value = snapshot.reformerState
        _eventLog.value = snapshot.eventLog
        _weeklySocialScore.value = snapshot.weeklySocialScore
        addEventLog("已恢复上次存档——生活继续。")
    }

    /** 尝试读取本地存档，成功则恢复状态 */
    fun tryLoadGame(): Boolean {
        val snapshot = gameSaveRepository?.load() ?: return false
        restoreSnapshot(snapshot)
        return true
    }

    private fun tickWeekly(state: TimeState) {
        if (_weeklyDailyStats.isEmpty()) return

        // ====== 全局每周主调度（8步执行顺序）======
        // 基于上周玩家手动操作标志，运行本周自动兜底逻辑
        weeklyMasterUpdate(state)

        val weeklyStats = WeeklySettlement.aggregate(
            dailyStatsList = _weeklyDailyStats.toList(),
            weekNumber = state.totalWeeks,
            month = state.month,
            season = state.season,
            avgTemperature = getTemperatureForMonth(state.month),
            dominantWeather = _weatherState.value,
            isYouth = state.lifeStage == LifeStage.YOUTH
        )

        _monthlyWeeklyStats.add(weeklyStats)
        _weeklyDailyStats.clear()

        // ====== 三档周事件系统判定 ======
        val eventTier = determineWeeklyEventTier()
        _weeklyEventTier.value = eventTier

        when (eventTier) {
            WeeklyEventTier.MUNDANE -> {
                _consecutiveMundaneWeeks++
                val summary = generateMundaneSummary()
                _weeklyEventSummary.value = summary
                addEventLog("本周平淡：$summary")
            }
            WeeklyEventTier.MEDIUM -> {
                _consecutiveMundaneWeeks = 0
                val event = generateMediumEvent()
                if (event != null) {
                    _weeklyEventSummary.value = event.first
                    _pendingWeekEventChoices.value = event.second
                    addEventLog("【生活琐事】${event.first}", isMajor = true)
                } else {
                    //  fallback 到平淡
                    _weeklyEventSummary.value = generateMundaneSummary()
                    _consecutiveMundaneWeeks = 1
                }
            }
            WeeklyEventTier.MAJOR -> {
                _consecutiveMundaneWeeks = 0
                val event = generateMajorEvent()
                if (event != null) {
                    _weeklyEventSummary.value = event.first
                    _pendingWeekEventChoices.value = event.second
                    addEventLog("【人生节点】${event.first}", isMajor = true)
                } else {
                    // fallback 到中等
                    val medium = generateMediumEvent()
                    if (medium != null) {
                        _weeklyEventSummary.value = medium.first
                        _pendingWeekEventChoices.value = medium.second
                        addEventLog("【生活琐事】${medium.first}", isMajor = true)
                    } else {
                        _weeklyEventSummary.value = generateMundaneSummary()
                        _consecutiveMundaneWeeks = 1
                    }
                }
            }
        }

        // 周度总结事件日志（标记为重大事件）
        if (weeklyStats.summary.isNotEmpty()) {
            addEventLog("周末总结：${weeklyStats.summary}", isMajor = true)
        }
        
        // 更新周统计数据用于周简报
        _weeklyStats.value = weeklyStats
        
        // 更新当前周数
        _currentWeek.value = state.totalWeeks
        
        // 触发周简报弹窗（延迟一秒显示，让其他事件先处理）
        viewModelScope.launch {
            delay(1000)
            triggerWeeklyBrief()
        }
        
        // 清空本周事件列表，准备下一周
        clearWeeklyEvents()

        // 重置每周社交活跃度（周一重新开始统计）
        resetWeeklySocialScore()

        // 重置玩家手动操作标志（新一周开始，等待玩家周末操作）
        resetWeeklyManualFlags()
    }

    /** 月度结算 */
    private fun tickMonthly(result: TickResult) {
        val state = result.newState

        // 先调用旧版年度推进（兼容时代成本系统）
        if (result.isNewYear) {
            tickYear()
        }

        // 月度全局结算
        val monthlyStats = MonthlySettlement.settle(
            monthlyIncome = _spaceState.value.monthlyIncome,
            rent = _spaceState.value.monthlyRent,
            currentSavings = _spaceState.value.currentSavings,
            weeklyStatsList = _monthlyWeeklyStats.toList(),
            month = state.month,
            age = state.age,
            lifeStage = state.lifeStage,
            isSeasonChange = result.isSeasonChange,
            consecutiveBadMonths = _consecutiveBadMonths,
            consecutiveGoodMonths = _consecutiveGoodMonths,
            classBackground = 1  // 默认中产，后续从童年创伤/家境系统获取
        )

        _lastMonthlyStats.value = monthlyStats
        _monthlyWeeklyStats.clear()

        // 更新纪念馆：月度收支
        _memorialMoneyDelta.value += (monthlyStats.netBalance)

        // 更新连续负面/正面月份计数
        val hasSevereNegative = monthlyStats.majorEvents.any { it.category == EventCategory.SEVERE_NEGATIVE }
        val hasPositive = monthlyStats.majorEvents.any { it.category == EventCategory.POSITIVE }

        if (hasSevereNegative) {
            _consecutiveBadMonths++
            _consecutiveGoodMonths = 0
        } else if (hasPositive) {
            _consecutiveGoodMonths++
            _consecutiveBadMonths = 0
        } else {
            _consecutiveBadMonths = 0
            _consecutiveGoodMonths = 0
        }

        // 月度结算日志（标记为重大事件，不受日限频影响）
        addEventLog(monthlyStats.summary, isMajor = true)

        // 重大事件记录
        monthlyStats.majorEvents.forEach { event ->
            addEventLog("${event.title}：${event.description}", isMajor = true)
        }

        // 年龄里程碑
        result.milestones.forEach { milestone ->
            addEventLog("${state.age}岁：${milestone.label}——${milestone.description}", isMajor = true)
        }

        // 人生阶段切换
        if (result.isStageTransition) {
            addEventLog("进入${state.lifeStage.label}阶段。${state.lifeStage.description}", isMajor = true)
        }
    }

    // ============================================
    // v2.0 时代成本系统 集成方法
    // ============================================

    /**
     * 年度推进（在 tick() 中每年调用一次）
     *
     * 每年1月1日触发：
     * - 年份递增
     * - 房租随通胀调整
     * - 行业薪资系数更新
     * - 记录时代事件日志
     */
    private fun tickYear() {
        val newYear = _currentYear.value + 1
        _currentYear.value = newYear

        // 房租通胀调整
        val adjustedRent = eraInflation.adjustPrice(
            _spaceState.value.monthlyRent,
            newYear,
            "rent"
        )
        _spaceState.value = _spaceState.value.copy(
            monthlyRent = adjustedRent
        )

        // 行业状态检查
        val pathId = _currentLifePathId
        if (pathId != null) {
            val industry = IndustryLifecycles.findByPathId(pathId)
            if (industry != null) {
                val stage = industry.getStageDescription(newYear)
                val multiplier = industry.getSalaryMultiplier(newYear)

                // 行业衰退警告
                if (multiplier < 0.8 && newYear % 2 == 0) {
                    addEventLog("${newYear}年。${industry.displayName}${stage}。这不是你个人的问题——是时代在变。")
                }

                // 行业薪资调整
                if (multiplier > 0) {
                    val adjustedIncome = _spaceState.value.monthlyIncome * multiplier
                    _spaceState.value = _spaceState.value.copy(
                        monthlyIncome = adjustedIncome
                    )
                }
            }
        }

        // 就业环境日志（每5年触发一次）
        if (newYear % 5 == 0) {
            val difficulty = EraEmploymentConfigs.getDifficultyMultiplier(newYear)
            val description = EraEmploymentConfigs.getDescription(newYear)
            if (difficulty > 1.0) {
                addEventLog("${newYear}年。${description}。时代就业环境的变化，会影响每一个人的机会。")
            }
        }

        // 信念系统年度更新
        tickBeliefYearly()

        // 阶层行为年度更新
        tickClassBehaviorYearly()
    }

    /**
     * 获取当前年份通胀调整后的价格
     */
    fun getAdjustedPrice(basePrice: Double, category: String): Double {
        return eraInflation.adjustPrice(basePrice, _currentYear.value, category)
    }

    /**
     * 获取当前行业状态信息
     */
    fun getIndustryStatus(): String {
        val pathId = _currentLifePathId ?: return ""
        return GentleTextProvider.describeIndustryStage(pathId, _currentYear.value)
    }

    /**
     * 获取行业薪资倍数
     */
    fun getIndustrySalaryMultiplier(): Double {
        val pathId = _currentLifePathId ?: return 1.0
        return IndustryLifecycles.findByPathId(pathId)?.getSalaryMultiplier(_currentYear.value) ?: 1.0
    }

    /**
     * 获取当前就业环境难度
     */
    fun getEmploymentDifficulty(): Double {
        return EraEmploymentConfigs.getDifficultyMultiplier(_currentYear.value)
    }

    /**
     * 计算代际成本差距
     *
     * @param parentYear 父辈年份
     * @param parentHourlyWage 父辈时薪
     */
    fun getGenerationalCostGap(
        parentYear: Int,
        parentHourlyWage: Double,
        itemBasePrice: Double
    ): GenerationalCostGap {
        return GenerationalCostScenarios.calculateHousingGap(
            parentYear = parentYear,
            childYear = _currentYear.value,
            housePriceBase = itemBasePrice,
            parentHourlyWage = parentHourlyWage,
            childHourlyWage = getHourlyWage(),
            inflation = eraInflation
        )
    }

    /**
     * 获取时代成本解析（第五段文案）
     */
    fun getEraCostText(scenario: String, personalWorkHours: Double, eraCostHours: Double, context: String = ""): String {
        return GentleTextProvider.describeEraCostCommentary(
            scenario, personalWorkHours, eraCostHours, getHourlyWage(), context
        )
    }

    // ============================================
    // v2.1 科研与技术流通体系 集成方法
    // ============================================

    /** 科研生涯状态 */
    private val _researchState = MutableStateFlow(ResearchCareerState())
    val researchState: StateFlow<ResearchCareerState> = _researchState.asStateFlow()

    /**
     * 初始化学术生涯（开局时调用）
     *
     * 96% 概率中产，4% 概率寒门（百里挑四）。
     * 寒门中基础科研仅占1%，绝大多数进入应用型研发。
     */
    fun initResearchCareer() {
        val origin = if (Math.random() < 0.96) {
            ResearcherOrigin.AFFLUENT_MIDDLE
        } else {
            ResearcherOrigin.COUNTY_UNDERPRIVILEGED
        }
        val config = ResearcherOriginConfigs.configs[origin]!!

        // 寒门基础科研（≤1%）——极低概率额外筛选
        val branch: ResearchCareerBranch = if (origin == ResearcherOrigin.COUNTY_UNDERPRIVILEGED) {
            if (Math.random() < 0.01) ResearchCareerBranch.BASIC_RESEARCH
            else ResearchCareerBranch.APPLIED_RD
        } else {
            ResearchCareerBranch.BASIC_RESEARCH
        }

        _researchState.value = ResearchCareerState(
            origin = origin,
            branch = branch,
            faction = AcademicFaction.INDEPENDENT,
            financialStress = config.monthlyFinancialStress,
            labMonthlyIncome = if (branch == ResearchCareerBranch.BASIC_RESEARCH) 5000.0 else 6000.0
        )
        addEventLog(GentleTextProvider.describeResearchOrigin(origin))
        if (branch == ResearchCareerBranch.APPLIED_RD && origin == ResearcherOrigin.COUNTY_UNDERPRIVILEGED) {
            addEventLog("因为经济压力，你选择了应用型技术研发方向。周期更短、见效更快——你知道自己没有试错的本钱。")
        }

        // 检测革新者隐藏潜质（千分之三概率，不暴露）
        initReformerPotential()
    }

    /**
     * 切换科研职业分支
     */
    fun switchResearchBranch(newBranch: ResearchCareerBranch) {
        val oldBranch = _researchState.value.branch
        if (oldBranch == newBranch) return

        // 寒门不允许选基础科研（周期太长老没钱撑不住）
        if (newBranch == ResearchCareerBranch.BASIC_RESEARCH && !newBranch.accessibleToPoor
            && _researchState.value.origin == ResearcherOrigin.COUNTY_UNDERPRIVILEGED
        ) {
            addEventLog("基础理论科研周期长达8-12年，以你目前的经济状况，家庭现金流很难长期支撑。可以考虑应用型研发或技术经纪人方向。")
            return
        }

        _researchState.value = _researchState.value.copy(branch = newBranch)
        addEventLog(GentleTextProvider.describeResearchBranchSwitch(oldBranch, newBranch))
    }

    /**
     * 推进研究进度（tick 时调用）
     *
     * 每小时增加研究进度。当进度达到100时，技术成果落地，触发全局影响。
     *
     * @param hoursElapsed 本次 tick 经过的小时数
     */
    private fun tickResearchProgress(hoursElapsed: Int) {
        val state = _researchState.value
        if (state.branch == ResearchCareerBranch.TECH_SPECULATOR || state.researchProgress >= 100) return

        val totalCycleHours = state.branch.cycleYears.last * 12 * state.branch.monthlyWorkHours
        val progressPerHour = 100.0 / totalCycleHours
        val newProgress = (state.researchProgress + hoursElapsed * progressPerHour).toInt()
            .coerceAtMost(100)

        _researchState.value = state.copy(
            researchProgress = newProgress,
            totalResearchHours = state.totalResearchHours + hoursElapsed
        )

        // 成果落地
        if (newProgress >= 100 && state.completedTechs == 0) {
            applyTechBreakthrough()
        }
    }

    /**
     * 技术成果落地 → 全局参数变化
     */
    private fun applyTechBreakthrough() {
        val state = _researchState.value
        val impact = when (state.branch) {
            ResearchCareerBranch.BASIC_RESEARCH -> TechGlobalImpacts.findByTechName("自动化流水线")
            ResearchCareerBranch.APPLIED_RD -> TechGlobalImpacts.findByTechName("家用节能设备")
            else -> null
        } ?: return

        _researchState.value = state.copy(completedTechs = state.completedTechs + 1)

        // 全局影响
        val space = _spaceState.value
        if (impact.triggersInflation) {
            _spaceState.value = space.copy(
                monthlyRent = space.monthlyRent * (1 + impact.priceImpactPercent / 100)
            )
        }

        addEventLog(GentleTextProvider.describeTechGlobalImpact(impact))

        // 产出对应分支的五段式文案
        val fullText = when (state.origin) {
            ResearcherOrigin.AFFLUENT_MIDDLE -> GentleTextProvider.describeAffluentResearchBreakthrough(
                state.totalResearchHours, impact
            )
            ResearcherOrigin.COUNTY_UNDERPRIVILEGED -> GentleTextProvider.describePoorResearcherDevice(
                state.totalResearchHours, state.originConfig.extraWorkHoursForStudy
            )
        }
        addEventLog(fullText)
    }

    /**
     * 技术经纪人对接投产（手动触发）
     */
    fun triggerTechBrokerDeal() {
        val state = _researchState.value
        if (state.branch != ResearchCareerBranch.TECH_BROKER) return

        val dealHours = 2100.0
        val text = GentleTextProvider.describeTechBrokerDeal(dealHours)
        addEventLog(text)
    }

    /**
     * 投机套利者垄断操作（手动触发）
     */
    fun triggerSpeculatorMonopoly() {
        val state = _researchState.value
        if (state.branch != ResearchCareerBranch.TECH_SPECULATOR) return

        val impact = TechGlobalImpacts.findByTechName("垄断型技术产品") ?: return
        val dealHours = 800.0
        val text = GentleTextProvider.describeSpeculatorMonopoly(dealHours, impact.priceImpactPercent)
        addEventLog(text)

        // 全局影响：通胀
        val space = _spaceState.value
        _spaceState.value = space.copy(
            monthlyRent = space.monthlyRent * (1 + impact.priceImpactPercent / 100)
        )
    }

    /**
     * 触发科研随机事件
     */
    fun triggerResearchRandomEvent(): ResearchLifeEvent? {
        val state = _researchState.value
        val events = ResearchLifeEvents.getEventsFor(state.origin, state.branch, state.faction)
        if (events.isEmpty()) return null

        val event = events.random()
        addEventLog(event.description)
        return event
    }

    /**
     * 处理科研事件中的玩家选择
     */
    fun handleResearchEventChoice(event: ResearchLifeEvent, choiceIndex: Int) {
        addEventLog("你选择了：${event.choices.getOrElse(choiceIndex) { "继续思考" }}")

        // 特殊处理：寒门放弃科研转行
        if (event.id == "poor_family_pressure" && choiceIndex == 2) {
            addEventLog("你决定放弃科研方向，先撑起家庭经济。这不是失败——是你选择了当下最紧迫的事。人生还长，科研的路以后还能再走。")
        }

        // 特殊处理：投机者良心叩问选择降价
        if (event.id == "speculator_ethics_check" && choiceIndex == 0) {
            val space = _spaceState.value
            _spaceState.value = space.copy(
                monthlyRent = space.monthlyRent * 0.95  // 降价让利，减轻通胀
            )
            addEventLog("你释放了一部分平价货源。利润少了，但你今晚睡得着了。")
        }

        // 特殊处理：加入学阀派系
        if (event.id == "faction_join_offer" && choiceIndex == 0) {
            joinFaction(AcademicFaction.INSIDER)
        }

        // 特殊处理：独立研究者接受企业合作
        if (event.id == "independent_budget_crisis" && choiceIndex == 1) {
            joinFaction(AcademicFaction.CORPORATE)
        }

        // 特殊处理：放弃独立加入学阀
        if (event.id == "independent_budget_crisis" && choiceIndex == 2) {
            joinFaction(AcademicFaction.INSIDER)
        }

        // 特殊处理：寒门放弃科研
        if (event.id == "poor_family_pressure" && choiceIndex == 2) {
            addEventLog("你决定放弃科研方向，先撑起家庭经济。这不是失败——是你选择了当下最紧迫的事。人生还长，科研的路以后还能再走。")
        }

        // 贫困家人急病（放弃科研）
        if (event.id == "poor_family_emergency" && choiceIndex == 0) {
            addEventLog("你拿出全部积蓄寄回了家。科研暂停了——但家人比论文更重要。你知道这个选择意味着什么，但你不后悔。")
        }

        // 专利被收购
        if (event.id == "patent_buyout_offer" && choiceIndex == 0) {
            triggerPatentMonopoly()
        }
    }

    // ============================================
    // v2.2 学术派系与垄断管理
    // ============================================

    /**
     * 加入指定学术派系
     */
    fun joinFaction(faction: AcademicFaction) {
        val state = _researchState.value
        val oldFaction = state.faction
        if (oldFaction == faction) return

        val newFactionState = when (faction) {
            AcademicFaction.INSIDER -> AcademicFactionState(
                faction = faction,
                resourceLevel = 80,
                autonomyLevel = 30,
                publicationAccess = 90,
                savedResourceHours = 1800.0
            )
            AcademicFaction.CORPORATE -> AcademicFactionState(
                faction = faction,
                resourceLevel = 70,
                autonomyLevel = 40,
                publicationAccess = 50,
                savedResourceHours = 800.0
            )
            AcademicFaction.INDEPENDENT -> AcademicFactionState(
                faction = faction,
                resourceLevel = 30,
                autonomyLevel = 90,
                publicationAccess = 20,
                savedResourceHours = 0.0
            )
        }

        _researchState.value = state.copy(
            faction = faction,
            factionState = newFactionState
        )
        addEventLog(GentleTextProvider.describeFactionSwitch(oldFaction, faction))
    }

    /**
     * 触发学术资源壁垒事件（寒门独立研究者论文被拒）
     */
    fun triggerResourceBarrier() {
        val state = _researchState.value
        if (state.hasEncounteredResourceBarrier) return
        if (state.faction != AcademicFaction.INDEPENDENT) return
        if (state.origin != ResearcherOrigin.COUNTY_UNDERPRIVILEGED) return

        _researchState.value = state.copy(hasEncounteredResourceBarrier = true)
        val text = GentleTextProvider.describePoorResearcherResourceBarrier(
            state.totalResearchHours,
            state.originConfig.extraWorkHoursForStudy + 1400.0
        )
        addEventLog(text)
    }

    /**
     * 触发专利商业垄断
     */
    fun triggerPatentMonopoly() {
        val state = _researchState.value
        if (state.hasEncounteredPatentMonopoly) return

        _researchState.value = state.copy(hasEncounteredPatentMonopoly = true)
        val impact = AcademicMonopolyImpacts.impacts[AcademicMonopolyType.PATENT_MONOPOLY] ?: return
        val text = GentleTextProvider.describePatentMonopolyImpact(
            state.totalResearchHours, impact.priceImpactPercent
        )
        addEventLog(text)

        // 全局通胀
        val space = _spaceState.value
        _spaceState.value = space.copy(
            monthlyRent = space.monthlyRent * (1 + impact.priceImpactPercent / 100)
        )
        addEventLog(GentleTextProvider.describeAcademicMonopolyImpact(impact))
    }

    /**
     * 获取学阀内部权益描述
     */
    fun getAcademicInsiderText(): String {
        val state = _researchState.value
        if (state.faction != AcademicFaction.INSIDER) return ""
        return GentleTextProvider.describeAcademicInsiderAdvantage(
            state.factionState.savedResourceHours
        )
    }

    // ============================================
    // v2.3 跨圈层相遇事件 集成
    // ============================================

    /** 当前触发的相遇事件（供 UI 弹出对话） */
    private val _currentEncounter = MutableStateFlow<CrossClassEncounter?>(null)
    val currentEncounter: StateFlow<CrossClassEncounter?> = _currentEncounter.asStateFlow()

    /** 跨圈层接触次数（用于认知成长追踪） */
    private val _crossClassContacts = MutableStateFlow(0)
    val crossClassContacts: StateFlow<Int> = _crossClassContacts.asStateFlow()

    /** 相遇事件冷却天数（触发后至少间隔 N 天才能再次触发） */
    private var _encounterCooldownDays = 0

    /** 玩家年龄（用于年龄联动——中年更包容） */
    private val _playerAge = MutableStateFlow(25)
    val playerAge: StateFlow<Int> = _playerAge.asStateFlow()

    /**
     * 触发跨圈层相遇随机事件
     *
     * 条件：
     * - 不在冷却期
     * - 当前身份存在可匹配的相遇场景
     * - 节假日/周末/傍晚触发概率更高
     * - 年龄越大，触发后选择包容的概率倾向越高（由 handleEncounterChoice 的 UI 引导体现）
     */
    fun triggerCrossClassEncounter() {
        val pathId = _currentLifePathId
        if (pathId.isEmpty()) return

        // 冷却检查（至少间隔3天）
        if (_encounterCooldownDays > 0) return

        // 查找匹配当前身份的相遇场景
        val candidates = CrossClassEncounters.findByRole(pathId)
        if (candidates.isEmpty()) return

        val encounter = candidates.random()
        _currentEncounter.value = encounter

        // 触发通知日志
        addEventLog(GentleTextProvider.describeEncounterTrigger(encounter))
        addEventLog(GentleTextProvider.describeCrossClassEncounter(encounter))

        // 纯内存模式：相遇事件已通过addEventLog记录
    }

    /**
     * 处理玩家在相遇事件中的选择
     *
     * @param choiceIndex 玩家选择的索引（0=浅层相逢, 1=深度磨合, 2=观念僵持）
     */
    fun handleEncounterChoice(choiceIndex: Int) {
        val encounter = _currentEncounter.value ?: return
        val choice = encounter.choices.getOrElse(choiceIndex) { return }

        // 记录选择日志
        addEventLog(GentleTextProvider.describeEncounterChoiceResult(encounter, choiceIndex))
        addEventLog(GentleTextProvider.describeEncounterClosing(choice.outcome))

        // 根据走向更新参数
        when (choice.outcome) {
            EncounterOutcome.SUPERFICIAL -> {
                // 浅层相逢：跨圈层接触+1，认知微调
                _crossClassContacts.value += 1
            }
            EncounterOutcome.DEEP_INTEGRATION -> {
                // 深度磨合：跨圈层接触+1，认知显著调整
                _crossClassContacts.value += 1

                // 更新精神状态的认知弹性（间接减少认知局限）
                val currentMental = _mentalState.value
                _mentalState.value = currentMental.copy(
                    happiness = (currentMental.happiness + 10).coerceIn(0, 100),
                    senseOfControl = (currentMental.senseOfControl + 8).coerceIn(0, 100),
                    socialFulfillment = (currentMental.socialFulfillment + 15).coerceIn(0, 100)
                )
            }
            EncounterOutcome.STALEMATE -> {
                // 观念僵持：跨圈层接触+1，但情绪可能短暂下降
                _crossClassContacts.value += 1
                val currentMental = _mentalState.value
                _mentalState.value = currentMental.copy(
                    anxiety = (currentMental.anxiety + 5).coerceIn(0, 100),
                    happiness = (currentMental.happiness - 3).coerceIn(0, 100)
                )
            }
        }

        // 设置冷却期（3-7天随机）
        _encounterCooldownDays = (3..7).random()

        // 清除当前相遇
        _currentEncounter.value = null

        // 认知成长提示（每5次跨圈层接触触发）
        if (_crossClassContacts.value % 5 == 0) {
            val growthMsg = GentleTextProvider.describeCognitiveGrowth(_crossClassContacts.value)
            if (growthMsg.isNotEmpty()) {
                addEventLog(growthMsg)
            }
        }
    }

    /**
     * 清除当前相遇事件（玩家关闭弹窗但不做选择）
     */
    fun dismissEncounter() {
        _currentEncounter.value = null
        _encounterCooldownDays = (1..3).random()
    }

    /**
     * 跨圈层相遇调度逻辑（在 tick() 中调用）
     *
     * 触发条件：
     * - 节假日/周末/傍晚（17-20点）高概率：8%
     * - 工作日白天中概率：3%
     * - 深夜/凌晨关闭
     * - 不在冷却期
     */
    private fun dispatchCrossClassEncounter(hour: Int) {
        if (_encounterCooldownDays > 0) return
        if (_currentEncounter.value != null) return
        val pathId = _currentLifePathId
        if (pathId.isEmpty()) return

        // 检查是否有可用的相遇场景
        val candidates = CrossClassEncounters.findByRole(pathId)
        if (candidates.isEmpty()) return

        val isWeekend = _gameDay.value % 7 >= 5
        val isEvening = hour in 17..20
        val baseChance = when {
            isWeekend && isEvening -> 0.08   // 周末傍晚：8%
            isWeekend -> 0.05                // 周末其他时间：5%
            isEvening -> 0.06                // 工作日傍晚：6%
            hour in 10..16 -> 0.03           // 工作日白天：3%
            else -> 0.0                      // 深夜/凌晨关闭
        }

        if (Math.random() < baseChance) {
            triggerCrossClassEncounter()
        }
    }

    /**
     * 每日更新相遇冷却与年龄
     */
    private fun tickEncounterDaily() {
        // 冷却递减
        if (_encounterCooldownDays > 0) {
            _encounterCooldownDays -= 1
        }

        // 每360天（约一年）增加一岁
        if (_gameDay.value % 360 == 0) {
            val newAge = _playerAge.value + 1
            _playerAge.value = newAge
            // 年龄里程碑提示（每10岁）
            if (newAge % 10 == 0) {
                addEventLog(GentleTextProvider.describeAgeTolerance(newAge))
            }
            // 年龄增长，阅历增加带来的精神稳定
            val mental = _mentalState.value
            _mentalState.value = mental.copy(
                senseOfControl = (mental.senseOfControl + 2).coerceIn(0, 100),
                anxiety = (mental.anxiety - 1).coerceIn(0, 100)
            ).coerce()

            // 退休切换：60岁停发工资，改为养老金
            if (newAge == 60) {
                val space = _spaceState.value
                val lastSalary = space.monthlyIncome
                val pension = lastSalary * 0.55  // 养老金约为最后工资的55%
                _spaceState.value = space.copy(monthlyIncome = pension)
                addEventLog("60岁退休，月薪停发。你开始领取养老金，每月 ¥${pension.toInt()}，约为退休前收入的55%。")
            }
            // 晚年养老储蓄提醒
            if (newAge == 55) {
                addEventLog("55岁，退休前夕。检查一下你的存款，是否能支撑起退休后的生活？")
            }
        }
    }

    // ============================================
    // v2.4 日常细碎挫折 集成
    // ============================================

    /** 当前触发的细碎挫折（供 UI 弹出） */
    private val _currentFrustration = MutableStateFlow<DailyFrustration?>(null)
    val currentFrustration: StateFlow<DailyFrustration?> = _currentFrustration.asStateFlow()

    /** 连续遭遇挫折的天数（用于长期焦虑累积） */
    private var _consecutiveFrustrationDays = 0

    /** 长期累积焦虑值（0-100，缓慢增长） */
    private val _longTermAnxiety = MutableStateFlow(0)
    val longTermAnxiety: StateFlow<Int> = _longTermAnxiety.asStateFlow()

    /** 应对方式统计：消极内耗次数 */
    private var _ruminateCount = 0
    /** 应对方式统计：调整补救次数 */
    private var _activeFixCount = 0
    /** 应对方式统计：放空休息次数 */
    private var _restCount = 0

    /**
     * 每日细碎挫折调度（在 dispatchRandomEvents 中调用）
     *
     * 触发规则：
     * - 高频低冲击：每天按概率触发1-2件
     * - 压力/疲劳偏高时，失眠、工作失误、情绪烦躁类概率提升
     * - 中年之后（35+），同等事件的情绪负面波动幅度降低（由 handleFrustrationChoice 中体现）
     * - 深夜/凌晨（0-6点）关闭触发
     */
    private fun dispatchDailyFrustration(hour: Int) {
        if (_currentFrustration.value != null) return
        // 深夜/凌晨不触发日常琐事
        if (hour in 0..6) return
        val body = _bodyState.value

        // 基础触发概率（每小时）：白天约 6%，傍晚约 8%
        val baseChance = when {
            hour in 17..21 -> 0.08   // 傍晚：8%
            hour in 7..16 -> 0.06    // 白天：6%
            else -> 0.03             // 深夜：3%
        }

        // 疲劳/压力加成：疲劳>50，概率翻倍
        val fatigueMultiplier = if (body.fatigueLevel > 50) 2.0 else 1.0
        val finalChance = (baseChance * fatigueMultiplier).coerceAtMost(0.25)

        if (Math.random() < finalChance) {
            triggerDailyFrustration()
        }
    }

    /**
     * 触发一个随机细碎挫折
     *
     * 根据当前状态智能选择事件类型：
     * - 疲劳偏高 → 失眠/工作失误概率提升
     * - 近期有争吵 → 人际冲突概率提升
     */
    fun triggerDailyFrustration() {
        val body = _bodyState.value
        val mental = _mentalState.value

        // 根据状态调整各类别权重
        val weightInsomnia = if (body.fatigueLevel > 60) 8 else 3
        val weightWorkError = if (body.fatigueLevel > 50) 7 else 4
        val weightInterpersonal = if (mental.anxiety > 50) 7 else 4
        val weightProperty = 5
        val weightHealth = if (body.healthScore < 50) 6 else 3
        val weightEnvironmental = 4

        // 加权随机选择类别
        val weightedCategories = listOf(
            DailyFrustrationCategory.PROPERTY_TIME to weightProperty,
            DailyFrustrationCategory.INTERPERSONAL to weightInterpersonal,
            DailyFrustrationCategory.WORK_ERROR to weightWorkError,
            DailyFrustrationCategory.HEALTH to weightInsomnia,
            DailyFrustrationCategory.ENVIRONMENTAL to weightEnvironmental
        )
        val totalWeight = weightedCategories.sumOf { it.second }
        var roll = Math.random() * totalWeight
        var selectedCategory = DailyFrustrationCategory.PROPERTY_TIME
        for ((cat, weight) in weightedCategories) {
            roll -= weight
            if (roll <= 0) {
                selectedCategory = cat
                break
            }
        }

        // 从该类别中随机选一个事件
        val candidates = DailyFrustrations.byCategory(selectedCategory)
        if (candidates.isEmpty()) return
        val frustration = candidates.random()

        _currentFrustration.value = frustration

        // 触发日志
        addEventLog(GentleTextProvider.describeFrustrationTrigger(frustration))
        addEventLog(GentleTextProvider.describeDailyFrustration(frustration))

        // 连续挫折天数追踪
        _consecutiveFrustrationDays += 1

        // 长期焦虑累积提示
        val stressMsg = GentleTextProvider.describeCumulativeStress(_consecutiveFrustrationDays)
        if (stressMsg.isNotEmpty()) {
            addEventLog(stressMsg)
        }

        // 纯内存模式：日常挫折已通过addEventLog记录
    }

    /**
     * 处理玩家对细碎挫折的应对选择
     *
     * @param choiceIndex 0=消极内耗, 1=调整补救, 2=放空休息
     */
    fun handleFrustrationChoice(choiceIndex: Int) {
        val frustration = _currentFrustration.value ?: return
        val choice = frustration.choices.getOrElse(choiceIndex) { return }

        // 年龄调节：35岁以上，负面情绪波动减半
        val age = _playerAge.value
        val ageModifier = if (age >= 35) 0.5 else 1.0

        // 基础效果
        val body = _bodyState.value
        val mental = _mentalState.value
        val baseFatigueChange = frustration.fatigueChange + choice.extraFatigue
        val baseMoodChange = frustration.moodChange + choice.extraMood

        _bodyState.value = body.copy(
            fatigueLevel = (body.fatigueLevel + (baseFatigueChange * ageModifier).toInt()).coerceIn(0, 100),
            mood = (body.mood + (baseMoodChange * ageModifier).toInt()).coerceIn(0, 100)
        )

        // 应对方式统计
        when (choice.coping) {
            FrustrationCoping.RUMINATE -> {
                _ruminateCount += 1
                // 长期焦虑累积
                _longTermAnxiety.value = (_longTermAnxiety.value + 3).coerceIn(0, 100)
            }
            FrustrationCoping.ACTIVE_FIX -> {
                _activeFixCount += 1
                // 积极应对降低长期焦虑
                _longTermAnxiety.value = (_longTermAnxiety.value - 1).coerceIn(0, 100)
            }
            FrustrationCoping.REST -> {
                _restCount += 1
                _longTermAnxiety.value = (_longTermAnxiety.value - 2).coerceIn(0, 100)
            }
        }

        // 记录选择日志
        addEventLog(GentleTextProvider.describeFrustrationChoiceResult(frustration, choiceIndex))
        addEventLog(GentleTextProvider.describeFrustrationClosing(choice.coping))

        // 长期焦虑过高时提示
        if (_longTermAnxiety.value >= 80) {
            addEventLog("你长期处于高压状态——频繁的琐事正在消耗你的内心。你值得给自己放一个长假。这不是逃避，是自我保护。")
        }

        // 挫折联动信念消解
        applyFrustrationToBelief(_consecutiveFrustrationDays)

        // 清除当前挫折
        _currentFrustration.value = null
    }

    /**
     * 清除当前挫折事件（玩家关闭弹窗不做选择）
     */
    fun dismissFrustration() {
        _currentFrustration.value = null
        // 不做选择也视为一次挫折体验
        _consecutiveFrustrationDays += 1
    }

    /**
     * 每日重置连续挫折天数（如果当天没有挫折）
     */
    private fun tickFrustrationDaily() {
        // 如果今天没有遇到挫折，重置连续天数
        // 此方法在每日开始时由 tick() 调用
        _consecutiveFrustrationDays = 0
    }

    // ============================================
    // v2.5 信念系统 集成
    // ============================================

    /** 信念运行时状态 */
    private val _beliefState = MutableStateFlow(BeliefState())
    val beliefState: StateFlow<BeliefState> = _beliefState.asStateFlow()

    /** 信念消解事件冷却天数 */
    private var _beliefErosionCooldown = 0

    /**
     * 初始化信念系统（玩家选择了科研/艺术/创业等理想型职业时调用）
     */
    fun initBelief(origin: BeliefOrigin) {
        _beliefState.value = BeliefState(
            beliefValue = 80,
            origin = origin,
            yearsHeld = 0,
            isStillHolding = true
        )
        addEventLog("你选择了${origin.label}。这是一条漫长而艰难的路——你知道这一点，但你还是踏上了第一步。")
    }

    /**
     * 每日信念消解调度（在 tick() 的新一天调用）
     *
     * 规则：
     * - 有信念系统才触发
     * - 疲劳/焦虑越高，消解概率越大
     * - 年龄增长，自然衰减
     * - 细碎挫折累积天数越高，消解概率越大
     */
    private fun tickBeliefDaily() {
        val state = _beliefState.value
        if (!state.isStillHolding || state.origin == null) return

        // 冷却检查
        if (_beliefErosionCooldown > 0) {
            _beliefErosionCooldown -= 1
            return
        }

        val body = _bodyState.value
        val mental = _mentalState.value

        // 年龄自然衰减：每年信念值小幅下降（青年期信念最高，中年后加速下降）
        val age = _playerAge.value
        val ageDecay = when {
            age < 25 -> 0   // 青年：信念未受侵蚀
            age < 35 -> 1   // 中青年：轻微磨损
            age < 50 -> 2   // 中年：加速磨损
            else -> 3       // 老年：持续磨损
        }

        // 疲劳/焦虑加成
        val fatigueBonus = if (body.fatigueLevel > 60) 2 else 0
        val anxietyBonus = if (mental.anxiety > 60) 2 else 0
        val consecutiveBonus = if (_consecutiveFrustrationDays > 3) 1 else 0

        val totalDecay = ageDecay + fatigueBonus + anxietyBonus + consecutiveBonus

        if (totalDecay > 0) {
            val newBelief = (state.beliefValue - totalDecay).coerceIn(0, 100)
            _beliefState.value = state.copy(beliefValue = newBelief)
        }

        // 随机信念消解事件（概率受疲劳/焦虑/连续挫折天数加成）
        val erosionEvent = BeliefErosionEvents.events.random()
        val erosionChance = erosionEvent.baseProbability +
                (body.fatigueLevel / 500.0) +
                (mental.anxiety / 500.0) +
                (_consecutiveFrustrationDays / 200.0)

        if (Math.random() < erosionChance) {
            val newBelief = (state.beliefValue - erosionEvent.beliefDecay).coerceIn(0, 100)
            _beliefState.value = state.copy(
                beliefValue = newBelief,
                totalFailures = state.totalFailures + 1,
                lastErosionCause = erosionEvent.cause
            )
            addEventLog(GentleTextProvider.describeBeliefErosion(erosionEvent))
            _beliefErosionCooldown = (5..15).random()
        }

        // 信念值变化时检查里程碑
        checkBeliefMilestones()
    }

    /**
     * 每年信念系统更新（在 tickYear() 中调用）
     */
    private fun tickBeliefYearly() {
        val state = _beliefState.value
        if (!state.isStillHolding || state.origin == null) return

        val newYears = state.yearsHeld + 1
        _beliefState.value = state.copy(yearsHeld = newYears)

        // 坚守年限里程碑
        val yearMilestones = BeliefMilestones.milestones.filter {
            it.id.startsWith("years_held_") && it.id.contains(newYears.toString())
        }
        for (milestone in yearMilestones) {
            addEventLog(GentleTextProvider.describeBeliefMilestone(milestone, newYears))
        }

        // 信念值低于阈值时触发崩溃事件
        if (state.beliefValue <= 10 && state.isStillHolding) {
            _beliefState.value = state.copy(
                isStillHolding = false,
                beliefValue = 0,
                turningPoint = "第${newYears}年，信念彻底崩塌。"
            )
            addEventLog(GentleTextProvider.describeBeliefBroken())
        }

        // 坚守多年但从未成功（20年+）
        if (newYears >= 20 && !state.hasAchievedSuccess) {
            addEventLog(GentleTextProvider.describeBeliefLifelongFailure(newYears))
        }
    }

    /**
     * 检查信念值里程碑
     */
    private fun checkBeliefMilestones() {
        val state = _beliefState.value
        val thresholds = listOf(90, 70, 50, 30, 10)
        for (threshold in thresholds) {
            val milestone = BeliefMilestones.milestones.find {
                it.beliefThreshold == threshold && it.isPositive == (state.beliefValue >= threshold)
            }
            if (milestone != null && state.beliefValue == threshold) {
                addEventLog(GentleTextProvider.describeBeliefMilestone(milestone, state.yearsHeld))
            }
        }
    }

    /**
     * 日常挫折完成后，联动信念消解
     */
    fun applyFrustrationToBelief(frustrationCount: Int) {
        val state = _beliefState.value
        if (!state.isStillHolding || state.origin == null) return

        // 每3次日常挫折，信念微量下降
        if (frustrationCount > 0 && frustrationCount % 3 == 0) {
            val newBelief = (state.beliefValue - 1).coerceIn(0, 100)
            _beliefState.value = state.copy(beliefValue = newBelief)
        }
    }

    // ============================================
    // v2.5 代际认知对话 集成
    // ============================================

    /** 当前触发的代际对话 */
    private val _currentGenerationalDialogue = MutableStateFlow<GenerationalDialogue?>(null)
    val currentGenerationalDialogue: StateFlow<GenerationalDialogue?> = _currentGenerationalDialogue.asStateFlow()

    /** 是否已经触发过青年觉醒事件 */
    private var _youthRealizationTriggered = false
    /** 是否已经触发过中年闭环事件 */
    private var _midlifeClosureTriggered = false
    /** 代际循环是否被打破（用于中年阶段选择） */
    private var _generationalCycleBroken = false

    /**
     * 代际对话调度（在 dispatchRandomEvents 中调用）
     *
     * 触发规则：
     * - 孩童阶段（0-18岁）：触发消费诉求对话（长辈说"等长大就好了"）
     * - 青年阶段（25-30岁）：触发青年觉醒对话（发现"等长大就好了"不够用）
     * - 中年阶段（40-50岁）：触发中年闭环对话（面对下一代，延续还是打破）
     */
    private fun dispatchGenerationalDialogue(hour: Int) {
        val age = _playerAge.value
        val isEvening = hour in 17..21  // 下班/放学后更容易触发

        when {
            // 孩童阶段：消费诉求（不阻塞，可重复触发）
            !isEvening -> return

            // 青年阶段：25-30岁，触发一次觉醒
            age in 25..30 && !_youthRealizationTriggered -> {
                if (Math.random() < 0.03) {
                    triggerGenerationalDialogue("youth_realization")
                    _youthRealizationTriggered = true
                }
            }
            // 中年阶段：40-50岁，触发一次闭环
            age in 40..50 && !_midlifeClosureTriggered -> {
                if (Math.random() < 0.03) {
                    triggerGenerationalDialogue("midlife_closure")
                    _midlifeClosureTriggered = true
                }
            }
        }
    }

    /**
     * 触发代际对话
     */
    fun triggerGenerationalDialogue(dialogueId: String) {
        val dialogue = GenerationalDialogues.findById(dialogueId) ?: return
        _currentGenerationalDialogue.value = dialogue

        addEventLog("【${dialogue.type.label}】${dialogue.triggerContext}")
        addEventLog(GentleTextProvider.describeGenerationalDialogue(dialogue))

        // 纯内存模式：代际对话已通过addEventLog记录
    }

    /**
     * 处理代际对话的玩家选择
     */
    fun handleGenerationalChoice(choiceIndex: Int) {
        val dialogue = _currentGenerationalDialogue.value ?: return
        val choice = dialogue.choices.getOrElse(choiceIndex) { return }

        addEventLog(GentleTextProvider.describeGenerationalChoiceResult(dialogue, choiceIndex))

        when (dialogue.id) {
            "youth_realization" -> {
                if (choice.mode == GenerationalResponseMode.LOGICAL_ANALYSIS) {
                    // 选择了底层逻辑思考，认知升级
                    _generationalCycleBroken = true
                    addEventLog(GentleTextProvider.describeGenerationalClosing(true))
                    // 精神层面提升
                    val mental = _mentalState.value
                    _mentalState.value = mental.copy(
                        senseOfControl = (mental.senseOfControl + 15).coerceIn(0, 100),
                        happiness = (mental.happiness + 5).coerceIn(0, 100)
                    )
                } else {
                    addEventLog(GentleTextProvider.describeGenerationalClosing(false))
                }
            }
            "midlife_closure" -> {
                if (choice.mode == GenerationalResponseMode.LOGICAL_ANALYSIS) {
                    _generationalCycleBroken = true
                    addEventLog(GentleTextProvider.describeGenerationalClosing(true))
                } else {
                    addEventLog(GentleTextProvider.describeGenerationalClosing(false))
                }
            }
        }

        _currentGenerationalDialogue.value = null
    }

    /**
     * 清除代际对话
     */
    fun dismissGenerationalDialogue() {
        _currentGenerationalDialogue.value = null
    }

    // ============================================
    // v2.6 阶层行为倾向 集成
    // ============================================

    /** 阶层行为运行时状态 */
    private val _classBehaviorState = MutableStateFlow(ClassBehaviorState())
    val classBehaviorState: StateFlow<ClassBehaviorState> = _classBehaviorState.asStateFlow()

    /** 后天转折事件冷却天数 */
    private var _postnatalShiftCooldown = 0
    /** 是否已触发过初始行为模式 */
    private var _initialModeRolled = false

    /**
     * 初始化阶层行为系统（角色创建时调用）
     *
     * @param background 阶层出身
     */
    fun initClassBehavior(background: ClassBackground) {
        val config = ClassBehaviorConfigs.getConfig(background)
        val state = when (background) {
            ClassBackground.UNDERPRIVILEGED -> {
                val mode = ClassBehaviorConfigs.rollPoorPowerMode()
                ClassBehaviorState(
                    background = background,
                    survivalAnxiety = config.initialSurvivalAnxiety,
                    isInPower = true,
                    powerMode = mode,
                    anxietyDescription = config.anxietyDescription
                )
            }
            ClassBackground.AFFLUENT -> {
                val mode = ClassBehaviorConfigs.rollEliteMode()
                ClassBehaviorState(
                    background = background,
                    survivalAnxiety = config.initialSurvivalAnxiety,
                    isInPower = true,
                    eliteMode = mode,
                    anxietyDescription = config.anxietyDescription
                )
            }
        }
        _classBehaviorState.value = state
        _initialModeRolled = true

        // 初始日志
        addEventLog(GentleTextProvider.describeClassInitialState(background, config.initialSurvivalAnxiety))
        when (background) {
            ClassBackground.UNDERPRIVILEGED ->
                addEventLog(GentleTextProvider.describePowerBehavior(state.powerMode!!, background))
            ClassBackground.AFFLUENT ->
                addEventLog(GentleTextProvider.describeEliteBehavior(state.eliteMode!!))
        }
    }

    /**
     * 每日阶层行为参数更新
     *
     * 规则：
     * - 长期安稳掌权 → 生存焦虑缓慢下降
     * - 遭遇挫折/面临威胁 → 焦虑上升
     * - 后天转折事件随机触发
     */
    private fun tickClassBehaviorDaily() {
        val state = _classBehaviorState.value
        if (!_initialModeRolled) return

        // 冷却处理
        if (_postnatalShiftCooldown > 0) {
            _postnatalShiftCooldown -= 1
        }

        val body = _bodyState.value
        val mental = _mentalState.value

        // 生存焦虑自然衰减：长期安稳掌权（每月约降1点），但受挫折阻止
        val anxietyDecay = if (state.yearsInPower >= 3 && _consecutiveFrustrationDays == 0) {
            if (Math.random() < 0.1) 1 else 0  // 10%概率每天降1点
        } else 0

        // 生存焦虑上升：连续挫折、财务紧张
        val anxietyRise = when {
            _consecutiveFrustrationDays >= 7 -> 2
            body.fatigueLevel > 70 -> 1
            _spaceState.value.currentSavings < 5000.0 -> 1
            mental.anxiety > 70 -> 1
            else -> 0
        }

        val newAnxiety = (state.survivalAnxiety - anxietyDecay + anxietyRise).coerceIn(0, 100)
        val newState = state.copy(survivalAnxiety = newAnxiety)

        // 焦虑消解到阈值时更新行为模式
        if (state.background == ClassBackground.UNDERPRIVILEGED && state.powerMode != null) {
            val adjustedMode = ClassBehaviorConfigs.adjustModeByPostnatalShift(
                state.powerMode, state.postnatalModifier
            )
            // 焦虑长期降低后，强硬管控可转为中庸
            val newMode = if (newAnxiety <= 40 && state.powerMode == PowerBehaviorMode.HARSH_CONTROL
                && state.yearsInPower >= 5
            ) {
                PowerBehaviorMode.MODERATE
            } else adjustedMode

            if (newMode != state.powerMode && state.yearsInPower >= 5) {
                addEventLog(GentleTextProvider.describeBehaviorModeShift(
                    state.powerMode.label, newMode.label,
                    "长期安稳掌权让焦虑消解，处事逐渐缓和。"
                ))
            }
            _classBehaviorState.value = newState.copy(powerMode = newMode)
        } else if (state.background == ClassBackground.AFFLUENT && state.eliteMode != null) {
            val adjustedMode = ClassBehaviorConfigs.adjustEliteModeByPostnatalShift(
                state.eliteMode, state.postnatalModifier
            )
            _classBehaviorState.value = newState.copy(eliteMode = adjustedMode)
        } else {
            _classBehaviorState.value = newState
        }

        // 随机后天转折事件
        if (_postnatalShiftCooldown <= 0 && Math.random() < 0.01) {
            triggerPostnatalShift()
        }
    }

    /**
     * 每年阶层行为参数更新
     */
    private fun tickClassBehaviorYearly() {
        val state = _classBehaviorState.value
        if (!_initialModeRolled) return

        val newYears = state.yearsInPower + 1
        _classBehaviorState.value = state.copy(yearsInPower = newYears)

        // 掌权10年后（焦虑大幅缓解），可能触发重大行为转变
        if (newYears == 10 && state.background == ClassBackground.UNDERPRIVILEGED
            && state.survivalAnxiety <= 30
        ) {
            addEventLog("你掌权十年了。当初那份深植于恐惧的强硬，已经慢慢松开了。你不再需要靠控制一切来确保自己不会跌回去。")
        }
    }

    /**
     * 触发后天转折事件
     */
    fun triggerPostnatalShift() {
        val state = _classBehaviorState.value
        if (!_initialModeRolled || state.hasPostnatalShift) return

        val event = PostnatalShiftEvents.events.random()
        val newModifier = (state.postnatalModifier + event.shiftAmount).coerceIn(-30, 30)

        val newState = state.copy(
            postnatalModifier = newModifier,
            hasPostnatalShift = true,
            shiftDescription = event.description
        )
        _classBehaviorState.value = newState

        addEventLog(GentleTextProvider.describePostnatalShift(event))

        // 根据后天偏移调整行为模式
        when (state.background) {
            ClassBackground.UNDERPRIVILEGED -> {
                val adjustedMode = ClassBehaviorConfigs.adjustModeByPostnatalShift(
                    state.powerMode ?: PowerBehaviorMode.HARSH_CONTROL, newModifier
                )
                if (adjustedMode != state.powerMode) {
                    _classBehaviorState.value = newState.copy(powerMode = adjustedMode)
                    addEventLog(GentleTextProvider.describeBehaviorModeShift(
                        state.powerMode?.label ?: "",
                        adjustedMode.label,
                        event.description
                    ))
                }
            }
            ClassBackground.AFFLUENT -> {
                val adjustedMode = ClassBehaviorConfigs.adjustEliteModeByPostnatalShift(
                    state.eliteMode ?: EliteBehaviorMode.MILD_MAINTAIN, newModifier
                )
                if (adjustedMode != state.eliteMode) {
                    _classBehaviorState.value = newState.copy(eliteMode = adjustedMode)
                    addEventLog(GentleTextProvider.describeBehaviorModeShift(
                        state.eliteMode?.label ?: "",
                        adjustedMode.label,
                        event.description
                    ))
                }
            }
        }

        _postnatalShiftCooldown = (30..90).random()
    }

    /**
     * 玩家主动选择行为模式（自由准则）
     *
     * 底层：可选择强硬/中庸/体恤
     * 富裕：可选择温和维护/革新/骄横
     */
    fun chooseBehaviorMode(modeLabel: String) {
        val state = _classBehaviorState.value
        when (state.background) {
            ClassBackground.UNDERPRIVILEGED -> {
                val newMode = PowerBehaviorMode.entries.find { it.label == modeLabel } ?: return
                val oldMode = state.powerMode?.label ?: ""
                _classBehaviorState.value = state.copy(powerMode = newMode)
                addEventLog(GentleTextProvider.describeBehaviorModeShift(
                    oldMode, newMode.label, "你主动做出了选择。"
                ))
            }
            ClassBackground.AFFLUENT -> {
                val newMode = EliteBehaviorMode.entries.find { it.label == modeLabel } ?: return
                val oldMode = state.eliteMode?.label ?: ""
                _classBehaviorState.value = state.copy(eliteMode = newMode)
                addEventLog(GentleTextProvider.describeBehaviorModeShift(
                    oldMode, newMode.label, "你主动做出了选择。"
                ))
            }
        }
    }

    /**
     * 获取阶层行为整体评述
     */
    fun getClassBehaviorSummary(): String {
        return GentleTextProvider.describeClassBehaviorSummary(_classBehaviorState.value)
    }

    // ============================================
    // v2.7 革新者隐藏角色 集成
    // ============================================

    /** 革新者解锁进度状态 */
    private val _reformerState = MutableStateFlow(ReformerUnlockState())
    val reformerState: StateFlow<ReformerUnlockState> = _reformerState.asStateFlow()

    /** 是否具备革新者候选资质（千分之三概率） */
    private var _hasReformerPotential = false
    /** 中年危机事件是否已触发 */
    private var _midlifeCrisisTriggered = false
    /** 改革行动冷却天数 */
    private var _reformCooldownDays = 0
    /** 候选资质检测年份（开局后N年首次检测） */
    private val reformCheckStartYear = 20

    /**
     * 开局检测革新者候选资质（千分之三概率）
     *
     * 概率约束：结合出身枷锁、生存焦虑、圈层阻挠、信念坚守、时代限制
     * 整体原生随机生成概率设定在 0.3% 以内
     */
    fun initReformerPotential() {
        // 千分之三概率
        _hasReformerPotential = Math.random() < 0.003

        // 纯内存模式：革新者潜质已记录到内存状态
    }

    /**
     * 每日革新者资质检测调度
     *
     * 检测时序：
     * 1. 开局后N年内：完全不可见，和普通角色无区别
     * 2. 中年阶段：检测三个前置条件，未达成则继续等待或关闭
     * 3. 前置全部达成 + 时代危机：弹出抉择 -> 解锁支线
     */
    private fun tickReformerDaily() {
        val state = _reformerState.value
        if (!_hasReformerPotential || state.branchClosed || state.isReformer) return

        // 冷却处理
        if (_reformCooldownDays > 0) {
            _reformCooldownDays -= 1
            return
        }

        val age = _playerAge.value
        // 开局20年内：完全不可见
        if (age < reformCheckStartYear) return

        // 中年阶段（35-55岁）：定期检测前置条件
        if (age in 35..55 && _reformCooldownDays <= 0) {
            checkReformerPrerequisites()
            _reformCooldownDays = 90  // 每季度检测一次
        }

        // 55岁后仍未触发：自动转为隐性思想革新者或关闭
        if (age > 55 && !state.isReformer && !state.branchClosed) {
            // 如果认知+信念达成，但时代窗口未开 → 隐性思想革新者
            if (state.cognitiveMet && state.beliefDurable && !state.eraWindowOpen) {
                unlockAsImplicitReformer()
            } else {
                closeReformerBranch("年龄已超过革新窗口期。时代不会等一个人太久。")
            }
        }
    }

    /**
     * 检测三个革新者前置条件
     */
    private fun checkReformerPrerequisites() {
        val belief = _beliefState.value
        val classBehavior = _classBehaviorState.value
        val age = _playerAge.value

        // 条件1：认知门槛
        val cognitiveMet = when (classBehavior.background) {
            ClassBackground.UNDERPRIVILEGED -> {
                // 底层：需跳出匮乏焦虑思维
                classBehavior.survivalAnxiety <= 40 && age >= 35
            }
            ClassBackground.AFFLUENT -> {
                // 富人：需跳出圈层壁垒，认知限制减少
                classBehavior.postnatalModifier >= 5 && age >= 35
            }
            else -> false
        }

        // 条件2：信念耐久
        val beliefDurable = belief.isStillHolding &&
                belief.beliefValue >= 60 &&
                belief.yearsHeld >= 15 &&  // 至少坚守15年
                _crossClassContacts.value >= 5  // 至少5次跨圈层接触

        // 条件3：时代窗口
        val eraWindowOpen = when {
            // 行业垄断严重 → 改革窗口
            _researchState.value.hasEncounteredResourceBarrier || _researchState.value.hasEncounteredPatentMonopoly -> true
            // 通胀高企 → 改革窗口
            _currentYear.value % 10 == 0 && Math.random() < 0.3 -> true
            // 就业环境恶化 → 改革窗口
            _currentYear.value > 30 -> Math.random() < 0.2
            else -> false
        }

        val newState = _reformerState.value.copy(
            cognitiveMet = cognitiveMet,
            beliefDurable = beliefDurable,
            eraWindowOpen = eraWindowOpen
        )
        _reformerState.value = newState

        // 三个条件全部达成的日志（仅内部记录，不通知玩家）
        if (newState.allPrerequisitesMet && !_midlifeCrisisTriggered) {
            triggerMidlifeCrisis()
        }

        // 条件达成但玩家可查的提示
        if (cognitiveMet && !_reformerState.value.cognitiveMet) {
            // 认知门槛首次达成
        }
    }

    /**
     * 中年危机抉择节点：时代危机触发
     */
    fun triggerMidlifeCrisis() {
        if (_midlifeCrisisTriggered) return
        _midlifeCrisisTriggered = true

        // 只有前瞻条件达成才向玩家展示
        addEventLog(GentleTextProvider.describeReformerMidlifeCrisis())
    }

    /**
     * 玩家在中年危机节点选择：站出来改革
     */
    fun chooseReformerPath() {
        _playerManuallyChoseMidlifePathThisWeek = true
        val state = _reformerState.value
        if (!state.allPrerequisitesMet || state.branchClosed) return

        // 根据时代窗口决定是显性还是隐性
        if (state.eraWindowOpen) {
            // 显性落地革新者
            _reformerState.value = state.copy(
                isReformer = true,
                form = ReformerForm.EXPLICIT
            )
            addEventLog(GentleTextProvider.describeReformerUnlock(ReformerForm.EXPLICIT))
        } else {
            // 隐性思想革新者
            unlockAsImplicitReformer()
        }
    }

    /**
     * 玩家在中年危机节点选择：稳妥自保（永久关闭支线）
     */
    fun chooseSafePath() {
        _playerManuallyChoseMidlifePathThisWeek = true
        closeReformerBranch("你选择了稳妥自保。这不是错——大多数人都会这么选。你依然是个好人。")
    }

    /**
     * 解锁为隐性思想革新者
     */
    private fun unlockAsImplicitReformer() {
        val state = _reformerState.value
        if (state.branchClosed || state.isReformer) return

        _reformerState.value = state.copy(
            isReformer = true,
            form = ReformerForm.IMPLICIT,
            isLegacySeed = true,
            legacyArchive = "一代思想者的改革构想——时代未至，火种已埋。\n" +
                    "记录年份：第${_currentYear.value}年\n" +
                    "传承标记：待后人开启"
        )
        addEventLog(GentleTextProvider.describeReformerUnlock(ReformerForm.IMPLICIT))
        addEventLog(GentleTextProvider.describeLegacySeed())
    }

    /**
     * 永久关闭革新者支线
     */
    private fun closeReformerBranch(reason: String) {
        _reformerState.value = _reformerState.value.copy(
            branchClosed = true,
            closureReason = reason
        )
        addEventLog(GentleTextProvider.describeReformerBranchClosed(reason))
    }

    /**
     * 执行改革行动（仅显性落地革新者可调用）
     */
    fun executeReform(actionId: String) {
        val state = _reformerState.value
        if (!state.isReformer || state.form != ReformerForm.EXPLICIT) return
        if (state.branchClosed) return

        val action = ReformActions.actions.find { it.id == actionId } ?: return

        // 认知门槛检查
        val belief = _beliefState.value
        if (belief.beliefValue < action.requiredCognition) {
            addEventLog("你的认知深度尚不足以推动「${action.title}」这项改革。需要继续打磨。")
            return
        }

        // 成功概率计算（基础 + 民众支持加成 + 时代窗口加成）
        val successBonus = (state.publicSupport - 50) / 100.0 + 0.1  // 民众支持加成
        val actualRate = (action.baseSuccessRate + successBonus).coerceIn(0.05, 0.85)
        val success = Math.random() < actualRate

        // 改革结果描述
        val resultDescription = if (success) {
            "经过漫长博弈，「${action.title}」终于正式落地。你签下文件的那一刻——你知道，从今天起，一些事情不一样了。"
        } else {
            when {
                action.resistanceLevel >= 80 ->
                    "既得利益群体的抵制太猛烈了。「${action.title}」被暂时搁置——你低估了对手的力量。但你不是被击败的，是被暂时挡住的。"
                Math.random() < 0.5 ->
                    "「${action.title}」在最后关头被否决了。你数月的努力付诸东流——但你知道，这不是结束。"
                else ->
                    "「${action.title}」以折中方案的形式通过了——被大幅削弱。这不是你想要的完整版本，但至少迈出了一步。"
            }
        }

        val aftermathDescription = if (success) {
            "民众支持度提升。${action.eraCost}"
        } else {
            "改革受阻，民众支持度小幅下降，既得利益群体的抵制更加嚣张。你需要做好打持久战的准备。"
        }

        val result = ReformResult(
            action = action,
            success = success,
            actualSuccessRate = actualRate,
            resultDescription = resultDescription,
            aftermathDescription = aftermathDescription,
            causesBranchClosure = !success && state.failedAttempts >= 2
        )

        // 更新状态
        val newCompleted = if (success) state.completedActions + actionId else state.completedActions
        val newFailures = if (success) state.failedAttempts else state.failedAttempts + 1
        val newSupport = (state.publicSupport + (if (success) action.publicSupportImpact * 0.5 else -5.0))
            .toInt().coerceIn(0, 100)
        val newResistance = (state.resistanceIntensity + (if (success) -5 else 10)).coerceIn(0, 100)

        _reformerState.value = state.copy(
            completedActions = newCompleted,
            failedAttempts = newFailures,
            publicSupport = newSupport,
            resistanceIntensity = newResistance,
            branchClosed = result.causesBranchClosure
        )

        // 日志
        addEventLog(GentleTextProvider.describeReformAction(action))
        addEventLog(GentleTextProvider.describeReformResult(result))

        if (success) {
            addEventLog(GentleTextProvider.describeReformEraAnnouncement(action))
        } else {
            addEventLog(GentleTextProvider.describeReformFailedButArchived(action))
        }

        // 连续失败导致支线关闭
        if (result.causesBranchClosure) {
            closeReformerBranch("连续改革失败，既得利益群体的反击已超出你的承受范围。你选择了退让——这不是懦弱，是留得青山。")
        }

        // 完成所有领域改革：人生总结
        if (state.completedActions.size >= ReformDomain.entries.size) {
            addEventLog(GentleTextProvider.describeReformerLifeSummary(_reformerState.value))
        }
    }

    /**
     * 获取当前可执行的改革行动列表
     */
    fun getAvailableReforms(): List<ReformAction> {
        val state = _reformerState.value
        if (!state.isReformer || state.form != ReformerForm.EXPLICIT) return emptyList()
        val belief = _beliefState.value
        return ReformActions.feasibleActions(belief.beliefValue)
    }

    // ============================================
    // v2.8 心理内核 集成（内层基础层）
    // ============================================

    /** 心理内核状态 */
    private val _psychologicalCore = MutableStateFlow(PsychologicalCore())
    val psychologicalCore: StateFlow<PsychologicalCore> = _psychologicalCore.asStateFlow()

    /** 心理摘要日志冷却天数 */
    private var _psychSummaryCooldown = 0
    /** 晚年叙事是否已触发 */
    private var _lateLifeNarrativeTriggered = false

    /**
     * 每日心理内核自然恢复
     */
    private fun tickPsychologicalDaily() {
        val psych = _psychologicalCore.value

        var happiness = psych.happiness
        var loneliness = psych.loneliness
        var anxiety = psych.anxiety
        var resilience = psych.beliefResilience
        var identity = psych.selfIdentity
        var nihilism = psych.nihilism
        var empathy = psych.empathy

        // 孤独：原子化社会基线偏高，独居时自然+1，有社交时-1
        if (psych.consecutiveLonelyDays >= 3) {
            loneliness = (loneliness + 2).coerceIn(0, 100)
        } else if (psych.daysSinceDeepConnection <= 3) {
            loneliness = (loneliness - 1).coerceIn(0, 100)
        }

        // 焦虑：收支稳定缓慢回落
        val savings = _spaceState.value.currentSavings
        if (savings >= _spaceState.value.monthlyRent * 3) {
            anxiety = (anxiety - 1).coerceIn(0, 100)
        }

        // 自我认同：平淡日子无特殊事件时不变化
        // 信念韧性：有目标时+1
        if (_beliefState.value.isStillHolding) {
            resilience = (resilience + 1).coerceIn(0, 100)
        }
        if (_currentLifePathId != null && Math.random() < 0.1) {
            resilience = (resilience - 1).coerceIn(0, 100)  // 日常随机损耗
        }

        // 共情：长期脱离大众缓慢下降
        if (_crossClassContacts.value == 0 && Math.random() < 0.05) {
            empathy = (empathy - 1).coerceIn(0, 100)
        }

        // 虚无迷茫：中年以后自然缓慢上升
        if (_playerAge.value >= 35 && Math.random() < 0.05) {
            nihilism = (nihilism + 1).coerceIn(0, 100)
        }

        // 幸福感由其余参数综合结算（取其余6维反向前加权均值的变化漂移）
        val avgOthers = ((100 - loneliness) + (100 - anxiety) + resilience + identity +
                (100 - nihilism) + empathy) / 6
        happiness = (happiness + (avgOthers - happiness) / 7).coerceIn(0, 100)

        // 社交意愿 = 反孤独×0.6 + 反焦虑×0.2 + 幸福感×0.2
        val newSocialWill = (((100 - loneliness) * 0.6) + ((100 - anxiety) * 0.2) + (happiness * 0.2))
            .toInt().coerceIn(0, 100)

        // 长期规划意愿 = 信念韧性×0.3 + 反迷茫×0.4 + 自我认同×0.3
        val newLongTermPlanning = ((resilience * 0.3) + ((100 - nihilism) * 0.4) + (identity * 0.3))
            .toInt().coerceIn(0, 100)

        val consecutiveLonely = if (loneliness > 60) psych.consecutiveLonelyDays + 1 else 0
        val consecutiveAnxious = if (anxiety > 60) psych.consecutiveAnxiousDays + 1 else 0
        val daysSinceDeep = psych.daysSinceDeepConnection + 1

        _psychologicalCore.value = psych.copy(
            happiness = happiness,
            loneliness = loneliness,
            anxiety = anxiety,
            beliefResilience = resilience,
            selfIdentity = identity,
            nihilism = nihilism,
            empathy = empathy,
            mentalEnergy = psych.overallHealth,
            socialWill = newSocialWill,
            longTermPlanning = newLongTermPlanning,
            consecutiveLonelyDays = consecutiveLonely,
            consecutiveAnxiousDays = consecutiveAnxious,
            daysSinceDeepConnection = daysSinceDeep
        )

        // 心理摘要日志（每7天一次）
        if (_psychSummaryCooldown <= 0) {
            val changes = mutableListOf<String>()
            if (psych.loneliness > 60) changes.add("孤独感")
            if (psych.anxiety > 60) changes.add("焦虑感")
            if (psych.nihilism > 50) changes.add("对意义的迷茫")
            if (psych.selfIdentity < 35) changes.add("自我怀疑")
            if (psych.beliefResilience < 35) changes.add("心力耗损")

            if (changes.isNotEmpty()) {
                val psychCopy = _psychologicalCore.value
                val hint = GentleTextProvider.describePsychImpactHint("时间流逝", psychCopy)
                if (hint.isNotEmpty()) {
                    addEventLog(hint)
                }
            }
            _psychSummaryCooldown = 7
        } else {
            _psychSummaryCooldown -= 1
        }

        // 晚年叙事检测
        if (_playerAge.value >= 60 && !_lateLifeNarrativeTriggered) {
            triggerLateLifeNarrative()
        }
    }

    /**
     * 应用外部事件对心理参数的影响
     */
    fun applyExternalPsychImpact(impact: ExternalPsychImpact) {
        val psych = _psychologicalCore.value
        _psychologicalCore.value = psych.copy(
            happiness = (psych.happiness + impact.happinessDelta).coerceIn(0, 100),
            loneliness = (psych.loneliness + impact.lonelinessDelta).coerceIn(0, 100),
            anxiety = (psych.anxiety + impact.anxietyDelta).coerceIn(0, 100),
            beliefResilience = (psych.beliefResilience + impact.resilienceDelta).coerceIn(0, 100),
            selfIdentity = (psych.selfIdentity + impact.identityDelta).coerceIn(0, 100),
            nihilism = (psych.nihilism + impact.nihilismDelta).coerceIn(0, 100),
            empathy = (psych.empathy + impact.empathyDelta).coerceIn(0, 100),
            daysSinceDeepConnection = 0  // 有意义的外部事件重置连接天数
        )
    }

    /**
     * 原子化心理事件调度
     */
    private fun dispatchAtomizedPsychEvent(hour: Int) {
        val isWeekend = _gameDay.value % 7 >= 5  // 简化周末判断
        val isEvening = hour in 18..22
        val body = _bodyState.value
        val age = _playerAge.value

        // 高疲劳时概率加倍
        val fatigueMultiplier = if (body.fatigueLevel > 60) 2.0 else 1.0

        for (event in AtomizedPsychEvents.events) {
            var baseProb = event.dailyProbability * fatigueMultiplier

            // 中年事件仅在中年级触发
            if (event.id == "midlife_meaning_crisis" && age !in 40..55) continue
            // 周末事件仅在周末触发
            if (event.id == "weekend_emptiness" && !isWeekend) continue
            // 晚间事件
            if (event.id in listOf("online_social_void", "work_exhaustion_no_energy") && !isEvening) continue

            if (Math.random() < baseProb) {
                addEventLog(GentleTextProvider.describeAtomizedPsychEvent(event))
                applyExternalPsychImpact(event.impact)
                break  // 每天最多触发一个原子化心理事件
            }
        }
    }

    /**
     * 触发晚年精神叙事
     */
    private fun triggerLateLifeNarrative() {
        _lateLifeNarrativeTriggered = true
        val psych = _psychologicalCore.value
        val narrative = LateLifeNarratives.determineNarrative(psych)

        addEventLog("你已步入晚年。物质大局已定，时代浪潮尘埃落定——是时候回望这一生了。")
        addEventLog(GentleTextProvider.describeLateLifeNarrative(narrative))
        addEventLog(GentleTextProvider.describeLifeReview(psych))
    }

    /**
     * 获取心理内核描述
     */
    fun getPsychologicalSummary(): String {
        return GentleTextProvider.describePsychologicalCore(_psychologicalCore.value)
    }

    /**
     * 心理参数决定行为可行性检查
     */
    fun checkPsychBehaviorConstraint(): PsychBehaviorConstraint? {
        val psych = _psychologicalCore.value
        return when {
            psych.isDeeplyLonely -> PsychBehaviorConstraints.DEEP_LONELINESS_CONSTRAINT
            psych.hasMeaningCrisis -> PsychBehaviorConstraints.NIHILISM_GIVE_UP
            _playerAge.value in 60..100 -> {
                val narrative = LateLifeNarratives.determineNarrative(psych)
                when (narrative) {
                    LateLifeNarrative.PEACE_WITH_ORDINARY, LateLifeNarrative.SPIRITUAL_BEYOND ->
                        PsychBehaviorConstraints.LATE_LIFE_PEACE_NARRATIVE
                    LateLifeNarrative.UNREST_HANDWRITTEN ->
                        PsychBehaviorConstraints.LATE_LIFE_UNREST_NARRATIVE
                }
            }
            else -> null
        }
    }

    // ============================================
    // v2.9 独处逃避行为 集成
    // ============================================

    private val _withdrawalState = MutableStateFlow(WithdrawalState())
    val withdrawalState: StateFlow<WithdrawalState> = _withdrawalState.asStateFlow()

    /** 逃避触发冷却天数 */
    private var _withdrawalCooldown = 0
    /** 恶性循环是否已触发过（防重复） */
    private var _viciousCycleTriggered = false

    /**
     * 每日逃避行为调度
     *
     * 规则：
     * 1. 计算压力指标 = 焦虑×0.4 + 疲劳×0.3 + 迷茫×0.3
     * 2. 压力超阈值 → 按当前逃避模式匹配分年龄段事件
     * 3. 已有逃避状态 → 每日应用后果，累积天数
     * 4. 深度封闭30天以上 → 概率触发独处自洽
     */
    private fun tickWithdrawalDaily() {
        val state = _withdrawalState.value
        val psych = _psychologicalCore.value
        val body = _bodyState.value
        val age = _playerAge.value

        // 冷却处理
        if (_withdrawalCooldown > 0) {
            _withdrawalCooldown -= 1
        }

        // 计算压力指标
        val pressureIndex = (psych.anxiety * 0.4 + body.fatigueLevel * 0.3 + psych.nihilism * 0.3).toInt()

        // 确定年龄组
        val ageGroup = when {
            age in 12..22 -> AgePressureSource.YOUTH
            age in 35..55 -> AgePressureSource.MIDLIFE
            age >= 60 -> AgePressureSource.ELDERLY
            else -> null
        }

        // 已有逃避状态：每日应用后果
        if (state.isWithdrawing) {
            val newDays = state.consecutiveWithdrawalDays + 1
            val consequence = WithdrawalConsequences.dailyConsequence(state.mode, newDays)

            // 应用心理后果
            applyExternalPsychImpact(ExternalPsychImpact(
                source = "逃避行为-${state.mode.label}",
                lonelinessDelta = consequence.lonelinessDelta,
                anxietyDelta = consequence.anxietyDelta,
                identityDelta = consequence.identityDelta,
                nihilismDelta = consequence.nihilismDelta
            ))

            // 更新社交意愿
            val psychNow = _psychologicalCore.value
            _psychologicalCore.value = psychNow.copy(
                socialWill = (psychNow.socialWill + consequence.socialWillDelta).coerceIn(0, 100)
            )

            // 人际关系衰减
            if (Math.random() < consequence.relationshipDecayProbability) {
                addEventLog("由于长期回避社交，部分人际关系正在疏远。")
            }

            // 恶性循环
            if (consequence.triggersViciousCycle && !_viciousCycleTriggered) {
                _viciousCycleTriggered = true
                addEventLog(GentleTextProvider.describeViciousCycle())
                val cycleEffect = WithdrawalConsequences.viciousCycleEffect(state.mode)
                applyExternalPsychImpact(ExternalPsychImpact(
                    source = "恶性循环",
                    lonelinessDelta = cycleEffect.lonelinessDelta,
                    anxietyDelta = cycleEffect.anxietyDelta,
                    identityDelta = cycleEffect.identityDelta,
                    nihilismDelta = cycleEffect.nihilismDelta
                ))
                _withdrawalState.value = state.copy(hasSocialAnxietyLoop = true)
            }

            // 长期深度封闭 → 独处自洽
            if (state.mode == WithdrawalMode.DEEP_WITHDRAWAL && newDays >= 30 && !state.hasFoundSolitudePeace) {
                if (Math.random() < 0.3) {
                    _withdrawalState.value = state.copy(hasFoundSolitudePeace = true)
                    addEventLog(GentleTextProvider.describeSolitudePeace())
                }
            }

            // 短期休整超过3天自动降级为阶段性回避
            val newMode = if (state.mode == WithdrawalMode.SHORT_REST && newDays > 3) {
                addEventLog(GentleTextProvider.describeWithdrawalModeShift(
                    WithdrawalMode.SHORT_REST, WithdrawalMode.PHASED_AVOIDANCE,
                    "短期休整超过3天，状态开始向回避倾斜。"
                ))
                WithdrawalMode.PHASED_AVOIDANCE
            } else state.mode

            _withdrawalState.value = state.copy(
                mode = newMode,
                consecutiveWithdrawalDays = newDays,
                totalWithdrawalDays = state.totalWithdrawalDays + 1
            )
            return
        }

        // 无逃避状态：检测是否触发
        if (_withdrawalCooldown > 0 || ageGroup == null) return

        // 信念韧性充足 → 主动抗压倾向
        if (psych.beliefResilience >= 60) {
            if (Math.random() < 0.5) return  // 50%概率直接选择抗压
        }

        // 匹配触发事件
        val matchingTriggers = WithdrawalTriggers.triggers.filter {
            it.ageGroup == ageGroup && pressureIndex >= it.minPressureThreshold
        }

        if (matchingTriggers.isNotEmpty()) {
            val event = matchingTriggers.random()
            val effectiveProb = when (ageGroup) {
                AgePressureSource.MIDLIFE -> event.baseProbability * 1.3  // 中年高发
                AgePressureSource.YOUTH -> event.baseProbability
                AgePressureSource.ELDERLY -> event.baseProbability * 0.8  // 老年偏低
                else -> event.baseProbability
            }
            // 连续挫折天数加成
            val frustrationBonus = _consecutiveFrustrationDays / 100.0
            val finalProb = (effectiveProb + frustrationBonus).coerceAtMost(0.50)

            if (Math.random() < finalProb) {
                triggerWithdrawal(event)
            }
        }
    }

    /**
     * 触发逃避行为
     */
    private fun triggerWithdrawal(event: WithdrawalTriggerEvent) {
        val oldMode = _withdrawalState.value.mode
        val psych = _psychologicalCore.value
        val body = _bodyState.value

        _withdrawalState.value = WithdrawalState(
            mode = event.targetMode,
            consecutiveWithdrawalDays = 0,
            totalWithdrawalDays = _withdrawalState.value.totalWithdrawalDays,
            isWithdrawing = true,
            triggerSource = event.ageGroup.label,
            triggerSnapshot = "焦虑${psych.anxiety}/疲劳${body.fatigueLevel}/迷茫${psych.nihilism}",
            socialWillDrop = when (event.targetMode) {
                WithdrawalMode.SHORT_REST -> 5
                WithdrawalMode.PHASED_AVOIDANCE -> 15
                WithdrawalMode.DEEP_WITHDRAWAL -> 30
                else -> 0
            },
            hasFoundSolitudePeace = false,
            hasSocialAnxietyLoop = false
        )

        addEventLog(GentleTextProvider.describeWithdrawalTrigger(event))
        if (oldMode != WithdrawalMode.ACTIVE_RESISTANCE && oldMode != event.targetMode) {
            addEventLog(GentleTextProvider.describeWithdrawalModeShift(
                oldMode, event.targetMode, event.description
            ))
        }
        _withdrawalCooldown = (7..30).random()
    }

    /**
     * 玩家手动干预逃避状态
     */
    fun handleWithdrawalChoice(choiceId: String) {
        val state = _withdrawalState.value
        if (!state.isWithdrawing) return

        val choice = WithdrawalPlayerChoices.choices.find { it.id == choiceId } ?: return
        val oldMode = state.mode

        if (choice.breaksCurrentWithdrawal) {
            // 打断逃避
            _withdrawalState.value = state.copy(
                mode = choice.resultMode,
                isWithdrawing = false,
                consecutiveWithdrawalDays = 0,
                hasSocialAnxietyLoop = false
            )
            _viciousCycleTriggered = false
        } else {
            // 调整模式但不打断
            _withdrawalState.value = state.copy(mode = choice.resultMode)
        }

        // 自由干预：缩短冷却
        _withdrawalCooldown = (1..3).random()

        if (oldMode != choice.resultMode) {
            addEventLog(GentleTextProvider.describeWithdrawalModeShift(
                oldMode, choice.resultMode, choice.description
            ))
        }
    }

    // ============================================
    // v2.10 爱情/婚恋/亲密关系 集成
    // ============================================

    private val _loveState = MutableStateFlow(LoveRelationshipState())
    val loveState: StateFlow<LoveRelationshipState> = _loveState.asStateFlow()

    /** 婚恋事件冷却天数 */
    private var _loveCooldown = 0
    /** 当前待处理的婚恋事件 */
    private var _pendingLoveEvent: LoveEvent? = null

    /** 设置社交预算档位 */
    fun setSocialBudgetTier(tier: HobbyBudgetTier) {
        _socialBudgetTier.value = tier
        addEventLog("社交预算已调整为：${tier.label}，相亲候选池规模随之变化。")
    }

    /** 重置每周社交活跃度（周一时调用） */
    fun resetWeeklySocialScore() {
        _weeklySocialScore.value = 0
    }

    /** 记录一次社交行为（团建、聚餐、乐器社群等） */
    fun recordSocialActivity(activityType: String) {
        val current = _weeklySocialScore.value
        val addScore = when (activityType) {
            "team_building" -> 20
            "hobby_community" -> 15
            "friend_dinner" -> 15
            "outing" -> 12
            "online_social" -> 5
            else -> 8
        }
        _weeklySocialScore.value = (current + addScore).coerceAtMost(100)
    }

    /**
     * 计算相亲综合门槛评分（五项指标）
     * 返回 Pair(总评分 0-100, 是否达到最低门槛)
     */
    private fun calculateDatingEligibility(): Pair<Int, Boolean> {
        val age = _playerAge.value
        val space = _spaceState.value
        val mode = _shoppingMode.value
        val socialScore = _weeklySocialScore.value
        val appearance = _appearanceState.value
        val hobbyProf = _hobbyProficiency.value

        // 指标1：生活水平（消费档位） 0-25分
        val livingScore = when (mode) {
            ShoppingMode.SURVIVAL -> if (space.currentSavings > 10000) 8 else 3
            ShoppingMode.BALANCED -> 18
            ShoppingMode.PREMIUM -> 25
        }

        // 指标2：社交活跃度 0-25分
        val socialScoreValue = when {
            socialScore >= 60 -> 25
            socialScore >= 40 -> 18
            socialScore >= 20 -> 10
            socialScore > 0 -> 5
            else -> 0
        }

        // 指标3：个人闪光点（爱好+形象+健康） 0-25分
        val healthScore = _bodyState.value.healthScore.coerceIn(0, 100)
        val appearanceScore = ((appearance.level.socialModifier + 0.15) * 100).toInt().coerceIn(0, 100)
        val highlightScore = (
            (hobbyProf * 0.15).toInt() +
            (appearanceScore * 0.05).toInt() +
            (healthScore * 0.05).toInt()
        ).coerceIn(0, 25)

        // 指标4：存款房产 0-15分
        val assetScore = when {
            space.currentSavings > 100000 -> 15
            space.currentSavings > 50000 -> 12
            space.currentSavings > 20000 -> 8
            space.currentSavings > 5000 -> 4
            else -> 1
        }

        // 指标5：年龄阶段 0-10分
        val ageScore = when {
            age in 22..28 -> 10
            age in 18..21 -> 8
            age in 29..30 -> 7
            age in 31..35 -> 5
            age in 36..38 -> 3
            age in 39..44 -> 1
            else -> 0
        }

        val total = livingScore + socialScoreValue + highlightScore + assetScore + ageScore
        // 最低门槛：总分 >= 35 且社交活跃度 > 0（必须有社交渠道）
        val passesThreshold = total >= 35 && socialScore > 0
        return total to passesThreshold
    }

    /**
     * 每日婚恋调度（v2.11 严格一夫一妻 + 社交门槛控制）
     *
     * 规则：
     * 1. 已婚 → 锁死全部相亲推送，仅触发夫妻/家庭事件
     * 2. 单身 → 按五项指标评估相亲门槛，不达标不推送
     * 3. 社交活跃度为零 → 候选池为空，无相亲渠道
     * 4. 青年(18-30) → 核心区间，恋爱/分手/异地抉择触发概率最高
     * 5. 中年(31-55) → 婚姻矛盾累积、危机触发为主
     * 6. 老年(60+) → 伴侣离世，孤独冲击
     */
    private fun tickLoveDaily() {
        val state = _loveState.value
        val psych = _psychologicalCore.value
        val age = _playerAge.value

        // 冷却处理
        if (_loveCooldown > 0) {
            _loveCooldown -= 1
        }
        // 有待处理事件：等待玩家抉择
        if (_pendingLoveEvent != null) return

        // 异地每日效应
        if (state.isLongDistance && state.isInRelationship) {
            val newDistanceDays = state.longDistanceDays + 1
            val distanceEffect = LoveConsequences.longDistanceEffect(newDistanceDays)
            applyExternalPsychImpact(ExternalPsychImpact(
                source = "异地-${newDistanceDays}天",
                lonelinessDelta = distanceEffect.lonelinessDelta,
                anxietyDelta = distanceEffect.anxietyDelta,
                happinessDelta = distanceEffect.happinessDelta
            ))
            _loveState.value = state.copy(longDistanceDays = newDistanceDays)

            val daysText = listOf(7, 30, 90).find { it == newDistanceDays }
            if (daysText != null) {
                addEventLog(GentleTextProvider.describeLongDistancePain(daysText))
            }
        }

        // 关系中：每日幸福加成/压力累积
        if (state.isInRelationship) {
            val newLoveDays = state.loveDays + 1
            val newMarriageDays = if (state.status == LoveStatus.MARRIED) state.marriageDays + 1 else state.marriageDays

            // 基础幸福加成
            if (Math.random() < 0.05) {
                _psychologicalCore.value = psych.copy(
                    happiness = (psych.happiness + 1).coerceIn(0, 100)
                )
            }

            // 里程碑文案
            val milestoneText = GentleTextProvider.describeLoveDailyEffect(newLoveDays, state.status)
            if (milestoneText.isNotEmpty()) {
                addEventLog(milestoneText)
            }

            _loveState.value = state.copy(
                loveDays = newLoveDays,
                marriageDays = newMarriageDays
            )
        }

        // 婚恋矛盾累积（婚姻状态）
        if (state.status == LoveStatus.MARRIED && state.maritalConflictDays > 0) {
            val conflictEffect = LoveConsequences.maritalConflictEffect(state.maritalConflictDays)
            applyExternalPsychImpact(ExternalPsychImpact(
                source = "婚姻矛盾-${state.maritalConflictDays}天",
                lonelinessDelta = conflictEffect.lonelinessDelta,
                anxietyDelta = conflictEffect.anxietyDelta,
                happinessDelta = conflictEffect.happinessDelta
            ))
            // 矛盾触发逃避
            if (conflictEffect.triggersWithdrawal && Math.random() < conflictEffect.withdrawalProbabilityBonus) {
                _withdrawalCooldown = 0  // 缩短现有冷却，允许触发
            }

            val conflictText = GentleTextProvider.describeMarriageConflict(state.maritalConflictDays)
            if (conflictText.isNotEmpty()) {
                addEventLog(conflictText)
            }
        }

        // 失恋恢复阶段递进
        if (state.hasExperiencedHeartbreak && state.heartbreakRecoveryPhase != HeartbreakPhase.NOT_APPLICABLE
            && state.heartbreakRecoveryPhase != HeartbreakPhase.RECOVERED
            && state.heartbreakRecoveryPhase != HeartbreakPhase.TRANSFORMED) {
            tickHeartbreakRecovery(state)
        }

        // ====== 严格一夫一妻：已婚状态锁死全部相亲推送 ======
        if (state.status == com.example.townapp.data.LoveStatus.MARRIED) {
            // 已婚者只处理夫妻/家庭事件，不触发任何相亲/恋爱新事件
            return
        }

        // 婚恋窗口期判定（根据用户总纲领）
        when {
            age < 18 -> return  // 未成年不触发
            age >= 45 -> {
                // 45岁后永久关闭婚恋系统，终身单身
                return
            }
            age in 39..44 -> {
                // 39-44岁末期机会极少，每年仅一次极低概率检测
                if (_gameDay.value % 360 != 0 || kotlin.random.Random.nextInt(100) >= 5) return
            }
            age in 31..38 -> {
                // 31-38岁晚婚缓冲，条件严苛，概率大幅下降
                if (kotlin.random.Random.nextInt(100) >= 15) return
            }
        }

        // ====== 相亲综合门槛评估（五项指标） ======
        val (eligibilityScore, passesThreshold) = calculateDatingEligibility()

        // 没有社交渠道 = 候选池为空
        if (_weeklySocialScore.value <= 0) {
            // 长期无社交时，每年极低概率触发一次强制社交事件（如公司团建）带来的相亲机会
            if (_gameDay.value % 360 == 0 && kotlin.random.Random.nextInt(100) < 3) {
                addEventLog("公司组织了一次难得的团建，你被迫出了趟门——意外遇到了一个相亲对象。")
            }
            return
        }

        // 综合条件不达标，不推送相亲
        if (!passesThreshold) {
            // 极低生活水平：长期无相亲，但偶尔通过文案提示玩家状态
            if (eligibilityScore < 20 && kotlin.random.Random.nextInt(100) < 5) {
                addEventLog("你过着上班-回家两点一线的日子，几乎没有什么社交。相亲？你连个认识新人的渠道都没有。")
            }
            return
        }

        // 概率检测婚恋事件
        val matchingEvents = LoveEvents.matching(age, state.status, state.triggeredEventIds)
        if (matchingEvents.isEmpty()) return

        // 时代成本联动：通胀越高，婚恋矛盾概率加权
        val inflationBonus = (eraInflation.annualInflationRate - 0.03).coerceIn(0.0, 0.05) * 100
        // 青年阶段核心概率加成（结合综合评分修正）
        val baseAgeBonus = when {
            age in 18..30 -> 1.3   // 青年核心区间（22-30最优）
            age in 31..38 -> 0.6   // 晚婚缓冲，概率折半
            age in 39..44 -> 0.2   // 末期机会极少
            age in 45..55 -> 0.1   // 中老年偏低
            else -> 0.05            // 老年黄昏恋极低概率
        }
        // 综合评分越高，相亲概率越高（封顶 1.5x）
        val eligibilityMultiplier = 0.7 + (eligibilityScore / 100.0) * 0.8
        val ageGroupBonus = baseAgeBonus * eligibilityMultiplier

        val event = matchingEvents.random()
        // 外貌修正：颜值影响恋爱/社交事件触发概率
        val appearanceMod = _appearanceState.value.effectiveLoveModifier()
        val finalProb = (event.baseProbability * ageGroupBonus + appearanceMod + inflationBonus / 100.0).coerceIn(0.01, 0.30)

        if (Math.random() < finalProb) {
            _pendingLoveEvent = event
            addEventLog(GentleTextProvider.describeLoveEvent(event))
        }
    }

    /**
     * 处理婚恋事件中的玩家抉择
     */
    fun handleLoveChoice(choiceId: String) {
        _playerManuallyDatedThisWeek = true
        val event = _pendingLoveEvent ?: return
        val choice = event.choices.find { it.id == choiceId } ?: return
        val oldStatus = _loveState.value.status

        // 应用心理冲击
        val consequence = LoveConsequences.initialImpact(event.scenario)
        applyExternalPsychImpact(ExternalPsychImpact(
            source = "婚恋-${event.scenario.label}",
            lonelinessDelta = choice.lonelinessDelta,
            anxietyDelta = choice.anxietyDelta,
            happinessDelta = choice.happinessDelta
        ))

        // 更新心理状态
        val psych = _psychologicalCore.value
        _psychologicalCore.value = psych.copy(
            beliefResilience = (psych.beliefResilience + choice.resilienceDelta).coerceIn(0, 100),
            selfIdentity = (psych.selfIdentity + choice.identityDelta).coerceIn(0, 100),
            socialWill = (psych.socialWill + if (consequence.triggersWithdrawal) consequence.socialWillDelta else 0).coerceIn(0, 100)
        )

        // 更新关系状态
        val state = _loveState.value
        _loveState.value = state.copy(
            status = choice.resultStatus,
            relationshipStress = (state.relationshipStress + choice.stressDelta).coerceIn(0, 100),
            hasCareerImpact = choice.careerImpact.isNotEmpty(),
            hasAbandonedIdeal = choice.causesIdealAbandonment,
            isLongDistance = choice.id.endsWith("long_distance"),
            hasExperiencedHeartbreak = choice.resultStatus == LoveStatus.SINGLE && oldStatus == LoveStatus.IN_LOVE,
            heartbreakRecoveryPhase = if (choice.resultStatus == LoveStatus.SINGLE && oldStatus == LoveStatus.IN_LOVE)
                HeartbreakPhase.SHOCK else HeartbreakPhase.NOT_APPLICABLE,
            maritalConflictDays = if (choice.id == "avoid_conflict") state.maritalConflictDays + 1
                else if (choice.id == "work_on_it") 0 else state.maritalConflictDays,
            triggeredEventIds = state.triggeredEventIds + event.id
        )

        // 城市迁居
        if (choice.triggersCityChange && choice.cityTierChange != 0) {
            val space = _spaceState.value
            val newTier = (space.cityTier + choice.cityTierChange).coerceIn(1, 4)
            val tierRentMultiplier = mapOf(1 to 0.2, 2 to 1.0, 3 to 2.5, 4 to 5.0)
            _spaceState.value = space.copy(
                cityTier = newTier,
                monthlyRent = (2000.0 * (tierRentMultiplier[newTier] ?: 1.0)),
                monthlyIncome = if (newTier > 2) space.monthlyIncome * 1.5 else space.monthlyIncome
            )
            addEventLog("你搬到了${when(newTier){1->"小城市";2->"中型城市";3->"大城市";else->"超大城市"}}。房租和收入都变了——生活方式也变了。")
        }

        // 失恋→逃避联动
        if (consequence.triggersWithdrawal && choice.resultStatus == LoveStatus.SINGLE) {
            _withdrawalCooldown = 0  // 打破冷却，允许立即触发逃避
        }

        // 日志
        addEventLog(GentleTextProvider.describeLoveChoiceResult(choice))
        if (oldStatus != choice.resultStatus) {
            addEventLog(GentleTextProvider.describeLoveStatusTransition(oldStatus, choice.resultStatus))
        }

        _pendingLoveEvent = null
        _loveCooldown = (30..90).random()
    }

    /**
     * 失恋恢复阶段每日递进
     */
    private fun tickHeartbreakRecovery(state: LoveRelationshipState) {
        val psych = _psychologicalCore.value
        val currentPhase = state.heartbreakRecoveryPhase
        val daysSinceHeartbreak = state.triggeredEventIds.size  // 简化为事件计数作为时间代理

        val nextPhase = when {
            currentPhase == HeartbreakPhase.SHOCK && daysSinceHeartbreak >= 3 -> HeartbreakPhase.GRIEVING
            currentPhase == HeartbreakPhase.GRIEVING && daysSinceHeartbreak >= 14 -> HeartbreakPhase.ADJUSTING
            currentPhase == HeartbreakPhase.ADJUSTING && daysSinceHeartbreak >= 30 -> {
                // 信念韧性高 → 可能蜕变
                if (psych.beliefResilience >= 70 && Math.random() < 0.4) HeartbreakPhase.TRANSFORMED
                else HeartbreakPhase.RECOVERED
            }
            else -> null
        }

        if (nextPhase != null) {
            _loveState.value = state.copy(heartbreakRecoveryPhase = nextPhase)
            addEventLog(GentleTextProvider.describeHeartbreakRecovery(nextPhase))

            // 蜕变：大幅提升信念韧性
            if (nextPhase == HeartbreakPhase.TRANSFORMED) {
                _psychologicalCore.value = psych.copy(
                    beliefResilience = (psych.beliefResilience + 15).coerceIn(0, 100),
                    selfIdentity = (psych.selfIdentity + 10).coerceIn(0, 100)
                )
            }
        }
    }

    /**
     * 主动触发婚恋事件（青年阶段起点：进入恋爱）
     */
    fun startLoveRelationship() {
        _playerManuallyDatedThisWeek = true
        val state = _loveState.value
        if (state.isInRelationship) return
        if (_playerAge.value < 18 || _playerAge.value > 55) return

        _loveState.value = state.copy(
            status = LoveStatus.IN_LOVE,
            loveDays = 0,
            partnerDescription = "一个让你心跳加速的人",
            hasExperiencedHeartbreak = false,
            heartbreakRecoveryPhase = HeartbreakPhase.NOT_APPLICABLE
        )
        addEventLog(GentleTextProvider.describeLoveStatusTransition(LoveStatus.SINGLE, LoveStatus.IN_LOVE))
        _loveCooldown = (60..120).random()
    }

    /** 获取当前待处理婚恋事件 */
    fun getPendingLoveEvent(): LoveEvent? = _pendingLoveEvent

    // ============================================
    // v2.11 外貌颜值先天参数 集成
    // ============================================

    private val _appearanceState = MutableStateFlow(AppearanceState())
    val appearanceState: StateFlow<AppearanceState> = _appearanceState.asStateFlow()

    /** 已触发的外貌心理事件ID */
    private val _triggeredAppearanceEventIds = mutableListOf<String>()

    /**
     * 初始化外貌（角色创建时调用，应在 initClassBehavior 之后）
     *
     * @param background 阶层出身（富裕家庭高颜值概率略高）
     */
    fun initAppearance(background: ClassBackground) {
        val level = AppearanceInitializer.roll(background)
        _appearanceState.value = AppearanceState(level = level, currentAge = _playerAge.value)

        // 应用外貌对初始心理参数的影响
        val psych = _psychologicalCore.value
        _psychologicalCore.value = psych.copy(
            selfIdentity = (psych.selfIdentity + level.selfIdentityBase).coerceIn(0, 100),
            anxiety = (psych.anxiety + level.anxietyBase).coerceIn(0, 100),
            empathy = if (level.tier >= 4) (psych.empathy - 5).coerceIn(0, 100) else psych.empathy
        )

        addEventLog(GentleTextProvider.describeAppearanceInit(level, background))
    }

    /**
     * 每日外貌系统调度
     *
     * 处理：
     * 1. 年龄衰减提示（35/45岁）
     * 2. 高颜值骚扰/流言事件
     * 3. 外貌相关心理事件
     * 4. 容貌焦虑触发
     */
    private fun tickAppearanceDaily() {
        val state = _appearanceState.value
        val age = _playerAge.value

        // 年龄同步
        if (state.currentAge != age) {
            _appearanceState.value = state.copy(currentAge = age)
        }

        val updatedState = _appearanceState.value

        // 年龄衰减提示（高颜值）
        if (updatedState.level.tier >= 4) {
            val decayText = GentleTextProvider.describeAppearanceAgeDecay(updatedState.level, age)
            if (decayText.isNotEmpty()) {
                addEventLog(decayText)
            }
        }

        // 骚扰/流言风险（亮眼/出众档）
        if (updatedState.level.harassmentRisk > 0 && Math.random() < updatedState.level.harassmentRisk) {
            val newCount = updatedState.harassmentCount + 1
            _appearanceState.value = updatedState.copy(harassmentCount = newCount)

            val harassmentText = GentleTextProvider.describeAppearanceHarassment(newCount)
            if (harassmentText.isNotEmpty()) {
                addEventLog(harassmentText)
            }

            // 骚扰→焦虑+逃避倾向
            applyExternalPsychImpact(ExternalPsychImpact(
                source = "外貌骚扰",
                anxietyDelta = 3, happinessDelta = -2
            ))
            // 持续骚扰→可能触发社交回避
            if (newCount >= 3 && Math.random() < 0.15) {
                _withdrawalCooldown = 0
            }
        }

        // 容貌焦虑（高颜值30岁后）
        if (updatedState.isAppearanceAnxietyRisk && Math.random() < 0.03) {
            _appearanceState.value = updatedState.copy(hasAppearanceAnxiety = true)
            applyExternalPsychImpact(ExternalPsychImpact(
                source = "容貌焦虑",
                anxietyDelta = 5, identityDelta = -4
            ))
        }

        // 外貌相关随机心理事件
        val matchingPsychEvents = AppearancePsychEvents.matching(
            updatedState.level, age, _triggeredAppearanceEventIds
        )
        if (matchingPsychEvents.isNotEmpty() && Math.random() < 0.04) {
            val event = matchingPsychEvents.random()
            _triggeredAppearanceEventIds.add(event.id)

            applyExternalPsychImpact(ExternalPsychImpact(
                source = "外貌-${event.title}",
                lonelinessDelta = event.lonelinessDelta,
                anxietyDelta = event.anxietyDelta,
                identityDelta = event.identityDelta,
                resilienceDelta = event.resilienceDelta
            ))

            addEventLog(GentleTextProvider.describeAppearancePsychEvent(event))

            // "选择深耕"事件 → 标记状态
            if (event.id == "appearance_substance_choice") {
                _appearanceState.value = updatedState.copy(hasChosenSubstanceOverLooks = true)
                // 能力深耕→信念韧性大幅提升
                val psych = _psychologicalCore.value
                _psychologicalCore.value = psych.copy(
                    beliefResilience = (psych.beliefResilience + 10).coerceIn(0, 100),
                    selfIdentity = (psych.selfIdentity + 8).coerceIn(0, 100)
                )
                addEventLog(GentleTextProvider.describeSubstanceOverLooks())
            }
        }
    }

    /**
     * 获取外貌对婚恋事件的概率修正
     */
    fun getAppearanceLoveModifier(): Double {
        return _appearanceState.value.effectiveLoveModifier()
    }

    /**
     * 获取外貌对初次社交的印象修正
     */
    fun getAppearanceSocialModifier(): Double {
        val state = _appearanceState.value
        // 时代环境调节
        val eraMult = AppearanceEraModifier.getEraMultiplier(
            _currentYear.value,
            unemploymentRate = if (_currentYear.value in 2028..2032) 0.12 else 0.05,
            inflationRate = eraInflation.annualInflationRate
        )
        return state.effectiveSocialModifier() * eraMult
    }

    /**
     * 获取外貌对指定职业类型的面试修正
     */
    fun getAppearanceCareerBonus(careerType: String): Double {
        val state = _appearanceState.value
        val eraMult = AppearanceEraModifier.getEraMultiplier(
            _currentYear.value,
            unemploymentRate = if (_currentYear.value in 2028..2032) 0.12 else 0.05,
            inflationRate = eraInflation.annualInflationRate
        )
        return AppearanceCareerImpact.getInterviewBonus(state.level, careerType) * eraMult
    }

    // ============================================
    // v2.12 服饰穿戴 + 童年创伤 集成
    // ============================================

    private val _clothingWearState = MutableStateFlow(ClothingWearState())
    val clothingWearState: StateFlow<ClothingWearState> = _clothingWearState.asStateFlow()

    private val _childhoodTraumaState = MutableStateFlow(ChildhoodTraumaState())
    val childhoodTraumaState: StateFlow<ChildhoodTraumaState> = _childhoodTraumaState.asStateFlow()

    private var _lastClothingHealthEventDay = 0
    private var _lastTraumaDrainDay = 0
    /** 自我对话是否已呈现（等待玩家选择） */
    private var _pendingPastSelfDialogue: PastSelfDialogueEvent? = null

    // ============================================
    // v2.13 性别系统 + 材质成分系统 集成
    // ============================================

    private val _genderState = MutableStateFlow(GenderRuntimeState.forSex(BiologicalSex.MALE))
    val genderState: StateFlow<GenderRuntimeState> = _genderState.asStateFlow()

    /** 各穿戴位当前面料 */
    private val _slotFabrics = MutableStateFlow<Map<WearSlot, FabricType>>(mapOf(
        WearSlot.FEET to FabricType.COTTON,
        WearSlot.UPPER_BODY to FabricType.COTTON,
        WearSlot.LOWER_BODY to FabricType.COTTON,
        WearSlot.OUTERWEAR to FabricType.COTTON
    ))
    val slotFabrics: StateFlow<Map<WearSlot, FabricType>> = _slotFabrics.asStateFlow()

    /** 当前洗涤剂 */
    private val _currentDetergent = MutableStateFlow(DetergentType.SOAP_BASED)
    val currentDetergent: StateFlow<DetergentType> = _currentDetergent.asStateFlow()

    /** 拥有哪些日用品 */
    private val _ownedHouseholdProducts = MutableStateFlow<Set<HouseholdProductType>>(emptySet())
    val ownedHouseholdProducts: StateFlow<Set<HouseholdProductType>> = _ownedHouseholdProducts.asStateFlow()

    /** 各药品品类累计耐药性 */
    private val _drugResistance = MutableStateFlow<Map<DrugCategory, Double>>(mapOf(
        DrugCategory.ANTIBIOTIC to 0.0
    ))
    val drugResistance: StateFlow<Map<DrugCategory, Double>> = _drugResistance.asStateFlow()

    private var _lastGenderEventDay = 0

    /**
     * 初始化性别系统（角色创建时调用）
     */
    fun initGender(sex: BiologicalSex) {
        _genderState.value = GenderRuntimeState.forSex(sex)
    }

    /**
     * 初始化材质成分系统（角色创建时调用，根据家境分配初始面料）
     */
    fun initMaterialSystems(background: ClassBackground) {
        // 富裕出身 → 可选羊毛/纯棉；底层 → 化纤/纯棉为主
        val defaultFabric = if (background == ClassBackground.AFFLUENT) FabricType.COTTON else FabricType.POLYESTER
        _slotFabrics.value = mapOf(
            WearSlot.FEET to if (background == ClassBackground.AFFLUENT) FabricType.COTTON else FabricType.POLYESTER,
            WearSlot.UPPER_BODY to defaultFabric,
            WearSlot.LOWER_BODY to defaultFabric,
            WearSlot.OUTERWEAR to if (background == ClassBackground.AFFLUENT) FabricType.WOOL else FabricType.POLYESTER
        )
        _currentDetergent.value = if (background == ClassBackground.AFFLUENT)
            DetergentType.MILD_DETERGENT else DetergentType.SOAP_BASED
        _ownedHouseholdProducts.value = if (background == ClassBackground.AFFLUENT)
            setOf(HouseholdProductType.SOAP) else emptySet()
    }

    /**
     * 初始化穿戴系统（角色创建时调用）
     */
    fun initClothingWear(background: ClassBackground) {
        val quality = if (background == ClassBackground.AFFLUENT)
            ClothingQuality.NEAT else ClothingQuality.PLAIN
        val slots = mapOf(
            WearSlot.FEET to WearSlotState(
                WearSlot.FEET,
                breathability = if (background == ClassBackground.AFFLUENT) ShoeBreathability.GOOD else ShoeBreathability.MODERATE,
                quality = quality,
                cleanliness = ClothingCleanliness.CLEAN
            ),
            WearSlot.LOWER_BODY to WearSlotState(WearSlot.LOWER_BODY, quality = quality, cleanliness = ClothingCleanliness.CLEAN),
            WearSlot.UPPER_BODY to WearSlotState(WearSlot.UPPER_BODY, quality = quality, cleanliness = ClothingCleanliness.CLEAN),
            WearSlot.OUTERWEAR to WearSlotState(WearSlot.OUTERWEAR, quality = quality, cleanliness = ClothingCleanliness.CLEAN)
        )
        _clothingWearState.value = ClothingWearState(
            slots = slots,
            overallQuality = quality,
            overallCleanliness = ClothingCleanliness.CLEAN
        )
    }

    // ============================================
    // v2.0 医疗健康系统集成
    // ============================================

    private val _medicalState = MutableStateFlow(MedicalRuntimeState())
    val medicalState: StateFlow<MedicalRuntimeState> = _medicalState.asStateFlow()

    private var _lastMedicalTickDay = 0

    /**
     * 初始化医疗系统（角色创建时调用）
     */
    fun initMedicalSystem(nutritionLevel: NutritionLevel) {
        val body = _bodyState.value
        val baseConstitution = 60 + nutritionLevel.constitutionModifier + (body.healthScore - 50) / 5
        _medicalState.value = MedicalRuntimeState(
            constitutionScore = baseConstitution.coerceIn(20, 95),
            defaultTreatmentRoute = TreatmentRoute.NATURAL_RECOVERY
        )
    }

    /**
     * 获取体质分数（含疾病后遗症惩罚）
     */
    fun constitutionScore(): Int {
        val base = _medicalState.value.constitutionScore
        val sequelaPenalty = _medicalState.value.permanentSequelae.size * 5
        return (base - sequelaPenalty).coerceAtLeast(10)
    }

    /**
     * 初始化童年创伤系统（角色创建时调用）
     *
     * 根据家境自动生成创伤条目，青年阶段前参数隐藏
     */
    fun initChildhoodTrauma(background: ClassBackground) {
        val traumas = TraumaGenerator.generate(background)
        val severity = TraumaGenerator.totalUnhealedSeverity(traumas)
        _childhoodTraumaState.value = ChildhoodTraumaState(
            traumas = traumas,
            totalUnhealedSeverity = severity
        )
        if (traumas.isNotEmpty()) {
            addEventLog(GentleTextProvider.describeChildhoodSummary(traumas))
        }
    }

    /**
     * 每日穿戴系统调度
     *
     * 处理：
     * 1. 鞋袜潮湿累积 + 脚气风险
     * 2. 衣物温度适配检测
     * 3. 洁净度退化 + 皮肤风险
     * 4. 穿戴健康事件触发
     * 5. 社交心理事件触发
     */
    private fun tickClothingDaily() {
        val state = _clothingWearState.value
        val body = _bodyState.value
        val gameDay = _gameDay.value

        // ======== 1. 洁净度退化 ========
        var updatedSlots = state.slots.mapValues { (_, slot) ->
            val newDaysWash = slot.daysSinceLastWash + 1
            val newCleanliness = when {
                newDaysWash >= 4 -> ClothingCleanliness.FILTHY
                newDaysWash >= 2 -> ClothingCleanliness.DIRTY
                else -> slot.cleanliness
            }
            slot.copy(daysSinceLastWash = newDaysWash, cleanliness = newCleanliness)
        }

        // ======== 2. 鞋袜潮湿累积 + 脚气风险 ========
        val feetSlot = updatedSlots[WearSlot.FEET]!!
        val isRainy = Math.random() < 0.15  // 简化雨天判断
        val temp = 15.0 + 15 * Math.sin(_gameDay.value.toDouble() / 365 * 2 * Math.PI)  // 模拟季节温度

        val dampnessRisk = WeatherClothingLink.calculateDampnessRisk(temp, 0.5, isRainy)
        val newDampness = (feetSlot.dampness + dampnessRisk / 5).coerceIn(0, 100)
        val newConsecutiveDays = feetSlot.consecutiveWearDays + 1

        // 脚气风险计算
        val breathabilityFactor = feetSlot.breathability.moistureRetention
        val newFungusRisk = (state.footFungusRisk +
            (newDampness * breathabilityFactor / 10).toInt() +
            (newConsecutiveDays / 3).coerceAtMost(10)).coerceIn(0, 100)

        updatedSlots = updatedSlots + (WearSlot.FEET to feetSlot.copy(
            dampness = newDampness, consecutiveWearDays = newConsecutiveDays
        ))

        // ======== 3. 衣物温度适配检测 ========
        val upperSlot = updatedSlots[WearSlot.UPPER_BODY]!!
        val appropriate = WeatherClothingLink.isClothingAppropriate(upperSlot.warmth, temp)
        var tempFatigueBonus = 0

        if (!appropriate) {
            val recommended = WeatherClothingLink.recommendWarmth(temp)
            if (upperSlot.warmth.warmthValue < recommended.warmthValue) {
                // 穿少了
                tempFatigueBonus = 3
                if (Math.random() < 0.1) {
                    addEventLog(GentleTextProvider.describeClothingHealthEvent(
                        ClothingHealthEvents.events.first { it.id == "thin_clothes_cold" }
                    ))
                }
            } else {
                // 穿多了
                tempFatigueBonus = 2
                if (Math.random() < 0.08) {
                    addEventLog(GentleTextProvider.describeClothingHealthEvent(
                        ClothingHealthEvents.events.first { it.id == "heavy_clothes_heat" }
                    ))
                }
            }
        }

        // ======== 4. 健康事件触发检测 ========
        val newState = state.copy(
            slots = updatedSlots,
            footFungusRisk = newFungusRisk,
            hoursSinceLastLaundry = state.hoursSinceLastLaundry + 24
        )

        // 脚气爆发检测
        val newFootCondition = when {
            newFungusRisk > 80 && state.footCondition.severity < 3 -> {
                addEventLog(GentleTextProvider.describeFootCondition(FootCondition.SEVERE_FUNGUS))
                FootCondition.SEVERE_FUNGUS
            }
            newFungusRisk > 50 && state.footCondition.severity < 2 -> {
                addEventLog(GentleTextProvider.describeFootCondition(FootCondition.MODERATE_FUNGUS))
                FootCondition.MODERATE_FUNGUS
            }
            newFungusRisk > 30 && state.footCondition.severity < 1 -> {
                addEventLog(GentleTextProvider.describeFootCondition(FootCondition.MILD_FUNGUS))
                FootCondition.MILD_FUNGUS
            }
            else -> state.footCondition
        }

        // 脚气→疲劳值
        if (newFootCondition != FootCondition.HEALTHY) {
            _bodyState.value = body.copy(
                fatigueLevel = (body.fatigueLevel + newFootCondition.fatigueBonus).coerceIn(0, 100)
            )
        }

        // 皮肤问题检测
        val overallClean = updatedSlots.values.minOf { it.cleanliness.tier }
        val skinRiskIncrease = when (overallClean) {
            1 -> 15
            2 -> 5
            else -> 0
        }
        val newSkinRisk = (state.skinIssueRisk + skinRiskIncrease).coerceIn(0, 100)
        val newSkin = when {
            newSkinRisk > 60 && state.skinCondition != SkinCondition.MODERATE_DERMATITIS -> {
                addEventLog(GentleTextProvider.describeClothingHealthEvent(
                    ClothingHealthEvents.events.first { it.id == "dirty_clothes_skin" }
                ))
                SkinCondition.MODERATE_DERMATITIS
            }
            newSkinRisk > 25 && state.skinCondition == SkinCondition.HEALTHY -> SkinCondition.MILD_ACNE
            else -> state.skinCondition
        }

        // ======== 5. 洗衣提醒 ========
        val laundryText = GentleTextProvider.describeLaundryReminder(newState.hoursSinceLastLaundry)
        if (laundryText.isNotEmpty() && _gameDay.value % 3 == 0 && Math.random() < 0.3) {
            addEventLog(laundryText)
        }

        // ======== 6. 社交心理事件 ========
        if (_gameDay.value % 5 == 0 && Math.random() < 0.06) {
            val socialEvents = ClothingSocialEvents.events
            val relevantEvent = when {
                newState.overallQuality.tier >= 4 && !newState.hasLuxuryAnxiety && Math.random() < 0.4 ->
                    socialEvents.find { it.id == "luxury_anxiety" }
                newState.overallQuality.tier <= 1 && !newState.hasExperiencedClothingPrejudice && Math.random() < 0.5 ->
                    socialEvents.find { it.id == "shabby_prejudice" }
                newState.overallCleanliness.tier >= 4 && Math.random() < 0.3 ->
                    socialEvents.find { it.id == "neat_impression" }
                newState.hasChosenSimplicity && Math.random() < 0.2 ->
                    socialEvents.find { it.id == "simplicity_peace" }
                else -> null
            }
            if (relevantEvent != null) {
                addEventLog(GentleTextProvider.describeClothingSocialEvent(relevantEvent))
                applyExternalPsychImpact(ExternalPsychImpact(
                    source = "穿戴-${relevantEvent.title}",
                    anxietyDelta = relevantEvent.anxietyDelta,
                    identityDelta = relevantEvent.identityDelta
                ))
                if (relevantEvent.id == "luxury_anxiety") {
                    _clothingWearState.value = newState.copy(hasLuxuryAnxiety = true)
                }
                if (relevantEvent.id == "simplicity_peace") {
                    _clothingWearState.value = newState.copy(hasChosenSimplicity = true)
                }
                if (relevantEvent.id == "shabby_prejudice") {
                    _clothingWearState.value = newState.copy(hasExperiencedClothingPrejudice = true)
                }
            }
        }

        // ======== 7. 更新状态 ========
        _clothingWearState.value = newState.copy(
            footCondition = newFootCondition,
            skinCondition = newSkin,
            skinIssueRisk = newSkinRisk,
            overallCleanliness = updatedSlots.values.minOf { it.cleanliness }.let { min ->
                ClothingCleanliness.entries.find { it.tier == min.tier } ?: ClothingCleanliness.NORMAL
            },
            overallQuality = updatedSlots.values.map { it.quality }.let { qualities ->
                val maxTier = qualities.maxOf { it.tier }
                ClothingQuality.entries.find { it.tier == maxTier } ?: ClothingQuality.PLAIN
            }
        )
    }

    /**
     * 每日童年创伤调度
     *
     * 处理：
     * 1. 未治愈创伤每日消耗心理参数（28岁前静默消耗）
     * 2. 28-35岁触发与十年前自我对话事件
     */
    private fun tickChildhoodTraumaDaily() {
        val traumaState = _childhoodTraumaState.value
        val age = _playerAge.value

        // 无创伤 → 跳过
        if (traumaState.traumas.isEmpty()) return

        // 自我对话进行中 → 等待玩家选择
        if (_pendingPastSelfDialogue != null) return

        // ======== 每日心理消耗 ========
        val drain = traumaState.dailyPsychDrain()
        if (drain.anxietyDelta != 0 || drain.identityDelta != 0) {
            applyExternalPsychImpact(ExternalPsychImpact(
                source = "童年创伤消耗",
                anxietyDelta = drain.anxietyDelta,
                identityDelta = drain.identityDelta,
                lonelinessDelta = drain.lonelinessDelta
            ))
            // 每周展示一次消耗描述
            if (_gameDay.value % 7 == 0 && drain.description.isNotEmpty() && age >= 18) {
                addEventLog(GentleTextProvider.describePsychDrain(drain))
            }
        }

        // ======== 中年自我对话触发（28-35岁，仅触发一次） ========
        if (age in 28..35 &&
            !traumaState.pastSelfDialogueTriggered &&
            !traumaState.hasAvoidedDialogue &&
            traumaState.totalUnhealedSeverity > 10) {

            val pastAge = age - 10
            val unhealed = traumaState.traumas.filter { !it.isHealed }
            val summary = unhealed.joinToString("、") { it.type.label }

            val dialogueEvent = PastSelfDialogueEvent(
                pastSelfAge = pastAge,
                currentAge = age,
                yearsGap = 10,
                traumasSummary = summary,
                introNarrative = ""
            )

            _pendingPastSelfDialogue = dialogueEvent
            addEventLog(GentleTextProvider.describePastSelfDialogueIntro(dialogueEvent))
        }
    }

    /**
     * 每日性别生理调度
     *
     * 处理：
     * 1. 生理期月度周期推进（女性专属）
     * 2. 孕产状态推进
     * 3. 痛经对体能/工作效率的影响
     * 4. 情绪压抑累积（男性专属）
     * 5. 性别随机事件触发
     */
    private fun tickGenderDaily() {
        val state = _genderState.value
        val body = _bodyState.value
        val psych = _psychologicalCore.value
        val age = _playerAge.value

        // ======== 1. 生理期周期（女性，青春期开始） ========
        if (state.menstrualCycle != null && age >= 13) {
            val newCycle = MenstrualCycleSystem.tick(state.menstrualCycle)

            // 痛经对体能的影响
            if (newCycle.isMenstruating) {
                val staminaPenalty = newCycle.staminaPenalty()
                if (staminaPenalty > 0) {
                    _bodyState.value = body.copy(
                        fatigueLevel = (body.fatigueLevel + staminaPenalty).coerceIn(0, 100)
                    )
                }
                // 重度痛经 → 工作效率下降 + 逃离倾向
                if (newCycle.painLevel == PeriodPainLevel.SEVERE && _gameDay.value % 3 == 0) {
                    applyExternalPsychImpact(ExternalPsychImpact(
                        source = "生理期-重度痛经",
                        anxietyDelta = newCycle.anxietyBonus(),
                        lonelinessDelta = (newCycle.withdrawalBonus() * 100).toInt()
                    ))
                }
                // 痛经事件
                if (Math.random() < 0.08 && _gameDay.value - _lastGenderEventDay > 7) {
                    val event = when (newCycle.painLevel) {
                        PeriodPainLevel.SEVERE ->
                            MenstrualEvents.events.first { it.id == "severe_cramps" }
                        PeriodPainLevel.MODERATE ->
                            MenstrualEvents.events.first { it.id == "mild_cramps" }
                        else -> MenstrualEvents.events.first { it.id == "period_fatigue" }
                    }
                    addEventLog(GentleTextProvider.describeMenstrualEvent(event))
                    _lastGenderEventDay = _gameDay.value
                }
            }

            _genderState.value = state.copy(menstrualCycle = newCycle)
        }

        // ======== 2. 孕产状态推进（女性） ========
        if (state.childbirth.phase != ChildbirthPhase.NONE &&
            state.childbirth.phase != ChildbirthPhase.RECOVERED) {
            val newChildbirth = ChildbirthSystem.tick(state.childbirth)

            // 孕期/产后体能折损
            val reduction = newChildbirth.staminaReduction()
            if (reduction > 0 && _gameDay.value % 3 == 0) {
                _bodyState.value = body.copy(
                    fatigueLevel = (body.fatigueLevel + (reduction * 10).toInt()).coerceIn(0, 100)
                )
            }

            _genderState.value = state.copy(childbirth = newChildbirth)
        }

        // ======== 3. 男性情绪压抑累积 ========
        if (state.sex == BiologicalSex.MALE) {
            val tendency = state.psychTendency
            // 孤独值高时更容易压抑情绪
            if (psych.loneliness > 50 && Math.random() < 0.05) {
                val newSuppression = state.accumulatedSuppression + 2
                _genderState.value = state.copy(accumulatedSuppression = newSuppression)
                // 情绪压抑超过阈值 → 深度迷茫事件
                if (newSuppression >= tendency.deepConfusionThreshold && Math.random() < 0.03) {
                    val event = GenderCareerEvents.events.first { it.id == "emotional_suppression" }
                    addEventLog(GentleTextProvider.describeGenderCareerEvent(event))
                    applyExternalPsychImpact(ExternalPsychImpact(
                        source = "性别-情绪压抑",
                        anxietyDelta = event.anxietyDelta,
                        identityDelta = event.identityDelta
                    ))
                    _genderState.value = _genderState.value.copy(accumulatedSuppression = 0)
                }
            }
        }

        // ======== 4. 婚恋性别事件（成年阶段） ========
        if (age >= 22 && _gameDay.value % 15 == 0 && Math.random() < 0.04) {
            val prob = CourtshipProbability.forEra(state.eraNorm)
            val eventKey = if (state.sex == BiologicalSex.FEMALE && Math.random() < prob.femaleHarassmentRate) {
                "harassment_encounter"
            } else if (state.sex == BiologicalSex.MALE && Math.random() < prob.maleFinancialPressureRate) {
                "dowry_pressure"
            } else null

            if (eventKey != null) {
                val event = GenderCourtshipEvents.events.first { it.id == eventKey }
                addEventLog(GentleTextProvider.describeGenderCourtshipEvent(event))
                applyExternalPsychImpact(ExternalPsychImpact(
                    source = "性别-${event.title}",
                    anxietyDelta = event.anxietyDelta,
                    identityDelta = event.identityDelta
                ))
            }
        }
    }

    /**
     * 每日材质-成分 健康联动调度
     *
     * 处理：
     * 1. 面料成分对脚气风险的修正
     * 2. 面料成分对受寒/中暑的修正
     * 3. 洗涤剂对皮肤脱皮的影响
     * 4. 日用品对健康的防护
     */
    private fun tickMaterialDaily() {
        val clothing = _clothingWearState.value
        val fabrics = _slotFabrics.value
        val detergent = _currentDetergent.value
        val products = _ownedHouseholdProducts.value
        val temp = 15.0 + 15 * Math.sin(_gameDay.value.toDouble() / 365 * 2 * Math.PI)
        val humidity = 0.5 + 0.3 * Math.sin(_gameDay.value.toDouble() / 365 * 2 * Math.PI)

        // ======== 1. 鞋袜面料 对脚气风险的修正 ========
        val feetFabric = fabrics[WearSlot.FEET] ?: FabricType.COTTON
        val fabricFungusModifier = FabricHealthLink.footFungusRiskModifier(feetFabric, temp, humidity)
        if (fabricFungusModifier > 5) {
            // 面料加重了脚气风险
            val currentRisk = clothing.footFungusRisk
            _clothingWearState.value = clothing.copy(
                footFungusRisk = (currentRisk + fabricFungusModifier / 3).coerceIn(0, 100)
            )
        }

        // ======== 2. 上装面料 对受寒/中暑的修正 ========
        val upperFabric = fabrics[WearSlot.UPPER_BODY] ?: FabricType.COTTON
        // 受寒风险
        if (temp < 15 && Math.random() < FabricHealthLink.coldRiskModifier(upperFabric, temp)) {
            addEventLog(GentleTextProvider.describeFabricColdRisk(upperFabric, temp.toInt()))
            _bodyState.value = _bodyState.value.copy(
                fatigueLevel = (_bodyState.value.fatigueLevel + 3).coerceIn(0, 100)
            )
        }
        // 酷暑疲劳
        val heatFatigue = FabricHealthLink.heatFatigueModifier(upperFabric, temp)
        if (heatFatigue > 0 && _gameDay.value % 2 == 0) {
            _bodyState.value = _bodyState.value.copy(
                fatigueLevel = (_bodyState.value.fatigueLevel + heatFatigue).coerceIn(0, 100)
            )
        }

        // ======== 3. 洗涤剂 对手部皮肤的影响 ========
        val laundryFreq = (3 - clothing.overallCleanliness.tier).coerceAtLeast(1)
        val peelingRisk = DetergentSkinLink.handPeelingRisk(detergent, 0.4, laundryFreq)
        if (peelingRisk > 0.02 && Math.random() < peelingRisk && _gameDay.value % 7 == 0) {
            addEventLog(GentleTextProvider.describeDetergentSkinIssue(detergent))
            if (clothing.skinCondition == SkinCondition.HEALTHY) {
                _clothingWearState.value = clothing.copy(
                    skinCondition = SkinCondition.HAND_PEELING
                )
            }
        }

        // ======== 4. 日用品 健康防护 ========
        // 除湿剂 → 降低脚气风险
        val hasDehumidifier = products.contains(HouseholdProductType.DEHUMIDIFIER)
        val dehumidifierProtection = HouseholdProductSystem.healthProtectionModifier(
            hasDehumidifier, HouseholdProductType.DEHUMIDIFIER, humidity
        )
        if (dehumidifierProtection > 0) {
            val currentFungus = clothing.footFungusRisk
            val reduced = (currentFungus - (dehumidifierProtection * 15).toInt()).coerceAtLeast(0)
            _clothingWearState.value = _clothingWearState.value.copy(footFungusRisk = reduced)
        }

        // 护肤品 → 降低皮肤问题
        val hasMoisturizer = products.contains(HouseholdProductType.MOISTURIZER)
        val moisturizerProtection = HouseholdProductSystem.healthProtectionModifier(
            hasMoisturizer, HouseholdProductType.MOISTURIZER, humidity
        )
        if (moisturizerProtection > 0 && clothing.skinIssueRisk > 10 && _gameDay.value % 7 == 0) {
            _clothingWearState.value = _clothingWearState.value.copy(
                skinIssueRisk = (clothing.skinIssueRisk - (moisturizerProtection * 30).toInt()).coerceAtLeast(0)
            )
        }
    }

    /**
     * 每日医疗健康调度
     *
     * 1. 检测新疾病触发
     * 2. 现有疾病恢复/恶化推进
     * 3. 疾病对身体+心理的影响
     */
    private fun tickMedicalDaily() {
        if (_gameDay.value - _lastMedicalTickDay < 1) return
        _lastMedicalTickDay = _gameDay.value

        val medical = _medicalState.value
        val clothing = _clothingWearState.value
        val body = _bodyState.value
        val age = _playerAge.value
        val temp = 15.0 + 15 * Math.sin(_gameDay.value.toDouble() / 365 * 2 * Math.PI)
        val humidity = 0.5 + 0.3 * Math.sin(_gameDay.value.toDouble() / 365 * 2 * Math.PI)
        val isRaining = Math.random() < 0.15
        val constitution = constitutionScore()

        var updatedDiseases = medical.activeDiseases.toMutableList()
        var updatedSequelae = medical.permanentSequelae.toMutableList()
        var totalFatigueDelta = 0
        val newDiseaseLogs = mutableListOf<String>()

        // ======== 1. 疾病触发检测 ========
        val triggeredDisease = DiseaseTriggerRules.checkDailyTrigger(
            constitution = constitution,
            clothingHygiene = clothing.overallCleanliness.tier / 5.0,
            footFungusRisk = clothing.footFungusRisk,
            skinIssueRisk = clothing.skinIssueRisk,
            temperature = temp, humidity = humidity,
            isRaining = isRaining, age = age
        )

        if (triggeredDisease != null) {
            val existing = updatedDiseases.find { it.type == triggeredDisease && it.currentSeverity > 10 }
            if (existing == null) {
                val newDisease = ActiveDisease(
                    type = triggeredDisease,
                    currentSeverity = triggeredDisease.baseSeverity * 12 + (Math.random() * 10).toInt(),
                    daysActive = 0,
                    onsetAge = age,
                    activeTreatment = if (triggeredDisease.baseSeverity <= 3) medical.defaultTreatmentRoute else null
                )
                updatedDiseases.add(newDisease)
                newDiseaseLogs.add(GentleTextProvider.describeDiseaseOnset(newDisease))
            }
        }

        // ======== 2. 疾病逐日推进 ========
        val progressedDiseases = updatedDiseases.map { disease ->
            val progressed = disease.copy(daysActive = disease.daysActive + 1)

            // 检查是否恶化到更严重的病种
            val progression = DiseaseTriggerRules.checkDiseaseProgression(progressed, constitution, age)
            if (progression != null && progression != disease.type) {
                val noneExisting = updatedDiseases.none { existing ->
                    existing.type == progression && existing.currentSeverity > 10
                }
                if (noneExisting) {
                    updatedDiseases.add(ActiveDisease(
                        type = progression,
                        currentSeverity = progression.baseSeverity * 15,
                        daysActive = 0,
                        onsetAge = age
                    ))
                    newDiseaseLogs.add(GentleTextProvider.describeDiseaseProgression(disease.type, progression))
                }
            }

            // 恢复计算
            val result = MedicalRecoveryEngine.calculateDailyRecovery(
                progressed, constitution, age, temp, humidity
            )

            totalFatigueDelta += result.fatigueDelta

            var updated = progressed.copy(currentSeverity = result.newSeverity.coerceIn(0, 100))

            if (result.becomesChronic && !updated.isChronic) {
                updated = updated.copy(isChronic = true)
                newDiseaseLogs.add(GentleTextProvider.describeDiseaseChronic(updated))
            }
            if (result.becomesPermanent) {
                updated = updated.copy(hasPermanentSequela = true)
                if (!updatedSequelae.contains(disease.type)) {
                    updatedSequelae.add(disease.type)
                }
                newDiseaseLogs.add(GentleTextProvider.describePermanentSequela(updated))
            }

            updated
        }
        updatedDiseases = progressedDiseases.toMutableList()

        // ======== 3. 移除已痊愈的疾病（严重度归零且非慢性） ========
        val cured = updatedDiseases.filter { d -> d.currentSeverity <= 0 && !d.isChronic }
        cured.forEach { d -> newDiseaseLogs.add(GentleTextProvider.describeDiseaseCured(d)) }
        updatedDiseases = updatedDiseases.filter { d -> d.currentSeverity > 0 || d.isChronic }
            .toMutableList()

        // ======== 4. 应用到身体和心理状态 ========
        _medicalState.value = medical.copy(
            activeDiseases = updatedDiseases,
            permanentSequelae = updatedSequelae
        )

        // 疲劳惩罚
        if (totalFatigueDelta > 0) {
            _bodyState.value = body.copy(
                fatigueLevel = (body.fatigueLevel + totalFatigueDelta).coerceIn(0, 100),
                healthScore = (body.healthScore - totalFatigueDelta / 4).coerceIn(10, 100)
            )
        }

        // 慢性病长期压低头条健康基线
        val chronicCount = updatedDiseases.count { d -> d.isChronic }
        if (chronicCount > 0 && _gameDay.value % 7 == 0) {
            _bodyState.value = _bodyState.value.copy(
                healthScore = (_bodyState.value.healthScore - chronicCount).coerceIn(10, 100)
            )
        }

        // 疾病对心理的负担
        if (updatedDiseases.any { d -> d.type.baseSeverity >= 5 } && _gameDay.value % 3 == 0) {
            applyExternalPsychImpact(ExternalPsychImpact(
                source = "医疗-重症",
                anxietyDelta = 3,
                lonelinessDelta = 1
            ))
        }

        // ======== 5. 输出日志 ========
        newDiseaseLogs.forEach { addEventLog(it) }
    }

    /**
     * 处理中年自我对话选择
     */
    fun handlePastSelfDialogue(choice: PastSelfDialogueChoice) {
        val event = _pendingPastSelfDialogue ?: return
        val state = _childhoodTraumaState.value

        // 治愈创伤
        val updatedTraumas = if (choice.healsRatio > 0) {
            state.traumas.map { trauma ->
                if (!trauma.isHealed && Math.random() < choice.healsRatio) {
                    trauma.copy(isHealed = true, healedAtAge = _playerAge.value)
                } else trauma
            }
        } else {
            state.traumas
        }

        val newSeverity = TraumaGenerator.totalUnhealedSeverity(updatedTraumas)
        val psych = _psychologicalCore.value

        _childhoodTraumaState.value = state.copy(
            traumas = updatedTraumas,
            totalUnhealedSeverity = newSeverity,
            pastSelfDialogueTriggered = true,
            dialogueChoice = choice,
            hasAvoidedDialogue = choice.healsRatio == 0.0,
            dialogueYear = _currentYear.value,
            pastSelfAge = event.pastSelfAge
        )

        // 心理参数变化
        _psychologicalCore.value = psych.copy(
            anxiety = (psych.anxiety + choice.anxietyDelta).coerceIn(0, 100),
            selfIdentity = (psych.selfIdentity + choice.identityDelta).coerceIn(0, 100),
            beliefResilience = (psych.beliefResilience + choice.resilienceDelta).coerceIn(0, 100)
        )

        // 日志
        addEventLog(GentleTextProvider.describeDialogueResult(choice))

        // 治愈播报
        val healedCount = updatedTraumas.count { it.isHealed } - state.traumas.count { it.isHealed }
        if (healedCount > 0) {
            val healed = updatedTraumas.filter { it.isHealed }.takeLast(healedCount)
            healed.forEach { addEventLog(GentleTextProvider.describeHealedTrauma(it)) }
        } else if (choice.healsRatio == 0.0) {
            addEventLog(GentleTextProvider.describeTraumaIgnored())
        }

        _pendingPastSelfDialogue = null
    }

    /** 获取当前待处理的自我对话事件 */
    fun getPendingPastSelfDialogue(): PastSelfDialogueEvent? = _pendingPastSelfDialogue

    /**
     * 执行洗衣操作
     * 消耗1.5小时，恢复所有穿戴位洁净度
     */
    fun doLaundry() {
        val current = _clothingWearState.value
        val newState = LaundrySystem.doLaundry(current)
        _clothingWearState.value = newState
        addEventLog("你花了一个半小时把衣服洗了。挂在阳台上——风一吹，洗衣液的清香飘进来了。明天穿上干净的衣服——整个人都不一样。")
    }

    /**
     * 更改鞋袜（透气/更换）
     */
    fun changeFootwear(breathability: ShoeBreathability) {
        val state = _clothingWearState.value
        val feetSlot = state.slots[WearSlot.FEET]!!.copy(
            breathability = breathability,
            consecutiveWearDays = 0,
            dampness = 0,
            cleanliness = ClothingCleanliness.CLEAN
        )
        val updated = state.copy(
            slots = state.slots + (WearSlot.FEET to feetSlot)
        )
        _clothingWearState.value = updated
        addEventLog("你换上了${breathability.label}的鞋袜。脚终于可以透口气了——感觉好多了。")
        // 换鞋后脚气风险下降
        if (state.footFungusRisk > 20) {
            _clothingWearState.value = updated.copy(
                footFungusRisk = (state.footFungusRisk - 15).coerceAtLeast(0)
            )
        }
    }

    /**
     * 获取穿戴对社交印象的修正系数
     */
    fun getClothingSocialModifier(): Double {
        return _clothingWearState.value.socialImpressionModifier()
    }

    /**
     * 获取童年创伤对社交回避的加成
     */
    fun getTraumaWithdrawalBonus(): Double {
        return ChildhoodClothingCompensation.socialWithdrawalBonus(
            _childhoodTraumaState.value.traumas
        )
    }

    // ============================================
    // v2.13 材质-成分 操作接口
    // ============================================

    /** 更换某穿戴位的面料成分 */
    fun changeSlotFabric(slot: WearSlot, fabric: FabricType) {
        _slotFabrics.value = _slotFabrics.value + (slot to fabric)
        addEventLog(GentleTextProvider.describeFabricChange(slot, fabric))
    }

    /** 更换洗涤剂类型 */
    fun changeDetergent(type: DetergentType) {
        _currentDetergent.value = type
        addEventLog(GentleTextProvider.describeDetergentChange(type))
    }

    /** 购买日用品 */
    fun purchaseHouseholdProduct(product: HouseholdProductType) {
        _ownedHouseholdProducts.value = _ownedHouseholdProducts.value + product
        addEventLog(GentleTextProvider.describeHouseholdProductPurchase(product))
    }

    /** 使用药品 */
    fun useDrug(ingredient: DrugIngredient): DrugEffect {
        // 耐药性调整后的实际药效
        val resistance = _drugResistance.value[ingredient.category] ?: 0.0
        val effect = when (ingredient.category) {
            DrugCategory.ANTIFUNGAL -> DrugSystem.applyAntifungal(
                ingredient, _clothingWearState.value.footFungusRisk, resistance
            )
            else -> DrugEffect(
                ingredient = ingredient,
                actualEfficacy = DrugSystem.calculateEfficacy(ingredient, resistance),
                sideEffectTriggered = DrugSystem.checkSideEffect(ingredient)
            )
        }

        // 更新耐药性
        val newResistance = DrugSystem.accumulateResistance(resistance, ingredient)
        _drugResistance.value = _drugResistance.value + (ingredient.category to newResistance)

        // 应用效果到身体
        if (ingredient.category == DrugCategory.ANTIFUNGAL) {
            val newFungusRisk = (_clothingWearState.value.footFungusRisk -
                (effect.actualEfficacy * 30).toInt()).coerceAtLeast(0)
            _clothingWearState.value = _clothingWearState.value.copy(footFungusRisk = newFungusRisk)
        }

        if (effect.sideEffectTriggered) {
            _bodyState.value = _bodyState.value.copy(
                fatigueLevel = (_bodyState.value.fatigueLevel + 3).coerceIn(0, 100)
            )
        }

        // 日志
        addEventLog(GentleTextProvider.describeDrugUse(effect))

        // 触发宠物反馈
        _petDialogue.value = PetManager.getDrugReply(
            drugName = ingredient.label,
            category = ingredient.category.label,
            sideEffectTriggered = effect.sideEffectTriggered
        )

        return effect
    }

    // ============================================
    // v2.13 性别系统 操作接口
    // ============================================

    /** 使用止痛药缓解痛经 */
    fun usePeriodPainkiller() {
        _playerManuallyTreatedPeriodThisWeek = true
        val state = _genderState.value
        if (state.menstrualCycle != null) {
            val newCycle = MenstrualCycleSystem.usePainkiller(state.menstrualCycle)
            _genderState.value = state.copy(menstrualCycle = newCycle)
            addEventLog("你吃了一片布洛芬。半小时后——腹部的绞紧感松开了。不是不疼了——是能忍受了。在这个月最难的几天里——你给自己请了一个帮手。")
        }
    }

    /** 喝红糖姜茶 */
    fun drinkBrownSugarGinger() {
        _playerManuallyTreatedPeriodThisWeek = true
        val state = _genderState.value
        if (state.menstrualCycle != null) {
            val newCycle = MenstrualCycleSystem.useBrownSugarGinger(state.menstrualCycle)
            _genderState.value = state.copy(menstrualCycle = newCycle)
            addEventLog("你泡了一杯红糖姜茶——那股暖意从喉咙滑进胃里，再蔓延到小腹。不是药，但它让你感觉好了一点。在这个月最冷的日子里——你给自己煮了一杯热。")
        }
    }

    /** 开始怀孕（主动触发或随机事件触发） */
    fun startPregnancy() {
        val state = _genderState.value
        if (state.sex != BiologicalSex.FEMALE) return
        if (state.hasChosenDINK) {
            addEventLog("你选择了丁克——这个决定值得被尊重。不是每个人都必须当父母——你的人生规划你自己来定。")
            return
        }
        _genderState.value = state.copy(
            childbirth = ChildbirthState(
                phase = ChildbirthPhase.PREGNANT,
                pregnancyDaysRemaining = ChildbirthSystem.PREGNANCY_DURATION
            )
        )
        addEventLog("你怀孕了。接下来的九个月——你的身体会经历一场漫长的改造。这不是轻松的旅程——但你选择了它。")
    }

    /** 准备产后调理物资 */
    fun preparePostpartumSupplies() {
        val state = _genderState.value
        _genderState.value = state.copy(
            childbirth = state.childbirth.copy(hasPostpartumSupplies = true)
        )
        addEventLog("你为产后恢复准备好了物资——滋补的食材、足够的时间、家人的帮衬。这些东西不会让身体瞬间恢复——但它们会告诉你：有人在乎你的恢复。包括你自己。")
    }

    /** 更新年代社会风气（随时代推进调用） */
    fun updateEraNorm(era: EraSocialNorm) {
        _genderState.value = _genderState.value.copy(eraNorm = era)
        addEventLog(GentleTextProvider.describeEraNormChange(era))
    }

    /** 更新职场偏见等级（革新者政策推进调用） */
    fun updateWorkplaceBias(bias: WorkplaceGenderBias) {
        _genderState.value = _genderState.value.copy(workplaceBias = bias)
        if (bias.biasLevel < 0.1) {
            addEventLog("反歧视政策持续推行了多年——今天的职场，性别不再是一道额外的门槛。不是一步到位的——是很多人推了很久才推开的。")
        }
    }

    /** 选择人生路线：独身/丁克/晚婚 */
    fun chooseLifeRoute(route: String) {
        when (route) {
            "celibacy" -> _genderState.value = _genderState.value.copy(hasChosenCelibacy = true)
            "dink" -> _genderState.value = _genderState.value.copy(hasChosenDINK = true)
            "late_marriage" -> _genderState.value = _genderState.value.copy(hasChosenLateMarriage = true)
        }
        addEventLog("你做出了一个关于自己人生的决定。不关别人的事——这是你的路。小镇不会替你评判——只会在你走每一步的时候，安静地陪着。")
    }

    // ============================================
    // v2.0 医疗系统 操作接口
    // ============================================

    /** 选择全局默认治疗路线 */
    fun setDefaultTreatmentRoute(route: TreatmentRoute) {
        _medicalState.value = _medicalState.value.copy(defaultTreatmentRoute = route)
        addEventLog(GentleTextProvider.describeTreatmentRouteChoice(route))
    }

    /** 为特定疾病选择治疗方案 */
    fun treatDisease(diseaseType: DiseaseType, route: TreatmentRoute, drugTier: DrugTier) {
        _playerManuallyTreatedDiseaseThisWeek = true
        val medical = _medicalState.value
        // 路线与药品的兼容性检查
        if (drugTier.priceTier > route.maxDrugTier.priceTier) {
            addEventLog("${route.label}路线不支持使用${drugTier.label}级别的药物——换一个方案试试。")
            return
        }
        // 需要诊断但路线不支持
        if (drugTier.requiresDiagnosis && !route.requiresDiagnosis) {
            addEventLog("${drugTier.label}需要先拍片/化验才能使用——${route.label}路线不包含诊断流程。要么换药，要么换路线。")
            return
        }

        val updatedDiseases = medical.activeDiseases.map { d ->
            if (d.type == diseaseType) {
                d.copy(
                    activeTreatment = route,
                    activeDrugTier = drugTier,
                    treatmentDays = 0
                )
            } else d
        }

        _medicalState.value = medical.copy(activeDiseases = updatedDiseases)
        val costEstimate = drugTier.priceTier * 50.0
        addEventLog(GentleTextProvider.describeTreatmentStart(diseaseType, route, drugTier, costEstimate))
    }

    /** 放弃治疗（回到自愈路线） */
    fun abandonTreatment(diseaseType: DiseaseType) {
        _playerManuallyTreatedDiseaseThisWeek = true
        val medical = _medicalState.value
        val updatedDiseases = medical.activeDiseases.map { d ->
            if (d.type == diseaseType) {
                d.copy(activeTreatment = null, activeDrugTier = null)
            } else d
        }
        _medicalState.value = medical.copy(activeDiseases = updatedDiseases)
        addEventLog(GentleTextProvider.describeTreatmentAbandoned(diseaseType))
    }

    /** 更新营养等级（饮食系统联动） */
    fun updateNutritionLevel(level: NutritionLevel) {
        val medical = _medicalState.value
        val newConstitution = (medical.constitutionScore + level.constitutionModifier)
            .coerceIn(20, 95)
        _medicalState.value = medical.copy(constitutionScore = newConstitution)
        addEventLog(GentleTextProvider.describeNutritionLevelChange(level))
    }

    // ============================================
    // v2.0 药品目录 查询接口（折叠详情层）
    // ============================================

    /**
     * 获取病症的快速三选一方案文本
     * 主界面小病弹窗用——草药/西药/自愈 三行简洁文本
     */
    fun getQuickTripleChoiceText(diseaseType: DiseaseType): String {
        val (herbal, western, selfHealNote) = DiseaseDrugMatcher.getQuickTripleChoice(diseaseType)
        return GentleTextProvider.describeQuickChoice(diseaseType, herbal, western, selfHealNote)
    }

    /**
     * 根据家庭收入自动匹配药品
     * 玩家不点详情时，系统后台自动选择
     */
    fun autoMatchDrug(diseaseType: DiseaseType, monthlyIncome: Double): Pair<SpecificDrug?, DrugTier> {
        return DiseaseDrugMatcher.autoMatchByIncome(diseaseType, monthlyIncome)
    }

    /**
     * 获取某药品的详情弹窗文本
     * 玩家点击详情按钮时调用
     */
    fun getDrugDetailText(drugId: String): String? {
        val drug = DrugCatalog.allDrugs.find { it.id == drugId } ?: return null
        return GentleTextProvider.describeDrugDetail(drug)
    }

    /**
     * 获取某药品的详情弹窗标题
     */
    fun getDrugDetailTitle(drugId: String): String? {
        val drug = DrugCatalog.allDrugs.find { it.id == drugId } ?: return null
        return GentleTextProvider.describeDrugDetailTitle(drug)
    }

    /**
     * 获取某病症下的全部药品（按档位分组）
     * 详情弹窗展示用
     */
    fun getDrugOptionsForDisease(diseaseType: DiseaseType): Map<DrugTier, List<SpecificDrug>> {
        return DiseaseDrugMatcher.getAvailableDrugs(diseaseType)
    }

    /**
     * 获取某病症下的药品汇总（按用途+档位双重分组）
     * 深层详情弹窗：该病有哪些用途的药、各档位各有哪些选项
     */
    fun getDrugSummaryForDisease(diseaseType: DiseaseType): Map<DrugUsageCategory, Map<DrugTier, List<SpecificDrug>>> {
        return DiseaseDrugMatcher.getDrugSummaryByDisease(diseaseType)
    }

    /**
     * 按药品档位获取该档位下所有药物
     */
    fun getDrugsByTier(tier: DrugTier): List<SpecificDrug> {
        return DrugCatalog.allDrugs.filter { it.tier == tier }
    }

    // ============================================
    // v2.16 性别差异化用药 查询接口
    // ============================================

    /**
     * 获取当前性别生理阶段标识（孕期/产后/经期/青春期/null）
     */
    fun getGenderStageBanner(): String? {
        return GenderDrugRules.describeStageBanner(_genderState.value, _playerAge.value)
    }

    /**
     * 获取当前性别阶段用药提示（展示在药品详情弹窗顶部）
     */
    fun getGenderStagePrompt(): String? {
        return GenderDrugRules.describeStagePrompt(_genderState.value, _playerAge.value)
    }

    /**
     * 是否存在活跃的性别生理阶段
     */
    fun hasActiveGenderStage(): Boolean {
        return GenderDrugRules.hasActiveStage(_genderState.value, _playerAge.value)
    }

    /**
     * 获取某病症下当前性别可用的药品（按档位分组，已过滤锁定药品）
     */
    fun getGenderAwareDrugOptions(diseaseType: DiseaseType): Map<DrugTier, List<GenderAwareDrugInfo>> {
        val drugs = DiseaseDrugMatcher.getAvailableDrugs(diseaseType)
        return drugs.mapValues { (_, tierDrugs) ->
            GenderDrugRules.wrapAll(tierDrugs, _genderState.value, _playerAge.value)
        }
    }

    /**
     * 获取某药品的性别感知详情文本（基础详情 + 性别阶段提示）
     */
    fun getGenderAwareDrugDetail(drugId: String): String? {
        val drug = DrugCatalog.allDrugs.find { it.id == drugId } ?: return null
        val baseText = GentleTextProvider.describeDrugDetail(drug)
        val genderNotes = GenderDrugRules.describeDrugGenderNotes(drugId, _genderState.value, _playerAge.value)
        return if (genderNotes != null) baseText + genderNotes else baseText
    }

    /**
     * 获取某药品的锁定状态（用于 UI 置灰判断）
     */
    fun getDrugLockStatus(drugId: String): DrugLockStatus {
        return GenderDrugRules.getLockStatus(drugId, _genderState.value)
    }

    /**
     * 判断某药品当前是否可用
     */
    fun isDrugCurrentlyAvailable(drugId: String): Boolean {
        return GenderDrugRules.isDrugAllowed(drugId, _genderState.value)
    }

    /**
     * 获取性别感知的快速三选一方案（自动过滤锁定药品）
     */
    fun getGenderAwareQuickChoice(diseaseType: DiseaseType): Triple<GenderAwareDrugInfo?, GenderAwareDrugInfo?, String> {
        val (herbal, western, selfHealNote) = DiseaseDrugMatcher.getQuickTripleChoice(diseaseType)
        val genderState = _genderState.value
        val age = _playerAge.value

        val herbalInfo = herbal?.let {
            if (GenderDrugRules.isDrugAllowed(it.id, genderState)) {
                GenderDrugRules.wrapDrug(it, genderState, age)
            } else null
        }
        val westernInfo = western?.let {
            if (GenderDrugRules.isDrugAllowed(it.id, genderState)) {
                GenderDrugRules.wrapDrug(it, genderState, age)
            } else null
        }

        return Triple(herbalInfo, westernInfo, selfHealNote)
    }

    // ============================================
    // v2.17 传统俗语·时代局限性 认知模块
    // ============================================

    /** 已解锁的俗语词条 ID */
    private val _unlockedSayingIds = MutableStateFlow<Set<String>>(emptySet())
    val unlockedSayingIds: StateFlow<Set<String>> = _unlockedSayingIds.asStateFlow()

    /**
     * 向长辈求助——触发俗语建议
     * 短期焦虑小幅下降，现实参数不变；同时解锁该俗语认知词条
     *
     * @param trigger 触发场景（择业受挫/婚恋压力/中年危机等）
     * @return 长辈给出的俗语建议文案；null 表示无匹配俗语（触发兜底文案）
     */
    fun askElderAdvice(trigger: SayingTrigger): String {
        // 选取该触发场景下尚未解锁的俗语
        val candidates = TraditionalSayingsLibrary.getByTrigger(trigger)
            .filter { it.id !in _unlockedSayingIds.value }

        if (candidates.isEmpty()) {
            // 该场景俗语已全部解锁——兜底文案
            return GentleTextProvider.describeElderAdviceFallback()
        }

        val saying = candidates.random()

        // 解锁该词条
        _unlockedSayingIds.value = _unlockedSayingIds.value + saying.id

        // 短期焦虑下降（仅情绪层面）
        val currentAnxiety = _mentalState.value.anxiety
        _mentalState.value = _mentalState.value.copy(
            anxiety = (currentAnxiety - saying.anxietyReduction).coerceAtLeast(0)
        )

        // 记录事件日志
        val eventText = GentleTextProvider.describeElderAdvice(saying)
        val fullReflection = GentleTextProvider.describeSayingReflection(saying)
        addEventLog(eventText + "\n\n" + fullReflection)

        return eventText
    }

    /**
     * 获取某俗语词条的完整反思内容
     */
    fun getSayingReflection(sayingId: String): String? {
        val saying = TraditionalSayingsLibrary.getById(sayingId) ?: return null
        return GentleTextProvider.describeSayingReflection(saying)
    }

    /**
     * 获取已解锁的俗语列表
     */
    fun getUnlockedSayings(): List<TraditionalSaying> {
        return TraditionalSayingsLibrary.allSayings
            .filter { it.id in _unlockedSayingIds.value }
    }

    /**
     * 按板块获取已解锁的俗语列表
     */
    fun getUnlockedSayingsByCategory(category: SayingCategory): List<TraditionalSaying> {
        return TraditionalSayingsLibrary.getByCategory(category)
            .filter { it.id in _unlockedSayingIds.value }
    }

    /**
     * 获取某板块的俗语目录（含解锁状态）
     */
    fun getSayingCatalog(category: SayingCategory): String {
        val sayings = TraditionalSayingsLibrary.getByCategory(category)
        val unlocked = sayings.filter { it.id in _unlockedSayingIds.value }
        return GentleTextProvider.describeSayingCatalog(category, unlocked)
    }

    /**
     * 检查某个俗语是否已解锁
     */
    fun isSayingUnlocked(sayingId: String): Boolean {
        return sayingId in _unlockedSayingIds.value
    }

    /**
     * 获取所有板块的解锁进度
     */
    fun getSayingUnlockProgress(): Map<SayingCategory, Pair<Int, Int>> {
        return SayingCategory.entries.associateWith { category ->
            val total = TraditionalSayingsLibrary.getByCategory(category).size
            val unlocked = TraditionalSayingsLibrary.getByCategory(category)
                .count { it.id in _unlockedSayingIds.value }
            Pair(unlocked, total)
        }
    }

    // ============================================
    // v2.18 成语自由探索 —— 旧书房
    // ============================================

    /**
     * 在旧书房中浏览一条成语（主动探索发现）
     * 不强制、不催促，玩家主动点进来才会触发
     */
    fun browseIdiomInLibrary(idiomId: Int): String {
        val idiom = IdiomRepository.getIdiomById(idiomId) ?: return "这卷竹简似乎被虫蛀了，看不清字迹。"
        // 记录发现
        _discoveredIdiomIds.value = _discoveredIdiomIds.value + idiomId
        // 主动探索也给予少量觉醒值
        addAwakeningPoints(2)
        return GentleTextProvider.describeIdiomCard(idiom)
    }

    /**
     * 获取旧书房推荐探索的成语（优先推荐未发现的）
     */
    fun getLibraryRecommendations(count: Int = 5): List<IdiomData> {
        return IdiomExploreManager.getExploreRecommendations(_discoveredIdiomIds.value, count)
    }

    /**
     * 按主题浏览旧书房中的成语
     */
    fun getLibraryByTheme(theme: IdiomExploreManager.IdiomTheme): List<IdiomData> {
        return IdiomExploreManager.getByTheme(theme)
    }

    /**
     * 按稀有度浏览旧书房中的成语
     */
    fun getLibraryByRarity(rarity: IdiomRarity): List<IdiomData> {
        return IdiomExploreManager.getByRarity(rarity)
    }

    /**
     * 获取所有主题及其成语数量
     */
    fun getLibraryAllThemes(): Map<IdiomExploreManager.IdiomTheme, Int> {
        return IdiomExploreManager.getAllThemes()
    }

    /**
     * 获取所有主题的探索进度
     */
    fun getLibraryThemeProgress(): Map<IdiomExploreManager.IdiomTheme, Pair<Int, Int>> {
        return IdiomExploreManager.getAllThemeProgress(_discoveredIdiomIds.value)
    }

    /**
     * 获取所有稀有度等级的成语数量
     */
    fun getLibraryRarityDistribution(): Map<IdiomRarity, Int> {
        return IdiomExploreManager.getRarityDistribution()
    }

    /**
     * 检查并触发里程碑成语解锁
     * 在角色年龄变更时调用，到达里程碑节点自动解锁1-2条未发现的成语
     *
     * @return 本次解锁的成语ID列表（可能为空）
     */
    fun checkMilestoneIdiomUnlock(age: Int): List<Int> {
        val unlocks = IdiomExploreManager.getMilestoneUnlocks(age, _discoveredIdiomIds.value)
        if (unlocks.isEmpty()) return emptyList()

        val unlockedIds = unlocks.map { it.idiomId }
        _discoveredIdiomIds.value = _discoveredIdiomIds.value + unlockedIds

        // 记录事件日志
        val idiomNames = unlocks.joinToString("、") { it.name }
        addEventLog("岁月在你身上留下了痕迹，也让你读懂了一些从前看不懂的话。\n新领悟：${idiomNames}")

        return unlockedIds
    }

    /**
     * 偶然发现一条成语（日常随机事件触发）
     */
    fun discoverRandomIdiom(): IdiomData? {
        val idiom = IdiomExploreManager.getRandomUndiscovered(_discoveredIdiomIds.value) ?: return null
        _discoveredIdiomIds.value = _discoveredIdiomIds.value + idiom.idiomId
        addEventLog("你在旧书房的角落里，翻到了一卷落满灰尘的竹简。\n「${idiom.name}」—— 你以前从没认真读过它。")
        return idiom
    }

    /**
     * 获取已发现的成语总数
     */
    fun getDiscoveredIdiomCount(): Int = _discoveredIdiomIds.value.size

    /**
     * 获取成语总数
     */
    fun getTotalIdiomCount(): Int = IdiomExploreManager.getTotalCount()

    /**
     * 检查某个成语是否已被发现
     */
    fun isIdiomDiscovered(idiomId: Int): Boolean = idiomId in _discoveredIdiomIds.value

    /**
     * 获取未发现的成语列表
     */
    fun getUndiscoveredIdioms(): List<IdiomData> {
        val all = IdiomRepository.getAllIdioms()
        return all.filter { it.idiomId !in _discoveredIdiomIds.value }
    }

    // ============================================
    // v2.19 场景联动 —— 人生事件 + 穿搭冲突
    // ============================================

    /**
     * 向长辈求助 —— 根据人生困境类型，触发传统成语建议
     *
     * 核心逻辑：长辈输出俗语 → 焦虑值小幅下降（心理安慰）
     * 现实困境（失业、婚恋、经济、健康）数值无任何改善
     *
     * @param scene 当前人生困境类型
     * @return 长辈建议文案；null 表示无匹配成语（已触发兜底文案）
     */
    fun askElderAboutLifeEvent(scene: IdiomSceneMapper.LifeEventScene): String {
        val idiom = IdiomSceneMapper.getElderAdviceIdiom(scene, _discoveredIdiomIds.value)

        if (idiom == null) {
            val fallback = GentleTextProvider.describeElderNoAdviceFallback(scene.displayName)
            addEventLog(fallback)
            return fallback
        }

        // 记录发现
        _discoveredIdiomIds.value = _discoveredIdiomIds.value + idiom.idiomId

        // 短期焦虑下降（仅情绪层面）
        val currentAnxiety = _mentalState.value.anxiety
        _mentalState.value = _mentalState.value.copy(
            anxiety = (currentAnxiety - 3).coerceAtLeast(0)
        )

        // 输出长辈话术
        val adviceText = GentleTextProvider.describeElderIdiomAdvice(idiom, scene.displayName)
        addEventLog(adviceText)

        return adviceText
    }

    // ============================================
    // v2.14 认知反思系统（时代局限性深度反思条目）
    // ============================================

    /**
     * 检查并触发认知反思条目
     *
     * 在长辈输出俗语之后调用。根据场景和年龄匹配可用的认知反思条目。
     * 支持9条俗语，分青年(22岁)/中年(30岁)/资深(46岁)三档解锁。
     *
     * @param scene 当前人生困境类型
     * @return 触发的反思条目；null 表示未触发
     */
    fun checkCognitionReflectionAfterElderAdvice(
        scene: IdiomSceneMapper.LifeEventScene
    ): CognitionReflection? {
        val age = _playerAge.value

        // 场景映射：LifeEventScene → 认知反思触发场景关键词（19条俗语全覆盖）
        val sceneKeys = when (scene) {
            IdiomSceneMapper.LifeEventScene.CAREER_FRUSTRATION -> listOf(
                "失业/负债/事业受挫", "职场表现突出/被同事排挤", "收入停滞/羡慕他人生活",
                "想要改变现状但被劝阻", "频繁变动/环境不适应"
            )
            IdiomSceneMapper.LifeEventScene.FINANCIAL_STRESS -> listOf(
                "失业/负债/事业受挫", "利益受损/付出无回报", "经济不确定/未来焦虑"
            )
            IdiomSceneMapper.LifeEventScene.SOCIAL_CONFLICT -> listOf(
                "交友不慎/被朋友拖累", "待人宽厚反被算计", "被他人拖累/卷入是非",
                "被他人过度索取/身心透支"
            )
            IdiomSceneMapper.LifeEventScene.HEALTH_ANXIETY -> listOf(
                "生活杂乱/心力交瘁"
            )
            IdiomSceneMapper.LifeEventScene.GENERAL_ADVICE -> listOf(
                "家庭争吵/亲子矛盾", "过度操心晚辈/身心俱疲", "工作辛苦想放弃学习/觉得读书无用",
                "个人意愿与集体/家庭要求冲突", "拒绝接受新事物/固守旧习惯",
                "孤独感/社交压力", "退休焦虑/年龄压力"
            )
            else -> null
        } ?: return null

        // 查找可用的认知反思（按场景匹配 + 年龄门槛）
        val available = sceneKeys.flatMap { key ->
            CognitionReflectionLibrary.getAvailableForScene(
                scene = key,
                age = age,
                unlockedIds = _unlockedReflectionIds.value
            )
        }.distinctBy { it.id }

        if (available.isEmpty()) return null

        val entry = available.first()
        _activeCognitionReflection.value = entry
        _unlockedReflectionIds.value = _unlockedReflectionIds.value + entry.id

        addEventLog("你翻开了书房的竹简：「${entry.proverb}」——\n${entry.layerOne.coreInsight}")
        return entry
    }

    /**
     * 玩家在认知反思中选择了一条路径
     *
     * 支持最多3个选择分支，应用心理/身体/社交/职业/技能/家庭多维度参数变化。
     *
     * @param choiceId 选择ID（如 "endure" / "avoid" / "converge" / "balance" 等）
     */
    fun resolveCognitionReflectionChoice(choiceId: String) {
        val entry = _activeCognitionReflection.value ?: return
        val choice = entry.choices.firstOrNull { it.id == choiceId } ?: return

        val result = choice.result

        // ── 心理参数 ──
        val mental = _mentalState.value
        _mentalState.value = mental.copy(
            anxiety = (mental.anxiety + result.anxietyDelta).coerceIn(0, 100),
            selfEsteem = (mental.selfEsteem + result.identityDelta).coerceIn(0, 100),
            socialFulfillment = (mental.socialFulfillment + result.socialWillDelta + result.socialGoodwillDelta + result.familyIntimacyDelta).coerceIn(0, 100),
            loneliness = (mental.loneliness + result.lonelinessDelta).coerceIn(0, 100),
            workSatisfaction = (mental.workSatisfaction + result.careerProgressDelta).coerceIn(0, 100),
            creativeFlow = (mental.creativeFlow + result.skillPointsDelta).coerceIn(0, 100),
            daysInNegativeState = if (result.clearNegativeStatus) 0 else mental.daysInNegativeState
        )

        // ── 身体参数 ──
        val body = _bodyState.value
        _bodyState.value = body.copy(
            fatigueLevel = (body.fatigueLevel + result.fatigueDelta).coerceIn(0, 100)
        )

        // 输出选择结果
        _cognitionReflectionResult.value = result.followUpText
        addEventLog(result.followUpText)

        // 清理
        _activeCognitionReflection.value = null
    }

    /**
     * 关闭认知反思（不做选择，只是关闭查看）
     */
    fun dismissCognitionReflection() {
        _activeCognitionReflection.value = null
    }

    /**
     * 穿搭冲突触发 —— 长辈看到你的个性穿搭，忍不住点评一句
     *
     * 触发条件：玩家选择了特定风格的穿搭（前卫/过度消费/不合传统）
     * 效果：仅心理提示，不改变穿搭属性、不影响社交好感
     *
     * @param conflictType 穿搭冲突类型
     * @param outfitName 穿搭套装名称
     * @return 长辈点评文案；null 表示未触发（概率控制）
     */
    fun checkOutfitConflict(
        conflictType: IdiomSceneMapper.OutfitStyleConflict,
        outfitName: String
    ): String? {
        // 30%概率触发，避免每次穿搭都被点评
        if (kotlin.random.Random.nextFloat() > 0.3f) return null

        val idiom = IdiomSceneMapper.getOutfitConflictIdiom(conflictType) ?: return null

        // 记录发现
        _discoveredIdiomIds.value = _discoveredIdiomIds.value + idiom.idiomId

        // 轻微焦虑浮动（代际审美差异带来的不适）
        val currentAnxiety = _mentalState.value.anxiety
        _mentalState.value = _mentalState.value.copy(
            anxiety = (currentAnxiety + 2).coerceAtMost(100)
        )

        val text = GentleTextProvider.describeOutfitConflictAdvice(idiom, outfitName)
        addEventLog(text)

        return text
    }

    // ============================================
    // 成语‑抉择联动引擎
    // ============================================

    /**
     * 根据角色当前状态推断原生家庭背景
     *
     * 推断依据：
     * - 月薪 > 15000 → 富裕家庭
     * - 月薪 > 6000  → 中产家庭
     * - 其他         → 保守工薪家庭
     */
    private fun inferFamilyBackground(): DecisionPreferenceEngine.CharacterBackground {
        val income = _spaceState.value.monthlyIncome
        return when {
            income > 15000.0 -> DecisionPreferenceEngine.CharacterBackground.WEALTHY_FAMILY
            income > 6000.0 -> DecisionPreferenceEngine.CharacterBackground.MIDDLE_FAMILY
            else -> DecisionPreferenceEngine.CharacterBackground.CONSERVATIVE_FAMILY
        }
    }

    /**
     * 根据角色消费行为推断消费观念
     *
     * 推断依据：
     * - 衣物数量少 + 低消费 → 节俭型
     * - 衣物数量适中          → 均衡型
     * - 衣物数量多 + 高消费   → 随性型
     */
    private fun inferSpendingPhilosophy(): DecisionPreferenceEngine.SpendingPhilosophy {
        val clothingCount = _clothingItems.value.size
        val outfitCount = _outfitSets.value.size
        return when {
            clothingCount <= 5 && outfitCount <= 3 -> DecisionPreferenceEngine.SpendingPhilosophy.THRIFTY
            clothingCount <= 15 -> DecisionPreferenceEngine.SpendingPhilosophy.BALANCED
            else -> DecisionPreferenceEngine.SpendingPhilosophy.CASUAL
        }
    }

    /**
     * 构建决策上下文
     */
    fun buildDecisionContext(): DecisionPreferenceEngine.DecisionContext {
        return DecisionPreferenceEngine.DecisionContext(
            age = _playerAge.value,
            background = inferFamilyBackground(),
            spending = inferSpendingPhilosophy(),
            unlockedReflectionIds = _unlockedReflectionIds.value,
            history = _decisionHistory.value
        )
    }

    /**
     * 根据人生事件场景获取候选俗语
     *
     * 同一事件，不同角色会想起不同的俗语。
     * 调用此方法后，_candidateIdioms 状态流更新，UI 可展示候选俗语列表。
     *
     * @param scene 人生事件场景
     * @return 候选俗语列表（2-3条），包含当前决策倾向和原因说明
     */
    fun getCandidateIdiomsForEvent(
        scene: IdiomSceneMapper.LifeEventScene
    ): DecisionPreferenceEngine.CandidateIdioms {
        val eventType = DecisionPreferenceEngine.sceneToEventType(scene)
            ?: return DecisionPreferenceEngine.CandidateIdioms(
                tendency = DecisionPreferenceEngine.DecisionTendency.BALANCED,
                entries = emptyList(),
                tendencyReason = "无匹配事件类型",
                tendencyDescription = "无匹配事件类型"
            )

        val ctx = buildDecisionContext()
        val result = DecisionPreferenceEngine.getCandidateIdioms(eventType, ctx)
        _candidateIdioms.value = result
        return result
    }

    /**
     * 记录一次决策（含俗语和选择）
     *
     * 在玩家做出选择后调用，更新决策历史，影响后续决策倾向。
     *
     * @param idiomId 俗语ID
     * @param choiceId 选择ID
     * @param outcome 结果：positive / negative / neutral
     */
    fun recordDecisionWithIdiom(
        idiomId: String,
        choiceId: String,
        outcome: String = "neutral"
    ) {
        val record = DecisionPreferenceEngine.DecisionRecord(
            eventType = _candidateIdioms.value?.entries?.firstOrNull()?.triggerScene ?: "未知",
            idiomId = idiomId,
            choiceId = choiceId,
            outcome = outcome,
            age = _playerAge.value
        )
        _decisionHistory.value = DecisionPreferenceEngine.updateHistory(
            _decisionHistory.value, record
        )
        _candidateIdioms.value = null
    }

    /**
     * 获取当前决策倾向说明文字（用于 UI 展示）
     */
    fun getDecisionTendencyDescription(): String {
        return DecisionPreferenceEngine.describeTendency(buildDecisionContext())
    }

    // ============================================
    // 精细手动模式 — 游戏模式切换
    // ============================================

    /** 切换游戏模式 */
    fun toggleGameMode() {
        _gameMode.value = if (_gameMode.value == GameMode.AUTO) GameMode.MANUAL else GameMode.AUTO
    }

    fun setGameMode(mode: GameMode) {
        _gameMode.value = mode
    }

    /** 切换自动生活模式（工作日自动托管 / 周末手动精细） */
    fun toggleAutoLife() {
        _autoLifeEnabled.value = !_autoLifeEnabled.value
        val state = if (_autoLifeEnabled.value) "开启" else "关闭"
        addEventLog("自动生活模式已${state}——工作日将自动处理基础生活开销")
    }

    // ============================================
    // 精细手动模式 — 饮食系统
    // ============================================

    /** 更新当日饮食计划 */
    fun setDailyDietPlan(plan: DietSystem.DailyDietPlan) {
        _dailyDietPlan.value = plan
        _dietEffect.value = DietSystem.calculateDailyEffect(plan)

        // 应用饮食效果到健康参数
        val effect = _dietEffect.value ?: return
        val bodyScale = 10.0  // 将 0-1 的 delta 映射到 0-100 的身体状态
        val mentalScale = 10.0
        _bodyState.value = _bodyState.value.copy(
            fatigueLevel = (_bodyState.value.fatigueLevel + (effect.fatigueDelta * bodyScale).toInt()).coerceIn(0, 100),
            immuneLevel = (_bodyState.value.immuneLevel + (effect.gutHealthDelta * bodyScale).toInt()).coerceIn(0, 100)
        )
        _mentalState.value = _mentalState.value.copy(
            anxiety = (_mentalState.value.anxiety - (effect.joyDelta * mentalScale).toInt()).coerceIn(0, 100),
            sleepQuality = (_mentalState.value.sleepQuality + (effect.sleepDelta * mentalScale).toInt()).coerceIn(0, 100)
        )
        // 扣除饮食花费
        _spaceState.value = _spaceState.value.copy(
            currentSavings = (_spaceState.value.currentSavings - effect.totalCost).coerceAtLeast(0.0)
        )
    }

    /** 设置长期饮食习惯 */
    fun setDietHabit(habit: DietSystem.LongTermDietHabit) {
        _dietHabit.value = habit
    }

    /** 自动生成当日饮食（根据消费观念） */
    fun autoGenerateDiet() {
        val isThrifty = inferSpendingPhilosophy() == DecisionPreferenceEngine.SpendingPhilosophy.THRIFTY
        val isBalanced = inferSpendingPhilosophy() == DecisionPreferenceEngine.SpendingPhilosophy.BALANCED
        val plan = DietSystem.autoGenerateDailyDiet(isThrifty, isBalanced, false)
        setDailyDietPlan(plan)
    }

    // ============================================
    // 精细手动模式 — 工作日晚间规划
    // ============================================

    /** 设置今晚活动 */
    fun setEveningActivities(activities: List<WorkdayPlanner.Activity>) {
        val plan = WorkdayPlanner.calculateEveningEffect(activities)
        _eveningPlan.value = plan

        // 应用活动效果
        val scale = 8.0
        _bodyState.value = _bodyState.value.copy(
            fatigueLevel = (_bodyState.value.fatigueLevel + (plan.totalFatigue * scale).toInt()).coerceIn(0, 100)
        )
        _mentalState.value = _mentalState.value.copy(
            workStress = (_mentalState.value.workStress + (plan.totalFatigue * scale).toInt()).coerceIn(0, 100),
            anxiety = (_mentalState.value.anxiety + (plan.totalAnxiety * scale).toInt()).coerceIn(0, 100),
            selfEsteem = (_mentalState.value.selfEsteem + (plan.totalSelfEsteem * scale).toInt()).coerceIn(0, 100),
            happiness = (_mentalState.value.happiness + (plan.totalSocialFulfillment * scale).toInt()).coerceIn(0, 100)
        )
        _spaceState.value = _spaceState.value.copy(
            currentSavings = (_spaceState.value.currentSavings - plan.totalCost).coerceAtLeast(0.0)
        )

        // 检查是否包含极端抉择（辞职/旷工）→ 触发决策弹窗
        val hasExtreme = activities.any { it.category == WorkdayPlanner.ActivityCategory.EXTREME }
        if (hasExtreme) {
            getCandidateIdiomsForEvent(
                IdiomSceneMapper.LifeEventScene.CAREER_FRUSTRATION
            )
        }
    }

    /** 自动生成今晚活动 */
    fun autoGenerateEvening() {
        val isThrifty = inferSpendingPhilosophy() == DecisionPreferenceEngine.SpendingPhilosophy.THRIFTY
        val isBalanced = inferSpendingPhilosophy() == DecisionPreferenceEngine.SpendingPhilosophy.BALANCED
        val plan = WorkdayPlanner.autoGenerateEvening(isThrifty, isBalanced)
        setEveningActivities(plan.activities)
    }

    // ============================================
    // 精细手动模式 — 职业选择
    // ============================================

    /** 选择职业 */
    fun selectCareer(careerId: String) {
        val career = CareerPathSystem.allCareers.firstOrNull { it.id == careerId } ?: return

        val isConservative = inferFamilyBackground() == DecisionPreferenceEngine.CharacterBackground.CONSERVATIVE_FAMILY
        val isWealthy = inferFamilyBackground() == DecisionPreferenceEngine.CharacterBackground.WEALTHY_FAMILY
        val skillPoints = _bodyState.value.fatigueLevel.toDouble() / 10.0 // 简化：用体能估算技能点
        val recommended = CareerPathSystem.getRecommendedPath(
            isConservative, isWealthy, skillPoints, _spaceState.value.currentSavings
        )
        val isRecommended = recommended == career.pathType

        val effect = CareerPathSystem.calculateCareerEffect(
            career, _spaceState.value.currentSavings, isRecommended
        )

        _careerChoiceEffect.value = effect
        _spaceState.value = _spaceState.value.copy(
            monthlyIncome = effect.monthlySalary,
            currentSavings = (_spaceState.value.currentSavings - effect.startupCostDeducted).coerceAtLeast(0.0)
        )

        addEventLog("选择了职业：${career.name}，月薪${effect.monthlySalary}元。${if (isRecommended) "系统推荐路线。" else "你选择了自己的路。"}")

        // 未开局状态下选择职业，自动进入第1天
        if (_gameDay.value == 0) {
            _gameDay.value = 1
        }
    }

    /** 获取推荐职业路径 */
    fun getRecommendedCareerPath(): CareerPathSystem.CareerPathType {
        val isConservative = inferFamilyBackground() == DecisionPreferenceEngine.CharacterBackground.CONSERVATIVE_FAMILY
        val isWealthy = inferFamilyBackground() == DecisionPreferenceEngine.CharacterBackground.WEALTHY_FAMILY
        val skillPoints = _bodyState.value.energy * 10.0
        return CareerPathSystem.getRecommendedPath(
            isConservative, isWealthy, skillPoints, _spaceState.value.currentSavings
        )
    }

    // ============================================
    // 精细手动模式 — 玩家主动事件
    // ============================================

    /** 主动触发玩家事件 */
    fun triggerPlayerEvent(eventId: String) {
        val event = PlayerEventSystem.allPlayerEvents.firstOrNull { it.id == eventId } ?: return

        val effect = PlayerEventSystem.calculateEffect(event)
        _playerEventEffect.value = effect

        // 应用事件效果
        val scale = 8.0
        _bodyState.value = _bodyState.value.copy(
            fatigueLevel = (_bodyState.value.fatigueLevel + (event.fatigueDelta * scale).toInt()).coerceIn(0, 100),
            immuneLevel = (_bodyState.value.immuneLevel + (event.healthDelta * scale).toInt()).coerceIn(0, 100)
        )
        _mentalState.value = _mentalState.value.copy(
            anxiety = (_mentalState.value.anxiety + (event.anxietyDelta * scale).toInt()).coerceIn(0, 100),
            selfEsteem = (_mentalState.value.selfEsteem + (event.selfEsteemDelta * scale).toInt()).coerceIn(0, 100),
            happiness = (_mentalState.value.happiness + (event.socialFulfillmentDelta * scale).toInt()).coerceIn(0, 100)
        )
        _spaceState.value = _spaceState.value.copy(
            currentSavings = (_spaceState.value.currentSavings - event.cost).coerceAtLeast(0.0)
        )

        addEventLog("主动触发：${event.name}。${effect.summary}")

        // 触发抉择弹窗
        if (event.triggersChoice && event.type == PlayerEventSystem.PlayerEventType.CAREER) {
            getCandidateIdiomsForEvent(IdiomSceneMapper.LifeEventScene.CAREER_FRUSTRATION)
        }
    }

    /**
     * 获取当前模拟速度
     */
    fun getSimulationSpeed(): Double = simulationControlUseCase.getSimulationSpeed()

    /**
     * 设置模拟速度
     */
    fun setSimulationSpeed(speed: Double) {
        simulationControlUseCase.setSimulationSpeed(speed)
    }
}
