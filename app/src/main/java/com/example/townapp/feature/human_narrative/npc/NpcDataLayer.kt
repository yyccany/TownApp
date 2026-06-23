package com.example.townapp.feature.human_narrative.npc

import android.content.Context
import android.util.Log
import com.example.townapp.data.asset.TextAssetLoader
import com.example.townapp.feature.human_narrative.npc.repository.NpcDao
import com.example.townapp.feature.human_narrative.npc.repository.NpcPresetData
import com.example.townapp.feature.human_narrative.npc.repository.NpcRepository
import com.example.townapp.feature.human_narrative.npc.util.NpcLifecycleManager

/**
 * NPC 数据层统一入口
 *
 * 严格遵循小镇项目铁律：
 * 1. 单例模式，全局唯一入口
 * 2. 不导入任何 Dao
 * 3. 不写 SQL 筛选条件
 * 4. 不做文件 IO
 * 5. 只提供 Repository 和 LifecycleManager 的统一获取方式
 *
 * 设计目的：
 * - ViewModel 层不直接依赖 Repository，通过 DataLayer 统一注入
 * - 方便后续扩展（如切换数据源、添加缓存层等）
 * - 保持分层清晰，避免 ViewModel 直接操作数据库
 */
object NpcDataLayer {
    
    /** 单例实例 */
    val instance = NpcDataLayer
    
    /** NPC 数据访问对象 */
    private val npcDao: NpcDao = NpcDao()
    
    /** 文本加载器（需要 Context 初始化） */
    private var textLoader: TextAssetLoader? = null
    
    /** NPC 数据仓库（延迟初始化） */
    private var _npcRepository: NpcRepository? = null
    val npcRepository: NpcRepository
        get() = _npcRepository ?: throw IllegalStateException("NpcDataLayer 未初始化，请先调用 initialize(context)")
    
    /** NPC 生命周期管理器（延迟初始化） */
    private var _lifecycleManager: NpcLifecycleManager? = null
    val lifecycleManager: NpcLifecycleManager
        get() = _lifecycleManager ?: throw IllegalStateException("NpcDataLayer 未初始化，请先调用 initialize(context)")
    
    /**
     * 初始化数据层（需要在 Application 或 MainActivity 中调用）
     * 
     * @param context Android Context（用于加载 assets 文件）
     */
    fun initialize(context: Context) {
        TextAssetLoader.init(context)
        textLoader = TextAssetLoader.get()
        _npcRepository = NpcRepository(npcDao, textLoader!!)

        // 初始化预设NPC数据到内存数据库
        val presetList = NpcPresetData.getAllPresetNpcs()
        npcDao.upsertAll(presetList)
        Log.d("NPC_INIT", "预设NPC数据加载完成，共${presetList.size}条")

        _lifecycleManager = NpcLifecycleManager(_npcRepository!!)
    }
}