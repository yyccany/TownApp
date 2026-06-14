package com.example.townapp.ui.theme

import androidx.compose.ui.graphics.Color

// ============================================
// 🎨 评级颜色映射
// 说明：将领域层返回的评级文本/分数映射为UI颜色
// ============================================

/**
 * 根据衣物评级获取颜色
 */
fun getClothingRatingColor(rating: String): Color {
    return when (rating) {
        "SSR" -> Color(0xFF9C27B0)
        "SR" -> Color(0xFF3F51B5)
        "A级" -> Color(0xFF4CAF50)
        "B级" -> Color(0xFFFFC107)
        "C级" -> Color(0xFFFF9800)
        "D级" -> Color(0xFFF44336)
        else -> Color(0xFF757575)
    }
}

/**
 * 根据衣物品质等级获取颜色
 */
fun getQualityGradeColor(grade: String): Color {
    return when (grade) {
        "超值" -> Color(0xFF4CAF50)
        "合理" -> Color(0xFF8BC34A)
        "一般" -> Color(0xFFFFC107)
        "溢价" -> Color(0xFFFF9800)
        "智商税" -> Color(0xFFF44336)
        else -> Color(0xFF757575)
    }
}

/**
 * 根据衣物错配等级获取颜色
 */
fun getMismatchColor(level: String): Color {
    return when (level) {
        "高度实用" -> Color(0xFF4CAF50)
        "比较实用" -> Color(0xFF8BC34A)
        "中等" -> Color(0xFFFFC107)
        "偏装饰性" -> Color(0xFFFF9800)
        "纯装饰" -> Color(0xFFF44336)
        else -> Color(0xFF757575)
    }
}

/**
 * 根据食物营养等级获取颜色
 */
fun getNutritionGradeColor(grade: String): Color {
    return when (grade) {
        "营养王者" -> Color(0xFF1B5E20)
        "营养丰富" -> Color(0xFF388E3C)
        "营养一般" -> Color(0xFFFF8F00)
        "营养较低" -> Color(0xFFFF6F00)
        "空热量" -> Color(0xFFD32F2F)
        else -> Color(0xFF757575)
    }
}

/**
 * 根据食物性价比等级获取颜色
 */
fun getCostEfficiencyColor(grade: String): Color {
    return when (grade) {
        "蛋白质神车" -> Color(0xFF1B5E20)
        "性价比高" -> Color(0xFF388E3C)
        "性价比一般" -> Color(0xFFFF8F00)
        "有点小贵" -> Color(0xFFFF6F00)
        "蛋白质刺客" -> Color(0xFFD32F2F)
        else -> Color(0xFF757575)
    }
}

/**
 * 根据食物健康风险等级获取颜色
 */
fun getHealthRiskColor(level: String): Color {
    return when (level) {
        "健康友好" -> Color(0xFF4CAF50)
        "适量食用" -> Color(0xFF8BC34A)
        "偶尔吃吃" -> Color(0xFFFFC107)
        "尽量少吃" -> Color(0xFFFF9800)
        "健康杀手" -> Color(0xFFF44336)
        else -> Color(0xFF757575)
    }
}

/**
 * 根据食物能量等级获取颜色
 */
fun getFoodPowerColor(level: String): Color {
    return when (level) {
        "超级食物" -> Color(0xFF9C27B0)
        "优秀" -> Color(0xFF3F51B5)
        "良好" -> Color(0xFF4CAF50)
        "一般" -> Color(0xFFFFC107)
        "垃圾食品" -> Color(0xFFF44336)
        else -> Color(0xFF757575)
    }
}

/**
 * 根据住房成本评级获取颜色
 */
fun getHousingCostColor(rating: String): Color {
    return when (rating) {
        "非常便宜" -> Color(0xFF4CAF50)
        "比较便宜" -> Color(0xFF8BC34A)
        "中等水平" -> Color(0xFFFFC107)
        "有点小贵" -> Color(0xFFFF9800)
        "比较昂贵" -> Color(0xFFF57C00)
        "非常昂贵" -> Color(0xFFF44336)
        else -> Color(0xFF757575)
    }
}

/**
 * 根据住房空间评级获取颜色
 */
fun getHousingSpaceColor(rating: String): Color {
    return when (rating) {
        "宽敞舒适" -> Color(0xFF4CAF50)
        "比较宽敞" -> Color(0xFF8BC34A)
        "中等水平" -> Color(0xFFFFC107)
        "有点拥挤" -> Color(0xFFFF9800)
        "非常拥挤" -> Color(0xFFF44336)
        else -> Color(0xFF757575)
    }
}

/**
 * 根据智商税等级获取颜色
 */
fun getScamColor(level: String): Color {
    return when (level) {
        "实在好物" -> Color(0xFF4CAF50)
        "有点溢价" -> Color(0xFF8BC34A)
        "品牌税" -> Color(0xFFFFC107)
        "智商税" -> Color(0xFFFF9800)
        "重灾区" -> Color(0xFFF44336)
        else -> Color(0xFF757575)
    }
}

/**
 * 根据分数 0-1 获取渐变色（绿→黄→红）
 */
fun getScoreColor(score: Double): Color {
    return when {
        score >= 0.8 -> Color(0xFF1B5E20)
        score >= 0.6 -> Color(0xFF4CAF50)
        score >= 0.4 -> Color(0xFFFFC107)
        score >= 0.2 -> Color(0xFFFF9800)
        else -> Color(0xFFF44336)
    }
}
