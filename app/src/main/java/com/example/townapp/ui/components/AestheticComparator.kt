package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.feature.town_simulation.AestheticBusiness
import com.example.townapp.feature.town_simulation.AestheticBusiness.AestheticItem
import com.example.townapp.feature.town_simulation.AestheticBusiness.ComparisonResult
import com.example.townapp.ui.design.TownAestheticDesign
import java.util.Locale

@Composable
fun AestheticComparator() {
    var item1Name by remember { mutableStateOf("") }
    var item1Price by remember { mutableStateOf("") }
    var item1FunctionalValue by remember { mutableStateOf("") }
    var item1HealthCost by remember { mutableStateOf("") }
    var item1LifeYears by remember { mutableStateOf("") }

    var item2Name by remember { mutableStateOf("") }
    var item2Price by remember { mutableStateOf("") }
    var item2FunctionalValue by remember { mutableStateOf("") }
    var item2HealthCost by remember { mutableStateOf("") }
    var item2LifeYears by remember { mutableStateOf("") }

    var comparisonResult by remember { mutableStateOf<ComparisonResult?>(null) }
    var showResult by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(TownAestheticDesign.Spacing.md),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.lg),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.lg)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.lg)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🎨",
                    fontSize = TownAestheticDesign.Typography.Size.`3xl`
                )
                Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.base))
                Text(
                    text = "审美对比器",
                    fontSize = TownAestheticDesign.Typography.Size.xl,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.primary
                )
            }

            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.lg))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(TownAestheticDesign.Spacing.md)
            ) {
                ItemInputCard(
                    title = "物品A",
                    name = item1Name,
                    onNameChange = { item1Name = it },
                    price = item1Price,
                    onPriceChange = { item1Price = it },
                    functionalValue = item1FunctionalValue,
                    onFunctionalValueChange = { item1FunctionalValue = it },
                    healthCost = item1HealthCost,
                    onHealthCostChange = { item1HealthCost = it },
                    lifeYears = item1LifeYears,
                    onLifeYearsChange = { item1LifeYears = it },
                    color = TownAestheticDesign.ColorPalette.accent
                )

                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "⚖️",
                        fontSize = TownAestheticDesign.Typography.Size.`3xl`
                    )
                }

                ItemInputCard(
                    title = "物品B",
                    name = item2Name,
                    onNameChange = { item2Name = it },
                    price = item2Price,
                    onPriceChange = { item2Price = it },
                    functionalValue = item2FunctionalValue,
                    onFunctionalValueChange = { item2FunctionalValue = it },
                    healthCost = item2HealthCost,
                    onHealthCostChange = { item2HealthCost = it },
                    lifeYears = item2LifeYears,
                    onLifeYearsChange = { item2LifeYears = it },
                    color = TownAestheticDesign.ColorPalette.warning
                )
            }

            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.lg))

            Button(
                onClick = {
                    val item1 = AestheticItem(
                        name = item1Name,
                        price = item1Price.toDoubleOrNull() ?: 0.0,
                        functionalValue = item1FunctionalValue.toDoubleOrNull() ?: 0.0,
                        healthCost = item1HealthCost.toDoubleOrNull() ?: 0.0,
                        usefulLifeYears = item1LifeYears.toDoubleOrNull() ?: 1.0
                    )

                    val item2 = AestheticItem(
                        name = item2Name,
                        price = item2Price.toDoubleOrNull() ?: 0.0,
                        functionalValue = item2FunctionalValue.toDoubleOrNull() ?: 0.0,
                        healthCost = item2HealthCost.toDoubleOrNull() ?: 0.0,
                        usefulLifeYears = item2LifeYears.toDoubleOrNull() ?: 1.0
                    )

                    comparisonResult = AestheticBusiness.compareItems(item1, item2)
                    showResult = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TownAestheticDesign.ColorPalette.primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.base)
            ) {
                Text(
                    text = "开始对比",
                    fontSize = TownAestheticDesign.Typography.Size.base,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (showResult && comparisonResult != null) {
                Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.lg))
                ComparisonResultCard(comparisonResult!!)
            }
        }
    }
}

@Composable
fun RowScope.ItemInputCard(
    title: String,
    name: String,
    onNameChange: (String) -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    functionalValue: String,
    onFunctionalValueChange: (String) -> Unit,
    healthCost: String,
    onHealthCostChange: (String) -> Unit,
    lifeYears: String,
    onLifeYearsChange: (String) -> Unit,
    color: Color
) {
    Card(
        modifier = Modifier.weight(1f),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.md),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.none)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.md)
        ) {
            Text(
                text = title,
                fontSize = TownAestheticDesign.Typography.Size.lg,
                fontWeight = FontWeight.Bold,
                color = color
            )

            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.base))

            InputField(
                label = "物品名称",
                value = name,
                onValueChange = onNameChange
            )
            InputField(
                label = "价格（元）",
                value = price,
                onValueChange = onPriceChange
            )
            InputField(
                label = "功能价值",
                value = functionalValue,
                onValueChange = onFunctionalValueChange
            )
            InputField(
                label = "健康成本（小时）",
                value = healthCost,
                onValueChange = onHealthCostChange
            )
            InputField(
                label = "使用年限",
                value = lifeYears,
                onValueChange = onLifeYearsChange
            )
        }
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = TownAestheticDesign.Typography.Size.xs,
            color = TownAestheticDesign.ColorPalette.textTertiary,
            modifier = Modifier.padding(bottom = TownAestheticDesign.Spacing.xs)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(fontSize = TownAestheticDesign.Typography.Size.sm),
            shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.sm),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }
    Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.sm))
}

@Composable
fun ComparisonResultCard(result: ComparisonResult) {
    val winnerColor = TownAestheticDesign.ColorPalette.success.copy(alpha = 0.1f)
    val loserColor = TownAestheticDesign.ColorPalette.warning.copy(alpha = 0.1f)
    val isItem1Winner = result.density1 >= result.density2

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isItem1Winner) winnerColor else loserColor
        ),
        shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.md),
        elevation = CardDefaults.cardElevation(defaultElevation = TownAestheticDesign.Elevation.md)
    ) {
        Column(
            modifier = Modifier.padding(TownAestheticDesign.Spacing.lg)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "💎 对比结果",
                    fontSize = TownAestheticDesign.Typography.Size.lg,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.textPrimary
                )
            }

            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ResultItem(
                    name = result.item1.name,
                    valueDensity = result.density1,
                    isWinner = isItem1Winner
                )
                ResultItem(
                    name = result.item2.name,
                    valueDensity = result.density2,
                    isWinner = !isItem1Winner
                )
            }

            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = TownAestheticDesign.ColorPalette.primary
                ),
                shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.sm)
            ) {
                Box(
                    modifier = Modifier.padding(TownAestheticDesign.Spacing.md),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = result.betterItemName,
                        fontSize = TownAestheticDesign.Typography.Size.base,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.md))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MetricCard(
                    "价值密度",
                    String.format(Locale.US, "%.2f", result.density1),
                    String.format(Locale.US, "%.2f", result.density2)
                )
                MetricCard(
                    "价格",
                    "${result.item1.price}元",
                    "${result.item2.price}元"
                )
                MetricCard(
                    "健康成本",
                    "${result.item1.healthCost}h",
                    "${result.item2.healthCost}h"
                )
            }
        }
    }
}

@Composable
fun ResultItem(name: String, valueDensity: Double, isWinner: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (isWinner) TownAestheticDesign.ColorPalette.success
                else TownAestheticDesign.ColorPalette.textDisabled
            ),
            shape = RoundedCornerShape(TownAestheticDesign.CornerRadius.sm)
        ) {
            Text(
                text = if (isWinner) "🏆" else "💧",
                fontSize = TownAestheticDesign.Typography.Size.`2xl`,
                modifier = Modifier.padding(TownAestheticDesign.Spacing.base)
            )
        }
        Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xs))
        Text(
            text = name.ifEmpty { "未命名" },
            fontSize = TownAestheticDesign.Typography.Size.base,
            fontWeight = FontWeight.SemiBold,
            color = TownAestheticDesign.ColorPalette.textPrimary
        )
        Text(
            text = "价值密度: ${String.format(Locale.US, "%.2f", valueDensity)}",
            fontSize = TownAestheticDesign.Typography.Size.sm,
            color = if (isWinner) TownAestheticDesign.ColorPalette.success
            else TownAestheticDesign.ColorPalette.textTertiary
        )
    }
}

@Composable
fun MetricCard(label: String, value1: String, value2: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = TownAestheticDesign.Typography.Size.xs,
            color = TownAestheticDesign.ColorPalette.textTertiary
        )
        Spacer(modifier = Modifier.height(TownAestheticDesign.Spacing.xs))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value1,
                fontSize = TownAestheticDesign.Typography.Size.sm,
                color = TownAestheticDesign.ColorPalette.accent
            )
            Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.sm))
            Text(
                text = "vs",
                fontSize = TownAestheticDesign.Typography.Size.xs,
                color = TownAestheticDesign.ColorPalette.textDisabled
            )
            Spacer(modifier = Modifier.width(TownAestheticDesign.Spacing.sm))
            Text(
                text = value2,
                fontSize = TownAestheticDesign.Typography.Size.sm,
                color = TownAestheticDesign.ColorPalette.warning
            )
        }
    }
}