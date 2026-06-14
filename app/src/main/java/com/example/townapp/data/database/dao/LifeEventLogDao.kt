package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.townapp.data.database.entity.LifeEventLogEntity

@Dao
interface LifeEventLogDao {
    @Insert
    suspend fun insert(log: LifeEventLogEntity): Long

    @Insert
    suspend fun insertAll(logs: List<LifeEventLogEntity>)

    @Query("SELECT * FROM life_event_log ORDER BY triggerTime DESC LIMIT :limit")
    suspend fun getRecent(limit: Int): List<LifeEventLogEntity>

    @Query("SELECT eventType, COUNT(*) as count FROM life_event_log GROUP BY eventType")
    suspend fun getEventStats(): List<EventStat>

    @Query("DELETE FROM life_event_log WHERE triggerTime < :beforeTime")
    suspend fun deleteOld(beforeTime: Long)

    @Query("DELETE FROM life_event_log")
    suspend fun deleteAll()
}

data class EventStat(
    val eventType: String,
    val count: Int
)