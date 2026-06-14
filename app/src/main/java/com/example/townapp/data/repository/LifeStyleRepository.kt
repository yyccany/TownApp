package com.example.townapp.data.repository

import com.example.townapp.data.model.LifeStyleBehavior
import com.example.townapp.data.model.LifeStyleEntry

/**
 * 生活图鉴仓库。
 *
 * 每条数据都是真实普通人生活经历的客观呈现，
 * 不做任何价值判断，不输出任何结论。
 *
 * 贡献原则（写入代码注释永远不能打破）：
 * 1. 只收录我们真正了解的生活方式
 * 2. 每个条目必须包含至少两个不同视角的真实故事
 * 3. 所有数值变化只是客观事实，不代表好坏
 */
object LifeStyleRepository {

    /** 所有生活方式条目。 */
    private val entries: List<LifeStyleEntry> = listOf(

        // ============================================================
        // 中国本土篇（基于真实观察和普通人的分享）
        // ============================================================

        // -------- 1. 乡镇留守生活 --------
        LifeStyleEntry(
            id = "cn_village_left_behind",
            name = "乡镇留守生活",
            region = "乡镇",
            commonBehaviors = listOf(
                LifeStyleBehavior(
                    description = "父母外出打工，由祖辈抚养",
                    effects = mapOf("物质条件" to +2, "亲子相处时间" to -8),
                    explanation = "父母在外务工收入通常高于本地务农，家庭总经济状况有所改善；但父母与子女长期分居，每年见面次数有限。"
                ),
                LifeStyleBehavior(
                    description = "放学后做农活/家务",
                    effects = mapOf("生活技能" to +5, "自由玩耍时间" to -4),
                    explanation = "留守儿童往往需要帮祖辈做农活或家务，较早学会独立生活；但可自由支配的时间少于城市同龄人。"
                ),
                LifeStyleBehavior(
                    description = "用手机刷短视频打发时间",
                    effects = mapOf("娱乐获取" to +4, "户外活动时间" to -3, "视力" to -2),
                    explanation = "乡镇娱乐设施有限，手机是主要的娱乐来源；长期使用导致近视率上升。"
                )
            ),
            realStories = listOf(
                "我爸妈在我3岁就去广东打工了，一年回来一次。爷爷奶奶把我带大，我6岁就会做饭了。考上大学那年，我妈在电话里哭了很久，说对不起我。其实我不怪他们，他们不出去打工，我连学费都没有。",
                "带孩子真的太难了。我们也不想出去打工，但在老家种一年地，不如在外面干三个月。村里的小学只有三个老师，教学质量跟城里完全没法比。我想把孩子带在身边，但城里学校要借读费，我们在厂里住的地方也小。"
            ),
            unlockCondition = "与来自乡镇的 NPC 交流 5 次或阅读相关书籍"
        ),

        // -------- 2. 小镇做题家 --------
        LifeStyleEntry(
            id = "cn_exam_town",
            name = "小镇做题家生活",
            region = "县城/地级市",
            commonBehaviors = listOf(
                LifeStyleBehavior(
                    description = "从初中起每天学习 12-14 小时",
                    effects = mapOf("应试能力" to +8, "体能" to -3, "课外阅读量" to -5),
                    explanation = "高强度的刷题训练能有效提高考试分数，但长期久坐和睡眠不足导致体能下降，课外知识面相对狭窄。"
                ),
                LifeStyleBehavior(
                    description = "周末和寒暑假上补习班",
                    effects = mapOf("学科成绩" to +5, "可自由支配时间" to -6),
                    explanation = "额外的补课能帮助巩固学科知识，但占据了大部分课余时间。"
                ),
                LifeStyleBehavior(
                    description = "父母花家庭收入的 30-50% 在教育上",
                    effects = mapOf("升学概率" to +4, "家庭储蓄率" to -5),
                    explanation = "大量的教育投入提高了上好大学的可能性，但挤压了家庭在其他方面的支出和储蓄。"
                )
            ),
            realStories = listOf(
                "我高中三年每天五点五十起床，晚上十一点半睡觉，除了学习还是学习。最后考上了一所 985，在我们县城是轰动一时的事。但到了大学我发现，同学会钢琴、会英语演讲、会编程，我除了做题什么都不会。那种差距感，比高考还难熬。",
                "我儿子从小学就开始补课，一年花好几万。我不确定这对他是不是真的好，但别人的孩子都补，我不补心里不踏实。有时候看他那么累，我也心疼。"
            ),
            unlockCondition = "用过小镇做题家 5 个行为条目后自动解锁"
        ),

        // -------- 3. 大城市白领 --------
        LifeStyleEntry(
            id = "cn_white_collar",
            name = "大城市白领生活",
            region = "一线城市",
            commonBehaviors = listOf(
                LifeStyleBehavior(
                    description = "早高峰通勤 60-90 分钟",
                    effects = mapOf("通勤体验" to -4, "移动时间" to +2),
                    explanation = "长距离通勤是城市扩张的客观结果。通勤时间可用于听播客、看书或补觉，但拥挤的地铁车厢对体力和心情有消耗。"
                ),
                LifeStyleBehavior(
                    description = "外卖解决工作餐",
                    effects = mapOf("用餐便利性" to +5, "饮食支出" to -2, "烹饪技能" to -3),
                    explanation = "外卖节省了做饭和洗碗的时间，但长期依赖会削弱烹饪能力，日常餐饮开销高于自己做饭。"
                ),
                LifeStyleBehavior(
                    description = "租房占总支出 35-50%",
                    effects = mapOf("居住稳定性" to -3, "月可支配收入" to -4),
                    explanation = "高房租压缩了其他消费和储蓄空间，且面临房东涨租或搬家等不确定性。"
                ),
                LifeStyleBehavior(
                    description = "周末去网红店打卡/社交",
                    effects = mapOf("社交活动" to +4, "月娱乐支出" to -3),
                    explanation = "城市提供了丰富的社交和娱乐选择，但打卡文化也会带来额外的消费。"
                )
            ),
            realStories = listOf(
                "来上海五年了，工资从八千涨到了两万，但幸福感没有涨。房租从两千涨到了五千，吃饭从三十涨到了六十。每天在地铁上挤一个半小时，回到家只想躺着。有时候想想，还不如回老家，至少不用租房。",
                "我很喜欢上海。这里有最好的展览、演出、咖啡馆，你能接触到全世界最新的东西。我的同事来自五湖四海，每个人的故事都不一样。虽然累，但我学到了很多，成长了很多。"
            ),
            unlockCondition = "行为记录中出现 '通勤' 超过 10 次"
        ),

        // -------- 4. 小城安稳生活 --------
        LifeStyleEntry(
            id = "cn_small_city",
            name = "小城生活",
            region = "三四线城市",
            commonBehaviors = listOf(
                LifeStyleBehavior(
                    description = "步行或骑车 15 分钟上班",
                    effects = mapOf("通勤时间" to +5, "碳排放" to -3),
                    explanation = "小城市规模小，通勤距离短，节省了大量时间和交通成本。"
                ),
                LifeStyleBehavior(
                    description = "周末和家人/朋友聚餐",
                    effects = mapOf("家庭关系" to +5, "社交圈紧密程度" to +4),
                    explanation = "小城市社交圈相对稳定，亲友之间见面频率高。"
                ),
                LifeStyleBehavior(
                    description = "收入约为一线城市同岗位的 50-60%",
                    effects = mapOf("可支配收入" to -2, "住房面积" to +5),
                    explanation = "工资水平低于大城市，但房价和物价也低得多，同样的收入在小城市能获得更大的居住空间。"
                )
            ),
            realStories = listOf(
                "我从深圳回了老家，现在月薪八千，但房子 120 平，全款买的。每天走路十分钟上班，中午回家吃饭。虽然工资比深圳少了一半，但生活质量高了很多。我不觉得这是一种失败。",
                "回来两年了，说实话有点后悔。这里太安逸了，没有压力也没有动力。同学群里他们讨论的是期权、融资、上市，我们讨论的是哪家超市鸡蛋打折。我觉得自己的人生停滞了。"
            ),
            unlockCondition = "与来自小城市的 NPC 交流 5 次"
        ),

        // -------- 5. 互联网大厂工作 --------
        LifeStyleEntry(
            id = "cn_big_tech",
            name = "互联网大厂工作生活",
            region = "一线城市",
            commonBehaviors = listOf(
                LifeStyleBehavior(
                    description = "每天工作 10-12 小时",
                    effects = mapOf("职业成长速度" to +6, "可自由支配时间" to -5, "睡眠时间" to -2),
                    explanation = "高强度工作带来快速的经验积累和职业晋升，但严重挤压了个人生活和休息时间。"
                ),
                LifeStyleBehavior(
                    description = "公司提供三餐和健身房",
                    effects = mapOf("生活便利度" to +4, "日常开支" to -3),
                    explanation = "大厂福利减少了员工在饮食和健身上的时间和金钱支出。"
                ),
                LifeStyleBehavior(
                    description = "持续学习新技术应对职场竞争",
                    effects = mapOf("技术能力" to +6, "精神压力" to -3),
                    explanation = "互联网行业技术迭代快，持续学习是保持竞争力的必要条件，但也意味着几乎没有真正「下班」的时候。"
                )
            ),
            realStories = listOf(
                "在大厂三年，技术上成长很快，薪资翻了三倍。但我三年没有休过一次完整的年假，没有在晚上十点前下过班。有一天照镜子，发现自己头发白了很多，我才 27 岁。",
                "大厂给了一个农村孩子改变阶层的机会。我现在的收入是我爸的十倍，我能给家里买房了。累是累，但我不觉得这是剥削——这是公平交易，我付出时间，得到回报。"
            ),
            unlockCondition = "行为记录中 '编程' 或 '技能学习' 超过 20 次"
        ),

        // -------- 6. 自由职业/数字游民 --------
        LifeStyleEntry(
            id = "cn_freelancer",
            name = "自由职业者生活",
            region = "通用",
            commonBehaviors = listOf(
                LifeStyleBehavior(
                    description = "在家或咖啡店工作",
                    effects = mapOf("通勤时间" to +6, "工作环境切换" to +2),
                    explanation = "省去了通勤时间和固定办公座位的束缚，但需要自律来维持工作效率。"
                ),
                LifeStyleBehavior(
                    description = "收入不稳定，淡旺季明显",
                    effects = mapOf("时间自由度" to +6, "收入稳定性" to -4),
                    explanation = "自由职业者可以自主安排时间，但收入波动大，缺乏固定的社保和福利保障。"
                ),
                LifeStyleBehavior(
                    description = "自己交社保和医保",
                    effects = mapOf("社会保障" to +1, "月固定支出" to -2),
                    explanation = "自由职业者需要自行承担社保费用，保障水平低于企业职工。"
                )
            ),
            realStories = listOf(
                "自由职业第三年了，收入已经超过上班时候。最大的收获不是钱，是你可以决定什么时候工作、在哪里工作。上个月我在大理的民宿里写代码，下午去洱海边跑步。这种自由，给多少钱都不换。",
                "太焦虑了。这个月活多就能赚两万，下个月没活就零收入。社保要自己交，生病了没有带薪病假。有时候半夜醒来会想：我是不是应该回去上班？至少每个月有固定工资打到卡上。"
            ),
            unlockCondition = "行为记录中有 '自学' 和 '远程工作' 相关记录"
        ),

        // -------- 7. 全职妈妈/爸爸 --------
        LifeStyleEntry(
            id = "cn_fulltime_parent",
            name = "全职带娃生活",
            region = "通用",
            commonBehaviors = listOf(
                LifeStyleBehavior(
                    description = "全天候照顾 0-3 岁孩子",
                    effects = mapOf("孩子安全感" to +5, "个人独处时间" to -7, "睡眠连续性" to -4),
                    explanation = "亲自抚养能建立良好的亲子依恋关系，但 24 小时待命的状态对个人时间和睡眠质量影响很大。"
                ),
                LifeStyleBehavior(
                    description = "家庭收入减少一份",
                    effects = mapOf("家庭经济压力" to -3, "育儿质量" to +4),
                    explanation = "夫妻一方全职带娃减少了家庭总收入，但节省了保姆费用，且能按照自己的理念养育孩子。"
                ),
                LifeStyleBehavior(
                    description = "社交圈缩小",
                    effects = mapOf("亲子社交" to +3, "成人社交" to -4),
                    explanation = "社交重心转向宝妈/宝爸群体和亲子活动，与单身或无孩朋友的联系减少。"
                )
            ),
            realStories = listOf(
                "辞职带娃两年了。看着孩子从抬头、翻身、坐、爬到走路，每一个第一次我都在她身边。那种参与感，是任何工作都给不了的。但有时候也会崩溃——连续 800 天没有睡过一个整觉，没有人替你。",
                "我是全职爸爸。刚开始很难接受这个角色，周围的人都觉得男人应该在外面赚钱。但带了一年之后我发现，陪伴孩子成长这件事本身就有巨大的价值。我老婆事业发展得很好，我为她高兴。"
            ),
            unlockCondition = "行为记录中有 '育儿' 或 '家庭' 相关记录"
        ),

        // -------- 8. 考研/考编生活 --------
        LifeStyleEntry(
            id = "cn_exam_cram",
            name = "备考生活（考研/考编）",
            region = "通用",
            commonBehaviors = listOf(
                LifeStyleBehavior(
                    description = "每天在图书馆/自习室学习 10-12 小时",
                    effects = mapOf("应试准备" to +7, "体能" to -3, "社交活动" to -5),
                    explanation = "长时间的备考是应对激烈竞争的必要投入，但久坐和缺乏运动会影响身体健康。"
                ),
                LifeStyleBehavior(
                    description = "备考期间零收入或兼职收入",
                    effects = mapOf("职业经历积累" to -3, "家庭经济支持" to -4),
                    explanation = "全职备考意味着没有正式工作和稳定收入，在经济上依赖家庭支持或之前的积蓄。"
                ),
                LifeStyleBehavior(
                    description = "目标岗位报录比 100:1 以上",
                    effects = mapOf("备考动力" to +3, "心理压力" to -5),
                    explanation = "极低的录取率意味着大多数人即使付出了巨大努力也无法成功，这种不确定性是备考过程中最消耗心理能量的部分。"
                )
            ),
            realStories = listOf(
                "考了三次研究生，前两次都没过。第三次考上的那天，我在图书馆门口哭了好久。不是高兴，是委屈——三年啊，我同学都工作了，我还在考试。但现在研二了，不后悔。"
            ),
            unlockCondition = "行为记录中有 '学习' 相关记录超过 30 次"
        )
    )

    /** 返回所有生活方式条目。 */
    fun getAllEntries(): List<LifeStyleEntry> = entries

    /** 按地域分类查询。 */
    fun getEntriesByRegion(region: String): List<LifeStyleEntry> =
        entries.filter { it.region == region }

    /** 按 ID 查询。 */
    fun getEntryById(id: String): LifeStyleEntry? =
        entries.firstOrNull { it.id == id }

    /** 返回所有地域标签列表。 */
    fun getAllRegions(): List<String> =
        entries.map { it.region }.distinct()
}