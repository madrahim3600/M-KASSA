package com.mkassa.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkassa.app.data.local.entity.UserEntity
import com.mkassa.app.data.local.entity.UserRole
import com.mkassa.app.data.repository.UserRepository
import com.mkassa.app.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserManagementViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authService: AuthService
) : ViewModel() {

    private val _users = MutableStateFlow<List<UserEntity>>(emptyList())
    val users: StateFlow<List<UserEntity>> = _users.asStateFlow()

    private val _createUserState = MutableStateFlow<CreateUserState>(CreateUserState.Idle)
    val createUserState: StateFlow<CreateUserState> = _createUserState

    private val _deleteUserState = MutableStateFlow<DeleteUserState>(DeleteUserState.Idle)
    val deleteUserState: StateFlow<DeleteUserState> = _deleteUserState

    init {
        loadAllUsers()
    }

    fun loadAllUsers() {
        viewModelScope.launch {
            userRepository.getAllUsers().collect { userList ->
                _users.value = userList
            }
        }
    }

    fun createAdmin(
        username: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String,
        phone: String
    ) {
        viewModelScope.launch {
            _createUserState.value = CreateUserState.Loading
            try {
                val userId = authService.createAdmin(
                    username, password, firstName, lastName, email, phone
                )
                _createUserState.value = CreateUserState.Success("Admin muvaffaqiyatli yaratildi")
                loadAllUsers()
            } catch (e: Exception) {
                _createUserState.value = CreateUserState.Error(e.message ?: "Xato sodir bo'ldi")
            }
        }
    }

    fun createOffisant(
        username: String,
        password: String,
        firstName: String,
        lastName: String,
        email: String,
        phone: String
    ) {
        viewModelScope.launch {
            _createUserState.value = CreateUserState.Loading
            try {
                val userId = authService.createOffisant(
                    username, password, firstName, lastName, email, phone
                )
                _createUserState.value = CreateUserState.Success("Offisant muvaffaqiyatli yaratildi")
                loadAllUsers()
            } catch (e: Exception) {
                _createUserState.value = CreateUserState.Error(e.message ?: "Xato sodir bo'ldi")
            }
        }
    }

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            try {
                userRepository.updateUser(user)
                loadAllUsers()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            _deleteUserState.value = DeleteUserState.Loading
            try {
                userRepository.deleteUserById(userId)
                _deleteUserState.value = DeleteUserState.Success
                loadAllUsers()
            } catch (e: Exception) {
                _deleteUserState.value = DeleteUserState.Error(e.message ?: "Xato sodir bo'ldi")
            }
        }
    }

    fun getAdmins() = userRepository.getUsersByRole(UserRole.ADMIN)
    fun getOffisants() = userRepository.getUsersByRole(UserRole.OFFISANT)
}

sealed class CreateUserState {
    object Idle : CreateUserState()
    object Loading : CreateUserState()
    data class Success(val message: String) : CreateUserState()
    data class Error(val message: String) : CreateUserState()
}

sealed class DeleteUserState {
    object Idle : DeleteUserState()
    object Loading : DeleteUserState()
    object Success : DeleteUserState()
    data class Error(val message: String) : DeleteUserState()
}
