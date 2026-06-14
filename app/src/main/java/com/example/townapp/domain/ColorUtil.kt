package com.example.townapp.domain

import com.example.townapp.data.ColorStyleTag
import com.example.townapp.data.GlobalColor
import com.example.townapp.data.globalColorList

object ColorUtil {

    /** 根据颜色ID获取十六进制色值 */
    fun getColorHexById(colorId: Int): String {
        return globalColorList.firstOrNull { it.colorId == colorId }?.colorHex ?: "#CCCCCC"
    }

    /** 根据颜色ID获取颜色名称 */
    fun getColorNameById(colorId: Int): String {
        return globalColorList.firstOrNull { it.colorId == colorId }?.colorName ?: "未知"
    }

    /** 根据颜色ID获取风格标签 */
    fun getStyleTagById(colorId: Int): ColorStyleTag {
        return globalColorList.firstOrNull { it.colorId == colorId }?.styleTag ?: ColorStyleTag.CLASSIC
    }

    /** 根据主色风格推荐适配颜色列表 */
    fun getMatchColorList(mainStyle: ColorStyleTag): List<GlobalColor> {
        return globalColorList.filter { it.styleTag == mainStyle }
    }

    /** 根据风格标签推荐穿搭配色文案 */
    fun getStyleDescription(styleTag: ColorStyleTag): String = when (styleTag) {
        ColorStyleTag.CLASSIC -> "经典撞色，永不出错"
        ColorStyleTag.SAME_TONE -> "同色系搭配，柔和舒适"
        ColorStyleTag.LIGHT -> "浅色系清爽，适合日常出行"
        ColorStyleTag.DARK -> "深色系沉稳，适合商务通勤"
        ColorStyleTag.RETRO -> "复古色系，文艺质感"
    }

    /** 从全局库中获取所有颜色 */
    fun getAllColors(): List<GlobalColor> = globalColorList
}