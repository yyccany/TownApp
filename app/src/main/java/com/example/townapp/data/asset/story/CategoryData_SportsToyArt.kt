/**
 * ============================================
 * 🏋️ 体育健身 / 🧸 玩具桌游 / 🎨 美术文创 品类数据
 * ID 区间：14000~14049 / 15000~15049 / 16000~16049
 * ============================================
 */
package com.example.townapp.data

import com.example.townapp.data.ContactType
import com.example.townapp.data.PixelShapeType
import com.example.townapp.data.ProductItem
import com.example.townapp.data.CompositionRelation
import com.example.townapp.data.MaterialItem

// ============================================
// 🏋️ 体育健身用品 (ID: 14001~14030)
// ============================================
val sportsProducts = listOf(
    ProductItem(id = 14001, name = "瑜伽垫", category = "体育健身", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "6mm TPE材质 防滑瑜伽垫"),
    ProductItem(id = 14002, name = "哑铃套装", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "20kg可调节哑铃套装"),
    ProductItem(id = 14003, name = "跑步机", category = "体育健身", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "家用折叠跑步机 电动坡度"),
    ProductItem(id = 14004, name = "动感单车", category = "体育健身", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "磁控静音动感单车"),
    ProductItem(id = 14005, name = "跳绳", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "钢丝轴承跳绳 可调节长度"),
    ProductItem(id = 14006, name = "弹力带套装", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "5条装 不同阻力级"),
    ProductItem(id = 14007, name = "篮球", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "标准7号篮球 室内外通用"),
    ProductItem(id = 14008, name = "足球", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "5号足球 机缝 PU材质"),
    ProductItem(id = 14009, name = "网球拍", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "碳素网球拍 穿线版"),
    ProductItem(id = 14010, name = "羽毛球拍", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "碳纤维羽毛球拍 对装"),
    ProductItem(id = 14011, name = "乒乓球拍", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "反胶乒乓球拍 横握"),
    ProductItem(id = 14012, name = "游泳镜", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "防雾防水游泳镜 可替换鼻桥"),
    ProductItem(id = 14013, name = "健身追踪器", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "智能手环 心率血氧监测"),
    ProductItem(id = 14014, name = "沙袋", category = "体育健身", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "立式拳击沙袋 可调节高度"),
    ProductItem(id = 14015, name = "健腹轮", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "自动回弹健腹轮 加宽轮面"),
    ProductItem(id = 14016, name = "杠铃套装", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "50kg杠铃套装 含杠铃杆和铃片"),
    ProductItem(id = 14017, name = "壶铃", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "铸铁壶铃 12kg"),
    ProductItem(id = 14018, name = "拉力器", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "弹簧拉力器 五簧可调"),
    ProductItem(id = 14019, name = "健身球", category = "体育健身", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "65cm瑜伽健身球 防爆"),
    ProductItem(id = 14020, name = "俯卧撑支架", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "防滑俯卧撑架 加宽底座"),
    ProductItem(id = 14021, name = "划船机", category = "体育健身", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "磁阻划船机 折叠收纳"),
    ProductItem(id = 14022, name = "椭圆机", category = "体育健身", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "磁控椭圆机 静音家用"),
    ProductItem(id = 14023, name = "攀岩绳", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "动力绳 10.2mm 60m"),
    ProductItem(id = 14024, name = "护腕", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "弹性护腕 加压固定"),
    ProductItem(id = 14025, name = "护膝", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "运动护膝 弹簧支撑"),
    ProductItem(id = 14026, name = "运动水壶", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "750ml Tritan材质 运动水壶"),
    ProductItem(id = 14027, name = "瑜伽砖", category = "体育健身", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "EVA瑜伽砖 高密度"),
    ProductItem(id = 14028, name = "速度跳绳", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.VERTICAL, description = "竞速跳绳 钢轴轴承"),
    ProductItem(id = 14029, name = "握力器", category = "体育健身", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "可调握力器 10-60kg"),
    ProductItem(id = 14030, name = "引体向上杆", category = "体育健身", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "门框式引体向上杆 免打孔")
)

// ============================================
// 🧸 玩具桌游 (ID: 15001~15030)
// ============================================
val toyProducts = listOf(
    ProductItem(id = 15001, name = "国际象棋", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "木质国际象棋 含棋盘"),
    ProductItem(id = 15002, name = "围棋", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "19路围棋 双面凸棋子"),
    ProductItem(id = 15003, name = "麻将", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "136张标准麻将 含骰子"),
    ProductItem(id = 15004, name = "扑克牌", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "PVC扑克牌 54张"),
    ProductItem(id = 15005, name = "乐高积木", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "创意系列 600颗粒装"),
    ProductItem(id = 15006, name = "遥控车", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.HORIZONTAL, description = "1:16遥控赛车 充电式"),
    ProductItem(id = 15007, name = "玩偶公仔", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "30cm搪胶公仔 关节可动"),
    ProductItem(id = 15008, name = "拼图", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "1000片风景拼图"),
    ProductItem(id = 15009, name = "大富翁", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "经典地产大富翁 含道具"),
    ProductItem(id = 15010, name = "毛绒玩具", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "40cm泰迪熊 短毛绒"),
    ProductItem(id = 15011, name = "积木", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "木制积木 100块装 早教"),
    ProductItem(id = 15012, name = "模型套件", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "1:100高达拼装模型"),
    ProductItem(id = 15013, name = "悠悠球", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "金属轴承悠悠球 蝶形"),
    ProductItem(id = 15014, name = "飞盘", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "175g标准飞盘 软胶"),
    ProductItem(id = 15015, name = "魔方", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "三阶魔方 竞速型 磁力定位"),
    ProductItem(id = 15016, name = "跳棋", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "玻璃珠跳棋 木质棋盘"),
    ProductItem(id = 15017, name = "军棋", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "陆战棋 明棋暗棋双玩法"),
    ProductItem(id = 15018, name = "五子棋", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "磁力五子棋 折叠棋盘"),
    ProductItem(id = 15019, name = "卡坦岛", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "德式桌游 资源建设类"),
    ProductItem(id = 15020, name = "陀螺", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.VERTICAL, description = "合金战斗陀螺 发射器套装"),
    ProductItem(id = 15021, name = "弹珠", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "玻璃弹珠 50颗装 混色"),
    ProductItem(id = 15022, name = "沙包", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "6只装 手抛沙包 帆布材质"),
    ProductItem(id = 15023, name = "毽子", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "鹅毛毽子 铜钱底座"),
    ProductItem(id = 15024, name = "竹蜻蜓", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "手动竹蜻蜓 环保竹制"),
    ProductItem(id = 15025, name = "万花筒", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.VERTICAL, description = "纸筒万花筒 旋转图案"),
    ProductItem(id = 15026, name = "七巧板", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "木质七巧板 含图案手册"),
    ProductItem(id = 15027, name = "孔明锁", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "鲁班锁 6件套装 木质"),
    ProductItem(id = 15028, name = "九连环", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "金属九连环 解环益智"),
    ProductItem(id = 15029, name = "多米诺骨牌", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "100片彩色多米诺 含摆件"),
    ProductItem(id = 15030, name = "桌上冰球", category = "玩具桌游", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "迷你桌上冰球 双人对战")
)

// ============================================
// 🎨 美术文创用品 (ID: 16001~16030)
// ============================================
val artProducts = listOf(
    ProductItem(id = 16001, name = "彩色铅笔", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.HORIZONTAL, description = "36色水溶性彩铅 铁盒装"),
    ProductItem(id = 16002, name = "水彩颜料套装", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "24色固体水彩 含画笔"),
    ProductItem(id = 16003, name = "油画颜料", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "12色油画颜料 管装 各40ml"),
    ProductItem(id = 16004, name = "画布", category = "美术文创", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "40x50cm棉麻画布 5只装"),
    ProductItem(id = 16005, name = "素描本", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "A4素描本 100g 40页"),
    ProductItem(id = 16006, name = "画笔套装", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "10支装尼龙画笔 各号型"),
    ProductItem(id = 16007, name = "超轻粘土", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "24色超轻粘土 各30g"),
    ProductItem(id = 16008, name = "画架", category = "美术文创", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "落地实木画架 可折叠升降"),
    ProductItem(id = 16009, name = "书法套装", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.HORIZONTAL, description = "毛笔套装 含墨碟毛毡"),
    ProductItem(id = 16010, name = "折纸", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "15x15cm彩色折纸 100张"),
    ProductItem(id = 16011, name = "印章套装", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "6枚装卡通印章 含印泥"),
    ProductItem(id = 16012, name = "热胶枪", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "20W热熔胶枪 含10根胶棒"),
    ProductItem(id = 16013, name = "剪刀", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "不锈钢手工剪刀 防锈"),
    ProductItem(id = 16014, name = "切割垫", category = "美术文创", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "A3双面切割垫 自愈材质"),
    ProductItem(id = 16015, name = "马克笔", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "48色酒精马克笔 含笔袋"),
    ProductItem(id = 16016, name = "丙烯颜料", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "18色丙烯颜料 各60ml"),
    ProductItem(id = 16017, name = "调色盘", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "塑料梅花调色盘 24格"),
    ProductItem(id = 16018, name = "速写本", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "A5速写本 硬皮封面 120页"),
    ProductItem(id = 16019, name = "炭笔", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "素描炭笔 软中硬三支装"),
    ProductItem(id = 16020, name = "橡皮擦套装", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "4B绘画橡皮 可塑橡皮 高光橡皮"),
    ProductItem(id = 16021, name = "卷笔刀", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "金属双孔卷笔刀 削笔器"),
    ProductItem(id = 16022, name = "美工刀", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.VERTICAL, description = "30°美工刀 含备用刀片"),
    ProductItem(id = 16023, name = "直尺套装", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.HORIZONTAL, description = "不锈钢直尺 15cm+30cm 套装"),
    ProductItem(id = 16024, name = "圆规", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "绘图圆规 含延长杆 铅芯盒"),
    ProductItem(id = 16025, name = "彩墨", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "6色钢笔墨水 非碳素 各30ml"),
    ProductItem(id = 16026, name = "毛毡", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "50x70cm羊毛毡垫 书法用"),
    ProductItem(id = 16027, name = "刺绣套装", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "十字绣入门套装 含绣绷彩线"),
    ProductItem(id = 16028, name = "衍纸工具", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "衍纸笔 模板尺 彩纸条套装"),
    ProductItem(id = 16029, name = "陶艺工具", category = "美术文创", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "8件套陶艺雕塑刀 木质手柄"),
    ProductItem(id = 16030, name = "装裱框", category = "美术文创", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.SQUARE, description = "A4相框 实木边框 可挂墙")
)

// ============================================
// 🔗 体育健身用品组成关系 (14001~14030)
// ============================================
val sportsCompositions = listOf(
    // 瑜伽垫 (14001)
    CompositionRelation(productId = 14001, materialId = 7010, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 14001, materialId = 7018, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 14001, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 哑铃套装 (14002)
    CompositionRelation(productId = 14002, materialId = 7001, useAmount = 15.0, unit = "千克"),
    CompositionRelation(productId = 14002, materialId = 7003, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 14002, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 14002, materialId = 20001, useAmount = 0.5, unit = "小时"),
    // 跑步机 (14003)
    CompositionRelation(productId = 14003, materialId = 7001, useAmount = 40.0, unit = "千克"),
    CompositionRelation(productId = 14003, materialId = 7002, useAmount = 5.0, unit = "千克"),
    CompositionRelation(productId = 14003, materialId = 6007, useAmount = 3.0, unit = "个"),
    CompositionRelation(productId = 14003, materialId = 7007, useAmount = 2.0, unit = "千克"),
    CompositionRelation(productId = 14003, materialId = 20004, useAmount = 1.0, unit = "次"),
    // 动感单车 (14004)
    CompositionRelation(productId = 14004, materialId = 7001, useAmount = 25.0, unit = "千克"),
    CompositionRelation(productId = 14004, materialId = 7002, useAmount = 8.0, unit = "千克"),
    CompositionRelation(productId = 14004, materialId = 7018, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 14004, materialId = 20001, useAmount = 2.0, unit = "小时"),
    // 跳绳 (14005)
    CompositionRelation(productId = 14005, materialId = 7001, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 14005, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 14005, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 弹力带套装 (14006)
    CompositionRelation(productId = 14006, materialId = 7010, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 14006, materialId = 7018, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 14006, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 篮球 (14007)
    CompositionRelation(productId = 14007, materialId = 7018, useAmount = 0.6, unit = "千克"),
    CompositionRelation(productId = 14007, materialId = 7010, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 14007, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 足球 (14008)
    CompositionRelation(productId = 14008, materialId = 7009, useAmount = 0.1, unit = "平方米"),
    CompositionRelation(productId = 14008, materialId = 7018, useAmount = 0.4, unit = "千克"),
    CompositionRelation(productId = 14008, materialId = 7010, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 14008, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 网球拍 (14009)
    CompositionRelation(productId = 14009, materialId = 7002, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 14009, materialId = 7007, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 14009, materialId = 7010, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 14009, materialId = 20001, useAmount = 0.5, unit = "小时"),
    // 羽毛球拍 (14010)
    CompositionRelation(productId = 14010, materialId = 7002, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 14010, materialId = 7007, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 14010, materialId = 7010, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 14010, materialId = 20001, useAmount = 0.4, unit = "小时"),
    // 乒乓球拍 (14011)
    CompositionRelation(productId = 14011, materialId = 5005, useAmount = 0.1, unit = "立方米"),
    CompositionRelation(productId = 14011, materialId = 7018, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 14011, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 游泳镜 (14012)
    CompositionRelation(productId = 14012, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 14012, materialId = 7018, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 14012, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 健身追踪器 (14013)
    CompositionRelation(productId = 14013, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 14013, materialId = 7018, useAmount = 0.01, unit = "千克"),
    CompositionRelation(productId = 14013, materialId = 20004, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 14013, materialId = 20001, useAmount = 0.5, unit = "小时"),
    // 沙袋 (14014)
    CompositionRelation(productId = 14014, materialId = 7009, useAmount = 0.5, unit = "平方米"),
    CompositionRelation(productId = 14014, materialId = 7010, useAmount = 2.0, unit = "千克"),
    CompositionRelation(productId = 14014, materialId = 7005, useAmount = 15.0, unit = "千克"),
    CompositionRelation(productId = 14014, materialId = 20001, useAmount = 1.0, unit = "小时"),
    // 健腹轮 (14015)
    CompositionRelation(productId = 14015, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 14015, materialId = 7018, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 14015, materialId = 7001, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 14015, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 杠铃套装 (14016)
    CompositionRelation(productId = 14016, materialId = 7001, useAmount = 40.0, unit = "千克"),
    CompositionRelation(productId = 14016, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 14016, materialId = 20001, useAmount = 0.5, unit = "小时"),
    // 壶铃 (14017)
    CompositionRelation(productId = 14017, materialId = 7001, useAmount = 10.0, unit = "千克"),
    CompositionRelation(productId = 14017, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 14017, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 拉力器 (14018)
    CompositionRelation(productId = 14018, materialId = 7001, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 14018, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 14018, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 健身球 (14019)
    CompositionRelation(productId = 14019, materialId = 7006, useAmount = 0.8, unit = "千克"),
    CompositionRelation(productId = 14019, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 14019, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 俯卧撑支架 (14020)
    CompositionRelation(productId = 14020, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 14020, materialId = 7018, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 14020, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 划船机 (14021)
    CompositionRelation(productId = 14021, materialId = 7001, useAmount = 30.0, unit = "千克"),
    CompositionRelation(productId = 14021, materialId = 7002, useAmount = 5.0, unit = "千克"),
    CompositionRelation(productId = 14021, materialId = 7007, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 14021, materialId = 20004, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 14021, materialId = 20001, useAmount = 2.0, unit = "小时"),
    // 椭圆机 (14022)
    CompositionRelation(productId = 14022, materialId = 7001, useAmount = 35.0, unit = "千克"),
    CompositionRelation(productId = 14022, materialId = 7002, useAmount = 6.0, unit = "千克"),
    CompositionRelation(productId = 14022, materialId = 6007, useAmount = 4.0, unit = "个"),
    CompositionRelation(productId = 14022, materialId = 20004, useAmount = 1.0, unit = "次"),
    // 攀岩绳 (14023)
    CompositionRelation(productId = 14023, materialId = 7010, useAmount = 1.5, unit = "千克"),
    CompositionRelation(productId = 14023, materialId = 7005, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 14023, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 护腕 (14024)
    CompositionRelation(productId = 14024, materialId = 7010, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 14024, materialId = 7018, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 14024, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 护膝 (14025)
    CompositionRelation(productId = 14025, materialId = 7010, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 14025, materialId = 7018, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 14025, materialId = 20001, useAmount = 0.15, unit = "小时"),
    // 运动水壶 (14026)
    CompositionRelation(productId = 14026, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 14026, materialId = 7018, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 14026, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 瑜伽砖 (14027)
    CompositionRelation(productId = 14027, materialId = 7006, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 14027, materialId = 20001, useAmount = 0.05, unit = "小时"),
    // 速度跳绳 (14028)
    CompositionRelation(productId = 14028, materialId = 7001, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 14028, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 14028, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 握力器 (14029)
    CompositionRelation(productId = 14029, materialId = 7001, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 14029, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 14029, materialId = 7018, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 14029, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 引体向上杆 (14030)
    CompositionRelation(productId = 14030, materialId = 7001, useAmount = 2.0, unit = "千克"),
    CompositionRelation(productId = 14030, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 14030, materialId = 7018, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 14030, materialId = 20001, useAmount = 0.3, unit = "小时")
)

// ============================================
// 🔗 玩具桌游组成关系 (15001~15030)
// ============================================
val toyCompositions = listOf(
    // 国际象棋 (15001)
    CompositionRelation(productId = 15001, materialId = 5005, useAmount = 0.05, unit = "立方米"),
    CompositionRelation(productId = 15001, materialId = 7009, useAmount = 0.02, unit = "平方米"),
    CompositionRelation(productId = 15001, materialId = 20001, useAmount = 1.0, unit = "小时"),
    CompositionRelation(productId = 15001, materialId = 20004, useAmount = 0.2, unit = "次"),
    // 围棋 (15002)
    CompositionRelation(productId = 15002, materialId = 5005, useAmount = 0.04, unit = "立方米"),
    CompositionRelation(productId = 15002, materialId = 7005, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 15002, materialId = 20001, useAmount = 1.2, unit = "小时"),
    // 麻将 (15003)
    CompositionRelation(productId = 15003, materialId = 6007, useAmount = 144.0, unit = "个"),
    CompositionRelation(productId = 15003, materialId = 7005, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 15003, materialId = 20004, useAmount = 0.3, unit = "次"),
    CompositionRelation(productId = 15003, materialId = 20001, useAmount = 0.5, unit = "小时"),
    // 扑克牌 (15004)
    CompositionRelation(productId = 15004, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 15004, materialId = 6007, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 15004, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 乐高积木 (15005)
    CompositionRelation(productId = 15005, materialId = 6007, useAmount = 600.0, unit = "个"),
    CompositionRelation(productId = 15005, materialId = 7005, useAmount = 1.2, unit = "千克"),
    CompositionRelation(productId = 15005, materialId = 20004, useAmount = 2.0, unit = "次"),
    CompositionRelation(productId = 15005, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 遥控车 (15006)
    CompositionRelation(productId = 15006, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 15006, materialId = 7001, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 15006, materialId = 7005, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 15006, materialId = 20004, useAmount = 0.5, unit = "次"),
    CompositionRelation(productId = 15006, materialId = 20001, useAmount = 0.5, unit = "小时"),
    // 玩偶公仔 (15007)
    CompositionRelation(productId = 15007, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 15007, materialId = 7010, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 15007, materialId = 20004, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 15007, materialId = 20001, useAmount = 0.5, unit = "小时"),
    // 拼图 (15008)
    CompositionRelation(productId = 15008, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 15008, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 15008, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 大富翁 (15009)
    CompositionRelation(productId = 15009, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 15009, materialId = 6007, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 15009, materialId = 5005, useAmount = 0.02, unit = "立方米"),
    CompositionRelation(productId = 15009, materialId = 20004, useAmount = 1.0, unit = "次"),
    // 毛绒玩具 (15010)
    CompositionRelation(productId = 15010, materialId = 7010, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 15010, materialId = 7018, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 15010, materialId = 20001, useAmount = 0.5, unit = "小时"),
    // 积木 (15011)
    CompositionRelation(productId = 15011, materialId = 5005, useAmount = 0.05, unit = "立方米"),
    CompositionRelation(productId = 15011, materialId = 7006, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 15011, materialId = 20001, useAmount = 0.4, unit = "小时"),
    // 模型套件 (15012)
    CompositionRelation(productId = 15012, materialId = 6007, useAmount = 10.0, unit = "个"),
    CompositionRelation(productId = 15012, materialId = 7005, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 15012, materialId = 20004, useAmount = 2.0, unit = "次"),
    CompositionRelation(productId = 15012, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 悠悠球 (15013)
    CompositionRelation(productId = 15013, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 15013, materialId = 7002, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 15013, materialId = 7010, useAmount = 0.5, unit = "米"),
    CompositionRelation(productId = 15013, materialId = 20001, useAmount = 0.15, unit = "小时"),
    // 飞盘 (15014)
    CompositionRelation(productId = 15014, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 15014, materialId = 7005, useAmount = 0.15, unit = "千克"),
    CompositionRelation(productId = 15014, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 魔方 (15015)
    CompositionRelation(productId = 15015, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 15015, materialId = 7005, useAmount = 0.08, unit = "千克"),
    CompositionRelation(productId = 15015, materialId = 20004, useAmount = 0.3, unit = "次"),
    CompositionRelation(productId = 15015, materialId = 20001, useAmount = 0.15, unit = "小时"),
    // 跳棋 (15016)
    CompositionRelation(productId = 15016, materialId = 5005, useAmount = 0.02, unit = "立方米"),
    CompositionRelation(productId = 15016, materialId = 7007, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 15016, materialId = 20001, useAmount = 0.4, unit = "小时"),
    // 军棋 (15017)
    CompositionRelation(productId = 15017, materialId = 5005, useAmount = 0.02, unit = "立方米"),
    CompositionRelation(productId = 15017, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 15017, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 五子棋 (15018)
    CompositionRelation(productId = 15018, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 15018, materialId = 7005, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 15018, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 15018, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 卡坦岛 (15019)
    CompositionRelation(productId = 15019, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 15019, materialId = 6007, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 15019, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 15019, materialId = 20004, useAmount = 1.5, unit = "次"),
    // 陀螺 (15020)
    CompositionRelation(productId = 15020, materialId = 7002, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 15020, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 15020, materialId = 20001, useAmount = 0.15, unit = "小时"),
    // 弹珠 (15021)
    CompositionRelation(productId = 15021, materialId = 7007, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 15021, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 沙包 (15022)
    CompositionRelation(productId = 15022, materialId = 7010, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 15022, materialId = 7005, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 15022, materialId = 20001, useAmount = 0.15, unit = "小时"),
    // 毽子 (15023)
    CompositionRelation(productId = 15023, materialId = 7009, useAmount = 0.01, unit = "平方米"),
    CompositionRelation(productId = 15023, materialId = 7010, useAmount = 0.01, unit = "千克"),
    CompositionRelation(productId = 15023, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 竹蜻蜓 (15024)
    CompositionRelation(productId = 15024, materialId = 5005, useAmount = 0.005, unit = "立方米"),
    CompositionRelation(productId = 15024, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 万花筒 (15025)
    CompositionRelation(productId = 15025, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 15025, materialId = 7007, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 15025, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 15025, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 七巧板 (15026)
    CompositionRelation(productId = 15026, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 15026, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 15026, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 孔明锁 (15027)
    CompositionRelation(productId = 15027, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 15027, materialId = 20001, useAmount = 0.3, unit = "小时"),
    CompositionRelation(productId = 15027, materialId = 20004, useAmount = 0.5, unit = "次"),
    // 九连环 (15028)
    CompositionRelation(productId = 15028, materialId = 7001, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 15028, materialId = 20001, useAmount = 0.2, unit = "小时"),
    CompositionRelation(productId = 15028, materialId = 20004, useAmount = 0.3, unit = "次"),
    // 多米诺骨牌 (15029)
    CompositionRelation(productId = 15029, materialId = 5005, useAmount = 0.02, unit = "立方米"),
    CompositionRelation(productId = 15029, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 桌上冰球 (15030)
    CompositionRelation(productId = 15030, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 15030, materialId = 7001, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 15030, materialId = 7005, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 15030, materialId = 20004, useAmount = 0.5, unit = "次"),
    CompositionRelation(productId = 15030, materialId = 20001, useAmount = 0.5, unit = "小时")
)

// ============================================
// 🔗 美术文创用品组成关系 (16001~16030)
// ============================================
val artCompositions = listOf(
    // 彩色铅笔 (16001)
    CompositionRelation(productId = 16001, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 16001, materialId = 7007, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 16001, materialId = 20001, useAmount = 0.3, unit = "小时"),
    CompositionRelation(productId = 16001, materialId = 20004, useAmount = 0.2, unit = "次"),
    // 水彩颜料套装 (16002)
    CompositionRelation(productId = 16002, materialId = 7006, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 16002, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 16002, materialId = 20004, useAmount = 0.5, unit = "次"),
    CompositionRelation(productId = 16002, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 油画颜料 (16003)
    CompositionRelation(productId = 16003, materialId = 7006, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 16003, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 16003, materialId = 20004, useAmount = 0.5, unit = "次"),
    CompositionRelation(productId = 16003, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 画布 (16004)
    CompositionRelation(productId = 16004, materialId = 7010, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 16004, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 16004, materialId = 20001, useAmount = 0.15, unit = "小时"),
    // 素描本 (16005)
    CompositionRelation(productId = 16005, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 16005, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 16005, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 画笔套装 (16006)
    CompositionRelation(productId = 16006, materialId = 5005, useAmount = 0.005, unit = "立方米"),
    CompositionRelation(productId = 16006, materialId = 7010, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 16006, materialId = 7002, useAmount = 0.01, unit = "千克"),
    CompositionRelation(productId = 16006, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 超轻粘土 (16007)
    CompositionRelation(productId = 16007, materialId = 7006, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 16007, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 16007, materialId = 20001, useAmount = 0.15, unit = "小时"),
    // 画架 (16008)
    CompositionRelation(productId = 16008, materialId = 5005, useAmount = 0.08, unit = "立方米"),
    CompositionRelation(productId = 16008, materialId = 7001, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 16008, materialId = 20001, useAmount = 0.5, unit = "小时"),
    // 书法套装 (16009)
    CompositionRelation(productId = 16009, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 16009, materialId = 7010, useAmount = 0.03, unit = "千克"),
    CompositionRelation(productId = 16009, materialId = 7006, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 16009, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 折纸 (16010)
    CompositionRelation(productId = 16010, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 16010, materialId = 6007, useAmount = 0.01, unit = "千克"),
    CompositionRelation(productId = 16010, materialId = 20001, useAmount = 0.05, unit = "小时"),
    // 印章套装 (16011)
    CompositionRelation(productId = 16011, materialId = 5005, useAmount = 0.005, unit = "立方米"),
    CompositionRelation(productId = 16011, materialId = 7006, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 16011, materialId = 20001, useAmount = 0.25, unit = "小时"),
    // 热胶枪 (16012)
    CompositionRelation(productId = 16012, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 16012, materialId = 7001, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 16012, materialId = 7006, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 16012, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 剪刀 (16013)
    CompositionRelation(productId = 16013, materialId = 7001, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 16013, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 16013, materialId = 20001, useAmount = 0.15, unit = "小时"),
    // 切割垫 (16014)
    CompositionRelation(productId = 16014, materialId = 7006, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 16014, materialId = 20001, useAmount = 0.05, unit = "小时"),
    // 马克笔 (16015)
    CompositionRelation(productId = 16015, materialId = 6007, useAmount = 48.0, unit = "个"),
    CompositionRelation(productId = 16015, materialId = 7006, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 16015, materialId = 7010, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 16015, materialId = 20004, useAmount = 0.3, unit = "次"),
    CompositionRelation(productId = 16015, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 丙烯颜料 (16016)
    CompositionRelation(productId = 16016, materialId = 7006, useAmount = 0.8, unit = "千克"),
    CompositionRelation(productId = 16016, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 16016, materialId = 20004, useAmount = 0.3, unit = "次"),
    CompositionRelation(productId = 16016, materialId = 20001, useAmount = 0.2, unit = "小时"),
    // 调色盘 (16017)
    CompositionRelation(productId = 16017, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 16017, materialId = 20001, useAmount = 0.05, unit = "小时"),
    // 速写本 (16018)
    CompositionRelation(productId = 16018, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 16018, materialId = 5005, useAmount = 0.005, unit = "立方米"),
    CompositionRelation(productId = 16018, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 炭笔 (16019)
    CompositionRelation(productId = 16019, materialId = 5005, useAmount = 0.002, unit = "立方米"),
    CompositionRelation(productId = 16019, materialId = 7007, useAmount = 0.01, unit = "千克"),
    CompositionRelation(productId = 16019, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 橡皮擦套装 (16020)
    CompositionRelation(productId = 16020, materialId = 7006, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 16020, materialId = 6007, useAmount = 3.0, unit = "个"),
    CompositionRelation(productId = 16020, materialId = 20001, useAmount = 0.05, unit = "小时"),
    // 卷笔刀 (16021)
    CompositionRelation(productId = 16021, materialId = 7001, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 16021, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 16021, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 美工刀 (16022)
    CompositionRelation(productId = 16022, materialId = 7001, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 16022, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 16022, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 直尺套装 (16023)
    CompositionRelation(productId = 16023, materialId = 7001, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 16023, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 圆规 (16024)
    CompositionRelation(productId = 16024, materialId = 7001, useAmount = 0.03, unit = "千克"),
    CompositionRelation(productId = 16024, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 16024, materialId = 20001, useAmount = 0.15, unit = "小时"),
    // 彩墨 (16025)
    CompositionRelation(productId = 16025, materialId = 7006, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 16025, materialId = 6007, useAmount = 6.0, unit = "个"),
    CompositionRelation(productId = 16025, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 毛毡 (16026)
    CompositionRelation(productId = 16026, materialId = 7010, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 16026, materialId = 20001, useAmount = 0.1, unit = "小时"),
    // 刺绣套装 (16027)
    CompositionRelation(productId = 16027, materialId = 7010, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 16027, materialId = 7001, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 16027, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 16027, materialId = 20001, useAmount = 0.3, unit = "小时"),
    // 衍纸工具 (16028)
    CompositionRelation(productId = 16028, materialId = 6007, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 16028, materialId = 7001, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 16028, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 16028, materialId = 20001, useAmount = 0.15, unit = "小时"),
    // 陶艺工具 (16029)
    CompositionRelation(productId = 16029, materialId = 5005, useAmount = 0.01, unit = "立方米"),
    CompositionRelation(productId = 16029, materialId = 7001, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 16029, materialId = 20001, useAmount = 0.25, unit = "小时"),
    // 装裱框 (16030)
    CompositionRelation(productId = 16030, materialId = 5005, useAmount = 0.02, unit = "立方米"),
    CompositionRelation(productId = 16030, materialId = 7007, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 16030, materialId = 20001, useAmount = 0.2, unit = "小时")
)

// ============================================
// 📦 汇总导出
// ============================================
val allSportsProducts: List<ProductItem> = sportsProducts
val allToyProducts: List<ProductItem> = toyProducts
val allArtProducts: List<ProductItem> = artProducts
val allNewSportsToyArtProducts: List<ProductItem> = sportsProducts + toyProducts + artProducts
val allNewSportsToyArtCompositions: List<CompositionRelation> = sportsCompositions + toyCompositions + artCompositions