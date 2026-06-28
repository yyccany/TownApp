try {
    $json = Get-Content 'd:\TownApp\tmp_batch3_c.json' -Encoding UTF8 | ConvertFrom-Json
    $keys = $json.PSObject.Properties.Name
    Write-Host 'JSON valid.'
    Write-Host ('Keys: ' + ($keys -join ', '))
    Write-Host ('Count: ' + $keys.Count)
} catch {
    Write-Host ('JSON invalid: ' + $_.Exception.Message)
}
