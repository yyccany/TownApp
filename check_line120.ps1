$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$bytes = [System.IO.File]::ReadAllBytes($path)
$lineNum = 1
$lineStart = 0
for ($i = 0; $i -lt $bytes.Length; $i++) {
    if ($bytes[$i] -eq 0x0A) {
        if ($lineNum -eq 120) {
            $lineBytes = $bytes[$lineStart..($i-1)]
            $hex = ($lineBytes | ForEach-Object { $_.ToString('X2') }) -join ' '
            Write-Host "Line 120 bytes: $hex"
            # Also try to decode as UTF-8
            try {
                $text = [System.Text.Encoding]::UTF8.GetString($lineBytes)
                Write-Host "Line 120 text: $text"
            } catch {
                Write-Host "Failed to decode"
            }
        }
        $lineStart = $i + 1
        $lineNum++
    }
}