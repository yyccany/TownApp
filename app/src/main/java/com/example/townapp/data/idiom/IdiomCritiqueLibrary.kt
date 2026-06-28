package com.example.townapp.data.idiom

import android.content.Context
import com.example.townapp.data.spotlight.Spotlight

enum class ToxicityLevel(
    val displayName: String,
    val definition: String,
    val townAttitude: String,
    val colorHex: Long
) {
    POISONOUS("认知误区", "直接破坏个人边界与自主权，是典型的精神控制工具；披着道德外衣的伤害", "警惕使用，可能造成心理伤害", 0xFFFFE9D6),
    HARMFUL("焦虑陷阱", "放大竞争焦虑，让个体只能在比较和内卷中寻求价值认同", "谨慎使用，可能引发不良情绪", 0xFFFFE0D1),
    DISTORTED("片面认知", "美化等级/特权，将剥削合理化并包装为自我修炼", "保持警惕，理性看待", 0xFFFFF7D9),
    HERITAGE("传统观念", "蕴含朴素智慧但需与时俱进。去其糟粕、取其精华是关键", "辩证看待", 0xFFFBE3E6),
    TOWN_WISDOM("底层认知", "小镇原创认知，揭示时间、财富、身心之间的真实规律", "用心体会，慢慢理解", 0xFFE0EEFB)
}

enum class IdiomCategory(
    val displayName: String,
    val description: String,
    val colorHex: Long
) {
    CHARACTER("人格品德", "审视传统品德标准对个体的影响", 0xFFFBE3E6),
    METHOD("处事方法", "反思传统处世哲学的现代适用性", 0xFFFFF7D9),
    INTERPERSONAL("人际关系", "重新理解传统人际交往智慧", 0xFFFFF7D9),
    SOCIETY("社会观念", "剖析传统社会观念的深层逻辑", 0xFFFBE3E6),
    RELATIONSHIP("亲密关系", "重新定义亲密关系中的角色期待", 0xFFFBE3E6),
    COGNITION("认知思维", "识别思维陷阱与认知偏误", 0xFFE8E0F7),
    TOWN_SYSTEM("小镇内在", "理解小镇底层运行的认知法则", 0xFFE2F6EC),
    WORKPLACE("职场围城", "拆解职业滤镜与围城焦虑", 0xFFE0EEFB),
    CONSUMPTION("日常消费", "反思消费主义与圈层裹挟", 0xFFF9E2CC)
}

/**
 * 词条入口类型 —— 标记内容的来源类型
 *
 * 无论入口是什么，分析引擎统一使用三层唯物辩证结构：
 * 唯物史观底层 → 现代社会结构批判 → 认知心理学视角
 */
enum class EntryType(val displayName: String) {
    IDIOM("俗语"),           // 传统成语/俗语/谚语
    COINED("概念"),          // 自造概念词（面子囚徒、信息井蛙等）
    ACADEMIC("学术"),        // 学术/心理学概念（沉没成本、锚定效应等）
    MODERN_BELIEF("现代"),   // 现代生活观念陈述（学历决定一切等）
    DILEMMA("困境")          // 社交困境入口（被说犟、被说作等）
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
    val keyMessage: String,
    val examples: List<String> = emptyList(),
    val actionableTip: String = "",
    val cognitiveBiasTags: List<String> = emptyList(),
    val entryType: EntryType = EntryType.IDIOM
)

/**
 * 小镇认知字典词条仓库
 *
 * 所有词条数据从 assets/idioms/idioms.json 动态加载，实现文案与代码的彻底解耦。
 * 调用方无需关心数据来源，通过静态方法即可访问全部词条。
 *
 * 使用方式：
 * 1. 在 Application 或 MainActivity.onCreate() 中调用 IdiomCritiqueLibrary.initialize(context)
 * 2. 任何位置通过 IdiomCritiqueLibrary.getAllIdioms() 等静态方法访问数据
 */
class IdiomCritiqueLibrary private constructor(context: Context) {

    private val loader = IdiomAssetLoader(context.applicationContext)

    private val _idioms by lazy { loader.loadAllIdioms() }
    val idioms: List<IdiomCritique> get() = _idioms

    private val _idiomMap by lazy { _idioms.associateBy { it.id } }
    val idiomMap: Map<String, IdiomCritique> get() = _idiomMap

    companion object {
        @Volatile
        private var instance: IdiomCritiqueLibrary? = null

        /**
         * 初始化词条仓库。应在应用启动时调用一次。
         */
        fun initialize(context: Context) {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = IdiomCritiqueLibrary(context)
                    }
                }
            }
        }

        private fun ensureInitialized(): IdiomCritiqueLibrary {
            return instance ?: throw IllegalStateException(
                "IdiomCritiqueLibrary 尚未初始化。请在 Application.onCreate() 或 MainActivity.onCreate() 中先调用 initialize(context)。"
            )
        }

        fun getAllIdioms(): List<IdiomCritique> = ensureInitialized().idioms

        fun getById(id: String): IdiomCritique? = ensureInitialized().idiomMap[id]

        fun getIdiomsByCategory(category: IdiomCategory): List<IdiomCritique> {
            return ensureInitialized().idioms.filter { it.category == category }
        }

        fun getByCategory(category: IdiomCategory): List<IdiomCritique> {
            return getIdiomsByCategory(category)
        }

        fun getIdiomsByBiasTag(tag: String): List<IdiomCritique> {
            return ensureInitialized().idioms.filter { it.cognitiveBiasTags.contains(tag) }
        }

        fun getIdiomsByEntryType(entryType: EntryType): List<IdiomCritique> {
            return ensureInitialized().idioms.filter { it.entryType == entryType }
        }

        fun getCategories(): List<IdiomCategory> = IdiomCategory.entries.toList()
    }
}
