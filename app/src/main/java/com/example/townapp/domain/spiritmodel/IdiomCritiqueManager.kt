package com.example.townapp.domain.spiritmodel

import android.util.Log
import com.example.townapp.data.repository.DataIntegrationManager
import com.example.townapp.domain.engine.PlayerState
import kotlin.math.min

private const val TAG = "IdiomCritiqueManager"

/**
 * 精神/认知事件管理器
 *
 * 职责：
 * - 成语触发与解析
 * - 微事件、陪伴、认知卡片、认知剖析、闪光点等内容生成
 * - 返回事件文本与精神属性变更（由 SimulationEngine 统一应用）
 *
 * 设计原则：
 * - 只做内容生成和效果计算，不直接修改 PlayerState
 * - 输入当前状态和时间信息，输出事件结果
 */
object IdiomCritiqueManager {

    /**
     * 精神事件结果
     */
    data class SpiritEventResult(
        val eventText: String,      // 要加入 dailyEvents 的文本
        val logText: String,        // 日志标记
        val controlDelta: Double = 0.0,
        val happinessDelta: Double = 0.0,
        val anxietyDelta: Double = 0.0,
        val lonelinessDelta: Double = 0.0
    )

    /** 触发微事件 */
    fun triggerMicroEvent(): SpiritEventResult? {
        val microEvent = DataIntegrationManager.getRandomMicroEvent() ?: return null
        return SpiritEventResult(
            eventText = "微事件: ${microEvent.content}",
            logText = "微事件: ${microEvent.content}"
        )
    }

    /** 触发成语事件 */
    fun triggerIdiomEvent(): SpiritEventResult? {
        val idiom = DataIntegrationManager.getRandomIdiom() ?: return null
        return SpiritEventResult(
            eventText = "今日成语: ${idiom.idiom} - ${idiom.traditionalMeaning}",
            logText = "成语: ${idiom.idiom}"
        )
    }

    /** 触发陪伴内容 */
    fun triggerCompanionEvent(): SpiritEventResult? {
        val companion = DataIntegrationManager.getRandomCompanion()
        if (companion.isEmpty()) return null
        return SpiritEventResult(
            eventText = "陪伴: $companion",
            logText = "陪伴内容"
        )
    }

    /** 触发认知剖析 */
    fun triggerDissectionEvent(): SpiritEventResult? {
        val dissection = DataIntegrationManager.getRandomDissection() ?: return null
        return SpiritEventResult(
            eventText = "认知剖析: ${dissection.title}",
            logText = "认知剖析",
            controlDelta = 2.0,
            happinessDelta = 1.0
        )
    }

    /** 触发认知卡片 */
    fun triggerCognitiveCardEvent(): SpiritEventResult? {
        val card = DataIntegrationManager.getRandomCognitiveCard() ?: return null
        return SpiritEventResult(
            eventText = "认知卡片: ${card.title}",
            logText = "认知卡片",
            controlDelta = 1.5
        )
    }

    /** 触发模拟场景 */
    fun triggerScenarioEvent(): SpiritEventResult? {
        val scenario = DataIntegrationManager.getRandomScenario() ?: return null
        return SpiritEventResult(
            eventText = "模拟场景: ${scenario.name}",
            logText = "模拟场景"
        )
    }

    /** 触发闪光点 */
    fun triggerSpotlightEvent(): SpiritEventResult? {
        val spotlight = DataIntegrationManager.getSpotlightById("random") ?: return null
        return SpiritEventResult(
            eventText = "闪光点: ${spotlight.description}",
            logText = "闪光点",
            happinessDelta = 2.0
        )
    }

    /**
     * 应用精神事件结果到玩家状态 —— 返回新的不可变实例
     * 由 SimulationEngine 调用，保证状态修改入口统一
     */
    fun applySpiritEventResult(state: PlayerState, result: SpiritEventResult): PlayerState {
        return state.copy(
            control = min(100.0, state.control + result.controlDelta),
            happiness = min(100.0, state.happiness + result.happinessDelta),
            anxiety = min(100.0, state.anxiety + result.anxietyDelta),
            loneliness = min(100.0, state.loneliness + result.lonelinessDelta)
        ).also {
            Log.d(TAG, "应用精神事件: ${result.logText}")
        }
    }
}
