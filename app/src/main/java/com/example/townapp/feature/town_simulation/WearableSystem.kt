package com.example.townapp.feature.town_simulation

import com.example.townapp.data.microevent.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 穿戴系统（先体验后告知）
 * 
 * 核心原则：
 * 1. 详情页只写最客观的物理事实
 * 2. 只有点击「穿戴」「使用」后，3秒内才弹出导航式提示
 * 3. 永远不给用户提前预判的机会
 * 4. 让用户自己亲手去试
 */

class WearableSystem(
    private val microEventEngine: MicroEventEngine
) {
    
    // ============================================
    // 状态
    // ============================================
    
    private val _currentTip = MutableStateFlow<NavigationTip?>(null)
    val currentTip: StateFlow<NavigationTip?> = _currentTip.asStateFlow()
    
    private val _isTipShowing = MutableStateFlow(false)
    val isTipShowing: StateFlow<Boolean> = _isTipShowing.asStateFlow()
    
    private val _wearingState = MutableStateFlow(WearableState())
    val wearingState: StateFlow<WearableState> = _wearingState.asStateFlow()
    
    private var tipJob: Job? = null
    private var systemScope: CoroutineScope? = null
    
    // 延迟时间（3秒）
    private val tipDelayMs = 3000L
    private val tipDisplayDurationMs = 6000L
    
    // ============================================
    // 生命周期
    // ============================================
    
    fun start(scope: CoroutineScope) {
        systemScope = scope
        StructuredLogger.i("WearableSystem", "穿戴系统启动 - 先体验后告知")
    }
    
    fun stop() {
        tipJob?.cancel()
        systemScope = null
        StructuredLogger.i("WearableSystem", "穿戴系统停止")
    }
    
    // ============================================
    // 穿戴操作
    // ============================================
    
    /**
     * 穿戴物品
     * 核心逻辑：
     * 1. 先穿上，更新状态
     * 2. 延迟3秒，模拟真实体验的延迟
     * 3. 生成导航式提示，绝对不提前说
     */
    fun wearItem(item: WearableItem) {
        // 1. 更新穿戴状态
        updateWearingState(item)
        
        // 2. 延迟3秒后生成提示
        tipJob?.cancel()
        tipJob = systemScope?.launch(Dispatchers.Main) {
            delay(tipDelayMs)
            
            // 3. 生成穿戴后的提示
            val tip = generateWearTip(item)
            if (tip != null) {
                showTip(tip)
            }
        }
        
        StructuredLogger.d("WearableSystem", "穿戴物品: ${item.name}")
    }
    
    /**
     * 脱下物品
     */
    fun removeItem(slot: WearSlot) {
        updateWearingState { state ->
            when (slot) {
                WearSlot.CLOTHING -> state.copy(
                    currentClothing = null,
                    wornItems = state.wornItems.filter { it != "clothing" }
                )
                WearSlot.SHOES -> state.copy(
                    currentShoes = null,
                    wornItems = state.wornItems.filter { it != "shoes" }
                )
                WearSlot.ACCESSORY -> state.copy(
                    currentAccessory = null,
                    wornItems = state.wornItems.filter { it != "accessory" }
                )
            }
        }
        
        StructuredLogger.d("WearableSystem", "脱下物品: ${slot.name}")
    }
    
    // ============================================
    // 提示生成
    // ============================================
    
    /**
     * 生成穿戴后的提示
     * 
     * 规则：
     * - 只描述客观感受
     * - 语气软萌，不用警告语气
     * - 提供建议，不是命令
     */
    private fun generateWearTip(item: WearableItem): NavigationTip? {
        // 根据材质生成提示
        return when {
            // 化纤/聚酯纤维
            item.material.contains("聚酯纤维") || 
            item.material.contains("化纤") ||
            item.material.contains("涤纶") -> {
                NavigationTip(
                    id = "tip_${item.id}_${System.currentTimeMillis()}",
                    character = TownCharacter.DORO,
                    title = "检测到皮肤轻微发痒🥺",
                    content = "建议换成纯棉材质，会舒服很多哦～",
                    actionText = "知道了"
                )
            }
            
            // 羊毛
            item.material.contains("羊毛") ||
            item.material.contains("羊绒") -> {
                NavigationTip(
                    id = "tip_${item.id}_${System.currentTimeMillis()}",
                    character = TownCharacter.DORO,
                    title = "检测到皮肤有点扎🥺",
                    content = "建议里面穿一件打底衫，会舒服很多呢",
                    actionText = "知道了"
                )
            }
            
            // 高跟鞋
            item.material.contains("高跟鞋") ||
            (item.category.contains("鞋") && item.height > 5) -> {
                NavigationTip(
                    id = "tip_${item.id}_${System.currentTimeMillis()}",
                    character = TownCharacter.GUGAGA,
                    title = "检测到脚部压力过大咕咕🦶",
                    content = "建议不要走超过30分钟哦，不然脚会痛的咕咕",
                    actionText = "知道了"
                )
            }
            
            // 皂基洗面奶
            item.material.contains("皂基") ||
            item.material.contains("皂化") -> {
                NavigationTip(
                    id = "tip_${item.id}_${System.currentTimeMillis()}",
                    character = TownCharacter.TAFFI,
                    title = "检测到皮肤有点干喵😺",
                    content = "记得涂一点保湿霜哦，不然脸会干干的喵",
                    actionText = "知道了"
                )
            }
            
            // 紧身衣物
            item.fit == "紧身" || item.fit == "修身" -> {
                NavigationTip(
                    id = "tip_${item.id}_${System.currentTimeMillis()}",
                    character = TownCharacter.DORO,
                    title = "检测到有点紧🥺",
                    content = "穿久了可能会有点不舒服，建议多活动活动",
                    actionText = "知道了"
                )
            }
            
            // 纯棉（正面提示）
            item.material.contains("纯棉") ||
            item.material.contains("100%棉") -> {
                NavigationTip(
                    id = "tip_${item.id}_${System.currentTimeMillis()}",
                    character = TownCharacter.TAFFI,
                    title = "哇！好舒服喵😺",
                    content = "纯棉的材质穿着就是舒服呢～皮肤也不会发痒喵",
                    actionText = "好棒"
                )
            }
            
            // 宽松版型（正面提示）
            item.fit == "宽松" || item.fit == "oversize" -> {
                NavigationTip(
                    id = "tip_${item.id}_${System.currentTimeMillis()}",
                    character = TownCharacter.GUGAGA,
                    title = "好宽松好舒服咕咕💛",
                    content = "宽松的衣服穿着就是舒服呀，怎么动都不会勒咕咕",
                    actionText = "好棒"
                )
            }
            
            // 没有特殊提示
            else -> null
        }
    }
    
    /**
     * 显示提示
     */
    private suspend fun showTip(tip: NavigationTip) {
        _currentTip.value = tip
        _isTipShowing.value = true
        
        StructuredLogger.d("WearableSystem", "显示穿戴提示: ${tip.title}")
        
        // 延迟后自动隐藏
        delay(tipDisplayDurationMs)
        hideTip()
    }
    
    /**
     * 隐藏提示
     */
    private suspend fun hideTip() {
        _isTipShowing.value = false
        delay(300)
        _currentTip.value = null
    }
    
    /**
     * 用户关闭提示
     */
    suspend fun dismissTip() {
        hideTip()
    }
    
    // ============================================
    // 状态更新
    // ============================================
    
    private fun updateWearingState(item: WearableItem) {
        _wearingState.value = when (item.category) {
            "衣物", "上衣", "裤子", "裙子" -> _wearingState.value.copy(
                currentClothing = item.name,
                wornItems = _wearingState.value.wornItems + "clothing"
            )
            "鞋子", "袜子" -> _wearingState.value.copy(
                currentShoes = item.name,
                wornItems = _wearingState.value.wornItems + "shoes"
            )
            else -> _wearingState.value.copy(
                currentAccessory = item.name,
                wornItems = _wearingState.value.wornItems + "accessory"
            )
        }
    }
    
    private fun updateWearingState(update: (WearableState) -> WearableState) {
        _wearingState.value = update(_wearingState.value)
    }
}

// ============================================
// 数据类
// ============================================

/**
 * 穿戴物品
 */
data class WearableItem(
    val id: String,
    val name: String,
    val category: String,      // 衣物/鞋子/配件
    val material: String,     // 材质：聚酯纤维/纯棉/羊毛等
    val size: String = "",    // 尺码：S/M/L
    val color: String = "",   // 颜色
    val price: Double = 0.0, // 价格
    val fit: String = "",    // 版型：宽松/紧身/正常
    val height: Double = 0.0  // 高度（鞋子用，单位cm）
)

/**
 * 穿戴槽位
 */
enum class WearSlot {
    CLOTHING,
    SHOES,
    ACCESSORY
}
