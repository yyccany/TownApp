package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "material",
    indices = [
        Index(value = ["category"]),
        Index(value = ["name"])
    ]
)
data class MaterialEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String,
    val unit: String,
    val unitCost: Double,
    val calories: Double = 0.0,
    val protein: Double = 0.0,
    val fat: Double = 0.0,
    val carbs: Double = 0.0,
    val sodium: Double = 0.0,
    val medicalValue: String = "",
    /** 物品闪光点文案（v10 融合新增） */
    val sparkleDesc: String = "",
    /** 购置此物品所需劳动小时数（v1.3 时薪体系） */
    val workHourCost: Double = 0.0,
    val createTime: Long = System.currentTimeMillis()
)