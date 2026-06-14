package com.example.townapp.data.repository

import com.example.townapp.domain.engine.PlayerState

/**
 * 🌟 可接入模拟引擎的数据统一接口
 * 
 * 所有想融入模拟的数据库，都实现这个接口
 * 引擎只对接接口，不认具体数据类
 * 
 * 核心原则：
 * - 可触发：canTrigger() 返回true时才调用
 * - 可计算：getStateEffect() 返回具体状态变化
 * - 可展示：getDisplayText() 返回前端文案
 */
interface ISimulatableData {
    
    /** 数据唯一标识 */
    val id: String
    
    /** 数据名称 */
    val name: String
    
    /**
     * 判断是否可以触发
     * @param currentState 当前用户状态
     * @return true表示可以触发
     */
    fun canTrigger(currentState: PlayerState): Boolean
    
    /**
     * 获取对状态的影响
     * @return 状态变化量
     */
    fun getStateEffect(): StateEffect
    
    /**
     * 获取展示文案
     * @return 给前端显示的文本内容
     */
    fun getDisplayText(): String
    
    /**
     * 触发权重（用于随机选择）
     * @return 权重值，越大越容易被选中
     */
    fun getTriggerWeight(): Int = 10
}

/**
 * 📊 状态影响数据类
 * 
 * 定义数据对玩家状态的具体影响
 */
data class StateEffect(
    // 肉体状态影响
    val hunger: Double = 0.0,
    val energy: Double = 0.0,
    val health: Double = 0.0,
    
    // 精神状态影响
    val happiness: Double = 0.0,
    val anxiety: Double = 0.0,
    val loneliness: Double = 0.0,
    val control: Double = 0.0,
    
    // 其他影响
    val money: Double = 0.0,
    val skillLevel: Double = 0.0,
    val generationalPressure: Double = 0.0
) {
    /** 合并两个影响 */
    operator fun plus(other: StateEffect): StateEffect {
        return StateEffect(
            hunger = this.hunger + other.hunger,
            energy = this.energy + other.energy,
            health = this.health + other.health,
            happiness = this.happiness + other.happiness,
            anxiety = this.anxiety + other.anxiety,
            loneliness = this.loneliness + other.loneliness,
            control = this.control + other.control,
            money = this.money + other.money,
            skillLevel = this.skillLevel + other.skillLevel,
            generationalPressure = this.generationalPressure + other.generationalPressure
        )
    }
}

/**
 * 🎰 触发场景标签
 * 
 * 用于匹配数据的触发场景
 */
object TriggerScene {
    const val HEALTH_EVENT = "health_event"
    const val BODY_OVERLOAD = "body_overload"
    const val SLEEP_DEPRIVATION = "sleep_deprivation"
    const val ANXIETY_HIGH = "anxiety_high"
    const val DAILY_SETTLEMENT = "daily_settlement"
    const val ENDING_GENERATION = "ending_generation"
    const val SPACE_CHANGE = "space_change"
    const val PATH_CHANGE = "path_change"
}

/**
 * ⚙️ 模拟数据开关控制
 * 
 * 每一批接入都有独立开关，可一键回滚
 */
object SimulationDataSwitch {
    var enableSpaceEffects = false      // 空间影响
    var enableEventEffects = false      // 事件影响
    var enableIdiomEffects = false      // 成语影响
    var enablePathEffects = false       // 人生路径影响
    var enableGenerationalEffects = false // 代际影响
    
    /** 一键开启所有开关 */
    fun enableAll() {
        enableSpaceEffects = true
        enableEventEffects = true
        enableIdiomEffects = true
        enablePathEffects = true
        enableGenerationalEffects = true
    }
    
    /** 一键关闭所有开关 */
    fun disableAll() {
        enableSpaceEffects = false
        enableEventEffects = false
        enableIdiomEffects = false
        enablePathEffects = false
        enableGenerationalEffects = false
    }
}
