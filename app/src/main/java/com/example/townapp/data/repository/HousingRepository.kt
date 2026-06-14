package com.example.townapp.data.repository

data class HousingOption(
    val id: Int,
    val name: String,
    val type: String, // 住房类型
    val location: String, // 位置
    val monthlyRent: Double, // 月租金
    val purchasePrice: Double, // 购买价格（0表示不卖）
    val area: Double, // 面积（平方米）
    val commuteTime: Double, // 单程通勤时间（小时）
    val healthRisk: String, // LOW/MEDIUM/HIGH/EXTREME
    val valueScore: Double, // 价值评分 0-100
    val description: String
)

object HousingRepository {
    // 典型住房选择
    private val housingOptions = listOf(
        // 一线城市上海
        HousingOption(1001, "地下室单间", "地下室", "上海内环", 800.0, 0.0, 10.0, 0.5, "EXTREME", 15.0, "阴暗潮湿，无阳光，通风差，易引发呼吸道疾病和抑郁症"),
        HousingOption(1002, "老破小单间", "老破小", "上海中环", 2500.0, 0.0, 15.0, 0.8, "HIGH", 35.0, "老公房，设施老旧，但位置尚可"),
        HousingOption(1003, "郊区次新房合租", "合租", "上海外环", 1800.0, 0.0, 12.0, 1.5, "MEDIUM", 45.0, "地铁通勤，通勤时间长但有独立房间"),
        HousingOption(1004, "市区精装公寓", "公寓", "上海内环", 5000.0, 0.0, 30.0, 0.3, "LOW", 55.0, "商住两用，无燃气，水电费贵"),
        HousingOption(1005, "市区老小区", "商品房", "上海中环", 3500.0, 0.0, 45.0, 0.6, "MEDIUM", 50.0, "老公房，环境一般，但配套成熟"),
        HousingOption(1006, "近郊商品房", "商品房", "上海外环", 4500.0, 0.0, 70.0, 1.0, "MEDIUM", 60.0, "次新房小区，环境好，但通勤时间长"),
        HousingOption(1007, "市区品质小区", "商品房", "上海内环", 8000.0, 0.0, 90.0, 0.4, "LOW", 70.0, "品质小区，配套完善，价格高"),
        HousingOption(1008, "上海内环小户型", "商品房", "上海内环", 0.0, 5000000.0, 50.0, 0.3, "HIGH", 40.0, "总价500万，首付150万，贷款350万，30年还清"),
        HousingOption(1009, "上海中环次新房", "商品房", "上海中环", 0.0, 8000000.0, 80.0, 0.5, "HIGH", 45.0, "总价800万，首付240万，贷款560万，30年还清"),
        HousingOption(1010, "上海外环新楼盘", "商品房", "上海外环", 0.0, 10000000.0, 100.0, 0.8, "MEDIUM", 55.0, "总价1000万，首付300万，贷款700万，30年还清"),

        // 二线城市成都
        HousingOption(2001, "老旧小区单间", "老破小", "成都一环", 600.0, 0.0, 15.0, 0.3, "HIGH", 45.0, "老小区，设施陈旧，但位置核心"),
        HousingOption(2002, "老小区合租", "合租", "成都二环", 800.0, 0.0, 12.0, 0.5, "MEDIUM", 50.0, "合租，有独立房间，公用卫生间"),
        HousingOption(2003, "高层住宅单间", "公寓", "成都三环", 1200.0, 0.0, 25.0, 0.4, "LOW", 55.0, "高层住宅，有电梯，配套一般"),
        HousingOption(2004, "品质小区套二", "商品房", "成都二环", 2500.0, 0.0, 60.0, 0.4, "LOW", 70.0, "品质小区，环境好，配套成熟"),
        HousingOption(2005, "近郊新房", "商品房", "成都绕城", 1800.0, 0.0, 80.0, 0.8, "MEDIUM", 60.0, "新小区，环境好，通勤时间长"),
        HousingOption(2006, "成都一环小户型", "商品房", "成都一环", 0.0, 1200000.0, 50.0, 0.3, "HIGH", 50.0, "总价120万，首付36万，贷款84万，30年还清"),
        HousingOption(2007, "成都高新南区", "商品房", "成都高新区", 0.0, 2500000.0, 90.0, 0.5, "MEDIUM", 65.0, "总价250万，首付75万，贷款175万，30年还清"),

        // 三线城市
        HousingOption(3001, "老城区单间", "老破小", "市区", 300.0, 0.0, 20.0, 0.2, "MEDIUM", 60.0, "老小区，配套成熟，出行方便"),
        HousingOption(3002, "新城区住宅", "商品房", "新区", 800.0, 0.0, 90.0, 0.3, "LOW", 75.0, "新小区，环境好，配套逐步完善"),
        HousingOption(3003, "市区三居室", "商品房", "市区", 1200.0, 0.0, 120.0, 0.2, "LOW", 80.0, "大三居，宽敞舒适，性价比高"),
        HousingOption(3004, "三线城市购房", "商品房", "市区", 0.0, 600000.0, 100.0, 0.2, "LOW", 70.0, "总价60万，首付18万，贷款42万，30年还清"),
        HousingOption(3005, "县城自建房", "自建房", "县城", 0.0, 300000.0, 150.0, 0.1, "LOW", 65.0, "自建房屋，产权复杂，但空间大"),

        // 特殊住房类型
        HousingOption(4001, "青年旅社床位", "床位", "市中心", 500.0, 0.0, 6.0, 0.4, "HIGH", 30.0, "多人间，隐私差，但社交机会多"),
        HousingOption(4002, "人才公寓", "公寓", "产业园区", 1000.0, 0.0, 30.0, 0.5, "LOW", 60.0, "政府补贴，价格低，但位置偏"),
        HousingOption(4003, "蓝领公寓", "宿舍", "工业园区", 600.0, 0.0, 20.0, 0.6, "MEDIUM", 45.0, "集体宿舍，设施简单，价格便宜"),
        HousingOption(4004, "民宿单间", "民宿", "景区附近", 1500.0, 0.0, 25.0, 0.3, "MEDIUM", 50.0, "短租为主，灵活但不稳定"),
        HousingOption(4005, "长租酒店", "酒店", "市中心", 3000.0, 0.0, 35.0, 0.3, "LOW", 55.0, "酒店式管理，服务好，但无厨房"),
        HousingOption(4006, "共有产权房", "保障房", "近郊", 0.0, 3500000.0, 70.0, 0.7, "LOW", 65.0, "政府产权份额，价格低，但流通受限"),

        // 极端案例
        HousingOption(5001, "城中村民房", "城中村", "城中村", 400.0, 0.0, 15.0, 0.8, "EXTREME", 25.0, "握手楼，采光差，消防隐患大"),
        HousingOption(5002, "隔断房", "隔断", "老小区", 700.0, 0.0, 8.0, 0.5, "EXTREME", 20.0, "违规隔断，空间小，噪音大，法律风险高"),
        HousingOption(5003, "危房改造房", "危改", "老城区", 500.0, 0.0, 20.0, 0.4, "HIGH", 35.0, "老旧危房，安全隐患大，设施差")
    )

    fun getAllOptions(): List<HousingOption> = housingOptions

    fun getOptionsByCity(city: String): List<HousingOption> {
        return housingOptions.filter { option ->
            option.name.contains(city) || option.location.contains(city)
        }
    }

    fun getOptionsByType(type: String): List<HousingOption> {
        return housingOptions.filter { it.type == type }
    }

    fun getHealthyOptions(maxRisk: String = "MEDIUM"): List<HousingOption> {
        val riskOrder = listOf("LOW", "MEDIUM", "HIGH", "EXTREME")
        val maxIndex = riskOrder.indexOf(maxRisk)
        return housingOptions.filter { riskOrder.indexOf(it.healthRisk) <= maxIndex }
    }

    fun getAffordableOptions(maxMonthlyRent: Double): List<HousingOption> {
        return housingOptions.filter { it.monthlyRent > 0 && it.monthlyRent <= maxMonthlyRent }
    }

    fun getPurchaseOptions(maxPrice: Double): List<HousingOption> {
        return housingOptions.filter { it.purchasePrice > 0 && it.purchasePrice <= maxPrice }
    }

    fun getBestValueOptions(): List<HousingOption> {
        return housingOptions.sortedByDescending { it.valueScore }.take(10)
    }

    fun getWorstValueOptions(): List<HousingOption> {
        return housingOptions.sortedBy { it.valueScore }.take(10)
    }
}
