package com.mkassa.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_tables")
data class TableEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tableNumber: Int,
    val tableName: String,
    val capacity: Int,  // Sig'im (necha kishi)
    val status: TableStatus = TableStatus.EMPTY,  // Bo'sh yoki band
    val currentOrderId: Int? = null,  // Joriy buyurtma ID
    val notes: String = "",
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class TableStatus {
    EMPTY,    // Yashil - bo'sh
    OCCUPIED, // Qizil - band
    RESERVED  // Sariq - band qilindi
}
