package com.mkassa.app.data.local.dao

import androidx.room.*
import com.mkassa.app.data.local.entity.TableEntity
import com.mkassa.app.data.local.entity.TableStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTable(table: TableEntity): Long

    @Update
    suspend fun updateTable(table: TableEntity)

    @Delete
    suspend fun deleteTable(table: TableEntity)

    @Query("SELECT * FROM restaurant_tables WHERE id = :id")
    fun getTableById(id: Int): Flow<TableEntity?>

    @Query("SELECT * FROM restaurant_tables WHERE isActive = 1 ORDER BY tableNumber ASC")
    fun getAllActiveTables(): Flow<List<TableEntity>>

    @Query("SELECT * FROM restaurant_tables ORDER BY tableNumber ASC")
    fun getAllTables(): Flow<List<TableEntity>>

    @Query("SELECT * FROM restaurant_tables WHERE status = :status AND isActive = 1 ORDER BY tableNumber ASC")
    fun getTablesByStatus(status: TableStatus): Flow<List<TableEntity>>

    @Query("UPDATE restaurant_tables SET status = :status, updatedAt = :timestamp WHERE id = :tableId")
    suspend fun updateTableStatus(tableId: Int, status: TableStatus, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE restaurant_tables SET currentOrderId = :orderId WHERE id = :tableId")
    suspend fun setCurrentOrder(tableId: Int, orderId: Int?)

    @Query("DELETE FROM restaurant_tables WHERE id = :id")
    suspend fun deleteTableById(id: Int)
}
