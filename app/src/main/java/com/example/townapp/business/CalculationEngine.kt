package com.example.townapp.business

import com.example.townapp.data.database.dao.FormulaDao
import com.example.townapp.data.database.dao.MaterialDao
import com.example.townapp.data.database.entity.FormulaEntity
import com.example.townapp.data.database.entity.MaterialEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class CalculationEngine(
    private val materialDao: MaterialDao,
    private val formulaDao: FormulaDao
) {
    suspend fun calculateProductCost(productId: Long): Double {
        return withContext(Dispatchers.IO) {
            val formulas = formulaDao.getByProductId(productId)
            var totalCost = 0.0
            formulas.forEach { formula ->
                val material = materialDao.getById(formula.materialId)
                material?.let {
                    val unitMultiplier = getUnitMultiplier(formula.unit, it.unit)
                    totalCost += it.unitCost * formula.dosage * unitMultiplier
                }
            }
            roundTo2Decimals(totalCost)
        }
    }

    suspend fun calculateProductNutrition(productId: Long): NutritionResult {
        return withContext(Dispatchers.IO) {
            val formulas = formulaDao.getByProductId(productId)
            var totalCalories = 0.0
            var totalProtein = 0.0
            var totalFat = 0.0
            var totalCarbs = 0.0
            var totalSodium = 0.0

            formulas.forEach { formula ->
                val material = materialDao.getById(formula.materialId)
                material?.let {
                    val unitMultiplier = getUnitMultiplier(formula.unit, it.unit)
                    val weightRatio = if (it.unit == "g") formula.dosage * unitMultiplier / 100.0 else 1.0
                    totalCalories += it.calories * weightRatio
                    totalProtein += it.protein * weightRatio
                    totalFat += it.fat * weightRatio
                    totalCarbs += it.carbs * weightRatio
                    totalSodium += it.sodium * weightRatio
                }
            }

            NutritionResult(
                calories = roundTo1Decimal(totalCalories),
                protein = roundTo1Decimal(totalProtein),
                fat = roundTo1Decimal(totalFat),
                carbs = roundTo1Decimal(totalCarbs),
                sodium = roundTo1Decimal(totalSodium)
            )
        }
    }

    fun calculateIQTaxScore(
        infoAsymmetry: Double,
        emotionalPremium: Double,
        sunkCostIndex: Double
    ): Double {
        val score = (infoAsymmetry * 0.3 + emotionalPremium * 0.4 + sunkCostIndex * 0.3) / 10
        return roundTo1Decimal(score.coerceIn(0.0, 10.0))
    }

    fun calculateDailyCost(
        marketPrice: Double,
        lifeCycleDays: Int,
        monthlyMaintainCost: Double,
        timeCostDaily: Double,
        hourlyWage: Double = 50.0
    ): Double {
        val depreciationCost = if (lifeCycleDays > 0) marketPrice / lifeCycleDays else 0.0
        val dailyMaintainCost = monthlyMaintainCost / 30.0
        val timeValueCost = timeCostDaily * hourlyWage
        return roundTo2Decimals(depreciationCost + dailyMaintainCost + timeValueCost)
    }

    private fun getUnitMultiplier(fromUnit: String, toUnit: String): Double {
        return when {
            fromUnit == toUnit -> 1.0
            fromUnit == "kg" && toUnit == "g" -> 1000.0
            fromUnit == "g" && toUnit == "kg" -> 0.001
            fromUnit == "ml" && toUnit == "g" -> 1.0
            else -> 1.0
        }
    }

    private fun roundTo1Decimal(value: Double): Double {
        return (value * 10).roundToInt() / 10.0
    }

    private fun roundTo2Decimals(value: Double): Double {
        return (value * 100).roundToInt() / 100.0
    }

    data class NutritionResult(
        val calories: Double,
        val protein: Double,
        val fat: Double,
        val carbs: Double,
        val sodium: Double
    )
}