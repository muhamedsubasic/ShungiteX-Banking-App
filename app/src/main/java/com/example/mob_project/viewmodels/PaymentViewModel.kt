package com.example.mob_project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {
    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Idle)
    val paymentState: StateFlow<PaymentState> = _paymentState

    fun submitPayment(
        fromAccount: String,
        toAccount: String,
        amount: String,
        date: String,
        convertCurrency: Boolean,
        emergencyPayment: Boolean
    ) {
        if (amount.toDoubleOrNull() == null) {
            _paymentState.value = PaymentState.Error("Invalid amount")
            return
        }

        viewModelScope.launch {
            _paymentState.value = PaymentState.Loading
            try {
                // Call payment service
                _paymentState.value = PaymentState.Success
            } catch (e: Exception) {
                _paymentState.value = PaymentState.Error(e.message ?: "Payment failed")
            }
        }
    }
}

sealed class PaymentState {
    object Idle : PaymentState()
    object Loading : PaymentState()
    object Success : PaymentState()
    data class Error(val message: String) : PaymentState()
}