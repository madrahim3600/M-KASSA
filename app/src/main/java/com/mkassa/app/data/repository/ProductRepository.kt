package com.mkassa.app.data.repository

import com.mkassa.app.data.local.dao.ProductDao
import com.mkassa.app.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao
) {
    fun getAllProducts(): Flow<List<ProductEntity>> = productDao.getAllProducts()

    fun getAllActiveProducts(): Flow<List<ProductEntity>> = productDao.getAllActiveProducts()

    fun getProductsByCategory(categoryId: Int): Flow<List<ProductEntity>> =
        productDao.getProductsByCategory(categoryId)

    fun getProductById(id: Int): Flow<ProductEntity?> = productDao.getProductById(id)

    fun getLowStockProducts(): Flow<List<ProductEntity>> = productDao.getLowStockProducts()

    suspend fun createProduct(product: ProductEntity): Long = productDao.insertProduct(product)

    suspend fun updateProduct(product: ProductEntity) = productDao.updateProduct(product)

    suspend fun deleteProduct(product: ProductEntity) = productDao.deleteProduct(product)

    suspend fun deleteProductById(id: Int) = productDao.deleteProductById(id)

    suspend fun decreaseQuantity(productId: Int, amount: Double) =
        productDao.decreaseQuantity(productId, amount)

    suspend fun increaseQuantity(productId: Int, amount: Double) =
        productDao.increaseQuantity(productId, amount)
}
