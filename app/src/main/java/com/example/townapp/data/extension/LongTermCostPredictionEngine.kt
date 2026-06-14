package com.example.townapp.data.extension

import com.example.townapp.data.model.*
import com.example.townapp.performance.ThreadPoolManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

object LongTermCostPredictionEngine {
    private val predictionTemplates = listOf(
        LongTermCostPrediction(-1, "买车（20万）", 10, 410000.0, 41000.0, 4.7, emptyList(), 410000.0, HealthCostProjection(-0.1, 5000.0, 2), EnvironmentalCostProjection(50.0, 2500, 1.5)),
        LongTermCostPrediction(-2, "每天一包烟", 20, 250000.0, 0.0, 0.0, emptyList(), 300000.0, HealthCostProjection(-2.0, 100000.0, 4), EnvironmentalCostProjection(40.0, 2000, 0.5)),
        LongTermCostPrediction(-3, "买房（300万）", 30, 5880000.0, 87600.0, 10.0, emptyList(), 5880000.0, HealthCostProjection(-0.2, 10000.0, 2), EnvironmentalCostProjection(100.0, 5000, 3.0))
    )

    fun getPresetPredictions(): List<LongTermCostPrediction> = predictionTemplates

    suspend fun predictLongTermCost(itemName: String, initialPrice: Double, yearlyMaintenance: Double, years: Int, healthImpactPerYear: Double, carbonPerYear: Double): LongTermCostPrediction = withContext(ThreadPoolManager.calculationDispatcher) {
        val totalMoney = initialPrice + yearlyMaintenance * years
        LongTermCostPrediction(-1, itemName, years, totalMoney, totalMoney / 100, totalMoney / 876000.0, emptyList(), totalMoney, HealthCostProjection(healthImpactPerYear * years, totalMoney * 0.1, 2), EnvironmentalCostProjection(carbonPerYear * years, (carbonPerYear * years * 50).toInt(), carbonPerYear * years * 0.3))
    }
}

object CarbonCostDataManager {
    val presetCarbonData = listOf(
        CarbonCostInfo(1, "水泥", 0.8, 500.0, 2.0, false, false, 2),
        CarbonCostInfo(2, "钢材", 1.8, 1000.0, 3.0, true, false, 1),
        CarbonCostInfo(3, "木材", 0.1, 200.0, 5.0, true, true, 1),
        CarbonCostInfo(4, "塑料", 2.5, 1500.0, 1.0, false, false, 3),
        CarbonCostInfo(5, "玻璃", 0.9, 800.0, 1.0, true, false, 1),
        CarbonCostInfo(6, "棉花", 0.3, 10000.0, 10.0, true, true, 1),
        CarbonCostInfo(7, "石油制品", 3.0, 2000.0, 2.0, false, false, 4),
        CarbonCostInfo(8, "食品（肉）", 5.0, 15000.0, 50.0, false, true, 1),
        CarbonCostInfo(9, "食品（蔬菜）", 0.5, 300.0, 2.0, false, true, 1),
        CarbonCostInfo(10, "电子产品", 20.0, 5000.0, 0.5, true, false, 3),
        CarbonCostInfo(11, "纸制品", 0.6, 500.0, 3.0, true, true, 1),
        CarbonCostInfo(12, "皮革", 15.0, 12000.0, 30.0, false, true, 2)
    )

    private val customCarbonData = ConcurrentHashMap<Int, CarbonCostInfo>()

    fun getCarbonInfo(materialId: Int): CarbonCostInfo? = customCarbonData[materialId] ?: presetCarbonData.find { it.materialId == materialId }
    fun addCustomCarbonInfo(info: CarbonCostInfo) { customCarbonData[info.materialId] = info }
    fun getAllCarbonData(): List<CarbonCostInfo> = presetCarbonData + customCarbonData.values
}