package com.example.townapp.data.companion

import androidx.compose.ui.graphics.Color

/**
 * 小镇地图建筑彩蛋系统
 * 
 * 核心逻辑：把成语批判藏在地图的建筑里
 * 用户主动点建筑才会触发，不点就没有，完全自由
 * 我们只是给建筑加了一个新解读，不是把它删掉
 */

/**
 * 建筑类型
 */
enum class BuildingType(val emoji: String, val displayName: String, val color: Color) {
    SCHOOL("🏫", "私塾", Color(0xFFE8F5E9)),
    ANCESTRAL_HALL("🏛️", "祠堂", Color(0xFFF5E6D3)),
    TEMPLE("⛩️", "寺庙", Color(0xFFE4FFE8)),
    BIG_HOUSE("🏠", "大户人家", Color(0xFFFFE4EC)),
    MARKET("🏪", "集市", Color(0xFFF0E4FF)),
    WELL("🪣", "水井", Color(0xFFE4F0FF)),
    TREE("🌳", "大树", Color(0xFFE4FFE8)),
    BRIDGE("🌉", "小桥", Color(0xFFF5F8FF))
}

/**
 * 建筑彩蛋数据
 */
data class BuildingEasterEgg(
    val buildingType: BuildingType,
    val companionIndex: Int, // 0=塔菲喵, 1=doro, 2=咕咕嘎嘎
    val oldMeaning: String, // 传统解读
    val newMeaning: String, // 小镇新解读
    val companionMessage: String // 小家伙说的话
)

/**
 * 建筑彩蛋数据库
 */
object BuildingEasterEggDatabase {
    
    val allEasterEggs = listOf(
        // 私塾 - doro
        BuildingEasterEgg(
            buildingType = BuildingType.SCHOOL,
            companionIndex = 1,
            oldMeaning = "书山有路勤为径，学海无涯苦作舟",
            newMeaning = "学习可以是开心的，不用苦作舟",
            companionMessage = "原来这里的书，不是'书山有路勤为径'，而是'学习可以是开心的，不用苦作舟'🥺"
        ),
        
        // 祠堂 - doro
        BuildingEasterEgg(
            buildingType = BuildingType.ANCESTRAL_HALL,
            companionIndex = 1,
            oldMeaning = "光宗耀祖",
            newMeaning = "你自己开心，就是对祖宗最好的交代",
            companionMessage = "这里原来的规矩是'光宗耀祖'，但在我们小镇，你自己开心，就是对祖宗最好的交代🥺"
        ),
        
        // 寺庙 - 塔菲喵
        BuildingEasterEgg(
            buildingType = BuildingType.TEMPLE,
            companionIndex = 0,
            oldMeaning = "出人头地",
            newMeaning = "做普通人也很好",
            companionMessage = "原来大家来这里求'出人头地'，但在我们小镇，做普通人也很好呀喵😺"
        ),
        
        // 大户人家 - 咕咕嘎嘎
        BuildingEasterEgg(
            buildingType = BuildingType.BIG_HOUSE,
            companionIndex = 2,
            oldMeaning = "成王败寇",
            newMeaning = "赢了输了，都一样被爱",
            companionMessage = "原来这里的人，都在比'成王败寇'，但在我们小镇，赢了输了，都一样被爱咕咕！"
        ),
        
        // 集市 - 塔菲喵
        BuildingEasterEgg(
            buildingType = BuildingType.MARKET,
            companionIndex = 0,
            oldMeaning = "一分钱一分货",
            newMeaning = "贵的不一定好，便宜的不一定差",
            companionMessage = "原来这里的人都说'一分钱一分货'，但在我们小镇，贵的不一定好，开心最重要喵😺"
        ),
        
        // 水井 - doro
        BuildingEasterEgg(
            buildingType = BuildingType.WELL,
            companionIndex = 1,
            oldMeaning = "饮水思源",
            newMeaning = "你可以忘记，也可以记得，都没关系",
            companionMessage = "原来这里的人都说'饮水思源'，但在我们小镇，你可以忘记，也可以记得，都没关系🥺"
        ),
        
        // 大树 - 咕咕嘎嘎
        BuildingEasterEgg(
            buildingType = BuildingType.TREE,
            companionIndex = 2,
            oldMeaning = "十年树木",
            newMeaning = "你可以慢慢长，也可以不长",
            companionMessage = "原来这里的人都说'十年树木'，但在我们小镇，你可以慢慢长，也可以不长咕咕！"
        ),
        
        // 小桥 - 塔菲喵
        BuildingEasterEgg(
            buildingType = BuildingType.BRIDGE,
            companionIndex = 0,
            oldMeaning = "过河拆桥",
            newMeaning = "你可以拆桥，也可以不拆，都没关系",
            companionMessage = "原来这里的人都说'过河拆桥'是坏事，但在我们小镇，你可以拆桥，也可以不拆，都没关系喵😺"
        )
    )
    
    /**
     * 根据建筑类型获取彩蛋
     */
    fun getEasterEgg(buildingType: BuildingType): BuildingEasterEgg? {
        return allEasterEggs.find { it.buildingType == buildingType }
    }
    
    /**
     * 随机获取一个彩蛋（用于测试）
     */
    fun getRandomEasterEgg(): BuildingEasterEgg {
        return allEasterEggs.random()
    }
}