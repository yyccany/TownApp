package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.RecipeIngredientEntity

@Dao
interface RecipeIngredientDao {
    
    @Query("SELECT * FROM recipe_ingredient WHERE recipeId = :recipeId")
    suspend fun getIngredientsByRecipe(recipeId: Long): List<RecipeIngredientEntity>
    
    @Query("SELECT * FROM recipe_ingredient WHERE materialId = :materialId")
    suspend fun getRecipesByMaterial(materialId: Long): List<RecipeIngredientEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: RecipeIngredientEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllIngredients(ingredients: List<RecipeIngredientEntity>)
    
    @Delete
    suspend fun deleteIngredient(ingredient: RecipeIngredientEntity)
    
    @Query("DELETE FROM recipe_ingredient WHERE recipeId = :recipeId")
    suspend fun deleteIngredientsByRecipe(recipeId: Long)
    
    @Query("DELETE FROM recipe_ingredient")
    suspend fun deleteAllIngredients()
    
    @Query("SELECT COUNT(*) FROM recipe_ingredient")
    suspend fun count(): Int
}