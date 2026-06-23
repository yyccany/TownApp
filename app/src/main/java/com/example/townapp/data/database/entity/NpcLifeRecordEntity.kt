package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * NPC人生轨迹归档表（tb_npc_life_record）
 *
 * 永久留存每个NPC每日的时间分配、收入、情绪变化，完整呈现"同样24小时，不同选择走出两种人生"的长期差距。
 *
 * 设计原则：
 * 1. 客观记录，不做好坏评判
 * 2. 完整保留每日数据，支持回溯分析
 * 3. 所有字段与PlayerState对齐，保证数据一致性
 */
@Entity(tableName = "tb_npc_life_record")
data class NpcLifeRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    // ============================================
    // 身份标识
    // ============================================
    val npcId: String = "",              // NPC逻辑ID
    val npcName: String = "",            // NPC名称
    val careerId: String = "",           // 当前职业ID
    val careerName: String = "",         // 当前职业名称

    // ============================================
    // 时间信息
    // ============================================
    val gameDay: Int = 0,                // 游戏天数
    val gameHour: Int = 0,               // 记录时的游戏小时
    val createdAt: Long = System.currentTimeMillis(), // 创建时间戳

    // ============================================
    // 当日时间分配（分钟）
    // ============================================
    val workMinutes: Int = 0,            // 工作分钟
    val restMinutes: Int = 0,            // 休息分钟
    val leisureMinutes: Int = 0,         // 休闲分钟
    val socialMinutes: Int = 0,          // 社交分钟
    val studyMinutes: Int = 0,           // 学习分钟
    val sleepMinutes: Int = 0,           // 睡眠分钟
    val remainingMinutes: Int = 1440,    // 剩余分钟

    // ============================================
    // 双轨收入记录
    // ============================================
    val dayLaborIncome: Double = 0.0,    // 劳动收入（工作分钟×时薪）
    val dayCompoundIncome: Double = 0.0, // 复利收入（资产×复利系数）
    val dayTotalIncome: Double = 0.0,    // 总收入

    // ============================================
    // 资产与财富
    // ============================================
    val money: Double = 0.0,             // 当前现金
    val assets: Double = 0.0,            // 当前资产总额

    // ============================================
    // 身体状态
    // ============================================
    val energy: Double = 80.0,           // 精力 0-100
    val health: Double = 70.0,           // 健康 0-100
    val fatigue: Double = 0.0,           // 疲惫 0-100
    val hunger: Double = 80.0,           // 饱腹 0-100

    // ============================================
    // 精神状态
    // ============================================
    val happiness: Double = 60.0,        // 快乐 0-100
    val anxiety: Double = 30.0,          // 焦虑 0-100
    val loneliness: Double = 20.0,       // 孤独 0-100
    val trauma: Double = 0.0,            // 创伤 0-100

    // ============================================
    // 夜间状态（承接夜间叙事系统）
    // ============================================
    val sleepQuality: Double = 70.0,     // 睡眠质量 0-100
    val nightStatus: String = "NORMAL",  // 夜间状态：DEEP_SLEEP/SLEEP/INSOMNIA
    val dreamType: String = "",          // 梦境类型：PEACEFUL/NEUTRAL/NEGATIVE/NIGHTMARE
    val dreamContent: String = "",       // 梦境内容文案
    val nightMonoText: String = ""       // 深夜独白文案
) {
    /**
     * 夜间状态枚举
     */
    companion object {
        const val NIGHT_STATUS_DEEP_SLEEP = "DEEP_SLEEP"
        const val NIGHT_STATUS_SLEEP = "SLEEP"
        const val NIGHT_STATUS_LIGHT_SLEEP = "LIGHT_SLEEP"
        const val NIGHT_STATUS_INSOMNIA = "INSOMNIA"

        const val DREAM_TYPE_PEACEFUL = "PEACEFUL"
        const val DREAM_TYPE_NEUTRAL = "NEUTRAL"
        const val DREAM_TYPE_NEGATIVE = "NEGATIVE"
        const val DREAM_TYPE_NIGHTMARE = "NIGHTMARE"
    }
}
