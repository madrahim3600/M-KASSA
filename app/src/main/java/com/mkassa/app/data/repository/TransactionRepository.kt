package com.mkassa.app.data.repository

import com.mkassa.app.data.local.dao.TransactionDao
import com.mkassa.app.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    fun getAllTransactions(): Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()

    fun getTransactionById(id: Int): Flow<TransactionEntity?> = transactionDao.getTransactionById(id)

    fun getOrderTransactions(orderId: Int): Flow<List<TransactionEntity>> =
        transactionDao.getOrderTransactions(orderId)

    fun getTransactionsByDateRange(startTime: Long, endTime: Long): Flow<List<TransactionEntity>> =
        transactionDao.getTransactionsByDateRange(startTime, endTime)

    suspend fun createTransaction(transaction: TransactionEntity): Long =
        transactionDao.insertTransaction(transaction)
}
