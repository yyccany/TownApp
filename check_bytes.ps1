$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$lines = [System.IO.File]::ReadAllLines($path, [System.Text.Encoding]::UTF8)
$line = $lines[117]  # line 118 (0-based)
Write-Host "Line content: $line"
$bytes = [System.Text.Encoding]::UTF8.GetBytes($line)
for ($i = 0; $i -lt $bytes.Length; $i++) {
    if ($bytes[$i] -eq 0xE2 -and $i+2 -lt $bytes.Length) {
        Write-Host "Found E2 at $i, next: $($bytes[$i+1].ToString('X2')) $($bytes[$i+2].ToString('X2'))"
    }
}