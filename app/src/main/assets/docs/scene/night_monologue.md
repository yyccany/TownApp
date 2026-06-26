<!-- 夜间独白与梦境文案库 -->
<!-- 匹配规则：sleep_status + emotion 组合过滤，score根据疲劳/焦虑/创伤值匹配 -->
<!-- sleep_status: insomnia(失眠) restless(浅眠) deep(深睡) asleep(正常睡眠) -->
<!-- emotion: calm(平静) sad(难过) anxious(焦虑) lonely(孤独) -->
<!-- dream_type: peaceful(平静美梦) anxious(焦虑噩梦) memory(回忆梦境) childhood(童年旧梦) prophetic(预言感) -->

<!-- ==================== 失眠 - 焦虑 ==================== -->
<!-- key: night_insomnia_anxious_money | sleep_status:insomnia | emotion:anxious -->
【TEXT】
明天还要上班，钱却不够花。这样的日子，真的很难熬。

<!-- key: night_insomnia_anxious_work | sleep_status:insomnia | emotion:anxious -->
【TEXT】
明天有个重要的汇报，脑子停不下来，越想越慌。

<!-- key: night_insomnia_anxious_general | sleep_status:insomnia | emotion:anxious -->
【TEXT】
明明很累，就是睡不着。脑子里翻来覆去都是那些没做完的事。

<!-- key: night_insomnia_anxious_future | sleep_status:insomnia | emotion:anxious | anxiety_min:60 -->
【TEXT】
未来到底会怎么样呢？越想越觉得没有底气。

<!-- ==================== 失眠 - 难过 ==================== -->
<!-- key: night_insomnia_sad_lonely | sleep_status:insomnia | emotion:sad -->
【TEXT】
深夜的时候，孤独感会被放大很多倍。好像整个世界都睡着了，只有你一个人醒着。

<!-- key: night_insomnia_sad_regret | sleep_status:insomnia | emotion:sad -->
【TEXT】
要是当时做了不同的选择，现在会不会不一样呢。

<!-- key: night_insomnia_sad_tired | sleep_status:insomnia | emotion:sad | fatigue_min:70 -->
【TEXT】
真的好累啊。不是身体那种累，是心里累。

<!-- key: night_insomnia_sad_cry | sleep_status:insomnia | emotion:sad | trauma_min:60 -->
【TEXT】
不知道为什么，眼泪就悄悄流下来了。没关系，没人看见。

<!-- ==================== 失眠 - 平静 ==================== -->
<!-- key: night_insomnia_calm_listen | sleep_status:insomnia | emotion:calm -->
【TEXT】
听着窗外偶尔经过的车声，安安静静地躺着。睡不着也没关系，不用逼自己。

<!-- key: night_insomnia_calm_breath | sleep_status:insomnia | emotion:calm -->
【TEXT】
深呼吸。一下，两下。睡不着就睡不着，闭着眼休息也是好的。

<!-- key: night_insomnia_calm_thinking | sleep_status:insomnia | emotion:calm -->
【TEXT】
有些想法只有在深夜才会冒出来，不吵不闹，就安安静静待在那里。

<!-- ==================== 失眠 - 孤独 ==================== -->
<!-- key: night_insomnia_lonely_general | sleep_status:insomnia | emotion:lonely -->
【TEXT】
手机很安静，没有人找你。不过没关系，深夜本来就是一个人的时间。

<!-- key: night_insomnia_lonely_miss | sleep_status:insomnia | emotion:lonely | trauma_min:50 -->
【TEXT】
想起了一些很久没联系的人。不知道他们现在好不好。

<!-- key: night_insomnia_lonely_quiet | sleep_status:insomnia | emotion:lonely -->
【TEXT】
房间很安静，安静到能听见自己的心跳声。

<!-- ==================== 浅眠 - 焦虑 ==================== -->
<!-- key: night_restless_anxious_noise | sleep_status:restless | emotion:anxious -->
【TEXT】
睡得很浅，一点声音就醒了。心里好像总悬着什么事。

<!-- key: night_restless_anxious_half | sleep_status:restless | emotion:anxious -->
【TEXT】
半梦半醒之间，感觉睡了又好像没睡。明天起来应该会很累吧。

<!-- key: night_restless_anxious_dream | sleep_status:restless | emotion:anxious -->
【TEXT】
做了乱七八糟的梦，醒了就记不清了，只留下心慌慌的感觉。

<!-- ==================== 浅眠 - 难过 ==================== -->
<!-- key: night_restless_sad_mood | sleep_status:restless | emotion:sad -->
【TEXT】
醒来的时候心情沉沉的，好像梦里也在难过。

<!-- key: night_restless_sad_tired | sleep_status:restless | emotion:sad | fatigue_min:60 -->
【TEXT】
身体很疲惫，但睡眠没有把你接住。没关系，明天可以再休息。

<!-- key: night_restless_sad_blank | sleep_status:restless | emotion:sad -->
【TEXT】
醒过来的那几秒，脑子里一片空白，不知道自己在哪。

<!-- ==================== 浅眠 - 平静 ==================== -->
<!-- key: night_restless_calm_gentle | sleep_status:restless | emotion:calm -->
【TEXT】
虽然睡得不沉，但也算是休息了。慢慢来，不用要求自己睡得完美。

<!-- key: night_restless_calm_stretch | sleep_status:restless | emotion:calm -->
【TEXT】
翻了个身，拉了拉被子。还能再睡一会儿。

<!-- key: night_restless_calm_window | sleep_status:restless | emotion:calm -->
【TEXT】
天快亮了吗？窗外的颜色好像变浅了一点。

<!-- ==================== 浅眠 - 孤独 ==================== -->
<!-- key: night_restless_lonely_awake | sleep_status:restless | emotion:lonely -->
【TEXT】
醒了，房间里只有你一个人。但这没什么，一个人也可以很安心。

<!-- key: night_restless_lonely_pillow | sleep_status:restless | emotion:lonely -->
【TEXT】
枕头有点凉。翻个身，找个舒服的姿势继续躺着。

<!-- ==================== 深睡 - 平静治愈 ==================== -->
<!-- key: night_deep_calm_healing | sleep_status:deep | emotion:calm -->
【TEXT】
今晚睡得很沉，好像什么都放下了。真好。

<!-- key: night_deep_calm_reset | sleep_status:deep | emotion:calm -->
【TEXT】
一夜无梦，醒来的时候心里很平静。就像电脑重启了一样。

<!-- key: night_deep_calm_warm | sleep_status:deep | emotion:calm -->
【TEXT】
睡得很安稳，身体和心灵都得到了休息。

<!-- key: night_deep_calm_quiet | sleep_status:deep | emotion:calm -->
【TEXT】
这一夜，没有打扰，只有安静。

<!-- key: night_deep_calm_light | sleep_status:deep | emotion:calm -->
【TEXT】
好好睡了一觉，感觉整个人都轻盈了。

<!-- key: night_deep_calm_nodream | sleep_status:deep | emotion:calm -->
【TEXT】
梦里很温暖，醒来的时候心情很好。

<!-- key: night_deep_calm_nothing | sleep_status:deep | emotion:calm -->
【TEXT】
没有什么事情需要急着做。这样的夜晚，很珍贵。

<!-- key: night_deep_calm_body | sleep_status:deep | emotion:calm | fatigue_min:60 -->
【TEXT】
身体在修复，心里在放松。这样就很好。

<!-- ==================== 正常睡眠 ==================== -->
<!-- key: night_asleep_calm_tired | sleep_status:asleep | emotion:calm | fatigue_min:60 -->
【TEXT】
今天有点累，躺下很快就睡着了。

<!-- key: night_asleep_calm_worry | sleep_status:asleep | emotion:calm | anxiety_min:40 -->
【TEXT】
睡前有点担心明天的事，但睡着了就忘了。

<!-- key: night_asleep_calm_body | sleep_status:asleep | emotion:calm | fatigue_min:50 -->
【TEXT】
身体有点沉，睡眠还可以。

<!-- key: night_asleep_calm_fade | sleep_status:asleep | emotion:calm | fatigue_min:50 -->
【TEXT】
今天的疲惫，在睡眠里慢慢消散了。

<!-- key: night_asleep_calm_morning | sleep_status:asleep | emotion:calm | fatigue_min:40 -->
【TEXT】
一觉醒来，感觉比睡前轻松了一些。

<!-- key: night_asleep_calm_ok | sleep_status:asleep | emotion:calm -->
【TEXT】
今晚睡得还可以，不算特别沉，但也够了。

<!-- key: night_asleep_calm_plain | sleep_status:asleep | emotion:calm -->
【TEXT】
没有什么特别的梦，平平淡淡的一夜。

<!-- key: night_asleep_calm_fast | sleep_status:asleep | emotion:calm -->
【TEXT】
躺下没多久就睡着了，挺好的。

<!-- key: night_asleep_calm_wake | sleep_status:asleep | emotion:calm -->
【TEXT】
夜里醒了一次，但很快又睡着了。

<!-- key: night_asleep_calm_enough | sleep_status:asleep | emotion:calm -->
【TEXT】
睡眠质量一般，但身体得到了休息。没关系，这样就够了。

<!-- ==================== 梦境 - 平静美梦 ==================== -->
<!-- key: dream_peaceful_01 | dream_type:peaceful -->
【梦境内容】
梦见了一片很大的草地，风很软，阳光不晒。你就躺在那里，什么都不用做。
【emoji】
🌿

<!-- key: dream_peaceful_02 | dream_type:peaceful -->
【梦境内容】
梦见和很久没见的朋友一起吃饭，大家都没变，还是嘻嘻哈哈的。
【emoji】
🍜

<!-- key: dream_peaceful_03 | dream_type:peaceful -->
【梦境内容】
梦见小时候住的地方，院子里的花开了，奶奶在叫你回家吃饭。
【emoji】
🏡

<!-- key: dream_peaceful_04 | dream_type:peaceful -->
【梦境内容】
梦见在海边走，浪一下一下拍过来，声音很规律，心里特别安静。
【emoji】
🌊

<!-- key: dream_peaceful_05 | dream_type:peaceful -->
【梦境内容】
梦见一只猫蹭你的手，呼噜呼噜的，特别软特别暖。
【emoji】
🐱

<!-- key: dream_peaceful_06 | dream_type:peaceful -->
【梦境内容】
梦见飘在云上，什么重量都没有，就那么轻轻飘着。
【emoji】
☁️

<!-- key: dream_peaceful_07 | dream_type:peaceful -->
【梦境内容】
梦见下着小雨，你在屋里听着雨声，盖着被子，很安心。
【emoji】
🌧️

<!-- ==================== 梦境 - 焦虑噩梦 ==================== -->
<!-- key: dream_anxious_01 | dream_type:anxious -->
【梦境内容】
梦见有什么东西在追你，你跑啊跑，怎么也跑不快。醒过来的时候心跳很快。
【emoji】
😰

<!-- key: dream_anxious_02 | dream_type:anxious -->
【梦境内容】
梦见要考试/要汇报，但你什么都没准备，脑子一片空白。
【emoji】
📝

<!-- key: dream_anxious_03 | dream_type:anxious -->
【梦境内容】
梦见迷路了，怎么也找不到回家的路，周围都是陌生的。
【emoji】
🚶

<!-- key: dream_anxious_04 | dream_type:anxious -->
【梦境内容】
梦见手机/钱包丢了，怎么找都找不到。
【emoji】
😟

<!-- key: dream_anxious_05 | dream_type:anxious -->
【梦境内容】
梦见和人吵架，明明你是对的，但怎么都说不出话来。
【emoji】
😶

<!-- key: dream_anxious_06 | dream_type:anxious -->
【梦境内容】
梦见从高处掉下来，一直往下坠，然后就醒了。
【emoji】
💫

<!-- key: dream_anxious_07 | dream_type:anxious -->
【梦境内容】
梦见牙齿掉了，一颗一颗，吐也吐不完。
【emoji】
😬

<!-- ==================== 梦境 - 回忆梦境 ==================== -->
<!-- key: dream_memory_01 | dream_type:memory -->
【梦境内容】
梦见了学生时代的教室，阳光从窗户照进来，粉笔灰在光里飘。
【emoji】
📚

<!-- key: dream_memory_02 | dream_type:memory -->
【梦境内容】
梦见曾经住过的老房子，家具还是记忆里的位置。
【emoji】
🚪

<!-- key: dream_memory_03 | dream_type:memory -->
【梦境内容】
梦见年轻时候的爸爸妈妈，他们还没有白头发。
【emoji】
👨‍👩‍👧

<!-- key: dream_memory_04 | dream_type:memory -->
【梦境内容】
梦见和某个人走在放学/下班的路上，这条路好像走了无数遍。
【emoji】
🛤️

<!-- key: dream_memory_05 | dream_type:memory -->
【梦境内容】
梦见一个很久没想起过的人，醒来的时候有点恍惚。
【emoji】
🌙

<!-- key: dream_memory_06 | dream_type:memory -->
【梦境内容】
梦见以前养过的小动物，它还是记忆里小小的样子。
【emoji】
🐾

<!-- ==================== 梦境 - 童年旧梦 ==================== -->
<!-- key: dream_childhood_01 | dream_type:childhood -->
【梦境内容】
梦见夏天的午后，电风扇呼呼转，你在地上玩积木。
【emoji】
🧱

<!-- key: dream_childhood_02 | dream_type:childhood -->
【梦境内容】
梦见放学背着书包去小卖部买冰棍，五毛钱一根。
【emoji】
🍦

<!-- key: dream_childhood_03 | dream_type:childhood -->
【梦境内容】
梦见过年放烟花，你捂着耳朵抬头看，火星掉下来很好看。
【emoji】
🎆

<!-- key: dream_childhood_04 | dream_type:childhood -->
【梦境内容】
梦见在河里/溪边玩水，鞋子湿了不敢回家。
【emoji】
💦

<!-- key: dream_childhood_05 | dream_type:childhood -->
【梦境内容】
梦见和小伙伴捉迷藏，你躲在柜子里，听着外面的脚步声偷笑。
【emoji】
🙈

<!-- key: dream_childhood_06 | dream_type:childhood -->
【梦境内容】
梦见坐在妈妈/外婆的自行车后座，风把头发吹起来。
【emoji】
🚲

<!-- ==================== 梦境 - 预言感 ==================== -->
<!-- key: dream_prophetic_01 | dream_type:prophetic -->
【梦境内容】
梦里看见一扇门，你没有推开。醒来之后总觉得好像哪里不一样了。
【emoji】
✨

<!-- key: dream_prophetic_02 | dream_type:prophetic -->
【梦境内容】
梦见一个陌生人对你说了一句话，醒了之后怎么也想不起来内容，但记得那个语气。
【emoji】
🔮

<!-- key: dream_prophetic_03 | dream_type:prophetic -->
【梦境内容】
梦见天上有很亮的星星，你抬头看的时候，它好像动了一下。
【emoji】
⭐

<!-- key: dream_prophetic_04 | dream_type:prophetic -->
【梦境内容】
梦里你走到一个地方，感觉很熟悉，但现实中你从来没去过。
【emoji】
🌌

<!-- key: dream_prophetic_05 | dream_type:prophetic -->
【梦境内容】
梦见自己站在十字路口，选了一条没走过的路。
【emoji】
🛣️
