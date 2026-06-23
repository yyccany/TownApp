package com.example.townapp.feature.human_narrative.cognition.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.theme.AppDimens

data class ConditioningItem(
    val id: Int,
    val title: String,
    val description: String,
    val consequence: String,
    val perspective: String
)

private fun generateConditioningData(): List<ConditioningItem> {
    return listOf(
        ConditioningItem(
            id = 1,
            title = "「都是为你好」的包办",
            description = "父母替你选择专业、工作、伴侣，用爱的名义剥夺你的选择权",
            consequence = "成年后面对选择时感到恐惧和无力，难以做出独立决策",
            perspective = "爱是尊重对方的意愿，不是替对方生活"
        ),
        ConditioningItem(
            id = 2,
            title = "否定式教育",
            description = "\"这点小事都做不好\"、\"你怎么这么笨\"，习惯性否定孩子的能力",
            consequence = "形成\"我不行\"的核心信念，成年后缺乏自信，害怕失败",
            perspective = "批评的目的是解决问题，不是摧毁自信"
        ),
        ConditioningItem(
            id = 3,
            title = "过度谦虚教育",
            description = "孩子表现出色时，父母说\"这不算什么\"、\"别骄傲\"",
            consequence = "无法坦然接受赞美，总觉得自己不配拥有好的事物",
            perspective = "承认自己的优秀，和骄傲无关"
        ),
        ConditioningItem(
            id = 4,
            title = "比较式成长",
            description = "\"你看看别人家的孩子\"，永远在和同龄人比较",
            consequence = "形成\"我不够好\"的焦虑，一生都在追逐别人的认可",
            perspective = "每个人都有自己的节奏，无需比较"
        ),
        ConditioningItem(
            id = 5,
            title = "牺牲感绑架",
            description = "\"我为了你放弃了多少\"，用牺牲感换取孩子的服从",
            consequence = "内心充满愧疚感，难以建立健康的边界",
            perspective = "父母的选择是他们的责任，不是孩子的债"
        ),
        ConditioningItem(
            id = 6,
            title = "性别角色规训",
            description = "\"男孩子不能哭\"、\"女孩子要文静\"，用性别刻板印象约束孩子",
            consequence = "压抑真实的情感表达，无法自由做自己",
            perspective = "你的价值不取决于是否符合刻板印象"
        ),
        ConditioningItem(
            id = 7,
            title = "分数至上主义",
            description = "成绩是衡量一切的标准，其他都不重要",
            consequence = "形成\"只有优秀才有价值\"的错误信念，难以接受平凡",
            perspective = "分数只是能力的一部分，不是全部"
        ),
        ConditioningItem(
            id = 8,
            title = "稳定至上洗脑",
            description = "\"一定要找个稳定的工作\"，把稳定当作人生最高追求",
            consequence = "害怕风险，不敢尝试新事物，错过可能的机遇",
            perspective = "稳定是一种选择，不是唯一的选择"
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationConditioningScreen(
    onBack: () -> Unit
) {
    val items by remember { mutableStateOf(generateConditioningData()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📚 教育规训解剖") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", fontSize = 20.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFF9EC))
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFF9EC)),
            contentPadding = PaddingValues(AppDimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimens.radiusLarge),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0E0))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📚", fontSize = 40.sp)
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            "教育规训解剖",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A3728)
                        )
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            "审视成长过程中的规训与束缚",
                            fontSize = 13.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimens.radiusMedium),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "💡 小镇观察",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A3728)
                        )
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            "我们的成长过程中，很多「理所当然」的观念，其实是他人强加的规训。" +
                            "这些规训塑造了我们的思维模式和行为习惯，但不一定符合我们的真实需求。",
                            fontSize = 13.sp,
                            color = Color(0xFF666666),
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            itemsIndexed(items) { _, item ->
                ConditioningCard(item = item)
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimens.radiusMedium),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "🌱 自我觉察",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF666666)
                        )
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            "觉察是改变的开始。当你意识到某些思维模式不是真正属于你的时候，" +
                            "你就已经走在了重新选择的路上。你不需要对抗过去，只需温柔地看见。",
                            fontSize = 13.sp,
                            color = Color(0xFF666666),
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ConditioningCard(item: ConditioningItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${item.id}. ${item.title}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A3728)
            )
            
            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            
            Column(
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
            ) {
                Text(
                    text = "• 规训形式：${item.description}",
                    fontSize = 13.sp,
                    color = Color(0xFF666666)
                )
                
                Text(
                    text = "• 可能影响：${item.consequence}",
                    fontSize = 13.sp,
                    color = Color(0xFF888888)
                )
            }
            
            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF0E0))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "\"${item.perspective}\"",
                    fontSize = 13.sp,
                    color = Color(0xFF4A3728),
                    fontWeight = FontWeight.Medium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}