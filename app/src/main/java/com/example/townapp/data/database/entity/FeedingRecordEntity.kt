package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/** 喂食记录 Room 实体 —— 每次喂食的历史 */
@Entity(tableName = "feeding_records")
data class FeedingRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val residentId: Int,           // 被喂的居民 ID
    val residentName: String,      // 居民名字
    val dishName: String,          // 菜品名
    val bloodSugarChange: Double,  // 血糖变化量
    val moodChange: Int,           // 情绪变化量
    val healthChange: Int,         // 健康变化量
    val reaction: String,          // 一句话反应文案
    val reactionEmoji: String,     // 反应表情
    val timestamp: Long = System.currentTimeMillis()
)