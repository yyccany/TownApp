package com.example.townapp.data.extension

import com.example.townapp.data.model.*
import java.util.concurrent.ConcurrentHashMap

object AnonymousLifestyleSquare {
    private val towns = ConcurrentHashMap<String, AnonymousTown>()
    private val viewCounts = ConcurrentHashMap<String, Int>()

    fun publishTown(buildingStats: List<BuildingCategoryStat>, timeAllocation: List<TimeAllocationItem>, moneyAllocation: List<MoneyAllocationItem>): AnonymousTown {
        val anonymousId = "anon_${System.currentTimeMillis()}"
        val dominantTheme = timeAllocation.maxByOrNull { it.percentage }?.let { "${it.activity}型" } ?: "均衡型"
        val tagLine = "这位匿名用户是${dominantTheme}"
        val town = AnonymousTown(anonymousId, System.currentTimeMillis(), buildingStats, timeAllocation, moneyAllocation, tagLine, dominantTheme, 0)
        towns[anonymousId] = town
        return town
    }

    fun browseSquare(page: Int = 0, pageSize: Int = 10): List<AnonymousTown> = towns.values.sortedByDescending { it.createTime }.drop(page * pageSize).take(pageSize).onEach { incrementViewCount(it.anonymousId) }

    fun browseByTheme(theme: String, page: Int = 0, pageSize: Int = 10): List<AnonymousTown> = towns.values.filter { it.dominantLifeTheme.contains(theme) }.sortedByDescending { it.createTime }.drop(page * pageSize).take(pageSize)

    fun getLifestyleStats(): LifestyleStats {
        val themes = towns.values.groupBy { it.dominantLifeTheme }.mapValues { it.value.size }
        return LifestyleStats(towns.size, themes, emptyMap(), themes.maxByOrNull { it.value }?.key ?: "未知")
    }

    private fun incrementViewCount(id: String) { viewCounts.merge(id, 1) { old, _ -> old + 1 } }
    fun getTownCount(): Int = towns.size
    fun clearAll() { towns.clear(); viewCounts.clear() }
}

data class LifestyleStats(val totalTowns: Int, val themeDistribution: Map<String, Int>, val averageTimeAllocation: Map<String, Double>, val mostPopularTheme: String)