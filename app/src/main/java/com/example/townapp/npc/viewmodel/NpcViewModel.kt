package com.example.townapp.npc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.townapp.npc.NpcDataLayer
import com.example.townapp.npc.model.NpcDisplayVo
import com.example.townapp.npc.model.NpcFloatingThought
import com.example.townapp.npc.model.NpcTimelineVo
import com.example.townapp.npc.model.TownNarrativeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * NPC 业务逻辑层（状态流转桥梁）
 *
 * 严格遵循小镇项目铁律：
 * 1. 不导入任何 Dao
 * 2. 不写 SQL 筛选条件
 * 3. 不做文件 IO
 * 4. 所有数据需求通过 NpcDataLayer（统一入口）获取
 * 5. 只做两件事：监听数据变化、封装简单交互方法
 */
class NpcViewModel(
    private val dataLayer: NpcDataLayer = NpcDataLayer.instance
) : ViewModel() {

    private val repo = dataLayer.npcRepository

    /** 公开 Repository（供档案馆页面使用） */
    val repository = repo

    // ================== 状态输出（仅供 Compose UI 订阅） ==================

    /** 当前季节（控制 NPC 显示范围） */
    private val _currentSeasonId = MutableStateFlow(1)
    val currentSeasonId: StateFlow<Int> = _currentSeasonId.asStateFlow()

    /** 当前可见 NPC 列表（已转换为展示模型） */
    private val _npcList = MutableStateFlow<List<NpcDisplayVo>>(emptyList())
    val npcList: StateFlow<List<NpcDisplayVo>> = _npcList.asStateFlow()

    /** 当前选中的 NPC */
    private val _selectedNpc = MutableStateFlow<NpcDisplayVo?>(null)
    val selectedNpc: StateFlow<NpcDisplayVo?> = _selectedNpc.asStateFlow()

    /** NPC 列表是否可见（默认隐藏，交互才展开） */
    private val _isListVisible = MutableStateFlow(false)
    val isListVisible: StateFlow<Boolean> = _isListVisible.asStateFlow()

    /** 当前展示的记忆碎片（null 表示不展示） */
    private val _currentMemory = MutableStateFlow<com.example.townapp.npc.model.MemoryFragmentVo?>(null)
    val currentMemory: StateFlow<com.example.townapp.npc.model.MemoryFragmentVo?> = _currentMemory.asStateFlow()

    /** 生命周期管理器（通过 NpcDataLayer 获取） */
    private val lifecycleManager = dataLayer.lifecycleManager

    /** 当前玩家年龄（用于回忆计算） */
    private val _currentPlayerAge = MutableStateFlow(22)
    val currentPlayerAge: StateFlow<Int> = _currentPlayerAge.asStateFlow()

    /** 当前游戏年份 */
    private val _currentYear = MutableStateFlow(2025)
    val currentYear: StateFlow<Int> = _currentYear.asStateFlow()

    /** 当前游戏小时（0-23），用于 AtmosphereOverlay 昼夜渐变 */
    private val _currentHour = MutableStateFlow(12)
    val currentHour: StateFlow<Int> = _currentHour.asStateFlow()

    /** 当前 NPC 专属氛围色调（null = 不渲染遮罩） */
    private val _currentMoodPalette = MutableStateFlow<com.example.townapp.npc.model.TonePaletteVo?>(null)
    val currentMoodPalette: StateFlow<com.example.townapp.npc.model.TonePaletteVo?> = _currentMoodPalette.asStateFlow()

    /** 当前 NPC 时间线（用于档案馆时间线详情页） */
    private val _currentTimeline = MutableStateFlow<NpcTimelineVo?>(null)
    val currentTimeline: StateFlow<NpcTimelineVo?> = _currentTimeline.asStateFlow()

    // ================== 业务逻辑（无文件 IO、无数据库查询） ==================

    /**
     * 加载当前季节可见 NPC
     */
    fun loadNpcs() {
        viewModelScope.launch {
            _npcList.value = repo.getNpcsDisplayBySeason(_currentSeasonId.value)
        }
    }

    /**
     * 切换季节
     */
    fun changeSeason(newSeasonId: Int) {
        _currentSeasonId.value = newSeasonId
        loadNpcs()
    }

    /**
     * 选择 NPC（自动刷新对话 + 自动加载专属氛围色调）
     */
    fun selectNpc(npc: NpcDisplayVo) {
        _selectedNpc.value = npc
        loadNpcMood(npc.npcId)
    }

    /**
     * 加载 NPC 专属氛围色调
     */
    private fun loadNpcMood(npcId: Int) {
        viewModelScope.launch {
            _currentMoodPalette.value = repo.getNpcMoodPalette(npcId)
        }
    }

    /**
     * 清空 NPC 专属氛围色调（关闭弹窗时调用）
     */
    private fun clearNpcMood() {
        _currentMoodPalette.value = null
    }

    /**
     * 与 NPC 对话（提升好感，自动刷新对话文本）
     */
    fun talkToNpc(npcId: Int) {
        viewModelScope.launch {
            repo.updateFavor(npcId, delta = 5)
            refreshNpc(npcId)
        }
    }

    /**
     * 关闭对话弹窗（同时清空专属氛围色调）
     */
    fun closeDialog() {
        _selectedNpc.value = null
        clearNpcMood()
    }

    /**
     * 切换 NPC 列表显示/隐藏
     */
    fun toggleList() {
        _isListVisible.value = !_isListVisible.value
    }

    // ================== 折叠菜单分发（极简交互入口） ==================

    /** 当前路由标记：null=地图页，archive=纪念馆，settings=设置，textMode=文字模式 */
    private val _route = MutableStateFlow<String?>(null)
    val route: StateFlow<String?> = _route.asStateFlow()

    /** 打开纪念馆 */
    fun openArchive() {
        _route.value = "archive"
    }

    /** 打开设置 */
    fun openSettings() {
        _route.value = "settings"
    }

    /** 打开文字模式 */
    fun openTextMode() {
        _route.value = "textMode"
    }

    /** 返回地图 */
    fun backToMap() {
        _route.value = null
    }

    /**
     * 查看 NPC 时间线（档案馆详情页）
     */
    fun viewNpcTimeline(npcId: Int) {
        viewModelScope.launch {
            val timeline = repo.getNpcTimeline(npcId)
            _currentTimeline.value = timeline
            _route.value = "timeline"
        }
    }

    /**
     * 触发回忆弹窗（简化版，供时间线页面调用）
     */
    fun triggerMemory(markId: Int) {
        triggerMemory(
            markId = markId,
            groupId = 1,
            currentYear = _currentYear.value,
            currentPlayerAge = _currentPlayerAge.value
        )
    }

    /**
     * 刷新单个 NPC 数据（好感变化后自动重新匹配对话）
     */
    private fun refreshNpc(npcId: Int) {
        viewModelScope.launch {
            repo.getNpcDisplayById(npcId)?.let { updated ->
                _selectedNpc.value = updated
                val index = _npcList.value.indexOfFirst { it.npcId == npcId }
                if (index >= 0) {
                    val updatedList = _npcList.value.toMutableList()
                    updatedList[index] = updated
                    _npcList.value = updatedList
                }
            }
        }
    }

    // ================== 记忆碎片触发逻辑 ==================

    /**
     * 触发回忆弹窗（走到特定地点 / 见到特定 NPC 时调用）
     *
     * @param markId 记忆标记 ID
     * @param groupId 记忆组 ID
     * @param currentYear 当前游戏年份
     * @param currentPlayerAge 当前玩家年龄
     * @param relatedNpcId 关联 NPC ID（可选）
     */
    fun triggerMemory(
        markId: Int,
        groupId: Int,
        currentYear: Int,
        currentPlayerAge: Int,
        relatedNpcId: Int = 0
    ) {
        viewModelScope.launch {
            val memory = repo.getMemoryFragment(
                markId = markId,
                groupId = groupId,
                currentYear = currentYear,
                currentPlayerAge = currentPlayerAge,
                seasonId = _currentSeasonId.value,
                relatedNpcId = relatedNpcId
            )
            _currentMemory.value = memory
        }
    }

    /**
     * 关闭回忆弹窗（自动销毁）
     */
    fun closeMemory() {
        // 清除当前 NPC 的回忆标记
        _selectedNpc.value?.let { npc ->
            lifecycleManager.clearMemoryMark(npc.npcId)
        }
        _currentMemory.value = null
        clearNpcMood()
    }

    // ================== 生命周期入口（年份递增时调用） ==================

    /**
     * 年份递增：触发 NPC 生命周期年度迭代
     * 由全局时间工具类或主 ViewModel 在玩家过完完整一年后调用
     */
    fun onYearAdvanced() {
        viewModelScope.launch {
            val events = lifecycleManager.onYearAdvanced()

            // 处理本年度触发的回忆事件
            events.forEach { event ->
                when (event) {
                    is com.example.townapp.npc.util.LifecycleEvent.MemoryTriggered -> {
                        // 写入 markId 标记到 NpcStatus
                        val status = repo.getNpcStatus(event.npcId)
                        repo.batchUpdateStatus(
                            listOf(status.copy(memoryTriggerMark = event.markId))
                        )
                    }
                    is com.example.townapp.npc.util.LifecycleEvent.WorkStateChanged -> {
                        // 工作状态变更：刷新 NPC 列表数据
                        loadNpcs()
                    }
                }
            }

            // 玩家年龄 +1，年份 +1
            _currentPlayerAge.value = _currentPlayerAge.value + 1
            _currentYear.value = _currentYear.value + 1
        }
    }

    /**
     * 与 NPC 对话时检查并触发回忆弹窗
     * 如果该 NPC 身上有 memoryTriggerMark，自动弹出回忆
     */
    fun checkAndTriggerMemoryOnTalk(npcId: Int) {
        viewModelScope.launch {
            val status = repo.getNpcStatus(npcId)
            if (status.memoryTriggerMark > 0) {
                triggerMemory(
                    markId = status.memoryTriggerMark,
                    groupId = npcId,  // groupId 与 npcId 保持一致，便于定位 JSON
                    currentYear = _currentYear.value,
                    currentPlayerAge = _currentPlayerAge.value,
                    relatedNpcId = npcId
                )
            }
        }
    }

    /**
     * 获取当前地图上 NPC 的漂浮独白列表
     *
     * 落实实事求是原则：
     * - 内心碎语极短（4~8 个字），仅作为环境点缀
     * - 不传递完整叙事，只是生活碎片的微弱回响
     * - 贴合普通人含蓄的表达习惯，不会长篇大论倾诉
     *
     * @return 漂浮独白列表（已自动截断为 8 字以内，最多返回 2 条）
     */
    fun getFloatingThoughts(): List<NpcFloatingThought> {
        return _npcList.value
            .filter { it.moodLevel <= 3 }
            .map { npc ->
                val rawThought = when (npc.moodLevel) {
                    1 -> "房租又涨了"
                    2 -> "今天好累"
                    3 -> "日子好难"
                    else -> "..."
                }
                NpcFloatingThought(
                    npcId = npc.npcId,
                    npcName = npc.name,
                    content = rawThought.take(TownNarrativeState.MAX_THOUGHT_LENGTH),
                    x = npc.x / 100f,
                    y = npc.y / 100f,
                    remainingDuration = 3000L
                )
            }
            .take(TownNarrativeState.MAX_FLOATING_THOUGHTS)
    }
}
