package com.example.townapp.feature.town_simulation

object ValueDensityCalculator {

    fun calculateLifetimeValueDensity(
        functionalValue: Double,
        totalCost: Double,
        usefulLife: Double,
        age: Int,
        lifeExpectancy: Int = 85
    ): Double {
        val remainingLife = lifeExpectancy - age
        val effectiveUseTime = minOf(usefulLife, remainingLife.toDouble())
        
        val agingPenalty = if (age > 60) {
            1.0 + (age - 60) * 0.1
        } else {
            1.0
        }
        
        val totalLifetimeValue = functionalValue * effectiveUseTime * agingPenalty
        
        return if (totalCost == 0.0) 0.0 else totalLifetimeValue / totalCost
    }

    fun calculateRetirementSavings(
        currentAge: Int,
        retirementAge: Int = 65,
        monthlyIncome: Double,
        monthlyExpense: Double,
        expectedReturn: Double = 0.05
    ): Double {
        val yearsUntilRetirement = retirementAge - currentAge
        val monthlySavings = monthlyIncome - monthlyExpense
        val totalMonths = yearsUntilRetirement * 12
        
        var totalSavings = 0.0
        var currentSavings = 0.0
        
        for (month in 1..totalMonths) {
            currentSavings = (currentSavings + monthlySavings) * (1 + expectedReturn / 12)
            totalSavings = currentSavings
        }
        
        return totalSavings
    }

    fun calculateYearsUntilBankruptcy(
        currentAge: Int,
        currentSavings: Double,
        monthlyExpense: Double,
        monthlyIncome: Double = 0.0
    ): Int {
        val monthlyNet = monthlyIncome - monthlyExpense
        
        if (monthlyNet >= 0) {
            return Int.MAX_VALUE
        }
        
        val monthsUntilBankruptcy = currentSavings / Math.abs(monthlyNet)
        return currentAge + (monthsUntilBankruptcy / 12).toInt()
    }

    fun calculateAgingImpact(
        age: Int,
        decorationStyle: String
    ): Double {
        return when (decorationStyle) {
            "适老化日式" -> when {
                age < 60 -> 1.0
                age < 70 -> 2.5
                age < 80 -> 4.5
                else -> 6.0
            }
            "日式极简" -> when {
                age < 60 -> 1.0
                age < 70 -> 1.8
                age < 80 -> 2.5
                else -> 3.0
            }
            "现代简约" -> when {
                age < 60 -> 1.0
                age < 70 -> 0.8
                age < 80 -> 0.4
                else -> 0.1
            }
            "欧式豪华" -> when {
                age < 60 -> 1.0
                age < 70 -> 0.3
                age < 80 -> -0.5
                else -> -1.5
            }
            "网红ins风" -> when {
                age < 60 -> 1.0
                age < 70 -> -0.5
                age < 80 -> -2.0
                else -> -3.5
            }
            else -> 1.0
        }
    }

    fun getLifetimeValueDensityForDecoration(
        decorationStyle: String,
        age: Int
    ): Double {
        val baseDensity = when (decorationStyle) {
            "适老化日式" -> 10.8
            "日式极简" -> 10.8
            "现代简约" -> 3.9
            "北欧风" -> 2.6
            "新中式" -> 1.2
            "欧式豪华" -> 0.7
            "网红ins风" -> 0.2
            else -> 0.0
        }
        
        return baseDensity * calculateAgingImpact(age, decorationStyle)
    }

    fun suggestRetirementPlan(
        currentAge: Int,
        monthlyIncome: Double,
        monthlyExpense: Double,
        currentSavings: Double
    ): RetirementPlan {
        val targetRetirementAge = 65
        val yearsUntilRetirement = targetRetirementAge - currentAge
        
        val requiredMonthlySavings = if (yearsUntilRetirement > 0) {
            val targetSavings = 25 * 12 * monthlyExpense
            targetSavings / (yearsUntilRetirement * 12)
        } else {
            0.0
        }
        
        val currentMonthlySavings = monthlyIncome - monthlyExpense
        val savingsGap = requiredMonthlySavings - currentMonthlySavings
        
        val suggestions = mutableListOf<String>()
        if (savingsGap > 0) {
            suggestions.add("每月需要多存 ${savingsGap.toInt()} 元")
            suggestions.add("可以考虑减少不必要的消费")
            suggestions.add("可以考虑增加收入来源")
        } else {
            suggestions.add("你的储蓄计划良好")
            suggestions.add("继续保持当前的消费习惯")
        }
        
        return RetirementPlan(
            yearsUntilRetirement = yearsUntilRetirement,
            currentMonthlySavings = currentMonthlySavings,
            requiredMonthlySavings = requiredMonthlySavings,
            savingsGap = savingsGap,
            suggestions = suggestions
        )
    }

    data class RetirementPlan(
        val yearsUntilRetirement: Int,
        val currentMonthlySavings: Double,
        val requiredMonthlySavings: Double,
        val savingsGap: Double,
        val suggestions: List<String>
    )

    // ============================================================
    // 行为驱动的代价计算 —— 对齐 BehaviorBuildingMapping
    // ============================================================

    /**
     * 单次行为的代价计算结果。
     * @param density 本次行为的量化数值（可正可负）
     * @param style 对应的建筑风格
     * @param module 所属模块（食物/服装/住房/认知）
     * @param buildingId 对应的 TownRepository 建筑 id
     * @param tip 给用户的一句话提示
     */
    data class BehaviorDensityResult(
        val density: Double,
        val style: BuildingStyle,
        val module: String,
        val buildingId: String,
        val tip: String
    )

    /**
     * 计算一次行为输入的量化数值。
     *
     * @param type 大类（food / clothing / housing / cognitive），大小写不敏感
     * @param subType 具体行为 key，与 BehaviorBuildingMapping.behaviorKey 对齐
     * @param amount 本次行为的"数量"（金额或小时数）；正数即可，正负由 mapping 内部决定
     * @return 量化数值 + 建筑风格；如果没有找到对应的映射，返回 null。
     */
    fun calculateBehaviorDensity(
        type: String,
        subType: String,
        amount: Double
    ): BehaviorDensityResult? {
        val mapping = BehaviorBuildingMapping.findByBehavior(type, subType) ?: return null
        val density = mapping.valueDensityPerUnit * amount.coerceAtLeast(0.0)
        val style = BuildingStyleClassifier.classify(density)
        return BehaviorDensityResult(
            density = density,
            style = style,
            module = mapping.module,
            buildingId = mapping.buildingId,
            tip = mapping.cognitiveTip
        )
    }

    /**
     * 统计一段历史行为的"平均数值"——用于小镇顶部状态栏和趋势图。
     *
     * 输入格式：每次行为的 (type, subType, amount) 三元组。
     * 输出：按日期聚合后的日均数值，方便 UI 绘制 7 天 / 30 天 / 1 年折线。
     */
    data class DailyDensitySnapshot(
        val dateLabel: String,      // YYYY-MM-DD
        val averageDensity: Double, // 当天所有行为的数值平均值
        val totalActions: Int,      // 当天行为条数
        val style: BuildingStyle
    )

    /**
     * 把一组带日期的行为聚合成"日均数值"时间线。
     *
     * @param records 每条行为记录 (dateLabel, type, subType, amount)
     * @param emptyIfMissing 当某一天没有任何行为时，是否用 0 填充。默认 false，避免人为拉低曲线。
     */
    fun aggregateDailyDensity(
        records: List<Tuple4<String, String, String, Double>>,
        emptyIfMissing: Boolean = false
    ): List<DailyDensitySnapshot> {
        val byDate = records.groupBy { it.first }
        return byDate.map { (date, dayRecords) ->
            val densities = dayRecords.mapNotNull {
                calculateBehaviorDensity(it.second, it.third, it.fourth)?.density
            }
            val avg = if (densities.isEmpty()) 0.0 else densities.average()
            DailyDensitySnapshot(
                dateLabel = date,
                averageDensity = avg,
                totalActions = densities.size,
                style = BuildingStyleClassifier.classify(avg)
            )
        }.sortedBy { it.dateLabel }
    }

    /**
     * 给调用方用的简易四元组，避免引入 kotlin-pair 嵌套。
     */
    data class Tuple4<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

    /**
     * 计算「过去 N 天平均数值」与「上一个 N 天」的对比，
     * 用于 UI 底部的趋势总结文案。
     */
    data class TrendSummary(
        val currentAverage: Double,
        val previousAverage: Double,
        val percentChange: Double,
        val style: BuildingStyle,
        val summaryText: String
    )

    fun summarizeTrend(currentSnaps: List<DailyDensitySnapshot>, previousSnaps: List<DailyDensitySnapshot>): TrendSummary {
        val currentAvg = if (currentSnaps.isEmpty()) 0.0 else currentSnaps.map { it.averageDensity }.average()
        val prevAvg = if (previousSnaps.isEmpty()) 0.0 else previousSnaps.map { it.averageDensity }.average()
        val change = if (prevAvg == 0.0) 0.0 else (currentAvg - prevAvg) / kotlin.math.abs(prevAvg) * 100.0
        val style = BuildingStyleClassifier.classify(currentAvg)
        val hint = when {
            change > 15 -> "比上周提升了 ${change.toInt()}%，继续保持！"
            change > 0 -> "比上周提升了 ${change.toInt()}%，不错。"
            change > -10 -> "比上周变化不大，保持关注。"
            else -> "比上周下降了 ${change.toInt()}%，是时候盘点一下了。"
        }
        return TrendSummary(
            currentAverage = currentAvg,
            previousAverage = prevAvg,
            percentChange = change,
            style = style,
            summaryText = "你本周的平均值为 ${"%.1f".format(currentAvg)}，$hint"
        )
    }
}