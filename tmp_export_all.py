import json

with open(r'd:\TownApp\tmp_entries_to_rewrite.json', 'r', encoding='utf-8') as f:
    data = json.load(f)

for entry in data:
    print(f"ID: {entry['id']}")
    print(f"Category: {entry['category']}")
    print(f"Idiom: {entry.get('idiom','')}")
    print(f"Keys: {list(entry.keys())}")
    # check if has all 6 fields
    has_all = all(k in entry for k in ['traditionalMeaning','examples','townPerspective','actionableTip','distortedTruth','keyMessage'])
    print(f"Has all 6 fields: {has_all}")
    print("---")
