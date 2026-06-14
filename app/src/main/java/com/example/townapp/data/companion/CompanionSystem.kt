package com.example.townapp.data.companion

import android.content.Context
import com.example.townapp.core.constants.SimulationConstants
import com.example.townapp.data.database.dao.CompanionCharacterDao
import com.example.townapp.data.database.dao.CompanionEmotionQuoteDao
import com.example.townapp.data.database.dao.CompanionSceneQuoteDao
import com.example.townapp.data.database.entity.CuteCharacterEntity
import com.example.townapp.data.database.entity.CuteEmotionQuoteEntity
import com.example.townapp.data.database.entity.CuteSceneQuoteEntity
import com.example.townapp.business.StructuredLogger
import com.example.townapp.security.AuditLogger
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

/**
 * 🐱 角色陪伴系统核心类
 * 负责管理三个角色的数据、情绪、场景触发等核心逻辑
 * 集成：生日系统、周年纪念日系统、随机小惊喜
 */
class CompanionSystem(
    private val characterDao: CompanionCharacterDao,
    private val emotionQuoteDao: CompanionEmotionQuoteDao,
    private val sceneQuoteDao: CompanionSceneQuoteDao
) {
    private val TAG = "CompanionSystem"
    
    // 懒加载的子模块
    private var surpriseManager: SurpriseManager? = null
    private var anniversaryTracker: AnniversaryTracker? = null
    
    /**
     * 初始化所有子模块
     * 必须在 initialize() 之后或之前调用
     */
    fun setupContext(context: Context) {
        anniversaryTracker = AnniversaryTracker()
        surpriseManager = SurpriseManager(characterDao, sceneQuoteDao)
    }
    
    // ============================================
    // 初始化与数据管理
    // ============================================
    
    suspend fun initialize() {
        StructuredLogger.i(TAG, "初始化角色陪伴系统...")
        
        // 检查是否需要初始化数据
        if (characterDao.count() == 0) {
            StructuredLogger.i(TAG, "首次运行，初始化角色数据...")
            val seeds = CompanionSeedData.getAllSeeds()
            characterDao.insertAllCharacters(seeds.first)
            emotionQuoteDao.insertAllQuotes(seeds.second)
            sceneQuoteDao.insertAllQuotes(seeds.third)
            StructuredLogger.i(TAG, "角色数据初始化完成！共 ${seeds.first.size} 个角色，${seeds.second.size} 条情绪语录，${seeds.third.size} 条场景语录")
        } else {
            StructuredLogger.i(TAG, "角色数据已存在，跳过初始化")
        }
    }
    
    // ============================================
    // 启动时显示的祝福（生日+周年+惊喜）
    // ============================================
    
    /**
     * 获取启动时显示的祝福
     * 这是用户每次打开APP都会调用的核心方法
     */
    suspend fun getStartupBlessings(): List<SurpriseManager.CharacterBlessing> {
        val tracker = anniversaryTracker ?: return emptyList()
        val manager = surpriseManager ?: return emptyList()
        
        return manager.getAllBlessings(tracker)
    }
    
    /**
     * 强制触发一次随机惊喜（用于演示或测试）
     */
    suspend fun forceTriggerSurprise(): SurpriseManager.CharacterBlessing? {
        val manager = surpriseManager ?: return null
        val characters = characterDao.getDefaultCharacters()
        if (characters.isEmpty()) return null
        
        val randomCharacter = characters.random()
        val quote = sceneQuoteDao.getRandomQuoteByScene(
            randomCharacter.characterId,
            CuteSceneQuoteEntity.SCENE_RANDOM_SURPRISE
        ) ?: return null
        
        return SurpriseManager.CharacterBlessing(
            characterId = randomCharacter.characterId,
            characterName = randomCharacter.characterName,
            characterEmoji = randomCharacter.characterEmoji,
            quoteText = quote.quoteText,
            emoji = quote.emoji,
            type = SurpriseManager.BlessingType.SURPRISE
        )
    }
    
    /**
     * 获取小镇陪伴信息（周年纪念日数据）
     */
    fun getAnniversaryInfo(): AnniversaryTracker.AnniversaryInfo? {
        return anniversaryTracker?.getAnniversaryInfo()
    }
    
    // ============================================
    // 角色信息查询
    // ============================================
    
    fun getAllCharacters(): Flow<List<CuteCharacterEntity>> = characterDao.getAllCharacters()
    
    suspend fun getCharacterById(id: Int): CuteCharacterEntity? = characterDao.getCharacterById(id)
    
    suspend fun getCharacterByMood(mood: Int): CuteCharacterEntity? {
        val characters = characterDao.getDefaultCharacters()
        return when {
            mood >= CuteCharacterEntity.MOOD_HAPPY -> characters.find { it.personalityType == "active" }
            mood <= CuteCharacterEntity.MOOD_WORRIED -> characters.find { it.personalityType == "gentle" }
            else -> characters.randomOrNull()
        }
    }
    
    // ============================================
    // 语录生成
    // ============================================
    
    /**
     * 根据角色ID和情绪类型获取随机语录
     */
    suspend fun getEmotionQuote(characterId: Int, emotionType: String): CuteEmotionQuoteEntity? {
        return emotionQuoteDao.getRandomQuoteByEmotion(characterId, emotionType)
    }
    
    /**
     * 根据角色ID获取随机语录
     */
    suspend fun getRandomEmotionQuote(characterId: Int): CuteEmotionQuoteEntity? {
        return emotionQuoteDao.getRandomQuote(characterId)
    }
    
    /**
     * 根据角色ID和场景类型获取随机语录
     */
    suspend fun getSceneQuote(characterId: Int, sceneType: String): CuteSceneQuoteEntity? {
        return sceneQuoteDao.getRandomQuoteByScene(characterId, sceneType)
    }
    
    /**
     * 获取所有角色的场景语录（按优先级）
     */
    suspend fun getSceneQuotesByType(sceneType: String): List<CuteSceneQuoteEntity> {
        return sceneQuoteDao.getQuotesBySceneType(sceneType)
    }
    
    // ============================================
    // 问候语生成
    // ============================================
    
    /**
     * 获取角色问候语
     */
    suspend fun getGreeting(characterId: Int): String? {
        val character = characterDao.getCharacterById(characterId) ?: return null
        val timeOfDay = getTimeOfDay()
        
        return when (timeOfDay) {
            TimeOfDay.MIDNIGHT -> character.greetingMidnight
            TimeOfDay.MORNING -> character.greetingMorning
            TimeOfDay.AFTERNOON -> character.greetingAfternoon
            TimeOfDay.NIGHT -> character.greetingNight
        }
    }
    
    /**
     * 获取当前时间的问候语（跨所有角色）
     */
    suspend fun getGreetings(): List<Pair<CuteCharacterEntity, String>> {
        val characters = characterDao.getDefaultCharacters()
        val timeOfDay = getTimeOfDay()
        
        return characters.mapNotNull { character ->
            val greeting = when (timeOfDay) {
                TimeOfDay.MIDNIGHT -> character.greetingMidnight
                TimeOfDay.MORNING -> character.greetingMorning
                TimeOfDay.AFTERNOON -> character.greetingAfternoon
                TimeOfDay.NIGHT -> character.greetingNight
            }
            character to greeting
        }
    }
    
    /**
     * 获取久未回归的问候
     */
    suspend fun getReturnGreeting(characterId: Int): String? {
        return characterDao.getCharacterById(characterId)?.greetingReturn
    }
    
    // ============================================
    // 心情系统
    // ============================================
    
    /**
     * 更新角色心情
     */
    suspend fun updateMood(characterId: Int, moodChange: Int) {
        val character = characterDao.getCharacterById(characterId) ?: return
        val newMood = (character.currentMood + moodChange).coerceIn(0, 100)
        characterDao.updateMood(characterId, newMood)
        StructuredLogger.d(TAG, "角色 $characterId 心情变化: ${character.currentMood} -> $newMood")
    }
    
    /**
     * 更新角色精力
     */
    suspend fun updateEnergy(characterId: Int, energyChange: Int) {
        val character = characterDao.getCharacterById(characterId) ?: return
        val newEnergy = (character.currentEnergy + energyChange).coerceIn(0, 100)
        characterDao.updateEnergy(characterId, newEnergy)
        StructuredLogger.d(TAG, "角色 $characterId 精力变化: ${character.currentEnergy} -> $newEnergy")
    }
    
    /**
     * 记录互动时间
     */
    suspend fun recordInteraction(characterId: Int) {
        characterDao.updateLastInteraction(characterId, System.currentTimeMillis())
    }
    
    /**
     * 恢复所有角色精力（每天凌晨调用）
     */
    suspend fun restoreAllEnergy() {
        val characters = characterDao.getDefaultCharacters()
        characters.forEach { character ->
            val restoredEnergy = minOf(character.currentEnergy + 30, 100)
            characterDao.updateEnergy(character.characterId, restoredEnergy)
            
            // 心情也会稍微恢复
            val restoredMood = minOf(character.currentMood + 5, 100)
            characterDao.updateMood(character.characterId, restoredMood)
        }
        StructuredLogger.i(TAG, "已恢复所有角色精力")
    }
    
    /**
     * 检查并处理久未登录的角色
     */
    suspend fun checkAbsentCharacters(lastLoginTime: Long): List<Pair<CuteCharacterEntity, String>> {
        val absentCharacters = mutableListOf<Pair<CuteCharacterEntity, String>>()
        val characters = characterDao.getDefaultCharacters()
        val absentTime = System.currentTimeMillis() - lastLoginTime
        
        val daysAbsent = absentTime / SimulationConstants.MILLIS_PER_DAY
        
        characters.forEach { character ->
            val greeting = when {
                daysAbsent >= 30 -> character.greetingReturn
                daysAbsent >= 7 -> character.greetingReturn
                else -> null
            }
            greeting?.let { absentCharacters.add(character to it) }
        }
        
        return absentCharacters
    }
    
    // ============================================
    // 辅助方法
    // ============================================
    
    enum class TimeOfDay {
        MIDNIGHT,    // 0-5点
        MORNING,     // 6-11点
        AFTERNOON,   // 12-17点
        NIGHT        // 18-23点
    }
    
    private fun getTimeOfDay(): TimeOfDay {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 0..5 -> TimeOfDay.MIDNIGHT
            in 6..11 -> TimeOfDay.MORNING
            in 12..17 -> TimeOfDay.AFTERNOON
            else -> TimeOfDay.NIGHT
        }
    }
    
    /**
     * 获取当前是深夜还是普通时间
     */
    fun isLateNight(): Boolean {
        return getTimeOfDay() == TimeOfDay.MIDNIGHT || getTimeOfDay() == TimeOfDay.NIGHT
    }
}
