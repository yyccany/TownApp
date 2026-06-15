$screenFiles = Get-ChildItem -Path "d:\TownApp\app\src\main\java\com\example\townapp\ui\screens" -Filter "*.kt"
$componentFiles = Get-ChildItem -Path "d:\TownApp\app\src\main\java\com\example\townapp\ui\components" -Filter "*.kt"
$files = $screenFiles + $componentFiles

function GetPaddingDimen($val) {
    switch ([int]$val) {
        24 { "AppDimens.paddingXXLarge" }
        20 { "AppDimens.paddingXLarge" }
        16 { "AppDimens.paddingLarge" }
        12 { "AppDimens.paddingMedium" }
        8  { "AppDimens.paddingSmall" }
        default { $null }
    }
}

function GetRadiusDimen($val) {
    switch ([int]$val) {
        20 { "AppDimens.radiusXLarge" }
        16 { "AppDimens.radiusLarge" }
        12 { "AppDimens.radiusMedium" }
        8  { "AppDimens.radiusSmall" }
        default { $null }
    }
}

foreach ($file in $files) {
    $content = Get-Content -Path $file.FullName -Raw -Encoding UTF8
    $originalContent = $content
    $needsImport = $false

    # 1. Simple padding(N.dp) - e.g., .padding(16.dp), Modifier.padding(8.dp)
    $content = [regex]::Replace($content, '(?<![\w.])padding\((\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "padding($d)" } else { $m.Value }
    })

    # 2. padding(vertical = N.dp)
    $content = [regex]::Replace($content, 'padding\(vertical = (\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "padding(vertical = $d)" } else { $m.Value }
    })

    # 3. padding(horizontal = N.dp)
    $content = [regex]::Replace($content, 'padding\(horizontal = (\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "padding(horizontal = $d)" } else { $m.Value }
    })

    # 4. padding(bottom = N.dp)
    $content = [regex]::Replace($content, 'padding\(bottom = (\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "padding(bottom = $d)" } else { $m.Value }
    })

    # 5. padding(top = N.dp)
    $content = [regex]::Replace($content, 'padding\(top = (\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "padding(top = $d)" } else { $m.Value }
    })

    # 6. padding(start = N.dp)
    $content = [regex]::Replace($content, 'padding\(start = (\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "padding(start = $d)" } else { $m.Value }
    })

    # 7. padding(end = N.dp)
    $content = [regex]::Replace($content, 'padding\(end = (\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "padding(end = $d)" } else { $m.Value }
    })

    # 8. Mixed padding: padding(horizontal = N.dp, vertical = M.dp)
    $content = [regex]::Replace($content, 'padding\(horizontal = (\d+)\.dp, vertical = (\d+)\.dp\)', {
        param($m)
        $h = GetPaddingDimen ([int]$m.Groups[1].Value)
        $v = GetPaddingDimen ([int]$m.Groups[2].Value)
        $r = "padding("
        if ($h) { $script:needsImport = $true; $r += "horizontal = $h" } else { $r += "horizontal = $($m.Groups[1].Value).dp" }
        $r += ", "
        if ($v) { $script:needsImport = $true; $r += "vertical = $v" } else { $r += "vertical = $($m.Groups[2].Value).dp" }
        $r += ")"
        $r
    })

    # 9. Mixed padding reversed: padding(vertical = N.dp, horizontal = M.dp)
    $content = [regex]::Replace($content, 'padding\(vertical = (\d+)\.dp, horizontal = (\d+)\.dp\)', {
        param($m)
        $v = GetPaddingDimen ([int]$m.Groups[1].Value)
        $h = GetPaddingDimen ([int]$m.Groups[2].Value)
        $r = "padding("
        if ($v) { $script:needsImport = $true; $r += "vertical = $v" } else { $r += "vertical = $($m.Groups[1].Value).dp" }
        $r += ", "
        if ($h) { $script:needsImport = $true; $r += "horizontal = $h" } else { $r += "horizontal = $($m.Groups[2].Value).dp" }
        $r += ")"
        $r
    })

    # 10. RoundedCornerShape(N.dp)
    $content = [regex]::Replace($content, 'RoundedCornerShape\((\d+)\.dp\)', {
        param($m)
        $d = GetRadiusDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "RoundedCornerShape($d)" } else { $m.Value }
    })

    # 11. RoundedCornerShape with named params - run up to 4 times for all corners
    for ($i = 0; $i -lt 4; $i++) {
        $content = [regex]::Replace($content, 'RoundedCornerShape\(([^)]*?\w+ = )(\d+)\.dp([^)]*?)\)', {
            param($m)
            $prefix = $m.Groups[1].Value
            $d = GetRadiusDimen ([int]$m.Groups[2].Value)
            $suffix = $m.Groups[3].Value
            if ($d) { $script:needsImport = $true; "RoundedCornerShape($prefix$d$suffix)" } else { $m.Value }
        })
    }

    # 12. cardElevation(defaultElevation = N.dp)
    $content = [regex]::Replace($content, 'cardElevation\(defaultElevation = (\d+)\.dp\)', {
        param($m)
        $val = [int]$m.Groups[1].Value
        if ($val -eq 4) { $script:needsImport = $true; "cardElevation(defaultElevation = AppDimens.cardElevation)" } else { $m.Value }
    })

    # 13. shadowElevation = N.dp
    $content = [regex]::Replace($content, 'shadowElevation = (\d+)\.dp', {
        param($m)
        $val = [int]$m.Groups[1].Value
        if ($val -eq 4) { $script:needsImport = $true; "shadowElevation = AppDimens.cardElevation" } else { $m.Value }
    })

    # 14. elevation = N.dp (Card elevation property)
    $content = [regex]::Replace($content, 'elevation = (\d+)\.dp', {
        param($m)
        $val = [int]$m.Groups[1].Value
        if ($val -eq 4) { $script:needsImport = $true; "elevation = AppDimens.cardElevation" } else { $m.Value }
    })

    # 15. Spacer with height or width
    $content = [regex]::Replace($content, '(Spacer\([^)]*?)(height|width)\((\d+)\.dp\)', {
        param($m)
        $prefix = $m.Groups[1].Value
        $func = $m.Groups[2].Value
        $d = GetPaddingDimen ([int]$m.Groups[3].Value)
        if ($d) { $script:needsImport = $true; "$prefix$func($d)" } else { $m.Value }
    })

    # 16. Arrangement.spacedBy(N.dp)
    $content = [regex]::Replace($content, 'Arrangement\.spacedBy\((\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "Arrangement.spacedBy($d)" } else { $m.Value }
    })

    # 17. PaddingValues(N.dp)
    $content = [regex]::Replace($content, 'PaddingValues\((\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "PaddingValues($d)" } else { $m.Value }
    })

    # 18. PaddingValues(horizontal = N.dp)
    $content = [regex]::Replace($content, 'PaddingValues\(horizontal = (\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "PaddingValues(horizontal = $d)" } else { $m.Value }
    })

    # 19. PaddingValues(vertical = N.dp)
    $content = [regex]::Replace($content, 'PaddingValues\(vertical = (\d+)\.dp\)', {
        param($m)
        $d = GetPaddingDimen ([int]$m.Groups[1].Value)
        if ($d) { $script:needsImport = $true; "PaddingValues(vertical = $d)" } else { $m.Value }
    })

    # 20. PaddingValues(horizontal = N.dp, vertical = M.dp)
    $content = [regex]::Replace($content, 'PaddingValues\(horizontal = (\d+)\.dp, vertical = (\d+)\.dp\)', {
        param($m)
        $h = GetPaddingDimen ([int]$m.Groups[1].Value)
        $v = GetPaddingDimen ([int]$m.Groups[2].Value)
        $r = "PaddingValues("
        if ($h) { $script:needsImport = $true; $r += "horizontal = $h" } else { $r += "horizontal = $($m.Groups[1].Value).dp" }
        $r += ", "
        if ($v) { $script:needsImport = $true; $r += "vertical = $v" } else { $r += "vertical = $($m.Groups[2].Value).dp" }
        $r += ")"
        $r
    })

    # Add import if needed
    if ($needsImport -and $content -notmatch 'import com\.example\.townapp\.ui\.theme\.AppDimens') {
        $content = $content -replace '(package com\.example\.townapp[^\n]*\n)', "`$1`nimport com.example.townapp.ui.theme.AppDimens`n"
    }

    if ($content -ne $originalContent) {
        Set-Content -Path $file.FullName -Value $content -NoNewline -Encoding UTF8
        Write-Host "Updated: $($file.FullName)"
    }
}
