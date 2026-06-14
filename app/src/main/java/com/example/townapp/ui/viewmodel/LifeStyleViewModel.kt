package com.example.townapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.townapp.data.model.LifeStyleEntry
import com.example.townapp.data.repository.LifeStyleRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 生活图鉴 ViewModel。
 *
 * 管理所有生活方式条目的解锁状态、解锁进度、解锁事件通知。
 * 不做任何价值判断，只管理状态。
 *
 * 解锁规则（"实事求是"原则）：
 * - 不预设「好的行为解锁好心条目」，只做关键词匹配和频次统计
 * - 达到阈值即解锁，不评价条目本身的"好/坏"
 */
class LifeStyleViewModel : ViewModel() {

    private val _allEntries = MutableStateFlow(
        LifeStyleRepository.getAllEntries().map { entry ->
            entry.copy(isUnlocked = false)
        }
    )

    /** 所有生活方式条目（包含解锁状态）。 */
    val allEntries: StateFlow<List<LifeStyleEntry>> = _allEntries.asStateFlow()

    /** 最近解锁的条目 ID（供 UI 弹出通知）。 */
    private val _recentlyUnlocked = MutableSharedFlow<String>(replay = 0)
    val recentlyUnlocked: SharedFlow<String> = _recentlyUnlocked.asSharedFlow()

    // 行为计数（分类 → 累计次数）
    private val behaviorCounts = mutableMapOf<String, Int>()

    // 各条目已满足的解锁条件数（条目 id → 进度因子）
    private val unlockProgress = MutableStateFlow<Map<String, Int>>(emptyMap())
    val unlockProgressMap: StateFlow<Map<String, Int>> = unlockProgress.asStateFlow()

    /** 按 ID 查询单个条目。 */
    fun getEntryById(id: String): LifeStyleEntry? =
        _allEntries.value.firstOrNull { it.id == id }

    /** 手动解锁指定条目（用于测试或管理员操作）。 */
    fun unlockEntry(id: String) {
        _allEntries.value = _allEntries.value.map { entry ->
            if (entry.id == id) entry.copy(isUnlocked = true) else entry
        }
    }

    /** 按地域过滤。 */
    fun getEntriesByRegion(region: String): List<LifeStyleEntry> =
        _allEntries.value.filter { it.region == region }

    /** 获取所有可用的地域标签。 */
    fun getAllRegions(): List<String> = LifeStyleRepository.getAllRegions()

    // ============================================================
    // 行为记录 → 解锁检查
    // ============================================================

    /**
     * 记录一次行为并检查是否有条目解锁。
     *
     * @param type 行为类型（如"food"、"mental"）
     * @param subType 行为子类型（如"reading"、"takeout"）
     * @param amount 行为量
     * @return 本次新解锁的条目 ID 列表（可为空）
     */
    fun recordBehavior(type: String, subType: String, amount: Double): List<String> {
        // 1. 统计关键词
        val keywords = listOfNotNull(
            type,
            subType,
            // 从 behaviorKey 中提取有意义的词干
            subType.split("_").firstOrNull()
        )
        keywords.forEach { kw ->
            behaviorCounts[kw] = (behaviorCounts[kw] ?: 0) + 1
        }

        // 2. 检查每个未解锁条目是否有新解锁的
        val newlyUnlocked = mutableListOf<String>()

        _allEntries.value = _allEntries.value.map { entry ->
            if (entry.isUnlocked) return@map entry

            val satisfied = countSatisfiedConditions(entry)
            val progress = unlockProgress.value.toMutableMap()
            progress[entry.id] = satisfied
            unlockProgress.value = progress

            if (satisfied >= MIN_CONDITIONS_TO_UNLOCK) {
                newlyUnlocked.add(entry.id)
                entry.copy(isUnlocked = true)
            } else {
                entry
            }
        }

        // 3. 发射解锁事件
        newlyUnlocked.forEach { id ->
            _recentlyUnlocked.tryEmit(id)
        }

        return newlyUnlocked
    }

    /**
     * 统计某个条目已满足多少个解锁条件。
     *
     * "条件"定义（实事求是地基于用户真实行为）：
     * - 与该条目地域相关的行为 ≥ 3 次
     * - 与该条目 name 关键词相关的行为 ≥ 2 次
     * - 与该条目 unlockCondition 关键词相关的行为 ≥ 3 次
     */
    private fun countSatisfiedConditions(entry: LifeStyleEntry): Int {
        var count = 0

        // 条件1：地域相关行为
        val regionKeywords = extractKeywords(entry.region)
        val regionHits = regionKeywords.sumOf { kw ->
            behaviorCounts.entries
                .filter { it.key.contains(kw, ignoreCase = true) }
                .sumOf { it.value }
        }
        if (regionHits >= 3) count++

        // 条件2：名称关键词相关行为
        val nameKeywords = extractKeywords(entry.name)
        val nameHits = nameKeywords.sumOf { kw ->
            behaviorCounts.entries
                .filter { it.key.contains(kw, ignoreCase = true) }
                .sumOf { it.value }
        }
        if (nameHits >= 2) count++

        // 条件3：解锁条件关键词相关行为
        val conditionKeywords = extractKeywords(entry.unlockCondition)
        val conditionHits = conditionKeywords.sumOf { kw ->
            behaviorCounts.entries
                .filter { it.key.contains(kw, ignoreCase = true) }
                .sumOf { it.value }
        }
        if (conditionHits >= 3) count++

        // 条件4：行为总次数 ≥ 10（说明玩家在积极使用系统）
        val totalActions = behaviorCounts.values.sum()
        if (totalActions >= 10) count++

        return count
    }

    /**
     * 从文本中提取有意义的解锁关键词。
     * 去除停用词，只保留有判别力的词。
     */
    private fun extractKeywords(text: String): List<String> {
        val stopWords = setOf("的", "了", "在", "是", "有", "和", "与", "或",
            "阅读", "观看", "交流", "来自", "相关", "超过", "记录")
        return text
            .replace(Regex("[，。！？、；：\"\"''（）【】《》\\d+]"), " ")
            .split(" ")
            .map { it.trim() }
            .filter { it.length >= 2 && it !in stopWords }
            .distinct()
    }

    /** 获取某个条目的解锁进度（0 到 4）。 */
    fun getProgress(entryId: String): Int =
        unlockProgress.value[entryId] ?: 0

    companion object {
        /** 解锁所需的最低条件数。 */
        private const val MIN_CONDITIONS_TO_UNLOCK = 2
    }
}