package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.townapp.data.database.entity.QuoteEntity

@Dao
interface QuoteDao {
    @Insert
    suspend fun insert(quote: QuoteEntity): Long

    @Insert
    suspend fun insertAll(quotes: List<QuoteEntity>)

    @Query("SELECT * FROM quotes WHERE roleId = :roleId AND sceneId = :sceneId")
    suspend fun getByRoleAndScene(roleId: String, sceneId: String): List<QuoteEntity>

    @Query("SELECT * FROM quotes WHERE sceneId = :sceneId")
    suspend fun getByScene(sceneId: String): List<QuoteEntity>

    @Query("SELECT * FROM quotes WHERE roleId = :roleId")
    suspend fun getByRole(roleId: String): List<QuoteEntity>

    @Query("SELECT DISTINCT sceneId FROM quotes")
    suspend fun getAllScenes(): List<String>

    @Query("SELECT DISTINCT roleId FROM quotes")
    suspend fun getAllRoles(): List<String>

    @Query("DELETE FROM quotes")
    suspend fun deleteAll()
}