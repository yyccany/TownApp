$ErrorActionPreference = "Stop"
$devPath = "d:\TownApp\docs_development"
$targetDir = "d:\TownApp\app\src\main\assets\docs\14-dev-docs"
$targetFile = "$targetDir\base.md"

if (!(Test-Path $targetDir)) { New-Item -ItemType Directory -Path $targetDir -Force | Out-Null }

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
[void]$sb.AppendLine("# Dev Docs Pool")
[void]$sb.AppendLine()
[void]$sb.AppendLine("## Content")
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
        if ($line -match '^\[ \]' -or $line -match '^\[x\]') { continue }
        if ($line -match '\|') { continue }
        if ($line.Length -lt 8 -or $line.Length -gt 200) { continue }
        $line = $line -replace '\*\*([^*]+)\*\*', '$1'
        if ($line -match '^(key|type|score|sleep|dream|tendency|emotion|season|identity|category|age|time|npc|behavior|response|id|name|desc|price|tier|effect|condition|prob|weight|rarity|flag|icon|tag|group|parent|level|cost|income|outcome|require):') { continue }
        if ($line -match '^[A-Za-z_][A-Za-z0-9_]*[:]') { continue }
        [void]$sb.AppendLine("- $line")
        $docAdded++
        $totalAdded++
    }
    Write-Host "$docName : $docAdded lines"
}

$utf8NoBom = [System.Text.UTF8Encoding]::new($false)
[System.IO.File]::WriteAllText($targetFile, $sb.ToString(), $utf8NoBom)
Write-Host "Total imported: $totalAdded lines"
Write-Host "Output: $targetFile"
