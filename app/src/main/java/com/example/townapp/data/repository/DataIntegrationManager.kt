package com.example.townapp.data.repository

import android.util.Log

object DataIntegrationManager {
    
    private const val TAG = "DataIntegrationManager"
    private var initialized = false
    
    fun initialize() {
        if (initialized) return
        initialized = true
        
        Log.d(TAG, "初始化所有数据...")
        
        try {
            // 验证基础数据是否可访问
            val foodCount = com.example.townapp.data.completeFoodItems.size
            Log.d(TAG, "基础食材数量: $foodCount")
            
            val idiomCount = com.example.townapp.data.idiom.IdiomCritiqueLibrary.getAllIdioms().size
            Log.d(TAG, "成语数量: $idiomCount")
            
            val eventCount = GameEventRepository.getAllEvents().size
            Log.d(TAG, "事件数量: $eventCount")
            
            val spaceCount = SpaceConfigRepository.getAllSpaces().size
            Log.d(TAG, "空间数量: $spaceCount")
            
            Log.d(TAG, "数据初始化完成")
        } catch (e: Exception) {
            Log.e(TAG, "数据初始化失败", e)
        }
    }
    
    fun getRandomMicroEvent(): com.example.townapp.data.microevent.MicroEvent? {
        val events = com.example.townapp.data.microevent.MicroEventQuotes.getAllEvents()
        return if (events.isEmpty()) null else events.random()
    }
    
    fun getRandomIdiom(): com.example.townapp.data.idiom.IdiomCritique? {
        val idioms = com.example.townapp.data.idiom.IdiomCritiqueLibrary.getAllIdioms()
        return if (idioms.isEmpty()) null else idioms.random()
    }
    
    fun getRandomCompanion(): String {
        return "今天也要好好照顾自己哦~"
    }
    
    fun getAllVehicles(): List<com.example.townapp.data.Vehicle> {
        return com.example.townapp.data.getVehicles()
    }
    
    fun getAllClothingCategories(): List<com.example.townapp.data.Category> {
        return com.example.townapp.data.getAllCategories()
    }
    
    fun getRandomDissection(): Dissection? {
        return Dissection("认知剖析", "深入思考你的生活状态")
    }
    
    fun getRandomCognitiveCard(): CognitiveCard? {
        return CognitiveCard("认知卡片", "学习新的思维方式")
    }
    
    fun getRandomScenario(): Scenario? {
        return Scenario("模拟场景", "体验不同的生活情境")
    }
    
    fun getSpotlightById(id: String): Spotlight? {
        return Spotlight("闪光点", "发现生活中的美好")
    }
    
    data class Dissection(val title: String, val content: String)
    data class CognitiveCard(val title: String, val content: String)
    data class Scenario(val name: String, val description: String)
    data class Spotlight(val title: String, val description: String)
}
