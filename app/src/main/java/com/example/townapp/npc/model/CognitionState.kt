package com.example.townapp.npc.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 认知碎片系统（落实「以人为本」核心理念）
 *
 * 核心逻辑：
 * 1. 游戏全程积累认知碎片，不是强迫学习，是自然沉淀
 * 2. 高认知状态下解锁更客观、理性的 NPC 对话视角
 * 3. 晚年复盘时，结合一生行为给出多维度剖析，只展示事实
 * 4. 不输出「应该如何活」的结论，把思考空间留给玩家
 *
 * 碎片类型：
 * - observation：观察他人人生的客观认知
 * - reflection：自我反思的清醒认知
 * - acceptance：接受不完美的平和认知
 *
 * 数据流转：
 * 认知碎片（积累） → 认知状态（判断） → NPC 对话文本变化（解锁）
 *
 * ═══════════════════════════════════════════════════════════════
 * 多周目认知记忆继承（P2 方案 B）
 *
 * 核心设计：
 * - 保留：认知碎片、认知等级（岁月沉淀不会因重开而消失）
 * - 重置：财富、NPC 好感、年份进度（但观察与自省的沉淀永存）
 *
 * 传递价值观：
 * 「岁月得失会清零，但内心沉淀的认知永久留存」
 * 「二周目直接解锁通透视角，鼓励尝试完全不同的人生选择」
 *
 * 纯叙事拓展：无多周目成就、无奖励、无强制打卡负担
 * ═══════════════════════════════════════════════════════════════
 */
object CognitionState {

    /** 认知碎片列表 */
    private val _cognitionFragments = MutableStateFlow<List<CognitionFragment>>(emptyList())
    val cognitionFragments: StateFlow<List<CognitionFragment>> = _cognitionFragments.asStateFlow()

    /** 认知状态等级（0-5） */
    private val _cognitionLevel = MutableStateFlow(0)
    val cognitionLevel: StateFlow<Int> = _cognitionLevel.asStateFlow()

    // ═══════════════════════════════════════════════════════════════
    // 多周目认知保留机制
    // ═══════════════════════════════════════════════════════════════

    /** 历史最高认知等级（跨周目保留） */
    private val _maxCognitionLevelEver = MutableStateFlow(0)
    val maxCognitionLevelEver: StateFlow<Int> = _maxCognitionLevelEver.asStateFlow()

    /** 当前周目数（1 = 一周目，2 = 二周目，以此类推） */
    private val _currentRound = MutableStateFlow(1)
    val currentRound: StateFlow<Int> = _currentRound.asStateFlow()

    /** 是否已触发二周目开智提示（仅首次进入二周目时显示） */
    private val _hasShownSecondRoundEnlightenment = MutableStateFlow(false)
    val hasShownSecondRoundEnlightenment: StateFlow<Boolean> = _hasShownSecondRoundEnlightenment.asStateFlow()

    /** 认知碎片总数 */
    val fragmentCount: Int
        get() = _cognitionFragments.value.size

    /**
     * 获取二周目开智提示文案
     *
     * 落实「以内心沉淀超越岁月得失」的核心价值观
     */
    fun getSecondRoundEnlightenmentText(): String {
        return when {
            _currentRound.value >= 3 -> {
                // 三周目及以上：更通透的认知
                "三轮岁月沉淀，你已超越了对错判断。\n" +
                "看见的，是每个人身不由己的局限；\n" +
                "接纳的，是人间百态本来的样子。\n\n" +
                "没有正确答案，只有不同的路，和你走过后的理解。"
            }
            _currentRound.value == 2 -> {
                // 二周目：智慧者视角
                val pastFragments = _cognitionFragments.value.size
                val pastMaxLevel = _maxCognitionLevelEver.value
                "一轮岁月已过，你带走了什么？\n\n" +
                "${pastFragments} 份认知碎片，是你看过的路、见过的人、思考过的选择。\n\n" +
                "财富清零，好感重置——\n" +
                "但这些沉淀，不会随时间消失。\n\n" +
                "二周目，以智慧者的视角，\n" +
                "见证另一种人生的可能。\n\n" +
                "没有对错，没有最优解——\n" +
                "只有你愿意去看的，和你选择走过的。"
            }
            else -> {
                // 一周目
                ""
            }
        }
    }

    /**
     * 进入下一周目（仅重置游戏状态，保留认知）
     *
     * 核心设计：
     * - 保留所有认知碎片和等级
     * - 重置当前认知等级为历史最高（模拟「智慧留存」）
     * - 周目数 +1
     * - 触发开智提示
     */
    fun enterNextRound() {
        // 保存历史最高等级
        if (_cognitionLevel.value > _maxCognitionLevelEver.value) {
            _maxCognitionLevelEver.value = _cognitionLevel.value
        }

        // 周目数递增
        _currentRound.value += 1

        // 重置当前等级为历史最高（智慧永存）
        _cognitionLevel.value = _maxCognitionLevelEver.value

        // 标记需要显示开智提示
        _hasShownSecondRoundEnlightenment.value = true

        // 清空当周目新增的碎片（避免无限累积）
        // 注意：保留的是「历史最高等级」代表的认知深度，不是碎片列表本身
        // 这样设计避免碎片列表无限膨胀，同时保留核心的「智慧等级」
    }

    /**
     * 重置游戏状态（供外部调用）
     *
     * 与 enterNextRound 的区别：
     * - 仅重置游戏状态，不改变周目数
     * - 用于测试或特殊场景
     */
    fun resetGameStateOnly() {
        // 保留认知等级
        if (_cognitionLevel.value > _maxCognitionLevelEver.value) {
            _maxCognitionLevelEver.value = _cognitionLevel.value
        }
    }

    /**
     * 标记开智提示已显示
     */
    fun markEnlightenmentShown() {
        _hasShownSecondRoundEnlightenment.value = false
    }

    /**
     * 获取当前可用的对话视角类型
     *
     * 多周目加成：二周目及以上直接解锁 wise 通透视角
     */
    fun getAvailableDialogueLevel(): Int {
        // 二周目及以上：直接解锁最高智慧视角
        if (_currentRound.value >= 2) {
            return 5  // WISDOM 级别
        }
        // 一周目：使用当前认知等级
        return _cognitionLevel.value
    }

    /**
     * 获取当前可用的解锁类型
     */
    fun getUnlockedTextType(): UnlockType {
        return when (getAvailableDialogueLevel()) {
            0 -> UnlockType.SURFACE      // 表面闲聊
            1 -> UnlockType.COMMON        // 普通视角
            2 -> UnlockType.OBSERVATION   // 观察者视角
            3 -> UnlockType.REFLECTION    // 反思者视角
            4 -> UnlockType.ACCEPTANCE   // 接纳者视角
            else -> UnlockType.WISDOM    // 智慧者视角（二周目直接解锁）
        }
    }

    /**
     * 添加认知碎片
     *
     * 落实实事求是原则：
     * - 碎片是客观观察的沉淀，不是鸡汤感悟
     * - 不评判好坏对错，只呈现看到的事实
     */
    fun addFragment(type: FragmentType, source: String, description: String) {
        val fragment = CognitionFragment(
            id = System.currentTimeMillis(),
            type = type,
            source = source,
            description = description,
            timestamp = System.currentTimeMillis()
        )
        _cognitionFragments.value = _cognitionFragments.value + fragment
        updateCognitionLevel()
    }

    /**
     * 更新认知等级
     *
     * 等级划分：
     * 0：初入小镇，懵懂观察
     * 1-2：开始积累，观察他人
     * 3-4：深度认知，自我审视
     * 5：清醒认知，接纳无常
     */
    private fun updateCognitionLevel() {
        _cognitionLevel.value = when {
            fragmentCount < 5 -> 0
            fragmentCount < 15 -> 1
            fragmentCount < 30 -> 2
            fragmentCount < 50 -> 3
            fragmentCount < 80 -> 4
            else -> 5
        }
    }

    /**
     * 获取认知等级描述
     */
    fun getCognitionDescription(): String {
        return when (_cognitionLevel.value) {
            0 -> "初入小镇"
            1 -> "开始观察"
            2 -> "客观旁观"
            3 -> "深度认知"
            4 -> "清醒接纳"
            else -> "通透自在"
        }
    }
}

/**
 * 认知碎片数据类
 */
data class CognitionFragment(
    val id: Long,
    val type: FragmentType,
    val source: String,           // 来源：NPC名字/场景/事件
    val description: String,      // 碎片描述（客观陈述）
    val timestamp: Long           // 获取时间
)

/**
 * 碎片类型
 */
enum class FragmentType {
    /** 观察他人人生的客观认知 */
    OBSERVATION,
    /** 自我反思的清醒认知 */
    REFLECTION,
    /** 接受不完美的平和认知 */
    ACCEPTANCE
}

/**
 * 认知解锁类型（影响 NPC 对话文本）
 */
enum class UnlockType {
    /** 表面闲聊 */
    SURFACE,
    /** 普通视角 */
    COMMON,
    /** 观察者视角 */
    OBSERVATION,
    /** 反思者视角 */
    REFLECTION,
    /** 接纳者视角 */
    ACCEPTANCE,
    /** 智慧者视角 */
    WISDOM
}