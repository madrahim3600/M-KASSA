package com.mkassa.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkassa.app.data.local.entity.TableEntity
import com.mkassa.app.data.local.entity.TableStatus
import com.mkassa.app.data.repository.TableRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TableViewModel @Inject constructor(
    private val tableRepository: TableRepository
) : ViewModel() {

    private val _tables = MutableStateFlow<List<TableEntity>>(emptyList())
    val tables: StateFlow<List<TableEntity>> = _tables.asStateFlow()

    private val _emptyTables = MutableStateFlow<List<TableEntity>>(emptyList())
    val emptyTables: StateFlow<List<TableEntity>> = _emptyTables.asStateFlow()

    private val _occupiedTables = MutableStateFlow<List<TableEntity>>(emptyList())
    val occupiedTables: StateFlow<List<TableEntity>> = _occupiedTables.asStateFlow()

    private val _createTableState = MutableStateFlow<CreateTableState>(CreateTableState.Idle)
    val createTableState: StateFlow<CreateTableState> = _createTableState

    init {
        loadTables()
    }

    fun loadTables() {
        viewModelScope.launch {
            tableRepository.getAllActiveTables().collect { tables ->
                _tables.value = tables
                _emptyTables.value = tables.filter { it.status == TableStatus.EMPTY }
                _occupiedTables.value = tables.filter { it.status == TableStatus.OCCUPIED }
            }
        }
    }

    fun createTable(tableNumber: Int, tableName: String, capacity: Int) {
        viewModelScope.launch {
            _createTableState.value = CreateTableState.Loading
            try {
                val table = TableEntity(
                    tableNumber = tableNumber,
                    tableName = tableName,
                    capacity = capacity
                )
                tableRepository.createTable(table)
                _createTableState.value = CreateTableState.Success
                loadTables()
            } catch (e: Exception) {
                _createTableState.value = CreateTableState.Error(e.message ?: "Xato sodir bo'ldi")
            }
        }
    }

    fun updateTableStatus(tableId: Int, status: TableStatus) {
        viewModelScope.launch {
            try {
                tableRepository.updateTableStatus(tableId, status)
                loadTables()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun setCurrentOrder(tableId: Int, orderId: Int?) {
        viewModelScope.launch {
            try {
                tableRepository.setCurrentOrder(tableId, orderId)
                loadTables()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteTable(tableId: Int) {
        viewModelScope.launch {
            try {
                tableRepository.deleteTableById(tableId)
                loadTables()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

sealed class CreateTableState {
    object Idle : CreateTableState()
    object Loading : CreateTableState()
    object Success : CreateTableState()
    data class Error(val message: String) : CreateTableState()
}
