$json = Get-Content 'app\src\main\assets\idioms\idioms.json' -Raw -Encoding UTF8 | ConvertFrom-Json

# 第一批已重写的词条ID
$batch1Ids = @(
    'da_gong_wu_si', 'she_ji_wei_ren', 'kong_rong_rang_li', 'ben_niao_xian_fei', 'chi_kui_shi_fu',
    'tian_dao_chou_qin', 'chi_de_ku_zhong_ku', 'bai_shan_xiao_wei_xian', 'ren_yi_shi_feng_ping_lang_jing', 'cheng_wang_bai_kou',
    'chu_ren_tou_di', 'ren_ding_sheng_tian', 'nan_er_you_lei_bu_qing_dan', 'qin_neng_bu_zhuo', 'shu_zhong_zi_you_huang_jin_wu',
    'nian_qing_jiu_yao_duo_chi_ku', 'san_shi_er_li', 'jia_he_wan_shi_xing', 'tian_xia_wu_bu_shi_de_fu_mu', 'nv_zi_wu_cai_bian_shi_de'
) | ForEach-Object { $_.ToLower() }

# 目标四大类
$targetCategories = @('CHARACTER', 'METHOD', 'INTERPERSONAL', 'RELATIONSHIP')

# 筛选尚未重写的目标词条
$targetItems = $json | Where-Object {
    ($targetCategories -contains $_.category) -and
    ($batch1Ids -notcontains $_.id.ToLower())
} | Select-Object id, idiom, category, toxicityLevel, cognitiveBiasTags

Write-Host "四大类中尚未重写的候选词条共 $($targetItems.Count) 条"
Write-Host "="

# 按毒性等级排序（POISONOUS/HARMFUL优先），然后输出
$targetItems = $targetItems | Sort-Object -Property @{Expression={
    switch ($_.toxicityLevel) {
        'POISONOUS' { 1 }
        'HARMFUL' { 2 }
        'DISTORTED' { 3 }
        'HERITAGE' { 4 }
        'TOWN_WISDOM' { 5 }
        default { 6 }
    }
}}

foreach ($item in $targetItems) {
    $tags = $item.cognitiveBiasTags -join ','
    $line = "$($item.id) / $($item.idiom) / $($item.category) / $($item.toxicityLevel) / $tags"
    Write-Host $line
}
