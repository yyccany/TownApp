$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$content = [System.IO.File]::ReadAllText($path, [System.Text.Encoding]::UTF8)
$content = $content.Replace(([char]0x201C).ToString(), '\"').Replace(([char]0x201D).ToString(), '\"')
[System.IO.File]::WriteAllText($path, $content, [System.Text.Encoding]::UTF8)
Write-Host 'Done'