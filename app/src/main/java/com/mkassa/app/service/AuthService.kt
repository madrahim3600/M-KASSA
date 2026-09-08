package com.mkassa.app.service

import com.mkassa.app.data.repository.UserRepository
import com.mkassa.app.data.local.entity.UserEntity
import com.mkassa.app.data.local.entity.UserRole
import com.mkassa.app.util.PasswordHasher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun login(username: String, password: String): UserEntity? {
        val user = userRepository.getUserByUsernameSync(username)
        return if (user != null && PasswordHasher.verify(password, user.passwordHash)) {
            userRepository.updateLastLogin(user.id)
            user
        } else {
            null
        }
    }

    suspend fun createSuperAdmin(
        username: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String,
        phone: String
    ): Long {
        val user = UserEntity(
            username = username,
            passwordHash = PasswordHasher.hash(password),
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            role = UserRole.SUPER_ADMIN
        )
        return userRepository.createUser(user)
    }

    suspend fun createAdmin(
        username: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String,
        phone: String
    ): Long {
        val user = UserEntity(
            username = username,
            passwordHash = PasswordHasher.hash(password),
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            role = UserRole.ADMIN
        )
        return userRepository.createUser(user)
    }

    suspend fun createOffisant(
        username: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String,
        phone: String
    ): Long {
        val user = UserEntity(
            username = username,
            passwordHash = PasswordHasher.hash(password),
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            role = UserRole.OFFISANT
        )
        return userRepository.createUser(user)
    }

    suspend fun changePassword(userId: Int, oldPassword: String, newPassword: String): Boolean {
        val user = userRepository.getUserById(userId)
        return try {
            // Flow orqali user olish kerak
            false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun resetPassword(userId: Int, newPassword: String) {
        val user = userRepository.getUserById(userId)
        // Flow bilan ishlash kerak
    }

    fun getAllAdmins(): Flow<List<UserEntity>> =
        userRepository.getUsersByRole(UserRole.ADMIN)

    fun getAllOffisants(): Flow<List<UserEntity>> =
        userRepository.getUsersByRole(UserRole.OFFISANT)
}
