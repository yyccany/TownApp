package com.example.townapp.data

/**
 * 全球化多标准合规判定映射层（方案A：独立合规层，原生数据零改动）
 *
 * 核心规则：
 * - 原生商品量化数据永不修改
 * - 标准切换仅改变"合规判定结果"
 * - 系统不对任何标准做优劣评判，仅客观展示
 */

// ============================================
// 标准类型枚举
// ============================================
enum class StandardType(val displayName: String, val icon: String, val description: String) {
    GB("国标", "🇨🇳", "中国国家标准 — 基于《食品安全国家标准》《药典》"),
    FDA("FDA", "🇺🇸", "美国食品药品监督管理局 — 基于 CFR Title 21"),
    CE("CE", "🇪🇺", "欧盟 CE 认证 — 基于 EU Regulation 2017/745"),
    JIS("JIS", "🇯🇵", "日本工业标准 — 基于 JIS Q 1001 / 药事法")
}

// ============================================
// 合规等级枚举
// ============================================
enum class ComplianceLevel(
    val label: String,
    val color: Long,
    val description: String
) {
    EXCEED("优于标准", 0xFF2E7D32, "各项指标均优于该标准限值"),
    PASS("符合标准", 0xFF4CAF50, "各项指标满足该标准要求"),
    WARN("接近限值", 0xFFFFC107, "部分指标接近标准上限，建议关注"),
    FAIL("不合规", 0xFFF44336, "存在指标超出该标准允许范围")
}

// ============================================
// 合规映射数据模型
// ============================================
data class StandardComplianceMapping(
    val itemId: Int,
    val standardType: StandardType,
    val complianceLevel: ComplianceLevel,
    val riskLabel: String,       // 该标准下的风险判定（与原生 healthRisk 独立）
    val detailNote: String = ""  // 合规判定依据简述
)

// ============================================
// 全球化标准映射仓库
// ============================================
object GlobalStandardRepository {

    // ============================================================
    // 食品合规映射（基于食品营养量化指标，映射到各标准限值）
    // ============================================================
    private val foodCompliance: List<StandardComplianceMapping> = listOf(
        // ---- 蛋奶类 (id 1-10) ----
        StandardComplianceMapping(1, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "蛋白质13.5g/100g满足国标蛋类标准；大肠菌群≤100CFU"),
        StandardComplianceMapping(1, StandardType.FDA, ComplianceLevel.EXCEED, "安全",
            "散养模式符合FDA动物福利指南；沙门氏菌检测通过"),
        StandardComplianceMapping(1, StandardType.CE, ComplianceLevel.PASS, "低风险",
            "符合EU 853/2004蛋类卫生标准"),
        StandardComplianceMapping(1, StandardType.JIS, ComplianceLevel.PASS, "低风险",
            "满足JIS鸡蛋品质分级特级标准"),

        StandardComplianceMapping(2, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "乳脂3.6g符合GB 25190灭菌乳标准；菌落总数<20000 CFU/ml"),
        StandardComplianceMapping(2, StandardType.FDA, ComplianceLevel.PASS, "低风险",
            "符合FDA Grade A巴氏杀菌乳标准"),
        StandardComplianceMapping(2, StandardType.CE, ComplianceLevel.EXCEED, "安全",
            "各项指标优于EU 853/2004液态乳标准"),
        StandardComplianceMapping(2, StandardType.JIS, ComplianceLevel.PASS, "低风险",
            "符合JIS乳等省令成分规格"),

        StandardComplianceMapping(6, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "符合GB 5420干酪标准；黄曲霉毒素M1<0.5μg/kg"),
        StandardComplianceMapping(6, StandardType.FDA, ComplianceLevel.PASS, "低风险",
            "符合FDA 21CFR 133切达干酪标准"),
        StandardComplianceMapping(6, StandardType.CE, ComplianceLevel.EXCEED, "安全",
            "符合EU PDO切达干酪原产地保护标准"),
        StandardComplianceMapping(6, StandardType.JIS, ComplianceLevel.PASS, "低风险",
            "符合JIS奶酪类加工标准"),

        // ---- 肉禽类 (id 11-28) ----
        StandardComplianceMapping(11, StandardType.GB, ComplianceLevel.EXCEED, "安全",
            "符合GB 2707鲜(冻)畜肉标准；挥发性盐基氮≤15mg/100g"),
        StandardComplianceMapping(11, StandardType.FDA, ComplianceLevel.PASS, "低风险",
            "符合USDA鸡肉标准；无抗生素残留"),
        StandardComplianceMapping(11, StandardType.CE, ComplianceLevel.PASS, "低风险",
            "符合EU 543/2008禽肉销售标准"),
        StandardComplianceMapping(11, StandardType.JIS, ComplianceLevel.EXCEED, "安全",
            "满足JAS有机畜产品标准"),

        StandardComplianceMapping(18, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "符合GB 10136动物性水产制品标准"),
        StandardComplianceMapping(18, StandardType.FDA, ComplianceLevel.EXCEED, "安全",
            "符合FDA Seafood HACCP标准；汞含量<0.1ppm"),
        StandardComplianceMapping(18, StandardType.CE, ComplianceLevel.EXCEED, "安全",
            "符合EU 853/2004水产品标准；养殖三文鱼溯源完整"),
        StandardComplianceMapping(18, StandardType.JIS, ComplianceLevel.PASS, "低风险",
            "符合JIS生食用鲜鱼贝类标准"),

        // ---- 猪肉类 (id 29-38) ----
        StandardComplianceMapping(29, StandardType.GB, ComplianceLevel.WARN, "中风险",
            "脂肪含量37g/100g超过GB 28050低脂宣称限值；饱和脂肪酸偏高"),
        StandardComplianceMapping(29, StandardType.FDA, ComplianceLevel.WARN, "中风险",
            "饱和脂肪≥每日推荐摄入量33%，标注建议限制摄入"),
        StandardComplianceMapping(29, StandardType.CE, ComplianceLevel.WARN, "中风险",
            "高脂分类；EFSA建议饱和脂肪≤总能量10%"),
        StandardComplianceMapping(29, StandardType.JIS, ComplianceLevel.WARN, "中风险",
            "脂质含量超出日本食事摄取基准上限"),

        StandardComplianceMapping(30, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "脂肪7g/100g符合低脂标准；蛋白质20g满足瘦肉分类"),
        StandardComplianceMapping(30, StandardType.FDA, ComplianceLevel.EXCEED, "安全",
            "符合FDA瘦肉标准(fat<10%)；蛋白质含量优秀"),
        StandardComplianceMapping(30, StandardType.CE, ComplianceLevel.EXCEED, "安全",
            "蛋白质含量满足EU 1169/2011营养宣称中'高蛋白'标准"),
        StandardComplianceMapping(30, StandardType.JIS, ComplianceLevel.PASS, "低风险",
            "符合日本食品表示基准的脂质限制"),

        StandardComplianceMapping(37, StandardType.GB, ComplianceLevel.WARN, "中风险",
            "加工肉制品；GB 2760亚硝酸盐限量≤30mg/kg需标注"),
        StandardComplianceMapping(37, StandardType.FDA, ComplianceLevel.FAIL, "高风险",
            "IARC 1类致癌物(加工肉制品)；FDA要求标注健康警示"),
        StandardComplianceMapping(37, StandardType.CE, ComplianceLevel.FAIL, "高风险",
            "EFSA确认加工肉与结直肠癌风险正相关"),
        StandardComplianceMapping(37, StandardType.JIS, ComplianceLevel.WARN, "中风险",
            "亚硝酸根残留需符合食品卫生法第11条"),

        // ---- 牛肉类 (id 39-44) ----
        StandardComplianceMapping(40, StandardType.GB, ComplianceLevel.EXCEED, "安全",
            "符合GB 2707鲜(冻)畜肉标准；脂肪8g/100g属于低脂"),
        StandardComplianceMapping(40, StandardType.FDA, ComplianceLevel.EXCEED, "安全",
            "符合USDA Prime级牛肉标准；蛋白质含量优秀"),
        StandardComplianceMapping(40, StandardType.CE, ComplianceLevel.EXCEED, "安全",
            "符合EU 1308/2013优质牛肉分类标准"),
        StandardComplianceMapping(40, StandardType.JIS, ComplianceLevel.EXCEED, "安全",
            "满足日本牛肉等级评定A5标准"),

        // ---- 加工零食类 (常见高糖高脂) ----
        // --- 使用假想的加工食品ID范围映射 ---
        StandardComplianceMapping(100, StandardType.GB, ComplianceLevel.WARN, "中风险",
            "糖含量超过GB 28050低糖宣称限值(≤5g/100g)"),
        StandardComplianceMapping(100, StandardType.FDA, ComplianceLevel.WARN, "中风险",
            "添加糖≥每日推荐摄入量15%；建议限制"),
        StandardComplianceMapping(100, StandardType.CE, ComplianceLevel.WARN, "中风险",
            "含糖量触发EU营养标签红色警示"),
        StandardComplianceMapping(100, StandardType.JIS, ComplianceLevel.WARN, "中风险",
            "糖分超出日本食事摄取基准"),

        StandardComplianceMapping(101, StandardType.GB, ComplianceLevel.FAIL, "高风险",
            "饱和脂肪+反式脂肪总量超标；GB 28050 需强制标注"),
        StandardComplianceMapping(101, StandardType.FDA, ComplianceLevel.FAIL, "高风险",
            "反式脂肪>0.5g/份 触发FDA强制警示标签"),
        StandardComplianceMapping(101, StandardType.CE, ComplianceLevel.FAIL, "高风险",
            "反式脂肪含量超出EU 2019/649设定的2g/100g上限"),
        StandardComplianceMapping(101, StandardType.JIS, ComplianceLevel.FAIL, "高风险",
            "加工油脂含量触发食品卫生法标示义务")
    )

    // ============================================================
    // OTC药品合规映射（基于ConsumableRepository id 501-555）
    // ============================================================
    private val medicineCompliance: List<StandardComplianceMapping> = listOf(
        // 501 布洛芬缓释胶囊
        StandardComplianceMapping(501, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "收录于《中国药典》2020版；符合化学药品质量标准"),
        StandardComplianceMapping(501, StandardType.FDA, ComplianceLevel.EXCEED, "安全",
            "FDA OTC Monograph NDA 批准的NSAID；安全记录超40年"),
        StandardComplianceMapping(501, StandardType.CE, ComplianceLevel.PASS, "低风险",
            "EMA评估报告确认收益大于风险；含胃肠道副作用提示"),
        StandardComplianceMapping(501, StandardType.JIS, ComplianceLevel.PASS, "低风险",
            "PMDA批准为指定第二类医药品；标注'胃障害注意'"),

        // 502 对乙酰氨基酚片
        StandardComplianceMapping(502, StandardType.GB, ComplianceLevel.EXCEED, "安全",
            "收录于《中国药典》；质量标准与国际一致"),
        StandardComplianceMapping(502, StandardType.FDA, ComplianceLevel.PASS, "低风险",
            "FDA OTC Monograph批准；标注日最大剂量4g 肝毒性风险"),
        StandardComplianceMapping(502, StandardType.CE, ComplianceLevel.PASS, "低风险",
            "EMA建议限用剂量≤3g/日减低肝毒性"),
        StandardComplianceMapping(502, StandardType.JIS, ComplianceLevel.PASS, "低风险",
            "PMDA批准；处方集标注'肝障害に注意'"),

        // 503 阿莫西林胶囊 (处方抗生素)
        StandardComplianceMapping(503, StandardType.GB, ComplianceLevel.WARN, "中风险",
            "处方药；国标要求标注β-内酰胺类过敏警示"),
        StandardComplianceMapping(503, StandardType.FDA, ComplianceLevel.WARN, "中风险",
            "FDA处方药(Prescription Only)；标注过敏反应与耐药性风险"),
        StandardComplianceMapping(503, StandardType.CE, ComplianceLevel.WARN, "中风险",
            "EMA要求限制非必要使用以控制抗生素耐药"),
        StandardComplianceMapping(503, StandardType.JIS, ComplianceLevel.WARN, "中风险",
            "处方笺医药品；标注'ペニシリンショック'过敏警示"),

        // 504 氯雷他定片 (抗过敏)
        StandardComplianceMapping(504, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "OTC甲类；收录于《中国药典》"),
        StandardComplianceMapping(504, StandardType.FDA, ComplianceLevel.EXCEED, "安全",
            "FDA OTC批准；第二代抗组胺药，嗜睡副作用低于一代"),
        StandardComplianceMapping(504, StandardType.CE, ComplianceLevel.PASS, "低风险",
            "EMA集中审评通过；非镇静抗组胺药"),
        StandardComplianceMapping(504, StandardType.JIS, ComplianceLevel.PASS, "低风险",
            "PMDA批准指定第二类医药品"),

        // 505 奥美拉唑肠溶胶囊 (胃药)
        StandardComplianceMapping(505, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "收录于《中国药典》；肠溶制剂标准合格"),
        StandardComplianceMapping(505, StandardType.FDA, ComplianceLevel.PASS, "低风险",
            "FDA OTC与处方双通道批准；长期使用需监测"),
        StandardComplianceMapping(505, StandardType.CE, ComplianceLevel.WARN, "中风险",
            "EMA提示长期使用与低镁血症关联"),
        StandardComplianceMapping(505, StandardType.JIS, ComplianceLevel.PASS, "低风险",
            "PMDA批准为第1类医药品"),

        // ---- 中药 (id 551-555) ----
        // 551 板蓝根颗粒
        StandardComplianceMapping(551, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "收录于《中国药典》一部；含腺苷≥0.03%"),
        StandardComplianceMapping(551, StandardType.FDA, ComplianceLevel.FAIL, "高风险",
            "FDA未批准为药品；分类为膳食补充剂(Dietary Supplement)；禁止宣称疗效"),
        StandardComplianceMapping(551, StandardType.CE, ComplianceLevel.FAIL, "高风险",
            "EMA传统草药注册(THMPD)；需标注'传统用途，未经临床试验验证'"),
        StandardComplianceMapping(551, StandardType.JIS, ComplianceLevel.WARN, "中风险",
            "归类为'健康食品'非药品；不可标注治疗功效"),

        // 552 连花清瘟胶囊
        StandardComplianceMapping(552, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "国药准字Z；含连翘苷≥0.15%、麻黄碱标注"),
        StandardComplianceMapping(552, StandardType.FDA, ComplianceLevel.FAIL, "高风险",
            "FDA未批准上市；麻黄碱成分在美国为管制物质(Controlled Substance)"),
        StandardComplianceMapping(552, StandardType.CE, ComplianceLevel.FAIL, "高风险",
            "未获EMA/THMPD注册；含麻黄成分在欧盟多国禁止"),
        StandardComplianceMapping(552, StandardType.JIS, ComplianceLevel.FAIL, "高风险",
            "麻黄碱在日本为觉醒剂取缔法管控物质"),

        // 553 六味地黄丸
        StandardComplianceMapping(553, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "收录于《中国药典》；六味药材指纹图谱达标"),
        StandardComplianceMapping(553, StandardType.FDA, ComplianceLevel.FAIL, "高风险",
            "归类为膳食补充剂不可宣称疗效；需标注'These statements not evaluated by FDA'"),
        StandardComplianceMapping(553, StandardType.CE, ComplianceLevel.FAIL, "高风险",
            "未在EMA注册；归类为食品补充剂"),
        StandardComplianceMapping(553, StandardType.JIS, ComplianceLevel.WARN, "中风险",
            "归类为汉方·生药制剂；需符合日本药局方汉方标准"),

        // 554 藿香正气水
        StandardComplianceMapping(554, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "国药准字Z；含乙醇40-50%标注'服后不得驾车'"),
        StandardComplianceMapping(554, StandardType.FDA, ComplianceLevel.FAIL, "高风险",
            "含高浓度乙醇(40-50%)；在美国不符合OTC药品标准"),
        StandardComplianceMapping(554, StandardType.CE, ComplianceLevel.FAIL, "高风险",
            "乙醇含量触发EU酒精饮料法规；未被EMA注册为药品"),
        StandardComplianceMapping(554, StandardType.JIS, ComplianceLevel.FAIL, "高风险",
            "乙醇浓度超出日本医药品添加剂标准"),

        // 555 云南白药
        StandardComplianceMapping(555, StandardType.GB, ComplianceLevel.WARN, "中风险",
            "国家保密配方；含草乌(乌头碱)标注'不可过量/长期使用'"),
        StandardComplianceMapping(555, StandardType.FDA, ComplianceLevel.FAIL, "高风险",
            "配方未公开不符合FDA药品注册要求；禁止以药品形式进入美国市场"),
        StandardComplianceMapping(555, StandardType.CE, ComplianceLevel.FAIL, "高风险",
            "未公开成分为EU药品注册硬性障碍"),
        StandardComplianceMapping(555, StandardType.JIS, ComplianceLevel.FAIL, "高风险",
            "乌头属成分在日本受药事法严格限制")
    )

    // ============================================================
    // 保健品合规映射（基于ConsumableRepository id 701-703）
    // ============================================================
    private val supplementCompliance: List<StandardComplianceMapping> = listOf(
        // 701 脑白金
        StandardComplianceMapping(701, StandardType.GB, ComplianceLevel.WARN, "中风险",
            "蓝帽保健食品；主要成分褪黑素；标注'本品不能替代药物'"),
        StandardComplianceMapping(701, StandardType.FDA, ComplianceLevel.WARN, "中风险",
            "褪黑素为膳食补充剂；FDA未评估其有效性声明"),
        StandardComplianceMapping(701, StandardType.CE, ComplianceLevel.WARN, "中风险",
            "褪黑素在多数EU国家为处方药非食品补充剂"),
        StandardComplianceMapping(701, StandardType.JIS, ComplianceLevel.WARN, "中风险",
            "归类为健康食品；褪黑素在日本药品区分明确"),

        // 702 氨基葡萄糖片
        StandardComplianceMapping(702, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "蓝帽保健食品；盐酸氨基葡萄糖≥98%"),
        StandardComplianceMapping(702, StandardType.FDA, ComplianceLevel.WARN, "中风险",
            "归类为膳食补充剂；FDA声明'有限的科学证据支持其关节健康功效'"),
        StandardComplianceMapping(702, StandardType.CE, ComplianceLevel.WARN, "中风险",
            "EMA评定证据不足以作为药品；归类为食品补充剂"),
        StandardComplianceMapping(702, StandardType.JIS, ComplianceLevel.PASS, "低风险",
            "归类为功能性表示食品"),

        // 703 深海鱼油
        StandardComplianceMapping(703, StandardType.GB, ComplianceLevel.PASS, "低风险",
            "蓝帽保健食品；EPA+DHA≥25%；过氧化值合格"),
        StandardComplianceMapping(703, StandardType.FDA, ComplianceLevel.EXCEED, "安全",
            "FDA认定为GRAS(一般公认安全)；FDA批准Lovaza处方级鱼油用于降甘油三酯"),
        StandardComplianceMapping(703, StandardType.CE, ComplianceLevel.PASS, "低风险",
            "符合EU 1881/2006重金属限量；Omega-3健康声称获EFSA批准"),
        StandardComplianceMapping(703, StandardType.JIS, ComplianceLevel.EXCEED, "安全",
            "满足JAS有机水产品加工标准")
    )

    // ============================================================
    // 查询接口
    // ============================================================

    /** 获取指定商品在所有标准下的合规结果 */
    fun getCompliance(itemId: Int): Map<StandardType, StandardComplianceMapping> {
        val all = foodCompliance + medicineCompliance + supplementCompliance
        return all.filter { it.itemId == itemId }
            .associateBy { it.standardType }
    }

    /** 获取指定商品在指定标准下的合规结果 */
    fun getCompliance(itemId: Int, standard: StandardType): StandardComplianceMapping? {
        return getCompliance(itemId)[standard]
    }

    /** 按合规等级分组统计（用于数据看板）*/
    fun getStatsByStandard(standard: StandardType, itemType: String? = null): Map<ComplianceLevel, Int> {
        val source = when (itemType) {
            "food" -> foodCompliance
            "medicine" -> medicineCompliance
            "supplement" -> supplementCompliance
            else -> foodCompliance + medicineCompliance + supplementCompliance
        }
        return source.filter { it.standardType == standard }
            .groupBy { it.complianceLevel }
            .mapValues { it.value.size }
    }

    /** 获取已覆盖的商品ID列表 */
    fun getCoveredItemIds(category: String? = null): Set<Int> {
        val source = when (category) {
            "药品" -> medicineCompliance
            "保健品" -> supplementCompliance
            "生鲜食材", "加工食品" -> foodCompliance
            else -> foodCompliance + medicineCompliance + supplementCompliance
        }
        return source.map { it.itemId }.toSet()
    }
}