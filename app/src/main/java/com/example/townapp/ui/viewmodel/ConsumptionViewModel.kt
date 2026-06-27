package com.example.townapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.townapp.data.database.entity.ConsumptionChoiceEventEntity
import com.example.townapp.data.database.entity.UserConsumptionStateEntity
import com.example.townapp.domain.consumption.ConsumptionSystem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 消费系统 ViewModel
 *
 * 管理消费仪表盘、消费抉择事件的UI状态。
 * 不做任何价值判断，只管理状态和数据转换。
 *
 * 设计原则：
 * - 无对错评判，只用客观数值展示不同取舍的长期结果
 * - 所有文案、描述全部走数据层，ViewModel 只做状态转换
 * - 遵循小镇宪章：永不输出评判性结论
 */
class ConsumptionViewModel : ViewModel() {

    // ==================== 状态流 ====================

    /** 用户消费状态 */
    val consumptionState: StateFlow<UserConsumptionStateEntity> =
        MutableStateFlow(UserConsumptionStateEntity())

    /** 消费抉择事件列表 */
    val choiceEvents: StateFlow<List<ConsumptionChoiceEventEntity>> =
        MutableStateFlow(emptyList())

    /** 待处理的消费抉择 */
    val pendingChoice: StateFlow<ConsumptionChoiceEventEntity?> =
        MutableStateFlow(null)

    // ==================== 计算属性 ====================

    /**
     * 获取当前消费倾向的长期效果
     */
    fun getLongTermEffect(): ConsumptionSystem.LongTermEffect {
        return ConsumptionSystem.LongTermEffect()
    }

    /**
     * 获取当前消费倾向的温柔描述文案
     */
    fun getOrientationNarrative(): String? {
        return null
    }

    /**
     * 获取四大维度的评分列表（用于UI展示）
     */
    fun getDimensionScores(): List<DimensionScore> {
        val state = consumptionState.value
        return listOf(
            DimensionScore("food", "饮食", state.foodScore, "日常优质食材 vs 宴席网红溢价"),
            DimensionScore("clothing", "穿搭", state.clothingScore, "贴身舒适 vs 大牌logo"),
            DimensionScore("housing", "居家", state.housingScore, "环保健康家电 vs 网红软装颜值"),
            DimensionScore("transport", "出行", state.transportScore, "安全护具 vs 豪车排面贷款")
        )
    }

    /**
     * 获取综合评分等级描述
     */
    fun getOverallLevel(): String {
        val score = consumptionState.value.overallScore
        return when {
            score >= 80 -> "人本生活家"
            score >= 60 -> "清醒消费者"
            score >= 40 -> "矛盾探索者"
            score >= 20 -> "排场追随者"
            else -> "虚荣透支者"
        }
    }

    // ==================== 用户操作 ====================

    /**
     * 做出消费抉择
     * @param eventId 事件ID
     * @param choice 1=人本路线，2=虚荣路线
     */
    fun makeChoice(eventId: String, choice: Int) {
    }

    /**
     * 触发一个消费抉择事件（用于测试或剧情触发）
     */
    fun triggerChoiceEvent(eventId: String, eventType: String, playerAge: Int = 30) {
    }

    /**
     * 重置消费状态（新周目）
     */
    fun resetConsumption() {
    }

    // ==================== 数据类 ====================

    /**
     * 维度评分数据类（用于UI展示）
     */
    data class DimensionScore(
        val key: String,
        val name: String,
        val score: Int,
        val description: String
    ) {
        /** 评分等级：high/medium/low */
        val level: String
            get() = when {
                score >= 60 -> "high"
                score >= 40 -> "medium"
                else -> "low"
            }
    }

    companion object {
        // 消费抉择事件ID前缀
        const val CHOICE_EVENT_PREFIX = "consumption_choice_"
    }
}
