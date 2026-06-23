package com.example.townapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.feature.town_simulation.AestheticBusiness
import com.example.townapp.feature.town_simulation.AestheticBusiness.AestheticLevel
import com.example.townapp.ui.design.TownAestheticDesign

@Composable
fun AestheticLevelDisplay(
    detoxCompleted: Boolean = false,
    dailyDeleteCount: Int = 0,
    weeklyReviewCount: Int = 0,
    principlesMastered: Int = 0
) {
    val level = remember {
        AestheticBusiness.calculateAestheticLevel(
            detoxCompleted,
            dailyDeleteCount,
            weeklyReviewCount,
            principlesMastered
        )
    }
    
    val nextLevel = remember { getNextLevel(level) }
    val progress = remember { calculateProgress(level, dailyDeleteCount, weeklyReviewCount) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(TownAestheticDesign.Spacing.md),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.lg)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.lg)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🎨",
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.base))
                    Text(
                        text = "审美等级",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TownAestheticDesign.ColorPalette.primary
                    )
                }
                SuggestionChip(
                    onClick = {},
                    label = { Text(text = level.displayName) }
                )
            }
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
            
            LevelProgressCard(level, nextLevel, progress)
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
            
            PermissionList(level)
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))
            
            StatsGrid(dailyDeleteCount, weeklyReviewCount, principlesMastered)
        }
    }
}

fun getNextLevel(currentLevel: AestheticLevel): AestheticLevel? {
    val levels = AestheticLevel.values()
    val currentIndex = levels.indexOf(currentLevel)
    return if (currentIndex < levels.size - 1) levels[currentIndex + 1] else null
}

fun calculateProgress(level: AestheticLevel, dailyDeleteCount: Int, weeklyReviewCount: Int): Int {
    return when (level) {
        AestheticLevel.WHITE -> {
            if (dailyDeleteCount >= 10) 100 else (dailyDeleteCount * 10)
        }
        AestheticLevel.BEGINNER -> {
            val target = 100
            val current = dailyDeleteCount
            if (current >= target) 100 else (current * 100 / target)
        }
        AestheticLevel.INTERMEDIATE -> {
            val target = 50
            val current = weeklyReviewCount
            if (current >= target) 100 else (current * 100 / target)
        }
        AestheticLevel.MASTER -> {
            val target = 4
            val current = if (dailyDeleteCount >= 100 && weeklyReviewCount >= 50) 4 else 3
            (current * 100 / target)
        }
        AestheticLevel.AWAKENED -> 100
    }
}

fun getLevelColor(level: AestheticLevel): Color {
    return when (level) {
        AestheticLevel.WHITE -> TownAestheticDesign.ColorPalette.textDisabled
        AestheticLevel.BEGINNER -> TownAestheticDesign.ColorPalette.bronze
        AestheticLevel.INTERMEDIATE -> TownAestheticDesign.ColorPalette.silver
        AestheticLevel.MASTER -> TownAestheticDesign.ColorPalette.gold
        AestheticLevel.AWAKENED -> TownAestheticDesign.ColorPalette.accent
    }
}

@Composable
fun LevelProgressCard(currentLevel: AestheticLevel, nextLevel: AestheticLevel?, progress: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.md)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${currentLevel.displayName}: ${currentLevel.description}",
                    fontSize = 14.sp,
                    color = TownAestheticDesign.ColorPalette.textPrimary,
                    fontWeight = FontWeight.Medium
                )
                if (nextLevel != null) {
                    Text(
                        text = "下一级: ${nextLevel.displayName}",
                        fontSize = 12.sp,
                        color = TownAestheticDesign.ColorPalette.textTertiary
                    )
                } else {
                    Text(
                        text = "最高等级",
                        fontSize = 12.sp,
                        color = TownAestheticDesign.ColorPalette.gold,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.sm))
            
            LinearProgressIndicator(
                progress = progress / 100f,
                modifier = Modifier.fillMaxWidth(),
                color = getLevelColor(currentLevel)
            )
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xs))
            
            Text(
                text = "进度: $progress%",
                fontSize = 12.sp,
                color = TownAestheticDesign.ColorPalette.textTertiary,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun PermissionList(level: AestheticLevel) {
    val permissions = getLevelPermissions(level)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.md)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.md)
        ) {
            Text(
                text = "当前权限",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TownAestheticDesign.ColorPalette.textPrimary
            )
            
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.sm))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(TownAestheticDesign.Spacing.xs)
            ) {
                permissions.forEach { (icon, text, unlocked) ->
                    PermissionItem(icon, text, unlocked)
                }
            }
        }
    }
}

fun getLevelPermissions(level: AestheticLevel): List<Triple<String, String, Boolean>> {
    val allPermissions = listOf(
        Triple("💰", "查看物品价格", true),
        Triple("💎", "查看物品价值密度", level.ordinal >= 1),
        Triple("⏳", "查看物品使用寿命", level.ordinal >= 2),
        Triple("📊", "查看物品全生命周期成本", level.ordinal >= 3),
        Triple("🤖", "自动识别低价值密度物品", level.ordinal >= 4)
    )
    return allPermissions
}

@Composable
fun PermissionItem(icon: String, text: String, unlocked: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (unlocked) icon else "🔒",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.sm))
        Text(
            text = text,
            fontSize = 14.sp,
            color = if (unlocked) TownAestheticDesign.ColorPalette.textPrimary 
                    else TownAestheticDesign.ColorPalette.textDisabled
        )
    }
}

@Composable
fun StatsGrid(dailyDeleteCount: Int, weeklyReviewCount: Int, principlesMastered: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatCard("🗑️", "每日一删", dailyDeleteCount, "目标: 100")
        StatCard("🔍", "每周一评", weeklyReviewCount, "目标: 50")
        StatCard("✨", "掌握原则", principlesMastered, "目标: 4")
    }
}

@Composable
fun RowScope.StatCard(icon: String, label: String, value: Int, target: String) {
    Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.sm)
    ) {
        Column(
            modifier = Modifier
                .padding(TownAestheticDesign.Spacing.base)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xs))
            Text(
                text = value.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TownAestheticDesign.ColorPalette.textPrimary
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = TownAestheticDesign.ColorPalette.textTertiary
            )
            Text(
                text = target,
                fontSize = 12.sp,
                color = TownAestheticDesign.ColorPalette.textDisabled
            )
        }
    }
}
