package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.townapp.data.database.entity.AppConfigEntity

@Dao
interface AppConfigDao {
    @Insert
    suspend fun insert(config: AppConfigEntity): Long

    @Query("SELECT * FROM app_config ORDER BY id DESC LIMIT 1")
    suspend fun getLatest(): AppConfigEntity?

    @Update
    suspend fun update(config: AppConfigEntity)

    @Query("DELETE FROM app_config")
    suspend fun deleteAll()
}