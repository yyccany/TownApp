package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 生活事件日志实体
 *
 * v10 融合：新增物品交互追踪字段
 */
@Entity(tableName = "life_event_log")
data class LifeEventLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val eventType: String,
    val triggerTime: Long = System.currentTimeMillis(),

    // ============================================
    // 物品交互追踪（v10 新增）
    // ============================================
    /** 是否查看了物品闪光点 */
    val isViewSparkle: Boolean = false,
    /** 交互物品名称 */
    val itemName: String = "",
    /** 关联的生命路径 ID */
    val relatedLifePathId: String = "",

    // ============================================
    // 时薪消费记录（v1.3 时薪-物品价值锐评）
    // ============================================
    /** 物品价格 */
    val itemPrice: Double = 0.0,
    /** 购置此物品所耗费的劳动小时数 */
    val workHourCost: Double = 0.0
)