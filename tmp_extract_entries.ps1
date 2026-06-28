$json = Get-Content 'd:\TownApp\app\src\main\assets\idioms\idioms.json' -Encoding UTF8 | ConvertFrom-Json

$targets = @(
    'chi_kui_shi_fu','bai_shan_xiao_wei_xian','ren_yi_shi_feng_ping_lang_jing',
    'shen_shou_bu_da_xiao_lian_ren','yi_ge_ba_zhang_pai_bu_xiang','da_shi_qin_ma_shi_ai',
    'ji_tong_ya_jiang','cheng_jia_li_ye','fu_mu_bu_dong_wo','wei_hai_zi_xi_sheng_suo_you'
)

foreach ($item in $json) {
    if ($targets -contains $item.id) {
        Write-Host "=== ID: $($item.id) ==="
        Write-Host "IDIOM: $($item.idiom)"
        Write-Host "CATEGORY: $($item.category)"
        Write-Host "TRADITIONAL_MEANING: $($item.traditionalMeaning)"
        Write-Host "DISTORTED_TRUTH: $($item.distortedTruth)"
        Write-Host "TOWN_PERSPECTIVE: $($item.townPerspective)"
        if ($item.examples) {
            Write-Host "EXAMPLES: $($item.examples)"
        }
        if ($item.actionableTip) {
            Write-Host "ACTIONABLE_TIP: $($item.actionableTip)"
        }
        if ($item.keyMessage) {
            Write-Host "KEY_MESSAGE: $($item.keyMessage)"
        }
        Write-Host ""
    }
}
