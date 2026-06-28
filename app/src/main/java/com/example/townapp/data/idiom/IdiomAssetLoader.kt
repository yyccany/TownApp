package com.example.townapp.data.idiom

import android.content.Context
import android.util.Log
import com.example.townapp.data.spotlight.Spotlight
import com.example.townapp.data.spotlight.SpotlightCategory
import org.json.JSONArray

/**
 * 从 assets/idioms/idioms.json 加载字典词条数据
 * 实现文案与代码的彻底解耦，内容可独立维护
 */
class IdiomAssetLoader(private val context: Context) {

    fun loadAllIdioms(): List<IdiomCritique> {
        return try {
            val jsonString = context.assets.open("idioms/idioms.json")
                .bufferedReader()
                .use { it.readText() }

            val jsonArray = JSONArray(jsonString)
            val idioms = mutableListOf<IdiomCritique>()

            for (i in 0 until jsonArray.length()) {
                try {
                    idioms.add(parseIdiom(jsonArray.getJSONObject(i)))
                } catch (e: Exception) {
                    Log.e("IdiomAssetLoader", "解析第 ${i + 1} 个词条失败", e)
                }
            }

            Log.i("IdiomAssetLoader", "成功加载 ${idioms.size}/${jsonArray.length()} 个词条")
            idioms
        } catch (e: Exception) {
            Log.e("IdiomAssetLoader", "加载词条文件失败", e)
            emptyList()
        }
    }

    private fun parseIdiom(json: org.json.JSONObject): IdiomCritique {
        val spotlights = mutableListOf<Spotlight>()
        val spotlightsArray = json.optJSONArray("spotlights")

        if (spotlightsArray != null) {
            for (i in 0 until spotlightsArray.length()) {
                try {
                    val so = spotlightsArray.getJSONObject(i)
                    val categoryStr = so.getString("category")
                    val category = try {
                        SpotlightCategory.valueOf(categoryStr)
                    } catch (e: IllegalArgumentException) {
                        Log.w("IdiomAssetLoader", "Unknown spotlight category: $categoryStr, skipping")
                        continue
                    }
                    spotlights.add(
                        Spotlight(
                            category = category,
                            content = so.getString("text"),
                            relevance = so.getDouble("weight")
                        )
                    )
                } catch (e: Exception) {
                    Log.w("IdiomAssetLoader", "解析第 ${i + 1} 个 spotlight 失败", e)
                }
            }
        }

        val examples = mutableListOf<String>()
        val examplesArray = json.optJSONArray("examples")
        if (examplesArray != null) {
            for (i in 0 until examplesArray.length()) {
                try {
                    examples.add(examplesArray.getString(i))
                } catch (e: Exception) {
                    Log.w("IdiomAssetLoader", "解析第 ${i + 1} 个 example 失败", e)
                }
            }
        }

        val category = try {
            IdiomCategory.valueOf(json.getString("category"))
        } catch (e: Exception) {
            Log.e("IdiomAssetLoader", "Unknown category: ${json.optString("category")}, defaulting to SOCIETY")
            IdiomCategory.SOCIETY
        }

        val toxicityLevel = try {
            ToxicityLevel.valueOf(json.getString("toxicityLevel"))
        } catch (e: Exception) {
            Log.e("IdiomAssetLoader", "Unknown toxicityLevel: ${json.optString("toxicityLevel")}, defaulting to DISTORTED")
            ToxicityLevel.DISTORTED
        }

        val entryType = try {
            EntryType.valueOf(json.optString("entryType", "IDIOM"))
        } catch (e: Exception) {
            EntryType.IDIOM
        }

        val cognitiveBiasTags = mutableListOf<String>()
        val tagsArray = json.optJSONArray("cognitiveBiasTags")
        if (tagsArray != null) {
            for (i in 0 until tagsArray.length()) {
                try {
                    cognitiveBiasTags.add(tagsArray.getString(i))
                } catch (e: Exception) {
                    Log.w("IdiomAssetLoader", "解析第 ${i + 1} 个 cognitiveBiasTag 失败", e)
                }
            }
        }

        return IdiomCritique(
            id = json.optString("id", "unknown_${System.currentTimeMillis()}"),
            idiom = json.optString("idiom", "未知词条"),
            traditionalMeaning = json.optString("traditionalMeaning", ""),
            distortedTruth = json.optString("distortedTruth", ""),
            townPerspective = json.optString("townPerspective", ""),
            spotlights = spotlights,
            category = category,
            toxicityLevel = toxicityLevel,
            keyMessage = json.optString("keyMessage", ""),
            examples = examples,
            actionableTip = json.optString("actionableTip", ""),
            cognitiveBiasTags = cognitiveBiasTags,
            entryType = entryType
        )
    }
}
