package com.example.townapp.domain.consumption

/**
 * 小镇消费价值观系统 —— 以人为本，分清本末
 *
 * 核心设计原则：
 * 1. 无对错评判，只用客观数值展示不同取舍的长期结果
 * 2. 外物（美食/服饰/装修/车辆）只是工具，人的身心才是核心资源
 * 3. 两种路线都能走完人生，只是长期状态不同：
 *    - 人本路线（优先健康/舒适/安全）→ 长期身心稳定，少病痛，自由时间多
 *    - 虚荣路线（优先排场/溢价/外观）→ 短期情绪满足，长期身心损耗，债务压力
 */
object ConsumptionSystem {

    /**
     * 四大消费维度评分（0~100）
     * 分值越高 = 越偏向「以人为本」（钱花在刀刃上，养护自身）
     * 分值越低 = 越偏向「舍本逐末」（砸钱在外在排场上，透支健康）
     */
    data class OrientationScore(
        val food: Int = 50,        // 吃：日常优质食材 vs 宴席网红溢价
        val clothing: Int = 50,    // 穿：贴身舒适 vs 大牌logo
        val housing: Int = 50,     // 住：环保健康家电 vs 网红软装颜值
        val transport: Int = 50,   // 行：安全护具 vs 豪车排面贷款
    ) {
        /** 综合人本取向评分（0~100） */
        val overall: Int get() = ((food + clothing + housing + transport) / 4).coerceIn(0, 100)

        /** 是否整体偏向人本路线（≥60） */
        val isPeopleOriented: Boolean get() = overall >= 60

        /** 是否整体偏向虚荣透支路线（≤40） */
        val isVanityDriven: Boolean get() = overall <= 40
    }

    /**
     * 长期消费习惯累积的身心状态修正
     */
    data class LongTermEffect(
        val healthDeltaPerDay: Double = 0.0,      // 每日健康增减
        val moodDeltaPerDay: Double = 0.0,        // 每日情绪增减
        val fatigueMultiplier: Double = 1.0,      // 精力消耗倍率（>1 = 更累，<1 = 更轻松）
        val sleepQualityDelta: Double = 0.0,      // 睡眠质量修正（正=更好）
        val illnessProbabilityMultiplier: Double = 1.0, // 生病概率倍率
        val dreamNightmareProbabilityDelta: Double = 0.0, // 噩梦概率增减
        val freeTimeBonusHoursPerWeek: Double = 0.0, // 每周额外自由时间（人本路线省下来的就医/打扫/还贷时间）
    )

    /**
     * 基于当前消费倾向评分，计算长期效果
     * 不评判，只客观呈现：你怎么花钱，身体就怎么反馈给你
     */
    fun calculateLongTermEffect(score: OrientationScore): LongTermEffect {
        val overall = score.overall

        // 线性映射：0分（极致虚荣）<-> 100分（极致人本）
        // 中心点50分：无增益无损耗
        val normalized = (overall - 50) / 50.0 // -1.0 ~ +1.0

        return LongTermEffect(
            healthDeltaPerDay = normalized * 0.15,           // 极致人本：每日+0.15健康；极致虚荣：每日-0.15健康
            moodDeltaPerDay = normalized * 0.1,              // 情绪同步缓慢变化
            fatigueMultiplier = 1.0 - normalized * 0.3,      // 人本路线精力消耗降低30%，虚荣路线升高30%
            sleepQualityDelta = normalized * 0.2,            // 睡眠质量±20%
            illnessProbabilityMultiplier = 1.0 - normalized * 0.5, // 人本路线生病概率减半，虚荣翻倍
            dreamNightmareProbabilityDelta = -normalized * 0.3,     // 人本路线噩梦概率降30%，虚荣升30%
            freeTimeBonusHoursPerWeek = normalized * 5.0     // 极致人本每周多5小时自由时间，极致虚荣少5小时（还贷/治病/打扫）
        )
    }

    // ============================================
    // 饮食维度评分更新
    // ============================================

    /**
     * 吃了一份食物后，更新饮食维度评分
     * - 优质天然食材（高healthDelta、高gutHealthDelta）→ 加分
     * - 溢价网红/高油高糖/快餐 → 减分
     */
    fun updateFoodScore(current: Int, food: com.example.townapp.data.DietSystem.FoodItem): Int {
        var delta = 0
        // 健康食材加分
        if (food.healthDelta >= 0.2) delta += 3
        if (food.gutHealthDelta >= 0.2) delta += 2
        // 高油高糖溢价/快餐减分
        if (food.healthDelta <= -0.2) delta -= 2
        if (food.fatigueDelta >= 0.2) delta -= 2
        if (food.category == com.example.townapp.data.DietSystem.FoodCategory.FAST_FOOD) delta -= 1
        // 宴席/高溢价大额消费（cost>40）如果不健康，额外减分（为了排场吃贵的不健康食物）
        if (food.cost > 40 && food.healthDelta < 0) delta -= 3
        return (current + delta).coerceIn(0, 100)
    }

    // ============================================
    // 穿搭维度评分更新
    // ============================================

    /**
     * 穿一件衣物后，更新穿搭维度评分
     * 材质、是否贴身、是否为智商税溢价决定加减分
     */
    data class ClothingInfo(
        val material: String,
        val isBaseLayer: Boolean,  // 是否贴身内衣/睡衣/鞋袜
        val isIQTax: Boolean,      // 是否为溢价智商税（大牌logo限量款）
        val comfortBonus: Int      // 舒适度加成
    )

    fun updateClothingScore(current: Int, clothing: ClothingInfo): Int {
        var delta = 0
        // 贴身衣物选好材质 → 大幅加分
        if (clothing.isBaseLayer) {
            if (clothing.comfortBonus >= 2) delta += 5
            if (clothing.comfortBonus <= 0) delta -= 3
        }
        // 溢价智商税 → 减分
        if (clothing.isIQTax) delta -= 3
        // 舒适实用 → 加分
        if (clothing.comfortBonus >= 1 && !clothing.isIQTax) delta += 1
        return (current + delta).coerceIn(0, 100)
    }

    // ============================================
    // 居家维度评分更新
    // ============================================

    /**
     * 家居设备/装修类型
     */
    enum class HomeItemType {
        /** 环保基材/新风/净水/除湿/护眼/静音 → 人本路线，大幅加分 */
        HEALTH_APPLIANCE,
        /** 装饰摆件/网红背景墙/轻奢吊顶 → 颜值溢价，减分 */
        DECORATION,
        /** 基础实用家具 → 中性 */
        BASIC_FURNITURE
    }

    fun updateHousingScore(current: Int, itemType: HomeItemType, cost: Double): Int {
        var delta = 0
        when (itemType) {
            HomeItemType.HEALTH_APPLIANCE -> delta += 8  // 健康家电永久加分
            HomeItemType.DECORATION -> {
                delta -= (cost / 100).toInt().coerceAtMost(5) // 越贵的装饰减分越多
            }
            HomeItemType.BASIC_FURNITURE -> delta += 1
        }
        return (current + delta).coerceIn(0, 100)
    }

    // ============================================
    // 出行维度评分更新
    // ============================================

    /**
     * 代步/通勤配件类型
     */
    enum class TransportItemType {
        /** 安全配件/护腰坐垫/车载净化/防爆轮胎 → 人本路线加分 */
        SAFETY_COMFORT,
        /** 豪车贷款/外观改装/车标氛围灯 → 排面溢价减分 */
        LUXURY_APPEARANCE,
        /** 普通代步工具 → 中性 */
        BASIC_TRANSPORT
    }

    data class TransportPurchase(
        val itemType: TransportItemType,
        val hasLoan: Boolean,  // 是否贷款购买
        val monthlyLoanPayment: Double = 0.0
    )

    fun updateTransportScore(current: Int, purchase: TransportPurchase): Int {
        var delta = 0
        when (purchase.itemType) {
            TransportItemType.SAFETY_COMFORT -> delta += 6
            TransportItemType.LUXURY_APPEARANCE -> delta -= 5
            TransportItemType.BASIC_TRANSPORT -> delta += 1
        }
        // 贷款买豪车 → 大幅减分（每月强制还贷，压缩自由时间）
        if (purchase.hasLoan && purchase.itemType == TransportItemType.LUXURY_APPEARANCE) {
            delta -= (purchase.monthlyLoanPayment / 500).toInt().coerceAtMost(5)
        }
        return (current + delta).coerceIn(0, 100)
    }

    /**
     * 吃了一份食物后，更新饮食维度评分（适配现有data.FoodItem）
     * - 高营养密度食物 → 加分（钱花在营养上，养护身体）
     * - 智商税/低营养密度高溢价食物 → 减分（钱花在营销/排场上，对身体无益处）
     */
    fun updateFoodScore(current: Int, food: com.example.townapp.data.FoodItem): Int {
        var delta = 0
        // 营养密度≥60 → 优质食物，加分
        if (food.nutrientDensityScore >= 60) delta += 3
        if (food.nutrientDensityScore >= 80) delta += 2
        // 营养密度<30 → 空热量/垃圾食品，减分
        if (food.nutrientDensityScore < 30) delta -= 2
        // 智商税溢价 → 减分
        if (food.isIQTax) delta -= 2 + food.iqTaxLevel
        // 价格高但营养低 → 额外减分（花大价钱吃没营养的东西，典型舍本逐末）
        val cost = food.pricePer100g * food.typicalServing / 100.0
        if (cost > 30 && food.nutrientDensityScore < 40) delta -= 2
        return (current + delta).coerceIn(0, 100)
    }

    /**
     * 穿一件衣物后，更新穿搭维度评分（适配现有ClothingItem）
     * - 亲肤材质/基础舒适款 → 加分
     * - 大牌logo/智商税/低舒适度 → 减分
     */
    fun updateClothingScore(
        current: Int,
        isBaseLayer: Boolean,
        comfortScore: Int,
        isIQTax: Boolean,
        price: Double
    ): Int {
        var delta = 0
        // 贴身衣物选舒适的 → 大幅加分
        if (isBaseLayer) {
            if (comfortScore >= 7) delta += 5
            if (comfortScore <= 3) delta -= 3
        }
        // 舒适实用外衣 → 加分
        if (comfortScore >= 6 && !isIQTax) delta += 1
        // 智商税溢价衣物 → 减分
        if (isIQTax) delta -= 3
        // 高价但不舒适 → 额外减分
        if (price > 500 && comfortScore < 5) delta -= 2
        return (current + delta).coerceIn(0, 100)
    }


    /** 获取当前消费倾向的温柔描述文案（不评判，只看见） */
    fun getOrientationNarrative(score: OrientationScore): String? {
        val overall = score.overall
        return when {
            overall >= 80 -> "你把钱大多花在了让自己舒服的地方，日子安稳，身体轻快。"
            overall in 60..79 -> "你慢慢学会了先照顾好自己，外在的排场没那么重要了。"
            overall in 41..59 -> null // 中间状态不说话
            overall in 21..40 -> "最近为了撑场面花了不少，身体好像有点累了。"
            else -> "大额开销、人情往来、颜值排场堆在一起，身体在悄悄发出信号。"
        }
    }
}
