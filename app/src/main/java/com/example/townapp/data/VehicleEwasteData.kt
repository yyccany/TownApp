/**
 * ============================================
 * 🚗 汽车成品扩展数据 (ID: 10016~10030)
 * ♻️ 电子废弃物数据 (ID: 6031~6050)
 * ============================================
 */
package com.example.townapp.data

data class Vehicle(
    val name: String,
    val costPerKm: Double,
    val description: String
)

fun getVehicles(): List<Vehicle> {
    return listOf(
        Vehicle("微型轿车", 0.4, "1.0L 城市代步 经济省油"),
        Vehicle("中型SUV", 0.8, "2.0T 五座 家用舒适"),
        Vehicle("豪华轿车", 1.2, "3.0T 旗舰级 商务座驾"),
        Vehicle("敞篷跑车", 1.5, "双座 软顶敞篷 高性能"),
        Vehicle("硬派越野车", 1.0, "四驱 非承载车身 越野"),
        Vehicle("旅行车", 0.7, "2.0T 大空间 长途旅行"),
        Vehicle("厢式货车", 0.6, "载重1.5吨 城市物流"),
        Vehicle("氢燃料电池轿车", 0.3, "氢能源 零排放 续航800km"),
        Vehicle("插电混动SUV", 0.5, "1.5T插混 纯电续航100km"),
        Vehicle("电动超跑", 0.6, "三电机 百公里加速2.5s"),
        Vehicle("纯电大巴", 0.4, "续航300km 载客60人 公交"),
        Vehicle("电动三轮快递车", 0.15, "载重300kg 快递末端配送")
    )
}

// ============================================
// 🚗 扩展汽车成品 (ID: 10016~10030)
// ============================================
val extendedVehicleProducts = listOf(
    ProductItem(id = 10016, name = "微型轿车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "1.0L 城市代步 经济省油"),
    ProductItem(id = 10017, name = "中型SUV", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "2.0T 五座 家用舒适"),
    ProductItem(id = 10018, name = "豪华轿车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 2, shapeType = PixelShapeType.HORIZONTAL, description = "3.0T 旗舰级 商务座驾"),
    ProductItem(id = 10019, name = "敞篷跑车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.HORIZONTAL, description = "双座 软顶敞篷 高性能"),
    ProductItem(id = 10020, name = "硬派越野车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "四驱 非承载车身 越野"),
    ProductItem(id = 10021, name = "旅行车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "2.0T 大空间 长途旅行"),
    ProductItem(id = 10022, name = "厢式货车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "载重1.5吨 城市物流"),
    ProductItem(id = 10023, name = "混凝土搅拌车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 3, shapeType = PixelShapeType.HORIZONTAL, description = "8m³ 罐体 工程用车"),
    ProductItem(id = 10024, name = "垃圾压缩车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 5, shapeType = PixelShapeType.HORIZONTAL, description = "压缩式 环卫专用"),
    ProductItem(id = 10025, name = "校车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 6, shapeType = PixelShapeType.HORIZONTAL, description = "黄色 专用 可载30人"),
    ProductItem(id = 10026, name = "氢燃料电池轿车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 1, shapeType = PixelShapeType.HORIZONTAL, description = "氢能源 零排放 续航800km"),
    ProductItem(id = 10027, name = "插电混动SUV", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "1.5T插混 纯电续航100km"),
    ProductItem(id = 10028, name = "电动超跑", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 10, shapeType = PixelShapeType.HORIZONTAL, description = "三电机 百公里加速2.5s"),
    ProductItem(id = 10029, name = "纯电大巴", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 6, shapeType = PixelShapeType.HORIZONTAL, description = "续航300km 载客60人 公交"),
    ProductItem(id = 10030, name = "电动三轮快递车", category = "汽车", contactType = ContactType.NON_CONTACT, colorId = 4, shapeType = PixelShapeType.HORIZONTAL, description = "载重300kg 快递末端配送")
)

// ============================================
// 🔗 新车组成关系 (10016~10030)
// ============================================
val extendedVehicleCompositions = listOf(
    // --- 微型轿车 (10016) ---
    CompositionRelation(productId = 10016, materialId = 7001, useAmount = 0.6, unit = "吨"),
    CompositionRelation(productId = 10016, materialId = 7002, useAmount = 0.1, unit = "吨"),
    CompositionRelation(productId = 10016, materialId = 7003, useAmount = 30.0, unit = "千克"),
    CompositionRelation(productId = 10016, materialId = 7007, useAmount = 60.0, unit = "千克"),
    CompositionRelation(productId = 10016, materialId = 7008, useAmount = 20.0, unit = "千克"),
    CompositionRelation(productId = 10016, materialId = 6004, useAmount = 20.0, unit = "个"),
    CompositionRelation(productId = 10016, materialId = 5006, useAmount = 5.0, unit = "平方米"),
    CompositionRelation(productId = 10016, materialId = 7009, useAmount = 2.0, unit = "平方米"),

    // --- 中型SUV (10017) ---
    CompositionRelation(productId = 10017, materialId = 7001, useAmount = 1.5, unit = "吨"),
    CompositionRelation(productId = 10017, materialId = 7002, useAmount = 0.3, unit = "吨"),
    CompositionRelation(productId = 10017, materialId = 7003, useAmount = 60.0, unit = "千克"),
    CompositionRelation(productId = 10017, materialId = 7018, useAmount = 20.0, unit = "千克"),
    CompositionRelation(productId = 10017, materialId = 7007, useAmount = 120.0, unit = "千克"),
    CompositionRelation(productId = 10017, materialId = 7008, useAmount = 40.0, unit = "千克"),
    CompositionRelation(productId = 10017, materialId = 6004, useAmount = 40.0, unit = "个"),
    CompositionRelation(productId = 10017, materialId = 5006, useAmount = 8.0, unit = "平方米"),
    CompositionRelation(productId = 10017, materialId = 7009, useAmount = 8.0, unit = "平方米"),
    CompositionRelation(productId = 10017, materialId = 7012, useAmount = 10.0, unit = "千克"),

    // --- 豪华轿车 (10018) ---
    CompositionRelation(productId = 10018, materialId = 7001, useAmount = 1.3, unit = "吨"),
    CompositionRelation(productId = 10018, materialId = 7002, useAmount = 0.4, unit = "吨"),
    CompositionRelation(productId = 10018, materialId = 7005, useAmount = 30.0, unit = "千克"),
    CompositionRelation(productId = 10018, materialId = 7003, useAmount = 50.0, unit = "千克"),
    CompositionRelation(productId = 10018, materialId = 7011, useAmount = 15.0, unit = "千克"),
    CompositionRelation(productId = 10018, materialId = 7009, useAmount = 15.0, unit = "平方米"),
    CompositionRelation(productId = 10018, materialId = 7007, useAmount = 100.0, unit = "千克"),
    CompositionRelation(productId = 10018, materialId = 6004, useAmount = 60.0, unit = "个"),
    CompositionRelation(productId = 10018, materialId = 6017, useAmount = 8.0, unit = "个"),
    CompositionRelation(productId = 10018, materialId = 5005, useAmount = 0.5, unit = "立方米"),

    // --- 敞篷跑车 (10019) ---
    CompositionRelation(productId = 10019, materialId = 7001, useAmount = 0.8, unit = "吨"),
    CompositionRelation(productId = 10019, materialId = 7002, useAmount = 0.5, unit = "吨"),
    CompositionRelation(productId = 10019, materialId = 7005, useAmount = 50.0, unit = "千克"),
    CompositionRelation(productId = 10019, materialId = 7011, useAmount = 20.0, unit = "千克"),
    CompositionRelation(productId = 10019, materialId = 7003, useAmount = 35.0, unit = "千克"),
    CompositionRelation(productId = 10019, materialId = 7007, useAmount = 40.0, unit = "千克"),
    CompositionRelation(productId = 10019, materialId = 7018, useAmount = 10.0, unit = "千克"),
    CompositionRelation(productId = 10019, materialId = 6004, useAmount = 30.0, unit = "个"),
    CompositionRelation(productId = 10019, materialId = 7009, useAmount = 5.0, unit = "平方米"),

    // --- 硬派越野车 (10020) ---
    CompositionRelation(productId = 10020, materialId = 7001, useAmount = 2.0, unit = "吨"),
    CompositionRelation(productId = 10020, materialId = 7002, useAmount = 0.2, unit = "吨"),
    CompositionRelation(productId = 10020, materialId = 7012, useAmount = 30.0, unit = "千克"),
    CompositionRelation(productId = 10020, materialId = 7003, useAmount = 80.0, unit = "千克"),
    CompositionRelation(productId = 10020, materialId = 7018, useAmount = 30.0, unit = "千克"),
    CompositionRelation(productId = 10020, materialId = 7007, useAmount = 80.0, unit = "千克"),
    CompositionRelation(productId = 10020, materialId = 7008, useAmount = 30.0, unit = "千克"),
    CompositionRelation(productId = 10020, materialId = 6004, useAmount = 25.0, unit = "个"),
    CompositionRelation(productId = 10020, materialId = 5006, useAmount = 6.0, unit = "平方米"),
    CompositionRelation(productId = 10020, materialId = 7013, useAmount = 50.0, unit = "千克"),

    // --- 旅行车 (10021) ---
    CompositionRelation(productId = 10021, materialId = 7001, useAmount = 1.4, unit = "吨"),
    CompositionRelation(productId = 10021, materialId = 7002, useAmount = 0.25, unit = "吨"),
    CompositionRelation(productId = 10021, materialId = 7003, useAmount = 55.0, unit = "千克"),
    CompositionRelation(productId = 10021, materialId = 7007, useAmount = 110.0, unit = "千克"),
    CompositionRelation(productId = 10021, materialId = 7008, useAmount = 35.0, unit = "千克"),
    CompositionRelation(productId = 10021, materialId = 6004, useAmount = 35.0, unit = "个"),
    CompositionRelation(productId = 10021, materialId = 5006, useAmount = 10.0, unit = "平方米"),
    CompositionRelation(productId = 10021, materialId = 7009, useAmount = 6.0, unit = "平方米"),
    CompositionRelation(productId = 10021, materialId = 5005, useAmount = 0.3, unit = "立方米"),

    // --- 厢式货车 (10022) ---
    CompositionRelation(productId = 10022, materialId = 7001, useAmount = 1.8, unit = "吨"),
    CompositionRelation(productId = 10022, materialId = 7002, useAmount = 0.15, unit = "吨"),
    CompositionRelation(productId = 10022, materialId = 7013, useAmount = 80.0, unit = "千克"),
    CompositionRelation(productId = 10022, materialId = 7003, useAmount = 40.0, unit = "千克"),
    CompositionRelation(productId = 10022, materialId = 7007, useAmount = 60.0, unit = "千克"),
    CompositionRelation(productId = 10022, materialId = 7008, useAmount = 25.0, unit = "千克"),
    CompositionRelation(productId = 10022, materialId = 6004, useAmount = 15.0, unit = "个"),
    CompositionRelation(productId = 10022, materialId = 5006, useAmount = 4.0, unit = "平方米"),

    // --- 混凝土搅拌车 (10023) ---
    CompositionRelation(productId = 10023, materialId = 7001, useAmount = 3.5, unit = "吨"),
    CompositionRelation(productId = 10023, materialId = 7013, useAmount = 200.0, unit = "千克"),
    CompositionRelation(productId = 10023, materialId = 7003, useAmount = 60.0, unit = "千克"),
    CompositionRelation(productId = 10023, materialId = 7012, useAmount = 50.0, unit = "千克"),
    CompositionRelation(productId = 10023, materialId = 7018, useAmount = 25.0, unit = "千克"),
    CompositionRelation(productId = 10023, materialId = 7007, useAmount = 80.0, unit = "千克"),
    CompositionRelation(productId = 10023, materialId = 6004, useAmount = 10.0, unit = "个"),
    CompositionRelation(productId = 10023, materialId = 7020, useAmount = 20.0, unit = "千克"),

    // --- 垃圾压缩车 (10024) ---
    CompositionRelation(productId = 10024, materialId = 7001, useAmount = 2.8, unit = "吨"),
    CompositionRelation(productId = 10024, materialId = 7013, useAmount = 150.0, unit = "千克"),
    CompositionRelation(productId = 10024, materialId = 7003, useAmount = 45.0, unit = "千克"),
    CompositionRelation(productId = 10024, materialId = 7018, useAmount = 20.0, unit = "千克"),
    CompositionRelation(productId = 10024, materialId = 7007, useAmount = 50.0, unit = "千克"),
    CompositionRelation(productId = 10024, materialId = 7008, useAmount = 30.0, unit = "千克"),
    CompositionRelation(productId = 10024, materialId = 6004, useAmount = 12.0, unit = "个"),
    CompositionRelation(productId = 10024, materialId = 7006, useAmount = 1.0, unit = "桶"),

    // --- 校车 (10025) ---
    CompositionRelation(productId = 10025, materialId = 7001, useAmount = 2.2, unit = "吨"),
    CompositionRelation(productId = 10025, materialId = 7002, useAmount = 0.2, unit = "吨"),
    CompositionRelation(productId = 10025, materialId = 7003, useAmount = 70.0, unit = "千克"),
    CompositionRelation(productId = 10025, materialId = 7018, useAmount = 15.0, unit = "千克"),
    CompositionRelation(productId = 10025, materialId = 7007, useAmount = 150.0, unit = "千克"),
    CompositionRelation(productId = 10025, materialId = 7008, useAmount = 30.0, unit = "千克"),
    CompositionRelation(productId = 10025, materialId = 6004, useAmount = 20.0, unit = "个"),
    CompositionRelation(productId = 10025, materialId = 5006, useAmount = 20.0, unit = "平方米"),
    CompositionRelation(productId = 10025, materialId = 7009, useAmount = 10.0, unit = "平方米"),
    CompositionRelation(productId = 10025, materialId = 7012, useAmount = 15.0, unit = "千克"),

    // --- 氢燃料电池轿车 (10026) ---
    CompositionRelation(productId = 10026, materialId = 7001, useAmount = 1.0, unit = "吨"),
    CompositionRelation(productId = 10026, materialId = 7002, useAmount = 0.35, unit = "吨"),
    CompositionRelation(productId = 10026, materialId = 7005, useAmount = 40.0, unit = "千克"),
    CompositionRelation(productId = 10026, materialId = 7011, useAmount = 25.0, unit = "千克"),
    CompositionRelation(productId = 10026, materialId = 7003, useAmount = 40.0, unit = "千克"),
    CompositionRelation(productId = 10026, materialId = 7007, useAmount = 80.0, unit = "千克"),
    CompositionRelation(productId = 10026, materialId = 6004, useAmount = 50.0, unit = "个"),
    CompositionRelation(productId = 10026, materialId = 6022, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 10026, materialId = 7022, useAmount = 5.0, unit = "千克"),
    CompositionRelation(productId = 10026, materialId = 7008, useAmount = 30.0, unit = "千克"),

    // --- 插电混动SUV (10027) ---
    CompositionRelation(productId = 10027, materialId = 7001, useAmount = 1.4, unit = "吨"),
    CompositionRelation(productId = 10027, materialId = 7002, useAmount = 0.3, unit = "吨"),
    CompositionRelation(productId = 10027, materialId = 7005, useAmount = 25.0, unit = "千克"),
    CompositionRelation(productId = 10027, materialId = 7003, useAmount = 55.0, unit = "千克"),
    CompositionRelation(productId = 10027, materialId = 7007, useAmount = 100.0, unit = "千克"),
    CompositionRelation(productId = 10027, materialId = 6003, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 10027, materialId = 6004, useAmount = 50.0, unit = "个"),
    CompositionRelation(productId = 10027, materialId = 6026, useAmount = 2.0, unit = "片"),
    CompositionRelation(productId = 10027, materialId = 7008, useAmount = 35.0, unit = "千克"),
    CompositionRelation(productId = 10027, materialId = 7009, useAmount = 8.0, unit = "平方米"),

    // --- 电动超跑 (10028) ---
    CompositionRelation(productId = 10028, materialId = 7001, useAmount = 0.6, unit = "吨"),
    CompositionRelation(productId = 10028, materialId = 7002, useAmount = 0.6, unit = "吨"),
    CompositionRelation(productId = 10028, materialId = 7005, useAmount = 80.0, unit = "千克"),
    CompositionRelation(productId = 10028, materialId = 7011, useAmount = 30.0, unit = "千克"),
    CompositionRelation(productId = 10028, materialId = 7003, useAmount = 30.0, unit = "千克"),
    CompositionRelation(productId = 10028, materialId = 6003, useAmount = 2.0, unit = "个"),
    CompositionRelation(productId = 10028, materialId = 6004, useAmount = 80.0, unit = "个"),
    CompositionRelation(productId = 10028, materialId = 6026, useAmount = 4.0, unit = "片"),
    CompositionRelation(productId = 10028, materialId = 6013, useAmount = 4.0, unit = "个"),
    CompositionRelation(productId = 10028, materialId = 7009, useAmount = 4.0, unit = "平方米"),

    // --- 纯电大巴 (10029) ---
    CompositionRelation(productId = 10029, materialId = 7001, useAmount = 5.0, unit = "吨"),
    CompositionRelation(productId = 10029, materialId = 7002, useAmount = 1.0, unit = "吨"),
    CompositionRelation(productId = 10029, materialId = 7003, useAmount = 150.0, unit = "千克"),
    CompositionRelation(productId = 10029, materialId = 7018, useAmount = 50.0, unit = "千克"),
    CompositionRelation(productId = 10029, materialId = 7007, useAmount = 300.0, unit = "千克"),
    CompositionRelation(productId = 10029, materialId = 6003, useAmount = 6.0, unit = "个"),
    CompositionRelation(productId = 10029, materialId = 6004, useAmount = 100.0, unit = "个"),
    CompositionRelation(productId = 10029, materialId = 6026, useAmount = 10.0, unit = "片"),
    CompositionRelation(productId = 10029, materialId = 7008, useAmount = 100.0, unit = "千克"),
    CompositionRelation(productId = 10029, materialId = 5006, useAmount = 30.0, unit = "平方米"),
    CompositionRelation(productId = 10029, materialId = 7012, useAmount = 50.0, unit = "千克"),

    // --- 电动三轮快递车 (10030) ---
    CompositionRelation(productId = 10030, materialId = 7001, useAmount = 40.0, unit = "千克"),
    CompositionRelation(productId = 10030, materialId = 7002, useAmount = 10.0, unit = "千克"),
    CompositionRelation(productId = 10030, materialId = 7003, useAmount = 5.0, unit = "千克"),
    CompositionRelation(productId = 10030, materialId = 7007, useAmount = 15.0, unit = "千克"),
    CompositionRelation(productId = 10030, materialId = 6003, useAmount = 1.0, unit = "个"),
    CompositionRelation(productId = 10030, materialId = 6026, useAmount = 1.0, unit = "片"),
    CompositionRelation(productId = 10030, materialId = 6004, useAmount = 5.0, unit = "个"),
    CompositionRelation(productId = 10030, materialId = 7008, useAmount = 5.0, unit = "千克")
)

// ============================================
// ♻️ 电子废弃物材料 (ID: 6031~6050)
// ============================================
val ewasteMaterials = listOf(
    MaterialItem(id = 6031, name = "废旧电路板", category = "电子料", unit = "千克", pricePerUnit = 15.0, contactType = ContactType.NON_CONTACT, colorId = 4, description = "含金/银/铜 可回收贵金属"),
    MaterialItem(id = 6032, name = "废旧锂电池", category = "电子料", unit = "千克", pricePerUnit = 8.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "含钴/锂/镍 需专业回收处理"),
    MaterialItem(id = 6033, name = "废旧显示器", category = "电子料", unit = "个", pricePerUnit = 20.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "含液晶面板/背光模组 可拆解回收"),
    MaterialItem(id = 6034, name = "废旧硬盘", category = "电子料", unit = "个", pricePerUnit = 10.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "含钕磁铁/铝盘片/电路板可回收"),
    MaterialItem(id = 6035, name = "废旧手机", category = "电子料", unit = "个", pricePerUnit = 25.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "含金/银/钯/铜 城市矿产"),
    MaterialItem(id = 6036, name = "废旧电源适配器", category = "电子料", unit = "个", pricePerUnit = 3.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "含铜线圈/电路板 可拆解回收"),
    MaterialItem(id = 6037, name = "废旧打印机", category = "电子料", unit = "台", pricePerUnit = 15.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "含电路板/电机/塑料 可拆解"),
    MaterialItem(id = 6038, name = "废旧CRT显示器", category = "电子料", unit = "台", pricePerUnit = -10.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "含铅玻璃/电子枪 处理成本高"),
    MaterialItem(id = 6039, name = "废旧电路板粉末", category = "电子料", unit = "千克", pricePerUnit = 12.0, contactType = ContactType.NON_CONTACT, colorId = 3, description = "破碎分选后 含贵金属精矿"),
    MaterialItem(id = 6040, name = "废旧电线电缆", category = "电子料", unit = "千克", pricePerUnit = 35.0, contactType = ContactType.NON_CONTACT, colorId = 3, description = "含铜/铝线芯 PVC外皮可分离"),
    MaterialItem(id = 6041, name = "废旧集成电路", category = "电子料", unit = "千克", pricePerUnit = 50.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "含硅晶圆/金线/引脚 高价值回收"),
    MaterialItem(id = 6042, name = "废旧液晶面板", category = "电子料", unit = "片", pricePerUnit = 5.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "含液晶/偏光片/玻璃 需专业处理"),
    MaterialItem(id = 6043, name = "废旧电容器", category = "电子料", unit = "千克", pricePerUnit = 8.0, contactType = ContactType.NON_CONTACT, colorId = 3, description = "含铝/钽/电解液 可回收金属"),
    MaterialItem(id = 6044, name = "废旧变压器", category = "电子料", unit = "个", pricePerUnit = 20.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "含铜线圈/硅钢片 可拆解回收"),
    MaterialItem(id = 6045, name = "废旧键盘", category = "电子料", unit = "个", pricePerUnit = 2.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "含塑料/电路薄膜 低价值回收"),
    MaterialItem(id = 6046, name = "废旧鼠标", category = "电子料", unit = "个", pricePerUnit = 1.5, contactType = ContactType.NON_CONTACT, colorId = 2, description = "含塑料/光学模组 低价值回收"),
    MaterialItem(id = 6047, name = "废弃硒鼓", category = "电子料", unit = "个", pricePerUnit = 5.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "含碳粉/感光鼓 可回收再生"),
    MaterialItem(id = 6048, name = "废旧散热器", category = "电子料", unit = "个", pricePerUnit = 8.0, contactType = ContactType.NON_CONTACT, colorId = 5, description = "含铝/铜 高纯度金属回收"),
    MaterialItem(id = 6049, name = "废旧散热风扇", category = "电子料", unit = "个", pricePerUnit = 2.0, contactType = ContactType.NON_CONTACT, colorId = 2, description = "含铜线圈/塑料 可拆解回收"),
    MaterialItem(id = 6050, name = "废旧屏幕玻璃", category = "电子料", unit = "千克", pricePerUnit = -2.0, contactType = ContactType.NON_CONTACT, colorId = 1, description = "含 aluminosilicate 玻璃 处理成本较高")
)

// ============================================
// 📦 所有新增汽车成品汇总
// ============================================
val allExtendedVehicleProducts: List<ProductItem> by lazy {
    extendedVehicleProducts
}

// ============================================
// 📦 所有新增电子废弃物材料汇总
// ============================================
val allEwasteMaterials: List<MaterialItem> by lazy {
    ewasteMaterials
}

// ============================================
// 📦 所有扩展材料汇总（含电子废弃物）
// ============================================
val allExtendedMaterialsWithEwaste: List<MaterialItem> by lazy {
    extendedBuildingMaterials + extendedElectronicMaterials + extendedIndustrialMaterials + ewasteMaterials
}

// ============================================
// 📦 所有材料汇总（含扩展和电子废弃物）
// ============================================
val allMaterialsComprehensive: List<MaterialItem> by lazy {
    buildingMaterials + electronicMaterials + industrialMaterials +
    extendedBuildingMaterials + extendedElectronicMaterials + extendedIndustrialMaterials +
    ewasteMaterials
}

// ============================================
// 📦 按接触分类的筛选列表
// ============================================
/** 所有食用类材料 (contactType = EDIBLE) */
val edibleMaterials: List<MaterialItem>
    get() = allMaterials.filter { it.contactType == ContactType.EDIBLE }

/** 所有肤接触材料 (contactType = SKIN_CONTACT) */
val skinContactMaterials: List<MaterialItem>
    get() = allMaterials.filter { it.contactType == ContactType.SKIN_CONTACT }

/** 所有非接触材料 (contactType = NON_CONTACT) */
val nonContactMaterials: List<MaterialItem>
    get() = allMaterials.filter { it.contactType == ContactType.NON_CONTACT }