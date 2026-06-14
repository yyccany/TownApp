$path = 'app\src\main\java\com\example\townapp\ui\screens\CognitiveScalpelScreen.kt'
$bytes = [System.IO.File]::ReadAllBytes($path)

# UTF-8 bytes for left double quote U+201C: E2 80 9C
# UTF-8 bytes for right double quote U+201D: E2 80 9D
# Replace with backslash + regular double quote: 5C 22

$newBytes = New-Object System.Collections.Generic.List[byte]
$i = 0
while ($i -lt $bytes.Length) {
    if ($i + 2 -lt $bytes.Length -and $bytes[$i] -eq 0xE2 -and $bytes[$i+1] -eq 0x80 -and ($bytes[$i+2] -eq 0x9C -or $bytes[$i+2] -eq 0x9D)) {
        $newBytes.Add(0x5C)  # backslash
        $newBytes.Add(0x22)  # double quote
        $i += 3
    } else {
        $newBytes.Add($bytes[$i])
        $i++
    }
}

[System.IO.File]::WriteAllBytes($path, $newBytes.ToArray())
Write-Host "Done, processed $($bytes.Length) bytes"