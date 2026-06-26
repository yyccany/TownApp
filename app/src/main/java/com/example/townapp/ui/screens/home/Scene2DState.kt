package com.example.townapp.ui.screens.home

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

enum class BirthIdentity(
    val id: String,
    val displayName: String,
    val description: String,
    val spawnPosition: Offset,
    val initialConsumptionBias: Int,
    val playerColor: Color
) {
    OFFICE_WORKER(
        id = "office_worker",
        displayName = "普通上班族",
        description = "朝九晚五，日子踏实安稳。家离公司近，买菜方便，体力充沛。",
        spawnPosition = Offset(300f, 310f),
        initialConsumptionBias = 65,
        playerColor = Color(0xFF6B8E6B)
    ),
    MEDIA_FREELANCER(
        id = "media_freelancer",
        displayName = "新媒体自由职业",
        description = "社交广泛，穿搭讲究。住在商圈附近，认识很多人，但身体容易累。",
        spawnPosition = Offset(600f, 460f),
        initialConsumptionBias = 35,
        playerColor = Color(0xFF9B6B9B)
    ),
    FRESH_GRADUATE(
        id = "fresh_graduate",
        displayName = "应届毕业生",
        description = "一切刚刚开始，未来有无限可能。从镇中心出发，走哪条路由你决定。",
        spawnPosition = Offset(400f, 350f),
        initialConsumptionBias = 50,
        playerColor = Color(0xFF6B8E9B)
    )
}

data class Scene2DState(
    val viewMode: ViewMode = ViewMode.PIXEL_2D,
    val cameraOffset: Offset = Offset.Zero,
    val playerPosition: Offset = BirthIdentity.FRESH_GRADUATE.spawnPosition,
    val birthIdentity: BirthIdentity = BirthIdentity.FRESH_GRADUATE,
    val showIdentitySelector: Boolean = false,
    val currentSceneId: String = "town_center",
    val playerDirection: Direction = Direction.DOWN,
    val isWalking: Boolean = false,
    val npcs: List<NpcScenePosition> = emptyList(),
    val buildings: List<BuildingScenePosition> = defaultBuildings(),
    val cameraScale: Float = 1f,
    val nearbyBuildingId: String? = null,
    val nearbyNpcId: String? = null,
    val interactionMenuOpen: Boolean = false,
    val interactionOptions: List<InteractionOption> = emptyList(),
    val interactionTitle: String = ""
)

data class InteractionOption(
    val id: String,
    val label: String,
    val consumptionTendency: Int? = null
)

enum class ViewMode {
    TEXT_NARRATIVE,
    PIXEL_2D
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

data class NpcScenePosition(
    val npcId: String,
    val name: String,
    val position: Offset,
    val direction: Direction = Direction.DOWN,
    val emoji: String = "👤",
    val consumptionTendency: Int = 50,
    val isInteractable: Boolean = true,
    val bubbleText: String? = null
)

data class BuildingScenePosition(
    val buildingId: String,
    val name: String,
    val position: Offset,
    val size: Offset = Offset(120f, 100f),
    val buildingType: BuildingType = BuildingType.HOUSE,
    val consumptionStyle: ConsumptionStyle = ConsumptionStyle.NEUTRAL,
    val emoji: String = "🏠",
    val isEnterable: Boolean = true
)

enum class BuildingType {
    HOUSE, SHOP, CLINIC, MARKET, PARK, SCHOOL, WORKPLACE
}

enum class ConsumptionStyle {
    PEOPLE_ORIENTED,
    NEUTRAL,
    VANITY_ORIENTED
}

private fun defaultBuildings(): List<BuildingScenePosition> = listOf(
    BuildingScenePosition(
        buildingId = "player_home",
        name = "家",
        position = Offset(300f, 250f),
        buildingType = BuildingType.HOUSE,
        emoji = "🏡"
    ),
    BuildingScenePosition(
        buildingId = "vegetable_market",
        name = "菜市场",
        position = Offset(550f, 200f),
        buildingType = BuildingType.MARKET,
        consumptionStyle = ConsumptionStyle.PEOPLE_ORIENTED,
        emoji = "🥬"
    ),
    BuildingScenePosition(
        buildingId = "clinic",
        name = "社区诊所",
        position = Offset(150f, 200f),
        buildingType = BuildingType.CLINIC,
        consumptionStyle = ConsumptionStyle.PEOPLE_ORIENTED,
        emoji = "🏥"
    ),
    BuildingScenePosition(
        buildingId = "luxury_shop",
        name = "名牌店",
        position = Offset(600f, 400f),
        buildingType = BuildingType.SHOP,
        consumptionStyle = ConsumptionStyle.VANITY_ORIENTED,
        emoji = "💎"
    ),
    BuildingScenePosition(
        buildingId = "park",
        name = "公园",
        position = Offset(200f, 450f),
        buildingType = BuildingType.PARK,
        size = Offset(150f, 120f),
        emoji = "🌳"
    ),
    BuildingScenePosition(
        buildingId = "workplace",
        name = "上班地点",
        position = Offset(450f, 480f),
        buildingType = BuildingType.WORKPLACE,
        emoji = "🏢"
    )
)

data class MapTile(
    val x: Int,
    val y: Int,
    val tileType: TileType,
    val detailColor: Color = Color.Transparent
)

enum class TileType {
    GRASS, ROAD, PAVEMENT, WATER, FLOWER, TREE
}
