$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$lines = [System.IO.File]::ReadAllLines($path, [System.Text.Encoding]::UTF8)
foreach ($n in @(119, 229, 249, 255, 257, 287, 290, 294, 298, 305, 320, 321, 326, 335)) {
    Write-Host "Line $($n+1): $($lines[$n])"
}