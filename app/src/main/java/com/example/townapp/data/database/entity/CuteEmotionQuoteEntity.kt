package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// ============================================
// 💬 角色情绪语录表 — 每个角色在不同情绪下的专属话语
// 情绪类型：happy(开心) / worried(担心) / sad(难过) / excited(兴奋) / envious(羡慕) / consoling(安慰)
// ============================================
@Entity(
    tableName = "companion_emotion_quotes",
    foreignKeys = [
        ForeignKey(
            entity = CuteCharacterEntity::class,
            parentColumns = ["characterId"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("characterId")]
)
data class CuteEmotionQuoteEntity(
    @PrimaryKey(autoGenerate = true) val quoteId: Int = 0,
    val characterId: Int,
    val emotionType: String,           // happy / worried / sad / excited / envious / consoling
    val quoteText: String,             // 语录内容
    val emoji: String,                 // 配套emoji
    val triggerWeight: Int = 10       // 触发权重（影响随机选择概率）
) {
    companion object {
        // 情绪类型常量
        const val EMOTION_HAPPY = "happy"
        const val EMOTION_WORRIED = "worried"
        const val EMOTION_SAD = "sad"
        const val EMOTION_EXCITED = "excited"
        const val EMOTION_ENVIOUS = "envious"
        const val EMOTION_CONSOLING = "consoling"
        const val EMOTION_GENTLE = "gentle"
        const val EMOTION_GREETING = "greeting"
    }
}
