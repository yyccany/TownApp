package com.example.townapp.data.companion

import androidx.compose.ui.graphics.Color
import com.example.townapp.ui.screens.Companion

/**
 * 小家伙温柔提醒系统
 * 
 * 核心逻辑：我们不禁止任何事，我们只解除"必须"
 * 不是"你不能让梨"，而是"你不用必须让梨"
 * 不是"你不能努力"，而是"你不用必须努力"
 * 
 * 这些提醒只有用户主动做了"过度规训"的操作才会触发
 * 语气是"提醒"，不是"指责"
 */

/**
 * 触发类型 - 用户做了什么过度规训的操作
 */
enum class TriggerType(val displayName: String) {
    ONLY_HEALTHY_FOOD("连续3天只吃健康餐"),
    STUDY_LATE_NIGHT("凌晨2点还在学习"),
    USE_SCAM_PRODUCT("用了智商税保健品"),
    NO_REST_WEEK("连续一周没休息"),
    GIVE_ALL_TO_OTHERS("把好东西都给了别人"),
    UNCOMFORTABLE_FAME("穿不舒服的名牌"),
    OVERWORK("过度工作"),
    SELF_SACRIFICE("自我牺牲"),
    FORCED_KINDNESS("被迫善良")
}

/**
 * 温柔提醒数据
 */
data class GentleReminder(
    val triggerType: TriggerType,
    val companionIndex: Int, // 0=塔菲喵, 1=doro, 2=咕咕嘎嘎
    val idiomReference: String, // 对应的成语
    val message: String, // 温柔提醒的内容
    val backgroundColor: Color
)

/**
 * 温柔提醒数据库
 */
object GentleReminderDatabase {
    
    val allReminders = listOf(
        // 连续3天只吃健康餐 - 塔菲喵
        GentleReminder(
            triggerType = TriggerType.ONLY_HEALTHY_FOOD,
            companionIndex = 0,
            idiomReference = "吃得苦中苦，方为人上人",
            message = "你不用'吃得苦中苦，方为人上人'哦，偶尔吃点薯片也没关系，小猫咪开心就好喵😺",
            backgroundColor = Color(0xFFFFE4EC)
        ),
        
        // 凌晨2点还在学习 - doro
        GentleReminder(
            triggerType = TriggerType.STUDY_LATE_NIGHT,
            companionIndex = 1,
            idiomReference = "头悬梁锥刺股",
            message = "你不用'头悬梁锥刺股'哦，困了就睡觉，身体比学习重要🥺",
            backgroundColor = Color(0xFFE4F0FF)
        ),
        
        // 用了智商税保健品 - 咕咕嘎嘎
        GentleReminder(
            triggerType = TriggerType.USE_SCAM_PRODUCT,
            companionIndex = 2,
            idiomReference = "一分钱一分货",
            message = "你不用'一分钱一分货'哦，贵的不一定好，开心就好咕咕！",
            backgroundColor = Color(0xFFE4FFE8)
        ),
        
        // 连续一周没休息 - 塔菲喵
        GentleReminder(
            triggerType = TriggerType.NO_REST_WEEK,
            companionIndex = 0,
            idiomReference = "天道酬勤",
            message = "你不用'天道酬勤'哦，努力不一定成功，休息一下也没关系喵😺",
            backgroundColor = Color(0xFFFFE4EC)
        ),
        
        // 把好东西都给了别人 - doro
        GentleReminder(
            triggerType = TriggerType.GIVE_ALL_TO_OTHERS,
            companionIndex = 1,
            idiomReference = "舍己为人",
            message = "你不用'舍己为人'哦，你自己才是最重要的🥺",
            backgroundColor = Color(0xFFE4F0FF)
        ),
        
        // 穿不舒服的名牌 - 咕咕嘎嘎
        GentleReminder(
            triggerType = TriggerType.UNCOMFORTABLE_FAME,
            companionIndex = 2,
            idiomReference = "人靠衣装",
            message = "你不用'人靠衣装'哦，舒服比面子重要，我们都爱你咕咕！",
            backgroundColor = Color(0xFFE4FFE8)
        ),
        
        // 过度工作 - doro
        GentleReminder(
            triggerType = TriggerType.OVERWORK,
            companionIndex = 1,
            idiomReference = "勤能补拙",
            message = "你不用'勤能补拙'哦，你已经很棒了，不用证明什么🥺",
            backgroundColor = Color(0xFFE4F0FF)
        ),
        
        // 自我牺牲 - 塔菲喵
        GentleReminder(
            triggerType = TriggerType.SELF_SACRIFICE,
            companionIndex = 0,
            idiomReference = "吃亏是福",
            message = "吃亏不是福哦，是别人在欺负你。保护好自己，才是最重要的喵😺",
            backgroundColor = Color(0xFFFFE4EC)
        ),
        
        // 被迫善良 - doro
        GentleReminder(
            triggerType = TriggerType.FORCED_KINDNESS,
            companionIndex = 1,
            idiomReference = "忍一时风平浪静",
            message = "忍一时越想越气，退一步越想越亏。你不用忍，你可以发脾气🥺",
            backgroundColor = Color(0xFFE4F0FF)
        )
    )
    
    /**
     * 根据触发类型获取提醒
     */
    fun getReminder(triggerType: TriggerType): GentleReminder? {
        return allReminders.find { it.triggerType == triggerType }
    }
    
    /**
     * 随机获取一个提醒（用于测试）
     */
    fun getRandomReminder(): GentleReminder {
        return allReminders.random()
    }
}

/**
 * 触发检测器 - 检测用户是否做了过度规训的操作
 * 
 * 注意：这些检测是"观察"，不是"监控"
 * 我们只是看看用户做了什么，然后温柔的提醒
 * 不评判、不指责、不强迫
 */
class TriggerDetector {
    
    // 记录用户的行为（用于检测连续操作）
    private val behaviorLog = mutableMapOf<String, Int>()
    
    /**
     * 记录用户行为
     */
    fun logBehavior(behaviorType: String) {
        behaviorLog[behaviorType] = (behaviorLog[behaviorType] ?: 0) + 1
    }
    
    /**
     * 检测是否应该触发提醒
     */
    fun shouldTriggerReminder(triggerType: TriggerType): Boolean {
        return when (triggerType) {
            TriggerType.ONLY_HEALTHY_FOOD -> {
                // 连续3天只吃健康餐
                behaviorLog["healthy_food_only"] ?: 0 >= 3
            }
            TriggerType.STUDY_LATE_NIGHT -> {
                // 凌晨2点还在学习
                behaviorLog["study_late_night"] ?: 0 >= 1
            }
            TriggerType.USE_SCAM_PRODUCT -> {
                // 用了智商税保健品
                behaviorLog["scam_product"] ?: 0 >= 1
            }
            TriggerType.NO_REST_WEEK -> {
                // 连续一周没休息
                behaviorLog["no_rest_days"] ?: 0 >= 7
            }
            TriggerType.GIVE_ALL_TO_OTHERS -> {
                // 把好东西都给了别人
                behaviorLog["give_all"] ?: 0 >= 1
            }
            TriggerType.UNCOMFORTABLE_FAME -> {
                // 穿不舒服的名牌
                behaviorLog["uncomfortable_fame"] ?: 0 >= 1
            }
            TriggerType.OVERWORK -> {
                // 过度工作
                behaviorLog["overwork_hours"] ?: 0 >= 12
            }
            TriggerType.SELF_SACRIFICE -> {
                // 自我牺牲
                behaviorLog["self_sacrifice"] ?: 0 >= 1
            }
            TriggerType.FORCED_KINDNESS -> {
                // 被迫善良
                behaviorLog["forced_kindness"] ?: 0 >= 1
            }
        }
    }
    
    /**
     * 清除行为记录（触发提醒后清除）
     */
    fun clearBehavior(behaviorType: String) {
        behaviorLog.remove(behaviorType)
    }
    
    /**
     * 获取所有触发类型
     */
    fun checkAllTriggers(): List<TriggerType> {
        return TriggerType.entries.filter { shouldTriggerReminder(it) }
    }
}