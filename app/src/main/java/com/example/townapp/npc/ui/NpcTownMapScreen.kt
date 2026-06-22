package com.example.townapp.npc.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.townapp.npc.model.CognitionState
import com.example.townapp.npc.model.NpcDisplayVo
import com.example.townapp.npc.model.NpcTimelineVo
import com.example.townapp.npc.model.TownNarrativeState
import com.example.townapp.npc.viewmodel.NpcViewModel
import com.example.townapp.ui.components.AtmosphereOverlay
import com.example.townapp.ui.components.FirstTimeGuide
import com.example.townapp.ui.components.FloatingExpandMenu
import com.example.townapp.ui.components.MenuItem
import com.example.townapp.ui.components.MemoryFragmentDialog
import com.example.townapp.ui.components.MiniStatusPanel
import com.example.townapp.ui.components.NpcDialogBox
import com.example.townapp.ui.components.NpcFloatingThoughts
import com.example.townapp.ui.components.NpcMoodOverlay
import com.example.townapp.ui.modifier.slowZoomIn
import com.example.townapp.ui.modifier.vignetteEffect

/**
 * 像素小镇 NPC 地图主屏幕（极简改造版）
 *
 * 核心玩法：
 * 1. 两条路线完全平等：静默观察者（路线A）vs 短暂相交亲历者（路线B）
 * 2. 时间不可逆：年份自动迭代，错过的人生永久无法回溯
 * 3. 认知觉醒：积累认知碎片，解锁更客观的对话视角
 *
 * 严格遵循小镇项目铁律：
 * 1. 全屏 Canvas 优先，无任何平铺常驻按钮
 * 2. 所有常驻 UI 改为可折叠悬浮控件（右上角 + 右下角）
 * 3. 弹窗按需渲染，关闭即从 Compose 树销毁
 * 4. 画面 95% 留给像素地图，UI 极简克制
 * 5. UI 零底层耦合：仅依赖 ViewModel，无文件 IO、无 Dao
 */
@Composable
fun NpcTownMapScreen(
    viewModel: NpcViewModel = viewModel()
) {
    val npcList by viewModel.npcList.collectAsState()
    val selectedNpc by viewModel.selectedNpc.collectAsState()
    val currentMemory by viewModel.currentMemory.collectAsState()
    val currentSeasonId by viewModel.currentSeasonId.collectAsState()
    val currentYear by viewModel.currentYear.collectAsState()
    val currentPlayerAge by viewModel.currentPlayerAge.collectAsState()
    val currentHour by viewModel.currentHour.collectAsState()
    val route by viewModel.route.collectAsState()
    val currentMoodPalette by viewModel.currentMoodPalette.collectAsState()

    // 首次进入引导状态（仅首次显示）
    var showFirstTimeGuide by remember { mutableStateOf(true) }

    // ================== 首次进入引导 ==================
    if (showFirstTimeGuide) {
        FirstTimeGuide(
            onDismiss = { showFirstTimeGuide = false }
        )
        return
    }

    // ================== 路由分发（极简） ==================
    when (route) {
        "archive" -> {
            NpcArchiveScreen(
                repository = viewModel.repository,
                onSelectNpc = { npc -> viewModel.viewNpcTimeline(npc.npcId) },
                onBack = { viewModel.backToMap() }
            )
            return
        }
        "timeline" -> {
            viewModel.currentTimeline.value?.let { timeline ->
                NpcTimelineScreen(
                    timeline = timeline,
                    onBack = { viewModel.backToMap() },
                    onViewMemory = { markId -> viewModel.triggerMemory(markId) }
                )
            } ?: run {
                viewModel.backToMap()
            }
            return
        }
        "settings" -> {
            SettingsScreenPlaceholder(onBack = { viewModel.backToMap() })
            return
        }
        "textMode" -> {
            TextModeScreenPlaceholder(onBack = { viewModel.backToMap() })
            return
        }
        else -> { /* 地图页继续渲染 */ }
    }

    // ================== 主画面：Box 分层（自底向上） ==================
    Box(modifier = Modifier.fillMaxSize()) {

        // 第一层：全屏像素地图画布（占满 100% 屏幕）
        // 应用剧情运镜：选中 NPC 或显示回忆时缓慢推镜
        // 应用暗角：玩家进入晚年（年龄>=60）时自动启用
        PixelMapCanvas(
            npcs = npcList,
            onNpcClick = { viewModel.selectNpc(it) },
            modifier = Modifier
                .fillMaxSize()
                .slowZoomIn(enabled = selectedNpc != null || currentMemory != null)
                .vignetteEffect(strength = (currentPlayerAge.coerceAtLeast(60) - 60) / 30f * 0.6f)
        )

        // 第二层：昼夜/四季氛围遮罩（画布之上、悬浮 UI 之下，不拦截触摸）
        AtmosphereOverlay(
            seasonId = currentSeasonId,
            currentHour = currentHour
        )

        // 第三层：NPC 专属人物氛围滤镜（选中 NPC 时激活，关闭即消失）
        NpcMoodOverlay(
            palette = currentMoodPalette,
            visible = selectedNpc != null
        )

        // 第四层：NPC 漂浮独白（极低透明度，静默观光模式下隐藏）
        val hideAllUI by TownNarrativeState.hideAllUI.collectAsState()
        val silentMode by TownNarrativeState.silentTourMode.collectAsState()

        if (!hideAllUI) {
            // 仅在非全域隐藏模式下显示漂浮独白
            NpcFloatingThoughts(
                thoughts = viewModel.getFloatingThoughts(),
                modifier = Modifier.fillMaxSize()
            )

            // 第五层：右上角可折叠迷你状态面板（默认折叠为小点）
            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                MiniStatusPanel(
                    seasonId = currentSeasonId,
                    year = currentYear,
                    playerAge = currentPlayerAge,
                    health = 100,
                    energy = 80,
                    onHideUI = { TownNarrativeState.toggleHideAllUI() },
                    hideAllUIActive = hideAllUI
                )
            }

            // 第六层：右下角圆形折叠菜单（默认小圆点）
            Box(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) {
                FloatingExpandMenu(
                    menuItems = listOf(
                        MenuItem(
                            label = "纪念馆",
                            icon = Icons.Filled.Home,
                            onClick = { viewModel.openArchive() }
                        ),
                        MenuItem(
                            label = "设置",
                            icon = Icons.Filled.Settings,
                            onClick = { viewModel.openSettings() }
                        ),
                        MenuItem(
                            label = "文字模式",
                            icon = Icons.Filled.Edit,
                            onClick = { viewModel.openTextMode() }
                        ),
                        MenuItem(
                            label = "静默观光",
                            icon = Icons.Filled.KeyboardArrowDown,
                            onClick = { TownNarrativeState.toggleSilentTourMode() },
                            isToggle = true,
                            toggleState = silentMode,
                            activeIcon = Icons.Filled.KeyboardArrowUp
                        ),
                        MenuItem(
                            label = "全域隐藏",
                            icon = Icons.Filled.KeyboardArrowDown,
                            onClick = { TownNarrativeState.toggleHideAllUI() },
                            isToggle = true,
                            toggleState = hideAllUI,
                            activeIcon = Icons.Filled.KeyboardArrowUp
                        )
                    )
                )
            }
        }

        // 全域隐藏 UI 模式下：顶部淡金色提示
        if (hideAllUI) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            ) {
                androidx.compose.material3.Surface(
                    color = Color.Black.copy(alpha = 0.4f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                ) {
                    androidx.compose.material3.Text(
                        text = "全域隐藏：点击任意处恢复 UI",
                        color = Color(0xFFFFD700).copy(alpha = 0.6f),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // 第七层：弹窗（按需渲染，关闭即销毁）
        selectedNpc?.let { npc ->
            NpcDialogBox(
                npc = npc,
                palette = currentMoodPalette,
                onClose = { viewModel.closeDialog() },
                onNextDialog = { viewModel.talkToNpc(npc.npcId) }
            )
        }

        currentMemory?.let { memory ->
            MemoryFragmentDialog(
                memory = memory,
                onDismiss = { viewModel.closeMemory() }
            )
        }

        // 全域隐藏 UI 模式下：点击任意处恢复 UI
        if (hideAllUI) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { TownNarrativeState.setHideAllUI(false) }
            )
        }
    }
}

/**
 * 像素地图画布（全屏占满，绘制 NPC 位置）
 *
 * 极简实现：仅绘制 NPC 占位点 + 像素地图背景
 * 后续可扩展：远中近景分层、NPC 帧动画、光影径向光晕
 */
@Composable
fun PixelMapCanvas(
    npcs: List<NpcDisplayVo>,
    onNpcClick: (NpcDisplayVo) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color(0xFF87CEEB))  // 天空蓝背景占位
    ) {
        // 像素地图层（暂时用纯色背景，后续接精灵图）
        Canvas(modifier = Modifier.fillMaxSize()) {
            // 绘制地面色带
            drawRect(
                color = Color(0xFF8B7355),
                topLeft = Offset(0f, size.height * 0.6f),
                size = androidx.compose.ui.geometry.Size(size.width, size.height * 0.4f)
            )
        }

        // NPC 点击层（覆盖在画布之上）
        npcs.forEach { npc ->
            Box(
                modifier = Modifier
                    .offset(x = npc.x.dp, y = npc.y.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.3f))
                    .clickable { onNpcClick(npc) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = npc.name.take(1),
                    color = Color.White
                )
            }
        }

        // 中心提示文字（首次进入时显示）
        if (npcs.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "小镇正在苏醒...",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// ================== 占位页面（后续接入真实页面） ==================

@Composable
private fun ArchiveScreenPlaceholder(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF1A1A1A))) {
        Text("纪念馆页面占位", color = Color.White, modifier = Modifier.align(Alignment.Center))
        TextButton(onClick = onBack, modifier = Modifier.align(Alignment.TopStart)) {
            Text("← 返回", color = Color.White)
        }
    }
}

@Composable
private fun SettingsScreenPlaceholder(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF1A1A1A))) {
        Text("设置页面占位", color = Color.White, modifier = Modifier.align(Alignment.Center))
        TextButton(onClick = onBack, modifier = Modifier.align(Alignment.TopStart)) {
            Text("← 返回", color = Color.White)
        }
    }
}

@Composable
private fun TextModeScreenPlaceholder(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF1A1A1A))) {
        Text("文字模式页面占位", color = Color.White, modifier = Modifier.align(Alignment.Center))
        TextButton(onClick = onBack, modifier = Modifier.align(Alignment.TopStart)) {
            Text("← 返回", color = Color.White)
        }
    }
}
