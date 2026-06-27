package com.example.townapp.data.asset

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

data class MarkdownEntry(
    val key: String,
    val attributes: Map<String, String>,
    val fields: Map<String, String>
)

class TextAssetLoader private constructor(
    private val context: Context,
    private val gson: Gson = Gson()
) {
    private val cache = ConcurrentHashMap<String, Map<String, String>>()
    private val paletteCache = ConcurrentHashMap<String, List<TonePaletteJson>>()
    private val markdownCache = ConcurrentHashMap<String, List<MarkdownEntry>>()
    private val plainTextLinesCache = ConcurrentHashMap<String, List<String>>()

    private fun loadFile(path: String): Map<String, String> {
        return cache.getOrPut(path) {
            val inputStream = context.assets.open(path)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson(jsonString, type)
        }
    }

    fun getText(filePath: String, key: String): String {
        return try {
            loadFile(filePath)[key] ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    fun getTextByFullKey(fullKey: String): String {
        val parts = fullKey.split("|", limit = 2)
        return if (parts.size == 2) {
            getText(parts[0], parts[1])
        } else {
            ""
        }
    }

    fun getCognitionText(filePath: String, baseKey: String, cognitiveLevel: Int): String {
        val tier = when {
            cognitiveLevel < 2 -> "surface"
            cognitiveLevel < 4 -> "observe"
            else -> "wise"
        }

        val tiers = when (tier) {
            "surface" -> listOf("surface")
            "observe" -> listOf("observe", "surface")
            else -> listOf("wise", "observe", "surface")
        }

        for (t in tiers) {
            val text = getText(filePath, "${baseKey}_$t")
            if (text.isNotEmpty()) return text
        }

        return getText(filePath, baseKey)
    }

    fun getAllText(filePath: String): Map<String, String> {
        return loadFile(filePath)
    }

    private fun loadPaletteList(path: String): List<TonePaletteJson> {
        return paletteCache.getOrPut(path) {
            val inputStream = context.assets.open(path)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<TonePaletteJson>>() {}.type
            gson.fromJson(jsonString, type)
        }
    }

    fun loadTonePalette(filePath: String, paletteId: Int): TonePaletteJson? {
        return try {
            loadPaletteList(filePath).firstOrNull { it.paletteId == paletteId }
        } catch (e: Exception) {
            null
        }
    }

    fun parseHexColor(hex: String): Color {
        return try {
            val cleaned = hex.removePrefix("#")
            val longValue = java.lang.Long.parseLong(cleaned, 16)
            Color(longValue)
        } catch (e: Exception) {
            Color.Transparent
        }
    }

    fun clearCache(filePath: String) {
        cache.remove(filePath)
        paletteCache.remove(filePath)
    }

    fun clearAllCache() {
        cache.clear()
        paletteCache.clear()
        markdownCache.clear()
        plainTextLinesCache.clear()
    }

    private fun loadMarkdownFile(path: String): List<MarkdownEntry> {
        return markdownCache.getOrPut(path) {
            val entries = mutableListOf<MarkdownEntry>()
            try {
                val inputStream = context.assets.open(path)
                val fullText = inputStream.bufferedReader().use { it.readText() }
                parseMarkdownContent(fullText, entries)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            entries
        }
    }

    private fun parseMarkdownContent(fullText: String, entries: MutableList<MarkdownEntry>) {
        val entryPattern = Regex("""<!--\s*(.*?)\s*-->([\s\S]*?)(?=<!--|$)""")
        val attrPattern = Regex("""(\w+)\s*:\s*([^|]+)""")
        val fieldPattern = Regex("""【([^】]+)】\s*\n([\s\S]*?)(?=【|<!--|$)""")

        for (match in entryPattern.findAll(fullText)) {
            val attrString = match.groupValues[1].trim()
            val contentBlock = match.groupValues[2].trim()

            if (attrString.isEmpty() || !contentBlock.contains("【")) continue

            val attrs = mutableMapOf<String, String>()
            var entryKey = ""
            for (attrMatch in attrPattern.findAll(attrString)) {
                val key = attrMatch.groupValues[1].trim()
                val value = attrMatch.groupValues[2].trim()
                attrs[key] = value
                if (key == "key") entryKey = value
            }

            if (entryKey.isEmpty()) continue

            val fields = mutableMapOf<String, String>()
            for (fieldMatch in fieldPattern.findAll(contentBlock)) {
                val fieldName = fieldMatch.groupValues[1].trim()
                val fieldValue = fieldMatch.groupValues[2].trim()
                if (fieldName.isNotEmpty() && fieldValue.isNotEmpty()) {
                    fields[fieldName] = fieldValue
                }
            }

            if (fields.isNotEmpty()) {
                entries.add(MarkdownEntry(entryKey, attrs, fields))
            }
        }
    }

    fun getMarkdownField(path: String, key: String, fieldName: String): String {
        return loadMarkdownFile(path).firstOrNull { it.key == key }
            ?.fields?.get(fieldName) ?: ""
    }

    fun getMarkdownEntry(path: String, key: String): MarkdownEntry? {
        return loadMarkdownFile(path).firstOrNull { it.key == key }
    }

    fun getMarkdownTextByFilters(
        path: String,
        filters: Map<String, String>,
        score: Int? = null
    ): String {
        val entries = loadMarkdownFile(path)
        if (entries.isEmpty()) return ""

        val scored = entries.mapNotNull { entry ->
            var match = true
            var scoreBonus = 0

            for ((filterKey, filterValue) in filters) {
                val entryValue = entry.attributes[filterKey]
                if (entryValue == null) {
                    match = false
                    break
                }
                if (!entryValue.equals("all", ignoreCase = true)) {
                    if (entryValue.equals(filterValue, ignoreCase = true)) {
                        scoreBonus += 10
                    } else {
                        match = false
                        break
                    }
                }
            }

            if (match && score != null) {
                val minScore = entry.attributes["score_min"]?.toIntOrNull()
                    ?: entry.attributes["fatigue_min"]?.toIntOrNull()
                    ?: entry.attributes["anxiety_min"]?.toIntOrNull()
                    ?: entry.attributes["trauma_min"]?.toIntOrNull() ?: 0
                val maxScore = entry.attributes["score_max"]?.toIntOrNull()
                    ?: entry.attributes["fatigue_max"]?.toIntOrNull()
                    ?: entry.attributes["anxiety_max"]?.toIntOrNull()
                    ?: entry.attributes["trauma_max"]?.toIntOrNull() ?: 100
                if (score in minScore..maxScore) {
                    scoreBonus += 20
                } else {
                    match = false
                }
            }

            if (match) entry to scoreBonus else null
        }

        val best = scored.maxByOrNull { it.second }?.first
        return best?.fields?.values?.firstOrNull()
            ?: entries.firstOrNull()?.fields?.values?.firstOrNull()
            ?: ""
    }

    fun getRandomMarkdownText(
        path: String,
        filters: Map<String, String>? = null,
        score: Int? = null,
        fieldName: String? = null
    ): String {
        val entry = getRandomMarkdownEntry(path, filters, score)
        return if (fieldName != null) {
            entry?.fields?.get(fieldName) ?: entry?.fields?.values?.firstOrNull() ?: ""
        } else {
            entry?.fields?.values?.firstOrNull() ?: ""
        }
    }

    fun getRandomMarkdownEntry(
        path: String,
        filters: Map<String, String>? = null,
        score: Int? = null
    ): MarkdownEntry? {
        val entries = loadMarkdownFile(path)
        if (entries.isEmpty()) return null

        val keyFilter = filters?.get("key")

        val matching = if (filters == null) {
            entries
        } else {
            entries.filter { entry ->
                if (keyFilter != null && entry.key != keyFilter) return@filter false

                var match = true
                for ((filterKey, filterValue) in filters) {
                    if (filterKey == "key") continue
                    val entryValue = entry.attributes[filterKey]
                    if (entryValue != null) {
                        val values = entryValue.split(",").map { it.trim() }
                        if (!values.any { it.equals("all", ignoreCase = true) || it.equals(filterValue, ignoreCase = true) }) {
                            match = false; break
                        }
                    }
                }

                if (match && score != null) {
                    val hasScoreConstraint = entry.attributes.containsKey("score_min") ||
                        entry.attributes.containsKey("score_max") ||
                        entry.attributes.containsKey("fatigue_min") ||
                        entry.attributes.containsKey("fatigue_max") ||
                        entry.attributes.containsKey("anxiety_min") ||
                        entry.attributes.containsKey("anxiety_max") ||
                        entry.attributes.containsKey("trauma_min") ||
                        entry.attributes.containsKey("trauma_max")

                    if (hasScoreConstraint) {
                        val minScore = entry.attributes["score_min"]?.toIntOrNull()
                            ?: entry.attributes["fatigue_min"]?.toIntOrNull()
                            ?: entry.attributes["anxiety_min"]?.toIntOrNull()
                            ?: entry.attributes["trauma_min"]?.toIntOrNull() ?: 0
                        val maxScore = entry.attributes["score_max"]?.toIntOrNull()
                            ?: entry.attributes["fatigue_max"]?.toIntOrNull()
                            ?: entry.attributes["anxiety_max"]?.toIntOrNull()
                            ?: entry.attributes["trauma_max"]?.toIntOrNull() ?: 100
                        if (score !in minScore..maxScore) match = false
                    }
                }
                match
            }
        }

        if (matching.isNotEmpty()) {
            return matching[Random.nextInt(matching.size)]
        }

        if (keyFilter != null && score != null) {
            val genericMatches = entries.filter { it.key == keyFilter &&
                !it.attributes.containsKey("score_min") && !it.attributes.containsKey("score_max") &&
                !it.attributes.containsKey("fatigue_min") && !it.attributes.containsKey("fatigue_max") &&
                !it.attributes.containsKey("anxiety_min") && !it.attributes.containsKey("anxiety_max") &&
                !it.attributes.containsKey("trauma_min") && !it.attributes.containsKey("trauma_max")
            }
            if (genericMatches.isNotEmpty()) {
                return genericMatches[Random.nextInt(genericMatches.size)]
            }
            val keyOnlyMatches = entries.filter { it.key == keyFilter }
            if (keyOnlyMatches.isNotEmpty()) {
                return keyOnlyMatches[Random.nextInt(keyOnlyMatches.size)]
            }
        }

        return if (keyFilter != null) {
            entries.firstOrNull { it.key == keyFilter }
        } else {
            entries[Random.nextInt(entries.size)]
        }
    }

    fun getMarkdownAttribute(path: String, key: String, attrName: String): String? {
        return getMarkdownEntry(path, key)?.attributes?.get(attrName)
    }

    fun getAllMarkdownEntries(path: String): List<MarkdownEntry> {
        return loadMarkdownFile(path)
    }

    private fun loadPlainTextLines(path: String, startFromHeading: String? = NARRATIVE_HEADING, scanAllFile: Boolean = false): List<String> {
        val cacheKey = "${path}#${startFromHeading}#all=$scanAllFile"
        return plainTextLinesCache.getOrPut(cacheKey) {
            val lines = mutableListOf<String>()
            try {
                val inputStream = context.assets.open(path)
                var fullText = inputStream.bufferedReader().use { it.readText() }

                if (!scanAllFile && startFromHeading != null) {
                    val headingIdx = fullText.indexOf(startFromHeading)
                    if (headingIdx >= 0) {
                        val afterHeading = fullText.substring(headingIdx + startFromHeading.length)
                        val nextHeadingIdx = afterHeading.indexOf("\n## ")
                        fullText = if (nextHeadingIdx >= 0) {
                            startFromHeading + afterHeading.substring(0, nextHeadingIdx)
                        } else {
                            fullText.substring(headingIdx)
                        }
                    }
                }

                val linePattern = Regex("""^-\s+(.+)$""", RegexOption.MULTILINE)
                for (match in linePattern.findAll(fullText)) {
                    var line = match.groupValues[1].trim()
                    if (line.startsWith("[ ]") || line.startsWith("[x]") || line.startsWith("[X]")) continue
                    if (line.contains("|")) continue
                    if (line.startsWith("<!--") || line.endsWith("-->")) continue
                    if (line.length < 4 || line.length > 500) continue

                    // ====== 彻底清洗markdown标记 ======
                    line = line.replace(Regex("""\*\*([^*]+)\*\*"""), "$1")          // **bold**
                    line = line.replace(Regex("""\*([^*]+)\*"""), "$1")              // *italic*
                    line = line.replace(Regex("""__([^_]+)__"""), "$1")              // __bold__
                    line = line.replace(Regex("""`([^`]+)`"""), "$1")                // `code`
                    line = line.replace(Regex("""~~([^~]+)~~"""), "$1")              // ~~strike~~
                    line = line.replace(Regex("""\[([^\]]+)\]\([^)]+\)"""), "$1")    // [text](url)
                    line = line.replace(Regex("""!\[([^\]]*)\]\([^)]+\)"""), "$1")   // ![alt](img)
                    line = line.replace(Regex("""^>\s*"""), "")                      // > quote prefix
                    line = line.replace(Regex("""^#+\s*"""), "")                     // # headings
                    line = line.replace(Regex("""^\d+\.\s*"""), "")                  // 1. ordered list
                    line = line.replace(Regex("""\[( |x|X)\]"""), "")                // [ ] [x] checkbox remnants
                    line = line.trim()

                    // 过滤分隔符/纯符号行
                    if (line.matches(Regex("""^[-=*_]{3,}$"""))) continue
                    if (line.isBlank()) continue

                    line = line.replace(Regex("""^note_\d+\s*[:：]\s*"""), "")
                    line = line.trim()

                    if (line.contains("：已上线") || line.contains("：已废弃") || line.contains("：已实现")) continue

                    if (line.startsWith("来源") || line.startsWith("状态") || line.startsWith("说明")
                        || line.startsWith("对应成语") || line.startsWith("触发场景") || line.startsWith("体感")
                        || line.startsWith("闪光点") || line.startsWith("小镇评述") || line.startsWith("副标题")
                        || line.startsWith("场景：") || line.startsWith("物品") || line.startsWith("领域")
                        || line.startsWith("认知门槛") || line.startsWith("成功率") || line.startsWith("阻力")
                        || line.startsWith("身体感受") || line.startsWith("个人代价") || line.startsWith("时代代价")) continue

                    if (line.startsWith("key:") || line.startsWith("type:") || line.startsWith("score_")
                        || line.startsWith("sleep_status:") || line.startsWith("dream_type:")
                        || line.startsWith("tendency:") || line.startsWith("emotion:")
                        || line.startsWith("season:") || line.startsWith("identity:")
                        || line.startsWith("category:") || line.startsWith("age_")
                        || line.startsWith("time:") || line.startsWith("npc_id:")
                        || line.startsWith("npc_type:") || line.startsWith("behavior:")
                        || line.startsWith("response:")) continue
                    if (line.matches(Regex("""^[A-Za-z_][A-Za-z0-9_]*:.*"""))) continue

                    if (Regex("""[上下][升降][+-]?\d""").containsMatchIn(line) && line.length < 20) continue
                    if (Regex("""疲惫[+-]\d""").containsMatchIn(line)) continue
                    if (Regex("""焦虑[+-]\d""").containsMatchIn(line)) continue
                    if (Regex("""孤独[+-]\d""").containsMatchIn(line)) continue
                    if (Regex("""技能[+-]\d""").containsMatchIn(line)) continue
                    if (Regex("""自我认同[+-]\d""").containsMatchIn(line)) continue
                    if (Regex("""社交满足[+-]\d""").containsMatchIn(line)) continue
                    if (Regex("""花费\d+元""").containsMatchIn(line) && line.length < 20) continue
                    if (Regex("""\d+\.?\d*小时""").containsMatchIn(line) && line.length < 20) continue
                    if (Regex("""概率[+-]?\d+%?""").containsMatchIn(line) && line.length < 20) continue
                    if (Regex("""[≥≤]\d+""").containsMatchIn(line)) continue
                    if (Regex("""\d+-\d+°[CF]""").containsMatchIn(line)) continue
                    if (line.contains("自动选") || line.contains("自动搭配") || line.contains("自动避开")) continue
                    if (line.contains("当前穿搭") || line.contains("适配季节")) continue
                    if (line.contains("脚气") && line.contains("风险")) continue
                    if (line.contains("受寒概率") || line.contains("闷热难耐")) continue

                    val stripped = line.replace(Regex("""^[^：:]{1,10}[：:]\s*"""), "")
                    if (stripped.length < 6) continue

                    if (stripped.startsWith("key:") || stripped.startsWith("type:") || stripped.startsWith("score_")) continue
                    if (stripped.matches(Regex("""^[A-Za-z_][A-Za-z0-9_]*\s*[=:].*"""))) continue

                    // ====== 过滤宏大叙事：小镇只写具体生活，不写时代洪流 ======
                    // 一个句子同时含2个及以上"大词"视为宏大叙事，过滤掉
                    val grandWords = listOf(
                        "世界", "时代", "命运", "洪流", "阶级", "历史的", "人间", "众生",
                        "觉醒者", "看透", "本质", "终极", "宇宙", "救赎", "顿悟", "开悟",
                        "万物皆", "真相", "生命的意义", "人性本质", "社会规则", "资本的",
                        "这个世界", "人这一生", "人生而", "我们都在", "成年人的世界",
                        "这就是人生", "大多数人", "底层逻辑", "认知觉醒", "阶层", "格局",
                        "注定", "宿命", "轮回", "一切都", "所谓人生"
                    )
                    val hitCount = grandWords.count { stripped.contains(it) }
                    if (hitCount >= 2) continue
                    // 单个大词但句子太短（<15字）也跳过——通常是空洞口号
                    if (hitCount >= 1 && stripped.length < 15) continue
                    // 含明显说教/判断句式
                    if (stripped.contains("你必须") || stripped.contains("你应该") || stripped.contains("人一定要")) continue
                    if (stripped.contains("真正的") && stripped.contains("才是")) continue

                    lines.add(stripped.ifEmpty { line })
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            lines
        }
    }

    fun getRandomPlainTextLine(path: String, startFromHeading: String? = NARRATIVE_HEADING): String {
        val lines = loadPlainTextLines(path, startFromHeading)
        return if (lines.isNotEmpty()) lines[Random.nextInt(lines.size)] else ""
    }

    fun getRandomPlainTextLineScanAll(path: String): String {
        val lines = loadPlainTextLines(path, scanAllFile = true)
        return if (lines.isNotEmpty()) lines[Random.nextInt(lines.size)] else ""
    }

    fun getPlainTextLineCount(path: String, startFromHeading: String? = null, scanAllFile: Boolean = false): Int {
        return loadPlainTextLines(path, startFromHeading, scanAllFile).size
    }

    fun getRandomPlainTextLineFromPaths(paths: List<String>, startFromHeading: String? = NARRATIVE_HEADING, scanAllFile: Boolean = false): String {
        val allLines = mutableListOf<String>()
        for (path in paths) {
            allLines.addAll(loadPlainTextLines(path, startFromHeading, scanAllFile))
        }
        return if (allLines.isNotEmpty()) allLines[Random.nextInt(allLines.size)] else ""
    }

    fun getRandomPlainTextLineFromPathsScanAll(paths: List<String>): String {
        return getRandomPlainTextLineFromPaths(paths, null, true)
    }

    companion object {
        const val NARRATIVE_HEADING = "## 随机叙事文案池"

        @Volatile
        private var instance: TextAssetLoader? = null

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

        fun get(): TextAssetLoader {
            return instance ?: throw IllegalStateException(
                "TextAssetLoader not initialized"
            )
        }
    }
}

data class TonePaletteJson(
    @SerializedName("paletteId")
    val paletteId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("baseTint")
    val baseTint: String,
    @SerializedName("vignetteBase")
    val vignetteBase: Float,
    @SerializedName("saturation")
    val saturation: Float,
    @SerializedName("annotation")
    val annotation: String = ""
)
