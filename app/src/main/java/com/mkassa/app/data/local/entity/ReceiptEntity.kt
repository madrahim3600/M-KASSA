package com.mkassa.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "receipts",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ReceiptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val orderId: Int,
    val receiptNumber: String,
    val receiptFormat: String,  // JSON format
    val isPrinted: Boolean = false,
    val isSent: Boolean = false,
    val printedAt: Long? = null,
    val sentAt: Long? = null,
    val sentTo: String = "",  // Email, SMS, yoki boshqa
    val createdAt: Long = System.currentTimeMillis()
)
