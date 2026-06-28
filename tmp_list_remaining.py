import json
with open(r'd:\TownApp\tmp_entries_to_rewrite.json','r',encoding='utf-8') as f:
    data = json.load(f)
for e in data:
    if e.get('category') in ('CONSUMPTION','TOWN_SYSTEM'):
        print(f"{e.get('category')}: {e.get('id')} -> {e.get('idiom')}")
