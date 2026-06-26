$ErrorActionPreference = "Stop"
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

$targetBase = "d:\TownApp\app\src\main\assets\docs"
$sourceDir = "d:\TownApp\docs_development"
$docsDir = "d:\TownApp\docs"

$systemDirs = Get-ChildItem -Path $targetBase -Directory | Where-Object { $_.Name -match "^\d{2}-" } | Sort-Object Name | Select-Object -ExpandProperty FullName
Write-Host "Found $($systemDirs.Count) system directories"

function Get-ValidLines($filePath) {
    $result = @()
    try {
        $bytes = [System.IO.File]::ReadAllBytes($filePath)
        $content = [System.Text.Encoding]::UTF8.GetString($bytes)
        $matches = [regex]::Matches($content, '(?m)^-\s+(.+)$')
        foreach ($m in $matches) {
            $line = $m.Groups[1].Value.Trim()
            $line = $line -replace '\*\*([^*]+)\*\*', '$1'
            if ($line.Length -ge 10 -and $line.Length -le 180) {
                $skip = $false
                if ($line.Contains("|") -or $line.Contains("<!--")) { $skip = $true }
                if (-not $skip) { $result += $line }
            }
        }
    } catch {
        Write-Host "  WARN: $($_.Exception.Message)"
    }
    return $result
}

$sourceFiles = @()
Get-ChildItem -Path $sourceDir -Filter "*.md" -Recurse | ForEach-Object { $sourceFiles += $_.FullName }
Get-ChildItem -Path $docsDir -Filter "*.md" | ForEach-Object { $sourceFiles += $_.FullName }
Write-Host "Processing $($sourceFiles.Count) source files..."

$allLines = [System.Collections.ArrayList]::new()
$seen = @{}
foreach ($f in $sourceFiles) {
    $lines = Get-ValidLines $f
    foreach ($l in $lines) {
        $key = $l.GetHashCode()
        if (-not $seen.ContainsKey($key)) {
            $seen[$key] = $true
            [void]$allLines.Add($l)
        }
    }
}
Write-Host "Extracted $($allLines.Count) unique valid lines"

$crlf = "`r`n"
$sysCount = $systemDirs.Count
$perSys = [Math]::Ceiling($allLines.Count / $sysCount)
$totalAppended = 0
$baseFile = $null

for ($i = 0; $i -lt $sysCount; $i++) {
    $sysDir = $systemDirs[$i]
    $baseFile = Get-ChildItem -Path $sysDir -Filter "*.md" | Select-Object -First 1
    if (-not $baseFile) {
        Write-Host "  SKIP: $(Split-Path $sysDir -Leaf)"
        continue
    }
    
    $start = $i * $perSys
    $end = [Math]::Min($start + $perSys, $allLines.Count) - 1
    if ($start -ge $allLines.Count) { continue }
    
    $sb = [System.Text.StringBuilder]::new()
    [void]$sb.Append($crlf)
    $cnt = 0
    for ($j = $start; $j -le $end; $j++) {
        [void]$sb.Append("- " + $allLines[$j] + $crlf)
        $cnt++
    }
    
    [System.IO.File]::AppendAllText($baseFile.FullName, $sb.ToString(), [System.Text.UTF8Encoding]::new($false))
    Write-Host "  $(Split-Path $sysDir -Leaf) : +$cnt"
    $totalAppended += $cnt
}

Write-Host "Done! Total imported: $totalAppended lines"
