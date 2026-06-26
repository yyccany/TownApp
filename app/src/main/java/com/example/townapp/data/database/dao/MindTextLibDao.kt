package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.townapp.data.database.entity.MindTextLibEntity

@Dao
interface MindTextLibDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(text: MindTextLibEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(texts: List<MindTextLibEntity>)

    @Query("SELECT * FROM mind_text_lib WHERE type = :type AND :score BETWEEN consumptionMin AND consumptionMax ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomByTypeAndScore(type: String, score: Int): MindTextLibEntity?

    @Query("SELECT * FROM mind_text_lib WHERE type = :type AND :score BETWEEN consumptionMin AND consumptionMax")
    suspend fun getByTypeAndScore(type: String, score: Int): List<MindTextLibEntity>

    @Query("SELECT * FROM mind_text_lib WHERE type = :type")
    suspend fun getByType(type: String): List<MindTextLibEntity>

    @Query("SELECT * FROM mind_text_lib")
    suspend fun getAll(): List<MindTextLibEntity>

    @Query("DELETE FROM mind_text_lib")
    suspend fun deleteAll()
}
