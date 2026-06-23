package com.example.townapp.data

/**
 * 玩家主动触发事件面板
 *
 * 原有随机突发事件保持不变。玩家可以主动规划短期变故：
 * 主动辞职、自费体检、外出短途旅行、主动和伴侣深度谈心等。
 * 选定后时序系统立刻结算对应的参数变化，触发配套抉择弹窗。
 */
object PlayerEventSystem {

    /** 事件类型 */
    enum class PlayerEventType(val label: String) {
        CAREER("职业变动"),
        HEALTH("健康管理"),
        TRAVEL("出行旅行"),
        RELATIONSHIP("情感关系"),
        LIFESTYLE("生活方式")
    }

    /** 玩家可主动触发的事件 */
    data class PlayerEvent(
        val id: String,
        val name: String,
        val type: PlayerEventType,
        val cost: Double = 0.0,          // 花费
        val fatigueDelta: Double,        // 疲惫变化
        val anxietyDelta: Double,        // 焦虑变化
        val selfEsteemDelta: Double,     // 自我认同变化
        val lonelinessDelta: Double,     // 孤独变化
        val socialFulfillmentDelta: Double, // 社交满足
        val healthDelta: Double,         // 健康变化
        val triggersChoice: Boolean,     // 是否触发抉择弹窗
        val description: String
    )

    // ============================================
    // 玩家事件库
    // ============================================

    val allPlayerEvents: List<PlayerEvent> = listOf(
        // ── 职业变动 ──
        PlayerEvent("quit_job_active", "主动辞职", PlayerEventType.CAREER,
            0.0, -0.5, 0.3, 0.2, -0.1, -0.2, 0.0,
            triggersChoice = true,
            description = "提交辞职信，重新出发。触发职业抉择弹窗。"),

        PlayerEvent("job_hopping", "跳槽面试", PlayerEventType.CAREER,
            50.0, 0.2, 0.2, 0.1, 0.0, 0.0, 0.0,
            triggersChoice = true,
            description = "投递简历，准备面试。需要花费时间和交通费。"),

        PlayerEvent("start_side_hustle", "开启副业", PlayerEventType.CAREER,
            200.0, 0.3, 0.0, 0.3, 0.0, 0.0, 0.0,
            triggersChoice = false,
            description = "投入启动资金，开辟副业收入来源。"),

        // ── 健康管理 ──
        PlayerEvent("health_checkup", "自费体检", PlayerEventType.HEALTH,
            300.0, 0.0, -0.2, 0.1, 0.0, 0.0, 0.2,
            triggersChoice = false,
            description = "全面体检，安心。早发现早治疗。"),

        PlayerEvent("therapy", "心理咨询", PlayerEventType.HEALTH,
            200.0, 0.0, -0.3, 0.2, 0.0, 0.0, 0.0,
            triggersChoice = false,
            description = "和心理咨询师聊聊，焦虑大幅下降。"),

        PlayerEvent("gym_signup", "办健身卡", PlayerEventType.HEALTH,
            500.0, 0.1, -0.2, 0.2, 0.0, 0.1, 0.3,
            triggersChoice = false,
            description = "健身房年卡，长期坚持体质稳步提升。"),

        // ── 出行旅行 ──
        PlayerEvent("short_trip", "短途旅行", PlayerEventType.TRAVEL,
            500.0, -0.3, -0.3, 0.1, -0.2, 0.2, 0.1,
            triggersChoice = false,
            description = "周末短途出行，换个环境透透气。疲惫和焦虑大幅下降。"),

        PlayerEvent("vacation", "度假旅行", PlayerEventType.TRAVEL,
            2000.0, -0.5, -0.4, 0.2, -0.1, 0.3, 0.2,
            triggersChoice = false,
            description = "一周假期，彻底放松。花费不菲但身心舒畅。"),

        // ── 情感关系 ──
        PlayerEvent("deep_talk", "伴侣深度谈心", PlayerEventType.RELATIONSHIP,
            0.0, 0.0, -0.2, 0.1, -0.3, 0.4, 0.0,
            triggersChoice = true,
            description = "坐下来好好聊聊，化解误会，增进感情。"),

        PlayerEvent("visit_parents", "回家探望父母", PlayerEventType.RELATIONSHIP,
            200.0, -0.1, -0.1, 0.1, -0.3, 0.3, 0.0,
            triggersChoice = false,
            description = "抽空回趟家，陪父母吃顿饭。"),

        // ── 生活方式 ──
        PlayerEvent("declutter", "断舍离大扫除", PlayerEventType.LIFESTYLE,
            0.0, 0.1, -0.2, 0.2, 0.0, 0.0, 0.0,
            triggersChoice = false,
            description = "清理不需要的物品，环境清爽，心情舒畅。"),

        PlayerEvent("learn_skill", "报班学技能", PlayerEventType.LIFESTYLE,
            1000.0, 0.2, 0.0, 0.3, 0.0, 0.1, 0.0,
            triggersChoice = false,
            description = "报名培训班，系统学习一项新技能。")
    )

    /**
     * 计算玩家事件的效果摘要
     */
    fun calculateEffect(event: PlayerEvent): PlayerEventEffect {
        return PlayerEventEffect(
            event = event,
            summary = buildString {
                if (event.cost > 0) append("支出 ${event.cost}元，")
                if (event.fatigueDelta < 0) append("疲惫缓解，")
                if (event.fatigueDelta > 0) append("疲惫增加，")
                if (event.anxietyDelta < 0) append("焦虑下降，")
                if (event.selfEsteemDelta > 0) append("自我认同提升，")
                if (event.lonelinessDelta < 0) append("孤独感降低，")
                if (event.socialFulfillmentDelta > 0) append("社交满足增进，")
                if (event.healthDelta > 0) append("健康改善，")
            }.trimEnd(',')
        )
    }

    data class PlayerEventEffect(
        val event: PlayerEvent,
        val summary: String
    )
}