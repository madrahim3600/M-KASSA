package com.mkassa.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val orderId: Int?,
    val userId: Int?,
    val amount: Double,
    val paymentMethod: String,
    val transactionType: TransactionType,
    val description: String = "",
    val receiptNumber: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class TransactionType {
    PAYMENT,   // To'lov
    REFUND,    // Qaytarish
    EXPENSE    // Xarajat
}
