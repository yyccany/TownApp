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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.townapp.domain.model.simulation.FoodDisplayVo
import com.example.townapp.feature.town_simulation.viewmodel.NutritionAnalysisViewModel
import com.example.townapp.ui.theme.AppDimens
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionAnalysisScreen(
    onBack: () -> Unit
) {
    val viewModel = remember { NutritionAnalysisViewModel() }
    // 生命周期感知订阅数据流（标准模板）
    val foodList by viewModel.foodList.collectAsStateWithLifecycle()
    val selectedFood by viewModel.selectedFood.collectAsStateWithLifecycle()
    val currentCategory by viewModel.currentCategory.collectAsStateWithLifecycle()
    // 页面进入自动加载数据（标准模板）
    LaunchedEffect(Unit) {
        viewModel.loadAllFood()
    }

    if (selectedFood != null) {
        NutritionDetailScreen(
            food = selectedFood!!,
            onBack = { viewModel.clearSelection() }
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("🔬 营养分析") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Text("←", fontSize = 20.sp)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE8F5E9))
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFE8F5E9))
            ) {
                // 分类筛选栏
                CategoryFilterBar(
                    currentCategory = currentCategory,
                    onCategorySelected = { category ->
                        when (category) {
                            null -> viewModel.clearCategory()
                            "低风险" -> viewModel.loadByRiskLevel("LOW")
                            "中风险" -> viewModel.loadByRiskLevel("MEDIUM")
                            "高风险" -> viewModel.loadByRiskLevel("HIGH")
                            "极高风险" -> viewModel.loadByRiskLevel("EXTREME")
                            "智商税" -> viewModel.loadIQTaxFoods()
                            else -> viewModel.loadByCategory(category)
                        }
                    }
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(AppDimens.paddingLarge),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = AppDimens.paddingSmall),
                            shape = RoundedCornerShape(AppDimens.radiusLarge),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("🔬", fontSize = 40.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "小镇食材库",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32)
                                )
                                Text(
                                    "共 ${foodList.size} 种食材 · 营养数据来源于 Room 持久数据库",
                                    fontSize = 12.sp,
                                    color = Color(0xFF81C784),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }

                    items(foodList) { food ->
                        RawFoodCard(
                            food = food,
                            onClick = { viewModel.selectFood(food) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryFilterBar(
    currentCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    val categories = listOf("全部", "低风险", "中风险", "高风险", "极高风险", "智商税")
    val categoryColors = mapOf(
        "全部" to Color(0xFF4CAF50),
        "低风险" to Color(0xFF4CAF50),
        "中风险" to Color(0xFFFF9800),
        "高风险" to Color(0xFFF44336),
        "极高风险" to Color(0xFF9C27B0),
        "智商税" to Color(0xFF795548)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F5E9))
            .padding(horizontal = AppDimens.paddingMedium, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        categories.forEach { category ->
            val isSelected = currentCategory == category || (currentCategory == null && category == "全部")
            val color = categoryColors[category] ?: Color(0xFF4CAF50)

            FilterChip(
                selected = isSelected,
                onClick = {
                    onCategorySelected(if (category == "全部") null else category)
                },
                label = {
                    Text(
                        text = category,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = color.copy(alpha = 0.2f),
                    selectedLabelColor = color
                ),
                modifier = Modifier.height(32.dp)
            )
        }
    }
}

@Composable
fun RawFoodCard(
    food: FoodDisplayVo,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val riskColor = Color(android.graphics.Color.parseColor(food.riskColorHex))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                isPressed = true
                onClick()
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = food.foodName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF2E7D32)
                        )
                        if (food.isIQTax) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "⚠️IQ",
                                fontSize = 10.sp,
                                color = Color(0xFF795548)
                            )
                        }
                    }
                    Text(
                        text = food.category,
                        fontSize = 12.sp,
                        color = Color(0xFF81C784),
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
                            text = food.riskLevelLabel,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = riskColor
                        )
                    }
                    Text(
                        text = "营养 ${food.nutritionScore}",
                        fontSize = 11.sp,
                        color = Color(0xFF81C784),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // 简要营养数据
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NutritionChip("热量", "${food.calories.toInt()}kcal")
                NutritionChip("蛋白", "${food.protein}g")
                NutritionChip("脂肪", "${food.fat}g")
                NutritionChip("碳水", "${food.carbohydrate}g")
            }
        }
    }
}

@Composable
fun NutritionChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2E7D32)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color(0xFFA5D6A7)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionDetailScreen(
    food: FoodDisplayVo,
    onBack: () -> Unit
) {
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        showContent = true
    }

    val riskColor = Color(android.graphics.Color.parseColor(food.riskColorHex))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(food.foodName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", fontSize = 20.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = riskColor.copy(alpha = 0.15f))
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE8F5E9)),
            contentPadding = PaddingValues(AppDimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingLarge)
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
                        shape = RoundedCornerShape(AppDimens.radiusXLarge)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🥗", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                food.foodName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp,
                                color = Color(0xFF2E7D32)
                            )
                            Text(
                                food.category,
                                fontSize = 14.sp,
                                color = Color(0xFF81C784),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            if (food.isIQTax) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF795548).copy(alpha = 0.15f)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "⚠️ 智商税产品 · ${food.iqTaxReason}",
                                        fontSize = 12.sp,
                                        color = Color(0xFF795548),
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }
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
                        shape = RoundedCornerShape(AppDimens.radiusLarge)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "⚠️ 风险评估",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = riskColor
                                )
                                Text(
                                    food.riskLevelLabel,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = riskColor
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            RiskRow("重金属", food.heavyMetalRisk, food.heavyMetalDesc)
                            RiskRow("农药残留", food.pesticideRisk, "")
                            RiskRow("糖油风险", food.sugarOilRisk, "")
                            RiskRow("加工风险", food.processingRisk, "")
                            RiskRow("添加剂", food.additiveRisk, "")

                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { (food.totalRiskScore.toFloat() / 100f).coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = riskColor,
                                trackColor = riskColor.copy(alpha = 0.2f)
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
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(AppDimens.radiusLarge)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "📊 营养成分（每100g）",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF2E7D32)
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            NutritionDetailRow("热量", "${food.calories.toInt()} kcal")
                            NutritionDetailRow("蛋白质", "${food.protein}g")
                            NutritionDetailRow("脂肪", "${food.fat}g")
                            NutritionDetailRow("碳水化合物", "${food.carbohydrate}g")
                            NutritionDetailRow("膳食纤维", "${food.fiber}g")
                            NutritionDetailRow("糖", "${food.sugar}g")
                            NutritionDetailRow("盐", "${food.salt}g")
                            NutritionDetailRow("胆固醇", "${food.cholesterol.toInt()}mg")
                            NutritionDetailRow("钙", "${food.calcium.toInt()}mg")
                            NutritionDetailRow("铁", "${food.iron}mg")
                            NutritionDetailRow("维生素C", "${food.vitaminC}mg")

                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("综合营养评分", fontSize = 13.sp, color = Color(0xFF5D4037))
                                Text(
                                    "${food.nutritionScore}分",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF4CAF50)
                                )
                            }
                        }
                    }
                }
            }

            // 实事求是
            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 300)) + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                        shape = RoundedCornerShape(AppDimens.radiusLarge)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "🎯 实事求是",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF1565C0)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                food.note.ifBlank { "暂无营养师点评" },
                                fontSize = 14.sp,
                                color = Color(0xFF5D4037),
                                lineHeight = 22.sp
                            )
                            if (food.actualEffect.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "实际功效：${food.actualEffect}",
                                    fontSize = 13.sp,
                                    color = Color(0xFF666666),
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }

            // 消费提醒
            if (food.caution.isNotBlank()) {
                item {
                    AnimatedVisibility(
                        visible = showContent,
                        enter = fadeIn(animationSpec = tween(300, delayMillis = 400)) + slideInVertically()
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                            shape = RoundedCornerShape(AppDimens.radiusLarge)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "💡 消费提醒",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFFF57F17)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    food.caution,
                                    fontSize = 14.sp,
                                    color = Color(0xFF5D4037),
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }
                }
            }

            // 自由选择
            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 500)) + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                        shape = RoundedCornerShape(AppDimens.radiusLarge)
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
                                "小镇只陈述事实，不替你做选择。知道了这些数据之后，选择权永远在你手里。",
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
fun RiskRow(label: String, value: Double, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 12.sp, color = Color(0xFF5D4037))
        Row(verticalAlignment = Alignment.CenterVertically) {
            LinearProgressIndicator(
                progress = { (value / 100.0).coerceIn(0.0, 1.0).toFloat() },
                modifier = Modifier
                    .width(60.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = when {
                    value >= 70 -> Color(0xFFF44336)
                    value >= 40 -> Color(0xFFFF9800)
                    else -> Color(0xFF4CAF50)
                },
                trackColor = Color(0xFFE0E0E0)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "${value.toInt()}",
                fontSize = 11.sp,
                color = Color(0xFF5D4037)
            )
        }
    }
}

@Composable
fun NutritionDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = Color(0xFF5D4037))
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2E7D32))
    }
}
