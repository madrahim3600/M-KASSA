package com.mkassa.app.data.local.dao

import androidx.room.*
import com.mkassa.app.data.local.entity.OrderEntity
import com.mkassa.app.data.local.entity.OrderStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity): Long

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Delete
    suspend fun deleteOrder(order: OrderEntity)

    @Query("SELECT * FROM orders WHERE id = :id")
    fun getOrderById(id: Int): Flow<OrderEntity?>

    @Query("SELECT * FROM orders WHERE orderNumber = :orderNumber")
    fun getOrderByNumber(orderNumber: String): Flow<OrderEntity?>

    @Query("SELECT * FROM orders WHERE status = :status ORDER BY createdAt DESC")
    fun getOrdersByStatus(status: OrderStatus): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders ORDER BY createdAt DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE tableId = :tableId ORDER BY createdAt DESC")
    fun getTableOrders(tableId: Int): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE createdAt BETWEEN :startTime AND :endTime ORDER BY createdAt DESC")
    fun getOrdersByDateRange(startTime: Long, endTime: Long): Flow<List<OrderEntity>>

    @Query("SELECT COUNT(*) FROM orders WHERE DATE(datetime(createdAt/1000, 'unixepoch')) = DATE('now')")
    fun getTodayOrderCount(): Flow<Int>

    @Query("SELECT SUM(totalAmount) FROM orders WHERE isPaid = 1 AND DATE(datetime(createdAt/1000, 'unixepoch')) = DATE('now')")
    fun getTodayTotalAmount(): Flow<Double?>

    @Query("UPDATE orders SET status = :status, updatedAt = :timestamp WHERE id = :orderId")
    suspend fun updateOrderStatus(orderId: Int, status: OrderStatus, timestamp: Long = System.currentTimeMillis())

    @Query("DELETE FROM orders WHERE id = :id")
    suspend fun deleteOrderById(id: Int)
}
