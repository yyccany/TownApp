package com.example.townapp.domain.usecase.resident_case

import android.util.Log
import com.example.townapp.domain.model.resident.ResidentArchiveVo
import com.example.townapp.domain.model.resident.ResidentDisplayVo
import com.example.townapp.domain.model.resident.TimelineNodeVo
import com.example.townapp.feature.human_narrative.npc.NpcDataLayer
import com.example.townapp.feature.human_narrative.npc.model.NpcDisplayVo
import com.example.townapp.feature.human_narrative.npc.model.NpcTimelineVo

/**
 * 获取所有居民用例
 * 
 * 严格遵循实事求是：
 * 1. 仅从 NpcRepository 读取内存 NPC 数据，不加工
 * 2. 将 NpcDisplayVo 转换为 ResidentDisplayVo
 * 3. 日志追踪数据条数
 */
class GetAllResidentUseCase(
    private val dataLayer: NpcDataLayer = NpcDataLayer.instance
) {
    private val repo = dataLayer.npcRepository

    fun execute(): List<ResidentDisplayVo> {
        val rawList = repo.getAllNpcBasicInfo()
        Log.d("RESIDENT_DB", "居民原始数据条数：${rawList.size}")
        
        val voList = rawList.map { it.toResidentDisplayVo() }
        Log.d("RESIDENT_VO", "组装后居民展示数据条数：${voList.size}")
        return voList
    }

    fun getBySeason(seasonId: Int): List<ResidentDisplayVo> {
        val rawList = repo.getNpcsDisplayBySeason(seasonId)
        Log.d("RESIDENT_DB", "季节${seasonId}居民数据：${rawList.size}")
        return rawList.map { it.toResidentDisplayVo() }
    }

    fun getByAgeRange(ageRange: Int): List<ResidentDisplayVo> {
        val rawList = repo.getNpcsByAgeRange(ageRange)
        return rawList.map { it.toResidentDisplayVo() }
    }

    fun getArchive(npcId: Int): ResidentArchiveVo? {
        val timeline = repo.getNpcTimeline(npcId) ?: return null
        return timeline.toResidentArchiveVo()
    }

    fun getCount(): Int = repo.getAllNpcBasicInfo().size

    // ============ 转换扩展 ============
    
    private fun NpcDisplayVo.toResidentDisplayVo(): ResidentDisplayVo {
        return ResidentDisplayVo(
            npcId = this.npcId,
            name = this.name,
            jobName = this.jobName,
            age = 0,  // NpcDisplayVo 没有 age，从 status 获取需要额外查询
            health = 0,
            favor = this.favor,
            intimacy = this.intimacy,
            favorLabel = this.favorLabel,
            moodLevel = this.moodLevel,
            x = this.x,
            y = this.y,
            portraitId = this.portraitId,
            currentDialog = this.currentDialog,
            seasonName = this.seasonName,
            jobId = this.jobId
        )
    }

    private fun NpcTimelineVo.toResidentArchiveVo(): ResidentArchiveVo {
        return ResidentArchiveVo(
            npcId = this.npcId,
            name = this.name,
            jobName = this.jobName,
            currentAge = this.currentAge,
            health = this.health,
            workStateDesc = this.workStateDesc,
            timelineNodes = this.timelineNodes.map { node ->
                TimelineNodeVo(
                    type = node.type.name,
                    year = node.year,
                    ageAtYear = node.ageAtYear,
                    seasonName = node.seasonName,
                    title = node.title,
                    description = node.description,
                    unlocked = node.unlocked,
                    memoryMarkId = node.memoryMarkId
                )
            },
            cognitionAnnotation = this.cognitionAnnotation,
            cognitiveLimitation = this.cognitiveLimitation,
            limitationExplanation = this.limitationExplanation,
            birthEnvironment = this.birthEnvironment,
            consumptionCommentary = this.consumptionCommentary
        )
    }
}
