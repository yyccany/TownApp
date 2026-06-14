package com.example.townapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.townapp.data.database.entity.UserSpaceState
import kotlinx.coroutines.flow.Flow

/**
 * 空间状态数据访问接口
 */
@Dao
interface UserSpaceStateDao {

    @Query("SELECT * FROM tb_user_space_state WHERE userId = :userId LIMIT 1")
    fun getFlow(userId: Int = 1): Flow<UserSpaceState?>

    @Query("SELECT * FROM tb_user_space_state WHERE userId = :userId LIMIT 1")
    suspend fun get(userId: Int = 1): UserSpaceState?

    @Query("SELECT * FROM tb_user_space_state WHERE userId = :userId LIMIT 1")
    fun getSync(userId: Int = 1): UserSpaceState?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(state: UserSpaceState)

    @Update
    suspend fun update(state: UserSpaceState)

    @Update
    fun updateSync(state: UserSpaceState)

    @Query("UPDATE tb_user_space_state SET updateTime = :time WHERE userId = :userId")
    suspend fun touch(userId: Int, time: Long = System.currentTimeMillis())
}
