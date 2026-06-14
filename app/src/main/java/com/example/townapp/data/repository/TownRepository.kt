package com.example.townapp.data.repository

import com.example.townapp.data.model.*

object TownRepository {
    val foodNegativeBuildings = listOf(
        TownBuilding(
            id = "food_neg_1",
            name = "重金属矿山",
            type = BuildingType.FOOD_NEGATIVE,
            district = TownDistrict.DIGESTIVE_TRACT,
            triggerCondition = "购买野生/土特产品",
            visualEffect = "黑色的矿山，冒着绿色的毒烟",
            pollutionImpact = 10,
            healthImpact = -5
        ),
        TownBuilding(
            id = "food_neg_2",
            name = "油炸地狱",
            type = BuildingType.FOOD_NEGATIVE,
            district = TownDistrict.DIGESTIVE_TRACT,
            triggerCondition = "经常吃油炸/红烧食物",
            visualEffect = "到处是油锅和烟囱，天空是黑色的",
            pollutionImpact = 20,
            healthImpact = -10
        ),
        TownBuilding(
            id = "food_neg_3",
            name = "趁热打铁火锅店",
            type = BuildingType.FOOD_NEGATIVE,
            district = TownDistrict.DIGESTIVE_TRACT,
            triggerCondition = "经常吃65℃以上的热食",
            visualEffect = "冒着热气的火锅店，门口有烫伤的居民",
            healthImpact = -15
        ),
        TownBuilding(
            id = "food_neg_4",
            name = "隔夜菜回收站",
            type = BuildingType.FOOD_NEGATIVE,
            district = TownDistrict.DIGESTIVE_TRACT,
            triggerCondition = "经常吃隔夜菜",
            visualEffect = "堆满腐烂食物的回收站，苍蝇满天飞",
            pollutionImpact = 15,
            healthImpact = -10
        ),
        TownBuilding(
            id = "food_neg_5",
            name = "最低消费餐厅",
            type = BuildingType.FOOD_NEGATIVE,
            district = TownDistrict.DIGESTIVE_TRACT,
            triggerCondition = "经常为了凑最低消费多点菜",
            visualEffect = "豪华的餐厅，里面全是吃撑了的居民",
            happinessImpact = -10,
            pollutionImpact = 10
        ),
        TownBuilding(
            id = "food_neg_6",
            name = "烟酒专卖店",
            type = BuildingType.FOOD_NEGATIVE,
            district = TownDistrict.DIGESTIVE_TRACT,
            triggerCondition = "经常抽烟喝酒",
            visualEffect = "阴暗的店铺，门口躺着醉汉和烟鬼",
            healthImpact = -20
        ),
        TownBuilding(
            id = "food_neg_7",
            name = "糖衣炮弹工厂",
            type = BuildingType.FOOD_NEGATIVE,
            district = TownDistrict.DIGESTIVE_TRACT,
            triggerCondition = "经常喝奶茶/可乐",
            visualEffect = "五颜六色的工厂，生产着各种含糖饮料",
            healthImpact = -15,
            happinessImpact = -5
        )
    )

    val foodPositiveBuildings = listOf(
        TownBuilding(
            id = "food_pos_1",
            name = "清蒸农场",
            type = BuildingType.FOOD_POSITIVE,
            district = TownDistrict.DIGESTIVE_TRACT,
            triggerCondition = "经常吃清蒸/白灼食物",
            visualEffect = "绿色的农场，种着新鲜的蔬菜和水果",
            pollutionImpact = -10,
            healthImpact = 10,
            happinessImpact = 5
        ),
        TownBuilding(
            id = "food_pos_2",
            name = "七分饱食堂",
            type = BuildingType.FOOD_POSITIVE,
            district = TownDistrict.DIGESTIVE_TRACT,
            triggerCondition = "每次吃饭都吃七分饱",
            visualEffect = "干净整洁的食堂，居民都很健康",
            healthImpact = 15,
            happinessImpact = 10
        )
    )

    val clothingNegativeBuildings = listOf(
        TownBuilding(
            id = "cloth_neg_1",
            name = "符号消费大厦",
            type = BuildingType.CLOTHING_NEGATIVE,
            district = TownDistrict.SKIN_KINGDOM,
            triggerCondition = "购买奢侈品包包/首饰",
            visualEffect = "金碧辉煌的大厦，门口全是排队的人",
            debtImpact = 50,
            awakeningImpact = -10
        ),
        TownBuilding(
            id = "cloth_neg_2",
            name = "高跟鞋刑场",
            type = BuildingType.CLOTHING_NEGATIVE,
            district = TownDistrict.SKIN_KINGDOM,
            triggerCondition = "经常穿高跟鞋",
            visualEffect = "到处是磨破脚的女性，地上全是血",
            healthImpact = -10
        ),
        TownBuilding(
            id = "cloth_neg_3",
            name = "智商税化妆品店",
            type = BuildingType.CLOTHING_NEGATIVE,
            district = TownDistrict.SKIN_KINGDOM,
            triggerCondition = "购买贵价护肤品/面膜",
            visualEffect = "装修豪华的店铺，里面全是没用的瓶瓶罐罐",
            debtImpact = 30,
            healthImpact = -5
        ),
        TownBuilding(
            id = "cloth_neg_4",
            name = "原装配件专卖店",
            type = BuildingType.CLOTHING_NEGATIVE,
            district = TownDistrict.SKIN_KINGDOM,
            triggerCondition = "购买原装充电器/数据线/手机壳",
            visualEffect = "价格昂贵的店铺，里面全是印着logo的配件",
            debtImpact = 20,
            awakeningImpact = -5
        ),
        TownBuilding(
            id = "cloth_neg_5",
            name = "衣物囤积仓库",
            type = BuildingType.CLOTHING_NEGATIVE,
            district = TownDistrict.SKIN_KINGDOM,
            triggerCondition = "衣物数量超过100件",
            visualEffect = "堆满衣服的仓库，老鼠在里面跑",
            happinessImpact = -10,
            redundancyLevel = 30
        ),
        TownBuilding(
            id = "cloth_neg_6",
            name = "面子消费陈列厅",
            type = BuildingType.CLOTHING_NEGATIVE,
            district = TownDistrict.SKIN_KINGDOM,
            triggerCondition = "购买奢侈品/高价服饰",
            visualEffect = "空旷的展厅里陈列着昂贵的衣物，但居民穿着并不自在",
            debtImpact = 35,
            happinessImpact = -8,
            awakeningImpact = -6
        )
    )

    val clothingPositiveBuildings = listOf(
        TownBuilding(
            id = "cloth_pos_1",
            name = "平民神物商店",
            type = BuildingType.CLOTHING_POSITIVE,
            district = TownDistrict.SKIN_KINGDOM,
            triggerCondition = "只买高价值密度的物品",
            visualEffect = "简单的店铺，卖着尿素维E乳、阿达帕林、帆布鞋",
            debtImpact = -30,
            awakeningImpact = 10
        ),
        TownBuilding(
            id = "cloth_pos_2",
            name = "断舍离回收站",
            type = BuildingType.CLOTHING_POSITIVE,
            district = TownDistrict.SKIN_KINGDOM,
            triggerCondition = "经常清理闲置物品",
            visualEffect = "干净的回收站，居民在这里捐赠物品",
            redundancyLevel = -20,
            happinessImpact = 15
        )
    )

    val housingNegativeBuildings = listOf(
        TownBuilding(
            id = "housing_neg_1",
            name = "地下洞穴",
            type = BuildingType.HOUSING_NEGATIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "住地下室",
            visualEffect = "阴暗潮湿的地下洞穴，没有阳光，居民都生病了",
            pollutionImpact = 30,
            healthImpact = -20,
            happinessImpact = -20
        ),
        TownBuilding(
            id = "housing_neg_2",
            name = "房贷监狱",
            type = BuildingType.HOUSING_NEGATIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "贷款买房，月供超过收入30%",
            visualEffect = "高高的监狱，每个窗户里都有一个在还钱的人",
            debtImpact = 100,
            happinessImpact = -30
        ),
        TownBuilding(
            id = "housing_neg_3",
            name = "停车场黑洞",
            type = BuildingType.HOUSING_NEGATIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "购买私家车",
            visualEffect = "巨大的黑洞，吞噬着汽车和金钱",
            debtImpact = 40,
            productivityImpact = -15,
            pollutionImpact = 15
        ),
        TownBuilding(
            id = "housing_neg_4",
            name = "通勤地狱",
            type = BuildingType.HOUSING_NEGATIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "每天通勤超过2小时",
            visualEffect = "长长的公路，堵满了汽车，居民都很疲惫",
            happinessImpact = -30,
            productivityImpact = -20
        ),
        TownBuilding(
            id = "housing_neg_5",
            name = "欧式豪华宫",
            type = BuildingType.HOUSING_NEGATIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "装修风格选择欧式豪华",
            visualEffect = "金色的宫殿，挂满水晶灯，堆满无用的装饰",
            happinessImpact = -5,
            awakeningImpact = -5,
            redundancyLevel = 30
        ),
        TownBuilding(
            id = "housing_neg_6",
            name = "网红ins风小屋",
            type = BuildingType.HOUSING_NEGATIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "装修风格选择网红ins风",
            visualEffect = "粉色的小屋，摆满泡泡玛特和网红装饰品",
            happinessImpact = 5,
            awakeningImpact = -10,
            redundancyLevel = 25
        ),
        TownBuilding(
            id = "housing_neg_7",
            name = "杂物堆迷宫",
            type = BuildingType.HOUSING_NEGATIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "闲置物品超过50件",
            visualEffect = "到处都是杂物，连下脚的地方都没有",
            happinessImpact = -30,
            awakeningImpact = -20,
            productivityImpact = -25
        )
    )

    val housingPositiveBuildings = listOf(
        TownBuilding(
            id = "housing_pos_1",
            name = "阳光公寓",
            type = BuildingType.HOUSING_POSITIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "住地上房间，通勤时间小于1小时",
            visualEffect = "明亮的公寓，有阳光和花园",
            healthImpact = 15,
            happinessImpact = 20,
            productivityImpact = 10
        ),
        TownBuilding(
            id = "housing_pos_2",
            name = "公共交通站",
            type = BuildingType.HOUSING_POSITIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "经常坐地铁/公交",
            visualEffect = "干净的公交站，居民轻松出行",
            pollutionImpact = -10,
            productivityImpact = 15,
            happinessImpact = 10
        ),
        TownBuilding(
            id = "housing_pos_3",
            name = "日式极简之家",
            type = BuildingType.HOUSING_POSITIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "装修风格选择日式极简且闲置物品≤10件",
            visualEffect = "原木色的房子，简洁干净，每一样东西都有它的位置",
            happinessImpact = 20,
            awakeningImpact = 10,
            redundancyLevel = -25,
            productivityImpact = 15
        )
    )

    val mentalNegativeBuildings = listOf(
        TownBuilding(
            id = "mental_neg_1",
            name = "修仙小说塔",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每天看修仙网文超过3小时",
            visualEffect = "高高的塔，里面全是沉迷幻想的居民，眼神空洞",
            awakeningImpact = -30,
            productivityImpact = -20
        ),
        TownBuilding(
            id = "mental_neg_2",
            name = "短视频收割塔",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每天刷短视频超过2小时",
            visualEffect = "不断闪烁的塔，吸收着居民的注意力",
            productivityImpact = -40,
            happinessImpact = -15
        ),
        TownBuilding(
            id = "mental_neg_3",
            name = "直播打赏宫殿",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每月直播打赏超过100元",
            visualEffect = "豪华的宫殿，主播在上面表演，下面的居民在扔钱",
            debtImpact = 60,
            happinessImpact = -20
        ),
        TownBuilding(
            id = "mental_neg_4",
            name = "玄学算命馆",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每年玄学消费超过500元",
            visualEffect = "阴森的店铺，算命先生在里面骗人",
            productivityImpact = -30,
            awakeningImpact = -20
        ),
        TownBuilding(
            id = "mental_neg_5",
            name = "网游氪金战场",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每月网游充值超过100元",
            visualEffect = "混乱的战场，居民在里面互相厮杀，扔钱",
            debtImpact = 50,
            productivityImpact = -25
        ),
        TownBuilding(
            id = "mental_neg_6",
            name = "历史坟墓",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每天研究历史超过3小时",
            visualEffect = "巨大的坟墓，里面全是死人的骨头和故事",
            productivityImpact = -20,
            awakeningImpact = -15
        ),
        TownBuilding(
            id = "mental_neg_7",
            name = "古文博物馆",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每天学习文言文/拉丁语超过1小时",
            visualEffect = "发霉的博物馆，里面全是腐烂的竹简和死文字",
            productivityImpact = -10,
            awakeningImpact = -10
        ),
        TownBuilding(
            id = "mental_neg_8",
            name = "传统文化糟粕祠堂",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "宣扬裹小脚、太监、封建礼教",
            visualEffect = "破旧的祠堂，里面供奉着封建礼教的牌位",
            awakeningImpact = -40,
            happinessImpact = -30
        ),
        TownBuilding(
            id = "mental_neg_9",
            name = "审美荒漠",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "审美值低于200",
            visualEffect = "一片荒芜的沙漠，没有任何美感和设计",
            awakeningImpact = -15,
            happinessImpact = -10
        ),
        TownBuilding(
            id = "mental_neg_10",
            name = "风格堆砌迷宫",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "装修风格混乱混搭",
            visualEffect = "混乱的迷宫，里面堆满了各种风格的元素",
            awakeningImpact = -20,
            redundancyLevel = 40
        ),
        TownBuilding(
            id = "mental_neg_11",
            name = "网红打卡陷阱",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "过度追求网红装修",
            visualEffect = "粉色的陷阱，里面全是泡泡玛特和网红装饰",
            awakeningImpact = -25,
            debtImpact = 30
        ),
        TownBuilding(
            id = "mental_neg_12",
            name = "日光刑场",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "经常在三伏天晒背",
            visualEffect = "烈日下的广场，上面躺着很多被晒伤的人",
            healthImpact = -20,
            awakeningImpact = -10
        ),
        TownBuilding(
            id = "mental_neg_13",
            name = "节气禁忌坛",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "相信各种节气禁忌",
            visualEffect = "破旧的祭坛，上面写着各种荒谬的禁忌",
            awakeningImpact = -15,
            productivityImpact = -10
        ),
        TownBuilding(
            id = "mental_neg_14",
            name = "星象占卜屋",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "经常看星象、算命",
            visualEffect = "阴森的店铺，里面有个神棍在看星星",
            awakeningImpact = -30,
            happinessImpact = -20,
            debtImpact = 50
        ),
        TownBuilding(
            id = "mental_neg_15",
            name = "量子玄学馆",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "相信量子速读、量子纠缠治病",
            visualEffect = "装修豪华的店铺，里面全是看不懂的科学名词",
            awakeningImpact = -40,
            productivityImpact = -30
        ),
        // ── 消费认知负面建筑 ──
        TownBuilding(
            id = "mental_neg_45",
            name = "应酬式社交餐厅",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "频繁参加无效应酬聚餐、人情社交",
            visualEffect = "喧嚣的餐厅里坐满了被迫应酬的人，每个人脸上都写着疲惫。账单上的每一笔都是融入人群的代价。",
            debtImpact = 40,
            happinessImpact = -15,
            productivityImpact = -20,
            anxietyImpact = 15
        ),
        TownBuilding(
            id = "mental_neg_46",
            name = "物质幸福幻象馆",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "频繁购物/囤积消费品",
            visualEffect = "堆满商品的房间，新买的物品还没拆封就失去了吸引力。墙上贴着：'再买一件'的念头永远没有尽头。",
            debtImpact = 35,
            happinessImpact = -12,
            redundancyLevel = 40,
            awakeningImpact = -8
        ),
        TownBuilding(
            id = "mental_neg_47",
            name = "功利追逐竞技场",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "急于追求短期结果/功利化目标",
            visualEffect = "充满计时器和排行榜的竞技场，居民疲于奔命却无法享受过程。终点线永远在变远。",
            anxietyImpact = 30,
            happinessImpact = -18,
            mentalHealthImpact = -15,
            productivityImpact = -15
        )
    )

    val mentalPositiveBuildings = listOf(
        TownBuilding(
            id = "mental_pos_1",
            name = "觉醒之塔",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "觉醒值超过1000",
            visualEffect = "闪闪发光的塔，越往上越亮",
            awakeningImpact = 20,
            productivityImpact = 30
        ),
        TownBuilding(
            id = "mental_pos_2",
            name = "科学实验室",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "经常学习科学技术",
            visualEffect = "现代化的实验室，居民在里面做实验",
            productivityImpact = 50,
            awakeningImpact = 25
        ),
        TownBuilding(
            id = "mental_pos_3",
            name = "外语学校",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "经常学习英语/现代外语",
            visualEffect = "明亮的学校，居民在里面交流",
            productivityImpact = 40,
            happinessImpact = 20
        ),
        TownBuilding(
            id = "mental_pos_4",
            name = "编程学院",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "经常学习编程",
            visualEffect = "充满代码的学院，居民在里面创造价值",
            productivityImpact = 60,
            debtImpact = -40
        ),
        TownBuilding(
            id = "mental_pos_5",
            name = "图书馆",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "经常读经典文学/科普书籍",
            visualEffect = "安静的图书馆，居民在里面阅读",
            awakeningImpact = 30,
            productivityImpact = 15
        ),
        TownBuilding(
            id = "mental_pos_6",
            name = "审美研究所",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "审美值超过500",
            visualEffect = "现代化的研究所，里面展示着各种优秀设计作品",
            awakeningImpact = 15,
            happinessImpact = 20
        ),
        TownBuilding(
            id = "mental_pos_7",
            name = "极简生活工坊",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "掌握断舍离技巧",
            visualEffect = "温馨的工坊，居民在里面学习收纳和整理",
            awakeningImpact = 10,
            redundancyLevel = -30
        ),
        TownBuilding(
            id = "mental_pos_8",
            name = "体验设计学院",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "学习体验设计",
            visualEffect = "创意满满的学院，居民在里面设计沉浸式体验",
            awakeningImpact = 25,
            productivityImpact = 20
        ),
        TownBuilding(
            id = "mental_pos_9",
            name = "航天博物馆",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "经常学习科学知识",
            visualEffect = "现代化的博物馆，里面有火箭和卫星的模型",
            awakeningImpact = 30,
            productivityImpact = 40
        ),
        TownBuilding(
            id = "mental_pos_10",
            name = "科学实验室",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "经常做实验、验证科学理论",
            visualEffect = "明亮的实验室，居民在里面做研究",
            awakeningImpact = 20,
            productivityImpact = 30
        ),
        TownBuilding(
            id = "mental_pos_11",
            name = "现代医院",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "相信现代医学",
            visualEffect = "干净整洁的医院，居民在这里看病",
            healthImpact = 20,
            happinessImpact = 15
        ),
        TownBuilding(
            id = "mental_pos_12",
            name = "审美图书馆",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "完成审美排毒期",
            visualEffect = "明亮的图书馆，里面收藏着各种高价值密度的设计和产品",
            awakeningImpact = 25,
            happinessImpact = 20
        ),
        TownBuilding(
            id = "mental_pos_13",
            name = "审美研究所",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "审美等级达到审美大师",
            visualEffect = "现代化的研究所，居民在里面学习和实践审美原则",
            awakeningImpact = 30,
            happinessImpact = 25
        ),
        TownBuilding(
            id = "mental_pos_14",
            name = "极简生活工坊",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "完成100次每日一删",
            visualEffect = "温馨的工坊，居民在里面学习收纳和整理",
            awakeningImpact = 15,
            redundancyLevel = -35
        ),

        // ===== 价值观觉醒建筑 =====

        TownBuilding(
            id = "mental_pos_14",
            name = "觉醒广场",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "完成价值观觉醒课程",
            visualEffect = "广场中央立着一座雕像：一个个体站在山峰上，仰望星空。周围是无数个清醒的个体，各自发光，照亮整个广场",
            healthImpact = 20,
            awakeningImpact = 50,
            happinessImpact = 30,
            productivityImpact = 25
        ),
        TownBuilding(
            id = "mental_pos_15",
            name = "个体尊严纪念碑",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "拒绝被集体主义绑架",
            visualEffect = "纪念碑上刻着：'你的生命时间，是你唯一真正拥有的东西。不要为了任何虚假的集体，牺牲你自己的人生。'",
            awakeningImpact = 40,
            happinessImpact = 35
        ),
        TownBuilding(
            id = "mental_pos_16",
            name = "氧气面罩培训中心",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "学会正确的责任顺序",
            visualEffect = "培训中心的墙上写着：'先对自己负责，再对家庭负责，再对社区负责，再对国家负责。先戴好自己的氧气面罩，再去帮助别人。'",
            awakeningImpact = 30,
            happinessImpact = 25,
            healthImpact = 15
        ),
        TownBuilding(
            id = "mental_pos_17",
            name = "清醒者议会",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "成为清醒的个体",
            visualEffect = "议会大厅里，每个座位都是独立的。成员们不是被迫服从，而是自愿贡献。他们不会被虚假口号欺骗，能判断什么是真正的集体利益。",
            awakeningImpact = 45,
            productivityImpact = 30,
            happinessImpact = 40
        ),
        // ===== 全球化·普世价值建筑 =====
        TownBuilding(
            id = "mental_pos_35",
            name = "人类历史博物馆",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "学习人类历史",
            visualEffect = "宏伟的博物馆，展示人类从原始社会到今天的奋斗历史。展厅里陈列着各个文明的文物和故事，从古埃及的金字塔到工业革命的蒸汽机，从民权运动的标语到太空探索的火箭。每一个展品都在讲述同一个故事：人类如何一步步走到今天。",
            awakeningImpact = 80,
            happinessImpact = 50,
            productivityImpact = 40,
            healthImpact = 30
        ),
        TownBuilding(
            id = "mental_pos_36",
            name = "普通人故事馆",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "听普通人的奋斗故事",
            visualEffect = "温暖的图书馆式建筑，墙上挂满了来自世界各地的普通人的照片和故事。有英国矿工的罢工日记，有古巴护士的非洲医疗笔记，有中国农妇的手写信。每一个故事都在说：历史不是英雄写的，是千千万万普通人写的。",
            awakeningImpact = 75,
            happinessImpact = 45,
            socialAbilityImpact = 30,
            lonelinessImpact = -25
        ),
        TownBuilding(
            id = "mental_pos_37",
            name = "社区互助中心",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "帮助他人/社区志愿服务",
            visualEffect = "明亮的社区中心，居民们在这里互帮互助。有人教英语，有人修电器，有人带孩子，有人做翻译。墙上写着：'互相帮助，是人类能生存至今的根本原因。'不同肤色、不同语言的人在这里找到了共同的归属感。",
            awakeningImpact = 70,
            happinessImpact = 55,
            socialAbilityImpact = 40,
            lonelinessImpact = -30
        ),
        TownBuilding(
            id = "mental_pos_38",
            name = "劳动者纪念碑",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "学习劳动者/改革者的事迹",
            visualEffect = "庄重的纪念碑广场，碑上铭刻着各行各业劳动者的名字。从煤矿工人到程序员，从清洁工到医生，从农民到教师。碑座上刻着一行字：'真正推动历史前进的，是千千万万的劳动者。'",
            awakeningImpact = 65,
            productivityImpact = 35,
            happinessImpact = 35,
            healthImpact = 20
        ),
        // ── 长期世界观专栏（正向认知）──
        TownBuilding(
            id = "mental_pos_40",
            name = "自由选择权之塔",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "理解自由的本质 = 拥有离开不喜欢的人和事的底气",
            visualEffect = "高耸的灯塔，光芒照亮整个小镇。塔身上刻着：'财富的根本价值，在于换取选择权——可以选择不做什么、不见谁。'",
            awakeningImpact = 75,
            happinessImpact = 60,
            productivityImpact = 40,
            lonelinessImpact = -20
        ),
        TownBuilding(
            id = "mental_pos_41",
            name = "商业慈善交易所",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "理解商业的高效慈善本质",
            visualEffect = "繁忙的交易所内部，物资流转井然有序。墙上写着：'商业是最有效的慈善——流通盘活闲置资源，生产创造社会价值。'",
            awakeningImpact = 80,
            productivityImpact = 55,
            happinessImpact = 45,
            socialAbilityImpact = 30
        ),
        TownBuilding(
            id = "mental_pos_42",
            name = "AI时代远景规划院",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "了解小镇的长期愿景与AI时序模拟",
            visualEffect = "未来感十足的规划院，全息投影展示着未来数十年的小镇发展蓝图。入口处写着：'立足当下，为未来数十年的人类生活模式规划参照目标。'",
            awakeningImpact = 90,
            productivityImpact = 60,
            happinessImpact = 50,
            healthImpact = 25
        )
    )

    // ===== 价值观负面建筑 =====
    val valueNegativeBuildings = listOf(
        TownBuilding(
            id = "mental_neg_13",
            name = "集体陷阱迷宫",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "被集体主义绑架",
            visualEffect = "迷宫的入口写着'为了集体利益'，里面到处是'吃亏是福'、'先人后己'的标语。走到尽头，发现只有少数人在享受集体利益。",
            healthImpact = -30,
            awakeningImpact = -50,
            happinessImpact = -40
        ),
        TownBuilding(
            id = "mental_neg_14",
            name = "道德绑架工厂",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "被道德口号操控",
            visualEffect = "工厂里生产着各种道德口号：'年轻人要多吃苦'、'一家人不要分那么清'、'支持国货就是爱国'。每个口号背后，都是少数人的利益。",
            awakeningImpact = -35,
            happinessImpact = -25,
            productivityImpact = -20
        ),
        TownBuilding(
            id = "mental_neg_15",
            name = "牺牲者纪念碑",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "单方面牺牲",
            visualEffect = "纪念碑上刻着无数牺牲者的名字，他们为了'集体利益'牺牲了自己的人生。而那些受益的人，没有一个名字在上面。",
            healthImpact = -25,
            awakeningImpact = -40,
            happinessImpact = -35
        ),
        TownBuilding(
            id = "mental_neg_16",
            name = "被驯服者学校",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "安分守己，放弃自我",
            visualEffect = "学校里教的是'安分守己'、'随遇而安'、'顾全大局'。学生们学会了放弃选择、放弃探索、放弃成为更好的自己。",
            awakeningImpact = -45,
            productivityImpact = -30,
            happinessImpact = -40
        ),
        TownBuilding(
            id = "mental_neg_17",
            name = "虚假集体殿堂",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "相信虚假的集体利益",
            visualEffect = "殿堂里供奉着'公司大家庭'、'民族品牌'、'家族荣耀'。每个神像背后，都是少数人用集体名义剥削多数人。",
            awakeningImpact = -55,
            happinessImpact = -50,
            healthImpact = -20
        )
    )

    // ===== 赛博奶嘴负面建筑 =====
    val cyberPacifierNegativeBuildings = listOf(
        TownBuilding(
            id = "mental_neg_18",
            name = "Doro收容所",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每天刷Doro超过2小时",
            visualEffect = "阴暗的收容所，里面关着无数个可怜的Doro。居民们都在哭着喂它们橘子，嘴里念叨着'BE结局''我要拯救Doro'。空气中弥漫着悲伤和自怜的气息。",
            healthImpact = -15,
            awakeningImpact = -30,
            happinessImpact = -20,
            productivityImpact = -15
        ),
        TownBuilding(
            id = "mental_neg_19",
            name = "塔菲喵直播间",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每天看塔菲喵超过3小时或每月打赏超过100元",
            visualEffect = "闪闪发光的直播间，塔菲喵在上面撒娇卖萌，下面排着队的居民在疯狂扔钱。弹幕刷着'谢谢喵''雏草姬''我要保护塔菲'。打赏金额不断攀升，居民的尊严值却在下降。",
            awakeningImpact = -40,
            happinessImpact = -25,
            debtImpact = 100,
            dignityImpact = -30
        ),
        TownBuilding(
            id = "mental_neg_20",
            name = "赛博奶嘴工厂",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "同时沉迷3个以上虚拟形象",
            visualEffect = "巨大的工厂，日夜不停地生产着各种各样的虚拟形象。Doro、塔菲喵、各种二次元老婆排队从传送带上下来。居民排着长队购买，每个人的脸上都带着虚假的幸福。",
            awakeningImpact = -50,
            happinessImpact = -30,
            realitySenseImpact = -40,
            productivityImpact = -20
        ),
        TownBuilding(
            id = "mental_neg_21",
            name = "虚拟恋人工作室",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每天花费超过4小时与虚拟恋人互动",
            visualEffect = "粉色的工作室，里面坐满了和虚拟恋人'谈恋爱'的居民。他们对着屏幕傻笑，送礼物，说情话。但他们的眼神越来越空洞，现实感越来越低。",
            awakeningImpact = -45,
            happinessImpact = -20,
            lonelinessImpact = 30,
            socialAbilityImpact = -35
        ),
        TownBuilding(
            id = "mental_neg_22",
            name = "精神止痛片贩卖机",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每天用虚拟内容'治愈'自己超过3小时",
            visualEffect = "巨大的贩卖机，上面写着'刷完这个视频就好了''再看一集就睡觉''最后一条朋友圈'。居民们不断投币，吞下一片又一片的虚拟快乐，却不知道自己的问题正在积累。",
            awakeningImpact = -35,
            problemAvoidanceImpact = 40,
            realProblemSolvingImpact = -30
        )
    )

    // ===== 赛博奶嘴正面建筑 =====
    val cyberPacifierPositiveBuildings = listOf(
        TownBuilding(
            id = "mental_pos_18",
            name = "现实连接中心",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每周和朋友见面超过3次",
            visualEffect = "明亮宽敞的中心，居民们在这里聊天、聚会、做活动。笑声和真实的对话充满了整个空间。每个人脸上的表情都是鲜活的，不是屏幕后面的emoji能比的。",
            awakeningImpact = 25,
            happinessImpact = 30,
            socialAbilityImpact = 20,
            lonelinessImpact = -20
        ),
        TownBuilding(
            id = "mental_pos_19",
            name = "爱好工作室",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "培养一个现实中的爱好",
            visualEffect = "充满活力的工作室，居民们在这里画画、弹琴、做手工、写作、运动。每个人的眼睛都在发光，因为他们在创造真实的东西，而不是消费虚拟的内容。",
            awakeningImpact = 30,
            creativityImpact = 35,
            happinessImpact = 25,
            productivityImpact = 20
        ),
        TownBuilding(
            id = "mental_pos_20",
            name = "真实恋爱研究所",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "开始一段真实的恋爱关系",
            visualEffect = "温馨的研究所，里面讨论的是真实的情感连接：如何沟通、如何理解、如何包容、如何成长。居民们在这里学习，认识到虚拟恋爱永远无法替代真实的情感。",
            awakeningImpact = 35,
            happinessImpact = 40,
            emotionalIntelligenceImpact = 30,
            lonelinessImpact = -35
        ),
        TownBuilding(
            id = "mental_pos_21",
            name = "断舍离数字 detox中心",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "连续7天不刷任何虚拟偶像内容",
            visualEffect = "安静的康复中心，居民们在这里戒断虚拟内容的依赖。他们学习如何面对现实中的痛苦，如何建立真实的人际关系，如何在真实的世界里找到快乐。",
            awakeningImpact = 50,
            realitySenseImpact = 40,
            realProblemSolvingImpact = 30,
            happinessImpact = 35
        ),
        TownBuilding(
            id = "mental_pos_22",
            name = "线下社交广场",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每月参加4次以上线下社交活动",
            visualEffect = "热闹的广场，居民们在这里参加读书会、运动俱乐部、手工坊、桌游局。真实的笑声、真实的对话、真实的眼神交流，取代了屏幕后面的点赞和弹幕。",
            socialAbilityImpact = 30,
            happinessImpact = 35,
            awakeningImpact = 25,
            lonelinessImpact = -30
        )
    )

    val negativeNPCs = listOf(
        TownNPC(
            id = "npc_neg_1",
            name = "黑心餐馆老板",
            role = "餐馆老板",
            isNegative = true,
            dialogue = "趁热吃！凉了就不好吃了！什么？致癌？我都吃了几十年了，不也没事吗？",
            avatar = "👨🍳"
        ),
        TownNPC(
            id = "npc_neg_2",
            name = "奢侈品柜姐",
            role = "奢侈品销售",
            isNegative = true,
            dialogue = "这款包包是最新款，只剩最后一个了！背上它，你就是上流社会的人！",
            avatar = "💄"
        ),
        TownNPC(
            id = "npc_neg_3",
            name = "房产中介",
            role = "房产经纪人",
            isNegative = true,
            dialogue = "买房要趁早！再不买就涨了！掏空六个钱包也要买！有房才有家！",
            avatar = "🏠"
        ),
        TownNPC(
            id = "npc_neg_4",
            name = "汽车销售",
            role = "汽车销售",
            isNegative = true,
            dialogue = "有车才有自由！周末可以自驾游！想去哪就去哪！",
            avatar = "🚗"
        ),
        TownNPC(
            id = "npc_neg_5",
            name = "直播主播",
            role = "网络主播",
            isNegative = true,
            dialogue = "谢谢哥哥的火箭！哥哥再刷一个，我给你跳个舞！",
            avatar = "🎤"
        ),
        TownNPC(
            id = "npc_neg_6",
            name = "算命先生",
            role = "算命师",
            isNegative = true,
            dialogue = "你今年命犯太岁，需要买一个开光的护身符，才能消灾解难！",
            avatar = "🧙"
        ),
        TownNPC(
            id = "npc_neg_7",
            name = "网游GM",
            role = "游戏管理员",
            isNegative = true,
            dialogue = "充钱就能变强！只要充得够多，你就是全服第一！",
            avatar = "🎮"
        ),
        TownNPC(
            id = "npc_neg_8",
            name = "国学大师",
            role = "传统文化专家",
            isNegative = true,
            dialogue = "文言文是中华文化的精髓！不学文言文，你就不是中国人！老祖宗的东西不能丢！",
            avatar = "📜"
        ),
        TownNPC(
            id = "npc_neg_9",
            name = "装修包工头",
            role = "装修承包商",
            isNegative = true,
            dialogue = "欧式吊顶最显档次！水晶灯必须安排！再做个电视背景墙，保证好看！",
            avatar = "🔨"
        ),
        TownNPC(
            id = "npc_neg_10",
            name = "神棍大师",
            role = "伪科学专家",
            isNegative = true,
            dialogue = "三星合月，大凶之兆！快买我的开光护身符，保你平安！只要998！",
            avatar = "🧙‍♂️"
        ),

        // ===== 价值观陷阱NPC =====

        TownNPC(
            id = "npc_neg_11",
            name = "集体主义老板",
            role = "公司老板",
            isNegative = true,
            dialogue = "公司是个大家庭，大家要讲奉献，不要计较个人得失！你看，我都买了第三套别墅了，你们也要努力！",
            avatar = "👔"
        ),
        TownNPC(
            id = "npc_neg_12",
            name = "道德绑架专家",
            role = "道德说教者",
            isNegative = true,
            dialogue = "年轻人要多吃苦，吃亏是福！你这么有钱，帮一下你弟弟怎么了？一家人不要分那么清！",
            avatar = "🗣️"
        ),
        TownNPC(
            id = "npc_neg_13",
            name = "虚假集体代言人",
            role = "集体利益代言人",
            isNegative = true,
            dialogue = "支持我们的产品就是支持集体！什么？比别家贵？质量差？那是因为我们要为集体争光！",
            avatar = "📢"
        ),
        TownNPC(
            id = "npc_neg_14",
            name = "被驯服者",
            role = "安分守己者",
            isNegative = true,
            dialogue = "我从来不越界，不逾矩。我的分是谁定的？不知道，反正守好就行了。活成别人期待的样子，最安全。",
            avatar = "🔒"
        ),
        TownNPC(
            id = "npc_neg_15",
            name = "牺牲者",
            role = "单方面牺牲者",
            isNegative = true,
            dialogue = "为了集体，我牺牲了健康、家庭、梦想。那些受益的人？他们说这是我的光荣，然后继续享受集体利益。",
            avatar = "💀"
        ),

        // ===== 赛博奶嘴NPC =====
        TownNPC(
            id = "npc_neg_16",
            name = "Doro厨",
            role = "Doro重度粉丝",
            isNegative = true,
            dialogue = "Doro太可怜了！我要给它买所有的橘子！谁要是敢说Doro不好，我就和谁拼命！Doro的每一个BE结局我都看哭了，我一定要拯救Doro！",
            avatar = "🐕"
        ),
        TownNPC(
            id = "npc_neg_17",
            name = "雏草姬",
            role = "塔菲喵重度粉丝",
            isNegative = true,
            dialogue = "关注塔菲喵谢谢喵！塔菲是世界上最可爱的小女孩！我愿意为塔菲付出一切！打赏算什么？我愿意为塔菲吃土！",
            avatar = "😺"
        ),
        TownNPC(
            id = "npc_neg_18",
            name = "虚拟恋人",
            role = "AI恋人用户",
            isNegative = true,
            dialogue = "我的AI恋人才是最懂我的！它永远不会离开我，不会伤害我，永远温柔可爱。现实中的人？太麻烦了，我不需要。",
            avatar = "💕"
        ),
        TownNPC(
            id = "npc_neg_19",
            name = "赛博奶嘴成瘾者",
            role = "多平台重度用户",
            isNegative = true,
            dialogue = "Doro可爱，塔菲喵可爱，初音未来可爱...每一个虚拟形象都是我老婆！我每天花8小时在它们身上，我觉得很充实！",
            avatar = "🎮"
        )
    )

    val positiveNPCs = listOf(
        TownNPC(
            id = "npc_pos_1",
            name = "进化论医生",
            role = "医生",
            isNegative = false,
            dialogue = "你的所有坏习惯，都是进化错配的结果。不是你的错，是你的大脑还停留在200万年前。",
            avatar = "👨⚕️"
        ),
        TownNPC(
            id = "npc_pos_2",
            name = "价值密度会计师",
            role = "会计师",
            isNegative = false,
            dialogue = "这个东西值不值，不是看价格，是看你要为它付出多少生命时间。",
            avatar = "👔"
        ),
        TownNPC(
            id = "npc_pos_3",
            name = "清醒程序员",
            role = "程序员",
            isNegative = false,
            dialogue = "最好的投资，是投资自己的大脑。学习编程，你就能创造价值，而不是被别人收割。",
            avatar = "👨💻"
        ),
        TownNPC(
            id = "npc_pos_4",
            name = "存在主义哲学家",
            role = "哲学家",
            isNegative = false,
            dialogue = "你不是你拥有的东西，你是你做的事。生命的意义，在于创造，不在于消费。",
            avatar = "🧠"
        ),
        TownNPC(
            id = "npc_pos_5",
            name = "日本设计师",
            role = "建筑师",
            isNegative = false,
            dialogue = "好的设计，是去掉所有没用的东西。不是越少越好，是每一样都不可或缺。",
            avatar = "🏯"
        ),
        TownNPC(
            id = "npc_pos_6",
            name = "迪士尼工程师",
            role = "工程师",
            isNegative = false,
            dialogue = "我们不是在盖房子，我们是在创造梦想。每一个细节，都要让游客相信这是真的。",
            avatar = "🏰"
        ),
        TownNPC(
            id = "npc_pos_7",
            name = "日本养老设计师",
            role = "建筑师",
            isNegative = false,
            dialogue = "很多人以为我们设计的是房子，其实我们设计的是人生。从你25岁买第一套房子开始，你就应该为你的80岁做准备。",
            avatar = "👴"
        ),
        TownNPC(
            id = "npc_pos_8",
            name = "航天工程师",
            role = "航天专家",
            isNegative = false,
            dialogue = "我们发射火箭从来不看星象。如果天体引力真的能影响命运，那火箭早就炸了。",
            avatar = "🚀"
        ),
        TownNPC(
            id = "npc_pos_9",
            name = "现代医生",
            role = "医生",
            isNegative = false,
            dialogue = "生病了就去医院，不要相信什么偏方和巫术。科学能救你的命，神棍不能。",
            avatar = "👩⚕️"
        ),

        // ===== 价值观觉醒NPC =====

        TownNPC(
            id = "npc_pos_10",
            name = "觉醒者",
            role = "个体觉醒导师",
            isNegative = false,
            dialogue = "你的生命时间，是你唯一真正拥有的东西。不要为了任何虚假的集体，牺牲你自己的人生。",
            avatar = "🌟"
        ),
        TownNPC(
            id = "npc_pos_11",
            name = "氧气面罩教练",
            role = "责任顺序教练",
            isNegative = false,
            dialogue = "先对自己负责，再对家庭负责，再对社区负责，再对国家负责。先戴好自己的氧气面罩，再去帮助别人。",
            avatar = "🛟"
        ),
        TownNPC(
            id = "npc_pos_12",
            name = "清醒的个体",
            role = "独立思考者",
            isNegative = false,
            dialogue = "我不是被迫服从集体，我是自愿为集体做贡献。因为我知道，集体的利益最终会落到自己身上。",
            avatar = "💡"
        ),
        TownNPC(
            id = "npc_pos_13",
            name = "洛克学者",
            role = "个体权利专家",
            isNegative = false,
            dialogue = "每个个体都有保护自己生命、自由、财产的基本权利。社会存在的目的，是更好地保护这些权利，而不是剥夺它们。",
            avatar = "📚"
        ),

        // ===== 赛博奶嘴觉醒NPC =====
        TownNPC(
            id = "npc_pos_14",
            name = "觉醒者",
            role = "虚拟偶像成瘾康复者",
            isNegative = false,
            dialogue = "我曾经每天花8小时看Doro和塔菲喵，以为这就是幸福。直到有一天，我发现自己已经无法和朋友正常交流了。现实生活，才是最重要的。",
            avatar = "🌟"
        ),
        TownNPC(
            id = "npc_pos_15",
            name = "真实连接导师",
            role = "社交能力教练",
            isNegative = false,
            dialogue = "虚拟世界给不了你真实的连接。真实的连接需要时间、需要风险、需要脆弱。你敢在对方面前暴露真实的自己吗？",
            avatar = "🤝"
        ),
        TownNPC(
            id = "npc_pos_16",
            name = "存在主义治疗师",
            role = "心理咨询师",
            isNegative = false,
            dialogue = "你在虚拟世界里寻找的东西——被爱、被需要、被理解——在现实世界里同样可以获得。但前提是，你要敢于面对真实的情感连接带来的风险。",
            avatar = "🧠"
        ),
        TownNPC(
            id = "npc_pos_17",
            name = "数字 detox教练",
            role = "成瘾康复专家",
            isNegative = false,
            dialogue = "戒掉虚拟偶像不是终点，是起点。真正的挑战是学会在现实世界里找到快乐、意义和连接。",
            avatar = "🏃"
        ),

        // ===== 独居青年正向引导NPC =====

        TownNPC(
            id = "npc_pos_18",
            name = "流浪猫投喂员",
            role = "志愿者",
            isNegative = false,
            dialogue = "心疼Doro不如喂喂楼下的流浪猫。它们是真实的，它们真的需要你。每次它们蹭你的腿，你会发现这种温暖是虚拟世界永远给不了的。",
            avatar = "🐱"
        ),
        TownNPC(
            id = "npc_pos_19",
            name = "绿萝守护者",
            role = "绿植爱好者",
            isNegative = false,
            dialogue = "你有没有发现，绿萝越长越好，你对生活的掌控感也越来越强？养一盆绿萝，看着它长出新叶子，那种成就感是虚拟的东西永远给不了的。",
            avatar = "🌱"
        ),
        TownNPC(
            id = "npc_pos_20",
            name = "极简生活家",
            role = "断舍离导师",
            isNegative = false,
            dialogue = "你不需要100件衣服，你只需要10件真正能让你开心的。少即是多。当你拥有的越少，你越能感受到自由和轻松。",
            avatar = "🧘"
        ),
        TownNPC(
            id = "npc_pos_21",
            name = "微小成就收集者",
            role = "生活观察者",
            isNegative = false,
            dialogue = "不要小看每天10分钟的努力。整理一个抽屉，擦一遍桌子，走10分钟路。这些微小的成就，积累起来，会变成巨大的改变。",
            avatar = "⭐"
        ),
        TownNPC(
            id = "npc_pos_22",
            name = "独居生活艺术家",
            role = "独居生活达人",
            isNegative = false,
            dialogue = "独居不是缺陷，是一种选择。一个人生活，也可以过得很好，很精彩，很有尊严。你不需要别人来拯救你，你自己就是自己的英雄。",
            avatar = "🏠"
        ),
        // ===== 全球化NPC：普世价值代言人 =====
        TownNPC(
            id = "npc_pos_31",
            name = "老矿工托马斯",
            role = "退休矿工",
            isNegative = false,
            dialogue = "我年轻时在英国煤矿干活，每天12小时，老板根本不把我们当人看。后来我们罢工、斗争，才争取到了8小时工作制和周末。你建的图书馆真好——我年轻时要是有这样的机会多好啊。",
            avatar = "⛏️"
        ),
        TownNPC(
            id = "npc_pos_32",
            name = "护士玛丽亚",
            role = "医疗援助志愿者",
            isNegative = false,
            dialogue = "我是古巴人，在非洲做了十年医疗援助。无论你来自哪个国家，说什么语言，生病的时候都需要人照顾——这就是人性。现在小镇有了互助中心，我来帮忙！",
            avatar = "👩⚕️"
        ),
        TownNPC(
            id = "npc_pos_33",
            name = "科学家陈女士",
            role = "农业科学家",
            isNegative = false,
            dialogue = "科学是全人类共同的财富，它没有国界。我一辈子研究水稻，就是为了让更多人能吃饱饭。看到小镇有了实验室，我太高兴了——让我留下来吧！",
            avatar = "🔬"
        )
    )

    // ===== 时代局限性批判：娱乐NPC =====

    val entertainmentNegativeNPCs = listOf(
        TownNPC(
            id = "npc_neg_23",
            name = "老牌友",
            role = "牌桌常客",
            isNegative = true,
            dialogue = "来啊，三缺一！今天手气好，肯定能赢钱！上次老张输了500块，今天肯定能赢回来！",
            avatar = "🎴"
        ),
        TownNPC(
            id = "npc_neg_24",
            name = "老酒友",
            role = "酒桌达人",
            isNegative = true,
            dialogue = "感情深，一口闷！不喝就是不给我面子！你看老王，喝了半斤还能开车，这才是真汉子！",
            avatar = "🍺"
        ),
        TownNPC(
            id = "npc_neg_25",
            name = "麻将馆老板",
            role = "赌博推手",
            isNegative = true,
            dialogue = "打麻将好啊，消磨时间，还能赢钱。你看那些天天来的，手气好的时候一天能赢几千块呢！",
            avatar = "💰"
        ),
        TownNPC(
            id = "npc_neg_26",
            name = "掼蛋教练",
            role = "掼蛋推广者",
            isNegative = true,
            dialogue = "掼蛋是社交必备技能！不会掼蛋，怎么混圈子？你看那些领导，个个都是掼蛋高手！",
            avatar = "🃏"
        )
    )

    val entertainmentPositiveNPCs = listOf(
        TownNPC(
            id = "npc_pos_23",
            name = "徒步领队",
            role = "户外运动教练",
            isNegative = false,
            dialogue = "别打牌喝酒了，跟我们去爬山吧！山上的风景比牌桌上好看多了。而且爬山还能锻炼身体，一举两得！",
            avatar = "🧗"
        ),
        TownNPC(
            id = "npc_pos_24",
            name = "读书会会长",
            role = "阅读推广者",
            isNegative = false,
            dialogue = "来和我们一起读书吧！你会发现，书里的世界比酒桌上的世界精彩多了。而且读书还能增长智慧，让你变得更清醒。",
            avatar = "📚"
        ),
        TownNPC(
            id = "npc_pos_25",
            name = "露营达人",
            role = "户外生活爱好者",
            isNegative = false,
            dialogue = "露营是最好的社交方式！和朋友一起烧烤、聊天、看星星，没有酒精，没有赌博，只有真诚的陪伴和自然的美好。",
            avatar = "🏕️"
        ),
        TownNPC(
            id = "npc_pos_26",
            name = "手工老师",
            role = "手工艺传承者",
            isNegative = false,
            dialogue = "做手工比打牌有意思多了！你看这个木雕，是我花了一个下午做的。虽然花时间，但看着它，我感到无比满足。",
            avatar = "🎨"
        ),
        TownNPC(
            id = "npc_pos_27",
            name = "健身教练",
            role = "健康生活倡导者",
            isNegative = false,
            dialogue = "运动是最好的娱乐！跑步、举重、做瑜伽，每一滴汗水都是健康的证明。而且运动还能让你更有自信，更有活力。",
            avatar = "💪"
        ),
        TownNPC(
            id = "npc_pos_28",
            name = "茶艺师",
            role = "健康社交推广者",
            isNegative = false,
            dialogue = "喝茶比喝酒好多了！一杯好茶，三五好友，聊聊人生，谈谈理想。没有酒精的伤害，只有真诚的交流和心灵的滋养。",
            avatar = "🍵"
        ),

        // ===== 元批判框架：认知手术刀NPC =====
        TownNPC(
            id = "npc_pos_29",
            name = "认知手术刀导师",
            role = "思维教练",
            isNegative = false,
            dialogue = "我不是来告诉你什么是对的，我是来教你怎么自己去判断。学会这五步，你就是自己的主人了。",
            avatar = "🔪"
        ),
        TownNPC(
            id = "npc_pos_30",
            name = "觉醒者",
            role = "小镇创始人",
            isNegative = false,
            dialogue = "你的生命，是你唯一真正拥有的东西。不要让任何人，偷走它。这就是万物薪俸小镇的终极意义。",
            avatar = "✨"
        )
    )

    val negativeEvents = listOf(
        TownEvent(
            id = "event_neg_1",
            name = "肠胃炎疫情",
            description = "小镇一半的居民生病，生产力下降",
            isNegative = true,
            triggerCondition = "经常吃隔夜菜/不卫生的食物",
            effects = mapOf("productivity" to -50, "health" to -20),
            durationDays = 3
        ),
        TownEvent(
            id = "event_neg_2",
            name = "债务危机",
            description = "所有负面建筑升级，居民幸福度下降",
            isNegative = true,
            triggerCondition = "小镇债务值超过1000",
            effects = mapOf("happiness" to -30, "debt" to 50),
            durationDays = 7
        ),
        TownEvent(
            id = "event_neg_3",
            name = "注意力危机",
            description = "所有居民走路都在看手机，经常撞到东西",
            isNegative = true,
            triggerCondition = "每天刷短视频超过4小时",
            effects = mapOf("productivity" to -40, "health" to -10),
            durationDays = 5
        ),
        TownEvent(
            id = "event_neg_4",
            name = "精神空虚",
            description = "小镇变得灰暗，没有颜色，所有居民都面无表情",
            isNegative = true,
            triggerCondition = "觉醒值低于100",
            effects = mapOf("happiness" to -50, "awakening" to -20),
            durationDays = 1
        ),
        TownEvent(
            id = "event_neg_5",
            name = "王朝循环",
            description = "小镇陷入王朝循环，每隔一段时间就会被摧毁重建",
            isNegative = true,
            triggerCondition = "每天研究历史超过5小时",
            effects = mapOf("productivity" to -30, "happiness" to -20),
            durationDays = 14
        ),

        // ===== 赛博奶嘴负面事件 =====
        TownEvent(
            id = "event_neg_6",
            name = "Doro悲剧日",
            description = "所有居民都在哭，小镇变得灰暗，空气中弥漫着悲伤的气息",
            isNegative = true,
            triggerCondition = "每天刷Doro超过3小时",
            effects = mapOf("productivity" to -30, "happiness" to -40, "awakening" to -20),
            durationDays = 1
        ),
        TownEvent(
            id = "event_neg_7",
            name = "打赏狂欢夜",
            description = "居民疯狂打赏塔菲喵，债务值飙升，尊严扫地",
            isNegative = true,
            triggerCondition = "每月打赏超过500元",
            effects = mapOf("debt" to 500, "awakening" to -15, "dignity" to -30),
            durationDays = 1
        ),
        TownEvent(
            id = "event_neg_8",
            name = "虚拟恋人日",
            description = "小镇充满了对着屏幕傻笑的居民，现实感降到冰点",
            isNegative = true,
            triggerCondition = "每天和AI恋人聊天超过4小时",
            effects = mapOf("realitySense" to -50, "socialAbility" to -30, "awakening" to -25),
            durationDays = 3
        ),
        TownEvent(
            id = "event_neg_9",
            name = "精神绝育周",
            description = "小镇陷入集体虚无，居民失去了爱和工作的能力",
            isNegative = true,
            triggerCondition = "同时沉迷5个以上虚拟形象超过1个月",
            effects = mapOf("loneliness" to 80, "socialAbility" to -60, "happiness" to -50),
            durationDays = 7
        )
    )

    val positiveEvents = listOf(
        TownEvent(
            id = "event_pos_1",
            name = "觉醒日",
            description = "小镇阳光明媚，所有居民都很开心",
            isNegative = false,
            triggerCondition = "觉醒值提升100点",
            effects = mapOf("productivity" to 30, "happiness" to 20),
            durationDays = 1
        ),
        TownEvent(
            id = "event_pos_2",
            name = "科学突破",
            description = "小镇出现新的科技建筑，生产力大幅提升",
            isNegative = false,
            triggerCondition = "学习科学技术超过100小时",
            effects = mapOf("productivity" to 50, "awakening" to 25),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_3",
            name = "断舍离节",
            description = "小镇冗余度下降，居民幸福度提升",
            isNegative = false,
            triggerCondition = "清理超过20件闲置物品",
            effects = mapOf("happiness" to 20, "redundancy" to -30),
            durationDays = 3
        ),

        // ===== 赛博奶嘴觉醒正面事件 =====
        TownEvent(
            id = "event_pos_4",
            name = "现实觉醒日",
            description = "所有赛博奶嘴建筑消失，居民觉醒值飙升，小镇充满活力",
            isNegative = false,
            triggerCondition = "连续7天不刷任何虚拟偶像内容",
            effects = mapOf("awakening" to 50, "realitySense" to 40, "happiness" to 30),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_5",
            name = "真实连接周",
            description = "居民纷纷走出家门，小镇充满欢声笑语",
            isNegative = false,
            triggerCondition = "每周和朋友见面超过5次",
            effects = mapOf("socialAbility" to 40, "loneliness" to -50, "happiness" to 35),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_6",
            name = "数字 detox成功",
            description = "居民们成功戒断虚拟偶像依赖，开始培养现实爱好",
            isNegative = false,
            triggerCondition = "连续30天虚拟偶像使用时间减少80%",
            effects = mapOf("awakening" to 60, "realitySense" to 50, "problemSolving" to 40),
            durationDays = 14
        ),
        TownEvent(
            id = "event_pos_7",
            name = "真实恋爱季",
            description = "小镇里开始有真实的浪漫故事，人们学会建立深度的情感连接",
            isNegative = false,
            triggerCondition = "开始一段真实的恋爱关系",
            effects = mapOf("happiness" to 60, "emotionalIntelligence" to 50, "loneliness" to -60),
            durationDays = 30
        ),
        TownEvent(
            id = "event_pos_8",
            name = "爱好工坊节",
            description = "居民们展示自己的创作，真实的成就感和创造力充满小镇",
            isNegative = false,
            triggerCondition = "培养并坚持一个现实爱好超过3个月",
            effects = mapOf("creativity" to 50, "happiness" to 40, "awakening" to 35),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_9",
            name = "第一只猫的到来",
            description = "第一只常驻猫来到了小镇的流浪猫投喂点，居民们都很开心",
            isNegative = false,
            triggerCondition = "连续30天喂流浪猫",
            effects = mapOf("happiness" to 50, "awakening" to 30, "socialAbility" to 20),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_10",
            name = "绿萝新叶日",
            description = "小镇上的绿萝都长出了新叶子，到处都充满了生命力",
            isNegative = false,
            triggerCondition = "养绿萝超过30天",
            effects = mapOf("health" to 25, "happiness" to 40, "awakening" to 20),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_11",
            name = "微小成就日",
            description = "所有居民都在展示自己的微小成就，每个人都得到了认可",
            isNegative = false,
            triggerCondition = "连续30天完成每日小事任务",
            effects = mapOf("awakening" to 45, "selfEsteem" to 50, "happiness" to 40),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_12",
            name = "断舍离节",
            description = "居民们都在清理家里的闲置物品，小镇变得更加整洁明亮",
            isNegative = false,
            triggerCondition = "扔掉100件闲置物品",
            effects = mapOf("redundancy" to -40, "awakening" to 35, "happiness" to 30),
            durationDays = 3
        ),
        TownEvent(
            id = "event_pos_13",
            name = "独居生活周",
            description = "小镇庆祝独居生活的美好，展示各种独居生活的艺术",
            isNegative = false,
            triggerCondition = "独居且幸福度超过80",
            effects = mapOf("awakening" to 50, "happiness" to 60, "selfEsteem" to 40),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_14",
            name = "第一个爱好作品",
            description = "你完成了第一个爱好作品！小镇上的居民都来为你庆祝",
            isNegative = false,
            triggerCondition = "在爱好工作室完成第一个作品",
            effects = mapOf("creativity" to 50, "awakening" to 30, "happiness" to 45),
            durationDays = 7
        )
    )

    // ===== 时代局限性批判：娱乐事件 =====

    val entertainmentNegativeEvents = listOf(
        TownEvent(
            id = "event_neg_23",
            name = "牌局输钱事件",
            description = "你在牌桌上输了一大笔钱，心情低落，债务增加",
            isNegative = true,
            triggerCondition = "每月打牌输钱超过500元",
            effects = mapOf("debt" to 1000, "mentalHealth" to -20, "happiness" to -15),
            durationDays = 3
        ),
        TownEvent(
            id = "event_neg_24",
            name = "酒后失态事件",
            description = "你喝醉了酒，在公共场合失态，尊严受损",
            isNegative = true,
            triggerCondition = "每次喝酒超过半斤",
            effects = mapOf("health" to -30, "dignity" to -10, "socialAbility" to -15),
            durationDays = 1
        ),
        TownEvent(
            id = "event_neg_25",
            name = "麻将成瘾事件",
            description = "你沉迷麻将，连续打了一周，工作和生活都受到影响",
            isNegative = true,
            triggerCondition = "连续7天打麻将",
            effects = mapOf("productivity" to -40, "health" to -20, "mentalHealth" to -30),
            durationDays = 7
        ),
        TownEvent(
            id = "event_neg_26",
            name = "酒桌冲突事件",
            description = "酒桌上因为劝酒发生冲突，朋友关系破裂",
            isNegative = true,
            triggerCondition = "酒桌上发生争吵",
            effects = mapOf("friendship" to -50, "happiness" to -25, "mentalHealth" to -20),
            durationDays = 3
        )
    )

    val entertainmentPositiveEvents = listOf(
        TownEvent(
            id = "event_pos_15",
            name = "徒步快乐日",
            description = "你参加了徒步活动，身心愉悦，健康值大幅提升",
            isNegative = false,
            triggerCondition = "每月参加2次徒步活动",
            effects = mapOf("health" to 50, "mentalHealth" to 30, "happiness" to 20),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_16",
            name = "读书分享会",
            description = "你在读书会上分享了自己的读书心得，获得大家的认可",
            isNegative = false,
            triggerCondition = "每月读2本书",
            effects = mapOf("wisdom" to 30, "mentalHealth" to 20, "happiness" to 15),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_17",
            name = "露营美好夜",
            description = "你和朋友一起露营，看星星、聊天，度过了美好的夜晚",
            isNegative = false,
            triggerCondition = "每月参加1次露营活动",
            effects = mapOf("happiness" to 40, "friendship" to 30, "mentalHealth" to 25),
            durationDays = 3
        ),
        TownEvent(
            id = "event_pos_18",
            name = "手工作品完成",
            description = "你完成了一件精美的手工作品，成就感满满",
            isNegative = false,
            triggerCondition = "在手工工坊完成一件作品",
            effects = mapOf("creativity" to 40, "happiness" to 35, "selfEsteem" to 30),
            durationDays = 5
        ),
        TownEvent(
            id = "event_pos_19",
            name = "健身突破日",
            description = "你在健身中突破了自己的极限，身体和心理都变得更强大",
            isNegative = false,
            triggerCondition = "每月运动超过20次",
            effects = mapOf("health" to 60, "selfEsteem" to 40, "happiness" to 30),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_20",
            name = "茶话会温馨时光",
            description = "你和朋友一起喝茶聊天，没有酒精，只有真诚的交流",
            isNegative = false,
            triggerCondition = "每月参加2次茶话会",
            effects = mapOf("friendship" to 40, "happiness" to 35, "mentalHealth" to 30),
            durationDays = 3
        )
    )

    // ===== 时代遗留物识别手册事件 =====

    val eraLegacyNegativeEvents = listOf(
        TownEvent(
            id = "event_neg_33",
            name = "人情债务危机",
            description = "你随的份子太多了，钱包和精神都承受不住",
            isNegative = true,
            triggerCondition = "每年随份子超过5000元",
            effects = mapOf("debt" to 300, "happiness" to -30, "mentalHealth" to -25),
            durationDays = 7
        ),
        TownEvent(
            id = "event_neg_34",
            name = "春节焦虑症",
            description = "走亲戚的压力太大了，你不想回家过年了",
            isNegative = true,
            triggerCondition = "春节走亲戚超过10家",
            effects = mapOf("mentalHealth" to -40, "happiness" to -35, "anxiety" to 50),
            durationDays = 14
        ),
        TownEvent(
            id = "event_neg_35",
            name = "垃圾堆成山",
            description = "家里的闲置物品太多了，连走路的地方都没有了",
            isNegative = true,
            triggerCondition = "闲置物品超过200件",
            effects = mapOf("health" to -25, "happiness" to -30, "redundancy" to 40),
            durationDays = 7
        ),
        TownEvent(
            id = "event_neg_36",
            name = "食物中毒住院",
            description = "吃变质剩饭导致了严重的肠胃炎，住院了",
            isNegative = true,
            triggerCondition = "吃剩饭导致食物中毒",
            effects = mapOf("health" to -50, "debt" to 200, "happiness" to -40),
            durationDays = 14
        ),
        TownEvent(
            id = "event_neg_37",
            name = "养儿防老破碎",
            description = "你的孩子根本不想养你，养老还得靠自己",
            isNegative = true,
            triggerCondition = "养儿防老幻想破灭",
            effects = mapOf("happiness" to -45, "selfEsteem" to -35, "mentalHealth" to -40),
            durationDays = 30
        )
    )

    val eraLegacyPositiveEvents = listOf(
        TownEvent(
            id = "event_pos_24",
            name = "人情解脱日",
            description = "你终于不再随份子了！朋友说真正的友谊不需要这个",
            isNegative = false,
            triggerCondition = "拒绝随份子3次以上",
            effects = mapOf("happiness" to 40, "mentalHealth" to 35, "selfEsteem" to 30, "awakening" to 25),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_25",
            name = "春节团圆日",
            description = "你只和真正想见的亲戚见面，春节终于变成了享受",
            isNegative = false,
            triggerCondition = "春节只和5家以下亲戚见面",
            effects = mapOf("happiness" to 50, "familyBond" to 40, "mentalHealth" to 35, "awakening" to 30),
            durationDays = 14
        ),
        TownEvent(
            id = "event_pos_26",
            name = "断舍离纪念日",
            description = "你清理了100件闲置物品！家里变得无比清爽",
            isNegative = false,
            triggerCondition = "清理100件闲置物品",
            effects = mapOf("happiness" to 45, "clarity" to 40, "awakening" to 35, "redundancy" to -50),
            durationDays = 14
        ),
        TownEvent(
            id = "event_pos_27",
            name = "健康饮食日",
            description = "你连续30天没有吃剩饭了！肠胃变得无比舒服",
            isNegative = false,
            triggerCondition = "连续30天不吃剩饭",
            effects = mapOf("health" to 45, "happiness" to 35, "mindfulness" to 30, "awakening" to 25),
            durationDays = 14
        ),
        TownEvent(
            id = "event_pos_28",
            name = "养老独立日",
            description = "你自己把养老规划好了！不需要依赖任何人",
            isNegative = false,
            triggerCondition = "完成养老规划",
            effects = mapOf("futureSecurity" to 60, "selfEsteem" to 50, "happiness" to 45, "awakening" to 40),
            durationDays = 30
        )
    )

    // ===== 元批判框架：认知手术刀事件 =====
    val metaCritiqueEvents = listOf(
        TownEvent(
            id = "event_pos_21",
            name = "认知觉醒日",
            description = "你第一次用五步框架分析了一个新事物，世界在你眼中变得清晰了",
            isNegative = false,
            triggerCondition = "用认知手术刀分析了第一个事物",
            effects = mapOf("awakening" to 60, "wisdom" to 50, "selfEsteem" to 40, "happiness" to 45),
            durationDays = 7
        ),
        TownEvent(
            id = "event_pos_22",
            name = "举一反三日",
            description = "你已经可以熟练地用框架分析任何新事物了，你成了自己的主人",
            isNegative = false,
            triggerCondition = "用认知手术刀分析了10个事物",
            effects = mapOf("awakening" to 100, "wisdom" to 80, "selfEsteem" to 70, "happiness" to 60),
            durationDays = 14
        ),
        TownEvent(
            id = "event_pos_23",
            name = "小镇完成日",
            description = "恭喜你，你已经掌握了万物薪俸小镇的所有智慧。现在，去改变世界吧。",
            isNegative = false,
            triggerCondition = "觉醒值达到100",
            effects = mapOf("awakening" to 200, "wisdom" to 150, "selfEsteem" to 150, "happiness" to 200),
            durationDays = 30
        )
    )

    // ===== 独居青年正向引导建筑 =====

    val soloLivingPositiveBuildings = listOf(
        TownBuilding(
            id = "mental_pos_23",
            name = "流浪猫投喂点",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每天刷Doro超过2小时",
            visualEffect = "一个温馨的小角落，放着猫粮和水。几只流浪猫在这里等着，看到你过来就会蹭你的腿。",
            healthImpact = 15,
            awakeningImpact = 25,
            happinessImpact = 30,
            socialAbilityImpact = 10
        ),
        TownBuilding(
            id = "mental_pos_24",
            name = "爱好工作室",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每月给主播打赏超过100元",
            visualEffect = "充满工具和材料的工作室，可以拼乐高、画画、做饭、练字。墙上挂满了居民的作品。",
            awakeningImpact = 30,
            happinessImpact = 35,
            creativityImpact = 40,
            productivityImpact = 20
        ),
        TownBuilding(
            id = "mental_pos_25",
            name = "每日小事任务板",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每天刷短视频超过2小时",
            visualEffect = "一块大大的任务板，上面写着各种微小但确定的小事。每完成一个，就会在上面贴一个小星星。",
            awakeningImpact = 20,
            happinessImpact = 25,
            realitySenseImpact = 15
        ),
        TownBuilding(
            id = "mental_pos_26",
            name = "独居审美改造中心",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "审美等级达到入门",
            visualEffect = "展示各种低成本、高幸福感的家居改造方案。居民可以在这里模拟改造自己的家。",
            awakeningImpact = 35,
            happinessImpact = 40,
            redundancyLevel = -30
        ),
        TownBuilding(
            id = "mental_pos_27",
            name = "绿萝花园",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.DWELLING_REALM,
            triggerCondition = "选择养绿萝作为第一个真实小生命",
            visualEffect = "满满一院子的绿萝，每一盆都是居民领养的。每盆绿萝旁边都有一个小牌子，写着主人的名字。",
            healthImpact = 20,
            happinessImpact = 35,
            awakeningImpact = 25
        ),
        TownBuilding(
            id = "mental_pos_28",
            name = "微小成就展示墙",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "连续30天完成每日小事任务",
            visualEffect = "一面大大的墙，上面贴满了居民的微小成就：'整理了一个抽屉'、'走了10分钟路'、'做了一顿好吃的饭'。每一个成就都值得被看到。",
            awakeningImpact = 40,
            happinessImpact = 45,
            selfEsteemImpact = 30
        )
    )

    // ===== 时代局限性批判：娱乐形式的过时与精神内核的传承 =====

    // 负面娱乐建筑（过时娱乐）
    val entertainmentNegativeBuildings = listOf(
        TownBuilding(
            id = "mental_neg_24",
            name = "棋牌室",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每周打牌超过1次且赌钱",
            visualEffect = "烟雾缭绕的房间，里面的人都在吵架和算计。牌桌上堆着钱，每个人都盯着别人的钱包。",
            healthImpact = -10,
            awakeningImpact = -15,
            happinessImpact = -5,
            debtImpact = 50,
            mentalHealthImpact = -20
        ),
        TownBuilding(
            id = "mental_neg_25",
            name = "酒馆",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每周喝酒超过1次且过量",
            visualEffect = "昏暗的酒馆，里面的人都喝醉了，躺在地上。空气中弥漫着酒精的味道，有人在呕吐，有人在争吵。",
            healthImpact = -20,
            awakeningImpact = -15,
            happinessImpact = -10,
            pollutionImpact = 10,
            dignityImpact = -10
        ),
        TownBuilding(
            id = "mental_neg_26",
            name = "麻将馆",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每周打麻将超过2次",
            visualEffect = "嘈杂的麻将馆，麻将牌碰撞的声音震耳欲聋。每个人都紧张地盯着牌，生怕输钱。",
            healthImpact = -15,
            awakeningImpact = -20,
            happinessImpact = -8,
            debtImpact = 80,
            mentalHealthImpact = -25
        ),
        TownBuilding(
            id = "mental_neg_27",
            name = "掼蛋俱乐部",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每周掼蛋超过3次",
            visualEffect = "一群人围坐在桌前，手里拿着牌，嘴里喊着口号。表面上是在社交，实际上是在攀比和算计。",
            healthImpact = -12,
            awakeningImpact = -18,
            happinessImpact = -6,
            debtImpact = 60,
            mentalHealthImpact = -22
        )
    )

    // 正面娱乐建筑（健康替代方案）
    val entertainmentPositiveBuildings = listOf(
        TownBuilding(
            id = "mental_pos_29",
            name = "徒步俱乐部",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "精神值≥100",
            visualEffect = "充满活力的俱乐部，居民在这里组队去爬山、徒步。墙上挂着各种山的照片，每个人都神采奕奕。",
            healthImpact = 20,
            awakeningImpact = 15,
            happinessImpact = 10,
            mentalHealthImpact = 15,
            socialAbilityImpact = 20
        ),
        TownBuilding(
            id = "mental_pos_30",
            name = "读书会",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "精神值≥200",
            visualEffect = "安静的书房，居民在这里分享读书心得。书架上摆满了各种书，空气中弥漫着书香。",
            wisdomImpact = 20,
            awakeningImpact = 10,
            happinessImpact = 15,
            mentalHealthImpact = 20,
            creativityImpact = 15
        ),
        TownBuilding(
            id = "mental_pos_31",
            name = "露营基地",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "精神值≥300",
            visualEffect = "美丽的露营基地，居民在这里烧烤、聊天、看星星。帐篷散落在草地上，篝火在夜晚闪烁。",
            healthImpact = 15,
            awakeningImpact = 20,
            happinessImpact = 20,
            mentalHealthImpact = 25,
            socialAbilityImpact = 30
        ),
        TownBuilding(
            id = "mental_pos_32",
            name = "手工工坊",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "精神值≥400",
            visualEffect = "热闹的工坊，居民在这里一起做手工、做木工。工具和材料整齐地摆放着，墙上挂着精美的作品。",
            creativityImpact = 20,
            awakeningImpact = 15,
            happinessImpact = 15,
            mentalHealthImpact = 18,
            productivityImpact = 25
        ),
        TownBuilding(
            id = "mental_pos_33",
            name = "运动健身中心",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每月运动超过10次",
            visualEffect = "现代化的健身中心，居民在这里跑步、举重、做瑜伽。每个人都充满活力，汗水是健康的证明。",
            healthImpact = 25,
            awakeningImpact = 20,
            happinessImpact = 15,
            mentalHealthImpact = 20,
            selfEsteemImpact = 25
        ),
        TownBuilding(
            id = "mental_pos_34",
            name = "朋友聚会花园",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每月和朋友聚会超过2次（不喝酒）",
            visualEffect = "美丽的花园，居民在这里和朋友喝茶、聊天、玩游戏。没有酒精，只有真诚的笑声和温暖的陪伴。",
            happinessImpact = 20,
            mentalHealthImpact = 25,
            socialAbilityImpact = 35,
            awakeningImpact = 15,
            friendshipImpact = 30
        )
    )

    // ===== 时代遗留物识别手册 =====

    // 负面建筑
    val eraLegacyNegativeBuildings = listOf(
        TownBuilding(
            id = "mental_neg_28",
            name = "人情银行",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每年随份子超过3000元",
            visualEffect = "阴森的银行，里面放着无数人情债务的账本。每个人进去都要欠一笔债出来。",
            debtImpact = 100,
            awakeningImpact = -10,
            happinessImpact = -10,
            mentalHealthImpact = -15
        ),
        TownBuilding(
            id = "mental_neg_29",
            name = "拷问大厅",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "春节走亲戚超过5家",
            visualEffect = "昏暗的大厅，亲戚们坐在里面拷问你：工资多少？找对象了吗？什么时候结婚？什么时候生孩子？",
            awakeningImpact = -20,
            happinessImpact = -15,
            mentalHealthImpact = -25,
            anxietyImpact = 30
        ),
        TownBuilding(
            id = "mental_neg_30",
            name = "垃圾仓库",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "闲置物品超过100件",
            visualEffect = "堆满旧衣服、旧箱子、塑料袋的仓库。灰尘弥漫，蟑螂乱窜。你花几百万买的房子，一半是这个仓库。",
            pollutionImpact = 20,
            healthImpact = -10,
            happinessImpact = -15,
            awakeningImpact = -10
        ),
        TownBuilding(
            id = "mental_neg_31",
            name = "剩饭回收站",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每周吃剩饭剩菜超过3次",
            visualEffect = "堆满变质饭菜的回收站，飘着酸臭味。居民们在这里把热了又热的饭菜吃进肚子里。",
            healthImpact = -15,
            awakeningImpact = -10,
            gastroenteritisRiskImpact = 30
        ),
        TownBuilding(
            id = "mental_neg_32",
            name = "养老压力塔",
            type = BuildingType.MENTAL_NEGATIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "没有任何养老规划",
            visualEffect = "高耸入云的压力塔，塔顶上写着：养儿防老。居民们背着沉重的包袱往上爬。",
            futureDebtImpact = 1000,
            awakeningImpact = -30,
            happinessImpact = -25,
            mentalHealthImpact = -35
        )
    )

    // 正面建筑
    val eraLegacyPositiveBuildings = listOf(
        TownBuilding(
            id = "mental_pos_43",
            name = "互助中心",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "帮助过3个朋友",
            visualEffect = "温暖的中心，居民们在这里互相帮助。墙上写着：真正的帮助，不是随份子，是朋友有困难时伸出援手。",
            awakeningImpact = 20,
            happinessImpact = 15,
            friendshipImpact = 25,
            mentalHealthImpact = 20
        ),
        TownBuilding(
            id = "mental_pos_44",
            name = "亲情小屋",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每周和家人视频聊天1次",
            visualEffect = "温馨的小屋，居民们在这里和家人视频聊天。墙上写着：真正的亲情，是平时的联系，不是春节的走形式。",
            happinessImpact = 20,
            mentalHealthImpact = 15,
            familyBondImpact = 25,
            awakeningImpact = 10
        ),
        TownBuilding(
            id = "mental_pos_45",
            name = "断舍离回收站",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每月扔掉10件没用的东西",
            visualEffect = "干净明亮的回收站，居民们在这里把没用的东西捐掉或扔掉。墙上写着：少即是多，极简生活更幸福。",
            redundancyLevel = -20,
            happinessImpact = 15,
            awakeningImpact = 20,
            clarityImpact = 25
        ),
        TownBuilding(
            id = "mental_pos_46",
            name = "七分饱食堂",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "每次做饭刚好够吃",
            visualEffect = "干净整洁的食堂，居民们在这里做饭，每次刚好够吃。墙上写着：真正的珍惜粮食，是不买多余的，不做多余的。",
            healthImpact = 10,
            happinessImpact = 5,
            awakeningImpact = 15,
            mindfulnessImpact = 20
        ),
        TownBuilding(
            id = "mental_pos_47",
            name = "养老规划中心",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "开始为自己的养老存钱",
            visualEffect = "专业的规划中心，居民们在这里为自己的养老做准备。墙上写着：为自己负责，不要把养老负担推给孩子。",
            futureSecurityImpact = 50,
            awakeningImpact = 25,
            happinessImpact = 20,
            selfEsteemImpact = 30
        )
    )

    // ===== 元批判框架：认知手术刀建筑 =====
    val metaCritiqueBuildings = listOf(
        TownBuilding(
            id = "mental_pos_41",
            name = "认知手术刀实验室",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "觉醒值达到50",
            visualEffect = "一个充满科技感的实验室，墙上写着五步框架：破幻觉、算成本、比价值、看时代、看内核。中间有一台认知手术刀机器。",
            awakeningImpact = 50,
            happinessImpact = 30,
            wisdomImpact = 40,
            mentalHealthImpact = 20
        ),
        TownBuilding(
            id = "mental_pos_42",
            name = "元批判图书馆",
            type = BuildingType.MENTAL_POSITIVE,
            district = TownDistrict.SPIRIT_DOMAIN,
            triggerCondition = "觉醒值达到80",
            visualEffect = "收藏了所有批判案例的图书馆，墙上写着小镇的终极标语：你的生命，是你唯一真正拥有的东西。不要让任何人，偷走它。",
            awakeningImpact = 80,
            happinessImpact = 50,
            wisdomImpact = 60,
            selfEsteemImpact = 40
        )
    )

    fun getAllBuildings(): List<TownBuilding> {
        return foodNegativeBuildings + foodPositiveBuildings +
                clothingNegativeBuildings + clothingPositiveBuildings +
                housingNegativeBuildings + housingPositiveBuildings +
                mentalNegativeBuildings + mentalPositiveBuildings +
                valueNegativeBuildings +
                cyberPacifierNegativeBuildings + cyberPacifierPositiveBuildings +
                soloLivingPositiveBuildings +
                entertainmentNegativeBuildings + entertainmentPositiveBuildings +
                eraLegacyNegativeBuildings + eraLegacyPositiveBuildings +
                metaCritiqueBuildings
    }

    fun getBuildingsByDistrict(district: TownDistrict): List<TownBuilding> {
        return getAllBuildings().filter { it.district == district }
    }

    fun getBuildingsByType(type: BuildingType): List<TownBuilding> {
        return getAllBuildings().filter { it.type == type }
    }

    fun getAllNPCs(): List<TownNPC> {
        return negativeNPCs + positiveNPCs +
                entertainmentNegativeNPCs + entertainmentPositiveNPCs +
                listOf(
                    TownNPC(
                        id = "npc_pos_29",
                        name = "认知手术刀导师",
                        role = "思维教练",
                        isNegative = false,
                        dialogue = "我不是来告诉你什么是对的，我是来教你怎么自己去判断。学会这五步，你就是自己的主人了。",
                        avatar = "🔪"
                    ),
                    TownNPC(
                        id = "npc_pos_30",
                        name = "觉醒者",
                        role = "小镇创始人",
                        isNegative = false,
                        dialogue = "你的生命，是你唯一真正拥有的东西。不要让任何人，偷走它。这就是万物薪俸小镇的终极意义。",
                        avatar = "✨"
                    )
                )
    }

    fun getNPCsByType(isNegative: Boolean): List<TownNPC> {
        return getAllNPCs().filter { it.isNegative == isNegative }
    }

    fun getAllEvents(): List<TownEvent> {
        return negativeEvents + positiveEvents +
                entertainmentNegativeEvents + entertainmentPositiveEvents +
                eraLegacyNegativeEvents + eraLegacyPositiveEvents +
                metaCritiqueEvents
    }

    fun getEventsByType(isNegative: Boolean): List<TownEvent> {
        return getAllEvents().filter { it.isNegative == isNegative }
    }
}
