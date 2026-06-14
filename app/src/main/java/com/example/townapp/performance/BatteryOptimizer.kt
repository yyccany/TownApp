package com.example.townapp.performance

import android.app.Application
import android.content.Context
import android.os.BatteryManager
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.townapp.business.StructuredLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object BatteryOptimizer {

    private const val TAG = "BatteryOptimizer"
    private var isAppInForeground = false
    private var batteryLevel = 100

    fun initialize(application: Application) {
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                isAppInForeground = true
                Log.d(TAG, "应用进入前台")
                StructuredLogger.i(TAG, "应用进入前台")
            }

            override fun onStop(owner: LifecycleOwner) {
                isAppInForeground = false
                Log.d(TAG, "应用进入后台")
                StructuredLogger.i(TAG, "应用进入后台")
                cleanupBackgroundTasks()
            }
        })
    }

    fun updateBatteryLevel(context: Context) {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        Log.d(TAG, "当前电量: $batteryLevel%")

        if (batteryLevel < 20) {
            Log.w(TAG, "电量低于20%，建议优化后台任务")
            reduceBackgroundActivity()
        }
    }

    fun isLowBattery(): Boolean {
        return batteryLevel < 20
    }

    fun isAppInForeground(): Boolean {
        return isAppInForeground
    }

    private fun cleanupBackgroundTasks() {
        // 缓存层已移除，全程内存运行
        ThreadPoolManager.shutdownAll()
    }

    private fun reduceBackgroundActivity() {
        Log.i(TAG, "降低后台活动以节省电量")
        // 缓存层已移除，无额外清理需求
    }

    fun shouldRunHeavyTask(): Boolean {
        return isAppInForeground && batteryLevel >= 10
    }
}