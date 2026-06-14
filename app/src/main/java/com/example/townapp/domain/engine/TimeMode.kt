package com.example.townapp.domain.engine

/**
 * 模拟时间运行模式。
 *
 * - [FOREGROUND]：前台实时模式，正常时间节拍
 * - [BACKGROUND]：后台静默模式，降低刷新频率
 * - [FAST_FORWARD]：手动快进模式，批量推进时间
 */
enum class TimeMode {
    FOREGROUND,
    BACKGROUND,
    FAST_FORWARD
}
