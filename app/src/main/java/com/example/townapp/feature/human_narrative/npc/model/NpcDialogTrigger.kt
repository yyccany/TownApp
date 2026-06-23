package com.example.townapp.feature.human_narrative.npc.model

/**
 * NPC 对话触发条件值对象
 *
 * 用于在 Repository 内部组合多条件查询时传递参数，
 * 严格遵循：复杂多条件联合查询只允许在 Repository 内组装一次。
 *
 * 扩展支持认知等级联动：
 * - cognitiveLevel 0-1：浅层通俗对话（表面闲聊）
 * - cognitiveLevel 2-3：观察者视角对话（客观旁观）
 * - cognitiveLevel 4-5：智慧者视角对话（接纳通透）
 *
 * 落实「以人为本」：高认知状态解锁更客观、理性的对话视角
 */
data class NpcDialogTrigger(
    val jobId: Int,
    val seasonId: Int,
    val favor: Int,
    val intimacy: Int = 0,
    val cognitiveLevel: Int = 0  // 玩家认知等级（0-5）
) {
    /** 根据好感值生成好感分级 key：low / mid / high */
    val favorTier: String
        get() = when {
            favor < 30 -> "low"
            favor < 70 -> "mid"
            else -> "high"
        }

    /**
     * 根据认知等级生成对话风格后缀
     *
     * 严格遵循实事求是原则：
     * - surface：表面闲聊，贴近普通人日常表达
     * - observe：观察者视角，客观陈述不评判
     * - wise：智慧者视角，接纳无常不焦虑
     */
    val cognitionTier: String
        get() = when {
            cognitiveLevel < 2 -> "surface"
            cognitiveLevel < 4 -> "observe"
            else -> "wise"
        }

    /**
     * 生成对话 key（资产 JSON 索引）
     *
     * 格式：job_{jobId}_season_{seasonId}_{favorTier}_{cognitionTier}
     * 例如：job_3_season_1_mid_observe
     */
    val dialogKey: String
        get() = "job_${jobId}_season_${seasonId}_${favorTier}_$cognitionTier"

    /**
     * 降级对话 key（当精确匹配不存在时使用）
     *
     * 降级顺序：同认知等级不同好感 → 同好感不同认知等级 → 完全基础版
     */
    val fallbackKeys: List<String>
        get() {
            val tiers = listOf("high", "mid", "low")
            val cogTiers = listOf(cognitionTier,
                if (cognitionTier == "observe") "surface" else cognitionTier,
                if (cognitionTier == "wise") "observe" else cognitionTier
            )

            val keys = mutableListOf<String>()
            for (cog in cogTiers.distinct()) {
                for (tier in tiers) {
                    keys.add("job_${jobId}_season_${seasonId}_${tier}_$cog")
                }
            }
            // 最后兜底基础版
            keys.add("job_${jobId}_season_${seasonId}_low_surface")
            return keys
        }
}
