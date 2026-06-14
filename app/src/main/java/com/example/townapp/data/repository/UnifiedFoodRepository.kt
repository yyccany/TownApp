package com.example.townapp.data.repository

import com.example.townapp.data.FoodItem
import com.example.townapp.data.completeFoodItems
import com.example.townapp.feature.food.CuisineFoodRepository

/**
 * 🍽️ 统一食品仓库 - 整合基础食材和特色菜品
 * 
 * 本仓库将现有的基础食材(completeFoodItems)与特色菜品(CuisineFoodRepository)合并
 * 提供统一的访问接口，方便小镇居民喂食系统使用
 * 
 * ✨ 核心特色：每日综合价值计算
 * - 不仅仅是记账工具，而是从「万物平等」视角计算物品的综合价值
 * - 金钱层面：购入价、每日折旧成本
 * - 精神层面：愉悦感、焦虑缓解、情绪价值
 * - 闪光点：陪伴价值、不可替代的意义
 */
object UnifiedFoodRepository {

    /**
     * 获取所有食品（基础食材 + 特色菜品）
     */
    fun getAllFoods(): List<FoodItem> {
        return completeFoodItems + cuisineFoodsAsFoodItems()
    }

    /**
     * 获取基础食材（原有的completeFoodItems）
     */
    fun getBasicFoods(): List<FoodItem> {
        return completeFoodItems
    }

    /**
     * 获取特色菜品（从CuisineFoodRepository转换）
     */
    fun getSpecialtyFoods(): List<FoodItem> {
        return cuisineFoodsAsFoodItems()
    }

    /**
     * 根据ID获取食品
     */
    fun getFoodById(id: Int): FoodItem? {
        // 先查基础食材
        completeFoodItems.find { it.id == id }?.let { return it }
        // 再查特色菜品
        return cuisineFoodsAsFoodItems().find { it.id == id }
    }

    /**
     * 根据品类获取食品
     */
    fun getFoodsByCategory(category: String): List<FoodItem> {
        return getAllFoods().filter { it.category == category }
    }

    /**
     * 获取所有品类
     */
    fun getAllCategories(): List<String> {
        return getAllFoods().map { it.category }.distinct()
    }

    /**
     * 搜索食品（支持名称、品类、描述）
     */
    fun searchFoods(query: String): List<FoodItem> {
        val lowerQuery = query.lowercase()
        return getAllFoods().filter {
            it.name.lowercase().contains(lowerQuery) ||
            it.category.lowercase().contains(lowerQuery) ||
            (it.note?.lowercase()?.contains(lowerQuery) == true)
        }
    }

    /**
     * 获取健康食品（营养评分 >= 70）
     */
    fun getHealthyFoods(): List<FoodItem> {
        return getAllFoods().filter { it.nutritionalScore >= 70.0 }
    }

    /**
     * 获取高热量食品（卡路里 >= 300/100g）
     */
    fun getHighCalorieFoods(): List<FoodItem> {
        return getAllFoods().filter { it.caloriesPer100g >= 300.0 }
    }

    /**
     * 获取高蛋白食品（蛋白质 >= 20g/100g）
     */
    fun getHighProteinFoods(): List<FoodItem> {
        return getAllFoods().filter { it.proteinPer100g >= 20.0 }
    }

    /**
     * 获取低脂肪食品（脂肪 <= 5g/100g）
     */
    fun getLowFatFoods(): List<FoodItem> {
        return getAllFoods().filter { it.fatPer100g <= 5.0 }
    }

    /**
     * 根据价格范围筛选
     */
    fun getFoodsByPriceRange(minPrice: Double, maxPrice: Double): List<FoodItem> {
        return getAllFoods().filter { it.pricePer100g in minPrice..maxPrice }
    }

    /**
     * 获取食品总数
     */
    fun getTotalCount(): Int {
        return getAllFoods().size
    }

    /**
     * ✨ 获取食品的每日综合价值
     * 
     * 这是小镇的核心功能之一，从「万物平等」视角计算物品的综合价值：
     * - 💰 每日成本：购入价 ÷ 预计使用天数
     * - 💖 精神增益：愉悦感、焦虑缓解、饱腹感
     * - ✨ 闪光点：陪伴价值、不可替代的意义
     * 
     * 示例输出：
     * > 📅 每日成本：0.6元
     * > 💖 每日精神增益：愉悦感 +2、焦虑感 -1.5
     * > ✨ 它的闪光点：营养满分，高蛋白，在每一餐中守护你的健康
     */
    fun getDailyValue(foodId: Int): ItemValueCalculator.DailyValueResult {
        val food = getFoodById(foodId)
        return if (food != null) {
            ItemValueCalculator.calculateDailyValue(food)
        } else {
            ItemValueCalculator.DailyValueResult()
        }
    }

    /**
     * ✨ 获取所有食品的每日综合价值列表
     */
    fun getAllDailyValues(): List<Pair<FoodItem, ItemValueCalculator.DailyValueResult>> {
        return getAllFoods().map { food ->
            food to ItemValueCalculator.calculateDailyValue(food)
        }
    }

    /**
     * ✨ 获取高价值食品（综合评分 >= 80）
     */
    fun getHighValueFoods(): List<FoodItem> {
        return getAllDailyValues()
            .filter { it.second.overallScore >= 80.0 }
            .map { it.first }
    }

    /**
     * 将CuisineFood转换为FoodItem格式
     * 特色菜品使用ID 2000+，避免与基础食材冲突
     */
    private fun cuisineFoodsAsFoodItems(): List<FoodItem> {
        val cuisineFoods = CuisineFoodRepository.getAllFoods()
        return cuisineFoods.map { cuisineFood ->
            // 转换品类名称
            val category = mapCategory(cuisineFood.category)
            
            // 计算每100g营养值
            val scale = if (cuisineFood.calories > 0) 100.0 / cuisineFood.calories else 1.0
            
            FoodItem(
                id = cuisineFood.id + 2000,  // 使用2000+ ID区间
                name = cuisineFood.name,
                category = category,
                pricePer100g = cuisineFood.price,
                proteinPer100g = cuisineFood.protein * scale,
                fatPer100g = cuisineFood.oil * scale,
                carbohydratePer100g = cuisineFood.sugar * scale * 4,
                fiberPer100g = 0.0,
                caloriesPer100g = cuisineFood.calories,
                typicalServing = 100,
                shelfLifeDays = 30.0,
                mealsPerWeek = when (cuisineFood.healthRisk) {
                    "LOW" -> 5
                    "MEDIUM" -> 3
                    "HIGH" -> 1
                    "EXTREME" -> 0
                    else -> 3
                },
                qualityScore = cuisineFood.nutritionScore / 50.0,
                breedingDegradation = 0.1,
                processingLoss = 0.2,
                nutritionalScore = cuisineFood.nutritionScore * 2,
                glycemicIndex = 0.0,
                satietyIndex = 50.0,
                note = cuisineFood.description,
                isIQTax = cuisineFood.healthRisk == "EXTREME",
                iqTaxLevel = when (cuisineFood.healthRisk) {
                    "EXTREME" -> 2
                    "HIGH" -> 1
                    else -> 0
                }
            )
        }
    }

    /**
     * 将特色菜品品类映射到基础食材品类
     */
    private fun mapCategory(category: String): String {
        return when (category) {
            "中餐菜", "西餐", "日料" -> "肉禽水产"
            "肉类", "蛋类" -> "肉禽水产"
            "鱼类", "海鲜" -> "肉禽水产"
            "蔬菜" -> "蔬菜水果"
            "水果" -> "蔬菜水果"
            "主食", "面食" -> "谷类主食"
            "豆制品", "乳制品" -> "蛋奶豆"
            "零食", "甜品", "速食", "快餐" -> "饮料零食"
            "调味品" -> "调料油脂"
            "饮品" -> "饮料零食"
            "轻食", "沙拉" -> "蔬菜水果"
            "早餐" -> "谷类主食"
            else -> "其他"
        }
    }
}
