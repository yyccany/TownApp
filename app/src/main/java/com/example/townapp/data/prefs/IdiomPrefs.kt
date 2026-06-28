package com.example.townapp.data.prefs

import android.content.Context
import android.content.SharedPreferences

/**
 * 词条收藏本地存储（轻量SharedPreferences，无数据库依赖）
 * 规则：只存ID列表，无数量统计、无阅读任务、无打卡提醒
 */
object IdiomPrefs {
    private const val PREFS_NAME = "idiom_prefs"
    private const val KEY_FAVORITES = "favorites"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 切换收藏状态：已收藏则移除，未收藏则添加
     */
    fun toggleFavorite(context: Context, idiomId: String): Boolean {
        val prefs = getPrefs(context)
        val current = getFavoriteSet(prefs)
        val isNowFavorite = if (current.contains(idiomId)) {
            current.remove(idiomId)
            false
        } else {
            current.add(idiomId)
            true
        }
        prefs.edit().putString(KEY_FAVORITES, current.joinToString(",")).apply()
        return isNowFavorite
    }

    /**
     * 判断是否已收藏
     */
    fun isFavorite(context: Context, idiomId: String): Boolean {
        return getFavoriteSet(getPrefs(context)).contains(idiomId)
    }

    /**
     * 获取全部收藏ID列表
     */
    fun getFavoriteIds(context: Context): List<String> {
        return getFavoriteSet(getPrefs(context)).toList()
    }

    private fun getFavoriteSet(prefs: SharedPreferences): MutableSet<String> {
        val raw = prefs.getString(KEY_FAVORITES, "") ?: ""
        return if (raw.isEmpty()) mutableSetOf() else raw.split(",").toMutableSet()
    }
}
