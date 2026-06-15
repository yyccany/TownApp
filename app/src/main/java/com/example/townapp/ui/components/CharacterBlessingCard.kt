package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import com.example.townapp.data.companion.AnniversaryTracker
import com.example.townapp.data.companion.SurpriseManager
import com.example.townapp.ui.CompanionColors
import com.example.townapp.ui.theme.BrandColors

/**
 * 🎉 角色祝福卡片组件
 * 在用户打开APP时显示生日、周年纪念日、随机惊喜的祝福
 */
@Composable
fun CharacterBlessingCard(
    blessing: SurpriseManager.CharacterBlessing,
    onDismiss: () -> Unit,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val themeColor = CompanionColors.getThemeColor(blessing.characterId)
    val lightColor = CompanionColors.getLightColor(blessing.characterId)
    
    val typeIcon = when (blessing.type) {
        SurpriseManager.BlessingType.BIRTHDAY -> "🎂"
        SurpriseManager.BlessingType.ANNIVERSARY -> "💛"
        SurpriseManager.BlessingType.SURPRISE -> "🎁"
    }
    
    val typeText = when (blessing.type) {
        SurpriseManager.BlessingType.BIRTHDAY -> "今天是${blessing.characterName}的生日"
        SurpriseManager.BlessingType.ANNIVERSARY -> "小镇周年纪念日"
        SurpriseManager.BlessingType.SURPRISE -> "突然出现"
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingSmall)
            .clip(RoundedCornerShape(AppDimens.radiusLarge))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        lightColor,
                        Color.White
                    )
                )
            )
            .border(1.5.dp, themeColor.copy(alpha = 0.5f), RoundedCornerShape(AppDimens.radiusLarge))
            .clickable(role = Role.Button) { onClick() }
            .padding(16.dp)
            .semantics {
                contentDescription = "$typeText: ${blessing.quoteText}"
                role = Role.Button
            }
    ) {
        Column {
            // 顶部类型标签
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = AppDimens.paddingSmall)
            ) {
                Text(text = typeIcon, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = typeText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = themeColor
                )
            }
            
            // 祝福主体
            Row(verticalAlignment = Alignment.Top) {
                Text(text = blessing.characterEmoji, fontSize = 40.sp)
                Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = blessing.characterName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = blessing.quoteText,
                        fontSize = 15.sp,
                        color = BrandColors.TextPrimary,
                        lineHeight = 22.sp
                    )
                }
            }
            
            // 关闭按钮
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppDimens.paddingMedium),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.semantics {
                        contentDescription = "关闭祝福"
                        role = Role.Button
                    }
                ) {
                    Text(
                        text = "收到了 💕",
                        fontSize = 12.sp,
                        color = themeColor
                    )
                }
            }
        }
    }
}

/**
 * 📜 多角色祝福列表组件
 * 一次性显示多个角色的祝福（如生日时所有角色都来祝贺）
 */
@Composable
fun CharacterBlessingList(
    blessings: List<SurpriseManager.CharacterBlessing>,
    onDismiss: (SurpriseManager.CharacterBlessing) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        blessings.forEach { blessing ->
            CharacterBlessingCard(
                blessing = blessing,
                onDismiss = { onDismiss(blessing) }
            )
        }
    }
}

/**
 * 💕 小镇陪伴信息组件
 * 显示"已经认识X天"等温暖信息
 */
@Composable
fun AnniversaryInfoCard(
    info: AnniversaryTracker.AnniversaryInfo,
    modifier: Modifier = Modifier
) {
    if (info.daysTogether == 0) return
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.paddingLarge, vertical = 4.dp)
            .clip(RoundedCornerShape(AppDimens.radiusMedium))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        BrandColors.Blue.copy(alpha = 0.08f),
                        BrandColors.Purple.copy(alpha = 0.08f)
                    )
                )
            )
            .padding(12.dp)
            .semantics {
                contentDescription = "我们已经认识${info.daysTogether}天了"
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "💛", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "我们已经认识 ${info.daysTogether} 天了",
                fontSize = 12.sp,
                color = BrandColors.TextSecondary
            )
            if (info.yearsTogether > 0) {
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "（${info.yearsTogether} 周年）",
                    fontSize = 12.sp,
                    color = BrandColors.Blue,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
