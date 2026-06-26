package com.example.townapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.FoodItem
import com.example.townapp.domain.WeeklyStats
import com.example.townapp.ui.viewmodel.DailyPlan
import com.example.townapp.ui.viewmodel.HomeViewModel
import com.example.townapp.ui.viewmodel.WeekEventChoice

// 书页配色，全局统一
private val paper = Color(0xFFF5F0E6)
private val ink = Color(0xFF3A352D)
private val inkLight = Color(0xFF7A7468)
private val inkFaint = Color(0xFFB5AEA0)
private val overlay = Color(0x26000000)

@Composable
fun FoodPickerSheet(
    foodItems: List<FoodItem>,
    savings: Double,
    foodEatCount: Map<String, Int>,
    onSelect: (FoodItem) -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(overlay)
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(paper)
                .clickable(enabled = false) {}
                .padding(horizontal = 32.dp, vertical = 32.dp)
        ) {
            Text(
                text = "想吃什么？",
                fontSize = 18.sp,
                color = ink,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "余额 ${String.format("%.0f", savings)} 元",
                fontSize = 13.sp,
                color = inkFaint,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(foodItems) { food ->
                    val cost = food.pricePer100g * food.typicalServing / 100.0
                    val canAfford = savings >= cost
                    val eatCount = foodEatCount[food.name] ?: 0

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = canAfford) { onSelect(food) }
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = food.name,
                            fontSize = 15.sp,
                            color = if (canAfford) ink else inkFaint
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = buildString {
                                append(food.note)
                                append("  ·  ")
                                append(String.format("%.1f", cost))
                                append(" 元")
                                if (eatCount > 0) {
                                    append("  ·  吃过")
                                    append(eatCount)
                                    append("次")
                                }
                            },
                            fontSize = 12.sp,
                            color = inkFaint
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(inkFaint.copy(alpha = 0.2f))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "不了",
                fontSize = 14.sp,
                color = inkFaint,
                modifier = Modifier
                    .clickable(onClick = onDismiss)
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ComfortTapOverlay(
    message: String,
    onClose: () -> Unit,
    onTap: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(overlay)
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(48.dp)
                .clickable(onClick = onTap),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                fontSize = 17.sp,
                color = ink,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "点一下换一句  ·  点空白处离开",
                fontSize = 12.sp,
                color = inkFaint
            )
        }
    }
}

@Composable
fun WeeklyBriefDialog(
    weeklyStats: WeeklyStats,
    currentWeek: Int,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(overlay)
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
                .background(paper)
                .padding(32.dp)
                .clickable(enabled = false) {}
        ) {
            Text(
                text = "第 ${currentWeek} 周",
                fontSize = 18.sp,
                color = ink,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (weeklyStats.summary.isNotEmpty()) {
                Text(
                    text = weeklyStats.summary,
                    fontSize = 15.sp,
                    color = inkLight,
                    lineHeight = 26.sp,
                    modifier = Modifier.padding(bottom = 28.dp)
                )
            }

            listOf(
                "疲惫 ${String.format("%.0f", weeklyStats.totalFatigue)}%",
                "焦虑 ${String.format("%.0f", weeklyStats.avgAnxiety)}%",
                "吃饭花了 ${String.format("%.0f", weeklyStats.totalFoodCost)} 元",
                "熬了 ${weeklyStats.stayUpLateDays} 天夜"
            ).forEach { line ->
                Text(
                    text = line,
                    fontSize = 14.sp,
                    color = inkFaint,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "继续",
                fontSize = 15.sp,
                color = ink,
                modifier = Modifier
                    .clickable(onClick = onDismiss)
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun WeekEventChoiceDialog(
    eventSummary: String,
    choices: List<WeekEventChoice>,
    onChoiceSelected: (WeekEventChoice) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(overlay),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
                .background(paper)
                .padding(32.dp)
        ) {
            Text(
                text = eventSummary,
                fontSize = 16.sp,
                color = ink,
                lineHeight = 26.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            choices.forEach { choice ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onChoiceSelected(choice) }
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = choice.label,
                        fontSize = 15.sp,
                        color = ink,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = choice.description,
                        fontSize = 13.sp,
                        color = inkFaint,
                        lineHeight = 20.sp
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(inkFaint.copy(alpha = 0.2f))
                )
            }
        }
    }
}

// 保留但不使用，不影响编译
@Composable
fun WeeklyPlanDialog(
    viewModel: HomeViewModel,
    weeklyPlan: Map<Int, DailyPlan>,
    currentDay: Int,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(overlay)
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(paper)
                .clickable(enabled = false) {}
                .padding(32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "周计划暂时收起来了，先过好今天吧。",
                fontSize = 15.sp,
                color = inkLight,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = "好",
                fontSize = 15.sp,
                color = ink,
                modifier = Modifier.clickable(onClick = onDismiss).padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun WeeklyExpenseCard(
    expense: com.example.townapp.ui.viewmodel.WeeklyExpense,
    mode: com.example.townapp.ui.viewmodel.ShoppingMode
) {}

@Composable
fun WeekEventsPanel(
    weeklyEvents: List<String>,
    currentWeek: Int,
    onClose: () -> Unit
) {}

@Composable
fun PetDialogueCard(
    text: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(overlay)
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                color = ink,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )
        }
    }
}
