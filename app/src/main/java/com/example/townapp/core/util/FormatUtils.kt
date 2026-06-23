package com.example.townapp.core.util

import java.util.Locale

object FormatUtils {

    private val EN_US = Locale.US

    fun format(format: String, vararg args: Any): String {
        return String.format(EN_US, format, *args)
    }

    fun formatMoney(amount: Double): String {
        return String.format(EN_US, "%.2f", amount)
    }

    fun formatMoneyInt(amount: Double): String {
        return String.format(EN_US, "%.0f", amount)
    }

    fun formatHours(hours: Double): String {
        return String.format(EN_US, "%.1f", hours)
    }

    fun formatHoursInt(hours: Double): String {
        return String.format(EN_US, "%.0f", hours)
    }

    fun formatPercent(value: Double): String {
        return String.format(EN_US, "%.0f", value)
    }

    fun formatPercentDecimal(value: Double): String {
        return String.format(EN_US, "%.2f", value)
    }

    fun formatInt(value: Int): String {
        return String.format(EN_US, "%d", value)
    }

    fun formatDouble(value: Double): String {
        return String.format(EN_US, "%.2f", value)
    }

    fun formatDouble1(value: Double): String {
        return String.format(EN_US, "%.1f", value)
    }

    fun formatDouble0(value: Double): String {
        return String.format(EN_US, "%.0f", value)
    }
}