package com.example.townapp.data.repository

import com.example.townapp.data.database.dao.LifeArchiveDao
import com.example.townapp.data.database.entity.LifeArchiveEntity
import kotlinx.coroutines.flow.Flow

/**
 * 人生存档仓库
 *
 * 提供对人生存档数据的统一访问接口。
 * 所有的读取和写入都通过这里进行。
 */
class LifeArchiveRepository(
    private val lifeArchiveDao: LifeArchiveDao
) {
    /**
     * 获取所有存档
     */
    fun getAllArchives(): Flow<List<LifeArchiveEntity>> =
        lifeArchiveDao.getAllArchives()

    /**
     * 获取单个存档
     */
    suspend fun getArchiveById(id: Long): LifeArchiveEntity? =
        lifeArchiveDao.getArchiveById(id)

    /**
     * 获取存档数量
     */
    fun getArchiveCount(): Flow<Int> =
        lifeArchiveDao.getArchiveCount()

    /**
     * 创建新存档
     */
    suspend fun createArchive(
        content: String,
        emoji: String? = null,
        mood: String? = null
    ): Long {
        val archive = LifeArchiveEntity(
            content = content,
            emoji = emoji,
            mood = mood,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        return lifeArchiveDao.insertArchive(archive)
    }

    /**
     * 更新存档
     */
    suspend fun updateArchive(
        id: Long,
        content: String,
        emoji: String? = null,
        mood: String? = null
    ) {
        val existing = lifeArchiveDao.getArchiveById(id) ?: return
        val updated = existing.copy(
            content = content,
            emoji = emoji,
            mood = mood,
            updatedAt = System.currentTimeMillis()
        )
        lifeArchiveDao.updateArchive(updated)
    }

    /**
     * 删除存档
     */
    suspend fun deleteArchive(id: Long) {
        lifeArchiveDao.deleteArchiveById(id)
    }

    /**
     * 清空所有存档
     */
    suspend fun deleteAllArchives() {
        lifeArchiveDao.deleteAllArchives()
    }

    /**
     * 按心情筛选
     */
    fun getArchivesByMood(mood: String): Flow<List<LifeArchiveEntity>> =
        lifeArchiveDao.getArchivesByMood(mood)

    /**
     * 搜索存档
     */
    fun searchArchives(keyword: String): Flow<List<LifeArchiveEntity>> =
        lifeArchiveDao.searchArchives(keyword)
}
