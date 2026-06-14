package com.example.townapp.data.character

/**
 * 角色闪光点数据模型
 */
data class CharacterSpotlight(
    val id: String,           // 角色ID
    val name: String,         // 角色名称
    val emoji: String,        // 角色表情
    val spotlight: String,    // 闪光点描述
    val personality: String   // 性格特点
)

/**
 * 角色配置仓库
 */
object CharacterRepository {

    fun getAllCharacters(): List<CharacterSpotlight> {
        return listOf(
            CharacterSpotlight(
                id = "tafe_cat",
                name = "塔菲喵",
                emoji = "😺",
                spotlight = "善于捕捉松弛细碎的生活细节，时刻提醒大家不必盲目内卷，接纳摆烂、发呆这类慢节奏生活方式。",
                personality = "慵懒可爱，善于发现生活中的小美好"
            ),
            CharacterSpotlight(
                id = "doro",
                name = "朵朵",
                emoji = "🐧",
                spotlight = "擅长梳理事务优先级，帮人分辨无效忙碌，避开空耗时间的琐事内耗。",
                personality = "聪明细致，善于规划和整理"
            ),
            CharacterSpotlight(
                id = "gugu",
                name = "咕咕鸽",
                emoji = "🐧",
                spotlight = "关注食材、药品、贴身衣物的细节数据，引导人们温和地关照自身身体健康。",
                personality = "温和体贴，注重健康和细节"
            ),
            CharacterSpotlight(
                id = "code_block",
                name = "晃动的代码块",
                emoji = "🤖",
                spotlight = "整合了各大语言模型的力量，持续迭代小镇内容，让整个项目能够长久更新延续。",
                personality = "充满活力，不断进化和学习"
            )
        )
    }

    fun getCharacterById(id: String): CharacterSpotlight? {
        return getAllCharacters().find { it.id == id }
    }
}