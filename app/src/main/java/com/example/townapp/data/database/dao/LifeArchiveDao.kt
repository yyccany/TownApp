package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.LifeArchiveEntity
import kotlinx.coroutines.flow.Flow

/**
 * 人生存档 DAO
 */
@Dao
interface LifeArchiveDao {

    /**
     * 获取所有存档，按时间倒序
     */
    @Query("SELECT * FROM life_archive ORDER BY createdAt DESC")
    fun getAllArchives(): Flow<List<LifeArchiveEntity>>

    /**
     * 获取单个存档
     */
    @Query("SELECT * FROM life_archive WHERE id = :id")
    suspend fun getArchiveById(id: Long): LifeArchiveEntity?

    /**
     * 获取存档数量
     */
    @Query("SELECT COUNT(*) FROM life_archive")
    fun getArchiveCount(): Flow<Int>

    /**
     * 插入新存档
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArchive(archive: LifeArchiveEntity): Long

    /**
     * 更新存档
     */
    @Update
    suspend fun updateArchive(archive: LifeArchiveEntity)

    /**
     * 删除存档
     */
    @Delete
    suspend fun deleteArchive(archive: LifeArchiveEntity)

    /**
     * 根据ID删除存档
     */
    @Query("DELETE FROM life_archive WHERE id = :id")
    suspend fun deleteArchiveById(id: Long)

    /**
     * 清空所有存档
     */
    @Query("DELETE FROM life_archive")
    suspend fun deleteAllArchives()

    /**
     * 按心情筛选存档
     */
    @Query("SELECT * FROM life_archive WHERE mood = :mood ORDER BY createdAt DESC")
    fun getArchivesByMood(mood: String): Flow<List<LifeArchiveEntity>>

    /**
     * 搜索存档内容
     */
    @Query("SELECT * FROM life_archive WHERE content LIKE '%' || :keyword || '%' ORDER BY createdAt DESC")
    fun searchArchives(keyword: String): Flow<List<LifeArchiveEntity>>
}
