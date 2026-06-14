package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.CuteSceneQuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanionSceneQuoteDao {
    
    @Query("SELECT * FROM companion_scene_quotes WHERE characterId = :characterId ORDER BY priority DESC")
    fun getQuotesByCharacter(characterId: Int): Flow<List<CuteSceneQuoteEntity>>
    
    @Query("SELECT * FROM companion_scene_quotes WHERE characterId = :characterId AND sceneType = :sceneType ORDER BY priority DESC")
    suspend fun getQuotesByScene(characterId: Int, sceneType: String): List<CuteSceneQuoteEntity>
    
    @Query("SELECT * FROM companion_scene_quotes WHERE characterId = :characterId AND sceneType = :sceneType ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuoteByScene(characterId: Int, sceneType: String): CuteSceneQuoteEntity?
    
    @Query("SELECT * FROM companion_scene_quotes WHERE sceneType = :sceneType ORDER BY priority DESC")
    suspend fun getQuotesBySceneType(sceneType: String): List<CuteSceneQuoteEntity>
    
    @Query("SELECT * FROM companion_scene_quotes ORDER BY priority DESC LIMIT :limit")
    suspend fun getTopQuotes(limit: Int): List<CuteSceneQuoteEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: CuteSceneQuoteEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuotes(quotes: List<CuteSceneQuoteEntity>)
    
    @Delete
    suspend fun deleteQuote(quote: CuteSceneQuoteEntity)
    
    @Query("DELETE FROM companion_scene_quotes WHERE characterId = :characterId")
    suspend fun deleteQuotesByCharacter(characterId: Int)
    
    @Query("DELETE FROM companion_scene_quotes")
    suspend fun deleteAllQuotes()
    
    @Query("SELECT COUNT(*) FROM companion_scene_quotes")
    suspend fun count(): Int
    
    @Query("SELECT COUNT(*) FROM companion_scene_quotes WHERE characterId = :characterId")
    suspend fun countByCharacter(characterId: Int): Int
}
