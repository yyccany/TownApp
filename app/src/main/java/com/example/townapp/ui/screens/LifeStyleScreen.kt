package com.example.townapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.townapp.data.model.LifeStyleEntry
import com.example.townapp.ui.viewmodel.LifeStyleViewModel
import kotlinx.coroutines.launch

/**
 * 生活图鉴主列表页。
 *
 * 展示所有生活方式条目，已解锁的可见名称和详情，
 * 未解锁的显示"???"、解锁进度条。
 *
 * 当有新条目解锁时，底部弹出 Snackbar 通知。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifeStyleScreen(
    viewModel: LifeStyleViewModel = viewModel(),
    onEntryClick: (LifeStyleEntry) -> Unit,
    onBack: () -> Unit
) {
    val entries by viewModel.allEntries.collectAsState()
    val progressMap by viewModel.unlockProgressMap.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // 监听解锁事件
    LaunchedEffect(Unit) {
        viewModel.recentlyUnlocked.collect { entryId ->
            val entry = viewModel.getEntryById(entryId)
            if (entry != null) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "解锁生活方式：${entry.name}",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("人类生活图鉴") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← 返回小镇")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        if (entries.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "暂无生活方式条目。\n通过学习和探索解锁更多。",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(32.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .graphicsLayer { clip = true },
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(entries, key = { it.id }) { entry ->
                    LifeStyleCard(
                        entry = entry,
                        progress = progressMap[entry.id] ?: 0,
                        onClick = { onEntryClick(entry) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LifeStyleCard(
    entry: LifeStyleEntry,
    progress: Int = 0,
    onClick: () -> Unit
) {
    val maxProgress = 4

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = entry.isUnlocked) { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (entry.isUnlocked)
                MaterialTheme.colorScheme.surface
            else
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // 名称行
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (entry.isUnlocked) entry.name else "???",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                if (!entry.isUnlocked) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = "未解锁",
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "已解锁",
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            // 地域
            if (entry.isUnlocked) {
                Text(
                    text = entry.region,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // 提示/状态
            Text(
                text = entry.unlockHint,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )

            // 未解锁条目：显示解锁进度条
            if (!entry.isUnlocked) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 进度条
                    LinearProgressIndicator(
                        progress = { (progress.toFloat() / maxProgress).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$progress/$maxProgress",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}