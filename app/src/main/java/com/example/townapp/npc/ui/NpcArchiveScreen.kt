package com.example.townapp.npc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.npc.model.NpcDisplayVo
import com.example.townapp.npc.repository.NpcRepository
import com.example.townapp.ui.components.NpcMoodOverlay

/**
 * 众生时间档案馆主页面
 *
 * 核心定位：平等、包容、多元的人文公益展示载体
 * 浏览任意 NPC 完整人生，直观看见不同年龄、职业、境遇人群的岁月取舍
 *
 * 设计原则（落实三大内核）：
 * 1. 以人为本：无热门推荐、无排行榜、无优先级分类
 * 2. 自由平等：所有 NPC 入口平等，可随机翻阅、碎片化浏览
 * 3. 实事求是：客观呈现平淡、遗憾、微小欢喜多元人生，不美化苦难
 *
 * 布局结构：
 * ┌─────────────────────────────────┐
 * │  分类筛选栏（片区/年龄段/职业） │
 * ├─────────────────────────────────┤
 * │                                 │
 * │       NPC 列表（卡片式）         │
 * │   头像 + 姓名 + 职业 + 年龄     │
 * │                                 │
 * └─────────────────────────────────┘
 */
@Composable
fun NpcArchiveScreen(
    repository: NpcRepository,
    onSelectNpc: (NpcDisplayVo) -> Unit,
    onBack: () -> Unit
) {
    // 筛选状态
    var selectedDistrict by remember { mutableStateOf(0) }
    var selectedAgeRange by remember { mutableStateOf(-1) }
    var selectedJob by remember { mutableStateOf(0) }

    // 获取分类列表
    val districtList = remember { repository.getDistrictList() }
    val ageRangeList = remember { repository.getAgeRangeList() }
    val jobList = remember { repository.getJobList() }

    // 获取 NPC 列表（带筛选，使用 derivedStateOf 确保筛选变化时重新计算）
    val npcList by remember {
        derivedStateOf {
            repository.getAllNpcBasicInfo().filter { npc ->
                val status = repository.getNpcStatus(npc.npcId)
                val ageMatch = selectedAgeRange == -1 || when (selectedAgeRange) {
                    0 -> status.age in 18..35
                    1 -> status.age in 36..55
                    2 -> status.age >= 56
                    else -> true
                }
                val jobMatch = selectedJob == 0 || npc.jobId == selectedJob
                ageMatch && jobMatch
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A1A1A), Color(0xFF0A0A0A))
                )
            )
    ) {
        // 顶部标题栏
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            // 返回按钮
            Text(
                text = "← 返回",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onBack() }
            )

            // 标题
            Text(
                text = "众生时间档案馆",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // 分类筛选栏
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // 片区筛选
            FilterRow(
                label = "片区",
                options = districtList,
                selected = selectedDistrict,
                onSelect = { selectedDistrict = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 年龄段筛选
            FilterRow(
                label = "年龄段",
                options = ageRangeList,
                selected = selectedAgeRange,
                onSelect = { selectedAgeRange = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 职业筛选
            FilterRow(
                label = "职业",
                options = jobList,
                selected = selectedJob,
                onSelect = { selectedJob = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // NPC 列表
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(npcList) { npc ->
                val status = repository.getNpcStatus(npc.npcId)
                val palette = repository.getNpcMoodPalette(npc.npcId)

                NpcArchiveCard(
                    npc = npc,
                    age = status.age,
                    health = status.health,
                    palette = palette,
                    onClick = { onSelectNpc(npc) }
                )
            }
        }

        // 底部提示（自由平等原则）
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "自由翻阅，没有热门推荐，每个人都是完整独立的个体",
                color = Color.White.copy(alpha = 0.3f),
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 筛选栏行组件
 */
@Composable
private fun FilterRow(
    label: String,
    options: List<Pair<Int, String>>,
    selected: Int,
    onSelect: (Int) -> Unit
) {
    Column {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            options.forEach { (id, name) ->
                val isSelected = selected == id
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isSelected) Color(0xFFFFD700).copy(alpha = 0.2f)
                            else Color.White.copy(alpha = 0.08f)
                        )
                        .clickable { onSelect(id) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = name,
                        color = if (isSelected) Color(0xFFFFD700).copy(alpha = 0.8f)
                        else Color.White.copy(alpha = 0.6f),
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

/**
 * NPC 档案馆卡片组件
 */
@Composable
private fun NpcArchiveCard(
    npc: NpcDisplayVo,
    age: Int,
    health: Int,
    palette: com.example.townapp.npc.model.TonePaletteVo?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // 专属色调背景（极淡）
            palette?.let {
                NpcMoodOverlay(
                    palette = it,
                    visible = true,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 头像占位
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = npc.name.take(1),
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // 信息区
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = npc.name,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${npc.jobName} · ${age}岁",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 11.sp
                    )
                }

                // 健康状态
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.White.copy(alpha = 0.1f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width((60 * health / 100).dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                when {
                                    health >= 70 -> Color(0xFF4CAF50)
                                    health >= 40 -> Color(0xFFFFC107)
                                    else -> Color(0xFFF44336)
                                }.copy(alpha = 0.6f)
                            )
                    )
                }
            }
        }
    }
}