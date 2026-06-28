# -*- coding: utf-8 -*-
"""
深入调查审计发现的问题
"""
import json
from collections import Counter, defaultdict

with open('app/src/main/assets/idioms/idioms.json', encoding='utf-8') as f:
    data = json.load(f)

# ========================
# 问题1：examples格式不一致
# ========================
print('='*60)
print('问题1: examples格式不一致')
print('='*60)

bad_examples = []
good_examples = []
weird_examples = []
for item in data:
    exs = item.get('examples', [])
    if not exs:
        weird_examples.append((item['idiom'], '空'))
        continue
    
    # 检查类型
    if isinstance(exs, str):
        weird_examples.append((item['idiom'], f'字符串类型: {repr(exs[:50])}'))
        continue
    
    if not isinstance(exs, list):
        weird_examples.append((item['idiom'], f'非list类型: {type(exs).__name__}'))
        continue
    
    # 检查list元素
    first = exs[0] if len(exs) > 0 else None
    if first is None:
        weird_examples.append((item['idiom'], '空list'))
    elif isinstance(first, str) and len(first) <= 2:
        bad_examples.append(item['idiom'])
    elif isinstance(first, str) and len(first) >= 3:
        good_examples.append(item['idiom'])
    elif isinstance(first, dict):
        weird_examples.append((item['idiom'], f'dict类型: {list(first.keys())}'))
    else:
        weird_examples.append((item['idiom'], f'未知元素类型: {type(first).__name__}'))

print(f'正常examples(字符串列表): {len(good_examples)}条')
print(f'异常examples(字符拆分): {len(bad_examples)}条')
print(f'其他异常: {len(weird_examples)}条')

if bad_examples:
    print(f'\n字符拆分异常词条({len(bad_examples)}):')
    for t in bad_examples:
        print(f'  {t}')

# 查看一个异常词条
print(f'\n异常示例:')
for item in data:
    if item['idiom'] in bad_examples[:1]:
        exs = item.get('examples', [])
        print(f'  {item["idiom"]}: type={type(exs).__name__}, len={len(exs)}, first5={exs[:10]}')
        break

# 查看其他异常
if weird_examples:
    print(f'\n其他异常({len(weird_examples)}):')
    for t, reason in weird_examples[:20]:
        print(f'  {t}: {reason}')

print(f'\n正常示例:')
for item in data:
    if item['idiom'] == '智商':
        exs = item.get('examples', [])
        print(f'  智商: type={type(exs).__name__}, len={len(exs)}')
        for e in exs[:2]:
            print(f'    {repr(e)[:100]}')
        break

# ========================
# 问题2：spotlights结构不一致
# ========================
print(f'\n{"="*60}')
print('问题2: spotlights结构不一致')
print('='*60)

bad_spotlights = []
good_spotlights = []
for item in data:
    spots = item.get('spotlights', [])
    if not spots:
        bad_spotlights.append((item['idiom'], '空'))
        continue
    
    first = spots[0]
    if isinstance(first, dict):
        if 'category' in first and 'content' in first:
            good_spotlights.append(item['idiom'])
        else:
            bad_spotlights.append((item['idiom'], f'dict缺字段: {list(first.keys())}'))
    elif isinstance(first, str):
        bad_spotlights.append((item['idiom'], f'字符串类型: {repr(first)[:60]}'))
    else:
        bad_spotlights.append((item['idiom'], f'类型: {type(first).__name__}'))

print(f'正常spotlights(dict+完整字段): {len(good_spotlights)}条')
print(f'异常spotlights: {len(bad_spotlights)}条')

# 统计异常类型
bad_types = Counter()
for _, reason in bad_spotlights:
    if '字符串' in reason:
        bad_types['字符串类型'] += 1
    elif '空' in reason:
        bad_types['空'] += 1
    elif '缺字段' in reason:
        bad_types['dict缺字段'] += 1
    else:
        bad_types[reason] += 1
print(f'异常类型:')
for k, v in bad_types.most_common():
    print(f'  {k}: {v}')

# 查看字符串类型spotlight示例
print(f'\n字符串类型spotlight示例:')
count = 0
for item in data:
    spots = item.get('spotlights', [])
    if spots and isinstance(spots[0], str):
        print(f'  {item["idiom"]}: {spots[:3]}')
        count += 1
        if count >= 5:
            break

# 查看dict缺字段示例
print(f'\ndict缺字段spotlight示例:')
count = 0
for item in data:
    spots = item.get('spotlights', [])
    if spots and isinstance(spots[0], dict) and ('category' not in spots[0] or 'content' not in spots[0]):
        print(f'  {item["idiom"]}: keys={list(spots[0].keys())}')
        count += 1
        if count >= 5:
            break

# 查看正常示例
print(f'\n正常spotlight示例:')
for item in data:
    spots = item.get('spotlights', [])
    if spots and isinstance(spots[0], dict) and 'category' in spots[0] and 'content' in spots[0]:
        print(f'  {item["idiom"]}: {json.dumps(spots[0], ensure_ascii=False)[:120]}')
        break

# ========================
# 问题3：枚举值两套系统
# ========================
print(f'\n{"="*60}')
print('问题3: 枚举值两套系统')
print('='*60)

en_cats = Counter()
cn_cats = Counter()
other_cats = Counter()
en_cat_items = []
for item in data:
    cat = item.get('category', '')
    if cat in ['SOCIETY', 'COGNITION', 'CONSUMPTION', 'INTERPERSONAL', 'TOWN_SYSTEM', 
               'WORKPLACE', 'CHARACTER', 'RELATIONSHIP', 'METHOD', 'HERITAGE', 'EMOTION']:
        en_cats[cat] += 1
        en_cat_items.append(item['idiom'])
    elif cat in ['底层认知', '片面认知', '焦虑陷阱', '认知误区']:
        cn_cats[cat] += 1
    else:
        other_cats[cat] += 1

print(f'英文category枚举: {sum(en_cats.values())}条')
for k, v in en_cats.most_common():
    print(f'  {k}: {v}')
print(f'中文category枚举: {sum(cn_cats.values())}条')
for k, v in cn_cats.most_common():
    print(f'  {k}: {v}')
if other_cats:
    print(f'其他: {sum(other_cats.values())}条')
    for k, v in other_cats.most_common():
        print(f'  {k}: {v}')

# 同样检查toxicityLevel
print(f'\ntoxicityLevel:')
en_tox = Counter()
cn_tox = Counter()
for item in data:
    tox = item.get('toxicityLevel', '')
    if tox in ['DISTORTED', 'HARMFUL', 'POISONOUS', 'TOWN_WISDOM']:
        en_tox[tox] += 1
    elif tox in ['底层认知', '片面认知', '焦虑陷阱', '认知误区']:
        cn_tox[tox] += 1
    else:
        print(f'  未知: {tox}')
print(f'  英文: {sum(en_tox.values())}条 -> {dict(en_tox)}')
print(f'  中文: {sum(cn_tox.values())}条 -> {dict(cn_tox)}')

# ========================
# 问题4：townPerspective前缀格式
# ========================
print(f'\n{"="*60}')
print('问题4: townPerspective前缀格式')
print('='*60)

import re
prefix_pattern = re.compile(r'^(唯物史观[：:]|现代社会结构批判[：:]|认知心理学视角[：:])')

has_prefix = []
no_prefix = []
for item in data:
    tp = item.get('townPerspective', [])
    if isinstance(tp, str):
        tp = [tp]
    if any(prefix_pattern.match(p) for p in tp):
        has_prefix.append(item['idiom'])
    else:
        no_prefix.append(item['idiom'])

print(f'有前缀段落: {len(has_prefix)}条')
print(f'无前缀段落: {len(no_prefix)}条')

# 查看无前缀词条的townPerspective
print(f'\n无前缀词条示例(前5个):')
shown = 0
for item in data:
    if item['idiom'] in no_prefix:
        tp = item.get('townPerspective', [])
        if isinstance(tp, str):
            tp = [tp]
        print(f'\n  [{item["idiom"]}] ({len(tp)}段):')
        for p in tp[:3]:
            print(f'    {p[:100]}')
        shown += 1
        if shown >= 5:
            break

# ========================
# 问题5：cognitiveBiasTags空
# ========================
print(f'\n{"="*60}')
print('问题5: cognitiveBiasTags空')
print('='*60)
empty_tags = []
for item in data:
    tags = item.get('cognitiveBiasTags', [])
    if not tags or (len(tags) == 1 and not tags[0]):
        empty_tags.append(item['idiom'])
print(f'空tags: {len(empty_tags)}条')
for t in empty_tags:
    print(f'  {t}')

# ========================
# 问题6：distortedTruth过短
# ========================
print(f'\n{"="*60}')
print('问题6: distortedTruth过短')
print('='*60)
for item in data:
    dt = item.get('distortedTruth', '')
    if len(dt) < 10:
        print(f'  {item["idiom"]}: {repr(dt)} ({len(dt)}字)')
