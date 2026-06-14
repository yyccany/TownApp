package com.example.townapp.data.repository

import com.example.townapp.data.database.TownDatabase
import com.example.townapp.data.database.entity.MaterialEntity
import com.example.townapp.data.database.entity.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val database: TownDatabase) {
    suspend fun getAllProducts(isChildMode: Boolean): List<ProductEntity> {
        return withContext(Dispatchers.IO) {
            if (isChildMode) {
                database.productDao().getAllForChild()
            } else {
                database.productDao().getAllForAdult()
            }
        }
    }

    suspend fun getProductById(id: Long): ProductEntity? {
        return withContext(Dispatchers.IO) {
            database.productDao().getById(id)
        }
    }

    suspend fun getProductsByCategory(category: String, isChildMode: Boolean): List<ProductEntity> {
        return withContext(Dispatchers.IO) {
            if (isChildMode) {
                database.productDao().getByCategoryForChild(category)
            } else {
                database.productDao().getByCategoryForAdult(category)
            }
        }
    }

    suspend fun searchProducts(query: String, isChildMode: Boolean): List<ProductEntity> {
        return withContext(Dispatchers.IO) {
            if (isChildMode) {
                database.productDao().searchForChild("%$query%")
            } else {
                database.productDao().searchForAdult("%$query%")
            }
        }
    }

    suspend fun getAllCategories(): List<String> {
        return withContext(Dispatchers.IO) {
            database.productDao().getAllCategories()
        }
    }

    suspend fun insertProduct(product: ProductEntity): Long {
        return withContext(Dispatchers.IO) {
            database.productDao().insert(product)
        }
    }

    suspend fun updateProduct(product: ProductEntity) {
        withContext(Dispatchers.IO) {
            database.productDao().update(product)
        }
    }

    suspend fun getAllMaterials(): List<MaterialEntity> {
        return withContext(Dispatchers.IO) {
            database.materialDao().getAll()
        }
    }

    suspend fun getMaterialById(id: Long): MaterialEntity? {
        return withContext(Dispatchers.IO) {
            database.materialDao().getById(id)
        }
    }

    suspend fun getMaterialsByCategory(category: String): List<MaterialEntity> {
        return withContext(Dispatchers.IO) {
            database.materialDao().getByCategory(category)
        }
    }

    suspend fun insertMaterial(material: MaterialEntity): Long {
        return withContext(Dispatchers.IO) {
            database.materialDao().insert(material)
        }
    }
}