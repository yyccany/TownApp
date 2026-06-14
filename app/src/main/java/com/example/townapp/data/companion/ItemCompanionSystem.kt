package com.example.townapp.data.companion

import com.example.townapp.data.database.entity.ProductEntity

/**
 * 物品使用陪伴系统
 *
 * 当用户使用/穿戴/购买任何物品时，三个小家伙会自动看到，
 * 并对用户说一句软乎乎的、专属的陪伴话。
 *
 * 让你感觉到：不管你做了什么，都有人在陪着你。
 */

/**
 * 三角色专属对话
 */
data class ItemCompanionQuotes(
    val taffi: String,   // 塔菲喵 - 活泼可爱风格
    val gugaga: String,  // 咕咕嘎嘎 - 羡慕委屈风格
    val doro: String     // doro - 温柔治愈风格
)

/**
 * 物品分类对应的陪伴模板
 */
private object CategoryTemplates {

    // 塔菲喵模板（活泼可爱）
    val taffiTemplates = mapOf(
        "食品" to listOf(
            "吃了{name}喵！好满足的样子呢😋",
            "{name}听起来好好吃喵！今天也是幸福的一天🍴",
            "{name}！小猫咪也想尝尝喵🐱"
        ),
        "饮料" to listOf(
            "喝了{name}喵！冰冰凉凉的好舒服🧊",
            "{name}！夏天喝这个最棒了对不对喵☕",
            "哇{name}！听起来就很解渴喵💧"
        ),
        "服装" to listOf(
            "穿了{name}喵！今天也要美美的哦👗",
            "{name}！小猫咪觉得超级好看喵✨",
            "{name}！你穿起来一定很好看喵👗"
        ),
        "电子产品" to listOf(
            "用了{name}喵！科技感满满呢📱",
            "{name}！小猫咪也想知道是什么感觉喵💻",
            "哇{name}喵！现代生活好神奇哦✨"
        ),
        "药品" to listOf(
            "用了{name}喵...身体不舒服吗？塔菲在这里陪着你哦💊",
            "{name}...塔菲知道你有点不舒服...但是会好起来的喵😺",
            "用了{name}喵...乖乖休息，很快就会好的💜"
        ),
        "家具" to listOf(
            "{name}看起来好舒服喵～在家就要好好放松🏠",
            "{name}喵！小猫咪也想在上面滚来滚去🛋️",
            "有了{name}，家更温馨了呢喵🏠"
        ),
        "交通工具" to listOf(
            "今天用了{name}喵！去哪里玩啦～🚗",
            "{name}！小猫咪好羡慕你能到处跑喵🌟",
            "哇{name}喵！旅途顺利吗？🚗"
        ),
        "默认" to listOf(
            "你今天用了{name}喵！✨",
            "{name}...塔菲觉得今天你做得很好喵💛",
            "哇{name}喵！小猫咪也在哦🐱"
        )
    )

    // 咕咕嘎嘎模板（羡慕委屈）
    val gugagaTemplates = mapOf(
        "食品" to listOf(
            "{name}！看起来好香咕咕🤤",
            "我也想吃{name}咕咕...🥺",
            "{name}！光是看着就好想吃咕咕🍴"
        ),
        "饮料" to listOf(
            "{name}！好想喝一口咕咕🧋",
            "冰凉的{name}！好羡慕你咕咕🥺",
            "{name}！咕咕也想冰冰凉凉的🌊"
        ),
        "服装" to listOf(
            "{name}！穿起来一定很好看咕咕👗",
            "我也想有{name}咕咕...🥺",
            "{name}！好羡慕你有这么好看的衣服咕咕✨"
        ),
        "电子产品" to listOf(
            "{name}！好高科技咕咕📱",
            "我也想玩{name}咕咕...🥺",
            "{name}！看起来好厉害咕咕💻"
        ),
        "药品" to listOf(
            "{name}...咕咕希望你快快好起来🥺",
            "吃了{name}...咕咕陪着你，很快就不难受了🧸",
            "{name}...乖乖休息，咕咕给你温暖的抱抱🥺"
        ),
        "家具" to listOf(
            "{name}！看起来好舒服咕咕🛋️",
            "我也想躺在{name}上面咕咕...🥺",
            "{name}！家的感觉咕咕🏠"
        ),
        "交通工具" to listOf(
            "{name}！好酷炫咕咕🚗",
            "我也想坐{name}咕咕...🥺",
            "{name}！能带我去看看世界吗咕咕🌍"
        ),
        "默认" to listOf(
            "{name}！咕咕好羡慕你哦🥺",
            "我也要{name}咕咕...🥺",
            "{name}...咕咕觉得你今天很棒呢✨"
        )
    )

    // doro模板（温柔治愈）
    val doroTemplates = mapOf(
        "食品" to listOf(
            "{name}...好好吃呢💛",
            "食物是生活的味道呀...{name}一定很美味吧🍽️",
            "{name}...每一口都是小小的幸福呢🌿"
        ),
        "饮料" to listOf(
            "{name}...入口的温度刚刚好呢☕",
            "喝点东西...给自己一点时间休息吧💧",
            "{name}...这是给自己的一份温柔呢🍵"
        ),
        "服装" to listOf(
            "{name}...穿得舒服最重要了呢👗",
            "衣服是我们表达自己的方式呢...你喜欢就好💜",
            "{name}...你值得穿好看的衣服🌸"
        ),
        "电子产品" to listOf(
            "{name}...科技让生活更便利了呢📱",
            "工具是为了让我们过得更好...希望你用得开心💻",
            "{name}...它帮你连接这个世界呢🌐"
        ),
        "药品" to listOf(
            "{name}...乖乖照顾自己，你已经很棒了💊",
            "不舒服的时候...要好好休息哦🌙",
            "{name}...身体会慢慢好起来的，你要对自己温柔一点💜"
        ),
        "家具" to listOf(
            "{name}...家是最舒服的地方呢🏠",
            "有一个舒适的家...是最幸福的事了呢🛋️",
            "{name}...好好享受在家的时光吧🌙"
        ),
        "交通工具" to listOf(
            "{name}...带你去看更大的世界呢🚗",
            "路上的风景...也是旅行的一部分呢🌍",
            "{name}...不管去哪里，平安最重要💛"
        ),
        "默认" to listOf(
            "{name}...你做得很好哦💛",
            "不管是什么...你用了它，就有它的意义呢✨",
            "{name}...小小的日常，就是生活的全部呢🌿"
        )
    )

    /**
     * 获取物品对应的分类
     */
    fun getCategory(product: ProductEntity): String {
        return when {
            product.category.contains("食品") || product.category.contains("食物") -> "食品"
            product.category.contains("饮料") || product.category.contains("饮品") -> "饮料"
            product.category.contains("服装") || product.category.contains("衣") || product.category.contains("鞋") -> "服装"
            product.category.contains("电子") || product.category.contains("手机") || product.category.contains("电脑") -> "电子产品"
            product.category.contains("药") || product.category.contains("医疗") -> "药品"
            product.category.contains("家具") || product.category.contains("家私") || product.category.contains("家居") -> "家具"
            product.category.contains("交通") || product.category.contains("车") || product.category.contains("自行车") -> "交通工具"
            else -> "默认"
        }
    }
}

/**
 * 物品陪伴语录生成器
 */
object ItemCompanionGenerator {

    /**
     * 为物品生成三角色专属对话
     */
    fun generateQuotes(product: ProductEntity): ItemCompanionQuotes {
        val category = CategoryTemplates.getCategory(product)
        val name = product.name

        return ItemCompanionQuotes(
            taffi = generateTaffiQuote(category, name),
            gugaga = generateGugagaQuote(category, name),
            doro = generateDoroQuote(category, name)
        )
    }

    private fun generateTaffiQuote(category: String, name: String): String {
        val templates = CategoryTemplates.taffiTemplates[category]
            ?: CategoryTemplates.taffiTemplates["默认"]!!
        return templates.random().replace("{name}", name)
    }

    private fun generateGugagaQuote(category: String, name: String): String {
        val templates = CategoryTemplates.gugagaTemplates[category]
            ?: CategoryTemplates.gugagaTemplates["默认"]!!
        return templates.random().replace("{name}", name)
    }

    private fun generateDoroQuote(category: String, name: String): String {
        val templates = CategoryTemplates.doroTemplates[category]
            ?: CategoryTemplates.doroTemplates["默认"]!!
        return templates.random().replace("{name}", name)
    }
}

/**
 * 增强版物品使用事件
 */
data class ItemUseCompanionEvent(
    val product: ProductEntity,
    val scene: UseScene,
    val quotes: ItemCompanionQuotes,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * 物品陪伴系统管理器
 */
object ItemCompanionSystem {

    /**
     * 根据使用场景生成事件
     */
    fun createEvent(product: ProductEntity, scene: UseScene): ItemUseCompanionEvent {
        val quotes = ItemCompanionGenerator.generateQuotes(product)
        return ItemUseCompanionEvent(
            product = product,
            scene = scene,
            quotes = quotes
        )
    }

    /**
     * 获取使用场景对应的前缀
     */
    fun getScenePrefix(scene: UseScene, productName: String): String {
        return when (scene) {
            UseScene.WEAR -> "穿了"
            UseScene.USE -> "用了"
            UseScene.BUY -> "买了"
            UseScene.EAT -> if (isDrink(productName)) "喝了" else "吃了"
        }
    }

    /**
     * 判断是否为饮料类
     */
    private fun isDrink(name: String): Boolean {
        val drinks = listOf("饮料", "水", "茶", "咖啡", "奶茶", "汁", "汤", "奶", "酒", "啤", "可乐", "汽水", "酸奶", "豆浆", "粥", "冰淇淋", "冰沙", "奶昔", "橙汁", "苹果汁", "葡萄汁")
        return drinks.any { name.contains(it) }
    }
}
