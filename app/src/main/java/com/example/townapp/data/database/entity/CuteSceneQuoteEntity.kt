package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// ============================================
// 🌤️ 角色场景语录表 — 特定时间、行为触发的专属对话
// 场景类型：time_early_morning(凌晨) / time_late_night(深夜) / time_morning(早晨) / 
//          action_food(吃东西) / action_drink(喝饮料) / action_medicine(吃药) / 
//          action_clothing(穿衣) / action_long_absent(久未登录) / action_weather_rain(雨天)
// ============================================
@Entity(
    tableName = "companion_scene_quotes",
    foreignKeys = [
        ForeignKey(
            entity = CuteCharacterEntity::class,
            parentColumns = ["characterId"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("characterId"), Index("sceneType")]
)
data class CuteSceneQuoteEntity(
    @PrimaryKey(autoGenerate = true) val quoteId: Int = 0,
    val characterId: Int,
    val sceneType: String,             // 场景类型
    val quoteText: String,             // 语录内容
    val emoji: String,                 // 配套emoji
    val priority: Int = 5,            // 优先级（数字越大优先级越高）
    val moodChange: Int = 0           // 触发后心情变化值
) {
    companion object {
        // 场景类型常量
        const val SCENE_MIDNIGHT = "time_midnight"          // 凌晨3点
        const val SCENE_LATE_NIGHT = "time_late_night"     // 深夜
        const val SCENE_MORNING = "time_morning"           // 早晨
        const val SCENE_AFTERNOON = "time_afternoon"       // 下午
        
        const val SCENE_FOOD_JUNK = "action_food_junk"     // 吃垃圾食品
        const val SCENE_FOOD_HEALTHY = "action_food_healthy" // 吃健康食物
        const val SCENE_DRINK_MILKTEA = "action_drink_milktea" // 喝奶茶
        const val SCENE_DRINK_WATER = "action_drink_water"  // 喝水
        const val SCENE_MEDICINE = "action_medicine"       // 吃药
        const val SCENE_CLOTHING_BAD = "action_clothing_bad" // 穿不舒适的衣物
        
        const val SCENE_LONG_ABSENT_7 = "absent_7days"     // 7天未登录
        const val SCENE_LONG_ABSENT_30 = "absent_30days"   // 30天未登录
        
        const val SCENE_WEATHER_RAIN = "weather_rain"       // 雨天
        const val SCENE_WEATHER_HOT = "weather_hot"         // 高温
        
        const val SCENE_VALUE_LOW = "value_low"            // 买了低价值物品
        const val SCENE_VALUE_HIGH = "value_high"          // 买了高价值物品
        
        const val SCENE_SCAMPERED = "action_scam"           // 被骗了
        
        const val SCENE_BIRTHDAY = "character_birthday"      // 角色生日
        const val SCENE_ANNIVERSARY = "town_anniversary"     // 小镇周年纪念日
        const val SCENE_RANDOM_SURPRISE = "random_surprise"  // 随机小惊喜
    }
}
