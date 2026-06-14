package com.example.townapp.data.extension

import com.example.townapp.data.model.*
import com.example.townapp.performance.ThreadPoolManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ParallelTimelineEngine {
    val presetChoices = listOf(
        LifeChoice(id="preset_buy_house", name="买房（300万）", description="购买一套300万的房产", initialCost=3000000.0, lifeTimeCost=87600.0, monthlyRecurringCost=8000.0, attentionOccupation=0.3, maintenanceCost10Year=200000.0, opportunityCost=500000.0, emotionalBenefit=60.0, healthImpact=-5.0, socialImpact=20.0, carbonCost=100.0, riskLevel=2, tag="住房"),
        LifeChoice(id="preset_travel_world", name="环游世界（100万）", description="花100万环游世界", initialCost=1000000.0, lifeTimeCost=26280.0, monthlyRecurringCost=0.0, attentionOccupation=0.1, maintenanceCost10Year=0.0, opportunityCost=800000.0, emotionalBenefit=95.0, healthImpact=15.0, socialImpact=40.0, carbonCost=30.0, riskLevel=1, tag="旅行"),
        LifeChoice(id="preset_buy_car", name="买车（20万）", description="购买一辆20万的车", initialCost=200000.0, lifeTimeCost=8000.0, monthlyRecurringCost=2000.0, attentionOccupation=0.15, maintenanceCost10Year=210000.0, opportunityCost=100000.0, emotionalBenefit=40.0, healthImpact=-3.0, socialImpact=15.0, carbonCost=5.0, riskLevel=2, tag="出行"),
        LifeChoice(id="preset_public_transport", name="公共交通", description="持续使用公共交通", initialCost=0.0, lifeTimeCost=3000.0, monthlyRecurringCost=500.0, attentionOccupation=0.05, maintenanceCost10Year=60000.0, opportunityCost=0.0, emotionalBenefit=10.0, healthImpact=5.0, socialImpact=0.0, carbonCost=0.5, riskLevel=1, tag="出行"),
        LifeChoice(id="preset_smoking", name="每天一包烟", description="每天抽一包烟持续20年", initialCost=0.0, lifeTimeCost=0.0, monthlyRecurringCost=750.0, attentionOccupation=0.05, maintenanceCost10Year=90000.0, opportunityCost=50000.0, emotionalBenefit=5.0, healthImpact=-40.0, socialImpact=-10.0, carbonCost=2.0, riskLevel=4, tag="健康"),
        LifeChoice(id="preset_fitness", name="健身投资", description="每月投入健身持续10年", initialCost=0.0, lifeTimeCost=3000.0, monthlyRecurringCost=500.0, attentionOccupation=0.1, maintenanceCost10Year=60000.0, opportunityCost=0.0, emotionalBenefit=80.0, healthImpact=50.0, socialImpact=25.0, carbonCost=0.3, riskLevel=1, tag="健康")
    )

    suspend fun compareChoices(choiceA: LifeChoice, choiceB: LifeChoice): ChoiceComparison = withContext(ThreadPoolManager.calculationDispatcher) {
        val items = listOf(
            ComparisonItem("初始金钱代价", formatMoney(choiceA.initialCost), formatMoney(choiceB.initialCost), formatDiff(choiceA.initialCost, choiceB.initialCost)),
            ComparisonItem("生命时长代价", "${choiceA.lifeTimeCost.toInt()}小时", "${choiceB.lifeTimeCost.toInt()}小时", formatTimeDiff(choiceA.lifeTimeCost, choiceB.lifeTimeCost)),
            ComparisonItem("每月持续支出", formatMoney(choiceA.monthlyRecurringCost), formatMoney(choiceB.monthlyRecurringCost), formatDiff(choiceA.monthlyRecurringCost, choiceB.monthlyRecurringCost)),
            ComparisonItem("注意力占用", "${(choiceA.attentionOccupation*100).toInt()}%", "${(choiceB.attentionOccupation*100).toInt()}%", formatDiff(choiceA.attentionOccupation*100, choiceB.attentionOccupation*100)),
            ComparisonItem("精神收益", "${choiceA.emotionalBenefit.toInt()}分", "${choiceB.emotionalBenefit.toInt()}分", formatDiff(choiceA.emotionalBenefit, choiceB.emotionalBenefit)),
            ComparisonItem("健康影响", formatHealth(choiceA.healthImpact), formatHealth(choiceB.healthImpact), formatDiff(choiceA.healthImpact, choiceB.healthImpact)),
            ComparisonItem("环境代价(碳排放)", "${choiceA.carbonCost}吨", "${choiceB.carbonCost}吨", formatDiff(choiceA.carbonCost, choiceB.carbonCost))
        )
        ChoiceComparison(choiceA, choiceB, items, "${choiceA.name} 相比 ${choiceB.name}")
    }

    private fun formatMoney(amount: Double): String = if(amount>=10000) "${String.format("%.0f",amount/10000)}万" else "${amount.toInt()}元"
    private fun formatDiff(a: Double, b: Double): String { val diff = a-b; return when { diff>0 -> "A多${String.format("%.1f",diff)}"; diff<0 -> "B多${String.format("%.1f",-diff)}"; else -> "相同" }}
    private fun formatTimeDiff(a: Double, b: Double): String { val diff = a-b; return when { diff>0 -> "A多${diff.toInt()}小时"; diff<0 -> "B多${(-diff).toInt()}小时"; else -> "相同" }}
    private fun formatHealth(impact: Double): String = when { impact>20 -> "显著积极"; impact>0 -> "略微积极"; impact==0.0 -> "无影响"; impact>-20 -> "略微消极"; else -> "显著消极" }
}