package com.example.townapp.data

/**
 * 材质-成分精细化系统（v2.13 物资微观参数层）
 *
 * 核心设计理念：
 * 1. 沿用现有物资框架，只细化底层参数，不打乱整体结构
 * 2. 每种成分具备恒定基础特性，环境、天气只改变实际效果
 * 3. 成分长期决定健康上限，仅作为微调级参数
 * 4. 不凌驾家境、个人信念、时代等核心变量之上
 *
 * 分层：
 *   面料成分（棉/麻/化纤/羊毛）→ 洗涤剂（皂基/温和）→ 穿戴健康
 *   药品成分（脚气药/感冒药/慢性病药/抗生素）→ 药效+副作用 → 体质
 *   日用品成分（除湿剂/香皂/护肤品）→ 环境+皮肤 → 健康微调
 */

// ============================================
// 一、面料成分体系
// ============================================

/**
 * 面料纤维类型
 * 每种纤维特性恒定，环境只调整场景收益
 */
enum class FabricType(
    val label: String,
    /** 透气性 0-1（越高越透气） */
    val breathability: Double,
    /** 吸汗能力 0-1（越高越吸汗） */
    val moistureAbsorption: Double,
    /** 锁温能力 0-1（越高越保暖） */
    val thermalRetention: Double,
    /** 面料柔软度 0-1（越低越硬，摩擦擦伤风险越高） */
    val softness: Double,
    /** 防水性 0-1 */
    val waterproof: Double,
    /** 霉菌滋生倾向 0-1（越高越容易发霉） */
    val moldRisk: Double,
    /** 干燥速度 0-1（越高干得越快） */
    val dryingSpeed: Double,
    /** 价格系数（相对基准） */
    val priceCoefficient: Double,
    val description: String
) {
    /** 纯棉：天然棉纤维，透气吸汗但锁温一般，潮湿环境易发霉 */
    COTTON(
        "纯棉", breathability = 0.7, moistureAbsorption = 0.8,
        thermalRetention = 0.3, softness = 0.75, waterproof = 0.1,
        moldRisk = 0.55, dryingSpeed = 0.4, priceCoefficient = 1.0,
        "棉纤维孔隙大，透气吸汗——夏天出汗后湿气排得快，脚气风险低。但寒冬单穿锁不住体温，湿了不好干。"
    ),
    /** 亚麻：植物韧皮纤维，透气性极佳但偏硬，适合体力劳动 */
    LINEN(
        "亚麻", breathability = 0.85, moistureAbsorption = 0.7,
        thermalRetention = 0.15, softness = 0.35, waterproof = 0.05,
        moldRisk = 0.3, dryingSpeed = 0.75, priceCoefficient = 0.9,
        "亚麻透气散热一流，酷暑穿着比棉还凉快。但面料偏硬，长期贴身干活会磨皮肤——适合户外体力活。"
    ),
    /** 聚酯化纤：人工合成纤维，防水但闷热，真菌滋生风险高 */
    POLYESTER(
        "聚酯化纤", breathability = 0.2, moistureAbsorption = 0.1,
        thermalRetention = 0.25, softness = 0.5, waterproof = 0.85,
        moldRisk = 0.15, dryingSpeed = 0.9, priceCoefficient = 0.6,
        "化纤不吸水、不透气——雨淋不湿但汗闷在里面。盛夏穿化纤鞋袜，脚气风险翻倍。便宜，但脚替你付了代价。"
    ),
    /** 羊毛：动物蛋白纤维，御寒极强但夏季灾难，湿了难干 */
    WOOL(
        "羊毛", breathability = 0.4, moistureAbsorption = 0.6,
        thermalRetention = 0.9, softness = 0.7, waterproof = 0.2,
        moldRisk = 0.5, dryingSpeed = 0.15, priceCoefficient = 2.5,
        "羊毛卷曲纤维锁住空气——寒冬最强保暖。但夏天穿就是蒸笼，湿了又重又难干——真菌的最爱。"
    )
}

/**
 * 面料成分 对穿戴健康的影响
 */
object FabricHealthLink {

    /**
     * 根据面料和季节计算脚气风险调整
     * @param fabric 面料类型
     * @param temperature 当前温度
     * @param humidity 湿度 0-1
     */
    fun footFungusRiskModifier(fabric: FabricType, temperature: Double, humidity: Double): Int {
        var risk = 0
        // 夏季高温 + 低透气 → 闷热滋生真菌
        if (temperature > 25 && fabric.breathability < 0.4) risk += 12
        // 高湿 + 慢干 → 长期潮湿
        if (humidity > 0.6 && fabric.dryingSpeed < 0.3) risk += 8
        // 纯棉在潮湿环境霉菌风险
        if (fabric == FabricType.COTTON && humidity > 0.65) risk += 5
        // 化纤盛夏加倍
        if (fabric == FabricType.POLYESTER && temperature > 28) risk += 10
        // 羊毛潮湿后滋生真菌
        if (fabric == FabricType.WOOL && humidity > 0.5 && temperature > 15) risk += 7
        return risk
    }

    /**
     * 面料对皮肤擦伤风险的加成
     * 亚麻偏硬 → 长期摩擦擦伤
     */
    fun skinChafingModifier(fabric: FabricType, isManualLabor: Boolean): Double {
        if (!isManualLabor) return 0.0
        val hardnessPenalty = (1.0 - fabric.softness) * 0.08
        return hardnessPenalty.coerceIn(0.0, 0.1)
    }

    /**
     * 面料对受寒风险的影响
     * 温度低 + 锁温差 → 感冒概率
     */
    fun coldRiskModifier(fabric: FabricType, temperature: Double): Double {
        if (temperature > 15) return 0.0
        val insulationGap = (1.0 - fabric.thermalRetention) * (15.0 - temperature) / 30.0
        return insulationGap.coerceIn(0.0, 0.15)
    }

    /**
     * 面料对酷暑疲劳的影响
     * 锁温强 + 高温 → 燥热疲惫
     */
    fun heatFatigueModifier(fabric: FabricType, temperature: Double): Int {
        if (temperature < 28) return 0
        return (fabric.thermalRetention * 5).toInt()
    }

    /**
     * 面料对皮肤炎症的风险
     * 纯棉在潮湿环境霉菌滋生 → 皮炎
     */
    fun skinInflammationRisk(fabric: FabricType, humidity: Double): Int {
        if (fabric.moldRisk > 0.4 && humidity > 0.6) {
            return (fabric.moldRisk * humidity * 15).toInt()
        }
        return 0
    }
}

// ============================================
// 二、洗涤剂成分
// ============================================

/**
 * 洗涤剂类型
 */
enum class DetergentType(
    val label: String,
    /** 清洁力 0-1 */
    val cleaningPower: Double,
    /** 皮肤刺激性 0-1 */
    val skinIrritation: Double,
    /** 硬水残留程度 0-1 */
    val hardWaterResidue: Double,
    /** 价格（月消耗/元） */
    val monthlyCost: Double,
    val description: String
) {
    /** 皂基：清洁力强但刺激，硬水地区残留严重 */
    SOAP_BASED(
        "皂基洗衣粉", cleaningPower = 0.9, skinIrritation = 0.6,
        hardWaterResidue = 0.7, monthlyCost = 8.0,
        "皂基去污猛——油渍汗渍一遍就干净。但水质硬的地区容易残留，洗完之后手会干得起皮。便宜，好用——但你的手替你付了代价。"
    ),
    /** 温和合成洗衣液：刺激低但贵 */
    MILD_DETERGENT(
        "温和洗衣液", cleaningPower = 0.75, skinIrritation = 0.15,
        hardWaterResidue = 0.1, monthlyCost = 25.0,
        "温和配方——手不会起皮，衣服洗完了有淡淡的清香。比皂基贵三倍——但你的手觉得值。"
    )
}

/**
 * 洗涤剂对皮肤的影响
 */
object DetergentSkinLink {
    /**
     * 计算洗涤剂导致的手部脱皮风险
     * @param detergent 洗涤剂类型
     * @param waterHardness 水硬度 0-1
     * @param laundryFrequency 每周洗衣次数
     */
    fun handPeelingRisk(
        detergent: DetergentType,
        waterHardness: Double,
        laundryFrequency: Int
    ): Double {
        val baseRisk = detergent.skinIrritation * 0.05
        val hardWaterBonus = if (waterHardness > 0.5) detergent.hardWaterResidue * 0.04 else 0.0
        val frequencyMultiplier = (laundryFrequency / 2.0).coerceAtMost(2.0)
        return (baseRisk + hardWaterBonus) * frequencyMultiplier
    }
}

// ============================================
// 三、药品成分体系
// ============================================

/**
 * 药品品类
 */
enum class DrugCategory(val label: String) {
    ANTIFUNGAL("脚气/真菌药"),
    COLD_REMEDY("感冒药"),
    CHRONIC_DISEASE("慢性病药"),
    ANTIBIOTIC("抗生素"),
    PAIN_RELIEF("止痛药")
}

/**
 * 药品成分类型
 */
enum class DrugIngredient(
    val label: String,
    val category: DrugCategory,
    /** 基础药效 0-1 */
    val efficacy: Double,
    /** 起效周期（天） */
    val onsetDays: Int,
    /** 副作用严重度 0-1 */
    val sideEffectSeverity: Double,
    /** 副作用类型 */
    val sideEffectType: SideEffectType,
    /** 价格等级（1-5） */
    val priceTier: Int,
    /** 耐药性风险 0-1（抗生素专属，其他为0） */
    val resistanceRisk: Double = 0.0,
    val description: String
) {
    // ===== 脚气/真菌药 =====

    /** 酮康唑：一线抗真菌，起效稳定 */
    KETOCONAZOLE(
        "酮康唑", DrugCategory.ANTIFUNGAL, efficacy = 0.85, onsetDays = 28,
        sideEffectSeverity = 0.1, sideEffectType = SideEffectType.NONE,
        priceTier = 2, resistanceRisk = 0.0,
        description = "酮康唑抑菌成分，常规4-6周根除真菌——起效不算最快，但稳。副作用低，大多数人都能用。"
    ),
    /** 廉价抑菌膏：见效慢但便宜 */
    CHEAP_ANTIFUNGAL(
        "廉价抑菌膏", DrugCategory.ANTIFUNGAL, efficacy = 0.45, onsetDays = 56,
        sideEffectSeverity = 0.05, sideEffectType = SideEffectType.NONE,
        priceTier = 1, resistanceRisk = 0.0,
        description = "便宜——几块钱一支。但见效很慢，拖久了容易转成慢性脚气。省钱和忍受之间——你得自己选。"
    ),
    /** 强效抗真菌药：见效快但有副作用 */
    STRONG_ANTIFUNGAL(
        "强效抗真菌剂", DrugCategory.ANTIFUNGAL, efficacy = 0.95, onsetDays = 14,
        sideEffectSeverity = 0.35, sideEffectType = SideEffectType.SKIN_PEELING,
        priceTier = 4, resistanceRisk = 0.0,
        description = "药效猛——两周就能看到效果。但皮肤可能会脱皮、泛红——用的时候有点不舒服。快和舒服——它选了前者。"
    ),

    // ===== 感冒药 =====

    /** 中成药：温和调理 */
    HERBAL_COLD(
        "中成药", DrugCategory.COLD_REMEDY, efficacy = 0.5, onsetDays = 5,
        sideEffectSeverity = 0.02, sideEffectType = SideEffectType.NONE,
        priceTier = 2, resistanceRisk = 0.0,
        description = "温和调理——不伤胃、不犯困。但也别指望一粒就退烧。慢慢来——感冒也是，人生也是。"
    ),
    /** 布洛芬：解热镇痛快但有伤胃风险 */
    IBUPROFEN(
        "布洛芬", DrugCategory.COLD_REMEDY, efficacy = 0.85, onsetDays = 1,
        sideEffectSeverity = 0.2, sideEffectType = SideEffectType.STOMACH_DAMAGE,
        priceTier = 1, resistanceRisk = 0.0,
        description = "退烧速度最快——半小时见效。但长期吃会伤胃黏膜——别把它当糖丸。"
    ),

    // ===== 慢性病药 =====

    /** 降压药：长期服用维持体质，固定月支出 */
    ANTIHYPERTENSIVE(
        "降压药", DrugCategory.CHRONIC_DISEASE, efficacy = 0.7, onsetDays = 30,
        sideEffectSeverity = 0.08, sideEffectType = SideEffectType.NONE,
        priceTier = 3, resistanceRisk = 0.0,
        description = "不是为了治——是为了稳。每月固定花一笔钱，换血压平稳。这不是奢侈——是你的身体需要的基础维护。"
    ),

    // ===== 抗生素 =====

    /** 普通抗生素 */
    BASIC_ANTIBIOTIC(
        "普通抗生素", DrugCategory.ANTIBIOTIC, efficacy = 0.8, onsetDays = 3,
        sideEffectSeverity = 0.15, sideEffectType = SideEffectType.GUT_FLORA,
        priceTier = 2, resistanceRisk = 0.15,
        description = "针对细菌感染——见效快，但用多了以后就不好使了。抗生素不是水——别忘了关水龙头。"
    ),
    /** 强效抗生素 */
    STRONG_ANTIBIOTIC(
        "强效抗生素", DrugCategory.ANTIBIOTIC, efficacy = 0.95, onsetDays = 2,
        sideEffectSeverity = 0.3, sideEffectType = SideEffectType.GUT_FLORA,
        priceTier = 5, resistanceRisk = 0.25,
        description = "重症强效——但价格昂贵。更关键的是——滥用会让细菌学会反抗。下次再生病的时候——你可能无药可用了。"
    ),

    // ===== 止痛药 =====

    /** 红糖姜茶：温和缓解痛经 */
    BROWN_SUGAR_GINGER(
        "红糖姜茶", DrugCategory.PAIN_RELIEF, efficacy = 0.3, onsetDays = 1,
        sideEffectSeverity = 0.0, sideEffectType = SideEffectType.NONE,
        priceTier = 1, resistanceRisk = 0.0,
        description = "古老的方法——不是药，但确实能让肚子舒服一点。红糖和姜——大自然给的止痛片。"
    ),
    /** 布洛芬止痛 */
    IBUPROFEN_PAIN(
        "布洛芬止痛", DrugCategory.PAIN_RELIEF, efficacy = 0.8, onsetDays = 1,
        sideEffectSeverity = 0.2, sideEffectType = SideEffectType.STOMACH_DAMAGE,
        priceTier = 1, resistanceRisk = 0.0,
        description = "半小时止痛——但别空腹吃。胃告诉你的事——你要听。"
    )
}

/**
 * 副作用类型
 */
enum class SideEffectType(val label: String, val description: String) {
    NONE("无", "无明显副作用"),
    SKIN_PEELING("皮肤脱皮", "用药后皮肤可能出现轻微脱皮、泛红"),
    STOMACH_DAMAGE("肠胃损伤", "长期服用可能损伤胃黏膜，降低身体素质基线"),
    GUT_FLORA("肠道菌群紊乱", "抗生素会杀死肠道有益菌，抵抗力短期下降")
}

/**
 * 药品运行时效果
 */
data class DrugEffect(
    val ingredient: DrugIngredient,
    /** 经耐药性调整后的实际药效 */
    val actualEfficacy: Double,
    /** 是否触发副作用 */
    val sideEffectTriggered: Boolean,
    /** 疲劳值临时增加 */
    val fatigueDelta: Int = 0,
    /** 健康值变化 */
    val healthDelta: Int = 0,
    /** 月度药费 */
    val monthlyCost: Double = 0.0
)

/**
 * 药品系统逻辑
 */
object DrugSystem {

    /**
     * 计算实际药效（含耐药性衰减）
     * @param ingredient 药品成分
     * @param accumulatedResistance 该品类累计耐药性 0-1
     */
    fun calculateEfficacy(ingredient: DrugIngredient, accumulatedResistance: Double): Double {
        val resistancePenalty = accumulatedResistance * ingredient.resistanceRisk
        return (ingredient.efficacy * (1.0 - resistancePenalty)).coerceIn(0.1, 1.0)
    }

    /**
     * 判断是否触发副作用
     */
    fun checkSideEffect(ingredient: DrugIngredient): Boolean {
        return Math.random() < ingredient.sideEffectSeverity
    }

    /**
     * 使用药品后更新脚气状态
     */
    fun applyAntifungal(
        ingredient: DrugIngredient,
        currentFungusRisk: Int,
        accumulatedResistance: Double
    ): DrugEffect {
        val efficacy = calculateEfficacy(ingredient, accumulatedResistance)
        val sideEffect = checkSideEffect(ingredient)
        val fungusReduction = (efficacy * 30).toInt()

        return DrugEffect(
            ingredient = ingredient,
            actualEfficacy = efficacy,
            sideEffectTriggered = sideEffect,
            fatigueDelta = if (sideEffect) 3 else 0,
            healthDelta = if (sideEffect && ingredient.sideEffectType == SideEffectType.SKIN_PEELING) -1 else 0
        )
    }

    /**
     * 凝结慢性耐药性
     * 每次使用抗生素，该品类耐药性累积
     */
    fun accumulateResistance(current: Double, ingredient: DrugIngredient): Double {
        if (ingredient.resistanceRisk == 0.0) return current
        return (current + ingredient.resistanceRisk * 0.1).coerceIn(0.0, 1.0)
    }
}

// ============================================
// 四、日用品成分
// ============================================

/**
 * 日用消耗品类型
 */
enum class HouseholdProductType(
    val label: String,
    /** 基础效果 0-1 */
    val baseEffect: Double,
    /** 月消耗基准（潮湿地区自动上调） */
    val monthlyUsageBase: Int,
    /** 月均花费（基准） */
    val monthlyCost: Double,
    /** 适用健康目标 */
    val healthTarget: HouseholdHealthTarget,
    val description: String
) {
    /** 除湿剂：氯化钙吸收环境水汽 */
    DEHUMIDIFIER(
        "除湿剂", baseEffect = 0.5, monthlyUsageBase = 2,
        monthlyCost = 15.0, healthTarget = HouseholdHealthTarget.FUNGUS_PREVENTION,
        "氯化钙吸收空气中的水汽——房间不潮了，真菌就少了繁殖的地盘。潮湿地区的人会多用几包——不是浪费，是必要。"
    ),
    /** 香皂：脂肪酸清洁 */
    SOAP(
        "香皂", baseEffect = 0.6, monthlyUsageBase = 1,
        monthlyCost = 5.0, healthTarget = HouseholdHealthTarget.SKIN_CARE,
        "脂肪酸清洁——每天洗一次，手不会脱皮。不是什么了不起的东西——但肥皂在你没用之前，你不知道它有多重要。"
    ),
    /** 保湿护肤品 */
    MOISTURIZER(
        "护肤品", baseEffect = 0.7, monthlyUsageBase = 1,
        monthlyCost = 30.0, healthTarget = HouseholdHealthTarget.SKIN_CARE,
        "保湿成分锁住水分——干燥地区的手，洗完不涂这个，皮会一片一片地翘起来。不是化妆品——是皮肤的必需品。"
    )
}

/**
 * 日用品健康目标
 */
enum class HouseholdHealthTarget(val label: String) {
    FUNGUS_PREVENTION("真菌预防"),
    SKIN_CARE("皮肤护理")
}

/**
 * 日用品系统逻辑
 */
object HouseholdProductSystem {

    /**
     * 潮湿地区除湿剂消耗量上调
     */
    fun adjustMonthlyUsage(product: HouseholdProductType, avgHumidity: Double): Int {
        if (product == HouseholdProductType.DEHUMIDIFIER && avgHumidity > 0.6) {
            return (product.monthlyUsageBase * 1.5).toInt()
        }
        return product.monthlyUsageBase
    }

    /**
     * 计算日用品对健康的防护效果
     * @param hasProduct 是否使用该产品
     * @param product 产品类型
     * @param environmentHumidity 环境湿度
     */
    fun healthProtectionModifier(
        hasProduct: Boolean,
        product: HouseholdProductType,
        environmentHumidity: Double
    ): Double {
        if (!hasProduct) return 0.0

        return when (product) {
            HouseholdProductType.DEHUMIDIFIER -> {
                // 潮湿环境除湿效果更明显
                val humidityBonus = if (environmentHumidity > 0.6) 1.5 else 1.0
                product.baseEffect * humidityBonus * 0.15
            }
            HouseholdProductType.SOAP -> product.baseEffect * 0.1
            HouseholdProductType.MOISTURIZER -> {
                // 干燥环境护肤效果更明显
                val dryBonus = if (environmentHumidity < 0.4) 1.5 else 1.0
                product.baseEffect * dryBonus * 0.12
            }
        }
    }
}

// ============================================
// 五、面料-穿戴位绑定
// ============================================

/**
 * 将面料绑定到穿戴位的状态扩展
 * 在 WearSlotState 基础上新增面料成分字段
 */
data class MaterialWearSlotState(
    val slot: WearSlot,
    val fabric: FabricType = FabricType.COTTON,
    val breathability: ShoeBreathability = ShoeBreathability.MODERATE,
    val warmth: ClothingWarmth = ClothingWarmth.MODERATE,
    val cleanliness: ClothingCleanliness = ClothingCleanliness.NORMAL,
    val quality: ClothingQuality = ClothingQuality.PLAIN,
    val dampness: Int = 0,
    val wearAndTear: Int = 0,
    val consecutiveWearDays: Int = 0,
    val daysSinceLastWash: Int = 0
) {
    /** 基于面料重新计算透气性表现 */
    fun effectiveBreathability(): Double {
        return fabric.breathability * (1.0 - breathability.moistureRetention * 0.5)
    }

    /** 基于面料重新计算锁温表现 */
    fun effectiveWarmth(): Double {
        return fabric.thermalRetention * warmth.warmthValue / 7.0
    }
}