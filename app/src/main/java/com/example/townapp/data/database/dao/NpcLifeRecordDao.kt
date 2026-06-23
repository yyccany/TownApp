package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.townapp.data.database.entity.NpcLifeRecordEntity

@Dao
interface NpcLifeRecordDao {
    @Insert
    suspend fun insert(record: NpcLifeRecordEntity)

    @Insert
    suspend fun insertAll(records: List<NpcLifeRecordEntity>)

    @Update
    suspend fun update(record: NpcLifeRecordEntity)

    @Query("SELECT * FROM tb_npc_life_record WHERE npcId = :npcId ORDER BY gameDay DESC")
    suspend fun getRecordsByNpcId(npcId: String): List<NpcLifeRecordEntity>

    @Query("SELECT * FROM tb_npc_life_record WHERE npcId = :npcId AND gameDay = :gameDay")
    suspend fun getRecordByNpcAndDay(npcId: String, gameDay: Int): NpcLifeRecordEntity?

    @Query("SELECT * FROM tb_npc_life_record WHERE npcId = :npcId ORDER BY gameDay DESC LIMIT :limit")
    suspend fun getRecentRecords(npcId: String, limit: Int): List<NpcLifeRecordEntity>

    @Query("SELECT SUM(dayLaborIncome) FROM tb_npc_life_record WHERE npcId = :npcId")
    suspend fun getTotalLaborIncome(npcId: String): Double

    @Query("SELECT SUM(dayCompoundIncome) FROM tb_npc_life_record WHERE npcId = :npcId")
    suspend fun getTotalCompoundIncome(npcId: String): Double

    @Query("SELECT AVG(happiness) FROM tb_npc_life_record WHERE npcId = :npcId ORDER BY gameDay DESC LIMIT :days")
    suspend fun getAvgHappinessRecent(npcId: String, days: Int): Double

    @Query("SELECT AVG(anxiety) FROM tb_npc_life_record WHERE npcId = :npcId ORDER BY gameDay DESC LIMIT :days")
    suspend fun getAvgAnxietyRecent(npcId: String, days: Int): Double

    @Query("SELECT AVG(trauma) FROM tb_npc_life_record WHERE npcId = :npcId ORDER BY gameDay DESC LIMIT :days")
    suspend fun getAvgTraumaRecent(npcId: String, days: Int): Double

    @Query("SELECT AVG(workMinutes) FROM tb_npc_life_record WHERE npcId = :npcId ORDER BY gameDay DESC LIMIT :days")
    suspend fun getAvgWorkMinutesRecent(npcId: String, days: Int): Double

    @Query("DELETE FROM tb_npc_life_record WHERE npcId = :npcId")
    suspend fun deleteByNpcId(npcId: String)

    @Query("DELETE FROM tb_npc_life_record")
    suspend fun deleteAll()
}
