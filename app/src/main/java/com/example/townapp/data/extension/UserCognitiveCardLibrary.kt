package com.example.townapp.data.extension

import com.example.townapp.data.model.*
import java.util.concurrent.ConcurrentHashMap

object UserCognitiveCardLibrary {
    private val cards = ConcurrentHashMap<String, UserCognitiveCard>()
    private val pendingReview = ConcurrentHashMap<String, UserCognitiveCard>()

    fun submitCard(anonymousUserId: String, title: String, content: String, category: String, relatedItems: List<Int> = emptyList(), costRealization: CostRealization? = null): UserCognitiveCard {
        val card = UserCognitiveCard("card_${System.currentTimeMillis()}", anonymousUserId, System.currentTimeMillis(), title, content, category, relatedItems, costRealization, ReviewStatus.PENDING, null, 0, 0.0)
        pendingReview[card.cardId] = card
        return card
    }

    fun approveCard(cardId: String): UserCognitiveCard? {
        val card = pendingReview.remove(cardId) ?: return null
        val approved = card.copy(reviewStatus = ReviewStatus.APPROVED, approveTime = System.currentTimeMillis())
        cards[cardId] = approved
        return approved
    }

    fun getApprovedCards(category: String? = null, page: Int = 0, pageSize: Int = 10): List<UserCognitiveCard> {
        val filtered = if(category != null) cards.values.filter { it.category == category && it.reviewStatus == ReviewStatus.APPROVED } else cards.values.filter { it.reviewStatus == ReviewStatus.APPROVED }
        return filtered.sortedByDescending { it.unlockCount }.drop(page*pageSize).take(pageSize)
    }

    fun unlockCard(cardId: String): UserCognitiveCard? {
        val card = cards[cardId] ?: return null
        val updated = card.copy(unlockCount = card.unlockCount + 1)
        cards[cardId] = updated
        return updated
    }

    fun getPresetCards(): List<UserCognitiveCard> = listOf(
        UserCognitiveCard("preset_001", "system", 0, "名牌包的隐藏代价", "之前以为买名牌包很值，后来发现每年要花500块保养", "消费反思", emptyList(), CostRealization("提升生活品质","买包2万+每年保养500","注意力占用","物件价值不等于价格"), ReviewStatus.APPROVED, 0, 100, 4.5),
        UserCognitiveCard("preset_002", "system", 0, "每天1小时健身的改变", "坚持3年健身，身体状态从亚健康变成活力充沛", "健康认知", emptyList(), CostRealization("健身累占时间","每天1小时x365天x3年=1095小时","放弃的娱乐社交时间","时间花在哪里身体就回报在哪里"), ReviewStatus.APPROVED, 0, 85, 4.8),
        UserCognitiveCard("preset_003", "system", 0, "减少外卖后的变化", "过去一年每天点外卖花了2万，自己做饭花了1万但多花了365小时", "生活感悟", emptyList(), CostRealization("外卖贵","外卖2万/年 vs 自己做饭1万/年+365小时","做饭时间也是劳动投入","省钱不是唯一标准"), ReviewStatus.APPROVED, 0, 72, 4.3)
    )

    fun getPendingReviewCount(): Int = pendingReview.size
    fun getApprovedCount(): Int = cards.values.count { it.reviewStatus == ReviewStatus.APPROVED }
    fun clearAll() { cards.clear(); pendingReview.clear() }
}