package com.example.townapp.feature.human_narrative.cognition

/**
 * 双生存法则模拟器 - 场景数据
 * 
 * 模拟10个核心生活场景，展示不同选择在不同阶层的后果
 */

object SimulatorScenarios {
    
    /**
     * 获取所有场景
     */
    fun getAllScenarios(): List<SimulatorScenario> = listOf(
        // 场景1：亲戚借钱
        createBorrowingScenario(),
        // 场景2：同事加班
        createOvertimeScenario(),
        // 场景3：朋友吐槽
        createComplaintScenario(),
        // 场景4：父母问工资
        createSalaryScenario(),
        // 场景5：陌生人求助
        createStrangerScenario(),
        // 场景6：领导画饼
        createBossScenario(),
        // 场景7：亲戚攀比
        createComparisonScenario(),
        // 场景8：朋友借钱不还
        createDebtScenario(),
        // 场景9：同事甩锅
        createBlameScenario(),
        // 场景10：过年回家
        createHomecomingScenario()
    )
    
    // ============================================
    // 场景1：亲戚借钱买房
    // ============================================
    private fun createBorrowingScenario(): SimulatorScenario {
        return SimulatorScenario(
            id = "borrow_money",
            title = "亲戚找你借10万块钱买房",
            description = "你的表弟要买婚房，首付差10万。他找你借，说一年后还你。你知道他的工资不高，还款能力存疑。",
            applicableClasses = SocialClass.entries.toList(),
            traditionalChoice = ScenarioChoice(
                id = "traditional_borrow",
                title = "借给他（传统道德：亲情无价）",
                description = "毕竟是亲戚，不帮说不过去。钱是身外之物，亲情最重要。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "亲戚会到处夸你孝顺、懂事",
                        longTermResult = "亲戚大概率不会按时还钱。以后他还会找你借更多的钱。你自己省吃俭用，把钱借给别人，最后你还是穷，别人只会觉得你傻。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "损失10万，可能永远要不回来", 5),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系变复杂，他可能会蹬鼻子上脸", 4),
                            ImpactResult(ImpactDimension.TIME, "追债耗费大量时间和精力", 4),
                            ImpactResult(ImpactDimension.MENTAL, "后悔、自责、生活压力增大", 4)
                        ),
                        quote = "升米恩，斗米仇。你帮了他100次，只要有1次不帮，你就成了坏人。",
                        keyInsight = "底层借贷的核心问题：你没有筹码，他不会珍惜。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "亲戚会感谢你，会在别人面前夸你",
                        longTermResult = "他还钱会很慢，可能要拖个三五年。以后他还会找你帮忙，但不会太过分。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "损失利息收益，资金流动性降低", 3),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系更亲近，但多了一层债务纠葛", 2),
                            ImpactResult(ImpactDimension.REPUTATION, "获得'讲义气'的名声", 2),
                            ImpactResult(ImpactDimension.MENTAL, "担心还钱问题，但可以承受", 2)
                        ),
                        quote = "中层借贷的风险：你能承受损失，但会形成习惯性依赖。",
                        keyInsight = "中层借贷的核心问题：你有筹码，但关系会变味。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "亲戚会对你感恩戴德，会到处说你是大好人",
                        longTermResult = "他会尽快把钱还给你，还会给你带点礼物。以后你有什么事，他会尽力帮你。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "本金安全，获得利息和礼物回报", 1),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "获得一个忠诚的'小弟'", 2),
                            ImpactResult(ImpactDimension.REPUTATION, "家族地位进一步巩固", 3),
                            ImpactResult(ImpactDimension.MENTAL, "建立长期人情投资", 1)
                        ),
                        quote = "有钱人的'帮助'不是施舍，是投资。",
                        keyInsight = "上层借贷的核心：你帮助他，他回馈你，形成良性循环。"
                    )
                )
            ),
            realisticChoice = ScenarioChoice(
                id = "realistic_borrow",
                title = "不借，找借口拒绝（现实法则：保护自己的利益）",
                description = "直接借钱风险太大。你可以说自己也在还房贷、买了理财产品取不出来，或者给他推荐银行借贷。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "亲戚会有点不高兴",
                        longTermResult = "亲戚会到处说你不孝、忘本、有钱了就不认人。全家族都会骂你。但你保住了自己的10万块钱，你可以用这笔钱改善自己的生活。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "保住10万，可以用于自我投资", 5),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系变淡，但避免了更大的麻烦", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "家族评价下降，被说'自私'", 3),
                            ImpactResult(ImpactDimension.MENTAL, "短期内有压力，但长期解脱", 2)
                        ),
                        quote = "保住本金，才有可能改变命运。",
                        keyInsight = "底层拒绝的核心：你没有损失什么，因为借出去基本也要不回来。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "亲戚会有点尴尬，但不会太得罪你",
                        longTermResult = "他会去找别人借钱。你们的关系会变淡，但不会破裂。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "保住本金和利息", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系变淡，但保持基本体面", 2),
                            ImpactResult(ImpactDimension.REPUTATION, "被说'不够意思'，但不会影响太大", 2),
                            ImpactResult(ImpactDimension.MENTAL, "有点愧疚，但总体轻松", 1)
                        ),
                        quote = "中层拒绝的核心：给个台阶，不伤和气。",
                        keyInsight = "中层拒绝的艺术：不是直接说不，而是给出一个合理的替代方案。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "亲戚会有点尴尬，但不会说什么",
                        longTermResult = "他知道你有钱，也知道你不借是应该的。你们的关系不会受到太大影响。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "资金安全", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系基本不变", 1),
                            ImpactResult(ImpactDimension.REPUTATION, "被认为'有原则'", 2),
                            ImpactResult(ImpactDimension.MENTAL, "无压力", 1)
                        ),
                        quote = "有钱人的'不借'不会得罪人，因为对方知道你本来就没有义务。",
                        keyInsight = "上层拒绝的核心：你有实力，拒绝也是一种尊重。"
                    )
                )
            )
        )
    }
    
    // ============================================
    // 场景2：同事找你帮忙加班
    // ============================================
    private fun createOvertimeScenario(): SimulatorScenario {
        return SimulatorScenario(
            id = "overtime_help",
            title = "同事找你帮忙加班做他的工作",
            description = "你的同事小李要下班接孩子，但他手头有个报告还没写完。他请你帮忙顶一下，说明天请你喝奶茶。",
            applicableClasses = SocialClass.entries.toList(),
            traditionalChoice = ScenarioChoice(
                id = "traditional_overtime",
                title = "帮忙（传统道德：助人为乐）",
                description = "都是同事，互相帮忙很正常。喝杯奶茶也不错。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "同事感谢你，说'你人真好'",
                        longTermResult = "小李以后会经常找你帮忙，因为他发现你'好说话'。你的时间被大量占用，工作效率下降，还没人记得你的好。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "失去的时间可以用于自我提升", 4),
                            ImpactResult(ImpactDimension.TIME, "加班到很晚，影响第二天状态", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为'软柿子'，被更多人找", 4),
                            ImpactResult(ImpactDimension.MENTAL, "疲惫、委屈", 3)
                        ),
                        quote = "职场老好人的结局：累死自己，便宜别人。",
                        keyInsight = "底层职场的残酷：你越'好说话'，活越多。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "同事感谢你",
                        longTermResult = "小李偶尔会找你帮忙，但不会太过分。你的人缘不错，但工作效率会受影响。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "偶尔一杯奶茶", 1),
                            ImpactResult(ImpactDimension.TIME, "偶尔加班，不影响太大", 2),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "人缘好，但被当成'工具人'", 2),
                            ImpactResult(ImpactDimension.MENTAL, "偶尔委屈，但可以接受", 2)
                        ),
                        quote = "中层职场的现实：帮助是相互的，但不能没有底线。",
                        keyInsight = "中层职场的选择：帮可以，但要让他知道你也有成本。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "同事很感激你",
                        longTermResult = "小李成为你的'支持者'，在你需要的时候也会帮你。你们形成了良好的互助关系。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "奶茶升级为请客吃饭", 1),
                            ImpactResult(ImpactDimension.TIME, "你选择性地帮助，不影响核心工作", 2),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "建立互惠网络", 3),
                            ImpactResult(ImpactDimension.MENTAL, "助人的快乐", 1)
                        ),
                        quote = "上层职场的逻辑：帮助是一种投资，不是施舍。",
                        keyInsight = "上层职场的优势：你有底气选择帮谁、帮什么。"
                    )
                )
            ),
            realisticChoice = ScenarioChoice(
                id = "realistic_overtime",
                title = "拒绝或提出条件（现实法则：有条件的帮助）",
                description = "你可以直接拒绝，也可以提出条件：'我今天有事，但明天下午可以帮你review一下'。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "同事有点失望",
                        longTermResult = "同事不会再轻易找你帮忙。你保住了自己的时间，但可能被认为'不够合群'。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "保住时间，可以自我提升", 4),
                            ImpactResult(ImpactDimension.TIME, "准点下班", 3),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系变淡，但避免了被当成软柿子", 3),
                            ImpactResult(ImpactDimension.MENTAL, "更轻松，有时间提升自己", 3)
                        ),
                        quote = "底层拒绝的艺术：不是'不能帮'，是'这次不方便'。",
                        keyInsight = "底层拒绝的好处：不被当成软柿子，有时间提升自己。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "同事理解你的立场",
                        longTermResult = "同事会自己想办法，或者找别人帮忙。你们的关系不会受影响，甚至可能更尊重你。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "保住时间", 3),
                            ImpactResult(ImpactDimension.TIME, "准点下班", 3),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "被尊重，有边界感", 3),
                            ImpactResult(ImpactDimension.MENTAL, "舒适，有掌控感", 3)
                        ),
                        quote = "中层拒绝的好处：被当成有原则的人，而不是老好人。",
                        keyInsight = "中层拒绝的艺术：给出替代方案，显示你在乎但不迁就。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "同事完全理解",
                        longTermResult = "同事会感激你的直接，不会有怨气。你们的关系基于互相尊重，而不是一方付出。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "无影响", 1),
                            ImpactResult(ImpactDimension.TIME, "完全掌控自己的时间", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系基于平等和尊重", 4),
                            ImpactResult(ImpactDimension.MENTAL, "高度自主和满足", 4)
                        ),
                        quote = "上层拒绝的境界：拒绝是一种尊重，不是冒犯。",
                        keyInsight = "上层拒绝的资本：你有实力，不需要讨好任何人。"
                    )
                )
            )
        )
    }
    
    // ============================================
    // 场景3：朋友跟你吐槽老板
    // ============================================
    private fun createComplaintScenario(): SimulatorScenario {
        return SimulatorScenario(
            id = "friend_complaint",
            title = "朋友跟你吐槽老板有多恶心",
            description = "你的好朋友小王约你出来喝酒，席间疯狂吐槽他的老板：加班不给钱、抢功甩锅、人品恶劣。他问你：'你说，我老板是不是个傻逼？'",
            applicableClasses = SocialClass.entries.toList(),
            traditionalChoice = ScenarioChoice(
                id = "traditional_complaint",
                title = "跟着一起骂（传统道德：朋友要义气）",
                description = "朋友有难，你要站在他这边。一起骂老板，释放压力。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "朋友觉得你很够义气，你们聊得很开心",
                        longTermResult = "小王可能会录音或者截图，把你说的话当成'证据'。或者他转头跟别人说'我朋友也觉得我老板是傻逼'。你的话可能传到不该传的地方。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "喝酒花钱", 2),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "话可能传出去，成为隐患", 5),
                            ImpactResult(ImpactDimension.REPUTATION, "可能被认为'爱抱怨'", 3),
                            ImpactResult(ImpactDimension.MENTAL, "释放了压力，但埋下隐患", 2)
                        ),
                        quote = "吐槽一时爽，传出去火葬场。",
                        keyInsight = "底层吐槽的风险：朋友的'朋友'可能是你的敌人。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "朋友觉得你很理解他",
                        longTermResult = "小王经常找你吐槽。你成了他的'情绪垃圾桶'。虽然关系更近了，但你吸收了很多负能量。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "多次聚会花费", 2),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为'吐槽对象'，关系不健康", 3),
                            ImpactResult(ImpactDimension.MENTAL, "吸收负能量，情绪受影响", 4),
                            ImpactResult(ImpactDimension.TIME, "时间被占用", 2)
                        ),
                        quote = "朋友的吐槽是毒药，喝多了会中毒。",
                        keyInsight = "中层吐槽的问题：朋友关系变成了'情绪依赖'。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "朋友觉得你在认真听他说话",
                        longTermResult = "小王偶尔找你倾诉，把你当成可以信任的人。你们的关系基于互相支持。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "偶尔聚会花费", 1),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为互相信任的朋友", 2),
                            ImpactResult(ImpactDimension.MENTAL, "给予支持的满足感", 2),
                            ImpactResult(ImpactDimension.TIME, "偶尔陪伴，不频繁", 1)
                        ),
                        quote = "上层倾听的方式：共情但不附和，给建议但不站队。",
                        keyInsight = "上层倾听的价值：帮助朋友解决问题，而不是一起抱怨。"
                    )
                )
            ),
            realisticChoice = ScenarioChoice(
                id = "realistic_complaint",
                title = "倾听但不附和（现实法则：保护自己）",
                description = "你可以听他吐槽，但不要跟着骂。可以用'那你怎么想的？''你有没有考虑过换个环境？'这样的话引导他思考，而不是发泄情绪。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "朋友可能觉得你不够'义气'",
                        longTermResult = "朋友会减少找你吐槽，但你们的关系更健康了。你没有被当成'情绪垃圾桶'，有时间做自己的事。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "减少无效社交花费", 3),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系更平等，不被当成垃圾桶", 3),
                            ImpactResult(ImpactDimension.MENTAL, "情绪稳定，不吸收负能量", 4),
                            ImpactResult(ImpactDimension.TIME, "时间用于自我提升", 3)
                        ),
                        quote = "倾听是善良，附和是愚蠢。",
                        keyInsight = "底层倾听的智慧：让朋友发泄，但不把自己拖下水。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "朋友觉得你很理性",
                        longTermResult = "小王会偶尔找你倾诉，但不会把你当成情绪垃圾桶。你成了他可以真正商量事情的朋友。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "偶尔聚会花费", 1),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为真正的朋友，不是吐槽对象", 3),
                            ImpactResult(ImpactDimension.MENTAL, "给予支持但不吸收负能量", 3),
                            ImpactResult(ImpactDimension.TIME, "时间可控", 2)
                        ),
                        quote = "中层倾听的价值：帮助朋友成长，而不是一起沉沦。",
                        keyInsight = "中层倾听的技巧：问问题引导思考，给建议而不是站队。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "朋友很感激你的理性建议",
                        longTermResult = "小王把你当成导师级别的朋友，会认真听取你的建议。你们的关系基于互相成长。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "正常社交花费", 1),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为高质量人脉网络的一员", 4),
                            ImpactResult(ImpactDimension.MENTAL, "给予智慧支持的满足感", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "被视为有智慧的人", 3)
                        ),
                        quote = "上层倾听的目标：帮助朋友成功，建立互惠网络。",
                        keyInsight = "上层倾听的回报：你帮助的人，日后可能成为你的助力。"
                    )
                )
            )
        )
    }
    
    // ============================================
    // 场景4：父母问你工资多少
    // ============================================
    private fun createSalaryScenario(): SimulatorScenario {
        return SimulatorScenario(
            id = "salary_question",
            title = "父母问你现在工资多少",
            description = "过年回家，妈妈问你：'你现在一个月挣多少钱啊？'你知道如果说实话，可能会被追问、被比较、被担心。",
            applicableClasses = SocialClass.entries.toList(),
            traditionalChoice = ScenarioChoice(
                id = "traditional_salary",
                title = "说实话（传统道德：对父母要坦诚）",
                description = "父母是家人，没什么好隐瞒的。坦诚相待，他们也会理解的。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "妈妈会担心你",
                        longTermResult = "妈妈会到处跟亲戚说'我儿子一个月才挣5000'。全家族都知道你混得不好。他们会'关心'你，给你介绍工作、给你出主意。所有人都在评价你的人生。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为家族谈资，压力倍增", 5),
                            ImpactResult(ImpactDimension.REPUTATION, "家族评价下降，被各种'关心'", 5),
                            ImpactResult(ImpactDimension.MENTAL, "被评判的感觉，自尊受损", 4),
                            ImpactResult(ImpactDimension.MONEY, "可能被借钱、被'资助'", 3)
                        ),
                        quote = "父母的爱，有时候是一种压力。",
                        keyInsight = "底层说实话的风险：父母的爱会变成家族的集体评判。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "妈妈会追问细节",
                        longTermResult = "妈妈会问你存了多少钱、什么时候买房、有没有对象。全家族都知道你的情况，被当成'成功标准'或者'反面教材'。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为家族的'参照物'", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "被拿来比较，压力增大", 3),
                            ImpactResult(ImpactDimension.MENTAL, "被追问的疲惫感", 3),
                            ImpactResult(ImpactDimension.TIME, "应付各种关心", 2)
                        ),
                        quote = "中层说实话的后果：成为家族的'话题人物'。",
                        keyInsight = "中层说实话的成本：你的生活变成公开的话题。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "父母很骄傲",
                        longTermResult = "父母会在亲戚面前炫耀你。你的家族地位提升，成为'别人家的孩子'。但也会有更多人找你帮忙、借钱。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "家族地位提升，但负担也增加", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "被视为成功人士", 4),
                            ImpactResult(ImpactDimension.MENTAL, "被认可的满足感，但也有压力", 2),
                            ImpactResult(ImpactDimension.MONEY, "可能被要求'回馈家族'", 3)
                        ),
                        quote = "上层说实话的好处：成为家族的骄傲，获得更多资源。",
                        keyInsight = "上层说实话的价值：你的成功可以转化为家族资源。"
                    )
                )
            ),
            realisticChoice = ScenarioChoice(
                id = "realistic_salary",
                title = "模糊回答（现实法则：保护隐私）",
                description = "'还行吧，够花''比上不足比下有余''工作还在发展中，先不急着看工资'。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "妈妈会追问",
                        longTermResult = "妈妈会继续追问，但你可以用'工作忙''还要加班''先不聊这个'来转移话题。最终她还是会担心，但至少你的具体数字不会传出去。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "妈妈担心但不会传出去", 2),
                            ImpactResult(ImpactDimension.MENTAL, "避免被评判的轻松感", 4),
                            ImpactResult(ImpactDimension.REPUTATION, "不会被拿来比较", 3),
                            ImpactResult(ImpactDimension.TIME, "减少应付追问的时间", 3)
                        ),
                        quote = "模糊是一种保护，不是欺骗。",
                        keyInsight = "底层模糊的好处：保护隐私，减少家族压力。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "妈妈有点不满意这个回答",
                        longTermResult = "她会追问几次，但你坚持不说。最终她会接受，但你可能会被说'不够坦诚'。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系基本不变", 1),
                            ImpactResult(ImpactDimension.MENTAL, "避免被追问的轻松感", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "不会被精确比较", 3),
                            ImpactResult(ImpactDimension.TIME, "减少应付追问的时间", 2)
                        ),
                        quote = "中层模糊的艺术：给一个满意的答案，但不是完整的答案。",
                        keyInsight = "中层模糊的技巧：用'发展'代替'数字'。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "父母会很骄傲",
                        longTermResult = "他们会骄傲但不会到处说，因为他们理解'闷声发大财'的道理。你的隐私得到了保护，但父母的骄傲也得到了满足。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系更好，父母理解你", 3),
                            ImpactResult(ImpactDimension.MENTAL, "隐私保护，同时满足父母", 4),
                            ImpactResult(ImpactDimension.REPUTATION, "不会成为炫耀工具", 3),
                            ImpactResult(ImpactDimension.TIME, "减少被追问的时间", 2)
                        ),
                        quote = "上层模糊的境界：既满足父母，又保护自己。",
                        keyInsight = "上层模糊的优势：有底气说不，也有智慧怎么说不。"
                    )
                )
            )
        )
    }
    
    // ============================================
    // 场景5：陌生人找你帮忙
    // ============================================
    private fun createStrangerScenario(): SimulatorScenario {
        return SimulatorScenario(
            id = "stranger_help",
            title = "火车站有人找你帮忙转账",
            description = "在火车站候车，一个穿着体面的中年人走过来，说自己手机没电了，身上现金不够，能不能请你帮忙转2000块钱给他，他到时候转回给你。",
            applicableClasses = SocialClass.entries.toList(),
            traditionalChoice = ScenarioChoice(
                id = "traditional_stranger",
                title = "帮忙转账（传统道德：助人为乐）",
                description = "人家有困难，能帮就帮。火车站又不是骗子窝，看他穿着体面，应该不是骗子。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "对方很感激，说'你真是好人'",
                        longTermResult = "2000块钱打了水漂。那个'体面的中年人'从此消失。你报警也没用，这种诈骗破案率极低。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "损失2000元，可能永远追不回", 5),
                            ImpactResult(ImpactDimension.MENTAL, "自责、后悔、愤怒", 5),
                            ImpactResult(ImpactDimension.TIME, "报警、录口供耗费大量时间", 4)
                        ),
                        quote = "火车站的求助，99%是诈骗。",
                        keyInsight = "底层帮陌生人的风险：你的善良会被利用。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "对方很感激",
                        longTermResult = "大概率是诈骗。2000块钱虽然能承受，但也是一笔损失。而且会觉得自己很蠢，影响心情。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "损失2000元", 4),
                            ImpactResult(ImpactDimension.MENTAL, "自我怀疑", 3),
                            ImpactResult(ImpactDimension.TIME, "报警、录口供", 3)
                        ),
                        quote = "中层被骗的代价：钱能承受，但自尊受损。",
                        keyInsight = "中层帮陌生人的问题：损失可承受，但会怀疑自己的判断力。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "对方很感激",
                        longTermResult = "如果是诈骗，损失2000但不会影响生活。如果不是诈骗，你可能建立一段新的人脉。概率极低，但回报可能很高。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "大概率损失2000", 3),
                            ImpactResult(ImpactDimension.MENTAL, "损失可承受", 2),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "极低概率建立人脉", 1)
                        ),
                        quote = "上层帮陌生人的逻辑：这是一次博弈，不是慈善。",
                        keyInsight = "上层帮陌生人的资本：承受得起可能的损失。"
                    )
                )
            ),
            realisticChoice = ScenarioChoice(
                id = "realistic_stranger",
                title = "拒绝，指路去找工作人员（现实法则：保护自己）",
                description = "火车站有警察、有工作人员。找他们比找我更合适。我的钱是我的，我没有义务帮你。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "对方会换个目标",
                        longTermResult = "你保住了2000块钱。你没有成为受害者。你在心里默默感谢自己没有'善良'。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "保住2000元", 5),
                            ImpactResult(ImpactDimension.MENTAL, "庆幸自己没被骗", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "在骗子眼里你是'不好骗的人'", 2)
                        ),
                        quote = "拒绝骗子，不是冷漠，是智慧。",
                        keyInsight = "底层拒绝的价值：你保护的是你自己，改变不了的事情。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "对方会换个目标",
                        longTermResult = "你保住了钱，也不会有'自己是不是太冷漠'的自我怀疑。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "保住2000元", 5),
                            ImpactResult(ImpactDimension.MENTAL, "没有自我怀疑", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "在骗子眼里你是'不好骗的人'", 2)
                        ),
                        quote = "中层拒绝的好处：保护自己的利益，不需要自我怀疑。",
                        keyInsight = "中层拒绝的智慧：你知道自己做了正确的选择。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "对方会换个目标",
                        longTermResult = "你根本没把这当成选项。你知道自己的时间和钱都很宝贵，不会浪费在这种地方。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "无影响", 1),
                            ImpactResult(ImpactDimension.MENTAL, "完全无压力", 1),
                            ImpactResult(ImpactDimension.TIME, "无影响", 1)
                        ),
                        quote = "上层拒绝的境界：根本不在考虑范围内。",
                        keyInsight = "上层拒绝的资本：时间和钱都宝贵，不会浪费在低回报的事情上。"
                    )
                )
            )
        )
    }
    
    // ============================================
    // 场景6：领导给你'画饼'
    // ============================================
    private fun createBossScenario(): SimulatorScenario {
        return SimulatorScenario(
            id = "boss_promise",
            title = "领导说'好好干，年终奖不会亏待你'",
            description = "你连续加班一个月完成项目，老板在总结会上拍着你的肩膀说：'好好干，年终奖不会亏待你。'但你隐约知道公司今年效益不好，年终奖可能很少。",
            applicableClasses = SocialClass.entries.toList(),
            traditionalChoice = ScenarioChoice(
                id = "traditional_boss",
                title = "相信领导，继续努力（传统道德：敬业守信）",
                description = "领导都这么说了，肯定是有道理的。我好好干，年终奖应该不会差。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "领导觉得你很上进",
                        longTermResult = "年终奖只有半个月工资，还被扣了各种'项目损耗'。领导早就忘了当初说的话，或者记得但选择忘记。你继续努力，期待明年会有回报。明年他又画了一个新饼。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "实际年终奖远低于预期", 5),
                            ImpactResult(ImpactDimension.MENTAL, "被欺骗的愤怒、失望", 5),
                            ImpactResult(ImpactDimension.TIME, "继续无休止加班", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "领导继续用你，但不会重用你", 3)
                        ),
                        quote = "画饼是领导的基本技能，吃饼是员工的悲剧。",
                        keyInsight = "底层吃饼的代价：付出最多，得到最少。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "领导觉得你很可靠",
                        longTermResult = "年终奖比预期少30%，但你已经有心理准备。你开始考虑年后跳槽。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "年终奖低于预期，但能承受", 3),
                            ImpactResult(ImpactDimension.MENTAL, "有失望但有心理准备", 2),
                            ImpactResult(ImpactDimension.TIME, "开始准备简历", 3),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "骑驴找马", 2)
                        ),
                        quote = "中层吃饼的智慧：吃着碗里的，看着锅里的。",
                        keyInsight = "中层吃饼的策略：相信他的话，但不做任何牺牲。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "领导觉得你很有潜力",
                        longTermResult = "年终奖虽然不多，但你早就不指望这个了。你有自己的副业、投资、或者核心竞争力。领导的话只是参考，你有自己的判断。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "年终奖不影响整体收入", 1),
                            ImpactResult(ImpactDimension.MENTAL, "完全不受影响", 1),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "保持专业关系", 1),
                            ImpactResult(ImpactDimension.TIME, "正常工作节奏", 1)
                        ),
                        quote = "上层不吃饼的原因：饼太小，不值得吃。",
                        keyInsight = "上层不吃饼的资本：他们有自己的'饼'。"
                    )
                )
            ),
            realisticChoice = ScenarioChoice(
                id = "realistic_boss",
                title = "表面感谢，实际行动（现实法则：只看结果）",
                description = "嘴上说'谢谢领导栽培'，但开始悄悄更新简历。年终奖少就少，我早就有备选方案了。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "领导觉得你态度很好",
                        longTermResult = "你找到了新工作，跳槽成功。涨薪20%。你离开时领导才知道你要走，但已经来不及挽留了。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "涨薪20%，总收入增加", 5),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "离开但保持体面", 2),
                            ImpactResult(ImpactDimension.MENTAL, "自信、有掌控感", 4),
                            ImpactResult(ImpactDimension.TIME, "准备跳槽的时间成本", 3)
                        ),
                        quote = "底层跳槽的价值：这是你改变命运的机会。",
                        keyInsight = "底层跳槽的意义：每一次跳槽都是一次涨薪的机会。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "领导觉得你是个稳重的人",
                        longTermResult = "你有几个offer在手，选择了一个更好的。跳槽或者内部谈判都游刃有余。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "更好的offer等着你", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "保持体面离开", 2),
                            ImpactResult(ImpactDimension.MENTAL, "有选择权的满足感", 4),
                            ImpactResult(ImpactDimension.TIME, "准备时间成本", 2)
                        ),
                        quote = "中层跳槽的艺术：不是我背叛，是双向选择。",
                        keyInsight = "中层跳槽的资本：有实力才有选择权。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "领导觉得你是个有格局的人",
                        longTermResult = "你继续在公司待着，但不是因为饼，是因为你自己想待。你有自己的议价能力。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "维持高收入，可能还有额外机会", 2),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "平等的合作关系", 3),
                            ImpactResult(ImpactDimension.MENTAL, "完全掌控自己的职业", 4),
                            ImpactResult(ImpactDimension.TIME, "正常节奏，无额外压力", 1)
                        ),
                        quote = "上层不跳槽的境界：不是离不开，是选择留下。",
                        keyInsight = "上层留下的条件：不是因为饼，是因为值得。"
                    )
                )
            )
        )
    }
    
    // ============================================
    // 场景7：亲戚聚会被比较
    // ============================================
    private fun createComparisonScenario(): SimulatorScenario {
        return SimulatorScenario(
            id = "family_comparison",
            title = "过年亲戚聚会，有人炫耀孩子",
            description = "过年走亲戚，三叔炫耀他儿子在大公司上班，月薪3万。亲戚们都夸他儿子有出息。然后他们看向你，问：'你现在怎么样啊？'",
            applicableClasses = SocialClass.entries.toList(),
            traditionalChoice = ScenarioChoice(
                id = "traditional_comparison",
                title = "如实回答，被动比较（传统道德：诚实不说谎）",
                description = "我就5000一个月，说实话也没什么。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "亲戚们表面安慰，心里觉得你'不行'",
                        longTermResult = "三婶会拿你当反面教材：'你要好好读书，不然就像XXX一样。'你的父母会感觉很没面子。春节余下的几天，你都会被各种'关心'包围。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为家族的话题和比较对象", 5),
                            ImpactResult(ImpactDimension.MENTAL, "被评判的羞耻感和愤怒", 5),
                            ImpactResult(ImpactDimension.REPUTATION, "家族评价下降", 4),
                            ImpactResult(ImpactDimension.MONEY, "可能还要被借钱", 3)
                        ),
                        quote = "家族的比较，是你永远赢不了的战争。",
                        keyInsight = "底层说实话的代价：你的诚实，成为伤害你父母的工具。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "亲戚们表面客气，内心评判",
                        longTermResult = "你的父母会帮你圆场，但心里会有压力。你自己也要应对各种'建议'和'关心'。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为话题，但程度较轻", 3),
                            ImpactResult(ImpactDimension.MENTAL, "被比较的烦躁感", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "家族评价中等", 2),
                            ImpactResult(ImpactDimension.TIME, "应付各种'建议'", 2)
                        ),
                        quote = "中层说实话的后果：不被嘲笑，但也不被羡慕。",
                        keyInsight = "中层说实话的成本：你要花精力应对各种'关心'。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "亲戚们都夸你有出息",
                        longTermResult = "你的父母很骄傲。你的话语权在家族中增加。但也会有更多人找你'帮忙'。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "家族地位提升", 3),
                            ImpactResult(ImpactDimension.MENTAL, "被认可的满足感，但也有压力", 2),
                            ImpactResult(ImpactDimension.REPUTATION, "家族评价高", 4),
                            ImpactResult(ImpactDimension.MONEY, "可能被要求'回馈家族'", 3)
                        ),
                        quote = "上层说实话的回报：成为家族的骄傲，获得更多资源。",
                        keyInsight = "上层说实话的价值：你的成功可以换取家族资源。"
                    )
                )
            ),
            realisticChoice = ScenarioChoice(
                id = "realistic_comparison",
                title = "模糊回答，反客为主（现实法则：掌握话语权）",
                description = "'还行吧，在XX行业发展。'然后反问三叔：'弟弟那个行业现在很卷吧？我看新闻说都在裁员呢。'转移话题，掌握主动权。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "亲戚们有点尴尬",
                        longTermResult = "话题被转移，你的具体情况没人知道。你和父母都避免了被评判的命运。亲戚可能觉得你'不够坦诚'，但你不在乎。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系基本不变，避免成为话题", 3),
                            ImpactResult(ImpactDimension.MENTAL, "避免了被评判的压力", 4),
                            ImpactResult(ImpactDimension.REPUTATION, "不被拿来比较", 4),
                            ImpactResult(ImpactDimension.TIME, "减少应付追问的时间", 3)
                        ),
                        quote = "模糊是一种保护，不是欺骗。",
                        keyInsight = "底层模糊的价值：保护自己和家人，不被当成话题。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "亲戚们转移话题",
                        longTermResult = "你成功掌握了话语权。你的回答既不撒谎，也不暴露具体信息。亲戚们觉得你'有出息但不张扬'。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "被尊重，有话语权", 4),
                            ImpactResult(ImpactDimension.MENTAL, "有掌控感的满足", 4),
                            ImpactResult(ImpactDimension.REPUTATION, "被视为'有分寸的人'", 3),
                            ImpactResult(ImpactDimension.TIME, "话题快速转移", 2)
                        ),
                        quote = "中层模糊的艺术：掌握话语权，不被动挨打。",
                        keyInsight = "中层模糊的技巧：模糊+反问=掌握主动权。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "亲戚们对你很客气",
                        longTermResult = "他们知道你'不好惹'，不敢随便评价你。你的气场让他们自动收敛。你和家人度过了愉快的春节。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "被尊重，没人敢惹", 4),
                            ImpactResult(ImpactDimension.MENTAL, "完全掌控局面的满足", 5),
                            ImpactResult(ImpactDimension.REPUTATION, "被视为'有实力的人'", 4),
                            ImpactResult(ImpactDimension.TIME, "话题自然转移", 1)
                        ),
                        quote = "上层模糊的境界：不用说话，气场已经说明一切。",
                        keyInsight = "上层模糊的资本：实力是最好的话语权。"
                    )
                )
            )
        )
    }
    
    // ============================================
    // 场景8：朋友借钱不还
    // ============================================
    private fun createDebtScenario(): SimulatorScenario {
        return SimulatorScenario(
            id = "friend_debt",
            title = "半年前借给朋友5000块，他说下周还",
            description = "半年前，你的好朋友小张找你借了5000块，说下个月还。一周拖一个月，一个月拖半年。现在你手头紧，想把钱要回来。",
            applicableClasses = SocialClass.entries.toList(),
            traditionalChoice = ScenarioChoice(
                id = "traditional_debt",
                title = "继续等，或者不好意思要（传统道德：朋友之间不要太计较）",
                description = "催债显得太不近人情了。他可能是真的有困难，我再等等吧。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "朋友觉得你'不急用'",
                        longTermResult = "小张已经习惯了'不还钱也没事'。你再等半年，这5000块大概率永远不会主动还。你的生活本来就紧巴巴的，这5000块可能就是你一个月的生活费。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "5000元可能永远要不回", 5),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "朋友关系变味，你成了'冤大头'", 4),
                            ImpactResult(ImpactDimension.MENTAL, "委屈、后悔、自责", 4),
                            ImpactResult(ImpactDimension.TIME, "半年的等待成本", 3)
                        ),
                        quote = "不好意思催债，债就永远不还。",
                        keyInsight = "底层等债的代价：你的善良，被当成软弱。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "朋友说'马上马上'",
                        longTermResult = "你再催几次，可能要回来3000块。剩下的2000块，就当'交朋友费'了。你开始怀疑这段友谊。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "可能损失2000-3000", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "友谊受损，开始怀疑这段关系", 4),
                            ImpactResult(ImpactDimension.MENTAL, "不舒服，但还能承受", 3),
                            ImpactResult(ImpactDimension.TIME, "多次催债的时间成本", 2)
                        ),
                        quote = "中层等债的结果：要么损失本金，要么损失朋友。",
                        keyInsight = "中层等债的问题：拖得越久，损失越大。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "朋友会尽快还",
                        longTermResult = "5000块对他来说是一笔钱，对你来说不算什么。他会尽快还，因为你们的关系对他也有价值。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "本金安全，可能还收到感谢", 1),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系更好，他更尊重你", 3),
                            ImpactResult(ImpactDimension.MENTAL, "无压力", 1),
                            ImpactResult(ImpactDimension.TIME, "几乎不需要催", 1)
                        ),
                        quote = "上层借钱的结果：对方更珍惜这段关系。",
                        keyInsight = "上层借钱的优势：钱不重要，关系才重要。"
                    )
                )
            ),
            realisticChoice = ScenarioChoice(
                id = "realistic_debt",
                title = "直接要，或者用借口要（现实法则：保护自己的利益）",
                description = "'张哥，最近手头紧，那5000块能不能这周转我？'或者'我最近要交房租，那5000块你看方便吗？'",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "朋友有点不高兴",
                        longTermResult = "如果他有钱，他会还，但可能会觉得你'太计较了'。如果他没钱，你可能会损失这5000块。但至少你知道了真相。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "可能全部要回，可能损失部分", 3),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "关系可能受影响，但你知道谁是真朋友", 3),
                            ImpactResult(ImpactDimension.MENTAL, "早知道早解脱", 3),
                            ImpactResult(ImpactDimension.TIME, "一次催债的时间成本", 2)
                        ),
                        quote = "催债是友谊的试金石。",
                        keyInsight = "底层催债的价值：早知道真相，早做决定。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "朋友说'好的我想想办法'",
                        longTermResult = "他会分期还，或者找别人借钱先还你。你们的关系会有点尴尬，但他会尊重你的做法。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "大概率能要回大部分", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "有点尴尬，但关系还能维持", 2),
                            ImpactResult(ImpactDimension.MENTAL, "有压力但有进展", 2),
                            ImpactResult(ImpactDimension.TIME, "需要跟进几次", 2)
                        ),
                        quote = "中层催债的艺术：给台阶，但要结果。",
                        keyInsight = "中层催债的技巧：态度要好，目标要明确。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "朋友立刻转账",
                        longTermResult = "5000块到账。你们的关系基于互相尊重和信任。他更尊重你了，因为你让他知道你不是好欺负的。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "本金安全", 5),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "被尊重，关系更健康", 4),
                            ImpactResult(ImpactDimension.MENTAL, "无压力", 1),
                            ImpactResult(ImpactDimension.TIME, "几乎不需要催", 1)
                        ),
                        quote = "上层催债的好处：对方更珍惜这段关系。",
                        keyInsight = "上层催债的价值：有实力催债，也是对对方的尊重。"
                    )
                )
            )
        )
    }
    
    // ============================================
    // 场景9：同事甩锅
    // ============================================
    private fun createBlameScenario(): SimulatorScenario {
        return SimulatorScenario(
            id = "colleague_blame",
            title = "同事把项目搞砸了，在会上说是你的问题",
            description = "你和同事小李一起做一个项目。小李负责的部分出了问题，在项目复盘会上，他说'这部分是小王负责的，我只是协助'。老板看向你。",
            applicableClasses = SocialClass.entries.toList(),
            traditionalChoice = ScenarioChoice(
                id = "traditional_blame",
                title = "沉默接受，顾全大局（传统道德：以和为贵）",
                description = "吵架显得太不成熟了。我忍一忍，顾全大局。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "老板觉得你是那个出问题的人",
                        longTermResult = "你背了黑锅，但没有证据反驳。老板对你的印象变差，年终奖受影响。小李下次还会甩锅给你，因为他知道你好欺负。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "年终奖受影响，可能被扣绩效", 4),
                            ImpactResult(ImpactDimension.REPUTATION, "在老板眼里留下坏印象", 5),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "小李会继续欺负你", 4),
                            ImpactResult(ImpactDimension.MENTAL, "委屈、愤怒、憋屈", 5)
                        ),
                        quote = "沉默不是美德，是懦弱。",
                        keyInsight = "底层沉默的代价：背黑锅、被欺负、被当成软柿子。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "老板有点疑惑",
                        longTermResult = "你沉默，小李得逞。但如果你有证据，事情可能会有转机。你开始后悔没有留证据。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "可能影响年终奖", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "被误解，但没有证据", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "和小李的关系变复杂", 3),
                            ImpactResult(ImpactDimension.MENTAL, "委屈，但能接受", 2)
                        ),
                        quote = "中层沉默的代价：机会主义者的温床。",
                        keyInsight = "中层沉默的问题：没有证据，就是哑巴吃黄连。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "老板觉得你可能是对的",
                        longTermResult = "你有证据（邮件、聊天记录），可以反驳。小李知道你不好欺负，以后不敢再甩锅给你。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "无损失", 1),
                            ImpactResult(ImpactDimension.REPUTATION, "在老板眼里是有能力的人", 3),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "小李对你有敬畏", 3),
                            ImpactResult(ImpactDimension.MENTAL, "有理有据的自信", 2)
                        ),
                        quote = "上层沉默的资本：有证据，有底气。",
                        keyInsight = "上层沉默的价值：不是不反击，是等时机。"
                    )
                )
            ),
            realisticChoice = ScenarioChoice(
                id = "realistic_blame",
                title = "用证据反驳（现实法则：保护自己的利益）",
                description = "你可以拿出证据：'这部分是小李负责的，我3号的邮件写得很清楚，他回复说没问题。'",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "老板开始怀疑小李",
                        longTermResult = "如果证据充分，你洗清嫌疑。如果证据不充分，你可能被当成'甩锅的人'。你和小李的关系彻底破裂。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "如果成功，无损失；如果失败，损失更大", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "风险较大，可能两面不讨好", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "和小李彻底决裂", 4),
                            ImpactResult(ImpactDimension.MENTAL, "紧张、压力大", 3)
                        ),
                        quote = "底层反驳的风险：如果没有证据，你可能更惨。",
                        keyInsight = "底层反驳的前提：必须有证据，否则不要轻易反驳。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "老板重新审视情况",
                        longTermResult = "证据说话，小李露馅。你洗清嫌疑，小李以后不敢再甩锅给你。老板觉得你是个有原则的人。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "无损失", 4),
                            ImpactResult(ImpactDimension.REPUTATION, "被视为有原则的人", 4),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "和小李关系破裂，但赢得尊重", 3),
                            ImpactResult(ImpactDimension.MENTAL, "有压力的解脱感", 3)
                        ),
                        quote = "中层反驳的价值：用证据说话，有理有据。",
                        keyInsight = "中层反驳的技巧：不是吵架，是用事实说话。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "老板立刻站在你这边",
                        longTermResult = "小李被批评，你在老板眼里加分。你有底气、有证据、有实力。小李以后对你客客气气的。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.MONEY, "无损失，可能加分", 4),
                            ImpactResult(ImpactDimension.REPUTATION, "被视为有能力有原则的人", 5),
                            ImpactResult(ImpactDimension.RELATIONSHIP, "小李对你有敬畏", 4),
                            ImpactResult(ImpactDimension.MENTAL, "有理有据的自信", 3)
                        ),
                        quote = "上层反驳的境界：不怒自威，不用发火。",
                        keyInsight = "上层反驳的资本：实力是最有力的武器。"
                    )
                )
            )
        )
    }
    
    // ============================================
    // 场景10：过年回家
    // ============================================
    private fun createHomecomingScenario(): SimulatorScenario {
        return SimulatorScenario(
            id = "spring_festival",
            title = "过年回家，亲戚问你挣多少钱",
            description = "大年初二，亲戚们聚在一起。表姐炫耀她儿子买了新房。三叔问他儿子（月薪8000）什么时候买房。轮到你，有人问：'你现在怎么样啊？'",
            applicableClasses = SocialClass.entries.toList(),
            traditionalChoice = ScenarioChoice(
                id = "traditional_homecoming",
                title = "如实回答（传统道德：对家人要坦诚）",
                description = "亲戚问就答呗，挣多少说多少。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "亲戚们表面安慰",
                        longTermResult = "你的回答成为亲戚们的谈资。你妈会感觉很没面子。'读书读出来也没什么用''看看人家XXX'。整个春节，你和家人都要被各种'关心'包围。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为家族的话题，被各种比较", 5),
                            ImpactResult(ImpactDimension.REPUTATION, "家族评价下降", 5),
                            ImpactResult(ImpactDimension.MENTAL, "被评判的羞耻和愤怒", 5),
                            ImpactResult(ImpactDimension.MONEY, "可能被借钱、被'资助'", 3)
                        ),
                        quote = "家族聚会，是你永远赢不了的攀比。",
                        keyInsight = "底层如实回答的代价：你的坦诚，成为伤害你父母的工具。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "亲戚们觉得你'还行'",
                        longTermResult = "不是最好的，也不是最差的。你被当成'普通人'。你妈会感觉'还好'，但心里可能有点不甘。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "成为普通话题", 2),
                            ImpactResult(ImpactDimension.REPUTATION, "中等评价", 2),
                            ImpactResult(ImpactDimension.MENTAL, "有点压力但能接受", 2),
                            ImpactResult(ImpactDimension.TIME, "应付一些'建议'", 2)
                        ),
                        quote = "中层如实回答的结果：不高不低，不被羡慕也不被嘲笑。",
                        keyInsight = "中层如实回答的成本：被当成参照物。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "亲戚们都很羡慕",
                        longTermResult = "你爸妈很骄傲，在亲戚面前说话都有底气。但也会有各种'帮忙''介绍'找上来。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "家族地位提升，但负担也增加", 3),
                            ImpactResult(ImpactDimension.REPUTATION, "家族评价高", 4),
                            ImpactResult(ImpactDimension.MENTAL, "被认可的满足感", 3),
                            ImpactResult(ImpactDimension.MONEY, "可能被要求'回馈家族'", 3)
                        ),
                        quote = "上层如实回答的回报：成为家族的骄傲。",
                        keyInsight = "上层如实回答的价值：你的成功可以换取家族地位。"
                    )
                )
            ),
            realisticChoice = ScenarioChoice(
                id = "realistic_homecoming",
                title = "模糊回答，转移话题（现实法则：掌握主动权）",
                description = "'还行吧，在XX行业发展。'然后反问：'表弟那个新房在哪个区啊？现在房价怎么样？'转移话题，掌握主动权。",
                consequences = mapOf(
                    SocialClass.BOTTOM to ChoiceConsequence(
                        immediateResult = "亲戚们转移话题",
                        longTermResult = "没人再追问你具体数字。你的具体情况成为秘密。你和父母都避免了被评判的命运。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "避免成为话题", 4),
                            ImpactResult(ImpactDimension.REPUTATION, "不被拿来比较", 4),
                            ImpactResult(ImpactDimension.MENTAL, "避免了被评判的压力", 4),
                            ImpactResult(ImpactDimension.TIME, "话题快速转移", 3)
                        ),
                        quote = "模糊是一种保护，不是欺骗。",
                        keyInsight = "底层模糊的价值：保护自己和家人。"
                    ),
                    SocialClass.MIDDLE to ChoiceConsequence(
                        immediateResult = "亲戚们聊起房价",
                        longTermResult = "话题转移，你成功掌握了主动权。亲戚们觉得你'有出息但不张扬'。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "被尊重，有话语权", 4),
                            ImpactResult(ImpactDimension.REPUTATION, "被视为'有分寸的人'", 3),
                            ImpactResult(ImpactDimension.MENTAL, "有掌控感的满足", 4),
                            ImpactResult(ImpactDimension.TIME, "话题自然转移", 2)
                        ),
                        quote = "中层模糊的艺术：掌握话语权，不被动挨打。",
                        keyInsight = "中层模糊的技巧：模糊+反问=掌握主动权。"
                    ),
                    SocialClass.TOP to ChoiceConsequence(
                        immediateResult = "亲戚们都很客气",
                        longTermResult = "他们知道你'不好惹'，不敢随便评价你。你的气场让他们自动收敛。",
                        impacts = listOf(
                            ImpactResult(ImpactDimension.RELATIONSHIP, "被尊重，没人敢惹", 4),
                            ImpactResult(ImpactDimension.REPUTATION, "被视为'有实力的人'", 4),
                            ImpactResult(ImpactDimension.MENTAL, "完全掌控局面的满足", 5),
                            ImpactResult(ImpactDimension.TIME, "话题自然转移", 1)
                        ),
                        quote = "上层模糊的境界：不用说话，气场已经说明一切。",
                        keyInsight = "上层模糊的资本：实力是最好的话语权。"
                    )
                )
            )
        )
    }
}
