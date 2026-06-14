package com.example.townapp.data.companion

import com.example.townapp.data.database.entity.FoodNutritionEntity
import com.example.townapp.data.database.entity.ProductEntity

/**
 * 智能物品陪伴语录生成器
 *
 * 根据物品的**真实数据**，生成专属的、更细节的陪伴话。
 * 每个物品的数据不一样，生成的话也完全不一样。
 */

/**
 * 三角色专属对话
 */
data class SmartCompanionQuotes(
    val taffi: String,   // 塔菲喵 - 活泼可爱风格
    val gugaga: String,  // 咕咕嘎嘎 - 羡慕委屈风格
    val doro: String     // doro - 温柔治愈风格
)

/**
 * 智能物品陪伴生成器
 */
object SmartCompanionGenerator {

    /**
     * 为物品生成专属对话（基于真实数据）
     */
    fun generateQuotes(
        product: ProductEntity,
        nutrition: FoodNutritionEntity? = null
    ): SmartCompanionQuotes {
        // 根据不同类型生成不同风格的对话
        return when {
            // 食品类 - 使用营养数据生成专属对话
            isFoodCategory(product) && nutrition != null -> generateFoodQuotes(product, nutrition)
            // 智商税类 - 温柔化解
            isIQTaxProduct(product, nutrition) -> generateIQTaxQuotes(product)
            // 普通物品 - 使用通用模板
            else -> generateGenericQuotes(product)
        }
    }

    /**
     * 判断是否为食品分类
     */
    private fun isFoodCategory(product: ProductEntity): Boolean {
        val foodCategories = listOf("食品", "食物", "零食", "水果", "蔬菜", "肉", "蛋", "奶", "饮料", "饮品", "主食")
        return foodCategories.any { product.category.contains(it) }
    }

    /**
     * 判断是否为智商税产品
     */
    private fun isIQTaxProduct(product: ProductEntity, nutrition: FoodNutritionEntity?): Boolean {
        return nutrition?.isIQTax == true || product.iqTaxScore > 50
    }

    /**
     * 根据营养数据生成食品专属对话
     */
    private fun generateFoodQuotes(
        product: ProductEntity,
        nutrition: FoodNutritionEntity
    ): SmartCompanionQuotes {
        val name = product.name
        val quotes = mutableListOf<Pair<String, String>>() // taffi, doro

        // 高蛋白食物
        if (nutrition.proteinPer100g > 20) {
            quotes.add(Pair(
                "哇{name}！蛋白质好丰富喵！吃完了力气满满哦🥩",
                "{name}...满满的蛋白质呢，身体会感谢你的💪"
            ))
        }

        // 高糖食物
        if (nutrition.sugarPer100g > 15) {
            quotes.add(Pair(
                "甜甜的{name}喵！偶尔吃点甜的，心情会好呢🍬",
                "{name}...甜甜的味道，生活也需要这点甜呢🍭"
            ))
        }

        // 高盐食物
        if (nutrition.saltPer100g > 2) {
            quotes.add(Pair(
                "{name}有点咸喵...记得多喝点水哦💧",
                "{name}...有点咸...身体需要更多水分了呢，记得喝水呀💧"
            ))
        }

        // 高热量食物
        if (nutrition.caloriesPer100g > 400) {
            quotes.add(Pair(
                "{name}热量有点高喵...但是吃饱了才有力气嘛🍔",
                "{name}...热量满满呢，偶尔吃饱点也没关系哦🍔"
            ))
        }

        // 高纤维食物
        if (nutrition.fiberPer100g > 5) {
            quotes.add(Pair(
                "{name}纤维好丰富喵！肠道健康哦🥬",
                "{name}...丰富的纤维，肠道会很舒服呢🥬"
            ))
        }

        // 高脂肪食物
        if (nutrition.fatPer100g > 20) {
            quotes.add(Pair(
                "{name}油脂有点多喵...但是香呀，偶尔吃吃没关系喵🍖",
                "{name}...香香的...偶尔犒劳一下自己也没关系呢🍖"
            ))
        }

        // 低碳水食物（适合减肥）
        if (nutrition.carbohydratePer100g < 10 && nutrition.caloriesPer100g > 50) {
            quotes.add(Pair(
                "{name}碳水不高喵！想控制碳水的话，这个很合适哦🥗",
                "{name}...低碳水...对身体很友好呢🥗"
            ))
        }

        // 高维生素C食物
        if (nutrition.vitaminCMg > 50) {
            quotes.add(Pair(
                "{name}维生素C好丰富喵！皮肤会变好哦🍊",
                "{name}...满满的维生素C...会变美的呢🍊"
            ))
        }

        // 高钙食物
        if (nutrition.calciumMg > 100) {
            quotes.add(Pair(
                "{name}钙质丰富喵！骨头更强壮哦🦴",
                "{name}...钙质满满...对身体很好呢🦴"
            ))
        }

        // 智商税食物
        if (nutrition.isIQTax) {
            quotes.add(Pair(
                "{name}...塔菲觉得开心最重要啦，不用想太多喵🍟",
                "{name}...不用想太多啦...开心就好呢🍟"
            ))
        }

        // 如果没有特殊数据，生成通用对话
        if (quotes.isEmpty()) {
            return generateGenericQuotes(product)
        }

        // 随机选择一条
        val selected = quotes.random()
        val taffi = selected.first.replace("{name}", name)
        val doro = selected.second.replace("{name}", name)

        return SmartCompanionQuotes(
            taffi = taffi,
            gugaga = generateGugagaByCategory(product),
            doro = doro
        )
    }

    /**
     * 根据分类生成咕咕嘎嘎的对话
     */
    private fun generateGugagaByCategory(product: ProductEntity): String {
        val name = product.name
        val category = product.category

        return when {
            category.contains("肉") -> "{name}！好香咕咕...我也想吃肉肉🤤".replace("{name}", name)
            category.contains("水果") -> "{name}！好甜咕咕...好想吃🍎".replace("{name}", name)
            category.contains("饮料") || category.contains("饮品") -> "{name}！好想喝一口咕咕🧋".replace("{name}", name)
            category.contains("零食") -> "{name}！看起来好香咕咕🍪".replace("{name}", name)
            category.contains("蔬菜") -> "{name}！好健康咕咕...就是有点羡慕🥬".replace("{name}", name)
            category.contains("甜") -> "{name}！好甜咕咕...🥺".replace("{name}", name)
            else -> "{name}！好想吃咕咕...🥺".replace("{name}", name)
        }
    }

    /**
     * 智商税产品专属对话
     */
    private fun generateIQTaxQuotes(product: ProductEntity): SmartCompanionQuotes {
        val name = product.name
        val reason = product.description.take(20)

        return SmartCompanionQuotes(
            taffi = "{name}喵...塔菲觉得你喜欢就好啦，不用想太多哦💜".replace("{name}", name),
            gugaga = "{name}...咕咕觉得开心最重要啦🥺".replace("{name}", name),
            doro = "{name}...不用想太多啦...你开心就好呢💜".replace("{name}", name)
        )
    }

    /**
     * 普通物品通用对话
     */
    private fun generateGenericQuotes(product: ProductEntity): SmartCompanionQuotes {
        val name = product.name
        val category = product.category

        val (taffiTemplate, gugagaTemplate, doroTemplate) = getTemplatesByCategory(category)

        return SmartCompanionQuotes(
            taffi = taffiTemplate.replace("{name}", name),
            gugaga = gugagaTemplate.replace("{name}", name),
            doro = doroTemplate.replace("{name}", name)
        )
    }

    /**
     * 根据分类获取模板
     */
    private fun getTemplatesByCategory(category: String): Triple<String, String, String> {
        return when {
            category.contains("服装") || category.contains("衣") || category.contains("鞋") -> Triple(
                "{name}喵！今天穿得美美的哦👗",
                "{name}！好漂亮咕咕...👗",
                "{name}...穿得舒服最重要，你喜欢就好💜"
            )
            category.contains("电子") || category.contains("手机") || category.contains("电脑") -> Triple(
                "{name}喵！科技感满满呢📱",
                "{name}！好高科技咕咕📱",
                "{name}...它帮你连接世界呢📱"
            )
            category.contains("药") || category.contains("医疗") -> Triple(
                "{name}喵...塔菲陪着你呢，乖乖休息哦💊",
                "{name}...咕咕希望你快好起来🥺",
                "{name}...要好好照顾自己呢💜"
            )
            category.contains("家具") || category.contains("家居") -> Triple(
                "{name}喵！在家里好舒服🏠",
                "{name}！好羡慕你咕咕🛋️",
                "{name}...家是最温暖的地方呢🏠"
            )
            category.contains("交通") || category.contains("车") -> Triple(
                "{name}喵！去哪里玩啦🚗",
                "{name}！好酷咕咕🚗",
                "{name}...平安出行最重要呢🚗"
            )
            else -> Triple(
                "{name}喵！你用了呢✨",
                "{name}！好棒咕咕✨",
                "{name}...你做得很好哦💛"
            )
        }
    }
}

/**
 * 物品使用陪伴事件
 */
data class SmartCompanionEvent(
    val product: ProductEntity,
    val nutrition: FoodNutritionEntity?,
    val scene: UseScene,
    val quotes: SmartCompanionQuotes,
    val timestamp: Long = System.currentTimeMillis()
) {
    /**
     * 获取完整的场景描述
     */
    fun getSceneDescription(): String {
        val prefix = when (scene) {
            UseScene.EAT -> {
                val name = product.name
                when {
                    name.contains("饮料") || name.contains("水") || name.contains("茶") || name.contains("咖啡") ||
                    name.contains("奶茶") || name.contains("汁") || name.contains("汤") || name.contains("奶") ||
                    name.contains("酒") || name.contains("啤") || name.contains("可乐") || name.contains("汽水") -> "喝了"
                    else -> "吃了"
                }
            }
            else -> scene.prefix
        }
        return "$prefix${product.name}"
    }
}

/**
 * 智能物品陪伴系统管理器
 */
object SmartCompanionSystem {

    /**
     * 根据使用场景创建陪伴事件
     */
    fun createEvent(
        product: ProductEntity,
        nutrition: FoodNutritionEntity?,
        scene: UseScene
    ): SmartCompanionEvent {
        val quotes = SmartCompanionGenerator.generateQuotes(product, nutrition)
        return SmartCompanionEvent(
            product = product,
            nutrition = nutrition,
            scene = scene,
            quotes = quotes
        )
    }

    /**
     * 批量创建多个事件（用于用户一次性使用多个物品）
     */
    fun createEvents(
        products: List<Pair<ProductEntity, FoodNutritionEntity?>>,
        scene: UseScene
    ): List<SmartCompanionEvent> {
        return products.map { (product, nutrition) ->
            createEvent(product, nutrition, scene)
        }
    }
}
