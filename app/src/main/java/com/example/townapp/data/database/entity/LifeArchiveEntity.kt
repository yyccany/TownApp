package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 人生存档实体
 *
 * 用户可以把所有不能跟别人说的话，存在这里。
 * 只有用户自己能看，永远不会被泄露。
 *
 * @param id 唯一标识
 * @param content 存档内容
 * @param emoji 配套emoji（可选）
 * @param mood 当时的心情（可选）
 * @param createdAt 创建时间
 * @param updatedAt 更新时间
 */
@Entity(tableName = "life_archive")
data class LifeArchiveEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /** 存档内容 */
    val content: String,

    /** 配套emoji（可选） */
    val emoji: String? = null,

    /** 当时的心情（happy/sad/angry/afraid/anxious/tired/neutral） */
    val mood: String? = null,

    /** 创建时间（时间戳） */
    val createdAt: Long = System.currentTimeMillis(),

    /** 更新时间（时间戳） */
    val updatedAt: Long = System.currentTimeMillis()
)
