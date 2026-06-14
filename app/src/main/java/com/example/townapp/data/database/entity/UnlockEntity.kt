package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unlocks")
data class UnlockEntity(
    @PrimaryKey val id: Int,
    val name: String = "",
    val category: String = "",
    val isUnlocked: Boolean = false
)