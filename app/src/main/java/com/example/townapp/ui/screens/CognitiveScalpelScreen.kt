package com.example.townapp.ui.screens

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.townapp.ui.theme.AppColors
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CognitiveScalpelScreen(
    onBack: () -> Unit,
    topic: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9EC))
            .navigationBarsPadding()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "🔪 ${topic ?: "认知手术刀"}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "返回",
                        tint = AppColors.TextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFFFF9EC)
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(AppDimens.paddingLarge),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimens.radiusLarge),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0E0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = topic ?: "认知手术刀",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.TextPrimary,
                            modifier = Modifier.padding(bottom = AppDimens.paddingLarge)
                        )
                        
                        Text(
                            text = """
认知手术刀是一种思维工具，帮助你：

• 🔍 识别隐藏的假设和偏见
• ✂️ 切割复杂问题，找到核心
• 💡 从不同角度审视事物
• 🧠 培养批判性思维能力

这不是要否定一切，而是要更清晰地看见真相。
                            """.trimIndent(),
                            fontSize = 14.sp,
                            color = AppColors.DeepBrown,
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(AppDimens.paddingXXLarge))
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimens.radiusLarge),
                    colors = CardDefaults.cardColors(containerColor = AppColors.SuccessBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = "💡 今日反思",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.SuccessDark,
                            modifier = Modifier.padding(bottom = AppDimens.paddingMedium)
                        )
                        
                        Text(
                            text = """
"你所看到的，是你选择看到的。
但真相，往往藏在你没注意的地方。"
                            """.trimIndent(),
                            fontSize = 14.sp,
                            color = AppColors.SuccessMedium,
                            lineHeight = 24.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}
