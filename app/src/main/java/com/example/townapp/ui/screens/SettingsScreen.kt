package com.example.townapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.core.state.UserPreferencesManager
import com.example.townapp.core.theme.VeraThemeMode
import com.example.townapp.ui.components.StandardTopBar
import com.example.townapp.ui.theme.DictionaryTokens
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onMenuClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 展开模式状态：0=智能预览 1=全部展开 2=全部收起
    var expandMode by remember { mutableIntStateOf(0) }
    // 主题模式
    var themeMode by remember { mutableStateOf(VeraThemeMode.DefaultMonochrome) }

    // 读取持久化配置
    LaunchedEffect(Unit) {
        UserPreferencesManager.getExpandMode(context).collect { mode ->
            expandMode = mode
        }
    }
    LaunchedEffect(Unit) {
        UserPreferencesManager.getThemeMode(context).collect { mode ->
            themeMode = mode
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StandardTopBar(
            title = "软件设置",
            onMenuClick = onBack,
            menuIcon = "back"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = DictionaryTokens.pagePadding)
        ) {
            Spacer(modifier = Modifier.height(DictionaryTokens.sectionSpacing))

            // 字典模式说明
            SectionLabel(text = "字典模式")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
            ) {
                Row(
                    modifier = Modifier.padding(DictionaryTokens.cardPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(DictionaryTokens.primaryLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "字典",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "当前运行模式",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "纯认知字典查询模式，所有内容从本地加载",
                            fontSize = DictionaryTokens.Type.captionSize,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(DictionaryTokens.sectionSpacing))

            // 阅读偏好
            SectionLabel(text = "阅读偏好")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
            ) {
                Column(modifier = Modifier.padding(DictionaryTokens.cardPadding)) {
                    Text(
                        text = "词条文本默认展开模式",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "控制打开词条时各模块的默认显示状态",
                        fontSize = DictionaryTokens.Type.captionSize,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // 选项列表
                    ExpandModeOption(
                        title = "智能预览（显示前3行）",
                        description = "每个模块只显示前3行，点击展开按钮查看完整内容",
                        selected = expandMode == 0,
                        onClick = {
                            scope.launch {
                                UserPreferencesManager.setExpandMode(context, 0)
                                expandMode = 0
                            }
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                    ExpandModeOption(
                        title = "全部自动展开",
                        description = "打开词条时所有模块默认展开显示完整内容",
                        selected = expandMode == 1,
                        onClick = {
                            scope.launch {
                                UserPreferencesManager.setExpandMode(context, 1)
                                expandMode = 1
                            }
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                    ExpandModeOption(
                        title = "全部默认收起",
                        description = "打开词条时所有模块默认收起，需手动展开查看",
                        selected = expandMode == 2,
                        onClick = {
                            scope.launch {
                                UserPreferencesManager.setExpandMode(context, 2)
                                expandMode = 2
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(DictionaryTokens.sectionSpacingLarge))

            // 外观主题
            SectionLabel(text = "外观主题")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
            ) {
                Column(modifier = Modifier.padding(DictionaryTokens.cardPadding)) {
                    Text(
                        text = "主题色彩方案",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "切换详情页图标、按钮、标签的配色风格，布局和交互不变",
                        fontSize = DictionaryTokens.Type.captionSize,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // 默认黑白极简（默认选中）
                    ThemeModeOption(
                        title = "默认黑白极简",
                        description = "纯黑单线图标，极简克制，日常阅读舒适",
                        selected = themeMode == VeraThemeMode.DefaultMonochrome,
                        onClick = {
                            scope.launch {
                                UserPreferencesManager.setThemeMode(context, VeraThemeMode.DefaultMonochrome)
                                themeMode = VeraThemeMode.DefaultMonochrome
                            }
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    )
                    ThemeModeOption(
                        title = "马卡龙童趣彩色",
                        description = "柔和低饱和马卡龙色，图标多彩，兼顾儿童使用",
                        selected = themeMode == VeraThemeMode.MacaronChild,
                        onClick = {
                            scope.launch {
                                UserPreferencesManager.setThemeMode(context, VeraThemeMode.MacaronChild)
                                themeMode = VeraThemeMode.MacaronChild
                            }
                        }
                    )
                }
            }

            // 关于
            SectionLabel(text = "关于")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
            ) {
                Column(modifier = Modifier.padding(DictionaryTokens.cardPadding)) {
                    Text(
                        text = "关于VERA认知字典",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "v3.0",
                        fontSize = DictionaryTokens.Type.captionSize,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "不评判、不催促、拒绝标准化规训，认可每个人独一无二的人生节奏。\n\n面对婚育、人生选择等重大议题，我们不提供标准答案，只帮你看清束缚你的思维陷阱。\n\n当你被世俗教条困住时，在这里看清枷锁，回归属于自己的生活与选择。",
                        fontSize = DictionaryTokens.Type.bodySize,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(DictionaryTokens.bottomSafePadding))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = DictionaryTokens.Type.headingSize,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}

@Composable
private fun ExpandModeOption(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = DictionaryTokens.primaryLight
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun ThemeModeOption(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = DictionaryTokens.primaryLight
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected)
                    MaterialTheme.colorScheme.onSurface
                else
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                lineHeight = 16.sp
            )
        }
    }
}
