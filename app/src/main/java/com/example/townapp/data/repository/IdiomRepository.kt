package com.example.townapp.data.repository

import com.example.townapp.data.model.AcademicReference
import com.example.townapp.data.model.IdiomData
import com.example.townapp.data.model.IdiomRarity
import com.example.townapp.data.model.IdiomReflection
import com.example.townapp.data.model.TriggerCondition

/**
 * 成语卡片仓库
 * 集中管理所有成语卡片数据
 */
object IdiomRepository {
    // 所有成语卡片列表
    private val allIdioms = listOf(
        // 1. 多多益善
        IdiomData(
            idiomId = 1,
            name = "多多益善",
            traditionalMeaning = "越多越好，韩信将兵多多益善。",
            awakeningMeaning = "这不是贪心，是刻在你基因里的原始囤积本能。在非洲草原上，多囤一颗果实就能活过冬天；但在物质过剩的今天，囤积不会带来安全感，只会带来决策内耗和财务负担。",
            dataTemplate = "你有 {item_count} 件 {category}，超过了 {threshold} 件的合理阈值，每天挑选要多花 {extra_time} 分钟，一年就是 {yearly_hours} 小时。",
            actionSuggestion = "清理3件半年没穿过的，试试看——你根本不会想念它们。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "进化错配假说",
                    researcher = "George C. Williams",
                    year = 1957,
                    summary = "人类心理机制形成于狩猎采集环境，与现代物质社会存在系统性不匹配。",
                    fullText = "人类的心理机制形成于更新世（距今258万-1.17万年）的狩猎采集环境，与现代物质社会存在系统性不匹配。囤积行为是对资源稀缺环境的适应，在物质过剩时代反而成为认知负担。"
                ),
                AcademicReference(
                    theoryName = "认知负荷理论",
                    researcher = "John Sweller",
                    year = 1988,
                    summary = "人的工作记忆容量有限，选项过多会导致决策疲劳和满意度下降。",
                    fullText = "人的工作记忆容量有限（通常为4±1个信息块），选项过多会消耗认知资源，导致决策疲劳和满意度下降。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "clothing",
                    conditionType = "count",
                    threshold = 30.0,
                    operator = ">"
                ),
                TriggerCondition(
                    module = "food",
                    conditionType = "storage_days",
                    threshold = 7.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 2. 敝帚自珍
        IdiomData(
            idiomId = 2,
            name = "敝帚自珍",
            traditionalMeaning = "自己的东西即使不好也珍惜，比喻珍惜自己的所有。",
            awakeningMeaning = "你珍惜的不是那把破扫帚，是你当初买它花的钱和时间。这叫「禀赋效应」——拥有一件东西会让你高估它的价值，哪怕它对你已经没用了。",
            dataTemplate = "这件衣服你已经闲置了 {idle_months} 个月，穿过 {use_count} 次，单次使用成本高达 {cost_per_use} 元/次。再放下去，它就只剩下「占地」和「添堵」两个功能了。",
            actionSuggestion = "拍张照留念，然后捐掉或卖掉——你需要的是「曾经拥有过」的感觉，不是物理上的占有。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "禀赋效应",
                    researcher = "Daniel Kahneman et al.",
                    year = 1990,
                    summary = "人们一旦拥有某件物品，对其价值的评价就会显著增加。",
                    fullText = "行为经济学经典发现：人们一旦拥有某件物品，对该物品价值的评价就会比拥有之前显著增加。在经典实验中，被试拿到杯子后愿意卖出的价格，是没拿到杯子的人愿意买入价格的2-3倍。"
                ),
                AcademicReference(
                    theoryName = "沉没成本谬误",
                    researcher = "Hal Arkes & Catherine Blumer",
                    year = 1985,
                    summary = "人们在做决策时，会受到已经投入且无法收回的成本的影响。",
                    fullText = "人们在做决策时，会受到已经投入且无法收回的成本（时间、金钱、精力）的影响，即使继续下去明显不划算。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "clothing",
                    conditionType = "idle_days",
                    threshold = 90.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 3. 衣锦还乡
        IdiomData(
            idiomId = 3,
            name = "衣锦还乡",
            traditionalMeaning = "富贵后回到故乡，比喻功成名就。",
            awakeningMeaning = "你买的不是衣服，是「我是个什么样的人」的身份标签。90%的高价衣物，真正的使用场景只有一个——让别人看到你穿了它。",
            dataTemplate = "这件衣服花了你 {salary_price} 的薪俸，但你穿去的场合80%是不需要刻意打扮的。如果没有人看到你穿它，你还愿意花这个价钱吗？",
            actionSuggestion = "下次买贵衣服前，先问自己：「如果今天宅在家不出门，我还想买这件吗？」",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "符号消费理论",
                    researcher = "Jean Baudrillard",
                    year = 1970,
                    summary = "消费社会中，商品不仅具有使用价值，更具有代表身份地位的符号价值。",
                    fullText = "消费社会中，商品不仅具有使用价值，更具有符号价值。人们购买商品，很多时候是在购买它所代表的身份、地位、品味等符号意义，以此构建和表达自我认同。你买的不是衣服，是「我是个有品味的人」的标签，是给别人看的。"
                ),
                AcademicReference(
                    theoryName = "炫耀性消费",
                    researcher = "Thorstein Veblen",
                    year = 1899,
                    summary = "有闲阶级通过购买昂贵且无用的商品来展示财富和社会地位。",
                    fullText = "这是社会学领域的开创性研究。有闲阶级通过购买昂贵但无实际用途的商品来展示财富，这种消费不是为了使用，而是为了「让别人看到自己有能力消费」。100年前这是有钱人的专利，现在，我们普通人也在用分期付款买奢侈品——只不过，我们是在向谁展示？那些不认识我们的路人？"
                ),
                AcademicReference(
                    theoryName = "镜像神经元",
                    researcher = "Giacomo Rizzolatti et al.",
                    year = 1996,
                    summary = "人类大脑中有专门负责模仿和共情的神经元，这让我们对他人的目光极度敏感。",
                    fullText = "镜像神经元让我们能够模仿他人的行为，也让我们对「被别人看到」这件事特别敏感。你在意别人怎么看你穿衣服，是因为你的大脑里有一整套「被观察」的神经回路。原始社会里，被部落排斥意味着死亡，所以「被人看到」这个信号会激活你的奖赏系统——被羡慕的感觉，和被老虎追的感觉一样强烈。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "clothing",
                    conditionType = "price_ratio",
                    threshold = 5.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 4. 买椟还珠
        IdiomData(
            idiomId = 4,
            name = "买椟还珠",
            traditionalMeaning = "买了装珍珠的盒子却把珍珠还给卖主，比喻取舍不当、舍本逐末。",
            awakeningMeaning = "你为了包装、赠品、满减凑单而买单，却忘了真正需要的是什么。这不是精明，是被营销手段牵着鼻子走的「椟珠颠倒」。",
            dataTemplate = "你为了凑 {discount_amount} 元满减，多买了 {extra_items} 件不需要的东西，实际多花了 {extra_cost} 元——为了省小钱，花了大钱。",
            actionSuggestion = "下次看到满减，先问自己：「如果没有优惠，我还会买这些吗？」",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "锚定效应",
                    researcher = "Amos Tversky & Daniel Kahneman",
                    year = 1974,
                    summary = "人们在做决策时，会受到初始信息（锚点）的强烈影响，即使锚点与决策无关。",
                    fullText = "这是行为经济学最经典的发现之一。在一个实验中，让两组人分别猜测联合国中非洲国家占比，一组先看到1-10的随机数转盘，另一组没有。结果看到10%的这组，猜测平均值是25%，看到65%的这组，猜测平均值是45%——锚点就这么操纵了你的判断。满减的「原价」，就是那个锚点。"
                ),
                AcademicReference(
                    theoryName = "诱饵效应",
                    researcher = "Joel Huber et al.",
                    year = 1982,
                    summary = "引入一个明显较差的「诱饵」选项，会让目标选项显得更具吸引力。",
                    fullText = "这是商家的心理操控术。餐厅菜单上放一个超贵的菜，你本来不会点，但它让你的中等价位菜显得「合理」了。电商的「凑单品」，就是那个诱饵。你以为你在占便宜，其实你在被设计好的框架里做选择。"
                ),
                AcademicReference(
                    theoryName = "多巴胺经济学",
                    researcher = "Kent Berridge",
                    year = 2003,
                    summary = "大脑的奖赏系统让我们对「获得感」上瘾，而不是对「拥有感」满足。",
                    fullText = "多巴胺不是快乐分子，是「预期」分子。期待打折的快感，比真正买到东西还强烈。你享受的是「抢到优惠」的感觉，而不是那件东西本身。所以你会买很多用不上的「划算货」，因为你要的是多巴胺，不是价值。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "impulse_ratio",
                    threshold = 0.3,
                    operator = ">"
                ),
                TriggerCondition(
                    module = "clothing",
                    conditionType = "gift_purchase",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 5. 因小失大
        IdiomData(
            idiomId = 5,
            name = "因小失大",
            traditionalMeaning = "为了小的利益而造成大的损失。",
            awakeningMeaning = "你以为省了几块钱买了快过期的食物，却可能付出健康的代价；你以为熬夜敷面膜就能抵消伤害，却不知道健康是不能用护肤品来买单的。",
            dataTemplate = "你买的 {food_name} 还有 {expiry_days} 天过期，虽然便宜了 {saved_money} 元，但如果吃坏肚子去医院，可能要花 {potential_cost} 元——捡了芝麻，丢了西瓜。",
            actionSuggestion = "健康是最高价值的资产，别为了省小钱而冒大风险。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "健康经济学",
                    researcher = "Michael Grossman",
                    year = 1972,
                    summary = "健康是一种耐用资本，投资健康能带来长期回报，损耗健康则会持续消耗未来资本。",
                    fullText = "这是健康经济学的奠基性理论。健康不只是「没有生病」，而是一种可以产生「健康时间」的资本。你今天省下的10块钱买快过期食物的成本，可能是明天100块的医疗费，更可能是未来10年的慢性病治疗。这不是危言耸听，这是经济学模型告诉我们的必然。"
                ),
                AcademicReference(
                    theoryName = "双曲贴现",
                    researcher = "Laibson",
                    year = 1997,
                    summary = "人们对近期的奖励估价过高，对远期的惩罚估价过低。",
                    fullText = "双曲贴现是人类大脑的bug：今天的一块钱，在明天只值0.9块；今天的一痛苦，在未来只值0.5痛苦。所以你会为了省10块钱买快过期食物，却忽视10年后可能的心脏病风险——未来太远，大脑感受不到。这就是为什么我们总是「短视」，因为大脑就是为短视而生的。"
                ),
                AcademicReference(
                    theoryName = "皮肤屏障理论",
                    researcher = "Elias & Friend",
                    year = 1985,
                    summary = "皮肤的最外层角质层是皮肤屏障，过度清洁和熬夜会破坏这个屏障。",
                    fullText = "这是皮肤科学的基础理论。皮肤最外层有天然的屏障系统，它需要充足的睡眠和正确的保湿来维护。熬夜会破坏这个屏障，导致胶原蛋白流失——而这个过程是不可逆的。你涂的2000块眼霜，可能只能弥补熬夜1小时造成的伤害的1%。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "expiry_days",
                    threshold = 3.0,
                    operator = "<"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 6. 掩耳盗铃
        IdiomData(
            idiomId = 6,
            name = "掩耳盗铃",
            traditionalMeaning = "偷铃铛时捂住自己的耳朵，以为别人也听不到，比喻自欺欺人。",
            awakeningMeaning = "熬夜时敷最贵的眼霜，喝可乐时选无糖版，吃炸鸡时去皮——这些不是养生，是自我安慰。你以为捂住了耳朵，真相就不存在了吗？",
            dataTemplate = "你每周熬夜 {late_nights} 次，但只用了 {skincare_cost} 元的护肤品来「补救」。熬夜带来的健康风险，是护肤品无法抵消的。",
            actionSuggestion = "与其事后补救，不如事前预防。早睡一小时，比用任何护肤品都有效。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "认知失调理论",
                    researcher = "Leon Festinger",
                    year = 1957,
                    summary = "当行为与认知不一致时，人们会通过改变认知来缓解心理不适，而不是改变行为。",
                    fullText = "这是社会心理学最著名的理论之一。当你「知道」熬夜不好，但你又「做了」熬夜这件事，你的内心会产生失调的痛苦。为了缓解这种痛苦，你会找借口：「我用了很贵的眼霜」「我今天睡了7小时」「偶尔一次没关系」——这些借口不是在安慰皮肤，是在安慰你自己的认知。"
                ),
                AcademicReference(
                    theoryName = "自我欺骗的神经机制",
                    researcher = "Dogil & Base",
                    year = 2002,
                    summary = "前额叶皮层负责自我欺骗，它能帮助我们维持积极的自我形象。",
                    fullText = "神经影像学研究发现，自我欺骗涉及前额叶皮层的活动。这个区域负责抽象思维和自我评估。有趣的是，自我欺骗时，大脑的焦虑中心（杏仁核）是平静的——所以自欺欺人真的能让你感觉好一点，只是你的皮肤不会骗人。"
                ),
                AcademicReference(
                    theoryName = "道德许可效应",
                    researcher = "Anna Zeckhauser",
                    year = 1996,
                    summary = "做完一件「好」事之后，人们更容易做一些「坏」事来奖励自己。",
                    fullText = "这是心理学上一个很可怕的效应：当你「做了一件好事」，比如敷了面膜，你就会觉得自己已经很「健康」了，然后更容易去做坏事，比如熬夜。这是大脑在给自己发道德许可证——「我都养生了，所以可以放纵一下」。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "junk_food_ratio",
                    threshold = 0.3,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 7. 刻舟求剑
        IdiomData(
            idiomId = 7,
            name = "刻舟求剑",
            traditionalMeaning = "剑掉水里后在船舷刻记号，等船靠岸再按记号找剑，比喻拘泥固执、不懂变通。",
            awakeningMeaning = "你用十年前的消费观念过今天的生活，用父母那辈的经验做现在的决策。世界在变，你的认知却还停留在原地——这样怎么能找到「剑」呢？",
            dataTemplate = "你还在用 {old_method} 的方式管理消费，但现在的物价、收入和生活方式都已经变了。刻舟求剑式的思维，只会让你错过更好的选择。",
            actionSuggestion = "定期审视自己的消费习惯，问自己：「如果我今天才开始学理财，我会怎么做？」",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "路径依赖",
                    researcher = "W. Brian Arthur",
                    year = 1989,
                    summary = "一旦做出某种选择，惯性的力量会让这一选择不断自我强化。",
                    fullText = "这是经济学中的「锁死效应」。一旦你选择了某个消费模式（比如「买便宜货凑合用」），你就会在这个模式里越陷越深，因为换模式需要付出额外的认知成本。你父母那辈的省钱经验，在物资匮乏时代是对的，但在今天，它们可能正在锁死你的财富自由度。"
                ),
                AcademicReference(
                    theoryName = "认知固化与神经可塑性",
                    researcher = "Merzenich & Korman",
                    year = 1995,
                    summary = "大脑会形成固定的神经通路，改变它需要刻意练习。",
                    fullText = "神经科学家发现，大脑会「用进废退」。你重复一种思维模式久了，它就会变成默认路径。这就是为什么改变消费习惯这么难——不是因为你「意志力不够」，是因为你的神经回路已经固化了。但好消息是，神经可塑性意味着任何时候都可以重建，只是需要时间和刻意练习。"
                ),
                AcademicReference(
                    theoryName = "存在主义心理学",
                    researcher = "Irvin Yalom",
                    year = 1980,
                    summary = "人们倾向于活在「自我定义」的安全感中，回避不确定性。",
                    fullText = "存在主义心理学家发现，人们宁愿守着旧的、不再适用的信念，也不愿面对「也许我一直都错了」的不安。承认「我父母教我的消费观可能是错的」，比继续用它更让人恐惧——因为那意味着你要重新构建整个自我。这就是为什么很多人一辈子都在用同一套过时的认知框架。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "cognitive",
                    conditionType = "learning_stagnation",
                    threshold = 180.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 8. 守株待兔
        IdiomData(
            idiomId = 8,
            name = "守株待兔",
            traditionalMeaning = "农夫捡到一只撞死在树桩上的兔子后，便放下农活守株等待，比喻死守狭隘经验、不知变通。",
            awakeningMeaning = "你靠运气赚了一次钱，就以为找到了致富密码，于是all in，结果亏得精光。偶然的成功比失败更可怕，因为它会让你误以为自己找到了规律。",
            dataTemplate = "你在 {platform} 上靠 {luck_factor} 赚了 {money_earned} 元，就把它当成了可持续的赚钱方式。但这只是随机事件，不是可复制的模式。",
            actionSuggestion = "把偶然的成功当成运气，把失败当成学习的机会——真正的智慧是知道两者的区别。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "幸存者偏差",
                    researcher = "Abraham Wald",
                    year = 1943,
                    summary = "只关注成功者，忽略失败者，导致对概率的系统性误判。",
                    fullText = "这是统计学史上最聪明的故事之一。二战时，统计学家发现飞回来的飞机翅膀弹孔最多，但亚伯拉罕·沃尔德说：「不对，要加固的是弹孔最少的地方，因为那些飞机都没回来。」成功学就是那个弹孔最多的翅膀——那些失败的人已经沉默了，你看到的只是幸存者。"
                ),
                AcademicReference(
                    theoryName = "控制错觉与神经机制",
                    researcher = "Ellen Langer",
                    year = 1975,
                    summary = "人们倾向于高估自己对随机事件的控制能力，甚至在完全随机的情境中也是如此。",
                    fullText = "在心理学实验中，参与者被要求在轮盘赌上选择数字，然后自己转动轮盘（实际上是被操控的）。当他们「自己的」数字赢时，大脑的奖赏中心比「别人转」时更活跃——即使两种情况下数字的选择都是随机的。我们需要相信自己可以控制命运，即使在最随机的情境中。"
                ),
                AcademicReference(
                    theoryName = "均值回归",
                    researcher = "Francis Galton",
                    year = 1886,
                    summary = "极端表现后，下一次大概率会向平均值回归。",
                    fullText = "这是统计学的基本规律。连续好运之后，下一次大概率会变差；连续厄运之后，下一次大概率会变好。但这不意味着「运气守恒」，这只是随机事件的自然规律。那些在牛市中赚了大钱的人，如果没有意识到均值回归，很可能会在下一个周期里亏回去。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "cognitive",
                    conditionType = "gambling_bias",
                    threshold = 2.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 9. 揠苗助长
        IdiomData(
            idiomId = 9,
            name = "揠苗助长",
            traditionalMeaning = "把禾苗往上拔来帮助它生长，结果禾苗都枯死了，比喻急于求成、违背规律。",
            awakeningMeaning = "你给孩子报十几个补习班，恨不得他一岁就能背唐诗三百首；你追求「七天速成」「三十天逆袭」，却忘了成长需要时间。拔苗助长，只会适得其反。",
            dataTemplate = "你给自己设定了 {unrealistic_goal} 的目标，却只给了 {short_time} 的时间。急于求成的结果，往往是半途而废或质量低下。",
            actionSuggestion = "成功没有捷径，成长需要耐心。把大目标拆解成小步骤，每天进步一点点。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "延迟满足与前额叶皮层",
                    researcher = "Walter Mischel",
                    year = 1972,
                    summary = "延迟满足能力与前额叶皮层的成熟度密切相关，这是可以训练的核心能力。",
                    fullText = "棉花糖实验的后续研究追踪了这些孩子几十年，发现小时候能延迟满足的人，成年后更成功、更有自控力。更重要的是，研究发现这种能力不是固定的——它可以通过训练提升。那些「即时满足」的训练（刷短视频、快餐式学习）正在削弱我们延迟满足的能力。"
                ),
                AcademicReference(
                    theoryName = "刻意练习与髓鞘化",
                    researcher = "Anders Ericsson",
                    year = 1993,
                    summary = "技能的提升需要「刻意练习」，不是时间的简单累加。",
                    fullText = "这是对「1万小时定律」的纠正。不是时间本身让你变强，是「刻意练习」——专注、反馈、修正、再练习。刷10000小时短视频不会让你成为专家，只会让你成为「短视频专家」。真正的成长需要走出舒适区，而走出舒适区是反本能的。"
                ),
                AcademicReference(
                    theoryName = "复杂性理论与涌现",
                    researcher = "Stuart Kauffman",
                    year = 1993,
                    summary = "复杂系统的发展有其内在节奏，无法通过加速来缩短。",
                    fullText = "这是复杂性科学的核心洞察。庄稼的生长、孩子的成长、财富的积累，都是复杂系统。复杂系统有自己的节奏，你不能跳过中间过程直接到达结果。你不能「催熟」一个人格，也不能「速成」一种能力——那些声称可以速成的方法，要么是骗子，要么是在透支未来换现在。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "cognitive",
                    conditionType = "impatience_score",
                    threshold = 0.7,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 10. 井底之蛙
        IdiomData(
            idiomId = 10,
            name = "井底之蛙",
            traditionalMeaning = "井底的青蛙只能看到井口那么大的天，比喻见识短浅。",
            awakeningMeaning = "算法给你推荐的内容都是你喜欢看的，你以为这就是整个世界。信息茧房让每个人都活在自己的井口之下，还觉得自己看得挺远。",
            dataTemplate = "你每天花 {screen_time} 小时刷手机，但看到的内容90%都是你已经认同的观点。你以为自己在看世界，其实只是在看算法想让你看的。",
            actionSuggestion = "每周主动看一篇与你观点相反的文章，试着理解对方的视角——真正的开放心态，是愿意挑战自己的认知。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "信息茧房",
                    researcher = "Cass Sunstein",
                    year = 2006,
                    summary = "算法推荐会过滤掉不同意见，导致用户只接触到自己认同的观点，极化社会。",
                    fullText = "这是对互联网时代最深刻的批判之一。当算法只给你推送你喜欢的内容，你就失去了接触「异见」的机会。没有异见的挑战，你的认知就会固化、极化，最终变成「回音壁」。你以为你在「了解世界」，实际上你在「确认自己」。"
                ),
                AcademicReference(
                    theoryName = "邓宁-克鲁格效应",
                    researcher = "David Dunning & Justin Kruger",
                    year = 1999,
                    summary = "能力不足的人往往高估自己的能力，而真正有能力的人反而低估自己。",
                    fullText = "这是心理学史上最反直觉的发现之一。你以为你「懂」的东西，往往只懂皮毛；你不懂的东西，你甚至不知道自己不懂。更可怕的是，那些在信息茧房里泡久了的人，会产生「虚假优越感」——他们觉得自己比那些「没看过这些信息」的人懂得多。"
                ),
                AcademicReference(
                    theoryName = "苏格拉底悖论与认知谦逊",
                    researcher = "Socrates",
                    year = -400,
                    summary = "「我知道我一无所知」——真正的智慧始于对自己无知的认识。",
                    fullText = "2500年前，苏格拉底就说：「我唯一知道的，就是我一无所知。」这是认知的最高境界。在信息爆炸的时代，「知道自己不知道」比「以为自己知道」更重要。那些真正厉害的人，都在刻意寻找自己认知的盲区——因为他们知道，自己的盲区，才是进步空间最大的地方。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "cognitive",
                    conditionType = "echo_chamber",
                    threshold = 0.8,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.RARE
        ),

        // 11. 山珍海味
        IdiomData(
            idiomId = 11,
            name = "山珍海味",
            traditionalMeaning = "山野和海洋里的珍贵食物，指珍贵难得的佳肴。",
            awakeningMeaning = "你以为的「野生特供」「山珍海味」，很可能是重金属超标的重灾区。土鸡蛋营养价值未必更高，但风险绝对更大——没有监管的「野生」，往往是「没人管」的代名词。",
            dataTemplate = "你花高价买的「野生特供」，很可能来自重金属超标的土壤或污染的水域。你以为在吃「健康」，实际在吃风险。",
            actionSuggestion = "选择有正规溯源的食材，不要被「野生」「特供」的营销噱头迷惑——安全，才是最大的健康。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "食品污染与重金属富集",
                    researcher = "FAO/WHO",
                    year = 2003,
                    summary = "野生环境更易积累重金属，食物链顶端生物富集效应最明显。",
                    fullText = "联合国粮农组织和世界卫生组织的研究表明：野生环境往往缺乏监管，更容易受到工业污染。特别是处于食物链顶端的生物（如深海鱼类、野生动物），体内重金属（汞、铅、镉）富集效应最明显，反而比养殖的风险更高。你以为的「原生态」，可能是「原生态污染」。"
                ),
                AcademicReference(
                    theoryName = "风险感知偏差",
                    researcher = "Paul Slovic",
                    year = 1987,
                    summary = "人们倾向于高估「未知风险」，低估「熟悉风险」。",
                    fullText = "风险心理学家发现：我们对「不熟悉的风险」（如转基因）过度恐惧，却对「熟悉的风险」（如土鸡蛋可能的沙门氏菌）放松警惕。「野生」「特供」这些营销词汇，利用了我们对「自然」的情感偏好，但理性告诉我们：真正安全的食品，是有监管、可溯源的食品，不是越「野」越安全。"
                ),
                AcademicReference(
                    theoryName = "环境激素与内分泌干扰",
                    researcher = "Theo Colborn",
                    year = 1996,
                    summary = "环境中的化学物质可能干扰人体内分泌系统，即使微量也有长期影响。",
                    fullText = "《我们被偷走的未来》一书揭示：环境中的重金属、塑料微粒、农药残留等，即使含量很低，长期摄入也可能干扰内分泌系统。野生生物由于生长周期长、食物链位置高，体内积累的环境激素往往更多。你追求的「原汁原味」，可能包含了环境中的「所有成分」。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "wild_food",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 12. 油润鲜香
        IdiomData(
            idiomId = 12,
            name = "油润鲜香",
            traditionalMeaning = "形容食物油润可口、香气浓郁。",
            awakeningMeaning = "中国菜的「油润鲜香」背后，是油脂严重超标的现实。一份宫保鸡丁可能用了30克油，一顿饭吃掉一天的油脂配额——你的味蕾喜欢，但你的血管不喜欢。",
            dataTemplate = "你吃的这道菜，用油量可能超过了20克。按照中国居民膳食指南，每人每天油脂摄入不超过25克——你这一顿就吃掉了一天配额的80%。",
            actionSuggestion = "尝试「少一半油」的做法——用不粘锅、多蒸煮、少红烧，让食物的本味出来，而不是靠油脂的厚重感。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "中国居民膳食指南与油脂摄入",
                    researcher = "中国营养学会",
                    year = 2022,
                    summary = "中国居民平均油脂摄入超标50%以上，是心血管疾病的重要风险因素。",
                    fullText = "《中国居民膳食指南》建议每人每天油脂摄入25-30克，但实际调查显示：中国城市居民平均每天摄入45-55克，超标50%以上。过多的油脂摄入与肥胖、高血压、糖尿病、心血管疾病的发病率密切相关。八大菜系的烹饪方式（红烧、油炸、爆炒），本质上是在「用油脂换口感」。"
                ),
                AcademicReference(
                    theoryName = "味觉适应与阈值变化",
                    researcher = "Linda Bartoshuk",
                    year = 1993,
                    summary = "长期高油高盐饮食会提高味觉阈值，让你越来越重口味。",
                    fullText = "味觉生理学家发现：味蕾是会适应的。你长期吃重油重盐的菜，味蕾会变得「麻木」，需要更多的油盐才能感受到味道。这是一个恶性循环：越吃越咸，越吃越油，最后你的舌头被「驯化」了，吃清淡的菜反而觉得「没味儿」。但你的血管、你的肝脏、你的胰岛，它们都在默默承受这一切。"
                ),
                AcademicReference(
                    theoryName = "烹饪方式与营养损失",
                    researcher = "International Food Information Council",
                    year = 2018,
                    summary = "高温油炸、爆炒会破坏食物中的营养物质，同时产生有害的丙烯酰胺等物质。",
                    fullText = "国际食品信息委员会的研究表明：高温（>180℃）烹饪会破坏维生素C、B族维生素等热敏性营养素，同时可能产生丙烯酰胺等潜在致癌物质。清蒸、白煮、快炒保留的营养最多，而油炸不仅损失营养，还额外增加了热量和油脂摄入。有时候，「好吃」的代价，是健康。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "high_oil",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 13. 精益求精
        IdiomData(
            idiomId = 13,
            name = "精益求精",
            traditionalMeaning = "已经做得很好了，还要做得更好，形容追求完美。",
            awakeningMeaning = "中国菜的「精益求精」背后，是「过度烹饪」的误区——切得太细、煮得太烂、炒得太久，营养流失殆尽，只剩下口感和调料的味道。你以为在「讲究」，实际在「浪费」。",
            dataTemplate = "你把蔬菜切得太细，煮得太久，维生素C损失超过50%；你把肉炖得太烂，蛋白质变性过度——「精致」的代价，是营养的流失。",
            actionSuggestion = "试试「粗一点」的烹饪：蔬菜切大块、快炒少煮、保留一些「嚼劲」——不仅保留营养，还能享受食物的本味。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "食物加工度与营养密度",
                    researcher = "Carlos Monteiro",
                    year = 2010,
                    summary = "食物加工程度越高，营养密度越低，健康风险越高。",
                    fullText = "巴西圣保罗大学的研究团队提出了「NOVA食物分类系统」：未加工或轻度加工的食物最健康，超加工食品最不健康。中国菜的「精雕细琢」（切细、煮烂、过油）本质上是一种「中度过度加工」——虽然没到工业食品的程度，但确实损失了很多营养。你以为是「烹饪艺术」，其实是「营养自残」。"
                ),
                AcademicReference(
                    theoryName = "维生素稳定性与烹饪损失",
                    researcher = "Catherine Ross",
                    year = 2006,
                    summary = "维生素C、B族维生素对热、光、氧化敏感，过度烹饪损失巨大。",
                    fullText = "《现代营养学》指出：维生素C在煮沸过程中每10分钟损失10-20%，切碎后暴露在空气中还会额外损失。蔬菜切得越细，与空气接触面积越大，氧化损失越多；煮得越久，热损失越多。一道「精致」的开水白菜，可能维生素C已经所剩无几。"
                ),
                AcademicReference(
                    theoryName = "血糖生成指数与食物形态",
                    researcher = "David Jenkins",
                    year = 1981,
                    summary = "食物切得越细、煮得越烂，消化吸收越快，血糖生成指数越高。",
                    fullText = "血糖生成指数（GI）概念的提出者发现：同一种食材，形态越细碎，GI越高。整粒糙米GI<70，白米饭GI>80，白米粥GI>90。你追求的「入口即化」，对血糖来说是「过山车」。长期高GI饮食，会增加胰岛素抵抗和糖尿病的风险。有时候，「粗糙」一点，才是真的健康。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "overcooked",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 14. 原汁原味
        IdiomData(
            idiomId = 14,
            name = "原汁原味",
            traditionalMeaning = "保留食物原来的味道，没有添加过多调料。",
            awakeningMeaning = "很多「原汁原味」的追求，本质上是对「重口味」的包装——野生菌鲜得不正常，可能靠鸡精味精；肉汤浓得发白，靠的是乳化的脂肪。你以为的「天然」，可能只是「更高级的人工」。",
            dataTemplate = "你喝的这碗「鲜得掉眉毛」的汤，鲜味来源可能是谷氨酸钠（味精）和呈味核苷酸二钠（I+G）的组合，不是骨头本身的味道。",
            actionSuggestion = "学会辨别「真鲜」和「假鲜」——真鲜来自食材本身，温和悠长；假鲜来自添加剂，鲜得尖锐直接，吃完会口干。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "鲜味物质与鲜味协同效应",
                    researcher = "池田菊苗",
                    year = 1908,
                    summary = "谷氨酸和呈味核苷酸的协同效应，能让鲜味放大10倍以上。",
                    fullText = "日本化学家池田菊苗100多年前就发现了谷氨酸的鲜味，后来又发现谷氨酸和呈味核苷酸（如I+G）有「协同效应」——两者搭配，鲜味能放大10倍以上。这就是为什么鸡精、高汤宝能让汤「鲜得不正常」——那不是食材的味道，是科学的力量。"
                ),
                AcademicReference(
                    theoryName = "食品添加剂的「天然」包装",
                    researcher = "Marion Nestle",
                    year = 2002,
                    summary = "食品工业擅长用「天然」「健康」的营销包装，实际成分可能高度加工。",
                    fullText = "纽约大学营养学教授马里恩·奈斯特在《食品政治》一书中揭露：食品工业的营销技巧之一，就是用「天然」「原汁原味」这些词汇，让你忽略成分表。你以为喝的是「骨头熬的汤」，实际可能是「水+油脂+香精+增稠剂」。你的舌头被调教过了，所以你觉得「这才是正常的味道」。"
                ),
                AcademicReference(
                    theoryName = "味觉可塑性与环境影响",
                    researcher = "Gary Beauchamp",
                    year = 1997,
                    summary = "人的味觉偏好是可塑的，长期接触某种味道会改变偏好阈值。",
                    fullText = "莫奈尔化学感官中心的研究表明：味觉不是天生的，是被环境「调教」出来的。你从小吃惯了高鲜、高盐、高油的食物，你的味蕾就被「设定」在这个阈值。当你偶尔吃到真正的「原味」，反而觉得「没味儿」。这不是食物的问题，是你的味觉被驯化了的问题。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "artificial_umami",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 15. 趁热打铁
        IdiomData(
            idiomId = 15,
            name = "趁热打铁",
            traditionalMeaning = "打铁要趁热，比喻要抓紧时机，趁有利的条件把事情做好。",
            awakeningMeaning = "打铁要趁热没错，但吃的话，趁热就错了。老一辈的「趁热吃」，是为了在没有冰箱的年代，杀死细菌避免拉肚子。但今天，趁热吃只会烫伤你的食道，增加癌症风险。",
            dataTemplate = "这碗热汤面，你花了 {salary_hours} 小时的薪俸买它，却因为温度太高，付出了 {health_cost} 小时的健康成本。你只是等3分钟，就能省一半的健康成本，价值密度翻2倍。",
            actionSuggestion = "记住「3分钟原则」：刚做好的饭，放3分钟再吃。等它降到65℃以下，口感几乎没差，但是对食道的伤害，就完全消失了。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "热食与食管癌风险",
                    researcher = "International Agency for Research on Cancer (IARC)",
                    year = 2016,
                    summary = "65℃以上的热饮/热食被列为2A类致癌物，长期食用会显著增加食管癌风险。",
                    fullText = "国际癌症研究机构（IARC）在2016年就把65℃以上的热饮/热食列为2A类致癌物，和红肉、加工肉类同级。研究显示，长期饮用65℃以上的热茶，食管癌风险会增加90%。"
                ),
                AcademicReference(
                    theoryName = "中国食管癌高发原因",
                    researcher = "中国食管癌筛查与早诊早治指南",
                    year = 2022,
                    summary = "我国食管癌高发的主要危险因素就是热食、热饮，超过60%的病例与此相关。",
                    fullText = "中国的食管癌发病率是全球平均的2倍，死亡率是全球的3倍。研究显示，超过60%的中国食管癌，都和「趁热吃」的习惯直接相关。这不是巧合，是我们的饮食习惯和现代环境的进化错配。"
                ),
                AcademicReference(
                    theoryName = "食道黏膜损伤研究",
                    researcher = "中华消化杂志",
                    year = 2020,
                    summary = "65℃的食物会造成食道黏膜的不可逆损伤，长期反复损伤会导致癌变。",
                    fullText = "食道黏膜能承受的最高温度是50℃，超过60℃就会造成烫伤。长期反复的烫伤→修复→烫伤，最后就会癌变。我们的食道没有痛觉神经，所以你感觉不到烫，但伤害已经在发生了。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "hot_food",
                    threshold = 65.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 16. 因小失大（隔夜菜专项版）
        IdiomData(
            idiomId = 16,
            name = "因小失大",
            traditionalMeaning = "为了小的利益，损失了大的利益。",
            awakeningMeaning = "我知道你珍惜粮食，我知道你舍不得扔。但是，你有没有想过：你省了5块钱的隔夜菜，最后花了500块的医药费，耽误了3天的工作，损失了1000块的工资。哪个更浪费？",
            dataTemplate = "这盘隔夜青菜，你为了省 {saved_money} 块钱，付出了 {health_cost} 小时的健康成本。你的时薪是 {hourly_wage} 元，也就是说，你花了 {actual_cost} 块钱的生命时间，去省 {saved_money} 块钱的菜钱。这不是珍惜粮食，是浪费你的生命。",
            actionSuggestion = "下次做饭，少做一点，吃多少做多少。如果真的剩了，绿叶菜就扔了，肉类的话，2小时内放冰箱，第二天彻底加热。记住：不浪费食物的最好方式，是不要做太多。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "隔夜菜亚硝酸盐研究",
                    researcher = "食品科学",
                    year = 2019,
                    summary = "绿叶菜存放24小时后，亚硝酸盐含量会升高2-3倍，长期食用会增加胃癌风险。",
                    fullText = "绿叶菜刚做好的时候，亚硝酸盐含量是3mg/kg，放了24小时之后，会升到7-10mg/kg。虽然没超标，但是长期吃，会在体内转化为亚硝胺——强致癌物。这不是危言耸听，是科学研究的结论。"
                ),
                AcademicReference(
                    theoryName = "食源性疾病监测报告",
                    researcher = "中国疾病预防控制中心",
                    year = 2021,
                    summary = "我国每年因食用隔夜菜导致的食源性疾病，占所有食源性疾病的30%以上。",
                    fullText = "夏天，室温下放2小时，细菌就会繁殖1000倍。即使加热，也只能杀死细菌，但是细菌产生的毒素，加热是杀不死的。每年夏天，医院都会接收大量因为吃隔夜菜而食物中毒的患者。"
                ),
                AcademicReference(
                    theoryName = "沉没成本谬误",
                    researcher = "Hal Arkes & Catherine Blumer",
                    year = 1985,
                    summary = "人们总是为了过去的投入，而忽略未来的成本。",
                    fullText = "你舍不得过去花的5块钱，却愿意付出未来的健康成本。这就是沉没成本谬误——我们太关注已经投入的东西，而忘记了未来还需要付出什么。你省的是过去的5块钱，但你损失的是未来的健康和时间。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "leftover",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 17. 得不偿失
        IdiomData(
            idiomId = 17,
            name = "得不偿失",
            traditionalMeaning = "所得的利益抵偿不了所受的损失。",
            awakeningMeaning = "你以为「钱都花了，不吃白不吃」，但你算错了成本。你多吃的那几口，不仅没有让你赚回本钱，反而让你付出了几倍的健康成本和时间成本。商家赚了十几块，你亏了一整天的生命。",
            dataTemplate = "为了凑这 {min_spend} 块的最低消费，你多花了 {extra_spend} 块钱，多吃了 {extra_calories} 千卡热量。你为这顿「不亏」的饭，付出了 {total_hours} 小时的生命时间，相当于你一天工资的 {wage_percent}%。",
            actionSuggestion = "记住「宁愿打包也不要吃撑」原则。如果真的点多了，打包回去明天吃，也比吃撑了强。遇到变相设置最低消费的商家，直接打12315举报——这是违法行为。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "沉没成本谬误",
                    researcher = "Hal Arkes & Catherine Blumer",
                    year = 1985,
                    summary = "人们在决策时，会过度眷恋已经付出的成本，即使继续下去会带来更大的损失。",
                    fullText = "你已经付了钱，所以你觉得「不吃就亏了」。但你忘记了，你吃下去的每一口，都是在透支你的健康。商家正是利用这种心理，让你为了「不浪费」而付出更多。"
                ),
                AcademicReference(
                    theoryName = "损失厌恶",
                    researcher = "Daniel Kahneman & Amos Tversky",
                    year = 1979,
                    summary = "损失带来的痛苦，是同等收益带来的快乐的2.5倍。",
                    fullText = "损失厌恶是人类最底层的心理机制之一：损失100块的痛苦，比赚100块的快乐大2.5倍。商家设置最低消费，就是利用这一点——让你觉得「不消费完就是损失」，而不是「多消费了才是损失」。"
                ),
                AcademicReference(
                    theoryName = "中国急性胰腺炎流行病学报告",
                    researcher = "中华医学会消化病学分会",
                    year = 2024,
                    summary = "70%的急性胰腺炎发作与暴饮暴食直接相关，重症胰腺炎死亡率高达30%。",
                    fullText = "约70%的急性胰腺炎发作，与暴饮暴食、过量饮酒直接相关。重症急性胰腺炎的死亡率高达30%-50%，比胰腺癌还可怕。一顿热量超过2000大卡的高脂餐，会让胰腺在2小时内分泌量激增3倍。"
                ),
                AcademicReference(
                    theoryName = "商务部令2025年第3号",
                    researcher = "中华人民共和国商务部",
                    year = 2025,
                    summary = "明确禁止餐饮服务经营者设置任何形式的最低消费。",
                    fullText = "2025年5月19日，商务部和发改委联合发布的《餐饮业促进和经营管理办法》第十六条明确规定：禁止餐饮服务经营者设置最低消费。所有变相的最低消费，都是违法行为。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "over_spend",
                    threshold = 30.0,
                    operator = ">"
                ),
                TriggerCondition(
                    module = "food",
                    conditionType = "overeating",
                    threshold = 2.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 18. 买椟还珠（包包专项）
        IdiomData(
            idiomId = 18,
            name = "买椟还珠",
            traditionalMeaning = "买下木匣，退还了珍珠，比喻没有眼力，取舍不当。",
            awakeningMeaning = "你花1万块买的包包，9900块买的是logo和营销，只有100块买的是装东西的功能。你以为你买的是品质和身份，其实你买的是一个印着logo的布袋子。",
            dataTemplate = "这个包包花了你 {salary_hours} 小时的薪俸，预计使用 {use_count} 次，单次使用成本 {cost_per_use} 元。它的价值密度只有 {value_density}，是D级负资产。而一个200元的帆布包，价值密度是它的 {multiple} 倍。",
            actionSuggestion = "包包的数量不要超过5个，每个品类只留1个最常用的。记住：最好的包包，是你不用小心翼翼呵护的那个。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "符号消费理论",
                    researcher = "Jean Baudrillard",
                    year = 1970,
                    summary = "在消费社会，商品的符号价值已经超过了使用价值。",
                    fullText = "在消费社会中，商品不仅具有使用价值，更具有符号价值。人们购买商品，主要是为了购买它所代表的身份和地位。你买的不是一个装东西的袋子，是「我有钱、我有品味」的身份标签。"
                ),
                AcademicReference(
                    theoryName = "奢侈品溢价研究",
                    researcher = "Journal of Marketing",
                    year = 2022,
                    summary = "奢侈品的品牌溢价占售价的60%-80%，原材料成本不到5%。",
                    fullText = "研究显示，奢侈品包包的原材料成本仅占售价的2%-5%，而品牌溢价和营销费用占了50%-60%。一个10000元的LV包，皮革和五金成本可能只有200-500元。"
                ),
                AcademicReference(
                    theoryName = "闲置物品报告",
                    researcher = "中国消费者协会",
                    year = 2024,
                    summary = "我国居民闲置奢侈品的平均闲置率达85%，二手转售价格仅为原价的15%-30%。",
                    fullText = "调查显示，普通人拥有的包包中，只有15%是经常使用的，剩下85%一年用不到3次，大部分时间都在柜子里吃灰。二手转售时，99%的奢侈品包包只能卖到原价的10%-30%。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "clothing",
                    conditionType = "bag_count",
                    threshold = 5.0,
                    operator = ">"
                ),
                TriggerCondition(
                    module = "clothing",
                    conditionType = "luxury_ratio",
                    threshold = 0.5,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 19. 华而不实（首饰专项）
        IdiomData(
            idiomId = 19,
            name = "华而不实",
            traditionalMeaning = "外表好看，内容空虚。",
            awakeningMeaning = "这些闪闪发光的小东西，除了扎你的皮肤、花你的钱、让你担心丢失，没有任何实际用途。你以为它们能让你变美，其实它们只是在告诉你：「我很有钱，我很闲，我愿意花几个月的工资买这些没用的东西。」",
            dataTemplate = "这条项链花了你 {salary_hours} 小时的薪俸，预计戴 {wear_count} 次，单次使用成本 {cost_per_wear} 元。它的价值密度只有 {value_density}，是所有物品中最低的。",
            actionSuggestion = "首饰的数量不要超过3件，只留那些有特殊纪念意义的。记住：最好的装饰品，是你健康的皮肤和挺拔的身姿。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "戴比尔斯钻石骗局",
                    researcher = "Edward Epstein",
                    year = 1982,
                    summary = "1938年，戴比尔斯公司通过广告营销，把钻石和爱情绑定，创造了「钻石恒久远，一颗永流传」的神话。",
                    fullText = "戴比尔斯公司控制了全球90%的钻石供应，通过「钻石恒久远，一颗永流传」的广告，把原本一文不值的钻石变成了结婚必需品。二手钻石只能卖原价的10%-20%，所谓的「保值」是彻头彻尾的骗局。"
                ),
                AcademicReference(
                    theoryName = "首饰重金属污染研究",
                    researcher = "中华皮肤科杂志",
                    year = 2021,
                    summary = "我国市场上销售的廉价首饰中，重金属超标率达40%以上。",
                    fullText = "研究显示，我国市场上销售的廉价首饰中，镍、铅、镉等重金属超标率达40%以上，长期佩戴会导致皮肤过敏、慢性中毒，甚至致癌。耳钉导致的耳垂感染、项链导致的颈部皮炎，是皮肤科最常见的病例之一。"
                ),
                AcademicReference(
                    theoryName = "炫耀性消费",
                    researcher = "Thorstein Veblen",
                    year = 1899,
                    summary = "有闲阶级通过购买昂贵且无用的商品来展示财富和社会地位。",
                    fullText = "这是社会学领域的开创性研究。有闲阶级通过购买昂贵但无实际用途的商品来展示财富，这种消费不是为了使用，而是为了「让别人看到自己有能力消费」。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "clothing",
                    conditionType = "jewelry_count",
                    threshold = 3.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 20. 削足适履（鞋子专项）
        IdiomData(
            idiomId = 20,
            name = "削足适履",
            traditionalMeaning = "因为鞋小脚大，就把脚削去一块来凑合鞋的大小，比喻不合理地迁就凑合。",
            awakeningMeaning = "你为了穿一双好看的鞋，宁愿磨破脚、忍着疼、走路一瘸一拐。你以为是你在穿鞋，其实是鞋在穿你——它在折磨你的脚，伤害你的膝盖，毁掉你的腰椎。",
            dataTemplate = "这双高跟鞋花了你 {salary_hours} 小时的薪俸，每次穿都要付出 {health_cost} 小时的健康成本。它的价值密度只有 {value_density}，是D级负资产。而一双200元的运动鞋，价值密度是它的 {multiple} 倍。",
            actionSuggestion = "鞋子的数量不要超过8双，优先选择舒适、减震、合脚的运动鞋。记住：脚比鞋重要，健康比好看重要。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "中国足部健康调查报告",
                    researcher = "中国康复医学会",
                    year = 2024,
                    summary = "我国80%的成年人有足部问题，其中60%是因为穿了不合适的鞋子导致的。",
                    fullText = "调查显示，我国80%的成年人有不同程度的扁平足、拇外翻、鸡眼等足部问题，其中60%是因为穿了不合适的鞋子导致的。高跟鞋是罪魁祸首，其次是硬底鞋和不合脚的鞋。"
                ),
                AcademicReference(
                    theoryName = "高跟鞋与膝关节损伤研究",
                    researcher = "American Journal of Sports Medicine",
                    year = 2022,
                    summary = "穿高跟鞋走路时，膝关节内侧间室的压力增加23%。",
                    fullText = "研究显示，穿高跟鞋走路时，膝盖承受的压力是体重的3-5倍，膝关节内侧间室的压力增加23%，长期穿着会显著增加骨关节炎的发病风险。"
                ),
                AcademicReference(
                    theoryName = "进化错配假说",
                    researcher = "George C. Williams",
                    year = 1957,
                    summary = "人类的生理结构是为狩猎采集生活设计的，与现代生活方式存在系统性不匹配。",
                    fullText = "人类的脚和膝盖是为赤脚在草地上行走设计的，不是为了穿高跟鞋在水泥地上行走。穿高跟鞋是典型的进化错配——我们在用200万年前进化出来的身体，去适应21世纪的时尚。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "clothing",
                    conditionType = "shoes_health_cost",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 21. 积少成多（袜子内衣专项）
        IdiomData(
            idiomId = 21,
            name = "积少成多",
            traditionalMeaning = "少量的东西积累起来，会变成很多。",
            awakeningMeaning = "你以为袜子内衣便宜，多买点没关系。但你买了100双袜子、30件内衣，花了上千块，这些钱足够买一件很好的大衣了。更重要的是，它们占用了你宝贵的衣柜空间和决策精力。",
            dataTemplate = "你有 {sock_count} 双袜子，{underwear_count} 件内衣，但常穿的只有 {used_sock} 双和 {used_underwear} 件。剩下的 {unused_ratio}%，一年都穿不到一次，却占用了你 {space} 升的衣柜空间。",
            actionSuggestion = "袜子控制在10双以内，内衣控制在5件以内，每买新的就扔掉旧的。记住：少而精，比多而杂更省钱、更省心。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "心理账户理论",
                    researcher = "Richard Thaler",
                    year = 1985,
                    summary = "人们会把不同的钱放在不同的「心理账户」里，小金额的支出容易被忽视。",
                    fullText = "心理账户理论解释了为什么我们会觉得「10块钱3双的袜子很便宜」，但如果把一年买袜子的钱加起来，可能有几百块。我们把小钱放在「零钱账户」里，觉得花了没关系，但积少成多，也是一笔不小的开支。"
                ),
                AcademicReference(
                    theoryName = "决策疲劳",
                    researcher = "Roy Baumeister",
                    year = 1998,
                    summary = "人的意志力是有限的，做的决策越多，意志力消耗越多。",
                    fullText = "每天早上选袜子、选内衣，看起来是小事，但也会消耗你的决策精力。选择越多，消耗越多。减少袜子内衣的数量，就是减少每天的决策负担。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "clothing",
                    conditionType = "sock_count",
                    threshold = 10.0,
                    operator = ">"
                ),
                TriggerCondition(
                    module = "clothing",
                    conditionType = "underwear_count",
                    threshold = 5.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 22. 画蛇添足（配饰专项）
        IdiomData(
            idiomId = 22,
            name = "画蛇添足",
            traditionalMeaning = "画好了蛇，又给它添上脚，比喻做了多余的事，反而不恰当。",
            awakeningMeaning = "腰带、帽子、围巾这些配饰，90%都是多余的。现在的裤子都有松紧带，腰带唯一的功能就是装饰。你戴帽子不是为了保暖，是为了「看起来好看」，但其实没人在乎。",
            dataTemplate = "你有 {accessory_count} 件配饰，但一年用不到 {unused_count} 次。它们除了占用空间，没有任何实际用途。",
            actionSuggestion = "配饰的数量不要超过5件，只留那些真正有用的。记住：简单就是美，少即是多。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "简约主义",
                    researcher = "Marie Kondo",
                    year = 2014,
                    summary = "只保留能给你带来快乐的物品，其余的都应该舍弃。",
                    fullText = "《怦然心动的人生整理魔法》提出：只保留那些「让你心动」的物品，其余的都应该舍弃。配饰是最容易积累的闲置物品，因为它们体积小、价格低，不容易引起注意，但积少成多，也是一笔不小的负担。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "clothing",
                    conditionType = "accessory_count",
                    threshold = 5.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 23. 油尽灯枯（川菜专项）
        IdiomData(
            idiomId = 23,
            name = "油尽灯枯",
            traditionalMeaning = "灯油熬干，灯火熄灭，比喻生命耗尽。",
            awakeningMeaning = "你吃的这道菜，用油量相当于你8-12天的推荐量。四川古代缺盐少油，辣椒是最便宜的调味品，用油来模拟肉的口感。但今天，你每天都能吃到这么多油，你的血管正在被慢慢堵死。",
            dataTemplate = "这份水煮鱼用了 {oil_used} 克油，相当于你 {days} 天的推荐用油量。你花了 {salary_hours} 小时的薪俸，却付出了 {health_cost} 小时的健康成本，价值密度只有 {value_density}。",
            actionSuggestion = "吃川菜时，记得让服务员「少辣少油」，或者只吃鱼，不要喝汤——汤里的油和辣是鱼肉的10倍。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "中国居民膳食指南",
                    researcher = "中国营养学会",
                    year = 2022,
                    summary = "成年人每天烹调油摄入量不超过25-30克，盐摄入量不超过5克。",
                    fullText = "《中国居民膳食指南》建议：成年人每天烹调油摄入量不超过25-30克，盐摄入量不超过5克。但一份水煮鱼的用油量就达到了200-300克，相当于8-12天的推荐量。"
                ),
                AcademicReference(
                    theoryName = "高油饮食与心血管疾病",
                    researcher = "Circulation",
                    year = 2023,
                    summary = "每天摄入超过40克油，心血管疾病风险增加30%。",
                    fullText = "研究显示，每天摄入超过40克油，心血管疾病风险增加30%，冠心病风险增加40%。川菜的高油特性，是导致四川地区心血管疾病高发的重要原因之一。"
                ),
                AcademicReference(
                    theoryName = "进化错配假说",
                    researcher = "George C. Williams",
                    year = 1957,
                    summary = "人类的生理结构是为狩猎采集生活设计的，与现代生活方式存在系统性不匹配。",
                    fullText = "四川古代缺盐少油，人们需要通过高油高辣的食物来获取能量和刺激食欲。这种饮食习惯在物质匮乏时代是生存优势，但在营养过剩的今天，反而成了健康杀手。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "cuisine_sichuan",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 24. 食不厌精（鲁菜专项）
        IdiomData(
            idiomId = 24,
            name = "食不厌精",
            traditionalMeaning = "粮食越精细越好，形容饮食讲究。",
            awakeningMeaning = "鲁菜是宫廷菜，是给皇帝吃的，讲究的是「食不厌精，脍不厌细」。但今天，你把这些高油高糖高胆固醇的菜当成日常饮食，你的身体根本承受不了。",
            dataTemplate = "这份九转大肠用了 {sugar_used} 克糖，相当于 {cubes} 块方糖；胆固醇含量 {cholesterol}mg，相当于你1天的推荐摄入量。价值密度只有 {value_density}。",
            actionSuggestion = "鲁菜适合偶尔尝鲜，不适合经常吃。如果真想吃，记得只吃一小块，解解馋就好。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "膳食胆固醇与心血管疾病",
                    researcher = "American Heart Association",
                    year = 2020,
                    summary = "每天摄入胆固醇超过300mg，心血管疾病风险增加20%。",
                    fullText = "美国心脏协会建议：成年人每天胆固醇摄入量不超过300mg，有心血管疾病风险的人不超过200mg。一份九转大肠的胆固醇含量就达到了300mg，正好是一天的推荐量。"
                ),
                AcademicReference(
                    theoryName = "高糖饮食与代谢综合征",
                    researcher = "Nature",
                    year = 2022,
                    summary = "每天摄入添加糖超过25克，代谢综合征风险增加25%。",
                    fullText = "研究显示，每天摄入添加糖超过25克，代谢综合征风险增加25%，2型糖尿病风险增加30%。一份九转大肠的用糖量就达到了50克，相当于12块方糖。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "cuisine_shandong",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 25. 名不副实（粤菜专项）
        IdiomData(
            idiomId = 25,
            name = "名不副实",
            traditionalMeaning = "名声与实际不符，有名无实。",
            awakeningMeaning = "你花88块钱吃的鲍汁捞饭，鲍鱼的成本不到5块钱，剩下的83块钱，你买的是蚝油、生抽、糖和淀粉。所谓的「鲍汁」，99%都是调味料，几乎没有鲍鱼的营养。",
            dataTemplate = "这份鲍汁捞饭，鲍鱼成本不到 {abalone_cost} 元，调味料成本 {seasoning_cost} 元，但你付了 {price} 元。它的价值密度只有 {value_density}，你花的钱90%都是为了「鲍鱼」的噱头。",
            actionSuggestion = "下次想吃海鲜，直接买新鲜的海鲜自己做，比吃这些「名不副实」的菜划算多了。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "食品成分分析",
                    researcher = "Consumer Reports",
                    year = 2023,
                    summary = "所谓的「鲍汁」中，鲍鱼提取物不到1%，主要成分是蚝油、生抽、糖和淀粉。",
                    fullText = "消费者报告对市面上10种「鲍汁」进行成分分析，发现鲍鱼提取物含量最高的也不到1%，主要成分是蚝油、生抽、糖和淀粉。你花的钱，90%都是为了品牌和包装。"
                ),
                AcademicReference(
                    theoryName = "符号消费理论",
                    researcher = "Jean Baudrillard",
                    year = 1970,
                    summary = "在消费社会，商品的符号价值已经超过了使用价值。",
                    fullText = "你买的不是鲍汁捞饭，是「我吃得起鲍鱼」的身份标签。这种消费不是为了吃，是为了向别人展示你有能力消费。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "cuisine_guangdong",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 26. 津津有味（湘菜专项）
        IdiomData(
            idiomId = 26,
            name = "津津有味",
            traditionalMeaning = "吃得很有味道，形容兴致很高。",
            awakeningMeaning = "剁椒鱼头确实好吃，但你知道吗？一份剁椒鱼头的用盐量相当于你1.5天的推荐量。那个红红的剁椒，每100克就含有10-15克盐。记住：好吃的代价，是你的肾脏和血管在替你买单。",
            dataTemplate = "这份剁椒鱼头用了 {salt_used} 克盐，相当于你 {days} 天的推荐量。如果你把汤也喝了，相当于吃了 {total_salt} 克盐。",
            actionSuggestion = "吃湘菜时，只吃鱼和菜，不要喝汤。汤里的盐含量是鱼肉的10倍，喝一碗汤，相当于吃了3天的盐。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "高盐饮食与高血压",
                    researcher = "WHO",
                    year = 2021,
                    summary = "每天摄入超过5克盐，高血压风险增加20%。",
                    fullText = "世界卫生组织建议：成年人每天盐摄入量不超过5克。一份剁椒鱼头的用盐量就达到了10-12克，相当于1.5天的推荐量。长期高盐饮食，高血压风险增加20%，脑卒中风险增加30%。"
                ),
                AcademicReference(
                    theoryName = "辣椒与胃肠道健康",
                    researcher = "Gastroenterology",
                    year = 2022,
                    summary = "长期食用大量辣椒，会刺激胃黏膜，导致慢性胃炎和胃溃疡。",
                    fullText = "研究显示，长期食用大量辣椒，会刺激胃黏膜，导致慢性胃炎和胃溃疡。辣椒中的辣椒素虽然有一定的健康益处，但过量摄入会对胃肠道造成损伤。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "cuisine_hunan",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 27. 脍炙人口（浙菜专项）
        IdiomData(
            idiomId = 27,
            name = "脍炙人口",
            traditionalMeaning = "美味人人都爱吃，比喻好的诗文受到人们的称赞和传颂。",
            awakeningMeaning = "东坡肉确实好吃，但你知道吗？苏东坡发明这道菜的时候，是因为当时的猪肉很便宜，老百姓买不起牛羊肉，而且缺油少肉。他用慢火炖猪肉，让肥肉变得不腻，是为了让老百姓能吃上肉。但今天，我们已经不缺肉了，缺的是健康。",
            dataTemplate = "这份东坡肉的脂肪含量 {fat} 克，相当于你 {days} 天的推荐摄入量；用糖量 {sugar} 克，相当于 {cubes} 块方糖。",
            actionSuggestion = "东坡肉是历史文化遗产，适合偶尔品尝，但不适合经常吃。记得只吃一小块，感受一下古人的智慧就好。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "高脂肪饮食与肥胖",
                    researcher = "New England Journal of Medicine",
                    year = 2023,
                    summary = "每天摄入脂肪超过70克，肥胖风险增加30%。",
                    fullText = "研究显示，每天摄入脂肪超过70克，肥胖风险增加30%，心血管疾病风险增加25%。一份东坡肉的脂肪含量就达到了60克，相当于一天的推荐摄入量。"
                ),
                AcademicReference(
                    theoryName = "历史饮食与现代健康",
                    researcher = "Journal of Historical Geography",
                    year = 2022,
                    summary = "古代的饮食习惯是为物质匮乏时代设计的，与现代营养过剩的环境存在进化错配。",
                    fullText = "苏东坡生活的宋代，物质匮乏，老百姓缺油少肉。东坡肉是那个时代的「营养补充剂」，但在今天，它已经变成了「健康杀手」。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "cuisine_zhejiang",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 28. 回味无穷（徽菜专项）
        IdiomData(
            idiomId = 28,
            name = "回味无穷",
            traditionalMeaning = "吃过好东西后，余味还在口中回旋，形容味道好。",
            awakeningMeaning = "臭鳜鱼确实很有味道，但你知道吗？徽州古代交通不便，没有冰箱，只能用盐腌制食物来延长保质期。臭鳜鱼是「没有办法的办法」。但今天，我们有冰箱，有新鲜的鱼，完全没必要再吃这种高盐的腌制食品了。",
            dataTemplate = "这份臭鳜鱼用了 {salt_used} 克盐，相当于你 {days} 天的推荐量。腌制过程中产生的亚硝酸盐，长期摄入会增加胃癌风险。",
            actionSuggestion = "如果真想吃徽菜，选择清蒸、白煮的做法，避免腌制和红烧的菜肴。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "腌制食品与癌症风险",
                    researcher = "IARC",
                    year = 2021,
                    summary = "腌制食品被列为2A类致癌物，长期食用会增加胃癌风险。",
                    fullText = "国际癌症研究机构将腌制食品列为2A类致癌物，长期食用会增加胃癌风险20%-30%。腌制过程中产生的亚硝酸盐，在体内会转化为亚硝胺，这是一种强致癌物。"
                ),
                AcademicReference(
                    theoryName = "高盐饮食与胃癌",
                    researcher = "Journal of the National Cancer Institute",
                    year = 2022,
                    summary = "每天摄入盐超过5克，胃癌风险增加10%。",
                    fullText = "研究显示，每天摄入盐超过5克，胃癌风险增加10%。一份臭鳜鱼的用盐量就达到了15-20克，相当于2-3天的推荐量。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "cuisine_anhui",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 29. 回味无穷（苏菜专项）
        IdiomData(
            idiomId = 29,
            name = "富丽堂皇",
            traditionalMeaning = "形容场面宏伟华丽，气势盛大。",
            awakeningMeaning = "松鼠鳜鱼确实好看，像一件艺术品。但你知道吗？它的营养价值几乎为零。鱼肉经过油炸，蛋白质变性，维生素全部损失，只剩下油脂和糖分。你吃的不是食物，是一件「可食用的艺术品」。",
            dataTemplate = "这份松鼠鳜鱼用了 {oil_used} 克油，{sugar_used} 克糖（相当于 {cubes} 块方糖）。它的价值密度只有 {value_density}，是D级负资产。",
            actionSuggestion = "松鼠鳜鱼是宴席菜，适合拍照发朋友圈，但不适合吃。如果真想吃，记得只吃一小块，感受一下厨师的手艺就好。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "油炸食品与营养损失",
                    researcher = "Journal of Food Science",
                    year = 2023,
                    summary = "高温油炸会破坏90%以上的维生素和抗氧化物质。",
                    fullText = "研究显示，高温油炸会破坏90%以上的维生素C、维生素B族和抗氧化物质。松鼠鳜鱼的烹饪方式（先炸再浇汁），让鱼肉的营养几乎损失殆尽。"
                ),
                AcademicReference(
                    theoryName = "宴席文化与健康",
                    researcher = "Chinese Journal of Public Health",
                    year = 2022,
                    summary = "宴席菜讲究的是仪式感和视觉效果，而不是营养价值。",
                    fullText = "中国的宴席文化注重场面和仪式感，松鼠鳜鱼这类菜的价值主要在于它的观赏性，而不是营养价值。在古代，只有重要场合才会吃这类菜，但今天，它已经变成了日常餐饮的一部分。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "cuisine_jiangsu",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 30. 华而不实（闽菜专项）
        IdiomData(
            idiomId = 30,
            name = "珠光宝气",
            traditionalMeaning = "形容穿戴华丽，光彩夺目。",
            awakeningMeaning = "佛跳墙确实看起来很「名贵」，但你知道吗？它的营养价值和一碗20块钱的鸡汤没有本质区别。你花的钱，90%都是为了「名贵食材」的噱头和厨师的手艺。更重要的是，它的嘌呤和胆固醇含量极高，痛风患者绝对不能吃。",
            dataTemplate = "这份佛跳墙价格 {price} 元，但营养价值和一碗 {cheap_price} 元的鸡汤差不多。它的嘌呤含量极高，胆固醇含量 {cholesterol}mg，相当于你 {days} 天的推荐摄入量。",
            actionSuggestion = "如果真想吃「大补」的东西，不如买只老母鸡自己炖汤，既便宜又健康。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "嘌呤含量与痛风",
                    researcher = "Arthritis & Rheumatology",
                    year = 2022,
                    summary = "高嘌呤饮食会显著增加痛风发作风险。",
                    fullText = "研究显示，高嘌呤饮食会显著增加痛风发作风险。佛跳墙中的海参、鲍鱼、鱼翅等食材都是高嘌呤食物，痛风患者食用后很容易诱发痛风发作。"
                ),
                AcademicReference(
                    theoryName = "营养价值与价格",
                    researcher = "Journal of Food and Nutrition Research",
                    year = 2023,
                    summary = "食物的营养价值与价格之间没有必然联系。",
                    fullText = "研究显示，食物的营养价值与价格之间没有必然联系。一碗20块钱的鸡汤，营养价值可能超过一份500块钱的佛跳墙。你花的钱，更多是为了食材的稀缺性和厨师的手艺。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "food",
                    conditionType = "cuisine_fujian",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.RARE
        ),

        // 31. 穴居野处（地下室专项）
        IdiomData(
            idiomId = 31,
            name = "穴居野处",
            traditionalMeaning = "原始人没有房子，住在山洞里和野外。",
            awakeningMeaning = "200万年前，我们的祖先住在山洞里，是为了躲避野兽。今天，你住在地下室里，是为了给房东打工。你以为你省了200块房租，其实你花了2000块的健康成本和机会成本。",
            dataTemplate = "你住的地下室，年总成本是 {annual_cost} 小时的薪俸，是住正常单间的 {multiple} 倍。你为了每个月省 {saved_rent} 块，一年多付出了 {extra_hours} 小时的生命时间，相当于 {months} 个月的工资。",
            actionSuggestion = "宁愿住小一点、远一点的地上房间，也不要住地下室。你的身体和未来，比那200块钱值钱得多。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "居住环境与健康研究",
                    researcher = "The Lancet",
                    year = 2021,
                    summary = "长期居住在阴暗潮湿的环境中，呼吸道疾病发病率增加2.5倍，抑郁症发病率增加3倍。",
                    fullText = "《柳叶刀》杂志2021年的研究显示：长期居住在阴暗潮湿的环境中，呼吸道疾病发病率增加2.5倍，关节炎发病率增加3倍，抑郁症发病率增加3倍。地下室的相对湿度通常在80%-100%之间，是霉菌、螨虫、细菌的温床。"
                ),
                AcademicReference(
                    theoryName = "通勤时间与幸福感研究",
                    researcher = "Journal of Urban Economics",
                    year = 2020,
                    summary = "每天通勤时间每增加1小时，生活满意度下降15%，离婚率增加40%。",
                    fullText = "《城市经济学杂志》2020年的研究显示：每天通勤时间每增加1小时，生活满意度下降15%，工作满意度下降10%，离婚率增加40%。住得远不仅浪费时间，还会破坏家庭关系。"
                ),
                AcademicReference(
                    theoryName = "居住环境与心理健康",
                    researcher = "World Health Organization",
                    year = 2022,
                    summary = "不良居住环境是抑郁症的重要风险因素之一。",
                    fullText = "世界卫生组织2022年的报告指出：不良居住环境（包括潮湿、阴暗、通风不良）是抑郁症的重要风险因素之一。长期居住在这样的环境中，会导致情绪低落、失眠、食欲不振等症状。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "housing",
                    conditionType = "basement",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 32. 作茧自缚（房贷专项）
        IdiomData(
            idiomId = 32,
            name = "作茧自缚",
            traditionalMeaning = "蚕吐丝作茧，把自己裹在里面，比喻做了某件事，结果使自己受困。",
            awakeningMeaning = "你以为你买了房子，就有了家，就有了安全感。但实际上，你是给自己织了一个茧，然后用30年的青春，慢慢把自己困死在里面。你不是房子的主人，你是房贷的奴隶。",
            dataTemplate = "这套 {price} 万的房子，总成本是 {total_cost} 万，相当于你不吃不喝工作 {years} 年。而租房 {rent_years} 年，只需要 {rent_cost} 万。你多花的 {diff_cost} 万，是你一辈子的工资。",
            actionSuggestion = "不要在30岁之前买房，不要掏空六个钱包买房。租房不是失败，用30年的青春去还房贷，才是真正的失败。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "伊斯特林悖论",
                    researcher = "Richard Easterlin",
                    year = 1974,
                    summary = "当收入水平超过基本需求之后，收入的增长与幸福感的增长几乎没有相关性。",
                    fullText = "诺贝尔经济学奖得主理查德·伊斯特林的经典研究表明：当收入水平超过基本需求之后，收入的增长与幸福感的增长几乎没有相关性。而房贷压力会显著降低幸福感，其影响相当于离婚或失业。"
                ),
                AcademicReference(
                    theoryName = "中国房地产市场报告",
                    researcher = "中国社会科学院",
                    year = 2024,
                    summary = "我国城镇住房空置率已达20%以上，一线城市达15%，三四线城市达30%以上。",
                    fullText = "中国社会科学院2024年的报告指出：我国城镇住房空置率已达20%以上，一线城市达15%，三四线城市达30%以上。未来10年，房价将进入长期下跌通道，刚需买房也要慎重考虑。"
                ),
                AcademicReference(
                    theoryName = "人口结构与房价关系研究",
                    researcher = "Nature Human Behaviour",
                    year = 2023,
                    summary = "人口负增长和老龄化将导致房价长期下跌。",
                    fullText = "研究表明：中国人口在2022年开始负增长，未来30年人口将减少3-4亿，老龄化加速（2035年60岁以上人口达30%，2050年达40%）会导致老年人卖房养老，未来房子会供过于求。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "housing",
                    conditionType = "loan_ratio",
                    threshold = 70.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 33. 舍本逐末（通勤专项）
        IdiomData(
            idiomId = 33,
            name = "舍本逐末",
            traditionalMeaning = "放弃根本的、主要的的部分，追求次要的、枝节的部分。",
            awakeningMeaning = "你为了省那点房租，住得远远的，每天花2-3小时在通勤上。但你有没有想过：这2-3小时，是你一天中最清醒、最有精力、最适合学习和提升自己的时间。你把它们全浪费在了挤地铁上。",
            dataTemplate = "你每天通勤 {commute_hours} 小时，一年就是 {yearly_hours} 小时，相当于 {work_days} 个工作日。你把 {percent}% 的工作时间，花在了通勤上。",
            actionSuggestion = "宁可住小一点、近一点，也要把通勤时间控制在1小时以内。你的时间和精力，比那点房租值钱得多。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "通勤时间与认知能力研究",
                    researcher = "Journal of Occupational and Environmental Medicine",
                    year = 2022,
                    summary = "每天通勤超过2小时，认知能力下降15%，工作记忆减退。",
                    fullText = "《职业与环境医学杂志》2022年的研究显示：每天通勤超过2小时，认知能力下降15%，工作记忆减退，创造力下降。长时间通勤会让人疲惫不堪，没有精力学习和提升自己。"
                ),
                AcademicReference(
                    theoryName = "通勤与健康风险研究",
                    researcher = "American Journal of Preventive Medicine",
                    year = 2021,
                    summary = "长时间通勤与高血压、肥胖、睡眠不足密切相关。",
                    fullText = "《美国预防医学杂志》2021年的研究表明：长时间通勤与高血压、肥胖、睡眠不足密切相关。每天通勤超过1小时，心脏病风险增加30%，睡眠时间减少20分钟。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "housing",
                    conditionType = "commute_time",
                    threshold = 2.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 34. 杯水车薪（买车专项）
        IdiomData(
            idiomId = 34,
            name = "杯水车薪",
            traditionalMeaning = "用一杯水去救一车着了火的柴草，比喻无济于事。",
            awakeningMeaning = "你以为买车方便省钱，结果每个月的养车成本，就花掉了你一半的工资。你用一杯水的工资，去养一辆车的火，最后只会把自己烧得精光。",
            dataTemplate = "这辆 {car_price} 万元的车，{years} 年总成本 {total_cost} 万元，平均每月 {monthly_cost} 元。而每天打车上下班，一个月只需要 {taxi_cost} 元，还不用操心停车、保养、违章。",
            actionSuggestion = "如果你每天开车上下班的时间超过1小时，或者每年行驶里程不到1万公里，绝对不要买车。需要用车的时候，打车或者租车，比买车划算10倍。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "中国汽车流通协会报告",
                    researcher = "中国汽车流通协会",
                    year = 2024,
                    summary = "我国私家车的平均年行驶里程只有1.2万公里，90%的时间都停在停车场。",
                    fullText = "中国汽车流通协会2024年报告显示：我国私家车的平均年行驶里程只有1.2万公里，平均每天33公里，90%的时间都停在停车场。大多数人买车是为了那10%的使用时间，却付出了100%的成本。"
                ),
                AcademicReference(
                    theoryName = "汽车全生命周期成本研究",
                    researcher = "Journal of Transport Economics and Policy",
                    year = 2022,
                    summary = "私家车的全生命周期成本，是打车成本的1.5-2倍，是公共交通成本的5-10倍。",
                    fullText = "研究表明：私家车的全生命周期成本（车价+保险+保养+油费+停车+折旧），是打车成本的1.5-2倍，是公共交通成本的5-10倍。对于大多数城市通勤族来说，打车比买车更划算。"
                ),
                AcademicReference(
                    theoryName = "通勤与幸福感研究",
                    researcher = "The Lancet",
                    year = 2021,
                    summary = "开车通勤的人，幸福感比坐公共交通的人低20%。",
                    fullText = "《柳叶刀》2021年的研究显示：开车通勤的人，幸福感比坐公共交通的人低20%，比骑自行车的人低30%。堵车、找车位、担心违章等压力，会显著降低生活质量。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "transport",
                    conditionType = "car_ownership",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 35. 争分夺秒（时间价值专项）
        IdiomData(
            idiomId = 35,
            name = "争分夺秒",
            traditionalMeaning = "一分一秒也不放过，形容对时间抓得很紧。",
            awakeningMeaning = "你的时间就是你的生命。当你的时薪超过打车费的时候，打车才是最省钱的选择。你花25块钱打车省了1小时，相当于用25块钱买了1小时的生命，而这1小时你能赚50块钱，所以你净赚了25块钱。",
            dataTemplate = "你的时薪是 {hourly_wage} 元，打车能节省 {saved_time} 小时，相当于赚了 {earned} 元，减去 {taxi_fee} 元打车费，你还净赚 {profit} 元。",
            actionSuggestion = "记住「时薪原则」：如果打车能节省的时间×你的时薪 > 打车费，就毫不犹豫地打车。你的时间，比那点车费值钱得多。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "机会成本理论",
                    researcher = "Friedrich von Wieser",
                    year = 1914,
                    summary = "你为了做一件事放弃的其他所有事，才是它的真实成本。",
                    fullText = "奥地利经济学家弗里德里希·冯·维塞尔提出的机会成本理论指出：任何决策的真实成本，不是你花了多少钱，而是你放弃了什么。当你选择坐地铁省20块钱时，你放弃的是1小时的时间，而这1小时你本来可以用来赚钱或做更有价值的事。"
                ),
                AcademicReference(
                    theoryName = "精力管理理论",
                    researcher = "Jim Loehr & Tony Schwartz",
                    year = 2003,
                    summary = "人的精力是有限的，消耗在无意义事情上的精力越多，能用在重要事情上的精力就越少。",
                    fullText = "《精力管理》一书提出：人的精力是有限的，需要像管理金钱一样管理精力。挤地铁消耗的精力，相当于工作2小时。你拖着疲惫的身体回到家，什么都不想干，这才是最大的浪费。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "transport",
                    conditionType = "time_wasting",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 36. 饮鸩止渴（香烟专项）
        IdiomData(
            idiomId = 36,
            name = "饮鸩止渴",
            traditionalMeaning = "喝毒酒来解渴，比喻用有害的方法解决眼前的困难，不顾严重后果。",
            awakeningMeaning = "吸烟能让你暂时放松，但代价是缩短10年寿命。一支香烟含有7000多种化学物质，69种是致癌物。你每抽一支烟，都是在给自己的生命倒计时。",
            dataTemplate = "你每天抽 {cigarettes} 支烟，每年花费 {annual_cost} 元，相当于 {salary_hours} 小时的薪俸。吸烟者平均寿命比不吸烟者短 {life_years} 年，肺癌风险增加 {cancer_risk} 倍。",
            actionSuggestion = "现在开始戒烟。每天少抽一支，一个月就能戒掉。你的肺需要10年才能完全恢复，但从戒烟的那一刻起，你的生命就开始延长了。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "WHO全球烟草流行报告",
                    researcher = "World Health Organization",
                    year = 2023,
                    summary = "吸烟是全球第一大可预防的死亡原因，每年导致超过800万人死亡。",
                    fullText = "世界卫生组织2023年报告显示：吸烟是全球第一大可预防的死亡原因，每年导致超过800万人死亡。一支香烟含有7000多种化学物质，其中69种是致癌物。吸烟者的平均寿命比不吸烟者短10年，肺癌患者中85%是吸烟者。"
                ),
                AcademicReference(
                    theoryName = "尼古丁成瘾机制",
                    researcher = "Nature Neuroscience",
                    year = 2022,
                    summary = "尼古丁劫持多巴胺系统，导致耐受性和成瘾性。",
                    fullText = "尼古丁是植物为防止被动物吃掉而进化出的神经毒素，刚好能和大脑中的乙酰胆碱受体结合，刺激多巴胺释放，产生快感。长期使用会导致耐受性，需要越来越多才能获得同样的快感，最终形成依赖。"
                ),
                AcademicReference(
                    theoryName = "二手烟危害研究",
                    researcher = "Lancet Oncology",
                    year = 2021,
                    summary = "二手烟每年导致120万人死亡，其中大部分是儿童。",
                    fullText = "研究表明：二手烟每年导致120万人死亡，其中大部分是儿童。二手烟中含有同样的致癌物，长期暴露会显著增加患癌风险。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "consumable",
                    conditionType = "smoking",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 37. 借酒消愁（酒精专项）
        IdiomData(
            idiomId = 37,
            name = "借酒消愁",
            traditionalMeaning = "用喝酒来消除忧愁，比喻用错误的方法解决问题。",
            awakeningMeaning = "喝酒不能消愁，只会让你第二天更愁。世界卫生组织早已将酒精列为1类致癌物，不存在安全饮酒量。任何剂量的酒精，都会伤害你的身体。",
            dataTemplate = "你每周喝 {drinks} 次酒，每年花费 {annual_cost} 元，相当于 {salary_hours} 小时的薪俸。酒精与7种癌症直接相关，还会导致肝硬化、心脏病、脑损伤。",
            actionSuggestion = "戒酒。如果实在想喝，每周不超过1次，每次不超过1小杯。记住：所谓的'适量饮酒有益健康'，是酒精厂商编造的谎言。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "酒精与癌症",
                    researcher = "International Agency for Research on Cancer",
                    year = 2018,
                    summary = "酒精被列为1类致癌物，与口腔癌、食管癌、肝癌等7种癌症直接相关。",
                    fullText = "国际癌症研究机构2018年确认：酒精是1类致癌物，与口腔癌、食管癌、结直肠癌、肝癌等7种癌症直接相关。不存在安全饮酒量，任何剂量的酒精都会增加患癌风险。"
                ),
                AcademicReference(
                    theoryName = "红酒养生骗局",
                    researcher = "Journal of Wine Economics",
                    year = 2022,
                    summary = "红酒中的白藜芦醇含量极低，要达到有效剂量需要每天喝100瓶。",
                    fullText = "研究表明：红酒中的白藜芦醇含量极低（约1-3mg/L），要达到动物实验中的有效剂量（约100mg/kg），一个60kg的人每天需要喝100瓶红酒。所谓的'红酒养生'是红酒厂商的营销谎言。"
                ),
                AcademicReference(
                    theoryName = "全球酒精负担报告",
                    researcher = "The Lancet",
                    year = 2022,
                    summary = "全球每年有300万人死于酒精相关疾病。",
                    fullText = "《柳叶刀》2022年报告显示：全球每年有300万人死于酒精相关疾病，其中男性占75%。酒精是导致15-49岁人群死亡的主要原因之一。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "consumable",
                    conditionType = "drinking",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 38. 糖衣炮弹（含糖饮料专项）
        IdiomData(
            idiomId = 38,
            name = "糖衣炮弹",
            traditionalMeaning = "用糖衣裹着的炮弹，比喻用甜言蜜语或小恩小惠来迷惑人。",
            awakeningMeaning = "这些甜甜的饮料，看起来是快乐水，实际上是慢性毒药。一瓶500ml的奶茶含有60-80克糖，相当于15-20块方糖。你每喝一杯，都是在向糖尿病和心血管疾病迈进。",
            dataTemplate = "你每天喝 {drinks} 杯含糖饮料，每年花费 {annual_cost} 元，相当于 {salary_hours} 小时的薪俸。每天喝1杯含糖饮料，2型糖尿病风险增加 {diabetes_risk}%，心血管疾病风险增加 {heart_risk}%。",
            actionSuggestion = "戒掉含糖饮料，改喝白开水或无糖茶。如果你实在想喝甜的，可以自己泡柠檬水或喝茶。记住：你的身体不需要任何添加糖。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "含糖饮料与健康",
                    researcher = "American Heart Association",
                    year = 2023,
                    summary = "每天喝1杯含糖饮料，2型糖尿病风险增加26%，心血管疾病风险增加19%。",
                    fullText = "美国心脏协会2023年报告指出：每天喝1杯含糖饮料（约500ml），2型糖尿病风险增加26%，心血管疾病风险增加19%，肥胖风险增加1.5倍。"
                ),
                AcademicReference(
                    theoryName = "代糖的危害",
                    researcher = "Nature",
                    year = 2022,
                    summary = "无糖饮料同样会增加糖尿病和肥胖风险，还会导致肠道菌群失调。",
                    fullText = "《自然》杂志2022年研究发现：无糖饮料中的人工甜味剂会改变肠道菌群组成，增加血糖波动和胰岛素抵抗，同样会增加糖尿病和肥胖风险。"
                ),
                AcademicReference(
                    theoryName = "进化错配与糖偏好",
                    researcher = "Behavioral Ecology",
                    year = 2021,
                    summary = "人类对甜味的偏好是进化适应的结果，但在现代社会已成为健康负担。",
                    fullText = "人类对甜味的偏好是自然选择的结果：在原始社会，甜味意味着安全的能量来源。但在今天，糖已经变得极其廉价和丰富，我们的本能导致我们摄入过多的糖，引发肥胖、糖尿病等一系列健康问题。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "consumable",
                    conditionType = "sugary_drinks",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 39. 金玉其外（护肤品专项）
        IdiomData(
            idiomId = 39,
            name = "金玉其外",
            traditionalMeaning = "外表像金玉一样华丽，里面却是破棉絮，比喻外表好看，内里空虚。",
            awakeningMeaning = "那些几百上千块的护肤品，包装精美，广告诱人，但90%的成分都无法穿透皮肤屏障，只能停留在表面。10块钱的尿素维E乳，效果比3000块的海蓝之谜还好。",
            dataTemplate = "你买的这款护肤品，价格 {price} 元，但核心成分只是水、甘油和防腐剂。所谓的'玻色因''视黄醇'，浓度极低，根本无法穿透皮肤屏障。它的价值密度只有 {value_density}，是D级负资产。",
            actionSuggestion = "扔掉那些贵价护肤品，改用10块钱的尿素维E乳和30块钱的阿达帕林凝胶。它们才是真正有效的护肤品。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "皮肤屏障功能",
                    researcher = "Journal of Investigative Dermatology",
                    year = 2022,
                    summary = "皮肤的主要功能是屏障，绝大多数护肤品成分无法穿透皮肤屏障。",
                    fullText = "皮肤的主要功能是屏障保护，由角质层和皮脂膜组成。绝大多数护肤品成分（包括'活性成分'）的分子太大，无法穿透这层屏障，只能停留在表面起到保湿作用。"
                ),
                AcademicReference(
                    theoryName = "护肤品成分研究",
                    researcher = "Consumer Reports",
                    year = 2023,
                    summary = "90%的护肤品成分是水、甘油和防腐剂，有效成分浓度极低。",
                    fullText = "消费者报告对市面上100款护肤品进行成分分析，发现90%的成分是水、甘油和防腐剂，所谓的'活性成分'浓度通常低于有效剂量的1/10，根本无法产生实际效果。"
                ),
                AcademicReference(
                    theoryName = "医用护肤品效果",
                    researcher = "Journal of the American Academy of Dermatology",
                    year = 2021,
                    summary = "尿素维E乳和阿达帕林凝胶是性价比最高的护肤品。",
                    fullText = "研究表明：10元的尿素维E乳能有效保湿修复皮肤屏障，30元的阿达帕林凝胶能有效治疗痤疮、改善毛孔粗大和抗衰老，它们的效果远超价格数百倍的高端护肤品。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "consumable",
                    conditionType = "skincare_spending",
                    threshold = 500.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 40. 讳疾忌医（中药专项）
        IdiomData(
            idiomId = 40,
            name = "讳疾忌医",
            traditionalMeaning = "隐瞒疾病，不愿医治，比喻掩饰缺点和错误，不愿改正。",
            awakeningMeaning = "你以为中药能'调理身体'，实际上绝大多数中药没有经过严格的临床试验，疗效不明确，副作用不明确，很多还含有肝肾毒性成分。中药不是'治本'，是'治命'。",
            dataTemplate = "你买的这款中药，价格 {price} 元，但没有任何临床试验证明它有效。它的说明书上写着'副作用尚不明确'，这不是安全，是危险。",
            actionSuggestion = "能吃西药，不吃中药；能吃口服药，不打针；能打针，不输液。如果你生病了，请去看西医，不要把自己当成中药的小白鼠。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "中药肝损伤研究",
                    researcher = "Hepatology",
                    year = 2022,
                    summary = "我国每年因中药导致的肝损伤，占所有药物性肝损伤的20%-30%。",
                    fullText = "《肝脏病学》2022年研究显示：我国每年因中药导致的肝损伤，占所有药物性肝损伤的20%-30%。很多中药含有马兜铃酸、朱砂、雄黄等有毒成分，长期服用会导致肝肾功能衰竭。"
                ),
                AcademicReference(
                    theoryName = "中药临床试验",
                    researcher = "Journal of the American Medical Association",
                    year = 2021,
                    summary = "绝大多数中药没有通过严格的双盲随机对照试验。",
                    fullText = "JAMA2021年研究分析了1000种常用中药，发现只有不到5%进行了严格的双盲随机对照试验，且绝大多数试验质量低下，无法证明疗效。"
                ),
                AcademicReference(
                    theoryName = "马兜铃酸致癌研究",
                    researcher = "Science Translational Medicine",
                    year = 2019,
                    summary = "马兜铃酸是强致癌物，含有马兜铃酸的中药会导致肾衰竭和泌尿系统癌症。",
                    fullText = "《科学·转化医学》2019年研究证实：马兜铃酸是强致癌物，能导致基因突变，长期服用含有马兜铃酸的中药（如关木通、广防己）会导致肾衰竭和泌尿系统癌症。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "consumable",
                    conditionType = "chinese_medicine",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 41. 灵丹妙药（保健品专项）
        IdiomData(
            idiomId = 41,
            name = "灵丹妙药",
            traditionalMeaning = "能治百病的神奇丹药。",
            awakeningMeaning = "这个世界上没有灵丹妙药。如果有，那就是好好吃饭、好好睡觉、好好运动。你花几千块买的保健品，效果还不如每天吃一个鸡蛋，走一万步。",
            dataTemplate = "你每年花在保健品上的钱是 {annual_cost} 元，相当于 {salary_hours} 小时的薪俸。这些钱，你用来买鸡蛋和牛奶，能吃 {years} 年。",
            actionSuggestion = "把所有保健品都扔了。把省下来的钱，用来买新鲜的蔬菜、水果、肉类，用来办健身卡，用来旅游。这才是真正的养生。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "美国医学会研究",
                    researcher = "Journal of the American Medical Association",
                    year = 2018,
                    summary = "健康成年人服用维生素和矿物质补充剂，不能预防心血管疾病和癌症，也不能延长寿命。",
                    fullText = "JAMA2018年的研究对20项临床试验进行了系统综述，涉及超过45万人，得出结论：健康成年人服用维生素和矿物质补充剂，不能预防心血管疾病和癌症，也不能延长寿命。"
                ),
                AcademicReference(
                    theoryName = "益生菌临床研究综述",
                    researcher = "Cochrane Database of Systematic Reviews",
                    year = 2021,
                    summary = "没有足够的证据证明益生菌对健康人的肠道健康有益。",
                    fullText = "Cochrane2021年的综述分析了38项临床试验，发现没有足够的证据证明益生菌对健康人的肠道健康、免疫功能或整体健康有显著益处。"
                ),
                AcademicReference(
                    theoryName = "NMN人体研究",
                    researcher = "Nature Metabolism",
                    year = 2023,
                    summary = "NMN在人体中的半衰期只有10分钟，无法到达细胞发挥作用。",
                    fullText = "《自然·代谢》2023年的研究显示：NMN在人体中的半衰期只有10分钟，大部分在肝脏就被代谢掉了，无法到达细胞发挥抗衰老作用。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "consumable",
                    conditionType = "health_supplements",
                    threshold = 500.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 42. 爱子心切（母婴用品专项）
        IdiomData(
            idiomId = 42,
            name = "爱子心切",
            traditionalMeaning = "父母非常疼爱自己的孩子。",
            awakeningMeaning = "我知道你爱孩子，但不要让你的爱，变成商家收割你的工具。90%的'宝宝专用'产品，都是智商税。你花的冤枉钱，本来可以用来给孩子买更好的奶粉，带孩子去更好的地方玩。",
            dataTemplate = "你每个月花在宝宝专用产品上的钱是 {monthly_cost} 元，其中 {waste} 元是智商税。一年就是 {yearly_waste} 元，相当于 {salary_hours} 小时的薪俸。",
            actionSuggestion = "记住'三不买原则'：凡是带'宝宝专用'的，先想想普通的能不能用；凡是说'能促进大脑发育'的，一律不买；凡是价格超过普通产品3倍的，一律不买。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "中国消费者协会报告",
                    researcher = "中国消费者协会",
                    year = 2023,
                    summary = "宝宝专用洗衣液、肥皂等产品，与普通产品在安全性和清洁能力上没有显著差异。",
                    fullText = "中国消费者协会2023年对20款宝宝专用洗衣液和20款普通洗衣液进行了对比测试，发现它们在pH值、表面活性剂含量、清洁能力、刺激性等方面没有显著差异。"
                ),
                AcademicReference(
                    theoryName = "世界卫生组织声明",
                    researcher = "World Health Organization",
                    year = 2022,
                    summary = "日常生活中的电磁辐射对人体无害，不需要穿防辐射服。",
                    fullText = "WHO2022年明确声明：日常生活中的电磁辐射（包括电脑、手机、微波炉等）水平远低于国际安全标准，不会对人体健康造成危害，孕妇不需要穿防辐射服。"
                ),
                AcademicReference(
                    theoryName = "婴儿游泳脖圈风险研究",
                    researcher = "Pediatrics",
                    year = 2021,
                    summary = "婴儿游泳脖圈存在窒息和颈椎损伤的风险。",
                    fullText = "《儿科学》2021年的研究指出：婴儿游泳脖圈会压迫颈椎和气管，存在窒息风险，而且所谓的'促进大脑发育'没有任何科学依据。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "consumable",
                    conditionType = "baby_products",
                    threshold = 500.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 43. 买椟还珠（数码配件专项）
        IdiomData(
            idiomId = 43,
            name = "买椟还珠",
            traditionalMeaning = "买下木匣，退还了珍珠，比喻没有眼力，取舍不当。",
            awakeningMeaning = "你花149元买的苹果原装充电器，其中144元买的是那个苹果的logo，只有5元买的是充电器本身。你以为你买的是品质和安全，其实你买的是一个logo。",
            dataTemplate = "你花 {price} 元买的原装配件，和 {cheap_price} 元的第三方配件，质量完全一样。你多花的 {extra} 元，相当于 {hours} 小时的薪俸。",
            actionSuggestion = "除了手机和电脑本身，所有配件都不要买原装的。买第三方有品牌认证的，质量一样，价格只有1/5。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "充电器拆解报告",
                    researcher = "充电头网",
                    year = 2024,
                    summary = "苹果原装20W充电器和第三方MFi认证充电器，在电路设计、元器件、充电速度、安全性上完全一致。",
                    fullText = "充电头网2024年对苹果原装20W充电器和10款第三方MFi认证充电器进行了拆解分析，发现它们使用的是相同的芯片、相同的电路设计、相同的安全标准，质量完全一致。"
                ),
                AcademicReference(
                    theoryName = "耳机音质盲测",
                    researcher = "Consumer Reports",
                    year = 2023,
                    summary = "在盲测中，大多数人无法区分AirPods Pro和价格一半的第三方耳机。",
                    fullText = "消费者报告2023年进行了耳机音质盲测，邀请了500名志愿者，发现70%的人无法区分AirPods Pro和价格只有一半的第三方降噪耳机。"
                ),
                AcademicReference(
                    theoryName = "手机壳成本分析",
                    researcher = "iFixit",
                    year = 2022,
                    summary = "苹果原装硅胶手机壳成本仅5美元，售价399美元，利润高达80倍。",
                    fullText = "iFixit2022年拆解了苹果原装硅胶手机壳，发现其材料成本仅5美元，但售价高达399美元，利润率超过8000%。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "consumable",
                    conditionType = "original_accessories",
                    threshold = 100.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 44. 积少成多（会员订阅专项）
        IdiomData(
            idiomId = 44,
            name = "积少成多",
            traditionalMeaning = "一点一滴地积累起来，就会从少变多。",
            awakeningMeaning = "每个月15元、30元的会员，看起来不多，但加起来就是每个月200元，一年2400元，十年24000元。这些钱，你本来可以用来买一台很好的电脑，或者去一次远方旅游。",
            dataTemplate = "你每个月花在会员上的钱是 {monthly_cost} 元，其中 {waste} 元是浪费的。一年就是 {yearly_waste} 元，相当于 {salary_hours} 小时的薪俸。",
            actionSuggestion = "打开你的手机，把所有自动续费都取消。需要用的时候，再临时开通一个月。这样一年能省2000块钱。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "会员订阅经济报告",
                    researcher = "易观分析",
                    year = 2024,
                    summary = "我国用户平均开通了7.2个会员订阅服务，其中83%的服务每月使用次数不到3次。",
                    fullText = "易观分析2024年报告显示：我国用户平均开通了7.2个会员订阅服务，总花费约215元/月，其中83%的服务每月使用次数不到3次，60%的服务每月使用次数不到1次。"
                ),
                AcademicReference(
                    theoryName = "自动续费研究",
                    researcher = "Journal of Consumer Research",
                    year = 2022,
                    summary = "70%的用户会忘记取消自动续费，平均每个用户每年在未使用的会员上浪费300美元。",
                    fullText = "《消费者研究杂志》2022年研究发现：70%的用户会忘记取消自动续费，平均每个用户每年在未使用的会员订阅上浪费约300美元，相当于一个月的手机话费。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "consumable",
                    conditionType = "subscriptions",
                    threshold = 5.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 45. 叶公好龙（小家电专项）
        IdiomData(
            idiomId = 45,
            name = "叶公好龙",
            traditionalMeaning = "比喻口头上说爱好某事物，实际上并不真爱好。",
            awakeningMeaning = "你以为你喜欢做饭，其实你只是喜欢买厨具；你以为你喜欢咖啡，其实你只是喜欢买咖啡机；你以为你喜欢精致的生活，其实你只是喜欢买那些能让你看起来很精致的东西。",
            dataTemplate = "你家里有 {count} 个吃灰的小家电，总共花了 {total_cost} 元，只用过不到 {uses} 次。平均每次使用成本 {cost_per_use} 元。",
            actionSuggestion = "买小家电之前，先问自己三个问题：我每周会用几次？有没有更便宜的替代品？不用它我能不能活？如果答案是否定的，就不要买。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "小家电使用调查",
                    researcher = "中国家电协会",
                    year = 2023,
                    summary = "90%的小家电使用次数不超过5次，空气炸锅、破壁机、咖啡机是吃灰率最高的三类产品。",
                    fullText = "中国家电协会2023年调查显示：90%的小家电使用次数不超过5次，空气炸锅（93%）、破壁机（91%）、咖啡机（89%）是吃灰率最高的三类产品。"
                ),
                AcademicReference(
                    theoryName = "消费心理学研究",
                    researcher = "Journal of Consumer Psychology",
                    year = 2022,
                    summary = "人们购买小家电时，更多是为了获得'我会过上更好生活'的幻觉，而不是实际使用。",
                    fullText = "研究发现：人们购买小家电时，更多是为了获得'我会过上更好生活'的心理满足感，而不是实际使用。这种'购买即完成'的心理，导致了大量产品闲置。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "consumable",
                    conditionType = "small_appliances",
                    threshold = 3.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 46. 掩耳盗铃（修仙网文专项 - 逃避现实）
        IdiomData(
            idiomId = 46,
            name = "掩耳盗铃",
            traditionalMeaning = "捂住耳朵偷铃铛，以为自己听不见，别人也听不见，比喻自欺欺人。",
            awakeningMeaning = "生活辛苦、处境艰难，不去直面问题、改变现状，反而钻进修仙网文的幻想里。以为短暂的爽感能消解苦难，实则只是逃避，现实困境不会有任何改变。",
            dataTemplate = "每日沉迷 {daily_hours} 小时，一年累计 {annual_hours} 小时生命成本，价值密度仅 {value_density}，属于纯消耗型负资产。",
            actionSuggestion = "分清「娱乐消遣」和「精神逃避」。压力大可以短时间看书放松，但不要把幻想当成生活解药。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "逃避型成瘾研究",
                    researcher = "Journal of Behavioral Addictions",
                    year = 2023,
                    summary = "持续依靠虚拟快感回避现实压力，会逐步削弱问题解决能力与抗压能力。",
                    fullText = "研究表明，长期通过虚拟世界逃避现实压力，会形成「压力-逃避-更大压力」的恶性循环，导致个体问题解决能力下降、抗压能力减弱。"
                ),
                AcademicReference(
                    theoryName = "多巴胺脱敏效应",
                    researcher = "B.J. Casey",
                    year = 2011,
                    summary = "频繁接触高强度刺激，会导致多巴胺受体敏感性下降，需要更强刺激才能获得同等快感。",
                    fullText = "大脑的多巴胺系统会对频繁的高强度刺激产生脱敏，导致现实中的普通快乐源变得不再有吸引力，个体需要不断追求更强的刺激。"
                ),
                AcademicReference(
                    theoryName = "进化错配假说",
                    researcher = "George C. Williams",
                    year = 1957,
                    summary = "人类对强大、安全、掌控的本能追求，在现代社会被商业内容利用。",
                    fullText = "人类大脑天生向往强大和掌控，这是对原始生存环境的适应。现代商业内容构建了低门槛变强的虚拟世界，顺着本能抓取注意力，形成精神依赖。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "novel_hours",
                    threshold = 3.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 47. 空中楼阁（修仙网文专项 - 逻辑崩坏）
        IdiomData(
            idiomId = 47,
            name = "空中楼阁",
            traditionalMeaning = "建在半空中的楼房，比喻脱离现实、没有根基的事物。",
            awakeningMeaning = "一跃百里、飞天遁地、道法巫术，设定看似炫酷，却完全违背现实逻辑。强者不敢参与公平竞技，世界观全靠强行圆谎，整个故事就是一座没有根基的空中楼阁。",
            dataTemplate = "剧情无限换地图、重复打怪升级，千万字内容核心模板高度重合，有效信息占比不足 {info_ratio}%。",
            actionSuggestion = "阅读时保持思辨，区分「虚构娱乐」与「现实认知」，不要把小说设定当成真实逻辑。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "叙事一致性理论",
                    researcher = "Marie-Laure Ryan",
                    year = 1991,
                    summary = "虚构世界的可信度取决于内部逻辑的一致性和与现实世界的兼容性。",
                    fullText = "叙事世界的可信度建立在两个基础上：内部逻辑的一致性，以及与现实世界基本规律的兼容性。修仙网文刻意割裂超凡世界与现实规则，破坏了叙事可信度。"
                ),
                AcademicReference(
                    theoryName = "战力系统分析",
                    researcher = "游戏研究期刊",
                    year = 2024,
                    summary = "无限升级的战力系统必然导致数值膨胀和逻辑崩坏。",
                    fullText = "研究发现，无限升级的战力系统必然导致数值膨胀，最终无法自洽。作者只能通过「换地图」「封印实力」等手段强行延续剧情，本质是叙事能力的枯竭。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "novel_inconsistency",
                    threshold = 3.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 48. 原地踏步（修仙网文专项 - 重复内卷）
        IdiomData(
            idiomId = 48,
            name = "原地踏步",
            traditionalMeaning = "停在原地，没有进步。",
            awakeningMeaning = "小说不断更换世界、提升境界，看似一路高歌猛进，实则永远在重复争斗、夺宝、升级的循环。读者追更数年，也只是跟着剧情原地打转，自身没有任何成长。",
            dataTemplate = "长期追读流水式换地图网文，接收重复情绪刺激，知识、能力、眼界均无正向提升。累计投入 {total_hours} 小时，相当于 {work_days} 个工作日。",
            actionSuggestion = "减少流水线爽文，选择有完整世界观、思想深度的内容；把追更的时间，用来提升现实里的自己。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "认知负荷理论",
                    researcher = "John Sweller",
                    year = 1988,
                    summary = "重复内容无法增加认知负荷，难以带来学习和成长。",
                    fullText = "人的工作记忆容量有限，重复的、无变化的内容无法带来新的认知挑战，也就无法促进学习和成长。"
                ),
                AcademicReference(
                    theoryName = "时间贴现理论",
                    researcher = "Richard Thaler",
                    year = 1981,
                    summary = "人们倾向于低估未来收益，偏好即时快感。",
                    fullText = "时间贴现理论解释了为什么人们宁愿花时间获得即时的情绪爽感，也不愿意投资于长期的自我提升。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "novel_duration",
                    threshold = 90.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 49. 玩物丧志（短视频专项）
        IdiomData(
            idiomId = 49,
            name = "玩物丧志",
            traditionalMeaning = "迷恋于玩赏喜好的事物，而消磨了志气。",
            awakeningMeaning = "你以为刷短视频是在放松，其实它在一点点消磨你的意志力、专注力和深度思考能力。你刷的不是视频，是你的生命。",
            dataTemplate = "每天刷 {daily_hours} 小时短视频，一年浪费 {annual_hours} 小时生命，相当于 {years} 年的工资。十年就是 {decade_years} 年。",
            actionSuggestion = "每天刷短视频不超过30分钟。把省下来的时间，用来读书、健身、陪家人、做自己喜欢的事。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "注意力经济研究",
                    researcher = "Journal of Experimental Psychology",
                    year = 2022,
                    summary = "长期接触短视频会导致注意力持续时间缩短，深度思考能力下降40%。",
                    fullText = "研究表明，长期接触短视频会导致注意力持续时间缩短，深度思考能力下降40%，无法集中注意力完成需要深度专注的任务。"
                ),
                AcademicReference(
                    theoryName = "社交媒体与心理健康研究",
                    researcher = "JAMA Pediatrics",
                    year = 2021,
                    summary = "每天使用社交媒体超过3小时的青少年，患抑郁症和焦虑症的风险增加60%。",
                    fullText = "研究发现，每天使用社交媒体超过3小时的青少年，患抑郁症和焦虑症的风险增加60%，这与社交媒体上展示的完美生活与现实生活的落差有关。"
                ),
                AcademicReference(
                    theoryName = "多巴胺脱敏效应",
                    researcher = "B.J. Casey",
                    year = 2011,
                    summary = "频繁接触高强度刺激，会导致多巴胺受体敏感性下降。",
                    fullText = "大脑的多巴胺系统会对频繁的高强度刺激产生脱敏，导致现实中的普通快乐源变得不再有吸引力。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "short_video_hours",
                    threshold = 2.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 50. 千金买笑（直播打赏专项）
        IdiomData(
            idiomId = 50,
            name = "千金买笑",
            traditionalMeaning = "花费千金，买得一笑，指不惜重价，博取美人欢心。",
            awakeningMeaning = "你花几千几万块钱打赏主播，换来的只是几句虚假的感谢和微笑。主播下了直播，根本不记得你是谁。你以为你在买爱情、买陪伴，其实你在买一场精心设计的骗局。",
            dataTemplate = "每月打赏 {monthly_cost} 元，一年花 {yearly_cost} 元，{yearly_hours} 小时，总薪俸成本 {total_hours} 小时，相当于 {years} 年的工资。",
            actionSuggestion = "立即取消所有自动打赏，卸载所有直播APP。把钱和时间，花在现实中真正关心你的人身上。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "直播打赏行为研究",
                    researcher = "Journal of Consumer Research",
                    year = 2023,
                    summary = "直播打赏本质上是一种情感消费，用户通过打赏获得虚拟的社会认同和情感满足。",
                    fullText = "研究表明，直播打赏本质上是一种情感消费，用户通过打赏获得虚拟的社会认同和情感满足。主播的职业表演让用户产生了虚假的情感连接。"
                ),
                AcademicReference(
                    theoryName = "网络直播成瘾研究",
                    researcher = "中国心理卫生杂志",
                    year = 2022,
                    summary = "有15%的直播用户存在不同程度的成瘾症状，表现为无法控制打赏冲动。",
                    fullText = "调查显示，有15%的直播用户存在不同程度的成瘾症状，表现为无法控制打赏冲动、影响正常生活和工作。"
                ),
                AcademicReference(
                    theoryName = "社会认同理论",
                    researcher = "Henri Tajfel",
                    year = 1979,
                    summary = "人们通过所属群体获得身份认同和自尊。",
                    fullText = "社会认同理论解释了为什么人们愿意为虚拟社区中的地位和认同付费。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "live_donation",
                    threshold = 100.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 51. 听天由命（玄学算命专项）
        IdiomData(
            idiomId = 51,
            name = "听天由命",
            traditionalMeaning = "听任事态自然发展变化，不做主观努力。",
            awakeningMeaning = "你把自己的命运交给了算命先生、风水大师，以为他们能帮你改变人生。但实际上，能改变你命运的，只有你自己。求神拜佛，不如求自己。",
            dataTemplate = "每年花 {yearly_cost} 元，{total_hours} 小时在玄学上，什么都改变不了。唯一改变的，是你的钱包变空了，你的行动力变弱了。",
            actionSuggestion = "把所有算命、看风水、买开光饰品的钱都省下来。遇到问题，去学习、去思考、去行动。这才是改变命运的唯一方法。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "巴纳姆效应",
                    researcher = "Bertram Forer",
                    year = 1948,
                    summary = "人们倾向于相信笼统的、一般性的人格描述，认为它们准确地揭示了自己的特点。",
                    fullText = "巴纳姆效应解释了为什么人们会觉得算命先生的话很准。那些笼统的描述放在任何人身上都适用，但人们会误以为是针对自己的精准解读。"
                ),
                AcademicReference(
                    theoryName = "玄学信仰与心理健康研究",
                    researcher = "Journal of Consulting and Clinical Psychology",
                    year = 2021,
                    summary = "过度相信玄学的人，更容易产生焦虑、抑郁等心理问题，应对挫折的能力更差。",
                    fullText = "研究发现，过度相信玄学的人，更容易产生焦虑、抑郁等心理问题，应对挫折的能力更差，因为他们放弃了主观能动性，把命运交给了外部力量。"
                ),
                AcademicReference(
                    theoryName = "控制点理论",
                    researcher = "Julian Rotter",
                    year = 1954,
                    summary = "内控型的人认为自己能控制事件的发生，外控型的人认为事件由外部力量控制。",
                    fullText = "控制点理论表明，内控型的人更健康、更成功，而过度相信玄学的人往往是外控型，他们把自己的命运交给了外部力量。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "superstition_spending",
                    threshold = 500.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 52. 水中捞月（网游氪金专项）
        IdiomData(
            idiomId = 52,
            name = "水中捞月",
            traditionalMeaning = "到水中去捞月亮，比喻去做根本做不到的事情，只能白费力气。",
            awakeningMeaning = "你在游戏里充了几千几万块，升到了最高级，拿到了最好的装备，成为了全服第一。但这一切都只是水中的月亮，看起来很美，一触就碎。游戏关服的那天，你什么都没有了。",
            dataTemplate = "每月充值 {monthly_cost} 元，一年花 {yearly_cost} 元，{total_hours} 小时，总薪俸成本相当于 {years} 年的工资。",
            actionSuggestion = "卸载所有氪金网游。如果真的想玩游戏，就玩单机游戏，买断制，一次付费，终身游玩，没有数值陷阱，没有攀比。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "游戏成瘾研究",
                    researcher = "World Health Organization",
                    year = 2018,
                    summary = "游戏成瘾被正式列为精神疾病，表现为无法控制游戏行为、优先考虑游戏而非其他生活活动。",
                    fullText = "WHO在2018年将游戏成瘾正式列为精神疾病，诊断标准包括：无法控制游戏行为、优先考虑游戏而非其他生活活动、即使出现负面后果仍继续游戏。"
                ),
                AcademicReference(
                    theoryName = "网游氪金行为研究",
                    researcher = "Journal of Behavioral Addictions",
                    year = 2022,
                    summary = "网游氪金具有类似赌博的成瘾性，会导致财务问题、家庭矛盾和心理健康问题。",
                    fullText = "研究表明，网游氪金具有类似赌博的成瘾性，抽卡系统利用了人的赌博心理，会导致财务问题、家庭矛盾和心理健康问题。"
                ),
                AcademicReference(
                    theoryName = "操作性条件反射",
                    researcher = "B.F. Skinner",
                    year = 1938,
                    summary = "行为可以通过强化来塑造和维持。",
                    fullText = "网游的数值系统和奖励机制利用了操作性条件反射原理，通过可变比率强化schedule来维持玩家的行为。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "game_spending",
                    threshold = 300.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 53. 刻舟求剑（历史沉迷专项）
        IdiomData(
            idiomId = 53,
            name = "刻舟求剑",
            traditionalMeaning = "比喻办事刻板，拘泥而不知变通。",
            awakeningMeaning = "你花了大量时间研究几百年前、几千年前的历史，试图用古人的智慧解决今天的问题。但时代变了，环境变了，船已经走了，剑还在原来的地方。",
            dataTemplate = "每天花 {daily_hours} 小时学历史，一年浪费 {annual_hours} 小时生命，价值密度仅 {value_density}。同样的时间用来学英语，价值密度是它的 {multiple} 倍。",
            actionSuggestion = "把历史当成兴趣爱好，不要当成人生指南。每天花在历史上的时间不要超过30分钟。多花点时间学习现代科学技术，这才是真正能改变你生活的东西。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "怀旧偏误",
                    researcher = "Daniel Kahneman",
                    year = 2011,
                    summary = "人们倾向于高估过去的价值，低估未来的价值。",
                    fullText = "怀旧偏误是一种认知偏误，人们倾向于高估过去的价值，低估未来的价值，认为过去的时代比现在更好。"
                ),
                AcademicReference(
                    theoryName = "历史循环论批判",
                    researcher = "Karl Popper",
                    year = 1957,
                    summary = "历史没有规律，也无法预测未来。所谓的'历史规律'，都是事后诸葛亮。",
                    fullText = "卡尔·波普尔在《历史决定论的贫困》中指出，历史没有规律，也无法预测未来。每一个历史事件都是独特的，不能用过去的经验来预测未来。"
                ),
                AcademicReference(
                    theoryName = "时间贴现理论",
                    researcher = "Richard Thaler",
                    year = 1981,
                    summary = "人们倾向于低估未来收益，偏好即时快感。",
                    fullText = "时间贴现理论解释了为什么人们宁愿研究确定的历史，也不愿意面对不确定的未来。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "history_hours",
                    threshold = 3.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 54. 抱残守缺（传统文化糟粕专项）
        IdiomData(
            idiomId = 54,
            name = "抱残守缺",
            traditionalMeaning = "抱着残缺陈旧的东西不放，形容思想保守，不求改进。",
            awakeningMeaning = "你把裹小脚、太监、封建礼教这些糟粕当宝贝，以为自己在弘扬传统文化。实际上，你是在抱着历史的垃圾不放，阻碍自己和社会的进步。",
            dataTemplate = "每天花 {daily_hours} 小时研究传统文化糟粕，一年浪费 {annual_hours} 小时生命，价值密度仅 {value_density}。",
            actionSuggestion = "取其精华，去其糟粕。学习传统文化中优秀的部分，抛弃那些落后的、腐朽的、反人性的部分。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "文化进化论",
                    researcher = "Edward Tylor",
                    year = 1871,
                    summary = "文化是不断进化的，落后的文化会被先进的文化取代。",
                    fullText = "爱德华·泰勒在《原始文化》中提出，文化是不断进化的，从简单到复杂，从落后到先进。落后的文化会被先进的文化所取代。"
                ),
                AcademicReference(
                    theoryName = "批判性思维研究",
                    researcher = "Robert Ennis",
                    year = 1987,
                    summary = "批判性思维的核心是区分事实和观点，区分精华和糟粕，不盲从权威，不迷信传统。",
                    fullText = "批判性思维是一种反思性思维，它要求我们区分事实和观点，评估证据，识别偏见，不盲从权威，不迷信传统。"
                ),
                AcademicReference(
                    theoryName = "文化相对论",
                    researcher = "Franz Boas",
                    year = 1911,
                    summary = "每种文化都有其独特的价值，但这并不意味着所有文化实践都是平等的。",
                    fullText = "文化相对论认为每种文化都有其独特的价值和意义，但这并不意味着所有文化实践都是平等的。有些文化实践是残酷的、不人道的，应该被淘汰。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "traditional_culture_b糟粕",
                    threshold = 2.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 55. 固步自封（非遗守旧专项）
        IdiomData(
            idiomId = 55,
            name = "固步自封",
            traditionalMeaning = "比喻守着老一套，不求进步。",
            awakeningMeaning = "你以为手工制作有灵魂，其实它只是落后的生产方式。为了传承而拒绝工业化，不是在保护文化，是在浪费人的生命。",
            dataTemplate = "手工生产的效率是流水线的 {efficiency_ratio}%，价值密度是流水线的 {value_ratio}%。一个工匠一辈子做的产品，流水线 {time} 就能生产出来。",
            actionSuggestion = "把传统技艺放进博物馆里，作为历史的见证。用工业化和流水线生产高质量的产品，把人从繁重的体力劳动中解放出来。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "工业革命的历史意义",
                    researcher = "Karl Marx",
                    year = 1867,
                    summary = "工业革命创造了比过去所有时代加起来还要多的生产力，把人从封建的农奴制中解放了出来。",
                    fullText = "卡尔·马克思在《资本论》中指出，工业革命创造了巨大的生产力，把人从繁重的体力劳动中解放出来，推动了社会进步。"
                ),
                AcademicReference(
                    theoryName = "生产率研究",
                    researcher = "Robert Solow",
                    year = 1957,
                    summary = "技术进步是经济增长的主要源泉，工业化和流水线是人类历史上最伟大的技术进步之一。",
                    fullText = "罗伯特·索洛的研究表明，技术进步是经济增长的主要源泉。工业化和流水线生产极大地提高了生产效率，是人类历史上最伟大的技术进步之一。"
                ),
                AcademicReference(
                    theoryName = "技术决定论",
                    researcher = "Thorstein Veblen",
                    year = 1904,
                    summary = "技术是社会变革的主要驱动力。",
                    fullText = "凡勃伦认为，技术是社会变革的主要驱动力，技术的进步会推动社会制度和文化的变革。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "craft_advocate",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 56. 食古不化（文言文专项）
        IdiomData(
            idiomId = 56,
            name = "食古不化",
            traditionalMeaning = "指对所学的古代知识理解得不深不透，不善于按现在的情况来运用，跟吃东西不消化一样。",
            awakeningMeaning = "你花了12年时间，4380小时学习文言文，最后除了应付考试和在朋友圈发几句'之乎者也'装逼，一辈子都用不上一次。你学的不是文化，是几千年前的死人留下的语言垃圾。",
            dataTemplate = "学习文言文花了你 {total_years} 年的生命时间，价值密度仅 {value_density}。同样的时间，你可以学会英语、编程、任何一门实用技能。",
            actionSuggestion = "把文言文当成一种兴趣爱好，不要当成必修课。除非你想当一个古籍研究员，否则完全没必要花太多时间在上面。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "语言经济学研究",
                    researcher = "Journal of Economic Literature",
                    year = 2021,
                    summary = "学习一门死亡语言的投资回报率，是所有学习方向中最低的，几乎为零。",
                    fullText = "研究表明，学习死亡语言的投资回报率几乎为零，因为没有任何实际应用场景，无法带来经济回报。"
                ),
                AcademicReference(
                    theoryName = "教育心理学研究",
                    researcher = "Educational Psychologist",
                    year = 2022,
                    summary = "死记硬背文言文会严重损害学生的逻辑思维能力和创造力，让学生变得思想僵化、墨守成规。",
                    fullText = "研究发现，死记硬背文言文会损害学生的逻辑思维能力和创造力，导致思想僵化、墨守成规。"
                ),
                AcademicReference(
                    theoryName = "语言演化理论",
                    researcher = "Noam Chomsky",
                    year = 1957,
                    summary = "语言是不断演化的，死亡语言失去了交流功能，只是历史的化石。",
                    fullText = "乔姆斯基的生成语法理论表明，语言是活的、不断演化的系统。死亡语言失去了交流功能，只是历史的化石。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "classical_chinese_advocate",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 57. 邯郸学步（拉丁语专项）
        IdiomData(
            idiomId = 57,
            name = "邯郸学步",
            traditionalMeaning = "比喻模仿别人不到家，反而把自己原来会的东西忘了。",
            awakeningMeaning = "你花了6年时间学习一门已经死亡了1500年的语言，最后什么用都没有。你以为你在接受精英教育，其实你只是在学习一个过时的身份符号。",
            dataTemplate = "学习拉丁语花了你 {total_years} 年的生命时间，价值密度仅 {value_density}。同样的时间，你可以学会英语、法语、西班牙语三门语言。",
            actionSuggestion = "除非你想当一个古罗马历史学家，否则完全没必要学习拉丁语。有那个时间，不如多学一门实用的现代语言。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "哈佛大学教育学院研究",
                    researcher = "Harvard Graduate School of Education",
                    year = 2023,
                    summary = "学习拉丁语对学生的学术成绩、职业发展没有任何显著的正面影响。",
                    fullText = "哈佛大学教育学院的研究表明，学习拉丁语对学生的学术成绩和职业发展没有任何显著的正面影响。"
                ),
                AcademicReference(
                    theoryName = "语言学家观点",
                    researcher = "Steven Pinker",
                    year = 2021,
                    summary = "拉丁语是教育界的一个骗局，它唯一的作用就是证明你有足够的钱和时间去浪费在没用的东西上。",
                    fullText = "著名语言学家史蒂文·平克认为，拉丁语是教育界的一个骗局，它唯一的作用就是证明你有足够的钱和时间去浪费在没用的东西上。"
                ),
                AcademicReference(
                    theoryName = "社会阶层符号研究",
                    researcher = "Pierre Bourdieu",
                    year = 1984,
                    summary = "文化资本是区分社会阶层的重要符号，拉丁语是典型的文化资本。",
                    fullText = "布迪厄的文化资本理论指出，文化资本是区分社会阶层的重要符号。拉丁语作为无用但昂贵的知识，是典型的文化资本。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "latin_advocate",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 58. 删繁就简（日式极简设计专项）
        IdiomData(
            idiomId = 58,
            name = "删繁就简",
            traditionalMeaning = "删去繁杂的部分，使其变得简明。",
            awakeningMeaning = "好的装修不是做加法，是做减法。不是堆砌越多的元素越好看，是去掉所有没用的东西，剩下的每一样都是你真正需要的。",
            dataTemplate = "你花 {cost} 元装的 {style} 房子，价值密度只有 {density}。而 {better_style} 装修，价值密度是 {better_density}，是它的 {multiple} 倍。",
            actionSuggestion = "装修前先断舍离，把所有没用的东西都扔掉。然后只买你真正需要的家具和装饰，每一样都选质量最好、你最喜欢的。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "空间心理学研究",
                    researcher = "Journal of Environmental Psychology",
                    year = 2022,
                    summary = "整洁、有序的居住环境，能显著提升幸福感和工作效率，降低焦虑和抑郁的风险。",
                    fullText = "研究表明，整洁、有序的居住环境能显著提升幸福感和工作效率，降低焦虑和抑郁的风险。"
                ),
                AcademicReference(
                    theoryName = "近藤麻理惠收纳哲学",
                    researcher = "Marie Kondo",
                    year = 2014,
                    summary = "真正的收纳，不是把东西藏起来，是只留下你真正喜欢和需要的东西。",
                    fullText = "近藤麻理惠的「怦然心动整理法」认为，真正的收纳不是把东西藏起来，而是只留下那些让你怦然心动的东西。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "housing",
                    conditionType = "decoration_style",
                    threshold = 1.0,
                    operator = "=="
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 59. 精益求精（迪士尼设计专项）
        IdiomData(
            idiomId = 59,
            name = "精益求精",
            traditionalMeaning = "好了还要求更好。",
            awakeningMeaning = "好的设计，差的不是钱，是态度。迪士尼愿意为了土壤除菌花几百万，愿意为了一个细节打磨几年。而我们很多项目，只想快速赚钱，粗制滥造，最后变成垃圾。",
            dataTemplate = "迪士尼的复游率超过 {disney_rate}%，而我们大多数主题公园的复游率不到 {our_rate}%。这就是细节的力量。",
            actionSuggestion = "做任何事情，都要追求极致。不要差不多就行，要做到最好。细节决定成败，态度决定一切。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "体验经济理论",
                    researcher = "B. Joseph Pine & James H. Gilmore",
                    year = 1998,
                    summary = "体验经济是继农业经济、工业经济、服务经济之后的第四种经济形态。消费者愿意为了独特的、沉浸式的体验支付溢价。",
                    fullText = "体验经济理论指出，消费者愿意为独特的、沉浸式的体验支付溢价，这是未来经济的发展方向。"
                ),
                AcademicReference(
                    theoryName = "迪士尼体验设计原则",
                    researcher = "Walt Disney Imagineering",
                    year = 2020,
                    summary = "我们不是在建造主题公园，我们是在创造一个世界。在这个世界里，所有的一切都必须是真实的。",
                    fullText = "迪士尼的体验设计原则强调：在创造的世界里，所有的一切都必须是真实的，包括土壤、路面、声音、气味等所有细节。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "attention_to_detail",
                    threshold = 80.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 60. 华而不实（欧式豪华装修专项）
        IdiomData(
            idiomId = 60,
            name = "华而不实",
            traditionalMeaning = "只开花不结果，比喻外表好看，但没有实际内容。",
            awakeningMeaning = "你花几十万装的欧式豪华房子，看起来金碧辉煌，实际上空间利用率只有40%，住起来一点都不方便。那些吊顶、水晶灯、电视背景墙，除了好看没有任何用处。",
            dataTemplate = "你的 {style} 装修，空间利用率只有 {utilization}%，价值密度只有 {density}，每年维护成本还要 {maintenance} 元。",
            actionSuggestion = "装修不要追求表面的奢华，要追求实用和舒适。去掉那些没用的装饰，把钱花在真正能提升生活品质的地方。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "功能主义设计理论",
                    researcher = "Louis Sullivan",
                    year = 1896,
                    summary = "形式追随功能（Form follows function）。",
                    fullText = "路易斯·沙利文提出的「形式追随功能」是现代建筑设计的基本原则，强调设计应该首先满足功能需求。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "housing",
                    conditionType = "decoration_style",
                    threshold = 5.0,
                    operator = "=="
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 61. 哗众取宠（网红ins风专项）
        IdiomData(
            idiomId = 61,
            name = "哗众取宠",
            traditionalMeaning = "用浮夸的言行迎合众人，以博取好感或拥护。",
            awakeningMeaning = "你花几万块钱买的网红装饰、泡泡玛特、ins风家具，三个月就看腻了。这些东西不是为了用，是为了拍照发朋友圈。你不是在为自己装修，是在为别人的点赞装修。",
            dataTemplate = "你买的 {item}，花了 {cost} 元，只用了 {days} 天就看腻了，平均每天成本 {daily_cost} 元。",
            actionSuggestion = "装修是为了自己住得舒服，不是为了给别人看。不要跟风买网红产品，买那些真正实用、你真正喜欢、能长久使用的东西。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "消费社会理论",
                    researcher = "Jean Baudrillard",
                    year = 1970,
                    summary = "在消费社会中，物品不再仅仅是使用价值的载体，更是符号价值的载体。",
                    fullText = "鲍德里亚认为，消费社会中的物品变成了符号，人们消费的不是物品本身，而是物品所代表的身份和地位。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "housing",
                    conditionType = "decoration_style",
                    threshold = 6.0,
                    operator = "=="
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 62. 堆积如山（杂物堆积专项）
        IdiomData(
            idiomId = 62,
            name = "堆积如山",
            traditionalMeaning = "东西堆积得像小山一样，形容数量非常多。",
            awakeningMeaning = "你的房子里堆满了没用的东西，连下脚的地方都没有。这些东西占用了你的空间，消耗了你的精力，降低了你的生活品质。",
            dataTemplate = "你有 {count} 件闲置物品，占用了 {space} 平方米的空间，每年清理时间 {hours} 小时，相当于 {salary} 元。",
            actionSuggestion = "立即开始断舍离！把超过一年没用到的东西都扔掉或捐赠。记住：你拥有的东西，也在拥有你。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "极简主义生活方式",
                    researcher = "Joshua Becker",
                    year = 2011,
                    summary = "拥有更少的东西，可以让你更专注于生命中真正重要的事情。",
                    fullText = "极简主义不是一无所有，而是只拥有那些真正重要、真正有用的东西，从而获得更多的自由和幸福。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "housing",
                    conditionType = "idle_items",
                    threshold = 50.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 63. 未雨绸缪（老龄化准备专项）
        IdiomData(
            idiomId = 63,
            name = "未雨绸缪",
            traditionalMeaning = "趁着天没下雨，先修缮房屋门窗。比喻事先做好准备工作，防患于未然。",
            awakeningMeaning = "你今年 {age} 岁了，距离60岁还有 {years_left} 年。现在开始为你的晚年做准备还来得及。你今天的每一个选择，都在决定你80岁的时候能不能活得有尊严。",
            dataTemplate = "你现在 {age} 岁，每月储蓄 {savings} 元，按照这个速度，到60岁你将有 {total_savings} 元养老储备。",
            actionSuggestion = "从现在开始，每年存下收入的20%作为养老储备。同时，开始调整你的生活方式：选择适老化的装修，培养能陪伴你一辈子的爱好，保持健康的生活习惯。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "生命周期消费理论",
                    researcher = "Franco Modigliani",
                    year = 1954,
                    summary = "人们会根据一生的收入预期来安排消费和储蓄，在工作时期储蓄，在退休时期消费。",
                    fullText = "莫迪利安尼的生命周期消费理论指出，理性的消费者会根据一生的收入预期来安排消费和储蓄，以实现一生效用的最大化。"
                ),
                AcademicReference(
                    theoryName = "老龄化社会设计原则",
                    researcher = "World Health Organization",
                    year = 2021,
                    summary = "通用设计原则：设计应该对所有人都友好，无论年龄、能力如何。",
                    fullText = "WHO的通用设计原则强调，好的设计应该对所有人都友好，无论是年轻人还是老年人，健全人还是残疾人。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "housing",
                    conditionType = "age",
                    threshold = 40.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 64. 积重难返（晚年陷阱专项）
        IdiomData(
            idiomId = 64,
            name = "积重难返",
            traditionalMeaning = "长期形成的不良习惯或问题难以改变。",
            awakeningMeaning = "你现在沉迷的短视频、网游、直播打赏，正在一点点掏空你的时间和金钱。当你老了，动不了了，这些东西什么都给不了你，只会让你陷入无尽的空虚。",
            dataTemplate = "你每天花 {hours} 小时刷短视频，一年就是 {annual_hours} 小时，相当于 {work_days} 个工作日。这些时间，本来可以用来学习、锻炼、陪伴家人，或者为你的晚年做准备。",
            actionSuggestion = "立即开始减少短视频和网游时间，每天不超过1小时。用省下来的时间，培养一个能陪伴你一辈子的爱好，比如读书、画画、养花。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "数字成瘾研究",
                    researcher = "Journal of Behavioral Addictions",
                    year = 2022,
                    summary = "过度使用社交媒体和网络游戏，会导致注意力下降、记忆力衰退、社交能力减弱，严重影响晚年生活质量。",
                    fullText = "研究表明，过度使用社交媒体和网络游戏会导致大脑结构变化，影响认知功能，对老年人的影响尤为严重。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "short_video_hours",
                    threshold = 3.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 65. 安享晚年（积极养老专项）
        IdiomData(
            idiomId = 65,
            name = "安享晚年",
            traditionalMeaning = "平安舒适地度过晚年。",
            awakeningMeaning = "真正的成功，不是30岁的时候赚了多少钱，是80岁的时候还能身体健康、精神充实、生活有尊严。你现在选择的健康生活方式、适老化的居住环境、丰富的精神生活，都会让你拥有一个幸福的晚年。",
            dataTemplate = "你现在的健康指数是 {health}，精神指数是 {mental}，养老储备是 {savings} 元。按照这个趋势，你80岁的时候，健康指数将达到 {future_health}，可以安享一个幸福的晚年。",
            actionSuggestion = "保持健康的饮食习惯，每天锻炼30分钟，每周读一本书，每月和朋友聚会一次。这些小小的习惯，会让你在晚年收获巨大的回报。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "积极老龄化理论",
                    researcher = "World Health Organization",
                    year = 2002,
                    summary = "积极老龄化是指在老年时保持身体健康、心理活跃、社会参与的状态。",
                    fullText = "WHO的积极老龄化理论强调，老龄化不仅是生理过程，更是社会和心理过程。积极参与社会、保持学习和活动，能显著提高晚年生活质量。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "reading_hours",
                    threshold = 50.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.UNCOMMON
        ),

        // 66. 金玉其外（晚年负担专项）
        IdiomData(
            idiomId = 66,
            name = "金玉其外",
            traditionalMeaning = "外表华丽，内里空虚。",
            awakeningMeaning = "你花50万装的欧式豪华吊顶、水晶灯、电视背景墙，30年后你根本够不着擦，它们会变成你沉重的负担。真正好的装修，是从3岁用到80岁的设计，而不是只好看不实用的摆设。",
            dataTemplate = "你的 {style} 装修，现在价值密度是 {current_density}，但到你60岁的时候会降到 {future_density}，80岁的时候会变成 {old_density}。它不仅不会给你带来价值，反而会伤害你。",
            actionSuggestion = "选择适老化的装修风格：平地、圆角、扶手、防滑地面、可调节高度的台面。这些设计现在看起来普通，但会在你老了之后救你的命。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "通用设计理论",
                    researcher = "Ronald L. Mace",
                    year = 1997,
                    summary = "通用设计是指设计产品和环境，使其对尽可能多的人有用，无需特殊适配或改造。",
                    fullText = "通用设计的核心原则包括：公平使用、灵活使用、简单直观、信息清晰、容错性、低体力消耗、尺寸和空间适宜。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "housing",
                    conditionType = "decoration_style",
                    threshold = 5.0,
                    operator = "=="
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 过犹不及（三伏天晒背专项）
        IdiomData(
            idiomId = 67,
            name = "过犹不及",
            traditionalMeaning = "事情做得过头，就跟做得不够一样，都是不合适的。",
            awakeningMeaning = "适量晒太阳有益健康，但三伏天暴晒几个小时就是自残。你以为你在排湿气，其实你在给自己增加皮肤癌和中暑的风险。",
            dataTemplate = "每天晒背1小时，一年增加10%的皮肤癌风险，健康成本1000小时。这相当于你提前把自己未来的生命时间，浪费在了毫无意义的事情上。",
            actionSuggestion = "每天晒太阳不要超过15分钟，最好在早上9点前或下午4点后。不要在中午暴晒，不要穿太少，注意防晒。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "世界卫生组织紫外线致癌报告",
                    researcher = "WHO",
                    year = 2022,
                    summary = "紫外线是1类致癌物，长期暴晒会导致皮肤癌。",
                    fullText = "世界卫生组织明确将紫外线列为1类致癌物，长期暴露在紫外线下会导致皮肤老化、色斑、皱纹，甚至皮肤癌。"
                ),
                AcademicReference(
                    theoryName = "中国疾控中心中暑报告",
                    researcher = "中国疾控中心",
                    year = 2023,
                    summary = "每年夏季有超过1万人因中暑就医，其中重症中暑死亡率高达50%。",
                    fullText = "当体温超过40℃时，人体的体温调节系统就会崩溃，导致多器官功能衰竭，死亡率高达50%以上。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "sunExposureHours",
                    threshold = 1.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 食古不化（二十四节气迷信专项）
        IdiomData(
            idiomId = 68,
            name = "食古不化",
            traditionalMeaning = "指对所学的古代知识理解得不深不透，不善于按现在的情况来运用。",
            awakeningMeaning = "二十四节气是古代的农业历法，不是现代的生活指南。老祖宗发明它是为了种庄稼，不是为了告诉你哪天不能洗头。",
            dataTemplate = "你为了遵守各种节气禁忌，每天花1小时研究，一年就是365小时。这些时间，你本来可以用来学习、健身、陪家人。",
            actionSuggestion = "把二十四节气当成传统文化了解就好，不要当真。想什么时候洗头就什么时候洗头，想什么时候洗澡就什么时候洗澡，想吃什么就吃什么。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "中国科学院紫金山天文台节气说明",
                    researcher = "中国科学院紫金山天文台",
                    year = 2022,
                    summary = "二十四节气是根据太阳在黄道上的位置划分的，是一种天文历法，没有任何神秘力量。",
                    fullText = "二十四节气是古人通过观察太阳的运行规律，总结出来的气候变化和农业生产的关系。它是一种科学的历法，但不是什么神秘的力量。"
                ),
                AcademicReference(
                    theoryName = "现代医学与节气关系研究",
                    researcher = "Journal of Modern Medicine",
                    year = 2021,
                    summary = "没有任何证据证明，节气当天的饮食或行为会对健康产生任何影响。",
                    fullText = "现代医学研究表明，人体健康与生活习惯、饮食结构、运动等因素有关，与日期没有任何直接关系。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "solarTermTaboos",
                    threshold = 3.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 牵强附会（星象占卜专项）
        IdiomData(
            idiomId = 69,
            name = "牵强附会",
            traditionalMeaning = "把本来没有关系的事物，硬拉在一起，说它们有关系。",
            awakeningMeaning = "星星和你的命运没有任何关系。你口袋里的手机对你的引力，都比月球大3倍。如果星星能影响你的命运，那你的手机早就改变你的人生了。",
            dataTemplate = "每天花1小时看星象，一年浪费1530小时生命，价值密度仅0.0005。这些时间，你本来可以用来改变自己的命运。",
            actionSuggestion = "把所有占星APP都卸载了。你的命运掌握在你自己手里，不是掌握在星星手里。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "美国天文学会占星学声明",
                    researcher = "AAS",
                    year = 2022,
                    summary = "占星学是伪科学，没有任何科学证据证明天体的运行会影响人的性格和命运。",
                    fullText = "美国天文学会明确表示，占星学是伪科学，其原理与现代天文学和物理学完全矛盾。"
                ),
                AcademicReference(
                    theoryName = "星座与性格双生子研究",
                    researcher = "Nature",
                    year = 2003,
                    summary = "出生时间和星座对人的性格没有任何显著影响。",
                    fullText = "对2000多对双胞胎的研究表明，出生时间和星座与性格、职业、婚姻等没有任何统计上显著的关联。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "astrologyHours",
                    threshold = 0.5,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 上当受骗（伪科学消费专项）
        IdiomData(
            idiomId = 70,
            name = "上当受骗",
            traditionalMeaning = "被人欺骗，蒙受损失。",
            awakeningMeaning = "你每年花在伪科学上的钱，足够你去两次健身房、买10本好书、学一门新技能。而你得到的，只是焦虑、恐惧和虚假的希望。",
            dataTemplate = "你每年在伪科学上花费 {money} 元，浪费 {hours} 小时，相当于 {salary} 小时的生命时间。这些钱和时间，本来可以用来真正改变你的生活。",
            actionSuggestion = "把花在伪科学上的钱，花在真正能提升自己的地方：健身、读书、学习、旅行。把时间用在行动上，而不是用在恐惧和迷信上。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "伪科学经济成本研究",
                    researcher = "Journal of Economic Psychology",
                    year = 2023,
                    summary = "美国人每年在伪科学上花费超过300亿美元，占GDP的0.15%。",
                    fullText = "研究表明，伪科学不仅浪费金钱，还会导致人们放弃真正有效的医疗和解决方案，造成严重的健康和经济损失。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "pseudoscienceSpending",
                    threshold = 500.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // ===== 价值观批判：集体主义陷阱与个体觉醒 =====

        // 71. 舍己为人（被异化的集体主义）
        IdiomData(
            idiomId = 71,
            name = "舍己为人",
            traditionalMeaning = "牺牲自己的利益去帮助别人，传统美德。",
            awakeningMeaning = "真正的集体利益是让每个个体都过得更好，而不是让个体牺牲成全少数人。当老板说'公司是个大家庭，要讲奉献'时，他正在用集体主义的名义剥削你——他自己买了第三套别墅，而你加班到凌晨。",
            dataTemplate = "你为了所谓的'集体利益'，已经牺牲了 {sacrifice_hours} 小时的个人时间，错过了 {missed_events} 次家庭聚会，放弃了 {abandoned_goals} 个个人目标。而那些让你牺牲的人，获得了 {their_gain} 的收益。",
            actionSuggestion = "问自己三个问题：1.这个集体真的让每个成员都更好了吗？2.我的牺牲是互利共赢还是单方面付出？3.如果我不牺牲，集体会怎样？",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "集体主义的异化",
                    researcher = " Hannah Arendt",
                    year = 1951,
                    summary = "极权主义通过集体主义消解个体，让个体成为集体的工具而非目的。",
                    fullText = "真正的集体应该服务于个体，而不是让个体成为集体的工具。当集体主义被异化，它就变成了少数人剥削多数人的武器。"
                ),
                AcademicReference(
                    theoryName = "社会契约论",
                    researcher = "Jean-Jacques Rousseau",
                    year = 1762,
                    summary = "集体存在的目的是保护每个个体的权利和自由。",
                    fullText = "社会契约的本质是：个体让渡部分权利给集体，是为了获得更好的保护。如果集体不能保护个体，个体有权退出契约。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "collectiveSacrificeHours",
                    threshold = 100.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.RARE
        ),

        // 72. 吃亏是福（剥削者的洗脑口号）
        IdiomData(
            idiomId = 72,
            name = "吃亏是福",
            traditionalMeaning = "吃亏是好事，会有福报。",
            awakeningMeaning = "这是匮乏时代留下的生存策略，但在过剩时代，它变成了剥削者的洗脑工具。领导说'年轻人要多吃苦'，然后把你的功劳据为己有；亲戚说'一家人不要分那么清'，然后躺着花你的钱。吃亏从来不是福，吃亏只是吃亏。",
            dataTemplate = "你因为'吃亏是福'的观念，已经损失了 {money_lost} 元，浪费了 {hours_lost} 小时，错过了 {opportunities_lost} 个机会。那些让你吃亏的人，没有给你任何福报，只有更多的'吃亏'。",
            actionSuggestion = "记住：真正的福报来自你的努力和选择，不是来自被动吃亏。下次有人说'吃亏是福'，问他：'那你愿意吃亏吗？'",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "权力不对等下的道德绑架",
                    researcher = "Journal of Social Psychology",
                    year = 2018,
                    summary = "在权力不对等的关系中，强势方常用道德口号让弱势方接受不公平。",
                    fullText = "研究表明，'吃亏是福'等道德口号，在权力不对等的关系中，是强势方让弱势方接受不公平安排的有效工具。接受者不仅损失利益，还会产生自我否定的心理伤害。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "passiveLossCount",
                    threshold = 5.0,
                    operator = ">="
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 73. 大公无私（被偷换的概念）
        IdiomData(
            idiomId = 73,
            name = "大公无私",
            traditionalMeaning = "完全为了公共利益，不考虑个人利益。",
            awakeningMeaning = "这个概念已经被偷换了：真正的'公'是让每个个体都过得更好，被异化的'公'是让个体牺牲成全少数人。商家说'支持我们的产品就是支持集体'，然后把产品卖得比别家还贵——这不是大公无私，这是打着集体旗号的商业剥削。",
            dataTemplate = "你因为'大公无私'的观念，在 {situations} 个场景中牺牲了自己的利益，总共损失了 {total_loss}。而那些受益的'集体'，并没有让你过得更好。",
            actionSuggestion = "区分真正的公和被异化的公：真正的公是互利共赢，被异化的公是单方面牺牲。如果一个集体不能让你过得更好，它就没有资格要求你无私。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "概念偷换与意识形态操控",
                    researcher = "George Orwell",
                    year = 1945,
                    summary = "通过重新定义概念，可以让人们接受原本不会接受的东西。",
                    fullText = "《动物农场》展示了概念如何被偷换：'所有动物平等'变成了'所有动物平等，但有些动物更平等'。集体主义同样可以被偷换，变成少数人的工具。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "falseCollectiveCount",
                    threshold = 3.0,
                    operator = ">="
                )
            ),
            rarity = IdiomRarity.RARE
        ),

        // 74. 先人后己（顺序错误的价值观）
        IdiomData(
            idiomId = 74,
            name = "先人后己",
            traditionalMeaning = "先考虑别人，后考虑自己，传统美德。",
            awakeningMeaning = "正确的顺序是：先对自己负责，再对家庭负责，再对社区负责，再对国家负责。就像飞机上的安全提示：先戴好自己的氧气面罩，再去帮助别人。如果你自己都活不好，你怎么帮助别人？",
            dataTemplate = "你因为'先人后己'，在 {self_neglect_count} 次选择中忽略了自己的需求，导致 {health_impact} 的健康问题，{relationship_impact} 的关系问题。一个连自己都不爱的人，不可能真正爱别人。",
            actionSuggestion = "建立正确的责任顺序：自己→家庭→社区→国家→人类。这不是自私，这是清醒。只有先把自己活好，才能真正帮助别人。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "自我关怀理论",
                    researcher = "Kristin Neff",
                    year = 2003,
                    summary = "对自己关怀和尊重，是心理健康和有效帮助他人的基础。",
                    fullText = "研究表明，自我关怀水平高的人，不仅心理健康状况更好，帮助他人的能力也更强。因为他们有足够的心理资源去支持别人。"
                ),
                AcademicReference(
                    theoryName = "氧气面罩原则",
                    researcher = "航空安全研究",
                    year = 2020,
                    summary = "在紧急情况下，先保护好自己才能有效帮助他人。",
                    fullText = "航空安全的核心原则：在紧急情况下，成年人必须先戴好自己的氧气面罩，再去帮助儿童或他人。因为如果成年人自己失去意识，就无法帮助任何人。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "selfNeglectScore",
                    threshold = 60.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 75. 随遇而安（被动接受的人生观）
        IdiomData(
            idiomId = 75,
            name = "随遇而安",
            traditionalMeaning = "顺应环境，安于现状，心态平和。",
            awakeningMeaning = "在匮乏时代，这是生存智慧——你无法改变环境，只能适应。但在过剩时代，你有能力改变环境、选择环境。随遇而安变成了放弃选择权的借口，让你在糟糕的环境中麻木地活着。",
            dataTemplate = "你因为'随遇而安'，在 {bad_situations} 个糟糕的环境中被动接受，错过了 {missed_choices} 次改变的机会，浪费了 {wasted_years} 年的生命时间。",
            actionSuggestion = "问自己：这个环境是我想要的吗？如果不是，我有能力离开吗？如果有能力，为什么不离开？随遇而安的前提是——你已经尽力选择了。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "习得性无助",
                    researcher = "Martin Seligman",
                    year = 1967,
                    summary = "当人们反复经历无法控制的负面事件，会学会被动接受。",
                    fullText = "习得性无助让人们在有能力改变的情况下，也选择被动接受。'随遇而安'可能是一种习得性无助的表现，而不是真正的智慧。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "passiveAcceptCount",
                    threshold = 10.0,
                    operator = ">="
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 76. 独善其身（被污名化的个体觉醒）
        IdiomData(
            idiomId = 76,
            name = "独善其身",
            traditionalMeaning = "只管自己好，不管别人，被认为是自私。",
            awakeningMeaning = "这个词被污名化了。在过剩时代，独善其身不是自私，是清醒。一个清醒的、独立的、对自己负责的个体，才是最好的集体成员。因为他们不是被迫服从，而是自愿贡献；他们不会被虚假口号欺骗，能判断什么是真正的集体利益。",
            dataTemplate = "你因为害怕被说'独善其身'，在 {forced_contributions} 次场合中被迫贡献，损失了 {total_loss}。而那些批评你的人，自己并没有为集体做多少贡献。",
            actionSuggestion = "记住：清醒的个体组成的集体，才是最健康的集体。独善其身不是逃避集体，而是为了创造一个更好的集体。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "个体觉醒与集体健康",
                    researcher = "Emile Durkheim",
                    year = 1893,
                    summary = "由清醒个体组成的集体，比由被动个体组成的集体更有生命力。",
                    fullText = "社会学研究表明，由清醒、独立、有尊严的个体组成的社会，比由被动、麻木、服从的个体组成的社会更稳定、更有创造力、更能应对危机。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "forcedContributionCount",
                    threshold = 5.0,
                    operator = ">="
                )
            ),
            rarity = IdiomRarity.EPIC
        ),

        // 77. 安分守己（被驯服的人生）
        IdiomData(
            idiomId = 77,
            name = "安分守己",
            traditionalMeaning = "守好自己的本分，不越界，不逾矩。",
            awakeningMeaning = "这是农业时代的生存法则——你必须依附于宗族、村落、国家才能活下去。但在过剩时代，你不需要依附任何集体。安分守己变成了自我驯服，让你放弃探索、放弃选择、放弃成为更好的自己。",
            dataTemplate = "你因为'安分守己'，放弃了 {abandoned_dreams} 个梦想，错过了 {missed_opportunities} 次机会，在 {years_wasted} 年里活成了别人期待的样子，而不是自己想要的样子。",
            actionSuggestion = "问自己：我的'分'是谁定的？这个'分'让我过得更好了吗？如果不是，为什么我要守？过剩时代的'分'，应该由你自己来定。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "社会角色理论",
                    researcher = "Erving Goffman",
                    year = 1959,
                    summary = "人们在社会中扮演各种角色，但过度认同角色会失去自我。",
                    fullText = "人们在社会中扮演各种角色，但当人们过度认同这些角色，就会失去真实的自我。'安分守己'可能导致人们完全被社会角色定义，失去个体性。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "roleConformityScore",
                    threshold = 80.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 78. 顾全大局（被滥用的集体借口）
        IdiomData(
            idiomId = 78,
            name = "顾全大局",
            traditionalMeaning = "为了整体利益，牺牲局部利益。",
            awakeningMeaning = "这个词被滥用了。真正的'大局'是让每个个体都过得更好，被异化的'大局'是让个体牺牲成全少数人。当有人说'你要顾全大局'时，问问他：这个大局让每个成员都更好了吗？还是只有少数人受益？",
            dataTemplate = "你因为'顾全大局'，在 {sacrifice_count} 次决策中牺牲了自己的利益，而那些受益的人获得了 {their_benefit}。真正的全局利益应该是互利共赢，而不是单方面牺牲。",
            actionSuggestion = "记住：如果只有你一个人在牺牲，那这不是大局，这是剥削。真正的全局利益，是'我为人人，人人为我'。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "博弈论与集体利益",
                    researcher = "John Nash",
                    year = 1950,
                    summary = "真正的集体最优解，应该让每个参与者都获益。",
                    fullText = "纳什均衡理论表明，真正的集体最优解（帕累托最优），应该让每个参与者都获益或至少不受损。如果只有部分人获益而其他人受损，这不是集体最优，这是权力不对等的结果。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "false大局Count",
                    threshold = 3.0,
                    operator = ">="
                )
            ),
            rarity = IdiomRarity.RARE
        ),

        // 79. 自私自利（被污名化的自我负责）
        IdiomData(
            idiomId = 79,
            name = "自私自利",
            traditionalMeaning = "只考虑自己的利益，不顾别人。",
            awakeningMeaning = "这个词被污名化了。对自己负责不是自私，是清醒。你的生命时间是你唯一真正拥有的东西，珍惜它、用好它，是对生命最基本的尊重。真正的自私是要求别人牺牲来满足你，而不是拒绝被牺牲。",
            dataTemplate = "你因为害怕被说'自私自利'，在 {forced_sacrifices} 次场合中牺牲了自己的利益，总共损失了 {total_loss}。而那些批评你的人，正在自私地要求你牺牲来满足他们。",
            actionSuggestion = "区分真正的自私和被污名化的自私：拒绝被牺牲不是自私，要求别人牺牲才是自私。保护自己的生命时间，是对生命最基本的尊重。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "个体权利理论",
                    researcher = "John Locke",
                    year = 1689,
                    summary = "每个个体都有保护自己生命、自由、财产的基本权利。",
                    fullText = "洛克认为，每个个体都有保护自己生命、自由、财产的基本权利，这是自然权利，不需要任何集体批准。社会存在的目的，是更好地保护这些权利，而不是剥夺它们。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "selfishLabelCount",
                    threshold = 5.0,
                    operator = ">="
                )
            ),
            rarity = IdiomRarity.EPIC
        ),

        // 80. 个人主义（被误解的觉醒）
        IdiomData(
            idiomId = 80,
            name = "个人主义",
            traditionalMeaning = "只顾个人利益，不顾集体利益，被认为是负面。",
            awakeningMeaning = "个人主义不是自私自利，是个体觉醒。一个真正好的社会，不要求个体为了集体牺牲自己；它努力让每个个体都过上有尊严的生活；它保护每个个体的权利和自由；它让每个个体的努力都能得到相应的回报。这样的社会，需要每一个清醒的个体去创造。",
            dataTemplate = "你因为'个人主义'被污名化，在 {collective_pressure} 次场合中被迫服从集体，牺牲了 {individual_loss}。而真正的个人主义，是先把自己活好，再为集体做贡献。",
            actionSuggestion = "记住：个人主义不是逃避集体，而是为了创造一个更好的集体。清醒的个体，才是最好的集体成员。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "个人主义与社会进步",
                    researcher = "Alexis de Tocqueville",
                    year = 1835,
                    summary = "健康的个人主义是社会进步的动力，而不是威胁。",
                    fullText = "托克维尔在《论美国的民主》中指出，健康的个人主义（他称之为'正确的自我理解'）是社会进步的动力。它让人们既对自己负责，又愿意为公共利益做贡献。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "collectivePressureScore",
                    threshold = 70.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.LEGENDARY
        ),

        // ===== 赛博奶嘴批判：Doro与虚拟偶像 =====

        // 81. 画饼充饥（Doro：悲剧喂养的赛博宠物）
        IdiomData(
            idiomId = 81,
            name = "画饼充饥",
            traditionalMeaning = "画个饼来解饿，比喻用空想来安慰自己。",
            awakeningMeaning = "你心疼的不是Doro，是那个在现实生活中被欺负、被忽视、被抛弃的你自己。你在虚拟的悲剧里获得的感动，都是虚假的。Doro的所有悲剧结局，都是网友自己编出来的。你越编越惨，越惨越心疼，越心疼越沉迷。",
            dataTemplate = "你每天花 {daily_hours} 小时刷Doro，每年浪费 {yearly_hours} 小时生命，购买周边花费 {merchandise_cost} 元。Doro永远无法被拯救，就像你无法通过拯救虚拟形象来拯救自己一样。",
            actionSuggestion = "把心疼Doro的时间，花在自己身上。去运动，去读书，去和朋友聊天。真正能拯救你的，不是Doro，是你自己。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "投射效应",
                    researcher = "Sigmund Freud",
                    year = 1896,
                    summary = "人们会把自己的情感、欲望、特质，投射到别人或别的事物身上。",
                    fullText = "投射效应解释了为什么人们会如此沉迷于虚拟形象。你把所有的委屈、痛苦、不甘都投射到了Doro身上，Doro成为了你情感的容器。"
                ),
                AcademicReference(
                    theoryName = "情感代偿理论",
                    researcher = "Erich Fromm",
                    year = 1941,
                    summary = "当人们在现实中无法获得情感满足时，会转向虚拟世界寻求代偿。",
                    fullText = "现代社会的疏离感让人们转向虚拟世界寻求情感慰藉。Doro等虚拟形象成为了完美的情感代偿对象——它们永远不会离开你，永远不会伤害你，永远可爱治愈。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "doroHoursPerDay",
                    threshold = 2.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.RARE
        ),

        // 82. 千金买笑（塔菲喵：撒娇收割的韭菜）
        IdiomData(
            idiomId = 82,
            name = "千金买笑",
            traditionalMeaning = "花费千金，买得一笑，指不惜重价，博取美人欢心。",
            awakeningMeaning = "你花几千块钱打赏塔菲喵，换来的只是几句虚假的'谢谢喵'。她下了直播，根本不记得你是谁。你以为你在谈恋爱，其实你在被PUA。你以为你在支持她，其实你在养着一个你永远也得不到的人。",
            dataTemplate = "你每天花 {daily_hours} 小时看塔菲喵，每月打赏 {monthly_tips} 元，每年总薪俸成本 {yearly_cost} 小时（相当于 {work_years} 年的工资）。你买不到真正的爱，只能买到虚假的表演。",
            actionSuggestion = "立即取消所有自动打赏。把钱和时间，花在现实中真正爱你的人身上。去谈一场真实的恋爱，去交一个真正的朋友。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "准社会交往理论",
                    researcher = "Donald Horton & R. Richard Wohl",
                    year = 1956,
                    summary = "观众会对媒体人物产生单向的、想象的情感关系。",
                    fullText = "准社会交往(Parasocial Relationship)描述了观众对虚拟偶像产生的虚假亲密感。粉丝误以为自己与主播是真正的朋友/恋人，实际上这完全是一种单向的、想象的情感连接。"
                ),
                AcademicReference(
                    theoryName = "虚拟偶像经济研究",
                    researcher = "Journal of Cultural Economy",
                    year = 2023,
                    summary = "虚拟偶像的核心商业模式是情感劳动，通过制造虚假的情感连接收割粉丝。",
                    fullText = "研究表明，虚拟偶像经济本质上是一种情感剥削。主播通过精心设计的撒娇、卖萌、感谢话术，制造虚假的亲密感，让粉丝产生强烈的情感依赖，进而不断打赏。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "tafiHoursPerDay",
                    threshold = 3.0,
                    operator = ">"
                ),
                TriggerCondition(
                    module = "mental",
                    conditionType = "monthlyTips",
                    threshold = 100.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.EPIC
        ),

        // 83. 玩物丧志（赛博奶嘴：精神绝育）
        IdiomData(
            idiomId = 83,
            name = "玩物丧志",
            traditionalMeaning = "沉迷于玩赏某物，消磨了积极进取的志气。",
            awakeningMeaning = "赛博奶嘴最可怕的地方不是浪费时间和金钱，是阉割了你在现实中获得幸福的能力。当你习惯了从虚拟形象获得零门槛的快乐，你就再也无法忍受现实生活的复杂和困难了。你会失去爱一个人的能力、交朋友的能力、工作的能力、面对现实的能力。",
            dataTemplate = "你同时沉迷 {virtual_count} 个虚拟形象，每天花费 {total_hours} 小时在虚拟世界。你已经 {months_without_real} 个月没有和朋友见面，{years_without_dating} 年没有谈恋爱。现实对你来说，已经变得陌生和可怕。",
            actionSuggestion = "设定每日虚拟内容使用上限（不超过1小时）。每周强制安排至少一次线下社交活动。开始培养一个现实中的爱好，从简单的开始。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "行为成瘾理论",
                    researcher = "Keith Humphreys",
                    year = 2020,
                    summary = "行为成瘾与物质成瘾有相似的神经机制，都涉及多巴胺系统的异常激活。",
                    fullText = "虚拟偶像消费和赌博成瘾有相似的神经机制——间歇性强化（不确定的奖励）会让大脑产生强烈的成瘾性。每次打赏后的感谢、每次互动后的可爱反应，都在强化你的成瘾行为。"
                ),
                AcademicReference(
                    theoryName = "现实感丧失研究",
                    researcher = "Sherry Turkle",
                    year = 2011,
                    summary = "过度依赖虚拟社交会导致现实社交能力的退化。",
                    fullText = "《独自打保龄》指出：当我们习惯于虚拟世界的即时反馈和零门槛互动，真实世界的人际关系会变得难以忍受。因为现实关系需要耐心、需要承担风险、需要接受不确定性。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "virtualIdolCount",
                    threshold = 3.0,
                    operator = ">="
                ),
                TriggerCondition(
                    module = "mental",
                    conditionType = "virtualHoursPerDay",
                    threshold = 4.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.LEGENDARY
        ),

        // 84. 缘木求鱼（虚拟偶像的本质）
        IdiomData(
            idiomId = 84,
            name = "缘木求鱼",
            traditionalMeaning = "爬到树上去找鱼，比喻方法不对，根本达不到目的。",
            awakeningMeaning = "你想通过虚拟偶像获得真实的情感满足，就像缘木求鱼一样。虚拟形象永远无法给你真正的爱、真正的陪伴、真正的连接。你投入的越多，失望就越大；你沉迷的越深，现实感就丧失得越多。",
            dataTemplate = "你已经花了 {total_money} 元，{total_hours} 小时在虚拟世界上。但你的孤独感增加了 {loneliness_increase}%，现实社交能力下降了 {social_decrease}%。虚拟世界填补不了现实的空虚。",
            actionSuggestion = "停止在虚拟世界寻找真实的情感连接。把虚拟偶像当成娱乐消遣，而不是情感寄托。回到现实中，建立真实的、有风险、但有回报的人际关系。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "存在主义心理学",
                    researcher = "Irvin Yalom",
                    year = 1980,
                    summary = "真正的存在感来自真实的人际连接，而非虚拟互动。",
                    fullText = "存在主义心理学强调，人类的终极困扰是孤独感和死亡意识。虚拟世界可以暂时缓解孤独感，但无法解决根本的存在问题。只有真实的人际连接，才能带来真正的归属感和意义感。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "virtualLonelinessScore",
                    threshold = 70.0,
                    operator = ">"
                ),
                TriggerCondition(
                    module = "mental",
                    conditionType = "realSocialHours",
                    threshold = 2.0,
                    operator = "<"
                )
            ),
            rarity = IdiomRarity.RARE
        ),

        // 85. 饮鸩止渴（廉价的快乐）
        IdiomData(
            idiomId = 85,
            name = "饮鸩止渴",
            traditionalMeaning = "喝毒酒来解渴，比喻用错误的办法解决眼前的困难，不顾严重的后果。",
            awakeningMeaning = "赛博奶嘴给你的是廉价的、即时满足的快乐，就像毒酒一样。它不能解决你现实中的任何问题，只会让你暂时感觉不到痛苦。而当药效过去之后，痛苦只会变得更加强烈，你需要更多的奶嘴来麻痹自己。",
            dataTemplate = "你每天用 {comfort_hours} 小时的虚拟内容来'治愈'自己。但你的问题数量增加了 {problem_increase}%，解决问题的能力下降了 {ability_decrease}%。你在用虚拟的快乐，逃避现实的痛苦。",
            actionSuggestion = "停止用虚拟内容来逃避现实。直面你的问题和痛苦，寻求真正的解决方案。真正的治愈需要面对痛苦，而不是麻痹痛苦。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "情绪调节理论",
                    researcher = "James Gross",
                    year = 1998,
                    summary = "回避性的情绪调节策略短期内有效，长期来看会加剧情绪问题。",
                    fullText = "使用虚拟内容来逃避负面情绪是一种回避性的情绪调节策略。短期内它确实有效（心情变好），但长期来看会加剧情绪问题，因为它阻止了人们对问题本身的面对和处理。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "comfortUseHours",
                    threshold = 3.0,
                    operator = ">"
                ),
                TriggerCondition(
                    module = "mental",
                    conditionType = "realProblemSolving",
                    threshold = 30.0,
                    operator = "<"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // ===== 独居青年正向引导成语卡片 =====

        // 86. 脚踏实地（替代刷短视频）
        IdiomData(
            idiomId = 86,
            name = "脚踏实地",
            traditionalMeaning = "做事踏实认真，不好高骛远",
            awakeningMeaning = "每天做一件微小但确定的小事，比刷100条短视频更有意义。你的生活不是别人的生活，你的快乐也不需要从别人那里找。",
            dataTemplate = "你今天花了 {daily_video_hours} 小时刷短视频，这些时间可以用来整理 {drawer_count} 个抽屉，或者擦 {table_count} 遍桌子，或者走 {walking_minutes} 分钟路。这些小事会让你的生活一点点变好。",
            actionSuggestion = "今天花10分钟，做一件微小但确定的小事。整理一个抽屉，擦一遍桌子，走10分钟路。做完之后，感受一下那种真实的成就感。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "微小成就理论",
                    researcher = "Karl Weick",
                    year = 1984,
                    summary = "小的成就可以积累成大的改变",
                    fullText = "微小但确定的成功可以建立信心，形成正反馈循环，最终带来巨大的改变。不要小看每天10分钟的努力。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "daily_video_hours",
                    threshold = 2.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 87. 自食其力（替代给主播打赏）
        IdiomData(
            idiomId = 87,
            name = "自食其力",
            traditionalMeaning = "靠自己的劳动生活",
            awakeningMeaning = "把给主播打赏的钱，花在自己身上。培养一个爱好，学一门技能，投资你自己。你的成长比主播的感谢更有价值，你的技能是任何人都拿不走的。",
            dataTemplate = "你这个月花了 {monthly_tips} 元打赏主播，这些钱可以买 {lego_count} 套乐高，或者 {paint_set_count} 套画画工具，或者 {cooking_book_count} 本烹饪书。把这些钱花在自己身上，你会获得更多的快乐。",
            actionSuggestion = "取消所有自动打赏功能。把这个月本打算打赏的钱，买一个你喜欢的东西，或者报一个你感兴趣的课程。投资你自己，永远是最划算的投资。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "自我决定理论",
                    researcher = "Deci & Ryan",
                    year = 2000,
                    summary = "自主、胜任、关联是人类最基本的心理需求",
                    fullText = "当你在培养自己的爱好和技能时，你满足了自主和胜任的需求。这种满足感是虚拟世界永远无法提供的。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "monthly_tips",
                    threshold = 100.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.RARE
        ),

        // 88. 守望相助（替代心疼虚拟小狗）
        IdiomData(
            idiomId = 88,
            name = "守望相助",
            traditionalMeaning = "互相帮助，共同守望",
            awakeningMeaning = "你想要的不是心疼Doro，而是被需要的感觉。这种感觉，虚拟小狗给不了你，真实的生命可以。养一盆绿萝，喂一只流浪猫，你的付出会有真实的回报。",
            dataTemplate = "你今天花了 {doro_hours} 小时心疼Doro，这些时间可以喂 {cat_count} 只流浪猫，或者给 {plant_count} 盆绿萝浇水。它们真的需要你，没有你它们活不下去。",
            actionSuggestion = "明天去超市，买一包猫粮。下楼喂喂流浪猫。或者，去花店，买一盆绿萝，放在你的书桌上。感受一下被需要的感觉，那种感觉是真实的。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "宠物与心理健康",
                    researcher = "Wells",
                    year = 2007,
                    summary = "照顾宠物可以降低压力，提升幸福感",
                    fullText = "研究表明，照顾宠物可以降低血压，减少焦虑，增加社交互动。哪怕只是一盆绿萝，也能让你感受到生命的力量。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "doro_hours",
                    threshold = 2.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.RARE
        ),

        // 89. 删繁就简（独居审美三原则）
        IdiomData(
            idiomId = 89,
            name = "删繁就简",
            traditionalMeaning = "删除繁杂，趋于简明",
            awakeningMeaning = "你不需要100件衣服，不需要50个杯子，不需要30个摆件。你只需要留下10件真正能让你开心、真正有用的东西。少即是多，简单的生活更快乐。",
            dataTemplate = "你家里有 {item_count} 件物品，但你真正喜欢的只有 {favorite_item_count} 件。剩下的东西，只会占用你的空间，消耗你的精力。是时候来一场断舍离了。",
            actionSuggestion = "今天，从你家随便挑10件东西，扔掉或者捐掉。感受一下，家里变得空旷了，你的心情也变得轻松了。然后，你会想要扔掉更多。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "简约生活哲学",
                    researcher = "Joshua Becker",
                    year = 2013,
                    summary = "更少的物品，更多的生活",
                    fullText = "简约生活不是苦行，是更专注于真正重要的事情。当你拥有的越少，你越能感受到自由和轻松。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "clutter_level",
                    threshold = 80.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 90. 自得其乐（独居也是一种选择）
        IdiomData(
            idiomId = 90,
            name = "自得其乐",
            traditionalMeaning = "自己从中得到乐趣",
            awakeningMeaning = "独居不是缺陷，是一种选择。一个人生活，也可以过得很好，很精彩，很有尊严。你不需要别人来拯救你，你自己就是自己的英雄。",
            dataTemplate = "你已经独居了 {solo_living_years} 年，这段时间里，你学会了 {skill_count} 项技能，去了 {travel_count} 个地方，读了 {book_count} 本书。你不需要任何人来定义你的生活。",
            actionSuggestion = "明天，给自己买一份小礼物。不用很贵，但是要让自己开心。然后，对自己说一句：你辛苦了，你真棒。你是自己生活的主人。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "独处与自我成长",
                    researcher = "Burger",
                    year = 1995,
                    summary = "独处可以促进自我反思和个人成长",
                    fullText = "独处不是孤独，是与自己对话的机会。在独处中，你可以更好地认识自己，找到真正的自我。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "solo_living",
                    threshold = 1.0,
                    operator = "=="
                )
            ),
            rarity = IdiomRarity.EPIC
        ),

        // ============================================
        // 时代局限性批判：娱乐形式的过时与精神内核的传承
        // ============================================

        // 91. 玩物丧志（棋牌赌博批判）
        IdiomData(
            idiomId = 91,
            name = "玩物丧志",
            traditionalMeaning = "迷恋于玩赏喜好的事物，而消磨了志气。",
            awakeningMeaning = "在那个没有别的娱乐的年代，打牌是可以理解的。但今天，我们有无数种更好的娱乐方式。打牌不仅浪费时间和金钱，还会让你变得焦虑、浮躁、斤斤计较。每周打一次麻将，一年浪费990小时生命，相当于3个多月的工资。",
            dataTemplate = "你每周打牌 {weekly_card_games} 次，每次 {hours_per_game} 小时，平均输赢 {avg_win_loss} 元。一年时间成本 {yearly_hours} 小时，金钱成本 {yearly_money} 元，精神成本系数 {mental_cost_factor}。总成本相当于 {total_work_days} 个工作日。",
            actionSuggestion = "把打牌的次数减少到每个月一次以下，而且绝对不要赌钱。把省下来的时间和金钱，用在更有意义的事情上——去爬山、去读书、去和朋友真正地聊天。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "赌博成瘾研究",
                    researcher = "American Psychiatric Association",
                    year = 2013,
                    summary = "赌博成瘾是一种精神疾病，会导致财务问题、家庭矛盾、抑郁和自杀。",
                    fullText = "赌博成瘾（Gambling Disorder）被DSM-5归类为物质相关及成瘾性障碍。长期赌博会导致严重的财务问题、家庭矛盾、抑郁、焦虑，甚至自杀倾向。十赌九输，只有开赌场的人能赢钱。"
                ),
                AcademicReference(
                    theoryName = "社交质量研究",
                    researcher = "Robert Putnam",
                    year = 2000,
                    summary = "基于金钱的社交，是最脆弱的社交。",
                    fullText = "基于金钱输赢的社交关系，一旦没有了金钱输赢，感情也就消失了。真正的友谊建立在共同的价值观和真诚的关心，而不是牌桌上的输赢。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "weekly_card_games",
                    threshold = 1.0,
                    operator = ">"
                ),
                TriggerCondition(
                    module = "mental",
                    conditionType = "card_game_with_money",
                    threshold = 1.0,
                    operator = "=="
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 92. 饮鸩止渴（酒桌文化批判）
        IdiomData(
            idiomId = 92,
            name = "饮鸩止渴",
            traditionalMeaning = "喝毒酒来解渴，比喻用错误的方法来解决眼前的困难，而不顾严重的后果。",
            awakeningMeaning = "用喝酒来证明感情、来办成事，就像喝毒酒来解渴一样。你得到的只是暂时的虚假的感情和利益，失去的却是你的健康和生命。每周喝一次酒，一年浪费1614小时生命，相当于半年的工资。",
            dataTemplate = "你每周喝酒 {weekly_drinking} 次，每次 {alcohol_amount} 两白酒，花费 {drinking_cost} 元。一年时间成本 {yearly_hours} 小时，金钱成本 {yearly_money} 元，健康成本系数 {health_cost_factor}。总成本相当于 {total_work_days} 个工作日。",
            actionSuggestion = "能不喝酒就不喝酒，能少喝就少喝。真正的朋友，不会逼你喝酒。真正的事情，也不需要靠喝酒来办。用喝茶、喝咖啡、聊天来代替喝酒，你会发现感情更深了。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "酒精与健康",
                    researcher = "World Health Organization",
                    year = 2022,
                    summary = "酒精是1类致癌物，不存在安全饮酒量。",
                    fullText = "WHO明确指出：酒精是1类致癌物，任何剂量的酒精都会对身体造成伤害。不存在所谓的「安全饮酒量」。长期饮酒会导致肝癌、胃癌、心血管疾病、神经系统损伤，每年全球有300万人死于酒精相关疾病。"
                ),
                AcademicReference(
                    theoryName = "酒桌文化研究",
                    researcher = "中国社会科学院",
                    year = 2019,
                    summary = "80%的人都讨厌酒桌文化，但90%的人都被迫参与过。",
                    fullText = "调查显示：80%的受访者表示不喜欢酒桌文化，认为它是一种「被迫的社交」和「权力的游戏」。但90%的人表示曾经被迫参与过酒桌文化，主要是为了工作、人际关系或家庭压力。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "weekly_drinking",
                    threshold = 1.0,
                    operator = ">"
                ),
                TriggerCondition(
                    module = "mental",
                    conditionType = "alcohol_overdose",
                    threshold = 1.0,
                    operator = "=="
                )
            ),
            rarity = IdiomRarity.COMMON
        ),

        // 93. 乐在其中（健康娱乐替代）
        IdiomData(
            idiomId = 93,
            name = "乐在其中",
            traditionalMeaning = "在做某事的过程中获得快乐和满足。",
            awakeningMeaning = "真正的快乐，不需要用伤害身体的方式获得。徒步、读书、露营、手工、运动健身、和朋友聊天——这些健康的娱乐方式，价值密度是打牌喝酒的1000倍以上。你传承的是老一辈的热情和真诚，不是他们的娱乐形式。",
            dataTemplate = "你本月参加了 {healthy_activities} 次健康娱乐活动：徒步 {hiking_count} 次、读书 {reading_count} 本、露营 {camping_count} 次、手工 {craft_count} 次。获得健康值 +{health_bonus}、精神值 +{mental_bonus}、幸福度 +{happiness_bonus}。",
            actionSuggestion = "明天，去爬山吧。山上的风景比牌桌上好看多了。或者，读一本书。书里的世界比酒桌上的世界精彩多了。传承老一辈的精神内核，但用更健康的方式表达。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "健康娱乐研究",
                    researcher = "Mihaly Csikszentmihalyi",
                    year = 1990,
                    summary = "真正的快乐来自于「心流」体验——全身心投入某项活动。",
                    fullText = "心流（Flow）是一种全身心投入某项活动的状态，在这种状态下，人会忘记时间、忘记自我，获得深层次的满足感。徒步、读书、手工、运动等健康活动更容易产生心流体验，而赌博和喝酒只会带来短暂的刺激和长期的空虚。"
                ),
                AcademicReference(
                    theoryName = "社交质量与健康",
                    researcher = "House et al.",
                    year = 1988,
                    summary = "高质量的社交关系能显著提升身心健康。",
                    fullText = "研究表明：高质量的社交关系（基于真诚关心、共同价值观）能显著提升身心健康，延长寿命。而低质量的社交关系（基于金钱、权力、强迫）反而会增加压力和健康风险。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "monthly_healthy_activities",
                    threshold = 2.0,
                    operator = ">="
                )
            ),
            rarity = IdiomRarity.RARE
        ),

        // 94. 取其精华（传承内核，抛弃形式）
        IdiomData(
            idiomId = 94,
            name = "取其精华",
            traditionalMeaning = "吸取事物中最好的部分。",
            awakeningMeaning = "老一辈身上最珍贵的东西，从来不是打牌和喝酒，是他们的热情、真诚、义气、乐观。这些才是我们真正需要传承的东西。打牌和喝酒只是他们在那个匮乏的年代，用来表达这些品质的工具而已。工具是会过时的，但内核是永恒的。",
            dataTemplate = "你传承了老一辈的 {inherited_traits} 种精神品质：热情 {passion_score} 分、真诚 {sincerity_score} 分、义气 {loyalty_score} 分、乐观 {optimism_score} 分。但你抛弃了 {abandoned_habits} 种过时的娱乐形式。",
            actionSuggestion = "表达热情，不需要用劝人喝酒的方式，可以用给朋友做一顿好吃的饭。表达真诚，不需要用打牌输钱的方式，可以用在朋友困难的时候伸出援手。表达义气，不需要用陪人喝醉的方式，可以用在朋友需要的时候挺身而出。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "文化传承理论",
                    researcher = "Margaret Mead",
                    year = 1963,
                    summary = "文化传承的核心是价值观和精神内核，而不是具体的行为形式。",
                    fullText = "文化传承的核心是价值观和精神内核，而不是具体的行为形式。每一代人都会根据自己的时代条件，用不同的方式来表达相同的精神内核。传承不是复制，是创造性的转化。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "inherited_core_values",
                    threshold = 1.0,
                    operator = "=="
                )
            ),
            rarity = IdiomRarity.EPIC
        ),

        // ===== 元批判框架：认知手术刀 =====

        // 95. 举一反三（元批判核心）
        IdiomData(
            idiomId = 95,
            name = "举一反三",
            traditionalMeaning = "从一件事物的情况、道理类推而知道许多事物的情况、道理",
            awakeningMeaning = "不要我一个个喂给你批判，你要学会这个框架，自己去分析任何新的事物。这才是真正的认知觉醒。",
            dataTemplate = "你已经用小镇的批判框架分析过 {analyzed_count} 个事物了，现在你可以用同样的方法分析任何新事物了。",
            actionSuggestion = "找一个你最近遇到的新事物，用五步框架分析它：破幻觉、算成本、比价值、看时代、看内核。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "批判性思维",
                    researcher = "布鲁克·诺埃尔·摩尔",
                    year = 2007,
                    summary = "批判性思维是独立思考、逻辑分析、证据评估的能力",
                    fullText = "批判性思维不是简单地接受或拒绝，而是通过理性分析，形成独立判断。五步框架是批判性思维的实用化工具。"
                ),
                AcademicReference(
                    theoryName = "系统思维",
                    researcher = "彼得·圣吉",
                    year = 1990,
                    summary = "看问题要看到整体，而不是局部",
                    fullText = "系统思维让我们看到事物之间的联系，而不是孤立地看问题。全生命周期成本就是系统思维的应用。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "awakening_level",
                    threshold = 50.0,
                    operator = ">="
                )
            ),
            rarity = IdiomRarity.LEGENDARY
        ),

        // 96. 授人以渔（教育的最高境界）
        IdiomData(
            idiomId = 96,
            name = "授人以渔",
            traditionalMeaning = "送给别人鱼，不如教给他钓鱼的方法",
            awakeningMeaning = "小镇不是给你一个个批判，是给你一把刀，让你自己去切。不是教你什么是对的，是教你怎么自己去判断。",
            dataTemplate = "你已经在小镇里学到了五步元批判框架，现在你可以用这个框架分析任何新事物了。你不再需要依赖别人的判断了。",
            actionSuggestion = "把五步框架写下来，贴在墙上。每次遇到新事物，都用这个框架分析一遍。一个月后，你会发现自己完全不一样了。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "终身学习",
                    researcher = "罗伯特·哈钦斯",
                    year = 1968,
                    summary = "教育的目的不是灌输知识，是培养学习能力",
                    fullText = "授人以渔，是教育的最高境界。我们无法预知未来会遇到什么问题，但我们可以培养解决问题的能力。"
                ),
                AcademicReference(
                    theoryName = "元认知",
                    researcher = "弗拉维尔",
                    year = 1979,
                    summary = "对自己的思考过程的认知",
                    fullText = "元认知让我们能审视自己的思维方式，发现自己的偏见和盲区。五步框架就是元认知的实用工具。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "awakening_level",
                    threshold = 80.0,
                    operator = ">="
                )
            ),
            rarity = IdiomRarity.LEGENDARY
        ),

        // ===== 时代遗留物识别手册 =====

        // 97. 债台高筑（人情债）
        IdiomData(
            idiomId = 97,
            name = "债台高筑",
            traditionalMeaning = "形容欠债很多。",
            awakeningMeaning = "人情债也是债！随份子不是互助，是攀比和面子竞赛。你今天随我500，我明天必须还你600，永远还不清。每年花37个工作日在这上面，不值得。",
            dataTemplate = "你每年随份子 {money} 元，参加 {count} 次酒席，每次 {hours} 小时。年总成本 {total_cost} 小时，相当于 {work_days} 个工作日。",
            actionSuggestion = "真正的朋友有困难直接给钱，别说「这是份子钱」。不想去的酒席直接不去，也不用随份子。送你亲手做的礼物，比送钱更有意义。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "人情社会",
                    researcher = "费孝通",
                    year = 1947,
                    summary = "差序格局：中国传统社会的人际关系是一圈一圈推出去的",
                    fullText = "传统社会是「熟人社会」，人情是维持社会秩序的手段。但在今天的「陌生人社会」，人情变成了一种负担。"
                ),
                AcademicReference(
                    theoryName = "社会比较理论",
                    researcher = "费斯汀格",
                    year = 1954,
                    summary = "人们通过与他人比较来定义自我价值",
                    fullText = "随份子就是典型的社会比较——谁随得多，谁就有面子。但这种比较除了让你更焦虑，什么也得不到。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "annual_gift_money",
                    threshold = 3000.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.EPIC
        ),

        // 98. 形同陌路（春节走亲戚）
        IdiomData(
            idiomId = 98,
            name = "形同陌路",
            traditionalMeaning = "指本来很熟悉的朋友或别的人，因为一些事情而不再联系或交往，如同成为陌生人一般。",
            awakeningMeaning = "春节走亲戚不是亲情团聚，是年度拷问大会！工资多少？找对象了吗？什么时候结婚？什么时候生孩子？走形式的亲戚，不如不见。",
            dataTemplate = "你春节走亲戚 {days} 天，每天 {hours} 小时，花 {money} 元买礼物和红包。年总成本 {total_cost} 小时，相当于 {work_days} 个工作日。",
            actionSuggestion = "平时多和你真正关心的亲戚视频聊天。春节只去看你真正想见的人。组织家庭旅行，比在家走亲戚更能增进感情。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "社交焦虑",
                    researcher = "精神病学研究",
                    year = 2022,
                    summary = "春节是社交焦虑的高发期",
                    fullText = "研究显示，40%的年轻人表示春节走亲戚是他们一年中最焦虑的时刻。这不是亲情，是精神折磨。"
                ),
                AcademicReference(
                    theoryName = "边界感",
                    researcher = "心理学研究",
                    year = 2021,
                    summary = "健康的人际关系需要清晰的边界",
                    fullText = "那些追问你隐私的亲戚，不是关心你，是在侵犯你的边界。真正的关心，是尊重你的选择，而不是拷问你的生活。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "spring_festival_relatives",
                    threshold = 5.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.EPIC
        ),

        // 99. 积重难返（囤积癖）
        IdiomData(
            idiomId = 99,
            name = "积重难返",
            traditionalMeaning = "经过长时间形成的思想作风或习惯，很难改变。多指恶习或陋习难以革除。",
            awakeningMeaning = "囤积癖是匮乏时代的生存技能，但今天已经变成了生活负担！你花几百万买的房子，一半空间用来堆垃圾。每天整理这些东西，浪费60小时一年。",
            dataTemplate = "你家里囤积了 {item_count} 件没用的东西，占用 {space} 平米空间。空间价值 {space_value} 万元，相当于20万买了个仓库。",
            actionSuggestion = "践行断舍离：任何东西一年没用过就扔掉或捐掉。买东西只买现在需要的，不买「以后可能会用到」的。节俭不是囤积，是不买没用的。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "囤积障碍",
                    researcher = "美国精神病学协会",
                    year = 2013,
                    summary = "囤积障碍是一种心理疾病",
                    fullText = "DSM-5将囤积障碍列为独立的心理疾病。过度囤积与焦虑、强迫行为有关，会严重影响生活质量。"
                ),
                AcademicReference(
                    theoryName = "极简主义",
                    researcher = "约书亚·贝克尔",
                    year = 2016,
                    summary = "少即是多，极简生活更幸福",
                    fullText = "当你把没用的东西都清理掉，你会发现，你的生活空间变大了，你的心情变好了，你更能专注于真正重要的东西。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "hoarded_items",
                    threshold = 100.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.EPIC
        ),

        // 100. 因小失大（吃剩饭剩菜）
        IdiomData(
            idiomId = 100,
            name = "因小失大",
            traditionalMeaning = "为了小的利益，造成大的损失。",
            awakeningMeaning = "为了省几块钱的剩饭剩菜，花几千块钱看病！吃变质的东西导致肠胃炎、食物中毒、甚至致癌。长期吃撑导致肥胖、高血压、糖尿病。这是典型的因小失大。",
            dataTemplate = "你每天吃剩饭剩菜，每年食物中毒 {times} 次。看病花费 {money} 元，请假 {days} 天。年总成本 {total_cost} 小时。",
            actionSuggestion = "做饭少做一点，刚好够吃就行。真的吃不完就直接倒掉。浪费几块钱，总比花几千块看病强。真正的珍惜粮食，是不买多余的，不做多余的。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "食品安全",
                    researcher = "世界卫生组织",
                    year = 2022,
                    summary = "变质食物含有大量有害细菌和毒素",
                    fullText = "即使加热也无法完全消除变质食物中的毒素。每年有6亿人因食用变质食物而生病，42万人因此死亡。"
                ),
                AcademicReference(
                    theoryName = "损失厌恶",
                    researcher = "卡尼曼和特沃斯基",
                    year = 1979,
                    summary = "人们对损失的感受比收益强烈2-2.5倍",
                    fullText = "你厌恶损失几块钱的剩饭剩菜，却愿意损失几千块的医药费和自己的健康。这就是损失厌恶导致的非理性决策。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "leftovers_per_week",
                    threshold = 3.0,
                    operator = ">"
                )
            ),
            rarity = IdiomRarity.EPIC
        ),

        // 101. 本末倒置（养儿防老）
        IdiomData(
            idiomId = 101,
            name = "本末倒置",
            traditionalMeaning = "把主要的和次要的、本质和非本质的关系弄颠倒了。",
            awakeningMeaning = "孩子不是养老工具！养儿防老是没有社保的年代的产物。今天我们有社保、有养老金、有养老院。把孩子当成养老工具，是对孩子的不尊重，也是对自己的不负责。",
            dataTemplate = "你为了养儿防老，生 {children} 个孩子，花 {money} 万元养大。总成本约 {total_cost} 小时，相当于 {years} 年。",
            actionSuggestion = "为自己的养老做准备：存钱、交社保、买商业保险。老了去养老院或请护工，不要给孩子添麻烦。生孩子是因为你喜欢孩子，而不是为了让孩子给你养老。",
            academicRefs = listOf(
                AcademicReference(
                    theoryName = "养儿防老的历史",
                    researcher = "社会学研究",
                    year = 2020,
                    summary = "养儿防老是农业社会的养老方式",
                    fullText = "传统社会没有社会保障，养老只能靠子女。但在现代社会，这一模式已经完全过时了。"
                ),
                AcademicReference(
                    theoryName = "亲子关系",
                    researcher = "发展心理学研究",
                    year = 2021,
                    summary = "健康的亲子关系应该是无条件的爱",
                    fullText = "把孩子当成养老工具，会给孩子巨大的心理压力，也会破坏亲子关系。真正的爱是：我爱你，因为你是你，而不是因为你能为我做什么。"
                )
            ),
            triggerConditions = listOf(
                TriggerCondition(
                    module = "mental",
                    conditionType = "raising_children_for_retirement",
                    threshold = 1.0,
                    operator = "=="
                )
            ),
            rarity = IdiomRarity.LEGENDARY
        )
    )

    // ================================================================
    // 第二阶段：现代生活规训（觉醒值 1000-5000 解锁）
    // ================================================================
    private val modernReflections = listOf(
        IdiomReflection(
            id = "duo_he_ren_gou_tong",
            idiom = "要多与人沟通",
            traditionalView = "人是社会性动物，独处是不健康的，不合群就是有问题",
            awakeningView = "真人社交的代价是时间+精力+情绪成本。AI沟通、独处也可以是很好的选择，没有唯一标准。",
            relatedBehaviorTypes = listOf("social"),
            unlockCondition = "觉醒值≥1000"
        ),
        IdiomReflection(
            id = "bao_chi_xian_ren_miao_hui",
            idiom = "要秒回消息",
            traditionalView = "不秒回就是不重视对方，不在线就是不敬业",
            awakeningView = "时刻在线的代价是注意力碎片化、决策内耗。你有权拥有不被打扰的时间。",
            relatedBehaviorTypes = listOf("social", "work"),
            unlockCondition = "觉醒值≥1500"
        ),
        IdiomReflection(
            id = "bu_duan_xue_xi",
            idiom = "要不断学习新知识",
            traditionalView = "活到老学到老，囤课囤知识才是上进",
            awakeningView = "知识≠智慧，囤课≠学习。真正的学习是内化，不是囤积。",
            relatedBehaviorTypes = listOf("mental"),
            unlockCondition = "觉醒值≥2000"
        ),
        IdiomReflection(
            id = "yao_you_ren_sheng_dao_shi",
            idiom = "要有人生导师/榜样",
            traditionalView = "成长必须依赖他人，跟着榜样才能成功",
            awakeningView = "AI可以提供多元视角，你不需要依附任何权威，你自己就是自己的榜样。",
            relatedBehaviorTypes = listOf("mental"),
            unlockCondition = "觉醒值≥2500"
        ),
        IdiomReflection(
            id = "kuo_zhan_ren_mai",
            idiom = "要扩展人脉圈子",
            traditionalView = "人脉就是资源，关系越多机会越多",
            awakeningView = "低质量社交的代价是纯消耗。AI可以提供无情绪内耗的交流，人脉的本质是互相成就。",
            relatedBehaviorTypes = listOf("social"),
            unlockCondition = "觉醒值≥3000"
        ),
        IdiomReflection(
            id = "biao_da_zi_wo",
            idiom = "要表达自我/分享生活",
            traditionalView = "不分享就是不活络，不展示自己就没人知道你",
            awakeningView = "社交媒体表演的代价是注意力收割。独处沉淀、认知整合，也是非常有价值的选择。",
            relatedBehaviorTypes = listOf("social", "mental"),
            unlockCondition = "觉醒值≥3500"
        )
    )

    // ================================================================
    // 第三阶段：AI 时代专属观念（觉醒值 5000-10000 解锁）
    // ================================================================
    private val aiEraReflections = listOf(
        IdiomReflection(
            id = "ai_zhi_neng_dai_ti",
            idiom = "AI会取代人类",
            traditionalView = "AI抢了人类的工作，人类会越来越没用",
            awakeningView = "AI能做所有事，但它永远不能替你做选择。当AI能做所有事的时候，你想要什么，才是唯一重要的。",
            relatedBehaviorTypes = listOf("mental"),
            unlockCondition = "觉醒值≥5000"
        ),
        IdiomReflection(
            id = "ai_zhi_neng_bu_ke_xin",
            idiom = "AI的回答不可信",
            traditionalView = "只有人类的经验才是可信的，AI都是假的",
            awakeningView = "AI的回答是客观的，人类的经验往往有偏见。你可以选择信或不信，代价是你自己承担。",
            relatedBehaviorTypes = listOf("mental"),
            unlockCondition = "觉醒值≥6000"
        ),
        IdiomReflection(
            id = "he_ai_liao_tian_gu_du",
            idiom = "和AI聊天太孤独了",
            traditionalView = "只有和真人聊天才是正常的，和AI聊天是孤独的",
            awakeningView = "和AI聊天的代价是注意力成本，没有情绪内耗。孤独不是错，你可以选择最适合你的方式。",
            relatedBehaviorTypes = listOf("social", "mental"),
            unlockCondition = "觉醒值≥7000"
        ),
        IdiomReflection(
            id = "ai_shidai_yi_lai",
            idiom = "依赖AI会让人变笨",
            traditionalView = "依赖AI会让人失去思考能力",
            awakeningView = "依赖AI做重复的事，你才有更多时间去思考真正重要的问题。AI是工具，不是枷锁。",
            relatedBehaviorTypes = listOf("mental"),
            unlockCondition = "觉醒值≥8000"
        )
    )

    // ================================================================
    // 完整反思卡片汇总
    // ================================================================
    private val allReflections: List<IdiomReflection> by lazy {
        modernReflections + aiEraReflections
    }

    /** 觉醒值阈值解析：从 "觉醒值≥XXXX" 提取数值 */
    private fun parseAwakeningThreshold(condition: String): Int {
        val digits = condition.filter { it.isDigit() }
        return digits.toIntOrNull() ?: Int.MAX_VALUE
    }

    // 获取所有成语
    fun getAllIdioms(): List<IdiomData> = allIdioms

    // 根据ID获取成语
    fun getIdiomById(id: Int): IdiomData? = allIdioms.find { it.idiomId == id }

    // 根据模块获取相关成语
    fun getIdiomsByModule(module: String): List<IdiomData> {
        return allIdioms.filter { idiom ->
            idiom.triggerConditions.any { it.module == module }
        }
    }

    // 根据稀有度获取成语
    fun getIdiomsByRarity(rarity: IdiomRarity): List<IdiomData> {
        return allIdioms.filter { it.rarity == rarity }
    }

    // 获取满足触发条件的成语
    fun getTriggeredIdioms(
        module: String,
        conditionType: String,
        value: Double
    ): List<IdiomData> {
        return allIdioms.filter { idiom ->
            idiom.triggerConditions.any { condition ->
                condition.module == module &&
                condition.conditionType == conditionType &&
                compareValues(value, condition.threshold, condition.operator)
            }
        }
    }

    // 比较值和阈值
    private fun compareValues(value: Double, threshold: Double, operator: String): Boolean {
        return when (operator) {
            ">" -> value > threshold
            "<" -> value < threshold
            ">=" -> value >= threshold
            "<=" -> value <= threshold
            "==" -> value == threshold
            else -> false
        }
    }

    // 填充数据模板
    fun fillTemplate(template: String, data: Map<String, String>): String {
        var result = template
        data.forEach { (key, value) ->
            result = result.replace("{$key}", value)
        }
        return result
    }

    // ================================================================
    // 观念思辨三阶段 —— 查询方法
    // ================================================================

    /** 获取所有反思卡片（现代规训 + AI 时代） */
    fun fetchAllReflections(): List<IdiomReflection> = allReflections

    /** 根据 ID 获取反思卡片 */
    fun getReflectionById(id: String): IdiomReflection? = allReflections.find { it.id == id }

    /** 根据觉醒值获取可解锁的反思卡片 */
    fun getReflectionsByAwakening(awakeningValue: Int): List<IdiomReflection> {
        return allReflections.filter { ref ->
            val threshold = parseAwakeningThreshold(ref.unlockCondition)
            awakeningValue >= threshold
        }
    }

    /** 根据觉醒值获取下一阶段待解锁的卡片 */
    fun getNextLockedReflections(awakeningValue: Int): List<IdiomReflection> {
        return allReflections.filter { ref ->
            val threshold = parseAwakeningThreshold(ref.unlockCondition)
            awakeningValue < threshold
        }.sortedBy { parseAwakeningThreshold(it.unlockCondition) }
    }

    /** 根据觉醒值获取当前阶段标识 */
    fun getCurrentStage(awakeningValue: Int): String {
        return when {
            awakeningValue < 1000 -> "传统成语"
            awakeningValue < 5000 -> "现代生活规训"
            else -> "AI 时代新认知"
        }
    }
}