package com.example.townapp.data

/**
 * 革新者隐藏角色体系（v2.7 时代变革者长线隐藏支线）
 *
 * 核心设计原则：
 * 1. 基础诞生概率 ≤ 0.3%（千分之三），开局不可选，需数十年层层解锁前置条件
 * 2. 三种硬性前置：认知门槛（跳出圈层）、信念耐久（数十年不崩）、时代窗口（外部条件成熟）
 * 3. 两种隐藏形态：显性落地革新者（推行政策修改全局参数） / 隐性思想革新者（记录理念跨代传承）
 * 4. 中途任何一步妥协 → 永久关闭支线，退回普通职业主线
 * 5. 失败型革新者同样留存档案，为后代提供跨代伏笔
 *
 * 整体闭环：
 *   科研人员 → 改变技术生产力
 *   革新者   → 调整社会分配制度
 *   普通人   → 构成时代基础大众
 *   三者互相作用，组成完整动态演化的小镇世界
 */

// ============================================
// 一、革新者形态枚举
// ============================================

/**
 * 革新者的两种隐藏形态
 */
enum class ReformerForm(val label: String, val description: String) {
    /** 显性落地革新者：抓住时代窗口推行政策改革，修改全局参数 */
    EXPLICIT(
        "显性落地革新者",
        "集齐全部条件，抓住时代窗口，推行渐进式或激进式政策改革，直接修改全局物价、社保、就业、资源分配参数。一代仅寥寥数人。"
    ),
    /** 隐性思想革新者：当下无法落地，记录理念待后代继承 */
    IMPLICIT(
        "隐性思想革新者",
        "受时代条件限制无法推行改革。调研弊病、记录构想、发表观点——当下未改变规则，但思想留存。数十年后新生代借鉴前人理念开启改革。"
    )
}

// ============================================
// 二、解锁前置条件枚举
// ============================================

/**
 * 革新者解锁的三类硬性前置条件
 */
enum class ReformerPrerequisite(val id: String, val label: String, val description: String) {
    /** 条件1：认知门槛——跳出自身圈层局限 */
    COGNITIVE_THRESHOLD(
        "cognitive",
        "认知门槛",
        "富人需深入底层调研打破圈层壁垒，看懂通胀、资本垄断、学阀固化等底层逻辑。底层需跳出匮乏生存焦虑思维，不再单纯优先保全个人利益。"
    ),
    /** 条件2：信念耐久——数十年抵御多重消磨 */
    BELIEF_DURABILITY(
        "belief",
        "信念耐久",
        "持续抵御资本施压、民众非议、日常疲惫失眠、家庭收支压力。中途信念下滑或妥协即失去资格。"
    ),
    /** 条件3：时代窗口——外部条件成熟 */
    ERA_WINDOW(
        "era",
        "时代窗口",
        "产业已发展到一定阶段，社会基础条件具备。缺少时代契机，改革无法落地，仅可作为思想者埋下火种。"
    )
}

// ============================================
// 三、革新行动类型
// ============================================

/**
 * 革新者的改革行动领域
 */
enum class ReformDomain(val label: String, val affectedParameters: String) {
    /** 物价调控：降低垄断产品价格 */
    PRICE_CONTROL("物价调控", "调整消费品价格指数，降低民生刚需产品溢价"),
    /** 就业保障：增加基层岗位，降低失业率 */
    EMPLOYMENT_GUARANTEE("就业保障", "增加基层就业机会，提高低收入群体收入底线"),
    /** 资源再分配：破垄断，重新分配学术/产业资源 */
    RESOURCE_REDISTRIBUTION("资源再分配", "打破学阀专利垄断，重新分配科研经费与设备资源"),
    /** 社会保障：完善底层兜底体系 */
    SOCIAL_SECURITY("社会保障", "建立失业救济、医疗保障、养老兜底机制"),
    /** 教育公平：推动教育机会均等化 */
    EDUCATION_EQUITY("教育公平", "增加基础教育投入，缩小区域教育资源差距")
}

// ============================================
// 四、改革行动数据模型
// ============================================

/**
 * 革新者发起的单次改革行动
 */
data class ReformAction(
    val id: String,
    val domain: ReformDomain,
    /** 改革标题 */
    val title: String,
    /** 改革描述 */
    val description: String,
    /** 改革类型：渐进式 or 激进式 */
    val isGradual: Boolean,
    /** 需要的最低认知参数（认知门槛判断用） */
    val requiredCognition: Int,
    /** 成功概率基础值（受时代窗口、民众支持加成） */
    val baseSuccessRate: Double,
    /** 对现有既得利益群体的冲击度（0-100，越高越容易被抵制） */
    val resistanceLevel: Int,
    /** 全局物价影响（正=降物价/惠民） */
    val priceImpact: Double,
    /** 就业率影响 */
    val employmentImpact: Double,
    /** 民众支持度影响 */
    val publicSupportImpact: Double,
    /** 五段式文案 */
    val bodyFeeling: String,
    val sparkle: String,
    val personalCost: String,
    val eraCost: String,
    val townCommentary: String
)

// ============================================
// 五、革新者运行状态
// ============================================

/**
 * 革新者解锁进度状态
 */
data class ReformerUnlockState(
    /** 是否已是革新者（解锁后为true） */
    val isReformer: Boolean = false,
    /** 革新者形态 */
    val form: ReformerForm? = null,
    /** 认知门槛是否达成 */
    val cognitiveMet: Boolean = false,
    /** 信念耐久是否达标 */
    val beliefDurable: Boolean = false,
    /** 时代窗口是否开启 */
    val eraWindowOpen: Boolean = false,
    /** 是否已永久关闭支线 */
    val branchClosed: Boolean = false,
    /** 关闭原因 */
    val closureReason: String = "",
    /** 已执行的改革行动列表 */
    val completedActions: List<String> = emptyList(),
    /** 改革失败次数 */
    val failedAttempts: Int = 0,
    /** 是否为跨代思想伏笔（隐性革新者） */
    val isLegacySeed: Boolean = false,
    /** 遗留的思想档案文本 */
    val legacyArchive: String = "",
    /** 普通民众对改革的支持度（0-100） */
    val publicSupport: Int = 50,
    /** 既得利益群体的抵制强度（0-100） */
    val resistanceIntensity: Int = 50
) {
    /** 是否已完全达成三个前置条件 */
    val allPrerequisitesMet: Boolean
        get() = cognitiveMet && beliefDurable && eraWindowOpen

    /** 是否处于候选阶段（尚未解锁但未关闭） */
    val isCandidate: Boolean
        get() = !isReformer && !branchClosed
}

// ============================================
// 六、预置改革行动库
// ============================================

object ReformActions {

    val actions: List<ReformAction> = listOf(

        // ---- 物价调控 ----
        ReformAction(
            id = "price_cap_essentials",
            domain = ReformDomain.PRICE_CONTROL,
            title = "民生刚需品限价令",
            description = "对粮油、药品、基础能源等民生刚需品设定价格上限，打破资本垄断定价。",
            isGradual = true,
            requiredCognition = 60,
            baseSuccessRate = 0.45,
            resistanceLevel = 75,
            priceImpact = -0.15,
            employmentImpact = 0.02,
            publicSupportImpact = 20.0,
            bodyFeeling = "你在文件上签下名字的那一刻，手是抖的。你知道一出这道门，资本方的电话就会打爆你的座机。但你想起菜市场里那个数着硬币买菜的老人——你的笔落了下去。",
            sparkle = "限价令不是完美的方案，但它挡住了最迫在眉睫的生存压力。在你无法动整个系统之前，先让最底层的人喘一口气——这是一种务实的悲悯。",
            personalCost = "数周谈判、深夜修改条款、应对多方施压——额外消耗约200小时工时。个人安全受到威胁，出门需要谨慎。",
            eraCost = "短期：必需消费品价格下降约15%，底层居民月支出压力减轻。中期：资本方利润缩水，可能减少该领域投资。长期：若配套政策未跟上，可能引发供应短缺。",
            townCommentary = "一项限价令背后，是你和一群看不见的对手之间的博弈。你赢了这一局——但你知道，这只是开始。真正的问题不在价格，在分配。"
        ),
        ReformAction(
            id = "patent_compulsory_license",
            domain = ReformDomain.PRICE_CONTROL,
            title = "垄断专利强制许可",
            description = "对长期垄断、定价过高的核心专利实施强制许可，允许多家企业生产，打破技术垄断壁垒。",
            isGradual = false,
            requiredCognition = 75,
            baseSuccessRate = 0.30,
            resistanceLevel = 90,
            priceImpact = -0.25,
            employmentImpact = 0.05,
            publicSupportImpact = 25.0,
            bodyFeeling = "你触碰了最不该碰的东西——资本的核心利益。强制许可的文件递出去那一刻，你知道自己已经没有回头路了。这一仗要么赢，要么你的一切都会被反噬。",
            sparkle = "专利本应是激励创新的工具，但当它沦为垄断的武器时，你选择把它还原回工具。不破不立——这是一场你明知胜算不高却必须打的仗。",
            personalCost = "高强度对抗消耗数月，个人声誉面临污名化攻击，日均工作16小时以上。身心俱疲，但内心始终有一个声音在说：不能退。",
            eraCost = "短期：专利产品价格下降约25%，普通家庭购买力释放。资本方强烈抵制，可能缩减研发投入。长期：倒逼产业从垄断竞争走向技术竞争，培育新的创新生态。",
            townCommentary = "你动了既得利益者的蛋糕——这比改革本身更危险。但你算过一笔账：底层居民因为专利垄断多付的每一分钱，都是在为别人的特权买单。你不是在打破专利，你是在打破这种不对等。"
        ),

        // ---- 就业保障 ----
        ReformAction(
            id = "employment_safety_net",
            domain = ReformDomain.EMPLOYMENT_GUARANTEE,
            title = "基层就业安全网",
            description = "设立公共就业岗位池，为失业者提供过渡性就业机会，确保基本生存底线不被击穿。",
            isGradual = true,
            requiredCognition = 55,
            baseSuccessRate = 0.55,
            resistanceLevel = 50,
            priceImpact = 0.0,
            employmentImpact = 0.10,
            publicSupportImpact = 30.0,
            bodyFeeling = "你见到过太多被时代抛弃的人——工厂关闭后的老工人、被AI替代的文员。你无法阻止时代前进，但你可以确保没有人被落下时摔得太重。",
            sparkle = "就业安全网不是施舍——是给每一个被意外击倒的人一个重新站起来的机会。这份制度设计的核心不是仁慈，是尊严。",
            personalCost = "政策设计与落地耗时数月，需要协调多个部门、企业、社区——奔波之中数次因过劳生病。",
            eraCost = "短期：财政支出增加，但底层消费能力回升，经济循环加速。长期：降低社会不稳定因素，减少极端贫困导致的治安与健康成本。",
            townCommentary = "一个社会的底线，看它怎么对待最弱势的那群人。你用一张安全网，兜住了那些本该被筛掉的人——这不是慈悲，是公平。"
        ),

        // ---- 资源再分配 ----
        ReformAction(
            id = "academic_budget_reform",
            domain = ReformDomain.RESOURCE_REDISTRIBUTION,
            title = "科研经费去学阀化改革",
            description = "打破学阀圈层对科研经费的垄断分配，建立公开评审机制，允许体制外科研团队公平竞争。",
            isGradual = true,
            requiredCognition = 80,
            baseSuccessRate = 0.35,
            resistanceLevel = 85,
            priceImpact = 0.0,
            employmentImpact = 0.03,
            publicSupportImpact = 10.0,
            bodyFeeling = "你坐进会议室，对面是清一色的资深教授——他们中的大多数，一生都在这个体系里如鱼得水。你说出改革方案的时候，空气凝固了整整三秒。你知道——你触碰了学术圈最敏感的神经。",
            sparkle = "真正的好研究不该困在人脉里。你打开了一扇门——那些没有背景但有才华的人，终于有机会被看见了。",
            personalCost = "学术圈层的联合抵制让你举步维艰。数月内论文遭拒、合作中断、经费冻结——你在用自己的职业生涯为改革买单。",
            eraCost = "短期：学术圈层强烈震荡，部分资深学者可能外流。长期：科研活力释放，非主流方向得到支持，创新多样性提升，跨代形成正向循环。",
            townCommentary = "学阀不是某一个坏人——是一套被时间固化的规则。你对抗的不是人，是规则本身。这意味着你不可能靠打败一个人来赢——你必须换一套规则。这条路比任何改革都更难走，但你没有绕开。"
        ),

        // ---- 社会保障 ----
        ReformAction(
            id = "universal_basic_healthcare",
            domain = ReformDomain.SOCIAL_SECURITY,
            title = "全民基础医疗保障",
            description = "建立覆盖全人群的基础医疗保障体系，确保底层民众不会因病返贫。",
            isGradual = true,
            requiredCognition = 65,
            baseSuccessRate = 0.50,
            resistanceLevel = 60,
            priceImpact = -0.05,
            employmentImpact = 0.02,
            publicSupportImpact = 35.0,
            bodyFeeling = "你见过太多这样的故事：一个家庭因为一次大病，从勉强温饱跌入了深渊。你算了一下——建立一个兜底的医疗保障，成本远低于社会为因病致贫付出的后续代价。",
            sparkle = "你无法保证每个人都能享受最好的医疗——但你至少能保证，不会有人因为看不起病而等死。这是底线，不是施舍。",
            personalCost = "方案设计与资金测算耗时数月，需要平衡财政可持续性与覆盖面。长期熬夜加班，身体出现预警信号。",
            eraCost = "短期：财政压力增加，但底层因病致贫率大幅下降。长期：全民健康水平提升，劳动力质量改善，间接降低社会总成本。",
            townCommentary = "疾病面前人人平等——但治病面前从来不是。你做的不是让富人少花钱，是让穷人有命花钱。这不只是医疗改革——这是对'人命不等价'这句话的宣战。"
        ),

        // ---- 教育公平 ----
        ReformAction(
            id = "education_resource_balance",
            domain = ReformDomain.EDUCATION_EQUITY,
            title = "区域教育资源均等化",
            description = "缩小城区与县乡教育经费差距，推动优质师资跨区域流动，增加底层学子升学通道。",
            isGradual = true,
            requiredCognition = 70,
            baseSuccessRate = 0.40,
            resistanceLevel = 65,
            priceImpact = 0.0,
            employmentImpact = 0.05,
            publicSupportImpact = 28.0,
            bodyFeeling = "你自己走过那条路——从县城考出来，才知道城里孩子从小拥有的东西，你到二十岁才第一次见到。教育的不公平是一种代际的不公平——你不想让下一代再走一遍。",
            sparkle = "教育公平不是让所有人都考满分——是让一个有天赋的农村孩子，不会因为学校没有实验室而放弃科研的梦想。你提供的是机会，不是结果。",
            personalCost = "协调各区域利益格局极度消耗精力，你需要平衡既得利益者与改革受益者的冲突，反复谈判妥协之间，工时不计其数。",
            eraCost = "短期：优质学区家庭可能不满，部分教育资源被迫重新分配。长期：底层人力资本提升，社会流动性增强，数十年后形成更加多元的人才结构。",
            townCommentary = "教育资源垄断是最隐蔽的特权——因为被剥夺的人甚至不知道他们被剥夺了什么。你做的不是让所有学校变成名校——是让每一所学校都不再是牢笼。"
        )
    )

    /** 根据领域筛选改革行动 */
    fun byDomain(domain: ReformDomain): List<ReformAction> =
        actions.filter { it.domain == domain }

    /** 根据认知门槛筛选可行的改革 */
    fun feasibleActions(cognitionLevel: Int): List<ReformAction> =
        actions.filter { it.requiredCognition <= cognitionLevel }
}

// ============================================
// 七、改革结果
// ============================================

/**
 * 单次改革的执行结果
 */
data class ReformResult(
    val action: ReformAction,
    val success: Boolean,
    /** 实际成功概率（含时代窗口、民众支持加成） */
    val actualSuccessRate: Double,
    /** 具体结果描述 */
    val resultDescription: String,
    /** 对改革后续的影响描述 */
    val aftermathDescription: String,
    /** 是否导致支线关闭 */
    val causesBranchClosure: Boolean = false
)