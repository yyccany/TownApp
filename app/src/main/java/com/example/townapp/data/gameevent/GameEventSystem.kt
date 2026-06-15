package com.example.townapp.data.gameevent

import kotlin.random.Random

enum class EventType {
    WORKPLACE,
    ECONOMIC_DISPUTE,
    SOCIAL_CONFLICT,
    POLICY_CHANGE
}

enum class EventSeverity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

data class GameEvent(
    val id: String,
    val type: EventType,
    val title: String,
    val description: String,
    val severity: EventSeverity,
    val impactMood: Int,
    val impactEnergy: Int,
    val impactMoney: Int,
    val choices: List<EventChoice>
)

data class EventChoice(
    val id: String,
    val text: String,
    val consequence: String,
    val moodChange: Int,
    val energyChange: Int,
    val moneyChange: Int,
    val reputationChange: Int
)

object GameEventGenerator {
    private val workplaceEvents = listOf(
        GameEvent(
            id = "work_gossip",
            type = EventType.WORKPLACE,
            title = "同事的闲话",
            description = "你听到同事在背后议论你，说你最近工作不上心。这些话传到了领导耳朵里。",
            severity = EventSeverity.LOW,
            impactMood = -5,
            impactEnergy = -3,
            impactMoney = 0,
            choices = listOf(
                EventChoice(
                    id = "ignore",
                    text = "不予理会",
                    consequence = "你选择无视这些闲话，专注于自己的工作。时间会证明一切。",
                    moodChange = -3,
                    energyChange = 0,
                    moneyChange = 0,
                    reputationChange = 2
                ),
                EventChoice(
                    id = "confront",
                    text = "找对方对质",
                    consequence = "你找到议论你的同事对质。对方表面道歉，但你们的关系从此变得尴尬。",
                    moodChange = -5,
                    energyChange = -5,
                    moneyChange = 0,
                    reputationChange = -3
                ),
                EventChoice(
                    id = "improve",
                    text = "用业绩说话",
                    consequence = "你加倍努力工作，用出色的成绩堵住了所有人的嘴。",
                    moodChange = 5,
                    energyChange = -8,
                    moneyChange = 0,
                    reputationChange = 5
                )
            )
        ),
        GameEvent(
            id = "work_overtime",
            type = EventType.WORKPLACE,
            title = "强制无偿加班",
            description = "上级要求你周末加班完成一个紧急项目，但没有任何加班费。",
            severity = EventSeverity.MEDIUM,
            impactMood = -10,
            impactEnergy = -15,
            impactMoney = 0,
            choices = listOf(
                EventChoice(
                    id = "accept",
                    text = "默默接受",
                    consequence = "你接受了加班。虽然疲惫，但保住了工作稳定。",
                    moodChange = -8,
                    energyChange = -15,
                    moneyChange = 0,
                    reputationChange = 3
                ),
                EventChoice(
                    id = "negotiate",
                    text = "协商调休",
                    consequence = "你和上级协商，用加班时间换取了调休。双方都比较满意。",
                    moodChange = -2,
                    energyChange = -10,
                    moneyChange = 0,
                    reputationChange = 0
                ),
                EventChoice(
                    id = "refuse",
                    text = "拒绝加班",
                    consequence = "你拒绝了加班要求。上级脸色很难看，你感觉自己被记在了小本子上。",
                    moodChange = -5,
                    energyChange = 5,
                    moneyChange = 0,
                    reputationChange = -5
                )
            )
        ),
        GameEvent(
            id = "work_unfair",
            type = EventType.WORKPLACE,
            title = "绩效不公",
            description = "你发现这个月的绩效奖金被克扣了一部分，理由是你\"态度不够积极\"。",
            severity = EventSeverity.HIGH,
            impactMood = -15,
            impactEnergy = -10,
            impactMoney = -500,
            choices = listOf(
                EventChoice(
                    id = "accept_unfair",
                    text = "忍气吞声",
                    consequence = "你选择忍耐。这笔钱不算多，但心里很不舒服。",
                    moodChange = -12,
                    energyChange = -5,
                    moneyChange = -500,
                    reputationChange = 0
                ),
                EventChoice(
                    id = "collect_evidence",
                    text = "收集证据申诉",
                    consequence = "你收集了工作记录和证据，向HR申诉。经过调查，你拿回了属于自己的奖金，但和直属领导的关系变得微妙。",
                    moodChange = 8,
                    energyChange = -10,
                    moneyChange = 0,
                    reputationChange = -3
                ),
                EventChoice(
                    id = "quit",
                    text = "辞职不干",
                    consequence = "你提交了辞职信。此处不留爷，自有留爷处。但下一份工作还没着落。",
                    moodChange = 5,
                    energyChange = 5,
                    moneyChange = 0,
                    reputationChange = 0
                )
            )
        ),
        GameEvent(
            id = "work_blame",
            type = EventType.WORKPLACE,
            title = "替人背锅",
            description = "同事的失误导致项目出了问题，他却把责任推到了你身上。",
            severity = EventSeverity.HIGH,
            impactMood = -15,
            impactEnergy = -10,
            impactMoney = -300,
            choices = listOf(
                EventChoice(
                    id = "take_blame",
                    text = "默默背锅",
                    consequence = "你选择承担责任。虽然委屈，但维护了团队和谐。",
                    moodChange = -15,
                    energyChange = -8,
                    moneyChange = -300,
                    reputationChange = 2
                ),
                EventChoice(
                    id = "defend",
                    text = "澄清事实",
                    consequence = "你拿出证据澄清了事实。真相大白，但同事对你产生了敌意。",
                    moodChange = 5,
                    energyChange = -5,
                    moneyChange = 0,
                    reputationChange = -5
                ),
                EventChoice(
                    id = "compromise",
                    text = "共同承担",
                    consequence = "你提议和同事一起承担责任。虽然都受了批评，但避免了更大的冲突。",
                    moodChange = -5,
                    energyChange = -5,
                    moneyChange = -150,
                    reputationChange = 0
                )
            )
        )
    )

    private val economicEvents = listOf(
        GameEvent(
            id = "loan_default",
            type = EventType.ECONOMIC_DISPUTE,
            title = "朋友借钱不还",
            description = "半年前借给朋友的钱，到了约定日期他却迟迟不还。",
            severity = EventSeverity.MEDIUM,
            impactMood = -10,
            impactEnergy = -5,
            impactMoney = 0,
            choices = listOf(
                EventChoice(
                    id = "forgive",
                    text = "算了不要了",
                    consequence = "你决定不再追究。钱不多，但看清了一个人。",
                    moodChange = -5,
                    energyChange = 2,
                    moneyChange = -1000,
                    reputationChange = 3
                ),
                EventChoice(
                    id = "remind",
                    text = "委婉提醒",
                    consequence = "你委婉地提醒了朋友。他表示最近手头紧，会尽快还。",
                    moodChange = -3,
                    energyChange = -3,
                    moneyChange = 0,
                    reputationChange = -1
                ),
                EventChoice(
                    id = "demand",
                    text = "正式催讨",
                    consequence = "你正式要求朋友还款。他不太高兴，但还是还了钱。你们的关系变得疏远。",
                    moodChange = 2,
                    energyChange = -5,
                    moneyChange = 1000,
                    reputationChange = -5
                )
            )
        ),
        GameEvent(
            id = "bill_dispute",
            type = EventType.ECONOMIC_DISPUTE,
            title = "合租账单纠纷",
            description = "合租室友提交的水电账单看起来有问题，似乎在虚报费用。",
            severity = EventSeverity.LOW,
            impactMood = -8,
            impactEnergy = -3,
            impactMoney = 0,
            choices = listOf(
                EventChoice(
                    id = "accept_bill",
                    text = "照单全付",
                    consequence = "你没有多说什么，按账单付了钱。心里有些不舒服。",
                    moodChange = -5,
                    energyChange = 0,
                    moneyChange = -150,
                    reputationChange = 1
                ),
                EventChoice(
                    id = "check_bill",
                    text = "核对账单",
                    consequence = "你要求查看缴费凭证。室友有些尴尬，承认多算了一点。",
                    moodChange = 2,
                    energyChange = -5,
                    moneyChange = -80,
                    reputationChange = -2
                ),
                EventChoice(
                    id = "confront_bill",
                    text = "当面质问",
                    consequence = "你直接质问室友。他恼羞成怒，你们大吵了一架。",
                    moodChange = -10,
                    energyChange = -8,
                    moneyChange = -50,
                    reputationChange = -5
                )
            )
        ),
        GameEvent(
            id = "moral_coercion",
            type = EventType.ECONOMIC_DISPUTE,
            title = "人情绑架",
            description = "亲戚找你借钱，还说\"大家都是一家人，这点忙都不帮？\"",
            severity = EventSeverity.MEDIUM,
            impactMood = -12,
            impactEnergy = -8,
            impactMoney = 0,
            choices = listOf(
                EventChoice(
                    id = "give_in",
                    text = "碍于情面借钱",
                    consequence = "你借给了亲戚钱。虽然不情愿，但维护了表面的和睦。",
                    moodChange = -8,
                    energyChange = -3,
                    moneyChange = -2000,
                    reputationChange = 2
                ),
                EventChoice(
                    id = "refuse_politely",
                    text = "委婉拒绝",
                    consequence = "你委婉地表示自己也不宽裕。亲戚不太高兴，但也没多说什么。",
                    moodChange = -5,
                    energyChange = -3,
                    moneyChange = 0,
                    reputationChange = -2
                ),
                EventChoice(
                    id = "refuse_directly",
                    text = "直接拒绝",
                    consequence = "你直接拒绝了。亲戚脸色很难看，说你\"不讲亲情\"。",
                    moodChange = -3,
                    energyChange = 2,
                    moneyChange = 0,
                    reputationChange = -8
                )
            )
        ),
        GameEvent(
            id = "usury_offer",
            type = EventType.ECONOMIC_DISPUTE,
            title = "高利贷诱惑",
            description = "有人向你推荐一款\"高收益\"理财产品，听起来很诱人，但利率高得不正常。",
            severity = EventSeverity.HIGH,
            impactMood = 5,
            impactEnergy = 0,
            impactMoney = 0,
            choices = listOf(
                EventChoice(
                    id = "invest_all",
                    text = "全部投入",
                    consequence = "你把大部分积蓄投了进去。短期内确实赚了一些钱，但风险正在积聚。",
                    moodChange = 10,
                    energyChange = 0,
                    moneyChange = 500,
                    reputationChange = 0
                ),
                EventChoice(
                    id = "invest_part",
                    text = "小额尝试",
                    consequence = "你只投了一小部分钱。既体验了收益，也控制了风险。",
                    moodChange = 5,
                    energyChange = 0,
                    moneyChange = 100,
                    reputationChange = 0
                ),
                EventChoice(
                    id = "reject",
                    text = "果断拒绝",
                    consequence = "你拒绝了这个诱惑。虽然错过了可能的高收益，但保住了本金安全。",
                    moodChange = 0,
                    energyChange = 3,
                    moneyChange = 0,
                    reputationChange = 2
                )
            )
        )
    )

    private val socialEvents = listOf(
        GameEvent(
            id = "social_rumor",
            type = EventType.SOCIAL_CONFLICT,
            title = "谣言缠身",
            description = "不知从哪里传出关于你的谣言，让你在社交圈里很尴尬。",
            severity = EventSeverity.MEDIUM,
            impactMood = -12,
            impactEnergy = -8,
            impactMoney = 0,
            choices = listOf(
                EventChoice(
                    id = "ignore_rumor",
                    text = "不予理会",
                    consequence = "你选择无视谣言。时间久了，谣言自然平息了。",
                    moodChange = -8,
                    energyChange = 2,
                    moneyChange = 0,
                    reputationChange = 3
                ),
                EventChoice(
                    id = "explain",
                    text = "公开澄清",
                    consequence = "你在朋友圈发了声明澄清。大部分人相信了你，但也有人觉得你\"此地无银\"。",
                    moodChange = -3,
                    energyChange = -5,
                    moneyChange = 0,
                    reputationChange = -1
                ),
                EventChoice(
                    id = "find_source",
                    text = "追查源头",
                    consequence = "你找到了散布谣言的人。对方道歉了，但你们的关系彻底破裂。",
                    moodChange = 3,
                    energyChange = -10,
                    moneyChange = 0,
                    reputationChange = -5
                )
            )
        ),
        GameEvent(
            id = "social_exclusion",
            type = EventType.SOCIAL_CONFLICT,
            title = "被小团体排挤",
            description = "你发现同事/朋友形成了一个小团体，有意无意地把你排除在外。",
            severity = EventSeverity.MEDIUM,
            impactMood = -10,
            impactEnergy = -5,
            impactMoney = 0,
            choices = listOf(
                EventChoice(
                    id = "accept_exclusion",
                    text = "接受现状",
                    consequence = "你接受了被排挤的事实，开始享受独处的时光。",
                    moodChange = -5,
                    energyChange = 3,
                    moneyChange = 0,
                    reputationChange = 0
                ),
                EventChoice(
                    id = "join_group",
                    text = "尝试融入",
                    consequence = "你尝试主动融入他们。经过努力，他们终于接纳了你。",
                    moodChange = 5,
                    energyChange = -8,
                    moneyChange = 0,
                    reputationChange = 3
                ),
                EventChoice(
                    id = "form_new_group",
                    text = "另起炉灶",
                    consequence = "你没有强行融入，而是找到了其他志同道合的朋友。",
                    moodChange = 8,
                    energyChange = 0,
                    moneyChange = 0,
                    reputationChange = 2
                )
            )
        ),
        GameEvent(
            id = "social_favor",
            type = EventType.SOCIAL_CONFLICT,
            title = "人情债",
            description = "朋友帮了你一个大忙，现在他要求你帮一个超出你能力范围的忙作为回报。",
            severity = EventSeverity.HIGH,
            impactMood = -10,
            impactEnergy = -8,
            impactMoney = 0,
            choices = listOf(
                EventChoice(
                    id = "repay_favor",
                    text = "尽力帮忙",
                    consequence = "你硬着头皮帮了这个忙。虽然很累，但还清了人情。",
                    moodChange = -5,
                    energyChange = -15,
                    moneyChange = -500,
                    reputationChange = 5
                ),
                EventChoice(
                    id = "negotiate_favor",
                    text = "协商变通",
                    consequence = "你和朋友协商，用其他方式偿还人情。双方都比较满意。",
                    moodChange = -2,
                    energyChange = -5,
                    moneyChange = -200,
                    reputationChange = 1
                ),
                EventChoice(
                    id = "refuse_favor",
                    text = "坦诚拒绝",
                    consequence = "你坦诚地表示自己做不到。朋友有些失望，但也理解你的处境。",
                    moodChange = -5,
                    energyChange = 2,
                    moneyChange = 0,
                    reputationChange = -3
                )
            )
        )
    )

    private val policyEvents = listOf(
        GameEvent(
            id = "policy_tax_rise",
            type = EventType.POLICY_CHANGE,
            title = "税收调整",
            description = "新政策出台，对中低收入群体的税负有所增加。",
            severity = EventSeverity.HIGH,
            impactMood = -15,
            impactEnergy = -5,
            impactMoney = -800,
            choices = listOf(
                EventChoice(
                    id = "accept_tax",
                    text = "接受现实",
                    consequence = "你接受了税收调整。生活成本增加了，但日子还得过。",
                    moodChange = -10,
                    energyChange = 0,
                    moneyChange = -800,
                    reputationChange = 0
                ),
                EventChoice(
                    id = "cut_expense",
                    text = "削减开支",
                    consequence = "你开始精打细算，削减不必要的开支。生活质量下降了一些，但收支平衡了。",
                    moodChange = -5,
                    energyChange = -5,
                    moneyChange = -400,
                    reputationChange = 2
                ),
                EventChoice(
                    id = "protest",
                    text = "参与请愿",
                    consequence = "你参与了市民请愿活动。虽然不一定能改变政策，但表达了自己的声音。",
                    moodChange = 5,
                    energyChange = -10,
                    moneyChange = -100,
                    reputationChange = -3
                )
            )
        ),
        GameEvent(
            id = "policy_price_rise",
            type = EventType.POLICY_CHANGE,
            title = "物价上涨",
            description = "受政策影响，生活必需品价格普遍上涨。",
            severity = EventSeverity.HIGH,
            impactMood = -12,
            impactEnergy = -5,
            impactMoney = -500,
            choices = listOf(
                EventChoice(
                    id = "accept_price",
                    text = "承受涨价",
                    consequence = "你接受了物价上涨的现实。每个月的开支增加了不少。",
                    moodChange = -8,
                    energyChange = 0,
                    moneyChange = -500,
                    reputationChange = 0
                ),
                EventChoice(
                    id = "change_consumption",
                    text = "调整消费习惯",
                    consequence = "你开始购买更便宜的替代品，调整消费习惯。生活变得简朴了一些。",
                    moodChange = -3,
                    energyChange = -3,
                    moneyChange = -200,
                    reputationChange = 1
                ),
                EventChoice(
                    id = "increase_income",
                    text = "增加收入",
                    consequence = "你开始寻找副业或加班机会。收入增加了，但休息时间变少了。",
                    moodChange = 3,
                    energyChange = -12,
                    moneyChange = 300,
                    reputationChange = 2
                )
            )
        ),
        GameEvent(
            id = "policy_welfare_cut",
            type = EventType.POLICY_CHANGE,
            title = "福利削减",
            description = "公共福利项目有所削减，医疗、教育等方面的支出需要自己承担更多。",
            severity = EventSeverity.CRITICAL,
            impactMood = -18,
            impactEnergy = -10,
            impactMoney = -1000,
            choices = listOf(
                EventChoice(
                    id = "accept_welfare",
                    text = "接受现实",
                    consequence = "你接受了福利削减的现实。生活压力明显增加了。",
                    moodChange = -15,
                    energyChange = -5,
                    moneyChange = -1000,
                    reputationChange = 0
                ),
                EventChoice(
                    id = "save_more",
                    text = "增加储蓄",
                    consequence = "你大幅削减开支，增加储蓄应对未来的不确定性。生活变得非常节俭。",
                    moodChange = -8,
                    energyChange = -8,
                    moneyChange = -300,
                    reputationChange = 3
                ),
                EventChoice(
                    id = "move",
                    text = "考虑搬家",
                    consequence = "你开始考虑搬到生活成本更低的地方。这是一个重大的决定。",
                    moodChange = -5,
                    energyChange = -15,
                    moneyChange = -500,
                    reputationChange = -2
                )
            )
        )
    )

    fun generateEvent(socialLevel: Int, jobCompetition: Int, savings: Int): GameEvent? {
        val probability = calculateProbability(socialLevel, jobCompetition, savings)
        if (Random.nextDouble() > probability) {
            return null
        }

        val eventPool = when {
            jobCompetition >= 70 -> workplaceEvents + economicEvents
            socialLevel >= 70 -> socialEvents + economicEvents
            savings < 2000 -> economicEvents + socialEvents
            else -> emptyList()
        }

        if (eventPool.isEmpty()) {
            return null
        }

        return eventPool.random()
    }

    private fun calculateProbability(socialLevel: Int, jobCompetition: Int, savings: Int): Double {
        val socialFactor = socialLevel.coerceIn(0, 100) / 100.0 * 0.4
        val jobFactor = jobCompetition.coerceIn(0, 100) / 100.0 * 0.4
        val savingsFactor = if (savings < 2000) 0.3 else (2000 - savings).coerceAtLeast(-1000) / 2000.0 * 0.2

        return (socialFactor + jobFactor + savingsFactor).coerceIn(0.0, 0.8)
    }

    fun getAllEvents(): List<GameEvent> {
        return workplaceEvents + economicEvents + socialEvents + policyEvents
    }
}