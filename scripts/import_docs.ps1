$ErrorActionPreference = "Stop"

$sourceDir = "d:\TownApp\docs_development"
$docsDir = "d:\TownApp\docs"
$targetBase = "d:\TownApp\app\src\main\assets\docs"

$systemDirs = @(
    "01-core", "02-social", "03-career", "04-material",
    "05-diet", "06-medical", "07-culture", "08-cognition",
    "09-companion", "10-events", "11-products", "12-archives",
    "13-newgame"
)

$systemDirMap = @{
    "01-core" = "01-" + [char]0x6838 + [char]0x5FC3 + [char]0x4EBA + [char]0x751F + [char]0x7CFB + [char]0x7EDF
    "02-social" = "02-" + [char]0x793E + [char]0x4EA4 + [char]0x5FC3 + [char]0x7406 + [char]0x7CFB + [char]0x7EDF
    "03-career" = "03-" + [char]0x804C + [char]0x4E1A + [char]0x7ECF + [char]0x6D4E + [char]0x7CFB + [char]0x7EDF
    "04-material" = "04-" + [char]0x7269 + [char]0x8D28 + [char]0x751F + [char]0x6D3B + [char]0x7CFB + [char]0x7EDF
    "05-diet" = "05-" + [char]0x996E + [char]0x98DF + [char]0x5065 + [char]0x5EB7 + [char]0x7CFB + [char]0x7EDF
    "06-medical" = "06-" + [char]0x533B + [char]0x7597 + [char]0x5065 + [char]0x5EB7 + [char]0x7CFB + [char]0x7EDF
    "07-culture" = "07-" + [char]0x6587 + [char]0x5316 + [char]0x5A31 + [char]0x4E50 + [char]0x7CFB + [char]0x7EDF
    "08-cognition" = "08-" + [char]0x8BA4 + [char]0x77E5 + [char]0x53CD + [char]0x601D + [char]0x7CFB + [char]0x7EDF
    "09-companion" = "09-" + [char]0x4E92 + [char]0x52A8 + [char]0x966A + [char]0x4F34 + [char]0x7CFB + [char]0x7EDF
    "10-events" = "10-" + [char]0x4EBA + [char]0x751F + [char]0x4E8B + [char]0x4EF6 + [char]0x7CFB + [char]0x7EDF
    "11-products" = "11-" + [char]0x4EA7 + [char]0x54C1 + [char]0x5206 + [char]0x7C7B + [char]0x6570 + [char]0x636E
    "12-archives" = "12-" + [char]0x4F17 + [char]0x751F + [char]0x65F6 + [char]0x95F4 + [char]0x6863 + [char]0x6848 + [char]0x9986
    "13-newgame" = "13-" + [char]0x591A + [char]0x5468 + [char]0x76EE + [char]0x8BA4 + [char]0x77E5 + [char]0x7EE7 + [char]0x627F
}

function Get-ValidLines($filePath) {
    $result = @()
    try {
        $content = [System.IO.File]::ReadAllText($filePath, [System.Text.UTF8Encoding]::new($false))
        $matches = [regex]::Matches($content, '(?m)^-\s+(.+)$')
        foreach ($m in $matches) {
            $line = $m.Groups[1].Value.Trim()
            $line = $line -replace '\*\*[^*]+\*\*', ''
            $line = $line.Trim()
            if ($line.Length -ge 10 -and $line.Length -le 180) {
                $skip = $false
                $badPrefixes = @([char]0x6765 + [char]0x6E90, [char]0x72B6 + [char]0x6001, [char]0x8BF4 + [char]0x660E, [char]0x89E6 + [char]0x53D1, [char]0x573A + [char]0x666F, [char]0x573A + [char]0x666F, [char]0x526F + [char]0x6807 + [char]0x9898, [char]0x573A + [char]0x666F, [char]0x7269 + [char]0x54C1)
                foreach ($bp in $badPrefixes) {
                    if ($line.StartsWith($bp)) { $skip = $true; break }
                }
                if (-not $skip -and -not $line.Contains("|") -and -not $line.Contains("<!--")) {
                    $result += $line
                }
            }
        }
    } catch {}
    return $result
}

$sourceFiles = @()
Get-ChildItem -Path $sourceDir -Filter "*.md" -Recurse | ForEach-Object { $sourceFiles += $_.FullName }
Get-ChildItem -Path $docsDir -Filter "*.md" | ForEach-Object { $sourceFiles += $_.FullName }

Write-Host "Processing $($sourceFiles.Count) files..."

$allLines = [System.Collections.ArrayList]::new()
foreach ($f in $sourceFiles) {
    $lines = Get-ValidLines $f
    foreach ($l in $lines) { [void]$allLines.Add($l) }
}

Write-Host "Extracted $($allLines.Count) lines"

$crlf = "`r`n"
$idx = 0
$totalAppended = 0
foreach ($sysKey in $systemDirs) {
    $sysName = $systemDirMap[$sysKey]
    $targetFile = Join-Path $targetBase "$sysName"
    $targetFile = Join-Path $targetFile ([char]0x57FA + [char]0x7840 + [char]0x8BBE + [char]0x5B9A + ".md")
    
    if (-not (Test-Path $targetFile)) {
        Write-Host "SKIP: $sysName (file not found)"
        $idx++
        continue
    }
    
    $share = [Math]::Ceiling($allLines.Count / $systemDirs.Count)
    $start = ($idx * $share)
    $end = [Math]::Min($start + $share, $allLines.Count) - 1
    if ($start -ge $allLines.Count) { $idx++; continue }
    
    $sb = [System.Text.StringBuilder]::new()
    [void]$sb.Append($crlf)
    $cnt = 0
    for ($i = $start; $i -le $end; $i++) {
        [void]$sb.Append("- " + $allLines[$i] + $crlf)
        $cnt++
    }
    
    [System.IO.File]::AppendAllText($targetFile, $sb.ToString(), [System.Text.UTF8Encoding]::new($false))
    Write-Host "  $sysName : +$cnt"
    $totalAppended += $cnt
    $idx++
}

Write-Host "Done! Total: $totalAppended lines imported"
