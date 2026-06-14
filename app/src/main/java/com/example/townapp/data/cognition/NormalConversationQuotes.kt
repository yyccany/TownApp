package com.example.townapp.data.cognition

import com.example.townapp.data.microevent.TownCharacter

/**
 * 正常交流语录库
 * 
 * 核心宗旨：打破传统成语的束缚，用日常、温暖、真实的语言与用户交流
 * 风格参考：三礼主播的亲切、自然、有温度的表达方式
 * 
 * 核心原则：
 * 1. 真实：不说空话套话，用真诚的语言表达
 * 2. 温暖：传递关怀和支持，让用户感受到被理解
 * 3. 理性：用数据和事实说话，但保持温柔的表达方式
 * 4. 陪伴：不是说教，而是陪伴和支持
 */

// 语录类型
enum class ConversationType(
    val displayName: String,
    val emoji: String
) {
    ENCOURAGEMENT("鼓励", "💪"),
    REFLECTION("反思", "🤔"),
    OBSERVATION("观察", "👀"),
    SUPPORT("支持", "🤝"),
    CELEBRATION("庆祝", "🎉"),
    COMFORT("安慰", "💛"),
    INSIGHT("洞察", "💡"),
    HUMOR("幽默", "😄")
}

// 语录数据
data class ConversationQuote(
    val id: String,
    val type: ConversationType,
    val character: TownCharacter,
    val content: String,
    val context: String = "",
    val keywords: List<String> = emptyList()
)

/**
 * 正常交流语录数据库
 */
object NormalConversationQuotes {

    fun getAllQuotes(): List<ConversationQuote> {
        return getEncouragementQuotes() +
                getReflectionQuotes() +
                getObservationQuotes() +
                getSupportQuotes() +
                getCelebrationQuotes() +
                getComfortQuotes() +
                getInsightQuotes() +
                getHumorQuotes()
    }

    fun getQuotesByType(type: ConversationType): List<ConversationQuote> {
        return getAllQuotes().filter { it.type == type }
    }

    fun getQuotesByCharacter(character: TownCharacter): List<ConversationQuote> {
        return getAllQuotes().filter { it.character == character }
    }

    fun getRandomQuote(): ConversationQuote {
        val allQuotes = getAllQuotes()
        return allQuotes.random()
    }

    // ============================================
    // 一、鼓励语录
    // ============================================
    private fun getEncouragementQuotes(): List<ConversationQuote> = listOf(
        ConversationQuote(
            id = "enc_1",
            type = ConversationType.ENCOURAGEMENT,
            character = TownCharacter.TAFFI,
            content = "你今天已经很棒了喵～不管做了什么，只要认真对待，就是值得肯定的😺",
            context = "用户完成某项任务后",
            keywords = listOf("努力", "肯定", "完成")
        ),
        ConversationQuote(
            id = "enc_2",
            type = ConversationType.ENCOURAGEMENT,
            character = TownCharacter.DORO,
            content = "慢慢来，不用急。每个人的节奏都不一样，找到自己的步调最重要💛",
            context = "用户感到焦虑或压力时",
            keywords = listOf("慢慢来", "节奏", "自己")
        ),
        ConversationQuote(
            id = "enc_3",
            type = ConversationType.ENCOURAGEMENT,
            character = TownCharacter.GUGAGA,
            content = "咕咕～你已经在进步了，只是有时候自己没发现而已🥰",
            context = "用户怀疑自己的进步时",
            keywords = listOf("进步", "发现")
        ),
        ConversationQuote(
            id = "enc_4",
            type = ConversationType.ENCOURAGEMENT,
            character = TownCharacter.TAFFI,
            content = "哪怕只是迈出一小步，也是朝着目标前进了喵😺",
            context = "用户面对困难任务时",
            keywords = listOf("小步", "前进", "目标")
        ),
        ConversationQuote(
            id = "enc_5",
            type = ConversationType.ENCOURAGEMENT,
            character = TownCharacter.DORO,
            content = "你比自己想象的更强大，相信自己的能力💪",
            context = "用户缺乏自信时",
            keywords = listOf("强大", "相信", "能力")
        )
    )

    // ============================================
    // 二、反思语录
    // ============================================
    private fun getReflectionQuotes(): List<ConversationQuote> = listOf(
        ConversationQuote(
            id = "ref_1",
            type = ConversationType.REFLECTION,
            character = TownCharacter.TAFFI,
            content = "有时候停下来想想：这件事真的值得你花时间吗？喵～",
            context = "用户陷入忙碌时",
            keywords = listOf("时间", "价值", "思考")
        ),
        ConversationQuote(
            id = "ref_2",
            type = ConversationType.REFLECTION,
            character = TownCharacter.DORO,
            content = "我们常常为了别人的期待而活，却忘了问问自己真正想要什么",
            context = "用户感到迷茫时",
            keywords = listOf("期待", "自己", "想要")
        ),
        ConversationQuote(
            id = "ref_3",
            type = ConversationType.REFLECTION,
            character = TownCharacter.GUGAGA,
            content = "咕咕～消费是为了让生活更好，不是为了证明什么🥺",
            context = "用户过度消费时",
            keywords = listOf("消费", "生活", "证明")
        ),
        ConversationQuote(
            id = "ref_4",
            type = ConversationType.REFLECTION,
            character = TownCharacter.TAFFI,
            content = "你的时间很宝贵，每一小时都值得被认真对待喵😺",
            context = "用户浪费时间时",
            keywords = listOf("时间", "宝贵", "认真")
        ),
        ConversationQuote(
            id = "ref_5",
            type = ConversationType.REFLECTION,
            character = TownCharacter.DORO,
            content = "真正的自由，是有能力选择自己想要的生活",
            context = "用户感到被束缚时",
            keywords = listOf("自由", "选择", "生活")
        )
    )

    // ============================================
    // 三、观察语录
    // ============================================
    private fun getObservationQuotes(): List<ConversationQuote> = listOf(
        ConversationQuote(
            id = "obs_1",
            type = ConversationType.OBSERVATION,
            character = TownCharacter.TAFFI,
            content = "喵～我发现你最近很注重健康呢，这是个好习惯！",
            context = "用户关注健康时",
            keywords = listOf("健康", "习惯")
        ),
        ConversationQuote(
            id = "obs_2",
            type = ConversationType.OBSERVATION,
            character = TownCharacter.DORO,
            content = "你选择的食物越来越健康了，能感觉到你对自己的用心💛",
            context = "用户饮食改善时",
            keywords = listOf("健康", "食物", "用心")
        ),
        ConversationQuote(
            id = "obs_3",
            type = ConversationType.OBSERVATION,
            character = TownCharacter.GUGAGA,
            content = "咕咕～你今天的觉醒值涨了，说明你在思考哦🥰",
            context = "用户觉醒值增加时",
            keywords = listOf("觉醒值", "思考")
        ),
        ConversationQuote(
            id = "obs_4",
            type = ConversationType.OBSERVATION,
            character = TownCharacter.TAFFI,
            content = "我注意到你开始记录日常了，这会让生活更清晰喵😺",
            context = "用户开始记录时",
            keywords = listOf("记录", "日常", "清晰")
        ),
        ConversationQuote(
            id = "obs_5",
            type = ConversationType.OBSERVATION,
            character = TownCharacter.DORO,
            content = "你的消费越来越理性了，这是成熟的表现",
            context = "用户消费更理性时",
            keywords = listOf("消费", "理性", "成熟")
        )
    )

    // ============================================
    // 四、支持语录
    // ============================================
    private fun getSupportQuotes(): List<ConversationQuote> = listOf(
        ConversationQuote(
            id = "sup_1",
            type = ConversationType.SUPPORT,
            character = TownCharacter.TAFFI,
            content = "不管你做什么决定，我们都会支持你的喵😺",
            context = "用户做重要决定时",
            keywords = listOf("决定", "支持")
        ),
        ConversationQuote(
            id = "sup_2",
            type = ConversationType.SUPPORT,
            character = TownCharacter.DORO,
            content = "累了就休息，不需要勉强自己。你值得被温柔对待💛",
            context = "用户感到疲惫时",
            keywords = listOf("休息", "温柔", "值得")
        ),
        ConversationQuote(
            id = "sup_3",
            type = ConversationType.SUPPORT,
            character = TownCharacter.GUGAGA,
            content = "咕咕～你不是一个人，我们一直都在🥰",
            context = "用户感到孤独时",
            keywords = listOf("孤独", "陪伴")
        ),
        ConversationQuote(
            id = "sup_4",
            type = ConversationType.SUPPORT,
            character = TownCharacter.TAFFI,
            content = "想哭就哭，想笑就笑，不用在意别人的眼光喵😺",
            context = "用户情绪波动时",
            keywords = listOf("情绪", "真实", "自己")
        ),
        ConversationQuote(
            id = "sup_5",
            type = ConversationType.SUPPORT,
            character = TownCharacter.DORO,
            content = "你的感受很重要，不要忽略自己的内心声音",
            context = "用户压抑情绪时",
            keywords = listOf("感受", "内心", "声音")
        )
    )

    // ============================================
    // 五、庆祝语录
    // ============================================
    private fun getCelebrationQuotes(): List<ConversationQuote> = listOf(
        ConversationQuote(
            id = "cel_1",
            type = ConversationType.CELEBRATION,
            character = TownCharacter.TAFFI,
            content = "恭喜恭喜！又完成了一件大事喵～🎉",
            context = "用户完成重要任务时",
            keywords = listOf("完成", "恭喜", "大事")
        ),
        ConversationQuote(
            id = "cel_2",
            type = ConversationType.CELEBRATION,
            character = TownCharacter.DORO,
            content = "太棒了！你的努力终于有了回报💛",
            context = "用户获得成就时",
            keywords = listOf("努力", "回报", "成就")
        ),
        ConversationQuote(
            id = "cel_3",
            type = ConversationType.CELEBRATION,
            character = TownCharacter.GUGAGA,
            content = "咕咕～你做到了！真的太厉害了🥰",
            context = "用户克服困难时",
            keywords = listOf("做到", "厉害", "克服")
        ),
        ConversationQuote(
            id = "cel_4",
            type = ConversationType.CELEBRATION,
            character = TownCharacter.TAFFI,
            content = "今天的你也闪闪发光哦喵🌟",
            context = "用户日常打卡时",
            keywords = listOf("日常", "闪光", "打卡")
        ),
        ConversationQuote(
            id = "cel_5",
            type = ConversationType.CELEBRATION,
            character = TownCharacter.DORO,
            content = "每一个小进步都值得庆祝，你做得很好！",
            context = "用户取得小进步时",
            keywords = listOf("进步", "庆祝", "好")
        )
    )

    // ============================================
    // 六、安慰语录
    // ============================================
    private fun getComfortQuotes(): List<ConversationQuote> = listOf(
        ConversationQuote(
            id = "com_1",
            type = ConversationType.COMFORT,
            character = TownCharacter.TAFFI,
            content = "没关系的喵，谁都会有心情不好的时候，我陪着你😺",
            context = "用户心情低落时",
            keywords = listOf("心情", "陪伴", "没关系")
        ),
        ConversationQuote(
            id = "com_2",
            type = ConversationType.COMFORT,
            character = TownCharacter.DORO,
            content = "失败不是终点，只是暂时没找到正确的方法而已💛",
            context = "用户遭遇失败时",
            keywords = listOf("失败", "方法", "暂时")
        ),
        ConversationQuote(
            id = "com_3",
            type = ConversationType.COMFORT,
            character = TownCharacter.GUGAGA,
            content = "咕咕～不要自责，你已经尽力了🥺",
            context = "用户自责时",
            keywords = listOf("自责", "尽力")
        ),
        ConversationQuote(
            id = "com_4",
            type = ConversationType.COMFORT,
            character = TownCharacter.TAFFI,
            content = "今天不想努力也没关系，休息是为了更好地出发喵😺",
            context = "用户感到疲惫不想努力时",
            keywords = listOf("休息", "努力", "出发")
        ),
        ConversationQuote(
            id = "com_5",
            type = ConversationType.COMFORT,
            character = TownCharacter.DORO,
            content = "你不需要完美，真实的你就已经很好了",
            context = "用户追求完美感到压力时",
            keywords = listOf("完美", "真实", "好")
        )
    )

    // ============================================
    // 七、洞察语录
    // ============================================
    private fun getInsightQuotes(): List<ConversationQuote> = listOf(
        ConversationQuote(
            id = "ins_1",
            type = ConversationType.INSIGHT,
            character = TownCharacter.TAFFI,
            content = "喵～你知道吗？你的每一小时都值很多钱呢，要好好利用哦😺",
            context = "用户浪费时间时",
            keywords = listOf("时间", "价值", "利用")
        ),
        ConversationQuote(
            id = "ins_2",
            type = ConversationType.INSIGHT,
            character = TownCharacter.DORO,
            content = "消费的本质是用你的时间去交换，想清楚是否值得💛",
            context = "用户准备消费时",
            keywords = listOf("消费", "时间", "交换")
        ),
        ConversationQuote(
            id = "ins_3",
            type = ConversationType.INSIGHT,
            character = TownCharacter.GUGAGA,
            content = "咕咕～真正的奢侈不是拥有多少，而是拥有的都是你真正需要的🥰",
            context = "用户过度消费时",
            keywords = listOf("奢侈", "需要", "拥有")
        ),
        ConversationQuote(
            id = "ins_4",
            type = ConversationType.INSIGHT,
            character = TownCharacter.TAFFI,
            content = "健康是最宝贵的财富，投资自己的身体永远值得喵😺",
            context = "用户关注健康时",
            keywords = listOf("健康", "财富", "投资")
        ),
        ConversationQuote(
            id = "ins_5",
            type = ConversationType.INSIGHT,
            character = TownCharacter.DORO,
            content = "你值得被爱，不是因为你做了什么，而是因为你就是你",
            context = "用户自我价值感低时",
            keywords = listOf("值得", "爱", "自我")
        )
    )

    // ============================================
    // 八、幽默语录
    // ============================================
    private fun getHumorQuotes(): List<ConversationQuote> = listOf(
        ConversationQuote(
            id = "hum_1",
            type = ConversationType.HUMOR,
            character = TownCharacter.TAFFI,
            content = "喵～听说程序员的头发是用来写代码的，掉了说明代码写多了😺",
            context = "用户掉头发时",
            keywords = listOf("头发", "程序员", "代码")
        ),
        ConversationQuote(
            id = "hum_2",
            type = ConversationType.HUMOR,
            character = TownCharacter.DORO,
            content = "如果花钱能买到快乐，那我的快乐可能有点贵💛",
            context = "用户消费后",
            keywords = listOf("快乐", "消费", "贵")
        ),
        ConversationQuote(
            id = "hum_3",
            type = ConversationType.HUMOR,
            character = TownCharacter.GUGAGA,
            content = "咕咕～为什么减肥这么难？因为美食太香了🥺",
            context = "用户减肥时",
            keywords = listOf("减肥", "美食", "难")
        ),
        ConversationQuote(
            id = "hum_4",
            type = ConversationType.HUMOR,
            character = TownCharacter.TAFFI,
            content = "今天的摸鱼是为了明天更好地努力喵～（理直气壮）😺",
            context = "用户休息时",
            keywords = listOf("休息", "努力", "摸鱼")
        ),
        ConversationQuote(
            id = "hum_5",
            type = ConversationType.HUMOR,
            character = TownCharacter.DORO,
            content = "购物车就像黑洞，放进去的东西再也出不来了",
            context = "用户购物时",
            keywords = listOf("购物车", "黑洞", "购物")
        )
    )
}