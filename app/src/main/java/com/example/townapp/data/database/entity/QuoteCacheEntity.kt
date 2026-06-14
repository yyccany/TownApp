package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote_cache")
data class QuoteCacheEntity(
    @PrimaryKey val cacheKey: String,
    val useTime: Long
)