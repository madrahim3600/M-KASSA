package com.mkassa.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkassa.app.data.local.entity.OrderEntity
import com.mkassa.app.data.local.entity.OrderStatus
import com.mkassa.app.data.repository.OrderRepository
import com.mkassa.app.service.OrderService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val orderService: OrderService
) : ViewModel() {

    private val _orders = MutableStateFlow<List<OrderEntity>>(emptyList())
    val orders: StateFlow<List<OrderEntity>> = _orders.asStateFlow()

    private val _currentOrder = MutableStateFlow<OrderEntity?>(null)
    val currentOrder: StateFlow<OrderEntity?> = _currentOrder.asStateFlow()

    private val _todayOrderCount = MutableStateFlow(0)
    val todayOrderCount: StateFlow<Int> = _todayOrderCount.asStateFlow()

    private val _todayTotalAmount = MutableStateFlow(0.0)
    val todayTotalAmount: StateFlow<Double> = _todayTotalAmount.asStateFlow()

    init {
        loadTodayStats()
        loadOrders()
    }

    fun loadOrders() {
        viewModelScope.launch {
            orderRepository.getAllOrders().collect { orders ->
                _orders.value = orders
            }
        }
    }

    fun loadTodayStats() {
        viewModelScope.launch {
            orderRepository.getTodayOrderCount().collect { count ->
                _todayOrderCount.value = count
            }
        }
        viewModelScope.launch {
            orderRepository.getTodayTotalAmount().collect { amount ->
                _todayTotalAmount.value = amount ?: 0.0
            }
        }
    }

    fun createOrder(userId: Int?, tableId: Int?, orderType: String, paymentMethod: String? = null) {
        viewModelScope.launch {
            try {
                val orderId = orderService.createOrder(userId, tableId, orderType, paymentMethod)
                val order = OrderEntity(
                    id = orderId.toInt(),
                    orderNumber = "",
                    userId = userId,
                    tableId = tableId,
                    orderType = com.mkassa.app.data.local.entity.OrderType.valueOf(orderType)
                )
                _currentOrder.value = order
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun updateOrderStatus(orderId: Int, status: OrderStatus) {
        viewModelScope.launch {
            try {
                orderRepository.updateOrderStatus(orderId, status)
                loadOrders()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
