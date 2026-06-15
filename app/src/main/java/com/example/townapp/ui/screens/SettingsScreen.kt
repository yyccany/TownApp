package com.example.townapp.ui.screens

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.townapp.ui.theme.AppColors
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.components.OrdinaryLifeMemorial
import com.example.townapp.ui.components.TopNavBar
import com.example.townapp.ui.viewmodel.TownViewModel

@Composable
fun SettingsScreen(
    viewModel: TownViewModel,
    onBack: () -> Unit,
    onNavigateToAdminPanel: () -> Unit = {},
    onNavigateToArchive: () -> Unit,
    onNavigateToDocument: () -> Unit = {}
) {
    val settings by viewModel.settings.collectAsState()
    val openCredits = remember { mutableStateOf(false) }

    if (openCredits.value) {
        PixelCreditsScreen(onDismiss = { openCredits.value = false })
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF9EC))
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Center
        ) {
            TopNavBar(title = "设置", showBack = true, onBack = onBack)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // 纯内存模式说明
                Text(
                    text = "💾 内存模式",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextDark,
                    modifier = Modifier.padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.paddingLarge),
                    shape = RoundedCornerShape(AppDimens.radiusMedium),
                    colors = CardDefaults.cardColors(containerColor = AppColors.SuccessBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(AppColors.SuccessLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🌱", fontSize = 24.sp)
                        }
                        Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "当前运行模式",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = AppColors.SuccessDark
                            )
                            Text(
                                text = "所有数据仅保存在内存中，关闭应用后自动清空",
                                fontSize = 12.sp,
                                color = AppColors.Success
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingXXLarge))

                // 🏠 平凡生活纪念馆
                Text(
                    text = "🏠 纪念馆",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextDark,
                    modifier = Modifier.padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium)
                )
                val mealCount by viewModel.memorialMealCount.collectAsState()
                val instantNoodleCount by viewModel.memorialInstantNoodleCount.collectAsState()
                val goodSleepDays by viewModel.memorialGoodSleepDays.collectAsState()
                val lateNightCount by viewModel.memorialLateNightCount.collectAsState()
                val selfCareActions by viewModel.memorialSelfCareActions.collectAsState()
                val placesLived by viewModel.memorialPlacesLived.collectAsState()
                val moneyDelta by viewModel.memorialMoneyDelta.collectAsState()
                OrdinaryLifeMemorial(
                    mealCount = mealCount,
                    instantNoodleCount = instantNoodleCount,
                    goodSleepDays = goodSleepDays,
                    lateNightCount = lateNightCount,
                    selfCareActions = selfCareActions,
                    placesLived = placesLived,
                    moneyDelta = moneyDelta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.paddingLarge)
                )

                Spacer(modifier = Modifier.height(AppDimens.paddingXXLarge))

                // 后台面板入口
                Text(
                    text = "🔧 后台调试",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextDark,
                    modifier = Modifier.padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.paddingLarge)
                        .clickable { onNavigateToAdminPanel() },
                    shape = RoundedCornerShape(AppDimens.radiusMedium),
                    colors = CardDefaults.cardColors(containerColor = AppColors.LightOrangeBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(AppColors.WarningAmber),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("⚙️", fontSize = 24.sp)
                        }
                        Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "后台隐藏面板",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = AppColors.ErrorDeepOrange
                            )
                            Text(
                                text = "系统参数调试、一键重置",
                                fontSize = 12.sp,
                                color = AppColors.ErrorDark
                            )
                        }
                        Text("→", fontSize = 18.sp, color = AppColors.WarningAmber)
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingXXLarge))

                // 世界观可折叠卡片
                var expandWorldView by remember { mutableStateOf(false) }
                
                Text(
                    text = "🌍 世界观",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextDark,
                    modifier = Modifier.padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.paddingLarge),
                    shape = RoundedCornerShape(AppDimens.radiusMedium),
                    border = BorderStroke(1.dp, AppColors.BorderLight),
                    colors = CardDefaults.cardColors(containerColor = AppColors.OffWhiteBackground)
                ) {
                    Column {
                        // 可点击的标题行
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandWorldView = !expandWorldView }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "小镇世界观",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.WarmBrown
                            )
                            Text(
                                text = if (expandWorldView) "▼" else "▶",
                                fontSize = 14.sp,
                                color = AppColors.PrimaryWarm
                            )
                        }
                        
                        // 展开内容
                        if (expandWorldView) {
                            Column(modifier = Modifier.padding(horizontal = AppDimens.paddingLarge)) {
                                // 核心宗旨
                                Text(
                                    text = "小镇宗旨",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.WarmBrown
                                )
                                Text(
                                    text = "以人为本，每个人内心存有向善的闪光点潜能。生存压力、小镇政策会左右短期行为，系统仅展示现实利弊，不规定最优人生道路，交由你自主抉择短期得失与长远人生走向。",
                                    fontSize = 14.sp,
                                    color = AppColors.TextSecondary,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(vertical = AppDimens.paddingSmall)
                                )

                                // 三大准则
                                Text(
                                    text = "三大准则",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppColors.WarmBrown,
                                    modifier = Modifier.padding(top = AppDimens.paddingLarge)
                                )

                                val principles = listOf(
                                    Pair("自由", "躺平独居、职场打拼、革新小镇政策均可选择，个人选择能够改变自身命运，长期决策还会影响小镇整体社会环境。"),
                                    Pair("平等", "各类职业没有高低优劣之分，贫富差距源自物价税负、生存压力、长期个人选择，所有人先天人格潜能对等。"),
                                    Pair("实事求是", "系统变量环环相扣，短期会遇见内卷、人际算计、失业负债等现实困境；时间拉长后，个人选择与顶层改革能够改善整体环境，只呈现客观事实，不做说教式指令输出。")
                                )

                                principles.forEach { (title, desc) ->
                                    Column(modifier = Modifier.padding(vertical = 6.dp)) {
                                        Text(
                                            text = "• $title",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = AppColors.PrimaryWarm
                                        )
                                        Text(
                                            text = desc,
                                            fontSize = 13.sp,
                                            color = AppColors.TextSecondary,
                                            lineHeight = 18.sp,
                                            modifier = Modifier.padding(start = AppDimens.paddingLarge)
                                        )
                                    }
                                }

                                // 延伸阅读提示
                                Text(
                                    text = "\n深层仿真模型、社会运行规则，可查阅小镇全量文案文档。",
                                    fontSize = 13.sp,
                                    color = AppColors.PrimaryWarm,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingXXLarge))

                // 📚 文档资料入口
                Text(
                    text = "📚 文档资料",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextDark,
                    modifier = Modifier.padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.paddingLarge)
                        .clickable { onNavigateToDocument() },
                    shape = RoundedCornerShape(AppDimens.radiusMedium),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF9C27B0).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("📖", fontSize = 24.sp)
                        }
                        Spacer(modifier = Modifier.width(AppDimens.paddingMedium))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "小镇全量文案文档",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF7B1FA2)
                            )
                            Text(
                                text = "全部剧情、文案、数据内容（11个系统）",
                                fontSize = 12.sp,
                                color = Color(0xFF9C27B0)
                            )
                        }
                        Text("→", fontSize = 18.sp, color = Color(0xFF7B1FA2))
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingXXLarge))

                Text(
                    text = "关于",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextDark,
                    modifier = Modifier.padding(horizontal = AppDimens.paddingLarge, vertical = AppDimens.paddingMedium)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.paddingLarge),
                    shape = RoundedCornerShape(AppDimens.radiusMedium),
                    colors = CardDefaults.cardColors(containerColor = AppColors.CardBackgroundLight)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "🏘️ 万物薪俸小镇",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.TextDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "v2.0 纯内存版",
                            fontSize = 12.sp,
                            color = AppColors.TextGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.paddingXXLarge))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppDimens.paddingLarge)
                        .clickable { openCredits.value = true },
                    colors = CardDefaults.cardColors(containerColor = AppColors.LightOrangeBackground),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.5.dp, AppColors.WarningAmber)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppDimens.paddingXLarge, vertical = 18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "💛",
                                fontSize = 22.sp
                            )
                            Spacer(modifier = Modifier.width(AppDimens.paddingSmall))
                            Text(
                                text = "小镇的小家伙们",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.ErrorDeepOrange
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "✨",
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "打开致谢名单，看看是谁在陪你",
                            fontSize = 12.sp,
                            color = AppColors.ErrorDark,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
