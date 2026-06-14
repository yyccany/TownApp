package com.example.townapp.data.repository

import app.cash.turbine.test
import com.example.townapp.domain.engine.GameTime
import com.example.townapp.domain.engine.PlayerState
import com.example.townapp.domain.spacemodel.SpaceState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * SimulationRepository 单元测试
 *
 * 验证 StateFlow 初始值、commit/update 状态变更、时间推进逻辑。
 */
class SimulationRepositoryTest {

    @Before
    fun setUp() {
        SimulationRepository.fullReset()
    }

    @Test
    fun `initial state has default values`() = runTest {
        assertEquals(1, SimulationRepository.gameTimeFlow.value.days)
        assertEquals(8, SimulationRepository.gameTimeFlow.value.hours)
        assertEquals(80.0, SimulationRepository.playerStateFlow.value.hunger, 0.001)
        assertEquals(1500.0, SimulationRepository.spaceStateFlow.value.rent, 0.001)
    }

    @Test
    fun `commitGameTime updates gameTimeFlow`() = runTest {
        val newTime = GameTime(days = 5, hours = 14)

        SimulationRepository.gameTimeFlow.test {
            awaitItem() // 初始值
            SimulationRepository.commitGameTime(newTime)
            val emitted = awaitItem()
            assertEquals(5, emitted.days)
            assertEquals(14, emitted.hours)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `commitPlayerState updates playerStateFlow`() = runTest {
        val newState = PlayerState(hunger = 50.0, energy = 40.0)

        SimulationRepository.playerStateFlow.test {
            awaitItem()
            SimulationRepository.commitPlayerState(newState)
            val emitted = awaitItem()
            assertEquals(50.0, emitted.hunger, 0.001)
            assertEquals(40.0, emitted.energy, 0.001)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `commitSpaceState updates spaceStateFlow`() = runTest {
        val newState = SpaceState(rent = 3000.0, salary = 9000.0)

        SimulationRepository.spaceStateFlow.test {
            awaitItem()
            SimulationRepository.commitSpaceState(newState)
            val emitted = awaitItem()
            assertEquals(3000.0, emitted.rent, 0.001)
            assertEquals(9000.0, emitted.salary, 0.001)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updatePlayerState transforms current state`() = runTest {
        SimulationRepository.playerStateFlow.test {
            awaitItem()
            SimulationRepository.updatePlayerState { it.copy(hunger = it.hunger - 10.0) }
            val emitted = awaitItem()
            assertEquals(70.0, emitted.hunger, 0.001)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `advanceTime handles hour overflow and day increment`() {
        SimulationRepository.commitGameTime(GameTime(days = 1, hours = 20))

        SimulationRepository.advanceTime(8)

        val time = SimulationRepository.gameTimeFlow.value
        assertEquals(4, time.hours)
        assertEquals(2, time.days)
    }

    @Test
    fun `setSeason updates season`() {
        SimulationRepository.setSeason("winter")

        assertEquals("winter", SimulationRepository.gameTimeFlow.value.season)
    }

    @Test
    fun `fullReset restores all flows to default`() {
        SimulationRepository.commitGameTime(GameTime(days = 100, hours = 23))
        SimulationRepository.commitPlayerState(PlayerState(age = 30, money = 9999.0))
        SimulationRepository.commitSpaceState(SpaceState(rent = 9999.0))

        SimulationRepository.fullReset()

        assertEquals(1, SimulationRepository.gameTimeFlow.value.days)
        assertEquals(8, SimulationRepository.gameTimeFlow.value.hours)
        assertEquals(80.0, SimulationRepository.playerStateFlow.value.hunger, 0.001)
        assertEquals(1500.0, SimulationRepository.spaceStateFlow.value.rent, 0.001)
    }
}
