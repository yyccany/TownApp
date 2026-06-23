package com.example.townapp.data

/**
 * 性别差异化用药约束系统（v2.16）
 *
 * 核心设计原则：
 * 1. 药品清单、价格、基础药效男女完全一致——不存在性别等级差异
 * 2. 约束来自客观生理阶段（孕期/产后/经期/男性青春期/饮酒），而非社会偏见
 * 3. 常规状态下药品展示完全相同；触发特殊生理阶段后动态弹出提示
 * 4. 高危禁忌药物（孕期布洛芬等）在 UI 层直接置灰锁定，仅医院渠道开放
 * 5. 所有约束独立于 DrugCatalog，不污染药物条目本身
 */

// ============================================
// 一、性别药物约束数据模型
// ============================================

/**
 * 单种药物在性别/生理阶段下的约束条件
 */
data class GenderDrugConstraint(
    val drugId: String,
    /** 孕期禁用——UI 层直接锁定，不可自行购买 */
    val pregnancyRestricted: Boolean = false,
    /** 产后/哺乳期禁用 */
    val postpartumRestricted: Boolean = false,
    /** 孕期允许但附带警告 */
    val pregnancyWarning: String = "",
    /** 产后允许但附带警告 */
    val postpartumWarning: String = "",
    /** 经期使用备注 */
    val menstruationNote: String = "",
    /** 男性专属备注 */
    val maleNote: String = ""
)

/**
 * 药物解锁状态
 */
enum class DrugLockStatus(val label: String) {
    UNLOCKED("可用"),
    LOCKED_PREGNANCY("孕期禁用"),
    LOCKED_POSTPARTUM("产后禁用")
}

/**
 * 带性别约束的药物查询结果
 */
data class GenderAwareDrugInfo(
    val drug: SpecificDrug,
    val lockStatus: DrugLockStatus = DrugLockStatus.UNLOCKED,
    val warnings: List<String> = emptyList(),
    val notes: List<String> = emptyList()
)

// ============================================
// 二、性别药物约束规则引擎
// ============================================

object GenderDrugRules {

    // ---- 约束映射表（仅录入有性别差异的药物） ----

    private val constraints: Map<String, GenderDrugConstraint> = mapOf(

        // ====== 孕期/产后禁用 ======
        "western_ibuprofen" to GenderDrugConstraint(
            drugId = "western_ibuprofen",
            pregnancyRestricted = true,
            postpartumRestricted = true,
            pregnancyWarning = "孕期严禁自行服用布洛芬，高烧时须前往医院开具孕期安全药剂",
            menstruationNote = "可缓解痛经——但经期出血量可能小幅增多，属于正常附带作用"
        ),
        "standard_doxycycline" to GenderDrugConstraint(
            drugId = "standard_doxycycline",
            pregnancyRestricted = true,
            postpartumRestricted = true,
            pregnancyWarning = "孕期严禁口服多西环素（四环素类），仅允许外用克林霉素凝胶",
            maleNote = "长期应酬饮酒，服用后肝肾损伤风险高于均值"
        ),
        "western_chlorphenamine" to GenderDrugConstraint(
            drugId = "western_chlorphenamine",
            pregnancyRestricted = true,
            postpartumRestricted = true,
            pregnancyWarning = "孕期、哺乳期禁用一代抗组胺药（嗜睡副作用强），优先二代抗组胺药"
        ),
        "standard_antibiotic" to GenderDrugConstraint(
            drugId = "standard_antibiotic",
            pregnancyRestricted = true,
            postpartumRestricted = true,
            pregnancyWarning = "孕期使用抗生素须医院验血确诊后由医生开具，不可自行购买",
            maleNote = "长期应酬饮酒，抗生素服用后肝肾损伤风险高于均值"
        ),

        // ====== 孕期警告（不禁用但有限制） ======
        "western_desonide" to GenderDrugConstraint(
            drugId = "western_desonide",
            pregnancyWarning = "孕期连续使用限7天以内（非孕期1-2周），禁止长期大面积涂抹",
            postpartumWarning = "产后激素波动，湿疹复发概率提升，用药周期可延长但需遵医嘱"
        ),
        "western_loratadine" to GenderDrugConstraint(
            drugId = "western_loratadine",
            pregnancyWarning = "孕期优先选用二代抗组胺药——氯雷他定或西替利嗪均可，困倦感极轻"
        ),
        "western_cetirizine" to GenderDrugConstraint(
            drugId = "western_cetirizine",
            pregnancyWarning = "孕期优先选用二代抗组胺药——西替利嗪或氯雷他定均可，少数人仍有轻微困倦"
        ),
        "standard_terbinafine_oral" to GenderDrugConstraint(
            drugId = "standard_terbinafine_oral",
            pregnancyWarning = "孕期口服抗真菌药需医生确认肝功能后使用，优先外用乳膏"
        ),

        // ====== 经期备注 ======
        "herbal_zuguangsan" to GenderDrugConstraint(
            drugId = "herbal_zuguangsan",
            menstruationNote = "经期体质敏感，外用中成药皮肤过敏概率上浮——瘙痒加剧立刻停用"
        ),
        "western_terbinafine" to GenderDrugConstraint(
            drugId = "western_terbinafine",
            menstruationNote = "经期脚部水肿、湿气堆积，脚气复发概率小幅上涨，用药周期可能延长2-3天",
            maleNote = "脚部汗腺发达、出汗量大，潮湿环境脚气初始患病率更高，前期需落实鞋袜暴晒护理"
        ),
        "western_bifonazole" to GenderDrugConstraint(
            drugId = "western_bifonazole",
            menstruationNote = "经期脚部水肿，真菌易复发，用药周期可能延长2-3天",
            maleNote = "脚部汗腺发达，潮湿环境脚气初始患病率更高"
        ),
        "herbal_huoxiang" to GenderDrugConstraint(
            drugId = "herbal_huoxiang",
            menstruationNote = "经期部分人群服用藿香正气后痛经加重——个体差异，非药不好，是体质不匹配"
        ),
        "western_adapalene" to GenderDrugConstraint(
            drugId = "western_adapalene",
            menstruationNote = "经期前后激素波动，痤疮容易阶段性爆发，用药周期拉长——是激素，不是你护肤没做好",
            maleNote = "青春期雄激素旺盛，胸背出油痤疮发病率显著更高，阿达帕林使用频次高于女性均值"
        ),
        "western_clindamycin" to GenderDrugConstraint(
            drugId = "western_clindamycin",
            menstruationNote = "经期前后痤疮爆发，克林霉素凝胶使用频次阶段性上升",
            maleNote = "青春期出油长痘概率更高，克林霉素为通用药物，使用频次高于女性均值"
        ),
        "herbal_danshentong" to GenderDrugConstraint(
            drugId = "herbal_danshentong",
            menstruationNote = "经期服用活血类中成药需谨慎——丹参酮有活血作用，经期出血量可能增多"
        ),
        "herbal_sanqi" to GenderDrugConstraint(
            drugId = "herbal_sanqi",
            menstruationNote = "三七活血化瘀——经期服用可能增加出血量，跌打损伤合并经期时建议外用替代"
        ),

        // ====== 男性专属备注 ======
        "western_paracetamol" to GenderDrugConstraint(
            drugId = "western_paracetamol",
            maleNote = "长期应酬饮酒，对乙酰氨基酚与酒精叠加加重肝脏负担——不是药不好，是酒和药同路"
        )
    )

    // ============================================
    // 三、查询接口
    // ============================================

    /**
     * 根据 drugId 获取约束条件（无约束则返回空）
     */
    fun getConstraint(drugId: String): GenderDrugConstraint? = constraints[drugId]

    /**
     * 判断某药品在给定性别状态下是否可用
     */
    fun isDrugAllowed(drugId: String, genderState: GenderRuntimeState): Boolean {
        val constraint = constraints[drugId] ?: return true
        if (constraint.pregnancyRestricted && genderState.childbirth.phase == ChildbirthPhase.PREGNANT) return false
        if (constraint.postpartumRestricted && genderState.childbirth.phase == ChildbirthPhase.POSTPARTUM) return false
        return true
    }

    /**
     * 获取药品的锁定状态
     */
    fun getLockStatus(drugId: String, genderState: GenderRuntimeState): DrugLockStatus {
        val constraint = constraints[drugId] ?: return DrugLockStatus.UNLOCKED
        if (constraint.pregnancyRestricted && genderState.childbirth.phase == ChildbirthPhase.PREGNANT) {
            return DrugLockStatus.LOCKED_PREGNANCY
        }
        if (constraint.postpartumRestricted && genderState.childbirth.phase == ChildbirthPhase.POSTPARTUM) {
            return DrugLockStatus.LOCKED_POSTPARTUM
        }
        return DrugLockStatus.UNLOCKED
    }

    /**
     * 获取当前性别状态下应显示的警告列表
     * 按优先级：孕期 > 产后 > 经期
     */
    fun getActiveWarnings(drugId: String, genderState: GenderRuntimeState): List<String> {
        val constraint = constraints[drugId] ?: return emptyList()
        val warnings = mutableListOf<String>()

        when {
            genderState.childbirth.phase == ChildbirthPhase.PREGNANT -> {
                if (constraint.pregnancyWarning.isNotEmpty()) warnings.add(constraint.pregnancyWarning)
            }
            genderState.childbirth.phase == ChildbirthPhase.POSTPARTUM -> {
                if (constraint.postpartumWarning.isNotEmpty()) warnings.add(constraint.postpartumWarning)
            }
        }

        return warnings
    }

    /**
     * 获取当前性别状态下应显示的备注列表
     */
    fun getActiveNotes(
        drugId: String,
        genderState: GenderRuntimeState,
        age: Int
    ): List<String> {
        val constraint = constraints[drugId] ?: return emptyList()
        val notes = mutableListOf<String>()

        // 女性生理阶段备注
        if (genderState.sex == BiologicalSex.FEMALE) {
            // 经期备注
            if (genderState.menstrualCycle?.isMenstruating == true && constraint.menstruationNote.isNotEmpty()) {
                notes.add(constraint.menstruationNote)
            }
        }

        // 男性备注：青春期（13-25）或中年饮酒（35+）
        if (genderState.sex == BiologicalSex.MALE && constraint.maleNote.isNotEmpty()) {
            if (age in 13..25 || age >= 35) {
                notes.add(constraint.maleNote)
            }
        }

        return notes
    }

    /**
     * 获取完整的性别感知药品信息（包含锁定状态、警告、备注）
     */
    fun wrapDrug(
        drug: SpecificDrug,
        genderState: GenderRuntimeState,
        age: Int
    ): GenderAwareDrugInfo {
        return GenderAwareDrugInfo(
            drug = drug,
            lockStatus = getLockStatus(drug.id, genderState),
            warnings = getActiveWarnings(drug.id, genderState),
            notes = getActiveNotes(drug.id, genderState, age)
        )
    }

    /**
     * 过滤：只返回当前性别状态下可用的药品
     */
    fun filterAvailable(
        drugs: List<SpecificDrug>,
        genderState: GenderRuntimeState
    ): List<SpecificDrug> {
        return drugs.filter { isDrugAllowed(it.id, genderState) }
    }

    /**
     * 批量封装：返回所有药品的性别感知信息
     */
    fun wrapAll(
        drugs: List<SpecificDrug>,
        genderState: GenderRuntimeState,
        age: Int
    ): List<GenderAwareDrugInfo> {
        return drugs.map { wrapDrug(it, genderState, age) }
    }

    // ============================================
    // 四、文案生成
    // ============================================

    /**
     * 生成性别阶段标题
     */
    fun describeStageBanner(genderState: GenderRuntimeState, age: Int): String? {
        return when {
            genderState.childbirth.phase == ChildbirthPhase.PREGNANT -> "孕期用药提示"
            genderState.childbirth.phase == ChildbirthPhase.POSTPARTUM -> "产后用药提示"
            genderState.sex == BiologicalSex.FEMALE &&
                genderState.menstrualCycle?.isMenstruating == true -> "经期用药提示"
            genderState.sex == BiologicalSex.MALE && age in 13..25 -> "青春期用药提示"
            else -> null
        }
    }

    /**
     * 生成性别阶段用药提示的详情文案（展示在药品详情弹窗顶部）
     */
    fun describeStagePrompt(genderState: GenderRuntimeState, age: Int): String? {
        val phase = genderState.childbirth.phase
        val isMenstruating = genderState.menstrualCycle?.isMenstruating == true

        return when {
            phase == ChildbirthPhase.PREGNANT -> {
                "你正处于孕期——部分药物已锁定。这不是限制你的选择——是在保护你和孩子。\n" +
                "下方药品中，标记「孕期禁用」的仅能通过医院渠道获取；标记「孕期提示」的可以正常使用但请注意附带说明。"
            }
            phase == ChildbirthPhase.POSTPARTUM -> {
                "你正处于产后恢复期——身体在修复中。部分药物暂时锁定。\n" +
                "激素变化可能让湿疹、脚气更容易复发——这不是身体变差了，是激素在重新校准。"
            }
            genderState.sex == BiologicalSex.FEMALE && isMenstruating -> {
                "你正处于经期——部分药物的效果和使用体验可能略有不同。\n" +
                "这不是药的问题，也不是你的问题——是每个月这几天，身体在用另一种节奏运转。"
            }
            genderState.sex == BiologicalSex.MALE && age in 13..25 -> {
                "你处于青春期——雄激素旺盛，身体在快速变化。\n" +
                "出油、长痘是激素在说话，不是你没洗干净。部分药物的使用频次可能高于常规。"
            }
            else -> null
        }
    }

    /**
     * 生成某药品在性别阶段下的附加警告文本（用于拼接在详情弹窗中）
     */
    fun describeDrugGenderNotes(
        drugId: String,
        genderState: GenderRuntimeState,
        age: Int
    ): String? {
        val lockStatus = getLockStatus(drugId, genderState)
        val warnings = getActiveWarnings(drugId, genderState)
        val notes = getActiveNotes(drugId, genderState, age)

        if (lockStatus == DrugLockStatus.UNLOCKED && warnings.isEmpty() && notes.isEmpty()) return null

        val sb = StringBuilder()
        sb.append("\n—— 性别阶段提示 ——\n")

        if (lockStatus != DrugLockStatus.UNLOCKED) {
            sb.append("⚠ ${lockStatus.label}：此药当前不可自行购买，仅医院渠道开放\n")
        }

        warnings.forEach { sb.append("⚠ $it\n") }
        notes.forEach { sb.append("· $it\n") }

        return sb.toString()
    }

    /**
     * 是否存在任何活跃的性别阶段（用于决定是否显示提示板块）
     */
    fun hasActiveStage(genderState: GenderRuntimeState, age: Int): Boolean {
        return genderState.childbirth.phase == ChildbirthPhase.PREGNANT ||
            genderState.childbirth.phase == ChildbirthPhase.POSTPARTUM ||
            (genderState.sex == BiologicalSex.FEMALE && genderState.menstrualCycle?.isMenstruating == true) ||
            (genderState.sex == BiologicalSex.MALE && age in 13..25)
    }
}