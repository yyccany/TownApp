package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.DietSystem
import com.example.townapp.data.DietSystem.FoodItem

/**
 * 饮食选择面板
 *
 * 手动挑选三餐食材。早中晚三餐可分别挑选食材：
 * 粗粮、肉类、生鲜蔬果、外卖快餐、甜品零食等。
 */
@Composable
fun DietPanel(
    currentPlan: DietSystem.DailyDietPlan,
    onPlanChange: (DietSystem.DailyDietPlan) -> Unit,
    modifier: Modifier = Modifier,
    enableInternalScroll: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (enableInternalScroll) Modifier.verticalScroll(rememberScrollState()) else Modifier),
        verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
    ) {
        Text("今日饮食", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        // 三餐选择
        MealSection(
            label = "早餐",
            selected = currentPlan.breakfast,
            onToggle = { food ->
                val newBreakfast = toggleFood(currentPlan.breakfast, food)
                onPlanChange(currentPlan.copy(breakfast = newBreakfast))
            }
        )
        MealSection(
            label = "午餐",
            selected = currentPlan.lunch,
            onToggle = { food ->
                val newLunch = toggleFood(currentPlan.lunch, food)
                onPlanChange(currentPlan.copy(lunch = newLunch))
            }
        )
        MealSection(
            label = "晚餐",
            selected = currentPlan.dinner,
            onToggle = { food ->
                val newDinner = toggleFood(currentPlan.dinner, food)
                onPlanChange(currentPlan.copy(dinner = newDinner))
            }
        )

        // 加餐
        Text("午休加餐", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
        ) {
            val snacks = DietSystem.allFoods.filter { it.category == DietSystem.FoodCategory.SNACK }
            snacks.forEach { food ->
                FilterChip(
                    selected = currentPlan.snack?.id == food.id,
                    onClick = {
                        onPlanChange(currentPlan.copy(
                            snack = if (currentPlan.snack?.id == food.id) null else food
                        ))
                    },
                    label = { Text(food.name, fontSize = 12.sp) }
                )
            }
        }

        HorizontalDivider()

        // 饮食效果预览
        val effect = DietSystem.calculateDailyEffect(currentPlan)
        if (effect.totalCost > 0) {
            Surface(
                shape = RoundedCornerShape(AppDimens.radiusSmall),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("今日饮食花费：${effect.totalCost}元", fontSize = 13.sp)
                    Text(
                        "健康${if (effect.healthDelta >= 0) "+" else ""}${"%.1f".format(effect.healthDelta)} | " +
                        "疲惫${if (effect.fatigueDelta >= 0) "+" else ""}${"%.1f".format(effect.fatigueDelta)} | " +
                        "心情${if (effect.joyDelta >= 0) "+" else ""}${"%.1f".format(effect.joyDelta)}",
                        fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun MealSection(
    label: String,
    selected: List<FoodItem>,
    onToggle: (FoodItem) -> Unit
) {
    Column {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val mainFoods = DietSystem.allFoods.filter {
                it.category in setOf(DietSystem.FoodCategory.STAPLE, DietSystem.FoodCategory.MEAT,
                    DietSystem.FoodCategory.VEGETABLE, DietSystem.FoodCategory.FAST_FOOD,
                    DietSystem.FoodCategory.SEAFOOD)
            }
            mainFoods.take(8).forEach { food ->
                FilterChip(
                    selected = selected.any { it.id == food.id },
                    onClick = { onToggle(food) },
                    label = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(food.name, fontSize = 11.sp)
                            Text("${food.cost}元", fontSize = 9.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                )
            }
        }
    }
}

private fun toggleFood(list: List<FoodItem>, food: FoodItem): List<FoodItem> {
    return if (list.any { it.id == food.id }) {
        list.filter { it.id != food.id }
    } else {
        list + food
    }
}