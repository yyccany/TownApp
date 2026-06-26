import os
import re
import chardet

base = r"d:\TownApp"
dev = os.path.join(base, "docs_development")
target_base = os.path.join(base, "app", "src", "main", "assets", "docs")

mapping = {
    "世界观语录库文案提取.md": ["01-核心人生系统", "08-认知反思系统"],
    "人生抉择体系文案提取.md": ["10-人生事件系统", "01-核心人生系统"],
    "医疗药物系统文案提取.md": ["06-医疗健康系统"],
    "穿搭出行系统文案提取.md": ["04-物质生活系统"],
    "饮食系统文案提取.md": ["05-饮食健康系统"],
    "随机事件库文案提取.md": ["07-文化娱乐系统", "02-社交心理系统", "09-互动陪伴系统"],
    "小镇静态文案全量提取文档.md": ["01-核心人生系统", "02-社交心理系统", "03-职业经济系统", "04-物质生活系统", "07-文化娱乐系统", "09-互动陪伴系统"],
}

def is_valid_line(line):
    line = line.strip()
    line = re.sub(r"\*\*[^*]+\*\*", "", line)
    line = re.sub(r"^note_\d+\s*[:：]\s*", "", line)
    line = line.strip()
    if len(line) < 8 or len(line) > 200:
        return False
    bad_starts = ["来源", "状态", "说明", "对应成语", "##"]
    for bs in bad_starts:
        if line.startswith(bs):
            return False
    bad_contains = ["：已上线", "：已废弃"]
    for bc in bad_contains:
        if bc in line:
            return False
    return line

for src_name, targets in mapping.items():
    src_path = os.path.join(dev, src_name)
    if not os.path.exists(src_path):
        print(f"跳过不存在的: {src_name}")
        continue
    with open(src_path, "rb") as f:
        raw = f.read()
    det = chardet.detect(raw)
    encoding = det["encoding"] or "utf-8"
    try:
        content = raw.decode(encoding)
    except:
        content = raw.decode("utf-8", errors="ignore")
    valid_lines = []
    for match in re.finditer(r"^-\s+(.+)$", content, re.MULTILINE):
        line = is_valid_line(match.group(1))
        if line:
            valid_lines.append(line)
    print(f"{src_name}: 有效文案 {len(valid_lines)} 条")
    chunk_size = (len(valid_lines) + len(targets) - 1) // len(targets)
    for i, target_folder in enumerate(targets):
        chunk = valid_lines[i*chunk_size : (i+1)*chunk_size]
        if not chunk:
            continue
        target_file = os.path.join(target_base, target_folder, "基础设定.md")
        with open(target_file, "a", encoding="utf-8") as f:
            f.write("\n\n")
            for line in chunk:
                f.write(f"- {line}\n")
        print(f"  -> {target_folder}: 追加 {len(chunk)} 条")

print("\n全部追加完成！")
