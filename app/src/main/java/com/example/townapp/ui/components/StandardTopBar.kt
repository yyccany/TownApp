package com.example.townapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.theme.DictionaryTokens

/**
 * 标准化顶部导航栏（全页面统一布局）
 *
 * 布局规范：
 * - 左：汉堡菜单图标（首页）或返回箭头（详情/子页面）
 * - 中：页面标题（首页=万物薪俸小镇；分类页=分类名；详情页=词条名）
 * - 右：两个图标 → 视图切换（卡片/纯列表）、搜索放大镜
 *
 * @param title 页面标题
 * @param onMenuClick 左上角按钮回调（默认汉堡菜单）
 * @param menuIcon 菜单图标类型："menu"=汉堡, "back"=返回箭头
 * @param showViewToggle 是否显示视图切换按钮
 * @param viewToggleState 视图切换状态（true=卡片视图，false=列表视图）
 * @param onViewToggle 视图切换回调
 * @param showSearch 是否显示搜索按钮
 * @param onSearchClick 搜索按钮回调
 * @param actions 额外的右侧操作按钮
 */
@Composable
fun StandardTopBar(
    title: String,
    onMenuClick: () -> Unit,
    menuIcon: String = "menu",
    showViewToggle: Boolean = false,
    viewToggleState: Boolean = true,
    onViewToggle: (() -> Unit)? = null,
    showSearch: Boolean = false,
    onSearchClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = DictionaryTokens.pagePadding, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左：菜单/返回按钮
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
                .clickable { onMenuClick() },
            contentAlignment = Alignment.Center
        ) {
            NavIcon(
                type = menuIcon,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(22.dp)
            )
        }

        // 中：标题（占据剩余空间，居中）
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = DictionaryTokens.Type.titleSize,
                fontWeight = DictionaryTokens.Type.titleWeight,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // 右：视图切换 + 搜索 + 额外操作
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (showViewToggle && onViewToggle != null) {
                ViewToggleIcon(
                    isCardView = viewToggleState,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
                        .clickable { onViewToggle() }
                        .padding(9.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            if (showSearch && onSearchClick != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
                        .clickable { onSearchClick() },
                    contentAlignment = Alignment.Center
                ) {
                    SearchIcon(
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            actions()
        }
    }
}

// ============================================
// 导航图标（汉堡菜单 / 返回箭头）
// ============================================

@Composable
private fun NavIcon(type: String, tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2f * density

        when (type) {
            "menu" -> {
                // 汉堡菜单：三条横线
                drawLine(tint, Offset(w * 0.1f, h * 0.3f), Offset(w * 0.9f, h * 0.3f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.1f, h * 0.5f), Offset(w * 0.9f, h * 0.5f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.1f, h * 0.7f), Offset(w * 0.9f, h * 0.7f), stroke, cap = StrokeCap.Round)
            }
            "back" -> {
                // 返回箭头：左尖括号
                drawLine(tint, Offset(w * 0.6f, h * 0.15f), Offset(w * 0.25f, h * 0.5f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.25f, h * 0.5f), Offset(w * 0.6f, h * 0.85f), stroke, cap = StrokeCap.Round)
            }
        }
    }
}

// ============================================
// 搜索放大镜图标
// ============================================

@Composable
fun SearchIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2f * density
        drawCircle(tint, radius = w * 0.35f, center = Offset(w * 0.42f, h * 0.42f), style = Stroke(stroke))
        drawLine(tint, Offset(w * 0.65f, h * 0.65f), Offset(w * 0.9f, h * 0.9f), stroke, cap = StrokeCap.Round)
    }
}

// ============================================
// 视图切换图标（卡片 / 列表）
// ============================================

@Composable
private fun ViewToggleIcon(isCardView: Boolean, tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 2f * density

        if (isCardView) {
            // 卡片视图图标：两个矩形
            drawRect(tint, topLeft = Offset(w * 0.1f, h * 0.15f), size = androidx.compose.ui.geometry.Size(w * 0.35f, h * 0.3f), style = Stroke(stroke))
            drawRect(tint, topLeft = Offset(w * 0.55f, h * 0.15f), size = androidx.compose.ui.geometry.Size(w * 0.35f, h * 0.3f), style = Stroke(stroke))
            drawRect(tint, topLeft = Offset(w * 0.1f, h * 0.55f), size = androidx.compose.ui.geometry.Size(w * 0.35f, h * 0.3f), style = Stroke(stroke))
            drawRect(tint, topLeft = Offset(w * 0.55f, h * 0.55f), size = androidx.compose.ui.geometry.Size(w * 0.35f, h * 0.3f), style = Stroke(stroke))
        } else {
            // 列表视图图标：三条横线带圆点
            drawCircle(tint, radius = w * 0.05f, center = Offset(w * 0.15f, h * 0.2f))
            drawLine(tint, Offset(w * 0.3f, h * 0.2f), Offset(w * 0.9f, h * 0.2f), stroke, cap = StrokeCap.Round)
            drawCircle(tint, radius = w * 0.05f, center = Offset(w * 0.15f, h * 0.5f))
            drawLine(tint, Offset(w * 0.3f, h * 0.5f), Offset(w * 0.9f, h * 0.5f), stroke, cap = StrokeCap.Round)
            drawCircle(tint, radius = w * 0.05f, center = Offset(w * 0.15f, h * 0.8f))
            drawLine(tint, Offset(w * 0.3f, h * 0.8f), Offset(w * 0.9f, h * 0.8f), stroke, cap = StrokeCap.Round)
        }
    }
}
