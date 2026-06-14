package com.example.townapp.domain.spacemodel

/**
 * 空间状态快照 —— 纯不可变数据类
 */
data class SpaceState(
    val rent: Double = 1500.0,     // 月租
    val salary: Double = 3000.0,   // 月薪
    val commuteMinutesOneWay: Int = 30, // 单程通勤时间（分钟）
    val light: Int = 60,           // 采光 0-100
    val noise: Int = 40,           // 噪音 0-100
    val cleanliness: Int = 70,     // 清洁度 0-100
    val ventilation: Int = 60,     // 通风 0-100
    val size: Int = 30             // 面积（平米）
)
