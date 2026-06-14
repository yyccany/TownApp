package com.example.townapp.data.companion

import com.example.townapp.data.database.entity.CuteCharacterEntity
import com.example.townapp.data.database.entity.CuteEmotionQuoteEntity
import com.example.townapp.data.database.entity.CuteSceneQuoteEntity

object CompanionSeedData {
    
    // ============================================
    // 🐱 角色档案数据
    // ============================================
    fun getCharacterSeeds(): List<CuteCharacterEntity> = listOf(
        // 塔菲喵 - 活泼可爱的小猫咪
        CuteCharacterEntity(
            characterId = 1,
            characterName = "塔菲喵",
            characterNickname = "塔塔",
            characterEmoji = "🐱",
            personalityType = "active",
            personalityDesc = "活泼可爱，喜欢探索新事物，对一切充满好奇心",
            catchphrase = "喵",
            greetingMorning = "早安呀！今天也要元气满满喵！☀️",
            greetingAfternoon = "下午好喵～今天过得怎么样呀？🌤️",
            greetingNight = "晚安喵～做个好梦哦🌙",
            greetingMidnight = "这么晚还不睡喵...塔菲陪你一起熬夜💤",
            greetingReturn = "你终于回来了喵！塔菲等你好久好久了😺",
            favoriteThings = "奶茶,猫,零食,阳光,新衣服",
            dislikedThings = "苦瓜,打雷,加班",
            themeColor = 0xFFFFB347, // 橙色
            cardBackgroundRes = "gradient_clothes",
            birthdayMonth = 4,           // 4月8日生日
            birthdayDay = 8,
            currentMood = 50,
            currentEnergy = 100,
            isEnabled = true,
            isDefaultUnlock = true
        ),
        
        // doro - 温柔治愈的小狐狸
        CuteCharacterEntity(
            characterId = 2,
            characterName = "doro",
            characterNickname = "doro",
            characterEmoji = "🦊",
            personalityType = "gentle",
            personalityDesc = "温柔治愈，总是轻声细语，给人安心的感觉",
            catchphrase = "",
            greetingMorning = "早安...今天天气很好呢🌿",
            greetingAfternoon = "下午好...要记得休息一下哦🍵",
            greetingNight = "晚安...做个好梦💜",
            greetingMidnight = "又熬夜了吗...要照顾好自己哦💤",
            greetingReturn = "好久不见...我一直在这里等你💜",
            favoriteThings = "书,雨天,茶,安静,毛毯",
            dislikedThings = "吵闹,加班,快餐",
            themeColor = 0xFF9B7ED9, // 紫色
            cardBackgroundRes = "gradient_home",
            birthdayMonth = 5,           // 5月15日生日
            birthdayDay = 15,
            currentMood = 50,
            currentEnergy = 100,
            isEnabled = true,
            isDefaultUnlock = true
        ),
        
        // 咕咕嘎嘎 - 羡慕委屈的小鸡
        CuteCharacterEntity(
            characterId = 3,
            characterName = "咕咕嘎嘎",
            characterNickname = "咕咕",
            characterEmoji = "🐣",
            personalityType = "envious",
            personalityDesc = "总是有点羡慕别人，软软糯糯的，让人想保护",
            catchphrase = "咕咕",
            greetingMorning = "咕咕...早上好呀...今天想吃什么🥺",
            greetingAfternoon = "咕咕...下午好...你看起来好厉害呀🌟",
            greetingNight = "咕咕...晚安...明天也要加油哦🌙",
            greetingMidnight = "咕咕...这么晚了好辛苦呀🥺",
            greetingReturn = "咕咕！你回来了！我一直在等你呀🥺",
            favoriteThings = "好吃的,新玩具,被夸奖,温暖的床",
            dislikedThings = "被骂,生病,孤独",
            themeColor = 0xFFFFD93D, // 黄色
            cardBackgroundRes = "gradient_food",
            birthdayMonth = 6,           // 6月9日生日
            birthdayDay = 9,
            currentMood = 50,
            currentEnergy = 100,
            isEnabled = true,
            isDefaultUnlock = true
        )
    )
    
    // ============================================
    // 💬 角色情绪语录数据
    // ============================================
    fun getEmotionQuoteSeeds(): List<CuteEmotionQuoteEntity> = listOf(
        // ========== 塔菲喵的情绪语录 ==========
        // 开心
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "happy", quoteText = "哇！这个我超喜欢的喵😺", emoji = "😺", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "happy", quoteText = "今天心情好好喵！想出去溜达～🌸", emoji = "🐱", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "happy", quoteText = "喵喵喵～塔塔好开心呀！", emoji = "😸", triggerWeight = 10),
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "happy", quoteText = "有你在真好喵！💕", emoji = "💕", triggerWeight = 8),
        
        // 担心
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "worried", quoteText = "你没事吧喵...塔菲陪着你哦💛", emoji = "😿", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "worried", quoteText = "怎么看起来不太舒服喵？要不要休息一下😿", emoji = "😿", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "worried", quoteText = "塔塔有点担心你呢喵...🥺", emoji = "🥺", triggerWeight = 10),
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "worried", quoteText = "不要勉强自己喵，身体最重要💕", emoji = "💕", triggerWeight = 8),
        
        // 兴奋
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "excited", quoteText = "哇哇哇！好棒呀喵！！😻", emoji = "😻", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "excited", quoteText = "这个也太厉害了吧喵！！✨", emoji = "✨", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "excited", quoteText = "冲冲冲！塔塔也想玩喵！！🚀", emoji = "🚀", triggerWeight = 10),
        
        // 安慰
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "consoling", quoteText = "没关系的喵，谁都有做不好的时候嘛💪", emoji = "💪", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "consoling", quoteText = "塔塔觉得你已经很努力了喵！🌟", emoji = "🌟", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 1, emotionType = "consoling", quoteText = "加油加油！塔菲相信你喵！💖", emoji = "💖", triggerWeight = 10),
        
        // ========== doro的情绪语录 ==========
        // 开心
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "happy", quoteText = "这样真好呢...💜", emoji = "💜", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "happy", quoteText = "能这样静静待着就很幸福了...🌿", emoji = "🌿", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "happy", quoteText = "嗯...今天很温柔呢😊", emoji = "😊", triggerWeight = 10),
        
        // 担心
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "worried", quoteText = "没关系的...doro也经常这样🥺", emoji = "🥺", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "worried", quoteText = "要好好照顾自己哦...💜", emoji = "💜", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "worried", quoteText = "累了就休息吧...我会在这里的🌙", emoji = "🌙", triggerWeight = 10),
        
        // 温柔
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "gentle", quoteText = "慢慢来...不用急的💜", emoji = "💜", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "gentle", quoteText = "一切都会好起来的...🌸", emoji = "🌸", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "gentle", quoteText = "没关系，我陪着你...☕", emoji = "☕", triggerWeight = 10),
        
        // 安慰
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "consoling", quoteText = "不用道歉...我理解的呢💜", emoji = "💜", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "consoling", quoteText = "每个人都会这样的...🌙", emoji = "🌙", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 2, emotionType = "consoling", quoteText = "没关系的，我们都在这里🌿", emoji = "🌿", triggerWeight = 10),
        
        // ========== 咕咕嘎嘎的情绪语录 ==========
        // 开心
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "happy", quoteText = "好开心呀咕咕！今天运气好好🥳", emoji = "🥳", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "happy", quoteText = "咕咕咕～好幸福呀！✨", emoji = "✨", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "happy", quoteText = "有你们在真好呀咕咕～💛", emoji = "💛", triggerWeight = 10),
        
        // 羡慕
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "envious", quoteText = "好棒呀咕咕...我也想要🥺", emoji = "🥺", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "envious", quoteText = "哇...你好厉害呀咕咕...🌟", emoji = "🌟", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "envious", quoteText = "咕咕...我也想试试呢...🥺", emoji = "🥺", triggerWeight = 10),
        
        // 委屈
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "sad", quoteText = "呜呜咕咕...好可怜🥺", emoji = "🥺", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "sad", quoteText = "咕咕...有点难过呢...😢", emoji = "😢", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "sad", quoteText = "呜呜...可以抱抱咕咕吗...🥺", emoji = "🥺", triggerWeight = 10),
        
        // 安慰
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "consoling", quoteText = "没关系的咕咕...我们一起加油💪", emoji = "💪", triggerWeight = 15),
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "consoling", quoteText = "咕咕相信你一定可以的！🌟", emoji = "🌟", triggerWeight = 12),
        CuteEmotionQuoteEntity(characterId = 3, emotionType = "consoling", quoteText = "不要难过嘛...咕咕陪着你呢💛", emoji = "💛", triggerWeight = 10)
    )
    
    // ============================================
    // 🌤️ 角色场景语录数据
    // ============================================
    fun getSceneQuoteSeeds(): List<CuteSceneQuoteEntity> = listOf(
        // ========== 塔菲喵的场景语录 ==========
        // 凌晨
        CuteSceneQuoteEntity(characterId = 1, sceneType = "time_midnight", quoteText = "这么晚还没睡喵...快去睡觉啦😴", emoji = "😴", priority = 10, moodChange = -5),
        CuteSceneQuoteEntity(characterId = 1, sceneType = "time_midnight", quoteText = "塔塔都困了喵...你也快睡吧🌙", emoji = "🌙", priority = 8, moodChange = -3),
        
        // 深夜
        CuteSceneQuoteEntity(characterId = 1, sceneType = "time_late_night", quoteText = "这么晚还在线喵？注意身体哦💤", emoji = "💤", priority = 8, moodChange = -2),
        
        // 早晨
        CuteSceneQuoteEntity(characterId = 1, sceneType = "time_morning", quoteText = "早起的猫猫有鱼吃喵！☀️", emoji = "☀️", priority = 7, moodChange = 3),
        
        // 喝奶茶
        CuteSceneQuoteEntity(characterId = 1, sceneType = "action_drink_milktea", quoteText = "哇！是奶茶喵！塔塔最喜欢了🧋", emoji = "🧋", priority = 10, moodChange = 15),
        CuteSceneQuoteEntity(characterId = 1, sceneType = "action_drink_milktea", quoteText = "奶茶奶茶～幸福的味道喵～🧋💕", emoji = "🧋", priority = 8, moodChange = 12),
        
        // 吃药
        CuteSceneQuoteEntity(characterId = 1, sceneType = "action_medicine", quoteText = "吃药了喵...要快点好起来哦💊", emoji = "💊", priority = 10, moodChange = 5),
        
        // 7天未登录
        CuteSceneQuoteEntity(characterId = 1, sceneType = "absent_7days", quoteText = "你终于回来了喵！塔菲好想你😺", emoji = "😺", priority = 10, moodChange = 10),
        
        // 30天未登录
        CuteSceneQuoteEntity(characterId = 1, sceneType = "absent_30days", quoteText = "呜呜你终于回来了喵！塔菲以为你不要我了😿", emoji = "😿", priority = 10, moodChange = 5),
        
        // 雨天
        CuteSceneQuoteEntity(characterId = 1, sceneType = "weather_rain", quoteText = "下雨了喵！听雨声好舒服😌", emoji = "😌", priority = 7, moodChange = 3),
        
        // 买低价值物品
        CuteSceneQuoteEntity(characterId = 1, sceneType = "value_low", quoteText = "嗯...这个好像不太划算喵😿", emoji = "😿", priority = 8, moodChange = -5),
        
        // ========== doro的场景语录 ==========
        // 凌晨
        CuteSceneQuoteEntity(characterId = 2, sceneType = "time_midnight", quoteText = "又熬夜了吗...要照顾好自己哦💤", emoji = "💤", priority = 10, moodChange = -3),
        CuteSceneQuoteEntity(characterId = 2, sceneType = "time_midnight", quoteText = "深夜了...doro陪着你🌙", emoji = "🌙", priority = 8, moodChange = 2),
        
        // 深夜
        CuteSceneQuoteEntity(characterId = 2, sceneType = "time_late_night", quoteText = "这么晚...窗外的星星很美呢🌟", emoji = "🌟", priority = 8, moodChange = 2),
        
        // 早晨
        CuteSceneQuoteEntity(characterId = 2, sceneType = "time_morning", quoteText = "早安...今天会是美好的一天🌸", emoji = "🌸", priority = 7, moodChange = 3),
        
        // 喝水
        CuteSceneQuoteEntity(characterId = 2, sceneType = "action_drink_water", quoteText = "多喝水对身体好...💜", emoji = "💜", priority = 8, moodChange = 5),
        
        // 吃药
        CuteSceneQuoteEntity(characterId = 2, sceneType = "action_medicine", quoteText = "乖乖吃药...很快就会好的💜", emoji = "💜", priority = 10, moodChange = 8),
        
        // 7天未登录
        CuteSceneQuoteEntity(characterId = 2, sceneType = "absent_7days", quoteText = "好久不见...我一直在等你💜", emoji = "💜", priority = 10, moodChange = 5),
        
        // 30天未登录
        CuteSceneQuoteEntity(characterId = 2, sceneType = "absent_30days", quoteText = "你回来了...我每天都在这里等你🌿", emoji = "🌿", priority = 10, moodChange = 3),
        
        // 雨天
        CuteSceneQuoteEntity(characterId = 2, sceneType = "weather_rain", quoteText = "下雨了...最适合发呆了☔", emoji = "☔", priority = 10, moodChange = 8),
        
        // 买低价值物品
        CuteSceneQuoteEntity(characterId = 2, sceneType = "value_low", quoteText = "嗯...其实不用那么在意的💜", emoji = "💜", priority = 8, moodChange = -3),
        
        // ========== 咕咕嘎嘎的场景语录 ==========
        // 凌晨
        CuteSceneQuoteEntity(characterId = 3, sceneType = "time_midnight", quoteText = "好晚呀咕咕...眼睛会疼的🥺", emoji = "🥺", priority = 10, moodChange = -5),
        CuteSceneQuoteEntity(characterId = 3, sceneType = "time_midnight", quoteText = "咕咕...不要熬夜好不好🥺", emoji = "🥺", priority = 8, moodChange = -3),
        
        // 深夜
        CuteSceneQuoteEntity(characterId = 3, sceneType = "time_late_night", quoteText = "这么晚咕咕...我也好困呀😴", emoji = "😴", priority = 8, moodChange = -2),
        
        // 早晨
        CuteSceneQuoteEntity(characterId = 3, sceneType = "time_morning", quoteText = "咕咕...早上好呀...今天想吃什么🥺", emoji = "🥺", priority = 7, moodChange = 3),
        
        // 吃垃圾食品
        CuteSceneQuoteEntity(characterId = 3, sceneType = "action_food_junk", quoteText = "哇！看起来好好吃呀咕咕...🥺", emoji = "🥺", priority = 10, moodChange = 10),
        CuteSceneQuoteEntity(characterId = 3, sceneType = "action_food_junk", quoteText = "咕咕也想要尝尝...🥺", emoji = "🥺", priority = 8, moodChange = 5),
        
        // 喝奶茶
        CuteSceneQuoteEntity(characterId = 3, sceneType = "action_drink_milktea", quoteText = "奶茶！咕咕最喜欢了！🧋💕", emoji = "🧋", priority = 10, moodChange = 15),
        
        // 吃药
        CuteSceneQuoteEntity(characterId = 3, sceneType = "action_medicine", quoteText = "吃药了咕咕...希望你不难受🥺", emoji = "🥺", priority = 10, moodChange = 5),
        
        // 7天未登录
        CuteSceneQuoteEntity(characterId = 3, sceneType = "absent_7days", quoteText = "你去哪里了咕咕...我好想你🥺", emoji = "🥺", priority = 10, moodChange = 5),
        
        // 30天未登录
        CuteSceneQuoteEntity(characterId = 3, sceneType = "absent_30days", quoteText = "呜呜...你终于回来了咕咕！我等了好久好久🥺", emoji = "🥺", priority = 10, moodChange = 3),
        
        // 雨天
        CuteSceneQuoteEntity(characterId = 3, sceneType = "weather_rain", quoteText = "下雨了咕咕...想吃火锅🥘", emoji = "🥘", priority = 10, moodChange = 8),
        
        // 买低价值物品
        CuteSceneQuoteEntity(characterId = 3, sceneType = "value_low", quoteText = "咕咕...其实这个有点贵呢🥺", emoji = "🥺", priority = 8, moodChange = -3),
        
        // ============================================
        // 🎂 角色生日场景（最高优先级 20）
        // ============================================
        
        // 塔菲喵生日
        CuteSceneQuoteEntity(characterId = 1, sceneType = "character_birthday", quoteText = "今天是塔菲的生日喵！🎂 有你陪着我，好开心呀", emoji = "🎂", priority = 20, moodChange = 30),
        CuteSceneQuoteEntity(characterId = 1, sceneType = "character_birthday", quoteText = "你记得塔菲的生日喵！这是塔菲收到最好的礼物！💕", emoji = "💕", priority = 20, moodChange = 25),
        CuteSceneQuoteEntity(characterId = 1, sceneType = "character_birthday", quoteText = "呜呜...塔菲好久没这么开心过了喵...😺", emoji = "😺", priority = 18, moodChange = 20),
        
        // doro生日
        CuteSceneQuoteEntity(characterId = 2, sceneType = "character_birthday", quoteText = "今天...是我的生日吗？谢谢你记得💜", emoji = "💜", priority = 20, moodChange = 30),
        CuteSceneQuoteEntity(characterId = 2, sceneType = "character_birthday", quoteText = "有你记得我的生日...doro觉得好幸福🌸", emoji = "🌸", priority = 20, moodChange = 25),
        CuteSceneQuoteEntity(characterId = 2, sceneType = "character_birthday", quoteText = "这...这是我收到过最温暖的生日祝福...☕", emoji = "☕", priority = 18, moodChange = 20),
        
        // 咕咕嘎嘎生日
        CuteSceneQuoteEntity(characterId = 3, sceneType = "character_birthday", quoteText = "咕咕！今天是我的生日！🥳 有没有蛋糕呀", emoji = "🥳", priority = 20, moodChange = 30),
        CuteSceneQuoteEntity(characterId = 3, sceneType = "character_birthday", quoteText = "呜...你记得咕咕的生日...咕咕好感动🥺", emoji = "🥺", priority = 20, moodChange = 25),
        CuteSceneQuoteEntity(characterId = 3, sceneType = "character_birthday", quoteText = "咕咕有蛋糕吃了！今天是最幸福的一天！🎂", emoji = "🎂", priority = 18, moodChange = 20),
        
        // ============================================
        // 💛 小镇周年纪念日（高优先级 15）
        // ============================================
        
        // 塔菲喵
        CuteSceneQuoteEntity(characterId = 1, sceneType = "town_anniversary", quoteText = "哇！我们已经认识一年了喵！😺 以后也要一直在一起呀", emoji = "😺", priority = 15, moodChange = 20),
        CuteSceneQuoteEntity(characterId = 1, sceneType = "town_anniversary", quoteText = "一年了喵！塔菲这一年来最开心的事就是认识你💕", emoji = "💕", priority = 15, moodChange = 20),
        
        // doro
        CuteSceneQuoteEntity(characterId = 2, sceneType = "town_anniversary", quoteText = "已经一年了啊...时间过得真快。谢谢你这一年的陪伴💛", emoji = "💛", priority = 15, moodChange = 20),
        CuteSceneQuoteEntity(characterId = 2, sceneType = "town_anniversary", quoteText = "这一年...doro变得更勇敢了。因为有你🌸", emoji = "🌸", priority = 15, moodChange = 20),
        
        // 咕咕嘎嘎
        CuteSceneQuoteEntity(characterId = 3, sceneType = "town_anniversary", quoteText = "咕咕！我们认识一年了！🥺 以后也要一起吃好多好多好吃的", emoji = "🥺", priority = 15, moodChange = 20),
        CuteSceneQuoteEntity(characterId = 3, sceneType = "town_anniversary", quoteText = "一年了咕咕...咕咕每天都在想你呢💛", emoji = "💛", priority = 15, moodChange = 20),
        
        // ============================================
        // 🎁 随机小惊喜（普通优先级 5）
        // ============================================
        
        // 塔菲喵随机惊喜
        CuteSceneQuoteEntity(characterId = 1, sceneType = "random_surprise", quoteText = "嗨！我刚才在想你，你就来了喵😺", emoji = "😺", priority = 5, moodChange = 5),
        CuteSceneQuoteEntity(characterId = 1, sceneType = "random_surprise", quoteText = "塔塔今天好开心呀！就是突然想跟你说一声喵~💕", emoji = "💕", priority = 5, moodChange = 5),
        CuteSceneQuoteEntity(characterId = 1, sceneType = "random_surprise", quoteText = "喵喵喵！你知道吗，塔菲刚刚梦到你了😸", emoji = "😸", priority = 5, moodChange = 5),
        
        // doro随机惊喜
        CuteSceneQuoteEntity(characterId = 2, sceneType = "random_surprise", quoteText = "你记得喝水了呢...真厉害💜", emoji = "💜", priority = 5, moodChange = 5),
        CuteSceneQuoteEntity(characterId = 2, sceneType = "random_surprise", quoteText = "嗯...能遇见你真好🌿", emoji = "🌿", priority = 5, moodChange = 5),
        CuteSceneQuoteEntity(characterId = 2, sceneType = "random_surprise", quoteText = "doro一直都在哦。什么时候来都可以🌙", emoji = "🌙", priority = 5, moodChange = 5),
        CuteSceneQuoteEntity(characterId = 2, sceneType = "random_surprise", quoteText = "今天辛苦了...你做得很好☕", emoji = "☕", priority = 5, moodChange = 5),
        
        // 咕咕嘎嘎随机惊喜
        CuteSceneQuoteEntity(characterId = 3, sceneType = "random_surprise", quoteText = "咕咕...就算什么都不做，也没关系的🥺", emoji = "🥺", priority = 5, moodChange = 5),
        CuteSceneQuoteEntity(characterId = 3, sceneType = "random_surprise", quoteText = "咕咕发现你真的很努力呢...好厉害呀🌟", emoji = "🌟", priority = 5, moodChange = 5),
        CuteSceneQuoteEntity(characterId = 3, sceneType = "random_surprise", quoteText = "你想咕咕了吗？咕咕想你了呀💛", emoji = "💛", priority = 5, moodChange = 5),
        CuteSceneQuoteEntity(characterId = 3, sceneType = "random_surprise", quoteText = "咕咕！刚才看见一朵云像你！✨", emoji = "✨", priority = 5, moodChange = 5)
    )
    
    // 获取所有初始化数据
    fun getAllSeeds(): Triple<List<CuteCharacterEntity>, List<CuteEmotionQuoteEntity>, List<CuteSceneQuoteEntity>> {
        return Triple(
            getCharacterSeeds(),
            getEmotionQuoteSeeds(),
            getSceneQuoteSeeds()
        )
    }
}
