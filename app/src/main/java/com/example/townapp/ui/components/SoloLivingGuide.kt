package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class GuideStage(val stageName: String, val description: String) {
    ACCEPTANCE("接纳期", "不批判，不指责，先接住你的痛苦"),
    EXPERIMENT("尝试期", "尝试一个替代方案，获得真实的快乐"),
    GROWTH("成长期", "建立稳定的生活习惯，获得对生活的掌控感"),
    AWAKENING("觉醒期", "找到自己真正热爱的事情，实现自我价值")
}

data class AlternativeOption(
    val name: String,
    val icon: String,
    val cost: String,
    val timePerDay: String,
    val valueDensity: Int,
    val difficulty: String
)

@Composable
fun SoloLivingGuide() {
    var currentStage by remember { mutableStateOf(GuideStage.ACCEPTANCE) }
    var selectedDoroAlternative by remember { mutableStateOf(0) }
    var selectedTaffyAlternative by remember { mutableStateOf(0) }
    var selectedShortVideoAlternative by remember { mutableStateOf(0) }
    
    val doroAlternatives = listOf(
        AlternativeOption("养一盆绿萝", "🌿", "10元", "1分钟", 1000, "★☆☆☆☆"),
        AlternativeOption("喂楼下的流浪猫", "🐱", "10元/周", "5分钟", 500, "★☆☆☆☆"),
        AlternativeOption("养一只乌龟", "🐢", "50元", "2分钟", 300, "★★☆☆☆"),
        AlternativeOption("养一只仓鼠", "🐹", "100元", "5分钟", 200, "★★☆☆☆"),
        AlternativeOption("沉迷Doro", "🐕", "0元", "2小时", 0, "★★★★★")
    )
    
    val taffyAlternatives = listOf(
        AlternativeOption("拼乐高", "🧱", "100元/套", "30分钟", 100, "★☆☆☆☆"),
        AlternativeOption("练字", "✍️", "20元", "15分钟", 200, "★☆☆☆☆"),
        AlternativeOption("画画", "🎨", "30元", "20分钟", 150, "★★☆☆☆"),
        AlternativeOption("学做饭", "🍳", "0元", "30分钟", 300, "★★☆☆☆"),
        AlternativeOption("给塔菲喵打赏", "😺", "2000元/年", "3小时", 0, "★★★★★")
    )
    
    val shortVideoAlternatives = listOf(
        AlternativeOption("整理一个抽屉", "🗄️", "0元", "10分钟", 200, "★☆☆☆☆"),
        AlternativeOption("擦一遍桌子", "🧹", "0元", "5分钟", 300, "★☆☆☆☆"),
        AlternativeOption("走10分钟路", "🚶", "0元", "10分钟", 150, "★☆☆☆☆"),
        AlternativeOption("读10页书", "📖", "0元", "10分钟", 500, "★★☆☆☆"),
        AlternativeOption("刷短视频", "📱", "0元", "2.5小时", 0, "★★★★★")
    )
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .graphicsLayer { clip = true },
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            WelcomeCard()
        }
        
        item {
            StageSelector(currentStage = currentStage, onStageChange = { currentStage = it })
        }
        
        item {
            StageContent(
                stage = currentStage,
                doroAlternatives = doroAlternatives,
                selectedDoroAlternative = selectedDoroAlternative,
                onDoroAlternativeChange = { selectedDoroAlternative = it },
                taffyAlternatives = taffyAlternatives,
                selectedTaffyAlternative = selectedTaffyAlternative,
                onTaffyAlternativeChange = { selectedTaffyAlternative = it },
                shortVideoAlternatives = shortVideoAlternatives,
                selectedShortVideoAlternative = selectedShortVideoAlternative,
                onShortVideoAlternativeChange = { selectedShortVideoAlternative = it }
            )
        }
        
        item {
            SoloAestheticPrinciplesCard()
        }
        
        item {
            RealLifeExamplesCard()
        }
    }
}

@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0x153498DB)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🌱", fontSize = 28.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "独居青年正向引导",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3498DB)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "独居不是缺陷，是一种选择。一个人生活，也可以过得很好，很精彩，很有尊严。你不需要别人来拯救你，你自己就是自己的英雄。",
                fontSize = 15.sp,
                color = Color(0xFF555555),
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                "我们不会把奶嘴从你嘴里抢走，然后说'你不许哭'。我们会给你提供同样满足你需求，但更健康、更持久、更有价值的替代方案。",
                fontSize = 15.sp,
                color = Color(0xFF3498DB),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun StageSelector(
    currentStage: GuideStage,
    onStageChange: (GuideStage) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "📈 阶梯式引导系统",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3498DB)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GuideStage.values().forEach { stage ->
                    StageButton(
                        stage = stage,
                        isSelected = currentStage == stage,
                        onClick = { onStageChange(stage) }
                    )
                }
            }
        }
    }
}

@Composable
fun StageButton(
    stage: GuideStage,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = if (isSelected) CardDefaults.cardElevation(defaultElevation = 4.dp) else CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                Color(0xFF3498DB) 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val stageIcon = when (stage) {
                GuideStage.ACCEPTANCE -> "💚"
                GuideStage.EXPERIMENT -> "🔬"
                GuideStage.GROWTH -> "🌱"
                GuideStage.AWAKENING -> "✨"
            }
            Text(stageIcon, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                stage.stageName,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.White else Color(0xFF555555)
            )
        }
    }
}

@Composable
fun StageContent(
    stage: GuideStage,
    doroAlternatives: List<AlternativeOption>,
    selectedDoroAlternative: Int,
    onDoroAlternativeChange: (Int) -> Unit,
    taffyAlternatives: List<AlternativeOption>,
    selectedTaffyAlternative: Int,
    onTaffyAlternativeChange: (Int) -> Unit,
    shortVideoAlternatives: List<AlternativeOption>,
    selectedShortVideoAlternative: Int,
    onShortVideoAlternativeChange: (Int) -> Unit
) {
    when (stage) {
        GuideStage.ACCEPTANCE -> AcceptanceContent()
        GuideStage.EXPERIMENT -> ExperimentContent(
            doroAlternatives = doroAlternatives,
            selectedDoroAlternative = selectedDoroAlternative,
            onDoroAlternativeChange = onDoroAlternativeChange,
            taffyAlternatives = taffyAlternatives,
            selectedTaffyAlternative = selectedTaffyAlternative,
            onTaffyAlternativeChange = onTaffyAlternativeChange,
            shortVideoAlternatives = shortVideoAlternatives,
            selectedShortVideoAlternative = selectedShortVideoAlternative,
            onShortVideoAlternativeChange = onShortVideoAlternativeChange
        )
        GuideStage.GROWTH -> GrowthContent()
        GuideStage.AWAKENING -> AwakeningContent()
    }
}

@Composable
fun AcceptanceContent() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("💚", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "接纳期：0-30天",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3498DB)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "你不需要立刻戒掉所有的赛博奶嘴，也不需要立刻变成完美的人。我们唯一的要求是：每天刷Doro/短视频/直播的时间，减少10分钟。",
                fontSize = 15.sp,
                color = Color(0xFF555555),
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoBox(
                icon = "💡",
                title = "今日小目标",
                content = "减少10分钟虚拟内容时间。哪怕只有10分钟，也是值得庆祝的改变。"
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoBox(
                icon = "🏆",
                title = "今日奖励",
                content = "每减少10分钟，获得1点觉醒值。积少成多，变化自然会发生。"
            )
        }
    }
}

@Composable
fun ExperimentContent(
    doroAlternatives: List<AlternativeOption>,
    selectedDoroAlternative: Int,
    onDoroAlternativeChange: (Int) -> Unit,
    taffyAlternatives: List<AlternativeOption>,
    selectedTaffyAlternative: Int,
    onTaffyAlternativeChange: (Int) -> Unit,
    shortVideoAlternatives: List<AlternativeOption>,
    selectedShortVideoAlternative: Int,
    onShortVideoAlternativeChange: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0x0A27AE60)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🔬", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "尝试期：30-90天",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF27AE60)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    "选择一个替代方案，每天坚持10分钟。从最简单的开始，慢慢找到真正适合你的。",
                    fontSize = 15.sp,
                    color = Color(0xFF555555),
                    lineHeight = 24.sp
                )
            }
        }
        
        AlternativeSelector(
            title = "替代Doro：从心疼虚拟小狗到照顾真实小生命",
            subtitle = "你真正需要的是被需要的感觉，而这一点，真实的小生命可以给你更多。",
            options = doroAlternatives,
            selectedIndex = selectedDoroAlternative,
            onSelect = onDoroAlternativeChange
        )
        
        AlternativeSelector(
            title = "替代塔菲喵：从给主播打赏到给自己投资",
            subtitle = "你真正需要的是被关注的感觉，而这一点，培养自己的爱好可以给你更多。",
            options = taffyAlternatives,
            selectedIndex = selectedTaffyAlternative,
            onSelect = onTaffyAlternativeChange
        )
        
        AlternativeSelector(
            title = "替代短视频：从刷别人的生活到过自己的生活",
            subtitle = "你真正需要的是确定感和掌控感，而这一点，完成微小的小事可以给你更多。",
            options = shortVideoAlternatives,
            selectedIndex = selectedShortVideoAlternative,
            onSelect = onShortVideoAlternativeChange
        )
    }
}

@Composable
fun AlternativeSelector(
    title: String,
    subtitle: String,
    options: List<AlternativeOption>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3498DB)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                subtitle,
                fontSize = 14.sp,
                color = Color(0xFF555555)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                options.forEachIndexed { index, option ->
                    AlternativeCard(
                        option = option,
                        isSelected = index == selectedIndex,
                        onClick = { onSelect(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun AlternativeCard(
    option: AlternativeOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = if (isSelected) CardDefaults.cardElevation(defaultElevation = 4.dp) else CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0x153498DB) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(option.icon, fontSize = 28.sp)

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    option.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoChip("💰", option.cost)
                    InfoChip("⏱️", option.timePerDay)
                    InfoChip("⭐", option.difficulty)
                }
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF6B7280)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "数值 ${option.valueDensity}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun InfoChip(icon: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 14.sp)
        Text(
            text,
            fontSize = 12.sp,
            color = Color(0xFF777777)
        )
    }
}

@Composable
fun GrowthContent() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🌱", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "成长期：90-180天",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3498DB)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "每天完成3件微小但确定的小事。建立稳定的生活习惯，重新获得对生活的掌控感。",
                fontSize = 15.sp,
                color = Color(0xFF555555),
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            DailyTaskList()
        }
    }
}

@Composable
fun DailyTaskList() {
    val tasks = listOf(
        "整理一个抽屉",
        "擦一遍桌子",
        "走10分钟路",
        "读10页书",
        "做一顿好吃的饭",
        "给家人打个电话",
        "做5个俯卧撑"
    )
    
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0x0AF39C12)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "🎯 今日小事选择",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF39C12)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            tasks.take(3).forEach { task ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF27AE60)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        task,
                        fontSize = 14.sp,
                        color = Color(0xFF333333)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "每完成一件小事，获得5点觉醒值，2点幸福度。",
                fontSize = 13.sp,
                color = Color(0xFF777777)
            )
        }
    }
}

@Composable
fun AwakeningContent() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0x10F1C40F)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("✨", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "觉醒期：180天以上",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF1C40F)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "找到自己真正热爱的事情，实现自我价值。这时候，虚拟世界对你来说，只是娱乐，不再是逃避。",
                fontSize = 15.sp,
                color = Color(0xFF555555),
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoBox(
                icon = "🎨",
                title = "找到你的热爱",
                content = "绘画、写作、编程、手工…什么都可以。关键是，这件事本身就让你快乐，不需要任何额外的奖励。"
            )
        }
    }
}

@Composable
fun SoloAestheticPrinciplesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🎨", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "独居审美三原则",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3498DB)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            AestheticPrincipleItem(
                number = "1",
                title = "10件物品原则",
                description = "只留下真正能让你开心的东西。你不需要100件衣服，你只需要10件真正喜欢的。"
            )
            
            AestheticPrincipleItem(
                number = "2",
                title = "100元改造原则",
                description = "用最少的钱，获得最大的幸福感。换个门把手，买块桌布，就能让家变得不一样。"
            )
            
            AestheticPrincipleItem(
                number = "3",
                title = "全生命周期友好原则",
                description = "现在舒服，老了也舒服。不要有门槛，不要有尖锐边角，一切为了长期考虑。"
            )
        }
    }
}

@Composable
fun AestheticPrincipleItem(number: String, title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0x083498DB)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF3498DB)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    number,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column {
                Text(
                    title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    description,
                    fontSize = 13.sp,
                    color = Color(0xFF555555),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun RealLifeExamplesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0x0827AE60)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🌟", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "真实的例子",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF27AE60)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            InfoBox(
                icon = "🐱",
                title = "喂流浪猫的故事",
                content = "每天下班，都会去喂楼下的流浪猫。现在，它们看到我就会过来蹭我的腿。这种被需要的感觉，比什么都真实。"
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoBox(
                icon = "🌱",
                title = "养绿萝的故事",
                content = "从一盆绿萝开始，现在家里有了10盆。看着它们长出新叶子，那种成就感是刷短视频永远给不了的。"
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoBox(
                icon = "✍️",
                title = "练字的故事",
                content = "从小就想把字写好，现在终于开始了。每天15分钟，字越来越好看，心情也越来越平静。"
            )
        }
    }
}

@Composable
fun InfoBox(icon: String, title: String, content: String) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Text(icon, fontSize = 24.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                content,
                fontSize = 14.sp,
                color = Color(0xFF555555),
                lineHeight = 21.sp
            )
        }
    }
}
