package com.example.townapp.ui.screens.home

import com.example.townapp.data.asset.GameText

object SceneInteractionConfig {

    data class ActionResponse(
        val eventLog: String,
        val petDialogue: String? = null,
        val scoreDelta: Int = 0
    )

    fun getBuildingOptions(buildingId: String): List<InteractionOption> {
        return buildingOptionMap[buildingId] ?: defaultOptions
    }

    fun getResponse(optionId: String, identity: BirthIdentity, consumptionScore: Int): ActionResponse {
        val reactionKey = when (optionId) {
            "work_start" -> when (identity) {
                BirthIdentity.OFFICE_WORKER -> "work_office"
                BirthIdentity.MEDIA_FREELANCER -> "work_media"
                BirthIdentity.FRESH_GRADUATE -> "work_graduate"
            }
            "chat", "npc_chat", "market_chat_vendor", "clinic_chat_doctor", "work_chat" -> "work_chat"
            "listen", "npc_listen" -> "npc_listen"
            "rest", "home_rest" -> "home_rest"
            "cook", "home_cook" -> "home_cook"
            "buy_fresh", "market_buy_fresh" -> "market_buy_fresh"
            "buy_luxury", "luxury_buy" -> "luxury_buy"
            "decorate_luxury", "home_decor_luxury" -> "home_decor_luxury"
            "buy_supplements", "clinic_buy_supplements" -> "clinic_buy_supplements"
            "window_shop", "luxury_window_shop" -> "luxury_window_shop"
            "just_look", "market_just_look", "leave", "look" -> "market_just_look"
            "luxury_leave" -> "luxury_leave"
            "checkup", "clinic_checkup" -> "clinic_checkup"
            "sit", "park_sit" -> "park_sit"
            "walk", "park_walk" -> "park_walk"
            "feed_cat", "park_feed_cat" -> "park_feed_cat"
            "overtime", "work_overtime" -> "work_overtime"
            else -> optionId
        }

        val reaction = GameText.reaction(reactionKey, consumptionScore)
        return ActionResponse(
            eventLog = reaction.eventLog.ifEmpty { "你做出了选择。" },
            petDialogue = reaction.petDialog.ifEmpty { null },
            scoreDelta = reaction.scoreDelta
        )
    }

    fun getNpcGreeting(
        npcType: NpcType,
        identity: BirthIdentity,
        consumptionScore: Int
    ): String {
        val typeStr = when (npcType) {
            NpcType.PRACTICAL -> "practical"
            NpcType.VANITY -> "vanity"
            NpcType.NEUTRAL -> "neutral"
        }
        val identityStr = identity.id
        var text = GameText.npcDialog(typeStr, identityStr, consumptionScore)
        if (text.isEmpty()) {
            text = GameText.menuLabel("npc_chat")
        }
        return text
    }

    fun getOptionLabel(optionKey: String): String {
        return GameText.menuLabel(optionKey)
    }

    private fun opt(key: String): InteractionOption {
        return InteractionOption(
            id = key,
            label = getOptionLabel(key),
            consumptionTendency = GameText.optionTendency(key)
        )
    }

    enum class NpcType {
        PRACTICAL,
        VANITY,
        NEUTRAL
    }

    private val defaultOptions: List<InteractionOption> by lazy {
        listOf(
            opt("look"),
            opt("leave")
        )
    }

    private val buildingOptionMap: Map<String, List<InteractionOption>> by lazy {
        mapOf(
            "player_home" to listOf(
                opt("home_rest"),
                opt("home_cook"),
                opt("home_decor_luxury")
            ),
            "vegetable_market" to listOf(
                opt("market_buy_fresh"),
                opt("market_chat_vendor"),
                opt("market_just_look")
            ),
            "luxury_shop" to listOf(
                opt("luxury_buy"),
                opt("luxury_window_shop"),
                opt("luxury_leave")
            ),
            "clinic" to listOf(
                opt("clinic_checkup"),
                opt("clinic_chat_doctor"),
                opt("clinic_buy_supplements")
            ),
            "workplace" to listOf(
                opt("work_start"),
                opt("work_chat"),
                opt("work_overtime")
            ),
            "park" to listOf(
                opt("park_walk"),
                opt("park_sit"),
                opt("park_feed_cat")
            )
        )
    }
}
