package com.example.townapp.ui.components

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.database.entity.ProductEntity

@Composable
fun AdultDataDashboard(
    products: List<ProductEntity>,
    onProductClick: (ProductEntity) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(adultBackground)) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "万物薪俸小镇",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1a1a2e)
                )
                Text(
                    text = "${products.size} 件商品",
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize().padding(top = AppDimens.paddingLarge)) {
                items(products) { product ->
                    AdultProductCard(
                        product = product,
                        onClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun AdultProductCard(
    product: ProductEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = AppDimens.paddingSmall)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(AppDimens.radiusMedium),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1a1a2e)
                )
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    Text(
                        text = product.category,
                        fontSize = 12.sp,
                        color = Color(0xFF888888),
                        modifier = Modifier.padding(end = AppDimens.paddingSmall)
                    )
                    if (product.subCategory.isNotEmpty()) {
                        Text(
                            text = product.subCategory,
                            fontSize = 12.sp,
                            color = Color(0xFFAAAAAA)
                        )
                    }
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "¥${product.marketPrice}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFe94560)
                )
                if (product.iqTaxScore > 0) {
                    Text(
                        text = "VD: ${product.iqTaxScore}",
                        fontSize = 12.sp,
                        color = getIQTaxColor(product.iqTaxScore)
                    )
                }
            }
        }
    }
}

private val adultBackground = Color(0xFFf8f9fa)

private fun getIQTaxColor(score: Double): Color {
    return when {
        score >= 7 -> Color(0xFFe94560)
        score >= 4 -> Color(0xFFf39c12)
        else -> Color(0xFF27ae60)
    }
}