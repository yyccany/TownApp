package com.example.townapp.feature.town_simulation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.townapp.domain.model.simulation.FoodDisplayVo
import com.example.townapp.domain.usecase.simulation_case.GetRawFoodNutritionUseCase
import com.example.townapp.feature.town_simulation.NutritionRiskCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 食材营养分析页面 ViewModel
 * 遵循实事求是：
 * 1. MutableStateFlow 存储页面数据
 * 2. 更新时新建集合赋值
 * 3. 通过 UseCase 获取数据，日志追踪条数
 */
class NutritionAnalysisViewModel : ViewModel() {

    private val getRawFoodNutritionUseCase = GetRawFoodNutritionUseCase()

    // ================== 状态输出 ==================
    private val _foodList = MutableStateFlow<List<FoodDisplayVo>>(emptyList())
    val foodList: StateFlow<List<FoodDisplayVo>> = _foodList.asStateFlow()

    private val _selectedFood = MutableStateFlow<FoodDisplayVo?>(null)
    val selectedFood: StateFlow<FoodDisplayVo?> = _selectedFood.asStateFlow()

    private val _currentCategory = MutableStateFlow<String?>(null)
    val currentCategory: StateFlow<String?> = _currentCategory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ================== 数据加载 ==================
    fun loadAllFood() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val voList = getRawFoodNutritionUseCase.execute()
                _foodList.value = voList.toList()
                Log.d("NUTRITION_VM", "推送UI数据：${voList.size}条")
            } catch (e: Exception) {
                Log.e("NUTRITION_VM", "加载食材数据失败", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                _currentCategory.value = category
                val voList = getRawFoodNutritionUseCase.getByCategory(category)
                _foodList.value = voList.toList()
                Log.d("NUTRITION_VM", "分类${category}数据：${voList.size}条")
            } catch (e: Exception) {
                Log.e("NUTRITION_VM", "加载分类数据失败", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadByRiskLevel(level: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                _currentCategory.value = when (level) {
                    "LOW" -> "低风险"
                    "MEDIUM" -> "中风险"
                    "HIGH" -> "高风险"
                    "EXTREME" -> "极高风险"
                    else -> level
                }
                val voList = getRawFoodNutritionUseCase.getByRiskLevel(level)
                _foodList.value = voList.toList()
                Log.d("NUTRITION_VM", "风险等级${level}数据：${voList.size}条")
            } catch (e: Exception) {
                Log.e("NUTRITION_VM", "加载风险数据失败", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadIQTaxFoods() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                _currentCategory.value = "智商税"
                val voList = getRawFoodNutritionUseCase.getIQTaxFoods()
                _foodList.value = voList.toList()
                Log.d("NUTRITION_VM", "智商税食品数据：${voList.size}条")
            } catch (e: Exception) {
                Log.e("NUTRITION_VM", "加载智商税数据失败", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isBlank()) {
                loadAllFood()
                return@launch
            }
            try {
                val voList = getRawFoodNutritionUseCase.search(query)
                _foodList.value = voList.toList()
                Log.d("NUTRITION_VM", "搜索${query}结果：${voList.size}条")
            } catch (e: Exception) {
                Log.e("NUTRITION_VM", "搜索失败", e)
            }
        }
    }

    // ================== 交互操作 ==================
    fun selectFood(food: FoodDisplayVo) {
        _selectedFood.value = food
        Log.d("NUTRITION_VM", "选中食材：${food.foodName}")
    }

    fun clearSelection() {
        _selectedFood.value = null
    }

    fun clearCategory() {
        _currentCategory.value = null
        loadAllFood()
    }

    // ================== 缓存初始化状态 ==================
    fun isCacheReady(): Boolean = NutritionRiskCache.size() > 0
}
