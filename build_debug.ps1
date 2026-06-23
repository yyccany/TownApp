$ErrorActionPreference = "Continue"
$output = & ".\gradlew.bat" "compileDebugKotlin", "--no-daemon" 2>&1 
$output | Out-File -FilePath "D:\TownApp\buildlog.txt" -Encoding utf8
Write-Host "=== BUILD OUTPUT ==="
$output | Select-Object -Last 100
