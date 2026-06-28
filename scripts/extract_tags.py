#!/usr/bin/env python3
"""Extract cognitive bias tags from distortedTruth text for 41 empty-tag entries."""
import json
import re

with open('app/src/main/assets/idioms/idioms.json', 'r', encoding='utf-8') as f:
    data = json.load(f)

# Show a sample spotlight format
for e in data:
    if e['category'] == 'COGNITION' and e.get('spotlights'):
        print(f'=== Sample spotlight format: {e["idiom"]} ===')
        for s in e['spotlights']:
            print(f'  category={s["category"]}, weight={s["weight"]}, text={s["text"][:80]}')
        break

print()

# Extract tags from distortedTruth
empty_tag_entries = [e for e in data if not e.get('cognitiveBiasTags')]
print(f'=== Extracting tags for {len(empty_tag_entries)} entries ===')

for e in empty_tag_entries:
    dt = e['distortedTruth']
    tags = []

    # Pattern 1: 对应'XX'和'YY' or 对应'XX'与'YY'
    for m in re.finditer(r"对应['\u2018\u2019\u201c\u201d]([^'\u2018\u2019\u201c\u201d]+)['\u2018\u2019\u201c\u201d][和与]['\u2018\u2019\u201c\u201d]([^'\u2018\u2019\u201c\u201d]+)", dt):
        tags.append(m.group(1).strip())
        tags.append(m.group(2).strip())

    # Pattern 2: 绑定'XX'与'YY'
    for m in re.finditer(r"绑定['\u2018\u2019\u201c\u201d]([^'\u2018\u2019\u201c\u201d]+)['\u2018\u2019\u201c\u201d][与和]['\u2018\u2019\u201c\u201d]([^'\u2018\u2019\u201c\u201d]+)", dt):
        tags.append(m.group(1).strip())
        tags.append(m.group(2).strip())

    # Pattern 3: 对应'XX'
    for m in re.finditer(r"对应['\u2018\u2019\u201c\u201d]([^'\u2018\u2019\u201c\u201d]{2,10})['\u2018\u2019\u201c\u201d]", dt):
        tags.append(m.group(1).strip())

    # Deduplicate
    seen = set()
    unique_tags = []
    for t in tags:
        if t and t not in seen and len(t) <= 15:
            seen.add(t)
            unique_tags.append(t)

    print(f'{e["idiom"]}: {unique_tags}')
