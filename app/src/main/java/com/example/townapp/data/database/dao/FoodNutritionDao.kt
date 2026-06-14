package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.FoodNutritionEntity

@Dao
interface FoodNutritionDao {
    /** 全量加载（供缓存初始化） */
    @Query("SELECT * FROM tb_food_nutrition ORDER BY foodId ASC")
    suspend fun getAll(): List<FoodNutritionEntity>

    /** 按foodId查询（缓存加载锁） */
    @Query("SELECT * FROM tb_food_nutrition WHERE foodId = :foodId")
    suspend fun getById(foodId: Int): FoodNutritionEntity?

    /** 按品类查询 */
    @Query("SELECT * FROM tb_food_nutrition WHERE category = :category ORDER BY nutritionScore DESC")
    suspend fun getByCategory(category: String): List<FoodNutritionEntity>

    /** 批量写入（初始化用） */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<FoodNutritionEntity>)

    /** 单条更新（用户自定义修改） */
    @Update
    suspend fun update(food: FoodNutritionEntity)

    /** 计数 */
    @Query("SELECT COUNT(*) FROM tb_food_nutrition")
    suspend fun count(): Int
}