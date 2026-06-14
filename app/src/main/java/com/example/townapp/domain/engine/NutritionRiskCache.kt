package com.example.townapp.domain.engine

import com.example.townapp.data.database.dao.FoodNutritionDao
import com.example.townapp.data.database.dao.FoodRiskDao
import com.example.townapp.data.database.entity.FoodNutritionEntity
import com.example.townapp.data.database.entity.FoodRiskEntity
import kotlinx.coroutines.*

/**
 * 营养 & 风险一级常驻内存缓存
 *
 * 设计原则：
 * - App 启动时一次性全量加载，后续查询零数据库调用
 * - 用户自定义修改先更新内存（保证实时性），再异步落库
 * - 熔断兜底：数据异常自动返回默认值，界面无空白无崩溃
 */
object NutritionRiskCache {
    /** 营养数据内存缓存（foodId -> FoodNutritionEntity） */
    private val nutritionMap = mutableMapOf<Int, FoodNutritionEntity>()

    /** 风险数据内存缓存（foodId -> FoodRiskEntity） */
    private val riskMap = mutableMapOf<Int, FoodRiskEntity>()

    /** 缓存是否已初始化 */
    @Volatile
    private var isInitialized = false

    /** 默认营养（熔断兜底） */
    val DEFAULT_NUTRITION = FoodNutritionEntity(
        foodId = 0, foodName = "未知食材", category = "未知",
        nutritionScore = 30, typicalServingG = 100
    )

    /** 默认风险（熔断兜底） */
    val DEFAULT_RISK = FoodRiskEntity(
        foodId = 0, foodName = "未知食材", riskLevel = "MEDIUM",
        totalRiskScore = 50, caution = "数据暂不可用，请稍后重试"
    )

    /**
     * 初始化缓存：一次性加载全部营养 & 风险数据到内存。
     * 应在 App 启动时调用一次。
     */
    suspend fun init(
        nutritionDao: FoodNutritionDao,
        riskDao: FoodRiskDao
    ) = withContext(Dispatchers.IO) {
        try {
            val nutritionList = nutritionDao.getAll()
            nutritionMap.clear()
            nutritionList.forEach { nutritionMap[it.foodId] = it }

            val riskList = riskDao.getAll()
            riskMap.clear()
            riskList.forEach { riskMap[it.foodId] = it }

            isInitialized = true
        } catch (e: Exception) {
            isInitialized = false
        }
    }

    /** 获取营养数据（零DB调用，熔断兜底） */
    fun getNutrition(foodId: Int): FoodNutritionEntity {
        if (!isInitialized) return DEFAULT_NUTRITION
        return nutritionMap[foodId] ?: DEFAULT_NUTRITION
    }

    /** 获取风险数据（零DB调用，熔断兜底） */
    fun getRisk(foodId: Int): FoodRiskEntity {
        if (!isInitialized) return DEFAULT_RISK
        return riskMap[foodId] ?: DEFAULT_RISK
    }

    /** 获取所有营养数据（按品类筛选） */
    fun getByCategory(category: String): List<FoodNutritionEntity> {
        if (!isInitialized) return emptyList()
        return nutritionMap.values.filter { it.category == category }
            .sortedByDescending { it.nutritionScore }
    }

    /** 获取所有风险数据（按风险等级筛选） */
    fun getByRiskLevel(level: String): List<FoodRiskEntity> {
        if (!isInitialized) return emptyList()
        return riskMap.values.filter { it.riskLevel == level }
            .sortedByDescending { it.totalRiskScore }
    }

    /** 全部营养列表 */
    fun allNutrition(): List<FoodNutritionEntity> {
        return nutritionMap.values.toList()
    }

    /** 全部风险列表 */
    fun allRisks(): List<FoodRiskEntity> {
        return riskMap.values.toList()
    }

    /** 用户自定义修改 → 先更新内存，再异步落库 */
    fun updateNutrition(food: FoodNutritionEntity, dao: FoodNutritionDao, scope: CoroutineScope) {
        nutritionMap[food.foodId] = food
        scope.launch(Dispatchers.IO) {
            try { dao.update(food) } catch (_: Exception) {}
        }
    }

    /** 用户自定义修改 → 先更新内存，再异步落库 */
    fun updateRisk(risk: FoodRiskEntity, dao: FoodRiskDao, scope: CoroutineScope) {
        riskMap[risk.foodId] = risk
        scope.launch(Dispatchers.IO) {
            try { dao.update(risk) } catch (_: Exception) {}
        }
    }

    /** 同类营养/风险组合查询 */
    fun getNutritionRisk(foodId: Int): Pair<FoodNutritionEntity, FoodRiskEntity> {
        return getNutrition(foodId) to getRisk(foodId)
    }

    /** 缓存大小 */
    fun size() = nutritionMap.size
}
