package com.mkassa.app.data.repository

import com.mkassa.app.data.local.dao.EmployeeDao
import com.mkassa.app.data.local.entity.EmployeeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepository @Inject constructor(
    private val employeeDao: EmployeeDao
) {
    fun getAllEmployees(): Flow<List<EmployeeEntity>> = employeeDao.getAllEmployees()

    fun getEmployeeById(id: Int): Flow<EmployeeEntity?> = employeeDao.getEmployeeById(id)

    fun getEmployeeByUserId(userId: Int): Flow<EmployeeEntity?> =
        employeeDao.getEmployeeByUserId(userId)

    fun getEmployeesByPosition(position: String): Flow<List<EmployeeEntity>> =
        employeeDao.getEmployeesByPosition(position)

    suspend fun createEmployee(employee: EmployeeEntity): Long =
        employeeDao.insertEmployee(employee)

    suspend fun updateEmployee(employee: EmployeeEntity) = employeeDao.updateEmployee(employee)

    suspend fun deleteEmployee(employee: EmployeeEntity) = employeeDao.deleteEmployee(employee)

    suspend fun deleteEmployeeById(id: Int) = employeeDao.deleteEmployeeById(id)
}
