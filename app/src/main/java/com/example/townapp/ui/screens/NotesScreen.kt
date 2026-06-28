package com.example.townapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.townapp.data.prefs.NotePrefs
import com.example.townapp.ui.components.StandardTopBar
import com.example.townapp.ui.theme.DictionaryTokens

/**
 * 思辨笔记汇总页面
 * 空白自由文本，没有答题模板、没有对错评判、没有自省任务，只做本地私密记录
 */
@Composable
fun NotesScreen(
    onBack: () -> Unit,
    onIdiomClick: (String) -> Unit
) {
    val context = LocalContext.current
    val allNotes = NotePrefs.getAllNotes(context)
    val notesWithIdioms = allNotes.mapNotNull { (idiomId, note) ->
        val idiom = IdiomCritiqueLibrary.getAllIdioms().find { it.id == idiomId }
        idiom?.let { it to note }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StandardTopBar(
            title = "思辨笔记",
            onMenuClick = onBack,
            menuIcon = "back",
            showSearch = false,
            onSearchClick = null
        )

        if (notesWithIdioms.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "还没有思辨笔记",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "在词条详情页写下你的真实想法，它们会出现在这里",
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
                items(notesWithIdioms) { (idiom, note) ->
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
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = idiom.idiom,
                                    fontSize = DictionaryTokens.Type.titleSize,
                                    fontWeight = DictionaryTokens.Type.titleWeight,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
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
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = note,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                lineHeight = 22.sp,
                                maxLines = 5
                            )
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
