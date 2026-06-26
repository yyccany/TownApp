package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.townapp.data.database.entity.GoodsConsumptionTagEntity

@Dao
interface GoodsConsumptionTagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tag: GoodsConsumptionTagEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tags: List<GoodsConsumptionTagEntity>)

    @Query("SELECT * FROM goods_consumption_tags WHERE goodsId = :goodsId AND goodsType = :goodsType")
    suspend fun getTagsByGoods(goodsId: String, goodsType: String): List<GoodsConsumptionTagEntity>

    @Query("SELECT * FROM goods_consumption_tags WHERE goodsType = :goodsType")
    suspend fun getTagsByType(goodsType: String): List<GoodsConsumptionTagEntity>

    @Query("SELECT * FROM goods_consumption_tags")
    suspend fun getAll(): List<GoodsConsumptionTagEntity>

    @Query("DELETE FROM goods_consumption_tags")
    suspend fun deleteAll()
}
