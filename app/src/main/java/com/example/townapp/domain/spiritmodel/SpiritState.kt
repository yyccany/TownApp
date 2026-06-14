package com.example.townapp.domain.spiritmodel

/**
 * 精神状态数据类
 *
 * 存放心理状态、认知记录、观念数据
 */
data class SpiritState(
    var happiness: Double = 60.0,           // 幸福感 0-100
    var anxiety: Double = 30.0,             // 焦虑 0-100
    var loneliness: Double = 20.0,          // 孤独感 0-100
    var control: Double = 50.0,             // 控制感 0-100
    var generationalPressure: Double = 0.0, // 代际压力 0-100
    var generationalPressureEnabled: Boolean = true
)
