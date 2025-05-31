package com.example.mob_project.viewmodels

import com.example.mob_project.navigation.BottomNavItem


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String, navController: NavHostController) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            when {
                email.isEmpty() || password.isEmpty() -> {
                    _loginState.value = LoginState.Error("Email and password required")
                }
                !isValidEmail(email) -> {
                    _loginState.value = LoginState.Error("Invalid email format")
                }
                password.length < 6 -> {
                    _loginState.value = LoginState.Error("Password too short")
                }
                else -> {
                    kotlinx.coroutines.delay(1000)

                    if (email == "test@example.com" && password == "password123") {
                        _loginState.value = LoginState.Success
                        navController.navigate(BottomNavItem.Home.route) {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        _loginState.value = LoginState.Error("Invalid credentials")
                    }
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}