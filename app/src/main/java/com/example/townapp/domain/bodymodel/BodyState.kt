package com.example.townapp.domain.bodymodel

/**
 * 肉体状态数据类
 *
 * 存放睡眠、疲劳、健康、饮食等生理数据
 */
data class BodyState(
    var hunger: Double = 80.0,      // 饱腹度 0-100
    var energy: Double = 80.0,      // 精力 0-100
    var health: Double = 70.0,      // 健康 0-100
    var age: Int = 20,              // 年龄
    var workingYears: Int = 0       // 工作年限
)
