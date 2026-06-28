import json

with open(r'd:\TownApp\tmp_entries_to_rewrite.json', 'r', encoding='utf-8') as f:
    data = json.load(f)

by_cat = {}
for entry in data:
    cat = entry.get('category','UNKNOWN')
    by_cat.setdefault(cat, []).append(entry)

for cat, entries in by_cat.items():
    print(f"\n=== {cat} ({len(entries)}条) ===")
    for e in entries:
        print(f"  {e['id']}: {e.get('idiom','')}")
