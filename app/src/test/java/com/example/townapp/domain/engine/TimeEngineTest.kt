package com.example.townapp.domain.engine

import com.example.townapp.domain.TimeEngine
import com.example.townapp.domain.util.TimeState
import org.junit.Assert.*
import org.junit.Test

/**
 * TimeEngine 单元测试
 *
 * 验证时间推进规则：小时递进、跨天、跨周、跨月、跨年、年龄增长。
 */
class TimeEngineTest {

    private val baseState = TimeState(
        hour = 8,
        day = 1,
        weekDay = 1,
        weekOfMonth = 1,
        month = 1,
        year = 1,
        totalDays = 1,
        totalWeeks = 0,
        age = 20,
        season = com.example.townapp.data.Season.SPRING
    )

    @Test
    fun `advanceHour increments hour within same day`() {
        val result = TimeEngine.advanceHour(baseState.copy(hour = 10))

        assertFalse(result.isNewDay)
        assertEquals(11, result.newState.hour)
        assertEquals(1, result.newState.day)
    }

    @Test
    fun `advanceHour at 23 triggers new day`() {
        val result = TimeEngine.advanceHour(baseState.copy(hour = 23))

        assertTrue(result.isNewDay)
        assertEquals(0, result.newState.hour)
        assertEquals(2, result.newState.day)
        assertEquals(2, result.newState.totalDays)
    }

    @Test
    fun `advanceHour triggers new week on Monday`() {
        // 周日23点 -> 周一0点，新的一周
        val state = baseState.copy(hour = 23, day = 7, weekDay = 7, totalDays = 7)
        val result = TimeEngine.advanceHour(state)

        assertTrue(result.isNewDay)
        assertTrue(result.isNewWeek)
        assertEquals(1, result.newState.weekDay)
    }

    @Test
    fun `advanceHour triggers new month on day 30`() {
        val state = baseState.copy(
            hour = 23, day = 30, weekDay = 2, weekOfMonth = 5,
            month = 1, totalDays = 30
        )
        val result = TimeEngine.advanceHour(state)

        assertTrue(result.isNewDay)
        assertTrue(result.isNewMonth)
        assertEquals(1, result.newState.day)
        assertEquals(1, result.newState.weekOfMonth)
        assertEquals(2, result.newState.month)
    }

    @Test
    fun `advanceHour triggers new year on month 12 day 30`() {
        val state = baseState.copy(
            hour = 23, day = 30, month = 12, weekDay = 3,
            weekOfMonth = 5, year = 1, totalDays = 360
        )
        val result = TimeEngine.advanceHour(state)

        assertTrue(result.isNewDay)
        assertTrue(result.isNewMonth)
        assertTrue(result.isNewYear)
        assertEquals(1, result.newState.month)
        assertEquals(2, result.newState.year)
    }

    @Test
    fun `advanceHour triggers season change in transition months`() {
        // 2月30日 -> 3月1日，触发换季
        val state = baseState.copy(
            hour = 23, day = 30, month = 2, weekDay = 4,
            weekOfMonth = 5, totalDays = 60
        )
        val result = TimeEngine.advanceHour(state)

        assertTrue(result.isNewMonth)
        assertTrue(result.isSeasonChange)
        assertEquals(3, result.newState.month)
    }

    @Test
    fun `advanceHour triggers age up every 360 days`() {
        // day=30, 推进后 newDay=31 > 30 触发月切换
        // totalDays=359, 推进后变为 360，满足 360 % 360 == 0
        val state = baseState.copy(
            hour = 23, day = 30, month = 12, weekDay = 5,
            weekOfMonth = 5, year = 1, totalDays = 359, age = 20
        )
        val result = TimeEngine.advanceHour(state)

        assertTrue(result.isNewMonth)
        assertTrue(result.isAgeUp)
        assertEquals(21, result.newState.age)
    }

    @Test
    fun `advanceDay directly increments day and triggers flags`() {
        val state = baseState.copy(day = 29, weekDay = 5, weekOfMonth = 5, totalDays = 29)
        val result = TimeEngine.advanceDay(state)

        assertTrue(result.isNewDay)
        assertEquals(30, result.newState.day)
        assertEquals(6, result.newState.weekDay)
    }
}
