package com.example.townapp.feature.food.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.townapp.domain.model.simulation.CuisineFoodVo
import com.example.townapp.domain.usecase.simulation_case.GetCuisineFoodListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 美食赏析页面 ViewModel
 * 严格遵循实事求是：
 * 1. 使用 MutableStateFlow 存储页面数据
 * 2. 更新时新建集合赋值，禁止原地修改
 * 3. 所有数据需求通过 UseCase 获取
 * 4. 日志记录数据条数变更
 */
class FoodAppreciationViewModel : ViewModel() {

    private val getCuisineFoodListUseCase = GetCuisineFoodListUseCase()

    // ================== 状态输出 ==================
    private val _foodList = MutableStateFlow<List<CuisineFoodVo>>(emptyList())
    val foodList: StateFlow<List<CuisineFoodVo>> = _foodList.asStateFlow()

    private val _selectedFood = MutableStateFlow<CuisineFoodVo?>(null)
    val selectedFood: StateFlow<CuisineFoodVo?> = _selectedFood.asStateFlow()

    private val _currentCategory = MutableStateFlow<String?>(null)
    val currentCategory: StateFlow<String?> = _currentCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ================== 数据加载 ==================
    fun loadAllFood() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val voList = getCuisineFoodListUseCase.execute()
                _foodList.value = voList.toList()
                Log.d("FOOD_VM", "推送UI数据：${voList.size}条")
            } catch (e: Exception) {
                Log.e("FOOD_VM", "加载菜肴数据失败", e)
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
                val voList = getCuisineFoodListUseCase.getByCategory(category)
                _foodList.value = voList.toList()
                Log.d("FOOD_VM", "分类${category}数据：${voList.size}条")
            } catch (e: Exception) {
                Log.e("FOOD_VM", "加载分类数据失败", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadHealthyFoods() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                _currentCategory.value = "健康"
                val voList = getCuisineFoodListUseCase.getHealthyFoods()
                _foodList.value = voList.toList()
                Log.d("FOOD_VM", "健康食品数据：${voList.size}条")
            } catch (e: Exception) {
                Log.e("FOOD_VM", "加载健康食品失败", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadRiskyFoods() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                _currentCategory.value = "高风险"
                val voList = getCuisineFoodListUseCase.getRiskyFoods()
                _foodList.value = voList.toList()
                Log.d("FOOD_VM", "高风险食品数据：${voList.size}条")
            } catch (e: Exception) {
                Log.e("FOOD_VM", "加载高风险食品失败", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchQuery.value = query
            if (query.isBlank()) {
                loadAllFood()
                return@launch
            }
            try {
                val voList = getCuisineFoodListUseCase.search(query)
                _foodList.value = voList.toList()
                Log.d("FOOD_VM", "搜索${query}结果：${voList.size}条")
            } catch (e: Exception) {
                Log.e("FOOD_VM", "搜索失败", e)
            }
        }
    }

    // ================== 交互操作 ==================
    fun selectFood(food: CuisineFoodVo) {
        _selectedFood.value = food
        Log.d("FOOD_VM", "选中食品：${food.name}")
    }

    fun clearSelection() {
        _selectedFood.value = null
    }

    fun clearCategory() {
        _currentCategory.value = null
        loadAllFood()
    }
}
