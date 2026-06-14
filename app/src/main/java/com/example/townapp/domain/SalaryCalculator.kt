package com.example.townapp.domain

import com.example.townapp.data.GlobalSettings

object SalaryCalculator {

    enum class TimeUnit {
        MINUTE, HOUR, WORKDAY
    }

    data class HourlyResult(
        val baseHourly: Double,
        val advancedHourly: Double,
        val displayValue: Double,
        val displayUnit: TimeUnit,
        val displayText: String
    )

    fun calculateHourlySalary(settings: GlobalSettings): HourlyResult {
        val monthlyIncome = settings.monthlyIncome
        val workDays = settings.workDaysPerMonth
        val workHours = settings.workHoursPerDay
        val commuteHours = settings.commuteHoursPerDay
        val overtimeHours = settings.overtimeHoursPerDay

        val baseHourly = monthlyIncome / (workDays * workHours)
        val advancedHourly = monthlyIncome / (workDays * (workHours + commuteHours + overtimeHours))

        val targetValue = if (settings.advancedMode) advancedHourly else baseHourly

        val (displayValue, unit) = when {
            targetValue < 60 -> {
                val perMinute = targetValue / 60
                if (perMinute < 1) {
                    (perMinute * 100 to TimeUnit.MINUTE)
                } else {
                    (perMinute to TimeUnit.MINUTE)
                }
            }
            targetValue >= 60 * 8 -> {
                (targetValue / (workHours) to TimeUnit.WORKDAY)
            }
            else -> {
                (targetValue to TimeUnit.HOUR)
            }
        }

        val unitText = when (unit) {
            TimeUnit.MINUTE -> "分/分钟"
            TimeUnit.HOUR -> "元/小时"
            TimeUnit.WORKDAY -> "元/工作日"
        }

        return HourlyResult(
            baseHourly = baseHourly,
            advancedHourly = advancedHourly,
            displayValue = displayValue,
            displayUnit = unit,
            displayText = "¥${String.format("%.1f", displayValue)}/$unitText"
        )
    }

    fun calculateCostInTime(hourlyRate: Double, cost: Double): Pair<Double, TimeUnit> {
        val hours = cost / hourlyRate
        return when {
            hours < 1 -> {
                val minutes = hours * 60
                if (minutes < 1) {
                    (minutes * 60 to TimeUnit.MINUTE)
                } else {
                    (minutes to TimeUnit.MINUTE)
                }
            }
            hours >= 8 -> {
                (hours / 8 to TimeUnit.WORKDAY)
            }
            else -> {
                (hours to TimeUnit.HOUR)
            }
        }
    }

    fun getTimeUnitText(unit: TimeUnit): String {
        return when (unit) {
            TimeUnit.MINUTE -> "分钟"
            TimeUnit.HOUR -> "小时"
            TimeUnit.WORKDAY -> "工作日"
        }
    }

    /**
     * 计算薪俸成本（直接返回价格，用于简化计算）
     * 在更复杂的实现中，可以根据用户的时薪计算时间成本
     */
    fun calculateSalaryPrice(price: Double): Double {
        return price
    }
}
