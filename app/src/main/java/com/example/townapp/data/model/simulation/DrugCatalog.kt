package com.example.townapp.data

/**
 * 药品目录——细分药物条目（V2.0 折叠详情层 完整版）
 *
 * 设计原则：
 * 1. 沿用四类药品大框架，涵盖日常高频药物：足部、手部、躯干皮肤、筋骨、内科、过敏六大门类
 * 2. 主界面仅展示药品大类，点击详情弹窗才查看细分药物
 * 3. 不点开详情，系统按家庭收入自动匹配默认药物
 * 4. 真实药物命名、现代医学常识，不做伪科学
 * 5. 所有中成药标注过敏风险，处方药标注不可自行长期服用
 * 6. 一代抗组胺药标注嗜睡警告，激素药膏标注使用周期限制
 * 7. 六大病症分类不变——细分药物仅为路线内部细节拓展
 */

// ============================================
// 一、细分药物条目
// ============================================

/**
 * 药物用途分类
 */
enum class DrugUsageCategory(val label: String) {
    ANTIPYRETIC("退烧止痛"),
    ANTIFUNGAL("抗真菌/脚气"),
    SKIN_CARE("皮肤护理/伤口"),
    ORTHOPEDIC("骨科/筋骨"),
    BURN_CARE("烫伤"),
    COLD_COUGH("感冒咳嗽"),
    ANTIBIOTIC("抗生素"),
    CHRONIC_CARE("慢性病控制"),
    ANTIHISTAMINE("抗过敏"),
    DIGESTIVE("肠胃消化"),
    ORAL_CARE("口腔护理"),
    RHINITIS("鼻炎护理")
}

/**
 * 细分药物
 */
data class SpecificDrug(
    val id: String,
    /** 药品通用名 */
    val name: String,
    /** 所属药品档位 */
    val tier: DrugTier,
    /** 用途分类 */
    val usageCategory: DrugUsageCategory,
    /** 适用病症 */
    val applicableDiseases: List<DiseaseType>,
    /** 是否为处方药 */
    val isPrescription: Boolean = false,
    /** 是否外用 */
    val isExternal: Boolean = false,
    /** 基础药效修正（相对该档位基线的加减） */
    val efficacyModifier: Double = 0.0,
    /** 起效天数 */
    val onsetDays: Int = 3,
    /** 价格区间（元） */
    val costMin: Double = 5.0,
    val costMax: Double = 50.0,
    /** 副作用说明 */
    val sideEffectNote: String = "",
    /** 中成药过敏风险 */
    val hasAllergyRisk: Boolean = false,
    /** 限制条件 */
    val restrictions: String = "",
    /** 居家护理建议 */
    val homeCare: String = "",
    /** 折叠详情展示文本 */
    val detailDescription: String = ""
)

/**
 * 药品目录——全部细分药物（永久定稿，不再新增）
 */
object DrugCatalog {

    // ============================================
    // 一、简易草药/中成药（古法-低价档位）
    // ============================================

    // --- 退烧/感冒 ---
    val HERBAL_FEVER = SpecificDrug(
        id = "herbal_fever",
        name = "退烧草药",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.ANTIPYRETIC,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        efficacyModifier = -0.05, onsetDays = 5,
        costMin = 3.0, costMax = 15.0,
        sideEffectNote = "高烧状态效果微弱，拖延超过7天大概率转为呼吸道慢性咳嗽",
        restrictions = "不适于38.5℃以上高烧",
        homeCare = "多喝温水，保证睡眠休息",
        detailDescription = "乡村草药、自制退烧方——3-5天缓慢降温。便宜、随手可得，适合低收入家庭。但高烧不退时别硬撑——那不是草药的问题，是它能力就到这儿。"
    )

    val HERBAL_CHAIHU = SpecificDrug(
        id = "herbal_chaihu",
        name = "柴胡颗粒",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.ANTIPYRETIC,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.0, onsetDays = 2,
        costMin = 12.0, costMax = 28.0,
        hasAllergyRisk = true,
        homeCare = "持续高烧2天以上就医检查炎症根源",
        detailDescription = "中成药退热——比土方草药快一点，但不如布洛芬精准。38.5℃以上还是建议西药——不是中成药不好，是退烧这件事，速度有时比态度重要。"
    )

    val HERBAL_GANMAOLING = SpecificDrug(
        id = "herbal_ganmaoling",
        name = "感冒灵颗粒",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.COLD_COUGH,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.0, onsetDays = 3,
        costMin = 10.0, costMax = 25.0,
        sideEffectNote = "中成药感冒制剂存在过敏风险，出现皮疹立刻停药，服用氯雷他定",
        hasAllergyRisk = true,
        homeCare = "多喝温水，保证睡眠休息",
        detailDescription = "感冒了冲一包——热乎乎的，鼻子通了一点。中成药的温和路线——不求速效，求一个舒服。但过敏体质先少量试用——这是对所有中药的基本礼仪。"
    )

    val HERBAL_LIANHUA = SpecificDrug(
        id = "herbal_lianhua",
        name = "连花清瘟胶囊",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.COLD_COUGH,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION, DiseaseType.RESPIRATORY_CHRONIC),
        efficacyModifier = 0.05, onsetDays = 3,
        costMin = 15.0, costMax = 35.0,
        sideEffectNote = "中成药存在过敏风险，出现皮疹立刻停药",
        hasAllergyRisk = true,
        homeCare = "多喝温水，保证睡眠休息",
        detailDescription = "呼吸道感染的中成药选择——清瘟解毒。对上呼吸道炎症有一定效果——但慢性呼吸道问题它只能辅助，不能替代专科用药。"
    )

    // --- 皮肤/脚气/湿疹 ---
    val HERBAL_ANTIFUNGAL = SpecificDrug(
        id = "herbal_antifungal",
        name = "苦参抑菌草药",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.ANTIFUNGAL,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = -0.05, onsetDays = 10,
        costMin = 5.0, costMax = 20.0,
        sideEffectNote = "阴雨潮湿环境药效下降，根治周期拉长，反复发作概率偏高",
        homeCare = "脚趾缝擦干，鞋袜暴晒，不共用拖鞋",
        detailDescription = "外敷处理脚气、轻度湿疹脱皮。苦参是老祖宗留下来的抑菌方——有用，但天潮湿的时候效果打折扣。不像西药那么稳定——但几块钱一包，对得起它的价格。"
    )

    val HERBAL_ZUGUANGSAN = SpecificDrug(
        id = "herbal_zuguangsan",
        name = "足光散",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.ANTIFUNGAL,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.0, onsetDays = 14,
        costMin = 8.0, costMax = 22.0,
        sideEffectNote = "中成药过敏风险——外用后瘙痒加剧、泛红肿胀，停用并口服氯雷他定",
        hasAllergyRisk = true,
        homeCare = "脚趾缝擦干，鞋袜暴晒，不共用拖鞋",
        detailDescription = "泡脚用的中成药散剂——对付脚气的老方子。每天泡一泡，脚趾缝干爽了。但如果泡了反而更痒更红——那是过敏，不是药在起效。停掉，换特比萘芬。"
    )

    val HERBAL_CHUSHIZHIYANG = SpecificDrug(
        id = "herbal_chushizhiyang",
        name = "除湿止痒软膏",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.0, onsetDays = 5,
        costMin = 12.0, costMax = 30.0,
        sideEffectNote = "中成药药膏过敏后，停用中药制剂，单用西药抗过敏药",
        hasAllergyRisk = true,
        homeCare = "规避化纤衣物，不用热水烫洗患处",
        detailDescription = "湿疹、皮炎的温和中成药外用药——不刺激、不含激素。适合轻中度皮肤问题。但如果涂了几天反而更红——你的皮肤在说：我们八字不合。换成西药地奈德试试。"
    )

    val HERBAL_QINGDAISAN = SpecificDrug(
        id = "herbal_qingdaisan",
        name = "青黛散",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.0, onsetDays = 5,
        costMin = 8.0, costMax = 20.0,
        sideEffectNote = "接触中药粉剂后红肿，清水冲洗，口服抗过敏药片",
        hasAllergyRisk = true,
        homeCare = "做家务佩戴防水手套，避开洗洁精",
        detailDescription = "汗疱疹的传统外敷药——青黛粉调敷在手掌上。凉凉的，缓解水疱瘙痒。但粉剂过敏的人不少——红肿了立刻洗掉，不是你的手娇气，是药和你不合。"
    )

    val HERBAL_HUATUOGAO = SpecificDrug(
        id = "herbal_huatuogao",
        name = "华佗膏",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.ANTIFUNGAL,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.0, onsetDays = 10,
        costMin = 6.0, costMax = 18.0,
        sideEffectNote = "华佗膏过敏停用，外用炉甘石舒缓",
        hasAllergyRisk = true,
        homeCare = "杜绝手脚触碰交叉感染真菌",
        detailDescription = "手癣的平价中成药膏——几块钱一支。老祖宗留下来的真菌方子。但药膏和皮肤的关系很私人——有人涂了就好，有人涂了更糟。过敏了就停——你不欠药一个机会。"
    )

    val HERBAL_YUNANZI = SpecificDrug(
        id = "herbal_yunanzi",
        name = "鸦胆子外用软膏",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = -0.05, onsetDays = 21,
        costMin = 10.0, costMax = 30.0,
        sideEffectNote = "鸦胆子腐蚀性较强，皮肤红肿刺痛即刻冲洗，服用西替利嗪抗过敏",
        hasAllergyRisk = true,
        restrictions = "仅适用于小面积鸡眼、跖疣",
        homeCare = "禁止抠抓疣体，疣体偏大医院冷冻治疗",
        detailDescription = "对付鸡眼、跖疣的民间方子——利用鸦胆子的腐蚀性把疣体一点一点蚀掉。有效——但疼。腐蚀性意味着健康的皮肤也会被烧到。小面积的可以试试——大的直接去医院冷冻，别硬撑。"
    )

    val HERBAL_YULIETIEGAO = SpecificDrug(
        id = "herbal_yulietiegao",
        name = "愈裂贴膏",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.0, onsetDays = 7,
        costMin = 5.0, costMax = 15.0,
        homeCare = "晚间厚涂保湿，减少赤脚行走",
        detailDescription = "足跟干裂了——贴一贴。中成药贴膏把裂开的皮肤封住、保湿、慢慢长好。不贵、不疼——就是每天贴的时候麻烦一点。但脚后跟裂开走路是真的疼。"
    )

    // --- 烫伤 ---
    val HERBAL_BURN = SpecificDrug(
        id = "herbal_burn",
        name = "烫伤草药",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.BURN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = -0.10, onsetDays = 7,
        costMin = 3.0, costMax = 12.0,
        sideEffectNote = "深二度及以上烫伤仅能临时止痛，伤口极易感染恶化",
        restrictions = "仅适合浅表层轻微烫伤",
        homeCare = "烫伤立刻冷水冲15-20分钟，创面保持干燥",
        detailDescription = "适合浅表层轻微烫伤——清凉、止痛。但深二度以上的烫伤，草药只能临时盖住疼——感染在皮下面悄悄扩散。你感觉不到——但它一直在。"
    )

    val HERBAL_SHIRUNSHAOSHANG = SpecificDrug(
        id = "herbal_shirunshaoshang",
        name = "湿润烧伤膏",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.BURN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.0, onsetDays = 5,
        costMin = 18.0, costMax = 45.0,
        sideEffectNote = "烧伤膏部分人群皮肤过敏，冲洗后外涂炉甘石",
        hasAllergyRisk = true,
        homeCare = "烫伤立刻冷水冲15-20分钟，创面保持干燥",
        detailDescription = "中成药烧伤膏——涂上去湿润清凉，隔绝空气、缓解灼痛。适用于一、二度烫伤的创面保护。深二度以上不要只靠它——感染的风险比省下的钱大得多。"
    )

    val HERBAL_KANGFUXIN = SpecificDrug(
        id = "herbal_kangfuxin",
        name = "康复新液",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.05, onsetDays = 3,
        costMin = 20.0, costMax = 50.0,
        hasAllergyRisk = true,
        homeCare = "创面保持干燥，擦伤避免沾水",
        detailDescription = "促进创面愈合的中成药液——擦伤、术后伤口、烫伤创面都可以用。加速皮肤再生——不是杀菌，是帮你的皮肤长回来。价格比碘伏贵——但它做的是另一件事：修复，不是消毒。"
    )

    // --- 筋骨 ---
    val HERBAL_ORTHOPEDIC = SpecificDrug(
        id = "herbal_orthopedic",
        name = "正骨草药",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.ORTHOPEDIC,
        applicableDiseases = listOf(DiseaseType.MUSCULOSKELETAL),
        isExternal = true,
        efficacyModifier = 0.0, onsetDays = 14,
        costMin = 10.0, costMax = 50.0,
        sideEffectNote = "医师手法不佳会造成骨骼错位，留下长期筋骨慢性病后遗症",
        restrictions = "骨折仅草药正骨存在随机性，推荐配合标准骨科药剂",
        homeCare = "急性期冷敷，3天后热敷，减少关节大幅度活动",
        detailDescription = "扭伤、轻度摔伤外敷可用。但骨折靠草药正骨——这是赌。赌赢了省钱，赌输了骨头长歪了陪你一辈子。不是草药不好——是它的精度就到这儿。"
    )

    val HERBAL_YUNNAN_BAIYAO = SpecificDrug(
        id = "herbal_yunnan_baiyao",
        name = "云南白药气雾剂",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.ORTHOPEDIC,
        applicableDiseases = listOf(DiseaseType.MUSCULOSKELETAL),
        isExternal = true,
        efficacyModifier = 0.05, onsetDays = 3,
        costMin = 25.0, costMax = 45.0,
        sideEffectNote = "部分人皮肤起疹，停用后服用氯雷他定",
        hasAllergyRisk = true,
        homeCare = "软组织扭伤急性期冷敷，3天后热敷",
        detailDescription = "跌打损伤的国民级选择——喷一喷，凉凉的，散瘀消肿。但有些人会对白药成分过敏——起疹子不是药不好，是你的身体在说：我和它不对付。"
    )

    val HERBAL_SANQI = SpecificDrug(
        id = "herbal_sanqi",
        name = "三七片（口服）",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.ORTHOPEDIC,
        applicableDiseases = listOf(DiseaseType.MUSCULOSKELETAL),
        efficacyModifier = 0.0, onsetDays = 5,
        costMin = 10.0, costMax = 30.0,
        hasAllergyRisk = true,
        homeCare = "骨挫伤长期静养，减少关节大幅度活动",
        detailDescription = "活血化瘀——老祖宗传下来的内服方。跌打之后吃几天，散瘀消肿。注意：过敏体质的人先少量试用——不是药不好，是每个人体质不一样。"
    )

    // --- 痤疮/毛囊炎 ---
    val HERBAL_DANSHENTONG = SpecificDrug(
        id = "herbal_danshentong",
        name = "丹参酮胶囊（口服）",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        efficacyModifier = 0.0, onsetDays = 10,
        costMin = 18.0, costMax = 40.0,
        sideEffectNote = "口服丹参酮起红疹，停药，服用西替利嗪",
        hasAllergyRisk = true,
        homeCare = "出汗后及时清洁，选用纯棉透气衣物",
        detailDescription = "痤疮、毛囊炎的中成药内服选择——从体内清火消炎。起效慢——但方向是对的。如果吃了起疹——说明你和丹参酮不对付，停掉换西药。"
    )

    val HERBAL_HUANGLIANGAO = SpecificDrug(
        id = "herbal_huangliangao",
        name = "黄连膏",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.0, onsetDays = 5,
        costMin = 8.0, costMax = 22.0,
        sideEffectNote = "黄连膏皮肤泛红瘙痒，清水清洁局部",
        hasAllergyRisk = true,
        homeCare = "禁止挤压脓点，防止深部感染",
        detailDescription = "毛囊炎的传统中成药膏——清热燥湿。对付细菌性毛囊炎有一定效果。但过敏了就停——红肿不是药在起作用，是你的毛囊在抗议。"
    )

    // --- 晒伤 ---
    val HERBAL_BOHE = SpecificDrug(
        id = "herbal_bohe",
        name = "复方薄荷脑软膏",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.0, onsetDays = 1,
        costMin = 6.0, costMax = 18.0,
        sideEffectNote = "中药软膏过敏，冲洗皮肤，扑尔敏夜间服用",
        hasAllergyRisk = true,
        homeCare = "正午时段做好防晒",
        detailDescription = "晒伤了——涂一层薄薄的薄荷膏，凉丝丝的。晒后镇静——不含激素，温和舒服。但它是安抚，不是治疗——严重晒伤起水泡了，炉甘石比它更对症。"
    )

    // --- 肠胃 ---
    val HERBAL_HUOXIANG = SpecificDrug(
        id = "herbal_huoxiang",
        name = "藿香正气软胶囊",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.DIGESTIVE,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.0, onsetDays = 2,
        costMin = 8.0, costMax = 20.0,
        sideEffectNote = "藿香正气过敏后停药，配合补液避免脱水",
        hasAllergyRisk = true,
        homeCare = "饮食清淡，避开生冷食物",
        detailDescription = "受凉腹泻、肠胃不舒服——藿香正气是中成药里最忙的那盒。夏天吃坏了、凉着了——一粒下去，肠胃安分了不少。但别把它当止泻药硬吃——它是调，不是堵。"
    )

    // --- 口腔 ---
    val HERBAL_XIGUASHUANG = SpecificDrug(
        id = "herbal_xiguashuang",
        name = "西瓜霜喷剂",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.ORAL_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.0, onsetDays = 3,
        costMin = 8.0, costMax = 20.0,
        sideEffectNote = "西瓜霜喷剂口腔黏膜刺痛肿胀，清水漱口，口服抗过敏药物",
        hasAllergyRisk = true,
        homeCare = "均衡补充维生素，少吃刺激性食物",
        detailDescription = "口腔溃疡的老朋友——喷一喷，凉凉的。不是治疗，是让溃疡面不那么疼——在它自己愈合之前。有些人喷了反而刺痛——那是过敏，不是你娇气。"
    )

    // --- 鼻炎 ---
    val HERBAL_XINQIN = SpecificDrug(
        id = "herbal_xinqin",
        name = "辛芩颗粒",
        tier = DrugTier.HERBAL_SIMPLE,
        usageCategory = DrugUsageCategory.RHINITIS,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.0, onsetDays = 14,
        costMin = 18.0, costMax = 45.0,
        sideEffectNote = "辛芩颗粒过敏者，停用全部中药，单用二代抗组胺西药",
        hasAllergyRisk = true,
        homeCare = "远离粉尘、花粉，定期清洁家居灰尘",
        detailDescription = "中药类抗过敏口服制剂——专门对付常年性过敏性鼻炎。起效慢，但温和——适合不想吃西药的人。但如果你本身对中药成分过敏——这不是药的问题，是你的免疫系统和它不对付。"
    )

    // ============================================
    // 二、平价常规西药（大众主流）
    // ============================================

    // --- 退烧/止痛 ---
    val WESTERN_IBUPROFEN = SpecificDrug(
        id = "western_ibuprofen",
        name = "布洛芬",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ANTIPYRETIC,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION, DiseaseType.MUSCULOSKELETAL),
        efficacyModifier = 0.10, onsetDays = 1,
        costMin = 8.0, costMax = 25.0,
        sideEffectNote = "长期连续按月服用，体质参数小幅下降，肠胃不适概率提升",
        restrictions = "38.5℃以上服用，空腹忌用",
        homeCare = "持续高烧2天以上就医检查炎症根源",
        detailDescription = "退烧、止痛、消炎——布洛芬是药店里最忙的那瓶药。半小时见效——比草药快。但别空腹吃，也别当糖丸——胃是沉默的，但它会记账。女性生理期痛经优先选用。"
    )

    val WESTERN_PARACETAMOL = SpecificDrug(
        id = "western_paracetamol",
        name = "对乙酰氨基酚",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ANTIPYRETIC,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.10, onsetDays = 1,
        costMin = 5.0, costMax = 18.0,
        sideEffectNote = "过量服用伤肝，严格按剂量服用",
        restrictions = "38.5℃以上服用，每日不超过4次",
        homeCare = "持续高烧2天以上就医检查炎症根源",
        detailDescription = "退烧止痛的另一选择——对胃的刺激比布洛芬小。适合胃不好、空腹也要吃药的人。退烧效果稳定——但别超量，肝会记账。"
    )

    // --- 感冒/上呼吸道 ---
    val WESTERN_COLD_COMPOUND = SpecificDrug(
        id = "western_cold_compound",
        name = "复方氨酚烷胺片",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.COLD_COUGH,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.05, onsetDays = 2,
        costMin = 8.0, costMax = 20.0,
        sideEffectNote = "含抗组胺成分，部分人嗜睡",
        restrictions = "不适用于儿童、孕妇",
        homeCare = "多喝温水，保证睡眠休息",
        detailDescription = "感冒的综合处理方案——退烧、止痛、缓解鼻塞、止咳，一片全包。方便——但别因为它方便就天天吃。感冒是自限性的——药是帮你舒服一点，不是替你打仗。"
    )

    val WESTERN_COUGH_SYRUP = SpecificDrug(
        id = "western_cough_syrup",
        name = "复方止咳片",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.COLD_COUGH,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.05, onsetDays = 3,
        costMin = 10.0, costMax = 30.0,
        sideEffectNote = "长期慢性咳嗽仅缓解症状，无法根除病根",
        restrictions = "不适用于慢性呼吸道疾病",
        homeCare = "多喝温水，保证睡眠休息",
        detailDescription = "短期压制咳嗽症状——普通感冒咳嗽可以治愈。但如果你已经咳了一个月以上——这片药只能让你舒服几小时，它治不了根。慢性咳嗽需要呼吸科医生的处方——不是药不好，是病种不对。"
    )

    // --- 抗真菌（足癣/手癣）---
    val WESTERN_TERBINAFINE = SpecificDrug(
        id = "western_terbinafine",
        name = "特比萘芬乳膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ANTIFUNGAL,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.15, onsetDays = 28,
        costMin = 15.0, costMax = 40.0,
        sideEffectNote = "潮湿环境仅拉长恢复时长，不会失效",
        homeCare = "脚趾缝擦干，鞋袜暴晒，不共用拖鞋",
        detailDescription = "常规脚气专用药膏——4-6周基本根除真菌。不像草药看天吃饭——潮湿天它只是慢一点，不会罢工。每天涂一次——坚持一个月，真菌会认输的。"
    )

    val WESTERN_BIFONAZOLE = SpecificDrug(
        id = "western_bifonazole",
        name = "联苯苄唑乳膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ANTIFUNGAL,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.15, onsetDays = 21,
        costMin = 12.0, costMax = 35.0,
        sideEffectNote = "无明显副作用",
        homeCare = "脚趾缝擦干，鞋袜暴晒，不共用拖鞋",
        detailDescription = "脚气的另一种西药选择——和特比萘芬机制不同，效果相似。每天涂一次——真菌最怕的就是你坚持。治脚气没有捷径——只有每天涂、每天擦干脚趾缝。"
    )

    val WESTERN_MICONAZOLE = SpecificDrug(
        id = "western_miconazole",
        name = "硝酸咪康唑乳膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ANTIFUNGAL,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.10, onsetDays = 14,
        costMin = 8.0, costMax = 25.0,
        homeCare = "杜绝手脚触碰交叉感染真菌",
        detailDescription = "手癣、体癣的常规西药选择——和脚气用药类似但更适合手部。手癣比脚气好治——因为手暴露在空气里，不像脚趾缝那么潮湿。但要记住：摸完脚再摸手——真菌会搬家。"
    )

    val WESTERN_KETOCONAZOLE = SpecificDrug(
        id = "western_ketoconazole",
        name = "酮康唑乳膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ANTIFUNGAL,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.10, onsetDays = 10,
        costMin = 8.0, costMax = 22.0,
        homeCare = "均衡补充维生素，少吃刺激性食物",
        detailDescription = "口角炎真菌感染的外用药——嘴角裂开、发红发痒，可能是真菌在作祟。涂几天就好——但别忘了同时补维生素B2，缺维生素才是根。"
    )

    // --- 皮肤伤口/消毒 ---
    val WESTERN_IODOPHOR = SpecificDrug(
        id = "western_iodophor",
        name = "碘伏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.10, onsetDays = 1,
        costMin = 3.0, costMax = 10.0,
        sideEffectNote = "无明显副作用",
        homeCare = "擦伤创面保持干燥",
        detailDescription = "皮肤擦伤、普通伤口消毒——碘伏是居家必备的温柔杀毒剂。不疼、不刺激、不会让你龇牙咧嘴。几块钱一瓶——但它在你看不见的地方，挡住了一整支细菌军团。"
    )

    val WESTERN_MUPIROCIN = SpecificDrug(
        id = "western_mupirocin",
        name = "莫匹罗星软膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.10, onsetDays = 2,
        costMin = 15.0, costMax = 35.0,
        sideEffectNote = "无明显副作用",
        homeCare = "创面保持干燥，禁止挤压脓点",
        detailDescription = "皮肤擦伤后的细菌防线——涂一层，那些看不见的细菌就进不来了。倒刺发炎、浅表伤口——莫匹罗星是皮肤的第一道安保。"
    )

    val WESTERN_FUSIDIC_ACID = SpecificDrug(
        id = "western_fusidic_acid",
        name = "夫西地酸乳膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.10, onsetDays = 3,
        costMin = 18.0, costMax = 40.0,
        homeCare = "禁止挤压脓点，防止深部感染",
        detailDescription = "细菌性毛囊炎的精准用药——夫西地酸专门对付皮肤表面的细菌感染。脓点别挤——涂药让它自己消退。挤破了的脓点，细菌会往更深处跑——那不是排毒，是扩散。"
    )

    val WESTERN_ETHACRIDINE = SpecificDrug(
        id = "western_ethacridine",
        name = "乳酸依沙吖啶溶液",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.05, onsetDays = 3,
        costMin = 5.0, costMax = 15.0,
        homeCare = "脚趾缝擦干，渗水创面保持通风",
        detailDescription = "脚气渗水、皮肤糜烂时的湿敷方案——黄药水。不是杀菌主力——是在创面还在流水的时候，帮你收干、清洁、给后面的杀菌药铺路。"
    )

    // --- 湿疹/皮炎/瘙痒 ---
    val WESTERN_CALAMINE = SpecificDrug(
        id = "western_calamine",
        name = "炉甘石洗剂",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.05, onsetDays = 1,
        costMin = 5.0, costMax = 15.0,
        sideEffectNote = "无明显副作用",
        homeCare = "规避化纤衣物，不用热水烫洗患处",
        detailDescription = "皮肤瘙痒的救星——湿疹、晒伤、蚊子包、过敏起疹——摇一摇涂上，凉凉的，马上不痒。不是治疗根本——是让你舒服一点，在身体自己修好之前。"
    )

    val WESTERN_DESONIDE = SpecificDrug(
        id = "western_desonide",
        name = "地奈德乳膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.15, onsetDays = 3,
        costMin = 15.0, costMax = 40.0,
        sideEffectNote = "激素类药膏，连续外用周期控制在1-2周以内，禁止长期大面积涂抹",
        restrictions = "面部慎用，不超过2周",
        homeCare = "规避化纤衣物，不用热水烫洗患处",
        detailDescription = "湿疹、皮炎、汗疱疹的低效激素药膏——涂了就好得快。但它有边界：连续用不要超过两周，不要涂一大片。激素药膏是借力——不能当护肤品天天抹。"
    )

    val WESTERN_HIRUDOID = SpecificDrug(
        id = "western_hirudoid",
        name = "多磺酸粘多糖乳膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE, DiseaseType.MUSCULOSKELETAL),
        isExternal = true,
        efficacyModifier = 0.05, onsetDays = 5,
        costMin = 25.0, costMax = 60.0,
        homeCare = "挫伤48小时冷敷，之后热敷散瘀",
        detailDescription = "淤青消得快——皮下挫伤、磕碰后涂这个，那片青紫会淡得快一些。不是必需品——淤青自己也会消。但在意的人可以用——皮肤上的印记有时候比疼更让人不舒服。"
    )

    // --- 足跟干裂/皮肤保湿 ---
    val WESTERN_UREA_VITAMIN_E = SpecificDrug(
        id = "western_urea_vitamin_e",
        name = "尿素维E乳膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.05, onsetDays = 7,
        costMin = 8.0, costMax = 22.0,
        homeCare = "晚间厚涂保湿，减少赤脚行走",
        detailDescription = "足跟干裂的保湿修复——尿素软化硬皮，维E滋润。每天涂一次，一星期后脚后跟不裂了。不是什么神奇药——就是给你的脚补了它该有的水分。"
    )

    val WESTERN_VASELINE = SpecificDrug(
        id = "western_vaseline",
        name = "凡士林/维E乳",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.05, onsetDays = 3,
        costMin = 5.0, costMax = 15.0,
        sideEffectNote = "润肤制剂极少过敏，出现红疹立刻清洗",
        homeCare = "做家务佩戴防水手套，避开洗洁精",
        detailDescription = "汗疱疹、皮肤干燥的基础保湿——凡士林是最朴素的护肤品。不治本——但在皮肤屏障被破坏的时候，帮你把水分锁住。最简单的往往最安全。"
    )

    // --- 鸡眼/疣 ---
    val WESTERN_SALICYLIC_ACID = SpecificDrug(
        id = "western_salicylic_acid",
        name = "水杨酸贴膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.05, onsetDays = 14,
        costMin = 8.0, costMax = 20.0,
        homeCare = "禁止抠抓疣体，疣体偏大医院冷冻治疗",
        detailDescription = "鸡眼的水杨酸方案——贴上去，角质一层层软化脱落。慢——但安全。每天换一张——两周后鸡眼变小了。大疣体别硬贴，直接去医院冷冻——一根针的事。"
    )

    val WESTERN_IMIQUIMOD = SpecificDrug(
        id = "western_imiquimod",
        name = "咪喹莫特乳膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.10, onsetDays = 28,
        costMin = 40.0, costMax = 100.0,
        homeCare = "禁止抠抓疣体",
        detailDescription = "跖疣的免疫调节外用药——不是直接杀死病毒，是唤醒你皮肤的免疫力自己清除疣体。贵——但对付顽固疣体比水杨酸有效。慢——免疫系统需要时间。"
    )

    // --- 痤疮 ---
    val WESTERN_ADAPALENE = SpecificDrug(
        id = "western_adapalene",
        name = "阿达帕林凝胶",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.10, onsetDays = 28,
        costMin = 30.0, costMax = 70.0,
        sideEffectNote = "初期可能脱皮、泛红——正常反应，持续使用2-4周后适应",
        restrictions = "晚间使用，日间注意防晒",
        homeCare = "出汗后及时清洁，选用纯棉透气衣物",
        detailDescription = "痤疮的一线外用药——不是今天涂明天好，是帮你重建毛囊的正常角质代谢。前两周可能脱皮——那是旧皮肤在退役。坚持一个月——新皮肤会告诉你值不值得。"
    )

    val WESTERN_CLINDAMYCIN = SpecificDrug(
        id = "western_clindamycin",
        name = "克林霉素凝胶",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.SKIN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.10, onsetDays = 5,
        costMin = 20.0, costMax = 50.0,
        homeCare = "出汗后及时清洁，选用纯棉透气衣物",
        detailDescription = "红肿脓疱型痤疮的抗生素外用药——杀菌消炎，针对正在发炎的痘痘。涂在红疙瘩上，几天后它瘪下去了。但别大面积涂——抗生素外用药也要用在刀刃上。"
    )

    // --- 烫伤 ---
    val WESTERN_SILVER_SULFADIAZINE = SpecificDrug(
        id = "western_silver_sulfadiazine",
        name = "磺胺嘧啶银乳膏",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.BURN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.15, onsetDays = 3,
        costMin = 25.0, costMax = 60.0,
        homeCare = "烫伤立刻冷水冲15-20分钟，创面保持干燥",
        detailDescription = "二度烫伤的西药标准方案——磺胺嘧啶银既能杀菌又能促进创面愈合。比草药可靠、比湿润烧伤膏的抗菌能力强。烫伤最怕感染——这个药帮你挡住的，就是那层看不见的危险。"
    )

    // --- 筋骨扭伤 ---
    val WESTERN_DICLOFENAC_GEL = SpecificDrug(
        id = "western_diclofenac_gel",
        name = "双氯芬酸二乙胺乳胶剂",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ORTHOPEDIC,
        applicableDiseases = listOf(DiseaseType.MUSCULOSKELETAL),
        isExternal = true,
        efficacyModifier = 0.10, onsetDays = 2,
        costMin = 15.0, costMax = 40.0,
        homeCare = "急性期冷敷，3天后热敷，减少关节大幅度活动",
        detailDescription = "扭伤、软组织肿痛的外用消炎止痛凝胶——涂在痛处，凉凉的，肿慢慢消下去。和布洛芬一外一内——组合使用效果最好。但记住：骨头的问题涂药没用——那是物理，不是炎症。"
    )

    // --- 肠胃 ---
    val WESTERN_MONTMORILLONITE = SpecificDrug(
        id = "western_montmorillonite",
        name = "蒙脱石散",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.DIGESTIVE,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.10, onsetDays = 1,
        costMin = 10.0, costMax = 30.0,
        homeCare = "饮食清淡，避开生冷食物",
        detailDescription = "拉肚子了——蒙脱石散不是杀菌，是给肠道涂一层保护膜。把毒素和细菌吸附掉、排出去。一天见效——但记住它只是应急，不是让你继续吃不干净东西的借口。"
    )

    val WESTERN_PROBIOTICS = SpecificDrug(
        id = "western_probiotics",
        name = "双歧杆菌制剂",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.DIGESTIVE,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.05, onsetDays = 5,
        costMin = 20.0, costMax = 60.0,
        sideEffectNote = "无明显副作用",
        homeCare = "饮食清淡，避开生冷食物",
        detailDescription = "调节肠道菌群——拉完肚子、吃完抗生素之后，肠道里的有益菌被清空了。双歧杆菌帮它们重新种回去。不是立竿见影——但肠道生态重建，急不得。"
    )

    // --- 口腔 ---
    val WESTERN_CHLORHEXIDINE = SpecificDrug(
        id = "western_chlorhexidine",
        name = "氯己定含漱液",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ORAL_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isExternal = true,
        efficacyModifier = 0.10, onsetDays = 3,
        costMin = 10.0, costMax = 25.0,
        homeCare = "均衡补充维生素，少吃刺激性食物",
        detailDescription = "口腔溃疡、牙龈炎的消毒含漱液——漱一漱，嘴里干净了。不是治好溃疡——是让溃疡周围不感染，给你口腔黏膜自己愈合的时间。味道不太好——但有用。"
    )

    val WESTERN_VITAMIN_B2 = SpecificDrug(
        id = "western_vitamin_b2",
        name = "维生素B2",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ORAL_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        efficacyModifier = 0.05, onsetDays = 7,
        costMin = 3.0, costMax = 10.0,
        sideEffectNote = "无明显副作用",
        homeCare = "均衡补充维生素，少吃刺激性食物",
        detailDescription = "口角炎、口腔溃疡的根源之一——缺维生素B2。不是药——是你的身体缺了一种原材料。每天一片，几块钱——嘴角裂开的口子慢慢愈合。最便宜也最本质的修复。"
    )

    // --- 抗过敏 ---
    val WESTERN_CHLORPHENAMINE = SpecificDrug(
        id = "western_chlorphenamine",
        name = "马来酸氯苯那敏（扑尔敏）",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ANTIHISTAMINE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE, DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.05, onsetDays = 1,
        costMin = 3.0, costMax = 10.0,
        sideEffectNote = "一代抗组胺药，嗜睡副作用显著，服药后禁止驾车、操作机械",
        restrictions = "优先睡前使用",
        homeCare = "远离粉尘、花粉，定期清洁家居灰尘",
        detailDescription = "抗过敏效果强——但会困。困到你睁不开眼的那种困。所以睡前吃——睡一觉，过敏也好了，觉也补了。白天要吃的话——选氯雷他定或西替利嗪。"
    )

    val WESTERN_LORATADINE = SpecificDrug(
        id = "western_loratadine",
        name = "氯雷他定",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ANTIHISTAMINE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE, DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.05, onsetDays = 1,
        costMin = 10.0, costMax = 30.0,
        sideEffectNote = "二代抗组胺药，困倦感极轻，适合日间使用",
        homeCare = "远离粉尘、花粉，定期清洁家居灰尘",
        detailDescription = "白天吃的抗过敏药——不困、不晕、正常工作。过敏了吃一片——该干嘛干嘛。和扑尔敏的区别：一个让你睡，一个让你醒——选哪个看你的时间表。"
    )

    val WESTERN_CETIRIZINE = SpecificDrug(
        id = "western_cetirizine",
        name = "西替利嗪",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.ANTIHISTAMINE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE, DiseaseType.MILD_INFECTION),
        efficacyModifier = 0.05, onsetDays = 1,
        costMin = 8.0, costMax = 25.0,
        sideEffectNote = "二代抗组胺药，少数人仍有轻微困倦",
        homeCare = "远离粉尘、花粉，定期清洁家居灰尘",
        detailDescription = "氯雷他定的同类药——同属二代抗组胺药。困倦感和氯雷他定差不多——极轻。如果氯雷他定对你效果不够好——换西替利嗪试试。每个人对同级别的药反应略有不同。"
    )

    // --- 鼻炎 ---
    val WESTERN_SALINE_NASAL = SpecificDrug(
        id = "western_saline_nasal",
        name = "生理盐水洗鼻剂",
        tier = DrugTier.REGULAR_WESTERN,
        usageCategory = DrugUsageCategory.RHINITIS,
        applicableDiseases = listOf(DiseaseType.MILD_INFECTION),
        isExternal = true,
        efficacyModifier = 0.05, onsetDays = 1,
        costMin = 5.0, costMax = 15.0,
        sideEffectNote = "无明显副作用",
        homeCare = "远离粉尘、花粉，定期清洁家居灰尘",
        detailDescription = "过敏性鼻炎的第一步——不是药，是清洗。每天用生理盐水冲一冲鼻腔——把花粉、灰尘、过敏原冲走。零副作用——因为它就是盐水。效果温和——但坚持下来，鼻子的抗议声会小很多。"
    )

    // ============================================
    // 三、标准医用药剂（需检查确诊）
    // ============================================

    val STANDARD_ANTIBIOTIC = SpecificDrug(
        id = "standard_antibiotic",
        name = "抗生素消炎药",
        tier = DrugTier.STANDARD_MEDICAL,
        usageCategory = DrugUsageCategory.ANTIBIOTIC,
        applicableDiseases = listOf(DiseaseType.SEVERE_CONDITION, DiseaseType.RESPIRATORY_CHRONIC, DiseaseType.SKIN_ISSUE),
        isPrescription = true,
        efficacyModifier = 0.15, onsetDays = 3,
        costMin = 50.0, costMax = 200.0,
        sideEffectNote = "滥用后后续重症药效会降低（耐药性累积）",
        restrictions = "必须验血确诊后使用，不可自行购买长期服用",
        homeCare = "遵医嘱完成全部疗程，不可提前停药",
        detailDescription = "肺炎、伤口重度感染、摔伤后化脓——必须验血确诊后使用。抗生素不是水——用一次，细菌就学一次。下次再生病的时候——同样的药可能就不管用了。完成全部疗程——别提前停。"
    )

    val STANDARD_TERBINAFINE_ORAL = SpecificDrug(
        id = "standard_terbinafine_oral",
        name = "盐酸特比萘芬片",
        tier = DrugTier.STANDARD_MEDICAL,
        usageCategory = DrugUsageCategory.ANTIFUNGAL,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isPrescription = true,
        efficacyModifier = 0.20, onsetDays = 14,
        costMin = 60.0, costMax = 180.0,
        sideEffectNote = "口服抗真菌药，需监测肝功能",
        restrictions = "处方药——大面积顽固感染才用，不可自行购买长期服用",
        homeCare = "遵医嘱完成全部疗程",
        detailDescription = "脚气太重、乳膏压不住了——口服特比萘芬从血液里杀真菌。强——但需要医生开、需要查肝功能。外用药搞不定的顽固脚气——这是最后的常规武器。"
    )

    val STANDARD_DOXYCYCLINE = SpecificDrug(
        id = "standard_doxycycline",
        name = "多西环素",
        tier = DrugTier.STANDARD_MEDICAL,
        usageCategory = DrugUsageCategory.ANTIBIOTIC,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isPrescription = true,
        efficacyModifier = 0.15, onsetDays = 7,
        costMin = 40.0, costMax = 150.0,
        sideEffectNote = "四环素类抗生素，需医生诊断后遵医嘱服用，不可自行购买长期服药",
        restrictions = "处方药——大面积脓疱型痤疮才使用",
        homeCare = "出汗后及时清洁，选用纯棉透气衣物，遵医嘱完成全部疗程",
        detailDescription = "胸背部大面积脓疱型痤疮——外用药不够了，这时需要口服抗生素从体内消炎。多西环素是对付重度痤疮的标准选择——但它是处方药，需要医生评估。抗生素永远是你能不用就不用的东西——但该用的时候，也别硬扛。"
    )

    val STANDARD_ORTHOPEDIC = SpecificDrug(
        id = "standard_orthopedic",
        name = "骨科复位配套药剂",
        tier = DrugTier.STANDARD_MEDICAL,
        usageCategory = DrugUsageCategory.ORTHOPEDIC,
        applicableDiseases = listOf(DiseaseType.MUSCULOSKELETAL),
        isPrescription = true,
        efficacyModifier = 0.20, onsetDays = 7,
        costMin = 200.0, costMax = 800.0,
        sideEffectNote = "配合复位手术，大幅降低骨骼畸形后遗症概率",
        restrictions = "骨折手术之后配套使用，需拍片确诊",
        homeCare = "术后遵医嘱康复训练，骨挫伤长期静养",
        detailDescription = "骨折手术之后的配套用药——不是药在治你的骨头，是药在帮你稳住手术的效果。配合复位手术，骨头长在对的位置——这不是草药能做到的精度。贵——但错位的骨头要跟你一辈子。"
    )

    val STANDARD_RESPIRATORY = SpecificDrug(
        id = "standard_respiratory",
        name = "呼吸道专用处方药",
        tier = DrugTier.STANDARD_MEDICAL,
        usageCategory = DrugUsageCategory.COLD_COUGH,
        applicableDiseases = listOf(DiseaseType.RESPIRATORY_CHRONIC),
        isPrescription = true,
        efficacyModifier = 0.20, onsetDays = 5,
        costMin = 80.0, costMax = 300.0,
        sideEffectNote = "针对久咳形成的肺炎炎症，解决慢性咳嗽病根",
        restrictions = "需要拍片/化验确诊后使用",
        homeCare = "多喝温水，保证睡眠休息，遵医嘱完成全部疗程",
        detailDescription = "复方止咳片治不了的——它能治。针对久咳形成的肺炎炎症——不是压症状，是解决病根。需要拍片、需要化验——这不是折腾你，是确认你的肺到底在跟什么战斗。"
    )

    // ============================================
    // 四、高端特效药（富人专属）
    // ============================================

    val PREMIUM_SEVERE_INFECTION = SpecificDrug(
        id = "premium_severe_infection",
        name = "重症退烧抗感染特效药",
        tier = DrugTier.PREMIUM_SPECIALTY,
        usageCategory = DrugUsageCategory.ANTIBIOTIC,
        applicableDiseases = listOf(DiseaseType.SEVERE_CONDITION),
        isPrescription = true,
        efficacyModifier = 0.10, onsetDays = 2,
        costMin = 500.0, costMax = 3000.0,
        sideEffectNote = "后遗症概率极低",
        restrictions = "重症监护级别用药",
        homeCare = "遵医嘱，可能需住院治疗",
        detailDescription = "应对重度感染、高烧休克——这是医院里的最后一道防线。后遗症概率极低——但代价是极高的价格。一场大病用这个——可能是几年积蓄。但活着——活着就有机会。"
    )

    val PREMIUM_BURN_REPAIR = SpecificDrug(
        id = "premium_burn_repair",
        name = "深度烫伤修复药剂",
        tier = DrugTier.PREMIUM_SPECIALTY,
        usageCategory = DrugUsageCategory.BURN_CARE,
        applicableDiseases = listOf(DiseaseType.SKIN_ISSUE),
        isPrescription = true,
        efficacyModifier = 0.10, onsetDays = 7,
        costMin = 800.0, costMax = 5000.0,
        sideEffectNote = "极大减少疤痕、皮肤永久损伤",
        restrictions = "严重烫伤专用，需住院治疗",
        homeCare = "烫伤立刻冷水冲15-20分钟，立即就医",
        detailDescription = "处理重度烫伤——不是让你不疼，是让你的皮肤不至于留下一辈子的烙印。草药能止疼——但它修复不了被烧掉的皮肤层。这个可以——但价格会告诉你：皮肤有多贵。"
    )

    val PREMIUM_CHRONIC_CONTROL = SpecificDrug(
        id = "premium_chronic_control",
        name = "慢性病长效控制药",
        tier = DrugTier.PREMIUM_SPECIALTY,
        usageCategory = DrugUsageCategory.CHRONIC_CARE,
        applicableDiseases = listOf(DiseaseType.METABOLIC_CHRONIC),
        isPrescription = true,
        efficacyModifier = 0.10, onsetDays = 30,
        costMin = 300.0, costMax = 1500.0,
        sideEffectNote = "月度持续高额开支",
        restrictions = "长期服用，每月固定支出",
        homeCare = "定期监测血压、血糖，保持健康饮食作息",
        detailDescription = "高血压、代谢类慢病长期使用——不是为了治，是为了稳。每月固定一笔钱——不是奢侈，是你身体的基础维护费。不吃也能活——但你的身体素质基线会在你不注意的时候，一点一点往下掉。"
    )

    // ============================================
    // 全部药品列表
    // ============================================

    val allDrugs: List<SpecificDrug> = listOf(
        // —— 简易草药/中成药 ——
        HERBAL_FEVER, HERBAL_CHAIHU, HERBAL_GANMAOLING, HERBAL_LIANHUA,
        HERBAL_ANTIFUNGAL, HERBAL_ZUGUANGSAN, HERBAL_CHUSHIZHIYANG, HERBAL_QINGDAISAN,
        HERBAL_HUATUOGAO, HERBAL_YUNANZI, HERBAL_YULIETIEGAO,
        HERBAL_BURN, HERBAL_SHIRUNSHAOSHANG, HERBAL_KANGFUXIN,
        HERBAL_ORTHOPEDIC, HERBAL_YUNNAN_BAIYAO, HERBAL_SANQI,
        HERBAL_DANSHENTONG, HERBAL_HUANGLIANGAO, HERBAL_BOHE,
        HERBAL_HUOXIANG, HERBAL_XIGUASHUANG, HERBAL_XINQIN,
        // —— 平价西药 ——
        WESTERN_IBUPROFEN, WESTERN_PARACETAMOL, WESTERN_COLD_COMPOUND, WESTERN_COUGH_SYRUP,
        WESTERN_TERBINAFINE, WESTERN_BIFONAZOLE, WESTERN_MICONAZOLE, WESTERN_KETOCONAZOLE,
        WESTERN_IODOPHOR, WESTERN_MUPIROCIN, WESTERN_FUSIDIC_ACID, WESTERN_ETHACRIDINE,
        WESTERN_CALAMINE, WESTERN_DESONIDE, WESTERN_HIRUDOID,
        WESTERN_UREA_VITAMIN_E, WESTERN_VASELINE,
        WESTERN_SALICYLIC_ACID, WESTERN_IMIQUIMOD,
        WESTERN_ADAPALENE, WESTERN_CLINDAMYCIN,
        WESTERN_SILVER_SULFADIAZINE,
        WESTERN_DICLOFENAC_GEL,
        WESTERN_MONTMORILLONITE, WESTERN_PROBIOTICS,
        WESTERN_CHLORHEXIDINE, WESTERN_VITAMIN_B2,
        WESTERN_CHLORPHENAMINE, WESTERN_LORATADINE, WESTERN_CETIRIZINE,
        WESTERN_SALINE_NASAL,
        // —— 标准医疗 ——
        STANDARD_ANTIBIOTIC, STANDARD_TERBINAFINE_ORAL, STANDARD_DOXYCYCLINE,
        STANDARD_ORTHOPEDIC, STANDARD_RESPIRATORY,
        // —— 高端特效 ——
        PREMIUM_SEVERE_INFECTION, PREMIUM_BURN_REPAIR, PREMIUM_CHRONIC_CONTROL
    )
}

// ============================================
// 二、病症-药物匹配表
// ============================================

/**
 * 病症-药物匹配规则
 */
object DiseaseDrugMatcher {

    /**
     * 根据病症获取可选药品列表（按档位分组）
     */
    fun getAvailableDrugs(diseaseType: DiseaseType): Map<DrugTier, List<SpecificDrug>> {
        return DrugCatalog.allDrugs
            .filter { it.applicableDiseases.contains(diseaseType) }
            .groupBy { it.tier }
    }

    /**
     * 根据病症和用途分类获取可选药品（更细粒度匹配）
     */
    fun getAvailableDrugsByUsage(diseaseType: DiseaseType, usageCategory: DrugUsageCategory): List<SpecificDrug> {
        return DrugCatalog.allDrugs
            .filter { it.applicableDiseases.contains(diseaseType) && it.usageCategory == usageCategory }
    }

    /**
     * 获取某档位下针对某病症的推荐药物
     */
    fun getRecommendedDrug(diseaseType: DiseaseType, tier: DrugTier): SpecificDrug? {
        val drugs = getAvailableDrugs(diseaseType)[tier] ?: return null
        // 优先选非处方药（if same tier）
        return drugs.minByOrNull { if (it.isPrescription) 1 else 0 }
    }

    /**
     * 根据家庭收入自动匹配默认药物
     * 低收入 → 草药，中等 → 平价西药，高收入 → 标准医疗
     */
    fun autoMatchByIncome(diseaseType: DiseaseType, monthlyIncome: Double): Pair<SpecificDrug?, DrugTier> {
        val tier = when {
            monthlyIncome < 3000 -> DrugTier.HERBAL_SIMPLE
            monthlyIncome < 10000 -> DrugTier.REGULAR_WESTERN
            monthlyIncome < 30000 -> DrugTier.STANDARD_MEDICAL
            else -> DrugTier.PREMIUM_SPECIALTY
        }
        val drug = getRecommendedDrug(diseaseType, tier)
        return Pair(drug, tier)
    }

    /**
     * 获取病症的快速三选一方案（草药/西药/自愈）
     */
    fun getQuickTripleChoice(diseaseType: DiseaseType): Triple<SpecificDrug?, SpecificDrug?, String> {
        val herbal = getRecommendedDrug(diseaseType, DrugTier.HERBAL_SIMPLE)
        val western = getRecommendedDrug(diseaseType, DrugTier.REGULAR_WESTERN)
        val selfHealNote = when {
            diseaseType.canSelfHeal -> "硬扛自愈——零成本，无副作用，但恢复慢且可能恶化"
            else -> "硬扛自愈——${diseaseType.label}不可自愈，拖延将导致慢性化"
        }
        return Triple(herbal, western, selfHealNote)
    }

    /**
     * 按用途分类获取该病症下的用药方案汇总
     * 用于详情弹窗展示：该病症下有哪些用途的药、各有哪些选项
     */
    fun getDrugSummaryByDisease(diseaseType: DiseaseType): Map<DrugUsageCategory, Map<DrugTier, List<SpecificDrug>>> {
        return DrugCatalog.allDrugs
            .filter { it.applicableDiseases.contains(diseaseType) }
            .groupBy { it.usageCategory }
            .mapValues { (_, drugs) -> drugs.groupBy { it.tier } }
    }
}