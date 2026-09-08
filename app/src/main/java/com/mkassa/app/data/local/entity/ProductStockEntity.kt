package com.mkassa.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_stock",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductStockEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val incomingQuantity: Double,  // Kirim miqdori
    val costPricePerUnit: Double,  // Kirim narxi bir donada
    val sellingPricePerUnit: Double,  // Sotuv narxi bir donada
    val totalCost: Double,  // Jami kirim summa
    val totalSelling: Double,  // Jami sotuv summa
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
