package com.example.townapp.data

/**
 * 时代成本系统（v2.0 五层人生模型闭环的最后一块）
 *
 * ============================================
 * 核心概念
 * ============================================
 * 个人工时成本：个人劳动换取金钱，受自身职业、能力控制。
 * 时代成本：宏观时代变迁之下，一代人被动承担的整体代价，不受个人工时控制。
 * 个人再勤奋，也无法脱离时代大环境。
 *
 * 举个简单的例子：二十年前一份普通岗位薪资、房价极低，同等劳动时长，买房压力很小；
 * 数十年之后，年轻人即便个人工作时长、劳动强度不变，房价整体抬升，买房需要付出
 * 多出数倍工时。多出的这部分劳动损耗，就是时代带来的成本。
 *
 * ============================================
 * 四类时代成本
 * ============================================
 * 1. 通胀物价成本：物价逐年上涨，购买力缩水
 * 2. 行业兴衰机会成本：产业迭代，部分行业衰落，薪资停滞
 * 3. 阶段时代环境成本：就业市场整体内卷/宽松，应届生竞争难度变化
 * 4. 代际时代落差成本：父辈与子女两代人的生存条件差距
 *
 * ============================================
 * 世界观定位
 * ============================================
 * 至此，全部影响人生的变量梳理齐全：
 * 微观：先天身体、原生家庭、童年、爱好、消费、工时收支、性格
 * 人际：亲友婚恋、育儿养老、职业圈层认知差异
 * 地域：城乡迁徙、城市物价、地域风俗
 * 短期时代：体育赛事、潮流文娱、阶段性就业环境
 * 长期时代：通胀、行业兴衰、代际成本、数十年社会变迁
 * ============================================
 */

// ============================================
// 一、通胀物价成本
// ============================================

/**
 * 通胀配置 —— 年度物价上涨系数
 */
data class EraInflationConfig(
    /** 基准年（游戏时间起点） */
    val baseYear: Int = 2024,
    /** 年通胀率 */
    val annualInflationRate: Double = 0.03,
    /** 不同品类的通胀差异系数（相对基准通胀率） */
    val categoryMultipliers: Map<String, Double> = mapOf(
        "food" to 1.0,        // 食品：标准通胀
        "rent" to 1.5,         // 房租：高于平均
        "medical" to 1.3,      // 医疗：高于平均
        "entertainment" to 1.0, // 文娱：标准
        "education" to 1.4,    // 教育：高于平均
        "clothing" to 0.8,     // 服饰：低于平均（制造业效率提升）
        "electronics" to 0.5,  // 电子产品：远低于平均（技术迭代降价）
        "daily" to 1.0         // 日用品：标准
    )
) {
    /**
     * 计算指定年份某品类相对于基准年的价格倍数
     */
    fun getPriceMultiplier(year: Int, category: String): Double {
        val yearsPassed = (year - baseYear).coerceAtLeast(0)
        val categoryRate = annualInflationRate * (categoryMultipliers[category] ?: 1.0)
        return Math.pow(1.0 + categoryRate, yearsPassed.toDouble())
    }

    /**
     * 计算折算后的价格
     */
    fun adjustPrice(basePrice: Double, year: Int, category: String): Double {
        return basePrice * getPriceMultiplier(year, category)
    }
}

// ============================================
// 二、行业兴衰机会成本
// ============================================

/**
 * 行业生命周期配置
 */
data class IndustryLifecycle(
    val industryName: String,
    val displayName: String,
    /** 行业兴起年份 */
    val riseYear: Int,
    /** 鼎盛峰值年份 */
    val peakYear: Int,
    /** 开始衰退年份 */
    val declineYear: Int,
    /** 是否已消亡（0=否，年份=消亡年份） */
    val extinctYear: Int = 0,
    /** 薪资倍数曲线（年份 → 相对基准薪资的倍数） */
    val salaryMultiplierDescription: String
) {
    /**
     * 计算指定年份该行业的薪资倍数
     *
     * 简化为四段式：上升期 → 鼎盛期 → 衰退期 → 消亡/转型期
     */
    fun getSalaryMultiplier(year: Int): Double = when {
        extinctYear > 0 && year >= extinctYear -> 0.0          // 行业消亡
        year >= declineYear -> maxOf(0.5, 1.0 - (year - declineYear) * 0.05)  // 衰退期
        year >= peakYear -> 1.0                                  // 鼎盛期
        year >= riseYear -> 0.7 + (year - riseYear) * 0.03      // 上升期
        else -> 0.6                                              // 行业早期
    }

    /** 当前行业阶段描述 */
    fun getStageDescription(year: Int): String = when {
        extinctYear > 0 && year >= extinctYear -> "该行业已经基本消亡，从业人员大多转行"
        year >= declineYear -> "行业处于衰退期，岗位缩减，薪资增长停滞"
        year >= peakYear -> "行业处于鼎盛期，薪资和岗位数量达到峰值"
        year >= riseYear -> "行业处于上升期，岗位和薪资逐年增长"
        else -> "行业尚在早期发展阶段"
    }
}

/**
 * 预置行业生命周期数据
 */
object IndustryLifecycles {

    val industries: List<IndustryLifecycle> = listOf(
        // 传统制造业：2024年已过巅峰，2030后加速衰退
        IndustryLifecycle(
            industryName = "traditional_manufacturing",
            displayName = "传统制造业",
            riseYear = 2000, peakYear = 2015, declineYear = 2028, extinctYear = 0,
            salaryMultiplierDescription = "2000-2015上升期 → 2015-2028鼎盛 → 2028后衰退"
        ),
        // 传统零售：2024年已在衰退中
        IndustryLifecycle(
            industryName = "traditional_retail",
            displayName = "传统线下零售",
            riseYear = 1995, peakYear = 2010, declineYear = 2020, extinctYear = 0,
            salaryMultiplierDescription = "1995-2010上升期 → 2010-2020鼎盛 → 2020后持续衰退"
        ),
        // 互联网/新媒体：2024年仍在鼎盛期，2035后可能进入平台期
        IndustryLifecycle(
            industryName = "internet_new_media",
            displayName = "互联网/新媒体",
            riseYear = 2010, peakYear = 2025, declineYear = 2038, extinctYear = 0,
            salaryMultiplierDescription = "2010-2025上升期 → 2025-2038鼎盛 → 2038后进入平台期"
        ),
        // 外卖/物流配送：2024鼎盛期，2035后可能因自动化衰退
        IndustryLifecycle(
            industryName = "delivery_logistics",
            displayName = "外卖/物流配送",
            riseYear = 2015, peakYear = 2024, declineYear = 2035, extinctYear = 0,
            salaryMultiplierDescription = "2015-2024上升期 → 2024-2035鼎盛 → 2035后可能因自动化衰退"
        ),
        // 建筑/工地：随城市化进程波动
        IndustryLifecycle(
            industryName = "construction",
            displayName = "建筑/工地",
            riseYear = 2000, peakYear = 2020, declineYear = 2030, extinctYear = 0,
            salaryMultiplierDescription = "2000-2020上升期 → 2020-2030鼎盛 → 2030后增速放缓"
        ),
        // 基层公职：相对稳定，波动小
        IndustryLifecycle(
            industryName = "civil_service",
            displayName = "基层公职",
            riseYear = 2000, peakYear = 2020, declineYear = 2050, extinctYear = 0,
            salaryMultiplierDescription = "长期稳定，薪资随通胀缓慢增长"
        ),
        // 自由职业/自媒体：仍在上升期
        IndustryLifecycle(
            industryName = "freelance_media",
            displayName = "自由职业/自媒体",
            riseYear = 2018, peakYear = 2030, declineYear = 2045, extinctYear = 0,
            salaryMultiplierDescription = "2018-2030快速上升期 → 2030后进入平台期"
        )
    )

    fun findByName(name: String): IndustryLifecycle? = industries.find { it.industryName == name }

    /** 根据职业路径 ID 查找对应的行业 */
    fun findByPathId(pathId: String): IndustryLifecycle? = when (pathId) {
        "migrant_youth" -> findByName("traditional_manufacturing")
        "construction_worker" -> findByName("construction")
        "delivery_rider" -> findByName("delivery_logistics")
        "shop_owner" -> findByName("traditional_retail")
        "civil_servant" -> findByName("civil_service")
        "freelancer" -> findByName("freelance_media")
        "fresh_graduate" -> findByName("internet_new_media")
        else -> null
    }
}

// ============================================
// 三、阶段性时代整体环境
// ============================================

/**
 * 时代就业环境配置
 */
data class EraEmploymentConfig(
    val yearRange: IntRange,
    /** 就业难度系数（1.0=正常，>1.0=更难，<1.0=更容易） */
    val difficultyMultiplier: Double,
    /** 年份描述 */
    val description: String
)

/**
 * 预置时代环境数据
 */
object EraEmploymentConfigs {

    val configs: List<EraEmploymentConfig> = listOf(
        EraEmploymentConfig(2024..2027, 1.0, "就业市场平稳期，应届生求职难度适中"),
        EraEmploymentConfig(2028..2032, 1.2, "就业内卷加剧，大量应届生涌入市场，竞争压力上升"),
        EraEmploymentConfig(2033..2038, 1.0, "新兴产业吸纳部分就业，整体压力有所缓解"),
        EraEmploymentConfig(2039..2045, 1.3, "传统行业加速衰退，结构性失业增加，转行压力增大"),
        EraEmploymentConfig(2046..2055, 1.1, "新经济形态逐步稳定，就业环境趋于平稳")
    )

    fun getDifficultyMultiplier(year: Int): Double {
        return configs.find { year in it.yearRange }?.difficultyMultiplier ?: 1.0
    }

    fun getDescription(year: Int): String {
        return configs.find { year in it.yearRange }?.description ?: "时代环境数据暂缺"
    }
}

// ============================================
// 四、代际时代落差成本
// ============================================

/**
 * 代际成本对比数据
 *
 * 用于计算父辈与子女两代人在同等生活目标下的工时差距。
 */
data class GenerationalCostGap(
    /** 父辈基准年 */
    val parentYear: Int,
    /** 子女基准年 */
    val childYear: Int,
    /** 父辈时期某物品价格 */
    val parentPrice: Double,
    /** 子女时期同物品价格（已考虑通胀） */
    val childPrice: Double,
    /** 父辈时期时薪 */
    val parentHourlyWage: Double,
    /** 子女时期时薪 */
    val childHourlyWage: Double
) {
    /** 父辈所需工时 */
    val parentWorkHours: Double get() = if (parentHourlyWage > 0) parentPrice / parentHourlyWage else 0.0

    /** 子女所需工时 */
    val childWorkHours: Double get() = if (childHourlyWage > 0) childPrice / childHourlyWage else 0.0

    /** 时代成本：多出的工时 */
    val eraCostHours: Double get() = (childWorkHours - parentWorkHours).coerceAtLeast(0.0)

    /** 时代成本占子女总工时的比例 */
    val eraCostRatio: Double get() = if (childWorkHours > 0) eraCostHours / childWorkHours else 0.0
}

/**
 * 代际对比场景配置
 */
object GenerationalCostScenarios {

    /**
     * 计算买房代际成本
     *
     * @param parentYear 父辈购房年份
     * @param childYear 子女购房年份
     * @param housePriceBase 基准年房价
     * @param parentHourlyWage 父辈时薪
     * @param childHourlyWage 子女时薪
     * @param inflation 通胀配置
     */
    fun calculateHousingGap(
        parentYear: Int,
        childYear: Int,
        housePriceBase: Double,
        parentHourlyWage: Double,
        childHourlyWage: Double,
        inflation: EraInflationConfig
    ): GenerationalCostGap {
        val parentPrice = inflation.adjustPrice(housePriceBase, parentYear, "rent")
        val childPrice = inflation.adjustPrice(housePriceBase, childYear, "rent")
        return GenerationalCostGap(parentYear, childYear, parentPrice, childPrice, parentHourlyWage, childHourlyWage)
    }
}