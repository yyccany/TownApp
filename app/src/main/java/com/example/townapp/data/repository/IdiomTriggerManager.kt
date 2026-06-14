package com.example.townapp.data.repository

import com.example.townapp.data.model.IdiomData
import com.example.townapp.data.model.IdiomRarity

/**
 * 成语触发管理器
 * 根据用户数据自动匹配并触发相关成语卡片
 */
object IdiomTriggerManager {

    /**
     * 触发条件类型常量
     */
    object ConditionTypes {
        const val COUNT = "count"
        const val IDLE_DAYS = "idle_days"
        const val PRICE_RATIO = "price_ratio"
        const val USAGE_RATE = "usage_rate"
        const val STORAGE_DAYS = "storage_days"
        const val EXPIRY_DAYS = "expiry_days"
        const val IMPULSE_RATIO = "impulse_ratio"
        const val GIFT_PURCHASE = "gift_purchase"
        const val JUNK_FOOD_RATIO = "junk_food_ratio"
        const val LEARNING_STAGNATION = "learning_stagnation"
        const val GAMBLING_BIAS = "gambling_bias"
        const val IMPATIENCE_SCORE = "impatience_score"
        const val ECHO_CHAMBER = "echo_chamber"
    }

    /**
     * 模块常量
     */
    object Modules {
        const val FOOD = "food"
        const val CLOTHING = "clothing"
        const val HOUSING = "housing"
        const val COGNITIVE = "cognitive"
    }

    /**
     * 获取用户衣物数据触发的成语
     */
    fun getClothingTriggeredIdioms(
        totalCount: Double = 0.0,
        idleDays: Double = 0.0,
        priceRatio: Double = 0.0,
        bagCount: Double = 0.0,
        jewelryCount: Double = 0.0,
        shoesHealthCost: Double = 0.0,
        sockCount: Double = 0.0,
        underwearCount: Double = 0.0,
        accessoryCount: Double = 0.0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 多多益善：衣物数量超过阈值
        if (totalCount > 30) {
            IdiomRepository.getIdiomById(1)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "item_count" to totalCount.toString(),
                            "category" to "衣物",
                            "threshold" to "30",
                            "extra_time" to ((totalCount - 30) * 0.5).toInt().toString(),
                            "yearly_hours" to (((totalCount - 30) * 0.5 * 365) / 60).toInt().toString()
                        )
                    )
                )
            }
        }

        // 敝帚自珍：闲置超过90天
        if (idleDays > 90) {
            IdiomRepository.getIdiomById(2)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "idle_months" to (idleDays / 30).toInt().toString(),
                            "use_count" to "0",
                            "cost_per_use" to "∞"
                        )
                    )
                )
            }
        }

        // 衣锦还乡：价格过高
        if (priceRatio > 5.0) {
            IdiomRepository.getIdiomById(3)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "salary_price" to "${priceRatio}倍"
                        )
                    )
                )
            }
        }

        // 买椟还珠：包包过多
        if (bagCount > 5.0) {
            IdiomRepository.getIdiomById(18)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "salary_hours" to "352",
                            "use_count" to "50",
                            "cost_per_use" to "200",
                            "value_density" to "0.013",
                            "multiple" to "1700"
                        )
                    )
                )
            }
        }

        // 华而不实：首饰过多
        if (jewelryCount > 3.0) {
            IdiomRepository.getIdiomById(19)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "salary_hours" to "176",
                            "wear_count" to "20",
                            "cost_per_wear" to "250",
                            "value_density" to "0.006"
                        )
                    )
                )
            }
        }

        // 削足适履：鞋子健康成本高
        if (shoesHealthCost > 1.0) {
            IdiomRepository.getIdiomById(20)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "salary_hours" to "70",
                            "health_cost" to "1",
                            "value_density" to "0.15",
                            "multiple" to "190"
                        )
                    )
                )
            }
        }

        // 积少成多：袜子内衣过多
        if (sockCount > 10.0 || underwearCount > 5.0) {
            IdiomRepository.getIdiomById(21)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "sock_count" to "$sockCount",
                            "underwear_count" to "$underwearCount",
                            "used_sock" to "5",
                            "used_underwear" to "3",
                            "unused_ratio" to "90",
                            "space" to "5"
                        )
                    )
                )
            }
        }

        // 画蛇添足：配饰过多
        if (accessoryCount > 5.0) {
            IdiomRepository.getIdiomById(22)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "accessory_count" to "$accessoryCount",
                            "unused_count" to "${accessoryCount - 1}"
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户食物数据触发的成语
     */
    fun getFoodTriggeredIdioms(
        storageDays: Double = 0.0,
        expiryDays: Double = 0.0,
        impulseRatio: Double = 0.0,
        junkFoodRatio: Double = 0.0,
        wildFood: Double = 0.0,
        highOil: Double = 0.0,
        overcooked: Double = 0.0,
        artificialUmami: Double = 0.0,
        hotFood: Double = 0.0,
        leftover: Double = 0.0,
        overSpend: Double = 0.0,
        overeating: Double = 0.0,
        cuisineSichuan: Double = 0.0,
        cuisineShandong: Double = 0.0,
        cuisineGuangdong: Double = 0.0,
        cuisineJiangsu: Double = 0.0,
        cuisineHunan: Double = 0.0,
        cuisineZhejiang: Double = 0.0,
        cuisineFujian: Double = 0.0,
        cuisineAnhui: Double = 0.0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 多多益善：库存过多
        if (storageDays > 7) {
            IdiomRepository.getIdiomById(1)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "item_count" to "${storageDays.toInt()}天",
                            "category" to "食物",
                            "threshold" to "7",
                            "extra_time" to "5",
                            "yearly_hours" to "30"
                        )
                    )
                )
            }
        }

        // 因小失大：快过期
        if (expiryDays < 3) {
            IdiomRepository.getIdiomById(5)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "food_name" to "快过期食品",
                            "expiry_days" to expiryDays.toInt().toString(),
                            "saved_money" to "5",
                            "potential_cost" to "500"
                        )
                    )
                )
            }
        }

        // 买椟还珠：冲动消费
        if (impulseRatio > 0.3) {
            IdiomRepository.getIdiomById(4)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "discount_amount" to "100",
                            "extra_items" to "${(impulseRatio * 10).toInt()}",
                            "extra_cost" to "${(impulseRatio * 200).toInt()}"
                        )
                    )
                )
            }
        }

        // 掩耳盗铃：垃圾食品过多
        if (junkFoodRatio > 0.3) {
            IdiomRepository.getIdiomById(6)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "late_nights" to "${(junkFoodRatio * 7).toInt()}",
                            "skincare_cost" to "200"
                        )
                    )
                )
            }
        }

        // 山珍海味：野生特供
        if (wildFood > 1.0) {
            IdiomRepository.getIdiomById(11)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        emptyMap()
                    )
                )
            }
        }

        // 油润鲜香：高油饮食
        if (highOil > 1.0) {
            IdiomRepository.getIdiomById(12)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        emptyMap()
                    )
                )
            }
        }

        // 精益求精：过度烹饪
        if (overcooked > 1.0) {
            IdiomRepository.getIdiomById(13)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        emptyMap()
                    )
                )
            }
        }

        // 原汁原味：人工鲜味
        if (artificialUmami > 1.0) {
            IdiomRepository.getIdiomById(14)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        emptyMap()
                    )
                )
            }
        }

        // 趁热打铁：热食
        if (hotFood > 65.0) {
            IdiomRepository.getIdiomById(15)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "salary_hours" to "0.53",
                            "health_cost" to "0.81"
                        )
                    )
                )
            }
        }

        // 因小失大：隔夜菜
        if (leftover > 1.0) {
            IdiomRepository.getIdiomById(16)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "saved_money" to "5",
                            "health_cost" to "0.28",
                            "hourly_wage" to "28",
                            "actual_cost" to "8"
                        )
                    )
                )
            }
        }

        // 得不偿失：超额消费或吃撑
        if (overSpend > 30.0 || overeating > 2.0) {
            IdiomRepository.getIdiomById(17)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "min_spend" to "200",
                            "extra_spend" to "50",
                            "extra_calories" to "800",
                            "total_hours" to "7.46",
                            "wage_percent" to "93"
                        )
                    )
                )
            }
        }

        // 油尽灯枯：川菜
        if (cuisineSichuan > 0.0) {
            IdiomRepository.getIdiomById(23)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "oil_used" to "250",
                            "days" to "8-12",
                            "salary_hours" to "4.51",
                            "health_cost" to "10.15",
                            "value_density" to "17.1"
                        )
                    )
                )
            }
        }

        // 食不厌精：鲁菜
        if (cuisineShandong > 0.0) {
            IdiomRepository.getIdiomById(24)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "sugar_used" to "50",
                            "cubes" to "12",
                            "cholesterol" to "300",
                            "value_density" to "7.1"
                        )
                    )
                )
            }
        }

        // 名不副实：粤菜
        if (cuisineGuangdong > 0.0) {
            IdiomRepository.getIdiomById(25)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "abalone_cost" to "5",
                            "seasoning_cost" to "20",
                            "price" to "88",
                            "value_density" to "24.8"
                        )
                    )
                )
            }
        }

        // 津津有味：湘菜
        if (cuisineHunan > 0.0) {
            IdiomRepository.getIdiomById(26)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "salt_used" to "10-12",
                            "days" to "1.5",
                            "total_salt" to "30"
                        )
                    )
                )
            }
        }

        // 脍炙人口：浙菜
        if (cuisineZhejiang > 0.0) {
            IdiomRepository.getIdiomById(27)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "fat" to "60",
                            "days" to "1",
                            "sugar" to "40",
                            "cubes" to "10"
                        )
                    )
                )
            }
        }

        // 回味无穷：徽菜
        if (cuisineAnhui > 0.0) {
            IdiomRepository.getIdiomById(28)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "salt_used" to "15-20",
                            "days" to "2-3"
                        )
                    )
                )
            }
        }

        // 富丽堂皇：苏菜
        if (cuisineJiangsu > 0.0) {
            IdiomRepository.getIdiomById(29)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "oil_used" to "200",
                            "sugar_used" to "60",
                            "cubes" to "15",
                            "value_density" to "3.9"
                        )
                    )
                )
            }
        }

        // 珠光宝气：闽菜
        if (cuisineFujian > 0.0) {
            IdiomRepository.getIdiomById(30)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "price" to "598",
                            "cheap_price" to "20",
                            "cholesterol" to "500",
                            "days" to "2"
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户认知数据触发的成语
     */
    fun getCognitiveTriggeredIdioms(
        learningStagnation: Double = 0.0,
        gamblingBias: Double = 0.0,
        impatienceScore: Double = 0.0,
        echoChamber: Double = 0.0,
        screenTime: Double = 0.0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 刻舟求剑：学习停滞
        if (learningStagnation > 180) {
            IdiomRepository.getIdiomById(7)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "old_method" to "老方法"
                        )
                    )
                )
            }
        }

        // 守株待兔：赌博倾向
        if (gamblingBias > 2.0) {
            IdiomRepository.getIdiomById(8)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "platform" to "投资平台",
                            "luck_factor" to "运气",
                            "money_earned" to "1000"
                        )
                    )
                )
            }
        }

        // 揠苗助长：急躁得分
        if (impatienceScore > 0.7) {
            IdiomRepository.getIdiomById(9)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "unrealistic_goal" to "不切实际的目标",
                            "short_time" to "太短的时间"
                        )
                    )
                )
            }
        }

        // 井底之蛙：信息茧房
        if (echoChamber > 0.8 || screenTime > 5) {
            IdiomRepository.getIdiomById(10)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "screen_time" to screenTime.toString()
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户住房数据触发的成语
     */
    fun getHousingTriggeredIdioms(
        basement: Double = 0.0,
        loanRatio: Double = 0.0,
        commuteTime: Double = 0.0,
        decorationStyle: String = "毛坯房",
        idleItemsCount: Int = 0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 穴居野处：地下室
        if (basement > 0.0) {
            IdiomRepository.getIdiomById(31)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "annual_cost" to "1910",
                            "multiple" to "2.5",
                            "saved_rent" to "200",
                            "extra_hours" to "1157",
                            "months" to "7"
                        )
                    )
                )
            }
        }

        // 作茧自缚：高贷款比例
        if (loanRatio > 70.0) {
            IdiomRepository.getIdiomById(32)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "price" to "500",
                            "total_cost" to "788",
                            "years" to "50.7",
                            "rent_years" to "30",
                            "rent_cost" to "180",
                            "diff_cost" to "608"
                        )
                    )
                )
            }
        }

        // 舍本逐末：通勤时间过长
        if (commuteTime > 2.0) {
            IdiomRepository.getIdiomById(33)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "commute_hours" to commuteTime.toString(),
                            "yearly_hours" to "${(commuteTime * 250).toInt()}",
                            "work_days" to "${(commuteTime * 250 / 8).toInt()}",
                            "percent" to "${(commuteTime / 10 * 100).toInt()}"
                        )
                    )
                )
            }
        }

        // 删繁就简：日式极简
        if (decorationStyle == "日式极简" && idleItemsCount <= 10) {
            IdiomRepository.getIdiomById(58)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "cost" to "150000",
                            "style" to "日式极简",
                            "density" to "10.8",
                            "better_style" to "欧式豪华",
                            "better_density" to "0.7",
                            "multiple" to "15"
                        )
                    )
                )
            }
        }

        // 精益求精：高品质设计
        if (decorationStyle == "日式极简" || decorationStyle == "现代简约") {
            IdiomRepository.getIdiomById(59)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "disney_rate" to "80",
                            "our_rate" to "10"
                        )
                    )
                )
            }
        }

        // 华而不实：欧式豪华
        if (decorationStyle == "欧式豪华") {
            IdiomRepository.getIdiomById(60)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "style" to "欧式豪华",
                            "utilization" to "40",
                            "density" to "0.7",
                            "maintenance" to "10000"
                        )
                    )
                )
            }
        }

        // 哗众取宠：网红ins风
        if (decorationStyle == "网红ins风") {
            IdiomRepository.getIdiomById(61)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "item" to "泡泡玛特/网红装饰",
                            "cost" to "5000",
                            "days" to "90",
                            "daily_cost" to "56"
                        )
                    )
                )
            }
        }

        // 堆积如山：闲置物品过多
        if (idleItemsCount >= 50) {
            IdiomRepository.getIdiomById(62)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "count" to "$idleItemsCount",
                            "space" to "${(idleItemsCount * 0.1).toInt()}",
                            "hours" to "${(idleItemsCount * 0.5).toInt()}",
                            "salary" to "${(idleItemsCount * 0.5 * 28.4).toInt()}"
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户出行数据触发的成语
     */
    fun getTransportTriggeredIdioms(
        carOwnership: Double = 0.0,
        dailyCommuteTime: Double = 0.0,
        hourlyWage: Double = 0.0,
        taxiSavings: Double = 0.0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 杯水车薪：买车
        if (carOwnership > 0.0) {
            IdiomRepository.getIdiomById(34)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "car_price" to "10",
                            "years" to "10",
                            "total_cost" to "30",
                            "monthly_cost" to "2507",
                            "taxi_cost" to "1600"
                        )
                    )
                )
            }
        }

        // 争分夺秒：时间价值
        if (hourlyWage > 0.0 && taxiSavings > 0.0) {
            val savedEarned = hourlyWage * taxiSavings
            val profit = savedEarned - 25 // 假设打车费25元
            IdiomRepository.getIdiomById(35)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "hourly_wage" to hourlyWage.toString(),
                            "saved_time" to taxiSavings.toString(),
                            "earned" to savedEarned.toString(),
                            "taxi_fee" to "25",
                            "profit" to profit.toString()
                        )
                    )
                )
            }
        }

        // 积少成多：通勤时间过长（复用已有的）
        if (dailyCommuteTime > 2.0) {
            IdiomRepository.getIdiomById(21)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "daily_time" to dailyCommuteTime.toString(),
                            "yearly_hours" to "${(dailyCommuteTime * 250).toInt()}",
                            "work_days" to "${(dailyCommuteTime * 250 / 8).toInt()}",
                            "lifetime_years" to "${(dailyCommuteTime * 250 * 30 / 8 / 220).toInt()}"
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户消费品数据触发的成语
     */
    fun getConsumableTriggeredIdioms(
        smoking: Double = 0.0,
        drinking: Double = 0.0,
        sugaryDrinks: Double = 0.0,
        skincareSpending: Double = 0.0,
        chineseMedicine: Double = 0.0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 饮鸩止渴：吸烟
        if (smoking > 0.0) {
            IdiomRepository.getIdiomById(36)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "cigarettes" to "${smoking.toInt()}",
                            "annual_cost" to "${(smoking * 20 * 365 / 20).toInt()}",
                            "salary_hours" to "${((smoking * 20 * 365) / 28.4).toInt()}",
                            "life_years" to "10",
                            "cancer_risk" to "20"
                        )
                    )
                )
            }
        }

        // 借酒消愁：饮酒
        if (drinking > 0.0) {
            IdiomRepository.getIdiomById(37)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "drinks" to "${drinking.toInt()}",
                            "annual_cost" to "${(drinking * 200 * 52).toInt()}",
                            "salary_hours" to "${((drinking * 200 * 52) / 28.4).toInt()}"
                        )
                    )
                )
            }
        }

        // 糖衣炮弹：含糖饮料
        if (sugaryDrinks > 0.0) {
            IdiomRepository.getIdiomById(38)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "drinks" to "${sugaryDrinks.toInt()}",
                            "annual_cost" to "${(sugaryDrinks * 15 * 365).toInt()}",
                            "salary_hours" to "${((sugaryDrinks * 15 * 365) / 28.4).toInt()}",
                            "diabetes_risk" to "26",
                            "heart_risk" to "19"
                        )
                    )
                )
            }
        }

        // 金玉其外：护肤品
        if (skincareSpending > 500.0) {
            IdiomRepository.getIdiomById(39)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "price" to "$skincareSpending",
                            "value_density" to "0.003"
                        )
                    )
                )
            }
        }

        // 讳疾忌医：中药
        if (chineseMedicine > 0.0) {
            IdiomRepository.getIdiomById(40)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "price" to "$chineseMedicine"
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户消费品进阶数据触发的成语（保健品、母婴、数码、会员、小家电）
     */
    fun getConsumableAdvancedTriggeredIdioms(
        healthSupplements: Double = 0.0,
        babyProducts: Double = 0.0,
        originalAccessories: Double = 0.0,
        subscriptions: Double = 0.0,
        smallAppliances: Double = 0.0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 灵丹妙药：保健品
        if (healthSupplements > 500.0) {
            IdiomRepository.getIdiomById(41)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "annual_cost" to "${healthSupplements.toInt()}",
                            "salary_hours" to "${(healthSupplements / 28.4).toInt()}",
                            "years" to "${(healthSupplements / 365 / 5).toInt()}"
                        )
                    )
                )
            }
        }

        // 爱子心切：母婴用品
        if (babyProducts > 500.0) {
            IdiomRepository.getIdiomById(42)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "monthly_cost" to "${babyProducts.toInt()}",
                            "waste" to "${(babyProducts * 0.8).toInt()}",
                            "yearly_waste" to "${(babyProducts * 0.8 * 12).toInt()}",
                            "salary_hours" to "${(babyProducts * 0.8 * 12 / 28.4).toInt()}"
                        )
                    )
                )
            }
        }

        // 买椟还珠：数码配件
        if (originalAccessories > 100.0) {
            IdiomRepository.getIdiomById(43)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "price" to "${originalAccessories.toInt()}",
                            "cheap_price" to "${(originalAccessories * 0.2).toInt()}",
                            "extra" to "${(originalAccessories * 0.8).toInt()}",
                            "hours" to "${(originalAccessories * 0.8 / 28.4).toInt()}"
                        )
                    )
                )
            }
        }

        // 积少成多：会员订阅
        if (subscriptions > 5.0) {
            IdiomRepository.getIdiomById(44)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "monthly_cost" to "${(subscriptions * 25).toInt()}",
                            "waste" to "${(subscriptions * 20).toInt()}",
                            "yearly_waste" to "${(subscriptions * 240).toInt()}",
                            "salary_hours" to "${(subscriptions * 240 / 28.4).toInt()}"
                        )
                    )
                )
            }
        }

        // 叶公好龙：小家电
        if (smallAppliances > 3.0) {
            IdiomRepository.getIdiomById(45)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "count" to "${smallAppliances.toInt()}",
                            "total_cost" to "${(smallAppliances * 600).toInt()}",
                            "uses" to "${smallAppliances.toInt() * 5}",
                            "cost_per_use" to "${(smallAppliances * 600 / (smallAppliances * 5)).toInt()}"
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户精神内容数据触发的成语（修仙网文、短视频、直播等）
     */
    fun getMentalTriggeredIdioms(
        dailyNovelHours: Double = 0.0,
        novelInconsistency: Double = 0.0,
        novelDurationDays: Double = 0.0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 掩耳盗铃：重度沉迷修仙网文
        if (dailyNovelHours >= 3.0) {
            IdiomRepository.getIdiomById(46)?.let { idiom ->
                val annualHours = dailyNovelHours * 365
                val mentalCostHours = annualHours * 2.0
                val totalHours = annualHours + mentalCostHours
                val valueDensity = dailyNovelHours * 0.002 / (dailyNovelHours * 2.0)
                
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "daily_hours" to "${dailyNovelHours.toInt()}",
                            "annual_hours" to "${totalHours.toInt()}",
                            "value_density" to "%.4f".format(valueDensity)
                        )
                    )
                )
            }
        }

        // 空中楼阁：剧情逻辑崩坏
        if (novelInconsistency >= 3.0) {
            IdiomRepository.getIdiomById(47)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "info_ratio" to "5"
                        )
                    )
                )
            }
        }

        // 原地踏步：长期追读换地图爽文
        if (novelDurationDays >= 90.0) {
            IdiomRepository.getIdiomById(48)?.let { idiom ->
                val totalHours = dailyNovelHours * novelDurationDays
                val workDays = totalHours / 8.0
                
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "total_hours" to "${totalHours.toInt()}",
                            "work_days" to "${workDays.toInt()}"
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户四大精神鸦片数据触发的成语（短视频、直播打赏、玄学算命、网游氪金）
     */
    fun getMentalOpiumTriggeredIdioms(
        dailyShortVideoHours: Double = 0.0,
        monthlyLiveDonation: Double = 0.0,
        yearlySuperstitionSpending: Double = 0.0,
        monthlyGameSpending: Double = 0.0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 玩物丧志：短视频
        if (dailyShortVideoHours >= 2.0) {
            IdiomRepository.getIdiomById(49)?.let { idiom ->
                val annualTimeHours = dailyShortVideoHours * 365
                val mentalCostHours = annualTimeHours * 2.5
                val totalHours = annualTimeHours + mentalCostHours
                val years = totalHours / 2000.0
                
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "daily_hours" to "${dailyShortVideoHours.toInt()}",
                            "annual_hours" to "${totalHours.toInt()}",
                            "years" to "%.1f".format(years),
                            "decade_years" to "${(years * 10).toInt()}"
                        )
                    )
                )
            }
        }

        // 千金买笑：直播打赏
        if (monthlyLiveDonation >= 100.0) {
            IdiomRepository.getIdiomById(50)?.let { idiom ->
                val yearlyCost = monthlyLiveDonation * 12
                val yearlyHours = yearlyCost / 28.4
                val timeCostHours = 2.0 * 365
                val mentalCostHours = timeCostHours * 3.0
                val totalHours = yearlyHours + timeCostHours + mentalCostHours
                val years = totalHours / 2000.0
                
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "monthly_cost" to "${monthlyLiveDonation.toInt()}",
                            "yearly_cost" to "${yearlyCost.toInt()}",
                            "yearly_hours" to "${timeCostHours.toInt()}",
                            "total_hours" to "${totalHours.toInt()}",
                            "years" to "%.1f".format(years)
                        )
                    )
                )
            }
        }

        // 听天由命：玄学算命
        if (yearlySuperstitionSpending >= 500.0) {
            IdiomRepository.getIdiomById(51)?.let { idiom ->
                val moneyHours = yearlySuperstitionSpending / 28.4
                val timeCostHours = 100.0
                val mentalCostHours = timeCostHours * 4.0
                val totalHours = moneyHours + timeCostHours + mentalCostHours
                
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "yearly_cost" to "${yearlySuperstitionSpending.toInt()}",
                            "total_hours" to "${totalHours.toInt()}"
                        )
                    )
                )
            }
        }

        // 水中捞月：网游氪金
        if (monthlyGameSpending >= 300.0) {
            IdiomRepository.getIdiomById(52)?.let { idiom ->
                val yearlyCost = monthlyGameSpending * 12
                val moneyHours = yearlyCost / 28.4
                val timeCostHours = 3.0 * 365
                val mentalCostHours = timeCostHours * 2.5
                val totalHours = moneyHours + timeCostHours + mentalCostHours
                val years = totalHours / 2000.0
                
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "monthly_cost" to "${monthlyGameSpending.toInt()}",
                            "yearly_cost" to "${yearlyCost.toInt()}",
                            "total_hours" to "${totalHours.toInt()}",
                            "years" to "%.1f".format(years)
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户学习方向数据触发的成语（历史沉迷、传统文化糟粕、非遗守旧）
     */
    fun getLearningTriggeredIdioms(
        dailyHistoryHours: Double = 0.0,
        dailyTraditionalCultureHours: Double = 0.0,
        craftAdvocate: Double = 0.0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 刻舟求剑：历史沉迷
        if (dailyHistoryHours >= 3.0) {
            IdiomRepository.getIdiomById(53)?.let { idiom ->
                val annualTimeHours = dailyHistoryHours * 365
                val mentalCostHours = annualTimeHours * 1.5
                val totalHours = annualTimeHours + mentalCostHours
                val valueDensity = dailyHistoryHours * 0.1 / (dailyHistoryHours * 1.5)
                val multiple = (5.0 / valueDensity).toInt()
                
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "daily_hours" to "${dailyHistoryHours.toInt()}",
                            "annual_hours" to "${totalHours.toInt()}",
                            "value_density" to "%.2f".format(valueDensity),
                            "multiple" to "$multiple"
                        )
                    )
                )
            }
        }

        // 抱残守缺：传统文化糟粕
        if (dailyTraditionalCultureHours >= 2.0) {
            IdiomRepository.getIdiomById(54)?.let { idiom ->
                val annualTimeHours = dailyTraditionalCultureHours * 365
                val mentalCostHours = annualTimeHours * 3.0
                val totalHours = annualTimeHours + mentalCostHours
                val valueDensity = dailyTraditionalCultureHours * 0.01 / (dailyTraditionalCultureHours * 3.0)
                
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "daily_hours" to "${dailyTraditionalCultureHours.toInt()}",
                            "annual_hours" to "${totalHours.toInt()}",
                            "value_density" to "%.4f".format(valueDensity)
                        )
                    )
                )
            }
        }

        // 固步自封：非遗守旧
        if (craftAdvocate >= 1.0) {
            IdiomRepository.getIdiomById(55)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "efficiency_ratio" to "1",
                            "value_ratio" to "1",
                            "time" to "一天"
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户死亡语言学习数据触发的成语（文言文、拉丁语）
     */
    fun getDeadLanguageTriggeredIdioms(
        classicalChineseAdvocate: Double = 0.0,
        latinAdvocate: Double = 0.0
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 食古不化：文言文
        if (classicalChineseAdvocate >= 1.0) {
            IdiomRepository.getIdiomById(56)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "total_years" to "4.5",
                            "value_density" to "0.001"
                        )
                    )
                )
            }
        }

        // 邯郸学步：拉丁语
        if (latinAdvocate >= 1.0) {
            IdiomRepository.getIdiomById(57)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "total_years" to "2.25",
                            "value_density" to "0.001"
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取用户伪科学相关数据触发的成语（三伏天晒背、节气迷信、星象占卜等）
     */
    fun getPseudoscienceTriggeredIdioms(
        sunExposureHours: Double = 0.0,
        solarTermTaboos: Int = 0,
        astrologyHours: Double = 0.0,
        pseudoscienceSpending: Double = 0.0,
        userAge: Int = 30
    ): List<Pair<IdiomData, Map<String, String>>> {
        val results = mutableListOf<Pair<IdiomData, Map<String, String>>>()

        // 过犹不及：三伏天晒背
        if (sunExposureHours > 1.0) {
            IdiomRepository.getIdiomById(67)?.let { idiom ->
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "daily_hours" to "$sunExposureHours"
                        )
                    )
                )
            }
        }

        // 食古不化：节气迷信
        if (solarTermTaboos > 3) {
            IdiomRepository.getIdiomById(68)?.let { idiom ->
                val yearlyHours = solarTermTaboos * 365.0
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "taboo_count" to "$solarTermTaboos",
                            "yearly_hours" to "$yearlyHours"
                        )
                    )
                )
            }
        }

        // 牵强附会：星象占卜
        if (astrologyHours > 0.5) {
            IdiomRepository.getIdiomById(69)?.let { idiom ->
                val yearlyHours = astrologyHours * 365.0
                val moneyCost = yearlyHours * 5.0 // 假设每小时花费5元
                val mentalCostHours = yearlyHours * 3.0
                val totalHours = yearlyHours + mentalCostHours
                val valueDensity = 0.0005
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "daily_hours" to "$astrologyHours",
                            "yearly_hours" to "$yearlyHours",
                            "value_density" to "%.4f".format(valueDensity)
                        )
                    )
                )
            }
        }

        // 上当受骗：伪科学消费
        if (pseudoscienceSpending > 500.0) {
            IdiomRepository.getIdiomById(70)?.let { idiom ->
                val moneyHours = pseudoscienceSpending / 28.4
                val timeHours = 100.0
                val mentalHours = timeHours * 2.0
                val totalHours = moneyHours + timeHours + mentalHours
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "money" to "$pseudoscienceSpending",
                            "hours" to "$totalHours",
                            "salary" to "$moneyHours"
                        )
                    )
                )
            }
        }

        // 未雨绸缪：年龄大于40岁时触发，且不是伪科学的，这里先检查是否有正向的触发，我们这里先不触发这个，先触发其他的成语。
        if (userAge >= 40) {
            IdiomRepository.getIdiomById(63)?.let { idiom ->
                val yearsLeft = 60 - userAge
                val targetSavings = 25 * 12 * 1000.0
                val monthlySavingsNeeded = if (yearsLeft > 0) targetSavings / (yearsLeft * 12) else 0.0
                results.add(
                    Pair(
                        idiom,
                        mapOf(
                            "age" to "$userAge",
                            "years_left" to "$yearsLeft"
                        )
                    )
                )
            }
        }

        return results
    }

    /**
     * 获取所有可用成语（用于认知页面展示）
     */
    fun getAllIdiomsWithEmptyData(): List<Pair<IdiomData, Map<String, String>>> {
        return IdiomRepository.getAllIdioms().map { Pair(it, emptyMap()) }
    }

    /**
     * 根据稀有度获取成语
     */
    fun getIdiomsByRarity(rarity: IdiomRarity): List<Pair<IdiomData, Map<String, String>>> {
        return IdiomRepository.getIdiomsByRarity(rarity).map { Pair(it, emptyMap()) }
    }

    /**
     * 获取推荐的成语（随机选择3-5个）
     */
    fun getRecommendedIdioms(count: Int = 3): List<Pair<IdiomData, Map<String, String>>> {
        val allIdioms = IdiomRepository.getAllIdioms()
        return allIdioms.shuffled().take(count).map { Pair(it, emptyMap()) }
    }

    /**
     * 根据ID获取成语
     */
    fun getIdiomById(id: Int): Pair<IdiomData, Map<String, String>>? {
        return IdiomRepository.getIdiomById(id)?.let { Pair(it, emptyMap()) }
    }
}