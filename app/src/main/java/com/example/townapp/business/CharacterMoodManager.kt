package com.example.townapp.business

import com.example.townapp.data.HobbyConfigs
import com.example.townapp.data.database.dao.CompanionCharacterDao
import com.example.townapp.data.database.dao.CompanionEmotionQuoteDao
import com.example.townapp.data.database.dao.CompanionSceneQuoteDao
import com.example.townapp.data.database.entity.CuteCharacterEntity
import com.example.townapp.data.database.entity.CuteSceneQuoteEntity
import kotlinx.coroutines.*

/**
 * 🐱 角色心情管理系统
 * 负责管理三个角色的心情值、精力值，以及触发相应的场景反馈
 */
class CharacterMoodManager(
    private val characterDao: CompanionCharacterDao,
    private val emotionQuoteDao: CompanionEmotionQuoteDao,
    private val sceneQuoteDao: CompanionSceneQuoteDao
) {

    private val moodScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    
    /**
     * 清理协程，防止内存泄漏
     */
    fun clear() {
        moodScope.cancel()
    }

    // ============================================
    // 心情更新 - 用户行为触发
    // ============================================
    
    /**
     * 用户记录行为后，更新相关角色的心情
     * @return 触发的话语（如果有）
     */
    suspend fun onUserAction(
        actionType: String,
        actionSubType: String,
        amount: Double
    ): CharacterReaction {
        val sceneType = mapActionToScene(actionType, actionSubType)
        
        // 获取所有角色
        val characters = characterDao.getDefaultCharacters()
        
        val reactions = mutableListOf<SingleCharacterReaction>()
        
        characters.forEach { character ->
            // 根据角色性格调整心情变化
            val moodChange = calculateMoodChange(character, sceneType, amount)
            val energyChange = calculateEnergyChange(character, sceneType)
            
            // 更新角色状态
            characterDao.updateMood(character.characterId, (character.currentMood + moodChange).coerceIn(0, 100))
            characterDao.updateEnergy(character.characterId, (character.currentEnergy + energyChange).coerceIn(0, 100))
            characterDao.updateLastInteraction(character.characterId, System.currentTimeMillis())
            
            // 获取角色的反应语录
            val sceneQuote = sceneQuoteDao.getRandomQuoteByScene(character.characterId, sceneType)
            val emotionQuote = emotionQuoteDao.getRandomQuoteByEmotion(character.characterId, getEmotionByMood(character.currentMood + moodChange))
            
            val quoteText = sceneQuote?.quoteText ?: emotionQuote?.quoteText
            val quoteEmoji = sceneQuote?.emoji ?: emotionQuote?.emoji
            
            if (quoteText != null) {
                reactions.add(
                    SingleCharacterReaction(
                        characterId = character.characterId,
                        characterName = character.characterName,
                        characterEmoji = character.characterEmoji,
                        quoteText = quoteText,
                        emoji = quoteEmoji ?: "",
                        moodChange = moodChange
                    )
                )
            }
        }
        
        return CharacterReaction(
            reactions = reactions,
            sceneType = sceneType
        )
    }
    
    /**
     * 获取角色问候
     */
    suspend fun getGreetings(): List<SingleCharacterReaction> {
        val characters = characterDao.getDefaultCharacters()
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        
        return characters.mapNotNull { character ->
            val greeting = when (hour) {
                in 0..5 -> character.greetingMidnight
                in 6..11 -> character.greetingMorning
                in 12..17 -> character.greetingAfternoon
                else -> character.greetingNight
            }
            
            SingleCharacterReaction(
                characterId = character.characterId,
                characterName = character.characterName,
                characterEmoji = character.characterEmoji,
                quoteText = greeting,
                emoji = getMoodEmoji(character.currentMood),
                moodChange = 0
            )
        }
    }
    
    /**
     * 检查久未登录的角色
     */
    suspend fun checkAbsentCharacters(lastLoginTime: Long): List<SingleCharacterReaction> {
        val absentDays = (System.currentTimeMillis() - lastLoginTime) / (24 * 60 * 60 * 1000)
        
        if (absentDays < 7) return emptyList()
        
        val characters = characterDao.getDefaultCharacters()
        val sceneType = if (absentDays >= 30) CuteSceneQuoteEntity.SCENE_LONG_ABSENT_30 else CuteSceneQuoteEntity.SCENE_LONG_ABSENT_7
        
        return characters.mapNotNull { character ->
            val sceneQuote = sceneQuoteDao.getRandomQuoteByScene(character.characterId, sceneType)
            val greetingReturn = characterDao.getCharacterById(character.characterId)?.greetingReturn
            
            val quoteText = sceneQuote?.quoteText ?: greetingReturn
            val quoteEmoji = sceneQuote?.emoji ?: getMoodEmoji(character.currentMood)
            
            if (quoteText != null) {
                SingleCharacterReaction(
                    characterId = character.characterId,
                    characterName = character.characterName,
                    characterEmoji = character.characterEmoji,
                    quoteText = quoteText,
                    emoji = quoteEmoji,
                    moodChange = 0
                )
            } else {
                null
            }
        }
    }
    
    /**
     * 每日凌晨恢复所有角色精力
     */
    suspend fun restoreDailyEnergy() {
        val characters = characterDao.getDefaultCharacters()
        characters.forEach { character ->
            val newEnergy = minOf(character.currentEnergy + 30, 100)
            val newMood = minOf(character.currentMood + 5, 100)
            characterDao.updateEnergy(character.characterId, newEnergy)
            characterDao.updateMood(character.characterId, newMood)
        }
        StructuredLogger.i("CharacterMoodManager", "已每日恢复所有角色精力和心情")
    }
    
    /**
     * 获取当前所有角色的心情状态
     */
    suspend fun getAllCharacterStatus(): List<CharacterStatus> {
        return characterDao.getDefaultCharacters().map { character ->
            CharacterStatus(
                characterId = character.characterId,
                characterName = character.characterName,
                characterEmoji = character.characterEmoji,
                mood = character.currentMood,
                energy = character.currentEnergy,
                personalityType = character.personalityType
            )
        }
    }

    // ============================================
    // 辅助方法
    // ============================================
    
    private fun mapActionToScene(actionType: String, actionSubType: String): String {
        // 根据行为类型映射到场景
        return when (actionType) {
            "food" -> {
                when {
                    actionSubType.contains("奶茶") || actionSubType.contains("奶茶") -> CuteSceneQuoteEntity.SCENE_DRINK_MILKTEA
                    actionSubType.contains("垃圾食品") || actionSubType.contains("快餐") -> CuteSceneQuoteEntity.SCENE_FOOD_JUNK
                    else -> CuteSceneQuoteEntity.SCENE_FOOD_JUNK
                }
            }
            "drink" -> {
                when {
                    actionSubType.contains("水") -> CuteSceneQuoteEntity.SCENE_DRINK_WATER
                    actionSubType.contains("奶茶") -> CuteSceneQuoteEntity.SCENE_DRINK_MILKTEA
                    else -> CuteSceneQuoteEntity.SCENE_DRINK_WATER
                }
            }
            "medicine" -> CuteSceneQuoteEntity.SCENE_MEDICINE
            "clothing" -> CuteSceneQuoteEntity.SCENE_CLOTHING_BAD
            "scam" -> CuteSceneQuoteEntity.SCENE_SCAMPERED
            else -> CuteSceneQuoteEntity.SCENE_VALUE_LOW
        }
    }
    
    private fun calculateMoodChange(character: CuteCharacterEntity, sceneType: String, amount: Double): Int {
        // 根据角色性格计算心情变化
        val baseChange = when (sceneType) {
            CuteSceneQuoteEntity.SCENE_DRINK_MILKTEA -> 15
            CuteSceneQuoteEntity.SCENE_FOOD_JUNK -> -5
            CuteSceneQuoteEntity.SCENE_MEDICINE -> -5
            CuteSceneQuoteEntity.SCENE_SCAMPERED -> -10
            else -> 0
        }
        
        // 根据性格调整
        return when (character.personalityType) {
            "active" -> { // 塔菲喵 - 活泼，对好事更开心
                when (sceneType) {
                    CuteSceneQuoteEntity.SCENE_DRINK_MILKTEA -> baseChange + 5
                    CuteSceneQuoteEntity.SCENE_MEDICINE -> baseChange - 3
                    else -> baseChange
                }
            }
            "gentle" -> { // doro - 温柔，对坏事更担心
                when (sceneType) {
                    CuteSceneQuoteEntity.SCENE_FOOD_JUNK -> baseChange - 3
                    CuteSceneQuoteEntity.SCENE_MEDICINE -> baseChange + 3 // 担心但也欣慰
                    else -> baseChange
                }
            }
            "envious" -> { // 咕咕嘎嘎 - 羡慕，对吃的特别敏感
                when (sceneType) {
                    CuteSceneQuoteEntity.SCENE_DRINK_MILKTEA -> baseChange + 8
                    CuteSceneQuoteEntity.SCENE_FOOD_JUNK -> baseChange + 5 // 其实很开心
                    CuteSceneQuoteEntity.SCENE_SCAMPERED -> baseChange - 5
                    else -> baseChange
                }
            }
            else -> baseChange
        }
    }
    
    private fun calculateEnergyChange(character: CuteCharacterEntity, sceneType: String): Int {
        // 深夜行为会消耗精力
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return if (hour in 0..5) {
            when (character.personalityType) {
                "active" -> -10 // 塔菲喵熬夜特别累
                "gentle" -> -5  // doro比较能熬夜
                "envious" -> -8 // 咕咕嘎嘎
                else -> -5
            }
        } else {
            0
        }
    }
    
    private fun getEmotionByMood(mood: Int): String {
        return when {
            mood >= 70 -> "happy"
            mood >= 40 -> "worried"
            mood >= 20 -> "sad"
            else -> "sad"
        }
    }
    
    private fun getMoodEmoji(mood: Int): String {
        return when {
            mood >= 70 -> "😊"
            mood >= 40 -> "😐"
            mood >= 20 -> "😟"
            else -> "😢"
        }
    }

    // ============================================
    // 数据类
    // ============================================
    
    data class CharacterReaction(
        val reactions: List<SingleCharacterReaction>,
        val sceneType: String
    )
    
    data class SingleCharacterReaction(
        val characterId: Int,
        val characterName: String,
        val characterEmoji: String,
        val quoteText: String,
        val emoji: String,
        val moodChange: Int
    )
    
    data class CharacterStatus(
        val characterId: Int,
        val characterName: String,
        val characterEmoji: String,
        val mood: Int,
        val energy: Int,
        val personalityType: String
    )
}
