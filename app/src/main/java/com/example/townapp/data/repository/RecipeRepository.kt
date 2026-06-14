package com.example.townapp.data.repository

import com.example.townapp.data.database.TownDatabase
import com.example.townapp.data.database.entity.RecipeEntity
import com.example.townapp.data.database.entity.RecipeIngredientEntity
import com.example.townapp.data.recipe.InternationalRecipeData
import com.example.townapp.data.recipe.RecipeCategoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RecipeRepository(private val database: TownDatabase) {

    private val recipeDao = database.recipeDao()
    private val recipeIngredientDao = database.recipeIngredientDao()

    fun getAllRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getAllRecipes()
    }

    fun getRecipesByDomestic(isDomestic: Boolean): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByDomestic(isDomestic)
    }

    fun getRecipesByRegion(region: String): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByRegion(region)
    }

    fun getRecipesByRegions(regions: List<String>): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByRegions(regions)
    }

    fun getRecipesByCategory(category: String): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByCategory(category)
    }

    fun getRecipesByCategories(categories: List<String>): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByCategories(categories)
    }

    fun getRecipesByCookingMethod(method: String): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByCookingMethod(method)
    }

    fun getRecipesByIngredientType(type: String): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByIngredientType(type)
    }

    fun getRecipesByFlavor(flavor: String): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByFlavor(flavor)
    }

    fun getRecipesByCountry(country: String): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByCountry(country)
    }

    fun getRecipesByDifficulty(level: Int): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByDifficulty(level)
    }

    fun getRecipesByDifficultyRange(minLevel: Int, maxLevel: Int): Flow<List<RecipeEntity>> {
        return recipeDao.getRecipesByDifficultyRange(minLevel, maxLevel)
    }

    fun getFamilyFriendlyRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getFamilyFriendlyRecipes()
    }

    fun getQuickRecipes(maxMinutes: Int): Flow<List<RecipeEntity>> {
        return recipeDao.getQuickRecipes(maxMinutes)
    }

    fun searchRecipes(query: String): Flow<List<RecipeEntity>> {
        return recipeDao.searchRecipes("%$query%")
    }

    fun getVegetarianRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getVegetarianRecipes()
    }

    fun getVeganRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getVeganRecipes()
    }

    fun getGlutenFreeRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getGlutenFreeRecipes()
    }

    suspend fun getRecipeById(id: Long): RecipeEntity? {
        return withContext(Dispatchers.IO) {
            recipeDao.getRecipeById(id)
        }
    }

    suspend fun getRandomRecipes(limit: Int): List<RecipeEntity> {
        return withContext(Dispatchers.IO) {
            recipeDao.getRandomRecipes(limit)
        }
    }

    suspend fun getTopRecipes(limit: Int): List<RecipeEntity> {
        return withContext(Dispatchers.IO) {
            recipeDao.getTopRecipes(limit)
        }
    }

    suspend fun getTopRecipesByRegion(region: String, limit: Int): List<RecipeEntity> {
        return withContext(Dispatchers.IO) {
            recipeDao.getTopRecipesByRegion(region, limit)
        }
    }

    suspend fun countRecipes(): Int {
        return withContext(Dispatchers.IO) {
            recipeDao.count()
        }
    }

    suspend fun countByDomestic(isDomestic: Boolean): Int {
        return withContext(Dispatchers.IO) {
            recipeDao.countByDomestic(isDomestic)
        }
    }

    suspend fun countByRegion(region: String): Int {
        return withContext(Dispatchers.IO) {
            recipeDao.countByRegion(region)
        }
    }

    suspend fun countByCookingMethod(method: String): Int {
        return withContext(Dispatchers.IO) {
            recipeDao.countByCookingMethod(method)
        }
    }

    suspend fun countByIngredientType(type: String): Int {
        return withContext(Dispatchers.IO) {
            recipeDao.countByIngredientType(type)
        }
    }

    suspend fun countByFlavor(flavor: String): Int {
        return withContext(Dispatchers.IO) {
            recipeDao.countByFlavor(flavor)
        }
    }

    suspend fun getAllRegions(): List<String> {
        return withContext(Dispatchers.IO) {
            recipeDao.getAllRegions()
        }
    }

    suspend fun getAllCategories(): List<String> {
        return withContext(Dispatchers.IO) {
            recipeDao.getAllCategories()
        }
    }

    suspend fun getAllCookingMethods(): List<String> {
        return withContext(Dispatchers.IO) {
            recipeDao.getAllCookingMethods()
        }
    }

    suspend fun getAllIngredientTypes(): List<String> {
        return withContext(Dispatchers.IO) {
            recipeDao.getAllIngredientTypes()
        }
    }

    suspend fun getAllFlavorProfiles(): List<String> {
        return withContext(Dispatchers.IO) {
            recipeDao.getAllFlavorProfiles()
        }
    }

    suspend fun getAllCountries(): List<String> {
        return withContext(Dispatchers.IO) {
            recipeDao.getAllCountries()
        }
    }

    suspend fun insertRecipe(recipe: RecipeEntity): Long {
        return withContext(Dispatchers.IO) {
            recipeDao.insertRecipe(recipe)
        }
    }

    suspend fun insertAllRecipes(recipes: List<RecipeEntity>) {
        withContext(Dispatchers.IO) {
            recipeDao.insertAllRecipes(recipes)
        }
    }

    suspend fun updateRecipe(recipe: RecipeEntity) {
        withContext(Dispatchers.IO) {
            recipeDao.updateRecipe(recipe)
        }
    }

    suspend fun deleteRecipe(recipe: RecipeEntity) {
        withContext(Dispatchers.IO) {
            recipeDao.deleteRecipe(recipe)
        }
    }

    suspend fun deleteAllRecipes() {
        withContext(Dispatchers.IO) {
            recipeDao.deleteAllRecipes()
        }
    }

    suspend fun getIngredientsByRecipe(recipeId: Long): List<RecipeIngredientEntity> {
        return withContext(Dispatchers.IO) {
            recipeIngredientDao.getIngredientsByRecipe(recipeId)
        }
    }

    suspend fun insertIngredient(ingredient: RecipeIngredientEntity) {
        withContext(Dispatchers.IO) {
            recipeIngredientDao.insertIngredient(ingredient)
        }
    }

    suspend fun insertAllIngredients(ingredients: List<RecipeIngredientEntity>) {
        withContext(Dispatchers.IO) {
            recipeIngredientDao.insertAllIngredients(ingredients)
        }
    }

    suspend fun initializeSeedData() {
        withContext(Dispatchers.IO) {
            val count = recipeDao.count()
            if (count == 0) {
                val recipes = InternationalRecipeData.getAllRecipes()
                recipeDao.insertAllRecipes(recipes)
            }
        }
    }

    suspend fun initializeFullRecipeData() {
        withContext(Dispatchers.IO) {
            val count = recipeDao.count()
            if (count < 500) {
                val recipes = InternationalRecipeData.getAllRecipes()
                recipeDao.insertAllRecipes(recipes)
            }
        }
    }

    suspend fun getBalancedRecipes(): List<RecipeEntity> {
        return InternationalRecipeData.getBalancedRecipes()
    }

    fun getRecipeStatistics(): InternationalRecipeData.RecipeStatistics {
        return InternationalRecipeData.getStatistics()
    }

    fun getRecipesFromDataByRegion(region: String): List<RecipeEntity> {
        return InternationalRecipeData.getRecipesByRegion(region)
    }

    fun getRecipesFromDataByCategory(category: String): List<RecipeEntity> {
        return InternationalRecipeData.getRecipesByCategory(category)
    }

    fun getCategoryManager(): RecipeCategoryManager {
        return RecipeCategoryManager
    }

    fun getRegionInfo(regionCode: String): RecipeCategoryManager.CategoryInfo? {
        return RecipeCategoryManager.getRegionInfo(regionCode)
    }

    fun getCategoryInfo(categoryCode: String): RecipeCategoryManager.CategoryInfo? {
        return RecipeCategoryManager.getCategoryInfo(categoryCode)
    }

    fun getCookingMethodInfo(methodCode: String): RecipeCategoryManager.CategoryInfo? {
        return RecipeCategoryManager.getCookingMethodInfo(methodCode)
    }

    fun getIngredientTypeInfo(typeCode: String): RecipeCategoryManager.CategoryInfo? {
        return RecipeCategoryManager.getIngredientTypeInfo(typeCode)
    }

    fun getFlavorInfo(flavorCode: String): RecipeCategoryManager.CategoryInfo? {
        return RecipeCategoryManager.getFlavorInfo(flavorCode)
    }

    fun getCountryInfo(countryCode: String): RecipeCategoryManager.CategoryInfo? {
        return RecipeCategoryManager.getCountryInfo(countryCode)
    }

    fun getDifficultyLabel(level: Int): String {
        return RecipeCategoryManager.getDifficultyLabel(level)
    }

    fun getDifficultyStars(level: Int): String {
        return RecipeCategoryManager.getDifficultyStars(level)
    }
}