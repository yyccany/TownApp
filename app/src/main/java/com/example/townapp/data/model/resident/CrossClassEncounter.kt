package com.example.townapp.data

/**
 * 跨圈层相遇事件体系（v2.3 阶层认知碰撞）
 *
 * 核心设计原则：
 * 1. 分歧根源是环境、教育、原生家境塑造的思维差异，不是人品高低
 * 2. 三种相处走向完全开放：浅层相逢、深度磨合、观念僵持，不强制和解
 * 3. 一次相遇很难彻底扭转多年形成的思维习惯，不制造理想化戏剧性和解
 * 4. 以人为本：聚焦个体内心纠结、自我怀疑、平庸感与苦衷
 * 5. 中年阶段角色阅历更沉稳，更容易包容不同想法
 */

// ============================================
// 一、相遇场景枚举
// ============================================

/**
 * 跨圈层相遇场景类型
 */
enum class EncounterScenario(val id: String, val label: String, val description: String) {
    /** 场景一：寒门科研青年与中产派系科研者（实验室共事） */
    POOR_RESEARCHER_MEETS_INSIDER(
        "poor_researcher_insider",
        "寒门科研青年 × 中产派系科研者",
        "实验室共事。一个被贫困岁月打磨，一个受优渥圈层规训。"
    ),
    /** 场景二：工厂工人与资深科研人员（厂区技术改造调研） */
    WORKER_MEETS_RESEARCHER(
        "worker_researcher",
        "工厂工人 × 资深科研人员",
        "技术改造调研。科研者着眼长远数据，工人担忧眼下生计。"
    ),
    /** 场景三：普通上班族与科创创业者（街边闲谈） */
    OFFICE_WORKER_MEETS_ENTREPRENEUR(
        "office_entrepreneur",
        "普通上班族 × 科创创业者",
        "街边闲谈。一人安于平凡烟火，一人奔赴浪潮前沿。"
    )
}

// ============================================
// 二、相处走向枚举
// ============================================

/**
 * 相遇后的三种相处走向
 */
enum class EncounterOutcome(val label: String, val description: String) {
    /** 浅层相逢：短暂交流后各自回归原有生活，观念基本不变 */
    SUPERFICIAL(
        "浅层相逢",
        "短暂交流过后，各自回归原有生活，观念基本不变。隔阂依旧存在，但彼此多了一份对对方处境的简单理解。"
    ),
    /** 深度磨合：长期共事互相补齐认知盲区，认知参数正向调整 */
    DEEP_INTEGRATION(
        "深度磨合",
        "持续长期共事、来往，互相补齐自身认知盲区，优化个人规划。认知参数发生长期正向调整。"
    ),
    /** 观念僵持：无法接纳彼此思维模式，分歧持续存在 */
    STALEMATE(
        "观念僵持",
        "无法接纳彼此的思维模式，分歧持续存在，合作破裂。长久环境塑造的观念很难短期改变——这很正常。"
    )
}

// ============================================
// 三、相遇事件数据模型
// ============================================

/**
 * 跨圈层相遇事件
 *
 * 五段式结构：体感 → 闪光点 → 个人工时 → 时代成本 → 小镇评述
 */
data class CrossClassEncounter(
    val scenario: EncounterScenario,
    /** 角色A的身份标识（pathId） */
    val roleA: String,
    /** 角色B的身份标识（pathId） */
    val roleB: String,
    /** 角色A的称呼 */
    val roleALabel: String,
    /** 角色B的称呼 */
    val roleBLabel: String,
    /** 触发场景描述 */
    val triggerContext: String,

    // ---- 五段式文案 ----
    /** 第一段：体感反馈 */
    val bodyFeeling: String,
    /** 第二段：闪光点 */
    val sparkle: String,
    /** 第三段：个人工时锐评 */
    val personalCost: String,
    /** 第四段：时代成本解析 */
    val eraCost: String,
    /** 第五段：小镇评述 */
    val townCommentary: String,

    /** 三种走向的玩家可选回应 */
    val choices: List<EncounterChoice>
)

/**
 * 玩家回应选项
 */
data class EncounterChoice(
    val outcome: EncounterOutcome,
    val label: String,
    val responseText: String,
    /** 认知参数变化描述 */
    val cognitionChange: String
)

// ============================================
// 四、预置相遇场景
// ============================================

object CrossClassEncounters {

    val encounters: List<CrossClassEncounter> = listOf(

        // ===== 场景一：寒门科研青年 × 中产派系科研者 =====
        CrossClassEncounter(
            scenario = EncounterScenario.POOR_RESEARCHER_MEETS_INSIDER,
            roleA = "researcher_poor",
            roleB = "academic_insider",
            roleALabel = "寒门科研青年",
            roleBLabel = "中产派系科研者",
            triggerContext = "实验室项目合作——你凭借多年钻研拿到项目入场资格，和从小依托学术圈层长大的同龄人搭档科研",
            bodyFeeling = "合作初期时常争执。对方觉得你不懂行业规则、不会走捷径；你感慨对方看不见底层生存压力，一开口就是「找家里认识的人打个招呼」。深夜独处时，你开始怀疑自己——是不是眼界太狭隘、太过平庸，不懂这套学术体系的运作逻辑。",
            sparkle = "争执之余，你们慢慢看见彼此背后的难处。中产青年意识到圈层资源带来的信息壁垒——他不是故意依赖人脉，是从小到大身边的人都是这样解决问题的。你也懂得了学术体系经费运转的现实约束——有些项目确实需要资源铺垫。",
            personalCost = "前期观念分歧，耗费数百小时沟通磨合工时，拖累短期项目进度。长期磨合后分工互补——你补上了他对底层需求的认知盲区，他帮你打通了经费申请渠道，整体研发效率提升。",
            eraCost = "二人磨合阶段项目进度放缓，新技术落地时间小幅延后。磨合达成共识后，研发方案兼顾经费可行性与大众消费承受能力，后续技术普惠效果更好——普通家庭也能负担得起。",
            townCommentary = "一个被贫困岁月打磨，一个受优渥圈层环境规训，两人的观念都是成长环境的产物。寒门青年会怀疑自身平庸，中产青年深陷圈层思维定式，大家各有苦衷。相逢不一定能彻底抹平多年形成的认知差距，却给了双方跳出固有视角的机会——看见彼此，就是改变的开始。",
            choices = listOf(
                EncounterChoice(
                    outcome = EncounterOutcome.SUPERFICIAL,
                    label = "维持表面合作，各自保留看法",
                    responseText = "你不去纠正他的习惯，他也不会追问你的经历。项目结束后，你们礼貌地道别，各自回到原来的圈子里。这一场共事，彼此多了一点模糊的理解——但也仅此而已。",
                    cognitionChange = "对对方圈层的认知略有松动，但核心观念未变。"
                ),
                EncounterChoice(
                    outcome = EncounterOutcome.DEEP_INTEGRATION,
                    label = "主动沟通，尝试理解彼此处境",
                    responseText = "你在一次深夜加班后，主动聊起了自己的求学经历。他沉默了很久，然后说了一句：「我一直以为所有人都是家里供着读完的。」你们开始真正看见对方——不是标签，是人。",
                    cognitionChange = "双方的认知局限各减少15点，跨圈层接触次数+1，创新方案质量提升。"
                ),
                EncounterChoice(
                    outcome = EncounterOutcome.STALEMATE,
                    label = "无法忍受，申请换组",
                    responseText = "你受不了他动辄「找家里打个招呼」的做派，他也觉得你太较真、不懂变通。项目还没结束，你就申请换了搭档。你们各自松了一口气——但心里都有一丝说不清的遗憾。",
                    cognitionChange = "认知局限未变，对对方圈层的好感度下降。但你没有错——不是所有人都必须兼容。"
                )
            )
        ),

        // ===== 场景二：工厂工人 × 资深科研人员 =====
        CrossClassEncounter(
            scenario = EncounterScenario.WORKER_MEETS_RESEARCHER,
            roleA = "construction_worker",
            roleB = "researcher_affluent",
            roleALabel = "工厂工人",
            roleBLabel = "资深科研人员",
            triggerContext = "厂区技术改造调研——科研人员为自动化升级项目实地走访，规划用智能流水线提升产能",
            bodyFeeling = "你听着对方描绘的未来产业升级蓝图——全自动流水线、智能调度、产能翻倍。他说得很兴奋，你听得很惶恐。你第一反应不是技术进步有多好，而是：这条线要是全自动了，我和工友们去哪儿？他起初以为你抗拒时代进步，细聊之后才得知——你全家五口人的收入，都靠你在厂里的这份工作。",
            sparkle = "科研者开始调整方案，在规划中加入了岗位转岗培训计划。你结合多年一线经验，指出了自动化设备落地后会出现的实操漏洞——比如传感器在高温车间会失灵、机械臂在粉尘环境下容易卡顿。这些是书本上没有的，是你用二十年站在流水线旁换来的经验。",
            personalCost = "实地沟通占用了科研人员的调研工时，你牺牲了午休和下班后的休息时间，带着他看设备、讲细节。短期增加了双方的时间消耗——但换来的方案，比拍脑袋写的更靠谱。",
            eraCost = "调整后的方案放缓了自动化改造速度，新增了转岗培训周期。这规避了大规模短期失业阵痛，拉长了产业转型周期——代价是效率提升来得更慢，但民生代价也降得更低。",
            townCommentary = "科研者受高等学术教育，习惯着眼数十年的时代远景；工人一辈子扎根流水线，优先考量当下生计。谈不上谁眼界更高，只是时代赋予的成长路径截然不同。短暂相逢，让宏大的技术理想接住了普通人现实的苦衷——平庸细碎的生计日常，同样在影响时代前进的走向。",
            choices = listOf(
                EncounterChoice(
                    outcome = EncounterOutcome.SUPERFICIAL,
                    label = "配合调研，但不再主动参与后续",
                    responseText = "你带着他走了一遍车间，该说的都说了。调研结束，他回去了，你继续上工。你们之间的交集到此为止——你心里清楚，他写的报告里，你的名字不会出现。但没关系，你做了你能做的。",
                    cognitionChange = "科研者对你的印象从「抗拒进步」变为「有现实顾虑」，但方案未实质性调整。"
                ),
                EncounterChoice(
                    outcome = EncounterOutcome.DEEP_INTEGRATION,
                    label = "持续参与改造方案，提供一线反馈",
                    responseText = "你主动加入了厂区的技术改造讨论组，每周抽时间参加方案讨论。你提出的十几条实操建议被采纳了——他后来在报告里专门写了你的名字。你第一次觉得，流水线工人说的话，也能被听见。",
                    cognitionChange = "双方的认知局限各减少20点，你的职业认同感上升，技术改造方案质量提升。"
                ),
                EncounterChoice(
                    outcome = EncounterOutcome.STALEMATE,
                    label = "拒绝配合，认为对方不关心工人死活",
                    responseText = "你看着他那些花里胡哨的PPT，想到工友们可能失业，心里一阵火。你拒绝了后续配合——「你们这些坐办公室的，画几张图就想把我们全换掉？」他愣住了，你转身回了车间。",
                    cognitionChange = "认知分歧固化，双方关系恶化。但你的愤怒有道理——他不是来抢你饭碗的，但你的饭碗确实悬了。"
                )
            )
        ),

        // ===== 场景三：普通上班族 × 科创创业者 =====
        CrossClassEncounter(
            scenario = EncounterScenario.OFFICE_WORKER_MEETS_ENTREPRENEUR,
            roleA = "civil_servant",
            roleB = "freelancer",
            roleALabel = "普通上班族",
            roleBLabel = "科创创业者",
            triggerContext = "街边咖啡店偶遇——创业者抱着电脑在改BP，你下班路过，聊了起来",
            bodyFeeling = "听完创业者对未来的规划——要用新技术改变整个行业、要融资、要上市——你内心一阵怅然。你每天做的事情是写报告、开会、应付检查，重复而枯燥。你觉得自己是不是太安于现状了，是不是活得太平庸了。而创业者看见你工作日准时下班、周末不用加班，疲惫之余偶尔羡慕：不用背负项目成败的压力，不用每天醒来第一件事就是看账户余额。",
            sparkle = "你以日常消费体验，指出了新产品面向大众时的定价痛点——「你说的这个价格，我一个月工资扣掉房租和吃饭，剩下的连试用版都买不起」。创业者愣住了，他从来没从这个角度想过。而他的拼搏，也让你开始思考：自己平淡的生活里，到底缺了什么、又拥有了什么。",
            personalCost = "闲谈属于私人休闲时间，没有直接工作工时损耗。但这段对话间接优化了创业者的产品规划——他回去后把定价方案砍了一半。一周后，他发来一条消息：谢谢你那天说的话。",
            eraCost = "产品定价贴合普通工薪人群收入水平后，新技术普及门槛降低，更多普通人可以负担使用。技术红利从少数人的玩具，变成了大多数人能摸到的日常。",
            townCommentary = "有人奔赴浪潮前沿推动时代变革，有人安于日复一日平凡的烟火生活。相遇之后，双方都会短暂羡慕彼此的人生——可一番思索过后，大多依旧回归原本的生活。多数人本就是普通人，接纳自身的平庸，也是一种人生选择。平庸不等于低劣，宏大理想也不等于高人一等。",
            choices = listOf(
                EncounterChoice(
                    outcome = EncounterOutcome.SUPERFICIAL,
                    label = "加了微信，但后来再也没有联系",
                    responseText = "你们互相加了微信，说了句「有空再聊」。你知道大概率不会再有后续了——他继续奔跑，你继续上班。但那天下午的对话，你们都记住了一小部分。",
                    cognitionChange = "对另一种生活方式的想象更具体了，但生活轨迹不变。"
                ),
                EncounterChoice(
                    outcome = EncounterOutcome.DEEP_INTEGRATION,
                    label = "保持联系，偶尔提供用户视角反馈",
                    responseText = "你们偶尔会发消息。他发新产品原型给你看，你从普通用户的角度提意见。你不是他的团队成员，但你成了他产品里最接地气的那个「顾问」。你慢慢发现——你的「平庸」视角，恰好是他最缺的东西。",
                    cognitionChange = "你的自我价值感提升，创业者的产品更贴合大众需求。跨圈层接触次数+1。"
                ),
                EncounterChoice(
                    outcome = EncounterOutcome.STALEMATE,
                    label = "敷衍几句，早早结束对话",
                    responseText = "你听着他那些宏大的词汇——「颠覆」「风口」「赛道」——觉得离自己太远了。你敷衍了几句，说还有事先走了。走出咖啡店的时候，你松了口气，但也有一丝说不清的落寞。",
                    cognitionChange = "你对自己生活的评价未变，但隐隐觉得「也许我也可以试试别的」。这个念头很快被第二天的闹钟冲散了。"
                )
            )
        )
    )

    /** 根据玩家身份查找可能触发的相遇场景 */
    fun findByRole(pathId: String): List<CrossClassEncounter> {
        return encounters.filter { it.roleA == pathId || it.roleB == pathId }
    }

    /** 根据场景ID查找 */
    fun findById(scenarioId: String): CrossClassEncounter? {
        return encounters.find { it.scenario.id == scenarioId }
    }
}