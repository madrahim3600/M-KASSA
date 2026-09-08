package com.mkassa.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkassa.app.data.local.entity.UserEntity
import com.mkassa.app.service.AuthService
import com.mkassa.app.util.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val user = authService.login(username, password)
                if (user != null) {
                    _currentUser.value = user
                    preferencesManager.setCurrentUser(user.id.toString(), user.role.name, user.username)
                    _loginState.value = LoginState.Success(user)
                } else {
                    _loginState.value = LoginState.Error("Login yoki parol noto'g'ri")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Xato sodir bo'ldi")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            preferencesManager.logout()
            _currentUser.value = null
            _loginState.value = LoginState.Idle
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: UserEntity) : LoginState()
    data class Error(val message: String) : LoginState()
}
