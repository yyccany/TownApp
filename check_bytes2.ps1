$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$bytes = [System.IO.File]::ReadAllBytes($path)
$lineNum = 1
$lineStart = 0
for ($i = 0; $i -lt $bytes.Length; $i++) {
    if ($bytes[$i] -eq 0x0A) {
        if ($lineNum -eq 120 -or $lineNum -eq 230 -or $lineNum -eq 250) {
            $lineBytes = $bytes[$lineStart..($i-1)]
            $hex = ($lineBytes | ForEach-Object { $_.ToString('X2') }) -join ' '
            Write-Host "Line $lineNum bytes: $hex"
        }
        $lineStart = $i + 1
        $lineNum++
    }
}