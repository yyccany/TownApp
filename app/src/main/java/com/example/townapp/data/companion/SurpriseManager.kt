package com.example.townapp.data.companion

import com.example.townapp.data.database.dao.CompanionCharacterDao
import com.example.townapp.data.database.dao.CompanionSceneQuoteDao
import com.example.townapp.data.database.entity.CuteCharacterEntity
import com.example.townapp.data.database.entity.CuteSceneQuoteEntity
import com.example.townapp.business.StructuredLogger
import java.util.Calendar
import kotlin.random.Random

/**
 * 🎁 随机小惊喜管理器
 * 负责处理随机触发的角色互动
 * - 1% 概率的"突然出现"机制
 * - 生日当天自动触发
 * - 周年纪念日自动触发
 */
class SurpriseManager(
    private val characterDao: CompanionCharacterDao,
    private val sceneQuoteDao: CompanionSceneQuoteDao
) {
    
    companion object {
        private const val TAG = "SurpriseManager"
        private const val SURPRISE_PROBABILITY = 0.01f  // 1% 概率
    }
    
    /**
     * 检测今天是否某个角色生日
     * @return 生日角色ID列表
     */
    suspend fun checkTodayBirthdays(): List<Int> {
        val today = Calendar.getInstance()
        val month = today.get(Calendar.MONTH) + 1  // 0-indexed to 1-indexed
        val day = today.get(Calendar.DAY_OF_MONTH)
        
        val characters = characterDao.getDefaultCharacters()
        val birthdayCharacters = characters.filter { character ->
            character.birthdayMonth == month && character.birthdayDay == day
        }
        
        if (birthdayCharacters.isNotEmpty()) {
            StructuredLogger.i(TAG, "今天是 ${birthdayCharacters.joinToString { it.characterName }} 的生日！")
        }
        
        return birthdayCharacters.map { it.characterId }
    }
    
    /**
     * 获取今天生日角色的祝福语
     */
    suspend fun getBirthdayBlessings(): List<CharacterBlessing> {
        val birthdayIds = checkTodayBirthdays()
        if (birthdayIds.isEmpty()) return emptyList()
        
        return birthdayIds.mapNotNull { characterId ->
            val quote = sceneQuoteDao.getRandomQuoteByScene(characterId, CuteSceneQuoteEntity.SCENE_BIRTHDAY)
            val character = characterDao.getCharacterById(characterId) ?: return@mapNotNull null
            
            quote?.let {
                CharacterBlessing(
                    characterId = characterId,
                    characterName = character.characterName,
                    characterEmoji = character.characterEmoji,
                    quoteText = it.quoteText,
                    emoji = it.emoji,
                    type = BlessingType.BIRTHDAY
                )
            }
        }
    }
    
    /**
     * 触发随机小惊喜（1% 概率）
     * @return 惊喜内容，如果没有触发则返回null
     */
    suspend fun tryTriggerRandomSurprise(): CharacterBlessing? {
        // 1% 概率触发
        if (Random.nextFloat() > SURPRISE_PROBABILITY) {
            return null
        }
        
        // 随机选择一个角色
        val characters = characterDao.getDefaultCharacters()
        if (characters.isEmpty()) return null
        
        val randomCharacter = characters.random()
        val quote = sceneQuoteDao.getRandomQuoteByScene(
            randomCharacter.characterId,
            CuteSceneQuoteEntity.SCENE_RANDOM_SURPRISE
        ) ?: return null
        
        StructuredLogger.i(TAG, "🎁 触发了 ${randomCharacter.characterName} 的随机小惊喜！")
        
        return CharacterBlessing(
            characterId = randomCharacter.characterId,
            characterName = randomCharacter.characterName,
            characterEmoji = randomCharacter.characterEmoji,
            quoteText = quote.quoteText,
            emoji = quote.emoji,
            type = BlessingType.SURPRISE
        )
    }
    
    /**
     * 获取周年纪念日祝福
     */
    suspend fun getAnniversaryBlessings(anniversaryTracker: AnniversaryTracker): List<CharacterBlessing> {
        if (!anniversaryTracker.isAnniversaryToday()) return emptyList()
        if (anniversaryTracker.hasCelebratedThisYear()) return emptyList()
        
        val characters = characterDao.getDefaultCharacters()
        val blessings = mutableListOf<CharacterBlessing>()
        
        characters.forEach { character ->
            val quote = sceneQuoteDao.getRandomQuoteByScene(
                character.characterId,
                CuteSceneQuoteEntity.SCENE_ANNIVERSARY
            )
            quote?.let {
                blessings.add(
                    CharacterBlessing(
                        characterId = character.characterId,
                        characterName = character.characterName,
                        characterEmoji = character.characterEmoji,
                        quoteText = it.quoteText,
                        emoji = it.emoji,
                        type = BlessingType.ANNIVERSARY
                    )
                )
            }
        }
        
        // 标记已庆祝
        anniversaryTracker.markAnniversaryCelebrated()
        
        StructuredLogger.i(TAG, "💛 小镇周年纪念日！已认识 ${anniversaryTracker.getDaysTogether()} 天")
        return blessings
    }
    
    /**
     * 获取所有待显示的祝福（生日 + 周年 + 随机惊喜）
     * 这是在用户打开APP时调用的核心方法
     */
    suspend fun getAllBlessings(anniversaryTracker: AnniversaryTracker): List<CharacterBlessing> {
        val blessings = mutableListOf<CharacterBlessing>()
        
        // 1. 检查生日（最高优先级）
        blessings.addAll(getBirthdayBlessings())
        
        // 2. 检查周年纪念日
        if (blessings.isEmpty()) {
            blessings.addAll(getAnniversaryBlessings(anniversaryTracker))
        }
        
        // 3. 尝试触发随机惊喜
        if (blessings.isEmpty()) {
            tryTriggerRandomSurprise()?.let { blessings.add(it) }
        }
        
        return blessings
    }
    
    // ============================================
    // 数据类
    // ============================================
    
    enum class BlessingType {
        BIRTHDAY,      // 生日
        ANNIVERSARY,   // 周年
        SURPRISE       // 随机惊喜
    }
    
    data class CharacterBlessing(
        val characterId: Int,
        val characterName: String,
        val characterEmoji: String,
        val quoteText: String,
        val emoji: String,
        val type: BlessingType
    )
}
