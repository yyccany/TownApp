package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户精神状态表（tb_user_mental_state）
 * 精神状态不是一个单一的"心情值"，而是一个复杂的多维度情绪体系。
 * 它决定了你有没有动力去做任何事情。
 *
 * 设计原则：所有维度都是 0-100 的分值
 * - 正向维度（快乐、掌控、自尊、意义）：越高越好
 * - 负向维度（焦虑、孤独）：越低越好
 */
@Entity(tableName = "tb_user_mental_state")
data class UserMentalState(
    @PrimaryKey val userId: Int,               // 居民ID

    // ============================================
    // 核心六维精神状态
    // ============================================
    val happiness: Int = 70,                    // 快乐感 0-100（美食、娱乐、社交、成就带来）
    val anxiety: Int = 20,                      // 焦虑感 0-100（存款不足、失业、健康问题、未来不确定性带来）
    val loneliness: Int = 30,                   // 孤独感 0-100（独居时间过长、缺乏社交、没有亲密关系带来）
    val senseOfControl: Int = 65,               // 掌控感 0-100（存款余额、工作稳定性、居住稳定性带来）
    val selfEsteem: Int = 60,                   // 自尊感 0-100（收入水平、职业地位、居住环境带来）
    val meaning: Int = 50,                      // 意义感 0-100（目标实现、自我成长、帮助他人带来）

    // ============================================
    // 辅助维度（用于计算和展示）
    // ============================================
    val energyLevel: Int = 75,                  // 心理能量 0-100（综合反映精神状态好坏）
    val sleepQuality: Int = 70,                 // 睡眠质量 0-100（受噪音、采光、焦虑影响）
    val socialFulfillment: Int = 50,            // 社交满足度 0-100
    val creativeFlow: Int = 40,                 // 心流体验 0-100（沉浸式做事的满足感）

    // ============================================
    // 状态追踪
    // ============================================
    val daysSinceSocialContact: Int = 3,        // 距离上次社交接触的天数
    val daysSinceAchievement: Int = 7,          // 距离上次成就感的天数
    val daysInNegativeState: Int = 0,           // 持续负面状态的天数（用于触发崩溃）

    // ============================================
    // 工时-精神联动（v1.4 第二阶段）
    // ============================================
    val workStress: Int = 30,                   // 工作压力 0-100（加班、通勤、工作强度带来）
    val lifePathType: String = "",              // 生活路径：hustle/balanced/rest
    val workSatisfaction: Int = 50,             // 工作满意度 0-100
    val burnoutRisk: Int = 15,                  // 职业倦怠风险 0-100
    val traumaLevel: Int = 0,                   // 心理创伤值 0-100（长期压力累积）

    // ============================================
    // 记录
    // ============================================
    val updateTime: Long = System.currentTimeMillis()
) {
    /**
     * 综合精神健康评分（0-100）
     * 加权计算：快乐(20%) + 低焦虑(20%) + 低孤独(15%) + 掌控(15%) + 自尊(15%) + 意义(15%)
     */
    val overallMentalHealth: Int
        get() {
            val score = (happiness * 0.20) +
                    ((100 - anxiety) * 0.20) +
                    ((100 - loneliness) * 0.15) +
                    (senseOfControl * 0.15) +
                    (selfEsteem * 0.15) +
                    (meaning * 0.15)
            return score.toInt().coerceIn(0, 100)
        }

    /**
     * 是否处于焦虑边缘（焦虑 > 70）
     */
    val isAnxious: Boolean get() = anxiety > 70

    /**
     * 是否处于抑郁倾向（孤独 > 70 且 快乐 < 30）
     */
    val isDepressed: Boolean get() = loneliness > 70 && happiness < 30

    /**
     * 是否处于失控状态（掌控 < 20）
     */
    val isOutOfControl: Boolean get() = senseOfControl < 20

    /**
     * 是否存在意义危机（意义 < 15）
     */
    val hasMeaningCrisis: Boolean get() = meaning < 15

    /**
     * 整体状态等级
     */
    val mentalStatus: MentalStatus
        get() = when {
            overallMentalHealth >= 75 -> MentalStatus.FLOURISHING     // 蓬勃发展
            overallMentalHealth >= 55 -> MentalStatus.BALANCED        // 平衡稳定
            overallMentalHealth >= 35 -> MentalStatus.STRUGGLING      // 艰难挣扎
            overallMentalHealth >= 20 -> MentalStatus.BURNOUT         // 精疲力竭
            else -> MentalStatus.BREAKDOWN                            // 濒临崩溃
        }

    /**
     * 行为动力修正（影响玩家的行为选择）
     * 返回值：-1.0 ~ +1.0（负数表示动力降低，正数表示动力增加）
     */
    val motivationModifier: Double
        get() {
            var mod = 0.0
            mod += (happiness - 50) * 0.005       // 快乐每偏离50分，改变0.25
            mod -= (anxiety - 30) * 0.008         // 焦虑每超过30，降低0.08
            mod -= (loneliness - 30) * 0.005      // 孤独每超过30，降低0.05
            mod += (senseOfControl - 50) * 0.006  // 掌控每偏离50，改变0.3
            mod += (meaning - 40) * 0.004         // 意义每偏离40，改变0.2
            return mod.coerceIn(-1.0, 1.0)
        }

    /**
     * 将所有维度约束在合法范围内
     */
    fun coerce(): UserMentalState = copy(
        happiness = happiness.coerceIn(0, 100),
        anxiety = anxiety.coerceIn(0, 100),
        loneliness = loneliness.coerceIn(0, 100),
        senseOfControl = senseOfControl.coerceIn(0, 100),
        selfEsteem = selfEsteem.coerceIn(0, 100),
        meaning = meaning.coerceIn(0, 100),
        energyLevel = energyLevel.coerceIn(0, 100),
        sleepQuality = sleepQuality.coerceIn(0, 100),
        socialFulfillment = socialFulfillment.coerceIn(0, 100),
        creativeFlow = creativeFlow.coerceIn(0, 100),
        workStress = workStress.coerceIn(0, 100),
        workSatisfaction = workSatisfaction.coerceIn(0, 100),
        burnoutRisk = burnoutRisk.coerceIn(0, 100),
        traumaLevel = traumaLevel.coerceIn(0, 100)
    )
}

/**
 * 精神状态等级
 */
enum class MentalStatus(val displayName: String, val emoji: String, val description: String) {
    FLOURISHING("蓬勃", "🌞", "状态很好，对生活充满热情"),
    BALANCED("稳定", "🌤️", "整体平衡，偶尔有小波动"),
    STRUGGLING("挣扎", "⛅", "日子有点难，但还在撑着"),
    BURNOUT("疲惫", "🌧️", "身心俱疲，需要好好休息"),
    BREAKDOWN("崩溃", "⛈️", "濒临崩溃，需要停下来")
}

/**
 * 生活路径类型（v1.4 工时-身心联动）
 */
enum class LifePathType(val displayName: String, val description: String) {
    HUSTLE("奋斗", "主动加班、多接活，收入更高，但身体负担也更重"),
    BALANCED("劳逸结合", "按部就班工作，该休息就休息，收入与健康取个平衡"),
    REST("躺平", "能少干就少干，留出时间给自己，收入低一些但活得轻松")
}
