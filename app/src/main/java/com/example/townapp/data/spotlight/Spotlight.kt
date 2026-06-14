package com.example.townapp.data.spotlight

enum class SpotlightCategory(
    val displayName: String,
    val emoji: String,
    val colorHex: Long
) {
    // 成语分类
    SELF_CULTIVATION("修身成长", "🌱", 0xFF84CC16),
    INTERPERSONAL("人际智慧", "🤝", 0xFF3B82F6),
    STRATEGY("处世策略", "🎯", 0xFFF59E0B),
    COGNITION("认知思维", "💡", 0xFF8B5CF6),
    EMOTION("情感心理", "💗", 0xFFEC4899),
    
    // 物资分类
    NUTRITION("营养补给", "🥗", 0xFF22C55E),
    ENERGY("能量供给", "⚡", 0xFFEAB308),
    EMOTIONAL("情绪调节", "🌈", 0xFF06B6D4),
    CULTURE("文化传承", "🏺", 0xFFA16207),
    COMFORT("舒适体感", "🧘", 0xFF10B981),
    FUNCTION("功能实用", "🔧", 0xFF6B7280),
    AESTHETIC("美学表达", "🎨", 0xFFD946EF),
    SAFETY("安全保障", "🛡️", 0xFF3B82F6),
    EFFICIENCY("便捷高效", "🚀", 0xFF14B8A6),
    ENVIRONMENT("环境适配", "🌍", 0xFF059669),
    HEALING("情绪疗愈", "💆", 0xFFF43F5E),
    INSPIRATION("认知启发", "🔮", 0xFF7C3AED),
    CONNECTION("社交连接", "👥", 0xFFEC4899),
    TRADITION("历史价值", "📜", 0xFF92400E),
    RESILIENCE("韧性力量", "🌿", 0xFF22C55E),
    FREEDOM("自主选择", "🗽", 0xFF0EA5E9)
}

data class Spotlight(
    val category: SpotlightCategory,
    val content: String,
    val relevance: Double = 1.0
) {
    override fun toString(): String {
        return "${category.emoji} ${category.displayName}: $content"
    }
}