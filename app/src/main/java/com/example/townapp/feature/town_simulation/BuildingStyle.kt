package com.example.townapp.feature.town_simulation

/**
 * 建筑风格分级。
 *
 * 用于 UI 层面的视觉区分（入场动画强度、光晕颜色、粒子密度），
 * 不代表任何价值判断，仅反映行为在客观维度上的量化差异。
 */
enum class BuildingStyle(val order: Int) {
    HIGH(7),
    MODERATE_HIGH(6),
    MODERATE(5),
    AVERAGE(4),
    BELOW_AVERAGE(3),
    LOW(2),
    VERY_LOW(1),
    MINIMAL(0);

    val haloColorHex: String
        get() = when (this) {
            HIGH, MODERATE_HIGH, MODERATE -> "#FFD966"
            AVERAGE, BELOW_AVERAGE -> "#FFFFFF"
            LOW, VERY_LOW -> "#A0A0A0"
            MINIMAL -> "#3A1C1C"
        }
}

object BuildingStyleClassifier {

    /**
     * 根据行为累计的数值（单次或累计）给出建筑风格分级。
     * 数值高低仅反映量化差异，不代表优劣。
     */
    fun classify(value: Double): BuildingStyle {
        return when {
            value >= 100.0 -> BuildingStyle.HIGH
            value >= 50.0 -> BuildingStyle.MODERATE_HIGH
            value >= 20.0 -> BuildingStyle.MODERATE
            value >= 10.0 -> BuildingStyle.AVERAGE
            value >= 5.0 -> BuildingStyle.BELOW_AVERAGE
            value >= 0.1 -> BuildingStyle.LOW
            value >= 0.0 -> BuildingStyle.VERY_LOW
            else -> BuildingStyle.MINIMAL
        }
    }

    /**
     * 根据分级返回对应的入场动画延迟系数（毫秒）。
     * 数值越高的样式，动画越柔和绵长。
     */
    fun animationDurationMs(style: BuildingStyle): Int {
        return when (style) {
            BuildingStyle.HIGH -> 800
            BuildingStyle.MODERATE_HIGH -> 600
            BuildingStyle.MODERATE -> 500
            BuildingStyle.AVERAGE -> 400
            BuildingStyle.BELOW_AVERAGE -> 350
            BuildingStyle.LOW -> 300
            BuildingStyle.VERY_LOW -> 250
            BuildingStyle.MINIMAL -> 200
        }
    }
}