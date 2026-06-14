package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.UnlockEntity

@Dao
interface UnlockDao {
    @Query("SELECT * FROM unlocks")
    suspend fun getAllUnlocks(): List<UnlockEntity>

    @Query("SELECT * FROM unlocks WHERE category = :category")
    suspend fun getUnlocksByCategory(category: String): List<UnlockEntity>

    @Update
    suspend fun updateUnlock(unlock: UnlockEntity)
}