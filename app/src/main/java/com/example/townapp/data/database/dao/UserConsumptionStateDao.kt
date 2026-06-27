package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.UserConsumptionStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserConsumptionStateDao {

    /** 获取用户消费状态（Flow形式，实时更新） */
    @Query("SELECT * FROM tb_user_consumption_state WHERE userId = :userId LIMIT 1")
    fun getByUserIdFlow(userId: Int = 1): Flow<UserConsumptionStateEntity?>

    /** 暂停式获取（非Flow，供后台任务） */
    @Query("SELECT * FROM tb_user_consumption_state WHERE userId = :userId LIMIT 1")
    suspend fun getByUserId(userId: Int = 1): UserConsumptionStateEntity?

    /** 计数 */
    @Query("SELECT COUNT(*) FROM tb_user_consumption_state")
    suspend fun count(): Int

    /** 插入单个状态 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(state: UserConsumptionStateEntity)

    /** 批量初始化 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UserConsumptionStateEntity>)

    /** 更新单个状态 */
    @Update
    suspend fun update(state: UserConsumptionStateEntity)

    /** 同步更新（非挂起，供后台任务） */
    @Update
    fun updateSync(state: UserConsumptionStateEntity)

    /** 删除 */
    @Query("DELETE FROM tb_user_consumption_state WHERE userId = :userId")
    suspend fun delete(userId: Int)
}
