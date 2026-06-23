package com.example.townapp.domain.model.simulation

import com.example.townapp.data.database.entity.UserBodyState
import com.example.townapp.data.database.entity.FatigueStatus

/**
 * 居民身体状态展示模型（domain 层 VO）
 * 
 * 整合 UserBodyState 实体数据，供 UI 层统一渲染
 * 遵循实事求是：如实呈现身体数据，不做主观评判
 * 遵循以人为本：身体状态是居民一切行为选择的基础
 * 
 * 数据来源：Room tb_user_body_state 表
 */
data class ResidentBodyStateVo(
    // 基础信息
    val userId: Int,
    val userName: String,
    
    // 消化系统
    val satiety: Int,                    // 饱腹感 0-100
    val nutritionBalance: Int,           // 营养均衡度 0-100
    val gastroBurden: Int,               // 肠胃负担 0-100
    
    // 生理指标
    val bloodSugar: Double,              // 血糖 mmol/L
    val bloodPressure: Int,              // 收缩压 mmHg
    val heartRate: Int,                  // 心率 bpm
    val bodyTemperature: Double,         // 体温 ℃
    val bodyFatPercent: Double,          // 体脂率 %
    val cholesterolMg: Double,           // 胆固醇 mg/dL
    
    // 皮肤舒适
    val comfortLevel: Int,               // 舒适度 0-100
    val skinStatus: Int,                // 皮肤状态 0-100
    
    // 毒素与免疫
    val toxinLevel: Double,             // 毒素累积 0-100
    val heavyMetalAccum: Double,        // 重金属累积
    val immuneLevel: Int,               // 免疫水平 0-100
    
    // 能量与健康
    val energy: Int,                     // 精力 0-100
    val healthScore: Int,               // 综合健康评分 0-100
    
    // 情绪
    val mood: Int,                      // 情绪 0-100
    
    // 疲劳状态
    val fatigueLevel: Int,
    val fatigueStatus: String,
    val fatigueDescription: String,
    
    // 进食记录
    val lastMealName: String,
    val lastMealTime: Long,
    val totalMeals: Int,
    val highRiskMeals: Int,
    
    // 状态标签
    val isHungry: Boolean,
    val isTired: Boolean,
    val isSick: Boolean,
    val isOverburdened: Boolean,
    val isHighBloodSugar: Boolean,
    val isOverFatigued: Boolean,
    
    // 健康评估
    val healthLevel: String,            // 优秀/良好/一般/较差
    val healthLevelColor: String,       // UI 颜色 hex
    val healthAdvice: String            // 实事求是建议
) {
    companion object {
        fun fromEntity(state: UserBodyState): ResidentBodyStateVo {
            val fatigueStatusEnum = state.fatigueStatus
            
            return ResidentBodyStateVo(
                // 基础
                userId = state.userId,
                userName = state.userName,
                
                // 消化
                satiety = state.satiety,
                nutritionBalance = state.nutritionBalance,
                gastroBurden = state.gastroBurden,
                
                // 生理
                bloodSugar = state.bloodSugar,
                bloodPressure = state.bloodPressure,
                heartRate = state.heartRate,
                bodyTemperature = state.bodyTemperature,
                bodyFatPercent = state.bodyFatPercent,
                cholesterolMg = state.cholesterolMg,
                
                // 舒适
                comfortLevel = state.comfortLevel,
                skinStatus = state.skinStatus,
                
                // 毒素
                toxinLevel = state.toxinLevel,
                heavyMetalAccum = state.heavyMetalAccum,
                immuneLevel = state.immuneLevel,
                
                // 能量
                energy = state.energy,
                healthScore = state.healthScore,
                
                // 情绪
                mood = state.mood,
                
                // 疲劳
                fatigueLevel = state.fatigueLevel,
                fatigueStatus = fatigueStatusEnum.displayName,
                fatigueDescription = fatigueStatusEnum.description,
                
                // 进食
                lastMealName = state.lastMealName,
                lastMealTime = state.lastMealTime,
                totalMeals = state.totalMeals,
                highRiskMeals = state.highRiskMeals,
                
                // 状态标签
                isHungry = state.isHungry,
                isTired = state.isTired,
                isSick = state.isSick,
                isOverburdened = state.isOverburdened,
                isHighBloodSugar = state.isHighBloodSugar,
                isOverFatigued = state.isOverFatigued,
                
                // 健康评估
                healthLevel = when {
                    state.healthScore >= 85 -> "优秀"
                    state.healthScore >= 70 -> "良好"
                    state.healthScore >= 50 -> "一般"
                    else -> "较差"
                },
                healthLevelColor = when {
                    state.healthScore >= 85 -> "#4CAF50"
                    state.healthScore >= 70 -> "#8BC34A"
                    state.healthScore >= 50 -> "#FF9800"
                    else -> "#F44336"
                },
                healthAdvice = generateHealthAdvice(state)
            )
        }
        
        private fun generateHealthAdvice(state: UserBodyState): String {
            return when {
                state.isHungry -> "已经很久没吃东西了，身体开始消耗储备。找点东西吃吧，不一定非要营养均衡，能吃进去就行。"
                state.isOverburdened -> "肠胃负担较重，近期建议选择清淡一些的食物，给消化系统一些喘息时间。"
                state.toxinLevel > 70 -> "体内毒素累积较多，选择天然、未加工的食物会更有帮助。"
                state.immuneLevel < 40 -> "免疫力偏低，多摄入优质蛋白和维生素有助于改善。"
                state.fatigueLevel > 75 -> "身体已经非常疲惫了，好好休息比继续吃东西更重要。"
                state.bloodSugar > 8.0 -> "血糖偏高，精制碳水化合物摄入要控制了。"
                state.healthScore >= 85 -> "身体状态很好，保持现在的饮食习惯就好。"
                state.healthScore >= 70 -> "整体状态不错，偶尔放纵一下也没关系。"
                else -> "身体有些透支，但吃点东西总比饿着强。"
            }
        }
    }
}

/**
 * 进食影响结果（应用食物后的反馈）
 */
data class FeedingResultVo(
    val updatedBodyState: ResidentBodyStateVo,
    val moodDelta: Int,
    val reaction: String,
    val reactionEmoji: String,
    val nutritionGain: Int,      // 营养提升
    val toxinGain: Double,       // 毒素增加
    val energyChange: Int       // 精力变化
)
