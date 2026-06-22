package com.example.townapp.npc

import com.example.townapp.common.TextAssetLoader
import com.example.townapp.npc.repository.NpcDao
import com.example.townapp.npc.repository.NpcRepository
import com.example.townapp.npc.util.NpcLifecycleManager

/**
 * NPC 模块全局唯一入口
 *
 * 严格遵循小镇项目铁律：
 * 1. 全项目唯一获取 NPC 数据的入口
 * 2. UI / ViewModel 禁止独立 new NpcRepository / NpcDao
 * 3. 全局只通过 NpcDataLayer.instance.npcRepository 获取数据
 * 4. init() 只在 Application.onCreate 调用一次
 * 5. TextAssetLoader 统一注入，Repository 内部完成 ID→文本转换
 * 6. NpcLifecycleManager 统一管理 NPC 独立时间线
 *
 * 对标认知模块：DataIntegrationManager 中的认知模块集成方式
 */
object NpcDataLayer {
    @Volatile
    private var initialized = false

    /** 全局唯一的 NPC 仓库实例 */
    lateinit var npcRepository: NpcRepository
        private set

    /** 全局唯一的 NPC 生命周期管理器 */
    lateinit var lifecycleManager: NpcLifecycleManager
        private set

    /** 单例自身，供 ViewModel 注入 */
    val instance: NpcDataLayer
        get() = this

    /**
     * 模块初始化（仅在 Application.onCreate 调用一次）
     * 双重检查锁定，保证并发安全
     */
    fun init() {
        if (initialized) return
        synchronized(this) {
            if (initialized) return
            val dao = NpcDao()
            val textLoader = TextAssetLoader.get()
            npcRepository = NpcRepository(dao, textLoader)
            lifecycleManager = NpcLifecycleManager(npcRepository)
            initialized = true
        }
    }

    /** 重置（仅供测试 / 重启小镇使用，业务代码禁止调用） */
    fun reset() {
        if (initialized) {
            npcRepository.resetAll()
        }
    }

    /** 是否已初始化（供调用方保护用） */
    fun isInitialized(): Boolean = initialized
}
