package com.example.townapp.domain.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 行为触发式认知碎片管理器
 *
 * 严格遵循实事求是 + 自由平等原则：
 * 1. 碎片无等级压制门槛（不锁剧情）
 * 2. 无收集成就、无奖励、无进度条
 * 3. 碎片仅作为复盘素材，不影响游玩自由路线
 *
 * 触发场景分类（自然沉淀式）：
 * - 观察类：静默观光完整一日 / 翻看陌生人完整人生
 * - 反思类：看完回忆片段 / 与观念差异大的NPC交谈
 * - 接纳类：使用认知手术刀拆解 / 晚年复盘
 */
object CognitionTrigger {

    // 触发记录（避免短时间内重复触发）
    private val _lastTriggerTime = MutableStateFlow<Map<String, Long>>(emptyMap())
    val lastTriggerTime = _lastTriggerTime.asStateFlow()

    private const val TRIGGER_COOLDOWN = 30_000L  // 30秒冷却

    /**
     * 触发观察类碎片
     */
    fun triggerObservation(
        source: String,
        description: String,
        cooldownKey: String = source
    ) {
        if (isOnCooldown(cooldownKey)) return
        CognitionState.addFragment(
            type = FragmentType.OBSERVATION,
            source = source,
            description = description
        )
        recordTrigger(cooldownKey)
    }

    /**
     * 触发反思类碎片
     */
    fun triggerReflection(
        source: String,
        description: String,
        cooldownKey: String = source
    ) {
        if (isOnCooldown(cooldownKey)) return
        CognitionState.addFragment(
            type = FragmentType.REFLECTION,
            source = source,
            description = description
        )
        recordTrigger(cooldownKey)
    }

    /**
     * 触发接纳类碎片
     */
    fun triggerAcceptance(
        source: String,
        description: String,
        cooldownKey: String = source
    ) {
        if (isOnCooldown(cooldownKey)) return
        CognitionState.addFragment(
            type = FragmentType.ACCEPTANCE,
            source = source,
            description = description
        )
        recordTrigger(cooldownKey)
    }

    private fun isOnCooldown(key: String): Boolean {
        val lastTime = _lastTriggerTime.value[key] ?: return false
        return System.currentTimeMillis() - lastTime < TRIGGER_COOLDOWN
    }

    private fun recordTrigger(key: String) {
        _lastTriggerTime.value = _lastTriggerTime.value + (key to System.currentTimeMillis())
    }
}