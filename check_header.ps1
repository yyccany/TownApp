$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$bytes = [System.IO.File]::ReadAllBytes($path)
$hex = ($bytes[0..19] | ForEach-Object { $_.ToString('X2') }) -join ' '
Write-Host "First 20 bytes: $hex"