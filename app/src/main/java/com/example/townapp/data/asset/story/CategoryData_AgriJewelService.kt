/**
 * ============================================
 * 🌾 农林农具数据 (ID: 17001~17025)
 * 💎 珠宝古董数据 (ID: 18001~18025)
 * 🧩 无形服务数据 (ID: 19001~19030)
 * ============================================
 */
package com.example.townapp.data

import com.example.townapp.data.ContactType
import com.example.townapp.data.PixelShapeType
import com.example.townapp.data.ProductItem
import com.example.townapp.data.CompositionRelation
import com.example.townapp.data.MaterialItem

// ============================================
// 🌾 农林农具产品 (ID: 17001~17025)
// ============================================
val agricultureProducts = listOf(
    ProductItem(id = 17001, name = "铁锹", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "农用铁锹 锹头碳钢 木柄"),
    ProductItem(id = 17002, name = "锄头", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "农用锄头 松土除草 钢制"),
    ProductItem(id = 17003, name = "镰刀", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "收割镰刀 弧形刀片 木柄"),
    ProductItem(id = 17004, name = "浇水壶", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "园艺浇水壶 2L 塑料材质"),
    ProductItem(id = 17005, name = "园艺剪", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "修枝园艺剪 不锈钢刃口"),
    ProductItem(id = 17006, name = "手推车", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "农用手推车 斗式 载重100kg"),
    ProductItem(id = 17007, name = "修枝锯", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "木工修枝锯 三面磨齿"),
    ProductItem(id = 17008, name = "喷雾器", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "手动喷雾器 16L 农用"),
    ProductItem(id = 17009, name = "育苗盘", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.HORIZONTAL, description = "育苗穴盘 72孔 塑料材质"),
    ProductItem(id = 17010, name = "花盆", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "陶瓷花盆 口径20cm"),
    ProductItem(id = 17011, name = "种子包", category = "农林农具", contactType = ContactType.EDIBLE, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "混合蔬菜种子 家庭种植"),
    ProductItem(id = 17012, name = "肥料", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "复合有机肥 5kg装"),
    ProductItem(id = 17013, name = "培养土", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "营养培养土 10L 通用型"),
    ProductItem(id = 17014, name = "盆栽树", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.VERTICAL, description = "小型松柏盆栽 盆景造型"),
    ProductItem(id = 17015, name = "多肉盆栽", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "多肉植物组合盆栽 含盆"),
    ProductItem(id = 17016, name = "喷壶", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "按压式喷壶 500ml 细雾"),
    ProductItem(id = 17017, name = "耙子", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "农用耙子 铁齿 木柄"),
    ProductItem(id = 17018, name = "铲子", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "园艺小铲 移栽挖土 不锈钢"),
    ProductItem(id = 17019, name = "草帽", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "麦秆草帽 防晒透气"),
    ProductItem(id = 17020, name = "农用手套", category = "农林农具", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "加厚乳胶手套 防刺耐磨"),
    ProductItem(id = 17021, name = "稻草人", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "农田稻草人 驱鸟装饰"),
    ProductItem(id = 17022, name = "滴灌带", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "农用滴灌带 管径16mm 100m"),
    ProductItem(id = 17023, name = "有机堆肥", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "蚯蚓有机堆肥 10kg 改良土壤"),
    ProductItem(id = 17024, name = "果树苗", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.VERTICAL, description = "苹果果树苗 嫁接苗 两年生"),
    ProductItem(id = 17025, name = "温室大棚套件", category = "农林农具", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "小型温室大棚 6m² PVC骨架")
)

// ============================================
// 🔗 农林农具组成关系 (17001~17025)
// ============================================
val agricultureCompositions = listOf(
    // --- 铁锹 (17001) ---
    CompositionRelation(productId = 17001, materialId = 7001, useAmount = 0.8, unit = "千克"),
    CompositionRelation(productId = 17001, materialId = 5005, useAmount = 0.3, unit = "立方米"),
    CompositionRelation(productId = 17001, materialId = 20009, useAmount = 0.5, unit = "月"),

    // --- 锄头 (17002) ---
    CompositionRelation(productId = 17002, materialId = 7001, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 17002, materialId = 5005, useAmount = 0.4, unit = "立方米"),
    CompositionRelation(productId = 17002, materialId = 20009, useAmount = 0.5, unit = "月"),

    // --- 镰刀 (17003) ---
    CompositionRelation(productId = 17003, materialId = 7001, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 17003, materialId = 5005, useAmount = 0.2, unit = "立方米"),
    CompositionRelation(productId = 17003, materialId = 20009, useAmount = 0.3, unit = "月"),

    // --- 浇水壶 (17004) ---
    CompositionRelation(productId = 17004, materialId = 7005, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 17004, materialId = 7006, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 17004, materialId = 20009, useAmount = 0.2, unit = "月"),

    // --- 园艺剪 (17005) ---
    CompositionRelation(productId = 17005, materialId = 7001, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 17005, materialId = 5005, useAmount = 0.15, unit = "立方米"),
    CompositionRelation(productId = 17005, materialId = 20009, useAmount = 0.3, unit = "月"),

    // --- 手推车 (17006) ---
    CompositionRelation(productId = 17006, materialId = 7001, useAmount = 5.0, unit = "千克"),
    CompositionRelation(productId = 17006, materialId = 7002, useAmount = 1.0, unit = "千克"),
    CompositionRelation(productId = 17006, materialId = 7006, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 17006, materialId = 20009, useAmount = 1.0, unit = "月"),

    // --- 修枝锯 (17007) ---
    CompositionRelation(productId = 17007, materialId = 7001, useAmount = 0.25, unit = "千克"),
    CompositionRelation(productId = 17007, materialId = 5005, useAmount = 0.2, unit = "立方米"),
    CompositionRelation(productId = 17007, materialId = 20009, useAmount = 0.3, unit = "月"),

    // --- 喷雾器 (17008) ---
    CompositionRelation(productId = 17008, materialId = 7005, useAmount = 0.8, unit = "千克"),
    CompositionRelation(productId = 17008, materialId = 7006, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 17008, materialId = 7001, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 17008, materialId = 20009, useAmount = 0.5, unit = "月"),

    // --- 育苗盘 (17009) ---
    CompositionRelation(productId = 17009, materialId = 7005, useAmount = 0.15, unit = "千克"),
    CompositionRelation(productId = 17009, materialId = 20009, useAmount = 0.2, unit = "月"),

    // --- 花盆 (17010) ---
    CompositionRelation(productId = 17010, materialId = 7005, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 17010, materialId = 20009, useAmount = 0.2, unit = "月"),

    // --- 种子包 (17011) ---
    CompositionRelation(productId = 17011, materialId = 7010, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 17011, materialId = 20009, useAmount = 1.0, unit = "月"),

    // --- 肥料 (17012) ---
    CompositionRelation(productId = 17012, materialId = 7006, useAmount = 2.0, unit = "千克"),
    CompositionRelation(productId = 17012, materialId = 20009, useAmount = 0.5, unit = "月"),

    // --- 培养土 (17013) ---
    CompositionRelation(productId = 17013, materialId = 5005, useAmount = 0.1, unit = "立方米"),
    CompositionRelation(productId = 17013, materialId = 20009, useAmount = 0.3, unit = "月"),

    // --- 盆栽树 (17014) ---
    CompositionRelation(productId = 17014, materialId = 5005, useAmount = 0.05, unit = "立方米"),
    CompositionRelation(productId = 17014, materialId = 7010, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 17014, materialId = 20009, useAmount = 3.0, unit = "月"),

    // --- 多肉盆栽 (17015) ---
    CompositionRelation(productId = 17015, materialId = 5005, useAmount = 0.02, unit = "立方米"),
    CompositionRelation(productId = 17015, materialId = 7010, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 17015, materialId = 20009, useAmount = 2.0, unit = "月"),

    // --- 喷壶 (17016) ---
    CompositionRelation(productId = 17016, materialId = 7005, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 17016, materialId = 7001, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 17016, materialId = 20009, useAmount = 0.2, unit = "月"),

    // --- 耙子 (17017) ---
    CompositionRelation(productId = 17017, materialId = 7001, useAmount = 0.6, unit = "千克"),
    CompositionRelation(productId = 17017, materialId = 5005, useAmount = 0.3, unit = "立方米"),
    CompositionRelation(productId = 17017, materialId = 20009, useAmount = 0.4, unit = "月"),

    // --- 铲子 (17018) ---
    CompositionRelation(productId = 17018, materialId = 7001, useAmount = 0.15, unit = "千克"),
    CompositionRelation(productId = 17018, materialId = 5005, useAmount = 0.12, unit = "立方米"),
    CompositionRelation(productId = 17018, materialId = 20009, useAmount = 0.2, unit = "月"),

    // --- 草帽 (17019) ---
    CompositionRelation(productId = 17019, materialId = 7010, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 17019, materialId = 20009, useAmount = 0.3, unit = "月"),

    // --- 农用手套 (17020) ---
    CompositionRelation(productId = 17020, materialId = 7006, useAmount = 0.15, unit = "千克"),
    CompositionRelation(productId = 17020, materialId = 7010, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 17020, materialId = 20009, useAmount = 0.2, unit = "月"),

    // --- 稻草人 (17021) ---
    CompositionRelation(productId = 17021, materialId = 5005, useAmount = 0.3, unit = "立方米"),
    CompositionRelation(productId = 17021, materialId = 7010, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 17021, materialId = 20009, useAmount = 0.5, unit = "月"),

    // --- 滴灌带 (17022) ---
    CompositionRelation(productId = 17022, materialId = 7006, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 17022, materialId = 7005, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 17022, materialId = 20009, useAmount = 0.5, unit = "月"),

    // --- 有机堆肥 (17023) ---
    CompositionRelation(productId = 17023, materialId = 5005, useAmount = 0.1, unit = "立方米"),
    CompositionRelation(productId = 17023, materialId = 20009, useAmount = 1.0, unit = "月"),

    // --- 果树苗 (17024) ---
    CompositionRelation(productId = 17024, materialId = 5005, useAmount = 0.03, unit = "立方米"),
    CompositionRelation(productId = 17024, materialId = 20009, useAmount = 2.0, unit = "月"),

    // --- 温室大棚套件 (17025) ---
    CompositionRelation(productId = 17025, materialId = 7002, useAmount = 5.0, unit = "千克"),
    CompositionRelation(productId = 17025, materialId = 7005, useAmount = 3.0, unit = "千克"),
    CompositionRelation(productId = 17025, materialId = 5005, useAmount = 0.2, unit = "立方米"),
    CompositionRelation(productId = 17025, materialId = 20009, useAmount = 1.0, unit = "月")
)

// ============================================
// 💎 珠宝古董产品 (ID: 18001~18025)
// ============================================
val jewelryProducts = listOf(
    ProductItem(id = 18001, name = "金戒指", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "24K纯金戒指 简约素圈"),
    ProductItem(id = 18002, name = "银项链", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.VERTICAL, description = "S925纯银项链 细链吊坠款"),
    ProductItem(id = 18003, name = "钻石手镯", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "18K金镶钻手镯 天然钻石"),
    ProductItem(id = 18004, name = "翡翠吊坠", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "天然A货翡翠 冰种飘花"),
    ProductItem(id = 18005, name = "珍珠耳环", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "Akoya海水珍珠 18K金耳钩"),
    ProductItem(id = 18006, name = "奢华手表", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 2, shapeType = PixelShapeType.SQUARE, description = "自动机械表 蓝宝石镜面 鳄鱼皮表带"),
    ProductItem(id = 18007, name = "设计师手袋", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "头层牛皮 手工缝制 限量款"),
    ProductItem(id = 18008, name = "古董花瓶", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.VERTICAL, description = "清代青花瓷瓶 官窑精品"),
    ProductItem(id = 18009, name = "青铜雕像", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "西周青铜爵 仿古工艺"),
    ProductItem(id = 18010, name = "书法卷轴", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.VERTICAL, description = "名家书法真迹 宣纸裱轴"),
    ProductItem(id = 18011, name = "纪念币", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "纯金纪念币 限量发行 精铸"),
    ProductItem(id = 18012, name = "宝石", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "天然红宝石 鸽血红 1克拉"),
    ProductItem(id = 18013, name = "水晶摆件", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "天然白水晶 晶簇摆件"),
    ProductItem(id = 18014, name = "玛瑙手串", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "天然玛瑙 8mm圆珠 弹力绳"),
    ProductItem(id = 18015, name = "象牙雕刻", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "猛犸象牙雕刻 传统工艺"),
    ProductItem(id = 18016, name = "景泰蓝花瓶", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "铜胎掐丝珐琅 手工制作"),
    ProductItem(id = 18017, name = "黄金项链", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.VERTICAL, description = "足金项链 水波纹链 时尚百搭"),
    ProductItem(id = 18018, name = "铂金戒指", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "PT950铂金 六爪镶嵌 钻石求婚戒"),
    ProductItem(id = 18019, name = "红宝石胸针", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "天然红宝石 18K金镶嵌 复古款"),
    ProductItem(id = 18020, name = "蓝宝石耳坠", category = "珠宝古董", contactType = ContactType.SKIN_CONTACT, colorId = 4, shapeType = PixelShapeType.VERTICAL, description = "蓝宝石 钻石围镶"),
    ProductItem(id = 18021, name = "古董座钟", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "洛可可风格 铜鎏金 机械钟"),
    ProductItem(id = 18022, name = "名家油画", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.HORIZONTAL, description = "当代名家油画 布面油彩 原创"),
    ProductItem(id = 18023, name = "沉香木雕", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "野生沉香 手工木雕 山水摆件"),
    ProductItem(id = 18024, name = "和田玉摆件", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "和田玉 羊脂白玉 精雕"),
    ProductItem(id = 18025, name = "古董瓷器", category = "珠宝古董", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "宋代汝窑天青釉 三足洗")
)

// ============================================
// 🔗 珠宝古董组成关系 (18001~18025)
// ============================================
val jewelryCompositions = listOf(
    // --- 金戒指 (18001) ---
    CompositionRelation(productId = 18001, materialId = 7002, useAmount = 3.0, unit = "克"),
    CompositionRelation(productId = 18001, materialId = 20010, useAmount = 1.0, unit = "项"),

    // --- 银项链 (18002) ---
    CompositionRelation(productId = 18002, materialId = 7002, useAmount = 5.0, unit = "克"),
    CompositionRelation(productId = 18002, materialId = 7010, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 18002, materialId = 20010, useAmount = 1.0, unit = "项"),

    // --- 钻石手镯 (18003) ---
    CompositionRelation(productId = 18003, materialId = 7002, useAmount = 8.0, unit = "克"),
    CompositionRelation(productId = 18003, materialId = 7009, useAmount = 0.05, unit = "平方米"),
    CompositionRelation(productId = 18003, materialId = 20010, useAmount = 2.0, unit = "项"),

    // --- 翡翠吊坠 (18004) ---
    CompositionRelation(productId = 18004, materialId = 7002, useAmount = 2.0, unit = "克"),
    CompositionRelation(productId = 18004, materialId = 7010, useAmount = 0.01, unit = "千克"),
    CompositionRelation(productId = 18004, materialId = 20010, useAmount = 1.5, unit = "项"),

    // --- 珍珠耳环 (18005) ---
    CompositionRelation(productId = 18005, materialId = 7002, useAmount = 1.0, unit = "克"),
    CompositionRelation(productId = 18005, materialId = 20010, useAmount = 1.0, unit = "项"),

    // --- 奢华手表 (18006) ---
    CompositionRelation(productId = 18006, materialId = 7001, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 18006, materialId = 7009, useAmount = 0.1, unit = "平方米"),
    CompositionRelation(productId = 18006, materialId = 7005, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 18006, materialId = 20010, useAmount = 3.0, unit = "项"),

    // --- 设计师手袋 (18007) ---
    CompositionRelation(productId = 18007, materialId = 7009, useAmount = 1.5, unit = "平方米"),
    CompositionRelation(productId = 18007, materialId = 7010, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 18007, materialId = 7002, useAmount = 0.1, unit = "千克"),
    CompositionRelation(productId = 18007, materialId = 20010, useAmount = 2.0, unit = "项"),

    // --- 古董花瓶 (18008) ---
    CompositionRelation(productId = 18008, materialId = 7005, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 18008, materialId = 20010, useAmount = 4.0, unit = "项"),

    // --- 青铜雕像 (18009) ---
    CompositionRelation(productId = 18009, materialId = 7001, useAmount = 2.0, unit = "千克"),
    CompositionRelation(productId = 18009, materialId = 20010, useAmount = 3.0, unit = "项"),

    // --- 书法卷轴 (18010) ---
    CompositionRelation(productId = 18010, materialId = 7010, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 18010, materialId = 5005, useAmount = 0.02, unit = "立方米"),
    CompositionRelation(productId = 18010, materialId = 20010, useAmount = 2.0, unit = "项"),

    // --- 纪念币 (18011) ---
    CompositionRelation(productId = 18011, materialId = 7002, useAmount = 10.0, unit = "克"),
    CompositionRelation(productId = 18011, materialId = 20010, useAmount = 1.0, unit = "项"),

    // --- 宝石 (18012) ---
    CompositionRelation(productId = 18012, materialId = 20010, useAmount = 3.0, unit = "项"),

    // --- 水晶摆件 (18013) ---
    CompositionRelation(productId = 18013, materialId = 5005, useAmount = 0.03, unit = "立方米"),
    CompositionRelation(productId = 18013, materialId = 20010, useAmount = 1.0, unit = "项"),

    // --- 玛瑙手串 (18014) ---
    CompositionRelation(productId = 18014, materialId = 7010, useAmount = 0.02, unit = "千克"),
    CompositionRelation(productId = 18014, materialId = 20010, useAmount = 1.0, unit = "项"),

    // --- 象牙雕刻 (18015) ---
    CompositionRelation(productId = 18015, materialId = 5005, useAmount = 0.02, unit = "立方米"),
    CompositionRelation(productId = 18015, materialId = 20010, useAmount = 3.0, unit = "项"),

    // --- 景泰蓝花瓶 (18016) ---
    CompositionRelation(productId = 18016, materialId = 7001, useAmount = 0.5, unit = "千克"),
    CompositionRelation(productId = 18016, materialId = 7005, useAmount = 0.2, unit = "千克"),
    CompositionRelation(productId = 18016, materialId = 20010, useAmount = 2.0, unit = "项"),

    // --- 黄金项链 (18017) ---
    CompositionRelation(productId = 18017, materialId = 7002, useAmount = 6.0, unit = "克"),
    CompositionRelation(productId = 18017, materialId = 20010, useAmount = 1.5, unit = "项"),

    // --- 铂金戒指 (18018) ---
    CompositionRelation(productId = 18018, materialId = 7001, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 18018, materialId = 7002, useAmount = 3.0, unit = "克"),
    CompositionRelation(productId = 18018, materialId = 20010, useAmount = 2.0, unit = "项"),

    // --- 红宝石胸针 (18019) ---
    CompositionRelation(productId = 18019, materialId = 7002, useAmount = 3.0, unit = "克"),
    CompositionRelation(productId = 18019, materialId = 20010, useAmount = 1.5, unit = "项"),

    // --- 蓝宝石耳坠 (18020) ---
    CompositionRelation(productId = 18020, materialId = 7002, useAmount = 4.0, unit = "克"),
    CompositionRelation(productId = 18020, materialId = 20010, useAmount = 1.5, unit = "项"),

    // --- 古董座钟 (18021) ---
    CompositionRelation(productId = 18021, materialId = 7001, useAmount = 1.5, unit = "千克"),
    CompositionRelation(productId = 18021, materialId = 7002, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 18021, materialId = 5005, useAmount = 0.05, unit = "立方米"),
    CompositionRelation(productId = 18021, materialId = 20010, useAmount = 4.0, unit = "项"),

    // --- 名家油画 (18022) ---
    CompositionRelation(productId = 18022, materialId = 7010, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 18022, materialId = 5005, useAmount = 0.03, unit = "立方米"),
    CompositionRelation(productId = 18022, materialId = 20010, useAmount = 3.0, unit = "项"),

    // --- 沉香木雕 (18023) ---
    CompositionRelation(productId = 18023, materialId = 5005, useAmount = 0.05, unit = "立方米"),
    CompositionRelation(productId = 18023, materialId = 20010, useAmount = 3.0, unit = "项"),

    // --- 和田玉摆件 (18024) ---
    CompositionRelation(productId = 18024, materialId = 5005, useAmount = 0.03, unit = "立方米"),
    CompositionRelation(productId = 18024, materialId = 7010, useAmount = 0.05, unit = "千克"),
    CompositionRelation(productId = 18024, materialId = 20010, useAmount = 2.0, unit = "项"),

    // --- 古董瓷器 (18025) ---
    CompositionRelation(productId = 18025, materialId = 7005, useAmount = 0.3, unit = "千克"),
    CompositionRelation(productId = 18025, materialId = 20010, useAmount = 4.0, unit = "项")
)

// ============================================
// 🧩 无形服务产品 (ID: 19001~19030)
// ============================================
val serviceProducts = listOf(
    ProductItem(id = 19001, name = "家庭保洁", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "全屋深度保洁 4小时服务"),
    ProductItem(id = 19002, name = "理发服务", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "专业剪发洗吹 男女通用"),
    ProductItem(id = 19003, name = "家电维修", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "家用电器上门维修 检测+维修"),
    ProductItem(id = 19004, name = "快递配送", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "同城快递 上门取送 次日达"),
    ProductItem(id = 19005, name = "在线课程", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "职业技能在线课程 视频教学"),
    ProductItem(id = 19006, name = "技术咨询", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "IT技术咨询 方案设计 一对一"),
    ProductItem(id = 19007, name = "翻译服务", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "中英互译 文档翻译 专业级"),
    ProductItem(id = 19008, name = "平面设计", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "LOGO/海报/宣传册设计"),
    ProductItem(id = 19009, name = "演唱会门票", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "热门演唱会 内场VIP座"),
    ProductItem(id = 19010, name = "电影票", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "院线电影票 2D/3D通用"),
    ProductItem(id = 19011, name = "流媒体订阅", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "视频平台月度会员 高清无广告"),
    ProductItem(id = 19012, name = "健身课程", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "健身房团课 月卡 瑜伽/操课"),
    ProductItem(id = 19013, name = "私教训练", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "一对一私教 定制训练计划"),
    ProductItem(id = 19014, name = "宠物美容", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.SQUARE, description = "宠物洗护美容 剪毛修剪指甲"),
    ProductItem(id = 19015, name = "洗车服务", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "标准洗车 内外清洁 打蜡"),
    ProductItem(id = 19016, name = "搬家服务", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "同城搬家 打包搬运 拆装家具"),
    ProductItem(id = 19017, name = "法律咨询", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "法律顾问 合同审查 诉讼咨询"),
    ProductItem(id = 19018, name = "会计服务", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "代理记账 报税 财务咨询"),
    ProductItem(id = 19019, name = "摄影服务", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "人像摄影 证件照 写真跟拍"),
    ProductItem(id = 19020, name = "瑜伽课程", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "哈他瑜伽 小班课 10节套餐"),
    ProductItem(id = 19021, name = "美容护理", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.SQUARE, description = "面部护理 深层清洁 补水保湿"),
    ProductItem(id = 19022, name = "按摩服务", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "中式推拿 全身放松 60分钟"),
    ProductItem(id = 19023, name = "心理咨询", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "心理咨询 情绪疏导 50分钟/次"),
    ProductItem(id = 19024, name = "家政服务", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "钟点工保洁 3小时起 日常维护"),
    ProductItem(id = 19025, name = "管道疏通", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "下水道疏通 马桶疏通 上门服务"),
    ProductItem(id = 19026, name = "空调清洗", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "空调深度清洗 拆机杀菌 2台起"),
    ProductItem(id = 19027, name = "手机维修", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "手机屏幕更换 电池更换 检测维修"),
    ProductItem(id = 19028, name = "电脑维修", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.SQUARE, description = "电脑故障诊断 系统重装 硬件升级"),
    ProductItem(id = 19029, name = "网约车服务", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "快车出行 即时叫车 安全接送"),
    ProductItem(id = 19030, name = "外卖配送", category = "无形服务", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.SQUARE, description = "餐饮外卖 即时配送 30分钟达")
)

// ============================================
// 🔗 无形服务组成关系 (19001~19030)
// ============================================
val serviceCompositions = listOf(
    // --- 家庭保洁 (19001) ---
    CompositionRelation(productId = 19001, materialId = 20001, useAmount = 4.0, unit = "小时"),
    CompositionRelation(productId = 19001, materialId = 20003, useAmount = 1.0, unit = "小时"),

    // --- 理发服务 (19002) ---
    CompositionRelation(productId = 19002, materialId = 20001, useAmount = 1.0, unit = "小时"),
    CompositionRelation(productId = 19002, materialId = 20003, useAmount = 0.5, unit = "小时"),

    // --- 家电维修 (19003) ---
    CompositionRelation(productId = 19003, materialId = 20001, useAmount = 2.0, unit = "小时"),
    CompositionRelation(productId = 19003, materialId = 20002, useAmount = 1.0, unit = "次"),

    // --- 快递配送 (19004) ---
    CompositionRelation(productId = 19004, materialId = 20001, useAmount = 1.0, unit = "小时"),
    CompositionRelation(productId = 19004, materialId = 20007, useAmount = 1.0, unit = "次"),

    // --- 在线课程 (19005) ---
    CompositionRelation(productId = 19005, materialId = 20004, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 19005, materialId = 20001, useAmount = 10.0, unit = "小时"),

    // --- 技术咨询 (19006) ---
    CompositionRelation(productId = 19006, materialId = 20002, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 19006, materialId = 20001, useAmount = 3.0, unit = "小时"),

    // --- 翻译服务 (19007) ---
    CompositionRelation(productId = 19007, materialId = 20001, useAmount = 4.0, unit = "小时"),
    CompositionRelation(productId = 19007, materialId = 20002, useAmount = 1.0, unit = "次"),

    // --- 平面设计 (19008) ---
    CompositionRelation(productId = 19008, materialId = 20004, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 19008, materialId = 20001, useAmount = 8.0, unit = "小时"),

    // --- 演唱会门票 (19009) ---
    CompositionRelation(productId = 19009, materialId = 20003, useAmount = 3.0, unit = "小时"),
    CompositionRelation(productId = 19009, materialId = 20004, useAmount = 1.0, unit = "次"),

    // --- 电影票 (19010) ---
    CompositionRelation(productId = 19010, materialId = 20003, useAmount = 2.0, unit = "小时"),
    CompositionRelation(productId = 19010, materialId = 20001, useAmount = 0.5, unit = "小时"),

    // --- 流媒体订阅 (19011) ---
    CompositionRelation(productId = 19011, materialId = 20004, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 19011, materialId = 20001, useAmount = 2.0, unit = "小时"),

    // --- 健身课程 (19012) ---
    CompositionRelation(productId = 19012, materialId = 20001, useAmount = 10.0, unit = "小时"),
    CompositionRelation(productId = 19012, materialId = 20003, useAmount = 10.0, unit = "小时"),

    // --- 私教训练 (19013) ---
    CompositionRelation(productId = 19013, materialId = 20001, useAmount = 5.0, unit = "小时"),
    CompositionRelation(productId = 19013, materialId = 20003, useAmount = 5.0, unit = "小时"),

    // --- 宠物美容 (19014) ---
    CompositionRelation(productId = 19014, materialId = 20001, useAmount = 1.5, unit = "小时"),
    CompositionRelation(productId = 19014, materialId = 20003, useAmount = 0.5, unit = "小时"),

    // --- 洗车服务 (19015) ---
    CompositionRelation(productId = 19015, materialId = 20001, useAmount = 1.0, unit = "小时"),
    CompositionRelation(productId = 19015, materialId = 20003, useAmount = 0.5, unit = "小时"),

    // --- 搬家服务 (19016) ---
    CompositionRelation(productId = 19016, materialId = 20001, useAmount = 6.0, unit = "小时"),
    CompositionRelation(productId = 19016, materialId = 20007, useAmount = 1.0, unit = "次"),

    // --- 法律咨询 (19017) ---
    CompositionRelation(productId = 19017, materialId = 20002, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 19017, materialId = 20001, useAmount = 2.0, unit = "小时"),

    // --- 会计服务 (19018) ---
    CompositionRelation(productId = 19018, materialId = 20001, useAmount = 4.0, unit = "小时"),
    CompositionRelation(productId = 19018, materialId = 20002, useAmount = 1.0, unit = "次"),

    // --- 摄影服务 (19019) ---
    CompositionRelation(productId = 19019, materialId = 20001, useAmount = 3.0, unit = "小时"),
    CompositionRelation(productId = 19019, materialId = 20004, useAmount = 1.0, unit = "次"),

    // --- 瑜伽课程 (19020) ---
    CompositionRelation(productId = 19020, materialId = 20001, useAmount = 10.0, unit = "小时"),
    CompositionRelation(productId = 19020, materialId = 20003, useAmount = 10.0, unit = "小时"),

    // --- 美容护理 (19021) ---
    CompositionRelation(productId = 19021, materialId = 20001, useAmount = 1.5, unit = "小时"),
    CompositionRelation(productId = 19021, materialId = 20003, useAmount = 0.5, unit = "小时"),

    // --- 按摩服务 (19022) ---
    CompositionRelation(productId = 19022, materialId = 20001, useAmount = 1.0, unit = "小时"),
    CompositionRelation(productId = 19022, materialId = 20003, useAmount = 1.0, unit = "小时"),

    // --- 心理咨询 (19023) ---
    CompositionRelation(productId = 19023, materialId = 20002, useAmount = 1.0, unit = "次"),
    CompositionRelation(productId = 19023, materialId = 20001, useAmount = 1.0, unit = "小时"),

    // --- 家政服务 (19024) ---
    CompositionRelation(productId = 19024, materialId = 20001, useAmount = 3.0, unit = "小时"),
    CompositionRelation(productId = 19024, materialId = 20007, useAmount = 1.0, unit = "次"),

    // --- 管道疏通 (19025) ---
    CompositionRelation(productId = 19025, materialId = 20001, useAmount = 1.0, unit = "小时"),
    CompositionRelation(productId = 19025, materialId = 20002, useAmount = 1.0, unit = "次"),

    // --- 空调清洗 (19026) ---
    CompositionRelation(productId = 19026, materialId = 20001, useAmount = 2.0, unit = "小时"),
    CompositionRelation(productId = 19026, materialId = 20002, useAmount = 1.0, unit = "次"),

    // --- 手机维修 (19027) ---
    CompositionRelation(productId = 19027, materialId = 20001, useAmount = 1.0, unit = "小时"),
    CompositionRelation(productId = 19027, materialId = 20002, useAmount = 1.0, unit = "次"),

    // --- 电脑维修 (19028) ---
    CompositionRelation(productId = 19028, materialId = 20001, useAmount = 2.0, unit = "小时"),
    CompositionRelation(productId = 19028, materialId = 20002, useAmount = 1.0, unit = "次"),

    // --- 网约车服务 (19029) ---
    CompositionRelation(productId = 19029, materialId = 20001, useAmount = 1.0, unit = "小时"),
    CompositionRelation(productId = 19029, materialId = 20007, useAmount = 1.0, unit = "次"),

    // --- 外卖配送 (19030) ---
    CompositionRelation(productId = 19030, materialId = 20001, useAmount = 0.5, unit = "小时"),
    CompositionRelation(productId = 19030, materialId = 20007, useAmount = 1.0, unit = "次")
)

// ============================================
// 📦 各类别汇总列表
// ============================================

/** 农林农具产品汇总 */
val allAgricultureProducts: List<ProductItem> = agricultureProducts

/** 珠宝古董产品汇总 */
val allJewelryProducts: List<ProductItem> = jewelryProducts

/** 无形服务产品汇总 */
val allServiceProducts: List<ProductItem> = serviceProducts

/** 全部新增产品（农林农具 + 珠宝古董 + 无形服务） */
val allNewAgriJewelServiceProducts: List<ProductItem> = agricultureProducts + jewelryProducts + serviceProducts

/** 全部新增组成关系 */
val allNewAgriJewelServiceCompositions: List<CompositionRelation> = agricultureCompositions + jewelryCompositions + serviceCompositions