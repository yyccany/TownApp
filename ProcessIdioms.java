import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class ProcessIdioms {
    public static void main(String[] args) throws Exception {
        Map<String, List<String>> tagsMap = new HashMap<>();
        tagsMap.put("ben_niao_xian_fei", Arrays.asList("社会对比偏差", "从众内卷偏差"));
        tagsMap.put("bai_shan_xiao_wei_xian", Arrays.asList("非黑即白思维"));
        tagsMap.put("ren_yi_shi_feng_ping_lang_jing", Arrays.asList("非黑即白思维"));
        tagsMap.put("cheng_wang_bai_kou", Arrays.asList("非黑即白思维"));
        tagsMap.put("chu_ren_tou_di", Arrays.asList("社会对比偏差"));
        tagsMap.put("jun_shi_tong_ming", Arrays.asList());
        tagsMap.put("lao_jin_cai_kong", Arrays.asList());
        tagsMap.put("fu_li_chang_liu", Arrays.asList());
        tagsMap.put("shen_xin_tong_yuan", Arrays.asList());
        tagsMap.put("ye_bu_cheng_mian", Arrays.asList());
        tagsMap.put("xian_xia_liao_yu", Arrays.asList());
        tagsMap.put("ri_ji_yue_lei", Arrays.asList());
        tagsMap.put("ge_an_qi_ming", Arrays.asList());
        tagsMap.put("shi_qu_ge_yi", Arrays.asList());
        tagsMap.put("chi_ku_fei_fu", Arrays.asList());
        tagsMap.put("tie_wan_yi_xiu", Arrays.asList());
        tagsMap.put("mian_zi_qiu_tu", Arrays.asList("社会对比偏差"));
        tagsMap.put("xin_xi_jing_wa", Arrays.asList("过度概括"));
        tagsMap.put("kuang_jia_qiu_tu", Arrays.asList());
        tagsMap.put("nu_li_fei_chong_fen", Arrays.asList("归因谬误"));
        tagsMap.put("bian_jie_fei_ren", Arrays.asList());
        tagsMap.put("she_jiao_fei_liang", Arrays.asList());
        tagsMap.put("kong_zhi_cuo_jue", Arrays.asList("归因谬误"));
        tagsMap.put("bei_gong_she_ying", Arrays.asList("过度概括"));
        tagsMap.put("fu_xing_pian_cha", Arrays.asList("过度概括"));
        tagsMap.put("kai_yuan_jie_liu", Arrays.asList());
        tagsMap.put("zhu_dong_zheng_qu", Arrays.asList());
        tagsMap.put("cui_hun_wei_cheng", Arrays.asList("从众内卷偏差"));
        tagsMap.put("chen_mo_cheng_ben", Arrays.asList("损失厌恶"));
        tagsMap.put("que_ren_pian_wu", Arrays.asList("过度概括"));
        tagsMap.put("xing_cun_zhe_pian_cha", Arrays.asList("幸存者偏差"));
        tagsMap.put("mao_ding_xiao_ying", Arrays.asList());
        tagsMap.put("hou_jian_zhi_ming_pian_wu", Arrays.asList("归因谬误"));
        tagsMap.put("ji_ben_gui_yin_cuo_wu", Arrays.asList("归因谬误"));
        tagsMap.put("ke_de_xing_qi_fa", Arrays.asList("过度概括"));
        tagsMap.put("kuang_jia_xiao_ying", Arrays.asList());
        tagsMap.put("deng_ning_ke_lu_ge_xiao_ying", Arrays.asList());
        tagsMap.put("sun_shi_yan_wu", Arrays.asList("损失厌恶"));
        tagsMap.put("ren_ding_sheng_tian", Arrays.asList("归因谬误"));
        tagsMap.put("nan_er_you_lei_bu_qing_dan", Arrays.asList("非黑即白思维"));
        tagsMap.put("qin_neng_bu_zhuo", Arrays.asList("归因谬误"));
        tagsMap.put("shu_zhong_zi_you_huang_jin_wu", Arrays.asList("过度概括"));
        tagsMap.put("shen_shou_bu_da_xiao_lian_ren", Arrays.asList());
        tagsMap.put("yi_ge_ba_zhang_pai_bu_xiang", Arrays.asList("过度概括"));
        tagsMap.put("shi_zhe_sheng_cun", Arrays.asList("归因谬误"));
        tagsMap.put("ke_lian_zhi_ren_bi_you_ke_hen_zhi_chu", Arrays.asList("归因谬误"));
        tagsMap.put("jia_he_wan_shi_xing", Arrays.asList());
        tagsMap.put("tian_xia_wu_bu_shi_de_fu_mu", Arrays.asList("非黑即白思维"));
        tagsMap.put("san_si_er_hou_xing", Arrays.asList());
        tagsMap.put("che_dao_shan_qian_bi_you_lu", Arrays.asList());
        tagsMap.put("tui_yi_bu_hai_kuo_tian_kong", Arrays.asList());
        tagsMap.put("yi_he_wei_gui", Arrays.asList("非黑即白思维"));
        tagsMap.put("da_shi_qin_ma_shi_ai", Arrays.asList("非黑即白思维"));
        tagsMap.put("jia_ji_sui_ji_jia_gou_sui_gou", Arrays.asList("非黑即白思维"));
        tagsMap.put("fu_qi_mei_you_ge_ye_chou", Arrays.asList());
        tagsMap.put("lai_du_lai_le", Arrays.asList("损失厌恶"));
        tagsMap.put("ren_qing_shi_gu", Arrays.asList());
        tagsMap.put("duo_yi_shi_bu_ru_shao_yi_shi", Arrays.asList("损失厌恶"));
        tagsMap.put("sai_weng_shi_ma_yan_zhi_fei_fu", Arrays.asList());
        tagsMap.put("ji_suo_bu_yu_wu_shi_yu_ren", Arrays.asList());
        tagsMap.put("shou_ren_yi_yu_bu_ru_shou_ren_yi_yu", Arrays.asList());
        tagsMap.put("nv_zi_wu_cai_bian_shi_de", Arrays.asList("非黑即白思维"));
        tagsMap.put("guang_zong_yao_zu", Arrays.asList("社会对比偏差"));
        tagsMap.put("bu_xiao_you_san_wu_hou_wei_da", Arrays.asList("从众内卷偏差"));
        tagsMap.put("hun_yu_cong_lai_bu_shi_yi_dao_xuan_ze_ti", Arrays.asList("非黑即白思维"));
        tagsMap.put("hai_zi_kang_ju_xue_xi_gen_yuan", Arrays.asList("归因谬误"));
        tagsMap.put("zhe_shan_wang_zhe_na_shan_gao", Arrays.asList("社会对比偏差"));
        tagsMap.put("da_zhong_lian_chong_pang_zi", Arrays.asList("社会对比偏差"));
        tagsMap.put("si_yao_mian_zi_huo_shou_zui", Arrays.asList("社会对比偏差"));
        tagsMap.put("zuo_jing_guan_tian", Arrays.asList());
        tagsMap.put("xia_chong_bu_ke_yu_bing", Arrays.asList());
        tagsMap.put("ji_tong_ya_jiang", Arrays.asList());
        tagsMap.put("ge_hang_ru_ge_shan", Arrays.asList("社会对比偏差"));
        tagsMap.put("ren_kao_yi_zhuang_ma_kao_an", Arrays.asList("社会对比偏差"));
        tagsMap.put("nian_qing_jiu_yao_duo_chi_ku", Arrays.asList("从众内卷偏差", "归因谬误"));
        tagsMap.put("an_wen_jiu_shi_tang_ping", Arrays.asList("非黑即白思维"));
        tagsMap.put("ren_wang_gao_chu_zou", Arrays.asList("社会对比偏差"));
        tagsMap.put("ao_chu_tou", Arrays.asList("幸存者偏差"));
        tagsMap.put("san_shi_er_li", Arrays.asList("从众内卷偏差", "社会对比偏差"));
        tagsMap.put("yi_fen_qian_yi_fen_huo", Arrays.asList("过度概括"));
        tagsMap.put("neng_sheng_ze_sheng", Arrays.asList("损失厌恶"));
        tagsMap.put("she_bu_de_hai_zi_tao_bu_zhe_lang", Arrays.asList("损失厌恶"));
        tagsMap.put("qin_jian_jie_yue_deng_yu_kou_men", Arrays.asList("非黑即白思维"));
        tagsMap.put("qiong_yang_er_fu_yang_nv", Arrays.asList("非黑即白思维"));
        tagsMap.put("gong_shuo_gong_you_li_po_shuo_po_you_li", Arrays.asList());
        tagsMap.put("dao_bu_tong_bu_xiang_wei_mou", Arrays.asList("非黑即白思维"));
        tagsMap.put("qian_ren_qian_mian", Arrays.asList());
        tagsMap.put("zhong_ren_jie_zui_wo_du_xing", Arrays.asList());
        tagsMap.put("wu_du_bu_zhang_fu", Arrays.asList("非黑即白思维"));
        tagsMap.put("ren_bu_wei_ji_tian_zhu_di_mie", Arrays.asList("归因谬误"));
        tagsMap.put("wei_nv_zi_yu_xiao_ren_nan_yang_ye", Arrays.asList("非黑即白思维"));
        tagsMap.put("ren_ru_fu_zhong", Arrays.asList("损失厌恶"));
        tagsMap.put("an_pin_le_dao", Arrays.asList("禀赋效应"));
        tagsMap.put("yi_de_bao_yuan", Arrays.asList());
        tagsMap.put("san_ge_chou_pi_jiang_ding_ge_zhu_ge_liang", Arrays.asList("过度概括"));
        tagsMap.put("jun_zi_zhi_jiao_dan_ru_shui", Arrays.asList());
        tagsMap.put("feng_ren_zhi_shuo_san_fen_hua", Arrays.asList());
        tagsMap.put("jia_jia_you_ben_nan_nian_de_jing", Arrays.asList());
        tagsMap.put("zui_du_fu_ren_xin", Arrays.asList("非黑即白思维"));
        tagsMap.put("cheng_jia_li_ye", Arrays.asList("从众内卷偏差"));
        tagsMap.put("pin_jian_fu_qi_bai_shi_ai", Arrays.asList("归因谬误"));
        tagsMap.put("nv_ren_zhong_jiu_yao_gu_jia", Arrays.asList("非黑即白思维"));
        tagsMap.put("jie_hun_cai_suan_wan_zheng", Arrays.asList("从众内卷偏差"));
        tagsMap.put("men_dang_hu_dui", Arrays.asList("社会对比偏差"));
        tagsMap.put("nan_zun_nv_bei", Arrays.asList("非黑即白思维"));
        tagsMap.put("xian_cheng_jia_hou_li_ye", Arrays.asList("从众内卷偏差"));
        tagsMap.put("wang_zi_cheng_long", Arrays.asList("社会对比偏差"));
        tagsMap.put("fu_mu_du_shi_wei_ni_hao", Arrays.asList("非黑即白思维"));
        tagsMap.put("gun_bang_di_xia_chu_xiao_zi", Arrays.asList("归因谬误"));
        tagsMap.put("xiao_hai_zi_dong_shen_me", Arrays.asList("非黑即白思维"));
        tagsMap.put("jun_zi_yuan_pao_chu", Arrays.asList("非黑即白思维"));
        tagsMap.put("xue_er_you_ze_shi", Arrays.asList("从众内卷偏差"));
        tagsMap.put("bu_gan_bu_jing_chi_le_mei_bing", Arrays.asList("过度概括"));
        tagsMap.put("zhong_kou_cai_xia_fan", Arrays.asList("过度概括"));
        tagsMap.put("ye_sheng_jiu_shi_geng_hao", Arrays.asList("过度概括"));
        tagsMap.put("shao_he_shui_mei_shi", Arrays.asList("过度概括"));
        tagsMap.put("ming_li_zhu_ding", Arrays.asList("归因谬误"));
        tagsMap.put("xing_zuo_ding_ren_sheng", Arrays.asList("归因谬误"));
        tagsMap.put("shi_pin_gai_yun", Arrays.asList("归因谬误"));
        tagsMap.put("yu_shi_guai_yun_qi", Arrays.asList("归因谬误"));
        tagsMap.put("jie_shi_jian_fei_yue_jian_yue_xu", Arrays.asList("过度概括"));
        tagsMap.put("nong_tang_da_bu_bu_chi_rou_zhang_pang", Arrays.asList("过度概括"));
        tagsMap.put("yun_qi_bi_xu_da_liang_jin_bu", Arrays.asList("过度概括"));
        tagsMap.put("pian_fang_shi_liao_sheng_guo_chi_yao", Arrays.asList("过度概括"));
        tagsMap.put("kao_shi_qian_qiu_fu_bai_fo", Arrays.asList("归因谬误"));
        tagsMap.put("tiao_cao_kan_huang_li", Arrays.asList("归因谬误"));
        tagsMap.put("sheng_bing_xian_zhao_feng_shui_xian_sheng", Arrays.asList("归因谬误"));
        tagsMap.put("gen_feng_wang_hong_xiao_fei", Arrays.asList("从众内卷偏差"));
        tagsMap.put("yong_she_chi_pin_zheng_ming_zhi_chang_di_wei", Arrays.asList("社会对比偏差"));
        tagsMap.put("fu_mu_bu_dong_wo", Arrays.asList());
        tagsMap.put("di_yu_qi_shi", Arrays.asList("过度概括"));
        tagsMap.put("bie_ren_jia_de_hai_zi", Arrays.asList("社会对比偏差"));
        tagsMap.put("san_shi_wu_sui_men_kan", Arrays.asList("社会对比偏差"));
        tagsMap.put("xue_li_jue_ding_yi_qie", Arrays.asList("过度概括"));
        tagsMap.put("wei_hai_zi_xi_sheng_suo_you", Arrays.asList("损失厌恶"));
        tagsMap.put("you_qian_cai_you_yi_qie", Arrays.asList("社会对比偏差"));
        tagsMap.put("chen_nian_qing_pin_ming_gao_qian", Arrays.asList("从众内卷偏差"));
        tagsMap.put("bie_ren_du_juan_wo_bu_nu_li_jiu_luo_hou", Arrays.asList("从众内卷偏差"));
        tagsMap.put("cu_cha_dan_fan", Arrays.asList("过度概括"));
        tagsMap.put("bing_cong_kou_ru", Arrays.asList("过度概括"));
        tagsMap.put("shi_bu_yan_jing_kuai_bu_yan_xi", Arrays.asList("过度概括"));
        tagsMap.put("chen_re_chi", Arrays.asList("过度概括"));
        tagsMap.put("he_bai_zhou_yang_wei", Arrays.asList("过度概括"));
        tagsMap.put("he_gu_tou_tang_bu_gai", Arrays.asList("过度概括"));
        tagsMap.put("yi_guan_chu_chu", Arrays.asList("社会对比偏差"));
        tagsMap.put("xin_san_nian_jiu_san_nian", Arrays.asList("损失厌恶"));
        tagsMap.put("zhi_chuan_gui_de_bu_chuan_dui_de", Arrays.asList("社会对比偏差"));
        tagsMap.put("yao_li_rong_jiu_shi_qiong_ren_de_diao", Arrays.asList("社会对比偏差"));
        tagsMap.put("an_ju_le_ye", Arrays.asList("从众内卷偏差"));
        tagsMap.put("jin_wo_yin_wo_bu_ru_zi_ji_de_gou_wo", Arrays.asList("禀赋效应"));
        tagsMap.put("fu_bu_zhu_da_wu_qiong_bu_xing_yuan_lu", Arrays.asList("损失厌恶"));
        tagsMap.put("mai_fang_cai_ti_mian_zu_fang_shi_guo_du", Arrays.asList("社会对比偏差"));
        tagsMap.put("an_bu_dang_che", Arrays.asList("损失厌恶"));
        tagsMap.put("zou_ma_guan_hua", Arrays.asList("从众内卷偏差"));
        tagsMap.put("xing_wan_li_lu_du_wan_juan_shu", Arrays.asList());

        List<String> lines = Files.readAllLines(Paths.get("app/src/main/assets/idioms/idioms.json"), StandardCharsets.UTF_8);
        List<String> result = new ArrayList<>();
        String currentId = null;
        boolean hasTags = false;
        int processed = 0;
        int skipped = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            result.add(line);

            // 检测 id 行
            if (line.trim().startsWith("\"id\":")) {
                String idStr = line.trim();
                int start = idStr.indexOf('"', idStr.indexOf(':')) + 1;
                int end = idStr.indexOf('"', start);
                if (end > start) {
                    currentId = idStr.substring(start, end);
                }
                hasTags = false;
            }

            // 检测是否已有 cognitiveBiasTags
            if (line.trim().startsWith("\"cognitiveBiasTags\":")) {
                hasTags = true;
            }

            // 检测 actionableTip 行
            if (line.trim().startsWith("\"actionableTip\":")) {
                // 查看下一行
                if (i + 1 < lines.size()) {
                    String nextLine = lines.get(i + 1);
                    // 如果下一行是 } 或 }, 说明需要插入标签
                    if (!hasTags && nextLine.trim().equals("}") || nextLine.trim().equals("},")) {
                        List<String> tags = tagsMap.get(currentId);
                        if (tags != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("        \"cognitiveBiasTags\": ");
                            if (tags.isEmpty()) {
                                sb.append("[]");
                            } else {
                                sb.append("[");
                                for (int j = 0; j < tags.size(); j++) {
                                    if (j > 0) sb.append(", ");
                                    sb.append("\"").append(tags.get(j)).append("\"");
                                }
                                sb.append("]");
                            }
                            result.add(sb.toString());
                            processed++;
                        } else {
                            // 未知 id，添加空数组
                            result.add("        \"cognitiveBiasTags\": []");
                            processed++;
                            System.out.println("Warning: No tags found for id: " + currentId);
                        }
                    } else if (hasTags) {
                        skipped++;
                    }
                }
            }
        }

        Files.write(Paths.get("app/src/main/assets/idioms/idioms.json"), result, StandardCharsets.UTF_8);
        System.out.println("Processed: " + processed + ", Skipped: " + skipped);
    }
}
