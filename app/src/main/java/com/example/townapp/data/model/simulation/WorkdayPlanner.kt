package com.example.townapp.data

/**
 * 工作日时段自由规划系统
 *
 * 周一至周五默认通勤上班，核心工作时段强制在岗，不可改动。
 * 上下班通勤前后、午休、下班后晚间时段开放玩家自定义选择。
 *
 * 晚间选择直接更新疲惫、技能点数、孤独值、消费支出参数。
 * 挂机状态下，晚间行为按照角色性格自动分配。
 */
object WorkdayPlanner {

    /** 时段类型 */
    enum class TimeSlot(val label: String) {
        COMMUTE("通勤时段"),
        LUNCH_BREAK("午休时段"),
        EVENING("晚间时段"),
        PRE_WORK("上班前")
    }

    /** 晚间活动类型 */
    enum class ActivityCategory(val label: String) {
        GROWTH("成长类"),
        LEISURE("休闲类"),
        SOCIAL("社交类"),
        CHORES("生活琐事"),
        EXTREME("极端抉择")
    }

    /** 单项活动 */
    data class Activity(
        val id: String,
        val name: String,
        val category: ActivityCategory,
        val slot: TimeSlot,
        val duration: Double,          // 耗时（小时）
        val cost: Double = 0.0,        // 花费（元）
        val fatigueDelta: Double,      // 疲惫变化
        val skillDelta: Double,        // 技能变化
        val lonelinessDelta: Double,   // 孤独变化
        val anxietyDelta: Double,      // 焦虑变化
        val selfEsteemDelta: Double,   // 自我认同变化
        val socialFulfillmentDelta: Double, // 社交满足
        val description: String
    )

    // ============================================
    // 活动库
    // ============================================

    val allActivities: List<Activity> = listOf(
        // ── 成长类 ──
        Activity("learn_coding", "线上编程学习", ActivityCategory.GROWTH, TimeSlot.EVENING,
            2.0, 0.0, 0.3, 0.5, 0.0, -0.1, 0.2, 0.0,
            "投入两小时学习编程，技能点数稳步上涨"),
        Activity("exam_prep", "备考证书", ActivityCategory.GROWTH, TimeSlot.EVENING,
            2.5, 30.0, 0.4, 0.6, 0.0, -0.2, 0.1, 0.0,
            "系统备考，报名费不低但长期回报可观"),
        Activity("online_course", "在线课程", ActivityCategory.GROWTH, TimeSlot.EVENING,
            1.5, 20.0, 0.2, 0.4, 0.0, -0.1, 0.2, 0.0,
            "碎片时间充电，稳步积累"),
        Activity("reading", "阅读书籍", ActivityCategory.GROWTH, TimeSlot.EVENING,
            1.0, 0.0, -0.1, 0.3, 0.0, -0.2, 0.1, 0.0,
            "安静阅读，沉淀思考"),
        Activity("side_hustle", "副业接单", ActivityCategory.GROWTH, TimeSlot.EVENING,
            2.0, 0.0, 0.5, 0.4, 0.0, 0.0, 0.3, 0.0,
            "熬夜赶单，技能和收入都有提升，但很累"),

        // ── 休闲类 ──
        Activity("watch_show", "追剧游戏", ActivityCategory.LEISURE, TimeSlot.EVENING,
            2.0, 0.0, -0.2, 0.0, -0.1, -0.2, 0.0, 0.0,
            "放松身心，但容易沉迷熬夜"),
        Activity("short_video", "刷短视频", ActivityCategory.LEISURE, TimeSlot.EVENING,
            1.5, 0.0, 0.1, 0.0, -0.1, 0.0, 0.0, 0.0,
            "碎片化娱乐，时间过得飞快"),
        Activity("walk", "散步", ActivityCategory.LEISURE, TimeSlot.EVENING,
            0.5, 0.0, -0.3, 0.0, -0.1, -0.2, 0.0, 0.0,
            "出门走走，放松身心，零成本"),
        Activity("exercise", "健身运动", ActivityCategory.LEISURE, TimeSlot.EVENING,
            1.0, 20.0, 0.2, 0.0, 0.0, -0.3, 0.2, 0.0,
            "强身健体，短期疲惫但长期有益"),
        Activity("hobby", "兴趣爱好", ActivityCategory.LEISURE, TimeSlot.EVENING,
            1.5, 15.0, -0.1, 0.2, 0.0, -0.2, 0.2, 0.0,
            "做自己喜欢的事，创造力提升"),

        // ── 社交类 ──
        Activity("family_chat", "和家人沟通", ActivityCategory.SOCIAL, TimeSlot.EVENING,
            0.5, 0.0, -0.1, 0.0, -0.2, -0.1, 0.0, 0.3,
            "一通电话，几句家常，孤独感下降"),
        Activity("friend_dinner", "邀约朋友聚餐", ActivityCategory.SOCIAL, TimeSlot.EVENING,
            2.0, 60.0, 0.1, 0.0, -0.3, -0.2, 0.1, 0.4,
            "热闹聚餐，社交满足大幅提升，但花钱不少"),
        Activity("date", "约会", ActivityCategory.SOCIAL, TimeSlot.EVENING,
            2.5, 80.0, 0.2, 0.0, -0.3, -0.1, 0.2, 0.5,
            "浪漫约会，情感满足感拉满"),
        Activity("community", "社区活动", ActivityCategory.SOCIAL, TimeSlot.EVENING,
            1.5, 0.0, 0.0, 0.1, -0.2, 0.0, 0.0, 0.2,
            "参与社区志愿服务，认识邻里"),

        // ── 生活琐事 ──
        Activity("housework", "整理家务", ActivityCategory.CHORES, TimeSlot.EVENING,
            1.0, 0.0, 0.2, 0.0, 0.0, 0.0, 0.1, 0.0,
            "打扫卫生，整理房间，环境整洁心情好"),
        Activity("laundry", "清洗衣物", ActivityCategory.CHORES, TimeSlot.EVENING,
            0.5, 5.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0,
            "洗衣晾晒，维持衣物整洁"),
        Activity("grocery", "采购日用品", ActivityCategory.CHORES, TimeSlot.EVENING,
            1.0, 50.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0,
            "超市采购，补充一周所需"),

        // ── 极端抉择 ──
        Activity("quit_job", "申请离职", ActivityCategory.EXTREME, TimeSlot.EVENING,
            0.5, 0.0, -0.5, 0.0, 0.3, 0.2, 0.3, -0.2,
            "提交辞职信，短期焦虑但获得自由，触发职业随机事件"),
        Activity("absent", "临时请假旷工", ActivityCategory.EXTREME, TimeSlot.EVENING,
            0.5, 0.0, -0.3, 0.0, -0.1, 0.1, 0.1, 0.0,
            "旷工一天，有扣工资和失业风险")
    )

    // ============================================
    // 单日工作日晚间计划
    // ============================================

    data class EveningPlan(
        val activities: List<Activity> = emptyList(),
        val totalCost: Double = 0.0,
        val totalFatigue: Double = 0.0,
        val totalSkill: Double = 0.0,
        val totalLoneliness: Double = 0.0,
        val totalAnxiety: Double = 0.0,
        val totalSelfEsteem: Double = 0.0,
        val totalSocialFulfillment: Double = 0.0
    )

    /**
     * 计算晚间活动的总效果
     */
    fun calculateEveningEffect(activities: List<Activity>): EveningPlan {
        var cost = 0.0
        var fatigue = 0.0
        var skill = 0.0
        var loneliness = 0.0
        var anxiety = 0.0
        var selfEsteem = 0.0
        var social = 0.0

        activities.forEach { a ->
            cost += a.cost
            fatigue += a.fatigueDelta
            skill += a.skillDelta
            loneliness += a.lonelinessDelta
            anxiety += a.anxietyDelta
            selfEsteem += a.selfEsteemDelta
            social += a.socialFulfillmentDelta
        }

        return EveningPlan(
            activities = activities,
            totalCost = cost,
            totalFatigue = fatigue,
            totalSkill = skill,
            totalLoneliness = loneliness,
            totalAnxiety = anxiety,
            totalSelfEsteem = selfEsteem,
            totalSocialFulfillment = social
        )
    }

    /**
     * 自动分配晚间活动（根据性格）
     */
    fun autoGenerateEvening(isThrifty: Boolean, isBalanced: Boolean): EveningPlan {
        val activities = if (isThrifty) {
            // 节俭型：优先学习提升
            listOf(allActivities.first { it.id == "learn_coding" })
        } else if (isBalanced) {
            // 均衡型：交替学习与休闲
            listOf(allActivities.first { it.id == "reading" },
                allActivities.first { it.id == "walk" })
        } else {
            // 随性型：偏重娱乐
            listOf(allActivities.first { it.id == "watch_show" },
                allActivities.first { it.id == "short_video" })
        }
        return calculateEveningEffect(activities)
    }
}