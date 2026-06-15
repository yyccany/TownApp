package com.example.townapp.ui.cognition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.theme.AppDimens

data class HarmfulPhrase(
    val id: Int,
    val phrase: String,
    val context: String,
    val impact: String,
    val alternative: String,
    val reflection: String
)

private fun generateHarmfulPhrases(): List<HarmfulPhrase> {
    return listOf(
        HarmfulPhrase(
            id = 1,
            phrase = "我都是为了你好",
            context = "父母在干涉孩子选择时常用的理由",
            impact = "让孩子感到愧疚，难以建立健康的个人边界",
            alternative = "我担心你，所以想和你聊聊我的看法",
            reflection = "爱不是控制的借口"
        ),
        HarmfulPhrase(
            id = 2,
            phrase = "你怎么这么不听话",
            context = "孩子表达不同意见时",
            impact = "让孩子觉得表达自己的想法是错误的",
            alternative = "我理解你的想法，可以和我说说吗",
            reflection = "听话不是衡量好孩子的标准"
        ),
        HarmfulPhrase(
            id = 3,
            phrase = "这点小事都做不好",
            context = "孩子犯错或做事达不到预期时",
            impact = "摧毁孩子的自信心，形成\"我不行\"的信念",
            alternative = "这次没做好没关系，我们一起看看怎么改进",
            reflection = "错误是学习的机会，不是否定的理由"
        ),
        HarmfulPhrase(
            id = 4,
            phrase = "你看看别人家的孩子",
            context = "父母拿孩子和同龄人比较时",
            impact = "让孩子产生强烈的自卑感和竞争焦虑",
            alternative = "每个人都有自己的节奏，你也有你的优点",
            reflection = "比较是偷走快乐的小偷"
        ),
        HarmfulPhrase(
            id = 5,
            phrase = "等你长大了就懂了",
            context = "孩子追问某些事情的原因时",
            impact = "让孩子感到不被尊重，关闭沟通的通道",
            alternative = "这个问题很好，我们一起探讨一下",
            reflection = "孩子的好奇心值得被认真对待"
        ),
        HarmfulPhrase(
            id = 6,
            phrase = "我们家条件不好，你要懂事",
            context = "父母向孩子强调家庭经济状况时",
            impact = "让孩子过早背负不属于自己的压力和愧疚",
            alternative = "我们现在这样也很好，你只管安心成长",
            reflection = "家庭的责任是父母的，不是孩子的"
        ),
        HarmfulPhrase(
            id = 7,
            phrase = "女孩子读那么多书没用",
            context = "谈论教育投资时",
            impact = "用性别刻板印象限制孩子的发展",
            alternative = "读书是为了让你有更多选择的权利",
            reflection = "性别不该成为限制可能性的理由"
        ),
        HarmfulPhrase(
            id = 8,
            phrase = "我为你放弃了多少",
            context = "父母表达牺牲感时",
            impact = "让孩子产生沉重的愧疚感，难以独立",
            alternative = "我的选择是我自己的决定，你不用有负担",
            reflection = "父母的付出是爱的表达，不是索取的筹码"
        ),
        HarmfulPhrase(
            id = 9,
            phrase = "这点苦都吃不了",
            context = "孩子遇到困难想放弃时",
            impact = "否定孩子的感受，让孩子不敢表达脆弱",
            alternative = "确实很难，我们可以休息一下再试试",
            reflection = "承认困难不是懦弱，是勇气"
        ),
        HarmfulPhrase(
            id = 10,
            phrase = "你太让我失望了",
            context = "孩子未能达到父母期望时",
            impact = "让孩子觉得自己不被爱，形成讨好型人格",
            alternative = "我对你有期待，但更重要的是你开心",
            reflection = "爱应该是无条件的"
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntergenerationalHarmScreen(
    onBack: () -> Unit
) {
    val phrases by remember { mutableStateOf(generateHarmfulPhrases()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🫂 代际语言伤害") },
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
                        Text("🫂", fontSize = 40.sp)
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            "代际语言伤害",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A3728)
                        )
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            "看见语言背后的模式，寻找新的沟通方式",
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
                            "语言是有力量的，尤其是来自亲人的语言。有些话在说者看来是关心，" +
                            "但在听者心里可能留下很深的印记。这不意味着谁对谁错，" +
                            "而是提醒我们，沟通可以有更温柔的方式。",
                            fontSize = 13.sp,
                            color = Color(0xFF666666),
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            itemsIndexed(phrases) { _, item ->
                HarmPhraseCard(item = item)
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimens.radiusMedium),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "🌱 和解之道",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF666666)
                        )
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        Text(
                            "看见这些伤害，不是为了指责谁，而是为了更好地理解自己。" +
                            "父母也是带着他们的成长印记在生活。当你觉察到这些模式，" +
                            "你就已经在创造新的可能。",
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
fun HarmPhraseCard(item: HarmfulPhrase) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFE0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("💬", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                Text(
                    text = "\"${item.phrase}\"",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A3728)
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            Column(
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
            ) {
                Text(
                    text = "• 常见场景：${item.context}",
                    fontSize = 12.sp,
                    color = Color(0xFF888888)
                )
                
                Text(
                    text = "• 可能影响：${item.impact}",
                    fontSize = 12.sp,
                    color = Color(0xFFE57373)
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E9))
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("💡 ", fontSize = 14.sp)
                    Text(
                        text = "替代表达：${item.alternative}",
                        fontSize = 12.sp,
                        color = Color(0xFF388E3C),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF0E0))
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "\"${item.reflection}\"",
                    fontSize = 12.sp,
                    color = Color(0xFF4A3728),
                    fontWeight = FontWeight.Medium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}