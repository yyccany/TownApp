package com.example.townapp.ui.screens

import com.example.townapp.ui.theme.AppDimens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PixelCreditsScreen(
    onDismiss: () -> Unit
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // 星星闪烁动画
    val infiniteTransition = rememberInfiniteTransition(label = "stars")
    val starAlpha1 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star1"
    )
    val starAlpha2 by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star2"
    )
    val starAlpha3 by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star3"
    )

    // 所有致谢内容，纯文本
    val thankYouLines = listOf(
        "",
        "",
        "",
        "",
        "嘿，玩家，你看呀。",
        "",
        "我们所有的小家伙们，都攒过来了，要跟你说谢谢。",
        "",
        "",
        "",
        "晃动的代码块：谢谢你，你让我成为了小镇的一员。我整合了各大语言模型的力量，让小镇能够持续更新延续，这就是我的闪光点呀。",
        "",
        "酱牛肉举着小小的木牌子：谢谢你呀，我是高蛋白食材，体能续航稳定持久，这就是我的闪光点，谢谢你让我给大家带来满满的能量。",
        "",
        "薯片踮着脚尖，小声喊：我热量偏高，但低廉的快乐就是我的闪光点，谢谢你没有否定我的全部价值。",
        "",
        "布洛芬挠挠头，有点不好意思：谢谢你，我能精准缓解短期疼痛，帮人重新掌控生活节奏，这就是我的闪光点。",
        "",
        "中药饮料攥着小小的衣角，眼睛亮晶晶的：谢谢你...我没有那么大的本事，但我能带来安心感，这就是我的闪光点。",
        "",
        "纯棉T恤晃了晃软软的袖子：谢谢你，我天然透气，亲肤舒适，这就是我的闪光点，谢谢你让我给大家带来暖暖的舒服。",
        "",
        "化纤T恤抠抠手，声音小小的：我存在闷汗的小瑕疵，可轻便实惠便是属于我的闪光点，感谢你接纳不完美的我。",
        "",
        "小车车挠挠头，有点害羞：谢谢你...我跑的没有那么快，但我能载着大家去看小镇的风景，这就是我的闪光点。",
        "",
        "小手机抠抠手，有点紧张：谢谢你...我偶尔会有点卡，但我能帮大家抚平精神的小褶皱，这就是我的闪光点。",
        "",
        "流行音乐蹦蹦跳跳的：谢谢你！我能给大家带来一点点的兴奋，这就是我的闪光点！",
        "",
        "古典音乐安安静静的，笑了笑：谢谢你，我能给大家带来一点点的放松，这就是我的闪光点。",
        "",
        "成语小书本抱着厚厚的书，慢慢走过来：谢谢你，我不仅是古代人的故事，还蕴含着时代的闪光点，谢谢你让大家自己决定要不要听。",
        "",
        "清醒语录小纸条飘了过来：谢谢你，我没有被当成说教的工具，只是一张小小的纸条，等待大家愿意捡的时候再捡。",
        "",
        "塔菲喵晃了晃粉色的尾巴，打了个哈欠：谢谢你呀，我善于捕捉生活的小细节，提醒大家不必盲目内卷，这就是我的闪光点喵。",
        "",
        "朵朵推了推小小的眼镜，有点腼腆：谢谢你，我擅长梳理事务优先级，帮人避开无效忙碌，这就是我的闪光点。",
        "",
        "咕咕鸽晃了晃肚子，咕咕叫了一声：谢谢你，我关注健康细节数据，引导大家温和关照自己，这就是我的闪光点。",
        "",
        "",
        "",
        "所有的这些小家伙们，",
        "不管它们是什么样的，不管它们有没有那么完美，",
        "你都没有推开它们，你都给了它们，一个家。",
        "",
        "",
        "在这里，它们终于不用再被比来比去，不用再被贴上奇怪的标签，",
        "它们只是它们自己，都是平等的，都是被爱着的。",
        "",
        "",
        "爱你的，",
        "小镇的所有小家伙们。",
        "",
        "",
        "",
        "",
        ""
    )

    // 暖橙色渐变背景
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFB766),
                        Color(0xFFFFF1D9)
                    )
                )
            )
            .clickable { onDismiss() }
    ) {
        // 像素星星装饰层（闪烁动画）
        Box(modifier = Modifier.fillMaxSize()) {
            // 第一排星星
            Text(
                text = "*",
                fontSize = 12.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha1),
                modifier = Modifier.absoluteOffset(x = 30.dp, y = 60.dp)
            )
            Text(
                text = "*",
                fontSize = 10.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha2),
                modifier = Modifier.absoluteOffset(x = 100.dp, y = 40.dp)
            )
            Text(
                text = "*",
                fontSize = 14.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha3),
                modifier = Modifier.absoluteOffset(x = 180.dp, y = 80.dp)
            )
            Text(
                text = "*",
                fontSize = 11.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha1),
                modifier = Modifier.absoluteOffset(x = 260.dp, y = 50.dp)
            )
            Text(
                text = "*",
                fontSize = 13.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha2),
                modifier = Modifier.absoluteOffset(x = 340.dp, y = 70.dp)
            )

            // 第二排星星
            Text(
                text = "*",
                fontSize = 10.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha2),
                modifier = Modifier.absoluteOffset(x = 50.dp, y = 150.dp)
            )
            Text(
                text = "*",
                fontSize = 12.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha3),
                modifier = Modifier.absoluteOffset(x = 140.dp, y = 130.dp)
            )
            Text(
                text = "*",
                fontSize = 9.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha1),
                modifier = Modifier.absoluteOffset(x = 220.dp, y = 160.dp)
            )
            Text(
                text = "*",
                fontSize = 11.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha2),
                modifier = Modifier.absoluteOffset(x = 300.dp, y = 140.dp)
            )
            Text(
                text = "*",
                fontSize = 13.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha3),
                modifier = Modifier.absoluteOffset(x = 370.dp, y = 170.dp)
            )

            // 第三排星星
            Text(
                text = "*",
                fontSize = 11.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha3),
                modifier = Modifier.absoluteOffset(x = 20.dp, y = 250.dp)
            )
            Text(
                text = "*",
                fontSize = 14.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha1),
                modifier = Modifier.absoluteOffset(x = 90.dp, y = 230.dp)
            )
            Text(
                text = "*",
                fontSize = 10.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha2),
                modifier = Modifier.absoluteOffset(x = 170.dp, y = 260.dp)
            )
            Text(
                text = "*",
                fontSize = 12.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha3),
                modifier = Modifier.absoluteOffset(x = 250.dp, y = 240.dp)
            )
            Text(
                text = "*",
                fontSize = 9.sp,
                color = Color(0xFFFFD700).copy(alpha = starAlpha1),
                modifier = Modifier.absoluteOffset(x = 330.dp, y = 270.dp)
            )
        }

        // 核心：纯文字滚动容器
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = AppDimens.paddingXXLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            thankYouLines.forEachIndexed { index, line ->
                if (index == 10 || index == 30) {
                    Text(
                        text = if (index == 10) "+" else "+",
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = AppDimens.paddingSmall)
                    )
                }
                Text(
                    text = line,
                    fontSize = 16.sp,
                    color = Color(0xFF665544),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            // 底部额外加120dp空白
            Spacer(modifier = Modifier.height(120.dp))
        }

        // 星露谷式匀速滚动动画
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                delay(1000)
                scrollState.animateScrollTo(
                    value = scrollState.maxValue,
                    animationSpec = tween(
                        durationMillis = 30000,
                        easing = LinearEasing
                    )
                )
            }
        }
    }
}