package com.example.mob_project.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
//    var email = mutableStateOf("")
//        private set
//
//    var password = mutableStateOf("")
//        private set
//
//    var isLoading = mutableStateOf(false)
//        private set
//
//    var loginError = mutableStateOf<String?>(null)
//        private set
//
//    fun onEmailChange(newEmail: String) {
//        email.value = newEmail
//    }
//
//    fun onPasswordChange(newPassword: String) {
//        password.value = newPassword
//    }
//
//    fun login(onSuccess: () -> Unit) {
//        isLoading.value = true
//        loginError.value = null
//        viewModelScope.launch {
//            // TODO: Replace with real authentication logic
//            if (email.value == "test@example.com" && password.value == "password") {
//                onSuccess()
//            } else {
//                loginError.value = "Invalid credentials"
//            }
//            isLoading.value = false
//        }
//    }
}