package com.example.townapp.core.state

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.townapp.core.theme.VeraThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore 顶层扩展（必须在 object 外部）
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "vera_preferences")

/**
 * VERA 用户偏好设置管理
 * 负责：全局默认展开模式、单卡片展开状态缓存
 */
object UserPreferencesManager {

    // ============================================
    // 全局默认展开模式
    // 0 = 智能预览(前3行)  1 = 全部自动展开  2 = 全部默认收起
    // ============================================
    private val KEY_EXPAND_MODE = intPreferencesKey("expand_mode")

    // ============================================
    // 主题模式
    // ============================================
    private val KEY_THEME_MODE = stringPreferencesKey("theme_mode")

    // ============================================
    // 单卡片展开状态缓存
    // Key: "${idiomId}_${moduleKey}"  ->  Value: true=展开 / false=收起
    // ============================================
    private fun cardExpandKey(idiomId: String, moduleKey: String): Preferences.Key<Boolean> {
        return booleanPreferencesKey("card_expand_${idiomId}_${moduleKey}")
    }

    // ============================================
    // 读取全局展开模式（默认 0 = 智能预览）
    // ============================================
    fun getExpandMode(context: Context): Flow<Int> {
        return context.dataStore.data.map { prefs ->
            prefs[KEY_EXPAND_MODE] ?: 0
        }
    }

    // ============================================
    // 设置全局展开模式
    // ============================================
    suspend fun setExpandMode(context: Context, mode: Int) {
        context.dataStore.edit { prefs ->
            prefs[KEY_EXPAND_MODE] = mode
        }
    }

    // ============================================
    // 读取主题模式（默认 DefaultMonochrome）
    // ============================================
    fun getThemeMode(context: Context): Flow<VeraThemeMode> {
        return context.dataStore.data.map { prefs ->
            val raw = prefs[KEY_THEME_MODE] ?: VeraThemeMode.DefaultMonochrome.name
            try {
                VeraThemeMode.valueOf(raw)
            } catch (_: IllegalArgumentException) {
                VeraThemeMode.DefaultMonochrome
            }
        }
    }

    // ============================================
    // 设置主题模式
    // ============================================
    suspend fun setThemeMode(context: Context, mode: VeraThemeMode) {
        context.dataStore.edit { prefs ->
            prefs[KEY_THEME_MODE] = mode.name
        }
    }

    // ============================================
    // 读取单卡片展开状态（null = 无缓存，跟随全局）
    // ============================================
    fun getCardExpandState(
        context: Context,
        idiomId: String,
        moduleKey: String
    ): Flow<Boolean?> {
        val key = cardExpandKey(idiomId, moduleKey)
        return context.dataStore.data.map { prefs ->
            prefs[key]  // null = 无手动记录，回落全局
        }
    }

    // ============================================
    // 设置单卡片展开状态（手动覆盖）
    // ============================================
    suspend fun setCardExpandState(
        context: Context,
        idiomId: String,
        moduleKey: String,
        expanded: Boolean
    ) {
        val key = cardExpandKey(idiomId, moduleKey)
        context.dataStore.edit { prefs ->
            prefs[key] = expanded
        }
    }

    // ============================================
    // 清除某词条所有卡片缓存（恢复跟随全局）
    // ============================================
    suspend fun clearIdiomCardCache(context: Context, idiomId: String, moduleKeys: List<String>) {
        context.dataStore.edit { prefs ->
            for (mk in moduleKeys) {
                val key = cardExpandKey(idiomId, mk)
                prefs.remove(key)
            }
        }
    }

    // ============================================
    // 清除全部卡片缓存（全局重置）
    // ============================================
    suspend fun clearAllCardCache(context: Context) {
        context.dataStore.edit { prefs ->
            val keysToRemove = prefs.asMap().keys.filterIsInstance<Preferences.Key<*>>()
                .filter { it.name.startsWith("card_expand_") }
            for (key in keysToRemove) {
                prefs.remove(key)
            }
        }
    }
}
