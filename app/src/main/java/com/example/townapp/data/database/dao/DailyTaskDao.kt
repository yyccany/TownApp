package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.DailyTaskEntity

@Dao
interface DailyTaskDao {
    @Query("SELECT * FROM daily_tasks WHERE date = :date")
    suspend fun getTasksByDate(date: String): List<DailyTaskEntity>

    @Insert
    suspend fun insertTask(task: DailyTaskEntity)

    @Update
    suspend fun updateTask(task: DailyTaskEntity)

    @Query("SELECT COUNT(*) FROM daily_tasks WHERE date = :date")
    suspend fun getTaskCountByDate(date: String = java.text.SimpleDateFormat("yyyy-MM-dd").format(java.util.Date())): Int

    @Insert
    suspend fun insertAll(tasks: List<DailyTaskEntity>)
}