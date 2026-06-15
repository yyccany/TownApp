package com.example.townapp.data.idiom

/**
 * 小镇碎碎念 - 治愈系信息流数据
 * 真实生活场景 + 一句话戳心字幕
 */

enum class WhisperType(val emoji: String) {
    LIFE("🌿"),      // 生活感悟
    IDIOM("📖"),      // 成语新解
    WORLDVIEW("🌍"), // 世界观
    SELF("💛"),      // 自我关怀
    LOVE("💕"),      // 爱与关系
    TIME("⏳"),      // 时间感悟
    SIMPLE("🍃"),    // 简单生活
    COURAGE("🔥"),   // 勇气与成长
    LIVING("🍹")     // 吃喝玩乐与生活小事
}

data class TownWhisper(
    val id: String,
    val type: WhisperType,
    val subtitle: String,    // 场景描述
    val content: String,     // 戳心字幕
    val townPerspective: String = "",  // 小镇视角（可选）
    val tag: String = ""     // 标签（可选）
)

object TownWhisperLibrary {

    fun getAllWhispers(): List<TownWhisper> {
        return lifeWhispers + idiomWhispers + worldviewWhispers + selfWhispers + loveWhispers + timeWhispers + simpleWhispers + courageWhispers + livingWhispers
    }

    fun getWhispersByType(type: WhisperType): List<TownWhisper> {
        return getAllWhispers().filter { it.type == type }
    }

    // 🌿 生活感悟
    private val lifeWhispers = listOf(
        TownWhisper(
            id = "life_1",
            type = WhisperType.LIFE,
            subtitle = "清晨的厨房",
            content = "给自己煮一碗热汤，比什么都重要",
            tag = "早餐"
        ),
        TownWhisper(
            id = "life_2",
            type = WhisperType.LIFE,
            subtitle = "深夜加班回家",
            content = "灯亮着，心就暖着",
            tag = "回家"
        ),
        TownWhisper(
            id = "life_3",
            type = WhisperType.LIFE,
            subtitle = "下雨天的窗边",
            content = "允许自己什么都不做",
            tag = "独处"
        ),
        TownWhisper(
            id = "life_4",
            type = WhisperType.LIFE,
            subtitle = "周末的阳台",
            content = "晒太阳不是浪费时间，是充电",
            tag = "休息"
        ),
        TownWhisper(
            id = "life_5",
            type = WhisperType.LIFE,
            subtitle = "拥挤的地铁",
            content = "每个人都有自己的目的地，不用急",
            tag = "通勤"
        ),
        TownWhisper(
            id = "life_6",
            type = WhisperType.LIFE,
            subtitle = "超市的货架",
            content = "买自己喜欢的，不是别人觉得好的",
            tag = "购物"
        ),
        TownWhisper(
            id = "life_7",
            type = WhisperType.LIFE,
            subtitle = "春天的公园",
            content = "花开了，你的心情也该开了",
            tag = "季节"
        ),
        TownWhisper(
            id = "life_8",
            type = WhisperType.LIFE,
            subtitle = "冬天的被窝",
            content = "赖床是对冬天最基本的尊重",
            tag = "冬天"
        ),
        TownWhisper(
            id = "life_9",
            type = WhisperType.LIFE,
            subtitle = "傍晚的厨房",
            content = "烟火气，是最好的治愈",
            tag = "做饭"
        ),
        TownWhisper(
            id = "life_10",
            type = WhisperType.LIFE,
            subtitle = "午后的咖啡馆",
            content = "发呆是思考的另一种形式",
            tag = "独处"
        ),
        TownWhisper(
            id = "life_11",
            type = WhisperType.LIFE,
            subtitle = "整理房间时",
            content = "断舍离的不是物品，是执念",
            tag = "整理"
        ),
        TownWhisper(
            id = "life_12",
            type = WhisperType.LIFE,
            subtitle = "等公交车时",
            content = "慢一点，风景会更好",
            tag = "等待"
        ),
        TownWhisper(
            id = "life_13",
            type = WhisperType.LIFE,
            subtitle = "深夜的书房",
            content = "读书不是为了有用，是为了遇见自己",
            tag = "阅读"
        ),
        TownWhisper(
            id = "life_14",
            type = WhisperType.LIFE,
            subtitle = "清晨的公园",
            content = "跑步不是为了减肥，是为了感受风",
            tag = "运动"
        ),
        TownWhisper(
            id = "life_15",
            type = WhisperType.LIFE,
            subtitle = "整理衣柜时",
            content = "穿让自己舒服的，不是让别人好看的",
            tag = "自我"
        )
    )

    // 📖 成语新解
    private val idiomWhispers = listOf(
        TownWhisper(
            id = "idiom_1",
            type = WhisperType.IDIOM,
            subtitle = "关于「任劳任怨」",
            content = "你可以任劳，但绝不任怨",
            townPerspective = "该要的钱一分都不能少，该甩的锅一个都不能背"
        ),
        TownWhisper(
            id = "idiom_2",
            type = WhisperType.IDIOM,
            subtitle = "关于「大公无私」",
            content = "自私是人的天性，不是罪恶",
            townPerspective = "你爱自己，为自己考虑，天经地义"
        ),
        TownWhisper(
            id = "idiom_3",
            type = WhisperType.IDIOM,
            subtitle = "关于「舍己为人」",
            content = "连自己都不爱，怎么爱别人",
            townPerspective = "先把自己照顾好，再谈帮助别人"
        ),
        TownWhisper(
            id = "idiom_4",
            type = WhisperType.IDIOM,
            subtitle = "关于「持之以恒」",
            content = "不喜欢就放弃，做不到就停下来",
            townPerspective = "坚持没有意义的事情，才是浪费生命"
        ),
        TownWhisper(
            id = "idiom_5",
            type = WhisperType.IDIOM,
            subtitle = "关于「天道酬勤」",
            content = "努力不一定会成功，但你已经很棒了",
            townPerspective = "就算不成功，也没关系"
        ),
        TownWhisper(
            id = "idiom_6",
            type = WhisperType.IDIOM,
            subtitle = "关于「谦虚谨慎」",
            content = "你的优秀值得被所有人看见",
            townPerspective = "可以骄傲，可以炫耀，可以说自己很棒"
        ),
        TownWhisper(
            id = "idiom_7",
            type = WhisperType.IDIOM,
            subtitle = "关于「乐于助人」",
            content = "帮人是情分，不帮是本分",
            townPerspective = "你的时间和精力比别人的请求重要一万倍"
        ),
        TownWhisper(
            id = "idiom_8",
            type = WhisperType.IDIOM,
            subtitle = "关于「忍辱负重」",
            content = "那些辱，你不用背；那些重，你可以扔",
            townPerspective = "不用为了所谓的未来，牺牲现在的快乐"
        ),
        TownWhisper(
            id = "idiom_9",
            type = WhisperType.IDIOM,
            subtitle = "关于「笨鸟先飞」",
            content = "不用跟别人比，按你自己的节奏来",
            townPerspective = "就算晚飞，就算飞不动，也没关系"
        ),
        TownWhisper(
            id = "idiom_10",
            type = WhisperType.IDIOM,
            subtitle = "关于「破釜沉舟」",
            content = "永远给自己留一条退路",
            townPerspective = "你的生命比任何成功都重要"
        )
    )

    // 🌍 世界观 - 小镇的信念
    private val worldviewWhispers = listOf(
        TownWhisper(
            id = "worldview_1",
            type = WhisperType.WORLDVIEW,
            subtitle = "小镇宗旨",
            content = "以人为本，自由、平等、公正",
            tag = "宗旨"
        ),
        TownWhisper(
            id = "worldview_2",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于评判",
            content = "任何人生选择，不分好坏优劣",
            tag = "包容"
        ),
        TownWhisper(
            id = "worldview_3",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于答案",
            content = "没有正确答案，没有最优解",
            tag = "多元"
        ),
        TownWhisper(
            id = "worldview_4",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于存在",
            content = "你存在本身就值得",
            tag = "自我价值"
        ),
        TownWhisper(
            id = "worldview_5",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于底气",
            content = "你这样就很好",
            tag = "接纳"
        ),
        TownWhisper(
            id = "worldview_6",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于被看见",
            content = "你不用优秀、不用正确，就足够被看见",
            tag = "看见"
        ),
        TownWhisper(
            id = "worldview_7",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于小镇的承诺",
            content = "不管你是谁、带着什么来、活成什么样子，我认得你",
            tag = "承诺"
        ),
        TownWhisper(
            id = "worldview_8",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于陪伴",
            content = "你想来就来，想走就走，小镇永远在原地",
            tag = "陪伴"
        ),
        TownWhisper(
            id = "worldview_9",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于比较",
            content = "你不用比任何人好，你是你自己就够了",
            tag = "自我"
        ),
        TownWhisper(
            id = "worldview_10",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于证明",
            content = "你选什么路、过得好不好，都不用向谁证明",
            tag = "自由"
        ),
        TownWhisper(
            id = "worldview_11",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于选择",
            content = "不同的选择，对应不同的状态与代价",
            tag = "选择"
        ),
        TownWhisper(
            id = "worldview_12",
            type = WhisperType.WORLDVIEW,
            subtitle = "关于人生",
            content = "不教你怎么活，只陈述客观事实",
            tag = "尊重"
        )
    )

    // 💛 自我关怀
    private val selfWhispers = listOf(
        TownWhisper(
            id = "self_1",
            type = WhisperType.SELF,
            subtitle = "照镜子时",
            content = "你很美，真的",
            tag = "自信"
        ),
        TownWhisper(
            id = "self_2",
            type = WhisperType.SELF,
            subtitle = "犯错误时",
            content = "没关系，你已经尽力了",
            tag = "宽容"
        ),
        TownWhisper(
            id = "self_3",
            type = WhisperType.SELF,
            subtitle = "感到疲惫时",
            content = "累了就休息，不是偷懒",
            tag = "休息"
        ),
        TownWhisper(
            id = "self_4",
            type = WhisperType.SELF,
            subtitle = "面对批评时",
            content = "别人的看法，只是他们的看法",
            tag = "自我"
        ),
        TownWhisper(
            id = "self_5",
            type = WhisperType.SELF,
            subtitle = "感到焦虑时",
            content = "慢慢来，时间可以等你",
            tag = "焦虑"
        ),
        TownWhisper(
            id = "self_6",
            type = WhisperType.SELF,
            subtitle = "对自己失望时",
            content = "你已经做得很好了",
            tag = "鼓励"
        ),
        TownWhisper(
            id = "self_7",
            type = WhisperType.SELF,
            subtitle = "想放弃时",
            content = "放弃也需要勇气",
            tag = "勇气"
        ),
        TownWhisper(
            id = "self_8",
            type = WhisperType.SELF,
            subtitle = "感到孤独时",
            content = "独处不是孤独，是和自己相处",
            tag = "独处"
        ),
        TownWhisper(
            id = "self_9",
            type = WhisperType.SELF,
            subtitle = "面对选择时",
            content = "没有对错，只有不同",
            tag = "选择"
        ),
        TownWhisper(
            id = "self_10",
            type = WhisperType.SELF,
            subtitle = "回顾过去时",
            content = "所有经历，都是礼物",
            tag = "成长"
        ),
        TownWhisper(
            id = "self_11",
            type = WhisperType.SELF,
            subtitle = "展望未来时",
            content = "未来还没到来，不用提前焦虑",
            tag = "未来"
        ),
        TownWhisper(
            id = "self_12",
            type = WhisperType.SELF,
            subtitle = "照顾自己时",
            content = "爱自己不是自私，是责任",
            tag = "自爱"
        )
    )

    // 💕 爱与关系
    private val loveWhispers = listOf(
        TownWhisper(
            id = "love_1",
            type = WhisperType.LOVE,
            subtitle = "恋爱中",
            content = "好的关系，让你更像自己",
            tag = "爱情"
        ),
        TownWhisper(
            id = "love_2",
            type = WhisperType.LOVE,
            subtitle = "友情中",
            content = "朋友不是用来比较的",
            tag = "友情"
        ),
        TownWhisper(
            id = "love_3",
            type = WhisperType.LOVE,
            subtitle = "亲情中",
            content = "家人不是你的责任，是你的牵挂",
            tag = "亲情"
        ),
        TownWhisper(
            id = "love_4",
            type = WhisperType.LOVE,
            subtitle = "面对分手时",
            content = "分开不是失败，是选择",
            tag = "分手"
        ),
        TownWhisper(
            id = "love_5",
            type = WhisperType.LOVE,
            subtitle = "单身时",
            content = "一个人也可以很精彩",
            tag = "单身"
        ),
        TownWhisper(
            id = "love_6",
            type = WhisperType.LOVE,
            subtitle = "争吵后",
            content = "道歉不是认输，是珍惜",
            tag = "沟通"
        ),
        TownWhisper(
            id = "love_7",
            type = WhisperType.LOVE,
            subtitle = "表达爱意时",
            content = "说出来，比藏在心里好",
            tag = "表达"
        ),
        TownWhisper(
            id = "love_8",
            type = WhisperType.LOVE,
            subtitle = "面对误解时",
            content = "解释不是必要的，理解才是",
            tag = "理解"
        ),
        TownWhisper(
            id = "love_9",
            type = WhisperType.LOVE,
            subtitle = "帮助别人后",
            content = "帮了你，我很开心",
            tag = "善意"
        ),
        TownWhisper(
            id = "love_10",
            type = WhisperType.LOVE,
            subtitle = "被帮助时",
            content = "接受帮助不是软弱",
            tag = "接受"
        )
    )

    // ⏳ 时间感悟
    private val timeWhispers = listOf(
        TownWhisper(
            id = "time_1",
            type = WhisperType.TIME,
            subtitle = "清晨醒来",
            content = "新的一天，不用急着开始",
            tag = "早晨"
        ),
        TownWhisper(
            id = "time_2",
            type = WhisperType.TIME,
            subtitle = "深夜时分",
            content = "累了就睡，不用强迫自己",
            tag = "深夜"
        ),
        TownWhisper(
            id = "time_3",
            type = WhisperType.TIME,
            subtitle = "周末时光",
            content = "周末不是用来完成任务的",
            tag = "周末"
        ),
        TownWhisper(
            id = "time_4",
            type = WhisperType.TIME,
            subtitle = "等待中",
            content = "等待也是生活的一部分",
            tag = "等待"
        ),
        TownWhisper(
            id = "time_5",
            type = WhisperType.TIME,
            subtitle = "回顾过去",
            content = "过去的已经过去，未来还没到来",
            tag = "回忆"
        ),
        TownWhisper(
            id = "time_6",
            type = WhisperType.TIME,
            subtitle = "面对年龄",
            content = "年龄只是数字，不是限制",
            tag = "年龄"
        ),
        TownWhisper(
            id = "time_7",
            type = WhisperType.TIME,
            subtitle = "感到浪费时间时",
            content = "开心的时间，没有浪费",
            tag = "时间观"
        ),
        TownWhisper(
            id = "time_8",
            type = WhisperType.TIME,
            subtitle = "赶deadline时",
            content = "完不成也没关系，天不会塌",
            tag = "压力"
        ),
        TownWhisper(
            id = "time_9",
            type = WhisperType.TIME,
            subtitle = "晒太阳时",
            content = "时光很慢，你可以慢慢来",
            tag = "慢生活"
        ),
        TownWhisper(
            id = "time_10",
            type = WhisperType.TIME,
            subtitle = "看夕阳时",
            content = "今天结束了，明天还会来",
            tag = "结束"
        )
    )

    // 🍃 简单生活
    private val simpleWhispers = listOf(
        TownWhisper(
            id = "simple_1",
            type = WhisperType.SIMPLE,
            subtitle = "喝一杯茶",
            content = "慢一点，再慢一点",
            tag = "慢生活"
        ),
        TownWhisper(
            id = "simple_2",
            type = WhisperType.SIMPLE,
            subtitle = "吃一碗饭",
            content = "好好吃饭，就是好好生活",
            tag = "吃饭"
        ),
        TownWhisper(
            id = "simple_3",
            type = WhisperType.SIMPLE,
            subtitle = "走一段路",
            content = "路上的风景，比目的地更重要",
            tag = "走路"
        ),
        TownWhisper(
            id = "simple_4",
            type = WhisperType.SIMPLE,
            subtitle = "听一首歌",
            content = "让音乐带你回到自己",
            tag = "音乐"
        ),
        TownWhisper(
            id = "simple_5",
            type = WhisperType.SIMPLE,
            subtitle = "看一朵云",
            content = "天空没有答案，但很治愈",
            tag = "自然"
        ),
        TownWhisper(
            id = "simple_6",
            type = WhisperType.SIMPLE,
            subtitle = "写一段话",
            content = "文字是写给自己看的",
            tag = "写作"
        ),
        TownWhisper(
            id = "simple_7",
            type = WhisperType.SIMPLE,
            subtitle = "洗一件衣服",
            content = "平凡的事，也有意义",
            tag = "日常"
        ),
        TownWhisper(
            id = "simple_8",
            type = WhisperType.SIMPLE,
            subtitle = "看一部电影",
            content = "偶尔逃避现实，也没关系",
            tag = "放松"
        ),
        TownWhisper(
            id = "simple_9",
            type = WhisperType.SIMPLE,
            subtitle = "种一盆花",
            content = "等待花开，也是一种幸福",
            tag = "植物"
        ),
        TownWhisper(
            id = "simple_10",
            type = WhisperType.SIMPLE,
            subtitle = "发一会儿呆",
            content = "发呆不是浪费时间",
            tag = "放空"
        )
    )

    // 🔥 勇气与成长
    private val courageWhispers = listOf(
        TownWhisper(
            id = "courage_1",
            type = WhisperType.COURAGE,
            subtitle = "面对恐惧时",
            content = "害怕也没关系，你可以慢慢来",
            tag = "勇气"
        ),
        TownWhisper(
            id = "courage_2",
            type = WhisperType.COURAGE,
            subtitle = "说\"不\"时",
            content = "拒绝不是伤害，是保护自己",
            tag = "边界"
        ),
        TownWhisper(
            id = "courage_3",
            type = WhisperType.COURAGE,
            subtitle = "做决定时",
            content = "没有完美的选择，只有适合你的选择",
            tag = "选择"
        ),
        TownWhisper(
            id = "courage_4",
            type = WhisperType.COURAGE,
            subtitle = "面对失败时",
            content = "失败不是终点，是新的开始",
            tag = "失败"
        ),
        TownWhisper(
            id = "courage_5",
            type = WhisperType.COURAGE,
            subtitle = "尝试新事物时",
            content = "不用害怕，你比想象中更强大",
            tag = "尝试"
        ),
        TownWhisper(
            id = "courage_6",
            type = WhisperType.COURAGE,
            subtitle = "表达自己时",
            content = "你的声音，值得被听见",
            tag = "表达"
        ),
        TownWhisper(
            id = "courage_7",
            type = WhisperType.COURAGE,
            subtitle = "面对批评时",
            content = "批评你的人，不一定比你更懂",
            tag = "自信"
        ),
        TownWhisper(
            id = "courage_8",
            type = WhisperType.COURAGE,
            subtitle = "改变时",
            content = "改变很难，但值得",
            tag = "改变"
        ),
        TownWhisper(
            id = "courage_9",
            type = WhisperType.COURAGE,
            subtitle = "坚持自己时",
            content = "与众不同不是错",
            tag = "独特"
        ),
        TownWhisper(
            id = "courage_10",
            type = WhisperType.COURAGE,
            subtitle = "爱自己时",
            content = "爱自己需要勇气",
            tag = "自爱"
        ),
        TownWhisper(
            id = "courage_11",
            type = WhisperType.COURAGE,
            subtitle = "原谅自己时",
            content = "原谅自己，是最难也是最重要的事",
            tag = "原谅"
        ),
        TownWhisper(
            id = "courage_12",
            type = WhisperType.COURAGE,
            subtitle = "重新开始时",
            content = "任何时候开始都不晚",
            tag = "开始"
        )
    )

    // 🍹 吃喝玩乐与生活小事 - 小镇真正推崇的生活态度
    private val livingWhispers = listOf(
        TownWhisper(
            id = "living_1",
            type = WhisperType.LIVING,
            subtitle = "吃一碗热乎的面",
            content = "胃暖了，心就安了",
            tag = "美食"
        ),
        TownWhisper(
            id = "living_2",
            type = WhisperType.LIVING,
            subtitle = "喝一杯冰镇汽水",
            content = "气泡在舌尖跳舞，快乐很简单",
            tag = "饮品"
        ),
        TownWhisper(
            id = "living_3",
            type = WhisperType.LIVING,
            subtitle = "吃一顿火锅",
            content = "热气腾腾中，所有烦恼都融化了",
            tag = "美食"
        ),
        TownWhisper(
            id = "living_4",
            type = WhisperType.LIVING,
            subtitle = "吃一块蛋糕",
            content = "甜的东西，能治愈一切",
            tag = "甜品"
        ),
        TownWhisper(
            id = "living_5",
            type = WhisperType.LIVING,
            subtitle = "喝一杯热茶",
            content = "茶香袅袅，岁月静好",
            tag = "饮品"
        ),
        TownWhisper(
            id = "living_6",
            type = WhisperType.LIVING,
            subtitle = "吃一碗冰淇淋",
            content = "夏天的快乐，是冰淇淋给的",
            tag = "甜品"
        ),
        TownWhisper(
            id = "living_7",
            type = WhisperType.LIVING,
            subtitle = "喝一杯咖啡",
            content = "咖啡的苦，生活的甜",
            tag = "饮品"
        ),
        TownWhisper(
            id = "living_8",
            type = WhisperType.LIVING,
            subtitle = "吃一顿烧烤",
            content = "烟火气里，有最真实的幸福",
            tag = "美食"
        ),
        TownWhisper(
            id = "living_9",
            type = WhisperType.LIVING,
            subtitle = "喝一杯奶茶",
            content = "三分糖，刚刚好",
            tag = "饮品"
        ),
        TownWhisper(
            id = "living_10",
            type = WhisperType.LIVING,
            subtitle = "吃一份水果",
            content = "新鲜的味道，是自然的馈赠",
            tag = "美食"
        ),
        TownWhisper(
            id = "living_11",
            type = WhisperType.LIVING,
            subtitle = "看一部喜欢的电影",
            content = "暂时逃离现实，也是一种休息",
            tag = "娱乐"
        ),
        TownWhisper(
            id = "living_12",
            type = WhisperType.LIVING,
            subtitle = "听一首老歌",
            content = "旋律响起，回忆涌上心头",
            tag = "音乐"
        ),
        TownWhisper(
            id = "living_13",
            type = WhisperType.LIVING,
            subtitle = "逛菜市场",
            content = "人间烟火气，最抚凡人心",
            tag = "日常"
        ),
        TownWhisper(
            id = "living_14",
            type = WhisperType.LIVING,
            subtitle = "散步",
            content = "慢慢走，感受风的温柔",
            tag = "运动"
        ),
        TownWhisper(
            id = "living_15",
            type = WhisperType.LIVING,
            subtitle = "晒太阳",
            content = "阳光是免费的快乐",
            tag = "日常"
        ),
        TownWhisper(
            id = "living_16",
            type = WhisperType.LIVING,
            subtitle = "看云",
            content = "云朵没有形状，快乐也没有定义",
            tag = "自然"
        ),
        TownWhisper(
            id = "living_17",
            type = WhisperType.LIVING,
            subtitle = "发呆",
            content = "大脑放假，也是一种充电",
            tag = "放空"
        ),
        TownWhisper(
            id = "living_18",
            type = WhisperType.LIVING,
            subtitle = "睡懒觉",
            content = "周末最大的奢侈",
            tag = "休息"
        ),
        TownWhisper(
            id = "living_19",
            type = WhisperType.LIVING,
            subtitle = "泡个热水澡",
            content = "疲惫随水流走",
            tag = "放松"
        ),
        TownWhisper(
            id = "living_20",
            type = WhisperType.LIVING,
            subtitle = "整理房间",
            content = "环境干净了，心情也会变好",
            tag = "整理"
        ),
        TownWhisper(
            id = "living_21",
            type = WhisperType.LIVING,
            subtitle = "种花",
            content = "看着生命成长，是一种幸福",
            tag = "植物"
        ),
        TownWhisper(
            id = "living_22",
            type = WhisperType.LIVING,
            subtitle = "养宠物",
            content = "毛茸茸的温暖，能治愈一切",
            tag = "宠物"
        ),
        TownWhisper(
            id = "living_23",
            type = WhisperType.LIVING,
            subtitle = "和朋友聚餐",
            content = "笑声比美食更重要",
            tag = "友情"
        ),
        TownWhisper(
            id = "living_24",
            type = WhisperType.LIVING,
            subtitle = "独自旅行",
            content = "一个人也能很精彩",
            tag = "旅行"
        ),
        TownWhisper(
            id = "living_25",
            type = WhisperType.LIVING,
            subtitle = "看一场日落",
            content = "夕阳无限好，哪怕近黄昏",
            tag = "自然"
        ),
        TownWhisper(
            id = "living_26",
            type = WhisperType.LIVING,
            subtitle = "吃早餐",
            content = "好好吃早餐，是一天的仪式感",
            tag = "美食"
        ),
        TownWhisper(
            id = "living_27",
            type = WhisperType.LIVING,
            subtitle = "看星星",
            content = "仰望星空，烦恼变小了",
            tag = "自然"
        ),
        TownWhisper(
            id = "living_28",
            type = WhisperType.LIVING,
            subtitle = "写日记",
            content = "和自己对话，也是一种陪伴",
            tag = "写作"
        ),
        TownWhisper(
            id = "living_29",
            type = WhisperType.LIVING,
            subtitle = "折纸",
            content = "简单的手工，带来单纯的快乐",
            tag = "手工"
        ),
        TownWhisper(
            id = "living_30",
            type = WhisperType.LIVING,
            subtitle = "逛书店",
            content = "在书的海洋里，找到自己",
            tag = "阅读"
        )
    )
}