$json = Get-Content 'app\src\main\assets\idioms\idioms.json' -Raw -Encoding UTF8 | ConvertFrom-Json

$groupFiles = @('tmp_batch1_group1.json', 'tmp_batch1_group2.json', 'tmp_batch1_group3.json')
$totalUpdated = 0

foreach ($gf in $groupFiles) {
    if (Test-Path $gf) {
        $updates = Get-Content $gf -Raw -Encoding UTF8 | ConvertFrom-Json
        foreach ($item in $json) {
            $updateKey = $item.id
            if ($updates.$updateKey) {
                $u = $updates.$updateKey
                if ($u.traditionalMeaning) { $item.traditionalMeaning = $u.traditionalMeaning }
                if ($u.distortedTruth) { $item.distortedTruth = $u.distortedTruth }
                if ($u.townPerspective) { $item.townPerspective = $u.townPerspective }
                if ($u.examples) { $item.examples = $u.examples }
                if ($u.actionableTip) { $item.actionableTip = $u.actionableTip }
                if ($u.keyMessage) { $item.keyMessage = $u.keyMessage }
                Write-Host "Updated: $($item.id) ($($item.idiom))"
                $totalUpdated++
            }
        }
    } else {
        Write-Warning "File not found: $gf"
    }
}

$json | ConvertTo-Json -Depth 10 | Set-Content 'app\src\main\assets\idioms\idioms.json' -Encoding UTF8
Write-Host "Merge complete. Total updated: $totalUpdated"
