package com.example.townapp.data.memorydb

import com.example.townapp.domain.engine.PlayerState

/**
 * 肉体实时内存表
 *
 * 职责：
 * - 内存存储玩家肉体相关实时数据
 * - 关闭 App 自动清空，无任何持久化逻辑
 * - 切换模型/重置时由仓库主动清空
 */
object BodyMemoryTable {
    var hunger: Double = 80.0
    var energy: Double = 80.0
    var health: Double = 70.0
    var age: Int = 20
    var workingYears: Int = 0

    /**
     * 从 PlayerState 同步到内存表
     */
    fun syncFrom(state: PlayerState) {
        hunger = state.hunger
        energy = state.energy
        health = state.health
        age = state.age
        workingYears = state.workingYears
    }

    /**
     * 同步到 PlayerState —— 返回新的不可变实例
     */
    fun syncTo(state: PlayerState): PlayerState = state.copy(
        hunger = hunger,
        energy = energy,
        health = health,
        age = age,
        workingYears = workingYears
    )

    /** 清空 */
    fun clear() {
        hunger = 80.0
        energy = 80.0
        health = 70.0
        age = 20
        workingYears = 0
    }
}
