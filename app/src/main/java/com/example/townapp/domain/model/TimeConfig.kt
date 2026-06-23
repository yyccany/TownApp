package com.example.townapp.domain.model

import com.example.townapp.core.constants.SimulationConstants

/**
 * 时间配置数据类（存储玩家时间偏好）
 *
 * 严格遵循实事求是：
 * - 所有时间参数可配置，存储在数据库中
 * - 倍率选项固定为系统预设值（0.25x/0.5x/1x/2x/4x/8x）
 *
 * 严格遵循自由平等：
 * - 玩家自由选择倍率，无强制
 * - 所有玩家共用同一套倍率选项，无差异化
 */
data class TimeConfig(
    /** 当前流速倍率（0 = 暂停） */
    val speedMultiplier: Float = SimulationConstants.DEFAULT_SPEED,

    /** 是否首次设置（用于引导） */
    val isFirstSetup: Boolean = true,

    /** 上次使用倍率的记录（用于推荐） */
    val lastUsedSpeed: Float = SimulationConstants.DEFAULT_SPEED
) {
    companion object {
        /** 系统预设的倍率选项 */
        val SPEED_OPTIONS = SimulationConstants.SPEED_OPTIONS

        /** 默认配置 */
        fun defaultConfig(): TimeConfig = TimeConfig(
            speedMultiplier = SimulationConstants.DEFAULT_SPEED,
            isFirstSetup = true,
            lastUsedSpeed = SimulationConstants.DEFAULT_SPEED
        )
    }

    /** 是否暂停 */
    val isPaused: Boolean get() = speedMultiplier <= 0f

    /** 获取可读的倍率描述 */
    fun getSpeedLabel(): String = when (speedMultiplier) {
        0f -> "暂停"
        0.25f -> "0.25x 慢放"
        0.5f -> "0.5x 慢放"
        1f -> "1x 正常"
        2f -> "2x 快进"
        4f -> "4x 快进"
        8f -> "8x 快进"
        else -> "${speedMultiplier}x"
    }
}
