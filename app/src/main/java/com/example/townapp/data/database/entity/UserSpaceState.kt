package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户居住空间状态表（tb_user_space_state）
 * 空间不是一个只扣房租的地址，而是一个包裹着你的物理场。
 * 它的每一个属性都在持续影响你的肉体和精神。
 */
@Entity(tableName = "tb_user_space_state")
data class UserSpaceState(
    @PrimaryKey val userId: Int,               // 居民ID

    // ============================================
    // 基础信息
    // ============================================
    val addressName: String = "临时住所",       // 住所名称
    val cityTier: Int = 2,                      // 城市等级(1-4)
    val monthlyRent: Double = 2000.0,           // 月租
    val monthlyIncome: Double = 10000.0,        // 月收入（用于计算压力）

    // ============================================
    // 空间属性矩阵（0-100，部分值有不同范围）
    // ============================================
    val areaSqm: Int = 20,                      // 面积 ㎡（不是0-100，真实面积）
    val light: Int = 60,                        // 采光（朝南=100，朝北暗房=10）
    val noise: Int = 40,                        // 噪音（越低越好，临街=80，安静小区=20）
    val cleanliness: Int = 70,                  // 清洁度（杂乱/灰尘会降低）
    val ventilation: Int = 50,                  // 通风
    val avgTemperatureCelsius: Int = 25,        // 平均室温 ℃
    val furnitureLevel: Int = 30,               // 家具齐全度（毛坯=10，精装修=90）
    val safety: Int = 80,                        // 安全性（老小区低，新小区高）
    val privacy: Int = 60,                      // 私密性（合租低，独居高）
    val neighborhoodQuality: Int = 50,          // 邻里环境

    // ============================================
    // 通勤属性
    // ============================================
    val commuteMinutesOneWay: Int = 30,         // 单程通勤时间（分钟）
    val workHoursPerDay: Int = 8,               // 每日工作时间（小时）
    val workDaysPerWeek: Int = 5,               // 每周工作天数

    // ============================================
    // 经济压力
    // ============================================
    val currentSavings: Double = 5000.0,        // 当前存款（影响掌控感）
    val hasDebt: Boolean = false,                // 是否有债务
    val debtAmount: Double = 0.0,                // 债务金额

    // ============================================
    // 记录追踪
    // ============================================
    val daysLivedHere: Int = 0,                  // 在这里居住的天数
    val updateTime: Long = System.currentTimeMillis()
) {
    /**
     * 租金收入比（0-100，越高越穷）
     * 30以下=轻松，30-50=有压力，50以上=严重压力
     */
    val rentToIncomeRatio: Int
        get() = if (monthlyIncome > 0) {
            (monthlyRent / monthlyIncome * 100).toInt().coerceIn(0, 100)
        } else 100

    /**
     * 是否是狭小空间（15㎡以下）
     */
    val isCramped: Boolean get() = areaSqm < 15

    /**
     * 是否是黑暗空间（采光低于30）
     */
    val isDark: Boolean get() = light < 30

    /**
     * 是否是高噪音空间（噪音高于70）
     */
    val isNoisy: Boolean get() = noise > 70

    /**
     * 综合空间质量评分（0-100）
     */
    val overallSpaceQuality: Int
        get() {
            var score = 0
            score += (light * 0.15).toInt()            // 采光占15%
            score += ((100 - noise) * 0.15).toInt()    // 低噪音占15%
            score += (cleanliness * 0.12).toInt()      // 清洁度占12%
            score += (ventilation * 0.1).toInt()       // 通风占10%
            score += (safety * 0.12).toInt()           // 安全占12%
            score += (privacy * 0.12).toInt()          // 隐私占12%
            score += (furnitureLevel * 0.08).toInt()   // 家具占8%
            score += (neighborhoodQuality * 0.08).toInt() // 邻里占8%
            score += (if (areaSqm >= 30) 8 else (areaSqm / 30.0 * 8).toInt()) // 面积占8%
            // 温度适当调整
            val tempScore = when {
                avgTemperatureCelsius in 20..26 -> 10
                avgTemperatureCelsius in 16..28 -> 6
                else -> 2
            }
            score += tempScore
            return score.coerceIn(0, 100)
        }

    /**
     * 通勤压力评分（0-100，越高越累）
     */
    val commuteStress: Int
        get() {
            val dailyCommuteHours = commuteMinutesOneWay * 2 / 60.0
            val weeklyWorkHours = workHoursPerDay * workDaysPerWeek + dailyCommuteHours * workDaysPerWeek
            return (weeklyWorkHours / 50.0 * 100).toInt().coerceIn(0, 100)
        }

    /**
     * 经济压力评分（0-100，越高越焦虑）
     */
    val financialStress: Int
        get() {
            var stress = rentToIncomeRatio
            if (hasDebt) stress += 20
            if (currentSavings < monthlyRent) stress += 25
            else if (currentSavings < monthlyRent * 3) stress += 10
            return stress.coerceIn(0, 100)
        }

    /**
     * 将所有维度约束在合法范围内
     */
    fun coerce(): UserSpaceState = copy(
        cityTier = cityTier.coerceIn(1, 4),
        light = light.coerceIn(0, 100),
        noise = noise.coerceIn(0, 100),
        cleanliness = cleanliness.coerceIn(0, 100),
        ventilation = ventilation.coerceIn(0, 100),
        avgTemperatureCelsius = avgTemperatureCelsius.coerceIn(-20, 50),
        furnitureLevel = furnitureLevel.coerceIn(0, 100),
        safety = safety.coerceIn(0, 100),
        privacy = privacy.coerceIn(0, 100),
        neighborhoodQuality = neighborhoodQuality.coerceIn(0, 100),
        commuteMinutesOneWay = commuteMinutesOneWay.coerceAtLeast(0),
        workHoursPerDay = workHoursPerDay.coerceAtLeast(0),
        workDaysPerWeek = workDaysPerWeek.coerceIn(0, 7),
        daysLivedHere = daysLivedHere.coerceAtLeast(0)
    )
}
