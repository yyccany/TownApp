package com.example.townapp.data

/**
 * 孩童行为场景系统（v1.7 原生本能 × 成年疲惫 镜像对照）
 *
 * ============================================
 * 整体架构：两类孩童，分轨运行，互不混淆
 * ============================================
 *
 * 一、临时场景 NPC 孩童（短期触发型，优先落地）
 *   - 他人的孩子：邻居家小孩、街坊孩童、社区玩伴
 *   - 只在触发事件时登场，事件结束回归场景背景
 *   - 无完整人设、无成长线、无长期跟随
 *   - 用途：推动成年人主角的情绪和心态微变化
 *   - 适用场景：全职妈妈居家育儿、务工社区邻里孩童、小区玩耍儿童
 *   - 对应本文件：ChildBehavior / ChildBehaviorScene / ChildBehaviorScenes / IdentityChildReactions
 *
 * 二、童年主线自我（玩家可操控的完整人生路线）
 *   - 十年前小时候的自己，属于完整人生路线
 *   - 具备全套体系：专属物品、时序变化、四段文案、成长困境、先天参数
 *   - 童年选择持续影响成年后的性格、消费习惯、心理状态
 *   - 对应已有系统：LifePathSimulator 中的 childhood 路径
 *   - 对应本文件：ChildType.PLAYER_CHILDHOOD（仅做标记，不混入临时场景逻辑）
 *
 * 三、未来拓展（长线）：
 *   新增多条孩童主线，对应不同出身：
 *   - 城市富裕家庭小孩、县城普通孩童、大山乡村孩童、先天残障孩童
 *   - 从童年开启人生模拟，经历升学、择业，成长至青年、中年
 *   - 童年经历完整映射成年后的认知局限、消费观念、爱好偏好
 *
 * ============================================
 * 核心命题：孩童遵从本能心意，成年人优先计较损失。
 * 繁重的生活压力，慢慢改变了人本能的处事方式，疲惫消磨了一部分原生的温柔。
 *
 * 设计原则：
 * - 不简单评判大人发火是错误，而是剖析情绪背后的处境成因
 * - 孩童两种行为（善意捡拾 / 调皮捣乱）随机触发，没有固定模板
 * - 不同人设面对同一场景，情绪反应各有差异
 * - 长期经历中，年轻时的急躁会慢慢沉淀为中年后的平和
 * ============================================
 */

// ============================================
// 一、孩童角色类型（架构级区分）
// ============================================

/**
 * 孩童角色类型 —— 临时场景 NPC 还是玩家主线角色
 */
enum class ChildType(val label: String, val description: String) {
    /** 临时场景 NPC：他人的孩子，短期事件交互，无完整成长线 */
    TEMP_NPC(
        "临时场景孩童",
        "邻居家小孩、街坊孩童，事件触发时登场，事件结束回归背景。无完整人设，不长期跟随。"
    ),
    /** 童年主线自我：玩家可操控，完整人生周期，童年选择决定往后数十年 */
    PLAYER_CHILDHOOD(
        "童年主线自我",
        "十年前小时候的自己。完整人生路线，具备全套体系。童年选择持续影响成年后的一切。"
    )
}

/**
 * 临时 NPC 孩童来源类型
 *
 * 不同来源的临时孩童适配不同的人设和场景。
 */
enum class TempChildNpcType(val label: String, val typicalScene: String) {
    /** 邻居家小孩：全职妈妈居家育儿、务工社区中最常见 */
    NEIGHBOR_KID("邻居家小孩", "家居场景、社区闲聊"),
    /** 街坊孩童：街边、社区公园常见 */
    STREET_KID("街坊孩童", "街边场景、傍晚散步"),
    /** 社区玩伴：节假日、小区活动常见 */
    COMMUNITY_KID("社区玩伴", "节假日、社区活动")
}

/**
 * 未来可拓展的孩童主线出身类型（长线规划，暂不实现）
 */
enum class ChildLifePathOrigin(val label: String, val description: String) {
    RICH_CITY("城市富裕家庭", "物质充裕，教育资源丰富，从小规划清晰。成年后可能缺乏对底层生存压力的切身理解。"),
    COUNTY_ORDINARY("县城普通家庭", "生活节奏适中，资源一般。童年朴实，成年后北上广打拼或返乡定居，两种路径各有得失。"),
    MOUNTAIN_VILLAGE("大山乡村", "物质匮乏，早当家。幼年跟长辈干农活，闲暇山间玩耍。长大后外出务工，幼年习惯影响成年后的休闲方式与抗压能力。"),
    CONGENITAL_DISABLED("先天残障孩童", "从小面对身体障碍，克服困难是日常。童年经历塑造出远超常人的韧性和独特的视角。")
}

// ============================================
// 二、临时场景 NPC 相关枚举（成年人视角，与别人家孩子互动）
// ============================================

/** 孩童行为类型 */
enum class ChildBehavior(val label: String) {
    HELP_PICK_UP("主动弯腰捡拾"),
    PLAYFUL_MISCHIEF("调皮拨弄玩耍")
}

/** 散落物品类型 */
enum class ScatteredItemType(
    val label: String,
    val typicalLossCost: Double,       // 典型损耗金额
    val typicalCleanupMinutes: Int     // 典型收拾时间
) {
    FOOD_FRUIT("水果", 15.0, 40),
    SMALL_OBJECTS("零碎小物件", 5.0, 30)
}

/** 玩家（成年人）应对方式 */
enum class PlayerChildResponse(val label: String, val description: String) {
    SCOLD("当场发火斥责", "疲惫叠加突发琐事，烦躁冲口而出"),
    PATIENT_TALK("耐心沟通", "按下情绪，先看见孩子那份纯粹"),
    SILENT_CLEANUP("沉默独自收拾", "不责怪也不说教，默默把东西收拾好")
}

// ============================================
// 三、临时场景配置（成年人 × 临时 NPC 孩童）
// ============================================

/**
 * 孩童行为场景配置
 *
 * 四段式结构：体感反馈 → 闪光点 → 时薪成本锐评 → 小镇评述
 */
data class ChildBehaviorScene(
    val id: String,
    val behavior: ChildBehavior,
    val itemType: ScatteredItemType,
    val itemName: String,
    /** 临时孩童类型（邻居/街坊/社区） */
    val npcType: TempChildNpcType = TempChildNpcType.STREET_KID,
    /** 场景画面描述 */
    val sceneImage: String,
    /** 额外收拾时间（分钟），调皮行为会翻倍 */
    val extraCleanupMinutes: Int,
    /** 实际损耗金额 */
    val actualLossCost: Double,

    // ---- 四段式结构 ----
    val bodyFeeling: String,
    val sparkle: String,
    val workHourCheck: String,
    val townCommentary: String
)

// ============================================
// 四、预置场景库
// ============================================

object ChildBehaviorScenes {

    val scenes: List<ChildBehaviorScene> = listOf(

        // ---- 场景1：荔枝散落，孩童主动捡拾（邻居小孩） ----
        ChildBehaviorScene(
            id = "litchi_help",
            behavior = ChildBehavior.HELP_PICK_UP,
            itemType = ScatteredItemType.FOOD_FRUIT,
            itemName = "荔枝",
            npcType = TempChildNpcType.NEIGHBOR_KID,
            sceneImage = "一盒荔枝从手上滑落，红色果子滚了一地。你还没来得及叹气，低头看见邻居家小孩已经蹲在地上，一颗一颗把荔枝拢回盒子。",
            extraCleanupMinutes = 0,  // 孩子帮忙反而省了时间
            actualLossCost = 12.0,    // 几颗摔坏的

            bodyFeeling = "满地滚落的荔枝，原本心头一阵烦躁。低头看见小孩蹲在地上，小小的手一颗颗收拢果子，心头的火气忽然被压了下去。",
            sparkle = "孩子出于本能释放善意，没有考虑麻烦与损耗，只是看见东西掉了就想帮忙捡起来。这份纯粹的、不掺杂任何功利考量的心意，猝不及防地唤醒了你搁置已久的柔软。",
            workHourCheck = "整理散落水果本需要约40分钟，摔坏的果子折合几块钱损耗。损失客观存在，但孩子帮你省下了一半的收拾时间。",
            townCommentary = "日复一日的生计琐事，让我们习惯优先盘算损耗、麻烦与代价。压力久了，急躁便成了常态。孩童出于本心的善意，短暂打破了成年人固化的处事思维，让人停下脚步，反思自己当下的情绪。损失确实存在，可眼前这份纯粹，同样值得被看见。"
        ),

        // ---- 场景2：乒乓球散落，孩童调皮拨弄（街坊孩童） ----
        ChildBehaviorScene(
            id = "pingpong_mischief",
            behavior = ChildBehavior.PLAYFUL_MISCHIEF,
            itemType = ScatteredItemType.SMALL_OBJECTS,
            itemName = "乒乓球",
            npcType = TempChildNpcType.STREET_KID,
            sceneImage = "乒乓球箱子倾覆，几十颗小球哗啦啦散落一地。街边玩耍的小孩眼睛一亮，觉得这些弹跳的小球新奇极了，伸手拍打，小球弹得更远、滚得更散。",
            extraCleanupMinutes = 30,  // 捣乱翻倍工作量
            actualLossCost = 3.0,      // 几个踩扁的

            bodyFeeling = "忙活许久才规整好的物件被打乱，一堆小球四散各处，孩子还在咯咯笑着拍打。当下疲惫烦躁涌上心头，下意识想要呵斥孩子。",
            sparkle = "孩童只是被弹跳的小球吸引，出于玩耍本能行动。在他的世界里，没有「收拾」和「时间成本」的概念——只有眼前这些蹦蹦跳跳的小球，和他控制不住的好奇心。并非存心添乱，只是天性使然。",
            workHourCheck = "原本收拾需要30分钟，经过孩子一番扰动，整理时长翻倍成了1小时。额外消耗的这30分钟，是你本可以用来休息的时间。",
            townCommentary = "成年人背负着任务、时间、生活开销带来的压力，规划好的节奏被打乱，烦躁是疲惫累积后的本能反应。孩子还不懂成人世界里时间成本、劳作代价——二者所处的处境完全不同，冲突本就源于环境差异。事后冷静下来，人便会慢慢懂得：孩子不是故意的，只是还没长大。"
        )
    )

    fun findById(id: String): ChildBehaviorScene? = scenes.find { it.id == id }

    /** 随机抽取一个场景 */
    fun random(): ChildBehaviorScene = scenes.random()

    /** 按 NPC 类型筛选场景 */
    fun findByNpcType(npcType: TempChildNpcType): List<ChildBehaviorScene> {
        return scenes.filter { it.npcType == npcType }
    }
}

// ============================================
// 五、不同人设面对临时孩童的反应差异
// ============================================

/**
 * 不同人设面对临时 NPC 孩童场景的情绪倾向
 *
 * 不评判哪种反应更好，只描述不同处境下的自然反应。
 * 注意：此配置仅适用于成年人角色与临时 NPC 孩童的互动。
 * childhood_self 为特殊标记——代表玩家切换为孩童视角，不参与此互动逻辑。
 */
data class IdentityChildReaction(
    val pathId: String,
    val pathName: String,
    /** 此身份在此场景中的角色类型 */
    val childType: ChildType,
    /** 初始烦躁值（0-100，越高越容易急躁）。仅 ChildType.TEMP_NPC 生效。 */
    val baselineIrritability: Int,
    /** 反应倾向描述 */
    val reactionTendency: String,
    /** 触发频率（此场景在此人设下出现的概率权重，1-10） */
    val triggerWeight: Int
)

object IdentityChildReactions {

    val reactions: Map<String, IdentityChildReaction> = mapOf(
        // ============ 成年人视角（与临时 NPC 孩童互动） ============
        "housewife" to IdentityChildReaction(
            pathId = "housewife", pathName = "全职妈妈",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 65,
            reactionTendency = "日常家务繁杂，忙碌一天后面对孩子捣乱，更容易急躁发火。但事后看着孩子天真模样，常常生出自我反思。中年阶段经历无数次类似小事，心态会慢慢变得松弛平和。",
            triggerWeight = 10  // 高频触发
        ),
        "migrant_youth" to IdentityChildReaction(
            pathId = "migrant_youth", pathName = "外出打工青年",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 55,
            reactionTendency = "结束一天重体力劳作，租住的房间零碎杂物多。休息时物品散落，疲惫之下情绪起伏明显。但漂泊在外，对孩子多了一份天然的宽容。",
            triggerWeight = 5
        ),
        "construction_worker" to IdentityChildReaction(
            pathId = "construction_worker", pathName = "工地工人",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 60,
            reactionTendency = "一天重体力活下来，身体疲惫到极点。面对散落物品，烦躁来得快，但看着孩子天真的脸，又狠不下心真的发火。",
            triggerWeight = 5
        ),
        "fresh_graduate" to IdentityChildReaction(
            pathId = "fresh_graduate", pathName = "应届毕业生",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 40,
            reactionTendency = "尚未被生活磨平棱角，面对孩子行为相对包容。但如果是毕业后第一份工作压力大，情绪也容易波动。",
            triggerWeight = 4
        ),
        "shop_owner" to IdentityChildReaction(
            pathId = "shop_owner", pathName = "小店店主",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 50,
            reactionTendency = "长期守店，琐事缠身。物品散落意味着额外的收拾时间，烦躁是本能。但做生意久了，对人也多了一份耐心。",
            triggerWeight = 4
        ),
        "civil_servant" to IdentityChildReaction(
            pathId = "civil_servant", pathName = "基层公务员",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 45,
            reactionTendency = "日常工作讲究流程和秩序，被打乱节奏会本能不适。但长期的群众工作，也培养了一定的耐心和包容。",
            triggerWeight = 4
        ),
        "delivery_rider" to IdentityChildReaction(
            pathId = "delivery_rider", pathName = "外卖骑手",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 50,
            reactionTendency = "奔波一天，时间被切碎。回到出租屋只想休息，面对额外的收拾任务，情绪容易上来。但见过太多普通人的生活，对孩子有一份朴素的理解。",
            triggerWeight = 5
        ),
        "adult_child" to IdentityChildReaction(
            pathId = "adult_child", pathName = "全职儿女",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 35,
            reactionTendency = "生活节奏舒缓，压力相对较小。面对孩童行为，有更多耐心和包容心。但偶尔也会因为照顾家人的疲惫而情绪波动。",
            triggerWeight = 3
        ),
        "retired_worker" to IdentityChildReaction(
            pathId = "retired_worker", pathName = "退休工人",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 30,
            reactionTendency = "历经半生风雨，心态趋于平和。面对孩子，更多的是慈祥和宽容。年轻时的急躁，早就被岁月磨成了柔软。",
            triggerWeight = 3
        ),
        "freelancer" to IdentityChildReaction(
            pathId = "freelancer", pathName = "自由职业者",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 45,
            reactionTendency = "时间自由但收入不稳定，打断工作节奏会带来隐形压力。但自由惯了，对生活的不确定性容忍度更高。",
            triggerWeight = 3
        ),
        "affluent_youth" to IdentityChildReaction(
            pathId = "affluent_youth", pathName = "优渥家境青年",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 30,
            reactionTendency = "家务大多由他人打理，缺少细碎劳作压力。物品损耗带来的焦虑偏弱，情绪更为松弛。但缺少和孩童日常相处的经验，偶尔会不知所措。",
            triggerWeight = 2
        ),
        "gifted_youth" to IdentityChildReaction(
            pathId = "gifted_youth", pathName = "天赋高智商青年",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 40,
            reactionTendency = "习惯高效和秩序，被打乱节奏会产生不适。但理性思维让他能够快速分析孩子的行为动机，情绪控制相对成熟。",
            triggerWeight = 3
        ),
        "strong_rural" to IdentityChildReaction(
            pathId = "strong_rural", pathName = "强健乡村青年",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 40,
            reactionTendency = "体力好但生活简单，面对孩子行为朴实直接。不会多想，烦躁来得快去得也快。",
            triggerWeight = 4
        ),
        "disabled_youth" to IdentityChildReaction(
            pathId = "disabled_youth", pathName = "肢体残障青年",
            childType = ChildType.TEMP_NPC,
            baselineIrritability = 45,
            reactionTendency = "收拾物品需要额外付出体力，不便带来的烦躁真实存在。但长期克服困难，比常人更懂得包容和理解。",
            triggerWeight = 3
        ),

        // ============ 童年主线自我（玩家操控孩童视角，不参与临时 NPC 互动） ============
        "childhood_self" to IdentityChildReaction(
            pathId = "childhood_self", pathName = "童年时期的自己",
            childType = ChildType.PLAYER_CHILDHOOD,
            baselineIrritability = 0,
            reactionTendency = "你就是那个蹲在地上捡荔枝的孩子。或者你就是那个拍打乒乓球的小调皮。你不需要选择怎么回应——你只是凭本能在做你的事。这个视角，是给成年后的你回望的。",
            triggerWeight = 1  // 特殊支线，不随机触发，由回望支线手动调用
        )
    )

    fun getReaction(pathId: String): IdentityChildReaction? = reactions[pathId]

    /** 筛选所有成年人视角（临时 NPC 互动）的人设 */
    fun getAdultReactions(): List<IdentityChildReaction> {
        return reactions.values.filter { it.childType == ChildType.TEMP_NPC }
    }

    /** 判断指定身份是否属于童年主线自我 */
    fun isChildhoodSelf(pathId: String): Boolean {
        return reactions[pathId]?.childType == ChildType.PLAYER_CHILDHOOD
    }
}