package com.example.townapp.data.model

enum class BuildingType {
    FOOD_NEGATIVE,
    FOOD_POSITIVE,
    CLOTHING_NEGATIVE,
    CLOTHING_POSITIVE,
    HOUSING_NEGATIVE,
    HOUSING_POSITIVE,
    MENTAL_NEGATIVE,
    MENTAL_POSITIVE
}

enum class TownDistrict {
    DIGESTIVE_TRACT,    // 消化道
    SKIN_KINGDOM,       // 皮肤国
    DWELLING_REALM,     // 栖息界
    SPIRIT_DOMAIN       // 精神域
}

data class TownBuilding(
    val id: String,
    val name: String,
    val type: BuildingType,
    val district: TownDistrict,
    val triggerCondition: String,
    val visualEffect: String,
    val pollutionImpact: Int = 0,
    val healthImpact: Int = 0,
    val debtImpact: Int = 0,
    val awakeningImpact: Int = 0,
    val happinessImpact: Int = 0,
    val productivityImpact: Int = 0,
    val redundancyLevel: Int = 0,
    val dignityImpact: Int = 0,
    val realitySenseImpact: Int = 0,
    val lonelinessImpact: Int = 0,
    val socialAbilityImpact: Int = 0,
    val problemAvoidanceImpact: Int = 0,
    val realProblemSolvingImpact: Int = 0,
    val creativityImpact: Int = 0,
    val emotionalIntelligenceImpact: Int = 0,
    val selfEsteemImpact: Int = 0,
    val mentalHealthImpact: Int = 0,
    val wisdomImpact: Int = 0,
    val anxietyImpact: Int = 0,
    val gastroenteritisRiskImpact: Int = 0,
    val futureDebtImpact: Int = 0,
    val friendshipImpact: Int = 0,
    val familyBondImpact: Int = 0,
    val clarityImpact: Int = 0,
    val mindfulnessImpact: Int = 0,
    val futureSecurityImpact: Int = 0
)

data class TownNPC(
    val id: String,
    val name: String,
    val role: String,
    val isNegative: Boolean,
    val dialogue: String,
    val avatar: String
)

data class TownEvent(
    val id: String,
    val name: String,
    val description: String,
    val isNegative: Boolean,
    val triggerCondition: String,
    val effects: Map<String, Int>,
    val durationDays: Int
)

data class TownState(
    val pollutionLevel: Int,
    val debtLevel: Int,
    val healthLevel: Int,
    val awakeningLevel: Int,
    val happinessLevel: Int,
    val productivityLevel: Int,
    val redundancyLevel: Int,
    val aestheticLevel: Int,
    val weather: WeatherState,
    val activeBuildings: List<TownBuilding>,
    val activeEvents: List<TownEvent>,
    val currentNPCs: List<TownNPC>
)

enum class WeatherState(val icon: String, val description: String) {
    SUNNY("☀️", "晴天"),
    CLOUDY("⛅", "多云"),
    OVERCAST("☁️", "阴天"),
    HAZY("😶‍🌫️", "雾霾"),
    RAINY("🌧️", "小雨"),
    STORM("⛈️", "暴风雨")
}

data class DecorationStyle(
    val valueDensity: Double,
    val spaceUtilization: Double,
    val happinessBonus: Double,
    val awakeningBonus: Double
)

data class FoodStats(
    val heavyMetalCount: Int = 0,
    val friedFoodCount: Int = 0,
    val overnightFoodCount: Int = 0,
    val tooHotFoodCount: Int = 0,
    val sugaryDrinkCount: Int = 0,
    val alcoholCount: Int = 0,
    val smokingCount: Int = 0,
    val steamedFoodCount: Int = 0,
    val vegetableCount: Int = 0,
    val deliciousMealCount: Int = 0,
    val spoiledFoodCount: Int = 0,
    val eatsUntilFull: Boolean = true
)

data class ClothingStats(
    val luxuryItemCount: Int = 0,
    val cosmeticsCount: Int = 0,
    val originalAccessoryCount: Int = 0,
    val totalClothingCount: Int = 0,
    val idleClothingCount: Int = 0,
    val highValueDensityCount: Int = 0,
    val discardedCount: Int = 0
)

data class HousingStats(
    val livesInBasement: Boolean = false,
    val hasHouseLoan: Boolean = false,
    val hasCar: Boolean = false,
    val commuteTime: Double = 0.0,
    val hasSunlight: Boolean = true,
    val carCount: Int = 0,
    val usesPublicTransport: Boolean = false,
    val decorationStyle: String = "毛坯房",
    val idleItemsCount: Int = 0
)

data class MentalStats(
    val novelHours: Double = 0.0,
    val shortVideoHours: Double = 0.0,
    val historyHours: Double = 0.0,
    val classicalChineseHours: Double = 0.0,
    val latinHours: Double = 0.0,
    val englishHours: Double = 0.0,
    val scienceHours: Double = 0.0,
    val programmingHours: Double = 0.0,
    val aestheticValue: Int = 0,
    val criticalThinkingHours: Double = 0.0,
    val readingHours: Double = 0.0,
    val workHours: Double = 0.0,
    val learningHours: Double = 0.0,
    val gamingHours: Double = 0.0,
    val monthlyLiveDonation: Double = 0.0,
    val monthlyGameSpending: Double = 0.0,
    val monthlySuperstitionSpending: Double = 0.0,
    val isAddictedToLive: Boolean = false,
    val totalAwakeningPoints: Int = 0,
    val sunExposureHours: Double = 0.0,
    val solarTermTaboos: Int = 0,
    val astrologyHours: Double = 0.0,
    val pseudoscienceSpending: Double = 0.0,
    val trustsModernMedicine: Boolean = false,
    val doesExperiments: Boolean = false,
    val detoxCompleted: Boolean = false,
    val dailyDeleteCount: Int = 0,
    val weeklyReviewCount: Int = 0,
    val comparisonCount: Int = 0,
    val observationCount: Int = 0,
    val principlesMastered: Int = 0,
    // 消费认知追踪
    val socialPartyCount: Int = 0,        // 应酬聚餐次数
    val luxuryPurchaseCount: Int = 0,      // 奢侈品/面子消费次数
    val commodityHoardCount: Int = 0,      // 囤积消费品次数
    val impatientPursuitCount: Int = 0     // 功利追逐次数
)
