#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
批量为成语添加闪光点脚本
遵循万物薪俸小镇的"万物平等，各有光芒"原则
每个成语都有其独特的价值和时代闪光点
"""

import re
import random

# 闪光点分类
SPOTLIGHT_CATEGORIES = {
    "TRADITION": {"emoji": "📜", "name": "历史价值"},
    "FREEDOM": {"emoji": "🗽", "name": "自主选择"},
    "RESILIENCE": {"emoji": "🌿", "name": "韧性力量"},
    "STRATEGY": {"emoji": "🎯", "name": "处世策略"},
    "EMOTION": {"emoji": "💗", "name": "情感心理"},
    "INTERPERSONAL": {"emoji": "🤝", "name": "人际智慧"},
    "SELF_CULTIVATION": {"emoji": "🌱", "name": "修身成长"},
    "COGNITION": {"emoji": "💡", "name": "认知思维"},
    "CULTURE": {"emoji": "🏺", "name": "文化传承"},
}

# 闪光点模板库 - 根据不同类型的成语生成不同的闪光点
SPOTLIGHT_TEMPLATES = {
    # 牺牲奉献类成语
    "sacrifice": [
        ("TRADITION", "在古代社会，这种奉献精神有助于维持群体的生存与稳定"),
        ("FREEDOM", "在现代，它可以作为一种自愿的选择，而非强制要求"),
        ("EMOTION", "当出自真心时，这份善意是人性中温暖的光芒"),
    ],
    # 勤奋苦学类成语
    "diligence": [
        ("RESILIENCE", "体现了坚持不懈、克服困难的坚韧品质"),
        ("STRATEGY", "当努力是自主选择的成长路径时，它能带来真正的进步"),
        ("SELF_CULTIVATION", "展现了主动提升自我的积极态度"),
    ],
    # 人情世故类成语
    "interpersonal": [
        ("INTERPERSONAL", "蕴含着与人相处的智慧和分寸感"),
        ("TRADITION", "反映了古代社会人际交往的生存智慧"),
        ("FREEDOM", "在尊重边界的前提下，这些技巧能增进人际关系"),
    ],
    # 励志类成语
    "motivation": [
        ("RESILIENCE", "传递了逆境中不屈不挠的精神力量"),
        ("COGNITION", "揭示了目标明确、专注前行的重要性"),
        ("EMOTION", "能激励人们在低谷时重新振作"),
    ],
    # 传统道德类成语
    "morality": [
        ("TRADITION", "承载了古代社会的道德规范和价值观念"),
        ("FREEDOM", "现代社会中，可作为个人品德修养的参考"),
        ("CULTURE", "是中华传统文化中宝贵的精神遗产"),
    ],
    # 通用模板（适用于所有成语）
    "general": [
        ("TRADITION", "作为传统文化的一部分，它承载着特定的历史记忆"),
        ("COGNITION", "从不同角度思考，能获得新的启发和感悟"),
        ("CULTURE", "体现了汉语言文化的博大精深和独特魅力"),
    ],
}

def classify_idiom(idiom_text):
    """根据成语内容分类"""
    sacrifice_keywords = ["舍己", "无私", "奉献", "牺牲", "为公", "尽瘁", "鞠躬", "忘我"]
    diligence_keywords = ["勤奋", "刻苦", "努力", "学习", "钻研", "苦读", "寒窗", "悬梁", "刺股"]
    interpersonal_keywords = ["相处", "待人", "处事", "为人", "交友", "知人", "识人", "圆滑", "变通"]
    motivation_keywords = ["励志", "图强", "奋斗", "拼搏", "进取", "自强不息", "迎难而上"]
    morality_keywords = ["道德", "品德", "诚信", "正直", "善良", "忠孝", "仁义", "礼义"]
    
    if any(kw in idiom_text for kw in sacrifice_keywords):
        return "sacrifice"
    elif any(kw in idiom_text for kw in diligence_keywords):
        return "diligence"
    elif any(kw in idiom_text for kw in interpersonal_keywords):
        return "interpersonal"
    elif any(kw in idiom_text for kw in motivation_keywords):
        return "motivation"
    elif any(kw in idiom_text for kw in morality_keywords):
        return "morality"
    else:
        return "general"

def generate_spotlights(idiom_text):
    """为成语生成闪光点"""
    category = classify_idiom(idiom_text)
    templates = SPOTLIGHT_TEMPLATES[category] + SPOTLIGHT_TEMPLATES["general"]
    
    # 随机选择2-3个闪光点
    selected = random.sample(templates, min(2, len(templates)))
    
    # 生成格式化的闪光点代码
    spotlight_lines = []
    for cat, content in selected:
        relevance = round(random.uniform(0.7, 1.0), 1)
        line = f'                    Spotlight(SpotlightCategory.{cat}, "{content}", {relevance})'
        spotlight_lines.append(line)
    
    return ",\n".join(spotlight_lines)

def process_file(input_path, output_path):
    """处理成语文件，添加闪光点"""
    with open(input_path, "r", encoding="utf-8") as f:
        content = f.read()
    
    # 匹配没有spotlights的IdiomCritique
    pattern = r'(IdiomCritique\(\s*id = "([^"]+)",\s*idiom = "([^"]+)",[^)]+?)(?=\s*category = )'
    
    def replace_match(match):
        full_match = match.group(1)
        idiom_id = match.group(2)
        idiom_text = match.group(3)
        
        # 检查是否已经有spotlights
        if "spotlights =" in full_match:
            return full_match
        
        # 生成闪光点
        spotlights = generate_spotlights(idiom_text)
        
        # 构建新的内容
        new_content = f'{full_match},\n            spotlights = listOf(\n{spotlights}\n            )'
        return new_content
    
    # 执行替换
    new_content = re.sub(pattern, replace_match, content, flags=re.DOTALL)
    
    with open(output_path, "w", encoding="utf-8") as f:
        f.write(new_content)
    
    print(f"处理完成！已为成语添加闪光点")

if __name__ == "__main__":
    input_file = r"d:\TownApp\app\src\main\java\com\example\townapp\data\idiom\IdiomCritiqueLibrary.kt"
    output_file = r"d:\TownApp\app\src\main\java\com\example\townapp\data\idiom\IdiomCritiqueLibrary.kt"
    
    process_file(input_file, output_file)
    print("所有成语闪光点添加完成！")