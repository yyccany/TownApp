package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户生理状态表（tb_user_body_state）
 * 用户私有动态表，存储角色/居民的实时生理数据。
 * 支持短期即时影响 + 长期累积变化。
 *
 * 设计原则：人首先是一个生物有机体，身体状态影响一切行为选择
 */
@Entity(tableName = "tb_user_body_state")
data class UserBodyState(
    @PrimaryKey val userId: Int,               // 居民ID
    val userName: String = "",                 // 居民名称

    // ============================================
    // 消化系统状态（第一层：吃进去的东西）
    // ============================================
    val satiety: Int = 70,                     // 饱腹感 0-100（低于20=饥饿，低于0开始掉健康）
    val nutritionBalance: Int = 50,            // 营养均衡度 0-100（蛋白质/碳水/脂肪/维生素比例）
    val gastroBurden: Int = 20,                // 肠胃负担 0-100（吃太油太辣太撑会升高）

    // ============================================
    // 生理指标（基础代谢）
    // ============================================
    val bloodSugar: Double = 5.2,              // 血糖 mmol/L（空腹正常 3.9-6.1）
    val bloodPressure: Int = 120,              // 收缩压 mmHg（正常 90-140）
    val heartRate: Int = 72,                   // 心率 bpm（正常 60-100）
    val bodyTemperature: Double = 36.5,        // 体温 ℃（正常 36.0-37.2）
    val bodyFatPercent: Double = 22.0,         // 体脂率 %
    val cholesterolMg: Double = 180.0,         // 血清胆固醇 mg/dL（正常 <200）

    // ============================================
    // 皮肤与感官系统（第二层：接触皮肤的东西）
    // ============================================
    val comfortLevel: Int = 70,                // 舒适度 0-100（受衣物材质、环境清洁度、湿度影响）
    val skinStatus: Int = 80,                  // 皮肤状态 0-100（受洗护用品、紫外线、压力影响）

    // ============================================
    // 毒素与免疫
    // ============================================
    val toxinLevel: Double = 0.0,             // 体内毒素累积值 0-100
    val toxinDecayRate: Double = 2.0,          // 毒素自然衰减速率（每周期）
    val heavyMetalAccum: Double = 0.0,         // 重金属累积量
    val immuneLevel: Int = 80,                 // 免疫水平 0-100（由营养均衡度+睡眠质量+运动决定）

    // ============================================
    // 能量与健康
    // ============================================
    val energy: Int = 80,                      // 精力 0-100
    val healthScore: Int = 85,                 // 综合健康评分 0-100
    val traumaLevel: Int = 0,                  // 创伤值 0-100（长期压力、负面事件累积）

    // ============================================
    // 情绪（基础情绪，受身体状态影响）
    // ============================================
    val mood: Int = 75,                        // 情绪 0-100

    // ============================================
    // 工时-疲劳联动系统（v1.4 第二阶段）
    // ============================================
    val dailyWorkHours: Double = 8.0,          // 今日实际工作时长
    val fatigueLevel: Int = 20,                 // 累积疲劳值 0-100（低于30=精力充沛，30-60=轻度疲劳，60-80=中度疲劳，80+重度疲劳）
    val overtimeStreakDays: Int = 0,            // 连续加班天数
    val weeklyWorkHours: Double = 0.0,          // 本周累计工作时长
    val totalOvertimeHours: Double = 0.0,       // 生涯累计加班时长

    // ============================================
    // 记录追踪
    // ============================================
    val lastMealTime: Long = 0L,               // 上次进食时间戳
    val lastMealName: String = "",             // 上次进食菜品
    val totalMeals: Int = 0,                   // 累计进食次数
    val highRiskMeals: Int = 0,                // 高风险进食次数
    val updateTime: Long = System.currentTimeMillis()  // 最后更新时间
) {
    /**
     * 快速判断是否处于不良状态
     */
    val isHungry: Boolean get() = satiety < 30
    val isTired: Boolean get() = energy < 30
    val isSick: Boolean get() = healthScore < 40 || immuneLevel < 30
    val isOverburdened: Boolean get() = gastroBurden > 70
    val hasFever: Boolean get() = bodyTemperature > 37.3
    val isLowBloodSugar: Boolean get() = bloodSugar < 3.5
    val isHighBloodSugar: Boolean get() = bloodSugar > 7.8

    /** 是否处于过度疲劳状态 */
    val isOverFatigued: Boolean get() = fatigueLevel > 75

    /** 疲劳等级 */
    val fatigueStatus: FatigueStatus
        get() = when {
            fatigueLevel <= 30 -> FatigueStatus.ENERGETIC
            fatigueLevel <= 55 -> FatigueStatus.MILD
            fatigueLevel <= 75 -> FatigueStatus.MODERATE
            fatigueLevel <= 90 -> FatigueStatus.SEVERE
            else -> FatigueStatus.EXHAUSTED
        }

    /**
     * 将所有维度约束在合法范围内
     */
    fun coerce(): UserBodyState = copy(
        satiety = satiety.coerceIn(0, 100),
        nutritionBalance = nutritionBalance.coerceIn(0, 100),
        gastroBurden = gastroBurden.coerceIn(0, 100),
        comfortLevel = comfortLevel.coerceIn(0, 100),
        skinStatus = skinStatus.coerceIn(0, 100),
        toxinLevel = toxinLevel.coerceIn(0.0, 100.0),
        heavyMetalAccum = heavyMetalAccum.coerceAtLeast(0.0),
        immuneLevel = immuneLevel.coerceIn(0, 100),
        energy = energy.coerceIn(0, 100),
        healthScore = healthScore.coerceIn(0, 100),
        traumaLevel = traumaLevel.coerceIn(0, 100),
        mood = mood.coerceIn(0, 100),
        dailyWorkHours = dailyWorkHours.coerceAtLeast(0.0),
        fatigueLevel = fatigueLevel.coerceIn(0, 100),
        overtimeStreakDays = overtimeStreakDays.coerceAtLeast(0),
        weeklyWorkHours = weeklyWorkHours.coerceAtLeast(0.0),
        totalOvertimeHours = totalOvertimeHours.coerceAtLeast(0.0)
    )
}

/**
 * 疲劳等级
 */
enum class FatigueStatus(val displayName: String, val description: String) {
    ENERGETIC("精力充沛", "身体状态良好，工作生活都有劲"),
    MILD("轻度疲劳", "有一点累，但不影响正常生活"),
    MODERATE("中度疲劳", "身体开始发出信号，需要适当休息"),
    SEVERE("重度疲劳", "腰酸背痛、睡眠质量下降，该停下来歇歇了"),
    EXHAUSTED("极度疲惫", "身体已经透支，继续硬撑会出问题")
}
