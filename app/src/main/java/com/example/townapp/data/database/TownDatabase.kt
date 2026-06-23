package com.example.townapp.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
        UserSpaceState::class, UserMentalState::class
    ],
    version = 11,
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
                            // 数据库表创建完成后，异步执行种子数据插入
                            val pendingInstance = INSTANCE
                            if (pendingInstance != null) {
                                kotlin.concurrent.thread {
                                    try {
                                        val seeds = SeedData.all()
                                        kotlinx.coroutines.runBlocking {
                                            pendingInstance.foodNutritionDao().insertAll(seeds.first)
                                            pendingInstance.foodRiskDao().insertAll(seeds.second)
                                        }
                                        Log.d("ROOM_DB_INIT", "SeedData插入完成：营养${seeds.first.size}条，风险${seeds.second.size}条")
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