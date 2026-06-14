package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "product",
    indices = [
        Index(value = ["hideInChild"]),
        Index(value = ["category"]),
        Index(value = ["name"])
    ]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String,
    val subCategory: String = "",
    val marketPrice: Double,
    val description: String = "",
    val nutritionValue: String = "",
    val medicalValue: String = "",
    val mentalValue: String = "",
    val socialValue: String = "",
    val wearRate: Double = 0.0,
    val lifeCycle: Int = 0,
    val monthlyMaintainCost: Double = 0.0,
    val timeCostDaily: Double = 0.0,
    val secondHandRate: Double = 100.0,
    val infoAsymmetry: Double = 0.0,
    val emotionalPremium: Double = 0.0,
    val sunkCostIndex: Double = 0.0,
    val iqTaxScore: Double = 0.0,
    val hideInChild: Boolean = false,
    /** 物品闪光点文案（v10 融合新增） */
    val sparkleDesc: String = "",
    /** 购置此物品所需劳动小时数（v1.3 时薪体系） */
    val workHourCost: Double = 0.0,
    val createTime: Long = System.currentTimeMillis()
)