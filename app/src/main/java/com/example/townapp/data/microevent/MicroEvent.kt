package com.example.townapp.data.microevent

/**
 * 日常微事件数据模型
 * 
 * 核心原则：
 * 1. 唯一主体：全小镇只有「你」一个人
 * 2. 绝对先体验后告知：所有物品先穿/先用，再讲代价
 * 3. 只有陪伴：所有事件都没有惩罚，只有软乎乎的陪伴
 * 
 * 99%的现实我们讲透，但那1%的爱，要渗透进每一个细碎的日常里。
 */

// ============================================
// 角色定义
// ============================================

/**
 * 小镇角色枚举
 */
enum class TownCharacter(
    val emoji: String,
    val displayName: String,
    val description: String
) {
    TAFFI("😺", "塔菲", "负责身体微事件"),
    GUGAGA("🐧", "咕咕嘎嘎", "负责家务微事件"),
    DORO("🥺", "朵朵", "负责环境微事件")
}

// ============================================
// 事件类型
// ============================================

/**
 * 微事件类型
 */
enum class MicroEventType {
    BODY,       // 身体微事件
    HOUSEWORK,  // 家务微事件
    ENVIRONMENT, // 环境微事件
    MOOD        // 心情微事件
}

// ============================================
// 微事件数据类
// ============================================

/**
 * 微事件
 */
data class MicroEvent(
    val id: String,
    val type: MicroEventType,
    val triggerCondition: String,      // 触发条件
    val character: TownCharacter,       // 负责的角色
    val content: String,               // 事件内容
    val isPositive: Boolean = true,    // 是否是积极事件
    val priority: Int = 0,            // 优先级（0最低）
    val tags: List<String> = emptyList() // 标签
)

// ============================================
// 心情状态
// ============================================

/**
 * 用户心情状态
 */
enum class MoodState(val emoji: String, val description: String) {
    HAPPY("😊", "开心"),
    OK("😐", "一般"),
    SAD("😢", "难过"),
    ANXIOUS("😰", "焦虑"),
    TIRED("😴", "疲惫"),
    ANGRY("😠", "生气")
}

// ============================================
// 身体状态
// ============================================

/**
 * 用户身体状态
 */
data class BodyState(
    val waterIntake: Int = 0,           // 喝水量（杯）
    val hasBrushedTeeth: Boolean = false, // 是否刷牙
    val hasShowered: Boolean = false,     // 是否洗澡
    val yawnCount: Int = 0,              // 打哈欠次数
    val eyeRubCount: Int = 0,           // 揉眼睛次数
    val hairLossCount: Int = 0          // 掉头发次数
)

// ============================================
// 环境状态
// ============================================

/**
 * 天气状态
 */
enum class WeatherState(val emoji: String, val description: String) {
    SUNNY("☀️", "晴天"),
    CLOUDY("☁️", "阴天"),
    RAINY("🌧️", "下雨"),
    WINDY("💨", "刮风"),
    SNOWY("❄️", "下雪"),
    UNKNOWN("🌤️", "未知")
}

/**
 * 时间状态
 */
enum class TimeState(val emoji: String, val description: String) {
    MORNING("🌅", "早上"),
    NOON("☀️", "中午"),
    AFTERNOON("🌤️", "下午"),
    EVENING("🌆", "傍晚"),
    NIGHT("🌙", "晚上"),
    MIDNIGHT("🌑", "深夜")
}

/**
 * 环境状态
 */
data class EnvironmentState(
    val weather: WeatherState = WeatherState.UNKNOWN,
    val time: TimeState = TimeState.MORNING,
    val isTrashFull: Boolean = false,
    val isDeskDusty: Boolean = false,
    val isClothesPiled: Boolean = false
)

// ============================================
// 穿戴状态
// ============================================

/**
 * 穿戴物品状态
 */
data class WearableState(
    val currentClothing: String? = null,
    val currentShoes: String? = null,
    val currentAccessory: String? = null,
    val wornItems: List<String> = emptyList()
)

// ============================================
// 导航式提示
// ============================================

/**
 * 导航式提示（先体验后告知）
 */
data class NavigationTip(
    val id: String,
    val character: TownCharacter,
    val title: String,           // 如："检测到皮肤轻微发痒"
    val content: String,         // 如："建议换成纯棉材质，会舒服很多哦"
    val actionText: String = "知道了",
    val isWarning: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

// ============================================
// 用户状态（唯一主体）
// ============================================

/**
 * 用户状态（唯一主体）
 */
data class UserState(
    val mood: MoodState = MoodState.OK,
    val body: BodyState = BodyState(),
    val environment: EnvironmentState = EnvironmentState(),
    val wearable: WearableState = WearableState(),
    val lastEventTime: Long = 0,
    val appOpenCount: Int = 0,
    val idleTime: Long = 0  // 闲置时间（毫秒）
)
