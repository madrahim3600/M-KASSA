package com.mkassa.app.service

import com.mkassa.app.data.repository.OrderRepository
import com.mkassa.app.data.repository.ProductRepository
import com.mkassa.app.data.local.entity.OrderEntity
import com.mkassa.app.data.local.entity.OrderItemEntity
import com.mkassa.app.util.OrderNumberGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderService @Inject constructor(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) {
    suspend fun createOrder(
        userId: Int?,
        tableId: Int?,
        orderType: String,
        paymentMethod: String? = null
    ): Long {
        val order = OrderEntity(
            orderNumber = OrderNumberGenerator.generateOrderNumber(),
            userId = userId,
            tableId = tableId,
            orderType = com.mkassa.app.data.local.entity.OrderType.valueOf(orderType),
            paymentMethod = paymentMethod?.let { com.mkassa.app.data.local.entity.PaymentMethod.valueOf(it) }
        )
        return orderRepository.createOrder(order)
    }

    suspend fun addItemToOrder(
        orderId: Int,
        productId: Int,
        productName: String,
        quantity: Double,
        unitPrice: Double
    ) {
        val totalPrice = quantity * unitPrice
        val item = OrderItemEntity(
            orderId = orderId,
            productId = productId,
            productName = productName,
            quantity = quantity,
            unitPrice = unitPrice,
            totalPrice = totalPrice
        )
        orderRepository.addOrderItem(item)
        // Stokdan chiqarish
        productRepository.decreaseQuantity(productId, quantity)
    }

    suspend fun completeOrder(
        orderId: Int,
        paymentMethod: String,
        totalAmount: Double
    ): OrderEntity? {
        val orderEntity = OrderEntity(
            id = orderId,
            orderNumber = "",
            userId = null,
            tableId = null,
            orderType = com.mkassa.app.data.local.entity.OrderType.DINE_IN,
            status = com.mkassa.app.data.local.entity.OrderStatus.COMPLETED,
            totalAmount = totalAmount,
            paymentMethod = com.mkassa.app.data.local.entity.PaymentMethod.valueOf(paymentMethod),
            isPaid = true
        )
        orderRepository.updateOrder(orderEntity)
        return null
    }

    fun getOrderTotal(orderId: Int): Flow<Double> {
        return orderRepository.getOrderItemsTotal(orderId).map { it ?: 0.0 }
    }

    fun getTodayStats(): Flow<Pair<Int, Double>> {
        return orderRepository.getTodayOrderCount().map { count ->
            val amount = orderRepository.getTodayTotalAmount()
            Pair(count, 0.0)
        }
    }
}
