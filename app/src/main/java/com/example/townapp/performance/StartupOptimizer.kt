package com.example.townapp.performance

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.example.townapp.SeedData
import com.example.townapp.data.database.TownDatabase
import com.example.townapp.feature.town_simulation.BodyStateBusiness
import com.example.townapp.feature.town_simulation.StructuredLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StartupOptimizer : Initializer<Unit> {

    private val TAG = "StartupOptimizer"

    override fun create(context: Context) {
        val startTime = System.currentTimeMillis()
        StructuredLogger.i(TAG, "开始优化启动流程...")

        GlobalScope.launch(Dispatchers.IO) {
            try {
                preCacheData(context)
                preInitializeDatabase(context)
            } catch (e: Exception) {
                Log.e(TAG, "启动优化失败", e)
            }

            val endTime = System.currentTimeMillis()
            val duration = endTime - startTime
            StructuredLogger.logPerformance(TAG, "startup_optimization", duration)
            Log.i(TAG, "启动优化完成，耗时: $duration ms")
        }
    }

    private suspend fun preCacheData(context: Context) {
        // 缓存层已移除，全程内存运行
        Log.d(TAG, "跳过缓存初始化（内存模式）")
    }

    private suspend fun preInitializeDatabase(context: Context) {
        val dbStartTime = System.currentTimeMillis()
        try {
            val database = TownDatabase.getDatabase(context)
            val nutritionCount = database.foodNutritionDao().count()
            if (nutritionCount == 0) {
                val seeds = SeedData.all()
                database.foodNutritionDao().insertAll(seeds.first)
                database.foodRiskDao().insertAll(seeds.second)
            }
            BodyStateBusiness.startLongTermAccumulation(database.userBodyStateDao())
        } catch (e: Exception) {
            Log.w(TAG, "预初始化数据库失败（首次启动正常）", e)
        }
        val dbDuration = System.currentTimeMillis() - dbStartTime
        Log.d(TAG, "数据库预初始化完成，耗时: $dbDuration ms")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}

object AppStartup {

    fun initialize(context: Context) {
        Log.i("AppStartup", "执行应用启动优化...")
        androidx.startup.AppInitializer.getInstance(context)
            .initializeComponent(StartupOptimizer::class.java)
    }
}