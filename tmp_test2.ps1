$json = Get-Content 'app\src\main\assets\idioms\idioms.json' -Raw -Encoding UTF8 | ConvertFrom-Json
$json[0] | Add-Member -NotePropertyName 'cognitiveBiasTags' -NotePropertyValue @('test1', 'test2') -Force
$json[0] | ConvertTo-Json -Depth 10 | Write-Host
