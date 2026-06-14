package com.example.townapp.data

/**
 * 跨职业视角对话模板（v1.6 职业圈层认知局限体系）
 *
 * 核心设计原则：
 * - 每种职业都有固有认知局限，根源是环境隔绝，不是个人偏见
 * - 不审判任何一方的看法，只展示"片面看法—客观现实—视角差异成因"
 * - 玩家可选择认同对方、说出客观事实、沉默聆听
 * - 多次接触不同群体后，阅历增长，原有片面认知逐步修正
 */

// ============================================
// 对话数据模型
// ============================================

/**
 * 跨职业视角对话场景
 *
 * 四段式结构：体感反馈 → 闪光点 → 现实锐评 → 小镇评述
 */
data class CrossProfessionDialogue(
    val id: String,
    /** 发言者身份 pathId */
    val speakerPathId: String,
    /** 发言者身份名称 */
    val speakerName: String,
    /** 倾听者身份 pathId */
    val listenerPathId: String,
    /** 倾听者身份名称 */
    val listenerName: String,
    /** 触发场景 */
    val triggerContext: String,
    /** 发言者开场白（体现其认知局限） */
    val speakerOpeningLine: String,

    // ---- 四段式结构 ----
    /** 第一段：体感反馈——发言者描述出自己内心羡慕/向往的生活模式 */
    val bodyFeeling: String,
    /** 第二段：闪光点——为什么对方会有这种想法，挖掘其背后合理的心理需求 */
    val sparkle: String,
    /** 第三段：现实锐评——展示客观数据，看到表象和真实生活的差距 */
    val realityCheck: String,
    /** 第四段：小镇评述——解释认知局限的环境成因，不下判断 */
    val townCommentary: String,

    /** 玩家可选回应 */
    val choices: List<DialogueChoice>
)

/**
 * 玩家回应选项
 */
data class DialogueChoice(
    val label: String,
    val responseText: String,
    /** 长期效果：认知迭代方向 */
    val effect: String
)

// ============================================
// 预置跨职业对话场景
// ============================================

object CrossProfessionDialogues {

    val dialogues: List<CrossProfessionDialogue> = listOf(

        // ---------- 场景1：公务员 ↔ 工地工人 ----------
        CrossProfessionDialogue(
            id = "civil_to_worker",
            speakerPathId = "civil_servant",
            speakerName = "基层公务员",
            listenerPathId = "construction_worker",
            listenerName = "工地工人",
            triggerContext = "社区邻里闲聊时偶遇",
            speakerOpeningLine = "你平日在外干活，风吹开阔，随时看见山野风景，比我们办公室久坐拘束自在许多。我真羡慕你。",
            bodyFeeling = "你听到对方羡慕你的工作环境——风吹日晒、露天开阔、不用整天坐在密闭房间里。他的语气里透着一股真诚的向往。",
            sparkle = "对方长期身处密闭写字楼，每天面对的是文件、会议和流程。他发自内心地向往户外、开阔和自由——这份羡慕背后，是对自己工作环境束缚感的真实表达。",
            realityCheck = "户外劳作是谋生工作，并非休闲游玩。每日重体力劳动时长10小时以上，夏季高温暴晒、冬季寒风刺骨，日晒劳损、薪资按项目结算不稳定。坐在办公室里看到的「户外自由」，和亲身体验的「户外劳动」是两回事。",
            townCommentary = "长久坐在写字楼之中，只能看见户外开阔的表象，缺少体力劳作的亲身经历。环境隔开了彼此的生活，便形成了理想化的想象——这是环境带来的认知局限，无关个人偏见。",
            choices = listOf(
                DialogueChoice("认同对方", "是啊，外面空气是好。各有各的难处吧。", "你选择不去纠正对方的看法，保持了友好的氛围。但认知偏差依旧存在。"),
                DialogueChoice("说出事实", "其实挺累的——一天扛几千块砖，夏天晒脱皮，冬天冻得手都伸不直。不是你想的那种自由。", "你用亲身经历打破了对方的理想化想象。对方沉默了一会儿，若有所思。"),
                DialogueChoice("沉默聆听", "……", "你只是听着，没有反驳也没有认同。对方继续说着自己的羡慕，你默默承受着对方看不见的辛苦。")
            )
        ),

        // ---------- 场景2：应届毕业生 ↔ 工地工人 ----------
        CrossProfessionDialogue(
            id = "graduate_to_worker",
            speakerPathId = "fresh_graduate",
            speakerName = "应届毕业生",
            listenerPathId = "construction_worker",
            listenerName = "工地工人",
            triggerContext = "周末外出时路过工地，和工人闲聊",
            speakerOpeningLine = "你们每天在户外干活，身体肯定特别好吧？又能晒太阳又能锻炼，一举多得啊！",
            bodyFeeling = "对方用羡慕的语气评价你的工作——觉得露天干活就是户外健身，既能赚钱又能保持健康。他真心觉得这是一份理想的工作。",
            sparkle = "年轻人长期坐在办公室，腰酸背痛、缺乏运动，对自己的身体状况隐隐焦虑。看到户外体力劳动者，他下意识把自己缺少的「运动」和「户外」投射到了对方身上——这份羡慕背后，是对自己久坐生活方式的担忧。",
            realityCheck = "工地劳动不是健身——是每天扛水泥、搬砖头、爬脚手架，持续10小时以上。长期重体力劳动带来的是腰肌劳损、关节磨损、日晒皮肤损伤，不是健身房里的「锻炼」。他看到的「一举多得」，是体力劳动者用身体换来的生存。",
            townCommentary = "城市长大的年轻人，生活经验以网络碎片信息为主。短视频里体力劳动被美化、田园生活被滤镜化——缺少实地接触，便容易构建理想化的想象。这不是傲慢，是成长环境带来的视角盲区。",
            choices = listOf(
                DialogueChoice("认同对方", "哈哈，是挺锻炼人的。", "你没有纠正对方的误解。他带着理想化的印象离开了——但也许有一天，他会亲眼看到真实的工地。"),
                DialogueChoice("说出事实", "不是健身——是打工。每天扛几百斤水泥，腰早就坏了。你要不要试试？", "你用直白的话语打破了对方的滤镜。他愣了一下，然后认真地点了点头。"),
                DialogueChoice("沉默聆听", "……", "你只是笑了笑，没有解释。他看不到你手上的茧和腰上的膏药——这些是你不想解释的日常。")
            )
        ),

        // ---------- 场景3：优渥青年 ↔ 外出打工青年 ----------
        CrossProfessionDialogue(
            id = "affluent_to_migrant",
            speakerPathId = "affluent_youth",
            speakerName = "优渥家境青年",
            listenerPathId = "migrant_youth",
            listenerName = "外出打工青年",
            triggerContext = "节假日返乡时偶然同车，聊起各自的生活",
            speakerOpeningLine = "我觉得你应该趁年轻多投资自己，报个培训班、学个技能——现在机会这么多，只要肯规划，怎么会一直打工呢？",
            bodyFeeling = "对方很真诚地给你建议——他觉得你应该趁年轻多学习、多投资自己。他的语气里没有恶意，甚至带着一种「想帮你」的善意。",
            sparkle = "对方从小拥有充足的教育资源和家庭支持，职业道路被规划得井井有条。在他的经验里，「努力就有回报」是成立的——因为他付出努力的同时，有家庭兜底、有资源支撑。他真心觉得所有人都有这种条件。",
            realityCheck = "外出打工青年每月工资大部分要寄回家，剩下的只够基本生活。培训班几千块的学费，是他好几个月的积蓄。每天加班到晚上八九点，周末也经常排班——不是不想学，是精力和金钱都不允许。他看到的「机会」，对很多人来说是需要先解决生存才能考虑的事。",
            townCommentary = "从小不用为钱发愁的人，很难真切体会「几百块就能压垮一个月」的生活。他的建议本身没有错，只是忽略了两个人的开局条件完全不同。这不是傲慢，是环境带来的认知局限——他从未体验过没有安全网的人生。",
            choices = listOf(
                DialogueChoice("认同对方", "你说得对，我会考虑的。", "你没有反驳——不是因为认同，是因为你知道解释起来太累了。他说得没错，但「对」和「能做到」之间，隔着你的整个生活。"),
                DialogueChoice("说出事实", "我每个月工资寄回家之后只剩几百块。你说的培训班，学费够我活三个月。", "你平静地说出了事实。对方的表情从自信变成了错愕——他第一次意识到，他口中的「机会」对你是奢侈品。"),
                DialogueChoice("沉默聆听", "……", "你只是听着。他以为你在认真考虑他的建议。你心里明白——你们的起点不同，对「努力」的定义也不同。")
            )
        ),

        // ---------- 场景4：小店店主 ↔ 基层公务员 ----------
        CrossProfessionDialogue(
            id = "shop_to_civil",
            speakerPathId = "shop_owner",
            speakerName = "小店店主",
            listenerPathId = "civil_servant",
            listenerName = "基层公务员",
            triggerContext = "公务员来店里买东西时闲聊",
            speakerOpeningLine = "你们上班多好啊，不愁生意、不愁客源，工资月底准时到账。不像我，一天不开张就一天没收入，天天提心吊胆。",
            bodyFeeling = "对方用羡慕的语气说起你的工作——稳定、不用担心失业、工资按时发。他守店多年，最渴望的就是这种「不用担心明天」的安稳。",
            sparkle = "小店店主每天面对不确定的客流和收入，最稀缺的就是「稳定」二字。他看到公务员的稳定——准时发薪、社保齐全、不用担心明天——这是他在自己生活里最渴望的东西。",
            realityCheck = "公务员的稳定是有代价的：严格的考勤制度、层层审批的流程、无休止的文书工作、上级和群众的双重压力。稳定背后的「不自由」，是小店店主自己当老板时不用承受的。两种生活各有得失。",
            townCommentary = "自主经营习惯了自由支配时间，便难以理解体制内严格管理的另一面。同样，体制内的人也难以体会小店主的收入焦虑。环境隔绝了彼此的生活，便形成了各自的盲区——没有谁更正确，只是处境不同。",
            choices = listOf(
                DialogueChoice("认同对方", "也不容易，大家都一样。", "你没有多说什么。这句话既不是敷衍，也不是诉苦——只是承认了生活没有容易的。"),
                DialogueChoice("说出事实", "我们每天要写十几份报告、应付各种检查。你起码不用看谁的脸色。", "你透露了稳定背后的代价。对方若有所思——原来他羡慕的「稳定」，也有他不了解的束缚。"),
                DialogueChoice("沉默聆听", "……", "你只是听着。他以为你默认了他的羡慕——而你知道，你们各自羡慕着对方拥有而自己缺少的东西。")
            )
        ),

        // ---------- 场景5：退休工人 ↔ 应届毕业生 ----------
        CrossProfessionDialogue(
            id = "retired_to_graduate",
            speakerPathId = "retired_worker",
            speakerName = "退休工人",
            listenerPathId = "fresh_graduate",
            listenerName = "应届毕业生",
            triggerContext = "社区公园里偶遇，聊起工作和生活",
            speakerOpeningLine = "你们现在的年轻人啊，动不动就辞职、跳槽，一点苦都吃不了。我们那会儿，在一个单位一干就是一辈子，再苦再累也扛着。",
            bodyFeeling = "对方用老一辈的口吻评价你的职业选择——他觉得你不够能吃苦、不够稳定。他的语气里带着一种「过来人」的笃定。",
            sparkle = "退休工人一辈子在一个单位，那个年代的工作是铁饭碗，努力就有回报、工龄就是资历。他用自己一生的经验来衡量年轻人——他不是故意要批判，只是他的人生经验告诉他：坚持就是胜利。",
            realityCheck = "现在的就业市场和他那个年代完全不同。灵活用工、35岁危机、行业快速迭代——年轻人不是不想稳定，是稳定本身就变成了奢侈品。跳槽有时候不是任性，是生存策略。时代变了，衡量「吃苦」的标准也变了。",
            townCommentary = "一辈子遵循工厂节奏的人，很难理解当代年轻人面对的全新就业环境。他的经验框架是基于他那个时代的——这不是固执，是时代和环境带来的认知局限。两代人的「吃苦」，不是同一种苦。",
            choices = listOf(
                DialogueChoice("认同对方", "您说得对，我们这代确实不如你们那代能吃苦。", "你顺着他的话说了。他满意地点点头——但你知道，这不是「吃苦」的问题，是时代不同了。"),
                DialogueChoice("说出事实", "我们不是不想稳定——是现在公司不跟你签长期合同，35岁就被优化了。不是我们不想待，是待不住。", "你用事实回应了他的判断。他沉默了一会儿，然后叹了口气——也许他想起了自己的子女。"),
                DialogueChoice("沉默聆听", "……", "你只是听着，没有反驳。他有他的时代经验，你有你的现实处境。你们之间隔着的不是代沟，是整个时代。")
            )
        ),

        // ---------- 场景6：教师视角（用应届毕业生代）↔ 工地工人 ----------
        CrossProfessionDialogue(
            id = "graduate_as_teacher_to_worker",
            speakerPathId = "fresh_graduate",
            speakerName = "师范毕业生（准教师）",
            listenerPathId = "construction_worker",
            listenerName = "工地工人",
            triggerContext = "放学路上经过工地，和休息的工人搭话",
            speakerOpeningLine = "你们在外面干活，风吹日晒、自由自在的，比我们整天待在教室里舒服多了。我有时候真羡慕你们。",
            bodyFeeling = "对方用羡慕的语气看着你的工作环境——觉得户外劳动是自由的、不受拘束的。他长期待在教室和办公室，真的向往开阔的空间。",
            sparkle = "教师长期面对固定教室、固定课表、固定学生，生活节奏按学期运转。他发自内心地向往户外、自由和变化——这份羡慕背后，是对自己职业环境束缚感的真实表达。",
            realityCheck = "工地劳动没有「自由自在」——每天固定工时、固定工期，风雨无阻。高温40度照样搬砖，暴雨天也要抢工期。教室里的闷热和工地上的暴晒，是两种不同的难受。他看到的「自由」，是体力劳动者用身体换来的。",
            townCommentary = "长期处在固定环境中的人，容易理想化另一种完全不同的人生。这不是谁的错——教师在教室里教书育人，工人在工地上建设城市，都在用自己的方式认真生活。不理解对方，只是因为环境隔开了彼此。",
            choices = listOf(
                DialogueChoice("认同对方", "各有各的好吧，你们也不容易。", "你没有多解释。他也许是真心羡慕，也许只是随口一说——你选择不较真。"),
                DialogueChoice("说出事实", "你今天站了多久？我在外面站了十个小时了，腰已经不是自己的了。", "你用身体的事实回应了他的羡慕。他看了一眼你的工装和劳保手套，不再说话了。"),
                DialogueChoice("沉默聆听", "……", "你只是擦了擦汗。他以为你在享受户外的自由——你不知道怎么告诉他，你只是没有选择。")
            )
        )
    )

    /** 根据发言者和倾听者身份查找对话 */
    fun findDialogue(speakerPathId: String, listenerPathId: String): CrossProfessionDialogue? {
        return dialogues.find { it.speakerPathId == speakerPathId && it.listenerPathId == listenerPathId }
    }

    /** 查找所有发言者为指定身份的对话 */
    fun findBySpeaker(speakerPathId: String): List<CrossProfessionDialogue> {
        return dialogues.filter { it.speakerPathId == speakerPathId }
    }

    /** 查找所有涉及指定身份的对话（作为发言者或倾听者） */
    fun findByParticipant(pathId: String): List<CrossProfessionDialogue> {
        return dialogues.filter { it.speakerPathId == pathId || it.listenerPathId == pathId }
    }
}