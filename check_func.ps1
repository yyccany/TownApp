$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$lines = [System.IO.File]::ReadAllLines($path, [System.Text.Encoding]::UTF8)
for ($i = 0; $i -lt [Math]::Min(30, $lines.Length); $i++) {
    Write-Host "Line $($i+1): $($lines[$i])"
}