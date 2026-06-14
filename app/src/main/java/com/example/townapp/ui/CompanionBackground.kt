package com.example.townapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.townapp.R
import com.example.townapp.data.database.entity.CuteCharacterEntity

/**
 * 🎨 角色背景资源辅助类
 * 负责管理和使用各分类的渐变背景资源
 */
object CompanionBackground {
    
    // 资源名称常量
    const val RES_GRADIENT_FOOD = "gradient_food"
    const val RES_GRADIENT_CLOTHES = "gradient_clothes"
    const val RES_GRADIENT_HOME = "gradient_home"
    const val RES_GRADIENT_BACKGROUND = "gradient_background"
    
    // 根据物品分类获取背景资源
    fun getBackgroundResForCategory(category: ItemCategory): Int {
        return when (category) {
            ItemCategory.FOOD -> R.drawable.gradient_food
            ItemCategory.CLOTHING -> R.drawable.gradient_clothes
            ItemCategory.HOME -> R.drawable.gradient_home
            ItemCategory.VEHICLE -> R.drawable.gradient_home
            ItemCategory.ELECTRONICS -> R.drawable.gradient_clothes
            ItemCategory.HOBBY -> R.drawable.gradient_clothes
            ItemCategory.DEFAULT -> R.drawable.gradient_background
        }
    }
    
    // 根据角色获取卡片背景资源
    fun getBackgroundResForCharacter(characterId: Int): Int {
        return when (characterId) {
            CuteCharacterEntity.TAFFY_ID -> R.drawable.gradient_clothes
            CuteCharacterEntity.DORO_ID -> R.drawable.gradient_home
            CuteCharacterEntity.GUGU_ID -> R.drawable.gradient_food
            else -> R.drawable.gradient_background
        }
    }
    
    // 物品分类枚举
    enum class ItemCategory {
        FOOD,           // 食物/饮料
        CLOTHING,       // 服装/日用品
        HOME,           // 家具/家居
        VEHICLE,        // 交通工具
        ELECTRONICS,    // 电子产品
        HOBBY,          // 兴趣爱好
        DEFAULT         // 默认
    }
    
    // 物品分类判断（基于关键词）
    fun categorizeItem(itemName: String): ItemCategory {
        val lowerName = itemName.lowercase()
        return when {
            // 食物分类
            lowerName.contains("奶茶") || lowerName.contains("咖啡") || 
            lowerName.contains("饮料") || lowerName.contains("汉堡") ||
            lowerName.contains("炸鸡") || lowerName.contains("薯条") ||
            lowerName.contains("火锅") || lowerName.contains("米饭") ||
            lowerName.contains("面") || lowerName.contains("包") ||
            lowerName.contains("零食") || lowerName.contains("蛋糕") ||
            lowerName.contains("水果") || lowerName.contains("蔬菜") -> ItemCategory.FOOD
            
            // 服装分类
            lowerName.contains("衣服") || lowerName.contains("裤子") ||
            lowerName.contains("裙子") || lowerName.contains("鞋子") ||
            lowerName.contains("帽子") || lowerName.contains("包") ||
            lowerName.contains("化妆品") || lowerName.contains("护肤品") ||
            lowerName.contains("香水") || lowerName.contains("首饰") -> ItemCategory.CLOTHING
            
            // 家居分类
            lowerName.contains("沙发") || lowerName.contains("床") ||
            lowerName.contains("桌子") || lowerName.contains("椅子") ||
            lowerName.contains("灯") || lowerName.contains("柜子") ||
            lowerName.contains("窗帘") || lowerName.contains("地毯") -> ItemCategory.HOME
            
            // 交通工具
            lowerName.contains("车") || lowerName.contains("自行车") ||
            lowerName.contains("摩托车") || lowerName.contains("公交") ||
            lowerName.contains("地铁") -> ItemCategory.VEHICLE
            
            // 电子产品
            lowerName.contains("手机") || lowerName.contains("电脑") ||
            lowerName.contains("平板") || lowerName.contains("耳机") ||
            lowerName.contains("相机") || lowerName.contains("电视") -> ItemCategory.ELECTRONICS
            
            // 兴趣爱好
            lowerName.contains("书") || lowerName.contains("吉他") ||
            lowerName.contains("画") || lowerName.contains("运动") ||
            lowerName.contains("游戏") || lowerName.contains("玩具") -> ItemCategory.HOBBY
            
            else -> ItemCategory.DEFAULT
        }
    }
}

/**
 * 🌈 角色主题颜色
 */
object CompanionColors {
    // 塔菲喵 - 橙色系
    val TaffyOrange = Color(0xFFFFB347)
    val TaffyOrangeLight = Color(0xFFFFE4B5)
    val TaffyOrangeDark = Color(0xFFFF8C00)
    
    // doro - 紫色系
    val DoroPurple = Color(0xFF9B7ED9)
    val DoroPurpleLight = Color(0xFFE6E0FF)
    val DoroPurpleDark = Color(0xFF7B5DC9)
    
    // 咕咕嘎嘎 - 黄色系
    val GugugagaYellow = Color(0xFFFFD93D)
    val GugugagaYellowLight = Color(0xFFFFF4B8)
    val GugugagaYellowDark = Color(0xFFFFC107)
    
    // 根据角色ID获取主题色
    fun getThemeColor(characterId: Int): Color {
        return when (characterId) {
            CuteCharacterEntity.TAFFY_ID -> TaffyOrange
            CuteCharacterEntity.DORO_ID -> DoroPurple
            CuteCharacterEntity.GUGU_ID -> GugugagaYellow
            else -> TaffyOrange
        }
    }
    
    fun getLightColor(characterId: Int): Color {
        return when (characterId) {
            CuteCharacterEntity.TAFFY_ID -> TaffyOrangeLight
            CuteCharacterEntity.DORO_ID -> DoroPurpleLight
            CuteCharacterEntity.GUGU_ID -> GugugagaYellowLight
            else -> TaffyOrangeLight
        }
    }
    
    fun getDarkColor(characterId: Int): Color {
        return when (characterId) {
            CuteCharacterEntity.TAFFY_ID -> TaffyOrangeDark
            CuteCharacterEntity.DORO_ID -> DoroPurpleDark
            CuteCharacterEntity.GUGU_ID -> GugugagaYellowDark
            else -> TaffyOrangeDark
        }
    }
}
