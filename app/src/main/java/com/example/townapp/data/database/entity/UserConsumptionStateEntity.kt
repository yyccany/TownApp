package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户消费状态表（tb_user_consumption_state）
 *
 * 记录玩家当前的消费倾向评分，四大维度：衣食住行。
 * 分值越高 = 越偏向「以人为本」（钱花在刀刃上，养护自身）
 * 分值越低 = 越偏向「舍本逐末」（砸钱在外在排场上，透支健康）
 *
 * 设计原则：
 * 1. Entity 只存数字 / ID / 状态
 * 2. 文案、描述全部走 TextAssetLoader 或 MindTextLib
 * 3. 无对错评判，只用客观数值展示不同取舍的长期结果
 */
@Entity(tableName = "tb_user_consumption_state")
data class UserConsumptionStateEntity(
    @PrimaryKey val userId: Int = 1,

    // ============================================
    // 四大消费维度评分（0~100）
    // ============================================

    /** 吃：日常优质食材 vs 宴席网红溢价 */
    val foodScore: Int = 50,

    /** 穿：贴身舒适 vs 大牌logo */
    val clothingScore: Int = 50,

    /** 住：环保健康家电 vs 网红软装颜值 */
    val housingScore: Int = 50,

    /** 行：安全护具 vs 豪车排面贷款 */
    val transportScore: Int = 50,

    // ============================================
    // 累积统计数据
    // ============================================

    /** 累计人本消费次数（健康/舒适/安全类消费） */
    val peopleOrientedCount: Int = 0,

    /** 累计虚荣消费次数（排场/溢价/智商税类消费） */
    val vanityDrivenCount: Int = 0,

    /** 累计消费金额 */
    val totalConsumptionAmount: Double = 0.0,

    /** 累计节省的医疗/维修/还贷时间（小时） */
    val savedTimeHours: Double = 0.0,

    // ============================================
    // 记录追踪
    // ============================================

    /** 上次消费时间戳 */
    val lastConsumptionTime: Long = 0L,

    /** 上次消费类型（food/clothing/housing/transport） */
    val lastConsumptionType: String = "",

    /** 最后更新时间 */
    val updateTime: Long = System.currentTimeMillis()
) {
    /** 综合人本取向评分（0~100） */
    val overallScore: Int
        get() = ((foodScore + clothingScore + housingScore + transportScore) / 4).coerceIn(0, 100)

    /** 是否整体偏向人本路线（≥60） */
    val isPeopleOriented: Boolean
        get() = overallScore >= 60

    /** 是否整体偏向虚荣透支路线（≤40） */
    val isVanityDriven: Boolean
        get() = overallScore <= 40
}
