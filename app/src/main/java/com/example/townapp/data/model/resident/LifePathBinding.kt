package com.example.townapp.data

import com.example.townapp.data.database.entity.BuildingEntity

/**
 * 人生身份绑定数据层 —— 小镇 v10 核心融合
 *
 * 每条人生路径 → 自动映射到：
 * 1. 专属房屋/场景（BuildingEntity 配置）
 * 2. 默认穿搭风格（衣物名称 + 描述 + 闪光点）
 * 3. 常备饮食清单（日常餐食）
 * 4. UserSpaceState 初始化配置
 *
 * 设计原则：
 * - 全部取材于当下中国普通人的生活实际
 * - 不美化、不贬低，只如实呈现
 * - 每件物品都有闪光点 —— 藏着被忽略的陪伴价值
 */

// ============================================
// 身份绑定的默认穿搭条目
// ============================================
data class DefaultOutfit(
    val name: String,
    val category: String,       // 上衣/裤子/鞋/配件
    val material: String,       // 材质
    val color: String,          // 颜色
    val description: String,    // 穿着描述
    val sparkle: String         // 闪光点
)

// ============================================
// 身份绑定的常备饮食
// ============================================
data class DefaultFood(
    val name: String,
    val category: String,       // 主食/家常菜/饮品/零食
    val typicalCost: Double,    // 典型花费（元）
    val description: String     // 吃后感
)

// ============================================
// 完整身份绑定配置
// ============================================
data class LifePathBinding(
    val pathId: String,
    val pathTitle: String,

    // 空间配置
    val spaceConfig: SpaceConfig,

    // 默认穿搭
    val defaultOutfits: List<DefaultOutfit>,

    // 常备饮食
    val defaultFoods: List<DefaultFood>
)

// ============================================
// 空间配置（用于初始化 UserSpaceState）
// ============================================
data class SpaceConfig(
    val addressName: String,         // 住所名称
    val areaSqm: Int,                // 面积
    val monthlyRent: Double,         // 月租
    val monthlyIncome: Double,       // 月收入
    /** 月工作总工时（v1.3 时薪体系） */
    val monthlyWorkHours: Double,
    val light: Int,                  // 采光
    val noise: Int,                  // 噪音（低=安静）
    val privacy: Int,                // 私密性
    val furnitureLevel: Int,         // 家具齐全度
    val commuteMinutesOneWay: Int,   // 单程通勤
    val workHoursPerDay: Int,        // 日工作时长
    val currentSavings: Double,      // 初始存款
    val description: String          // 空间一句话描述
)

// ============================================
// 10 条人生身份的完整绑定配置
// ============================================
object LifePathBindingData {

    val bindings: Map<String, LifePathBinding> = mapOf(

        // ========== 1. 外出打工青年 ==========
        "migrant_youth" to LifePathBinding(
            pathId = "migrant_youth",
            pathTitle = "外出打工青年",
            spaceConfig = SpaceConfig(
                addressName = "厂区集体宿舍",
                areaSqm = 12,
                monthlyRent = 200.0,
                monthlyIncome = 4500.0,
                monthlyWorkHours = 260.0,
                light = 50,
                noise = 60,
                privacy = 20,
                furnitureLevel = 20,
                commuteMinutesOneWay = 5,
                workHoursPerDay = 10,
                currentSavings = 3000.0,
                description = "上下铺，简易书桌，公共洗漱间。不大，但这是你在这座城市的落脚点。"
            ),
            defaultOutfits = listOf(
                DefaultOutfit("工装外套", "外套", "涤棉混纺", "深蓝色",
                    "耐磨耐脏，每天上班前套上就走。袖口磨得有些发白。",
                    "陪你站过无数个十小时的班。不好看，但很可靠。"),
                DefaultOutfit("劳保鞋", "鞋", "橡胶底+帆布面", "黑色",
                    "鞋底厚实防滑，鞋头加硬防砸。穿久了脚会闷，但安全。",
                    "替你踩过车间湿滑的地面、挡过掉下来的零件。每一步都稳稳当当的。"),
                DefaultOutfit("旧T恤", "上衣", "纯棉", "灰色",
                    "领口有点松了，下摆洗得有些变形。但穿着睡觉很舒服。",
                    "下了班换掉工装，这件旧T恤就是你最放松的时刻。洗了多少次已经数不清了。")
            ),
            defaultFoods = listOf(
                DefaultFood("食堂快餐", "主食", 8.0, "一份米饭配两素一荤，量足。吃完了继续上工，胃里有东西就不慌。"),
                DefaultFood("馒头配咸菜", "主食", 3.0, "最简单也最省钱的吃法。啃着馒头的时候会想家，但吃饱了就有力气。"),
                DefaultFood("方便面", "速食", 4.0, "车间加完班回来泡一碗。热水冲下去的那一下，整个人都暖和了。"),
                DefaultFood("食堂大锅菜", "家常菜", 10.0, "味道不精致，但油水足。和工友一起吃，聊聊天，一天的累就消了一半。")
            )
        ),

        // ========== 2. 全职妈妈 ==========
        "housewife" to LifePathBinding(
            pathId = "housewife",
            pathTitle = "全职妈妈",
            spaceConfig = SpaceConfig(
                addressName = "城市普通居民住宅",
                areaSqm = 80,
                monthlyRent = 0.0,
                monthlyIncome = 3000.0,
                monthlyWorkHours = 0.0,
                light = 70,
                noise = 35,
                privacy = 50,
                furnitureLevel = 60,
                commuteMinutesOneWay = 0,
                workHoursPerDay = 16,
                currentSavings = 20000.0,
                description = "三室一厅，不大不小。客厅地上永远有孩子的玩具，厨房灶台上永远有洗了一半的碗。"
            ),
            defaultOutfits = listOf(
                DefaultOutfit("居家棉布围裙", "配件", "棉布", "碎花",
                    "系上又解下，一天要好几回。口袋里有孩子的橡皮筋和一包纸巾。",
                    "它见过你炒菜、洗碗、给孩子擦手。不上台面的东西，却是这个家运转的核心。"),
                DefaultOutfit("舒适棉拖鞋", "鞋", "棉布底", "米白色",
                    "底磨得薄了。你在屋子里来来回回，从厨房到阳台，从卧室到客厅。",
                    "这双拖鞋走的路不比你出差少。只是你走的是家，不是写字楼。"),
                DefaultOutfit("宽松居家便装", "上衣", "纯棉", "浅灰色",
                    "不起球不勒身，喂奶、做家务、跑菜市场都能穿。",
                    "你不需要穿得多好看。穿着舒服、干活方便——这就是你每天的「战袍」。")
            ),
            defaultFoods = listOf(
                DefaultFood("家常番茄炒蛋", "家常菜", 6.0, "孩子最爱吃的菜。你做了无数次，火候刚刚好。一家人的筷子都往这个盘子里伸。"),
                DefaultFood("白粥配小菜", "主食", 3.0, "早上一碗热粥下肚，胃就醒了。不管昨天多累，新的一天从这碗粥开始。"),
                DefaultFood("自己包的饺子", "主食", 15.0, "周末有空就包一些冻起来。自己擀的皮比买的好吃。老公说外面吃不到这个味道。"),
                DefaultFood("热牛奶", "饮品", 4.0, "孩子睡前一杯，你偶尔也给自己热一杯。喝完关灯，一天终于安静了。")
            )
        ),

        // ========== 3. 外卖骑手 ==========
        "delivery_rider" to LifePathBinding(
            pathId = "delivery_rider",
            pathTitle = "外卖骑手",
            spaceConfig = SpaceConfig(
                addressName = "城中村单间",
                areaSqm = 15,
                monthlyRent = 1000.0,
                monthlyIncome = 6000.0,
                monthlyWorkHours = 224.0,
                light = 40,
                noise = 55,
                privacy = 70,
                furnitureLevel = 25,
                commuteMinutesOneWay = 0,
                workHoursPerDay = 12,
                currentSavings = 5000.0,
                description = "老小区里的一间小单间。一张床、一张桌子、一个衣柜。你大部分时间都在外面跑，这间屋子主要用来睡觉。"
            ),
            defaultOutfits = listOf(
                DefaultOutfit("骑行防风外套", "外套", "防水聚酯", "蓝色",
                    "背后印着平台logo。防风防小雨，拉链修过一次还能用。",
                    "风吹日晒都靠它顶着。客户不会看你穿什么，但你知道：跑得多就挣得多，这件外套从来不掉链子。"),
                DefaultOutfit("防滑运动鞋", "鞋", "网面+橡胶底", "黑色",
                    "鞋底花纹快磨平了。每天蹬地起步、爬楼梯，一天下来脚底发烫。",
                    "你丈量城市的工具不是导航，是这双鞋。每一单的距离，它都帮你跑过了。"),
                DefaultOutfit("骑行手套", "配件", "涤纶+硅胶防滑", "黑色",
                    "掌心加厚防震，指头露在外面方便操作手机。夏天闷汗冬天冻手。",
                    "握了一天车把。别人看手机抢单，你靠手感和经验。这双手套知道你的不容易。")
            ),
            defaultFoods = listOf(
                DefaultFood("路边快餐", "主食", 12.0, "趁单少的间隙扒拉几口。有时候吃到一半来单了，放下筷子就往外跑。"),
                DefaultFood("包子豆浆", "主食", 5.0, "早会结束后在路边买两个包子。热乎乎的，几口就没了。吃完开始一天的奔波。"),
                DefaultFood("瓶装水", "饮品", 2.0, "天热的时候一天能喝三瓶。电动车停下来等红灯的时候拧开灌一口。"),
                DefaultFood("炒饭/炒面", "主食", 10.0, "晚上收工后在小摊上吃一份。油大、管饱、便宜。吃完了回出租屋，洗洗睡。")
            )
        ),

        // ========== 4. 应届毕业生 ==========
        "fresh_graduate" to LifePathBinding(
            pathId = "fresh_graduate",
            pathTitle = "应届毕业生",
            spaceConfig = SpaceConfig(
                addressName = "合租房小单间",
                areaSqm = 10,
                monthlyRent = 800.0,
                monthlyIncome = 0.0,
                monthlyWorkHours = 0.0,
                light = 55,
                noise = 50,
                privacy = 40,
                furnitureLevel = 30,
                commuteMinutesOneWay = 45,
                workHoursPerDay = 0,
                currentSavings = 2000.0,
                description = "三室一厅里最小那间。一张床、一张桌子、一个布衣柜。隔壁室友不怎么说话，厨房冰箱里大家的食物挤在一起。"
            ),
            defaultOutfits = listOf(
                DefaultOutfit("面试正装", "外套", "涤纶混纺", "黑色",
                    "不太贵但熨得很平整。每次面试前才穿，回来就挂好。肩膀那里有点紧但看起来精神。",
                    "穿上它就觉得自己像个大人了。虽然心里慌，但你每次都挺直了腰走进去。你在学着为自己争取。"),
                DefaultOutfit("帆布双肩包", "配件", "帆布", "卡其色",
                    "背了四年大学，边角磨破了但还能装。里面永远塞着简历、充电宝和一把伞。",
                    "陪你跑过一天三场面试。它不在乎你去的是写字楼还是居民楼里的初创公司。它有容乃大。"),
                DefaultOutfit("白衬衫", "上衣", "棉涤混纺", "白色",
                    "百搭款。面试穿、见人穿、拍证件照也穿。袖口微微发黄，但整体还算精神。",
                    "你不知道自己值多少钱，但每次穿上它都告诉镜子里的自己：你可以的。")
            ),
            defaultFoods = listOf(
                DefaultFood("便利店饭团", "速食", 6.0, "赶面试的路上顺手买一个。在地铁上啃完，擦擦嘴去下一场。"),
                DefaultFood("外卖特价套餐", "主食", 15.0, "用优惠券点外卖是必备技能。比到店便宜五块，省下来的钱够坐一次地铁。"),
                DefaultFood("挂耳咖啡", "饮品", 3.0, "早上起来冲一杯。不是为了品味，是为了撑住一天的面试和笔试。"),
                DefaultFood("鸡蛋灌饼", "主食", 5.0, "小区门口的摊子。加一个蛋多加五毛。热乎乎地咬下去，觉得今天又有希望了。")
            )
        ),

        // ========== 5. 工地工人 ==========
        "construction_worker" to LifePathBinding(
            pathId = "construction_worker",
            pathTitle = "工地工人",
            spaceConfig = SpaceConfig(
                addressName = "工地简易工棚",
                areaSqm = 10,
                monthlyRent = 0.0,
                monthlyIncome = 7000.0,
                monthlyWorkHours = 260.0,
                light = 45,
                noise = 75,
                privacy = 15,
                furnitureLevel = 15,
                commuteMinutesOneWay = 0,
                workHoursPerDay = 10,
                currentSavings = 10000.0,
                description = "工地边上的活动板房。夏天像蒸笼，冬天漏风。和三个工友挤一间，但也就晚上回来躺一下，白天都在工地上。"
            ),
            defaultOutfits = listOf(
                DefaultOutfit("劳保服", "外套", "帆布", "迷彩绿",
                    "耐磨耐刮，口袋多到能装下扳手和手机。汗渍洗不掉但无所谓，反正明天还会脏。",
                    "你在工地上弯腰、蹲下、爬上爬下。这身劳保服从来没让你露过怯。它不花哨，但保护你。"),
                DefaultOutfit("防砸劳保鞋", "鞋", "牛皮+钢头", "土黄色",
                    "鞋底厚实得像两块砖。踩钉子也不怕。夏天穿闷出一脚汗但安全。",
                    "工地上最怕脚下出事。这双鞋替你挡了无数次。你没倒下，它也没。"),
                DefaultOutfit("线手套", "配件", "棉线", "白色",
                    "一打一打地买，掌心两三天就磨破。破了换一双新的继续搬。",
                    "手套换了一双又一双，但手还是你的手。茧子越来越厚，力气也越来越大。你从来不是靠别人养的人。")
            ),
            defaultFoods = listOf(
                DefaultFood("工地盒饭", "主食", 10.0, "一荤两素，米饭管够。蹲在阴凉处吃，工友几个围一圈。吃完歇十分钟继续上工。"),
                DefaultFood("馒头+大锅菜", "主食", 6.0, "馒头蘸菜汤，最扛饿的吃法。不精致但实在，和你人一样。"),
                DefaultFood("冰镇啤酒", "饮品", 4.0, "收工后工友喊你去小卖部。一瓶冰啤下肚，一天的灰和汗都冲淡了。"),
                DefaultFood("面条", "主食", 8.0, "晚上饿了下碗面。没什么配菜，放点酱油和辣椒就很香。吃着吃着就想家了。")
            )
        ),

        // ========== 6. 小店店主 ==========
        "shop_owner" to LifePathBinding(
            pathId = "shop_owner",
            pathTitle = "小店店主",
            spaceConfig = SpaceConfig(
                addressName = "商住一体小店",
                areaSqm = 40,
                monthlyRent = 3000.0,
                monthlyIncome = 5000.0,
                monthlyWorkHours = 300.0,
                light = 65,
                noise = 45,
                privacy = 60,
                furnitureLevel = 40,
                commuteMinutesOneWay = 0,
                workHoursPerDay = 14,
                currentSavings = 30000.0,
                description = "前面是店、后面是住处。货架之间只留一条窄窄的过道。你在这几十平米里待的时间比任何地方都长。"
            ),
            defaultOutfits = listOf(
                DefaultOutfit("棉麻围裙", "配件", "棉麻", "深棕色",
                    "口袋沉甸甸的：零钱、笔、小本子。系带的地方磨出了须须。",
                    "它见过你搬货、理货、算账、招呼客人。三十平米的店里你走了上万步。你是个小老板，也是自己的店员。"),
                DefaultOutfit("老布鞋", "鞋", "布面+橡胶底", "黑色",
                    "软底轻便，站一天不容易脚疼。穿了两年了，鞋面有些褪色。",
                    "你站在这家店里的时间加起来比任何地方都多。它陪着你，从早到晚，从进货到关门。"),
                DefaultOutfit("旧毛衣", "上衣", "混纺", "灰色",
                    "冬天店里冷，套一件旧毛衣。袖口有点起球但暖和。客人进来你笑着打招呼，没人注意你穿什么。",
                    "一件旧毛衣陪你过几个冬天。你不需要天天换新，因为你知道：店里的货比身上的衣服重要。")
            ),
            defaultFoods = listOf(
                DefaultFood("自家做的盖浇饭", "主食", 8.0, "在后面的小厨房炒个菜盖在饭上。比外卖便宜，味道也是自己习惯的。"),
                DefaultFood("茶叶蛋", "零食", 2.0, "在店门口的小锅里煮着。自己吃两个，也给常来的小孩递一个。"),
                DefaultFood("速溶咖啡", "饮品", 2.0, "午后犯困冲一杯。小店没有午休，但可以来杯速溶提提神。"),
                DefaultFood("泡面+火腿肠", "速食", 5.0, "忙起来顾不上做饭。泡面是最快的。吃完把碗一推继续看店。")
            )
        ),

        // ========== 7. 全职儿女 ==========
        "adult_child" to LifePathBinding(
            pathId = "adult_child",
            pathTitle = "全职儿女",
            spaceConfig = SpaceConfig(
                addressName = "父母家次卧",
                areaSqm = 15,
                monthlyRent = 0.0,
                monthlyIncome = 1500.0,
                monthlyWorkHours = 0.0,
                light = 60,
                noise = 30,
                privacy = 45,
                furnitureLevel = 50,
                commuteMinutesOneWay = 0,
                workHoursPerDay = 0,
                currentSavings = 50000.0,
                description = "住了几十年的老房子。家具旧了、墙皮有点掉，但每个角落都是回忆。爸妈在这间屋里把你养大，现在换你照顾他们了。"
            ),
            defaultOutfits = listOf(
                DefaultOutfit("舒适居家服", "上衣", "纯棉", "浅蓝色",
                    "宽松不勒，弯腰给爸妈洗脚、蹲着择菜都不碍事。",
                    "你不需要西装革履。你对体面的定义不是穿多好，而是爸妈今天有没有按时吃药。"),
                DefaultOutfit("厚底拖鞋", "鞋", "EVA", "藏青色",
                    "防滑、软底、不响。半夜起来给爸妈倒水，不会吵醒他们。",
                    "你比闹钟还准时。爸妈的起夜时间你摸得比自己的还清楚。这双拖鞋知道你的每一次轻手轻脚。"),
                DefaultOutfit("帆布购物袋", "配件", "帆布", "米色",
                    "每天拎着去菜市场。挑菜的时候会想：这个妈能吃、那个爸咬不动。",
                    "别人在聊绩效和KPI，你在算今天买的排骨炖多久才会烂。你的KPI是爸妈的血压和血糖——比任何考核都重要。")
            ),
            defaultFoods = listOf(
                DefaultFood("清蒸鱼", "家常菜", 18.0, "爸爱吃鱼。你每次挑刺都挑得仔仔细细，怕他卡着。他说不用这么小心，你还是会挑。"),
                DefaultFood("小米粥", "主食", 3.0, "妈妈胃不好，早晚各喝一碗小米粥。你熬得稠稠的，比买的养胃。"),
                DefaultFood("蒸蛋羹", "家常菜", 4.0, "软软的，爸妈都能吃。你蒸了很多年，火候刚好。小时候是他们蒸给你吃，现在是你在厨房忙。"),
                DefaultFood("热牛奶", "饮品", 4.0, "睡前给爸妈热一杯。你看表：九点半，该吃药了。日子就这样围着他们转——你心甘情愿。")
            )
        ),

        // ========== 8. 退休工人 ==========
        "retired_worker" to LifePathBinding(
            pathId = "retired_worker",
            pathTitle = "退休工人",
            spaceConfig = SpaceConfig(
                addressName = "老小区一居室",
                areaSqm = 50,
                monthlyRent = 0.0,
                monthlyIncome = 2800.0,
                monthlyWorkHours = 0.0,
                light = 65,
                noise = 30,
                privacy = 80,
                furnitureLevel = 55,
                commuteMinutesOneWay = 0,
                workHoursPerDay = 0,
                currentSavings = 80000.0,
                description = "老家属院的两室一厅。住了二十年了，邻居都认识。窗外那棵梧桐树比你搬进来的时候高了很多。你不用赶时间了——时间终于完全属于你了。"
            ),
            defaultOutfits = listOf(
                DefaultOutfit("老式布衣", "上衣", "棉布", "深灰色",
                    "宽松舒服，口袋大得能装下收音机和老花镜。每天早上穿上，慢慢踱到公园。",
                    "年轻的时候穿工装，现在穿布衣。不赶时髦，舒服就好。你用了几十年终于学会：对自己好不需要理由。"),
                DefaultOutfit("软底布鞋", "鞋", "布面+牛筋底", "黑色",
                    "底软、轻便、不挤脚。你穿着它走过菜市场、公园、楼下棋摊。",
                    "步子是慢下来了，但每一步都走得很稳。不用跑、不用追、不用赶。你年轻的时候已经赶够了。"),
                DefaultOutfit("毛线帽", "配件", "毛线", "藏青色",
                    "冬天出门散步就戴上。有点起球但不影响保暖。",
                    "儿女买的，说是「别冻着脑袋」。你嘴上说不用，但每次出门都戴着。暖的不只是头，是心。")
            ),
            defaultFoods = listOf(
                DefaultFood("热茶", "饮品", 1.0, "茶叶不贵但泡得浓。每天早上一缸茶、阳台上一坐，就是大半个上午。"),
                DefaultFood("清粥小菜", "主食", 4.0, "早上喝粥。配点咸菜、半个咸鸭蛋。不紧不慢地吃完，看看窗外。"),
                DefaultFood("蒸馒头", "主食", 3.0, "自己蒸的。比买的有嚼劲。发酵的时候你就在旁边看报纸，等它慢慢发起来。"),
                DefaultFood("炖汤", "家常菜", 12.0, "砂锅炖一下午，骨头都炖酥了。儿女周末回来喝一碗，说比外面的好喝。你就笑。")
            )
        ),

        // ========== 9. 自由职业者 ==========
        "freelancer" to LifePathBinding(
            pathId = "freelancer",
            pathTitle = "自由职业者",
            spaceConfig = SpaceConfig(
                addressName = "居家工作室",
                areaSqm = 35,
                monthlyRent = 2500.0,
                monthlyIncome = 8000.0,
                monthlyWorkHours = 120.0,
                light = 60,
                noise = 35,
                privacy = 85,
                furnitureLevel = 55,
                commuteMinutesOneWay = 0,
                workHoursPerDay = 10,
                currentSavings = 15000.0,
                description = "一张大桌子占了大半个房间。左边是电脑屏幕，右边是咖啡杯和水杯。窗外是小区，安安静静的。这就是你的办公室。"
            ),
            defaultOutfits = listOf(
                DefaultOutfit("居家卫衣", "上衣", "纯棉", "灰色",
                    "宽松、不起球、不扎脖子。早上起床套上就开始干活。",
                    "不需要在乎别人怎么看你穿什么。你只需要在乎：今天能不能在 deadline 前交稿。"),
                DefaultOutfit("降噪耳机", "配件", "蛋白皮耳罩", "黑色",
                    "戴上世界就安静了。小区装修、楼下小孩哭——都听不见。",
                    "你为自己创造专注的空间。没有同事帮你挡噪音，你自己挡。你是一个人的团队，也是自己的后勤。"),
                DefaultOutfit("宽松家居裤", "裤子", "棉麻", "米色",
                    "不勒腰、不闷汗、坐着干活一天也不难受。",
                    "没有人看你穿什么。你的价值在你的作品里——在你的稿子里、代码里、设计图里。裤子只是裤子，但舒服很重要。")
            ),
            defaultFoods = listOf(
                DefaultFood("手冲咖啡", "饮品", 5.0, "不是品味，是习惯。早上磨豆子、注水、等滴完。这几分钟是你一天里最安静的时刻。"),
                DefaultFood("外卖沙拉/简餐", "主食", 25.0, "忙起来没空做饭。点一份沙拉在电脑前吃完。健康和效率只能兼顾成这样。"),
                DefaultFood("速冻水饺", "速食", 12.0, "冰箱常备。懒得想吃什么的时候煮一袋。沾点醋就是一顿。"),
                DefaultFood("泡面+鸡蛋", "速食", 5.0, "凌晨还在改方案。饿了下碗泡面，打个蛋。吃完接着改。没有人给你发加班费，但你在为自己做事。")
            )
        ),

        // ========== 10. 基层公务员 ==========
        "civil_servant" to LifePathBinding(
            pathId = "civil_servant",
            pathTitle = "基层公务员",
            spaceConfig = SpaceConfig(
                addressName = "单位宿舍/普通住宅",
                areaSqm = 50,
                monthlyRent = 500.0,
                monthlyIncome = 4500.0,
                monthlyWorkHours = 154.0,
                light = 65,
                noise = 35,
                privacy = 70,
                furnitureLevel = 45,
                commuteMinutesOneWay = 20,
                workHoursPerDay = 8,
                currentSavings = 40000.0,
                description = "规规整整的小户型。东西不多但每样都很实用。窗台上养了两盆绿萝，长势不错。早上骑电动车去单位，十几分钟就到。"
            ),
            defaultOutfits = listOf(
                DefaultOutfit("深色夹克", "外套", "涤纶", "藏青色",
                    "款式简单不出挑。开会、下乡、见群众都能穿。口袋大，装得下笔记本和笔。",
                    "你不需要穿得多显眼。低调、得体、干净——这就是你的风格。你做的事比穿什么重要。"),
                DefaultOutfit("皮鞋", "鞋", "牛皮", "黑色",
                    "鞋底补过一次但还是擦得亮。每次出门前弯腰擦两下，是对工作的尊重。",
                    "你穿着它走过办事大厅的大理石地面，也走过贫困户家门口的泥巴路。它不嫌脏，你也不嫌累。"),
                DefaultOutfit("保温杯", "配件", "不锈钢", "银色",
                    "放在办公桌上。开会时端上。被同事笑说「老干部作风」但你自己知道：冬天有一口热水喝，比什么都实在。",
                    "它陪你开过无数会、改过无数文件。那些默默做了的事，不需要被人知道。保温杯知道就够了。")
            ),
            defaultFoods = listOf(
                DefaultFood("单位食堂午餐", "主食", 5.0, "两荤一素一汤，补贴后很便宜。和同事一起吃，聊聊工作、聊聊孩子。"),
                DefaultFood("家常粥面", "主食", 6.0, "晚上回家自己做。下一碗面、拌个凉菜。安安静静吃完，看看新闻。"),
                DefaultFood("茶叶", "饮品", 2.0, "办公室常备。来人办事给泡一杯。你自己也喝，喝了很多年，喝的是习惯。"),
                DefaultFood("水果", "零食", 5.0, "下午四点吃个苹果。补充体力，也给自己喘口气的时间。")
            )
        )
    )

    // ============================================
    // 工具方法
    // ============================================

    /** 根据路径 ID 获取绑定配置 */
    fun getBinding(pathId: String): LifePathBinding? = bindings[pathId]

    /** 获取所有身份 ID 列表 */
    fun allPathIds(): List<String> = bindings.keys.toList()
}