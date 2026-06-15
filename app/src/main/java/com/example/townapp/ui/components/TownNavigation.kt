package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.WindowInsets

@Composable
fun TownBottomNavigation(
    currentTab: String,
    onTabChange: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFFFFF9EC),
        contentColor = MaterialTheme.colorScheme.onSurface,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        // 纯内存模式：只保留小镇和设置两个主入口
        // 其他功能通过小镇界面内的导航访问
        val tabs = listOf(
            "town" to "🏠",
            "settings" to "⚙️"
        )
        
        tabs.forEach { (id, icon) ->
            NavigationBarItem(
                icon = {
                    Text(
                        text = icon,
                        fontSize = if (currentTab == id) 24.sp else 20.sp
                    )
                },
                label = {
                    Text(
                        text = getTabLabel(id),
                        fontSize = 10.sp,
                        fontWeight = if (currentTab == id) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selected = currentTab == id,
                onClick = { onTabChange(id) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }
}

private fun getTabLabel(id: String): String {
    return when (id) {
        "town" -> "小镇"
        "settings" -> "设置"
        else -> id
    }
}
