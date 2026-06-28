# -*- coding: utf-8 -*-
"""
Vera词条质量审计脚本
检查维度：
1. 字段完整性
2. 三层结构完整性（唯物史观/社会结构批判/认知心理学）
3. townPerspective格式一致性
4. 内容方向检查（是否偏离唯物辩证法）
5. 枚举值一致性
6. spotlights结构检查
7. cognitiveBiasTags非空检查
8. 字段内容质量检查（过短/过长的字段）
"""
import json
import re
from collections import Counter, defaultdict

with open('app/src/main/assets/idioms/idioms.json', encoding='utf-8') as f:
    data = json.load(f)

total = len(data)
print(f'=== 总词条数: {total} ===\n')

# ========================
# 1. 字段完整性检查
# ========================
required_fields = [
    'id', 'idiom', 'entryType', 'traditionalMeaning', 'distortedTruth',
    'townPerspective', 'spotlights', 'category', 'toxicityLevel',
    'keyMessage', 'examples', 'actionableTip', 'cognitiveBiasTags'
]

field_issues = []
for item in data:
    missing = [f for f in required_fields if f not in item]
    if missing:
        field_issues.append((item.get('idiom', '???'), missing))

print(f'1. 字段完整性: {"全部通过" if not field_issues else f"发现{len(field_issues)}个问题"}')
for title, missing in field_issues:
    print(f'   {title} 缺: {missing}')

# ========================
# 2. 三层结构完整性
# ========================
layer_keywords = {
    '唯物史观': ['唯物史观', '物质生产', '经济基础', '生产方式', '前工业', '前现代', '生产关系', '封建', '小农', '雇佣', '资本主义', '资本增殖', '市场经济'],
    '社会结构批判': ['社会结构批判', '社会结构', '结构性', '阶级', '劳资', '资源分配', '分配不均', '意识形态', '规训', '掩盖', '转移', '消费主义', '产业化', '内卷'],
    '认知心理学': ['认知心理学', '认知偏差', '归因谬误', '归因', '标签化', '事后归因', '片面', '自我否定', '内耗', '认知误区', '二分法', '二元对立', '选择性']
}

layer_issues = []
layer_stats = {'full_3_layers': 0, 'missing_1_layer': 0, 'missing_2_layers': 0, 'missing_3_layers': 0}

for item in data:
    tp = item.get('townPerspective', [])
    if isinstance(tp, str):
        tp = [tp]
    full_text = ' '.join(tp) if tp else ''
    
    found_layers = []
    for layer_name, keywords in layer_keywords.items():
        if any(kw in full_text for kw in keywords):
            found_layers.append(layer_name)
    
    if len(found_layers) == 3:
        layer_stats['full_3_layers'] += 1
    elif len(found_layers) == 2:
        layer_stats['missing_1_layer'] += 1
        missing_layer = [l for l in layer_keywords if l not in found_layers]
        layer_issues.append((item['idiom'], f'缺{missing_layer[0]}层'))
    elif len(found_layers) == 1:
        layer_stats['missing_2_layers'] += 1
        missing_layers = [l for l in layer_keywords if l not in found_layers]
        layer_issues.append((item['idiom'], f'缺{missing_layers}层'))
    else:
        layer_stats['missing_3_layers'] += 1
        layer_issues.append((item['idiom'], '三层全缺'))

print(f'\n2. 三层结构完整性:')
print(f'   完整三层: {layer_stats["full_3_layers"]} ({layer_stats["full_3_layers"]*100//total}%)')
print(f'   缺一层: {layer_stats["missing_1_layer"]} ({layer_stats["missing_1_layer"]*100//total}%)')
print(f'   缺两层: {layer_stats["missing_2_layers"]} ({layer_stats["missing_2_layers"]*100//total}%)')
print(f'   缺三层: {layer_stats["missing_3_layers"]} ({layer_stats["missing_3_layers"]*100//total}%)')
if layer_issues:
    print(f'   问题词条:')
    for title, issue in layer_issues[:30]:
        print(f'     {title}: {issue}')
    if len(layer_issues) > 30:
        print(f'     ... 还有{len(layer_issues)-30}条')

# ========================
# 3. townPerspective 段落前缀检查
# ========================
prefix_pattern = re.compile(r'^(唯物史观[：:](?!：)|现代社会结构批判[：:](?!：)|认知心理学视角[：:](?!：))')
prefix_issues = []
prefix_stats = Counter()

for item in data:
    tp = item.get('townPerspective', [])
    if isinstance(tp, str):
        tp = [tp]
    if not tp:
        prefix_issues.append((item['idiom'], 'townPerspective为空'))
        continue
    
    for para in tp:
        if prefix_pattern.match(para):
            prefix = prefix_pattern.match(para).group(1).rstrip('：:')
            prefix_stats[prefix] += 1
        else:
            # 检查是否是总结性段落（不以三层前缀开头是合理的）
            pass
    
    # 检查是否至少有一个三层前缀段落
    has_prefix = any(prefix_pattern.match(p) for p in tp)
    if not has_prefix and len(tp) > 0:
        prefix_issues.append((item['idiom'], '无三层前缀段落'))

print(f'\n3. townPerspective前缀格式:')
print(f'   段落前缀统计:')
for prefix, count in prefix_stats.most_common():
    print(f'     {prefix}: {count}段')
if prefix_issues:
    print(f'   无前缀段落词条: {len(prefix_issues)}条')
    for title, issue in prefix_issues[:20]:
        print(f'     {title}: {issue}')
else:
    print(f'   全部词条至少包含一个三层前缀段落')

# ========================
# 4. 枚举值一致性
# ========================
print(f'\n4. 枚举值分布:')
cats = Counter(item.get('category', '') for item in data)
toxs = Counter(item.get('toxicityLevel', '') for item in data)
ets = Counter(item.get('entryType', 'IDIOM') for item in data)

print(f'   category:')
for k, v in cats.most_common():
    print(f'     {k}: {v}')
print(f'   toxicityLevel:')
for k, v in toxs.most_common():
    print(f'     {k}: {v}')
print(f'   entryType:')
for k, v in ets.most_common():
    print(f'     {k}: {v}')

# 检查是否有不在标准枚举中的值
valid_cats = {'认知误区', '焦虑陷阱', '片面认知', '底层认知'}
valid_toxs = {'认知误区', '焦虑陷阱', '片面认知', '底层认知'}
invalid_cats = [k for k in cats if k not in valid_cats]
invalid_toxs = [k for k in toxs if k not in valid_toxs]
if invalid_cats:
    print(f'   ⚠ 非标准category值: {invalid_cats}')
if invalid_toxs:
    print(f'   ⚠ 非标准toxicityLevel值: {invalid_toxs}')

# ========================
# 5. spotlights结构检查
# ========================
spotlight_issues = []
spotlight_cats = Counter()
for item in data:
    spots = item.get('spotlights', [])
    if not spots:
        spotlight_issues.append((item['idiom'], 'spotlights为空'))
    for s in spots:
        if 'category' not in s or 'content' not in s:
            spotlight_issues.append((item['idiom'], 'spotlight缺字段'))
        spotlight_cats[s.get('category', '')] += 1

print(f'\n5. spotlights结构:')
print(f'   spotlight category分布:')
for k, v in spotlight_cats.most_common():
    print(f'     {k}: {v}')
if spotlight_issues:
    print(f'   问题: {len(spotlight_issues)}条')
    for title, issue in spotlight_issues[:10]:
        print(f'     {title}: {issue}')

# ========================
# 6. 字段内容质量检查（长度异常）
# ========================
length_issues = []
for item in data:
    title = item['idiom']
    tm = item.get('traditionalMeaning', '')
    dt = item.get('distortedTruth', '')
    km = item.get('keyMessage', '')
    at = item.get('actionableTip', '')
    
    if len(tm) < 10:
        length_issues.append((title, f'traditionalMeaning过短({len(tm)}字)'))
    if len(dt) < 10:
        length_issues.append((title, f'distortedTruth过短({len(dt)}字)'))
    if len(km) < 5:
        length_issues.append((title, f'keyMessage过短({len(km)}字)'))
    if len(at) < 10:
        length_issues.append((title, f'actionableTip过短({len(at)}字)'))
    
    tp = item.get('townPerspective', [])
    if isinstance(tp, str):
        tp = [tp]
    total_tp_len = sum(len(p) for p in tp)
    if total_tp_len < 50:
        length_issues.append((title, f'townPerspective过短({total_tp_len}字)'))

print(f'\n6. 字段长度异常: {len(length_issues)}个问题')
for title, issue in length_issues[:20]:
    print(f'   {title}: {issue}')
if len(length_issues) > 20:
    print(f'   ... 还有{len(length_issues)-20}个')

# ========================
# 7. cognitiveBiasTags检查
# ========================
empty_tags = []
for item in data:
    tags = item.get('cognitiveBiasTags', [])
    if not tags or (len(tags) == 1 and not tags[0]):
        empty_tags.append(item['idiom'])

print(f'\n7. cognitiveBiasTags空: {len(empty_tags)}条')
if empty_tags:
    print(f'   {empty_tags[:20]}')

# ========================
# 8. ID唯一性检查
# ========================
id_list = [item['id'] for item in data]
id_dups = [k for k, v in Counter(id_list).items() if v > 1]
title_list = [item['idiom'] for item in data]
title_dups = [k for k, v in Counter(title_list).items() if v > 1]

print(f'\n8. ID/标题唯一性:')
print(f'   ID重复: {len(id_dups)}个 -> {id_dups}')
print(f'   标题重复: {len(title_dups)}个 -> {title_dups}')

# ========================
# 9. examples格式检查
# ========================
example_issues = []
for item in data:
    exs = item.get('examples', [])
    if not exs:
        example_issues.append((item['idiom'], 'examples为空'))
    for ex in exs:
        if not isinstance(ex, str) or len(ex) < 5:
            example_issues.append((item['idiom'], f'example异常: {repr(ex)[:50]}'))

print(f'\n9. examples格式: {len(example_issues)}个问题')
for title, issue in example_issues[:10]:
    print(f'   {title}: {issue}')

# ========================
# 10. 唯心归因检查 - 关键！
# ========================
# 检查是否存在唯心归因表述（把问题归因为个人性格/天赋/命运，而非社会结构）
idealistic_patterns = [
    (r'天生.{0,10}(就|注定|注定)', '天生决定论'),
    (r'(命|命运).{0,10}(决定|注定|安排)', '命运决定论'),
    (r'(性格|人格).{0,10}(决定|注定|导致).{0,10}(人生|命运|成败)', '性格决定论'),
    (r'(基因|血型|星座).{0,10}(决定|影响).{0,10}(性格|命运|能力)', '基因/星座决定论'),
]

idealistic_issues = []
for item in data:
    # 检查全文本（排除traditionalMeaning，因为传统释义可能引用民间说法）
    texts_to_check = [
        ('distortedTruth', item.get('distortedTruth', '')),
        ('keyMessage', item.get('keyMessage', '')),
        ('actionableTip', item.get('actionableTip', '')),
    ]
    tp = item.get('townPerspective', [])
    if isinstance(tp, str):
        tp = [tp]
    for p in tp:
        texts_to_check.append(('townPerspective', p))
    
    for field, text in texts_to_check:
        for pattern, label in idealistic_patterns:
            if re.search(pattern, text):
                # 需要检查是否在批判而非认同
                # 简单启发：如果前后有"不是""并非""错误"等否定词，可能是批判性引用
                context = text[max(0, text.find(re.search(pattern, text).group())-20):text.find(re.search(pattern, text).group())+30]
                if not any(neg in context for neg in ['不是', '并非', '错误', '谬误', '误区', '不应该', '不能简单']):
                    idealistic_issues.append((item['idiom'], field, label, context[:60]))

print(f'\n10. 唯心归因检查（关键）: {len(idealistic_issues)}个疑似问题')
for title, field, label, ctx in idealistic_issues[:20]:
    print(f'   {title} [{field}] {label}: ...{ctx}...')

# ========================
# 总结
# ========================
print(f'\n{"="*60}')
print(f'审计总结 ({total}条)')
print(f'{"="*60}')
print(f'字段完整性: {"✅ 通过" if not field_issues else f"❌ {len(field_issues)}个问题"}')
print(f'三层结构完整: {layer_stats["full_3_layers"]}/{total} ({layer_stats["full_3_layers"]*100//total}%)')
print(f'枚举值一致: {"✅ 通过" if not invalid_cats and not invalid_toxs else "❌ 有异常值"}')
print(f'spotlights: {"✅ 通过" if not spotlight_issues else f"❌ {len(spotlight_issues)}个问题"}')
print(f'字段长度: {"✅ 通过" if not length_issues else f"⚠ {len(length_issues)}个问题"}')
print(f'cognitiveBiasTags: {"✅ 全部非空" if not empty_tags else f"⚠ {len(empty_tags)}条为空"}')
print(f'ID/标题唯一: {"✅ 通过" if not id_dups and not title_dups else "❌ 有重复"}')
print(f'examples: {"✅ 通过" if not example_issues else f"❌ {len(example_issues)}个问题"}')
print(f'唯心归因: {"✅ 未发现" if not idealistic_issues else f"⚠ {len(idealistic_issues)}个疑似"}')
