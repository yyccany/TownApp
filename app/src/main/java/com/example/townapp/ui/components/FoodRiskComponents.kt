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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.*

@Composable
fun RiskBadge(
    riskLevel: RiskLevel,
    modifier: Modifier = Modifier,
    size: Int = 8
) {
    val color = when(riskLevel) {
        RiskLevel.LOW -> Color(0xFFE8F5E9)      // 浅绿背景
        RiskLevel.MEDIUM -> Color(0xFFFFF3E0)    // 浅黄背景
        RiskLevel.HIGH -> Color(0xFFFFE0B2)      // 浅橙背景
        RiskLevel.CRITICAL -> Color(0xFFFFCDD2)  // 浅红背景
    }
    val textColor = when(riskLevel) {
        RiskLevel.LOW -> Color(0xFF4CAF50)
        RiskLevel.MEDIUM -> Color(0xFFFF9800)
        RiskLevel.HIGH -> Color(0xFFF57C00)
        RiskLevel.CRITICAL -> Color(0xFFF44336)
    }
    val text = when(riskLevel) {
        RiskLevel.LOW -> "\u26AA"
        RiskLevel.MEDIUM -> "\uD83D\uDFE8"
        RiskLevel.HIGH -> "\uD83D\uDFE7"
        RiskLevel.CRITICAL -> "\u274C"
    }

    Box(
        modifier = modifier
            .background(color, RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = (size).sp,
            color = textColor
        )
    }
}

@Composable
fun NutritionFactTable(
    nutrition: FoodNutrition,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("\u8425\u517B\u6210\u5206\u8868\uFF08\u6BCF100g\uFF09", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()

            NutritionRow("\u70ED\u91CF", "${nutrition.calorie.toInt()} kcal", Color(0xFFE74C3C))
            HorizontalDivider()
            NutritionRow("\u86CB\u767D\u8D28", "${nutrition.protein} g", Color(0xFF2ECC71))
            HorizontalDivider()
            NutritionRow("\u8102\u80AA", "${nutrition.fat} g", Color(0xFFF39C12))
            if (nutrition.sugar > 0) {
                HorizontalDivider()
                NutritionRow("  \u5176\u4E2D\u7CD6\u5206", "${nutrition.sugar} g", Color(0xFFE74C3C))
            }
            HorizontalDivider()
            NutritionRow("\u78B3\u6C34\u5316\u5408\u7269", "${nutrition.carbohydrate} g", Color(0xFF3498DB))
            HorizontalDivider()
            NutritionRow("\u81B3\u98DF\u7EA4\u7EF4", "${nutrition.fiber} g", Color(0xFF27AE60))
            HorizontalDivider()
            NutritionRow("\u7EF4\u751F\u7D20\u8BC4\u5206", "${nutrition.vitaminScore.toInt()}/100", Color(0xFF9B59B6))
            HorizontalDivider()
            NutritionRow("\u77FF\u7269\u8D28\u8BC4\u5206", "${nutrition.mineralScore.toInt()}/100", Color(0xFF8E44AD))

            Spacer(modifier = Modifier.height(8.dp))
            // 综合营养评分进度条
            val score = calculateNutritionScore(nutrition)
            val scoreColor = when {
                score >= 70 -> Color(0xFF2ECC71)
                score >= 50 -> Color(0xFFF39C12)
                else -> Color(0xFFE74C3C)
            }
            Text("\u7EFC\u5408\u8425\u517B\u8BC4\u5206", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f).height(8.dp).background(Color(0xFFE0E0E0), RoundedCornerShape(4.dp))) {
                    Box(modifier = Modifier.fillMaxWidth((score / 100f).toFloat()).height(8.dp).background(scoreColor, RoundedCornerShape(4.dp)))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("${score.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = scoreColor)
            }
        }
    }
}

@Composable
private fun NutritionRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = valueColor)
    }
}

@Composable
fun FoodRiskCard(
    risk: FoodRisk,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when(risk.riskLevel) {
                RiskLevel.LOW -> Color(0xFFF1F8E9)
                RiskLevel.MEDIUM -> Color(0xFFFFF8E1)
                RiskLevel.HIGH -> Color(0xFFFFF3E0)
                RiskLevel.CRITICAL -> Color(0xFFFFEBEE)
            }
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("\u98CE\u9669\u8BC4\u7EA7", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                RiskBadge(riskLevel = risk.riskLevel)
                Spacer(modifier = Modifier.width(4.dp))
                Text(risk.riskLevel.label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold,
                    color = when(risk.riskLevel) {
                        RiskLevel.LOW -> Color(0xFF4CAF50)
                        RiskLevel.MEDIUM -> Color(0xFFFF9800)
                        RiskLevel.HIGH -> Color(0xFFF57C00)
                        RiskLevel.CRITICAL -> Color(0xFFF44336)
                    })
            }
            Spacer(modifier = Modifier.height(8.dp))

            if (risk.greaseSugar > 0) RiskFactorBar("\u7CD6\u6CB9\u98CE\u9669", risk.greaseSugar, Color(0xFFF39C12))
            if (risk.heavyMetal > 0) RiskFactorBar("\u91CD\u91D1\u5C5E\u6C61\u67D3", risk.heavyMetal, Color(0xFFE74C3C))
            if (risk.pesticide > 0) RiskFactorBar("\u519C\u6B8B\u98CE\u9669", risk.pesticide, Color(0xFF9B59B6))
            if (risk.parasite > 0) RiskFactorBar("\u5BC4\u751F\u866B\u98CE\u9669", risk.parasite, Color(0xFFE67E22))
            if (risk.bacteria > 0) RiskFactorBar("\u5FAE\u751F\u7269\u98CE\u9669", risk.bacteria, Color(0xFF795548))

            if (risk.heavyMetal == 0.0 && risk.pesticide == 0.0 && risk.greaseSugar == 0.0 && risk.parasite == 0.0 && risk.bacteria == 0.0) {
                Text("\u65E0\u663E\u8457\u98CE\u9669\u6307\u6807", fontSize = 12.sp, color = Color(0xFF4CAF50))
            }
        }
    }
}

@Composable
private fun RiskFactorBar(label: String, value: Double, color: Color) {
    Column(modifier = Modifier.padding(vertical = 3.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("${value.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = color)
        }
        Spacer(modifier = Modifier.height(2.dp))
        val bgColor = Color(0xFFE0E0E0)
        Box(modifier = Modifier.fillMaxWidth().height(6.dp).background(bgColor, RoundedCornerShape(3.dp))) {
            Box(
                modifier = Modifier
                    .fillMaxWidth((value / 100f).toFloat().coerceIn(0f, 1f))
                    .height(6.dp)
                    .background(color, RoundedCornerShape(3.dp))
            )
        }
    }
}

@Composable
fun BodyStateIndicator(
    bodyLevel: BodyLevel,
    toxinAccumulation: Double = 0.0,
    modifier: Modifier = Modifier
) {
    val color = when(bodyLevel) {
        BodyLevel.HEALTHY -> Color(0xFF4CAF50)
        BodyLevel.SLIGHT_DISCOMFORT -> Color(0xFF8BC34A)
        BodyLevel.MODERATE_FATIGUE -> Color(0xFFFF9800)
        BodyLevel.OBVIOUS_DISCOMFORT -> Color(0xFFFF5722)
        BodyLevel.SEVERE_ILLNESS -> Color(0xFFF44336)
    }
    val emoji = when(bodyLevel) {
        BodyLevel.HEALTHY -> "\uD83D\uDC9A"
        BodyLevel.SLIGHT_DISCOMFORT -> "\uD83D\uDC9B"
        BodyLevel.MODERATE_FATIGUE -> "\uD83E\uDDE1"
        BodyLevel.OBVIOUS_DISCOMFORT -> "\u2764\uFE0F\u200D\uD83E\uDE79"
        BodyLevel.SEVERE_ILLNESS -> "\uD83D\uDDA4"
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(bodyLevel.label, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
                Text(bodyLevel.description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (toxinAccumulation > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("\u6BD2\u7D20\u7D2F\u79EF", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier.width(60.dp).height(6.dp).background(Color(0xFFE0E0E0), RoundedCornerShape(3.dp))) {
                            Box(modifier = Modifier.fillMaxWidth((toxinAccumulation / 100f).toFloat()).height(6.dp).background(Color(0xFFE74C3C), RoundedCornerShape(3.dp)))
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${toxinAccumulation.toInt()}%", fontSize = 10.sp, color = Color(0xFFE74C3C))
                    }
                }
            }
        }
    }
}

@Composable
fun EmotionModifierDisplay(
    mod: EmotionModifier,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5).copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("\u60C5\u7EEA\u5F71\u54CD\u8BC4\u4F30", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9B59B6))
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("\uD83D\uDE0A \u60C5\u7EEA\u53D8\u5316: ", fontSize = 13.sp)
                Text(
                    "${if (mod.moodDelta >= 0) "+" else ""}${mod.moodDelta}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (mod.moodDelta >= 0) Color(0xFF2ECC71) else Color(0xFFE74C3C)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("\u26A1 \u7CBE\u529B\u53D8\u5316: ", fontSize = 13.sp)
                Text(
                    "${if (mod.energyDelta >= 0) "+" else ""}${mod.energyDelta}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (mod.energyDelta >= 0) Color(0xFF2ECC71) else Color(0xFFE74C3C)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                mod.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

@Composable
fun AdultChildModeToggle(
    isAdultMode: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("\uD83D\uDC76", fontSize = 16.sp)
        Switch(
            checked = isAdultMode,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFF3498DB))
        )
        Text("\uD83E\uDDD1", fontSize = 16.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            if (isAdultMode) "\u6210\u4EBA\u6A21\u5F0F" else "\u5B69\u7AE5\u6A21\u5F0F",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}