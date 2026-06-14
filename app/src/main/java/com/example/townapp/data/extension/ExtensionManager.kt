package com.example.townapp.data.extension

import android.util.Log

/**
 * 万物代价操作系统 - 六大扩展模块统一入口
 * 全部增量开发，不改动底层架构
 */
object ExtensionManager {

    private const val TAG = "ExtensionManager"
    private var initialized = false

    fun initialize() {
        if (initialized) return
        Log.d(TAG, "===== 初始化万物代价操作系统扩展 =====")
        initialized = true
    }

    fun shutdown() {
        AnonymousLifestyleSquare.clearAll()
        UserCognitiveCardLibrary.clearAll()
        initialized = false
        Log.d(TAG, "扩展模块已关闭")
    }

    fun isInitialized(): Boolean = initialized

    fun getStatus(): ExtensionStatus {
        return ExtensionStatus(
            presetChoiceCount = ParallelTimelineEngine.presetChoices.size,
            anonymousTownCount = AnonymousLifestyleSquare.getTownCount(),
            presetCardCount = UserCognitiveCardLibrary.getPresetCards().size,
            pendingCardCount = UserCognitiveCardLibrary.getPendingReviewCount(),
            approvedCardCount = UserCognitiveCardLibrary.getApprovedCount(),
            longTermPredictionCount = LongTermCostPredictionEngine.getPresetPredictions().size,
            carbonMaterialCount = CarbonCostDataManager.getAllCarbonData().size
        )
    }
}

data class ExtensionStatus(
    val presetChoiceCount: Int,
    val anonymousTownCount: Int,
    val presetCardCount: Int,
    val pendingCardCount: Int,
    val approvedCardCount: Int,
    val longTermPredictionCount: Int,
    val carbonMaterialCount: Int
)

/**
 * 扩展模块SQL生成器
 */
object ExtensionSqlGenerator {
    fun generateExtensionTablesSql(): String {
        return """
            CREATE TABLE IF NOT EXISTS tb_behavior_log (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id TEXT NOT NULL,
                timestamp INTEGER NOT NULL,
                activity TEXT NOT NULL,
                category TEXT NOT NULL,
                duration_hours REAL NOT NULL,
                money_spent REAL NOT NULL,
                attention_level INTEGER NOT NULL,
                body_state_after INTEGER NOT NULL,
                emotion_after TEXT NOT NULL
            );
            CREATE TABLE IF NOT EXISTS tb_user_cognitive_card (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                card_id TEXT NOT NULL UNIQUE,
                user_id TEXT NOT NULL,
                submit_time INTEGER NOT NULL,
                title TEXT NOT NULL,
                content TEXT NOT NULL,
                category TEXT NOT NULL,
                related_items TEXT NOT NULL,
                cost_realization TEXT,
                review_status TEXT NOT NULL,
                approve_time INTEGER,
                unlock_count INTEGER NOT NULL DEFAULT 0,
                rating REAL NOT NULL DEFAULT 0
            );
            CREATE TABLE IF NOT EXISTS tb_anonymous_town (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                anonymous_id TEXT NOT NULL UNIQUE,
                create_time INTEGER NOT NULL,
                building_stats TEXT NOT NULL,
                time_allocation TEXT NOT NULL,
                money_allocation TEXT NOT NULL,
                tag_line TEXT NOT NULL,
                dominant_theme TEXT NOT NULL,
                view_count INTEGER NOT NULL DEFAULT 0
            );
            CREATE TABLE IF NOT EXISTS tb_carbon_cost (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                material_id INTEGER NOT NULL UNIQUE,
                material_name TEXT NOT NULL,
                carbon_per_unit REAL NOT NULL,
                water_usage REAL NOT NULL DEFAULT 0,
                land_usage REAL NOT NULL DEFAULT 0,
                recyclable INTEGER NOT NULL DEFAULT 0,
                biodegradable INTEGER NOT NULL DEFAULT 0,
                toxic_level INTEGER NOT NULL DEFAULT 1
            );
            CREATE TABLE IF NOT EXISTS tb_long_term_prediction (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                item_id INTEGER NOT NULL,
                item_name TEXT NOT NULL,
                prediction_years INTEGER NOT NULL,
                total_money_cost REAL NOT NULL,
                equivalent_life_hours REAL NOT NULL,
                equivalent_work_years REAL NOT NULL,
                yearly_breakdown TEXT NOT NULL,
                cumulative_cost REAL NOT NULL,
                health_cost_projection TEXT,
                environmental_cost TEXT
            );
        """.trimIndent()
    }
}