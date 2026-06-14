package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    
    @Query("SELECT * FROM recipe ORDER BY name")
    fun getAllRecipes(): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE isDomestic = :domestic ORDER BY name")
    fun getRecipesByDomestic(domestic: Boolean): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE region = :region ORDER BY name")
    fun getRecipesByRegion(region: String): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE category = :category ORDER BY name")
    fun getRecipesByCategory(category: String): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE cookingMethod = :method ORDER BY name")
    fun getRecipesByCookingMethod(method: String): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE ingredientType = :type ORDER BY name")
    fun getRecipesByIngredientType(type: String): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE flavorProfile = :flavor ORDER BY name")
    fun getRecipesByFlavor(flavor: String): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE country = :country ORDER BY name")
    fun getRecipesByCountry(country: String): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE difficulty = :level ORDER BY name")
    fun getRecipesByDifficulty(level: Int): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE isFamilyFriendly = 1 ORDER BY name")
    fun getFamilyFriendlyRecipes(): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipeById(id: Long): RecipeEntity?
    
    @Query("SELECT * FROM recipe WHERE isVegetarian = 1 ORDER BY name")
    fun getVegetarianRecipes(): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE isVegan = 1 ORDER BY name")
    fun getVeganRecipes(): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE isGlutenFree = 1 ORDER BY name")
    fun getGlutenFreeRecipes(): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE cookTimeMinutes <= :maxMinutes ORDER BY cookTimeMinutes")
    fun getQuickRecipes(maxMinutes: Int): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE name LIKE :query OR nameEn LIKE :query ORDER BY name")
    fun searchRecipes(query: String): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomRecipes(limit: Int): List<RecipeEntity>
    
    @Query("SELECT * FROM recipe ORDER BY rating DESC LIMIT :limit")
    suspend fun getTopRecipes(limit: Int): List<RecipeEntity>
    
    @Query("SELECT * FROM recipe WHERE region = :region ORDER BY rating DESC LIMIT :limit")
    suspend fun getTopRecipesByRegion(region: String, limit: Int): List<RecipeEntity>
    
    @Query("SELECT COUNT(*) FROM recipe")
    suspend fun count(): Int
    
    @Query("SELECT COUNT(*) FROM recipe WHERE isDomestic = :domestic")
    suspend fun countByDomestic(domestic: Boolean): Int
    
    @Query("SELECT COUNT(*) FROM recipe WHERE region = :region")
    suspend fun countByRegion(region: String): Int
    
    @Query("SELECT COUNT(*) FROM recipe WHERE cookingMethod = :method")
    suspend fun countByCookingMethod(method: String): Int
    
    @Query("SELECT COUNT(*) FROM recipe WHERE ingredientType = :type")
    suspend fun countByIngredientType(type: String): Int
    
    @Query("SELECT COUNT(*) FROM recipe WHERE flavorProfile = :flavor")
    suspend fun countByFlavor(flavor: String): Int
    
    @Query("SELECT DISTINCT region FROM recipe ORDER BY region")
    suspend fun getAllRegions(): List<String>
    
    @Query("SELECT DISTINCT category FROM recipe ORDER BY category")
    suspend fun getAllCategories(): List<String>
    
    @Query("SELECT DISTINCT cookingMethod FROM recipe ORDER BY cookingMethod")
    suspend fun getAllCookingMethods(): List<String>
    
    @Query("SELECT DISTINCT ingredientType FROM recipe ORDER BY ingredientType")
    suspend fun getAllIngredientTypes(): List<String>
    
    @Query("SELECT DISTINCT flavorProfile FROM recipe ORDER BY flavorProfile")
    suspend fun getAllFlavorProfiles(): List<String>
    
    @Query("SELECT DISTINCT country FROM recipe ORDER BY country")
    suspend fun getAllCountries(): List<String>
    
    @Query("SELECT * FROM recipe WHERE region IN (:regions) ORDER BY name")
    fun getRecipesByRegions(regions: List<String>): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE category IN (:categories) ORDER BY name")
    fun getRecipesByCategories(categories: List<String>): Flow<List<RecipeEntity>>
    
    @Query("SELECT * FROM recipe WHERE difficulty BETWEEN :minLevel AND :maxLevel ORDER BY difficulty")
    fun getRecipesByDifficultyRange(minLevel: Int, maxLevel: Int): Flow<List<RecipeEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecipes(recipes: List<RecipeEntity>)
    
    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)
    
    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
    
    @Query("DELETE FROM recipe")
    suspend fun deleteAllRecipes()
}