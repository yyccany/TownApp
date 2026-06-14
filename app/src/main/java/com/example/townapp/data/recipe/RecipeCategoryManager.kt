package com.example.townapp.data.recipe

import com.example.townapp.data.database.entity.RecipeEntity

object RecipeCategoryManager {

    data class CategoryInfo(
        val code: String,
        val name: String,
        val nameEn: String,
        val icon: String
    )

    val regions = listOf(
        CategoryInfo("EAST_ASIA", "东亚", "East Asia", "🌏"),
        CategoryInfo("SOUTHEAST_ASIA", "东南亚", "Southeast Asia", "🌴"),
        CategoryInfo("SOUTH_ASIA", "南亚", "South Asia", "🏔️"),
        CategoryInfo("MIDDLE_EAST", "中东", "Middle East", "🕌"),
        CategoryInfo("EUROPE", "欧洲", "Europe", "🏰"),
        CategoryInfo("NORTH_AMERICA", "北美", "North America", "🗽"),
        CategoryInfo("SOUTH_AMERICA", "南美", "South America", "🌎"),
        CategoryInfo("AFRICA", "非洲", "Africa", "🐘"),
        CategoryInfo("OCEANIA", "大洋洲", "Oceania", "🦘")
    )

    val countries = listOf(
        // 东亚
        CategoryInfo("CN", "中国", "China", "🇨🇳"),
        CategoryInfo("JP", "日本", "Japan", "🇯🇵"),
        CategoryInfo("KR", "韩国", "Korea", "🇰🇷"),
        CategoryInfo("TW", "中国台湾", "Taiwan", "🇹🇼"),
        CategoryInfo("HK", "中国香港", "Hong Kong", "🇭🇰"),
        // 东南亚
        CategoryInfo("TH", "泰国", "Thailand", "🇹🇭"),
        CategoryInfo("VN", "越南", "Vietnam", "🇻🇳"),
        CategoryInfo("MY", "马来西亚", "Malaysia", "🇲🇾"),
        CategoryInfo("SG", "新加坡", "Singapore", "🇸🇬"),
        CategoryInfo("ID", "印度尼西亚", "Indonesia", "🇮🇩"),
        CategoryInfo("PH", "菲律宾", "Philippines", "🇵🇭"),
        CategoryInfo("MY", "缅甸", "Myanmar", "🇲🇲"),
        // 南亚
        CategoryInfo("IN", "印度", "India", "🇮🇳"),
        CategoryInfo("PK", "巴基斯坦", "Pakistan", "🇵🇰"),
        CategoryInfo("BD", "孟加拉国", "Bangladesh", "🇧🇩"),
        CategoryInfo("LK", "斯里兰卡", "Sri Lanka", "🇱🇰"),
        // 中东
        CategoryInfo("IL", "以色列", "Israel", "🇮🇱"),
        CategoryInfo("EG", "埃及", "Egypt", "🇪🇬"),
        CategoryInfo("TR", "土耳其", "Turkey", "🇹🇷"),
        CategoryInfo("SA", "沙特阿拉伯", "Saudi Arabia", "🇸🇦"),
        CategoryInfo("AE", "阿联酋", "UAE", "🇦🇪"),
        // 欧洲
        CategoryInfo("IT", "意大利", "Italy", "🇮🇹"),
        CategoryInfo("FR", "法国", "France", "🇫🇷"),
        CategoryInfo("ES", "西班牙", "Spain", "🇪🇸"),
        CategoryInfo("PT", "葡萄牙", "Portugal", "🇵🇹"),
        CategoryInfo("DE", "德国", "Germany", "🇩🇪"),
        CategoryInfo("UK", "英国", "United Kingdom", "🇬🇧"),
        CategoryInfo("GR", "希腊", "Greece", "🇬🇷"),
        CategoryInfo("RU", "俄罗斯", "Russia", "🇷🇺"),
        CategoryInfo("PL", "波兰", "Poland", "🇵🇱"),
        CategoryInfo("HU", "匈牙利", "Hungary", "🇭🇺"),
        CategoryInfo("SE", "瑞典", "Sweden", "🇸🇪"),
        CategoryInfo("NO", "挪威", "Norway", "🇳🇴"),
        // 北美
        CategoryInfo("US", "美国", "United States", "🇺🇸"),
        CategoryInfo("CA", "加拿大", "Canada", "🇨🇦"),
        CategoryInfo("MX", "墨西哥", "Mexico", "🇲🇽"),
        // 南美
        CategoryInfo("BR", "巴西", "Brazil", "🇧🇷"),
        CategoryInfo("AR", "阿根廷", "Argentina", "🇦🇷"),
        CategoryInfo("CL", "智利", "Chile", "🇨🇱"),
        CategoryInfo("CO", "哥伦比亚", "Colombia", "🇨🇴"),
        // 非洲
        CategoryInfo("MA", "摩洛哥", "Morocco", "🇲🇦"),
        CategoryInfo("ZA", "南非", "South Africa", "🇿🇦"),
        CategoryInfo("EG", "埃及", "Egypt", "🇪🇬"),
        CategoryInfo("KE", "肯尼亚", "Kenya", "🇰🇪"),
        CategoryInfo("NG", "尼日利亚", "Nigeria", "🇳🇬"),
        // 大洋洲
        CategoryInfo("AU", "澳大利亚", "Australia", "🇦🇺"),
        CategoryInfo("NZ", "新西兰", "New Zealand", "🇳🇿")
    )

    val categories = listOf(
        CategoryInfo("中式", "中式", "Chinese", "🥢"),
        CategoryInfo("日式", "日式", "Japanese", "🍣"),
        CategoryInfo("韩式", "韩式", "Korean", "🥘"),
        CategoryInfo("泰式", "泰式", "Thai", "🍛"),
        CategoryInfo("越南", "越南", "Vietnamese", "🍜"),
        CategoryInfo("印度", "印度", "Indian", "🍛"),
        CategoryInfo("意式", "意式", "Italian", "🍕"),
        CategoryInfo("法式", "法式", "French", "🥐"),
        CategoryInfo("西班牙", "西班牙", "Spanish", "🍤"),
        CategoryInfo("葡萄牙", "葡萄牙", "Portuguese", "🍷"),
        CategoryInfo("希腊", "希腊", "Greek", "🥗"),
        CategoryInfo("德国", "德国", "German", "🥨"),
        CategoryInfo("英国", "英国", "British", "🍝"),
        CategoryInfo("俄罗斯", "俄罗斯", "Russian", "🥘"),
        CategoryInfo("美式", "美式", "American", "🍔"),
        CategoryInfo("墨西哥", "墨西哥", "Mexican", "🌮"),
        CategoryInfo("巴西", "巴西", "Brazilian", "🍖"),
        CategoryInfo("阿根廷", "阿根廷", "Argentine", "🥩"),
        CategoryInfo("摩洛哥", "摩洛哥", "Moroccan", "🍲"),
        CategoryInfo("北非", "北非", "North African", "🍘"),
        CategoryInfo("南非", "南非", "South African", "🥘"),
        CategoryInfo("澳洲", "澳洲", "Australian", "🥧")
    )

    val cookingMethods = listOf(
        CategoryInfo("炒", "炒", "Stir Fry", "🔥"),
        CategoryInfo("煮", "煮", "Boil", "🍲"),
        CategoryInfo("蒸", "蒸", "Steam", "♨️"),
        CategoryInfo("烤", "烤", "Bake", "🍪"),
        CategoryInfo("炸", "炸", "Fry", "🍟"),
        CategoryInfo("炖", "炖", "Stew", "🥘"),
        CategoryInfo("焖", "焖", "Simmer", "🍲"),
        CategoryInfo("烩", "烩", "Braise", "🍛"),
        CategoryInfo("煎", "煎", "Pan Fry", "🥞"),
        CategoryInfo("凉拌", "凉拌", "Cold", "🥗"),
        CategoryInfo("烧烤", "烧烤", "Grill", "🍖"),
        CategoryInfo("烘培", "烘培", "Roast", "🍰"),
        CategoryInfo("汤", "汤", "Soup", "🥣"),
        CategoryInfo("沙拉", "沙拉", "Salad", "🥗"),
        CategoryInfo("甜品", "甜品", "Dessert", "🍩")
    )

    val ingredientTypes = listOf(
        CategoryInfo("肉类", "肉类", "Meat", "🥩"),
        CategoryInfo("海鲜", "海鲜", "Seafood", "🦐"),
        CategoryInfo("蔬菜", "蔬菜", "Vegetable", "🥬"),
        CategoryInfo("素食", "素食", "Vegetarian", "🥗"),
        CategoryInfo("主食", "主食", "Staple", "🍚"),
        CategoryInfo("甜点", "甜点", "Dessert", "🍰"),
        CategoryInfo("汤品", "汤品", "Soup", "🥣"),
        CategoryInfo("沙拉", "沙拉", "Salad", "🥗"),
        CategoryInfo("面食", "面食", "Pasta", "🍝"),
        CategoryInfo("米饭", "米饭", "Rice", "🍚")
    )

    val flavorProfiles = listOf(
        CategoryInfo("麻辣", "麻辣", "Spicy & Numbing", "🌶️"),
        CategoryInfo("酸甜", "酸甜", "Sweet & Sour", "🍬"),
        CategoryInfo("咸鲜", "咸鲜", "Savory", "🍲"),
        CategoryInfo("清淡", "清淡", "Light", "🥗"),
        CategoryInfo("香辣", "香辣", "Spicy & Aromatic", "🌶️"),
        CategoryInfo("醇厚", "醇厚", "Rich", "🥘"),
        CategoryInfo("蒜香", "蒜香", "Garlic", "🧄"),
        CategoryInfo("清新", "清新", "Fresh", "🍋"),
        CategoryInfo("烟熏", "烟熏", "Smoky", "🫕"),
        CategoryInfo("香甜", "香甜", "Sweet", "🍯"),
        CategoryInfo("奶香", "奶香", "Buttery", "🧈"),
        CategoryInfo("香草", "香草", "Herbal", "🌿")
    )

    fun getRegionInfo(regionCode: String): CategoryInfo? {
        return regions.find { it.code == regionCode }
    }

    fun getCategoryInfo(categoryCode: String): CategoryInfo? {
        return categories.find { it.code == categoryCode }
    }

    fun getCookingMethodInfo(methodCode: String): CategoryInfo? {
        return cookingMethods.find { it.code == methodCode }
    }

    fun getIngredientTypeInfo(typeCode: String): CategoryInfo? {
        return ingredientTypes.find { it.code == typeCode }
    }

    fun getFlavorInfo(flavorCode: String): CategoryInfo? {
        return flavorProfiles.find { it.code == flavorCode }
    }

    fun getCountryInfo(countryCode: String): CategoryInfo? {
        return countries.find { it.code == countryCode }
    }

    fun getDifficultyLabel(level: Int): String {
        return when (level) {
            1 -> "入门"
            2 -> "简单"
            3 -> "中等"
            4 -> "困难"
            5 -> "大师"
            else -> "未知"
        }
    }

    fun getDifficultyStars(level: Int): String {
        return "★".repeat(level) + "☆".repeat(5 - level)
    }
}