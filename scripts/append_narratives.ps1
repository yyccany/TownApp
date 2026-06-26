$ErrorActionPreference = "Stop"
$base = "d:\TownApp"
$dev = "$base\docs_development"
$targetBase = "$base\app\src\main\assets\docs"

$mapping = @{
    "世界观语录库文案提取.md" = @("01-核心人生系统", "08-认知反思系统")
    "人生抉择体系文案提取.md" = @("10-人生事件系统", "01-核心人生系统")
    "医疗药物系统文案提取.md" = @("06-医疗健康系统")
    "穿搭出行系统文案提取.md" = @("04-物质生活系统")
    "饮食系统文案提取.md" = @("05-饮食健康系统")
    "随机事件库文案提取.md" = @("07-文化娱乐系统", "02-社交心理系统", "09-互动陪伴系统")
    "小镇静态文案全量提取文档.md" = @("01-核心人生系统", "02-社交心理系统", "03-职业经济系统", "04-物质生活系统", "07-文化娱乐系统", "09-互动陪伴系统")
}

foreach ($srcName in $mapping.Keys) {
    $srcPath = Join-Path $dev $srcName
    if (-not (Test-Path $srcPath)) { Write-Host "跳过: $srcName"; continue }
    $bytes = [System.IO.File]::ReadAllBytes($srcPath)
    $utf8 = New-Object System.Text.UTF8Encoding($false, $true)
    try { $content = $utf8.GetString($bytes) } catch { $content = [System.Text.Encoding]::UTF8.GetString($bytes) }

    $validLines = New-Object System.Collections.ArrayList
    foreach ($m in [regex]::Matches($content, "(?m)^-\s+(.+)$")) {
        $line = $m.Groups[1].Value.Trim()
        $line = $line -replace "\*\*[^*]+\*\*", ""
        $line = $line -replace "^note_\d+\s*[:：]\s*", ""
        $line = $line.Trim()
        $bad = $false
        foreach ($prefix in @("来源","状态","说明","对应成语","##")) { if ($line.StartsWith($prefix)) { $bad = $true; break } }
        foreach ($kw in @("：已上线","：已废弃")) { if ($line.Contains($kw)) { $bad = $true; break } }
        if (-not $bad -and $line.Length -ge 8 -and $line.Length -le 200) {
            [void]$validLines.Add($line)
        }
    }
    Write-Host "$srcName : 有效 $($validLines.Count) 条"
    $targets = $mapping[$srcName]
    $chunkSize = [Math]::Ceiling($validLines.Count / $targets.Count)
    for ($i = 0; $i -lt $targets.Count; $i++) {
        $chunk = $validLines.GetRange($i * $chunkSize, [Math]::Min($chunkSize, $validLines.Count - $i * $chunkSize))
        if ($chunk.Count -eq 0) { continue }
        $targetFile = Join-Path (Join-Path $targetBase $targets[$i]) "基础设定.md"
        $sb = New-Object System.Text.StringBuilder
        [void]$sb.Append("`r`n`r`n")
        foreach ($ln in $chunk) { [void]$sb.Append("- $ln`r`n") }
        [System.IO.File]::AppendAllText($targetFile, $sb.ToString(), [System.Text.Encoding]::UTF8)
        Write-Host "  -> $($targets[$i]): 追加 $($chunk.Count) 条"
    }
}
Write-Host "`n全部追加完成！"
