package com.mkassa.app.data.repository

import com.mkassa.app.data.local.dao.TableDao
import com.mkassa.app.data.local.entity.TableEntity
import com.mkassa.app.data.local.entity.TableStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableRepository @Inject constructor(
    private val tableDao: TableDao
) {
    fun getAllTables(): Flow<List<TableEntity>> = tableDao.getAllTables()

    fun getAllActiveTables(): Flow<List<TableEntity>> = tableDao.getAllActiveTables()

    fun getTableById(id: Int): Flow<TableEntity?> = tableDao.getTableById(id)

    fun getTablesByStatus(status: TableStatus): Flow<List<TableEntity>> =
        tableDao.getTablesByStatus(status)

    suspend fun createTable(table: TableEntity): Long = tableDao.insertTable(table)

    suspend fun updateTable(table: TableEntity) = tableDao.updateTable(table)

    suspend fun deleteTable(table: TableEntity) = tableDao.deleteTable(table)

    suspend fun deleteTableById(id: Int) = tableDao.deleteTableById(id)

    suspend fun updateTableStatus(tableId: Int, status: TableStatus) =
        tableDao.updateTableStatus(tableId, status)

    suspend fun setCurrentOrder(tableId: Int, orderId: Int?) =
        tableDao.setCurrentOrder(tableId, orderId)
}
