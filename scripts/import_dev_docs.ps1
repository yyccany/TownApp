$ErrorActionPreference = "Stop"
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

$devPath = "d:\TownApp\docs_development"
$targetDir = "d:\TownApp\app\src\main\assets\docs\14-开发文档文案池"
$targetFile = "$targetDir\基础设定.md"

if (!(Test-Path $targetDir)) {
    New-Item -ItemType Directory -Path $targetDir -Force | Out-Null
}

$devDocs = @(
    "世界观语录库文案提取.md",
    "人生抉择体系文案提取.md",
    "医疗药物系统文案提取.md",
    "小镇静态文案全量提取文档.md",
    "穿搭出行系统文案提取.md",
    "随机事件库文案提取.md",
    "饮食系统文案提取.md"
)

$sb = New-Object System.Text.StringBuilder
[void]$sb.AppendLine("# 开发文档补充文案池")
[void]$sb.AppendLine()
[void]$sb.AppendLine("> 从docs_development目录的文案提取文档中导入的补充叙事内容")
[void]$sb.AppendLine()
[void]$sb.AppendLine("## 随机叙事文案池")
[void]$sb.AppendLine()

$totalAdded = 0

foreach ($docName in $devDocs) {
    $docPath = Join-Path $devPath $docName
    if (!(Test-Path $docPath)) { continue }

    $content = Get-Content $docPath -Encoding UTF8 -Raw
    $dashLines = [regex]::Matches($content, '(?m)^-\s+(.+)$')

    $docAdded = 0
    foreach ($m in $dashLines) {
        $line = $m.Groups[1].Value.Trim()

        if ($line -match '^\[ \]' -or $line -match '^\[x\]' -or $line -match '^\[X\]') { continue }
        if ($line -match '\|') { continue }
        if ($line -match '^<!--' -or $line -match '-->$') { continue }
        if ($line.Length -lt 8 -or $line.Length -gt 200) { continue }

        $line = $line -replace '\*\*([^*]+)\*\*', '$1'

        if ($line -match '^(来源|状态|说明|对应成语|触发场景|体感|闪光点|小镇评述|副标题|场景|物品|key|type|score|sleep_status|dream_type|tendency|emotion|season|identity|category|age|time|npc|behavior|response|id|name|desc|price|tier|effect|condition|prob|weight|rarity|flag|icon|tag|group|parent|level|cost|income|outcome|require)') { continue }
        if ($line -match '[：:](已上线|已废弃|已实现|未实现|TODO|待补|待做)$') { continue }
        if ($line -match '^[A-Za-z_][A-Za-z0-9_]*[:：]') { continue }

        [void]$sb.AppendLine("- $line")
        $docAdded++
        $totalAdded++
    }
    Write-Host "$docName : 导入 $docAdded 条"
}

$utf8NoBom = [System.Text.UTF8Encoding]::new($false)
[System.IO.File]::WriteAllText($targetFile, $sb.ToString(), $utf8NoBom)

Write-Host ""
Write-Host "=== 导入完成 ==="
Write-Host "目标文件: $targetFile"
Write-Host "总计导入: $totalAdded 条叙事行"
