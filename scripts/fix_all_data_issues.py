#!/usr/bin/env python3
"""
Fix all data format issues found in audit:
1. townPerspective: list -> string (joined with \n\n)
2. distortedTruth: list -> string (joined with \n\n)
3. examples: str/dict -> list of strings
4. spotlight: add weight field where missing
5. category: Chinese displayName -> English enum name
6. toxicityLevel: Chinese displayName -> English enum name
"""

import json
import re
from collections import Counter

INPUT = 'app/src/main/assets/idioms/idioms.json'

# === Mapping tables ===

# ToxicityLevel: Chinese displayName -> English enum name
TOXICITY_MAP = {
    '认知误区': 'POISONOUS',
    '焦虑陷阱': 'HARMFUL',
    '片面认知': 'DISTORTED',
    '传统观念': 'HERITAGE',
    '底层认知': 'TOWN_WISDOM',
}

# IdiomCategory: keyword-based mapping for the 79 problematic entries
# Priority order matters - more specific keywords first
def guess_category(idiom, entry_type):
    title = idiom
    # RELATIONSHIP (亲密关系)
    if any(k in title for k in ['亲密关系', '婚姻', '婆媳', '择偶', '早恋']):
        return 'RELATIONSHIP'
    # INTERPERSONAL (人际关系)
    if any(k in title for k in ['人际', '社交', '识人', '人情', '霸凌', '冒犯', '讨好', '人己', '人际关系', '社交策略']):
        return 'INTERPERSONAL'
    # WORKPLACE (职场围城)
    if any(k in title for k in ['职场', '职业', '向上管理', '执行力', '抗压', '会来事', '劳动', '制造业', '芯片',
                                 '智能制造', '智能驾驶', '生产工具', '劳动异化', '新能源']):
        return 'WORKPLACE'
    # CONSUMPTION (日常消费)
    if any(k in title for k in ['消费', '奢侈品', '市场', '品牌', '商业', '精致', '攀比', '资产', '投资']):
        return 'CONSUMPTION'
    # COGNITION (认知思维)
    if any(k in title for k in ['认知', '判断力', '祛魅', '偏见', '偏执', '理性', '心理学', '抑郁',
                                 '情绪', '人性', '欲望', '贪婪', '痛苦', '同情', '享乐', '现实', '悲观',
                                 '意志', '哲学', '真理', '自由', '自卑', '自尊']):
        return 'COGNITION'
    # CHARACTER (人格品德)
    if any(k in title for k in ['善良', '善恶', '美德', '勤俭', '忠义', '气质', '谦逊', '人设',
                                 '强者', '弱者', '努力', '舒适区', '驱动力', '情绪价值', '趋同',
                                 '权威', '精英', '慕强', '偶像崇拜']):
        return 'CHARACTER'
    # TOWN_SYSTEM (小镇内在)
    if any(k in title for k in ['现代人格话术', '市场经济下劳动']):
        return 'TOWN_SYSTEM'
    # SOCIETY (社会观念) - default for most macro/social/political topics
    if any(k in title for k in ['资本', '权力', '权利', '全球化', '社会', '政治', '政权', '主权',
                                 '种族', '舆论', '政治正确', '战争', '制度', '阶级', '特权',
                                 '意识形态', '生产关系', '实践', '社会主义', '现代化', '现代性',
                                 '人工智能', '实体经济', '审美', '艺术', '宗教', '自然法',
                                 '偶像', '英雄', '侠义', '忠孝', '尊师', '自强']):
        return 'SOCIETY'
    # METHOD (处事方法)
    if any(k in title for k in ['方法', '策略']):
        return 'METHOD'
    # Default by entryType
    if entry_type == 'ACADEMIC':
        return 'COGNITION'
    if entry_type == 'DILEMMA':
        return 'INTERPERSONAL'
    return 'SOCIETY'


def fix_entry(item):
    """Fix a single entry's data format issues. Returns (fixed_entry, changes_list)."""
    changes = []
    cn_cats = ['底层认知', '片面认知', '焦虑陷阱', '认知误区', '传统观念']

    # 1. Fix townPerspective: list -> string
    tp = item.get('townPerspective')
    if isinstance(tp, list):
        item['townPerspective'] = '\n\n'.join(tp)
        changes.append(f'townPerspective: list({len(tp)}) -> string')

    # 2. Fix distortedTruth: list -> string
    dt = item.get('distortedTruth')
    if isinstance(dt, list):
        item['distortedTruth'] = '\n\n'.join(dt)
        changes.append(f'distortedTruth: list({len(dt)}) -> string')

    # 3. Fix examples: str/dict -> list of strings
    exs = item.get('examples')
    if isinstance(exs, str):
        # Split by newlines if multi-line, otherwise single item
        parts = [p.strip() for p in exs.split('\n') if p.strip()]
        if len(parts) <= 1:
            item['examples'] = [exs] if exs else []
        else:
            item['examples'] = parts
        changes.append(f'examples: str -> list({len(item["examples"])})')
    elif isinstance(exs, dict):
        # Extract values as list
        values = list(exs.values())
        item['examples'] = [str(v) for v in values]
        changes.append(f'examples: dict -> list({len(item["examples"])})')
    elif exs is None:
        item['examples'] = []
        changes.append('examples: None -> empty list')

    # 4. Fix spotlight: add weight field where missing
    spots = item.get('spotlights', [])
    spot_fixed = 0
    for s in spots:
        if isinstance(s, dict):
            if 'weight' not in s:
                s['weight'] = 1.0
                spot_fixed += 1
    if spot_fixed > 0:
        changes.append(f'spotlight: added weight to {spot_fixed} items')

    # 5. Fix category: Chinese -> English
    cat = item.get('category', '')
    if cat in cn_cats:
        et = item.get('entryType', 'IDIOM')
        new_cat = guess_category(item.get('idiom', ''), et)
        item['category'] = new_cat
        changes.append(f'category: {cat} -> {new_cat}')

    # 6. Fix toxicityLevel: Chinese -> English
    tox = item.get('toxicityLevel', '')
    if tox in TOXICITY_MAP:
        new_tox = TOXICITY_MAP[tox]
        item['toxicityLevel'] = new_tox
        changes.append(f'toxicityLevel: {tox} -> {new_tox}')

    return item, changes


def main():
    with open(INPUT, encoding='utf-8') as f:
        data = json.load(f)

    print(f'Loaded {len(data)} entries')

    total_changes = 0
    entries_fixed = 0
    change_types = Counter()

    for i, item in enumerate(data):
        item, changes = fix_entry(item)
        if changes:
            entries_fixed += 1
            total_changes += len(changes)
            for c in changes:
                change_types[c.split(':')[0]] += 1

    # Write back
    with open(INPUT, 'w', encoding='utf-8') as f:
        json.dump(data, f, ensure_ascii=False, indent=2)

    print(f'\nFixed {entries_fixed} entries, {total_changes} total changes')
    print(f'\nChange types:')
    for k, v in change_types.most_common():
        print(f'  {k}: {v}')

    # Verify
    print('\n=== Post-fix verification ===')
    tp_list = sum(1 for item in data if isinstance(item.get('townPerspective'), list))
    dt_list = sum(1 for item in data if isinstance(item.get('distortedTruth'), list))
    ex_bad = sum(1 for item in data if not isinstance(item.get('examples'), list))
    cn_cat = sum(1 for item in data if item.get('category') in ['底层认知', '片面认知', '焦虑陷阱', '认知误区', '传统观念'])
    cn_tox = sum(1 for item in data if item.get('toxicityLevel') in ['底层认知', '片面认知', '焦虑陷阱', '认知误区', '传统观念'])
    sw_bad = sum(1 for item in data for s in item.get('spotlights', []) if isinstance(s, dict) and 'weight' not in s)

    print(f'  townPerspective as list: {tp_list} (should be 0)')
    print(f'  distortedTruth as list: {dt_list} (should be 0)')
    print(f'  examples not list: {ex_bad} (should be 0)')
    print(f'  Chinese category: {cn_cat} (should be 0)')
    print(f'  Chinese toxicityLevel: {cn_tox} (should be 0)')
    print(f'  spotlight missing weight: {sw_bad} (should be 0)')

    if tp_list == 0 and dt_list == 0 and ex_bad == 0 and cn_cat == 0 and cn_tox == 0 and sw_bad == 0:
        print('\n  ALL CHECKS PASSED!')
    else:
        print('\n  SOME ISSUES REMAIN!')


if __name__ == '__main__':
    main()
