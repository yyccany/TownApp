package com.example.townapp.data

// ============================================
// 🥗 食品营养数据 (IDs 1-20 from TownViewModel)
// ============================================
val foodNutritionMap: Map<Int, FoodNutrition> = buildMap {
    // ===== IDs 1-20 (TownViewModel 基础食材) =====
    put(1, FoodNutrition(1, protein = 13.3, fat = 8.8, carbohydrate = 2.8, sugar = 0.3, fiber = 0.0, calorie = 144.0, vitaminScore = 75.0, mineralScore = 60.0))
    put(2, FoodNutrition(2, protein = 24.6, fat = 1.9, carbohydrate = 0.0, sugar = 0.0, fiber = 0.0, calorie = 118.0, vitaminScore = 40.0, mineralScore = 35.0))
    put(3, FoodNutrition(3, protein = 2.8, fat = 0.4, carbohydrate = 7.0, sugar = 1.7, fiber = 2.6, calorie = 34.0, vitaminScore = 90.0, mineralScore = 65.0))
    put(4, FoodNutrition(4, protein = 7.0, fat = 0.8, carbohydrate = 78.0, sugar = 0.5, fiber = 1.3, calorie = 345.0, vitaminScore = 10.0, mineralScore = 15.0))
    put(5, FoodNutrition(5, protein = 7.0, fat = 25.0, carbohydrate = 60.0, sugar = 2.0, fiber = 2.0, calorie = 450.0, vitaminScore = 5.0, mineralScore = 10.0))
    put(6, FoodNutrition(6, protein = 2.6, fat = 0.3, carbohydrate = 25.9, sugar = 0.1, fiber = 0.3, calorie = 116.0, vitaminScore = 5.0, mineralScore = 8.0))
    put(7, FoodNutrition(7, protein = 7.8, fat = 1.1, carbohydrate = 58.6, sugar = 0.5, fiber = 1.3, calorie = 286.0, vitaminScore = 8.0, mineralScore = 12.0))
    put(8, FoodNutrition(8, protein = 13.3, fat = 9.5, carbohydrate = 2.8, sugar = 0.3, fiber = 0.0, calorie = 143.0, vitaminScore = 72.0, mineralScore = 58.0))
    put(9, FoodNutrition(9, protein = 18.0, fat = 20.0, carbohydrate = 0.0, sugar = 0.0, fiber = 0.0, calorie = 250.0, vitaminScore = 50.0, mineralScore = 55.0))
    put(10, FoodNutrition(10, protein = 1.8, fat = 0.4, carbohydrate = 3.8, sugar = 1.0, fiber = 1.6, calorie = 24.0, vitaminScore = 85.0, mineralScore = 60.0))
    put(11, FoodNutrition(11, protein = 0.9, fat = 0.2, carbohydrate = 4.0, sugar = 2.6, fiber = 1.2, calorie = 18.0, vitaminScore = 80.0, mineralScore = 40.0))
    put(12, FoodNutrition(12, protein = 0.8, fat = 0.2, carbohydrate = 2.9, sugar = 1.4, fiber = 0.5, calorie = 16.0, vitaminScore = 45.0, mineralScore = 30.0))
    put(13, FoodNutrition(13, protein = 0.2, fat = 0.2, carbohydrate = 13.5, sugar = 10.3, fiber = 1.2, calorie = 52.0, vitaminScore = 60.0, mineralScore = 25.0))
    put(14, FoodNutrition(14, protein = 1.4, fat = 0.2, carbohydrate = 22.0, sugar = 12.2, fiber = 1.8, calorie = 91.0, vitaminScore = 65.0, mineralScore = 35.0))
    put(15, FoodNutrition(15, protein = 3.2, fat = 3.6, carbohydrate = 5.0, sugar = 5.0, fiber = 0.0, calorie = 66.0, vitaminScore = 70.0, mineralScore = 80.0))
    put(16, FoodNutrition(16, protein = 2.0, fat = 0.1, carbohydrate = 19.0, sugar = 1.0, fiber = 1.8, calorie = 81.0, vitaminScore = 55.0, mineralScore = 40.0))
    put(17, FoodNutrition(17, protein = 8.1, fat = 4.8, carbohydrate = 4.2, sugar = 0.5, fiber = 0.4, calorie = 81.0, vitaminScore = 30.0, mineralScore = 70.0))
    put(18, FoodNutrition(18, protein = 20.0, fat = 13.0, carbohydrate = 0.0, sugar = 0.0, fiber = 0.0, calorie = 208.0, vitaminScore = 75.0, mineralScore = 65.0))
    put(19, FoodNutrition(19, protein = 1.2, fat = 0.2, carbohydrate = 15.5, sugar = 12.0, fiber = 2.4, calorie = 62.0, vitaminScore = 95.0, mineralScore = 35.0))
    put(20, FoodNutrition(20, protein = 9.0, fat = 5.0, carbohydrate = 52.0, sugar = 5.0, fiber = 2.5, calorie = 290.0, vitaminScore = 15.0, mineralScore = 20.0))

    // ===== IDs 71-199 (CompleteData 扩展食材) =====
    // 使用品类默认营养数据填充
    for (foodItem in completeFoodItems) {
        if (foodItem.id in 71..199 && !containsKey(foodItem.id)) {
            val nutrition = defaultNutritionForCategory(foodItem.category).copy(foodId = foodItem.id)
            put(foodItem.id, nutrition)
        }
    }
}

// ============================================
// ⚠️ 食品风险数据
// ============================================
val foodRiskMap: Map<Int, FoodRisk> = buildMap {
    // ----- IDs 1-20 -----
    // 蛋奶类 / 低风险
    put(1, FoodRisk(1, riskLevel = RiskLevel.LOW))
    // 肉禽 / 中等风险（农残/激素）
    put(2, FoodRisk(2, pesticide = 10.0, riskLevel = RiskLevel.MEDIUM))
    // 蔬菜 / 中等风险（农残）
    put(3, FoodRisk(3, pesticide = 18.0, riskLevel = RiskLevel.MEDIUM))
    // 精制米面 / 低风险
    put(4, FoodRisk(4, riskLevel = RiskLevel.LOW))
    // 薯片 / 高风险（糖油混合物）
    put(5, FoodRisk(5, greaseSugar = 75.0, riskLevel = RiskLevel.HIGH))
    // 米饭 / 低风险
    put(6, FoodRisk(6, riskLevel = RiskLevel.LOW))
    // 馒头 / 低风险
    put(7, FoodRisk(7, riskLevel = RiskLevel.LOW))
    // 水煮蛋 / 低风险
    put(8, FoodRisk(8, riskLevel = RiskLevel.LOW))
    // 牛肉 / 中等风险
    put(9, FoodRisk(9, pesticide = 10.0, riskLevel = RiskLevel.MEDIUM))
    // 青菜 / 中等风险（农残较高）
    put(10, FoodRisk(10, pesticide = 20.0, riskLevel = RiskLevel.MEDIUM))
    // 番茄 / 中等风险
    put(11, FoodRisk(11, pesticide = 15.0, riskLevel = RiskLevel.MEDIUM))
    // 黄瓜 / 中等风险
    put(12, FoodRisk(12, pesticide = 15.0, riskLevel = RiskLevel.MEDIUM))
    // 苹果 / 低风险
    put(13, FoodRisk(13, riskLevel = RiskLevel.LOW))
    // 香蕉 / 低风险
    put(14, FoodRisk(14, riskLevel = RiskLevel.LOW))
    // 牛奶 / 低风险
    put(15, FoodRisk(15, riskLevel = RiskLevel.LOW))
    // 土豆 / 中等风险
    put(16, FoodRisk(16, pesticide = 15.0, riskLevel = RiskLevel.MEDIUM))
    // 豆腐 / 低风险
    put(17, FoodRisk(17, riskLevel = RiskLevel.LOW))
    // 三文鱼 / 中等风险（重金属轻微）
    put(18, FoodRisk(18, heavyMetal = 8.0, pesticide = 5.0, riskLevel = RiskLevel.MEDIUM))
    // 橙子 / 低风险
    put(19, FoodRisk(19, riskLevel = RiskLevel.LOW))
    // 面包 / 低风险
    put(20, FoodRisk(20, riskLevel = RiskLevel.LOW))

    // ----- IDs 71-199 高风险/中等风险项 -----
    // === 饮料零食: 高糖油风险 ===
    put(129, FoodRisk(129, greaseSugar = 60.0, riskLevel = RiskLevel.HIGH))       // 可乐
    put(135, FoodRisk(135, greaseSugar = 50.0, riskLevel = RiskLevel.MEDIUM))     // 白砂糖
    put(138, FoodRisk(138, greaseSugar = 78.0, riskLevel = RiskLevel.HIGH))       // 薯片
    put(140, FoodRisk(140, greaseSugar = 55.0, riskLevel = RiskLevel.HIGH))       // 牛奶巧克力
    put(141, FoodRisk(141, greaseSugar = 45.0, riskLevel = RiskLevel.MEDIUM))     // 苏打饼干
    put(143, FoodRisk(143, greaseSugar = 70.0, riskLevel = RiskLevel.HIGH))       // 辣条
    put(145, FoodRisk(145, greaseSugar = 50.0, riskLevel = RiskLevel.MEDIUM))     // 冰淇淋
    put(128, FoodRisk(128, greaseSugar = 30.0, riskLevel = RiskLevel.MEDIUM))     // 蜂蜜(高糖)

    // === 肉禽水产: 中等风险（农残/激素） ===
    put(151, FoodRisk(151, pesticide = 10.0, riskLevel = RiskLevel.MEDIUM))       // 牛肉(肥牛卷)
    put(152, FoodRisk(152, pesticide = 12.0, riskLevel = RiskLevel.MEDIUM))       // 猪肉馅
    put(153, FoodRisk(153, pesticide = 10.0, riskLevel = RiskLevel.MEDIUM))       // 鸡爪
    put(154, FoodRisk(154, heavyMetal = 5.0, pesticide = 8.0, riskLevel = RiskLevel.MEDIUM)) // 鸭血
    put(168, FoodRisk(168, pesticide = 12.0, riskLevel = RiskLevel.MEDIUM))       // 老母鸡
    put(169, FoodRisk(169, pesticide = 10.0, riskLevel = RiskLevel.MEDIUM))       // 鸽子
    put(170, FoodRisk(170, pesticide = 8.0, riskLevel = RiskLevel.MEDIUM))        // 兔肉
    put(199, FoodRisk(199, pesticide = 10.0, riskLevel = RiskLevel.MEDIUM))       // 羊肉卷

    // === 蔬菜水果: 中等风险（农残） ===
    put(146, FoodRisk(146, pesticide = 12.0, riskLevel = RiskLevel.MEDIUM))       // 绿豆芽
    put(147, FoodRisk(147, pesticide = 15.0, riskLevel = RiskLevel.MEDIUM))       // 豇豆
    put(148, FoodRisk(148, pesticide = 12.0, riskLevel = RiskLevel.MEDIUM))       // 黄豆芽
    put(149, FoodRisk(149, pesticide = 15.0, riskLevel = RiskLevel.MEDIUM))       // 荷兰豆
    put(150, FoodRisk(150, pesticide = 15.0, riskLevel = RiskLevel.MEDIUM))       // 秋葵
    put(157, FoodRisk(157, pesticide = 18.0, riskLevel = RiskLevel.MEDIUM))       // 西芹
    put(158, FoodRisk(158, pesticide = 15.0, riskLevel = RiskLevel.MEDIUM))       // 油麦菜
    put(159, FoodRisk(159, pesticide = 15.0, riskLevel = RiskLevel.MEDIUM))       // 茼蒿
    put(160, FoodRisk(160, pesticide = 15.0, riskLevel = RiskLevel.MEDIUM))       // 杭椒
    put(161, FoodRisk(161, pesticide = 12.0, riskLevel = RiskLevel.MEDIUM))       // 牛油果
    put(193, FoodRisk(193, pesticide = 10.0, riskLevel = RiskLevel.MEDIUM))       // 枸杞(干)
    put(194, FoodRisk(194, pesticide = 10.0, riskLevel = RiskLevel.MEDIUM))       // 红枣(干)
    put(196, FoodRisk(196, heavyMetal = 5.0, riskLevel = RiskLevel.MEDIUM))       // 银耳(干)

    // === 调味料: 中等风险（添加剂/高盐） ===
    put(171, FoodRisk(171, greaseSugar = 20.0, riskLevel = RiskLevel.MEDIUM))     // 意面酱
    put(172, FoodRisk(172, greaseSugar = 25.0, riskLevel = RiskLevel.MEDIUM))     // 番茄酱
    put(187, FoodRisk(187, greaseSugar = 20.0, riskLevel = RiskLevel.MEDIUM))     // 芝麻酱
    put(188, FoodRisk(188, greaseSugar = 20.0, riskLevel = RiskLevel.MEDIUM))     // 花生酱
}

// ============================================
// 📊 辅助函数
// ============================================

/** 按品类生成默认营养数据的工厂方法 */
fun defaultNutritionForCategory(category: String): FoodNutrition = when (category) {
    "肉禽水产" -> FoodNutrition(0, protein = 18.0, fat = 12.0, carbohydrate = 0.0, sugar = 0.0, fiber = 0.0, calorie = 200.0, vitaminScore = 35.0, mineralScore = 40.0)
    "蔬菜水果" -> FoodNutrition(0, protein = 1.5, fat = 0.3, carbohydrate = 6.0, sugar = 3.0, fiber = 2.0, calorie = 35.0, vitaminScore = 70.0, mineralScore = 45.0)
    "谷类主食" -> FoodNutrition(0, protein = 8.0, fat = 1.5, carbohydrate = 55.0, sugar = 1.0, fiber = 5.0, calorie = 280.0, vitaminScore = 20.0, mineralScore = 25.0)
    "蛋奶豆", "蛋奶类" -> FoodNutrition(0, protein = 8.0, fat = 5.0, carbohydrate = 4.0, sugar = 2.0, fiber = 0.5, calorie = 90.0, vitaminScore = 60.0, mineralScore = 65.0)
    "豆类制品" -> FoodNutrition(0, protein = 10.0, fat = 5.0, carbohydrate = 8.0, sugar = 1.0, fiber = 3.0, calorie = 110.0, vitaminScore = 35.0, mineralScore = 55.0)
    "饮料零食" -> FoodNutrition(0, protein = 2.0, fat = 8.0, carbohydrate = 30.0, sugar = 15.0, fiber = 1.0, calorie = 200.0, vitaminScore = 15.0, mineralScore = 10.0)
    "调料油脂" -> FoodNutrition(0, protein = 0.5, fat = 50.0, carbohydrate = 5.0, sugar = 2.0, fiber = 0.0, calorie = 450.0, vitaminScore = 5.0, mineralScore = 5.0)
    else -> FoodNutrition(0, protein = 5.0, fat = 3.0, carbohydrate = 15.0, sugar = 3.0, fiber = 2.0, calorie = 120.0, vitaminScore = 40.0, mineralScore = 35.0)
}

/** 根据foodId获取营养数据，不存在时返回默认值 */
fun getNutrition(foodId: Int): FoodNutrition = foodNutritionMap[foodId] ?: FoodNutrition(foodId = foodId)

/** 根据foodId获取风险数据，不存在时返回低风险默认值 */
fun getFoodRisk(foodId: Int): FoodRisk = foodRiskMap[foodId] ?: FoodRisk(foodId = foodId, riskLevel = RiskLevel.LOW)

/** 获取某个FoodItem的综合营养评分(0-100) */
fun calculateNutritionScore(nutrition: FoodNutrition): Double {
    val proteinScore = (nutrition.protein / 25.0 * 20).coerceIn(0.0, 20.0)
    val fiberScore = (nutrition.fiber / 10.0 * 15).coerceIn(0.0, 15.0)
    val vitScore = nutrition.vitaminScore * 0.25
    val minScore = nutrition.mineralScore * 0.2
    val calorieDensity = (1.0 - (nutrition.calorie / 500.0).coerceIn(0.0, 1.0)) * 10
    val sugarPenalty = (1.0 - (nutrition.sugar / 30.0).coerceIn(0.0, 1.0)) * 10
    val fatQualityScore = (1.0 - (nutrition.fat / 50.0).coerceIn(0.0, 1.0)) * 10
    return (proteinScore + fiberScore + vitScore + minScore + calorieDensity + sugarPenalty + fatQualityScore).coerceIn(0.0, 100.0)
}