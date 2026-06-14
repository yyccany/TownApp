package com.example.townapp.data

/**
 * 科研与技术流通体系（v2.2 学术垄断与圈层固化）
 *
 * ============================================
 * 核心设计原则
 * ============================================
 * 1. 实事求是还原出身资源差距：寒门科研概率4%（百里挑四），基础科研≤1%
 * 2. 学阀不是反派：知识垄断是学术圈层长期发展形成的环境问题，分资源垄断与专利垄断
 * 3. 圈层人员人格平等：手握资源红利，同时受制于派系规则约束
 * 4. 努力存在天花板：个人奋斗会受圈层、资本、原生家境约束
 *
 * ============================================
 * 体系结构
 * ============================================
 * 出身：中产富裕（96%）/ 县城寒门（4%，其中基础科研≤1%）
 * 学术派系：加入学阀 / 独立游离 / 企业合作
 * 博士毕业后三分支：基础科研 / 应用研发 / 技术商业化
 * 知识垄断：资源垄断（学阀）/ 专利垄断（资本）
 * 全局联动：垄断 → 物价/创新速度/行业迭代
 * ============================================
 */

// ============================================
// 一、科研出身类型（概率已修正）
// ============================================

/**
 * 科研者家庭出身
 */
enum class ResearcherOrigin(val label: String, val probability: Double, val description: String) {
    /** 中产-富裕家庭：96%概率。教育资源充足，家庭兜底，试错空间大。 */
    AFFLUENT_MIDDLE(
        "城市中产/富裕家庭",
        0.96,
        "教育资源充足，读研读博期间无经济压力，家庭兜底房租与生活开支。可以不计短期收入得失，深耕长线基础研究。"
    ),
    /** 县城寒门：4%概率（百里挑四）。天赋+自律+奖学金，约束严苛。 */
    COUNTY_UNDERPRIVILEGED(
        "县城寒门",
        0.04,
        "中小学优质师资稀缺，缺少课外实验资源。依靠奖学金、助学贷款走完求学道路。硕士博士期间补贴微薄，背负家庭经济期待，生存压力极大。属于幸存者式极少数情况——一百人里仅有四人走到这一步。"
    )
}

/**
 * 科研出身配置
 */
data class ResearcherOriginConfig(
    val origin: ResearcherOrigin,
    /** 初始负债（寒门更高，学贷/家庭借款） */
    val initialDebt: Double,
    /** 月度家庭经济压力（0-100，越高越焦虑） */
    val monthlyFinancialStress: Int,
    /** 求学阶段额外打工工时 */
    val extraWorkHoursForStudy: Double,
    /** 家庭是否可兜底长期无收入 */
    val familySafetyNet: Boolean,
    /** 开局描述 */
    val openingDescription: String
)

object ResearcherOriginConfigs {
    val configs: Map<ResearcherOrigin, ResearcherOriginConfig> = mapOf(
        ResearcherOrigin.AFFLUENT_MIDDLE to ResearcherOriginConfig(
            origin = ResearcherOrigin.AFFLUENT_MIDDLE,
            initialDebt = 0.0,
            monthlyFinancialStress = 10,
            extraWorkHoursForStudy = 0.0,
            familySafetyNet = true,
            openingDescription = "你出生在一个城市中产家庭。从小有最好的教育资源，读研读博期间家里承担了房租和生活费。你不需要为生计发愁——可以全身心投入研究。这份运气不是所有人都有的。96%的科研者和你一样，出身已经为你铺好了路。"
        ),
        ResearcherOrigin.COUNTY_UNDERPRIVILEGED to ResearcherOriginConfig(
            origin = ResearcherOrigin.COUNTY_UNDERPRIVILEGED,
            initialDebt = 80000.0,
            monthlyFinancialStress = 80,
            extraWorkHoursForStudy = 1400.0,
            familySafetyNet = false,
            openingDescription = "你出生在县城一个普通家庭。在一百个和你同样出身的人里，只有四个人走到了科研岗位。你靠着奖学金和助学贷款一路读到博士，读研期间周末做家教、寒暑假打工。你知道自己是从概率极低的缝隙里钻出来的——这份自觉，让你比任何人都清楚普通人的不容易。"
        )
    )
}

// ============================================
// 二、学术派系（圈层归属）
// ============================================

/**
 * 学术派系归属
 */
enum class AcademicFaction(val label: String, val description: String) {
    /** 加入学阀派系：资源充足，但自主选题受限 */
    INSIDER(
        "学阀派系内部",
        "依托家族前辈人脉进入核心实验室，经费充足，设备一流。但研究方向需遵从派系统一规划，个人自主选题空间受限。省去争取资源的大量内耗，同时被圈层规则约束。"
    ),
    /** 独立游离在外：自主自由，但资源匮乏 */
    INDEPENDENT(
        "独立游离在外",
        "不加入任何派系，研究方向完全自主。但经费紧张，论文发表渠道狭窄，缺少学术引荐人脉。每一份资源都要靠自己争取，学术成果的传播速度受限。"
    ),
    /** 与企业合作：资金充足，但方向受资本引导 */
    CORPORATE(
        "企业合作研发",
        "和外部企业签订合作协议，经费充足，设备齐全。但研究方向受资本方引导，优先追逐短期盈利项目，长期基础研究空间被压缩。"
    )
}

/**
 * 学术派系状态
 */
data class AcademicFactionState(
    val faction: AcademicFaction = AcademicFaction.INDEPENDENT,
    /** 派系内资源等级（0-100，INSIDER 最高，INDEPENDENT 最低） */
    val resourceLevel: Int = 30,
    /** 自主选题自由度（0-100，INDEPENDENT 最高，INSIDER 最低） */
    val autonomyLevel: Int = 70,
    /** 论文发表渠道通畅度（0-100） */
    val publicationAccess: Int = 30,
    /** 在派系内节省的资源争取工时 */
    val savedResourceHours: Double = 0.0
)

// ============================================
// 三、知识垄断体系
// ============================================

/**
 * 知识垄断类型
 */
enum class AcademicMonopolyType(val label: String) {
    /** 学术资源垄断（学阀）：控制实验室经费、设备、期刊、项目名额 */
    RESOURCE_MONOPOLY("学术资源垄断"),
    /** 专利商业垄断（资本）：买断核心专利，控制授权渠道，抬高售价 */
    PATENT_MONOPOLY("专利商业垄断")
}

/**
 * 知识垄断对全局的影响
 */
data class AcademicMonopolyImpact(
    val type: AcademicMonopolyType,
    val description: String,
    /** 对物价的影响（正=涨价） */
    val priceImpactPercent: Double,
    /** 对创新速度的影响（正=加速，负=放缓） */
    val innovationSpeedDelta: Double,
    /** 受影响群体 */
    val affectedGroup: String,
    /** 长期后果 */
    val longTermConsequence: String
)

object AcademicMonopolyImpacts {
    val impacts: Map<AcademicMonopolyType, AcademicMonopolyImpact> = mapOf(
        AcademicMonopolyType.RESOURCE_MONOPOLY to AcademicMonopolyImpact(
            type = AcademicMonopolyType.RESOURCE_MONOPOLY,
            description = "学阀圈层把控实验室经费、设备、期刊发表渠道与项目立项名额。外部人才无法进入核心项目，论文长期无法登载权威期刊。",
            priceImpactPercent = 0.0,
            innovationSpeedDelta = -0.3,
            affectedGroup = "寒门科研者、独立研究者、民间技术人才",
            longTermConsequence = "短期压制外部创新速度，新兴技术迭代节奏放缓。长期积压大量未落地的民间技术成果，积蓄行业变革的潜在变量。"
        ),
        AcademicMonopolyType.PATENT_MONOPOLY to AcademicMonopolyImpact(
            type = AcademicMonopolyType.PATENT_MONOPOLY,
            description = "资深科研团队与大型企业买断前沿核心专利，控制技术授权渠道，划定区域销售壁垒，人为抬高新产品售价。",
            priceImpactPercent = 15.0,
            innovationSpeedDelta = -0.1,
            affectedGroup = "普通消费者、底层居民、中小企业",
            longTermConsequence = "消费品物价上浮，局部通胀抬升，普通民众生活开支增加。节能家电等刚需新产品被垄断后，底层居民难以享受技术红利。"
        )
    )
}

// ============================================
// 四、科研职业分支（博士毕业后可选）
// ============================================

enum class ResearchCareerBranch(
    val label: String,
    val description: String,
    val cycleYears: IntRange,
    val monthlyIncomeRange: ClosedRange<Double>,
    val monthlyWorkHours: Double,
    val accessibleToPoor: Boolean
) {
    BASIC_RESEARCH(
        "基础理论科研",
        "从零开展实验攻关，产出全新专利、新技术。周期极长，寒门出身概率≤1%。",
        8..12,
        4000.0..8000.0,
        220.0,
        accessibleToPoor = false
    ),
    APPLIED_RD(
        "应用型技术研发",
        "面向具体产品的技术开发，3-5年见效。寒门科研者的主要赛道。",
        3..6,
        6000.0..15000.0,
        200.0,
        accessibleToPoor = true
    ),
    TECH_BROKER(
        "技术经纪人（合规）",
        "合法收购成熟专利，对接工厂投产，打通科研成果市场化落地渠道。",
        1..2,
        10000.0..30000.0,
        180.0,
        accessibleToPoor = true
    ),
    TECH_SPECULATOR(
        "技术套利投机者",
        "窃取未保护的实验成果、仿制专利技术，或买断专利后垄断市场抬高售价。",
        0..1,
        20000.0..80000.0,
        160.0,
        accessibleToPoor = true
    )
}

// ============================================
// 五、全局技术影响
// ============================================

data class TechGlobalImpact(
    val techName: String,
    val description: String,
    val priceImpactPercent: Double,
    val newJobsCreated: Int,
    val oldJobsLost: Int,
    val salaryCapIncrease: Double,
    val triggersInflation: Boolean,
    val impactOnWorkingClass: String
)

object TechGlobalImpacts {
    val impacts: List<TechGlobalImpact> = listOf(
        TechGlobalImpact(
            "自动化流水线", "工业生产效率大幅提升，产品制造成本下降",
            -15.0, 5000, 20000, 500.0, false,
            "流水线工人大规模失业，需要转型学习新技能。短期阵痛明显，长期运维岗位薪资更高。"
        ),
        TechGlobalImpact(
            "家用节能设备", "家庭电费水费支出下降，长期降低生活成本",
            -8.0, 3000, 1000, 200.0, false,
            "普通家庭每月水电开支减少，节省的钱可用于其他消费。对底层人群是正向改善。"
        ),
        TechGlobalImpact(
            "新材料突破", "替代昂贵进口材料，本土制造业成本降低",
            -20.0, 8000, 3000, 800.0, false,
            "原材料降价带动下游产品降价，日常消费品价格下降。但原材料行业工人面临转型。"
        ),
        TechGlobalImpact(
            "垄断型技术产品", "区域市场被垄断，产品售价远高于合理水平",
            25.0, 500, 0, 0.0, true,
            "刚需消费品价格上涨，底层人群储蓄购买力缩水。垄断区域内的普通家庭开支增加。"
        )
    )
    fun findByTechName(name: String): TechGlobalImpact? = impacts.find { it.techName == name }
}

// ============================================
// 六、科研者随机事件（含新增学阀/垄断事件）
// ============================================

data class ResearchLifeEvent(
    val id: String,
    val eventName: String,
    val requiredOrigin: ResearcherOrigin? = null,
    val requiredBranch: ResearchCareerBranch? = null,
    val requiredFaction: AcademicFaction? = null,
    val description: String,
    val forcesChoice: Boolean,
    val choices: List<String> = emptyList()
)

object ResearchLifeEvents {

    val events: List<ResearchLifeEvent> = listOf(
        // ===== 寒门专属事件 =====
        ResearchLifeEvent(
            id = "poor_family_pressure",
            eventName = "家里催你就业",
            requiredOrigin = ResearcherOrigin.COUNTY_UNDERPRIVILEGED,
            description = "家里打来电话，说弟弟妹妹的学费又涨了，问你什么时候能正式工作赚钱。你知道父母不是催你——他们只是撑不住了。",
            forcesChoice = true,
            choices = listOf(
                "坚持科研，申请延期补助，自己再省一点",
                "转做应用型研发，缩短周期尽快产出",
                "放弃科研，找一份高薪工作先撑起家里"
            )
        ),
        ResearchLifeEvent(
            id = "poor_family_emergency",
            eventName = "老家亲人突发疾病",
            requiredOrigin = ResearcherOrigin.COUNTY_UNDERPRIVILEGED,
            description = "家里突然来电话，说父亲住院了。手术费需要两万块，家里凑不出来。你看着银行卡里仅剩的三千块科研备用金，手在发抖。",
            forcesChoice = true,
            choices = listOf(
                "拿出全部积蓄寄回家，暂停科研去打工",
                "向导师借钱应急，继续科研",
                "申请紧急科研补助，争取宽限时间"
            )
        ),
        ResearchLifeEvent(
            id = "poor_scholarship_deadline",
            eventName = "奖学金评估截止",
            requiredOrigin = ResearcherOrigin.COUNTY_UNDERPRIVILEGED,
            description = "今年的奖学金评估结果出来了——你拿到了，但金额比去年少了。你要么去兼职补贴，要么申请助学贷款。两份选择都不轻松。",
            forcesChoice = true,
            choices = listOf(
                "周末兼职，压缩科研时间",
                "申请助学贷款，先撑过这个阶段"
            )
        ),

        // ===== 富裕专属事件 =====
        ResearchLifeEvent(
            id = "rich_family_intervention",
            eventName = "家族资本介入项目",
            requiredOrigin = ResearcherOrigin.AFFLUENT_MIDDLE,
            description = "家族里的长辈找到你，说有个投资方对你的研究方向很感兴趣，但要求你调整研究重点，优先做能短期盈利的方向。",
            forcesChoice = true,
            choices = listOf(
                "拒绝资本介入，坚持原定方向",
                "接受投资，调整部分研究重点",
                "折中：保留基础方向，同时开一个应用课题"
            )
        ),

        // ===== 学阀派系事件 =====
        ResearchLifeEvent(
            id = "faction_join_offer",
            eventName = "学阀派系向你伸出橄榄枝",
            requiredFaction = AcademicFaction.INDEPENDENT,
            description = "你导师的导师——一位学阀圈层的核心人物，注意到了你的研究。他邀请你加入他的实验室团队，经费充足，设备一流。但你也听说，进了他的团队，研究方向由他定，论文署名也要按派系规则来。",
            forcesChoice = true,
            choices = listOf(
                "加入派系，获得充足资源",
                "婉拒邀请，继续独立研究",
                "暂时挂名合作，保留自主权"
            )
        ),
        ResearchLifeEvent(
            id = "faction_insider_resource",
            eventName = "实验室经费分配",
            requiredFaction = AcademicFaction.INSIDER,
            description = "实验室年度经费分配下来了。你是派系内的人，拿到了充足的预算。但同时你看到隔壁独立实验室的同事，因为没有经费，实验设备坏了三个月都没修好。你心里有些复杂。",
            forcesChoice = false,
            choices = listOf("继续自己的研究——这是你应得的", "试着帮隔壁申请一些剩余经费", "什么也不说，但开始反思这套体系")
        ),

        // ===== 独立研究者事件 =====
        ResearchLifeEvent(
            id = "independent_paper_rejected",
            eventName = "论文再次被拒",
            requiredFaction = AcademicFaction.INDEPENDENT,
            description = "你的论文又被权威期刊退回来了。审稿意见模棱两可，但你隐约觉得——不是因为论文不好，是因为你没有大佬的推荐信。独立研究者的路，处处都是隐形门槛。",
            forcesChoice = true,
            choices = listOf(
                "继续修改论文，再投一次",
                "换一个不那么权威的期刊发表",
                "开始考虑是不是该加入某个派系"
            )
        ),
        ResearchLifeEvent(
            id = "independent_budget_crisis",
            eventName = "实验室经费告急",
            requiredFaction = AcademicFaction.INDEPENDENT,
            description = "实验室的经费只剩最后一个月了。设备坏了买不起新的，试剂耗材快用完了。你对着账本看了很久——如果这个月还拿不到新项目，实验就要停了。",
            forcesChoice = true,
            choices = listOf(
                "自掏腰包先撑一阵子",
                "寻找企业合作，接受部分方向限制",
                "申请加入学阀派系，放弃部分自主权"
            )
        ),

        // ===== 企业合作事件 =====
        ResearchLifeEvent(
            id = "corporate_direction_conflict",
            eventName = "企业要求调整研究方向",
            requiredFaction = AcademicFaction.CORPORATE,
            description = "合作企业发来邮件，说你的基础研究投入太大、见效太慢，要求你把精力转向能快速变现的应用项目。你手里的基础课题还没做完——但企业的耐心已经耗尽了。",
            forcesChoice = true,
            choices = listOf(
                "妥协，先做应用项目保住合作",
                "坚持基础方向，自己挤出时间继续",
                "终止合作，回到独立研究"
            )
        ),

        // ===== 专利垄断事件 =====
        ResearchLifeEvent(
            id = "patent_buyout_offer",
            eventName = "企业出价收购你的专利",
            requiredBranch = ResearchCareerBranch.APPLIED_RD,
            description = "一家大型企业看中了你的技术专利，出价很高——够你十年不用为钱发愁。但合同里有一条：一旦收购，你无权过问技术定价和销售区域。你预感到，他们可能会垄断市场、抬高售价。",
            forcesChoice = true,
            choices = listOf(
                "接受收购，拿了钱不管后续",
                "拒绝，寻找合规的技术经纪人合作",
                "谈判：保留部分定价参与权，降低收购价"
            )
        ),

        // ===== 通用事件 =====
        ResearchLifeEvent(
            id = "experiment_failure",
            eventName = "实验第七次失败",
            requiredBranch = ResearchCareerBranch.BASIC_RESEARCH,
            description = "实验又失败了。这是第七次了。你坐在实验室里，看着一堆数据，不知道是方向错了还是自己不够聪明。",
            forcesChoice = false,
            choices = listOf("再试一次", "换个方向", "休息几天再回来")
        ),
        ResearchLifeEvent(
            id = "speculator_ethics_check",
            eventName = "良心叩问",
            requiredBranch = ResearchCareerBranch.TECH_SPECULATOR,
            description = "你垄断了本地某种医疗耗材的供应渠道，价格翻了一倍。今天路过医院，看到一个老人因为买不起药在门口徘徊。你认出了那个药——是你手里的那批货。",
            forcesChoice = true,
            choices = listOf(
                "降价让利，放一部分平价货源",
                "继续维持高价，这是生意",
                "捐一批货给医院，但要匿名"
            )
        )
    )

    fun getEventsFor(
        origin: ResearcherOrigin?,
        branch: ResearchCareerBranch?,
        faction: AcademicFaction? = null
    ): List<ResearchLifeEvent> {
        return events.filter { event ->
            (event.requiredOrigin == null || event.requiredOrigin == origin) &&
            (event.requiredBranch == null || event.requiredBranch == branch) &&
            (event.requiredFaction == null || event.requiredFaction == faction)
        }
    }
}

// ============================================
// 七、科研生涯状态
// ============================================

data class ResearchCareerState(
    val origin: ResearcherOrigin = ResearcherOrigin.AFFLUENT_MIDDLE,
    val branch: ResearchCareerBranch = ResearchCareerBranch.BASIC_RESEARCH,
    /** 学术派系归属 */
    val faction: AcademicFaction = AcademicFaction.INDEPENDENT,
    val factionState: AcademicFactionState = AcademicFactionState(),
    val totalResearchHours: Double = 0.0,
    val researchProgress: Int = 0,
    val completedTechs: Int = 0,
    val hasConsideredQuitting: Boolean = false,
    val financialStress: Int = 0,
    val labMonthlyIncome: Double = 0.0,
    /** 是否触发过学术壁垒事件 */
    val hasEncounteredResourceBarrier: Boolean = false,
    /** 是否触发过专利垄断事件 */
    val hasEncounteredPatentMonopoly: Boolean = false
) {
    val originConfig: ResearcherOriginConfig
        get() = ResearcherOriginConfigs.configs[origin]!!

    val estimatedRemainingMonths: Int
        get() {
            val totalCycle = branch.cycleYears.last * 12
            val progressPerHour = 100.0 / totalCycle / branch.monthlyWorkHours
            val remaining = (100 - researchProgress) / progressPerHour / branch.monthlyWorkHours
            return remaining.toInt().coerceAtLeast(0)
        }

    /** 寒门基础科研实际概率（≤1%） */
    val isPoorBasicResearch: Boolean
        get() = origin == ResearcherOrigin.COUNTY_UNDERPRIVILEGED
                && branch == ResearchCareerBranch.BASIC_RESEARCH
}