package com.example.townapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
 * 侧边抽屉菜单项数据
 */
data class DrawerItem(
    val id: String,
    val label: String,
    val iconType: String,
    val description: String = "",
    val badge: String = ""
)

/**
 * 第一组：词典配套功能
 */
val dictionaryItems = listOf(
    DrawerItem("favorites", "收藏词条", "star", "我标记的精彩词条"),
    DrawerItem("notes", "思辨笔记", "note", "记录你对婚育、人生规划的真实纠结，没有正确、觉醒、落后之分，你的感受永远第一位"),
    DrawerItem("import_export", "词条导入导出", "transfer", "备份与共享词条库")
)

/**
 * 第二组：系统辅助功能（搁置中）
 */
val systemItems = listOf(
    DrawerItem("feedback", "意见反馈", "message", "功能开发中"),
    DrawerItem("about", "关于 VERA 认知字典", "info", "了解 Vera 的设计理念与使用方法", badge = "v3.0"),
    DrawerItem("settings", "软件设置", "gear", "功能开发中")
)

/**
 * 侧边抽屉内容面板
 * 浅米色底色 + 顶部纯文字品牌区 + 两个白色卡片分组
 */
@Composable
fun AppDrawerContent(
    currentItem: String,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = DictionaryTokens.pagePadding)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // ============================================
        // 顶部品牌区：纯文字居中，无深色背景
        // ============================================
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "VERA",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cognition Dictionary",
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ============================================
        // 第一组卡片：词典配套功能
        // ============================================
        DrawerCard(title = "词典配套功能") {
            dictionaryItems.forEach { item ->
                DrawerRow(
                    item = item,
                    selected = currentItem == item.id,
                    onClick = { onItemClick(item.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ============================================
        // 第二组卡片：系统辅助功能
        // ============================================
        DrawerCard(title = "系统辅助功能") {
            systemItems.forEach { item ->
                DrawerRow(
                    item = item,
                    selected = currentItem == item.id,
                    onClick = { onItemClick(item.id) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 底部小字
        Text(
            text = "不评判 · 不催促 · 你这样就很好",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            letterSpacing = 0.5.sp
        )
    }
}

/**
 * 卡片分组容器：16dp圆角 + 白色背景 + 低阴影
 * 对齐首页词条卡片样式
 */
@Composable
private fun DrawerCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(DictionaryTokens.radiusCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 卡片标题
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            // 内容
            content()
        }
    }
}

/**
 * 菜单项单行：左侧图标 + 文字信息
 */
@Composable
private fun DrawerRow(
    item: DrawerItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
            .background(
                if (selected) DictionaryTokens.primary.copy(alpha = 0.08f)
                else Color.Transparent
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧图标
        DrawerIcon(
            type = item.iconType,
            tint = if (selected) DictionaryTokens.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // 文字信息
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.label,
                    fontSize = 15.sp,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                    color = if (selected) DictionaryTokens.primary else MaterialTheme.colorScheme.onSurface
                )
                if (item.badge.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.badge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = DictionaryTokens.primary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(DictionaryTokens.primary.copy(alpha = 0.12f))
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    )
                }
            }
            if (item.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

/**
 * 抽屉图标 — 细线线性风格
 */
@Composable
private fun DrawerIcon(type: String, tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 1.5f * density

        when (type) {
            "star" -> {
                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(w * 0.5f, h * 0.1f)
                    lineTo(w * 0.62f, h * 0.38f) ; lineTo(w * 0.9f, h * 0.4f)
                    lineTo(w * 0.7f, h * 0.6f)  ; lineTo(w * 0.78f, h * 0.88f)
                    lineTo(w * 0.5f, h * 0.72f) ; lineTo(w * 0.22f, h * 0.88f)
                    lineTo(w * 0.3f, h * 0.6f)  ; lineTo(w * 0.1f, h * 0.4f)
                    lineTo(w * 0.38f, h * 0.38f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(stroke))
            }
            "note" -> {
                // 细线笔记本图标
                drawRect(tint, topLeft = Offset(w * 0.15f, h * 0.1f), size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.8f), style = Stroke(stroke))
                drawLine(tint, Offset(w * 0.3f, h * 0.35f), Offset(w * 0.7f, h * 0.35f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.3f, h * 0.5f), Offset(w * 0.7f, h * 0.5f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.3f, h * 0.65f), Offset(w * 0.6f, h * 0.65f), stroke, cap = StrokeCap.Round)
            }
            "transfer" -> {
                // 细线双向箭头
                drawLine(tint, Offset(w * 0.2f, h * 0.3f), Offset(w * 0.8f, h * 0.3f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.65f, h * 0.15f), Offset(w * 0.8f, h * 0.3f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.8f, h * 0.3f), Offset(w * 0.65f, h * 0.45f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.8f, h * 0.7f), Offset(w * 0.2f, h * 0.7f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.35f, h * 0.55f), Offset(w * 0.2f, h * 0.7f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.2f, h * 0.7f), Offset(w * 0.35f, h * 0.85f), stroke, cap = StrokeCap.Round)
            }
            "message" -> {
                // 细线消息气泡
                drawRect(tint, topLeft = Offset(w * 0.1f, h * 0.15f), size = androidx.compose.ui.geometry.Size(w * 0.8f, h * 0.55f), style = Stroke(stroke))
                drawLine(tint, Offset(w * 0.1f, h * 0.3f), Offset(w * 0.9f, h * 0.3f), stroke * 0.7f, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.1f, h * 0.45f), Offset(w * 0.9f, h * 0.45f), stroke * 0.7f, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.25f, h * 0.7f), Offset(w * 0.15f, h * 0.9f), stroke, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.25f, h * 0.7f), Offset(w * 0.5f, h * 0.7f), stroke, cap = StrokeCap.Round)
            }
            "info" -> {
                // 细线信息图标
                drawCircle(tint, radius = w * 0.4f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(stroke))
                drawLine(tint, Offset(w * 0.5f, h * 0.28f), Offset(w * 0.5f, h * 0.3f), stroke * 1.5f, cap = StrokeCap.Round)
                drawLine(tint, Offset(w * 0.5f, h * 0.42f), Offset(w * 0.5f, h * 0.72f), stroke, cap = StrokeCap.Round)
            }
            "gear" -> {
                // 细线齿轮图标
                drawCircle(tint, radius = w * 0.3f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(stroke))
                drawCircle(tint, radius = w * 0.1f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(stroke))
                val teeth = listOf(
                    Offset(w * 0.5f, h * 0.08f) to Offset(w * 0.5f, h * 0.22f),
                    Offset(w * 0.5f, h * 0.92f) to Offset(w * 0.5f, h * 0.78f),
                    Offset(w * 0.08f, h * 0.5f) to Offset(w * 0.22f, h * 0.5f),
                    Offset(w * 0.92f, h * 0.5f) to Offset(w * 0.78f, h * 0.5f)
                )
                teeth.forEach { (start, end) ->
                    drawLine(tint, start, end, stroke, cap = StrokeCap.Round)
                }
            }
        }
    }
}
