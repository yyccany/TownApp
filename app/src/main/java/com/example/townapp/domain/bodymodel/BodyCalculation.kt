package com.example.townapp.domain.bodymodel

import com.example.townapp.domain.engine.PlayerState
import kotlin.math.max
import kotlin.math.min

/**
 * 肉体数值计算引擎
 *
 * 职责：
 * - 健康值综合计算（饱腹、精力、精神、年龄、家庭压力、房贷）
 * - 年龄系数计算（影响健康损耗速率）
 *
 * 设计原则：
 * - 纯函数，不修改外部状态
 * - 输入 PlayerState，输出计算结果
 * - 由 SimulationEngine 调用后自行赋值
 */
object BodyCalculation {

    /**
     * 获取年龄系数（影响健康损耗速率）
     * 中年角色健康损耗更快
     */
    fun getAgeFactor(age: Int): Double {
        return when {
            age < 25 -> 0.8
            age < 35 -> 1.0
            age < 45 -> 1.3
            else -> 1.5
        }
    }

    /**
     * 综合计算健康值
     *
     * 影响因素：
     * - 肉体：饱腹度、精力
     * - 精神：幸福感、焦虑度
     * - 空间/社会：家庭压力、房贷压力
     * - 生理：年龄系数
     */
    fun calculateHealth(state: PlayerState): Double {
        var health = state.health

        val ageFactor = getAgeFactor(state.age)

        // 饱腹影响
        if (state.hunger < 20) health -= 2.0 * ageFactor
        if (state.hunger > 80) health += 1.0

        // 精力影响
        if (state.energy < 20) health -= 2.0 * ageFactor
        if (state.energy > 80) health += 1.0

        // 精神状态影响
        if (state.happiness < 30) health -= 1.0 * ageFactor
        if (state.anxiety > 70) health -= 1.0 * ageFactor

        // 家庭压力影响
        if (state.familyPressure > 30) health -= 0.5 * ageFactor

        // 房贷压力影响
        if (state.housingDebt > 0) health -= 0.3 * ageFactor

        return max(0.0, min(100.0, health))
    }
}
