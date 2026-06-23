package com.example.townapp.feature.human_narrative.npc.repository

import android.util.Log
import com.example.townapp.data.asset.TextAssetLoader
import com.example.townapp.domain.model.CognitionState
import com.example.townapp.feature.human_narrative.npc.model.NpcBase
import com.example.townapp.feature.human_narrative.npc.model.NpcDisplayVo
import com.example.townapp.feature.human_narrative.npc.model.NpcDialogTrigger
import com.example.townapp.feature.human_narrative.npc.model.NpcStatus
import com.example.townapp.feature.human_narrative.npc.model.NpcTimelineVo
import com.example.townapp.feature.human_narrative.npc.model.TimelineNode
import com.example.townapp.feature.human_narrative.npc.model.NodeType

/**
 * NPC 数据仓库（关键收口层）
 *
 * 严格遵循小镇项目铁律：
 * 1. 所有多条件查询 / 筛选 / 组合逻辑只在这里写一次
 * 2. UI / ViewModel 一律禁止直接调用 NpcDao
 * 3. UI / ViewModel 一律禁止手写 where 条件
 * 4. 对外只暴露"按业务语义命名"的方法，不暴露 Dao 内部细节
 * 5. 数字 ID → 完整文本 的转换只在这里完成，输出给上层的都是现成文字
 *
 * 对标认知模块：CognitiveTipRepository
 */
class NpcRepository(
    private val dao: NpcDao,
    private val textLoader: TextAssetLoader,
    internal val presetNpcs: MutableList<NpcBase> = mutableListOf()
) {
    // ================== 文本路径常量（集中管理，一处修改全局生效） ==================

    private companion object {
        const val PATH_NPC_NAME = "text/npc/name.json"
        const val PATH_NPC_JOB = "text/npc/job.json"
        const val PATH_NPC_SEASON = "text/npc/season.json"
        const val PATH_NPC_DIALOG = "text/npc/dialog/"
        const val PATH_NPC_MEMORY = "text/npc/memory/"
        const val PATH_NPC_TONE_PALETTE = "text/npc/tone_palette.json"
    }

    // ================== 公开辅助方法（供调度器使用） ==================

    /**
     * 根据 NPC ID 获取基础信息（供 NpcTextDispatchManager 使用）
     */
    fun getNpcById(npcId: Int): NpcBase? = dao.queryById(npcId)

    /**
     * 获取默认年龄段描述（供调度器回退使用）
     */
    fun getDefaultAgeDescription(jobId: Int, age: Int): String = getAgeDescription(jobId, age)

    // ================== 文本读取（ID → 完整文本，只在这里转换） ==================

    /** 获取 NPC 姓名 */
    private fun getNameById(nameTextId: Int): String {
        return textLoader.getText(PATH_NPC_NAME, "name_$nameTextId")
    }

    /** 获取职业名称 */
    private fun getJobById(jobId: Int): String {
        return textLoader.getText(PATH_NPC_JOB, "job_$jobId")
    }

    /**
     * 职业 ID → 消费认知 pathId 映射
     * 将 jobId 转换为 ConsumerCognitionData 中的 pathId
     */
    private fun jobIdToCognitionPathId(jobId: Int): String? {
        return when (jobId) {
            3 -> "migrant_youth"
            13 -> "housewife"
            9 -> "delivery_rider"
            14 -> "fresh_graduate"
            27 -> "construction_worker"
            5 -> "shop_owner"
            24 -> "adult_child"
            12 -> "retired_worker"
            11 -> "freelancer"
            19 -> "civil_servant"
            6 -> "programmer"
            7 -> "migrant_youth"
            8 -> "migrant_youth"
            10 -> "shop_owner"
            15 -> "delivery_rider"
            16 -> "migrant_youth"
            17 -> "housewife"
            18 -> "construction_worker"
            20 -> "shop_owner"
            21 -> "shop_owner"
            22 -> "construction_worker"
            23 -> "migrant_youth"
            25 -> "shop_owner"
            26 -> "delivery_rider"
            28 -> "shop_owner"
            29 -> "shop_owner"
            30 -> "migrant_youth"
            else -> null
        }
    }

    /** 获取季节名称 */
    private fun getSeasonById(seasonId: Int): String {
        return textLoader.getText(PATH_NPC_SEASON, "season_$seasonId")
    }

    /**
     * 根据触发条件匹配对话文本
     *
     * 落实「以人为本」：
     * - 高认知等级解锁更客观、理性的对话视角
     * - 自动降级匹配：当精确 key 不存在时尝试 fallback
     */
    private fun getDialogByTrigger(trigger: NpcDialogTrigger): String {
        val seasonFileName = when (trigger.seasonId) {
            1 -> "spring.json"
            2 -> "summer.json"
            3 -> "autumn.json"
            4 -> "winter.json"
            else -> "spring.json"
        }
        val filePath = "${PATH_NPC_DIALOG}$seasonFileName"

        // 优先尝试精确匹配
        var text = textLoader.getText(filePath, trigger.dialogKey)
        if (text.isNotEmpty()) return text

        // 尝试降级匹配（保持同认知等级，降低好感要求）
        for (key in trigger.fallbackKeys) {
            text = textLoader.getText(filePath, key)
            if (text.isNotEmpty()) return text
        }

        // 最后兜底：使用最基础的 key
        return textLoader.getText(filePath, "job_${trigger.jobId}_season_${trigger.seasonId}_low_surface")
    }

    /** 获取好感分级标签 */
    private fun getFavorLabel(favor: Int): String {
        return when {
            favor < 30 -> "低"
            favor < 70 -> "中"
            else -> "高"
        }
    }

    // ================== NpcBase → NpcDisplayVo 转换（核心转换逻辑） ==================

    /**
     * 将底层 NpcBase + NpcStatus 转换为展示模型
     * 【唯一转换入口】所有数字 ID 在此转换为完整文本
     *
     * 扩展支持认知等级联动：
     * - 高认知等级自动切换到更客观、理性的对话版本
     * - 二周目及以上直接解锁智慧者视角（通透对话）
     */
    private fun toDisplayVo(base: NpcBase, status: NpcStatus): NpcDisplayVo {
        // 获取当前可用的对话视角等级（支持多周目加成）
        val dialogueLevel = CognitionState.getAvailableDialogueLevel()

        val trigger = NpcDialogTrigger(
            jobId = base.jobId,
            seasonId = status.currentSeasonId,
            favor = status.favor,
            intimacy = status.intimacy,
            cognitiveLevel = dialogueLevel
        )
        return NpcDisplayVo(
            npcId = base.id,
            jobId = base.jobId,
            name = getNameById(base.nameTextId),
            jobName = getJobById(base.jobId),
            seasonName = getSeasonById(status.currentSeasonId),
            x = base.x,
            y = base.y,
            portraitId = base.portraitId,
            favor = status.favor,
            intimacy = status.intimacy,
            currentDialog = getDialogByTrigger(trigger),
            favorLabel = getFavorLabel(status.favor)
        )
    }

    // ================== NPC 基础信息查询（输出 NpcDisplayVo） ==================

    /**
     * 按 ID 查询 NPC（输出展示模型）
     */
    fun getNpcDisplayById(npcId: Int): NpcDisplayVo? {
        val base = dao.queryById(npcId) ?: return null
        val status = getNpcStatus(npcId, defaultSeasonId = base.seasonId)
        return toDisplayVo(base, status)
    }

    /**
     * 按季节获取当前可见 NPC（输出展示模型列表）
     * 【唯一入口】其他任何地方不准再写 seasonId 筛选
     */
    fun getNpcsDisplayBySeason(seasonId: Int): List<NpcDisplayVo> {
        return dao.queryBySeason(seasonId).map { base ->
            val status = getNpcStatus(base.id, defaultSeasonId = base.seasonId)
            toDisplayVo(base, status)
        }
    }

    /**
     * 按职业 + 季节组合查询（输出展示模型列表）
     * 【唯一入口】复合条件查询统一收口
     */
    fun getNpcsDisplayByJobAndSeason(jobId: Int, seasonId: Int): List<NpcDisplayVo> {
        return dao.queryByJob(jobId).filter { it.seasonId == seasonId }.map { base ->
            val status = getNpcStatus(base.id, defaultSeasonId = base.seasonId)
            toDisplayVo(base, status)
        }
    }

    /**
     * 按区域 + 季节组合查询（输出展示模型列表）
     */
    fun getNpcsDisplayByDistrictAndSeason(districtId: Int, seasonId: Int): List<NpcDisplayVo> {
        return dao.queryByDistrict(districtId).filter { it.seasonId == seasonId }.map { base ->
            val status = getNpcStatus(base.id, defaultSeasonId = base.seasonId)
            toDisplayVo(base, status)
        }
    }

    // ================== NPC 状态查询与更新 ==================

    /**
     * 获取 NPC 动态状态（无记录时返回默认状态）
     */
    fun getNpcStatus(npcId: Int, defaultSeasonId: Int = 1): NpcStatus {
        return dao.queryStatus(npcId) ?: NpcStatus.default(npcId, defaultSeasonId)
    }

    /**
     * 更新好感度（自动 clamp 在 [0, 100]）
     */
    fun updateFavor(npcId: Int, delta: Int) {
        val current = getNpcStatus(npcId)
        val newFavor = (current.favor + delta).coerceIn(0, 100)
        dao.updateStatus(current.copy(
            favor = newFavor,
            lastInteractionTime = System.currentTimeMillis()
        ))
        if (newFavor >= 70 && current.unlockedDialogLevel < 2) {
            dao.updateStatus(current.copy(unlockedDialogLevel = 2))
        }
    }

    /**
     * 更新亲密度
     */
    fun updateIntimacy(npcId: Int, delta: Int) {
        val current = getNpcStatus(npcId)
        dao.updateStatus(current.copy(
            intimacy = (current.intimacy + delta).coerceIn(0, 100),
            lastInteractionTime = System.currentTimeMillis()
        ))
    }

    /**
     * 切换 NPC 所在季节
     */
    fun changeSeason(npcId: Int, newSeasonId: Int) {
        val current = getNpcStatus(npcId)
        dao.updateStatus(current.copy(currentSeasonId = newSeasonId))
    }

    // ================== 批量接口（供 NpcLifecycleManager 调用） ==================

    /**
     * 批量更新 NPC 状态
     * 【唯一入口】NpcLifecycleManager 每年调用一次此方法
     */
    fun batchUpdateStatus(statuses: List<NpcStatus>) {
        dao.batchUpdateStatus(statuses)
    }

    /**
     * 获取所有 NPC 状态（用于全局年度迭代）
     */
    fun getAllStatuses(): List<NpcStatus> {
        return dao.queryAllStatusForLifecycle()
    }

    // ================== 记忆碎片加载（markId → MemoryFragmentVo） ==================

    /**
     * 根据 markId + 群组 ID 加载记忆碎片展示模型
     * 【唯一入口】所有记忆碎片的 ID → 文本转换只在这里完成
     *
     * 认知联动（落实「以人为本」）：
     * - 同一段回忆，根据玩家当前认知等级自动选择最匹配解读
     * - 低认知：看见委屈、不甘
     * - 中认知：理解时代、生计、局限
     * - 高认知：接纳身不由己，自然和解
     *
     * @param markId 记忆标记 ID（数字）
     * @param groupId 记忆组 ID（用于定位 JSON 文件）
     * @param currentYear 当前游戏年份
     * @param currentPlayerAge 当前玩家年龄
     * @param seasonId 记忆发生时的季节 ID
     * @param relatedNpcId 关联 NPC ID（可选，0 表示无关联）
     * @return 展示模型，文件不存在时返回 null
     */
    fun getMemoryFragment(
        markId: Int,
        groupId: Int,
        currentYear: Int,
        currentPlayerAge: Int,
        seasonId: Int,
        relatedNpcId: Int = 0
    ): com.example.townapp.domain.model.MemoryFragmentVo? {
        val filePath = "${PATH_NPC_MEMORY}memory_group_$groupId.json"

        // 1. 读取标题（标题无认知分层）
        val titleKey = "memory_${markId}_title"
        val title = textLoader.getText(filePath, titleKey)
            .ifEmpty { textLoader.getText(filePath, "memory_$markId").split("|").firstOrNull() ?: "" }

        // 2. 读取三层认知解读
        val surfaceText = textLoader.getCognitionText(filePath, "memory_${markId}_content", 0)
        val observeText = textLoader.getCognitionText(filePath, "memory_${markId}_content", 2)
        val wiseText = textLoader.getCognitionText(filePath, "memory_${markId}_content", 4)

        // 3. 旧格式兼容：从 "memory_$markId" 解析 title|content|year|playerAge
        val legacyRaw = textLoader.getText(filePath, "memory_$markId")
        if (legacyRaw.isNotEmpty() && surfaceText.isEmpty() && observeText.isEmpty() && wiseText.isEmpty()) {
            val parts = legacyRaw.split("|")
            if (parts.size >= 4) {
                val legacyTitle = parts[0]
                val legacyContent = parts[1]
                val year = parts[2].toIntOrNull() ?: currentYear
                val playerAgeAtTime = parts[3].toIntOrNull() ?: currentPlayerAge
                val yearsAgo = (currentYear - year).coerceAtLeast(0)
                val relatedNpcName = if (relatedNpcId > 0) {
                    dao.queryById(relatedNpcId)?.let { getNameById(it.nameTextId) } ?: ""
                } else ""

                return com.example.townapp.domain.model.MemoryFragmentVo(
                    markId = markId,
                    title = legacyTitle,
                    content = legacyContent,
                    year = year,
                    playerAgeAtTime = playerAgeAtTime,
                    yearsAgo = yearsAgo,
                    seasonName = getSeasonById(seasonId),
                    relatedNpcName = relatedNpcName,
                    musicId = 0,
                    cognitionTier = "surface",
                    surfaceContent = legacyContent,
                    observeContent = legacyContent,
                    wiseContent = legacyContent
                )
            }
        }

        // 4. 新格式组装（三层认知）
        if (title.isEmpty() && surfaceText.isEmpty()) return null
        val finalTitle = title.ifEmpty { "回忆" }
        val finalSurface = surfaceText.ifEmpty { "那年发生了些事。" }
        val finalObserve = observeText.ifEmpty { surfaceText.ifEmpty { "那年发生了些事。" } }
        val finalWise = wiseText.ifEmpty { observeText.ifEmpty { surfaceText.ifEmpty { "那年发生了些事。" } } }

        val yearsAgo = 0  // 新格式无年份字段，默认为当前

        val relatedNpcName = if (relatedNpcId > 0) {
            dao.queryById(relatedNpcId)?.let { getNameById(it.nameTextId) } ?: ""
        } else ""

        // 5. 根据当前玩家认知等级选择最匹配内容
        val cognitiveLevel = CognitionState.cognitionLevel.value
        val cognitionTier = when {
            cognitiveLevel < 2 -> "surface"
            cognitiveLevel < 4 -> "observe"
            else -> "wise"
        }
        val selectedContent = when (cognitionTier) {
            "wise" -> finalWise
            "observe" -> finalObserve
            else -> finalSurface
        }

        return com.example.townapp.domain.model.MemoryFragmentVo(
            markId = markId,
            title = finalTitle,
            content = selectedContent,
            year = currentYear,
            playerAgeAtTime = currentPlayerAge,
            yearsAgo = yearsAgo,
            seasonName = getSeasonById(seasonId),
            relatedNpcName = relatedNpcName,
            musicId = 0,
            cognitionTier = cognitionTier,
            surfaceContent = finalSurface,
            observeContent = finalObserve,
            wiseContent = finalWise
        )
    }

    /**
     * 批量加载某组所有记忆碎片（按 markId 列表）
     */
    fun getMemoryFragmentsByGroup(
        markIds: List<Int>,
        groupId: Int,
        currentYear: Int,
        currentPlayerAge: Int,
        seasonId: Int
    ): List<com.example.townapp.domain.model.MemoryFragmentVo> {
        return markIds.mapNotNull { markId ->
            getMemoryFragment(
                markId = markId,
                groupId = groupId,
                currentYear = currentYear,
                currentPlayerAge = currentPlayerAge,
                seasonId = seasonId
            )
        }
    }

    // ================== 初始化与重置 ==================

    /**
     * 初始化预置 NPC 列表（仅小镇初始化时调用一次）
     */
    fun initPresetNpcs(npcs: List<NpcBase>) {
        dao.upsertAll(npcs)
        val defaultStatuses = npcs.map { NpcStatus.default(it.id, it.seasonId) }
        dao.initStatusAll(defaultStatuses)
    }

    /**
     * 重置 NPC 数据（仅供测试 / 重启小镇使用）
     */
    fun resetAll() {
        dao.clearAll()
    }

    // ================== NPC 专属氛围色调（调色板查询） ==================

    /**
     * 根据 NPC ID 获取其专属氛围色调展示模型
     * 【唯一入口】ID → 调色板 JSON 解析 → Color + Float 转换
     *
     * @param npcId NPC 唯一 ID
     * @return TonePaletteVo，未找到返回 null
     */
    fun getNpcMoodPalette(npcId: Int): com.example.townapp.feature.human_narrative.npc.model.TonePaletteVo? {
        // 第一步：从 Dao 取出基础信息
        val base = dao.queryById(npcId) ?: return null

        // 第二步：从静态资源加载调色板 JSON
        val paletteJson = textLoader.loadTonePalette(PATH_NPC_TONE_PALETTE, base.tonePaletteId)
            ?: return null

        // 第三步：解析 hex 颜色字符串为 Compose Color
        val color = textLoader.parseHexColor(paletteJson.baseTint)

        // 第四步：合并 NpcBase 动态字段 + JSON 静态字段
        return com.example.townapp.feature.human_narrative.npc.model.TonePaletteVo(
            paletteId = paletteJson.paletteId,
            paletteName = paletteJson.name,
            baseTint = color,
            vignetteBase = base.vignetteStrength,
            saturation = paletteJson.saturation,
            annotation = paletteJson.annotation
        )
    }

    // ================== 众生档案馆数据查询（供 NpcArchiveScreen 使用） ==================

    /**
     * 获取所有 NPC 基础信息（供档案馆列表使用）
     * 不包含对话内容，仅基础信息，减少数据量
     */
    fun getAllNpcBasicInfo(): List<NpcDisplayVo> {
        val rawList = dao.queryAll()
        Log.d("NPC_DB_CHECK", "内存NPC原始数据条数：${rawList.size}")
        val voList = rawList.map { base ->
            val status = getNpcStatus(base.id, defaultSeasonId = base.seasonId)
            NpcDisplayVo(
                npcId = base.id,
                jobId = base.jobId,
                name = getNameById(base.nameTextId),
                jobName = getJobById(base.jobId),
                seasonName = getSeasonById(status.currentSeasonId),
                x = base.x,
                y = base.y,
                portraitId = base.portraitId,
                favor = status.favor,
                intimacy = status.intimacy,
                currentDialog = "",
                favorLabel = getFavorLabel(status.favor)
            )
        }
        Log.d("NPC_VO_CHECK", "组装后NPC展示数据条数：${voList.size}")
        return voList
    }

    /**
     * 按年龄段筛选 NPC
     * ageRange: 0=青年(18-35), 1=中年(36-55), 2=老年(56+)
     */
    fun getNpcsByAgeRange(ageRange: Int): List<NpcDisplayVo> {
        return getAllNpcBasicInfo().filter { npc ->
            val status = getNpcStatus(npc.npcId)
            when (ageRange) {
                0 -> status.age in 18..35
                1 -> status.age in 36..55
                2 -> status.age >= 56
                else -> true
            }
        }
    }

    /**
     * 获取 NPC 完整人生时间线（供档案馆时间线详情页使用）
     *
     * 认知联动（落实「以人为本」）：
     * - 高认知自动追加中立注解，拆解比较偏差、幸存者偏差
     * - 注解极淡金色低透明度小字，玩家可选择看或忽略
     *
     * 扩充叙事（落实「见证普通人一生」）：
     * - 按职业生成独特的人生节点描述
     * - 覆盖各行各业：教师、医生、工人、农民、程序员、快递员等
     * - 呈现多元、真实、不可复制的人生轨迹
     */
    fun getNpcTimeline(npcId: Int): NpcTimelineVo? {
        val base = dao.queryById(npcId) ?: return null
        val status = getNpcStatus(npcId, defaultSeasonId = base.seasonId)
        val jobId = base.jobId

        // 组装时间线节点
        val nodes = mutableListOf<TimelineNode>()

        // 生成年度节点（从20岁到当前年龄，每5年一个节点）
        val startAge = 20
        for (age in startAge..status.age step 5) {
            val year = age - 20
            nodes.add(TimelineNode(
                type = NodeType.YEARLY,
                year = year,
                ageAtYear = age,
                seasonName = getSeasonById(status.currentSeasonId),
                title = "${age}岁",
                description = getAgeDescription(jobId, age),
                unlocked = true
            ))
        }

        // 添加回忆节点（如果有回忆触发标记）
        if (status.memoryTriggerMark > 0) {
            nodes.add(TimelineNode(
                type = NodeType.MEMORY,
                year = status.livedYears / 5,
                ageAtYear = status.age,
                seasonName = getSeasonById(status.currentSeasonId),
                title = "回忆碎片",
                description = "一段值得铭记的过往",
                unlocked = true,
                memoryMarkId = status.memoryTriggerMark
            ))
        }

        // 添加职业变更节点
        if (status.workStateId != 1) {
            val workDesc = when (status.workStateId) {
                2 -> "退休"
                3 -> "病休"
                4 -> "离世"
                else -> "失业"
            }
            nodes.add(TimelineNode(
                type = when (status.workStateId) {
                    2 -> NodeType.RETIREMENT
                    3 -> NodeType.HEALTH_EVENT
                    4 -> NodeType.DEATH
                    else -> NodeType.CAREER_CHANGE
                },
                year = status.livedYears,
                ageAtYear = status.age,
                seasonName = getSeasonById(status.currentSeasonId),
                title = workDesc,
                description = when (status.workStateId) {
                    2 -> "结束职业生涯，开始退休生活"
                    3 -> "健康状况不佳，需要休养"
                    4 -> "走完了一生"
                    else -> "暂时离开工作岗位"
                },
                unlocked = true
            ))
        }

        // 根据认知等级生成中立注解
        val cognitiveLevel = CognitionState.cognitionLevel.value
        val cognitionAnnotation = if (cognitiveLevel >= 4) {
            "每个人的人生节奏不同，快慢没有优劣。\n所见的只是概率的一种呈现，不必比较。"
        } else if (cognitiveLevel >= 2) {
            "每个人都在自己的时区里前行。"
        } else {
            ""
        }

        // 获取消费认知数据（职业圈层认知局限）
        val cognitionPathId = jobIdToCognitionPathId(jobId)
        val consumerCognition = cognitionPathId?.let {
            com.example.townapp.data.ConsumerCognitionData.getCognition(it)
        }

        return NpcTimelineVo(
            npcId = base.id,
            name = getNameById(base.nameTextId),
            jobName = getJobById(base.jobId),
            currentAge = status.age,
            health = status.health,
            workStateDesc = when (status.workStateId) {
                1 -> "在职"
                2 -> "退休"
                3 -> "病休"
                4 -> "离世"
                else -> "失业"
            },
            palette = getNpcMoodPalette(base.id),
            timelineNodes = nodes.sortedBy { it.year },
            cognitionAnnotation = cognitionAnnotation,
            cognitiveLimitation = consumerCognition?.cognitiveLimitation ?: "",
            limitationExplanation = consumerCognition?.limitationExplanation ?: "",
            birthEnvironment = consumerCognition?.birthEnvironment ?: "",
            consumptionCommentary = consumerCognition?.townConsumptionCommentary ?: ""
        )
    }

    /**
     * 根据职业和年龄生成对应的人生节点描述
     *
     * 落实「见证普通人一生」的核心定位：
     * - 每个职业都有独特的人生叙事
     * - 呈现真实的职业甘苦，不美化不矮化
     * - 没有对错判断，只有客观呈现
     */
    private fun getAgeDescription(jobId: Int, age: Int): String {
        return when (jobId) {
            // ═══════════ 教师（job_1）═══════════
            1 -> when (age) {
                20 -> "师范毕业，怀揣教育理想"
                25 -> "站稳讲台，开始理解教书育人"
                30 -> "成为骨干教师，学生亲切叫'老师好'"
                35 -> "评上职称，桃李开始遍天下"
                40 -> "送走一届届毕业生，见证无数青春"
                45 -> "嗓子不如从前，但经验越来越丰富"
                50 -> "开始思考退休后的生活"
                55 -> "学生返校看望，感慨时光飞逝"
                60 -> "光荣退休，三尺讲台成回忆"
                65 -> "偶尔回学校看看，教室里换了新人"
                else -> "安享晚年"
            }
            // ═══════════ 医生（job_2）═══════════
            2 -> when (age) {
                20 -> "医学院毕业，白大褂穿上身"
                25 -> "住院医师规范化培训，连轴转是常态"
                30 -> "开始独立看诊，救人于病痛之间"
                35 -> "积累临床经验，见证生死离别"
                40 -> "成为主治医师，面对生命更加敬畏"
                45 -> "见过太多生死，反而更珍惜健康"
                50 -> "身体开始走下坡路，夜班越来越难熬"
                55 -> "退居二线，把机会留给年轻人"
                60 -> "正式退休，离开了熟悉的诊室"
                65 -> "偶尔被请去会诊，医术仍是宝"
                else -> "安度晚年"
            }
            // ═══════════ 工人（job_3）═══════════
            3 -> when (age) {
                20 -> "进厂当学徒，靠双手吃饭"
                25 -> "成为熟练工，手上开始有老茧"
                30 -> "养家糊口，工厂是第二个家"
                35 -> "带徒弟，传授一线经验"
                40 -> "身体开始透支，但还得撑着"
                45 -> "工厂效益不好，担忧未来"
                50 -> "体力不如从前，转岗做管理"
                55 -> "等待退休，担心身体出问题"
                60 -> "正式退休，流水线成了回忆"
                65 -> "靠退休金生活，儿女各自忙"
                else -> "安度晚年"
            }
            // ═══════════ 农民（job_4）═══════════
            4 -> when (age) {
                20 -> "继承家业，开始种地生涯"
                25 -> "娶妻生子，守着几亩地"
                30 -> "靠天吃饭，明白农民的辛苦"
                35 -> "孩子长大，期盼他们走出农村"
                40 -> "年景有好有坏，学会了等待"
                45 -> "身体累弯了腰，但土地是根"
                50 -> "孩子在外打工，孤独但理解"
                55 -> "干不动重活了，只种点菜自给"
                60 -> "地给了孩子，仍放不下锄头"
                65 -> "坐在门口晒太阳，回忆当年"
                else -> "颐养天年"
            }
            // ═══════════ 店主（job_5）═══════════
            5 -> when (age) {
                20 -> "接手家里的小店，开始学做生意"
                25 -> "摸出门道，有了固定客源"
                30 -> "店铺发展，迎来送往各色人"
                35 -> "电商冲击，开始担忧"
                40 -> "转型尝试，努力适应变化"
                45 -> "守着一方小店，维系老街坊"
                50 -> "身体还行，继续开着店"
                55 -> "想着要不要关店，舍不得街坊"
                60 -> "终于关了店，闲不下来"
                65 -> "偶尔在店里坐坐，回忆旧时光"
                else -> "安享生活"
            }
            // ═══════════ 程序员（job_6）═══════════
            6 -> when (age) {
                20 -> "计算机专业毕业，梦想改变世界"
                25 -> "996是常态，bug永远修不完"
                30 -> "成为技术骨干，头发开始稀疏"
                35 -> "35岁危机逼近，开始焦虑"
                40 -> "转管理还是继续写代码，两难"
                45 -> "身体报警，加班力不从心"
                50 -> "被裁员风险笼罩，寻找出路"
                55 -> "转行做培训，或者自己创业"
                60 -> "离开了互联网，江湖再见"
                65 -> "偶尔翻翻旧代码，青春在那里"
                else -> "安度余生"
            }
            // ═══════════ 快递员（job_7）═══════════
            7 -> when (age) {
                20 -> "进城打工，成为快递小哥"
                25 -> "熟悉片区，每天跑个不停"
                30 -> "风里来雨里去，为了家拼了"
                35 -> "腰开始出问题，但不敢停"
                40 -> "被投诉过，也被感谢过"
                45 -> "体力下降，但经验老道"
                50 -> "转做驿站，或者送大件"
                55 -> "干不动了，考虑回老家"
                60 -> "回到家乡，离开了快递行业"
                65 -> "偶尔收个快递，回忆当年"
                else -> "安度晚年"
            }
            // ═══════════ 保安（job_8）═══════════
            8 -> when (age) {
                20 -> "成为保安，开始值夜班"
                25 -> "守着一方平安，见惯人来人往"
                30 -> "被尊重过，也被轻视过"
                35 -> "年纪渐长，腿脚开始不利索"
                40 -> "有人叫叔有人叫爷，岁月催人老"
                45 -> "身体还能撑，但希望少值夜班"
                50 -> "成为老保安，新人都叫你前辈"
                55 -> "考虑退休，但闲不住"
                60 -> "正式退休，离开熟悉的岗位"
                65 -> "偶尔回小区看看，保安换了几茬"
                else -> "安享晚年"
            }
            // ═══════════ 外卖骑手（job_9）═══════════
            9 -> when (age) {
                20 -> "成为外卖骑手，骑车穿梭大街小巷"
                25 -> "超时被投诉过，委屈往肚里咽"
                30 -> "每天送餐几十单，月收入还行"
                35 -> "腰和膝盖开始疼，但不敢休息"
                40 -> "见过各种奇葩事，学会了忍耐"
                45 -> "体力不如从前，但更懂路线"
                50 -> "考虑转行，但不知道干啥"
                55 -> "干不动了，离开了外卖行业"
                60 -> "回老家，找份轻松工作"
                65 -> "偶尔点外卖，想起当年自己"
                else -> "安度余生"
            }
            // ═══════════ 摊贩（job_10）═══════════
            10 -> when (age) {
                20 -> "开始摆摊，起早贪黑"
                25 -> "有了固定摊位，老顾客多了"
                30 -> "风吹日晒，皮肤粗糙了"
                35 -> "城管制裁过，也被顾客坑过"
                40 -> "为了家，拼了老命"
                45 -> "身体开始出问题，但放不下摊位"
                50 -> "孩子大了，不用那么拼了"
                55 -> "干不动了，摊位让给别人"
                60 -> "闲在家里，不习惯"
                65 -> "偶尔逛早市，回忆当年"
                else -> "安享生活"
            }
            // ═══════════ 自由职业者（job_11）═══════════
            11 -> when (age) {
                20 -> "不上班，开始自由职业"
                25 -> "有活儿的时候拼命，没活儿的时候焦虑"
                30 -> "慢慢有了口碑，收入稳定了些"
                35 -> "没有社保，自己操心养老"
                40 -> "工作生活搅在一起，边界模糊"
                45 -> "身体报警，但停不下来"
                50 -> "开始考虑转型，或者兼职上班"
                55 -> "自由职业越来越难，接活儿少了"
                60 -> "勉强维持，不愿承认老了"
                65 -> "终于可以歇歇了"
                else -> "安度余生"
            }
            // ═══════════ 退休工人（job_12）═══════════
            12 -> when (age) {
                20 -> "当年意气风发进了工厂"
                25 -> "在厂里找到了另一半"
                30 -> "上有老下有小，拼命干活"
                35 -> "工厂改革，下岗风险"
                40 -> "咬牙撑着，不敢生病"
                45 -> "送走了老人，孩子也大了"
                50 -> "终于熬到退休，退休金不多不少"
                55 -> "开始带孙子，找点事做"
                60 -> "身体毛病多了，医院跑得勤"
                65 -> "广场舞学会了，日子有了新节奏"
                else -> "安享晚年"
            }
            // ═══════════ 全职妈妈（job_13）═══════════
            13 -> when (age) {
                20 -> "结婚后当了全职妈妈"
                25 -> "围着孩子转，失去了自我"
                30 -> "孩子上学了，有点空闲了"
                35 -> "和社会脱节，有点焦虑"
                40 -> "孩子青春叛逆期，操碎了心"
                45 -> "开始想找点事做，但不容易"
                50 -> "孩子住校了，家里空落落的"
                55 -> "孩子工作了，开始有了自己的生活"
                60 -> "当了奶奶/外婆，又开始新一轮"
                65 -> "孩子偶尔回家，聚少离多"
                else -> "安享余生"
            }
            // ═══════════ 实习生（job_14）═══════════
            14 -> when (age) {
                20 -> "刚出校门，社会是个大学堂"
                25 -> "换了几份工作，慢慢找到方向"
                30 -> "成为正式员工，压力变大"
                35 -> "成为老员工，开始带新人"
                40 -> "上有老下有小，担子重了"
                45 -> "职业倦怠，但不敢跳槽"
                50 -> "开始反思，这辈子值不值"
                55 -> "等待退休，但又不甘心"
                60 -> "正式退休，离开职场"
                65 -> "偶尔翻翻旧照片，回忆从前"
                else -> "安度晚年"
            }
            // ═══════════ 出租车司机（job_15）═══════════
            15 -> when (age) {
                20 -> "考取驾照，成为出租车司机"
                25 -> "每天十几个小时，腰坐坏了"
                30 -> "见过各种乘客，人间百态"
                35 -> "网约车冲击，生意变差"
                40 -> "咬牙撑着，供孩子上学"
                45 -> "身体报警，但停不下来"
                50 -> "换新能源车，或者转行"
                55 -> "开不动了，考虑退休"
                60 -> "正式退休，离开方向盘"
                65 -> "偶尔打车，怀念当年"
                else -> "安度余生"
            }
            // ═══════════ 清洁工（job_16）═══════════
            16 -> when (age) {
                20 -> "成为城市美容师，凌晨四点起床"
                25 -> "日复一日，街道越来越干净"
                30 -> "被嫌弃过，也被感谢过"
                35 -> "身体开始吃不消，关节疼"
                40 -> "被人叫阿姨/大叔，不习惯"
                45 -> "腰弯了背驼了，但还在扫街"
                50 -> "快干不动了，但不敢退休"
                55 -> "终于可以少扫点，申请轻松岗位"
                60 -> "正式退休，离开了熟悉的街道"
                65 -> "走在街上，看着新来的清洁工"
                else -> "安享晚年"
            }
            // ═══════════ 护士（job_17）═══════════
            17 -> when (age) {
                20 -> "护校毕业，成为白衣天使"
                25 -> "三班倒，生物钟全乱了"
                30 -> "扎针无数，手上有了茧子"
                35 -> "医患矛盾，心累"
                40 -> "成为护士长，责任更大了"
                45 -> "身体报警，夜班越来越难熬"
                50 -> "调离一线，退居幕后"
                55 -> "快要退休了，舍不得同事"
                60 -> "正式退休，离开了医院"
                65 -> "偶尔回医院看看，护士服成了回忆"
                else -> "安度余生"
            }
            // ═══════════ 厨师（job_18）═══════════
            18 -> when (age) {
                20 -> "进厨房当学徒，切菜端盘子"
                25 -> "开始掌勺，灶台是战场"
                30 -> "饭店生意好，逢年过节最忙"
                35 -> "油烟熏久了，嗓子和肺不好"
                40 -> "成为主厨，要操心的事情多了"
                45 -> "身体报警，久站腿静脉曲张"
                50 -> "干不动大灶了，做轻松点的岗位"
                55 -> "考虑退休，舍不得灶台"
                60 -> "正式退休，放下了锅铲"
                65 -> "偶尔下厨，厨艺还在"
                else -> "安享余生"
            }
            // ═══════════ 公务员（job_19）═══════════
            19 -> when (age) {
                20 -> "考上公务员，铁饭碗捧上了"
                25 -> "科员做起，慢慢熬资历"
                30 -> "开始独当一面，写材料开会"
                35 -> "晋升竞争激烈，论资排辈"
                40 -> "成为科长，责任大了"
                45 -> "仕途瓶颈，看不到头"
                50 -> "开始认命，做好本职工作"
                55 -> "等待退休，还有几年"
                60 -> "正式退休，离开了机关"
                65 -> "偶尔回单位看看，物是人非"
                else -> "安享晚年"
            }
            // ═══════════ 理发师（job_20）═══════════
            20 -> when (age) {
                20 -> "当学徒，开始学理发"
                25 -> "出师了，有了固定客人"
                30 -> "手艺越来越好，口碑传开了"
                35 -> "开了自己的小店，当了老板"
                40 -> "同行竞争激烈，要不断进修"
                45 -> "站久了腰不行，但放不下剪刀"
                50 -> "成了老师傅，有人专程来找"
                55 -> "干不动了，让徒弟接手"
                60 -> "正式退休，离开了理发椅"
                65 -> "偶尔给邻居剪剪头发，当消遣"
                else -> "安享余生"
            }
            // ═══════════ 个体小老板（job_21）═══════════
            21 -> when (age) {
                20 -> "开始创业，九死一生"
                25 -> "小有所成，但压力大得很"
                30 -> "员工多了，要操心的事更多"
                35 -> "市场变化快，时刻不敢放松"
                40 -> "上有老下有小，公司和家庭"
                45 -> "身体报警，但还是撑着"
                50 -> "生意稳定了，开始想退路"
                55 -> "考虑转让，或者交给下一代"
                60 -> "终于可以歇歇了"
                65 -> "偶尔去店里看看，江湖再见"
                else -> "安享余生"
            }
            // ═══════════ 水电工（job_22）═══════════
            22 -> when (age) {
                20 -> "当学徒，开始学水电"
                25 -> "出师了，可以接活了"
                30 -> "技术越来越好，名声传出去了"
                35 -> "爬高上低，腰椎间盘突出"
                40 -> "带徒弟，把经验传下去"
                45 -> "体力不如从前，但技术是宝"
                50 -> "干不动大活了，接点轻松活"
                55 -> "考虑退休，舍不得老客户"
                60 -> "正式退休，工具箱收起来了"
                65 -> "偶尔帮邻居修修，当锻炼身体"
                else -> "安享晚年"
            }
            // ═══════════ 销售员（job_23）═══════════
            23 -> when (age) {
                20 -> "成为销售员，跑客户"
                25 -> "底薪低，全靠提成"
                30 -> "客户越来越多，开始有积累"
                35 -> "被人拒绝是家常便饭"
                40 -> "成为销售老手，有了人脉"
                45 -> "体力不如从前，但经验是宝"
                50 -> "考虑转行，做培训或者管理"
                55 -> "干不动了，离开销售行业"
                60 -> "正式退休，偶尔翻翻老照片"
                65 -> "偶尔被人认出，当年也是个销冠"
                else -> "安享余生"
            }
            // ═══════════ 家庭主妇（job_24）═══════════
            24 -> when (age) {
                20 -> "结婚后成为家庭主妇"
                25 -> "照顾全家，伸手要钱"
                30 -> "孩子长大了，有片刻喘息"
                35 -> "和社会脱节，有点迷茫"
                40 -> "老人开始生病，两头照顾"
                45 -> "孩子青春期，操碎了心"
                50 -> "孩子工作了，有了独立生活"
                55 -> "老人走了，松了口气又失落"
                60 -> "当奶奶/外婆了，又开始忙"
                65 -> "终于可以歇歇了"
                else -> "安享余生"
            }
            // ═══════════ 外卖店老板（job_25）═══════════
            25 -> when (age) {
                20 -> "开了外卖店，起早贪黑"
                25 -> "平台抽成高，利润薄"
                30 -> "勉强维持，熬过疫情"
                35 -> "被投诉被罚款，夹缝中生存"
                40 -> "身体报警，但还是撑着"
                45 -> "干不动了，考虑转让"
                50 -> "终于关了店，松了口气"
                55 -> "闲在家里，不习惯"
                60 -> "帮儿女带孩子"
                65 -> "偶尔点外卖，回忆当年"
                else -> "安享余生"
            }
            // ═══════════ 网约车司机（job_26）═══════════
            26 -> when (age) {
                20 -> "成为网约车司机，注册平台"
                25 -> "每天十几个小时，疲劳驾驶"
                30 -> "平台抽成高，到手没几个钱"
                35 -> "见过各种奇葩乘客"
                40 -> "身体报警，久坐不行的"
                45 -> "考虑转行，但不知道干啥"
                50 -> "开不动了，离开网约车"
                55 -> "回老家，找份轻松工作"
                60 -> "正式退休"
                65 -> "偶尔打车，想起当年自己"
                else -> "安享余生"
            }
            // ═══════════ 建筑工（job_27）═══════════
            27 -> when (age) {
                20 -> "成为建筑工人，在工地上干活"
                25 -> "高空作业，危险常伴"
                30 -> "工资不少，但都是拿命换的"
                35 -> "有了老茧，手越来越粗糙"
                40 -> "身体开始出问题，但还是撑着"
                45 -> "工地上出过事，担惊受怕"
                50 -> "干不动大活了，做轻松岗位"
                55 -> "快退休了，想回老家"
                60 -> "正式退休，离开了工地"
                65 -> "回到家乡，种点菜养养鸡"
                else -> "安享余生"
            }
            // ═══════════ 快递驿站老板（job_28）═══════════
            28 -> when (age) {
                20 -> "开了快递驿站，天天入库出库"
                25 -> "被人投诉过件损坏，冤枉"
                30 -> "平台压榨，利润越来越薄"
                35 -> "入库出库打包，样样要钱"
                40 -> "身体吃不消，但放不下"
                45 -> "考虑转让，但舍不得老客户"
                50 -> "终于脱手了，松了口气"
                55 -> "闲在家里，不习惯"
                60 -> "帮儿女带孩子"
                65 -> "偶尔取个快递，回忆当年"
                else -> "安享余生"
            }
            // ═══════════ 早餐摊主（job_29）═══════════
            29 -> when (age) {
                20 -> "开始摆早餐摊，凌晨三点起床"
                25 -> "手艺越来越好，回头客多了"
                30 -> "供孩子上学，拼了命干活"
                35 -> "凌晨起来晚上睡，生活没规律"
                40 -> "身体报警，但还是撑着"
                45 -> "孩子工作了，不用那么拼了"
                50 -> "干不动了，摊位让给别人"
                55 -> "终于可以睡个懒觉了"
                60 -> "闲在家里，种花养鸟"
                65 -> "偶尔逛早市，回忆当年"
                else -> "安享余生"
            }
            // ═══════════ 仓库管理员（job_30）═══════════
            30 -> when (age) {
                20 -> "成为仓库管理员，入库出库"
                25 -> "熟悉仓库的每个角落"
                30 -> "体力活不少，腰酸背痛"
                35 -> "货物丢了要赔，精神压力大"
                40 -> "成了老员工，带新人"
                45 -> "身体报警，但不敢生病"
                50 -> "快要退休了"
                55 -> "等待退休，不舍得老地方"
                60 -> "正式退休，离开了仓库"
                65 -> "偶尔路过仓库，回忆当年"
                else -> "安享余生"
            }
            // ═══════════ 默认通用描述 ════════════
            else -> when (age) {
                20 -> "开始独立生活"
                25 -> "踏入职场"
                30 -> "成家立业"
                35 -> "事业稳定"
                40 -> "上有老下有小"
                45 -> "人生分水岭"
                50 -> "知天命之年"
                55 -> "开始考虑退休"
                60 -> "正式退休"
                65 -> "享受晚年"
                else -> "又一年"
            }
        }
    }

    /**
     * 获取小镇片区列表（供档案馆分类使用）
     */
    fun getDistrictList(): List<Pair<Int, String>> {
        return listOf(
            0 to "全镇",
            1 to "东片区",
            2 to "西片区",
            3 to "南片区",
            4 to "北片区"
        )
    }

    /**
     * 获取年龄段列表（供档案馆分类使用）
     */
    fun getAgeRangeList(): List<Pair<Int, String>> {
        return listOf(
            -1 to "全年龄段",
            0 to "青年 (18-35)",
            1 to "中年 (36-55)",
            2 to "老年 (56+)"
        )
    }

    /**
     * 获取职业列表（供档案馆分类使用）
     */
    fun getJobList(): List<Pair<Int, String>> {
        return listOf(
            0 to "全职业",
            1 to "教师",
            2 to "医生",
            3 to "工人",
            4 to "农民",
            5 to "店主"
        )
    }
}