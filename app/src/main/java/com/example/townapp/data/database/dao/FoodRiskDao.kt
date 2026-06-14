package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.FoodRiskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodRiskDao {
    /** 全量加载（供缓存初始化） */
    @Query("SELECT * FROM tb_food_risk ORDER BY foodId ASC")
    suspend fun getAll(): List<FoodRiskEntity>

    /** 按foodId查询 */
    @Query("SELECT * FROM tb_food_risk WHERE foodId = :foodId")
    suspend fun getById(foodId: Int): FoodRiskEntity?

    /** 按风险等级查询 */
    @Query("SELECT * FROM tb_food_risk WHERE riskLevel = :level ORDER BY totalRiskScore DESC")
    suspend fun getByRiskLevel(level: String): List<FoodRiskEntity>

    /** 批量写入（初始化用） */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<FoodRiskEntity>)

    /** 单条更新 */
    @Update
    suspend fun update(risk: FoodRiskEntity)

    /** 计数 */
    @Query("SELECT COUNT(*) FROM tb_food_risk")
    suspend fun count(): Int
}