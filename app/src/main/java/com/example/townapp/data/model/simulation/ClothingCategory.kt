package com.example.townapp.data

data class Category(
    val name: String,
    val season: String,
    val style: String
)

enum class ClothingCategory(
    val displayName: String,
    val maxThreshold: Int,
    val defaultPrice: Double,
    val defaultLifespanMonths: Double,
    val defaultMaxWearsPerYear: Int
) {
    TOP("上衣", 12, 150.0, 18.0, 60),
    PANTS("裤子", 8, 200.0, 24.0, 50),
    OUTERWEAR("外套", 6, 500.0, 36.0, 30),
    SHOES("鞋履", 8, 300.0, 18.0, 80),
    SKINCARE("护肤品", 10, 200.0, 12.0, 365),
    UNDERWEAR("内衣", 10, 80.0, 6.0, 90),
    ACCESSORIES("配饰", 15, 100.0, 24.0, 40),
    DRESSES("连衣裙", 5, 300.0, 18.0, 40),
    SPORTS("运动装", 5, 250.0, 12.0, 40),
    FORMAL("正装", 3, 800.0, 36.0, 20);

    fun isOverThreshold(count: Int): Boolean {
        return count >= maxThreshold
    }

    fun isSignificantlyOverThreshold(count: Int): Boolean {
        return count >= maxThreshold * 1.5
    }
}

fun getCategoryByName(name: String): ClothingCategory {
    return ClothingCategory.values().find { it.displayName == name } ?: ClothingCategory.TOP
}

fun getCategoryByCategory(category: String): ClothingCategory {
    return ClothingCategory.values().find { it.name.equals(category, ignoreCase = true) } ?: ClothingCategory.TOP
}

fun getAllCategories(): List<Category> {
    return listOf(
        Category("上衣", "四季", "休闲/正式"),
        Category("裤子", "四季", "休闲/正式"),
        Category("外套", "秋冬", "保暖/时尚"),
        Category("鞋履", "四季", "休闲/运动"),
        Category("护肤品", "四季", "保养"),
        Category("内衣", "四季", "舒适"),
        Category("配饰", "四季", "装饰"),
        Category("连衣裙", "春夏", "优雅"),
        Category("运动装", "四季", "运动"),
        Category("正装", "四季", "正式")
    )
}
