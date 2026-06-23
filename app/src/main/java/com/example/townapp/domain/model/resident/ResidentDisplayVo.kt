package com.example.townapp.domain.model.resident

import com.example.townapp.feature.human_narrative.npc.model.NpcDisplayVo
import com.example.townapp.feature.human_narrative.npc.model.NpcTimelineVo

/**
 * 居民展示模型（domain 层 VO）
 * 
 * 封装 NPC 内存库的完整人文数据，供 UI 层统一渲染
 * 遵循实事求是：如实呈现人物数据，不做主观评判
 * 
 * 数据来源：
 * - NpcRepository.getAllNpcBasicInfo() → NpcDisplayVo
 * - NpcRepository.getNpcTimeline() → NpcTimelineVo
 * - TextAssetLoader → name.json / job.json 文案
 */
data class ResidentDisplayVo(
    // 基础识别
    val npcId: Int,
    val name: String,
    val jobName: String,
    
    // 状态数据
    val age: Int,
    val health: Int,
    val favor: Int,
    val intimacy: Int,
    val favorLabel: String,
    val moodLevel: Int,
    
    // 位置（像素地图坐标）
    val x: Int,
    val y: Int,
    val portraitId: Int,
    
    // 当前对话
    val currentDialog: String,
    val seasonName: String,
    
    // 职业 ID（用于氛围色调匹配）
    val jobId: Int
)

/**
 * 居民完整档案模型（档案馆时间线详情页用）
 * 
 * 整合 NPC 人生时间线 + 消费认知 + 成长环境
 * 遵循以人为本：人是核心，不是工具背景板
 */
data class ResidentArchiveVo(
    // 基础信息
    val npcId: Int,
    val name: String,
    val jobName: String,
    val currentAge: Int,
    val health: Int,
    val workStateDesc: String,
    
    // 时间线
    val timelineNodes: List<TimelineNodeVo>,
    
    // 认知注解
    val cognitionAnnotation: String,
    val cognitiveLimitation: String,
    val limitationExplanation: String,
    val birthEnvironment: String,
    val consumptionCommentary: String
)

data class TimelineNodeVo(
    val type: String,
    val year: Int,
    val ageAtYear: Int,
    val seasonName: String,
    val title: String,
    val description: String,
    val unlocked: Boolean,
    val memoryMarkId: Int
)
