package com.example.townapp.domain.usecase

import com.example.townapp.domain.engine.ActionResult
import com.example.townapp.domain.engine.SimulationEngine
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * PlayerActionUseCase 单元测试
 *
 * 验证所有角色行为方法均正确委托至 SimulationEngine，参数与返回值透传无误。
 */
class PlayerActionUseCaseTest {

    private lateinit var useCase: PlayerActionUseCase

    @Before
    fun setUp() {
        useCase = PlayerActionUseCase()
        mockkObject(SimulationEngine)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // ==================== 基础生存行为 ====================

    @Test
    fun `eat delegates to SimulationEngine with correct foodId`() {
        val expected = ActionResult(success = true, message = "吃饱了")
        every { SimulationEngine.eat(1) } returns expected

        val result = useCase.eat(1)

        assertEquals(expected, result)
        verify { SimulationEngine.eat(1) }
    }

    @Test
    fun `sleep delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "睡好了")
        every { SimulationEngine.sleep(8) } returns expected

        val result = useCase.sleep(8)

        assertEquals(expected, result)
        verify { SimulationEngine.sleep(8) }
    }

    @Test
    fun `work delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "工作完成")
        every { SimulationEngine.work(8) } returns expected

        val result = useCase.work(8)

        assertEquals(expected, result)
        verify { SimulationEngine.work(8) }
    }

    @Test
    fun `study delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "学习有收获")
        every { SimulationEngine.study(4) } returns expected

        val result = useCase.study(4)

        assertEquals(expected, result)
        verify { SimulationEngine.study(4) }
    }

    @Test
    fun `idle delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "发呆中")
        every { SimulationEngine.idle(2) } returns expected

        val result = useCase.idle(2)

        assertEquals(expected, result)
        verify { SimulationEngine.idle(2) }
    }

    @Test
    fun `payRent delegates to SimulationEngine`() {
        val expected = ActionResult(success = true, message = "房租已支付")
        every { SimulationEngine.payRent() } returns expected

        val result = useCase.payRent()

        assertEquals(expected, result)
        verify { SimulationEngine.payRent() }
    }

    // ==================== 社交与休闲行为 ====================

    @Test
    fun `walk delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "散步愉快")
        every { SimulationEngine.walk(1) } returns expected

        val result = useCase.walk(1)

        assertEquals(expected, result)
        verify { SimulationEngine.walk(1) }
    }

    @Test
    fun `socialize delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "社交愉快")
        every { SimulationEngine.socialize(2) } returns expected

        val result = useCase.socialize(2)

        assertEquals(expected, result)
        verify { SimulationEngine.socialize(2) }
    }

    @Test
    fun `meditate delegates to SimulationEngine with correct minutes`() {
        val expected = ActionResult(success = true, message = "内心平静")
        every { SimulationEngine.meditate(30) } returns expected

        val result = useCase.meditate(30)

        assertEquals(expected, result)
        verify { SimulationEngine.meditate(30) }
    }

    @Test
    fun `read delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "阅读有收获")
        every { SimulationEngine.read(2) } returns expected

        val result = useCase.read(2)

        assertEquals(expected, result)
        verify { SimulationEngine.read(2) }
    }

    @Test
    fun `listenMusic delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "音乐治愈")
        every { SimulationEngine.listenMusic(1) } returns expected

        val result = useCase.listenMusic(1)

        assertEquals(expected, result)
        verify { SimulationEngine.listenMusic(1) }
    }

    @Test
    fun `exercise delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "运动达标")
        every { SimulationEngine.exercise(1) } returns expected

        val result = useCase.exercise(1)

        assertEquals(expected, result)
        verify { SimulationEngine.exercise(1) }
    }

    @Test
    fun `cook delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "烹饪成功")
        every { SimulationEngine.cook(2) } returns expected

        val result = useCase.cook(2)

        assertEquals(expected, result)
        verify { SimulationEngine.cook(2) }
    }

    @Test
    fun `organize delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "整理完毕")
        every { SimulationEngine.organize(1) } returns expected

        val result = useCase.organize(1)

        assertEquals(expected, result)
        verify { SimulationEngine.organize(1) }
    }

    @Test
    fun `watchMovie delegates to SimulationEngine with correct hours`() {
        val expected = ActionResult(success = true, message = "电影好看")
        every { SimulationEngine.watchMovie(2) } returns expected

        val result = useCase.watchMovie(2)

        assertEquals(expected, result)
        verify { SimulationEngine.watchMovie(2) }
    }

    @Test
    fun `teaBreak delegates to SimulationEngine with correct minutes`() {
        val expected = ActionResult(success = true, message = "茶歇放松")
        every { SimulationEngine.teaBreak(15) } returns expected

        val result = useCase.teaBreak(15)

        assertEquals(expected, result)
        verify { SimulationEngine.teaBreak(15) }
    }

    @Test
    fun `tendPlant delegates to SimulationEngine`() {
        val expected = ActionResult(success = true, message = "植物茁壮")
        every { SimulationEngine.tendPlant() } returns expected

        val result = useCase.tendPlant()

        assertEquals(expected, result)
        verify { SimulationEngine.tendPlant() }
    }

    @Test
    fun `journal delegates to SimulationEngine with correct minutes`() {
        val expected = ActionResult(success = true, message = "日记已记录")
        every { SimulationEngine.journal(10) } returns expected

        val result = useCase.journal(10)

        assertEquals(expected, result)
        verify { SimulationEngine.journal(10) }
    }

    // ==================== 空间行为 ====================

    @Test
    fun `changeSpace delegates to SimulationEngine with correct spaceId`() {
        val expected = ActionResult(success = true, message = "空间切换成功")
        every { SimulationEngine.changeSpace("room_2000") } returns expected

        val result = useCase.changeSpace("room_2000")

        assertEquals(expected, result)
        verify { SimulationEngine.changeSpace("room_2000") }
    }
}
