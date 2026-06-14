package com.example.townapp.core.state

/**
 * 全局状态枚举
 */

enum class WorldType {
    URBAN,      // 都市
    SMALL_TOWN, // 小城
    CAMPUS,     // 校园
    WANDERER    // 漂泊
}

enum class SimulationStatus {
    IDLE,       // 空闲
    RUNNING,    // 运行中
    PAUSED,     // 暂停
    FAST_FORWARD // 快进中
}

enum class TimeMode {
    FOREGROUND,     // 前台实时
    BACKGROUND,     // 后台静默
    FAST_FORWARD    // 手动快进
}
