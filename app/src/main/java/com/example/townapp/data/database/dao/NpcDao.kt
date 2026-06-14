package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.NpcEntity

@Dao
interface NpcDao {
    @Query("SELECT * FROM npcs")
    suspend fun getAllNpcs(): List<NpcEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNpc(npc: NpcEntity)

    @Update
    suspend fun updateNpc(npc: NpcEntity)

    @Query("DELETE FROM npcs WHERE id = :id")
    suspend fun deleteNpc(id: Int)
}