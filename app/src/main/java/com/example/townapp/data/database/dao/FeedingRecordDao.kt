package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.FeedingRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedingRecordDao {
    @Query("SELECT * FROM feeding_records ORDER BY timestamp DESC")
    fun getAll(): Flow<List<FeedingRecordEntity>>

    @Query("SELECT * FROM feeding_records WHERE residentId = :residentId ORDER BY timestamp DESC")
    fun getByResident(residentId: Int): Flow<List<FeedingRecordEntity>>

    @Query("SELECT * FROM feeding_records ORDER BY timestamp DESC LIMIT :limit")
    fun getRecent(limit: Int): Flow<List<FeedingRecordEntity>>

    @Insert
    suspend fun insert(record: FeedingRecordEntity)

    @Query("DELETE FROM feeding_records")
    suspend fun deleteAll()
}