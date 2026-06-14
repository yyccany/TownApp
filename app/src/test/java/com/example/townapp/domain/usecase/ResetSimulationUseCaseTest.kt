package com.example.townapp.domain.usecase

import com.example.townapp.domain.engine.SimulationEngine
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * ResetSimulationUseCase 单元测试
 */
class ResetSimulationUseCaseTest {

    private lateinit var useCase: ResetSimulationUseCase

    @Before
    fun setUp() {
        useCase = ResetSimulationUseCase()
        mockkObject(SimulationEngine)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke delegates to SimulationEngine reset`() {
        every { SimulationEngine.reset() } just Runs

        useCase.invoke()

        verify { SimulationEngine.reset() }
    }
}
