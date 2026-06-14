package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.townapp.data.database.entity.UserMentalState
import kotlinx.coroutines.flow.Flow

/**
 * 精神状态数据访问接口
 */
@Dao
interface UserMentalStateDao {

    @Query("SELECT * FROM tb_user_mental_state WHERE userId = :userId LIMIT 1")
    fun getFlow(userId: Int = 1): Flow<UserMentalState?>

    @Query("SELECT * FROM tb_user_mental_state WHERE userId = :userId LIMIT 1")
    suspend fun get(userId: Int = 1): UserMentalState?

    @Query("SELECT * FROM tb_user_mental_state WHERE userId = :userId LIMIT 1")
    fun getSync(userId: Int = 1): UserMentalState?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(state: UserMentalState)

    @Update
    suspend fun update(state: UserMentalState)

    @Update
    fun updateSync(state: UserMentalState)

    @Query("UPDATE tb_user_mental_state SET updateTime = :time WHERE userId = :userId")
    suspend fun touch(userId: Int, time: Long = System.currentTimeMillis())
}
