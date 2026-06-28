import json

with open('app/src/main/assets/idioms/idioms.json', 'r', encoding='utf-8') as f:
    original = json.load(f)

with open('tmp_batch1_partial.json', 'r', encoding='utf-8') as f:
    updates = json.load(f)

for item in original:
    idiom_id = item.get('id')
    if idiom_id in updates:
        update = updates[idiom_id]
        for key in ['traditionalMeaning', 'distortedTruth', 'townPerspective', 'examples', 'actionableTip', 'keyMessage']:
            if key in update:
                item[key] = update[key]
        print(f'Updated: {idiom_id} ({item.get("idiom")})')

with open('app/src/main/assets/idioms/idioms.json', 'w', encoding='utf-8') as f:
    json.dump(original, f, ensure_ascii=False, indent=4)

print(f'Merge complete: {len(updates)} items')
