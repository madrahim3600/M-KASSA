package com.mkassa.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,  // Bog'langan user ID
    val position: String,  // Lavozim
    val jobType: String,  // Ish turi
    val dailyWage: Double = 0.0,  // Kunlik maoshi
    val monthlyWage: Double = 0.0,  // Oylik maoshi
    val commissionPercent: Double = 0.0,  // Xizmat haqi %
    val totalEarnings: Double = 0.0,  // Jami kirim
    val notes: String = "",
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
