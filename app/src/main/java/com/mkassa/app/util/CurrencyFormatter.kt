package com.mkassa.app.util

import java.text.DecimalFormat
import java.text.NumberFormat

object CurrencyFormatter {
    private val currencyFormat: NumberFormat = DecimalFormat("#,##0.00")

    fun formatCurrency(amount: Double, currency: String = "UZS"): String {
        return "${currencyFormat.format(amount)} $currency"
    }

    fun formatPrice(price: Double): String {
        return currencyFormat.format(price)
    }

    fun parsePrice(priceString: String): Double {
        return try {
            currencyFormat.parse(priceString)?.toDouble() ?: 0.0
        } catch (e: Exception) {
            0.0
        }
    }
}
