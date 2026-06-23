package com.example.townapp.feature.town_simulation

import com.example.townapp.data.database.entity.UserBodyState
import com.example.townapp.data.database.entity.UserMentalState
import com.example.townapp.data.database.entity.UserSpaceState
import kotlin.random.Random

/**
 * 状态驱动事件引擎
 *
 * 设计原则：
 * - 不是随机事件，而是基于状态触发的事件
 * - 每一个事件都应该让玩家感觉到"这就是我的生活"
 * - 事件的严重程度与状态的恶化程度正相关
 *
 * 触发规则：
 * - 饥饿 → 低血糖 → 医疗支出
 * - 高油高糖持续 → 肠胃炎 → 医疗费 + 请假
 * - 高噪音 + 长期焦虑 → 失眠 → 精力下降 → 工作效率低
 * - 租金压力大 + 存款低 → 财务焦虑 → 焦虑加剧
 * - 长期孤独 → 情绪崩溃 → 需要社交 / 宠物陪伴
 * - 掌控感持续低 → 冲动消费 → 买贵的东西但不快乐
 */
object StateDrivenEventEngine {

    /**
     * 基于当前完整状态评估，检查应该触发的事件
     * @return 可能触发的事件列表（空列表表示无需触发任何事件）
     */
    fun evaluateTriggers(
        body: UserBodyState,
        space: UserSpaceState,
        mental: UserMentalState,
        daysInNegativeState: Int
    ): List<StateDrivenEvent> {
        val events = mutableListOf<StateDrivenEvent>()

        // ============================================
        // 身体事件
        // ============================================
        // 1. 严重饥饿 → 身体不适
        if (body.satiety < 10 && Random.nextDouble() < 0.4) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.HEALTH,
                    severity = EventSeverity.MODERATE,
                    title = "饿得头晕",
                    description = "你饿得手脚发软，胃隐隐作痛。这样下去不是办法。",
                    immediateCostYuan = 0.0,
                    immediateEnergyDelta = -15,
                    immediateHealthDelta = -10,
                    immediateAnxietyDelta = 5
                )
            )
        }

        // 2. 肠胃负担高 → 肠胃炎
        if (body.gastroBurden > 85 && Random.nextDouble() < 0.3) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.HEALTH,
                    severity = EventSeverity.HIGH,
                    title = "急性肠胃炎",
                    description = "胃很不舒服，跑了好几趟厕所。可能得去医院，或者至少休息几天。",
                    immediateCostYuan = 200.0 + Random.nextInt(100, 300),
                    immediateEnergyDelta = -25,
                    immediateHealthDelta = -20,
                    immediateAnxietyDelta = 10,
                    workBlockedDays = 2
                )
            )
        }

        // 3. 毒素过载 → 皮肤爆痘 / 过敏
        if (body.toxinLevel > 75 && Random.nextDouble() < 0.25) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.HEALTH,
                    severity = EventSeverity.LOW,
                    title = "皮肤过敏",
                    description = "脸上和后背冒出好多痘痘，皮肤状态很差。",
                    immediateCostYuan = 80.0,
                    immediateEnergyDelta = -5,
                    immediateHealthDelta = -5,
                    immediateEsteemDelta = -5
                )
            )
        }

        // 4. 血糖飙升 → 犯困乏力
        if (body.bloodSugar > 9.0 && Random.nextDouble() < 0.3) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.HEALTH,
                    severity = EventSeverity.LOW,
                    title = "饭后昏昏欲睡",
                    description = "吃完这顿后血糖飙升，整个人困得不行，只想找地方躺平。",
                    immediateCostYuan = 0.0,
                    immediateEnergyDelta = -15,
                    immediateHappinessDelta = -5,
                    workEfficiencyMultiplier = 0.5
                )
            )
        }

        // ============================================
        // 空间事件
        // ============================================
        // 5. 高噪音 → 失眠
        if (space.noise > 75 && Random.nextDouble() < 0.3) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.SPACE,
                    severity = EventSeverity.MODERATE,
                    title = "又失眠了",
                    description = "外面太吵了，翻来覆去到半夜才睡着。第二天状态很差。",
                    immediateCostYuan = 0.0,
                    immediateEnergyDelta = -20,
                    immediateAnxietyDelta = 10,
                    immediateHappinessDelta = -8,
                    workEfficiencyMultiplier = 0.6
                )
            )
        }

        // 6. 低采光 → 情绪低落
        if (space.light < 25 && Random.nextDouble() < 0.25) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.SPACE,
                    severity = EventSeverity.LOW,
                    title = "感觉有点闷",
                    description = "房间里暗沉沉的，好像心里也照不到阳光。整个人有点提不起劲。",
                    immediateCostYuan = 0.0,
                    immediateEnergyDelta = -8,
                    immediateHappinessDelta = -10,
                    immediateMeaningDelta = -5
                )
            )
        }

        // 7. 租金压力 + 存款低 → 财务焦虑
        if (space.rentToIncomeRatio > 50 && space.currentSavings < space.monthlyRent * 2 && Random.nextDouble() < 0.35) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.FINANCE,
                    severity = EventSeverity.HIGH,
                    title = "房租压力好大",
                    description = "看着银行卡余额和下个月的房租，心里一阵焦虑。这笔钱必须得想办法。",
                    immediateCostYuan = 0.0,
                    immediateAnxietyDelta = 20,
                    immediateControlDelta = -15,
                    immediateHappinessDelta = -10
                )
            )
        }

        // 8. 长通勤 → 疲惫累积
        if (space.commuteMinutesOneWay > 75 && Random.nextDouble() < 0.3) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.SPACE,
                    severity = EventSeverity.MODERATE,
                    title = "通勤太累",
                    description = "挤了一个多小时地铁/公交，到家的时候已经精疲力竭。什么都不想做了。",
                    immediateCostYuan = 0.0,
                    immediateEnergyDelta = -25,
                    immediateAnxietyDelta = 5,
                    immediateHappinessDelta = -8
                )
            )
        }

        // 9. 小空间 + 物品多 → 烦躁
        if (space.areaSqm < 15 && Random.nextDouble() < 0.25 && daysInNegativeState >= 2) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.SPACE,
                    severity = EventSeverity.LOW,
                    title = "地方太小",
                    description = "看着屋里堆得到处都是的东西，心里一阵烦躁。想整理又没力气。",
                    immediateCostYuan = 0.0,
                    immediateAnxietyDelta = 8,
                    immediateComfortDelta = -10
                )
            )
        }

        // ============================================
        // 精神事件
        // ============================================
        // 10. 焦虑高 → 想刷手机逃避
        if (mental.anxiety > 70 && Random.nextDouble() < 0.4) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.MENTAL,
                    severity = EventSeverity.MODERATE,
                    title = "想逃避",
                    description = "心里乱乱的，只想躺着刷短视频或者玩游戏，不想面对任何事情。",
                    immediateCostYuan = if (Random.nextDouble() < 0.5) (Random.nextInt(30, 200)).toDouble() else 0.0,
                    immediateEnergyDelta = -5,
                    immediateAnxietyDelta = -3,
                    impliedBehaviorHint = "ESC"  // 暗示玩家倾向于逃避行为
                )
            )
        }

        // 11. 孤独高 → 想联系某人但又没力气
        if (mental.loneliness > 75 && Random.nextDouble() < 0.3 && mental.energyLevel < 50) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.MENTAL,
                    severity = EventSeverity.MODERATE,
                    title = "想联系又没力气",
                    description = "看着通讯录里的朋友名字，想发消息又放下了。算了，太累了。",
                    immediateCostYuan = 0.0,
                    immediateEnergyDelta = -5,
                    immediateHappinessDelta = -5,
                    immediateLonelinessDelta = 8
                )
            )
        }

        // 12. 掌控感低 + 焦虑高 → 冲动消费（最大的差异化体验）
        if (mental.senseOfControl < 20 && mental.anxiety > 75 && Random.nextDouble() < 0.35) {
            val impulseAmount = when {
                space.monthlyIncome > 0 -> (space.monthlyIncome * Random.nextDouble(0.05, 0.25))
                else -> 200.0
            }
            events.add(
                StateDrivenEvent(
                    category = EventCategory.MENTAL,
                    severity = EventSeverity.HIGH,
                    title = "失控消费",
                    description = "你控制不住地下单了一堆本来不需要的东西。买完那一刻很爽，但马上又后悔了。",
                    immediateCostYuan = impulseAmount,
                    immediateHappinessDelta = 10,
                    immediateAnxietyDelta = 15,
                    immediateControlDelta = -10,
                    immediateEsteemDelta = -8,
                    delayedDaysToRegret = 1  // 第二天会后悔
                )
            )
        }

        // 13. 意义感低 → 迷茫发呆
        if (mental.meaning < 20 && Random.nextDouble() < 0.25) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.MENTAL,
                    severity = EventSeverity.MODERATE,
                    title = "又在发呆",
                    description = "你盯着天花板/屏幕发呆了好久。不知道这一切是为了什么。时间就这样过去了。",
                    immediateCostYuan = 0.0,
                    immediateEnergyDelta = -5,
                    immediateMeaningDelta = -5,
                    workEfficiencyMultiplier = 0.5
                )
            )
        }

        // 14. 自尊低 → 自我否定
        if (mental.selfEsteem < 25 && Random.nextDouble() < 0.3 && daysInNegativeState >= 3) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.MENTAL,
                    severity = EventSeverity.HIGH,
                    title = "又开始自我否定",
                    description = "脑子里有个声音在说：你什么都做不好。你知道这不理性，但你止不住这样想。",
                    immediateCostYuan = 0.0,
                    immediateHappinessDelta = -15,
                    immediateEsteemDelta = -8,
                    immediateAnxietyDelta = 10,
                    immediateMeaningDelta = -8,
                    workEfficiencyMultiplier = 0.4
                )
            )
        }

        // ============================================
        // 综合事件（多种因素共同触发）
        // ============================================
        // 15. 全面崩溃（极低概率但最有冲击力）
        val isEverythingBad = body.healthScore < 40 &&
                mental.happiness < 20 &&
                mental.anxiety > 70 &&
                mental.senseOfControl < 20 &&
                space.currentSavings < space.monthlyRent * 1.5 &&
                daysInNegativeState >= 5

        if (isEverythingBad && Random.nextDouble() < 0.2) {
            events.add(
                StateDrivenEvent(
                    category = EventCategory.CRITICAL,
                    severity = EventSeverity.CRITICAL,
                    title = "崩溃",
                    description = "你终于撑不住了。坐在地上/床上，什么也不想做，只想哭。也许哭出来会好一点。",
                    immediateCostYuan = 0.0,
                    immediateEnergyDelta = -30,
                    immediateHealthDelta = -15,
                    immediateHappinessDelta = -20,
                    immediateAnxietyDelta = 15,
                    immediateMeaningDelta = -15,
                    immediateLonelinessDelta = 10,
                    workBlockedDays = 3,
                    workEfficiencyMultiplier = 0.0
                )
            )
        }

        return events
    }

    /**
     * 简化版：只返回最高优先级的一个事件（避免事件轰炸）
     */
    fun pickTopEvent(events: List<StateDrivenEvent>): StateDrivenEvent? {
        if (events.isEmpty()) return null

        // 严重程度优先，然后按金额影响力排序
        val sorted = events.sortedWith(compareBy(
            { -it.severity.ordinal },          // 严重程度降序
            { -it.immediateCostYuan },         // 金额大的优先展示
            { -(it.immediateEnergyDelta + it.immediateHappinessDelta) } // 影响大优先
        ))

        return sorted.first()
    }

    /**
     * 计算事件触发后对玩家各维度的净变化（用于UI展示）
     */
    fun summarizeImpact(event: StateDrivenEvent): String {
        val impacts = mutableListOf<String>()

        if (event.immediateCostYuan > 0) {
            impacts.add("支出 ¥${"%.0f".format(event.immediateCostYuan)}")
        }
        if (event.immediateEnergyDelta != 0) {
            impacts.add("精力 ${if (event.immediateEnergyDelta > 0) "+" else ""}${event.immediateEnergyDelta}")
        }
        if (event.immediateHealthDelta != 0) {
            impacts.add("健康 ${if (event.immediateHealthDelta > 0) "+" else ""}${event.immediateHealthDelta}")
        }
        if (event.immediateHappinessDelta != 0) {
            impacts.add("快乐 ${if (event.immediateHappinessDelta > 0) "+" else ""}${event.immediateHappinessDelta}")
        }
        if (event.immediateAnxietyDelta != 0) {
            impacts.add("焦虑 ${if (event.immediateAnxietyDelta > 0) "+" else ""}${event.immediateAnxietyDelta}")
        }
        if (event.workBlockedDays > 0) {
            impacts.add("无法工作 ${event.workBlockedDays} 天")
        }
        if (event.workEfficiencyMultiplier < 1.0) {
            impacts.add("工作效率 × ${event.workEfficiencyMultiplier}")
        }

        return if (impacts.isEmpty()) "没有直接影响" else impacts.joinToString("，")
    }
}

// ============================================
// 事件数据结构
// ============================================

data class StateDrivenEvent(
    val category: EventCategory,
    val severity: EventSeverity,
    val title: String,
    val description: String,

    // 即时经济影响
    val immediateCostYuan: Double = 0.0,

    // 即时身体影响
    val immediateEnergyDelta: Int = 0,
    val immediateHealthDelta: Int = 0,
    val immediateComfortDelta: Int = 0,

    // 即时精神影响
    val immediateHappinessDelta: Int = 0,
    val immediateAnxietyDelta: Int = 0,
    val immediateLonelinessDelta: Int = 0,
    val immediateControlDelta: Int = 0,
    val immediateEsteemDelta: Int = 0,
    val immediateMeaningDelta: Int = 0,

    // 工作影响
    val workEfficiencyMultiplier: Double = 1.0,
    val workBlockedDays: Int = 0,

    // 延迟影响（第二天后悔等）
    val delayedDaysToRegret: Int = 0,

    // 行为暗示（用于AI建议时给玩家提示可能倾向的选择）
    val impliedBehaviorHint: String = ""
)

enum class EventCategory(val displayName: String, val emoji: String) {
    HEALTH("身体", "🩺"),
    SPACE("居住", "🏠"),
    FINANCE("财务", "💰"),
    MENTAL("心理", "🧠"),
    CRITICAL("危机", "⚠️")
}

enum class EventSeverity(val displayName: String, val level: Int) {
    NEGLIGIBLE("几乎无感", 0),
    LOW("轻微", 1),
    MODERATE("中等", 2),
    HIGH("严重", 3),
    CRITICAL("危机", 4)
}
