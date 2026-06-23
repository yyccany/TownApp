package com.example.townapp.domain.usecase.simulation_case

import android.util.Log
import com.example.townapp.domain.model.simulation.FoodDisplayVo
import com.example.townapp.feature.town_simulation.NutritionRiskCache

/**
 * 获取原始食材营养风险数据用例
 * 遵循实事求是：
 * 1. 从 NutritionRiskCache（内存缓存）读取 Room 原始数据
 * 2. 整合营养 + 风险两张表为单一展示模型
 * 3. 不加工数据，仅如实拼接字段
 */
class GetRawFoodNutritionUseCase {

    fun execute(): List<FoodDisplayVo> {
        val nutritionList = NutritionRiskCache.allNutrition()
        Log.d("ROOM_FOOD_DB", "UseCase读取原始营养数据：${nutritionList.size}条")

        val voList = nutritionList.map { nutrition ->
            val risk = NutritionRiskCache.getRisk(nutrition.foodId)
            FoodDisplayVo.fromEntity(nutrition, risk)
        }

        Log.d("ROOM_VO", "组装后食材展示数据条数：${voList.size}")
        return voList
    }

    fun getByCategory(category: String): List<FoodDisplayVo> {
        val nutritionList = NutritionRiskCache.getByCategory(category)
        return nutritionList.map { nutrition ->
            val risk = NutritionRiskCache.getRisk(nutrition.foodId)
            FoodDisplayVo.fromEntity(nutrition, risk)
        }
    }

    fun getByRiskLevel(level: String): List<FoodDisplayVo> {
        val riskList = NutritionRiskCache.getByRiskLevel(level)
        return riskList.map { risk ->
            val nutrition = NutritionRiskCache.getNutrition(risk.foodId)
            FoodDisplayVo.fromEntity(nutrition, risk)
        }
    }

    fun getById(foodId: Int): FoodDisplayVo? {
        val nutrition = NutritionRiskCache.getNutrition(foodId)
        if (nutrition.foodId == 0) return null
        val risk = NutritionRiskCache.getRisk(foodId)
        return FoodDisplayVo.fromEntity(nutrition, risk)
    }

    fun getIQTaxFoods(): List<FoodDisplayVo> {
        return execute().filter { it.isIQTax }
    }

    fun getHealthyFoods(): List<FoodDisplayVo> {
        return execute().filter { it.riskLevel == "LOW" && it.nutritionScore >= 60 }
    }

    fun search(query: String): List<FoodDisplayVo> {
        val lowerQuery = query.lowercase()
        return execute().filter {
            it.foodName.lowercase().contains(lowerQuery) ||
            it.category.lowercase().contains(lowerQuery) ||
            it.note.lowercase().contains(lowerQuery)
        }
    }
}
