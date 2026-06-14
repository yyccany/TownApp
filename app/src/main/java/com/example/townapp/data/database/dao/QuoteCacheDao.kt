package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.townapp.data.database.entity.QuoteCacheEntity

@Dao
interface QuoteCacheDao {
    @Insert
    suspend fun insert(cache: QuoteCacheEntity)

    @Query("SELECT * FROM quote_cache WHERE cacheKey = :key")
    suspend fun getByKey(key: String): QuoteCacheEntity?

    @Query("DELETE FROM quote_cache WHERE useTime < :expireTime")
    suspend fun cleanExpired(expireTime: Long)

    @Query("DELETE FROM quote_cache")
    suspend fun deleteAll()
}