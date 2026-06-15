package com.example.townapp.ui.screens

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.database.entity.LifeArchiveEntity
import com.example.townapp.ui.components.ChildhoodReflectionSection
import java.text.SimpleDateFormat
import java.util.*

/**
 * 人生存档界面
 *
 * 你的秘密基地。
 * 只有你自己能看，三个小家伙会帮你守着这些秘密。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifeArchiveScreen(
    archives: List<LifeArchiveEntity>,
    onCreateArchive: (content: String, emoji: String?, mood: String?) -> Unit,
    onUpdateArchive: (id: Long, content: String, emoji: String?, mood: String?) -> Unit,
    onDeleteArchive: (id: Long) -> Unit,
    onBack: () -> Unit = {}
) {
    var showEditor by remember { mutableStateOf(false) }
    var editingArchive by remember { mutableStateOf<LifeArchiveEntity?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 顶部导航
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color(0xFFFFD93D),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                        Text(
                            text = "🔐 人生存档",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2D2D44)
                )
            )

            // 引导语
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2D2D44)
                ),
                shape = RoundedCornerShape(AppDimens.radiusLarge)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🔐",
                        fontSize = 36.sp
                    )
                    Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                    Text(
                        text = "这里是只属于你的秘密基地",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "你可以把你所有不能跟别人说的话\n都放在这里。\n它们会安安静静地待在这里，\n永远陪着你。",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                }
            }

            // 存档列表
            if (archives.isEmpty()) {
                // 空状态
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "还没有存档",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            text = "点击下方 + 写下你的第一句话吧",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.3f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = AppDimens.paddingLarge),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium),
                    contentPadding = PaddingValues(vertical = AppDimens.paddingSmall)
                ) {
                    items(archives.size) { index ->
                        ArchiveCard(
                            archive = archives[index],
                            onEdit = {
                                editingArchive = archives[index]
                                showEditor = true
                            },
                            onDelete = { onDeleteArchive(archives[index].id) }
                        )
                    }
                }
            }

            // 底部添加按钮
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppDimens.paddingXXLarge, vertical = AppDimens.paddingSmall),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        editingArchive = null
                        showEditor = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4ECDC4)
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                    Text(
                        text = "写点什么",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // 📮 给小时候的自己捎句话
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppDimens.paddingXXLarge, vertical = AppDimens.paddingSmall)
            ) {
                ChildhoodReflectionSection()
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingLarge))
        }

        // 编辑对话框
        if (showEditor) {
            ArchiveEditorDialog(
                archive = editingArchive,
                onSave = { content, emoji, mood ->
                    if (editingArchive != null) {
                        onUpdateArchive(editingArchive!!.id, content, emoji, mood)
                    } else {
                        onCreateArchive(content, emoji, mood)
                    }
                    showEditor = false
                    editingArchive = null
                },
                onDismiss = {
                    showEditor = false
                    editingArchive = null
                }
            )
        }
    }
}

/**
 * 存档卡片
 */
@Composable
private fun ArchiveCard(
    archive: LifeArchiveEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2D2D44)
        ),
        shape = RoundedCornerShape(AppDimens.radiusLarge)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 心情标签
                archive.emoji?.let { emoji ->
                    Text(
                        text = emoji,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                }
                archive.mood?.let { mood ->
                    Surface(
                        color = getMoodColor(mood).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(AppDimens.radiusMedium)
                    ) {
                        Text(
                            text = getMoodText(mood),
                            fontSize = 11.sp,
                            color = getMoodColor(mood),
                            modifier = Modifier.padding(horizontal = AppDimens.paddingSmall, vertical = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                // 操作按钮
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "编辑",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(18.dp)
                    )
                }
                IconButton(
                    onClick = { showDeleteConfirm = true },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "删除",
                        tint = Color(0xFFE57373).copy(alpha = 0.7f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            // 内容
            Text(
                text = archive.content,
                fontSize = 15.sp,
                color = Color.White,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            // 时间
            Text(
                text = formatDate(archive.createdAt),
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.4f)
            )
        }
    }

    // 删除确认对话框
    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            containerColor = Color(0xFF2D2D44),
            shape = RoundedCornerShape(AppDimens.radiusXLarge),
            title = {
                Text(
                    text = "确定要删除吗？",
                    color = Color.White
                )
            },
            text = {
                Text(
                    text = "删除后，这个存档将永远消失。\n但没关系，如果你想让它消失，它就可以消失。",
                    color = Color.White.copy(alpha = 0.7f),
                    lineHeight = 22.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDeleteConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE57373)
                    ),
                    shape = RoundedCornerShape(AppDimens.radiusMedium)
                ) {
                    Text("删除")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text(
                        text = "留着",
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        )
    }
}

/**
 * 存档编辑器对话框
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArchiveEditorDialog(
    archive: LifeArchiveEntity?,
    onSave: (content: String, emoji: String?, mood: String?) -> Unit,
    onDismiss: () -> Unit
) {
    var content by remember { mutableStateOf(archive?.content ?: "") }
    var selectedEmoji by remember { mutableStateOf(archive?.emoji) }
    var selectedMood by remember { mutableStateOf(archive?.mood) }

    val emojis = listOf("😢", "😡", "😰", "😴", "🤗", "😌", "🥰", "😎", "🤔", "✨")
    val moods = listOf(
        "sad" to "难过",
        "angry" to "生气",
        "afraid" to "害怕",
        "anxious" to "焦虑",
        "tired" to "疲惫",
        "happy" to "开心",
        "peaceful" to "平静",
        "loved" to "被爱"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2D2D44),
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = if (archive == null) "写点什么吧" else "编辑存档",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                // 心情选择
                Text(
                    text = "此刻的心情（可选）",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
                ) {
                    moods.take(4).forEach { (key, label) ->
                        FilterChip(
                            selected = selectedMood == key,
                            onClick = {
                                selectedMood = if (selectedMood == key) null else key
                            },
                            label = { Text(label, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = getMoodColor(key),
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
                ) {
                    moods.drop(4).forEach { (key, label) ->
                        FilterChip(
                            selected = selectedMood == key,
                            onClick = {
                                selectedMood = if (selectedMood == key) null else key
                            },
                            label = { Text(label, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = getMoodColor(key),
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))

                // Emoji选择
                Text(
                    text = "配一个表情（可选）",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    emojis.forEach { emoji ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(
                                    if (selectedEmoji == emoji) Color(0xFF667EEA).copy(alpha = 0.3f)
                                    else Color.Transparent
                                )
                                .clickable {
                                    selectedEmoji = if (selectedEmoji == emoji) null else emoji
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = emoji, fontSize = 20.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))

                // 内容输入
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = {
                        Text(
                            text = "在这里写下你想说的话...\n\n没有格式要求，没有字数限制。\n想写什么就写什么。",
                            color = Color.White.copy(alpha = 0.3f),
                            fontSize = 14.sp
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF667EEA),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                        cursorColor = Color(0xFF667EEA)
                    ),
                    shape = RoundedCornerShape(AppDimens.radiusMedium)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(content, selectedEmoji, selectedMood) },
                enabled = content.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4ECDC4)
                ),
                shape = RoundedCornerShape(AppDimens.radiusMedium)
            ) {
                Text("保存")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "取消",
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
    )
}

/**
 * 获取心情对应的颜色
 */
private fun getMoodColor(mood: String): Color {
    return when (mood) {
        "sad" -> Color(0xFF6B9EFF)
        "angry" -> Color(0xFFFF6B6B)
        "afraid" -> Color(0xFF9B59B6)
        "anxious" -> Color(0xFFFFD93D)
        "tired" -> Color(0xFF95A5A6)
        "happy" -> Color(0xFF4ECDC4)
        "peaceful" -> Color(0xFF3498DB)
        "loved" -> Color(0xFFFF9E9E)
        else -> Color(0xFF95A5A6)
    }
}

/**
 * 获取心情对应的文字
 */
private fun getMoodText(mood: String): String {
    return when (mood) {
        "sad" -> "难过"
        "angry" -> "生气"
        "afraid" -> "害怕"
        "anxious" -> "焦虑"
        "tired" -> "疲惫"
        "happy" -> "开心"
        "peaceful" -> "平静"
        "loved" -> "被爱"
        else -> mood
    }
}

/**
 * 格式化日期
 */
private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
