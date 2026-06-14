package com.example.townapp.data

/**
 * 服饰穿戴动态参数体系（v2.12 个人微观动态参数-日常生活细节层）
 *
 * 核心设计理念：
 * 1. 归入日常生活细节层，和饮食、作息、居住环境并列，属于后天动态调整项
 * 2. 分两类机制：生理健康（鞋袜脚气/衣物受寒/卫生皮炎）+ 社会心理（穿搭整洁度→社交印象）
 * 3. 细碎参数默认自动化运行，仅异常状态达到阈值后推送提醒，避免操作负担
 * 4. 穿戴属于微调参数，不颠覆家境/时代/信念等核心变量的主导地位
 * 5. 三层联动：Weather（季节天气）→ ClothingWear（穿戴状态）→ BodyHealth + SocialImpression
 *
 * 世界观定位：
 *   闷湿鞋袜患病、冷水劳作手部脱皮、年少穿搭自卑——
 *   这些都是普通人真实的日常困境。细节看着细碎，却夯实了实事求是、以人为本的底色。
 */

// ============================================
// 一、穿戴位与状态枚举
// ============================================

/**
 * 穿戴位（身体不同部位的穿着）
 */
enum class WearSlot(val label: String, val description: String) {
    FEET("鞋袜", "脚部穿着——最容易受潮、滋生细菌的部位"),
    LOWER_BODY("下装", "裤子/裙子——耐磨性决定户外工作保护力"),
    UPPER_BODY("上装", "上衣——核心保暖层，单薄易受寒"),
    OUTERWEAR("外套", "最外层防护——防风、防水、保暖"),
    ACCESSORIES("配饰", "帽子/围巾/手套——辅助保暖与社交装饰")
}

/**
 * 鞋袜透气性等级
 */
enum class ShoeBreathability(val label: String, val moistureRetention: Double, val description: String) {
    POOR("不透气", 0.6, "塑料/人造革材质，闷湿环境极易滋生脚气"),
    MODERATE("一般", 0.3, "普通帆布/皮革，日常穿着基本够用"),
    GOOD("透气", 0.1, "网面/真皮透气款，适合运动与炎热天气")
}

/**
 * 衣物保暖等级
 */
enum class ClothingWarmth(val label: String, val warmthValue: Int, val description: String) {
    THIN("单薄", 1, "薄款T恤/短裤，低温环境容易受寒"),
    MODERATE("适中", 3, "普通长袖/长裤，适合春秋日常"),
    WARM("保暖", 5, "加绒/毛衣，冬季低温必备"),
    HEAVY("厚重", 7, "羽绒/棉服，极寒环境专用——酷暑穿会燥热")
}

/**
 * 衣物洁净度
 */
enum class ClothingCleanliness(val label: String, val tier: Int, val socialModifier: Double, val healthRisk: Double, val description: String) {
    FILTHY("脏污", 1, -0.08, 0.15, "长期未清洗，有明显污渍和异味"),
    DIRTY("偏脏", 2, -0.04, 0.05, "穿了两三天没洗，略有异味"),
    NORMAL("一般", 3, 0.0, 0.0, "日常穿着状态，没有问题"),
    CLEAN("干净", 4, 0.03, 0.0, "刚洗过/新换的，整洁清爽"),
    FRESH("清爽", 5, 0.05, 0.0, "精心打理过，散发着淡淡的洗衣液清香")
}

/**
 * 穿搭品质（外观档次）
 */
enum class ClothingQuality(val label: String, val tier: Int, val socialModifier: Double, val selfIdentityMod: Int, val description: String) {
    SHABBY("破旧", 1, -0.10, -5, "有破损、褪色、明显的年代感"),
    PLAIN("朴素", 2, 0.0, 0, "普通工薪穿着，干净但不显眼"),
    NEAT("整洁", 3, 0.03, 2, "搭配合理、干净得体，给人好印象"),
    REFINED("精致", 4, 0.06, 4, "讲究的穿搭，体现审美和用心"),
    LUXURY("昂贵", 5, 0.10, 6, "高端品牌服饰，社交场合自带光环——但也容易引来目光")
}

// ============================================
// 二、健康状态枚举
// ============================================

/**
 * 脚部健康问题
 */
enum class FootCondition(val label: String, val severity: Int, val fatigueBonus: Int, val description: String) {
    HEALTHY("健康", 0, 0, "脚部状态良好"),
    MILD_FUNGUS("轻微脚气", 1, 3, "偶尔瘙痒，不影响日常"),
    MODERATE_FUNGUS("中度脚气", 2, 5, "频繁瘙痒，走路时有点不舒服"),
    SEVERE_FUNGUS("严重脚气", 3, 8, "持续瘙痒难忍，破损出水，严重影响状态"),
    CHILBLAIN("冻疮", 2, 4, "脚部因长期湿冷长出冻疮，又红又肿")
}

/**
 * 皮肤问题
 */
enum class SkinCondition(val label: String, val severity: Int, val appearancePenalty: Double, val description: String) {
    HEALTHY("健康", 0, 0.0, "皮肤状态良好"),
    MILD_ACNE("轻微长痘", 1, -0.03, "脸上冒了几颗痘，不影响整体"),
    MODERATE_DERMATITIS("中度皮炎", 2, -0.05, "皮肤泛红、有皮屑，有点介意别人的目光"),
    HAND_PEELING("手部脱皮", 1, -0.02, "长期湿水、冷水劳作导致手指脱皮——不影响外观但做事不舒服")
}

// ============================================
// 三、穿戴运行时状态
// ============================================

/**
 * 单个穿戴位的状态
 */
data class WearSlotState(
    val slot: WearSlot,
    val breathability: ShoeBreathability = ShoeBreathability.MODERATE,
    val warmth: ClothingWarmth = ClothingWarmth.MODERATE,
    val cleanliness: ClothingCleanliness = ClothingCleanliness.NORMAL,
    val quality: ClothingQuality = ClothingQuality.PLAIN,
    /** 潮湿程度 0-100（雨水/汗水累积） */
    val dampness: Int = 0,
    /** 破损程度 0-100 */
    val wearAndTear: Int = 0,
    /** 连续穿着天数（未更换） */
    val consecutiveWearDays: Int = 0,
    /** 上次清洗距今多少天 */
    val daysSinceLastWash: Int = 0
) {
    /** 是否需要洗了 */
    val needsWash: Boolean
        get() = cleanliness.tier <= 2

    /** 是否有健康风险 */
    val hasHealthRisk: Boolean
        get() = when (slot) {
            WearSlot.FEET -> dampness > 30 || consecutiveWearDays > 3 || breathability == ShoeBreathability.POOR
            WearSlot.UPPER_BODY -> warmth == ClothingWarmth.THIN
            WearSlot.LOWER_BODY -> wearAndTear > 40
            else -> false
        }
}

/**
 * 整体穿戴系统运行时状态
 */
data class ClothingWearState(
    /** 各穿戴位状态 */
    val slots: Map<WearSlot, WearSlotState> = mapOf(
        WearSlot.FEET to WearSlotState(WearSlot.FEET),
        WearSlot.LOWER_BODY to WearSlotState(WearSlot.LOWER_BODY),
        WearSlot.UPPER_BODY to WearSlotState(WearSlot.UPPER_BODY),
        WearSlot.OUTERWEAR to WearSlotState(WearSlot.OUTERWEAR)
    ),
    /** 脚部健康状态 */
    val footCondition: FootCondition = FootCondition.HEALTHY,
    /** 脚气累计风险值 0-100 */
    val footFungusRisk: Int = 0,
    /** 皮肤状态 */
    val skinCondition: SkinCondition = SkinCondition.HEALTHY,
    /** 皮肤问题累计风险 0-100 */
    val skinIssueRisk: Int = 0,
    /** 整体穿搭品质（取各slot中档位最高的） */
    val overallQuality: ClothingQuality = ClothingQuality.PLAIN,
    /** 整体洁净度（取各slot中档位最低的） */
    val overallCleanliness: ClothingCleanliness = ClothingCleanliness.NORMAL,
    /** 今日已触发健康事件 */
    val healthEventTriggeredToday: Boolean = false,
    /** 是否因精致穿搭产生焦虑 */
    val hasLuxuryAnxiety: Boolean = false,
    /** 是否选择了朴素自在路线 */
    val hasChosenSimplicity: Boolean = false,
    /** 是否因穿着遭受过偏见 */
    val hasExperiencedClothingPrejudice: Boolean = false,
    /** 上次洗衣距今小时数 */
    val hoursSinceLastLaundry: Int = 0
) {
    /**
     * 计算当前穿搭的社交印象修正
     * 权重：洁净度60% + 品质40%
     */
    fun socialImpressionModifier(): Double {
        val cleanMod = overallCleanliness.socialModifier * 0.6
        val qualityMod = overallQuality.socialModifier * 0.4
        return cleanMod + qualityMod
    }

    /**
     * 计算穿搭对自我认同的影响
     */
    fun selfIdentityImpact(): Int {
        val qualityImpact = overallQuality.selfIdentityMod
        val luxuryAnxietyPenalty = if (hasLuxuryAnxiety) -3 else 0
        return qualityImpact + luxuryAnxietyPenalty
    }
}

// ============================================
// 四、穿戴健康事件
// ============================================

/**
 * 穿戴相关健康随机事件
 */
data class ClothingHealthEvent(
    val id: String,
    val title: String,
    val description: String,
    /** 脚气风险增量 */
    val fungusRiskDelta: Int = 0,
    /** 疲劳值变化 */
    val fatigueDelta: Int = 0,
    /** 健康值变化 */
    val healthDelta: Int = 0,
    /** 目标脚部状态 */
    val targetFootCondition: FootCondition? = null,
    /** 目标皮肤状态 */
    val targetSkinCondition: SkinCondition? = null,
    /** 小镇评述 */
    val townCommentary: String
)

object ClothingHealthEvents {

    val events: List<ClothingHealthEvent> = listOf(
        ClothingHealthEvent(
            id = "damp_shoes_fungus",
            title = "闷湿的鞋",
            description = "你脱下鞋——里面又潮又闷。脚趾缝有点痒。你知道——再不换鞋，脚气就要找上门了。",
            fungusRiskDelta = 20, fatigueDelta = 2,
            townCommentary = "脚气不是什么大毛病——但它会让你一整天走路都不舒服。换双透气的鞋，晒晒太阳——你值得这种舒服。"
        ),
        ClothingHealthEvent(
            id = "foot_fungus_outbreak",
            title = "脚气发作了",
            description = "脚趾间的瘙痒让你在工位上坐立不安。你忍着不去挠——但越忍越难受。今天一整天你的注意力被它啃走了一小块。",
            fungusRiskDelta = 10, fatigueDelta = 5,
            targetFootCondition = FootCondition.MILD_FUNGUS,
            townCommentary = "这种瘙痒不是大毛病——但它在你最需要专注的时候拖你的后腿。换鞋、上药、晾干——你今天就能好受一点。"
        ),
        ClothingHealthEvent(
            id = "severe_fungus",
            title = "脚已经受不了了",
            description = "脚趾间破皮了——走路的时候磨得生疼。你已经开始下意识地用脚外侧走路——这个习惯不太好，但你的脚在告诉你：我忍很久了。",
            fungusRiskDelta = 15, fatigueDelta = 8, healthDelta = -2,
            targetFootCondition = FootCondition.SEVERE_FUNGUS,
            townCommentary = "你的脚已经替你扛了很久了。别等到它喊疼了才想起它——给它一双透气的鞋、一双干爽的袜子——这不是奢侈，是基本的善待自己。"
        ),
        ClothingHealthEvent(
            id = "thin_clothes_cold",
            title = "穿少了",
            description = "一阵冷风吹过来，你打了个寒颤——你穿着薄薄的上衣，没想到今天会降温。现在你的鼻子开始发酸——这可不是好兆头。",
            fatigueDelta = 3, healthDelta = -1,
            townCommentary = "一件单薄的上衣挡不住突然的降温。这不是你不够注意——是天气没跟你商量。回去翻翻衣柜——那件厚衣服还在等着你。"
        ),
        ClothingHealthEvent(
            id = "heavy_clothes_heat",
            title = "热得难受",
            description = "大热天你穿着厚重的衣服，汗珠顺着背往下淌。你的脑子有点闷——做什么都提不起劲。不是因为不努力——只是你的身体在抗议这件衣服。",
            fatigueDelta = 4,
            townCommentary = "在大热天穿厚衣服——你的身体比你诚实。换件轻薄的——工作效率会告诉你：你早该换了。"
        ),
        ClothingHealthEvent(
            id = "dirty_clothes_skin",
            title = "该洗了",
            description = "你已经好几天没换衣服了。镜子里的自己有点狼狈——衣领有点黄，袖口有点黑。更要紧的是——你的背开始冒痘了。",
            fungusRiskDelta = 0, fatigueDelta = 0,
            targetSkinCondition = SkinCondition.MILD_ACNE,
            townCommentary = "你忙得没时间洗衣服——这不怪你。但脏衣服不会因为你忙就放过你的皮肤。抽个空——把自己弄干净。做事的效率会告诉你：值得。"
        ),
        ClothingHealthEvent(
            id = "hand_peeling_water",
            title = "起皮的手",
            description = "你低头看自己的手——指腹上的皮翘起来了。不是一次两次的事——是长期碰水、洗冷水、做粗活留下来的。你的手替你承受了很多东西。",
            fatigueDelta = 1,
            targetSkinCondition = SkinCondition.HAND_PEELING,
            townCommentary = "手脱皮不是什么大事——但它提醒你：你的双手替你扛了太多。给自己买副手套、用温水洗碗——这不是矫情，是你值得被自己善待。"
        ),
        ClothingHealthEvent(
            id = "worn_clothes_rash",
            title = "磨破的衣服",
            description = "你的裤子膝盖那里磨薄了——今天走路的时候，布料擦得皮肤有点疼。你的衣服和你一样——已经在替你工作了太久。",
            fatigueDelta = 2, healthDelta = -1,
            townCommentary = "一件磨破的衣服不会拖垮你——但那些小磨擦积累起来，会让你对生活多一份不必要的疲惫。缝一缝，补一补——或者换一条新的。这不是浪费——是你在照顾自己。"
        )
    )
}

// ============================================
// 五、社交心理事件
// ============================================

/**
 * 穿戴社交心理事件
 */
data class ClothingSocialEvent(
    val id: String,
    val title: String,
    val description: String,
    val socialWillDelta: Int = 0,
    val anxietyDelta: Int = 0,
    val identityDelta: Int = 0,
    val resilienceDelta: Int = 0,
    val townCommentary: String
)

object ClothingSocialEvents {

    val events: List<ClothingSocialEvent> = listOf(
        ClothingSocialEvent(
            id = "shabby_prejudice",
            title = "别人的眼光",
            description = "你今天穿了一件旧衣服出门——早上你没觉得有什么问题。但在电梯里，你注意到同事的目光在你的衣袖上短暂地停了一下——然后移开了。那一眼很短——但你感觉到了。",
            socialWillDelta = -3, anxietyDelta = 3, identityDelta = -3,
            townCommentary = "一件旧衣服不会拉低你这个人——但别人的偏见会让你觉得自己被拉低了。那不是你的问题——是他们用衣服判断人的问题。而你——你这一天的价值，不在那件衣服上。"
        ),
        ClothingSocialEvent(
            id = "neat_impression",
            title = "干净的印象",
            description = "今天你穿了那件刚洗过的白衬衫。中午开会的时候，新来的同事多看了你一眼——是那种很自然的、因为整洁而产生的好感。你没做什么——就是让自己看起来干净。",
            socialWillDelta = 2, anxietyDelta = -1, identityDelta = 2,
            townCommentary = "干净不是你多做了什么——是你对自己的一种尊重。当这件尊重被另一个人看到的时候——它就成了你和一个陌生人之间的第一座桥。"
        ),
        ClothingSocialEvent(
            id = "luxury_anxiety",
            title = "舍不得的精致",
            description = "你穿着那件花钱买的好衣服——但一整天你都小心翼翼，怕弄脏了、怕蹭皱了、怕坐久了留下折痕。明明是花钱给自己买的好东西——为什么穿上去反而更紧张了？",
            socialWillDelta = 0, anxietyDelta = 5, identityDelta = -3,
            townCommentary = "好衣服是让你自在的——不是让你紧张的。如果你花了大价钱得到的是一件需要时刻小心对待的东西——那它是在穿你，不是你穿它。"
        ),
        ClothingSocialEvent(
            id = "simplicity_peace",
            title = "不在乎了",
            description = "你今天穿了最普通的那件衣服——没有任何装饰、没有任何品牌。你走在街上，没有人多看你一眼——但你也不在乎。你发现：当你不再试图用衣服去证明什么的时候——你反而很自在。",
            socialWillDelta = 3, anxietyDelta = -5, identityDelta = 5, resilienceDelta = 3,
            townCommentary = "你不再用衣服去敲世界的门了——你用自己的手去推。这种自在不是放弃——是你在告诉世界：我的价值不在衣服上。这是我身上最坚固的东西。"
        )
    )
}

// ============================================
// 六、天气-穿戴联动
// ============================================

/**
 * 天气环境对穿戴的影响
 */
object WeatherClothingLink {

    /**
     * 根据温度和湿度计算鞋袜潮湿风险
     */
    fun calculateDampnessRisk(temperature: Double, humidity: Double, isRainy: Boolean): Int {
        var risk = 0
        // 夏季高温+高湿 → 出汗潮湿
        if (temperature > 28 && humidity > 0.6) risk += 15
        // 雨天涉水 → 直接潮湿
        if (isRainy) risk += 25
        // 冰冷环境 → 湿冷风险
        if (temperature < 10 && humidity > 0.5) risk += 10
        return risk.coerceIn(0, 100)
    }

    /**
     * 根据温度判断穿着建议的保暖等级
     */
    fun recommendWarmth(temperature: Double): ClothingWarmth = when {
        temperature < 5 -> ClothingWarmth.HEAVY
        temperature < 15 -> ClothingWarmth.WARM
        temperature < 22 -> ClothingWarmth.MODERATE
        else -> ClothingWarmth.THIN
    }

    /**
     * 判断当前穿着是否匹配温度
     * 返回 true 表示穿着合适
     */
    fun isClothingAppropriate(currentWarmth: ClothingWarmth, temperature: Double): Boolean {
        val recommended = recommendWarmth(temperature)
        // 允许偏差一档
        return Math.abs(currentWarmth.warmthValue - recommended.warmthValue) <= 2
    }
}

// ============================================
// 七、洗衣相关
// ============================================

/**
 * 洗衣操作
 */
data class LaundryAction(
    /** 消耗工时（小时） */
    val timeCost: Double = 1.5,
    /** 需消耗休闲工时 */
    val leisureCost: Boolean = true,
    /** 洗衣后的洁净度 */
    val resultCleanliness: ClothingCleanliness = ClothingCleanliness.CLEAN
)

object LaundrySystem {
    /** 单次洗衣消耗 1.5 小时休闲时间 */
    val DEFAULT_LAUNDRY = LaundryAction(timeCost = 1.5, leisureCost = true)

    /** 洗衣后，所有穿戴位洁净度升级 */
    fun doLaundry(state: ClothingWearState): ClothingWearState {
        val cleaned = state.slots.mapValues { (_, slot) ->
            slot.copy(
                cleanliness = ClothingCleanliness.CLEAN,
                daysSinceLastWash = 0,
                dampness = 0
            )
        }
        return state.copy(
            slots = cleaned,
            overallCleanliness = ClothingCleanliness.CLEAN,
            hoursSinceLastLaundry = 0
        )
    }
}