package com.example.townapp.data

/**
 * 消费者认知模型 —— v1.3 终极平等世界观
 *
 * 核心命题：众生皆无错，环境造百态。
 * 没有"清醒"或"不清醒"的人，只有不同处境、不同压力、不同信息渠道的人。
 * 每一个消费选择都是当下处境里最合理的自我照顾方式。
 * 小镇只解释成因，不审判人格。
 *
 * 每类人群的消费行为由三个维度如实记录：
 * 1. 食物选择偏好——日常吃什么、什么情况下吃什么
 * 2. 购物消费倾向——受哪些信息渠道影响
 * 3. 医药/保健品认知——信息获取渠道与局限
 */

// ============================================
// 消费特征枚举
// ============================================

/** 食物选择模式 */
enum class FoodPattern(val label: String) {
    SURVIVAL("吃饱就行"),       // 高饱腹、不挑营养、重油重碳水
    REWARD_EATING("犒劳型"),    // 平时节省，定期用大餐犒劳自己
    FAMILY_ORIENTED("家庭导向"), // 精打细算满足全家需求
    HEALTH_PLANNING("健康规划"), // 有意识地选择优质蛋白、清淡饮食
    CONVENIENCE("便利优先"),    // 没时间做饭，外卖/速食为主
    BALANCED_CANTEEN("食堂均衡") // 依托食堂，饮食均衡稳定
}

/** 消费信息差类型（非陷阱、非骗局——只记录不同人群面对的不同信息环境） */
enum class TrapType(val label: String) {
    LOW_PRICE_TRAP("低价信息差"),       // 低价商品信息不对称，难以辨别品质
    EMOTIONAL_PURCHASE("即时情绪消费"),   // 疲惫后的即时自我犒赏
    MARKETING_HYPE("营销信息影响"),      // 受短视频/直播/广告等营销渠道影响
    HEALTH_SCAM("健康信息差"),          // 缺少系统健康科普渠道
    PREMIUM_SOCIAL("社交圈层消费"),      // 为身份认同和圈子归属付费
    NONE("无显著信息差")
}

/** 服饰消费类型 */
enum class ClothingStyle(val label: String) {
    WORK_GEAR("劳作服饰"),          // 工装、劳保服——工具属性为主，实用第一
    CHEAP_FAST_FASHION("低价仿牌"), // 网红杂牌、仿大牌款式——短期体面
    PREMIUM_BRAND("轻奢质感"),      // 注重面料、设计、品牌——审美消费
    UTILITY_PLAIN("朴素实用"),      // 不挑款式，舒服耐穿即可
    FAMILY_COMFORT("居家舒适")      // 居家为主，干净舒服就好
}

/** 休闲文娱类型（v1.3 潮流文娱补全） */
enum class LeisureStyle(val label: String) {
    POP_CULTURE("潮流文娱追星"),      // 追星、演唱会、周边、饭圈社群
    OUTDOOR_ADVENTURE("户外运动"),    // 露营、骑行、徒步
    CREATIVE_COLLECTION("文创收集"),  // 盲盒、手账、贴纸、漫展
    LIVE_STREAMING("线上直播陪伴"),   // 直播观看、小额打赏
    SHORT_VIDEO_ONLY("碎片化短视频"), // 仅刷短视频放松
    TRAVEL_SPRINT("特种兵旅行"),      // 周末短途紧凑旅行
    CHILDHOOD_SIMPLE("童年简趣"),     // 动漫贴纸、简单爱好
    MINIMAL("极简休闲")              // 体力消耗大或时间碎片化，几乎无额外休闲
}

// ============================================
// 认知模型数据类
// ============================================

data class ConsumerCognition(
    val pathId: String,

    /** 食物选择模式 */
    val foodPattern: FoodPattern,

    /** 日常饮食描述 */
    val foodHabitDescription: String,

    /** 消费倾向描述 */
    val consumptionTrait: String,

    /** 主要信息差类型 */
    val trapType: TrapType,

    /** 信息差具体表现 */
    val trapDetail: String,

    /** 医药认知描述 */
    val medicalCognition: String,

    /** 小镇对这类人群消费环境的评述（只解释成因，不审判选择） */
    val townConsumptionCommentary: String,

    // ---- 服饰消费维度（v1.3 阶段二穿搭改造） ----

    /** 服饰消费类型 */
    val clothingStyle: ClothingStyle,

    /** 穿搭消费画像 */
    val clothingTrait: String,

    /** 小镇对穿搭选择的评述（只解释处境，不审判选择） */
    val clothingTownCommentary: String,

    // ---- 成长环境维度（v1.3 终极平等世界观） ----

    /** 出生地环境 */
    val birthEnvironment: String,

    /** 成长带来的迷茫状态 */
    val growthConfusion: String,

    /** 小镇对迷茫的评述（解释成因，发掘潜藏闪光点） */
    val confusionComment: String,

    // ---- 先天条件维度（v1.3 先天个体差异） ----

    /** 先天条件：体质、天赋、家境等不可自主选择的开局参数 */
    val innateCondition: String,

    // ---- 休闲文娱维度（v1.3 潮流文娱补全） ----

    /** 休闲方式类型 */
    val leisureStyle: LeisureStyle,

    /** 休闲行为描述 */
    val leisureTrait: String,

    /** 小镇对休闲选择的评述（只解释环境成因，不审判选择） */
    val leisureCommentary: String,

    /** 同辈圈层带来的心理状态（攀比焦虑、跟风冲动等，无则留空） */
    val peerAnxiety: String,

    // ---- 职业圈层认知局限（v1.6 跨职业视角隔阂体系） ----

    /** 该职业普遍存在的固有认知偏差——对其他职业/人群的片面看法 */
    val cognitiveLimitation: String,

    /** 视角偏差的成因解释——环境隔绝如何催生了这种偏差 */
    val limitationExplanation: String
)

// ============================================
// 15 条身份的完整认知配置
// ============================================

object ConsumerCognitionData {

    val cognitions: Map<String, ConsumerCognition> = mapOf(

        // 1. 外出打工青年
        "migrant_youth" to ConsumerCognition(
            pathId = "migrant_youth",
            foodPattern = FoodPattern.REWARD_EATING,
            foodHabitDescription = "平日三餐食堂简餐、面条为主，精打细算。月末发薪后会买红烧肉、油炸快餐犒劳自己。",
            consumptionTrait = "攒钱时非常克制，发薪日情绪释放。网购时偏爱低价仿款衣物，款式时髦但面料不耐穿。",
            trapType = TrapType.LOW_PRICE_TRAP,
            trapDetail = "挑选低价仿牌工装、潮流短袖，看着款式新潮，穿了两个月就变形起球。在信息不对称中为贴牌溢价付出了更高价格。",
            medicalCognition = "知道布洛芬处理肌肉酸痛，但闲暇刷短视频时会受到养生偏方和不知名保健品推销信息的影响。",
            townConsumptionCommentary = "一个人在陌生城市打拼，把钱掰成两半花。发薪日奖励自己的那顿红烧肉——是太累了，需要一个能立刻让自己开心的东西。廉价的贴牌衣物不是在辨别力上输了，是没有多余的精力去比价研究。每一个受营销信息影响的瞬间背后，都站着一种生存压力。这不是谁的错。",
            clothingStyle = ClothingStyle.CHEAP_FAST_FASHION,
            clothingTrait = "选购衣物时偏爱低价仿款，款式时髦但面料不耐穿。偶尔买一件看起来体面的，穿两个月就变形起球。也许知道不太值，但「好看又不贵」本身就是在疲惫生活里给自己留一点好看的样子。",
            clothingTownCommentary = "在车间里穿工装，出了厂门想穿件像样的。廉价仿牌穿上身的那一刻，不是不知道它撑不了多久——但「看起来还行」的体面感，是疲劳生活里一点小小的安慰。这不是眼光的问题，是精力都花在了更有价值的地方：认真工作、攒钱、养家。",
            birthEnvironment = "偏远乡镇山村，童年长期协助家中农活，课外兴趣课程完全空白。",
            growthConfusion = "学生时代结束后外出务工，没有系统爱好，闲暇只会刷短视频、玩手机，被问到未来规划时常感到迷茫，不知道长期的人生目标。",
            confusionComment = "童年的精力优先用来应付生计，没有机会体验各类爱好。暂时看不清前路十分正常，踏实肯吃苦、懂得体谅家人，便是藏在他身上珍贵的闪光点。",
            innateCondition = "先天普通体质，家境乡镇普通家庭，天赋大众化。",
            leisureStyle = LeisureStyle.MINIMAL,
            leisureTrait = "休息时刷偶像短视频，线上浏览饭圈内容。受预算限制极少购买高价门票，以线上轻量化追星为主。偶尔看到同龄人的演唱会动态，闪过一丝羡慕，但工位上的订单提醒很快就把这点心思冲散了。",
            leisureCommentary = "平日奔波疲惫，碎片化刷偶像短视频，是枯燥打工生活里低成本的情绪慰藉。不是不想去演唱会——是时间和预算都不允许。在能力范围内给自己一点微小的快乐，已经是很会照顾自己了。",
            peerAnxiety = "偶尔看到同龄人发布演唱会、旅行动态，内心闪过一丝羡慕和对比。但很快就被工作冲散——生活的节奏不给你时间焦虑。",
            cognitiveLimitation = "觉得办公室工作吹着空调、环境舒适，工作轻松简单。看不到办公室人群长期久坐焦虑、职场内卷、繁杂人事内耗，只凭借外在环境做出简单判断。",
            limitationExplanation = "长期在车间、户外体力劳作，身体疲惫是每天最直观的感受。密闭写字楼里的精神内耗、人际摩擦、KPI焦虑——这些看不见的消耗，不亲身经历很难体会。环境隔绝了彼此的生活，便形成了理想化的想象。"
        ),

        // 2. 全职妈妈
        "housewife" to ConsumerCognition(
            pathId = "housewife",
            foodPattern = FoodPattern.FAMILY_ORIENTED,
            foodHabitDescription = "家庭日常精打细算，家常菜清淡家常。逢节假日全家聚餐会做一顿大的，带孩子吃顿好的当作奖励。",
            consumptionTrait = "日常开销精打细算到每一块钱。但面对母婴产品、育儿保健品时，在「为孩子好」的信息面前更容易被说服。",
            trapType = TrapType.MARKETING_HYPE,
            trapDetail = "面对母婴网红产品、儿童保健品广告时更容易受影响。「为了孩子」这几个字，是所有妈妈都很难不放在心上的。",
            medicalCognition = "家庭常备药齐全，但面对育儿类保健品、儿童营养品的营销时缺乏辨别渠道，容易付出更高价格。",
            townConsumptionCommentary = "她把每一分钱都花在了别人身上——孩子的衣服、全家的菜、日用品的精打细算。偶尔受母婴营销信息影响，不是她不够仔细，是「为了孩子好」这几个字太重了。没有人教过妈妈们怎么辨别育儿产品的营销话术。这不是妈妈的问题，是整个社会在信息支持上做得还不够。",
            clothingStyle = ClothingStyle.FAMILY_COMFORT,
            clothingTrait = "自己的衣服能省则省，一件居家便装穿好几年。给孩子买衣服时偶尔会受母婴博主推荐影响，买回一些价格偏高的儿童衣物。",
            clothingTownCommentary = "她的衣柜里没有几件属于自己的新衣服。围裙、拖鞋、居家便装——这些不上台面的衣物，是她作为妈妈的全部装备。偶尔受母婴营销信息影响，不是因为不谨慎，是因为「为了孩子」这几个字太重了。",
            birthEnvironment = "普通县城，成长环境安稳平淡，课余生活简单。",
            growthConfusion = "成年后重心投入家庭，长期围着家务琐事打转，慢慢忘记年轻时的喜好，偶尔疑惑自我价值，不清楚除家庭之外自己可以追求什么。",
            confusionComment = "长久投入家庭生活，个人节奏顺着家庭节奏变化，暂时迷失自我十分平常。日复一日细心照料家人，这份长久的耐心，便是独属于她的闪光点。",
            innateCondition = "先天普通体质，家境县城普通家庭，天赋大众化。",
            leisureStyle = LeisureStyle.SHORT_VIDEO_ONLY,
            leisureTrait = "育儿家务繁重，碎片化刷短视频明星片段，极少线下参与。偶尔在短视频里看到喜欢的明星，会停下来多刷几秒——那是她一天里为数不多的、完全属于自己的几秒钟。",
            leisureCommentary = "她的时间被切成碎片，分给孩子、分给做饭、分给家务。刷短视频是她唯一不用离开家的放松方式。不是不想出去玩——是能自由支配的时间实在太少了。",
            peerAnxiety = "",
            cognitiveLimitation = "长期居家育儿，精力被切碎，习惯性认为所有人的生活重心都应以家庭为中心。难以切身感受职场通勤、加班和职场人情关系的消耗，偶尔会低估上班族在家庭和工作之间的平衡难度。",
            limitationExplanation = "她的一天被家庭琐事填满，没有机会坐在办公室经历开会、汇报、上下级沟通。职场里的精神消耗是看不见的——没有切身体验，便容易形成片面判断。这不是她的问题，是环境隔绝带来的视角局限。"
        ),

        // 3. 外卖骑手
        "delivery_rider" to ConsumerCognition(
            pathId = "delivery_rider",
            foodPattern = FoodPattern.CONVENIENCE,
            foodHabitDescription = "奔波赶路来不及做饭，街边快餐、外卖为主。高强度体力消耗下先考虑填饱肚子，顾不上营养搭配。",
            consumptionTrait = "收入波动大但跑得多挣得多。消费偏即时型，偶尔买廉价潮流衣物或新款手机配件犒劳自己。",
            trapType = TrapType.EMOTIONAL_PURCHASE,
            trapDetail = "跑了一天精疲力尽，休息时刷手机看到特价潮流短袖。款式花哨但面料单薄。花钱不多，但衣柜里堆了好几件穿几次就不太想穿的。",
            medicalCognition = "备基础止痛药、创可贴。碎片化看短视频时偶尔接收到不实健康资讯，但没时间深究真假。",
            townConsumptionCommentary = "他的时间不是自己的——在商家、顾客、平台之间被切成碎片。能坐下来吃顿热饭的时间都很少，更别说研究吃什么才健康。花几十块买件好看的短袖，是这一天唯一属于自己的奖励。不理性，但很真实——这种真实不需要被评判。",
            clothingStyle = ClothingStyle.CHEAP_FAST_FASHION,
            clothingTrait = "骑行装备是生产工具，舍得花钱买好的。但日常便装偏爱廉价潮流款，款式花哨但面料单薄。衣柜里堆着几件穿几次就不太想穿的。",
            clothingTownCommentary = "他的收入靠里程和好评。防风外套和防滑鞋是吃饭的工具，不能省。但下了车回到出租屋，他也想穿得精神一点。几十块一件的潮流短袖，花得也许不值——但在跑了一整天之后，拆快递的那一刻是这一天为数不多的期待。",
            birthEnvironment = "三四线小城市，课业紧凑，童年几乎没有课外兴趣培养时间。",
            growthConfusion = "早早步入社会奔波谋生，日常被订单和奔波填满，闲暇碎片化严重，无暇思考长远规划，对未来感到茫然。",
            confusionComment = "生存压力挤占了规划未来的时间，忙碌之下无暇思索长远方向。风雨之中坚守工作，待人谦和，踏实靠谱，都是他的闪光点。",
            innateCondition = "先天普通体质，家境三四线小城普通家庭，天赋大众化。",
            leisureStyle = LeisureStyle.MINIMAL,
            leisureTrait = "体力消耗过大，下班后几乎没有精力参与线下娱乐。手机里的短视频是唯一的放松——刷到什么看什么，没有特别偏好，只是需要一点不用动脑的东西。",
            leisureCommentary = "跑了一整天，腿和腰都不是自己的了。能在出租屋躺着刷会儿手机，已经是一天里最好的时光。不是他不想出去玩——是身体真的没有多余的力气了。",
            peerAnxiety = "",
            cognitiveLimitation = "路上奔波久了，觉得坐办公室的人风吹不着雨淋不着，是轻松活。看不见办公室人群长期久坐的腰颈劳损、绩效考核的精神压力、无休止会议带来的消耗——只凭借外在环境做出简单判断。",
            limitationExplanation = "骑手的一天在红绿灯和楼梯间度过，身体疲惫是直接感受。密闭办公楼里的精神内耗、人际摩擦、KPI焦虑——这些看不见的消耗，和风吹日晒是两种不同的累。环境隔绝了彼此的生活，便形成了各自的盲区。"
        ),

        // 4. 应届毕业生
        "fresh_graduate" to ConsumerCognition(
            pathId = "fresh_graduate",
            foodPattern = FoodPattern.SURVIVAL,
            foodHabitDescription = "刚步入社会收入有限，压缩伙食开销。一边吃便利店饭团省钱，一边跟风买网红服饰和代餐零食。",
            consumptionTrait = "消费两极化：基本生活非常节省，但为「看起来像大人」会购入不合身价位的衣物和数码产品。",
            trapType = TrapType.LOW_PRICE_TRAP,
            trapDetail = "为省钱买廉价正装，面试时袖口起球被注意到。为「精致生活」买一堆网红小物，后来全部积灰。摇摆中慢慢学会辨别。",
            medicalCognition = "仗着年轻不怎么吃药。身体不适先扛着。对保健品几乎无判断力，容易被颜值高的健康产品吸引。",
            townConsumptionCommentary = "他渴望体面但预算有限，有审美但没经验。买便宜货踩坑、买贵的又后悔，这个阶段几乎每个人都会经历。没有人天生就会管理金钱和欲望。在一次次选择中慢慢找到自己的节奏，才是大多数普通人的成长路径。这不是犯错，是必经之路。",
            clothingStyle = ClothingStyle.CHEAP_FAST_FASHION,
            clothingTrait = "消费两极化明显：面试正装咬牙买了套过得去的，日常衣服则挑最便宜的。跟风买过几件网红单品，穿几次就闲置了。正在从「好看就行」慢慢学会「好穿才行」。",
            clothingTownCommentary = "他的衣柜里有两类衣服：面试穿的「假装大人」款和回出租屋换上的大学旧T恤。花小钱买网红款是每个毕业生的必修课——不是因为不会挑，是因为刚开始学着挑。每一次买错，都是在为以后买对积累经验。",
            birthEnvironment = "二线城市，教育资源充足，从小课程繁多，课业内卷严重。",
            growthConfusion = "从小到大一直跟随家长规划前进，大学毕业脱离固有规划后，突然不知道自己想要什么，职业选择摇摆不定，爱好零散且没有深耕。",
            confusionComment = "从前人生道路大多由家人规划，独自面对社会时陷入迷茫是常态。善于快速学习新事物、愿意接纳新鲜生活，就是他身上的长处。",
            innateCondition = "先天普通体质，家境二线城市中等家庭，天赋中等偏上。",
            leisureStyle = LeisureStyle.POP_CULTURE,
            leisureTrait = "刚入职薪资偏低，缩减日常伙食费存钱抢演唱会门票、购买周边。看到朋友圈同龄人打卡演唱会、晒新潮穿搭，会产生跟风消费冲动。为了期待已久的演唱会，吃一个月泡面也心甘情愿。",
            leisureCommentary = "刚踏入社会，工作琐碎迷茫，偶像演唱会是逃离职场压力的出口。和同龄人一起追星，也收获了同辈之间的陪伴感。短期省吃俭用的消费取舍，是年轻人独有的情绪投资——为热爱买单，不需要被评判值不值。",
            peerAnxiety = "同龄人薪资、穿搭、娱乐方式带来的横向对比焦虑。看到朋友打卡演唱会、穿新潮衣服，会产生「我是不是也该对自己好一点」的冲动。这份焦虑不是虚荣——是同龄人圈层里再正常不过的参照心理。",
            cognitiveLimitation = "沉浸城市潮流环境，容易凭借网络碎片化信息看待乡村和体力劳动，产生理想化想象。想象务农是田园牧歌、想象工地是户外健身，缺乏实地生活体验，不了解体力劳作的艰苦和生存压力。",
            limitationExplanation = "从小在城市长大，信息渠道以网络为主。短视频里田园生活的滤镜、户外自由的美好画面——这些碎片信息构建了一幅理想化的图景，缺少实地接触和切身体验。这不是傲慢，是成长环境带来的视角盲区。"
        ),

        // 5. 工地工人
        "construction_worker" to ConsumerCognition(
            pathId = "construction_worker",
            foodPattern = FoodPattern.REWARD_EATING,
            foodHabitDescription = "工地大锅菜油脂偏高，收工后工友一起聚餐常选重油荤菜。体力消耗大，需要高热量支撑。",
            consumptionTrait = "大件开销非常节俭，但在小商品上有即时消费倾向。刷短视频时容易被直播特价吸引。",
            trapType = TrapType.HEALTH_SCAM,
            trapDetail = "手干裂了坚持用尿素维E乳，务实。但刷到「祛湿排毒」「固本培元」类养生短视频时容易下单。平时一块钱掰成两半花，买保健品时几百块说花就花了。",
            medicalCognition = "知常用药（布洛芬、创可贴、尿素软膏）。但缺乏系统健康科普渠道，容易被短视频养生内容说服。",
            townConsumptionCommentary = "他用身体挣钱。每一块肌肉都为了养家而酸痛。吃重油大餐——是一天搬几千块砖之后，只有红烧肉和冰啤能让他觉得「今天的累值了」。受养生视频信息影响，不是因为不聪明，是因为没有人告诉他哪些保健品是有效的、哪些是浪费钱。他的信息渠道只有手机屏幕那几寸。这不是个人的局限，是信息环境的局限。",
            clothingStyle = ClothingStyle.WORK_GEAR,
            clothingTrait = "工地上只穿劳保服和工装，耐磨耐脏是第一要求。偶尔休息日想穿件「像样」的衣服，但逛了一圈还是买了最便宜的。",
            clothingTownCommentary = "他的劳保服和工地上的水泥一样灰扑扑的。但这身衣服替他挡过钢筋、兜过汗水、在安全绳勒紧的时候没让他磨破皮。他不需要穿得多好看——每一处磨损都是他养家的勋章。",
            birthEnvironment = "深山村落，童年劳作繁重，资源匮乏。",
            growthConfusion = "早早外出务工，从未接触乐器、摄影这类兴趣项目，没有成型爱好，生活轨迹稳定单一，很少畅想未来更多可能性。",
            confusionComment = "环境限制了幼年选择，并非自身缺乏潜力。长年高强度劳作依旧勤恳踏实，待人质朴纯粹，这份特质格外珍贵。",
            innateCondition = "先天体魄强健，家境深山乡村贫困家庭，天赋大众化。",
            leisureStyle = LeisureStyle.MINIMAL,
            leisureTrait = "体力消耗过大，收工后几乎无精力参与线下娱乐。唯一放松就是躺在工棚刷短视频——搞笑视频、直播切片，看到什么刷什么。",
            leisureCommentary = "他的身体已经够累了，不需要再多一种娱乐方式。短视频是一天辛劳后最便宜的放松——不需要出门、不需要花钱、不需要动脑。在连轴转的日子里，能不费力地笑一下，已经是很奢侈的事了。",
            peerAnxiety = "",
            cognitiveLimitation = "觉得办公室工作吹着空调、环境舒适，工作轻松简单。看不到办公室人群长期久坐焦虑、职场内卷、繁杂人事内耗，只凭借外在环境做出简单判断。",
            limitationExplanation = "砖瓦水泥是每天最直观的感受。密闭写字楼里的精神内耗、人际摩擦、KPI焦虑——这些看不见的消耗，和扛水泥是两种不同的累。环境隔绝了彼此的生活，便形成了各自的盲区。"
        ),

        // 6. 小店店主
        "shop_owner" to ConsumerCognition(
            pathId = "shop_owner",
            foodPattern = FoodPattern.BALANCED_CANTEEN,
            foodHabitDescription = "日常饮食规律清淡，食材按需采购。一天到晚守店，做饭时间被压缩，有什么吃什么。",
            consumptionTrait = "对自己吝啬但对店舍得花钱。闲暇时容易被直播特价商品吸引，买一批实用性不高的杂货。",
            trapType = TrapType.LOW_PRICE_TRAP,
            trapDetail = "看直播时被特价吸引，进了货才发现卖不动。店里堆着几箱滞销杂货。自己买衣服也这样——便宜但不太好穿。",
            medicalCognition = "备基础常备药。对养生保健品消费克制，但也缺乏辨别真假的能力。",
            townConsumptionCommentary = "他经营着小店也经营着生活。进货选错、自己买东西选错——这不是眼光的问题，是信息差。小店主没有采购团队帮他比价验货，只能靠自己试错。每一次库存积压的背后，都是一个普通人在信息不对称中付出的代价。这不是他的问题，是环境。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "对自己吝啬，衣服穿坏了才换。但看直播时偶尔会被特价衣服吸引，买回来发现质量一般，懒得退就挂在衣柜里。",
            clothingTownCommentary = "他在店里穿围裙和旧毛衣，不需要穿给谁看。偶尔买下的衣服穿一两次就闲置了——不是因为不爱惜，是因为真的没时间打扮。他的时间和精力都给了这家店。",
            birthEnvironment = "县城长大，生活节奏平缓。",
            growthConfusion = "年轻时尝试过多种工作，最后落脚经营小店，长期守店，偶尔会思考人生还有没有其他可能性，内心偶尔迷茫。",
            confusionComment = "在不断试错后选定当下的生活，迷茫只是内心对于更多生活方式的好奇。待人宽厚、做事稳重，维系小店烟火，便是独有的闪光点。",
            innateCondition = "先天普通体质，家境县城普通家庭，天赋大众化。",
            leisureStyle = LeisureStyle.MINIMAL,
            leisureTrait = "长期守店，几乎没有固定休息日。唯一的休闲是空闲时刷刷短视频，偶尔看看老朋友的朋友圈。",
            leisureCommentary = "他的时间属于这家店。从早到晚守着柜台，偶尔的闲暇碎片化到只能刷几个短视频。不是不想出去玩——是店关一天就少一天收入。",
            peerAnxiety = "",
            cognitiveLimitation = "自主经营习惯了自由支配时间，难以理解体制内严格考勤、层级管理模式。认为按时打卡约束压抑，看不到稳定薪资、社会保障带来的安全感。",
            limitationExplanation = "他的一天自己说了算——开门关门、进货定价，都是自主决策。体制内的层级汇报、审批流程、考勤打卡——这些规矩在自由惯了的人看来是束缚，但同时也是保障。两种生活各有得失，没有谁更正确。"
        ),

        // 7. 全职儿女
        "adult_child" to ConsumerCognition(
            pathId = "adult_child",
            foodPattern = FoodPattern.FAMILY_ORIENTED,
            foodHabitDescription = "依托原生家庭生活，日常饮食随家庭习惯。会专门为爸妈做软烂易消化的菜。",
            consumptionTrait = "个人消费欲低但容易被线上折扣和直播内容影响。网购常有即兴下单，买回来的东西有些用不上。",
            trapType = TrapType.MARKETING_HYPE,
            trapDetail = "照顾爸妈日常开销精打细算。但刷手机时容易被折扣、新款吸引，买回一堆零散物品。",
            medicalCognition = "对爸妈的药比对自己的事还上心。但面对养生类产品营销时，出于「为爸妈好」也容易跟风购买。",
            townConsumptionCommentary = "他的手机购物车里一半是爸妈的药，一半是自己没忍住下单的小东西。他每天在照顾别人，偶尔想给自己一点小小的奖励——哪怕只是一件打折的衣服。照顾别人的人，也需要被照顾。这是最基本的人性需求，不需要任何人来批准。",
            clothingStyle = ClothingStyle.FAMILY_COMFORT,
            clothingTrait = "出门少，对衣服的需求以居家舒适为主。偶尔在网购折扣时即兴下单，买回的衣服有些穿不过来。",
            clothingTownCommentary = "他的衣柜里挂着几件舒服的居家服。没人看他穿什么，他自己也不怎么在意。偶尔在深夜刷到折扣衣服，买回来拆开的一瞬间觉得开心——这份开心，是他照顾爸妈的日子里给自己的小小犒赏。",
            birthEnvironment = "一线城市周边县城，从小可以接触大城市新鲜事物，教育条件尚可。",
            growthConfusion = "长期居住家中，依靠家庭生活，暂时没有稳定职业，不清楚之后要从事什么工作，爱好碎片化，没有长期坚持的方向。",
            confusionComment = "生活节奏松弛，暂时放缓了人生规划节奏。心思细腻，懂得照料家人情绪，在平淡日常里拥有温和的力量。",
            innateCondition = "先天普通体质，家境一线城市周边县城中等家庭，天赋大众化。",
            leisureStyle = LeisureStyle.CREATIVE_COLLECTION,
            leisureTrait = "闲暇时间丰富，购买盲盒、文创周边，参与本地线下漫展。在线上社群和同好交流，偶尔也会冲动消费买下超出预算的限量款。",
            leisureCommentary = "生活节奏松弛，没有职场压力，文创新鲜事物是平淡日常里的小小期待。拆盲盒的那一刻的期待感、漫展上遇到同好的认同感——这些微小的快乐，是他选择的生活方式里的一部分。",
            peerAnxiety = "",
            cognitiveLimitation = "长期依托家庭生活，对社会竞争压力缺乏切身感受。容易低估职场求职的难度和独立生存的压力，将其他人的职业困境简单归结为「不够努力」。",
            limitationExplanation = "他的生活节奏缓慢，没有经历过投简历石沉大海、面试被拒、房租到期的焦虑。缺少切身体验，便容易用自己有限的视角去理解别人的处境。这不是傲慢，是生活环境带来的认知盲区。"
        ),

        // 8. 退休工人
        "retired_worker" to ConsumerCognition(
            pathId = "retired_worker",
            foodPattern = FoodPattern.HEALTH_PLANNING,
            foodHabitDescription = "三餐清淡稳定，习惯粗粮家常菜。饮食理性：知道什么对自己好就吃什么。",
            consumptionTrait = "日常开销理性克制。但面对保健品推销和养生讲座时更容易受影响，是营销信息最容易触及的人群。",
            trapType = TrapType.HEALTH_SCAM,
            trapDetail = "一辈子省吃俭用。但养生讲座上的保健品一买就是几千块。儿女劝了也不听，因为推销员比他儿女陪他的时间还多。",
            medicalCognition = "饮食理性，但缺乏辨别保健品营销信息的能力。不会上网查真假，容易被面对面的热情推销打动。",
            townConsumptionCommentary = "他用几十年学会了怎么省钱，但没有人教过他怎么识别营销话术。推销员的每一句「叔叔阿姨」都是排练过的技巧，而他对身体的担忧和对陪伴的渴望，让他更容易接受这些信息。这不是他不够警惕，是这个社会为老年人提供的信息保护和支持还远远不够。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "不赶时髦，舒服就好。衣服一穿好几年，坏了才换。偶尔儿女给买的新衣服嘴上说浪费，其实心里高兴。",
            clothingTownCommentary = "他的老式布衣和软底布鞋穿了很多年。不是买不起新的，是觉得「还能穿，扔了可惜」。他用了一辈子学到的节俭——这本身是一种值得尊重的品格。偶尔穿上儿女买的新衣服出门遛弯，嘴上不说什么，但比平时多转了一圈。",
            birthEnvironment = "老牌工业城市，时代环境安稳，一辈子遵循工厂节奏生活。",
            growthConfusion = "在职期间人生路径固定，退休脱离岗位之后，一段时间找不到生活重心，需要慢慢适应退休生活。",
            confusionComment = "一辈子遵循既定节奏生活，环境塑造了安稳的生活模式。退休之后慢慢探索闲暇生活，坚守一辈子踏实做事的习惯，沉淀成独有的闪光点。",
            innateCondition = "先天普通体质，家境老牌工业城市普通家庭，天赋大众化。",
            leisureStyle = LeisureStyle.LIVE_STREAMING,
            leisureTrait = "退休后时间充裕，子女忙碌陪伴少。习惯打开手机看生活类主播直播，偶尔小额打赏，填补独居闲暇的孤独感。",
            leisureCommentary = "子女日常忙碌，独处时光漫长。直播间的陪伴填补了空闲时光，小额打赏是换取陪伴感的选择。不是他不懂网络消费——是线下的陪伴不够，线上的陪伴就成了最方便的选择。",
            peerAnxiety = "",
            cognitiveLimitation = "一辈子遵循工厂节奏，习惯稳定明确的职业路径，难以理解当代年轻人的灵活就业、跳槽和职业焦虑。容易将年轻人的迷茫归结为「吃不了苦」，忽略了时代环境已经完全不同。",
            limitationExplanation = "他在一个单位干了一辈子，那个年代的工作是铁饭碗，努力就有回报。现在的年轻人面对的是完全不同的就业市场——灵活用工、35岁危机、行业快速迭代。时代变了，但一个人的经验框架很难同步更新。这不是固执，是时代和环境带来的认知局限。"
        ),

        // 9. 自由职业者
        "freelancer" to ConsumerCognition(
            pathId = "freelancer",
            foodPattern = FoodPattern.HEALTH_PLANNING,
            foodHabitDescription = "收入不稳定但时间自由，可以自主规划三餐。多数人注重高蛋白健康饮食。",
            consumptionTrait = "分清实用消费和审美消费。会为好的设计、好的体验付费，但也可能陷入「提升效率」的设备消费。",
            trapType = TrapType.PREMIUM_SOCIAL,
            trapDetail = "「提高生产力」是最容易让人下单的口号：买了人体工学椅但坐姿还是歪的，买了降噪耳机但还是在刷手机。偶尔为品牌溢价付费，知道溢价了但觉得值。",
            medicalCognition = "具备一定健康意识，有渠道了解科学信息。但也可能过度关注身体信号，有轻微健康焦虑。",
            townConsumptionCommentary = "他拥有了很多人羡慕的自由，但这种自由同时也意味着没有安全网。他的每一笔消费都是在为自己投资——有时候投对了，有时候投偏了。能分清「我需要」和「我被说服需要」之间的界限，需要很多次试错。他已经比大多数人走得远了，偶尔投入多了也是成长的学费。",
            clothingStyle = ClothingStyle.PREMIUM_BRAND,
            clothingTrait = "居家工作时穿得舒服，见客户时注重质感。会为好的面料和设计买单，但偶尔也陷入「提升效率」的消费——买了很多生产力工具，真正用上的没几个。",
            clothingTownCommentary = "他的衣柜里居家服和出街装分得很清。在家一百块的卫衣，出门见人那套值好几千。不是铺张浪费——他买的不是衣服，是一份「我在认真对待自己的事业」的底气。偶尔买贵了也没关系，分清审美消费和情绪消费，需要时间。",
            birthEnvironment = "一线城市，幼年兴趣资源丰富，体验过乐器、绘画等多种爱好。",
            growthConfusion = "职业收入浮动较大，业务不稳定阶段，会焦虑自我规划是否合适，在多种工作模式之间纠结摇摆。",
            confusionComment = "从小选择繁多，反而容易陷入取舍难题。善于独立规划生活、抗压能力强，是长期环境沉淀出的优势。",
            innateCondition = "先天普通体质，家境一线城市优渥家庭，天赋中等偏上。",
            leisureStyle = LeisureStyle.OUTDOOR_ADVENTURE,
            leisureTrait = "时间自由，灵活规划短途出行。不定期参加音乐节、周末骑行露营，在户外运动和演出现场结识志同道合的朋友。",
            leisureCommentary = "自由职业时间灵活，可以错峰出行避开人潮。音乐节和露营不只是娱乐——是他在独处工作一段时间后，重新和世界连接的方式。对他而言，户外不是消费，是充电。",
            peerAnxiety = "",
            cognitiveLimitation = "习惯自主安排时间，难以理解体制内考勤约束和层级管理。认为按时打卡是浪费时间，看不到稳定薪资和社保带来的安全感。偶尔也会低估稳定收入对普通家庭的重要性。",
            limitationExplanation = "他的时间是自己说了算，收入波动大但自由度高。体制内的稳定和保障对自由惯了的人来说像束缚——但对需要养家糊口的人来说，是生活的基本盘。两种生活各有得失，没有谁更正确。"
        ),

        // 10. 基层公务员
        "civil_servant" to ConsumerCognition(
            pathId = "civil_servant",
            foodPattern = FoodPattern.BALANCED_CANTEEN,
            foodHabitDescription = "三餐大多依托单位食堂，饮食均衡稳定。偶尔聚餐应酬，但整体控制良好。",
            consumptionTrait = "消费趋于克制理性。不追风口不买网红，偶尔购入体面的轻奢日用品。",
            trapType = TrapType.NONE,
            trapDetail = "相对理智，圈层消费较少。但也可能为自己的「稳」过度保守，偶尔也想买点好东西——告诉自己，这也是值得的。",
            medicalCognition = "有基本健康意识，单位体检覆盖。但缺乏深度健康规划。",
            townConsumptionCommentary = "他是社会运转的基石——不显眼但不可或缺。他的消费观和他的工作一样：稳、不出格、够用就行。这份稳重是他用多年的规矩换来的。偶尔也想买点好的——那是告诉自己：我也值得拥有一些好东西。每个人都值得——包括那些看起来「没什么欲望」的人。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "深色夹克、白衬衫、黑皮鞋——衣柜里的标准配置。不追款式不买网红，注重得体而非时尚。偶尔给自己买一件轻奢单品，纠结很久才下单。",
            clothingTownCommentary = "他的衣柜和他的人一样：规矩、干净、不出格。不是不想要好东西，是早就习惯了「差不多就行」。偶尔咬牙买一件质感好的大衣，穿了好几个冬天——不是不讲究，是把讲究花在了别人看不见的地方。",
            birthEnvironment = "地级市，教育资源中等，生活安稳。",
            growthConfusion = "入职之后日常琐事繁多，长久重复的工作里，偶尔会思索个人长期发展方向，短暂陷入迷茫。",
            confusionComment = "平稳环境下长期从事细碎工作，容易阶段性产生迷茫。做事严谨细致，处事稳重，就是他身上长久沉淀下来的特质。",
            innateCondition = "先天普通体质，家境地级市中等家庭，天赋中等。",
            leisureStyle = LeisureStyle.TRAVEL_SPRINT,
            leisureTrait = "工作日事务繁琐，周末会选择性打卡短途特种兵旅行。两天之内赶三个景点，回来累得腰酸背痛，但周一上班时觉得上周没有白过。",
            leisureCommentary = "工作日被琐事占满，整块休假时间稀缺。紧凑的短途旅行，是忙碌上班族平衡生活的折中选择——虽然累，但至少为自己攒了一点点新鲜感。",
            peerAnxiety = "",
            cognitiveLimitation = "习惯稳定可控的生活节奏，容易默认所有工作都可以规划安排。看待临时工、流动务工人员时，会片面认为对方工作时间自由，不用打卡约束，忽略务工群体薪资不稳定、社保缺失、收入没有保障、活计随项目波动的生存风险。",
            limitationExplanation = "他的工作按流程走，收入按时到账，社保稳定缴纳。长期处于这种环境，容易默认「稳定」是常态。但务工群体的收入随项目波动、没有社保兜底——这种不安全感，不亲身体验很难理解。环境隔绝了彼此的生活，便形成了理想化的想象。"
        ),

        // 11. 童年时期的自己（v1.3 时光回望支线）
        "childhood_self" to ConsumerCognition(
            pathId = "childhood_self",
            foodPattern = FoodPattern.SURVIVAL,
            foodHabitDescription = "童年家庭开支紧凑，三餐简单朴素。偶尔一瓶柠檬糖水就是最好的犒赏。",
            consumptionTrait = "孩童时期没有独立消费能力，物质欲望简单。最大的消费就是校门口小卖部几毛钱的零食。",
            trapType = TrapType.NONE,
            trapDetail = "童年阶段尚无消费选择权，信息差的影响尚未显现。",
            medicalCognition = "家人备基础常备药。生病了大人带着去诊所，自己不懂也无需操心。",
            townConsumptionCommentary = "童年的你还没有学会「消费」这件事。你的快乐不来自买了什么——一个塑料玩具、一瓶糖水、傍晚一阵凉风，就是全部。这份简单，长大后偶尔会怀念。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "衣服多是亲戚家孩子穿剩下的，偶尔过年买一件新的能高兴很久。不懂什么叫款式，干净就好。",
            clothingTownCommentary = "童年的衣柜里没有「穿搭」这个概念。穿着哥哥姐姐剩下的衣服，袖子卷两圈，裤脚挽三道——不合身，但干净。你不知道什么叫体面，但你从来都体体面面地去上学。",
            birthEnvironment = "乡镇小城，家庭收入普通，物质资源有限。",
            growthConfusion = "孩童阶段受制于家庭条件，没有机会接触各类兴趣课程，不清楚自己的爱好是什么，对长大之后的生活一片茫然，习惯独自消化孤单情绪。",
            confusionComment = "童年的孩子无法自主选择成长环境，环境限制了眼界与选择。孩童时期踏实懂事、安静自愈的特质，是潜藏在你身上长久不变的闪光点，多年之后长大的你，依旧保有这份底色。",
            innateCondition = "先天体质普通，乡镇普通家庭，天赋平常。",
            leisureStyle = LeisureStyle.CHILDHOOD_SIMPLE,
            leisureTrait = "童年时期的潮流小物件：动漫海报贴在床头、卡通贴纸贴在铅笔盒上。放学后在小卖部门口和同学交换贴纸，是那时候最热闹的社交活动。",
            leisureCommentary = "童年没有演唱会、没有盲盒、没有线上社交——但也有一份属于自己的小欢喜。小卖部门口的贴纸交换，是那个年代最纯粹的快乐。简单，但足够温暖一段童年。",
            peerAnxiety = "",
            cognitiveLimitation = "孩童视角，对成年人的世界缺乏完整认知，用简单逻辑理解复杂问题。容易用「好人/坏人」「对/错」的二元框架看待大人的选择，不理解成年人的妥协和无奈。",
            limitationExplanation = "童年的世界是简单的——有对错、有是非、有童话。成年人的世界充满了灰色地带：为了养家做的妥协、为了生活忍的委屈。这些复杂，要长大以后才会懂。这不是孩子的错，是成长阶段带来的必然局限。"
        ),

        // 12. 优渥家境青年（v1.3 先天个体差异）
        "affluent_youth" to ConsumerCognition(
            pathId = "affluent_youth",
            foodPattern = FoodPattern.HEALTH_PLANNING,
            foodHabitDescription = "饮食注重营养搭配，从小接触各类健康食材，口味清淡讲究。偶尔独自外出时也会吃路边摊，体验普通人家的烟火气。",
            consumptionTrait = "消费能力强，注重品质与品牌，但容易陷入圈层消费。偶尔为「彰显身份」购买超出实际需求的高溢价商品。",
            trapType = TrapType.PREMIUM_SOCIAL,
            trapDetail = "为维持社交圈层认同感，在高端餐饮、定制服饰、社交活动上花费不菲。部分消费并非真实需求，而是环境裹挟。",
            medicalCognition = "有完善的体检和健康管理渠道，但长期依赖家庭安排，独立健康管理能力偏弱。",
            townConsumptionCommentary = "富足的物质打开了广阔视野，优渥的出身意味着更早接触优质资源。但从小到大道路大多被家人安排妥当，缺少自主抉择的试错空间。消费上的宽裕不等于精神上的自由——迷茫，是环境束缚之下正常的状态。",
            clothingStyle = ClothingStyle.PREMIUM_BRAND,
            clothingTrait = "衣柜里挂着定制西装和轻奢品牌，质感与设计兼具。偶尔独自逛街时，会买一件便宜的地摊货——不是觉得划算，是想体验一下不按标签活的感觉。",
            clothingTownCommentary = "他的衣服价格不菲，每一件都是精心挑选的。但比起衣服本身，他更在意的是——穿什么才能让家人满意、让圈子认可。偶尔买一件便宜货，不是缺钱，是缺一小块属于自己的自由。",
            birthEnvironment = "一线大城市，家庭经济条件优渥，从小拥有高端教育资源，兴趣课程齐全。",
            growthConfusion = "人生路线长期由家人规划，学业、未来工作早已被安排妥当，缺少自主抉择机会。习惯了优渥物质，脱离家庭庇护后，独立处理琐事能力偏弱，时常迷茫，分不清内心喜好。",
            confusionComment = "旁人羡慕优渥的出身，却看不到层层规划之下个人选择空间被压缩。富足只是起点，内心想要的生活依旧需要慢慢探寻。迷茫不是软弱——是长期遵循他人规划之后，终于开始问自己：我到底想要什么。",
            innateCondition = "先天家境优渥，出生于一线城市富裕家庭，天赋普通但资源丰富。",
            leisureStyle = LeisureStyle.POP_CULTURE,
            leisureTrait = "频繁奔赴各地演唱会、音乐节，预算充足，线下应援打卡。圈层社交依托潮流文娱展开——周末飞另一个城市看演出，周一回来继续上班。偶尔参与之后，内心怀疑自己到底是喜欢音乐，还是只是习惯了在这种场合社交。",
            leisureCommentary = "社交绑定娱乐活动，部分出行并非自身喜爱，只是顺应圈子社交需求。在旁人羡慕的目光里，他偶尔会感到疲惫——不是不喜欢音乐，而是分不清喜欢的是音乐本身，还是被圈子推着走的生活方式。",
            peerAnxiety = "圈层社交带来的隐性攀比——谁去了更小众的音乐节、谁穿的品牌更限量、谁认识更多圈内人。这些看不见的标准，构成了优渥圈层里的另一种内卷。",
            cognitiveLimitation = "物质条件充足，从小道路被规划得清晰，很难真切体会普通人房租、生存开支带来的生存压力。容易将拮据生活归结为个人规划不足，忽视原生家境、开局条件的先天差距。",
            limitationExplanation = "他从小不用为钱发愁，每一笔开销都有家庭兜底。普通人为房租焦虑、为几百块纠结——这些体验不在他的生活经验里。缺少切身体验，便容易用自己有限的视角去理解别人的处境。这不是傲慢，是环境带来的认知局限。"
        ),

        // 13. 天赋高智商青年（v1.3 先天个体差异）
        "gifted_youth" to ConsumerCognition(
            pathId = "gifted_youth",
            foodPattern = FoodPattern.BALANCED_CANTEEN,
            foodHabitDescription = "饮食不规律，专注时忘记吃饭，饿了随手抓点零食。偶尔用高热量食物缓解高强度脑力消耗后的疲惫。",
            consumptionTrait = "实用主义消费为主，但容易在「提升效率」的工具上投入过多。买了大量专业书籍和生产力设备，真正用上的不到一半。",
            trapType = TrapType.PREMIUM_SOCIAL,
            trapDetail = "「提升效率」是最容易让人下单的口号。买了降噪耳机、人体工学椅、各类效率APP会员——但真正能提升效率的，是放下焦虑。",
            medicalCognition = "有较强的自学能力，会主动查阅医学文献。但长期高强度脑力消耗，精神焦虑和睡眠问题被忽视。",
            townConsumptionCommentary = "天赋带来了起步优势，学习新知识的速度远超同龄人。但外界随之而来的高期待，变成无形的压力。所有人只看见天赋的红利，很少顾及长期紧绷带来的疲惫。天赋是馈赠，也是枷锁——你不需要永远优秀才值得被认可。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "对穿搭不讲究，舒服就行。衣柜里清一色的T恤和牛仔裤，偶尔被朋友提醒才买两件像样的。",
            clothingTownCommentary = "他的脑子转得太快，顾不上研究穿搭。别人花时间打扮的时候，他在看书、在解题、在想别人没想过的事情。衣服对他而言只是遮体的布料——他不需要靠外表来证明自己聪明。",
            birthEnvironment = "地级市中产家庭，孩童时期学习领悟速度远超同龄人，从小被师长寄予极高期待。",
            growthConfusion = "从小到大大家默认他必须优秀，失败难以被周围人接纳，长期精神紧绷。快速完成学业任务后，缺少明确目标，容易陷入虚无焦虑。",
            confusionComment = "天赋出众的人，往往也是最孤独的人。旁人只看到他的成绩，看不到他深夜独自焦虑的样子。容错率低不是他的错——是环境把「优秀」当成理所当然。偶尔停下来、慢下来，也是人生的一部分。",
            innateCondition = "先天智力天赋出众，学习领悟速度远超常人，家境地级市中产。",
            leisureStyle = LeisureStyle.MINIMAL,
            leisureTrait = "休闲时间极少，几乎全部投入学习和研究。偶尔放松也是刷科普类短视频——别人刷娱乐，他刷知识。不是不想玩，是脑子里永远有没解决的问题。",
            leisureCommentary = "他的大脑从来没有真正休息过。别人看到的「天才」，是无数个别人在娱乐而他在思考的夜晚换来的。不是他不需要放松——是他的大脑已经被训练得停不下来。",
            peerAnxiety = "从小学到大学，所有人都默认他必须优秀。他不是在和别人比——他是在和「别人眼中的自己」比。这种压力，比任何同辈攀比都更沉重。",
            cognitiveLimitation = "习惯快速理解和解决问题，难以理解普通人学习/工作中的困难。容易低估他人的努力，将他人的困境归结为「不够聪明」或「不够努力」，忽略了智商差异本身就是先天条件。",
            limitationExplanation = "他从小学东西就比别人快，解题、分析、记忆都轻松。这种天赋让他不容易理解——别人花三倍时间还学不会，不是因为不努力，是大脑的工作方式不同。长期处于「轻松掌握」的状态，便容易形成认知盲区。这不是傲慢，是天赋带来的视角局限。"
        ),

        // 14. 先天体魄强健的乡村青年（v1.3 先天个体差异）
        "strong_rural" to ConsumerCognition(
            pathId = "strong_rural",
            foodPattern = FoodPattern.CONVENIENCE,
            foodHabitDescription = "体力消耗大，饮食以高碳水、高蛋白为主，干完活能吃三大碗。不讲究营养搭配，吃饱就行。",
            consumptionTrait = "消费务实，需要什么买什么。偶尔在赶集时被小摊贩的低价物品吸引，买回一堆用不上的工具。",
            trapType = TrapType.LOW_PRICE_TRAP,
            trapDetail = "工具类消费容易被低价吸引，买个便宜锄头用了两天就断柄。但下次还是被价格打动——因为便宜本身就是一种诱惑。",
            medicalCognition = "相信身体底子好，小病小痛扛一扛就过去。对身体的细微预警信号不够敏感，中年以后容易集中爆发健康问题。",
            townConsumptionCommentary = "天生强健的体魄，帮他扛住了乡村繁重的体力劳动。但也正因为身体常年强健，年轻时容易忽视身体细微损耗——这不是大意，是环境给的一种盲区。身体好的人，更容易把健康当成理所当然。",
            clothingStyle = ClothingStyle.WORK_GEAR,
            clothingTrait = "干农活穿耐磨的粗布衣，休息时换件干净的旧T恤。衣服对他来说只有两个功能：保暖和遮体。",
            clothingTownCommentary = "他的衣服上沾着泥土和汗渍，但这身衣服比任何定制西装都更经得起磨损。每一件穿旧的衣服，都是他扛过农忙、撑起生计的见证。他不需要穿得好看——他只需要穿得耐用。",
            birthEnvironment = "山区乡村，天生体力充沛，耐力出众，农活和重体力劳作上手很快。",
            growthConfusion = "年轻时体力充足，经常超负荷干活，不在意腰酸劳损等细微身体预警，习惯透支身体。日常娱乐资源有限，闲暇方式单一，对未来没有太多规划。",
            confusionComment = "先天强健的身体是馈赠，但也容易让人忽略身体的极限。长期透支不是不珍惜自己——是环境教会了他：有力气就得干活，停下来就是浪费。偶尔停下来、听听身体的声音，也是对自己的一种照顾。",
            innateCondition = "先天体魄强健，体力充沛、耐力出众，家境山区乡村贫困家庭。",
            leisureStyle = LeisureStyle.OUTDOOR_ADVENTURE,
            leisureTrait = "闲暇刷短视频时关注户外骑行博主，羡慕那些骑着车翻山越岭的人。条件允许后，计划购置骑行装备，开启户外骑行。在此之前，先在手机上看看别人骑。",
            leisureCommentary = "一辈子和体力活打交道，身体好是他的本能。但骑行不是干活——是他在干活之外，第一次想要为自己做点什么。不是追求时髦，是发现自己的身体除了扛重物，还可以带自己去更远的地方。",
            peerAnxiety = "",
            cognitiveLimitation = "从小体力充沛，难以理解体质较弱者的身体限制。容易认为体弱是「缺乏锻炼」，将身体差异归结为个人不努力，忽略了先天体质本身就是不可自主选择的条件。",
            limitationExplanation = "他扛重物不喘、干一天农活不累，体力好是刻在基因里的。这种天赋让他不容易理解——有些人走几步就喘、干一会儿就腰疼，不是因为懒，是身体的条件不同。长期以自己的身体为标准衡量别人，便容易形成认知盲区。"
        ),

        // 15. 先天肢体残障青年（v1.3 先天个体差异）
        "disabled_youth" to ConsumerCognition(
            pathId = "disabled_youth",
            foodPattern = FoodPattern.BALANCED_CANTEEN,
            foodHabitDescription = "日常饮食以方便为主，优先考虑易获取和易烹饪的食材。偶尔会花时间给自己做一顿用心的饭，当作对自己的犒赏。",
            consumptionTrait = "消费务实理性，注重实用性和性价比。但社会无障碍设施不完善，出行相关的额外开销较多。",
            trapType = TrapType.NONE,
            trapDetail = "消费决策偏理性，但部分辅助器具和出行服务价格偏高，属于社会环境带来的额外负担。",
            medicalCognition = "对自身身体状况有较深了解，日常护理和康复知识丰富。但社会康复资源分布不均，获取专业指导存在客观障碍。",
            townConsumptionCommentary = "先天的身体局限，增加了生活的难度。社会环境的无障碍设施还不够完善，出行、就业都面临额外的阻碍。但为了适配生活而不断坚持——这份坚韧，便是独属于他珍贵的闪光点。不是他需要被怜悯，是这个社会需要变得更好。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "穿着以宽松舒适为主，方便日常活动。不在意款式，但会认真挑选面料的舒适度。",
            clothingTownCommentary = "他对衣服的要求很简单：舒服、方便、不碍事。每一件衣服都是经过仔细挑选的——不是挑款式，是挑穿在身上的感觉。他比任何人都更了解自己的身体，这份细致的觉察，是别人学不来的能力。",
            birthEnvironment = "普通县城，先天肢体存在不便，日常出行、体力劳作存在天然阻碍，部分岗位就业门槛更高。",
            growthConfusion = "出行设施不完善，生活琐事会耗费更多精力，偶尔会承受旁人异样眼光，求职时阻碍较多。在长期克服不便的过程中，心思细腻，善于观察细节，耐心极强。",
            confusionComment = "先天的身体局限，让他在日常生活中付出了更多心力。旁人的异样眼光、社会设施的不足、就业的隐形门槛——这些都不是他个人的问题，是环境需要改善的地方。而他在克服这些障碍的过程中磨炼出的韧性、细腻和耐心，是很多人一辈子都学不会的品质。",
            innateCondition = "先天肢体存在不便，日常出行与体力劳作存在天然阻碍，家境普通县城。",
            leisureStyle = LeisureStyle.POP_CULTURE,
            leisureTrait = "线上追星、观看直播演唱会回放、线上社群交流。出行不便没有阻碍他参与文娱——他通过屏幕看演唱会，在线上社群和同好交流，所有的热爱都在指尖抵达。",
            leisureCommentary = "社会场馆的无障碍设施还不够完善，线下演唱会对他来说意味着额外的挑战。但线上追星给了他同样的参与感——屏幕里的舞台和屏幕外的他，距离并不远。热爱不需要身体到场，心到了就是到了。",
            peerAnxiety = "",
            cognitiveLimitation = "长期应对身体障碍，习惯了克服困难的生活节奏。偶尔会低估健全人面临的精神压力和情绪困境，容易产生「你好手好脚的有什么好焦虑的」的想法，忽略了精神层面的痛苦同样真实。",
            limitationExplanation = "他的日常就是克服障碍——出行、工作、生活，每一件小事都要多花力气。这种长期的「硬扛」模式，让他容易以身体障碍为标尺来衡量别人的困难。但精神层面的痛苦同样是真实的——看不见的伤，也需要被承认。这不是冷漠，是不同的生存经验带来的视角差异。"
        ),

        // 16. 中产原创科研人员（v2.1 科研与技术流通体系）
        "researcher_affluent" to ConsumerCognition(
            pathId = "researcher_affluent",
            foodPattern = FoodPattern.CONVENIENCE,
            foodHabitDescription = "实验忙起来顾不上吃饭，靠外卖、速食、咖啡续命。偶尔周末自己做饭，当作放松。",
            consumptionTrait = "消费集中在专业书籍、实验设备、学术会议差旅。对穿衣吃饭不太讲究，但对研究相关的投入毫不吝啬。",
            trapType = TrapType.NONE,
            trapDetail = "家庭经济兜底，没有生存压力驱动的消费焦虑。但也因此对日常物价、生活成本缺乏敏感度。",
            medicalCognition = "具备基础健康知识，但长期伏案、久坐实验，颈椎腰椎问题突出。对身体的细微预警信号经常忽略——因为实验进度更重要。",
            townConsumptionCommentary = "他不需要为房租发愁，不用计算每顿饭花多少钱。这份从容不是他比别人更聪明——是他的家庭为他挡掉了许多生存压力。长期沉浸在实验室里，他的世界是由数据和论文构成的，有时候会忘记：外面的世界，不是所有人都能像他一样，把全部精力投入一件事。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "常年穿实验室白大褂、简朴T恤，对穿着没有审美追求。衣柜里大都是舒适、耐穿的基本款。",
            clothingTownCommentary = "他的衣服上没有品牌标签，但沾着实验室的试剂味道。这身衣服不需要好看——它需要的是，让他能在实验室里待上十几个小时不觉得累。",
            birthEnvironment = "城市中产家庭，从小教育资源充足，家庭可以兜底漫长的求学周期。",
            growthConfusion = "长期深耕实验室，习惯长远宏观视角，容易忽视短期民生阵痛。新技术落地会带来产业升级，但也会造成流水线工人失业，预判不到底层群体的生存压力。",
            confusionComment = "他看见的是数十年后的产业远景，却很难切身体会底层劳动者当下的失业窘迫。这不是冷漠——是他长期生活在实验室里，不接触流水线工人的日常，环境隔绝了视角。",
            innateCondition = "中产家庭出身，教育资源充足，家庭经济兜底。",
            leisureStyle = LeisureStyle.MINIMAL,
            leisureTrait = "休闲方式单一，周末偶尔散步、听音乐。大部分时间被实验占据，不觉得需要额外的娱乐方式。",
            leisureCommentary = "他的乐趣不在消费上，在于实验数据终于对上预期的那一刻。这种快乐不需要花钱，但需要大量的沉默和耐心。",
            peerAnxiety = "",
            cognitiveLimitation = "长期深耕实验室，习惯长远宏观视角，容易忽视短期民生阵痛。新技术落地会带来产业升级，但也会造成流水线工人大规模失业，预判不到底层群体失业后的生存压力。",
            limitationExplanation = "他从小被家庭兜底，没经历过「不工作就没饭吃」的生存压力。他看见的是技术进步带来的效率提升，看不见的是流水线工人失业后，一家老小等米下锅的焦虑。不是他的错——是环境没有给他机会去体验这一面。"
        ),

        // 17. 寒门应用型科研者（v2.1 科研与技术流通体系）
        "researcher_poor" to ConsumerCognition(
            pathId = "researcher_poor",
            foodPattern = FoodPattern.SURVIVAL,
            foodHabitDescription = "求学阶段靠食堂、泡面、馒头撑过来。工作后依然节俭，能省则省，偶尔才会给自己买点好吃的。",
            consumptionTrait = "消费极度务实，每一笔支出都精打细算。最大的开销是学术资料和科研设备，但优先选择二手或开源替代方案。",
            trapType = TrapType.LOW_PRICE_TRAP,
            trapDetail = "因为预算有限，容易被低价替代品吸引。但科研设备低价往往意味着精度不足，反而增加了实验失败的概率。",
            medicalCognition = "因为经济压力，小病习惯硬扛，拖延体检。对身体的亏欠感强烈，但总觉得「先撑过这个阶段再说」。",
            townConsumptionCommentary = "他用奖学金和助学贷款走完了求学路。读博期间周末做家教、寒暑假打工——每一分钱都要掰成两半花。他不是不想享受生活，是他必须先活下去。这份经历让他比任何人都清楚：技术落地的成本，对普通人意味着什么。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "衣服穿到起球也不换，干净整洁就行。面试时才会穿那套唯一体面的西装——那是他用好几个月省下来的生活费买的。",
            clothingTownCommentary = "他的衣服不贵，但穿在他身上有一种韧劲。每一件穿旧的衣服，都是他靠自己撑过来的见证。",
            birthEnvironment = "县城普通家庭，依靠奖学金和助学贷款完成学业，求学阶段长期背负经济压力。",
            growthConfusion = "亲身经历过拮据生活，会优先考量技术落地后的大众生存成本。但资金有限，项目容易被投资方左右规划，部分研发被迫优先追逐短期盈利。",
            confusionComment = "他比任何人都清楚普通人需要什么——但他没有足够的资金去支撑自己的理想。资本说了算，他只能在夹缝中尽量保持初衷。不是他没有定力，是环境给的选项本就有限。",
            innateCondition = "县城寒门出身，天赋+自律+奖学金走完求学道路。",
            leisureStyle = LeisureStyle.MINIMAL,
            leisureTrait = "几乎没有个人休闲时间。周末在实验室加班，偶尔散步当作休息。",
            leisureCommentary = "他的休闲不是消费——是中场休息。他知道自己不能停下来太久，因为身后没有退路。",
            peerAnxiety = "",
            cognitiveLimitation = "因长期受资金约束，容易高估资本对研发的决定性作用，低估了纯学术自由探索的长远价值。在项目选择上，有时会过度妥协于投资方的短期利益诉求。",
            limitationExplanation = "他经历过没钱的日子，所以对资金的敬畏刻在骨子里。但这份敬畏有时会让他低估了：有一些基础研究，虽然短期看不到回报，但十年后可能改变整个行业。环境给了他生存的紧迫感，也限制了他对「无用之用」的想象力。"
        ),

        // 18. 技术经纪人（v2.1 科研与技术流通体系）
        "tech_broker" to ConsumerCognition(
            pathId = "tech_broker",
            foodPattern = FoodPattern.CONVENIENCE,
            foodHabitDescription = "商务宴请、咖啡厅谈合作是常态，饮食偏社交化。独自吃饭时反而简单应付。",
            consumptionTrait = "消费集中在商务行头、社交场合、差旅出行。注重外在形象，因为这直接影响合作成功率。",
            trapType = TrapType.PREMIUM_SOCIAL,
            trapDetail = "社交圈层消费需求高，为了维护合作关系，经常需要在高档餐厅、酒店消费。这部分支出很大，但属于职业必要成本。",
            medicalCognition = "有体检意识，但长期应酬、饮酒、熬夜，身体处于亚健康状态。知道问题但很难改变——因为改变意味着减少社交。",
            townConsumptionCommentary = "他把实验室的成果带进了工厂，让技术从论文变成了产品。科研人员不擅长卖东西，他补上了这个环节。商业不是原罪——没有商业，再好的技术也只是实验室里的一个数据。",
            clothingStyle = ClothingStyle.PREMIUM_BRAND,
            clothingTrait = "注重穿着，西装、手表是商务标配。不是虚荣——是别人看他的第一眼，决定了合作的第一句话。",
            clothingTownCommentary = "他的西装是工作服，和工人的工装一样。只是他的工地是会议室和谈判桌，工具是谈判技巧和商业判断。",
            birthEnvironment = "城市家庭，商业敏锐度高，善于发现技术价值。",
            growthConfusion = "精通市场盈利逻辑，普遍缺少底层科研原理。有时会催促研发压缩实验周期，牺牲长期技术稳定性换取短期收益。",
            confusionComment = "他看见的是市场需求和盈利空间，看不见的是实验室里反复失败后重新来过的艰辛。他催研发加快进度，是因为市场不等人——不是他急功近利，是两种职业节奏的天然冲突。",
            innateCondition = "城市家庭出身，商业头脑敏锐，善于打通供需两端。",
            leisureStyle = LeisureStyle.POP_CULTURE,
            leisureTrait = "商务社交型休闲，高尔夫、品酒、音乐会。既是放松也是社交。",
            leisureCommentary = "他的休闲和工作很难分开——但这不一定是坏事。当工作本身就是一种创造价值的方式，界限模糊一点也无妨。",
            peerAnxiety = "",
            cognitiveLimitation = "精通市场盈利逻辑，普遍缺少底层科研原理，有时会催促研发压缩实验周期，牺牲长期技术稳定性换取短期收益。",
            limitationExplanation = "他习惯了用商业语言衡量一切——ROI、周期、回报率。但科研不是一门生意，有些突破需要时间，不能用金钱衡量。两种职业语言不同，分歧不是谁对谁错，是视角不同。"
        ),

        // 19. 学阀圈层内部人员（v2.2 学术垄断体系）
        "academic_insider" to ConsumerCognition(
            pathId = "academic_insider",
            foodPattern = FoodPattern.CONVENIENCE,
            foodHabitDescription = "实验室微波炉热饭是标配，咖啡是燃料。饮食极度不规律，往往到深夜实验收工才想起还没吃晚饭。",
            consumptionTrait = "消费集中在学术会议差旅、专业书籍、实验耗材。对日常物价感知迟钝——因为从未为房租发过愁。",
            trapType = TrapType.NONE,
            trapDetail = "圈层内部的资源分配机制对他来说是空气——自然而然，不需要去抢。也因此难以感知圈外人为争取经费所付出的巨大心力。",
            medicalCognition = "长期伏案，颈椎腰椎问题突出。但实验室不缺医疗资源——圈层内的医疗保障远好于普通人群。",
            townConsumptionCommentary = "他从读博那天起就没为经费发过愁——导师是学阀核心，设备、数据、期刊渠道一应俱全。他不需要去理解圈外科研者为什么举步维艰，因为他从未在那样的环境里待过。这不是傲慢，是环境给的盲区。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "实验室白大褂是常服。衣柜里偶尔有几件体面的衬衫——只在参加国际学术会议时才穿。",
            clothingTownCommentary = "他的白大褂上别着实验室的门禁卡，那张卡比任何名牌都值钱。它意味着可以凌晨两点还留在实验室跑数据，意味着不需要和任何人解释「为什么你还在」。",
            birthEnvironment = "中产/富裕家庭，家族学术人脉深厚，博士阶段已确定派系归属。",
            growthConfusion = "长期身处顶层学术圈层，默认现有资源分配模式合理，很难感知外部科研人员的资源困境。习惯维护既有圈层利益，将派系规则视为学术秩序。",
            confusionComment = "他看见的是学术规范和效率，看不见的是同一个规则下，有多少优秀人才被挡在门外。圈层不是他建的——但他身处其中，自然而然地在维护它。这不是他存心排外，是环境把他放在了一个看不到外面的位置。",
            innateCondition = "中产家庭出身，家族学术人脉深厚，圈层资源丰沛。",
            leisureStyle = LeisureStyle.MINIMAL,
            leisureTrait = "休闲几乎不存在。偶尔深夜实验收工后，听一段古典音乐当作放松。",
            leisureCommentary = "他的生活被实验室精确切割成小时——不是在工作，就是在准备下一轮实验。但他并不觉得这是牺牲，因为做自己喜欢的研究，本身就是一种奖赏。",
            peerAnxiety = "",
            cognitiveLimitation = "长期身处顶层学术圈层，默认现有资源分配模式合理，很难感知外部科研人员的资源困境。习惯维护既有圈层利益，将派系规则视为学术秩序，将外部人才无法进入核心项目归结为个人能力不足。",
            limitationExplanation = "他从读博那天起就在学阀派系内部——设备、经费、期刊渠道都是现成的。他从未体验过「自掏腰包买试剂」「论文被拒因为没有大佬推荐信」的处境。这不是他冷酷——是他的环境从未让他看见那些被挡在外面的人。圈层给了他最好的学术起点，也遮住了他看向圈外的视线。"
        ),

        // 20. 程序员
        "programmer" to ConsumerCognition(
            pathId = "programmer",
            foodPattern = FoodPattern.CONVENIENCE,
            foodHabitDescription = "工位上的外卖凉了才想起来吃，凌晨收工后靠重油宵夜填补空虚。饮食被项目进度切割得支离破碎，胃早已习惯了在代码间隙里找时间填饱自己。",
            consumptionTrait = "电子设备、机械键盘、人体工学椅、效率APP会员——为「提升效率」和「缓解久坐痛苦」持续投入，花起钱来不手软。",
            trapType = TrapType.PREMIUM_SOCIAL,
            trapDetail = "买了三把机械键盘发现手感差异并没有想象中大，人体工学椅到位了但坐姿还是歪的，效率APP开了一堆真正用的没几个。每一次「提升生产力」的消费，都是对自己疲惫的一种温柔补偿。",
            medicalCognition = "颈椎腰椎问题自己查资料、买护颈枕，但长期用眼和睡眠不足被「等项目上线再说」无限期推迟。身体发出的信号，总被排期表覆盖。",
            townConsumptionCommentary = "他的时间被需求文档和代码切割成碎片。买机械键盘和人体工学椅——不是虚荣，是久坐十几个小时后身体真的在呼救。每一次为「效率」付费，都是对自己疲惫的温柔补偿。没有人告诉他怎样才算「照顾好自己的身体」，他只能在深夜的购物软件里，用消费来模拟一份被照顾的感觉。这不是错，是信息环境里「关怀自己」的方式太单一了。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "衣柜里清一色的公司文化衫、连帽卫衣和旧牛仔裤。不讲究搭配，舒服就行。偶尔买件潮牌，穿两天还是换回旧T恤。",
            clothingTownCommentary = "他的衣服不需要 impress 任何人——除了编译器。文化衫和连帽卫衣是程序员的制服，不是审美懒惰，是把注意力花在了他觉得更重要的地方：代码逻辑、系统架构、bug 修复。偶尔买件潮牌穿两天又换回旧T恤——说明他试过，只是发现还是舒服最重要。",
            birthEnvironment = "普通城镇，从小接触电脑，理工科教育背景，习惯与机器打交道。",
            growthConfusion = "35岁危机悬在头顶，技术迭代快，担心自己的技能过时。除了写代码，似乎没有其他能拿得出手的竞争力。",
            confusionComment = "大厂的光环和高薪背后，是持续学习压力和年龄焦虑。能把复杂逻辑拆解成代码，本身就说明他有极强的抽象思维能力——这份能力，不只属于互联网，也属于他自己。",
            innateCondition = "先天普通体质，家境城镇普通家庭，逻辑思维能力较强。",
            leisureStyle = LeisureStyle.MINIMAL,
            leisureTrait = "下班后几乎无精力参与线下娱乐。偶尔刷刷技术博客或短视频，手机是唯一的放松工具，刷着刷着就睡着了。",
            leisureCommentary = "他的大脑在白天已经被代码占满，下班后不需要再输入任何信息。刷手机不是娱乐，是大脑的关机流程。不是不想出去玩——是连轴转之后，连「规划一次出行」的精力都没有了。",
            peerAnxiety = "35岁危机、同龄人升职加薪带来的横向对比焦虑。不是不优秀，是这个行业把「年轻」当作了一种消耗品。",
            cognitiveLimitation = "习惯用逻辑和效率衡量一切，难以理解体制内按部就班的工作节奏。容易认为「稳定」等于「混日子」，忽略不同职业选择的合理性和各自的代价。",
            limitationExplanation = "他的世界由代码和效率构成——输入、处理、输出，每一步都可以优化。但体制内的稳定、教师寒暑假的意义、自由职业者的时间焦虑，这些不能用算法复杂度来衡量。长期处于「效率至上」的环境，便容易用自己唯一的标尺去丈量所有人的选择。"
        ),

        // 21. 产品经理
        "product_manager" to ConsumerCognition(
            pathId = "product_manager",
            foodPattern = FoodPattern.CONVENIENCE,
            foodHabitDescription = "会议排满全天，盒饭在工位上凉透了才想起来吃。胃已经习惯了不规律的投喂时间，咖啡是唯一的饮食节奏标记。",
            consumptionTrait = "商务休闲装、咖啡、效率工具。为「看起来更专业」和「撑过漫长会议」持续投入，每一笔开销都藏着「不能输」的紧绷。",
            trapType = TrapType.EMOTIONAL_PURCHASE,
            trapDetail = "连续开了八个会之后，下单了三件商务衬衫和一堆据说能提升专注力的效率工具。第二天醒来发现有些东西并不急需——但下单的那一刻，确实让自己感觉重新掌控了一点生活。",
            medicalCognition = "精神压力大，焦虑失眠，胃不好。知道应该看医生，但总觉得「等项目上线再说」。靠咖啡和褪黑素硬撑，身体在默默记账。",
            townConsumptionCommentary = "她的日程表上没有「吃饭」这个优先级，只有「待办事项」。咖啡不是品味，是续命工具；商务衬衫不是虚荣，是会议室里必须披上的盔甲。每一次疲惫后的下单，都是在高压环境里给自己找一个喘息的出口。不是不理性——是理性已经全部花在了协调十个部门的需求上，留给自己的部分，只剩冲动。这份冲动，是她在照顾自己。",
            clothingStyle = ClothingStyle.PREMIUM_BRAND,
            clothingTrait = "注重穿搭的「专业感」，西装外套和休闲裤是标配。不是追求奢侈，是会议室里的第一印象直接决定话语权。偶尔买贵一点的衣服，告诉自己「这是对工作的投资」。",
            clothingTownCommentary = "她的外套不是穿给自己看的，是穿给会议室里的所有人看的。在互联网行业，「看起来靠谱」和「真的靠谱」同样重要。她不是在买衣服，是在买一份「我能 hold 住这个场子」的底气。",
            birthEnvironment = "城市或城镇，从小善于沟通表达，成长过程中习惯了协调多方关系。",
            growthConfusion = "协调多方需求久了，有时会怀疑产品的价值感。永远在推进别人的想法，不清楚真正属于自己的判断是什么。",
            confusionComment = "在纷繁复杂的需求中穿梭，容易被各方声音淹没。但能把混乱的需求梳理成清晰的路线图，这份沟通和统筹能力，本身就是一种稀缺的天赋。",
            innateCondition = "先天普通体质，家境城镇普通家庭，沟通协调能力较强。",
            leisureStyle = LeisureStyle.MINIMAL,
            leisureTrait = "加班到深夜，下班后只想瘫在床上刷几分钟短视频放空。周末偶尔有整块时间，但身体已经习惯了工作节奏，反而不知道该怎么休息。",
            leisureCommentary = "她的时间被切割成十五分钟的会议碎片，连休闲都习惯了这种碎片感。刷短视频不是因为喜欢，是因为大脑已经不会处理「整块时间」了。不是不想好好休个假——是就算放假，脑子也在跑需求文档。",
            peerAnxiety = "同职级竞争、KPI对比带来的横向焦虑。每个人都跑得很急，她也不敢慢下来。",
            cognitiveLimitation = "习惯以「用户价值」和「数据指标」衡量一切，难以理解技术人员的实现成本和创作人员的非标准化工时。容易低估写代码和做内容的实际难度，把落地问题简单归结为「资源不够」。",
            limitationExplanation = "她的日常工作围绕需求文档和数据漏斗展开，习惯了把复杂问题拆解成可执行的节点。但代码的架构设计、创作的内容灵感——这些无法被甘特图规划的工作，超出了她的日常经验。长期处于「流程化管理」的环境，便容易用自己的框架去框定所有人的产出。"
        ),

        // 22. 公立教师
        "public_teacher" to ConsumerCognition(
            pathId = "public_teacher",
            foodPattern = FoodPattern.BALANCED_CANTEEN,
            foodHabitDescription = "日常在学校食堂解决三餐，清淡规律。寒暑假回家会特意为家人做几顿好饭，把省下的精力花在厨房。",
            consumptionTrait = "实用主义，家庭导向，为学生花钱不犹豫。自己的东西能将就，但教辅资料和学生用品从不手软。",
            trapType = TrapType.NONE,
            trapDetail = "消费相对理性，但面对「学生需要」的情境时，容易忽视性价比。不是信息差，是「为人师」的责任感自然溢出的消费倾向。",
            medicalCognition = "慢性咽炎、静脉曲张、长期站立带来的职业病有认知，但觉得「当老师都这样」，习惯忍耐而非主动就医。",
            townConsumptionCommentary = "她的收入不高，但每一分都花得踏实。为学生买教辅资料时眼睛都不眨，给自己买件衣服却要货比三家。这不是牺牲，是她选择了一种「被需要」的生活方式。实用主义的背后，是对「够用就好」的满足，也是对他人需求的优先照顾。这种消费观不需要被「升级」，它本身就是一种成熟。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "穿搭以得体大方为主，不追时髦，干净整齐就好。偶尔家长会前会特意换件更正式的衣服——不是虚荣，是「不能给学生丢脸」。",
            clothingTownCommentary = "她的衣服没有logo，但每一件都干净整洁。站在讲台上，学生们不会注意她穿什么，但她依然保持着一份体面。这份体面不是穿给自己看的，是穿给那几十双眼睛看的——她用自己的样子，告诉学生「认真对待每一天」。",
            birthEnvironment = "普通城镇，成长环境安稳，从小擅长学习和表达，受到长辈喜爱。",
            growthConfusion = "工作稳定但重复性高，偶尔会想自己的人生价值是否仅限于这一方讲台。看到同龄人收入更高时，会有短暂恍惚。",
            confusionComment = "稳定的环境容易让人产生「是不是该出去闯一闯」的疑问。但能在日复一日的教学中保持耐心，把知识传递给一代又一代学生，这份坚持本身就是极珍贵的价值。",
            innateCondition = "先天普通体质，家境城镇普通家庭，亲和力和表达能力较好。",
            leisureStyle = LeisureStyle.TRAVEL_SPRINT,
            leisureTrait = "寒暑假是全年最期待的整块时间，会提前规划带家人短途旅行。平日下班后疲惫，几乎无额外休闲。",
            leisureCommentary = "她的时间被上课、备课、批改作业填满，唯一的整块自由是寒暑假。那几天短暂的旅行，是她为自己攒下的年度补给。不是不想经常出去玩——是平时真的走不开。",
            peerAnxiety = "",
            cognitiveLimitation = "长期处于校园环境，习惯标准化的教学进度和明确的评分体系。看待其他职业时，容易以「稳定」为标尺，难以理解自由职业者的收入焦虑和互联网行业的35岁危机。",
            limitationExplanation = "她的工作环境单纯，收入按时到账，假期规律。自由职业者下个月有没有单子、程序员35岁后还能不能找到工作——这些不确定性不在她的日常经验里。长期处于「可预期」的环境，便容易把「稳定」当作衡量所有人处境的默认标准。"
        ),

        // 23. 国企职员
        "state_employee" to ConsumerCognition(
            pathId = "state_employee",
            foodPattern = FoodPattern.BALANCED_CANTEEN,
            foodHabitDescription = "单位食堂三餐规律，口味清淡适中。偶尔和同事聚餐，也是选择稳妥的本地菜馆。",
            consumptionTrait = "稳健保守，注重性价比，福利依赖。工资不高但福利覆盖广，消费时习惯先看有没有单位团购或工会福利。",
            trapType = TrapType.NONE,
            trapDetail = "消费克制理性，单位福利覆盖了不少日常需求。但也因此对市场化商品的性价比判断偏弱，偶尔在福利覆盖不到的领域吃亏。",
            medicalCognition = "基础体检每年按时做，慢性病管理意识有，但觉得「小问题不用管」，心态平和到有些拖延。",
            townConsumptionCommentary = "他的工资条上的数字不算耀眼，但背后的福利和稳定性是很多人羡慕不来的。消费保守不是小气，是长期「按部就班」的生活训练出来的——每一分钱都要花在刀刃上，因为增量不多，所以存量格外重要。他依赖福利，不是懒惰，是合理利用了体制给予的安全感。这种消费观，是环境塑造的智慧。",
            clothingStyle = ClothingStyle.UTILITY_PLAIN,
            clothingTrait = "深色夹克、白衬衫、黑皮鞋——和基层公务员类似的标配。不追求时髦，但要「不出错」。偶尔买件好衣服，只在重要场合穿。",
            clothingTownCommentary = "他的衣柜和他的人一样：规矩、稳妥、不冒尖。不是买不起更好的，是「合适」比「好看」更重要。在一个讲究按部就班的环境里，穿得太出挑反而是一种负担。他的朴素，是对环境的一种得体适应。",
            birthEnvironment = "普通城市或城镇，成长环境安稳，家庭观念传统。",
            growthConfusion = "按部就班的日子久了，偶尔会羡慕别人的波澜壮阔。但真要他辞职闯荡，心里又会不安。",
            confusionComment = "在安稳中偶尔泛起涟漪，是人之常情。能把平凡的日子过得有条不紊，把家人照顾妥当，这份稳稳托住生活的能力，本身就是一种难得的本事。",
            innateCondition = "先天普通体质，家境城镇普通家庭，性格稳健踏实。",
            leisureStyle = LeisureStyle.TRAVEL_SPRINT,
            leisureTrait = "周末偶尔和朋友短途出游或钓鱼，平时下班后习惯看看电视剧或新闻。生活节奏缓慢而有规律。",
            leisureCommentary = "他的休闲不需要刺激，平稳本身就是一种享受。周末钓钓鱼、看看剧，是他为自己划定的生活缓冲区。不是不羡慕别人的精彩——是他知道，自己更想要的是可预期的幸福。",
            peerAnxiety = "",
            cognitiveLimitation = "长期处于稳定可预期的工作环境，习惯以「稳」为首要标准。看待创业者和自由职业者时，容易将其收入波动和风险归结为「不够踏实」，忽略不同选择背后的机遇和代价。",
            limitationExplanation = "他的工资按月到账，年终奖准时发放，几十年来一直如此。创业失败的亏损、自由职业断单的压力——这些体验不在他的生活轨迹里。长期处于「稳定即正确」的环境，便容易把「求稳」当作唯一的理性选择。"
        ),

        // 24. 内容创作者
        "content_creator" to ConsumerCognition(
            pathId = "content_creator",
            foodPattern = FoodPattern.CONVENIENCE,
            foodHabitDescription = "创作灵感来时忘记吃饭，没灵感时靠外卖和速食打发。收入微薄时，一碗泡面也能撑过一顿。",
            consumptionTrait = "设备投入大，相机、麦克风、灯光、剪辑软件。社交应酬少，节俭与冲动并存——省下的饭钱可能转眼买了新镜头。",
            trapType = TrapType.PREMIUM_SOCIAL,
            trapDetail = "每掉一百个粉丝就忍不住想「是不是设备不够好」，于是下单新镜头、新麦克风。设备堆了一屋子，但真正打动观众的内容，从来不是因为镜头更贵。",
            medicalCognition = "作息不规律，用眼过度。知道该休息，但数据好的夜晚舍不得睡，数据差的夜晚焦虑得睡不着。",
            townConsumptionCommentary = "他的收入像过山车，设备支出却像直线上升的台阶。每一台新相机背后，都藏着一个「是不是我不够好」的自我怀疑。节俭的时候吃泡面，冲动的时候买镜头——这种矛盾不是人格分裂，是创作者在「投入」和「产出」之间找不到确定感的正常反应。没有人告诉他：好的内容，从来不取决于设备的参数。",
            clothingStyle = ClothingStyle.CHEAP_FAST_FASHION,
            clothingTrait = "出镜时需要看起来体面，但预算有限，常买低价仿款或网红同款。镜头前光鲜，镜头后换回旧T恤。",
            clothingTownCommentary = "他的衣服分为两类：出镜时穿的「镜头款」和平时穿的「真实款」。低价仿款在镜头前打光后看起来不错——观众不会知道标签是什么。这不是虚荣，是创作者在有限预算里，为自己争取的一点专业感。",
            birthEnvironment = "普通城镇，从小喜欢表达和创作，通过网络接触到外面的世界。",
            growthConfusion = "数据波动直接影响自我价值感。今天爆款开心，明天无人问津就怀疑自己。不确定这条路能走多久。",
            confusionComment = "在数据和算法的海洋里漂浮，情绪被流量裹挟是创作者的常态。但愿意把想法做成内容分享出来，本身就说明他有表达的勇气和创作的热情——这份勇气比任何数据都更持久。",
            innateCondition = "先天普通体质，家境普通家庭，表达欲和创造力较强。",
            leisureStyle = LeisureStyle.SHORT_VIDEO_ONLY,
            leisureTrait = "闲暇时间也在刷短视频找灵感，看着同行的数据心生焦虑。偶尔关掉手机想休息，但手指已经形成了肌肉记忆。",
            leisureCommentary = "他的手机和他是连体婴——既是生产工具，也是唯一的娱乐渠道。刷短视频找灵感的时候，也在被动接收着同辈的成功案例。不是不想彻底断开——是断开了就不知道外面在流行什么，而这种「不知道」本身就是一种焦虑。",
            peerAnxiety = "数据对比、同行涨粉带来的焦虑。同辈创作者的数据波动直接冲击自我价值感。",
            cognitiveLimitation = "沉浸在流量逻辑里，习惯以「曝光量」和「转化率」衡量价值。难以理解体制内「没有数据反馈」的工作意义，容易把稳定工作等同于「没有影响力」。",
            limitationExplanation = "他的世界由播放量、点赞数和粉丝增长曲线构成，每一小时都有数据反馈。但教师的成就感来自十年后的学生回忆、公务员的价值来自政策落地的长期影响——这些无法被实时量化的价值，在他的经验框架里很难被看见。长期处于「即时反馈」的环境，便容易把「没有数据」等同于「没有价值」。"
        )
    )

    fun getCognition(pathId: String): ConsumerCognition? = cognitions[pathId]
}