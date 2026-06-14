package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.EventHistoryEntity

@Dao
interface EventHistoryDao {
    @Insert
    suspend fun insertEvent(event: EventHistoryEntity)

    @Query("SELECT * FROM event_history ORDER BY timestamp DESC LIMIT 50")
    suspend fun getRecentEvents(): List<EventHistoryEntity>

    @Query("SELECT COUNT(*) > 0 FROM event_history WHERE eventId = :eventId AND timestamp > :since")
    suspend fun hasEventTriggeredRecently(eventId: String, since: Long): Boolean
}