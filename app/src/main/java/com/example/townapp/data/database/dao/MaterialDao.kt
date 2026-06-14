package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.townapp.data.database.entity.MaterialEntity

@Dao
interface MaterialDao {
    @Insert
    suspend fun insert(material: MaterialEntity): Long

    @Insert
    suspend fun insertAll(materials: List<MaterialEntity>)

    @Query("SELECT * FROM material ORDER BY name COLLATE NOCASE")
    suspend fun getAll(): List<MaterialEntity>

    @Query("SELECT * FROM material WHERE category = :category ORDER BY name COLLATE NOCASE")
    suspend fun getByCategory(category: String): List<MaterialEntity>

    @Query("SELECT * FROM material WHERE id = :id")
    suspend fun getById(id: Long): MaterialEntity?

    @Query("SELECT * FROM material WHERE name LIKE :name ORDER BY name COLLATE NOCASE")
    suspend fun searchByName(name: String): List<MaterialEntity>

    @Query("DELETE FROM material")
    suspend fun deleteAll()
}