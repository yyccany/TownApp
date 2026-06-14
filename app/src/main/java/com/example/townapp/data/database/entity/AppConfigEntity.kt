package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_config")
data class AppConfigEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val isChildMode: Boolean = true,
    val defaultRole: String = "taffi",
    val eventFrequency: Int = 2,
    val viewType: Int = 1,
    val createTime: Long = System.currentTimeMillis()
)