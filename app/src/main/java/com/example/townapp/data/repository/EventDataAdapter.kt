package com.example.townapp.data.repository

import com.example.townapp.domain.engine.PlayerState

/**
 * 🎭 事件数据适配器
 * 
 * 将健康/精神事件数据转换为可接入模拟引擎的格式
 * 事件不再是纯弹窗，触发后真的改状态
 */
data class GameEvent(
    val id: String,
    val name: String,
    val description: String,
    val category: String,  // health_event, mental_event
    val triggerConditions: EventTriggerConditions = EventTriggerConditions(),
    val stateEffect: StateEffect = StateEffect(),
    val triggerWeight: Int = 10,
    val cooldownDays: Int = 3,  // 触发冷却天数
    var lastTriggerDay: Int = -999
)

/**
 * 事件触发条件
 */
data class EventTriggerConditions(
    // 状态阈值条件
    val minHunger: Double = 0.0,
    val maxHunger: Double = 100.0,
    val minEnergy: Double = 0.0,
    val maxEnergy: Double = 100.0,
    val minHealth: Double = 0.0,
    val maxHealth: Double = 100.0,
    val minHappiness: Double = 0.0,
    val maxHappiness: Double = 100.0,
    val minAnxiety: Double = 0.0,
    val maxAnxiety: Double = 100.0,
    val minLoneliness: Double = 0.0,
    val maxLoneliness: Double = 100.0,
    val minControl: Double = 0.0,
    val maxControl: Double = 100.0,
    
    // 触发场景标签
    val triggerScenes: List<String> = emptyList()
)

/**
 * 事件配置仓库
 */
object GameEventRepository {
    private val events = listOf(
        // ========== 健康事件 ==========
        GameEvent(
            id = "event_gastro",
            name = "肠胃炎",
            description = "可能是吃坏肚子了，上吐下泻，身体很虚弱",
            category = "health_event",
            triggerConditions = EventTriggerConditions(
                minHunger = 0.0,
                maxHunger = 30.0,
                minHealth = 0.0,
                maxHealth = 60.0,
                triggerScenes = listOf(TriggerScene.HEALTH_EVENT)
            ),
            stateEffect = StateEffect(
                health = -15.0,
                energy = -20.0,
                happiness = -10.0,
                anxiety = 10.0
            ),
            triggerWeight = 15,
            cooldownDays = 7
        ),
        GameEvent(
            id = "event_cold",
            name = "感冒",
            description = "喉咙痛、流鼻涕，浑身无力",
            category = "health_event",
            triggerConditions = EventTriggerConditions(
                minEnergy = 0.0,
                maxEnergy = 40.0,
                minHealth = 0.0,
                maxHealth = 70.0,
                triggerScenes = listOf(TriggerScene.BODY_OVERLOAD)
            ),
            stateEffect = StateEffect(
                health = -10.0,
                energy = -25.0,
                happiness = -5.0
            ),
            triggerWeight = 20,
            cooldownDays = 5
        ),
        GameEvent(
            id = "event_insomnia",
            name = "失眠",
            description = "躺在床上翻来覆去睡不着，脑子里想太多事情",
            category = "health_event",
            triggerConditions = EventTriggerConditions(
                minAnxiety = 60.0,
                maxAnxiety = 100.0,
                minEnergy = 0.0,
                maxEnergy = 50.0,
                triggerScenes = listOf(TriggerScene.SLEEP_DEPRIVATION)
            ),
            stateEffect = StateEffect(
                energy = -30.0,
                happiness = -8.0,
                anxiety = 5.0,
                health = -5.0
            ),
            triggerWeight = 25,
            cooldownDays = 3
        ),
        GameEvent(
            id = "event_headache",
            name = "头痛",
            description = "头痛欲裂，注意力无法集中",
            category = "health_event",
            triggerConditions = EventTriggerConditions(
                minEnergy = 0.0,
                maxEnergy = 30.0,
                minAnxiety = 50.0,
                maxAnxiety = 100.0,
                triggerScenes = listOf(TriggerScene.BODY_OVERLOAD)
            ),
            stateEffect = StateEffect(
                energy = -15.0,
                happiness = -10.0,
                control = -10.0
            ),
            triggerWeight = 18,
            cooldownDays = 2
        ),
        // ========== 精神事件 ==========
        GameEvent(
            id = "event_anxiety_attack",
            name = "焦虑发作",
            description = "突然感到一阵恐慌，心跳加速，喘不过气",
            category = "mental_event",
            triggerConditions = EventTriggerConditions(
                minAnxiety = 75.0,
                maxAnxiety = 100.0,
                triggerScenes = listOf(TriggerScene.ANXIETY_HIGH)
            ),
            stateEffect = StateEffect(
                anxiety = 20.0,
                happiness = -15.0,
                energy = -20.0,
                health = -8.0
            ),
            triggerWeight = 12,
            cooldownDays = 7
        ),
        GameEvent(
            id = "event_low_mood",
            name = "情绪低落",
            description = "提不起劲做任何事，觉得一切都没有意义",
            category = "mental_event",
            triggerConditions = EventTriggerConditions(
                minHappiness = 0.0,
                maxHappiness = 30.0,
                minLoneliness = 50.0,
                triggerScenes = listOf(TriggerScene.ANXIETY_HIGH)
            ),
            stateEffect = StateEffect(
                happiness = -10.0,
                loneliness = 15.0,
                control = -5.0
            ),
            triggerWeight = 22,
            cooldownDays = 4
        ),
        GameEvent(
            id = "event_loneliness",
            name = "孤独感",
            description = "一个人待久了，突然觉得很孤独",
            category = "mental_event",
            triggerConditions = EventTriggerConditions(
                minLoneliness = 60.0,
                maxHappiness = 40.0,
                triggerScenes = listOf(TriggerScene.ANXIETY_HIGH)
            ),
            stateEffect = StateEffect(
                loneliness = 20.0,
                happiness = -8.0,
                anxiety = 5.0
            ),
            triggerWeight = 18,
            cooldownDays = 3
        ),
        GameEvent(
            id = "event_burnout",
            name = "身心俱疲",
            description = "连续高强度工作后，感觉身体被掏空",
            category = "mental_event",
            triggerConditions = EventTriggerConditions(
                minEnergy = 0.0,
                maxEnergy = 20.0,
                minAnxiety = 60.0,
                triggerScenes = listOf(TriggerScene.BODY_OVERLOAD)
            ),
            stateEffect = StateEffect(
                energy = -35.0,
                happiness = -15.0,
                health = -10.0,
                control = -15.0
            ),
            triggerWeight = 10,
            cooldownDays = 10
        ),
        // ========== 正面事件 ==========
        GameEvent(
            id = "event_good_sleep",
            name = "一夜好眠",
            description = "难得睡了一个好觉，精神焕发",
            category = "mental_event",
            triggerConditions = EventTriggerConditions(
                minEnergy = 0.0,
                maxEnergy = 30.0,
                minHappiness = 40.0,
                maxAnxiety = 40.0
            ),
            stateEffect = StateEffect(
                energy = 40.0,
                happiness = 10.0,
                anxiety = -10.0,
                health = 5.0
            ),
            triggerWeight = 15,
            cooldownDays = 5
        ),
        GameEvent(
            id = "event_mood_lift",
            name = "心情好转",
            description = "不知为什么，心情突然变好了",
            category = "mental_event",
            triggerConditions = EventTriggerConditions(
                minHappiness = 0.0,
                maxHappiness = 40.0
            ),
            stateEffect = StateEffect(
                happiness = 15.0,
                anxiety = -10.0,
                loneliness = -10.0
            ),
            triggerWeight = 12,
            cooldownDays = 4
        ),
        // ========== 成就/奖励类正向事件 ==========
        GameEvent(
            id = "event_healthy_meal",
            name = "好好吃饭",
            description = "今天吃了营养均衡的一餐，身体很舒服",
            category = "reward_event",
            triggerConditions = EventTriggerConditions(
                minHappiness = 30.0,
                maxHunger = 50.0,
                minHealth = 50.0,
                triggerScenes = listOf(TriggerScene.HEALTH_EVENT)
            ),
            stateEffect = StateEffect(
                health = 8.0,
                energy = 10.0,
                happiness = 12.0,
                anxiety = -5.0
            ),
            triggerWeight = 20,
            cooldownDays = 2
        ),
        GameEvent(
            id = "event_good_rest",
            name = "充足的休息",
            description = "今天睡了个好觉，感觉精力充沛",
            category = "reward_event",
            triggerConditions = EventTriggerConditions(
                minEnergy = 60.0,
                maxAnxiety = 40.0,
                triggerScenes = listOf(TriggerScene.SLEEP_DEPRIVATION)
            ),
            stateEffect = StateEffect(
                energy = 25.0,
                health = 5.0,
                happiness = 8.0,
                anxiety = -8.0
            ),
            triggerWeight = 18,
            cooldownDays = 3
        ),
        GameEvent(
            id = "event_accomplishment",
            name = "小有成就",
            description = "完成了一件事，虽然不大，但很有成就感",
            category = "reward_event",
            triggerConditions = EventTriggerConditions(
                minControl = 50.0,
                maxAnxiety = 60.0,
                triggerScenes = listOf(TriggerScene.BODY_OVERLOAD)
            ),
            stateEffect = StateEffect(
                happiness = 15.0,
                control = 10.0,
                anxiety = -10.0,
                skillLevel = 2.0
            ),
            triggerWeight = 15,
            cooldownDays = 5
        ),
        GameEvent(
            id = "event_social_connection",
            name = "温暖的连接",
            description = "和朋友聊了聊天，感到被理解和支持",
            category = "reward_event",
            triggerConditions = EventTriggerConditions(
                minLoneliness = 40.0,
                maxHappiness = 60.0,
                triggerScenes = listOf(TriggerScene.ANXIETY_HIGH)
            ),
            stateEffect = StateEffect(
                loneliness = -20.0,
                happiness = 12.0,
                anxiety = -8.0,
                control = 5.0
            ),
            triggerWeight = 16,
            cooldownDays = 4
        ),
        GameEvent(
            id = "event_nature_moment",
            name = "自然时刻",
            description = "在户外待了一会儿，阳光和微风让心情平静",
            category = "reward_event",
            triggerConditions = EventTriggerConditions(
                minAnxiety = 30.0,
                maxHealth = 80.0,
                triggerScenes = listOf(TriggerScene.ANXIETY_HIGH)
            ),
            stateEffect = StateEffect(
                anxiety = -15.0,
                happiness = 10.0,
                energy = 5.0,
                health = 3.0
            ),
            triggerWeight = 14,
            cooldownDays = 3
        ),
        GameEvent(
            id = "event_simple_joy",
            name = "简单的快乐",
            description = "做了一件自己喜欢的小事，很满足",
            category = "reward_event",
            triggerConditions = EventTriggerConditions(
                minHappiness = 20.0,
                maxHappiness = 50.0,
                triggerScenes = listOf(TriggerScene.HEALTH_EVENT)
            ),
            stateEffect = StateEffect(
                happiness = 18.0,
                anxiety = -5.0,
                loneliness = -8.0
            ),
            triggerWeight = 17,
            cooldownDays = 2
        ),
        GameEvent(
            id = "event_self_care",
            name = "自我关爱",
            description = "今天做了一件照顾自己的事，对自己很满意",
            category = "reward_event",
            triggerConditions = EventTriggerConditions(
                minHealth = 40.0,
                minControl = 30.0,
                triggerScenes = listOf(TriggerScene.BODY_OVERLOAD)
            ),
            stateEffect = StateEffect(
                happiness = 15.0,
                control = 12.0,
                health = 5.0,
                anxiety = -10.0
            ),
            triggerWeight = 15,
            cooldownDays = 4
        ),
        // ========== 中性/混合效果事件 ==========
        GameEvent(
            id = "event_moderate_stress",
            name = "适度压力",
            description = "有些任务需要完成，但感觉能够应对",
            category = "neutral_event",
            triggerConditions = EventTriggerConditions(
                minAnxiety = 30.0,
                maxAnxiety = 60.0,
                minControl = 40.0,
                triggerScenes = listOf(TriggerScene.BODY_OVERLOAD)
            ),
            stateEffect = StateEffect(
                anxiety = 8.0,
                happiness = -3.0,
                skillLevel = 3.0,
                energy = -5.0
            ),
            triggerWeight = 20,
            cooldownDays = 3
        ),
        GameEvent(
            id = "event_comfort_food",
            name = "慰藉食物",
            description = "吃了点想吃的，虽然不太健康，但心情好了",
            category = "neutral_event",
            triggerConditions = EventTriggerConditions(
                minHappiness = 0.0,
                maxHappiness = 35.0,
                minLoneliness = 30.0,
                triggerScenes = listOf(TriggerScene.ANXIETY_HIGH)
            ),
            stateEffect = StateEffect(
                happiness = 12.0,
                loneliness = -10.0,
                health = -3.0,
                anxiety = -5.0
            ),
            triggerWeight = 18,
            cooldownDays = 4
        ),
        // ========== 难度更高的负面事件（需要有更极端的条件） ==========
        GameEvent(
            id = "event_severe_exhaustion",
            name = "严重疲劳",
            description = "连续高压工作后，身体发出了强烈警告",
            category = "health_event",
            triggerConditions = EventTriggerConditions(
                minEnergy = 0.0,
                maxEnergy = 15.0,
                minAnxiety = 70.0,
                triggerScenes = listOf(TriggerScene.BODY_OVERLOAD)
            ),
            stateEffect = StateEffect(
                energy = -40.0,
                happiness = -20.0,
                health = -15.0,
                anxiety = 15.0,
                control = -20.0
            ),
            triggerWeight = 8,
            cooldownDays = 14
        ),
        GameEvent(
            id = "event_depression_risk",
            name = "情绪低落风险",
            description = "持续的低落情绪开始影响日常生活",
            category = "mental_event",
            triggerConditions = EventTriggerConditions(
                minHappiness = 0.0,
                maxHappiness = 20.0,
                minLoneliness = 70.0,
                minAnxiety = 60.0,
                triggerScenes = listOf(TriggerScene.ANXIETY_HIGH)
            ),
            stateEffect = StateEffect(
                happiness = -15.0,
                loneliness = 25.0,
                anxiety = 15.0,
                control = -15.0
            ),
            triggerWeight = 5,
            cooldownDays = 21
        )
    )

    fun getAllEvents(): List<GameEvent> = events
    
    fun getEventsByCategory(category: String): List<GameEvent> = 
        events.filter { it.category == category }
    
    fun getEventById(id: String): GameEvent? = events.find { it.id == id }
}

/**
 * 事件数据适配器 - 实现ISimulatableData接口
 */
class EventDataAdapter(private val event: GameEvent) : ISimulatableData {
    override val id: String = event.id
    override val name: String = event.name

    override fun canTrigger(currentState: PlayerState): Boolean {
        // 检查开关
        if (!SimulationDataSwitch.enableEventEffects) return false
        
        // 检查状态条件
        val conditions = event.triggerConditions
        return currentState.hunger in conditions.minHunger..conditions.maxHunger &&
               currentState.energy in conditions.minEnergy..conditions.maxEnergy &&
               currentState.health in conditions.minHealth..conditions.maxHealth &&
               currentState.happiness in conditions.minHappiness..conditions.maxHappiness &&
               currentState.anxiety in conditions.minAnxiety..conditions.maxAnxiety &&
               currentState.loneliness in conditions.minLoneliness..conditions.maxLoneliness &&
               currentState.control in conditions.minControl..conditions.maxControl
    }

    override fun getStateEffect(): StateEffect {
        return event.stateEffect
    }

    override fun getDisplayText(): String {
        return event.description
    }

    override fun getTriggerWeight(): Int {
        return event.triggerWeight
    }
}
