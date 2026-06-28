import json

with open(r'd:\TownApp\tmp_entries_to_rewrite.json', 'r', encoding='utf-8') as f:
    data = json.load(f)

society = [e for e in data if e.get('category')=='SOCIETY']
with open(r'd:\TownApp\tmp_society.json', 'w', encoding='utf-8') as f:
    json.dump(society, f, ensure_ascii=False, indent=2)
print('exported', len(society))
