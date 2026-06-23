package com.example.townapp.domain

/**
 * 宠物管理类 —— 小镇居民的陪伴者
 *
 * 内核：不评判、不说教、不制造焦虑。
 * 宠物只是「看见」你的选择，轻轻说一句，不做对错判断。
 *
 * 触发时机：仅手动选择用餐时触发，自动托管模式不触发。
 */
object PetManager {

    /** 宠物默认名称 */
    private const val PET_NAME = "小东西"

    /**
     * 根据食物信息生成宠物反馈文案
     *
     * @param foodName 食物名称
     * @param foodNote 食物备注（风味描述）
     * @param category 食物分类
     * @param nutritionalScore 营养分数（0-100）
     * @return 宠物对话文案，null 表示不触发对话
     */
    fun getFoodReply(
        foodName: String,
        foodNote: String?,
        category: String,
        nutritionalScore: Double
    ): String? {
        return generateDefaultReply(foodName, category, nutritionalScore)
    }

    /**
     * 生成默认宠物反馈文案
     * 按食物营养等级区分，不评判好坏，只做观察式描述
     */
    private fun generateDefaultReply(
        foodName: String,
        category: String,
        nutritionalScore: Double
    ): String {
        val prefix = when {
            nutritionalScore >= 80 -> "「$PET_NAME」轻轻晃了晃脑袋："
            nutritionalScore >= 50 -> "「$PET_NAME」眨了眨眼："
            nutritionalScore >= 30 -> "「$PET_NAME」耷拉着耳朵，小声说："
            else -> "「$PET_NAME」蹭了蹭你的手："
        }

        val body = when {
            nutritionalScore >= 80 -> healthyReplies(category, foodName)
            nutritionalScore >= 50 -> moderateReplies(category, foodName)
            nutritionalScore >= 30 -> unhealthyReplies(category, foodName)
            else -> veryUnhealthyReplies(category, foodName)
        }

        return "$prefix$body"
    }

    // ============================================
    // 健康食材（营养分 ≥ 80）
    // ============================================
    private fun healthyReplies(category: String, name: String): String {
        return when {
            category.contains("蔬菜") || category.contains("水果") ->
                "今天吃${name}呀，水灵灵的。你的身体会记住这些新鲜的味道。"
            category.contains("主食") || category.contains("米饭") || name.contains("米饭") ->
                "今天吃得清淡，${name}暖暖的，你的气色稳定了不少。"
            category.contains("鱼") || category.contains("海鲜") ->
                "${name}是好东西，蛋白质对身体很温柔。"
            category.contains("豆") || category.contains("豆腐") ->
                "豆制品呀，朴实又扎实，身体喜欢这种踏实的营养。"
            category.contains("蛋") || category.contains("鸡蛋") ->
                "${name}简简单单，但该有的营养都有。"
            category.contains("鸡") || category.contains("牛") || category.contains("猪") ->
                "${name}搭配得不错，蛋白质和满足感都有了。"
            else ->
                "${name}吃得很舒服，身体会感谢你的。"
        }
    }

    // ============================================
    // 中等食材（营养分 50-79）
    // ============================================
    private fun moderateReplies(category: String, name: String): String {
        return when {
            category.contains("面") || category.contains("粉") ->
                "${name}很方便，偶尔吃一顿，胃里暖暖的。"
            category.contains("快餐") || category.contains("便当") ->
                "赶时间的时候${name}是个好选择，下次不赶时间的话，可以多嚼两口。"
            category.contains("零食") || category.contains("点心") ->
                "偶尔吃点${name}挺好的，开心就好。"
            category.contains("饮料") || category.contains("奶茶") ->
                "${name}甜甜的，喝的时候心情确实会好一点。"
            else ->
                "${name}还不错，吃得刚刚好。"
        }
    }

    // ============================================
    // 不太健康（营养分 30-49）
    // ============================================
    private fun unhealthyReplies(category: String, name: String): String {
        return when {
            category.contains("炸") || name.contains("炸") ->
                "${name}闻着真香……不过油脂偏高，长期吃的话精力容易下降。记得多休息。"
            category.contains("外卖") ->
                "${name}方便是方便，不过外面的油盐不太好控制。下次有空的话，自己做一顿也不错。"
            category.contains("烧烤") || name.contains("烤") ->
                "${name}偶尔吃一顿很过瘾，但别太频繁哦。"
            category.contains("甜") || category.contains("糖") ->
                "${name}甜食吃得开心，短暂提升愉悦感，不过晚上睡眠质量可能会轻微下降。"
            category.contains("肉") && (name.contains("肥") || name.contains("油")) ->
                "${name}偶尔吃一次没关系，身体需要时间代谢这些油脂。"
            else ->
                "吃${name}也挺好的，吃得开心最重要。"
        }
    }

    // ============================================
    // 很不健康（营养分 < 30）
    // ============================================
    private fun veryUnhealthyReplies(category: String, name: String): String {
        return when {
            category.contains("泡面") || name.contains("泡面") ->
                "${name}是很多人的深夜伴侣。不过营养确实不多，吃完记得喝点热水。"
            category.contains("辣条") || name.contains("辣条") ->
                "辣条的味道很刺激，但对胃不太友好。吃完了多喝点温水吧。"
            else ->
                "${name}吃完了，陪你再走走。"
        }
    }

    // ============================================
    // 自动托管模式下的精简提醒
    // ============================================
    fun getAutoFeedSummary(mealName: String): String {
        return "「$PET_NAME」悄悄看了一眼：给你准备了${mealName}。"
    }

    // ============================================
    // 换衣服反馈
    // ============================================
    /**
     * 根据衣物信息生成宠物反馈文案
     *
     * @param itemName 衣物名称
     * @param material 面料材质
     * @param category 衣物分类
     * @param isIQTax 是否为智商税商品
     * @param wearCount 已穿次数
     * @return 宠物对话文案
     */
    fun getWearReply(
        itemName: String,
        material: String,
        category: String,
        isIQTax: Boolean,
        wearCount: Int
    ): String {
        val prefix = when {
            wearCount <= 1 -> "「$PET_NAME」歪着头看了看："
            wearCount <= 5 -> "「$PET_NAME」眨了眨眼："
            wearCount <= 20 -> "「$PET_NAME」轻轻晃了晃脑袋："
            else -> "「$PET_NAME」蹭了蹭衣角："
        }

        val body = when {
            isIQTax -> iqTaxClothingReplies(itemName, category)
            material.contains("棉") || material.contains("麻") || material.contains("丝") || material.contains("毛") ->
                "换上了${itemName}啊，${material}透气又亲肤，穿着很舒服吧。"
            category.contains("内衣") || category.contains("袜") ->
                "${itemName}是贴身的，选得好很重要。"
            material.contains("化纤") || material.contains("涤纶") || material.contains("尼龙") ->
                "${itemName}挺耐穿的，不过${material}不太透气，出汗的时候注意一下。"
            wearCount >= 30 -> "${itemName}已经穿了很多次了，是真的很喜欢这件呀。"
            else -> "换上了${itemName}，看起来不错。"
        }

        return "$prefix$body"
    }

    private fun iqTaxClothingReplies(itemName: String, category: String): String {
        return when {
            category.contains("鞋") -> "${itemName}……穿得舒服就行，价格不重要。"
            category.contains("包") -> "${itemName}挺好看的，不过包是用来装东西的，不是用来装面子的。"
            else -> "${itemName}穿上了，但说实话，它的价格里有很大一部分是你看不见的溢价。"
        }
    }

    // ============================================
    // 服药反馈
    // ============================================
    /**
     * 根据药品信息生成宠物反馈文案
     *
     * @param drugName 药品名称
     * @param category 药品分类
     * @param sideEffectTriggered 是否触发副作用
     * @return 宠物对话文案
     */
    fun getDrugReply(
        drugName: String,
        category: String,
        sideEffectTriggered: Boolean
    ): String {
        val prefix = if (sideEffectTriggered) {
            "「$PET_NAME」担心地蹭了蹭你："
        } else {
            "「$PET_NAME」安静地趴在你旁边："
        }

        val body = when {
            category.contains("抗真菌") || category.contains("皮肤") ->
                "用了${drugName}，身体在慢慢恢复。给它一点时间。"
            category.contains("止痛") || category.contains("退烧") ->
                "${drugName}能缓解症状，但记得找出生病的根源，别只压住表面。"
            category.contains("维生素") || category.contains("保健") ->
                "${drugName}是补充剂，不是替代品。好好吃饭比什么都重要。"
            sideEffectTriggered ->
                "${drugName}起效了，但身体好像有点不太舒服。多休息，别硬撑。"
            else ->
                "用了${drugName}。药是工具，身体自己的修复力才是真正的医生。"
        }

        return "$prefix$body"
    }
}