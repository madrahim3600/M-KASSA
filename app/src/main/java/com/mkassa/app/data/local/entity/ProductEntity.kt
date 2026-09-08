package com.mkassa.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryId: Int,
    val name: String,
    val description: String = "",
    val image: String = "",
    val costPrice: Double,  // Kirim narxi
    val sellingPrice: Double,  // Sotuv narxi
    val unit: String,  // Ölchov turi (dona, litr, kg, va hokazo)
    val quantity: Double,  // Qolgan miqdori
    val minQuantity: Double = 5.0,  // Minimum miqdor
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
