package com.mkassa.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkassa.app.data.repository.UserRepository
import com.mkassa.app.data.repository.TransactionRepository
import com.mkassa.app.util.DateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _dailyReport = MutableStateFlow<DailyReport?>(null)
    val dailyReport: StateFlow<DailyReport?> = _dailyReport.asStateFlow()

    private val _monthlyReport = MutableStateFlow<MonthlyReport?>(null)
    val monthlyReport: StateFlow<MonthlyReport?> = _monthlyReport.asStateFlow()

    fun loadDailyReport() {
        viewModelScope.launch {
            try {
                val startTime = DateTimeUtils.getTodayStartTime()
                val endTime = DateTimeUtils.getTodayEndTime()
                transactionRepository.getTransactionsByDateRange(startTime, endTime).collect { transactions ->
                    val totalAmount = transactions.sumOf { it.amount }
                    val cashPayment = transactions.filter { it.paymentMethod == "CASH" }.sumOf { it.amount }
                    val onlinePayment = transactions.filter { it.paymentMethod == "ONLINE" }.sumOf { it.amount }
                    val creditPayment = transactions.filter { it.paymentMethod == "CREDIT" }.sumOf { it.amount }

                    _dailyReport.value = DailyReport(
                        date = System.currentTimeMillis(),
                        totalTransactions = transactions.size,
                        totalAmount = totalAmount,
                        cashPayment = cashPayment,
                        onlinePayment = onlinePayment,
                        creditPayment = creditPayment
                    )
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun loadMonthlyReport() {
        viewModelScope.launch {
            try {
                val startTime = DateTimeUtils.getMonthStart()
                val endTime = DateTimeUtils.getMonthEnd()
                transactionRepository.getTransactionsByDateRange(startTime, endTime).collect { transactions ->
                    val totalAmount = transactions.sumOf { it.amount }
                    val cashPayment = transactions.filter { it.paymentMethod == "CASH" }.sumOf { it.amount }
                    val onlinePayment = transactions.filter { it.paymentMethod == "ONLINE" }.sumOf { it.amount }

                    _monthlyReport.value = MonthlyReport(
                        totalTransactions = transactions.size,
                        totalAmount = totalAmount,
                        cashPayment = cashPayment,
                        onlinePayment = onlinePayment
                    )
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

data class DailyReport(
    val date: Long,
    val totalTransactions: Int,
    val totalAmount: Double,
    val cashPayment: Double,
    val onlinePayment: Double,
    val creditPayment: Double
)

data class MonthlyReport(
    val totalTransactions: Int,
    val totalAmount: Double,
    val cashPayment: Double,
    val onlinePayment: Double
)
