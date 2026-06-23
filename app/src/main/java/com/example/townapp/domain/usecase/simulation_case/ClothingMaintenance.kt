package com.example.townapp.domain

import com.example.townapp.data.ClimateState
import com.example.townapp.data.FabricType
import com.example.townapp.data.SeasonTemperature
import kotlin.random.Random

/**
 * 衣物维护与保养系统
 *
 * 熨烫、面料折旧、换季提醒——全部后台轻量化处理。
 * 只有衣物破损时弹窗提醒，其余静默结算。
 *
 * 核心原则：
 * - 熨烫是可选的居家操作，不是强制任务
 * - 衣物会自然折旧，换季不及时更换会不舒服
 * - 不设置繁杂洗护步骤，按月静默结算
 */

object ClothingMaintenance {

    // ============================================
    // 熨烫规则
    // ============================================
    data class IroningResult(
        val success: Boolean,
        val appearanceBoost: Double,
        val damageRisk: Double,
        val message: String
    )

    /**
     * 执行熨烫操作
     *
     * @param fabricType 面料类型
     * @return 熨烫结果
     */
    fun ironClothing(fabricType: FabricType): IroningResult {
        return when (fabricType) {
            FabricType.COTTON, FabricType.WOOL -> {
                // 可熨烫：小幅提升颜值
                IroningResult(
                    success = true,
                    appearanceBoost = 0.1,
                    damageRisk = 0.0,
                    message = "熨烫完毕，${fabricType.label}衣物平整如新，颜值小幅提升。"
                )
            }
            FabricType.POLYESTER, FabricType.LINEN -> {
                // 不可熨烫：高温损坏面料
                val damaged = Random.nextDouble() < 0.5  // 50%概率损坏
                if (damaged) {
                    IroningResult(
                        success = false,
                        appearanceBoost = -0.3,
                        damageRisk = 1.0,
                        message = "${fabricType.label}面料不耐高温，熨烫后纤维受损。这套穿搭的颜值下降了，需要修补或更换。"
                    )
                } else {
                    IroningResult(
                        success = true,
                        appearanceBoost = 0.0,
                        damageRisk = 0.3,
                        message = "侥幸没烫坏，但${fabricType.label}不推荐熨烫——下次别冒险了。"
                    )
                }
            }
        }
    }

    // ============================================
    // 衣物折旧
    // ============================================
    data class DepreciationResult(
        val wearLevel: Double,          // 当前磨损度 0.0-1.0（1.0=破损）
        val needsRepair: Boolean,       // 是否需要修补
        val needsReplace: Boolean,      // 是否需要更换
        val repairCost: Double,         // 修补费用
        val replaceCost: Double,        // 更换费用
        val appearancePenalty: Double,  // 颜值惩罚
        val message: String
    )

    /**
     * 月度结算衣物折旧
     *
     * @param fabricType 面料类型
     * @param currentWear 当前磨损度
     * @param climate 当前气候
     * @return 折旧结果
     */
    fun monthlyDepreciation(
        fabricType: FabricType,
        currentWear: Double,
        climate: ClimateState
    ): DepreciationResult {
        // 基础折旧率
        var baseDepreciation = 0.02

        // 潮湿环境加速折旧
        if (climate.humidity == com.example.townapp.data.HumidityLevel.WET) {
            baseDepreciation += 0.03
        }

        // 羊毛、纯棉在潮湿环境更容易发霉
        if (fabricType in setOf(FabricType.COTTON, FabricType.WOOL)
            && climate.humidity == com.example.townapp.data.HumidityLevel.WET
        ) {
            baseDepreciation += 0.02
        }

        val newWear = (currentWear + baseDepreciation).coerceAtMost(1.0)

        val needsRepair = newWear > 0.6
        val needsReplace = newWear >= 0.95
        val appearancePenalty = when {
            newWear > 0.8 -> -0.3
            newWear > 0.6 -> -0.15
            newWear > 0.4 -> -0.05
            else -> 0.0
        }

        val repairCost = when (fabricType) {
            FabricType.WOOL -> 80.0
            FabricType.COTTON -> 30.0
            FabricType.POLYESTER -> 20.0
            FabricType.LINEN -> 40.0
        }

        val replaceCost = when (fabricType) {
            FabricType.WOOL -> 500.0
            FabricType.COTTON -> 100.0
            FabricType.POLYESTER -> 80.0
            FabricType.LINEN -> 120.0
        }

        val message = when {
            needsReplace -> "这件${fabricType.label}衣物已经破损严重，影响外观。建议更换新衣（¥${replaceCost}）。"
            needsRepair -> "这件${fabricType.label}衣物出现磨损，可以考虑修补（¥${repairCost}）。"
            newWear > 0.4 -> "衣物有些许穿着痕迹，但不影响使用。"
            else -> "衣物状态良好。"
        }

        return DepreciationResult(
            wearLevel = newWear,
            needsRepair = needsRepair,
            needsReplace = needsReplace,
            repairCost = repairCost,
            replaceCost = replaceCost,
            appearancePenalty = appearancePenalty,
            message = message
        )
    }

    // ============================================
    // 换季提醒
    // ============================================
    data class SeasonalTransitionResult(
        val isTransition: Boolean,          // 是否换季月份
        val newSeason: SeasonTemperature,
        val outfitsNeedSwap: Boolean,       // 是否需要更换穿搭
        val coldRisk: Double,               // 换季未及时更换→受寒风险
        val heatRisk: Double,               // 换季未及时更换→闷热风险
        val message: String
    )

    /**
     * 换季月检查
     *
     * 换季月份：3月(春)、6月(夏)、9月(秋)、12月(冬)
     * 换季初期穿着未更换的衣物，会有短期不适
     */
    fun checkSeasonalTransition(
        month: Int,
        currentFabric: FabricType,
        isThick: Boolean
    ): SeasonalTransitionResult {
        val transitionMonths = setOf(3, 6, 9, 12)
        val isTransition = month in transitionMonths

        val newSeason = when (month) {
            in 3..5 -> SeasonTemperature.SPRING_AUTUMN
            in 6..8 -> SeasonTemperature.EXTREME_HEAT
            in 9..11 -> SeasonTemperature.SPRING_AUTUMN
            else -> SeasonTemperature.WINTER
        }

        val outfitsNeedSwap = !com.example.townapp.data.FabricRules.isSuitableForSeason(currentFabric, newSeason)

        val coldRisk = if (newSeason == SeasonTemperature.WINTER && outfitsNeedSwap) 0.15 else 0.0
        val heatRisk = if (newSeason == SeasonTemperature.EXTREME_HEAT && outfitsNeedSwap) 0.2 else 0.0

        val message = when {
            isTransition && newSeason == SeasonTemperature.WINTER && outfitsNeedSwap ->
                "换季了。寒冬已至，你身上的${currentFabric.label}衣物不够保暖。\n尽快更换加厚毛料套装，否则受寒风险上升。"
            isTransition && newSeason == SeasonTemperature.EXTREME_HEAT && outfitsNeedSwap ->
                "换季了。酷暑来临，你身上的${currentFabric.label}衣物太厚太闷。\n尽快更换亚麻或薄纯棉套装，否则闷热疲惫暴涨。"
            isTransition && outfitsNeedSwap ->
                "换季了。你身上的${currentFabric.label}衣物不太适合${newSeason.label}。\n可以考虑更换应季穿搭。"
            isTransition ->
                "换季了。${newSeason.label}已至，你身上的穿搭正好合适。"
            else -> ""
        }

        return SeasonalTransitionResult(
            isTransition = isTransition,
            newSeason = newSeason,
            outfitsNeedSwap = outfitsNeedSwap,
            coldRisk = coldRisk,
            heatRisk = heatRisk,
            message = message
        )
    }
}