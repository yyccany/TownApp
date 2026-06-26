package com.example.townapp.ui.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlin.math.pow
import com.example.townapp.data.repository.GameSaveRepository
import com.example.townapp.ui.screens.home.BuildingScenePosition
import com.example.townapp.ui.screens.home.BirthIdentity
import com.example.townapp.ui.screens.home.ConsumptionStyle
import com.example.townapp.ui.screens.home.Direction
import com.example.townapp.ui.screens.home.InteractionOption
import com.example.townapp.ui.screens.home.NpcScenePosition
import com.example.townapp.ui.screens.home.Scene2DState
import com.example.townapp.ui.screens.home.SceneInteractionConfig
import com.example.townapp.ui.screens.home.ViewMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    gameSaveRepository: GameSaveRepository? = null
) : TownViewModel(gameSaveRepository) {

    // ============================================
    // 首页专属弹窗状态（周计划编辑弹窗）
    // ============================================
    private val _showWeeklyPlanDialog = MutableStateFlow(false)
    override val showWeeklyPlanDialog: StateFlow<Boolean> = _showWeeklyPlanDialog.asStateFlow()

    override fun showWeeklyPlanEditor() {
        _showWeeklyPlanDialog.value = true
    }

    override fun hideWeeklyPlanEditor() {
        _showWeeklyPlanDialog.value = false
    }

    // ============================================
    // 首页专属弹窗状态（食物选择弹窗）
    // ============================================
    private val _showFoodPicker = MutableStateFlow(false)
    override val showFoodPicker: StateFlow<Boolean> = _showFoodPicker.asStateFlow()

    override fun showFoodPicker() {
        val currentHour = gameHour.value
        val isBreakfast = currentHour in 6..9
        val isLunch = currentHour in 11..14
        val isDinner = currentHour in 17..20
        
        if (isBreakfast || isLunch || isDinner) {
            _showFoodPicker.value = true
        } else {
            addEventLog("还未到用餐时间，先做点别的事吧")
        }
    }

    override fun hideFoodPicker() {
        _showFoodPicker.value = false
    }

    // ============================================
    // 首页专属弹窗状态（周简报弹窗）
    // ============================================
    private val _showWeeklyBrief = MutableStateFlow(false)
    override val showWeeklyBrief: StateFlow<Boolean> = _showWeeklyBrief.asStateFlow()

    override fun triggerWeeklyBrief() {
        _showWeeklyBrief.value = true
    }

    override fun dismissWeeklyBrief() {
        _showWeeklyBrief.value = false
    }

    // ============================================
    // 首页专属状态（宠物对话）
    // ============================================
    private val _petDialogue = MutableStateFlow<String?>(null)
    override val petDialogue: StateFlow<String?> = _petDialogue.asStateFlow()

    override fun setPetDialogue(text: String?) {
        _petDialogue.value = text
    }

    override fun clearPetDialogue() {
        setPetDialogue(null)
    }

    // ============================================
    // 首页专属弹窗状态（周事件抉择弹窗）
    // ============================================
    private val _pendingWeekEventChoices = MutableStateFlow<List<WeekEventChoice>?>(null)
    override val pendingWeekEventChoices: StateFlow<List<WeekEventChoice>?> = _pendingWeekEventChoices.asStateFlow()

    override fun setPendingWeekEventChoices(choices: List<WeekEventChoice>?) {
        _pendingWeekEventChoices.value = choices
    }

    override fun closeWeekEventChoiceDialog() {
        setPendingWeekEventChoices(null)
    }

    // ============================================
    // 首页专属状态：2.5D像素场景渲染
    // ============================================
    private val _scene2DState = MutableStateFlow(Scene2DState())
    val scene2DState: StateFlow<Scene2DState> = _scene2DState.asStateFlow()

    init {
        viewModelScope.launch {
            consumptionScore.collect { score ->
                if (_scene2DState.value.viewMode == ViewMode.PIXEL_2D) {
                    updateSceneByConsumptionScore(score.overall.coerceIn(0, 100))
                }
            }
        }
    }

    fun toggleViewMode() {
        val current = _scene2DState.value
        _scene2DState.value = current.copy(
            viewMode = if (current.viewMode == ViewMode.TEXT_NARRATIVE) ViewMode.PIXEL_2D else ViewMode.TEXT_NARRATIVE
        )
        if (_scene2DState.value.viewMode == ViewMode.PIXEL_2D) {
            initializeNpcPositions()
        }
    }

    fun setViewMode(mode: ViewMode) {
        _scene2DState.value = _scene2DState.value.copy(viewMode = mode)
        if (mode == ViewMode.PIXEL_2D) {
            initializeNpcPositions()
        }
    }

    fun movePlayer(dx: Float, dy: Float) {
        val current = _scene2DState.value
        if (current.interactionMenuOpen) return
        val newX = (current.playerPosition.x + dx).coerceIn(80f, 720f)
        val newY = (current.playerPosition.y + dy).coerceIn(100f, 520f)
        val direction = when {
            dy < 0 -> Direction.UP
            dy > 0 -> Direction.DOWN
            dx < 0 -> Direction.LEFT
            dx > 0 -> Direction.RIGHT
            else -> current.playerDirection
        }
        _scene2DState.value = current.copy(
            playerPosition = Offset(newX, newY),
            playerDirection = direction,
            isWalking = dx != 0f || dy != 0f
        )
        checkProximity()
    }

    fun stopWalking() {
        _scene2DState.value = _scene2DState.value.copy(isWalking = false)
    }

    fun setBirthIdentity(identity: BirthIdentity) {
        val current = _scene2DState.value
        _scene2DState.value = current.copy(
            birthIdentity = identity,
            playerPosition = identity.spawnPosition,
            showIdentitySelector = false
        )
        initializeNpcPositions()
        addEventLog("你以【${identity.displayName}】的身份来到小镇")
    }

    fun openIdentitySelector() {
        _scene2DState.value = _scene2DState.value.copy(showIdentitySelector = true)
    }

    fun teleportTo(buildingId: String) {
        val current = _scene2DState.value
        val target = current.buildings.firstOrNull { it.buildingId == buildingId } ?: return
        _scene2DState.value = current.copy(
            playerPosition = target.position.copy(y = target.position.y + 60f),
            isWalking = false
        )
        checkProximity()
        addEventLog("你快步走到了${target.name}")
    }

    fun interactWithNearby() {
        val current = _scene2DState.value
        if (current.interactionMenuOpen) {
            closeInteractionMenu()
            return
        }
        val nearbyBuilding = current.buildings.firstOrNull { building ->
            distance(building.position, current.playerPosition) < 100f
        }
        val nearbyNpc = current.npcs.firstOrNull { npc ->
            distance(npc.position, current.playerPosition) < 80f
        }
        when {
            nearbyBuilding != null -> openBuildingInteraction(nearbyBuilding)
            nearbyNpc != null -> openNpcInteraction(nearbyNpc)
        }
    }

    private fun openBuildingInteraction(building: BuildingScenePosition) {
        _scene2DState.value = _scene2DState.value.copy(
            interactionMenuOpen = true,
            interactionTitle = building.name,
            interactionOptions = SceneInteractionConfig.getBuildingOptions(building.buildingId),
            nearbyBuildingId = building.buildingId,
            isWalking = false
        )
    }

    private fun openNpcInteraction(npc: NpcScenePosition) {
        val options = listOf(
            InteractionOption("npc_chat", "和${npc.name}${SceneInteractionConfig.getOptionLabel("npc_chat")}"),
            InteractionOption("npc_listen", SceneInteractionConfig.getOptionLabel("npc_listen")),
            InteractionOption("npc_leave", SceneInteractionConfig.getOptionLabel("npc_leave"))
        )
        _scene2DState.value = _scene2DState.value.copy(
            interactionMenuOpen = true,
            interactionTitle = npc.name,
            interactionOptions = options,
            nearbyNpcId = npc.npcId,
            isWalking = false
        )
    }

    fun selectInteractionOption(optionId: String) {
        val current = _scene2DState.value
        val npc = current.npcs.firstOrNull { it.npcId == current.nearbyNpcId }
        closeInteractionMenu()
        viewModelScope.launch {
            if (optionId == "chat" && npc != null) {
                val npcType = when {
                    npc.consumptionTendency >= 60 -> SceneInteractionConfig.NpcType.PRACTICAL
                    npc.consumptionTendency <= 40 -> SceneInteractionConfig.NpcType.VANITY
                    else -> SceneInteractionConfig.NpcType.NEUTRAL
                }
                val dialogue = SceneInteractionConfig.getNpcGreeting(
                    npcType = npcType,
                    identity = current.birthIdentity,
                    consumptionScore = consumptionScore.value.overall.toInt()
                )
                addEventLog("你和${npc.name}聊了几句")
                setPetDialogue("${npc.name}：$dialogue")
            } else if (optionId == "listen" && npc != null) {
                addEventLog("你静静地听${npc.name}说话")
                setPetDialogue("${npc.name}对你笑了笑，没说什么，但你感觉很放松。")
            } else {
                val response = SceneInteractionConfig.getResponse(
                    optionId = optionId,
                    identity = current.birthIdentity,
                    consumptionScore = consumptionScore.value.overall.toInt()
                )
                addEventLog(response.eventLog)
                response.petDialogue?.let { setPetDialogue(it) }
            }
        }
    }

    fun closeInteractionMenu() {
        _scene2DState.value = _scene2DState.value.copy(
            interactionMenuOpen = false,
            interactionOptions = emptyList(),
            interactionTitle = ""
        )
    }

    private fun checkProximity() {
        val current = _scene2DState.value
        val playerPos = current.playerPosition
        var nearestBuilding: String? = null
        var nearestNpc: String? = null
        var minBuildingDist = Float.MAX_VALUE
        var minNpcDist = Float.MAX_VALUE

        current.buildings.forEach { building ->
            val d = distance(building.position, playerPos)
            if (d < 100f && d < minBuildingDist) {
                minBuildingDist = d
                nearestBuilding = building.buildingId
            }
        }
        current.npcs.forEach { npc ->
            val d = distance(npc.position, playerPos)
            if (d < 80f && d < minNpcDist) {
                minNpcDist = d
                nearestNpc = npc.npcId
            }
        }
        _scene2DState.value = current.copy(
            nearbyBuildingId = nearestBuilding,
            nearbyNpcId = nearestNpc
        )
    }

    private fun distance(a: Offset, b: Offset): Float {
        val dx = a.x - b.x
        val dy = a.y - b.y
        return kotlin.math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
    }

    private fun initializeNpcPositions() {
        val score = consumptionScore.value.overall.coerceIn(0, 100)
        updateSceneByConsumptionScore(score)
        checkProximity()
    }

    private fun updateSceneByConsumptionScore(score: Int) {
        val npcs = listOf(
            NpcScenePosition(
                npcId = "vegetable_vendor",
                name = "菜摊主",
                position = Offset(570f, 230f),
                consumptionTendency = 75,
                bubbleText = if (score >= 60) "今天的菜很新鲜" else null
            ),
            NpcScenePosition(
                npcId = "doctor",
                name = "社区医生",
                position = Offset(170f, 230f),
                consumptionTendency = 80
            ),
            NpcScenePosition(
                npcId = "shop_clerk",
                name = "名牌店店员",
                position = Offset(620f, 430f),
                consumptionTendency = 25,
                bubbleText = if (score <= 40) "新款限量到货了" else null
            ),
            NpcScenePosition(
                npcId = "elderly",
                name = "散步老人",
                position = Offset(230f, 470f),
                consumptionTendency = 70
            )
        )
        val updatedBuildings = _scene2DState.value.buildings.map { building ->
            when (building.buildingId) {
                "player_home" -> building.copy(
                    consumptionStyle = when {
                        score >= 60 -> ConsumptionStyle.PEOPLE_ORIENTED
                        score <= 40 -> ConsumptionStyle.VANITY_ORIENTED
                        else -> ConsumptionStyle.NEUTRAL
                    }
                )
                else -> building
            }
        }
        _scene2DState.value = _scene2DState.value.copy(
            npcs = npcs,
            buildings = updatedBuildings
        )
    }

    class Factory(private val gameSaveRepository: GameSaveRepository? = null) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(gameSaveRepository) as T
        }
    }
}
