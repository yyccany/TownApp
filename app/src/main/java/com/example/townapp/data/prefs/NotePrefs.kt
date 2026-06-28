package com.example.townapp.data.prefs

import android.content.Context
import android.content.SharedPreferences

/**
 * 思辨笔记本地存储（轻量SharedPreferences）
 * 规则：空白自由文本，无答题模板、无对错评判、无自省任务，只做本地私密记录
 */
object NotePrefs {
    private const val PREFS_NAME = "note_prefs"
    private const val KEY_PREFIX = "note_"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 保存笔记（与词条ID绑定）
     */
    fun saveNote(context: Context, idiomId: String, content: String) {
        getPrefs(context).edit().putString("$KEY_PREFIX$idiomId", content).apply()
    }

    /**
     * 获取指定词条的笔记
     */
    fun getNote(context: Context, idiomId: String): String {
        return getPrefs(context).getString("$KEY_PREFIX$idiomId", "") ?: ""
    }

    /**
     * 获取全部笔记（词条ID → 笔记内容）
     */
    fun getAllNotes(context: Context): Map<String, String> {
        val prefs = getPrefs(context)
        val all = prefs.all
        val result = mutableMapOf<String, String>()
        all.forEach { (key, value) ->
            if (key.startsWith(KEY_PREFIX) && value is String && value.trim().isNotEmpty()) {
                val idiomId = key.removePrefix(KEY_PREFIX)
                result[idiomId] = value.trim()
            }
        }
        return result
    }

    /**
     * 删除指定词条的笔记
     */
    fun deleteNote(context: Context, idiomId: String) {
        getPrefs(context).edit().remove("$KEY_PREFIX$idiomId").apply()
    }
}
