package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.theme.BrandColors

/**
 * 🔬 专家模式面板组件
 * 默认隐藏的高级功能入口，用户可以自行选择开启
 */
@Composable
fun ExpertModePanel(
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onParallelTimeline: () -> Unit,
    onLongTermCalculator: () -> Unit,
    onAnonymousSquare: () -> Unit,
    onLifeTimeline: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingSmall)
    ) {
        // 展开/收起按钮
        ExpertModeToggleButton(
            isExpanded = isExpanded,
            onClick = onToggleExpand
        )
        
        // 专家模式功能列表
        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppDimens.paddingMedium)
            ) {
                Text(
                    text = "🔬 专家模式",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandColors.TextPrimary,
                    modifier = Modifier.padding(bottom = AppDimens.paddingSmall)
                )
                
                Text(
                    text = "高级分析功能，可能需要更多数据支持",
                    fontSize = 12.sp,
                    color = BrandColors.TextSecondary,
                    modifier = Modifier.padding(bottom = AppDimens.paddingMedium)
                )
                
                // 功能列表
                ExpertModeItem(
                    icon = "⏳",
                    title = "平行人生",
                    description = "模拟如果你做了不同选择，人生会是什么样子",
                    onClick = onParallelTimeline
                )
                
                ExpertModeItem(
                    icon = "📊",
                    title = "长期成本计算器",
                    description = "计算一个物品10年的总拥有成本",
                    onClick = onLongTermCalculator
                )
                
                ExpertModeItem(
                    icon = "🌐",
                    title = "匿名生活广场",
                    description = "匿名分享自己的生活方式，没有点赞没有评论",
                    onClick = onAnonymousSquare
                )
                
                ExpertModeItem(
                    icon = "📅",
                    title = "时间线回顾",
                    description = "生成你在小镇的年度生活报告",
                    onClick = onLifeTimeline
                )
            }
        }
    }
}

/**
 * 专家模式展开/收起按钮
 */
@Composable
fun ExpertModeToggleButton(
    isExpanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(AppDimens.radiusMedium))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        BrandColors.Purple.copy(alpha = 0.1f),
                        BrandColors.Blue.copy(alpha = 0.1f)
                    )
                )
            )
            .border(1.dp, BrandColors.Purple.copy(alpha = 0.3f), RoundedCornerShape(AppDimens.radiusMedium))
            .clickable(role = Role.Button) { onClick() }
            .padding(16.dp)
            .semantics {
                contentDescription = if (isExpanded) "收起专家模式" else "展开专家模式"
                role = Role.Button
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🔬", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
            Text(
                text = "专家模式",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = BrandColors.Purple
            )
        }
        
        Text(
            text = if (isExpanded) "▲" else "▼",
            fontSize = 12.sp,
            color = BrandColors.Purple
        )
    }
}

/**
 * 专家模式功能项
 */
@Composable
fun ExpertModeItem(
    icon: String,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(AppDimens.radiusMedium))
            .background(Color.White)
            .border(1.dp, BrandColors.CardBorder, RoundedCornerShape(AppDimens.radiusMedium))
            .clickable(role = Role.Button) { onClick() }
            .padding(16.dp)
            .semantics {
                contentDescription = "$title：$description"
                role = Role.Button
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = icon, fontSize = 24.sp)
        Spacer(modifier = Modifier.width(AppDimens.paddingLarge))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = BrandColors.TextPrimary
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = BrandColors.TextSecondary
            )
        }
        Text(text = "→", fontSize = 16.sp, color = BrandColors.TextTertiary)
    }
}

/**
 * 专家模式开关组件（用于设置页面）
 */
@Composable
fun ExpertModeSwitch(
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium)
            .clip(RoundedCornerShape(AppDimens.radiusMedium))
            .background(Color.White)
            .border(1.dp, BrandColors.CardBorder, RoundedCornerShape(AppDimens.radiusMedium))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🔬", fontSize = 24.sp)
            Spacer(modifier = Modifier.width(AppDimens.paddingLarge))
            Column {
                Text(
                    text = "专家模式",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = BrandColors.TextPrimary
                )
                Text(
                    text = "显示高级分析功能",
                    fontSize = 12.sp,
                    color = BrandColors.TextSecondary
                )
            }
        }
        
        Switch(
            checked = isEnabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = BrandColors.Purple,
                checkedTrackColor = BrandColors.Purple.copy(alpha = 0.3f)
            )
        )
    }
}
