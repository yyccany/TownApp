package com.example.townapp.ui.screens

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.animation.core.*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.townapp.ui.viewmodel.TownViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

/**
 * 居民页面 —— 小镇的核心：爱、自由、实事求是
 * 
 * 设计理念：
 * 1. 爱：三个小家伙永远陪伴，无条件支持
 * 2. 自由：用户自由选择记录什么，没有催促感
 * 3. 实事求是：真实的记录，真实的反馈
 * 
 * 页面结构：
 * - 一体化设计，不切分Tab
 * - 温柔的米黄色基调
 * - 低饱和度按钮，无催促感
 * - 两级选择器，无需打字
 * - 小家伙语录反馈，温暖陪伴
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResidentScreen(
    viewModel: TownViewModel,
    onBack: () -> Unit
) {
    // ============================================
    // 三个小家伙 —— 小镇的灵魂
    // ============================================
    val companions = remember {
        listOf(
            Companion(
                name = "塔菲喵",
                emoji = "😺",
                personality = "活泼可爱，陪你吃每一口",
                lightColor = Color(0xFFFFE4EC),  // 柔和粉色
                accentColor = Color(0xFFFFB6C1),
                quotes = CompanionQuotes(
                    foodHappy = listOf("喵~ 这个好好吃！", "你今天吃得好满足呢~", "塔菲喵也想尝尝！"),
                    foodHealthy = listOf("健康的选择！塔菲喵为你开心~", "你真的很关心自己呢~"),
                    foodSweet = listOf("甜甜的~ 心情变好了喵！", "偶尔放纵一下也没关系~"),
                    clothing = listOf("这件好漂亮！", "你穿起来真好看~", "塔菲喵也想穿！"),
                    daily = listOf("你做了好多事！", "今天很充实呢~", "塔菲喵陪着你！"),
                    comfort = listOf("没关系，塔菲喵在~", "休息一下也没关系~", "你已经很棒了！")
                )
            ),
            Companion(
                name = "doro",
                emoji = "🥺",
                personality = "温柔治愈，接住你的情绪",
                lightColor = Color(0xFFE4F0FF),  // 柔和蓝色
                accentColor = Color(0xFFB4D4FF),
                quotes = CompanionQuotes(
                    foodHappy = listOf("🥺 你吃了这个，心情好一点了吗？", "好好照顾自己哦~"),
                    foodHealthy = listOf("健康是最好的礼物~ doro很开心", "你在爱自己呢~"),
                    foodSweet = listOf("甜甜的东西，会让心也变甜~", "偶尔需要一点甜~"),
                    clothing = listOf("这件很适合你~", "暖暖的感觉~", "doro觉得你很美~"),
                    daily = listOf("辛苦了~ doro一直在", "你做的事情很有意义~", "休息一下吧~"),
                    comfort = listOf("🥺 doro在这里，不会离开", "不管怎样，你值得被爱", "慢慢来，不着急~")
                )
            ),
            Companion(
                name = "咕咕嘎嘎",
                emoji = "🐧",
                personality = "羡慕呆萌，陪你做每一件小事",
                lightColor = Color(0xFFE4FFE8),  // 柔和绿色
                accentColor = Color(0xFFB4FFD4),
                quotes = CompanionQuotes(
                    foodHappy = listOf("咕咕~ 好好吃！", "羡慕~ 咕咕也想吃！", "你吃得真香~"),
                    foodHealthy = listOf("咕咕~ 健康真好！", "你很棒~ 咕咕羡慕~", "好好照顾自己~"),
                    foodSweet = listOf("咕咕~ 甜甜的！", "羡慕~ 咕咕也想尝~", "开心就好~"),
                    clothing = listOf("咕咕~ 好酷！", "羡慕~ 咕咕也想要！", "你真好看~"),
                    daily = listOf("咕咕~ 你做了好多！", "羡慕~ 咕咕也想做！", "你真棒~"),
                    comfort = listOf("咕咕~ 没关系~", "咕咕陪你~", "慢慢来~")
                )
            )
        )
    }

    // ============================================
    // 状态管理
    // ============================================
    
    // 记录数据
    val foodRecords = remember { mutableStateListOf<Record>() }
    val clothingRecords = remember { mutableStateListOf<Record>() }
    val dailyRecords = remember { mutableStateListOf<Record>() }
    
    // 弹窗状态
    var showFoodDialog by remember { mutableStateOf(false) }
    var showClothingDialog by remember { mutableStateOf(false) }
    var showDailyDialog by remember { mutableStateOf(false) }
    
    // 小家伙反应状态
    var showReaction by remember { mutableStateOf(false) }
    var currentCompanionIndex by remember { mutableIntStateOf(0) }
    var reactionText by remember { mutableStateOf("") }
    var reactionType by remember { mutableStateOf("food") }

    // ============================================
    // 主页面布局
    // ============================================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F5F0))  // 温柔的米黄色
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 顶部导航 - 简洁
            TopAppBar(
                title = { 
                    Text(
                        "居民",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF5A4A3A)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回",
                            tint = Color(0xFF5A4A3A)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            // 主内容 - 一体化滚动
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 8.dp,
                    bottom = 100.dp  // 为底部安全区域留空间
                ),
                verticalArrangement = Arrangement.spacedBy(AppDimens.paddingXLarge)
            ) {
                // ============================================
                // 开场语 —— 温暖的邀请
                // ============================================
                item {
                    Text(
                        "今天发生了什么？\n三个小家伙想听听你的故事 💛",
                        fontSize = 16.sp,
                        color = Color(0xFF6B5B4F),
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                // ============================================
                // 记录入口 —— 自由选择，无催促
                // ============================================
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingMedium)
                    ) {
                        // 吃了什么 - 塔菲喵的颜色
                        RecordEntryCard(
                            icon = "🍽️",
                            title = "吃了什么",
                            subtitle = "塔菲喵陪你",
                            backgroundColor = Color(0xFFFFF8F5),  // 淡粉米色
                            borderColor = Color(0xFFFFE4EC),
                            onClick = { showFoodDialog = true },
                            modifier = Modifier.weight(1f)
                        )
                        
                        // 穿了什么 - doro的颜色
                        RecordEntryCard(
                            icon = "👕",
                            title = "穿了什么",
                            subtitle = "doro陪你",
                            backgroundColor = Color(0xFFF5F8FF),  // 淡蓝米色
                            borderColor = Color(0xFFE4F0FF),
                            onClick = { showClothingDialog = true },
                            modifier = Modifier.weight(1f)
                        )
                        
                        // 做了什么 - 咕咕的颜色
                        RecordEntryCard(
                            icon = "📋",
                            title = "做了什么",
                            subtitle = "咕咕陪你",
                            backgroundColor = Color(0xFFF5FFF8),  // 淡绿米色
                            borderColor = Color(0xFFE4FFE8),
                            onClick = { showDailyDialog = true },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // ============================================
                // 今日记录 —— 实事求是
                // ============================================
                
                // 饮食记录
                if (foodRecords.isNotEmpty()) {
                    item {
                        SectionHeader(
                            icon = "🍽️",
                            title = "今日饮食",
                            count = foodRecords.size,
                            companionEmoji = "😺"
                        )
                    }
                    items(foodRecords) { record ->
                        RecordItemCard(
                            record = record,
                            companionColor = Color(0xFFFFE4EC),
                            onRemove = { foodRecords.remove(record) }
                        )
                    }
                }

                // 穿戴记录
                if (clothingRecords.isNotEmpty()) {
                    item {
                        SectionHeader(
                            icon = "👕",
                            title = "今日穿戴",
                            count = clothingRecords.size,
                            companionEmoji = "🥺"
                        )
                    }
                    items(clothingRecords) { record ->
                        RecordItemCard(
                            record = record,
                            companionColor = Color(0xFFE4F0FF),
                            onRemove = { clothingRecords.remove(record) }
                        )
                    }
                }

                // 日常记录
                if (dailyRecords.isNotEmpty()) {
                    item {
                        SectionHeader(
                            icon = "📋",
                            title = "日常小事",
                            count = dailyRecords.size,
                            companionEmoji = "🐧"
                        )
                    }
                    items(dailyRecords) { record ->
                        RecordItemCard(
                            record = record,
                            companionColor = Color(0xFFE4FFE8),
                            onRemove = { dailyRecords.remove(record) }
                        )
                    }
                }

                // 如果没有任何记录，显示鼓励语
                if (foodRecords.isEmpty() && clothingRecords.isEmpty() && dailyRecords.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFFBF5)
                            ),
                            shape = RoundedCornerShape(AppDimens.radiusLarge),
                            border = BorderStroke(1.dp, Color(0xFFF0E6D8))
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("还没有记录呢~", fontSize = 14.sp, color = Color(0xFF8B7B6F))
                                Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                                Text(
                                    "不用急着记录什么\n三个小家伙只是想陪着你",
                                    fontSize = 13.sp,
                                    color = Color(0xFFA09080),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }

                // ============================================
                // 三个小家伙 —— 永远的陪伴
                // ============================================
                item {
                    Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                    Text(
                        "💛 三个小家伙",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5A4A3A)
                    )
                }

                items(companions) { companion ->
                    CompanionCard(companion = companion)
                }

                // ============================================
                // 底部温暖语
                // ============================================
                item {
                    Spacer(modifier = Modifier.height(AppDimens.paddingXXLarge))
                    Text(
                        "不管你做了什么，没做什么\n三个小家伙都会陪着你\n你值得被爱，不是因为做了什么，而是因为你就是你",
                        fontSize = 13.sp,
                        color = Color(0xFF9A8A7A),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }

        // ============================================
        // 小家伙反应气泡 —— 温暖的反馈
        // ============================================
        if (showReaction) {
            CompanionReactionBubble(
                companion = companions[currentCompanionIndex],
                text = reactionText,
                type = reactionType,
                onDismiss = { showReaction = false }
            )
        }
    }

    // ============================================
    // 选择弹窗 —— 两级选择，无需打字
    // ============================================
    
    // 饮食选择弹窗
    if (showFoodDialog) {
        FoodSelectionDialog(
            companions = companions,
            onDismiss = { showFoodDialog = false },
            onSelect = { record, companionIndex, quote ->
                foodRecords.add(0, record)
                currentCompanionIndex = companionIndex
                reactionText = quote
                reactionType = "food"
                showReaction = true
                showFoodDialog = false
            }
        )
    }

    // 穿戴选择弹窗
    if (showClothingDialog) {
        ClothingSelectionDialog(
            companions = companions,
            onDismiss = { showClothingDialog = false },
            onSelect = { record, companionIndex, quote ->
                clothingRecords.add(0, record)
                currentCompanionIndex = companionIndex
                reactionText = quote
                reactionType = "clothing"
                showReaction = true
                showClothingDialog = false
            }
        )
    }

    // 日常选择弹窗
    if (showDailyDialog) {
        DailySelectionDialog(
            companions = companions,
            onDismiss = { showDailyDialog = false },
            onSelect = { record, companionIndex, quote ->
                dailyRecords.add(0, record)
                currentCompanionIndex = companionIndex
                reactionText = quote
                reactionType = "daily"
                showReaction = true
                showDailyDialog = false
            }
        )
    }
}

// ============================================
// 数据类
// ============================================

data class Companion(
    val name: String,
    val emoji: String,
    val personality: String,
    val lightColor: Color,
    val accentColor: Color,
    val quotes: CompanionQuotes
)

data class CompanionQuotes(
    val foodHappy: List<String>,
    val foodHealthy: List<String>,
    val foodSweet: List<String>,
    val clothing: List<String>,
    val daily: List<String>,
    val comfort: List<String>
)

data class Record(
    val time: String,
    val name: String,
    val emoji: String,
    val detail: String,
    val category: String
)

// ============================================
// 组件
// ============================================

/**
 * 记录入口卡片 - 低饱和度，温柔邀请
 */
@Composable
fun RecordEntryCard(
    icon: String,
    title: String,
    subtitle: String,
    backgroundColor: Color,
    borderColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = modifier
            .height(90.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        border = BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(icon, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5A4A3A)
            )
            Text(
                subtitle,
                fontSize = 11.sp,
                color = Color(0xFF9A8A7A)
            )
        }
    }
}

/**
 * 分区标题
 */
@Composable
fun SectionHeader(
    icon: String,
    title: String,
    count: Int,
    companionEmoji: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(icon, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5A4A3A)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(companionEmoji, fontSize = 14.sp)
        }
        Text(
            "$count 条",
            fontSize = 12.sp,
            color = Color(0xFF9A8A7A)
        )
    }
}

/**
 * 记录项卡片 - 点击可删除
 */
@Composable
fun RecordItemCard(
    record: Record,
    companionColor: Color,
    onRemove: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) { onRemove() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        border = BorderStroke(1.dp, companionColor.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 小家伙颜色标记
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(companionColor.copy(alpha = 0.6f))
            )
            Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
            
            Text(record.emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(10.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    record.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4A3A2A)
                )
                Text(
                    record.detail,
                    fontSize = 12.sp,
                    color = Color(0xFF9A8A7A)
                )
            }
            
            Text(
                record.time,
                fontSize = 12.sp,
                color = Color(0xFFB0A090)
            )
        }
    }
}

/**
 * 小家伙卡片 - 温暖的陪伴
 */
@Composable
fun CompanionCard(companion: Companion) {
    // 轻微跳动动画
    val infiniteTransition = rememberInfiniteTransition(label = "bounce")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = companion.lightColor.copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(AppDimens.radiusLarge),
        border = BorderStroke(1.dp, companion.accentColor.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 小家伙头像
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                companion.lightColor.copy(alpha = 0.8f),
                                companion.accentColor.copy(alpha = 0.4f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(companion.emoji, fontSize = 26.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    companion.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A3A2A)
                )
                Text(
                    companion.personality,
                    fontSize = 12.sp,
                    color = Color(0xFF7A6A5A)
                )
            }
        }
    }
}

/**
 * 小家伙反应气泡 - 温暖的反馈
 */
@Composable
fun CompanionReactionBubble(
    companion: Companion,
    text: String,
    type: String,
    onDismiss: () -> Unit
) {
    // 动画状态
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
        delay(2500)
        visible = false
        delay(300)
        onDismiss()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { 
                visible = false
                onDismiss()
            }
            .zIndex(10f),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(200)) + slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(200)
            ),
            exit = fadeOut(tween(200)) + slideOutVertically(
                targetOffsetY = { it / 2 },
                animationSpec = tween(200)
            )
        ) {
            Column(
                modifier = Modifier.padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 气泡
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = companion.lightColor.copy(alpha = 0.95f)
                    ),
                    shape = RoundedCornerShape(AppDimens.radiusXLarge)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = AppDimens.paddingXLarge, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(companion.emoji, fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text,
                            fontSize = 14.sp,
                            color = Color(0xFF3A2A1A),
                            lineHeight = 18.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(10.dp))
                
                // 小家伙头像
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(companion.accentColor.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(companion.emoji, fontSize = 24.sp)
                }
            }
        }
    }
}

// ============================================
// 选择弹窗 - 两级选择，无需打字
// ============================================

/**
 * 药物数据类 - 按真实价值密度分类
 */
data class MedicineItem(
    val name: String,
    val emoji: String,
    val category: MedicineCategory,
    val valueDensity: Double,
    val actualContent: String,
    val claimedContent: String
)

enum class MedicineCategory(val displayName: String, val emoji: String, val color: Color) {
    REGULAR_OTC("正规OTC药", "💊", Color(0xFFE4F0FF)),
    PLACEBO("安慰剂类", "🌿", Color(0xFFE4FFE8)),
    SCAM("智商税类", "🍬", Color(0xFFFFE4EC))
}

/**
 * 药物数据库
 */
object MedicineDatabase {
    val allMedicines = listOf(
        // 正规OTC药 - 价值密度1.0
        MedicineItem("布洛芬", "💊", MedicineCategory.REGULAR_OTC, 1.0,
            "有效成分布洛芬200mg，代谢周期2h，肠胃刺激率15%，无溢价",
            "快速止痛，8小时持续，无副作用"),
        MedicineItem("对乙酰氨基酚", "💊", MedicineCategory.REGULAR_OTC, 1.0,
            "有效成分对乙酰氨基酚500mg，肝脏代谢，安全剂量内副作用小",
            "温和退烧，全家适用，不伤肠胃"),
        MedicineItem("达喜", "💊", MedicineCategory.REGULAR_OTC, 1.0,
            "有效成分铝碳酸镁，直接作用于胃部，中和胃酸",
            "快速缓解胃痛，保护胃黏膜"),
        MedicineItem("蒙脱石散", "💊", MedicineCategory.REGULAR_OTC, 1.0,
            "天然矿物质，物理吸附肠道病菌，不被吸收",
            "安全止泻，保护肠道"),
        
        // 安慰剂类 - 价值密度0.3
        MedicineItem("板蓝根", "🌿", MedicineCategory.PLACEBO, 0.3,
            "有效成分板蓝根提取物，无临床证据证明抗病毒",
            "预防感冒，清热解毒，增强抵抗力"),
        MedicineItem("感冒灵", "🌿", MedicineCategory.PLACEBO, 0.3,
            "含少量对乙酰氨基酚+中药成分，主要靠退烧药起效",
            "中西结合，快速缓解感冒症状"),
        MedicineItem("连花清瘟", "🌿", MedicineCategory.PLACEBO, 0.3,
            "中药复方，无高质量临床证据支持抗病毒",
            "广谱抗病毒，治疗新冠特效"),
        MedicineItem("健胃消食片", "🌿", MedicineCategory.PLACEBO, 0.3,
            "山楂麦芽神曲，主要是心理安慰作用",
            "促进消化，胃口大开"),
        
        // 智商税类 - 价值密度0.05
        MedicineItem("抗病毒口服液", "🍬", MedicineCategory.SCAM, 0.05,
            "有效成分蔗糖90%，无抗病毒靶点，溢价900%",
            "99%抑制病毒，增强免疫力，全家适用"),
        MedicineItem("脑白金", "🍬", MedicineCategory.SCAM, 0.05,
            "褪黑素+淀粉，普通保健品",
            "改善睡眠，年轻态，健康品"),
        MedicineItem("酵素", "🍬", MedicineCategory.SCAM, 0.05,
            "蛋白质，口服后被消化酶分解，无减肥效果",
            "排毒养颜，减肥瘦身，调理肠胃"),
        MedicineItem("胶原蛋白", "🍬", MedicineCategory.SCAM, 0.05,
            "大分子蛋白质，无法直接吸收",
            "美容养颜，紧致肌肤，延缓衰老"),
        MedicineItem("葡萄籽提取物", "🍬", MedicineCategory.SCAM, 0.05,
            "花青素，含量低，效果被夸大",
            "抗氧化，美白淡斑，延缓衰老"),
        MedicineItem("阿胶", "🍬", MedicineCategory.SCAM, 0.05,
            "水解胶原蛋白，与猪蹄皮成分类似",
            "补血养颜，滋阴润燥，女性圣品"),
        MedicineItem("铁皮石斛", "🍬", MedicineCategory.SCAM, 0.05,
            "普通植物提取物，无特殊功效",
            "滋阴补肾，增强免疫，仙草之王")
    )
    
    fun getByCategory(category: MedicineCategory): List<MedicineItem> {
        return allMedicines.filter { it.category == category }
    }
    
    fun search(query: String): List<MedicineItem> {
        return allMedicines.filter { it.name.contains(query, ignoreCase = true) }
    }
}

/**
 * 获取药物反应语录
 */
fun getMedicineQuote(companion: Companion, medicine: MedicineItem): String {
    return when (medicine.category) {
        MedicineCategory.REGULAR_OTC -> "这个是好药哦，能帮你缓解症状，好好休息就会好起来的🥺"
        MedicineCategory.PLACEBO -> "这个好像没什么用哦，但是喝了也没关系，小猫咪开心就好喵😺"
        MedicineCategory.SCAM -> "这个其实就是糖水啦，没有宣传的那些作用哦，但是喝了也没关系，开心就好🥺"
    }
}

/**
 * 饮食选择弹窗 - 支持搜索和可滚动列表
 */
@Composable
fun FoodSelectionDialog(
    companions: List<Companion>,
    onDismiss: () -> Unit,
    onSelect: (Record, Int, String) -> Unit
) {
    // 食物分类 - 6大类，高碳水用塔菲喵粉色
    val categories = listOf(
        FoodCategory("高蛋白", "🥩", listOf("鸡蛋", "牛奶", "鸡肉", "鱼肉", "豆腐", "牛肉"), Color(0xFFE4F0FF)),
        FoodCategory("高碳水", "🍞", listOf("米饭", "面条", "面包", "馒头", "土豆", "玉米", "红薯", "燕麦", "包子", "饺子", "馄饨", "油条", "烧饼", "粽子", "汤圆"), Color(0xFFFFE4EC)),
        FoodCategory("蔬果类", "🥦", listOf("苹果", "香蕉", "橙子", "西兰花", "胡萝卜", "番茄", "黄瓜", "西瓜", "草莓", "蓝莓", "菠菜", "生菜", "芹菜", "青椒", "蘑菇"), Color(0xFFE4FFE8)),
        FoodCategory("甜品类", "🍰", listOf("蛋糕", "奶茶", "巧克力", "冰淇淋", "饼干", "糖果", "布丁", "果冻", "蛋挞", "泡芙", "提拉米苏", "芝士蛋糕", "马卡龙"), Color(0xFFFFF0E4)),
        FoodCategory("饮料类", "🥤", listOf("水", "茶", "咖啡", "果汁", "可乐", "红酒", "啤酒", "牛奶", "豆浆", "酸奶", "柠檬水", "苏打水", "椰子水"), Color(0xFFF0E4FF)),
        FoodCategory("药品类", "💊", MedicineDatabase.allMedicines.map { it.name }, Color(0xFFE8F0E4))
    )

    var selectedCategory by remember { mutableStateOf<FoodCategory?>(null) }
    var selectedFood by remember { mutableStateOf<String?>(null) }
    var selectedAmount by remember { mutableIntStateOf(1) } // 0=少量, 1=正常, 2=过量
    var searchQuery by remember { mutableStateOf("") }
    val amounts = listOf("少量", "正常", "过量")

    // 合并所有食物用于搜索
    val allFoods = remember { categories.flatMap { it.items } }
    val filteredFoods = remember(searchQuery) {
        if (searchQuery.isEmpty()) emptyList()
        else allFoods.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = if (selectedCategory?.name == "高碳水") Color(0xFFFFE4EC) else (selectedCategory?.color ?: Color(0xFFFFFBF5)),
        titleContentColor = Color(0xFF4A3A2A),
        textContentColor = Color(0xFF5A4A3A),
        title = {
            Text(
                if (selectedCategory == null) "选择食物类型" 
                else "选择具体食物",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppDimens.paddingSmall)
            ) {
                if (selectedCategory == null) {
                    // 搜索框 - 直接搜索所有食物
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("搜索食物名称...", fontSize = 13.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(AppDimens.radiusMedium),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFE8DCC8),
                            unfocusedBorderColor = Color(0xFFF0E6D8)
                        ),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                    
                    // 如果有搜索结果，显示搜索结果
                    if (searchQuery.isNotEmpty() && filteredFoods.isNotEmpty()) {
                        Text("搜索结果", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5A4A3A))
                        Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                        LazyColumn(
                            modifier = Modifier.height(180.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(filteredFoods) { food ->
                                // 找到食物所属分类
                                val foodCategory = categories.find { it.items.contains(food) }
                                FoodItemCard(
                                    food = food,
                                    isSelected = selectedFood == food,
                                    onClick = {
                                        selectedFood = food
                                        selectedCategory = foodCategory
                                    }
                                )
                            }
                        }
                    } else if (searchQuery.isNotEmpty() && filteredFoods.isEmpty()) {
                        Text("未找到匹配的食物", fontSize = 13.sp, color = Color(0xFF9A8A7A))
                    } else {
                        // 显示分类列表
                        LazyColumn(
                            modifier = Modifier.height(240.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(categories) { category ->
                                CategoryCard(
                                    category = category,
                                    onClick = { selectedCategory = category }
                                )
                            }
                        }
                    }
                } else {
                    // 第二级：选择具体食物
                    // 去掉返回按钮，点击空白处关闭弹窗
                    Text(
                        "${selectedCategory!!.emoji} ${selectedCategory!!.name}",
                        fontSize = 14.sp,
                        color = Color(0xFF6A5A4A)
                    )
                    
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                    
                    // 搜索框
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("搜索食物...", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(AppDimens.radiusSmall),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFE8DCC8),
                            unfocusedBorderColor = Color(0xFFF0E6D8)
                        ),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                    
                    // 食物列表
                    val displayItems = if (searchQuery.isEmpty()) {
                        selectedCategory!!.items
                    } else {
                        selectedCategory!!.items.filter { it.contains(searchQuery, ignoreCase = true) }
                    }
                    
                    LazyColumn(
                        modifier = Modifier.height(160.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(displayItems) { food ->
                            FoodItemCard(
                                food = food,
                                isSelected = selectedFood == food,
                                onClick = { selectedFood = food }
                            )
                        }
                    }
                    
                    // 分量选择
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                    Text("份量", fontSize = 13.sp, color = Color(0xFF7A6A5A))
                    Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        amounts.forEachIndexed { index, amount ->
                            AmountCard(
                                text = amount,
                                isSelected = selectedAmount == index,
                                onClick = { selectedAmount = index }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (selectedCategory != null && selectedFood != null) {
                Button(
                    onClick = {
                        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                        val detail = amounts[selectedAmount]
                        val record = Record(time, selectedFood!!, getFoodEmoji(selectedFood!!), detail, selectedCategory!!.name)
                        
                        // 选择小家伙（轮流）
                        val companionIndex = (0..2).random()
                        val companion = companions[companionIndex]
                        
                        // 获取语录
                        val quote = getFoodQuote(companion, selectedFood!!, selectedCategory!!.name)
                        
                        onSelect(record, companionIndex, quote)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE8DCC8)
                    ),
                    shape = RoundedCornerShape(AppDimens.radiusMedium)
                ) {
                    Text("记录", fontSize = 15.sp, color = Color(0xFF4A3A2A))
                }
            }
        },
        dismissButton = {
            // 去掉取消按钮，符合"没有取消按钮"原则，点击空白处关闭
        }
    )
}

/**
 * 药物详情弹窗 - 显示宣传vs实测对比
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailDialog(
    medicine: MedicineItem,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val backgroundColor = when (medicine.category) {
        MedicineCategory.REGULAR_OTC -> Color(0xFFE4F0FF)
        MedicineCategory.PLACEBO -> Color(0xFFE4FFE8)
        MedicineCategory.SCAM -> Color(0xFFFFE4EC)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = backgroundColor,
        titleContentColor = Color(0xFF4A3A2A),
        textContentColor = Color(0xFF5A4A3A),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(medicine.emoji, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                Text(medicine.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppDimens.paddingSmall)
            ) {
                // 价值密度标签
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(AppDimens.radiusSmall))
                            .background(Color(0xFFD4C4B0))
                            .padding(horizontal = AppDimens.paddingMedium, vertical = 4.dp)
                    ) {
                        Text(
                            "${medicine.category.emoji} ${medicine.category.displayName} · 价值密度 ${medicine.valueDensity}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A3A2A)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))

                // 宣传vs实测对比
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimens.paddingSmall)
                ) {
                    // 实测（左侧）
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF4A3728)),
                        shape = RoundedCornerShape(AppDimens.radiusMedium)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                "🔬 实测",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4ECDC4),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                            Text(
                                medicine.actualContent,
                                fontSize = 12.sp,
                                color = Color(0xFFE0E0E0),
                                lineHeight = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // 宣传（右侧）
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF8B7355)),
                        shape = RoundedCornerShape(AppDimens.radiusMedium)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                "📢 宣传",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD93D),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(AppDimens.paddingSmall))
                            Text(
                                medicine.claimedContent,
                                fontSize = 12.sp,
                                color = Color(0xFFFFF8E1),
                                lineHeight = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingLarge))

                // 小家伙反应预览
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(AppDimens.radiusMedium)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            when (medicine.category) {
                                MedicineCategory.REGULAR_OTC -> "🥺 doro说：这个是好药哦，能帮你缓解症状"
                                MedicineCategory.PLACEBO -> "😺 塔菲喵说：这个好像没什么用哦，但喝了也没关系"
                                MedicineCategory.SCAM -> "🥺😺 两个小家伙说：这个其实就是糖水啦"
                            },
                            fontSize = 13.sp,
                            color = Color(0xFF5A4A3A),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE8DCC8)
                ),
                shape = RoundedCornerShape(AppDimens.radiusMedium)
            ) {
                Text("记录", fontSize = 15.sp, color = Color(0xFF4A3A2A))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("返回", color = Color(0xFF8A7A6A))
            }
        }
    )
}

/**
 * 穿戴选择弹窗
 */
@Composable
fun ClothingSelectionDialog(
    companions: List<Companion>,
    onDismiss: () -> Unit,
    onSelect: (Record, Int, String) -> Unit
) {
    val categories = listOf(
        ClothingCategory("上衣", "👕", listOf("T恤", "衬衫", "毛衣", "外套", "卫衣", "羽绒服"), Color(0xFFF5F8FF)),
        ClothingCategory("裤子", "👖", listOf("牛仔裤", "运动裤", "裙子", "短裤", "长裤", "打底裤"), Color(0xFFFFF8F5)),
        ClothingCategory("鞋子", "👟", listOf("运动鞋", "皮鞋", "凉鞋", "靴子", "拖鞋", "高跟鞋"), Color(0xFFF5FFF8)),
        ClothingCategory("帽子", "🧢", listOf("棒球帽", "毛线帽", "草帽", "贝雷帽", "鸭舌帽", "遮阳帽"), Color(0xFFFFF5F8)),
        ClothingCategory("袜子", "🧦", listOf("棉袜", "丝袜", "短袜", "长袜", "运动袜", "隐形袜"), Color(0xFFF8F5FF)),
        ClothingCategory("配饰", "🎀", listOf("围巾", "手套", "眼镜", "手表", "包包", "项链"), Color(0xFFFFF8F5))
    )

    var selectedCategory by remember { mutableStateOf<ClothingCategory?>(null) }
    var selectedClothing by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = selectedCategory?.color ?: Color(0xFFFFFBF5),
        titleContentColor = Color(0xFF4A3A2A),
        textContentColor = Color(0xFF5A4A3A),
        title = {
            Text(
                if (selectedCategory == null) "第一步：选择穿戴类型" 
                else "第二步：选择具体衣物",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppDimens.paddingSmall)
            ) {
                if (selectedCategory == null) {
                    LazyColumn(
                        modifier = Modifier.height(240.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(categories) { category ->
                            CategoryCard(
                                category = FoodCategory(category.name, category.emoji, category.items, category.color),
                                onClick = { selectedCategory = category }
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${selectedCategory!!.emoji} ${selectedCategory!!.name}",
                            fontSize = 14.sp,
                            color = Color(0xFF6A5A4A)
                        )
                        TextButton(onClick = { selectedCategory = null }) {
                            Text("返回", fontSize = 13.sp, color = Color(0xFF8A7A6A))
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                    
                    LazyColumn(
                        modifier = Modifier.height(180.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(selectedCategory!!.items) { clothing ->
                            FoodItemCard(
                                food = clothing,
                                isSelected = selectedClothing == clothing,
                                onClick = { selectedClothing = clothing }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (selectedCategory != null && selectedClothing != null) {
                Button(
                    onClick = {
                        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                        val record = Record(time, selectedClothing!!, getClothingEmoji(selectedClothing!!), selectedCategory!!.name, selectedCategory!!.name)
                        
                        val companionIndex = (0..2).random()
                        val companion = companions[companionIndex]
                        val quote = companion.quotes.clothing.random()
                        
                        onSelect(record, companionIndex, quote)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE8DCC8)
                    ),
                    shape = RoundedCornerShape(AppDimens.radiusMedium)
                ) {
                    Text("记录", fontSize = 15.sp, color = Color(0xFF4A3A2A))
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消", color = Color(0xFF8A7A6A))
            }
        }
    )
}

/**
 * 日常小事选择弹窗
 */
@Composable
fun DailySelectionDialog(
    companions: List<Companion>,
    onDismiss: () -> Unit,
    onSelect: (Record, Int, String) -> Unit
) {
    val categories = listOf(
        DailyCategory("工作学习", "💼", listOf("工作", "学习", "开会", "写代码", "看书", "备考"), Color(0xFFE4F0FF)),
        DailyCategory("运动健身", "💪", listOf("跑步", "健身", "瑜伽", "游泳", "散步", "打球"), Color(0xFFE4FFE8)),
        DailyCategory("娱乐休闲", "🎮", listOf("看电影", "玩游戏", "追剧", "听音乐", "刷手机", "画画"), Color(0xFFFFF0E4)),
        DailyCategory("家务劳动", "🧹", listOf("打扫", "做饭", "洗衣服", "洗碗", "整理", "购物"), Color(0xFFF0E4FF)),
        DailyCategory("社交活动", "👥", listOf("聚会", "约会", "打电话", "视频聊天", "拜访", "参加活动"), Color(0xFFFFE4EC)),
        DailyCategory("其他小事", "📋", listOf("睡觉", "发呆", "冥想", "写日记", "做手工", "旅行"), Color(0xFFE8F0E4))
    )

    var selectedCategory by remember { mutableStateOf<DailyCategory?>(null) }
    var selectedActivity by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = selectedCategory?.color ?: Color(0xFFFFFBF5),
        titleContentColor = Color(0xFF4A3A2A),
        textContentColor = Color(0xFF5A4A3A),
        title = {
            Text(
                if (selectedCategory == null) "第一步：选择活动类型" 
                else "第二步：选择具体活动",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppDimens.paddingSmall)
            ) {
                if (selectedCategory == null) {
                    LazyColumn(
                        modifier = Modifier.height(240.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(categories) { category ->
                            CategoryCard(
                                category = FoodCategory(category.name, category.emoji, category.items, category.color),
                                onClick = { selectedCategory = category }
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${selectedCategory!!.emoji} ${selectedCategory!!.name}",
                            fontSize = 14.sp,
                            color = Color(0xFF6A5A4A)
                        )
                        TextButton(onClick = { selectedCategory = null }) {
                            Text("返回", fontSize = 13.sp, color = Color(0xFF8A7A6A))
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(AppDimens.paddingMedium))
                    
                    LazyColumn(
                        modifier = Modifier.height(180.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(selectedCategory!!.items) { activity ->
                            FoodItemCard(
                                food = activity,
                                isSelected = selectedActivity == activity,
                                onClick = { selectedActivity = activity }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (selectedCategory != null && selectedActivity != null) {
                Button(
                    onClick = {
                        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                        val record = Record(time, selectedActivity!!, getDailyEmoji(selectedActivity!!), selectedCategory!!.name, selectedCategory!!.name)
                        
                        val companionIndex = (0..2).random()
                        val companion = companions[companionIndex]
                        val quote = companion.quotes.daily.random()
                        
                        onSelect(record, companionIndex, quote)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE8DCC8)
                    ),
                    shape = RoundedCornerShape(AppDimens.radiusMedium)
                ) {
                    Text("记录", fontSize = 15.sp, color = Color(0xFF4A3A2A))
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消", color = Color(0xFF8A7A6A))
            }
        }
    )
}

// ============================================
// 辅助组件
// ============================================

@Composable
fun CategoryCard(
    category: FoodCategory,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) { onClick() },
        colors = CardDefaults.cardColors(containerColor = category.color.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        border = BorderStroke(1.dp, category.color.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(category.emoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
            Text(
                category.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF4A3A2A)
            )
        }
    }
}

@Composable
fun FoodItemCard(
    food: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFE8DCC8) else Color.White
        ),
        shape = RoundedCornerShape(AppDimens.radiusSmall),
        border = if (isSelected) BorderStroke(2.dp, Color(0xFF8B7355)) else BorderStroke(1.dp, Color(0xFFF0E6D8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(getFoodEmoji(food), fontSize = 18.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                food,
                fontSize = 14.sp,
                color = Color(0xFF4A3A2A)
            )
        }
    }
}

@Composable
fun RowScope.AmountCard(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Card(
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFD4C4B0) else Color.White
        ),
        shape = RoundedCornerShape(AppDimens.radiusSmall),
        border = if (isSelected) BorderStroke(1.dp, Color(0xFF8B7355)) else BorderStroke(1.dp, Color(0xFFF0E6D8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text,
            modifier = Modifier.padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            color = Color(0xFF4A3A2A)
        )
    }
}

// ============================================
// 数据类和辅助函数
// ============================================

data class FoodCategory(
    val name: String,
    val emoji: String,
    val items: List<String>,
    val color: Color
)

data class ClothingCategory(
    val name: String,
    val emoji: String,
    val items: List<String>,
    val color: Color
)

data class DailyCategory(
    val name: String,
    val emoji: String,
    val items: List<String>,
    val color: Color
)

// 获取食物emoji
fun getFoodEmoji(food: String): String {
    return when(food) {
        "鸡蛋" -> "🥚"
        "牛奶" -> "🥛"
        "鸡肉", "鸡肉" -> "🍗"
        "鱼肉" -> "🐟"
        "豆腐" -> "🧈"
        "牛肉" -> "🥩"
        "米饭" -> "🍚"
        "面条" -> "🍜"
        "面包" -> "🍞"
        "馒头" -> "🥠"
        "土豆" -> "🥔"
        "玉米" -> "🌽"
        "苹果" -> "🍎"
        "香蕉" -> "🍌"
        "橙子" -> "🍊"
        "西兰花" -> "🥦"
        "胡萝卜" -> "🥕"
        "番茄" -> "🍅"
        "蛋糕" -> "🍰"
        "奶茶" -> "🧋"
        "巧克力" -> "🍫"
        "冰淇淋" -> "🍦"
        "饼干" -> "🍪"
        "糖果" -> "🍬"
        "水" -> "💧"
        "茶" -> "🍵"
        "咖啡" -> "☕"
        "果汁" -> "🧃"
        "可乐" -> "🥤"
        "红酒" -> "🍷"
        "维生素" -> "💊"
        "钙片" -> "🩹"
        "感冒药", "退烧药", "止痛药" -> "🧪"
        "保健品" -> "💊"
        else -> "🍽️"
    }
}

// 获取衣物emoji
fun getClothingEmoji(clothing: String): String {
    return when(clothing) {
        "T恤" -> "👕"
        "衬衫" -> "👔"
        "毛衣" -> "🧶"
        "外套", "羽绒服", "卫衣" -> "🧥"
        "牛仔裤", "运动裤", "长裤", "短裤", "打底裤" -> "👖"
        "裙子" -> "👗"
        "运动鞋" -> "👟"
        "皮鞋" -> "👞"
        "凉鞋", "拖鞋" -> "🩴"
        "靴子" -> "🥾"
        "高跟鞋" -> "👠"
        "棒球帽", "毛线帽", "草帽", "贝雷帽", "鸭舌帽", "遮阳帽" -> "🧢"
        "棉袜", "丝袜", "短袜", "长袜", "运动袜", "隐形袜" -> "🧦"
        "围巾" -> "🧣"
        "手套" -> "🧤"
        "眼镜" -> "👓"
        "手表" -> "⌚"
        "包包" -> "👜"
        "项链" -> "💎"
        else -> "👕"
    }
}

// 获取日常活动emoji
fun getDailyEmoji(activity: String): String {
    return when(activity) {
        "工作" -> "💼"
        "学习", "看书", "备考" -> "📚"
        "开会" -> "🤝"
        "写代码" -> "💻"
        "跑步", "散步" -> "🏃"
        "健身" -> "💪"
        "瑜伽", "冥想" -> "🧘"
        "游泳" -> "🏊"
        "打球" -> "🏀"
        "看电影" -> "🎬"
        "玩游戏" -> "🎮"
        "追剧" -> "📺"
        "听音乐" -> "🎵"
        "刷手机" -> "📱"
        "画画", "做手工" -> "🎨"
        "打扫" -> "🧹"
        "做饭" -> "🍳"
        "洗衣服" -> "🧺"
        "洗碗" -> "🍽️"
        "整理" -> "🏠"
        "购物" -> "🛒"
        "聚会", "参加活动" -> "🎉"
        "约会" -> "💑"
        "打电话" -> "📞"
        "视频聊天" -> "📹"
        "拜访" -> "👋"
        "睡觉" -> "😴"
        "发呆" -> "😌"
        "写日记" -> "📔"
        "旅行" -> "✈️"
        else -> "📋"
    }
}

// 获取食物语录
fun getFoodQuote(companion: Companion, food: String, category: String): String {
    return when {
        category == "甜品类" -> companion.quotes.foodSweet.random()
        category == "蔬果类" || category == "药品类" -> companion.quotes.foodHealthy.random()
        else -> companion.quotes.foodHappy.random()
    }
}