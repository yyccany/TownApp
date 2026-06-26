package com.example.townapp

import com.example.townapp.data.database.entity.GoodsConsumptionTagEntity
import com.example.townapp.data.database.entity.MindTextLibEntity
import com.example.townapp.data.database.entity.QuoteEntity

object ConsumptionSeedData {

    fun goodsTags(): List<GoodsConsumptionTagEntity> = listOf(
        GoodsConsumptionTagEntity(goodsId = "100", goodsType = "food", tagKey = "nutrient_high", scoreDelta = 3, description = "鸡蛋：优质蛋白"),
        GoodsConsumptionTagEntity(goodsId = "101", goodsType = "food", tagKey = "nutrient_high", scoreDelta = 3, description = "鸡胸肉：高蛋白低脂肪"),
        GoodsConsumptionTagEntity(goodsId = "102", goodsType = "food", tagKey = "nutrient_high", scoreDelta = 3, description = "西兰花：高纤维"),
        GoodsConsumptionTagEntity(goodsId = "103", goodsType = "food", tagKey = "staple_food", scoreDelta = 1, description = "米饭：日常主食"),
        GoodsConsumptionTagEntity(goodsId = "104", goodsType = "food", tagKey = "natural_food", scoreDelta = 2, description = "苹果：天然水果"),
        GoodsConsumptionTagEntity(goodsId = "105", goodsType = "food", tagKey = "nutrient_high", scoreDelta = 2, description = "牛奶：优质钙源"),
        GoodsConsumptionTagEntity(goodsId = "106", goodsType = "food", tagKey = "nutrient_high", scoreDelta = 3, description = "豆腐：植物蛋白"),
        GoodsConsumptionTagEntity(goodsId = "107", goodsType = "food", tagKey = "natural_food", scoreDelta = 2, description = "番茄：天然蔬菜"),

        GoodsConsumptionTagEntity(goodsId = "200", goodsType = "food", tagKey = "junk_food", scoreDelta = -3, description = "薯片：高油高盐"),
        GoodsConsumptionTagEntity(goodsId = "201", goodsType = "food", tagKey = "luxury_premium", scoreDelta = -3, description = "网红奶茶：糖分炸弹"),
        GoodsConsumptionTagEntity(goodsId = "202", goodsType = "food", tagKey = "junk_food", scoreDelta = -2, description = "方便面：营养失衡"),

        GoodsConsumptionTagEntity(goodsId = "700", goodsType = "food", tagKey = "luxury_premium", scoreDelta = -4, description = "网红养生膏：智商税"),
        GoodsConsumptionTagEntity(goodsId = "701", goodsType = "food", tagKey = "marketing_gimmick", scoreDelta = -2, description = "0卡代糖：营销噱头"),
        GoodsConsumptionTagEntity(goodsId = "702", goodsType = "food", tagKey = "luxury_premium", scoreDelta = -4, description = "减肥酵素：智商税+有害"),
    )

    fun mindTexts(): List<MindTextLibEntity> = listOf(
        MindTextLibEntity(type = "night_mono", consumptionMin = 60, consumptionMax = 100, weight = 10, content = "今天吃得很舒服，肠胃暖暖的，这种踏实的感觉真好。"),
        MindTextLibEntity(type = "night_mono", consumptionMin = 60, consumptionMax = 100, weight = 10, content = "买的东西都是真正需要的，钱包和心里都很安稳。"),
        MindTextLibEntity(type = "night_mono", consumptionMin = 60, consumptionMax = 100, weight = 8, content = "衣服贴身舒服就好，不用给别人看的标签，自己知道舒服最重要。"),
        MindTextLibEntity(type = "night_mono", consumptionMin = 60, consumptionMax = 100, weight = 8, content = "家里的东西每一样都在用，没有积灰的摆设，空间清爽。"),

        MindTextLibEntity(type = "night_mono", consumptionMin = 0, consumptionMax = 40, weight = 10, content = "又买了一堆没用的东西，信用卡账单看着心慌，但当时就是忍不住..."),
        MindTextLibEntity(type = "night_mono", consumptionMin = 0, consumptionMax = 40, weight = 10, content = "穿大牌出门有面子，但鞋子磨脚、衣服勒得喘不过气，好累。"),
        MindTextLibEntity(type = "night_mono", consumptionMin = 0, consumptionMax = 40, weight = 8, content = "家里摆满了网红摆件，看着好看，但打扫起来真麻烦，好多都积灰了。"),
        MindTextLibEntity(type = "night_mono", consumptionMin = 0, consumptionMax = 40, weight = 8, content = "车贷压得喘不过气，但开出去别人会多看两眼，不知道值不值得。"),

        MindTextLibEntity(type = "night_mono", consumptionMin = 41, consumptionMax = 59, weight = 10, content = "今天有乱买的东西，也有真正需要的，就像日子一样，好坏都有吧。"),
        MindTextLibEntity(type = "night_mono", consumptionMin = 41, consumptionMax = 59, weight = 10, content = "有时候会为了面子消费，有时候又觉得没必要，人本来就是矛盾的。"),
        MindTextLibEntity(type = "night_mono", consumptionMin = 0, consumptionMax = 100, weight = 5, content = "今天就这样过去了，没有特别好，也没有特别坏。"),

        MindTextLibEntity(type = "sweet_dream", consumptionMin = 60, consumptionMax = 100, weight = 10, content = "梦里是小时候外婆家的厨房，热气腾腾的白粥配咸菜，简单却安心。"),
        MindTextLibEntity(type = "sweet_dream", consumptionMin = 60, consumptionMax = 100, weight = 8, content = "梦见自己穿着舒服的旧衣服，在阳光下散步，风一吹就很快乐。"),
        MindTextLibEntity(type = "sweet_dream", consumptionMin = 0, consumptionMax = 100, weight = 5, content = "梦里没有消费、没有比较，只是和喜欢的人安静坐着。"),

        MindTextLibEntity(type = "nightmare", consumptionMin = 0, consumptionMax = 40, weight = 10, content = "梦里全是还不完的账单，所有东西都在贬值，只有债务越变越多。"),
        MindTextLibEntity(type = "nightmare", consumptionMin = 0, consumptionMax = 40, weight = 8, content = "梦见穿着不合身的华丽衣服在人群中走，所有人都在看我，但我浑身不自在。"),

        MindTextLibEntity(type = "daily_thought", consumptionMin = 60, consumptionMax = 100, weight = 10, content = "东西够用就好，剩下的钱和时间，留给真正重要的人和事。"),
        MindTextLibEntity(type = "daily_thought", consumptionMin = 60, consumptionMax = 100, weight = 8, content = "别人穿什么买什么是别人的事，我的身体舒服不舒服，只有自己知道。"),
        MindTextLibEntity(type = "daily_thought", consumptionMin = 0, consumptionMax = 40, weight = 10, content = "下单的那一刻确实很快乐，但拆完快递就空虚了，这是怎么回事呢？"),
        MindTextLibEntity(type = "daily_thought", consumptionMin = 0, consumptionMax = 100, weight = 5, content = "没有什么东西是非买不可的，也没有什么选择是绝对正确的。"),
    )

    fun quotes(): List<QuoteEntity> = listOf(
        QuoteEntity(
            roleId = "doctor", sceneId = "daily",
            childContent = "多吃新鲜蔬菜水果，比什么保健品都强。",
            adultContent = "食材干净比宴席排场重要，肠胃舒服才长久。",
            consumptionMin = 60, consumptionMax = 100
        ),
        QuoteEntity(
            roleId = "vegetable_vendor", sceneId = "market",
            childContent = "阿姨自己种的菜，没打多少农药，吃着放心。",
            adultContent = "我卖了一辈子菜，看着新鲜、吃着对味的，就是好东西。",
            consumptionMin = 60, consumptionMax = 100
        ),
        QuoteEntity(
            roleId = "elderly", sceneId = "community",
            childContent = "衣服穿得暖、不磨皮肤就行，花那么多冤枉钱干啥。",
            adultContent = "我这衣服穿了十年了，舒服得很，新的反而硬邦邦磨得慌。",
            consumptionMin = 60, consumptionMax = 100
        ),

        QuoteEntity(
            roleId = "influencer_shop_clerk", sceneId = "mall",
            childContent = "小朋友穿这个名牌，同学都会羡慕你的！",
            adultContent = "姐，这款是限量款，背出去所有人都知道你有品位。",
            consumptionMin = 0, consumptionMax = 40
        ),
        QuoteEntity(
            roleId = "rich_kid", sceneId = "street",
            childContent = "你这衣服什么牌子啊？没见过，我妈说便宜货穿着不舒服。",
            adultContent = "车一定要买BBA入门款，不然谈生意别人看不起你。",
            consumptionMin = 0, consumptionMax = 40
        ),
        QuoteEntity(
            roleId = "salesman", sceneId = "home",
            childContent = "给孩子买这个进口学习桌，起跑线不能输！",
            adultContent = "姐，这套真皮沙发气派，亲戚朋友来家里有面子。",
            consumptionMin = 0, consumptionMax = 40
        ),

        QuoteEntity(
            roleId = "doctor", sceneId = "daily",
            childContent = "吃饭不挑食、睡觉按时，比啥补品都管用。",
            adultContent = "规律作息、均衡饮食，身体是自己的，别等出问题才后悔。",
            consumptionMin = 0, consumptionMax = 100
        ),
        QuoteEntity(
            roleId = "teacher", sceneId = "school",
            childContent = "小朋友，文具好用就行，不用跟同学比谁的贵。",
            adultContent = "真正的体面不是穿出来的，是你肚子里有多少东西。",
            consumptionMin = 0, consumptionMax = 100
        ),
    )
}
