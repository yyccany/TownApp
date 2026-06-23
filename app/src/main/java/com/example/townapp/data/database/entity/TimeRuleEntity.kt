package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 时间规则配置表（tb_time_rule）
 *
 * 严格遵循实事求是：
 * - 存储游戏底层时间规则参数，全部可配置
 * - 单日逻辑总量固定 1440 分钟，保证人人均等
 * - 流速倍率由玩家自由调节，存储供跨会话使用
 */
@Entity(tableName = "tb_time_rule")
data class TimeRuleEntity(
    @PrimaryKey
    val id: Int = 1,  // 全局唯一配置行

    /** 单日逻辑分钟总量（固定 1440，从不动摇） */
    val dayTotalLogicMin: Int = 1440,

    /** 深夜时段开始小时（固定 22） */
    val nightStartHour: Int = 22,

    /** 深夜时段结束小时（固定 6） */
    val nightEndHour: Int = 6,

    /** 深夜时段总分钟数（固定 480） */
    val nightTotalMinutes: Int = 480,

    /** 玩家上次使用的流速倍率 */
    val lastUsedSpeed: Float = 1f,

    /** 系统预设最大倍率 */
    val maxSpeedRate: Float = 8f,

    /** 系统预设最小倍率 */
    val minSpeedRate: Float = 0.25f,

    /** 系统预设默认倍率 */
    val defaultSpeedRate: Float = 1f,

    /** 每逻辑分钟对应的基准现实毫秒数（1x速度 = 60000ms） */
    val baseRealMsPerLogicMin: Long = 60000L,

    /** 版本号（用于迁移校验） */
    val version: Int = 1
) {
    companion object {
        /** 获取默认时间规则 */
        fun getDefault(): TimeRuleEntity = TimeRuleEntity()
    }
}
