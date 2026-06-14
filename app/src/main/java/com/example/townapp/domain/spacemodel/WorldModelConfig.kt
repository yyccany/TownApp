package com.example.townapp.domain.spacemodel

import com.example.townapp.core.state.WorldType

/**
 * 世界模型配置
 *
 * 四大社会模型：都市 / 小城 / 校园 / 漂泊
 * 每个模型独立配置租金、薪资、通勤、物价系数、压力系数
 */
data class WorldModelConfig(
    val type: WorldType,
    val name: String,
    val rent: Double,              // 月租
    val salary: Double,            // 月薪
    val commuteMinutes: Int,       // 单程通勤时间
    val priceIndex: Double = 1.0,  // 物价系数（影响消费）
    val pressureIndex: Double = 1.0 // 社会压力系数
)

/**
 * 世界模型配置仓库
 */
object WorldModelRepository {

    private val configs = mapOf(
        WorldType.URBAN to WorldModelConfig(
            type = WorldType.URBAN,
            name = "都市",
            rent = 3000.0,
            salary = 8000.0,
            commuteMinutes = 45,
            priceIndex = 1.5,
            pressureIndex = 1.4
        ),
        WorldType.SMALL_TOWN to WorldModelConfig(
            type = WorldType.SMALL_TOWN,
            name = "小城",
            rent = 800.0,
            salary = 3500.0,
            commuteMinutes = 15,
            priceIndex = 0.8,
            pressureIndex = 0.6
        ),
        WorldType.CAMPUS to WorldModelConfig(
            type = WorldType.CAMPUS,
            name = "校园",
            rent = 500.0,
            salary = 0.0,
            commuteMinutes = 10,
            priceIndex = 0.6,
            pressureIndex = 0.4
        ),
        WorldType.WANDERER to WorldModelConfig(
            type = WorldType.WANDERER,
            name = "漂泊",
            rent = 1500.0,
            salary = 4000.0,
            commuteMinutes = 30,
            priceIndex = 1.0,
            pressureIndex = 0.9
        )
    )

    fun getConfig(type: WorldType): WorldModelConfig =
        configs[type] ?: configs[WorldType.URBAN]!!

    fun getAllConfigs(): List<WorldModelConfig> = configs.values.toList()
}
