package com.example.townapp.data.model

/**
 * 学术引用数据模型
 */
data class AcademicReference(
    val theoryName: String,        // 理论名称
    val researcher: String,        // 提出者
    val year: Int,                 // 发表年份
    val summary: String,           // 一句话摘要（卡片底部显示）
    val fullText: String = ""      // 详细解释（折叠显示）
)

/**
 * 触发条件数据模型
 */
data class TriggerCondition(
    val module: String,            // 触发模块：food/clothing/housing/cognitive
    val conditionType: String,     // 条件类型：count/idle/price/usage_rate
    val threshold: Double,         // 阈值
    val operator: String = ">"     // 比较运算符：>/</>=/<=/==
)

/**
 * 成语稀有度枚举
 */
enum class IdiomRarity {
    COMMON,    // 常见（基础认知）
    UNCOMMON,  // 少见（进阶认知）
    RARE,      // 稀有（深度认知）
    EPIC,      // 史诗（核心认知）
    LEGENDARY  // 传说（终极认知）
}

/**
 * 完整成语数据模型
 */
data class IdiomData(
    val idiomId: Int,                          // 唯一标识
    val name: String,                          // 成语名称
    val traditionalMeaning: String,            // 传统解读
    val awakeningMeaning: String,              // 觉醒解读
    val spotlight: String = "",                // 时代闪光点（客观价值）
    val dataTemplate: String,                  // 数据填充模板
    val actionSuggestion: String,              // 行动建议
    val academicRefs: List<AcademicReference>, // 学术引用列表
    val triggerConditions: List<TriggerCondition>, // 触发条件
    val rarity: IdiomRarity = IdiomRarity.COMMON // 稀有度
)

/**
 * 观念思辨三阶段 —— 现代规训 / AI 时代观念卡片数据模型。
 *
 * 与 IdiomData（传统成语）共用触发/展示体系。
 * 每个卡片通过 unlockCondition（觉醒值阈值）分阶解锁，
 * 用户随着认知提升自然接触到更深层次的观念反思。
 */
data class IdiomReflection(
    val id: String,                          // 唯一标识（如 "duo_he_ren_gou_tong"）
    val idiom: String,                        // 观念名称（如 "要多与人沟通"）
    val traditionalView: String,              // 传统/社会规训解读
    val awakeningView: String,                // 觉醒视角 —— 只呈现代价，不做评判
    val relatedBehaviorTypes: List<String>,   // 关联行为类型（social / work / mental 等）
    val unlockCondition: String               // 解锁条件（如 "觉醒值≥1500"）
)