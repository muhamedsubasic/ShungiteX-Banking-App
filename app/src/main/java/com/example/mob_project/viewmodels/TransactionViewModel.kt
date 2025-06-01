package com.example.mob_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mob_project.model.Transaction
import com.example.mob_project.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    init {
        viewModelScope.launch {
            _transactions.value = transactionRepository.getAllTransactions()
        }
    }

    fun insertSampleTransaction() {
        viewModelScope.launch {
            val sample = Transaction(
                transactionType = "Store X",
                amount = 19.99,
                referenceNumber = "REF123456",
                status = "SENT",
                date = Date(),
                currency = "USD",
                paymentId = null
            )
            transactionRepository.insert(sample)


            _transactions.value = transactionRepository.getAllTransactions()
        }
    }
}