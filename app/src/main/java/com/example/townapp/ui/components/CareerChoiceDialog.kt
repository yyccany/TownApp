package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.townapp.data.CareerPathSystem
import com.example.townapp.ui.theme.AppDimens
import com.example.townapp.ui.theme.BrandColors

/**
 * 职业选择弹窗 — 直接显示全部职业的简单列表
 */
@Composable
fun CareerChoiceDialog(
    recommendedPath: CareerPathSystem.CareerPathType,
    onCareerSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(AppDimens.radiusXLarge),
            color = BrandColors.PageBg,
            tonalElevation = AppDimens.cardElevation,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(AppDimens.paddingLarge)) {
                // 标题栏
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "选择职业",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandColors.TextPrimary
                        )
                        Text(
                            text = "系统推荐：${recommendedPath.label}",
                            fontSize = 12.sp,
                            color = BrandColors.TextSecondary
                        )
                    }
                    TextButton(onClick = onDismiss) {
                        Text("关闭", fontSize = 13.sp, color = BrandColors.TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                // 职业列表
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
                ) {
                    items(CareerPathSystem.allCareers, key = { it.id }) { career ->
                        val isRecommended = career.pathType == recommendedPath
                        CareerCard(
                            career = career,
                            isRecommended = isRecommended,
                            onSelect = { onCareerSelected(career.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CareerCard(
    career: CareerPathSystem.CareerOption,
    isRecommended: Boolean,
    onSelect: () -> Unit
) {
    val highlightColor = when (career.pathType) {
        CareerPathSystem.CareerPathType.STABLE -> BrandColors.GreenHighlight
        CareerPathSystem.CareerPathType.CORPORATE -> BrandColors.BlueHighlight
        CareerPathSystem.CareerPathType.FREELANCE -> BrandColors.PurpleHighlight
        CareerPathSystem.CareerPathType.BUSINESS -> BrandColors.OrangeHighlight
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(AppDimens.cardElevation, RoundedCornerShape(AppDimens.radiusCard))
            .clip(RoundedCornerShape(AppDimens.radiusCard))
            .background(Color.White)
            .border(1.dp, BrandColors.CardBorder, RoundedCornerShape(AppDimens.radiusCard))
    ) {
        if (highlightColor != Color.Transparent) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.linearGradient(
                            0.0f to Color.Transparent,
                            1.0f to highlightColor,
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
                        )
                    )
            )
        }

        Column(modifier = Modifier.padding(AppDimens.paddingCard)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = career.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = BrandColors.TextPrimary
                )
                if (isRecommended) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = BrandColors.Blue
                    ) {
                        Text(
                            text = "推荐",
                            fontSize = 10.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = career.description,
                fontSize = 12.sp,
                color = BrandColors.TextSecondary,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            CareerStatsRow(career)

            if (career.startupCost > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "启动资金：${career.startupCost.toInt()}元",
                    fontSize = 11.sp,
                    color = BrandColors.Red
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onSelect,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppDimens.radiusSmall),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandColors.Blue,
                    contentColor = Color.White
                )
            ) {
                Text("选择${career.name}", fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun CareerStatsRow(career: CareerPathSystem.CareerOption) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatItem("月薪", if (career.baseSalary > 0) "${career.baseSalary.toInt()}" else "无", "元")
        StatItem("年增长", "${(career.salaryGrowth * 100).toInt()}", "%")
        StatItem("加班", "${(career.overtimeRate * 100).toInt()}", "%")
        StatItem("裁员风险", "${(career.layoffRisk * 100).toInt()}", "%")
    }
}

@Composable
private fun StatItem(label: String, value: String, unit: String) {
    Column {
        Text(
            text = label,
            fontSize = 10.sp,
            color = BrandColors.TextTertiary
        )
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = BrandColors.TextPrimary
            )
            Text(
                text = unit,
                fontSize = 10.sp,
                color = BrandColors.TextTertiary,
                modifier = Modifier.padding(start = 1.dp, bottom = 1.dp)
            )
        }
    }
}