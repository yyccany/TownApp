package com.example.townapp.data

/**
 * 医疗健康终极精简框架（V2.0 最终定稿 底层物资-健康-医疗总框架）
 *
 * 核心设计哲学：
 * 1. 底层是现代医学真实因果，表现层极度简化
 * 2. 六种病症涵盖全部人生健康风险，不再新增
 * 3. 四类药品 + 三条治疗路线 永久定稿
 * 4. 同时容纳现代医学、古法调理、顺其自然自愈三种真实社会人群选择
 * 5. 药效仅受三个变量影响：体质、年龄、环境温湿度
 */

// ============================================
// 一、六种基础病症（小镇全部病症，永久定稿）
// ============================================

/**
 * 病症大类
 */
enum class DiseaseType(
    val label: String,
    val baseSeverity: Int,
    val naturalHealChance: Double,
    val chronicRisk: Double,
    val canSelfHeal: Boolean,
    val description: String,
    val townCommentary: String
) {
    MILD_INFECTION(
        "轻微外感", baseSeverity = 2, naturalHealChance = 0.08,
        chronicRisk = 0.02, canSelfHeal = true,
        "感冒了——鼻子堵、嗓子疼、整个人闷闷的。不是什么大病，但今天做什么都比平时费力。",
        "感冒是身体的例行检修——它在提醒你：该休息了。睡一觉、喝点热水——身体自己会修好大部分东西。"
    ),

    SKIN_ISSUE(
        "皮肤问题", baseSeverity = 1, naturalHealChance = 0.03,
        chronicRisk = 0.15, canSelfHeal = false,
        "皮肤在发脾气——痒、脱皮、泛红。不是大问题，但它像一只小虫子，在你注意力最集中的时候咬你一口。",
        "皮肤是身体的面子——但它说出来的话，往往指向里子。潮湿、不透气、没洗干净——你皮肤在替你承受你忽略的东西。"
    ),

    MUSCULOSKELETAL(
        "筋骨损伤", baseSeverity = 3, naturalHealChance = 0.01,
        chronicRisk = 0.30, canSelfHeal = false,
        "扭了一下、摔了一下——骨头或者筋发出了一声警告。如果没处理好，它会变成一个陪你一辈子的天气预报：下雨天就疼。",
        "筋骨损伤最怕不治——不是它不会好，是它会好得不对。错位的骨头长好了还是错位的——这是物理，不是意志力。"
    ),

    RESPIRATORY_CHRONIC(
        "呼吸系统慢病", baseSeverity = 4, naturalHealChance = 0.005,
        chronicRisk = 0.40, canSelfHeal = false,
        "咳嗽断断续续——好了又犯、犯了又好。你习惯了这个声音——但你的肺没有。它在慢慢被消耗。",
        "反复的咳嗽不是老毛病——是肺在反复地喊：我还没好。慢性呼吸道问题——是最容易被忽视但最难逆转的一组病。"
    ),

    METABOLIC_CHRONIC(
        "基础慢性病", baseSeverity = 4, naturalHealChance = 0.0,
        chronicRisk = 0.60, canSelfHeal = false,
        "不是突然得的——是十年、二十年的饮食、作息、压力一点一点堆出来的。体虚、肥胖、代谢紊乱——这些病把你的身体基线一点一点往下拉。",
        "慢性病是生活习惯开的发票——它不会立刻让你倒下，但它会降低你每一天的出厂设置。"
    ),

    SEVERE_CONDITION(
        "重大重症", baseSeverity = 6, naturalHealChance = 0.0,
        chronicRisk = 0.70, canSelfHeal = false,
        "身体发出了一声巨响——不是咳嗽、不是痒、不是酸。是那种让你意识到：生命可以被截断的东西。",
        "重大重症是人生的分水岭——它让所有细碎的烦恼变得不值一提。这个时候——钱、医疗条件、家庭支持——平时看不见，现在变成了你全部的选择空间。"
    )
}

// ============================================
// 二、四类药品体系（永久定稿，不再细分）
// ============================================

enum class DrugTier(
    val label: String,
    val efficacy: Double,
    val sideEffectSeverity: Double,
    val priceTier: Int,
    val requiresDiagnosis: Boolean,
    val maxTreatableSeverity: Int,
    val chronicPrevention: Double,
    val description: String,
    val townCommentary: String
) {
    HERBAL_SIMPLE(
        "简易草药", efficacy = 0.35, sideEffectSeverity = 0.05,
        priceTier = 1, requiresDiagnosis = false, maxTreatableSeverity = 3,
        chronicPrevention = 0.2,
        "便宜的草药——几块钱一副，熬一熬能对付小毛病。但重症不治——这不是草药的错，是它的能力就到这里。",
        "古法草药是底层百姓的第一道防线——便宜、随手可得、轻症有效。但它有边界——越过那个边界，硬扛才是伤害。"
    ),

    REGULAR_WESTERN(
        "平价常规西药", efficacy = 0.65, sideEffectSeverity = 0.15,
        priceTier = 2, requiresDiagnosis = false, maxTreatableSeverity = 5,
        chronicPrevention = 0.5,
        "普通西药——感冒药、消炎药、止痛片。对症、稳定、大多数人都用得起。长期吃有点小损耗——但利大于弊。",
        "平价西药是百姓主流选择——不求最快，不求最强，求一个踏实。它可以解决大多数问题——解决不了的，就去医院。"
    ),

    STANDARD_MEDICAL(
        "标准医用药剂", efficacy = 0.80, sideEffectSeverity = 0.20,
        priceTier = 3, requiresDiagnosis = true, maxTreatableSeverity = 6,
        chronicPrevention = 0.75,
        "医院的药——必须先查清楚，再对症下药。拍片、化验——这不是折腾你，是为了不治错。费用不低，但精准。",
        "标准医疗是现代医学的核心——它用检查消除不确定性。贵——但贵在确定性。发烧不一定是感冒，咳嗽不一定是发炎——查了才知道。"
    ),

    PREMIUM_SPECIALTY(
        "高端特效药", efficacy = 0.95, sideEffectSeverity = 0.25,
        priceTier = 5, requiresDiagnosis = true, maxTreatableSeverity = 6,
        chronicPrevention = 0.95,
        "最好的药——进口、特效、几乎不留后遗症。但价格——不是所有人都能承受的。一场大病用高端药——可能就是几年积蓄。",
        "高端药是一种现实：钱能买时间、买恢复质量、买后遗症少一点。有钱不是罪——没钱也不是——但医疗资源的多寡，真实地影响一个人能从病里走出来的样子。"
    )
}

// ============================================
// 三、三条治疗路线
// ============================================

enum class TreatmentRoute(
    val label: String,
    val description: String,
    val maxDrugTier: DrugTier,
    val requiresDiagnosis: Boolean,
    val beliefFactor: Double,
    val townCommentary: String
) {
    MODERN_MEDICINE(
        "现代循证医学",
        "拍片、化验、确诊、对症用药。根治率最高、后遗症最少——重症的唯一出路。代价是贵——大病可返贫。",
        maxDrugTier = DrugTier.PREMIUM_SPECIALTY, requiresDiagnosis = true, beliefFactor = 0.9,
        "现代医学不信玄学——它信数据、信证据、信检测结果。它不承诺奇迹——但它承诺的是：在人类已知范围内，给你最好的治疗。"
    ),

    TRADITIONAL_REMEDY(
        "古法调理",
        "草药、正骨、经验治疗。低成本、适配底层轻症。但靠运气、靠医师水平——重症极易留终身病根。",
        maxDrugTier = DrugTier.REGULAR_WESTERN, requiresDiagnosis = false, beliefFactor = 0.6,
        "古法不是骗局——在青霉素发明之前，人类全靠它活下来了。但它有边界——骨折需要拍片、感染需要抗生素。"
    ),

    NATURAL_RECOVERY(
        "顺其自然自愈",
        "零成本、无药物副作用——但不治疗。轻症可以自愈——但中重症百分百慢性化、畸形化、体质永久降级。",
        maxDrugTier = DrugTier.HERBAL_SIMPLE, requiresDiagnosis = false, beliefFactor = 0.3,
        "自愈是一种自由——你的身体、你的选择。但自由的意思是：你也要承担自由的后果。轻症自愈是智慧——重症硬扛是赌博。"
    )
}

// ============================================
// 四、疾病运行时状态
// ============================================

data class ActiveDisease(
    val type: DiseaseType,
    val currentSeverity: Int,
    val daysActive: Int,
    val isChronic: Boolean = false,
    val hasPermanentSequela: Boolean = false,
    val activeTreatment: TreatmentRoute? = null,
    val activeDrugTier: DrugTier? = null,
    val treatmentDays: Int = 0,
    val onsetAge: Int = 0
) {
    fun dailyFatiguePenalty(): Int = (currentSeverity * 0.5).toInt()

    fun dailyHealthDrain(): Int = when {
        isChronic -> (currentSeverity * 0.3).toInt()
        hasPermanentSequela -> (currentSeverity * 0.15).toInt()
        else -> 0
    }

    fun workEfficiencyPenalty(): Double = when {
        currentSeverity > 70 -> 0.25
        currentSeverity > 40 -> 0.12
        currentSeverity > 20 -> 0.05
        else -> 0.0
    }

    val isUnderTreatment: Boolean get() = activeTreatment != null && activeDrugTier != null
}

data class MedicalRuntimeState(
    val activeDiseases: List<ActiveDisease> = emptyList(),
    val permanentSequelae: List<DiseaseType> = emptyList(),
    val defaultTreatmentRoute: TreatmentRoute = TreatmentRoute.NATURAL_RECOVERY,
    val constitutionScore: Int = 60,
    val lifetimeMedicalCost: Double = 0.0
) {
    val hasSevereDisease: Boolean
        get() = activeDiseases.any { d -> d.type.baseSeverity >= 5 }

    fun totalFatiguePenalty(): Int = activeDiseases.sumOf { d -> d.dailyFatiguePenalty() }

    fun hasChronicDisease(): Boolean = activeDiseases.any { d -> d.isChronic }
}

// ============================================
// 五、恢复计算引擎
// ============================================

data class RecoveryResult(
    val severityChange: Int,
    val newSeverity: Int,
    val becomesChronic: Boolean,
    val becomesPermanent: Boolean,
    val fatigueDelta: Int = 0,
    val hasSideEffect: Boolean = false,
    val description: String = ""
)

object MedicalRecoveryEngine {

    fun calculateDailyRecovery(
        disease: ActiveDisease,
        constitution: Int,
        age: Int,
        temperature: Double,
        humidity: Double
    ): RecoveryResult {
        if (disease.activeDrugTier == null) {
            return naturalRecovery(disease, constitution, age, temperature, humidity)
        }
        return medicalRecovery(disease, constitution, age, temperature, humidity)
    }

    private fun naturalRecovery(
        disease: ActiveDisease,
        constitution: Int,
        age: Int,
        temperature: Double,
        humidity: Double
    ): RecoveryResult {
        val diseaseType = disease.type

        if (!diseaseType.canSelfHeal) {
            val worsenAmount = when {
                diseaseType.baseSeverity >= 5 -> 3
                diseaseType.baseSeverity >= 3 -> 1
                else -> 0
            }
            val newSeverity = (disease.currentSeverity + worsenAmount).coerceAtMost(100)
            val becomesChronic = newSeverity > 60 && Math.random() < diseaseType.chronicRisk

            return RecoveryResult(
                severityChange = worsenAmount,
                newSeverity = newSeverity,
                becomesChronic = becomesChronic,
                becomesPermanent = newSeverity > 85 && Math.random() < 0.3,
                fatigueDelta = disease.dailyFatiguePenalty(),
                description = "你的${diseaseType.label}没有好转——它不声不响地又深了一层。身体没有自愈力对抗这个病。"
            )
        }

        val baseHealChance = diseaseType.naturalHealChance
        val constitutionBonus = (constitution - 50) * 0.0005
        val agePenalty = when {
            age > 60 -> -0.03
            age > 40 -> -0.015
            age < 20 -> 0.01
            else -> 0.0
        }
        val envPenalty = if (humidity > 0.7 || temperature < 5) -0.01 else 0.0
        val effectiveChance = (baseHealChance + constitutionBonus + agePenalty + envPenalty)
            .coerceIn(0.0, 0.15)

        return if (Math.random() < effectiveChance) {
            val healAmount = (5 + constitution / 20).coerceIn(3, 15)
            val newSeverity = (disease.currentSeverity - healAmount).coerceAtLeast(0)
            RecoveryResult(
                severityChange = -healAmount,
                newSeverity = newSeverity,
                becomesChronic = false,
                becomesPermanent = false,
                fatigueDelta = maxOf(0, disease.dailyFatiguePenalty() - healAmount / 2),
                description = if (newSeverity <= 0) "你的${diseaseType.label}终于好了——身体自己修好了自己。"
                else "你的${diseaseType.label}好了一点点——身体在慢慢修。给它时间。"
            )
        } else {
            RecoveryResult(
                severityChange = 0,
                newSeverity = disease.currentSeverity,
                becomesChronic = disease.daysActive > 30 && Math.random() < diseaseType.chronicRisk,
                becomesPermanent = false,
                fatigueDelta = disease.dailyFatiguePenalty(),
                description = "今天你的${diseaseType.label}没有变化——不更差，也没更好。"
            )
        }
    }

    private fun medicalRecovery(
        disease: ActiveDisease,
        constitution: Int,
        age: Int,
        temperature: Double,
        humidity: Double
    ): RecoveryResult {
        val drug = disease.activeDrugTier
            ?: return naturalRecovery(disease, constitution, age, temperature, humidity)

        val constitutionFactor = (constitution / 100.0).coerceIn(0.4, 1.2)
        val ageFactor = when {
            age < 20 -> 1.1
            age > 60 -> 0.6
            age > 40 -> 0.8
            else -> 1.0
        }
        val envFactor = when {
            humidity > 0.7 -> 0.85
            temperature < 5 -> 0.9
            else -> 1.0
        }

        val effectiveEfficacy = drug.efficacy * constitutionFactor * ageFactor * envFactor
        val healAmount = (effectiveEfficacy * 15).toInt().coerceIn(2, 20)
        val newSeverity = (disease.currentSeverity - healAmount).coerceAtLeast(0)

        val sideEffect = Math.random() < drug.sideEffectSeverity * (1.0 / ageFactor)
        val chronicPrevented = Math.random() < drug.chronicPrevention

        val becomesChronic = !chronicPrevented && disease.type.baseSeverity >= 3 &&
            disease.daysActive > 20 && Math.random() < disease.type.chronicRisk * 0.5

        return RecoveryResult(
            severityChange = -healAmount,
            newSeverity = newSeverity,
            becomesChronic = becomesChronic,
            becomesPermanent = newSeverity > 80 && !chronicPrevented && Math.random() < 0.15,
            fatigueDelta = if (sideEffect) 2 else maxOf(0, disease.dailyFatiguePenalty() - healAmount / 2),
            hasSideEffect = sideEffect,
            description = buildRecoveryDescription(disease, drug, healAmount, newSeverity, sideEffect)
        )
    }

    private fun buildRecoveryDescription(
        disease: ActiveDisease,
        drug: DrugTier,
        healAmount: Int,
        newSeverity: Int,
        hasSideEffect: Boolean
    ): String {
        val progress = when {
            newSeverity <= 0 -> "你的${disease.type.label}已经痊愈了。"
            healAmount >= 10 -> "你的${disease.type.label}明显好转——${drug.label}在起作用。"
            healAmount >= 5 -> "你的${disease.type.label}在慢慢好转——${drug.label}的疗效是稳定的。"
            else -> "${drug.label}在发挥作用——但恢复需要时间。不是药不行，是你的身体在慢慢吸收。"
        }
        val sideEffectNote = if (hasSideEffect) {
            "\n药的副作用出现了——有点不舒服，但在可控范围内。"
        } else ""
        return progress + sideEffectNote
    }
}

// ============================================
// 六、疾病触发规则
// ============================================

object DiseaseTriggerRules {

    fun checkDailyTrigger(
        constitution: Int,
        clothingHygiene: Double,
        footFungusRisk: Int,
        skinIssueRisk: Int,
        temperature: Double,
        humidity: Double,
        isRaining: Boolean,
        age: Int
    ): DiseaseType? {
        val weakConstitution = constitution < 40

        if (temperature < 12 && Math.random() < 0.06 * (if (weakConstitution) 1.5 else 1.0)) {
            return DiseaseType.MILD_INFECTION
        }

        if (footFungusRisk > 70 && Math.random() < 0.08) return DiseaseType.SKIN_ISSUE
        if (skinIssueRisk > 60 && Math.random() < 0.05) return DiseaseType.SKIN_ISSUE

        if (age > 35 && Math.random() < 0.003 * (age / 30.0)) return DiseaseType.MUSCULOSKELETAL

        if (age > 40 && Math.random() < 0.002 * (age / 30.0) * (if (weakConstitution) 2.0 else 1.0)) {
            return DiseaseType.METABOLIC_CHRONIC
        }

        if (age > 55 && Math.random() < 0.0005 * (age / 40.0)) {
            return DiseaseType.SEVERE_CONDITION
        }

        return null
    }

    fun checkDiseaseProgression(
        disease: ActiveDisease,
        constitution: Int,
        age: Int
    ): DiseaseType? {
        if (disease.type == DiseaseType.MILD_INFECTION &&
            disease.daysActive > 14 &&
            !disease.isUnderTreatment &&
            Math.random() < 0.15 * (if (constitution < 40) 2.0 else 1.0)) {
            return DiseaseType.RESPIRATORY_CHRONIC
        }
        return null
    }
}

// ============================================
// 七、营养等级系统
// ============================================

enum class NutritionLevel(
    val label: String,
    val constitutionModifier: Int,
    val growthModifier: Double,
    val diseaseResistanceModifier: Double,
    val appearanceModifier: Int,
    val description: String
) {
    DEFICIENT(
        "营养匮乏", constitutionModifier = -15, growthModifier = 0.7,
        diseaseResistanceModifier = -0.15, appearanceModifier = -5,
        "长期吃不饱、吃不好——不是你的错，是环境给你的限制。营养匮乏会在骨头里、血液里、免疫系统里写下痕迹。"
    ),
    ADEQUATE(
        "基础达标", constitutionModifier = 0, growthModifier = 1.0,
        diseaseResistanceModifier = 0.0, appearanceModifier = 0,
        "正常的三餐——吃饱了，营养够了。不好也不差——维持了你人生前进的基本燃料。"
    ),
    BALANCED(
        "均衡充足", constitutionModifier = 10, growthModifier = 1.15,
        diseaseResistanceModifier = 0.10, appearanceModifier = 3,
        "吃得好——不是奢侈，是给身体最好的原材料。均衡的营养让你的身体更耐用。"
    ),
    EXCESSIVE(
        "营养过剩", constitutionModifier = -5, growthModifier = 0.9,
        diseaseResistanceModifier = -0.05, appearanceModifier = -3,
        "吃得太好也是一种负担——过剩的营养变成脂肪、变成代谢压力。中年之后——那些年多吃的东西，会变成体检单上的箭头。"
    )
}