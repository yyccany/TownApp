package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actions")
data class ActionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val subType: String = "",
    val amount: Double = 0.0,
    val metadata: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)