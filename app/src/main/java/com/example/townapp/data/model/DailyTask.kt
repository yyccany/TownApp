package com.example.townapp.data.model

import java.util.UUID

/**
 * 每日任务数据模型。
 *
 * 完全中立原则：
 * - 只匹配行为类型（type 字段），不判断行为"好/坏"
 * - 记录「任何符合类型的动作」都算完成任务
 * - 吃沙拉和吃炸鸡都算"记录一次饮食"
 */
data class DailyTask(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val targetActionType: String,
    val targetCount: Int = 1,
    val currentCount: Int = 0,
    val isCompleted: Boolean = false
) {
    val progress: Float
        get() = (currentCount.toFloat() / targetCount.toFloat()).coerceIn(0f, 1f)
}

/**
 * 预设任务池。
 *
 * 所有任务只要求"记录行为"，不要求"好的行为"。
 * 每天从池中随机抽取 3 个任务。
 */
object DailyTaskPool {
    val allTasks: List<DailyTask> = listOf(
        DailyTask(
            name = "记录一次饮食",
            description = "记录你今天吃了什么",
            targetActionType = "food"
        ),
        DailyTask(
            name = "记录一次消费",
            description = "记录你今天花了多少钱",
            targetActionType = "clothing"
        ),
        DailyTask(
            name = "记录一次学习",
            description = "记录你今天学习了什么",
            targetActionType = "mental"
        ),
        DailyTask(
            name = "记录一次运动",
            description = "记录你今天的运动情况",
            targetActionType = "health"
        ),
        DailyTask(
            name = "记录一次社交",
            description = "记录你今天和谁交流了",
            targetActionType = "social"
        ),
        DailyTask(
            name = "查看一个生活方式",
            description = "打开生活图鉴，查看一个已解锁的条目",
            targetActionType = "view_lifestyle"
        ),
        DailyTask(
            name = "点击一个建筑",
            description = "点击小镇中的任意一个建筑，查看详情",
            targetActionType = "click_building"
        )
    )
}