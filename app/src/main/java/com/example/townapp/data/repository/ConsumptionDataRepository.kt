package com.example.townapp.data.repository

import com.example.townapp.data.database.dao.GoodsConsumptionTagDao
import com.example.townapp.data.database.dao.MindTextLibDao
import com.example.townapp.data.database.dao.QuoteDao
import com.example.townapp.data.database.entity.GoodsConsumptionTagEntity
import com.example.townapp.data.database.entity.MindTextLibEntity
import com.example.townapp.data.database.entity.QuoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConsumptionDataRepository @Inject constructor(
    private val goodsTagDao: GoodsConsumptionTagDao,
    private val mindTextDao: MindTextLibDao,
    private val quoteDao: QuoteDao
) {
    suspend fun getGoodsTags(goodsId: String, goodsType: String): List<GoodsConsumptionTagEntity> {
        return goodsTagDao.getTagsByGoods(goodsId, goodsType)
    }

    suspend fun calculateGoodsScoreDelta(goodsId: String, goodsType: String): Int {
        return getGoodsTags(goodsId, goodsType).sumOf { it.scoreDelta }
    }

    suspend fun getRandomMindText(type: String, consumptionScore: Int): MindTextLibEntity? {
        return mindTextDao.getRandomByTypeAndScore(type, consumptionScore.coerceIn(0, 100))
    }

    suspend fun getMindTextsByType(type: String): List<MindTextLibEntity> {
        return mindTextDao.getByType(type)
    }

    suspend fun getRandomQuote(roleId: String?, sceneId: String?, consumptionScore: Int): QuoteEntity? {
        val score = consumptionScore.coerceIn(0, 100)
        return when {
            roleId != null && sceneId != null -> quoteDao.getRandomByRoleSceneAndScore(roleId, sceneId, score)
            roleId != null -> quoteDao.getRandomByRoleAndScore(roleId, score)
            sceneId != null -> quoteDao.getRandomBySceneAndScore(sceneId, score)
            else -> null
        }
    }

    suspend fun insertGoodsTags(tags: List<GoodsConsumptionTagEntity>) {
        goodsTagDao.insertAll(tags)
    }

    suspend fun insertMindTexts(texts: List<MindTextLibEntity>) {
        mindTextDao.insertAll(texts)
    }

    suspend fun insertQuotes(quotes: List<QuoteEntity>) {
        quoteDao.insertAll(quotes)
    }

    suspend fun getAllGoodsTags(): List<GoodsConsumptionTagEntity> {
        return goodsTagDao.getAll()
    }

    suspend fun getAllMindTexts(): List<MindTextLibEntity> {
        return mindTextDao.getAll()
    }

    suspend fun clearAllConsumptionData() {
        goodsTagDao.deleteAll()
        mindTextDao.deleteAll()
    }
}
