package com.example.townapp.data

/**
 * 人生阶段系统（v2.14 时间系统重构）
 *
 * 按年龄区间划分四大人生阶段，绑定事件密度、解锁权限、参数修正。
 * 少年阶段（0-18）为后台预制，开局19岁仅生成童年简报，不参与运行时。
 *
 * 核心原则：
 * - 每个阶段有不同的人生主题和事件密度，不是"越老越差"
 * - 年龄里程碑解锁新能力，不是"到了某个年龄就废了"
 * - 阶段切换是自然的，没有"你已进入XX阶段"的评判性弹窗
 */

// ============================================
// 人生阶段枚举
// ============================================
enum class LifeStage(
    val label: String,
    val ageRange: IntRange,
    val description: String,
    /** 每月事件密度：该阶段每月触发随机事件的期望数量 */
    val monthlyEventDensity: Double,
    /** 体力劳动疲惫恢复速度系数（越高越快恢复） */
    val fatigueRecoveryRate: Double,
    /** 慢性病基础发病概率修正 */
    val chronicDiseaseModifier: Double
) {
    /** 少年阶段（0-18岁）：后台预制，开局仅生成童年简报 */
    CHILDHOOD(
        label = "少年",
        ageRange = 0..18,
        description = "后台预制阶段，开局生成童年经历简报",
        monthlyEventDensity = 0.0,
        fatigueRecoveryRate = 1.0,
        chronicDiseaseModifier = 0.0
    ),
    /** 青年阶段（19-29岁）：择业求学、婚恋组建，事件密度最高 */
    YOUTH(
        label = "青年",
        ageRange = 19..29,
        description = "择业、求学、恋爱、试错——人生最密集的探索期",
        monthlyEventDensity = 2.0,
        fatigueRecoveryRate = 1.1,
        chronicDiseaseModifier = 0.3
    ),
    /** 中年阶段（30-59岁）：房贷育儿、事业瓶颈，观念反思增多 */
    MIDDLE_AGE(
        label = "中年",
        ageRange = 30..59,
        description = "房贷、育儿、职场瓶颈——也是自我和解的开始",
        monthlyEventDensity = 1.5,
        fatigueRecoveryRate = 0.9,
        chronicDiseaseModifier = 0.6
    ),
    /** 老年阶段（60岁+）：退休、慢性病，极限运动解锁 */
    SENIOR(
        label = "老年",
        ageRange = 60..120,
        description = "退休生活——慢性病多了，但时间的自由也多了",
        monthlyEventDensity = 1.0,
        fatigueRecoveryRate = 0.7,
        chronicDiseaseModifier = 1.0
    );

    companion object {
        fun fromAge(age: Int): LifeStage = entries.firstOrNull { age in it.ageRange } ?: SENIOR
    }
}

// ============================================
// 年龄里程碑
// ============================================
data class AgeMilestone(
    val age: Int,
    val label: String,
    val description: String,
    /** 解锁的能力/权限 */
    val unlocks: List<String> = emptyList(),
    /** 触发的人生事件类型 */
    val eventType: String = ""
)

object AgeMilestones {
    val milestones: List<AgeMilestone> = listOf(
        AgeMilestone(19, "成年开局", "离开原生家庭，开始独立人生", unlocks = listOf("职业选择", "婚恋系统")),
        AgeMilestone(20, "穿搭自定义", "解锁手动穿搭模式，可以自由搭配套装", unlocks = listOf("手动穿搭")),
        AgeMilestone(22, "大学毕业", "完成高等教育，正式进入职场", eventType = "毕业"),
        AgeMilestone(25, "青年觉醒", "开始反思'等长大就好了'这句话", unlocks = listOf("成语反思条目")),
        AgeMilestone(30, "而立之年", "进入中年阶段，房贷育儿压力上升", eventType = "中年过渡"),
        AgeMilestone(35, "中年反思", "事业瓶颈期，原生观念反思深度事件增多", unlocks = listOf("深度反思事件")),
        AgeMilestone(40, "不惑之年", "中年自我和解支线开始", unlocks = listOf("自我和解支线")),
        AgeMilestone(45, "中年闭环", "面对下一代——延续还是打破代际循环", eventType = "代际闭环"),
        AgeMilestone(50, "知天命", "事业进入稳定期，跳槽概率下降", eventType = "事业稳定"),
        AgeMilestone(55, "退休前夕", "准备退休规划，养老金储蓄检查", eventType = "退休规划"),
        AgeMilestone(60, "退休", "停发月薪，依靠养老金与存款生活", unlocks = listOf("极限运动项目"), eventType = "退休"),
        AgeMilestone(65, "银发自由", "慢性病概率提高，但时间完全属于自己", eventType = "老年生活"),
        AgeMilestone(70, "古稀之年", "人生回顾事件增多，解锁老年智慧对话", unlocks = listOf("人生回顾对话")),
        AgeMilestone(80, "耄耋之年", "长寿里程碑，触发人生总结事件", eventType = "人生总结")
    )

    fun getMilestonesForAge(age: Int): List<AgeMilestone> =
        milestones.filter { it.age == age }

    fun getUpcomingMilestones(currentAge: Int, lookAhead: Int = 5): List<AgeMilestone> =
        milestones.filter { it.age in (currentAge + 1)..(currentAge + lookAhead) }
}

// ============================================
// 人生阶段规则
// ============================================
object LifeStageRules {

    /**
     * 获取当前阶段对应的事件类型权重
     * 青年：求职恋爱类占比高
     * 中年：失业、房贷育儿、事业瓶颈
     * 老年：慢性病、养老相关
     */
    fun getEventCategoryWeights(stage: LifeStage): Map<String, Double> = when (stage) {
        LifeStage.YOUTH -> mapOf(
            "career" to 0.30,       // 求职择业
            "love" to 0.25,         // 婚恋
            "social" to 0.15,       // 社交
            "health" to 0.10,       // 健康
            "finance" to 0.10,      // 财务
            "family" to 0.10        // 家庭
        )
        LifeStage.MIDDLE_AGE -> mapOf(
            "career" to 0.20,       // 事业瓶颈
            "family" to 0.25,       // 房贷育儿
            "finance" to 0.20,      // 财务压力
            "health" to 0.15,       // 健康下滑
            "social" to 0.10,       // 社交
            "love" to 0.10          // 婚恋
        )
        LifeStage.SENIOR -> mapOf(
            "health" to 0.35,       // 慢性病
            "family" to 0.25,       // 家庭/子女
            "social" to 0.15,       // 社交/老年活动
            "finance" to 0.15,      // 养老金
            "career" to 0.05,       // 退休返聘
            "love" to 0.05          // 晚年伴侣
        )
        else -> mapOf(
            "career" to 0.25, "love" to 0.20, "social" to 0.15,
            "health" to 0.15, "finance" to 0.15, "family" to 0.10
        )
    }

    /**
     * 体力劳动疲惫恢复速度
     */
    fun getFatigueRecoveryRate(age: Int): Double {
        return LifeStage.fromAge(age).fatigueRecoveryRate
    }

    /**
     * 慢性病基础发病概率修正
     */
    fun getChronicDiseaseModifier(age: Int): Double {
        return LifeStage.fromAge(age).chronicDiseaseModifier
    }

    /**
     * 该年龄是否已退休（60岁+）
     */
    fun isRetired(age: Int): Boolean = age >= 60
}