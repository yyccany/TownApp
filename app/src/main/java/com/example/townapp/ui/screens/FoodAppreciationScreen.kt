package com.example.townapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.feature.food.CuisineFood
import com.example.townapp.feature.food.CuisineFoodRepository
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodAppreciationScreen(
    onBack: () -> Unit
) {
    val foods by remember { mutableStateOf(CuisineFoodRepository.getAllFoods()) }
    var selectedFood by remember { mutableStateOf<CuisineFood?>(null) }

    if (selectedFood != null) {
        FoodDetailScreen(
            food = selectedFood!!,
            onBack = { selectedFood = null }
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("🍜 美食赏析") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Text("←", fontSize = 20.sp)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFF8E7))
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFFFF8E7)),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🍜", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "美食赏析",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5D4E37)
                            )
                            Text(
                                "吃进肚子里的，都值得被认真对待",
                                fontSize = 13.sp,
                                color = Color(0xFF8B7355),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }

                val groupedFoods = foods.groupBy { it.cuisine }
                groupedFoods.forEach { (cuisine, cuisineFoods) ->
                    item {
                        CuisineHeader(cuisine = cuisine, count = cuisineFoods.size)
                    }
                    items(cuisineFoods) { food ->
                        FoodCard(
                            food = food,
                            onClick = { selectedFood = food }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun CuisineHeader(cuisine: String, count: Int) {
    val cuisineColors = mapOf(
        "川菜" to Color(0xFFE53935),
        "鲁菜" to Color(0xFFFF8F00),
        "粤菜" to Color(0xFF43A047),
        "苏菜" to Color(0xFF7CB342),
        "湘菜" to Color(0xFFD32F2F),
        "浙菜" to Color(0xFF1E88E5),
        "闽菜" to Color(0xFF00897B),
        "徽菜" to Color(0xFF6D4C41)
    )
    
    val color = cuisineColors[cuisine] ?: Color(0xFF8B7355)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = cuisine,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5D4E37)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${count}道菜",
            fontSize = 12.sp,
            color = Color(0xFFA0896D)
        )
    }
}

@Composable
fun FoodCard(
    food: CuisineFood,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val riskColor = when (food.healthRisk) {
        "LOW" -> Color(0xFF4CAF50)
        "MEDIUM" -> Color(0xFFFFC107)
        "HIGH" -> Color(0xFFFF9800)
        "EXTREME" -> Color(0xFFF44336)
        else -> Color(0xFF9E9E9E)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                isPressed = true
                onClick()
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF4A3728)
                )
                Text(
                    text = food.province,
                    fontSize = 12.sp,
                    color = Color(0xFFA0896D),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(riskColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = getRiskLabel(food.healthRisk),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = riskColor
                    )
                }
                Text(
                    text = "营养 ${food.nutritionScore.toInt()}",
                    fontSize = 11.sp,
                    color = Color(0xFFA0896D),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Text("→", fontSize = 16.sp, color = Color(0xFFA0896D))
        }
    }
}

fun getRiskLabel(risk: String): String {
    return when (risk) {
        "LOW" -> "健康"
        "MEDIUM" -> "适量"
        "HIGH" -> "少吃"
        "EXTREME" -> "偶尔"
        else -> "未知"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailScreen(
    food: CuisineFood,
    onBack: () -> Unit
) {
    var showContent by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(300)
        showContent = true
    }

    val riskColor = when (food.healthRisk) {
        "LOW" -> Color(0xFF4CAF50)
        "MEDIUM" -> Color(0xFFFFC107)
        "HIGH" -> Color(0xFFFF9800)
        "EXTREME" -> Color(0xFFF44336)
        else -> Color(0xFF9E9E9E)
    }

    val nutritionLevel = when {
        food.nutritionScore >= 40 -> "优秀"
        food.nutritionScore >= 30 -> "良好"
        food.nutritionScore >= 20 -> "一般"
        else -> "较差"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(food.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", fontSize = 20.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = riskColor.copy(alpha = 0.2f))
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFF8E7)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 标题卡片
            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🍜", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                food.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = Color(0xFF4A3728)
                            )
                            Text(
                                "${food.cuisine} · ${food.province}",
                                fontSize = 14.sp,
                                color = Color(0xFFA0896D),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            // 风险评估
            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 100)) + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = riskColor.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "⚠️ 健康风险",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = riskColor
                                )
                                Text(
                                    getRiskLabel(food.healthRisk),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = riskColor
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { (food.nutritionScore.toFloat() / 50f).coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = riskColor,
                                trackColor = riskColor.copy(alpha = 0.2f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "推荐频率：${food.recommendedFrequency}",
                                fontSize = 13.sp,
                                color = Color(0xFF666666)
                            )
                        }
                    }
                }
            }

            // 营养成分
            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 200)) + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "📊 营养成分（每份）",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF2E7D32)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            NutritionRow("油脂", food.oil, "g", food.oil > 80)
                            NutritionRow("盐分", food.salt, "g", food.salt > 5)
                            NutritionRow("糖分", food.sugar, "g", food.sugar > 20)
                            NutritionRow("胆固醇", food.cholesterol, "mg", food.cholesterol > 150)
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "综合营养评分",
                                    fontSize = 13.sp,
                                    color = Color(0xFF5D4037)
                                )
                                Text(
                                    "${food.nutritionScore.toInt()}分 · $nutritionLevel",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = when {
                                        food.nutritionScore >= 40 -> Color(0xFF4CAF50)
                                        food.nutritionScore >= 30 -> Color(0xFF8BC34A)
                                        food.nutritionScore >= 20 -> Color(0xFFFFC107)
                                        else -> Color(0xFFF44336)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // 小镇解读
            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 300)) + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "💡 小镇解读",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFFF57F17)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                food.description,
                                fontSize = 14.sp,
                                color = Color(0xFF5D4037),
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }

            // 实事求是提示
            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 400)) + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "🎯 实事求是",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF1565C0)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            val advice = when (food.healthRisk) {
                                "LOW" -> "这道菜营养均衡，可以经常享用。享受美食的同时，也别忘了多样化饮食哦。"
                                "MEDIUM" -> "这道菜适量食用问题不大。注意控制频率和分量，美食当前也要懂得节制。"
                                "HIGH" -> "这道菜油盐较重，建议偶尔品尝就好。偶尔放纵一下也没关系，重要的是你知道自己在做什么。"
                                "EXTREME" -> "这道菜确实不太健康，但偶尔吃一次也没关系。重要的是你了解它，而不是被它裹挟。"
                                else -> "这道菜的具体情况需要具体分析。"
                            }
                            
                            Text(
                                advice,
                                fontSize = 14.sp,
                                color = Color(0xFF5D4037),
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }

            // 自由选择提示
            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 500)) + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "🌟 自由选择",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF7B1FA2)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "小镇不告诉你「应该吃什么」或「不应该吃什么」。这些数据只是客观事实，选择权始终在你手中。想吃就吃，不想吃就不吃，都是你的自由。",
                                fontSize = 14.sp,
                                color = Color(0xFF5D4037),
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun NutritionRow(name: String, value: Double, unit: String, isHigh: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            fontSize = 13.sp,
            color = Color(0xFF5D4037)
        )
        Text(
            text = "$value$unit",
            fontSize = 13.sp,
            fontWeight = if (isHigh) FontWeight.Bold else FontWeight.Normal,
            color = if (isHigh) Color(0xFFFF5722) else Color(0xFF5D4037)
        )
    }
}
