package com.example.townapp.data.spotlight

enum class SpotlightCategory(
    val displayName: String,
    val colorHex: Long
) {
    SELF_CULTIVATION("修身成长", 0xFF84CC16),
    INTERPERSONAL("人际智慧", 0xFF3B82F6),
    STRATEGY("处世策略", 0xFFF59E0B),
    COGNITION("认知思维", 0xFF8B5CF6),
    EMOTION("情感心理", 0xFFEC4899),
    NUTRITION("营养补给", 0xFF22C55E),
    ENERGY("能量供给", 0xFFEAB308),
    EMOTIONAL("情绪调节", 0xFF06B6D4),
    CULTURE("文化传承", 0xFFA16207),
    COMFORT("舒适体感", 0xFF10B981),
    FUNCTION("功能实用", 0xFF6B7280),
    AESTHETIC("美学表达", 0xFFD946EF),
    SAFETY("安全保障", 0xFF3B82F6),
    EFFICIENCY("便捷高效", 0xFF14B8A6),
    ENVIRONMENT("环境适配", 0xFF059669),
    HEALING("情绪疗愈", 0xFFF43F5E),
    INSPIRATION("认知启发", 0xFF7C3AED),
    CONNECTION("社交连接", 0xFFEC4899),
    TRADITION("历史价值", 0xFF92400E),
    RESILIENCE("韧性力量", 0xFF22C55E),
    FREEDOM("自主选择", 0xFF0EA5E9),
    CHARACTER("人格品德", 0xFFE91E63),
    HERITAGE("传承智慧", 0xFF4CAF50),
    METHOD("处事方法", 0xFF9C27B0),
    SOCIETY("社会观念", 0xFF2196F3),
    TOWN_SYSTEM("小镇内在", 0xFF2E7D32),
    SELF_GROWTH("自我成长", 0xFF84CC16),
    SOCIAL("社交互动", 0xFF3B82F6),
    CONSUMPTION("消费观念", 0xFFF9E2CC),
    WORKPLACE("职场围城", 0xFFE0EEFB),
    FAMILY("家庭关系", 0xFFFBE3E6),
    RELATIONSHIP("亲密关系", 0xFFFBE3E6)
}

data class Spotlight(
    val category: SpotlightCategory,
    val content: String,
    val relevance: Double = 1.0
) {
    override fun toString(): String {
        return "${category.displayName}: $content"
    }
}
