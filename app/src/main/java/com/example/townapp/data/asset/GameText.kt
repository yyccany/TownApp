package com.example.townapp.data.asset

object GameText {
    private const val SCENE_DIR = "docs/scene/"
    private const val SYSTEM_DIR = "docs/"

    // ==================== v4.0 公开类型ID（供调度使用） ====================
    const val SYS_CORE_LIFE = "core_life"
    const val SYS_SOCIAL_PSYCH = "social_psych"
    const val SYS_CAREER_ECON = "career_economy"
    const val SYS_MATERIAL_LIFE = "material_life"
    const val SYS_DIET_HEALTH = "diet_health"
    const val SYS_MEDICAL_HEALTH = "medical_health"
    const val SYS_CULTURE_ENT = "culture_ent"
    const val SYS_COGNITION = "cognition"
    const val SYS_COMPANION = "companion_moment"
    const val SYS_LIFE_EVENTS = "life_event"
    const val SYS_PRODUCTS = "product_desc"
    const val SYS_ARCHIVES = "archive_memory"
    const val SYS_NEW_GAME_PLUS = "new_game_plus"
    const val SYS_EXTRA_DOCS = "extra_docs"

    const val SCENE_BEHAVIOR = "behavior"
    const val SCENE_NPC_DIALOG = "npc_dialog"
    const val SCENE_MICRO_EVENT = "micro_event"
    const val SCENE_NIGHT_MONO = "night_monologue"
    const val SCENE_DREAM = "dream"
    const val SCENE_HIDDEN_NOTE = "hidden_note"
    const val SCENE_WHISPER = "whisper"
    const val SCENE_SMALL_MOMENT = "small_moment"
    const val SCENE_LEISURE = "leisure"
    const val SCENE_OBJECT_OBS = "object_obs"
    const val SCENE_FOOD = "food_desc"
    const val SCENE_LIFE_MOMENT = "life_moment"
    const val SCENE_WORLDVIEW = "worldview"
    const val SCENE_LIFE_WISDOM = "life_wisdom"
    const val SCENE_NPC_SEASON_DIALOG = "npc_season_dialog"
    const val SCENE_NPC_MEMORY = "npc_memory"

    const val BUILDING_INTERACT = "${SCENE_DIR}building_interact.md"
    const val BEHAVIOR_RESPONSE = "${SCENE_DIR}behavior_response.md"
    const val NPC_DIALOG = "${SCENE_DIR}npc_dialog.md"
    const val NIGHT_MONOLOGUE = "${SCENE_DIR}night_monologue.md"
    const val HIDDEN_NOTES = "${SCENE_DIR}hidden_notes.md"
    const val MICRO_EVENTS = "${SCENE_DIR}micro_events.md"

    private const val NPC_TEXT_DIR = "text/npc/"
    private val NPC_DIALOG_SPRING = "${NPC_TEXT_DIR}dialog/spring.json"
    private val NPC_DIALOG_SUMMER = "${NPC_TEXT_DIR}dialog/summer.json"
    private val NPC_DIALOG_AUTUMN = "${NPC_TEXT_DIR}dialog/autumn.json"
    private val NPC_DIALOG_WINTER = "${NPC_TEXT_DIR}dialog/winter.json"
    private val NPC_MEMORY_1 = "${NPC_TEXT_DIR}memory/memory_group_1.json"
    private val NPC_MEMORY_2 = "${NPC_TEXT_DIR}memory/memory_group_2.json"

    private val CORE_LIFE = "${SYSTEM_DIR}01-核心人生系统/基础设定.md"
    private val SOCIAL_PSYCH = "${SYSTEM_DIR}02-社交心理系统/基础设定.md"
    private val CAREER_ECON = "${SYSTEM_DIR}03-职业经济系统/基础设定.md"
    private val MATERIAL_LIFE = "${SYSTEM_DIR}04-物质生活系统/基础设定.md"
    private val DIET_HEALTH = "${SYSTEM_DIR}05-饮食健康系统/基础设定.md"
    private val MEDICAL_HEALTH = "${SYSTEM_DIR}06-医疗健康系统/基础设定.md"
    private val CULTURE_ENT = "${SYSTEM_DIR}07-文化娱乐系统/基础设定.md"
    private val COGNITION = "${SYSTEM_DIR}08-认知反思系统/基础设定.md"
    private val COMPANION = "${SYSTEM_DIR}09-互动陪伴系统/基础设定.md"
    private val LIFE_EVENTS = "${SYSTEM_DIR}10-人生事件系统/基础设定.md"
    private val PRODUCTS = "${SYSTEM_DIR}11-产品分类数据/基础设定.md"
    private val ARCHIVES = "${SYSTEM_DIR}12-众生时间档案馆/基础设定.md"
    private val NEW_GAME_PLUS = "${SYSTEM_DIR}13-多周目认知继承/基础设定.md"
    private val EXTRA_DEV_DOCS = "${SYSTEM_DIR}14-extra/基础设定.md"

    private val ALL_SYSTEM_FILES = listOf(
        CORE_LIFE, SOCIAL_PSYCH, CAREER_ECON, MATERIAL_LIFE,
        DIET_HEALTH, MEDICAL_HEALTH, CULTURE_ENT, COGNITION,
        COMPANION, LIFE_EVENTS, PRODUCTS, ARCHIVES, NEW_GAME_PLUS,
        EXTRA_DEV_DOCS
    )

    private const val NARRATIVE_HEADING = "## 随机叙事文案池"

    private fun loader() = TextAssetLoader.get()

    fun menuLabel(key: String): String {
        return loader().getMarkdownField(BUILDING_INTERACT, key, "菜单名称").ifEmpty { key }
    }

    fun optionTendency(key: String): Int? {
        return loader().getMarkdownAttribute(BUILDING_INTERACT, key, "tendency")?.toIntOrNull()
    }

    data class Reaction(
        val eventLog: String,
        val petDialog: String,
        val scoreDelta: Int
    )

    fun reaction(key: String, consumptionScore: Int? = null): Reaction {
        val filters = mapOf("key" to key)
        val entry = loader().getRandomMarkdownEntry(BEHAVIOR_RESPONSE, filters, consumptionScore)
        val fields = entry?.fields ?: emptyMap()
        val attrs = entry?.attributes ?: emptyMap()
        val scoreDelta = attrs["score_delta"]?.toIntOrNull() ?: 0

        return Reaction(
            eventLog = fields["事件日志"] ?: "",
            petDialog = fields["陪伴对话"] ?: "",
            scoreDelta = scoreDelta
        )
    }

    fun randomNightMonologue(
        sleepStatus: String,
        emotion: String? = null,
        fatigue: Int? = null,
        anxiety: Int? = null,
        trauma: Int? = null
    ): String {
        val filters = mutableMapOf<String, String>()
        filters["sleep_status"] = sleepStatus
        if (emotion != null) filters["emotion"] = emotion
        val score = when {
            anxiety != null -> anxiety
            trauma != null -> trauma
            fatigue != null -> fatigue
            else -> null
        }
        return loader().getRandomMarkdownText(NIGHT_MONOLOGUE, filters, score)
    }

    fun randomDream(dreamType: String): String {
        return loader().getRandomMarkdownText(
            NIGHT_MONOLOGUE,
            mapOf("dream_type" to dreamType),
            fieldName = "梦境内容"
        )
    }

    fun dreamEmoji(dreamType: String): String {
        return loader().getRandomMarkdownText(
            NIGHT_MONOLOGUE,
            mapOf("dream_type" to dreamType),
            fieldName = "emoji"
        ).ifEmpty {
            when (dreamType) {
                "peaceful" -> "💤"
                "anxious" -> "😰"
                "memory" -> "🌙"
                "childhood" -> "🧸"
                "prophetic" -> "✨"
                else -> "💤"
            }
        }
    }

    fun randomHiddenNote(): String {
        return loader().getRandomMarkdownText(HIDDEN_NOTES)
    }

    fun randomMicroEvent(type: String? = null, season: String? = null, emotion: String? = null): String {
        val filters = mutableMapOf<String, String>()
        if (type != null) filters["type"] = type
        if (season != null) filters["season"] = season
        if (emotion != null) filters["emotion"] = emotion
        return loader().getRandomMarkdownText(MICRO_EVENTS, if (filters.isEmpty()) null else filters)
    }

    fun randomSeasonalMicroEvent(season: String): String {
        val seasonalMatches = loader().getAllMarkdownEntries(MICRO_EVENTS).filter { entry ->
            entry.attributes["season"] == season
        }
        return if (seasonalMatches.isNotEmpty()) {
            seasonalMatches[kotlin.random.Random.nextInt(seasonalMatches.size)].fields.values.firstOrNull()
                ?: randomMicroEvent()
        } else {
            randomMicroEvent()
        }
    }

    fun npcDialog(type: String, identity: String? = null, consumptionScore: Int? = null): String {
        val filters = mutableMapOf<String, String>()
        filters["type"] = type
        if (identity != null) filters["identity"] = identity
        return loader().getRandomMarkdownText(NPC_DIALOG, filters, consumptionScore)
    }

    fun companionQuote(category: String? = null, emotion: String? = null): String {
        val filters = mutableMapOf<String, String>()
        if (category != null) filters["category"] = category
        if (emotion != null) filters["emotion"] = emotion
        return loader().getRandomMarkdownText(HIDDEN_NOTES, if (filters.isEmpty()) null else filters)
    }

    fun welcomeQuote(): String = companionQuote("welcome")

    fun comfortQuote(emotion: String? = null): String = companionQuote("comfort", emotion)

    fun tiredRestQuote(): String = companionQuote("tired_rest")

    fun randomWhisper(): String = companionQuote("whisper")

    fun lifeEvent(
        type: String? = null,
        season: String? = null
    ): String {
        val filters = mutableMapOf<String, String>()
        if (type != null) filters["type"] = type
        if (season != null) filters["season"] = season
        return loader().getRandomMarkdownText(MICRO_EVENTS, if (filters.isEmpty()) null else filters)
    }

    fun randomLeisureEvent(season: String? = null): String = lifeEvent("leisure", season)

    fun randomSmallMoment(): String = lifeEvent("small_moment")

    fun randomWorldviewQuote(): String {
        return loader().getRandomPlainTextLineFromPaths(listOf(CORE_LIFE, COGNITION), NARRATIVE_HEADING)
    }

    fun randomLeisureDescription(): String {
        return loader().getRandomPlainTextLine(CULTURE_ENT, NARRATIVE_HEADING)
    }

    fun randomLifeWisdom(): String {
        return loader().getRandomPlainTextLineFromPaths(listOf(CORE_LIFE, COGNITION, SOCIAL_PSYCH), NARRATIVE_HEADING)
    }

    fun randomFoodDescription(): String {
        return loader().getRandomPlainTextLine(DIET_HEALTH, NARRATIVE_HEADING)
    }

    fun randomObjectObservation(): String {
        return loader().getRandomPlainTextLineFromPaths(listOf(MATERIAL_LIFE, PRODUCTS), NARRATIVE_HEADING)
    }

    fun randomLifeMoment(): String {
        return loader().getRandomPlainTextLineFromPaths(ALL_SYSTEM_FILES.filter { it != EXTRA_DEV_DOCS }, NARRATIVE_HEADING)
    }

    // ==================== 各系统专属文案随机访问（叙事池section+强过滤版） ====================

    fun randomCoreLifeThought(): String {
        return loader().getRandomPlainTextLine(CORE_LIFE, NARRATIVE_HEADING)
    }

    fun randomSocialPsychThought(): String {
        return loader().getRandomPlainTextLine(SOCIAL_PSYCH, NARRATIVE_HEADING)
    }

    fun randomCareerEconomy(): String {
        return loader().getRandomPlainTextLine(CAREER_ECON, NARRATIVE_HEADING)
    }

    fun randomMaterialLifeDetail(): String {
        return loader().getRandomPlainTextLine(MATERIAL_LIFE, NARRATIVE_HEADING)
    }

    fun randomDietHealthTip(): String {
        return loader().getRandomPlainTextLine(DIET_HEALTH, NARRATIVE_HEADING)
    }

    fun randomMedicalHealth(): String {
        return loader().getRandomPlainTextLine(MEDICAL_HEALTH, NARRATIVE_HEADING)
    }

    fun randomCultureEntertainment(): String {
        return loader().getRandomPlainTextLine(CULTURE_ENT, NARRATIVE_HEADING)
    }

    fun randomCognitionReflection(): String {
        return loader().getRandomPlainTextLine(COGNITION, NARRATIVE_HEADING)
    }

    fun randomCompanionMoment(): String {
        return loader().getRandomPlainTextLine(COMPANION, NARRATIVE_HEADING)
    }

    fun randomLifeEventNarrative(): String {
        return loader().getRandomPlainTextLine(LIFE_EVENTS, NARRATIVE_HEADING)
    }

    fun randomProductDescription(): String {
        return loader().getRandomPlainTextLine(PRODUCTS, NARRATIVE_HEADING)
    }

    fun randomArchiveMemory(): String {
        return loader().getRandomPlainTextLine(ARCHIVES, NARRATIVE_HEADING)
    }

    fun randomNewGamePlusInsight(): String {
        return loader().getRandomPlainTextLine(NEW_GAME_PLUS, NARRATIVE_HEADING)
    }

    fun randomExtraDevDocs(): String {
        return loader().getRandomPlainTextLineScanAll(EXTRA_DEV_DOCS)
    }

    /**
     * NPC季节对话：按当前季节、消耗分数（亲密度）随机抽取
     * @param season "spring"/"summer"/"autumn"/"winter"
     * @param scoreOverall 消耗分数 0-100，决定对话层级（low/mid/high = surface/observe/wise）
     */
    fun randomNpcSeasonDialog(season: String? = null, scoreOverall: Int = 50): String {
        val seasonFile = when (season) {
            "summer" -> NPC_DIALOG_SUMMER
            "autumn" -> NPC_DIALOG_AUTUMN
            "winter" -> NPC_DIALOG_WINTER
            else -> NPC_DIALOG_SPRING
        }
        val intimacyTier = when {
            scoreOverall <= 40 -> "low"
            scoreOverall >= 60 -> "high"
            else -> "mid"
        }
        val allEntries = loader().getAllText(seasonFile)
        val candidates = allEntries.filterKeys { key ->
            key.contains("_${intimacyTier}_")
        }
        if (candidates.isEmpty()) {
            val fallback = allEntries.values.filter { it.isNotBlank() }
            return if (fallback.isNotEmpty()) fallback.random() else ""
        }
        return candidates.values.random()
    }

    /** NPC记忆故事：随机抽一条NPC的人生回忆 */
    fun randomNpcMemory(): String {
        val allMemory1 = loader().getAllText(NPC_MEMORY_1)
        val allMemory2 = loader().getAllText(NPC_MEMORY_2)
        val allEntries = allMemory1 + allMemory2
        val storyCandidates = mutableListOf<String>()
        for ((key, value) in allEntries) {
            if (key.endsWith("_content")) {
                storyCandidates.add(value)
            } else if (!key.contains("_title") && !key.contains("|") && value.length > 15) {
                storyCandidates.add(value)
            }
        }
        return if (storyCandidates.isNotEmpty()) storyCandidates.random() else ""
    }

    /**
     * 根据年龄段随机抽取对应系统的文案
     */
    fun randomAgeAppropriate(age: Int): String {
        val pools = when {
            age < 25 -> listOf(
                CAREER_ECON, SOCIAL_PSYCH, CULTURE_ENT, CORE_LIFE,
                MATERIAL_LIFE, COMPANION
            )
            age < 40 -> listOf(
                CAREER_ECON, SOCIAL_PSYCH, CORE_LIFE, COGNITION,
                DIET_HEALTH, MATERIAL_LIFE, MEDICAL_HEALTH
            )
            age < 60 -> listOf(
                CORE_LIFE, COGNITION, MEDICAL_HEALTH, LIFE_EVENTS,
                SOCIAL_PSYCH, DIET_HEALTH, ARCHIVES
            )
            else -> listOf(
                CORE_LIFE, ARCHIVES, COGNITION, MEDICAL_HEALTH,
                NEW_GAME_PLUS, LIFE_EVENTS, COMPANION
            )
        }
        return loader().getRandomPlainTextLineFromPaths(pools).ifEmpty {
            randomAnySystemLine()
        }
    }

    fun randomAnySystemLine(): String {
        return loader().getRandomPlainTextLineFromPaths(ALL_SYSTEM_FILES)
    }

    /**
     * v4.0 统一分发入口：根据类型ID调取对应文案
     * 系统ID对应13个业务文件夹，sceneType对应scene目录结构化文案
     * @param timeOfDay "night"/"meal"/"work"/"free" 影响behavior和事件类型选择
     * 保证永不返回空字符串
     */
    fun randomByType(
        typeId: String,
        age: Int = 30,
        scoreOverall: Int = 50,
        timeOfDay: String = "free",
        isTired: Boolean = false,
        isAnxious: Boolean = false,
        season: String? = null
    ): String {
        val result = when (typeId) {
            // 13个业务系统
            SYS_CORE_LIFE -> randomCoreLifeThought()
            SYS_SOCIAL_PSYCH -> randomSocialPsychThought()
            SYS_CAREER_ECON -> randomCareerEconomy()
            SYS_MATERIAL_LIFE -> randomMaterialLifeDetail()
            SYS_DIET_HEALTH -> randomDietHealthTip()
            SYS_MEDICAL_HEALTH -> randomMedicalHealth()
            SYS_CULTURE_ENT -> randomCultureEntertainment()
            SYS_COGNITION -> randomCognitionReflection()
            SYS_COMPANION -> randomCompanionMoment()
            SYS_LIFE_EVENTS -> randomLifeEventNarrative()
            SYS_PRODUCTS -> randomProductDescription()
            SYS_ARCHIVES -> randomArchiveMemory()
            SYS_NEW_GAME_PLUS -> randomNewGamePlusInsight()
            SYS_EXTRA_DOCS -> randomExtraDevDocs()

            // scene目录结构化文案（按场景随机选择）
            SCENE_BEHAVIOR -> {
                val pool = when (timeOfDay) {
                    "night" -> listOf("home_rest")
                    "meal" -> listOf("home_cook", "market_buy_fresh")
                    "work" -> listOf("work_office", "work_chat", "work_media", "work_graduate")
                    else -> listOf(
                        "home_rest", "home_cook", "park_walk", "park_sit", "park_feed_cat",
                        "market_just_look", "market_buy_fresh", "market_chat_vendor",
                        "luxury_window_shop", "luxury_leave", "clinic_checkup", "npc_chat", "npc_listen"
                    )
                }
                reaction(pool.random(), scoreOverall).eventLog
            }
            SCENE_NPC_DIALOG -> {
                npcDialog(
                    type = if (scoreOverall >= 60) "practical" else if (scoreOverall <= 40) "vanity" else "neutral",
                    consumptionScore = scoreOverall
                )
            }
            SCENE_MICRO_EVENT -> {
                val typePool = when (timeOfDay) {
                    "work" -> listOf("mood", "body", "weather", "season")
                    "meal" -> listOf("small_moment", "season", "mood")
                    "night" -> listOf("weather", "mood", "body")
                    else -> listOf("body", "housework", "weather", "mood", "leisure", "small_moment", "season")
                }
                randomMicroEvent(
                    type = typePool.random(),
                    season = season,
                    emotion = when {
                        isAnxious -> "anxious"
                        !isTired -> "happy"
                        else -> null
                    }
                )
            }
            SCENE_NIGHT_MONO -> {
                val (sleepStatus, emotion) = when {
                    isAnxious -> "insomnia" to "anxious"
                    isTired -> "restless" to "calm"
                    else -> "asleep" to "calm"
                }
                randomNightMonologue(
                    sleepStatus = sleepStatus,
                    emotion = emotion,
                    fatigue = if (isTired) 70 else 30,
                    anxiety = if (isAnxious) 70 else 30,
                    trauma = 20
                ).ifEmpty {
                    listOf("insomnia", "restless", "asleep", "deep").random().let { fallbackStatus ->
                        randomNightMonologue(sleepStatus = fallbackStatus, fatigue = 30, anxiety = 30, trauma = 20)
                    }
                }
            }
            SCENE_DREAM -> {
                val dreamPool = when {
                    isAnxious -> listOf("anxious", "prophetic", "memory")
                    isTired -> listOf("peaceful", "memory", "childhood")
                    else -> listOf("peaceful", "memory", "childhood", "prophetic")
                }
                randomDream(dreamPool.random())
            }
            SCENE_HIDDEN_NOTE -> randomHiddenNote()
            SCENE_WHISPER -> randomWhisper()
            SCENE_SMALL_MOMENT -> randomSmallMoment()
            SCENE_LEISURE -> randomLeisureDescription().ifEmpty { randomLeisureEvent(season = season) }
            SCENE_OBJECT_OBS -> randomObjectObservation()
            SCENE_FOOD -> randomFoodDescription().ifEmpty { randomDietHealthTip() }
            SCENE_LIFE_MOMENT -> randomLifeMoment()
            SCENE_WORLDVIEW -> randomWorldviewQuote()
            SCENE_LIFE_WISDOM -> randomLifeWisdom()
            SCENE_NPC_SEASON_DIALOG -> randomNpcSeasonDialog(season = season, scoreOverall = scoreOverall)
            SCENE_NPC_MEMORY -> randomNpcMemory()

            else -> randomLifeMoment()
        }
        return result.ifEmpty { randomAnySystemLine() }
    }
}
