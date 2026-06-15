package com.example.townapp.data.gameevent

import kotlin.random.Random

enum class FactionType {
    BENEVOLENT,
    SELFISH
}

data class TownPolicy(
    val id: String,
    val name: String,
    val description: String,
    val faction: FactionType,
    val taxRateChange: Double,
    val priceIncrease: Double,
    val welfareChange: Double,
    val impactDescription: String
)

object TownPolicyManager {
    private val benevolentPolicies = listOf(
        TownPolicy(
            id = "benevolent_tax_cut",
            name = "民生减税",
            description = "降低中低收入群体税负，提高民生保障",
            faction = FactionType.BENEVOLENT,
            taxRateChange = -0.15,
            priceIncrease = 0.0,
            welfareChange = 0.2,
            impactDescription = "你的税负减轻了，每月可支配收入增加"
        ),
        TownPolicy(
            id = "benevolent_welfare",
            name = "福利提升",
            description = "增加公共福利投入，改善医疗教育条件",
            faction = FactionType.BENEVOLENT,
            taxRateChange = 0.05,
            priceIncrease = -0.05,
            welfareChange = 0.3,
            impactDescription = "医疗和教育支出减少，生活压力减轻"
        ),
        TownPolicy(
            id = "benevolent_housing",
            name = "住房保障",
            description = "建设保障性住房，稳定房价",
            faction = FactionType.BENEVOLENT,
            taxRateChange = 0.08,
            priceIncrease = -0.1,
            welfareChange = 0.15,
            impactDescription = "房租/房价有所下降，居住成本降低"
        )
    )

    private val selfishPolicies = listOf(
        TownPolicy(
            id = "selfish_tax_hike",
            name = "财政紧缩",
            description = "提高中低收入群体税负，削减公共支出",
            faction = FactionType.SELFISH,
            taxRateChange = 0.2,
            priceIncrease = 0.1,
            welfareChange = -0.2,
            impactDescription = "税负增加，生活成本上升"
        ),
        TownPolicy(
            id = "selfish_regressive",
            name = "富人优待",
            description = "降低富人税率，减少社会福利",
            faction = FactionType.SELFISH,
            taxRateChange = 0.15,
            priceIncrease = 0.15,
            welfareChange = -0.3,
            impactDescription = "贫富差距拉大，物价上涨"
        ),
        TownPolicy(
            id = "selfish_austerity",
            name = "紧缩政策",
            description = "大幅削减公共福利，降低政府支出",
            faction = FactionType.SELFISH,
            taxRateChange = 0.1,
            priceIncrease = 0.12,
            welfareChange = -0.4,
            impactDescription = "公共服务减少，生活压力倍增"
        )
    )

    var currentFaction: FactionType = FactionType.BENEVOLENT
    var currentTaxRate: Double = 0.2
    var currentPriceLevel: Double = 1.0
    var currentWelfareLevel: Double = 1.0

    fun switchFaction(newFaction: FactionType) {
        currentFaction = newFaction
        applyPolicies()
    }

    fun applyPolicies() {
        val policies = if (currentFaction == FactionType.BENEVOLENT) {
            benevolentPolicies
        } else {
            selfishPolicies
        }

        policies.forEach { policy ->
            currentTaxRate += policy.taxRateChange
            currentPriceLevel += policy.priceIncrease
            currentWelfareLevel += policy.welfareChange
        }

        currentTaxRate = currentTaxRate.coerceIn(0.0, 0.5)
        currentPriceLevel = currentPriceLevel.coerceIn(0.8, 1.5)
        currentWelfareLevel = currentWelfareLevel.coerceIn(0.5, 1.5)
    }

    fun getCurrentPolicyEffects(): List<String> {
        val effects = mutableListOf<String>()

        if (currentFaction == FactionType.BENEVOLENT) {
            effects.add("🌟 当前由向善派系执政")
            if (currentTaxRate < 0.2) effects.add("✅ 税负较低，民生友好")
            if (currentPriceLevel < 1.0) effects.add("✅ 物价稳定，生活成本可控")
            if (currentWelfareLevel > 1.0) effects.add("✅ 福利完善，保障充足")
        } else {
            effects.add("🌑 当前由利己派系执政")
            if (currentTaxRate > 0.2) effects.add("⚠️ 税负较高，生活压力大")
            if (currentPriceLevel > 1.0) effects.add("⚠️ 物价上涨，开支增加")
            if (currentWelfareLevel < 1.0) effects.add("⚠️ 福利削减，保障不足")
        }

        return effects
    }

    fun simulateFactionChange(): Boolean {
        val roll = Random.nextDouble()
        val changeProbability = if (currentFaction == FactionType.BENEVOLENT) 0.3 else 0.25
        return roll < changeProbability
    }

    fun getAllPolicies(): List<TownPolicy> {
        return benevolentPolicies + selfishPolicies
    }
}