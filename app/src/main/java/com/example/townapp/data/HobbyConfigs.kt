package com.example.townapp.data

/**
 * 爱好配置系统
 * 所有爱好都有不同的增益和成本，根据用户的行为动态计算价值密度
 */
object HobbyConfigs {

    // 共识性负面行为（这些永远是负面的）
    val CONSENSUS_NEGATIVE = setOf(
        "health:smoking",
        "health:excessive_alcohol",
        "health:drug_use"
    )

    // 共识性正面行为（这些永远是正面的）
    val CONSENSUS_POSITIVE = setOf(
        "health:exercise",
        "learning:reading",
        "health:sleep_well"
    )

    /**
     * 获取爱好配置
     */
    fun getConfig(type: String, subType: String): HobbyConfig? {
        return ALL_HOBBIES["$type:$subType"]
    }

    /**
     * 获取建筑配置
     */
    fun getBuildingConfig(type: String, subType: String): BuildingConfig? {
        return BUILDING_CONFIGS["$type:$subType"]
    }

    // --- 爱好配置 ---

    private val ALL_HOBBIES = mapOf(
        // 摄影
        "hobby:photography" to HobbyConfig(
            name = "摄影",
            joyGain = 5.0,
            skillGain = 3.0,
            socialGain = 2.0,
            incomeGain = 1.0,
            moneyCost = 10.0,
            timeCost = 5.0,
            healthCost = 0.5
        ),
        // 游戏
        "hobby:gaming" to HobbyConfig(
            name = "游戏",
            joyGain = 8.0,
            skillGain = 1.0,
            socialGain = 2.0,
            incomeGain = 0.5,
            moneyCost = 2.0,
            timeCost = 8.0,
            healthCost = 1.0
        ),
        // 旅行
        "hobby:travel" to HobbyConfig(
            name = "旅行",
            joyGain = 10.0,
            skillGain = 5.0,
            socialGain = 4.0,
            incomeGain = 0.0,
            moneyCost = 20.0,
            timeCost = 15.0,
            healthCost = 1.0
        ),
        // 阅读
        "learning:reading" to HobbyConfig(
            name = "阅读",
            joyGain = 4.0,
            skillGain = 10.0,
            socialGain = 1.0,
            incomeGain = 2.0,
            moneyCost = 1.0,
            timeCost = 3.0,
            healthCost = 0.1
        ),
        // 运动
        "health:exercise" to HobbyConfig(
            name = "运动",
            joyGain = 3.0,
            skillGain = 2.0,
            socialGain = 1.0,
            incomeGain = 0.0,
            moneyCost = 2.0,
            timeCost = 5.0,
            healthCost = -5.0 // 负面成本 = 健康增益
        ),
        // 短视频
        "mental:short_video" to HobbyConfig(
            name = "短视频",
            joyGain = 2.0,
            skillGain = 0.1,
            socialGain = 0.5,
            incomeGain = 0.0,
            moneyCost = 0.5,
            timeCost = 10.0,
            healthCost = 1.0
        ),
        // 含糖饮料
        "food:sugary_drink" to HobbyConfig(
            name = "含糖饮料",
            joyGain = 1.5,
            skillGain = 0.0,
            socialGain = 0.0,
            incomeGain = 0.0,
            moneyCost = 1.0,
            timeCost = 0.1,
            healthCost = 3.0
        ),
        // 奢侈品消费
        "consumption:luxury" to HobbyConfig(
            name = "奢侈品",
            joyGain = 2.0,
            skillGain = 0.0,
            socialGain = 1.0,
            incomeGain = 0.0,
            moneyCost = 100.0,
            timeCost = 2.0,
            healthCost = 0.0
        ),
        // 烹饪
        "hobby:cooking" to HobbyConfig(
            name = "烹饪",
            joyGain = 4.0,
            skillGain = 4.0,
            socialGain = 3.0,
            incomeGain = 0.0,
            moneyCost = 3.0,
            timeCost = 5.0,
            healthCost = -1.0
        ),
        // 绘画
        "hobby:painting" to HobbyConfig(
            name = "绘画",
            joyGain = 6.0,
            skillGain = 5.0,
            socialGain = 2.0,
            incomeGain = 1.0,
            moneyCost = 5.0,
            timeCost = 6.0,
            healthCost = 0.2
        ),
        // 音乐
        "hobby:music" to HobbyConfig(
            name = "音乐",
            joyGain = 7.0,
            skillGain = 4.0,
            socialGain = 3.0,
            incomeGain = 1.5,
            moneyCost = 8.0,
            timeCost = 7.0,
            healthCost = 0.1
        )
    )

    // --- 建筑配置 ---

    private val BUILDING_CONFIGS = mapOf(
        "hobby:photography" to BuildingConfig(
            name = "摄影建筑",
            district = "spirit",
            getBuildingId = { valueDensity ->
                when {
                    valueDensity >= 100.0 -> "photography:life_studio"
                    valueDensity >= 50.0 -> "photography:creative_studio"
                    valueDensity >= 20.0 -> "photography:hobby_room"
                    valueDensity >= 0.1 -> "photography:storage_room"
                    else -> "photography:equipment_church"
                }
            }
        ),
        "hobby:gaming" to BuildingConfig(
            name = "游戏建筑",
            district = "spirit",
            getBuildingId = { valueDensity ->
                when {
                    valueDensity >= 100.0 -> "gaming:single_player_arcade"
                    valueDensity >= 50.0 -> "gaming:friend_room"
                    valueDensity >= 20.0 -> "gaming:casual_room"
                    valueDensity >= 0.1 -> "gaming:common_room"
                    else -> "gaming:gacha_battlefield"
                }
            }
        ),
        "hobby:travel" to BuildingConfig(
            name = "旅行建筑",
            district = "spirit",
            getBuildingId = { valueDensity ->
                when {
                    valueDensity >= 100.0 -> "travel:exploration_hub"
                    valueDensity >= 50.0 -> "travel:adventure_agency"
                    valueDensity >= 20.0 -> "travel:travel_notes"
                    valueDensity >= 0.1 -> "travel:checkin_room"
                    else -> "travel:tourist_trap"
                }
            }
        )
    )
}

data class HobbyConfig(
    val name: String,
    val joyGain: Double,
    val skillGain: Double,
    val socialGain: Double,
    val incomeGain: Double,
    val moneyCost: Double,
    val timeCost: Double,
    val healthCost: Double
)

data class BuildingConfig(
    val name: String,
    val district: String,
    val getBuildingId: (valueDensity: Double) -> String
)

// ============================================
// v1.5 可伸缩爱好体系 —— 同一爱好，不同设备门槛
// ============================================

/**
 * 爱好设备层级
 * 同一个爱好（摄影/书法/音乐/体育），不同层级对应不同的金钱成本，
 * 但核心体验（记录/表达/沉浸/释放）完全相同。
 *
 * 设计原则：财富拉开设备上限，不拉开感知美好的下限。
 */
enum class HobbyTier(val label: String, val description: String) {
    FREE("零成本", "不需要任何额外花费就能参与"),
    BUDGET("入门", "花一点小钱，就能正经开始"),
    STANDARD("标准", "配备基础装备，体验完整"),
    PREMIUM("进阶", "品质装备，体验更上一层"),
    COLLECTOR("收藏级", "发烧友级别，装备本身成为爱好的一部分")
}

/**
 * 可伸缩爱好配置
 *
 * 每个爱好按设备层级提供不同的成本/增益配置。
 * 核心设计：高层的 joyGain 并非碾压低层，因为感受美好的能力与设备价格无关。
 * 高层主要增加 skillGain（更好的工具带来更高的技艺天花板）和 socialGain（同好圈层）。
 */
data class ScalableHobbyConfig(
    val name: String,
    val category: String,                     // 爱好类别：visual(视觉)/auditory(听觉)/physical(身体)/cultural(文化)
    /** 各层级的配置 */
    val tiers: Map<HobbyTier, HobbyTierConfig>,
    /** 一句话描述这个爱好的本质（用来做小镇评述的核心素材） */
    val essenceDescription: String,
    /** 是否所有人都能触及（true=高低端门槛差异只在设备，体验本质一样；false=有硬性身体/地域门槛） */
    val universallyAccessible: Boolean = true
)

/**
 * 单一层级的爱好成本/增益配置
 */
data class HobbyTierConfig(
    val moneyCostPerSession: Double,          // 单次花费（元）
    val timeCostHours: Double,                // 单次耗时（小时）
    val joyGain: Double,                      // 情绪快乐增益（高层不碾压低层）
    val skillGain: Double,                    // 技艺提升（高层设备更好，技艺天花板更高）
    val socialGain: Double,                   // 社交增益（高层更容易进入同好圈层）
    val equipmentCost: Double,                // 一次性设备投入（元）
    val typicalUserDescription: String,       // 典型用户画像
    val exampleEquipment: String              // 举例设备
)

/**
 * 所有可伸缩爱好配置
 */
object ScalableHobbies {

    // ---- 摄影：老年机 → 手机 → 入门单反 → 专业单反 → 收藏级 ----
    val PHOTOGRAPHY = ScalableHobbyConfig(
        name = "摄影",
        category = "visual",
        essenceDescription = "按下快门那一刻，你想留住的不是画面，是那一刻的心情",
        tiers = mapOf(
            HobbyTier.FREE to HobbyTierConfig(
                moneyCostPerSession = 0.0,
                timeCostHours = 0.5,
                joyGain = 4.0,
                skillGain = 0.5,
                socialGain = 1.0,
                equipmentCost = 0.0,
                typicalUserDescription = "用老年机随手拍下路边野花、傍晚天空的普通人",
                exampleEquipment = "老年机/旧手机自带摄像头"
            ),
            HobbyTier.BUDGET to HobbyTierConfig(
                moneyCostPerSession = 0.0,
                timeCostHours = 1.0,
                joyGain = 5.0,
                skillGain = 1.0,
                socialGain = 2.0,
                equipmentCost = 0.0,
                typicalUserDescription = "用智能手机记录日常，偶尔发朋友圈分享的打工人",
                exampleEquipment = "智能手机摄像头"
            ),
            HobbyTier.STANDARD to HobbyTierConfig(
                moneyCostPerSession = 5.0,
                timeCostHours = 2.0,
                joyGain = 6.0,
                skillGain = 3.0,
                socialGain = 3.0,
                equipmentCost = 3000.0,
                typicalUserDescription = "入门单反爱好者，周末出门拍照，关注构图和光线",
                exampleEquipment = "入门级单反/微单"
            ),
            HobbyTier.PREMIUM to HobbyTierConfig(
                moneyCostPerSession = 20.0,
                timeCostHours = 4.0,
                joyGain = 7.0,
                skillGain = 5.0,
                socialGain = 4.0,
                equipmentCost = 15000.0,
                typicalUserDescription = "专业摄影师/资深爱好者，有明确风格和审美追求",
                exampleEquipment = "全画幅专业单反+镜头群"
            ),
            HobbyTier.COLLECTOR to HobbyTierConfig(
                moneyCostPerSession = 50.0,
                timeCostHours = 6.0,
                joyGain = 7.0,
                skillGain = 6.0,
                socialGain = 5.0,
                equipmentCost = 50000.0,
                typicalUserDescription = "器材发烧友/收藏级玩家，镜头和机身的搭配本身就是乐趣",
                exampleEquipment = "哈苏/徕卡/中画幅+全套镜头"
            )
        )
    )

    // ---- 书法/练字：地面沾水 → 旧报纸 → 普通笔墨 → 品质文房 → 收藏级 ----
    val CALLIGRAPHY = ScalableHobbyConfig(
        name = "书法练字",
        category = "cultural",
        essenceDescription = "一笔一划之间，心也跟着静下来了。写得好不好不重要，重要的是那一刻你只属于自己",
        tiers = mapOf(
            HobbyTier.FREE to HobbyTierConfig(
                moneyCostPerSession = 0.0,
                timeCostHours = 0.5,
                joyGain = 3.0,
                skillGain = 1.0,
                socialGain = 1.0,
                equipmentCost = 0.0,
                typicalUserDescription = "公园里用大毛笔沾水在地上写字的退休老人",
                exampleEquipment = "海绵水笔（可重复使用）"
            ),
            HobbyTier.BUDGET to HobbyTierConfig(
                moneyCostPerSession = 1.0,
                timeCostHours = 1.0,
                joyGain = 4.0,
                skillGain = 2.0,
                socialGain = 1.0,
                equipmentCost = 20.0,
                typicalUserDescription = "用旧报纸和便宜毛笔练字的学生/上班族",
                exampleEquipment = "普通毛笔+旧报纸/毛边纸"
            ),
            HobbyTier.STANDARD to HobbyTierConfig(
                moneyCostPerSession = 5.0,
                timeCostHours = 1.5,
                joyGain = 5.0,
                skillGain = 4.0,
                socialGain = 2.0,
                equipmentCost = 200.0,
                typicalUserDescription = "有固定练字习惯的书法爱好者，临帖、琢磨笔法",
                exampleEquipment = "中档毛笔+宣纸+一得阁墨汁"
            ),
            HobbyTier.PREMIUM to HobbyTierConfig(
                moneyCostPerSession = 15.0,
                timeCostHours = 3.0,
                joyGain = 6.0,
                skillGain = 5.0,
                socialGain = 3.0,
                equipmentCost = 2000.0,
                typicalUserDescription = "书法老师/资深爱好者，研习多种书体，参加展览",
                exampleEquipment = "精品狼毫/兼毫+红星宣纸+老墨"
            ),
            HobbyTier.COLLECTOR to HobbyTierConfig(
                moneyCostPerSession = 30.0,
                timeCostHours = 4.0,
                joyGain = 6.0,
                skillGain = 6.0,
                socialGain = 4.0,
                equipmentCost = 10000.0,
                typicalUserDescription = "收藏级玩家/艺术家，笔墨纸砚的收藏本身就是文化传承",
                exampleEquipment = "定制毛笔+古法宣纸+陈年老墨+端砚"
            )
        )
    )

    // ---- 音乐：哼唱 → 手机APP → 入门乐器 → 品质乐器 → 收藏级 ----
    val MUSIC = ScalableHobbyConfig(
        name = "音乐",
        category = "auditory",
        essenceDescription = "不管是用嘴哼、拿手机放、还是用几万块的琴弹，音乐都是同一种东西——让情绪有个出口",
        tiers = mapOf(
            HobbyTier.FREE to HobbyTierConfig(
                moneyCostPerSession = 0.0,
                timeCostHours = 0.5,
                joyGain = 3.0,
                skillGain = 0.5,
                socialGain = 1.0,
                equipmentCost = 0.0,
                typicalUserDescription = "跟着哼歌、用手机外放听歌的普通人",
                exampleEquipment = "自己的嗓子/手机扬声器"
            ),
            HobbyTier.BUDGET to HobbyTierConfig(
                moneyCostPerSession = 1.0,
                timeCostHours = 1.0,
                joyGain = 5.0,
                skillGain = 1.0,
                socialGain = 2.0,
                equipmentCost = 100.0,
                typicalUserDescription = "用音乐APP听歌、偶尔去KTV的学生/打工人",
                exampleEquipment = "耳机+音乐APP会员"
            ),
            HobbyTier.STANDARD to HobbyTierConfig(
                moneyCostPerSession = 5.0,
                timeCostHours = 2.0,
                joyGain = 6.0,
                skillGain = 3.0,
                socialGain = 3.0,
                equipmentCost = 800.0,
                typicalUserDescription = "学一门乐器（吉他/尤克里里/电子琴），自娱自乐",
                exampleEquipment = "入门吉他/尤克里里/电子琴"
            ),
            HobbyTier.PREMIUM to HobbyTierConfig(
                moneyCostPerSession = 15.0,
                timeCostHours = 3.0,
                joyGain = 7.0,
                skillGain = 5.0,
                socialGain = 4.0,
                equipmentCost = 8000.0,
                typicalUserDescription = "有固定练琴习惯，参加乐队或小型演出的音乐爱好者",
                exampleEquipment = "品牌吉他/电钢琴/合成器"
            ),
            HobbyTier.COLLECTOR to HobbyTierConfig(
                moneyCostPerSession = 40.0,
                timeCostHours = 5.0,
                joyGain = 7.0,
                skillGain = 6.0,
                socialGain = 5.0,
                equipmentCost = 30000.0,
                typicalUserDescription = "专业音乐人/收藏级乐器玩家",
                exampleEquipment = "手工吉他/斯坦威钢琴/定制乐器"
            )
        )
    )

    // ---- 体育运动：公共球场 → 社区健身 → 健身房 → 私教/俱乐部 → 专业训练 ----
    val SPORTS = ScalableHobbyConfig(
        name = "体育运动",
        category = "physical",
        essenceDescription = "出一身汗之后的那种通透，穿什么鞋、在什么场地，没有本质区别",
        tiers = mapOf(
            HobbyTier.FREE to HobbyTierConfig(
                moneyCostPerSession = 0.0,
                timeCostHours = 0.5,
                joyGain = 3.0,
                skillGain = 1.0,
                socialGain = 1.0,
                equipmentCost = 0.0,
                typicalUserDescription = "在小区/公园跑步、散步、做拉伸的普通人",
                exampleEquipment = "运动鞋（已有的）"
            ),
            HobbyTier.BUDGET to HobbyTierConfig(
                moneyCostPerSession = 2.0,
                timeCostHours = 1.0,
                joyGain = 4.0,
                skillGain = 1.5,
                socialGain = 2.0,
                equipmentCost = 100.0,
                typicalUserDescription = "公共球场打球、社区健身房撸铁的年轻人",
                exampleEquipment = "篮球/羽毛球拍+公共场地"
            ),
            HobbyTier.STANDARD to HobbyTierConfig(
                moneyCostPerSession = 10.0,
                timeCostHours = 1.5,
                joyGain = 5.0,
                skillGain = 3.0,
                socialGain = 3.0,
                equipmentCost = 500.0,
                typicalUserDescription = "健身房会员，有固定锻炼计划的上班族",
                exampleEquipment = "健身房年卡+基础装备"
            ),
            HobbyTier.PREMIUM to HobbyTierConfig(
                moneyCostPerSession = 30.0,
                timeCostHours = 2.0,
                joyGain = 6.0,
                skillGain = 5.0,
                socialGain = 4.0,
                equipmentCost = 3000.0,
                typicalUserDescription = "请私教、参加运动俱乐部的重度爱好者",
                exampleEquipment = "私教课+品牌运动装备+专项器材"
            ),
            HobbyTier.COLLECTOR to HobbyTierConfig(
                moneyCostPerSession = 80.0,
                timeCostHours = 3.0,
                joyGain = 6.0,
                skillGain = 6.0,
                socialGain = 5.0,
                equipmentCost = 10000.0,
                typicalUserDescription = "高尔夫/马术/潜水等高端运动爱好者，或专业运动员",
                exampleEquipment = "专属教练+高端场地+全套专业装备"
            )
        )
    )

    /** 所有可伸缩爱好列表 */
    val ALL: List<ScalableHobbyConfig> = listOf(PHOTOGRAPHY, CALLIGRAPHY, MUSIC, SPORTS)

    /** 按名称查找 */
    fun findByName(name: String): ScalableHobbyConfig? = ALL.find { it.name == name }
}

// ============================================
// v1.5 全球公共文娱事件体系
// ============================================

/**
 * 公共文娱事件类型
 *
 * 这些不是个人爱好，而是跨越阶层的集体文化时刻。
 * 世界杯期间，不管你在城中村出租屋用手机看，还是在VIP包厢看现场，
 * 那一刻你和全世界的人一起屏住呼吸。
 */
enum class CollectiveEventType(
    val label: String,
    val frequency: String,                    // 举办频率
    val description: String,
    val freeAccess: String,                   // 零成本参与方式
    val budgetAccess: String,                 // 低成本参与方式
    val standardAccess: String,               // 标准参与方式
    val premiumAccess: String                 // 高端参与方式
) {
    WORLD_CUP(
        label = "世界杯",
        frequency = "每四年一届",
        description = "全世界的足球盛宴，不管你看不看球，那一个月街头巷尾都在讨论",
        freeAccess = "手机/电视免费直播，街边大排档集体观看",
        budgetAccess = "约朋友在家看球，买点零食啤酒",
        standardAccess = "体育酒吧/球迷广场大屏幕观赛",
        premiumAccess = "现场观赛（门票+机酒数千到数万元）"
    ),
    OLYMPICS(
        label = "奥运赛事",
        frequency = "每四年一届（夏奥/冬奥交替）",
        description = "人类体育竞技的巅峰，也是国家荣誉感的集体释放",
        freeAccess = "电视/网络平台免费直播",
        budgetAccess = "和朋友一起追中国队热门项目",
        standardAccess = "购买赛事周边/纪念品",
        premiumAccess = "现场观赛（极少有人能做到）"
    ),
    FILM_AWARDS(
        label = "电影颁奖礼",
        frequency = "每年一次",
        description = "金鸡奖、奥斯卡……红毯、获奖感言、争议和泪水，电影人的年终仪式",
        freeAccess = "社交媒体刷直播片段和热搜讨论",
        budgetAccess = "在家看全程直播，边看边刷微博",
        standardAccess = "和朋友办颁奖夜观影会",
        premiumAccess = "亲临颁奖典礼现场（极少）"
    ),
    FASHION_SHOW(
        label = "时装走秀",
        frequency = "每年两季（春夏/秋冬时装周）",
        description = "T台上的光影、音乐和服装，是一种视觉艺术形式",
        freeAccess = "社交媒体看秀场片段和博主解析",
        budgetAccess = "关注时尚博主解读，逛快时尚感受趋势",
        standardAccess = "观看完整秀场直播，了解设计师理念",
        premiumAccess = "受邀看秀/购买秀场款（门槛极高）"
    ),
    GLOBAL_TOURNAMENT(
        label = "全球球赛/电竞",
        frequency = "按赛季/每年不等",
        description = "欧冠决赛、NBA总决赛、LOL全球总决赛……跨越时区的集体狂欢",
        freeAccess = "免费直播平台观看",
        budgetAccess = "约朋友线下观赛聚会",
        standardAccess = "购买赛事周边球衣/皮肤",
        premiumAccess = "现场观赛/购买VIP包厢票"
    ),
    MUSIC_FESTIVAL(
        label = "音乐节",
        frequency = "每年多场（五一/十一/暑期集中）",
        description = "草地、舞台、人群——音乐节是年轻人的集体情绪释放",
        freeAccess = "看朋友圈/社交媒体直播片段",
        budgetAccess = "买早鸟票，和朋友拼车去",
        standardAccess = "买全价票，精心准备穿搭妆容",
        premiumAccess = "VIP专区+后台通行证（极少）"
    )
}

/**
 * 公共事件参与方式
 */
data class CollectiveEventParticipation(
    val event: CollectiveEventType,
    val accessLevelDescription: String,       // 针对当前用户身份的参与方式描述
    val estimatedCost: Double,                 // 预计花费
    val socialGain: Double,                    // 社交话题增益
    val joyGain: Double,                       // 情绪快乐增益
    val commentary: String                     // 小镇评述
)
