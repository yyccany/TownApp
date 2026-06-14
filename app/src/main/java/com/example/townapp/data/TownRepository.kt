package com.example.townapp.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// ============================================
// 🏪 数据仓库
// 说明：封装数据存取逻辑，未来可无缝切换到Room数据库或网络API
//      仅负责数据CRUD，不包含业务计算逻辑
// ============================================

class TownRepository {
    // 当前城市设置
    private val _settings = MutableStateFlow(
        GlobalSettings(
            city = "大城市",
            currency = "¥",
            awakeningMode = false
        )
    )
    val settings: StateFlow<GlobalSettings> = _settings

    // 衣物列表
    private val _clothingItems = MutableStateFlow(
        listOf(
            ClothingItem(
                id = 1,
                name = "白色纯棉T恤",
                category = "常规上衣",
                fabrics = listOf("纯棉"),
                fabricRatios = listOf(1.0),
                price = 79.0,
                expectedLifespanMonths = 24.0,
                maxWearsPerYear = 120,
                versatilityScore = 1.0,
                comfortScore = 0.8,
                durabilityScore = 0.7,
                qualityScore = 0.8,
                isIQTax = false,
                iqTaxLevel = 0,
                iqTaxReason = "",
                decisionDifficulty = 0.2,
                matchingDifficulty = 0.3,
                plannedObsolescence = 0.1,
                materialDegradation = 0.1
            ),
            ClothingItem(
                id = 2,
                name = "蓝色牛仔裤",
                category = "裤装",
                fabrics = listOf("纯棉"),
                fabricRatios = listOf(1.0),
                price = 299.0,
                expectedLifespanMonths = 60.0,
                maxWearsPerYear = 100,
                versatilityScore = 1.2,
                comfortScore = 0.9,
                durabilityScore = 0.9,
                qualityScore = 0.85,
                isIQTax = false,
                iqTaxLevel = 0,
                iqTaxReason = "",
                decisionDifficulty = 0.4,
                matchingDifficulty = 0.5,
                plannedObsolescence = 0.15,
                materialDegradation = 0.1
            ),
            ClothingItem(
                id = 3,
                name = "羊毛混纺大衣",
                category = "外搭外套",
                fabrics = listOf("羊毛", "聚酯纤维"),
                fabricRatios = listOf(0.7, 0.3),
                price = 1299.0,
                expectedLifespanMonths = 120.0,
                maxWearsPerYear = 50,
                versatilityScore = 0.8,
                comfortScore = 0.9,
                durabilityScore = 0.95,
                qualityScore = 0.9,
                isIQTax = true,
                iqTaxLevel = 2,
                iqTaxReason = "品牌溢价较高",
                decisionDifficulty = 0.8,
                matchingDifficulty = 0.8,
                plannedObsolescence = 0.2,
                materialDegradation = 0.1
            )
        )
    )
    val clothingItems: StateFlow<List<ClothingItem>> = _clothingItems

    // 食物列表
    private val _foodItems = MutableStateFlow(
        listOf(
            FoodItem(
                id = 1,
                name = "土鸡蛋",
                category = "蛋奶豆",
                pricePer100g = 1.0,
                proteinPer100g = 13.5,
                fatPer100g = 9.0,
                carbohydratePer100g = 2.8,
                fiberPer100g = 0.0,
                caloriesPer100g = 144.0,
                typicalServing = 50,
                shelfLifeDays = 30.0,
                mealsPerWeek = 7,
                qualityScore = 0.78,
                breedingDegradation = 0.03,
                processingLoss = 0.02
            ),
            FoodItem(
                id = 2,
                name = "鸡胸肉",
                category = "肉禽水产",
                pricePer100g = 12.0,
                proteinPer100g = 31.0,
                fatPer100g = 3.6,
                carbohydratePer100g = 0.0,
                fiberPer100g = 0.0,
                caloriesPer100g = 165.0,
                typicalServing = 100,
                shelfLifeDays = 3.0,
                mealsPerWeek = 4,
                qualityScore = 0.7,
                breedingDegradation = 0.1,
                processingLoss = 0.05
            ),
            FoodItem(
                id = 3,
                name = "牛腩肉",
                category = "肉禽水产",
                pricePer100g = 58.0,
                proteinPer100g = 26.0,
                fatPer100g = 20.0,
                carbohydratePer100g = 0.0,
                fiberPer100g = 0.0,
                caloriesPer100g = 250.0,
                typicalServing = 100,
                shelfLifeDays = 7.0,
                mealsPerWeek = 2,
                qualityScore = 0.8,
                breedingDegradation = 0.15,
                processingLoss = 0.02
            ),
            FoodItem(
                id = 4,
                name = "可乐",
                category = "饮料零食",
                pricePer100g = 3.5,
                proteinPer100g = 0.0,
                fatPer100g = 0.0,
                carbohydratePer100g = 10.6,
                fiberPer100g = 0.0,
                caloriesPer100g = 43.0,
                typicalServing = 330,
                shelfLifeDays = 180.0,
                mealsPerWeek = 3,
                qualityScore = 0.15,
                breedingDegradation = 0.0,
                processingLoss = 0.0
            ),
            FoodItem(
                id = 5,
                name = "纯牛奶",
                category = "蛋奶豆",
                pricePer100g = 1.5,
                proteinPer100g = 3.2,
                fatPer100g = 3.2,
                carbohydratePer100g = 5.0,
                fiberPer100g = 0.0,
                caloriesPer100g = 65.0,
                typicalServing = 250,
                shelfLifeDays = 7.0,
                mealsPerWeek = 7,
                qualityScore = 0.6,
                breedingDegradation = 0.05,
                processingLoss = 0.02
            ),
            FoodItem(
                id = 6,
                name = "原味酸奶",
                category = "蛋奶豆",
                pricePer100g = 3.0,
                proteinPer100g = 2.5,
                fatPer100g = 2.7,
                carbohydratePer100g = 9.3,
                fiberPer100g = 0.0,
                caloriesPer100g = 72.0,
                typicalServing = 200,
                shelfLifeDays = 14.0,
                mealsPerWeek = 5,
                qualityScore = 0.55,
                breedingDegradation = 0.1,
                processingLoss = 0.03
            ),
            FoodItem(
                id = 7,
                name = "老豆腐",
                category = "蛋奶豆",
                pricePer100g = 2.5,
                proteinPer100g = 8.1,
                fatPer100g = 3.7,
                carbohydratePer100g = 4.2,
                fiberPer100g = 0.4,
                caloriesPer100g = 81.0,
                typicalServing = 150,
                shelfLifeDays = 3.0,
                mealsPerWeek = 3,
                qualityScore = 0.65,
                breedingDegradation = 0.05,
                processingLoss = 0.0
            ),
            FoodItem(
                id = 8,
                name = "猪五花肉",
                category = "肉禽水产",
                pricePer100g = 25.0,
                proteinPer100g = 13.6,
                fatPer100g = 37.0,
                carbohydratePer100g = 2.4,
                fiberPer100g = 0.0,
                caloriesPer100g = 395.0,
                typicalServing = 100,
                shelfLifeDays = 3.0,
                mealsPerWeek = 2,
                qualityScore = 0.85,
                breedingDegradation = 0.2,
                processingLoss = 0.08
            ),
            FoodItem(
                id = 9,
                name = "三文鱼",
                category = "肉禽水产",
                pricePer100g = 60.0,
                proteinPer100g = 22.0,
                fatPer100g = 15.0,
                carbohydratePer100g = 0.0,
                fiberPer100g = 0.0,
                caloriesPer100g = 231.0,
                typicalServing = 100,
                shelfLifeDays = 2.0,
                mealsPerWeek = 1,
                qualityScore = 0.75,
                breedingDegradation = 0.1,
                processingLoss = 0.0
            ),
            FoodItem(
                id = 10,
                name = "薯片",
                category = "饮料零食",
                pricePer100g = 8.0,
                proteinPer100g = 7.5,
                fatPer100g = 35.0,
                carbohydratePer100g = 53.0,
                fiberPer100g = 4.5,
                caloriesPer100g = 540.0,
                typicalServing = 100,
                shelfLifeDays = 180.0,
                mealsPerWeek = 2,
                qualityScore = 0.1,
                breedingDegradation = 0.0,
                processingLoss = 0.9
            )
        )
    )
    val foodItems: StateFlow<List<FoodItem>> = _foodItems

    // 住房列表
    private val _housingItems = MutableStateFlow(
        listOf(
            HousingItem(
                id = 1,
                name = "温馨一居室",
                category = "整租",
                houseSize = 40.0,
                monthlyRent = 3500.0,
                propertyFee = 200.0,
                utilityFee = 150.0,
                residents = 1,
                items = listOf(
                    HouseholdItem(
                        id = 1,
                        name = "沙发",
                        category = "家具",
                        price = 1500.0,
                        lifespanYears = 5.0
                    ),
                    HouseholdItem(
                        id = 2,
                        name = "书桌",
                        category = "家具",
                        price = 800.0,
                        lifespanYears = 8.0
                    ),
                    HouseholdItem(
                        id = 3,
                        name = "空气炸锅",
                        category = "电器",
                        price = 399.0,
                        lifespanYears = 3.0
                    ),
                    HouseholdItem(
                        id = 4,
                        name = "电饭煲",
                        category = "电器",
                        price = 299.0,
                        lifespanYears = 5.0,
                        brand = "美的",
                        note = "日常煮饭必备"
                    ),
                    HouseholdItem(
                        id = 5,
                        name = "保温杯",
                        category = "日用品",
                        price = 89.0,
                        lifespanYears = 3.0,
                        brand = "膳魔师",
                        note = "四季通用，保温效果好"
                    ),
                    HouseholdItem(
                        id = 6,
                        name = "牙刷",
                        category = "日用品",
                        price = 15.0,
                        lifespanYears = 0.25,
                        brand = "高露洁",
                        note = "三个月更换一次"
                    ),
                    HouseholdItem(
                        id = 7,
                        name = "毛巾",
                        category = "日用品",
                        price = 25.0,
                        lifespanYears = 1.0,
                        brand = "洁丽雅",
                        note = "柔软吸水，定期更换"
                    ),
                    HouseholdItem(
                        id = 8,
                        name = "砧板",
                        category = "厨房",
                        price = 59.0,
                        lifespanYears = 2.0,
                        brand = "双枪",
                        note = "实木材质，防霉抗菌"
                    ),
                    HouseholdItem(
                        id = 9,
                        name = "炒锅",
                        category = "厨房",
                        price = 199.0,
                        lifespanYears = 5.0,
                        brand = "苏泊尔",
                        note = "不粘涂层，轻便易用"
                    ),
                    HouseholdItem(
                        id = 10,
                        name = "台灯",
                        category = "电器",
                        price = 129.0,
                        lifespanYears = 5.0,
                        brand = "小米",
                        note = "LED护眼，三档调光"
                    ),
                    HouseholdItem(
                        id = 11,
                        name = "垃圾桶",
                        category = "日用品",
                        price = 29.0,
                        lifespanYears = 5.0,
                        brand = "佳帮手",
                        note = "脚踏式，密封防臭"
                    ),
                    HouseholdItem(
                        id = 12,
                        name = "衣架",
                        category = "日用品",
                        price = 19.9,
                        lifespanYears = 3.0,
                        brand = "好太太",
                        note = "防滑设计，一包10个"
                    ),
                    HouseholdItem(
                        id = 13,
                        name = "收纳箱",
                        category = "日用品",
                        price = 49.0,
                        lifespanYears = 5.0,
                        brand = "禧天龙",
                        note = "透明可视，叠放稳固"
                    )
                )
            )
        )
    )
    val housingItems: StateFlow<List<HousingItem>> = _housingItems

    // ============= 衣物操作 =============

    fun addClothingItem(item: ClothingItem) {
        _clothingItems.value = _clothingItems.value + item
    }

    fun removeClothingItem(itemId: Int) {
        _clothingItems.value = _clothingItems.value.filter { it.id != itemId }
    }

    fun updateClothingItem(updatedItem: ClothingItem) {
        _clothingItems.value = _clothingItems.value.map {
            if (it.id == updatedItem.id) updatedItem else it
        }
    }

    // ============= 食物操作 =============

    fun addFoodItem(item: FoodItem) {
        _foodItems.value = _foodItems.value + item
    }

    fun removeFoodItem(itemId: Int) {
        _foodItems.value = _foodItems.value.filter { it.id != itemId }
    }

    fun updateFoodItem(updatedItem: FoodItem) {
        _foodItems.value = _foodItems.value.map {
            if (it.id == updatedItem.id) updatedItem else it
        }
    }

    // ============= 住房操作 =============

    fun addHousingItem(item: HousingItem) {
        _housingItems.value = _housingItems.value + item
    }

    fun removeHousingItem(itemId: Int) {
        _housingItems.value = _housingItems.value.filter { it.id != itemId }
    }

    fun updateHousingItem(updatedItem: HousingItem) {
        _housingItems.value = _housingItems.value.map {
            if (it.id == updatedItem.id) updatedItem else it
        }
    }

    // ============= 设置操作 =============

    fun updateCity(city: String) {
        _settings.value = _settings.value.copy(city = city)
    }

    fun toggleAwakeningMode() {
        _settings.value = _settings.value.copy(
            awakeningMode = !_settings.value.awakeningMode
        )
    }

    // ============= 数据查询（纯数据读取，无业务计算） =============

    fun getClothingItemById(id: Int): ClothingItem? {
        return _clothingItems.value.find { it.id == id }
    }

    fun getFoodItemById(id: Int): FoodItem? {
        return _foodItems.value.find { it.id == id }
    }

    fun getHousingItemById(id: Int): HousingItem? {
        return _housingItems.value.find { it.id == id }
    }

    fun getClothingByCategory(category: String): List<ClothingItem> {
        return _clothingItems.value.filter { it.category == category }
    }

    fun getFoodByCategory(category: String): List<FoodItem> {
        return _foodItems.value.filter { it.category == category }
    }

    // ============= 常量数据访问 =============

    fun getCities(): List<CityPreset> = cityPresets

    fun getFabricLibrary(): Map<String, FabricInfo> = fabricLibrary

    fun getFoodMaterialLibrary(): Map<String, FoodMaterialInfo> = foodMaterialLibrary

    fun getClothingTemplates(): List<ItemTemplate> = clothingTemplates

    fun getCognitiveBiases(): List<CognitiveBias> = cognitiveBiases

    fun getIdiomItems(): List<IdiomItem> = idiomItems

    fun getTips(category: String): List<String> {
        return when (category) {
            "food" -> foodTips
            "clothing" -> clothingTips
            "awakening" -> awakeningTips
            else -> emptyList()
        }
    }

    fun getRandomTip(category: String): String {
        val tips = getTips(category)
        return tips.randomOrNull() ?: ""
    }
}
