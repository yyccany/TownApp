package com.example.townapp.ui.screens

import com.example.townapp.ui.theme.AppDimens

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 清洗文档/文件夹名称，去除序号前缀、md后缀、前置小数点，用于UI展示
 * 原始示例：05‑饮食健康系统.md → 输出：饮食健康系统
 */
fun cleanDocDisplayName(rawName: String): String {
    return rawName
        .replace(Regex("^\\d{1,2}[-‑.]\\s*"), "")       // 01-  01.
        .replace(Regex("^\\(\\d{1,2}\\)\\s*"), "")      // (1)
        .replace(Regex("^\\[\\d{1,2}]\\s*"), "")        // [01]
        .replace(Regex("^[①②③④⑤⑥⑦⑧⑨⑩]\\s*"), "")   // ①
        .replace(Regex("^\\."), "")                      // .hidden
        .replace(Regex("\\.md$"), "")                    // 去掉md后缀
        .trim()
}

/**
 * 清洗 Markdown 正文内容，去除标题行首的数字序号符号
 * 示例：## 05‑基础设定 → ## 基础设定
 */
fun cleanMarkdownContent(rawMdText: String): String {
    val lineList = rawMdText.lines().map { line ->
        // 匹配标题行，去除标题后的数字序号
        if (line.startsWith("#")) {
            line.replace(Regex("^(#{1,6}\\s*)\\d{1,2}[.‑]\\s*"), "$1")
                .replace(Regex("^(#{1,6}\\s*)\\(\\d{1,2}\\)\\s*"), "$1")
                .replace(Regex("^(#{1,6}\\s*)\\[\\d{1,2}]\\s*"), "$1")
                .replace(Regex("^(#{1,6}\\s*)[①②③④⑤⑥⑦⑧⑨⑩]\\s*"), "$1")
        } else line
    }
    return lineList.joinToString("\n")
}

/**
 * 计算文本中关键词匹配次数（忽略大小写）
 */
private fun countMatches(text: String, query: String): Int {
    if (query.isBlank()) return 0
    var count = 0
    var idx = text.lowercase().indexOf(query.lowercase())
    while (idx >= 0) {
        count++
        idx = text.lowercase().indexOf(query.lowercase(), idx + query.length)
    }
    return count
}

/**
 * 构建带高亮样式的 AnnotatedString
 */
private fun buildHighlightedString(text: String, query: String): AnnotatedString {
    if (query.isBlank()) return AnnotatedString(text)
    val lowerQuery = query.lowercase()
    val lowerText = text.lowercase()
    return buildAnnotatedString {
        var lastIndex = 0
        var idx = lowerText.indexOf(lowerQuery)
        while (idx >= 0) {
            append(text.substring(lastIndex, idx))
            withStyle(style = SpanStyle(background = Color(0xFFFFF176), color = Color.Black)) {
                append(text.substring(idx, idx + query.length))
            }
            lastIndex = idx + query.length
            idx = lowerText.indexOf(lowerQuery, lastIndex)
        }
        append(text.substring(lastIndex))
    }
}

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
    // 当前所在文件夹名称（用于面包屑）
    var currentFolder by remember { mutableStateOf("") }
    // MD正文内容
    var mdContent by remember { mutableStateOf("") }
    // 加载状态
    var isLoading by remember { mutableStateOf(false) }
    // 搜索框显隐
    var showSearch by remember { mutableStateOf(false) }
    // 搜索关键词
    var searchQuery by remember { mutableStateOf("") }
    // 文档内容缓存：路径 -> 清洗后内容
    val docCache = remember { mutableStateMapOf<String, String>() }

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
                            ?.filter { it.endsWith(".md") && !it.startsWith(".") }?.sorted() ?: emptyList()
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
        
        // 提取文件夹名用于面包屑
        currentFolder = selectedPath.removePrefix("docs/").substringBefore("/")
        
        // 优先使用缓存，避免重复IO
        val cached = docCache[selectedPath]
        if (cached != null) {
            mdContent = cached
            isLoading = false
            return@LaunchedEffect
        }
        
        launch(Dispatchers.IO) {
            try {
                // 输出完整路径日志，便于核对
                Log.d("DocumentScreen", "尝试读取文件: $selectedPath")
                
                val content = context.assets.open(selectedPath)
                    .bufferedReader(charset = Charsets.UTF_8)
                    .readText()
                
                val cleaned = cleanMarkdownContent(content)
                // 写入缓存
                docCache[selectedPath] = cleaned
                // 状态更新在主线程执行（Compose快照系统会自动处理）
                mdContent = cleaned
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
                        .padding(vertical = AppDimens.paddingSmall),
                    state = rememberLazyListState()
                ) {
                    items(folderList) { folderName ->
                        // 一级分类
                        val isExpand = expandState[folderName] ?: false
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandState[folderName] = !isExpand }
                                .padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isExpand) "▼" else "▶",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.width(20.dp)
                            )
                            // 清洗文件夹名称，剔除数字前缀等
                            val showName = cleanDocDisplayName(folderName)
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
                                                currentDocName = cleanDocDisplayName(fileName)
                                                
                                                // 延迟关闭抽屉，避免状态冲突
                                                scope.launch {
                                                    delay(100)
                                                    drawerState.close()
                                                }
                                            }
                                            .padding(start = 36.dp, top = 4.dp, bottom = 4.dp, end = 16.dp)
                                    ) {
                                        Text(
                                            text = cleanDocDisplayName(fileName),
                                            fontSize = 14.sp,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = AppDimens.paddingSmall, vertical = AppDimens.paddingSmall)
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
                    },
                    actions = {
                        // 搜索按钮（后续迭代再启用）
                        // IconButton(onClick = { showSearch = !showSearch }) { ... }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .navigationBarsPadding()
            ) {
                // 面包屑导航
                if (selectedPath.isNotBlank()) {
                    val breadcrumb = buildString {
                        append("文档")
                        if (currentFolder.isNotBlank()) {
                            append(" / ${cleanDocDisplayName(currentFolder)}")
                        }
                        if (currentDocName.isNotBlank() && currentDocName != "小镇文档") {
                            append(" / $currentDocName")
                        }
                    }
                    Text(
                        text = breadcrumb,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = AppDimens.paddingXLarge, vertical = AppDimens.paddingSmall)
                    )
                }
                
                // 搜索框
                if (showSearch) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("搜索当前文档...") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppDimens.paddingXLarge),
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "清空", modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    )
                    
                    // 匹配计数提示
                    if (searchQuery.isNotBlank()) {
                        val matchCount = countMatches(mdContent, searchQuery)
                        Text(
                            text = if (matchCount > 0) "找到 $matchCount 处匹配" else "无匹配结果",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (matchCount > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(horizontal = AppDimens.paddingXLarge, vertical = AppDimens.paddingSmall)
                        )
                    }
                }
                
                // 主阅读区
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
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
                                content = mdContent,
                                highlightQuery = searchQuery,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = AppDimens.paddingMedium)
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
}

@Composable
private fun MarkdownContent(
    content: String,
    highlightQuery: String = "",
    modifier: Modifier = Modifier
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
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(scrollState)
            .padding(horizontal = AppDimens.paddingXLarge, vertical = AppDimens.paddingLarge)
    ) {
        // 自动滚动控制按钮（演示功能，正式版本可注释）
        Button(
            onClick = { autoScroll = !autoScroll },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppDimens.paddingLarge)
        ) {
            Text(if (autoScroll) "停止滚动" else "开启自动滚动")
        }
        
        // 渲染MD内容
        blocks.forEach { block ->
            when (block) {
                is MdBlock.Heading1 -> Text(
                    text = buildHighlightedString(block.text, highlightQuery),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppDimens.paddingLarge)
                )
                is MdBlock.Heading2 -> Text(
                    text = buildHighlightedString(block.text, highlightQuery),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppDimens.paddingMedium)
                )
                is MdBlock.Heading3 -> Text(
                    text = buildHighlightedString(block.text, highlightQuery),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
                is MdBlock.Heading4 -> Text(
                    text = buildHighlightedString(block.text, highlightQuery),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppDimens.paddingSmall)
                )
                is MdBlock.Paragraph -> Text(
                    text = buildHighlightedString(block.text, highlightQuery),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppDimens.paddingSmall)
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
                                    text = buildHighlightedString(item, highlightQuery),
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
                        .padding(vertical = AppDimens.paddingSmall),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = buildHighlightedString(block.text, highlightQuery),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is MdBlock.Code -> Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppDimens.paddingSmall),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = buildHighlightedString(block.text, highlightQuery),
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
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = buildHighlightedString(block.text, highlightQuery),
                        fontSize = 15.sp,
                        color = if (block.checked) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                is MdBlock.Table -> {
                    // 表格区域单独处理横向滚动，不影响整体布局
                    TableContent(rows = block.rows, highlightQuery = highlightQuery)
                }
                is MdBlock.HorizontalRule -> HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppDimens.paddingLarge),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
private fun TableContent(
    rows: List<List<String>>,
    highlightQuery: String = ""
) {
    if (rows.isEmpty()) return

    // 表格区域单独处理横向滚动，不影响整体布局
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppDimens.paddingSmall)
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
                                text = buildHighlightedString(cell, highlightQuery),
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
