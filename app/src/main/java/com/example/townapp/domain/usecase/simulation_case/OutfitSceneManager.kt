package com.example.townapp.domain

import com.example.townapp.data.ClimateState
import com.example.townapp.data.FabricRules
import com.example.townapp.data.OutfitScene
import com.example.townapp.data.OutfitSet
import com.example.townapp.data.model.WeatherState
import kotlin.random.Random

/**
 * 穿搭场景管理器
 *
 * 两条极简调用路径：
 * 1. 自动模式（默认）：优先场景 > 天气 > 家庭收入约束随机池
 * 2. 手动模式（青年解锁）：10套全部开放，三档效果
 *
 * 核心原则：
 * - 数据库不扩容，10套现有数据直接复用
 * - 男女通用同一套穿搭，仅在参数计算时微调
 * - 取消细碎面料材质计算，只保留：高温→疲惫、雨季→湿气
 * - 前端看不到复杂运算，后台封装
 */
object OutfitSceneManager {

    // ============================================
    // 穿搭模式
    // ============================================
    enum class OutfitMode { AUTO, MANUAL }

    // ============================================
    // 现有10套 → 场景归类
    // ============================================
    private val outfitSceneMap: Map<Int, OutfitScene> = mapOf(
        1 to OutfitScene.COMMUTE,   // 休闲日常 → 通勤
        2 to OutfitScene.COMMUTE,   // 通勤简约 → 通勤
        3 to OutfitScene.HOME,      // 温暖居家 → 居家
        4 to OutfitScene.BUSINESS,  // 商务正装 → 商务正式
        5 to OutfitScene.OUTDOOR,   // 运动出行 → 户外出行
        6 to OutfitScene.DATE,      // 文艺简约 → 约会休闲
        7 to OutfitScene.DATE,      // 城市漫步 → 约会休闲
        8 to OutfitScene.DATE,      // 温柔女生 → 约会休闲
        9 to OutfitScene.COMMUTE,   // 简约通勤 → 通勤
        10 to OutfitScene.DATE      // 周末出游 → 约会休闲
    )

    // ============================================
    // 家境档位（基于月收入）
    // ============================================
    enum class WealthLevel {
        LOW,      // 底层：月收入 < 3000
        MIDDLE,   // 普通：3000-8000
        HIGH      // 富裕：> 8000
    }

    fun classifyWealth(monthlyIncome: Double): WealthLevel = when {
        monthlyIncome < 3000 -> WealthLevel.LOW
        monthlyIncome < 8000 -> WealthLevel.MIDDLE
        else -> WealthLevel.HIGH
    }

    // ============================================
    // 穿搭效果三档
    // ============================================
    enum class OutfitEffectTier { REFINED, NEUTRAL, MISMATCHED }

    /**
     * 根据穿搭和场景，判断效果档位
     *
     * 规则：
     * - 精致套装 = 商务正装（4）、约会四套（6/7/8/10）
     * - 场景错配 = 居家（3）外出穿、盛夏穿商务正装（4）、户外场景穿居家
     * - 其余 = 中性持平
     */
    fun getEffectTier(outfit: OutfitSet, scene: OutfitScene): OutfitEffectTier {
        // 场景错配检测
        if (isMismatched(outfit, scene)) return OutfitEffectTier.MISMATCHED

        // 精致套装识别
        if (outfit.setId in setOf(4, 6, 7, 8, 10)) return OutfitEffectTier.REFINED

        return OutfitEffectTier.NEUTRAL
    }

    private fun isMismatched(outfit: OutfitSet, scene: OutfitScene): Boolean {
        val outfitScene = outfitSceneMap[outfit.setId] ?: OutfitScene.COMMUTE
        // 居家套外出 = 错配
        if (outfitScene == OutfitScene.HOME && scene != OutfitScene.HOME) return true
        // 盛夏穿商务正装 = 在天气层处理，这里不归入错配
        return false
    }

    // ============================================
    // 效果计算（三档 + 天气 + 性别微调）
    // ============================================
    data class OutfitEffectResult(
        val appearanceMod: Double,     // 颜值浮动
        val fatigueMod: Double,        // 疲惫值变化
        val diseaseRiskMod: Double,    // 疾病风险修正
        val selfEsteemMod: Int,        // 自尊感变化
        val datingSuccessMod: Double,  // 约会成功率修正
        val tier: OutfitEffectTier,
        val weatherNote: String,
        val fabricNote: String = ""    // 面料适配说明
    )

    fun calculateEffect(
        outfit: OutfitSet,
        scene: OutfitScene,
        weather: WeatherState,
        climate: ClimateState? = null,
        isFemale: Boolean = false,
        isMenstruating: Boolean = false
    ): OutfitEffectResult {
        val tier = getEffectTier(outfit, scene)
        val sb = StringBuilder()

        // 基础三档效果
        var appearance = when (tier) {
            OutfitEffectTier.REFINED -> 0.4 + Random.nextDouble() * 0.1       // 0.4~0.5
            OutfitEffectTier.NEUTRAL -> -0.2 + Random.nextDouble() * 0.4       // -0.2~+0.2
            OutfitEffectTier.MISMATCHED -> -0.6 + Random.nextDouble() * 0.3    // -0.6~-0.3
        }
        var fatigue = 0.0
        var diseaseRisk = 0.0
        var selfEsteem = when (tier) {
            OutfitEffectTier.REFINED -> 3
            OutfitEffectTier.NEUTRAL -> 0
            OutfitEffectTier.MISMATCHED -> -2
        }

        // 天气修正
        when (weather) {
            WeatherState.SUNNY -> {
                if (outfit.setId == 4) {  // 盛夏穿商务正装
                    fatigue += 0.3
                    sb.append("高温穿厚套装，疲惫值上升")
                }
            }
            WeatherState.STORM -> {
                // 雨天：纯棉质通勤款（1/2/9）更易湿气致病
                if (outfit.setId in setOf(1, 2, 9)) {
                    diseaseRisk += 0.15
                    sb.append("雨季棉质穿搭，湿气附着，疾病风险微升")
                }
                // 运动出行款（5）在雨天相对更好
                if (outfit.setId == 5) {
                    diseaseRisk -= 0.05
                }
            }
            WeatherState.RAINY -> {
                if (outfit.setId in setOf(1, 2, 9)) {
                    diseaseRisk += 0.08
                    sb.append("棉质通勤款遇雨，轻微湿气附着")
                }
            }
            else -> {}
        }

        // 性别微调（±0.05以内，整体持平）
        if (isFemale) {
            // 生理期 + 潮湿穿搭 → 脚气/湿疹概率小幅上浮
            if (isMenstruating && weather in setOf(WeatherState.RAINY, WeatherState.STORM)) {
                diseaseRisk += 0.05
                if (outfit.setId in setOf(1, 2, 3, 9)) {
                    diseaseRisk += 0.03  // 棉质/居家款叠加
                }
                sb.append("生理期体质敏感，潮湿环境穿搭注意防护")
            }
        }

        // 面料-气候适配惩罚
        val fabricNote = if (climate != null) {
            val penalty = FabricRules.calculatePenalty(outfit.fabricType, outfit.isThick, climate)
            fatigue += penalty.fatigueDelta
            diseaseRisk += penalty.diseaseRisk
            if (penalty.anxietyDelta != 0) {
                selfEsteem -= penalty.anxietyDelta / 2
            }
            if (penalty.isMismatch) {
                sb.append(" | ${penalty.reason}")
            }
            penalty.reason
        } else {
            "无面料信息"
        }

        // 约会成功率修正
        val datingSuccess = when (tier) {
            OutfitEffectTier.REFINED -> 0.15
            OutfitEffectTier.NEUTRAL -> 0.0
            OutfitEffectTier.MISMATCHED -> -0.2
        }

        return OutfitEffectResult(
            appearanceMod = appearance,
            fatigueMod = fatigue,
            diseaseRiskMod = diseaseRisk,
            selfEsteemMod = selfEsteem,
            datingSuccessMod = datingSuccess,
            tier = tier,
            weatherNote = sb.toString().ifEmpty { "当前天气对穿搭无额外影响" },
            fabricNote = fabricNote
        )
    }

    // ============================================
    // 自动模式：场景 + 天气 + 家境过滤
    // ============================================
    data class AutoSelectResult(
        val outfit: OutfitSet,
        val reason: String
    )

    fun autoSelectOutfit(
        scene: OutfitScene,
        weather: WeatherState,
        wealth: WealthLevel,
        allOutfits: List<OutfitSet>,
        climate: ClimateState? = null
    ): AutoSelectResult? {
        // Step 1: 按场景筛选
        val sceneOutfits = allOutfits.filter { outfitSceneMap[it.setId] == scene }
        if (sceneOutfits.isEmpty()) return null

        // Step 2: 天气过滤
        val weatherFiltered = sceneOutfits.filter { outfit ->
            when (weather) {
                WeatherState.SUNNY -> outfit.setId != 4  // 高温自动剔除商务正装
                WeatherState.RAINY, WeatherState.STORM -> outfit.setId != 1 && outfit.setId != 2  // 雨季剔除纯棉通勤款
                else -> true
            }
        }.ifEmpty { sceneOutfits }

        // Step 2.5: 面料季节过滤（优先推荐适配面料）
        val fabricFiltered = if (climate != null) {
            val ranked = FabricRules.getSeasonRankedFabrics(climate)
            weatherFiltered.sortedBy { outfit ->
                ranked.indexOf(outfit.fabricType).let { if (it < 0) 99 else it }
            }
        } else {
            weatherFiltered
        }

        // Step 3: 家境约束随机池
        val pool = when (wealth) {
            WealthLevel.HIGH -> {
                fabricFiltered.filter { it.setId in setOf(4, 6, 7, 8, 10) }
                    .ifEmpty { fabricFiltered }
            }
            WealthLevel.MIDDLE -> {
                fabricFiltered.filter { it.setId in setOf(1, 2, 5, 6, 7, 9, 10) }
                    .ifEmpty { fabricFiltered }
            }
            WealthLevel.LOW -> {
                fabricFiltered.filter { it.setId in setOf(1, 2, 5, 9) }
                    .ifEmpty { fabricFiltered }
            }
        }

        val selected = pool.random()
        return AutoSelectResult(
            outfit = selected,
            reason = buildSelectReason(scene, weather, wealth, selected)
        )
    }

    private fun buildSelectReason(
        scene: OutfitScene,
        weather: WeatherState,
        wealth: WealthLevel,
        outfit: OutfitSet
    ): String = when {
        wealth == WealthLevel.LOW -> "经济考量，自动选择了「${outfit.setName}」"
        weather == WeatherState.SUNNY && outfit.setId == 4 -> "天气炎热，自动避开了厚重套装"
        weather in setOf(WeatherState.RAINY, WeatherState.STORM) -> "雨天出行，自动选了更防潮的穿搭"
        else -> "根据${scene.displayName}场景，自动搭配了「${outfit.setName}」"
    }

    // ============================================
    // 查询接口
    // ============================================

    /**
     * 获取某个场景下的所有套装
     */
    fun getOutfitsByScene(scene: OutfitScene, allOutfits: List<OutfitSet>): List<OutfitSet> {
        return allOutfits.filter { outfitSceneMap[it.setId] == scene }
    }

    /**
     * 获取套装对应的场景
     */
    fun getSceneOf(outfitId: Int): OutfitScene = outfitSceneMap[outfitId] ?: OutfitScene.COMMUTE

    /**
     * 场景名称 → 枚举
     */
    fun sceneFromName(name: String): OutfitScene? = OutfitScene.entries.find {
        it.displayName == name || it.name == name
    }

    /**
     * 获取所有场景及其套装数量
     */
    fun getSceneDistribution(allOutfits: List<OutfitSet>): Map<OutfitScene, Int> {
        return OutfitScene.entries.associateWith { scene ->
            allOutfits.count { outfitSceneMap[it.setId] == scene }
        }
    }
}