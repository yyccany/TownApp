package com.example.townapp.domain.usecase

import com.example.townapp.domain.engine.ActionResult
import com.example.townapp.domain.engine.SimulationEngine

/**
 * 角色行为用例
 *
 * 职责：封装玩家所有行为动作的状态变更、消耗/收益计算。
 * 所有行为均委托 [SimulationEngine] 执行，本身不持有业务状态。
 * 不直接持有 View/ViewModel 引用，仅依赖 Engine（后续逐步内联业务逻辑）。
 */
class PlayerActionUseCase {

    // ==================== 基础生存行为 ====================

    /**
     * 进食，恢复饱腹感并触发营养/风险计算。
     *
     * @param foodId 食物唯一标识
     * @return 行为结果，包含状态变更与文案反馈
     */
    fun eat(foodId: Int): ActionResult = SimulationEngine.eat(foodId)

    /**
     * 睡眠，恢复精力并推进时间。
     *
     * @param hours 睡眠时长（小时）
     * @return 行为结果
     */
    fun sleep(hours: Int): ActionResult = SimulationEngine.sleep(hours)

    /**
     * 工作，获取收入但消耗精力与饱腹感。
     *
     * @param hours 工作时长（小时）
     * @return 行为结果
     */
    fun work(hours: Int): ActionResult = SimulationEngine.work(hours)

    /**
     * 学习，提升知识值但消耗精力。
     *
     * @param hours 学习时长（小时）
     * @return 行为结果
     */
    fun study(hours: Int): ActionResult = SimulationEngine.study(hours)

    /**
     * 发呆/ idle，轻微恢复精神但无收益。
     *
     * @param hours 时长（小时）
     * @return 行为结果
     */
    fun idle(hours: Int): ActionResult = SimulationEngine.idle(hours)

    /**
     * 支付房租，扣除货币并更新空间状态。
     *
     * @return 行为结果
     */
    fun payRent(): ActionResult = SimulationEngine.payRent()

    // ==================== 社交与休闲行为 ====================

    /**
     * 散步，轻微提升幸福感。
     *
     * @param hours 时长（小时）
     * @return 行为结果
     */
    fun walk(hours: Int): ActionResult = SimulationEngine.walk(hours)

    /**
     * 社交，提升幸福感但消耗精力。
     *
     * @param hours 时长（小时）
     * @return 行为结果
     */
    fun socialize(hours: Int): ActionResult = SimulationEngine.socialize(hours)

    /**
     * 冥想，降低焦虑值。
     *
     * @param minutes 时长（分钟）
     * @return 行为结果
     */
    fun meditate(minutes: Int): ActionResult = SimulationEngine.meditate(minutes)

    /**
     * 阅读，提升知识值。
     *
     * @param hours 时长（小时）
     * @return 行为结果
     */
    fun read(hours: Int): ActionResult = SimulationEngine.read(hours)

    /**
     * 听音乐，提升幸福感。
     *
     * @param hours 时长（小时）
     * @return 行为结果
     */
    fun listenMusic(hours: Int): ActionResult = SimulationEngine.listenMusic(hours)

    /**
     * 运动，提升健康值但消耗精力与饱腹感。
     *
     * @param hours 时长（小时）
     * @return 行为结果
     */
    fun exercise(hours: Int): ActionResult = SimulationEngine.exercise(hours)

    /**
     * 烹饪，提升生活技能并产出食物。
     *
     * @param hours 时长（小时）
     * @return 行为结果
     */
    fun cook(hours: Int): ActionResult = SimulationEngine.cook(hours)

    /**
     * 整理空间，提升环境舒适度。
     *
     * @param hours 时长（小时）
     * @return 行为结果
     */
    fun organize(hours: Int): ActionResult = SimulationEngine.organize(hours)

    /**
     * 看电影，提升幸福感但消耗精力。
     *
     * @param hours 时长（小时）
     * @return 行为结果
     */
    fun watchMovie(hours: Int): ActionResult = SimulationEngine.watchMovie(hours)

    /**
     * 茶歇，轻微恢复精力与幸福感。
     *
     * @param minutes 时长（分钟）
     * @return 行为结果
     */
    fun teaBreak(minutes: Int): ActionResult = SimulationEngine.teaBreak(minutes)

    /**
     * 照料植物，提升环境舒适度与幸福感。
     *
     * @return 行为结果
     */
    fun tendPlant(): ActionResult = SimulationEngine.tendPlant()

    /**
     * 写日记，降低焦虑值并记录当日事件。
     *
     * @param writeTime 写作时长（分钟）
     * @return 行为结果
     */
    fun journal(writeTime: Int): ActionResult = SimulationEngine.journal(writeTime)

    // ==================== 空间行为 ====================

    /**
     * 切换当前居住/活动空间，应用新空间的影响参数。
     *
     * @param spaceId 空间唯一标识
     * @return 行为结果
     */
    fun changeSpace(spaceId: String): ActionResult = SimulationEngine.changeSpace(spaceId)
}
