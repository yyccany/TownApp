package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "npcs")
data class NpcEntity(
    @PrimaryKey val id: Int,
    val npcId: String = "",             // 新增：逻辑NPC ID
    val name: String = "",
    val role: String = "",
    val mood: String = "正常",
    val relationship: Int = 0,
    val isNegative: Boolean = false,    // 新增：是否负面NPC
    val avatar: String? = null          // 新增：头像表情
)