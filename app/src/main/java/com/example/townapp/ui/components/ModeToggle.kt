package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ModeToggle(
    isChildMode: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onToggle() },
        colors = CardDefaults.cardColors(
            containerColor = if (isChildMode) Color(0xFFFFE4C4) else Color(0xFFe0e0e0)
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        if (!isChildMode) Color.White else Color.Transparent,
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (!isChildMode) "🧠" else "",
                    fontSize = 18.sp
                )
            }
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        if (isChildMode) Color.White else Color.Transparent,
                        RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isChildMode) "🎨" else "",
                    fontSize = 18.sp
                )
            }
        }
    }
}