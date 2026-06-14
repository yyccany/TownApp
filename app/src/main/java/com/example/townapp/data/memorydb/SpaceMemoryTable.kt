package com.example.townapp.data.memorydb

import com.example.townapp.domain.engine.PlayerState
import com.example.townapp.domain.spacemodel.SpaceState

/**
 * 空间/经济实时内存表
 *
 * 职责：
 * - 内存存储场景、经济、社会关系数据
 * - 关闭 App 自动清空，无任何持久化逻辑
 * - 切换模型/重置时由仓库主动清空
 */
object SpaceMemoryTable {
    var rent: Double = 1000.0
    var salary: Double = 3500.0
    var commuteMinutesOneWay: Int = 30
    var light: Int = 60
    var noise: Int = 40
    var cleanliness: Int = 70
    var ventilation: Int = 60
    var size: Int = 30

    // 经济与社会
    var money: Double = 5000.0
    var skillLevel: Double = 50.0
    var hasFamily: Boolean = false
    var familyPressure: Double = 0.0
    var housingDebt: Double = 0.0
    var clothingBonus: Double = 0.0
    var transportCost: Double = 0.0

    /**
     * 从 SpaceState 同步到内存表
     */
    fun syncFromSpace(state: SpaceState) {
        rent = state.rent
        salary = state.salary
        commuteMinutesOneWay = state.commuteMinutesOneWay
        light = state.light
        noise = state.noise
        cleanliness = state.cleanliness
        ventilation = state.ventilation
        size = state.size
    }

    /**
     * 同步到 SpaceState —— 返回新的不可变实例
     */
    fun syncToSpace(state: SpaceState): SpaceState = state.copy(
        rent = rent,
        salary = salary,
        commuteMinutesOneWay = commuteMinutesOneWay,
        light = light,
        noise = noise,
        cleanliness = cleanliness,
        ventilation = ventilation,
        size = size
    )

    /**
     * 从 PlayerState 同步经济/社会字段
     */
    fun syncFromPlayer(state: PlayerState) {
        money = state.money
        skillLevel = state.skillLevel
        hasFamily = state.hasFamily
        familyPressure = state.familyPressure
        housingDebt = state.housingDebt
        clothingBonus = state.clothingBonus
        transportCost = state.transportCost
    }

    /**
     * 同步经济/社会字段到 PlayerState —— 返回新的不可变实例
     */
    fun syncToPlayer(state: PlayerState): PlayerState = state.copy(
        money = money,
        skillLevel = skillLevel,
        hasFamily = hasFamily,
        familyPressure = familyPressure,
        housingDebt = housingDebt,
        clothingBonus = clothingBonus,
        transportCost = transportCost
    )

    /** 清空 */
    fun clear() {
        rent = 1000.0
        salary = 3500.0
        commuteMinutesOneWay = 30
        light = 60
        noise = 40
        cleanliness = 70
        ventilation = 60
        size = 30
        money = 5000.0
        skillLevel = 50.0
        hasFamily = false
        familyPressure = 0.0
        housingDebt = 0.0
        clothingBonus = 0.0
        transportCost = 0.0
    }
}
