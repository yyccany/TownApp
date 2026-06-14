package com.example.townapp.business

import com.example.townapp.data.cognition.*
import kotlin.math.roundToInt

/**
 * 认知觉醒后果计算引擎
 * 
 * 用于计算用户在模拟器中的选择后果
 * 遵循「实事求是」原则，只展示数据，不做道德评判
 */
object CognitionConsequenceEngine {
    
    /**
     * 计算选择的后果评分
     * 
     * @param choice 用户的选择
     * @param socialClass 用户选择的社会阶层
     * @return 后果评分（0-100）
     */
    fun calculateConsequenceScore(
        choice: ScenarioChoice,
        socialClass: SocialClass
    ): ConsequenceResult {
        val consequence = choice.consequences[socialClass] 
            ?: return ConsequenceResult(
                totalScore = 0,
                evaluation = "未知后果",
                dimensionScores = emptyList(),
                immediateResult = "未知",
                longTermResult = "未知",
                quote = "",
                keyInsight = ""
            )
        
        // 计算各个维度的影响评分
        val dimensionScores = consequence.impacts.map { impact ->
            DimensionScore(
                dimension = impact.dimension,
                score = impact.magnitude,
                description = impact.result
            )
        }
        
        // 计算总体评分
        val totalScore = dimensionScores.sumOf { it.score }
        
        // 计算综合评价
        val evaluation = when {
            totalScore <= 5 -> ConsequenceEvaluation.POSITIVE
            totalScore <= 10 -> ConsequenceEvaluation.NEUTRAL
            else -> ConsequenceEvaluation.NEGATIVE
        }
        
        return ConsequenceResult(
            totalScore = totalScore,
            evaluation = evaluation.displayName,
            dimensionScores = dimensionScores,
            immediateResult = consequence.immediateResult,
            longTermResult = consequence.longTermResult,
            quote = consequence.quote,
            keyInsight = consequence.keyInsight
        )
    }
    
    /**
     * 计算认知解剖的震撼度
     * 
     * @param dissection 认知解剖内容
     * @return 震撼度评分（1-5）
     */
    fun calculateImpactLevel(dissection: CognitionDissection): Int {
        // 评估震撼度
        val factors = listOf(
            dissection.traditionalReality.length / 100.0,  // 内容长度
            dissection.realConsequence.lines().size / 5.0,  // 真实后果的行数
            dissection.analysis.length / 200.0,  // 分析的深度
            dissection.conclusion.length / 100.0  // 结论的深度
        )
        
        return factors.sum().coerceIn(1.0, 5.0).toInt()
    }
    
    /**
     * 获取认知进度
     * 
     * @param completedDissections 已完成的解剖数量
     * @param completedScenarios 已完成的场景数量
     * @return 认知进度百分比
     */
    fun calculateProgress(
        completedDissections: Int,
        completedScenarios: Int
    ): Int {
        val totalDissections = CognitionData.getAllDissections().size
        val totalScenarios = SimulatorScenarios.getAllScenarios().size
        
        val dissectionProgress = (completedDissections.toFloat() / totalDissections * 50).toInt()
        val scenarioProgress = (completedScenarios.toFloat() / totalScenarios * 50).toInt()
        
        return dissectionProgress + scenarioProgress
    }
    
    /**
     * 生成认知洞察
     * 
     * @param choices 用户的所有选择
     * @param socialClass 用户的阶层
     * @return 洞察文本
     */
    fun generateInsight(
        choices: List<ScenarioChoice>,
        socialClass: SocialClass
    ): String {
        val traditionalCount = choices.count { it.id.contains("traditional") }
        val realisticCount = choices.count { it.id.contains("realistic") }
        
        return when {
            traditionalCount > realisticCount * 2 -> """
                你的选择倾向于传统道德观。
                
                这意味着你是一个重视人情、重视关系的人。
                你的善良和真诚是你的优点，但有时候会让你吃亏。
                
                建议：在保护好自己的前提下，继续做一个善良的人。
            """.trimIndent()
            
            realisticCount > traditionalCount * 2 -> """
                你的选择倾向于现实法则。
                
                这意味着你是一个理性、有边界感的人。
                你知道如何在社会中保护自己。
                
                建议：善良是一种选择，不是义务。继续保持你的理性。
            """.trimIndent()
            
            else -> """
                你的选择在传统道德和现实法则之间平衡。
                
                这意味着你是一个灵活、适应性强的人。
                你知道在不同情况下做出不同的选择。
                
                建议：保持这种灵活性，但也要有自己的底线。
            """.trimIndent()
        }
    }
    
    /**
     * 获取推荐场景
     * 
     * @param userClass 用户的阶层
     * @param completedScenarioIds 已完成的场景ID
     * @return 推荐场景列表
     */
    fun getRecommendedScenarios(
        userClass: SocialClass,
        completedScenarioIds: List<String>
    ): List<SimulatorScenario> {
        return SimulatorScenarios.getAllScenarios()
            .filter { it.id !in completedScenarioIds }
            .filter { userClass in it.applicableClasses }
            .take(3)
    }
    
    /**
     * 获取推荐解剖
     * 
     * @param completedTopicIds 已完成的主题ID
     * @return 推荐解剖列表
     */
    fun getRecommendedDissections(
        completedTopicIds: List<String>
    ): List<CognitionDissection> {
        return CognitionData.getAllDissections()
            .filter { it.topic.id !in completedTopicIds }
            .take(2)
    }
}

/**
 * 后果结果数据类
 */
data class ConsequenceResult(
    val totalScore: Int,                    // 总评分（0-25）
    val evaluation: String,                  // 综合评价
    val dimensionScores: List<DimensionScore>, // 各维度评分
    val immediateResult: String,            // 即时结果
    val longTermResult: String,             // 长期结果
    val quote: String,                     // 引用语
    val keyInsight: String                  // 关键洞察
) {
    /**
     * 获取评分等级
     */
    fun getLevel(): ScoreLevel {
        return when {
            totalScore <= 5 -> ScoreLevel.EXCELLENT
            totalScore <= 10 -> ScoreLevel.GOOD
            totalScore <= 15 -> ScoreLevel.AVERAGE
            totalScore <= 20 -> ScoreLevel.POOR
            else -> ScoreLevel.VERY_POOR
        }
    }
    
    /**
     * 获取评级描述
     */
    fun getLevelDescription(): String {
        return getLevel().description
    }
}

/**
 * 维度评分
 */
data class DimensionScore(
    val dimension: ImpactDimension,
    val score: Int,  // 1-5
    val description: String
)

/**
 * 评分等级
 */
enum class ScoreLevel(val displayName: String, val description: String) {
    EXCELLENT("🌟", "极佳选择"),
    GOOD("👍", "良好选择"),
    AVERAGE("😐", "一般选择"),
    POOR("😟", "较差选择"),
    VERY_POOR("❌", "糟糕选择")
}

/**
 * 综合评价
 */
enum class ConsequenceEvaluation(val displayName: String) {
    POSITIVE("正面后果"),
    NEUTRAL("中性后果"),
    NEGATIVE("负面后果")
}
