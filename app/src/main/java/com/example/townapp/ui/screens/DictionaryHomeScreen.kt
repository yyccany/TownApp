package com.example.townapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.focus.onFocusChanged
import com.example.townapp.data.idiom.IdiomCategory
import com.example.townapp.data.idiom.IdiomCritique
import com.example.townapp.data.idiom.IdiomCritiqueLibrary
import com.example.townapp.data.idiom.ToxicityLevel
import com.example.townapp.data.idiom.EntryType
import com.example.townapp.ui.components.StandardTopBar
import com.example.townapp.ui.components.SearchIcon
import com.example.townapp.ui.theme.DictionaryTokens

// ============================================
// 全局交互动效扩展
// ============================================

fun Modifier.clickableWithScale(onClick: () -> Unit): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(stiffness = 400f, dampingRatio = 0.75f),
        label = "cardScale"
    )
    this
        .scale(scale)
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
}

// ============================================
// 首页 Tab 数据
// ============================================

data class HomeTab(
    val id: String,
    val label: String,
    val category: IdiomCategory? = null,
    val isDaily: Boolean = false,
    val isFeatured: Boolean = false,
    val isSubjectivity: Boolean = false,
    val iconType: String = ""
)

val homeTabs = listOf(
    HomeTab("daily", "今日认知", isDaily = true, iconType = "sun"),
    HomeTab("character", "人格品德", category = IdiomCategory.CHARACTER, iconType = "person"),
    HomeTab("method", "处事方法", category = IdiomCategory.METHOD, iconType = "tool"),
    HomeTab("interpersonal", "人际关系", category = IdiomCategory.INTERPERSONAL, iconType = "people"),
    HomeTab("society", "社会观念", category = IdiomCategory.SOCIETY, iconType = "globe"),
    HomeTab("relationship", "亲密关系", category = IdiomCategory.RELATIONSHIP, iconType = "heart"),
    HomeTab("cognition", "认知思维", category = IdiomCategory.COGNITION, iconType = "brain"),
    HomeTab("workplace", "职场围城", category = IdiomCategory.WORKPLACE, iconType = "briefcase"),
    HomeTab("consumption", "日常消费", category = IdiomCategory.CONSUMPTION, iconType = "cart"),
    HomeTab("town_system", "小镇内在", category = IdiomCategory.TOWN_SYSTEM, iconType = "home"),
    HomeTab("subjectivity", "主体性工具", isSubjectivity = true, iconType = "compass"),
    HomeTab("featured", "值得先读", isFeatured = true, iconType = "star")
)

// ============================================
// 首页（横向Tab + 全屏词条列表）
// ============================================

@Composable
fun DictionaryHomeScreen(
    onNavigateToIdiom: (String) -> Unit,
    onMenuClick: () -> Unit,
    onSearchOpen: () -> Unit,
    readIdiomIds: List<String> = emptyList()
) {
    var selectedTabId by remember { mutableStateOf("daily") }
    var isCardView by remember { mutableStateOf(true) }
    val listState = rememberLazyListState()

    // 弧形裁切深度：根据滚动位置动态计算
    val arcDepth by remember {
        derivedStateOf {
            val offset = listState.firstVisibleItemScrollOffset.toFloat()
            val itemIndex = listState.firstVisibleItemIndex
            if (itemIndex == 0) (offset / 8f).coerceIn(0f, 10f) else 10f
        }
    }

    // 当前Tab对应的词条列表
    val currentTab = homeTabs.find { it.id == selectedTabId } ?: homeTabs.first()
    val dailyIdiom = remember { getDailyIdiom() }

    val idiomsToShow = remember(selectedTabId) {
        when {
            currentTab.isDaily -> IdiomCritiqueLibrary.getAllIdioms()
            currentTab.isFeatured -> IdiomCritiqueLibrary.getAllIdioms().shuffled().take(10)
            currentTab.isSubjectivity -> IdiomCritiqueLibrary.getIdiomsByEntryType(EntryType.DILEMMA)
            currentTab.category != null -> IdiomCritiqueLibrary.getIdiomsByCategory(currentTab.category)
            else -> IdiomCritiqueLibrary.getAllIdioms()
        }
    }

    val bgColor = MaterialTheme.colorScheme.background
    Box(modifier = Modifier.fillMaxSize().background(bgColor)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ====== Header区域（顶部导航 + 横向Tab + 弧形裁切） ======
            HeaderWithArc(
                arcDepth = arcDepth,
                title = "Vera",
                onMenuClick = onMenuClick,
                isCardView = isCardView,
                onViewToggle = { isCardView = !isCardView },
                onSearchClick = onSearchOpen,
                selectedTabId = selectedTabId,
                onTabSelect = { selectedTabId = it },
                bgColor = bgColor
            )

            // ====== 词条列表主体（全屏展示） ======
            if (idiomsToShow.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = DictionaryTokens.pagePadding,
                        vertical = DictionaryTokens.sectionSpacing
                    ),
                    verticalArrangement = Arrangement.spacedBy(DictionaryTokens.sectionSpacing)
                ) {
                    // 今日认知Tab：顶部展示今日词条大卡片
                    if (currentTab.isDaily && dailyIdiom.id != "empty") {
                        item(key = "daily_card") {
                            DailyInsightCard(
                                idiom = dailyIdiom,
                                onClick = { onNavigateToIdiom(dailyIdiom.id) }
                            )
                        }
                    }

                    // 分类标题
                    item(key = "section_header") {
                        TabSectionHeader(
                            tab = currentTab,
                            count = idiomsToShow.size
                        )
                    }

                    // 词条列表
                    items(idiomsToShow) { idiom ->
                        if (isCardView) {
                            IdiomCardItem(
                                idiom = idiom,
                                onClick = { onNavigateToIdiom(idiom.id) },
                                isRead = readIdiomIds.contains(idiom.id)
                            )
                        } else {
                            IdiomCompactItem(
                                idiom = idiom,
                                onClick = { onNavigateToIdiom(idiom.id) },
                                isRead = readIdiomIds.contains(idiom.id)
                            )
                        }
                    }

                    // 底部安全留白
                    item(key = "bottom_spacer") {
                        Spacer(modifier = Modifier.height(DictionaryTokens.bottomSafePadding))
                    }
                }
            }
        }
    }
}

// ============================================
// Header区域（标准顶栏 + 横向Tab + 弧形裁切凹陷）
// ============================================

@Composable
private fun HeaderWithArc(
    arcDepth: Float,
    title: String,
    onMenuClick: () -> Unit,
    isCardView: Boolean,
    onViewToggle: () -> Unit,
    onSearchClick: () -> Unit,
    selectedTabId: String,
    onTabSelect: (String) -> Unit,
    bgColor: Color = DictionaryTokens.pageBg
) {
    val onBgColor = MaterialTheme.colorScheme.onBackground
    // 用drawBehind绘制带弧形底部的背景
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                if (arcDepth > 0.5f) {
                    val path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(0f, size.height)
                        quadraticTo(
                            size.width / 2f, size.height - arcDepth,
                            size.width, size.height
                        )
                        lineTo(size.width, 0f)
                        close()
                    }
                    drawPath(path, color = bgColor)
                    // 弧形底部阴影
                    drawLine(
                        color = onBgColor.copy(alpha = (arcDepth / 40f).coerceAtMost(0.06f)),
                        start = Offset(0f, size.height - arcDepth * 0.5f),
                        end = Offset(size.width, size.height - arcDepth * 0.5f),
                        strokeWidth = 1f * density
                    )
                } else {
                    drawRect(bgColor)
                }
            }
    ) {
        Column {
            // 标准顶部导航栏（透明背景，由父容器绘制）
            StandardTopBar(
                title = title,
                onMenuClick = onMenuClick,
                menuIcon = "menu",
                showViewToggle = true,
                viewToggleState = isCardView,
                onViewToggle = onViewToggle,
                showSearch = true,
                onSearchClick = onSearchClick
            )

            // 横向可滑动分类Tab栏
            ScrollableTabRow(
                selectedTabId = selectedTabId,
                onTabSelect = onTabSelect
            )
        }
    }
}

// ============================================
// 横向可滑动分类Tab栏
// ============================================

@Composable
private fun ScrollableTabRow(
    selectedTabId: String,
    onTabSelect: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        contentPadding = PaddingValues(horizontal = DictionaryTokens.pagePadding),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(homeTabs) { tab ->
            val selected = tab.id == selectedTabId
            TabChip(
                label = tab.label,
                tabId = tab.id,
                iconType = tab.iconType,
                selected = selected,
                onClick = { onTabSelect(tab.id) }
            )
        }
    }
}

@Composable
private fun TabChip(
    label: String,
    tabId: String,
    iconType: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val barColor = DictionaryTokens.tabBarColor(tabId)
    val iconColor = if (selected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
    val textColor = if (selected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
    val fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .clickableWithScale { onClick() }
                .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 分类极简线性图标
            TabCategoryIcon(
                iconType = iconType,
                tint = iconColor,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = fontWeight,
                color = textColor
            )
        }
        // 下划线高亮（选中时2dp分类色条）
        Spacer(
            modifier = Modifier
                .width(24.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(if (selected) barColor else Color.Transparent)
        )
    }
}

// ============================================
// Tab区域标题
// ============================================

@Composable
private fun TabSectionHeader(tab: HomeTab, count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = tab.label,
            fontSize = DictionaryTokens.Type.headingSize,
            fontWeight = DictionaryTokens.Type.headingWeight,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "共${count}个词条",
            fontSize = DictionaryTokens.Type.captionSize,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}

// ============================================
// 词条卡片视图（完整信息）
// ============================================

@Composable
fun IdiomCardItem(
    idiom: IdiomCritique,
    onClick: () -> Unit,
    isRead: Boolean = false
) {
    val tagColor = DictionaryTokens.toxicityTagColor(idiom.toxicityLevel)
    val darkColor = DictionaryTokens.toxicityDarkColor(idiom.toxicityLevel)

    val titleColor = if (isRead) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface
    val bodyColor = if (isRead) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithScale { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard),
        shape = RoundedCornerShape(DictionaryTokens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp)
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
                    color = titleColor,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Spacer(modifier = Modifier.width(8.dp))
                // 右上角标签（色系与毒性等级对应）_标签不受已读影响
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(DictionaryTokens.radiusTag))
                        .background(tagColor.copy(alpha = if (isRead) 0.5f else 1f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = idiom.toxicityLevel.displayName,
                        fontSize = DictionaryTokens.Type.tagSize,
                        color = darkColor.copy(alpha = if (isRead) 0.5f else 1f),
                        fontWeight = DictionaryTokens.Type.tagWeight
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = idiom.traditionalMeaning.take(44) +
                        if (idiom.traditionalMeaning.length > 44) "..." else "",
                fontSize = DictionaryTokens.Type.captionSize,
                color = bodyColor,
                lineHeight = DictionaryTokens.Type.captionLineHeight,
                maxLines = 2
            )
        }
    }
}

// ============================================
// 词条紧凑列表视图（与卡片视图共用统一外壳，仅隐藏简介文字）
// ============================================

@Composable
fun IdiomCompactItem(
    idiom: IdiomCritique,
    onClick: () -> Unit,
    isRead: Boolean = false
) {
    val tagColor = DictionaryTokens.toxicityTagColor(idiom.toxicityLevel)
    val darkColor = DictionaryTokens.toxicityDarkColor(idiom.toxicityLevel)
    val titleColor = if (isRead) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithScale { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard),
        shape = RoundedCornerShape(DictionaryTokens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
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
                    color = titleColor,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(DictionaryTokens.radiusTag))
                        .background(tagColor.copy(alpha = if (isRead) 0.5f else 1f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = idiom.toxicityLevel.displayName,
                        fontSize = DictionaryTokens.Type.tagSize,
                        color = darkColor.copy(alpha = if (isRead) 0.5f else 1f),
                        fontWeight = DictionaryTokens.Type.tagWeight
                    )
                }
            }
        }
    }
}

// ============================================
// 今日认知大卡片
// ============================================

@Composable
fun DailyInsightCard(idiom: IdiomCritique, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithScale { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard),
        shape = RoundedCornerShape(DictionaryTokens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            DictionaryTokens.primaryBg,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(horizontal = DictionaryTokens.cardPadding, vertical = 18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(DictionaryTokens.radiusTag))
                            .background(DictionaryTokens.primary.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "今日认知",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = idiom.idiom,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = "→",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

// ============================================
// 空状态
// ============================================

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "暂无词条",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "请检查词条数据文件",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }
    }
}

// ============================================
// 搜索覆盖面板（全页面通用，由MainActivity渲染）
// ============================================

@Composable
fun SearchOverlay(
    initialQuery: String = "",
    onClose: () -> Unit,
    onIdiomClick: (String) -> Unit
) {
    var searchQuery by remember(initialQuery) { mutableStateOf(initialQuery) }
    var searchHistory by remember { mutableStateOf(listOf<String>()) }

    val searchResults = if (searchQuery.isNotEmpty()) {
        IdiomCritiqueLibrary.getAllIdioms().filter {
            it.idiom.contains(searchQuery) ||
            it.traditionalMeaning.contains(searchQuery) ||
            it.townPerspective.contains(searchQuery) ||
            it.keyMessage.contains(searchQuery) ||
            it.cognitiveBiasTags.any { tag -> tag.contains(searchQuery) }
        }
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        // 搜索栏
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DictionaryTokens.pagePadding, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(DictionaryTokens.radiusCard))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = DictionaryTokens.cardPaddingSmall, vertical = 12.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SearchIcon(
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "搜索俗语、认知误区…",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                            }
                            innerTextField()
                        }
                    )
                    if (searchQuery.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "✕",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.clickable { searchQuery = "" }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "取消",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.clickable { onClose() }
            )
        }

        // 搜索结果 / 热门词条
        if (searchQuery.isNotEmpty()) {
            if (searchResults.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "未找到相关词条",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "试试其他关键词",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
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
                    verticalArrangement = Arrangement.spacedBy(DictionaryTokens.sectionSpacing)
                ) {
                    item {
                        Text(
                            text = "找到 ${searchResults.size} 个结果",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                    items(searchResults) { idiom ->
                        SearchResultItem(
                            idiom = idiom,
                            onClick = {
                                if (searchQuery.isNotBlank() && !searchHistory.contains(searchQuery)) {
                                    searchHistory = listOf(searchQuery) + searchHistory.take(4)
                                }
                                onIdiomClick(idiom.id)
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(DictionaryTokens.bottomSafePadding)) }
                }
            }
        } else {
            // 热门词条推荐
            val hotIdioms = remember { IdiomCritiqueLibrary.getAllIdioms().shuffled().take(12) }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = DictionaryTokens.pagePadding,
                    vertical = DictionaryTokens.sectionSpacing
                ),
                verticalArrangement = Arrangement.spacedBy(DictionaryTokens.sectionSpacing)
            ) {
                item {
                    Text(
                        text = "热门词条",
                        fontSize = DictionaryTokens.Type.headingSize,
                        fontWeight = DictionaryTokens.Type.headingWeight,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                items(hotIdioms) { idiom ->
                    SearchResultItem(
                        idiom = idiom,
                        onClick = { onIdiomClick(idiom.id) }
                    )
                }
                item { Spacer(modifier = Modifier.height(DictionaryTokens.bottomSafePadding)) }
            }
        }
    }
}

@Composable
fun SearchResultItem(idiom: IdiomCritique, onClick: () -> Unit) {
    val tagColor = DictionaryTokens.toxicityTagColor(idiom.toxicityLevel)
    val darkColor = DictionaryTokens.toxicityDarkColor(idiom.toxicityLevel)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithScale { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard),
        shape = RoundedCornerShape(DictionaryTokens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 14.dp)
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

// ============================================
// Tab 分类极简线性图标（1.6f stroke 统一标准）
// ============================================

@Composable
private fun TabCategoryIcon(iconType: String, tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2f * density

        when (iconType) {
            "sun" -> {
                // 太阳/今日：圆形 + 射线
                drawCircle(tint, radius = w * 0.2f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(stroke))
                val rays = 4
                for (i in 0 until rays) {
                    val angle = (i * 90f + 45f) * (Math.PI / 180).toFloat()
                    val sx = w * 0.5f + kotlin.math.cos(angle) * w * 0.32f
                    val sy = h * 0.5f + kotlin.math.sin(angle) * h * 0.32f
                    val ex = w * 0.5f + kotlin.math.cos(angle) * w * 0.48f
                    val ey = h * 0.5f + kotlin.math.sin(angle) * h * 0.48f
                    drawLine(tint, Offset(sx, sy), Offset(ex, ey), stroke, cap = StrokeCap.Round)
                }
            }
            "person" -> {
                // 人格：人形 + 盾牌轮廓
                drawCircle(tint, radius = w * 0.15f, center = Offset(w * 0.5f, h * 0.3f), style = Stroke(stroke))
                drawLine(tint, Offset(w * 0.3f, h * 0.5f), Offset(w * 0.7f, h * 0.5f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.5f, h * 0.45f), Offset(w * 0.5f, h * 0.85f), stroke, cap = StrokeCap.Round)
            }
            "tool" -> {
                // 处事：扳手简化
                drawCircle(tint, radius = w * 0.22f, center = Offset(w * 0.45f, h * 0.4f), style = Stroke(stroke))
                drawLine(tint, Offset(w * 0.6f, h * 0.55f), Offset(w * 0.85f, h * 0.8f), stroke, cap = StrokeCap.Round)
            }
            "people" -> {
                // 人际关系：两个人形
                drawCircle(tint, radius = w * 0.13f, center = Offset(w * 0.3f, h * 0.25f), style = Stroke(stroke))
                drawLine(tint, Offset(w * 0.3f, h * 0.4f), Offset(w * 0.3f, h * 0.85f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.15f, h * 0.55f), Offset(w * 0.45f, h * 0.55f), stroke, cap = StrokeCap.Round)

                drawCircle(tint, radius = w * 0.13f, center = Offset(w * 0.7f, h * 0.25f), style = Stroke(stroke))
                drawLine(tint, Offset(w * 0.7f, h * 0.4f), Offset(w * 0.7f, h * 0.85f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.55f, h * 0.55f), Offset(w * 0.85f, h * 0.55f), stroke, cap = StrokeCap.Round)
            }
            "globe" -> {
                // 社会观念：地球简化
                drawCircle(tint, radius = w * 0.35f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(stroke))
                drawLine(tint, Offset(w * 0.5f, h * 0.15f), Offset(w * 0.5f, h * 0.85f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.15f, h * 0.5f), Offset(w * 0.85f, h * 0.5f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.3f, h * 0.25f), Offset(w * 0.7f, h * 0.25f), stroke * 0.7f, cap = StrokeCap.Round)
            }
            "heart" -> {
                // 亲密关系：心形
                val path = Path().apply {
                    moveTo(w * 0.5f, h * 0.85f)
                    cubicTo(w * 0.1f, h * 0.55f, w * 0.15f, h * 0.15f, w * 0.5f, h * 0.35f)
                    cubicTo(w * 0.85f, h * 0.15f, w * 0.9f, h * 0.55f, w * 0.5f, h * 0.85f)
                }
                drawPath(path, color = tint, style = Stroke(stroke))
            }
            "brain" -> {
                // 认知思维：灯泡
                drawCircle(tint, radius = w * 0.22f, center = Offset(w * 0.5f, h * 0.35f), style = Stroke(stroke))
                drawLine(tint, Offset(w * 0.4f, h * 0.57f), Offset(w * 0.6f, h * 0.57f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.45f, h * 0.57f), Offset(w * 0.45f, h * 0.78f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.55f, h * 0.57f), Offset(w * 0.55f, h * 0.78f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.38f, h * 0.78f), Offset(w * 0.62f, h * 0.78f), stroke, cap = StrokeCap.Round)
                // 灯泡内光线
                drawLine(tint, Offset(w * 0.5f, h * 0.18f), Offset(w * 0.5f, h * 0.28f), stroke * 0.8f, cap = StrokeCap.Round)
            }
            "home" -> {
                // 小镇内在：房屋
                drawLine(tint, Offset(w * 0.15f, h * 0.55f), Offset(w * 0.15f, h * 0.88f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.85f, h * 0.55f), Offset(w * 0.85f, h * 0.88f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.1f, h * 0.88f), Offset(w * 0.9f, h * 0.88f), stroke, cap = StrokeCap.Round)
                // 屋顶
                drawLine(tint, Offset(w * 0.1f, h * 0.55f), Offset(w * 0.5f, h * 0.15f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.5f, h * 0.15f), Offset(w * 0.9f, h * 0.55f), stroke, cap = StrokeCap.Round)
            }
            "star" -> {
                // 值得先读：星形
                val path = Path().apply {
                    moveTo(w * 0.5f, h * 0.1f)
                    lineTo(w * 0.62f, h * 0.35f); lineTo(w * 0.9f, h * 0.4f)
                    lineTo(w * 0.7f, h * 0.6f); lineTo(w * 0.78f, h * 0.85f)
                    lineTo(w * 0.5f, h * 0.72f); lineTo(w * 0.22f, h * 0.85f)
                    lineTo(w * 0.3f, h * 0.6f); lineTo(w * 0.1f, h * 0.4f)
                    lineTo(w * 0.38f, h * 0.35f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(stroke))
            }
            "briefcase" -> {
                // 职场围城：公文包
                drawRect(tint, topLeft = Offset(w * 0.2f, h * 0.35f), size = androidx.compose.ui.geometry.Size(w * 0.6f, h * 0.5f), style = Stroke(stroke))
                drawLine(tint, Offset(w * 0.35f, h * 0.35f), Offset(w * 0.35f, h * 0.25f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.65f, h * 0.35f), Offset(w * 0.65f, h * 0.25f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.35f, h * 0.25f), Offset(w * 0.65f, h * 0.25f), stroke, cap = StrokeCap.Round)
            }
            "cart" -> {
                // 日常消费：购物车
                drawLine(tint, Offset(w * 0.15f, h * 0.3f), Offset(w * 0.4f, h * 0.3f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.4f, h * 0.3f), Offset(w * 0.55f, h * 0.7f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.55f, h * 0.7f), Offset(w * 0.8f, h * 0.7f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.25f, h * 0.45f), Offset(w * 0.6f, h * 0.45f), stroke, cap = StrokeCap.Round)
                drawCircle(tint, radius = w * 0.08f, center = Offset(w * 0.6f, h * 0.82f), style = Stroke(stroke))
                drawCircle(tint, radius = w * 0.08f, center = Offset(w * 0.75f, h * 0.82f), style = Stroke(stroke))
            }
            "compass" -> {
                // 主体性工具：指南针
                drawCircle(tint, radius = w * 0.38f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(stroke))
                // 指针（菱形）
                val path = Path().apply {
                    moveTo(w * 0.5f, h * 0.2f)
                    lineTo(w * 0.6f, h * 0.5f)
                    lineTo(w * 0.5f, h * 0.8f)
                    lineTo(w * 0.4f, h * 0.5f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(stroke))
            }
            else -> {
                // 默认：实心圆点
                drawCircle(tint, radius = w * 0.2f, center = Offset(w * 0.5f, h * 0.5f))
            }
        }
    }
}

// ============================================
// 辅助函数
// ============================================

internal fun getDailyIdiom(): IdiomCritique {
    val allIdioms = IdiomCritiqueLibrary.getAllIdioms()
    if (allIdioms.isEmpty()) {
        return IdiomCritique(
            id = "empty",
            idiom = "暂无词条",
            traditionalMeaning = "词条库暂时为空，请检查数据文件",
            distortedTruth = "",
            townPerspective = "",
            category = IdiomCategory.SOCIETY,
            toxicityLevel = ToxicityLevel.DISTORTED,
            keyMessage = "请检查 assets/idioms/idioms.json 是否存在且格式正确"
        )
    }
    val dayOfYear = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_YEAR)
    return allIdioms[dayOfYear % allIdioms.size]
}
