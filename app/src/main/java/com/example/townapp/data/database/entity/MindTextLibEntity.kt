package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 精神独白文案库 —— 深夜独白/梦境/内心提示统一管理
 * 数据驱动，修改文案不用改代码重编译
 *
 * type分类：
 * - night_mono：深夜入睡前独白
 * - sweet_dream：好梦/治愈梦境
 * - nightmare：噩梦/焦虑梦境
 * - daily_thought：日常随机内心提示
 */
@Entity(
    tableName = "mind_text_lib",
    indices = [Index(value = ["type"])]
)
data class MindTextLibEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val consumptionMin: Int = 0,
    val consumptionMax: Int = 100,
    val weight: Int = 10, // 触发权重，越大越容易被选中
    val content: String,
    val createTime: Long = System.currentTimeMillis()
)
