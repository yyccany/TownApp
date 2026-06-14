package com.example.townapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.*

data class ModernQuote(
    val id: Int,
    val category: String,
    val categoryColor: Color,
    val categoryIcon: String,
    val original: String,
    val townInterpretation: String
)

// 生成现代清醒语录数据（5大类×10条=50条）
private fun generateModernQuotes(): List<ModernQuote> {
    val categories = listOf(
        Triple("忙碌与充实", Color(0xFFE91E63), listOf(
            Pair("很多人忙得两脚不沾地，却永远没有交付结果", "忙碌是一种状态，不是一种价值"),
            Pair("有人不断填满时间，却很少接触到关键节点", "填满时间很容易，推进事情很难"),
            Pair("忙碌很容易创造虚假的充实感，却未必创造价值", "有些忙碌能创造价值，有些不能"),
            Pair("多数空转不是因为不够努力，而是资源错配", "方向错了，越努力越累"),
            Pair("待办事项只是为了掩盖优先级的缺失", "很多事情，其实根本不用做"),
            Pair("真正重要的不是忙碌，是推进核心问题的能力", "决定结果的，永远是少数几个关键节点"),
            Pair("不要用战术上的勤奋，掩盖战略上的懒惰", "你可以选择忙碌，也可以选择不忙，这都是你的自由"),
            Pair("你的待办清单很长，但最重要的事只有一件", "分清主次，才能真正推进"),
            Pair("时间管理不是让你更忙，而是让你更有效", "效率不是忙碌的代名词"),
            Pair("别用虚假充实感欺骗自己，结果不会说谎", "真实地面对自己，比什么都重要")
        )),
        Triple("努力与结果", Color(0xFF9C27B0), listOf(
            Pair("绝大多数人的努力，还没到拼天赋的地步", "努力是一个变量，不是一个结果"),
            Pair("你可以有梦想，但别让梦想把你拖死", "有些事情，不是努力就能改变的"),
            Pair("知道自己不行，比假装很行重要一万倍", "承认自己做不到，也是一种实事求是"),
            Pair("别拿「佛系」当遮羞布，你那是懒不是通透", "懒也是一种选择，没有对错"),
            Pair("你的焦虑90%来自能力配不上野心", "野心和能力不匹配的时候，焦虑就会产生"),
            Pair("努力不一定有结果，但不努力一定很舒服", "结果是客观的，和你付出了多少情绪无关"),
            Pair("普通人最大的问题是认不清自己几斤几两", "认清自己的边界，比盲目努力更重要"),
            Pair("不是所有的努力都有回报，但一定有意义", "过程本身，就是最大的收获"),
            Pair("选择比努力更重要，但选择也需要努力", "两者并不矛盾"),
            Pair("真正的努力，是让自己有选择的权利", "不是为了感动自己")
        )),
        Triple("自我与他人", Color(0xFF673AB7), listOf(
            Pair("永远不要指出别人的任何问题，包括亲戚朋友", "每个人都有自己的人生轨迹"),
            Pair("成年人最大的清醒是不介入别人的生活", "别人的选择，是别人的事"),
            Pair("不要用自己的经验指点别人，你的经验预测不了别人的未来", "你的经验，只适用于你自己"),
            Pair("莫做他人生命的摆渡人，哪怕血缘织就脐带", "你无法叫醒一个不想醒的人"),
            Pair("认知是盘根错节的古树，三两句春风吹不散经年寒霜", "改变一个人，需要他自己撞南墙"),
            Pair("你的价值不在别人嘴里", "别人怎么看你，和你无关"),
            Pair("对人八分好，留两分爱自己", "你不必为了满足别人的期待而活"),
            Pair("最好的关系，是彼此不累", "不强求，不讨好"),
            Pair("学会拒绝，比学会付出更难", "拒绝是一种能力"),
            Pair("你不是人民币，做不到每个人都喜欢", "做自己就好")
        )),
        Triple("时间与意义", Color(0xFF3F51B5), listOf(
            Pair("人生是旷野，不是轨道", "人生没有必须要走的路"),
            Pair("人生没有标准答案，每个人都有自己的节奏", "每个人的节奏都不一样，没有快慢之分"),
            Pair("真正的自由是站在街头谁也不等的自由", "你不必按照别人的时间表生活"),
            Pair("生活是自己的，与他人无关", "时间是你的，你可以浪费它"),
            Pair("慢慢来，谁都有雨后晴天", "发呆、躺平、什么都不做，都不是浪费时间"),
            Pair("所有的生活都是自己选的，结局不是得到就是学到", "你可以选择任何你喜欢的生活方式"),
            Pair("人生不止一个方向，多做让自己快乐的事", "快乐是主观的，没有统一标准"),
            Pair("活着的意义，是你自己赋予的", "没有标准答案"),
            Pair("时间是最公平的资源，你怎么用它决定了你", "每个人每天都只有24小时"),
            Pair("重要的不是你活了多久，而是你怎么活", "质量比数量更重要")
        )),
        Triple("成功与规训", Color(0xFF2196F3), listOf(
            Pair("人生最大的成功是用自己喜欢的方式过一生", "成功没有统一的标准"),
            Pair("别总盯着别人的高光时刻，你没看到人家摔了多少跤", "别人的成功，和你无关"),
            Pair("不要因为别人都在跑，你就跟着跑", "你不必成为别人期待的样子"),
            Pair("所谓的成功，只是社会给你画的一个圈", "社会的规训，只是一种建议，不是命令"),
            Pair("普通家庭的孩子，别玩富二代那套，你没那个资本", "实事求是，有多少能力办多少事"),
            Pair("卷又卷不赢，躺又躺不平，不如按自己的节奏走", "你可以选择平凡，也可以选择不平凡"),
            Pair("到老了最牛的不是攒了多少钱，是没亏过心", "活着本身，就是一种意义"),
            Pair("社会标准是用来参考的，不是用来绑架你的", "你有权利选择自己的标准"),
            Pair("平凡不等于平庸，普通也不等于失败", "每个人都有自己的价值"),
            Pair("不要被世俗的成功定义，找到自己的路", "你的路，只有你自己能走")
        ))
    )

    val allQuotes = mutableListOf<ModernQuote>()
    var id = 1

    categories.forEach { (category, color, quotes) ->
        quotes.forEach { (original, interpretation) ->
            allQuotes.add(
                ModernQuote(
                    id = id++,
                    category = category,
                    categoryColor = color,
                    categoryIcon = getIconForCategory(category),
                    original = original,
                    townInterpretation = interpretation
                )
            )
        }
    }

    return allQuotes
}

private fun getIconForCategory(category: String): String {
    return when (category) {
        "忙碌与充实" -> "⏰"
        "努力与结果" -> "💪"
        "自我与他人" -> "🤝"
        "时间与意义" -> "🌿"
        "成功与规训" -> "🏆"
        else -> "💡"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernQuotesScreen(
    onBack: () -> Unit
) {
    val quotes by remember { mutableStateOf(generateModernQuotes()) }
    var selectedQuote by remember { mutableStateOf<ModernQuote?>(null) }

    if (selectedQuote != null) {
        QuoteDetailScreen(
            quote = selectedQuote!!,
            onBack = { selectedQuote = null }
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("💬 现代清醒语录") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Text("←", fontSize = 20.sp)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF5F5F5))
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF5F5F5)),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("💬", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "现代清醒语录",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                            Text(
                                "永远不说\"你应该\"，永远不评判对错",
                                fontSize = 13.sp,
                                color = Color(0xFF999999),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }

                val groupedQuotes = quotes.groupBy { it.category }
                groupedQuotes.forEach { (category, categoryQuotes) ->
                    item {
                        QuoteCategoryHeader(
                            category = category,
                            icon = categoryQuotes.first().categoryIcon,
                            count = categoryQuotes.size,
                            color = categoryQuotes.first().categoryColor
                        )
                    }
                    items(categoryQuotes) { quote ->
                        QuoteCard(
                            quote = quote,
                            onClick = { selectedQuote = quote }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun QuoteCategoryHeader(category: String, icon: String, count: Int, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 16.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = category,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${count}条",
            fontSize = 12.sp,
            color = Color(0xFF999999)
        )
    }
}

@Composable
fun QuoteCard(
    quote: ModernQuote,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                isPressed = true
                onClick()
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = quote.original,
                fontSize = 14.sp,
                color = Color(0xFF333333),
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(quote.categoryColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(quote.categoryIcon, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "小镇解读 →",
                    fontSize = 12.sp,
                    color = quote.categoryColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteDetailScreen(
    quote: ModernQuote,
    onBack: () -> Unit
) {
    var showContent by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(200)
        showContent = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(quote.category) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", fontSize = 20.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = quote.categoryColor.copy(alpha = 0.15f))
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(quote.categoryIcon, fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                quote.category,
                                fontSize = 14.sp,
                                color = quote.categoryColor,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 100)) + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("📝", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "原话",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color(0xFF666666)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                quote.original,
                                fontSize = 15.sp,
                                color = Color(0xFF333333),
                                lineHeight = 24.sp
                            )
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 200)) + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = quote.categoryColor.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🏘️", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "小镇解读",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = quote.categoryColor
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                quote.townInterpretation,
                                fontSize = 15.sp,
                                color = Color(0xFF333333),
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            item {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(animationSpec = tween(300, delayMillis = 300)) + slideInVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "💡 实事求是",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF666666)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "小镇不告诉你「你应该怎么想」，只陈述客观事实。你怎么理解，怎么选择，完全由你自己决定。",
                                fontSize = 14.sp,
                                color = Color(0xFF666666),
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
