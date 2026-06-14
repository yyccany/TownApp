package com.example.townapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.townapp.data.ItemState
import com.example.townapp.data.PixelShapeType
import com.example.townapp.domain.ColorUtil

/**
 * 全局通用像素绘制组件
 *
 * 自动通过 colorId 从全局颜色库取色，
 * 同时保留状态调色（饱和度/亮度），
 * 形状尺寸固定，不受人体数据影响。
 *
 * @param shapeType 形状类型（SQUARE/HORIZONTAL/VERTICAL/DISH_PLATE/DISH_BOWL）
 * @param colorId 全局颜色库ID
 * @param modifier 外部修饰符
 * @param baseSize 基准尺寸
 * @param itemState 物品状态（NEW/SLIGHT_WORN/OLD）
 * @param label 可选文字标签（如菜名/服饰名首字）
 */
@Composable
fun DynamicPixelShape(
    shapeType: PixelShapeType,
    colorId: Int,
    modifier: Modifier = Modifier,
    baseSize: Dp = 40.dp,
    itemState: ItemState = ItemState.NEW,
    label: String = ""
) {
    val hexColor = ColorUtil.getColorHexById(colorId)
    val finalColor = ColorStateMachine.getColorFromHex(hexColor, itemState)

    val (width, height) = when (shapeType) {
        PixelShapeType.SQUARE -> baseSize to baseSize
        PixelShapeType.HORIZONTAL -> baseSize * 1.5f to baseSize
        PixelShapeType.VERTICAL -> baseSize to baseSize * 1.5f
        PixelShapeType.DISH_PLATE -> baseSize * 1.2f to baseSize * 0.8f
        PixelShapeType.DISH_BOWL -> baseSize to baseSize * 0.9f
    }

    Box(
        modifier = modifier
            .size(width, height)
            .clip(RoundedCornerShape(4.dp))
            .background(finalColor),
        contentAlignment = Alignment.Center
    ) {
        if (label.isNotEmpty()) {
            androidx.compose.material3.Text(
                text = label.take(1),
                fontSize = (baseSize.value * 0.4f).sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 快捷组件：根据颜色ID和状态绘制纯色像素块
 */
@Composable
fun PixelBlock(
    colorId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    itemState: ItemState = ItemState.NEW
) {
    DynamicPixelShape(
        shapeType = PixelShapeType.SQUARE,
        colorId = colorId,
        modifier = modifier,
        baseSize = size,
        itemState = itemState
    )
}