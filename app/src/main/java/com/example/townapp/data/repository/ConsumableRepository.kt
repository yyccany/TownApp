package com.example.townapp.data.repository

data class ConsumableItem(
    val id: Int,
    val name: String,
    val category: String, // 类别
    val brand: String,
    val price: Double, // 价格（元）
    val usageCount: Int, // 使用次数
    val healthRisk: String, // LOW/MEDIUM/HIGH/EXTREME
    val valueScore: Double, // 价值评分 0-100
    val description: String,
    val alternative: String, // 替代品
    val alternativePrice: Double // 替代品价格
)

object ConsumableRepository {
    // 成瘾性消费品
    private val addictiveItems = listOf(
        // 香烟
        ConsumableItem(101, "中华香烟", "香烟", "中华", 65.0, 20, "EXTREME", 1.0, "一包20支，尼古丁含量1.0mg/支，含有69种致癌物", "戒烟", 0.0),
        ConsumableItem(102, "红塔山香烟", "香烟", "红塔山", 20.0, 20, "EXTREME", 1.0, "一包20支，尼古丁含量0.8mg/支", "戒烟", 0.0),
        ConsumableItem(103, "电子烟", "香烟", "悦刻", 99.0, 200, "HIGH", 5.0, "尼古丁含量5%，虽无烟焦油，但仍有成瘾性", "戒烟", 0.0),
        
        // 酒精
        ConsumableItem(201, "茅台飞天", "白酒", "茅台", 1499.0, 10, "EXTREME", 2.0, "53度酱香白酒，酒精含量53%", "戒酒", 0.0),
        ConsumableItem(202, "五粮液", "白酒", "五粮液", 1399.0, 10, "EXTREME", 2.0, "52度浓香白酒，酒精含量52%", "戒酒", 0.0),
        ConsumableItem(203, "青岛啤酒", "啤酒", "青岛", 8.0, 3, "HIGH", 10.0, "500ml瓶装，酒精含量4%", "戒酒", 0.0),
        ConsumableItem(204, "长城红酒", "红酒", "长城", 128.0, 5, "MEDIUM", 15.0, "750ml瓶装，酒精含量12%", "戒酒", 0.0),
        
        // 含糖饮料
        ConsumableItem(301, "可口可乐", "饮料", "可口可乐", 3.5, 100, "HIGH", 5.0, "500ml瓶装，含糖54g，相当于13块方糖", "白开水", 0.0),
        ConsumableItem(302, "奈雪奶茶", "饮料", "奈雪", 22.0, 30, "HIGH", 3.0, "500ml，含糖60-80g，相当于15-20块方糖", "白开水", 0.0),
        ConsumableItem(303, "元气森林", "饮料", "元气森林", 5.0, 100, "MEDIUM", 10.0, "500ml，无糖但含人工甜味剂", "白开水", 0.0),
        ConsumableItem(304, "红牛", "饮料", "红牛", 6.0, 30, "HIGH", 5.0, "250ml，含咖啡因80mg，牛磺酸1000mg", "黑咖啡", 15.0)
    )

    // 护肤品
    private val skincareItems = listOf(
        // 贵价护肤品
        ConsumableItem(401, "海蓝之谜面霜", "护肤品", "La Mer", 3000.0, 60, "LOW", 2.0, "50ml，核心成分：水、甘油、矿物油", "尿素维E乳", 10.0),
        ConsumableItem(402, "雅诗兰黛小棕瓶", "护肤品", "Estee Lauder", 1080.0, 60, "LOW", 1.0, "50ml，核心成分：水、二裂酵母发酵产物", "阿达帕林凝胶", 30.0),
        ConsumableItem(403, "SK-II神仙水", "护肤品", "SK-II", 1590.0, 100, "LOW", 1.0, "230ml，核心成分：水、半乳糖酵母样菌发酵产物", "尿素维E乳", 10.0),
        ConsumableItem(404, "兰蔻菁纯面霜", "护肤品", "Lancome", 2580.0, 60, "LOW", 2.0, "50ml，核心成分：水、甘油、角鲨烷", "阿达帕林凝胶", 30.0),
        ConsumableItem(405, "面膜", "护肤品", "各类品牌", 15.0, 1, "LOW", 5.0, "单片装，核心成分：水、甘油、防腐剂", "矿泉水喷雾", 10.0),
        ConsumableItem(406, "迪奥口红", "护肤品", "Dior", 350.0, 50, "LOW", 10.0, "3.5g，核心成分：凡士林、羊毛脂", "凡士林润唇膏", 5.0),
        
        // 平价/医用护肤品
        ConsumableItem(451, "尿素维生素E乳", "护肤品", "协和", 10.0, 200, "LOW", 95.0, "100ml，核心成分：尿素10%、维生素E", "无", 0.0),
        ConsumableItem(452, "阿达帕林凝胶", "护肤品", "达芙文", 30.0, 90, "LOW", 90.0, "30g，第三代维A酸，治疗痤疮、抗衰老", "无", 0.0),
        ConsumableItem(453, "氧化锌软膏", "护肤品", "炉甘石", 15.0, 100, "LOW", 85.0, "20g，物理防晒、治疗湿疹", "无", 0.0),
        ConsumableItem(454, "凡士林润唇膏", "护肤品", "凡士林", 5.0, 100, "LOW", 80.0, "4.8g，核心成分：凡士林", "无", 0.0)
    )

    // 药品
    private val medicineItems = listOf(
        // 现代药品 - 解热镇痛
        ConsumableItem(501, "布洛芬缓释胶囊", "药品", "芬必得", 20.0, 20, "LOW", 98.0, "20粒，解热镇痛，安全有效", "无", 0.0),
        ConsumableItem(502, "对乙酰氨基酚片", "药品", "泰诺林", 15.0, 20, "LOW", 96.0, "20片，解热镇痛，对胃刺激小于布洛芬", "无", 0.0),
        ConsumableItem(503, "阿莫西林胶囊", "药品", "联邦", 25.0, 24, "MEDIUM", 88.0, "24粒，青霉素类抗生素，需皮试", "无", 0.0),
        // 抗过敏
        ConsumableItem(504, "氯雷他定片", "药品", "开瑞坦", 28.0, 12, "LOW", 92.0, "12片，第二代抗组胺药，嗜睡副作用低", "无", 0.0),
        ConsumableItem(505, "盐酸西替利嗪", "药品", "仙特明", 22.0, 10, "LOW", 90.0, "10片，抗过敏，起效快于氯雷他定", "无", 0.0),
        // 消化系统
        ConsumableItem(506, "奥美拉唑肠溶胶囊", "药品", "洛赛克", 35.0, 14, "LOW", 93.0, "14粒，质子泵抑制剂，抑制胃酸", "无", 0.0),
        ConsumableItem(507, "蒙脱石散", "药品", "思密达", 25.0, 10, "LOW", 90.0, "10袋，物理止泻，不进入血液循环", "无", 0.0),
        ConsumableItem(508, "多潘立酮片", "药品", "吗丁啉", 18.0, 30, "MEDIUM", 82.0, "30片，促胃动力药，注意心脏副作用", "无", 0.0),
        // 心血管/代谢
        ConsumableItem(509, "硝苯地平控释片", "药品", "拜新同", 42.0, 7, "LOW", 95.0, "7片，钙通道阻滞剂，降压药", "无", 0.0),
        ConsumableItem(510, "二甲双胍片", "药品", "格华止", 15.0, 30, "LOW", 94.0, "30片，2型糖尿病一线用药", "无", 0.0),
        ConsumableItem(511, "阿托伐他汀钙片", "药品", "立普妥", 55.0, 7, "LOW", 93.0, "7片，降脂药，降低胆固醇", "无", 0.0),
        // 外用药
        ConsumableItem(512, "莫匹罗星软膏", "药品", "百多邦", 28.0, 15, "LOW", 88.0, "15g，外用抗生素软膏", "无", 0.0),
        ConsumableItem(513, "复方醋酸地塞米松", "药品", "皮炎平", 12.0, 20, "MEDIUM", 70.0, "20g，激素类外用药，不宜长期使用", "无", 0.0),
        // 维生素类
        ConsumableItem(514, "维生素D滴剂", "药品", "星鲨", 35.0, 30, "LOW", 92.0, "30粒，400IU/粒，促进钙吸收", "晒太阳", 0.0),
        ConsumableItem(515, "复合维生素B片", "药品", "华中药业", 5.0, 100, "LOW", 88.0, "100片，B族维生素补充", "粗粮", 10.0),

        // 中药
        ConsumableItem(551, "板蓝根颗粒", "中药", "白云山", 25.0, 20, "MEDIUM", 10.0, "20袋，清热解毒，无临床试验证明有效", "布洛芬", 20.0),
        ConsumableItem(552, "藿香正气水", "中药", "太极", 15.0, 10, "HIGH", 5.0, "10支，含酒精40%-50%，副作用尚不明确", "蒙脱石散", 25.0),
        ConsumableItem(553, "牛黄解毒片", "中药", "同仁堂", 10.0, 36, "HIGH", 5.0, "36片，含雄黄（含砷），长期服用可能中毒", "布洛芬", 20.0),
        ConsumableItem(554, "复方丹参滴丸", "中药", "天士力", 35.0, 180, "MEDIUM", 10.0, "180丸，活血化瘀，无严格临床试验", "阿司匹林", 10.0),
        ConsumableItem(555, "六味地黄丸", "中药", "同仁堂", 20.0, 360, "MEDIUM", 5.0, "360丸，滋阴补肾，无严格临床试验", "无", 0.0),
        ConsumableItem(556, "云南白药气雾剂", "中药", "云南白药", 45.0, 60, "MEDIUM", 20.0, "60g，保密配方，成分不完全公开", "冰敷", 0.0),
        ConsumableItem(557, "急支糖浆", "中药", "太极", 28.0, 10, "LOW", 15.0, "100ml，止咳化痰，含麻黄碱", "蜂蜜水", 5.0),
        ConsumableItem(558, "安宫牛黄丸", "中药", "同仁堂", 560.0, 1, "MEDIUM", 8.0, "1丸，急救用药，含朱砂(硫化汞)和雄黄(砷)", "无替代", 0.0),
        ConsumableItem(559, "逍遥丸", "中药", "同仁堂", 18.0, 200, "MEDIUM", 5.0, "200丸，疏肝解郁，无严格临床试验", "运动疗法", 0.0),
        ConsumableItem(560, "保和丸", "中药", "同仁堂", 15.0, 200, "LOW", 8.0, "200丸，消食导滞，辅助消化", "散步", 0.0)
    )

    // 其他消费品
    private val otherItems = listOf(
        // 电子产品配件
        ConsumableItem(601, "AirPods Pro", "电子产品", "Apple", 1899.0, 365, "LOW", 30.0, "无线降噪耳机，音质不错但价格过高", "有线耳机", 100.0),
        ConsumableItem(602, "手机壳", "电子产品", "各类品牌", 50.0, 365, "LOW", 20.0, "保护手机，防止摔落", "硅胶壳", 10.0),
        ConsumableItem(603, "数据线", "电子产品", "Apple", 149.0, 365, "LOW", 15.0, "Lightning数据线", "第三方数据线", 20.0),
        
        // 保健品
        ConsumableItem(701, "脑白金", "保健品", "脑白金", 198.0, 30, "LOW", 5.0, "主要成分：褪黑素、淀粉", "早睡", 0.0),
        ConsumableItem(702, "阿胶", "保健品", "东阿阿胶", 899.0, 30, "LOW", 5.0, "主要成分：驴皮、冰糖", "蛋白质粉", 50.0),
        ConsumableItem(703, "维生素C片(保健品)", "保健品", "汤臣倍健", 99.0, 100, "LOW", 20.0, "维生素C 1000mg/片，远超RDA", "橙子", 5.0),
        ConsumableItem(704, "深海鱼油", "保健品", "Swisse", 198.0, 90, "LOW", 30.0, "EPA 180mg + DHA 120mg/粒", "三文鱼", 60.0),
        ConsumableItem(705, "蛋白粉(乳清)", "保健品", "ON", 398.0, 30, "LOW", 55.0, "乳清蛋白24g/勺，健身补充", "鸡胸肉", 12.0),
        ConsumableItem(706, "钙片+维生素D", "保健品", "钙尔奇", 89.0, 60, "LOW", 45.0, "钙600mg+VD 400IU/片", "牛奶", 3.0),
        ConsumableItem(707, "益生菌胶囊", "保健品", "LifeSpace", 168.0, 60, "LOW", 25.0, "15种菌株，320亿CFU/粒", "酸奶", 3.0),
        ConsumableItem(708, "叶酸片", "保健品", "斯利安", 35.0, 30, "LOW", 60.0, "0.4mg/片，备孕孕期必需", "深绿色蔬菜", 4.0),
        ConsumableItem(709, "胶原蛋白口服液", "保健品", "Fancl", 258.0, 10, "LOW", 8.0, "5000mg胶原蛋白肽/瓶，皮肤弹性声称", "鸡蛋", 1.5),
        ConsumableItem(710, "护肝片(奶蓟草)", "保健品", "Swisse", 168.0, 60, "LOW", 15.0, "奶蓟草提取物35g/kg，保肝声称", "戒酒", 0.0),
        
        // 零食
        ConsumableItem(801, "乐事薯片", "零食", "乐事", 10.0, 10, "MEDIUM", 10.0, "50g，含大量盐和反式脂肪", "坚果", 20.0),
        ConsumableItem(802, "巧克力", "零食", "德芙", 20.0, 5, "MEDIUM", 15.0, "100g，含大量糖和脂肪", "黑巧克力", 30.0),
        ConsumableItem(803, "辣条", "零食", "卫龙", 5.0, 10, "HIGH", 5.0, "含大量盐、糖、添加剂", "水果", 5.0)
    )

    fun getAllItems(): List<ConsumableItem> {
        return addictiveItems + skincareItems + medicineItems + otherItems
    }

    fun getItemsByCategory(category: String): List<ConsumableItem> {
        return getAllItems().filter { it.category == category }
    }

    fun getHighRiskItems(): List<ConsumableItem> {
        return getAllItems().filter { it.healthRisk in listOf("HIGH", "EXTREME") }
    }

    fun getLowValueItems(): List<ConsumableItem> {
        return getAllItems().filter { it.valueScore < 20 }
    }

    fun getBestValueItems(): List<ConsumableItem> {
        return getAllItems().filter { it.valueScore >= 80 }
    }

    fun getAlternatives(itemId: Int): ConsumableItem? {
        val item = getAllItems().find { it.id == itemId }
        return item?.let { altName ->
            getAllItems().find { it.name == altName.alternative }
        }
    }

    /**
     * 计算年消费总成本（薪俸小时）
     */
    fun calculateAnnualCost(dailyCost: Double, healthRisk: String): Double {
        val riskMultiplier = when (healthRisk) {
            "EXTREME" -> 10.0
            "HIGH" -> 5.0
            "MEDIUM" -> 2.0
            else -> 1.0
        }
        val annualMoneyCost = dailyCost * 365
        val healthCost = annualMoneyCost * riskMultiplier
        return (annualMoneyCost + healthCost) / 28.4 // 按时薪28.4元计算
    }

    /**
     * 计算价值密度
     */
    fun calculateValueDensity(price: Double, usageCount: Int, healthRisk: String): Double {
        val riskMultiplier = when (healthRisk) {
            "EXTREME" -> 10.0
            "HIGH" -> 5.0
            "MEDIUM" -> 2.0
            else -> 1.0
        }
        
        val salaryCost = price / 28.4
        val healthCost = salaryCost * riskMultiplier
        val totalCost = salaryCost + healthCost
        
        return if (totalCost == 0.0) 0.0 else usageCount / totalCost
    }
}
