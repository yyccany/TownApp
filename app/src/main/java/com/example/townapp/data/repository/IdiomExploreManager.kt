package com.example.townapp.data.repository

import com.example.townapp.data.model.IdiomData
import com.example.townapp.data.model.IdiomRarity
import kotlin.random.Random

/**
 * 成语自由探索管理器
 *
 * 小镇里有一间旧书房，书架上整齐地码着100卷竹简。
 * 有些竹简，你在生活里自然会翻到（通过触发机制）；
 * 有些竹简藏得深一些，需要你在书房里主动探索。
 *
 * 这里没有催促、没有红点、没有"还有X条未读"。
 * 你想来就来，想走就走。
 * 每一条竹简，都在它该在的地方，安静地等你。
 */
object IdiomExploreManager {

    /**
     * 成语主题分类 —— 覆盖全部100条成语
     */
    enum class IdiomTheme(val displayName: String, val description: String) {
        CONSUMPTION("消费与物质", "衣物、食物、住房、出行——你花钱的方式，就是你的生活方式"),
        COGNITIVE("认知与决策", "学习、投资、信息茧房——你的思维方式，决定了你的生活质量"),
        HEALTH("健康与消费品", "烟酒糖茶、护肤品、保健品——那些慢慢侵蚀你的日常消费"),
        MENTAL("精神与娱乐", "短视频、直播、网文、游戏——虚拟世界的真实代价"),
        PSEUDOSCIENCE("伪科学与迷信", "晒背、节气、星象——科学时代里的古老执念"),
        COLLECTIVISM("集体与个体", "舍己为人、大公无私、先人后己——重新理解你与集体的关系"),
        SOLO_LIVING("独居与自我", "一个人生活，也可以过得很好、很精彩、很有尊严"),
        SOCIAL_CRITIQUE("时代局限性", "棋牌、酒桌、虚拟偶像——旧时代的娱乐，新时代的陷阱"),
        LIFE_WISDOM("生活智慧", "脚踏实地、自食其力、守望相助——简单而真实的生活哲学"),
        LEGACY_CRITIQUE("传统观念反思", "债台高筑、形同陌路——那些需要重新审视的人生观念")
    }

    /**
     * 成语ID → 主题映射
     */
    private val idiomThemeMap: Map<Int, IdiomTheme> = mapOf(
        // 消费与物质 (1-6, 11-35)
        1 to IdiomTheme.CONSUMPTION, 2 to IdiomTheme.CONSUMPTION, 3 to IdiomTheme.CONSUMPTION,
        4 to IdiomTheme.CONSUMPTION, 5 to IdiomTheme.CONSUMPTION, 6 to IdiomTheme.CONSUMPTION,
        11 to IdiomTheme.CONSUMPTION, 12 to IdiomTheme.CONSUMPTION, 13 to IdiomTheme.CONSUMPTION,
        14 to IdiomTheme.CONSUMPTION, 15 to IdiomTheme.CONSUMPTION, 16 to IdiomTheme.CONSUMPTION,
        17 to IdiomTheme.CONSUMPTION, 18 to IdiomTheme.CONSUMPTION, 19 to IdiomTheme.CONSUMPTION,
        20 to IdiomTheme.CONSUMPTION, 21 to IdiomTheme.CONSUMPTION, 22 to IdiomTheme.CONSUMPTION,
        23 to IdiomTheme.CONSUMPTION, 24 to IdiomTheme.CONSUMPTION, 25 to IdiomTheme.CONSUMPTION,
        26 to IdiomTheme.CONSUMPTION, 27 to IdiomTheme.CONSUMPTION, 28 to IdiomTheme.CONSUMPTION,
        29 to IdiomTheme.CONSUMPTION, 30 to IdiomTheme.CONSUMPTION,
        31 to IdiomTheme.CONSUMPTION, 32 to IdiomTheme.CONSUMPTION, 33 to IdiomTheme.CONSUMPTION,
        34 to IdiomTheme.CONSUMPTION, 35 to IdiomTheme.CONSUMPTION,
        // 认知与决策 (7-10)
        7 to IdiomTheme.COGNITIVE, 8 to IdiomTheme.COGNITIVE, 9 to IdiomTheme.COGNITIVE,
        10 to IdiomTheme.COGNITIVE,
        // 健康与消费品 (36-45)
        36 to IdiomTheme.HEALTH, 37 to IdiomTheme.HEALTH, 38 to IdiomTheme.HEALTH,
        39 to IdiomTheme.HEALTH, 40 to IdiomTheme.HEALTH,
        41 to IdiomTheme.HEALTH, 42 to IdiomTheme.HEALTH, 43 to IdiomTheme.HEALTH,
        44 to IdiomTheme.HEALTH, 45 to IdiomTheme.HEALTH,
        // 精神与娱乐 (46-55)
        46 to IdiomTheme.MENTAL, 47 to IdiomTheme.MENTAL, 48 to IdiomTheme.MENTAL,
        49 to IdiomTheme.MENTAL, 50 to IdiomTheme.MENTAL, 51 to IdiomTheme.MENTAL,
        52 to IdiomTheme.MENTAL,
        53 to IdiomTheme.MENTAL, 54 to IdiomTheme.MENTAL, 55 to IdiomTheme.MENTAL,
        // 伪科学与迷信 (56-57, 67-70)
        56 to IdiomTheme.PSEUDOSCIENCE, 57 to IdiomTheme.PSEUDOSCIENCE,
        67 to IdiomTheme.PSEUDOSCIENCE, 68 to IdiomTheme.PSEUDOSCIENCE,
        69 to IdiomTheme.PSEUDOSCIENCE, 70 to IdiomTheme.PSEUDOSCIENCE,
        // 消费与物质 (58-63)
        58 to IdiomTheme.CONSUMPTION, 59 to IdiomTheme.CONSUMPTION, 60 to IdiomTheme.CONSUMPTION,
        61 to IdiomTheme.CONSUMPTION, 62 to IdiomTheme.CONSUMPTION, 63 to IdiomTheme.CONSUMPTION,
        // 传统观念反思 (64-65)
        64 to IdiomTheme.LEGACY_CRITIQUE, 65 to IdiomTheme.LEGACY_CRITIQUE,
        // 消费与物质 (66)
        66 to IdiomTheme.CONSUMPTION,
        // 集体与个体 (71-80)
        71 to IdiomTheme.COLLECTIVISM, 72 to IdiomTheme.COLLECTIVISM, 73 to IdiomTheme.COLLECTIVISM,
        74 to IdiomTheme.COLLECTIVISM, 75 to IdiomTheme.COLLECTIVISM, 76 to IdiomTheme.COLLECTIVISM,
        77 to IdiomTheme.COLLECTIVISM, 78 to IdiomTheme.COLLECTIVISM, 79 to IdiomTheme.COLLECTIVISM,
        80 to IdiomTheme.COLLECTIVISM,
        // 时代局限性 (81-85)
        81 to IdiomTheme.SOCIAL_CRITIQUE, 82 to IdiomTheme.SOCIAL_CRITIQUE,
        83 to IdiomTheme.SOCIAL_CRITIQUE, 84 to IdiomTheme.SOCIAL_CRITIQUE,
        85 to IdiomTheme.SOCIAL_CRITIQUE,
        // 独居与自我 (86-90)
        86 to IdiomTheme.SOLO_LIVING, 87 to IdiomTheme.SOLO_LIVING,
        88 to IdiomTheme.SOLO_LIVING, 89 to IdiomTheme.SOLO_LIVING,
        90 to IdiomTheme.SOLO_LIVING,
        // 时代局限性 (91-92)
        91 to IdiomTheme.SOCIAL_CRITIQUE, 92 to IdiomTheme.SOCIAL_CRITIQUE,
        // 生活智慧 (93-100)
        93 to IdiomTheme.LIFE_WISDOM, 94 to IdiomTheme.LIFE_WISDOM, 95 to IdiomTheme.LIFE_WISDOM,
        96 to IdiomTheme.LIFE_WISDOM, 97 to IdiomTheme.LIFE_WISDOM, 98 to IdiomTheme.LIFE_WISDOM,
        99 to IdiomTheme.LIFE_WISDOM, 100 to IdiomTheme.LIFE_WISDOM
    )

    // ============================================
    // 按主题浏览
    // ============================================

    /**
     * 获取某个主题下的全部成语
     */
    fun getByTheme(theme: IdiomTheme): List<IdiomData> {
        val ids = idiomThemeMap.filter { it.value == theme }.keys
        return ids.mapNotNull { IdiomRepository.getIdiomById(it) }
    }

    /**
     * 获取所有主题（含该主题下的成语数量）
     */
    fun getAllThemes(): Map<IdiomTheme, Int> {
        return IdiomTheme.entries.associateWith { theme ->
            idiomThemeMap.count { it.value == theme }
        }
    }

    /**
     * 获取成语对应的主题
     */
    fun getThemeOf(idiomId: Int): IdiomTheme? {
        return idiomThemeMap[idiomId]
    }

    // ============================================
    // 按稀有度浏览
    // ============================================

    /**
     * 获取所有稀有度等级及其成语数量
     */
    fun getRarityDistribution(): Map<IdiomRarity, Int> {
        return IdiomRarity.entries.associateWith { rarity ->
            IdiomRepository.getIdiomsByRarity(rarity).size
        }
    }

    /**
     * 按稀有度获取成语列表
     */
    fun getByRarity(rarity: IdiomRarity): List<IdiomData> {
        return IdiomRepository.getIdiomsByRarity(rarity)
    }

    // ============================================
    // 自由探索 —— 随机推荐
    // ============================================

    /**
     * 获取随机推荐成语（优先推荐稀有度较高的未发现成语）
     *
     * @param discoveredIds 已发现的成语ID集合
     * @param count 推荐数量
     * @return 推荐的成语列表
     */
    fun getExploreRecommendations(discoveredIds: Set<Int>, count: Int = 5): List<IdiomData> {
        val allIdioms = IdiomRepository.getAllIdioms()

        // 未发现的成语，按稀有度排序（LEGENDARY > EPIC > RARE > UNCOMMON > COMMON）
        val undiscovered = allIdioms
            .filter { it.idiomId !in discoveredIds }
            .sortedByDescending { it.rarity.ordinal }

        if (undiscovered.isEmpty()) {
            // 全部发现后，随机推荐
            return allIdioms.shuffled().take(count)
        }

        // 优先取高稀有度的未发现成语，混合一些随机
        val highRarity = undiscovered.take(count * 2 / 3)
        val random = allIdioms.shuffled().take(count / 3)
        return (highRarity + random).distinct().shuffled().take(count)
    }

    /**
     * 里程碑解锁 —— 角色到达特定年龄时，解锁1-2条未发现的成语
     *
     * 里程碑节点：20, 25, 30, 35, 40, 45, 50, 55, 60岁
     * 按年龄阶段分配主题优先级：
     *   20-25岁（青年婚恋/择业期）：传统观念、婚恋、处世
     *   30-35岁（中年压力期）：谋生、人际、时代局限
     *   40-45岁（中年反思期）：集体与个体、消费反思
     *   50-60岁（晚年反思期）：生活智慧、独居与自我、传统观念反思
     *
     * @param age 角色当前年龄
     * @param discoveredIds 已发现的成语ID集合
     * @return 本次解锁的成语列表（可能为空）
     */
    fun getMilestoneUnlocks(age: Int, discoveredIds: Set<Int>): List<IdiomData> {
        val milestones = setOf(20, 25, 30, 35, 40, 45, 50, 55, 60)
        if (age !in milestones) return emptyList()

        val unlockCount = if (age >= 40) 2 else 1
        val allIdioms = IdiomRepository.getAllIdioms()
        val undiscovered = allIdioms.filter { it.idiomId !in discoveredIds }

        if (undiscovered.isEmpty()) return emptyList()

        // 按年龄阶段选择优先主题
        val priorityThemes = when {
            age <= 25 -> listOf(
                IdiomTheme.LEGACY_CRITIQUE,    // 传统观念反思
                IdiomTheme.COLLECTIVISM,        // 集体与个体
                IdiomTheme.SOLO_LIVING,         // 独居与自我
                IdiomTheme.LIFE_WISDOM          // 生活智慧
            )
            age <= 35 -> listOf(
                IdiomTheme.SOCIAL_CRITIQUE,     // 时代局限性
                IdiomTheme.COLLECTIVISM,        // 集体与个体
                IdiomTheme.COGNITIVE,           // 认知与决策
                IdiomTheme.CONSUMPTION          // 消费与物质
            )
            age <= 45 -> listOf(
                IdiomTheme.COLLECTIVISM,        // 集体与个体
                IdiomTheme.CONSUMPTION,         // 消费与物质
                IdiomTheme.HEALTH,              // 健康与消费品
                IdiomTheme.MENTAL               // 精神与娱乐
            )
            else -> listOf(
                IdiomTheme.LIFE_WISDOM,         // 生活智慧
                IdiomTheme.SOLO_LIVING,         // 独居与自我
                IdiomTheme.LEGACY_CRITIQUE,     // 传统观念反思
                IdiomTheme.PSEUDOSCIENCE        // 伪科学与迷信
            )
        }

        // 从优先主题中收集未发现成语
        val priorityIds = idiomThemeMap
            .filter { it.value in priorityThemes }
            .keys
        val priorityUndiscovered = undiscovered.filter { it.idiomId in priorityIds }

        // 70%概率从优先主题中选，30%从全部未发现中随机
        val pool = if (priorityUndiscovered.isNotEmpty() && Random.nextFloat() < 0.7f) {
            priorityUndiscovered
        } else {
            undiscovered
        }

        return pool.shuffled().take(unlockCount)
    }

    // ============================================
    // 统计与状态
    // ============================================

    /**
     * 获取成语总数
     */
    fun getTotalCount(): Int = IdiomRepository.getAllIdioms().size

    /**
     * 获取某个主题下已发现/未发现的数量
     */
    fun getThemeProgress(theme: IdiomTheme, discoveredIds: Set<Int>): Pair<Int, Int> {
        val ids = idiomThemeMap.filter { it.value == theme }.keys
        val discovered = ids.count { it in discoveredIds }
        return Pair(discovered, ids.size)
    }

    /**
     * 获取所有主题的探索进度
     */
    fun getAllThemeProgress(discoveredIds: Set<Int>): Map<IdiomTheme, Pair<Int, Int>> {
        return IdiomTheme.entries.associateWith { theme ->
            getThemeProgress(theme, discoveredIds)
        }
    }

    /**
     * 获取一条随机的、未发现的成语（用于日常偶然发现）
     */
    fun getRandomUndiscovered(discoveredIds: Set<Int>): IdiomData? {
        val allIdioms = IdiomRepository.getAllIdioms()
        val undiscovered = allIdioms.filter { it.idiomId !in discoveredIds }
        return if (undiscovered.isNotEmpty()) undiscovered.random() else null
    }
}