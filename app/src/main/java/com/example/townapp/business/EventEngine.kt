package com.example.townapp.business

import com.example.townapp.data.database.dao.LifeEventLogDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class EventEngine(
    private val lifeEventLogDao: LifeEventLogDao,
    private var frequency: Int = 2
) {
    private val events = listOf(
        LifeEvent("mosquito_bite", "被蚊子咬了", "胳膊上起了一个小红包", 1.0),
        LifeEvent("scratch", "不小心磕碰", "膝盖有点擦伤", 0.8),
        LifeEvent("toy_part_lost", "玩具零件丢失", "乐高积木少了一块", 0.6),
        LifeEvent("doll_shedding", "玩偶掉毛", "毛绒玩具掉了一些毛", 0.5),
        LifeEvent("pet_scratch", "宠物抓伤", "被小猫轻轻抓了一下", 0.4),
        LifeEvent("find_object", "路边拾物", "发现了一个有趣的小物件", 0.7),
        LifeEvent("bead_moisture", "手串受潮", "木质手串有点受潮", 0.3),
        LifeEvent("food_spoil", "食物轻微变质", "面包有点干硬", 0.6),
        LifeEvent("clothes_stain", "衣物沾污", "衣服不小心沾了污渍", 0.7),
        LifeEvent("stationery_lost", "文具丢失", "找不到那支常用的笔了", 0.8)
    )

    suspend fun triggerRandomEvent(): LifeEvent? {
        return withContext(Dispatchers.IO) {
            val adjustedProbability = frequency * 0.3
            if (Random.nextDouble() > adjustedProbability) {
                return@withContext null
            }

            val weightedEvent = events.let {
                val totalWeight = it.sumOf { e -> e.weight }
                var random = Random.nextDouble() * totalWeight
                for (event in it) {
                    random -= event.weight
                    if (random <= 0) {
                        return@let event
                    }
                }
                it.first()
            }

            lifeEventLogDao.insert(
                com.example.townapp.data.database.entity.LifeEventLogEntity(
                    eventType = weightedEvent.id
                )
            )
            weightedEvent
        }
    }

    suspend fun getEventStats(): Map<String, Int> {
        return withContext(Dispatchers.IO) {
            lifeEventLogDao.getEventStats().associate { it.eventType to it.count }
        }
    }

    fun setFrequency(frequency: Int) {
        this.frequency = frequency.coerceIn(1, 3)
    }

    data class LifeEvent(
        val id: String,
        val title: String,
        val description: String,
        val weight: Double
    )
}