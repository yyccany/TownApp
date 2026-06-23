package com.example.townapp.feature.town_simulation

import com.example.townapp.data.model.*
import com.example.townapp.data.repository.TownRepository

object TownBusiness {
    fun calculateTownState(
        foodStats: FoodStats,
        clothingStats: ClothingStats,
        housingStats: HousingStats,
        mentalStats: MentalStats
    ): TownState {
        val pollutionLevel = calculatePollutionLevel(foodStats, housingStats, mentalStats)
        val debtLevel = calculateDebtLevel(clothingStats, housingStats, mentalStats)
        val healthLevel = calculateHealthLevel(foodStats, housingStats)
        val awakeningLevel = calculateAwakeningLevel(mentalStats, housingStats)
        val happinessLevel = calculateHappinessLevel(foodStats, housingStats, mentalStats)
        val productivityLevel = calculateProductivityLevel(housingStats, mentalStats)
        val redundancyLevel = calculateRedundancyLevel(clothingStats, housingStats)
        val aestheticLevel = calculateAestheticLevel(housingStats, mentalStats)
        val weather = determineWeather(pollutionLevel, awakeningLevel, happinessLevel)
        val activeBuildings = determineActiveBuildings(foodStats, clothingStats, housingStats, mentalStats)
        val activeEvents = determineActiveEvents(foodStats, mentalStats, debtLevel, awakeningLevel)
        val currentNPCs = determineNPCs(awakeningLevel, debtLevel)

        return TownState(
            pollutionLevel = pollutionLevel,
            debtLevel = debtLevel,
            healthLevel = healthLevel,
            awakeningLevel = awakeningLevel,
            happinessLevel = happinessLevel,
            productivityLevel = productivityLevel,
            redundancyLevel = redundancyLevel,
            aestheticLevel = aestheticLevel,
            weather = weather,
            activeBuildings = activeBuildings,
            activeEvents = activeEvents,
            currentNPCs = currentNPCs
        )
    }

    private fun calculatePollutionLevel(
        foodStats: FoodStats,
        housingStats: HousingStats,
        mentalStats: MentalStats
    ): Int {
        var pollution = 50

        pollution += foodStats.heavyMetalCount * 10
        pollution += foodStats.friedFoodCount * 5
        pollution += foodStats.overnightFoodCount * 3
        pollution += foodStats.sugaryDrinkCount * 2

        pollution += housingStats.carCount * 5
        pollution += if (housingStats.livesInBasement) 30 else 0

        pollution += mentalStats.shortVideoHours.toInt() * 2
        pollution += mentalStats.gamingHours.toInt() * 1

        return pollution.coerceIn(0, 100)
    }

    private fun calculateDebtLevel(
        clothingStats: ClothingStats,
        housingStats: HousingStats,
        mentalStats: MentalStats
    ): Int {
        var debt = 50

        debt += clothingStats.luxuryItemCount * 50
        debt += clothingStats.cosmeticsCount * 10
        debt += clothingStats.originalAccessoryCount * 20

        debt += if (housingStats.hasHouseLoan) 100 else 0
        debt += if (housingStats.hasCar) 40 else 0

        debt += mentalStats.monthlyLiveDonation.toInt() / 2
        debt += mentalStats.monthlyGameSpending.toInt() / 2

        return debt.coerceIn(0, 100)
    }

    private fun calculateHealthLevel(foodStats: FoodStats, housingStats: HousingStats): Int {
        var health = 70

        health -= foodStats.friedFoodCount * 10
        health -= foodStats.sugaryDrinkCount * 8
        health -= foodStats.overnightFoodCount * 5
        health -= foodStats.tooHotFoodCount * 15
        health -= foodStats.alcoholCount * 20
        health -= foodStats.smokingCount * 25

        health += foodStats.steamedFoodCount * 10
        health += foodStats.vegetableCount * 5
        health += if (foodStats.eatsUntilFull) -10 else 10

        health -= if (housingStats.livesInBasement) 20 else 0
        health += if (housingStats.hasSunlight) 15 else 0

        return health.coerceIn(0, 100)
    }

    private fun calculateAwakeningLevel(mentalStats: MentalStats, housingStats: HousingStats): Int {
        var awakening = 50

        awakening -= mentalStats.novelHours.toInt() * 10
        awakening -= mentalStats.shortVideoHours.toInt() * 8
        awakening -= mentalStats.historyHours.toInt() * 5
        awakening -= mentalStats.classicalChineseHours.toInt() * 8
        awakening -= mentalStats.latinHours.toInt() * 8

        awakening += mentalStats.englishHours.toInt() * 10
        awakening += mentalStats.scienceHours.toInt() * 15
        awakening += mentalStats.programmingHours.toInt() * 20
        awakening += mentalStats.criticalThinkingHours.toInt() * 25

        val decorationBonus = HousingBusiness.calculateDecorationBonus(housingStats)
        awakening += decorationBonus.awakeningBonus.toInt()

        return awakening.coerceIn(0, 100)
    }

    private fun calculateHappinessLevel(
        foodStats: FoodStats,
        housingStats: HousingStats,
        mentalStats: MentalStats
    ): Int {
        var happiness = 60

        happiness += foodStats.deliciousMealCount * 5
        happiness -= foodStats.spoiledFoodCount * 10

        happiness += if (housingStats.hasSunlight) 15 else 0
        happiness -= if (housingStats.commuteTime > 2) 20 else 0
        happiness -= if (housingStats.hasHouseLoan) 30 else 0

        val decorationBonus = HousingBusiness.calculateDecorationBonus(housingStats)
        happiness += decorationBonus.happinessBonus.toInt()

        happiness -= mentalStats.shortVideoHours.toInt() * 5
        happiness += mentalStats.readingHours.toInt() * 8
        happiness -= if (mentalStats.isAddictedToLive) 20 else 0

        return happiness.coerceIn(0, 100)
    }

    private fun calculateProductivityLevel(housingStats: HousingStats, mentalStats: MentalStats): Int {
        var productivity = 60

        productivity -= if (housingStats.commuteTime > 2) 20 else 0

        productivity -= mentalStats.shortVideoHours.toInt() * 10
        productivity -= mentalStats.gamingHours.toInt() * 8
        productivity -= mentalStats.novelHours.toInt() * 5

        productivity += mentalStats.workHours.toInt() * 10
        productivity += mentalStats.learningHours.toInt() * 15

        return productivity.coerceIn(0, 100)
    }

    private fun calculateRedundancyLevel(clothingStats: ClothingStats, housingStats: HousingStats): Int {
        var redundancy = 30

        redundancy += (clothingStats.totalClothingCount - 30).coerceAtLeast(0)
        redundancy += clothingStats.idleClothingCount * 2

        val decorationBonus = HousingBusiness.calculateDecorationBonus(housingStats)
        redundancy += decorationBonus.redundancyBonus.toInt()

        return redundancy.coerceIn(0, 100)
    }

    private fun calculateAestheticLevel(housingStats: HousingStats, mentalStats: MentalStats): Int {
        var aesthetic = mentalStats.aestheticValue.coerceIn(0, 100)

        val styleBonus = when (housingStats.decorationStyle) {
            "适老化日式" -> 25
            "日式极简" -> 20
            "现代简约" -> 10
            "北欧风" -> 15
            "新中式" -> 5
            "欧式豪华" -> -15
            "网红ins风" -> -25
            "杂物堆" -> -30
            else -> 0
        }
        aesthetic += styleBonus

        if (housingStats.idleItemsCount <= 10) {
            aesthetic += 15
        } else if (housingStats.idleItemsCount >= 50) {
            aesthetic -= 20
        }

        return aesthetic.coerceIn(0, 100)
    }

    private fun determineWeather(pollution: Int, awakening: Int, happiness: Int): WeatherState {
        return when {
            pollution >= 80 || happiness < 20 -> WeatherState.STORM
            pollution >= 60 || awakening < 30 -> WeatherState.RAINY
            pollution >= 40 || awakening < 50 -> WeatherState.HAZY
            pollution >= 20 || awakening < 70 -> WeatherState.OVERCAST
            awakening >= 80 && happiness >= 70 -> WeatherState.SUNNY
            else -> WeatherState.CLOUDY
        }
    }

    private fun determineActiveBuildings(
        foodStats: FoodStats,
        clothingStats: ClothingStats,
        housingStats: HousingStats,
        mentalStats: MentalStats
    ): List<TownBuilding> {
        val buildings = mutableListOf<TownBuilding>()

        if (foodStats.heavyMetalCount > 0) {
            buildings.add(TownRepository.foodNegativeBuildings[0])
        }
        if (foodStats.friedFoodCount > 3) {
            buildings.add(TownRepository.foodNegativeBuildings[1])
        }
        if (foodStats.tooHotFoodCount > 5) {
            buildings.add(TownRepository.foodNegativeBuildings[2])
        }
        if (foodStats.overnightFoodCount > 3) {
            buildings.add(TownRepository.foodNegativeBuildings[3])
        }
        if (foodStats.sugaryDrinkCount > 5) {
            buildings.add(TownRepository.foodNegativeBuildings[6])
        }

        if (clothingStats.luxuryItemCount > 2) {
            buildings.add(TownRepository.clothingNegativeBuildings[0])
        }
        if (clothingStats.cosmeticsCount > 5) {
            buildings.add(TownRepository.clothingNegativeBuildings[2])
        }
        if (clothingStats.totalClothingCount > 100) {
            buildings.add(TownRepository.clothingNegativeBuildings[4])
        }

        if (housingStats.livesInBasement) {
            buildings.add(TownRepository.housingNegativeBuildings[0])
        }
        if (housingStats.hasHouseLoan) {
            buildings.add(TownRepository.housingNegativeBuildings[1])
        }
        if (housingStats.hasCar) {
            buildings.add(TownRepository.housingNegativeBuildings[2])
        }
        if (housingStats.commuteTime > 2) {
            buildings.add(TownRepository.housingNegativeBuildings[3])
        }
        if (housingStats.decorationStyle == "欧式豪华") {
            buildings.add(TownRepository.housingNegativeBuildings[4])
        }
        if (housingStats.decorationStyle == "网红ins风") {
            buildings.add(TownRepository.housingNegativeBuildings[5])
        }
        if (housingStats.idleItemsCount >= 50) {
            buildings.add(TownRepository.housingNegativeBuildings[6])
        }

        if (mentalStats.novelHours >= 3) {
            buildings.add(TownRepository.mentalNegativeBuildings[0])
        }
        if (mentalStats.shortVideoHours >= 2) {
            buildings.add(TownRepository.mentalNegativeBuildings[1])
        }
        if (mentalStats.monthlyLiveDonation >= 100) {
            buildings.add(TownRepository.mentalNegativeBuildings[2])
        }
        if (mentalStats.monthlySuperstitionSpending >= 500) {
            buildings.add(TownRepository.mentalNegativeBuildings[3])
        }
        if (mentalStats.monthlyGameSpending >= 100) {
            buildings.add(TownRepository.mentalNegativeBuildings[4])
        }
        if (mentalStats.historyHours >= 3) {
            buildings.add(TownRepository.mentalNegativeBuildings[5])
        }
        if (mentalStats.classicalChineseHours >= 1 || mentalStats.latinHours >= 1) {
            buildings.add(TownRepository.mentalNegativeBuildings[6])
        }
        if (mentalStats.aestheticValue < 200) {
            buildings.add(TownRepository.mentalNegativeBuildings[8])
        }
        if (housingStats.decorationStyle == "欧式豪华" || 
            housingStats.decorationStyle == "网红ins风") {
            buildings.add(TownRepository.mentalNegativeBuildings[9])
        }
        if (housingStats.decorationStyle == "网红ins风") {
            buildings.add(TownRepository.mentalNegativeBuildings[10])
        }
        if (mentalStats.sunExposureHours > 1.0) {
            buildings.add(TownRepository.mentalNegativeBuildings[11])
        }
        if (mentalStats.solarTermTaboos > 3) {
            buildings.add(TownRepository.mentalNegativeBuildings[12])
        }
        if (mentalStats.astrologyHours > 0.5) {
            buildings.add(TownRepository.mentalNegativeBuildings[13])
        }
        if (mentalStats.pseudoscienceSpending > 500.0) {
            buildings.add(TownRepository.mentalNegativeBuildings[14])
        }

        if (foodStats.steamedFoodCount > 5) {
            buildings.add(TownRepository.foodPositiveBuildings[0])
        }
        if (!foodStats.eatsUntilFull) {
            buildings.add(TownRepository.foodPositiveBuildings[1])
        }

        if (clothingStats.highValueDensityCount > 5) {
            buildings.add(TownRepository.clothingPositiveBuildings[0])
        }
        if (clothingStats.discardedCount > 20) {
            buildings.add(TownRepository.clothingPositiveBuildings[1])
        }

        if (!housingStats.livesInBasement && housingStats.commuteTime <= 1) {
            buildings.add(TownRepository.housingPositiveBuildings[0])
        }
        if (housingStats.usesPublicTransport) {
            buildings.add(TownRepository.housingPositiveBuildings[1])
        }
        if (housingStats.decorationStyle == "日式极简" && housingStats.idleItemsCount <= 10) {
            buildings.add(TownRepository.housingPositiveBuildings[2])
        }

        if (mentalStats.totalAwakeningPoints >= 1000) {
            buildings.add(TownRepository.mentalPositiveBuildings[0])
        }
        if (mentalStats.scienceHours >= 100) {
            buildings.add(TownRepository.mentalPositiveBuildings[1])
        }
        if (mentalStats.englishHours >= 50) {
            buildings.add(TownRepository.mentalPositiveBuildings[2])
        }
        if (mentalStats.programmingHours >= 50) {
            buildings.add(TownRepository.mentalPositiveBuildings[3])
        }
        if (mentalStats.readingHours >= 50) {
            buildings.add(TownRepository.mentalPositiveBuildings[4])
        }
        if (mentalStats.aestheticValue >= 500) {
            buildings.add(TownRepository.mentalPositiveBuildings[5])
        }
        if (housingStats.idleItemsCount <= 10) {
            buildings.add(TownRepository.mentalPositiveBuildings[6])
        }
        if (mentalStats.scienceHours >= 50) {
            buildings.add(TownRepository.mentalPositiveBuildings[7])
        }
        if (mentalStats.scienceHours >= 100) {
            buildings.add(TownRepository.mentalPositiveBuildings[8])
        }
        if (mentalStats.doesExperiments) {
            buildings.add(TownRepository.mentalPositiveBuildings[9])
        }
        if (mentalStats.trustsModernMedicine) {
            buildings.add(TownRepository.mentalPositiveBuildings[10])
        }
        if (mentalStats.detoxCompleted) {
            buildings.add(TownRepository.mentalPositiveBuildings[11])
        }
        val aestheticLevel = AestheticBusiness.calculateAestheticLevel(
            mentalStats.detoxCompleted,
            mentalStats.dailyDeleteCount,
            mentalStats.weeklyReviewCount,
            mentalStats.principlesMastered
        )
        if (aestheticLevel == AestheticBusiness.AestheticLevel.MASTER || 
            aestheticLevel == AestheticBusiness.AestheticLevel.AWAKENED) {
            buildings.add(TownRepository.mentalPositiveBuildings[12])
        }
        if (mentalStats.dailyDeleteCount >= 100) {
            buildings.add(TownRepository.mentalPositiveBuildings[13])
        }

        return buildings
    }

    private fun determineActiveEvents(
        foodStats: FoodStats,
        mentalStats: MentalStats,
        debtLevel: Int,
        awakeningLevel: Int
    ): List<TownEvent> {
        val events = mutableListOf<TownEvent>()

        if (foodStats.overnightFoodCount > 10 || foodStats.spoiledFoodCount > 5) {
            events.add(TownRepository.negativeEvents[0])
        }
        if (debtLevel > 80) {
            events.add(TownRepository.negativeEvents[1])
        }
        if (mentalStats.shortVideoHours >= 4) {
            events.add(TownRepository.negativeEvents[2])
        }
        if (awakeningLevel < 30) {
            events.add(TownRepository.negativeEvents[3])
        }
        if (mentalStats.historyHours >= 5) {
            events.add(TownRepository.negativeEvents[4])
        }

        if (mentalStats.learningHours >= 100) {
            events.add(TownRepository.positiveEvents[1])
        }

        return events
    }

    private fun determineNPCs(awakeningLevel: Int, debtLevel: Int): List<TownNPC> {
        val npcs = mutableListOf<TownNPC>()

        // 根据觉醒值动态调整NPC类型
        // 低觉醒（<40）：更多负面NPC
        if (awakeningLevel < 40) {
            npcs.addAll(TownRepository.negativeNPCs.shuffled().take(3))
        }
        
        // 中觉醒（40-70）：混合展示
        if (awakeningLevel in 40..70) {
            // 选择性展示1-2个负面NPC
            val negativeCount = when {
                awakeningLevel < 50 -> 2
                awakeningLevel < 60 -> 1
                else -> 1
            }
            npcs.addAll(TownRepository.negativeNPCs.shuffled().take(negativeCount))
            
            // 选择性展示1个正面NPC作为对比
            if (awakeningLevel >= 55) {
                npcs.addAll(TownRepository.positiveNPCs.shuffled().take(1))
            }
        }
        
        // 高觉醒（>70）：更多正面NPC
        if (awakeningLevel > 70) {
            npcs.addAll(TownRepository.positiveNPCs.shuffled().take(2))
        }
        
        // 债务危机：额外增加一个财务相关NPC
        if (debtLevel > 70) {
            npcs.add(TownRepository.negativeNPCs.find { it.id.contains("debt") || it.role.contains("债务") } 
                ?: TownRepository.negativeNPCs.last())
        }

        return npcs.distinctBy { it.id }.shuffled().take(5) // 最多显示5个NPC
    }
}
