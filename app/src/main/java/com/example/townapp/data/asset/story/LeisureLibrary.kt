package com.example.townapp.data

import com.example.townapp.data.model.WeatherState

/**
 * 休闲事件体系
 *
 * 小镇90%以上居民为工薪阶层，休闲内容按真实人口比例分配：
 * - 平民娱乐（21项）：占整体75%，日常高频触发
 * - 中产娱乐（6项）：占整体20%，需要攒钱，一年2-3次
 * - 富豪娱乐（4项）：占整体5%，普通人一生仅1-2次体验
 *
 * 核心原则：不评判任何休闲方式。散步和游轮，都是一个人选择让自己好过一点的方式。
 */

// ============================================
// 阶层分层
// ============================================
enum class LeisureTier(val label: String, val description: String) {
    COMMON("平民", "日常高频，开销极低，大多数人的大多数时间"),
    MIDDLE("中产", "需要攒钱，一年2-3次，阶段性奖励"),
    RICH("富豪", "普通人一生仅1-2次体验，常态化仅富人可选")
}

// ============================================
// 休闲时段分类
// ============================================
enum class LeisureCategory(val label: String, val description: String) {
    WEEKDAY_EVENING("工作日晚间", "下班后1-2小时短时活动"),
    WEEKEND("周末两日", "休息日，时间充裕，近郊为主"),
    SHORT_HOLIDAY("法定小长假", "3-4天假期，短途出游"),
    SEASONAL("四季专属", "季节性特定活动")
}

// ============================================
// 休闲事件效果
// ============================================
data class LeisureEffects(
    val anxietyDelta: Int = 0,          // 焦虑变化（负=降低焦虑）
    val lonelinessDelta: Int = 0,       // 孤独变化（负=降低孤独）
    val fatigueDelta: Int = 0,          // 疲惫变化（正=增加疲惫）
    val selfEsteemDelta: Int = 0,       // 自尊变化
    val happinessDelta: Int = 0,        // 快乐变化
    val healthRiskDelta: Double = 0.0,  // 疾病风险修正
    val nutritionDelta: Double = 0.0,   // 营养修正
    val socialFulfillmentDelta: Int = 0,// 社交满足度
    val awakeningPoints: Int = 0,       // 觉醒值
    val costMin: Double = 0.0,          // 最低消费
    val costMax: Double = 0.0           // 最高消费
)

// ============================================
// 休闲事件数据模型
// ============================================
data class LeisureEvent(
    val id: String,
    val name: String,
    val tier: LeisureTier,
    val category: LeisureCategory,
    val season: Season? = null,                 // 限定季节，null=全年
    val weatherBlocked: Set<WeatherState> = emptySet(),  // 恶劣天气禁止
    val weatherBoosted: WeatherState? = null,   // 最佳天气
    val minAge: Int = 0,                        // 最低年龄
    val savingsThreshold: Double = 0.0,         // 存款门槛
    val monthlyIncomeThreshold: Double = 0.0,   // 月收入门槛
    val fatigueThreshold: Int = 80,             // 疲惫值上限（超过则放弃）
    val outfitScene: OutfitScene? = null,       // 穿搭场景
    val duration: String = "",                  // 耗时描述
    val effects: LeisureEffects = LeisureEffects(),
    val triggerWeight: Double = 1.0,            // 触发权重
    val description: String = "",               // 事件描述
    val cognitiveTag: String = ""               // 关联认知条目（如"赛博奶嘴"）
)

// ============================================
// 季节
// ============================================
enum class Season(val label: String) {
    SPRING("春"), SUMMER("夏"), AUTUMN("秋"), WINTER("冬")
}

// ============================================
// 31条休闲事件库
// ============================================
object LeisureLibrary {

    // ============================================
    // 第一层：平民大众娱乐（21项）
    // ============================================

    // ---- 工作日晚间休闲（6项） ----
    val EVENING_WALK = LeisureEvent(
        id = "evening_walk",
        name = "小区楼下散步慢跑",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKDAY_EVENING,
        weatherBlocked = setOf(WeatherState.STORM),
        weatherBoosted = WeatherState.SUNNY,
        fatigueThreshold = 75,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "30-60分钟",
        effects = LeisureEffects(
            anxietyDelta = -3, lonelinessDelta = -2, fatigueDelta = -2,
            happinessDelta = 2, socialFulfillmentDelta = 1,
            healthRiskDelta = 0.0  // 雨天脚气风险由天气+穿搭联动处理
        ),
        triggerWeight = 10.0,
        description = "换上运动鞋，下楼走一圈。碰到邻居，点头打个招呼。风很舒服，今晚的月亮也不错。"
    )

    val EVENING_SHORT_VIDEO = LeisureEvent(
        id = "evening_short_video",
        name = "居家刷短视频追剧",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKDAY_EVENING,
        outfitScene = OutfitScene.HOME,
        duration = "1-2小时",
        effects = LeisureEffects(
            anxietyDelta = -4, happinessDelta = 3, fatigueDelta = 0,
            awakeningPoints = -1  // 长期占用学习时间
        ),
        triggerWeight = 8.0,
        description = "窝在沙发上，打开手机。一个接一个，不知不觉就刷了很久。放松是放松了，但明天还要早起。",
        cognitiveTag = "赛博奶嘴"
    )

    val EVENING_NIGHT_MARKET = LeisureEvent(
        id = "evening_night_market",
        name = "楼下夜市小吃摊闲逛",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKDAY_EVENING,
        weatherBlocked = setOf(WeatherState.STORM),
        weatherBoosted = WeatherState.SUNNY,
        fatigueThreshold = 70,
        outfitScene = OutfitScene.COMMUTE,
        duration = "1小时",
        effects = LeisureEffects(
            anxietyDelta = -2, lonelinessDelta = -3, happinessDelta = 3,
            nutritionDelta = 0.1, socialFulfillmentDelta = 2,
            costMin = 10.0, costMax = 30.0
        ),
        triggerWeight = 6.0,
        description = "夜市灯火通明，炒粉的香味飘了半条街。买一份烤串，坐在路边，看着来来往往的人——这就是活着的烟火气。"
    )

    val EVENING_GAMING = LeisureEvent(
        id = "evening_gaming",
        name = "居家棋牌线上游戏",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKDAY_EVENING,
        outfitScene = OutfitScene.HOME,
        duration = "1-3小时",
        effects = LeisureEffects(
            anxietyDelta = -3, lonelinessDelta = -3, happinessDelta = 4,
            awakeningPoints = -1, costMin = 0.0, costMax = 20.0
        ),
        triggerWeight = 5.0,
        description = "打开游戏，戴上耳机。虚拟世界里没有白天的烦恼——但你知道，回到现实，该做的事还在那里。",
        cognitiveTag = "赛博奶嘴"
    )

    val EVENING_SQUARE_DANCE = LeisureEvent(
        id = "evening_square_dance",
        name = "街边公园广场舞健身",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKDAY_EVENING,
        weatherBlocked = setOf(WeatherState.STORM, WeatherState.RAINY),
        minAge = 30,
        fatigueThreshold = 70,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "1小时",
        effects = LeisureEffects(
            anxietyDelta = -4, lonelinessDelta = -5, fatigueDelta = -1,
            happinessDelta = 3, socialFulfillmentDelta = 4
        ),
        triggerWeight = 3.0,
        description = "广场上音乐响起来了。阿姨们跳得热火朝天，你站在旁边跟着扭了两下——管它好不好看，开心就好。"
    )

    val EVENING_MARKET_SHOPPING = LeisureEvent(
        id = "evening_market_shopping",
        name = "晚间菜市场闲逛采购",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKDAY_EVENING,
        weatherBlocked = setOf(WeatherState.STORM),
        fatigueThreshold = 70,
        outfitScene = OutfitScene.COMMUTE,
        duration = "30-60分钟",
        effects = LeisureEffects(
            anxietyDelta = -1, happinessDelta = 1, nutritionDelta = 0.15,
            costMin = 15.0, costMax = 50.0
        ),
        triggerWeight = 4.0,
        description = "菜市场快收摊了，老板急着回家，给你多塞了两根葱。今晚的菜，比超市新鲜，还便宜。"
    )

    // ---- 周末两日休闲（6项） ----
    val WEEKEND_PARK_CAMPING = LeisureEvent(
        id = "weekend_park_camping",
        name = "城市免费公园露营野餐",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKEND,
        season = Season.SPRING,  // 春夏最佳，但全年可触发
        weatherBlocked = setOf(WeatherState.STORM),
        weatherBoosted = WeatherState.SUNNY,
        fatigueThreshold = 65,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "半天",
        effects = LeisureEffects(
            anxietyDelta = -5, lonelinessDelta = -3, happinessDelta = 5,
            fatigueDelta = 2, healthRiskDelta = 0.0,  // 暴晒/雨季皮炎由天气联动
            costMin = 5.0, costMax = 30.0
        ),
        triggerWeight = 8.0,
        description = "带上家里做的三明治，铺一块布在草地上。阳光从树叶间漏下来，你躺下来，觉得时间慢了下来。"
    )

    val WEEKEND_LIBRARY = LeisureEvent(
        id = "weekend_library",
        name = "图书馆看书博物馆参观",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKEND,
        outfitScene = OutfitScene.COMMUTE,
        duration = "2-4小时",
        effects = LeisureEffects(
            anxietyDelta = -5, lonelinessDelta = -1, happinessDelta = 2,
            selfEsteemDelta = 2, awakeningPoints = 3,
            costMin = 0.0, costMax = 0.0
        ),
        triggerWeight = 6.0,
        description = "图书馆很安静，只有翻书的声音。你随手抽了一本，坐在窗边读了一个下午。出来的时候，觉得脑子清爽了很多。"
    )

    val WEEKEND_COUNTRY_FAIR = LeisureEvent(
        id = "weekend_country_fair",
        name = "近郊乡镇一日赶集",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKEND,
        weatherBlocked = setOf(WeatherState.STORM),
        fatigueThreshold = 60,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "半天到一天",
        effects = LeisureEffects(
            anxietyDelta = -3, lonelinessDelta = -2, happinessDelta = 4,
            fatigueDelta = 3, nutritionDelta = 0.2,
            costMin = 10.0, costMax = 50.0
        ),
        triggerWeight = 4.0,
        description = "坐上公交车，晃晃悠悠到了乡下集市。老奶奶卖的土鸡蛋、现摘的蔬菜——比城里超市的好吃多了。"
    )

    val WEEKEND_CINEMA_BOARDGAME = LeisureEvent(
        id = "weekend_cinema_boardgame",
        name = "室内平价影院桌游馆",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKEND,
        fatigueThreshold = 70,
        outfitScene = OutfitScene.DATE,
        duration = "2-3小时",
        effects = LeisureEffects(
            anxietyDelta = -4, lonelinessDelta = -5, happinessDelta = 5,
            socialFulfillmentDelta = 4,
            costMin = 20.0, costMax = 80.0
        ),
        triggerWeight = 5.0,
        description = "和朋友约了下午场，票价便宜一半。看完电影出来，天还没黑，你们站在路边聊了很久——好久没这么开心了。"
    )

    val WEEKEND_FISHING = LeisureEvent(
        id = "weekend_fishing",
        name = "河边垂钓",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKEND,
        weatherBlocked = setOf(WeatherState.STORM),
        weatherBoosted = WeatherState.CLOUDY,
        minAge = 25,
        fatigueThreshold = 65,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "半天",
        effects = LeisureEffects(
            anxietyDelta = -6, lonelinessDelta = 0, happinessDelta = 3,
            fatigueDelta = 1, selfEsteemDelta = 1,
            costMin = 0.0, costMax = 30.0
        ),
        triggerWeight = 3.0,
        description = "坐在河边，甩下鱼竿。水面很平静，你的心也跟着沉下来。钓没钓到鱼不重要——重要的是，你终于听到了自己的呼吸声。"
    )

    val WEEKEND_CYCLING = LeisureEvent(
        id = "weekend_cycling",
        name = "城市绿道骑行",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.WEEKEND,
        weatherBlocked = setOf(WeatherState.STORM, WeatherState.RAINY),
        weatherBoosted = WeatherState.SUNNY,
        fatigueThreshold = 60,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "1-2小时",
        effects = LeisureEffects(
            anxietyDelta = -4, fatigueDelta = -1, happinessDelta = 4,
            healthRiskDelta = 0.0,  // 高温暴晒疲惫由天气联动
            costMin = 0.0, costMax = 5.0
        ),
        triggerWeight = 4.0,
        description = "扫一辆共享单车，沿着河边绿道骑。风吹过来，把一周的疲惫都吹散了。"
    )

    // ---- 法定小长假短途（3项） ----
    val HOLIDAY_DAY_TRIP = LeisureEvent(
        id = "holiday_day_trip",
        name = "周边县城一日往返大巴游",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.SHORT_HOLIDAY,
        weatherBlocked = setOf(WeatherState.STORM),
        weatherBoosted = WeatherState.SUNNY,
        fatigueThreshold = 55,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "一天",
        effects = LeisureEffects(
            anxietyDelta = -5, lonelinessDelta = -3, happinessDelta = 6,
            fatigueDelta = 4, socialFulfillmentDelta = 3,
            costMin = 30.0, costMax = 100.0
        ),
        triggerWeight = 5.0,
        description = "坐上早班大巴，当天往返。小县城有不一样的味道——一碗地道的小吃，一条不认识的街，就够了。"
    )

    val HOLIDAY_PICKING = LeisureEvent(
        id = "holiday_picking",
        name = "家庭近郊采摘园采摘",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.SHORT_HOLIDAY,
        season = Season.AUTUMN,
        weatherBlocked = setOf(WeatherState.STORM),
        weatherBoosted = WeatherState.SUNNY,
        fatigueThreshold = 60,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "半天",
        effects = LeisureEffects(
            anxietyDelta = -3, lonelinessDelta = -5, happinessDelta = 5,
            fatigueDelta = 2, nutritionDelta = 0.2,
            costMin = 30.0, costMax = 80.0
        ),
        triggerWeight = 4.0,
        description = "一家人钻进果园，摘了满满一篮子。孩子跑在前面，笑声洒了一路。这些果子比超市的甜——可能是因为亲手摘的。"
    )

    val HOLIDAY_FAMILY_VISIT = LeisureEvent(
        id = "holiday_family_visit",
        name = "乡村亲友串门探亲",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.SHORT_HOLIDAY,
        weatherBlocked = setOf(WeatherState.STORM),
        fatigueThreshold = 55,
        outfitScene = OutfitScene.COMMUTE,
        duration = "1-2天",
        effects = LeisureEffects(
            anxietyDelta = -2, lonelinessDelta = -6, happinessDelta = 4,
            fatigueDelta = 3, socialFulfillmentDelta = 5,
            costMin = 20.0, costMax = 100.0
        ),
        triggerWeight = 5.0,
        description = "回到乡下老家，长辈在门口等着。桌上摆满了菜，他们不停往你碗里夹。话还是那些老话——但听着听着，心里就暖了。",
        cognitiveTag = "长辈俗语触发"
    )

    // ---- 四季季节性专属（4项） ----
    val SUMMER_SWIMMING = LeisureEvent(
        id = "summer_swimming",
        name = "城市公共泳池游泳",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.SEASONAL,
        season = Season.SUMMER,
        weatherBoosted = WeatherState.SUNNY,
        fatigueThreshold = 65,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "1-2小时",
        effects = LeisureEffects(
            anxietyDelta = -4, fatigueDelta = -2, happinessDelta = 5,
            healthRiskDelta = 0.05,  // 泳池潮湿→真菌风险
            costMin = 10.0, costMax = 30.0
        ),
        triggerWeight = 6.0,
        description = "一头扎进水里，整个世界都安静了。游完出来，浑身清爽——夏天就该这样过。"
    )

    val WINTER_INDOOR_SPORTS = LeisureEvent(
        id = "winter_indoor_sports",
        name = "室内公共体育馆打球",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.SEASONAL,
        season = Season.WINTER,
        fatigueThreshold = 60,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "1-2小时",
        effects = LeisureEffects(
            anxietyDelta = -4, fatigueDelta = -1, lonelinessDelta = -3,
            happinessDelta = 4, socialFulfillmentDelta = 3,
            costMin = 10.0, costMax = 30.0
        ),
        triggerWeight = 5.0,
        description = "外面风大雪大，体育馆里暖烘烘的。打了一场球，出了一身汗——冬天也需要动一动。"
    )

    val AUTUMN_HIKING = LeisureEvent(
        id = "autumn_hiking",
        name = "郊外山野徒步",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.SEASONAL,
        season = Season.AUTUMN,
        weatherBlocked = setOf(WeatherState.STORM),
        weatherBoosted = WeatherState.SUNNY,
        fatigueThreshold = 55,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "半天",
        effects = LeisureEffects(
            anxietyDelta = -5, fatigueDelta = 3, happinessDelta = 5,
            selfEsteemDelta = 2, costMin = 0.0, costMax = 20.0
        ),
        triggerWeight = 6.0,
        description = "秋天的山，颜色最丰富。踩着落叶往上走，空气凉凉的，肺里都是草木的味道。爬到山顶，觉得一切都值了。"
    )

    val FESTIVAL_FAMILY_DINNER = LeisureEvent(
        id = "festival_family_dinner",
        name = "春节中秋家庭聚餐",
        tier = LeisureTier.COMMON,
        category = LeisureCategory.SEASONAL,
        season = Season.WINTER,  // 春节为主
        outfitScene = OutfitScene.DATE,
        duration = "半天到一天",
        effects = LeisureEffects(
            anxietyDelta = -2, lonelinessDelta = -8, happinessDelta = 6,
            socialFulfillmentDelta = 8, nutritionDelta = 0.3,
            costMin = 50.0, costMax = 200.0
        ),
        triggerWeight = 8.0,
        description = "一家人围坐在桌前，菜摆得满满当当。窗外鞭炮声此起彼伏，你看着身边这些面孔——这就是你所有的底气。"
    )

    // ============================================
    // 第二层：中产休闲项目（6项）
    // ============================================
    val MIDDLE_ROAD_TRIP = LeisureEvent(
        id = "middle_road_trip",
        name = "省内自驾游",
        tier = LeisureTier.MIDDLE,
        category = LeisureCategory.WEEKEND,
        weatherBlocked = setOf(WeatherState.STORM),
        savingsThreshold = 3000.0,
        monthlyIncomeThreshold = 5000.0,
        fatigueThreshold = 50,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "2-3天",
        effects = LeisureEffects(
            anxietyDelta = -8, lonelinessDelta = -4, happinessDelta = 8,
            fatigueDelta = 3, socialFulfillmentDelta = 4,
            costMin = 300.0, costMax = 800.0
        ),
        triggerWeight = 2.0,
        description = "租一辆车，周五下班就出发。两天时间，把省内的山路、海边、小镇都走了一遍。回来的时候，虽然累，但心里装满了风景。"
    )

    val MIDDLE_BEACH = LeisureEvent(
        id = "middle_beach",
        name = "海滨城市沙滩度假",
        tier = LeisureTier.MIDDLE,
        category = LeisureCategory.SHORT_HOLIDAY,
        season = Season.SUMMER,
        savingsThreshold = 4000.0,
        monthlyIncomeThreshold = 6000.0,
        fatigueThreshold = 50,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "2-3天",
        effects = LeisureEffects(
            anxietyDelta = -8, lonelinessDelta = -3, happinessDelta = 9,
            fatigueDelta = 2, healthRiskDelta = 0.03,
            costMin = 500.0, costMax = 1500.0
        ),
        triggerWeight = 2.0,
        description = "海风咸咸的，沙滩软软的。你躺在遮阳伞下，听着海浪声——觉得攒了半年的钱，值了。"
    )

    val MIDDLE_RIVER_CRUISE = LeisureEvent(
        id = "middle_river_cruise",
        name = "沿江内河观光游轮",
        tier = LeisureTier.MIDDLE,
        category = LeisureCategory.SHORT_HOLIDAY,
        savingsThreshold = 5000.0,
        monthlyIncomeThreshold = 7000.0,
        fatigueThreshold = 50,
        outfitScene = OutfitScene.DATE,
        duration = "3-4天",
        effects = LeisureEffects(
            anxietyDelta = -9, lonelinessDelta = -2, happinessDelta = 8,
            fatigueDelta = 1, selfEsteemDelta = 2,
            costMin = 800.0, costMax = 2000.0
        ),
        triggerWeight = 1.5,
        description = "船缓缓驶过三峡，两岸青山如画。你站在甲板上，风吹过来——觉得这辈子，能看一眼这样的风景，就够了。"
    )

    val MIDDLE_SCENIC_SPOT = LeisureEvent(
        id = "middle_scenic_spot",
        name = "收费风景区深度游",
        tier = LeisureTier.MIDDLE,
        category = LeisureCategory.SHORT_HOLIDAY,
        weatherBlocked = setOf(WeatherState.STORM),
        savingsThreshold = 3000.0,
        monthlyIncomeThreshold = 5000.0,
        fatigueThreshold = 50,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "2-3天",
        effects = LeisureEffects(
            anxietyDelta = -7, lonelinessDelta = -3, happinessDelta = 7,
            fatigueDelta = 4, selfEsteemDelta = 1,
            costMin = 300.0, costMax = 1000.0
        ),
        triggerWeight = 2.0,
        description = "买了门票，坐了缆车。站在观景台上，群山在你脚下——你拍了张照片，发给了很久没联系的朋友。"
    )

    val MIDDLE_EQUESTRIAN = LeisureEvent(
        id = "middle_equestrian",
        name = "马术高尔夫单次体验",
        tier = LeisureTier.MIDDLE,
        category = LeisureCategory.WEEKEND,
        savingsThreshold = 5000.0,
        monthlyIncomeThreshold = 8000.0,
        fatigueThreshold = 55,
        outfitScene = OutfitScene.BUSINESS,
        duration = "半天",
        effects = LeisureEffects(
            anxietyDelta = -5, lonelinessDelta = -1, happinessDelta = 6,
            selfEsteemDelta = 3, socialFulfillmentDelta = 2,
            costMin = 300.0, costMax = 800.0
        ),
        triggerWeight = 1.0,
        description = "第一次握马球杆，姿势笨拙得自己都想笑。但教练说没关系，每个人都从零开始。今天你不是来和人比的——是来体验的。"
    )

    val MIDDLE_HOT_SPRING = LeisureEvent(
        id = "middle_hot_spring",
        name = "城市周边温泉度假村",
        tier = LeisureTier.MIDDLE,
        category = LeisureCategory.WEEKEND,
        season = Season.WINTER,
        savingsThreshold = 3000.0,
        monthlyIncomeThreshold = 5000.0,
        fatigueThreshold = 50,
        outfitScene = OutfitScene.DATE,
        duration = "1-2天",
        effects = LeisureEffects(
            anxietyDelta = -7, fatigueDelta = -3, happinessDelta = 7,
            healthRiskDelta = -0.02,  // 温泉缓解疲劳
            costMin = 200.0, costMax = 600.0
        ),
        triggerWeight = 2.0,
        description = "泡在温泉里，热气氤氲。冬天的寒意被挡在外面，你觉得骨头缝里的疲惫都化开了。"
    )

    // ============================================
    // 第三层：富豪高端娱乐（4项）
    // ============================================
    val RICH_SKY_DIVING = LeisureEvent(
        id = "rich_sky_diving",
        name = "高空跳伞蹦极极限运动",
        tier = LeisureTier.RICH,
        category = LeisureCategory.SHORT_HOLIDAY,
        savingsThreshold = 20000.0,
        monthlyIncomeThreshold = 20000.0,
        fatigueThreshold = 50,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "半天",
        effects = LeisureEffects(
            anxietyDelta = -10, happinessDelta = 10, selfEsteemDelta = 5,
            costMin = 2000.0, costMax = 5000.0
        ),
        triggerWeight = 0.3,
        description = "从飞机舱门跳下去的那一刻，心脏几乎停跳。然后——风灌满了耳朵，世界在你脚下展开。这辈子，值了。"
    )

    val RICH_CRUISE = LeisureEvent(
        id = "rich_cruise",
        name = "远洋邮轮私人海岛度假",
        tier = LeisureTier.RICH,
        category = LeisureCategory.SHORT_HOLIDAY,
        savingsThreshold = 50000.0,
        monthlyIncomeThreshold = 30000.0,
        fatigueThreshold = 50,
        outfitScene = OutfitScene.DATE,
        duration = "5-10天",
        effects = LeisureEffects(
            anxietyDelta = -12, lonelinessDelta = -2, happinessDelta = 12,
            fatigueDelta = -5, selfEsteemDelta = 4,
            costMin = 5000.0, costMax = 20000.0
        ),
        triggerWeight = 0.2,
        description = "邮轮缓缓驶离港口，海面一望无际。你躺在甲板躺椅上，手里端着一杯饮料——人生有几个这样的时刻？"
    )

    val RICH_MOUNTAIN = LeisureEvent(
        id = "rich_mountain",
        name = "雪山专业攀登",
        tier = LeisureTier.RICH,
        category = LeisureCategory.SHORT_HOLIDAY,
        savingsThreshold = 30000.0,
        monthlyIncomeThreshold = 25000.0,
        fatigueThreshold = 40,
        outfitScene = OutfitScene.OUTDOOR,
        duration = "5-7天",
        effects = LeisureEffects(
            anxietyDelta = -10, happinessDelta = 10, selfEsteemDelta = 6,
            fatigueDelta = 8, healthRiskDelta = 0.05,
            costMin = 3000.0, costMax = 10000.0
        ),
        triggerWeight = 0.2,
        description = "站在雪山之巅，云在脚下，天地一片苍茫。你大口喘着气，眼泪和汗水混在一起——不是累的，是感动的。"
    )

    val RICH_WINE_PARTY = LeisureEvent(
        id = "rich_wine_party",
        name = "私人酒会直升机观光",
        tier = LeisureTier.RICH,
        category = LeisureCategory.WEEKEND,
        savingsThreshold = 30000.0,
        monthlyIncomeThreshold = 30000.0,
        fatigueThreshold = 50,
        outfitScene = OutfitScene.BUSINESS,
        duration = "半天",
        effects = LeisureEffects(
            anxietyDelta = -6, happinessDelta = 8, selfEsteemDelta = 5,
            socialFulfillmentDelta = 4,
            costMin = 2000.0, costMax = 8000.0
        ),
        triggerWeight = 0.2,
        description = "直升机从楼顶起飞，整个城市在你脚下展开。你端着一杯香槟，看着窗外——突然觉得，努力了这么多年，也不是没有回报。"
    )

    // ============================================
    // 全部事件列表
    // ============================================
    val ALL_COMMON: List<LeisureEvent> = listOf(
        EVENING_WALK, EVENING_SHORT_VIDEO, EVENING_NIGHT_MARKET,
        EVENING_GAMING, EVENING_SQUARE_DANCE, EVENING_MARKET_SHOPPING,
        WEEKEND_PARK_CAMPING, WEEKEND_LIBRARY, WEEKEND_COUNTRY_FAIR,
        WEEKEND_CINEMA_BOARDGAME, WEEKEND_FISHING, WEEKEND_CYCLING,
        HOLIDAY_DAY_TRIP, HOLIDAY_PICKING, HOLIDAY_FAMILY_VISIT,
        SUMMER_SWIMMING, WINTER_INDOOR_SPORTS, AUTUMN_HIKING,
        FESTIVAL_FAMILY_DINNER
    )

    val ALL_MIDDLE: List<LeisureEvent> = listOf(
        MIDDLE_ROAD_TRIP, MIDDLE_BEACH, MIDDLE_RIVER_CRUISE,
        MIDDLE_SCENIC_SPOT, MIDDLE_EQUESTRIAN, MIDDLE_HOT_SPRING
    )

    val ALL_RICH: List<LeisureEvent> = listOf(
        RICH_SKY_DIVING, RICH_CRUISE, RICH_MOUNTAIN, RICH_WINE_PARTY
    )

    val ALL_EVENTS: List<LeisureEvent> = ALL_COMMON + ALL_MIDDLE + ALL_RICH

    fun getById(id: String): LeisureEvent? = ALL_EVENTS.find { it.id == id }

    fun getByTier(tier: LeisureTier): List<LeisureEvent> = when (tier) {
        LeisureTier.COMMON -> ALL_COMMON
        LeisureTier.MIDDLE -> ALL_MIDDLE
        LeisureTier.RICH -> ALL_RICH
    }

    fun getByCategory(category: LeisureCategory): List<LeisureEvent> =
        ALL_EVENTS.filter { it.category == category }

    fun getBySeason(season: Season): List<LeisureEvent> =
        ALL_EVENTS.filter { it.season == null || it.season == season }
}