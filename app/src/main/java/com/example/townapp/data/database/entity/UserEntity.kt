package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户主控人物实体
 *
 * 小镇 v10 融合：新增人生身份绑定字段
 * - lifePathId / lifePathName：选身份后绑定，关联 LifePathSimulator 的 10 条人生路径
 * - defaultBuildingId：绑定专属房屋，选定身份时自动加载对应建筑场景
 * - defaultOutfitId：绑定默认穿搭，选定身份时预设穿衣风格
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "默认用户",
    val level: Int = 1,
    val experience: Int = 0,
    val currency: Double = 0.0,

    // ============================================
    // 人生身份绑定（v10 新增）
    // ============================================
    /** 人生路径 ID（空=未选择） */
    val lifePathId: String = "",
    /** 人生身份名称 */
    val lifePathName: String = "",
    /** 绑定专属房屋 ID */
    val defaultBuildingId: Int = 0,
    /** 绑定默认穿搭 ID */
    val defaultOutfitId: Int = 0,

    // ============================================
    // 时薪体系（v1.3 时薪-物品价值锐评）
    // ============================================
    /** 月收入 */
    val monthlyIncome: Double = 0.0,
    /** 月工作总工时 */
    val monthlyWorkHours: Double = 0.0
)