package com.mkassa.app.data.local.dao

import androidx.room.*
import com.mkassa.app.data.local.entity.ReceiptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: ReceiptEntity): Long

    @Update
    suspend fun updateReceipt(receipt: ReceiptEntity)

    @Query("SELECT * FROM receipts WHERE id = :id")
    fun getReceiptById(id: Int): Flow<ReceiptEntity?>

    @Query("SELECT * FROM receipts WHERE orderId = :orderId")
    fun getReceiptByOrderId(orderId: Int): Flow<ReceiptEntity?>

    @Query("SELECT * FROM receipts ORDER BY createdAt DESC")
    fun getAllReceipts(): Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM receipts WHERE isPrinted = 0 ORDER BY createdAt ASC")
    fun getUnprintedReceipts(): Flow<List<ReceiptEntity>>
}
