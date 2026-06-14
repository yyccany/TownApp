package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.database.entity.ProductEntity

@Composable
fun PixelTownMap(
    products: List<ProductEntity>,
    onProductClick: (ProductEntity) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(pixelBackground)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🏠 万物薪俸小镇",
                fontSize = 28.sp,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFF4A3728),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products.size) { index ->
                    val product = products[index]
                    PixelProductCard(
                        product = product,
                        onClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun PixelProductCard(
    product: ProductEntity,
    onClick: () -> Unit
) {
    val icon = getProductIcon(product.category)
    val bgColor = getCategoryColor(product.category)

    Card(
        modifier = Modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = product.name,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}

private val pixelBackground = Color(0xFFF5E6D3)

private fun getProductIcon(category: String): String {
    return when {
        category.contains("家常菜") -> "🍳"
        category.contains("快餐") -> "🍔"
        category.contains("主食") -> "🍚"
        category.contains("零食") -> "🍿"
        category.contains("饮料") -> "🥤"
        category.contains("穿戴") -> "👕"
        category.contains("文具") -> "✏️"
        category.contains("玩具") -> "🧸"
        category.contains("宠物") -> "🐾"
        else -> "📦"
    }
}

private fun getCategoryColor(category: String): Color {
    return when {
        category.contains("家常菜") -> Color(0xFFFFE4C4)
        category.contains("快餐") -> Color(0xFFE0FFE0)
        category.contains("主食") -> Color(0xFFFFF8DC)
        category.contains("零食") -> Color(0xFFFFE0FF)
        category.contains("饮料") -> Color(0xFFE0FFFF)
        category.contains("穿戴") -> Color(0xFFFFFFE0)
        category.contains("文具") -> Color(0xFFE0E0FF)
        category.contains("玩具") -> Color(0xFFFFF0E0)
        category.contains("宠物") -> Color(0xFFE0FFE0)
        else -> Color(0xFFF0F0F0)
    }
}