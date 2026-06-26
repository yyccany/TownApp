package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 商品消费标签表 —— 数据驱动，配置化决定商品对消费倾向的影响
 * 不用在代码里硬编码isIQTax、isHealthy等布尔值，全部通过标签配置
 *
 * 标签说明：
 * - nutrient_high：高营养密度 → 饮食维度加分
 * - junk_food：垃圾食品/空热量 → 饮食维度减分
 * - luxury_premium：溢价/智商税/排场消费 → 对应维度减分
 * - health_device：健康家电（新风/净水/除湿等）→ 居家维度加分
 * - decor_only：纯装饰无实用价值 → 居家维度减分
 * - skin_friendly：亲肤舒适材质 → 穿搭维度加分
 * - uncomfortable：不舒适/廉价贴身 → 穿搭维度减分
 * - safety_comfort：安全/护具/人体工学 → 出行维度加分
 * - car_loan：豪车贷款/外观改装 → 出行维度减分
 */
@Entity(
    tableName = "goods_consumption_tags",
    indices = [Index(value = ["goodsId", "goodsType"])]
)
data class GoodsConsumptionTagEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val goodsId: String,
    val goodsType: String, // food / clothing / housing / transport
    val tagKey: String,
    val scoreDelta: Int, // 加分/减分数值（正=人本路线加分，负=虚荣路线减分）
    val description: String = ""
)
