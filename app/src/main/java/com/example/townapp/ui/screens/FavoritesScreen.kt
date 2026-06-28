package com.example.townapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.idiom.IdiomCritiqueLibrary
import com.example.townapp.data.prefs.IdiomPrefs
import com.example.townapp.ui.components.StandardTopBar
import com.example.townapp.ui.theme.DictionaryTokens

/**
 * 收藏词条列表页
 * 无收藏数量统计、无阅读任务、无打卡提醒，完全自愿标记
 */
@Composable
fun FavoritesScreen(
    onBack: () -> Unit,
    onMenuClick: () -> Unit = {},
    onIdiomClick: (String) -> Unit
) {
    val context = LocalContext.current
    val favoriteIds = IdiomPrefs.getFavoriteIds(context)
    val favoriteIdioms = favoriteIds.mapNotNull { id ->
        IdiomCritiqueLibrary.getAllIdioms().find { it.id == id }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StandardTopBar(
            title = "收藏词条",
            onMenuClick = onBack,
            menuIcon = "back",
            showSearch = false,
            onSearchClick = null
        )

        if (favoriteIdioms.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "你还没有收藏任何词条",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "看到有共鸣的观念可点击星标留存",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = DictionaryTokens.pagePadding,
                    vertical = DictionaryTokens.sectionSpacing
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(favoriteIdioms) { index, idiom ->
                    val tagColor = DictionaryTokens.toxicityTagColor(idiom.toxicityLevel)
                    val darkColor = DictionaryTokens.toxicityDarkColor(idiom.toxicityLevel)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onIdiomClick(idiom.id) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                        elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(tagColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = darkColor
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = idiom.idiom,
                                        fontSize = DictionaryTokens.Type.titleSize,
                                        fontWeight = DictionaryTokens.Type.titleWeight,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.weight(1f, fill = false)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(DictionaryTokens.radiusTag))
                                            .background(tagColor)
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = idiom.toxicityLevel.displayName,
                                            fontSize = DictionaryTokens.Type.tagSize,
                                            color = darkColor,
                                            fontWeight = DictionaryTokens.Type.tagWeight
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = idiom.traditionalMeaning.take(40) +
                                            if (idiom.traditionalMeaning.length > 40) "..." else "",
                                    fontSize = DictionaryTokens.Type.captionSize,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(DictionaryTokens.bottomSafePadding))
                }
            }
        }
    }
}
