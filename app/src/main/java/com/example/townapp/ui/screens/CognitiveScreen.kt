package com.example.townapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.components.TopNavBar

@Composable
fun CognitiveScreen(
    onNavigateToScalpel: () -> Unit,
    onNavigateToSimulator: () -> Unit,
    onNavigateToIdiomCritic: () -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopNavBar(title = "认知觉醒", showBack = true, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            "🧠 认知觉醒",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "在这里，你可以探索不同的认知视角，挑战传统观念，找到属于自己的思考方式。",
                            fontSize = 14.sp,
                            color = Color(0xFF558B2F),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Text(
                    "选择你想探索的模块：",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF37474F),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NavigationButton(
                        icon = "🔪",
                        title = "认知手术刀",
                        description = "学会五步批判法，剖析生活中的各种现象",
                        onClick = onNavigateToScalpel
                    )

                    NavigationButton(
                        icon = "🎮",
                        title = "双生存法则模拟器",
                        description = "在不同场景中做出选择，体验不同的人生路径",
                        onClick = onNavigateToSimulator
                    )

                    NavigationButton(
                        icon = "📖",
                        title = "成语批判解读",
                        description = "重新解读传统成语，打破规训，发现新视角",
                        onClick = onNavigateToIdiomCritic
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("💡 自由探索", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF37474F))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "这里没有「必须」和「应该」。你可以随时离开，随时回来。你的时间，由你自己支配。",
                            fontSize = 13.sp,
                            color = Color(0xFF78909C),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
fun NavigationButton(
    icon: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5E6D3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFE8DFD4)),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A3728)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    description,
                    fontSize = 14.sp,
                    color = Color(0xFF8B7355),
                    lineHeight = 18.sp
                )
            }

            Text("→", fontSize = 20.sp, color = Color(0xFFA0896D))
        }
    }
}