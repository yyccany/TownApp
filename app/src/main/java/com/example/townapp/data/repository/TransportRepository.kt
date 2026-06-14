package com.example.townapp.data.repository

data class TransportOption(
    val id: Int,
    val name: String,
    val type: String, // 出行类型
    val dailyCost: Double, // 每日成本（元）
    val dailyTime: Double, // 每日时间（小时）
    val comfortLevel: String, // 舒适度：LOW/MEDIUM/HIGH
    val healthImpact: String, // 健康影响：POSITIVE/NEUTRAL/NEGATIVE
    val flexibility: String, // 灵活性：LOW/MEDIUM/HIGH
    val valueScore: Double, // 价值评分 0-100
    val description: String,
    val recommended: Boolean = false // 是否推荐
)

object TransportRepository {
    // 典型出行方式
    private val transportOptions = listOf(
        // 绿色出行
        TransportOption(101, "步行", "步行", 0.0, 0.5, "LOW", "POSITIVE", "LOW", 85.0, "最健康的出行方式，适合短途通勤（2公里内）", true),
        TransportOption(102, "自行车", "自行车", 2.0, 0.4, "LOW", "POSITIVE", "MEDIUM", 80.0, "环保健康，适合3-5公里通勤，需考虑天气和安全", true),
        TransportOption(103, "共享单车", "共享单车", 3.0, 0.4, "LOW", "POSITIVE", "HIGH", 75.0, "灵活方便，按次计费，适合1-3公里短途出行", true),
        TransportOption(104, "电动自行车", "电动车", 5.0, 0.3, "MEDIUM", "NEUTRAL", "HIGH", 70.0, "速度快，省力，但需要充电和维护", true),

        // 公共交通
        TransportOption(201, "地铁", "地铁", 8.0, 0.7, "MEDIUM", "NEUTRAL", "MEDIUM", 72.0, "准时快速，适合中长距离通勤，高峰时段拥挤", true),
        TransportOption(202, "公交", "公交", 4.0, 1.0, "LOW", "NEUTRAL", "MEDIUM", 55.0, "覆盖面广，但速度慢、不准时，高峰期拥挤", false),
        TransportOption(203, "有轨电车", "有轨电车", 6.0, 0.6, "HIGH", "NEUTRAL", "MEDIUM", 70.0, "舒适平稳，准时，但线路有限", true),
        TransportOption(204, "BRT", "快速公交", 5.0, 0.5, "MEDIUM", "NEUTRAL", "MEDIUM", 68.0, "专用车道，速度快，舒适度中等", true),

        // 机动出行
        TransportOption(301, "网约车", "网约车", 30.0, 0.3, "HIGH", "NEUTRAL", "HIGH", 65.0, "门到门服务，舒适便捷，费用较高", true),
        TransportOption(302, "出租车", "出租车", 35.0, 0.3, "HIGH", "NEUTRAL", "HIGH", 60.0, "传统出租车，随招随停，价格透明", true),
        TransportOption(303, "顺风车", "顺风车", 15.0, 0.4, "MEDIUM", "NEUTRAL", "MEDIUM", 62.0, "共享出行，价格实惠，但需要拼车，时间不确定", true),
        TransportOption(304, "专车", "专车", 60.0, 0.25, "HIGH", "NEUTRAL", "HIGH", 55.0, "高端服务，豪华车型，费用高", false),

        // 私家车
        TransportOption(401, "私家车燃油", "私家车", 80.0, 0.4, "HIGH", "NEGATIVE", "HIGH", 35.0, "自由方便，但成本高，堵车严重，污染环境", false),
        TransportOption(402, "私家车电动", "私家车", 40.0, 0.4, "HIGH", "NEUTRAL", "HIGH", 45.0, "比燃油车便宜，但需要充电，续航有限", false),
        TransportOption(403, "共享汽车", "共享汽车", 45.0, 0.4, "MEDIUM", "NEUTRAL", "HIGH", 50.0, "按小时计费，灵活使用，无需维护", true),
        TransportOption(404, "租车", "租车", 100.0, 0.35, "HIGH", "NEUTRAL", "HIGH", 48.0, "适合长途出行或特殊需求，成本较高", false),

        // 长途出行
        TransportOption(501, "高铁", "高铁", 200.0, 2.0, "HIGH", "NEUTRAL", "MEDIUM", 75.0, "快速舒适，适合200-1000公里出行", true),
        TransportOption(502, "飞机", "飞机", 800.0, 4.0, "HIGH", "NEUTRAL", "LOW", 60.0, "最快的长途出行方式，但机场往返耗时", true),
        TransportOption(503, "长途汽车", "长途汽车", 100.0, 5.0, "LOW", "NEUTRAL", "MEDIUM", 40.0, "最便宜的长途方式，但耗时久、舒适度差", false),
        TransportOption(504, "火车", "火车", 150.0, 6.0, "MEDIUM", "NEUTRAL", "MEDIUM", 45.0, "经济实惠，适合中长途，但速度较慢", false)
    )

    fun getAllOptions(): List<TransportOption> = transportOptions

    fun getOptionsByType(type: String): List<TransportOption> {
        return transportOptions.filter { it.type == type }
    }

    fun getRecommendedOptions(): List<TransportOption> {
        return transportOptions.filter { it.recommended }
    }

    fun getGreenOptions(): List<TransportOption> {
        return transportOptions.filter { listOf("步行", "自行车", "共享单车", "电动车").contains(it.type) }
    }

    fun getPublicTransportOptions(): List<TransportOption> {
        return transportOptions.filter { listOf("地铁", "公交", "有轨电车", "BRT").contains(it.type) }
    }

    fun getPrivateCarOptions(): List<TransportOption> {
        return transportOptions.filter { listOf("私家车燃油", "私家车电动").contains(it.type) }
    }

    fun getBestValueOptions(): List<TransportOption> {
        return transportOptions.sortedByDescending { it.valueScore }.take(10)
    }

    fun getWorstValueOptions(): List<TransportOption> {
        return transportOptions.sortedBy { it.valueScore }.take(5)
    }

    /**
     * 根据时薪计算最优出行方式
     * @param hourlyWage 用户时薪（元）
     * @param distance 通勤距离（公里）
     * @return 推荐的出行方式列表（按价值密度排序）
     */
    fun getOptimalTransport(hourlyWage: Double, distance: Double): List<TransportOption> {
        return transportOptions
            .map { option ->
                // 计算价值密度：考虑时间成本和金钱成本
                val timeCost = option.dailyTime * hourlyWage
                val totalCost = option.dailyCost + timeCost
                val valueDensity = if (totalCost > 0) option.valueScore / totalCost else Double.MAX_VALUE
                Pair(option, valueDensity)
            }
            .sortedByDescending { it.second }
            .map { it.first }
            .take(5)
    }

    /**
     * 计算私家车全生命周期成本
     * @param carPrice 车价（万元）
     * @param years 使用年限（年）
     * @param annualMileage 年行驶里程（公里）
     * @param isElectric 是否电动车
     * @return 平均每月成本（元）
     */
    fun calculateCarTotalCost(
        carPrice: Double,
        years: Int = 10,
        annualMileage: Double = 12000.0,
        isElectric: Boolean = false
    ): Double {
        // 固定成本
        val purchaseCost = carPrice * 10000
        val tax = purchaseCost * 0.0885 // 购置税
        val insurance = if (isElectric) 3000.0 else 4000.0 * years
        val maintenance = if (isElectric) 500.0 else 1500.0 * years

        // 变动成本
        val fuelOrElectricCost = if (isElectric) {
            // 电动车：电耗15kWh/100km，电价0.6元/kWh
            (annualMileage / 100) * 15 * 0.6 * years
        } else {
            // 燃油车：油耗8L/100km，油价7元/L
            (annualMileage / 100) * 8 * 7 * years
        }

        val parking = 300.0 * 12 * years
        val fines = 500.0 * years
        val repairs = 1000.0 * years

        // 折旧
        val residualValue = purchaseCost * 0.1 // 10年后残值10%
        val depreciation = purchaseCost - residualValue

        // 总成本
        val totalCost = purchaseCost + tax + insurance + maintenance + fuelOrElectricCost + parking + fines + repairs + depreciation

        // 平均每月成本
        return totalCost / (years * 12)
    }

    /**
     * 计算出行决策
     * @param hourlyWage 用户时薪（元）
     * @param taxiFee 打车费用（元）
     * @param taxiTime 打车时间（小时）
     * @param publicTime 公共交通时间（小时）
     * @param publicFee 公共交通费用（元）
     * @return 是否推荐打车（true=打车更划算，false=公共交通更划算）
     */
    fun shouldTakeTaxi(
        hourlyWage: Double,
        taxiFee: Double,
        taxiTime: Double,
        publicTime: Double,
        publicFee: Double
    ): Boolean {
        // 打车的净成本 = 打车费 - 节省时间能赚的钱
        val savedTime = publicTime - taxiTime
        val earningsFromSavedTime = savedTime * hourlyWage
        val taxiNetCost = taxiFee - earningsFromSavedTime

        // 如果打车净成本为负，说明打车更划算
        return taxiNetCost < publicFee
    }
}
