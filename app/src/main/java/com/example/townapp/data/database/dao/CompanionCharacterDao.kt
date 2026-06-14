package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.CuteCharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanionCharacterDao {
    
    @Query("SELECT * FROM companion_characters WHERE isEnabled = 1 ORDER BY characterId")
    fun getAllCharacters(): Flow<List<CuteCharacterEntity>>
    
    @Query("SELECT * FROM companion_characters WHERE characterId = :id")
    suspend fun getCharacterById(id: Int): CuteCharacterEntity?
    
    @Query("SELECT * FROM companion_characters WHERE isDefaultUnlock = 1")
    suspend fun getDefaultCharacters(): List<CuteCharacterEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CuteCharacterEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CuteCharacterEntity>)
    
    @Update
    suspend fun updateCharacter(character: CuteCharacterEntity)
    
    @Query("UPDATE companion_characters SET currentMood = :mood WHERE characterId = :characterId")
    suspend fun updateMood(characterId: Int, mood: Int)
    
    @Query("UPDATE companion_characters SET currentEnergy = :energy WHERE characterId = :characterId")
    suspend fun updateEnergy(characterId: Int, energy: Int)
    
    @Query("UPDATE companion_characters SET lastInteractionTime = :time WHERE characterId = :characterId")
    suspend fun updateLastInteraction(characterId: Int, time: Long)
    
    @Delete
    suspend fun deleteCharacter(character: CuteCharacterEntity)
    
    @Query("DELETE FROM companion_characters")
    suspend fun deleteAllCharacters()
    
    @Query("SELECT COUNT(*) FROM companion_characters")
    suspend fun count(): Int
}
