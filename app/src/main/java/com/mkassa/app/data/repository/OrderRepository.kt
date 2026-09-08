package com.mkassa.app.data.repository

import com.mkassa.app.data.local.dao.OrderDao
import com.mkassa.app.data.local.dao.OrderItemDao
import com.mkassa.app.data.local.entity.OrderEntity
import com.mkassa.app.data.local.entity.OrderItemEntity
import com.mkassa.app.data.local.entity.OrderStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
    private val orderItemDao: OrderItemDao
) {
    fun getAllOrders(): Flow<List<OrderEntity>> = orderDao.getAllOrders()

    fun getOrderById(id: Int): Flow<OrderEntity?> = orderDao.getOrderById(id)

    fun getOrderByNumber(orderNumber: String): Flow<OrderEntity?> =
        orderDao.getOrderByNumber(orderNumber)

    fun getOrdersByStatus(status: OrderStatus): Flow<List<OrderEntity>> =
        orderDao.getOrdersByStatus(status)

    fun getTableOrders(tableId: Int): Flow<List<OrderEntity>> =
        orderDao.getTableOrders(tableId)

    fun getOrdersByDateRange(startTime: Long, endTime: Long): Flow<List<OrderEntity>> =
        orderDao.getOrdersByDateRange(startTime, endTime)

    fun getTodayOrderCount(): Flow<Int> = orderDao.getTodayOrderCount()

    fun getTodayTotalAmount(): Flow<Double?> = orderDao.getTodayTotalAmount()

    fun getOrderItems(orderId: Int): Flow<List<OrderItemEntity>> =
        orderItemDao.getOrderItems(orderId)

    fun getOrderItemsTotal(orderId: Int): Flow<Double?> =
        orderItemDao.getOrderItemsTotal(orderId)

    suspend fun createOrder(order: OrderEntity): Long = orderDao.insertOrder(order)

    suspend fun updateOrder(order: OrderEntity) = orderDao.updateOrder(order)

    suspend fun deleteOrder(order: OrderEntity) = orderDao.deleteOrder(order)

    suspend fun addOrderItem(item: OrderItemEntity): Long = orderItemDao.insertOrderItem(item)

    suspend fun addOrderItems(items: List<OrderItemEntity>) = orderItemDao.insertOrderItems(items)

    suspend fun updateOrderItem(item: OrderItemEntity) = orderItemDao.updateOrderItem(item)

    suspend fun deleteOrderItem(item: OrderItemEntity) = orderItemDao.deleteOrderItem(item)

    suspend fun deleteOrderItems(orderId: Int) = orderItemDao.deleteOrderItems(orderId)

    suspend fun updateOrderStatus(orderId: Int, status: OrderStatus) =
        orderDao.updateOrderStatus(orderId, status)
}
