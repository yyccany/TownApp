package com.example.townapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 右下角圆形折叠功能菜单
 *
 * 严格遵循小镇项目铁律：
 * 1. 纯 UI 组件，无任何文件 IO、无任何数据库调用
 * 2. 默认收起为一个小圆点，不占据视觉焦点
 * 3. 扇形展开菜单项，点击空白处可自动收起
 * 4. 所有菜单项回调由调用方传入
 */
data class MenuItem(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    /** 是否为切换状态项（如开关） */
    val isToggle: Boolean = false,
    /** 当前切换状态（仅 isToggle=true 时生效） */
    val toggleState: Boolean = false,
    /** 切换状态为 true 时的图标 */
    val activeIcon: ImageVector? = null
)

@Composable
fun FloatingExpandMenu(
    menuItems: List<MenuItem>,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 45f else 0f,
        animationSpec = tween(200),
        label = "menu-rotation"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        // 菜单项：扇形展开
        menuItems.forEachIndexed { index, item ->
            AnimatedVisibility(
                visible = isExpanded,
                enter = scaleIn(animationSpec = tween(200)) + fadeIn(),
                exit = scaleOut(animationSpec = tween(200)) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = ((index + 1) * 56).dp)
            ) {
                MenuItemChip(item = item)
            }
        }

        // 主按钮：小圆点 ↔ 关闭图标
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { isExpanded = !isExpanded },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isExpanded) Icons.Filled.Close else Icons.Filled.Add,
                contentDescription = if (isExpanded) "关闭菜单" else "打开菜单",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .rotate(rotation)
            )
        }
    }
}

/** 单个菜单项（带文字标签的小圆按钮） */
@Composable
private fun MenuItemChip(item: MenuItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(
                if (item.isToggle && item.toggleState) {
                    Color.Black.copy(alpha = 0.6f)
                } else {
                    Color.Black.copy(alpha = 0.4f)
                }
            )
            .clickable { item.onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = if (item.isToggle && item.toggleState && item.activeIcon != null) {
                item.activeIcon
            } else {
                item.icon
            },
            contentDescription = item.label,
            tint = if (item.isToggle && item.toggleState) {
                Color(0xFFFFD700)
            } else {
                Color.White
            },
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = item.label,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}
