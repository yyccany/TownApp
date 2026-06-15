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
            ),
            IdiomCritique(
                id = "kong_rong_rang_li",
                idiom = "孔融让梨",
                traditionalMeaning = "要谦让，要把好的给别人",
                distortedTruth = "从小教导孩子牺牲自己的喜好去迎合他人的期待，这会让孩子形成'我的需求不重要'的认知。",
                townPerspective = "谦让是美德，但不是义务。你喜欢的东西，你有权利自己享用。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.SELF_CULTIVATION, "强迫孩子谦让可能导致自我价值感低下", 0.9),
                    Spotlight(SpotlightCategory.FREEDOM, "孩子有权选择自己喜欢的东西", 1.0)
                ),
                category = IdiomCategory.CHARACTER,
                toxicityLevel = ToxicityLevel.HARMFUL,
                keyMessage = "你不用让，喜欢就自己吃"
            ),
            IdiomCritique(
                id = "ben_niao_xian_fei",
                idiom = "笨鸟先飞",
                traditionalMeaning = "要努力，要比别人更拼",
                distortedTruth = "暗示你比别人差，需要付出更多才能弥补差距，这是一种隐性的贬低。",
                townPerspective = "每个人都有自己的节奏。你不需要和别人比较，按自己的步调来就好。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.SELF_CULTIVATION, "比较会产生焦虑，每个人都有自己的节奏", 1.0),
                    Spotlight(SpotlightCategory.FREEDOM, "定义成功的权利在你自己", 0.9)
                ),
                category = IdiomCategory.CHARACTER,
                toxicityLevel = ToxicityLevel.HARMFUL,
                keyMessage = "按自己的节奏来就好"
            ),
            IdiomCritique(
                id = "chi_kui_shi_fu",
                idiom = "吃亏是福",
                traditionalMeaning = "吃亏是好事，要学会忍",
                distortedTruth = "美化被欺负，让受害者自我安慰，这是对不公的合理化。",
                townPerspective = "吃亏不是福，是别人在侵犯你的边界。你有权利保护自己。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.INTERPERSONAL, "容忍不公只会让侵犯者变本加厉", 1.0),
                    Spotlight(SpotlightCategory.FREEDOM, "你的权利需要自己维护", 0.9)
                ),
                category = IdiomCategory.INTERPERSONAL,
                toxicityLevel = ToxicityLevel.POISONOUS,
                keyMessage = "吃亏不是福，是被欺负"
            ),
            IdiomCritique(
                id = "tian_dao_chou_qin",
                idiom = "天道酬勤",
                traditionalMeaning = "努力就会成功",
                distortedTruth = "将成功归因于个人努力，忽视环境、机遇、出身等因素，这是一种片面的成功观。",
                townPerspective = "努力很重要，但成功还需要很多其他因素。就算不成功，你也很棒了。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.COGNITION, "成功是多种因素共同作用的结果", 1.0),
                    Spotlight(SpotlightCategory.SELF_CULTIVATION, "你的价值不取决于是否成功", 0.9)
                ),
                category = IdiomCategory.SOCIETY,
                toxicityLevel = ToxicityLevel.DISTORTED,
                keyMessage = "努力不一定成功，但你已经很棒"
            ),
            IdiomCritique(
                id = "chi_de_ku_zhong_ku",
                idiom = "吃得苦中苦",
                traditionalMeaning = "要吃苦，才能成功",
                distortedTruth = "将苦难浪漫化，认为成功必须经历痛苦，这会让人忍受不必要的折磨。",
                townPerspective = "吃苦不是成功的必要条件。你可以选择更轻松的方式达到目标。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.SELF_CULTIVATION, "苦难不是勋章，幸福才是目的", 1.0),
                    Spotlight(SpotlightCategory.FREEDOM, "你有权选择不吃苦", 0.9)
                ),
                category = IdiomCategory.CHARACTER,
                toxicityLevel = ToxicityLevel.HARMFUL,
                keyMessage = "吃苦不是必须的"
            ),
            IdiomCritique(
                id = "bai_shan_xiao_wei_xian",
                idiom = "百善孝为先",
                traditionalMeaning = "要听话，要孝顺父母",
                distortedTruth = "将孝顺等同于服从，忽视个人的独立意志和边界。",
                townPerspective = "孝顺不是盲目听话。你可以爱父母，也可以有自己的想法和生活。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.INTERPERSONAL, "孝顺是爱，不是服从", 1.0),
                    Spotlight(SpotlightCategory.FREEDOM, "你有权拥有自己的人生", 0.9)
                ),
                category = IdiomCategory.RELATIONSHIP,
                toxicityLevel = ToxicityLevel.DISTORTED,
                keyMessage = "孝顺不是听话"
            ),
            IdiomCritique(
                id = "ren_yi_shi_feng_ping_lang_jing",
                idiom = "忍一时风平浪静",
                traditionalMeaning = "要忍耐，不要发脾气",
                distortedTruth = "教导人压抑情绪，认为表达愤怒是不好的，这会导致情绪积累和自我伤害。",
                townPerspective = "压抑情绪只会伤害自己。你有权利表达自己的感受。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.EMOTION, "压抑情绪会损害心理健康", 1.0),
                    Spotlight(SpotlightCategory.FREEDOM, "表达情绪是健康的", 0.9)
                ),
                category = IdiomCategory.INTERPERSONAL,
                toxicityLevel = ToxicityLevel.HARMFUL,
                keyMessage = "你不用忍，可以发脾气"
            ),
            IdiomCritique(
                id = "cheng_wang_bai_kou",
                idiom = "成王败寇",
                traditionalMeaning = "赢了才是成功，输了就是失败",
                distortedTruth = "以结果论英雄，忽视过程中的努力和成长，这是一种功利主义的价值观。",
                townPerspective = "输赢只是一时的结果。无论成败，你都值得被爱和尊重。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.COGNITION, "成功不应该用输赢来定义", 1.0),
                    Spotlight(SpotlightCategory.SELF_CULTIVATION, "你的价值不取决于成败", 0.9)
                ),
                category = IdiomCategory.SOCIETY,
                toxicityLevel = ToxicityLevel.DISTORTED,
                keyMessage = "赢输都一样被爱"
            ),
            IdiomCritique(
                id = "chu_ren_tou_di",
                idiom = "出人头地",
                traditionalMeaning = "要成功，要比别人强",
                distortedTruth = "将'比别人强'作为人生目标，陷入永无止境的比较和竞争中。",
                townPerspective = "做普通人也很好。你不需要比别人强，做自己就足够了。",
                spotlights = listOf(
                    Spotlight(SpotlightCategory.COGNITION, "社会不需要每个人都出人头地", 1.0),
                    Spotlight(SpotlightCategory.SELF_CULTIVATION, "平凡也是一种选择", 0.9)
                ),
                category = IdiomCategory.SOCIETY,
                toxicityLevel = ToxicityLevel.HARMFUL,
                keyMessage = "做普通人也很好"
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
