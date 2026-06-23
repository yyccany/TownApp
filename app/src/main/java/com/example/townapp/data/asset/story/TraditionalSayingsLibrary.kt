package com.example.townapp.data

/**
 * 传统俗语·成语时代局限性分析模块（v2.17）
 *
 * 核心设计理念：
 * 1. 纯文本认知内容——不接入实时游戏参数运算，不改动数值体系
 * 2. 角色成长过程中解锁——青年择业受挫、婚恋失败、中年危机时向长辈求助后触发
 * 3. 长辈输出情绪安慰（短期焦虑下降），但不解决现实物质问题
 * 4. 每条词条包含三部分：原俗语 → 旧时代局限 → 现代可选新出路
 * 5. 两种生存模式没有优劣——乡土安稳和现代灵活都是合理人生选择
 *
 * 世界观定位：
 *   正视代际差异——长辈的建议烙印着农耕时代的生存智慧，
 *   安慰作用大于实际作用。但这不意味着他们错了——
 *   只是他们活在一个我们已经走出来的时代。
 *   角色从"听从长辈"到"理解长辈的局限"，是思想层面的成长。
 */

// ============================================
// 一、数据模型
// ============================================

/**
 * 俗语板块分类
 */
enum class SayingCategory(val label: String, val description: String) {
    STRUGGLE_SURVIVAL("奋斗谋生",
        "农耕生存逻辑——推崇隐忍吃苦，默认个人选择极少"
    ),
    INTERPERSONAL("人际相处",
        "熟人社会逻辑——优先人情妥协，适配乡土社群，不适用于原子化城市"
    ),
    MARRIAGE_FAMILY("婚恋家庭",
        "传宗接代、家族共同体为核心——和现代个人婚恋自由理念存在差异"
    ),
    ADVERSITY_MINDSET("逆境心态",
        "困境被动接纳现实——侧重向内自我消化苦难"
    )
}

/**
 * 触发场景
 */
enum class SayingTrigger(val label: String) {
    YOUTH_CAREER("青年择业受挫"),
    MARRIAGE_PRESSURE("婚恋失败/催婚压力"),
    MIDLIFE_CRISIS("中年事业/通胀危机"),
    WORKPLACE_CONFLICT("职场人际冲突"),
    GENERAL_CRISIS("重大人生挫折")
}

/**
 * 单条俗语词条
 */
data class TraditionalSaying(
    val id: String,
    val category: SayingCategory,
    val trigger: SayingTrigger,
    /** 原俗语/成语 */
    val originalSaying: String,
    /** 旧时代背景与局限 */
    val eraLimitation: String,
    /** 现代可选新出路（小镇世界观视角） */
    val modernAlternative: String,
    /** 短期焦虑下降值 */
    val anxietyReduction: Int = 5
)

// ============================================
// 二、俗语词条库
// ============================================

object TraditionalSayingsLibrary {

    val allSayings: List<TraditionalSaying> = listOf(

        // ==================== 板块1：奋斗谋生类 ====================

        TraditionalSaying(
            id = "saying_eat_bitter",
            category = SayingCategory.STRUGGLE_SURVIVAL,
            trigger = SayingTrigger.YOUTH_CAREER,
            originalSaying = "吃得苦中苦，方为人上人",
            eraLimitation =
                "古代阶层固化极强，底层普通人只有长期高强度体力劳作、隐忍吃苦，是为数不多向上爬升的路径，默认环境无法改变，只能改造自身忍受苦难。长辈会以此劝你埋头加班、忍受压榨——在他们那个年代，这确实是唯一的出路。",
            modernAlternative =
                "在现代社会，单纯埋头吃苦不等于突破困境。岗位行业错误、职场内卷内耗，盲目吃苦只会消耗个人健康、消磨信念韧性。\n" +
                "可选新出路：更换新兴行业、学习新技能、规划副业、调整生活节奏优先保障身心健康。不必依靠无底线吃苦换取出路——你的身体不是耗材。"
        ),
        TraditionalSaying(
            id = "saying_stay_safe",
            category = SayingCategory.STRUGGLE_SURVIVAL,
            trigger = SayingTrigger.YOUTH_CAREER,
            originalSaying = "安分守己，安稳度日",
            eraLimitation =
                "农耕社会，安稳代表温饱生存底线。动荡年代，变动意味着饥荒流离失所，规避一切风险是最优解。长辈面对你辞职、跨城市打拼的选择，会优先劝你守着稳定工作——在粮食靠天吃饭的年代，这是对的。",
            modernAlternative =
                "现代产业迭代速度快，固守老旧岗位可能面临行业淘汰失业。适度风险、灵活择业，是当代可行方案。安稳只是众多人生选择中的一种，并非唯一标准答案。\n" +
                "你可以选择安稳——但那是你选的，不是因为你没得选。"
        ),
        TraditionalSaying(
            id = "saying_heaven_rewards",
            category = SayingCategory.STRUGGLE_SURVIVAL,
            trigger = SayingTrigger.YOUTH_CAREER,
            originalSaying = "天道酬勤",
            eraLimitation =
                "农耕时代，努力和收获是直接对应的——多种一亩地，多收一担粮。长辈相信'只要努力就有回报'，因为在他们的经验里，这是真的。但城市写字楼里的努力——加班、内卷、讨好领导——和回报之间的关系，远没有种地那么直接。",
            modernAlternative =
                "在现代社会，行业选择、信息差、平台效应、运气，对结果的影响往往大于'努力'本身。努力依然重要——但它不是充分条件，只是必要条件之一。\n" +
                "你可以努力——但请把努力用在正确的方向上。不要用战术上的勤奋，掩盖战略上的懒惰。"
        ),
        TraditionalSaying(
            id = "saying_iron_rice_bowl",
            category = SayingCategory.STRUGGLE_SURVIVAL,
            trigger = SayingTrigger.MIDLIFE_CRISIS,
            originalSaying = "铁饭碗，一辈子不愁",
            eraLimitation =
                "计划经济时代，国企、事业单位确实意味着终身保障。长辈那一代人见过太多'没编制'的苦——所以他们把'铁饭碗'当成人生终极目标。但那个时代已经过去了——铁饭碗也会生锈，编制也会裁员。",
            modernAlternative =
                "现代社会的安全感，不再来自一个'永远不倒闭的单位'，而是来自你自身的能力储备、多元收入来源、以及随时可以重新开始的底气。\n" +
                "铁饭碗不是某个岗位——是你能在任何地方找到饭吃。"
        ),
        TraditionalSaying(
            id = "saying_thrift_only",
            category = SayingCategory.STRUGGLE_SURVIVAL,
            trigger = SayingTrigger.MIDLIFE_CRISIS,
            originalSaying = "勤俭持家，省吃俭用",
            eraLimitation =
                "物资匮乏年代，节省是唯一的生存策略——因为确实没有多余的东西可以浪费。长辈会劝你省着点花、别乱买东西——在他们那个年代，省下来的就是活下来的。",
            modernAlternative =
                "现代社会的核心问题不是'不够省'，而是收入结构单一、抗风险能力弱。节流重要，但开源更重要。\n" +
                "把钱花在提升自己、拓宽收入渠道上——这不是浪费，是投资。省吃俭用能让你活得久一点——但只有开源，才能让你活得好一点。"
        ),

        // ==================== 板块2：人际相处类 ====================

        TraditionalSaying(
            id = "saying_step_back",
            category = SayingCategory.INTERPERSONAL,
            trigger = SayingTrigger.WORKPLACE_CONFLICT,
            originalSaying = "退一步海阔天空",
            eraLimitation =
                "乡土熟人圈子世代聚居，邻里、乡亲长期深度绑定，妥协退让能够维持社群和睦，规避长久的人际矛盾。长辈遇到你在职场被排挤、被欺负，会劝你'忍一忍就过去了'——因为在村里，得罪一个人就是得罪一辈子。",
            modernAlternative =
                "城市职场大多为短期合作关系，一味退让会不断被他人侵占权益，加剧自我压抑，提升孤独值、焦虑值。\n" +
                "现代选择：划定个人边界，拒绝无底线妥协。沟通谈判、更换工作脱离不良人际环境——在陌生人社会里，你有权利离开。"
        ),
        TraditionalSaying(
            id = "saying_many_friends",
            category = SayingCategory.INTERPERSONAL,
            trigger = SayingTrigger.WORKPLACE_CONFLICT,
            originalSaying = "多个朋友多条路",
            eraLimitation =
                "过去物资闭塞，人脉代表物资互通渠道。熟人越多，生存资源越多。长辈会劝你多出去社交、多认识人——在他们的经验里，朋友就是保险。",
            modernAlternative =
                "原子化城市模式下，泛社交人脉会消耗大量个人休息时间，挤占自我成长、独处休整的时间。无效社交反而加剧疲惫与内耗。\n" +
                "现代出路：精简社交圈层，优先深度挚友。大量浅社交可主动舍弃——独居、精简社交是合理的生活方式。三五个真朋友，比五百个微信好友有用得多。"
        ),
        TraditionalSaying(
            id = "saying_peace_most_valuable",
            category = SayingCategory.INTERPERSONAL,
            trigger = SayingTrigger.WORKPLACE_CONFLICT,
            originalSaying = "和为贵",
            eraLimitation =
                "乡土社会里，冲突的代价太大——宗族械斗、世代结仇，所以'和'被放在第一位。长辈面对你和同事的矛盾，会劝你息事宁人——因为在他们的经验里，冲突就是危险。",
            modernAlternative =
                "现代社会的'和'不应该是单方面忍让。建设性冲突——表达不同意见、争取合理权益——是健康人际关系的一部分。\n" +
                "'和'不是没有冲突，是冲突之后还能坐下来谈。"
        ),
        TraditionalSaying(
            id = "saying_dont_fight",
            category = SayingCategory.INTERPERSONAL,
            trigger = SayingTrigger.WORKPLACE_CONFLICT,
            originalSaying = "不争不抢，顺其自然",
            eraLimitation =
                "在资源总量固定的农耕社会，'争抢'意味着从别人碗里抢食——这确实是危险的。长辈会劝你不要太出头、不要太主动——因为在他们的世界里，出头鸟先被打。",
            modernAlternative =
                "现代社会的机会不是固定大小的蛋糕——很多机会是创造出来的、是你主动争取才出现的。不争不抢不等于'善良'，有时候只是把机会拱手让人。\n" +
                "你可以不争——但请确认，这是因为你不需要，而不是因为你不敢。"
        ),
        TraditionalSaying(
            id = "saying_save_face",
            category = SayingCategory.INTERPERSONAL,
            trigger = SayingTrigger.GENERAL_CRISIS,
            originalSaying = "人活一张脸，树活一张皮",
            eraLimitation =
                "熟人社会里，面子就是信用——丢了面子，意味着失去了整个社群对你的信任，连借粮都借不到。长辈会劝你'别丢人'——因为在他们的生存逻辑里，面子是真的能当饭吃的。",
            modernAlternative =
                "在陌生人社会，大多数人对你的评价和你的实际生活无关。为了'面子'去做违背自己意愿的事——买超出能力的房子、办超出预算的婚礼——是在用别人的眼光惩罚自己的钱包。\n" +
                "面子是给别人看的——日子是你自己过的。"
        ),

        // ==================== 板块3：婚恋家庭类 ====================

        TraditionalSaying(
            id = "saying_marry_first",
            category = SayingCategory.MARRIAGE_FAMILY,
            trigger = SayingTrigger.MARRIAGE_PRESSURE,
            originalSaying = "成家立业，先成家后立业",
            eraLimitation =
                "古代成家依靠家族合力劳作，婚姻为生存抱团方式。越早组建家庭，劳动力越多。长辈面对单身青年，普遍催婚——把成家当作人生必选项。在他们那个年代，一个人确实很难活下去。",
            modernAlternative =
                "现代社会房贷、育儿成本极高，过早结婚容易背负巨额负债，挤压事业规划空间。\n" +
                "现代可选路径：晚婚、丁克、优先事业发展、独身生活。婚姻不再是人生必经阶段——它变成了众多选项中的一个。你可以选择结婚——但那是你想要的，不是你欠谁的。"
        ),
        TraditionalSaying(
            id = "saying_make_do",
            category = SayingCategory.MARRIAGE_FAMILY,
            trigger = SayingTrigger.MARRIAGE_PRESSURE,
            originalSaying = "过日子凑合就行",
            eraLimitation =
                "旧时女性生存依附家庭，离婚生存风险极高。婚姻的核心功能是'活下去'而不是'活得好'。长辈会劝你在感情里忍一忍、凑合凑合——因为在他们的时代，离婚的成本是生存级的。",
            modernAlternative =
                "当代个人经济独立，婚姻感情破裂之后，离婚、重新规划人生属于可行选择。不必勉强凑合度日。\n" +
                "'凑合'不是美德——是两个人都没有更好的选择时的妥协。如果你有选择——不要用'凑合'来辜负自己。"
        ),
        TraditionalSaying(
            id = "saying_marry_by_age",
            category = SayingCategory.MARRIAGE_FAMILY,
            trigger = SayingTrigger.MARRIAGE_PRESSURE,
            originalSaying = "男大当婚，女大当嫁",
            eraLimitation =
                "古代平均寿命短、医疗条件差，二十岁不结婚就意味着错过了生育窗口。'到了年龄就该结婚'是生存策略——不是人生哲学。长辈催婚时的焦虑，是刻在基因里的对'断后'的恐惧。",
            modernAlternative =
                "现代人平均寿命大幅延长，生育窗口也随之拓宽。三十岁、四十岁结婚——不是'晚了'，是你的人生节奏和别人不一样。\n" +
                "婚姻没有'应该'的年龄——只有你自己觉得准备好了的时刻。"
        ),
        TraditionalSaying(
            id = "saying_no_son_no_filial",
            category = SayingCategory.MARRIAGE_FAMILY,
            trigger = SayingTrigger.MARRIAGE_PRESSURE,
            originalSaying = "不孝有三，无后为大",
            eraLimitation =
                "农耕社会，家族延续是每个人的义务——没有后代意味着年老时无人赡养、死后无人祭祀。这是现实的生存焦虑，不是单纯的'思想落后'。长辈的催生压力，背后是对'你老了怎么办'的恐惧。",
            modernAlternative =
                "现代社会有养老体系、社保、个人储蓄——'养儿防老'不再是唯一选项。生育是权利，不是义务。\n" +
                "你可以选择生育——因为你想要一个新生命陪伴你走过一段人生。不是因为'应该'，不是因为'必须'——是因为你愿意。"
        ),
        TraditionalSaying(
            id = "saying_follow_husband",
            category = SayingCategory.MARRIAGE_FAMILY,
            trigger = SayingTrigger.MARRIAGE_PRESSURE,
            originalSaying = "嫁鸡随鸡，嫁狗随狗",
            eraLimitation =
                "旧时女性没有独立经济来源，婚姻是天——天塌了，人就活不下去了。所以'随'是唯一的生存策略。长辈劝你忍一忍——不是她们不心疼你，是她们当年真的没有别的路。",
            modernAlternative =
                "女性经济独立是现代社会最深刻的变革之一。你不需要'随'任何人——婚姻是两个独立的人选择同行，而不是一方依附另一方。\n" +
                "如果同行变成了拖行——停下来，或者换一条路。这不是你的错。"
        ),

        // ==================== 板块4：逆境心态类 ====================

        TraditionalSaying(
            id = "saying_fate_determined",
            category = SayingCategory.ADVERSITY_MINDSET,
            trigger = SayingTrigger.MIDLIFE_CRISIS,
            originalSaying = "命由天定，万般皆是命",
            eraLimitation =
                "古代天灾、赋税、饥荒——个人无力对抗，个体很难改变外部大环境。宿命论用来消解苦难带来的心理崩溃，安抚焦虑情绪。长辈在通胀、大规模失业的时代困境中，会劝你接纳现状——因为在他们的经验里，反抗是徒劳的。",
            modernAlternative =
                "宏观时代浪潮个人难以全盘改变，但个体可通过择业、迁居、技能提升调整个人处境。极少数革新者可以推动政策变革，调整宏观社会基线——人具备主动改造环境的可能性。\n" +
                "命不是写好的剧本——是一张地图，有些路被封了，但你没走过的那条，或许还开着。"
        ),
        TraditionalSaying(
            id = "saying_go_with_flow",
            category = SayingCategory.ADVERSITY_MINDSET,
            trigger = SayingTrigger.GENERAL_CRISIS,
            originalSaying = "顺其自然，随遇而安",
            eraLimitation =
                "古代灾害、疾病缺少医疗手段，饥荒瘟疫无力抗衡。'顺其自然'是被动妥协的最优解——不是不想反抗，是反抗了也没用。长辈在你遇到困难时劝你'看开点'——是他们能给出的最好的安慰了。",
            modernAlternative =
                "放到现代，生病可选择现代医疗方案，失业可以学习技能转型，多数困境拥有主动解决路径。'顺其自然'适合心理和解层面——接受无法改变的事——但不能当作现实问题的逃避借口。\n" +
                "顺其自然是在你尽了全力之后——对结果的一种坦然。不是在你还没开始之前——就提前投降。"
        ),
        TraditionalSaying(
            id = "saying_misfortune_blessing",
            category = SayingCategory.ADVERSITY_MINDSET,
            trigger = SayingTrigger.GENERAL_CRISIS,
            originalSaying = "塞翁失马，焉知非福",
            eraLimitation =
                "这是少数几个在今天依然适用的古老智慧——它讲的不是被动接受，而是'不要用眼前的得失来定义一件事的全部意义'。但长辈在用它安慰你时，往往省略了后半句——塞翁失马之后，他并没有躺平，而是继续好好活着。",
            modernAlternative =
                "这条俗语本身没有太大问题——问题在于，它是用来安慰你的，不是用来让你不行动的。\n" +
                "失去了一份工作——可能是找到更好工作的开始。但前提是，你去找了。塞翁不是坐在家里等马回来的——他是在继续过日子的过程中，等到了马带着新马回来。"
        ),
        TraditionalSaying(
            id = "saying_boat_reaches_bridge",
            category = SayingCategory.ADVERSITY_MINDSET,
            trigger = SayingTrigger.GENERAL_CRISIS,
            originalSaying = "船到桥头自然直",
            eraLimitation =
                "古代交通不便，行船确实有太多不可控因素——水流、风向、天气——所以'别想太多，到了再说'是务实的经验。长辈在你焦虑时劝你'别急'——是他们在用有限的工具安抚你的情绪。",
            modernAlternative =
                "现代社会的很多问题，不是'到了自然就直了'——房贷不会自动还清，工作不会自动找上门，健康不会自动恢复。你需要主动规划、提前准备——但也要接受'计划赶不上变化'。\n" +
                "船到桥头自然直——前提是，你的船在往前走。"
        ),
        TraditionalSaying(
            id = "saying_heaven_wont_block",
            category = SayingCategory.ADVERSITY_MINDSET,
            trigger = SayingTrigger.GENERAL_CRISIS,
            originalSaying = "天无绝人之路",
            eraLimitation =
                "在极度匮乏的农耕时代，'天无绝人之路'是生存意志的最后防线——它让人在绝境中不至于崩溃。长辈说这句话时，传递的不是解决方法，是'我还在，你也要在'。",
            modernAlternative =
                "现代社会确实有更多路径——但路径不会自动出现。天不会绝人之路——但你要自己去找那条路。\n" +
                "这句话真正的力量不是'等着路出现'，而是'相信有路，然后去找'。长辈给了你相信的勇气——剩下的事，是你自己的。"
        )
    )

    // ============================================
    // 三、按分类/触发场景查询
    // ============================================

    fun getByCategory(category: SayingCategory): List<TraditionalSaying> {
        return allSayings.filter { it.category == category }
    }

    fun getByTrigger(trigger: SayingTrigger): List<TraditionalSaying> {
        return allSayings.filter { it.trigger == trigger }
    }

    fun getById(id: String): TraditionalSaying? {
        return allSayings.find { it.id == id }
    }
}