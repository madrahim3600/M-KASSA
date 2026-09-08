package com.mkassa.app.util

object OrderNumberGenerator {
    fun generateOrderNumber(): String {
        val timestamp = System.currentTimeMillis()
        val random = (1000..9999).random()
        return "ORD-$timestamp-$random"
    }

    fun generateReceiptNumber(): String {
        val timestamp = System.currentTimeMillis()
        return "REC-$timestamp"
    }
}
