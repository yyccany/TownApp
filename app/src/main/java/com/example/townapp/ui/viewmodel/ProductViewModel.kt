package com.example.townapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.townapp.feature.town_simulation.CalculationEngine
import com.example.townapp.data.database.TownDatabase
import com.example.townapp.data.database.entity.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(private val database: TownDatabase) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductEntity>>(emptyList())
    val products: StateFlow<List<ProductEntity>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedProduct = MutableStateFlow<ProductEntity?>(null)
    val selectedProduct: StateFlow<ProductEntity?> = _selectedProduct.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val calculationEngine = CalculationEngine(
        database.materialDao(),
        database.formulaDao()
    )

    fun loadProducts(isChildMode: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = withContext(Dispatchers.IO) {
                    if (isChildMode) {
                        database.productDao().getAllForChild()
                    } else {
                        database.productDao().getAllForAdult()
                    }
                }
                _products.value = result.sortedBy { it.name.lowercase() }
            } catch (e: Exception) {
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
        loadCategories(isChildMode)
    }

    fun loadCategories(isChildMode: Boolean) {
        viewModelScope.launch {
            val allCategories = withContext(Dispatchers.IO) {
                database.productDao().getAllCategories()
            }
            val filtered = if (isChildMode) {
                allCategories.filterNot { it in listOf("文玩嗜好", "烟酒") }
            } else {
                allCategories
            }
            _categories.value = filtered
        }
    }

    fun filterByCategory(category: String?, isChildMode: Boolean) {
        _selectedCategory.value = category
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                if (category == null) {
                    if (isChildMode) {
                        database.productDao().getAllForChild()
                    } else {
                        database.productDao().getAllForAdult()
                    }
                } else {
                    if (isChildMode) {
                        database.productDao().getByCategoryForChild(category)
                    } else {
                        database.productDao().getByCategoryForAdult(category)
                    }
                }
            }
            _products.value = result.sortedBy { it.name.lowercase() }
        }
    }

    fun search(query: String, isChildMode: Boolean) {
        _searchQuery.value = query
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                if (query.isBlank()) {
                    if (isChildMode) {
                        database.productDao().getAllForChild()
                    } else {
                        database.productDao().getAllForAdult()
                    }
                } else {
                    if (isChildMode) {
                        database.productDao().searchForChild("%$query%")
                    } else {
                        database.productDao().searchForAdult("%$query%")
                    }
                }
            }
            _products.value = result.sortedBy { it.name.lowercase() }
        }
    }

    fun selectProduct(product: ProductEntity) {
        _selectedProduct.value = product
    }

    fun clearSelection() {
        _selectedProduct.value = null
    }

    suspend fun calculateProductCost(productId: Long): Double {
        return calculationEngine.calculateProductCost(productId)
    }

    suspend fun calculateProductNutrition(productId: Long): CalculationEngine.NutritionResult {
        return calculationEngine.calculateProductNutrition(productId)
    }
}