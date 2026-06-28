$json = Get-Content 'app\src\main\assets\idioms\idioms.json' -Raw -Encoding UTF8 | ConvertFrom-Json
$updates = Get-Content 'tmp_batch1_partial.json' -Raw -Encoding UTF8 | ConvertFrom-Json

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
    }
}

$json | ConvertTo-Json -Depth 10 | Set-Content 'app\src\main\assets\idioms\idioms.json' -Encoding UTF8
Write-Host "Merge complete"
