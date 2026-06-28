package com.example.townapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.example.townapp.core.theme.LocalVeraColors
import com.example.townapp.core.theme.VeraThemeColors
import com.example.townapp.core.state.UserPreferencesManager
import com.example.townapp.data.idiom.IdiomCritiqueLibrary
import com.example.townapp.ui.components.AppDrawerContent
import com.example.townapp.ui.components.StandardTopBar
import com.example.townapp.ui.screens.*
import com.example.townapp.ui.theme.TownTheme
import com.example.townapp.ui.theme.DictionaryTokens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            IdiomCritiqueLibrary.initialize(this)
        } catch (e: Exception) {
            android.util.Log.e("TownApp", "词条库初始化失败", e)
        }
        val darkTheme = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
        setupImmersiveStatusBar(darkTheme)

        setContent {
            val context = LocalContext.current
            // 读取持久化主题偏好
            val themeMode by UserPreferencesManager.getThemeMode(context)
                .collectAsState(initial = com.example.townapp.core.theme.VeraThemeMode.DefaultMonochrome)
            val veraColors = when (themeMode) {
                com.example.townapp.core.theme.VeraThemeMode.MacaronChild -> VeraThemeColors.macaron()
                else -> VeraThemeColors.monochrome()
            }

            TownTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(LocalVeraColors provides veraColors) {
                        TownApp()
                    }
                }
            }
        }
    }
}

/**
 * 主应用入口
 *
 * 架构改造后：
 * - 无底部导航栏，页面底部统一预留40dp安全留白
 * - 左上角汉堡按钮 → 侧边抽屉收纳低频功能
 * - 顶部统一标准化Header
 */
@Composable
fun TownApp() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // 当前主页面
    var currentScreen by remember { mutableStateOf("home") }

    // 词条详情导航
    var selectedIdiomId by remember { mutableStateOf<String?>(null) }

    // 已读词条集合（点击进入详情后自动标记已读）
    val readIdiomIds = remember { mutableStateListOf<String>() }

    // 搜索面板开关
    var searchOpen by remember { mutableStateOf(false) }

    // 搜索初始关键词（标签点击触发搜索时填充）
    var searchInitialQuery by remember { mutableStateOf("") }

    // 打开抽屉
    val openDrawer: () -> Unit = {
        scope.launch { drawerState.open() }
    }

    // 打开搜索
    val openSearch: () -> Unit = {
        searchInitialQuery = ""
        searchOpen = true
    }

    // 通过标签打开搜索
    val openSearchByTag: (String) -> Unit = { tag ->
        searchInitialQuery = tag
        searchOpen = true
    }

    // 关闭搜索
    val closeSearch: () -> Unit = {
        searchOpen = false
        searchInitialQuery = ""
    }

    // 抽屉菜单点击处理
    val context = LocalContext.current
    val onDrawerItemClick: (String) -> Unit = { itemId ->
        scope.launch { drawerState.close() }
        // 搁置功能仅弹出 Toast，不跳转页面
        if (itemId in listOf("feedback", "settings", "import_export")) {
            android.widget.Toast.makeText(
                context,
                "该功能暂未开放",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        } else {
            currentScreen = itemId
            selectedIdiomId = null
        }
    }

    // 词条点击 → 进入详情（自动标记已读）
    val onNavigateToIdiom: (String) -> Unit = { idiomId ->
        if (!readIdiomIds.contains(idiomId)) {
            readIdiomIds.add(idiomId)
        }
        selectedIdiomId = idiomId
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = MaterialTheme.shapes.small,
                drawerContainerColor = MaterialTheme.colorScheme.background
            ) {
                AppDrawerContent(
                    currentItem = if (currentScreen == "home") "" else currentScreen,
                    onItemClick = onDrawerItemClick
                )
            }
        },
        gesturesEnabled = currentScreen == "home" && selectedIdiomId == null
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (currentScreen) {
                "home" -> {
                    if (selectedIdiomId != null) {
                        // 词条详情页（含主体性工具，统一引擎）
                        IdiomCriticScreen(
                            initialIdiomId = selectedIdiomId,
                            initialCategory = null,
                            onBack = {
                                selectedIdiomId = null
                            },
                            onMenuClick = openDrawer,
                            onSearchClick = { searchOpen = true },
                            onBiasTagSearch = openSearchByTag
                        )
                    } else {
                        // 首页（横向Tab + 全屏词条列表）
                        DictionaryHomeScreen(
                            onNavigateToIdiom = onNavigateToIdiom,
                            onMenuClick = openDrawer,
                            onSearchOpen = openSearch,
                            readIdiomIds = readIdiomIds
                        )
                    }
                }
                "favorites" -> {
                    FavoritesScreen(
                        onBack = { currentScreen = "home" },
                        onIdiomClick = { id ->
                            currentScreen = "home"
                            onNavigateToIdiom(id)
                        }
                    )
                }
                "notes" -> {
                    NotesScreen(
                        onBack = { currentScreen = "home" },
                        onIdiomClick = { id ->
                            currentScreen = "home"
                            onNavigateToIdiom(id)
                        }
                    )
                }
                "settings" -> {
                    SettingsScreen(
                        onBack = { currentScreen = "home" },
                        onMenuClick = openDrawer
                    )
                }
                "about" -> {
                    AboutVeraScreen(
                        onBack = { currentScreen = "home" },
                        onMenuClick = openDrawer
                    )
                }
                else -> {
                    PlaceholderScreen(
                        title = getScreenTitle(currentScreen),
                        onMenuClick = openDrawer,
                        onBack = { currentScreen = "home" }
                    )
                }
            }
        }

        // ====== 搜索面板覆盖层（全页面通用，覆盖在最上层） ======
        if (searchOpen) {
            SearchOverlay(
                initialQuery = searchInitialQuery,
                onClose = closeSearch,
                onIdiomClick = { id ->
                    closeSearch()
                    currentScreen = "home"
                    onNavigateToIdiom(id)
                }
            )
        }
    }
}

@Composable
private fun PlaceholderScreen(
    title: String,
    onMenuClick: () -> Unit,
    onBack: () -> Unit
) {
    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
        StandardTopBar(
            title = title,
            onMenuClick = onBack,
            menuIcon = "back",
            showSearch = false,
            onSearchClick = null
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = DictionaryTokens.textTitle
                )
                Text(
                    text = "正在建设中...",
                    fontSize = 14.sp,
                    color = DictionaryTokens.textCaption,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

private fun getScreenTitle(screen: String): String {
    return when (screen) {
        "favorites" -> "收藏词条"
        "notes" -> "思辨笔记"
        "import_export" -> "词条导入导出"
        "feedback" -> "意见反馈"
        "about" -> "关于 Vera"
        "settings" -> "设置"
        else -> screen
    }
}

// 设置沉浸式状态栏
private fun MainActivity.setupImmersiveStatusBar(darkTheme: Boolean = false) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowCompat.getInsetsController(window, window.decorView)?.let { controller ->
        controller.isAppearanceLightStatusBars = !darkTheme
    }
}
