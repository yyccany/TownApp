package com.example.townapp.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "formula",
    primaryKeys = ["productId", "materialId"],
    indices = [
        Index(value = ["productId"]),
        Index(value = ["materialId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
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
data class FormulaEntity(
    val productId: Long,
    val materialId: Long,
    val dosage: Double,
    val unit: String
)