package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "quotes",
    indices = [androidx.room.Index(value = ["roleId", "sceneId"])]
)
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val roleId: String,
    val sceneId: String,
    val childContent: String,
    val adultContent: String,
    val consumptionMin: Int = 0,
    val consumptionMax: Int = 100,
    val createTime: Long = System.currentTimeMillis()
)