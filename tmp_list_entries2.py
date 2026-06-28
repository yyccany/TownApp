import json

with open(r'd:\TownApp\app\src\main\assets\idioms\idioms.json', 'r', encoding='utf-8') as f:
    data = json.load(f)

done_ids = {
    "ming_li_zhu_ding", "xing_zuo_ding_ren_sheng", "shi_pin_gai_yun", "yu_shi_guai_yun_qi",
    "kao_shi_qian_qiu_fu_bai_fo", "tiao_cao_kan_huang_li", "sheng_bing_xian_zhao_feng_shui_xian_sheng",
    "bu_gan_bu_jing_chi_le_mei_bing", "zhong_kou_cai_xia_fan", "ye_sheng_jiu_shi_geng_hao",
    "shao_he_shui_mei_shi", "jie_shi_jian_fei_yue_jian_yue_xu", "nong_tang_da_bu_chi_rou_zhang_pang",
    "yun_qi_bi_xu_da_liang_jin_bu", "pian_fang_shi_liao_sheng_guo_chi_yao", "chen_re_chi",
    "he_bai_zhou_yang_wei", "he_gu_tou_tang_bu_gai", "bing_cong_kou_ru", "shi_bu_yan_jing_kuai_bu_yan_xi",
    "dian_shi_cheng_jin", "chang_sheng_bu_lao"
}

categories = {"SOCIETY", "WORKPLACE", "CONSUMPTION", "COGNITION", "TOWN_SYSTEM"}

target_entries = []
for entry in data:
    cat = entry.get("category", "")
    eid = entry.get("id", "")
    if cat in categories and eid not in done_ids:
        target_entries.append(entry)

print(f"Total entries to rewrite: {len(target_entries)}")
for entry in target_entries:
    print(entry["id"], entry["category"], entry.get("idiom",""))

with open(r'd:\TownApp\tmp_entries_to_rewrite.json', 'w', encoding='utf-8') as f:
    json.dump(target_entries, f, ensure_ascii=False, indent=4)
