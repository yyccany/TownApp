package com.example.townapp.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.townapp.ConsumptionSeedData
import com.example.townapp.SeedData
import com.example.townapp.data.database.dao.*
import com.example.townapp.data.database.entity.*

@Database(
    entities = [
        UserEntity::class, ActionEntity::class, BuildingEntity::class,
        NpcEntity::class, EventHistoryEntity::class, ResidentEntity::class,
        FeedingRecordEntity::class, FoodNutritionEntity::class, FoodRiskEntity::class,
        UserBodyState::class,
        /** 全重构新增：成品+原料+配方+语录+配置+缓存+事件日志 */
        MaterialEntity::class, ProductEntity::class, FormulaEntity::class,
        QuoteEntity::class, AppConfigEntity::class, QuoteCacheEntity::class, LifeEventLogEntity::class,
        /** 人生存档 */
        LifeArchiveEntity::class,
        /** 国际化食谱系统 */
        RecipeEntity::class, RecipeIngredientEntity::class,
        /** 万物薪俸小镇 v2：身体-空间-精神 三层闭环系统 */
        UserSpaceState::class, UserMentalState::class,
        /** v1.4 新增：人生轨迹归档 + 夜间状态 */
        NpcLifeRecordEntity::class, NightStateEntity::class,
        /** v1.5 消费系统：商品标签 + 精神文案库 */
        GoodsConsumptionTagEntity::class, MindTextLibEntity::class,
        /** v2.0 消费系统深化：用户消费状态 + 消费抉择事件 */
        UserConsumptionStateEntity::class, ConsumptionChoiceEventEntity::class
    ],
    version = 14,
    exportSchema = true
)
abstract class TownDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun actionDao(): ActionDao
    abstract fun buildingDao(): BuildingDao
    abstract fun npcDao(): NpcDao
    abstract fun eventHistoryDao(): EventHistoryDao
    abstract fun residentDao(): ResidentDao
    abstract fun feedingRecordDao(): FeedingRecordDao
    abstract fun foodNutritionDao(): FoodNutritionDao
    abstract fun foodRiskDao(): FoodRiskDao
    abstract fun userBodyStateDao(): UserBodyStateDao

    /** 全重构新增 DAO */
    abstract fun materialDao(): MaterialDao
    abstract fun productDao(): ProductDao
    abstract fun formulaDao(): FormulaDao
    abstract fun quoteDao(): QuoteDao
    abstract fun appConfigDao(): AppConfigDao
    abstract fun quoteCacheDao(): QuoteCacheDao
    abstract fun lifeEventLogDao(): LifeEventLogDao

    /** 人生存档 DAO */
    abstract fun lifeArchiveDao(): LifeArchiveDao

    /** 国际化食谱系统 DAO */
    abstract fun recipeDao(): RecipeDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao

    /** 万物薪俸小镇 v2：身体-空间-精神 三层闭环系统 DAO */
    abstract fun userSpaceStateDao(): UserSpaceStateDao
    abstract fun userMentalStateDao(): UserMentalStateDao

    /** v1.4 新增 DAO：人生轨迹归档 + 夜间状态 */
    abstract fun npcLifeRecordDao(): NpcLifeRecordDao
    abstract fun nightStateDao(): NightStateDao

    /** v1.5 消费系统 DAO：商品标签 + 精神文案库 */
    abstract fun goodsConsumptionTagDao(): GoodsConsumptionTagDao
    abstract fun mindTextLibDao(): MindTextLibDao

    /** v2.0 消费系统深化 DAO：用户消费状态 + 消费抉择事件 */
    abstract fun userConsumptionStateDao(): UserConsumptionStateDao
    abstract fun consumptionChoiceEventDao(): ConsumptionChoiceEventDao

    companion object {
        @Volatile
        private var INSTANCE: TownDatabase? = null

        fun getDatabase(context: Context): TownDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, TownDatabase::class.java, "town_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val pendingInstance = INSTANCE
                            if (pendingInstance != null) {
                                kotlin.concurrent.thread {
                                    try {
                                        val seeds = SeedData.all()
                                        val consumptionSeeds = ConsumptionSeedData
                                        kotlinx.coroutines.runBlocking {
                                            pendingInstance.foodNutritionDao().insertAll(seeds.first)
                                            pendingInstance.foodRiskDao().insertAll(seeds.second)
                                            pendingInstance.goodsConsumptionTagDao().insertAll(consumptionSeeds.goodsTags())
                                            pendingInstance.mindTextLibDao().insertAll(consumptionSeeds.mindTexts())
                                            pendingInstance.quoteDao().insertAll(consumptionSeeds.quotes())
                                            // 初始化用户消费状态（默认值50分，中立状态）
                                            pendingInstance.userConsumptionStateDao().insert(
                                                com.example.townapp.data.database.entity.UserConsumptionStateEntity()
                                            )
                                        }
                                        Log.d("ROOM_DB_INIT", "SeedData插入完成：营养${seeds.first.size}条，风险${seeds.second.size}条，消费标签${consumptionSeeds.goodsTags().size}条，独白文案${consumptionSeeds.mindTexts().size}条，NPC对话${consumptionSeeds.quotes().size}条")
                                    } catch (e: Exception) {
                                        Log.e("ROOM_DB_INIT", "SeedData插入失败", e)
                                    }
                                }
                            }
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun clearInstance() {
            INSTANCE = null
        }
    }
}