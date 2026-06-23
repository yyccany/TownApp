package com.example.townapp.feature.town_simulation

/**
 * 小镇建筑卡片。
 *
 * 聚合 TownBuilding、BehaviorBuildingMapping、BuildingStyle 三者的信息，
 * 专用于 UI 层绘制。不含任何评判性字段。
 */
data class TownBuildingCard(
    val buildingId: String,
    val name: String,
    val module: String,           // 食物 / 服装 / 住房 / 认知
    val style: BuildingStyle,
    val valueDensity: Double,     // 累计数值，用于卡片上的数字
    val totalCost: Double,        // 累计投入（元或小时）
    val totalReturn: Double,      // 累计获得
    val visualDescription: String,
    val cognitiveTip: String,     // 行为对应的中性描述
)

/**
 * 把一条 ActionFeedback + 对应的 TownBuilding 组装成卡片。
 * 只在用户点击建筑、或首次生成建筑时调用。
 */
object BuildingCardFactory {

    data class ActionFeedback(
        val buildingId: String?,
        val valueDensity: Double,
        val awakeningChange: Int
    )

    fun fromFeedback(
        feedback: ActionFeedback,
        amount: Double
    ): TownBuildingCard? {
        val buildingId = feedback.buildingId ?: return null
        val mapping = BehaviorBuildingMapping.findByBuildingId(buildingId) ?: return null
        val townBuilding = BehaviorBuildingMapping.resolveBuilding(mapping) ?: return null

        return TownBuildingCard(
            buildingId = buildingId,
            name = townBuilding.name,
            module = mapping.module,
            style = BuildingStyleClassifier.classify(feedback.valueDensity),
            valueDensity = feedback.valueDensity,
            totalCost = amount,
            totalReturn = feedback.awakeningChange.toDouble(),
            visualDescription = townBuilding.visualEffect,
            cognitiveTip = mapping.cognitiveTip
        )
    }

    /**
     * 仅用建筑 id + 当前累计数值生成卡片（用于"历史建筑"列表）。
     */
    fun fromBuildingId(buildingId: String, cumulativeDensity: Double): TownBuildingCard? {
        val mapping = BehaviorBuildingMapping.findByBuildingId(buildingId) ?: return null
        val townBuilding = BehaviorBuildingMapping.resolveBuilding(mapping) ?: return null
        val style = BuildingStyleClassifier.classify(cumulativeDensity)
        return TownBuildingCard(
            buildingId = buildingId,
            name = townBuilding.name,
            module = mapping.module,
            style = style,
            valueDensity = cumulativeDensity,
            totalCost = 0.0,
            totalReturn = 0.0,
            visualDescription = townBuilding.visualEffect,
            cognitiveTip = mapping.cognitiveTip
        )
    }
}