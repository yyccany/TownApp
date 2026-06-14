package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_history")
data class EventHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val eventId: String = "",            // 新增：事件唯一标识
    val eventType: String,
    val description: String = "",
    val effects: String = "",            // 新增：事件效果JSON
    val isNegative: Boolean = false,     // 新增：是否为负面事件
    val durationDays: Int = 0,           // 新增：持续天数
    val timestamp: Long = System.currentTimeMillis()
)