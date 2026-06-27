package com.example.townapp.data.repository

import com.example.townapp.data.database.dao.ConsumptionChoiceEventDao
import com.example.townapp.data.database.dao.UserConsumptionStateDao
import com.example.townapp.data.database.entity.ConsumptionChoiceEventEntity
import com.example.townapp.data.database.entity.UserConsumptionStateEntity
import com.example.townapp.domain.consumption.ConsumptionSystem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 消费系统数据仓库
 *
 * 整合用户消费状态、消费抉择事件、商品标签等数据。
 * 负责业务逻辑与数据持久化的桥接。
 *
 * 设计原则：
 * 1. Repository 只做数据调度，不做业务判断
 * 2. 业务逻辑全部在 ConsumptionSystem 中
 * 3. 所有状态变更通过 Flow 实时通知UI
 */
@Singleton
class ConsumptionRepository @Inject constructor(
    private val userConsumptionStateDao: UserConsumptionStateDao,
    private val consumptionChoiceEventDao: ConsumptionChoiceEventDao,
    private val consumptionDataRepository: ConsumptionDataRepository
) {

    // ============================================
    // 用户消费状态
    // ============================================

    /** 获取用户消费状态（Flow形式，实时更新） */
    fun getConsumptionStateFlow(userId: Int = 1): Flow<UserConsumptionStateEntity?> {
        return userConsumptionStateDao.getByUserIdFlow(userId)
    }

    /** 暂停式获取消费状态 */
    suspend fun getConsumptionState(userId: Int = 1): UserConsumptionStateEntity? {
        return userConsumptionStateDao.getByUserId(userId)
    }

    /** 获取综合人本评分（Flow形式） */
    fun getOverallScoreFlow(userId: Int = 1): Flow<Int> {
        return userConsumptionStateDao.getByUserIdFlow(userId).map { it?.overallScore ?: 50 }
    }

    /** 获取四大维度评分（Flow形式） */
    fun getDimensionScoresFlow(userId: Int = 1): Flow<ConsumptionSystem.OrientationScore> {
        return userConsumptionStateDao.getByUserIdFlow(userId).map { state ->
            ConsumptionSystem.OrientationScore(
                food = state?.foodScore ?: 50,
                clothing = state?.clothingScore ?: 50,
                housing = state?.housingScore ?: 50,
                transport = state?.transportScore ?: 50
            )
        }
    }

    /** 获取长期消费效果 */
    suspend fun getLongTermEffect(userId: Int = 1): ConsumptionSystem.LongTermEffect {
        val state = getConsumptionState(userId) ?: return ConsumptionSystem.LongTermEffect()
        val score = ConsumptionSystem.OrientationScore(
            food = state.foodScore,
            clothing = state.clothingScore,
            housing = state.housingScore,
            transport = state.transportScore
        )
        return ConsumptionSystem.calculateLongTermEffect(score)
    }

    // ============================================
    // 消费评分更新
    // ============================================

    /**
     * 更新饮食维度评分
     * @param foodScoreDelta 评分变化量（正=加分，负=减分）
     * @param amount 消费金额
     * @param isPeopleOriented 是否为人本消费
     */
    suspend fun updateFoodScore(
        foodScoreDelta: Int,
        amount: Double = 0.0,
        isPeopleOriented: Boolean? = null,
        userId: Int = 1
    ) {
        val currentState = getConsumptionState(userId) ?: UserConsumptionStateEntity(userId = userId)
        val newFoodScore = (currentState.foodScore + foodScoreDelta).coerceIn(0, 100)

        val newState = currentState.copy(
            foodScore = newFoodScore,
            totalConsumptionAmount = currentState.totalConsumptionAmount + amount,
            lastConsumptionTime = System.currentTimeMillis(),
            lastConsumptionType = "food",
            peopleOrientedCount = if (isPeopleOriented == true) currentState.peopleOrientedCount + 1 else currentState.peopleOrientedCount,
            vanityDrivenCount = if (isPeopleOriented == false) currentState.vanityDrivenCount + 1 else currentState.vanityDrivenCount,
            updateTime = System.currentTimeMillis()
        )

        userConsumptionStateDao.insert(newState)
    }

    /**
     * 更新穿搭维度评分
     */
    suspend fun updateClothingScore(
        clothingScoreDelta: Int,
        amount: Double = 0.0,
        isPeopleOriented: Boolean? = null,
        userId: Int = 1
    ) {
        val currentState = getConsumptionState(userId) ?: UserConsumptionStateEntity(userId = userId)
        val newClothingScore = (currentState.clothingScore + clothingScoreDelta).coerceIn(0, 100)

        val newState = currentState.copy(
            clothingScore = newClothingScore,
            totalConsumptionAmount = currentState.totalConsumptionAmount + amount,
            lastConsumptionTime = System.currentTimeMillis(),
            lastConsumptionType = "clothing",
            peopleOrientedCount = if (isPeopleOriented == true) currentState.peopleOrientedCount + 1 else currentState.peopleOrientedCount,
            vanityDrivenCount = if (isPeopleOriented == false) currentState.vanityDrivenCount + 1 else currentState.vanityDrivenCount,
            updateTime = System.currentTimeMillis()
        )

        userConsumptionStateDao.insert(newState)
    }

    /**
     * 更新居家维度评分
     */
    suspend fun updateHousingScore(
        housingScoreDelta: Int,
        amount: Double = 0.0,
        isPeopleOriented: Boolean? = null,
        userId: Int = 1
    ) {
        val currentState = getConsumptionState(userId) ?: UserConsumptionStateEntity(userId = userId)
        val newHousingScore = (currentState.housingScore + housingScoreDelta).coerceIn(0, 100)

        val newState = currentState.copy(
            housingScore = newHousingScore,
            totalConsumptionAmount = currentState.totalConsumptionAmount + amount,
            lastConsumptionTime = System.currentTimeMillis(),
            lastConsumptionType = "housing",
            peopleOrientedCount = if (isPeopleOriented == true) currentState.peopleOrientedCount + 1 else currentState.peopleOrientedCount,
            vanityDrivenCount = if (isPeopleOriented == false) currentState.vanityDrivenCount + 1 else currentState.vanityDrivenCount,
            updateTime = System.currentTimeMillis()
        )

        userConsumptionStateDao.insert(newState)
    }

    /**
     * 更新出行维度评分
     */
    suspend fun updateTransportScore(
        transportScoreDelta: Int,
        amount: Double = 0.0,
        isPeopleOriented: Boolean? = null,
        userId: Int = 1
    ) {
        val currentState = getConsumptionState(userId) ?: UserConsumptionStateEntity(userId = userId)
        val newTransportScore = (currentState.transportScore + transportScoreDelta).coerceIn(0, 100)

        val newState = currentState.copy(
            transportScore = newTransportScore,
            totalConsumptionAmount = currentState.totalConsumptionAmount + amount,
            lastConsumptionTime = System.currentTimeMillis(),
            lastConsumptionType = "transport",
            peopleOrientedCount = if (isPeopleOriented == true) currentState.peopleOrientedCount + 1 else currentState.peopleOrientedCount,
            vanityDrivenCount = if (isPeopleOriented == false) currentState.vanityDrivenCount + 1 else currentState.vanityDrivenCount,
            updateTime = System.currentTimeMillis()
        )

        userConsumptionStateDao.insert(newState)
    }

    /**
     * 根据商品ID和类型自动计算并更新评分
     */
    suspend fun consumeGoods(goodsId: String, goodsType: String, amount: Double = 0.0, userId: Int = 1) {
        val scoreDelta = consumptionDataRepository.calculateGoodsScoreDelta(goodsId, goodsType)
        val isPeopleOriented = when {
            scoreDelta > 0 -> true
            scoreDelta < 0 -> false
            else -> null
        }

        when (goodsType) {
            "food" -> updateFoodScore(scoreDelta, amount, isPeopleOriented, userId)
            "clothing" -> updateClothingScore(scoreDelta, amount, isPeopleOriented, userId)
            "housing" -> updateHousingScore(scoreDelta, amount, isPeopleOriented, userId)
            "transport" -> updateTransportScore(scoreDelta, amount, isPeopleOriented, userId)
        }
    }

    // ============================================
    // 消费抉择事件
    // ============================================

    /** 获取所有消费抉择事件（Flow形式） */
    fun getAllChoiceEventsFlow(): Flow<List<ConsumptionChoiceEventEntity>> {
        return consumptionChoiceEventDao.getAllFlow()
    }

    /** 获取待选择的事件 */
    suspend fun getPendingChoiceEvent(): ConsumptionChoiceEventEntity? {
        return consumptionChoiceEventDao.getPendingChoice()
    }

    /** 触发一个消费抉择事件 */
    suspend fun triggerChoiceEvent(
        eventId: String,
        eventType: String,
        triggerAge: Int
    ): ConsumptionChoiceEventEntity {
        val event = ConsumptionChoiceEventEntity(
            eventId = eventId,
            eventType = eventType,
            triggerAge = triggerAge,
            isTriggered = true,
            triggerTime = System.currentTimeMillis()
        )
        consumptionChoiceEventDao.insert(event)
        return event
    }

    /**
     * 玩家做出选择
     * @param eventId 事件ID
     * @param choice 选择：1=人本路线，2=虚荣路线
     */
    suspend fun makeChoice(eventId: String, choice: Int, userId: Int = 1) {
        val event = consumptionChoiceEventDao.getByEventId(eventId) ?: return
        val updatedEvent = event.copy(
            playerChoice = choice,
            choiceTime = System.currentTimeMillis()
        )
        consumptionChoiceEventDao.update(updatedEvent)

        // 根据选择更新消费评分
        val scoreDelta = if (choice == 1) 10 else -10
        when (event.eventType) {
            "food" -> updateFoodScore(scoreDelta, isPeopleOriented = choice == 1, userId = userId)
            "clothing" -> updateClothingScore(scoreDelta, isPeopleOriented = choice == 1, userId = userId)
            "housing" -> updateHousingScore(scoreDelta, isPeopleOriented = choice == 1, userId = userId)
            "transport" -> updateTransportScore(scoreDelta, isPeopleOriented = choice == 1, userId = userId)
        }
    }

    // ============================================
    // 文案获取
    // ============================================

    /** 获取当前消费倾向的温柔描述 */
    suspend fun getOrientationNarrative(userId: Int = 1): String? {
        val state = getConsumptionState(userId) ?: return null
        val score = ConsumptionSystem.OrientationScore(
            food = state.foodScore,
            clothing = state.clothingScore,
            housing = state.housingScore,
            transport = state.transportScore
        )
        return ConsumptionSystem.getOrientationNarrative(score)
    }

    /** 获取随机夜间独白文案 */
    suspend fun getRandomNightMonologue(userId: Int = 1): String? {
        val overallScore = getConsumptionState(userId)?.overallScore ?: 50
        return consumptionDataRepository.getRandomMindText("night_mono", overallScore)?.content
    }

    /** 获取随机梦境文案 */
    suspend fun getRandomDreamText(userId: Int = 1): String? {
        val overallScore = getConsumptionState(userId)?.overallScore ?: 50
        val type = if (overallScore >= 60) "sweet_dream" else "nightmare"
        return consumptionDataRepository.getRandomMindText(type, overallScore)?.content
    }

    /** 获取随机日常想法 */
    suspend fun getRandomDailyThought(userId: Int = 1): String? {
        val overallScore = getConsumptionState(userId)?.overallScore ?: 50
        return consumptionDataRepository.getRandomMindText("daily_thought", overallScore)?.content
    }

    // ============================================
    // 统计数据
    // ============================================

    /** 获取人本消费占比 */
    suspend fun getPeopleOrientedRatio(userId: Int = 1): Float {
        val state = getConsumptionState(userId) ?: return 0.5f
        val total = state.peopleOrientedCount + state.vanityDrivenCount
        if (total == 0) return 0.5f
        return state.peopleOrientedCount.toFloat() / total.toFloat()
    }

    /** 重置消费状态（新周目用） */
    suspend fun resetConsumptionState(userId: Int = 1) {
        val defaultState = UserConsumptionStateEntity(userId = userId)
        userConsumptionStateDao.insert(defaultState)
        consumptionChoiceEventDao.clearAll()
    }
}
