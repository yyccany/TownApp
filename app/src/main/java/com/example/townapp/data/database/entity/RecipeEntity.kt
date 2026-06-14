package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 🥘 食谱表
 * 存储完整的食谱信息，支持国际化和多维度分类
 */
@Entity(
    tableName = "recipe",
    indices = [
        Index(value = ["name"]),
        Index(value = ["region"]),
        Index(value = ["category"]),
        Index(value = ["cookingMethod"]),
        Index(value = ["ingredientType"]),
        Index(value = ["flavorProfile"]),
        Index(value = ["isDomestic"]),
        Index(value = ["difficulty"])
    ]
)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,              // 食谱名称
    val nameEn: String = "",       // 英文名称（国际化）
    val region: String,            // 地理区域
    val country: String = "",      // 国家
    val isDomestic: Boolean,       // 是否国内食谱
    val category: String,          // 菜系
    val cookingMethod: String = "",     // 烹饪方式：炒、煮、蒸、烤、炸、炖、焖、烩、煎、凉拌等
    val ingredientType: String = "",    // 食材类型：肉类、海鲜、蔬菜、素食、主食等
    val flavorProfile: String = "",     // 口味特点：麻辣、酸甜、咸鲜、清淡、香辣、醇厚等
    val difficulty: Int,           // 难度等级 1-5
    val cookTimeMinutes: Int,      // 烹饪时间（分钟）
    val servings: Int,             // 份量
    val description: String = "",  // 食谱描述
    val descriptionEn: String = "",// 英文描述
    val ingredients: String,       // 食材列表（JSON格式，包含详细用量）
    val ingredientsEn: String = "",// 英文食材列表
    val steps: String,             // 详细步骤描述（JSON格式）
    val stepsEn: String = "",      // 英文步骤描述
    val preparation: String = "",  // 食材准备步骤
    val cookingProcess: String = "",// 烹饪流程
    val heatControl: String = "",  // 火候控制要点
    val seasoningTips: String = "",// 调味技巧
    val tips: String = "",         // 烹饪小贴士
    val imageUrl: String = "",     // 图片URL
    val calories: Double = 0.0,    // 总热量
    val protein: Double = 0.0,     // 总蛋白质
    val fat: Double = 0.0,         // 总脂肪
    val carbs: Double = 0.0,       // 总碳水
    val sodium: Double = 0.0,      // 钠含量
    val fiber: Double = 0.0,       // 膳食纤维
    val rating: Double = 0.0,      // 评分
    val ratingCount: Int = 0,      // 评分人数
    val tags: String = "",         // 标签（逗号分隔）
    val originUrl: String = "",    // 来源链接（确保准确性）
    val isVegetarian: Boolean = false,  // 是否素食
    val isVegan: Boolean = false,      // 是否纯素
    val isGlutenFree: Boolean = false, // 是否无麸质
    val isFamilyFriendly: Boolean = true, // 是否适合家庭烹饪
    val createTime: Long = System.currentTimeMillis()
) {
    companion object {
        // 地理区域常量
        const val REGION_EAST_ASIA = "EAST_ASIA"
        const val REGION_SOUTHEAST_ASIA = "SOUTHEAST_ASIA"
        const val REGION_SOUTH_ASIA = "SOUTH_ASIA"
        const val REGION_MIDDLE_EAST = "MIDDLE_EAST"
        const val REGION_EUROPE = "EUROPE"
        const val REGION_NORTH_AMERICA = "NORTH_AMERICA"
        const val REGION_SOUTH_AMERICA = "SOUTH_AMERICA"
        const val REGION_AFRICA = "AFRICA"
        const val REGION_OCEANIA = "OCEANIA"
        
        // 烹饪方式常量
        const val METHOD_STIR_FRY = "炒"
        const val METHOD_BOIL = "煮"
        const val METHOD_STEAM = "蒸"
        const val METHOD_BAKE = "烤"
        const val METHOD_FRY = "炸"
        const val METHOD_STEW = "炖"
        const val METHOD_SIMMER = "焖"
        const val METHOD_BRAISE = "烩"
        const val METHOD_PAN_FRRY = "煎"
        const val METHOD_COLD = "凉拌"
        const val METHOD_GRILL = "烧烤"
        const val METHOD_ROAST = "烘培"
        const val METHOD_SOUP = "汤"
        const val METHOD_SALAD = "沙拉"
        const val METHOD_DESSERT = "甜品"
        
        // 食材类型常量
        const val INGREDIENT_MEAT = "肉类"
        const val INGREDIENT_SEAFOOD = "海鲜"
        const val INGREDIENT_VEGETABLE = "蔬菜"
        const val INGREDIENT_VEGETARIAN = "素食"
        const val INGREDIENT_STAPLE = "主食"
        const val INGREDIENT_DESSERT = "甜点"
        const val INGREDIENT_SOUP = "汤品"
        const val INGREDIENT_SALAD = "沙拉"
        const val INGREDIENT_PASTA = "面食"
        const val INGREDIENT_RICE = "米饭"
        
        // 口味特点常量
        const val FLAVOR_SPICY = "麻辣"
        const val FLAVOR_SWEET_SOUR = "酸甜"
        const val FLAVOR_SAVORY = "咸鲜"
        const val FLAVOR_LIGHT = "清淡"
        const val FLAVOR_SPICY_SWEET = "香辣"
        const val FLAVOR_RICH = "醇厚"
        const val FLAVOR_GARLIC = "蒜香"
        const val FLAVOR_CITRUS = "清新"
        const val FLAVOR_SMOKY = "烟熏"
        const val FLAVOR_SWEET = "香甜"
        const val FLAVOR_BUTTERY = "奶香"
        const val FLAVOR_HERBAL = "香草"
        
        // 菜系常量
        const val CATEGORY_CHINESE = "中式"
        const val CATEGORY_JAPANESE = "日式"
        const val CATEGORY_KOREAN = "韩式"
        const val CATEGORY_THAI = "泰式"
        const val CATEGORY_INDIAN = "印度"
        const val CATEGORY_ITALIAN = "意式"
        const val CATEGORY_FRENCH = "法式"
        const val CATEGORY_AMERICAN = "美式"
        const val CATEGORY_MEXICAN = "墨西哥"
        const val CATEGORY_GREEK = "希腊"
        const val CATEGORY_BRAZILIAN = "巴西"
        const val CATEGORY_AUSTRALIAN = "澳洲"
        const val CATEGORY_NORTH_AFRICAN = "北非"
    }
}