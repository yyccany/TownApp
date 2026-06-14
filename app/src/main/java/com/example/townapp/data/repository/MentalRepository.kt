package com.example.townapp.data.repository

data class MentalContent(
    val id: Int,
    val name: String,
    val category: String,
    val valueCoeff: Double,      // 价值系数（越高=正向价值越高）
    val mentalCostCoeff: Double, // 精神成本系数（越高=损耗越大）
    val description: String
)

object MentalRepository {
    private val mentalContents = listOf(
        MentalContent(
            id = 101,
            name = "修仙/系统流网文(重度沉迷)",
            category = "novel",
            valueCoeff = 0.002,
            mentalCostCoeff = 2.0,
            description = "每天阅读≥3小时，用来逃避现实"
        ),
        MentalContent(
            id = 102,
            name = "修仙/系统流网文(轻度消遣)",
            category = "novel",
            valueCoeff = 0.3,
            mentalCostCoeff = 1.1,
            description = "每天阅读≤1小时，仅作为消遣"
        ),
        MentalContent(
            id = 103,
            name = "经典文学/科普书籍",
            category = "book",
            valueCoeff = 8.0,
            mentalCostCoeff = 0.8,
            description = "有思想深度的阅读内容"
        ),
        MentalContent(
            id = 104,
            name = "短视频(娱乐)",
            category = "video",
            valueCoeff = 0.5,
            mentalCostCoeff = 1.3,
            description = "碎片化娱乐内容"
        ),
        MentalContent(
            id = 105,
            name = "学习资料/技能教程",
            category = "learning",
            valueCoeff = 50.0,
            mentalCostCoeff = 0.7,
            description = "能提升技能的学习内容"
        ),
        MentalContent(
            id = 106,
            name = "短视频(学习)",
            category = "video",
            valueCoeff = 5.0,
            mentalCostCoeff = 1.0,
            description = "有知识增量的短视频"
        ),
        MentalContent(
            id = 107,
            name = "网络小说(精品)",
            category = "novel",
            valueCoeff = 2.0,
            mentalCostCoeff = 1.0,
            description = "有完整世界观和思想深度的小说"
        ),
        MentalContent(
            id = 108,
            name = "直播打赏",
            category = "live",
            valueCoeff = 0.0005,
            mentalCostCoeff = 3.0,
            description = "为主播付费打赏"
        ),
        MentalContent(
            id = 109,
            name = "短视频(重度沉迷)",
            category = "video",
            valueCoeff = 0.001,
            mentalCostCoeff = 2.5,
            description = "每天刷短视频≥2小时，深度沉迷"
        ),
        MentalContent(
            id = 110,
            name = "玄学算命",
            category = "superstition",
            valueCoeff = 0.001,
            mentalCostCoeff = 4.0,
            description = "算命、看风水、买开光饰品"
        ),
        MentalContent(
            id = 111,
            name = "网游氪金",
            category = "game",
            valueCoeff = 0.0008,
            mentalCostCoeff = 2.5,
            description = "在网络游戏中充值消费"
        ),
        MentalContent(
            id = 112,
            name = "历史(重度沉迷)",
            category = "learning",
            valueCoeff = 0.1,
            mentalCostCoeff = 1.5,
            description = "每天学习历史≥3小时，沉迷过去"
        ),
        MentalContent(
            id = 113,
            name = "历史(兴趣爱好)",
            category = "learning",
            valueCoeff = 0.5,
            mentalCostCoeff = 1.1,
            description = "每天学习历史≤1小时，作为兴趣"
        ),
        MentalContent(
            id = 114,
            name = "传统文化糟粕",
            category = "culture",
            valueCoeff = 0.01,
            mentalCostCoeff = 3.0,
            description = "沉迷封建礼教、男尊女卑等糟粕"
        ),
        MentalContent(
            id = 115,
            name = "传统文化精华",
            category = "culture",
            valueCoeff = 2.0,
            mentalCostCoeff = 1.0,
            description = "学习勤劳、善良、节俭等美德"
        ),
        MentalContent(
            id = 116,
            name = "非遗手工(职业)",
            category = "craft",
            valueCoeff = 0.5,
            mentalCostCoeff = 1.5,
            description = "以手工技艺为职业"
        ),
        MentalContent(
            id = 117,
            name = "非遗手工(兴趣)",
            category = "craft",
            valueCoeff = 1.0,
            mentalCostCoeff = 1.2,
            description = "手工技艺作为兴趣爱好"
        ),
        MentalContent(
            id = 118,
            name = "英语/外语",
            category = "learning",
            valueCoeff = 5.0,
            mentalCostCoeff = 0.8,
            description = "学习英语或其他外语"
        ),
        MentalContent(
            id = 119,
            name = "科学技术",
            category = "learning",
            valueCoeff = 20.0,
            mentalCostCoeff = 0.7,
            description = "学习编程、工程、医学等"
        ),
        MentalContent(
            id = 120,
            name = "实用技能",
            category = "learning",
            valueCoeff = 10.0,
            mentalCostCoeff = 0.8,
            description = "学习做饭、开车、理财等"
        ),
        MentalContent(
            id = 121,
            name = "批判性思维",
            category = "learning",
            valueCoeff = 50.0,
            mentalCostCoeff = 0.5,
            description = "学习独立思考、逻辑分析"
        ),
        MentalContent(
            id = 122,
            name = "英语/现代外语",
            category = "language",
            valueCoeff = 10.0,
            mentalCostCoeff = 0.8,
            description = "学习英语或其他现代外语"
        ),
        MentalContent(
            id = 123,
            name = "编程语言",
            category = "language",
            valueCoeff = 20.0,
            mentalCostCoeff = 0.7,
            description = "学习Python、Java、C++等编程语言"
        ),
        MentalContent(
            id = 124,
            name = "文言文(必修)",
            category = "language",
            valueCoeff = 0.001,
            mentalCostCoeff = 2.0,
            description = "作为必修课学习文言文"
        ),
        MentalContent(
            id = 125,
            name = "文言文(兴趣)",
            category = "language",
            valueCoeff = 0.5,
            mentalCostCoeff = 1.2,
            description = "作为兴趣爱好学习文言文"
        ),
        MentalContent(
            id = 126,
            name = "拉丁语(必修)",
            category = "language",
            valueCoeff = 0.001,
            mentalCostCoeff = 2.0,
            description = "作为必修课学习拉丁语"
        ),
        MentalContent(
            id = 127,
            name = "拉丁语(兴趣)",
            category = "language",
            valueCoeff = 0.5,
            mentalCostCoeff = 1.2,
            description = "作为兴趣爱好学习拉丁语"
        ),

        // ===== 时代局限性批判：娱乐活动的价值密度 =====

        // 负面娱乐（过时娱乐）
        MentalContent(
            id = 128,
            name = "打牌(赌博)",
            category = "entertainment_negative",
            valueCoeff = 0.005,
            mentalCostCoeff = 2.0,
            description = "每周打牌超过1次且赌钱，输赢平均200元"
        ),
        MentalContent(
            id = 129,
            name = "打牌(娱乐不赌钱)",
            category = "entertainment_negative",
            valueCoeff = 0.5,
            mentalCostCoeff = 1.2,
            description = "每周打牌不超过1次，不赌钱"
        ),
        MentalContent(
            id = 130,
            name = "喝酒(过量)",
            category = "entertainment_negative",
            valueCoeff = 0.001,
            mentalCostCoeff = 5.0,
            description = "每周喝酒超过1次且过量，每次半斤白酒以上"
        ),
        MentalContent(
            id = 131,
            name = "喝酒(少量社交)",
            category = "entertainment_negative",
            valueCoeff = 0.3,
            mentalCostCoeff = 1.5,
            description = "偶尔少量饮酒，不超过2两"
        ),
        MentalContent(
            id = 132,
            name = "麻将(赌博)",
            category = "entertainment_negative",
            valueCoeff = 0.003,
            mentalCostCoeff = 2.5,
            description = "每周打麻将超过2次且赌钱"
        ),
        MentalContent(
            id = 133,
            name = "掼蛋(攀比)",
            category = "entertainment_negative",
            valueCoeff = 0.01,
            mentalCostCoeff = 2.0,
            description = "每周掼蛋超过3次，用来攀比和社交"
        ),

        // 正面娱乐（健康替代方案）
        MentalContent(
            id = 134,
            name = "徒步/爬山",
            category = "entertainment_positive",
            valueCoeff = 10.0,
            mentalCostCoeff = 0.5,
            description = "每月参加2次以上徒步活动"
        ),
        MentalContent(
            id = 135,
            name = "读书",
            category = "entertainment_positive",
            valueCoeff = 8.0,
            mentalCostCoeff = 0.8,
            description = "每月读2本以上书籍"
        ),
        MentalContent(
            id = 136,
            name = "露营",
            category = "entertainment_positive",
            valueCoeff = 15.0,
            mentalCostCoeff = 0.6,
            description = "每月参加1次以上露营活动"
        ),
        MentalContent(
            id = 137,
            name = "手工",
            category = "entertainment_positive",
            valueCoeff = 12.0,
            mentalCostCoeff = 0.7,
            description = "在手工工坊完成作品"
        ),
        MentalContent(
            id = 138,
            name = "运动健身",
            category = "entertainment_positive",
            valueCoeff = 20.0,
            mentalCostCoeff = 0.5,
            description = "每月运动超过10次"
        ),
        MentalContent(
            id = 139,
            name = "朋友聚会(不喝酒)",
            category = "entertainment_positive",
            valueCoeff = 15.0,
            mentalCostCoeff = 0.6,
            description = "每月和朋友聚会超过2次，不喝酒"
        ),
        MentalContent(
            id = 140,
            name = "茶话会",
            category = "entertainment_positive",
            valueCoeff = 18.0,
            mentalCostCoeff = 0.5,
            description = "和朋友喝茶聊天，真诚交流"
        )
    )

    fun getAllMentalContents(): List<MentalContent> = mentalContents

    fun getContentByName(name: String): MentalContent? {
        return mentalContents.find { it.name == name }
    }

    fun getContentsByCategory(category: String): List<MentalContent> {
        return mentalContents.filter { it.category == category }
    }

    fun getContentById(id: Int): MentalContent? {
        return mentalContents.find { it.id == id }
    }
}
