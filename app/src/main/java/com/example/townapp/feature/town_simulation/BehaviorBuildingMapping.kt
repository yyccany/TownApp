package com.example.townapp.feature.town_simulation

import com.example.townapp.data.model.TownBuilding
import com.example.townapp.data.repository.TownRepository

/**
 * 四大模块的「行为 → 价值密度 → 对应建筑」映射配置。
 *
 * 设计目标：
 * 1. 覆盖「食物 / 服装 / 住房 / 认知」四大模块，和 TownRepository 的建筑枚举对齐。
 * 2. 每个 BehaviorKey 对应一张 BuildingMapping：给出价值密度基准值 + 对应建筑 id。
 * 3. 所有数值都可以被 ValueDensityCalculator 引用，用于计算单个行为的价值密度。
 *
 * 价值密度基准值的约定（与 MULTI_VALUE_SYSTEM_DESIGN.md 对齐）：
 *   ≥ 100 → SSS 级
 *   50–99 → SS 级
 *   20–49 → S 级
 *   10–19 → A 级
 *    5–9 → B 级
 *  0.1–4.9 → C 级
 *  < 0.1 → D 级
 *     < 0 → F 级（典型的"消耗生命时间"的行为）
 */
object BehaviorBuildingMapping {

    /** 模块标签，用于 UI 文字展示和统计分类。 */
    const val MODULE_FOOD = "食物"
    const val MODULE_CLOTHING = "服装"
    const val MODULE_HOUSING = "住房"
    const val MODULE_COGNITIVE = "认知"

    /**
     * 行为-建筑映射条目。
     *
     * @param module 所属模块（食物/服装/住房/认知）
     * @param behaviorKey 与 TownSimulationManager.recordUserAction 的 subType 对齐
     * @param valueDensityPerUnit 每单位（元/小时）的价值密度贡献
     * @param buildingId 对应的 TownRepository 建筑 id（food_neg_1、food_pos_2 …）
     * @param cognitiveTip 行为输入时展示的信息性提示（不含价值判断）
     * @param isNegative true = 这是负面行为，建筑会出现在"陷阱/收割"区
     */
    data class BehaviorMapping(
        val module: String,
        val behaviorKey: String,
        val valueDensityPerUnit: Double,
        val buildingId: String,
        val cognitiveTip: String,
        val isNegative: Boolean
    )

    /**
     * 四大模块的完整映射表。下标顺序不重要，重要的是 behaviorKey 和 buildingId 全局唯一。
     */
    private val mappings: List<BehaviorMapping> = listOf(
        // ============================================================
        // 食物模块
        // ============================================================
        BehaviorMapping(
            module = MODULE_FOOD,
            behaviorKey = "homemade_meal",
            valueDensityPerUnit = 25.0,
            buildingId = "food_pos_2", // 七分饱食堂
            cognitiveTip = "自己做饭：食材成本约为外卖的 1/3，用油用盐可以自己控制。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_FOOD,
            behaviorKey = "steamed_food",
            valueDensityPerUnit = 20.0,
            buildingId = "food_pos_1", // 清蒸农场
            cognitiveTip = "清蒸/白灼烹饪温度不超过 100℃，食材营养保留率较高。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_FOOD,
            behaviorKey = "takeout",
            valueDensityPerUnit = 2.0,
            buildingId = "food_neg_1", // 重金属矿山（外卖的潜在安全问题代表）
            cognitiveTip = "外卖的便利性是用更高的油盐用量和包装成本换来的。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_FOOD,
            behaviorKey = "fried_food",
            valueDensityPerUnit = -5.0,
            buildingId = "food_neg_2", // 油炸地狱
            cognitiveTip = "油炸过程中食物吸收大量油脂，热量密度显著升高。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_FOOD,
            behaviorKey = "hot_meal",
            valueDensityPerUnit = -8.0,
            buildingId = "food_neg_3", // 趁热打铁火锅店
            cognitiveTip = "65℃ 以上的食物可能损伤口腔和食道黏膜。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_FOOD,
            behaviorKey = "overnight_food",
            valueDensityPerUnit = -6.0,
            buildingId = "food_neg_4", // 隔夜菜回收站
            cognitiveTip = "绿叶蔬菜隔夜后亚硝酸盐含量会随时间逐渐上升。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_FOOD,
            behaviorKey = "too_full",
            valueDensityPerUnit = -3.0,
            buildingId = "food_neg_5", // 最低消费餐厅
            cognitiveTip = "每餐吃到七分饱，胃部不需要承受过大的消化负担。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_FOOD,
            behaviorKey = "alcohol",
            valueDensityPerUnit = -15.0,
            buildingId = "food_neg_6", // 烟酒专卖店
            cognitiveTip = "酒精代谢产生的乙醛已被 WHO 列为一级致癌物。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_FOOD,
            behaviorKey = "sugary_drink",
            valueDensityPerUnit = -12.0,
            buildingId = "food_neg_7", // 糖衣炮弹工厂
            cognitiveTip = "一杯 500ml 奶茶的含糖量约为 13 块方糖（约 65g）。",
            isNegative = true
        ),

        // ============================================================
        // 服装模块
        // ============================================================
        BehaviorMapping(
            module = MODULE_CLOTHING,
            behaviorKey = "basic_wear",
            valueDensityPerUnit = 18.0,
            buildingId = "cloth_pos_2", // 断舍离回收站（基础耐穿 = 已做到断舍离）
            cognitiveTip = "一件基础款衬衫的穿着次数通常能超过 50 次，款式越简单越不容易过时。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_CLOTHING,
            behaviorKey = "second_hand",
            valueDensityPerUnit = 15.0,
            buildingId = "cloth_pos_1", // 平民神物商店
            cognitiveTip = "二手衣物的价格约为原价的 30%，且减少了纺织品的浪费。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_CLOTHING,
            behaviorKey = "fast_fashion",
            valueDensityPerUnit = 1.5,
            buildingId = "cloth_neg_5", // 衣物囤积仓库
            cognitiveTip = "快时尚品牌的设计周期约为 2 周，衣物的平均穿着次数在下降。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_CLOTHING,
            behaviorKey = "cosmetics",
            valueDensityPerUnit = -3.0,
            buildingId = "cloth_neg_3", // 智商税化妆品店
            cognitiveTip = "皮肤护理的基础是清洁、保湿和防晒，在这三项之外的项目因人而异。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_CLOTHING,
            behaviorKey = "high_heels",
            valueDensityPerUnit = -6.0,
            buildingId = "cloth_neg_2", // 高跟鞋刑场
            cognitiveTip = "穿高跟鞋时足底压力分布会发生变化，长期穿着可能影响步态。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_CLOTHING,
            behaviorKey = "luxury_bag",
            valueDensityPerUnit = -20.0,
            buildingId = "cloth_neg_1", // 符号消费大厦
            cognitiveTip = "奢侈品的品牌溢价通常占售价的 60-80%，功能性差异远小于价格差异。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_CLOTHING,
            behaviorKey = "branded_accessory",
            valueDensityPerUnit = -8.0,
            buildingId = "cloth_neg_4", // 原装配件专卖店
            cognitiveTip = "第三方配件的价格通常为原装的 20-30%，性能差异因产品类型而异。",
            isNegative = true
        ),

        // ============================================================
        // 住房模块
        // ============================================================
        BehaviorMapping(
            module = MODULE_HOUSING,
            behaviorKey = "aging_friendly",
            valueDensityPerUnit = 35.0,
            buildingId = "housing_pos_3", // 日式极简之家
            cognitiveTip = "适老化改造的投入约为装修总预算的 5-10%，可显著降低老年人居家意外风险。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_HOUSING,
            behaviorKey = "minimalist",
            valueDensityPerUnit = 30.0,
            buildingId = "housing_pos_3", // 日式极简之家（复用）
            cognitiveTip = "物品数量减少后，清洁和整理的时间成本也随之下降。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_HOUSING,
            behaviorKey = "modern_simple",
            valueDensityPerUnit = 8.0,
            buildingId = "housing_pos_1", // 阳光公寓
            cognitiveTip = "朝南采光充足的户型在冬季可减少约 30% 的取暖能耗。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_HOUSING,
            behaviorKey = "basement_living",
            valueDensityPerUnit = -25.0,
            buildingId = "housing_neg_1", // 地下洞穴
            cognitiveTip = "地下室通风和采光条件有限，长期居住需要关注空气质量和湿度。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_HOUSING,
            behaviorKey = "mortgage_prison",
            valueDensityPerUnit = -40.0,
            buildingId = "housing_neg_2", // 房贷监狱
            cognitiveTip = "月供占收入比例超过 30% 时，家庭应急储蓄空间会受到挤压。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_HOUSING,
            behaviorKey = "private_car",
            valueDensityPerUnit = -12.0,
            buildingId = "housing_neg_3", // 停车场黑洞
            cognitiveTip = "私家车的年持有成本（油费+保险+保养+停车）通常占家庭年收入的 8-15%。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_HOUSING,
            behaviorKey = "long_commute",
            valueDensityPerUnit = -20.0,
            buildingId = "housing_neg_4", // 通勤地狱
            cognitiveTip = "单程通勤超过 45 分钟的人群，日均可用于个人发展的时间减少约 1.5 小时。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_HOUSING,
            behaviorKey = "european_luxury",
            valueDensityPerUnit = -10.0,
            buildingId = "housing_neg_5", // 欧式豪华宫
            cognitiveTip = "欧式装修中的装饰性元素（吊顶、护墙板等）约占装修总预算的 20-30%。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_HOUSING,
            behaviorKey = "instagram_deco",
            valueDensityPerUnit = -15.0,
            buildingId = "housing_neg_6", // 网红ins风小屋
            cognitiveTip = "网红风格装饰品的更换周期通常约为 6-12 个月，低于经典风格。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_HOUSING,
            behaviorKey = "clutter_maze",
            valueDensityPerUnit = -22.0,
            buildingId = "housing_neg_7", // 杂物堆迷宫
            cognitiveTip = "家中闲置物品超过 50 件时，找东西的时间成本会明显增加。",
            isNegative = true
        ),

        // ============================================================
        // 认知模块
        // ============================================================
        // ——— 普世价值·全游戏最高价值密度 ———
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "human_history",
            valueDensityPerUnit = 80.0,
            buildingId = "mental_pos_35", // 人类历史博物馆
            cognitiveTip = "了解人类从哪里来，才能知道我们要到哪里去。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "people_stories",
            valueDensityPerUnit = 75.0,
            buildingId = "mental_pos_36", // 普通人故事馆
            cognitiveTip = "历史不是英雄写的，是千千万万普通人写的。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "community_service",
            valueDensityPerUnit = 70.0,
            buildingId = "mental_pos_37", // 社区互助中心
            cognitiveTip = "互相帮助，是人类能生存至今的根本原因。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "worker_history",
            valueDensityPerUnit = 65.0,
            buildingId = "mental_pos_38", // 劳动者纪念碑
            cognitiveTip = "真正推动历史前进的，是千千万万的劳动者。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "reading",
            valueDensityPerUnit = 45.0,
            buildingId = "mental_pos_5", // 图书馆
            cognitiveTip = "一本书的信息密度约为短视频的 10-50 倍（取决于内容类型）。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "skill_learning",
            valueDensityPerUnit = 55.0,
            buildingId = "mental_pos_4", // 编程学院
            cognitiveTip = "一项可市场化的技能通常需要 100-500 小时的刻意练习入门。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "documentary",
            valueDensityPerUnit = 22.0,
            buildingId = "mental_pos_12", // 审美图书馆
            cognitiveTip = "一部 90 分钟的纪录片通常浓缩了数月的拍摄和数年的背景研究。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "programming",
            valueDensityPerUnit = 60.0,
            buildingId = "mental_pos_4", // 编程学院（复用）
            cognitiveTip = "编程本质上是将重复性工作自动化的能力。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "exercise",
            valueDensityPerUnit = 40.0,
            buildingId = "mental_pos_19", // 爱好工作室（运动类）
            cognitiveTip = "每日步行 30 分钟的年均健康收益，在统计学上相当于减少约 10% 的心血管疾病风险。",
            isNegative = false
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "short_video",
            valueDensityPerUnit = -30.0,
            buildingId = "mental_neg_2", // 短视频收割塔
            cognitiveTip = "30 分钟短视频的信息获取效率与 3 分钟阅读大致相当，但信息密度因内容类型差异很大。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "webnovel",
            valueDensityPerUnit = -25.0,
            buildingId = "mental_neg_1", // 修仙小说塔
            cognitiveTip = "网络连载小说日均更新 3000-5000 字，核心剧情推进占比较少。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "live_broadcast",
            valueDensityPerUnit = -35.0,
            buildingId = "mental_neg_3", // 直播打赏宫殿
            cognitiveTip = "直播间的收入模式主要是打赏抽成，平台抽成比例通常为 50-80%。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "online_game",
            valueDensityPerUnit = -28.0,
            buildingId = "mental_neg_5", // 网游氪金战场
            cognitiveTip = "免费网游的收入模式主要依赖少数高消费用户，约 80% 的收入来自 5% 的玩家。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "classical_chinese",
            valueDensityPerUnit = -15.0,
            buildingId = "mental_neg_7", // 古文博物馆
            cognitiveTip = "文言文是古代汉语的书面语，与现代汉语在词汇和语法上有较大差异。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "superstition",
            valueDensityPerUnit = -22.0,
            buildingId = "mental_neg_4", // 玄学算命馆
            cognitiveTip = "算命预测的准确率在统计学上无法通过盲测验证。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "pseudoscience",
            valueDensityPerUnit = -40.0,
            buildingId = "mental_neg_15", // 量子玄学馆
            cognitiveTip = "「量子」是一个具体的物理学术语，与消费品的功能描述通常没有关联。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "doro",
            valueDensityPerUnit = -20.0,
            buildingId = "mental_neg_18", // Doro 收容所
            cognitiveTip = "虚拟偶像的互动本质上是预设脚本和算法生成的即时反馈。",
            isNegative = true
        ),
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "taffy_cat",
            valueDensityPerUnit = -45.0,
            buildingId = "mental_neg_19", // 塔菲喵直播间
            cognitiveTip = "虚拟主播直播间的 ARPU（每用户平均收入）约为传统直播平台的 2-5 倍。",
            isNegative = true
        ),
        // ═══ 社交消费/金钱观/消费品认知/商业价值/时代愿景 7条 ═══
        // 1. 应酬式合群社交-负向消费
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "social_party_consume",
            valueDensityPerUnit = -7.2,
            buildingId = "mental_neg_45",
            cognitiveTip = "合群大多是不得已的选择，应酬聚餐、人情社交的花销，只是被迫融入人群的代价。",
            isNegative = true
        ),
        // 2. 面子消费
        BehaviorMapping(
            module = MODULE_CLOTHING,
            behaviorKey = "luxury_status_consume",
            valueDensityPerUnit = -12.0,
            buildingId = "cloth_neg_6",
            cognitiveTip = "金钱的本意并非购置面子与他人眼光，用来拉开人际距离、换取个人清静，才是长期价值。",
            isNegative = true
        ),
        // 3. 消费品幸福局限性
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "commodity_happiness",
            valueDensityPerUnit = -4.8,
            buildingId = "mental_neg_46",
            cognitiveTip = "实体商品无法带来长久幸福感，清闲、拒绝被迫事务的选择权，才是稳定的幸福来源。",
            isNegative = true
        ),
        // 4. 自由的底层定义（正向认知）
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "freedom_definition",
            valueDensityPerUnit = 22.0,
            buildingId = "mental_pos_40",
            cognitiveTip = "自由的极致，是拥有离开所有不喜欢的人和事的底气，财富本质在换取这份选择权。",
            isNegative = false
        ),
        // 5. 商业市场化的慈善价值（正向产业认知）
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "market_charity_view",
            valueDensityPerUnit = 28.5,
            buildingId = "mental_pos_41",
            cognitiveTip = "商业是高效的慈善，流通盘活闲置物资，依托生产改造基础物料，创造全新社会价值。",
            isNegative = false
        ),
        // 6. 短期功利焦虑思维
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "impatient_result_pursuit",
            valueDensityPerUnit = -6.5,
            buildingId = "mental_neg_47",
            cognitiveTip = "急于索取结果会让目标变得功利，功利化之后，人便很难享受事情本身的过程。",
            isNegative = true
        ),
        // 7. AI时代小镇长期愿景（顶层全局认知）
        BehaviorMapping(
            module = MODULE_COGNITIVE,
            behaviorKey = "town_future_vision",
            valueDensityPerUnit = 35.0,
            buildingId = "mental_pos_42",
            cognitiveTip = "依托AI与小镇模拟系统，我们可以立足当下，为未来数十年的人类生活模式规划长期参照目标。",
            isNegative = false
        )
    )

    /** 快速根据 (type, subType) 从外层 recordUserAction 查找到建筑映射。 */
    fun findByBehavior(type: String, subType: String): BehaviorMapping? {
        // 为了和 TownSimulationManager.recordUserAction 保持兼容，
        // 我们优先用 subType 精确匹配；如果找不到再用 type 做一次兜底。
        return mappings.firstOrNull { it.behaviorKey == subType }
            ?: mappings.firstOrNull { it.module.equals(type, ignoreCase = true) }
    }

    fun findByBuildingId(buildingId: String): BehaviorMapping? {
        return mappings.firstOrNull { it.buildingId == buildingId }
    }

    /** 根据 behaviorKey 获取 cognitiveTip（供CognitiveTipRepository桥接使用） */
    fun getCognitiveTip(behaviorKey: String): String? {
        return mappings.firstOrNull { it.behaviorKey == behaviorKey }?.cognitiveTip
    }

    /** 向外暴露全部映射（供跨模块查询使用） */
    fun getAllMappings(): List<BehaviorMapping> = mappings

    /** 返回 TownRepository 中对应的 TownBuilding，供 UI 层取名字和描述使用。 */
    fun resolveBuilding(mapping: BehaviorMapping): TownBuilding? {
        val allBuildings = TownRepository.foodNegativeBuildings +
            TownRepository.foodPositiveBuildings +
            TownRepository.clothingNegativeBuildings +
            TownRepository.clothingPositiveBuildings +
            TownRepository.housingNegativeBuildings +
            TownRepository.housingPositiveBuildings +
            TownRepository.mentalPositiveBuildings +
            TownRepository.mentalNegativeBuildings +
            TownRepository.valueNegativeBuildings +
            TownRepository.cyberPacifierNegativeBuildings +
            TownRepository.cyberPacifierPositiveBuildings
        return allBuildings.firstOrNull { it.id == mapping.buildingId }
    }

    /** 列出所有映射，供调试和统计 UI 使用。 */
    fun all(): List<BehaviorMapping> = mappings
}
