package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 食品风险指标模板表（tb_food_risk）
 * 系统只读热表，存储标准化风险数据。
 * 包含重金属、农残、糖油、加工四大类风险。
 */
@Entity(
    tableName = "tb_food_risk",
    indices = [
        Index(value = ["foodName"]),
        Index(value = ["riskLevel"])
    ]
)
data class FoodRiskEntity(
    @PrimaryKey val foodId: Int,               // 全局ID（对应 FoodNutrition.foodId）
    val foodName: String,                      // 食材名称（冗余，加速缓存查询）
    val riskLevel: String = "LOW",             // 综合风险等级：LOW / MEDIUM / HIGH / EXTREME
    val heavyMetalRisk: Double = 0.0,          // 重金属风险 0-100
    val heavyMetalDesc: String = "",           // 重金属风险描述
    val pesticideRisk: Double = 0.0,           // 农残风险 0-100
    val pesticideDesc: String = "",            // 农残风险描述
    val sugarOilRisk: Double = 0.0,            // 糖油风险 0-100
    val sugarOilDesc: String = "",             // 糖油风险描述
    val processingRisk: Double = 0.0,          // 加工风险 0-100
    val processingDesc: String = "",             // 加工风险描述
    val additiveRisk: Double = 0.0,            // 添加剂风险 0-100
    val additiveDesc: String = "",             // 添加剂风险描述
    val totalRiskScore: Int = 0,               // 总风险评分 0-100
    val merchantClaim: String = "",            // 商家宣传文案
    val actualEffect: String = "",             // 实际功效（基于数据）
    val caution: String = ""                   // 风险提示
)