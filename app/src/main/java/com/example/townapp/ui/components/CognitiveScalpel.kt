package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AnalysisStep(
    val title: String,
    val description: String,
    val questions: List<String>
)

data class CaseStudy(
    val name: String,
    val icon: String,
    val illusion: String,
    val cost: String,
    val alternative: String,
    val era: String,
    val core: String,
    val conclusion: String
)

@Composable
fun CognitiveScalpel() {
    val analysisSteps = listOf(
        AnalysisStep(
            "第一步：先破幻觉",
            "它被包装出来的\"好处\"，是真的吗？",
            listOf(
                "这个好处，是真的吗？有没有科学证据？",
                "这个好处，是我真正需要的吗？还是别人强加给我的？",
                "这个好处，有没有可能是为了卖我东西编出来的？"
            )
        ),
        AnalysisStep(
            "第二步：再算成本",
            "所有的成本，都要算进去，不要只看显性成本",
            listOf(
                "金钱成本：买它要花多少钱？维护它要花多少钱？",
                "时间成本：你要花多少时间在它身上？",
                "健康成本：它会对你的身体造成什么伤害？",
                "精神成本：它会对你的精神造成什么伤害？",
                "机会成本：你花在它身上的钱和时间，本来可以用来做什么？"
            )
        ),
        AnalysisStep(
            "第三步：再比价值",
            "有没有更好的替代品？",
            listOf(
                "有没有更便宜的方法，能达到同样的效果？",
                "有没有更高效的方法，能节省我的时间？",
                "有没有更健康的方法，能减少对身体的伤害？"
            )
        ),
        AnalysisStep(
            "第四步：再看时代",
            "它是哪个时代的产物？",
            listOf(
                "它是为了解决哪个时代的问题而产生的？",
                "那个问题，今天还存在吗？",
                "今天的环境，和那个时代的环境，一样吗？"
            )
        ),
        AnalysisStep(
            "第五步：最后看内核",
            "我们要传承的，是内核，还是形式？",
            listOf(
                "这件事的内核是什么？",
                "它的形式，是唯一能表达这个内核的方式吗？",
                "有没有更好的形式，能更好地表达这个内核？"
            )
        )
    )

    val caseStudies = listOf(
        CaseStudy(
            "淄博烧烤",
            "🔥",
            "淄博烧烤好吃、便宜、有烟火气？",
            "你花了几千块钱，坐了十几个小时的火车，去排队吃一顿烧烤。",
            "同样的钱，你可以去吃家附近的烧烤，还能省下来时间和钱，去旅游、去学习。",
            "这是疫情之后，大家对真诚的集体渴望，是暂时的。",
            "我们要的是真诚，不是烧烤。",
            "淄博烧烤是一个情绪的出口，不是什么值得追求的美食。"
        ),
        CaseStudy(
            "特种兵旅游",
            "🎒",
            "年轻人要在 3 天玩 10 个城市，这是活力、是青春？",
            "你花了几千块钱，每天睡 4 小时，赶车赶景点，最后累得要死，什么都没记住。",
            "同样的钱，你可以花一周时间，好好玩一个地方，深度体验，而不是走马观花。",
            "这是内卷时代的产物，年轻人平时没有时间旅游，只能用这种方式。",
            "我们要的是看世界，不是打卡。",
            "特种兵旅游是没钱没时间的无奈，不是什么值得炫耀的事。"
        ),
        CaseStudy(
            "ChatGPT",
            "🤖",
            "ChatGPT 是人工智能，能取代人类，能做所有事？",
            "每个月 20 美元，每天用 1 小时，成本很低。",
            "它能帮你节省大量的时间，让你把时间花在更重要的事情上。",
            "这是 AI 时代的产物，是未来的趋势。",
            "它能帮我们解放生产力，让我们有更多的时间去做更有意义的事。",
            "ChatGPT 是真正的高价值工具，是我们应该拥抱的东西。"
        ),
        CaseStudy(
            "AI 绘画",
            "🎨",
            "AI 绘画能取代画家，能创造艺术？",
            "每个月几十块钱，成本很低。",
            "它能帮你节省大量的时间，让你把时间花在创意上。",
            "这是 AI 时代的产物，是未来的趋势。",
            "它能帮我们解放创造力，让我们有更多的时间去创造。",
            "AI 绘画也是真正的高价值工具，是我们应该拥抱的东西。"
        ),
        CaseStudy(
            "随份子",
            "💰",
            "随份子是传统，是人情世故，是维系关系的必要手段？",
            "每年随份子5000元，参加10次酒席，每次4小时。年总成本296小时，相当于37个工作日。",
            "真正的朋友有困难直接给钱，不说「这是份子钱」。普通朋友结婚，送你亲手做的礼物，或者一本书。",
            "随份子诞生于农业社会的匮乏时代，是民间互助保险。现在我们有银行、有保险、有社会保障。",
            "我们要的是互相帮助，不是随份子。",
            "随份子是匮乏时代的遗留物，现在已经变成了攀比工具和人情高利贷。"
        ),
        CaseStudy(
            "春节走亲戚",
            "🏮",
            "春节走亲戚是传统，是孝顺，是家族团结的体现？",
            "春节走亲戚7天，每天8小时，花2000元买礼物和红包。年总成本294小时，相当于37个工作日。",
            "平时多和你真正关心的亲戚视频聊天，多打电话。春节只去看你真正想见的人。组织一次家庭旅行。",
            "春节走亲戚诞生于交通和通讯极度不发达的年代。现在我们有电话、有微信、有高铁、有飞机。",
            "我们要的是关心家人，不是走形式。",
            "春节走亲戚现在已经变成了年度拷问大会和攀比大会。走形式的亲戚，不如不见。"
        ),
        CaseStudy(
            "囤积癖",
            "📦",
            "这东西以后可能用得上，扔了可惜，勤俭节约是美德？",
            "家里囤积了500件没用的东西，占用10平米空间。空间价值20万元，相当于20万买了个仓库。",
            "践行断舍离：任何东西一年没用过就扔掉或捐掉。买东西只买现在需要的，不买「以后可能会用到」的。",
            "囤积癖诞生于饥荒和物资极度匮乏的年代。老一辈经历过三年自然灾害，刻在骨子里的信念是「手里有粮，心里不慌」。现在我们物质过剩，超市里什么都有。",
            "我们要的是勤俭节约，不是囤积没用的东西。",
            "囤积癖现在已经变成了生活负担。你花几百万买的房子，一半的空间都用来堆垃圾。"
        ),
        CaseStudy(
            "吃剩饭剩菜",
            "🍛",
            "剩饭剩菜扔了可惜，勤俭节约是美德？",
            "每天吃剩饭剩菜，每年食物中毒1次。看病花费2000元，请假休息3天。年总成本约500小时。",
            "做饭少做一点，刚好够吃就行。真的吃不完就直接倒掉。浪费几块钱的饭菜，总比花几千块钱看病强。",
            "吃剩饭剩菜诞生于粮食匮乏的年代。以前粮食不够吃，能吃上饭就已经很不错了。现在我们粮食充足，根本不愁吃。",
            "我们要的是珍惜粮食，不是把变质的东西吃进肚子里。",
            "吃剩饭剩菜现在已经变成了健康杀手。吃变质的东西导致肠胃炎、食物中毒，甚至致癌。长期吃撑导致肥胖、高血压、糖尿病。"
        ),
        CaseStudy(
            "养儿防老",
            "👨‍👩‍👧",
            "养儿防老是传统，是孝道，是养老的唯一保障？",
            "为了养儿防老，生2个孩子，花200万养大。总成本约20万小时，相当于68年。",
            "为自己的养老做准备：存钱、交社保、买商业保险。老了之后，去养老院或请护工，不要给孩子添麻烦。生孩子是因为你喜欢孩子，想体验做父母的快乐。",
            "养儿防老诞生于没有社会保障的农业社会。以前没有社保，没有养老金，没有养老院。人老了，干不动活了，只能靠儿子养活。现在我们有社保，有养老金，有养老院，有各种养老服务。",
            "我们要的是孝顺父母，照顾老人，不是把孩子当成养老工具。",
            "养儿防老现在已经变成了很多家庭矛盾的根源。重男轻女，导致男女比例失调。父母把所有的希望都寄托在儿子身上，给儿子巨大压力。婆媳矛盾、姑嫂矛盾、兄弟矛盾，几乎都是因为养老和财产。"
        )
    )

    var currentStep by remember { mutableStateOf(0) }
    var analysisTarget by remember { mutableStateOf("") }
    var analysisAnswers by remember { mutableStateOf(List(5) { "" }) }
    var showCaseStudies by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(AppDimens.paddingLarge)
    ) {
        // 标题
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimens.radiusLarge),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5).copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
                ) {
                    Text("🔪", fontSize = 32.sp)
                    Text(
                        "认知手术刀",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    "学会这五步，你自己就能批判一切",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "万物薪俸小镇，欢迎你。\n你的生命，是你唯一真正拥有的东西。\n不要让任何人，偷走它。",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 1.8.sp
                )
            }
        }

        // 开始新分析按钮
        Button(
            onClick = {
                showCaseStudies = false
                currentStep = 0
                analysisTarget = ""
                analysisAnswers = List(5) { "" }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimens.radiusMedium)
        ) {
            Text("🔍 开始新分析", fontSize = 16.sp)
        }

        // 查看案例按钮
        Button(
            onClick = { showCaseStudies = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimens.radiusMedium),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("📚 查看实战案例", fontSize = 16.sp)
        }

        if (showCaseStudies) {
            CaseStudiesSection(caseStudies)
        } else {
            AnalysisSection(
                analysisSteps = analysisSteps,
                currentStep = currentStep,
                analysisTarget = analysisTarget,
                analysisAnswers = analysisAnswers,
                onStepChange = { currentStep = it },
                onTargetChange = { analysisTarget = it },
                onAnswerChange = { index, answer ->
                    analysisAnswers = analysisAnswers.toMutableList().also { it[index] = answer }
                }
            )
        }
    }
}

@Composable
fun CaseStudiesSection(caseStudies: List<CaseStudy>) {
    Column(verticalArrangement = Arrangement.spacedBy(AppDimens.paddingLarge)) {
        Text(
            "实战演练",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        caseStudies.forEach { case ->
            CaseStudyCard(case)
        }
    }
}

@Composable
fun CaseStudyCard(case: CaseStudy) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusLarge)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
            ) {
                Text(case.icon, fontSize = 28.sp)
                Text(
                    case.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            AnalysisRow("第一步：破幻觉", case.illusion)
            AnalysisRow("第二步：算成本", case.cost)
            AnalysisRow("第三步：比价值", case.alternative)
            AnalysisRow("第四步：看时代", case.era)
            AnalysisRow("第五步：看内核", case.core)

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (case.conclusion.contains("应该拥抱")) 
                        Color(0xFF4CAF50).copy(alpha = 0.1f) 
                    else 
                        Color(0xFFFF9800).copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(AppDimens.radiusMedium)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        "结论",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (case.conclusion.contains("应该拥抱")) 
                            Color(0xFF4CAF50) 
                        else 
                            Color(0xFFFF9800)
                    )
                    Text(
                        case.conclusion,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 1.6.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AnalysisRow(label: String, content: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            content,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 1.6.sp
        )
    }
}

@Composable
fun AnalysisSection(
    analysisSteps: List<AnalysisStep>,
    currentStep: Int,
    analysisTarget: String,
    analysisAnswers: List<String>,
    onStepChange: (Int) -> Unit,
    onTargetChange: (String) -> Unit,
    onAnswerChange: (Int, String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(AppDimens.paddingLarge)) {
        // 分析目标输入
        if (analysisTarget.isEmpty()) {
            OutlinedTextField(
                value = analysisTarget,
                onValueChange = onTargetChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("你想分析什么？") },
                placeholder = { Text("例如：买新手机、学钢琴、参加培训...") },
                shape = RoundedCornerShape(AppDimens.radiusMedium)
            )
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)),
                shape = RoundedCornerShape(AppDimens.radiusMedium)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
                ) {
                    Text("🎯", fontSize = 20.sp)
                    Text(
                        "分析目标：$analysisTarget",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { onTargetChange("") }) {
                        Text("重新选择")
                    }
                }
            }

            // 步骤指示器
            StepIndicator(
                currentStep = currentStep,
                totalSteps = 5,
                stepTitles = analysisSteps.map { it.title }
            )

            // 当前步骤内容
            if (currentStep < 5) {
                StepContent(
                    step = analysisSteps[currentStep],
                    stepIndex = currentStep,
                    answer = analysisAnswers[currentStep],
                    onAnswerChange = { onAnswerChange(currentStep, it) }
                )

                // 导航按钮
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
                ) {
                    if (currentStep > 0) {
                        Button(
                            onClick = { onStepChange(currentStep - 1) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(AppDimens.radiusMedium)
                        ) {
                            Text("← 上一步")
                        }
                    }
                    Button(
                        onClick = {
                            if (currentStep < 4) {
                                onStepChange(currentStep + 1)
                            } else {
                                onStepChange(5) // 显示结果
                            }
                        },
                        modifier = if (currentStep > 0) Modifier.weight(1f) else Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(AppDimens.radiusMedium)
                    ) {
                        Text(if (currentStep < 4) "下一步 →" else "✅ 生成分析结果")
                    }
                }
            } else {
                AnalysisResult(
                    target = analysisTarget,
                    answers = analysisAnswers
                )
            }
        }
    }
}

@Composable
fun StepIndicator(
    currentStep: Int,
    totalSteps: Int,
    stepTitles: List<String>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
    ) {
        repeat(totalSteps) { index ->
            val isActive = index == currentStep
            val isCompleted = index < currentStep

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCompleted) 
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) 
                    else if (isActive) 
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) 
                    else 
                        MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(AppDimens.radiusSmall)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        if (isCompleted) "✓" else "${index + 1}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCompleted || isActive) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        stepTitles[index].split("：")[0],
                        fontSize = 10.sp,
                        color = if (isCompleted || isActive) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun StepContent(
    step: AnalysisStep,
    stepIndex: Int,
    answer: String,
    onAnswerChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5).copy(alpha = 0.05f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
        ) {
            Text(
                step.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                step.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            Text(
                "思考问题：",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )

            step.questions.forEach { question ->
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
                ) {
                    Text("•", fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
                    Text(
                        question,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 1.6.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingMedium))

            OutlinedTextField(
                value = answer,
                onValueChange = onAnswerChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("写下你的思考...") },
                placeholder = { Text("把你的想法写下来，这是元认知的开始") },
                minLines = 4,
                maxLines = 8,
                shape = RoundedCornerShape(AppDimens.radiusMedium)
            )
        }
    }
}

private val STEP_NUMBERS = listOf("第一", "第二", "第三", "第四", "第五", "第六", "第七", "第八", "第九", "第十")

@Composable
fun AnalysisResult(target: String, answers: List<String>) {
    val stepTitles = listOf("破幻觉", "算成本", "比价值", "看时代", "看内核")

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.paddingLarge)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
            ) {
                Text("🎉", fontSize = 28.sp)
                Text(
                    "分析完成！",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }

            Text(
                "你对「$target」的分析：",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            answers.forEachIndexed { index, answer ->
                if (answer.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(AppDimens.radiusMedium)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                "${STEP_NUMBERS.getOrElse(index) { "第${index + 1}" }}步：${stepTitles[index]}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                answer,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 1.6.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700).copy(alpha = 0.1f)),
                shape = RoundedCornerShape(AppDimens.radiusMedium)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
                ) {
                    Text(
                        "✨ 恭喜你",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF8F00)
                    )
                    Text(
                        "你已经完成了一次完整的元批判！继续用这个框架去分析更多事物，你会发现，世界在你眼中变得越来越清晰。",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 1.8.sp
                    )
                }
            }
        }
    }
}
