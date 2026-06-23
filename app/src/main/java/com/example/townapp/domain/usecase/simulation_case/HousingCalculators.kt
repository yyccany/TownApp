package com.example.townapp.domain

import com.example.townapp.data.HousingItem
import com.example.townapp.data.HouseholdItem

// ============================================
// 🏠 住房成本计算器
// ============================================

object HousingCostCalculator {
    /**
     * 计算月度总支出
     */
    fun calculateMonthlyTotal(housing: HousingItem): Double {
        return housing.monthlyRent + housing.propertyFee + housing.utilityFee
    }

    /**
     * 计算每日成本
     */
    fun calculateDailyCost(housing: HousingItem): Double {
        return calculateMonthlyTotal(housing) / 30.0
    }

    /**
     * 计算每小时成本
     */
    fun calculateHourlyCost(housing: HousingItem): Double {
        return calculateDailyCost(housing) / 24.0
    }

    /**
     * 计算人均居住面积
     */
    fun calculateSpacePerPerson(housing: HousingItem): Double {
        return if (housing.residents > 0) housing.houseSize / housing.residents else 0.0
    }

    /**
     * 计算平米单价
     */
    fun calculateCostPerSquareMeter(housing: HousingItem): Double {
        return if (housing.houseSize > 0) calculateMonthlyTotal(housing) / housing.houseSize else 0.0
    }

    /**
     * 计算月度固定成本（不含家具折旧）
     */
    fun calculateMonthlyFixedCost(housing: HousingItem): Double {
        return housing.monthlyRent + housing.propertyFee + housing.utilityFee
    }

    /**
     * 计算家具家电月度折旧
     */
    fun calculateMonthlyDepreciation(housing: HousingItem): Double {
        var totalDepreciation = 0.0
        for (item in housing.items) {
            val yearlyDepreciation = item.price / item.lifespanYears
            totalDepreciation += yearlyDepreciation / 12.0
        }
        return totalDepreciation
    }

    /**
     * 获取家庭智商税评级
     */
    fun getHouseholdScamWarning(item: HouseholdItem): String {
        // 简单的智商税判断逻辑
        val priceToLifespanRatio = item.price / item.lifespanYears
        return when {
            priceToLifespanRatio > 1000 -> "智商税重灾区！花大价钱买了个不耐用的东西"
            priceToLifespanRatio > 500 -> "有点小贵，考虑一下是否真的需要"
            else -> "还可以，属于正常消费"
        }
    }
}

// ============================================
// ⭐ 住房评级计算器
// ============================================

object HousingRatingCalculator {
    /**
     * 成本评级（返回等级文本 + 分数0-5）
     */
    fun getCostRating(monthlyCost: Double): Pair<String, Double> {
        return when {
            monthlyCost < 2000 -> "非常便宜" to 5.0
            monthlyCost < 4000 -> "比较便宜" to 4.0
            monthlyCost < 6000 -> "中等水平" to 3.0
            monthlyCost < 8000 -> "有点小贵" to 2.0
            monthlyCost < 12000 -> "比较昂贵" to 1.0
            else -> "非常昂贵" to 0.5
        }
    }

    /**
     * 空间评级（返回等级文本 + 分数0-5）
     */
    fun getSpaceRating(spacePerPerson: Double): Pair<String, Double> {
        return when {
            spacePerPerson >= 40 -> "宽敞舒适" to 5.0
            spacePerPerson >= 25 -> "比较宽敞" to 4.0
            spacePerPerson >= 15 -> "中等水平" to 3.0
            spacePerPerson >= 10 -> "有点拥挤" to 2.0
            else -> "非常拥挤" to 1.0
        }
    }

    /**
     * 性价比综合评分 0-100
     */
    fun getOverallScore(housing: HousingItem): Double {
        val costScore = when {
            housing.monthlyRent < 2000 -> 100.0
            housing.monthlyRent < 4000 -> 80.0
            housing.monthlyRent < 6000 -> 60.0
            housing.monthlyRent < 8000 -> 40.0
            else -> 20.0
        }

        val spaceScore = (HousingCostCalculator.calculateSpacePerPerson(housing) / 40.0).coerceAtMost(1.0) * 100

        return costScore * 0.6 + spaceScore * 0.4
    }
}
