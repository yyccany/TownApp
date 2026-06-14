package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.townapp.data.database.entity.FormulaEntity

@Dao
interface FormulaDao {
    @Insert
    suspend fun insert(formula: FormulaEntity)

    @Insert
    suspend fun insertAll(formulas: List<FormulaEntity>)

    @Query("SELECT * FROM formula WHERE productId = :productId")
    suspend fun getByProductId(productId: Long): List<FormulaEntity>

    @Query("SELECT * FROM formula WHERE materialId = :materialId")
    suspend fun getByMaterialId(materialId: Long): List<FormulaEntity>

    @Query("DELETE FROM formula WHERE productId = :productId")
    suspend fun deleteByProductId(productId: Long)

    @Query("DELETE FROM formula")
    suspend fun deleteAll()
}