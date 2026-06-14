package com.example.townapp.data

import com.example.townapp.data.model.WeatherState

/**
 * 四季-面料-鞋袜材质系统
 *
 * 四类核心面料 + 配套鞋袜，绑定温度、湿度核心变量。
 * 精简到只有4种面料，不堆砌复杂工业参数。
 *
 * 核心原则：
 * - 盛夏穿毛料 → 闷热疲惫暴涨，不是"审美问题"，是"身体会不舒服"
 * - 寒冬穿亚麻 → 受寒感冒，不是"不够时髦"，是"真的会冷"
 * - 系统自动推荐适配季节的穿搭，手动错配也会让你穿，但身体会告诉你代价
 *
 * 注意：FabricType 枚举定义在 MaterialIngredientSystem.kt 中，此处复用。
 */

// ============================================
// 鞋袜材质
// ============================================
enum class ShoeMaterial(val label: String) {
    CANVAS("全棉帆布鞋"),    // 透气好，雨天防水差
    LEATHER("皮面羊毛靴"),   // 寒冬御寒最优
    SYNTHETIC("防水运动鞋"), // 雨季最优
    LINEN_SHOE("亚麻薄鞋")   // 夏季顶配
}

enum class SockMaterial(val label: String) {
    COTTON_SOCK("纯棉棉袜"),     // 春秋日常
    WOOL_SOCK("羊毛厚袜"),       // 寒冬御寒
    SYNTHETIC_SOCK("化纤运动袜"),// 雨季防水
    LINEN_SOCK("亚麻薄袜")       // 盛夏顶配
}

// ============================================
// 气候状态
// ============================================
enum class SeasonTemperature(val label: String, val rangeDesc: String) {
    EXTREME_HEAT("酷暑", "≥29°C"),
    SUMMER("夏季常温", "22-28°C"),
    SPRING_AUTUMN("春秋", "10-21°C"),
    WINTER("寒冬", "≤9°C")
}

enum class HumidityLevel(val label: String) {
    DRY("干燥"),
    NORMAL("常规"),
    WET("多雨潮湿")
}

data class ClimateState(
    val temperature: SeasonTemperature,
    val humidity: HumidityLevel,
    val weather: WeatherState
)

// ============================================
// 面料规则引擎
// ============================================
object FabricRules {

    /**
     * 判断面料是否可安全熨烫
     * 纯棉、羊毛可熨烫；化纤、亚麻高温熨烫会损坏
     */
    fun isIronSafe(fabric: FabricType): Boolean = when (fabric) {
        FabricType.COTTON, FabricType.WOOL -> true
        FabricType.POLYESTER, FabricType.LINEN -> false
    }

    /**
     * 判断面料是否适合当前季节
     */
    fun isSuitableForSeason(fabric: FabricType, temp: SeasonTemperature): Boolean {
        return when (temp) {
            SeasonTemperature.EXTREME_HEAT -> fabric in setOf(FabricType.LINEN, FabricType.COTTON)
            SeasonTemperature.SUMMER -> fabric in setOf(FabricType.LINEN, FabricType.COTTON, FabricType.POLYESTER)
            SeasonTemperature.SPRING_AUTUMN -> fabric in setOf(FabricType.COTTON, FabricType.POLYESTER, FabricType.LINEN)
            SeasonTemperature.WINTER -> fabric in setOf(FabricType.WOOL, FabricType.COTTON)
        }
    }

    // ============================================
    // 面料错配惩罚计算
    // ============================================
    data class FabricPenalty(
        val fatigueDelta: Double,       // 疲惫值变化
        val anxietyDelta: Int,          // 焦虑变化
        val diseaseRisk: Double,        // 疾病风险
        val reason: String,             // 原因说明
        val isMismatch: Boolean         // 是否错配
    )

    fun calculatePenalty(
        fabric: FabricType,
        isThick: Boolean,
        climate: ClimateState
    ): FabricPenalty {
        val temp = climate.temperature
        val humidity = climate.humidity

        // 盛夏穿毛料厚套装 → 最严重惩罚
        if (fabric == FabricType.WOOL && temp in setOf(SeasonTemperature.EXTREME_HEAT, SeasonTemperature.SUMMER)) {
            return FabricPenalty(
                fatigueDelta = 0.6, anxietyDelta = 5,
                diseaseRisk = 0.0,
                reason = "盛夏穿毛料厚套装，闷热难耐，疲惫值暴涨",
                isMismatch = true
            )
        }

        // 寒冬穿亚麻 → 受寒
        if (fabric == FabricType.LINEN && temp == SeasonTemperature.WINTER) {
            return FabricPenalty(
                fatigueDelta = 0.4, anxietyDelta = 2,
                diseaseRisk = 0.1,
                reason = "寒冬穿亚麻，防风保暖完全不足，受寒概率上升",
                isMismatch = true
            )
        }

        // 盛夏化纤鞋袜闷汗 → 脚气风险
        if (fabric == FabricType.POLYESTER && temp == SeasonTemperature.EXTREME_HEAT) {
            return FabricPenalty(
                fatigueDelta = 0.2, anxietyDelta = 2,
                diseaseRisk = 0.2,
                reason = "酷暑穿化纤鞋袜全天闷汗，脚气概率+20%",
                isMismatch = true
            )
        }

        // 雨季潮湿 + 纯棉 → 吸水湿气
        if (fabric == FabricType.COTTON && humidity == HumidityLevel.WET) {
            return FabricPenalty(
                fatigueDelta = 0.1, anxietyDelta = 0,
                diseaseRisk = 0.05,
                reason = "雨季纯棉吸水，鞋袜潮湿，脚气风险微升",
                isMismatch = false  // 不算严重错配，只是提醒
            )
        }

        // 盛夏厚纯棉长袜 → 闷脚
        if (fabric == FabricType.COTTON && isThick && temp == SeasonTemperature.EXTREME_HEAT) {
            return FabricPenalty(
                fatigueDelta = 0.2, anxietyDelta = 1,
                diseaseRisk = 0.3,
                reason = "盛夏厚纯棉长袜闷脚，脚气风险+30%",
                isMismatch = true
            )
        }

        // 正常匹配
        return FabricPenalty(
            fatigueDelta = 0.0, anxietyDelta = 0,
            diseaseRisk = 0.0, reason = "当前穿搭适配季节，舒适清爽",
            isMismatch = false
        )
    }

    // ============================================
    // 季节适配面料排序
    // ============================================
    fun getSeasonRankedFabrics(climate: ClimateState): List<FabricType> {
        val temp = climate.temperature
        val humidity = climate.humidity

        return when {
            // 雨季优先化纤防水
            humidity == HumidityLevel.WET -> listOf(FabricType.POLYESTER, FabricType.COTTON, FabricType.LINEN, FabricType.WOOL)
            // 酷暑优先亚麻
            temp == SeasonTemperature.EXTREME_HEAT -> listOf(FabricType.LINEN, FabricType.COTTON, FabricType.POLYESTER, FabricType.WOOL)
            // 夏季优先亚麻+纯棉
            temp == SeasonTemperature.SUMMER -> listOf(FabricType.LINEN, FabricType.COTTON, FabricType.POLYESTER, FabricType.WOOL)
            // 春秋优先纯棉+化纤
            temp == SeasonTemperature.SPRING_AUTUMN -> listOf(FabricType.COTTON, FabricType.POLYESTER, FabricType.LINEN, FabricType.WOOL)
            // 寒冬优先毛料
            temp == SeasonTemperature.WINTER -> listOf(FabricType.WOOL, FabricType.COTTON, FabricType.POLYESTER, FabricType.LINEN)
            else -> listOf(FabricType.COTTON, FabricType.POLYESTER, FabricType.LINEN, FabricType.WOOL)
        }
    }

    // ============================================
    // 气候推导
    // ============================================
    fun deriveClimate(month: Int, weather: WeatherState): ClimateState {
        val temp = when (month) {
            6, 7, 8 -> SeasonTemperature.EXTREME_HEAT
            5, 9 -> SeasonTemperature.SUMMER
            3, 4, 10, 11 -> SeasonTemperature.SPRING_AUTUMN
            else -> SeasonTemperature.WINTER
        }
        val humidity = when (weather) {
            WeatherState.RAINY, WeatherState.STORM -> HumidityLevel.WET
            WeatherState.SUNNY -> HumidityLevel.DRY
            else -> HumidityLevel.NORMAL
        }
        return ClimateState(temp, humidity, weather)
    }
}