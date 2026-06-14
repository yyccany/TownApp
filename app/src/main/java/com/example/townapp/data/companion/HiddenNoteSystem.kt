package com.example.townapp.data.companion

/**
 * 隐藏小纸条系统
 * 
 * 核心逻辑：像星露谷里的秘密纸条一样
 * 用户在小镇里逛的时候，随机捡到小纸条
 * 上面是我们的成语解读，完全是惊喜，不是强制
 * 
 * 重点：随机的，概率很低，不是天天给
 * 用户捡到了就是惊喜，没捡到就算了，完全是彩蛋
 */

/**
 * 小纸条数据
 */
data class HiddenNote(
    val id: String,
    val content: String, // 纸条内容
    val idiomReference: String? = null // 对应的成语（可选）
)

/**
 * 小纸条数据库
 */
object HiddenNoteDatabase {
    
    val allNotes = listOf(
        // 关于"忍"
        HiddenNote(
            id = "note_001",
            content = "忍一时越想越气，退一步越想越亏。\n你不用忍，你可以发脾气。",
            idiomReference = "忍一时风平浪静"
        ),
        
        // 关于"吃亏"
        HiddenNote(
            id = "note_002",
            content = "吃亏不是福，是别人在欺负你。\n保护好自己，才是最重要的。",
            idiomReference = "吃亏是福"
        ),
        
        // 关于"时间"
        HiddenNote(
            id = "note_003",
            content = "时间是你的，你可以浪费它。\n发呆、躺平、什么都不做，都没关系。",
            idiomReference = "一寸光阴一寸金"
        ),
        
        // 关于"努力"
        HiddenNote(
            id = "note_004",
            content = "努力不一定成功，不努力也不一定失败。\n你不用必须努力，你按自己的节奏来就好。",
            idiomReference = "天道酬勤"
        ),
        
        // 关于"让梨"
        HiddenNote(
            id = "note_005",
            content = "你不用让。你喜欢的梨，你就自己吃。\n孔融让梨是他的选择，不是你的义务。",
            idiomReference = "孔融让梨"
        ),
        
        // 关于"先飞"
        HiddenNote(
            id = "note_006",
            content = "你不用先飞。你按自己的节奏来就好。\n笨鸟可以先飞，也可以不飞，都可以。",
            idiomReference = "笨鸟先飞"
        ),
        
        // 关于"牺牲"
        HiddenNote(
            id = "note_007",
            content = "你不用牺牲自己。你自己才是最重要的。\n帮助别人很好，但不是必须。",
            idiomReference = "舍己为人"
        ),
        
        // 关于"吃苦"
        HiddenNote(
            id = "note_008",
            content = "吃苦不一定是好事。\n你可以选择不吃苦，也可以选择吃苦，都没关系。",
            idiomReference = "吃得苦中苦"
        ),
        
        // 关于"面子"
        HiddenNote(
            id = "note_009",
            content = "面子不重要，舒服才重要。\n你不用为了面子，委屈自己。",
            idiomReference = "人靠衣装"
        ),
        
        // 关于"孝顺"
        HiddenNote(
            id = "note_010",
            content = "孝顺不是听话。\n你可以爱父母，也可以有自己的想法。\n两者不冲突。",
            idiomReference = "百善孝为先"
        ),
        
        // 关于"善良"
        HiddenNote(
            id = "note_011",
            content = "善良不是软弱。\n你可以善良，也可以不善良。\n都可以，都值得被爱。",
            idiomReference = "善良"
        ),
        
        // 关于"成功"
        HiddenNote(
            id = "note_012",
            content = "成功不是唯一的目标。\n你可以追求成功，也可以不追求。\n都很好。",
            idiomReference = "成王败寇"
        ),
        
        // 关于"学习"
        HiddenNote(
            id = "note_013",
            content = "学习可以是开心的，不用苦作舟。\n你可以学，也可以不学。\n都很好。",
            idiomReference = "书山有路勤为径"
        ),
        
        // 关于"工作"
        HiddenNote(
            id = "note_014",
            content = "工作不是生活的全部。\n你可以工作，也可以休息。\n都很好。",
            idiomReference = "勤能补拙"
        ),
        
        // 关于"感恩"
        HiddenNote(
            id = "note_015",
            content = "感恩不是义务。\n你可以感恩，也可以不感恩。\n都没关系。",
            idiomReference = "饮水思源"
        ),
        
        // 关于"比较"
        HiddenNote(
            id = "note_016",
            content = "你不用和别人比较。\n你就是你，独一无二的你。\n这就足够了。",
            idiomReference = "比"
        ),
        
        // 关于"完美"
        HiddenNote(
            id = "note_017",
            content = "完美不是必须的。\n你可以不完美，也可以完美。\n都很好。",
            idiomReference = "完美"
        ),
        
        // 关于"责任"
        HiddenNote(
            id = "note_018",
            content = "责任不是枷锁。\n你可以承担责任，也可以不承担。\n都没关系。",
            idiomReference = "责任"
        ),
        
        // 关于"未来"
        HiddenNote(
            id = "note_019",
            content = "未来不是必须规划的。\n你可以规划，也可以不规划。\n都很好。",
            idiomReference = "未雨绸缪"
        ),
        
        // 关于"过去"
        HiddenNote(
            id = "note_020",
            content = "过去不是必须记住的。\n你可以记住，也可以忘记。\n都没关系。",
            idiomReference = "铭记"
        )
    )
    
    /**
     * 随机获取一张小纸条
     * 概率很低，不是每次都给
     */
    fun getRandomNote(): HiddenNote? {
        // 10% 的概率捡到纸条
        if ((0..9).random() == 0) {
            return allNotes.random()
        }
        return null
    }
    
    /**
     * 根据ID获取小纸条
     */
    fun getNoteById(id: String): HiddenNote? {
        return allNotes.find { it.id == id }
    }
    
    /**
     * 根据成语获取小纸条
     */
    fun getNoteByIdiom(idiom: String): HiddenNote? {
        return allNotes.find { it.idiomReference == idiom }
    }
}