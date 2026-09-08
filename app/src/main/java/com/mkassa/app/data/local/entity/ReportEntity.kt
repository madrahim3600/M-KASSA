package com.mkassa.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val reportDate: Long,
    val totalOrders: Int,
    val totalAmount: Double,
    val cashPayment: Double,
    val onlinePayment: Double,
    val creditPayment: Double,
    val refunds: Double,
    val expenses: Double,
    val netIncome: Double,
    val createdAt: Long = System.currentTimeMillis()
)
