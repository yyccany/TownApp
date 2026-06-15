package com.example.townapp.ui.cognition

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.townapp.ui.screens.CognitiveScalpelScreen
import com.example.townapp.ui.screens.IdiomCriticScreen
import com.example.townapp.ui.cognition.SurvivalSimulatorScreen
import com.example.townapp.ui.screens.ModernQuotesScreen
import com.example.townapp.ui.cognition.EducationConditioningScreen
import com.example.townapp.ui.cognition.IntergenerationalHarmScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CognitionAwakeningScreen(
    onBack: () -> Unit = {}
) {
    var currentScreen by remember { mutableStateOf<String?>(null) }

    if (currentScreen == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF9EC))
                .navigationBarsPadding()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "🧠 认知觉醒",
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
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }

                item {
                    NavigationButton(
                        icon = "🔪",
                        title = "认知手术刀",
                        onClick = { currentScreen = "scalpel" }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    NavigationButton(
                        icon = "🎮",
                        title = "双生存法则模拟器",
                        onClick = { currentScreen = "simulator" }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    NavigationButton(
                        icon = "📖",
                        title = "成语批判解读",
                        onClick = { currentScreen = "idiom" }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    NavigationButton(
                        icon = "💬",
                        title = "现代清醒语录",
                        onClick = { currentScreen = "quotes" }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    NavigationButton(
                        icon = "📚",
                        title = "教育规训解剖",
                        onClick = { currentScreen = "education" }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    NavigationButton(
                        icon = "🫂",
                        title = "代际语言伤害",
                        onClick = { currentScreen = "intergenerational" }
                    )
                }

                item { Spacer(modifier = Modifier.height(60.dp)) }
            }
        }
    } else {
        when (currentScreen) {
            "scalpel" -> {
                CognitiveScalpelScreen(
                    onBack = { currentScreen = null }
                )
            }
            "simulator" -> {
                SurvivalSimulatorScreen(
                    onBack = { currentScreen = null }
                )
            }
            "idiom" -> {
                IdiomCriticScreen(
                    onBack = { currentScreen = null }
                )
            }
            "quotes" -> {
                ModernQuotesScreen(
                    onBack = { currentScreen = null }
                )
            }
            "education" -> {
                EducationConditioningScreen(
                    onBack = { currentScreen = null }
                )
            }
            "intergenerational" -> {
                IntergenerationalHarmScreen(
                    onBack = { currentScreen = null }
                )
            }
        }
    }
}

@Composable
fun NavigationButton(
    icon: String,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0E0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFE8DFD4)),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.TextPrimary
            )

            Spacer(modifier = Modifier.weight(1f))

            Text("→", fontSize = 20.sp, color = AppColors.TextTertiary)
        }
    }
}