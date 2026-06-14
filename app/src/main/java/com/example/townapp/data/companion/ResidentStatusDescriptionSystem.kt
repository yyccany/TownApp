package com.example.townapp.data.companion

/**
 * 居民状态描述系统 - 以人为本，不评判
 * 
 * 核心逻辑：不管居民做什么，我们的状态描述永远是"以人为本"的
 * 永远不评判他，只描述他的状态，然后说"这就很好"
 * 
 * 重点：我们永远不评判用户的选择
 * 不管你选了传统的规训，还是选了我们的反规训
 * 我们都只说"你开心就好"
 */

/**
 * 居民行为类型
 */
enum class ResidentBehaviorType(val displayName: String) {
    LIE_FLAT("躺平"),
    EAT_JUNK_FOOD("吃垃圾食品"),
    WORK_HARD("努力工作"),
    GIVE_ALL("把好东西都给了别人"),
    REST("休息"),
    LEARN("学习"),
    EXERCISE("运动"),
    SOCIAL("社交"),
    DO_NOTHING("什么都不做")
}

/**
 * 状态描述数据
 */
data class ResidentStatusDescription(
    val behaviorType: ResidentBehaviorType,
    val positiveDescription: String, // 正向描述（以人为本）
    val avoidDescription: String // 避免的评判性描述
)

/**
 * 居民状态描述数据库
 */
object ResidentStatusDescriptionDatabase {
    
    val allDescriptions = listOf(
        // 躺平
        ResidentStatusDescription(
            behaviorType = ResidentBehaviorType.LIE_FLAT,
            positiveDescription = "他今天很开心，没有烦恼，这就足够了。",
            avoidDescription = "他很懒，不健康，要减肥"
        ),
        
        // 吃垃圾食品
        ResidentStatusDescription(
            behaviorType = ResidentBehaviorType.EAT_JUNK_FOOD,
            positiveDescription = "他今天吃得很开心，满足了自己的口味，这就很好。",
            avoidDescription = "他吃得不健康，要控制饮食"
        ),
        
        // 努力工作
        ResidentStatusDescription(
            behaviorType = ResidentBehaviorType.WORK_HARD,
            positiveDescription = "他今天很充实，他在做他喜欢的事，这就很好。",
            avoidDescription = "他太拼了，要休息"
        ),
        
        // 把好东西都给了别人
        ResidentStatusDescription(
            behaviorType = ResidentBehaviorType.GIVE_ALL,
            positiveDescription = "他今天很满足，他在做他想做的事，这就很棒。",
            avoidDescription = "他太傻了，被人欺负了"
        ),
        
        // 休息
        ResidentStatusDescription(
            behaviorType = ResidentBehaviorType.REST,
            positiveDescription = "他今天休息了，身体得到了放松，这就很好。",
            avoidDescription = "他太懒了，要动起来"
        ),
        
        // 学习
        ResidentStatusDescription(
            behaviorType = ResidentBehaviorType.LEARN,
            positiveDescription = "他今天学到了新东西，他在成长，这就很好。",
            avoidDescription = "他学得不够多，要更努力"
        ),
        
        // 运动
        ResidentStatusDescription(
            behaviorType = ResidentBehaviorType.EXERCISE,
            positiveDescription = "他今天运动了，身体很健康，这就很好。",
            avoidDescription = "他运动得不够，要更多"
        ),
        
        // 社交
        ResidentStatusDescription(
            behaviorType = ResidentBehaviorType.SOCIAL,
            positiveDescription = "他今天和朋友在一起，很开心，这就很好。",
            avoidDescription = "他社交得不够，要更外向"
        ),
        
        // 什么都不做
        ResidentStatusDescription(
            behaviorType = ResidentBehaviorType.DO_NOTHING,
            positiveDescription = "他今天什么都不做，发呆、躺平，都没关系，这就很好。",
            avoidDescription = "他浪费时间，要做事"
        )
    )
    
    /**
     * 根据行为类型获取状态描述
     */
    fun getDescription(behaviorType: ResidentBehaviorType): ResidentStatusDescription? {
        return allDescriptions.find { it.behaviorType == behaviorType }
    }
    
    /**
     * 获取正向描述（用于显示）
     */
    fun getPositiveDescription(behaviorType: ResidentBehaviorType): String {
        return getDescription(behaviorType)?.positiveDescription ?: "他今天很开心，这就足够了。"
    }
    
    /**
     * 随机获取一个正向描述
     */
    fun getRandomPositiveDescription(): String {
        return allDescriptions.random().positiveDescription
    }
}