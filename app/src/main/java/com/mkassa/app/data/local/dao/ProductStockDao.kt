package com.mkassa.app.data.local.dao

import androidx.room.*
import com.mkassa.app.data.local.entity.ProductStockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductStockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: ProductStockEntity): Long

    @Update
    suspend fun updateStock(stock: ProductStockEntity)

    @Query("SELECT * FROM product_stock WHERE id = :id")
    fun getStockById(id: Int): Flow<ProductStockEntity?>

    @Query("SELECT * FROM product_stock WHERE productId = :productId ORDER BY createdAt DESC")
    fun getProductStockHistory(productId: Int): Flow<List<ProductStockEntity>>

    @Query("SELECT SUM(incomingQuantity) FROM product_stock WHERE productId = :productId")
    fun getTotalIncomingQuantity(productId: Int): Flow<Double?>

    @Query("DELETE FROM product_stock WHERE id = :id")
    suspend fun deleteStockById(id: Int)
}
