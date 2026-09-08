package com.mkassa.app.service

import com.mkassa.app.data.repository.OrderRepository
import com.mkassa.app.data.repository.TransactionRepository
import com.mkassa.app.data.local.entity.TransactionEntity
import com.mkassa.app.data.local.entity.TransactionType
import com.mkassa.app.util.OrderNumberGenerator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionService @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val orderRepository: OrderRepository
) {
    suspend fun recordPayment(
        orderId: Int?,
        amount: Double,
        paymentMethod: String
    ): Long {
        val transaction = TransactionEntity(
            orderId = orderId,
            amount = amount,
            paymentMethod = paymentMethod,
            transactionType = TransactionType.PAYMENT,
            receiptNumber = OrderNumberGenerator.generateReceiptNumber()
        )
        return transactionRepository.createTransaction(transaction)
    }

    suspend fun recordRefund(
        orderId: Int?,
        amount: Double,
        reason: String = ""
    ): Long {
        val transaction = TransactionEntity(
            orderId = orderId,
            amount = amount,
            paymentMethod = "REFUND",
            transactionType = TransactionType.REFUND,
            description = reason
        )
        return transactionRepository.createTransaction(transaction)
    }

    fun getTransactionsByDateRange(startTime: Long, endTime: Long): Flow<List<TransactionEntity>> =
        transactionRepository.getTransactionsByDateRange(startTime, endTime)
}
