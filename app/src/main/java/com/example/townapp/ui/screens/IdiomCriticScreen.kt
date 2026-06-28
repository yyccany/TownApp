package com.example.townapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.idiom.IdiomCategory
import com.example.townapp.data.idiom.IdiomCritique
import com.example.townapp.data.idiom.IdiomCritiqueLibrary
import com.example.townapp.data.idiom.ToxicityLevel
import com.example.townapp.data.prefs.IdiomPrefs
import com.example.townapp.data.prefs.NotePrefs
import com.example.townapp.ui.components.StandardTopBar
import com.example.townapp.ui.theme.DictionaryTokens
import com.example.townapp.core.state.UserPreferencesManager
import com.example.townapp.core.theme.LocalVeraColors
import kotlinx.coroutines.launch

/**
 * 小镇认知字典页面
 * 用小镇的世界观重新解读传统词条
 */
@Composable
fun IdiomCriticScreen(
    initialIdiomId: String? = null,
    initialCategory: IdiomCategory? = null,
    onBack: () -> Unit,
    onMenuClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onBiasTagSearch: (String) -> Unit = {}
) {
    val initialIdiom = remember(initialIdiomId) {
        initialIdiomId?.let { id -> IdiomCritiqueLibrary.getAllIdioms().find { it.id == id } }
    }
    var selectedCategory by remember { mutableStateOf<IdiomCategory?>(initialCategory) }
    var selectedIdiom by remember { mutableStateOf<IdiomCritique?>(initialIdiom) }

    val entryPoint = remember {
        when {
            initialIdiomId != null -> "idiom"
            initialCategory != null -> "category"
            else -> "overview"
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            selectedIdiom != null -> {
                IdiomCritiqueDetailScreen(
                    idiom = selectedIdiom!!,
                    onBack = {
                        if (entryPoint == "idiom" && selectedIdiom?.id == initialIdiomId) {
                            onBack()
                        } else {
                            selectedIdiom = null
                        }
                    },
                    onMenuClick = onMenuClick,
                    onSearchClick = onSearchClick,
                    onNavigateToIdiom = { newId ->
                        selectedIdiom = IdiomCritiqueLibrary.getById(newId)
                    },
                    onBiasTagSearch = onBiasTagSearch
                )
            }
            selectedCategory != null -> {
                IdiomCategoryListScreen(
                    category = selectedCategory!!,
                    idioms = IdiomCritiqueLibrary.getIdiomsByCategory(selectedCategory!!),
                    onIdiomClick = { selectedIdiom = it },
                    onBack = {
                        if (entryPoint == "category") {
                            onBack()
                        } else {
                            selectedCategory = null
                        }
                    },
                    onMenuClick = onMenuClick
                )
            }
            else -> {
                IdiomCategoryOverviewScreen(
                    onCategoryClick = { selectedCategory = it },
                    onBack = onBack,
                    onMenuClick = onMenuClick
                )
            }
        }
    }
}

/**
 * 分类总览页面
 */
@Composable
private fun IdiomCategoryOverviewScreen(
    onCategoryClick: (IdiomCategory) -> Unit,
    onBack: () -> Unit,
    onMenuClick: () -> Unit
) {
    val categories = IdiomCategory.entries

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StandardTopBar(
            title = "小镇认知字典",
            onMenuClick = onBack,
            menuIcon = "back"
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = DictionaryTokens.pagePadding,
                vertical = DictionaryTokens.sectionSpacing
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = DictionaryTokens.cardExample),
                    shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            "关于这本字典",
                            fontWeight = FontWeight.Bold,
                            fontSize = DictionaryTokens.Type.headingSize,
                            color = DictionaryTokens.textHeadingThemed()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "每个词条背后，都藏着一套认知框架。\n" +
                            "有些框架在规训你、消耗你；\n" +
                            "有些框架在帮你理解世界运行的真实规律。\n\n" +
                            "这本字典有两部分：\n" +
                            "批判那些有毒的观念，拆穿它们的规训本质\n" +
                            "展示小镇的认知法则，理解时间、财富与身心的真实关系",
                            fontSize = DictionaryTokens.Type.bodySize,
                            color = DictionaryTokens.textBodyThemed(),
                            lineHeight = DictionaryTokens.Type.bodyLineHeight
                        )
                    }
                }
            }

            items(categories) { category ->
                OverviewCategoryCard(
                    category = category,
                    count = IdiomCritiqueLibrary.getIdiomsByCategory(category).size,
                    onClick = { onCategoryClick(category) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(DictionaryTokens.sectionSpacing))
                Text(
                    "词语只是框架，理解才是自由。\n认识这些词条背后的逻辑，你就能做出更清醒的选择。",
                    fontSize = DictionaryTokens.Type.captionSize,
                    color = DictionaryTokens.textCaptionThemed(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(DictionaryTokens.bottomSafePadding))
            }
        }
    }
}

@Composable
private fun OverviewCategoryCard(
    category: IdiomCategory,
    count: Int,
    onClick: () -> Unit
) {
    val accentColor = DictionaryTokens.categoryDarkColor(category)
    val tagColor = DictionaryTokens.categoryTagColor(category)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithScale { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(DictionaryTokens.radiusCard),
        elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DictionaryTokens.cardPaddingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(tagColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    category.displayName.first().toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    category.displayName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    category.description,
                    fontSize = DictionaryTokens.Type.captionSize,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${count}个",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = accentColor
                )
                Text(
                    "词条",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
private fun IdiomCategoryListScreen(
    category: IdiomCategory,
    idioms: List<IdiomCritique>,
    onIdiomClick: (IdiomCritique) -> Unit,
    onBack: () -> Unit,
    onMenuClick: () -> Unit
) {
    val accentColor = DictionaryTokens.categoryDarkColor(category)
    val tagColor = DictionaryTokens.categoryTagColor(category)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StandardTopBar(
            title = category.displayName,
            onMenuClick = onBack,
            menuIcon = "back"
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = DictionaryTokens.pagePadding,
                vertical = DictionaryTokens.sectionSpacing
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(DictionaryTokens.radiusTag))
                            .background(tagColor)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${idioms.size}个词条",
                            fontSize = DictionaryTokens.Type.tagSize,
                            color = accentColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            itemsIndexed(idioms) { index, idiom ->
                IdiomListItem(
                    idiom = idiom,
                    index = index + 1,
                    onClick = { onIdiomClick(idiom) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(DictionaryTokens.bottomSafePadding))
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
    val tagColor = DictionaryTokens.toxicityTagColor(idiom.toxicityLevel)
    val darkColor = DictionaryTokens.toxicityDarkColor(idiom.toxicityLevel)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithScale { onClick() },
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
            // 圆形序号，底色与标签色系匹配
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(tagColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$index",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkColor
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
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

// ============================================
// 词条详情页（含上/下一篇 + 随机词条连续阅读）
// ============================================

@Composable
private fun IdiomCritiqueDetailScreen(
    idiom: IdiomCritique,
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNavigateToIdiom: (String) -> Unit,
    onBiasTagSearch: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    // 收藏状态
    var isFavorite by remember(idiom.id) {
        mutableStateOf(IdiomPrefs.isFavorite(context, idiom.id))
    }

    // 思辨笔记状态
    var noteText by remember(idiom.id) {
        mutableStateOf(NotePrefs.getNote(context, idiom.id))
    }
    var noteExpanded by remember(idiom.id) {
        mutableStateOf(false)
    }

    // 切换词条时滚动回顶部
    LaunchedEffect(idiom.id) {
        listState.scrollToItem(0)
    }

    // 同分类词条列表（用于上/下一篇）
    val categoryidioms = remember(idiom.category) {
        IdiomCritiqueLibrary.getIdiomsByCategory(idiom.category)
    }
    val currentIndex = remember(idiom.id, categoryidioms) {
        categoryidioms.indexOfFirst { it.id == idiom.id }
    }
    val prevIdiom = remember(currentIndex, categoryidioms) {
        if (categoryidioms.isNotEmpty() && currentIndex >= 0) {
            val prevIndex = if (currentIndex == 0) categoryidioms.size - 1 else currentIndex - 1
            categoryidioms[prevIndex]
        } else null
    }
    val nextIdiom = remember(currentIndex, categoryidioms) {
        if (categoryidioms.isNotEmpty() && currentIndex >= 0) {
            val nextIndex = if (currentIndex == categoryidioms.size - 1) 0 else currentIndex + 1
            categoryidioms[nextIndex]
        } else null
    }
    val randomIdiom = remember(idiom.id) {
        val all = IdiomCritiqueLibrary.getAllIdioms().filter { it.id != idiom.id }
        if (all.isNotEmpty()) all.random() else null
    }

    // 批量展开/收起状态
    val scope = rememberCoroutineScope()
    val moduleKeys = listOf("definition", "examples", "core", "tip", "risk")
    var batchAllExpanded by remember(idiom.id) { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StandardTopBar(
            title = idiom.category.displayName,
            onMenuClick = onBack,
            menuIcon = "back",
            showSearch = true,
            onSearchClick = onSearchClick,
            actions = {
                // 批量展开/收起图标按钮（纯黑单线）
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
                        .clickable {
                            scope.launch {
                                val newState = !batchAllExpanded
                                for (key in moduleKeys) {
                                    com.example.townapp.core.state.UserPreferencesManager
                                        .setCardExpandState(context, idiom.id, key, newState)
                                }
                                batchAllExpanded = newState
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    ExpandCollapseIcon(
                        expanded = batchAllExpanded,
                        tint = LocalVeraColors.current.iconExpandCollapse,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = DictionaryTokens.pagePadding,
                vertical = DictionaryTokens.sectionSpacing
            ),
            verticalArrangement = Arrangement.spacedBy(DictionaryTokens.sectionSpacing)
        ) {
            // 头部标题区域
            item(key = "header_${idiom.id}") {
                DetailHeader(
                    idiom = idiom,
                    isFavorite = isFavorite,
                    onToggleFavorite = {
                        isFavorite = IdiomPrefs.toggleFavorite(context, idiom.id)
                    },
                    onBiasTagClick = onBiasTagSearch
                )
            }

            // 基础定义：纯白卡片
            item(key = "definition_${idiom.id}") {
                ContentCard(backgroundColor = DictionaryTokens.cardDefinition) {
                    CollapsibleSection(
                        title = "基础定义",
                        moduleKey = "definition",
                        idiomId = idiom.id
                    ) {
                        Text(
                            text = idiom.traditionalMeaning,
                            fontSize = DictionaryTokens.Type.bodySize,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            lineHeight = DictionaryTokens.Type.bodyLineHeight
                        )
                    }
                }
            }

            // 现实案例
            if (idiom.examples.isNotEmpty()) {
                item(key = "examples_${idiom.id}") {
                    ContentCard(backgroundColor = DictionaryTokens.cardBg) {
                        CollapsibleSection(
                            title = "现实案例",
                            moduleKey = "examples",
                            idiomId = idiom.id
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                idiom.examples.forEach { example ->
                                    ExampleItem(text = example)
                                }
                            }
                        }
                    }
                }
            }

            // 小镇辩证解读（核心）
            item(key = "core_${idiom.id}") {
                ContentCard(backgroundColor = DictionaryTokens.cardBg) {
                    CollapsibleSection(
                        title = "小镇辩证解读",
                        moduleKey = "core",
                        idiomId = idiom.id
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            // 五层视角拆分渲染（空心单线圆圈序号）
                            TownPerspectiveContent(text = idiom.townPerspective)
                            // 警示提示合并至模块末尾
                            if (idiom.spotlights.isNotEmpty()) {
                                SpotlightsBlock(spotlights = idiom.spotlights)
                            }
                        }
                    }
                }
            }

            // 可操作校准技巧
            if (idiom.actionableTip.isNotEmpty()) {
                item(key = "tip_${idiom.id}") {
                    ContentCard(backgroundColor = DictionaryTokens.cardBg) {
                        CollapsibleSection(
                            title = "可操作校准技巧",
                            moduleKey = "tip",
                            idiomId = idiom.id
                        ) {
                            Text(
                                text = idiom.actionableTip,
                                fontSize = DictionaryTokens.Type.bodySize,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                lineHeight = DictionaryTokens.Type.bodyLineHeight,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // 常见误读与滥用风险
            item(key = "risk_${idiom.id}") {
                ContentCard(backgroundColor = DictionaryTokens.cardBg) {
                    CollapsibleSection(
                        title = "常见误读与滥用风险",
                        moduleKey = "risk",
                        idiomId = idiom.id
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            if (idiom.distortedTruth.isNotEmpty()) {
                                Text(
                                    text = idiom.distortedTruth,
                                    fontSize = DictionaryTokens.Type.bodySize,
                                    color = DictionaryTokens.textBodyThemed(),
                                    lineHeight = DictionaryTokens.Type.bodyLineHeight
                                )
                            }
                            val isTownWisdom = idiom.toxicityLevel == ToxicityLevel.TOWN_WISDOM
                            Text(
                                text = "${if (isTownWisdom) "认知类型" else "大众认知偏差"}：${idiom.toxicityLevel.displayName}",
                                fontSize = DictionaryTokens.Type.captionSize,
                                color = DictionaryTokens.textCaptionThemed(),
                                lineHeight = DictionaryTokens.Type.captionLineHeight
                            )
                            Text(
                                text = "说明：${idiom.toxicityLevel.definition}",
                                fontSize = DictionaryTokens.Type.captionSize,
                                color = DictionaryTokens.textCaptionThemed(),
                                lineHeight = DictionaryTokens.Type.captionLineHeight
                            )
                            Text(
                                text = "小镇态度：${idiom.toxicityLevel.townAttitude}",
                                fontSize = DictionaryTokens.Type.captionSize,
                                color = DictionaryTokens.textCaptionThemed(),
                                lineHeight = DictionaryTokens.Type.captionLineHeight
                            )
                        }
                    }
                }
            }

            // 核心启示（标准ContentCard结构）
            item(key = "keymsg_${idiom.id}") {
                ContentCard(backgroundColor = DictionaryTokens.cardBg) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "核心启示",
                            fontSize = DictionaryTokens.Type.headingSize,
                            fontWeight = DictionaryTokens.Type.headingWeight,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                        )
                        Text(
                            text = idiom.keyMessage,
                            fontSize = DictionaryTokens.Type.bodySize,
                            fontWeight = FontWeight.Medium,
                            color = DictionaryTokens.textTitleThemed(),
                            lineHeight = DictionaryTokens.Type.bodyLineHeight
                        )
                    }
                }
            }

            // 思辨笔记卡片
            item(key = "note_${idiom.id}") {
                val noteColors = LocalVeraColors.current
                ContentCard(backgroundColor = DictionaryTokens.cardBg) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "思辨笔记",
                                fontSize = DictionaryTokens.Type.headingSize,
                                fontWeight = DictionaryTokens.Type.headingWeight,
                                color = DictionaryTokens.textTitleThemed()
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
                                    .clickable { noteExpanded = !noteExpanded }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (noteExpanded) "收起" else if (noteText.isNotEmpty()) "编辑" else "写笔记",
                                    fontSize = 13.sp,
                                    color = noteColors.noteAccent,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        if (noteExpanded) {
                            Spacer(modifier = Modifier.height(10.dp))
                            androidx.compose.material3.OutlinedTextField(
                                value = noteText,
                                onValueChange = {
                                    noteText = it
                                    NotePrefs.saveNote(context, idiom.id, it)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 4,
                                maxLines = 10,
                                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = noteColors.noteBorder,
                                    unfocusedBorderColor = DictionaryTokens.textCaptionThemed().copy(alpha = 0.2f),
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                                ),
                                shape = RoundedCornerShape(DictionaryTokens.radiusButton)
                            )
                        } else if (noteText.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = noteText,
                                fontSize = DictionaryTokens.Type.bodySize,
                                color = DictionaryTokens.textBodyThemed(),
                                lineHeight = DictionaryTokens.Type.bodyLineHeight
                            )
                        }
                    }
                }
            }

            // ====== 连续阅读导航（上/下一篇 + 随机词条） ======
            item(key = "reading_nav_${idiom.id}") {
                ReadingNavigation(
                    prevIdiom = prevIdiom,
                    nextIdiom = nextIdiom,
                    randomIdiom = randomIdiom,
                    onPrev = { prevIdiom?.let { onNavigateToIdiom(it.id) } },
                    onNext = { nextIdiom?.let { onNavigateToIdiom(it.id) } },
                    onRandom = { randomIdiom?.let { onNavigateToIdiom(it.id) } }
                )
            }

            // 底部文案
            item(key = "footer_${idiom.id}") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "词语只是框架，理解才是自由。",
                    fontSize = DictionaryTokens.Type.captionSize,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(DictionaryTokens.bottomSafePadding))
            }
        }
    }
}

// ============================================
// 连续阅读导航组件（上/下一篇 + 随机词条）
// ============================================

@Composable
private fun ReadingNavigation(
    prevIdiom: IdiomCritique?,
    nextIdiom: IdiomCritique?,
    randomIdiom: IdiomCritique?,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onRandom: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 分隔标题（上下留白充分）
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            )
            Text(
                text = "继续阅读",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                modifier = Modifier.padding(horizontal = 14.dp)
            )
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // 上一篇 / 下一篇
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 上一篇
            if (prevIdiom != null) {
                ReadingNavCard(
                    modifier = Modifier.weight(1f),
                    label = "上一篇",
                    idiomName = prevIdiom.idiom,
                    direction = "prev",
                    onClick = onPrev
                )
            }
            // 下一篇
            if (nextIdiom != null) {
                ReadingNavCard(
                    modifier = Modifier.weight(1f),
                    label = "下一篇",
                    idiomName = nextIdiom.idiom,
                    direction = "next",
                    onClick = onNext
                )
            }
        }

        // 随机认知词条（打破顺序浏览，强制碎片化思辨）
        if (randomIdiom != null) {
            Spacer(modifier = Modifier.height(12.dp))
            val randomColors = LocalVeraColors.current
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableWithScale { onRandom() },
                colors = CardDefaults.cardColors(
                    containerColor = randomColors.randomCardBg
                ),
                shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(DictionaryTokens.cardPaddingSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RandomIcon(
                        tint = randomColors.randomCardAccent,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "随机认知词条",
                            fontSize = 12.sp,
                            color = randomColors.randomCardAccent,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = randomIdiom.idiom,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DictionaryTokens.textTitleThemed()
                        )
                    }
                    Text(
                        text = "🎲",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ReadingNavCard(
    modifier: Modifier = Modifier,
    label: String,
    idiomName: String,
    direction: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickableWithScale { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(DictionaryTokens.radiusCard),
        elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
    ) {
        Column(
            modifier = Modifier.padding(DictionaryTokens.cardPaddingSmall),
            horizontalAlignment = if (direction == "prev") Alignment.Start else Alignment.End
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (direction == "prev") {
                    Text("←", fontSize = 14.sp, color = DictionaryTokens.textCaptionThemed())
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = DictionaryTokens.textCaptionThemed(),
                    fontWeight = FontWeight.Medium
                )
                if (direction == "next") {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("→", fontSize = 14.sp, color = DictionaryTokens.textCaptionThemed())
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = idiomName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DictionaryTokens.textTitleThemed(),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun RandomIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2f * density
        // 两个交叉的箭头（洗牌/随机图标）
        drawLine(tint, Offset(w * 0.2f, h * 0.3f), Offset(w * 0.8f, h * 0.3f), stroke, cap = StrokeCap.Round)
        drawLine(tint, Offset(w * 0.65f, h * 0.15f), Offset(w * 0.8f, h * 0.3f), stroke, cap = StrokeCap.Round)
        drawLine(tint, Offset(w * 0.8f, h * 0.3f), Offset(w * 0.65f, h * 0.45f), stroke, cap = StrokeCap.Round)

        drawLine(tint, Offset(w * 0.8f, h * 0.7f), Offset(w * 0.2f, h * 0.7f), stroke, cap = StrokeCap.Round)
        drawLine(tint, Offset(w * 0.35f, h * 0.55f), Offset(w * 0.2f, h * 0.7f), stroke, cap = StrokeCap.Round)
        drawLine(tint, Offset(w * 0.2f, h * 0.7f), Offset(w * 0.35f, h * 0.85f), stroke, cap = StrokeCap.Round)
    }
}

// ============================================
// 详情页子组件
// ============================================

@Composable
private fun DetailHeader(
    idiom: IdiomCritique,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBiasTagClick: (String) -> Unit = {}
) {
    val weakLabel = when (idiom.toxicityLevel) {
        ToxicityLevel.POISONOUS -> "易被滥用为规训工具"
        ToxicityLevel.HARMFUL -> "易放大焦虑与比较"
        ToxicityLevel.DISTORTED -> "大众认知易片面"
        ToxicityLevel.HERITAGE -> "需辩证取舍"
        ToxicityLevel.TOWN_WISDOM -> "小镇底层认知法则"
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = idiom.idiom,
                fontSize = DictionaryTokens.Type.titleSizeLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 34.sp,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            // 收藏星标按钮
            val veraColorsStar = LocalVeraColors.current
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
                    .clickable { onToggleFavorite() }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                StarIcon(
                    filled = isFavorite,
                    tint = if (isFavorite) veraColorsStar.iconStarFilled else veraColorsStar.iconStarEmpty,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${idiom.category.displayName} · $weakLabel",
                fontSize = DictionaryTokens.Type.captionSize,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                fontWeight = FontWeight.Medium
            )
        }
        // 认知偏差标签（极简无底色标签）
        if (idiom.cognitiveBiasTags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                idiom.cognitiveBiasTags.forEach { biasTag ->
                    Text(
                        text = "#$biasTag",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.clickable { onBiasTagClick(biasTag) }
                    )
                }
            }
        }
    }
}

@Composable
private fun StarIcon(filled: Boolean, tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 1.8f * density

        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.5f, h * 0.08f)
            lineTo(w * 0.61f, h * 0.36f)
            lineTo(w * 0.92f, h * 0.38f)
            lineTo(w * 0.68f, h * 0.58f)
            lineTo(w * 0.76f, h * 0.9f)
            lineTo(w * 0.5f, h * 0.74f)
            lineTo(w * 0.24f, h * 0.9f)
            lineTo(w * 0.32f, h * 0.58f)
            lineTo(w * 0.08f, h * 0.38f)
            lineTo(w * 0.39f, h * 0.36f)
            close()
        }
        if (filled) {
            drawPath(path, color = tint)
        } else {
            drawPath(path, color = tint, style = Stroke(stroke))
        }
    }
}

@Composable
private fun ContentCard(
    backgroundColor: Color,
    content: @Composable () -> Unit
) {
    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    val surfaceColor = MaterialTheme.colorScheme.surface
    // 深色模式下：所有浅色硬编码卡片背景统一回退surface，保证可读性
    val cardBg = if (isDark || backgroundColor == Color.White || backgroundColor == DictionaryTokens.cardBg) surfaceColor else backgroundColor
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        shape = RoundedCornerShape(DictionaryTokens.radiusCard),
        elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            content()
        }
    }
}

/**
 * 统一条目排版 —— 图标左对齐 + 正文统一缩进换行
 * 现实案例与辩证解读共用同一套对齐参数。
 */
@Composable
private fun IconParagraph(
    icon: @Composable (Modifier) -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        icon(Modifier.padding(top = 6.dp).size(14.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            fontSize = DictionaryTokens.Type.bodySize,
            color = DictionaryTokens.textBodyThemed(),
            lineHeight = DictionaryTokens.Type.bodyLineHeight,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ExampleItem(text: String) {
    val isAbuse = isAbuseScenario(text)
    IconParagraph(
        icon = if (isAbuse) {
            { mod -> HollowWarningIcon(mod) }
        } else {
            { mod -> HollowCheckIcon(mod) }
        },
        text = text
    )
}

private fun isAbuseScenario(text: String): Boolean {
    val abuseKeywords = listOf(
        "现代", "当代", "职场", "组织", "企业", "公司", "滥用",
        "极端", "异化", "利用", "剥削", "996", "加班", "管理层",
        "领导", "HR", "社会达尔文", "资本", "消费主义", "内卷",
        "被套用", "被曲解", "被包装", "话术", "道德绑架", "规训"
    )
    return abuseKeywords.any { text.contains(it) }
}


// ---- 全局展开/收起图标 ----

/**
 * 黑色单线展开/收起图标（用于右上角全局切换按钮）
 */
@Composable
private fun ExpandCollapseIcon(expanded: Boolean, tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 2f * density
        val w = size.width
        val h = size.height
        if (expanded) {
            // 收起图标：双箭头向内收敛
            drawLine(tint, Offset(w * 0.3f, h * 0.3f), Offset(w * 0.5f, h * 0.5f), stroke, cap = StrokeCap.Round)
            drawLine(tint, Offset(w * 0.7f, h * 0.3f), Offset(w * 0.5f, h * 0.5f), stroke, cap = StrokeCap.Round)
            drawLine(tint, Offset(w * 0.3f, h * 0.7f), Offset(w * 0.5f, h * 0.5f), stroke, cap = StrokeCap.Round)
            drawLine(tint, Offset(w * 0.7f, h * 0.7f), Offset(w * 0.5f, h * 0.5f), stroke, cap = StrokeCap.Round)
        } else {
            // 展开图标：双箭头向外扩散
            drawLine(tint, Offset(w * 0.5f, h * 0.5f), Offset(w * 0.3f, h * 0.3f), stroke, cap = StrokeCap.Round)
            drawLine(tint, Offset(w * 0.5f, h * 0.5f), Offset(w * 0.7f, h * 0.3f), stroke, cap = StrokeCap.Round)
            drawLine(tint, Offset(w * 0.5f, h * 0.5f), Offset(w * 0.3f, h * 0.7f), stroke, cap = StrokeCap.Round)
            drawLine(tint, Offset(w * 0.5f, h * 0.5f), Offset(w * 0.7f, h * 0.7f), stroke, cap = StrokeCap.Round)
        }
    }
}

// ---- 空心单线标识图标 ----

/**
 * 空心单线对勾 —— 原生合理场景标识
 * 使用单条 Path + Stroke 渲染，Round Join 保持转角自然，
 * 避免双段 drawLine Round Cap 在交汇处叠加重合产生实心感。
 */
@Composable
private fun HollowCheckIcon(modifier: Modifier = Modifier) {
    val iconColor = LocalVeraColors.current.iconCheck
    Canvas(modifier = modifier) {
        val stroke = 1.8f * density
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.15f, h * 0.55f)
            lineTo(w * 0.38f, h * 0.75f)
            lineTo(w * 0.85f, h * 0.28f)
        }
        drawPath(
            path = path,
            color = iconColor,
            style = Stroke(width = stroke, cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
        )
    }
}

/**
 * 空心单线警示三角 —— 现代滥用场景标识
 */
@Composable
private fun HollowWarningIcon(modifier: Modifier = Modifier) {
    val iconColor = LocalVeraColors.current.iconWarning
    Canvas(modifier = modifier) {
        val stroke = 1.8f * density
        val w = size.width
        val h = size.height
        // 空心三角
        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.1f)
            lineTo(w * 0.1f, h * 0.82f)
            lineTo(w * 0.9f, h * 0.82f)
            close()
        }
        drawPath(path, iconColor, style = Stroke(stroke))
        // 感叹号竖线
        drawLine(iconColor, Offset(w * 0.5f, h * 0.38f), Offset(w * 0.5f, h * 0.65f), stroke * 0.85f, cap = StrokeCap.Round)
        // 感叹号圆点
        drawCircle(iconColor, stroke * 0.55f, Offset(w * 0.5f, h * 0.77f))
    }
}

// ---- 辩证视角空心单线项目符号（全部归入 iconLine 语义色体系） ----

/** 空心圆形 ○ —— 唯物史观视角（宏观客观） */
@Composable
private fun HollowCircleBullet(modifier: Modifier = Modifier) {
    val iconColor = LocalVeraColors.current.iconLine
    Canvas(modifier = modifier) {
        val stroke = 1.8f * density
        val r = (size.width - stroke) / 2
        drawCircle(
            color = iconColor,
            radius = r,
            center = Offset(size.width / 2, size.height / 2),
            style = Stroke(stroke)
        )
    }
}

/** 空心方块 □ —— 现代社会结构批判（规则 / 组织） */
@Composable
private fun HollowSquareBullet(modifier: Modifier = Modifier) {
    val iconColor = LocalVeraColors.current.iconLine
    Canvas(modifier = modifier) {
        val stroke = 1.8f * density
        val inset = stroke / 2
        drawRect(
            color = iconColor,
            topLeft = Offset(inset, inset),
            size = Size(size.width - stroke, size.height - stroke),
            style = Stroke(stroke)
        )
    }
}

/** 空心菱形 ◇ —— 认知心理学视角（内心思维 / 多角度思辨） */
@Composable
private fun HollowDiamondBullet(modifier: Modifier = Modifier) {
    val iconColor = LocalVeraColors.current.iconLine
    Canvas(modifier = modifier) {
        val stroke = 1.8f * density
        val cx = size.width / 2
        val cy = size.height / 2
        val inset = stroke / 2
        val path = Path().apply {
            moveTo(cx, inset)                 // top
            lineTo(size.width - inset, cy)    // right
            lineTo(cx, size.height - inset)   // bottom
            lineTo(inset, cy)                 // left
            close()
        }
        drawPath(path, iconColor, style = Stroke(stroke, join = androidx.compose.ui.graphics.StrokeJoin.Miter))
    }
}

// ---- 辩证解读渲染（空心单线图标 + 分段排版） ----

/**
 * 将 townPerspective 文本按 \n\n 拆分为独立段落，
 * 根据段落前缀自动匹配对应的空心单线项目符号图标。
 * 排版参数与 ExampleItem 完全一致（IconParagraph 复用）。
 */
@Composable
private fun TownPerspectiveContent(text: String) {
    val paragraphs = text
        .split("\n\n")
        .map { it.trim() }
        .filter { it.isNotEmpty() }

    if (paragraphs.isEmpty()) {
        Text(
            text = text,
            fontSize = DictionaryTokens.Type.bodySize,
            color = DictionaryTokens.textBodyThemed(),
            lineHeight = DictionaryTokens.Type.bodyLineHeight
        )
        return
    }

    // 包裹独立 Column 隔离外层 spacedBy 干扰，保持段落间距一致
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        paragraphs.forEachIndexed { index, paragraph ->
            val icon: @Composable (Modifier) -> Unit = when {
                paragraph.startsWith("唯物史观") -> { mod -> HollowCircleBullet(mod) }
                paragraph.startsWith("现代社会结构批判") -> { mod -> HollowSquareBullet(mod) }
                paragraph.startsWith("认知心理学") -> { mod -> HollowDiamondBullet(mod) }
                else -> { mod -> HollowCircleBullet(mod) }
            }
            IconParagraph(icon = icon, text = paragraph)
        }
    }
}

/**
 * 警示提示区块 —— 纯黑白极简：空心圆点符号 + 无底色文字，嵌入小镇辩证解读模块末尾
 */
@Composable
private fun SpotlightsBlock(spotlights: List<com.example.townapp.data.spotlight.Spotlight>) {
        // 细分割线 + 警示内容
    val spotlightDivider = LocalVeraColors.current.spotlightDivider
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // 顶部分割线
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(spotlightDivider)
        )
        Spacer(modifier = Modifier.height(2.dp))
        // 标题行：空心圆点 + "警示提示"
        val spotlightDot = LocalVeraColors.current.spotlightDot
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 空心圆点（替代原绿色五角星）
            Canvas(modifier = Modifier.size(6.dp)) {
                drawCircle(
                    color = spotlightDot,
                    radius = size.width / 2f,
                    style = Stroke(1.5f * density)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "警示提示",
                fontSize = DictionaryTokens.Type.captionSize,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
            )
        }
        // 警示条目（纯文字，左侧缩进对齐圆点）
        spotlights.forEach { spotlight ->
            Row(verticalAlignment = Alignment.Top) {
                Spacer(modifier = Modifier.width(14.dp)) // 对齐圆点
                Text(
                    text = spotlight.content,
                    fontSize = DictionaryTokens.Type.captionSize,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                    lineHeight = DictionaryTokens.Type.captionLineHeight,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * 可折叠模块组件：卡片底部独立按钮控制展开/收起
 * 智能预览模式——折叠态显示前3行完整深色正文 + 渐隐遮罩
 * 标题栏无状态文字，底部唯一操作入口：【展开全部/收起】标准化圆角按钮
 */
@Composable
private fun CollapsibleSection(
    title: String,
    moduleKey: String,
    idiomId: String,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 读取全局展开模式（0=智能预览 1=全部展开 2=全部收起）
    val globalMode by UserPreferencesManager.getExpandMode(context)
        .collectAsState(initial = 0)

    // 读取本卡片手动缓存（null = 无缓存，跟随全局）
    val cardCache by UserPreferencesManager.getCardExpandState(context, idiomId, moduleKey)
        .collectAsState(initial = null)

    // 实际展开状态：手动缓存优先级 > 全局模式
    val expanded = remember(globalMode, cardCache) {
        cardCache ?: when (globalMode) {
            1 -> true
            else -> false
        }
    }

    val veraColors = LocalVeraColors.current

    Column(modifier = Modifier.fillMaxWidth()) {
        // 标题栏（无状态文字，只展示标题）
        Text(
            text = title,
            fontSize = DictionaryTokens.Type.headingSize,
            fontWeight = DictionaryTokens.Type.headingWeight,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )

        // 内容区域
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            if (expanded) {
                // 完全展开
                Column(modifier = Modifier.fillMaxWidth()) {
                    content()
                }
            } else {
                // 折叠预览：固定高度（约3行正文）+ 裁剪溢出内容 + 底部渐隐遮罩
                // 14sp正文 + 22sp行高 → 3行 ≈ 66dp，取72dp留有余地
                // 关键：将渐隐遮罩移入 clipped Box 内部，确保 align(BottomCenter) 在固定 72dp 内生效
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .clip(RoundedCornerShape(0.dp))
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        content()
                    }
                    // 渐隐遮罩（底部融合卡片底色，位于 clipped Box 内部）
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.surface
                                    )
                                )
                            )
                    )
                }
            }
        }

        // 底部唯一操作入口：统一圆角描边按钮（折叠/展开同一位置同一风格）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = {
                    scope.launch {
                        UserPreferencesManager.setCardExpandState(context, idiomId, moduleKey, !expanded)
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = veraColors.buttonText
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp, veraColors.buttonBorder
                ),
                shape = RoundedCornerShape(DictionaryTokens.radiusButton),
                contentPadding = PaddingValues(horizontal = 28.dp, vertical = 10.dp)
            ) {
                Text(
                    if (expanded) "收起" else "展开全部",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}