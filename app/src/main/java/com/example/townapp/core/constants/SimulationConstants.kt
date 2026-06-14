package com.example.townapp.core.constants

/**
 * 全局常量：生理参数默认值、时间基准
 */

object SimulationConstants {
    // 时间参数
    const val HOURS_PER_DAY = 24
    const val DAYS_PER_MONTH = 30
    const val MONTHS_PER_YEAR = 12
    const val DAYS_PER_WEEK = 7

    // 生理参数默认值
    const val DEFAULT_HUNGER = 80.0
    const val DEFAULT_ENERGY = 85.0
    const val DEFAULT_HEALTH = 85.0
    const val DEFAULT_HAPPINESS = 65.0
    const val DEFAULT_ANXIETY = 45.0
    const val DEFAULT_LONELINESS = 30.0
    const val DEFAULT_CONTROL = 50.0
    const val DEFAULT_MONEY = 2000.0
    const val DEFAULT_SKILL_LEVEL = 30.0
    const val DEFAULT_PRESSURE = 20.0

    // 衰减参数
    const val HUNGER_DECAY = 1.0
    const val ENERGY_DECAY = 0.5
    const val PRESSURE_DECAY = 0.05

    // 空间参数
    const val DEFAULT_RENT = 1500.0
    const val DEFAULT_SALARY = 3000.0

    // 触发概率
    const val MICRO_EVENT_PROBABILITY = 0.3
    const val IDIOM_PROBABILITY = 0.2
    const val SPARKLE_PROBABILITY = 0.25
    const val AESTHETIC_PROBABILITY = 0.3

    // 健康参数
    const val HEALTH_FOOD_BONUS = 8.0
    const val REST_ENERGY_BONUS = 25.0
    const val SOCIAL_HAPPINESS_BONUS = 12.0

    // 事件冷却
    const val DEFAULT_COOLDOWN_DAYS = 3

    // 时间换算
    const val MILLIS_PER_DAY = 24L * 60 * 60 * 1000
}
