package com.example.townapp.data

/**
 * 饮食系统 —— 手动挑选三餐食材，食材附带体质参数
 *
 * 周末规划界面、工作日晚间弹窗开放三餐选择面板。
 * 食材绑定属性：长期重油外卖 → 疲惫升高、肠胃患病概率上涨；
 * 坚持粗粮蔬果 → 体质稳步提升；甜食过多 → 小幅降低睡眠质量。
 *
 * 玩家可设置长期饮食习惯（如每周3次外卖），自动执行，不用每天重复操作。
 */
object DietSystem {

    /** 食材类别 */
    enum class FoodCategory(val label: String) {
        STAPLE("主食"), VEGETABLE("蔬果"), MEAT("肉类"),
        DAIRY("乳制品"), SNACK("零食甜品"), BEVERAGE("饮品"),
        FAST_FOOD("外卖快餐"), SEAFOOD("海鲜水产")
    }

    /** 单种食材 */
    data class FoodItem(
        val id: String,
        val name: String,
        val category: FoodCategory,
        val cost: Double,               // 花费（元）
        val satiety: Double,            // 饱腹度（0-100）
        val healthDelta: Double,        // 健康影响（正=有益，负=有害）
        val fatigueDelta: Double,       // 疲惫影响（正=加重，负=缓解）
        val sleepDelta: Double,         // 睡眠影响（正=改善，负=恶化）
        val gutHealthDelta: Double,     // 肠胃影响（正=改善，负=损害）
        val joyDelta: Double,           // 心情影响（正=愉悦）
        val seasonTag: String = "",     // 季节标签（"summer"/"winter"）
        val description: String
    )

    // ============================================
    // 食材库
    // ============================================

    val allFoods: List<FoodItem> = listOf(
        // ── 主食类 ──
        FoodItem("soba", "荞麦面", FoodCategory.STAPLE, 12.0, 60.0, 0.3, -0.1, 0.1, 0.2, 0.1, "summer",
            "低GI粗粮，夏季清凉主食，对肠胃负担小"),
        FoodItem("brown_rice", "糙米饭", FoodCategory.STAPLE, 8.0, 65.0, 0.2, -0.05, 0.0, 0.1, 0.0, "",
            "粗粮主食，饱腹感强，营养均衡"),
        FoodItem("white_rice", "白米饭", FoodCategory.STAPLE, 2.0, 55.0, 0.0, 0.0, 0.0, 0.0, 0.0, "",
            "基础主食，无功无过"),
        FoodItem("steamed_bun", "馒头", FoodCategory.STAPLE, 1.0, 50.0, 0.0, 0.0, 0.0, 0.0, 0.0, "",
            "传统面食，最便宜的饱腹选择"),
        FoodItem("noodles", "热汤面", FoodCategory.STAPLE, 6.0, 55.0, 0.1, 0.0, 0.0, 0.0, 0.2, "winter",
            "冬季暖身主食，一碗下肚暖意融融"),

        // ── 蔬果类 ──
        FoodItem("salad", "时蔬沙拉", FoodCategory.VEGETABLE, 15.0, 30.0, 0.4, -0.1, 0.2, 0.3, 0.1, "summer",
            "新鲜蔬菜，轻食健康，夏季首选"),
        FoodItem("stir_fry_veg", "清炒时蔬", FoodCategory.VEGETABLE, 10.0, 35.0, 0.3, 0.0, 0.1, 0.2, 0.0, "",
            "家常炒菜，荤素搭配的日常选择"),
        FoodItem("fruit_platter", "水果拼盘", FoodCategory.VEGETABLE, 12.0, 25.0, 0.3, 0.0, 0.1, 0.1, 0.3, "",
            "新鲜水果，补充维生素，心情愉悦"),
        FoodItem("pickled_veg", "腌菜小碟", FoodCategory.VEGETABLE, 3.0, 10.0, -0.1, 0.0, 0.0, -0.1, 0.0, "",
            "腌制蔬菜，省钱但营养偏低"),

        // ── 肉类 ──
        FoodItem("steamed_fish", "清蒸鱼", FoodCategory.MEAT, 25.0, 50.0, 0.4, 0.0, 0.2, 0.2, 0.2, "",
            "高蛋白低脂肪，营养好吸收"),
        FoodItem("braised_pork", "红烧肉", FoodCategory.MEAT, 20.0, 60.0, -0.2, 0.3, -0.1, -0.2, 0.3, "",
            "肥腻美味，吃多了肠胃负担重"),
        FoodItem("chicken_breast", "鸡胸肉", FoodCategory.MEAT, 15.0, 45.0, 0.3, 0.0, 0.1, 0.1, 0.0, "",
            "健身标配，高蛋白低脂"),
        FoodItem("egg_dish", "鸡蛋料理", FoodCategory.MEAT, 5.0, 35.0, 0.2, 0.0, 0.0, 0.1, 0.0, "",
            "实惠营养，日常必备"),

        // ── 乳制品 ──
        FoodItem("milk", "纯牛奶", FoodCategory.DAIRY, 6.0, 20.0, 0.2, 0.0, 0.3, 0.1, 0.0, "",
            "助眠补钙，睡前一杯睡得香"),
        FoodItem("yogurt", "酸奶", FoodCategory.DAIRY, 8.0, 15.0, 0.3, 0.0, 0.1, 0.3, 0.1, "",
            "益生菌调理肠胃，酸甜可口"),

        // ── 零食甜品 ──
        FoodItem("cake", "蛋糕甜点", FoodCategory.SNACK, 20.0, 25.0, -0.2, 0.1, -0.2, 0.0, 0.4, "",
            "甜食带来短暂快乐，但影响睡眠和健康"),
        FoodItem("nuts", "坚果混合", FoodCategory.SNACK, 15.0, 20.0, 0.2, 0.0, 0.0, 0.0, 0.1, "",
            "健康零食，补充优质脂肪"),
        FoodItem("chips", "薯片", FoodCategory.SNACK, 8.0, 10.0, -0.3, 0.1, 0.0, -0.1, 0.2, "",
            "高油高盐，解馋但不健康"),

        // ── 饮品类 ──
        FoodItem("green_tea", "绿茶", FoodCategory.BEVERAGE, 5.0, 0.0, 0.2, -0.1, 0.0, 0.1, 0.1, "",
            "清心安神，提神不伤胃"),
        FoodItem("bubble_tea", "奶茶", FoodCategory.BEVERAGE, 15.0, 10.0, -0.3, 0.0, -0.2, 0.0, 0.3, "",
            "高糖高热量，快乐但不健康"),
        FoodItem("coffee", "咖啡", FoodCategory.BEVERAGE, 10.0, 0.0, 0.0, -0.3, -0.2, 0.0, 0.1, "",
            "提神醒脑，但过量影响睡眠"),

        // ── 外卖快餐 ──
        FoodItem("burger_set", "汉堡套餐", FoodCategory.FAST_FOOD, 30.0, 70.0, -0.4, 0.3, -0.1, -0.3, 0.3, "",
            "高热量快餐，方便但长期吃损害健康"),
        FoodItem("fried_chicken", "炸鸡", FoodCategory.FAST_FOOD, 25.0, 60.0, -0.4, 0.2, -0.1, -0.3, 0.3, "",
            "油炸食品，偶尔解馋可以，常吃伤身"),
        FoodItem("takeout_box", "盒饭外卖", FoodCategory.FAST_FOOD, 18.0, 55.0, -0.2, 0.1, 0.0, -0.1, 0.0, "",
            "普通外卖，品质参差不齐"),
        FoodItem("instant_noodle", "泡面", FoodCategory.FAST_FOOD, 3.0, 40.0, -0.3, 0.1, 0.0, -0.2, 0.0, "",
            "最便宜的选择，但几乎没有营养"),

        // ── 海鲜水产 ──
        FoodItem("shrimp", "白灼虾", FoodCategory.SEAFOOD, 30.0, 35.0, 0.4, 0.0, 0.1, 0.2, 0.2, "",
            "高蛋白海鲜，营养丰富"),

        // ── 冬季特供 ──
        FoodItem("hotpot", "火锅", FoodCategory.MEAT, 50.0, 80.0, 0.0, 0.2, -0.1, -0.2, 0.5, "winter",
            "冬日聚餐首选，暖身又热闹，但吃多肠胃负担重"),
        FoodItem("congee", "热粥", FoodCategory.STAPLE, 3.0, 40.0, 0.2, 0.0, 0.1, 0.3, 0.1, "winter",
            "冬季暖胃首选，养胃安神")
    )

    // ============================================
    // 一日三餐计划
    // ============================================

    /** 单日饮食计划 */
    data class DailyDietPlan(
        val breakfast: List<FoodItem> = emptyList(),
        val lunch: List<FoodItem> = emptyList(),
        val dinner: List<FoodItem> = emptyList(),
        val snack: FoodItem? = null,       // 午休加餐
        val beverage: FoodItem? = null     // 饮品
    )

    /** 长期饮食习惯设置 */
    data class LongTermDietHabit(
        val fastFoodPerWeek: Int = 0,      // 每周外卖次数
        val preferHealthy: Boolean = false, // 偏好健康饮食
        val preferHearty: Boolean = false,  // 偏好丰盛大餐
        val skipBreakfast: Boolean = false  // 是否经常不吃早餐
    )

    // ============================================
    // 饮食效果计算
    // ============================================

    /**
     * 计算单日饮食的总效果
     */
    fun calculateDailyEffect(plan: DailyDietPlan): DietEffect {
        val allFoods = plan.breakfast + plan.lunch + plan.dinner +
                listOfNotNull(plan.snack, plan.beverage)

        var totalCost = 0.0
        var totalHealth = 0.0
        var totalFatigue = 0.0
        var totalSleep = 0.0
        var totalGut = 0.0
        var totalJoy = 0.0

        allFoods.forEach { food ->
            totalCost += food.cost
            totalHealth += food.healthDelta
            totalFatigue += food.fatigueDelta
            totalSleep += food.sleepDelta
            totalGut += food.gutHealthDelta
            totalJoy += food.joyDelta
        }

        // 不吃早餐惩罚
        if (plan.breakfast.isEmpty()) {
            totalFatigue += 0.3
            totalHealth -= 0.2
        }

        return DietEffect(
            totalCost = totalCost,
            healthDelta = totalHealth.clamp(-1.0, 1.0),
            fatigueDelta = totalFatigue.clamp(-1.0, 1.0),
            sleepDelta = totalSleep.clamp(-1.0, 1.0),
            gutHealthDelta = totalGut.clamp(-1.0, 1.0),
            joyDelta = totalJoy.clamp(-1.0, 1.0)
        )
    }

    data class DietEffect(
        val totalCost: Double,
        val healthDelta: Double,
        val fatigueDelta: Double,
        val sleepDelta: Double,
        val gutHealthDelta: Double,
        val joyDelta: Double
    )

    /**
     * 根据消费观念自动生成一日三餐
     */
    fun autoGenerateDailyDiet(isThrifty: Boolean, isBalanced: Boolean, isWeekend: Boolean): DailyDietPlan {
        val breakfast = if (isThrifty) {
            listOf(allFoods.first { it.id == "steamed_bun" }, allFoods.first { it.id == "milk" })
        } else if (isBalanced) {
            listOf(allFoods.first { it.id == "egg_dish" }, allFoods.first { it.id == "milk" })
        } else {
            listOf(allFoods.first { it.id == "cake" }, allFoods.first { it.id == "coffee" })
        }

        val lunch = if (isThrifty) {
            listOf(allFoods.first { it.id == "white_rice" }, allFoods.first { it.id == "stir_fry_veg" })
        } else if (isBalanced) {
            listOf(allFoods.first { it.id == "brown_rice" }, allFoods.first { it.id == "chicken_breast" },
                allFoods.first { it.id == "stir_fry_veg" })
        } else {
            if (isWeekend) listOf(allFoods.first { it.id == "hotpot" })
            else listOf(allFoods.first { it.id == "burger_set" })
        }

        val dinner = if (isThrifty) {
            listOf(allFoods.first { it.id == "noodles" })
        } else if (isBalanced) {
            listOf(allFoods.first { it.id == "steamed_fish" }, allFoods.first { it.id == "salad" })
        } else {
            listOf(allFoods.first { it.id == "fried_chicken" })
        }

        return DailyDietPlan(
            breakfast = breakfast,
            lunch = lunch,
            dinner = dinner,
            beverage = if (isThrifty) allFoods.first { it.id == "green_tea" }
            else allFoods.first { it.id == "bubble_tea" }
        )
    }
}

private fun Double.clamp(min: Double, max: Double): Double =
    this.coerceIn(min, max)