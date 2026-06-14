/**
 * ============================================
 * 🌟 万物薪俸小镇 - 宠物/五金工具/医疗健康 数据文件
 * ============================================
 * 宠物用品 ID: 11000~11049
 * 五金工具 ID: 12000~12049
 * 医疗健康 ID: 13000~13049
 * 宠物原料 ID: 11050~11069
 * 医疗原料 ID: 13050~13069
 * ============================================
 */

package com.example.townapp.data

import com.example.townapp.data.ContactType
import com.example.townapp.data.PixelShapeType
import com.example.townapp.data.ProductItem
import com.example.townapp.data.CompositionRelation
import com.example.townapp.data.MaterialItem

// ============================================
// 🐾 宠物用品成品数据 (ID: 11001~11030)
// ============================================
val petSupplies = listOf(
    ProductItem(id = 11001, name = "宠物笼(小型)", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "小型犬/猫笼 60x40x50cm"),
    ProductItem(id = 11002, name = "宠物笼(中型)", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "中型犬笼 80x60x70cm"),
    ProductItem(id = 11003, name = "牵引绳(小型犬)", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "小型犬用 1.2m 尼龙材质"),
    ProductItem(id = 11004, name = "牵引绳(大型犬)", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "大型犬用 1.5m 加厚尼龙"),
    ProductItem(id = 11005, name = "宠物窝(圆形)", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "直径50cm 加厚绒面猫窝"),
    ProductItem(id = 11006, name = "宠物窝(方形)", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "60x50cm 可拆洗狗窝"),
    ProductItem(id = 11007, name = "磨牙棒(狗用)", category = "宠物用品", contactType = ContactType.EDIBLE, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "牛皮卷磨牙棒 12cm"),
    ProductItem(id = 11008, name = "猫抓板", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "瓦楞纸猫抓板 45x20cm"),
    ProductItem(id = 11009, name = "逗猫棒", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "伸缩杆+羽毛玩具"),
    ProductItem(id = 11010, name = "宠物食盆", category = "宠物用品", contactType = ContactType.EDIBLE, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "不锈钢双碗 防滑底座"),
    ProductItem(id = 11011, name = "宠物水碗", category = "宠物用品", contactType = ContactType.EDIBLE, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "悬挂式饮水碗 500ml"),
    ProductItem(id = 11012, name = "宠物航空箱", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "中型航空箱 可登机"),
    ProductItem(id = 11013, name = "宠物梳子(短毛)", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "短毛犬猫专用 硅胶梳齿"),
    ProductItem(id = 11014, name = "宠物梳子(长毛)", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "长毛犬猫专用 不锈钢梳齿"),
    ProductItem(id = 11015, name = "宠物指甲剪", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "防剪错宠物指甲钳"),
    ProductItem(id = 11016, name = "宠物尿垫", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "加厚吸水 60x90cm 10片装"),
    ProductItem(id = 11017, name = "猫砂盆", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "全封闭式猫砂盆 带铲"),
    ProductItem(id = 11018, name = "宠物玩具球", category = "宠物用品", contactType = ContactType.EDIBLE, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "耐咬橡胶球 直径6cm"),
    ProductItem(id = 11019, name = "宠物飞盘", category = "宠物用品", contactType = ContactType.EDIBLE, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "软胶宠物飞盘 25cm"),
    ProductItem(id = 11020, name = "宠物项圈(皮质)", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "真皮项圈 可调节 30-50cm"),
    ProductItem(id = 11021, name = "宠物胸背带", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "防挣脱胸背带 可调节"),
    ProductItem(id = 11022, name = "宠物床垫", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "加厚记忆棉宠物床垫"),
    ProductItem(id = 11023, name = "宠物自动喂食器", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "定时定量自动投食 3L"),
    ProductItem(id = 11024, name = "宠物饮水机", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "循环活水 2L 静音水泵"),
    ProductItem(id = 11025, name = "宠物厕所(狗用)", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "诱导厕所 带围栏 60x50cm"),
    ProductItem(id = 11026, name = "宠物训练咬胶", category = "宠物用品", contactType = ContactType.EDIBLE, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "鸡肉味咬胶 12cm 5支装"),
    ProductItem(id = 11027, name = "宠物吊床", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "窗边吸盘吊床 承重15kg"),
    ProductItem(id = 11028, name = "宠物户外背包", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "宠物户外太空舱背包"),
    ProductItem(id = 11029, name = "牵引绳(伸缩)", category = "宠物用品", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "自动伸缩牵引绳 5m"),
    ProductItem(id = 11030, name = "宠物毛发收集器", category = "宠物用品", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "静电吸附宠物毛刷")
)

// ============================================
// 🛠️ 五金工具成品数据 (ID: 12001~12030)
// ============================================
val toolProducts = listOf(
    ProductItem(id = 12001, name = "羊角锤", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "0.5kg 羊角拔钉锤 防滑手柄"),
    ProductItem(id = 12002, name = "螺丝刀套装", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "6件套 十字一字 铬钒钢"),
    ProductItem(id = 12003, name = "活动扳手", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "12寸 铬钒合金 最大开口36mm"),
    ProductItem(id = 12004, name = "尖嘴钳", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "6寸 尖嘴钳 铬钒钢锻造"),
    ProductItem(id = 12005, name = "老虎钳", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "8寸 老虎钳 钢丝钳 PVC手柄"),
    ProductItem(id = 12006, name = "手电钻", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "600W 手电钻 10mm夹头"),
    ProductItem(id = 12007, name = "冲击钻", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "800W 冲击钻 混凝土钻孔"),
    ProductItem(id = 12008, name = "角磨机", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "100型 角磨机 12000rpm"),
    ProductItem(id = 12009, name = "电圆锯", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "7寸 电圆锯 185mm锯片 1600W"),
    ProductItem(id = 12010, name = "卷尺(5m)", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "5m 钢卷尺 自动回弹"),
    ProductItem(id = 12011, name = "水平尺", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "600mm 铝合金水平尺 三水泡"),
    ProductItem(id = 12012, name = "游标卡尺", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "0-150mm 不锈钢游标卡尺"),
    ProductItem(id = 12013, name = "安全帽", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "ABS安全帽 可调节 国标认证"),
    ProductItem(id = 12014, name = "劳保手套", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "耐磨防滑 乳胶涂层 12双装"),
    ProductItem(id = 12015, name = "护目镜", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "防飞溅防雾 聚碳酸酯镜片"),
    ProductItem(id = 12016, name = "安全绳", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "全身式安全带 2m缓冲绳"),
    ProductItem(id = 12017, name = "劳保鞋", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "钢头防刺穿 防滑底 42码"),
    ProductItem(id = 12018, name = "人字梯(家用)", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "铝合金人字梯 1.5m 承重100kg"),
    ProductItem(id = 12019, name = "工具收纳箱", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "PP塑料工具箱 18寸 双层收纳"),
    ProductItem(id = 12020, name = "工具腰带", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "加厚帆布工具腰带 8个口袋"),
    ProductItem(id = 12021, name = "手锯", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "折叠手锯 300mm 三面磨齿"),
    ProductItem(id = 12022, name = "手摇钻", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "手摇钻 夹持1-6mm 齿轮传动"),
    ProductItem(id = 12023, name = "锉刀套装", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "5件套 金刚石锉刀 细中粗齿"),
    ProductItem(id = 12024, name = "台虎钳", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "4寸 台虎钳 铸铁 360°旋转"),
    ProductItem(id = 12025, name = "电焊机", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "220V 逆变式电焊机 双电压"),
    ProductItem(id = 12026, name = "热风枪", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "2000W 热风枪 50-600℃可调"),
    ProductItem(id = 12027, name = "激光测距仪", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "40m 激光测距 精度±2mm"),
    ProductItem(id = 12028, name = "防尘口罩", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "KN95防尘口罩 带呼吸阀"),
    ProductItem(id = 12029, name = "耳塞(降噪)", category = "五金工具", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "降噪耳塞 SNR 37dB 硅胶材质"),
    ProductItem(id = 12030, name = "多功能工具箱", category = "五金工具", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "30件套 工具箱 含常用工具")
)

// ============================================
// 🏥 医疗健康成品数据 (ID: 13001~13030)
// ============================================
val medicalProducts = listOf(
    ProductItem(id = 13001, name = "创可贴(标准)", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "透气型 72x19mm 100片装"),
    ProductItem(id = 13002, name = "医用纱布(卷)", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.VERTICAL, description = "无菌纱布 8x600cm 医用级"),
    ProductItem(id = 13003, name = "医用胶带", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.VERTICAL, description = "透气无纺布胶带 1.25cmx5m"),
    ProductItem(id = 13004, name = "医用棉签(无菌)", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.VERTICAL, description = "无菌竹棒棉签 10cm 100支装"),
    ProductItem(id = 13005, name = "医用棉球", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "灭菌棉球 50g 袋装"),
    ProductItem(id = 13006, name = "电子体温计", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.VERTICAL, description = "电子数显 腋下/口腔 30秒速测"),
    ProductItem(id = 13007, name = "血压计(上臂式)", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "全自动上臂式 智能加压"),
    ProductItem(id = 13008, name = "血糖仪(家用)", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "家用血糖仪 赠试纸50条"),
    ProductItem(id = 13009, name = "一次性口罩(医用)", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "三层医用口罩 蓝色 50只装"),
    ProductItem(id = 13010, name = "KN95口罩", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "KN95五层防护 20只装"),
    ProductItem(id = 13011, name = "防护服(医用)", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "一次性医用防护服 连体"),
    ProductItem(id = 13012, name = "止痛贴膏", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "消炎镇痛贴 10cmx7cm 10片装"),
    ProductItem(id = 13013, name = "创口贴(防水)", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.HORIZONTAL, description = "防水型创口贴 透明PU膜 20片"),
    ProductItem(id = 13014, name = "医用弹性绷带", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.VERTICAL, description = "弹性绷带 8cmx450cm 自粘型"),
    ProductItem(id = 13015, name = "碘伏消毒液", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "碘伏消毒液 100ml 瓶装"),
    ProductItem(id = 13016, name = "75%酒精", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.VERTICAL, description = "75%医用酒精 500ml 喷雾瓶"),
    ProductItem(id = 13017, name = "医用一次性手套", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "无粉乳胶手套 中号 100只装"),
    ProductItem(id = 13018, name = "速效救心丸", category = "医疗健康", contactType = ContactType.EDIBLE, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "急救药品 40mgx60粒 舌下含服"),
    ProductItem(id = 13019, name = "板蓝根颗粒", category = "医疗健康", contactType = ContactType.EDIBLE, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "板蓝根颗粒 10gx20袋 冲剂",
        physicalQuant = PhysicalQuantification(
            molecularFormula = "植物提取物", activeIngredientRatio = 8.0, metabolicHalfLifeH = 1.5, giStimulationRate = 2.0, glycemicIndex = 25, emptyCalorieRatio = 5.0
        )),
    ProductItem(id = 13020, name = "维生素C片", category = "医疗健康", contactType = ContactType.EDIBLE, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "维生素C 100mgx100片 咀嚼片",
        physicalQuant = PhysicalQuantification(
            molecularFormula = "C₆H₈O₆", activeIngredientRatio = 95.0, metabolicHalfLifeH = 0.5, giStimulationRate = 3.0, glycemicIndex = 0
        )),
    ProductItem(id = 13021, name = "钙片(成人)", category = "医疗健康", contactType = ContactType.EDIBLE, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "碳酸钙D3 60片 瓶装"),
    ProductItem(id = 13022, name = "急救包(家用)", category = "医疗健康", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.HORIZONTAL, description = "家用急救包 20件套 含说明书"),
    ProductItem(id = 13023, name = "氧气袋", category = "医疗健康", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "42L 氧气袋 医用PVC 配导管"),
    ProductItem(id = 13024, name = "冰袋(可重复)", category = "医疗健康", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "凝胶冰袋 可重复使用 200g"),
    ProductItem(id = 13025, name = "医用剪刀", category = "医疗健康", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "不锈钢医用剪 14cm 直头"),
    ProductItem(id = 13026, name = "止血带", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.VERTICAL, description = "医用止血带 2.5cmx45cm 卡扣式"),
    ProductItem(id = 13027, name = "医用敷贴", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "无菌敷贴 10x15cm 5片装"),
    ProductItem(id = 13028, name = "口罩(儿童)", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "儿童医用口罩 三层 30只装"),
    ProductItem(id = 13029, name = "一次性帽子(医用)", category = "医疗健康", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "无纺布条形帽 蓝色 100只装"),
    ProductItem(id = 13030, name = "医用垃圾桶", category = "医疗健康", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "医疗废物垃圾桶 10L 脚踏式")
)

// ============================================
// 🐾 宠物用品 - 成品组成关系 (CompositionRelation)
// ============================================
val petCompositions = listOf(
    // 宠物笼(小型): 钢材+塑料外壳+包装
    CompositionRelation(productId = 11001, materialId = 7001, useAmount = 2.0, unit = "千克"),
    CompositionRelation(productId = 11001, materialId = 6007, useAmount = 0.5, unit = "个"),
    CompositionRelation(productId = 11001, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物笼(中型): 钢材+塑料外壳+包装
    CompositionRelation(productId = 11002, materialId = 7001, useAmount = 3.5, unit = "千克"),
    CompositionRelation(productId = 11002, materialId = 6007, useAmount = 0.8, unit = "个"),
    CompositionRelation(productId = 11002, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 牵引绳(小型犬): 纺织纤维+塑料外壳+包装
    CompositionRelation(productId = 11003, materialId = 7010, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 11003, materialId = 6007, useAmount = 0.1, unit = "个"),
    CompositionRelation(productId = 11003, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 牵引绳(大型犬): 纺织纤维+橡胶+包装
    CompositionRelation(productId = 11004, materialId = 7010, useAmount = 0.4, unit = "千克"),
    CompositionRelation(productId = 11004, materialId = 7018, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 11004, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物窝(圆形): 纺织纤维+合成橡胶+包装
    CompositionRelation(productId = 11005, materialId = 7010, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 11005, materialId = 7018, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 11005, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物窝(方形): 纺织纤维+木材+包装
    CompositionRelation(productId = 11006, materialId = 7010, useAmount = 0.6, unit = "千克"),
    CompositionRelation(productId = 11006, materialId = 5005, useAmount = 0.3, unit = "立方米"),
    CompositionRelation(productId = 11006, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 磨牙棒(狗用): 皮革+包装
    CompositionRelation(productId = 11007, materialId = 7009, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 11007, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 猫抓板: 木材+包装
    CompositionRelation(productId = 11008, materialId = 5005, useAmount = 0.2, unit = "立方米"),
    CompositionRelation(productId = 11008, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 逗猫棒: 纺织纤维+塑料外壳+包装
    CompositionRelation(productId = 11009, materialId = 7010, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 11009, materialId = 6007, useAmount = 0.1, unit = "个"),
    CompositionRelation(productId = 11009, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物食盆: 塑料外壳+包装
    CompositionRelation(productId = 11010, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 11010, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物水碗: 塑料外壳+包装
    CompositionRelation(productId = 11011, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 11011, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物航空箱: 塑料外壳+铝材+包装
    CompositionRelation(productId = 11012, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 11012, materialId = 7002, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 11012, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物梳子(短毛): 塑料外壳+橡胶+包装
    CompositionRelation(productId = 11013, materialId = 6007, useAmount = 0.5, unit = "个"),
    CompositionRelation(productId = 11013, materialId = 7018, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 11013, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物梳子(长毛): 塑料外壳+钢材+包装
    CompositionRelation(productId = 11014, materialId = 6007, useAmount = 0.5, unit = "个"),
    CompositionRelation(productId = 11014, materialId = 7001, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 11014, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物指甲剪: 钢材+塑料外壳+包装
    CompositionRelation(productId = 11015, materialId = 7001, useAmount = 0.08, unit = "千克"),
    CompositionRelation(productId = 11015, materialId = 6007, useAmount = 0.3, unit = "个"),
    CompositionRelation(productId = 11015, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物尿垫: 纺织纤维+包装
    CompositionRelation(productId = 11016, materialId = 7010, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 11016, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 猫砂盆: 塑料外壳+包装
    CompositionRelation(productId = 11017, materialId = 6007, useAmount = 1.5, unit = "个"),
    CompositionRelation(productId = 11017, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物玩具球: 合成橡胶+包装
    CompositionRelation(productId = 11018, materialId = 7018, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 11018, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物飞盘: 合成橡胶+包装
    CompositionRelation(productId = 11019, materialId = 7018, useAmount = 0.15, unit = "千克"),
    CompositionRelation(productId = 11019, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物项圈(皮质): 皮革+塑料外壳+包装
    CompositionRelation(productId = 11020, materialId = 7009, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 11020, materialId = 6007, useAmount = 0.2, unit = "个"),
    CompositionRelation(productId = 11020, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物胸背带: 纺织纤维+塑料外壳+包装
    CompositionRelation(productId = 11021, materialId = 7010, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 11021, materialId = 6007, useAmount = 0.3, unit = "个"),
    CompositionRelation(productId = 11021, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物床垫: 纺织纤维+合成橡胶+包装
    CompositionRelation(productId = 11022, materialId = 7010, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 11022, materialId = 7018, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 11022, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物自动喂食器: 塑料外壳+传感器+CPU+包装
    CompositionRelation(productId = 11023, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 11023, materialId = 6009, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 11023, materialId = 6013, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 11023, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物饮水机: 塑料外壳+传感器+包装
    CompositionRelation(productId = 11024, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 11024, materialId = 6009, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 11024, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物厕所(狗用): 塑料外壳+包装
    CompositionRelation(productId = 11025, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 11025, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物训练咬胶: 皮革+包装
    CompositionRelation(productId = 11026, materialId = 7009, useAmount = 0.15, unit = "千克"),
    CompositionRelation(productId = 11026, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物吊床: 纺织纤维+塑料外壳+包装
    CompositionRelation(productId = 11027, materialId = 7010, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 11027, materialId = 6007, useAmount = 0.3, unit = "个"),
    CompositionRelation(productId = 11027, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物户外背包: 纺织纤维+塑料外壳+包装
    CompositionRelation(productId = 11028, materialId = 7010, useAmount = 0.8, unit = "千克"),
    CompositionRelation(productId = 11028, materialId = 6007, useAmount = 0.5, unit = "个"),
    CompositionRelation(productId = 11028, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 牵引绳(伸缩): 塑料外壳+纺织纤维+包装
    CompositionRelation(productId = 11029, materialId = 6007, useAmount = 0.5, unit = "个"),
    CompositionRelation(productId = 11029, materialId = 7010, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 11029, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 宠物毛发收集器: 塑料外壳+橡胶+包装
    CompositionRelation(productId = 11030, materialId = 6007, useAmount = 0.5, unit = "个"),
    CompositionRelation(productId = 11030, materialId = 7018, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 11030, materialId = 20008, useAmount = 1.0, unit = "次")
)

// ============================================
// 🛠️ 五金工具 - 成品组成关系 (CompositionRelation)
// ============================================
val toolCompositions = listOf(
    // 羊角锤: 钢材+木材+包装
    CompositionRelation(productId = 12001, materialId = 7001, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 12001, materialId = 5005, useAmount = 0.1, unit = "立方米"),
    CompositionRelation(productId = 12001, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 螺丝刀套装: 钢材+塑料外壳+包装
    CompositionRelation(productId = 12002, materialId = 7001, useAmount = 0.4, unit = "千克"),
    CompositionRelation(productId = 12002, materialId = 6007, useAmount = 0.5, unit = "个"),
    CompositionRelation(productId = 12002, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 活动扳手: 钢材+包装
    CompositionRelation(productId = 12003, materialId = 7001, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 12003, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 尖嘴钳: 钢材+橡胶+包装
    CompositionRelation(productId = 12004, materialId = 7001, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 12004, materialId = 7018, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 12004, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 老虎钳: 钢材+橡胶+包装
    CompositionRelation(productId = 12005, materialId = 7001, useAmount = 0.4, unit = "千克"),
    CompositionRelation(productId = 12005, materialId = 7018, useAmount = 0.08, unit = "千克"),
    CompositionRelation(productId = 12005, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 手电钻: 塑料外壳+钢材+铜线+包装
    CompositionRelation(productId = 12006, materialId = 6007, useAmount = 1.5, unit = "个"),
    CompositionRelation(productId = 12006, materialId = 7001, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 12006, materialId = 5008, useAmount = 5.0, unit = "米"),
    CompositionRelation(productId = 12006, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 冲击钻: 塑料外壳+钢材+铜线+包装
    CompositionRelation(productId = 12007, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 12007, materialId = 7001, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 12007, materialId = 5008, useAmount = 8.0, unit = "米"),
    CompositionRelation(productId = 12007, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 角磨机: 塑料外壳+钢材+铝材+包装
    CompositionRelation(productId = 12008, materialId = 6007, useAmount = 1.5, unit = "个"),
    CompositionRelation(productId = 12008, materialId = 7001, useAmount = 0.4, unit = "千克"),
    CompositionRelation(productId = 12008, materialId = 7002, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 12008, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 电圆锯: 钢材+铝材+塑料外壳+包装
    CompositionRelation(productId = 12009, materialId = 7001, useAmount = 1.2, unit = "千克"),
    CompositionRelation(productId = 12009, materialId = 7002, useAmount = 0.8, unit = "千克"),
    CompositionRelation(productId = 12009, materialId = 6007, useAmount = 1.5, unit = "个"),
    CompositionRelation(productId = 12009, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 卷尺(5m): 钢材+塑料外壳+包装
    CompositionRelation(productId = 12010, materialId = 7001, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 12010, materialId = 6007, useAmount = 0.3, unit = "个"),
    CompositionRelation(productId = 12010, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 水平尺: 铝材+塑料外壳+包装
    CompositionRelation(productId = 12011, materialId = 7002, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 12011, materialId = 6007, useAmount = 0.2, unit = "个"),
    CompositionRelation(productId = 12011, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 游标卡尺: 钢材+包装
    CompositionRelation(productId = 12012, materialId = 7001, useAmount = 0.15, unit = "千克"),
    CompositionRelation(productId = 12012, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 安全帽: 塑料外壳+包装
    CompositionRelation(productId = 12013, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 12013, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 劳保手套: 纺织纤维+橡胶+包装
    CompositionRelation(productId = 12014, materialId = 7010, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 12014, materialId = 7018, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 12014, materialId = 20007, useAmount = 1.0, unit = "次"),
    // 护目镜: 塑料外壳+包装
    CompositionRelation(productId = 12015, materialId = 6007, useAmount = 0.5, unit = "个"),
    CompositionRelation(productId = 12015, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 安全绳: 纺织纤维+钢材+包装
    CompositionRelation(productId = 12016, materialId = 7010, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 12016, materialId = 7001, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 12016, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 劳保鞋: 皮革+钢材+橡胶+包装
    CompositionRelation(productId = 12017, materialId = 7009, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 12017, materialId = 7001, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 12017, materialId = 7018, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 12017, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 人字梯(家用): 铝材+包装
    CompositionRelation(productId = 12018, materialId = 7002, useAmount = 2.5, unit = "千克"),
    CompositionRelation(productId = 12018, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 工具收纳箱: 塑料外壳+包装
    CompositionRelation(productId = 12019, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 12019, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 工具腰带: 纺织纤维+塑料外壳+包装
    CompositionRelation(productId = 12020, materialId = 7010, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 12020, materialId = 6007, useAmount = 0.2, unit = "个"),
    CompositionRelation(productId = 12020, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 手锯: 钢材+木材+包装
    CompositionRelation(productId = 12021, materialId = 7001, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 12021, materialId = 5005, useAmount = 0.08, unit = "立方米"),
    CompositionRelation(productId = 12021, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 手摇钻: 钢材+塑料外壳+包装
    CompositionRelation(productId = 12022, materialId = 7001, useAmount = 0.4, unit = "千克"),
    CompositionRelation(productId = 12022, materialId = 6007, useAmount = 0.3, unit = "个"),
    CompositionRelation(productId = 12022, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 锉刀套装: 钢材+塑料外壳+包装
    CompositionRelation(productId = 12023, materialId = 7001, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 12023, materialId = 6007, useAmount = 0.3, unit = "个"),
    CompositionRelation(productId = 12023, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 台虎钳: 钢材+包装
    CompositionRelation(productId = 12024, materialId = 7001, useAmount = 3.0, unit = "千克"),
    CompositionRelation(productId = 12024, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 电焊机: 塑料外壳+钢材+铜线+CPU+包装
    CompositionRelation(productId = 12025, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 12025, materialId = 7001, useAmount = 1.5, unit = "千克"),
    CompositionRelation(productId = 12025, materialId = 5008, useAmount = 10.0, unit = "米"),
    CompositionRelation(productId = 12025, materialId = 6013, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 12025, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 热风枪: 塑料外壳+钢材+包装
    CompositionRelation(productId = 12026, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 12026, materialId = 7001, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 12026, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 激光测距仪: 塑料外壳+传感器+CPU+包装
    CompositionRelation(productId = 12027, materialId = 6007, useAmount = 0.5, unit = "个"),
    CompositionRelation(productId = 12027, materialId = 6009, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 12027, materialId = 6013, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 12027, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 防尘口罩: 纺织纤维+塑料外壳+包装
    CompositionRelation(productId = 12028, materialId = 7010, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 12028, materialId = 6007, useAmount = 0.1, unit = "个"),
    CompositionRelation(productId = 12028, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 耳塞(降噪): 合成橡胶+包装
    CompositionRelation(productId = 12029, materialId = 7018, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 12029, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 多功能工具箱: 钢材+塑料外壳+包装
    CompositionRelation(productId = 12030, materialId = 7001, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 12030, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 12030, materialId = 20008, useAmount = 1.0, unit = "次")
)

// ============================================
// 🏥 医疗健康 - 成品组成关系 (CompositionRelation)
// ============================================
val medicalCompositions = listOf(
    // 创可贴(标准): 纺织纤维+包装
    CompositionRelation(productId = 13001, materialId = 7010, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 13001, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 医用纱布(卷): 纺织纤维+包装
    CompositionRelation(productId = 13002, materialId = 7010, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 13002, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 医用胶带: 纺织纤维+包装
    CompositionRelation(productId = 13003, materialId = 7010, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 13003, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 医用棉签(无菌): 木材+纺织纤维+包装
    CompositionRelation(productId = 13004, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 13004, materialId = 7010, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 13004, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 医用棉球: 纺织纤维+包装
    CompositionRelation(productId = 13005, materialId = 7010, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 13005, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 电子体温计: 塑料外壳+传感器+CPU+包装
    CompositionRelation(productId = 13006, materialId = 6007, useAmount = 0.3, unit = "个"),
    CompositionRelation(productId = 13006, materialId = 6009, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 13006, materialId = 6013, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 13006, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 血压计(上臂式): 塑料外壳+传感器+CPU+包装
    CompositionRelation(productId = 13007, materialId = 6007, useAmount = 1.5, unit = "个"),
    CompositionRelation(productId = 13007, materialId = 6009, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 13007, materialId = 6013, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 13007, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 血糖仪(家用): 塑料外壳+传感器+CPU+包装
    CompositionRelation(productId = 13008, materialId = 6007, useAmount = 0.5, unit = "个"),
    CompositionRelation(productId = 13008, materialId = 6009, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 13008, materialId = 6013, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 13008, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 一次性口罩(医用): 纺织纤维+包装
    CompositionRelation(productId = 13009, materialId = 7010, useAmount = 0.03, unit = "千克"),
    CompositionRelation(productId = 13009, materialId = 20008, useAmount = 1.0, unit = "次"),
    // KN95口罩: 纺织纤维+塑料外壳+包装
    CompositionRelation(productId = 13010, materialId = 7010, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 13010, materialId = 6007, useAmount = 0.1, unit = "个"),
    CompositionRelation(productId = 13010, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 防护服(医用): 纺织纤维+包装
    CompositionRelation(productId = 13011, materialId = 7010, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 13011, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 止痛贴膏: 纺织纤维+包装
    CompositionRelation(productId = 13012, materialId = 7010, useAmount = 0.03, unit = "千克"),
    CompositionRelation(productId = 13012, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 创口贴(防水): 塑料外壳+包装
    CompositionRelation(productId = 13013, materialId = 6007, useAmount = 0.1, unit = "个"),
    CompositionRelation(productId = 13013, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 医用弹性绷带: 纺织纤维+包装
    CompositionRelation(productId = 13014, materialId = 7010, useAmount = 0.08, unit = "千克"),
    CompositionRelation(productId = 13014, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 碘伏消毒液: 塑料外壳+包装
    CompositionRelation(productId = 13015, materialId = 6007, useAmount = 0.2, unit = "个"),
    CompositionRelation(productId = 13015, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 75%酒精: 塑料外壳+包装
    CompositionRelation(productId = 13016, materialId = 6007, useAmount = 0.3, unit = "个"),
    CompositionRelation(productId = 13016, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 医用一次性手套: 合成橡胶+包装
    CompositionRelation(productId = 13017, materialId = 7018, useAmount = 0.08, unit = "千克"),
    CompositionRelation(productId = 13017, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 速效救心丸: 包装+人工工时
    CompositionRelation(productId = 13018, materialId = 20008, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 13018, materialId = 20001, useAmount = 0.5, unit = "小时"),
    // 板蓝根颗粒: 包装+人工工时
    CompositionRelation(productId = 13019, materialId = 20008, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 13019, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 维生素C片: 包装+人工工时
    CompositionRelation(productId = 13020, materialId = 20008, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 13020, materialId = 20001, useAmount = 0.4, unit = "小时"),
    // 钙片(成人): 包装+人工工时
    CompositionRelation(productId = 13021, materialId = 20008, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 13021, materialId = 20001, useAmount = 0.4, unit = "小时"),
    // 急救包(家用): 塑料外壳+包装
    CompositionRelation(productId = 13022, materialId = 6007, useAmount = 1.5, unit = "个"),
    CompositionRelation(productId = 13022, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 氧气袋: 合成橡胶+包装
    CompositionRelation(productId = 13023, materialId = 7018, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 13023, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 冰袋(可重复): 塑料外壳+包装
    CompositionRelation(productId = 13024, materialId = 6007, useAmount = 0.3, unit = "个"),
    CompositionRelation(productId = 13024, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 医用剪刀: 钢材+包装
    CompositionRelation(productId = 13025, materialId = 7001, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 13025, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 止血带: 合成橡胶+包装
    CompositionRelation(productId = 13026, materialId = 7018, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 13026, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 医用敷贴: 纺织纤维+包装
    CompositionRelation(productId = 13027, materialId = 7010, useAmount = 0.04, unit = "千克"),
    CompositionRelation(productId = 13027, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 口罩(儿童): 纺织纤维+包装
    CompositionRelation(productId = 13028, materialId = 7010, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 13028, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 一次性帽子(医用): 纺织纤维+包装
    CompositionRelation(productId = 13029, materialId = 7010, useAmount = 0.01, unit = "千克"),
    CompositionRelation(productId = 13029, materialId = 20008, useAmount = 1.0, unit = "次"),
    // 医用垃圾桶: 塑料外壳+包装
    CompositionRelation(productId = 13030, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 13030, materialId = 20008, useAmount = 1.0, unit = "次")
)

// ============================================
// 🐾 宠物原料数据 (ID: 11050~11069)
// 类别：宠物原料，接触类型：EDIBLE
// ============================================
val petRawMaterials = listOf(
    MaterialItem(id = 11050, name = "鸡肉粉(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 18.0, contactType = ContactType.EDIBLE, colorId = 3, description = "宠物食品用鸡肉粉 蛋白含量65%"),
    MaterialItem(id = 11051, name = "鱼肉粉(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 22.0, contactType = ContactType.EDIBLE, colorId = 1, description = "深海鱼粉 宠物食品级"),
    MaterialItem(id = 11052, name = "牛肉粉(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 25.0, contactType = ContactType.EDIBLE, colorId = 4, description = "宠物食品用牛肉粉"),
    MaterialItem(id = 11053, name = "玉米粉(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 5.0, contactType = ContactType.EDIBLE, colorId = 3, description = "宠物饲料用玉米粉"),
    MaterialItem(id = 11054, name = "小麦麸(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 4.0, contactType = ContactType.EDIBLE, colorId = 3, description = "宠物饲料膳食纤维来源"),
    MaterialItem(id = 11055, name = "鸡脂(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 12.0, contactType = ContactType.EDIBLE, colorId = 10, description = "宠物食品用鸡脂肪"),
    MaterialItem(id = 11056, name = "鱼油(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 35.0, contactType = ContactType.EDIBLE, colorId = 10, description = "宠物用Omega-3鱼油"),
    MaterialItem(id = 11057, name = "宠物复合维生素", category = "宠物原料", unit = "千克", pricePerUnit = 80.0, contactType = ContactType.EDIBLE, colorId = 10, description = "犬猫用复合维生素预混料"),
    MaterialItem(id = 11058, name = "宠物矿物质预混料", category = "宠物原料", unit = "千克", pricePerUnit = 60.0, contactType = ContactType.EDIBLE, colorId = 5, description = "宠物用钙磷锌预混料"),
    MaterialItem(id = 11059, name = "甘薯粉(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 8.0, contactType = ContactType.EDIBLE, colorId = 3, description = "宠物食品用甘薯粉 天然碳水"),
    MaterialItem(id = 11060, name = "动物内脏粉(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 14.0, contactType = ContactType.EDIBLE, colorId = 4, description = "鸡肝/猪肝等内脏粉 高适口性"),
    MaterialItem(id = 11061, name = "宠物益生菌", category = "宠物原料", unit = "千克", pricePerUnit = 120.0, contactType = ContactType.EDIBLE, colorId = 1, description = "宠物用益生菌粉 调理肠道"),
    MaterialItem(id = 11062, name = "马铃薯淀粉(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 6.0, contactType = ContactType.EDIBLE, colorId = 1, description = "宠物食品用马铃薯淀粉"),
    MaterialItem(id = 11063, name = "宠物调味料(鸡肉味)", category = "宠物原料", unit = "千克", pricePerUnit = 30.0, contactType = ContactType.EDIBLE, colorId = 3, description = "宠物食品风味增强剂"),
    MaterialItem(id = 11064, name = "绿茶提取物(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 90.0, contactType = ContactType.EDIBLE, colorId = 10, description = "宠物用天然抗氧化剂"),
    MaterialItem(id = 11065, name = "牛磺酸(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 150.0, contactType = ContactType.EDIBLE, colorId = 1, description = "宠物用牛磺酸 猫必须氨基酸"),
    MaterialItem(id = 11066, name = "卵磷脂(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 45.0, contactType = ContactType.EDIBLE, colorId = 3, description = "宠物用卵磷脂 美毛护肤"),
    MaterialItem(id = 11067, name = "宠物冻干肉粒", category = "宠物原料", unit = "千克", pricePerUnit = 55.0, contactType = ContactType.EDIBLE, colorId = 4, description = "冻干鸡肉粒 宠物零食原料"),
    MaterialItem(id = 11068, name = "胡萝卜粉(宠物级)", category = "宠物原料", unit = "千克", pricePerUnit = 10.0, contactType = ContactType.EDIBLE, colorId = 10, description = "宠物食品用蔬菜粉"),
    MaterialItem(id = 11069, name = "宠物菌种发酵液", category = "宠物原料", unit = "升", pricePerUnit = 40.0, contactType = ContactType.EDIBLE, colorId = 1, description = "宠物饲料发酵用菌液")
)

// ============================================
// 🏥 医疗原料数据 (ID: 13050~13069)
// 类别：医疗原料
// 非内服接触类: NON_CONTACT，药品类: EDIBLE
// ============================================
val medicalRawMaterials = listOf(
    MaterialItem(id = 13050, name = "医用脱脂棉", category = "医疗原料", unit = "千克", pricePerUnit = 30.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "医用级脱脂棉 无菌处理"),
    MaterialItem(id = 13051, name = "医用无纺布", category = "医疗原料", unit = "千克", pricePerUnit = 25.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "医用级无纺布 透气防水"),
    MaterialItem(id = 13052, name = "医用胶粘剂", category = "医疗原料", unit = "千克", pricePerUnit = 80.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "医用压敏胶 皮肤友好型"),
    MaterialItem(id = 13053, name = "药用明胶", category = "医疗原料", unit = "千克", pricePerUnit = 60.0, contactType = ContactType.EDIBLE, colorId = 1, description = "药用级明胶 胶囊/包衣用"),
    MaterialItem(id = 13054, name = "药用淀粉", category = "医疗原料", unit = "千克", pricePerUnit = 12.0, contactType = ContactType.EDIBLE, colorId = 1, description = "药用级淀粉 填充剂/崩解剂"),
    MaterialItem(id = 13055, name = "医用PVC粒料", category = "医疗原料", unit = "吨", pricePerUnit = 12000.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "医用级PVC 输液管/袋原料"),
    MaterialItem(id = 13056, name = "药用蔗糖", category = "医疗原料", unit = "千克", pricePerUnit = 15.0, contactType = ContactType.EDIBLE, colorId = 1, description = "药用级蔗糖 糖浆/片剂辅料"),
    MaterialItem(id = 13057, name = "医用酒精(原料)", category = "医疗原料", unit = "升", pricePerUnit = 8.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "95%医用酒精 消毒液原料"),
    MaterialItem(id = 13058, name = "碘伏(原料)", category = "医疗原料", unit = "升", pricePerUnit = 35.0, contactType = ContactType.NON_CONTACT, colorId = 3, description = "聚维酮碘溶液 消毒剂原料"),
    MaterialItem(id = 13059, name = "中药提取物(板蓝根)", category = "医疗原料", unit = "千克", pricePerUnit = 110.0, contactType = ContactType.EDIBLE, colorId = 4, description = "板蓝根浓缩提取物 颗粒剂原料"),
    MaterialItem(id = 13060, name = "药用碳酸钙", category = "医疗原料", unit = "千克", pricePerUnit = 8.0, contactType = ContactType.EDIBLE, colorId = 1, description = "药用级碳酸钙 钙片原料"),
    MaterialItem(id = 13061, name = "维生素C(原料药)", category = "医疗原料", unit = "千克", pricePerUnit = 90.0, contactType = ContactType.EDIBLE, colorId = 1, description = "医药级维生素C 原料药"),
    MaterialItem(id = 13062, name = "中药提取物(丹参)", category = "医疗原料", unit = "千克", pricePerUnit = 150.0, contactType = ContactType.EDIBLE, colorId = 4, description = "丹参提取物 心血管用药原料"),
    MaterialItem(id = 13063, name = "医用乳胶", category = "医疗原料", unit = "千克", pricePerUnit = 40.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "医用天然乳胶 手套/导管原料"),
    MaterialItem(id = 13064, name = "药用硬脂酸镁", category = "医疗原料", unit = "千克", pricePerUnit = 25.0, contactType = ContactType.EDIBLE, colorId = 1, description = "药用级硬脂酸镁 片剂润滑剂"),
    MaterialItem(id = 13065, name = "医用棉布", category = "医疗原料", unit = "米", pricePerUnit = 12.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "医用全棉纱布布匹"),
    MaterialItem(id = 13066, name = "药用薄荷脑", category = "医疗原料", unit = "千克", pricePerUnit = 180.0, contactType = ContactType.EDIBLE, colorId = 1, description = "药用级薄荷脑 外用药/含片原料"),
    MaterialItem(id = 13067, name = "中药提取物(甘草)", category = "医疗原料", unit = "千克", pricePerUnit = 80.0, contactType = ContactType.EDIBLE, colorId = 3, description = "甘草提取物 止咳化痰药原料"),
    MaterialItem(id = 13068, name = "医用塑料(PE)", category = "医疗原料", unit = "吨", pricePerUnit = 9500.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "医用级聚乙烯 输液瓶/包装原料"),
    MaterialItem(id = 13069, name = "医药级羟丙甲纤维素", category = "医疗原料", unit = "千克", pricePerUnit = 55.0, contactType = ContactType.EDIBLE, colorId = 1, description = "HPMC 缓释材料/胶囊壳原料")
)

// ============================================
// 📦 汇总列表
// ============================================
val allPetSupplies: List<ProductItem> = petSupplies
val allToolProducts: List<ProductItem> = toolProducts
val allMedicalProducts: List<ProductItem> = medicalProducts
val allNewPetToolMedicalProducts: List<ProductItem> = petSupplies + toolProducts + medicalProducts
val allNewPetToolMedicalCompositions: List<CompositionRelation> = petCompositions + toolCompositions + medicalCompositions