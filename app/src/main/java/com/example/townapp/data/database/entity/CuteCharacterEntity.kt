package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// ============================================
// 🐱 角色档案表 — 小镇陪伴者核心数据
// 三个性格鲜明的角色：塔菲喵、doro、咕咕嘎嘎
// ============================================
@Entity(tableName = "companion_characters")
data class CuteCharacterEntity(
    @PrimaryKey(autoGenerate = true) val characterId: Int = 0,
    
    // 基础信息
    val characterName: String,        // 角色名称：塔菲喵、doro、咕咕嘎嘎
    val characterNickname: String,    // 昵称：塔塔、doro、咕咕
    val characterEmoji: String,        // 角色头像emoji：🐱、🦊、🐣
    
    // 性格标签
    val personalityType: String,      // active(活泼) / gentle(温柔) / envious(羡慕委屈)
    val personalityDesc: String,       // 性格描述
    
    // 口头禅
    val catchphrase: String,           // 口头禅后缀（喵/咕咕/无）
    val greetingMorning: String,       // 早晨问候
    val greetingAfternoon: String,      // 下午问候
    val greetingNight: String,         // 晚间问候
    val greetingMidnight: String,      // 深夜问候（凌晨）
    val greetingReturn: String,        // 回归问候（久未登录）
    
    // 喜好与厌恶
    val favoriteThings: String,        // 喜欢的东西（逗号分隔）
    val dislikedThings: String,         // 讨厌的东西（逗号分隔）
    
    // 视觉配置
    val themeColor: Long,              // 角色专属颜色（ARGB）
    val cardBackgroundRes: String,      // 卡片背景资源名
    
    // 状态
    val isEnabled: Boolean = true,
    val isDefaultUnlock: Boolean = true,   // 默认解锁
    val unlockCondition: String = "",     // 解锁条件
    
    // 生日（角色专属日期）
    val birthdayMonth: Int = 0,         // 生日月份 1-12
    val birthdayDay: Int = 0,           // 生日日期 1-31
    
    // 心情系统
    val currentMood: Int = 50,         // 当前心情值 0-100
    val currentEnergy: Int = 100,      // 当前精力值 0-100
    val lastInteractionTime: Long = 0   // 最后互动时间戳
) {
    companion object {
        // 预设角色ID
        const val TAFFY_ID = 1          // 塔菲喵
        const val DORO_ID = 2           // doro
        const val GUGU_ID = 3          // 咕咕嘎嘎
        
        // 心情阈值
        const val MOOD_HAPPY = 70      // 开心阈值
        const val MOOD_WORRIED = 40    // 担心阈值
        const val MOOD_SAD = 20        // 难过阈值
        
        // 角色生日
        const val TAFFY_BIRTHDAY_MONTH = 4
        const val TAFFY_BIRTHDAY_DAY = 8
        const val DORO_BIRTHDAY_MONTH = 5
        const val DORO_BIRTHDAY_DAY = 15
        const val GUGU_BIRTHDAY_MONTH = 6
        const val GUGU_BIRTHDAY_DAY = 9
    }
}
