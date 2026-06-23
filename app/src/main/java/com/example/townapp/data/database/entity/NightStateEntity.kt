package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 夜间状态表（tb_night_state）
 *
 * 专门存储深夜22:00-6:00的失眠、梦境、独白数据，是"以人为本、治愈共情"最核心的叙事载体。
 *
 * 设计原则：
 * 1. 接纳所有负面情绪，不要求"必须积极"
 * 2. 完整保存脆弱的人生片段，传递"痛苦、内耗、疲惫都是正常的"
 * 3. 所有文案无评判、纯共情
 */
@Entity(tableName = "tb_night_state")
data class NightStateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    // ============================================
    // 身份标识
    // ============================================
    val npcId: String = "",              // NPC逻辑ID
    val npcName: String = "",            // NPC名称

    // ============================================
    // 时间信息
    // ============================================
    val gameDay: Int = 0,                // 游戏天数
    val gameHour: Int = 0,               // 记录时的游戏小时（22-23或0-5）
    val createdAt: Long = System.currentTimeMillis(),

    // ============================================
    // 睡眠状态判定
    // ============================================
    val sleepStatus: String = "ASLEEP",  // 睡眠状态：ASLEEP/RESTLESS/INSOMNIA/WAKEFUL
    val sleepDurationMinutes: Int = 0,   // 实际睡眠时长（分钟）
    val sleepQuality: Double = 70.0,     // 睡眠质量 0-100

    // ============================================
    // 梦境系统
    // ============================================
    val hasDream: Boolean = false,       // 是否做梦
    val dreamType: String = "",          // 梦境类型：PEACEFUL/NOSTALGIC/CONFUSING/NIGHTMARE/EMPTY
    val dreamContent: String = "",       // 梦境内容文案
    val dreamEmoji: String = "",         // 梦境表情

    // ============================================
    // 深夜独白（共情式文案）
    // ============================================
    val nightMonoText: String = "",      // 深夜内心独白
    val monoEmotion: String = "CALM",    // 独白情绪：CALM/SAD/ANXIOUS/LONELY/HOPeful/REGRET

    // ============================================
    // 触发条件记录
    // ============================================
    val fatigueAtBedtime: Double = 0.0,  // 睡前疲惫值
    val anxietyAtBedtime: Double = 0.0,  // 睡前焦虑值
    val traumaAtBedtime: Double = 0.0,   // 睡前创伤值
    val moneyAtBedtime: Double = 0.0,    // 睡前存款
    val workMinutesToday: Int = 0,       // 今日工作分钟

    // ============================================
    // 修复效果
    // ============================================
    val energyRecovered: Double = 0.0,   // 睡眠恢复精力
    val traumaReduced: Double = 0.0,     // 睡眠降低创伤
    val anxietyReduced: Double = 0.0     // 睡眠降低焦虑
) {
    /**
     * 睡眠状态枚举
     */
    companion object {
        const val SLEEP_STATUS_ASLEEP = "ASLEEP"           // 正常睡眠
        const val SLEEP_STATUS_RESTLESS = "RESTLESS"       // 浅眠不安
        const val SLEEP_STATUS_INSOMNIA = "INSOMNIA"       // 整夜失眠
        const val SLEEP_STATUS_WAKEFUL = "WAKEFUL"         // 清醒未睡
        const val SLEEP_STATUS_DEEP_SLEEP = "DEEP_SLEEP"   // 深度睡眠

        const val DREAM_TYPE_PEACEFUL = "PEACEFUL"         // 平和美好的梦
        const val DREAM_TYPE_NOSTALGIC = "NOSTALGIC"       // 怀旧温暖的梦
        const val DREAM_TYPE_CONFUSING = "CONFUSING"       // 混乱迷茫的梦
        const val DREAM_TYPE_NIGHTMARE = "NIGHTMARE"       // 噩梦
        const val DREAM_TYPE_EMPTY = "EMPTY"               // 无梦
        const val DREAM_TYPE_NEGATIVE = "NEGATIVE"         // 负面梦境

        const val MONO_EMOTION_CALM = "CALM"               // 平静
        const val MONO_EMOTION_SAD = "SAD"                 // 忧伤
        const val MONO_EMOTION_ANXIOUS = "ANXIOUS"         // 焦虑
        const val MONO_EMOTION_LONELY = "LONELY"           // 孤独
        const val MONO_EMOTION_HOPEFUL = "HOPEFUL"         // 期待
        const val MONO_EMOTION_REGRET = "REGRET"           // 遗憾
    }
}
