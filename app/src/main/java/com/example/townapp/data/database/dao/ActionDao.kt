package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.ActionEntity

@Dao
interface ActionDao {
    @Insert
    suspend fun insertAction(action: ActionEntity)

    @Query("SELECT * FROM actions ORDER BY timestamp DESC LIMIT 50")
    suspend fun getRecentActions(): List<ActionEntity>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM actions WHERE type = :type AND subType = :subType")
    suspend fun getTotalAmountByTypeAndSubType(type: String, subType: String): Double
}