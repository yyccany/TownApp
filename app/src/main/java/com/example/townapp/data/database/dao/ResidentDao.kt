package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.ResidentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResidentDao {
    @Query("SELECT * FROM residents ORDER BY id ASC")
    fun getAll(): Flow<List<ResidentEntity>>

    @Query("SELECT * FROM residents WHERE id = :id")
    suspend fun getById(id: Int): ResidentEntity?

    @Query("SELECT COUNT(*) FROM residents")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(residents: List<ResidentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resident: ResidentEntity)

    @Update
    suspend fun update(resident: ResidentEntity)
}