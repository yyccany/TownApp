$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$content = [System.IO.File]::ReadAllText($path, [System.Text.Encoding]::UTF8)

# Replace Chinese left/right double quotes with escaped regular double quote
$content = $content.Replace("`u{201C}", '\"').Replace("`u{201D}", '\"')

[System.IO.File]::WriteAllText($path, $content, [System.Text.Encoding]::UTF8)
Write-Host 'Done'