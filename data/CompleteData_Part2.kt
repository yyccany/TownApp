/**
 * ============================================
 * 🌟 万物薪俸小镇 - 完整数据文件 Part2
 * ============================================
 * 服饰单品、日用品、菜谱、穿搭套装、扩展材料及成品
 * ============================================
 */

package com.example.townapp.data

// ============================================
// 👔 服饰单品 (ID: 1000~1099)
// ============================================
val completeClothingItems = listOf(
    // --- 上衣 (1000~1019) ---
    ClothingItem(1000, "白色纯棉T恤", "上衣", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 79.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 120, versatilityScore = 1.0, comfortScore = 0.8, durabilityScore = 0.7, qualityScore = 0.8, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.2, matchingDifficulty = 0.3, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "白色"),
    ClothingItem(1001, "黑色纯棉T恤", "上衣", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 79.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 120, versatilityScore = 1.0, comfortScore = 0.8, durabilityScore = 0.7, qualityScore = 0.8, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.2, matchingDifficulty = 0.3, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "黑色"),
    ClothingItem(1002, "灰色卫衣", "上衣", fabrics = listOf("纯棉", "聚酯纤维"), fabricRatios = listOf(0.7, 0.3), price = 169.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 80, versatilityScore = 0.9, comfortScore = 0.9, durabilityScore = 0.8, qualityScore = 0.75, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.4, plannedObsolescence = 0.15, materialDegradation = 0.15, color = "灰色"),
    ClothingItem(1003, "条纹POLO衫", "上衣", fabrics = listOf("棉", "氨纶"), fabricRatios = listOf(0.95, 0.05), price = 129.0, expectedLifespanMonths = 30.0, maxWearsPerYear = 70, versatilityScore = 0.8, comfortScore = 0.75, durabilityScore = 0.7, qualityScore = 0.7, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.4, matchingDifficulty = 0.5, plannedObsolescence = 0.15, materialDegradation = 0.1, color = "蓝白条纹"),
    ClothingItem(1004, "白色衬衫(正装)", "上衣", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 199.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 60, versatilityScore = 0.9, comfortScore = 0.6, durabilityScore = 0.7, qualityScore = 0.75, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.3, matchingDifficulty = 0.4, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "白色"),
    ClothingItem(1005, "牛仔衬衫", "上衣", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 159.0, expectedLifespanMonths = 48.0, maxWearsPerYear = 50, versatilityScore = 0.8, comfortScore = 0.7, durabilityScore = 0.85, qualityScore = 0.7, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.4, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "牛仔蓝"),
    ClothingItem(1006, "亚麻衬衫", "上衣", fabrics = listOf("亚麻"), fabricRatios = listOf(1.0), price = 249.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 40, versatilityScore = 0.7, comfortScore = 0.85, durabilityScore = 0.6, qualityScore = 0.65, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.5, matchingDifficulty = 0.5, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "米白色"),
    ClothingItem(1007, "针织开衫", "上衣", fabrics = listOf("羊毛", "腈纶"), fabricRatios = listOf(0.5, 0.5), price = 299.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 40, versatilityScore = 0.7, comfortScore = 0.8, durabilityScore = 0.7, qualityScore = 0.65, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.4, matchingDifficulty = 0.5, plannedObsolescence = 0.15, materialDegradation = 0.15, color = "卡其色"),
    ClothingItem(1008, "运动T恤(速干)", "上衣", fabrics = listOf("聚酯纤维", "氨纶"), fabricRatios = listOf(0.9, 0.1), price = 89.0, expectedLifespanMonths = 18.0, maxWearsPerYear = 100, versatilityScore = 0.6, comfortScore = 0.7, durabilityScore = 0.6, qualityScore = 0.6, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.2, matchingDifficulty = 0.3, plannedObsolescence = 0.2, materialDegradation = 0.15, color = "深蓝色"),
    ClothingItem(1009, "宽松白T恤(长袖)", "上衣", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 99.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 80, versatilityScore = 0.9, comfortScore = 0.85, durabilityScore = 0.7, qualityScore = 0.7, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.2, matchingDifficulty = 0.3, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "白色"),
    ClothingItem(1010, "高领毛衣", "上衣", fabrics = listOf("羊毛"), fabricRatios = listOf(1.0), price = 259.0, expectedLifespanMonths = 48.0, maxWearsPerYear = 30, versatilityScore = 0.7, comfortScore = 0.8, durabilityScore = 0.8, qualityScore = 0.7, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.4, matchingDifficulty = 0.5, plannedObsolescence = 0.1, materialDegradation = 0.15, color = "深灰色"),
    ClothingItem(1011, "圆领毛衣", "上衣", fabrics = listOf("羊毛", "腈纶"), fabricRatios = listOf(0.6, 0.4), price = 199.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 35, versatilityScore = 0.8, comfortScore = 0.75, durabilityScore = 0.7, qualityScore = 0.65, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.3, matchingDifficulty = 0.4, plannedObsolescence = 0.15, materialDegradation = 0.15, color = "浅灰色"),

    // --- 外套 (1012~1022) ---
    ClothingItem(1012, "羊毛混纺大衣", "外套", fabrics = listOf("羊毛", "聚酯纤维"), fabricRatios = listOf(0.7, 0.3), price = 1299.0, expectedLifespanMonths = 120.0, maxWearsPerYear = 50, versatilityScore = 0.8, comfortScore = 0.9, durabilityScore = 0.95, qualityScore = 0.9, isIQTax = true, iqTaxLevel = 2, decisionDifficulty = 0.8, matchingDifficulty = 0.8, plannedObsolescence = 0.2, materialDegradation = 0.1, color = "深灰色"),
    ClothingItem(1013, "羽绒服(短款)", "外套", fabrics = listOf("尼龙", "羽绒"), fabricRatios = listOf(0.3, 0.7), price = 599.0, expectedLifespanMonths = 60.0, maxWearsPerYear = 40, versatilityScore = 0.7, comfortScore = 0.9, durabilityScore = 0.8, qualityScore = 0.8, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.5, matchingDifficulty = 0.5, plannedObsolescence = 0.15, materialDegradation = 0.1, color = "黑色"),
    ClothingItem(1014, "风衣", "外套", fabrics = listOf("聚酯纤维", "棉"), fabricRatios = listOf(0.6, 0.4), price = 399.0, expectedLifespanMonths = 48.0, maxWearsPerYear = 30, versatilityScore = 0.7, comfortScore = 0.7, durabilityScore = 0.75, qualityScore = 0.65, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.5, matchingDifficulty = 0.6, plannedObsolescence = 0.15, materialDegradation = 0.1, color = "卡其色"),
    ClothingItem(1015, "牛仔夹克", "外套", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 349.0, expectedLifespanMonths = 72.0, maxWearsPerYear = 40, versatilityScore = 0.8, comfortScore = 0.7, durabilityScore = 0.9, qualityScore = 0.75, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.4, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "牛仔蓝"),
    ClothingItem(1016, "运动卫衣外套", "外套", fabrics = listOf("纯棉", "聚酯纤维"), fabricRatios = listOf(0.6, 0.4), price = 199.0, expectedLifespanMonths = 30.0, maxWearsPerYear = 60, versatilityScore = 0.7, comfortScore = 0.8, durabilityScore = 0.7, qualityScore = 0.6, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.3, plannedObsolescence = 0.2, materialDegradation = 0.15, color = "黑色"),
    ClothingItem(1017, "冲锋衣(三合一)", "外套", fabrics = listOf("聚酯纤维", "防水膜"), fabricRatios = listOf(0.9, 0.1), price = 899.0, expectedLifespanMonths = 72.0, maxWearsPerYear = 30, versatilityScore = 0.6, comfortScore = 0.6, durabilityScore = 0.9, qualityScore = 0.8, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.6, matchingDifficulty = 0.7, plannedObsolescence = 0.1, materialDegradation = 0.05, color = "藏青色"),

    // --- 裤装 (1018~1030) ---
    ClothingItem(1018, "蓝色牛仔裤", "裤子", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 299.0, expectedLifespanMonths = 60.0, maxWearsPerYear = 100, versatilityScore = 1.2, comfortScore = 0.9, durabilityScore = 0.9, qualityScore = 0.85, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.4, matchingDifficulty = 0.5, plannedObsolescence = 0.15, materialDegradation = 0.1, color = "牛仔蓝"),
    ClothingItem(1019, "黑色休闲裤", "裤子", fabrics = listOf("棉", "氨纶"), fabricRatios = listOf(0.97, 0.03), price = 199.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 80, versatilityScore = 1.0, comfortScore = 0.85, durabilityScore = 0.75, qualityScore = 0.75, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.3, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "黑色"),
    ClothingItem(1020, "卡其色休闲裤", "裤子", fabrics = listOf("棉", "氨纶"), fabricRatios = listOf(0.97, 0.03), price = 229.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 70, versatilityScore = 0.9, comfortScore = 0.8, durabilityScore = 0.75, qualityScore = 0.7, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.4, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "卡其色"),
    ClothingItem(1021, "西装裤(黑色)", "裤子", fabrics = listOf("聚酯纤维", "羊毛"), fabricRatios = listOf(0.6, 0.4), price = 359.0, expectedLifespanMonths = 48.0, maxWearsPerYear = 50, versatilityScore = 0.7, comfortScore = 0.6, durabilityScore = 0.8, qualityScore = 0.7, isIQTax = true, iqTaxLevel = 1, decisionDifficulty = 0.5, matchingDifficulty = 0.5, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "黑色"),
    ClothingItem(1022, "运动束脚裤", "裤子", fabrics = listOf("聚酯纤维", "氨纶"), fabricRatios = listOf(0.85, 0.15), price = 149.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 80, versatilityScore = 0.7, comfortScore = 0.8, durabilityScore = 0.65, qualityScore = 0.6, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.2, matchingDifficulty = 0.3, plannedObsolescence = 0.2, materialDegradation = 0.15, color = "黑色"),
    ClothingItem(1023, "工装裤", "裤子", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 259.0, expectedLifespanMonths = 48.0, maxWearsPerYear = 60, versatilityScore = 0.6, comfortScore = 0.7, durabilityScore = 0.85, qualityScore = 0.65, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.4, matchingDifficulty = 0.5, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "军绿色"),
    ClothingItem(1024, "短裤(牛仔)", "裤子", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 129.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 50, versatilityScore = 0.6, comfortScore = 0.7, durabilityScore = 0.7, qualityScore = 0.6, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.2, matchingDifficulty = 0.3, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "牛仔蓝"),
    ClothingItem(1025, "西服短裤", "裤子", fabrics = listOf("聚酯纤维", "棉"), fabricRatios = listOf(0.5, 0.5), price = 179.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 30, versatilityScore = 0.5, comfortScore = 0.6, durabilityScore = 0.6, qualityScore = 0.55, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.4, matchingDifficulty = 0.5, plannedObsolescence = 0.15, materialDegradation = 0.1, color = "卡其色"),

    // --- 鞋履 (1026~1040) ---
    ClothingItem(1026, "白色运动鞋", "鞋履", fabrics = listOf("网布", "橡胶", "EVA"), fabricRatios = listOf(0.4, 0.4, 0.2), price = 359.0, expectedLifespanMonths = 18.0, maxWearsPerYear = 150, versatilityScore = 1.0, comfortScore = 0.85, durabilityScore = 0.6, qualityScore = 0.7, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.3, plannedObsolescence = 0.2, materialDegradation = 0.2, color = "白色"),
    ClothingItem(1027, "黑色皮鞋", "鞋履", fabrics = listOf("皮革", "橡胶"), fabricRatios = listOf(0.7, 0.3), price = 499.0, expectedLifespanMonths = 60.0, maxWearsPerYear = 60, versatilityScore = 0.7, comfortScore = 0.6, durabilityScore = 0.85, qualityScore = 0.75, isIQTax = true, iqTaxLevel = 1, decisionDifficulty = 0.5, matchingDifficulty = 0.5, plannedObsolescence = 0.1, materialDegradation = 0.15, color = "黑色"),
    ClothingItem(1028, "帆布鞋", "鞋履", fabrics = listOf("帆布", "橡胶"), fabricRatios = listOf(0.6, 0.4), price = 79.0, expectedLifespanMonths = 12.0, maxWearsPerYear = 120, versatilityScore = 0.8, comfortScore = 0.7, durabilityScore = 0.4, qualityScore = 0.5, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.2, matchingDifficulty = 0.3, plannedObsolescence = 0.3, materialDegradation = 0.2, color = "白色"),
    ClothingItem(1029, "板鞋", "鞋履", fabrics = listOf("皮革", "橡胶"), fabricRatios = listOf(0.5, 0.5), price = 259.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 100, versatilityScore = 0.8, comfortScore = 0.7, durabilityScore = 0.6, qualityScore = 0.6, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.3, plannedObsolescence = 0.2, materialDegradation = 0.15, color = "白色"),
    ClothingItem(1030, "短靴", "鞋履", fabrics = listOf("皮革", "橡胶"), fabricRatios = listOf(0.6, 0.4), price = 599.0, expectedLifespanMonths = 48.0, maxWearsPerYear = 40, versatilityScore = 0.6, comfortScore = 0.65, durabilityScore = 0.8, qualityScore = 0.7, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.5, matchingDifficulty = 0.6, plannedObsolescence = 0.1, materialDegradation = 0.15, color = "黑色"),
    ClothingItem(1031, "运动拖鞋", "鞋履", fabrics = listOf("EVA"), fabricRatios = listOf(1.0), price = 39.0, expectedLifespanMonths = 12.0, maxWearsPerYear = 90, versatilityScore = 0.3, comfortScore = 0.7, durabilityScore = 0.3, qualityScore = 0.3, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.1, matchingDifficulty = 0.2, plannedObsolescence = 0.3, materialDegradation = 0.2, color = "黑色"),
    ClothingItem(1032, "登山鞋", "鞋履", fabrics = listOf("皮革", "橡胶", "Gore-Tex"), fabricRatios = listOf(0.5, 0.4, 0.1), price = 799.0, expectedLifespanMonths = 72.0, maxWearsPerYear = 20, versatilityScore = 0.3, comfortScore = 0.6, durabilityScore = 0.95, qualityScore = 0.8, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.6, matchingDifficulty = 0.8, plannedObsolescence = 0.05, materialDegradation = 0.05, color = "棕色"),
    ClothingItem(1033, "凉鞋", "鞋履", fabrics = listOf("皮革", "橡胶"), fabricRatios = listOf(0.5, 0.5), price = 129.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 40, versatilityScore = 0.3, comfortScore = 0.7, durabilityScore = 0.5, qualityScore = 0.5, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.2, matchingDifficulty = 0.3, plannedObsolescence = 0.2, materialDegradation = 0.15, color = "棕色"),

    // --- 裙装 (1034~1040) ---
    ClothingItem(1034, "黑色半身裙", "连衣裙", fabrics = listOf("聚酯纤维", "氨纶"), fabricRatios = listOf(0.95, 0.05), price = 159.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 40, versatilityScore = 0.8, comfortScore = 0.7, durabilityScore = 0.7, qualityScore = 0.65, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.4, plannedObsolescence = 0.15, materialDegradation = 0.1, color = "黑色"),
    ClothingItem(1035, "碎花连衣裙", "连衣裙", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 259.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 30, versatilityScore = 0.5, comfortScore = 0.75, durabilityScore = 0.6, qualityScore = 0.6, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.5, matchingDifficulty = 0.6, plannedObsolescence = 0.15, materialDegradation = 0.1, color = "白色"),
    ClothingItem(1036, "百褶裙", "连衣裙", fabrics = listOf("聚酯纤维"), fabricRatios = listOf(1.0), price = 139.0, expectedLifespanMonths = 30.0, maxWearsPerYear = 35, versatilityScore = 0.6, comfortScore = 0.65, durabilityScore = 0.6, qualityScore = 0.55, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.4, plannedObsolescence = 0.15, materialDegradation = 0.1, color = "卡其色"),

    // --- 内衣袜子 (1041~1050) ---
    ClothingItem(1037, "纯棉内裤(3条装)", "内衣", fabrics = listOf("纯棉", "氨纶"), fabricRatios = listOf(0.95, 0.05), price = 59.0, expectedLifespanMonths = 6.0, maxWearsPerYear = 90, versatilityScore = 0.5, comfortScore = 0.8, durabilityScore = 0.4, qualityScore = 0.5, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.1, matchingDifficulty = 0.1, plannedObsolescence = 0.5, materialDegradation = 0.3, color = "黑色"),
    ClothingItem(1038, "棉袜(5双装)", "内衣", fabrics = listOf("纯棉", "氨纶"), fabricRatios = listOf(0.8, 0.2), price = 39.0, expectedLifespanMonths = 6.0, maxWearsPerYear = 120, versatilityScore = 0.4, comfortScore = 0.7, durabilityScore = 0.3, qualityScore = 0.4, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.1, matchingDifficulty = 0.1, plannedObsolescence = 0.5, materialDegradation = 0.3, color = "黑色"),
    ClothingItem(1039, "运动内衣", "内衣", fabrics = listOf("聚酯纤维", "氨纶"), fabricRatios = listOf(0.8, 0.2), price = 149.0, expectedLifespanMonths = 12.0, maxWearsPerYear = 80, versatilityScore = 0.3, comfortScore = 0.6, durabilityScore = 0.5, qualityScore = 0.55, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.3, matchingDifficulty = 0.3, plannedObsolescence = 0.3, materialDegradation = 0.2, color = "黑色"),
    ClothingItem(1040, "连裤袜", "内衣", fabrics = listOf("尼龙", "氨纶"), fabricRatios = listOf(0.85, 0.15), price = 29.0, expectedLifespanMonths = 3.0, maxWearsPerYear = 50, versatilityScore = 0.5, comfortScore = 0.6, durabilityScore = 0.2, qualityScore = 0.3, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.1, matchingDifficulty = 0.2, plannedObsolescence = 0.5, materialDegradation = 0.3, color = "黑色"),

    // --- 配饰 (1041~1050) ---
    ClothingItem(1041, "帆布腰带", "配饰", fabrics = listOf("帆布", "金属"), fabricRatios = listOf(0.8, 0.2), price = 59.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 120, versatilityScore = 0.6, comfortScore = 0.7, durabilityScore = 0.7, qualityScore = 0.5, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.2, matchingDifficulty = 0.2, plannedObsolescence = 0.1, materialDegradation = 0.15, color = "黑色"),
    ClothingItem(1042, "棒球帽", "配饰", fabrics = listOf("纯棉"), fabricRatios = listOf(1.0), price = 69.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 60, versatilityScore = 0.4, comfortScore = 0.7, durabilityScore = 0.7, qualityScore = 0.5, isIQTax = false, iqTaxLevel = 0, decisionDifficulty = 0.2, matchingDifficulty = 0.3, plannedObsolescence = 0.1, materialDegradation = 0.15, color = "黑色"),
    ClothingItem(1043, "围巾(羊毛)", "配饰", fabrics = listOf("羊毛"), fabricRatios = listOf(1.0), price = 129.0, expectedLifespanMonths = 60.0, maxWearsPerYear = 30, versatilityScore = 0.5, comfortScore = 0.8, durabilityScore = 0.8, qualityScore = 0.65, isIQTax = false, iqTaxLevel = 1, decisionDifficulty = 0.3, matchingDifficulty = 0.4, plannedObsolescence = 0.1, materialDegradation = 0.15, color = "深灰色"),
    ClothingItem(1044, "太阳镜", "配饰", fabrics = listOf("塑料", "金属"), fabricRatios = listOf(0.7, 0.3), price = 199.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 40, versatilityScore = 0.3, comfortScore = 0.5, durabilityScore = 0.5, qualityScore = 0.4, isIQTax = true, iqTaxLevel = 2, decisionDifficulty = 0.5, matchingDifficulty = 0.5, plannedObsolescence = 0.2, materialDegradation = 0.1, color = "黑色"),
    ClothingItem(1045, "手表(石英)", "配饰", fabrics = listOf("不锈钢", "玻璃", "皮革"), fabricRatios = listOf(0.6, 0.2, 0.2), price = 399.0, expectedLifespanMonths = 120.0, maxWearsPerYear = 100, versatilityScore = 0.6, comfortScore = 0.6, durabilityScore = 0.8, qualityScore = 0.6, isIQTax = true, iqTaxLevel = 2, decisionDifficulty = 0.6, matchingDifficulty = 0.5, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "银色"),

    // --- 正装 (1046~1050) ---
    ClothingItem(1046, "男士西装(套装)", "正装", fabrics = listOf("羊毛", "聚酯纤维"), fabricRatios = listOf(0.7, 0.3), price = 1999.0, expectedLifespanMonths = 72.0, maxWearsPerYear = 30, versatilityScore = 0.6, comfortScore = 0.5, durabilityScore = 0.85, qualityScore = 0.8, isIQTax = true, iqTaxLevel = 2, decisionDifficulty = 0.7, matchingDifficulty = 0.8, plannedObsolescence = 0.1, materialDegradation = 0.1, color = "藏青色"),
    ClothingItem(1047, "女士西装外套", "正装", fabrics = listOf("聚酯纤维", "氨纶"), fabricRatios = listOf(0.95, 0.05), price = 599.0, expectedLifespanMonths = 36.0, maxWearsPerYear = 40, versatilityScore = 0.6, comfortScore = 0.5, durabilityScore = 0.7, qualityScore = 0.65, isIQTax = true, iqTaxLevel = 1, decisionDifficulty = 0.5, matchingDifficulty = 0.6, plannedObsolescence = 0.15, materialDegradation = 0.1, color = "黑色"),
    ClothingItem(1048, "领带", "正装", fabrics = listOf("真丝"), fabricRatios = listOf(1.0), price = 199.0, expectedLifespanMonths = 120.0, maxWearsPerYear = 20, versatilityScore = 0.4, comfortScore = 0.3, durabilityScore = 0.85, qualityScore = 0.6, isIQTax = true, iqTaxLevel = 2, decisionDifficulty = 0.5, matchingDifficulty = 0.6, plannedObsolescence = 0.05, materialDegradation = 0.05, color = "藏青色"),
    ClothingItem(1049, "皮鞋(正装)", "正装", fabrics = listOf("牛皮", "橡胶"), fabricRatios = listOf(0.7, 0.3), price = 699.0, expectedLifespanMonths = 72.0, maxWearsPerYear = 40, versatilityScore = 0.5, comfortScore = 0.5, durabilityScore = 0.85, qualityScore = 0.75, isIQTax = true, iqTaxLevel = 1, decisionDifficulty = 0.5, matchingDifficulty = 0.5, plannedObsolescence = 0.1, materialDegradation = 0.15, color = "黑色"),
    ClothingItem(1050, "女士高跟鞋", "正装", fabrics = listOf("皮革", "橡胶"), fabricRatios = listOf(0.6, 0.4), price = 399.0, expectedLifespanMonths = 24.0, maxWearsPerYear = 30, versatilityScore = 0.4, comfortScore = 0.3, durabilityScore = 0.5, qualityScore = 0.5, isIQTax = true, iqTaxLevel = 1, decisionDifficulty = 0.6, matchingDifficulty = 0.6, plannedObsolescence = 0.2, materialDegradation = 0.15, color = "黑色"),
)

// ============================================
// 🏠 日用品 (ID: 2000~2049)
// ============================================
val completeHouseholdItems = listOf(
    HouseholdItem(2000, "沙发", "家具", price = 2500.0, lifespanYears = 8.0, brand = "宜家", note = "三人位布艺沙发"),
    HouseholdItem(2001, "双人床", "家具", price = 3000.0, lifespanYears = 10.0, brand = "喜临门", note = "1.8m实木框架"),
    HouseholdItem(2002, "书桌", "家具", price = 800.0, lifespanYears = 10.0, brand = "宜家", note = "1.2m简约书桌"),
    HouseholdItem(2003, "餐桌椅套装", "家具", price = 1500.0, lifespanYears = 8.0, brand = "林氏木业", note = "一桌四椅"),
    HouseholdItem(2004, "衣柜", "家具", price = 2500.0, lifespanYears = 12.0, brand = "尚品宅配", note = "双开门推拉衣柜"),
    HouseholdItem(2005, "冰箱(双门)", "电器", price = 2499.0, lifespanYears = 10.0, brand = "海尔", note = "200L 风冷无霜"),
    HouseholdItem(2006, "洗衣机(滚筒)", "电器", price = 1999.0, lifespanYears = 8.0, brand = "小天鹅", note = "8kg 变频"),
    HouseholdItem(2007, "空调(壁挂)", "电器", price = 2699.0, lifespanYears = 8.0, brand = "格力", note = "1.5匹 变频冷暖"),
    HouseholdItem(2008, "电视(55寸)", "电器", price = 2999.0, lifespanYears = 6.0, brand = "小米", note = "4K 智能电视"),
    HouseholdItem(2009, "微波炉", "电器", price = 399.0, lifespanYears = 5.0, brand = "格兰仕", note = "20L 机械旋钮"),
    HouseholdItem(2010, "电饭煲(4L)", "电器", price = 299.0, lifespanYears = 5.0, brand = "美的", note = "智能预约"),
    HouseholdItem(2011, "空气炸锅", "电器", price = 399.0, lifespanYears = 3.0, brand = "飞利浦", note = "3L 可视窗口"),
    HouseholdItem(2012, "电热水壶", "电器", price = 89.0, lifespanYears = 3.0, brand = "苏泊尔", note = "1.7L 不锈钢"),
    HouseholdItem(2013, "吸尘器", "电器", price = 999.0, lifespanYears = 5.0, brand = "戴森", note = "无线手持"),
    HouseholdItem(2014, "台灯(LED)", "电器", price = 129.0, lifespanYears = 5.0, brand = "小米", note = "LED护眼三档调光"),
    HouseholdItem(2015, "保温杯", "日用品", price = 89.0, lifespanYears = 5.0, brand = "膳魔师", note = "500ml 不锈钢"),
    HouseholdItem(2016, "牙刷(电动)", "日用品", price = 199.0, lifespanYears = 2.0, brand = "欧乐B", note = "充电式"),
    HouseholdItem(2017, "毛巾(纯棉)", "日用品", price = 25.0, lifespanYears = 1.0, brand = "洁丽雅", note = "33x70cm 柔软吸水"),
    HouseholdItem(2018, "浴巾", "日用品", price = 59.0, lifespanYears = 2.0, brand = "洁丽雅", note = "70x140cm"),
    HouseholdItem(2019, "砧板(实木)", "厨房", price = 59.0, lifespanYears = 2.0, brand = "双枪", note = "防霉抗菌"),
    HouseholdItem(2020, "炒锅(不粘)", "厨房", price = 199.0, lifespanYears = 3.0, brand = "苏泊尔", note = "30cm 不粘涂层"),
    HouseholdItem(2021, "汤锅(不锈钢)", "厨房", price = 159.0, lifespanYears = 8.0, brand = "双立人", note = "24cm 不锈钢"),
    HouseholdItem(2022, "菜刀套装", "厨房", price = 299.0, lifespanYears = 5.0, brand = "十八子作", note = "三件套"),
    HouseholdItem(2023, "碗碟套装", "厨房", price = 199.0, lifespanYears = 5.0, brand = "宜家", note = "16件套 白瓷"),
    HouseholdItem(2024, "筷子(10双)", "厨房", price = 19.9, lifespanYears = 1.0, brand = "双枪", note = "鸡翅木"),
    HouseholdItem(2025, "垃圾桶", "日用品", price = 29.0, lifespanYears = 3.0, brand = "佳帮手", note = "脚踏式密封"),
    HouseholdItem(2026, "衣架(10个)", "日用品", price = 19.9, lifespanYears = 3.0, brand = "好太太", note = "防滑浸塑"),
    HouseholdItem(2027, "收纳箱(大号)", "日用品", price = 49.0, lifespanYears = 5.0, brand = "禧天龙", note = "55L 透明"),
    HouseholdItem(2028, "拖把(旋转)", "日用品", price = 69.0, lifespanYears = 2.0, brand = "妙洁", note = "免手洗旋转拖把"),
    HouseholdItem(2029, "扫帚簸箕套装", "日用品", price = 39.9, lifespanYears = 3.0, brand = "茶花", note = "防风扫帚"),
    HouseholdItem(2030, "被子(羽绒)", "日用品", price = 599.0, lifespanYears = 5.0, brand = "水星家纺", note = "200x230cm 羽绒被"),
    HouseholdItem(2031, "枕头(记忆棉)", "日用品", price = 129.0, lifespanYears = 3.0, brand = "眠博士", note = "慢回弹 护颈"),
    HouseholdItem(2032, "四件套(纯棉)", "日用品", price = 299.0, lifespanYears = 3.0, brand = "水星家纺", note = "200x230cm 床单款"),
    HouseholdItem(2033, "窗帘(两片)", "日用品", price = 399.0, lifespanYears = 5.0, brand = "摩力克", note = "2.5m高 遮光"),
    HouseholdItem(2034, "瑜伽垫", "运动", price = 79.0, lifespanYears = 3.0, brand = "Keep", note = "6mm TPE材质"),
    HouseholdItem(2035, "哑铃套装", "运动", price = 299.0, lifespanYears = 10.0, brand = "Keep", note = "10kg 可调节"),
    HouseholdItem(2036, "跳绳", "运动", price = 29.0, lifespanYears = 2.0, brand = "迪卡侬", note = "钢丝绳 轴承"),
    HouseholdItem(2037, "运动水壶", "运动", price = 49.0, lifespanYears = 3.0, brand = "Contigo", note = "750ml Tritan"),
    HouseholdItem(2038, "背包(双肩)", "日用品", price = 199.0, lifespanYears = 4.0, brand = "瑞典北极狐", note = "30L 日常通勤"),
    HouseholdItem(2039, "雨伞(折叠)", "日用品", price = 39.0, lifespanYears = 2.0, brand = "天堂", note = "十骨防风"),
    HouseholdItem(2040, "手电筒", "日用品", price = 39.9, lifespanYears = 5.0, brand = "小米", note = "LED 可充电"),
    HouseholdItem(2041, "指甲刀套装", "日用品", price = 29.9, lifespanYears = 5.0, brand = "金达日美", note = "七件套"),
    HouseholdItem(2042, "吹风机", "电器", price = 149.0, lifespanYears = 5.0, brand = "飞科", note = "1800W 负离子"),
    HouseholdItem(2043, "剃须刀(电动)", "日用品", price = 299.0, lifespanYears = 5.0, brand = "飞利浦", note = "双刀头 干湿双剃"),
    HouseholdItem(2044, "加湿器", "电器", price = 99.0, lifespanYears = 3.0, brand = "小熊", note = "4L 静音"),
    HouseholdItem(2045, "电风扇(落地)", "电器", price = 199.0, lifespanYears = 5.0, brand = "美的", note = "7叶 遥控定时"),
    HouseholdItem(2046, "储物凳", "家具", price = 79.0, lifespanYears = 5.0, brand = "宜家", note = "可储物换鞋凳"),
    HouseholdItem(2047, "鞋柜", "家具", price = 299.0, lifespanYears = 8.0, brand = "林氏木业", note = "双层翻斗式"),
    HouseholdItem(2048, "书架", "家具", price = 399.0, lifespanYears = 8.0, brand = "宜家", note = "四层书架"),
    HouseholdItem(2049, "落地灯", "电器", price = 199.0, lifespanYears = 5.0, brand = "宜家", note = "可调角度 LED"),
)

// ============================================
// 🍳 菜谱 (ID: 3000~3049)
// ============================================
val completeRecipes = listOf(
    Recipe(3000, "番茄炒蛋", "家常菜", "1.番茄切块,鸡蛋打散\n2.热油先炒鸡蛋盛出\n3.炒番茄至出汁\n4.倒入鸡蛋翻炒调味", listOf(RecipeIngredient(51, 200f), RecipeIngredient(1, 100f))),
    Recipe(3001, "麻婆豆腐", "川菜", "1.豆腐切块焯水\n2.热油炒牛肉末\n3.加豆瓣酱炒出红油\n4.加水和豆腐煮3分钟\n5.勾芡撒花椒粉", listOf(RecipeIngredient(96, 300f), RecipeIngredient(44, 50f), RecipeIngredient(189, 15f), RecipeIngredient(174, 3f))),
    Recipe(3002, "白切鸡", "粤菜", "1.鸡洗净水烧开\n2.放入大火煮15分钟\n3.关火焖10分钟\n4.过冰水斩件", listOf(RecipeIngredient(168, 500f))),
    Recipe(3003, "蛋炒饭", "主食", "1.鸡蛋打散\n2.热油炒鸡蛋成碎末\n3.倒入米饭大火翻炒\n4.加盐葱花出锅", listOf(RecipeIngredient(110, 200f), RecipeIngredient(1, 50f))),
    Recipe(3004, "回锅肉", "川菜", "1.五花肉煮熟切薄片\n2.煸炒至卷曲出油\n3.加豆瓣酱炒红油\n4.放入青蒜翻炒", listOf(RecipeIngredient(29, 300f), RecipeIngredient(189, 15f))),
    Recipe(3005, "清蒸鲈鱼", "粤菜", "1.鱼身划刀抹盐料酒\n2.放姜片葱段\n3.水开蒸8-10分钟\n4.倒蒸汁淋热油豉油", listOf(RecipeIngredient(17, 400f))),
    Recipe(3006, "糖醋排骨", "家常菜", "1.排骨焯水\n2.炸至金黄\n3.炒糖色加醋汁\n4.收汁出锅撒芝麻", listOf(RecipeIngredient(31, 400f), RecipeIngredient(29, 50f))),
    Recipe(3007, "酸辣土豆丝", "家常菜", "1.土豆切丝泡水\n2.热油爆香辣椒花椒\n3.大火翻炒加醋\n4.加盐调味", listOf(RecipeIngredient(54, 300f), RecipeIngredient(175, 5f), RecipeIngredient(137, 10f))),
    Recipe(3008, "可乐鸡翅", "家常菜", "1.鸡翅划刀焯水\n2.煎至两面金黄\n3.倒入可乐加酱油\n4.小火焖煮收汁", listOf(RecipeIngredient(13, 400f), RecipeIngredient(129, 330f), RecipeIngredient(136, 15f))),
    Recipe(3009, "红烧肉", "家常菜", "1.五花肉切块焯水\n2.炒糖色加肉块\n3.加八角桂皮酱油\n4.小火慢炖1小时", listOf(RecipeIngredient(29, 500f), RecipeIngredient(135, 20f), RecipeIngredient(136, 20f))),
    Recipe(3010, "西红柿牛腩", "家常菜", "1.牛腩焯水\n2.番茄炒出汁\n3.加牛腩和开水炖1.5小时\n4.加盐调味", listOf(RecipeIngredient(39, 400f), RecipeIngredient(51, 200f))),
    Recipe(3011, "干煸四季豆", "家常菜", "1.四季豆去筋切段\n2.热油炸至微焦\n3.加蒜末干辣椒煸炒\n4.加盐出锅", listOf(RecipeIngredient(76, 300f), RecipeIngredient(56, 10f), RecipeIngredient(175, 5f))),
    Recipe(3012, "青椒肉丝", "家常菜", "1.瘦肉切丝腌制\n2.青椒切丝\n3.热油划肉丝盛出\n4.炒青椒再汇入肉丝", listOf(RecipeIngredient(30, 200f), RecipeIngredient(58, 150f))),
    Recipe(3013, "鱼香肉丝", "川菜", "1.肉丝腌制滑油\n2.木耳笋丝焯水\n3.调鱼香汁(糖醋酱油)\n4.爆炒出锅", listOf(RecipeIngredient(30, 200f), RecipeIngredient(69, 30f), RecipeIngredient(136, 10f), RecipeIngredient(137, 10f), RecipeIngredient(135, 10f))),
    Recipe(3014, "水煮鱼", "川菜", "1.鱼片腌制\n2.炒豆瓣酱煮汤\n3.烫蔬菜铺底\n4.鱼片煮熟淋热油", listOf(RecipeIngredient(28, 500f), RecipeIngredient(189, 20f), RecipeIngredient(175, 10f), RecipeIngredient(174, 5f))),
    Recipe(3015, "宫保鸡丁", "川菜", "1.鸡丁腌制滑油\n2.调碗汁(糖醋酱油)\n3.爆炒鸡丁加花生\n4.下调料汁收汁", listOf(RecipeIngredient(11, 300f), RecipeIngredient(103, 50f), RecipeIngredient(136, 10f), RecipeIngredient(137, 10f), RecipeIngredient(135, 15f))),
    Recipe(3016, "葱烧海参", "鲁菜", "1.海参处理干净\n2.大葱炸葱油\n3.加高汤煨海参\n4.勾芡淋葱油", listOf(RecipeIngredient(55, 100f))),
    Recipe(3017, "油焖大虾", "家常菜", "1.虾去虾线开背\n2.煎至金黄\n3.加番茄酱糖焖煮\n4.收汁撒葱花", listOf(RecipeIngredient(20, 300f))),
    Recipe(3018, "蒜蓉西兰花", "家常菜", "1.西兰花掰小朵焯水\n2.热油爆香蒜末\n3.倒入西兰花翻炒\n4.加盐蚝油调味", listOf(RecipeIngredient(50, 300f), RecipeIngredient(56, 15f))),
    Recipe(3019, "紫菜蛋花汤", "汤羹", "1.水烧开\n2.淋入蛋液搅散\n3.加紫菜\n4.加盐香油调味", listOf(RecipeIngredient(70, 5f), RecipeIngredient(1, 50f))),
    Recipe(3020, "排骨海带汤", "汤羹", "1.排骨焯水\n2.海带泡发切块\n3.一起炖1.5小时\n4.加盐调味", listOf(RecipeIngredient(31, 300f))),
    Recipe(3021, "扬州炒饭", "主食", "1.鸡蛋炒散\n2.虾仁火腿丁炒熟\n3.加米饭大火翻炒\n4.加青豆葱花", listOf(RecipeIngredient(110, 200f), RecipeIngredient(1, 50f), RecipeIngredient(20, 50f))),
    Recipe(3022, "兰州牛肉拉面", "主食", "1.牛骨熬汤\n2.拉面煮好\n3.加牛肉片萝卜\n4.浇汤撒蒜苗香菜", listOf(RecipeIngredient(117, 200f), RecipeIngredient(39, 100f))),
    Recipe(3023, "饺子(猪肉白菜)", "主食", "1.调馅(猪肉+白菜)\n2.和面擀皮\n3.包饺子\n4.煮熟蘸醋", listOf(RecipeIngredient(152, 300f), RecipeIngredient(48, 200f), RecipeIngredient(110, 300f))),
    Recipe(3024, "三明治", "西餐", "1.吐司烤香\n2.夹生菜火腿奶酪\n3.加沙拉酱\n4.对切开吃", listOf(RecipeIngredient(116, 100f), RecipeIngredient(60, 30f), RecipeIngredient(6, 30f))),
    Recipe(3025, "煎牛排", "西餐", "1.牛排室温回温\n2.热锅大火煎制\n3.加黄油迷迭香\n4.醒肉后切块", listOf(RecipeIngredient(40, 200f))),
    Recipe(3026, "番茄意面", "西餐", "1.煮意面\n2.炒番茄酱\n3.汇入煮好的意面\n4.撒芝士碎", listOf(RecipeIngredient(118, 200f), RecipeIngredient(51, 200f))),
    Recipe(3027, "沙拉(凯撒)", "西餐", "1.生菜洗净撕碎\n2.烤面包丁\n3.加凯撒酱\n4.撒帕玛森芝士", listOf(RecipeIngredient(60, 100f), RecipeIngredient(116, 50f))),
    Recipe(3028, "皮蛋豆腐", "凉菜", "1.豆腐切片摆盘\n2.皮蛋切碎铺上\n3.撒葱花姜末\n4.淋酱油香油", listOf(RecipeIngredient(97, 200f), RecipeIngredient(125, 50f))),
    Recipe(3029, "凉拌黄瓜", "凉菜", "1.黄瓜拍碎切段\n2.蒜末辣椒圈\n3.加醋盐糖凉拌\n4.淋香油", listOf(RecipeIngredient(52, 200f), RecipeIngredient(56, 10f))),
    Recipe(3030, "酸菜鱼", "川菜", "1.鱼片腌制\n2.酸菜炒香\n3.加水煮汤\n4.烫鱼片出锅", listOf(RecipeIngredient(28, 400f))),
    Recipe(3031, "辣子鸡", "川菜", "1.鸡块腌炸至金黄\n2.干辣椒花椒爆香\n3.汇入鸡块翻炒\n4.加盐糖调味", listOf(RecipeIngredient(12, 500f), RecipeIngredient(175, 50f), RecipeIngredient(174, 10f))),
    Recipe(3032, "酱牛肉", "家常菜", "1.牛腱子焯水\n2.加香料酱油炖2小时\n3.关火浸泡过夜\n4.切薄片装盘", listOf(RecipeIngredient(41, 500f), RecipeIngredient(136, 30f))),
    Recipe(3033, "可乐", "饮品", "", listOf(RecipeIngredient(129, 330f))),
    Recipe(3034, "豆浆油条", "早餐", "1.豆浆加热\n2.油条复炸酥脆\n3.豆浆加糖\n4.油条蘸食", listOf(RecipeIngredient(8, 250f))),
    Recipe(3035, "皮蛋瘦肉粥", "早餐", "1.米洗净煮粥\n2.瘦肉切丝腌制\n3.皮蛋切碎\n4.煮好加调味", listOf(RecipeIngredient(110, 100f), RecipeIngredient(30, 50f), RecipeIngredient(125, 30f))),
    Recipe(3036, "葱油饼", "早餐", "1.和面醒面\n2.擀开涂油撒葱花\n3.卷起再擀成饼\n4.煎至两面金黄", listOf(RecipeIngredient(116, 200f), RecipeIngredient(55, 20f))),
    Recipe(3037, "蒸饺", "早餐", "1.调馅包制\n2.上蒸锅\n3.大火蒸10分钟\n4.蘸醋食用", listOf(RecipeIngredient(152, 200f), RecipeIngredient(48, 150f), RecipeIngredient(117, 200f))),
    Recipe(3038, "肠粉", "早餐", "1.米浆调匀\n2.蒸盘刷油倒浆\n3.加肉末虾仁蒸熟\n4.刮起淋酱", listOf(RecipeIngredient(110, 100f), RecipeIngredient(34, 30f), RecipeIngredient(20, 30f))),
    Recipe(3039, "关东煮", "小吃", "1.白萝卜切块\n2.海带木鱼花熬汤\n3.加入鱼丸豆腐煮\n4.加酱油调味", listOf(RecipeIngredient(73, 100f), RecipeIngredient(96, 100f))),
    Recipe(3040, "烤红薯", "小吃", "1.红薯洗净\n2.烤箱200度40分钟\n3.翻面再烤20分钟", listOf(RecipeIngredient(119, 300f))),
    Recipe(3041, "奶油蘑菇汤", "汤羹", "1.蘑菇切片\n2.黄油炒香加面粉\n3.加牛奶煮浓稠\n4.加盐黑胡椒调味", listOf(RecipeIngredient(67, 150f), RecipeIngredient(2, 200f))),
    Recipe(3042, "烤鸡翅", "烤箱菜", "1.鸡翅划刀腌制\n2.烤箱200度预热\n3.烤20分钟翻面\n4.刷蜂蜜再烤5分钟", listOf(RecipeIngredient(13, 400f), RecipeIngredient(128, 15f))),
    Recipe(3043, "蒜香排骨", "烤箱菜", "1.排骨腌制(蒜末生抽)\n2.烤箱200度烤25分钟\n3.翻面再烤10分钟", listOf(RecipeIngredient(31, 500f), RecipeIngredient(56, 30f))),
    Recipe(3044, "蚝油生菜", "家常菜", "1.生菜洗净焯水\n2.铺盘\n3.热油爆香蒜末\n4.加蚝油生抽调汁淋上", listOf(RecipeIngredient(60, 200f), RecipeIngredient(56, 10f))),
    Recipe(3045, "清炒小白菜", "家常菜", "1.小白菜洗净切段\n2.热油爆香蒜末\n3.大火翻炒\n4.加盐调味即出", listOf(RecipeIngredient(78, 300f), RecipeIngredient(56, 10f))),
    Recipe(3046, "木耳炒鸡蛋", "家常菜", "1.木耳泡发撕小朵\n2.鸡蛋打散炒熟\n3.爆香葱花加木耳\n4.汇入鸡蛋翻炒", listOf(RecipeIngredient(69, 20f), RecipeIngredient(1, 100f))),
    Recipe(3047, "八宝粥", "粥品", "1.多种杂粮洗净泡水\n2.加水大火煮开\n3.转小火慢熬1小时\n4.加冰糖调味", listOf(RecipeIngredient(112, 50f), RecipeIngredient(101, 20f), RecipeIngredient(102, 20f), RecipeIngredient(113, 20f))),
    Recipe(3048, "酸辣粉", "小吃", "1.粉条泡软煮熟\n2.调酸辣碗底\n3.浇热汤\n4.加花生碎香菜", listOf(RecipeIngredient(176, 150f), RecipeIngredient(137, 15f), RecipeIngredient(173, 5f))),
    Recipe(3049, "海鲜炒乌冬", "主食", "1.乌冬面煮散\n2.虾仁鱿鱼炒熟\n3.加蔬菜翻炒\n4.加酱油调味", listOf(RecipeIngredient(20, 80f), RecipeIngredient(22, 80f), RecipeIngredient(55, 50f))),
)

// ============================================
// 👗 穿搭套装 (ID: 4000~4029)
// ============================================
val completeOutfitSets = listOf(
    OutfitSet(4000, "简约白T牛仔", "日常休闲", "白色+蓝色经典搭配", listOf(1000, 1018, 1026)),
    OutfitSet(4001, "黑色酷帅风", "街头潮流", "黑色T恤+黑裤+白鞋", listOf(1001, 1019, 1026)),
    OutfitSet(4002, "知性通勤", "职场通勤", "白衬衫+卡其裤+黑皮鞋", listOf(1004, 1020, 1027)),
    OutfitSet(4003, "休闲卫衣风", "周末出门", "灰色卫衣+牛仔裤+板鞋", listOf(1002, 1018, 1029)),
    OutfitSet(4004, "温婉连衣裙", "约会出行", "碎花连衣裙+帆布鞋", listOf(1035, 1028)),
    OutfitSet(4005, "秋冬大衣风", "秋冬穿搭", "大衣+高领毛衣+西裤+短靴", listOf(1012, 1010, 1021, 1030)),
    OutfitSet(4006, "运动休闲", "运动出行", "速干T恤+运动裤+跑鞋", listOf(1008, 1022, 1026)),
    OutfitSet(4007, "商务正装", "正式场合", "西装套装+白衬衫+领带+皮鞋", listOf(1046, 1004, 1048, 1049)),
    OutfitSet(4008, "温雅半身裙", "柔美风格", "白衬衫+黑半裙+平底鞋", listOf(1004, 1034, 1028)),
    OutfitSet(4009, "牛仔外套风", "街头混搭", "牛仔外套+条纹POLO+卡其裤", listOf(1015, 1003, 1020)),
    OutfitSet(4010, "轻奢皮靴风", "秋冬时髦", "风衣+毛衣+黑裤+短靴", listOf(1014, 1011, 1019, 1030)),
    OutfitSet(4011, "运动套装", "健身房", "运动内衣+速干T恤+运动裤", listOf(1039, 1008, 1022)),
    OutfitSet(4012, "暖系针织", "日常温柔", "针织开衫+白T+牛仔裤+帆布鞋", listOf(1007, 1000, 1018, 1028)),
    OutfitSet(4013, "户外冲锋", "户外露营", "冲锋衣+速干T恤+工装裤+登山鞋", listOf(1017, 1008, 1023, 1032)),
    OutfitSet(4014, "夏日清爽", "夏季穿搭", "白T+牛仔短裤+板鞋", listOf(1000, 1024, 1029)),
    OutfitSet(4015, "优雅百褶风", "淑女气质", "亚麻衬衫+百褶裙+凉鞋", listOf(1006, 1036, 1033)),
    OutfitSet(4016, "城市通勤", "日常通勤", "POLO衫+休闲裤+帆布鞋", listOf(1003, 1020, 1028)),
    OutfitSet(4017, "羽绒保暖", "冬季保暖", "羽绒服+高领毛衣+牛仔裤+短靴", listOf(1013, 1010, 1018, 1030)),
    OutfitSet(4018, "简约黑白配", "极简主义", "黑T+白鞋+黑裤,黑白配", listOf(1001, 1019, 1029)),
    OutfitSet(4019, "质感卡其风", "大地色系", "风衣+白衬衫+卡其裤+皮鞋", listOf(1014, 1004, 1020, 1027)),
    OutfitSet(4020, "工装飒爽", "中性风", "工装裤+黑T+板鞋", listOf(1023, 1001, 1029)),
    OutfitSet(4021, "学院风", "校园穿搭", "POLO衫+牛仔裤+帆布鞋", listOf(1003, 1018, 1028)),
    OutfitSet(4022, "清新碎花", "春夏出游", "碎花裙+牛仔外套+帆布鞋", listOf(1035, 1015, 1028)),
    OutfitSet(4023, "正装简搭", "商务休闲", "西装裤+白衬衫+帆布鞋", listOf(1021, 1004, 1028)),
    OutfitSet(4024, "舒适居家", "居家休息", "宽松卫衣+运动裤+拖鞋", listOf(1002, 1022, 1031)),
    OutfitSet(4025, "夜跑装备", "运动夜跑", "速干T恤+运动裤+跑鞋", listOf(1008, 1022, 1026)),
    OutfitSet(4026, "冬日暖阳", "冬季通勤", "羽绒服+毛衣+黑裤+靴子", listOf(1013, 1011, 1019, 1030)),
    OutfitSet(4027, "英伦格调", "复古风格", "风衣+毛衣+牛仔裤+短靴", listOf(1014, 1011, 1018, 1030)),
    OutfitSet(4028, "简约通勤", "职场日常", "白衬衫+黑裤+皮鞋", listOf(1004, 1019, 1027)),
    OutfitSet(4029, "派对小礼服", "正式派对", "小黑裙+高跟鞋", listOf(1034, 1050)),
)

// ============================================
// 🧱 扩展建筑建材 (ID: 5011~5030)
// 原有 5001~5010 已在 Models.kt 中定义
// ============================================
val extendedBuildingMaterials = listOf(
    MaterialItem(5011, "碎石", "建材", "吨", 120.0, colorId = 5, "混凝土骨料"),
    MaterialItem(5012, "大理石", "建材", "平方米", 350.0, colorId = 1, "高档地面装饰石材"),
    MaterialItem(5013, "花岗岩", "建材", "平方米", 280.0, colorId = 5, "室外地面/墙面石材"),
    MaterialItem(5014, "石膏板", "建材", "平方米", 25.0, colorId = 1, "室内吊顶隔墙材料"),
    MaterialItem(5015, "矿棉板", "建材", "平方米", 35.0, colorId = 1, "吸音吊顶材料"),
    MaterialItem(5016, "防水卷材", "建材", "卷", 350.0, colorId = 2, "屋顶防水材料"),
    MaterialItem(5017, "保温板", "建材", "平方米", 45.0, colorId = 1, "外墙保温材料"),
    MaterialItem(5018, "铝合金门窗", "建材", "平方米", 500.0, colorId = 1, "断桥铝门窗"),
    MaterialItem(5019, "不锈钢管", "建材", "米", 30.0, colorId = 5, "扶手/管道不锈钢材料"),
    MaterialItem(5020, "PVC地板", "建材", "平方米", 60.0, colorId = 3, "塑胶地板材料"),
    MaterialItem(5021, "实木地板", "建材", "平方米", 200.0, colorId = 3, "高档实木地板"),
    MaterialItem(5022, "乳胶漆", "建材", "桶", 300.0, colorId = 1, "内墙环保涂料"),
    MaterialItem(5023, "墙纸", "建材", "卷", 150.0, colorId = 1, "壁纸装饰材料"),
    MaterialItem(5024, "水泥砂浆", "建材", "吨", 300.0, colorId = 5, "砌筑抹灰材料"),
    MaterialItem(5025, "钢筋网", "建材", "平方米", 80.0, colorId = 5, "混凝土增强材料"),
    MaterialItem(5026, "石灰", "建材", "吨", 250.0, colorId = 1, "建筑石灰材料"),
    MaterialItem(5027, "河沙", "建材", "吨", 80.0, colorId = 3, "细沙,混凝土用"),
    MaterialItem(5028, "玻璃棉", "建材", "平方米", 30.0, colorId = 1, "保温隔音材料"),
    MaterialItem(5029, "防火板", "建材", "平方米", 55.0, colorId = 10, "防火装饰板材"),
    MaterialItem(5030, "硅钙板", "建材", "平方米", 20.0, colorId = 1, "轻质隔墙材料"),
)

// ============================================
// 🔌 扩展电子材料 (ID: 6011~6030)
// 原有 6001~6010 已在 Models.kt 中定义
// ============================================
val extendedElectronicMaterials = listOf(
    MaterialItem(6011, "内存条", "电子料", "条", 200.0, colorId = 2, "DDR4/DDR5内存模组"),
    MaterialItem(6012, "固态硬盘", "电子料", "个", 300.0, colorId = 5, "SSD存储"),
    MaterialItem(6013, "CPU处理器", "电子料", "个", 1500.0, colorId = 2, "中央处理器"),
    MaterialItem(6014, "GPU显卡", "电子料", "个", 3000.0, colorId = 2, "图形处理器"),
    MaterialItem(6015, "主板", "电子料", "片", 800.0, colorId = 4, "PCB主板"),
    MaterialItem(6016, "电源适配器", "电子料", "个", 80.0, colorId = 2, "充电器/电源模块"),
    MaterialItem(6017, "扬声器", "电子料", "个", 25.0, colorId = 2, "微型扬声器模组"),
    MaterialItem(6018, "麦克风", "电子料", "个", 15.0, colorId = 2, "微型麦克风"),
    MaterialItem(6019, "散热器", "电子料", "个", 60.0, colorId = 5, "CPU/芯片散热模组"),
    MaterialItem(6020, "震动马达", "电子料", "个", 10.0, colorId = 5, "手机震动马达"),
    MaterialItem(6021, "NFC芯片", "电子料", "个", 5.0, colorId = 2, "近场通信芯片"),
    MaterialItem(6022, "WiFi模块", "电子料", "个", 25.0, colorId = 2, "无线通信模块"),
    MaterialItem(6023, "蓝牙芯片", "电子料", "个", 8.0, colorId = 2, "蓝牙通信芯片"),
    MaterialItem(6024, "GPS模块", "电子料", "个", 20.0, colorId = 2, "定位导航模块"),
    MaterialItem(6025, "指纹传感器", "电子料", "个", 30.0, colorId = 5, "生物识别传感器"),
    MaterialItem(6026, "电池保护板", "电子料", "片", 15.0, colorId = 4, "锂电池保护电路"),
    MaterialItem(6027, "柔性电路板", "电子料", "片", 40.0, colorId = 4, "FPC柔性板"),
    MaterialItem(6028, "电阻电容套装", "电子料", "套", 10.0, colorId = 3, "被动元器件"),
    MaterialItem(6029, "连接器", "电子料", "个", 5.0, colorId = 5, "电子连接器"),
    MaterialItem(6030, "天线模组", "电子料", "个", 12.0, colorId = 5, "通信天线"),
)

// ============================================
// 🏭 扩展工业材料 (ID: 7011~7030)
// 原有 7001~7010 已在 Models.kt 中定义
// ============================================
val extendedIndustrialMaterials = listOf(
    MaterialItem(7011, "钛合金", "工业料", "吨", 280000.0, colorId = 5, "高强度轻质合金"),
    MaterialItem(7012, "不锈钢304", "工业料", "吨", 15000.0, colorId = 5, "食品级不锈钢"),
    MaterialItem(7013, "镀锌板", "工业料", "吨", 6000.0, colorId = 5, "防腐钢板"),
    MaterialItem(7014, "硅胶", "工业料", "千克", 40.0, colorId = 1, "密封/绝缘材料"),
    MaterialItem(7015, "聚氨酯", "工业料", "吨", 18000.0, colorId = 1, "高性能塑料"),
    MaterialItem(7016, "环氧树脂", "工业料", "千克", 50.0, colorId = 3, "高性能粘接材料"),
    MaterialItem(7017, "陶瓷纤维", "工业料", "千克", 80.0, colorId = 1, "耐高温材料"),
    MaterialItem(7018, "合成橡胶", "工业料", "吨", 15000.0, colorId = 2, "丁苯橡胶等"),
    MaterialItem(7019, "铝合金(工业)", "工业料", "吨", 20000.0, colorId = 1, "工业铝合金型材"),
    MaterialItem(7020, "铜合金", "工业料", "吨", 70000.0, colorId = 3, "黄铜/青铜等"),
    MaterialItem(7021, "石墨", "工业料", "千克", 60.0, colorId = 2, "润滑/导电材料"),
    MaterialItem(7022, "稀土材料", "工业料", "千克", 500.0, colorId = 5, "磁性材料/催化剂"),
    MaterialItem(7023, "碳化硅", "工业料", "千克", 200.0, colorId = 2, "超硬材料"),
    MaterialItem(7024, "亚克力板", "工业料", "千克", 25.0, colorId = 1, "透明塑料板材"),
    MaterialItem(7025, "聚四氟乙烯", "工业料", "千克", 150.0, colorId = 1, "PTFE特种塑料"),
    MaterialItem(7026, "镀铬剂", "工业料", "升", 80.0, colorId = 1, "表面处理材料"),
    MaterialItem(7027, "焊料", "工业料", "千克", 100.0, colorId = 5, "焊接材料"),
    MaterialItem(7028, "涂料(工业)", "工业料", "桶", 800.0, colorId = 1, "工业防腐涂料"),
    MaterialItem(7029, "碳纳米管", "工业料", "克", 50.0, colorId = 2, "纳米新材料"),
    MaterialItem(7030, "生物降解塑料", "工业料", "吨", 12000.0, colorId = 1, "环保材料"),
)

// ============================================
// 🏘️ 扩展房产成品 (ID: 8009~8015)
// ============================================原有 8001~8008 已在 Models.kt 中定义
// ============================================
val extendedHousingProducts = listOf(
    ProductItem(8009, "小户型LOFT", "房产", colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "40-60㎡ 挑高复式"),
    ProductItem(8010, "大平层", "房产", colorId = 3, shapeType = PixelShapeType.SQUARE, description = "200-300㎡ 平层豪宅"),
    ProductItem(8011, "四合院", "房产", colorId = 4, shapeType = PixelShapeType.SQUARE, description = "传统中式院落 300㎡+"),
    ProductItem(8012, "度假别墅", "房产", colorId = 4, shapeType = PixelShapeType.SQUARE, description = "依山傍水 500㎡+"),
    ProductItem(8013, "学生公寓", "房产", colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "高校周边 15-30㎡"),
    ProductItem(8014, "养老公寓", "房产", colorId = 3, shapeType = PixelShapeType.SQUARE, description = "适老化设计 50-80㎡"),
    ProductItem(8015, "社区商铺", "房产", colorId = 3, shapeType = PixelShapeType.HORIZONTAL, description = "底商 30-80㎡"),
)

// ============================================
// 💻 扩展数码成品 (ID: 9009~9020)
// 原有 9001~9008 已在 Models.kt 中定义
// ============================================
val extendedDigitalProducts = listOf(
    ProductItem(9009, "真无线耳机", "数码", colorId = 1, shapeType = PixelShapeType.SQUARE, description = "真无线 主动降噪"),
    ProductItem(9010, "头戴式耳机", "数码", colorId = 2, shapeType = PixelShapeType.SQUARE, description = "HiFi 有线/无线"),
    ProductItem(9011, "智能手表Pro", "数码", colorId = 2, shapeType = PixelShapeType.SQUARE, description = "eSIM 独立通话"),
    ProductItem(9012, "平板电脑Mini", "数码", colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "8寸 便携小屏"),
    ProductItem(9013, "游戏主机", "数码", colorId = 2, shapeType = PixelShapeType.SQUARE, description = "4K 游戏主机"),
    ProductItem(9014, "数码相机", "数码", colorId = 2, shapeType = PixelShapeType.SQUARE, description = "APS-C 微单"),
    ProductItem(9015, "投影仪", "数码", colorId = 1, shapeType = PixelShapeType.SQUARE, description = "1080P 智能投影"),
    ProductItem(9016, "路由器", "数码", colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "WiFi6 千兆"),
    ProductItem(9017, "移动硬盘", "数码", colorId = 5, shapeType = PixelShapeType.SQUARE, description = "2TB 便携存储"),
    ProductItem(9018, "智能音箱", "数码", colorId = 5, shapeType = PixelShapeType.SQUARE, description = "AI语音助手"),
    ProductItem(9019, "电子书阅读器", "数码", colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "6寸 墨水屏"),
    ProductItem(9020, "VR头显", "数码", colorId = 2, shapeType = PixelShapeType.SQUARE, description = "VR一体机"),
)

// ============================================
// 🚗 扩展交通工具 (ID: 10009~10015)
// 原有 10001~10008 已在 Models.kt 中定义
// ============================================
val extendedCarProducts = listOf(
    ProductItem(10009, "混合动力轿车", "汽车", colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "油电混动 省油"),
    ProductItem(10010, "纯电SUV", "汽车", colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "续航700km 纯电"),
    ProductItem(10011, "皮卡车", "汽车", colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "双排座 载货1吨"),
    ProductItem(10012, "自行车(山地)", "汽车", colorId = 3, shapeType = PixelShapeType.HORIZONTAL, description = "21速 山地车"),
    ProductItem(10013, "电动车(48V)", "汽车", colorId = 1, shapeType = PixelShapeType.VERTICAL, description = "新国标 代步"),
    ProductItem(10014, "滑板车(电动)", "汽车", colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "便携 短途代步"),
    ProductItem(10015, "三轮车(电动)", "汽车", colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "载货400kg 快递"),
)

// ============================================
// 🔗 扩展成品-原材料组成关系
// ============================================
val extendedCompositions = listOf(
    // --- 新房产组成 (8009~8015) ---
    CompositionRelation(8009, 5001, 8.0, "吨"),      // LOFT：水泥8吨
    CompositionRelation(8009, 5002, 2.5, "吨"),      // LOFT：钢筋2.5吨
    CompositionRelation(8009, 5003, 12.0, "吨"),     // LOFT：沙子12吨
    CompositionRelation(8009, 5018, 50.0, "平方米"), // LOFT：铝合金门窗50㎡
    CompositionRelation(8009, 5017, 80.0, "平方米"), // LOFT：保温板80㎡

    CompositionRelation(8010, 5001, 25.0, "吨"),     // 大平层：水泥25吨
    CompositionRelation(8010, 5002, 8.0, "吨"),      // 大平层：钢筋8吨
    CompositionRelation(8010, 5003, 30.0, "吨"),     // 大平层：沙子30吨
    CompositionRelation(8010, 5012, 150.0, "平方米"),// 大平层：大理石150㎡
    CompositionRelation(8010, 5021, 120.0, "平方米"),// 大平层：实木地板120㎡
    CompositionRelation(8010, 5022, 10.0, "桶"),     // 大平层：乳胶漆10桶

    CompositionRelation(8011, 5001, 30.0, "吨"),     // 四合院：水泥30吨
    CompositionRelation(8011, 5010, 15000.0, "块"),  // 四合院：砖块15000块
    CompositionRelation(8011, 5005, 30.0, "立方米"), // 四合院：木材30m³
    CompositionRelation(8011, 5013, 200.0, "平方米"),// 四合院：花岗岩200㎡

    // --- 新数码组成 (9009~9020) ---
    CompositionRelation(9009, 6004, 2.0, "个"),      // 耳机：芯片2个
    CompositionRelation(9009, 6003, 1.0, "个"),      // 耳机：锂电池1个
    CompositionRelation(9009, 6023, 1.0, "个"),      // 耳机：蓝牙芯片1个
    CompositionRelation(9009, 6007, 1.0, "个"),      // 耳机：塑料外壳1个
    CompositionRelation(9009, 6017, 2.0, "个"),      // 耳机：扬声器2个

    CompositionRelation(9010, 6004, 1.0, "个"),      // 头戴耳机：芯片1个
    CompositionRelation(9010, 6017, 2.0, "个"),      // 头戴耳机：扬声器2个
    CompositionRelation(9010, 7024, 0.3, "千克"),    // 头戴耳机：亚克力板0.3kg
    CompositionRelation(9010, 6018, 1.0, "个"),      // 头戴耳机：麦克风1个

    CompositionRelation(9011, 6005, 1.0, "个"),      // 智能手表Pro：显示屏1个
    CompositionRelation(9011, 6004, 2.0, "个"),      // 智能手表Pro：芯片2个
    CompositionRelation(9011, 6003, 1.0, "个"),      // 智能手表Pro：电池1个
    CompositionRelation(9011, 6009, 5.0, "个"),      // 智能手表Pro：传感器5个
    CompositionRelation(9011, 6020, 1.0, "个"),      // 智能手表Pro：震动马达1个
    CompositionRelation(9011, 6021, 1.0, "个"),      // 智能手表Pro：NFC芯片1个

    CompositionRelation(9016, 6004, 2.0, "个"),      // 路由器：芯片2个
    CompositionRelation(9016, 6022, 1.0, "个"),      // 路由器：WiFi模块1个
    CompositionRelation(9016, 6006, 1.0, "片"),      // 路由器：电路板1片
    CompositionRelation(9016, 6029, 4.0, "个"),      // 路由器：连接器4个

    // --- 新汽车组成 (10009~10015) ---
    CompositionRelation(10009, 7001, 1.0, "吨"),     // 混动轿车：钢材1吨
    CompositionRelation(10009, 7002, 0.2, "吨"),     // 混动轿车：铝材0.2吨
    CompositionRelation(10009, 7003, 40.0, "千克"),  // 混动轿车：橡胶40kg
    CompositionRelation(10009, 7007, 80.0, "千克"),  // 混动轿车：塑料颗粒80kg
    CompositionRelation(10009, 6003, 1.0, "个"),     // 混动轿车：动力电池1个

    CompositionRelation(10010, 7001, 1.3, "吨"),     // 纯电SUV：钢材1.3吨
    CompositionRelation(10010, 7002, 0.4, "吨"),     // 纯电SUV：铝材0.4吨
    CompositionRelation(10010, 7005, 30.0, "千克"),  // 纯电SUV：碳纤维30kg
    CompositionRelation(10010, 6003, 1.0, "个"),     // 纯电SUV：动力电池1个
    CompositionRelation(10010, 6004, 60.0, "个"),    // 纯电SUV：芯片60个

    CompositionRelation(10012, 7012, 2.0, "千克"),   // 山地自行车：不锈钢2kg
    CompositionRelation(10012, 7020, 0.5, "千克"),   // 山地自行车：铜合金0.5kg
    CompositionRelation(10012, 7003, 1.0, "千克"),   // 山地自行车：橡胶1kg

    CompositionRelation(10013, 7001, 5.0, "千克"),   // 电动车：钢材5kg
    CompositionRelation(10013, 6003, 1.0, "个"),     // 电动车：电池1个
    CompositionRelation(10013, 7003, 0.5, "千克"),   // 电动车：橡胶0.5kg
    CompositionRelation(10013, 7007, 2.0, "千克"),   // 电动车：塑料颗粒2kg
)

// ============================================
// 📦 所有新增材料汇总
// ============================================
val allExtendedMaterials: List<MaterialItem> by lazy {
    emptyList<MaterialItem>() // 扩展材料通过 extendedBuildingMaterials + extendedElectronicMaterials + extendedIndustrialMaterials 单独访问
}

// ============================================
// 📦 所有新增产品汇总
// ============================================
val allExtendedProducts: List<ProductItem> by lazy {
    extendedHousingProducts + extendedDigitalProducts + extendedCarProducts
}