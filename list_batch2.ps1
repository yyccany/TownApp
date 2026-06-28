$json = Get-Content 'app\src\main\assets\idioms\idioms.json' -Raw -Encoding UTF8 | ConvertFrom-Json
$batch1 = @('da_gong_wu_si','she_ji_wei_ren','kong_rong_rang_li','ben_niao_xian_fei','chi_kui_shi_fu','tian_dao_chou_qin','chi_de_ku_zhong_ku','bai_shan_xiao_wei_xian','ren_yi_shi_feng_ping_lang_jing','cheng_wang_bai_kou','chu_ren_tou_di','ren_ding_sheng_tian','nan_er_you_lei_bu_qing_dan','qin_neng_bu_zhuo','shu_zhong_zi_you_huang_jin_wu','nian_qing_jiu_yao_duo_chi_ku','san_shi_er_li','jia_he_wan_shi_xing','tian_xia_wu_bu_shi_de_fu_mu','nv_zi_wu_cai_bian_shi_de')
$cats = @('CHARACTER','METHOD','INTERPERSONAL','RELATIONSHIP')
$items = $json | Where-Object { $cats -contains $_.category -and $batch1 -notcontains $_.id }
Write-Host ('候选共 ' + $items.Count + ' 条')
foreach($i in $items){ Write-Host ($i.id + ' / ' + $i.idiom + ' / ' + $i.category + ' / ' + $i.toxicityLevel) }
