/**
 * ============================================
 * 🎵 音乐影音产品 & 包装物料数据
 * ============================================
 * 音乐影音产品 ID: 30001~30035
 * 包装物料 ID: 35001~35030
 * ============================================
 */

package com.example.townapp.data

import com.example.townapp.data.ContactType
import com.example.townapp.data.PixelShapeType
import com.example.townapp.data.ProductItem
import com.example.townapp.data.CompositionRelation
import com.example.townapp.data.MaterialItem

// ============================================
// 🎸 音乐影音产品 (ID: 30001~30035)
// ============================================
val musicAvProducts = listOf(
    // --- 弦乐器 (30001~30006) ---
    ProductItem(
        id = 30001,
        name = "木吉他",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 3,
        shapeType = PixelShapeType.VERTICAL,
        description = "古典民谣 云杉单板"
    ),
    ProductItem(
        id = 30002,
        name = "电吉他",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.VERTICAL,
        description = "实心电吉他 双拾音器"
    ),
    ProductItem(
        id = 30003,
        name = "小提琴",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 3,
        shapeType = PixelShapeType.VERTICAL,
        description = "4/4 全尺寸 云杉面板"
    ),
    ProductItem(
        id = 30004,
        name = "大提琴",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 3,
        shapeType = PixelShapeType.VERTICAL,
        description = "4/4 全尺寸 枫木背板"
    ),
    ProductItem(
        id = 30005,
        name = "尤克里里",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 3,
        shapeType = PixelShapeType.VERTICAL,
        description = "23寸 桃花心木"
    ),
    ProductItem(
        id = 30006,
        name = "竖琴",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 3,
        shapeType = PixelShapeType.VERTICAL,
        description = "47弦 踏板竖琴"
    ),

    // --- 管乐器 (30007~30012) ---
    ProductItem(
        id = 30007,
        name = "长笛",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 5,
        shapeType = PixelShapeType.HORIZONTAL,
        description = "C调 镍银材质"
    ),
    ProductItem(
        id = 30008,
        name = "单簧管",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.VERTICAL,
        description = "B调 乌木管体"
    ),
    ProductItem(
        id = 30009,
        name = "萨克斯管",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 10,
        shapeType = PixelShapeType.VERTICAL,
        description = "Eb中音 黄铜管体"
    ),
    ProductItem(
        id = 30010,
        name = "小号",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 10,
        shapeType = PixelShapeType.VERTICAL,
        description = "Bb调 黄铜镀金"
    ),
    ProductItem(
        id = 30011,
        name = "口琴",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 5,
        shapeType = PixelShapeType.HORIZONTAL,
        description = "C调 10孔布鲁斯"
    ),
    ProductItem(
        id = 30012,
        name = "手风琴",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.SQUARE,
        description = "120贝斯 键盘式"
    ),

    // --- 键盘乐器 (30013~30015) ---
    ProductItem(
        id = 30013,
        name = "数码钢琴",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.HORIZONTAL,
        description = "88键重锤 三角钢琴音色"
    ),
    ProductItem(
        id = 30014,
        name = "合成器",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.HORIZONTAL,
        description = "61键 数字合成引擎"
    ),
    ProductItem(
        id = 30015,
        name = "电子琴",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        shapeType = PixelShapeType.HORIZONTAL,
        description = "61键 便携编曲键盘"
    ),

    // --- 打击乐器 (30016~30018) ---
    ProductItem(
        id = 30016,
        name = "架子鼓",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.SQUARE,
        description = "5鼓3镲 标准配置"
    ),
    ProductItem(
        id = 30017,
        name = "卡洪鼓",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 3,
        shapeType = PixelShapeType.SQUARE,
        description = "木箱鼓 弗拉门戈"
    ),
    ProductItem(
        id = 30018,
        name = "木琴",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 3,
        shapeType = PixelShapeType.HORIZONTAL,
        description = "3组音域 红木音板"
    ),

    // --- 音频设备 (30019~30024) ---
    ProductItem(
        id = 30019,
        name = "录音棚麦克风",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.VERTICAL,
        description = "电容式 心形指向"
    ),
    ProductItem(
        id = 30020,
        name = "监听音箱",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.SQUARE,
        description = "有源 6.5寸低音"
    ),
    ProductItem(
        id = 30021,
        name = "调音台",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.HORIZONTAL,
        description = "16通道 数字调音台"
    ),
    ProductItem(
        id = 30022,
        name = "音频接口",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.SQUARE,
        description = "2进2出 USB-C"
    ),
    ProductItem(
        id = 30023,
        name = "专业监听耳机",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.SQUARE,
        description = "封闭式 参考级"
    ),
    ProductItem(
        id = 30024,
        name = "耳塞",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 1,
        shapeType = PixelShapeType.SQUARE,
        description = "入耳式 HiFi耳塞"
    ),

    // --- 媒体载体 (30025~30028) ---
    ProductItem(
        id = 30025,
        name = "黑胶唱片",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.SQUARE,
        description = "12寸 33转 立体声",
        physicalQuant = PhysicalQuantification(
            bpm = 60, dynamicRangeDb = 120.0, informationEntropy = 4.8
        )
    ),
    ProductItem(
        id = 30026,
        name = "CD专辑",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        shapeType = PixelShapeType.SQUARE,
        description = "数位光碟 精装版",
        physicalQuant = PhysicalQuantification(
            bpm = 120, dynamicRangeDb = 90.0, informationEntropy = 6.2
        )
    ),
    ProductItem(
        id = 30027,
        name = "乐谱本",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        shapeType = PixelShapeType.HORIZONTAL,
        description = "五线谱 练习本 A4"
    ),
    ProductItem(
        id = 30028,
        name = "总谱",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        shapeType = PixelShapeType.HORIZONTAL,
        description = "交响乐总谱 精装"
    ),

    // --- 配件 (30029~30035) ---
    ProductItem(
        id = 30029,
        name = "拨片套装",
        category = "音乐影音",
        contactType = ContactType.SKIN_CONTACT,
        colorId = 1,
        shapeType = PixelShapeType.SQUARE,
        description = "0.46~1.5mm 6片装"
    ),
    ProductItem(
        id = 30030,
        name = "乐器连接线",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.VERTICAL,
        description = "6.35mm 单声道 3米"
    ),
    ProductItem(
        id = 30031,
        name = "麦克风支架",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.VERTICAL,
        description = "落地式 伸缩调节"
    ),
    ProductItem(
        id = 30032,
        name = "乐谱架",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.VERTICAL,
        description = "折叠便携 桌面/落地"
    ),
    ProductItem(
        id = 30033,
        name = "乐器箱包",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.SQUARE,
        description = "加厚海绵 防水耐磨"
    ),
    ProductItem(
        id = 30034,
        name = "调音器",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 2,
        shapeType = PixelShapeType.SQUARE,
        description = "电子调音器 夹式"
    ),
    ProductItem(
        id = 30035,
        name = "节拍器",
        category = "音乐影音",
        contactType = ContactType.NON_CONTACT,
        colorId = 5,
        shapeType = PixelShapeType.SQUARE,
        description = "电子节拍器 可调速度"
    ),
)

// ============================================
// 🔗 音乐影音产品组成关系 (CompositionRelation)
// ============================================
val musicAvCompositions = listOf(
    // --- 弦乐器 ---
    // 木吉他 (30001): 木材 + 金属 + 人工工时
    CompositionRelation(productId = 30001, materialId = 5005, useAmount = 2.0, unit = "千克"),
    CompositionRelation(productId = 30001, materialId = 7008, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 30001, materialId = 20001, useAmount = 8.0, unit = "小时"),

    // 电吉他 (30002): 木材 + 金属 + 电子料 + 人工工时
    CompositionRelation(productId = 30002, materialId = 5005, useAmount = 2.5, unit = "千克"),
    CompositionRelation(productId = 30002, materialId = 7008, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 30002, materialId = 6017, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 30002, materialId = 6029, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 30002, materialId = 20001, useAmount = 10.0, unit = "小时"),

    // 小提琴 (30003): 木材 + 金属 + 人工工时
    CompositionRelation(productId = 30003, materialId = 5005, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 30003, materialId = 7008, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 30003, materialId = 20001, useAmount = 12.0, unit = "小时"),

    // 大提琴 (30004): 木材 + 金属 + 人工工时
    CompositionRelation(productId = 30004, materialId = 5005, useAmount = 3.0, unit = "千克"),
    CompositionRelation(productId = 30004, materialId = 7008, useAmount = 0.4, unit = "千克"),
    CompositionRelation(productId = 30004, materialId = 20001, useAmount = 16.0, unit = "小时"),

    // 尤克里里 (30005): 木材 + 人工工时
    CompositionRelation(productId = 30005, materialId = 5005, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 30005, materialId = 20001, useAmount = 4.0, unit = "小时"),

    // 竖琴 (30006): 木材 + 金属 + 人工工时
    CompositionRelation(productId = 30006, materialId = 5005, useAmount = 8.0, unit = "千克"),
    CompositionRelation(productId = 30006, materialId = 7008, useAmount = 1.5, unit = "千克"),
    CompositionRelation(productId = 30006, materialId = 20001, useAmount = 40.0, unit = "小时"),

    // --- 管乐器 ---
    // 长笛 (30007): 金属 + 人工工时
    CompositionRelation(productId = 30007, materialId = 7012, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 30007, materialId = 20001, useAmount = 6.0, unit = "小时"),

    // 单簧管 (30008): 木材 + 金属 + 人工工时
    CompositionRelation(productId = 30008, materialId = 5005, useAmount = 0.4, unit = "千克"),
    CompositionRelation(productId = 30008, materialId = 7008, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 30008, materialId = 7014, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 30008, materialId = 20001, useAmount = 8.0, unit = "小时"),

    // 萨克斯管 (30009): 金属 + 人工工时
    CompositionRelation(productId = 30009, materialId = 7008, useAmount = 2.0, unit = "千克"),
    CompositionRelation(productId = 30009, materialId = 7012, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 30009, materialId = 20001, useAmount = 10.0, unit = "小时"),

    // 小号 (30010): 金属 + 人工工时
    CompositionRelation(productId = 30010, materialId = 7008, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 30010, materialId = 20001, useAmount = 8.0, unit = "小时"),

    // 口琴 (30011): 金属 + 塑料
    CompositionRelation(productId = 30011, materialId = 7008, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 30011, materialId = 7007, useAmount = 0.05, unit = "千克"),

    // 手风琴 (30012): 木材 + 金属 + 纸板
    CompositionRelation(productId = 30012, materialId = 5005, useAmount = 1.5, unit = "千克"),
    CompositionRelation(productId = 30012, materialId = 7008, useAmount = 0.8, unit = "千克"),
    CompositionRelation(productId = 30012, materialId = 7018, useAmount = 0.5, unit = "千克"),

    // --- 键盘乐器 ---
    // 数码钢琴 (30013): 电子料 + 木材 + 塑料
    CompositionRelation(productId = 30013, materialId = 6006, useAmount = 2.0, unit = "片"),
    CompositionRelation(productId = 30013, materialId = 6004, useAmount = 3.0, unit = "个"),
    CompositionRelation(productId = 30013, materialId = 5005, useAmount = 5.0, unit = "千克"),
    CompositionRelation(productId = 30013, materialId = 6007, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 30013, materialId = 6017, useAmount = 2.0, unit = "个"),

    // 合成器 (30014): 电子料 + 塑料
    CompositionRelation(productId = 30014, materialId = 6006, useAmount = 2.0, unit = "片"),
    CompositionRelation(productId = 30014, materialId = 6004, useAmount = 4.0, unit = "个"),
    CompositionRelation(productId = 30014, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 30014, materialId = 6023, useAmount = 1.0, unit = "个"),

    // 电子琴 (30015): 电子料 + 塑料
    CompositionRelation(productId = 30015, materialId = 6006, useAmount = 1.0, unit = "片"),
    CompositionRelation(productId = 30015, materialId = 6004, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 30015, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 30015, materialId = 6017, useAmount = 2.0, unit = "个"),

    // --- 打击乐器 ---
    // 架子鼓 (30016): 木材 + 金属 + 塑料
    CompositionRelation(productId = 30016, materialId = 5005, useAmount = 8.0, unit = "千克"),
    CompositionRelation(productId = 30016, materialId = 7001, useAmount = 3.0, unit = "千克"),
    CompositionRelation(productId = 30016, materialId = 7007, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 30016, materialId = 7003, useAmount = 0.5, unit = "千克"),

    // 卡洪鼓 (30017): 木材 + 人工工时
    CompositionRelation(productId = 30017, materialId = 5005, useAmount = 3.0, unit = "千克"),
    CompositionRelation(productId = 30017, materialId = 20001, useAmount = 3.0, unit = "小时"),

    // 木琴 (30018): 木材 + 金属
    CompositionRelation(productId = 30018, materialId = 5005, useAmount = 2.0, unit = "千克"),
    CompositionRelation(productId = 30018, materialId = 7002, useAmount = 0.5, unit = "千克"),

    // --- 音频设备 ---
    // 录音棚麦克风 (30019): 电子料 + 金属
    CompositionRelation(productId = 30019, materialId = 6018, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 30019, materialId = 6006, useAmount = 1.0, unit = "片"),
    CompositionRelation(productId = 30019, materialId = 7002, useAmount = 0.3, unit = "千克"),

    // 监听音箱 (30020): 电子料 + 木材 + 塑料
    CompositionRelation(productId = 30020, materialId = 6017, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 30020, materialId = 6006, useAmount = 1.0, unit = "片"),
    CompositionRelation(productId = 30020, materialId = 5005, useAmount = 3.0, unit = "千克"),
    CompositionRelation(productId = 30020, materialId = 6007, useAmount = 1.0, unit = "个"),

    // 调音台 (30021): 电子料 + 塑料
    CompositionRelation(productId = 30021, materialId = 6006, useAmount = 3.0, unit = "片"),
    CompositionRelation(productId = 30021, materialId = 6004, useAmount = 5.0, unit = "个"),
    CompositionRelation(productId = 30021, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 30021, materialId = 6029, useAmount = 20.0, unit = "个"),

    // 音频接口 (30022): 电子料 + 塑料
    CompositionRelation(productId = 30022, materialId = 6006, useAmount = 1.0, unit = "片"),
    CompositionRelation(productId = 30022, materialId = 6004, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 30022, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 30022, materialId = 6029, useAmount = 6.0, unit = "个"),

    // 专业监听耳机 (30023): 电子料 + 塑料 + 橡胶
    CompositionRelation(productId = 30023, materialId = 6017, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 30023, materialId = 6006, useAmount = 1.0, unit = "片"),
    CompositionRelation(productId = 30023, materialId = 6007, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 30023, materialId = 7003, useAmount = 0.1, unit = "千克"),

    // 耳塞 (30024): 电子料 + 塑料 + 硅胶
    CompositionRelation(productId = 30024, materialId = 6017, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 30024, materialId = 6006, useAmount = 1.0, unit = "片"),
    CompositionRelation(productId = 30024, materialId = 7014, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 30024, materialId = 6023, useAmount = 1.0, unit = "个"),

    // --- 媒体载体 ---
    // 黑胶唱片 (30025): 内容载体 + 录制成本 + 创意设计费
    CompositionRelation(productId = 30025, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 30025, materialId = 20006, useAmount = 4.0, unit = "小时"),
    CompositionRelation(productId = 30025, materialId = 20004, useAmount = 1.0, unit = "次"),

    // CD专辑 (30026): 内容载体 + 录制成本 + 创意设计费
    CompositionRelation(productId = 30026, materialId = 20005, useAmount = 1.0, unit = "份"),
    CompositionRelation(productId = 30026, materialId = 20006, useAmount = 3.0, unit = "小时"),
    CompositionRelation(productId = 30026, materialId = 20004, useAmount = 1.0, unit = "次"),

    // 乐谱本 (30027): 内容载体
    CompositionRelation(productId = 30027, materialId = 20005, useAmount = 1.0, unit = "份"),

    // 总谱 (30028): 内容载体
    CompositionRelation(productId = 30028, materialId = 20005, useAmount = 1.0, unit = "份"),

    // --- 配件 ---
    // 拨片套装 (30029): 塑料
    CompositionRelation(productId = 30029, materialId = 7007, useAmount = 0.02, unit = "千克"),

    // 乐器连接线 (30030): 金属 + 塑料
    CompositionRelation(productId = 30030, materialId = 7008, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 30030, materialId = 7007, useAmount = 0.1, unit = "千克"),

    // 麦克风支架 (30031): 金属
    CompositionRelation(productId = 30031, materialId = 7001, useAmount = 2.0, unit = "千克"),

    // 乐谱架 (30032): 金属 + 塑料
    CompositionRelation(productId = 30032, materialId = 7001, useAmount = 1.5, unit = "千克"),
    CompositionRelation(productId = 30032, materialId = 7007, useAmount = 0.2, unit = "千克"),

    // 乐器箱包 (30033): 塑料 + 纺织料
    CompositionRelation(productId = 30033, materialId = 7007, useAmount = 0.8, unit = "千克"),
    CompositionRelation(productId = 30033, materialId = 7016, useAmount = 0.5, unit = "千克"),

    // 调音器 (30034): 电子料 + 塑料
    CompositionRelation(productId = 30034, materialId = 6006, useAmount = 1.0, unit = "片"),
    CompositionRelation(productId = 30034, materialId = 6004, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 30034, materialId = 6007, useAmount = 1.0, unit = "个"),

    // 节拍器 (30035): 电子料 + 塑料
    CompositionRelation(productId = 30035, materialId = 6006, useAmount = 1.0, unit = "片"),
    CompositionRelation(productId = 30035, materialId = 6004, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 30035, materialId = 6007, useAmount = 1.0, unit = "个"),
)

// ============================================
// 📦 包装物料 (ID: 35001~35030) — MaterialItem
// ============================================
val packagingMaterials = listOf(
    MaterialItem(
        id = 35001,
        name = "纸箱",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 5.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 3,
        description = "瓦楞纸箱 标准快递箱"
    ),
    MaterialItem(
        id = 35002,
        name = "塑料膜",
        category = "包装物料",
        unit = "平方米",
        pricePerUnit = 2.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "PE保护膜 透明"
    ),
    MaterialItem(
        id = 35003,
        name = "气泡膜",
        category = "包装物料",
        unit = "平方米",
        pricePerUnit = 3.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "防震气泡膜 缓冲包装"
    ),
    MaterialItem(
        id = 35004,
        name = "泡沫填充",
        category = "包装物料",
        unit = "千克",
        pricePerUnit = 10.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "EPE珍珠棉 填充减震"
    ),
    MaterialItem(
        id = 35005,
        name = "胶带",
        category = "包装物料",
        unit = "卷",
        pricePerUnit = 4.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 3,
        description = "透明封箱胶带 48mm"
    ),
    MaterialItem(
        id = 35006,
        name = "纸袋",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 1.5,
        contactType = ContactType.NON_CONTACT,
        colorId = 3,
        description = "牛皮纸袋 手提"
    ),
    MaterialItem(
        id = 35007,
        name = "塑料袋",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 0.5,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "PE塑料袋 背心式"
    ),
    MaterialItem(
        id = 35008,
        name = "木箱",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 25.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 3,
        description = "实木包装箱 定制尺寸"
    ),
    MaterialItem(
        id = 35009,
        name = "金属罐",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 8.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 5,
        description = "马口铁罐 密封包装"
    ),
    MaterialItem(
        id = 35010,
        name = "玻璃瓶",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 3.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "透明玻璃瓶 食品级"
    ),
    MaterialItem(
        id = 35011,
        name = "塑料瓶",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 1.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "PET塑料瓶 食品级"
    ),
    MaterialItem(
        id = 35012,
        name = "铝罐",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 2.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 5,
        description = "铝合金易拉罐"
    ),
    MaterialItem(
        id = 35013,
        name = "纸质标签",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 0.2,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "不干胶标签 铜版纸"
    ),
    MaterialItem(
        id = 35014,
        name = "热缩膜",
        category = "包装物料",
        unit = "平方米",
        pricePerUnit = 1.5,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "PVC热收缩膜 透明"
    ),
    MaterialItem(
        id = 35015,
        name = "打包带",
        category = "包装物料",
        unit = "米",
        pricePerUnit = 0.5,
        contactType = ContactType.NON_CONTACT,
        colorId = 5,
        description = "PP塑钢打包带"
    ),
    MaterialItem(
        id = 35016,
        name = "瓦楞纸板",
        category = "包装物料",
        unit = "平方米",
        pricePerUnit = 4.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 3,
        description = "三层/五层瓦楞纸板"
    ),
    MaterialItem(
        id = 35017,
        name = "拷贝纸",
        category = "包装物料",
        unit = "张",
        pricePerUnit = 0.3,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "薄页包装纸 防刮花"
    ),
    MaterialItem(
        id = 35018,
        name = "牛皮纸",
        category = "包装物料",
        unit = "张",
        pricePerUnit = 0.8,
        contactType = ContactType.NON_CONTACT,
        colorId = 3,
        description = "厚牛皮纸 包装信封"
    ),
    MaterialItem(
        id = 35019,
        name = "蜂窝纸",
        category = "包装物料",
        unit = "平方米",
        pricePerUnit = 3.5,
        contactType = ContactType.NON_CONTACT,
        colorId = 3,
        description = "蜂窝状纸板 缓冲垫"
    ),
    MaterialItem(
        id = 35020,
        name = "降解袋",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 1.2,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "淀粉基可降解塑料袋"
    ),
    MaterialItem(
        id = 35021,
        name = "堆肥袋",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 1.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 4,
        description = "全生物降解堆肥袋"
    ),
    MaterialItem(
        id = 35022,
        name = "防潮袋",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 0.8,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "铝箔防潮袋 自封"
    ),
    MaterialItem(
        id = 35023,
        name = "干燥剂",
        category = "包装物料",
        unit = "包",
        pricePerUnit = 0.5,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "硅胶干燥剂 防潮"
    ),
    MaterialItem(
        id = 35024,
        name = "自封袋",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 0.3,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "PE自封袋 密封存储"
    ),
    MaterialItem(
        id = 35025,
        name = "真空压缩袋",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 3.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 1,
        description = "衣物真空压缩收纳"
    ),
    MaterialItem(
        id = 35026,
        name = "礼品盒",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 15.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 10,
        description = "精装礼品盒 覆膜"
    ),
    MaterialItem(
        id = 35027,
        name = "展示架",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 12.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 5,
        description = "亚克力展示架 陈列"
    ),
    MaterialItem(
        id = 35028,
        name = "纸板隔层",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 1.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 3,
        description = "瓦楞隔层 分区包装"
    ),
    MaterialItem(
        id = 35029,
        name = "护角条",
        category = "包装物料",
        unit = "米",
        pricePerUnit = 1.2,
        contactType = ContactType.NON_CONTACT,
        colorId = 3,
        description = "纸护角 边角保护"
    ),
    MaterialItem(
        id = 35030,
        name = "托盘",
        category = "包装物料",
        unit = "个",
        pricePerUnit = 30.0,
        contactType = ContactType.NON_CONTACT,
        colorId = 3,
        description = "木托盘 仓储物流"
    ),
)

// ============================================
// 📦 汇总列表
// ============================================
val allMusicAvProducts: List<ProductItem> = musicAvProducts
val allPackagingMaterials: List<MaterialItem> = packagingMaterials
val allNewMusicAvProducts: List<ProductItem> = musicAvProducts
val allNewPackagingMaterials: List<MaterialItem> = packagingMaterials
val allNewMusicAvCompositions: List<CompositionRelation> = musicAvCompositions