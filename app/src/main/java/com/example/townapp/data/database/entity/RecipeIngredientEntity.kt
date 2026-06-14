package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 🥗 食谱食材关联表
 * 记录每个食谱需要的食材及用量
 */
@Entity(
    tableName = "recipe_ingredient",
    primaryKeys = ["recipeId", "materialId"],
    indices = [
        Index(value = ["recipeId"]),
        Index(value = ["materialId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MaterialEntity::class,
            parentColumns = ["id"],
            childColumns = ["materialId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecipeIngredientEntity(
    val recipeId: Long,
    val materialId: Long,
    val quantity: Double,
    val unit: String,
    val isMain: Boolean = false  // 是否为主料
)