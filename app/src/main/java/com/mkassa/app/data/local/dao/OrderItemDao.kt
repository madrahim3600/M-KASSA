package com.mkassa.app.data.local.dao

import androidx.room.*
import com.mkassa.app.data.local.entity.OrderItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(item: OrderItemEntity): Long

    @Insert
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Update
    suspend fun updateOrderItem(item: OrderItemEntity)

    @Delete
    suspend fun deleteOrderItem(item: OrderItemEntity)

    @Query("SELECT * FROM order_items WHERE id = :id")
    fun getOrderItemById(id: Int): Flow<OrderItemEntity?>

    @Query("SELECT * FROM order_items WHERE orderId = :orderId ORDER BY createdAt ASC")
    fun getOrderItems(orderId: Int): Flow<List<OrderItemEntity>>

    @Query("DELETE FROM order_items WHERE orderId = :orderId")
    suspend fun deleteOrderItems(orderId: Int)

    @Query("SELECT SUM(totalPrice) FROM order_items WHERE orderId = :orderId")
    fun getOrderItemsTotal(orderId: Int): Flow<Double?>
}
