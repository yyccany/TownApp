package com.example.townapp.data.randomtask

/**
 * 100件微小日常小事及其三角色软萌对话
 *
 * 每一件小事，不管多小，都有人看到，都有人陪着你。
 */

// ============================================
// 角色对话数据类
// ============================================

/**
 * 三个角色的软萌对话
 */
data class CompanionQuotes(
    val taffi: String,   // 塔菲喵 - 活泼可爱
    val gugaga: String,  // 咕咕嘎嘎 - 羡慕委屈
    val doro: String     // doro - 温柔治愈
)

/**
 * 带对话的小事
 */
data class DailyCompanionTask(
    val id: String,
    val content: String,      // 事情内容
    val emoji: String,        // 配套emoji
    val category: TaskCategory,
    val quotes: CompanionQuotes  // 三角色对话
)

// ============================================
// 100件小事语录库
// ============================================

object DailyCompanionLibrary {

    fun getAllTasks(): List<DailyCompanionTask> = listOf(

        // =============================================
        // 第一类：饮食篇（1-20）
        // =============================================

        DailyCompanionTask(
            id = "food_01",
            content = "喝了一杯冰奶茶",
            emoji = "🧋",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "哇！冰奶茶！夏天喝这个最爽了对不对喵😺",
                gugaga = "冰奶茶！我也想喝！咕咕���",
                doro = "冰凉的奶茶...光想想就很幸福呢💛"
            )
        ),
        DailyCompanionTask(
            id = "food_02",
            content = "吃了一块很甜的西瓜",
            emoji = "🍉",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "甜甜的西瓜！夏天最棒的味道喵🍉",
                gugaga = "西瓜汁水好多！咕咕看得口水都要流出来了🥺",
                doro = "甜甜的...好幸福🥰"
            )
        ),
        DailyCompanionTask(
            id = "food_03",
            content = "给自己煮了一碗热汤面",
            emoji = "🍜",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "热汤面！暖暖的好舒服喵🍜",
                gugaga = "汤面咕嘟咕嘟的...好香啊咕咕🤤",
                doro = "热乎乎的一碗...胃和心都暖了呢💛"
            )
        ),
        DailyCompanionTask(
            id = "food_04",
            content = "吃到了一直想吃的零食",
            emoji = "🍫",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "终于吃到了！开心喵😋",
                gugaga = "我也想吃！能分我一口吗咕咕🥺",
                doro = "想吃的东西终于吃到了...小小的幸福呢✨"
            )
        ),
        DailyCompanionTask(
            id = "food_05",
            content = "喝了一杯温水",
            emoji = "🥤",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "喝温水对身体好喵！棒棒👍",
                gugaga = "温水咕咕...虽然有点无聊但是很乖哦💛",
                doro = "温水...很舒服呢☺️"
            )
        ),
        DailyCompanionTask(
            id = "food_06",
            content = "吃了一个苹果",
            emoji = "🍎",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "苹果！一天一个刚刚好喵🍎",
                gugaga = "红红的苹果！好漂亮咕咕✨",
                doro = "清脆的苹果...声音听着就很治愈呢💚"
            )
        ),
        DailyCompanionTask(
            id = "food_07",
            content = "泡了一杯热咖啡",
            emoji = "☕",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "咖啡香喷喷！精神一整天喵☕",
                gugaga = "咖啡味好香...咕咕可以闻一下吗🤤",
                doro = "咖啡的香气...好温暖呢🌙"
            )
        ),
        DailyCompanionTask(
            id = "food_08",
            content = "吃了一口冰淇淋",
            emoji = "🍦",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "冰淇淋！甜甜冰冰的好幸福喵😋",
                gugaga = "冰淇淋！好凉快！咕咕也想舔一口🥺",
                doro = "入口即化的甜蜜...好温柔呢💜"
            )
        ),
        DailyCompanionTask(
            id = "food_09",
            content = "喝了一碗热粥",
            emoji = "🥣",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "热粥暖暖的！胃舒服喵🥣",
                gugaga = "粥咕嘟咕嘟的...好香啊咕咕✨",
                doro = "热乎乎的粥...很温柔的味道呢🍵"
            )
        ),
        DailyCompanionTask(
            id = "food_10",
            content = "吃了一块巧克力",
            emoji = "🍫",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "巧克力！快乐源泉喵😋",
                gugaga = "巧克力！甜甜的咕咕好羡慕🤤",
                doro = "巧克力的苦甜...刚刚好的味道💛"
            )
        ),
        DailyCompanionTask(
            id = "food_11",
            content = "吃了一把坚果",
            emoji = "🥜",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "坚果！健康又好吃喵🥜",
                gugaga = "脆脆的！好香啊咕咕🌰",
                doro = "嚼着坚果的声音...好治愈呢🌿"
            )
        ),
        DailyCompanionTask(
            id = "food_12",
            content = "喝了一杯蜂蜜水",
            emoji = "🍯",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "蜂蜜水！甜甜的很养胃喵🍯",
                gugaga = "蜂蜜水！好甜好甜咕咕🐝",
                doro = "甜甜的蜂蜜水...好温柔呢🌼"
            )
        ),
        DailyCompanionTask(
            id = "food_13",
            content = "吃了一个煮鸡蛋",
            emoji = "🥚",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "煮鸡蛋！简单又营养喵💪",
                gugaga = "蛋黄软软的！好想吃咕咕🥺",
                doro = "一颗简单的煮蛋...也可以很满足呢🥰"
            )
        ),
        DailyCompanionTask(
            id = "food_14",
            content = "吃了一份酸奶水果捞",
            emoji = "🍓",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "水果加酸奶！酸酸甜甜喵🍓",
                gugaga = "五颜六色的水果！好漂亮咕咕🌈",
                doro = "新鲜的水果和酸奶...好清爽呢🍃"
            )
        ),
        DailyCompanionTask(
            id = "food_15",
            content = "喝了一杯鲜榨果汁",
            emoji = "🍊",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "鲜榨果汁！满满维生素喵🍊",
                gugaga = "果汁咕嘟咕嘟...好新鲜咕咕🍹",
                doro = "一杯鲜榨的果汁...好幸福呢🍊"
            )
        ),
        DailyCompanionTask(
            id = "food_16",
            content = "吃了一块蛋糕",
            emoji = "🍰",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "蛋糕！今天也要甜甜的喵🍰",
                gugaga = "奶油好想吃！咕咕眼睛都亮了✨",
                doro = "一小块蛋糕...就是小小的仪式感呢🎂"
            )
        ),
        DailyCompanionTask(
            id = "food_17",
            content = "喝了一碗热豆浆",
            emoji = "🥛",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "热豆浆！香喷喷喵🥛",
                gugaga = "豆浆好香！咕咕也想喝一碗🌾",
                doro = "暖暖的豆浆...很温柔的味道呢🌱"
            )
        ),
        DailyCompanionTask(
            id = "food_18",
            content = "吃了一把葡萄",
            emoji = "🍇",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "葡萄！甜甜的爆珠感喵🍇",
                gugaga = "紫紫的葡萄！好漂亮咕咕✨",
                doro = "一颗颗葡萄...慢慢吃很享受呢🍇"
            )
        ),
        DailyCompanionTask(
            id = "food_19",
            content = "吃了一个包子",
            emoji = "🥟",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "包子！热腾腾的好香喵🥟",
                gugaga = "包子鼓鼓的！馅料一定很足咕咕🤤",
                doro = "软软热热的包子...很踏实呢🥟"
            )
        ),
        DailyCompanionTask(
            id = "food_20",
            content = "喝了一杯热可可",
            emoji = "🍫",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "热可可！冬天里的小太阳喵☀️",
                gugaga = "可可好香！咕咕也想暖暖手🧸",
                doro = "甜甜的热可可...好温暖呢🌟"
            )
        ),

        // =============================================
        // 第二类：休息篇（21-40）
        // =============================================

        DailyCompanionTask(
            id = "rest_01",
            content = "躺在床上发了5分钟的呆",
            emoji = "🫠",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "发呆喵！什么都不用想哦😺",
                gugaga = "发呆也很好咕咕...什么都不用做🥺",
                doro = "发呆也没关系的...doro也经常发呆🥺"
            )
        ),
        DailyCompanionTask(
            id = "rest_02",
            content = "换上了干净的床单",
            emoji = "🛏️",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "干净的床单！晚上一定睡得香喵😴",
                gugaga = "床单香香的！好羡慕咕咕🛏️",
                doro = "干净的床单...把自己裹起来吧💜"
            )
        ),
        DailyCompanionTask(
            id = "rest_03",
            content = "美美地睡了一觉",
            emoji = "😴",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "睡饱饱！精神恢复满分喵💯",
                gugaga = "睡觉觉是最幸福的事咕咕😴",
                doro = "好好休息了...你做得很好哦💚"
            )
        ),
        DailyCompanionTask(
            id = "rest_04",
            content = "小憩了20分钟",
            emoji = "💤",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "午睡喵！充个电继续努力💪",
                gugaga = "眯一下好舒服咕咕...😴",
                doro = "短短的休息...也很重要呢🌿"
            )
        ),
        DailyCompanionTask(
            id = "rest_05",
            content = "裹着被子赖了一会儿床",
            emoji = "🛌",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "被子里好暖和喵...再赖一会儿😪",
                gugaga = "赖床最舒服了咕咕...🥺",
                doro = "被子的温度...刚刚好呢🌙"
            )
        ),
        DailyCompanionTask(
            id = "rest_06",
            content = "躺在沙发上看了一会儿天花板",
            emoji = "🏠",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "天花板好白喵...放空一下😌",
                gugaga = "什么都不用想咕咕...😴",
                doro = "有时候什么都不是...也很好呢☁️"
            )
        ),
        DailyCompanionTask(
            id = "rest_07",
            content = "听了一会儿雨声",
            emoji = "🌧️",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "雨声滴滴答答好助眠喵🌧️",
                gugaga = "雨声好温柔咕咕...☔",
                doro = "哗啦啦的雨声...好平静呢🌧️"
            )
        ),
        DailyCompanionTask(
            id = "rest_08",
            content = "躺在草地上看天空",
            emoji = "🌿",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "蓝天白云！好舒服喵☁️",
                gugaga = "草地软软的！好想打滚咕咕🌿",
                doro = "云朵慢慢飘...时间也慢下来了呢🌸"
            )
        ),
        DailyCompanionTask(
            id = "rest_09",
            content = "在摇椅上摇了一会儿",
            emoji = "🪑",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "摇啊摇~好悠闲喵～🪑",
                gugaga = "摇椅好舒服咕咕～🌿",
                doro = "轻轻晃着...好平静呢～🍃"
            )
        ),
        DailyCompanionTask(
            id = "rest_10",
            content = "趴在桌子上休息了一会儿",
            emoji = "😴",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "趴一趴！小猫咪也要休息喵😴",
                gugaga = "桌面凉凉的好舒服咕咕🛋️",
                doro = "短暂的放空...也很好呢💜"
            )
        ),
        DailyCompanionTask(
            id = "rest_11",
            content = "靠在窗边晒了一会儿太阳",
            emoji = "☀️",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "太阳暖洋洋！好幸福喵☀️",
                gugaga = "阳光好暖和咕咕...🌞",
                doro = "阳光洒在身上...好温柔呢🌻"
            )
        ),
        DailyCompanionTask(
            id = "rest_12",
            content = "给自己泡了个热水脚",
            emoji = "🦶",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "热水脚！促进血液循环喵🦶",
                gugaga = "脚脚热乎乎的！好舒服咕咕✨",
                doro = "泡脚真的很舒服...要好好爱自己呢💜"
            )
        ),
        DailyCompanionTask(
            id = "rest_13",
            content = "敷了一张面膜",
            emoji = "🥒",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "面膜冰冰的！皮肤喝饱水喵🥒",
                gugaga = "敷面膜咕咕...好精致哦✨",
                doro = "给自己一点呵护...你值得哦💚"
            )
        ),
        DailyCompanionTask(
            id = "rest_14",
            content = "在浴缸里泡了个澡",
            emoji = "🛁",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "泡澡最舒服喵！全身都放松🛁",
                gugaga = "浴缸咕嘟咕嘟...好幸福啊🫧",
                doro = "泡在热水里...所有的疲惫都融化了🌸"
            )
        ),
        DailyCompanionTask(
            id = "rest_15",
            content = "闭上眼睛深呼吸了10次",
            emoji = "🌬️",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "深呼吸！氧气充满全身喵🌬️",
                gugaga = "慢慢吸气...慢慢呼气咕咕🌿",
                doro = "呼吸之间...平静就在心里🌙"
            )
        ),
        DailyCompanionTask(
            id = "rest_16",
            content = "盖着厚被子睡了一个懒觉",
            emoji = "🧸",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "厚被子好暖和喵...😴💤",
                gugaga = "睡懒觉是最幸福的事咕咕🧸",
                doro = "有时候睡眠...就是最好的治愈🌙"
            )
        ),
        DailyCompanionTask(
            id = "rest_17",
            content = "躺在黑暗中安静了一会儿",
            emoji = "🌑",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "黑暗中好安静喵...🦋",
                gugaga = "闭上眼睛...什么都不用想咕咕🌑",
                doro = "在黑暗里...和自己待一会儿🌙"
            )
        ),
        DailyCompanionTask(
            id = "rest_18",
            content = "在吊床上晃了一会儿",
            emoji = "🌴",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "吊床晃晃~好悠闲喵～🌴",
                gugaga = "晃啊晃的...好放松咕咕～🪁",
                doro = "轻轻晃动...好平静呢～🍃"
            )
        ),
        DailyCompanionTask(
            id = "rest_19",
            content = "听着白噪音工作了一会儿",
            emoji = "🔊",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "白噪音好助眠喵🔊",
                gugaga = "沙沙的声音好舒服咕咕🌧️",
                doro = "均匀的白噪音...很专注呢🎧"
            )
        ),
        DailyCompanionTask(
            id = "rest_20",
            content = "窝在角落里安静地待了一会儿",
            emoji = "🪟",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "角落好安全喵...🦔",
                gugaga = "小小的空间...好安心咕咕🏠",
                doro = "有时候独处...是最好的礼物🌙"
            )
        ),

        // =============================================
        // 第三类：宠物/自然篇（41-55）
        // =============================================

        DailyCompanionTask(
            id = "pet_01",
            content = "摸了一下路边的小猫咪",
            emoji = "🐱",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "小猫咪！软乎乎的对不对喵🐱",
                gugaga = "猫猫好可爱！它喜欢你咕咕🥰",
                doro = "小动物的生命力...好治愈呢🐱"
            )
        ),
        DailyCompanionTask(
            id = "pet_02",
            content = "和狗狗玩了一会儿",
            emoji = "🐕",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "狗狗好热情喵！它好喜欢你🐕",
                gugaga = "狗勾摇尾巴了！它很开心咕咕🦴",
                doro = "狗狗的忠诚...好温暖呢💛"
            )
        ),
        DailyCompanionTask(
            id = "pet_03",
            content = "看了一会儿小鸟",
            emoji = "🐦",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "小鸟叽叽喳喳好可爱喵🐦",
                gugaga = "小鸟在唱歌咕咕...🎵",
                doro = "小鸟的自由...好轻盈呢🕊️"
            )
        ),
        DailyCompanionTask(
            id = "pet_04",
            content = "看到了一只蝴蝶",
            emoji = "🦋",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "蝴蝶好漂亮喵！🦋",
                gugaga = "蝴蝶在跳舞咕咕...✨",
                doro = "蝴蝶的翅膀...好美呢🌸"
            )
        ),
        DailyCompanionTask(
            id = "pet_05",
            content = "看到了一朵漂亮的花",
            emoji = "🌸",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "花花好漂亮喵！🌸",
                gugaga = "这朵花好美咕咕...🌷",
                doro = "花开得很美...你也一样💐"
            )
        ),
        DailyCompanionTask(
            id = "pet_06",
            content = "在海边站了一会儿",
            emoji = "🌊",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "大海好广阔喵！🌊",
                gugaga = "海浪声好舒服咕咕...🌊",
                doro = "海风吹过...所有的烦恼都被吹走了呢🌬️"
            )
        ),
        DailyCompanionTask(
            id = "pet_07",
            content = "在树下站了一会儿",
            emoji = "🌳",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "大树好清凉喵！🌳",
                gugaga = "树叶沙沙响咕咕...🍃",
                doro = "站在树下...好平静呢🌳"
            )
        ),
        DailyCompanionTask(
            id = "pet_08",
            content = "看到了一只小松鼠",
            emoji = "🐿️",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "松鼠好可爱！尾巴毛茸茸喵🐿️",
                gugaga = "松鼠在跳来跳去咕咕...🐿️",
                doro = "小生命好有活力呢🐿️"
            )
        ),
        DailyCompanionTask(
            id = "pet_09",
            content = "看到彩虹了",
            emoji = "🌈",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "彩虹！好漂亮喵！🌈",
                gugaga = "七色彩虹咕咕！好幸运✨",
                doro = "彩虹是天空的微笑呢🌈"
            )
        ),
        DailyCompanionTask(
            id = "pet_10",
            content = "看到了一片落叶",
            emoji = "🍂",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "落叶好漂亮喵！🍂",
                gugaga = "叶子飘落咕咕...🍃",
                doro = "每一片落叶...都是季节的故事呢🍁"
            )
        ),
        DailyCompanionTask(
            id = "pet_11",
            content = "抬头看了看月亮",
            emoji = "🌙",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "月亮好圆喵！🌙",
                gugaga = "月光好温柔咕咕...🌙",
                doro = "月亮静静地看着你哦🌙"
            )
        ),
        DailyCompanionTask(
            id = "pet_12",
            content = "在草地上坐了一会儿",
            emoji = "🌱",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "草地软软的喵！🌱",
                gugaga = "小草好绿咕咕...🌿",
                doro = "和大地的连接...好踏实呢🌍"
            )
        ),
        DailyCompanionTask(
            id = "pet_13",
            content = "看到一只小蜜蜂在采蜜",
            emoji = "🐝",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "小蜜蜂好勤快喵！🐝",
                gugaga = "嗡嗡嗡~它在努力工作咕咕🌸",
                doro = "每一滴蜂蜜...都是勤劳的味道呢🍯"
            )
        ),
        DailyCompanionTask(
            id = "pet_14",
            content = "在河边待了一会儿",
            emoji = "🏞️",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "河水好清亮喵！🏞️",
                gugaga = "流水潺潺咕咕...🌊",
                doro = "水流的的声音...好平静呢💧"
            )
        ),
        DailyCompanionTask(
            id = "pet_15",
            content = "看到天边的晚霞",
            emoji = "🌅",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "晚霞好漂亮喵！🌅",
                gugaga = "天空被染成橘色咕咕...🌇",
                doro = "一天的结束...也可以这么美呢🌅"
            )
        ),

        // =============================================
        // 第四类：社交篇（56-70）
        // =============================================

        DailyCompanionTask(
            id = "social_01",
            content = "给爸妈打了个电话",
            emoji = "📞",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "给爸妈打电话喵！真孝顺📞",
                gugaga = "家人的声音...最温暖咕咕💚",
                doro = "和家人说说话...你做得很好哦📞"
            )
        ),
        DailyCompanionTask(
            id = "social_02",
            content = "和朋友聊了一会儿天",
            emoji = "💬",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "和朋友聊天！开心喵💬",
                gugaga = "朋友懂你咕咕...💜",
                doro = "有人听你说话...真好呢💬"
            )
        ),
        DailyCompanionTask(
            id = "social_03",
            content = "给喜欢的人发了一条消息",
            emoji = "💌",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "发出去了喵！好勇敢💌",
                gugaga = "消息发出去咕咕...心跳加速🌸",
                doro = "勇敢表达...就是最棒的💕"
            )
        ),
        DailyCompanionTask(
            id = "social_04",
            content = "对陌生人笑了一下",
            emoji = "😊",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "微笑好温暖喵！😊",
                gugaga = "你的笑容...会传染咕咕✨",
                doro = "一个微笑...可以是某人的小太阳哦☀️"
            )
        ),
        DailyCompanionTask(
            id = "social_05",
            content = "和邻居打了招呼",
            emoji = "👋",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "打招呼喵！邻里关系棒棒👍",
                gugaga = "小小的一声问候咕咕...🌼",
                doro = "人与人之间的连接...就这么简单呢👋"
            )
        ),
        DailyCompanionTask(
            id = "social_06",
            content = "给很久没联系的朋友发了消息",
            emoji = "📱",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "主动联系朋友喵！好贴心📱",
                gugaga = "朋友一定会很开心咕咕💫",
                doro = "想念就说出来...朋友不会介意的�.message"
            )
        ),
        DailyCompanionTask(
            id = "social_07",
            content = "抱了家人一下",
            emoji = "🤗",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "拥抱！好温暖喵🤗",
                gugaga = "家人会觉得很幸福咕咕💖",
                doro = "一个拥抱...胜过千言万语呢🤗"
            )
        ),
        DailyCompanionTask(
            id = "social_08",
            content = "和外卖小哥说了谢谢",
            emoji = "🙏",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "说谢谢喵！有礼貌👍",
                gugaga = "小哥也会开心的咕咕🌟",
                doro = "一句谢谢...是小小的善意呢🙏"
            )
        ),
        DailyCompanionTask(
            id = "social_09",
            content = "和同事聊了聊工作之外的事",
            emoji = "☕",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "聊聊天放松一下喵☕",
                gugaga = "同事也可以是朋友咕咕🤝",
                doro = "工作之外的交流...是生活的调味料呢☕"
            )
        ),
        DailyCompanionTask(
            id = "social_10",
            content = "给朋友发了一个表情包",
            emoji = "😄",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "发表情包喵！好可爱😄",
                gugaga = "朋友一定觉得很有趣咕咕😂",
                doro = "小小的表情...大大的快乐呢😄"
            )
        ),
        DailyCompanionTask(
            id = "social_11",
            content = "帮陌生人扶了一下门",
            emoji = "🚪",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "扶门喵！小小善意👍",
                gugaga = "陌生人会感谢你咕咕🌟",
                doro = "举手之劳...是城市的温度呢🚪"
            )
        ),
        DailyCompanionTask(
            id = "social_12",
            content = "和宠物说了说话",
            emoji = "🐾",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "和宠物说话喵！它听得懂🐾",
                gugaga = "宠物是很好的倾听者咕咕🐕",
                doro = "有时候...宠物比人更懂你呢🐾"
            )
        ),
        DailyCompanionTask(
            id = "social_13",
            content = "和爸妈一起吃了顿饭",
            emoji = "🍽️",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "和家人吃饭喵！最香🍽️",
                gugaga = "家的味道咕咕...最温暖🏠",
                doro = "和爱的人一起吃饭...就是幸福呢🍽️"
            )
        ),
        DailyCompanionTask(
            id = "social_14",
            content = "给远方的朋友写了封信",
            emoji = "✉️",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "写信喵！好有仪式感✉️",
                gugaga = "朋友收到会很感动咕咕💌",
                doro = "文字的温度...比消息更温暖呢✉️"
            )
        ),
        DailyCompanionTask(
            id = "social_15",
            content = "和喜欢的人多说了几句话",
            emoji = "💕",
            category = TaskCategory.SOCIAL,
            quotes = CompanionQuotes(
                taffi = "多说了几句喵！好棒💕",
                gugaga = "心跳加速咕咕...💓",
                doro = "勇敢迈出一步...就是开始呢💕"
            )
        ),

        // =============================================
        // 第五类：日常小事篇（71-100）
        // =============================================

        DailyCompanionTask(
            id = "daily_01",
            content = "穿上了最喜欢的衣服",
            emoji = "👗",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "新衣服！今天也要美美的喵👗",
                gugaga = "这件衣服好漂亮咕咕✨",
                doro = "穿自己喜欢的...就是最好的装扮呢👗"
            )
        ),
        DailyCompanionTask(
            id = "daily_02",
            content = "换了一个新发型",
            emoji = "💇",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "新发型喵！换换心情💇",
                gugaga = "新造型好酷咕咕！💫",
                doro = "改变一点...就会有新气象呢💇"
            )
        ),
        DailyCompanionTask(
            id = "daily_03",
            content = "整理了一下房间",
            emoji = "🧹",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "打扫房间喵！干干净净👍",
                gugaga = "房间整洁了咕咕...好舒服🧹",
                doro = "整洁的空间...带来好心情呢🧹"
            )
        ),
        DailyCompanionTask(
            id = "daily_04",
            content = "给自己买了一点小东西",
            emoji = "🎁",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "买买买喵！犒劳自己🎁",
                gugaga = "小礼物咕咕...好开心🛍️",
                doro = "给自己一点奖励...你值得哦🎁"
            )
        ),
        DailyCompanionTask(
            id = "daily_05",
            content = "听了一首喜欢的歌",
            emoji = "🎵",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "好听的歌喵！耳朵怀孕🎵",
                gugaga = "这首歌好喜欢咕咕🎶",
                doro = "音乐是灵魂的粮食呢🎵"
            )
        ),
        DailyCompanionTask(
            id = "daily_06",
            content = "看了一段喜欢的视频",
            emoji = "📺",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "视频好有趣喵！哈哈哈😂",
                gugaga = "这个视频笑死咕咕了🤣",
                doro = "小小的快乐...就这样简单呢📺"
            )
        ),
        DailyCompanionTask(
            id = "daily_07",
            content = "拍了一张好看的照片",
            emoji = "📷",
            category = TaskCategory.CREATIVE,
            quotes = CompanionQuotes(
                taffi = "照片好美喵！📷",
                gugaga = "拍得好好看咕咕！✨",
                doro = "记录下美好的瞬间...这就是生活呢📷"
            )
        ),
        DailyCompanionTask(
            id = "daily_08",
            content = "洗了一个香香的澡",
            emoji = "🛁",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "洗香香喵！干干净净🚿",
                gugaga = "浴室香香的咕咕...好舒服🧴",
                doro = "把自己洗干净...是自爱的开始呢🚿"
            )
        ),
        DailyCompanionTask(
            id = "daily_09",
            content = "用了一下喜欢的护肤品",
            emoji = "🧴",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "护肤品香香的喵！🧴",
                gugaga = "皮肤好滋润咕咕...✨",
                doro = "好好呵护自己...你很棒哦🧴"
            )
        ),
        DailyCompanionTask(
            id = "daily_10",
            content = "给植物浇了水",
            emoji = "🌱",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "植物喝水喵！🌱",
                gugaga = "小绿叶会谢谢你的咕咕🌿",
                doro = "照顾生命...是件美好的事呢🌱"
            )
        ),
        DailyCompanionTask(
            id = "daily_11",
            content = "开窗通风了一会儿",
            emoji = "🪟",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "新鲜空气喵！好舒服🪟",
                gugaga = "风吹进来咕咕...好凉快🌬️",
                doro = "让空气流通...房间也有呼吸呢🪟"
            )
        ),
        DailyCompanionTask(
            id = "daily_12",
            content = "叠了一下衣服",
            emoji = "👕",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "叠衣服喵！整整齐齐👕",
                gugaga = "衣服叠好咕咕...好有成就感🧺",
                doro = "小小的整理...让家更温馨呢👕"
            )
        ),
        DailyCompanionTask(
            id = "daily_13",
            content = "扔掉了一些不需要的东西",
            emoji = "🗑️",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "断舍离喵！清爽👍",
                gugaga = "丢掉不需要的咕咕...好轻松🗑️",
                doro = "放下执念...生活更轻松呢🗑️"
            )
        ),
        DailyCompanionTask(
            id = "daily_14",
            content = "看了一会儿书",
            emoji = "📚",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "看书喵！涨知识📚",
                gugaga = "沉浸在书里咕咕...📖",
                doro = "阅读是灵魂的旅行呢📚"
            )
        ),
        DailyCompanionTask(
            id = "daily_15",
            content = "写了几行字",
            emoji = "✏️",
            category = TaskCategory.CREATIVE,
            quotes = CompanionQuotes(
                taffi = "写字喵！练练手✏️",
                gugaga = "字写得好看咕咕✨",
                doro = "文字是情绪的出口呢✏️"
            )
        ),
        DailyCompanionTask(
            id = "daily_16",
            content = "画了一会儿画",
            emoji = "🎨",
            category = TaskCategory.CREATIVE,
            quotes = CompanionQuotes(
                taffi = "画画喵！发挥创意🎨",
                gugaga = "颜色好漂亮咕咕🌈",
                doro = "艺术是心灵的镜子呢🎨"
            )
        ),
        DailyCompanionTask(
            id = "daily_17",
            content = "出门走了一圈",
            emoji = "🚶",
            category = TaskCategory.MOVEMENT,
            quotes = CompanionQuotes(
                taffi = "散步喵！活动一下🚶",
                gugaga = "外面空气好新鲜咕咕🌳",
                doro = "出去走走...会发现不一样的世界呢🚶"
            )
        ),
        DailyCompanionTask(
            id = "daily_18",
            content = "伸了一个大懒腰",
            emoji = "💪",
            category = TaskCategory.MOVEMENT,
            quotes = CompanionQuotes(
                taffi = "伸懒腰喵！浑身舒展💪",
                gugaga = "筋骨都舒服了咕咕～🌿",
                doro = "身体需要这样的舒展呢～💪"
            )
        ),
        DailyCompanionTask(
            id = "daily_19",
            content = "给自己泡了一杯热茶",
            emoji = "🍵",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "热茶喵！暖暖的🍵",
                gugaga = "茶香袅袅咕咕...好香🌿",
                doro = "一杯热茶...就是片刻的宁静呢🍵"
            )
        ),
        DailyCompanionTask(
            id = "daily_20",
            content = "晒了一下太阳",
            emoji = "☀️",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "晒太阳喵！补补钙☀️",
                gugaga = "阳光暖洋洋咕咕...🌞",
                doro = "阳光是最好的维生素呢☀️"
            )
        ),
        DailyCompanionTask(
            id = "daily_21",
            content = "去便利店买了瓶水",
            emoji = "🏪",
            category = TaskCategory.MOVEMENT,
            quotes = CompanionQuotes(
                taffi = "买水喵！及时补水🏪",
                gugaga = "便利店好方便咕咕🍶",
                doro = "小小的出门...也是和世界的连接呢🏪"
            )
        ),
        DailyCompanionTask(
            id = "daily_22",
            content = "坐公交时靠在窗边看了风景",
            emoji = "🚌",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "看风景喵！好惬意🚌",
                gugaga = "窗外的景色咕咕...好美🚞",
                doro = "在移动中看世界...很浪漫呢🚌"
            )
        ),
        DailyCompanionTask(
            id = "daily_23",
            content = "在路边摊买了点小吃",
            emoji = "🍢",
            category = TaskCategory.MOVEMENT,
            quotes = CompanionQuotes(
                taffi = "小吃喵！好香🍢",
                gugaga = "路边摊好有烟火气咕咕🍡",
                doro = "人间烟火气...最抚人心呢🍢"
            )
        ),
        DailyCompanionTask(
            id = "daily_24",
            content = "给自己冲了一杯咖啡",
            emoji = "☕",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "咖啡喵！提神醒脑☕",
                gugaga = "咖啡香飘满屋咕咕☕",
                doro = "一杯咖啡的时间...是给自己的礼物呢☕"
            )
        ),
        DailyCompanionTask(
            id = "daily_25",
            content = "在日历上画了个圈",
            emoji = "📅",
            category = TaskCategory.CREATIVE,
            quotes = CompanionQuotes(
                taffi = "画圈圈喵！有仪式感📅",
                gugaga = "这天真重要咕咕✨",
                doro = "标记日子...让生活有迹可循呢📅"
            )
        ),
        DailyCompanionTask(
            id = "daily_26",
            content = "整理了一下手机相册",
            emoji = "📱",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "整理相册喵！回忆满满📱",
                gugaga = "翻到以前的照片咕咕...好怀念🌸",
                doro = "相册里的时光...都是真实的你呢📱"
            )
        ),
        DailyCompanionTask(
            id = "daily_27",
            content = "给自己冲了一杯蜂蜜水",
            emoji = "🍯",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "蜂蜜水喵！甜甜的🍯",
                gugaga = "蜂蜜化开咕咕...好香🐝",
                doro = "甜甜的问候...给自己的温暖呢🍯"
            )
        ),
        DailyCompanionTask(
            id = "daily_28",
            content = "吃了一块苏打饼干",
            emoji = "🍪",
            category = TaskCategory.CARE,
            quotes = CompanionQuotes(
                taffi = "饼干喵！脆脆的🍪",
                gugaga = "咸香酥脆咕咕...好棒🌮",
                doro = "简单的小食...也很满足呢🍪"
            )
        ),
        DailyCompanionTask(
            id = "daily_29",
            content = "听了一会儿雨声",
            emoji = "🌧️",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "雨声淅淅喵🌧️",
                gugaga = "下雨天好舒服咕咕☔",
                doro = "雨声哗哗...很安静呢🌧️"
            )
        ),
        DailyCompanionTask(
            id = "daily_30",
            content = "看了一会儿窗外",
            emoji = "🪟",
            category = TaskCategory.MINDFUL,
            quotes = CompanionQuotes(
                taffi = "看窗外喵！放空一下👀",
                gugaga = "窗外有只鸟咕咕🐦",
                doro = "偶尔放空...是很好的休息呢🪟"
            )
        )
    )

    /**
     * 随机获取一件小事
     */
    fun getRandomTask(): DailyCompanionTask {
        return getAllTasks().random()
    }

    /**
     * 按分类获取
     */
    fun getTasksByCategory(category: TaskCategory): List<DailyCompanionTask> {
        return getAllTasks().filter { it.category == category }
    }

    /**
     * 获取数量
     */
    fun getCount(): Int = getAllTasks().size
}
