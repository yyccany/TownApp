package com.example.townapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.AnimatedVisibility

@Composable
fun ChildhoodReflectionSection() {
    val message = remember { mutableStateOf("") }
    val isExpanded = remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "📮 给小时候的自己捎句话",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )
                IconButton(onClick = { isExpanded.value = !isExpanded.value }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = if (isExpanded.value) "收起" else "展开",
                        tint = Color(0xFF1565C0)
                    )
                }
            }
            
            AnimatedVisibility(visible = isExpanded.value) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    TextField(
                        value = message.value,
                        onValueChange = { message.value = it },
                        placeholder = { Text("写下你想对小时候的自己说的话...") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.End) {
                        Button(
                            onClick = {
                                message.value = ""
                                isExpanded.value = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1565C0)
                            )
                        ) {
                            Text("发送", color = Color.White)
                        }
                    }
                }
            }
            
            if (!isExpanded.value && message.value.isNotEmpty()) {
                Text(
                    text = "\"${message.value.take(20)}${if (message.value.length > 20) "..." else "\""}",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}