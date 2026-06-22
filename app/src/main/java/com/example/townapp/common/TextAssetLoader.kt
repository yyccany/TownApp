package com.example.townapp.common

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.util.concurrent.ConcurrentHashMap

/**
 * 全局静态文本加载器
 *
 * 严格遵循小镇项目铁律：
 * 1. 全局唯一单例，所有模块共用一套文本读取逻辑
 * 2. 内置缓存机制，assets 文件只读取一次
 * 3. 禁止 Compose / ViewModel 直接读取 assets，必须通过此类
 * 4. 统一 JSON 格式解析，支持按 key 精确查找
 *
 * 目录结构约定：
 * assets/text/
 * ├── npc/
 * │   ├── name.json           # NPC 姓名表
 * │   ├── job.json            # 职业名称表
 * │   └── dialog/
 * │       ├── spring.json     # 春季对话
 * │       ├── summer.json     # 夏季对话
 * │       ├── autumn.json     # 秋季对话
 * │       └── winter.json     # 冬季对话
 * └── cognition/
 *     ├── quote.json          # 认知语录
 *     └── idiom.json          # 成语批判
 */
class TextAssetLoader private constructor(
    private val context: Context,
    private val gson: Gson = Gson()
) {
    /** 文本缓存（key = 文件路径，value = 解析后的 Map） */
    private val cache = ConcurrentHashMap<String, Map<String, String>>()

    /** 调色板缓存（key = 文件路径，value = 解析后的列表） */
    private val paletteCache = ConcurrentHashMap<String, List<TonePaletteJson>>()

    /**
     * 根据文件路径读取整个 JSON 文件并缓存
     */
    private fun loadFile(path: String): Map<String, String> {
        return cache.getOrPut(path) {
            val inputStream = context.assets.open(path)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson(jsonString, type)
        }
    }

    /**
     * 根据 key 读取指定文件中的文本
     * @param filePath assets 相对路径（如 "text/npc/name.json"）
     * @param key 文本键（如 "name_1"）
     * @return 文本内容，未找到返回空字符串
     */
    fun getText(filePath: String, key: String): String {
        return try {
            loadFile(filePath)[key] ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 根据完整 key 读取文本（key 格式："filePath|key"）
     * 用于 Repository 内部批量匹配
     */
    fun getTextByFullKey(fullKey: String): String {
        val parts = fullKey.split("|", limit = 2)
        return if (parts.size == 2) {
            getText(parts[0], parts[1])
        } else {
            ""
        }
    }

    /**
     * 【认知分层文本读取】根据认知等级自动降级匹配
     *
     * 核心逻辑（落实「以人为本」）：
     * - cognitiveLevel 0-1：尝试 _surface 变体
     * - cognitiveLevel 2-3：尝试 _observe 变体，失败降级到 _surface
     * - cognitiveLevel 4-5：尝试 _wise 变体，依次降级
     *
     * 通用降级顺序（严格保证永远有结果）：
     * 1. baseKey_cognitionTier
     * 2. baseKey_lowerCognitionTier
     * 3. baseKey_lowestCognitionTier
     * 4. baseKey（无后缀，兼容旧格式）
     *
     * @param filePath JSON 文件路径
     * @param baseKey 基础 key（不含认知后缀）
     * @param cognitiveLevel 玩家当前认知等级（0-5）
     * @return 匹配的文本，完全无匹配时返回空字符串
     */
    fun getCognitionText(filePath: String, baseKey: String, cognitiveLevel: Int): String {
        val tier = when {
            cognitiveLevel < 2 -> "surface"
            cognitiveLevel < 4 -> "observe"
            else -> "wise"
        }

        // 降级顺序：当前等级 → 低一档 → 再低一档 → 无后缀
        val tiers = when (tier) {
            "surface" -> listOf("surface")
            "observe" -> listOf("observe", "surface")
            else -> listOf("wise", "observe", "surface")
        }

        for (t in tiers) {
            val text = getText(filePath, "${baseKey}_$t")
            if (text.isNotEmpty()) return text
        }

        // 兜底：使用无后缀 key（兼容存量旧数据）
        return getText(filePath, baseKey)
    }

    /**
     * 读取整个文件的所有文本
     */
    fun getAllText(filePath: String): Map<String, String> {
        return loadFile(filePath)
    }

    // ================== 调色板读取（NPC 专属氛围色） ==================

    /**
     * 读取调色板 JSON 列表（带缓存）
     */
    private fun loadPaletteList(path: String): List<TonePaletteJson> {
        return paletteCache.getOrPut(path) {
            val inputStream = context.assets.open(path)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<TonePaletteJson>>() {}.type
            gson.fromJson(jsonString, type)
        }
    }

    /**
     * 根据 paletteId 加载指定调色板（同步返回 Color + Float）
     *
     * @param filePath 调色板 JSON 路径
     * @param paletteId 调色板 ID
     * @return TonePaletteJson 原始数据，未找到返回 null
     */
    fun loadTonePalette(filePath: String, paletteId: Int): TonePaletteJson? {
        return try {
            loadPaletteList(filePath).firstOrNull { it.paletteId == paletteId }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 将 hex 颜色字符串解析为 Compose Color
     */
    fun parseHexColor(hex: String): Color {
        return try {
            val cleaned = hex.removePrefix("#")
            val longValue = java.lang.Long.parseLong(cleaned, 16)
            Color(longValue)
        } catch (e: Exception) {
            Color.Transparent
        }
    }

    /**
     * 清除指定文件缓存（仅供测试 / 热更新场景）
     */
    fun clearCache(filePath: String) {
        cache.remove(filePath)
        paletteCache.remove(filePath)
    }

    /**
     * 清除所有缓存（仅供测试 / 重置场景）
     */
    fun clearAllCache() {
        cache.clear()
        paletteCache.clear()
    }

    companion object {
        @Volatile
        private var instance: TextAssetLoader? = null

        /**
         * 初始化全局单例（仅在 Application.onCreate 调用一次）
         */
        fun init(context: Context): TextAssetLoader {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = TextAssetLoader(context.applicationContext)
                    }
                }
            }
            return instance!!
        }

        /**
         * 获取全局单例（必须先调用 init()）
         */
        fun get(): TextAssetLoader {
            return instance ?: throw IllegalStateException(
                "TextAssetLoader 未初始化，请先在 Application.onCreate 中调用 TextAssetLoader.init(context)"
            )
        }
    }
}

/**
 * 调色板 JSON 原始数据模型
 * 对应 assets/text/npc/tone_palette.json
 */
data class TonePaletteJson(
    @SerializedName("paletteId")
    val paletteId: Int,

    @SerializedName("name")
    val name: String,

    /** 基础色调 hex 字符串（如 #1AFFF2CC） */
    @SerializedName("baseTint")
    val baseTint: String,

    /** 暗角强度 0f ~ 1f */
    @SerializedName("vignetteBase")
    val vignetteBase: Float,

    /** 饱和度 0.5f ~ 1.3f */
    @SerializedName("saturation")
    val saturation: Float,

    /** 中立注解（客观描述，无价值评判） */
    @SerializedName("annotation")
    val annotation: String = ""
)
