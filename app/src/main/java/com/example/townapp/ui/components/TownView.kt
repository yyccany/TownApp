package com.example.townapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.business.*
import com.example.townapp.data.model.TownBuilding
import com.example.townapp.data.model.TownNPC
import com.example.townapp.data.model.TownState
import com.example.townapp.ui.design.TownAestheticDesign
import com.example.townapp.ui.design.TownAestheticDesign.TownDistrict
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TownView(townState: TownState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TownAestheticDesign.ColorPalette.background)
    ) {
        TownHeader(townState)
        WeatherBanner(townState.weather.name)
        DistrictTabs()
        DistrictContent(townState)
    }
}

@Composable
fun TownHeader(townState: TownState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(TownAestheticDesign.Spacing.md),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.md),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.lg)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.lg)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "万物薪俸小镇",
                    fontSize = TownAestheticDesign.Typography.Size.xl,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.primary
                )
                Text(
                    text = "觉醒值: ${townState.awakeningLevel}",
                    fontSize = TownAestheticDesign.Typography.Size.sm,
                    color = TownAestheticDesign.ColorPalette.textSecondary
                )
            }
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.base))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatChip(
                    label = "污染度",
                    value = townState.pollutionLevel,
                    color = TownAestheticDesign.ColorPalette.textSecondary
                )
                StatChip(
                    label = "债务值",
                    value = townState.debtLevel,
                    color = TownAestheticDesign.ColorPalette.warning
                )
                StatChip(
                    label = "健康值",
                    value = townState.healthLevel,
                    color = TownAestheticDesign.ColorPalette.success
                )
                StatChip(
                    label = "幸福度",
                    value = townState.happinessLevel,
                    color = TownAestheticDesign.ColorPalette.accent
                )
            }
        }
    }
}

@Composable
fun StatChip(label: String, value: Int, color: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.md),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.none)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.sm),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value.toString(),
                fontSize = TownAestheticDesign.Typography.Size.lg,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                fontSize = TownAestheticDesign.Typography.Size.xs,
                color = TownAestheticDesign.ColorPalette.textTertiary
            )
        }
    }
}

@Composable
fun WeatherBanner(weather: String) {
    val weatherInfo = getWeatherInfo(weather)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = TownAestheticDesign.Spacing.md),
        colors = CardDefaults.cardColors(containerColor = weatherInfo.color),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.md)
    ) {
        Row(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.base),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = weatherInfo.icon,
                fontSize = TownAestheticDesign.Typography.Size.`2xl`
            )
            Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.sm))
            Text(
                text = weatherInfo.description,
                fontSize = TownAestheticDesign.Typography.Size.base,
                color = Color.White
            )
        }
    }
}

fun getWeatherInfo(weather: String): WeatherInfo {
    return when (weather) {
        "SUNNY" -> WeatherInfo("☀️", "阳光明媚", Color(0xFFFFA500))
        "CLOUDY" -> WeatherInfo("⛅", "多云", Color(0xFF87CEEB))
        "OVERCAST" -> WeatherInfo("☁️", "阴天", Color(0xFF708090))
        "HAZY" -> WeatherInfo("😶‍🌫️", "雾霾", Color(0xFFA9A9A9))
        "RAINY" -> WeatherInfo("🌧️", "小雨", Color(0xFF4682B4))
        "STORM" -> WeatherInfo("⛈️", "暴风雨", Color(0xFF483D8B))
        else -> WeatherInfo("🌤️", "晴", Color(0xFF87CEEB))
    }
}

data class WeatherInfo(val icon: String, val description: String, val color: Color)

@Composable
fun DistrictTabs() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { clip = true },
        contentPadding = PaddingValues(TownAestheticDesign.Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(TownAestheticDesign.Spacing.sm)
    ) {
        items(TownDistrict.values(), key = { it }) { district ->
            DistrictTab(district)
        }
    }
}

@Composable
fun DistrictTab(district: TownDistrict) {
    val theme = TownAestheticDesign.DistrictTheme.getDistrictTheme(district)
    Card(
        colors = CardDefaults.cardColors(containerColor = theme.primaryColor.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.base),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.sm)
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = TownAestheticDesign.Spacing.md,
                vertical = TownAestheticDesign.Spacing.sm
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = theme.icon,
                fontSize = TownAestheticDesign.Typography.Size.lg
            )
            Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.xs))
            Text(
                text = theme.name,
                fontSize = TownAestheticDesign.Typography.Size.sm,
                color = theme.primaryColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun DistrictContent(townState: TownState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { clip = true },
        contentPadding = PaddingValues(TownAestheticDesign.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(TownAestheticDesign.Spacing.md)
    ) {
        item {
            TownSectionTitle("🏢 街区建筑")
        }
        itemsIndexed(townState.activeBuildings) { index, building ->
            BuildingCard(building, index = index)
        }

        if (townState.currentNPCs.isNotEmpty()) {
            item {
                TownSectionTitle("👥 小镇居民")
            }
            items(townState.currentNPCs, key = { it.id }) { npc ->
                NPCCard(npc)
            }
        }
    }
}

@Composable
fun TownSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = TownAestheticDesign.Typography.Size.lg,
        fontWeight = FontWeight.Bold,
        color = TownAestheticDesign.ColorPalette.textPrimary,
        modifier = Modifier.padding(bottom = TownAestheticDesign.Spacing.sm)
    )
}

@Composable
fun BuildingCard(building: TownBuilding, index: Int = 0) {
    val style = TownAestheticDesign.BuildingDesign.getBuildingStyle(building.id)

    // 从 BehaviorBuildingMapping 查询对应的价值密度
    val mapping = remember(building.id) { BehaviorBuildingMapping.findByBuildingId(building.id) }
    val density = mapping?.valueDensityPerUnit ?: 0.0

    // 入场动画：缩放 + 透明度
    val delayMs = (index * 80).coerceAtMost(600)
    val animScale = remember { Animatable(0.0f, 1.0f) }
    val animAlpha = remember { Animatable(0.0f, 1.0f) }

    LaunchedEffect(Unit) {
        delay(delayMs.toLong())
        launch {
            animScale.animateTo(1.0f, animationSpec = spring(dampingRatio = 0.6f, stiffness = 300f))
        }
        launch {
            animAlpha.animateTo(1.0f, animationSpec = tween(400))
        }
    }

    val accentColor = TownAestheticDesign.ColorPalette.textPrimary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(scaleX = animScale.value, scaleY = animScale.value, alpha = animAlpha.value),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.md),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.md)
    ) {
        Row(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(TownAestheticDesign.ColorPalette.surface)
                    .clip(RoundedCornerShape(TownAestheticDesign.CornerRadius.base)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getBuildingIcon(building.name),
                    fontSize = TownAestheticDesign.Typography.Size.`2xl`
                )
            }
            Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.md))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = building.name,
                    fontSize = TownAestheticDesign.Typography.Size.lg,
                    fontWeight = FontWeight.SemiBold,
                    color = TownAestheticDesign.ColorPalette.textPrimary
                )
                Text(
                    text = building.triggerCondition,
                    fontSize = TownAestheticDesign.Typography.Size.sm,
                    color = TownAestheticDesign.ColorPalette.textSecondary
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                // 数值展示
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = TownAestheticDesign.ColorPalette.surface,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = "${"%.1f".format(density)}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TownAestheticDesign.ColorPalette.textSecondary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                val impacts = getBuildingImpacts(building)
                impacts.forEach { (label, value) ->
                    Text(
                        text = "$label $value",
                        fontSize = TownAestheticDesign.Typography.Size.xs,
                        color = TownAestheticDesign.ColorPalette.textSecondary
                    )
                }
            }
        }
    }
}

fun getBuildingIcon(name: String): String {
    return when {
        name.contains("工厂") -> "🏭"
        name.contains("大厦") -> "🏢"
        name.contains("塔") -> "🏯"
        name.contains("博物馆") -> "🏛️"
        name.contains("图书馆") -> "📚"
        name.contains("实验室") -> "🔬"
        name.contains("医院") -> "🏥"
        name.contains("学院") -> "🏫"
        name.contains("农场") -> "🌾"
        name.contains("食堂") -> "🍽️"
        name.contains("商店") -> "🏪"
        name.contains("回收站") -> "♻️"
        name.contains("公寓") -> "🏠"
        name.contains("公交站") -> "🚌"
        name.contains("洞穴") -> "🕳️"
        name.contains("监狱") -> "🏰"
        name.contains("黑洞") -> "🕳️"
        name.contains("地狱") -> "🔥"
        name.contains("刑场") -> "⚔️"
        name.contains("祭坛") -> "⛫"
        name.contains("占卜") -> "🔮"
        name.contains("玄学") -> "🔮"
        name.contains("研究所") -> "🏢"
        name.contains("工坊") -> "🛠️"
        else -> "🏗️"
    }
}

fun getBuildingImpacts(building: TownBuilding): List<Pair<String, Int>> {
    val impacts = mutableListOf<Pair<String, Int>>()
    if (building.healthImpact != 0) impacts.add("❤️" to building.healthImpact)
    if (building.awakeningImpact != 0) impacts.add("✨" to building.awakeningImpact)
    if (building.happinessImpact != 0) impacts.add("😊" to building.happinessImpact)
    if (building.productivityImpact != 0) impacts.add("💼" to building.productivityImpact)
    if (building.debtImpact != 0) impacts.add("💸" to building.debtImpact)
    return impacts
}

@Composable
fun NPCCard(npc: TownNPC) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.surface.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.md),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.none)
    ) {
        Row(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = npc.avatar,
                fontSize = TownAestheticDesign.Typography.Size.`3xl`
            )
            Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.md))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = npc.name,
                        fontSize = TownAestheticDesign.Typography.Size.base,
                        fontWeight = FontWeight.SemiBold,
                        color = TownAestheticDesign.ColorPalette.textPrimary
                    )
                }
                Text(
                    text = npc.role,
                    fontSize = TownAestheticDesign.Typography.Size.xs,
                    color = TownAestheticDesign.ColorPalette.textTertiary
                )
            }
        }
        Row(
            modifier = Modifier.padding(
                start = TownAestheticDesign.Spacing.md,
                end = TownAestheticDesign.Spacing.md,
                bottom = TownAestheticDesign.Spacing.md
            )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        TownAestheticDesign.ColorPalette.surface.copy(alpha = 0.1f)
                    )
                    .clip(RoundedCornerShape(TownAestheticDesign.CornerRadius.sm))
                    .padding(TownAestheticDesign.Spacing.sm)
            ) {
                Text(
                    text = "\"${npc.dialogue}\"",
                    fontSize = TownAestheticDesign.Typography.Size.sm,
                    color = TownAestheticDesign.ColorPalette.textSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}