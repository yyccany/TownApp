package com.example.townapp.performance

import android.content.Context
import android.util.Log
import com.example.townapp.data.extension.ExtensionManager

/**
 * 万物商品模拟小镇 · 统一性能管理入口
 * 扩展模块管理（缓存层已移除，全程内存运行）
 */
object PerformanceManager {

    private const val TAG = "PerformanceManager"
    private var initialized = false

    fun initialize(context: Context) {
        if (initialized) return
        Log.d(TAG, "===== 初始化性能管理体系 =====")

        // 初始化扩展模块
        ExtensionManager.initialize()

        initialized = true
        Log.d(TAG, "性能管理体系初始化完成")
    }

    fun initializeWithPreCompute(context: Context, onReady: () -> Unit) {
        initialize(context)
        onReady()
    }

    fun shutdown() {
        if (!initialized) return
        Log.d(TAG, "关闭性能管理体系...")

        ThreadPoolManager.shutdownAll()
        ExtensionManager.shutdown()

        initialized = false
        Log.d(TAG, "性能管理体系已关闭")
    }

    fun isInitialized(): Boolean = initialized
}