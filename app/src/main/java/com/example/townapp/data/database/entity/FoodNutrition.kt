package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 食品营养指标模板表（tb_food_nutrition）
 * 系统只读热表，存储标准化营养数据。
 * 每100g基准值，含完整量化指标。
 */
@Entity(
    tableName = "tb_food_nutrition",
    indices = [
        Index(value = ["foodName"]),
        Index(value = ["category"]),
        Index(value = ["isIQTax"])
    ]
)
data class FoodNutritionEntity(
    @PrimaryKey val foodId: Int,               // 全局ID（食用类分段）
    val foodName: String,                      // 食材名称
    val category: String,                      // 品类：肉禽水产/蔬菜/水果/主食/蛋奶豆/零食/调味/饮品
    val proteinPer100g: Double = 0.0,          // 蛋白质 g
    val fatPer100g: Double = 0.0,              // 脂肪 g
    val carbohydratePer100g: Double = 0.0,     // 碳水 g
    val fiberPer100g: Double = 0.0,            // 膳食纤维 g
    val caloriesPer100g: Double = 0.0,         // 热量 kcal
    val sugarPer100g: Double = 0.0,            // 含糖量 g
    val saltPer100g: Double = 0.0,             // 盐分 g
    val cholesterolMg: Double = 0.0,           // 胆固醇 mg
    val saturatedFatPer100g: Double = 0.0,     // 饱和脂肪 g
    val transFatPer100g: Double = 0.0,         // 反式脂肪 g
    val calciumMg: Double = 0.0,               // 钙 mg
    val ironMg: Double = 0.0,                  // 铁 mg
    val vitaminCMg: Double = 0.0,              // 维生素C mg
    val nutritionScore: Int = 0,               // 综合营养评分 0-100
    val typicalServingG: Int = 100,            // 典型食用量 g
    val isIQTax: Boolean = false,              // 是否为智商税商品
    val iqTaxLevel: Int = 0,                   // 智商税等级 0-3
    val iqTaxReason: String = "",              // 智商税原因
    val note: String = ""                       // 营养师点评
)