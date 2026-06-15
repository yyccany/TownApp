package com.example.townapp.ui.screens

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.idiom.IdiomCategory
import com.example.townapp.data.idiom.IdiomCritique
import com.example.townapp.data.idiom.IdiomCritiqueLibrary

/**
 * 成语批判解读手册页面
 * 用小镇的世界观重新解读传统成语
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdiomCriticScreen(
    onBack: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf<IdiomCategory?>(null) }
    var selectedIdiom by remember { mutableStateOf<IdiomCritique?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            // 显示单个成语详情
            selectedIdiom != null -> {
                IdiomCritiqueDetailScreen(
                    idiom = selectedIdiom!!,
                    onBack = { selectedIdiom = null }
                )
            }
            // 显示某个分类的成语列表
            selectedCategory != null -> {
                IdiomCategoryListScreen(
                    category = selectedCategory!!,
                    idioms = IdiomCritiqueLibrary.getIdiomsByCategory(selectedCategory!!),
                    onIdiomClick = { selectedIdiom = it },
                    onBack = { selectedCategory = null }
                )
            }
            // 显示分类总览
            else -> {
                IdiomCategoryOverviewScreen(
                    onCategoryClick = { selectedCategory = it },
                    onBack = onBack
                )
            }
        }
    }
}

/**
 * 分类总览页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IdiomCategoryOverviewScreen(
    onCategoryClick: (IdiomCategory) -> Unit,
    onBack: () -> Unit
) {
    val categories = IdiomCategory.entries

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5E6D3))
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        "📖 成语批判解读手册",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        "拆穿几千年的规训",
                        fontSize = 12.sp,
                        color = Color(0xFF8B7355)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Text("←", fontSize = 20.sp)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFF5E6D3)
            )
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(AppDimens.radiusLarge)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "💡 小镇的世界观",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF4A3728)
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                Text(
                    "所有的成语，都是古代宗族社会、集体社会的产物。\n" +
                    "它们的核心，都是要你牺牲自己、成全别人。\n\n" +
                    "但我们的小镇，不是这样的。\n" +
                    "我们以人为本，永远不说「你应该」。\n" +
                    "你自己，才是最重要的。",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    lineHeight = 20.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(AppDimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
        ) {
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    count = IdiomCritiqueLibrary.getIdiomsByCategory(category).size,
                    onClick = { onCategoryClick(category) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                Text(
                    "💛 现实很苦，但你很甜。\n不管你是什么样的，你都值得被爱。",
                    fontSize = 14.sp,
                    color = Color(0xFF8B7355),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun CategoryCard(
    category: IdiomCategory,
    count: Int,
    onClick: () -> Unit
) {
    val color = Color(category.colorHex)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.cardElevation)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(category.emoji, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(AppDimens.paddingLarge))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    category.displayName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF4A3728)
                )
                Text(
                    category.description,
                    fontSize = 13.sp,
                    color = Color(0xFF666666)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${count}个",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = color
                )
                Text(
                    "成语",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IdiomCategoryListScreen(
    category: IdiomCategory,
    idioms: List<IdiomCritique>,
    onIdiomClick: (IdiomCritique) -> Unit,
    onBack: () -> Unit
) {
    val color = Color(category.colorHex)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5E6D3))
    ) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(category.emoji, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                    Text(category.displayName, fontWeight = FontWeight.Bold)
                }
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Text("←", fontSize = 20.sp)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = color.copy(alpha = 0.3f)
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(AppDimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(idioms) { index, idiom ->
                IdiomListItem(
                    idiom = idiom,
                    index = index + 1,
                    onClick = { onIdiomClick(idiom) }
                )
            }
        }
    }
}

@Composable
private fun IdiomListItem(
    idiom: IdiomCritique,
    index: Int,
    onClick: () -> Unit
) {
    val color = Color(idiom.category.colorHex)
    val toxicityColor = Color(idiom.toxicityLevel.colorHex)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "$index",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = color
                )
            }

            Spacer(modifier = Modifier.width(AppDimens.paddingMedium))

            Text(
                idiom.idiom,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF4A3728),
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(toxicityColor.copy(alpha = 0.2f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    idiom.toxicityLevel.emoji,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.width(AppDimens.paddingSmall))

            Text("→", fontSize = 16.sp, color = Color.Gray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IdiomCritiqueDetailScreen(
    idiom: IdiomCritique,
    onBack: () -> Unit
) {
    val color = Color(idiom.category.colorHex)
    val toxicityColor = Color(idiom.toxicityLevel.colorHex)

    val infiniteTransition = rememberInfiniteTransition(label = "bounce")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        color.copy(alpha = 0.3f),
                        Color(0xFFF5E6D3)
                    )
                )
            )
    ) {
        TopAppBar(
            title = { Text(idiom.category.displayName, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Text("←", fontSize = 20.sp)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = color.copy(alpha = 0.3f)
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(AppDimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingLarge)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(AppDimens.radiusXLarge)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.scale(scale),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(idiom.category.emoji, fontSize = 48.sp)
                        }

                        Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                        Text(
                            idiom.idiom,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color(0xFF4A3728)
                        )

                        Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(AppDimens.radiusSmall))
                                    .background(toxicityColor.copy(alpha = 0.3f))
                                    .padding(horizontal = AppDimens.paddingMedium, vertical = 6.dp)
                            ) {
                                Text(
                                    "${idiom.toxicityLevel.emoji} ${idiom.toxicityLevel.displayName}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = toxicityColor
                                )
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = toxicityColor.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(AppDimens.radiusLarge)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "${idiom.toxicityLevel.emoji} 毒性等级判定",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = toxicityColor
                        )
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            idiom.toxicityLevel.definition,
                            fontSize = 13.sp,
                            color = Color(0xFF666666)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "小镇态度：${idiom.toxicityLevel.townAttitude}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = toxicityColor
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0E0)),
                    shape = RoundedCornerShape(AppDimens.radiusLarge)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "❌ 传统解读（规训版本）",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFFCC0000)
                        )
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            idiom.traditionalMeaning,
                            fontSize = 15.sp,
                            color = Color(0xFF666666),
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            item {
                if (idiom.distortedTruth.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                        shape = RoundedCornerShape(AppDimens.radiusLarge)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "🔍 异化真相",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFFFF8C00)
                            )
                            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                            Text(
                                idiom.distortedTruth,
                                fontSize = 15.sp,
                                color = Color(0xFF5D4037),
                                lineHeight = 22.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(AppDimens.radiusLarge)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "💛 小镇批判解读",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = color
                        )
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            idiom.townPerspective,
                            fontSize = 16.sp,
                            color = Color(0xFF4A3728),
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            item {
                var expandSpotlight by remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF9C3)),
                    shape = RoundedCornerShape(AppDimens.radiusLarge)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandSpotlight = !expandSpotlight },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "✨ 时代闪光点",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFFCA8A04)
                            )
                            Text(text = if (expandSpotlight) "▼" else "▶", fontSize = 12.sp, color = Color.Gray)
                        }
                        if (expandSpotlight) {
                            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                            idiom.spotlights.forEachIndexed { index, spotlight ->
                                Column(modifier = Modifier.padding(bottom = if (index < idiom.spotlights.size - 1) 8.dp else 0.dp)) {
                                    Row(verticalAlignment = Alignment.Top) {
                                        Text(
                                            text = "${spotlight.category.emoji} ",
                                            fontSize = 15.sp
                                        )
                                        Text(
                                            text = spotlight.content,
                                            fontSize = 15.sp,
                                            color = Color(0xFF4A3728),
                                            lineHeight = 22.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(AppDimens.radiusLarge)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("💡", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
                        Text(
                            idiom.keyMessage,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A3728),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
                Text(
                    "💛 现实很苦，但你很甜。\n不管你是什么样的，你都值得被爱。",
                    fontSize = 14.sp,
                    color = Color(0xFF8B7355),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}