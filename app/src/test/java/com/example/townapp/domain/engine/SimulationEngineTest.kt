package com.example.townapp.domain.engine

import com.example.townapp.domain.spacemodel.SpaceState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * SimulationEngine 单元测试
 *
 * 验证状态快照、重置、空间更新、基础行为等核心逻辑。
 * 注意：SimulationEngine 为单例，每个测试前调用 [reset] 隔离状态。
 */
class SimulationEngineTest {

    @Before
    fun setUp() {
        SimulationEngine.reset()
    }

    @Test
    fun `reset restores default state values`() {
        val snapshot = SimulationEngine.getStateSnapshot()

        assertEquals(1, snapshot.gameTime.days)
        assertEquals(8, snapshot.gameTime.hours)
        // 默认职业（公务员）minAge=22，所以重置后 age 为 22
        assertEquals(22, snapshot.playerState.age)
        assertEquals(80.0, snapshot.playerState.hunger, 0.001)
        assertTrue(snapshot.playerState.energy >= 60.0)
        assertFalse(snapshot.playerState.hasFamily)
    }

    @Test
    fun `getStateSnapshot returns consistent data`() {
        val snapshot1 = SimulationEngine.getStateSnapshot()
        val snapshot2 = SimulationEngine.getStateSnapshot()

        assertEquals(snapshot1, snapshot2)
    }

    @Test
    fun `updateSpaceState modifies rent salary and commute`() {
        SimulationEngine.updateSpaceState(2500.0, 8000.0, 45)

        val snapshot = SimulationEngine.getStateSnapshot()
        assertEquals(2500.0, snapshot.spaceState.rent, 0.001)
        assertEquals(8000.0, snapshot.spaceState.salary, 0.001)
        assertEquals(45, snapshot.spaceState.commuteMinutesOneWay)
    }

    @Test
    fun `stop does not throw exception`() {
        SimulationEngine.stop()
        val snapshot = SimulationEngine.getStateSnapshot()
        assertNotNull(snapshot)
    }

    @Test
    fun `eat returns success result`() {
        val result = SimulationEngine.eat(1)

        assertTrue(result.success)
        assertTrue(result.message.isNotEmpty())
    }

    @Test
    fun `sleep restores energy`() {
        SimulationEngine.reset()
        val before = SimulationEngine.getStateSnapshot().playerState.energy

        val result = SimulationEngine.sleep(8)

        val after = SimulationEngine.getStateSnapshot().playerState.energy
        assertTrue(result.success)
        assertTrue(after >= before)
    }

    @Test
    fun `work changes money`() {
        SimulationEngine.reset()
        val before = SimulationEngine.getStateSnapshot().playerState.money

        val result = SimulationEngine.work(8)

        val after = SimulationEngine.getStateSnapshot().playerState.money
        assertTrue(result.success)
        // 工作应产生收入，金额应变化（增加或减少取决于内部逻辑）
        assertNotEquals(before, after, 0.001)
    }

    @Test
    fun `fastForward advances time by specified days`() = runTest {
        SimulationEngine.reset()
        val beforeDays = SimulationEngine.getStateSnapshot().gameTime.days

        val result = SimulationEngine.fastForward(5)

        val afterDays = SimulationEngine.getStateSnapshot().gameTime.days
        assertEquals(5, result.dailySummaries.size)
        assertTrue(afterDays > beforeDays)
    }
}
