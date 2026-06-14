package com.example.townapp.data.database.dao

import androidx.room.*
import com.example.townapp.data.database.entity.BuildingEntity

@Dao
interface BuildingDao {
    @Query("SELECT * FROM buildings")
    suspend fun getAllBuildings(): List<BuildingEntity>

    @Query("SELECT * FROM buildings WHERE category = :category")
    suspend fun getBuildingsByCategory(category: String): List<BuildingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuilding(building: BuildingEntity)

    @Update
    suspend fun updateBuilding(building: BuildingEntity)

    @Query("SELECT COALESCE(MAX(level), 0) FROM buildings WHERE buildingId = :buildingId")
    suspend fun getMaxLevel(buildingId: String): Int

    @Query("SELECT * FROM buildings WHERE buildingId = :buildingId")
    suspend fun getBuildingByBuildingId(buildingId: String): BuildingEntity?

    @Query("UPDATE buildings SET daysWithoutTrigger = 0 WHERE buildingId = :buildingId")
    suspend fun resetDaysWithoutTrigger(buildingId: String)

    @Query("UPDATE buildings SET level = :newLevel WHERE id = :id")
    suspend fun upgradeBuilding(id: Int, newLevel: Int)

    @Query("UPDATE buildings SET daysWithoutTrigger = daysWithoutTrigger + 1")
    suspend fun incrementDaysWithoutTrigger()

    @Query("SELECT * FROM buildings WHERE isActive = 1")
    suspend fun getActiveBuildings(): List<BuildingEntity>

    @Query("UPDATE buildings SET isActive = 0 WHERE id = :id")
    suspend fun deactivateBuilding(id: Int)
}