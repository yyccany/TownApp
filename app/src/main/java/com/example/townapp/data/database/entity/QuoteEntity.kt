package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val roleId: String,
    val sceneId: String,
    val childContent: String,
    val adultContent: String,
    val createTime: Long = System.currentTimeMillis()
)