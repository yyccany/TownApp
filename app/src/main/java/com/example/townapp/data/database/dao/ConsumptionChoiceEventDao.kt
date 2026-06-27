package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.ConsumptionChoiceEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumptionChoiceEventDao {

    /** 获取所有消费抉择事件（Flow形式） */
    @Query("SELECT * FROM tb_consumption_choice_event ORDER BY triggerTime DESC")
    fun getAllFlow(): Flow<List<ConsumptionChoiceEventEntity>>

    /** 暂停式获取所有事件 */
    @Query("SELECT * FROM tb_consumption_choice_event ORDER BY triggerTime DESC")
    suspend fun getAll(): List<ConsumptionChoiceEventEntity>

    /** 按类型获取事件 */
    @Query("SELECT * FROM tb_consumption_choice_event WHERE eventType = :eventType ORDER BY triggerTime DESC")
    suspend fun getByType(eventType: String): List<ConsumptionChoiceEventEntity>

    /** 获取未选择的事件 */
    @Query("SELECT * FROM tb_consumption_choice_event WHERE playerChoice = 0 AND isTriggered = 1 ORDER BY triggerTime DESC LIMIT 1")
    suspend fun getPendingChoice(): ConsumptionChoiceEventEntity?

    /** 按事件ID查询 */
    @Query("SELECT * FROM tb_consumption_choice_event WHERE eventId = :eventId LIMIT 1")
    suspend fun getByEventId(eventId: String): ConsumptionChoiceEventEntity?

    /** 计数 */
    @Query("SELECT COUNT(*) FROM tb_consumption_choice_event")
    suspend fun count(): Int

    /** 按类型计数 */
    @Query("SELECT COUNT(*) FROM tb_consumption_choice_event WHERE eventType = :eventType")
    suspend fun countByType(eventType: String): Int

    /** 插入单个事件 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: ConsumptionChoiceEventEntity)

    /** 批量插入 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ConsumptionChoiceEventEntity>)

    /** 更新单个事件 */
    @Update
    suspend fun update(event: ConsumptionChoiceEventEntity)

    /** 删除 */
    @Query("DELETE FROM tb_consumption_choice_event WHERE id = :id")
    suspend fun delete(id: Long)

    /** 清空所有 */
    @Query("DELETE FROM tb_consumption_choice_event")
    suspend fun clearAll()
}
