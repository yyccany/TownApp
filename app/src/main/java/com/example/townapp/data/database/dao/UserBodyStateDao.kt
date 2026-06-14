package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.UserBodyState
import kotlinx.coroutines.flow.Flow

@Dao
interface UserBodyStateDao {
    /** 全量获取所有居民生理状态 */
    @Query("SELECT * FROM tb_user_body_state ORDER BY userId ASC")
    fun getAll(): Flow<List<UserBodyState>>

    /** 暂停式获取（非Flow，供后台任务） */
    @Query("SELECT * FROM tb_user_body_state ORDER BY userId ASC")
    suspend fun getAllSync(): List<UserBodyState>

    /** 按居民ID查询 */
    @Query("SELECT * FROM tb_user_body_state WHERE userId = :userId")
    suspend fun getByUserId(userId: Int): UserBodyState?

    /** 按居民ID查询（简化版本） */
    @Query("SELECT * FROM tb_user_body_state WHERE userId = :userId LIMIT 1")
    suspend fun get(userId: Int = 1): UserBodyState?

    /** 计数 */
    @Query("SELECT COUNT(*) FROM tb_user_body_state")
    suspend fun count(): Int

    /** 插入单个状态 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(state: UserBodyState)

    /** 批量初始化 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UserBodyState>)

    /** 更新单个居民状态（喂食后即时更新） */
    @Update
    suspend fun update(state: UserBodyState)

    /** 同步更新（非挂起，供后台任务） */
    @Update
    fun updateSync(state: UserBodyState)

    /** 批量更新（后台长期任务用） */
    @Update
    suspend fun updateAll(list: List<UserBodyState>)

    /** 删除 */
    @Query("DELETE FROM tb_user_body_state WHERE userId = :userId")
    suspend fun delete(userId: Int)

    /** 同步获取 */
    @Query("SELECT * FROM tb_user_body_state WHERE userId = :userId LIMIT 1")
    fun getSync(userId: Int = 1): UserBodyState?
}