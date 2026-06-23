package com.example.townapp.data

data class GlobalSettings(
    val city: String = "大城市",
    val customLocation: String = "",           // 用户自定义地点名
    val customRent: Double = 0.0,              // 用户自定义月租（0表示使用预设）
    val currency: String = "¥",
    val awakeningMode: Boolean = false,
    val monthlyIncome: Double = 10000.0,
    val workHoursPerDay: Double = 8.0,
    val workDaysPerMonth: Double = 22.0,
    val commuteHoursPerDay: Double = 1.0,
    val overtimeHoursPerDay: Double = 0.0,
    val advancedMode: Boolean = false,
    val awakeningLevel: Int = 1,
    val totalAwakeningPoints: Int = 0,
    val weatherState: String = "sunny",
    val showCognitiveTipButton: Boolean = true,  // 清醒提示按钮可见性开关
    val showSpotlight: Boolean = true,           // 闪光点板块可见性开关
    val childhoodMode: Boolean = false           // 童年模式
)

data class FabricInfo(
    val baseCost: Double,
    val durability: Double,
    val comfort: Double,
    val breathability: Double
)

data class FoodMaterialInfo(
    val category: String,
    val basePrice: Double,
    val nutrition: Map<String, Double>,
    val shelfLifeDays: Double,
    val breedingDegradation: Double,
    val processingLoss: Double
)

data class CityPreset(
    val name: String,
    val avgRent: Double,
    val avgHousePrice: Double,
    val avgMealCost: Double,
    val tier: Int,
    val costIndex: Double
)

data class ItemTemplate(
    val name: String,
    val category: String,
    val materials: List<String>,
    val weight: Double,
    val thickness: Double,
    val maxWears: Int,
    val lifespanYears: Double,
    val versatility: Double,
    val comfort: Double,
    val isLuxury: Boolean,
    val iqTaxRisk: Double,
    val fashionRisk: Double,
    val priceRange: String,
    val referencePrice: Double
)

data class CognitiveBias(
    val id: Int,
    val name: String,
    val category: String,
    val definition: String,
    val example: String,
    val impact: String,
    val howToAvoid: String,
    val insight: String
)

// 食物营养信息类
data class SimpleFoodNutrition(
    val name: String,
    val nutritionScore: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double
)
