package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.townapp.data.database.entity.NightStateEntity

@Dao
interface NightStateDao {
    @Insert
    suspend fun insert(state: NightStateEntity)

    @Insert
    suspend fun insertAll(states: List<NightStateEntity>)

    @Update
    suspend fun update(state: NightStateEntity)

    @Query("SELECT * FROM tb_night_state WHERE npcId = :npcId ORDER BY gameDay DESC")
    suspend fun getStatesByNpcId(npcId: String): List<NightStateEntity>

    @Query("SELECT * FROM tb_night_state WHERE npcId = :npcId AND gameDay = :gameDay")
    suspend fun getStateByNpcAndDay(npcId: String, gameDay: Int): NightStateEntity?

    @Query("SELECT * FROM tb_night_state WHERE npcId = :npcId ORDER BY gameDay DESC LIMIT :limit")
    suspend fun getRecentStates(npcId: String, limit: Int): List<NightStateEntity>

    @Query("SELECT COUNT(*) FROM tb_night_state WHERE npcId = :npcId AND sleepStatus = 'INSOMNIA'")
    suspend fun getInsomniaCount(npcId: String): Int

    @Query("SELECT COUNT(*) FROM tb_night_state WHERE npcId = :npcId AND sleepStatus = 'DEEP_SLEEP'")
    suspend fun getDeepSleepCount(npcId: String): Int

    @Query("SELECT AVG(sleepQuality) FROM tb_night_state WHERE npcId = :npcId ORDER BY gameDay DESC LIMIT :days")
    suspend fun getAvgSleepQualityRecent(npcId: String, days: Int): Double

    @Query("SELECT AVG(energyRecovered) FROM tb_night_state WHERE npcId = :npcId ORDER BY gameDay DESC LIMIT :days")
    suspend fun getAvgEnergyRecoveryRecent(npcId: String, days: Int): Double

    @Query("DELETE FROM tb_night_state WHERE npcId = :npcId")
    suspend fun deleteByNpcId(npcId: String)

    @Query("DELETE FROM tb_night_state")
    suspend fun deleteAll()
}
