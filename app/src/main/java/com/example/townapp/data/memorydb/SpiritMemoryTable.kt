package com.example.townapp.data.memorydb

import com.example.townapp.domain.engine.PlayerState

/**
 * 精神/认知实时内存表
 *
 * 职责：
 * - 内存存储心理状态、认知记录、观念数据、事件记录
 * - 关闭 App 自动清空，无任何持久化逻辑
 * - 切换模型/重置时由仓库主动清空
 */
object SpiritMemoryTable {
    var happiness: Double = 60.0
    var anxiety: Double = 30.0
    var loneliness: Double = 20.0
    var control: Double = 50.0
    var generationalPressure: Double = 0.0
    var generationalPressureEnabled: Boolean = true

    /** 当日事件记录 */
    val dailyEvents = mutableListOf<String>()

    /** 历史事件记录（跨天） */
    val eventHistory = mutableListOf<String>()

    /** 成语记录 */
    val idiomHistory = mutableListOf<String>()

    /**
     * 从 PlayerState 同步到内存表
     */
    fun syncFrom(state: PlayerState) {
        happiness = state.happiness
        anxiety = state.anxiety
        loneliness = state.loneliness
        control = state.control
        generationalPressure = state.generationalPressure
        generationalPressureEnabled = state.generationalPressureEnabled
    }

    /**
     * 同步到 PlayerState —— 返回新的不可变实例
     */
    fun syncTo(state: PlayerState): PlayerState = state.copy(
        happiness = happiness,
        anxiety = anxiety,
        loneliness = loneliness,
        control = control,
        generationalPressure = generationalPressure,
        generationalPressureEnabled = generationalPressureEnabled
    )

    /** 添加当日事件 */
    fun addDailyEvent(event: String) {
        dailyEvents.add(event)
    }

    /** 添加历史事件 */
    fun addEventHistory(event: String) {
        eventHistory.add(event)
        if (eventHistory.size > 100) {
            eventHistory.removeAt(0)
        }
    }

    /** 添加成语记录 */
    fun addIdiom(idiom: String) {
        idiomHistory.add(idiom)
        if (idiomHistory.size > 50) {
            idiomHistory.removeAt(0)
        }
    }

    /** 清空 */
    fun clear() {
        happiness = 60.0
        anxiety = 30.0
        loneliness = 20.0
        control = 50.0
        generationalPressure = 0.0
        generationalPressureEnabled = true
        dailyEvents.clear()
        eventHistory.clear()
        idiomHistory.clear()
    }
}
