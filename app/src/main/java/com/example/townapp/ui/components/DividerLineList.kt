package com.example.townapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.screens.clickableWithScale
import com.example.townapp.ui.theme.DictionaryTokens

/**
 * 分割线列表组件（方案A — Caritas 信息流模式）
 *
 * 适用场景：资讯文章、故事、动态信息流
 * 特征：0.5dp细分割线分隔条目，无卡片无圆角无阴影，极致纵向空间利用率
 *
 * @param items 数据列表
 * @param onItemClick 条目点击回调
 * @param onFavoriteClick 收藏按钮点击回调（每条独立，互不干扰）
 * @param readIds 已读条目ID集合
 * @param tabs 顶部横向Tab列表（可选）
 * @param selectedTabId 当前选中Tab
 * @param onTabSelect Tab切换回调
 */
@Composable
fun DividerLineList(
    items: List<DividerLineItem>,
    onItemClick: (String) -> Unit,
    onFavoriteClick: ((String) -> Unit)? = null,
    readIds: Set<String> = emptySet(),
    tabs: List<DividerLineTab> = emptyList(),
    selectedTabId: String = "",
    onTabSelect: ((String) -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // 横向 Tab 栏（下划线高亮模式）
        if (tabs.isNotEmpty() && onTabSelect != null) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    horizontal = DictionaryTokens.pagePadding
                ),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(tabs) { tab ->
                    val selected = tab.id == selectedTabId
                    DividerLineTabChip(
                        label = tab.label,
                        accentColor = tab.color,
                        selected = selected,
                        onClick = { onTabSelect(tab.id) }
                    )
                }
            }

            // Tab与内容的微弱分割
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(DictionaryTokens.textCaptionThemed().copy(alpha = 0.12f))
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = DictionaryTokens.bottomSafePadding)
        ) {
            itemsIndexed(items) { index, item ->
                val isRead = readIds.contains(item.id)
                DividerLineRow(
                    item = item,
                    isRead = isRead,
                    onClick = { onItemClick(item.id) },
                    onFavoriteClick = if (onFavoriteClick != null) {
                        { onFavoriteClick(item.id) }
                    } else null
                )

                // 0.5dp 细分割线（最后一条不画）
                if (index < items.size - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = DictionaryTokens.pagePadding)
                            .height(0.5.dp)
                            .background(DictionaryTokens.textCaptionThemed().copy(alpha = 0.1f))
                    )
                }
            }
        }
    }
}

// ============================================
// 分割线Tab（下划线高亮，无背景无圆角）
// ============================================

@Composable
private fun DividerLineTabChip(
    label: String,
    accentColor: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (selected) DictionaryTokens.textTitleThemed() else DictionaryTokens.textCaptionThemed()
    val fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = fontWeight,
            color = textColor,
            modifier = Modifier
                .clickable { onClick() }
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
        // 下划线高亮
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(if (selected) accentColor else Color.Transparent)
        )
    }
}

// ============================================
// 分割线行（左：标题+简介 / 右：收藏按钮）
// ============================================

@Composable
private fun DividerLineRow(
    item: DividerLineItem,
    isRead: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: (() -> Unit)? = null
) {
    val titleColor = if (isRead) DictionaryTokens.textTitleReadThemed() else DictionaryTokens.textTitleUnreadThemed()
    val bodyColor = if (isRead) DictionaryTokens.textBodyReadThemed() else DictionaryTokens.textBodyUnreadThemed()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithScale { onClick() }
            .padding(horizontal = DictionaryTokens.pagePadding, vertical = 14.dp),
        verticalAlignment = Alignment.Top
    ) {
        // 左：文字区域
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                fontSize = if (item.subtitle.isNotEmpty()) 18.sp else 20.sp,
                fontWeight = FontWeight.Medium,
                color = titleColor,
                lineHeight = 26.sp,
                maxLines = 2
            )
            if (item.subtitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.subtitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = bodyColor,
                    lineHeight = 18.sp,
                    maxLines = 2
                )
            }
        }

        // 右：独立收藏按钮（独立点击区域，互不干扰）
        if (onFavoriteClick != null) {
            Spacer(modifier = Modifier.width(12.dp))
            FavoriteButton(
                isFavorited = item.isFavorited,
                onClick = onFavoriteClick
            )
        }
    }
}

// ============================================
// 收藏按钮（独立点击区域）
// ============================================

@Composable
private fun FavoriteButton(
    isFavorited: Boolean,
    onClick: () -> Unit
) {
    val tint = if (isFavorited) Color(0xFFD4A76A) else DictionaryTokens.textCaptionThemed()
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        FavoriteIcon(tint = tint, filled = isFavorited, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun FavoriteIcon(tint: Color, filled: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2f * density

        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.88f)
            cubicTo(w * 0.1f, h * 0.55f, w * 0.15f, h * 0.12f, w * 0.5f, h * 0.32f)
            cubicTo(w * 0.85f, h * 0.12f, w * 0.9f, h * 0.55f, w * 0.5f, h * 0.88f)
        }
        if (filled) {
            drawPath(path, color = tint)
        } else {
            drawPath(path, color = tint, style = Stroke(stroke))
        }
    }
}

// ============================================
// 数据模型
// ============================================

data class DividerLineItem(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val isFavorited: Boolean = false
)

data class DividerLineTab(
    val id: String,
    val label: String,
    val color: Color = DictionaryTokens.textCaption
)
