package com.example.townapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.repository.DataIntegrationManager
import com.example.townapp.data.repository.UnifiedFoodRepository

/**
 * 🌟 数据查看器 - 在模拟过程中查看和使用所有数据
 * 
 * 功能：
 * - 查看所有食物数据
 * - 查看所有空间数据
 * - 查看所有事件数据
 * - 查看所有成语数据
 * - 查看所有认知数据
 * - 查看所有交通工具
 * - 查看所有服装分类
 * - 直接使用数据到当前模拟
 */
@Composable
fun DataViewerScreen(
    onBack: () -> Unit,
    onUseFood: (foodId: Int) -> Unit,
    onUseSpace: (spaceId: String) -> Unit
) {
    var selectedTab by remember { mutableStateOf(DataTab.FOOD) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("数据查看器") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                // 标签切换
                TabRow(selectedTabIndex = selectedTab.ordinal) {
                    DataTab.values().forEach { tab ->
                        Tab(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            icon = { Icon(tab.icon, contentDescription = tab.name) },
                            text = { Text(tab.label) }
                        )
                    }
                }
                
                // 内容区域
                Box(modifier = Modifier.fillMaxSize()) {
                    when (selectedTab) {
                        DataTab.FOOD -> FoodDataView(onUse = onUseFood)
                        DataTab.SPACE -> SpaceDataView(onUse = onUseSpace)
                        DataTab.EVENT -> EventDataView()
                        DataTab.IDIOM -> IdiomDataView()
                        DataTab.COGNITION -> CognitionDataView()
                        DataTab.TRANSPORT -> TransportDataView()
                        DataTab.CLOTHING -> ClothingDataView()
                    }
                }
            }
        }
    )
}

/** 数据标签枚举 */
enum class DataTab(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    FOOD("食物", Icons.Default.ShoppingCart),
    SPACE("空间", Icons.Default.Home),
    EVENT("事件", Icons.Default.Warning),
    IDIOM("成语", Icons.Default.Face),
    COGNITION("认知", Icons.Default.Favorite),
    TRANSPORT("交通", Icons.Default.Refresh),
    CLOTHING("服装", Icons.Default.Person)
}

/** 食物数据视图 */
@Composable
fun FoodDataView(onUse: (Int) -> Unit) {
    val foods = UnifiedFoodRepository.getAllFoods()
    
    LazyColumn {
        items(foods) { food ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onUse(food.id) },
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = food.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(text = "分类: ${food.category}", fontSize = 14.sp, color = Color.Gray)
                        Text(text = "价格: ¥${String.format("%.2f", food.pricePer100g)}/100g", fontSize = 14.sp)
                    }
                    Icon(Icons.Default.ArrowForward, contentDescription = "使用")
                }
            }
        }
    }
}

/** 空间数据视图 */
@Composable
fun SpaceDataView(onUse: (String) -> Unit) {
    val spaces = com.example.townapp.data.repository.SpaceConfigRepository.getAllSpaces()
    
    LazyColumn {
        items(spaces) { space ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onUse(space.id) },
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = space.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(text = "价格: ¥${space.price}/月", fontSize = 14.sp)
                        Text(text = "面积: ${space.area}㎡", fontSize = 14.sp, color = Color.Gray)
                    }
                    Icon(Icons.Default.ArrowForward, contentDescription = "使用")
                }
            }
        }
    }
}

/** 事件数据视图 */
@Composable
fun EventDataView() {
    val events = com.example.townapp.data.repository.GameEventRepository.getAllEvents()
    
    LazyColumn {
        items(events) { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = event.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            text = event.category,
                            fontSize = 12.sp,
                            color = when (event.category) {
                                "health_event" -> Color.Red
                                "mental_event" -> Color.Blue
                                else -> Color.Gray
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = event.description, fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }
}

/** 成语数据视图 */
@Composable
fun IdiomDataView() {
    var idioms by remember { mutableStateOf<List<com.example.townapp.data.idiom.IdiomCritique>>(emptyList()) }
    
    LaunchedEffect(Unit) {
        val allIdioms = com.example.townapp.data.idiom.IdiomCritiqueLibrary.getAllIdioms()
        idioms = allIdioms
    }
    
    LazyColumn {
        items(idioms) { idiom ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = idiom.idiom, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "释义: ${idiom.traditionalMeaning}", fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "小镇解读: ${idiom.townPerspective}", fontSize = 14.sp)
                }
            }
        }
    }
}

/** 认知数据视图 */
@Composable
fun CognitionDataView() {
    var dissections by remember { mutableStateOf<List<com.example.townapp.data.cognition.Dissection>>(emptyList()) }
    
    LaunchedEffect(Unit) {
        dissections = com.example.townapp.data.cognition.getAllDissections()
    }
    
    LazyColumn {
        items(dissections) { dissection ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = dissection.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = dissection.summary, fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }
}

/** 交通工具数据视图 */
@Composable
fun TransportDataView() {
    val vehicles = com.example.townapp.data.getVehicles()
    
    LazyColumn {
        items(vehicles) { vehicle ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = vehicle.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(text = "¥${vehicle.costPerKm}/km", fontSize = 14.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = vehicle.description, fontSize = 14.sp)
                }
            }
        }
    }
}

/** 服装分类数据视图 */
@Composable
fun ClothingDataView() {
    val categories = DataIntegrationManager.getAllClothingCategories()
    
    LazyColumn {
        items(categories) { category ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = category.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "季节: ${category.season}", fontSize = 14.sp, color = Color.Gray)
                    Text(text = "风格: ${category.style}", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }
}
