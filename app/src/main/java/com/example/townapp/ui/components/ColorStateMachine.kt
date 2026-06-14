package com.example.townapp.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.townapp.data.ItemState

/**
 * 颜色状态机
 * 根据物品状态（全新/磨损/老旧）调整颜色饱和度与亮度
 * 使用 HSV 色彩空间进行操作
 */
object ColorStateMachine {

    /**
     * 根据物品状态调整颜色
     * @param baseColor 基础颜色
     * @param state 物品状态
     * @return 调整后的颜色
     */
    fun getColorByState(baseColor: Color, state: ItemState): Color {
        val argb = baseColor.toArgb()
        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(argb, hsv)

        when (state) {
            ItemState.NEW -> {
                // 保持原始饱和度/亮度
            }
            ItemState.SLIGHT_WORN -> {
                hsv[1] = (hsv[1] * 0.8f).coerceIn(0f, 1f)  // 降低饱和度
                hsv[2] = (hsv[2] * 0.9f).coerceIn(0f, 1f)  // 略微降低亮度
            }
            ItemState.OLD -> {
                hsv[1] = (hsv[1] * 0.5f).coerceIn(0f, 1f)  // 大幅降低饱和度
                hsv[2] = (hsv[2] * 0.7f).coerceIn(0f, 1f)  // 降低亮度
            }
        }

        return Color(android.graphics.Color.HSVToColor(hsv))
    }

    /**
     * 根据十六进制色值和状态返回调整后的 Compose Color
     */
    fun getColorFromHex(hex: String, state: ItemState): Color {
        val rawColor = try {
            Color(android.graphics.Color.parseColor(hex))
        } catch (e: Exception) {
            Color.Gray
        }
        return getColorByState(rawColor, state)
    }
}