package com.example.townapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.townapp.data.database.entity.NpcLifeRecordEntity
import com.example.townapp.data.database.entity.NightStateEntity
import kotlinx.coroutines.launch

@Composable
fun LifeArchiveScreen(
    onBack: () -> Unit,
    records: List<NpcLifeRecordEntity>,
    nightStates: List<NightStateEntity>
) {
    var selectedRecord by remember { mutableStateOf<NpcLifeRecordEntity?>(null) }
    
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF2C3E50),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Text("←", fontSize = 24.sp, color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            "人生轨迹",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                        Text(
                            "${records.size}天的时光",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        },
        backgroundColor = Color(0xFFFFFBF0),
        content = { padding ->
            Column(modifier = Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.height(24.dp))
                
                OverviewCards(records)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                TimeDistributionChart(records)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                IncomeChart(records)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                EmotionChart(records)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                TimelineList(
                    records = records,
                    nightStates = nightStates,
                    selectedRecord = selectedRecord,
                    onSelect = { selectedRecord = if (selectedRecord?.id == it.id) null else it }
                )
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    )
}

@Composable
fun OverviewCards(records: List<NpcLifeRecordEntity>) {
    if (records.isEmpty()) return
    
    val avgWorkMinutes = records.map { it.workMinutes }.average().toInt()
    val avgHappiness = records.map { it.happiness }.average().toInt()
    val totalLaborIncome = records.sumOf { it.dayLaborIncome }.toInt()
    val totalCompoundIncome = records.sumOf { it.dayCompoundIncome }.toInt()
    
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        backgroundColor = Color.White.copy(alpha = 0.8f)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📊", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("总体概况", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2C3E50))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                OverviewItem("平均工时", "${avgWorkMinutes}min", Color(0xFFE74C3C))
                OverviewItem("平均快乐", "${avgHappiness}%", Color(0xFF27AE60))
                OverviewItem("劳动收入", "¥$totalLaborIncome", Color(0xFF3498DB))
                OverviewItem("复利收入", "¥$totalCompoundIncome", Color(0xFF8E44AD))
            }
        }
    }
}

@Composable
fun OverviewItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(48.dp).background(color.copy(alpha = 0.1f), androidx.compose.foundation.shape.RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = color)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(label, fontSize = 11.sp, color = Color(0xFF95A5A6))
    }
}

@Composable
fun TimeDistributionChart(records: List<NpcLifeRecordEntity>) {
    if (records.isEmpty()) return
    
    val recentRecords = records.takeLast(14)
    
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        backgroundColor = Color.White.copy(alpha = 0.8f)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("⏰", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("时间分配", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2C3E50))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(modifier = Modifier.height(150.dp)) {
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    drawTimeDistributionBars(recentRecords)
                }
            }
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                LegendItem(Color(0xFFE74C3C), "工作")
                LegendItem(Color(0xFF3498DB), "休息")
                LegendItem(Color(0xFF2ECC71), "休闲")
            }
        }
    }
}

fun DrawScope.drawTimeDistributionBars(records: List<NpcLifeRecordEntity>) {
    val width = size.width
    val height = size.height
    val barWidth = width / records.size * 0.7f
    val spacing = width / records.size * 0.3f
    
    records.forEachIndexed { index, record ->
        val x = index * (barWidth + spacing) + spacing / 2
        val workHeight = (record.workMinutes.toFloat() / 1440f) * height * 0.8f
        val restHeight = (record.restMinutes.toFloat() / 1440f) * height * 0.8f
        val leisureHeight = (record.leisureMinutes.toFloat() / 1440f) * height * 0.8f
        
        drawRect(
            color = Color(0xFFE74C3C),
            topLeft = androidx.compose.ui.geometry.Offset(x, height - workHeight),
            size = androidx.compose.ui.geometry.Size(barWidth, workHeight)
        )
        
        drawRect(
            color = Color(0xFF3498DB),
            topLeft = androidx.compose.ui.geometry.Offset(x, height - workHeight - restHeight),
            size = androidx.compose.ui.geometry.Size(barWidth, restHeight)
        )
        
        drawRect(
            color = Color(0xFF2ECC71),
            topLeft = androidx.compose.ui.geometry.Offset(x, height - workHeight - restHeight - leisureHeight),
            size = androidx.compose.ui.geometry.Size(barWidth, leisureHeight)
        )
    }
}

@Composable
fun IncomeChart(records: List<NpcLifeRecordEntity>) {
    if (records.isEmpty()) return
    
    val recentRecords = records.takeLast(14)
    
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        backgroundColor = Color.White.copy(alpha = 0.8f)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("💰", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("收入轨迹", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2C3E50))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(modifier = Modifier.height(150.dp)) {
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    drawIncomeLines(recentRecords)
                }
            }
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                LegendItem(Color(0xFFE74C3C), "劳动收入")
                LegendItem(Color(0xFF8E44AD), "复利收入")
            }
        }
    }
}

fun DrawScope.drawIncomeLines(records: List<NpcLifeRecordEntity>) {
    val width = size.width
    val height = size.height
    val maxIncome = (records.maxOf { maxOf(it.dayLaborIncome, it.dayCompoundIncome) } * 1.2).toFloat()
    
    val laborPath = Path().apply {
        records.forEachIndexed { index, record ->
            val x = (index.toFloat() / (records.size - 1)) * width
            val y = height - (record.dayLaborIncome.toFloat() / maxIncome) * height * 0.8f
            if (index == 0) moveTo(x, y) else lineTo(x, y)
        }
    }
    
    val compoundPath = Path().apply {
        records.forEachIndexed { index, record ->
            val x = (index.toFloat() / (records.size - 1)) * width
            val y = height - (record.dayCompoundIncome.toFloat() / maxIncome) * height * 0.8f
            if (index == 0) moveTo(x, y) else lineTo(x, y)
        }
    }
    
    drawPath(laborPath, color = Color(0xFFE74C3C), style = Stroke(width = 3f))
    drawPath(compoundPath, color = Color(0xFF8E44AD), style = Stroke(width = 3f))
    
    for (i in 0 until records.size step 3) {
        val x = (i.toFloat() / (records.size - 1)) * width
        drawLine(
            color = Color(0xFFECF0F1),
            start = androidx.compose.ui.geometry.Offset(x, height * 0.2f),
            end = androidx.compose.ui.geometry.Offset(x, height)
        )
    }
}

@Composable
fun EmotionChart(records: List<NpcLifeRecordEntity>) {
    if (records.isEmpty()) return
    
    val recentRecords = records.takeLast(14)
    
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        backgroundColor = Color.White.copy(alpha = 0.8f)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("❤️", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("情绪变化", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2C3E50))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(modifier = Modifier.height(150.dp)) {
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    drawEmotionLines(recentRecords)
                }
            }
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                LegendItem(Color(0xFF27AE60), "快乐")
                LegendItem(Color(0xFFE74C3C), "焦虑")
                LegendItem(Color(0xFF8E44AD), "创伤")
            }
        }
    }
}

fun DrawScope.drawEmotionLines(records: List<NpcLifeRecordEntity>) {
    val width = size.width
    val height = size.height
    
    val happinessPath = Path().apply {
        records.forEachIndexed { index, record ->
            val x = (index.toFloat() / (records.size - 1)) * width
            val y = height - (record.happiness.toFloat() / 100f) * height * 0.8f
            if (index == 0) moveTo(x, y) else lineTo(x, y)
        }
    }
    
    val anxietyPath = Path().apply {
        records.forEachIndexed { index, record ->
            val x = (index.toFloat() / (records.size - 1)) * width
            val y = height - (record.anxiety.toFloat() / 100f) * height * 0.8f
            if (index == 0) moveTo(x, y) else lineTo(x, y)
        }
    }
    
    val traumaPath = Path().apply {
        records.forEachIndexed { index, record ->
            val x = (index.toFloat() / (records.size - 1)) * width
            val y = height - (record.trauma.toFloat() / 100f) * height * 0.8f
            if (index == 0) moveTo(x, y) else lineTo(x, y)
        }
    }
    
    drawPath(happinessPath, color = Color(0xFF27AE60), style = Stroke(width = 3f))
    drawPath(anxietyPath, color = Color(0xFFE74C3C), style = Stroke(width = 3f))
    drawPath(traumaPath, color = Color(0xFF8E44AD), style = Stroke(width = 3f))
    
    for (i in 0..4) {
        val y = height * 0.2f + (i * height * 0.8f / 4)
        drawLine(
            color = Color(0xFFECF0F1),
            start = androidx.compose.ui.geometry.Offset(0f, y),
            end = androidx.compose.ui.geometry.Offset(width, y)
        )
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp)) {
        Box(Modifier.size(8.dp).background(color, androidx.compose.foundation.shape.RoundedCornerShape(2.dp)))
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, fontSize = 11.sp, color = Color(0xFF95A5A6))
    }
}

@Composable
fun TimelineList(
    records: List<NpcLifeRecordEntity>,
    nightStates: List<NightStateEntity>,
    selectedRecord: NpcLifeRecordEntity?,
    onSelect: (NpcLifeRecordEntity) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        elevation = 0.dp,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        backgroundColor = Color.White.copy(alpha = 0.8f)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📜", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("时光回顾", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2C3E50))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(modifier = Modifier.height(300.dp)) {
                items(records.sortedByDescending { it.gameDay }) { record ->
                    val nightState = nightStates.find { it.gameDay == record.gameDay }
                    
                    TimelineItem(
                        record = record,
                        nightState = nightState,
                        isSelected = selectedRecord?.id == record.id,
                        onSelect = { onSelect(record) }
                    )
                }
            }
        }
    }
}

@Composable
fun TimelineItem(
    record: NpcLifeRecordEntity,
    nightState: NightStateEntity?,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onSelect() },
        elevation = 0.dp,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        backgroundColor = if (isSelected) Color(0xFFE3F2FD).copy(alpha = 0.8f) else Color.White.copy(alpha = 0.8f)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("第${record.gameDay}天", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2C3E50))
                Text(
                    when {
                        record.happiness >= 70 -> "😊"
                        record.happiness >= 40 -> "😌"
                        else -> "😔"
                    },
                    fontSize = 20.sp
                )
            }
            
            AnimatedVisibility(
                visible = isSelected,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Divider(color = Color(0xFFECF0F1), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        MiniStatItem("工时", "${record.workMinutes}min", Color(0xFFE74C3C))
                        MiniStatItem("快乐", "${record.happiness.toInt()}%", Color(0xFF27AE60))
                        MiniStatItem("焦虑", "${record.anxiety.toInt()}%", Color(0xFFE74C3C))
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        MiniStatItem("劳动收入", "¥${record.dayLaborIncome.toInt()}", Color(0xFF3498DB))
                        MiniStatItem("复利收入", "¥${record.dayCompoundIncome.toInt()}", Color(0xFF8E44AD))
                        MiniStatItem("资产", "¥${record.assets.toInt()}", Color(0xFF27AE60))
                    }
                    
                    if (nightState != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Divider(color = Color(0xFFECF0F1), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                when {
                                    nightState.sleepStatus == NightStateEntity.SLEEP_STATUS_INSOMNIA -> "😰"
                                    nightState.sleepStatus == NightStateEntity.SLEEP_STATUS_DEEP_SLEEP -> "💤"
                                    else -> "😴"
                                },
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "昨夜：${nightState.nightMonoText}",
                                    fontSize = 13.sp,
                                    color = Color(0xFF7F8C8D),
                                    lineHeight = 18.sp,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                                if (nightState.hasDream && nightState.dreamContent.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "${nightState.dreamEmoji} ${nightState.dreamContent}",
                                        fontSize = 12.sp,
                                        color = Color(0xFF95A5A6)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MiniStatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = color)
        Text(label, fontSize = 10.sp, color = Color(0xFF95A5A6))
    }
}
