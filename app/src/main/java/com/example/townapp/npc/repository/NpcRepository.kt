package com.example.townapp.npc.repository

import com.example.townapp.common.TextAssetLoader
import com.example.townapp.npc.model.CognitionState
import com.example.townapp.npc.model.NpcBase
import com.example.townapp.npc.model.NpcDisplayVo
import com.example.townapp.npc.model.NpcDialogTrigger
import com.example.townapp.npc.model.NpcStatus
import com.example.townapp.npc.model.NpcTimelineVo
import com.example.townapp.npc.model.TimelineNode
import com.example.townapp.npc.model.NodeType

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
    private val textLoader: TextAssetLoader
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

    // ================== 文本读取（ID → 完整文本，只在这里转换） ==================

    /** 获取 NPC 姓名 */
    private fun getNameById(nameTextId: Int): String {
        return textLoader.getText(PATH_NPC_NAME, "name_$nameTextId")
    }

    /** 获取职业名称 */
    private fun getJobById(jobId: Int): String {
        return textLoader.getText(PATH_NPC_JOB, "job_$jobId")
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
    ): com.example.townapp.npc.model.MemoryFragmentVo? {
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

                return com.example.townapp.npc.model.MemoryFragmentVo(
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

        return com.example.townapp.npc.model.MemoryFragmentVo(
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
    ): List<com.example.townapp.npc.model.MemoryFragmentVo> {
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
    fun getNpcMoodPalette(npcId: Int): com.example.townapp.npc.model.TonePaletteVo? {
        // 第一步：从 Dao 取出基础信息
        val base = dao.queryById(npcId) ?: return null

        // 第二步：从静态资源加载调色板 JSON
        val paletteJson = textLoader.loadTonePalette(PATH_NPC_TONE_PALETTE, base.tonePaletteId)
            ?: return null

        // 第三步：解析 hex 颜色字符串为 Compose Color
        val color = textLoader.parseHexColor(paletteJson.baseTint)

        // 第四步：合并 NpcBase 动态字段 + JSON 静态字段
        return com.example.townapp.npc.model.TonePaletteVo(
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
        return dao.queryAll().map { base ->
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
     */
    fun getNpcTimeline(npcId: Int): NpcTimelineVo? {
        val base = dao.queryById(npcId) ?: return null
        val status = getNpcStatus(npcId, defaultSeasonId = base.seasonId)

        // 组装时间线节点
        val nodes = mutableListOf<TimelineNode>()

        // 生成年度节点（简化版：从出生到当前年龄）
        val startAge = 20 // 假设 NPC 从 20 岁开始
        for (age in startAge..status.age step 5) {
            val year = age - 20
            nodes.add(TimelineNode(
                type = NodeType.YEARLY,
                year = year,
                ageAtYear = age,
                seasonName = getSeasonById(status.currentSeasonId),
                title = "${age}岁",
                description = when (age) {
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
                },
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
            cognitionAnnotation = cognitionAnnotation
        )
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
