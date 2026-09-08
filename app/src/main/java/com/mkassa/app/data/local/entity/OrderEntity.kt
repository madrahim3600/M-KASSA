package com.mkassa.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = TableEntity::class,
            parentColumns = ["id"],
            childColumns = ["tableId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val orderNumber: String,  // Buyurtma raqami
    val userId: Int?,  // Offisant ID
    val tableId: Int?,  // Xona ID (olib ketish uchun NULL)
    val orderType: OrderType,  // Olib ketish, Online, Xonada
    val status: OrderStatus = OrderStatus.PENDING,
    val totalAmount: Double = 0.0,
    val paymentMethod: PaymentMethod? = null,  // Naqd, Online, Qarz
    val isPaid: Boolean = false,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val updatedAt: Long = System.currentTimeMillis()
)

enum class OrderType {
    DINE_IN,      // Xonada
    TAKE_AWAY,    // Olib ketish
    ONLINE        // Online buyurtma
}

enum class OrderStatus {
    PENDING,      // Kutilmoqda
    PREPARING,    // Tayyorlanmoqda
    READY,        // Tayyor
    SERVED,       // Xizmat qilindi
    COMPLETED,    // Tugallandi
    CANCELLED     // Bekor qilindi
}

enum class PaymentMethod {
    CASH,         // Naqd pul
    ONLINE,       // Online to'lov
    CREDIT        // Qarz
}
