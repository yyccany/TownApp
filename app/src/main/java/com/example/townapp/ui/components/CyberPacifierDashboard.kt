package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.design.TownAestheticDesign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CyberPacifierDashboard() {
    var doroHours by remember { mutableStateOf(2.5f) }
    var tafiHours by remember { mutableStateOf(3.5f) }
    var monthlyTips by remember { mutableStateOf(300f) }
    var virtualIdolCount by remember { mutableStateOf(3) }
    var realSocialHours by remember { mutableStateOf(2f) }
    var hasHobby by remember { mutableStateOf(false) }
    var inRelationship by remember { mutableStateOf(false) }
    var daysSinceVirtualContent by remember { mutableStateOf(0) }

    val addictionLevel = calculateAddictionLevel(
        doroHours, tafiHours, monthlyTips, virtualIdolCount, realSocialHours
    )

    val valueDensity = calculateCyberPacifierValueDensity(
        doroHours, tafiHours, monthlyTips, virtualIdolCount
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🍼", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                        Text(
                            text = "赛博奶嘴监测站",
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TownAestheticDesign.ColorPalette.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(TownAestheticDesign.ColorPalette.background)
                .graphicsLayer { clip = true },
            contentPadding = PaddingValues(AppDimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
        ) {
            item {
                AddictionLevelCard(addictionLevel, valueDensity)
            }

            item {
                UsageInputCard(
                    doroHours = doroHours,
                    onDoroHoursChange = { doroHours = it },
                    tafiHours = tafiHours,
                    onTafiHoursChange = { tafiHours = it },
                    monthlyTips = monthlyTips,
                    onMonthlyTipsChange = { monthlyTips = it },
                    virtualIdolCount = virtualIdolCount,
                    onVirtualIdolCountChange = { virtualIdolCount = it }
                )
            }

            item {
                RecoveryProgressCard(
                    realSocialHours = realSocialHours,
                    onRealSocialHoursChange = { realSocialHours = it },
                    hasHobby = hasHobby,
                    onHasHobbyChange = { hasHobby = it },
                    inRelationship = inRelationship,
                    onInRelationshipChange = { inRelationship = it },
                    daysSinceVirtualContent = daysSinceVirtualContent,
                    onDaysChange = { daysSinceVirtualContent = it }
                )
            }

            item {
                CostAnalysisCard(doroHours, tafiHours, monthlyTips, virtualIdolCount)
            }

            item {
                AwakeningTipsCard(addictionLevel)
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

fun calculateAddictionLevel(
    doroHours: Float,
    tafiHours: Float,
    monthlyTips: Float,
    virtualIdolCount: Int,
    realSocialHours: Float
): AddictionLevel {
    var score = 0

    if (doroHours > 2) score += 20
    if (tafiHours > 3) score += 20
    if (monthlyTips > 100) score += 25
    if (virtualIdolCount >= 3) score += 15
    if (realSocialHours < 2) score += 20

    return when {
        score >= 80 -> AddictionLevel.SEVERE
        score >= 60 -> AddictionLevel.HIGH
        score >= 40 -> AddictionLevel.MEDIUM
        score >= 20 -> AddictionLevel.LOW
        else -> AddictionLevel.NONE
    }
}

fun calculateCyberPacifierValueDensity(
    doroHours: Float,
    tafiHours: Float,
    monthlyTips: Float,
    virtualIdolCount: Int
): Double {
    val yearlyHours = (doroHours + tafiHours) * 365
    val yearlyMoney = monthlyTips * 12
    val timeCost = yearlyHours
    val moneyCost = yearlyMoney / 28.4
    val healthCost = yearlyHours * 1.5
    val totalCost = timeCost + moneyCost + healthCost
    val functionalValue = 0.0
    return if (totalCost > 0) functionalValue / totalCost else 0.0
}

enum class AddictionLevel(val label: String, val color: Color, val emoji: String) {
    NONE("无依赖", Color(0xFF27AE60), "✅"),
    LOW("轻度依赖", Color(0xFFF39C12), "⚠️"),
    MEDIUM("中度依赖", Color(0xFFE67E22), "🔶"),
    HIGH("高度依赖", Color(0xFFE74C3C), "🚨"),
    SEVERE("严重成瘾", Color(0xFF8E44AD), "💀")
}

@Composable
fun AddictionLevelCard(level: AddictionLevel, valueDensity: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        colors = CardDefaults.cardColors(containerColor = level.color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = level.emoji, fontSize = 32.sp)
                    Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                    Text(
                        text = "成瘾等级",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TownAestheticDesign.ColorPalette.textPrimary
                    )
                }
                Card(
                    colors = CardDefaults.cardColors(containerColor = level.color),
                    shape = RoundedCornerShape(AppDimens.radiusXLarge)
                ) {
                    Text(
                        text = level.label,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = AppDimens.paddingMedium, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            Text(
                text = getAddictionDescription(level),
                fontSize = 14.sp,
                color = TownAestheticDesign.ColorPalette.textSecondary,
                lineHeight = 1.5.sp
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ValueDensityChip("价值密度", String.format("%.3f", valueDensity), level.color)
                ValueDensityChip("级别", level.name, level.color)
            }
        }
    }
}

fun getAddictionDescription(level: AddictionLevel): String {
    return when (level) {
        AddictionLevel.NONE -> "你目前的虚拟偶像使用情况健康，保持现状。多花时间在真实的社交和爱好上。"
        AddictionLevel.LOW -> "你开始对虚拟偶像产生轻度依赖。注意控制使用时间，增加现实社交活动。"
        AddictionLevel.MEDIUM -> "你已经对虚拟偶像产生中等依赖。它正在影响你的现实生活和社交能力。"
        AddictionLevel.HIGH -> "你对虚拟偶像产生了高度依赖。它正在蚕食你的生命时间和金钱，削弱你的现实生活能力。"
        AddictionLevel.SEVERE -> "你已经严重成瘾。虚拟世界正在取代你的现实生活。你正在失去爱、工作和社交的能力。"
    }
}

@Composable
fun ValueDensityChip(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TownAestheticDesign.ColorPalette.textTertiary
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun UsageInputCard(
    doroHours: Float,
    onDoroHoursChange: (Float) -> Unit,
    tafiHours: Float,
    onTafiHoursChange: (Float) -> Unit,
    monthlyTips: Float,
    onMonthlyTipsChange: (Float) -> Unit,
    virtualIdolCount: Int,
    onVirtualIdolCountChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation),
        shape = RoundedCornerShape(AppDimens.radiusLarge)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "📊", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                Text(
                    text = "虚拟偶像使用情况",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.primary
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            UsageSlider(
                label = "🐕 Doro每日使用时间",
                value = doroHours,
                onValueChange = onDoroHoursChange,
                valueRange = 0f..8f,
                warningThreshold = 2f,
                color = Color(0xFFFF69B4)
            )

            UsageSlider(
                label = "😺 塔菲喵每日使用时间",
                value = tafiHours,
                onValueChange = onTafiHoursChange,
                valueRange = 0f..8f,
                warningThreshold = 3f,
                color = Color(0xFFFFB6C1)
            )

            UsageSlider(
                label = "💰 每月打赏金额",
                value = monthlyTips,
                onValueChange = onMonthlyTipsChange,
                valueRange = 0f..2000f,
                warningThreshold = 100f,
                color = Color(0xFFFFD700)
            )

            UsageSlider(
                label = "🎮 沉迷虚拟形象数量",
                value = virtualIdolCount.toFloat(),
                onValueChange = { onVirtualIdolCountChange(it.toInt()) },
                valueRange = 0f..10f,
                warningThreshold = 3f,
                color = Color(0xFF9370DB),
                isInt = true
            )
        }
    }
}

@Composable
fun UsageSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    warningThreshold: Float,
    color: Color,
    isInt: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = TownAestheticDesign.ColorPalette.textSecondary
            )
            Text(
                text = if (isInt) "${value.toInt()}个" else "${String.format("%.1f", value)}小时",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (value > warningThreshold) TownAestheticDesign.ColorPalette.danger else color
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = if (value > warningThreshold) TownAestheticDesign.ColorPalette.danger else color,
                activeTrackColor = if (value > warningThreshold) TownAestheticDesign.ColorPalette.danger else color
            )
        )
    }
}

@Composable
fun RecoveryProgressCard(
    realSocialHours: Float,
    onRealSocialHoursChange: (Float) -> Unit,
    hasHobby: Boolean,
    onHasHobbyChange: (Boolean) -> Unit,
    inRelationship: Boolean,
    onInRelationshipChange: (Boolean) -> Unit,
    daysSinceVirtualContent: Int,
    onDaysChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF27AE60).copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🌱", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                Text(
                    text = "现实生活恢复进度",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF27AE60)
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            UsageSlider(
                label = "👥 每周现实社交时间",
                value = realSocialHours,
                onValueChange = onRealSocialHoursChange,
                valueRange = 0f..20f,
                warningThreshold = 3f,
                color = Color(0xFF27AE60)
            )

            RecoveryCheckItem(
                label = "🎨 是否培养了现实爱好",
                checked = hasHobby,
                onCheckedChange = onHasHobbyChange,
                emoji = "✅"
            )

            RecoveryCheckItem(
                label = "💕 是否开始真实恋爱",
                checked = inRelationship,
                onCheckedChange = onInRelationshipChange,
                emoji = "❤️"
            )

            UsageSlider(
                label = "📅 连续戒断虚拟内容天数",
                value = daysSinceVirtualContent.toFloat(),
                onValueChange = { onDaysChange(it.toInt()) },
                valueRange = 0f..30f,
                warningThreshold = 7f,
                color = Color(0xFF27AE60),
                isInt = true
            )

            if (daysSinceVirtualContent >= 7) {
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF27AE60).copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(AppDimens.radiusSmall)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🎉", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                        Text(
                            text = "恭喜！你已经完成7天戒断！'现实觉醒日'事件已触发！",
                            fontSize = 12.sp,
                            color = Color(0xFF27AE60),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecoveryCheckItem(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    emoji: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = TownAestheticDesign.ColorPalette.textSecondary
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF27AE60),
                checkedTrackColor = Color(0xFF27AE60).copy(alpha = 0.3f)
            )
        )
    }
}

@Composable
fun CostAnalysisCard(
    doroHours: Float,
    tafiHours: Float,
    monthlyTips: Float,
    virtualIdolCount: Int
) {
    val yearlyHours = (doroHours + tafiHours) * 365
    val yearlyMoney = monthlyTips * 12
    val moneyCostHours = yearlyMoney / 28.4
    val healthCostHours = yearlyHours * 1.5
    val totalCostHours = yearlyHours + moneyCostHours + healthCostHours
    val workDaysEquivalent = totalCostHours / 8

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.danger.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "💸", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                Text(
                    text = "年度薪俸成本分析",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.danger
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CostItem("时间成本", "${yearlyHours.toInt()}小时", TownAestheticDesign.ColorPalette.warning)
                CostItem("金钱成本", "${yearlyMoney.toInt()}元", TownAestheticDesign.ColorPalette.danger)
                CostItem("健康成本", "${healthCostHours.toInt()}小时", Color(0xFF8B0000))
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            Card(
                colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.danger),
                shape = RoundedCornerShape(AppDimens.radiusSmall)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "年度总成本",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "${totalCostHours.toInt()} 小时",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "≈ ${workDaysEquivalent.toInt()} 个工作日",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            Text(
                text = "💡 你每年在虚拟偶像上花费了相当于 ${workDaysEquivalent.toInt()} 个工作日的生命，这些时间本可以用于真实的社交、恋爱、工作和自我提升。",
                fontSize = 12.sp,
                color = TownAestheticDesign.ColorPalette.textSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CostItem(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TownAestheticDesign.ColorPalette.textTertiary
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun AwakeningTipsCard(level: AddictionLevel) {
    val tips = getAwakeningTips(level)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.gold.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "✨", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                Text(
                    text = "觉醒建议",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.gold
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            tips.forEach { tip ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "• ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TownAestheticDesign.ColorPalette.gold
                    )
                    Text(
                        text = tip,
                        fontSize = 14.sp,
                        color = TownAestheticDesign.ColorPalette.textSecondary,
                        lineHeight = 1.5.sp
                    )
                }
            }
        }
    }
}

fun getAwakeningTips(level: AddictionLevel): List<String> {
    return when (level) {
        AddictionLevel.NONE -> listOf(
            "继续保持健康的虚拟内容消费习惯",
            "多参与线下社交活动，保持现实连接",
            "培养更多现实爱好，丰富生活"
        )
        AddictionLevel.LOW -> listOf(
            "设定每日虚拟偶像使用上限（不超过1小时）",
            "每周至少安排2次和朋友见面",
            "开始培养一个现实中的爱好"
        )
        AddictionLevel.MEDIUM -> listOf(
            "立即取消所有自动打赏功能",
            "卸载直播平台APP，只在固定时间观看",
            "每周参加至少1次线下社交活动",
            "考虑寻求心理咨询师的帮助"
        )
        AddictionLevel.HIGH -> listOf(
            "立即停止所有打赏，断绝经济依赖",
            "进行7天数字 detox，完全不接触虚拟偶像",
            "去看心理咨询师，评估成瘾程度",
            "重新建立现实社交圈，从最简单的开始"
        )
        AddictionLevel.SEVERE -> listOf(
            "这已经是严重成瘾，需要专业帮助",
            "立即就医，评估是否需要成瘾治疗",
            "告诉家人朋友你需要帮助",
            "切断所有接触虚拟偶像的渠道",
            "进入康复中心，接受系统治疗"
        )
    }
}
