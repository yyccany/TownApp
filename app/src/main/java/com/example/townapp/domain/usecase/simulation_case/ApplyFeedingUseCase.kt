package com.example.townapp.domain.usecase.simulation_case

import android.util.Log
import com.example.townapp.data.database.dao.UserBodyStateDao
import com.example.townapp.data.database.entity.FoodNutritionEntity
import com.example.townapp.data.database.entity.FoodRiskEntity
import com.example.townapp.data.database.entity.UserBodyState
import com.example.townapp.domain.model.simulation.FeedingResultVo
import com.example.townapp.domain.model.simulation.FoodDisplayVo
import com.example.townapp.domain.model.simulation.ResidentBodyStateVo
import com.example.townapp.feature.town_simulation.BodyStateBusiness
import com.example.townapp.feature.town_simulation.NutritionRiskCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 应用进食用例
 * 
 * 严格遵循实事求是：
 * 1. 食材数据来自 Room（NutritionRiskCache）
 * 2. 身体状态计算由 BodyStateBusiness 执行
 * 3. 只做数据桥接，不做额外计算
 * 
 * 严格遵循以人为本：
 * 食物最终作用于人的身体，身体状态决定一切行为选择能力
 */
class ApplyFeedingUseCase(
    private val bodyStateDao: UserBodyStateDao
) {
    /**
     * 应用进食（根据食材 ID）
     * 
     * @param userId 居民 ID
     * @param foodId 食材 ID（对应 FoodNutritionEntity.foodId）
     * @param servingGrams 食用份量（克），默认 300g
     */
    suspend fun execute(userId: Int, foodId: Int, servingGrams: Int = 300): FeedingResultVo {
        return withContext(Dispatchers.IO) {
            // 1. 获取食材数据（营养 + 风险）
            val nutrition = NutritionRiskCache.getNutrition(foodId)
            val risk = NutritionRiskCache.getRisk(foodId)
            
            if (nutrition.foodId == 0) {
                Log.e("FEEDING_USE_CASE", "食材 $foodId 不存在")
                throw IllegalArgumentException("食材不存在：$foodId")
            }
            
            // 2. 获取当前身体状态
            val currentBody = bodyStateDao.getSync(userId) ?: UserBodyState(userId = userId)
            
            // 3. 应用进食影响
            val impact = BodyStateBusiness.applyFeeding(
                body = currentBody,
                nutrition = nutrition,
                risk = risk,
                servingGrams = servingGrams
            )
            
            // 4. 持久化更新后的身体状态
            bodyStateDao.updateSync(impact.updatedState)
            
            Log.d("FEEDING_USE_CASE", 
                "进食完成：${nutrition.foodName} → 饱腹+${impact.updatedState.satiety - currentBody.satiety}，" +
                "营养+${impact.updatedState.nutritionBalance - currentBody.nutritionBalance}，" +
                "毒素+${impact.updatedState.toxinLevel - currentBody.toxinLevel}")
            
            // 5. 返回结果
            FeedingResultVo(
                updatedBodyState = ResidentBodyStateVo.fromEntity(impact.updatedState),
                moodDelta = impact.moodDelta,
                reaction = impact.reaction,
                reactionEmoji = impact.reactionEmoji,
                nutritionGain = impact.updatedState.nutritionBalance - currentBody.nutritionBalance,
                toxinGain = impact.updatedState.toxinLevel - currentBody.toxinLevel,
                energyChange = impact.updatedState.energy - currentBody.energy
            )
        }
    }
    
    /**
     * 应用进食（直接传入 FoodDisplayVo）
     */
    suspend fun executeByFoodVo(userId: Int, foodVo: FoodDisplayVo, servingGrams: Int = 300): FeedingResultVo {
        return withContext(Dispatchers.IO) {
            // 1. 构造 Entity（因为 BodyStateBusiness 需要 Entity）
            val nutritionEntity = FoodNutritionEntity(
                foodId = foodVo.foodId,
                foodName = foodVo.foodName,
                category = foodVo.category,
                proteinPer100g = foodVo.protein,
                fatPer100g = foodVo.fat,
                carbohydratePer100g = foodVo.carbohydrate,
                fiberPer100g = foodVo.fiber,
                caloriesPer100g = foodVo.calories,
                sugarPer100g = foodVo.sugar,
                saltPer100g = foodVo.salt,
                cholesterolMg = foodVo.cholesterol,
                calciumMg = foodVo.calcium,
                ironMg = foodVo.iron,
                vitaminCMg = foodVo.vitaminC,
                nutritionScore = foodVo.nutritionScore,
                typicalServingG = foodVo.typicalServingG,
                note = foodVo.note,
                isIQTax = foodVo.isIQTax,
                iqTaxLevel = foodVo.iqTaxLevel,
                iqTaxReason = foodVo.iqTaxReason
            )
            
            val riskEntity = FoodRiskEntity(
                foodId = foodVo.foodId,
                foodName = foodVo.foodName,
                riskLevel = foodVo.riskLevel,
                heavyMetalRisk = foodVo.heavyMetalRisk,
                pesticideRisk = foodVo.pesticideRisk,
                sugarOilRisk = foodVo.sugarOilRisk,
                processingRisk = foodVo.processingRisk,
                additiveRisk = foodVo.additiveRisk,
                totalRiskScore = foodVo.totalRiskScore,
                heavyMetalDesc = foodVo.heavyMetalDesc,
                merchantClaim = foodVo.merchantClaim,
                actualEffect = foodVo.actualEffect,
                caution = foodVo.caution
            )
            
            // 2. 获取当前身体状态
            val currentBody = bodyStateDao.getSync(userId) ?: UserBodyState(userId = userId)
            
            // 3. 应用进食影响
            val impact = BodyStateBusiness.applyFeeding(
                body = currentBody,
                nutrition = nutritionEntity,
                risk = riskEntity,
                servingGrams = servingGrams
            )
            
            // 4. 持久化
            bodyStateDao.updateSync(impact.updatedState)
            
            // 5. 返回结果
            FeedingResultVo(
                updatedBodyState = ResidentBodyStateVo.fromEntity(impact.updatedState),
                moodDelta = impact.moodDelta,
                reaction = impact.reaction,
                reactionEmoji = impact.reactionEmoji,
                nutritionGain = impact.updatedState.nutritionBalance - currentBody.nutritionBalance,
                toxinGain = impact.updatedState.toxinLevel - currentBody.toxinLevel,
                energyChange = impact.updatedState.energy - currentBody.energy
            )
        }
    }
    
    /**
     * 获取当前身体状态
     */
    suspend fun getBodyState(userId: Int): ResidentBodyStateVo? {
        return withContext(Dispatchers.IO) {
            bodyStateDao.getSync(userId)?.let { ResidentBodyStateVo.fromEntity(it) }
        }
    }
    
    /**
     * 初始化居民身体状态（如果不存在）
     */
    suspend fun initBodyStateIfNeeded(userId: Int, userName: String) {
        withContext(Dispatchers.IO) {
            val existing = bodyStateDao.getSync(userId)
            if (existing == null) {
                val initial = UserBodyState(userId = userId, userName = userName)
                bodyStateDao.insert(initial)
                Log.d("FEEDING_USE_CASE", "初始化居民身体状态：userId=$userId, userName=$userName")
            }
        }
    }
}
