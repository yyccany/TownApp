package com.example.townapp.data.microevent

/**
 * 微事件语录数据
 * 
 * 核心原则：
 * 1. 永远不惩罚：所有事件都不会降低你的任何状态
 * 2. 只有陪伴：每个事件都只有一句软乎乎的话
 * 3. 触发频率：每10-30分钟随机触发一次，绝对不频繁
 */

object MicroEventQuotes {
    
    /**
     * 获取所有微事件
     */
    fun getAllEvents(): List<MicroEvent> = 
        getBodyEvents() + 
        getHouseworkEvents() + 
        getEnvironmentEvents() + 
        getMoodEvents()
    
    // ============================================
    // 身体微事件（塔菲负责）
    // ============================================
    
    private fun getBodyEvents(): List<MicroEvent> = listOf(
        // 头发相关
        MicroEvent(
            id = "body_hair_loss_1",
            type = MicroEventType.BODY,
            triggerCondition = "掉了一根头发",
            character = TownCharacter.TAFFI,
            content = "检测到你掉了一根头发喵～没关系啦，掉一根少一根烦恼哦😺",
            isPositive = true,
            priority = 1,
            tags = listOf("身体", "头发")
        ),
        MicroEvent(
            id = "body_hair_loss_2",
            type = MicroEventType.BODY,
            triggerCondition = "掉了一根头发",
            character = TownCharacter.TAFFI,
            content = "喵～又掉了一根呀？没关系啦，压力大的时候头发会掉的，这是正常的喵😺",
            isPositive = true,
            priority = 1,
            tags = listOf("身体", "头发")
        ),
        MicroEvent(
            id = "body_hair_loss_3",
            type = MicroEventType.BODY,
            triggerCondition = "掉了一根头发",
            character = TownCharacter.TAFFI,
            content = "哎呀，又掉了一根喵...不过没关系，你已经很棒了😺",
            isPositive = true,
            priority = 1,
            tags = listOf("身体", "头发")
        ),
        
        // 打哈欠
        MicroEvent(
            id = "body_yawn_1",
            type = MicroEventType.BODY,
            triggerCondition = "打了个哈欠",
            character = TownCharacter.TAFFI,
            content = "困了对不对？趴在桌子上睡5分钟吧，我帮你看着时间喵😺",
            isPositive = true,
            priority = 2,
            tags = listOf("身体", "困倦")
        ),
        MicroEvent(
            id = "body_yawn_2",
            type = MicroEventType.BODY,
            triggerCondition = "打了个哈欠",
            character = TownCharacter.TAFFI,
            content = "哈～好困的样子喵...要不要休息一下？😺",
            isPositive = true,
            priority = 2,
            tags = listOf("身体", "困倦")
        ),
        MicroEvent(
            id = "body_yawn_3",
            type = MicroEventType.BODY,
            triggerCondition = "打了个哈欠",
            character = TownCharacter.TAFFI,
            content = "喵～眼睛都快睁不开了...休息一下吧，没关系的😺",
            isPositive = true,
            priority = 2,
            tags = listOf("身体", "困倦")
        ),
        
        // 揉眼睛
        MicroEvent(
            id = "body_eye_rub_1",
            type = MicroEventType.BODY,
            triggerCondition = "揉了揉眼睛",
            character = TownCharacter.TAFFI,
            content = "眼睛有点酸对不对？看看窗外的绿色，休息一下吧😺",
            isPositive = true,
            priority = 2,
            tags = listOf("身体", "眼睛")
        ),
        MicroEvent(
            id = "body_eye_rub_2",
            type = MicroEventType.BODY,
            triggerCondition = "揉了揉眼睛",
            character = TownCharacter.TAFFI,
            content = "喵～眼睛累了就休息一下，不要太勉强自己哦😺",
            isPositive = true,
            priority = 2,
            tags = listOf("身体", "眼睛")
        ),
        MicroEvent(
            id = "body_eye_rub_3",
            type = MicroEventType.BODY,
            triggerCondition = "揉了揉眼睛",
            character = TownCharacter.TAFFI,
            content = "看屏幕太久了喵...站起来走动走动，让眼睛休息一下吧😺",
            isPositive = true,
            priority = 2,
            tags = listOf("身体", "眼睛")
        ),
        
        // 喝水
        MicroEvent(
            id = "body_water_1",
            type = MicroEventType.BODY,
            triggerCondition = "喝了一杯水",
            character = TownCharacter.TAFFI,
            content = "多喝水真棒！今天已经喝了第3杯啦，继续加油喵😺",
            isPositive = true,
            priority = 3,
            tags = listOf("身体", "喝水")
        ),
        MicroEvent(
            id = "body_water_2",
            type = MicroEventType.BODY,
            triggerCondition = "喝了一杯水",
            character = TownCharacter.TAFFI,
            content = "喵～好棒！多喝水皮肤会变好哦😺",
            isPositive = true,
            priority = 3,
            tags = listOf("身体", "喝水")
        ),
        MicroEvent(
            id = "body_water_3",
            type = MicroEventType.BODY,
            triggerCondition = "喝了一杯水",
            character = TownCharacter.TAFFI,
            content = "咕噜咕噜～今天也要好好喝水喵😺",
            isPositive = true,
            priority = 3,
            tags = listOf("身体", "喝水")
        ),
        
        // 刷牙
        MicroEvent(
            id = "body_brush_1",
            type = MicroEventType.BODY,
            triggerCondition = "刷完牙了",
            character = TownCharacter.TAFFI,
            content = "牙齿白白的！好干净！今天也是香香的小猫咪😺",
            isPositive = true,
            priority = 3,
            tags = listOf("身体", "刷牙")
        ),
        MicroEvent(
            id = "body_brush_2",
            type = MicroEventType.BODY,
            triggerCondition = "刷完牙了",
            character = TownCharacter.TAFFI,
            content = "喵～口气清新！笑起来真好看😺",
            isPositive = true,
            priority = 3,
            tags = listOf("身体", "刷牙")
        ),
        
        // 洗澡
        MicroEvent(
            id = "body_shower_1",
            type = MicroEventType.BODY,
            triggerCondition = "洗完澡了",
            character = TownCharacter.TAFFI,
            content = "洗的香香的！好舒服对不对？裹上小被子躺平吧～😺",
            isPositive = true,
            priority = 3,
            tags = listOf("身体", "洗澡")
        ),
        MicroEvent(
            id = "body_shower_2",
            type = MicroEventType.BODY,
            triggerCondition = "洗完澡了",
            character = TownCharacter.TAFFI,
            content = "喵～热水澡最舒服了！你今天辛苦了，好好休息吧😺",
            isPositive = true,
            priority = 3,
            tags = listOf("身体", "洗澡")
        ),
        
        // 吃饭
        MicroEvent(
            id = "body_eat_1",
            type = MicroEventType.BODY,
            triggerCondition = "吃完了饭",
            character = TownCharacter.TAFFI,
            content = "吃饱饱了喵～好棒！谢谢你好好吃饭😺",
            isPositive = true,
            priority = 3,
            tags = listOf("身体", "吃饭")
        ),
        MicroEvent(
            id = "body_eat_2",
            type = MicroEventType.BODY,
            triggerCondition = "吃完了饭",
            character = TownCharacter.TAFFI,
            content = "喵～肚子饱饱的！今天也是好好吃饭的一天呢😺",
            isPositive = true,
            priority = 3,
            tags = listOf("身体", "吃饭")
        )
    )
    
    // ============================================
    // 家务微事件（咕咕嘎嘎负责）
    // ============================================
    
    private fun getHouseworkEvents(): List<MicroEvent> = listOf(
        // 垃圾桶满了
        MicroEvent(
            id = "house_trash_1",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "垃圾桶满了",
            character = TownCharacter.GUGAGA,
            content = "垃圾桶好像满了咕咕💛 今天不想倒也没关系，明天再倒也可以的",
            isPositive = true,
            priority = 1,
            tags = listOf("家务", "垃圾桶")
        ),
        MicroEvent(
            id = "house_trash_2",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "垃圾桶满了",
            character = TownCharacter.GUGAGA,
            content = "嗯...垃圾桶满啦咕咕💛 但是没关系，明天倒也不迟的",
            isPositive = true,
            priority = 1,
            tags = listOf("家务", "垃圾桶")
        ),
        
        // 桌子有灰
        MicroEvent(
            id = "house_dust_1",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "桌子上有灰",
            character = TownCharacter.GUGAGA,
            content = "桌子上有一点点灰哦～不想擦也没关系，一点点灰不影响什么的咕咕",
            isPositive = true,
            priority = 1,
            tags = listOf("家务", "灰尘")
        ),
        MicroEvent(
            id = "house_dust_2",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "桌子上有灰",
            character = TownCharacter.GUGAGA,
            content = "嗯...有一点点灰咕咕💛 但是没关系，看不见就好啦",
            isPositive = true,
            priority = 1,
            tags = listOf("家务", "灰尘")
        ),
        
        // 衣服堆了
        MicroEvent(
            id = "house_clothes_1",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "衣服堆了一堆",
            character = TownCharacter.GUGAGA,
            content = "衣服好像堆了一点啦～不想洗也没关系，攒多了一起洗就好啦咕咕",
            isPositive = true,
            priority = 1,
            tags = listOf("家务", "衣服")
        ),
        MicroEvent(
            id = "house_clothes_2",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "衣服堆了一堆",
            character = TownCharacter.GUGAGA,
            content = "咕咕～衣服堆起来了...没关系，攒够了一波洗更省事咕咕💛",
            isPositive = true,
            priority = 1,
            tags = listOf("家务", "衣服")
        ),
        
        // 倒了垃圾（积极事件）
        MicroEvent(
            id = "house_trash_done_1",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "你倒了垃圾",
            character = TownCharacter.GUGAGA,
            content = "哇！你把垃圾倒掉了！好厉害！咕咕给你点赞👍💛",
            isPositive = true,
            priority = 2,
            tags = listOf("家务", "垃圾桶", "积极")
        ),
        MicroEvent(
            id = "house_trash_done_2",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "你倒了垃圾",
            character = TownCharacter.GUGAGA,
            content = "太棒了咕咕✨ 你真的好勤快！奖励你一朵小红花🌸",
            isPositive = true,
            priority = 2,
            tags = listOf("家务", "垃圾桶", "积极")
        ),
        
        // 擦了桌子（积极事件）
        MicroEvent(
            id = "house_clean_done_1",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "你擦了桌子",
            character = TownCharacter.GUGAGA,
            content = "桌子亮晶晶的！好干净！你真的好棒咕咕✨",
            isPositive = true,
            priority = 2,
            tags = listOf("家务", "桌子", "积极")
        ),
        MicroEvent(
            id = "house_clean_done_2",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "你擦了桌子",
            character = TownCharacter.GUGAGA,
            content = "哇～桌子好干净咕咕！看着心情都会变好呢💛✨",
            isPositive = true,
            priority = 2,
            tags = listOf("家务", "桌子", "积极")
        ),
        
        // 洗了衣服（积极事件）
        MicroEvent(
            id = "house_laundry_done_1",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "你洗了衣服",
            character = TownCharacter.GUGAGA,
            content = "衣服洗好啦！香香的！好厉害咕咕👍💛",
            isPositive = true,
            priority = 2,
            tags = listOf("家务", "衣服", "积极")
        ),
        MicroEvent(
            id = "house_laundry_done_2",
            type = MicroEventType.HOUSEWORK,
            triggerCondition = "你洗了衣服",
            character = TownCharacter.GUGAGA,
            content = "咕咕✨ 脏衣服变干净啦！你真的好棒！🌸",
            isPositive = true,
            priority = 2,
            tags = listOf("家务", "衣服", "积极")
        )
    )
    
    // ============================================
    // 环境微事件（朵朵负责）
    // ============================================
    
    private fun getEnvironmentEvents(): List<MicroEvent> = listOf(
        // 晴天
        MicroEvent(
            id = "env_sunny_1",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天是晴天",
            character = TownCharacter.DORO,
            content = "今天外面阳光好好哦～要不要拉开窗帘晒晒太阳？🥺💛",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "晴天")
        ),
        MicroEvent(
            id = "env_sunny_2",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天是晴天",
            character = TownCharacter.DORO,
            content = "阳光暖暖的～好舒服呀💛 今天的天气真不错呢",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "晴天")
        ),
        MicroEvent(
            id = "env_sunny_3",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天是晴天",
            character = TownCharacter.DORO,
            content = "☀️ 今天的太阳好温柔呀～要不要出去走走？🥺",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "晴天")
        ),
        
        // 阴天
        MicroEvent(
            id = "env_cloudy_1",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天是阴天",
            character = TownCharacter.DORO,
            content = "今天外面是阴天哦～没关系，我就是你的小太阳💛",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "阴天")
        ),
        MicroEvent(
            id = "env_cloudy_2",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天是阴天",
            character = TownCharacter.DORO,
            content = "今天有点阴沉沉的...但是没关系，我会一直陪着你的💛🥺",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "阴天")
        ),
        MicroEvent(
            id = "env_cloudy_3",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天是阴天",
            character = TownCharacter.DORO,
            content = "外面灰蒙蒙的...要不要听听歌？我陪着你呀💛",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "阴天")
        ),
        
        // 下雨
        MicroEvent(
            id = "env_rainy_1",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天下雨了",
            character = TownCharacter.DORO,
            content = "外面下雨了，哗啦啦的～听着雨声睡觉最舒服啦🥺💤",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "下雨")
        ),
        MicroEvent(
            id = "env_rainy_2",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天下雨了",
            character = TownCharacter.DORO,
            content = "🌧️ 雨声好治愈呀～要不要泡杯热茶？💛",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "下雨")
        ),
        MicroEvent(
            id = "env_rainy_3",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天下雨了",
            character = TownCharacter.DORO,
            content = "下雨了呢～好浪漫呀💧 记得带伞哦～🥺",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "下雨")
        ),
        
        // 刮风
        MicroEvent(
            id = "env_windy_1",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天刮风了",
            character = TownCharacter.DORO,
            content = "外面刮风了，好冷哦～记得多穿一件衣服，不要感冒啦🥺💛",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "刮风")
        ),
        MicroEvent(
            id = "env_windy_2",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "今天刮风了",
            character = TownCharacter.DORO,
            content = "呼～风好大呀💨 别着凉了哦～🥺",
            isPositive = true,
            priority = 1,
            tags = listOf("天气", "刮风")
        ),
        
        // 天黑了
        MicroEvent(
            id = "env_night_1",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "天黑了",
            character = TownCharacter.DORO,
            content = "天黑啦～开灯吧，不要在黑暗里看手机哦，对眼睛不好🥺💛",
            isPositive = true,
            priority = 1,
            tags = listOf("时间", "夜晚")
        ),
        MicroEvent(
            id = "env_night_2",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "天黑了",
            character = TownCharacter.DORO,
            content = "🌙 夜深了...早点休息吧，我在这里陪着你💛",
            isPositive = true,
            priority = 1,
            tags = listOf("时间", "夜晚")
        ),
        MicroEvent(
            id = "env_night_3",
            type = MicroEventType.ENVIRONMENT,
            triggerCondition = "天黑了",
            character = TownCharacter.DORO,
            content = "天黑黑～早点睡觉哦，晚安💤 明天又是新的一天🥺",
            isPositive = true,
            priority = 1,
            tags = listOf("时间", "夜晚")
        )
    )
    
    // ============================================
    // 心情微事件（三个小家伙一起负责）
    // ============================================
    
    private fun getMoodEvents(): List<MicroEvent> = listOf(
        // 闲置发呆
        MicroEvent(
            id = "mood_idle_1",
            type = MicroEventType.MOOD,
            triggerCondition = "你30分钟没有任何操作",
            character = TownCharacter.TAFFI,
            content = "你在发呆对不对？没关系，发多久都可以，我陪着你喵😺",
            isPositive = true,
            priority = 1,
            tags = listOf("心情", "发呆")
        ),
        MicroEvent(
            id = "mood_idle_2",
            type = MicroEventType.MOOD,
            triggerCondition = "你30分钟没有任何操作",
            character = TownCharacter.TAFFI,
            content = "喵～你在想什么呢？不用想，不用做任何事，我就在这里😺💛",
            isPositive = true,
            priority = 1,
            tags = listOf("心情", "发呆")
        ),
        MicroEvent(
            id = "mood_idle_3",
            type = MicroEventType.MOOD,
            triggerCondition = "你30分钟没有任何操作",
            character = TownCharacter.GUGAGA,
            content = "咕咕～你在放空吗？没关系的，放空也是一种休息咕咕💛",
            isPositive = true,
            priority = 1,
            tags = listOf("心情", "发呆")
        ),
        
        // 连续打开APP
        MicroEvent(
            id = "mood_bored_1",
            type = MicroEventType.MOOD,
            triggerCondition = "你连续打开APP3次，什么都没做",
            character = TownCharacter.DORO,
            content = "是不是有点无聊？还是有点难过？没关系，我在这里陪着你💛🥺",
            isPositive = true,
            priority = 1,
            tags = listOf("心情", "陪伴")
        ),
        MicroEvent(
            id = "mood_bored_2",
            type = MicroEventType.MOOD,
            triggerCondition = "你连续打开APP3次，什么都没做",
            character = TownCharacter.DORO,
            content = "嗯...好像有点无聊的样子...要不要听首歌？我陪你💛",
            isPositive = true,
            priority = 1,
            tags = listOf("心情", "陪伴")
        ),
        MicroEvent(
            id = "mood_bored_3",
            type = MicroEventType.MOOD,
            triggerCondition = "你连续打开APP3次，什么都没做",
            character = TownCharacter.TAFFI,
            content = "喵...是不是有点不开心？没关系，你可以跟我说说喵😺",
            isPositive = true,
            priority = 1,
            tags = listOf("心情", "陪伴")
        ),
        
        // 状态都是绿色
        MicroEvent(
            id = "mood_good_1",
            type = MicroEventType.MOOD,
            triggerCondition = "你今天所有状态都是绿色",
            character = TownCharacter.GUGAGA,
            content = "今天状态好好！太棒了！今天也是开心的一天咕咕🎉💛",
            isPositive = true,
            priority = 2,
            tags = listOf("心情", "积极")
        ),
        MicroEvent(
            id = "mood_good_2",
            type = MicroEventType.MOOD,
            triggerCondition = "你今天所有状态都是绿色",
            character = TownCharacter.TAFFI,
            content = "喵～今天你好棒！一切都很好呢😺✨",
            isPositive = true,
            priority = 2,
            tags = listOf("心情", "积极")
        ),
        MicroEvent(
            id = "mood_good_3",
            type = MicroEventType.MOOD,
            triggerCondition = "你今天所有状态都是绿色",
            character = TownCharacter.DORO,
            content = "💛 今天很顺利呢～继续保持呀～🥺",
            isPositive = true,
            priority = 2,
            tags = listOf("心情", "积极")
        ),
        
        // 心情不好
        MicroEvent(
            id = "mood_sad_1",
            type = MicroEventType.MOOD,
            triggerCondition = "你今天心情不好",
            character = TownCharacter.DORO,
            content = "我知道你今天有点难过～没关系的，难过也没关系，哭出来也没关系，我会一直陪着你的💛🥺",
            isPositive = true,
            priority = 0,
            tags = listOf("心情", "难过")
        ),
        MicroEvent(
            id = "mood_sad_2",
            type = MicroEventType.MOOD,
            triggerCondition = "你今天心情不好",
            character = TownCharacter.DORO,
            content = "🥺💧 没关系...难过的时候，就难过吧。我在这里，哪儿也不去",
            isPositive = true,
            priority = 0,
            tags = listOf("心情", "难过")
        ),
        MicroEvent(
            id = "mood_sad_3",
            type = MicroEventType.MOOD,
            triggerCondition = "你今天心情不好",
            character = TownCharacter.TAFFI,
            content = "喵...今天是不是很难？我抱抱你吧🐱💛 不管怎么样，我都陪着你",
            isPositive = true,
            priority = 0,
            tags = listOf("心情", "难过")
        ),
        MicroEvent(
            id = "mood_sad_4",
            type = MicroEventType.MOOD,
            triggerCondition = "你今天心情不好",
            character = TownCharacter.GUGAGA,
            content = "咕咕在这里💛 不开心的时候，就靠着我们吧...我们会一直陪着你",
            isPositive = true,
            priority = 0,
            tags = listOf("心情", "难过")
        ),
        
        // 重置小镇
        MicroEvent(
            id = "mood_reset_1",
            type = MicroEventType.MOOD,
            triggerCondition = "你重置了小镇",
            character = TownCharacter.TAFFI,
            content = "😺🐧🥺 三个小家伙一起出来：「欢迎回来，玩家。不管怎么样，我们都会在这里等你。我们重新开始吧💛」",
            isPositive = true,
            priority = 0,
            tags = listOf("心情", "重置", "欢迎")
        ),
        MicroEvent(
            id = "mood_reset_2",
            type = MicroEventType.MOOD,
            triggerCondition = "你重置了小镇",
            character = TownCharacter.DORO,
            content = "💛 没关系，我们重新开始。不管之前发生了什么，这里永远是你的小窝",
            isPositive = true,
            priority = 0,
            tags = listOf("心情", "重置", "欢迎")
        )
    )
}
