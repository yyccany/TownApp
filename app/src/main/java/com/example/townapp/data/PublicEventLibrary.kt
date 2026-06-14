package com.example.townapp.data

import com.example.townapp.data.model.WeatherState

/**
 * 大众群体性公共休闲事件体系
 *
 * 庙会、演唱会、世界杯、追星、音乐节、城市节庆——
 * 这些是跨越阶层的集体文化时刻。
 * 世界杯期间，不管你在城中村出租屋用手机看，还是在VIP包厢看现场，
 * 那一刻你和全世界的人一起屏住呼吸。
 *
 * 核心原则：
 * - 全民可参与的普惠型活动，富人买VIP，普通人买普通票
 * - 阶层差异体现在消费档位，基础体验人人平等
 * - 不强制参与，社恐可以宅家，社牛可以出门
 */

// ============================================
// 公共事件类型
// ============================================
enum class PublicEventType(val label: String, val description: String) {
    TEMPLE_FAIR("传统庙会", "春节、中秋的民俗集市，全家老少一起逛"),
    CONCERT("演唱会", "偶像巡回演唱会，年轻人的集体情绪释放"),
    MUSIC_FESTIVAL("露天音乐节", "草地、舞台、人群，夏天的集体狂欢"),
    WORLD_CUP("世界杯赛事", "四年一届，全世界的足球盛宴"),
    CITY_SPORTS("城市球赛", "常态化城市联赛，周末街边观赛"),
    FAN_IDOL("追星应援", "粉丝社群活动，理性或狂热的双向路径"),
    CITY_FESTIVAL("城市节庆", "夏日夜市、烟花节、灯光节，全民免费"),
    FIREWORK_SHOW("烟花节", "节日烟花汇演，全家出游的温馨时刻")
}

// ============================================
// 公共事件消费档位
// ============================================
data class PublicEventTier(
    val tier: LeisureTier,
    val costMin: Double,
    val costMax: Double,
    val outfitScene: OutfitScene,
    val effects: LeisureEffects,
    val description: String,
    val savingsThreshold: Double = 0.0,
    val monthlyIncomeThreshold: Double = 0.0
)

// ============================================
// 公共事件数据模型
// ============================================
data class PublicEvent(
    val id: String,
    val name: String,
    val type: PublicEventType,
    val frequency: String,
    val season: Season? = null,
    val months: List<Int> = emptyList(),
    val weatherBlocked: Set<WeatherState> = emptySet(),
    val weatherBoosted: WeatherState? = null,
    val minAge: Int = 0,
    val maxAge: Int = 120,
    val tiers: Map<LeisureTier, PublicEventTier>,
    val defaultWeight: Double = 1.0,
    val activeWeight: Double = 2.0,              // 活跃期间权重
    val description: String = "",
    val cognitiveTags: List<String> = emptyList(),
    val isFourYearCycle: Boolean = false,         // 是否四年一届
    val cycleYearOffset: Int = 0                  // 周期偏移（2022世界杯=2）
)

// ============================================
// 公共事件库
// ============================================
object PublicEventLibrary {

    // ============================================
    // 1. 庙会（春节、中秋）
    // ============================================
    val TEMPLE_FAIR = PublicEvent(
        id = "temple_fair",
        name = "春节/中秋庙会",
        type = PublicEventType.TEMPLE_FAIR,
        frequency = "每年春节、中秋，一年2-3次",
        months = listOf(1, 2, 9),
        weatherBlocked = setOf(WeatherState.STORM),
        minAge = 0,
        tiers = mapOf(
            LeisureTier.COMMON to PublicEventTier(
                tier = LeisureTier.COMMON,
                costMin = 10.0, costMax = 50.0,
                outfitScene = OutfitScene.COMMUTE,
                effects = LeisureEffects(
                    anxietyDelta = -3, lonelinessDelta = -5, happinessDelta = 5,
                    fatigueDelta = 3, nutritionDelta = 0.15,
                    socialFulfillmentDelta = 4, healthRiskDelta = 0.02
                ),
                description = "逛庙会集市、街边小吃、民俗杂耍。人挤人，但热闹。"
            ),
            LeisureTier.MIDDLE to PublicEventTier(
                tier = LeisureTier.MIDDLE,
                costMin = 50.0, costMax = 200.0,
                outfitScene = OutfitScene.DATE,
                savingsThreshold = 2000.0,
                effects = LeisureEffects(
                    anxietyDelta = -4, lonelinessDelta = -4, happinessDelta = 6,
                    fatigueDelta = 2, selfEsteemDelta = 1,
                    socialFulfillmentDelta = 3
                ),
                description = "付费非遗展馆、精品文创摊位。人少了很多，可以慢慢逛。"
            ),
            LeisureTier.RICH to PublicEventTier(
                tier = LeisureTier.RICH,
                costMin = 500.0, costMax = 2000.0,
                outfitScene = OutfitScene.BUSINESS,
                savingsThreshold = 10000.0, monthlyIncomeThreshold = 15000.0,
                effects = LeisureEffects(
                    anxietyDelta = -5, happinessDelta = 7, selfEsteemDelta = 3,
                    socialFulfillmentDelta = 2
                ),
                description = "包场私人民俗观赏。安静、私密，但少了点庙会的烟火气。"
            )
        ),
        defaultWeight = 3.0,
        activeWeight = 8.0,
        description = "庙会灯笼高挂，糖葫芦的叫卖声此起彼伏。长辈走在前面，你跟在后面——好像回到了小时候。",
        cognitiveTags = listOf("传统俗语", "代际观念")
    )

    // ============================================
    // 2. 演唱会
    // ============================================
    val CONCERT = PublicEvent(
        id = "concert",
        name = "偶像巡回演唱会",
        type = PublicEventType.CONCERT,
        frequency = "每年夏季、年末，不定期举办",
        months = listOf(6, 7, 8, 12),
        season = Season.SUMMER,
        weatherBlocked = setOf(WeatherState.STORM),
        minAge = 15,
        maxAge = 50,
        tiers = mapOf(
            LeisureTier.COMMON to PublicEventTier(
                tier = LeisureTier.COMMON,
                costMin = 80.0, costMax = 300.0,
                outfitScene = OutfitScene.COMMUTE,
                effects = LeisureEffects(
                    anxietyDelta = -7, lonelinessDelta = -4, happinessDelta = 8,
                    fatigueDelta = 4, socialFulfillmentDelta = 3
                ),
                description = "看台平价门票。虽然离舞台远了点，但大屏幕很清楚，全场一起唱的时候，你和所有人一样激动。"
            ),
            LeisureTier.MIDDLE to PublicEventTier(
                tier = LeisureTier.MIDDLE,
                costMin = 300.0, costMax = 1000.0,
                outfitScene = OutfitScene.DATE,
                savingsThreshold = 3000.0,
                effects = LeisureEffects(
                    anxietyDelta = -8, lonelinessDelta = -3, happinessDelta = 9,
                    fatigueDelta = 3, selfEsteemDelta = 2,
                    socialFulfillmentDelta = 4
                ),
                description = "内场门票。能看到偶像的表情，值了。"
            ),
            LeisureTier.RICH to PublicEventTier(
                tier = LeisureTier.RICH,
                costMin = 1500.0, costMax = 5000.0,
                outfitScene = OutfitScene.BUSINESS,
                savingsThreshold = 10000.0, monthlyIncomeThreshold = 15000.0,
                effects = LeisureEffects(
                    anxietyDelta = -9, happinessDelta = 10, selfEsteemDelta = 4,
                    socialFulfillmentDelta = 5
                ),
                description = "VIP前排 + 后台见面会。你和偶像击了掌——这大概是你今年最开心的一天。"
            )
        ),
        defaultWeight = 2.0,
        activeWeight = 5.0,
        description = "灯光暗下来，全场尖叫。前奏响起的瞬间，你忘了所有烦恼——这一刻，你只是你自己。",
        cognitiveTags = listOf("消费主义", "追星理性")
    )

    // ============================================
    // 3. 露天音乐节
    // ============================================
    val MUSIC_FESTIVAL = PublicEvent(
        id = "music_festival",
        name = "夏季露天音乐节",
        type = PublicEventType.MUSIC_FESTIVAL,
        frequency = "每年夏季集中举办",
        months = listOf(6, 7, 8),
        season = Season.SUMMER,
        weatherBlocked = setOf(WeatherState.STORM),
        weatherBoosted = WeatherState.SUNNY,
        minAge = 16,
        maxAge = 45,
        tiers = mapOf(
            LeisureTier.COMMON to PublicEventTier(
                tier = LeisureTier.COMMON,
                costMin = 50.0, costMax = 150.0,
                outfitScene = OutfitScene.OUTDOOR,
                effects = LeisureEffects(
                    anxietyDelta = -6, lonelinessDelta = -5, happinessDelta = 7,
                    fatigueDelta = 5, healthRiskDelta = 0.03,
                    socialFulfillmentDelta = 5
                ),
                description = "早鸟票，和朋友拼车去。草地上席地而坐，音乐响起来的时候，一切都值了。"
            ),
            LeisureTier.MIDDLE to PublicEventTier(
                tier = LeisureTier.MIDDLE,
                costMin = 200.0, costMax = 500.0,
                outfitScene = OutfitScene.DATE,
                savingsThreshold = 2000.0,
                effects = LeisureEffects(
                    anxietyDelta = -7, lonelinessDelta = -4, happinessDelta = 8,
                    fatigueDelta = 4, selfEsteemDelta = 1,
                    socialFulfillmentDelta = 4
                ),
                description = "全价票，精心准备了穿搭妆容。今天你很漂亮。"
            ),
            LeisureTier.RICH to PublicEventTier(
                tier = LeisureTier.RICH,
                costMin = 800.0, costMax = 3000.0,
                outfitScene = OutfitScene.BUSINESS,
                savingsThreshold = 8000.0, monthlyIncomeThreshold = 12000.0,
                effects = LeisureEffects(
                    anxietyDelta = -8, happinessDelta = 9, selfEsteemDelta = 3,
                    socialFulfillmentDelta = 4
                ),
                description = "VIP专区 + 后台通行证。你站在侧台看演出——这个视角，大多数人一辈子体验不到。"
            )
        ),
        defaultWeight = 2.0,
        activeWeight = 6.0,
        description = "草地、舞台、人群。夏天的风把音乐吹得很远，你站在人群里——觉得年轻真好。",
        cognitiveTags = listOf("社交破圈", "集体记忆")
    )

    // ============================================
    // 4. 世界杯（四年一届）
    // ============================================
    val WORLD_CUP = PublicEvent(
        id = "world_cup",
        name = "世界杯赛事",
        type = PublicEventType.WORLD_CUP,
        frequency = "四年一届，赛事持续一个月",
        months = listOf(6, 7),
        season = Season.SUMMER,
        minAge = 12,
        tiers = mapOf(
            LeisureTier.COMMON to PublicEventTier(
                tier = LeisureTier.COMMON,
                costMin = 0.0, costMax = 30.0,
                outfitScene = OutfitScene.HOME,
                effects = LeisureEffects(
                    anxietyDelta = -4, lonelinessDelta = -3, happinessDelta = 6,
                    fatigueDelta = 2, socialFulfillmentDelta = 3
                ),
                description = "居家线上直播观赛，街边大排档和球迷一起看球。熬夜看球，第二天上班有点困——但值了。"
            ),
            LeisureTier.MIDDLE to PublicEventTier(
                tier = LeisureTier.MIDDLE,
                costMin = 50.0, costMax = 200.0,
                outfitScene = OutfitScene.COMMUTE,
                savingsThreshold = 2000.0,
                effects = LeisureEffects(
                    anxietyDelta = -5, lonelinessDelta = -4, happinessDelta = 7,
                    fatigueDelta = 2, socialFulfillmentDelta = 5
                ),
                description = "线下观赛派对、付费观赛包间。大屏幕、啤酒、朋友——这才是看球。"
            ),
            LeisureTier.RICH to PublicEventTier(
                tier = LeisureTier.RICH,
                costMin = 5000.0, costMax = 20000.0,
                outfitScene = OutfitScene.BUSINESS,
                savingsThreshold = 30000.0, monthlyIncomeThreshold = 20000.0,
                effects = LeisureEffects(
                    anxietyDelta = -8, happinessDelta = 10, selfEsteemDelta = 5,
                    socialFulfillmentDelta = 6
                ),
                description = "出国现场观赛。你坐在球场里，听见几万人一起呐喊——这辈子，你终于在现场看了一场世界杯。"
            )
        ),
        defaultWeight = 0.5,
        activeWeight = 7.0,
        description = "四年一次。不管看不看球，那一个月街头巷尾都在讨论。你熬夜看了一场——主队赢了，你激动得睡不着。",
        cognitiveTags = listOf("集体记忆", "赛事狂热"),
        isFourYearCycle = true,
        cycleYearOffset = 2
    )

    // ============================================
    // 5. 城市球赛
    // ============================================
    val CITY_SPORTS = PublicEvent(
        id = "city_sports",
        name = "城市篮球足球联赛",
        type = PublicEventType.CITY_SPORTS,
        frequency = "赛季期间周末常态化举办",
        months = listOf(3, 4, 5, 9, 10, 11),
        weatherBlocked = setOf(WeatherState.STORM),
        minAge = 12,
        tiers = mapOf(
            LeisureTier.COMMON to PublicEventTier(
                tier = LeisureTier.COMMON,
                costMin = 0.0, costMax = 20.0,
                outfitScene = OutfitScene.COMMUTE,
                effects = LeisureEffects(
                    anxietyDelta = -3, lonelinessDelta = -3, happinessDelta = 4,
                    socialFulfillmentDelta = 3
                ),
                description = "周末街边观赛，免费或低价。和身边的人一起喊——不认识也没关系，这一刻你们是同一队的。"
            ),
            LeisureTier.MIDDLE to PublicEventTier(
                tier = LeisureTier.MIDDLE,
                costMin = 30.0, costMax = 100.0,
                outfitScene = OutfitScene.COMMUTE,
                savingsThreshold = 1000.0,
                effects = LeisureEffects(
                    anxietyDelta = -4, lonelinessDelta = -3, happinessDelta = 5,
                    socialFulfillmentDelta = 4
                ),
                description = "球场看台票。离球场更近，看得更清楚。"
            )
        ),
        defaultWeight = 2.0,
        activeWeight = 3.0,
        description = "周末下午，球场传来欢呼声。你路过的时候停下来看了一会儿——好久没这么热血了。"
    )

    // ============================================
    // 6. 追星应援
    // ============================================
    val FAN_IDOL = PublicEvent(
        id = "fan_idol",
        name = "追星应援活动",
        type = PublicEventType.FAN_IDOL,
        frequency = "随偶像活动不定期举办",
        months = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
        minAge = 14,
        maxAge = 40,
        tiers = mapOf(
            LeisureTier.COMMON to PublicEventTier(
                tier = LeisureTier.COMMON,
                costMin = 20.0, costMax = 100.0,
                outfitScene = OutfitScene.COMMUTE,
                effects = LeisureEffects(
                    anxietyDelta = -4, lonelinessDelta = -5, happinessDelta = 5,
                    socialFulfillmentDelta = 4
                ),
                description = "理性追星：一年1-2场，周边预算可控。同好社群一起应援——你找到了和自己一样的人。"
            ),
            LeisureTier.MIDDLE to PublicEventTier(
                tier = LeisureTier.MIDDLE,
                costMin = 200.0, costMax = 800.0,
                outfitScene = OutfitScene.DATE,
                savingsThreshold = 2000.0,
                effects = LeisureEffects(
                    anxietyDelta = -3, lonelinessDelta = -4, happinessDelta = 6,
                    fatigueDelta = 3, socialFulfillmentDelta = 5,
                    awakeningPoints = -1
                ),
                description = "狂热追星：频繁抢购高价门票、大额周边。透支存款，熬夜蹲直播——你很快乐，但储蓄在慢慢变少。"
            ),
            LeisureTier.RICH to PublicEventTier(
                tier = LeisureTier.RICH,
                costMin = 1000.0, costMax = 5000.0,
                outfitScene = OutfitScene.BUSINESS,
                savingsThreshold = 10000.0, monthlyIncomeThreshold = 12000.0,
                effects = LeisureEffects(
                    anxietyDelta = -6, happinessDelta = 8, selfEsteemDelta = 3,
                    socialFulfillmentDelta = 4
                ),
                description = "全款承包前排场次。你坐在最好的位置，灯光照过来的时候——你觉得这一切都值得。"
            )
        ),
        defaultWeight = 1.5,
        activeWeight = 3.0,
        description = "每个人都需要一点光。追星，是你在平凡日子里为自己点的一盏灯。",
        cognitiveTags = listOf("消费主义", "追星理性", "自我认同")
    )

    // ============================================
    // 7. 城市节庆（夏日夜市、灯光节）
    // ============================================
    val CITY_FESTIVAL = PublicEvent(
        id = "city_festival",
        name = "城市夏日夜市/灯光节",
        type = PublicEventType.CITY_FESTIVAL,
        frequency = "春夏秋季常态化举办",
        months = listOf(5, 6, 7, 8, 9, 10),
        season = Season.SUMMER,
        weatherBlocked = setOf(WeatherState.STORM),
        weatherBoosted = WeatherState.SUNNY,
        minAge = 0,
        tiers = mapOf(
            LeisureTier.COMMON to PublicEventTier(
                tier = LeisureTier.COMMON,
                costMin = 0.0, costMax = 30.0,
                outfitScene = OutfitScene.COMMUTE,
                effects = LeisureEffects(
                    anxietyDelta = -3, lonelinessDelta = -4, happinessDelta = 4,
                    fatigueDelta = 2, socialFulfillmentDelta = 3
                ),
                description = "傍晚散步顺路参与，花销极低。夜市灯火通明，人来人往——这就是活着的烟火气。"
            ),
            LeisureTier.MIDDLE to PublicEventTier(
                tier = LeisureTier.MIDDLE,
                costMin = 30.0, costMax = 100.0,
                outfitScene = OutfitScene.DATE,
                savingsThreshold = 1000.0,
                effects = LeisureEffects(
                    anxietyDelta = -4, lonelinessDelta = -3, happinessDelta = 5,
                    socialFulfillmentDelta = 4
                ),
                description = "买了点文创小物件，坐在夜市摊位上吃了一碗面。灯光很美，今晚的风也很温柔。"
            )
        ),
        defaultWeight = 3.0,
        activeWeight = 5.0,
        description = "夜市一条街，灯火通明。你端着一杯柠檬茶，慢悠悠地走——日子就该这样，不紧不慢。"
    )

    // ============================================
    // 8. 烟花节
    // ============================================
    val FIREWORK_SHOW = PublicEvent(
        id = "firework_show",
        name = "节日烟花汇演",
        type = PublicEventType.FIREWORK_SHOW,
        frequency = "春节、国庆等重大节日举办",
        months = listOf(1, 2, 10),
        weatherBlocked = setOf(WeatherState.STORM, WeatherState.RAINY),
        weatherBoosted = WeatherState.CLOUDY,
        minAge = 0,
        tiers = mapOf(
            LeisureTier.COMMON to PublicEventTier(
                tier = LeisureTier.COMMON,
                costMin = 0.0, costMax = 20.0,
                outfitScene = OutfitScene.COMMUTE,
                effects = LeisureEffects(
                    anxietyDelta = -4, lonelinessDelta = -6, happinessDelta = 6,
                    fatigueDelta = 2, socialFulfillmentDelta = 5
                ),
                description = "全家挤在人群里，仰头看烟花。孩子骑在爸爸肩上，你站在旁边——觉得这就是幸福。"
            ),
            LeisureTier.MIDDLE to PublicEventTier(
                tier = LeisureTier.MIDDLE,
                costMin = 50.0, costMax = 200.0,
                outfitScene = OutfitScene.DATE,
                savingsThreshold = 2000.0,
                effects = LeisureEffects(
                    anxietyDelta = -5, lonelinessDelta = -4, happinessDelta = 7,
                    selfEsteemDelta = 1, socialFulfillmentDelta = 4
                ),
                description = "买了观景台的位置，视野更好。烟花在头顶炸开——你悄悄许了个愿。"
            ),
            LeisureTier.RICH to PublicEventTier(
                tier = LeisureTier.RICH,
                costMin = 500.0, costMax = 2000.0,
                outfitScene = OutfitScene.BUSINESS,
                savingsThreshold = 8000.0, monthlyIncomeThreshold = 10000.0,
                effects = LeisureEffects(
                    anxietyDelta = -6, happinessDelta = 8, selfEsteemDelta = 2,
                    socialFulfillmentDelta = 3
                ),
                description = "VIP观景台，香槟，沙发。烟花很美——但好像少了点挤在人群里的热闹。"
            )
        ),
        defaultWeight = 2.0,
        activeWeight = 6.0,
        description = "砰——第一朵烟花在夜空炸开。所有人都抬起头，世界安静了一秒，然后欢呼声铺天盖地。",
        cognitiveTags = listOf("家庭记忆", "集体仪式")
    )

    // ============================================
    // 汇总
    // ============================================
    val ALL_EVENTS: List<PublicEvent> = listOf(
        TEMPLE_FAIR, CONCERT, MUSIC_FESTIVAL, WORLD_CUP,
        CITY_SPORTS, FAN_IDOL, CITY_FESTIVAL, FIREWORK_SHOW
    )

    fun getById(id: String): PublicEvent? = ALL_EVENTS.find { it.id == id }

    fun getByType(type: PublicEventType): PublicEvent? = ALL_EVENTS.find { it.type == type }

    fun getActiveEvents(month: Int, year: Int): List<PublicEvent> = ALL_EVENTS.filter { event ->
        if (event.isFourYearCycle) {
            // 四年一届周期检查
            (year - event.cycleYearOffset) % 4 == 0 && month in event.months
        } else {
            month in event.months
        }
    }
}