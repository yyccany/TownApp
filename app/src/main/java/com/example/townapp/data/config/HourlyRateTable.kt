package com.example.townapp.data

/**
 * 身份时薪基准表（V2.0 数据校准核心）
 *
 * 统一15条人生路径的月收入、月工时、时薪数据，作为 workHourCost 计算基准。
 *
 * 计算规则：
 * - 时薪 = 月收入 / 月工时
 * - 无固定劳动收入者（全职妈妈、全职儿女、退休工人、应届生）：时薪标记为 0，使用降级文案
 * - 优渥青年：依托家庭资产，不消耗个人工时，但仍标注时薪供对照参考
 *
 * 三类物品口径：
 * - 刚需日用品/食品：按日常售价
 * - 爱好器材/服饰：按主流市场价
 * - 高端体验：按实际客单价（跳伞、游艇、海外观赛）
 */
data class IdentityHourlyRate(
    val pathId: String,
    val pathTitle: String,
    val monthlyIncome: Double,
    val monthlyWorkHours: Double,
    val category: IdentityEarningCategory
) {
    /** 时薪（元/小时），无固定工时则返回 0 */
    val hourlyWage: Double
        get() = if (monthlyWorkHours > 0) monthlyIncome / monthlyWorkHours else 0.0

    /**
     * 计算该身份购置某件物品所需劳动小时数
     */
    fun workHourCost(itemPrice: Double): Double {
        if (hourlyWage <= 0) return -1.0  // 负数表示需要降级文案
        return itemPrice / hourlyWage
    }

    /**
     * 格式化工时显示
     */
    fun formatWorkHours(itemPrice: Double): String {
        val hours = workHourCost(itemPrice)
        if (hours < 0) return "—"
        return String.format("%.1f 小时", hours)
    }
}

/** 收入来源类型 */
enum class IdentityEarningCategory(val label: String, val description: String) {
    LABOR_WAGE("劳动薪资", "靠出卖劳动时间换取收入，时薪计算准确"),
    FAMILY_SUPPORT("家庭供养", "无个人固定劳动收入，依靠家庭/配偶/养老金支撑"),
    SAVINGS_ONLY("仅靠储蓄", "目前处于求职/待业状态，没有定时收入")
}

/**
 * 预置15条身份的时薪基准数据
 *
 * 数据来源：
 * - 10条主线来自 LifePathBindingData.SpaceConfig
 * - 5条补充线（童年、优渥、天赋、强健、残障）为基于场景描述的实际估算
 *
 * 收入/工时取的是该身份的「现实均值」，不做美化或压低。
 */
object IdentityHourlyRateTable {

    val rates: List<IdentityHourlyRate> = listOf(
        // ========== 生命路径绑定中的10条 ==========
        IdentityHourlyRate("migrant_youth", "外出打工青年", 4500.0, 260.0, IdentityEarningCategory.LABOR_WAGE),
        // → 时薪 ≈ 17.3 元/小时

        IdentityHourlyRate("housewife", "全职妈妈", 3000.0, 0.0, IdentityEarningCategory.FAMILY_SUPPORT),
        // → 无个人劳动时薪，收入为配偶收入/家庭共有

        IdentityHourlyRate("delivery_rider", "外卖骑手", 6000.0, 224.0, IdentityEarningCategory.LABOR_WAGE),
        // → 时薪 ≈ 26.8 元/小时

        IdentityHourlyRate("fresh_graduate", "应届毕业生", 0.0, 0.0, IdentityEarningCategory.SAVINGS_ONLY),
        // → 求职期，无固定收入

        IdentityHourlyRate("construction_worker", "工地工人", 7000.0, 260.0, IdentityEarningCategory.LABOR_WAGE),
        // → 时薪 ≈ 26.9 元/小时

        IdentityHourlyRate("shop_owner", "小店店主", 5000.0, 300.0, IdentityEarningCategory.LABOR_WAGE),
        // → 时薪 ≈ 16.7 元/小时（自主经营，工时最长但时薪偏低）

        IdentityHourlyRate("adult_child", "全职儿女", 1500.0, 0.0, IdentityEarningCategory.FAMILY_SUPPORT),
        // → 零用/补贴性质，非劳动收入

        IdentityHourlyRate("retired_worker", "退休工人", 2800.0, 0.0, IdentityEarningCategory.FAMILY_SUPPORT),
        // → 养老金，非劳动时薪

        IdentityHourlyRate("freelancer", "自由职业者", 8000.0, 120.0, IdentityEarningCategory.LABOR_WAGE),
        // → 时薪 ≈ 66.7 元/小时（高时薪但收入不稳定）

        IdentityHourlyRate("civil_servant", "基层公务员", 4500.0, 154.0, IdentityEarningCategory.LABOR_WAGE),
        // → 时薪 ≈ 29.2 元/小时

        // ========== 补充线（来自 LifePathSimulator，无 SpaceConfig，按场景估算） ==========
        IdentityHourlyRate("childhood_self", "童年时期的自己", 0.0, 0.0, IdentityEarningCategory.FAMILY_SUPPORT),
        // → 儿童无收入，依赖家庭。不适用时薪计算，展示降级文案。

        IdentityHourlyRate("affluent_youth", "优渥家境青年", 12000.0, 140.0, IdentityEarningCategory.LABOR_WAGE),
        // → 时薪 ≈ 85.7 元/小时
        // 家中安排的工作，薪资优渥但工时较短。文案区分：「依托家庭资产，不消耗个人工时」的场景用降级分支。

        IdentityHourlyRate("gifted_youth", "天赋高智商青年", 15000.0, 180.0, IdentityEarningCategory.LABOR_WAGE),
        // → 时薪 ≈ 83.3 元/小时。高收入但工时也长。

        IdentityHourlyRate("strong_rural", "先天体魄强健的乡村青年", 5000.0, 250.0, IdentityEarningCategory.LABOR_WAGE),
        // → 时薪 ≈ 20.0 元/小时。体力好但偏远地区薪资上限低。

        IdentityHourlyRate("disabled_youth", "先天肢体残障青年", 3500.0, 120.0, IdentityEarningCategory.LABOR_WAGE)
        // → 时薪 ≈ 29.2 元/小时。受限于身体条件，工作时长受限，但工作质量不打折。
    )

    /** 按路径 ID 查找时薪数据 */
    fun findByPathId(pathId: String): IdentityHourlyRate? = rates.find { it.pathId == pathId }

    /** 获取所有有劳动时薪的身份 */
    fun getLaborRates(): List<IdentityHourlyRate> =
        rates.filter { it.category == IdentityEarningCategory.LABOR_WAGE }

    /** 获取所有无固定劳动收入的 */
    fun getNonLaborRates(): List<IdentityHourlyRate> =
        rates.filter { it.category != IdentityEarningCategory.LABOR_WAGE }
}