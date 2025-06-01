package com.example.mob_project.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mob_project.model.Transaction
import com.example.mob_project.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {
//
//    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
//    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()
//
//    init {
//        fetchTransactions()
//    }
//
//    private fun fetchTransactions() {
//        viewModelScope.launch {
//            _transactions.value = repository.getAllTransactions()
//        }
//    }
//
//    fun addTransaction(transaction: Transaction) {
//        viewModelScope.launch {
//            repository.insert(transaction)
//            fetchTransactions()
//        }
//    }
//
//    fun updateTransaction(transaction: Transaction) {
//        viewModelScope.launch {
//            repository.update(transaction)
//            fetchTransactions()
//        }
//    }
//
//    fun deleteTransaction(transaction: Transaction) {
//        viewModelScope.launch {
//            repository.delete(transaction)
//            fetchTransactions()
//        }
//    }
}
