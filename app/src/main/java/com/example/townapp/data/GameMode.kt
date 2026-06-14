package com.example.townapp.data

/**
 * 游戏模式：自动托管 / 精细手动
 *
 * 自动模式：系统根据角色参数自动运行三餐、穿搭、晚间行为，
 *          玩家仅审阅月度简报。
 * 手动模式：工作日晚间、周末、三餐、穿搭、职业节点弹出操作面板，
 *          玩家逐项规划。
 *
 * 两种模式随时切换，原有存档直接兼容。
 */
enum class GameMode(val label: String, val description: String) {
    AUTO("自动托管", "系统根据角色性格自动运行，你只需审阅月度简报"),
    MANUAL("精细手动", "三餐、穿搭、晚间时段、职业路线由你亲手规划")
}