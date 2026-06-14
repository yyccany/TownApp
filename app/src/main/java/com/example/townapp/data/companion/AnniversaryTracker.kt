package com.example.townapp.data.companion

import com.example.townapp.business.StructuredLogger
import com.example.townapp.core.constants.SimulationConstants
import com.example.townapp.security.AuditLogger
import java.text.SimpleDateFormat
import java.util.*

/**
 * 💛 小镇周年纪念日系统（纯内存实现）
 *
 * 记录用户本次会话第一次打开APP的时间
 * 每年同一天，触发周年纪念日场景
 *
 * 注意：全程内存运行，关闭 App 后记录自动清空
 */
class AnniversaryTracker {

    companion object {
        private const val TAG = "AnniversaryTracker"
    }

    /** 本次会话首次启动时间（内存存储） */
    private var firstLaunchTime: Long = 0L

    /** 本次会话已庆祝的周年年份（内存存储） */
    private var celebratedYear: Int = 0

    /**
     * 获取第一次打开APP的时间
     * 如果不存在则记录当前时间
     */
    fun getFirstLaunchTime(): Long {
        if (firstLaunchTime == 0L) {
            firstLaunchTime = System.currentTimeMillis()
            StructuredLogger.i(TAG, "记录本次会话首次启动时间: ${formatDate(firstLaunchTime)}")
        }
        return firstLaunchTime
    }

    /**
     * 检查今天是否是周年纪念日
     */
    fun isAnniversaryToday(): Boolean {
        val firstLaunch = getFirstLaunchTime()
        if (firstLaunch == 0L) return false

        val firstCalendar = Calendar.getInstance().apply { timeInMillis = firstLaunch }
        val today = Calendar.getInstance()

        // 必须满足：不是同一天（首次启动当天不算周年）
        val isSameYear = firstCalendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
        if (isSameYear) return false

        // 必须满足：同月同日
        val isSameDay = firstCalendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                firstCalendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)

        return isSameDay
    }

    /**
     * 计算已经认识了多少天
     */
    fun getDaysTogether(): Int {
        val firstLaunch = getFirstLaunchTime()
        if (firstLaunch == 0L) return 0

        val diff = System.currentTimeMillis() - firstLaunch
        return (diff / SimulationConstants.MILLIS_PER_DAY).toInt()
    }

    /**
     * 计算已经认识了多少年
     */
    fun getYearsTogether(): Int {
        val firstLaunch = getFirstLaunchTime()
        if (firstLaunch == 0L) return 0

        val firstCalendar = Calendar.getInstance().apply { timeInMillis = firstLaunch }
        val today = Calendar.getInstance()

        var years = today.get(Calendar.YEAR) - firstCalendar.get(Calendar.YEAR)

        // 如果今年的纪念日还没到，少算一年
        val todayMonth = today.get(Calendar.MONTH)
        val todayDay = today.get(Calendar.DAY_OF_MONTH)
        val firstMonth = firstCalendar.get(Calendar.MONTH)
        val firstDay = firstCalendar.get(Calendar.DAY_OF_MONTH)

        if (todayMonth < firstMonth || (todayMonth == firstMonth && todayDay < firstDay)) {
            years -= 1
        }

        return maxOf(0, years)
    }

    /**
     * 获取纪念日信息
     */
    fun getAnniversaryInfo(): AnniversaryInfo {
        return AnniversaryInfo(
            firstLaunchTime = getFirstLaunchTime(),
            daysTogether = getDaysTogether(),
            yearsTogether = getYearsTogether(),
            isAnniversaryToday = isAnniversaryToday()
        )
    }

    /**
     * 标记今年的纪念日已经庆祝过
     */
    fun markAnniversaryCelebrated() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        celebratedYear = currentYear
        AuditLogger.logAction(
            AuditLogger.ActionType.APP_START,
            AuditLogger.Severity.INFO,
            "周年纪念日庆祝",
            additionalInfo = mapOf("year" to currentYear.toString())
        )
    }

    /**
     * 检查今年是否已经庆祝过
     */
    fun hasCelebratedThisYear(): Boolean {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        return currentYear == celebratedYear
    }

    private fun formatDate(timestamp: Long): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        return format.format(Date(timestamp))
    }

    data class AnniversaryInfo(
        val firstLaunchTime: Long,
        val daysTogether: Int,
        val yearsTogether: Int,
        val isAnniversaryToday: Boolean
    )
}
