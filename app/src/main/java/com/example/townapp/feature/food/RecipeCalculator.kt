package com.example.townapp.feature.food

import com.example.townapp.data.FoodItem
import com.example.townapp.data.Recipe
import com.example.townapp.data.RecipeIngredient

object RecipeCalculator {
    data class RecipeNutrition(
        val totalCalories: Float,
        val totalFat: Float,
        val totalProtein: Float
    )

    /** 计算单份食材的营养 */
    fun calculateIngredientNutrition(food: FoodItem, gram: Float): Triple<Float, Float, Float> {
        val ratio = gram / 100f
        return Triple(
            (food.caloriesPer100g * ratio).toFloat(),
            (food.fatPer100g * ratio).toFloat(),
            (food.proteinPer100g * ratio).toFloat()
        )
    }

    /** 汇总整道菜的营养 */
    fun getRecipeNutrition(recipe: Recipe, allFoods: List<FoodItem>): RecipeNutrition {
        var totalCal = 0f
        var totalFat = 0f
        var totalPro = 0f
        for (ri in recipe.ingredientIds) {
            val food = allFoods.firstOrNull { it.id == ri.foodId } ?: continue
            val (cal, fat, pro) = calculateIngredientNutrition(food, ri.useGram)
            totalCal += cal
            totalFat += fat
            totalPro += pro
        }
        return RecipeNutrition(totalCal, totalFat, totalPro)
    }

    /** 计算单份价格 */
    fun getIngredientCost(food: FoodItem, gram: Float): Double {
        return food.pricePer100g * (gram / 100.0)
    }

    fun getRecipeTotalCost(recipe: Recipe, allFoods: List<FoodItem>): Double {
        var total = 0.0
        for (ri in recipe.ingredientIds) {
            val food = allFoods.firstOrNull { it.id == ri.foodId } ?: continue
            total += getIngredientCost(food, ri.useGram)
        }
        return total
    }
}