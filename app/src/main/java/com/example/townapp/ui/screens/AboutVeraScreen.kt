package com.example.townapp.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.ui.components.StandardTopBar
import com.example.townapp.ui.theme.DictionaryTokens

/**
 * 关于 VERA 页面 — 用户可见轻量化版本
 *
 * 面向普通使用者，不使用复杂理论术语。
 * 只讲产品价值、设计初心、核心作用。
 */
@Composable
fun AboutVeraScreen(
    onBack: () -> Unit,
    onMenuClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StandardTopBar(
            title = "关于 Vera",
            onMenuClick = onBack,
            menuIcon = "back"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = DictionaryTokens.pagePadding)
        ) {
            Spacer(modifier = Modifier.height(DictionaryTokens.sectionSpacingLarge))

            // ============================================
            // VERA 品牌区
            // ============================================
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 图标圆形占位
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(DictionaryTokens.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "V",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = DictionaryTokens.primary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "VERA",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DictionaryTokens.textTitle,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cognition Dictionary · Train Your Judgment",
                    fontSize = DictionaryTokens.Type.captionSize,
                    color = DictionaryTokens.textCaption,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "v3.0",
                    fontSize = 12.sp,
                    color = DictionaryTokens.textCaption.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(DictionaryTokens.sectionSpacingLarge))

            // ============================================
            // 一句话定位
            // ============================================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                colors = CardDefaults.cardColors(containerColor = DictionaryTokens.primary.copy(alpha = 0.06f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = "一套帮你独立思考的认知工具。\n不教育你该怎么活，只帮你看清那些「从来如此」的念头。",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = DictionaryTokens.textHeading,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(DictionaryTokens.cardPadding)
                )
            }

            Spacer(modifier = Modifier.height(DictionaryTokens.sectionSpacingLarge))

            // ============================================
            // Vera 是怎样看问题的
            // ============================================
            SectionTitle("Vera 是怎样看问题的")

            ThinkingLayerCard(
                number = 1,
                title = "回溯来处",
                body = "每一句老话、每一个道理，都不是凭空冒出来的。\nVera 会带你回到它出生的年代：那时候人们靠什么生活？社会是什么结构？为什么这句话在当时有道理？\n看清了来处，才知道它今天还适不适用。"
            )

            ThinkingLayerCard(
                number = 2,
                title = "正视当下",
                body = "同一句话，放在今天可能变成了另外的意思。\n职场、婚恋、社交、消费——现代社会有现代社会的规则，老话被借来压榨、被借来绑架，Vera 帮你把这些「变味」的地方指出来。"
            )

            ThinkingLayerCard(
                number = 3,
                title = "识别思维陷阱",
                body = "人天生有一些思维漏洞：只看到成功案例、把复杂问题简化为对错、跟着多数人走就觉得安全。\nVera 标注了常见的认知偏差，帮你识别自己可能在哪里被绕进去了。"
            )

            ThinkingLayerCard(
                number = 4,
                title = "尊重科学事实",
                body = "有些说法涉及养生、命运、玄学，Vera 用可验证的科学事实来检验。\n不空谈「科学精神」，给你实实在在的自然科学依据。"
            )

            ThinkingLayerCard(
                number = 5,
                title = "也尊重你的感受",
                body = "看透了不等于变冷漠。\n亲情、爱情、友情本身是珍贵的东西——Vera 帮你识别其中的控制与伤害，也提醒你：真诚的感情依然值得。"
            )

            Spacer(modifier = Modifier.height(DictionaryTokens.sectionSpacingLarge))

            // ============================================
            // 核心理念
            // ============================================
            SectionTitle("核心理念")

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
            ) {
                Column(modifier = Modifier.padding(DictionaryTokens.cardPadding)) {
                    BeliefRow("人是目的，不是工具", "不拿任何「标准人生」绑架你的选择")
                    Spacer(modifier = Modifier.height(12.dp))
                    BeliefRow("帮你判断，不替你决定", "只拆解观念，不提供唯一答案")
                    Spacer(modifier = Modifier.height(12.dp))
                    BeliefRow("反对内卷，尊重节奏", "拒绝被「别人都这样了你怎么还」裹挟")
                    Spacer(modifier = Modifier.height(12.dp))
                    BeliefRow("科学理性，但不冰冷", "既看透套路，也容得下真诚的情感")
                    Spacer(modifier = Modifier.height(12.dp))
                    BeliefRow("辩证看待，不走极端", "不跪拜传统，也不全盘否定")
                }
            }

            Spacer(modifier = Modifier.height(DictionaryTokens.sectionSpacingLarge))

            // ============================================
            // 你能在这里做什么
            // ============================================
            SectionTitle("你能在这里做什么")

            // 功能卡片一：浏览词条
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
            ) {
                Row(
                    modifier = Modifier.padding(DictionaryTokens.cardPadding),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
                            .background(DictionaryTokens.primary.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "读",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DictionaryTokens.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "浏览词条",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DictionaryTokens.textTitle
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "数百条俗语、成语、流行观点的辩证解读，按分类浏览，看清每句话背后的时代逻辑与思维陷阱。",
                            fontSize = DictionaryTokens.Type.bodySize,
                            color = DictionaryTokens.textBody,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 功能卡片二：收藏
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
            ) {
                Row(
                    modifier = Modifier.padding(DictionaryTokens.cardPadding),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
                            .background(DictionaryTokens.tagYellow),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "☆",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB8860B)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "收藏词条",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DictionaryTokens.textTitle
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "把需要反复复盘的观点收藏起来，随时回来翻看。Vera 不催你、不考你、不算你打卡天数。",
                            fontSize = DictionaryTokens.Type.bodySize,
                            color = DictionaryTokens.textBody,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 功能卡片三：思辨笔记
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(DictionaryTokens.radiusCard),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = DictionaryTokens.elevationCard)
            ) {
                Row(
                    modifier = Modifier.padding(DictionaryTokens.cardPadding),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(DictionaryTokens.radiusButton))
                            .background(DictionaryTokens.tagBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✎",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3A6EA5)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "思辨笔记",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DictionaryTokens.textTitle
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "词条只提供公共的客观解读，你的个人经历、私人情感、小众观点——全写在笔记里。这是你的自留地，没有正确、觉醒、落后之分。",
                            fontSize = DictionaryTokens.Type.bodySize,
                            color = DictionaryTokens.textBody,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(DictionaryTokens.sectionSpacingLarge))

            // ============================================
            // 底部宣言
            // ============================================
            Text(
                text = "不评判 · 不催促 · 你这样就很好",
                fontSize = DictionaryTokens.Type.bodySize,
                fontWeight = FontWeight.Medium,
                color = DictionaryTokens.textCaption,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(DictionaryTokens.bottomSafePadding))
        }
    }
}

// ============================================
// 内部小组件
// ============================================

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = DictionaryTokens.Type.headingSize,
        fontWeight = FontWeight.Bold,
        color = DictionaryTokens.textHeading,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
private fun ThinkingLayerCard(
    number: Int,
    title: String,
    body: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        // 序号圆
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(DictionaryTokens.primary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$number",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DictionaryTokens.primary
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DictionaryTokens.textHeading
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = body,
                fontSize = DictionaryTokens.Type.bodySize,
                color = DictionaryTokens.textBody,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun BeliefRow(bold: String, detail: String) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "·",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DictionaryTokens.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = bold,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DictionaryTokens.textHeading
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = detail,
                fontSize = DictionaryTokens.Type.bodySize,
                color = DictionaryTokens.textBody,
                lineHeight = 18.sp
            )
        }
    }
}
