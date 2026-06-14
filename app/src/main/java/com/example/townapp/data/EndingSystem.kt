package com.example.townapp.data

/**
 * 多元结局系统 - 16种不同的人生结局
 * 所有结局都是平等的，没有高低之分
 */
object EndingSystem {

    // 所有结局定义
    val ALL_ENDINGS = listOf(
        // 觉醒类
        Ending(
            id = "awakener",
            name = "觉醒者",
            category = "觉醒类",
            description = "你活成了自己想要的样子，自由、清醒、有尊严。你的小镇是一个充满阳光和希望的地方。",
            checkCondition = { stats -> stats.awakeningValue >= 10000 },
            priority = 1
        ),

        // 艺术创作类
        Ending(
            id = "artist",
            name = "艺术家",
            category = "艺术创作类",
            description = "你把热爱变成了事业，用自己的作品感动了无数人。你的小镇是一个充满艺术气息的地方。",
            checkCondition = { stats -> stats.getHighestValueDensityHobby() in listOf("photography", "painting", "music") && stats.getHighestValueDensity() >= 100.0 },
            priority = 2
        ),

        // 游戏开发类
        Ending(
            id = "game_master",
            name = "游戏大师",
            category = "游戏开发类",
            description = "你把对游戏的热爱，变成了改变世界的力量。你的小镇是一个充满想象力的地方。",
            checkCondition = { stats -> stats.getHighestValueDensityHobby() == "gaming" && stats.getHighestValueDensity() >= 100.0 },
            priority = 2
        ),

        // 旅行探索类
        Ending(
            id = "traveler",
            name = "旅行者",
            category = "旅行探索类",
            description = "你用脚步丈量了世界，见识了不同的风景和文化。你的小镇是一个充满故事的地方。",
            checkCondition = { stats -> stats.getHighestValueDensityHobby() == "travel" && stats.getHighestValueDensity() >= 100.0 },
            priority = 2
        ),

        // 运动健身类
        Ending(
            id = "athlete",
            name = "运动健将",
            category = "运动健身类",
            description = "你用汗水浇灌了健康，身体和精神都充满了活力。你的小镇是一个充满运动气息的地方。",
            checkCondition = { stats -> stats.getHighestValueDensityHobby() == "exercise" && stats.getHighestValueDensity() >= 100.0 },
            priority = 2
        ),

        // 极简生活类
        Ending(
            id = "minimalist",
            name = "极简主义者",
            category = "极简生活类",
            description = "你明白了少即是多的道理，过着简单而自由的生活。你的小镇是一个整洁有序的地方。",
            checkCondition = { stats -> stats.minimalismScore >= 1000.0 },
            priority = 2
        ),

        // 家庭生活类
        Ending(
            id = "family_guardian",
            name = "家庭守护者",
            category = "家庭生活类",
            description = "你用心经营着家庭，创造了一个温馨而幸福的港湾。你的小镇是一个充满温暖的地方。",
            checkCondition = { stats -> stats.familyScore >= 1000.0 },
            priority = 2
        ),

        // 知识学习类
        Ending(
            id = "scholar",
            name = "学者",
            category = "知识学习类",
            description = "你在知识的海洋里遨游，不断探索和成长。你的小镇是一个充满智慧的地方。",
            checkCondition = { stats -> stats.getHighestValueDensityHobby() == "reading" && stats.getHighestValueDensity() >= 100.0 },
            priority = 2
        ),

        // 社交人际类
        Ending(
            id = "social_butterfly",
            name = "社交蝴蝶",
            category = "社交人际类",
            description = "你与人建立了深刻的连接，身边围绕着真正的朋友。你的小镇是一个充满欢笑的地方。",
            checkCondition = { stats -> stats.socialScore >= 1000.0 },
            priority = 2
        ),

        // 商业创业类
        Ending(
            id = "entrepreneur",
            name = "创业者",
            category = "商业创业类",
            description = "你把想法变成了现实，创造了属于自己的事业。你的小镇是一个充满机遇的地方。",
            checkCondition = { stats -> stats.incomeFromHobbies >= 100.0 },
            priority = 2
        ),

        // 自然环保类
        Ending(
            id = "nature_guardian",
            name = "自然守护者",
            category = "自然环保类",
            description = "你与自然和谐共处，为保护环境贡献了自己的力量。你的小镇是一个绿水青山的地方。",
            checkCondition = { stats -> stats.natureScore >= 1000.0 },
            priority = 2
        ),

        // 精神修行类
        Ending(
            id = "spiritual_practitioner",
            name = "修行者",
            category = "精神修行类",
            description = "你找到了内心的平静，活在当下，喜悦自在。你的小镇是一个宁静祥和的地方。",
            checkCondition = { stats -> stats.spiritualScore >= 1000.0 },
            priority = 2
        ),

        // 美食烹饪类
        Ending(
            id = "gourmet",
            name = "美食家",
            category = "美食烹饪类",
            description = "你用美食传递幸福，让身边的人都感受到温暖。你的小镇是一个充满香气的地方。",
            checkCondition = { stats -> stats.getHighestValueDensityHobby() == "cooking" && stats.getHighestValueDensity() >= 100.0 },
            priority = 2
        ),

        // 平凡稳定类
        Ending(
            id = "ordinary_person",
            name = "普通人",
            category = "平凡稳定类",
            description = "你过着平凡但幸福的生活，有一个温暖的家，几个好朋友。你的小镇不好也不坏，刚刚好。",
            checkCondition = { stats -> stats.awakeningValue in 1000..9999 },
            priority = 3
        ),

        // 探索多元类
        Ending(
            id = "life_explorer",
            name = "生活探险家",
            category = "探索多元类",
            description = "你勇敢地尝试了各种不同的生活方式，找到了最适合自己的那一种。你的小镇是一个多姿多彩的地方。",
            checkCondition = { stats -> stats.highValueDiversity >= 5 },
            priority = 3
        ),

        // 迷失反思类
        Ending(
            id = "lost_one",
            name = "迷失者",
            category = "迷失反思类",
            description = "你一辈子都在为别人而活，从来没有为自己活过。但没关系，什么时候开始觉醒都不晚。你的小镇是一个灰色的、但随时可以改变的地方。",
            checkCondition = { stats -> stats.awakeningValue < 1000 },
            priority = 4
        )
    )

    /**
     * 计算用户最终结局
     */
    fun calculateEnding(stats: EndingStats): Ending {
        // 按优先级检查每个结局，返回第一个满足条件的
        return ALL_ENDINGS
            .sortedBy { it.priority }
            .firstOrNull { it.checkCondition(stats) }
            ?: ALL_ENDINGS.last() // 默认返回最后一个
    }
}

data class Ending(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val checkCondition: (EndingStats) -> Boolean,
    val priority: Int // 优先级，数字越小越优先
)

data class EndingStats(
    val awakeningValue: Int,
    val hobbyValueDensities: Map<String, Double>,
    val minimalismScore: Double = 0.0,
    val familyScore: Double = 0.0,
    val socialScore: Double = 0.0,
    val natureScore: Double = 0.0,
    val spiritualScore: Double = 0.0,
    val incomeFromHobbies: Double = 0.0,
    val highValueDiversity: Int = 0
) {
    fun getHighestValueDensityHobby(): String? {
        return hobbyValueDensities.maxByOrNull { it.value }?.key
    }

    fun getHighestValueDensity(): Double {
        return hobbyValueDensities.values.maxOrNull() ?: 0.0
    }
}
