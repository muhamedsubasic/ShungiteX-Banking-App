package com.example.mob_project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mob_project.dao.UserDao
import com.example.mob_project.navigation.BottomNavItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(/*private val userDao: UserDao*/) : ViewModel() {
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

    init {
        // Temporarily disable DB check
        _isLoggedIn.value = false
        /*
        viewModelScope.launch {
            try {
                val userCount = userDao.getUserCount()
                _isLoggedIn.value = userCount > 0
            } catch (e: Exception) {
                _isLoggedIn.value = false
            }
        }
        */
    }

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
                    _loginState.value = LoginState.Error("Password must be at least 6 characters")
                }
                else -> {
                    // Temporarily allow any valid email/password
                    _isLoggedIn.value = true
                    _loginState.value = LoginState.Success
                    navController.navigate(BottomNavItem.Home.route) {
                        popUpTo("login") { inclusive = true }
                    }
                    /*
                    try {
                        val user = userDao.getUserByEmail(email)
                        if (user != null && user.password == password) {
                            _isLoggedIn.value = true
                            _loginState.value = LoginState.Success
                            navController.navigate(BottomNavItem.Home.route) {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            _loginState.value = LoginState.Error("Invalid credentials")
                        }
                    } catch (e: Exception) {
                        _loginState.value = LoginState.Error("Login failed: ${e.message}")
                    }
                    */
                }
            }
        }
    }

    fun logout(navController: NavHostController) {
        viewModelScope.launch {
            try {
                // userDao.clearAllUsers()  // Commented out for now

                _isLoggedIn.value = false
                _loginState.value = LoginState.Idle

                navController.navigate("login") {
                    popUpTo(0)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Logout failed")
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}