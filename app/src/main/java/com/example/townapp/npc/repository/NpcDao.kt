package com.example.townapp.npc.repository

import com.example.townapp.npc.model.NpcBase
import com.example.townapp.npc.model.NpcStatus
import java.util.concurrent.ConcurrentHashMap

/**
 * NPC 底层数据访问层
 *
 * 严格遵循小镇项目铁律：
 * 1. Dao 只做最基础的单字段 / 单表查询
 * 2. 复杂多条件联合查询全部交给 NpcRepository 组合，Dao 不写
 * 3. 禁止 UI / ViewModel 直接调用 Dao（仅 Repository 可调）
 * 4. 内存存储使用 ConcurrentHashMap，并发安全
 *
 * 当前实现：内存 Map 存储（对齐项目现有 UserCognitiveCardLibrary 风格）
 * 后续如需升级 Room / SQLite，只需替换此 Dao 内部实现，Repository 签名不变。
 */
class NpcDao {
    /** NPC 基础信息表（key = npcId） */
    private val npcBaseTable = ConcurrentHashMap<Int, NpcBase>()

    /** NPC 动态状态表（key = npcId） */
    private val npcStatusTable = ConcurrentHashMap<Int, NpcStatus>()

    // ================== NpcBase 基础单字段查询 ==================

    /** 按 npcId 查询单个 NPC 基础信息 */
    fun queryById(npcId: Int): NpcBase? = npcBaseTable[npcId]

    /** 按 seasonId 查询当前季节可见 NPC 列表 */
    fun queryBySeason(seasonId: Int): List<NpcBase> =
        npcBaseTable.values.filter { it.seasonId == seasonId }

    /** 按 jobId 查询同职业 NPC 列表 */
    fun queryByJob(jobId: Int): List<NpcBase> =
        npcBaseTable.values.filter { it.jobId == jobId }

    /** 按 districtId 查询同区域 NPC 列表 */
    fun queryByDistrict(districtId: Int): List<NpcBase> =
        npcBaseTable.values.filter { it.districtId == districtId }

    /** 按 dialogGroupId 查询同对话组 NPC 列表 */
    fun queryByDialogGroup(dialogGroupId: Int): List<NpcBase> =
        npcBaseTable.values.filter { it.dialogGroupId == dialogGroupId }

    /** 查询全部 NPC（仅用于初始化 / 调试，禁止业务逻辑使用） */
    fun queryAll(): List<NpcBase> = npcBaseTable.values.toList()

    // ================== NpcBase 写入（仅 Repository 调用） ==================

    /** 插入或更新 NPC 基础信息 */
    fun upsert(npc: NpcBase) {
        npcBaseTable[npc.id] = npc
    }

    /** 批量插入（用于初始化预置数据） */
    fun upsertAll(npcs: List<NpcBase>) {
        npcs.forEach { npcBaseTable[it.id] = it }
    }

    // ================== NpcStatus 基础单字段查询 ==================

    /** 按 npcId 查询 NPC 动态状态 */
    fun queryStatus(npcId: Int): NpcStatus? = npcStatusTable[npcId]

    /** 查询所有 NPC 状态（仅用于序列化 / 调试） */
    fun queryAllStatus(): List<NpcStatus> = npcStatusTable.values.toList()

    // ================== NpcStatus 写入（仅 Repository 调用） ==================

    /** 更新 NPC 状态 */
    fun updateStatus(status: NpcStatus) {
        npcStatusTable[status.npcId] = status
    }

    /** 批量初始化状态 */
    fun initStatusAll(statuses: List<NpcStatus>) {
        statuses.forEach { npcStatusTable[it.npcId] = it }
    }

    // ================== 批量操作（供 NpcLifecycleManager 使用） ==================

    /** 批量更新 NPC 状态（一次性写入多个状态变更） */
    fun batchUpdateStatus(statuses: List<NpcStatus>) {
        statuses.forEach { npcStatusTable[it.npcId] = it }
    }

    /** 查询所有 NPC 状态（用于全局年度迭代） */
    fun queryAllStatusForLifecycle(): List<NpcStatus> = npcStatusTable.values.toList()

    // ================== 管理操作（仅限测试 / 重置场景） ==================

    /** 清空所有数据（仅供测试或重置小镇时使用，业务代码禁止调用） */
    fun clearAll() {
        npcBaseTable.clear()
        npcStatusTable.clear()
    }
}
