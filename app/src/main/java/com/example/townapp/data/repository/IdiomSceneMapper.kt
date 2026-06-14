package com.example.townapp.data.repository

import com.example.townapp.data.model.IdiomData
import kotlin.random.Random

/**
 * 成语场景映射器
 *
 * 当角色向长辈求助时，根据当前人生困境类型，
 * 从对应主题的成语库中随机抽取一条，模拟长辈用传统俗语给出建议。
 *
 * 核心原则：
 * - 长辈输出俗语 → 焦虑值小幅下降（心理安慰）
 * - 现实困境数值（失业、婚恋、经济、健康）无任何改善
 * - 只提供情绪慰藉，不解决实际问题
 */
object IdiomSceneMapper {

    /**
     * 人生事件场景枚举
     */
    enum class LifeEventScene(val displayName: String) {
        CAREER_FRUSTRATION("择业受挫 / 职场内卷"),
        MARRIAGE_PRESSURE("相亲失败 / 婚恋迷茫"),
        FINANCIAL_STRESS("经济压力 / 负债拮据"),
        SOCIAL_CONFLICT("人际矛盾 / 被排挤"),
        HEALTH_ANXIETY("生病康复 / 纠结治疗"),
        APPEARANCE_ANXIETY("穿搭纠结 / 容貌焦虑"),
        GENERAL_ADVICE("日常求助")
    }

    /**
     * 场景 → 优先主题映射
     * 每个场景对应多个主题，按优先级排列
     */
    private val sceneThemeMap: Map<LifeEventScene, List<IdiomExploreManager.IdiomTheme>> = mapOf(
        LifeEventScene.CAREER_FRUSTRATION to listOf(
            IdiomExploreManager.IdiomTheme.LIFE_WISDOM,
            IdiomExploreManager.IdiomTheme.SOCIAL_CRITIQUE,
            IdiomExploreManager.IdiomTheme.COLLECTIVISM
        ),
        LifeEventScene.MARRIAGE_PRESSURE to listOf(
            IdiomExploreManager.IdiomTheme.LEGACY_CRITIQUE,
            IdiomExploreManager.IdiomTheme.LIFE_WISDOM,
            IdiomExploreManager.IdiomTheme.COLLECTIVISM
        ),
        LifeEventScene.FINANCIAL_STRESS to listOf(
            IdiomExploreManager.IdiomTheme.CONSUMPTION,
            IdiomExploreManager.IdiomTheme.SOCIAL_CRITIQUE,
            IdiomExploreManager.IdiomTheme.COGNITIVE
        ),
        LifeEventScene.SOCIAL_CONFLICT to listOf(
            IdiomExploreManager.IdiomTheme.COLLECTIVISM,
            IdiomExploreManager.IdiomTheme.COGNITIVE,
            IdiomExploreManager.IdiomTheme.LIFE_WISDOM
        ),
        LifeEventScene.HEALTH_ANXIETY to listOf(
            IdiomExploreManager.IdiomTheme.HEALTH,
            IdiomExploreManager.IdiomTheme.PSEUDOSCIENCE,
            IdiomExploreManager.IdiomTheme.LEGACY_CRITIQUE
        ),
        LifeEventScene.APPEARANCE_ANXIETY to listOf(
            IdiomExploreManager.IdiomTheme.MENTAL,
            IdiomExploreManager.IdiomTheme.LEGACY_CRITIQUE,
            IdiomExploreManager.IdiomTheme.SOLO_LIVING
        ),
        LifeEventScene.GENERAL_ADVICE to listOf(
            IdiomExploreManager.IdiomTheme.LIFE_WISDOM,
            IdiomExploreManager.IdiomTheme.LEGACY_CRITIQUE,
            IdiomExploreManager.IdiomTheme.COLLECTIVISM
        )
    )

    /**
     * 穿搭风格 → 可能触发的传统观念
     * 当玩家选择个性/潮流穿搭时，长辈可能点评
     */
    enum class OutfitStyleConflict(val displayName: String) {
        AVANT_GARDE("前卫个性"),
        OVERSPENDING("过度消费"),
        UNCONVENTIONAL("不合传统")
    }

    /** 穿搭冲突 → 长辈可能说的俗语ID列表 */
    private val outfitConflictIdiomIds: Map<OutfitStyleConflict, List<Int>> = mapOf(
        OutfitStyleConflict.AVANT_GARDE to listOf(3, 19, 20, 60, 61),  // 衣锦还乡, 华而不实, 削足适履, 华而不实, 哗众取宠
        OutfitStyleConflict.OVERSPENDING to listOf(3, 17, 21, 62),      // 衣锦还乡, 得不偿失, 积少成多, 堆积如山
        OutfitStyleConflict.UNCONVENTIONAL to listOf(2, 75, 77, 89)     // 敝帚自珍, 随遇而安, 安分守己, 删繁就简
    )

    // ============================================
    // 场景触发
    // ============================================

    /**
     * 根据人生事件场景，从对应主题中随机抽取一条成语
     *
     * @param scene 当前人生困境类型
     * @param discoveredIds 已发现的成语ID（用于优先推荐未发现的）
     * @return 成语数据，null 表示无匹配
     */
    fun getElderAdviceIdiom(scene: LifeEventScene, discoveredIds: Set<Int>): IdiomData? {
        val themes = sceneThemeMap[scene] ?: return null

        // 收集所有对应主题的成语
        val allCandidates = themes.flatMap { theme ->
            IdiomExploreManager.getByTheme(theme)
        }.distinctBy { it.idiomId }

        if (allCandidates.isEmpty()) return null

        // 优先从未发现的成语中选，其次从全部中选
        val undiscovered = allCandidates.filter { it.idiomId !in discoveredIds }
        val pool = if (undiscovered.isNotEmpty()) undiscovered else allCandidates

        return pool.random()
    }

    /**
     * 穿搭冲突触发长辈点评
     *
     * @param conflictType 穿搭冲突类型
     * @return 成语数据，null 表示概率未触发
     */
    fun getOutfitConflictIdiom(conflictType: OutfitStyleConflict): IdiomData? {
        val ids = outfitConflictIdiomIds[conflictType] ?: return null
        val id = ids.random()
        return IdiomRepository.getIdiomById(id)
    }

    /**
     * 获取场景对应的主题标签（用于UI展示）
     */
    fun getSceneThemeTags(scene: LifeEventScene): List<String> {
        return sceneThemeMap[scene]?.map { it.displayName } ?: emptyList()
    }

    // ============================================
    // 33条未触发成语分配方案
    // ============================================

    /**
     * 获取"集体与个体"类成语（ID 71-80）的触发场景
     * → 青年择业/婚恋/社交场景
     */
    fun getCollectivismIdiomIds(): List<Int> = (71..80).toList()

    /**
     * 获取"赛博奶嘴/时代局限性"类成语（ID 81-85）的触发场景
     * → 中年压力/精神娱乐场景
     */
    fun getSocialCritiqueIdiomIds(): List<Int> = (81..85).toList()

    /**
     * 获取"独居与自我"类成语（ID 86-90）的触发场景
     * → 青年择业/独居生活场景
     */
    fun getSoloLivingIdiomIds(): List<Int> = (86..90).toList()

    /**
     * 获取"时代局限性"类成语（ID 91-92）的触发场景
     * → 社交/消费场景
     */
    fun getLegacyCritiqueIdiomIds(): List<Int> = (91..92).toList()

    /**
     * 获取"生活智慧"类成语（ID 93-100）的触发场景
     * → 晚年里程碑 + 日常随机触发
     */
    fun getLifeWisdomIdiomIds(): List<Int> = (93..100).toList()

    /**
     * 获取"传统观念反思"类成语（ID 64-65）的触发场景
     * → 晚年里程碑
     */
    fun getLegacyReflectionIdiomIds(): List<Int> = listOf(64, 65)

    /**
     * 获取"消费与物质"类成语（ID 66）的触发场景
     * → 经济压力场景
     */
    fun getConsumptionIdiomId(): Int = 66
}