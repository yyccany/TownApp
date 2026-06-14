package com.example.townapp.domain.engine

import android.util.Log
import com.example.townapp.core.constants.SimulationConstants
import com.example.townapp.data.CareerPathSystem
import com.example.townapp.data.LifePathData
import com.example.townapp.data.repository.*
import com.example.townapp.domain.spacemodel.SpaceState
import com.example.townapp.domain.spacemodel.SpaceInfluence
import com.example.townapp.domain.bodymodel.BodyCalculation
import com.example.townapp.domain.spiritmodel.IdiomCritiqueManager
import com.example.townapp.data.repository.SimulationRepository
import com.example.townapp.data.DailyFrustrations
import com.example.townapp.data.DailyFrustration
import com.example.townapp.data.FrustrationChoice
import com.example.townapp.data.BeliefState
import com.example.townapp.data.BeliefMilestones
import com.example.townapp.data.BeliefErosionEvents
import com.example.townapp.data.PlayerEventSystem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * 🌟 模拟人生核心引擎
 * 
 * 这是小镇的心脏，负责维护整个模拟循环：
 * 时间流逝 → 状态衰减 → 用户选择 → 后果计算 → 状态更新
 * 
 * 核心特性：
 * - 无目标、开放式、状态驱动
 * - 三种时间模式：前台实时、后台静默、手动快进
 * - 三层状态系统：肉体、空间、精神
 * - 完全复用现有数据库和状态引擎
 */
object SimulationEngine {

    // ==================== 常量定义 ====================
    private const val DEFAULT_SPACE_ID = "room_1500"
    private const val EVENT_HISTORY_MAX_SIZE = 100

    // 事件触发概率常量（引擎特有，未纳入 SimulationConstants）
    private const val COMPANION_PROBABILITY = 0.15
    private const val COGNITIVE_CARD_PROBABILITY = 0.2
    private const val SPOTLIGHT_PROBABILITY = 0.25

    // 时间间隔常量（小时）
    private const val HUNGER_CHECK_INTERVAL = 4
    private const val ENERGY_CHECK_INTERVAL = 4
    private const val ANXIETY_CHECK_INTERVAL = 6

    /** 当前游戏时间 —— 由 Repository 统一管理（不可变快照） */
    private var gameTime: GameTime
        get() = SimulationRepository.gameTimeFlow.value
        set(value) { SimulationRepository.commitGameTime(value) }

    /** 当前玩家状态 —— 由 Repository 统一管理（不可变快照） */
    private var playerState: PlayerState
        get() = SimulationRepository.playerStateFlow.value
        set(value) { SimulationRepository.commitPlayerState(value) }

    /** 当前空间状态 —— 由 Repository 统一管理（不可变快照） */
    private var engineSpaceState: SpaceState
        get() = SimulationRepository.spaceStateFlow.value
        set(value) { SimulationRepository.commitSpaceState(value) }

    /** 模拟是否运行中 —— 由 Repository 统一管理 */
    private var isRunning: Boolean
        get() = SimulationRepository.isRunningFlow.value
        set(value) { SimulationRepository.setRunning(value) }

    /** 当前时间模式 */
    private var currentMode = TimeMode.FOREGROUND

    /** 时间流速倍率 */
    private val TIME_SCALE = mapOf(
        TimeMode.FOREGROUND to 60,    // 现实1分钟 = 游戏1小时
        TimeMode.BACKGROUND to 6,     // 现实1小时 = 游戏6小时
        TimeMode.FAST_FORWARD to 1440 // 一键模拟一整天
    )

    /** 模拟速度倍率（可动态调整） —— 由 Repository 统一管理 */
    private var engineSimulationSpeed: Double
        get() = SimulationRepository.simulationSpeedFlow.value
        set(value) { SimulationRepository.setSpeed(value) }

    /** 复用的随机数生成器（优化性能） */
    private val random = Random()

    /** 事件冷却追踪器：事件ID -> 上次触发天数 */
    private val eventCooldownTracker = mutableMapOf<String, Int>()

    /** 已触发的事件记录（用于成就判定） */
    private val triggeredEventHistory = mutableListOf<String>()

    // ==================== 人生事件系统 ====================
    /** 当前信念状态 */
    private var beliefState: BeliefState = BeliefState()

    /** 日常挫折事件冷却追踪器 */
    private val frustrationCooldownTracker = mutableMapOf<String, Int>()

    /** 日常挫折触发概率常量 */
    private const val DAILY_FRUSTRATION_PROBABILITY = 0.12

    /** 信念消解触发概率基础值 */
    private const val BELief_EROSION_BASE_PROBABILITY = 0.05

    // ==================== 财务系统 ====================
    /** 当前投资金额 */
    private var investmentAmount = 0.0

    /** 上次发工资的天数 */
    private var lastPayDay = -1

    /**
     * 启动模拟引擎
     */
    suspend fun start(mode: TimeMode = TimeMode.FOREGROUND) {
        currentMode = mode
        isRunning = true
        
        when (mode) {
            TimeMode.FOREGROUND -> runForegroundSimulation()
            TimeMode.BACKGROUND -> runBackgroundSimulation()
            TimeMode.FAST_FORWARD -> runBackgroundSimulation()
        }
    }

    /**
     * 停止模拟引擎
     */
    fun stop() {
        isRunning = false
    }

    /**
     * 设置模拟速度倍率
     * @param speed 速度倍率 (0.5-4.0)
     */
    fun setSimulationSpeed(speed: Double) {
        engineSimulationSpeed = max(0.5, min(4.0, speed))
    }

    /**
     * 获取当前模拟速度
     */
    fun getSimulationSpeed(): Double = engineSimulationSpeed

    /**
     * 手动快进模拟
     */
    suspend fun fastForward(days: Int): SimulationResult {
        currentMode = TimeMode.FAST_FORWARD
        val results = mutableListOf<DailySummary>()
        
        for (day in 1..days) {
            simulateDay()
            results.add(generateDailySummary())
        }
        
        return SimulationResult(results)
    }

    /**
     * 前台实时模式：现实30秒 = 游戏1小时（优化后）
     */
    private suspend fun runForegroundSimulation() = withContext(Dispatchers.Default) {
        while (isRunning) {
            // 优化：现实30秒 = 游戏1小时（原来60秒）
            val delayTime = (30000 / engineSimulationSpeed).toLong()
            delay(delayTime)
            advanceTime(1) // 前进1小时
            updateState()
            
            // 检查状态并触发事件
            checkAndTriggerEvents()
        }
    }

    /**
     * 后台静默模式：现实10分钟 = 游戏6小时（优化后）
     */
    private suspend fun runBackgroundSimulation() = withContext(Dispatchers.Default) {
        while (isRunning) {
            // 优化：现实10分钟 = 游戏6小时（原来1小时）
            val delayTime = (600000 / engineSimulationSpeed).toLong()
            delay(delayTime)
            advanceTime(6) // 前进6小时
            updateState()
        }
    }

    /**
     * 模拟一整天
     */
    private suspend fun simulateDay() {
        for (hour in 0..23) {
            advanceTime(1)
            updateState()
        }
    }

    /**
     * 推进游戏时间
     */
    private fun advanceTime(hours: Int) {
        var newHours = gameTime.hours + hours
        var newDays = gameTime.days
        var crossedDay = false
        while (newHours >= 24) {
            newHours -= 24
            newDays++
            crossedDay = true
        }
        gameTime = gameTime.copy(
            hours = newHours,
            days = newDays,
            week = (newDays / 7) + 1,
            dayOfWeek = ((newDays - 1) % 7) + 1
        )
        // 每日重置（仅在跨天时）
        if (crossedDay) {
            playerState = playerState.dailyReset()
        }
    }

    /**
     * 更新状态（核心循环）
     */
    private fun updateState() {
        // 1. 肉体状态自然衰减
        playerState = playerState.copy(
            hunger = max(0.0, playerState.hunger - SimulationConstants.HUNGER_DECAY),
            energy = max(0.0, playerState.energy - SimulationConstants.ENERGY_DECAY)
        )

        // 2. 应用所有可触发数据的影响（空间、事件等）
        applySimulatableDataEffects()

        // 3. 健康状态计算（基于饱腹和精力）
        playerState = playerState.copy(health = BodyCalculation.calculateHealth(playerState))

        // 4. 代际压力累积（如果开启）
        if (playerState.generationalPressureEnabled && SimulationDataSwitch.enableGenerationalEffects) {
            playerState = playerState.copy(
                generationalPressure = min(100.0, playerState.generationalPressure + SimulationConstants.PRESSURE_DECAY)
            )
        }
    }

    /**
     * 应用所有可触发数据的影响
     */
    private fun applySimulatableDataEffects() {
        val allEffects = mutableListOf<StateEffect>()
        val triggeredEventsLog = mutableListOf<String>()
        
        // 1. 空间影响
        applySpaceEffects(allEffects, triggeredEventsLog)
        
        // 2. 事件影响
        applyEventEffects(allEffects, triggeredEventsLog)
        
        // 3. 周期性触发事件
        applyPeriodicEvents(triggeredEventsLog)
        
        // 4. 情绪管理（每小时检查）
        updateMentalState()
        
        // 合并所有影响并应用到状态
        applyCombinedEffects(allEffects)
    }
    
    /**
     * 应用空间影响
     */
    private fun applySpaceEffects(effects: MutableList<StateEffect>, log: MutableList<String>) {
        if (SimulationDataSwitch.enableSpaceEffects) {
            val currentSpace = SpaceConfigRepository.getSpaceById(DEFAULT_SPACE_ID)
            currentSpace?.let {
                val adapter = SpaceDataAdapter(it)
                if (adapter.canTrigger(playerState)) {
                    effects.add(adapter.getStateEffect())
                    log.add("空间: ${adapter.name}")
                    playerState = playerState.copy(dailyEvents = playerState.dailyEvents + adapter.getDisplayText())
                }
            }
        }
    }
    
    /**
     * 应用事件影响（带权重随机选择和冷却机制）
     */
    private fun applyEventEffects(effects: MutableList<StateEffect>, log: MutableList<String>) {
        if (SimulationDataSwitch.enableEventEffects) {
            val allEvents = GameEventRepository.getAllEvents()
            
            // 过滤出可触发的事件（考虑冷却）
            val availableEvents = allEvents
                .filter { isEventOnCooldown(it) == false }
                .map { EventDataAdapter(it) }
                .filter { it.canTrigger(playerState) }
            
            if (availableEvents.isNotEmpty()) {
                val selectedEvent = selectRandomEventByWeight(availableEvents)
                selectedEvent?.let { event ->
                    // 记录触发
                    val gameEvent = allEvents.find { it.id == event.id }
                    gameEvent?.let {
                        eventCooldownTracker[it.id] = gameTime.days
                        triggeredEventHistory.add(it.id)
                        // 限制历史记录大小
                        if (triggeredEventHistory.size > 1000) {
                            triggeredEventHistory.removeAt(0)
                        }
                    }

                    effects.add(event.getStateEffect())
                    log.add("事件: ${event.name}")
                    playerState = playerState.copy(dailyEvents = playerState.dailyEvents + "${event.name}: ${event.getDisplayText()}")
                }
            }
        }
    }
    
    /**
     * 检查事件是否在冷却中
     */
    private fun isEventOnCooldown(event: GameEvent): Boolean {
        val lastTriggerDay = eventCooldownTracker[event.id] ?: return false
        val daysSinceLastTrigger = gameTime.days - lastTriggerDay
        return daysSinceLastTrigger < event.cooldownDays
    }
    
    /**
     * 根据权重随机选择事件
     */
    private fun selectRandomEventByWeight(events: List<ISimulatableData>): ISimulatableData? {
        val totalWeight = events.sumOf { it.getTriggerWeight() }
        var randomValue = random.nextInt(totalWeight)
        
        for (event in events) {
            randomValue -= event.getTriggerWeight()
            if (randomValue <= 0) {
                return event
            }
        }
        return null
    }
    
    /**
     * 应用周期性触发事件
     */
    private fun applyPeriodicEvents(log: MutableList<String>) {
        // 随机微事件（每天中午触发）
        if (gameTime.hours == 12 && random.nextDouble() < SimulationConstants.MICRO_EVENT_PROBABILITY) {
            triggerMicroEvent(log)
        }

        // 随机成语（每天傍晚触发）
        if (gameTime.hours == 18 && random.nextDouble() < SimulationConstants.IDIOM_PROBABILITY) {
            triggerIdiomEvent(log)
        }
        
        // 随机陪伴内容（每6小时触发）
        if (gameTime.hours % 6 == 0 && random.nextDouble() < COMPANION_PROBABILITY) {
            triggerCompanionEvent(log)
        }
        
        // 认知剖析（每周一早上触发）
        if (isMondayMorning()) {
            triggerDissectionEvent(log)
        }
        
        // 认知卡片（每3天上午触发）
        if (gameTime.days % 3 == 0 && gameTime.hours == 10 && random.nextDouble() < COGNITIVE_CARD_PROBABILITY) {
            triggerCognitiveCardEvent(log)
        }
        
        // 模拟场景（每月一次）
        if (isMonthlyTrigger()) {
            triggerScenarioEvent(log)
        }
        
        // 闪光点（每2天下午触发）
        if (gameTime.days % 2 == 0 && gameTime.hours == 16 && random.nextDouble() < SPOTLIGHT_PROBABILITY) {
            triggerSpotlightEvent(log)
        }
        
        // 服装影响（每天早晨）
        if (gameTime.hours == 7) {
            playerState = playerState.copy(clothingBonus = calculateClothingBonus())
        }

        // 交通工具影响（通勤时间）
        if (isCommuteTime()) {
            playerState = playerState.copy(transportCost = calculateTransportCost())
        }

        // 审美影响（每3天晚上）
        if (gameTime.days % 3 == 0 && gameTime.hours == 19 && random.nextDouble() < SimulationConstants.AESTHETIC_PROBABILITY) {
            triggerAestheticBonus(log)
        }

        // 日常挫折事件（每天随机触发）
        if (gameTime.hours == 14 && random.nextDouble() < DAILY_FRUSTRATION_PROBABILITY) {
            triggerDailyFrustrationEvent(log)
        }

        // 信念消解事件（每周触发）
        if (gameTime.days % 7 == 0 && gameTime.hours == 20 && beliefState.isStillHolding) {
            triggerBeliefErosionEvent(log)
        }
    }
    
    /**
     * 判断是否是周一早上
     */
    private fun isMondayMorning(): Boolean {
        return gameTime.days % 7 == 1 && gameTime.hours == 9
    }
    
    /**
     * 判断是否是每月触发时间
     */
    private fun isMonthlyTrigger(): Boolean {
        return gameTime.days % 30 == 0 && gameTime.hours == 14
    }
    
    /**
     * 判断是否是通勤时间
     */
    private fun isCommuteTime(): Boolean {
        return gameTime.hours == 8 || gameTime.hours == 18
    }
    
    /**
     * 触发微事件
     */
    private fun triggerMicroEvent(log: MutableList<String>) {
        IdiomCritiqueManager.triggerMicroEvent()?.let { result ->
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + result.eventText)
            log.add(result.logText)
            playerState = IdiomCritiqueManager.applySpiritEventResult(playerState, result)
        }
    }

    /**
     * 触发成语事件
     */
    private fun triggerIdiomEvent(log: MutableList<String>) {
        IdiomCritiqueManager.triggerIdiomEvent()?.let { result ->
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + result.eventText)
            log.add(result.logText)
            playerState = IdiomCritiqueManager.applySpiritEventResult(playerState, result)
        }
    }

    /**
     * 触发陪伴内容
     */
    private fun triggerCompanionEvent(log: MutableList<String>) {
        IdiomCritiqueManager.triggerCompanionEvent()?.let { result ->
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + result.eventText)
            log.add(result.logText)
            playerState = IdiomCritiqueManager.applySpiritEventResult(playerState, result)
        }
    }

    /**
     * 触发认知剖析
     */
    private fun triggerDissectionEvent(log: MutableList<String>) {
        IdiomCritiqueManager.triggerDissectionEvent()?.let { result ->
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + result.eventText)
            log.add(result.logText)
            playerState = IdiomCritiqueManager.applySpiritEventResult(playerState, result)
        }
    }

    /**
     * 触发认知卡片
     */
    private fun triggerCognitiveCardEvent(log: MutableList<String>) {
        IdiomCritiqueManager.triggerCognitiveCardEvent()?.let { result ->
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + result.eventText)
            log.add(result.logText)
            playerState = IdiomCritiqueManager.applySpiritEventResult(playerState, result)
        }
    }

    /**
     * 触发模拟场景
     */
    private fun triggerScenarioEvent(log: MutableList<String>) {
        IdiomCritiqueManager.triggerScenarioEvent()?.let { result ->
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + result.eventText)
            log.add(result.logText)
            playerState = IdiomCritiqueManager.applySpiritEventResult(playerState, result)
        }
    }

    /**
     * 触发闪光点
     */
    private fun triggerSpotlightEvent(log: MutableList<String>) {
        IdiomCritiqueManager.triggerSpotlightEvent()?.let { result ->
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + result.eventText)
            log.add(result.logText)
            playerState = IdiomCritiqueManager.applySpiritEventResult(playerState, result)
        }
    }
    
    /**
     * 触发审美加成
     */
    private fun triggerAestheticBonus(log: MutableList<String>) {
        val aestheticBonus = calculateAestheticBonus()
        playerState = playerState.copy(happiness = min(100.0, playerState.happiness + aestheticBonus))
        log.add("审美加成")
    }
    
    /**
     * 合并并应用所有状态影响
     */
    private fun applyCombinedEffects(effects: List<StateEffect>) {
        val combinedEffect = effects.reduceOrNull { acc, effect -> acc + effect }
        combinedEffect?.let {
            applyStateEffect(it)
        }
    }

    /**
     * 将状态影响应用到玩家状态
     */
    private fun applyStateEffect(effect: StateEffect) {
        playerState = playerState.copy(
            hunger = max(0.0, min(100.0, playerState.hunger + effect.hunger)),
            energy = max(0.0, min(100.0, playerState.energy + effect.energy)),
            health = max(0.0, min(100.0, playerState.health + effect.health)),
            happiness = max(0.0, min(100.0, playerState.happiness + effect.happiness)),
            anxiety = max(0.0, min(100.0, playerState.anxiety + effect.anxiety)),
            loneliness = max(0.0, min(100.0, playerState.loneliness + effect.loneliness)),
            control = max(0.0, min(100.0, playerState.control + effect.control)),
            money = playerState.money + effect.money,
            skillLevel = max(0.0, min(100.0, playerState.skillLevel + effect.skillLevel)),
            generationalPressure = max(0.0, min(100.0, playerState.generationalPressure + effect.generationalPressure))
        )
    }

    /**
     * 切换居住空间
     */
    fun changeSpace(spaceId: String): ActionResult {
        val space = SpaceConfigRepository.getSpaceById(spaceId)
        if (space == null) {
            return ActionResult(success = false, message = "找不到这个房间")
        }
        
        // 检查余额是否足够支付第一个月房租
        if (playerState.money < space.price) {
            return ActionResult(success = false, message = "余额不足，无法租住")
        }
        
        playerState = playerState.copy(money = playerState.money - space.price)

        // 更新空间状态
        engineSpaceState = SpaceState(
            rent = space.price,
            light = when {
                space.stateEffect.spiritLight < 0.7 -> 30
                space.stateEffect.spiritLight < 0.9 -> 60
                space.stateEffect.spiritLight >= 1.1 -> 90
                else -> 70
            },
            noise = when {
                space.stateEffect.spiritNoise < -3 -> 70
                space.stateEffect.spiritNoise < -1 -> 50
                space.stateEffect.spiritNoise >= 0 -> 20
                else -> 40
            },
            cleanliness = 70,
            ventilation = when {
                space.stateEffect.spiritLight >= 1.1 -> 80
                space.stateEffect.spiritLight < 0.7 -> 40
                else -> 60
            },
            size = space.area
        )
        
        return ActionResult(
            success = true,
            message = "成功租住${space.name}，花费¥${String.format("%.2f", space.price)}",
            sparkle = "选择一个好的居住环境，对身心健康很重要"
        )
    }

    /**
     * 计算服装加成
     */
    private fun calculateClothingBonus(): Double {
        val clothingCategories = DataIntegrationManager.getAllClothingCategories()
        if (clothingCategories.isEmpty()) return 0.0
        
        // 根据天气和服装类型计算加成
        val weatherBonus = when {
            gameTime.season == "summer" -> 1.5
            gameTime.season == "winter" -> 2.0
            else -> 1.0
        }
        
        return clothingCategories.size * 0.5 * weatherBonus
    }
    
    /**
     * 计算交通成本
     */
    private fun calculateTransportCost(): Double {
        val vehicles = DataIntegrationManager.getAllVehicles()
        if (vehicles.isEmpty()) return 5.0 // 默认步行成本
        
        // 简单计算：选择最便宜的交通工具
        return vehicles.minByOrNull { it.costPerKm }?.costPerKm ?: 5.0
    }
    
    /**
     * 计算审美加成
     */
    private fun calculateAestheticBonus(): Double {
        // 根据当前空间和物品计算审美加成
        val lightBonus = if (engineSpaceState.light > 70) 1.5 else 0.5
        val cleanlinessBonus = if (engineSpaceState.cleanliness > 60) 1.0 else 0.0
        
        return lightBonus + cleanlinessBonus
    }
    
    /**
     * 更新精神状态
     */
    private fun updateMentalState() {
        // 情绪管理：根据当前状态调整
        val anxietyReduction = if (playerState.happiness > 60) 0.5 else 0.0
        val lonelinessReduction = if (gameTime.hours in 19..21) 1.0 else 0.0
        playerState = playerState.copy(
            anxiety = max(0.0, playerState.anxiety - anxietyReduction),
            loneliness = max(0.0, playerState.loneliness - lonelinessReduction)
        )
    }
    
    /**
     * 检查并记录事件（静默模式，不自动弹窗）
     * 文案原则：温和、不催促、引导自我关怀，绝不用"警告/危险/必须"等压迫性语言
     */
    private fun checkAndTriggerEvents() {
        val events = mutableListOf<String>()

        // 饥饿事件 —— 温和提醒进食
        if (gameTime.hours % HUNGER_CHECK_INTERVAL == 0 && playerState.hunger < 20) {
            events.add(listOf(
                "胃有点空，吃点东西会舒服一些",
                "身体在轻声说：该喂它一点了",
                "哪怕简单吃一口，也是对今天的自己温柔一点",
                "饿了就先吃饭，其他的事可以等一等"
            ).random())
        }

        // 困倦事件 —— 引导休息，不制造焦虑
        if (gameTime.hours % ENERGY_CHECK_INTERVAL == 0 && playerState.energy < 20) {
            events.add(listOf(
                "眼皮有点沉了，闭上眼睛歇一歇吧",
                "累了就停下来，不用硬撑的",
                "休息不是偷懒，是身体在请求你照顾它",
                "此刻什么都不做，也很好",
                "你不需要一直运转，停下来是被允许的"
            ).random())
        }

        // 焦虑事件 —— 温和安抚，强调"这样也可以"
        if (gameTime.hours % ANXIETY_CHECK_INTERVAL == 0 && playerState.anxiety > 80) {
            events.add(listOf(
                "心里有点乱，没关系，这只是暂时的",
                "你现在的感觉，很多人都经历过，你不是一个人",
                "深呼吸，慢慢呼气，不用急着好起来",
                "焦虑的时候，哪怕只是坐着发呆，也已经很好了",
                "你不需要马上解决问题，先让自己喘口气"
            ).random())
        }

        // 房租到期提醒（每月一次）
        if (isMonthlyTrigger() && gameTime.hours == 0) {
            events.add(listOf(
                "又到交房租的时候了，钱够的话就不慌",
                "房租日到了，这个月也好好住过来了"
            ).random())
        }

        // 静默记录事件到历史列表，不触发弹窗
        if (events.isNotEmpty()) {
            SimulationRepository.eventHistory.addAll(events)
            // 保留最近N条事件记录
            while (SimulationRepository.eventHistory.size > EVENT_HISTORY_MAX_SIZE) {
                SimulationRepository.eventHistory.removeAt(0)
            }
        }
    }

    /**
     * 执行吃饭动作
     */
    fun eat(foodId: Int): ActionResult {
        val food = UnifiedFoodRepository.getFoodById(foodId)
        if (food == null) {
            return ActionResult(success = false, message = "找不到这个食物")
        }

        // 获取营养数据
        val nutrition = NutritionRiskCache.getNutrition(foodId)
        // 获取风险数据
        val risk = NutritionRiskCache.getRisk(foodId)

        // 计算饱腹恢复（基于营养分数）
        val hungerRestore = food.nutritionalScore * 0.8
        var newHunger = min(100.0, playerState.hunger + hungerRestore)

        // 计算花费
        val cost = food.pricePer100g * (food.typicalServing / 100.0)
        var newMoney = playerState.money - cost

        // 营养影响（蛋白质、维生素等）
        val proteinBonus = nutrition.proteinPer100g * 0.1
        val vitaminBonus = nutrition.vitaminCMg * 0.05
        var newHealth = kotlin.math.min(100.0, playerState.health + proteinBonus + vitaminBonus)
        var newAnxiety = playerState.anxiety

        // 风险影响（农药残留、重金属等）
        if (risk.riskLevel == "HIGH" || risk.riskLevel == "EXTREME") {
            newHealth = kotlin.math.max(0.0, newHealth - risk.totalRiskScore * 0.1)
            newAnxiety = kotlin.math.min(100.0, newAnxiety + 5.0)
        }

        // 精神影响
        val value = UnifiedFoodRepository.getDailyValue(foodId)
        val newHappiness = min(100.0, playerState.happiness + (value.mentalGain?.happiness ?: 0.0))

        playerState = playerState.copy(
            hunger = newHunger,
            money = newMoney,
            health = newHealth,
            anxiety = newAnxiety,
            happiness = newHappiness
        )
        
        // 构建消息
        val message = buildString {
            append("吃了${food.name}")
            append("，饱腹+${String.format("%.1f", hungerRestore)}")
            append("，花费¥${String.format("%.2f", cost)}")
            if (proteinBonus > 0) {
                append("，蛋白+${String.format("%.1f", proteinBonus)}")
            }
            if (risk.riskLevel == "HIGH") {
                append("（高风险食物）")
            }
        }

        return ActionResult(
            success = true,
            message = message,
            sparkle = value.sparkle
        )
    }

    /**
     * 执行睡觉动作
     */
    fun sleep(hours: Int): ActionResult {
        // 精力恢复
        val energyRestore = hours * 15.0
        playerState = playerState.copy(
            energy = min(100.0, playerState.energy + energyRestore),
            happiness = min(100.0, playerState.happiness + hours * 2.0),
            anxiety = max(0.0, playerState.anxiety - hours * 3.0)
        )

        // 推进时间
        advanceTime(hours)

        return ActionResult(
            success = true,
            message = "睡了${hours}小时，精力恢复了${String.format("%.1f", energyRestore)}"
        )
    }

    /**
     * 执行上班动作
     */
    fun work(hours: Int): ActionResult {
        // 获得收入
        val income = playerState.skillLevel * 20 * hours
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - hours * 5.0),
            money = playerState.money + income,
            happiness = max(0.0, playerState.happiness - hours * 1.0),
            anxiety = min(100.0, playerState.anxiety + hours * 0.5)
        )

        // 推进时间
        advanceTime(hours)

        return ActionResult(
            success = true,
            message = "工作了${hours}小时，赚了¥${String.format("%.2f", income)}"
        )
    }

    /**
     * 执行学习动作
     */
    fun study(hours: Int): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - hours * 8.0),
            skillLevel = min(100.0, playerState.skillLevel + hours * 2.0),
            happiness = min(100.0, playerState.happiness + hours * 1.0)
        )
        advanceTime(hours)
        return ActionResult(
            success = true,
            message = "学习了${hours}小时，技能提升了${String.format("%.1f", hours * 2.0)}"
        )
    }

    fun idle(hours: Int): ActionResult {
        playerState = playerState.copy(
            energy = min(100.0, playerState.energy + hours * 3.0),
            anxiety = max(0.0, playerState.anxiety - hours * 2.0),
            loneliness = max(0.0, playerState.loneliness - hours * 1.0)
        )
        advanceTime(hours)
        return ActionResult(
            success = true,
            message = "发呆了${hours}小时，感觉平静了一些"
        )
    }

    fun payRent(): ActionResult {
        val rent = engineSpaceState.rent
        if (playerState.money < rent) {
            return ActionResult(success = false, message = "钱不够交房租")
        }
        playerState = playerState.copy(money = playerState.money - rent)
        return ActionResult(
            success = true,
            message = "交了房租¥${String.format("%.2f", rent)}"
        )
    }

    fun walk(hours: Int): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - hours * 2.0),
            anxiety = max(0.0, playerState.anxiety - hours * 4.0),
            happiness = min(100.0, playerState.happiness + hours * 3.0),
            health = min(100.0, playerState.health + hours * 1.5)
        )
        advanceTime(hours)
        return ActionResult(
            success = true,
            message = "散步了${hours}小时，心情平静了很多"
        )
    }

    fun socialize(hours: Int): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - hours * 3.0),
            loneliness = max(0.0, playerState.loneliness - hours * 6.0),
            happiness = min(100.0, playerState.happiness + hours * 4.0),
            anxiety = max(0.0, playerState.anxiety - hours * 2.0)
        )
        advanceTime(hours)
        return ActionResult(
            success = true,
            message = "和朋友聊了${hours}小时，感到很温暖"
        )
    }

    fun meditate(minutes: Int): ActionResult {
        val hours = minutes / 60.0
        val effectiveHours = if (hours.toInt() < 1) 1 else hours.toInt()
        playerState = playerState.copy(
            energy = min(100.0, playerState.energy + effectiveHours * 5.0),
            anxiety = max(0.0, playerState.anxiety - effectiveHours * 5.0),
            happiness = min(100.0, playerState.happiness + effectiveHours * 2.0)
        )
        advanceTime(effectiveHours)
        return ActionResult(
            success = true,
            message = "冥想了${minutes}分钟，内心平静了很多"
        )
    }

    fun read(hours: Int): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - hours * 2.0),
            skillLevel = min(100.0, playerState.skillLevel + hours * 1.5),
            happiness = min(100.0, playerState.happiness + hours * 2.0),
            anxiety = max(0.0, playerState.anxiety - hours * 3.0),
            control = min(100.0, playerState.control + hours * 2.0)
        )
        advanceTime(hours)
        return ActionResult(
            success = true,
            message = "阅读了${hours}小时，内心充实了很多"
        )
    }

    fun listenMusic(hours: Int): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - hours * 1.0),
            happiness = min(100.0, playerState.happiness + hours * 3.0),
            anxiety = max(0.0, playerState.anxiety - hours * 2.0),
            loneliness = max(0.0, playerState.loneliness - hours * 1.0)
        )
        advanceTime(hours)
        return ActionResult(
            success = true,
            message = "听了${hours}小时音乐，心情舒缓了"
        )
    }

    fun exercise(hours: Int): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - hours * 8.0),
            health = min(100.0, playerState.health + hours * 6.0),
            happiness = min(100.0, playerState.happiness + hours * 3.0),
            anxiety = max(0.0, playerState.anxiety - hours * 4.0)
        )
        advanceTime(hours)
        return ActionResult(
            success = true,
            message = "运动了${hours}小时，身体很有活力"
        )
    }

    fun cook(hours: Int): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - hours * 3.0),
            skillLevel = min(100.0, playerState.skillLevel + hours * 1.0),
            happiness = min(100.0, playerState.happiness + hours * 4.0),
            control = min(100.0, playerState.control + hours * 2.0)
        )
        advanceTime(hours)
        return ActionResult(
            success = true,
            message = "做了${hours}小时的饭，很有成就感"
        )
    }

    fun organize(hours: Int): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - hours * 4.0),
            happiness = min(100.0, playerState.happiness + hours * 5.0),
            anxiety = max(0.0, playerState.anxiety - hours * 3.0),
            control = min(100.0, playerState.control + hours * 3.0)
        )
        advanceTime(hours)
        return ActionResult(
            success = true,
            message = "整理了${hours}小时房间，环境清爽了"
        )
    }

    fun watchMovie(hours: Int): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - hours * 1.5),
            happiness = min(100.0, playerState.happiness + hours * 2.5),
            anxiety = max(0.0, playerState.anxiety - hours * 2.0),
            loneliness = max(0.0, playerState.loneliness - hours * 1.0)
        )
        advanceTime(hours)
        return ActionResult(
            success = true,
            message = "看了${hours}小时电影，放松了身心"
        )
    }

    fun teaBreak(minutes: Int): ActionResult {
        val hours = minutes / 60.0
        val effectiveHours = max(1, hours.toInt())
        playerState = playerState.copy(
            energy = min(100.0, playerState.energy + effectiveHours * 2.0),
            happiness = min(100.0, playerState.happiness + effectiveHours * 2.0),
            anxiety = max(0.0, playerState.anxiety - effectiveHours * 3.0)
        )
        advanceTime(effectiveHours)
        return ActionResult(
            success = true,
            message = "喝了${minutes}分钟茶，内心平静了"
        )
    }

    fun tendPlant(): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - 1.0),
            happiness = min(100.0, playerState.happiness + 5.0),
            anxiety = max(0.0, playerState.anxiety - 3.0),
            control = min(100.0, playerState.control + 3.0)
        )
        advanceTime(1)
        return ActionResult(
            success = true,
            message = "照顾了一下植物，很治愈"
        )
    }

    fun journal(writeTime: Int): ActionResult {
        playerState = playerState.copy(
            energy = max(0.0, playerState.energy - writeTime * 0.5),
            control = min(100.0, playerState.control + writeTime * 1.5),
            anxiety = max(0.0, playerState.anxiety - writeTime * 1.0),
            happiness = min(100.0, playerState.happiness + writeTime * 0.5)
        )
        advanceTime(writeTime)
        return ActionResult(
            success = true,
            message = "写了${writeTime}分钟日记，内心清晰了一些"
        )
    }

    /**
     * 生成每日总结
     */
    private fun generateDailySummary(): DailySummary {
        return DailySummary(
            day = gameTime.days,
            moneyChange = playerState.dailyMoneyChange,
            maxHunger = playerState.dailyMaxHunger,
            minEnergy = playerState.dailyMinEnergy,
            avgHappiness = playerState.dailyAvgHappiness,
            events = playerState.dailyEvents
        )
    }

    /**
     * 获取当前状态快照
     */
    fun getStateSnapshot(): StateSnapshot {
        return StateSnapshot(
            gameTime = gameTime.copy(),
            playerState = playerState.copy(),
            spaceState = engineSpaceState.copy()
        )
    }

    /**
     * 设置空间状态
     */
    fun setSpaceState(newSpace: SpaceState) {
        engineSpaceState = newSpace
    }

    /**
     * 重置模拟（默认20岁开局）
     */
    /**
     * 更新空间状态参数
     */
    fun updateSpaceState(rent: Double, salary: Double, commuteMinutes: Int) {
        engineSpaceState = engineSpaceState.copy(
            rent = rent,
            salary = salary,
            commuteMinutesOneWay = commuteMinutes
        )
    }

    fun reset() {
        resetWithAge(20)
    }

    /**
     * 重置模拟并设置开局年龄和职业
     * @param startAge 开局年龄（20-45岁）
     * @param career 职业选项
     */
    fun resetWithCareer(startAge: Int, career: CareerPathSystem.CareerOption) {
        SimulationRepository.fullReset()
        gameTime = GameTime()
        playerState = initializePlayerStateWithCareer(startAge, career)
        engineSpaceState = SpaceState()
        isRunning = false
    }

    /**
     * 重置模拟并设置开局年龄（使用默认职业）
     * @param startAge 开局年龄（20-45岁）
     */
    fun resetWithAge(startAge: Int) {
        val defaultCareer = CareerPathSystem.allCareers.first()
        resetWithCareer(startAge, defaultCareer)
    }

    /**
     * 根据开局年龄和职业初始化玩家状态
     */
    private fun initializePlayerStateWithCareer(startAge: Int, career: CareerPathSystem.CareerOption): PlayerState {
        val clampedAge = max(career.minAge, max(20, min(45, startAge)))
        val workingYears = clampedAge - 22  // 假设22岁开始工作
        
        // 基于职业参数计算初始状态
        val baseMoney = if (workingYears > 0) {
            career.baseSalary * 12 * workingYears * 0.3  // 假设存下30%的收入
        } else {
            career.baseSalary * 3  // 新人有3个月生活费
        }
        
        val baseHealth = 90.0 - career.fatiguePerDay * 365 * max(0, workingYears) / 100.0
        val baseAnxiety = 20.0 + career.overtimeRate * 30.0 + career.layoffRisk * 20.0
        
        return PlayerState(
            careerId = career.id,
            age = clampedAge,
            workingYears = max(0, workingYears),
            hunger = 80.0,
            energy = max(60.0, 90.0 - career.fatiguePerDay * 10.0),
            health = max(50.0, baseHealth),
            happiness = max(40.0, 70.0 - career.overtimeRate * 20.0),
            anxiety = min(80.0, baseAnxiety),
            loneliness = 20.0 + if (career.overtimeRate > 0.5) 15.0 else 0.0,
            control = max(30.0, 60.0 - career.layoffRisk * 30.0),
            money = max(1000.0, baseMoney - career.startupCost),
            skillLevel = 30.0 + max(0, workingYears) * 3.0 + if (career.pathType == CareerPathSystem.CareerPathType.CORPORATE) 20.0 else 0.0,
            generationalPressure = 10.0 + max(0, workingYears) * 2.0,
            hasFamily = clampedAge >= 28 && Math.random() > 0.5,
            familyPressure = if (clampedAge >= 28 && Math.random() > 0.5) 15.0 else 0.0,
            housingDebt = if (clampedAge >= 30 && Math.random() > 0.6) 50000.0 else 0.0
        )
    }

    /**
     * 重置模拟并设置人生路径
     * @param startAge 开局年龄
     * @param lifePathId 人生路径ID
     */
    fun resetWithLifePath(startAge: Int, lifePathId: String) {
        SimulationRepository.fullReset()
        gameTime = GameTime()
        playerState = initializePlayerStateWithLifePath(startAge, lifePathId)
        engineSpaceState = SpaceState()
        isRunning = false
    }

    /**
     * 根据人生路径初始化玩家状态
     */
    private fun initializePlayerStateWithLifePath(startAge: Int, lifePathId: String): PlayerState {
        val clampedAge = max(20, min(45, startAge))
        
        return when (lifePathId) {
            // 外出打工青年：存款少、孤独高、健康一般
            "migrant_youth" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "freelance_dev",
                age = clampedAge,
                workingYears = clampedAge - 20,
                hunger = 70.0,
                energy = 65.0,
                health = 60.0,
                happiness = 50.0,
                anxiety = 45.0,
                loneliness = 60.0,
                control = 40.0,
                money = 3000.0 + (clampedAge - 20) * 2000.0,
                skillLevel = 25.0,
                generationalPressure = 30.0,
                hasFamily = false,
                familyPressure = 20.0,
                housingDebt = 0.0
            )
            
            // 全职妈妈：无收入但家庭完整、压力大
            "housewife" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "teacher",
                age = clampedAge,
                workingYears = 0,
                hunger = 75.0,
                energy = 55.0,
                health = 65.0,
                happiness = 55.0,
                anxiety = 50.0,
                loneliness = 45.0,
                control = 35.0,
                money = 5000.0,
                skillLevel = 35.0,
                generationalPressure = 40.0,
                hasFamily = true,
                familyPressure = 35.0,
                housingDebt = 0.0
            )
            
            // 外卖骑手：体力好、收入中等、风险高
            "delivery_rider" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "freelance_dev",
                age = clampedAge,
                workingYears = clampedAge - 22,
                hunger = 70.0,
                energy = 70.0,
                health = 65.0,
                happiness = 55.0,
                anxiety = 40.0,
                loneliness = 35.0,
                control = 50.0,
                money = 4000.0 + (clampedAge - 22) * 3000.0,
                skillLevel = 20.0,
                generationalPressure = 25.0,
                hasFamily = clampedAge >= 28,
                familyPressure = if (clampedAge >= 28) 20.0 else 0.0,
                housingDebt = 0.0
            )
            
            // 应届毕业生：充满希望但存款极少
            "fresh_graduate" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "civil_servant",
                age = clampedAge,
                workingYears = 0,
                hunger = 80.0,
                energy = 85.0,
                health = 85.0,
                happiness = 65.0,
                anxiety = 50.0,
                loneliness = 30.0,
                control = 45.0,
                money = 2000.0,
                skillLevel = 30.0,
                generationalPressure = 20.0,
                hasFamily = false,
                familyPressure = 0.0,
                housingDebt = 0.0
            )
            
            // 工地工人：体力透支、收入中等、孤独
            "construction_worker" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "state_enterprise",
                age = clampedAge,
                workingYears = clampedAge - 18,
                hunger = 75.0,
                energy = 60.0,
                health = 55.0,
                happiness = 50.0,
                anxiety = 35.0,
                loneliness = 55.0,
                control = 45.0,
                money = 5000.0 + (clampedAge - 18) * 4000.0,
                skillLevel = 30.0,
                generationalPressure = 30.0,
                hasFamily = clampedAge >= 25,
                familyPressure = if (clampedAge >= 25) 25.0 else 0.0,
                housingDebt = 0.0
            )
            
            // 小店店主：收入波动、压力大但有掌控感
            "shop_owner" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "small_shop",
                age = clampedAge,
                workingYears = clampedAge - 24,
                hunger = 75.0,
                energy = 65.0,
                health = 65.0,
                happiness = 55.0,
                anxiety = 55.0,
                loneliness = 30.0,
                control = 55.0,
                money = 10000.0 + (clampedAge - 24) * 5000.0,
                skillLevel = 40.0,
                generationalPressure = 35.0,
                hasFamily = true,
                familyPressure = 25.0,
                housingDebt = 20000.0
            )
            
            // 全职儿女：低压力但迷茫
            "adult_child" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "teacher",
                age = clampedAge,
                workingYears = 0,
                hunger = 80.0,
                energy = 75.0,
                health = 75.0,
                happiness = 60.0,
                anxiety = 40.0,
                loneliness = 35.0,
                control = 35.0,
                money = 8000.0,
                skillLevel = 30.0,
                generationalPressure = 35.0,
                hasFamily = true,
                familyPressure = 20.0,
                housingDebt = 0.0
            )
            
            // 退休工人：健康下降、收入固定、孤独
            "retired_worker" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "civil_servant",
                age = clampedAge,
                workingYears = clampedAge - 20,
                hunger = 70.0,
                energy = 55.0,
                health = 50.0,
                happiness = 65.0,
                anxiety = 25.0,
                loneliness = 55.0,
                control = 60.0,
                money = 80000.0 + (clampedAge - 60) * 10000.0,
                skillLevel = 50.0,
                generationalPressure = 15.0,
                hasFamily = true,
                familyPressure = 10.0,
                housingDebt = 0.0
            )
            
            // 自由职业者：收入波动、自由但焦虑
            "freelancer" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "freelance_dev",
                age = clampedAge,
                workingYears = clampedAge - 22,
                hunger = 75.0,
                energy = 70.0,
                health = 70.0,
                happiness = 60.0,
                anxiety = 50.0,
                loneliness = 40.0,
                control = 55.0,
                money = 8000.0 + (clampedAge - 22) * 4000.0,
                skillLevel = 55.0,
                generationalPressure = 25.0,
                hasFamily = clampedAge >= 28,
                familyPressure = if (clampedAge >= 28) 15.0 else 0.0,
                housingDebt = 0.0
            )
            
            // 基层公务员：稳定但压抑
            "civil_servant" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "civil_servant",
                age = clampedAge,
                workingYears = clampedAge - 22,
                hunger = 80.0,
                energy = 75.0,
                health = 75.0,
                happiness = 60.0,
                anxiety = 35.0,
                loneliness = 30.0,
                control = 50.0,
                money = 15000.0 + (clampedAge - 22) * 8000.0,
                skillLevel = 45.0,
                generationalPressure = 30.0,
                hasFamily = clampedAge >= 28,
                familyPressure = if (clampedAge >= 28) 20.0 else 0.0,
                housingDebt = 50000.0
            )
            
            // 优渥家境青年：存款多但迷茫
            "affluent_youth" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "finance_analyst",
                age = clampedAge,
                workingYears = 0,
                hunger = 85.0,
                energy = 80.0,
                health = 80.0,
                happiness = 55.0,
                anxiety = 45.0,
                loneliness = 40.0,
                control = 40.0,
                money = 50000.0,
                skillLevel = 40.0,
                generationalPressure = 35.0,
                hasFamily = true,
                familyPressure = 25.0,
                housingDebt = 0.0
            )
            
            // 天赋高智商青年：技能高但压力大
            "gifted_youth" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "programmer",
                age = clampedAge,
                workingYears = 0,
                hunger = 80.0,
                energy = 75.0,
                health = 75.0,
                happiness = 55.0,
                anxiety = 55.0,
                loneliness = 35.0,
                control = 50.0,
                money = 10000.0,
                skillLevel = 60.0,
                generationalPressure = 30.0,
                hasFamily = false,
                familyPressure = 15.0,
                housingDebt = 0.0
            )
            
            // 先天体魄强健的乡村青年：健康极佳、收入低
            "strong_rural" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "state_enterprise",
                age = clampedAge,
                workingYears = clampedAge - 18,
                hunger = 80.0,
                energy = 90.0,
                health = 95.0,
                happiness = 65.0,
                anxiety = 20.0,
                loneliness = 30.0,
                control = 60.0,
                money = 3000.0 + (clampedAge - 18) * 2000.0,
                skillLevel = 25.0,
                generationalPressure = 15.0,
                hasFamily = true,
                familyPressure = 10.0,
                housingDebt = 0.0
            )
            
            // 先天肢体残障青年：坚韧、收入低但控制感强
            "disabled_youth" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "freelance_dev",
                age = clampedAge,
                workingYears = clampedAge - 22,
                hunger = 75.0,
                energy = 60.0,
                health = 50.0,
                happiness = 60.0,
                anxiety = 40.0,
                loneliness = 45.0,
                control = 65.0,
                money = 5000.0 + (clampedAge - 22) * 2500.0,
                skillLevel = 45.0,
                generationalPressure = 20.0,
                hasFamily = clampedAge >= 28,
                familyPressure = if (clampedAge >= 28) 15.0 else 0.0,
                housingDebt = 0.0
            )
            
            // 回望童年的自己（特殊路径，默认用应届毕业生参数）
            "childhood_self" -> PlayerState(
                lifePathId = lifePathId,
                careerId = "civil_servant",
                age = clampedAge,
                workingYears = 0,
                hunger = 80.0,
                energy = 85.0,
                health = 85.0,
                happiness = 60.0,
                anxiety = 40.0,
                loneliness = 35.0,
                control = 45.0,
                money = 2000.0,
                skillLevel = 25.0,
                generationalPressure = 20.0,
                hasFamily = true,
                familyPressure = 15.0,
                housingDebt = 0.0
            )
            
            // 默认：应届毕业生
            else -> PlayerState(
                lifePathId = lifePathId,
                careerId = "civil_servant",
                age = clampedAge,
                workingYears = 0,
                hunger = 80.0,
                energy = 85.0,
                health = 85.0,
                happiness = 65.0,
                anxiety = 45.0,
                loneliness = 30.0,
                control = 50.0,
                money = 2000.0,
                skillLevel = 30.0,
                generationalPressure = 20.0,
                hasFamily = false,
                familyPressure = 0.0,
                housingDebt = 0.0
            )
        }
    }

    /**
     * 事件回调
     */
    var onEventsTriggered: ((List<String>) -> Unit)? = null

    /** 获取事件历史 */
    fun getEventHistory(): List<String> = SimulationRepository.eventHistory.toList()

    /** 清空事件历史 */
    fun clearEventHistory() {
        SimulationRepository.eventHistory.clear()
    }

    // ==================== 日常挫折事件系统 ====================

    /**
     * 触发日常挫折事件
     */
    private fun triggerDailyFrustrationEvent(log: MutableList<String>) {
        // 过滤出不在冷却中的事件
        val availableFrustrations = DailyFrustrations.allFrustrations
            .filter { isFrustrationOnCooldown(it) == false }

        if (availableFrustrations.isEmpty()) return

        // 根据权重随机选择一个挫折事件
        val selectedFrustration = selectFrustrationByWeight(availableFrustrations)
        selectedFrustration?.let { frustration ->
            // 记录触发
            frustrationCooldownTracker[frustration.id] = gameTime.days

            // 应用基础效果
            playerState = playerState.copy(
                energy = max(0.0, playerState.energy - frustration.fatigueChange),
                happiness = max(0.0, min(100.0, playerState.happiness + frustration.moodChange)),
                anxiety = max(0.0, min(100.0, playerState.anxiety + if (frustration.moodChange < 0) -frustration.moodChange * 0.5 else 0.0))
            )

            // 记录事件到日志
            val eventText = "【日常挫折】${frustration.title}: ${frustration.triggerHint}"
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + eventText)
            log.add("挫折: ${frustration.title}")

            // 记录小镇评述
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + "小镇评述：${frustration.townCommentary}")
        }
    }

    /**
     * 检查挫折事件是否在冷却中
     */
    private fun isFrustrationOnCooldown(frustration: DailyFrustration): Boolean {
        val lastTriggerDay = frustrationCooldownTracker[frustration.id] ?: return false
        val daysSinceLastTrigger = gameTime.days - lastTriggerDay
        return daysSinceLastTrigger < 3  // 冷却3天
    }

    /**
     * 根据权重随机选择挫折事件
     */
    private fun selectFrustrationByWeight(frustrations: List<DailyFrustration>): DailyFrustration? {
        val totalWeight = frustrations.sumOf { it.triggerWeight }
        var randomValue = random.nextInt(totalWeight)

        for (frustration in frustrations) {
            randomValue -= frustration.triggerWeight
            if (randomValue <= 0) {
                return frustration
            }
        }
        return null
    }

    /**
     * 处理玩家对挫折事件的应对选择
     */
    fun handleFrustrationChoice(frustrationId: String, choiceIndex: Int): ActionResult {
        val frustration = DailyFrustrations.findById(frustrationId)
        if (frustration == null) {
            return ActionResult(success = false, message = "找不到这个挫折事件")
        }

        val choice = frustration.choices.getOrNull(choiceIndex)
        if (choice == null) {
            return ActionResult(success = false, message = "无效的应对选择")
        }

        // 应用应对效果
        playerState = playerState.copy(
            energy = max(0.0, min(100.0, playerState.energy + choice.extraFatigue)),
            happiness = max(0.0, min(100.0, playerState.happiness + choice.extraMood)),
            anxiety = max(0.0, min(100.0, playerState.anxiety + if (choice.extraMood < 0) -choice.extraMood * 0.3 else 0.0))
        )

        // 记录应对结果
        val responseText = "应对：${choice.label} - ${choice.responseText}"
        playerState = playerState.copy(dailyEvents = playerState.dailyEvents + responseText)

        return ActionResult(
            success = true,
            message = choice.responseText,
            sparkle = frustration.sparkle
        )
    }

    // ==================== 信念系统 ====================

    /**
     * 触发信念消解事件
     */
    private fun triggerBeliefErosionEvent(log: MutableList<String>) {
        if (!beliefState.isStillHolding || beliefState.origin == null) return

        // 根据当前状态计算触发概率
        val adjustedProbability = BELief_EROSION_BASE_PROBABILITY +
            (playerState.anxiety / 100.0) * 0.03 +
            (100.0 - playerState.money) / 10000.0 * 0.02

        if (random.nextDouble() < adjustedProbability) {
            // 随机选择一个消解原因
            val erosionEvent = BeliefErosionEvents.events.random()

            // 更新信念状态
            beliefState = beliefState.copy(
                beliefValue = max(0, beliefState.beliefValue - erosionEvent.beliefDecay),
                lastErosionCause = erosionEvent.cause,
                totalFailures = beliefState.totalFailures + 1
            )

            // 检查是否触发里程碑
            checkBeliefMilestone()

            // 记录事件
            val eventText = "【信念动摇】${erosionEvent.description}"
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + eventText)
            log.add("信念消解: ${erosionEvent.cause.label}")
        }
    }

    /**
     * 检查信念里程碑
     */
    private fun checkBeliefMilestone() {
        val milestone = BeliefMilestones.milestones
            .filter { it.beliefThreshold > 0 }
            .find { beliefState.beliefValue <= it.beliefThreshold && !it.isPositive }

        milestone?.let {
            val eventText = "【信念里程碑】${it.title}: ${it.description}"
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + eventText)
            playerState = playerState.copy(dailyEvents = playerState.dailyEvents + "小镇评述：${it.townCommentary}")
        }
    }

    /**
     * 获取当前信念状态
     */
    fun getBeliefState(): BeliefState = beliefState

    /**
     * 设置信念起源
     */
    fun setBeliefOrigin(origin: com.example.townapp.data.BeliefOrigin) {
        beliefState = beliefState.copy(origin = origin)
    }

    // ==================== 玩家主动事件系统 ====================

    /**
     * 触发玩家主动事件
     */
    fun triggerPlayerEvent(eventId: String): ActionResult {
        val event = PlayerEventSystem.allPlayerEvents.find { it.id == eventId }
        if (event == null) {
            return ActionResult(success = false, message = "找不到这个事件")
        }

        // 检查余额是否足够
        if (playerState.money < event.cost) {
            return ActionResult(success = false, message = "余额不足，需要¥${event.cost}")
        }

        // 应用事件效果
        playerState = playerState.copy(
            money = playerState.money - event.cost,
            energy = max(0.0, min(100.0, playerState.energy + event.fatigueDelta * 20)),
            anxiety = max(0.0, min(100.0, playerState.anxiety + event.anxietyDelta * 20)),
            happiness = max(0.0, min(100.0, playerState.happiness + event.selfEsteemDelta * 15)),
            loneliness = max(0.0, min(100.0, playerState.loneliness + event.lonelinessDelta * 20)),
            health = max(0.0, min(100.0, playerState.health + event.healthDelta * 20))
        )

        // 记录事件
        val effectSummary = PlayerEventSystem.calculateEffect(event).summary
        playerState = playerState.copy(dailyEvents = playerState.dailyEvents + "【主动事件】${event.name}: $effectSummary")

        return ActionResult(
            success = true,
            message = effectSummary,
            sparkle = event.description
        )
    }

    /**
     * 获取所有可用的玩家主动事件
     */
    fun getAvailablePlayerEvents(): List<PlayerEventSystem.PlayerEvent> {
        return PlayerEventSystem.allPlayerEvents.filter { playerState.money >= it.cost }
    }

}
