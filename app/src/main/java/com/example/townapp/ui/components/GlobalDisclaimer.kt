package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 全局免责声明组件。
 * 全页面强制挂载，确保合规全覆盖。
 *
 * 核心声明：
 * 1. 所有数据基于公认可查的标准化数据库，不构成医疗建议
 * 2. 不推荐/不否定任何商品，仅做数据对比
 * 3. 用户自主决策，自行承担所有后果
 *
 * @param compact 紧凑模式（用于底部导航栏场景，显示简短版）
 */
@Composable
fun GlobalDisclaimer(
    compact: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (compact) {
        // 紧凑模式：底部一行
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "※ 本应用仅提供客观数据对比，不构成任何医疗或消费建议",
                fontSize = 9.sp,
                color = Color(0xFF999999),
                textAlign = TextAlign.Center
            )
        }
    } else {
        // 完整模式
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color(0xFFFAFAFA))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "免责声明",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF666666)
            )

            val statements = listOf(
                "本应用所有营养数据、风险指标均来源于公认可查的标准化数据库与科研成果，不作为任何医疗诊断、治疗或健康建议的依据。",
                "本应用不推荐、不否定任何商品或食材，仅基于标准化数据做客观对比展示。用户自主判断、自主选择，自行承担所有后果。",
                "本应用秉持自由、平等、实事求是的原则，不偏向任何商家、专家或权威机构，所有信息对等公开，不存在隐藏数据或选择性披露。",
                "如有健康疑虑，请咨询正规医疗机构；任何基于本应用数据做的消费决策，请以实际商品包装或官方检测报告为准。"
            )

            statements.forEachIndexed { index, text ->
                Text(
                    text = "${index + 1}. $text",
                    fontSize = 11.sp,
                    color = Color(0xFF888888),
                    lineHeight = 16.sp
                )
            }
        }
    }
}

/** 页面级免责声明挂载：自动附加到底部 */
@Composable
fun WithDisclaimer(
    compact: Boolean = false,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
        GlobalDisclaimer(compact = compact)
    }
}