package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.townapp.data.database.entity.ProductEntity

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: ProductEntity): Long

    @Insert
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM product WHERE hideInChild = 0 ORDER BY name COLLATE NOCASE")
    suspend fun getAllForChild(): List<ProductEntity>

    @Query("SELECT * FROM product ORDER BY name COLLATE NOCASE")
    suspend fun getAllForAdult(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE category = :category AND hideInChild = 0 ORDER BY name COLLATE NOCASE")
    suspend fun getByCategoryForChild(category: String): List<ProductEntity>

    @Query("SELECT * FROM product WHERE category = :category ORDER BY name COLLATE NOCASE")
    suspend fun getByCategoryForAdult(category: String): List<ProductEntity>

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getById(id: Long): ProductEntity?

    @Query("SELECT * FROM product WHERE name LIKE :name AND hideInChild = 0 ORDER BY name COLLATE NOCASE")
    suspend fun searchForChild(name: String): List<ProductEntity>

    @Query("SELECT * FROM product WHERE name LIKE :name ORDER BY name COLLATE NOCASE")
    suspend fun searchForAdult(name: String): List<ProductEntity>

    @Query("SELECT DISTINCT category FROM product ORDER BY category COLLATE NOCASE")
    suspend fun getAllCategories(): List<String>

    @Update
    suspend fun update(product: ProductEntity)

    @Query("DELETE FROM product")
    suspend fun deleteAll()
}