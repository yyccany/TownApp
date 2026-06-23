package com.example.townapp.data

// ============================================
// 📚 深度分层 - 成语数据（学术注脚 + 哲学思考）
// ============================================
val idiomDepthItems = listOf(
    // 1. 画饼充饥
    IdiomItem(
        id = 1,
        idiom = "画饼充饥",
        traditionalMeaning = "画个饼来解除饥饿。比喻用空想来安慰自己。",
        modernInterpretation = "现代版「画饼」：老板给你谈理想、谈未来、谈期权，就是不谈工资。你明知道是虚的，但还是听得津津有味，因为大脑会对「预期奖励」产生真实的愉悦感。",
        logicalAnalysis = "我们的大脑分不清「真实奖励」和「想象奖励」——只要激活了奖赏回路，多巴胺就会分泌，不管这个奖励是现在就能拿到还是十年后才兑现。",
        category = "消费误区",
        relatedCognitiveBiases = listOf("确认偏误", "锚定效应"),
        awakeningValue = 2,
        // 第二层：学术注脚
        academicFootnote = AcademicFootnote(
            reference = "预期奖励的神经机制",
            author = "Schultz et al.",
            year = "1997",
            explanation = "多巴胺神经元不仅对实际奖励有反应，对预示奖励的线索也会有反应。预期奖励会激活与真实奖励相同的神经回路，这就是为什么「画饼」真的能「充饥」。"
        ),
        // 第四层：哲学思考
        philosophicalReflection = PhilosophicalReflection(
            question = "你真正想要的，是那个「饼」，还是「画饼」带来的希望感？",
            reflection = "如果明天就是生命的最后一天，你还会为了一个虚无缥缈的未来而牺牲现在吗？"
        )
    ),
    
    // 2. 掩耳盗铃
    IdiomItem(
        id = 2,
        idiom = "掩耳盗铃",
        traditionalMeaning = "偷铃铛的人怕铃响，把自己耳朵堵住，以为自己听不见别人也听不见。比喻自己欺骗自己。",
        modernInterpretation = "现代版「掩耳盗铃」：熬夜的时候敷个面膜，喝可乐的时候选无糖版，吃炸鸡的时候去皮——你以为这样就健康了，其实只是在自我安慰。",
        logicalAnalysis = "当行为和认知产生冲突时，我们会通过改变认知来缓解失调，而不是改变行为。这就是为什么「自我欺骗」如此普遍。",
        category = "健康认知",
        relatedCognitiveBiases = listOf("认知失调", "自我欺骗"),
        awakeningValue = 3,
        academicFootnote = AcademicFootnote(
            reference = "认知失调理论",
            author = "Festinger",
            year = "1957",
            explanation = "当个体同时持有两种相互矛盾的认知时，会产生心理不适感。为了缓解这种失调，人们会调整其中一个认知，或者寻找理由来合理化矛盾的行为。"
        ),
        philosophicalReflection = PhilosophicalReflection(
            question = "你是真的想变得健康，还是只是想「觉得」自己很健康？",
            reflection = "自欺欺人是最容易的选择，但真相不会因为你捂住耳朵就消失。"
        )
    ),
    
    // 3. 亡羊补牢
    IdiomItem(
        id = 3,
        idiom = "亡羊补牢",
        traditionalMeaning = "羊丢了再去修补羊圈，还不算晚。比喻出了问题以后想办法补救，可以防止继续受损失。",
        modernInterpretation = "现代版「亡羊补牢」：体检出问题了才开始养生，信用卡刷爆了才开始存钱，颈椎病犯了才想起活动——我们总是在付出代价后才开始行动，但总比不做强。",
        logicalAnalysis = "损失带来的痛苦比收益带来的快乐更强烈，这就是为什么我们总是在失去后才懂得珍惜。",
        category = "消费误区",
        relatedCognitiveBiases = listOf("损失厌恶", "即时满足偏差"),
        awakeningValue = 1,
        academicFootnote = AcademicFootnote(
            reference = "损失厌恶的神经基础",
            author = "Tom et al.",
            year = "2007",
            explanation = "功能磁共振成像研究显示，损失会激活大脑中与疼痛相关的区域，而收益激活的是奖赏区域。损失的心理权重是收益的2-2.5倍。"
        ),
        philosophicalReflection = PhilosophicalReflection(
            question = "为什么总是要等到失去后，才知道什么是珍贵的？",
            reflection = "如果我们能够像想象失去那样去想象拥有，或许我们会更懂得珍惜当下。"
        )
    ),
    
    // 4. 守株待兔
    IdiomItem(
        id = 4,
        idiom = "守株待兔",
        traditionalMeaning = "宋国有个农夫，看见一只兔子撞在树桩上死了，便放下锄头在树桩旁等待，希望再得到撞死的兔子。比喻死守经验，不知变通。",
        modernInterpretation = "现代版「守株待兔」：靠运气赚了一次钱，就以为找到了致富密码，于是all in，结果亏得精光。偶然的成功比失败更可怕，因为它会让你误解这个世界的运行规律。",
        logicalAnalysis = "我们总是试图在随机事件中寻找规律，把运气当成能力，把偶然当成必然。",
        category = "消费误区",
        relatedCognitiveBiases = listOf("幸存者偏差", "赌徒谬误"),
        awakeningValue = 4,
        academicFootnote = AcademicFootnote(
            reference = "控制错觉",
            author = "Langer",
            year = "1975",
            explanation = "人们倾向于高估自己对事件的控制能力，即使事件完全是随机的。在一个经典实验中，人们会为自己选的彩票号码出更高的价格，尽管中奖概率完全一样。"
        ),
        philosophicalReflection = PhilosophicalReflection(
            question = "你怎么知道你以为的「能力」，其实不是「运气」？",
            reflection = "如果成功是运气，那失败也是。真正的智慧是知道两者的区别。"
        )
    ),
    
    // 5. 杯弓蛇影
    IdiomItem(
        id = 5,
        idiom = "杯弓蛇影",
        traditionalMeaning = "有人请客吃饭，挂在墙上的弓映在酒杯里，客人以为杯里有蛇，疑心喝下了蛇，回去就病了。比喻疑神疑鬼，自相惊扰。",
        modernInterpretation = "现代版「杯弓蛇影」：刷到一条健康科普，就觉得自己浑身是病；看到别人裁员，就担心自己也会被裁。我们的大脑对「威胁」过度敏感，这是进化的遗产，但在现代社会常常误报。",
        logicalAnalysis = "我们的大脑是为了「求存」而不是「求真」进化的——误报一千次没关系，漏报一次狮子就没命了。",
        category = "健康认知",
        relatedCognitiveBiases = listOf("可得性启发", "焦虑放大"),
        awakeningValue = 2,
        academicFootnote = AcademicFootnote(
            reference = "负性偏差",
            author = "Baumeister et al.",
            year = "2001",
            explanation = "坏信息比好信息更有力量：坏印象更容易形成，也更难改变；坏情绪更持久，坏事件影响更大。这种偏差是进化的产物，因为对威胁保持敏感更有利于生存。"
        ),
        philosophicalReflection = PhilosophicalReflection(
            question = "你害怕的，是真实的危险，还是想象中的恐惧？",
            reflection = "大多数让你焦虑的事情，永远都不会发生。但焦虑本身，却真实地消耗着你的生命。"
        )
    ),
    
    // 6. 朝三暮四
    IdiomItem(
        id = 6,
        idiom = "朝三暮四",
        traditionalMeaning = "养猴人给猴子橡子，说早上三颗晚上四颗，猴子很生气；说早上四颗晚上三颗，猴子就高兴了。比喻反复无常，也揭露了框架效应。",
        modernInterpretation = "现代版「朝三暮四」：同样是花100块钱，「满100减20」你觉得赚了，「不包邮10块」你觉得亏了。本质一样，但描述方式不同，你的感受完全不同——这就是框架效应。",
        logicalAnalysis = "同样的事实，不同的框架，会触发完全不同的心理机制。",
        category = "消费误区",
        relatedCognitiveBiases = listOf("框架效应", "损失厌恶"),
        awakeningValue = 3,
        academicFootnote = AcademicFootnote(
            reference = "框架效应",
            author = "Tversky & Kahneman",
            year = "1981",
            explanation = "同一个问题用「收益」框架描述，人们倾向于风险规避；用「损失」框架描述，人们倾向于风险寻求。框架的微小变化，能导致决策的巨大反转。"
        ),
        philosophicalReflection = PhilosophicalReflection(
            question = "你以为你在做选择，其实你只是在被框架选择？",
            reflection = "跳出框架的第一步，是意识到框架的存在。"
        )
    ),
    
    // 7. 井底之蛙
    IdiomItem(
        id = 7,
        idiom = "井底之蛙",
        traditionalMeaning = "井底的青蛙只能看到井口那么大的一块天。比喻见识短浅的人。",
        modernInterpretation = "现代版「井底之蛙」：算法给你推荐的内容都是你喜欢看的，你以为这就是整个世界。信息茧房让每个人都活在自己的井口之下，还觉得自己看得挺远。",
        logicalAnalysis = "我们只能看到我们能看到的，并且以为我们能看到的就是全部。",
        category = "认知偏差",
        relatedCognitiveBiases = listOf("确认偏误", "信息茧房"),
        awakeningValue = 4,
        academicFootnote = AcademicFootnote(
            reference = "信息茧房",
            author = "Sunstein",
            year = "2006",
            explanation = "在算法推荐时代，我们被喂食我们喜欢的内容，结果是视野越来越窄，偏见越来越深。我们以为自己看到了整个世界，其实我们只看到了算法想让我们看到的。"
        ),
        philosophicalReflection = PhilosophicalReflection(
            question = "你怎么知道你以为的「真理」，其实不是「偏见」？",
            reflection = "真正的智慧，是意识到自己所知甚少。"
        )
    ),
    
    // 8. 揠苗助长
    IdiomItem(
        id = 8,
        idiom = "揠苗助长",
        traditionalMeaning = "宋国有个人嫌禾苗长得慢，就把禾苗往上拔，结果禾苗都枯死了。比喻违反事物发展的客观规律，急于求成，反而坏事。",
        modernInterpretation = "现代版「揠苗助长」：给孩子报十几个补习班，恨不得他一岁就能背唐诗三百首。我们太急于看到结果，却忘了成长有自己的节奏，拔苗助长只会适得其反。",
        logicalAnalysis = "我们想要立竿见影的效果，却忘记了好东西都需要时间来酝酿。",
        category = "消费误区",
        relatedCognitiveBiases = listOf("即时满足偏差", "控制错觉"),
        awakeningValue = 2,
        academicFootnote = AcademicFootnote(
            reference = "延迟满足",
            author = "Mischel",
            year = "1972",
            explanation = "棉花糖实验发现，能够延迟满足的孩子在未来更成功。但现代社会正在训练我们越来越无法等待——一切都要即时，最好是马上。"
        ),
        philosophicalReflection = PhilosophicalReflection(
            question = "你想要的，是结果，还是结果到来的过程？",
            reflection = "如果人生是一场马拉松，你为什么要按百米冲刺的节奏来跑？"
        )
    ),
    
    // 9. 买椟还珠
    IdiomItem(
        id = 9,
        idiom = "买椟还珠",
        traditionalMeaning = "楚国人到郑国卖珍珠，装珍珠的盒子做得非常精美，郑国人买了盒子，把珍珠还回去了。比喻没有眼光，取舍不当。",
        modernInterpretation = "现代版「买椟还珠」：为了凑满减买了一堆没用的东西，为了赠品买了不需要的主商品，为了包装好看买了难喝的奶茶——我们常常为了「椟」而买单，却忘了真正想要的是「珠」。",
        logicalAnalysis = "我们会因为次要因素做出购买决定，而忽略真正重要的核心价值。",
        category = "消费误区",
        relatedCognitiveBiases = listOf("锚定效应", "禀赋效应"),
        awakeningValue = 3,
        academicFootnote = AcademicFootnote(
            reference = "符号消费",
            author = "Baudrillard",
            year = "1970",
            explanation = "在消费社会，我们消费的不只是商品的使用价值，更是符号价值——品牌、身份、地位、品味。很多时候，符号价值比使用价值还重要。"
        ),
        philosophicalReflection = PhilosophicalReflection(
            question = "你买的，是你需要的，还是你想要的？",
            reflection = "需要是有限的，想要是无限的。知道两者的区别，是自由的开始。"
        )
    ),
    
    // 10. 刻舟求剑
    IdiomItem(
        id = 10,
        idiom = "刻舟求剑",
        traditionalMeaning = "楚国人坐船时剑掉水里了，他在船舷上刻了个记号，等船靠岸了才按记号下水找剑。比喻拘泥成例，不知道跟着情势的变化而改变看法或办法。",
        modernInterpretation = "现代版「刻舟求剑」：用十年前的经验指导现在的生活，用父母的经验过自己的人生。世界在变，你却还在守着那个刻痕找剑，怎么可能找得到？",
        logicalAnalysis = "我们倾向于沿用过去的成功经验，却忘了世界永远在变化。",
        category = "认知偏差",
        relatedCognitiveBiases = listOf("认知固化", "路径依赖"),
        awakeningValue = 4,
        academicFootnote = AcademicFootnote(
            reference = "路径依赖",
            author = "Arthur",
            year = "1989",
            explanation = "一旦人们做了某种选择，就好比走上了一条不归路，惯性的力量会使这一选择不断自我强化，让你轻易走不出去。"
        ),
        philosophicalReflection = PhilosophicalReflection(
            question = "你在用过去的地图，找未来的路吗？",
            reflection = "唯一不变的，是变化本身。"
        )
    )
)
