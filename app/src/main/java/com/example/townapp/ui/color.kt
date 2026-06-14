import androidx.compose.ui.graphics.Color

fun getIQTaxColor(level: Int): Color {
    return when (level) {
        0 -> Color(0xFF4CAF50)   // 绿色 - 无智商税
        1 -> Color(0xFF8BC34A)   // 浅绿 - 极低
        2 -> Color(0xFFFFC107)   // 黄色 - 轻度
        3 -> Color(0xFFFF9800)   // 橙色 - 中度
        4 -> Color(0xFFFF5722)   // 深橙 - 重度
        5 -> Color(0xFFF44336)   // 红色 - 智商税之王
        else -> Color(0xFF9E9E9E) // 灰色 - 未知
    }
}