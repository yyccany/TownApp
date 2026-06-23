package com.example.townapp.domain.usecase.simulation_case

import android.util.Log
import com.example.townapp.domain.model.simulation.CuisineFoodVo
import com.example.townapp.feature.food.CuisineFoodRepository

/**
 * 获取菜肴列表用例
 * 遵循实事求是：只从硬编码仓库读取原始数据，转换为展示模型，不加工数据
 */
class GetCuisineFoodListUseCase {

    fun execute(): List<CuisineFoodVo> {
        val rawList = CuisineFoodRepository.getAllFoods()
        Log.d("CUISINE_DB", "菜肴原始数据条数：${rawList.size}")
        val voList = rawList.map { CuisineFoodVo.fromEntity(it) }
        Log.d("CUISINE_VO", "组装后菜肴展示数据条数：${voList.size}")
        return voList
    }

    fun getByCategory(category: String): List<CuisineFoodVo> {
        val rawList = CuisineFoodRepository.getFoodsByCategory(category)
        return rawList.map { CuisineFoodVo.fromEntity(it) }
    }

    fun getHealthyFoods(): List<CuisineFoodVo> {
        val rawList = CuisineFoodRepository.getHealthyFoods()
        return rawList.map { CuisineFoodVo.fromEntity(it) }
    }

    fun getRiskyFoods(): List<CuisineFoodVo> {
        val rawList = CuisineFoodRepository.getRiskyFoods()
        return rawList.map { CuisineFoodVo.fromEntity(it) }
    }

    fun search(query: String): List<CuisineFoodVo> {
        val rawList = CuisineFoodRepository.searchFoods(query)
        return rawList.map { CuisineFoodVo.fromEntity(it) }
    }
}
