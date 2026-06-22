package com.example.townapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 右上角可折叠迷你状态面板
 *
 * 严格遵循小镇项目铁律：
 * 1. 纯 UI 组件，无任何文件 IO、无任何数据库调用
 * 2. 所有状态数据由调用方传入
 * 3. 默认折叠状态，不占据画面视觉焦点
 * 4. 半透明 + 圆角 + 极小字号，贴合电影感留白
 *
 * 落实自由原则：
 * - 提供「一键隐藏所有悬浮 UI」按钮，最大化自由选择权
 *
 * 使用规范：
 * ```
 * MiniStatusPanel(
 *     seasonId = 1,
 *     year = 2025,
 *     playerAge = 22,
 *     health = 100,
 *     energy = 80,
 *     onHideUI = { TownNarrativeState.toggleHideAllUI() },
 *     hideAllUIActive = false
 * )
 * ```
 */
@Composable
fun MiniStatusPanel(
    seasonId: Int,
    year: Int,
    playerAge: Int,
    health: Int,
    energy: Int,
    onHideUI: () -> Unit = {},
    hideAllUIActive: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable { isExpanded = !isExpanded }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 默认折叠：只显示一个微小的状态指示
            AnimatedVisibility(
                visible = !isExpanded,
                enter = fadeIn(animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(200))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = getSeasonShort(seasonId),
                        color = Color.White,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = "展开状态",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(200))
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StatusItem(label = "季节", value = getSeasonShort(seasonId))
                        Spacer(modifier = Modifier.width(12.dp))
                        StatusItem(label = "年份", value = year.toString())
                        Spacer(modifier = Modifier.width(12.dp))
                        StatusItem(label = "年龄", value = playerAge.toString())
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = "收起状态",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        StatusItem(label = "健康", value = "$health")
                        Spacer(modifier = Modifier.width(12.dp))
                        StatusItem(label = "精力", value = "$energy")
                        Spacer(modifier = Modifier.width(12.dp))
                        // 隐藏 UI 按钮（落实自由原则）
                        IconButton(
                            onClick = onHideUI,
                            modifier = Modifier.size(20.dp)
                        ) {
                            Text(
                                text = if (hideAllUIActive) "[+]" else "[x]",
                                color = if (hideAllUIActive) Color(0xFFFFD700) else Color.White.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/** 状态项（极简标签 + 数值） */
@Composable
private fun StatusItem(label: String, value: String) {
    Row {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            color = Color.White,
            fontSize = 10.sp
        )
    }
}

/** 季节简称 */
private fun getSeasonShort(seasonId: Int): String = when (seasonId) {
    1 -> "春"
    2 -> "夏"
    3 -> "秋"
    4 -> "冬"
    else -> "?"
}
