package com.mkassa.app.data.local.dao

import androidx.room.*
import com.mkassa.app.data.local.entity.EmployeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: EmployeeEntity): Long

    @Update
    suspend fun updateEmployee(employee: EmployeeEntity)

    @Delete
    suspend fun deleteEmployee(employee: EmployeeEntity)

    @Query("SELECT * FROM employees WHERE id = :id")
    fun getEmployeeById(id: Int): Flow<EmployeeEntity?>

    @Query("SELECT * FROM employees WHERE userId = :userId")
    fun getEmployeeByUserId(userId: Int): Flow<EmployeeEntity?>

    @Query("SELECT * FROM employees ORDER BY createdAt DESC")
    fun getAllEmployees(): Flow<List<EmployeeEntity>>

    @Query("SELECT * FROM employees WHERE position = :position ORDER BY createdAt DESC")
    fun getEmployeesByPosition(position: String): Flow<List<EmployeeEntity>>

    @Query("DELETE FROM employees WHERE id = :id")
    suspend fun deleteEmployeeById(id: Int)
}
