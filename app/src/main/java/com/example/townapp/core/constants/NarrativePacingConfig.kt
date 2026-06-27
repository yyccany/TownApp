package com.example.townapp.core.constants

import com.example.townapp.data.LifeStage

/**
 * 叙事节奏配置 v4.0 —— 场景强绑定版
 *
 * 核心原则：不做全局盲抽，先判断「年龄+时段+状态」，只从匹配的3~4个文件夹取文案。
 * 13个系统文件夹均匀轮询，每个系统都稳定有出场机会。
 */
object NarrativePacingConfig {

    // ==================== 13个业务系统ID（对应docs下文件夹） ====================
    const val SYS_CORE_LIFE = "core_life"           // 01 核心人生系统
    const val SYS_SOCIAL_PSYCH = "social_psych"     // 02 社交心理系统
    const val SYS_CAREER_ECON = "career_economy"    // 03 职业经济系统
    const val SYS_MATERIAL_LIFE = "material_life"   // 04 物质生活系统
    const val SYS_DIET_HEALTH = "diet_health"       // 05 饮食健康系统
    const val SYS_MEDICAL_HEALTH = "medical_health" // 06 医疗健康系统
    const val SYS_CULTURE_ENT = "culture_ent"       // 07 文化娱乐系统
    const val SYS_COGNITION = "cognition"           // 08 认知反思系统
    const val SYS_COMPANION = "companion_moment"    // 09 互动陪伴系统
    const val SYS_LIFE_EVENTS = "life_event"        // 10 人生事件系统
    const val SYS_PRODUCTS = "product_desc"         // 11 产品分类数据
    const val SYS_ARCHIVES = "archive_memory"       // 12 众生时间档案馆
    const val SYS_NEW_GAME_PLUS = "new_game_plus"   // 13 多周目认知继承
    const val SYS_EXTRA_DOCS = "extra_docs"         // 14 开发文档补充文案池

    // scene目录结构化文案
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

    // 所有14个业务系统
    val ALL_SYSTEMS = listOf(
        SYS_CORE_LIFE, SYS_SOCIAL_PSYCH, SYS_CAREER_ECON, SYS_MATERIAL_LIFE,
        SYS_DIET_HEALTH, SYS_MEDICAL_HEALTH, SYS_CULTURE_ENT, SYS_COGNITION,
        SYS_COMPANION, SYS_LIFE_EVENTS, SYS_PRODUCTS, SYS_ARCHIVES, SYS_NEW_GAME_PLUS,
        SYS_EXTRA_DOCS
    )

    /** 低出场率稀有scene源（彩蛋池，每次tick都保证有机会出） */
    val RARE_SCENE_SOURCES = listOf(
        SCENE_DREAM, SCENE_HIDDEN_NOTE, SCENE_WHISPER, SCENE_OBJECT_OBS,
        SCENE_WORLDVIEW, SCENE_LIFE_WISDOM, SCENE_LIFE_MOMENT, SCENE_SMALL_MOMENT
    )

    // ==================== 分阶段内容数量 ====================
    /** 每次tick强制轮询几个14系统（保证所有系统文档在一天内都能轮完） */
    fun getForcedSystemCountPerTick(age: Int): Int = when {
        age < 30 -> 1
        age < 50 -> 1
        else -> 1
    }

    /** 每次tick从场景权重池抽取几条（scene结构化文案） */
    fun getSceneContentCount(age: Int): Int = when {
        age < 30 -> 1
        age < 50 -> 1
        else -> 1
    }

    /** 每次tick额外彩蛋条数量（低出场率文档：hidden_note/whisper/dream/object_obs等） */
    fun getBonusCount(age: Int): Int = when {
        age < 30 -> 0
        age < 50 -> 0
        else -> 0
    }

    // ==================== 时段定义 ====================
    enum class TimeOfDay {
        NIGHT,        // 深夜 22-6点
        MEAL_TIME,    // 饭点 7-9/11-13/17-19
        WORK_TIME,    // 工作时间 工作日9-11/14-17
        FREE_TIME     // 闲暇时间 其他
    }

    fun getTimeOfDay(hour: Int, isWeekend: Boolean): TimeOfDay = when {
        hour >= 22 || hour < 6 -> TimeOfDay.NIGHT
        hour in 7..9 || hour in 11..13 || hour in 17..19 -> TimeOfDay.MEAL_TIME
        !isWeekend && ((hour in 9..11) || (hour in 14..17)) -> TimeOfDay.WORK_TIME
        else -> TimeOfDay.FREE_TIME
    }

    // ==================== 核心：场景 → 文案池映射（强绑定，不盲抽） ====================
    /**
     * 根据「年龄+时段+身心状态」返回本次tick应该从哪些文件夹取文案
     * 每次只返回3-5个源，保证匹配度，同时13个系统轮询覆盖
     */
    fun getScenarioPools(
        age: Int,
        timeOfDay: TimeOfDay,
        isTired: Boolean,
        isAnxious: Boolean,
        healthLow: Boolean
    ): ScenarioPools {
        val stage = LifeStage.fromAge(age)
        val systemPools = mutableListOf<String>()
        val scenePools = mutableListOf<String>()
        var weights: Map<String, Int> = emptyMap()

        when {
            // ========== 青年阶段 ==========
            stage == LifeStage.YOUTH && timeOfDay == TimeOfDay.WORK_TIME -> {
                systemPools.addAll(listOf(SYS_CAREER_ECON, SYS_SOCIAL_PSYCH, SYS_CULTURE_ENT))
                scenePools.addAll(listOf(SCENE_BEHAVIOR, SCENE_NPC_DIALOG, SCENE_NPC_SEASON_DIALOG, SCENE_MICRO_EVENT))
                weights = mapOf(
                    SYS_CAREER_ECON to 22, SYS_SOCIAL_PSYCH to 18, SCENE_NPC_SEASON_DIALOG to 18,
                    SYS_CULTURE_ENT to 12, SCENE_BEHAVIOR to 12, SCENE_NPC_DIALOG to 10, SCENE_MICRO_EVENT to 8
                )
            }
            stage == LifeStage.YOUTH && timeOfDay == TimeOfDay.NIGHT -> {
                systemPools.addAll(listOf(SYS_CORE_LIFE, SYS_COGNITION, SYS_COMPANION))
                scenePools.addAll(listOf(SCENE_NIGHT_MONO, SCENE_DREAM, SCENE_NPC_MEMORY, SCENE_HIDDEN_NOTE, SCENE_WHISPER))
                weights = mapOf(
                    SCENE_NIGHT_MONO to 18, SCENE_NPC_MEMORY to 18, SYS_CORE_LIFE to 14,
                    SYS_COGNITION to 14, SYS_COMPANION to 12, SCENE_DREAM to 10,
                    SCENE_HIDDEN_NOTE to 7, SCENE_WHISPER to 7
                )
            }
            stage == LifeStage.YOUTH && timeOfDay == TimeOfDay.MEAL_TIME -> {
                systemPools.addAll(listOf(SYS_DIET_HEALTH, SYS_SOCIAL_PSYCH, SYS_MATERIAL_LIFE))
                scenePools.addAll(listOf(SCENE_FOOD, SCENE_NPC_DIALOG, SCENE_NPC_SEASON_DIALOG, SCENE_SMALL_MOMENT))
                weights = mapOf(
                    SCENE_FOOD to 22, SCENE_NPC_SEASON_DIALOG to 20, SCENE_NPC_DIALOG to 15,
                    SYS_DIET_HEALTH to 13, SYS_SOCIAL_PSYCH to 12, SYS_MATERIAL_LIFE to 8, SCENE_SMALL_MOMENT to 10
                )
            }
            stage == LifeStage.YOUTH -> {
                systemPools.addAll(listOf(SYS_CULTURE_ENT, SYS_SOCIAL_PSYCH, SYS_MATERIAL_LIFE, SYS_PRODUCTS))
                scenePools.addAll(listOf(SCENE_BEHAVIOR, SCENE_LEISURE, SCENE_NPC_DIALOG, SCENE_NPC_SEASON_DIALOG, SCENE_SMALL_MOMENT))
                weights = mapOf(
                    SYS_CULTURE_ENT to 17, SCENE_LEISURE to 15, SCENE_NPC_SEASON_DIALOG to 15,
                    SYS_SOCIAL_PSYCH to 12, SCENE_BEHAVIOR to 12, SCENE_NPC_DIALOG to 10,
                    SYS_MATERIAL_LIFE to 8, SCENE_SMALL_MOMENT to 8, SYS_PRODUCTS to 3
                )
            }

            // ========== 中年阶段 ==========
            stage == LifeStage.MIDDLE_AGE && timeOfDay == TimeOfDay.WORK_TIME -> {
                systemPools.addAll(listOf(SYS_CAREER_ECON, SYS_COGNITION, SYS_MEDICAL_HEALTH))
                scenePools.addAll(listOf(SCENE_BEHAVIOR, SCENE_NPC_DIALOG, SCENE_NPC_SEASON_DIALOG, SCENE_MICRO_EVENT))
                weights = mapOf(
                    SYS_CAREER_ECON to 20, SYS_COGNITION to 16, SCENE_BEHAVIOR to 15,
                    SCENE_NPC_SEASON_DIALOG to 15, SCENE_NPC_DIALOG to 12, SYS_MEDICAL_HEALTH to 10, SCENE_MICRO_EVENT to 12
                )
            }
            stage == LifeStage.MIDDLE_AGE && timeOfDay == TimeOfDay.NIGHT -> {
                val nightSystems = mutableListOf(SYS_CORE_LIFE, SYS_COGNITION, SYS_COMPANION)
                if (healthLow || isTired) nightSystems.add(SYS_MEDICAL_HEALTH)
                if (isAnxious) nightSystems.add(SYS_LIFE_EVENTS)
                systemPools.addAll(nightSystems)
                scenePools.addAll(listOf(SCENE_NIGHT_MONO, SCENE_NPC_MEMORY, SCENE_DREAM, SCENE_WHISPER))
                weights = buildMap {
                    put(SCENE_NIGHT_MONO, 18)
                    put(SCENE_NPC_MEMORY, 18)
                    put(SYS_COGNITION, 16)
                    put(SYS_CORE_LIFE, 13)
                    put(SYS_COMPANION, 13)
                    put(SCENE_WHISPER, 10)
                    put(SCENE_DREAM, 7)
                    if (healthLow) put(SYS_MEDICAL_HEALTH, 8)
                    if (isAnxious) put(SYS_LIFE_EVENTS, 8)
                }.filterValues { it > 0 }
            }
            stage == LifeStage.MIDDLE_AGE && timeOfDay == TimeOfDay.MEAL_TIME -> {
                systemPools.addAll(listOf(SYS_DIET_HEALTH, SYS_MATERIAL_LIFE, SYS_MEDICAL_HEALTH))
                scenePools.addAll(listOf(SCENE_FOOD, SCENE_SMALL_MOMENT, SCENE_NPC_DIALOG, SCENE_NPC_SEASON_DIALOG))
                weights = mapOf(
                    SYS_DIET_HEALTH to 18, SCENE_FOOD to 17, SCENE_NPC_SEASON_DIALOG to 17,
                    SYS_MATERIAL_LIFE to 12, SCENE_SMALL_MOMENT to 12, SYS_MEDICAL_HEALTH to 10, SCENE_NPC_DIALOG to 14
                )
            }
            stage == LifeStage.MIDDLE_AGE -> {
                val freeSystems = mutableListOf(SYS_COGNITION, SYS_CORE_LIFE, SYS_CULTURE_ENT, SYS_LIFE_EVENTS)
                if (healthLow) freeSystems.add(SYS_MEDICAL_HEALTH)
                systemPools.addAll(freeSystems)
                scenePools.addAll(listOf(SCENE_LEISURE, SCENE_SMALL_MOMENT, SCENE_BEHAVIOR, SCENE_NPC_SEASON_DIALOG, SCENE_NPC_MEMORY, SCENE_OBJECT_OBS))
                weights = buildMap {
                    put(SYS_COGNITION, 15)
                    put(SYS_CORE_LIFE, 13)
                    put(SCENE_LEISURE, 13)
                    put(SCENE_NPC_SEASON_DIALOG, 13)
                    put(SCENE_NPC_MEMORY, 10)
                    put(SYS_CULTURE_ENT, 10)
                    put(SYS_LIFE_EVENTS, 10)
                    put(SCENE_SMALL_MOMENT, 10)
                    put(SCENE_BEHAVIOR, 8)
                    put(SCENE_OBJECT_OBS, 4)
                    if (healthLow) put(SYS_MEDICAL_HEALTH, 6)
                }.filterValues { it > 0 }
            }

            // ========== 晚年阶段 ==========
            stage == LifeStage.SENIOR -> {
                val seniorSystems = mutableListOf(SYS_ARCHIVES, SYS_CORE_LIFE, SYS_COGNITION, SYS_COMPANION)
                if (healthLow) seniorSystems.add(SYS_MEDICAL_HEALTH)
                seniorSystems.add(SYS_NEW_GAME_PLUS)
                systemPools.addAll(seniorSystems)
                val seniorScenes = when (timeOfDay) {
                    TimeOfDay.NIGHT -> listOf(SCENE_NIGHT_MONO, SCENE_NPC_MEMORY, SCENE_DREAM, SCENE_WHISPER, SCENE_HIDDEN_NOTE)
                    TimeOfDay.MEAL_TIME -> listOf(SCENE_FOOD, SCENE_NPC_SEASON_DIALOG, SCENE_SMALL_MOMENT, SCENE_LEISURE)
                    TimeOfDay.WORK_TIME -> listOf(SCENE_LEISURE, SCENE_NPC_SEASON_DIALOG, SCENE_SMALL_MOMENT, SCENE_NPC_MEMORY, SCENE_OBJECT_OBS)
                    TimeOfDay.FREE_TIME -> listOf(SCENE_LEISURE, SCENE_NPC_SEASON_DIALOG, SCENE_NPC_MEMORY, SCENE_SMALL_MOMENT, SCENE_LIFE_MOMENT, SCENE_OBJECT_OBS)
                }
                scenePools.addAll(seniorScenes)
                weights = buildMap {
                    put(SYS_ARCHIVES, 20)
                    put(SCENE_NPC_MEMORY, 18)
                    put(SYS_CORE_LIFE, 15)
                    put(SYS_COGNITION, 12)
                    put(SYS_COMPANION, 12)
                    put(SCENE_NPC_SEASON_DIALOG, 12)
                    put(SYS_NEW_GAME_PLUS, 8)
                    put(SCENE_NIGHT_MONO, if (timeOfDay == TimeOfDay.NIGHT) 12 else 4)
                    put(SCENE_DREAM, if (timeOfDay == TimeOfDay.NIGHT) 8 else 2)
                    put(SCENE_LEISURE, 8)
                    put(SCENE_SMALL_MOMENT, 8)
                    put(SCENE_WHISPER, if (timeOfDay == TimeOfDay.NIGHT) 10 else 6)
                    put(SCENE_FOOD, if (timeOfDay == TimeOfDay.MEAL_TIME) 10 else 4)
                    put(SYS_MEDICAL_HEALTH, if (healthLow) 8 else 4)
                    put(SCENE_LIFE_MOMENT, 6)
                }.filterValues { it > 0 }
            }

            // ========== 兜底 ==========
            else -> {
                systemPools.addAll(listOf(SYS_CORE_LIFE, SYS_SOCIAL_PSYCH, SYS_CULTURE_ENT))
                scenePools.addAll(listOf(SCENE_BEHAVIOR, SCENE_SMALL_MOMENT, SCENE_LEISURE))
                weights = mapOf(
                    SCENE_BEHAVIOR to 20, SYS_CORE_LIFE to 18, SCENE_SMALL_MOMENT to 15,
                    SYS_SOCIAL_PSYCH to 15, SYS_CULTURE_ENT to 12, SCENE_LEISURE to 10,
                    SCENE_NPC_DIALOG to 10
                )
            }
        }

        return ScenarioPools(
            systemIds = systemPools.distinct(),
            sceneTypes = scenePools.distinct(),
            weights = weights
        )
    }

    data class ScenarioPools(
        val systemIds: List<String>,   // 从哪些系统文件夹取
        val sceneTypes: List<String>, // 从哪些scene结构化文案取
        val weights: Map<String, Int> // 每个源的权重
    )

    // ==================== 状态波动幅度（按年龄段） ====================
    data class StateFluctuation(
        val moodAmplitude: Int,
        val energyAmplitude: Int,
        val fatigueAmplitude: Int,
        val anxietyAmplitude: Int
    )

    fun getStateFluctuation(age: Int): StateFluctuation = when {
        age < 30 -> StateFluctuation(4, 4, 3, 3)
        age < 50 -> StateFluctuation(3, 3, 3, 3)
        else -> StateFluctuation(2, 2, 2, 2)
    }

    // ==================== 时间流速配置（分模式） ====================
    data class TimePace(
        val minHoursPerTick: Int,
        val maxHoursPerTick: Int,
        val minIntervalMs: Long,
        val maxIntervalMs: Long
    )

    fun getAutoPace(age: Int): TimePace = when {
        age < 25 -> TimePace(1, 1, 3500L, 6000L)
        age < 30 -> TimePace(1, 1, 3000L, 5000L)
        age < 40 -> TimePace(1, 2, 3000L, 5000L)
        age < 50 -> TimePace(1, 2, 2500L, 4500L)
        age < 60 -> TimePace(1, 2, 2500L, 4000L)
        age < 70 -> TimePace(2, 3, 2000L, 3500L)
        else -> TimePace(2, 4, 2000L, 3500L)
    }

    fun getManualPace(age: Int): TimePace = when {
        age < 60 -> TimePace(1, 1, 4000L, 7000L)
        else -> TimePace(1, 1, 4000L, 7000L)
    }

    // ==================== 行为池权重（按年龄段） ====================
    fun getBehaviorWeights(age: Int, scoreOverall: Int): Map<String, Int> {
        val weights = mutableMapOf<String, Int>()
        val stage = LifeStage.fromAge(age)

        weights["home_rest"] = if (stage == LifeStage.SENIOR) 20 else 10
        weights["home_cook"] = 8
        if (scoreOverall <= 55 || Math.random() < 0.25) {
            weights["home_decor_luxury"] = if (age < 40) 6 else 2
        }
        weights["park_walk"] = if (stage == LifeStage.SENIOR) 15 else 8
        weights["park_sit"] = if (stage == LifeStage.SENIOR) 12 else 5
        if (scoreOverall >= 45 || Math.random() < 0.35) {
            weights["park_feed_cat"] = 7
        }
        weights["market_just_look"] = 6
        weights["market_buy_fresh"] = 8
        if (Math.random() < 0.5) {
            weights["market_chat_vendor"] = if (age >= 40) 8 else 5
        }
        if (age < 50 && (scoreOverall <= 55 || Math.random() < 0.2)) {
            weights["luxury_window_shop"] = if (age < 35) 8 else 4
            weights["luxury_leave"] = if (age < 35) 5 else 2
        }
        weights["clinic_checkup"] = when {
            age >= 60 -> 12
            age >= 40 -> 6
            else -> 3
        }
        if (Math.random() < 0.35) {
            weights["clinic_chat_doctor"] = if (age >= 50) 8 else 4
        }
        if (age < 60) {
            weights["work_office"] = 12
            weights["work_chat"] = if (age < 40) 8 else 5
            if (age < 30) weights["work_graduate"] = 8
        }
        weights["npc_chat"] = if (age < 50) 8 else 5
        weights["npc_listen"] = if (age >= 30) 8 else 4

        return weights
    }
}
