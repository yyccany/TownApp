package com.example.townapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.townapp.ui.theme.AppColors
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.townapp.business.BodyStateBusiness
import com.example.townapp.business.EventEngine
import com.example.townapp.business.NutritionRiskCache
import com.example.townapp.data.SampleProducts
import com.example.townapp.data.database.TownDatabase
import com.example.townapp.data.database.dao.ProductDao
import com.example.townapp.data.database.entity.ProductEntity
import com.example.townapp.data.repository.GameSaveRepository
import com.example.townapp.performance.PerformanceManager
import com.example.townapp.ui.components.TownBottomNavigation
import com.example.townapp.ui.screens.LifeArchiveScreen
import com.example.townapp.ui.screens.SettingsScreen
import com.example.townapp.ui.screens.DocumentScreen
import com.example.townapp.ui.screens.ResidentScreen
import com.example.townapp.ui.screens.SimulationScreen
import com.example.townapp.ui.screens.DataViewerScreen
import com.example.townapp.ui.viewmodel.TownViewModel
import com.example.townapp.ui.cognition.CognitionAwakeningScreen
import com.example.townapp.ui.theme.TownTheme
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.townapp.business.StructuredLogger
import com.example.townapp.business.ExceptionHandler
import com.example.townapp.data.repository.DataIntegrationManager
import com.example.townapp.data.repository.SimulationDataSwitch

class MainActivity : ComponentActivity() {
    private lateinit var database: TownDatabase
    private lateinit var eventEngine: EventEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 设置沉浸式状态栏
        setupImmersiveStatusBar()
        
        try {
            database = TownDatabase.getDatabase(this)
            eventEngine = EventEngine(database.lifeEventLogDao())
            
            // 开启模拟数据开关，让空间、事件等数据真正生效
            SimulationDataSwitch.enableAll()
            
            // 初始化所有数据，确保每个数据文件都被调用
            DataIntegrationManager.initialize()
            
            setContent {
                TownTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        TownApp(database)
                    }
                }
            }
            
            initializeApp()
        } catch (e: Exception) {
            Log.e("MainActivity", "初始化失败", e)
            setContent {
                TownTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        ErrorScreen(error = e.message ?: "初始化失败")
                    }
                }
            }
        }
    }

    private fun initializeApp() {
        PerformanceManager.initialize(this)
        
        val startTime = StructuredLogger.start("initializeApp")
        
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val dbStartTime = StructuredLogger.start("db.init")
                val nutritionCount = database.foodNutritionDao().count()
                if (nutritionCount == 0) {
                    StructuredLogger.i("MainActivity", "Initializing nutrition and risk data...")
                    val seeds = SeedData.all()
                    database.foodNutritionDao().insertAll(seeds.first)
                    database.foodRiskDao().insertAll(seeds.second)
                    StructuredLogger.i("MainActivity", "Nutrition data: ${seeds.first.size} items, Risk data: ${seeds.second.size} items")
                }
                NutritionRiskCache.init(database.foodNutritionDao(), database.foodRiskDao())
                StructuredLogger.logDatabase("MainActivity", "nutrition_data_init", StructuredLogger.end("db.init", dbStartTime), nutritionCount > 0)
                
                val productStartTime = StructuredLogger.start("product.init")
                val products = database.productDao().getAllForAdult()
                if (products.isEmpty()) {
                    StructuredLogger.i("MainActivity", "Initializing sample product data...")
                    database.materialDao().insertAll(SampleProducts.getMaterials())
                    database.productDao().insertAll(SampleProducts.getProducts())
                    database.formulaDao().insertAll(SampleProducts.getFormulas())
                    StructuredLogger.i("MainActivity", "Product data initialized, total ${SampleProducts.getProducts().size} items")
                }
                StructuredLogger.logDatabase("MainActivity", "product_data_init", StructuredLogger.end("product.init", productStartTime), products.isNotEmpty())
                
                initializeCoreBusiness()
                
                StructuredLogger.i("MainActivity", "All data initialization completed")
                StructuredLogger.logPerformance("MainActivity", "initializeApp", StructuredLogger.end("initializeApp", startTime))
            } catch (e: Exception) {
                ExceptionHandler.handleDatabaseException("MainActivity", "应用初始化", e)
                throw e
            }
        }
        
        BodyStateBusiness.startLongTermAccumulation(database.userBodyStateDao())
    }
    
    private suspend fun initializeCoreBusiness() {
        try {
            val existingUser = database.userDao().getUser()
            if (existingUser == null) {
                database.userDao().insertUser(
                    com.example.townapp.data.database.entity.UserEntity(
                        id = 1,
                        name = "小镇居民",
                        level = 1,
                        experience = 0,
                        currency = 10000.0
                    )
                )
            }

            val existingNpcs = database.npcDao().getAllNpcs()
            if (existingNpcs.isEmpty()) {
                val npcs = listOf(
                    com.example.townapp.data.database.entity.NpcEntity(1, "taffi", "塔菲", "小镇管家", "🧚", 0, true),
                    com.example.townapp.data.database.entity.NpcEntity(2, "doro", "朵朵", "生活助手", "🐱", 0, true),
                    com.example.townapp.data.database.entity.NpcEntity(3, "gugaga", "咕咕鸽", "财务顾问", "🦉", 0, false),
                    com.example.townapp.data.database.entity.NpcEntity(4, "momoyo", "若叶睦", "知识导师", "📚", 0, false)
                )
                npcs.forEach { database.npcDao().insertNpc(it) }
            }

            val existingBuildings = database.buildingDao().getAllBuildings()
            if (existingBuildings.isEmpty()) {
                val buildings = listOf(
                    com.example.townapp.data.database.entity.BuildingEntity(
                        id = 1, buildingId = "home", name = "温馨小屋", category = "居住",
                        district = "🏠", level = 1, count = 1, cost = 0.0,
                        description = "", daysWithoutTrigger = 0, isActive = true
                    ),
                    com.example.townapp.data.database.entity.BuildingEntity(
                        id = 2, buildingId = "market", name = "市场", category = "购物",
                        district = "🛒", level = 1, count = 1, cost = 0.0,
                        description = "", daysWithoutTrigger = 0, isActive = true
                    ),
                    com.example.townapp.data.database.entity.BuildingEntity(
                        id = 3, buildingId = "park", name = "公园", category = "休闲",
                        district = "🌳", level = 1, count = 1, cost = 0.0,
                        description = "", daysWithoutTrigger = 0, isActive = true
                    ),
                    com.example.townapp.data.database.entity.BuildingEntity(
                        id = 4, buildingId = "hospital", name = "医院", category = "医疗",
                        district = "🏥", level = 1, count = 1, cost = 0.0,
                        description = "", daysWithoutTrigger = 0, isActive = true
                    ),
                    com.example.townapp.data.database.entity.BuildingEntity(
                        id = 5, buildingId = "school", name = "学校", category = "教育",
                        district = "🏫", level = 1, count = 1, cost = 0.0,
                        description = "", daysWithoutTrigger = 0, isActive = true
                    )
                )
                buildings.forEach { database.buildingDao().insertBuilding(it) }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "核心业务数据初始化失败", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BodyStateBusiness.stopLongTermAccumulation()
        PerformanceManager.shutdown()
    }
}

class ProductViewModel(private val productDao: ProductDao) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductEntity>>(emptyList())
    val products: StateFlow<List<ProductEntity>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = withContext(Dispatchers.IO) {
                    productDao.getAllForAdult()
                }
                _products.value = result.sortedBy { it.name.lowercase() }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "加载失败", e)
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

@Composable
fun TownApp(database: TownDatabase) {
    val productViewModel = remember { ProductViewModel(database.productDao()) }
    val context = LocalContext.current
    val gameSaveRepository = remember { GameSaveRepository(context) }
    val townViewModel = remember { TownViewModel.Factory(gameSaveRepository).create(TownViewModel::class.java) }

    // 尝试读取本地存档，无存档则保持默认初始状态
    LaunchedEffect(Unit) {
        townViewModel.tryLoadGame()
    }

    val isLoading by productViewModel.isLoading.collectAsState()

    var selectedProduct by remember { mutableStateOf<ProductEntity?>(null) }
    var currentTab by remember { mutableStateOf("town") }

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
    }

    Scaffold(
        bottomBar = {
            TownBottomNavigation(
                currentTab = currentTab,
                onTabChange = { currentTab = it }
            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 0.dp)
                .statusBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFF9EC))
            ) {
                when {
                    selectedProduct != null -> {
                        com.example.townapp.ui.components.ProductDetailPanel(
                            product = selectedProduct!!,
                            isChildMode = false,
                            onClose = { selectedProduct = null }
                        )
                    }
                    isLoading -> {
                        LoadingIndicator()
                    }
                    else -> {
                        when (currentTab) {
                            "town" -> {
                                com.example.townapp.ui.screens.TownHomeScreen(
                                    viewModel = townViewModel,
                                    onNavigate = { route ->
                                        when {
                                            route == "resident" -> currentTab = "resident"
                                            route == "cognitive" -> currentTab = "cognitive"
                                            route == "food" -> currentTab = "food"
                                            else -> {}
                                        }
                                    }
                                )
                            }
                            "resident" -> {
                                ResidentScreen(
                                    viewModel = townViewModel,
                                    onBack = { currentTab = "town" }
                                )
                            }
                            "cognitive" -> {
                                CognitionAwakeningScreen(
                                    onBack = { currentTab = "town" }
                                )
                            }
                            "food" -> {
                                com.example.townapp.ui.screens.FoodAppreciationScreen(
                                    onBack = { currentTab = "town" }
                                )
                            }
                            "simulate" -> {
                                SimulationScreen(
                                    onNavigateToFoodList = { currentTab = "food" },
                                    onNavigateToDataViewer = { currentTab = "dataviewer" }
                                )
                            }
                            "dataviewer" -> {
                                DataViewerScreen(
                                    onBack = { currentTab = "simulate" },
                                    onUseFood = { foodId ->
                                        com.example.townapp.domain.engine.SimulationEngine.eat(foodId)
                                        currentTab = "simulate"
                                    },
                                    onUseSpace = { spaceId ->
                                        com.example.townapp.domain.engine.SimulationEngine.changeSpace(spaceId)
                                        currentTab = "simulate"
                                    }
                                )
                            }
                            "settings" -> {
                                SettingsScreen(
                                    viewModel = townViewModel,
                                    onBack = { currentTab = "town" },
                                    onNavigateToArchive = { currentTab = "archive" },
                                    onNavigateToDocument = { currentTab = "document" }
                                )
                            }
                            "document" -> {
                                DocumentScreen()
                            }
                            "archive" -> {
                                LifeArchiveScreen(
                                    archives = emptyList(),
                                    onCreateArchive = { _, _, _ -> },
                                    onUpdateArchive = { _, _, _, _ -> },
                                    onDeleteArchive = { },
                                    onBack = { currentTab = "settings" }
                                )
                            }
                            else -> {
                                OtherTabContent(tab = currentTab)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize().background(AppColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "🌱",
                fontSize = 48.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "正在加载...",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.TextPrimary
            )
        }
    }
}
@Composable
fun OtherTabContent(tab: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = getTabEmoji(tab),
                fontSize = 48.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = getTabTitle(tab),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.TextPrimary
            )
            Text(
                text = "正在建设中...",
                fontSize = 14.sp,
                color = AppColors.PrimaryWarm,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

private fun getTabEmoji(tab: String): String {
    return when (tab) {
        "town" -> "🏠"
        "resident" -> "👤"
        "cognitive" -> "💡"
        "settings" -> "⚙️"
        else -> "📱"
    }
}

private fun getTabTitle(tab: String): String {
    return when (tab) {
        "town" -> "小镇"
        "resident" -> "居民"
        "cognitive" -> "认知觉醒"
        "settings" -> "设置"
        else -> tab
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.ErrorBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "❌",
                fontSize = 48.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "出错了",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.ErrorVivid
            )
            Text(
                text = error,
                fontSize = 14.sp,
                color = AppColors.TextSecondary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

// 设置沉浸式状态栏
private fun MainActivity.setupImmersiveStatusBar() {
    // 关闭系统默认安全边距
    WindowCompat.setDecorFitsSystemWindows(window, false)
    
    // 设置状态栏字体为深色（因为背景是浅色）
    val decorView = window.decorView
    val flags = decorView.systemUiVisibility or android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    decorView.systemUiVisibility = flags
}
