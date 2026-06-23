package com.example.townapp.feature.town_simulation

object AestheticBusiness {

    enum class AestheticLevel(val level: Int, val displayName: String, val description: String) {
        WHITE(0, "审美小白", "只能看到物品的价格"),
        BEGINNER(1, "审美入门", "能看到物品的价值密度"),
        INTERMEDIATE(2, "审美进阶", "能看到物品的使用寿命"),
        MASTER(3, "审美大师", "能看到物品的全生命周期成本"),
        AWAKENED(4, "审美觉醒", "能自动识别低价值密度的物品")
    }

    fun calculateAestheticLevel(
        detoxCompleted: Boolean,
        dailyDeleteCount: Int,
        weeklyReviewCount: Int,
        principlesMastered: Int
    ): AestheticLevel {
        return when {
            principlesMastered >= 4 -> AestheticLevel.AWAKENED
            weeklyReviewCount >= 50 -> AestheticLevel.MASTER
            dailyDeleteCount >= 100 -> AestheticLevel.INTERMEDIATE
            detoxCompleted -> AestheticLevel.BEGINNER
            else -> AestheticLevel.WHITE
        }
    }

    fun calculateAestheticExperience(
        dailyDeleteCount: Int,
        weeklyReviewCount: Int,
        comparisonCount: Int,
        observationCount: Int
    ): Int {
        return dailyDeleteCount * 10 +
               weeklyReviewCount * 50 +
               comparisonCount * 20 +
               observationCount * 5
    }

    fun calculateValueDensity(
        functionalValue: Double,
        monetaryCost: Double,
        healthCost: Double,
        timeCost: Double,
        usefulLifeYears: Double
    ): Double {
        val totalCost = monetaryCost + healthCost + timeCost
        val totalValue = functionalValue * usefulLifeYears
        
        return if (totalCost == 0.0) 0.0 else totalValue / totalCost
    }

    fun compareItems(item1: AestheticItem, item2: AestheticItem): ComparisonResult {
        val density1 = calculateValueDensity(
            item1.functionalValue,
            item1.price,
            item1.healthCost,
            item1.timeCost,
            item1.usefulLifeYears
        )
        val density2 = calculateValueDensity(
            item2.functionalValue,
            item2.price,
            item2.healthCost,
            item2.timeCost,
            item2.usefulLifeYears
        )

        val betterItem = if (density1 > density2) item1 else item2
        val multiple = if (minOf(density1, density2) == 0.0) Double.MAX_VALUE 
                       else maxOf(density1, density2) / minOf(density1, density2)

        return ComparisonResult(
            item1 = item1,
            item2 = item2,
            density1 = density1,
            density2 = density2,
            betterItem = betterItem,
            betterItemName = betterItem.name,
            multiple = multiple
        )
    }

    fun getDailyTask(): DailyAestheticTask {
        val tasks = listOf(
            DailyAestheticTask(
                id = "delete",
                title = "每日一删",
                description = "今天扔掉一样家里多余的、没用的、不好看的东西",
                rewardExp = 10,
                rewardAwakening = 5
            ),
            DailyAestheticTask(
                id = "observe",
                title = "审美观察",
                description = "观察你家的一个细节，比如门把手、水龙头、开关，看看它好不好用、好不好看",
                rewardExp = 5,
                rewardAwakening = 3
            ),
            DailyAestheticTask(
                id = "compare",
                title = "审美对比",
                description = "找两个同类物品对比，哪个更好看、更好用",
                rewardExp = 20,
                rewardAwakening = 10
            ),
            DailyAestheticTask(
                id = "learn",
                title = "审美学习",
                description = "看一个10分钟的设计短片或纪录片",
                rewardExp = 15,
                rewardAwakening = 8
            )
        )
        return tasks.random()
    }

    fun checkDetoxCompletion(detoxDays: Int): Boolean {
        return detoxDays >= 7
    }

    data class AestheticItem(
        val name: String,
        val price: Double,
        val functionalValue: Double,
        val healthCost: Double = 0.0,
        val timeCost: Double = 0.0,
        val usefulLifeYears: Double = 1.0,
        val description: String = ""
    )

    data class ComparisonResult(
        val item1: AestheticItem,
        val item2: AestheticItem,
        val density1: Double,
        val density2: Double,
        val betterItem: AestheticItem,
        val betterItemName: String,
        val multiple: Double
    )

    data class DailyAestheticTask(
        val id: String,
        val title: String,
        val description: String,
        val rewardExp: Int,
        val rewardAwakening: Int
    )

    val aestheticLibrary = listOf(
        AestheticItem(
            name = "尿素维E乳",
            price = 10.0,
            functionalValue = 100.0,
            usefulLifeYears = 1.0,
            description = "10块钱的护肤品，保湿效果比3000块的海蓝之谜还好"
        ),
        AestheticItem(
            name = "帆布鞋",
            price = 200.0,
            functionalValue = 90.0,
            usefulLifeYears = 2.0,
            description = "舒适、百搭、耐用，比高跟鞋美189倍"
        ),
        AestheticItem(
            name = "日式极简装修",
            price = 150000.0,
            functionalValue = 100.0,
            usefulLifeYears = 20.0,
            description = "价值密度是欧式豪华的15倍"
        ),
        AestheticItem(
            name = "优衣库基础款",
            price = 199.0,
            functionalValue = 85.0,
            usefulLifeYears = 3.0,
            description = "百搭、舒适、耐用，永远不会过时"
        ),
        AestheticItem(
            name = "无印良品收纳",
            price = 50.0,
            functionalValue = 95.0,
            usefulLifeYears = 5.0,
            description = "简洁、实用、统一，让家里变得整洁"
        )
    )
}