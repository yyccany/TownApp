#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""合并所有新词条到 idioms.json"""

import json
import sys
import os

# 导入各批次词条
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from add_dilemma_entries import dilemma_entries
from add_academic_entries import academic_entries
from add_modern_belief_entries import modern_belief_entries
from add_idiom_entries import idiom_entries
from add_coined_entries import coined_entries

# 读取现有JSON
json_path = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), 
                         "app", "src", "main", "assets", "idioms", "idioms.json")
with open(json_path, "r", encoding="utf-8") as f:
    existing = json.load(f)

print(f"现有词条数: {len(existing)}")

# 收集所有新词条
new_entries = []
new_entries.extend(dilemma_entries)
new_entries.extend(academic_entries)
new_entries.extend(modern_belief_entries)
new_entries.extend(idiom_entries)
new_entries.extend(coined_entries)
print(f"新增词条数: {len(new_entries)}")

# 检查ID重复
existing_ids = {item["id"] for item in existing}
existing_idioms = {item["idiom"] for item in existing}
dup_ids = []
dup_idioms = []
for entry in new_entries:
    if entry["id"] in existing_ids:
        dup_ids.append(entry["id"])
    if entry["idiom"] in existing_idioms:
        dup_idioms.append(entry["idiom"])

if dup_ids:
    print(f"WARNING: ID重复: {dup_ids}")
if dup_idioms:
    print(f"WARNING: 标题重复: {dup_idioms}")

# 检查新词条内部ID重复
new_ids = [e["id"] for e in new_entries]
from collections import Counter
id_counts = Counter(new_ids)
internal_dups = {k: v for k, v in id_counts.items() if v > 1}
if internal_dups:
    print(f"WARNING: 新词条内部ID重复: {internal_dups}")

if dup_ids or dup_idioms or internal_dups:
    print("存在重复，终止合并")
    sys.exit(1)

# 合并
merged = existing + new_entries
print(f"合并后词条数: {len(merged)}")

# 按entryType统计
from collections import defaultdict
type_counts = defaultdict(int)
for item in merged:
    type_counts[item.get("entryType", "IDIOM")] += 1
print("\n合并后分布:")
for et in ["IDIOM", "COINED", "ACADEMIC", "MODERN_BELIEF", "DILEMMA"]:
    print(f"  {et}: {type_counts[et]}")

# 写入JSON
with open(json_path, "w", encoding="utf-8") as f:
    json.dump(merged, f, ensure_ascii=False, indent=2)

print(f"\n已写入 {json_path}")
print(f"词条总数: {len(existing)} -> {len(merged)} (新增 {len(new_entries)})")
