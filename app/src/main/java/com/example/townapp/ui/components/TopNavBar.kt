package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets

/**
 * ########## 顶部导航栏（双模式） ##########
 *
 * ## 模式一：简单中心标题（推荐用于页面标题）
 * ```
 * TopNavBar(title = { Text("小镇主界面") })
 * ```
 *
 * ## 模式二：Material3 标准导航栏（带返回按钮 + 操作项）
 * ```
 * TopNavBar(
 *     title = "衣柜街",
 *     showBack = true,
 *     onBack = { navBack() },
 *     actions = { TextButton(...) }
 * )
 * ```
 *
 * ## 防闪退规则
 * 1. 标题变量必须使用 `remember { mutableStateOf() }` 托管，
 *    避免屏幕旋转/配置变更时状态丢失导致空指针闪退。
 *    ✅ `val pageTitle = remember { mutableStateOf("标题") }`
 *    ❌ `var pageTitle = "标题"`  // 重组时重置，可能引发崩溃
 * 2. 模式一使用 Compose lambda `@Composable () -> Unit`，
 *    类型安全，编译期保证非空，无需额外空值校验。
 * 3. 模式二 title 为 String 类型，传空字符串不会崩溃，
 *    但建议始终传入有意义的标题文案。
 */
object TopNavBarConstants {
    const val DEFAULT_TITLE = "万物薪俸小镇"
}

// ============================================================
// 模式一：Lambda 标题（灵活自定义，编译期空安全）
// ============================================================

/**
 * 顶部导航栏（Lambda 标题模式）
 *
 * 适用场景：需要动态标题、富文本标题、响应式标题的页面。
 * title 为 @Composable lambda，编译期保证非空，无需担心空指针。
 *
 * @param title 标题 Compose 区块
 * @param onBackClick 可选返回按钮回调
 * @param actions 右侧操作按钮
 */
@Composable
fun TopNavBar(
    title: @Composable () -> Unit,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 可选返回按钮
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "返回"
                )
            }
        }

        // 标题（占据剩余空间）
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            title()
        }

        // 右侧操作按钮占位（与左侧返回按钮对称，保持标题居中）
        if (onBackClick != null) {
            Spacer(modifier = Modifier.size(48.dp))
        }

        // 右侧自定义操作
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions()
        }
    }
}

// ============================================================
// 模式二：String 标题 + Material3 TopAppBar（向后兼容）
// ============================================================

/**
 * 顶部导航栏（String 标题模式，Material3 TopAppBar）
 *
 * 适用场景：简单固定标题的页面。
 * 保留此重载以兼容现有全部调用点。
 *
 * @param modifier 修饰符（Compose 规范：必须为第一个可选参数）
 * @param title 标题文案
 * @param showBack 是否显示返回按钮
 * @param onBack 返回回调
 * @param actions 右侧操作按钮
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    modifier: Modifier = Modifier,
    title: String,
    showBack: Boolean = false,
    onBack: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title.ifBlank { TopNavBarConstants.DEFAULT_TITLE },
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFFF9EC),
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        windowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier
    )
}