import json

with open('d:\\TownApp\\app\\src\\main\\assets\\idioms\\idioms.json', 'r', encoding='utf-8') as f:
    data = json.load(f)

completed = {
    'jia_he_wan_shi_xing', 'ren_qing_shi_gu', 'jia_ji_sui_ji_jia_gou_sui_gou',
    'fu_qi_mei_you_ge_ye_chou', 'lai_du_lai_le', 'duo_yi_shi_bu_ru_shao_yi_shi',
    'duo_ge_peng_you_duo_tiao_lu', 'jun_zi_zhi_jiao_dan_ru_shui', 'feng_ren_zhi_shuo_san_fen_hua',
    'jia_jia_you_ben_nan_nian_de_jing', 'zui_du_fu_ren_xin', 'gong_shuo_gong_you_li_po_shuo_po_you_li',
    'dao_bu_tong_bu_xiang_wei_mou', 'qian_ren_qian_mian', 'zhong_ren_jie_zui_wo_du_xing',
    'wu_du_bu_zhang_fu', 'ren_bu_wei_ji_tian_zhu_di_mie', 'wei_nv_zi_yu_xiao_ren_nan_yang_ye',
    'ren_ru_fu_zhong', 'an_pin_le_dao', 'yi_de_bao_yuan', 'tian_xia_wu_bu_shi_de_fu_mu',
    'nv_zi_wu_cai_bian_shi_de', 'cui_hun_wei_cheng', 'pin_jian_fu_qi_bai_shi_ai',
    'nv_ren_zhong_jiu_yao_gu_jia', 'jie_hun_cai_suan_wan_zheng', 'men_dang_hu_dui',
    'nan_zun_nv_bei', 'xian_cheng_jia_hou_li_ye', 'wang_zi_cheng_long',
    'fu_mu_du_shi_wei_ni_hao', 'gun_bang_di_xia_chu_xiao_zi'
}

needs_rewrite = []
for item in data['idioms']:
    cat = item.get('category', '')
    iid = item.get('id', '')
    if cat in ('INTERPERSONAL', 'RELATIONSHIP') and iid not in completed:
        needs_rewrite.append(iid)

print('COUNT:', len(needs_rewrite))
for iid in needs_rewrite:
    print(iid)
