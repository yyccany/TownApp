package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.example.townapp.feature.town_simulation.AestheticBusiness
import com.example.townapp.ui.design.TownAestheticDesign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AestheticLearningDashboard() {
    var detoxCompleted by remember { mutableStateOf(false) }
    var dailyDeleteCount by remember { mutableStateOf(25) }
    var weeklyReviewCount by remember { mutableStateOf(15) }
    var principlesMastered by remember { mutableStateOf(2) }
    
    val aestheticLevel = AestheticBusiness.calculateAestheticLevel(
        detoxCompleted,
        dailyDeleteCount,
        weeklyReviewCount,
        principlesMastered
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🎨",
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                        Text(
                            text = "万物薪俸小镇 - 审美学院",
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
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(TownAestheticDesign.ColorPalette.background)
                .graphicsLayer { clip = true },
            contentPadding = PaddingValues(
                vertical = 16.dp,
                horizontal = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
        ) {
            // 审美等级卡片
            item {
                AestheticLevelDisplay(
                    detoxCompleted = detoxCompleted,
                    dailyDeleteCount = dailyDeleteCount,
                    weeklyReviewCount = weeklyReviewCount,
                    principlesMastered = principlesMastered
                )
            }
            
            // 快速操作区
            item {
                QuickActionsCard(
                    onToggleDetox = { detoxCompleted = !detoxCompleted },
                    onIncrementDelete = { dailyDeleteCount++ },
                    onIncrementReview = { weeklyReviewCount++ },
                    onMasterPrinciple = { if (principlesMastered < 4) principlesMastered++ }
                )
            }
            
            // 每日灵感
            item {
                AestheticInspiration()
            }
            
            // 构图画廊
            item {
                CompositionGallery()
            }
            
            // 光影魔法师
            item {
                LightShadowGallery()
            }
            
            // 价值密度对比器
            item {
                AestheticComparator()
            }
            
            // 审美四大原则
            item {
                AestheticPrinciplesCardV2()
            }
            
            // 底部留白
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun QuickActionsCard(
    onToggleDetox: () -> Unit,
    onIncrementDelete: () -> Unit,
    onIncrementReview: () -> Unit,
    onMasterPrinciple: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.paddingSmall),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        colors = CardDefaults.cardColors(containerColor = TownAestheticDesign.ColorPalette.success.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⚡",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                Text(
                    text = "快速提升审美",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.primary
                )
            }
            
            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickActionButton(
                    icon = "🌊",
                    label = "完成排毒",
                    onClick = onToggleDetox,
                    color = TownAestheticDesign.ColorPalette.accent
                )
                QuickActionButton(
                    icon = "🗑️",
                    label = "每日一删",
                    onClick = onIncrementDelete,
                    color = TownAestheticDesign.ColorPalette.success
                )
                QuickActionButton(
                    icon = "🔍",
                    label = "每周一评",
                    onClick = onIncrementReview,
                    color = TownAestheticDesign.ColorPalette.warning
                )
                QuickActionButton(
                    icon = "✨",
                    label = "掌握原则",
                    onClick = onMasterPrinciple,
                    color = TownAestheticDesign.ColorPalette.gold
                )
            }
        }
    }
}

@Composable
fun QuickActionButton(
    icon: String,
    label: String,
    onClick: () -> Unit,
    color: Color
) {
    Card(
        modifier = Modifier
            .size(72.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )
            Text(
                text = label,
                fontSize = 10.sp,
                color = color,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AestheticPrinciplesCardV2() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.paddingSmall),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation),
        shape = RoundedCornerShape(AppDimens.radiusLarge)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📖",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                Text(
                    text = "审美四大原则",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TownAestheticDesign.ColorPalette.primary
                )
            }
            
            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
            
            PrincipleItem(
                number = "1",
                title = "功能优先",
                description = "不好用的东西，再好看也没有价值。形式服务于功能。",
                icon = "🎯",
                color = TownAestheticDesign.ColorPalette.accent
            )
            
            PrincipleItem(
                number = "2",
                title = "删繁就简",
                description = "去掉所有多余的东西，剩下的每一样都是不可或缺的。",
                icon = "✂️",
                color = TownAestheticDesign.ColorPalette.success
            )
            
            PrincipleItem(
                number = "3",
                title = "细节至上",
                description = "真正的美不在大的地方，在小的细节里。",
                icon = "🔬",
                color = TownAestheticDesign.ColorPalette.warning
            )
            
            PrincipleItem(
                number = "4",
                title = "全生命周期友好",
                description = "美要经得起时间的考验，适合30岁也适合80岁。",
                icon = "♾️",
                color = TownAestheticDesign.ColorPalette.gold
            )
        }
    }
}

@Composable
fun PrincipleItem(
    number: String,
    title: String,
    description: String,
    icon: String,
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = color),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = icon,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TownAestheticDesign.ColorPalette.textPrimary
                    )
                }
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = TownAestheticDesign.ColorPalette.textSecondary,
                    lineHeight = 1.4.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
