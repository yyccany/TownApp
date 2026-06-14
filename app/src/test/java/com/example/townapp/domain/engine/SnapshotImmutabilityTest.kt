package com.example.townapp.domain.engine

import org.junit.Assert.*
import org.junit.Test

/**
 * 数据模型不可变快照测试
 *
 * 验证 PlayerState / GameTime 等数据类的 copy 行为与默认值正确性。
 */
class SnapshotImmutabilityTest {

    @Test
    fun `PlayerState copy creates new instance without mutating original`() {
        val original = PlayerState(hunger = 80.0, energy = 70.0)
        val modified = original.copy(hunger = 60.0)

        assertEquals(80.0, original.hunger, 0.001)
        assertEquals(60.0, modified.hunger, 0.001)
        assertEquals(70.0, modified.energy, 0.001)
    }

    @Test
    fun `PlayerState dailyReset clears daily accumulators`() {
        val state = PlayerState(
            dailyMoneyChange = 100.0,
            dailyMaxHunger = 90.0,
            dailyMinEnergy = 50.0,
            dailyAvgHappiness = 70.0,
            dailyEvents = listOf("event1")
        )
        val reset = state.dailyReset()

        assertEquals(0.0, reset.dailyMoneyChange, 0.001)
        assertEquals(state.hunger, reset.dailyMaxHunger, 0.001)
        assertEquals(state.energy, reset.dailyMinEnergy, 0.001)
        assertEquals(state.happiness, reset.dailyAvgHappiness, 0.001)
        assertTrue(reset.dailyEvents.isEmpty())
    }

    @Test
    fun `GameTime copy preserves unmodified fields`() {
        val original = GameTime(days = 5, hours = 12, week = 1, dayOfWeek = 5, season = "summer")
        val modified = original.copy(hours = 18)

        assertEquals(5, modified.days)
        assertEquals(18, modified.hours)
        assertEquals("summer", modified.season)
    }

    @Test
    fun `GameTime getDayOfWeekName returns correct names`() {
        assertEquals("周一", GameTime(dayOfWeek = 1).getDayOfWeekName())
        assertEquals("周日", GameTime(dayOfWeek = 7).getDayOfWeekName())
        assertEquals("周一", GameTime(dayOfWeek = 99).getDayOfWeekName())
    }

    @Test
    fun `StateSnapshot preserves all nested values`() {
        val time = GameTime(days = 10, hours = 14)
        val player = PlayerState(age = 25, money = 3000.0)
        val space = com.example.townapp.domain.spacemodel.SpaceState(rent = 2000.0)

        val snapshot = StateSnapshot(time, player, space)

        assertEquals(10, snapshot.gameTime.days)
        assertEquals(25, snapshot.playerState.age)
        assertEquals(2000.0, snapshot.spaceState.rent, 0.001)
    }
}
