package com.example.townapp.data.model

// 平行时空人生模拟器
data class LifeChoice(
    val id: String, val name: String, val description: String,
    val initialCost: Double, val lifeTimeCost: Double, val monthlyRecurringCost: Double,
    val attentionOccupation: Double, val maintenanceCost10Year: Double, val opportunityCost: Double,
    val emotionalBenefit: Double, val healthImpact: Double, val socialImpact: Double,
    val carbonCost: Double, val riskLevel: Int, val tag: String,
    val detailItems: List<ChoiceDetailItem> = emptyList()
)

data class ChoiceDetailItem(val itemName: String, val costType: String, val costValue: Double, val costUnit: String, val period: String)
data class ChoiceComparison(val choiceA: LifeChoice, val choiceB: LifeChoice, val comparisonItems: List<ComparisonItem>, val summary: String)
data class ComparisonItem(val dimension: String, val valueA: String, val valueB: String, val difference: String, val isNeutral: Boolean = true)

// 匿名生活方式广场
data class AnonymousTown(val anonymousId: String, val createTime: Long, val buildingStats: List<BuildingCategoryStat>, val timeAllocation: List<TimeAllocationItem>, val moneyAllocation: List<MoneyAllocationItem>, val tagLine: String, val dominantLifeTheme: String, val viewCount: Int)
data class BuildingCategoryStat(val category: String, val count: Int, val percentage: Double)
data class TimeAllocationItem(val activity: String, val hours: Double, val percentage: Double)
data class MoneyAllocationItem(val category: String, val amount: Double, val percentage: Double)

// 人生时间线复盘
data class LifeTimelineReview(val period: String, val startDate: Long, val endDate: Long, val totalLifeHours: Double, val totalMoneySpent: Double, val timeCategories: List<TimeCategorySummary>, val moneyCategories: List<MoneyCategorySummary>, val attentionHotspots: List<AttentionHotspot>, val healthTrend: List<HealthTrendPoint>, val emotionTrend: List<EmotionTrendPoint>, val summaryMessage: String)
data class TimeCategorySummary(val category: String, val hours: Double, val percentage: Double, val moneySpent: Double, val trend: String)
data class MoneyCategorySummary(val category: String, val amount: Double, val percentage: Double, val perMonth: Double, val trend: String)
data class AttentionHotspot(val activity: String, val attentionScore: Double, val frequency: Int, val impact: String)
data class HealthTrendPoint(val date: String, val bodyLevel: Int, val emotionLevel: String)
data class EmotionTrendPoint(val date: String, val emotion: String, val intensity: Int)

// 用户共创认知卡片
data class UserCognitiveCard(val cardId: String, val userId: String, val submitTime: Long, val title: String, val content: String, val category: String, val relatedItems: List<Int>, val costRealization: CostRealization?, val reviewStatus: ReviewStatus, val approveTime: Long?, val unlockCount: Int, val rating: Double)
data class CostRealization(val initialExpectation: String, val actualCost: String, val hiddenCost: String, val lesson: String)
enum class ReviewStatus { PENDING, APPROVED, REJECTED }

// 长期代价预测
data class LongTermCostPrediction(val itemId: Int, val itemName: String, val predictionYears: Int, val totalMoneyCost: Double, val equivalentLifeHours: Double, val equivalentWorkYears: Double, val yearlyBreakdown: List<YearlyCostBreakdown>, val cumulativeCost: Double, val healthCostProjection: HealthCostProjection?, val environmentalCost: EnvironmentalCostProjection?)
data class YearlyCostBreakdown(val year: Int, val purchaseCost: Double, val maintenanceCost: Double, val consumableCost: Double, val opportunityCost: Double, val totalCost: Double)
data class HealthCostProjection(val lifeExpectancyImpact: Double, val medicalCostEstimate: Double, val riskLevel: Int)
data class EnvironmentalCostProjection(val totalCarbonTons: Double, val equivalentTrees: Int, val wasteGenerated: Double)

// 环境代价
data class CarbonCostInfo(val materialId: Int, val materialName: String, val carbonPerUnit: Double, val waterUsage: Double, val landUsage: Double, val recyclable: Boolean, val biodegradable: Boolean, val toxicLevel: Int)
data class ProductCarbonFootprint(val productId: Int, val productName: String, val totalCarbonTons: Double, val materialBreakdown: List<CarbonBreakdownItem>, val lifecyclePhases: List<CarbonPhase>, val comparisonLabel: String, val ecoScore: Int)
data class CarbonBreakdownItem(val materialName: String, val quantity: Double, val carbonTons: Double, val percentage: Double)
data class CarbonPhase(val phase: String, val carbonTons: Double, val percentage: Double)

// 扩展LifeCost
data class ExtendedLifeCost(val originalCost: Double, val carbonCost: Double, val healthCost10Year: Double, val attentionCost: Double, val socialCost: Double, val opportunityCost: Double, val totalComprehensiveCost: Double, val costBreakdown: Map<String, Double>)