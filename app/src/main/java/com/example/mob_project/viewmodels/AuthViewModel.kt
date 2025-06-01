package com.example.mob_project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mob_project.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            when {
                username.isEmpty() || password.isEmpty() -> {
                    _loginState.value = LoginState.Error("Username and password required")
                }
                password.length < 6 -> {
                    _loginState.value = LoginState.Error("Password must be at least 6 characters")
                }
                else -> {
                    try {
                        val user = userDao.getUserByUsernameAndPassword(username, password)
                        if (user != null) {
                            _isLoggedIn.value = true
                            _loginState.value = LoginState.Success
                        } else {
                            _loginState.value = LoginState.Error("Invalid credentials")
                        }
                    } catch (e: Exception) {
                        _loginState.value = LoginState.Error("Login failed: ${e.message}")
                    }
                }
            }
        }
    }

    fun logout(navController: NavHostController) {
        viewModelScope.launch {
            _isLoggedIn.value = false
            _loginState.value = LoginState.Idle

            // Switch to main thread before navigation
            withContext(Dispatchers.Main) {
                navController.navigate("login") {
                    popUpTo(0)
                }
            }
        }
    }

}
