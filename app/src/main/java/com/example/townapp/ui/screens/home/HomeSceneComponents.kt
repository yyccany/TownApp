package com.example.townapp.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.feature.town_simulation.GentleTextProvider
import com.example.townapp.data.LifePathBinding
import com.example.townapp.data.database.entity.UserSpaceState
import com.example.townapp.ui.theme.*

enum class EventType {
    COMPANION,
    COMMENTARY,
    TITLE,
    BODY,
    SYSTEM,
    NORMAL
}

private fun classifyEvent(text: String): EventType {
    if (text.endsWith("😺") || text.endsWith("🐧") || text.endsWith("💛") || 
        text.endsWith("🥺") || text.endsWith("喵") || text.endsWith("咕咕")) {
        return EventType.COMPANION
    }
    if (text.startsWith("【小镇评述】") || text.startsWith("【闪光点】") || 
        text.startsWith("【小城镇看到】")) {
        return EventType.COMMENTARY
    }
    if (text.startsWith("【") && text.contains("】") && text.length < 30) {
        return EventType.TITLE
    }
    if (text.startsWith("【体感】") || text.startsWith("身体") || text.startsWith("你")) {
        if (text.length < 60) return EventType.BODY
    }
    if (text.startsWith("自动") || text.startsWith("你以") || text.startsWith("你快步")) {
        return EventType.SYSTEM
    }
    return EventType.NORMAL
}

@Composable
fun EventLogSection(eventLog: List<String>) {
    val listState = rememberLazyListState()
    
    LaunchedEffect(eventLog.size) {
        if (eventLog.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 280.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        reverseLayout = true
    ) {
        items(eventLog.take(12), key = { it.hashCode() }) { event ->
            EventBubble(text = event)
        }
    }
}

@Composable
private fun EventBubble(text: String) {
    val eventType = remember(text) { classifyEvent(text) }
    
    val (bgColor, textColor) = when (eventType) {
        EventType.COMPANION -> Pair(
            TownDesignTokens.Colors.bgCardAlt,
            TownDesignTokens.Colors.textPrimary
        )
        EventType.COMMENTARY -> Pair(
            TownDesignTokens.Colors.accent.copy(alpha = 0.08f),
            TownDesignTokens.Colors.primaryDark
        )
        EventType.TITLE -> Pair(
            Color.Transparent,
            TownDesignTokens.Colors.textSecondary
        )
        EventType.BODY -> Pair(
            TownDesignTokens.Colors.bgSurface,
            TownDesignTokens.Colors.textSecondary
        )
        EventType.SYSTEM -> Pair(
            Color.Transparent,
            TownDesignTokens.Colors.textTertiary
        )
        EventType.NORMAL -> Pair(
            TownDesignTokens.Colors.bgSurface,
            TownDesignTokens.Colors.textSecondary
        )
    }

    val displayText = remember(text) {
        var processed = text
        listOf("【小镇评述】", "【闪光点】", "【小城镇看到】", "【体感】").forEach { prefix ->
            processed = processed.removePrefix(prefix)
        }
        processed.trim()
    }

    if (eventType == EventType.TITLE || eventType == EventType.SYSTEM) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = textColor,
            lineHeight = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp)
        )
    } else {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = bgColor),
            shape = RoundedCornerShape(2.dp),
            border = BorderStroke(
                1.dp,
                if (eventType == EventType.COMMENTARY) TownDesignTokens.Colors.accent.copy(alpha = 0.3f)
                else TownDesignTokens.Colors.borderLight
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = displayText,
                fontSize = 13.sp,
                color = textColor,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
fun SceneDescription(
    hour: Int,
    roomName: String,
    light: Int,
    areaSqm: Int
) {
    val sceneText = GentleTextProvider.describeScene(hour, roomName, light, areaSqm)
    val isNight = hour >= 22 || hour < 6
    val isDusk = hour in 17..18

    val bgColor = when {
        isNight -> TownDesignTokens.Colors.nightMid.copy(alpha = 0.92f)
        isDusk -> TownDesignTokens.Colors.bgSurface.copy(alpha = 0.95f)
        else -> TownDesignTokens.Colors.bgSurface.copy(alpha = 0.95f)
    }
    val borderColor = when {
        isNight -> TownDesignTokens.Colors.nightBorder
        else -> TownDesignTokens.Colors.border
    }
    val textColor = when {
        isNight -> TownDesignTokens.Colors.nightText
        else -> TownDesignTokens.Colors.textPrimary
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(TownDesignTokens.Radius.pixel),
        border = BorderStroke(TownDesignTokens.Border.pixelWidth, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = GentleTextProvider.timeEmoji(hour),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = sceneText,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun SpaceDetailCard(
    space: UserSpaceState,
    binding: LifePathBinding
) {
    val config = binding.spaceConfig
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = TownDesignTokens.Colors.bgSurface),
        shape = RoundedCornerShape(TownDesignTokens.Radius.pixel),
        border = BorderStroke(TownDesignTokens.Border.pixelWidth, TownDesignTokens.Colors.border),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = GentleTextProvider.spaceDetailTitle(space.addressName),
                    fontSize = 13.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                    color = TownDesignTokens.Colors.textPrimary
                )
                Text(
                    text = if (expanded) "收起 ▲" else "展开 ▼",
                    fontSize = 10.sp,
                    color = TownDesignTokens.Colors.textTertiary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = config.description,
                fontSize = 12.sp,
                color = TownDesignTokens.Colors.textSecondary,
                lineHeight = 18.sp
            )

            if (expanded) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = TownDesignTokens.Colors.divider)
                Spacer(modifier = Modifier.height(10.dp))

                SpaceDetailRow(
                    label = "面积",
                    value = "${config.areaSqm}㎡",
                    description = GentleTextProvider.describeArea(config.areaSqm)
                )

                SpaceDetailRow(
                    label = "月租",
                    value = if (config.monthlyRent == 0.0) "无需付租" else "${String.format("%.0f", config.monthlyRent)}元",
                    description = GentleTextProvider.describeRent(config.monthlyRent, config.monthlyIncome)
                )

                SpaceDetailRow(
                    label = "月收入",
                    value = "${String.format("%.0f", config.monthlyIncome)}元",
                    description = GentleTextProvider.describeIncome(config.monthlyIncome)
                )

                SpaceDetailRow(
                    label = "通勤",
                    value = if (config.commuteMinutesOneWay == 0) "无通勤" else "${config.commuteMinutesOneWay}分钟",
                    description = GentleTextProvider.describeCommute(config.commuteMinutesOneWay)
                )

                SpaceDetailRow(
                    label = "工作时长",
                    value = "${config.workHoursPerDay}小时/天",
                    description = GentleTextProvider.describeWorkHours(config.workHoursPerDay)
                )

                SpaceDetailRow(
                    label = "居住感受",
                    value = "",
                    description = GentleTextProvider.describeEnvironment(config.privacy, config.light, config.noise)
                )

                SpaceDetailRow(
                    label = "存款",
                    value = "${String.format("%.0f", config.currentSavings)}元",
                    description = GentleTextProvider.describeSavingsDetail(config.currentSavings, config.monthlyRent)
                )
            }
        }
    }
}

@Composable
fun SpaceDetailRow(
    label: String,
    value: String,
    description: String
) {
    Column(modifier = Modifier.padding(vertical = 5.dp)) {
        Row {
            Text(
                text = label,
                fontSize = 11.sp,
                color = TownDesignTokens.Colors.textTertiary,
                modifier = Modifier.width(64.dp)
            )
            if (value.isNotEmpty()) {
                Text(
                    text = value,
                    fontSize = 11.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                    color = TownDesignTokens.Colors.textPrimary
                )
            }
        }
        Text(
            text = description,
            fontSize = 11.sp,
            color = TownDesignTokens.Colors.textSecondary.copy(alpha = 0.85f),
            lineHeight = 16.sp,
            modifier = Modifier.padding(start = 64.dp)
        )
    }
}
