package com.example.townapp.domain.engine

/**
 * 玩家状态快照 —— 纯不可变数据类。
 *
 * 汇总角色在肉体、精神、资产、家庭等多维度的当前状态。
 * 所有字段均为 `val`，状态变更通过 [copy] 生成新实例。
 */
data class PlayerState(
    // 人生路径ID（来自LifePathData）
    val lifePathId: String = "fresh_graduate",

    // 职业ID（来自CareerPathSystem）
    val careerId: String = "civil_servant",

    // 年龄与生涯
    val age: Int = 20,
    val workingYears: Int = 0,

    // 肉体状态
    val hunger: Double = 80.0,
    val energy: Double = 80.0,
    val health: Double = 70.0,
    val fatigue: Double = 0.0,

    // 精神状态
    val happiness: Double = 60.0,
    val anxiety: Double = 30.0,
    val loneliness: Double = 20.0,
    val control: Double = 50.0,
    val trauma: Double = 0.0,

    // 资产与技能
    val money: Double = 5000.0,
    val assets: Double = 0.0,
    val skillLevel: Double = 50.0,

    // 代际压力
    val generationalPressure: Double = 0.0,
    val generationalPressureEnabled: Boolean = true,

    // 家庭状态
    val hasFamily: Boolean = false,
    val familyPressure: Double = 0.0,
    val housingDebt: Double = 0.0,

    // 每日统计
    val dailyMoneyChange: Double = 0.0,
    val dailyMaxHunger: Double = 0.0,
    val dailyMinEnergy: Double = 100.0,
    val dailyAvgHappiness: Double = 0.0,
    val dailyEvents: List<String> = emptyList(),
    
    // 双轨收入统计
    val dailyLaborIncome: Double = 0.0,
    val dailyCompoundIncome: Double = 0.0,
    val dailyWorkMinutes: Int = 0,

    // 服装与交通
    val clothingBonus: Double = 0.0,
    val transportCost: Double = 0.0
) {
    /**
     * 每日重置 —— 返回新的不可变实例，清空当日累计统计。
     */
    fun dailyReset(): PlayerState = copy(
        dailyMoneyChange = 0.0,
        dailyMaxHunger = hunger,
        dailyMinEnergy = energy,
        dailyAvgHappiness = happiness,
        dailyEvents = emptyList(),
        dailyLaborIncome = 0.0,
        dailyCompoundIncome = 0.0,
        dailyWorkMinutes = 0
    )
}
