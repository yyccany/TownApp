package com.example.townapp.ui.components

import com.example.townapp.data.FoodItem
import com.example.townapp.data.ClothingItem
import com.example.townapp.data.IdiomItem

object IdiomTrigger {

    fun getFoodIdioms(food: FoodItem): List<IdiomItem> {
        val idioms = mutableListOf<IdiomItem>()

        if (food.shelfLifeDays < 3 && food.nutrientDensityScore < 50) {
            idioms.add(createYinXiaoShiDa())
        }

        if (food.nutrientDensityScore < 30 || (food.fatPer100g > 20 && food.carbohydratePer100g > 50)) {
            idioms.add(createYanErDaoLing())
        }

        if (food.processingLoss > 0.3) {
            idioms.add(createHuaErBuShi())
        }

        if (food.isIQTax || food.iqTaxLevel >= 2) {
            idioms.add(createMingBuFuShi())
        }

        return idioms
    }

    fun getClothingIdioms(clothing: ClothingItem): List<IdiomItem> {
        val idioms = mutableListOf<IdiomItem>()

        if (clothing.wearCount == 0 && clothing.purchaseDate < System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000) {
            idioms.add(createBiZhouZiZhen())
        }

        if (clothing.costPerWear > 10 && clothing.durabilityScore < 0.5) {
            idioms.add(createHuaErBuShi())
        }

        if (clothing.isIQTax || clothing.iqTaxLevel >= 2) {
            idioms.add(createMingBuFuShi())
        }

        if (clothing.evolutionParadoxScore > 0.7) {
            idioms.add(createZhuLiuFengYun())
        }

        return idioms
    }

    private fun createYinXiaoShiDa(): IdiomItem {
        return IdiomItem(
            id = 1,
            idiom = "因小失大",
            traditionalMeaning = "为了小的利益而造成大的损失",
            modernInterpretation = "为了节省一点钱购买即将过期的食物，结果损害了健康，付出更大的医疗成本",
            logicalAnalysis = "人们往往只看到眼前的小优惠，却忽略了健康风险带来的潜在巨大成本。过期食品可能滋生细菌，导致食物中毒或慢性健康问题。",
            category = "消费误区",
            relatedCognitiveBiases = listOf("损失厌恶", "短视偏差"),
            awakeningValue = 3
        )
    }

    private fun createYanErDaoLing(): IdiomItem {
        return IdiomItem(
            id = 2,
            idiom = "掩耳盗铃",
            traditionalMeaning = "自欺欺人，以为自己听不到，别人也听不到",
            modernInterpretation = "明知是垃圾食品对健康有害，却安慰自己\"偶尔吃没关系\"，无视长期累积的健康风险",
            logicalAnalysis = "人类大脑倾向于忽视缓慢发生的危害。高热量、高糖分、高脂肪的食物会逐渐损害身体，但因为效果不明显而被忽视。",
            category = "健康认知",
            relatedCognitiveBiases = listOf("乐观偏差", "即时满足"),
            awakeningValue = 3
        )
    }

    private fun createBiZhouZiZhen(): IdiomItem {
        return IdiomItem(
            id = 3,
            idiom = "敝帚自珍",
            traditionalMeaning = "把自己家里的破扫帚当成宝贝",
            modernInterpretation = "购买了不适合自己的衣物，虽然很少穿甚至不穿，但因为花了钱就舍不得处理，让它占据空间",
            logicalAnalysis = "这是典型的沉没成本谬误。人们不愿意承认自己买错了东西，宁愿让物品闲置也不愿丢弃或转卖。",
            category = "消费误区",
            relatedCognitiveBiases = listOf("沉没成本谬误", "禀赋效应"),
            awakeningValue = 2
        )
    }

    private fun createHuaErBuShi(): IdiomItem {
        return IdiomItem(
            id = 4,
            idiom = "华而不实",
            traditionalMeaning = "外表华丽但内容空虚",
            modernInterpretation = "追求品牌、款式等外在因素，忽视衣物的舒适度、耐用性等实际价值",
            logicalAnalysis = "消费主义通过营销让人们追求符号价值而非实用价值。高价不一定等于高质量，品牌溢价往往超过产品本身价值。",
            category = "消费误区",
            relatedCognitiveBiases = listOf("品牌崇拜", "社会认同"),
            awakeningValue = 3
        )
    }

    private fun createMingBuFuShi(): IdiomItem {
        return IdiomItem(
            id = 5,
            idiom = "名不副实",
            traditionalMeaning = "名声与实际不符",
            modernInterpretation = "被营销宣传的\"纯天然\"、\"有机\"等概念吸引，付出高价却没有得到相应的品质",
            logicalAnalysis = "商家利用消费者对健康的关注，通过概念营销收取智商税。需要理性看待标签，关注实际成分和营养价值。",
            category = "消费误区",
            relatedCognitiveBiases = listOf("光环效应", "信息不对称"),
            awakeningValue = 3
        )
    }

    private fun createZhuLiuFengYun(): IdiomItem {
        return IdiomItem(
            id = 6,
            idiom = "逐流风云",
            traditionalMeaning = "追逐潮流，随波逐流",
            modernInterpretation = "盲目跟随时尚潮流，购买大量穿不了几次就过时的衣物",
            logicalAnalysis = "时尚产业通过快速迭代制造需求，让消费者陷入\"购买-淘汰-再购买\"的循环。真正的穿衣自由是找到适合自己的风格，而非追逐潮流。",
            category = "消费误区",
            relatedCognitiveBiases = listOf("从众心理", "害怕错过"),
            awakeningValue = 2
        )
    }
}
