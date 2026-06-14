# Batch add spotlights to all idioms
# Following the principle of "All things are equal, each has its own light"

$inputFile = "d:\TownApp\app\src\main\java\com\example\townapp\data\idiom\IdiomCritiqueLibrary.kt"
$content = Get-Content -Path $inputFile -Raw -Encoding UTF8

# Spotlight templates
$spotlightTemplates = @(
    @("TRADITION", "As part of traditional culture, it carries specific historical memories"),
    @("FREEDOM", "In modern times, it can be a voluntary choice rather than a mandatory requirement"),
    @("RESILIENCE", "Reflects the perseverance to overcome difficulties"),
    @("STRATEGY", "Contains wisdom and strategies for dealing with people and situations"),
    @("EMOTION", "Can touch people's hearts and evoke emotional resonance"),
    @("INTERPERSONAL", "Reflects wisdom and sense of proportion in interpersonal communication"),
    @("SELF_CULTIVATION", "Shows the pursuit of self-cultivation and character building"),
    @("COGNITION", "Thinking from different perspectives can bring new insights"),
    @("CULTURE", "Embodies the profound and extensive nature of Chinese language and culture")
)

# Keywords for sacrifice idioms
$sacrificeKeywords = @("sheji", "wu si", "fengxian", "xisheng", "weigong", "jingcui", "jugong", "wangwo", "baoguo", "juanqu")
# Keywords for diligence idioms  
$diligenceKeywords = @("qinfen", "keke", "nuli", "xuexi", "zuanyan", "kudu", "hanchuang", "xuanliang", "cigu", "zaobi")
# Keywords for motivation idioms
$motivationKeywords = @("tuqiang", "fendou", "pinbo", "jinqu", "buxi", "xiangshang", "pandeng", "chaoyue")

function Get-SpotlightTemplates($idiom) {
    $templates = @()
    
    if ($sacrificeKeywords | Where-Object { $idiom.ToLower() -match $_ }) {
        $templates += @("TRADITION", "In ancient society, this spirit of dedication helped maintain group survival and stability")
        $templates += @("FREEDOM", "When it comes from the heart, this kindness is a warm light in human nature")
    }
    elseif ($diligenceKeywords | Where-Object { $idiom.ToLower() -match $_ }) {
        $templates += @("RESILIENCE", "Reflects the perseverance to overcome difficulties")
        $templates += @("SELF_CULTIVATION", "Shows a positive attitude towards self-improvement")
    }
    elseif ($motivationKeywords | Where-Object { $idiom.ToLower() -match $_ }) {
        $templates += @("RESILIENCE", "Transmits the spiritual strength to persevere in adversity")
        $templates += @("EMOTION", "Can inspire people to cheer up in difficult times")
    }
    
    $templates += @("TRADITION", "As part of traditional culture, it carries specific historical memories")
    $templates += @("CULTURE", "Embodies the profound and extensive nature of Chinese language and culture")
    
    return $templates
}

function Generate-SpotlightCode($idiom) {
    $templates = Get-SpotlightTemplates $idiom
    $selected = $templates | Get-Random -Count 2
    
    $code = @()
    foreach ($template in $selected) {
        $category = $template[0]
        $content = $template[1]
        $relevance = [math]::Round((Get-Random -Minimum 0.7 -Maximum 1.0), 1)
        $code += "                    Spotlight(SpotlightCategory.$category, `"$content`", $relevance)"
    }
    
    return $code -join ",`n"
}

$pattern = [regex]::new('(IdiomCritique\(\s*id = `"([^`"]+)`",\s*idiom = `"([^`"]+)`",.*?)(?=\s*category = )', [System.Text.RegularExpressions.RegexOptions]::Singleline)

$count = 0
$newContent = $pattern.Replace($content, {
    param($match)
    $fullMatch = $match.Groups[1].Value
    $idiom = $match.Groups[3].Value
    
    if ($fullMatch -match 'spotlights =') {
        return $fullMatch
    }
    
    $spotlightCode = Generate-SpotlightCode $idiom
    $result = "$fullMatch,`n            spotlights = listOf(`n$spotlightCode`n            )"
    
    $script:count++
    if ($script:count % 50 -eq 0) {
        Write-Host "Processed $($script:count) idioms..."
    }
    
    return $result
})

Set-Content -Path $inputFile -Value $newContent -Encoding UTF8 -NoNewline

Write-Host "`nDone! Added spotlights to $count idioms."