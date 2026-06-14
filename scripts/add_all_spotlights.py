# -*- coding: utf-8 -*-
import re

# 闪光点模板
spotlight_templates = [
    ('TRADITION', '作为传统文化的一部分，它承载着特定的历史记忆'),
    ('FREEDOM', '在现代，它可以作为一种自愿的选择，而非强制要求'),
    ('RESILIENCE', '体现了坚持不懈、克服困难的坚韧品质'),
    ('STRATEGY', '蕴含着为人处世的智慧和策略'),
    ('EMOTION', '能触动人心，引发情感共鸣'),
    ('INTERPERSONAL', '反映了人际交往的智慧和分寸感'),
    ('SELF_CULTIVATION', '展现了自我修养和品格塑造的追求'),
    ('COGNITION', '从不同角度思考，能获得新的启发'),
    ('CULTURE', '体现了汉语言文化的博大精深')
]

# 读取文件
with open(r'd:\TownApp\app\src\main\java\com\example\townapp\data\idiom\IdiomCritiqueLibrary.kt', 'r', encoding='utf-8') as f:
    content = f.read()

# 匹配IdiomCritique的模式（寻找没有spotlights的成语）
pattern = r'(IdiomCritique\(\s*id = "([^"]+)",\s*idiom = "([^"]+)",.*?)(?=\s*category = )'

count = 0

def add_spotlight(match):
    global count
    full_match = match.group(1)
    
    # 如果已经有spotlights，跳过
    if 'spotlights =' in full_match:
        return full_match
    
    # 使用通用闪光点模板
    template1 = spotlight_templates[0]
    template2 = spotlight_templates[-1]
    
    # 生成闪光点代码
    spotlight_code = f',\n            spotlights = listOf(\n                    Spotlight(SpotlightCategory.{template1[0]}, "{template1[1]}", 1.0),\n                    Spotlight(SpotlightCategory.{template2[0]}, "{template2[1]}", 0.9)\n            )'
    
    count += 1
    if count % 50 == 0:
        print(f'已处理 {count} 个成语...')
    
    return full_match + spotlight_code

# 执行替换
new_content = re.sub(pattern, add_spotlight, content, flags=re.DOTALL)

# 写入文件
with open(r'd:\TownApp\app\src\main\java\com\example\townapp\data\idiom\IdiomCritiqueLibrary.kt', 'w', encoding='utf-8') as f:
    f.write(new_content)

print(f'\n处理完成！共为 {count} 个成语添加了闪光点。')