package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.LifePathData

private val paper = Color(0xFFF5F0E6)
private val ink = Color(0xFF3A352D)
private val inkLight = Color(0xFF7A7468)
private val inkFaint = Color(0xFFB5AEA0)
private val overlay = Color(0x26000000)

@Composable
fun LifePathChoiceDialog(
    onLifePathSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(overlay)
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .background(paper)
                .padding(horizontal = 32.dp, vertical = 32.dp)
                .verticalScroll(rememberScrollState())
                .clickable(enabled = false) {}
        ) {
            Text(
                text = "你会过怎样的日子？",
                fontSize = 18.sp,
                color = ink,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "每个人都在过自己的生活",
                fontSize = 13.sp,
                color = inkFaint,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            LifePathData.paths.forEach { path ->
                val params = LifePathData.careerParams[path.id]
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLifePathSelected(path.id) }
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = path.title,
                        fontSize = 16.sp,
                        color = ink,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = path.subtitle,
                        fontSize = 13.sp,
                        color = inkFaint,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Text(
                        text = path.sceneDescription,
                        fontSize = 13.sp,
                        color = inkLight,
                        lineHeight = 20.sp
                    )
                    if (params != null && params.baseSalary > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "月薪 ${params.baseSalary.toInt()}  ·  加班 ${(params.overtimeRate * 100).toInt()}%",
                            fontSize = 12.sp,
                            color = inkFaint
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(inkFaint.copy(alpha = 0.2f))
                )
            }
        }
    }
}
