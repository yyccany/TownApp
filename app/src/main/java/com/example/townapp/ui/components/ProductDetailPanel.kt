package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun ProductDetailPanel(
    product: ProductEntity,
    isChildMode: Boolean,
    onClose: () -> Unit
) {
    val bgColor = if (isChildMode) Color(0xFFF5E6D3) else Color(0xFFf8f9fa)
    val textColor = if (isChildMode) Color(0xFF4A3728) else Color(0xFF1a1a2e)

    Box(modifier = Modifier.fillMaxWidth().background(bgColor)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.name,
                    fontSize = if (isChildMode) 20.sp else 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = "关闭",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.clickable { onClose() }
                )
            }

            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Text(
                    text = "${product.category} · ${product.subCategory}",
                    fontSize = 14.sp,
                    color = Color(0xFF888888)
                )
            }

            if (product.description.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = product.description,
                        fontSize = 14.sp,
                        color = textColor,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            PriceCard(product = product, isChildMode = isChildMode)
            NutritionCard(product = product, isChildMode = isChildMode)
            ValueCard(product = product, isChildMode = isChildMode)
            CostCard(product = product, isChildMode = isChildMode)
        }
    }
}

@Composable
fun PriceCard(product: ProductEntity, isChildMode: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E7)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = if (isChildMode) "💰 价格信息" else "价格信息",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A3728)
            )
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Text(
                    text = "市场价",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "¥${product.marketPrice}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFe94560)
                )
            }
            if (product.secondHandRate < 100) {
                Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                    Text(
                        text = "二手保值率",
                        fontSize = 14.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${product.secondHandRate}%",
                        fontSize = 14.sp,
                        color = Color(0xFF27ae60)
                    )
                }
            }
            if (product.iqTaxScore > 0) {
                Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                    Text(
                        text = "价值密度评分",
                        fontSize = 14.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${product.iqTaxScore}/10",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (product.iqTaxScore >= 7) Color(0xFFe94560) else Color(0xFF27ae60)
                    )
                }
            }
        }
    }
}

@Composable
fun NutritionCard(product: ProductEntity, isChildMode: Boolean) {
    if (product.nutritionValue.isNotEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0FFE0)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = if (isChildMode) "🥗 营养信息" else "营养价值",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2D5A27)
                )
                Text(
                    text = product.nutritionValue,
                    fontSize = 14.sp,
                    color = Color(0xFF3D7A37),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ValueCard(product: ProductEntity, isChildMode: Boolean) {
    val hasValueData = product.medicalValue.isNotEmpty() ||
            product.mentalValue.isNotEmpty() ||
            product.socialValue.isNotEmpty()

    if (hasValueData) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0FFFF)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = if (isChildMode) "💎 价值信息" else "价值维度",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF275A6D)
                )
                if (product.medicalValue.isNotEmpty()) {
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                        Text(
                            text = "健康",
                            fontSize = 12.sp,
                            color = Color(0xFF27ae60),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = product.medicalValue,
                            fontSize = 12.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }
                if (product.mentalValue.isNotEmpty()) {
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                        Text(
                            text = "心理",
                            fontSize = 12.sp,
                            color = Color(0xFF9b59b6),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = product.mentalValue,
                            fontSize = 12.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }
                if (product.socialValue.isNotEmpty()) {
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                        Text(
                            text = "社交",
                            fontSize = 12.sp,
                            color = Color(0xFF3498db),
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = product.socialValue,
                            fontSize = 12.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CostCard(product: ProductEntity, isChildMode: Boolean) {
    val hasCostData = product.wearRate > 0 ||
            product.lifeCycle > 0 ||
            product.monthlyMaintainCost > 0 ||
            product.timeCostDaily > 0

    if (hasCostData) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0FF)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = if (isChildMode) "📊 使用成本" else "使用成本",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5A276D)
                )
                if (product.lifeCycle > 0) {
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                        Text(
                            text = "使用寿命",
                            fontSize = 12.sp,
                            color = Color(0xFF666666),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${product.lifeCycle} 天",
                            fontSize = 12.sp,
                            color = Color(0xFF5A276D)
                        )
                    }
                }
                if (product.wearRate > 0) {
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                        Text(
                            text = "磨损率",
                            fontSize = 12.sp,
                            color = Color(0xFF666666),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${product.wearRate}%",
                            fontSize = 12.sp,
                            color = Color(0xFF5A276D)
                        )
                    }
                }
                if (product.monthlyMaintainCost > 0) {
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                        Text(
                            text = "月养护费",
                            fontSize = 12.sp,
                            color = Color(0xFF666666),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "¥${product.monthlyMaintainCost}",
                            fontSize = 12.sp,
                            color = Color(0xFF5A276D)
                        )
                    }
                }
                if (product.timeCostDaily > 0) {
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                        Text(
                            text = "日均时间",
                            fontSize = 12.sp,
                            color = Color(0xFF666666),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${product.timeCostDaily} 小时",
                            fontSize = 12.sp,
                            color = Color(0xFF5A276D)
                        )
                    }
                }
            }
        }
    }
}