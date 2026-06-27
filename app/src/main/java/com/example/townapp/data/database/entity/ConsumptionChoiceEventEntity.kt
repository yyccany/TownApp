package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 消费抉择事件表（tb_consumption_choice_event）
 *
 * 记录玩家遇到的消费抉择事件，以及玩家的选择。
 * 每个事件都是一个"人生十字路口"，展示两种消费观的不同取舍。
 *
 * 设计原则：
 * 1. 无对错评判，只展示不同选择的长期后果
 * 2. 每个事件都有两个选项：人本路线 vs 虚荣路线
 * 3. 文案全部走 TextAssetLoader，Entity 只存 ID 和状态
 */
@Entity(tableName = "tb_consumption_choice_event")
data class ConsumptionChoiceEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    /** 事件ID（对应文案资源） */
    val eventId: String,

    /** 事件类型（food/clothing/housing/transport） */
    val eventType: String,

    /** 触发时的玩家年龄 */
    val triggerAge: Int,

    /** 玩家的选择：0=未选择，1=人本路线，2=虚荣路线 */
    val playerChoice: Int = 0,

    /** 选择时间戳 */
    val choiceTime: Long = 0L,

    /** 是否已触发 */
    val isTriggered: Boolean = false,

    /** 触发时间戳 */
    val triggerTime: Long = 0L,

    /** 创建时间 */
    val createTime: Long = System.currentTimeMillis()
)
