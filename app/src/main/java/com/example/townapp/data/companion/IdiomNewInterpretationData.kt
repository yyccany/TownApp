package com.example.townapp.data.companion

import androidx.compose.ui.graphics.Color

/**
 * 成语新解数据
 * 
 * 核心逻辑：把成语做成可探索的卡片
 * 用户主动点认知觉醒页面，主动点这个板块，主动点卡片，才会看到
 * 完全是用户自己要探索，我们不推给他
 */

/**
 * 成语新解数据
 */
data class IdiomNewInterpretation(
    val id: String,
    val emoji: String, // 代表emoji
    val idiom: String, // 成语名称
    val oldMeaning: String, // 传统解读
    val newMeaning: String, // 小镇新解读
    val color: Color // 卡片颜色
)

/**
 * 成语新解数据库
 */
object IdiomNewInterpretationDatabase {
    
    private val _allInterpretations = listOf(
        // 孔融让梨
        IdiomNewInterpretation(
            id = "idiom_001",
            emoji = "🥕",
            idiom = "孔融让梨",
            oldMeaning = "要谦让，要把好的给别人",
            newMeaning = "你不用让。你喜欢的梨，你就自己吃。",
            color = Color(0xFFFFE4EC)
        ),
        
        // 笨鸟先飞
        IdiomNewInterpretation(
            id = "idiom_002",
            emoji = "🐦",
            idiom = "笨鸟先飞",
            oldMeaning = "要努力，要比别人更拼",
            newMeaning = "你不用先飞。你按自己的节奏来就好。",
            color = Color(0xFFE4F0FF)
        ),
        
        // 吃亏是福
        IdiomNewInterpretation(
            id = "idiom_003",
            emoji = "🍚",
            idiom = "吃亏是福",
            oldMeaning = "吃亏是好事，要学会忍",
            newMeaning = "吃亏不是福，是别人在欺负你。",
            color = Color(0xFFE4FFE8)
        ),
        
        // 一寸光阴一寸金
        IdiomNewInterpretation(
            id = "idiom_004",
            emoji = "⏰",
            idiom = "一寸光阴一寸金",
            oldMeaning = "时间很宝贵，不能浪费",
            newMeaning = "时间是你的，你可以浪费它。",
            color = Color(0xFFF0E4FF)
        ),
        
        // 天道酬勤
        IdiomNewInterpretation(
            id = "idiom_005",
            emoji = "💪",
            idiom = "天道酬勤",
            oldMeaning = "努力就会成功",
            newMeaning = "努力不一定成功，不努力也不一定失败。",
            color = Color(0xFFFFF0E4)
        ),
        
        // 舍己为人
        IdiomNewInterpretation(
            id = "idiom_006",
            emoji = "💛",
            idiom = "舍己为人",
            oldMeaning = "要牺牲自己，帮助别人",
            newMeaning = "你不用牺牲自己。你自己才是最重要的。",
            color = Color(0xFFFFE4EC)
        ),
        
        // 吃得苦中苦
        IdiomNewInterpretation(
            id = "idiom_007",
            emoji = "🥬",
            idiom = "吃得苦中苦",
            oldMeaning = "要吃苦，才能成功",
            newMeaning = "吃苦不一定是好事。你可以选择不吃苦。",
            color = Color(0xFFE4F0FF)
        ),
        
        // 人靠衣装
        IdiomNewInterpretation(
            id = "idiom_008",
            emoji = "👕",
            idiom = "人靠衣装",
            oldMeaning = "要穿好看的衣服，才有面子",
            newMeaning = "面子不重要，舒服才重要。",
            color = Color(0xFFE4FFE8)
        ),
        
        // 百善孝为先
        IdiomNewInterpretation(
            id = "idiom_009",
            emoji = "👨‍👩‍👧",
            idiom = "百善孝为先",
            oldMeaning = "要听话，要孝顺父母",
            newMeaning = "孝顺不是听话。你可以爱父母，也可以有自己的想法。",
            color = Color(0xFFF0E4FF)
        ),
        
        // 忍一时风平浪静
        IdiomNewInterpretation(
            id = "idiom_010",
            emoji = "🌊",
            idiom = "忍一时风平浪静",
            oldMeaning = "要忍耐，不要发脾气",
            newMeaning = "忍一时越想越气。你不用忍，你可以发脾气。",
            color = Color(0xFFFFF0E4)
        ),
        
        // 书山有路勤为径
        IdiomNewInterpretation(
            id = "idiom_011",
            emoji = "📚",
            idiom = "书山有路勤为径",
            oldMeaning = "学习要刻苦，要努力",
            newMeaning = "学习可以是开心的，不用苦作舟。",
            color = Color(0xFFFFE4EC)
        ),
        
        // 勤能补拙
        IdiomNewInterpretation(
            id = "idiom_012",
            emoji = "🔧",
            idiom = "勤能补拙",
            oldMeaning = "要努力，弥补不足",
            newMeaning = "你已经很棒了，不用证明什么。",
            color = Color(0xFFE4F0FF)
        ),
        
        // 成王败寇
        IdiomNewInterpretation(
            id = "idiom_013",
            emoji = "👑",
            idiom = "成王败寇",
            oldMeaning = "赢了才是成功，输了就是失败",
            newMeaning = "赢了输了，都一样被爱。",
            color = Color(0xFFE4FFE8)
        ),
        
        // 光宗耀祖
        IdiomNewInterpretation(
            id = "idiom_014",
            emoji = "🏛️",
            idiom = "光宗耀祖",
            oldMeaning = "要成功，让家族骄傲",
            newMeaning = "你自己开心，就是对祖宗最好的交代。",
            color = Color(0xFFF0E4FF)
        ),
        
        // 出人头地
        IdiomNewInterpretation(
            id = "idiom_015",
            emoji = "⭐",
            idiom = "出人头地",
            oldMeaning = "要成功，要比别人强",
            newMeaning = "做普通人也很好。",
            color = Color(0xFFFFF0E4)
        ),
        
        // 一分钱一分货
        IdiomNewInterpretation(
            id = "idiom_016",
            emoji = "💰",
            idiom = "一分钱一分货",
            oldMeaning = "贵的就是好的",
            newMeaning = "贵的不一定好，便宜的不一定差。",
            color = Color(0xFFFFE4EC)
        ),
        
        // 饮水思源
        IdiomNewInterpretation(
            id = "idiom_017",
            emoji = "🪣",
            idiom = "饮水思源",
            oldMeaning = "要感恩，要记住别人的帮助",
            newMeaning = "你可以忘记，也可以记得，都没关系。",
            color = Color(0xFFE4F0FF)
        ),
        
        // 十年树木
        IdiomNewInterpretation(
            id = "idiom_018",
            emoji = "🌳",
            idiom = "十年树木",
            oldMeaning = "要长期规划，要耐心",
            newMeaning = "你可以慢慢长，也可以不长。",
            color = Color(0xFFE4FFE8)
        ),
        
        // 未雨绸缪
        IdiomNewInterpretation(
            id = "idiom_019",
            emoji = "🌧️",
            idiom = "未雨绸缪",
            oldMeaning = "要提前准备，要规划未来",
            newMeaning = "未来不是必须规划的。你可以规划，也可以不规划。",
            color = Color(0xFFF0E4FF)
        ),
        
        // 过河拆桥
        IdiomNewInterpretation(
            id = "idiom_020",
            emoji = "🌉",
            idiom = "过河拆桥",
            oldMeaning = "不能忘记帮助过你的人",
            newMeaning = "你可以拆桥，也可以不拆，都没关系。",
            color = Color(0xFFFFF0E4)
        )
    )
    
    /**
     * 获取所有成语新解
     */
    fun getAllInterpretations(): List<IdiomNewInterpretation> {
        return _allInterpretations
    }
    
    /**
     * 根据ID获取成语新解
     */
    fun getInterpretationById(id: String): IdiomNewInterpretation? {
        return _allInterpretations.find { it.id == id }
    }
    
    /**
     * 根据成语名称获取新解
     */
    fun getInterpretationByIdiom(idiom: String): IdiomNewInterpretation? {
        return _allInterpretations.find { it.idiom == idiom }
    }
}