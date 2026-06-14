package com.example.townapp.domain.engine

/**
 * 玩家行为执行结果。
 *
 * @param success 行为是否成功执行
 * @param message 反馈文案（成功提示或失败原因）
 * @param sparkle 附加的闪光/金句文案，可为空
 */
data class ActionResult(
    val success: Boolean,
    val message: String,
    val sparkle: String = ""
)
