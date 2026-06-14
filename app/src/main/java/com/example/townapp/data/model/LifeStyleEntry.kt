package com.example.townapp.data.model

/**
 * 生活方式图鉴条目。
 *
 * ===== 哲学底线 =====
 * 1. 不做任何价值判断：没有「正面行为」和「负面行为」，
 *    只有「行为」和它带来的客观数值变化。
 * 2. 不贴任何文化标签：不预设某一种生活方式「更好」或「更差」。
 * 3. 不输出任何结论：所有判断留给玩家自己。
 *
 * 每个条目描述一种真实存在的生活方式，
 * 包含行为模式、客观后果、普通人的真实故事。
 *
 * @param id 全局唯一标识
 * @param name 条目名称（中性描述）
 * @param region 地域标签（仅用于分类，不代表文化刻板印象）
 * @param commonBehaviors 该生活方式中常见的行为模式（不含褒贬）
 * @param realStories 来自真实普通人的故事（同时包含不同视角）
 * @param unlockCondition 解锁条件（客观描述）
 * @param contributorId 贡献者 ID（用于用户共创内容的溯源）
 */
data class LifeStyleEntry(
    val id: String,
    val name: String,
    val region: String,
    val commonBehaviors: List<LifeStyleBehavior>,
    val realStories: List<String>,
    val unlockCondition: String,
    val contributorId: String = "anonymous",
    val isUnlocked: Boolean = false
) {
    /** 供 UI 中未解锁时显示的提示文字。 */
    val unlockHint: String
        get() = if (isUnlocked) "已收集 ${realStories.size} 个真实故事" else unlockCondition
}

/**
 * 单一行为的数据。
 *
 * 只记录三个字段：
 * - 行为描述（中性）
 * - 客观后果（数值变化，正负号只是方向，不是好坏）
 * - 说明文字（进一步解释该后果的客观含义）
 *
 * 例子：
 *   Behavior("每天学习12小时",
 *       mapOf("学业进度" to +8, "体力" to -3, "社交时间" to -2))
 *   说明：[+8] = 学习进度加快，[-3] = 身体疲劳增加
 *   这些都是客观事实，没有好坏之分。
 */
data class LifeStyleBehavior(
    val description: String,
    val effects: Map<String, Int>,
    val explanation: String = ""
)