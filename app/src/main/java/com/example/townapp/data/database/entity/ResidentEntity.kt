package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.townapp.feature.food.ResidentHealth

/** 居民健康状态 Room 实体 —— 持久化喂食后的身体数据 */
@Entity(tableName = "residents")
data class ResidentEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val age: Int,
    val bloodSugar: Double,       // 血糖 mmol/L
    val bloodPressure: Int,       // 收缩压 mmHg
    val mood: Int,                // 情绪 0-100
    val energy: Int,              // 精力 0-100
    val healthScore: Int,         // 综合健康 0-100
    val lastMeal: String = ""     // 最近吃的菜品名
)

/** Room 实体 → 内存模型 */
fun ResidentEntity.toResidentHealth() = ResidentHealth(
    id = id, name = name, age = age,
    bloodSugar = bloodSugar, bloodPressure = bloodPressure,
    mood = mood, energy = energy, healthScore = healthScore,
    lastMeal = lastMeal
)

/** 内存模型 → Room 实体 */
fun ResidentHealth.toEntity() = ResidentEntity(
    id = id, name = name, age = age,
    bloodSugar = bloodSugar, bloodPressure = bloodPressure,
    mood = mood, energy = energy, healthScore = healthScore,
    lastMeal = lastMeal
)