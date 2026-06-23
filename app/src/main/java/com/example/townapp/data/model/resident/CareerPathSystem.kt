package com.example.townapp.data

/**
 * 职业多路径手动选择体系
 *
 * 四大路径，每条对应薪资、加班强度、晋升上限、疲惫增长速度。
 * 操作节点：毕业、离职裁员、跳槽窗口期。
 * 系统基于原生家境、过往技能点数给出推荐倾向，玩家无视推荐自由选定。
 */
object CareerPathSystem {

    /** 职业路线类型 */
    enum class CareerPathType(val label: String, val brief: String) {
        STABLE("稳定体制", "公务员、国企岗位，薪资平稳，加班偏少"),
        CORPORATE("大厂内卷", "互联网、企业白领，起薪高，加班频次高"),
        FREELANCE("自由副业", "前期收入不稳定，依靠自学技能接单"),
        BUSINESS("个体经商", "创业经营，风险偏高，盈利后财富增速快")
    }

    /** 具体职业岗位 */
    data class CareerOption(
        val id: String,
        val name: String,
        val pathType: CareerPathType,
        val baseSalary: Double,          // 基础月薪
        val salaryGrowth: Double,        // 年薪资增长率
        val salaryCeiling: Double,       // 薪资天花板
        val overtimeRate: Double,        // 加班频率（0-1，越高越频繁）
        val fatiguePerDay: Double,       // 每日疲惫增速
        val promotionSpeed: Double,      // 晋升速度（0-1）
        val layoffRisk: Double,          // 中年裁员风险（0-1）
        val startupCost: Double = 0.0,   // 启动成本（创业需要）
        val bankruptcyRisk: Double = 0.0,// 破产风险（创业需要）
        val minAge: Int = 22,            // 最低入职年龄
        
        // 双轨经济参数
        val hourlyWage: Double = 0.0,    // 时薪（劳动收入：工作分钟 × 时薪）
        val compoundRate: Double = 0.0,  // 复利系数（资产收入：资产总额 × 复利系数，睡眠自动结算）
        val assetGrowthRate: Double = 0.0,// 资产增长率（经营型职业资产增值速度）
        val dailyWorkRequirement: Int = 0,// 每日最低工作分钟要求
        
        val description: String
    )

    // ============================================
    // 职业库
    // ============================================

    val allCareers: List<CareerOption> = listOf(
        // ── 稳定体制路线 ──（纯劳动型，时薪稳定，无复利）
        CareerOption("civil_servant", "基层公务员", CareerPathType.STABLE,
            5000.0, 0.03, 12000.0, 0.2, 0.1, 0.3, 0.05,
            minAge = 22,
            hourlyWage = 30.0, compoundRate = 0.0, assetGrowthRate = 0.0,
            dailyWorkRequirement = 420,
            description = "朝九晚五，薪资稳定，失业风险低"),

        CareerOption("state_enterprise", "国企职员", CareerPathType.STABLE,
            6000.0, 0.04, 15000.0, 0.25, 0.15, 0.35, 0.08,
            minAge = 22,
            hourlyWage = 35.0, compoundRate = 0.0, assetGrowthRate = 0.0,
            dailyWorkRequirement = 450,
            description = "福利好，加班少，薪资稳定"),

        CareerOption("teacher", "公立教师", CareerPathType.STABLE,
            4500.0, 0.03, 10000.0, 0.3, 0.2, 0.25, 0.05,
            minAge = 22,
            hourlyWage = 27.0, compoundRate = 0.0, assetGrowthRate = 0.0,
            dailyWorkRequirement = 480,
            description = "寒暑假福利，压力适中"),

        // ── 大厂内卷路线 ──（劳动型为主，后期可积累技能资产）
        CareerOption("programmer", "程序员", CareerPathType.CORPORATE,
            12000.0, 0.08, 50000.0, 0.7, 0.4, 0.6, 0.35,
            minAge = 22,
            hourlyWage = 72.0, compoundRate = 0.0001, assetGrowthRate = 0.001,
            dailyWorkRequirement = 600,
            description = "起薪高、涨薪快，但996是常态。35岁后裁员风险显著上升。"),

        CareerOption("product_manager", "产品经理", CareerPathType.CORPORATE,
            10000.0, 0.07, 40000.0, 0.6, 0.35, 0.55, 0.30,
            minAge = 24,
            hourlyWage = 60.0, compoundRate = 0.0001, assetGrowthRate = 0.0008,
            dailyWorkRequirement = 540,
            description = "互联网核心岗位，沟通协调多，加班也不少。"),

        CareerOption("finance_analyst", "金融分析师", CareerPathType.CORPORATE,
            15000.0, 0.09, 60000.0, 0.8, 0.5, 0.65, 0.25,
            minAge = 24,
            hourlyWage = 90.0, compoundRate = 0.0002, assetGrowthRate = 0.0015,
            dailyWorkRequirement = 660,
            description = "金领起步，但工作强度极大，疲惫值飙升。"),

        // ── 自由副业路线 ──（劳动型为主，技能积累后可转化）
        CareerOption("freelance_dev", "自由开发者", CareerPathType.FREELANCE,
            4000.0, 0.12, 80000.0, 0.3, 0.2, 0.0, 0.0,
            minAge = 22,
            hourlyWage = 50.0, compoundRate = 0.0001, assetGrowthRate = 0.001,
            dailyWorkRequirement = 0,
            description = "前期收入不稳定，靠接单为生。深耕数年后收入上限极高，自己掌控时间。"),

        CareerOption("content_creator", "内容创作者", CareerPathType.FREELANCE,
            3000.0, 0.15, 100000.0, 0.4, 0.25, 0.0, 0.0,
            minAge = 22,
            hourlyWage = 40.0, compoundRate = 0.0003, assetGrowthRate = 0.002,
            dailyWorkRequirement = 0,
            description = "自媒体、视频创作，前期收入微薄，一旦做起来天花板极高。"),

        CareerOption("designer", "独立设计师", CareerPathType.FREELANCE,
            3500.0, 0.10, 50000.0, 0.35, 0.2, 0.0, 0.0,
            minAge = 22,
            hourlyWage = 45.0, compoundRate = 0.0001, assetGrowthRate = 0.0008,
            dailyWorkRequirement = 0,
            description = "接设计单为生，收入波动大但自由度高。"),

        // ── 个体经商路线 ──（资产型为主，复利收入，前期投入大）
        CareerOption("small_shop", "开小店铺", CareerPathType.BUSINESS,
            3000.0, 0.20, 100000.0, 0.6, 0.4, 0.0, 0.0,
            startupCost = 50000.0, bankruptcyRisk = 0.30,
            minAge = 24,
            hourlyWage = 20.0, compoundRate = 0.0008, assetGrowthRate = 0.005,
            dailyWorkRequirement = 540,
            description = "从一家小店开始，风险不小但回报可观。需要投入本金。"),

        CareerOption("restaurant", "餐饮创业", CareerPathType.BUSINESS,
            2000.0, 0.25, 150000.0, 0.7, 0.5, 0.0, 0.0,
            startupCost = 80000.0, bankruptcyRisk = 0.40,
            minAge = 26,
            hourlyWage = 15.0, compoundRate = 0.001, assetGrowthRate = 0.006,
            dailyWorkRequirement = 600,
            description = "餐饮行业门槛低但淘汰率高，成了就是一条街最火的店。"),

        CareerOption("ecommerce", "电商创业", CareerPathType.BUSINESS,
            4000.0, 0.30, 200000.0, 0.5, 0.35, 0.0, 0.0,
            startupCost = 30000.0, bankruptcyRisk = 0.35,
            minAge = 22,
            hourlyWage = 25.0, compoundRate = 0.0009, assetGrowthRate = 0.007,
            dailyWorkRequirement = 480,
            description = "线上开店，启动成本相对较低，但竞争激烈。"),

        // ── 其他生计路线 ──（纯劳动型）
        CareerOption("migrant_worker", "外出打工青年", CareerPathType.FREELANCE,
            3500.0, 0.05, 8000.0, 0.5, 0.3, 0.0, 0.0,
            minAge = 18,
            hourlyWage = 20.0, compoundRate = 0.0, assetGrowthRate = 0.0,
            dailyWorkRequirement = 540,
            description = "背井离乡进厂或跑工地，收入不高但比老家强。没有社保，干一天算一天。"),

        CareerOption("fulltime_mother", "全职妈妈", CareerPathType.STABLE,
            0.0, 0.0, 0.0, 0.1, 0.25, 0.0, 0.0,
            minAge = 22,
            hourlyWage = 0.0, compoundRate = 0.0, assetGrowthRate = 0.0,
            dailyWorkRequirement = 0,
            description = "24小时待命，没有工资，没有晋升。但小镇承认：这也是一份值得尊重的生计。")
    )

    // ============================================
    // 职业效果计算
    // ============================================

    /** 选择职业后的即时效果 */
    data class CareerChoiceEffect(
        val career: CareerOption,
        val monthlySalary: Double,
        val dailyFatigue: Double,
        val promotionRate: Double,
        val layoffAnnualRisk: Double,
        val startupCostDeducted: Double,     // 扣除的创业启动金
        val recommended: Boolean,             // 系统是否推荐
        val recommendationReason: String      // 推荐原因
    )

    /**
     * 基于角色背景推荐职业倾向
     */
    fun getRecommendedPath(
        isConservative: Boolean,
        isWealthy: Boolean,
        skillPoints: Double,
        currentSavings: Double
    ): CareerPathType {
        return when {
            // 保守家庭 + 技能不高 → 体制路线
            isConservative && skillPoints < 5.0 -> CareerPathType.STABLE
            // 富裕家庭 + 有存款 → 创业
            isWealthy && currentSavings > 50000.0 -> CareerPathType.BUSINESS
            // 技能高 → 大厂
            skillPoints > 8.0 -> CareerPathType.CORPORATE
            // 技能中等 + 存款不多 → 自由职业
            skillPoints > 4.0 -> CareerPathType.FREELANCE
            // 默认
            else -> CareerPathType.STABLE
        }
    }

    /**
     * 计算选择职业后的效果
     */
    fun calculateCareerEffect(
        career: CareerOption,
        currentSavings: Double,
        isRecommended: Boolean
    ): CareerChoiceEffect {
        val startupDeducted = if (career.pathType == CareerPathType.BUSINESS) {
            career.startupCost
        } else 0.0

        val reason = when (career.pathType) {
            CareerPathType.STABLE -> "稳定体面，适合追求生活平衡的你"
            CareerPathType.CORPORATE -> "高薪高成长，适合愿意拼搏的你"
            CareerPathType.FREELANCE -> "自由掌控时间，适合有技能积累的你"
            CareerPathType.BUSINESS -> "高风险高回报，适合有创业精神的你"
        }

        return CareerChoiceEffect(
            career = career,
            monthlySalary = career.baseSalary,
            dailyFatigue = career.fatiguePerDay,
            promotionRate = career.promotionSpeed,
            layoffAnnualRisk = career.layoffRisk,
            startupCostDeducted = startupDeducted,
            recommended = isRecommended,
            recommendationReason = reason
        )
    }
}