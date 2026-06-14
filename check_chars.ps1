$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$lines = [System.IO.File]::ReadAllLines($path, [System.Text.Encoding]::UTF8)
$line = $lines[119]
Write-Host "Line: $line"
for ($i = 0; $i -lt $line.Length; $i++) {
    $c = $line[$i]
    if ([int]$c -gt 127 -or $c -eq '"' -or $c -eq '"') {
        Write-Host "  Char at $i : '$c' = 0x$([int]$c.ToString('X4'))"
    }
}