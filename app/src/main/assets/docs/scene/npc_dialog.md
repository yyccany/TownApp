<!-- NPC对话文案库 -->
<!-- type: practical 务实NPC（菜摊主/医生/老人） tendency>=60 -->
<!-- type: vanity 虚荣NPC（名牌店店员） tendency<=40 -->
<!-- type: neutral 中立NPC（同事/路人） 40<tendency<60 -->
<!-- identity: office_worker/media_freelancer/fresh_graduate/all -->
<!-- score_min/score_max: 玩家消费分匹配区间（<=40虚荣/41-59中立/>=60人本） -->

<!-- ==================== 务实NPC - 人本路线（>=60分） ==================== -->
<!-- key: practical_all_high | type:practical | identity:all | score_min:60 -->
【TEXT】
日子嘛，舒服最重要，不用活给别人看。你现在这样就挺好。

<!-- key: practical_all_high_2 | type:practical | identity:all | score_min:60 -->
【TEXT】
看你气色不错，最近过得挺踏实吧？平平淡淡才是真，这样就好。

<!-- key: practical_all_high_3 | type:practical | identity:all | score_min:60 -->
【TEXT】
自己做饭吃啊？真不错，现在年轻人愿意做饭的不多了。会过日子比什么都强。

<!-- key: practical_office_high | type:practical | identity:office_worker | score_min:60 -->
【TEXT】
上班族能踏实过日子真好。今天菜新鲜，给你算便宜点。下班回家做顿饭，比外卖强。

<!-- key: practical_office_high_2 | type:practical | identity:office_worker | score_min:60 -->
【TEXT】
看你就是会过日子的人，不瞎买那些没用的。钱要花在刀刃上，吃饭睡觉最重要。

<!-- key: practical_media_high | type:practical | identity:media_freelancer | score_min:60 -->
【TEXT】
做自由职业还能把日子过这么稳当，真不错。别总熬夜，饭要按时吃。

<!-- key: practical_media_high_2 | type:practical | identity:media_freelancer | score_min:60 -->
【TEXT】
我看你最近气色比之前好多了，是不是作息规律了？继续保持啊，身体是本钱。

<!-- key: practical_graduate_high | type:practical | identity:fresh_graduate | score_min:60 -->
【TEXT】
刚毕业就能沉下心来好好过日子，不容易啊。慢慢来，日子会越过越好的。

<!-- key: practical_graduate_high_2 | type:practical | identity:fresh_graduate | score_min:60 -->
【TEXT】
年轻人不攀比，踏踏实实的，以后肯定错不了。这点菜送你了，刚摘的。

<!-- key: practical_worker_chat_high | type:practical | identity:office_worker | score_min:60 -->
【TEXT】
最近下班挺早啊？不加班挺好的，工作是做不完的，回家好好歇着比什么都强。

<!-- key: practical_doctor_high | type:practical | identity:all | score_min:60 -->
【TEXT】
看你状态不错，继续保持。好好吃饭好好睡觉，比吃什么补品都管用。

<!-- ==================== 务实NPC - 虚荣路线（<=40分） ==================== -->
<!-- key: practical_all_low | type:practical | identity:all | score_max:40 -->
【TEXT】
年轻人，别总买些花里胡哨的，吃好睡好比什么都强。那些名牌能当饭吃吗？

<!-- key: practical_all_low_2 | type:practical | identity:all | score_max:40 -->
【TEXT】
看你最近是不是总熬夜？脸色不太好。钱可以慢慢赚，身体垮了就什么都没了。

<!-- key: practical_all_low_3 | type:practical | identity:all | score_max:40 -->
【TEXT】
别总盯着手机看，对眼睛不好。有空多出来走走，晒晒太阳比什么都强。

<!-- key: practical_office_low | type:practical | identity:office_worker | score_max:40 -->
【TEXT】
上班族赚点钱不容易，别都花在那些没用的包装上。买菜回家自己做，干净又省钱。

<!-- key: practical_office_low_2 | type:practical | identity:office_worker | score_max:40 -->
【TEXT】
看你天天点外卖，那东西油大盐大，吃久了身体受不了。自己学着做做饭吧。

<!-- key: practical_media_low | type:practical | identity:media_freelancer | score_max:40 -->
【TEXT】
做新媒体的吧？穿得挺光鲜，别太拼了，流量是赚不完的，身体是自己的。

<!-- key: practical_media_low_2 | type:practical | identity:media_freelancer | score_max:40 -->
【TEXT】
看你天天抱着个手机拍，吃饭都顾不上。别为了拍视频把身体搞垮了，不值当。

<!-- key: practical_graduate_low | type:practical | identity:fresh_graduate | score_max:40 -->
【TEXT】
刚毕业别学别人乱花钱，攒点钱比什么都强。那些名牌包啊衣服啊，不能当饭吃。

<!-- key: practical_graduate_low_2 | type:practical | identity:fresh_graduate | score_max:40 -->
【TEXT】
找工作别光看工资高不高，能学到东西、能按时睡觉更重要。别拿健康换钱。

<!-- key: practical_doctor_low | type:practical | identity:all | score_max:40 -->
【TEXT】
你这作息不行啊，再这样下去身体要出问题的。别仗着年轻就硬扛。

<!-- key: practical_vendor_low | type:practical | identity:all | score_max:40 -->
【TEXT】
买菜就买菜，别总看那些网红推荐的进口菜，又贵又不新鲜，本地菜最好。

<!-- ==================== 务实NPC - 中立区间 ==================== -->
<!-- key: practical_all_neutral | type:practical | identity:all -->
【TEXT】
慢慢来，怎么过都是一辈子，自己舒服就行。今天菜挺新鲜的，随便看看。

<!-- key: practical_all_neutral_2 | type:practical | identity:all -->
【TEXT】
今天天气不错，出来逛逛啊？想买点什么？

<!-- key: practical_office_neutral | type:practical | identity:office_worker -->
【TEXT】
今天下班挺早啊？今天的西红柿不错，带两个回去？

<!-- key: practical_media_neutral | type:practical | identity:media_freelancer -->
【TEXT】
做自由职业时间自由吧？但也要记得按时吃饭，别总凑活。

<!-- key: practical_graduate_neutral | type:practical | identity:fresh_graduate -->
【TEXT】
刚毕业吧？慢慢来，日子不用急，身体是自己的。

<!-- key: practical_worker_neutral | type:practical | identity:office_worker -->
【TEXT】
上班辛苦了，下班了就好好歇歇，别总想着工作。

<!-- key: practical_chat | type:practical | identity:all -->
【TEXT】
今天天气不错啊。

<!-- ==================== 虚荣NPC - 虚荣路线（<=40分） ==================== -->
<!-- key: vanity_all_low | type:vanity | identity:all | score_max:40 -->
【TEXT】
新款到了，你背上肯定好看！现在流行这个，昨天刚补的货，再不买又没了。

<!-- key: vanity_all_low_2 | type:vanity | identity:all | score_max:40 -->
【TEXT】
这是我们这季的爆款，好多博主都在穿！你穿上绝对上镜，拍照特别好看。

<!-- key: vanity_all_low_3 | type:vanity | identity:all | score_max:40 -->
【TEXT】
你看这做工、这面料，跟那些便宜货就是不一样。一分钱一分货，买回去绝对值。

<!-- key: vanity_office_low | type:vanity | identity:office_worker | score_max:40 -->
【TEXT】
上班穿这个也太素了吧？你看你同事是不是都背大牌包了？你这包该换啦。

<!-- key: vanity_office_low_2 | type:vanity | identity:office_worker | score_max:40 -->
【TEXT】
姐/哥，不是我说，你穿成这样去开会，客户都觉得你不专业。这件特别显气场。

<!-- key: vanity_media_low | type:vanity | identity:media_freelancer | score_max:40 -->
【TEXT】
你也是做这行的吧？这款你拍视频肯定上镜！好多大V都来我们家拿货。

<!-- key: vanity_media_low_2 | type:vanity | identity:media_freelancer | score_max:40 -->
【TEXT】
妹妹/弟弟你粉丝多少啦？我们家这款穿上绝对涨粉！拍照特别出片。

<!-- key: vanity_graduate_low | type:vanity | identity:fresh_graduate | score_max:40 -->
【TEXT】
刚毕业吧？入职得有几件像样的衣服撑场面。第一印象很重要的！

<!-- key: vanity_graduate_low_2 | type:vanity | identity:fresh_graduate | score_max:40 -->
【TEXT】
你去面试穿这个，HR第一眼就把你刷了。我们这有毕业生特惠，一套下来也不贵。

<!-- key: vanity_buy_low | type:vanity | identity:all | score_max:40 -->
【TEXT】
您真有眼光！这是我们卖得最好的一款，好多人都买不到呢。您穿这个回头率绝对高。

<!-- ==================== 虚荣NPC - 人本路线（>=60分） ==================== -->
<!-- key: vanity_all_high | type:vanity | identity:all | score_min:60 -->
【TEXT】
你这衣服穿着舒服是舒服，就是太素了点，不上镜。不过你喜欢就好，舒服最重要嘛。

<!-- key: vanity_all_high_2 | type:vanity | identity:all | score_min:60 -->
【TEXT】
你气质这么好，就是穿得太随便了点，浪费了这么好的底子。不过...自己舒服最重要对吧。

<!-- key: vanity_all_high_3 | type:vanity | identity:all | score_min:60 -->
【TEXT】
现在像你这样不跟风买新款的人不多了。进来随便看看吧，不买也没关系。

<!-- key: vanity_office_high | type:vanity | identity:office_worker | score_min:60 -->
【TEXT】
你这件衣服穿了挺久了吧？质量真好。其实穿着舒服比什么都强，新款出得快，买不完的。

<!-- key: vanity_media_high | type:vanity | identity:media_freelancer | score_min:60 -->
【TEXT】
你是博主吧？现在好多博主都开始返璞归真了，穿简单点反而更有辨识度。

<!-- key: vanity_graduate_high | type:vanity | identity:fresh_graduate | score_min:60 -->
【TEXT】
刚毕业不用急着买太贵的衣服，干净整洁就很好。等以后真正需要了再买也不迟。

<!-- key: vanity_window_high | type:vanity | identity:all | score_min:60 -->
【TEXT】
没关系，不买也可以看看。新款确实好看，但不一定适合每个人。你慢慢逛。

<!-- key: vanity_leave_high | type:vanity | identity:all | score_min:60 -->
【TEXT】
慢走啊，欢迎下次再来。没关系，什么时候想买了再过来。

<!-- ==================== 虚荣NPC - 中立区间 ==================== -->
<!-- key: vanity_all_neutral | type:vanity | identity:all -->
【TEXT】
欢迎光临，随便看看，喜欢可以试一下。今天刚到的新款，我拿给你看看？

<!-- key: vanity_all_neutral_2 | type:vanity | identity:all -->
【TEXT】
要不要看看新款？最近大家都在买这个，卖得特别好。

<!-- key: vanity_office_neutral | type:vanity | identity:office_worker -->
【TEXT】
上班穿件好点的衣服，领导同事都高看你一眼。要不要试试这款？

<!-- key: vanity_media_neutral | type:vanity | identity:media_freelancer -->
【TEXT】
你是博主吧？我们家衣服特别出片，很多达人都来拿货，随便看看。

<!-- key: vanity_graduate_neutral | type:vanity | identity:fresh_graduate -->
【TEXT】
刚毕业找工作啊？我们这有适合面试穿的款式，要不要看看？

<!-- key: vanity_greeting | type:vanity | identity:all -->
【TEXT】
欢迎光临~

<!-- ==================== 中立NPC - 人本路线（>=60分） ==================== -->
<!-- key: neutral_all_high | type:neutral | identity:all | score_min:60 -->
【TEXT】
今天气色不错啊，看着挺精神的。最近过得挺好吧？

<!-- key: neutral_all_high_2 | type:neutral | identity:all | score_min:60 -->
【TEXT】
自己买菜做饭啊？真不错，会过日子。看你状态挺好的，继续保持啊。

<!-- key: neutral_all_high_3 | type:neutral | identity:all | score_min:60 -->
【TEXT】
看你脚步都轻快了，最近没什么烦心事吧？这样挺好的，平平淡淡就是福。

<!-- key: neutral_office_high | type:neutral | identity:office_worker | score_min:60 -->
【TEXT】
下班了？今天工作顺利吗？看你心情不错，不加班真好，早点回家休息吧。

<!-- key: neutral_media_high | type:neutral | identity:media_freelancer | score_min:60 -->
【TEXT】
最近是不是没怎么熬夜？看你气色比之前好多了。这样就对了，身体要紧。

<!-- key: neutral_graduate_high | type:neutral | identity:fresh_graduate | score_min:60 -->
【TEXT】
刚到小镇没多久吧？看你慢慢适应了，真好。别着急，日子会越来越顺的。

<!-- key: neutral_colleague_chat_high | type:neutral | identity:office_worker | score_min:60 -->
【TEXT】
最近大家都想早点下班回家做饭呢，这种氛围真好。工作是做不完的，生活才是自己的。

<!-- key: neutral_walk_high | type:neutral | identity:all | score_min:60 -->
【TEXT】
出来散步啊？今天天气真好，慢慢走，不着急。

<!-- ==================== 中立NPC - 虚荣路线（<=40分） ==================== -->
<!-- key: neutral_all_low | type:neutral | identity:all | score_max:40 -->
【TEXT】
最近是不是没休息好？看你好像有点累。别太拼了，身体要紧啊。

<!-- key: neutral_all_low_2 | type:neutral | identity:all | score_max:40 -->
【TEXT】
看你好像有点没精神，是不是压力太大了？别给自己太大压力，慢慢来。

<!-- key: neutral_all_low_3 | type:neutral | identity:all | score_max:40 -->
【TEXT】
最近天气变了，注意增减衣服，别感冒了。看你脸色不太好，多休息。

<!-- key: neutral_office_low | type:neutral | identity:office_worker | score_max:40 -->
【TEXT】
又加班啊？别太拼了，钱是赚不完的。看你天天这么晚走，身体吃不消的。

<!-- key: neutral_media_low | type:neutral | identity:media_freelancer | score_max:40 -->
【TEXT】
做自媒体挺辛苦的吧？看你天天在外面跑，注意休息啊，别累坏了。

<!-- key: neutral_graduate_low | type:neutral | identity:fresh_graduate | score_max:40 -->
【TEXT】
找工作呢？别太着急，焦虑也没用。先照顾好自己，饭要按时吃。

<!-- key: neutral_colleague_low | type:neutral | identity:all | score_max:40 -->
【TEXT】
你最近是不是压力挺大的？看你话都少了。别一个人扛着，有什么事可以说说。

<!-- key: neutral_stranger_low | type:neutral | identity:all | score_max:40 -->
【TEXT】
你好。（看你好像有点累，没有多说话）

<!-- ==================== 中立NPC - 中立区间 ==================== -->
<!-- key: neutral_all_neutral | type:neutral | identity:all -->
【TEXT】
今天天气不错啊。

<!-- key: neutral_all_neutral_2 | type:neutral | identity:all -->
【TEXT】
出来逛逛啊？

<!-- key: neutral_all_neutral_3 | type:neutral | identity:all -->
【TEXT】
吃了吗？

<!-- key: neutral_office_neutral | type:neutral | identity:office_worker -->
【TEXT】
上班去啊？今天周一，又要开始忙了。

<!-- key: neutral_media_neutral | type:neutral | identity:media_freelancer -->
【TEXT】
今天又出来拍东西啊？辛苦了。

<!-- key: neutral_graduate_neutral | type:neutral | identity:fresh_graduate -->
【TEXT】
刚到小镇吧？多走走就熟了，大家都挺好的。

<!-- key: neutral_stranger | type:neutral | identity:all -->
【TEXT】
你好。（路过，冲你点了点头）
