#!/usr/bin/env python3
"""
Fix 43 entries with missing data:
- 2 entries (点石成金, 长生不老): add spotlights
- 41 entries: add cognitiveBiasTags
"""
import json

INPUT = 'app/src/main/assets/idioms/idioms.json'

with open(INPUT, 'r', encoding='utf-8') as f:
    data = json.load(f)

# ============================================
# 1. Add spotlights to 点石成金 and 长生不老
# ============================================
spotlight_fixes = {
    '点石成金': [
        {
            "category": "COGNITION",
            "weight": 1.0,
            "text": "暴富神话的本质是幸存者偏差——你看到的是中了彩票的人，看不到的是千万个没中的人"
        },
        {
            "category": "CONSUMPTION",
            "weight": 0.9,
            "text": "成功学课程和财富能量课贩卖的不是方法，而是焦虑本身——为'秘密'付费的瞬间，你已经成为收割对象"
        },
        {
            "category": "INTERPERSONAL",
            "weight": 0.85,
            "text": "将他人的成功简单归因于'运气'或'秘密方法'，掩盖了长期积累和结构性条件，也让自己放弃了真正的行动"
        }
    ],
    '长生不老': [
        {
            "category": "COGNITION",
            "weight": 1.0,
            "text": "长寿个案的遗传背景和生活环境不可复制，将其推广为普遍有效的'秘籍'是过度概括"
        },
        {
            "category": "CONSUMPTION",
            "weight": 0.9,
            "text": "保健品和抗衰产业将衰老病理化，让正常生理过程变成需要付费解决的'问题'——恐惧是最好的销售员"
        },
        {
            "category": "EMOTION",
            "weight": 0.85,
            "text": "对死亡的恐惧被商业化为健康焦虑，焦虑本身比自然衰老更损害健康"
        }
    ]
}

# ============================================
# 2. Add cognitiveBiasTags to 41 entries
# ============================================
tag_fixes = {
    # TOWN_SYSTEM entries (extracted from distortedTruth)
    '均时同命': ['控制错觉', '计划谬误'],
    '劳尽财空': ['双曲贴现', '幸存者偏差'],
    '复利长流': ['指数增长偏见', '过度自信'],
    '身心同源': ['身心二元论', '过度心理化'],
    '夜不成眠': ['归因谬误', '反刍思维'],
    '闲暇疗愈': ['产出偏见', '休息内疚'],
    '日积月累': ['沉没成本谬误', '线性外推偏差'],
    '各安其命': ['习得性无助', '外部控制点'],
    '时区各异': ['社会时钟焦虑', '从众内卷偏差'],
    '吃苦非福': ['道德资本化', '结构性转嫁'],
    '铁碗易锈': ['损失厌恶', '现状偏见'],
    '框架囚徒': ['定势效应', '叙事陷阱'],
    '边界非忍': ['边界模糊', '怨恨积累'],
    '社交非量': ['邓巴数', 'FOMO'],
    '开源节流': ['稀缺心态', '心理账户'],
    '主动争取': ['自利归因', '行动偏见'],
    # COGNITION entries
    '锚定效应': ['锚定效应', '调整不足'],
    '框架效应': ['框架效应', '语义启动'],
    '邓宁-克鲁格效应': ['达克效应', '元认知缺陷'],
    # INTERPERSONAL entries
    '伸手不打笑脸人': ['互惠规范偏差', '情绪归因错误', '沉没成本谬误'],
    '人情世故': ['讨好型人格', '情绪内耗'],
    '鸡同鸭讲': ['确认偏误', '自我服务偏差', '基本归因错误'],
    '君子之交淡如水': ['过度概括', '情感懒惰'],
    '逢人只说三分话': ['过度概括', '信任泛化'],
    '家家有本难念的经': ['苦难正常化', '倾诉否定'],
    '父母不懂我': ['确认偏误', '知识诅咒', '基本归因错误'],
    # RELATIONSHIP entries
    '家和万事兴': ['权威服从偏差', '非黑即白思维'],
    '夫妻没有隔夜仇': ['情绪压抑', '快速翻篇强迫'],
    # METHOD entries
    '三思而后行': ['损失厌恶', '过度思考'],
    '车到山前必有路': ['幸存者偏差', '过度概括'],
    '退一步海阔天空': ['非黑即白思维', '过度概括'],
    '授人以鱼不如授人以渔': ['非黑即白思维', '过度概括'],
    # SOCIETY entries
    '坐井观天': ['确认偏误', '基本归因错误', '聚光灯效应'],
    '夏虫不可语冰': ['自我中心偏差', '基本归因错误', '可得性启发'],
    '公说公有理，婆说婆有理': ['相对主义谬误', '双重标准'],
    '千人千面': ['本质主义谬误', '独特性错觉'],
    '众人皆醉我独醒': ['优越感动机', '确认偏误', '达克效应'],
    '行万里路，读万卷书': ['鲜活效应', '认知吝啬'],
    # COGNITION entries
    '塞翁失马焉知非福': ['幸存者偏差', '过度概括'],
    # CHARACTER entries
    '以德报怨': ['道德绑架谬误', '沉没成本谬误', '习得性无助'],
    '己所不欲勿施于人': ['道德绑架谬误', '双重标准'],
}

# ============================================
# Apply fixes
# ============================================
fixed_spotlights = 0
fixed_tags = 0

for entry in data:
    idiom_name = entry['idiom']

    # Fix spotlights
    if idiom_name in spotlight_fixes and not entry.get('spotlights'):
        entry['spotlights'] = spotlight_fixes[idiom_name]
        fixed_spotlights += 1
        print(f'  [spotlight] {idiom_name}: +{len(entry["spotlights"])} spotlights')

    # Fix cognitiveBiasTags
    if idiom_name in tag_fixes and not entry.get('cognitiveBiasTags'):
        entry['cognitiveBiasTags'] = tag_fixes[idiom_name]
        fixed_tags += 1
        print(f'  [tags] {idiom_name}: {entry["cognitiveBiasTags"]}')

print(f'\n=== Summary ===')
print(f'Spotlights fixed: {fixed_spotlights}')
print(f'Tags fixed: {fixed_tags}')

# Verify no empty spotlights or tags remain
empty_spots = [e['idiom'] for e in data if not e.get('spotlights')]
empty_tags = [e['idiom'] for e in data if not e.get('cognitiveBiasTags')]
print(f'\nRemaining empty spotlights: {len(empty_spots)} {empty_spots}')
print(f'Remaining empty tags: {len(empty_tags)} {empty_tags}')

# Write back
with open(INPUT, 'w', encoding='utf-8') as f:
    json.dump(data, f, ensure_ascii=False, indent=2)

print(f'\nDone! Total entries: {len(data)}')
