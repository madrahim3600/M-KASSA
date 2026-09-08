package com.mkassa.app.data.repository

import com.mkassa.app.data.local.dao.UserDao
import com.mkassa.app.data.local.entity.UserEntity
import com.mkassa.app.data.local.entity.UserRole
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getAllUsers(): Flow<List<UserEntity>> = userDao.getAllUsers()

    fun getActiveUsers(): Flow<List<UserEntity>> = userDao.getActiveUsers()

    fun getUsersByRole(role: UserRole): Flow<List<UserEntity>> =
        userDao.getUsersByRole(role.name)

    fun getUserById(id: Int): Flow<UserEntity?> = userDao.getUserById(id)

    fun getUserByUsername(username: String): Flow<UserEntity?> =
        userDao.getUserByUsername(username)

    suspend fun getUserByUsernameSync(username: String): UserEntity? =
        userDao.getUserByUsernameSync(username)

    suspend fun createUser(user: UserEntity): Long = userDao.insertUser(user)

    suspend fun updateUser(user: UserEntity) = userDao.updateUser(user)

    suspend fun deleteUser(user: UserEntity) = userDao.deleteUser(user)

    suspend fun deleteUserById(id: Int) = userDao.deleteUserById(id)

    suspend fun updateLastLogin(userId: Int) =
        userDao.updateLastLogin(userId, System.currentTimeMillis())

    fun getUserCount(): Flow<Int> = userDao.getUserCount()
}
