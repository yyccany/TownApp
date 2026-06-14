package com.example.townapp.data.idiom

import com.example.townapp.data.spotlight.Spotlight
import com.example.townapp.data.spotlight.SpotlightCategory

enum class ToxicityLevel(
    val displayName: String,
    val emoji: String,
    val definition: String,
    val townAttitude: String,
    val colorHex: Long
) {
    POISONOUS("剧毒", "☠️", "直接破坏个人边界与自主权，是典型的精神控制工具；披着道德外衣的伤害", "警惕使用，可能造成心理伤害", 0xFFE53935),
    HARMFUL("有害", "⚠️", "放大竞争焦虑，让个体只能在比较和内卷中寻求价值认同", "谨慎使用，可能引发不良情绪", 0xFFF59E0B),
    DISTORTED("扭曲", "🔄", "美化等级/特权，将剥削合理化并包装为自我修炼", "保持警惕，理性看待", 0xFFFCD34D),
    HERITAGE("传承", "🏺", "蕴含朴素智慧但需与时俱进。去其糟粕、取其精华是关键", "辩证看待", 0xFF4CAF50)
}

enum class IdiomCategory(
    val emoji: String,
    val displayName: String,
    val description: String,
    val colorHex: Long
) {
    CHARACTER("👤", "人格品德", "审视传统品德标准对个体的影响", 0xFFE91E63),
    METHOD("⚙️", "处事方法", "反思传统处世哲学的现代适用性", 0xFF9C27B0),
    INTERPERSONAL("🤝", "人际关系", "重新理解传统人际交往智慧", 0xFF2196F3),
    SOCIETY("🏛️", "社会观念", "剖析传统社会观念的深层逻辑", 0xFF4CAF50),
    RELATIONSHIP("💞", "亲密关系", "重新定义亲密关系中的角色期待", 0xFFFF9800)
}

data class IdiomCritique(
    val id: String,
    val idiom: String,
    val traditionalMeaning: String,
    val distortedTruth: String,
    val townPerspective: String,
    val spotlights: List<Spotlight> = emptyList(),
    val category: IdiomCategory,
    val toxicityLevel: ToxicityLevel,
    val keyMessage: String
)

class IdiomCritiqueLibrary {
    companion object {
        fun getAllIdioms(): List<IdiomCritique> = listOf(
            IdiomCritique(
                id = "da_gong_wu_si",
                idiom = "大公无私",
                traditionalMeaning = "没有私心，一心为公",
                distortedTruth = "要求人完全无私是对人性的苛求，真正的无私需要建立在充足自我关怀的基础上。所谓无私往往要么是道德绑架，要么是自我牺牲。",
                townPerspective = "无私是高尚的品德，但首先要确保自己不是在自我剥削。每个人都有权利优先照顾自己，这不是自私。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.TRADITION, "传统道德对无私的过度推崇可能导致个人边界被侵犯", 1.0),
                    Spotlight(SpotlightCategory.FREEDOM, "无私应该是一种选择而非义务，强迫无私是压迫", 0.9)
                ),
                category = IdiomCategory.CHARACTER,
                toxicityLevel = ToxicityLevel.POISONOUS,
                keyMessage = "无私是高尚品德，但不要自我剥削"
            ),
            IdiomCritique(
                id = "she_ji_wei_ren",
                idiom = "舍己为人",
                traditionalMeaning = "牺牲自己利益去帮助别人的高尚品德",
                distortedTruth = "美化自我牺牲，鼓励人为了他人放弃自己的需求和幸福，这是一种道德绑架。",
                townPerspective = "帮助别人是美好的，但首先要照顾好自己。先利己再利人，不是自私而是智慧。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.EMOTION, "危急时刻舍己为人是勇气，但日常倡导则可能造成伤害", 1.0),
                    Spotlight(SpotlightCategory.FREEDOM, "帮助应该是有余力时的选择，而不是必须履行的义务", 0.9)
                ),
                category = IdiomCategory.CHARACTER,
                toxicityLevel = ToxicityLevel.POISONOUS,
                keyMessage = "先利己再利人，不是自私而是智慧"
            )
        )

        fun getById(id: String): IdiomCritique? {
            return getAllIdioms().find { it.id == id }
        }

        fun getByCategory(category: IdiomCategory): List<IdiomCritique> {
            return getAllIdioms().filter { it.category == category }
        }

        fun getIdiomsByCategory(category: IdiomCategory): List<IdiomCritique> {
            return getByCategory(category)
        }

        fun getCategories(): List<IdiomCategory> {
            return IdiomCategory.values().toList()
        }
    }
}
