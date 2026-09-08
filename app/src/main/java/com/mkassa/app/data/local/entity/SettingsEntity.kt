package com.mkassa.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,  // Har doim 1 bo'ladi
    val restaurantName: String = "M-KASSA",
    val restaurantPhone: String = "",
    val restaurantEmail: String = "",
    val restaurantAddress: String = "",
    val currency: String = "UZS",
    val theme: String = "LIGHT",  // LIGHT yoki DARK
    val primaryColor: String = "#2196F3",  // Ko'k
    val accentColor: String = "#FF5722",  // Naranjisish
    val receiptFormat: String = "THERMAL",  // THERMAL yoki STANDARD
    val receiptWidth: Int = 80,  // mm
    val printerType: String = "SUNMI",  // Printer turi
    val autoBackup: Boolean = true,
    val backupInterval: Int = 24,  // Soat
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
