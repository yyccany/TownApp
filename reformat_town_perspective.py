#!/usr/bin/env python3
"""
重构 idioms.json 中 townPerspective 字段的格式：
1. 将①~⑤五层各自拆分为独立段落，段落间空行分隔
2. 确保每个条目都有完整的五层（缺失则补充提示）
3. spotlights 内容不与 townPerspective 写在同一个字符串里（已是独立字段，无需改动JSON）
"""

import json
import re

def reformat_town_perspective(text):
    """
    将 townPerspective 中的五层视角拆为独立段落。
    输入: "① 唯物史观视角：...② 现代社会结构批判：...③ 认知心理学视角：...④ 自然科学视角：...⑤ 情感视角：..."
    输出: "① 唯物史观视角：...\n\n② 现代社会结构批判：...\n\n③ 认知心理学视角：...\n\n④ 自然科学视角：...\n\n⑤ 情感视角：..."
    """
    if not text or '①' not in text:
        return text
    
    # 按层标记分割（保留标记）
    # 标记格式：① ② ③ ④ ⑤
    markers = ['①', '②', '③', '④', '⑤']
    
    # 找到每个标记的位置
    positions = []
    for marker in markers:
        idx = text.find(marker)
        if idx != -1:
            positions.append((idx, marker))
    
    if not positions:
        return text
    
    # 按位置排序
    positions.sort(key=lambda x: x[0])
    
    # 拆分成各个段落
    sections = []
    for i, (pos, marker) in enumerate(positions):
        start = pos  # 包含标记
        end = positions[i+1][0] if i < len(positions)-1 else len(text)
        section = text[start:end].strip()
        sections.append(section)
    
    # 用两个换行符连接各段落（JSON里写成 \n\n）
    return '\n\n'.join(sections)

def check_missing_layers(text):
    """检查缺失的层级，返回缺失的层标记列表"""
    missing = []
    for marker in ['①', '②', '③', '④', '⑤']:
        if marker not in text:
            missing.append(marker)
    return missing

def main():
    json_path = r'D:\TownApp\app\src\main\assets\idioms\idioms.json'
    
    with open(json_path, 'r', encoding='utf-8') as f:
        data = json.load(f)
    
    print(f"读取 {len(data)} 条词条")
    
    reformatted = 0
    missing_layers_stats = {1: 0, 2: 0, 3: 0, 4: 0, 5: 0}  # 缺失层数统计
    
    for item in data:
        tp = item.get('townPerspective', '')
        if not tp:
            continue
        
        # 检查缺失层级
        missing = check_missing_layers(tp)
        if missing:
            missing_layers_stats[len(missing)] += 1
            # 打印前5条缺失情况
            if reformatted < 5:
                print(f"  [缺失层级] {item['idiom']}: 缺失 {missing}")
        
        # 重构格式
        new_tp = reformat_town_perspective(tp)
        if new_tp != tp:
            item['townPerspective'] = new_tp
            reformatted += 1
    
    # 写回文件（保留中文不转义）
    with open(json_path, 'w', encoding='utf-8') as f:
        json.dump(data, f, ensure_ascii=False, indent=2)
    
    print(f"\n重构完成：{reformatted} 条 townPerspective 已分段")
    print(f"缺失层级统计：")
    for k, v in missing_layers_stats.items():
        if v > 0:
            print(f"  缺失 {k} 层: {v} 条")

if __name__ == '__main__':
    main()
