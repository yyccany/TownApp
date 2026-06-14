package com.example.townapp.domain.usecase

import com.example.townapp.core.state.WorldType
import com.example.townapp.domain.engine.SimulationEngine
import com.example.townapp.domain.spacemodel.WorldModelRepository
import com.example.townapp.domain.spacemodel.WorldModelConfig
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * SwitchWorldUseCase 单元测试
 */
class SwitchWorldUseCaseTest {

    private lateinit var useCase: SwitchWorldUseCase

    @Before
    fun setUp() {
        useCase = SwitchWorldUseCase()
        mockkObject(SimulationEngine)
        mockkObject(WorldModelRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke stops engine, resets state, and applies world config`() {
        val config = WorldModelConfig(
            type = WorldType.URBAN,
            name = "都市",
            rent = 3000.0,
            salary = 8000.0,
            commuteMinutes = 45
        )
        every { WorldModelRepository.getConfig(WorldType.URBAN) } returns config
        every { SimulationEngine.stop() } just Runs
        every { SimulationEngine.reset() } just Runs
        every { SimulationEngine.updateSpaceState(any(), any(), any()) } just Runs

        useCase.invoke(WorldType.URBAN)

        verifyOrder {
            WorldModelRepository.getConfig(WorldType.URBAN)
            SimulationEngine.stop()
            SimulationEngine.reset()
            SimulationEngine.updateSpaceState(3000.0, 8000.0, 45)
        }
    }
}
