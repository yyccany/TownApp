$jsonText = [System.IO.File]::ReadAllText('app\src\main\assets\idioms\idioms.json', [System.Text.Encoding]::UTF8)
$original = [System.Text.Json.JsonSerializer]::Deserialize($jsonText, [System.Collections.Generic.List[object]])

$groupFiles = @('tmp_batch2_g1.json', 'tmp_batch2_g2.json', 'tmp_batch2_g3.json', 'tmp_batch2_g4.json', 'tmp_batch2_g5.json')
$totalUpdated = 0

foreach ($gf in $groupFiles) {
    if (Test-Path $gf) {
        $updateText = [System.IO.File]::ReadAllText($gf, [System.Text.Encoding]::UTF8)
        $updates = [System.Text.Json.JsonSerializer]::Deserialize($updateText, [System.Collections.Generic.Dictionary[string, object]])
        foreach ($item in $original) {
            $id = $item.id.ToString()
            if ($updates.ContainsKey($id)) {
                $u = $updates[$id]
                $fields = @('traditionalMeaning', 'distortedTruth', 'townPerspective', 'examples', 'actionableTip', 'keyMessage')
                foreach ($f in $fields) {
                    if ($u.PSObject.Properties.Name -contains $f -or $u.ContainsKey($f)) {
                        $val = $u[$f]
                        if ($val -ne $null) {
                            $item.$f = $val
                        }
                    }
                }
                Write-Host "Updated: $id ($($item.idiom))"
                $totalUpdated++
            }
        }
    }
}

$options = New-Object System.Text.Json.JsonSerializerOptions
$options.WriteIndented = $true
$options.Encoder = [System.Text.Encodings.Web.JavaScriptEncoder]::UnsafeRelaxedJsonEscaping
$output = [System.Text.Json.JsonSerializer]::Serialize($original, $options)
[System.IO.File]::WriteAllText('app\src\main\assets\idioms\idioms.json', $output, [System.Text.Encoding]::UTF8)

Write-Host "Merge complete. Total updated: $totalUpdated"
