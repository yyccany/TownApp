$json = Get-Content 'app\src\main\assets\idioms\idioms.json' -Raw -Encoding UTF8 | ConvertFrom-Json
Write-Host "Loaded $($json.Count) items"
Write-Host "First idiom: $($json[0].idiom)"
