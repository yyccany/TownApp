package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buildings")
data class BuildingEntity(
    @PrimaryKey val id: Int,
    val buildingId: String = "",        // 逻辑建筑ID
    val name: String = "",
    val category: String = "",
    val district: String = "",          // 区域
    val level: Int = 1,
    val count: Int = 1,
    val cost: Double = 0.0,
    val description: String = "",
    val daysWithoutTrigger: Int = 0,    // 无触发天数
    val isActive: Boolean = true,       // 是否激活

    // ============================================
    // 人生身份匹配（v10 新增）
    // ============================================
    /** 匹配的人生路径 ID（空=通用） */
    val matchLifePathId: String = ""
)