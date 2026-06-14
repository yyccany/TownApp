package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.CuteEmotionQuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanionEmotionQuoteDao {
    
    @Query("SELECT * FROM companion_emotion_quotes WHERE characterId = :characterId ORDER BY quoteId")
    fun getQuotesByCharacter(characterId: Int): Flow<List<CuteEmotionQuoteEntity>>
    
    @Query("SELECT * FROM companion_emotion_quotes WHERE characterId = :characterId AND emotionType = :emotionType")
    suspend fun getQuotesByEmotion(characterId: Int, emotionType: String): List<CuteEmotionQuoteEntity>
    
    @Query("SELECT * FROM companion_emotion_quotes WHERE characterId = :characterId AND emotionType = :emotionType ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuoteByEmotion(characterId: Int, emotionType: String): CuteEmotionQuoteEntity?
    
    @Query("SELECT * FROM companion_emotion_quotes WHERE characterId = :characterId ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuote(characterId: Int): CuteEmotionQuoteEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: CuteEmotionQuoteEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuotes(quotes: List<CuteEmotionQuoteEntity>)
    
    @Delete
    suspend fun deleteQuote(quote: CuteEmotionQuoteEntity)
    
    @Query("DELETE FROM companion_emotion_quotes WHERE characterId = :characterId")
    suspend fun deleteQuotesByCharacter(characterId: Int)
    
    @Query("DELETE FROM companion_emotion_quotes")
    suspend fun deleteAllQuotes()
    
    @Query("SELECT COUNT(*) FROM companion_emotion_quotes")
    suspend fun count(): Int
    
    @Query("SELECT COUNT(*) FROM companion_emotion_quotes WHERE characterId = :characterId")
    suspend fun countByCharacter(characterId: Int): Int
}
