package com.example.townapp.data.companion

/**
 * 使用场景类型
 */
enum class UseScene(val displayName: String, val prefix: String) {
    WEAR("穿戴", "穿了"),
    USE("使用", "用了"),
    BUY("购买", "买了"),
    EAT("吃/喝", "吃了")
}
