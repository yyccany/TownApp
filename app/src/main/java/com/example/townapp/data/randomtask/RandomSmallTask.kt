package com.example.townapp.data.randomtask

import com.example.townapp.data.microevent.TownCharacter

/**
 * 无目的随机小事
 *
 * 没有目标，没有打卡，没有奖励。
 * 只是一些5分钟就能完成的小事，帮你在这个操蛋的世界里，找到一点点小小的甜。
 */

/**
 * 随机小事数据类
 */
data class RandomSmallTask(
    val id: String,
    val content: String,           // 事情内容
    val emoji: String,           // 配套emoji
    val category: TaskCategory,  // 分类
    val duration: Int = 5,       // 大约需要的时间（分钟）
    val difficulty: Difficulty = Difficulty.EASY  // 难度
)

/**
 * 任务分类
 */
enum class TaskCategory(val displayName: String) {
    CARE("自我关爱"),      // 自我关爱
    MOVEMENT("轻微活动"),  // 轻微活动
    PLEASURE("感官享受"),  // 感官享受
    SOCIAL("社交联系"),    // 社交联系
    CREATIVE("创意表达"),  // 创意表达
    MINDFUL("正念放松")    // 正念放松
}

/**
 * 难度
 */
enum class Difficulty(val displayName: String) {
    EASY("超级简单"),      // 超级简单
    MEDIUM("有点意思"),    // 有点意思
    SPECIAL("值得一试")    // 值得一试
}

/**
 * 随机小事库
 */
object RandomSmallTaskLibrary {

    /**
     * 获取所有随机小事
     */
    fun getAllTasks(): List<RandomSmallTask> = listOf(
        // ============================================
        // 自我关爱类 (CARE)
        // ============================================
        RandomSmallTask(
            id = "care_1",
            content = "去喝一杯温度刚好的水",
            emoji = "💧",
            category = TaskCategory.CARE,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "care_2",
            content = "给自己泡一杯热茶或咖啡",
            emoji = "☕",
            category = TaskCategory.CARE,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "care_3",
            content = "洗一个热水澡，认认真真地洗",
            emoji = "🚿",
            category = TaskCategory.CARE,
            difficulty = Difficulty.MEDIUM
        ),
        RandomSmallTask(
            id = "care_4",
            content = "换上那件最舒服的睡衣或家居服",
            emoji = "👗",
            category = TaskCategory.CARE,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "care_5",
            content = "认真地涂一层护手霜或身体乳",
            emoji = "🧴",
            category = TaskCategory.CARE,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "care_6",
            content = "给自己的脸敷一个面膜",
            emoji = "🥒",
            category = TaskCategory.CARE,
            difficulty = Difficulty.MEDIUM
        ),
        RandomSmallTask(
            id = "care_7",
            content = "躺下来，闭上眼睛，什么都不做，就躺着",
            emoji = "😌",
            category = TaskCategory.CARE,
            difficulty = Difficulty.SPECIAL
        ),

        // ============================================
        // 轻微活动类 (MOVEMENT)
        // ============================================
        RandomSmallTask(
            id = "movement_1",
            content = "去楼下走5分钟，就5分钟",
            emoji = "🚶",
            category = TaskCategory.MOVEMENT,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "movement_2",
            content = "伸一个大大的懒腰，像小猫一样",
            emoji = "🐱",
            category = TaskCategory.MOVEMENT,
            difficulty = Difficulty.SPECIAL
        ),
        RandomSmallTask(
            id = "movement_3",
            content = "去窗边站一会儿，看看外面的天空",
            emoji = "🌤️",
            category = TaskCategory.MOVEMENT,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "movement_4",
            content = "出门买一根冰淇淋，就一根",
            emoji = "🍦",
            category = TaskCategory.MOVEMENT,
            difficulty = Difficulty.MEDIUM
        ),
        RandomSmallTask(
            id = "movement_5",
            content = "去楼下取个快递，哪怕没有快递也要下去走走",
            emoji = "📦",
            category = TaskCategory.MOVEMENT,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "movement_6",
            content = "去路边摊买一样你很久没吃的小吃",
            emoji = "🍢",
            category = TaskCategory.MOVEMENT,
            difficulty = Difficulty.MEDIUM
        ),

        // ============================================
        // 感官享受类 (PLEASURE)
        // ============================================
        RandomSmallTask(
            id = "pleasure_1",
            content = "吃一块你一直舍不得吃的零食",
            emoji = "🍫",
            category = TaskCategory.PLEASURE,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "pleasure_2",
            content = "听一首让你开心的老歌",
            emoji = "🎵",
            category = TaskCategory.PLEASURE,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "pleasure_3",
            content = "看一集你最喜欢的动画片或综艺",
            emoji = "📺",
            category = TaskCategory.PLEASURE,
            difficulty = Difficulty.MEDIUM
        ),
        RandomSmallTask(
            id = "pleasure_4",
            content = "点一份你一直想吃但没点的外卖",
            emoji = "🍜",
            category = TaskCategory.PLEASURE,
            difficulty = Difficulty.MEDIUM
        ),
        RandomSmallTask(
            id = "pleasure_5",
            content = "打开窗户，让新鲜空气吹进来",
            emoji = "🌬️",
            category = TaskCategory.PLEASURE,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "pleasure_6",
            content = "躺在床上，拉上窗帘，给自己制造一个黑暗的舒适空间",
            emoji = "🛏️",
            category = TaskCategory.PLEASURE,
            difficulty = Difficulty.SPECIAL
        ),

        // ============================================
        // 社交联系类 (SOCIAL)
        // ============================================
        RandomSmallTask(
            id = "social_1",
            content = "给你喜欢的人发一句\"我想你了\"",
            emoji = "💌",
            category = TaskCategory.SOCIAL,
            difficulty = Difficulty.MEDIUM
        ),
        RandomSmallTask(
            id = "social_2",
            content = "给很久没联系的朋友发个消息，随便聊两句",
            emoji = "💬",
            category = TaskCategory.SOCIAL,
            difficulty = Difficulty.MEDIUM
        ),
        RandomSmallTask(
            id = "social_3",
            content = "给爸妈打个电话，就说\"没事，想你们了\"",
            emoji = "📞",
            category = TaskCategory.SOCIAL,
            difficulty = Difficulty.SPECIAL
        ),
        RandomSmallTask(
            id = "social_4",
            content = "在街上对陌生人笑一下",
            emoji = "😊",
            category = TaskCategory.SOCIAL,
            difficulty = Difficulty.SPECIAL
        ),

        // ============================================
        // 创意表达类 (CREATIVE)
        // ============================================
        RandomSmallTask(
            id = "creative_1",
            content = "对着镜子笑一下，哪怕假装也行",
            emoji = "😁",
            category = TaskCategory.CREATIVE,
            difficulty = Difficulty.SPECIAL
        ),
        RandomSmallTask(
            id = "creative_2",
            content = "拿出手机，随便拍一张你觉得好看的东西",
            emoji = "📷",
            category = TaskCategory.CREATIVE,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "creative_3",
            content = "在手机备忘录里写一句话，就一句话",
            emoji = "📝",
            category = TaskCategory.CREATIVE,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "creative_4",
            content = "哼一首你最喜欢的歌，不管跑不跑调",
            emoji = "🎤",
            category = TaskCategory.CREATIVE,
            difficulty = Difficulty.SPECIAL
        ),
        RandomSmallTask(
            id = "creative_5",
            content = "在纸上随便画点什么，随便画",
            emoji = "🎨",
            category = TaskCategory.CREATIVE,
            difficulty = Difficulty.EASY
        ),

        // ============================================
        // 正念放松类 (MINDFUL)
        // ============================================
        RandomSmallTask(
            id = "mindful_1",
            content = "什么都不做，发5分钟呆",
            emoji = "🫠",
            category = TaskCategory.MINDFUL,
            difficulty = Difficulty.SPECIAL
        ),
        RandomSmallTask(
            id = "mindful_2",
            content = "深呼吸10次，慢慢地吸，慢慢地呼",
            emoji = "🌬️",
            category = TaskCategory.MINDFUL,
            difficulty = Difficulty.EASY
        ),
        RandomSmallTask(
            id = "mindful_3",
            content = "去摸一下路边的猫或狗",
            emoji = "🐕",
            category = TaskCategory.MINDFUL,
            difficulty = Difficulty.MEDIUM
        ),
        RandomSmallTask(
            id = "mindful_4",
            content = "站在窗边，看雨或者看云，就看着",
            emoji = "☁️",
            category = TaskCategory.MINDFUL,
            difficulty = Difficulty.SPECIAL
        ),
        RandomSmallTask(
            id = "mindful_5",
            content = "躺在床上，感受一下被子的柔软",
            emoji = "🛏️",
            category = TaskCategory.MINDFUL,
            difficulty = Difficulty.SPECIAL
        )
    )

    /**
     * 随机获取一个小事
     */
    fun getRandomTask(): RandomSmallTask {
        return getAllTasks().random()
    }

    /**
     * 按分类获取
     */
    fun getTasksByCategory(category: TaskCategory): List<RandomSmallTask> {
        return getAllTasks().filter { it.category == category }
    }
}
