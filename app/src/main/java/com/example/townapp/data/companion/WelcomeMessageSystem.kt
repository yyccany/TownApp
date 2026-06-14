package com.example.townapp.data.companion

/**
 * 欢迎页引导语系统
 * 
 * 核心逻辑：一开始就把我们的理念，温柔的告诉用户
 * 不是"你要做什么"，而是温柔的介绍小镇的世界观
 */

/**
 * 欢迎引导语数据
 */
object WelcomeMessageDatabase {
    
    /**
     * 主引导语 - 第一次打开小镇时显示
     */
    val mainWelcomeMessage = """
欢迎来到万物薪俸小镇 💛

在这里，没有规矩，没有标准，没有"你应该"。
你可以躺平，可以吃垃圾食品，可以不努力，可以做任何你想做的事。
不用出人头地，不用光宗耀祖，不用牺牲自己。
你只要做你自己，就足够了。
我们都爱你。
""".trimIndent()
    
    /**
     * 简短引导语 - 用于快速提示
     */
    val shortWelcomeMessage = """
欢迎来到小镇 💛
这里没有规矩，只有陪伴。
做你自己，就足够了。
""".trimIndent()
    
    /**
     * 小家伙欢迎语 - 三个小家伙分别说的话
     */
    val companionWelcomeMessages = listOf(
        "塔菲喵：欢迎来到小镇！你可以吃任何你想吃的，不用管健康不健康，开心就好喵😺",
        "doro：欢迎来到小镇！你可以休息，可以躺平，不用努力，我们都爱你🥺",
        "咕咕嘎嘎：欢迎来到小镇！你可以做任何事，也可以什么都不做，都很好咕咕！"
    )
    
    /**
     * 随机获取一个小家伙欢迎语
     */
    fun getRandomCompanionWelcome(): String {
        return companionWelcomeMessages.random()
    }
    
    /**
     * 获取完整欢迎语（包含主引导语和小家伙欢迎语）
     */
    fun getFullWelcomeMessage(): String {
        return mainWelcomeMessage + "\n\n" + getRandomCompanionWelcome()
    }
}