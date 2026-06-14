package com.example.townapp.ui.screens

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentScreen(
    onNavigateBack: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    // 抽屉状态
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    
    // 一级文件夹列表
    var folderList by remember { mutableStateOf(emptyList<String>()) }
    // 文件夹展开状态
    val expandState = remember { mutableStateMapOf<String, Boolean>() }
    // 每个文件夹对应的子MD文件
    var fileMap by remember { mutableStateOf(mapOf<String, List<String>>()) }
    // 当前选中的完整文件路径
    var selectedPath by remember { mutableStateOf("") }
    // 当前文档名称（用于顶部栏显示）
    var currentDocName by remember { mutableStateOf("小镇文档") }
    // MD正文内容
    var mdContent by remember { mutableStateOf("") }
    // 加载状态
    var isLoading by remember { mutableStateOf(false) }

    // 初始化：读取一级文件夹 + 每个文件夹下的md文件（必须在IO线程）
    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            try {
                val rootFolders = context.assets.list("docs")?.filter { !it.contains(".") } ?: emptyList()
                folderList = rootFolders.sorted()

                val tempMap = mutableMapOf<String, List<String>>()
                rootFolders.forEach { folder ->
                    try {
                        val files = context.assets.list("docs/$folder")
                            ?.filter { it.endsWith(".md") }?.sorted() ?: emptyList()
                        tempMap[folder] = files
                    } catch (e: Exception) {
                        Log.e("DocumentScreen", "读取文件夹失败: docs/$folder", e)
                        tempMap[folder] = emptyList()
                    }
                }
                fileMap = tempMap
            } catch (e: Exception) {
                Log.e("DocumentScreen", "初始化目录失败", e)
            }
        }
    }

    // 选中文件后加载MD内容（必须在IO线程）
    LaunchedEffect(selectedPath) {
        if (selectedPath.isBlank()) return@LaunchedEffect
        
        isLoading = true
        launch(Dispatchers.IO) {
            try {
                // 输出完整路径日志，便于核对
                Log.d("DocumentScreen", "尝试读取文件: $selectedPath")
                
                val content = context.assets.open(selectedPath)
                    .bufferedReader(charset = Charsets.UTF_8)
                    .readText()
                
                // 状态更新在主线程执行（Compose快照系统会自动处理）
                mdContent = content
                isLoading = false
                Log.d("DocumentScreen", "文件读取成功，长度: ${content.length}")
            } catch (e: Exception) {
                Log.e("DocumentScreen", "文档加载失败: $selectedPath", e)
                mdContent = "文档加载失败\n路径: $selectedPath\n错误: ${e.message}"
                isLoading = false
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            // 左侧抽屉目录
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp)
            ) {
                // 抽屉标题
                Text(
                    text = "文档目录",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                
                HorizontalDivider()
                
                // 目录列表（使用独立的滚动状态）
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 8.dp),
                    state = rememberLazyListState()
                ) {
                    items(folderList) { folderName ->
                        // 一级分类
                        val isExpand = expandState[folderName] ?: false
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandState[folderName] = !isExpand }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isExpand) "▼" else "▶",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.width(20.dp)
                            )
                            // 剔除数字前缀，只显示中文
                            val showName = folderName.drop(3)
                            Text(
                                text = showName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // 展开后显示二级文档
                        if (isExpand) {
                            val childFiles = fileMap[folderName] ?: emptyList()
                            Column {
                                childFiles.forEach { fileName ->
                                    val fullPath = "docs/$folderName/$fileName"
                                    val isSelected = selectedPath == fullPath
                                    
                                    Surface(
                                        color = if (isSelected) Color(0xFFE3F2FD) else Color.Transparent,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                // 优先更新文件路径
                                                selectedPath = fullPath
                                                currentDocName = fileName.removeSuffix(".md")
                                                
                                                // 延迟关闭抽屉，避免状态冲突
                                                scope.launch {
                                                    delay(100)
                                                    drawerState.close()
                                                }
                                            }
                                            .padding(start = 36.dp, top = 4.dp, bottom = 4.dp, end = 16.dp)
                                    ) {
                                        Text(
                                            text = fileName.removeSuffix(".md"),
                                            fontSize = 14.sp,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ) {
        // 主内容区域
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentDocName) },
                    navigationIcon = {
                        Row {
                            // 汉堡菜单按钮（呼出抽屉）
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "目录")
                            }
                            // 返回按钮
                            if (onNavigateBack != null) {
                                IconButton(onClick = onNavigateBack) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                                }
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            // 主阅读区 - 使用独立的滚动状态
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    isLoading -> {
                        // 加载中状态
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "正在加载...",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    mdContent.isNotBlank() -> {
                        // 文档内容区域 - 使用独立的滚动状态
                        MarkdownContent(
                            content = mdContent
                        )
                    }
                    else -> {
                        // 空白状态提示
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "点击左上角菜单选择文档",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MarkdownContent(
    content: String
) {
    val blocks = remember(content) { parseMarkdown(content) }
    
    // 滚动状态
    val scrollState = rememberScrollState()
    // 自动滚动开关
    var autoScroll by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    // 自动滚动逻辑：极低速参数，适合录屏拍摄
    LaunchedEffect(autoScroll) {
        if (autoScroll) {
            scope.launch {
                while (scrollState.value < scrollState.maxValue) {
                    scrollState.scrollTo(scrollState.value + 3)  // 步长3像素
                    delay(180)  // 间隔180毫秒
                }
                autoScroll = false
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // 自动滚动控制按钮（演示功能，正式版本可注释）
        Button(
            onClick = { autoScroll = !autoScroll },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(if (autoScroll) "停止滚动" else "开启自动滚动")
        }
        
        // 渲染MD内容
        blocks.forEach { block ->
            when (block) {
                is MdBlock.Heading1 -> Text(
                    text = block.text,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
                is MdBlock.Heading2 -> Text(
                    text = block.text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
                is MdBlock.Heading3 -> Text(
                    text = block.text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
                is MdBlock.Heading4 -> Text(
                    text = block.text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                is MdBlock.Paragraph -> Text(
                    text = block.text,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                is MdBlock.ListItems -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        block.items.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                            ) {
                                Text("• ", fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
                                Text(
                                    text = item,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 24.sp,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
                is MdBlock.Quote -> Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = block.text,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is MdBlock.Code -> Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = block.text,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is MdBlock.Checkbox -> Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (block.checked) "☑" else "☐",
                        fontSize = 15.sp,
                        color = if (block.checked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = " ${block.text}",
                        fontSize = 15.sp,
                        color = if (block.checked) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                is MdBlock.Table -> {
                    // 表格区域单独处理横向滚动，不影响整体布局
                    TableContent(rows = block.rows)
                }
                is MdBlock.HorizontalRule -> HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
private fun TableContent(
    rows: List<List<String>>
) {
    if (rows.isEmpty()) return

    // 表格区域单独处理横向滚动，不影响整体布局
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            rows.forEachIndexed { rowIndex, row ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEachIndexed { _, cell ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .widthIn(min = 80.dp)
                                .background(
                                    if (rowIndex == 0) MaterialTheme.colorScheme.surfaceVariant
                                    else if (rowIndex % 2 == 1) MaterialTheme.colorScheme.surface
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                )
                                .border(1.dp, MaterialTheme.colorScheme.outline)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = cell,
                                fontSize = 14.sp,
                                color = if (rowIndex == 0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = if (rowIndex == 0) FontWeight.SemiBold else FontWeight.Normal,
                                lineHeight = 20.sp,
                                softWrap = true
                            )
                        }
                    }
                }
            }
        }
    }
}

// MD解析逻辑
private fun parseMarkdown(content: String): List<MdBlock> {
    val blocks = mutableListOf<MdBlock>()
    val lines = content.lines()
    var i = 0
    while (i < lines.size) {
        val line = lines[i]
        when {
            line.trim().let { it == "---" || it == "***" || it == "___" } -> {
                blocks.add(MdBlock.HorizontalRule)
            }
            line.startsWith("# ") -> blocks.add(MdBlock.Heading1(line.removePrefix("# ").trim()))
            line.startsWith("## ") -> blocks.add(MdBlock.Heading2(line.removePrefix("## ").trim()))
            line.startsWith("### ") -> blocks.add(MdBlock.Heading3(line.removePrefix("### ").trim()))
            line.startsWith("#### ") -> blocks.add(MdBlock.Heading4(line.removePrefix("#### ").trim()))
            line.trim().let { it.startsWith("- [ ]") || it.startsWith("- [x]") || it.startsWith("- [X]") } -> {
                val checked = line.trim().startsWith("- [x]") || line.trim().startsWith("- [X]")
                blocks.add(MdBlock.Checkbox(line.substringAfter("]").trim(), checked))
            }
            line.startsWith("- ") || line.startsWith("* ") -> {
                val items = mutableListOf<String>()
                while (i < lines.size && (lines[i].startsWith("- ") || lines[i].startsWith("* "))) {
                    items.add(lines[i].substring(2).trim())
                    i++
                }
                blocks.add(MdBlock.ListItems(items))
                continue
            }
            line.startsWith("> ") -> blocks.add(MdBlock.Quote(line.removePrefix("> ").trim()))
            line.startsWith("```") -> {
                val codeLines = mutableListOf<String>()
                i++
                while (i < lines.size && !lines[i].startsWith("```")) {
                    codeLines.add(lines[i])
                    i++
                }
                blocks.add(MdBlock.Code(codeLines.joinToString("\n")))
            }
            isMarkdownTableLine(line) -> {
                val tableLines = mutableListOf<String>()
                while (i < lines.size && isMarkdownTableLine(lines[i])) {
                    tableLines.add(lines[i])
                    i++
                }
                blocks.add(parseMarkdownTable(tableLines))
                continue
            }
            line.isBlank() -> { }
            else -> {
                val paraLines = mutableListOf(line)
                i++
                while (i < lines.size && lines[i].isNotBlank() &&
                    !lines[i].startsWith("#") &&
                    !lines[i].startsWith("-") &&
                    !lines[i].startsWith("*") &&
                    !lines[i].startsWith("> ") &&
                    !lines[i].startsWith("```") &&
                    !isMarkdownTableLine(lines[i]) &&
                    !lines[i].trim().let { it == "---" || it == "***" || it == "___" }
                ) {
                    paraLines.add(lines[i])
                    i++
                }
                blocks.add(MdBlock.Paragraph(paraLines.joinToString("\n")))
                continue
            }
        }
        i++
    }
    return blocks
}

private fun isMarkdownTableLine(line: String): Boolean {
    val trimmed = line.trim()
    if (!trimmed.startsWith("|") || !trimmed.endsWith("|")) return false
    if (trimmed.all { it == '|' || it == '-' || it == ':' || it == ' ' }) return false
    return true
}

private fun parseMarkdownTable(tableLines: List<String>): MdBlock.Table {
    // 过滤掉表格分隔行（如 |---|---|）
    val rows = tableLines
        .filter { line ->
            // 分隔行只包含 |、-、: 和空格，不应作为数据行
            !line.trim().all { it == '|' || it == '-' || it == ':' || it == ' ' }
        }
        .map { line ->
            line.trim().removeSurrounding("|").split("|").map { it.trim() }
        }
    return MdBlock.Table(rows)
}

private sealed class MdBlock {
    data class Heading1(val text: String) : MdBlock()
    data class Heading2(val text: String) : MdBlock()
    data class Heading3(val text: String) : MdBlock()
    data class Heading4(val text: String) : MdBlock()
    data class Paragraph(val text: String) : MdBlock()
    data class ListItems(val items: List<String>) : MdBlock()
    data class Quote(val text: String) : MdBlock()
    data class Code(val text: String) : MdBlock()
    data class Checkbox(val text: String, val checked: Boolean) : MdBlock()
    data class Table(val rows: List<List<String>>) : MdBlock()
    data object HorizontalRule : MdBlock()
}