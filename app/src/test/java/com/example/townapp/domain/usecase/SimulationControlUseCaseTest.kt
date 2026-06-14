package com.example.townapp.domain.usecase

import com.example.townapp.domain.engine.SimulationEngine
import com.example.townapp.domain.engine.SimulationResult
import com.example.townapp.domain.engine.TimeMode
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * SimulationControlUseCase 单元测试
 *
 * 验证模拟控制操作（启停、调速、重置、快进、状态快照）均正确委托至 SimulationEngine。
 */
class SimulationControlUseCaseTest {

    private lateinit var useCase: SimulationControlUseCase

    @Before
    fun setUp() {
        useCase = SimulationControlUseCase()
        mockkObject(SimulationEngine)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `start delegates to SimulationEngine with correct mode`() = runTest {
        coEvery { SimulationEngine.start(TimeMode.FOREGROUND) } just Runs

        useCase.start(TimeMode.FOREGROUND)

        coVerify { SimulationEngine.start(TimeMode.FOREGROUND) }
    }

    @Test
    fun `stop delegates to SimulationEngine`() {
        every { SimulationEngine.stop() } just Runs

        useCase.stop()

        verify { SimulationEngine.stop() }
    }

    @Test
    fun `reset delegates to SimulationEngine`() {
        every { SimulationEngine.reset() } just Runs

        useCase.reset()

        verify { SimulationEngine.reset() }
    }

    @Test
    fun `resetWithLifePath delegates to SimulationEngine with correct parameters`() {
        every { SimulationEngine.resetWithLifePath(25, "tech_worker") } just Runs

        useCase.resetWithLifePath(25, "tech_worker")

        verify { SimulationEngine.resetWithLifePath(25, "tech_worker") }
    }

    @Test
    fun `fastForward delegates to SimulationEngine and returns result`() = runTest {
        val expected = SimulationResult(dailySummaries = emptyList())
        coEvery { SimulationEngine.fastForward(30) } returns expected

        val result = useCase.fastForward(30)

        assertEquals(expected, result)
        coVerify { SimulationEngine.fastForward(30) }
    }

    @Test
    fun `getSimulationSpeed delegates to SimulationEngine`() {
        every { SimulationEngine.getSimulationSpeed() } returns 2.5

        val result = useCase.getSimulationSpeed()

        assertEquals(2.5, result, 0.001)
        verify { SimulationEngine.getSimulationSpeed() }
    }

    @Test
    fun `setSimulationSpeed delegates to SimulationEngine with correct speed`() {
        every { SimulationEngine.setSimulationSpeed(any()) } just Runs

        useCase.setSimulationSpeed(3.0)

        verify { SimulationEngine.setSimulationSpeed(3.0) }
    }

    @Test
    fun `getStateSnapshot delegates to SimulationEngine`() {
        val expected = SimulationEngine.getStateSnapshot()
        every { SimulationEngine.getStateSnapshot() } returns expected

        val result = useCase.getStateSnapshot()

        assertEquals(expected, result)
        verify { SimulationEngine.getStateSnapshot() }
    }

    @Test
    fun `updateSpaceState delegates to SimulationEngine with correct parameters`() {
        every { SimulationEngine.updateSpaceState(any(), any(), any()) } just Runs

        useCase.updateSpaceState(2500.0, 8000.0, 45)

        verify { SimulationEngine.updateSpaceState(2500.0, 8000.0, 45) }
    }
}
